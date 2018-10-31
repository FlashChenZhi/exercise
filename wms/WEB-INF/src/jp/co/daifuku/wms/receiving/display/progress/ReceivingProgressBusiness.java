// $Id: ReceivingProgressBusiness.java 7674 2010-03-18 00:40:51Z okayama $
package jp.co.daifuku.wms.receiving.display.progress;

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
import jp.co.daifuku.wms.receiving.schedule.ReceivingInParameter;
import jp.co.daifuku.wms.receiving.schedule.ReceivingOutParameter;
import jp.co.daifuku.wms.receiving.schedule.ReceivingProgressSCH;
import jp.co.daifuku.wms.receiving.schedule.ReceivingProgressSCHParams;

/**
 * 入荷作業進捗の画面処理を行います。
 *
 * @version $Revision: 7674 $, $Date: 2010-03-18 09:40:51 +0900 (木, 18 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
@SuppressWarnings("serial")
public class ReceivingProgressBusiness
        extends ReceivingProgress
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ReceivingProgress_up(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_ReceivingProgress_up(LST_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT = new ListCellKey("LST_DETAIL_COUNT", new StringCellColumn(), true, false);

    /** lst_ReceivingProgress_up(LST_RECEIVE_COUNT) */
    private static final ListCellKey KEY_LST_RECEIVE_COUNT = new ListCellKey("LST_RECEIVE_COUNT", new StringCellColumn(), true, false);

    /** lst_ReceivingProgress_up(LST_CASE_QTY) */
    private static final ListCellKey KEY_LST_CASE_QTY = new ListCellKey("LST_CASE_QTY", new StringCellColumn(), true, false);

    /** lst_ReceivingProgress_up(LST_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PIECE_QTY = new ListCellKey("LST_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_ReceivingProgress_up(LST_SHORTAGE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY = new ListCellKey("LST_SHORTAGE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ReceivingProgress_down(LST_PLAN_DAY_2) */
    private static final ListCellKey KEY_LST_PLAN_DAY_2 = new ListCellKey("LST_PLAN_DAY_2", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_ReceivingProgress_down(LST_DETAIL_COUNT_2) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT_2 = new ListCellKey("LST_DETAIL_COUNT_2", new StringCellColumn(), true, false);

    /** lst_ReceivingProgress_down(LST_RECEIVE_COUNT_2) */
    private static final ListCellKey KEY_LST_RECEIVE_COUNT_2 = new ListCellKey("LST_RECEIVE_COUNT_2", new StringCellColumn(), true, false);

    /** lst_ReceivingProgress_down(LST_CASE_QTY_2) */
    private static final ListCellKey KEY_LST_CASE_QTY_2 = new ListCellKey("LST_CASE_QTY_2", new StringCellColumn(), true, false);

    /** lst_ReceivingProgress_down(LST_PIECE_QTY_2) */
    private static final ListCellKey KEY_LST_PIECE_QTY_2 = new ListCellKey("LST_PIECE_QTY_2", new StringCellColumn(), true, false);

    /** lst_ReceivingProgress_down(LST_SHORTAGE_QTY_2) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY_2 = new ListCellKey("LST_SHORTAGE_QTY_2", new NumberCellColumn(0), true, false);

    /** lst_ReceivingProgress_up keys */
    private static final ListCellKey[] LST_RECEIVINGPROGRESS_UP_KEYS = {
        KEY_LST_PLAN_DAY,
        KEY_LST_DETAIL_COUNT,
        KEY_LST_CASE_QTY,
        KEY_LST_SHORTAGE_QTY,
        KEY_LST_RECEIVE_COUNT,
        KEY_LST_PIECE_QTY,
    };

    /** lst_ReceivingProgress_down keys */
    private static final ListCellKey[] LST_RECEIVINGPROGRESS_DOWN_KEYS = {
        KEY_LST_PLAN_DAY_2,
        KEY_LST_DETAIL_COUNT_2,
        KEY_LST_CASE_QTY_2,
        KEY_LST_SHORTAGE_QTY_2,
        KEY_LST_RECEIVE_COUNT_2,
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

    /** ListCellModel lst_ReceivingProgress_up */
    private ListCellModel _lcm_lst_ReceivingProgress_up;

    /** ListCellModel lst_ReceivingProgress_down */
    private ListCellModel _lcm_lst_ReceivingProgress_down;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ReceivingProgressBusiness()
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
        // 定期送信を行います

        // get locale.
        ReceivingProgressSCHParams searchParam = new ReceivingProgressSCHParams();

        searchParam.set(ReceivingProgressSCHParams.PROCESS_FLAG, ReceivingInParameter.PROCESS_FLAG_VIEW);
        searchParam.set(ReceivingProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // リストセル上段の予定日を基準日とする。
        ListCellLine line = _lcm_lst_ReceivingProgress_up.get(1);
        // Sets planned date on a pop of ListCell a standard day.
        if (lst_ReceivingProgress_up.getVisible() == true)
        {
            searchParam.set(ReceivingProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
        }
        //進捗表示
        // Displays a progress
        progressViewList(searchParam);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_ProgressDisplayAuto_Click(ActionEvent e)
            throws Exception
    {
        rdo_ProgressDisplayAuto.setChecked(true);
        setFocus(rdo_ProgressDisplayAuto);
        this.viewState.setBoolean(ViewStateKeys.VSKEY_RDOCHECKED, false);
        setRegularTransmission();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_ProgressDisplayManual_Click(ActionEvent e)
            throws Exception
    {
        rdo_ProgressDisplayManual.setChecked(true);
        setFocus(rdo_ProgressDisplayManual);
        this.viewState.setBoolean(ViewStateKeys.VSKEY_RDOCHECKED, true);
        setRegularTransmission();
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

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    // DFKLOOK ここから
    /**
     * 作業進捗を画面へ表示します。<BR>
     * Displays an operation progress on a screen.<BR>
     * 作業進捗スケジュールクラスから表示用データを取得し、表示を行います。<BR>
     * Gets a data to display from Schedule for an operation progress, and then display them.<BR>
     * @param searchParam Search parameter
     * @throws Exception Reports all exceptions.<BR>
     */
    protected void progressViewList(ReceivingProgressSCHParams searchParam)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReceivingProgressSCH sch = null;
        try
        {

            conn = ConnectionManager.getSessionConnection(this);
            sch = new ReceivingProgressSCH(conn, this.getClass(), locale, ui);

            // 検索処理実行
            // Search process
            List<Params> outparams = sch.query(searchParam);
            message.setMsgResourceKey(sch.getMessage());

            if (outparams == null || outparams.size() == 0)
            {
                // 6003011=対象データはありませんでした。
                // 6003011 = "Target data was not found."
                message.setMsgResourceKey("6003011");

                if (searchParam.get(ReceivingProgressSCHParams.PROCESS_FLAG).equals(
                        ReceivingInParameter.PROCESS_FLAG_PREVIOUS_PAGE))
                {
                    btn_PrevPage.setEnabled(false);
                }
                else if (searchParam.get(ReceivingProgressSCHParams.PROCESS_FLAG).equals(
                        ReceivingInParameter.PROCESS_FLAG_NEXT_PAGE))
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
            // Displays progress graphs.
            setViewData(outparams);

            // 6001013=表示しました。
            // 6001013 = "Data is shown."
            message.setMsgResourceKey("6001013");
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                // コネクションのクローズを行う
                // Closes a connection.
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
     * パラメータを取得し、画面に表示します
     * Gets parameters and displays them on a screen. 
     * @param p Parameters to display on graphs and ListCell.<BR>
     * @throws Exception Errors when values are set.<BR>
     */
    protected void setViewData(List<Params> p)
            throws Exception
    {
        // 画面の初期化
        // Initializes a screen
        progressClear();

        //表示
        // Displays
        rdo_ProgressDisplayManual.setEnabled(true);
        rdo_ProgressDisplayAuto.setEnabled(true);
        lst_ReceivingProgress_up.setVisible(true);
        hbc_TaskProgress_up.setVisible(true);
        txt_ProgressRate_up.setVisible(true);
        lbl_Percent_up.setVisible(true);
        btn_Display.setEnabled(true);
        //リストセル初期化
        // Initializes ListCell
        lst_ReceivingProgress_up.clearRow();
        lst_ReceivingProgress_down.clearRow();
        //行の追加


        boolean first = true;
        for (Params outparam : p)
        {
            if (first)
            {
                ListCellLine line = _lcm_lst_ReceivingProgress_up.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(ReceivingProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(ReceivingProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_RECEIVE_COUNT, outparam.get(ReceivingProgressSCHParams.RECEIVE_COUNT));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(ReceivingProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(ReceivingProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(ReceivingProgressSCHParams.SHORTAGE_QTY));

                txt_ProgressRate_up.setText(outparam.getString(ReceivingProgressSCHParams.PROGRESS_RATE));
                hbc_TaskProgress_up.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_TaskProgress_up.setValue(Double.valueOf(outparam.getString(ReceivingProgressSCHParams.PROGRESS_RATE)));
                hbc_TaskProgress_up.createChart(httpRequest);
                lst_ReceivingProgress_up_SetLineToolTip(line);
                _lcm_lst_ReceivingProgress_up.add(line);

                first = false;
            }
            else
            {
                //表示
                // Displays
                lst_ReceivingProgress_down.setVisible(true);
                hbc_TaskProgress_down.setVisible(true);
                txt_ProgressRate_down.setVisible(true);
                lbl_Percent_down.setVisible(true);

                ListCellLine line = _lcm_lst_ReceivingProgress_down.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY_2, outparam.get(ReceivingProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_DETAIL_COUNT_2, outparam.get(ReceivingProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_RECEIVE_COUNT_2, outparam.get(ReceivingProgressSCHParams.RECEIVE_COUNT));
                line.setValue(KEY_LST_CASE_QTY_2, outparam.get(ReceivingProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY_2, outparam.get(ReceivingProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY_2, outparam.get(ReceivingProgressSCHParams.SHORTAGE_QTY));

                txt_ProgressRate_down.setText(outparam.getString(ReceivingProgressSCHParams.PROGRESS_RATE));
                hbc_TaskProgress_down.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_TaskProgress_down.setValue(Double.valueOf(outparam.getString(ReceivingProgressSCHParams.PROGRESS_RATE)));
                hbc_TaskProgress_down.createChart(httpRequest);

                lst_ReceivingProgress_down_SetLineToolTip(line);
                _lcm_lst_ReceivingProgress_down.add(line);
            }
            viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            viewState.setObject(ViewStateKeys.PROGRESS_RATE_UP, txt_ProgressRate_up.getValue());
            viewState.setObject(ViewStateKeys.PROGRESS_RATE_DOWN, txt_ProgressRate_down.getValue());

            //ボタン制御
            // Controls buttons.
            if (ReceivingOutParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(outparam.get(ReceivingProgressSCHParams.BUTTON_CONTROL_FLAG)))
            {
                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (ReceivingOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(outparam.get(ReceivingProgressSCHParams.BUTTON_CONTROL_FLAG)))
            {
                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (ReceivingOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(outparam.get(ReceivingProgressSCHParams.BUTTON_CONTROL_FLAG)))
            {
                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (ReceivingOutParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(outparam.get(ReceivingProgressSCHParams.BUTTON_CONTROL_FLAG)))
            {
                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(true);
            }
        }
    }

    /**
     * 自動更新か手動更新かを切り替えます。
     * Switches an auto update or an manual update.
     *
     */
    protected void setRegularTransmission()
    {
        // 手動か自動表示かをViewStateに保存します
        // Keeps a result (auto or maual) in ViewState
        if (rdo_ProgressDisplayAuto.getChecked())
        {
            setRegularTransmission(true);
        }
        else if (rdo_ProgressDisplayManual.getChecked())
        {
            setRegularTransmission(false);
        }
    }

    /**
     * 進捗表示をクリア処理を行います。
     * Clears a progress indication.
     */
    protected void progressClear()
    {
        //画面初期化（頁切り替えボタン/グラフ部分）
        // Initializes a screen (a button to switch pages / graph portions)

        btn_PrevPage.setEnabled(false);
        btn_NextPage.setEnabled(false);

        lst_ReceivingProgress_up.setVisible(false);
        lst_ReceivingProgress_down.setVisible(false);
        hbc_TaskProgress_up.setVisible(false);
        hbc_TaskProgress_down.setVisible(false);
        txt_ProgressRate_up.setText("");
        txt_ProgressRate_down.setText("");
        txt_ProgressRate_up.setVisible(false);
        txt_ProgressRate_down.setVisible(false);
        lbl_Percent_up.setVisible(false);
        lbl_Percent_down.setVisible(false);
    }

    // DFKLOOK ここまで

    /**
     *
     * @throws Exception 例外を返します。
     */
    private void initializeComponents()
            throws Exception
    {
        // get locale.
        Locale locale = httpRequest.getLocale();

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[]{rdo_ProgressDisplayAuto, rdo_ProgressDisplayManual}, locale);

        // initialize lst_ReceivingProgress_up.
        _lcm_lst_ReceivingProgress_up = new ListCellModel(lst_ReceivingProgress_up, LST_RECEIVINGPROGRESS_UP_KEYS, locale);
        _lcm_lst_ReceivingProgress_up.setToolTipVisible(KEY_LST_PLAN_DAY, false);
        _lcm_lst_ReceivingProgress_up.setToolTipVisible(KEY_LST_DETAIL_COUNT, false);
        _lcm_lst_ReceivingProgress_up.setToolTipVisible(KEY_LST_RECEIVE_COUNT, false);
        _lcm_lst_ReceivingProgress_up.setToolTipVisible(KEY_LST_CASE_QTY, false);
        _lcm_lst_ReceivingProgress_up.setToolTipVisible(KEY_LST_PIECE_QTY, false);
        _lcm_lst_ReceivingProgress_up.setToolTipVisible(KEY_LST_SHORTAGE_QTY, false);

        // initialize lst_ReceivingProgress_down.
        _lcm_lst_ReceivingProgress_down = new ListCellModel(lst_ReceivingProgress_down, LST_RECEIVINGPROGRESS_DOWN_KEYS, locale);
        _lcm_lst_ReceivingProgress_down.setToolTipVisible(KEY_LST_PLAN_DAY_2, false);
        _lcm_lst_ReceivingProgress_down.setToolTipVisible(KEY_LST_DETAIL_COUNT_2, false);
        _lcm_lst_ReceivingProgress_down.setToolTipVisible(KEY_LST_RECEIVE_COUNT_2, false);
        _lcm_lst_ReceivingProgress_down.setToolTipVisible(KEY_LST_CASE_QTY_2, false);
        _lcm_lst_ReceivingProgress_down.setToolTipVisible(KEY_LST_PIECE_QTY_2, false);
        _lcm_lst_ReceivingProgress_down.setToolTipVisible(KEY_LST_SHORTAGE_QTY_2, false);

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
    private void lst_ReceivingProgress_up_SetLineToolTip(ListCellLine line)
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
    private void lst_ReceivingProgress_down_SetLineToolTip(ListCellLine line)
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
        if (rdo_ProgressDisplayAuto.getChecked())
        {
            // set focus.
            setFocus(rdo_ProgressDisplayAuto);
        }
        else
        {
            // set focus.
            setFocus(rdo_ProgressDisplayManual);
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
        // set input parameters.
        ReceivingProgressSCHParams inparam = new ReceivingProgressSCHParams();

        // clear.
        rdo_ProgressDisplayAuto.setChecked(true);
        setFocus(rdo_ProgressDisplayAuto);
        txt_ProgressRate_up.setReadOnly(true);
        txt_ProgressRate_down.setReadOnly(true);

        // 頁切り替えボタン
        // A button to switch pages
        btn_PrevPage.setEnabled(false);
        btn_NextPage.setEnabled(false);
        // グラフ部分
        // Graphs
        lst_ReceivingProgress_up.setVisible(false);
        lst_ReceivingProgress_down.setVisible(false);
        hbc_TaskProgress_up.setVisible(false);
        hbc_TaskProgress_down.setVisible(false);
        txt_ProgressRate_up.setVisible(false);
        txt_ProgressRate_up.setText("");
        txt_ProgressRate_down.setVisible(false);
        txt_ProgressRate_down.setText("");
        lbl_Percent_up.setVisible(false);
        lbl_Percent_down.setVisible(false);


        inparam.set(ReceivingProgressSCHParams.PROCESS_FLAG, ReceivingInParameter.PROCESS_FLAG_VIEW);
        inparam.set(ReceivingProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        //進捗表示
        // Displays a progress
        progressViewList(inparam);

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
        rdo_ProgressDisplayAuto.validate(false);
        txt_ProgressRate_up.validate(this, false);
        txt_ProgressRate_down.validate(this, false);

        // DFKLOOK ここから
        //上段予定日を基準日にセットする
        // Sets a planned date on an upper section to a standard date.
        ReceivingProgressSCHParams searchParam = new ReceivingProgressSCHParams();
        //リストセル上段の予定日を基準日とする。
        searchParam.set(ReceivingProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        ListCellLine line = _lcm_lst_ReceivingProgress_up.get(1);
        // Sets a planned date on a lower section of ListCell to a standard date.        
        if (lst_ReceivingProgress_up.getVisible())
        {
            searchParam.set(ReceivingProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
        }
        //処理フラグ(表示)
        // Process flag (Display)
        searchParam.set(ReceivingProgressSCHParams.PROCESS_FLAG, ReceivingInParameter.PROCESS_FLAG_VIEW);

        //進捗表示
        // Displays a progress.
        progressViewList(searchParam);

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
        rdo_ProgressDisplayAuto.validate(false);
        txt_ProgressRate_up.validate(this, false);
        txt_ProgressRate_down.validate(this, false);

        ReceivingProgressSCHParams searchParam = new ReceivingProgressSCHParams();

        //リストセル上段の予定日を基準日とする。
        searchParam.set(ReceivingProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        ListCellLine line = _lcm_lst_ReceivingProgress_up.get(1);
        // Sets a planned date on an upper section of ListCell to a standard date.
        searchParam.set(ReceivingProgressSCHParams.PLAN_DAY,
                WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
        //処理フラグ(前頁)
        // Process flag (previous page)
        searchParam.set(ReceivingProgressSCHParams.PROCESS_FLAG, ReceivingInParameter.PROCESS_FLAG_PREVIOUS_PAGE);

        //進捗表示
        // Displays a progress
        progressViewList(searchParam);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_NextPage_Click_Process()
            throws Exception
    {
        // input validation.
        rdo_ProgressDisplayAuto.validate(false);
        txt_ProgressRate_up.validate(this, false);
        txt_ProgressRate_down.validate(this, false);

        ReceivingProgressSCHParams searchParam = new ReceivingProgressSCHParams();

        //リストセル上段の予定日を基準日とする。
        searchParam.set(ReceivingProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        ListCellLine upline = _lcm_lst_ReceivingProgress_up.get(1);
        ListCellLine downline = _lcm_lst_ReceivingProgress_down.get(1);
        // Sets a planned date on an upper section of ListCell to a standard date.
        if (lst_ReceivingProgress_down.getVisible() == true)
        {
            searchParam.set(ReceivingProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)downline.getValue(KEY_LST_PLAN_DAY_2)));
        }
        else
        {
            searchParam.set(ReceivingProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)upline.getValue(KEY_LST_PLAN_DAY)));
        }
        //処理フラグ(次頁)
        // Process flag (Next page)
        searchParam.set(ReceivingProgressSCHParams.PROCESS_FLAG, ReceivingInParameter.PROCESS_FLAG_NEXT_PAGE);

        //進捗表示
        // Displays a progress.
        progressViewList(searchParam);
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
