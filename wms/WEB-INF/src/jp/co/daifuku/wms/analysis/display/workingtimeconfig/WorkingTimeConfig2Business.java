// $Id: WorkingTimeConfig2Business.java 7417 2010-03-06 05:42:21Z okayama $
package jp.co.daifuku.wms.analysis.display.workingtimeconfig;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;
import java.util.Date;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import java.math.BigDecimal;

import jp.co.daifuku.bluedog.ui.control.LineChartDataset;
import jp.co.daifuku.bluedog.ui.control.VerticalBarLineChartBaseDataset;
import jp.co.daifuku.bluedog.util.chartutil.ChartColor;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.Constants;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.analysis.schedule.AnalysisOutParameter;
import jp.co.daifuku.wms.analysis.schedule.WorkingTimeConfigSCH;
import jp.co.daifuku.wms.analysis.schedule.WorkingTimeConfigSCHParams;

/**
 * 作業実績の画面処理を行います。
 *
 * @version $Revision: 7417 $, $Date: 2010-03-06 14:42:21 +0900 (土, 06 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class WorkingTimeConfig2Business
        extends WorkingTimeConfig2
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
    public WorkingTimeConfig2Business()
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
     * 閉じるボタンが押された時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void btn_Close_U_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_U_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
     */
    public void vbc_Chart_Click(ActionEvent e)
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
     * カーソルを移動します。
     * @throws Exception All the exceptions are reported.
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_AveTimeItem);

    }

    /**
     * 画面の初期表示を行います。
     * @throws Exception All the exceptions are reported.
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_AveTimeItem.setValue(null);
        txt_AvePiece.setValue(null);
        txt_AveTimePiece.setValue(null);

        // DFKLOOK ここから追加
        // 商品毎平均作業時間～ピース毎平均時間フィールドのReadOnly化
        txt_AveTimeItem.setReadOnly(true);
        txt_AveTimePiece.setReadOnly(true);
        txt_AvePiece.setReadOnly(true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        WorkingTimeConfigSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new WorkingTimeConfigSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            WorkingTimeConfigSCHParams inparam = new WorkingTimeConfigSCHParams();

            // １画面から持ってきた値をパラメータにセットします
            String kindOfWork = viewState.getString(ViewStateKeys.KIND_OF_WORK);
            BigDecimal decSecItem = viewState.getBigDecimal(ViewStateKeys.STORAGE_SEC_PER_ITEM);
            String strSecItem;
            if (decSecItem != null)
            {
                strSecItem = String.valueOf(decSecItem.doubleValue());
            }
            else
            {
                strSecItem = "0";
            }
            BigDecimal decSecPiece = viewState.getBigDecimal(ViewStateKeys.STORAGE_SEC_PER_PIECE);
            String strSecPiece;
            if (decSecPiece != null)
            {
                strSecPiece = String.valueOf(decSecPiece.doubleValue());
            }
            else
            {
                strSecPiece = "0";
            }

            inparam.set(WorkingTimeConfigSCHParams.KIND_WORK, kindOfWork);

            // 入荷作業
            if (kindOfWork.equals(DispResources.getText("LBL-W1221")))
            {
                inparam.set(WorkingTimeConfigSCHParams.INSTOCK_SEC_PER_ITEM, strSecItem);
                inparam.set(WorkingTimeConfigSCHParams.INSTOCK_SEC_PER_PIECE, strSecPiece);
            }
            // 入庫作業
            else if (kindOfWork.equals(DispResources.getText("LBL-W1222")))
            {
                inparam.set(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_ITEM, strSecItem);
                inparam.set(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_PIECE, strSecPiece);
            }
            // 出庫作業
            else if (kindOfWork.equals(DispResources.getText("LBL-W1223")))
            {
                inparam.set(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_ITEM, strSecItem);
                inparam.set(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_PIECE, strSecPiece);
            }
            // 仕分作業
            else if (kindOfWork.equals(DispResources.getText("LBL-W1224")))
            {
                inparam.set(WorkingTimeConfigSCHParams.SORTING_SEC_PER_ITEM, strSecItem);
                inparam.set(WorkingTimeConfigSCHParams.SORTING_SEC_PER_PIECE, strSecPiece);

            }
            // 出荷作業
            else if (kindOfWork.equals(DispResources.getText("LBL-W1225")))
            {
                inparam.set(WorkingTimeConfigSCHParams.SHIPPING_SEC_PER_ITEM, strSecItem);
                inparam.set(WorkingTimeConfigSCHParams.SHIPPING_SEC_PER_PIECE, strSecPiece);
            }

            // 処理結果を取得
            AnalysisOutParameter anaParam = null;
           List<Params> lparams = sch.query(inparam);
            if (lparams != null)
            {
                anaParam = (AnalysisOutParameter)lparams.get(0);
            }

            // 該当データなし
            if (anaParam == null || anaParam.getSizeOfResultDataList() <= 0)
            {
                // メッセージをセット
                message.setMsgResourceKey(sch.getMessage());
                vbc_Chart.setVisible(false);
                return;
            }

            anaParam.setKindOfWork(kindOfWork);
            // グラフ描画
            setChartData(anaParam);

            // 平均値表示
            txt_AveTimeItem.setText(setScale(anaParam.getAveSecPerItem(), 2));
            txt_AvePiece.setText(setScale(anaParam.getAvePiecesPerItem(), 0));
            txt_AveTimePiece.setText(setScale(anaParam.getAveSecPerPiece(), 2));

            message.setMsgResourceKey(sch.getMessage());
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
        // DFKLOOK ここまで追加
    }

    /**
     *
     * @throws Exception All the exceptions are reported.
     */
    private void btn_Close_U_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

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

            // DFKLOOK ここから追加と変更
            // タイトルにランク表示を追加し、ListNameに変更
            String subTitle = viewState.getString(ViewStateKeys.KIND_OF_WORK);
            title = title + "(" + subTitle + ")";
            lbl_ListName.setResourceKey(title);
            // DFKLOOK ここまで追加と変更
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // リストボックスの場合はpage.xmlから取得する
            // DFKLOOK ここから追加と変更
            // タイトルにランク表示を追加し、ListNameに変更
            // lbl_SettingName.setResourceKey(this.getTitleResourceKey());
            String titleName = DispResources.getText(this.getTitleResourceKey());
            String subTitle = viewState.getString(ViewStateKeys.KIND_OF_WORK);
            titleName = titleName + "(" + subTitle + ")";
            lbl_ListName.setText(titleName);
            // DFKLOOK ここまで追加と変更
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // 2画面遷移から戻ってきた場合はViewStateから取得する
            // DFKLOOK ここから追加と変更
            // タイトルにランク表示を追加し、ListNameに変更
            // lbl_SettingName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
            String titleName = viewState.getString(Constants.M_TITLE_KEY);
            String subTitle = viewState.getString(ViewStateKeys.KIND_OF_WORK);
            titleName = titleName + "(" + subTitle + ")";
            lbl_ListName.setText(titleName);
            // DFKLOOK ここまで追加と変更
        }
    }

    // DFKLOOK ここから追加
    /**
     * 引数で指定されたDouble値を、指定小数点より後ろは四捨五入し、
     * 文字列として返却します。
     * 
     * @param db 指定値
     * @param scale 有効小数点
     * @return 四捨五入後の文字列
     */
    private String setScale(double db, int scale)
    {
        return String.valueOf((new BigDecimal(db)).setScale(scale, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * グラフの描画を行います。
     * @param param 実績グラフデータ
     * @throws IOException ディスクへの書込みに失敗したときスローされます。
     */
    private void setChartData(AnalysisOutParameter param)
            throws IOException
    {
        // メモリ最大値の設定
        // 総作業時間・予測結果
        double maxWorkTime = param.getMaxWorkingTime();
        if (maxWorkTime > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
        {
            maxWorkTime = WmsParam.MAX_NUMBER_OF_DISP_GRAPH;
        }
        else
        {
            maxWorkTime = maxWorkTime * 1.2;
        }

        // 商品数
        double maxItemQty = param.getMaxItemQty();
        if (maxItemQty > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
        {
            maxItemQty = WmsParam.MAX_NUMBER_OF_DISP_GRAPH;
        }
        else
        {
            maxItemQty = maxItemQty * 1.2;
        }

        // 総ピース数
        double maxPieceQty = param.getMaxTotalPieces();
        if (maxPieceQty > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
        {
            maxPieceQty = WmsParam.MAX_NUMBER_OF_DISP_GRAPH;
        }
        else
        {
            maxPieceQty = maxPieceQty * 1.2;
        }

        // 総作業時間
        VerticalBarLineChartBaseDataset dataset1 =
                new LineChartDataset(DispResources.getText("LBL-W1256"), ChartColor.ROYALBLUE,
                        param.getMinWorkingTime() * 0.8, maxWorkTime, true);
        // 商品数
        VerticalBarLineChartBaseDataset dataset2 =
                new LineChartDataset(DispResources.getText("LBL-W0129"), ChartColor.AQUAMARINE,
                        param.getMinItemQty() * 0.8, maxItemQty, true);
        // 総ピース数
        VerticalBarLineChartBaseDataset dataset3 =
                new LineChartDataset(DispResources.getText("LBL-W1258"), ChartColor.BROWN,
                        param.getMinTotalPieces() * 0.8, maxPieceQty, true);
        // 予測結果
        VerticalBarLineChartBaseDataset dataset4 =
                new LineChartDataset(DispResources.getText("LBL-W1257"), ChartColor.GREENYELLOW,
                        param.getMinWorkingTime() * 0.8, maxWorkTime, false);

        for (int i = 0; i < param.getSizeOfResultDataList(); i++)
        {
            Date dt = WmsFormatter.toDate(param.getWorkDateFromResultDataList(i));
            String strDate = WmsFormatter.toDispDate(dt, this.getHttpRequest().getLocale());
            // 総作業時間
            if (param.getTotalWorkingTimeFromResultDataList(i) > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
            {
                dataset1.addGraph(strDate, WmsParam.MAX_NUMBER_OF_DISP_GRAPH);
            }
            else
            {
                dataset1.addGraph(strDate, param.getTotalWorkingTimeFromResultDataList(i));
            }

            // 商品数
            if (param.getItemQtyFromResultDataList(i) > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
            {
                dataset2.addGraph(strDate, WmsParam.MAX_NUMBER_OF_DISP_GRAPH);
            }
            else
            {
                dataset2.addGraph(strDate, param.getItemQtyFromResultDataList(i));
            }

            // 総ピース数
            if (param.getTotalPiecesFromResultDataList(i) > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
            {
                dataset3.addGraph(strDate, WmsParam.MAX_NUMBER_OF_DISP_GRAPH);
            }
            else
            {
                dataset3.addGraph(strDate, param.getTotalPiecesFromResultDataList(i));
            }

            // 予測結果
            if (param.getSimuWorkingTimeFromResultDataList(i) > WmsParam.MAX_NUMBER_OF_DISP_GRAPH)
            {
                dataset4.addGraph(strDate, WmsParam.MAX_NUMBER_OF_DISP_GRAPH);
            }
            else
            {
                dataset4.addGraph(strDate, param.getSimuWorkingTimeFromResultDataList(i));
            }
        }

        // チャートにデータセットを追加
        vbc_Chart.clearDataset();
        vbc_Chart.addDataset(dataset1);
        vbc_Chart.addDataset(dataset2);
        vbc_Chart.addDataset(dataset3);
        vbc_Chart.addDataset(dataset4);

        // チャート生成
        try
        {
            vbc_Chart.createChart(httpRequest);
            vbc_Chart.setVisible(true);
        }
        catch (IllegalStateException e)
        {
            // 6006001 = 予期しないエラーが発生しました。{0}
            RmiMsgLogClient.write(6006001, LogMessage.F_ERROR, getClass().getName());
            throw e;
        }
        catch (IOException e)
        {
            // 6006001 = 予期しないエラーが発生しました。{0}
            RmiMsgLogClient.write(6006001, LogMessage.F_ERROR, getClass().getName());
            throw e;
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
