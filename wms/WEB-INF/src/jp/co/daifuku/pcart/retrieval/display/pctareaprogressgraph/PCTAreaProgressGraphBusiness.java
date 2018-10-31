// $Id: PCTAreaProgressGraphBusiness.java 4324 2009-05-22 11:17:17Z okayama $
package jp.co.daifuku.pcart.retrieval.display.pctareaprogressgraph;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.base.controller.PCTConsignorController;
import jp.co.daifuku.pcart.retrieval.schedule.PCTAreaProgressGraphSCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTAreaProgressGraphSCHParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * エリア別作業進捗(グラフなし)の画面処理を行います。
 *
 * @version $Revision: 4324 $, $Date:: 2009-05-22 20:17:17 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTAreaProgressGraphBusiness
        extends PCTAreaProgressGraph
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_PCTAreaProgress1(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO =
            new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_PCTAreaProgress1(LST_ZONE_NO) */
    private static final ListCellKey KEY_LST_ZONE_NO =
            new ListCellKey("LST_ZONE_NO", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress1(LST_LINE_COUNT) */
    private static final ListCellKey KEY_LST_LINE_COUNT =
            new ListCellKey("LST_LINE_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress1(LST_LOT_COUNT) */
    private static final ListCellKey KEY_LST_LOT_COUNT =
            new ListCellKey("LST_LOT_COUNT", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress2(LST_AREA_NO_2) */
    private static final ListCellKey KEY_LST_AREA_NO_2 =
            new ListCellKey("LST_AREA_NO_2", new AreaCellColumn(), true, false);

    /** lst_PCTAreaProgress2(LST_ZONE_NO_2) */
    private static final ListCellKey KEY_LST_ZONE_NO_2 =
            new ListCellKey("LST_ZONE_NO_2", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress2(LST_LINE_COUNT_2) */
    private static final ListCellKey KEY_LST_LINE_COUNT_2 =
            new ListCellKey("LST_LINE_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress2(LST_LOT_COUNT_2) */
    private static final ListCellKey KEY_LST_LOT_COUNT_2 =
            new ListCellKey("LST_LOT_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress3(LST_AREA_NO_3) */
    private static final ListCellKey KEY_LST_AREA_NO_3 =
            new ListCellKey("LST_AREA_NO_3", new AreaCellColumn(), true, false);

    /** lst_PCTAreaProgress3(LST_ZONE_NO_3) */
    private static final ListCellKey KEY_LST_ZONE_NO_3 =
            new ListCellKey("LST_ZONE_NO_3", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress3(LST_LINE_COUNT_3) */
    private static final ListCellKey KEY_LST_LINE_COUNT_3 =
            new ListCellKey("LST_LINE_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress3(LST_LOT_COUNT_3) */
    private static final ListCellKey KEY_LST_LOT_COUNT_3 =
            new ListCellKey("LST_LOT_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress4(LST_AREA_NO_4) */
    private static final ListCellKey KEY_LST_AREA_NO_4 =
            new ListCellKey("LST_AREA_NO_4", new AreaCellColumn(), true, false);

    /** lst_PCTAreaProgress4(LST_ZONE_NO_4) */
    private static final ListCellKey KEY_LST_ZONE_NO_4 =
            new ListCellKey("LST_ZONE_NO_4", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress4(LST_LINE_COUNT_4) */
    private static final ListCellKey KEY_LST_LINE_COUNT_4 =
            new ListCellKey("LST_LINE_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress4(LST_LOT_COUNT_4) */
    private static final ListCellKey KEY_LST_LOT_COUNT_4 =
            new ListCellKey("LST_LOT_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTAreaProgress1 kyes */
    private static final ListCellKey[] LST_PCTAREAPROGRESS1_KEYS = {
            KEY_LST_AREA_NO,
            KEY_LST_ZONE_NO,
            KEY_LST_LINE_COUNT,
            KEY_LST_LOT_COUNT,
    };

    /** lst_PCTAreaProgress2 kyes */
    private static final ListCellKey[] LST_PCTAREAPROGRESS2_KEYS = {
            KEY_LST_AREA_NO_2,
            KEY_LST_ZONE_NO_2,
            KEY_LST_LINE_COUNT_2,
            KEY_LST_LOT_COUNT_2,
    };

    /** lst_PCTAreaProgress3 kyes */
    private static final ListCellKey[] LST_PCTAREAPROGRESS3_KEYS = {
            KEY_LST_AREA_NO_3,
            KEY_LST_ZONE_NO_3,
            KEY_LST_LINE_COUNT_3,
            KEY_LST_LOT_COUNT_3,
    };

    /** lst_PCTAreaProgress4 kyes */
    private static final ListCellKey[] LST_PCTAREAPROGRESS4_KEYS = {
            KEY_LST_AREA_NO_4,
            KEY_LST_ZONE_NO_4,
            KEY_LST_LINE_COUNT_4,
            KEY_LST_LOT_COUNT_4,
    };

    // DFKLOOK:ここから修正
    /**
     * 定数0
     */
    private static final int NUMBER_ZERO = 0;

    /**
     * 定数1
     */
    private static final int NUMBER_ONE = 1;

    /**
     * 定数2
     */
    private static final int NUMBER_TWO = 2;

    /**
     * 定数3
     */
    private static final int NUMBER_THREE = 3;

    /**
     * 進捗リストセルの最大表示件数
     */
    private static final int MAX_DISPLAY_PROGRESS = 4;

    // DFKLOOK:ここまで修正

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel ProgressDisplay */
    private RadioButtonGroup _grp_ProgressDisplay;

    /** ListCellModel lst_PCTAreaProgress1 */
    private ListCellModel _lcm_lst_PCTAreaProgress1;

    /** ListCellModel lst_PCTAreaProgress2 */
    private ListCellModel _lcm_lst_PCTAreaProgress2;

    /** ListCellModel lst_PCTAreaProgress3 */
    private ListCellModel _lcm_lst_PCTAreaProgress3;

    /** ListCellModel lst_PCTAreaProgress4 */
    private ListCellModel _lcm_lst_PCTAreaProgress4;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTAreaProgressGraphBusiness()
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
    public void page_ClientPull(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAreaProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAreaProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAreaProgressGraphSCHParams inparam = new PCTAreaProgressGraphSCHParams();

            if (txt_ConsignorCode.getText().equals(viewState.getString(ViewStateKeys.VS_CONSIGNOR_CODE))
                    && txt_BatchNo.getText().equals(viewState.getString(ViewStateKeys.VS_BATCH_NO))
                    && txt_BatchSeqNo.getText().equals(viewState.getString(ViewStateKeys.VS_BATCH_SEQ_NO)))
            {
                inparam.set(PCTAreaProgressGraphSCHParams.CONSIGNOR_CODE,
                        viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
                inparam.set(PCTAreaProgressGraphSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.VS_BATCH_NO));
                inparam.set(PCTAreaProgressGraphSCHParams.BATCH_SEQ_NO,
                        viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));

                if (lst_PCTAreaProgress1.getVisible())
                {
                    // エリアNo.
                    inparam.set(PCTAreaProgressGraphSCHParams.AREA_NO, lst_PCTAreaProgress1.getValue(1));
                    // ゾーンNo.
                    inparam.set(PCTAreaProgressGraphSCHParams.ZONE_NO, lst_PCTAreaProgress1.getValue(2));
                }
            }
            else
            {
                inparam.set(PCTAreaProgressGraphSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                inparam.set(PCTAreaProgressGraphSCHParams.BATCH_NO, txt_BatchNo.getValue());
                inparam.set(PCTAreaProgressGraphSCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());

                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getValue());
                viewState.setObject(ViewStateKeys.VS_BATCH_NO, txt_BatchNo.getText());
                viewState.setObject(ViewStateKeys.VS_BATCH_SEQ_NO, txt_BatchSeqNo.getText());
            }
            inparam.set(PCTAreaProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);
            inparam.set(PCTAreaProgressGraphSCHParams.BUTTON_CONTROL_FLAG, "");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            if (outparams == null || outparams.size() == 0)
            {
                // 表示データを削除
                clearView();


                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);

                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey("6003011");
                return;
            }

            setViewData(outparams);

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
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ConsignorCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // get locale.
        Connection conn = null;
        PCTConsignorController conController = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            conController = new PCTConsignorController(conn, this.getClass());
            txt_ConsignorName.setText(conController.getConsignorName(txt_ConsignorCode.getText(),
                    InParameter.SEARCH_TABLE_MASTER));

            setRegularTransmission();

            setFocus(txt_BatchNo);
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
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Auto_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // clear.
        rdo_Auto.setChecked(true);

        // 定期送信フラグの切り替え
        setRegularTransmission();

        // 必須入力チェック
        txt_ConsignorCode.validate(this, true);

        // 入力された内容をViewStateに保持
        viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
        viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getValue());
        viewState.setObject(ViewStateKeys.VS_BATCH_NO, txt_BatchNo.getText());
        viewState.setObject(ViewStateKeys.VS_BATCH_SEQ_NO, txt_BatchSeqNo.getText());
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Manual_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // clear.
        rdo_Manual.setChecked(true);

        // 定期送信フラグの切り替え
        setRegularTransmission();

        // 必須入力チェック
        txt_ConsignorCode.validate(this, true);
        // DFKLOOK:ここまで修正
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
     * リストセルデータ、グラフデータの初期化を行います。<BR>
     * @throws Exception 全ての例外を報告します。
     */
    protected void clearView()
            throws Exception
    {
        lst_PCTAreaProgress1.clearRow();
        lst_PCTAreaProgress1.setVisible(false);
        lst_PCTAreaProgress2.clearRow();
        lst_PCTAreaProgress2.setVisible(false);
        lst_PCTAreaProgress3.clearRow();
        lst_PCTAreaProgress3.setVisible(false);
        lst_PCTAreaProgress4.clearRow();
        lst_PCTAreaProgress4.setVisible(false);
        hbc_PCTAllTask1.setValue(0);
        hbc_PCTAllTask2.setValue(0);
        hbc_PCTAllTask3.setValue(0);
        hbc_PCTAllTask4.setValue(0);
        hbc_PCTAllTask1.createChart(httpRequest);
        hbc_PCTAllTask2.createChart(httpRequest);
        hbc_PCTAllTask3.createChart(httpRequest);
        hbc_PCTAllTask4.createChart(httpRequest);

        hbc_PCTAllTask1.setVisible(false);
        txt_ProgressRate1.setVisible(false);
        lbl_Percent1.setVisible(false);
        hbc_PCTAllTask2.setVisible(false);
        txt_ProgressRate2.setVisible(false);
        lbl_Percent2.setVisible(false);
        hbc_PCTAllTask3.setVisible(false);
        txt_ProgressRate3.setVisible(false);
        lbl_Percent3.setVisible(false);
        hbc_PCTAllTask4.setVisible(false);
        txt_ProgressRate4.setVisible(false);
        lbl_Percent4.setVisible(false);

        // 表示有効
        btn_Display.setEnabled(true);
        // 前頁無効
        btn_PrevPage.setEnabled(false);
        // 次頁無効
        btn_NextPage.setEnabled(false);
    }

    /**
     * 自動更新か手動更新かを切り替えます。
     * @throws Exception 全ての例外を報告します。
     */
    protected void setRegularTransmission()
            throws Exception

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
     * パラメータを取得し、画面に表示します。<BR>
     * @param p グラフ、リストエリアに表示するパラメータ
     * @throws Exception 全ての例外を報告します。
     */
    protected void setViewData(List<Params> ps)
            throws Exception
    {

        // 表示データを削除
        clearView();

        int i = 0;

        for (Params outparam : ps)
        {
            if (i == NUMBER_ZERO)
            {

                lst_PCTAreaProgress1.setVisible(true);
                hbc_PCTAllTask1.setVisible(true);
                txt_ProgressRate1.setVisible(true);
                lbl_Percent1.setVisible(true);

                ListCellLine line = _lcm_lst_PCTAreaProgress1.getNewLine();
                line.setValue(KEY_LST_AREA_NO, outparam.get(PCTAreaProgressGraphSCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO, outparam.get(PCTAreaProgressGraphSCHParams.ZONE_NO));
                line.setValue(KEY_LST_LINE_COUNT, outparam.get(PCTAreaProgressGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT, outparam.get(PCTAreaProgressGraphSCHParams.LOT_COUNT));
                lst_PCTAreaProgress1_SetLineToolTip(line);
                _lcm_lst_PCTAreaProgress1.add(line);

                lst_PCTAreaProgress1.setReverseView(false);
                hbc_PCTAllTask1.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_PCTAllTask1.setValue(Double.valueOf(outparam.getString(PCTAreaProgressGraphSCHParams.PROGRESS_RATE)));
                hbc_PCTAllTask1.createChart(httpRequest);
                txt_ProgressRate1.setValue(outparam.get(PCTAreaProgressGraphSCHParams.PROGRESS_RATE));
                txt_ProgressRate1.setReadOnly(true);
            }
            if (i == NUMBER_ONE)
            {

                lst_PCTAreaProgress2.setVisible(true);
                hbc_PCTAllTask2.setVisible(true);
                txt_ProgressRate2.setVisible(true);
                lbl_Percent2.setVisible(true);

                ListCellLine line = _lcm_lst_PCTAreaProgress2.getNewLine();
                line.setValue(KEY_LST_AREA_NO_2, outparam.get(PCTAreaProgressGraphSCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO_2, outparam.get(PCTAreaProgressGraphSCHParams.ZONE_NO));
                line.setValue(KEY_LST_LINE_COUNT_2, outparam.get(PCTAreaProgressGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_2, outparam.get(PCTAreaProgressGraphSCHParams.LOT_COUNT));
                lst_PCTAreaProgress2_SetLineToolTip(line);
                _lcm_lst_PCTAreaProgress2.add(line);

                lst_PCTAreaProgress2.setReverseView(false);
                hbc_PCTAllTask2.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_PCTAllTask2.setValue(Double.valueOf(outparam.getString(PCTAreaProgressGraphSCHParams.PROGRESS_RATE)));
                hbc_PCTAllTask2.createChart(httpRequest);
                txt_ProgressRate2.setValue(outparam.get(PCTAreaProgressGraphSCHParams.PROGRESS_RATE));
                txt_ProgressRate2.setReadOnly(true);
            }
            if (i == NUMBER_TWO)
            {

                lst_PCTAreaProgress3.setVisible(true);
                hbc_PCTAllTask3.setVisible(true);
                txt_ProgressRate3.setVisible(true);
                lbl_Percent3.setVisible(true);

                ListCellLine line = _lcm_lst_PCTAreaProgress3.getNewLine();
                line.setValue(KEY_LST_AREA_NO_3, outparam.get(PCTAreaProgressGraphSCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO_3, outparam.get(PCTAreaProgressGraphSCHParams.ZONE_NO));
                line.setValue(KEY_LST_LINE_COUNT_3, outparam.get(PCTAreaProgressGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_3, outparam.get(PCTAreaProgressGraphSCHParams.LOT_COUNT));
                lst_PCTAreaProgress3_SetLineToolTip(line);
                _lcm_lst_PCTAreaProgress3.add(line);

                lst_PCTAreaProgress3.setReverseView(false);
                hbc_PCTAllTask3.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_PCTAllTask3.setValue(Double.valueOf(outparam.getString(PCTAreaProgressGraphSCHParams.PROGRESS_RATE)));
                hbc_PCTAllTask3.createChart(httpRequest);
                txt_ProgressRate3.setValue(outparam.get(PCTAreaProgressGraphSCHParams.PROGRESS_RATE));
                txt_ProgressRate3.setReadOnly(true);
            }
            if (i == NUMBER_THREE)
            {

                lst_PCTAreaProgress4.setVisible(true);
                hbc_PCTAllTask4.setVisible(true);
                txt_ProgressRate4.setVisible(true);
                lbl_Percent4.setVisible(true);

                ListCellLine line = _lcm_lst_PCTAreaProgress4.getNewLine();
                line.setValue(KEY_LST_AREA_NO_4, outparam.get(PCTAreaProgressGraphSCHParams.AREA_NO));
                line.setValue(KEY_LST_ZONE_NO_4, outparam.get(PCTAreaProgressGraphSCHParams.ZONE_NO));
                line.setValue(KEY_LST_LINE_COUNT_4, outparam.get(PCTAreaProgressGraphSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_4, outparam.get(PCTAreaProgressGraphSCHParams.LOT_COUNT));
                lst_PCTAreaProgress4_SetLineToolTip(line);
                _lcm_lst_PCTAreaProgress4.add(line);

                lst_PCTAreaProgress4.setReverseView(false);
                hbc_PCTAllTask4.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_PCTAllTask4.setValue(Double.valueOf(outparam.getString(PCTAreaProgressGraphSCHParams.PROGRESS_RATE)));
                hbc_PCTAllTask4.createChart(httpRequest);
                txt_ProgressRate4.setValue(outparam.get(PCTAreaProgressGraphSCHParams.PROGRESS_RATE));
                txt_ProgressRate4.setReadOnly(true);
            }

            // ボタン制御
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(outparam.getString(PCTAreaProgressGraphSCHParams.BUTTON_CONTROL_FLAG)))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(outparam.getString(PCTAreaProgressGraphSCHParams.BUTTON_CONTROL_FLAG)))
            {

                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(outparam.getString(PCTAreaProgressGraphSCHParams.BUTTON_CONTROL_FLAG)))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(outparam.getString(PCTAreaProgressGraphSCHParams.BUTTON_CONTROL_FLAG)))
            {

                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(true);
            }

            i++;
        }

        if (ps.size() < MAX_DISPLAY_PROGRESS)
        {
            // 非表示
            for (int j = ps.size(); j < MAX_DISPLAY_PROGRESS; j++)
            {
                if (j == NUMBER_ZERO)
                {
                    lst_PCTAreaProgress1.setVisible(false);
                    hbc_PCTAllTask1.setVisible(false);
                    txt_ProgressRate1.setVisible(false);
                    lbl_Percent1.setVisible(false);
                }
                if (j == NUMBER_ONE)
                {
                    lst_PCTAreaProgress2.setVisible(false);
                    hbc_PCTAllTask2.setVisible(false);
                    txt_ProgressRate2.setVisible(false);
                    lbl_Percent2.setVisible(false);
                }
                if (j == NUMBER_TWO)
                {
                    lst_PCTAreaProgress3.setVisible(false);
                    hbc_PCTAllTask3.setVisible(false);
                    txt_ProgressRate3.setVisible(false);
                    lbl_Percent3.setVisible(false);
                }
                if (j == NUMBER_THREE)
                {
                    lst_PCTAreaProgress4.setVisible(false);
                    hbc_PCTAllTask4.setVisible(false);
                    txt_ProgressRate4.setVisible(false);
                    lbl_Percent4.setVisible(false);
                }
            }
        }
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

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[] {
                rdo_Auto,
                rdo_Manual
        }, locale);

        // initialize lst_PCTAreaProgress1.
        _lcm_lst_PCTAreaProgress1 = new ListCellModel(lst_PCTAreaProgress1, LST_PCTAREAPROGRESS1_KEYS, locale);
        _lcm_lst_PCTAreaProgress1.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_PCTAreaProgress1.setToolTipVisible(KEY_LST_ZONE_NO, true);
        _lcm_lst_PCTAreaProgress1.setToolTipVisible(KEY_LST_LINE_COUNT, true);
        _lcm_lst_PCTAreaProgress1.setToolTipVisible(KEY_LST_LOT_COUNT, true);

        // initialize lst_PCTAreaProgress2.
        _lcm_lst_PCTAreaProgress2 = new ListCellModel(lst_PCTAreaProgress2, LST_PCTAREAPROGRESS2_KEYS, locale);
        _lcm_lst_PCTAreaProgress2.setToolTipVisible(KEY_LST_AREA_NO_2, true);
        _lcm_lst_PCTAreaProgress2.setToolTipVisible(KEY_LST_ZONE_NO_2, true);
        _lcm_lst_PCTAreaProgress2.setToolTipVisible(KEY_LST_LINE_COUNT_2, true);
        _lcm_lst_PCTAreaProgress2.setToolTipVisible(KEY_LST_LOT_COUNT_2, true);

        // initialize lst_PCTAreaProgress3.
        _lcm_lst_PCTAreaProgress3 = new ListCellModel(lst_PCTAreaProgress3, LST_PCTAREAPROGRESS3_KEYS, locale);
        _lcm_lst_PCTAreaProgress3.setToolTipVisible(KEY_LST_AREA_NO_3, true);
        _lcm_lst_PCTAreaProgress3.setToolTipVisible(KEY_LST_ZONE_NO_3, true);
        _lcm_lst_PCTAreaProgress3.setToolTipVisible(KEY_LST_LINE_COUNT_3, true);
        _lcm_lst_PCTAreaProgress3.setToolTipVisible(KEY_LST_LOT_COUNT_3, true);

        // initialize lst_PCTAreaProgress4.
        _lcm_lst_PCTAreaProgress4 = new ListCellModel(lst_PCTAreaProgress4, LST_PCTAREAPROGRESS4_KEYS, locale);
        _lcm_lst_PCTAreaProgress4.setToolTipVisible(KEY_LST_AREA_NO_4, true);
        _lcm_lst_PCTAreaProgress4.setToolTipVisible(KEY_LST_ZONE_NO_4, true);
        _lcm_lst_PCTAreaProgress4.setToolTipVisible(KEY_LST_LINE_COUNT_4, true);
        _lcm_lst_PCTAreaProgress4.setToolTipVisible(KEY_LST_LOT_COUNT_4, true);

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
    private void lst_PCTAreaProgress1_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0043", KEY_LST_AREA_NO);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTAreaProgress2_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0043", KEY_LST_AREA_NO_2);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTAreaProgress3_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0043", KEY_LST_AREA_NO_3);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTAreaProgress4_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-P0043", KEY_LST_AREA_NO_4);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_ConsignorCode);

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
        PCTAreaProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAreaProgressGraphSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK:ここから修正          
            // set input parameters.
            PCTAreaProgressGraphSCHParams inparam = new PCTAreaProgressGraphSCHParams();

            // SCH call.
            Params initOutparam = sch.initFind(inparam);

            if (initOutparam == null)
            {
                clearView();
                rdo_Manual.setChecked(true);
                setRegularTransmission();
            }
            else
            {
                txt_ConsignorCode.setValue(initOutparam.get(PCTAreaProgressGraphSCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(initOutparam.get(PCTAreaProgressGraphSCHParams.CONSIGNOR_NAME));

                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
                viewState.setObject(ViewStateKeys.VS_BATCH_NO, txt_BatchNo.getText());
                viewState.setObject(ViewStateKeys.VS_BATCH_SEQ_NO, txt_BatchSeqNo.getText());

                // 荷主コードがあれば、リストセルを初期表示する
                if (!StringUtil.isBlank(txt_ConsignorCode.getText()))
                {
                    // set input parameters.
                    inparam = new PCTAreaProgressGraphSCHParams();
                    //DFKLOOK:ここまで修正

                    inparam.set(PCTAreaProgressGraphSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                    inparam.set(PCTAreaProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

                    // SCH call.
                    List<Params> outparams = sch.query(inparam);
                    message.setMsgResourceKey(sch.getMessage());

                    // DFKLOOK:ここから修正
                    if (outparams == null || outparams.size() == 0)
                    {
                        // 表示データを削除
                        clearView();

                        btn_PrevPage.setEnabled(false);
                        btn_NextPage.setEnabled(false);

                        // 6003011=対象データはありませんでした。
                        message.setMsgResourceKey("6003011");
                        return;
                    }

                    setViewData(outparams);
                }
                // clear.
                rdo_Auto.setChecked(true);
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
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAreaProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAreaProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAreaProgressGraphSCHParams inparam = new PCTAreaProgressGraphSCHParams();
            inparam.set(PCTAreaProgressGraphSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAreaProgressGraphSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTAreaProgressGraphSCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(PCTAreaProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);
            inparam.set(PCTAreaProgressGraphSCHParams.BUTTON_CONTROL_FLAG, "");

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            if (outparams == null || outparams.size() == 0)
            {
                // 表示データを削除
                clearView();


                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);

                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey("6003011");
                return;
            }

            setViewData(outparams);
            setRegularTransmission();

            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getValue());
            viewState.setObject(ViewStateKeys.VS_BATCH_NO, txt_BatchNo.getText());
            viewState.setObject(ViewStateKeys.VS_BATCH_SEQ_NO, txt_BatchSeqNo.getText());
            //DFKLOOK:ここまで修正
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
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAreaProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAreaProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAreaProgressGraphSCHParams inparam = new PCTAreaProgressGraphSCHParams();
            inparam.set(PCTAreaProgressGraphSCHParams.CONSIGNOR_CODE,
                    viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTAreaProgressGraphSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.VS_BATCH_NO));
            inparam.set(PCTAreaProgressGraphSCHParams.BATCH_SEQ_NO, viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));
            inparam.set(PCTAreaProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_PREVIOUS_PAGE);

            // DFKLOOK:ここから修正
            // エリアNo.
            inparam.set(PCTAreaProgressGraphSCHParams.AREA_NO, lst_PCTAreaProgress1.getValue(1));
            // ゾーンNo.
            inparam.set(PCTAreaProgressGraphSCHParams.ZONE_NO, lst_PCTAreaProgress1.getValue(2));

            txt_ConsignorCode.setValue(viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            txt_ConsignorName.setValue(viewState.getObject(ViewStateKeys.VS_CONSIGNOR_NAME));
            txt_BatchNo.setValue(viewState.getObject(ViewStateKeys.VS_BATCH_NO));
            txt_BatchSeqNo.setValue(viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());


            // DFKLOOK:ここから修正
            if (outparams == null || outparams.size() == 0)
            {
                // 表示データを削除
                clearView();


                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);

                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey("6003011");
                return;
            }

            setViewData(outparams);
            //DFKLOOK:ここまで修正
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
    private void btn_NextPage_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAreaProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAreaProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAreaProgressGraphSCHParams inparam = new PCTAreaProgressGraphSCHParams();
            inparam.set(PCTAreaProgressGraphSCHParams.CONSIGNOR_CODE,
                    viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTAreaProgressGraphSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.VS_BATCH_NO));
            inparam.set(PCTAreaProgressGraphSCHParams.BATCH_SEQ_NO, viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));
            inparam.set(PCTAreaProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_NEXT_PAGE);

            // DFKLOOK:ここから修正
            if (lst_PCTAreaProgress4.getVisible())
            {
                // リストセル下段のデータを基準とします。
                // エリアNo.
                inparam.set(PCTAreaProgressGraphSCHParams.AREA_NO, lst_PCTAreaProgress4.getValue(1));
                // ゾーンNo.
                inparam.set(PCTAreaProgressGraphSCHParams.ZONE_NO, lst_PCTAreaProgress4.getValue(2));
            }
            else if (lst_PCTAreaProgress3.getVisible())
            {
                // リストセル下段のデータを基準とします。
                // エリアNo.
                inparam.set(PCTAreaProgressGraphSCHParams.AREA_NO, lst_PCTAreaProgress3.getValue(1));
                // ゾーンNo.
                inparam.set(PCTAreaProgressGraphSCHParams.ZONE_NO, lst_PCTAreaProgress3.getValue(2));
            }
            else if (lst_PCTAreaProgress2.getVisible())
            {
                // リストセル下段のデータを基準とします。
                // エリアNo.
                inparam.set(PCTAreaProgressGraphSCHParams.AREA_NO, lst_PCTAreaProgress2.getValue(1));
                // ゾーンNo.
                inparam.set(PCTAreaProgressGraphSCHParams.ZONE_NO, lst_PCTAreaProgress2.getValue(2));
            }
            else
            {
                // 下段のデータが存在しない場合、上段を基準とします。
                // エリアNo.
                inparam.set(PCTAreaProgressGraphSCHParams.AREA_NO, lst_PCTAreaProgress1.getValue(1));
                // ゾーンNo.
                inparam.set(PCTAreaProgressGraphSCHParams.ZONE_NO, lst_PCTAreaProgress1.getValue(2));
            }

            txt_ConsignorCode.setValue(viewState.getObject(ViewStateKeys.VS_CONSIGNOR_CODE));
            txt_ConsignorName.setValue(viewState.getObject(ViewStateKeys.VS_CONSIGNOR_NAME));
            txt_BatchNo.setValue(viewState.getObject(ViewStateKeys.VS_BATCH_NO));
            txt_BatchSeqNo.setValue(viewState.getObject(ViewStateKeys.VS_BATCH_SEQ_NO));
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            if (outparams == null || outparams.size() == 0)
            {
                // 表示データを削除
                clearView();


                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);

                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey("6003011");
                return;
            }
            setViewData(outparams);
            //DFKLOOK:ここまで修正
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
