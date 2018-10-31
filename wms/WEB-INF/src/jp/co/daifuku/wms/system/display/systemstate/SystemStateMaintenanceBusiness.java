// $Id: SystemStateMaintenanceBusiness.java 7457 2010-03-08 05:46:10Z shibamoto $
package jp.co.daifuku.wms.system.display.systemstate;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.system.schedule.SystemInParameter;
import jp.co.daifuku.wms.system.schedule.SystemStateMaintenanceSCH;
import jp.co.daifuku.wms.system.schedule.SystemStateMaintenanceSCHParams;

/**
 * システム状態メンテナンスの画面処理を行います。
 *
 * @version $Revision: 7457 $, $Date: 2010-03-08 14:46:10 +0900 (月, 08 3 2010) $
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
        // initialize componenets.
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
        if (eventSource.startsWith("btn_DailyProcess_Click"))
        {
            // process call.
            btn_DailyProcess_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("btn_LoadPlanData_Click"))
        {
            // process call.
            btn_LoadPlanData_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("btn_CreateReportData_Click"))
        {
            // process call.
            btn_CreateReportData_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("btn_RetrievalAllocate_Click"))
        {
            // process call.
            btn_RetrievalAllocate_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("btn_AllocationClear_Click"))
        {
            // process call.
            btn_AllocationClear_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(eventSource);
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
        btn_DailyProcess_Click_Process(null);
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
        btn_LoadPlanData_Click_Process(null);
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
        btn_CreateReportData_Click_Process(null);
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
        btn_RetrievalAllocate_Click_Process(null);
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
       btn_AllocationClear_Click_Process(null);
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
        btn_Set_Click_Process(null);
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
                // DFKLOOK ここから 
            	//txt_InDailyProcess.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS));
                //txt_InLoadPlanData.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA));
                //txt_InCreateReportData.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA));
                //txt_InRetrievalAllocate.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE));
                //_grp_HostCommunication.setSelectedValue(outparam.get(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION));
                
            	// 日次処理
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS).equals(true))
            	{            
            		txt_InDailyProcess.setText(DisplayText.getText("LBL-W0376"));
            		btn_DailyProcess.setEnabled(true);
            	}
            	else
            	{
            		txt_InDailyProcess.setText(DisplayText.getText("LBL-W0375"));
            		btn_DailyProcess.setEnabled(false);            		
            	}
            	// 予定データ読み込み
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA).equals(true))
            	{            
            		txt_InLoadPlanData.setText(DisplayText.getText("LBL-W0376"));
            		btn_LoadPlanData.setEnabled(true);
            	}
            	else
            	{
            		txt_InLoadPlanData.setText(DisplayText.getText("LBL-W0375"));
            		btn_LoadPlanData.setEnabled(false);            		
            	}
            	// 報告データ作成
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA).equals(true))
            	{            
            		txt_InCreateReportData.setText(DisplayText.getText("LBL-W0376"));
            		btn_CreateReportData.setEnabled(true);
            	}
            	else
            	{
            		txt_InCreateReportData.setText(DisplayText.getText("LBL-W0375"));
            		btn_CreateReportData.setEnabled(false);            		
            	}
            	// 出庫引当
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE).equals(true))
            	{            
            		txt_InRetrievalAllocate.setText(DisplayText.getText("LBL-W0376"));
            		btn_RetrievalAllocate.setEnabled(true);
            	}
            	else
            	{
            		txt_InRetrievalAllocate.setText(DisplayText.getText("LBL-W0375"));
            		btn_RetrievalAllocate.setEnabled(false);            		
            	}
                // 搬送データクリア
                if (outparam.get(SystemStateMaintenanceSCHParams.IN_ALLOCATION_CLEAR).equals(true))
                {            
                    txt_InAllocationClear.setText(DisplayText.getText("LBL-W0376"));
                    btn_AllocationClear.setEnabled(true);
                }
                else
                {
                    txt_InAllocationClear.setText(DisplayText.getText("LBL-W0375"));
                    btn_AllocationClear.setEnabled(false);                    
                }
            	// ホスト通信
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION).equals(true))
                {
                	rdo_HostCommunication_Effectiv.setChecked(true);
                    setFocus(rdo_HostCommunication_Effectiv);
                }
                else
                {
                	rdo_HostCommunication_Invalidi.setChecked(true);
                    setFocus(rdo_HostCommunication_Invalidi);
                }
                // DFKLOOK ここまで
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
           
            // DFKLOOK ここから 
            //btn_DailyProcess.setEnabled(true);
            //btn_LoadPlanData.setEnabled(true);
            //btn_CreateReportData.setEnabled(true);
            //btn_RetrievalAllocate.setEnabled(true);
            // DFKLOOK ここまで

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
    private void btn_DailyProcess_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_DailyProcess_Click");
            return;
        }
        // DFKLOOK end

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

            // DFKLOOK strat コメント化
            // SCH call.
//            if (eventSource.equals("btn_DailyProcess_Click") && !sch.check(inparam))
//            {
//                if (StringUtil.isBlank(sch.getDispMessage()))
//                {
//                    // show message.
//                    message.setMsgResourceKey(sch.getMessage());
//                    return;
//                }
//                else
//                {
//                    // show confirm message.
//                    this.setConfirm(sch.getDispMessage(), false, true);
//                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_DailyProcess_Click_SCH");
//                    return;
//                }
//            }
            // DFKLOOK end

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
            // DFKLOOK start
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_DAILY_PROCESS));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_DAILY_PROCESS));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR));
            part11List.add(SystemDefine.HOST_ENABLED, "", rdo_HostCommunication_Effectiv.getChecked());
            part11List.add(SystemDefine.HOST_DISABLED, "", rdo_HostCommunication_Invalidi.getChecked());

            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_HOST_COMMUNICATION));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_HOST_COMMUNICATION));
            // DFKLOOK end
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
                // DFKLOOK ここから 
            	//txt_InDailyProcess.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS));
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS).equals(true))
            	{            
            		txt_InDailyProcess.setText(DisplayText.getText("LBL-W0376"));
            		btn_DailyProcess.setEnabled(true);
            	}
            	else
            	{
            		txt_InDailyProcess.setText(DisplayText.getText("LBL-W0375"));
            		btn_DailyProcess.setEnabled(false);            		
            	}
                viewState.setObject(ViewStateKeys.VS_IN_DAILY_PROCESS, outparam.get(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS));
            	// DFKLOOK ここまで
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
    private void btn_LoadPlanData_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_LoadPlanData_Click");
            return;
        }
        // DFKLOOK end

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

            // DFKLOOK start コメント化
            // SCH call.
//            if (eventSource.equals("btn_LoadPlanData_Click") && !sch.check(inparam))
//            {
//                if (StringUtil.isBlank(sch.getDispMessage()))
//                {
//                    // show message.
//                    message.setMsgResourceKey(sch.getMessage());
//                    return;
//                }
//                else
//                {
//                    // show confirm message.
//                    this.setConfirm(sch.getDispMessage(), false, true);
//                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_LoadPlanData_Click_SCH");
//                    return;
//                }
//            }
            // DFKLOOK end

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
            // DFKLOOK start
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_DAILY_PROCESS));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_DAILY_PROCESS));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR));
            part11List.add(SystemDefine.HOST_ENABLED, "", rdo_HostCommunication_Effectiv.getChecked());
            part11List.add(SystemDefine.HOST_DISABLED, "", rdo_HostCommunication_Invalidi.getChecked());

            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_HOST_COMMUNICATION));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_HOST_COMMUNICATION));
            // DFKLOOK end
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
            	// DFKLOOK ここから
            	//txt_InLoadPlanData.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA));
            	// 予定データ読み込み
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA).equals(true))
            	{            
            		txt_InLoadPlanData.setText(DisplayText.getText("LBL-W0376"));
            		btn_LoadPlanData.setEnabled(true);
            	}
            	else
            	{
            		txt_InLoadPlanData.setText(DisplayText.getText("LBL-W0375"));
            		btn_LoadPlanData.setEnabled(false);            		
            	}
                viewState.setObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA, outparam.get(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA));
            	// DFKLOOK ここまで
            
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
    private void btn_CreateReportData_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_CreateReportData_Click");
            return;
        }
        // DFKLOOK end

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

            // DFKLOOK start コメント化
            // SCH call.
//            if (eventSource.equals("btn_CreateReportData_Click") && !sch.check(inparam))
//            {
//                if (StringUtil.isBlank(sch.getDispMessage()))
//                {
//                    // show message.
//                    message.setMsgResourceKey(sch.getMessage());
//                    return;
//                }
//                else
//                {
//                    // show confirm message.
//                    this.setConfirm(sch.getDispMessage(), false, true);
//                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_CreateReportData_Click_SCH");
//                    return;
//                }
//            }
            // DFKLOOK end

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
            // DFKLOOK start
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_DAILY_PROCESS));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_DAILY_PROCESS));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR));
            part11List.add(SystemDefine.HOST_ENABLED, "", rdo_HostCommunication_Effectiv.getChecked());
            part11List.add(SystemDefine.HOST_DISABLED, "", rdo_HostCommunication_Invalidi.getChecked());

            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_HOST_COMMUNICATION));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_HOST_COMMUNICATION));
            // DFKLOOK end
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
            	// DFKLOOK ここから
            	//txt_InCreateReportData.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA));
            	// 報告データ作成
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA).equals(true))
            	{            
            		txt_InCreateReportData.setText(DisplayText.getText("LBL-W0376"));
            		btn_CreateReportData.setEnabled(true);
            	}
            	else
            	{
            		txt_InCreateReportData.setText(DisplayText.getText("LBL-W0375"));
            		btn_CreateReportData.setEnabled(false);            		
            	}
                viewState.setObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA, outparam.get(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA));
            	// DFKLOOK ここまで
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
    private void btn_RetrievalAllocate_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_RetrievalAllocate_Click");
            return;
        }
        // DFKLOOK end

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

            // DFKLOOK start コメント化
            // SCH call.
//            if (eventSource.equals("btn_RetrievalAllocate_Click") && !sch.check(inparam))
//            {
//                if (StringUtil.isBlank(sch.getDispMessage()))
//                {
//                    // show message.
//                    message.setMsgResourceKey(sch.getMessage());
//                    return;
//                }
//                else
//                {
//                    // show confirm message.
//                    this.setConfirm(sch.getDispMessage(), false, true);
//                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_RetrievalAllocate_Click_SCH");
//                    return;
//                }
//            }
            // DFKLOOK end

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
            // DFKLOOK start
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_DAILY_PROCESS));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_DAILY_PROCESS));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR));
            part11List.add(SystemDefine.HOST_ENABLED, "", rdo_HostCommunication_Effectiv.getChecked());
            part11List.add(SystemDefine.HOST_DISABLED, "", rdo_HostCommunication_Invalidi.getChecked());

            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_HOST_COMMUNICATION));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_HOST_COMMUNICATION));
            // DFKLOOK end
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
            	// DFKLOOK ここから
            	//txt_InRetrievalAllocate.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE));
            	// 出庫引当
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE).equals(true))
            	{            
            		txt_InRetrievalAllocate.setText(DisplayText.getText("LBL-W0376"));
            		btn_RetrievalAllocate.setEnabled(true);
            	}
            	else
            	{
            		txt_InRetrievalAllocate.setText(DisplayText.getText("LBL-W0375"));
            		btn_RetrievalAllocate.setEnabled(false);            		
            	}
                viewState.setObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE, outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE));
            	// DFKLOOK ここまで
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
   private void btn_AllocationClear_Click_Process(String eventSource)
           throws Exception
   {
       // DFKLOOK start
       if(StringUtil.isBlank(eventSource))
       {
       	// 設定しますか？
           this.setConfirm("MSG-W9000", false, true);
   		viewState.setString(_KEY_CONFIRMSOURCE, "btn_AllocationClear_Click");
           return;
       }
       // DFKLOOK end

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
           inparam.set(SystemStateMaintenanceSCHParams.IN_ALLOCATION_CLEAR, viewState.getObject(ViewStateKeys.IN_ALLOCATION_CLEAR));

           // DFKLOOK start コメント化
           // SCH call.
//           if (eventSource.equals("btn_AllocationClear_Click") && !sch.check(inparam))
//           {
//               if (StringUtil.isBlank(sch.getDispMessage()))
//               {
//                   // show message.
//                   message.setMsgResourceKey(sch.getMessage());
//                   return;
//               }
//               else
//               {
//                   // show confirm message.
//                   this.setConfirm(sch.getDispMessage(), false, true);
//                   viewState.setString(_KEY_CONFIRMSOURCE, "btn_AllocationClear_Click_SCH");
//                   return;
//               }
//           }
           // DFKLOOK end

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
            // DFKLOOK start
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_DAILY_PROCESS));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_DAILY_PROCESS));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR));
            part11List.add(SystemDefine.HOST_ENABLED, "", rdo_HostCommunication_Effectiv.getChecked());
            part11List.add(SystemDefine.HOST_DISABLED, "", rdo_HostCommunication_Invalidi.getChecked());

            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_HOST_COMMUNICATION));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_HOST_COMMUNICATION));
            // DFKLOOK end
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

           // commit.
           conn.commit();
           message.setMsgResourceKey(sch.getMessage());

           // set input parameters.
           inparam = new SystemStateMaintenanceSCHParams();
           inparam.set(SystemStateMaintenanceSCHParams.IN_ALLOCATION_CLEAR, viewState.getObject(ViewStateKeys.IN_ALLOCATION_CLEAR));

           // SCH call.
           List<Params> outparams = sch.query(inparam);

           // output display.
           for (Params outparam : outparams)
           {
               
            // DFKLOOK ここから
            //txt_InAllocationClear.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_ALLOCATION_CLEAR));
            // 搬送データクリア
            if (outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE).equals(true))
            {            
                txt_InAllocationClear.setText(DisplayText.getText("LBL-W0376"));
                btn_AllocationClear.setEnabled(true);
            }
            else
            {
                txt_InAllocationClear.setText(DisplayText.getText("LBL-W0375"));
                btn_AllocationClear.setEnabled(false);
            }
            viewState.setObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR, outparam.get(SystemStateMaintenanceSCHParams.IN_ALLOCATION_CLEAR));
            // DFKLOOK ここまで
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
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;
        }
        // DFKLOOK end

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
      
            // DFKLOOK ここから 
            //inparam.set(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION, viewState.getObject(ViewStateKeys.IN_HOST_COMMUNICATION));
            if (rdo_HostCommunication_Effectiv.getChecked())
            {
                inparam.set(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION, SystemDefine.HOST_ENABLED);
            }
            else
            {
                inparam.set(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION, SystemDefine.HOST_DISABLED);
            }
            // DFKLOOK ここまで

            // DFKLOOK start コメント化
            // SCH call.
//            if (eventSource.equals("btn_Set_Click") && !sch.check(inparam))
//            {
//                if (StringUtil.isBlank(sch.getDispMessage()))
//                {
//                    // show message.
//                    message.setMsgResourceKey(sch.getMessage());
//                    return;
//                }
//                else
//                {
//                    // show confirm message.
//                    this.setConfirm(sch.getDispMessage(), false, true);
//                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click_SCH");
//                    return;
//                }
//            }
            // DFKLOOK end

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
            // DFKLOOK start
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_DAILY_PROCESS));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_DAILY_PROCESS));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_LOAD_PLAN_DATA));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_CREATE_REPORT_DATA));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_RETRIEVAL_ALLOCATE));
            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_IN_ALLOCATION_CLEAR));
            part11List.add(SystemDefine.HOST_ENABLED, "", rdo_HostCommunication_Effectiv.getChecked());
            part11List.add(SystemDefine.HOST_DISABLED, "", rdo_HostCommunication_Invalidi.getChecked());

            part11List.add("1", "", (boolean)viewState.getBooleanObject(ViewStateKeys.VS_HOST_COMMUNICATION));
            part11List.add("0", "", !(boolean)viewState.getBooleanObject(ViewStateKeys.VS_HOST_COMMUNICATION));
            // DFKLOOK end
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
            	// DFKLOOK ここから
            	//_grp_HostCommunication.setSelectedValue(outparam.get(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION));
            	// ホスト通信
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION).equals(true))
                {
                	rdo_HostCommunication_Effectiv.setChecked(true);
                    setFocus(rdo_HostCommunication_Effectiv);
                }
                else
                {
                	rdo_HostCommunication_Invalidi.setChecked(true);
                    setFocus(rdo_HostCommunication_Invalidi);
                }
                viewState.setObject(ViewStateKeys.VS_HOST_COMMUNICATION, outparam.get(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION));
                // DFKLOOK ここまで
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
            	//DFKLOOK ここから
            	//txt_InDailyProcess.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS));
                //txt_InLoadPlanData.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA));
                //txt_InCreateReportData.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA));
                //txt_InRetrievalAllocate.setValue(outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE));
                //_grp_HostCommunication.setSelectedValue(outparam.get(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION));
            	
            	// 日次処理
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_DAILY_PROCESS).equals(true))
            	{            
            		txt_InDailyProcess.setText(DisplayText.getText("LBL-W0376"));
            		btn_DailyProcess.setEnabled(true);
            	}
            	else
            	{
            		txt_InDailyProcess.setText(DisplayText.getText("LBL-W0375"));
            		btn_DailyProcess.setEnabled(false);            		
            	}
            	// 予定データ読み込み
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_LOAD_PLAN_DATA).equals(true))
            	{            
            		txt_InLoadPlanData.setText(DisplayText.getText("LBL-W0376"));
            		btn_LoadPlanData.setEnabled(true);
            	}
            	else
            	{
            		txt_InLoadPlanData.setText(DisplayText.getText("LBL-W0375"));
            		btn_LoadPlanData.setEnabled(false);            		
            	}
            	// 報告データ作成
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_CREATE_REPORT_DATA).equals(true))
            	{            
            		txt_InCreateReportData.setText(DisplayText.getText("LBL-W0376"));
            		btn_CreateReportData.setEnabled(true);
            	}
            	else
            	{
            		txt_InCreateReportData.setText(DisplayText.getText("LBL-W0375"));
            		btn_CreateReportData.setEnabled(false);            		
            	}
            	// 出庫引当
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_RETRIEVAL_ALLOCATE).equals(true))
            	{            
            		txt_InRetrievalAllocate.setText(DisplayText.getText("LBL-W0376"));
            		btn_RetrievalAllocate.setEnabled(true);
            	}
            	else
            	{
            		txt_InRetrievalAllocate.setText(DisplayText.getText("LBL-W0375"));
            		btn_RetrievalAllocate.setEnabled(false);            		
            	}
                // 搬送データクリア
                if (outparam.get(SystemStateMaintenanceSCHParams.IN_ALLOCATION_CLEAR).equals(true))
                {            
                    txt_InAllocationClear.setText(DisplayText.getText("LBL-W0376"));
                    btn_AllocationClear.setEnabled(true);
                }
                else
                {
                    txt_InAllocationClear.setText(DisplayText.getText("LBL-W0375"));
                    btn_AllocationClear.setEnabled(false);                    
                }
            	// ホスト通信
            	if (outparam.get(SystemStateMaintenanceSCHParams.IN_HOST_COMMUNICATION).equals(true))
                {
                	rdo_HostCommunication_Effectiv.setChecked(true);
                    setFocus(rdo_HostCommunication_Effectiv);
                }
                else
                {
                	rdo_HostCommunication_Invalidi.setChecked(true);
                    setFocus(rdo_HostCommunication_Invalidi);
                }
                // DFKLOOK ここまで
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
            
            // DFKLOOK ここから
            //btn_DailyProcess.setEnabled(true);
            //btn_LoadPlanData.setEnabled(true);
            //btn_CreateReportData.setEnabled(true);
            //btn_RetrievalAllocate.setEnabled(true);
            // DFKLOOK ここまで

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
