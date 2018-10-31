// $Id: WorkMntAllBusiness.java 7162 2010-02-19 09:32:59Z shibamoto $
package jp.co.daifuku.pcart.retrieval.display.workmntall;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
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
import jp.co.daifuku.pcart.retrieval.listbox.customer.LstCustomerParams;
import jp.co.daifuku.pcart.retrieval.listbox.regularCustomer.LstRegularCustomerParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.pcart.retrieval.schedule.WorkMntAllSCH;
import jp.co.daifuku.pcart.retrieval.schedule.WorkMntAllSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * 作業メンテナンス一括の画面処理を行います。
 *
 * @version $Revision: 7162 $, $Date:: 2010-02-19 18:32:59 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class WorkMntAllBusiness
        extends WorkMntAll
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
    /** RadioButtonGroupModel Group */
    private RadioButtonGroup _grp_Group;

    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public WorkMntAllBusiness()
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
            // DFKLOOK ここから追加
            // フォーカスセット
            setFocus(_grp_Group.getSelected());
            // DFKLOOK ここまで追加
            return;
        }

        // choose process.
        if (eventSource.equals("btn_SearchRegularCustomer_Click"))
        {
            // process call.
            btn_SearchRegularCustomer_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchCustomor_Click"))
        {
            // process call.
            btn_SearchCustomor_Click_DlgBack(dialogParams);
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
    public void rdo_Delete_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから追加
        this.setAlert(DisplayResource.format("MSG-P0012"));

        setFocus(rdo_Delete);
        // DFKLOOK ここまで追加
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchRegularCustomer_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchRegularCustomer_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchCustomor_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchCustomor_Click_Process();
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
        List<String> itemLog = new ArrayList<String>();

        // 荷主コード
        itemLog.add(txt_ConsignorCode.getText());
        // 予定日
        itemLog.add(txt_PlanDay.getText());
        // バッチSeqNo.
        itemLog.add(txt_BatchSeqNo.getText());
        // エリアNo.
        itemLog.add(pul_Area.getSelectedValue());
        // 得意先コード
        itemLog.add(txt_RegularCustomerCode.getText());
        // 出荷先コード
        itemLog.add(txt_CustomerCode.getText());


        if (rdo_AllCompletion.getChecked())
        {
            // 処理
            itemLog.add(PCTRetrievalInParameter.PROCESSING_DIVISION_COMPLETION);
        }
        else if (rdo_AllStockout.getChecked())
        {
            // 処理
            itemLog.add(PCTRetrievalInParameter.PROCESSING_DIVISION_STOCKOUT);
        }
        else if (rdo_Delete.getChecked())
        {
            // 処理
            itemLog.add(PCTRetrievalInParameter.PROCESSING_DIVISION_DELETE);
        }


        // ログ出力
        PCTLogWriter opeLogWriter = new PCTLogWriter(conn);
        opeLogWriter.createOperationLog((DfkUserInfo)getUserInfo(), operationKind, itemLog);
    }

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

        // initialize Group.
        _grp_Group = new RadioButtonGroup(new RadioButton[] {
                rdo_AllCompletion,
                rdo_AllStockout,
                rdo_Delete
        }, locale);

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

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

            // load pul_Area.
            _pdm_pul_Area.init(conn, AreaType.FLOOR, StationType.ALL, "", true);

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
        setFocus(rdo_AllCompletion);

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
        WorkMntAllSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkMntAllSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkMntAllSCHParams inparam = new WorkMntAllSCHParams();

            // DFKLOOK ここから修正
            // clear.
            rdo_AllCompletion.setChecked(true);
            txt_ConsignorCode.setValue(null);
            txt_PlanDay.setValue(null);
            txt_BatchNo.setValue(null);
            txt_BatchSeqNo.setValue(null);
            _pdm_pul_Area.setSelectedValue(null);
            txt_RegularCustomerCode.setValue(null);
            txt_CustomerCode.setValue(null);
            txt_OrderNo.setValue(null);

            // SCH call.
            Params outparam = sch.initFind(inparam);
            if (outparam != null)
            {
                txt_ConsignorCode.setValue(outparam.get(WorkMntAllSCHParams.CONSIGNOR_CODE));
            }
            // DFKLOOK ここから修正
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
    private void btn_SearchRegularCustomer_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstRegularCustomerParams inparam = new LstRegularCustomerParams();
        inparam.set(LstRegularCustomerParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstRegularCustomerParams.PLAN_DAY, txt_PlanDay.getValue());
        inparam.set(LstRegularCustomerParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstRegularCustomerParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstRegularCustomerParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
        inparam.set(LstRegularCustomerParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstRegularCustomerParams.SEARCHTABLE, InParameter.SEARCH_TABLE_PLAN);

        // DFKLOOK ここから修正
        // 処理内容が削除以外の場合
        if (!rdo_Delete.getChecked())
        {
            inparam.set(LstRegularCustomerParams.STATUS_FLAG, PCTRetrievalInParameter.STATUS_FLAG_UNWORK);
            inparam.set(LstRegularCustomerParams.SCHEDULE_FLAG, PCTRetrievalInParameter.SCH_FLAG_COMPLETION);
        }
        // DFKLOOK ここまで修正

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchRegularCustomer_Click");
        redirect("/pcart/retrieval/listbox/regularCustomer/LstRegularCustomer.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchRegularCustomer_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstRegularCustomerParams outparam = new LstRegularCustomerParams(dialogParams);
        txt_RegularCustomerCode.setValue(outparam.get(LstRegularCustomerParams.REGULAR_CUSTOMER_CODE));

        // set focus.
        setFocus(txt_RegularCustomerCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchCustomor_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstCustomerParams inparam = new LstCustomerParams();
        inparam.set(LstCustomerParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstCustomerParams.PLAN_DAY, txt_PlanDay.getValue());
        inparam.set(LstCustomerParams.BATCH_NO, txt_BatchNo.getValue());
        inparam.set(LstCustomerParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
        inparam.set(LstCustomerParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
        inparam.set(LstCustomerParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstCustomerParams.CUSTOMER_CODE, txt_CustomerCode.getValue());

        // DFKLOOK ここから修正
        // 処理内容が削除以外の場合
        if (!rdo_Delete.getChecked())
        {
            inparam.set(LstCustomerParams.STATUS_FLAG, PCTRetrievalInParameter.STATUS_FLAG_UNWORK);
            inparam.set(LstCustomerParams.SCHEDULE_FLAG, PCTRetrievalInParameter.SCH_FLAG_COMPLETION);
        }
        // DFKLOOK ここまで修正

        inparam.set(LstCustomerParams.SEARCHTABLE, InParameter.SEARCH_TABLE_PLAN);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchCustomor_Click");
        redirect("/pcart/retrieval/listbox/customer/LstCustomer.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchCustomor_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstCustomerParams outparam = new LstCustomerParams(dialogParams);
        txt_CustomerCode.setValue(outparam.get(LstCustomerParams.CUSTOMER_CODE));

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
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_PlanDay.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);
        txt_RegularCustomerCode.validate(this, false);
        txt_CustomerCode.validate(this, false);
        txt_OrderNo.validate(this, false);
        pul_Area.validate(this, true);

        // DFKLOOK start
        if (StringUtil.isBlank(eventSource))
        {         
            // 設定しますか?
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Submit_Click");
            return;         
        }
        // DFKLOOK end
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkMntAllSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkMntAllSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkMntAllSCHParams inparam = new WorkMntAllSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(WorkMntAllSCHParams.PROCESS_FLAG, _grp_Group.getSelectedValue());
            inparam.set(WorkMntAllSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(WorkMntAllSCHParams.PLAN_DAY, txt_PlanDay.getValue());
            inparam.set(WorkMntAllSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(WorkMntAllSCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(WorkMntAllSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(WorkMntAllSCHParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
            inparam.set(WorkMntAllSCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(WorkMntAllSCHParams.ORDER_NO, txt_OrderNo.getValue());

            // DFKLOOK:ここから修正
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            // 更新対象データ取得
            inparamList = sch.queryUpdate(inparam);

            // set focus.
            setFocus(_grp_Group.getSelected());

            if (inparamList == null)
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);
            // DFKLOOK:ここまで修正

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // DFKLOOK:ここから修正
            // オペレーションログ出力
            log_write(conn, EmConstants.OPELOG_CLASS_SETTING);
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

    /**
     *
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // clear.
        rdo_AllCompletion.setChecked(true);
        txt_ConsignorCode.setValue(null);
        txt_PlanDay.setValue(null);
        txt_BatchNo.setValue(null);
        txt_BatchSeqNo.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);
        txt_RegularCustomerCode.setValue(null);
        txt_CustomerCode.setValue(null);
        txt_OrderNo.setValue(null);
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkMntAllSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkMntAllSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkMntAllSCHParams inparam = new WorkMntAllSCHParams();

            // DFKLOOK:ここから修正
            // SCH call.
            Params outparam = sch.initFind(inparam);
            if (outparam != null)
            {
                txt_ConsignorCode.setValue(outparam.get(WorkMntAllSCHParams.CONSIGNOR_CODE));
            }
            // DFKLOOK:ここまで修正

            // set focus.
            setFocus(rdo_AllCompletion);

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
