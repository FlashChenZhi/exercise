// $Id: ShippingResultABCAnalysisBusiness.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.analysis.display.shippingresultabcanalysis;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.Constants;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

import jp.co.daifuku.wms.base.util.SessionUtil;

import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.wms.analysis.listbox.shippingresultabcanalysis.LstShippingResultABCAnalysisParams;
import jp.co.daifuku.wms.analysis.schedule.ShippingResultABCAnalysisSCHParams;
import jp.co.daifuku.wms.analysis.schedule.ShippingResultABCAnalysisSCH;
import jp.co.daifuku.wms.analysis.schedule.AnalysisOutParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerParams;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerBusiness;

/**
 * 出荷実績ABC分析（条件入力）の画面処理を行います。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
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
     * 画面が読み込まれたときに呼ばれます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
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
     * 各コントロールイベント呼び出し前に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
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
     * ポップアップウインドから、戻ってくるときの処理をします。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
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
     * 出荷先ボタンが押された時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void btn_CustomerSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_CustomerSearch_Click_Process();
    }

    /**
     * 次へボタンが押された時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void btn_Next_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Next_Click_Process();
    }

    /**
     * 一覧（全て）ボタンが押された時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void btn_ListAll_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListAll_Click_Process();
    }

    /**
     * 一覧（Aのみ）ボタンが押された時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void btn_ListA_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListA_Click_Process();
    }

    /**
     * 一覧（Bのみ）ボタンが押された時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void btn_ListB_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListB_Click_Process();
    }

    /**
     * 一覧（Cのみ）ボタンが押された時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
     */
    private void initializePulldownModels()
            throws Exception
    {
    }

    /**
     *
     * @throws Exception All the exceptions are reported.
     */
    private void dispose()
            throws Exception
    {
    }

    /**
     * カーソルを移動します。
     * @throws Exception All the exceptions are reported.
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // DFKLOOK ここから追加
        if (!StringUtil.isBlank(txt_CustomerCode.getText()))
        {
            // 出荷先コードの入力が有る。
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
                // 出荷先名称を取得
                txt_CustomerName.setText(
                        sch.getCustomerName(WmsParam.DEFAULT_CONSIGNOR_CODE, txt_CustomerCode.getText()));
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
        else
        {
            txt_CustomerName.setText("");
        }
        // DFKLOOK ここまで追加

        // set focus.
        setFocus(txt_AnaFromDate);
    }

    /**
     * 画面の初期表示を行います。
     * @throws Exception All the exceptions are reported.
     */
    private void page_Load_Process()
            throws Exception
    {
        // DFKLOOK （１行追加）１画面目のタブを前に置きます。
        tab.setSelectedIndex(1);

        // output display.
        txt_AnaFromDate.setValue(viewState.getObject(ViewStateKeys.FROM_DATE));
        txt_AnaToDate.setValue(viewState.getObject(ViewStateKeys.TO_DATE));
        txt_CustomerCode.setValue(viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
        // DFKLOOK ラジオボタンの設定はこの後の処理でするからコメントにする
        //_grp_ANA_AbcAnalysis.setSelectedValue(viewState.getObject(ViewStateKeys.ANALYSIS_TYPE));
        txt_ThresholdA.setValue(viewState.getObject(ViewStateKeys.THRESHOLD_A));
        txt_ThresholdB.setValue(viewState.getObject(ViewStateKeys.THRESHOLD_B));

        // clear.
        txt_CustomerName.setReadOnly(true);

        // DFKLOOK ここから追加
        // 分類種別を設定
        String type = (String)viewState.getObject(ViewStateKeys.ANALYSIS_TYPE);
        if (!StringUtil.isBlank(type))
        {
            if (type.equals(DispResources.getText("RDB-W1201")))
            {
                rdo_WorkingCnt.setChecked(true);
            }
            else
            {
                rdo_ShippingCnt.setChecked(true);
            }
        }
        else
        {
            rdo_WorkingCnt.setChecked(true);
        }

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

            // 出荷先コードに入力が有るか確認します。
            if (!StringUtil.isBlank(txt_CustomerCode.getText()))
            {
                // 出荷先名称を取得
                txt_CustomerName.setValue(viewState.getObject(ViewStateKeys.CUSTOMER_NAME));
            }
            else
            {
                // 出荷先名称を取得
                txt_CustomerName.setValue("");
            }

            // 分析用設定ファイルからしきい値を取得
            Params p = sch.initFind(new ScheduleParams());
            if (p != null)
            {
                // Aランクしきい値の入力値がないとき
                if (StringUtil.isBlank(txt_ThresholdA.getText()))
                {
                    // 分析設定ファイルの値を表示します。
                    txt_ThresholdA.setInt(p.getInt(ShippingResultABCAnalysisSCHParams.THRESHOLD_A));
                }
                // Bランクしきい値の入力値がないとき
                if (StringUtil.isBlank(txt_ThresholdB.getText()))
                {
                    // 分析設定ファイルの値を表示します。
                    txt_ThresholdB.setInt(p.getInt(ShippingResultABCAnalysisSCHParams.THRESHOLD_B));
                }
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
        // DFKLOOK ここまで追加
    }

    /**
     * 出荷先検索ボタンが押された時の処理です。
     * @throws Exception All the exceptions are reported.
     */
    private void btn_CustomerSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstCustomerParams inparam = new LstCustomerParams();
        inparam.set(LstCustomerParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstCustomerParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
        inparam.set(LstCustomerParams.FROM_TO_FLAG, "");
        inparam.set(LstCustomerParams.SORT_PLACE_DISP, LstCustomerBusiness.LST_SORT_PLACE_HIDDEN);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_CustomerSearch_Click");
        redirect("/base/listbox/cust/LstCustomer.do", forwardParam, "/Progress.do");
    }

    /**
     * 出荷先検索で出荷先が選択された時に呼び出されます。
     * @param dialogParams DialogParameters
     * @throws Exception All the exceptions are reported.
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
     * 次へボタンが押された時の処理です。
     * @throws Exception All the exceptions are reported.
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

        // set ViewState parameters.
        viewState.setObject(ViewStateKeys.FROM_DATE, txt_AnaFromDate.getValue());
        viewState.setObject(ViewStateKeys.TO_DATE, txt_AnaToDate.getValue());
        viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        viewState.setObject(ViewStateKeys.CUSTOMER_CODE, txt_CustomerCode.getValue());
        // DFKLOOK 分析種別を設定
        viewState.setObject(ViewStateKeys.ANALYSIS_TYPE, getAnaLysisType());
        viewState.setObject(ViewStateKeys.THRESHOLD_A, txt_ThresholdA.getValue());
        viewState.setObject(ViewStateKeys.THRESHOLD_B, txt_ThresholdB.getValue());
        // DFKLOOK AnalysisOutParameter.RANK_ALLを設定
        viewState.setObject(ViewStateKeys.RANK, AnalysisOutParameter.RANK_ALL);
        viewState.setObject(ViewStateKeys.DISP_RANK, "");
        viewState.setObject(ViewStateKeys.CUSTOMER_NAME, txt_CustomerName.getValue());

        // DFKLOOK ここから追加
        // グラフ表示の処理
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

            // スケジュールパラメータへセット
            ShippingResultABCAnalysisSCHParams sparams = new ShippingResultABCAnalysisSCHParams();

            // 荷主コード 
            sparams.set(ShippingResultABCAnalysisSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            // 出荷先コード 
            sparams.set(ShippingResultABCAnalysisSCHParams.CUSTOMER_CODE, txt_CustomerCode.getText());

            if (!sch.nextCheck(sparams))
            {
                // メッセージをセット
                message.setMsgResourceKey(sch.getMessage());
                txt_CustomerName.setText("");

                return;
            }

            // 分析用設定ファイルハンドラーへしきい値を保存
            sparams.set(ShippingResultABCAnalysisSCHParams.THRESHOLD_A, txt_ThresholdA.getText());
            sparams.set(ShippingResultABCAnalysisSCHParams.THRESHOLD_B, txt_ThresholdB.getText());

            // スケジュールを開始します。
            if (!sch.startSCH(sparams))
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // 出荷履歴データセットをセッションにバインドします。
            // 取り残されているオブジェクトを一旦取り除きます。
            Object sRet = (Object)session.getAttribute("ShippingResultABCAnalysis");
            if (null != sRet)
            {
                session.removeAttribute("ShippingResultABCAnalysis");
            }

            // グラフ表示用データを取得
            AnalysisOutParameter viewParam = getABCDataSet(conn, locale, ui);
            if (null != viewParam)
            {
                // SessionにShippingResultABCAnalysisを保持
                this.getSession().setAttribute("ShippingResultABCAnalysis", viewParam);
                // DFKLOOK ここまで追加
                // forward.
                forward("/analysis/shippingresultabcanalysis/ShippingResultABCAnalysis2.do");
                // DFKLOOK ここから追加
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
        // DFKLOOK ここまで追加
    }

    /**
     * 一覧（全て）ボタンが押された時の処理です。
     * @throws Exception All the exceptions are reported.
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
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, txt_CustomerName.getValue());
        // DFKLOOK 分析種別を設定
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, getAnaLysisType());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, txt_ThresholdA.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, txt_ThresholdB.getValue());
        // DFKLOOK AnalysisOutParameter.RANK_ALLを設定
        inparam.set(LstShippingResultABCAnalysisParams.RANK, AnalysisOutParameter.RANK_ALL);
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, DispResources.getText("LBL-W1276"));
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, txt_CustomerName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ListAll_Click");
        redirect("/analysis/listbox/shippingresultabcanalysis/LstShippingResultABCAnalysis.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     * @throws Exception All the exceptions are reported.
     */
    private void btn_ListAll_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
    }

    /**
     * 一覧（Aのみ）ボタンが押された時の処理です。
     * @throws Exception All the exceptions are reported.
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
        // DFKLOOK 分析種別を設定
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, getAnaLysisType());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, txt_ThresholdA.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, txt_ThresholdB.getValue());
        // DFKLOOK AnalysisOutParameter.RANK_Aを設定
        inparam.set(LstShippingResultABCAnalysisParams.RANK, AnalysisOutParameter.RANK_A);
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, DispResources.getText("LBL-W1277"));
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, txt_CustomerName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ListA_Click");
        redirect("/analysis/listbox/shippingresultabcanalysis/LstShippingResultABCAnalysis.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     * @throws Exception All the exceptions are reported.
     */
    private void btn_ListA_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
    }

    /**
     * 一覧（Bのみ）ボタンが押された時の処理です。
     * @throws Exception All the exceptions are reported.
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
        // DFKLOOK 分析種別を設定
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, getAnaLysisType());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, txt_ThresholdA.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, txt_ThresholdB.getValue());
        // DFKLOOK AnalysisOutParameter.RANK_Bを設定
        inparam.set(LstShippingResultABCAnalysisParams.RANK, AnalysisOutParameter.RANK_B);
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK,  DispResources.getText("LBL-W1278"));
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, txt_CustomerName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ListB_Click");
        redirect("/analysis/listbox/shippingresultabcanalysis/LstShippingResultABCAnalysis.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     * @throws Exception All the exceptions are reported.
     */
    private void btn_ListB_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
    }

    /**
     * 一覧（Cのみ）ボタンが押された時の処理です。
     * @throws Exception All the exceptions are reported.
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
        // DFKLOOK 分析種別を設定
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, getAnaLysisType());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, txt_ThresholdA.getValue());
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, txt_ThresholdB.getValue());
        // DFKLOOK AnalysisOutParameter.RANK_Cを設定
        inparam.set(LstShippingResultABCAnalysisParams.RANK, AnalysisOutParameter.RANK_C);
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, DispResources.getText("LBL-W1279"));
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, txt_CustomerName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ListC_Click");
        redirect("/analysis/listbox/shippingresultabcanalysis/LstShippingResultABCAnalysis.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     * @throws Exception All the exceptions are reported.
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

    // DFKLOOK ここから追加
    /** 
     * ABC分析データセットを取得し返します。
     *
     * @param conn コネクションオブジェクト
     * @param locale Locale
     * @param ui DfkUserInfo
     * @return 分析データを格納したパラメータ。異常時はnull。
     * @throws Exception 全ての例外を報告します。
     */
    private AnalysisOutParameter getABCDataSet(Connection conn, Locale locale, DfkUserInfo ui)
            throws Exception
    {
        // スケジュールパラメータの宣言
        ShippingResultABCAnalysisSCHParams param = new ShippingResultABCAnalysisSCHParams();

        // １画面から持ってきた値をパラメータをセットします。
        param.set(ShippingResultABCAnalysisSCHParams.FROM_DATE,
                    viewState.getDate(ViewStateKeys.FROM_DATE));
        param.set(ShippingResultABCAnalysisSCHParams.TO_DATE,
                    viewState.getDate(ViewStateKeys.TO_DATE));
        param.set(ShippingResultABCAnalysisSCHParams.CONSIGNOR_CODE,
                    viewState.getString(ViewStateKeys.CONSIGNOR_CODE));
        param.set(ShippingResultABCAnalysisSCHParams.CUSTOMER_CODE,
                    viewState.getString(ViewStateKeys.CUSTOMER_CODE));
        param.set(ShippingResultABCAnalysisSCHParams.THRESHOLD_A,
                    viewState.getBigDecimal(ViewStateKeys.THRESHOLD_A));
        param.set(ShippingResultABCAnalysisSCHParams.THRESHOLD_B,
                    viewState.getBigDecimal(ViewStateKeys.THRESHOLD_B));

        ShippingResultABCAnalysisSCH sch =
            new ShippingResultABCAnalysisSCH(conn, this.getClass(), locale, ui);

        AnalysisOutParameter viewParam = null;
        List<Params> lparams = sch.query(param);
        if (lparams != null)
        {
            viewParam = (AnalysisOutParameter)lparams.get(0);
        }
        else
        {
            message.setMsgResourceKey(sch.getMessage());
            return null;
        }

        return viewParam;
    }

    /**
     * 分析種別ラジオボタンを参照し、分析種別を返します。
     * @return 分析種別
     */
    private String getAnaLysisType()
    {
        // 分析種別を返します。
        if (rdo_WorkingCnt.getChecked())
        {
            return DispResources.getText("RDB-W1201");
        }
        else
        {
            return DispResources.getText("RDB-W1202");
        }
    }
    // DFKLOOK ここまで追加

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
