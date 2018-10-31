// $Id: ShippingResultABCAnalysis2Business.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.analysis.display.shippingresultabcanalysis;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;
import java.util.Date;
import org.jfree.chart.JFreeChart;

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
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.WmsFormatter;

import jp.co.daifuku.wms.base.util.SessionUtil;

import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.analysis.listbox.shippingresultabcanalysis.LstShippingResultABCAnalysisParams;
import jp.co.daifuku.wms.analysis.schedule.ShippingResultABCAnalysisSCH;
import jp.co.daifuku.wms.analysis.schedule.ShippingResultABCAnalysisSCHParams;
import jp.co.daifuku.wms.analysis.schedule.AnalysisInParameter;
import jp.co.daifuku.wms.analysis.schedule.AnalysisOutParameter;
import jp.co.daifuku.wms.analysis.operator.AnalysisOperator;

/**
 * 出荷実績ABC分析（分析結果）の画面処理を行います。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
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
     * 戻るボタンが押された時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Back_Click_Process();
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
     * 画面の初期表示を行います。
     * @throws Exception All the exceptions are reported.
     */
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // DFKLOOK （１行追加）２画面目のタブを前に置きます。
        tab.setSelectedIndex(2);

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

            // DFKLOOK ここから追加
            // 出荷先名、分析期間、分析種別、しきい値の表示
            // 開始分析対象期間と、終了分析対象期間の大小関係を補正して取得し、画面に表示します。
            Date[] fromToDate = WmsFormatter.getFromTo(viewState.getDate(ViewStateKeys.FROM_DATE),
                                                        viewState.getDate(ViewStateKeys.TO_DATE));
            lbl_AnaFromDate.setText(WmsFormatter.toDispDate(fromToDate[0], locale));
            lbl_AnaToDate.setText(WmsFormatter.toDispDate(fromToDate[1], locale));
            lbl_CustomerCode.setText(viewState.getString(ViewStateKeys.CUSTOMER_CODE));
            lbl_CustomerName.setText("");
            if (!StringUtil.isBlank(lbl_CustomerCode.getText()))
            {
                // 出荷先名称を取得
                lbl_CustomerName.setText(sch.getCustomerName(WmsParam.DEFAULT_CONSIGNOR_CODE, lbl_CustomerCode.getText()));
            }
            lbl_AnalysisType.setText(viewState.getString(ViewStateKeys.ANALYSIS_TYPE));

            long tha = viewState.getBigDecimal(ViewStateKeys.THRESHOLD_A).longValue();
            lbl_ThresholdA.setText(String.valueOf(tha));
            long thb = viewState.getBigDecimal(ViewStateKeys.THRESHOLD_B).longValue();
            lbl_ThresholdB.setText(String.valueOf(thb));

            // ABC分析グラフを表示します。
            createChart(conn, inparam);

            // 6001013=表示しました。
            message.setMsgResourceKey("6001013");
            // DFKLOOK ここまで追加
            // DFKLOOK ここから削除
            // DFKLOOK SCHの呼び出しとメッセージ取得を削除
            // SCH call.
            // List<Params> outparams = sch.query(inparam);
            // message.setMsgResourceKey(sch.getMessage());
            // DFKLOOK ここまで削除

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
     * 戻るボタンが押された時の処理です。
     * @throws Exception All the exceptions are reported.
     */
    private void btn_Back_Click_Process()
            throws Exception
    {
        // forward.
        forward("/analysis/shippingresultabcanalysis/ShippingResultABCAnalysis.do");
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
        inparam.set(LstShippingResultABCAnalysisParams.FROM_DATE, viewState.getObject(ViewStateKeys.FROM_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.TO_DATE, viewState.getObject(ViewStateKeys.TO_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, viewState.getObject(ViewStateKeys.ANALYSIS_TYPE));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, viewState.getObject(ViewStateKeys.THRESHOLD_A));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, viewState.getObject(ViewStateKeys.THRESHOLD_B));
        // DFKLOOK ここから変更
        // 引数の指定を変更
        inparam.set(LstShippingResultABCAnalysisParams.RANK, AnalysisOutParameter.RANK_ALL);
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, DispResources.getText("LBL-W1276"));
        // DFKLOOK ここまで変更
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, viewState.getObject(ViewStateKeys.CUSTOMER_NAME));

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
        inparam.set(LstShippingResultABCAnalysisParams.FROM_DATE, viewState.getObject(ViewStateKeys.FROM_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.TO_DATE, viewState.getObject(ViewStateKeys.TO_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, viewState.getObject(ViewStateKeys.ANALYSIS_TYPE));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, viewState.getObject(ViewStateKeys.THRESHOLD_A));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, viewState.getObject(ViewStateKeys.THRESHOLD_B));
        // DFKLOOK ここから変更
        // 引数の指定を変更
        inparam.set(LstShippingResultABCAnalysisParams.RANK, AnalysisOutParameter.RANK_A);
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, DispResources.getText("LBL-W1277"));
        // DFKLOOK ここまで変更
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, viewState.getObject(ViewStateKeys.CUSTOMER_NAME));

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
        inparam.set(LstShippingResultABCAnalysisParams.FROM_DATE, viewState.getObject(ViewStateKeys.FROM_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.TO_DATE, viewState.getObject(ViewStateKeys.TO_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, viewState.getObject(ViewStateKeys.ANALYSIS_TYPE));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, viewState.getObject(ViewStateKeys.THRESHOLD_A));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, viewState.getObject(ViewStateKeys.THRESHOLD_B));
        // DFKLOOK ここから変更
        // 引数の指定を変更
        inparam.set(LstShippingResultABCAnalysisParams.RANK, AnalysisOutParameter.RANK_B);
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, DispResources.getText("LBL-W1278"));
        // DFKLOOK ここまで変更
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, viewState.getObject(ViewStateKeys.CUSTOMER_NAME));

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
        inparam.set(LstShippingResultABCAnalysisParams.FROM_DATE, viewState.getObject(ViewStateKeys.FROM_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.TO_DATE, viewState.getObject(ViewStateKeys.TO_DATE));
        inparam.set(LstShippingResultABCAnalysisParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_CODE, viewState.getObject(ViewStateKeys.CUSTOMER_CODE));
        inparam.set(LstShippingResultABCAnalysisParams.ANALYSIS_TYPE, viewState.getObject(ViewStateKeys.ANALYSIS_TYPE));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_A, viewState.getObject(ViewStateKeys.THRESHOLD_A));
        inparam.set(LstShippingResultABCAnalysisParams.THRESHOLD_B, viewState.getObject(ViewStateKeys.THRESHOLD_B));
        // DFKLOOK ここから変更
        // 引数の指定を変更
        inparam.set(LstShippingResultABCAnalysisParams.RANK, AnalysisOutParameter.RANK_C);
        inparam.set(LstShippingResultABCAnalysisParams.DISP_RANK, DispResources.getText("LBL-W1279"));
        // DFKLOOK ここまで変更
        inparam.set(LstShippingResultABCAnalysisParams.CUSTOMER_NAME, viewState.getObject(ViewStateKeys.CUSTOMER_NAME));

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
     * ABC分析グラフを描画します。
     * @param conn DBコネクション
     * @param p ScheduleParamsクラスのインスタンス。
     * @throws Exception 全ての例外を報告します。
     */
    private void createChart(Connection conn, Params p)
            throws Exception
    {
        AnalysisOutParameter oparam = (AnalysisOutParameter)this.getSession().getAttribute("ShippingResultABCAnalysis");

        AnalysisInParameter iparam = new AnalysisInParameter();
        iparam.setAnalysisType(p.getString(ShippingResultABCAnalysisSCHParams.ANALYSIS_TYPE));
        iparam.setThresholdA(p.getInt(ShippingResultABCAnalysisSCHParams.THRESHOLD_A));
        iparam.setThresholdB(p.getInt(ShippingResultABCAnalysisSCHParams.THRESHOLD_B));

        AnalysisOperator op = new AnalysisOperator(conn, this.getClass());
        // 分析グラフを作成
        JFreeChart chart = op.createChart(iparam, oparam);

        // JFreeChartオブジェクトのセット
        glc_Chart.setChart(chart);

        // ファイルの出力
        glc_Chart.createChart(httpRequest);
        glc_Chart.setVisible(true);
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
