// $Id: ShippingResultABCAnalysis2Business.java 5290 2009-10-28 04:29:37Z kishimoto $
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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.analysis.display.shippingresultabcanalysis.ShippingResultABCAnalysis2;
import jp.co.daifuku.wms.analysis.display.shippingresultabcanalysis.ViewStateKeys;
import jp.co.daifuku.wms.analysis.listbox.shippingresultabcanalysis.LstShippingResultABCAnalysisParams;
import jp.co.daifuku.wms.analysis.schedule.ShippingResultABCAnalysisSCH;
import jp.co.daifuku.wms.analysis.schedule.ShippingResultABCAnalysisSCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class ShippingResultABCAnalysis2Business
        extends ShippingResultABCAnalysis2
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
    public ShippingResultABCAnalysis2Business()
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
        if (eventSource.equals("btn_ListAll_Click"))
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
        ShippingResultABCAnalysisSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ShippingResultABCAnalysisSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ShippingResultABCAnalysisSCHParams inparam = new ShippingResultABCAnalysisSCHParams();
            inparam.set(ShippingResultABCAnalysisSCHParams.FROM_DATE, viewState.getObject(ViewStateKeys.FROM_DATE));
            inparam.set(ShippingResultABCAnalysisSCHParams.TO_DATE, viewState.getObject(ViewStateKeys.TO_DATE));
            inparam.set(ShippingResultABCAnalysisSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(ShippingResultABCAnalysisSCHParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
            inparam.set(ShippingResultABCAnalysisSCHParams.ANALYSIS_TYPE, viewState.getObject(ViewStateKeys.ANALYSIS_TYPE));
            inparam.set(ShippingResultABCAnalysisSCHParams.THRESHOLD_A, viewState.getObject(ViewStateKeys.THRESHOLD_A));
            inparam.set(ShippingResultABCAnalysisSCHParams.THRESHOLD_B, viewState.getObject(ViewStateKeys.THRESHOLD_B));

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            btn_ListAll.setEnabled(true);
            btn_ListA.setEnabled(true);
            btn_ListB.setEnabled(true);
            btn_ListC.setEnabled(true);

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
            // forward.
            forward("/analysis/shippingresultabcanalysis/ShippingResultABCAnalysis.do");
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
        inparam.set(LstShippingResultABCAnalysisParams.FROM_DATE, viewState.getObject(ViewStateKeys.FROM_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.TO_DATE, viewState.getObject(ViewStateKeys.TO_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, viewState.getObject(ViewStateKeys.ANALYSIS_TYPE));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, viewState.getObject(ViewStateKeys.THRESHOLD_A));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, viewState.getObject(ViewStateKeys.THRESHOLD_B));
        inparam.set(LstShippingResultABCAnalysisParams.RANK, "All");
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, "");
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, viewState.getObject(ViewStateKeys.CUSTOMER_NAME));

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
        inparam.set(LstShippingResultABCAnalysisParams.FROM_DATE, viewState.getObject(ViewStateKeys.FROM_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.TO_DATE, viewState.getObject(ViewStateKeys.TO_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, viewState.getObject(ViewStateKeys.ANALYSIS_TYPE));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, viewState.getObject(ViewStateKeys.THRESHOLD_A));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, viewState.getObject(ViewStateKeys.THRESHOLD_B));
        inparam.set(LstShippingResultABCAnalysisParams.RANK, "A");
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, "");
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, viewState.getObject(ViewStateKeys.CUSTOMER_NAME));

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
        inparam.set(LstShippingResultABCAnalysisParams.FROM_DATE, viewState.getObject(ViewStateKeys.FROM_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.TO_DATE, viewState.getObject(ViewStateKeys.TO_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, viewState.getObject(ViewStateKeys.ANALYSIS_TYPE));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, viewState.getObject(ViewStateKeys.THRESHOLD_A));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, viewState.getObject(ViewStateKeys.THRESHOLD_B));
        inparam.set(LstShippingResultABCAnalysisParams.RANK, "B");
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, "");
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, viewState.getObject(ViewStateKeys.CUSTOMER_NAME));

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
        inparam.set(LstShippingResultABCAnalysisParams.FROM_DATE, viewState.getObject(ViewStateKeys.FROM_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.TO_DATE, viewState.getObject(ViewStateKeys.TO_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, viewState.getObject(ViewStateKeys.ANALYSIS_TYPE));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, viewState.getObject(ViewStateKeys.THRESHOLD_A));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, viewState.getObject(ViewStateKeys.THRESHOLD_B));
        inparam.set(LstShippingResultABCAnalysisParams.RANK, "C");
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, "");
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, viewState.getObject(ViewStateKeys.CUSTOMER_NAME));

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
