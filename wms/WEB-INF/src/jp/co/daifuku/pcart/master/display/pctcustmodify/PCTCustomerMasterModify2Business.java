// $Id: PCTCustomerMasterModify2Business.java 7162 2010-02-19 09:32:59Z shibamoto $
package jp.co.daifuku.pcart.master.display.pctcustmodify;

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
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.pcart.base.util.PCTLogWriter;
import jp.co.daifuku.pcart.master.schedule.PCTCustomerMasterModifySCH;
import jp.co.daifuku.pcart.master.schedule.PCTCustomerMasterModifySCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.pcart.master.schedule.PCTMasterInParameter;

/**
 * 出荷先マスタ修正削除の画面処理を行います。
 *
 * @version $Revision: 7162 $, $Date: 2010-02-19 18:32:59 +0900 (金, 19 2 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTCustomerMasterModify2Business
        extends PCTCustomerMasterModify2
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
    /** PullDownModel pul_WorkPriority */
    private DefaultPullDownModel _pdm_pul_WorkPriority;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTCustomerMasterModify2Business()
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
        if (eventSource.startsWith("btn_Modify_Click"))
        {
            // process call.
            btn_Modify_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("btn_Delete_Click"))
        {
            // process call.
            btn_Delete_Click_Process(eventSource);
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
        btn_Modify_Click_Process(null);
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
        btn_Delete_Click_Process(null);
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
     /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_MenuNoFunc_Click(ActionEvent e)
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
        cusLog.add(lbl_JavaSetConsignorCode.getText());
        // 出荷先コード
        cusLog.add(lbl_JavaSetCustomerCode.getText());
        // 出荷先名称
        cusLog.add(txt_CustomerName.getText());
        // 作業優先度
        cusLog.add((pul_WorkPriority.getSelectedValue()));
        if (operationKind == EmConstants.OPELOG_CLASS_MODIFY)
        {
            cusLog.add(String.valueOf(viewState.getObject(ViewStateKeys.VS_JOB_PRIORITY)));
            cusLog.add(String.valueOf(viewState.getObject(ViewStateKeys.VS_CUSTOMER_NAME)));
        }

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
        PCTCustomerMasterModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerMasterModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerMasterModifySCHParams inparam = new PCTCustomerMasterModifySCHParams();
            inparam.set(PCTCustomerMasterModifySCHParams.CONSIGNOR_CODE,
                    viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTCustomerMasterModifySCHParams.CUSTOMER_CODE,
                    viewState.getObject(ViewStateKeys.VS_CUSTOMER_CODE));
            inparam.set(PCTCustomerMasterModifySCHParams.LAST_UPDATE_DATE,
                    viewState.getObject(ViewStateKeys.VS_LAST_UPDATE_DATE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                // DFKLOCK S
                lbl_JavaSetConsignorCode.setValue(outparam.get(PCTCustomerMasterModifySCHParams.CONSIGNOR_CODE));
                lbl_JavaSetCustomerCode.setValue(outparam.get(PCTCustomerMasterModifySCHParams.CUSTOMER_CODE));
                // DFKLOCK E
                txt_CustomerName.setValue(outparam.get(PCTCustomerMasterModifySCHParams.CUSTOMER_NAME));
                _pdm_pul_WorkPriority.setSelectedValue(outparam.get(PCTCustomerMasterModifySCHParams.JOB_PRIORITY));
                viewState.setObject(ViewStateKeys.VS_LAST_UPDATE_DATE,
                        outparam.get(PCTCustomerMasterModifySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_LAST_USED_DATE,
                        outparam.get(PCTCustomerMasterModifySCHParams.LAST_USED_DATE));
                viewState.setObject(ViewStateKeys.VS_JOB_PRIORITY,
                        outparam.get(PCTCustomerMasterModifySCHParams.JOB_PRIORITY));
                viewState.setObject(ViewStateKeys.VS_CUSTOMER_NAME,
                        outparam.get(PCTCustomerMasterModifySCHParams.CUSTOMER_NAME));
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
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Back_Click_Process()
            throws Exception
    {
        // set ViewState parameters.
        viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, lbl_JavaSetConsignorCode.getValue());
        viewState.setObject(ViewStateKeys.VS_CUSTOMER_CODE, lbl_JavaSetCustomerCode.getValue());

        // forward.
        forward("/pcart/master/pctcustmodify/PCTCustomerMasterModify.do");
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Modify_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK start
        if (StringUtil.isBlank(eventSource))
        {         
            // 修正登録しますか?
            this.setConfirm("MSG-W0013", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Modify_Click");
            return;         
        }
        // DFKLOOK end
        
        // DFKLOOK:ここから修正
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerMasterModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerMasterModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerMasterModifySCHParams inparam = new PCTCustomerMasterModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(PCTCustomerMasterModifySCHParams.CUSTOMER_NAME, txt_CustomerName.getValue());
            inparam.set(PCTCustomerMasterModifySCHParams.JOB_PRIORITY, _pdm_pul_WorkPriority.getSelectedValue());
            inparam.set(PCTCustomerMasterModifySCHParams.CONSIGNOR_CODE,
                    viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTCustomerMasterModifySCHParams.CUSTOMER_CODE,
                    viewState.getObject(ViewStateKeys.VS_CUSTOMER_CODE));
            inparam.set(PCTCustomerMasterModifySCHParams.LAST_UPDATE_DATE,
                    viewState.getObject(ViewStateKeys.VS_LAST_UPDATE_DATE));
            inparam.set(PCTCustomerMasterModifySCHParams.PROCESS_FLAG, PCTMasterInParameter.PROCESS_FLAG_MODIFY);
            inparam.set(PCTCustomerMasterModifySCHParams.LAST_USED_DATE,
                    viewState.getObject(ViewStateKeys.VS_LAST_USED_DATE));

            // DFKLOOK:ここまで修正

            // SCH call.
            if (eventSource.equals("btn_Modify_Click") && !sch.check(inparam))
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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Modify_Click_SCH");
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

            // DFKLOOK:ここから修正      
            // オペレーションログ出力
            log_write(conn, EmConstants.OPELOG_CLASS_MODIFY);
            // DFKLOOK:ここまで修正

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // set input parameters.
            inparam = new PCTCustomerMasterModifySCHParams();
            inparam.set(PCTCustomerMasterModifySCHParams.CONSIGNOR_CODE,
                    viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTCustomerMasterModifySCHParams.CUSTOMER_CODE,
                    viewState.getObject(ViewStateKeys.VS_CUSTOMER_CODE));


            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_CustomerName.setValue(outparam.get(PCTCustomerMasterModifySCHParams.CUSTOMER_NAME));
                _pdm_pul_WorkPriority.setSelectedValue(outparam.get(PCTCustomerMasterModifySCHParams.JOB_PRIORITY));
                viewState.setObject(ViewStateKeys.VS_LAST_UPDATE_DATE,
                        outparam.get(PCTCustomerMasterModifySCHParams.LAST_UPDATE_DATE));
                viewState.setObject(ViewStateKeys.VS_LAST_USED_DATE,
                        outparam.get(PCTCustomerMasterModifySCHParams.LAST_USED_DATE));
                viewState.setObject(ViewStateKeys.VS_JOB_PRIORITY,
                        outparam.get(PCTCustomerMasterModifySCHParams.JOB_PRIORITY));
                viewState.setObject(ViewStateKeys.VS_CUSTOMER_NAME,
                        outparam.get(PCTCustomerMasterModifySCHParams.CUSTOMER_NAME));
            }
            // DFKLOOK:ここまで修正

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

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Delete_Click_Process(String eventSource)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerMasterModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerMasterModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerMasterModifySCHParams inparam = new PCTCustomerMasterModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.set(PCTCustomerMasterModifySCHParams.CUSTOMER_NAME, txt_CustomerName.getValue());
            inparam.set(PCTCustomerMasterModifySCHParams.JOB_PRIORITY, _pdm_pul_WorkPriority.getSelectedValue());
            inparam.set(PCTCustomerMasterModifySCHParams.CONSIGNOR_CODE,
                    viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTCustomerMasterModifySCHParams.CUSTOMER_CODE,
                    viewState.getObject(ViewStateKeys.VS_CUSTOMER_CODE));
            inparam.set(PCTCustomerMasterModifySCHParams.LAST_UPDATE_DATE,
                    viewState.getObject(ViewStateKeys.VS_LAST_UPDATE_DATE));
            inparam.set(PCTCustomerMasterModifySCHParams.PROCESS_FLAG, PCTMasterInParameter.PROCESS_FLAG_DELETE);
            inparam.set(PCTCustomerMasterModifySCHParams.LAST_USED_DATE,
                    viewState.getObject(ViewStateKeys.VS_LAST_USED_DATE));

            // SCH call.
            if (StringUtil.isBlank(eventSource) && !sch.check(inparam))
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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Delete_Click_SCH");
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

            // DFKLOOK:ここから修正       
            // オペレーションログ出力
            log_write(conn, EmConstants.OPELOG_CLASS_DELETE);
            // DFKLOOK:ここまで修正

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            txt_CustomerName.setValue(null);
            txt_CustomerName.setReadOnly(true);
            _pdm_pul_WorkPriority.setSelectedValue(null);
            pul_WorkPriority.setEnabled(false);
            btn_Modify.setEnabled(false);
            btn_Delete.setEnabled(false);
            btn_Clear.setEnabled(false);

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

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_CustomerName.setValue(null);
        _pdm_pul_WorkPriority.setSelectedValue(SystemDefine.JOB_PRIORITY_INSIDE);

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
