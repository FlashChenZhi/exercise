// $Id: PCTUserWorkInquiryBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.retrieval.display.pctuserworkinquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
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
import jp.co.daifuku.pcart.retrieval.schedule.PCTUserWorkInquirySCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTUserWorkInquirySCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * ユーザ別作業状況照会の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class PCTUserWorkInquiryBusiness
        extends PCTUserWorkInquiry
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_UserWorkInquiry(LST_LINE_NO) */
    private static final ListCellKey KEY_LST_LINE_NO =
            new ListCellKey("LST_LINE_NO", new NumberCellColumn(0), true, false);

    /** lst_UserWorkInquiry(LST_USER_ID) */
    private static final ListCellKey KEY_LST_USER_ID =
            new ListCellKey("LST_USER_ID", new StringCellColumn(), true, false);

    /** lst_UserWorkInquiry(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME =
            new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_UserWorkInquiry(LST_PRODUCTION_RATE) */
    private static final ListCellKey KEY_LST_PRODUCTION_RATE =
            new ListCellKey("LST_PRODUCTION_RATE", new NumberCellColumn(0), true, false);

    /** lst_UserWorkInquiry(LST_LOT_COUNT) */
    private static final ListCellKey KEY_LST_LOT_COUNT =
            new ListCellKey("LST_LOT_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UserWorkInquiry(LST_ORDER_QTY) */
    private static final ListCellKey KEY_LST_ORDER_QTY =
            new ListCellKey("LST_ORDER_QTY", new NumberCellColumn(0), true, false);

    /** lst_UserWorkInquiry(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT =
            new ListCellKey("LST_LINE_COUNT", new NumberCellColumn(0), true, false);

    /** lst_UserWorkInquiry(REAL_TIME) */
    private static final ListCellKey KEY_REAL_TIME = new ListCellKey("REAL_TIME", new StringCellColumn(), true, false);

    /** lst_UserWorkInquiry kyes */
    private static final ListCellKey[] LST_USERWORKINQUIRY_KEYS = {
            KEY_LST_LINE_NO,
            KEY_LST_USER_ID,
            KEY_LST_USER_NAME,
            KEY_LST_PRODUCTION_RATE,
            KEY_LST_LOT_COUNT,
            KEY_LST_ORDER_QTY,
            KEY_LST_LINE_COUNT,
            KEY_REAL_TIME,
    };

    // DFKLOOK:ここから修正
    /**
     * 隠し項目 実績数
     */
    protected static final int HIDDEN_RESULT = 0;

    /**
     * 検索結果表示用:No.(リストセル)
     */
    protected static final int LIST_NO = 1;

    /**
     * 検索結果表示用:ユーザID(リストセル)
     */
    protected static final int LIST_USERID = 2;

    /**
     * 検索結果表示用:ユーザ名称(リストセル)
     */
    protected static final int LIST_USERNAME = 3;

    /**
     * 検索結果表示用:生産率(リストセル)
     */
    protected static final int LIST_RATE = 4;

    /**
     * 検索結果表示用:ロット数(リストセル)
     */
    protected static final int LIST_LOTQTY = 5;

    /**
     * 検索結果表示用:オーダー数(リストセル)
     */
    protected static final int LIST_ORDERQTY = 6;

    /**
     * 検索結果表示用:行数(リストセル)
     */
    protected static final int LIST_LINEQTY = 7;

    /**
     * 検索結果表示用:実稼働時間(リストセル)
     */
    protected static final int LIST_TIME = 8;

    /**
     * リストセルソート時のダミーデータ
     */
    protected static final String SORT_DUMMY_ADD_VAL = "0000000000";

    /**
     * リストセルソート時の生産率最大長(5ZZZ9.9)
     */
    protected static final int SORT_MAX_LEN_PRODUCTIONRATE = 5 + 1 + 1;

    /**
     * リストセルソート時のロット数最大長(9ZZZZZZZ9)
     */
    protected static final int SORT_MAX_LEN_LOTQTY = 9;

    /**
     * リストセルソート時の読込み順(SCHにてユーザID順にソートされている)
     */
    protected static final int SORT_MAX_LEN_READNO = 9;

    /**
     * グラフMAX係数
     */
    protected static final double MAXVAL_COEFFICIENT = 1.05;

    /**
     * 生産率折れ線グラフMAX初期値
     */
    protected static final double INITMAX_LINECHART = 140;

    /**
     * ロット数棒グラフMAX初期値
     */
    protected static final double INITMAX_VERTICALBAR = 1000;

    /**
     * 表示MAX件数
     */
    protected static final int DISP_MAX = 30;

    /**
     * ユーザ表示桁数
     */
    protected static final int USER_DISP_LENGTH = 4;

    /**
     * 表示ページ
     */
    private static int _dispPage = 0;

    /**
     * 表示ページ
     */
    private static double _lotMax = 0;

    /**
     * 表示データ(スケジュールからの出力パラメータ)
     */
    private static Params[] _dispData = null;

    // DFKLOOK:ここまで修正

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_UserWorkInquiry */
    private ListCellModel _lcm_lst_UserWorkInquiry;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTUserWorkInquiryBusiness()
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
        btn_NextPage_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
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
        // リストセルエリアをクリアする
        lst_UserWorkInquiry.clearRow();

        int dispPoint = _dispPage * DISP_MAX - DISP_MAX;
        for (int i = 0; i < DISP_MAX; i++)
        {
            // 最終行を取得
            int count = lst_UserWorkInquiry.getMaxRows();

            // 行を追加
            lst_UserWorkInquiry.addRow();

            // 最終行にカーソルを移動
            lst_UserWorkInquiry.setCurrentRow(count);

            // No.
            lst_UserWorkInquiry.setValue(LIST_NO, WmsFormatter.getNumFormat(dispPoint + 1));
            // ユーザID
            lst_UserWorkInquiry.setValue(LIST_USERID,
                    _dispData[dispPoint].getString(PCTUserWorkInquirySCHParams.USER_ID));
            // ユーザ名称
            lst_UserWorkInquiry.setValue(LIST_USERNAME,
                    _dispData[dispPoint].getString(PCTUserWorkInquirySCHParams.USER_NAME));
            // 生産率
            lst_UserWorkInquiry.setValue(LIST_RATE,
                    _dispData[dispPoint].getString(PCTUserWorkInquirySCHParams.PRODUCTION_RATE));
            // ロット数
            lst_UserWorkInquiry.setValue(LIST_LOTQTY,
                    WmsFormatter.getNumFormat(_dispData[dispPoint].getInt(PCTUserWorkInquirySCHParams.LOT_COUNT)));
            // オーダー数
            lst_UserWorkInquiry.setValue(LIST_ORDERQTY,
                    WmsFormatter.getNumFormat(_dispData[dispPoint].getInt(PCTUserWorkInquirySCHParams.ORDER_QTY)));
            // 行数
            lst_UserWorkInquiry.setValue(LIST_LINEQTY,
                    WmsFormatter.getNumFormat(_dispData[dispPoint].getInt(PCTUserWorkInquirySCHParams.LINE_COUNT)));
            // 実稼働時間
            lst_UserWorkInquiry.setValue(LIST_TIME,
                    WmsFormatter.toDispHourMinute((_dispData[dispPoint].getInt(PCTUserWorkInquirySCHParams.REAL_TIME))));


            dispPoint++;
            if (dispPoint >= _dispData.length)
            {
                break;
            }
        }
    }

    /**
     * リストセルに表示するデータを降順にソートします。<BR>
     *   ・生産率の降順<BR>
     *   ・ロット数の降順<BR>
     * <BR>
     * @param  param ソート前のオブジェクト
     * @throws Exception データベースエラーが発生した場合に報告します。
     * @return sortParam[] 降順ソート後のオブジェクト
     */
    protected Params[] listSort(List<Params> param)
            throws Exception
    {
        TreeMap<String, Params> sortMap = new TreeMap<String, Params>(new ExmComparator());

        String sortKey = null;
        String sortRate = null;
        String sortLotQty = null;
        String sortReadNo = null;
        int i = 0;
        for (Params outparam : param)
        {
            sortRate = SORT_DUMMY_ADD_VAL + outparam.getString(PCTUserWorkInquirySCHParams.PRODUCTION_RATEVAL);
            sortRate = sortRate.substring(sortRate.length() - SORT_MAX_LEN_PRODUCTIONRATE);
            sortLotQty =
                    SORT_DUMMY_ADD_VAL
                            + WmsFormatter.getNumFormat(outparam.getInt(PCTUserWorkInquirySCHParams.LOT_COUNT));
            sortLotQty = sortLotQty.substring(sortLotQty.length() - SORT_MAX_LEN_LOTQTY);
            sortReadNo = SORT_DUMMY_ADD_VAL + i;
            sortReadNo = sortReadNo.substring(sortReadNo.length() - SORT_MAX_LEN_READNO);
            sortKey = sortRate + sortLotQty + sortReadNo;
            sortMap.put(sortKey, outparam);
            i++;
        }

        Params[] sortParam = new Params[param.size()];
        int loop = 0;
        Iterator it = sortMap.keySet().iterator();
        while (it.hasNext())
        {
            Object object = it.next();
            sortParam[loop] = sortMap.get(object);
            loop++;
        }

        return sortParam;
    }


    /**
     * グラフを初期化します。
     * @throws Exception 全ての例外を報告します。 
     */
    protected void graphInit()
            throws Exception
    {
        // グラフクリア
        vbc_VerticalChart.clearDataset();

        // ロット数を棒グラフデータセットで作成
        VerticalBarLineChartBaseDataset vertical =
                new VerticalBarChartDataset(DispResources.getText("LBL-P0172"), ChartColor.ROYALBLUE, 0,
                        INITMAX_VERTICALBAR, true);

        // 生産率を折れ線グラフデータセットで作成
        LineChartDataset line =
                new LineChartDataset(DispResources.getText("LBL-P0066"), ChartColor.AQUAMARINE, 0, INITMAX_LINECHART,
                        true);

        // チャートに棒グラフ(ロット数)の空データセットを追加
        vbc_VerticalChart.addDataset(vertical);
        // チャートに折れ線グラフ(生産率)の空データセットを追加
        vbc_VerticalChart.addDataset(line);

        // チャートイメージを作成
        vbc_VerticalChart.createChart(httpRequest);
    }

    /**
     * グラフを表示します。
     * @throws Exception 全ての例外を報告します。 
     */
    protected void graphDisp()
            throws Exception
    {
        // グラフクリア
        vbc_VerticalChart.clearDataset();

        Double prodution = new Double(_dispData[0].getString(PCTUserWorkInquirySCHParams.PRODUCTION_RATEVAL));

        // 生産率を折れ線グラフデータセットで作成(左側のメモリを表示するためのダミー)
        // ※チャートのデータセット順にグラフが表示され、後からセットされる
        //   ロット数の棒グラフが生産率の折れ線グラフ表示を隠してしまうため、
        //   左側にメモリ表示のみを行うためのダミー
        LineChartDataset dummy_line =
                new LineChartDataset(DispResources.getText("LBL-P0066"), ChartColor.AQUAMARINE, 0,
                        prodution.doubleValue() * MAXVAL_COEFFICIENT, true);

        // ロット数を棒グラフデータセットで作成
        VerticalBarLineChartBaseDataset vertical =
                new VerticalBarChartDataset(DispResources.getText("LBL-P0172"), ChartColor.ROYALBLUE, 0, _lotMax
                        * MAXVAL_COEFFICIENT, true);

        // 生産率を折れ線グラフデータセットで作成
        LineChartDataset line =
                new LineChartDataset(DispResources.getText("LBL-P0066"), ChartColor.AQUAMARINE, 0,
                        prodution.doubleValue() * MAXVAL_COEFFICIENT, true);

        /*
         * 現在のページを表示する
         */
        String userName = null;
        int dispPoint = _dispPage * DISP_MAX - DISP_MAX;
        for (int i = 0; i < DISP_MAX; i++)
        {
            userName =
                    _dispData[dispPoint].getString(PCTUserWorkInquirySCHParams.USER_NAME).substring(0,
                            _dispData[dispPoint].getString(PCTUserWorkInquirySCHParams.USER_NAME).length());

            //            userName = _dispData[dispPoint].getUserName();
            // 折れ線グラフ(生産率)の値をセット(左側のメモリを表示するためのダミー)
            dummy_line.addGraph(userName, prodution);
            // 棒グラフ(ロット数)の値をセット
            vertical.addGraph(userName, (double)_dispData[dispPoint].getInt(PCTUserWorkInquirySCHParams.LOT_COUNT));
            // 折れ線グラフ(生産率)の値をセット
            Double addprodution =
                    new Double(_dispData[dispPoint].getString(PCTUserWorkInquirySCHParams.PRODUCTION_RATEVAL));
            line.addGraph(userName, addprodution.doubleValue());

            dispPoint++;
            if (dispPoint >= _dispData.length)
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
        vbc_VerticalChart.addDataset(vertical);
        // チャートに折れ線グラフ(生産率)のデータセットを追加
        vbc_VerticalChart.addDataset(line);

        // チャートイメージを作成
        vbc_VerticalChart.createChart(httpRequest);
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

        // initialize lst_UserWorkInquiry.
        _lcm_lst_UserWorkInquiry = new ListCellModel(lst_UserWorkInquiry, LST_USERWORKINQUIRY_KEYS, locale);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_LINE_NO, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_USER_ID, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_USER_NAME, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_PRODUCTION_RATE, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_LOT_COUNT, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_ORDER_QTY, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_LST_LINE_COUNT, false);
        _lcm_lst_UserWorkInquiry.setToolTipVisible(KEY_REAL_TIME, false);

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
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTUserWorkInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTUserWorkInquirySCH(conn, this.getClass(), locale, ui);

            // SCH call.
            List<Params> outparams = sch.query();
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            btn_PrevPage.setEnabled(false);
            btn_NextPage.setEnabled(false);

            // DFKLOOK:ここから修正
            if (outparams == null || outparams.size() == 0)
            {
                // グラフ初期化
                graphInit();
                return;
            }

            // 降順にソートする            
            _dispData = listSort(outparams);

            Double lotstandardval = new Double(_dispData[0].getString(PCTUserWorkInquirySCHParams.LOT_STANDARD_VALUE));
            lbl_StdLotNumberDisp.setText(WmsFormatter.getNumFormat(lotstandardval));

            /*
             * 最大ロット数の獲得
             */
            _lotMax = 0;
            for (int i = 0; i < _dispData.length; i++)
            {
                if (_dispData[i].getInt(PCTUserWorkInquirySCHParams.LOT_COUNT) > _lotMax)
                {
                    _lotMax = (double)_dispData[i].getInt(PCTUserWorkInquirySCHParams.LOT_COUNT);
                }
            }

            // 表示ページを１にする
            _dispPage = 1;

            // 取得データを画面コントロールに設定する
            setList();

            // グラフ表示
            graphDisp();

            // １ページ以上の場合、次ページボタンを有効化する
            if (_dispData.length > DISP_MAX)
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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // ボタンの無効をセットする
        btn_PrevPage.setEnabled(false);
        btn_NextPage.setEnabled(false);
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTUserWorkInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTUserWorkInquirySCH(conn, this.getClass(), locale, ui);

            // SCH call.
            List<Params> outparams = sch.query();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            if (outparams == null || outparams.size() == 0)
            {
                // グラフ初期化
                graphInit();
                return;
            }

            // 降順にソートする
            _dispData = listSort(outparams);

            Double lotstandardval = new Double(_dispData[0].getString(PCTUserWorkInquirySCHParams.LOT_STANDARD_VALUE));
            lbl_StdLotNumberDisp.setText(WmsFormatter.getNumFormat(lotstandardval));

            /*
             * 最大ロット数の獲得
             */
            _lotMax = 0;
            for (int i = 0; i < _dispData.length; i++)
            {
                if (_dispData[i].getInt(PCTUserWorkInquirySCHParams.LOT_COUNT) > _lotMax)
                {
                    _lotMax = (double)_dispData[i].getInt(PCTUserWorkInquirySCHParams.LOT_COUNT);
                }
            }

            // 表示ページを１にする
            _dispPage = 1;

            // 取得データを画面コントロールに設定する
            setList();

            // グラフ表示
            graphDisp();

            // １ページ以上の場合、次ページボタンを有効化する
            if (_dispData.length > DISP_MAX)
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
            // ボタンの無効をセットする
            btn_PrevPage.setEnabled(false);
            btn_NextPage.setEnabled(false);
            // リストセルをクリアする
            lst_UserWorkInquiry.clearRow();
            // DFKLOOK:ここまで修正
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    /**
     * 次ページボタンが押下されたときに呼ばれます。<BR>
     * <BR>
     * 概要： <DIR> 1.リストセルにユーザ毎の一覧情報を表示する<BR>
     * 　　　 <DIR> 2.グラフにユーザ毎の一覧情報を表示する<BR>
     * </DIR>
     * 
     * @param e ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_NextPage_Click_Process()
            throws Exception
    {
        try
        {
            // 表示ページを加算する
            _dispPage++;

            // 取得データを画面コントロールに設定する
            setList();

            // グラフ表示
            graphDisp();

            // これ以上ない場合、次ページボタンを無効化する
            if (_dispPage * DISP_MAX > _dispData.length)
            {
                btn_NextPage.setEnabled(false);
            }

            // 前ページボタンを有効化する
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
            lst_UserWorkInquiry.clearRow();
        }
    }

    /**
     * 前ページボタンが押下されたときに呼ばれます。<BR>
     * <BR>
     * 概要： <DIR> 1.リストセルにユーザ毎の一覧情報を表示する<BR>
     * 　　　 <DIR> 2.グラフにユーザ毎の一覧情報を表示する<BR>
     * </DIR>
     * 
     * @param e ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception 全ての例外を報告します。
     */
    private void btn_PrevPage_Click_Process()
            throws Exception
    {
        try
        {
            // 表示ページを減算する
            _dispPage--;

            // 取得データを画面コントロールに設定する
            setList();

            // グラフ表示
            graphDisp();

            // 先頭ページに戻った場合、前ページボタンを無効化する
            if (_dispPage == 1)
            {
                btn_PrevPage.setEnabled(false);
            }

            // 次ページボタンを有効化する
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
            lst_UserWorkInquiry.clearRow();
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

    /**
     * 降順に並べるためのコンパレータクラス
     */
    class ExmComparator
            implements Comparator
    {
        /**
         * 降順にソートします。<BR>
         * 
         * @param object1 ソートオブジェクト
         * @param object2 ソートオブジェクト
         * @return ソート結果
         */
        public int compare(Object object1, Object object2)
        {
            return ((Comparable)object1).compareTo(object2) * -1;
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
