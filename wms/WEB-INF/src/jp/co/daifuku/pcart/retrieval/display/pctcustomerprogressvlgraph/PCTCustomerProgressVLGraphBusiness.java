// $Id: PCTCustomerProgressVLGraphBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.retrieval.display.pctcustomerprogressvlgraph;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.LineChartDataset;
import jp.co.daifuku.bluedog.ui.control.VerticalBarChartDataset;
import jp.co.daifuku.bluedog.ui.control.VerticalBarLineChartBaseDataset;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.chartutil.ChartColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.retrieval.schedule.PCTCustomerProgressVLGraphSCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTCustomerProgressVLGraphSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 出荷先別作業進捗(縦棒)の画面処理を行います。
 *
 * Designer : H.Okayama<BR>
 * Maker : H.Okayama<BR>
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class PCTCustomerProgressVLGraphBusiness
        extends PCTCustomerProgressVLGraph
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_PCTCustomerProgress(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE =
            new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME =
            new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_PROGRESS_RATE) */
    private static final ListCellKey KEY_LST_PROGRESS_RATE =
            new ListCellKey("LST_PROGRESS_RATE", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_ORDER_COUNT =
            new ListCellKey("LST_ORDER_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_BOX_COUNT) */
    private static final ListCellKey KEY_LST_BOX_COUNT =
            new ListCellKey("LST_BOX_COUNT", new NumberCellColumn(0), true, false);

    /** lst_PCTCustomerProgress(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT =
            new ListCellKey("LST_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress(LST_LOT_COUNT) */
    private static final ListCellKey KEY_LST_LOT_COUNT =
            new ListCellKey("LST_LOT_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress kyes */
    private static final ListCellKey[] LST_PCTCUSTOMERPROGRESS_KEYS = {
            KEY_LST_CUSTOMER_CODE,
            KEY_LST_CUSTOMER_NAME,
            KEY_LST_PROGRESS_RATE,
            KEY_LST_ORDER_COUNT,
            KEY_LST_BOX_COUNT,
            KEY_LST_LINE_COUNT,
            KEY_LST_LOT_COUNT,
    };

    // DFKLOOK:ここから修正
    /**
     * ロット数棒グラフMAX初期値
     */
    protected static final double INITMAX_VERTICALBAR = 1400;

    /**
     * 箱数折れ線グラフMAX初期値
     */
    protected static final double INITMAX_LINECHART = 140;

    /**
     * 表示データ(スケジュールからの出力パラメータ)
     */
    private static List<Params> _dispData = null;

    /**
     * 最大ロット数
     */
    private static double _lotMax = 0;

    /**
     * 最大箱数
     */
    private static double _BoxMax = 0;

    /**
     * 表示ページ
     */
    private static int _dispPage = 0;

    /**
     * 表示MAX件数
     */
    protected static final int DISP_MAX = 30;

    /**
     * グラフMAX係数
     */
    protected static final double MAXVAL_COEFFICIENT = 1.05;

    // DFKLOOK:ここまで修正
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_PCTCustomerProgress */
    private ListCellModel _lcm_lst_PCTCustomerProgress;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTCustomerProgressVLGraphBusiness()
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
    public void btn_Display_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Display_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PrevPage_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PrevPage_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_NextPage_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_NextPage_Click_Process();
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
     * グラフを初期化します。
     * @throws Exception 全ての例外を報告します。 
     */
    protected void graphInit()
            throws Exception
    {
        // グラフクリア
        vbc_V_PCTCustomerProgress.clearDataset();

        // ロット数を棒グラフデータセットで作成
        VerticalBarLineChartBaseDataset vertical =
                new VerticalBarChartDataset(DispResources.getText("LBL-P0172"), ChartColor.ROYALBLUE, 0,
                        INITMAX_VERTICALBAR, true);

        // 箱数を折れ線グラフデータセットで作成
        LineChartDataset line =
                new LineChartDataset(DispResources.getText("LBL-P0090"), ChartColor.AQUAMARINE, 0, INITMAX_LINECHART,
                        true);

        // チャートに棒グラフ(ロット数)の空データセットを追加
        vbc_V_PCTCustomerProgress.addDataset(vertical);
        // チャートに折れ線グラフ(生産率)の空データセットを追加
        vbc_V_PCTCustomerProgress.addDataset(line);

        // チャートイメージを作成
        vbc_V_PCTCustomerProgress.createChart(httpRequest);
    }

    /**
     * <code>outParam</code>配列の値をリストセルエリアに設定します。<BR>
     * <BR>
     * 概要:<BR>
     * <DIR>
     *     1.リストセルエリアのクリア<BR>
     *     2.配列データをリストセルエリアへ設定<BR>
     * </DIR>
     * @throws Exception データベースエラーが発生した場合に報告します。
     */
    protected void setList()
            throws Exception
    {
        // リストセルのクリア
        _lcm_lst_PCTCustomerProgress.clear();

        // 表示件数の算出
        int dispPoint = _dispPage * DISP_MAX - DISP_MAX;
        for (int i = 0; i < DISP_MAX; i++)
        {
            // 進捗率の計算
            BigDecimal bgrate =
                    _dispData.get(dispPoint).getBigDecimal(PCTCustomerProgressVLGraphSCHParams.PROGRESS_RATE);
            double rate = bgrate.divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP).doubleValue();

            // リスト行データの生成
            ListCellLine line = _lcm_lst_PCTCustomerProgress.getNewLine();
            // 出荷先コード
            line.setValue(KEY_LST_CUSTOMER_CODE, _dispData.get(dispPoint).get(
                    PCTCustomerProgressVLGraphSCHParams.CUSTOMER_CODE));
            // 出荷先名称
            line.setValue(KEY_LST_CUSTOMER_NAME, _dispData.get(dispPoint).get(
                    PCTCustomerProgressVLGraphSCHParams.CUSTOMER_NAME));
            // 進捗率
            line.setValue(KEY_LST_PROGRESS_RATE, WmsFormatter.toProductionRate(rate));
            // オーダー件数
            line.setValue(KEY_LST_ORDER_COUNT, _dispData.get(dispPoint).get(
                    PCTCustomerProgressVLGraphSCHParams.ORDER_COUNT));
            // 箱数
            line.setValue(KEY_LST_BOX_COUNT,
                    _dispData.get(dispPoint).get(PCTCustomerProgressVLGraphSCHParams.BOX_COUNT));
            // 行数
            line.setValue(KEY_LST_LINE_COUNT, _dispData.get(dispPoint).get(
                    PCTCustomerProgressVLGraphSCHParams.LINE_COUNT));
            // ロット数
            line.setValue(KEY_LST_LOT_COUNT,
                    _dispData.get(dispPoint).get(PCTCustomerProgressVLGraphSCHParams.LOT_COUNT));
            // ToolTip
            lst_PCTCustomerProgress_SetLineToolTip(line);
            // 行追加
            _lcm_lst_PCTCustomerProgress.add(line);

            dispPoint++;
            if (dispPoint >= _dispData.size())
            {
                break;
            }
        }
    }

    /**
     * グラフを表示します。
     * @throws Exception 全ての例外を報告します。 
     */
    protected void graphDisp()
            throws Exception
    {
        // 取得データ位置の取得
        int dispPoint = _dispPage * DISP_MAX - DISP_MAX;

        // 進捗バーのクリア
        vbc_V_PCTCustomerProgress.clearDataset();

        // 箱数を折れ線グラフデータセットで作成(左側のメモリを表示するためのダミー)
        // ※チャートのデータセット順にグラフが表示され、後からセットされる
        //   ロット数の棒グラフが生産率の折れ線グラフ表示を隠してしまうため、
        //   左側にメモリ表示のみを行うためのダミー
        LineChartDataset dummy_line =
                new LineChartDataset(DispResources.getText("LBL-P0090"), ChartColor.AQUAMARINE, 0, (double)_BoxMax
                        * MAXVAL_COEFFICIENT, true);

        // ロット数を棒グラフデータセットで作成
        VerticalBarLineChartBaseDataset vertical =
                new VerticalBarChartDataset(DispResources.getText("LBL-P0172"), ChartColor.ROYALBLUE, 0, _lotMax
                        * MAXVAL_COEFFICIENT, true);

        // 箱数を折れ線グラフデータセットで作成
        //        LineChartDataset line = new LineChartDataset("",
        //                ChartColor.AQUAMARINE, 0, _dispData[0].getProductionRateVal() * MAXVAL_COEFFICIENT, false);
        LineChartDataset line =
                new LineChartDataset(DispResources.getText("LBL-P0090"), ChartColor.AQUAMARINE, 0, (double)_BoxMax
                        * MAXVAL_COEFFICIENT, true);

        /*
         * 現在のページを表示する
         */
        String customerName = "";
        for (int i = 0; i < DISP_MAX; i++)
        {
            // 出荷先名称の取得
            customerName = _dispData.get(dispPoint).getString(PCTCustomerProgressVLGraphSCHParams.CUSTOMER_NAME);
            customerName = customerName.substring(0, customerName.length());

            // 折れ線グラフ(生産率)の値をセット(左側のメモリを表示するためのダミー)
            dummy_line.addGraph(customerName, (double)_dispData.get(dispPoint).getInt(
                    PCTCustomerProgressVLGraphSCHParams.BOX_COUNT));
            // 棒グラフ(ロット数)の値をセット
            vertical.addGraph(customerName, (double)_dispData.get(dispPoint).getInt(
                    PCTCustomerProgressVLGraphSCHParams.LOT_QTY));
            // 折れ線グラフ(生産率)の値をセット
            line.addGraph(customerName, (double)_dispData.get(dispPoint).getInt(
                    PCTCustomerProgressVLGraphSCHParams.BOX_COUNT));

            dispPoint++;
            if (dispPoint >= _dispData.size())
            {
                break;
            }
        }

        // チャートに折れ線グラフ(生産率)のデータセットを追加(左側のメモリを表示するためのダミー)
        // ※チャートのデータセット順にグラフが表示され、後からセットされる
        //   ロット数の棒グラフが生産率の折れ線グラフ表示を隠してしまうため、
        //   左側にメモリ表示のみを行うためのダミー
        //        vbc_VerticalChart.addDataset(dummy_line);
        // チャートに棒グラフ(ロット数)のデータセットを追加
        vbc_V_PCTCustomerProgress.addDataset(vertical);
        // チャートに折れ線グラフ(生産率)のデータセットを追加
        vbc_V_PCTCustomerProgress.addDataset(line);

        // チャートイメージを作成
        vbc_V_PCTCustomerProgress.createChart(httpRequest);
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
        Locale locale = httpRequest.getLocale();

        // initialize lst_PCTCustomerProgress.
        _lcm_lst_PCTCustomerProgress =
                new ListCellModel(lst_PCTCustomerProgress, LST_PCTCUSTOMERPROGRESS_KEYS, locale);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_PROGRESS_RATE, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_ORDER_COUNT, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_BOX_COUNT, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_LINE_COUNT, true);
        _lcm_lst_PCTCustomerProgress.setToolTipVisible(KEY_LST_LOT_COUNT, true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTCustomerProgress_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
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
        PCTCustomerProgressVLGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressVLGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressVLGraphSCHParams inparam = new PCTCustomerProgressVLGraphSCHParams();
            inparam.set(PCTCustomerProgressVLGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // 前ページ表示ボタンを無効化
            btn_PrevPage.setEnabled(false);
            // 次ページ表示ボタンを無効化
            btn_NextPage.setEnabled(false);

            // データが存在しなかった場合
            if (outparams.size() == 0)
            {
                // リストセルのクリア
                _lcm_lst_PCTCustomerProgress.clear();

                // (6003011)対象データはありませんでした。
                message.setMsgResourceKey(WmsMessageFormatter.format(6003011));

                // グラフ初期化
                graphInit();

                return;
            }

            // 取得データ保持
            _dispData = outparams;

            // 最大ロット数と箱数の取得
            _lotMax = 0;
            _BoxMax = 0;
            for (Params param : _dispData)
            {
                if (param.getBigDecimal(PCTCustomerProgressVLGraphSCHParams.LOT_QTY).doubleValue() > _lotMax)
                {
                    _lotMax = param.getBigDecimal(PCTCustomerProgressVLGraphSCHParams.LOT_QTY).doubleValue();
                }
                if (param.getInt(PCTCustomerProgressVLGraphSCHParams.BOX_COUNT) > _BoxMax)
                {
                    _BoxMax = param.getInt(PCTCustomerProgressVLGraphSCHParams.BOX_COUNT);
                }
            }

            // 表示ページを１にする
            _dispPage = 1;

            // 取得データを画面コントロールに設定する
            setList();

            // グラフ表示
            graphDisp();

            // １ページ以上の場合、次ページボタンを有効化する
            if (_dispData.size() > DISP_MAX)
            {
                btn_NextPage.setEnabled(true);
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

            // DFKLOOK:ここから修正
            // グラフ初期化
            graphInit();
            // 表示ボタン(無効化)
            btn_Display.setEnabled(false);
            // 前頁ボタン(無効化)
            btn_PrevPage.setEnabled(false);
            // 次頁ボタン(無効化)
            btn_NextPage.setEnabled(false);
            // DFKLOOK:ここまで修正
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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressVLGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressVLGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressVLGraphSCHParams inparam = new PCTCustomerProgressVLGraphSCHParams();
            inparam.set(PCTCustomerProgressVLGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // 前ページ表示ボタンを無効化
            btn_PrevPage.setEnabled(false);
            // 次ページ表示ボタンを無効化
            btn_NextPage.setEnabled(false);

            // データが存在しなかった場合
            if (outparams.size() == 0)
            {
                // リストセルのクリア
                _lcm_lst_PCTCustomerProgress.clear();

                // (6003011)対象データはありませんでした。
                message.setMsgResourceKey(WmsMessageFormatter.format(6003011));

                // グラフ初期化
                graphInit();

                return;
            }

            // 取得データ保持
            _dispData = outparams;

            // 最大ロット数と箱数の取得
            _lotMax = 0;
            _BoxMax = 0;
            for (Params param : _dispData)
            {
                if (param.getBigDecimal(PCTCustomerProgressVLGraphSCHParams.LOT_QTY).doubleValue() > _lotMax)
                {
                    _lotMax = param.getBigDecimal(PCTCustomerProgressVLGraphSCHParams.LOT_QTY).doubleValue();
                }
                if (param.getInt(PCTCustomerProgressVLGraphSCHParams.BOX_COUNT) > _BoxMax)
                {
                    _BoxMax = param.getInt(PCTCustomerProgressVLGraphSCHParams.BOX_COUNT);
                }
            }

            // 表示ページを１にする
            _dispPage = 1;

            // 取得データを画面コントロールに設定する
            setList();

            // グラフ表示
            graphDisp();

            // １ページ以上の場合、次ページボタンを有効化する
            if (_dispData.size() > DISP_MAX)
            {
                btn_NextPage.setEnabled(true);
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
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PrevPage_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        try
        {
            // 表示ページを減算する
            _dispPage--;

            // 取得データを画面コントロールに設定する
            setList();

            // グラフ表示
            graphDisp();

            // 先頭頁の場合
            if (_dispPage == 1)
            {
                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
            }

            // 次頁ボタンを有効化する
            btn_NextPage.setEnabled(true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));

            // ボタンの無効をセットする
            btn_PrevPage.setEnabled(false);
            btn_NextPage.setEnabled(false);
            // リストセルをクリアする
            _lcm_lst_PCTCustomerProgress.clear();
        }
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_NextPage_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        try
        {
            // 表示ページを加算する
            _dispPage++;

            // 取得データを画面コントロールに設定する
            setList();

            // グラフ表示
            graphDisp();

            // 次頁データが存在しない場合
            if (_dispPage * DISP_MAX > _dispData.size())
            {
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);
            }

            // 前頁ボタンを有効化する
            btn_PrevPage.setEnabled(true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));

            // ボタンの無効をセットする
            btn_PrevPage.setEnabled(false);
            btn_NextPage.setEnabled(false);
            // リストセルをクリアする
            _lcm_lst_PCTCustomerProgress.clear();
        }
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
