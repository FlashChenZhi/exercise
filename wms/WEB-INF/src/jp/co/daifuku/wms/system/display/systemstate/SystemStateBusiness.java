// $Id: SystemStateBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.system.schedule.SystemStateSCH;
import jp.co.daifuku.wms.system.schedule.SystemStateSCHParams;

/**
 * システム状態設定の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class SystemStateBusiness
        extends SystemState
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_SystemStatus(LST_PRODUCT) */
    private static final ListCellKey KEY_LST_PRODUCT = new ListCellKey("LST_PRODUCT", new StringCellColumn(), true, false);

    /** lst_SystemStatus(LST_VERSION) */
    private static final ListCellKey KEY_LST_VERSION = new ListCellKey("LST_VERSION", new StringCellColumn(), true, false);

    /** lst_SystemStatus keys */
    private static final ListCellKey[] LST_SYSTEMSTATUS_KEYS = {
        KEY_LST_PRODUCT,
        KEY_LST_VERSION,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_SystemStatus */
    private ListCellModel _lcm_lst_SystemStatus;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public SystemStateBusiness()
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
    	// DFKLOOK ここから
    	// process call.
    	page_Load_Process();
    	// DFKLOOK ここまで
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
        btn_Set_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ReDisplayFunc_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplayFunc_Click_Process();
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

        // initialize lst_SystemStatus.
        _lcm_lst_SystemStatus = new ListCellModel(lst_SystemStatus, LST_SYSTEMSTATUS_KEYS, locale);
        _lcm_lst_SystemStatus.setToolTipVisible(KEY_LST_PRODUCT, false);
        _lcm_lst_SystemStatus.setToolTipVisible(KEY_LST_VERSION, false);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_SystemStatus_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
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
        SystemStateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SystemStateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SystemStateSCHParams inparam = new SystemStateSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());
            
            // DFKLOOK ここから
            Params[] dispParams = new Params[outparams.size()];
            outparams.toArray(dispParams);
            txt_InDailyProcess.setValue(dispParams[0].get(SystemStateSCHParams.IN_DAILY_PROCESS));
            txt_InLoadPlanData.setValue(dispParams[0].get(SystemStateSCHParams.IN_LOAD_PLAN_DATA));
            txt_InCreateReportData.setValue(dispParams[0].get(SystemStateSCHParams.IN_CREATE_REPORT_DATA));
            txt_InRetrievalAllocate.setValue(dispParams[0].get(SystemStateSCHParams.IN_RETRIEVAL_ALLOCATE));
            txt_InHostCommunication.setValue(dispParams[0].get(SystemStateSCHParams.IN_HOST_COMMUNICATION));
            txt_InPlanDataHoldDays.setValue(dispParams[0].get(SystemStateSCHParams.IN_PLAN_DATA_HOLD_DAYS));
            txt_InResultDataHoldDays.setValue(dispParams[0].get(SystemStateSCHParams.IN_RESULT_DATA_HOLD_DAYS));
            txt_InAllocationClear.setValue(dispParams[0].get(SystemStateSCHParams.IN_ALLOCATION_CLEAR));
            ListCellLine line = _lcm_lst_SystemStatus.getNewLine();
            //DFKLOOK:ここまで
            
            // output display.
            _lcm_lst_SystemStatus.clear();
            
            // DFKLOOK ここから
            for (int i = 1;i < dispParams.length ; i++)
            {
            	line.setValue(KEY_LST_PRODUCT, dispParams[i].get(SystemStateSCHParams.PRODUCT));
                line.setValue(KEY_LST_VERSION, dispParams[i].get(SystemStateSCHParams.VERSION));
                lst_SystemStatus_SetLineToolTip(line);
                _lcm_lst_SystemStatus.add(line);
            }
            // DFKLOOK ここまで

            // clear.
            txt_InDailyProcess.setReadOnly(true);
            txt_InLoadPlanData.setReadOnly(true);
            txt_InCreateReportData.setReadOnly(true);
            txt_InRetrievalAllocate.setReadOnly(true);
            txt_InHostCommunication.setReadOnly(true);

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
   private void btn_Set_Click_Process()
           throws Exception
   {
       	// set ViewState parameters.
       	viewState.setObject(ViewStateKeys.IN_DAILY_PROCESS, txt_InDailyProcess.getValue());
       	viewState.setObject(ViewStateKeys.IN_LOAD_PLAN_DATA, txt_InLoadPlanData.getValue());
       	viewState.setObject(ViewStateKeys.IN_CREATE_REPORT_DATA, txt_InCreateReportData.getValue());
       	viewState.setObject(ViewStateKeys.IN_RETRIEVAL_ALLOCATE, txt_InRetrievalAllocate.getValue());
       	viewState.setObject(ViewStateKeys.IN_HOST_COMMUNICATION, txt_InHostCommunication.getValue());
       	viewState.setObject(ViewStateKeys.IN_PLAN_DATA_HOLD_DAYS, txt_InPlanDataHoldDays.getValue());
       	viewState.setObject(ViewStateKeys.IN_RESULT_DATA_HOLD_DAYS, txt_InResultDataHoldDays.getValue());
        viewState.setObject(ViewStateKeys.IN_ALLOCATION_CLEAR, txt_InAllocationClear.getValue());
        
       	// forward.
       	forward("/system/systemstate/SystemStateMaintenance.do");
   }

    /**
     *
     * @throws Exception
     */
    private void btn_ReDisplayFunc_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SystemStateSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SystemStateSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SystemStateSCHParams inparam = new SystemStateSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから
            // output display.
            Params[] dispParams = new Params[outparams.size()];
            outparams.toArray(dispParams);
            txt_InDailyProcess.setValue(dispParams[0].get(SystemStateSCHParams.IN_DAILY_PROCESS));
            txt_InLoadPlanData.setValue(dispParams[0].get(SystemStateSCHParams.IN_LOAD_PLAN_DATA));
            txt_InCreateReportData.setValue(dispParams[0].get(SystemStateSCHParams.IN_CREATE_REPORT_DATA));
            txt_InRetrievalAllocate.setValue(dispParams[0].get(SystemStateSCHParams.IN_RETRIEVAL_ALLOCATE));
            txt_InHostCommunication.setValue(dispParams[0].get(SystemStateSCHParams.IN_HOST_COMMUNICATION));
            txt_InPlanDataHoldDays.setValue(dispParams[0].get(SystemStateSCHParams.IN_PLAN_DATA_HOLD_DAYS));
            txt_InResultDataHoldDays.setValue(dispParams[0].get(SystemStateSCHParams.IN_RESULT_DATA_HOLD_DAYS));
            txt_InAllocationClear.setValue(dispParams[0].get(SystemStateSCHParams.IN_ALLOCATION_CLEAR));
            ListCellLine line = _lcm_lst_SystemStatus.getNewLine();
            _lcm_lst_SystemStatus.clear();
            for (int i = 1;i < dispParams.length ; i++)
            {
            	line.setValue(KEY_LST_PRODUCT, dispParams[i].get(SystemStateSCHParams.PRODUCT));
                line.setValue(KEY_LST_VERSION, dispParams[i].get(SystemStateSCHParams.VERSION));
                lst_SystemStatus_SetLineToolTip(line);
                _lcm_lst_SystemStatus.add(line);
            }
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
