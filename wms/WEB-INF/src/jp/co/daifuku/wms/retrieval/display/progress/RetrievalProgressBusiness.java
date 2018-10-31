// $Id: RetrievalProgressBusiness.java 7674 2010-03-18 00:40:51Z okayama $
package jp.co.daifuku.wms.retrieval.display.progress;

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
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalOutParameter;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalProgressSCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalProgressSCHParams;

/**
 * 出庫作業進捗の画面処理を行います。
 *
 * @version $Revision: 7674 $, $Date: 2010-03-18 09:40:51 +0900 (木, 18 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
@SuppressWarnings("serial")
public class RetrievalProgressBusiness
        extends RetrievalProgress
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_RetrievalProgress1(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_RetrievalProgress1(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_ORDER_COUNT) */
    private static final ListCellKey KEY_LST_ORDER_COUNT = new ListCellKey("LST_ORDER_COUNT", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_TICKET_COUNT) */
    private static final ListCellKey KEY_LST_TICKET_COUNT = new ListCellKey("LST_TICKET_COUNT", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT = new ListCellKey("LST_DETAIL_COUNT", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_RETRIEVAL_COUNT) */
    private static final ListCellKey KEY_LST_RETRIEVAL_COUNT = new ListCellKey("LST_RETRIEVAL_COUNT", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_CASE_QTY) */
    private static final ListCellKey KEY_LST_CASE_QTY = new ListCellKey("LST_CASE_QTY", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PIECE_QTY = new ListCellKey("LST_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress1(LST_SHORTAGE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY = new ListCellKey("LST_SHORTAGE_QTY", new NumberCellColumn(0), true, false);

    /** lst_RetrievalProgress2(LST_PLAN_DAY_2) */
    private static final ListCellKey KEY_LST_PLAN_DAY_2 = new ListCellKey("LST_PLAN_DAY_2", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_RetrievalProgress2(LST_BATCH_NO_2) */
    private static final ListCellKey KEY_LST_BATCH_NO_2 = new ListCellKey("LST_BATCH_NO_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_ORDER_COUNT_2) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_2 = new ListCellKey("LST_ORDER_COUNT_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_TICKET_COUNT_2) */
    private static final ListCellKey KEY_LST_TICKET_COUNT_2 = new ListCellKey("LST_TICKET_COUNT_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_DETAIL_COUNT_2) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT_2 = new ListCellKey("LST_DETAIL_COUNT_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_RETRIEVAL_COUNT_2) */
    private static final ListCellKey KEY_LST_RETRIEVAL_COUNT_2 = new ListCellKey("LST_RETRIEVAL_COUNT_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_CASE_QTY_2) */
    private static final ListCellKey KEY_LST_CASE_QTY_2 = new ListCellKey("LST_CASE_QTY_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_PIECE_QTY_2) */
    private static final ListCellKey KEY_LST_PIECE_QTY_2 = new ListCellKey("LST_PIECE_QTY_2", new StringCellColumn(), true, false);

    /** lst_RetrievalProgress2(LST_SHORTAGE_QTY_2) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY_2 = new ListCellKey("LST_SHORTAGE_QTY_2", new NumberCellColumn(0), true, false);

    /** lst_RetrievalProgress1 keys */
    private static final ListCellKey[] LST_RETRIEVALPROGRESS1_KEYS = {
        KEY_LST_PLAN_DAY,
        KEY_LST_BATCH_NO,
        KEY_LST_ORDER_COUNT,
        KEY_LST_DETAIL_COUNT,
        KEY_LST_CASE_QTY,
        KEY_LST_SHORTAGE_QTY,
        KEY_LST_TICKET_COUNT,
        KEY_LST_RETRIEVAL_COUNT,
        KEY_LST_PIECE_QTY,
    };

    /** lst_RetrievalProgress2 keys */
    private static final ListCellKey[] LST_RETRIEVALPROGRESS2_KEYS = {
        KEY_LST_PLAN_DAY_2,
        KEY_LST_BATCH_NO_2,
        KEY_LST_ORDER_COUNT_2,
        KEY_LST_DETAIL_COUNT_2,
        KEY_LST_CASE_QTY_2,
        KEY_LST_SHORTAGE_QTY_2,
        KEY_LST_TICKET_COUNT_2,
        KEY_LST_RETRIEVAL_COUNT_2,
        KEY_LST_PIECE_QTY_2,
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

    /** ListCellModel lst_RetrievalProgress1 */
    private ListCellModel _lcm_lst_RetrievalProgress1;

    /** ListCellModel lst_RetrievalProgress2 */
    private ListCellModel _lcm_lst_RetrievalProgress2;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalProgressBusiness()
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
    public void page_ClientPull(ActionEvent e)
            throws Exception
    {
        RetrievalProgressSCHParams inParam = new RetrievalProgressSCHParams();

        inParam.set(RetrievalProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
        inParam.set(RetrievalProgressSCHParams.PROCESS_FLAG, RetrievalInParameter.PROCESS_FLAG_VIEW);

        //リストセル上段の予定日を基準日とする。
        inParam.set(RetrievalProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        ListCellLine line = _lcm_lst_RetrievalProgress1.get(1);
        if (lst_RetrievalProgress1.getVisible())
        {
            inParam.set(RetrievalProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
            inParam.set(RetrievalProgressSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
        }
        // 集約フラグ
        inParam.set(RetrievalProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
        // 処理フラグ(表示)
        inParam.set(RetrievalProgressSCHParams.PROCESS_FLAG, RetrievalInParameter.PROCESS_FLAG_VIEW);

        //進捗表示
        progressViewList(inParam);
    }

    /**
     *
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
     *
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
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_BatchNoUnit_Click(ActionEvent e)
            throws Exception
    {
        RetrievalProgressSCHParams inParam = new RetrievalProgressSCHParams();
        inParam.set(RetrievalProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inParam.set(RetrievalProgressSCHParams.COLLECT_FLAG, RetrievalInParameter.COLLECT_BATCHNO_UNIT);
        inParam.set(RetrievalProgressSCHParams.PROCESS_FLAG, RetrievalInParameter.PROCESS_FLAG_VIEW);

        //進捗表示
        progressViewList(inParam);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_PlanDateUnit_Click(ActionEvent e)
            throws Exception
    {
        RetrievalProgressSCHParams inParam = new RetrievalProgressSCHParams();
        inParam.set(RetrievalProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inParam.set(RetrievalProgressSCHParams.COLLECT_FLAG, RetrievalInParameter.COLLECT_PLANDATE_UNIT);
        inParam.set(RetrievalProgressSCHParams.PROCESS_FLAG, RetrievalInParameter.PROCESS_FLAG_VIEW);

        //進捗表示
        progressViewList(inParam);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_AllPlanDate_Click(ActionEvent e)
            throws Exception
    {
        RetrievalProgressSCHParams inParam = new RetrievalProgressSCHParams();
        inParam.set(RetrievalProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inParam.set(RetrievalProgressSCHParams.COLLECT_FLAG, RetrievalInParameter.COLLECT_ALL_PLANDATE);
        inParam.set(RetrievalProgressSCHParams.PROCESS_FLAG, RetrievalInParameter.PROCESS_FLAG_VIEW);

        //進捗表示
        progressViewList(inParam);
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
    // DFKLOOK ここから
    /**
     * 作業進捗を画面へ表示します。<BR>
     * 作業進捗スケジュールクラスから表示用データを取得し、表示を行います。<BR>
     * @param inParam 検索パラメータ
     * @throws Exception 全ての例外を報告します。
     */
    protected void progressViewList(RetrievalProgressSCHParams inParam)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalProgressSCH sch = null;
        try
        {
            conn = ConnectionManager.getSessionConnection(this);
            sch = new RetrievalProgressSCH(conn, this.getClass(), locale, ui);

            // スケジュールを呼び出します。
            List<Params> outParam = sch.query(inParam);
            if (outParam == null || outParam.size() == 0)
            {
                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey("6003011");

                if (inParam.get(RetrievalProgressSCHParams.PROCESS_FLAG).equals(
                        RetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE))
                {
                    btn_PrevPage.setEnabled(false);
                }
                else if (inParam.get(RetrievalProgressSCHParams.PROCESS_FLAG).equals(
                        RetrievalInParameter.PROCESS_FLAG_NEXT_PAGE))
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

            //進捗グラフを表示します
            setViewData(outParam);
            // 表示しました。
            message.setMsgResourceKey("6001013");

        }
        catch (Exception e)
        {
            e.printStackTrace();
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
     * パラメータを取得し、画面に表示します。<BR>
     * @param p グラフ、リストエリアに表示するパラメータ
     * @throws Exception 全ての例外を報告します。
     */
    protected void setViewData(List<Params> p)
            throws Exception
    {
        // 表示データを削除
        clearView();

        lst_RetrievalProgress1.setVisible(true);
        hbc_RetrievalTask1.setVisible(true);
        txt_ProgressRate1.setVisible(true);
        lbl_Percent1.setVisible(true);

        lst_RetrievalProgress1.clearRow();
        lst_RetrievalProgress2.clearRow();

        // output display.
        boolean first = true;
        for (Params outparam : p)
        {
            if (first)
            {
                ListCellLine line = _lcm_lst_RetrievalProgress1.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(RetrievalProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(RetrievalProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_COUNT, outparam.get(RetrievalProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_TICKET_COUNT, outparam.get(RetrievalProgressSCHParams.TICKET_COUNT));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(RetrievalProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_RETRIEVAL_COUNT, outparam.get(RetrievalProgressSCHParams.RETRIEVAL_COUNT));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(RetrievalProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(RetrievalProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(RetrievalProgressSCHParams.SHORTAGE_QTY));

                hbc_RetrievalTask1.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_RetrievalTask1.setValue(Double.valueOf(outparam.getString(RetrievalProgressSCHParams.PROGRESS_RATE1)));
                hbc_RetrievalTask1.createChart(httpRequest);
                txt_ProgressRate1.setText(String.valueOf(outparam.getString(RetrievalProgressSCHParams.PROGRESS_RATE1)));
                lst_RetrievalProgress1_SetLineToolTip(line);
                _lcm_lst_RetrievalProgress1.add(line);
                lst_RetrievalProgress2.setVisible(false);
                hbc_RetrievalTask2.setVisible(false);
                txt_ProgressRate2.setVisible(false);
                lbl_Percent2.setVisible(false);
                first = false;
            }
            else
            {
                lst_RetrievalProgress2.setVisible(true);
                hbc_RetrievalTask2.setVisible(true);
                txt_ProgressRate2.setVisible(true);
                lbl_Percent2.setVisible(true);

                ListCellLine line = _lcm_lst_RetrievalProgress2.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY_2, outparam.get(RetrievalProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_BATCH_NO_2, outparam.get(RetrievalProgressSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_COUNT_2, outparam.get(RetrievalProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_TICKET_COUNT_2, outparam.get(RetrievalProgressSCHParams.TICKET_COUNT));
                line.setValue(KEY_LST_DETAIL_COUNT_2, outparam.get(RetrievalProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_RETRIEVAL_COUNT_2, outparam.get(RetrievalProgressSCHParams.RETRIEVAL_COUNT));
                line.setValue(KEY_LST_CASE_QTY_2, outparam.get(RetrievalProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY_2, outparam.get(RetrievalProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY_2, outparam.get(RetrievalProgressSCHParams.SHORTAGE_QTY));

                hbc_RetrievalTask2.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_RetrievalTask2.setValue(Double.valueOf(outparam.getString(RetrievalProgressSCHParams.PROGRESS_RATE1)));
                hbc_RetrievalTask2.createChart(httpRequest);
                txt_ProgressRate2.setText(outparam.getString(RetrievalProgressSCHParams.PROGRESS_RATE1));

                lst_RetrievalProgress2_SetLineToolTip(line);
                _lcm_lst_RetrievalProgress2.add(line);
            }

            viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            viewState.setObject(ViewStateKeys.GROUP, _grp_Group.getSelectedValue());
            viewState.setObject(ViewStateKeys.PROGRESS_RATE1, txt_ProgressRate1.getValue());
            viewState.setObject(ViewStateKeys.PROGRESS_RATE2, txt_ProgressRate2.getValue());

            //ボタン制御
            if (RetrievalOutParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(outparam.get(RetrievalProgressSCHParams.BUTTON_CONTROL_FLAG)))
            {
                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (RetrievalOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(outparam.get(RetrievalProgressSCHParams.BUTTON_CONTROL_FLAG)))
            {
                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (RetrievalOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(outparam.get(RetrievalProgressSCHParams.BUTTON_CONTROL_FLAG)))
            {
                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (RetrievalOutParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(outparam.get(RetrievalProgressSCHParams.BUTTON_CONTROL_FLAG)))
            {
                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(true);
            }

        }
    }

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
        if (rdo_AllPlanDate.getChecked())
        {
            return RetrievalInParameter.COLLECT_ALL_PLANDATE;
        }
        else if (rdo_PlanDateUnit.getChecked())
        {
            return RetrievalInParameter.COLLECT_PLANDATE_UNIT;
        }
        else if (rdo_BatchNoUnit.getChecked())
        {
            return RetrievalInParameter.COLLECT_BATCHNO_UNIT;
        }
        else
        {
            return null;
        }
    }

    /**
     * リストセルデータ、グラフデータの初期化を行います。<BR>
     * @throws Exception 全ての例外を報告します。
     */
    protected void clearView()
            throws Exception
    {
        lst_RetrievalProgress1.clearRow();
        lst_RetrievalProgress2.clearRow();
        hbc_RetrievalTask1.setValue(0);
        hbc_RetrievalTask2.setValue(0);
        hbc_RetrievalTask1.createChart(httpRequest);
        hbc_RetrievalTask2.createChart(httpRequest);
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
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[]{rdo_Auto, rdo_Manual}, locale);

        // initialize Group.
        _grp_Group = new RadioButtonGroup(new RadioButton[]{rdo_BatchNoUnit, rdo_PlanDateUnit, rdo_AllPlanDate}, locale);

        // initialize lst_RetrievalProgress1.
        _lcm_lst_RetrievalProgress1 = new ListCellModel(lst_RetrievalProgress1, LST_RETRIEVALPROGRESS1_KEYS, locale);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_PLAN_DAY, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_ORDER_COUNT, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_TICKET_COUNT, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_DETAIL_COUNT, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_RETRIEVAL_COUNT, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_CASE_QTY, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_PIECE_QTY, false);
        _lcm_lst_RetrievalProgress1.setToolTipVisible(KEY_LST_SHORTAGE_QTY, false);

        // initialize lst_RetrievalProgress2.
        _lcm_lst_RetrievalProgress2 = new ListCellModel(lst_RetrievalProgress2, LST_RETRIEVALPROGRESS2_KEYS, locale);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_PLAN_DAY_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_BATCH_NO_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_ORDER_COUNT_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_TICKET_COUNT_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_DETAIL_COUNT_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_RETRIEVAL_COUNT_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_CASE_QTY_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_PIECE_QTY_2, false);
        _lcm_lst_RetrievalProgress2.setToolTipVisible(KEY_LST_SHORTAGE_QTY_2, false);

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
    @SuppressWarnings("all")
    private void dispose()
            throws Exception
    {
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_RetrievalProgress1_SetLineToolTip(ListCellLine line)
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
    private void lst_RetrievalProgress2_SetLineToolTip(ListCellLine line)
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
        // clear.
        setRegularTransmission(false);
        rdo_Auto.setChecked(true);
        setFocus(rdo_Auto);
        rdo_BatchNoUnit.setChecked(true);
        btn_Display.setEnabled(true);
        btn_PrevPage.setEnabled(false);
        btn_NextPage.setEnabled(false);
        lst_RetrievalProgress1.setVisible(false);
        lst_RetrievalProgress2.setVisible(false);
        hbc_RetrievalTask1.setVisible(false);
        hbc_RetrievalTask2.setVisible(false);
        txt_ProgressRate1.setVisible(false);
        txt_ProgressRate1.setText("");
        txt_ProgressRate2.setVisible(false);
        txt_ProgressRate2.setText("");
        lbl_Percent1.setVisible(false);
        lbl_Percent2.setVisible(false);

        // スケジュールパラメータ
        RetrievalProgressSCHParams inParam = new RetrievalProgressSCHParams();
        inParam.set(RetrievalProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inParam.set(RetrievalProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
        inParam.set(RetrievalProgressSCHParams.PROCESS_FLAG, RetrievalInParameter.PROCESS_FLAG_VIEW);

        //進捗表示
        progressViewList(inParam);

        // set focus.
        setFocus(rdo_Auto);

        setRegularTransmission();

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
        rdo_BatchNoUnit.validate(false);
        txt_ProgressRate1.validate(this, false);
        txt_ProgressRate2.validate(this, false);

        // DFKLOOK ここから
        //上段予定日を基準日にセットする
        // Sets a planned date on an upper section to a standard date.
        RetrievalProgressSCHParams inParam = new RetrievalProgressSCHParams();
        inParam.set(RetrievalProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        ListCellLine line = _lcm_lst_RetrievalProgress1.get(1);
        if (lst_RetrievalProgress1.getVisible())
        {
            inParam.set(RetrievalProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
            inParam.set(RetrievalProgressSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
        }
        // 集約フラグ
        inParam.set(RetrievalProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
        // 処理フラグ(表示)
        inParam.set(RetrievalProgressSCHParams.PROCESS_FLAG, RetrievalInParameter.PROCESS_FLAG_VIEW);

        //進捗表示
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
        rdo_BatchNoUnit.validate(false);
        txt_ProgressRate1.validate(this, false);
        txt_ProgressRate2.validate(this, false);

        // DFKLOOK ここから
        RetrievalProgressSCHParams inParam = new RetrievalProgressSCHParams();

        inParam.set(RetrievalProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        //リストセル上段の予定日を基準日とする。
        ListCellLine line = _lcm_lst_RetrievalProgress1.get(1);
        inParam.set(RetrievalProgressSCHParams.PLAN_DAY,
                WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
        inParam.set(RetrievalProgressSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
        // 集約フラグ
        inParam.set(RetrievalProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
        // 処理フラグ(表示)
        inParam.set(RetrievalProgressSCHParams.PROCESS_FLAG, RetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE);

        //進捗表示
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
        rdo_BatchNoUnit.validate(false);
        txt_ProgressRate1.validate(this, false);
        txt_ProgressRate2.validate(this, false);

        // DFKLOOK ここから
        RetrievalProgressSCHParams inParam = new RetrievalProgressSCHParams();

        inParam.set(RetrievalProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        ListCellLine line = _lcm_lst_RetrievalProgress1.get(1);
        ListCellLine line2 = _lcm_lst_RetrievalProgress2.get(1);
        if (lst_RetrievalProgress2.getVisible())
        {
            //リストセル下段のデータを基準とします。
            inParam.set(RetrievalProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)line2.getValue(KEY_LST_PLAN_DAY_2)));
            inParam.set(RetrievalProgressSCHParams.BATCH_NO, line2.getValue(KEY_LST_BATCH_NO_2));
        }
        else
        {
            // 下段のデータが存在しない場合、上段を基準とします。
            inParam.set(RetrievalProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
            inParam.set(RetrievalProgressSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
        }
        // 集約フラグ
        inParam.set(RetrievalProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
        // 処理フラグ(次頁)
        inParam.set(RetrievalProgressSCHParams.PROCESS_FLAG, RetrievalInParameter.PROCESS_FLAG_NEXT_PAGE);

        //進捗表示
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
