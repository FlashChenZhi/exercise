// $Id: ShippingResultABCAnalysisBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.wms.analysis.display.shippingresultabcanalysis;

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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.analysis.display.shippingresultabcanalysis.ShippingResultABCAnalysis;
import jp.co.daifuku.wms.analysis.display.shippingresultabcanalysis.ViewStateKeys;
import jp.co.daifuku.wms.analysis.listbox.shippingresultabcanalysis.LstShippingResultABCAnalysisParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerBusiness;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerParams;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class ShippingResultABCAnalysisBusiness
        extends ShippingResultABCAnalysis
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
    /** RadioButtonGroupModel ANA_AbcAnalysis */
    private RadioButtonGroup _grp_ANA_AbcAnalysis;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ShippingResultABCAnalysisBusiness()
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
        if (eventSource.equals("btn_CustomerSearch_Click"))
        {
            // process call.
            btn_CustomerSearch_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_ListAll_Click"))
        {
            // process call.
            btn_ListAll_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_ListA_Click"))
        {
            // process call.
            btn_ListA_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_ListB_Click"))
        {
            // process call.
            btn_ListB_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_ListC_Click"))
        {
            // process call.
            btn_ListC_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_CustomerSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_CustomerSearch_Click_Process();
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
    public void btn_ListAll_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListAll_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ListA_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListA_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ListB_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListB_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ListC_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListC_Click_Process();
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

        // initialize ANA_AbcAnalysis.
        _grp_ANA_AbcAnalysis = new RadioButtonGroup(new RadioButton[]{rdo_WorkingCnt, rdo_ShippingCnt}, locale);

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
        setFocus(txt_AnaFromDate);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // output display.
        txt_AnaFromDate.setValue(viewState.getObject(ViewStateKeys.FROM_DATE));
        txt_AnaToDate.setValue(viewState.getObject(ViewStateKeys.TO_DATE));
        txt_CustomerCode.setValue(viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
        _grp_ANA_AbcAnalysis.setSelectedValue(viewState.getObject(ViewStateKeys.ANALYSIS_TYPE));
        txt_ThresholdA.setValue(viewState.getObject(ViewStateKeys.THRESHOLD_A));
        txt_ThresholdB.setValue(viewState.getObject(ViewStateKeys.THRESHOLD_B));

        // clear.
        txt_CustomerName.setReadOnly(true);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_CustomerSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstCustomerParams inparam = new LstCustomerParams();
        inparam.set(LstCustomerParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstCustomerParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstCustomerParams.FROM_TO_FLAG, "FROM_TO_FLAG");
        inparam.set(LstCustomerParams.SORT_PLACE_DISP, LstCustomerBusiness.LST_SORT_PLACE_HIDDEN);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_CustomerSearch_Click");
        redirect("/base/listbox/cust/LstCustomer.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_CustomerSearch_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstCustomerParams outparam = new LstCustomerParams(dialogParams);
        txt_CustomerCode.setValue(outparam.get(LstCustomerParams.CUSTOMER_CODE));
        txt_CustomerName.setValue(outparam.get(LstCustomerParams.CUSTOMER_NAME));

        // set focus.
        setFocus(txt_CustomerCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Next_Click_Process()
            throws Exception
    {
        // input validation.
        txt_AnaFromDate.validate(this, true);
        txt_AnaToDate.validate(this, true);
        txt_CustomerCode.validate(this, false);
        txt_CustomerName.validate(this, false);
        rdo_WorkingCnt.validate(false);
        txt_ThresholdA.validate(this, true);
        txt_ThresholdB.validate(this, true);

        try
        {
            // set ViewState parameters.
            viewState.setObject(ViewStateKeys.FROM_DATE, txt_AnaFromDate.getValue());
            viewState.setObject(ViewStateKeys.TO_DATE, txt_AnaToDate.getValue());
            viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            viewState.setObject(ViewStateKeys.CUSTOMER_CODE, txt_CustomerCode.getValue());
            viewState.setObject(ViewStateKeys.ANALYSIS_TYPE, _grp_ANA_AbcAnalysis.getSelectedValue());
            viewState.setObject(ViewStateKeys.THRESHOLD_A, txt_ThresholdA.getValue());
            viewState.setObject(ViewStateKeys.THRESHOLD_B, txt_ThresholdB.getValue());
            viewState.setObject(ViewStateKeys.RANK, "");
            viewState.setObject(ViewStateKeys.DISP_RANK, "");
            viewState.setObject(ViewStateKeys.CUSTOMER_NAME, txt_CustomerName.getValue());

            // forward.
            forward("/analysis/shippingresultabcanalysis/ShippingResultABCAnalysis2.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ListAll_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstShippingResultABCAnalysisParams inparam = new LstShippingResultABCAnalysisParams();
        inparam.set(LstShippingResultABCAnalysisParams.FROM_DATE, txt_AnaFromDate.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.TO_DATE, txt_AnaToDate.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, _grp_ANA_AbcAnalysis.getSelectedValue());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, txt_ThresholdA.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, txt_ThresholdB.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.RANK, "All");
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, "");
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, txt_CustomerName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ListAll_Click");
        redirect("/analysis/listbox/shippingresultabcanalysis/LstShippingResultABCAnalysis.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_ListAll_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ListA_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstShippingResultABCAnalysisParams inparam = new LstShippingResultABCAnalysisParams();
        inparam.set(LstShippingResultABCAnalysisParams.FROM_DATE, txt_AnaFromDate.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.TO_DATE, txt_AnaToDate.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, _grp_ANA_AbcAnalysis.getSelectedValue());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, txt_ThresholdA.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, txt_ThresholdB.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.RANK, "A");
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, "");
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, txt_CustomerName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ListA_Click");
        redirect("/analysis/listbox/shippingresultabcanalysis/LstShippingResultABCAnalysis.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_ListA_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ListB_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstShippingResultABCAnalysisParams inparam = new LstShippingResultABCAnalysisParams();
        inparam.set(LstShippingResultABCAnalysisParams.FROM_DATE, txt_AnaFromDate.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.TO_DATE, txt_AnaToDate.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, _grp_ANA_AbcAnalysis.getSelectedValue());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, txt_ThresholdA.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, txt_ThresholdB.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.RANK, "B");
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, "");
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, txt_CustomerName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ListB_Click");
        redirect("/analysis/listbox/shippingresultabcanalysis/LstShippingResultABCAnalysis.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_ListB_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    private void btn_ListC_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstShippingResultABCAnalysisParams inparam = new LstShippingResultABCAnalysisParams();
        inparam.set(LstShippingResultABCAnalysisParams.FROM_DATE, txt_AnaFromDate.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.TO_DATE, txt_AnaToDate.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, _grp_ANA_AbcAnalysis.getSelectedValue());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, txt_ThresholdA.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, txt_ThresholdB.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.RANK, "C");
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, "");
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, txt_CustomerName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ListC_Click");
        redirect("/analysis/listbox/shippingresultabcanalysis/LstShippingResultABCAnalysis.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_ListC_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
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
