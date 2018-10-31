// $Id: StorageProgressBusiness.java 7674 2010-03-18 00:40:51Z okayama $
package jp.co.daifuku.wms.storage.display.progress;

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
import jp.co.daifuku.wms.storage.schedule.StorageInParameter;
import jp.co.daifuku.wms.storage.schedule.StorageOutParameter;
import jp.co.daifuku.wms.storage.schedule.StorageProgressSCH;
import jp.co.daifuku.wms.storage.schedule.StorageProgressSCHParams;

/**
 * 入庫作業進捗の画面処理を行います。
 *
 * @version $Revision: 7674 $, $Date: 2010-03-18 09:40:51 +0900 (木, 18 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
@SuppressWarnings("serial")
public class StorageProgressBusiness
        extends StorageProgress
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_StorageProgress_up(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_StorageProgress_up(LST_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT = new ListCellKey("LST_DETAIL_COUNT", new StringCellColumn(), true, false);

    /** lst_StorageProgress_up(LST_STORAGE_COUNT) */
    private static final ListCellKey KEY_LST_STORAGE_COUNT = new ListCellKey("LST_STORAGE_COUNT", new StringCellColumn(), true, false);

    /** lst_StorageProgress_up(LST_CASE_QTY) */
    private static final ListCellKey KEY_LST_CASE_QTY = new ListCellKey("LST_CASE_QTY", new StringCellColumn(), true, false);

    /** lst_StorageProgress_up(LST_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PIECE_QTY = new ListCellKey("LST_PIECE_QTY", new StringCellColumn(), true, false);

    /** lst_StorageProgress_up(LST_SHORTAGE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY = new ListCellKey("LST_SHORTAGE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StorageProgress_down(LST_PLAN_DAY_2) */
    private static final ListCellKey KEY_LST_PLAN_DAY_2 = new ListCellKey("LST_PLAN_DAY_2", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_StorageProgress_down(LST_DETAIL_COUNT_2) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT_2 = new ListCellKey("LST_DETAIL_COUNT_2", new StringCellColumn(), true, false);

    /** lst_StorageProgress_down(LST_STORAGE_COUNT_2) */
    private static final ListCellKey KEY_LST_STORAGE_COUNT_2 = new ListCellKey("LST_STORAGE_COUNT_2", new StringCellColumn(), true, false);

    /** lst_StorageProgress_down(LST_CASE_QTY_2) */
    private static final ListCellKey KEY_LST_CASE_QTY_2 = new ListCellKey("LST_CASE_QTY_2", new StringCellColumn(), true, false);

    /** lst_StorageProgress_down(LST_PIECE_QTY_2) */
    private static final ListCellKey KEY_LST_PIECE_QTY_2 = new ListCellKey("LST_PIECE_QTY_2", new StringCellColumn(), true, false);

    /** lst_StorageProgress_down(LST_SHORTAGE_QTY_2) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY_2 = new ListCellKey("LST_SHORTAGE_QTY_2", new NumberCellColumn(0), true, false);

    /** lst_StorageProgress_up keys */
    private static final ListCellKey[] LST_STORAGEPROGRESS_UP_KEYS = {
        KEY_LST_PLAN_DAY,
        KEY_LST_DETAIL_COUNT,
        KEY_LST_CASE_QTY,
        KEY_LST_SHORTAGE_QTY,
        KEY_LST_STORAGE_COUNT,
        KEY_LST_PIECE_QTY,
    };

    /** lst_StorageProgress_down keys */
    private static final ListCellKey[] LST_STORAGEPROGRESS_DOWN_KEYS = {
        KEY_LST_PLAN_DAY_2,
        KEY_LST_DETAIL_COUNT_2,
        KEY_LST_CASE_QTY_2,
        KEY_LST_SHORTAGE_QTY_2,
        KEY_LST_STORAGE_COUNT_2,
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

    /** ListCellModel lst_StorageProgress_up */
    private ListCellModel _lcm_lst_StorageProgress_up;

    /** ListCellModel lst_StorageProgress_down */
    private ListCellModel _lcm_lst_StorageProgress_down;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public StorageProgressBusiness()
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
        //DFKLOOK:ここから修正
        // 定期送信を行います
        StorageProgressSCHParams inParam = new StorageProgressSCHParams();
        inParam.set(StorageProgressSCHParams.PROCESS_FLAG, StorageInParameter.PROCESS_FLAG_VIEW);

        //荷主コード
        inParam.set(StorageProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        // リストセル上段の予定日を基準日とする。
        ListCellLine line = _lcm_lst_StorageProgress_up.get(1);
        if (lst_StorageProgress_up.getVisible() == true)
        {
            inParam.set(StorageProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
        }
        //進捗表示
        progressViewList(inParam);
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_ProgressDisplayAuto_Click(ActionEvent e)
            throws Exception
    {
        //DFKLOOK:ここから修正
        rdo_ProgressDisplayAuto.setChecked(true);
        setFocus(rdo_ProgressDisplayAuto);
        this.viewState.setBoolean(ViewStateKeys.VSKEY_RDOCHECKED, false);
        setRegularTransmission();
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_ProgressDisplayManual_Click(ActionEvent e)
            throws Exception
    {
        //DFKLOOK:ここから修正
        rdo_ProgressDisplayManual.setChecked(true);
        setFocus(rdo_ProgressDisplayManual);
        this.viewState.setBoolean(ViewStateKeys.VSKEY_RDOCHECKED, true);
        setRegularTransmission();
        //DFKLOOK:ここまで修正
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
     * 作業進捗スケジュールクラスから表示用データを取得し、表示を行います。<BR>
     * @param inParam 検索パラメータ
     * @throws Exception 全ての例外を報告します。
     */
    protected void progressViewList(StorageProgressSCHParams inParam)
            throws Exception
    {
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StorageProgressSCH sch = null;
        try
        {
            conn = ConnectionManager.getSessionConnection(this);
            sch = new StorageProgressSCH(conn, this.getClass(), locale, ui);

            // 検索処理実行
            // スケジュールを呼び出します。
            List<Params> outParam = sch.query(inParam);
            if (outParam == null || outParam.size() == 0)
            {
                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey("6003011");

                if (inParam.get(StorageProgressSCHParams.PROCESS_FLAG).equals(
                        StorageInParameter.PROCESS_FLAG_PREVIOUS_PAGE))
                {
                    btn_PrevPage.setEnabled(false);
                }
                else if (inParam.get(StorageProgressSCHParams.PROCESS_FLAG).equals(
                        StorageInParameter.PROCESS_FLAG_NEXT_PAGE))
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
            // 6001013=表示しました。
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
     * @param viewParam グラフ、リストエリアに表示するパラメータ
     * @throws Exception 値セット時エラー
     */
    protected void setViewData(List<Params> p)
            throws Exception
    {
        // 画面の初期化
        progressClear();


        //表示
        lst_StorageProgress_up.setVisible(true);
        hbc_TaskProgress_up.setVisible(true);
        txt_ProgressRate_up.setVisible(true);
        lbl_Percent_up.setVisible(true);
        btn_Display.setEnabled(true);

        // Initializes ListCell
        lst_StorageProgress_up.clearRow();
        lst_StorageProgress_down.clearRow();

        // output display.
        boolean first = true;
        for (Params outparam : p)
        {
            if (first)
            {
                ListCellLine line = _lcm_lst_StorageProgress_up.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(StorageProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(StorageProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_STORAGE_COUNT, outparam.get(StorageProgressSCHParams.STORAGE_COUNT));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(StorageProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(StorageProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(StorageProgressSCHParams.SHORTAGE_QTY));
                txt_ProgressRate_up.setText(outparam.getString(StorageProgressSCHParams.PROGRESS_RATE));
                hbc_TaskProgress_up.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_TaskProgress_up.setValue(Double.valueOf(outparam.getString(StorageProgressSCHParams.PROGRESS_RATE)));
                hbc_TaskProgress_up.createChart(httpRequest);
                lst_StorageProgress_up_SetLineToolTip(line);
                _lcm_lst_StorageProgress_up.add(line);
                first = false;
            }
            else
            {
                //表示
                lst_StorageProgress_down.setVisible(true);
                hbc_TaskProgress_down.setVisible(true);
                txt_ProgressRate_down.setVisible(true);
                lbl_Percent_down.setVisible(true);
                ListCellLine line = _lcm_lst_StorageProgress_down.getNewLine();
                line.setValue(KEY_LST_PLAN_DAY_2, outparam.get(StorageProgressSCHParams.PLAN_DAY));
                line.setValue(KEY_LST_DETAIL_COUNT_2, outparam.get(StorageProgressSCHParams.DETAIL_COUNT));
                line.setValue(KEY_LST_STORAGE_COUNT_2, outparam.get(StorageProgressSCHParams.STORAGE_COUNT));
                line.setValue(KEY_LST_CASE_QTY_2, outparam.get(StorageProgressSCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY_2, outparam.get(StorageProgressSCHParams.PIECE_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY_2, outparam.get(StorageProgressSCHParams.SHORTAGE_QTY));
                txt_ProgressRate_down.setText(outparam.getString(StorageProgressSCHParams.PROGRESS_RATE));
                hbc_TaskProgress_down.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_TaskProgress_down.setValue(Double.valueOf(outparam.getString(StorageProgressSCHParams.PROGRESS_RATE)));
                hbc_TaskProgress_down.createChart(httpRequest);
                lst_StorageProgress_up_SetLineToolTip(line);
                _lcm_lst_StorageProgress_down.add(line);
            }
            viewState.setObject(ViewStateKeys.PROGRESS_DISPLAY, _grp_ProgressDisplay.getSelectedValue());
            viewState.setObject(ViewStateKeys.PROGRESS_RATE_UP, txt_ProgressRate_up.getValue());
            viewState.setObject(ViewStateKeys.PROGRESS_RATE_DOWN, txt_ProgressRate_down.getValue());
            //ボタン制御
            if (StorageOutParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(outparam.getString(StorageProgressSCHParams.BUTTON_CONTROL_FLAG)))
            {
                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (StorageOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(outparam.getString(StorageProgressSCHParams.BUTTON_CONTROL_FLAG)))
            {
                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (StorageOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(outparam.getString(StorageProgressSCHParams.BUTTON_CONTROL_FLAG)))
            {
                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (StorageOutParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(outparam.getString(StorageProgressSCHParams.BUTTON_CONTROL_FLAG)))
            {
                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(true);
            }

        }

    }

    /**
     * 自動更新か手動更新かを切り替えます。
     *
     */
    protected void setRegularTransmission()
    {
        // 手動か自動表示かをViewStateに保存します
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
     */
    protected void progressClear()
    {
        //画面初期化（頁切り替えボタン/グラフ部分）

        btn_PrevPage.setEnabled(false);
        btn_NextPage.setEnabled(false);

        lst_StorageProgress_up.setVisible(false);
        lst_StorageProgress_down.setVisible(false);
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
     * @throws Exception
     */
    private void initializeComponents()
            throws Exception
    {
        // get locale.
        Locale locale = httpRequest.getLocale();

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[]{rdo_ProgressDisplayAuto, rdo_ProgressDisplayManual}, locale);

        // initialize lst_StorageProgress_up.
        _lcm_lst_StorageProgress_up = new ListCellModel(lst_StorageProgress_up, LST_STORAGEPROGRESS_UP_KEYS, locale);
        _lcm_lst_StorageProgress_up.setToolTipVisible(KEY_LST_PLAN_DAY, false);
        _lcm_lst_StorageProgress_up.setToolTipVisible(KEY_LST_DETAIL_COUNT, false);
        _lcm_lst_StorageProgress_up.setToolTipVisible(KEY_LST_STORAGE_COUNT, false);
        _lcm_lst_StorageProgress_up.setToolTipVisible(KEY_LST_CASE_QTY, false);
        _lcm_lst_StorageProgress_up.setToolTipVisible(KEY_LST_PIECE_QTY, false);
        _lcm_lst_StorageProgress_up.setToolTipVisible(KEY_LST_SHORTAGE_QTY, false);

        // initialize lst_StorageProgress_down.
        _lcm_lst_StorageProgress_down = new ListCellModel(lst_StorageProgress_down, LST_STORAGEPROGRESS_DOWN_KEYS, locale);
        _lcm_lst_StorageProgress_down.setToolTipVisible(KEY_LST_PLAN_DAY_2, false);
        _lcm_lst_StorageProgress_down.setToolTipVisible(KEY_LST_DETAIL_COUNT_2, false);
        _lcm_lst_StorageProgress_down.setToolTipVisible(KEY_LST_STORAGE_COUNT_2, false);
        _lcm_lst_StorageProgress_down.setToolTipVisible(KEY_LST_CASE_QTY_2, false);
        _lcm_lst_StorageProgress_down.setToolTipVisible(KEY_LST_PIECE_QTY_2, false);
        _lcm_lst_StorageProgress_down.setToolTipVisible(KEY_LST_SHORTAGE_QTY_2, false);

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
    private void lst_StorageProgress_up_SetLineToolTip(ListCellLine line)
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
    @SuppressWarnings("all")
    private void lst_StorageProgress_down_SetLineToolTip(ListCellLine line)
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
        // 初期設定を行います。
        setRegularTransmission(false);
        //入力エリア
        rdo_ProgressDisplayAuto.setChecked(true);
        setFocus(rdo_ProgressDisplayAuto);
        // 頁切り替えボタン
        btn_PrevPage.setEnabled(false);
        btn_NextPage.setEnabled(false);
        // グラフ部分
        lst_StorageProgress_up.setVisible(false);
        lst_StorageProgress_down.setVisible(false);
        hbc_TaskProgress_up.setVisible(false);
        hbc_TaskProgress_down.setVisible(false);
        txt_ProgressRate_up.setVisible(false);
        txt_ProgressRate_up.setText("");
        txt_ProgressRate_down.setVisible(false);
        txt_ProgressRate_down.setText("");
        lbl_Percent_up.setVisible(false);
        lbl_Percent_down.setVisible(false);

        // スケジュールパラメータ
        StorageProgressSCHParams inParam = new StorageProgressSCHParams();
        inParam.set(StorageProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inParam.set(StorageProgressSCHParams.PROCESS_FLAG, StorageInParameter.PROCESS_FLAG_VIEW);

        // フォーカスセット
        setFocus(rdo_ProgressDisplayAuto);
        this.viewState.setBoolean(ViewStateKeys.VSKEY_RDOCHECKED, true);
        //進捗表示
        progressViewList(inParam);

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
        StorageProgressSCHParams searchParam = new StorageProgressSCHParams();
        //荷主コード
        searchParam.set(StorageProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        //リストセル上段の予定日を基準日とする。
        ListCellLine line = _lcm_lst_StorageProgress_up.get(1);
        if (lst_StorageProgress_up.getVisible())
        {
            searchParam.set(StorageProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
        }
        //処理フラグ(表示)
        searchParam.set(StorageProgressSCHParams.PROCESS_FLAG, StorageInParameter.PROCESS_FLAG_VIEW);

        //進捗表示
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
        // DFKLOOK ここから
        StorageProgressSCHParams searchParam = new StorageProgressSCHParams();

        //荷主コード
        searchParam.set(StorageProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        //リストセル上段の予定日を基準日とする。
        ListCellLine line = _lcm_lst_StorageProgress_up.get(1);
        searchParam.set(StorageProgressSCHParams.PLAN_DAY,
                WmsFormatter.toParamDate((Date)line.getValue(KEY_LST_PLAN_DAY)));
        //処理フラグ(前頁)
        searchParam.set(StorageProgressSCHParams.PROCESS_FLAG, StorageInParameter.PROCESS_FLAG_PREVIOUS_PAGE);

        //進捗表示
        progressViewList(searchParam);
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
        rdo_ProgressDisplayAuto.validate(false);
        txt_ProgressRate_up.validate(this, false);
        txt_ProgressRate_down.validate(this, false);
        // DFKLOOK ここから
        StorageProgressSCHParams searchParam = new StorageProgressSCHParams();

        //荷主コード
        searchParam.set(StorageProgressSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        //リストセル上段の予定日を基準日とする。
        ListCellLine upline = _lcm_lst_StorageProgress_up.get(1);
        ListCellLine downline = _lcm_lst_StorageProgress_down.get(1);
        if (lst_StorageProgress_down.getVisible() == true)
        {
            searchParam.set(StorageProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)downline.getValue(KEY_LST_PLAN_DAY_2)));
        }
        else
        {
            searchParam.set(StorageProgressSCHParams.PLAN_DAY,
                    WmsFormatter.toParamDate((Date)upline.getValue(KEY_LST_PLAN_DAY)));
        }
        //処理フラグ(次頁)
        searchParam.set(StorageProgressSCHParams.PROCESS_FLAG, StorageInParameter.PROCESS_FLAG_NEXT_PAGE);

        //進捗表示
        progressViewList(searchParam);
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
