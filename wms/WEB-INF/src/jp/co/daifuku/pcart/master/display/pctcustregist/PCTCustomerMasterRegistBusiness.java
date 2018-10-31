// $Id: PCTCustomerMasterRegistBusiness.java 7162 2010-02-19 09:32:59Z shibamoto $
package jp.co.daifuku.pcart.master.display.pctcustregist;

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
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.pcart.base.util.PCTLogWriter;
import jp.co.daifuku.pcart.master.listbox.registcust.LstRegistCustomerParams;
import jp.co.daifuku.pcart.master.schedule.PCTCustomerMasterRegistSCH;
import jp.co.daifuku.pcart.master.schedule.PCTCustomerMasterRegistSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * 出荷先マスタ登録の画面処理を行います。
 *
 * @version $Revision: 7162 $, $Date: 2010-02-19 18:32:59 +0900 (金, 19 2 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTCustomerMasterRegistBusiness
        extends PCTCustomerMasterRegist
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK:start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK:end

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_WorkPriority */
    private DefaultPullDownModel _pdm_pul_WorkPriority;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTCustomerMasterRegistBusiness()
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
        if (eventSource.equals("btn_Search_Click"))
        {
            // process call.
            btn_Search_Click_DlgBack(dialogParams);
        }
    }

    // DFKLOOK start
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
        if (eventSource.startsWith("btn_Submit_Click"))
        {
        	btn_Submit_Click_Process(eventSource);
        }
    }
    // DFKLOOK end
    
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
        btn_Submit_Click_Process(null);
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

    // DFKLOOK:ここから修正
    /**
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param   conn            データベースコネクション
     * @param   operationKind  操作区分
     * @throws Exception 全ての例外を報告します。
     */
    protected void log_write(Connection conn, int operationKind)
            throws Exception
    {
        // オペレーションログ出力
        List<String> cusLog = new ArrayList<String>();

        // 荷主コード
        cusLog.add(txt_ConsignorCode.getText());
        // 出荷先コード
        cusLog.add(txt_CustomerCode.getText());
        // 出荷先名称
        cusLog.add(txt_CustomerName.getText());
        // 作業優先度
        cusLog.add(pul_WorkPriority.getSelectedValue());

        // ログ出力
        PCTLogWriter opeLogWriter = new PCTLogWriter(conn);
        opeLogWriter.createOperationLog((DfkUserInfo)getUserInfo(), operationKind, cusLog);
    }

    // DFKLOOK:ここまで修正


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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_WorkPriority.
        _pdm_pul_WorkPriority = new DefaultPullDownModel(pul_WorkPriority, locale, ui);

    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // load pul_WorkPriority.
            _pdm_pul_WorkPriority.init(conn);

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
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_ConsignorCode);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_ConsignorCode.setValue(null);
        txt_CustomerCode.setValue(null);
        txt_CustomerName.setValue(null);

        // DFKLOOK:ここから修正
        _pdm_pul_WorkPriority.setSelectedValue(SystemDefine.JOB_PRIORITY_INSIDE);

        // 初期表示時、荷主検索を行う
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerMasterRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerMasterRegistSCH(conn, this.getClass(), locale, ui);

            // SCH call.
            Params p = sch.initFind(new ScheduleParams());

            // output display.
            if (p != null)
            {
                txt_ConsignorCode.setValue(p.get(PCTCustomerMasterRegistSCHParams.CONSIGNOR_CODE));
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
        }
        finally
        {
            DBUtil.close(conn);
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Search_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstRegistCustomerParams inparam = new LstRegistCustomerParams();
        inparam.set(LstRegistCustomerParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstRegistCustomerParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Search_Click");
        redirect("/pcart/master/listbox/registcust/LstRegistCustomer.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_Search_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstRegistCustomerParams outparam = new LstRegistCustomerParams(dialogParams);
        txt_CustomerCode.setValue(outparam.get(LstRegistCustomerParams.CUSTOMER_CODE));
        txt_CustomerName.setValue(outparam.get(LstRegistCustomerParams.CUSTOMER_NAME));

        // set focus.
        setFocus(txt_CustomerCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Submit_Click_Process(String eventSource)
            throws Exception
    {

        // DFKLOOK:ここから修正
        txt_ConsignorCode.validate(this, true);
        txt_CustomerCode.validate(this, true);

        // DFKLOOK start
        if (StringUtil.isBlank(eventSource))
        {         
            // 登録しますか?
            this.setConfirm("MSG-W0012", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Submit_Click");
            return;         
        }
        // DFKLOOK end
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerMasterRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerMasterRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerMasterRegistSCHParams inparam = new PCTCustomerMasterRegistSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(PCTCustomerMasterRegistSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTCustomerMasterRegistSCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(PCTCustomerMasterRegistSCHParams.CUSTOMER_NAME, txt_CustomerName.getValue());
            inparam.set(PCTCustomerMasterRegistSCHParams.JOB_PRIORITY, _pdm_pul_WorkPriority.getSelectedValue());

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // DFKLOOK:ここから修正
            // オペレーションログ出力
            log_write(conn, EmConstants.OPELOG_CLASS_REGIST);
            // DFKLOOK:ここまで修正

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
        }
        finally
        {
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    // DFKLOOK:ここから修正

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_ConsignorCode.setValue(null);
        txt_CustomerCode.setValue(null);
        txt_CustomerName.setValue(null);

        // DFKLOOK:ここから修正
        _pdm_pul_WorkPriority.setSelectedValue(SystemDefine.JOB_PRIORITY_INSIDE);
        // DFKLOOK:ここまで修正

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
