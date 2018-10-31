// $Id: XDProgressBusiness.java 7691 2010-03-19 08:06:02Z ota $
package jp.co.daifuku.wms.crossdock.display.progress;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.crossdock.schedule.XDInParameter;
import jp.co.daifuku.wms.crossdock.schedule.XDProgressSCH;
import jp.co.daifuku.wms.crossdock.schedule.XDProgressSCHParams;

/**
 * TC予定進捗の画面処理を行います。
 *
 * @version $Revision: 7691 $, $Date: 2010-03-19 17:06:02 +0900 (金, 19 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class XDProgressBusiness
        extends XDProgress
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_TcPlanProgress(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_TcPlanProgress(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress(LST_RECEIVE_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_RECEIVE_DETAIL_COUNT = new ListCellKey("LST_RECEIVE_DETAIL_COUNT", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress(LST_SORT_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_SORT_DETAIL_COUNT = new ListCellKey("LST_SORT_DETAIL_COUNT", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress(LST_RECEIVE_CASE_QTY) */
    private static final ListCellKey KEY_LST_RECEIVE_CASE_QTY = new ListCellKey("LST_RECEIVE_CASE_QTY", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress(LST_RECEIVE_PIECE_QTY) */
    private static final ListCellKey KEY_LST_RECEIVE_PIECE_QTY = new ListCellKey("LST_RECEIVE_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress(LST_SORT_CASE_QTY) */
    private static final ListCellKey KEY_LST_SORT_CASE_QTY = new ListCellKey("LST_SORT_CASE_QTY", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress(LST_SORT_PIECE_QTY) */
    private static final ListCellKey KEY_LST_SORT_PIECE_QTY = new ListCellKey("LST_SORT_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress(LST_SHORTAGE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY = new ListCellKey("LST_SHORTAGE_QTY", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress2(LST_PLAN_DAY_2) */
    private static final ListCellKey KEY_LST_PLAN_DAY_2 = new ListCellKey("LST_PLAN_DAY_2", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_TcPlanProgress2(LST_BATCH_NO_2) */
    private static final ListCellKey KEY_LST_BATCH_NO_2 = new ListCellKey("LST_BATCH_NO_2", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress2(LST_RECEIVE_DETAIL_COUNT_2) */
    private static final ListCellKey KEY_LST_RECEIVE_DETAIL_COUNT_2 = new ListCellKey("LST_RECEIVE_DETAIL_COUNT_2", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress2(LST_SORT_DETAIL_COUNT_2) */
    private static final ListCellKey KEY_LST_SORT_DETAIL_COUNT_2 = new ListCellKey("LST_SORT_DETAIL_COUNT_2", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress2(LST_RECEIVE_CASE_QTY_2) */
    private static final ListCellKey KEY_LST_RECEIVE_CASE_QTY_2 = new ListCellKey("LST_RECEIVE_CASE_QTY_2", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress2(LST_RECEIVE_PIECE_QTY_2) */
    private static final ListCellKey KEY_LST_RECEIVE_PIECE_QTY_2 = new ListCellKey("LST_RECEIVE_PIECE_QTY_2", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress2(LST_SORT_CASE_QTY_2) */
    private static final ListCellKey KEY_LST_SORT_CASE_QTY_2 = new ListCellKey("LST_SORT_CASE_QTY_2", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress2(LST_SORT_PIECE_QTY_2) */
    private static final ListCellKey KEY_LST_SORT_PIECE_QTY_2 = new ListCellKey("LST_SORT_PIECE_QTY_2", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress2(LST_SHORTAGE_QTY_2) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY_2 = new ListCellKey("LST_SHORTAGE_QTY_2", new StringCellColumn(), true, false);

    /** lst_TcPlanProgress kyes */
    private static final ListCellKey[] LST_TCPLANPROGRESS_KEYS = {
            KEY_LST_PLAN_DAY,
            KEY_LST_BATCH_NO,
            KEY_LST_RECEIVE_DETAIL_COUNT,
            KEY_LST_RECEIVE_CASE_QTY,
            KEY_LST_SORT_CASE_QTY,
            KEY_LST_SHORTAGE_QTY,
            KEY_LST_SORT_DETAIL_COUNT,
            KEY_LST_RECEIVE_PIECE_QTY,
            KEY_LST_SORT_PIECE_QTY,
    };

    /** lst_TcPlanProgress2 kyes */
    private static final ListCellKey[] LST_TCPLANPROGRESS2_KEYS = {
            KEY_LST_PLAN_DAY_2,
            KEY_LST_BATCH_NO_2,
            KEY_LST_RECEIVE_DETAIL_COUNT_2,
            KEY_LST_RECEIVE_CASE_QTY_2,
            KEY_LST_SORT_CASE_QTY_2,
            KEY_LST_SHORTAGE_QTY_2,
            KEY_LST_SORT_DETAIL_COUNT_2,
            KEY_LST_RECEIVE_PIECE_QTY_2,
            KEY_LST_SORT_PIECE_QTY_2,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel ProgressDisplay */
    private RadioButtonGroup _grp_ProgressDisplay;

    /** RadioButtonGroupModel Group */
    private RadioButtonGroup _grp_Group;

    /** ListCellModel lst_TcPlanProgress */
    private ListCellModel _lcm_lst_TcPlanProgress;

    /** ListCellModel lst_TcPlanProgress2 */
    private ListCellModel _lcm_lst_TcPlanProgress2;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public XDProgressBusiness()
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
    // DFKLOOK ここから
    /**
     * 定期送信時に呼び出されます。
     * @param e ActionEvent
     * @throws Exception 全ての例外を報告します。
     */
    public void page_ClientPull(ActionEvent e)
            throws Exception
    {
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        XDProgressSCHParams inParam = new XDProgressSCHParams(ui);

        //リストセル上段の情報をを基準とする
        inParam.set(XDProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        ListCellLine line = _lcm_lst_TcPlanProgress.get(1);
        if (lst_TcPlanProgress.getVisible())
        {
            inParam.set(XDProgressSCHParams.PLAN_DAY, WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
            inParam.set(XDProgressSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));

        }

        // 集約フラグ
        inParam.set(XDProgressSCHParams.COLLECT_FLAG, checkCollectFlag());

        // 処理フラグ(表示)
        inParam.set(XDProgressSCHParams.PROCESS_FLAG, XDInParameter.PROCESS_FLAG_VIEW);

        //進捗表示
        progressViewList(inParam);
    }

    /** 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_Auto_Click(ActionEvent e)
            throws Exception
    {
        rdo_Auto.setChecked(true);
        setRegularTransmission();
    }

    /** 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_Manual_Click(ActionEvent e)
            throws Exception
    {
        rdo_Manual.setChecked(true);
        setRegularTransmission();
    }

    /** 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_PlanDateBatchNoUnit_Click(ActionEvent e)
            throws Exception
    {
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        // スケジュールパラメータ
        XDProgressSCHParams inParam = new XDProgressSCHParams(ui);
        inParam.set(XDProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inParam.set(XDProgressSCHParams.COLLECT_FLAG, XDInParameter.COLLECT_BATCHNO_UNIT);
        inParam.set(XDProgressSCHParams.PROCESS_FLAG, XDInParameter.PROCESS_FLAG_VIEW);

        //進捗表示
        progressViewList(inParam);
    }

    /** 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void rdo_PlanDateUnit_Click(ActionEvent e)
            throws Exception
    {
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        // スケジュールパラメータ
        XDProgressSCHParams inParam = new XDProgressSCHParams(ui);
        inParam.set(XDProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inParam.set(XDProgressSCHParams.COLLECT_FLAG, XDInParameter.COLLECT_PLANDATE_UNIT);
        inParam.set(XDProgressSCHParams.PROCESS_FLAG, XDInParameter.PROCESS_FLAG_VIEW);

        // 進捗表示
        progressViewList(inParam);
    }

    // DFKLOOK ここまで
    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    // DFKLOOK ここから
    /**
     * 自動更新か手動更新かを切り替えます。
     */
    protected void setRegularTransmission()
    {
        // 定期送信フラグの切り替えを行います。
        if (rdo_Auto.getChecked())
        {
            setRegularTransmission(true);
        }
        else if (rdo_Manual.getChecked())
        {
            setRegularTransmission(false);
        }
    }

    /**
     * 集約チェックボックスの値により、データを返します。<BR>
     * @return 集約条件フラグ
     */
    protected String checkCollectFlag()
    {
        if (rdo_PlanDateUnit.getChecked())
        {
            return XDInParameter.COLLECT_PLANDATE_UNIT;
        }
        else if (rdo_PlanDateBatchNoUnit.getChecked())
        {
            return XDInParameter.COLLECT_BATCHNO_UNIT;
        }
        else
        {
            return null;
        }
    }

    /**
     * 作業進捗を画面へ表示します。<BR>
     * 作業進捗スケジュールクラスから表示用データを取得し、表示を行います。<BR>
     * @param inParam 検索パラメータ
     * @throws Exception 全ての例外を報告します。
     */
    protected void progressViewList(XDProgressSCHParams inParam)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        XDProgressSCH sch = null;
        try
        {
            conn = ConnectionManager.getSessionConnection(this);
            sch = new XDProgressSCH(conn, this.getClass(), locale, ui);

            // スケジュールを呼び出します。
            List<Params> outParam = sch.query(inParam);
            if (outParam == null || outParam.size() == 0)
            {
                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey("6003011");

                if (inParam.get(XDProgressSCHParams.PROCESS_FLAG).equals(XDInParameter.PROCESS_FLAG_PREVIOUS_PAGE))
                {
                    btn_PrevPage.setEnabled(false);
                }
                else if (inParam.get(XDProgressSCHParams.PROCESS_FLAG).equals(XDInParameter.PROCESS_FLAG_NEXT_PAGE))
                {
                    btn_NextPage.setEnabled(false);
                }
                else
                {
                    btn_PrevPage.setEnabled(false);
                    btn_NextPage.setEnabled(false);
                }


                return;
            }


            // 進捗グラフを表示します
            setViewData(outParam);
            // 表示しました。
            message.setMsgResourceKey("6001013");
        }
        catch (Exception e)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(e, this));
        }
        finally
        {
            try
            {
                // コネクションのクローズを行う
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (Exception ex2)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex2, this));
            }
        }
    }

    /**
     * リストセルデータ、グラフデータの初期化を行います。<BR>
     * @throws Exception 全ての例外を報告します。
     */
    protected void clearView()
            throws Exception
    {
        // リストセル(上段)
        lst_TcPlanProgress.clearRow();

        // リストセル(下段)
        lst_TcPlanProgress2.clearRow();

        // 入荷バーチャート(上段)
        hbc_TaskProgressReceiving.setValue(0);

        // 入荷バーチャート(下段)
        hbc_TaskProgressReceiving2.setValue(0);

        // 仕分バーチャート(上段)
        hbc_TaskProgressSort.setValue(0);

        // 仕分バーチャート(下段)
        hbc_TaskProgressSort2.setValue(0);

        // 入荷バーチャート(上段)
        hbc_TaskProgressReceiving.createChart(httpRequest);

        // 入荷バーチャート(下段)
        hbc_TaskProgressReceiving2.createChart(httpRequest);

        // 仕分バーチャート(上段)
        hbc_TaskProgressSort.createChart(httpRequest);

        // 入荷バーチャート(下段)
        hbc_TaskProgressSort2.createChart(httpRequest);

        // 入荷パーセントテキストボックス(上段)
        txt_ProgressRateReceiving.setText("0");

        // 入荷パーセントテキストボックス(下段)
        txt_ProgressRateReceiving2.setText("0");

        // 仕分パーセントテキストボックス(上段)
        txt_ProgressRateSort.setText("0");

        // 仕分パーセントテキストボックス(下段)
        txt_ProgressRateSort2.setText("0");
    }

    /**
     * パラメータを取得し、画面に表示します。<BR>
     * @param p グラフ、リストエリアに表示するパラメータ
     * @throws Exception 全ての例外を報告します。
     */
    protected void setViewData(List<Params> p)
            throws Exception
    {

        // 表示データを削除
        clearView();

        // 上段の表示
        visibleUpper(true);
        boolean first = true;
        for (Params outparam : p)
        {
            if (first)
            {

                ListCellLine line = _lcm_lst_TcPlanProgress.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(XDProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(XDProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_RECEIVE_DETAIL_COUNT, outparam.get(XDProgressSCHParams.RECEIVE_DETAIL_COUNT));
                line.setValue(KEY_LST_SORT_DETAIL_COUNT, outparam.get(XDProgressSCHParams.SORT_DETAIL_COUNT));
                line.setValue(KEY_LST_RECEIVE_CASE_QTY, outparam.get(XDProgressSCHParams.RECEIVE_CASE_QTY));
                line.setValue(KEY_LST_RECEIVE_PIECE_QTY, outparam.get(XDProgressSCHParams.RECEIVE_PIECE_QTY));
                line.setValue(KEY_LST_SORT_CASE_QTY, outparam.get(XDProgressSCHParams.SORT_CASE_QTY));
                line.setValue(KEY_LST_SORT_PIECE_QTY, outparam.get(XDProgressSCHParams.SORT_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(XDProgressSCHParams.SHORTAGE_QTY));

                // 入荷進捗率
                txt_ProgressRateReceiving.setText(outparam.getString(XDProgressSCHParams.RECEIVE_PROGRESS));
                // 仕分進捗率
                txt_ProgressRateSort.setText(outparam.getString(XDProgressSCHParams.SORT_PROGRESS));
                // 入荷進捗グラフ
                hbc_TaskProgressReceiving.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_TaskProgressReceiving.setValue(Double.valueOf(outparam.getString(XDProgressSCHParams.RECEIVE_PROGRESS)));
                hbc_TaskProgressReceiving.createChart(httpRequest);
                // 仕分進捗グラフ
                hbc_TaskProgressSort.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_TaskProgressSort.setValue(Double.valueOf(outparam.getString(XDProgressSCHParams.SORT_PROGRESS)));
                hbc_TaskProgressSort.createChart(httpRequest);
                visibleLower(false);

                lst_TcPlanProgress_SetLineToolTip(line);
                _lcm_lst_TcPlanProgress.add(line);
                first = false;
            }
            else
            {
                visibleLower(true);
                ListCellLine line = _lcm_lst_TcPlanProgress2.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY_2, outparam.get(XDProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO_2, outparam.get(XDProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_RECEIVE_DETAIL_COUNT_2, outparam.get(XDProgressSCHParams.RECEIVE_DETAIL_COUNT));
                line.setValue(KEY_LST_SORT_DETAIL_COUNT_2, outparam.get(XDProgressSCHParams.SORT_DETAIL_COUNT));
                line.setValue(KEY_LST_RECEIVE_CASE_QTY_2, outparam.get(XDProgressSCHParams.RECEIVE_CASE_QTY));
                line.setValue(KEY_LST_RECEIVE_PIECE_QTY_2, outparam.get(XDProgressSCHParams.RECEIVE_PIECE_QTY));
                line.setValue(KEY_LST_SORT_CASE_QTY_2, outparam.get(XDProgressSCHParams.SORT_CASE_QTY));
                line.setValue(KEY_LST_SORT_PIECE_QTY_2, outparam.get(XDProgressSCHParams.SORT_PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY_2, outparam.get(XDProgressSCHParams.SHORTAGE_QTY));

                // 入荷進捗率
                txt_ProgressRateReceiving2.setText(outparam.getString(XDProgressSCHParams.RECEIVE_PROGRESS));
                // 仕分進捗率
                txt_ProgressRateSort2.setText(outparam.getString(XDProgressSCHParams.SORT_PROGRESS));
                // 入荷進捗グラフ
                hbc_TaskProgressReceiving2.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_TaskProgressReceiving2.setValue(Double.valueOf(outparam.getString(XDProgressSCHParams.RECEIVE_PROGRESS)));
                hbc_TaskProgressReceiving2.createChart(httpRequest);
                // 仕分進捗グラフ
                hbc_TaskProgressSort2.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_TaskProgressSort2.setValue(Double.valueOf(outparam.getString(XDProgressSCHParams.SORT_PROGRESS)));
                hbc_TaskProgressSort2.createChart(httpRequest);

                lst_TcPlanProgress2_SetLineToolTip(line);
                _lcm_lst_TcPlanProgress2.add(line);
            }

            viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            viewState.setObject(ViewStateKeys.GROUP, _grp_Group.getSelectedValue());
            viewState.setObject(ViewStateKeys.PROGRESS_RATE_RECEIVING, txt_ProgressRateReceiving.getValue());
            viewState.setObject(ViewStateKeys.PROGRESS_RATE_SORT, txt_ProgressRateSort.getValue());
            viewState.setObject(ViewStateKeys.PROGRESS_RATE_RECEIVING2, txt_ProgressRateReceiving2.getValue());
            viewState.setObject(ViewStateKeys.PROGRESS_RATE_SORT2, txt_ProgressRateSort2.getValue());

            // 前頁ボタン制御
            btn_PrevPage.setEnabled(outparam.getBoolean(XDProgressSCHParams.ENABLED_PREV_FLAG));

            // 次頁ボタン制御
            btn_NextPage.setEnabled(outparam.getBoolean(XDProgressSCHParams.ENABLED_NEXT_FLAG));

        }

    }

    /**
     * 上段項目の表示の可否を設定します
     * @param isVisible true - 表示 false - 非表示
     */
    protected void visibleUpper(boolean isVisible)
    {
        // リストセル(上段)
        lst_TcPlanProgress.setVisible(isVisible);

        // 入荷バーチャート(上段)
        hbc_TaskProgressReceiving.setVisible(isVisible);

        // 仕分バーチャート(上段)
        hbc_TaskProgressSort.setVisible(isVisible);

        // 入荷パーセントラベル(上段)
        lbl_PercentReceiving.setVisible(isVisible);

        // 仕分パーセントラベル(上段)
        lbl_PercentSort.setVisible(isVisible);

        // 入荷ラベル(上段)
        lbl_Receiving.setVisible(isVisible);

        // 仕分ラベル(上段)
        lbl_Sort.setVisible(isVisible);

        // 入荷パーセントテキストボックス(上段)
        txt_ProgressRateReceiving.setVisible(isVisible);

        // 仕分パーセントテキストボックス(上段)
        txt_ProgressRateSort.setVisible(isVisible);
    }

    /**
     * 下段項目の表示の可否を設定します
     * @param isVisible true - 表示 false - 非表示
     */
    protected void visibleLower(boolean isVisible)
    {
        // リストセル(下段)
        lst_TcPlanProgress2.setVisible(isVisible);

        // 入荷バーチャート(下段)
        hbc_TaskProgressReceiving2.setVisible(isVisible);

        // 仕分バーチャート(下段)
        hbc_TaskProgressSort2.setVisible(isVisible);

        // 入荷パーセントラベル(下段)
        lbl_PercentReceiving2.setVisible(isVisible);

        // 仕分パーセントラベル(下段)
        lbl_PercentSort2.setVisible(isVisible);

        // 入荷ラベル(下段)
        lbl_Receiving2.setVisible(isVisible);

        // 仕分ラベル(下段)
        lbl_Sort2.setVisible(isVisible);

        // 入荷パーセントテキストボックス(下段)
        txt_ProgressRateReceiving2.setVisible(isVisible);

        // 仕分パーセントテキストボックス(下段)
        txt_ProgressRateSort2.setVisible(isVisible);
    }

    // DFKLOOK ここまで

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

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[] {
                rdo_Auto,
                rdo_Manual
        }, locale);

        // initialize Group.
        _grp_Group = new RadioButtonGroup(new RadioButton[] {
                rdo_PlanDateBatchNoUnit,
                rdo_PlanDateUnit
        }, locale);

        // initialize lst_TcPlanProgress.
        _lcm_lst_TcPlanProgress = new ListCellModel(lst_TcPlanProgress, LST_TCPLANPROGRESS_KEYS, locale);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_PLAN_DAY, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_RECEIVE_DETAIL_COUNT, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_SORT_DETAIL_COUNT, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_RECEIVE_CASE_QTY, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_RECEIVE_PIECE_QTY, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_SORT_CASE_QTY, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_SORT_PIECE_QTY, false);
        _lcm_lst_TcPlanProgress.setToolTipVisible(KEY_LST_SHORTAGE_QTY, false);

        // initialize lst_TcPlanProgress2.
        _lcm_lst_TcPlanProgress2 = new ListCellModel(lst_TcPlanProgress2, LST_TCPLANPROGRESS2_KEYS, locale);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_PLAN_DAY_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_BATCH_NO_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_RECEIVE_DETAIL_COUNT_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_SORT_DETAIL_COUNT_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_RECEIVE_CASE_QTY_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_RECEIVE_PIECE_QTY_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_SORT_CASE_QTY_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_SORT_PIECE_QTY_2, false);
        _lcm_lst_TcPlanProgress2.setToolTipVisible(KEY_LST_SHORTAGE_QTY_2, false);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_TcPlanProgress_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_TcPlanProgress2_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // DFKLOOK ここから
        if (rdo_Auto.getChecked())
        {
            // set focus.
            setFocus(rdo_Auto);
        }
        else
        {
            // set focus.
            setFocus(rdo_Manual);
        }
        // DFKLOOK ここまで

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // DFKLOOK ここから
        // open connection.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        XDProgressSCHParams inParam = new XDProgressSCHParams(ui);


        // 頁切り替えボタン
        // A button to switch pages
        btn_PrevPage.setEnabled(false);
        btn_NextPage.setEnabled(false);
        // clear.
        rdo_Auto.setChecked(true);
        setFocus(rdo_Auto);
        rdo_PlanDateBatchNoUnit.setChecked(true);
        txt_ProgressRateReceiving.setReadOnly(true);
        txt_ProgressRateSort.setReadOnly(true);
        txt_ProgressRateReceiving2.setReadOnly(true);
        txt_ProgressRateSort2.setReadOnly(true);
        visibleUpper(false);
        visibleLower(false);


        // 定期送信の停止
        setRegularTransmission(false);

        inParam.set(XDProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inParam.set(XDProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
        inParam.set(XDProgressSCHParams.PROCESS_FLAG, XDInParameter.PROCESS_FLAG_VIEW);

        // ViewStateに入力項目をセットする
        getViewState().setBoolean(ViewStateKeys.VIEW_STATE_VIEW_AUTO, rdo_Auto.getChecked());
        getViewState().setString(ViewStateKeys.VIEW_STATE_COLLECT, XDInParameter.COLLECT_BATCHNO_UNIT);

        // 進捗表示
        progressViewList(inParam);

        // 定期送信の開始
        setRegularTransmission(true);
        // DFKLOOK ここまで


    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        rdo_Auto.validate(false);
        rdo_PlanDateBatchNoUnit.validate(false);
        txt_ProgressRateReceiving.validate(this, false);
        txt_ProgressRateSort.validate(this, false);
        txt_ProgressRateReceiving2.validate(this, false);
        txt_ProgressRateSort2.validate(this, false);

        // DFKLOOK ここから
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        XDProgressSCHParams inParam = new XDProgressSCHParams(ui);

        inParam.set(XDProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        // リストセル上段を基準とする
        ListCellLine line = _lcm_lst_TcPlanProgress.get(1);
        if (lst_TcPlanProgress.getVisible())
        {
            inParam.set(XDProgressSCHParams.PLAN_DAY, WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
            inParam.set(XDProgressSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
        }
        // 集約フラグ
        inParam.set(XDProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
        // 処理フラグ(表示)
        inParam.set(XDProgressSCHParams.PROCESS_FLAG, XDInParameter.PROCESS_FLAG_VIEW);

        // 進捗表示
        progressViewList(inParam);
        // DFKLOOK ここまで
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PrevPage_Click_Process()
            throws Exception
    {
        // input validation.
        rdo_Auto.validate(false);
        rdo_PlanDateBatchNoUnit.validate(false);
        txt_ProgressRateReceiving.validate(this, false);
        txt_ProgressRateSort.validate(this, false);
        txt_ProgressRateReceiving2.validate(this, false);
        txt_ProgressRateSort2.validate(this, false);

        // DFKLOOK ここから
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        XDProgressSCHParams inParam = new XDProgressSCHParams(ui);

        inParam.set(XDProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        // リストセル上段を基準とする
        ListCellLine line = _lcm_lst_TcPlanProgress.get(1);
        inParam.set(XDProgressSCHParams.PLAN_DAY, WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
        inParam.set(XDProgressSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
        // 集約フラグ
        inParam.set(XDProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
        // 処理フラグ(前頁)
        inParam.set(XDProgressSCHParams.PROCESS_FLAG, XDInParameter.PROCESS_FLAG_PREVIOUS_PAGE);
        // 進捗表示
        progressViewList(inParam);
        // DFKLOOK ここまで
    }

    /**
     *
     * @throws Exception
     */
    private void btn_NextPage_Click_Process()
            throws Exception
    {
        // input validation.
        rdo_Auto.validate(false);
        rdo_PlanDateBatchNoUnit.validate(false);
        txt_ProgressRateReceiving.validate(this, false);
        txt_ProgressRateSort.validate(this, false);
        txt_ProgressRateReceiving2.validate(this, false);
        txt_ProgressRateSort2.validate(this, false);

        // DFKLOOK ここから
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        XDProgressSCHParams inParam = new XDProgressSCHParams(ui);

        inParam.set(XDProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        ListCellLine line = _lcm_lst_TcPlanProgress.get(1);
        ListCellLine line2 = _lcm_lst_TcPlanProgress2.get(1);
        if (lst_TcPlanProgress2.getVisible())
        {
            // リストセル下段のデータを基準とする
            inParam.set(XDProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)line2.getValue(KEY_LST_PLAN_DAY_2)));
            inParam.set(XDProgressSCHParams.BATCH_NO, line2.getValue(KEY_LST_BATCH_NO_2));
        }
        else
        {
            // 下段のデータが存在しない場合、上段を基準とする
            inParam.set(XDProgressSCHParams.PLAN_DAY, WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
            inParam.set(XDProgressSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
        }

        // 集約フラグ
        inParam.set(XDProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
        // 処理フラグ(次頁)
        inParam.set(XDProgressSCHParams.PROCESS_FLAG, XDInParameter.PROCESS_FLAG_NEXT_PAGE);

        // 進捗表示
        progressViewList(inParam);
        // DFKLOOK ここまで
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
