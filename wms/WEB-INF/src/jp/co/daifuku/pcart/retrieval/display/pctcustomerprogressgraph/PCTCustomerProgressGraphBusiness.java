// $Id: PCTCustomerProgressGraphBusiness.java 6904 2010-01-26 03:01:48Z kumano $
package jp.co.daifuku.pcart.retrieval.display.pctcustomerprogressgraph;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.base.controller.PCTConsignorController;
import jp.co.daifuku.pcart.retrieval.listbox.regularCustomer.LstRegularCustomerParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTCustomerProgressGraphSCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTCustomerProgressGraphSCHParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * 出荷先別作業進捗(横棒)の画面処理を行います。
 *
 * Designer : H.Okayama<BR>
 * Maker : H.Okayama<BR>
 * @version $Revision: 6904 $, $Date:: 2010-01-26 12:01:48 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class PCTCustomerProgressGraphBusiness
        extends PCTCustomerProgressGraph
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_PCTCustomerProgress1(LST_CUSTOMER_CODE_1) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE_1 =
            new ListCellKey("LST_CUSTOMER_CODE_1", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress1(LST_CUSTOMER_NAME_1) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME_1 =
            new ListCellKey("LST_CUSTOMER_NAME_1", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress1(LST_ORDER_COUNT_1) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_1 =
            new ListCellKey("LST_ORDER_COUNT_1", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress1(LST_BOX_COUNT_1) */
    private static final ListCellKey KEY_LST_BOX_COUNT_1 =
            new ListCellKey("LST_BOX_COUNT_1", new NumberCellColumn(0), true, false);

    /** lst_PCTCustomerProgress1(LST_LINE_COUNT_1) */
    private static final ListCellKey KEY_LST_LINE_COUNT_1 =
            new ListCellKey("LST_LINE_COUNT_1", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress1(LST_LOT_COUNT_1) */
    private static final ListCellKey KEY_LST_LOT_COUNT_1 =
            new ListCellKey("LST_LOT_COUNT_1", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress2(LST_CUSTOMER_CODE_2) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE_2 =
            new ListCellKey("LST_CUSTOMER_CODE_2", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress2(LST_CUSTOMER_NAME_2) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME_2 =
            new ListCellKey("LST_CUSTOMER_NAME_2", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress2(LST_ORDER_COUNT_2) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_2 =
            new ListCellKey("LST_ORDER_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress2(LST_BOX_COUNT_2) */
    private static final ListCellKey KEY_LST_BOX_COUNT_2 =
            new ListCellKey("LST_BOX_COUNT_2", new NumberCellColumn(0), true, false);

    /** lst_PCTCustomerProgress2(LST_LINE_COUNT_2) */
    private static final ListCellKey KEY_LST_LINE_COUNT_2 =
            new ListCellKey("LST_LINE_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress2(LST_LOT_COUNT_2) */
    private static final ListCellKey KEY_LST_LOT_COUNT_2 =
            new ListCellKey("LST_LOT_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress3(LST_CUSTOMER_CODE_3) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE_3 =
            new ListCellKey("LST_CUSTOMER_CODE_3", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress3(LST_CUSTOMER_NAME_3) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME_3 =
            new ListCellKey("LST_CUSTOMER_NAME_3", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress3(LST_ORDER_COUNT_3) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_3 =
            new ListCellKey("LST_ORDER_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress3(LST_BOX_COUNT_3) */
    private static final ListCellKey KEY_LST_BOX_COUNT_3 =
            new ListCellKey("LST_BOX_COUNT_3", new NumberCellColumn(0), true, false);

    /** lst_PCTCustomerProgress3(LST_LINE_COUNT_3) */
    private static final ListCellKey KEY_LST_LINE_COUNT_3 =
            new ListCellKey("LST_LINE_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress3(LST_LOT_COUNT_3) */
    private static final ListCellKey KEY_LST_LOT_COUNT_3 =
            new ListCellKey("LST_LOT_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress4(LST_CUSTOMER_CODE_4) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE_4 =
            new ListCellKey("LST_CUSTOMER_CODE_4", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress4(LST_CUSTOMER_NAME_4) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME_4 =
            new ListCellKey("LST_CUSTOMER_NAME_4", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress4(LST_ORDER_COUNT_4) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_4 =
            new ListCellKey("LST_ORDER_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress4(LST_BOX_COUNT_4) */
    private static final ListCellKey KEY_LST_BOX_COUNT_4 =
            new ListCellKey("LST_BOX_COUNT_4", new NumberCellColumn(0), true, false);

    /** lst_PCTCustomerProgress4(LST_LINE_COUNT_4) */
    private static final ListCellKey KEY_LST_LINE_COUNT_4 =
            new ListCellKey("LST_LINE_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress4(LST_LOT_COUNT_4) */
    private static final ListCellKey KEY_LST_LOT_COUNT_4 =
            new ListCellKey("LST_LOT_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTCustomerProgress1 kyes */
    private static final ListCellKey[] LST_PCTCUSTOMERPROGRESS1_KEYS = {
            KEY_LST_CUSTOMER_CODE_1,
            KEY_LST_CUSTOMER_NAME_1,
            KEY_LST_ORDER_COUNT_1,
            KEY_LST_BOX_COUNT_1,
            KEY_LST_LINE_COUNT_1,
            KEY_LST_LOT_COUNT_1,
    };

    /** lst_PCTCustomerProgress2 kyes */
    private static final ListCellKey[] LST_PCTCUSTOMERPROGRESS2_KEYS = {
            KEY_LST_CUSTOMER_CODE_2,
            KEY_LST_CUSTOMER_NAME_2,
            KEY_LST_ORDER_COUNT_2,
            KEY_LST_BOX_COUNT_2,
            KEY_LST_LINE_COUNT_2,
            KEY_LST_LOT_COUNT_2,
    };

    /** lst_PCTCustomerProgress3 kyes */
    private static final ListCellKey[] LST_PCTCUSTOMERPROGRESS3_KEYS = {
            KEY_LST_CUSTOMER_CODE_3,
            KEY_LST_CUSTOMER_NAME_3,
            KEY_LST_ORDER_COUNT_3,
            KEY_LST_BOX_COUNT_3,
            KEY_LST_LINE_COUNT_3,
            KEY_LST_LOT_COUNT_3,
    };

    /** lst_PCTCustomerProgress4 kyes */
    private static final ListCellKey[] LST_PCTCUSTOMERPROGRESS4_KEYS = {
            KEY_LST_CUSTOMER_CODE_4,
            KEY_LST_CUSTOMER_NAME_4,
            KEY_LST_ORDER_COUNT_4,
            KEY_LST_BOX_COUNT_4,
            KEY_LST_LINE_COUNT_4,
            KEY_LST_LOT_COUNT_4,
    };

    // DFKLOOK:ここから修正
    /**
     * 出荷先コードのリストセル列番号
     */
    private static final int LST_CUSTOMER = 1;

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
    /** PullDownModel pul_AreaNo */
    private WmsAreaPullDownModel _pdm_pul_AreaNo;

    /** RadioButtonGroupModel ProgressDisplay */
    private RadioButtonGroup _grp_ProgressDisplay;

    /** ListCellModel lst_PCTCustomerProgress1 */
    private ListCellModel _lcm_lst_PCTCustomerProgress1;

    /** ListCellModel lst_PCTCustomerProgress2 */
    private ListCellModel _lcm_lst_PCTCustomerProgress2;

    /** ListCellModel lst_PCTCustomerProgress3 */
    private ListCellModel _lcm_lst_PCTCustomerProgress3;

    /** ListCellModel lst_PCTCustomerProgress4 */
    private ListCellModel _lcm_lst_PCTCustomerProgress4;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTCustomerProgressGraphBusiness()
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
        if (eventSource.equals("btn_P_Search_RegularCustomerCd_Click"))
        {
            // process call.
            btn_P_Search_RegularCustomerCd_Click_DlgBack(dialogParams);
        }
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
        txt_RegularCustomerCode.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressGraphSCHParams inparam = new PCTCustomerProgressGraphSCHParams();

            if (txt_ConsignorCode.getText().equals(viewState.getString(ViewStateKeys.VS_CONSIGNOR_CODE))
                    && txt_RegularCustomerCode.getText().equals(
                            viewState.getString(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE))
                    && _pdm_pul_AreaNo.getSelectedValue().equals(viewState.getString(ViewStateKeys.VS_AREA_NO))
                    && txt_BatchNo.getText().equals(viewState.getString(ViewStateKeys.VS_BATCH_NO))
                    && txt_BatchSeqNo.getText().equals(viewState.getString(ViewStateKeys.VS_BATCH_SEQ_NO)))
            {
                // 出荷先コード
                if (lst_PCTCustomerProgress1.getVisible())
                {
                    inparam.set(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE,
                            String.valueOf(lst_PCTCustomerProgress1.getValue(LST_CUSTOMER)));
                }
            }
            else
            {
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getValue());
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getValue());
                viewState.setObject(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
                viewState.setObject(ViewStateKeys.VS_REGULAR_CUSTOMER_NAME, txt_RegularCustomerName.getValue());
                viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_BATCH_NO, txt_BatchNo.getValue());
                viewState.setObject(ViewStateKeys.VS_BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            }
            inparam.set(PCTCustomerProgressGraphSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // 存在チェック
            if (outparams.size() == 0)
            {
                // 表示データを削除
                clearView();

                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);

                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey(WmsMessageFormatter.format(6003011));
                return;
            }

            // リストセット
            setViewData(outparams);

            // ボタン制御
            String button = outparams.get(0).getString(PCTCustomerProgressGraphSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {
                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {
                // 前頁ボタン(有効化)
                btn_PrevPage.setEnabled(true);
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {
                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
                // 次頁ボタン(有効化)
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {
                // 前頁ボタン(有効化)
                btn_PrevPage.setEnabled(true);
                // 次頁ボタン(有効化)
                btn_NextPage.setEnabled(true);
            }

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
    public void btn_P_Search_RegularCustomerCd_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_Search_RegularCustomerCd_Click_Process();
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void txt_ConsignorCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
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

            setFocus(txt_RegularCustomerCode);
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
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Auto_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_Auto_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Manual_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_Manual_Click_Process();
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
     * 自動更新か手動更新かを切り替えます。
     * 
     * @throws Exception
     *             全ての例外を報告します。
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
     * リストセルデータ、グラフデータの初期化を行います。<BR>
     * 
     * @throws Exception
     *             全ての例外を報告します。
     */
    protected void clearView()
            throws Exception
    {
        lst_PCTCustomerProgress1.clearRow();
        lst_PCTCustomerProgress1.setVisible(false);
        lst_PCTCustomerProgress2.clearRow();
        lst_PCTCustomerProgress2.setVisible(false);
        lst_PCTCustomerProgress3.clearRow();
        lst_PCTCustomerProgress3.setVisible(false);
        lst_PCTCustomerProgress4.clearRow();
        lst_PCTCustomerProgress4.setVisible(false);
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
    }

    /**
     * パラメータを取得し、画面に表示します。<BR>
     * 
     * @param viewParam グラフ、リストエリアに表示するパラメータ
     * @throws Exception 全ての例外を報告します。
     */
    protected void setViewData(List<Params> outParams)
            throws Exception
    {
        // 表示データを削除
        clearView();
        // 進捗率保持
        double rate = 0;
        // カウント変数
        int i = 0;
        // 進捗率計算変数
        BigDecimal bdRate = new BigDecimal(0);
        // リスト行
        ListCellLine line = null;

        // パラメータ件数分繰り返し
        for (Params param : outParams)
        {
            // 進捗率を取得
            bdRate = param.getBigDecimal(PCTCustomerProgressGraphSCHParams.PROGRESS_RATE);
            // BigDecimal → doubleへ変換
            rate = bdRate.divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP).doubleValue();

            // 1段目
            if (i == NUMBER_ZERO)
            {
                // リスト行
                // 行生成
                line = _lcm_lst_PCTCustomerProgress1.getNewLine();
                // 可視化
                _lcm_lst_PCTCustomerProgress1.getListCell().setVisible(true);
                // 進捗バー(可視化)
                hbc_PCTAllTask1.setVisible(true);
                // 進捗率(可視化)
                txt_ProgressRate1.setVisible(true);
                // %ラベル(可視化)
                lbl_Percent1.setVisible(true);

                // 設定(リスト)
                // 出荷先コード
                line.setValue(KEY_LST_CUSTOMER_CODE_1, param.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE));
                // 出荷先名称
                line.setValue(KEY_LST_CUSTOMER_NAME_1, param.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_NAME));
                // オーダー件数
                line.setValue(KEY_LST_ORDER_COUNT_1, param.get(PCTCustomerProgressGraphSCHParams.ORDER_COUNT));
                // 箱数
                line.setValue(KEY_LST_BOX_COUNT_1, param.get(PCTCustomerProgressGraphSCHParams.BOX_COUNT));
                // 行数
                line.setValue(KEY_LST_LINE_COUNT_1, param.get(PCTCustomerProgressGraphSCHParams.LINE_COUNT));
                // ロット数
                line.setValue(KEY_LST_LOT_COUNT_1, param.get(PCTCustomerProgressGraphSCHParams.LOT_COUNT));

                // 設定(進捗バー)
                hbc_PCTAllTask1.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_PCTAllTask1.setValue(rate);
                hbc_PCTAllTask1.createChart(httpRequest);

                // 進捗率
                txt_ProgressRate1.setValue(rate);
                txt_ProgressRate1.setReadOnly(true);

                // ToolTip
                lst_PCTCustomerProgress1_SetLineToolTip(line);

                // 行追加                
                _lcm_lst_PCTCustomerProgress1.add(line);
            }
            // 2段目
            else if (i == NUMBER_ONE)
            {
                // リスト行
                // 行生成
                line = _lcm_lst_PCTCustomerProgress2.getNewLine();
                // 可視化
                _lcm_lst_PCTCustomerProgress2.getListCell().setVisible(true);
                // 進捗バー(可視化)
                hbc_PCTAllTask2.setVisible(true);
                // 進捗率(可視化)
                txt_ProgressRate2.setVisible(true);
                // %ラベル(可視化)
                lbl_Percent2.setVisible(true);

                // 設定(リスト)
                // 出荷先コード
                line.setValue(KEY_LST_CUSTOMER_CODE_2, param.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE));
                // 出荷先名称
                line.setValue(KEY_LST_CUSTOMER_NAME_2, param.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_NAME));
                // オーダー件数
                line.setValue(KEY_LST_ORDER_COUNT_2, param.get(PCTCustomerProgressGraphSCHParams.ORDER_COUNT));
                // 箱数
                line.setValue(KEY_LST_BOX_COUNT_2, param.get(PCTCustomerProgressGraphSCHParams.BOX_COUNT));
                // 行数
                line.setValue(KEY_LST_LINE_COUNT_2, param.get(PCTCustomerProgressGraphSCHParams.LINE_COUNT));
                // ロット数
                line.setValue(KEY_LST_LOT_COUNT_2, param.get(PCTCustomerProgressGraphSCHParams.LOT_COUNT));

                // 設定(進捗バー)
                hbc_PCTAllTask2.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_PCTAllTask2.setValue(rate);
                hbc_PCTAllTask2.createChart(httpRequest);

                // 進捗率
                txt_ProgressRate2.setValue(rate);
                txt_ProgressRate2.setReadOnly(true);

                // ToolTip
                lst_PCTCustomerProgress2_SetLineToolTip(line);

                // 行追加                
                _lcm_lst_PCTCustomerProgress2.add(line);
            }
            // 3段目
            else if (i == NUMBER_TWO)
            {
                // リスト行
                // 行生成
                line = _lcm_lst_PCTCustomerProgress3.getNewLine();
                // 可視化
                _lcm_lst_PCTCustomerProgress3.getListCell().setVisible(true);
                // 進捗バー(可視化)
                hbc_PCTAllTask3.setVisible(true);
                // 進捗率(可視化)
                txt_ProgressRate3.setVisible(true);
                // %ラベル(可視化)
                lbl_Percent3.setVisible(true);

                // 設定(リスト)
                // 出荷先コード
                line.setValue(KEY_LST_CUSTOMER_CODE_3, param.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE));
                // 出荷先名称
                line.setValue(KEY_LST_CUSTOMER_NAME_3, param.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_NAME));
                // オーダー件数
                line.setValue(KEY_LST_ORDER_COUNT_3, param.get(PCTCustomerProgressGraphSCHParams.ORDER_COUNT));
                // 箱数
                line.setValue(KEY_LST_BOX_COUNT_3, param.get(PCTCustomerProgressGraphSCHParams.BOX_COUNT));
                // 行数
                line.setValue(KEY_LST_LINE_COUNT_3, param.get(PCTCustomerProgressGraphSCHParams.LINE_COUNT));
                // ロット数
                line.setValue(KEY_LST_LOT_COUNT_3, param.get(PCTCustomerProgressGraphSCHParams.LOT_COUNT));

                // 設定(進捗バー)
                hbc_PCTAllTask3.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_PCTAllTask3.setValue(rate);
                hbc_PCTAllTask3.createChart(httpRequest);

                // 進捗率
                txt_ProgressRate3.setValue(rate);
                txt_ProgressRate3.setReadOnly(true);

                // ToolTip
                lst_PCTCustomerProgress3_SetLineToolTip(line);

                // 行追加                
                _lcm_lst_PCTCustomerProgress3.add(line);
            }
            // 4段目
            else if (i == NUMBER_THREE)
            {
                // リスト行
                // 行生成
                line = _lcm_lst_PCTCustomerProgress4.getNewLine();
                // 可視化
                _lcm_lst_PCTCustomerProgress4.getListCell().setVisible(true);
                // 進捗バー(可視化)
                hbc_PCTAllTask4.setVisible(true);
                // 進捗率(可視化)
                txt_ProgressRate4.setVisible(true);
                // %ラベル(可視化)
                lbl_Percent4.setVisible(true);

                // 設定(リスト)
                // 出荷先コード
                line.setValue(KEY_LST_CUSTOMER_CODE_4, param.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE));
                // 出荷先名称
                line.setValue(KEY_LST_CUSTOMER_NAME_4, param.get(PCTCustomerProgressGraphSCHParams.CUSTOMER_NAME));
                // オーダー件数
                line.setValue(KEY_LST_ORDER_COUNT_4, param.get(PCTCustomerProgressGraphSCHParams.ORDER_COUNT));
                // 箱数
                line.setValue(KEY_LST_BOX_COUNT_4, param.get(PCTCustomerProgressGraphSCHParams.BOX_COUNT));
                // 行数
                line.setValue(KEY_LST_LINE_COUNT_4, param.get(PCTCustomerProgressGraphSCHParams.LINE_COUNT));
                // ロット数
                line.setValue(KEY_LST_LOT_COUNT_4, param.get(PCTCustomerProgressGraphSCHParams.LOT_COUNT));

                // 設定(進捗バー)
                hbc_PCTAllTask4.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_PCTAllTask4.setValue(rate);
                hbc_PCTAllTask4.createChart(httpRequest);

                // 進捗率
                txt_ProgressRate4.setValue(rate);
                txt_ProgressRate4.setReadOnly(true);

                // ToolTip
                lst_PCTCustomerProgress4_SetLineToolTip(line);

                // 行追加                
                _lcm_lst_PCTCustomerProgress4.add(line);
            }

            // カウント変数(インクリメント)
            i++;
        }

        // リストセルがリスト最大表示件数を上回っていた場合
        if (outParams.size() < MAX_DISPLAY_PROGRESS)
        {
            // カウント変数初期化
            i = 0;

            // リストの不可視化
            for (i = outParams.size(); i < MAX_DISPLAY_PROGRESS; i++)
            {
                // 1段目
                if (i == NUMBER_ZERO)
                {
                    // リスト
                    lst_PCTCustomerProgress1.setVisible(false);
                    // 進捗バー
                    hbc_PCTAllTask1.setVisible(false);
                    // 進捗率
                    txt_ProgressRate1.setVisible(false);
                    // %ラベル
                    lbl_Percent1.setVisible(false);
                }
                // 2段目
                else if (i == NUMBER_ONE)
                {
                    // リスト
                    lst_PCTCustomerProgress2.setVisible(false);
                    // 進捗バー
                    hbc_PCTAllTask2.setVisible(false);
                    // 進捗率
                    txt_ProgressRate2.setVisible(false);
                    // %ラベル
                    lbl_Percent2.setVisible(false);
                }
                // 3段目
                else if (i == NUMBER_TWO)
                {
                    // リスト
                    lst_PCTCustomerProgress3.setVisible(false);
                    // 進捗バー
                    hbc_PCTAllTask3.setVisible(false);
                    // 進捗率
                    txt_ProgressRate3.setVisible(false);
                    // %ラベル
                    lbl_Percent3.setVisible(false);
                }
                // 4段目
                else if (i == NUMBER_THREE)
                {
                    // リスト
                    lst_PCTCustomerProgress4.setVisible(false);
                    // 進捗バー
                    hbc_PCTAllTask4.setVisible(false);
                    // 進捗率
                    txt_ProgressRate4.setVisible(false);
                    // %ラベル
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_AreaNo.
        _pdm_pul_AreaNo = new WmsAreaPullDownModel(pul_AreaNo, locale, ui);

        // initialize ProgressDisplay.
        _grp_ProgressDisplay = new RadioButtonGroup(new RadioButton[] {
                rdo_Auto,
                rdo_Manual
        }, locale);

        // initialize lst_PCTCustomerProgress1.
        _lcm_lst_PCTCustomerProgress1 =
                new ListCellModel(lst_PCTCustomerProgress1, LST_PCTCUSTOMERPROGRESS1_KEYS, locale);
        _lcm_lst_PCTCustomerProgress1.setToolTipVisible(KEY_LST_CUSTOMER_CODE_1, true);
        _lcm_lst_PCTCustomerProgress1.setToolTipVisible(KEY_LST_CUSTOMER_NAME_1, true);
        _lcm_lst_PCTCustomerProgress1.setToolTipVisible(KEY_LST_ORDER_COUNT_1, true);
        _lcm_lst_PCTCustomerProgress1.setToolTipVisible(KEY_LST_BOX_COUNT_1, true);
        _lcm_lst_PCTCustomerProgress1.setToolTipVisible(KEY_LST_LINE_COUNT_1, true);
        _lcm_lst_PCTCustomerProgress1.setToolTipVisible(KEY_LST_LOT_COUNT_1, true);

        // initialize lst_PCTCustomerProgress2.
        _lcm_lst_PCTCustomerProgress2 =
                new ListCellModel(lst_PCTCustomerProgress2, LST_PCTCUSTOMERPROGRESS2_KEYS, locale);
        _lcm_lst_PCTCustomerProgress2.setToolTipVisible(KEY_LST_CUSTOMER_CODE_2, true);
        _lcm_lst_PCTCustomerProgress2.setToolTipVisible(KEY_LST_CUSTOMER_NAME_2, true);
        _lcm_lst_PCTCustomerProgress2.setToolTipVisible(KEY_LST_ORDER_COUNT_2, true);
        _lcm_lst_PCTCustomerProgress2.setToolTipVisible(KEY_LST_BOX_COUNT_2, true);
        _lcm_lst_PCTCustomerProgress2.setToolTipVisible(KEY_LST_LINE_COUNT_2, true);
        _lcm_lst_PCTCustomerProgress2.setToolTipVisible(KEY_LST_LOT_COUNT_2, true);

        // initialize lst_PCTCustomerProgress3.
        _lcm_lst_PCTCustomerProgress3 =
                new ListCellModel(lst_PCTCustomerProgress3, LST_PCTCUSTOMERPROGRESS3_KEYS, locale);
        _lcm_lst_PCTCustomerProgress3.setToolTipVisible(KEY_LST_CUSTOMER_CODE_3, true);
        _lcm_lst_PCTCustomerProgress3.setToolTipVisible(KEY_LST_CUSTOMER_NAME_3, true);
        _lcm_lst_PCTCustomerProgress3.setToolTipVisible(KEY_LST_ORDER_COUNT_3, true);
        _lcm_lst_PCTCustomerProgress3.setToolTipVisible(KEY_LST_BOX_COUNT_3, true);
        _lcm_lst_PCTCustomerProgress3.setToolTipVisible(KEY_LST_LINE_COUNT_3, true);
        _lcm_lst_PCTCustomerProgress3.setToolTipVisible(KEY_LST_LOT_COUNT_3, true);

        // initialize lst_PCTCustomerProgress4.
        _lcm_lst_PCTCustomerProgress4 =
                new ListCellModel(lst_PCTCustomerProgress4, LST_PCTCUSTOMERPROGRESS4_KEYS, locale);
        _lcm_lst_PCTCustomerProgress4.setToolTipVisible(KEY_LST_CUSTOMER_CODE_4, true);
        _lcm_lst_PCTCustomerProgress4.setToolTipVisible(KEY_LST_CUSTOMER_NAME_4, true);
        _lcm_lst_PCTCustomerProgress4.setToolTipVisible(KEY_LST_ORDER_COUNT_4, true);
        _lcm_lst_PCTCustomerProgress4.setToolTipVisible(KEY_LST_BOX_COUNT_4, true);
        _lcm_lst_PCTCustomerProgress4.setToolTipVisible(KEY_LST_LINE_COUNT_4, true);
        _lcm_lst_PCTCustomerProgress4.setToolTipVisible(KEY_LST_LOT_COUNT_4, true);

    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // load pul_AreaNo.
            _pdm_pul_AreaNo.init(conn, AreaType.FLOOR, StationType.ALL, "", true);

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
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTCustomerProgress1_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME_1);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTCustomerProgress2_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME_2);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTCustomerProgress3_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME_3);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_PCTCustomerProgress4_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME_4);
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
        PCTCustomerProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressGraphSCHParams inparam = new PCTCustomerProgressGraphSCHParams();

            // SCH call.
            // DFKLOOK:ここから修正
            // query → initFind
            Params outparam = sch.initFind(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // 前頁ボタン(無効化)
            btn_PrevPage.setEnabled(false);
            // 次頁ボタン(無効化)
            btn_NextPage.setEnabled(false);

            // output display.
            if (outparam != null)
            {
                txt_ConsignorCode.setValue(outparam.get(PCTCustomerProgressGraphSCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(outparam.get(PCTCustomerProgressGraphSCHParams.CONSIGNOR_NAME));
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE,
                        outparam.get(PCTCustomerProgressGraphSCHParams.CONSIGNOR_CODE));
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME,
                        outparam.get(PCTCustomerProgressGraphSCHParams.CONSIGNOR_NAME));

                // clear.
                rdo_Auto.setChecked(true);

                // 定期送信フラグの切り替え
                setRegularTransmission();
            }
            else
            {
                // clear.
                rdo_Manual.setChecked(true);

                // 定期送信フラグの切り替え
                setRegularTransmission();
            }

            // 表示データを削除
            clearView();

            // 荷主コードがあればリストを表示する
            if (!StringUtil.isBlank(txt_ConsignorCode.getText()))
            {
                btn_Display_Click(null);
            }
            // DFKLOOK:ここまで修正

            // clear.
            txt_RegularCustomerCode.setValue(null);
            txt_RegularCustomerName.setValue(null);
            _pdm_pul_AreaNo.setSelectedValue(null);
            txt_BatchNo.setValue(null);
            txt_BatchSeqNo.setValue(null);

            // set focus.
            setFocus(txt_ConsignorCode);
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
            // 表示データを削除
            clearView();
            // 表示ボタン(無効化)
            btn_Display.setEnabled(false);
            // 前頁ボタン(無効化)
            btn_PrevPage.setEnabled(false);
            // 次頁ボタン(無効化)
            btn_NextPage.setEnabled(false);
            // 定期送信フラグ(無効化)
            setRegularTransmission(false);
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
    private void btn_P_Search_RegularCustomerCd_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstRegularCustomerParams inparam = new LstRegularCustomerParams();
        inparam.set(LstRegularCustomerParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
        inparam.set(LstRegularCustomerParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
        inparam.set(LstRegularCustomerParams.SEARCHTABLE, InParameter.SEARCH_TABLE_PLAN);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Search_RegularCustomerCd_Click");
        redirect("/pcart/retrieval/listbox/regularCustomer/LstRegularCustomer.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_Search_RegularCustomerCd_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstRegularCustomerParams outparam = new LstRegularCustomerParams(dialogParams);
        txt_RegularCustomerCode.setValue(outparam.get(LstRegularCustomerParams.REGULAR_CUSTOMER_CODE));
        txt_RegularCustomerName.setValue(outparam.get(LstRegularCustomerParams.REGULAR_CUSTOMER_NAME));

        // set focus.
        setFocus(txt_RegularCustomerCode);

    }

    /**
     *
     * @throws Exception
     */
    private void rdo_Auto_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // clear.
        rdo_Auto.setChecked(true);

        // 定期送信フラグの切り替え
        setRegularTransmission();

        // input validation.
        txt_ConsignorCode.validate(this, true);

        // 入力された内容をViewStateに保持
        viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
        viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getText());
        viewState.setObject(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getText());
        viewState.setObject(ViewStateKeys.VS_REGULAR_CUSTOMER_NAME, txt_RegularCustomerName.getText());
        viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        viewState.setObject(ViewStateKeys.VS_BATCH_NO, txt_BatchNo.getText());
        viewState.setObject(ViewStateKeys.VS_BATCH_SEQ_NO, txt_BatchSeqNo.getText());
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void rdo_Manual_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // clear.
        rdo_Manual.setChecked(true);

        // 定期送信フラグの切り替え
        setRegularTransmission();

        // input validation.
        txt_ConsignorCode.validate(this, false);
        // DFKLOOK:ここまで修正
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
        txt_RegularCustomerCode.validate(this, false);
        txt_BatchNo.validate(this, false);
        txt_BatchSeqNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressGraphSCHParams inparam = new PCTCustomerProgressGraphSCHParams();
            inparam.set(PCTCustomerProgressGraphSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_SEQ_NO, txt_BatchSeqNo.getValue());
            inparam.set(PCTCustomerProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // DFKLOOK:ここから修正
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getText());
            viewState.setObject(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE, txt_RegularCustomerCode.getText());
            viewState.setObject(ViewStateKeys.VS_REGULAR_CUSTOMER_NAME, txt_RegularCustomerName.getText());
            viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_BATCH_NO, txt_BatchNo.getText());
            viewState.setObject(ViewStateKeys.VS_BATCH_SEQ_NO, txt_BatchSeqNo.getText());
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // 存在チェック
            if (outparams.size() == 0)
            {
                // 表示データを削除
                clearView();

                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);

                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey(WmsMessageFormatter.format(6003011));
                return;
            }

            // リストセット
            setViewData(outparams);

            // ボタン制御
            String button = outparams.get(0).getString(PCTCustomerProgressGraphSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {
                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {
                // 前頁ボタン(有効化)
                btn_PrevPage.setEnabled(true);
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {
                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
                // 次頁ボタン(有効化)
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {
                // 前頁ボタン(有効化)
                btn_PrevPage.setEnabled(true);
                // 次頁ボタン(有効化)
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
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressGraphSCHParams inparam = new PCTCustomerProgressGraphSCHParams();
            inparam.set(PCTCustomerProgressGraphSCHParams.CONSIGNOR_CODE,
                    viewState.getString(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTCustomerProgressGraphSCHParams.REGULAR_CUSTOMER_CODE,
                    viewState.getString(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE));
            inparam.set(PCTCustomerProgressGraphSCHParams.AREA_NO, viewState.getString(ViewStateKeys.VS_AREA_NO));
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_NO, viewState.getString(ViewStateKeys.VS_BATCH_NO));
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_SEQ_NO,
                    viewState.getString(ViewStateKeys.VS_BATCH_SEQ_NO));
            inparam.set(PCTCustomerProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_PREVIOUS_PAGE);

            // DFKLOOK:ここから修正
            // 出荷先コード
            inparam.set(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE,
                    String.valueOf(lst_PCTCustomerProgress1.getValue(LST_CUSTOMER)));

            txt_ConsignorCode.setValue(viewState.getString(ViewStateKeys.VS_CONSIGNOR_CODE));
            txt_ConsignorName.setValue(viewState.getString(ViewStateKeys.VS_CONSIGNOR_NAME));
            txt_RegularCustomerCode.setValue(viewState.getString(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE));
            txt_RegularCustomerName.setValue(viewState.getString(ViewStateKeys.VS_REGULAR_CUSTOMER_NAME));
            _pdm_pul_AreaNo.setSelectedValue(viewState.getString(ViewStateKeys.VS_AREA_NO));
            txt_BatchNo.setValue(viewState.getString(ViewStateKeys.VS_BATCH_NO));
            txt_BatchSeqNo.setValue(viewState.getString(ViewStateKeys.VS_BATCH_SEQ_NO));
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // 存在チェック
            if (outparams.size() == 0)
            {
                // 表示データを削除
                clearView();

                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);

                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey(WmsMessageFormatter.format(6003011));
                return;
            }

            // リストセット
            setViewData(outparams);

            // ボタン制御
            String button = outparams.get(0).getString(PCTCustomerProgressGraphSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {
                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {
                // 前頁ボタン(有効化)
                btn_PrevPage.setEnabled(true);
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {
                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
                // 次頁ボタン(有効化)
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {
                // 前頁ボタン(有効化)
                btn_PrevPage.setEnabled(true);
                // 次頁ボタン(有効化)
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
    private void btn_NextPage_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTCustomerProgressGraphSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTCustomerProgressGraphSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTCustomerProgressGraphSCHParams inparam = new PCTCustomerProgressGraphSCHParams();
            inparam.set(PCTCustomerProgressGraphSCHParams.CONSIGNOR_CODE,
                    viewState.getString(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTCustomerProgressGraphSCHParams.REGULAR_CUSTOMER_CODE,
                    viewState.getString(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE));
            inparam.set(PCTCustomerProgressGraphSCHParams.AREA_NO, viewState.getString(ViewStateKeys.VS_AREA_NO));
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_NO, viewState.getString(ViewStateKeys.VS_BATCH_NO));
            inparam.set(PCTCustomerProgressGraphSCHParams.BATCH_SEQ_NO,
                    viewState.getString(ViewStateKeys.VS_BATCH_SEQ_NO));
            inparam.set(PCTCustomerProgressGraphSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_NEXT_PAGE);

            // DFKLOOK:ここから修正
            // 出荷先コード
            if (lst_PCTCustomerProgress4.getVisible())
            {
                // リストセル下段のデータを基準とします。
                inparam.set(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE,
                        String.valueOf(lst_PCTCustomerProgress4.getValue(LST_CUSTOMER)));
            }
            else if (lst_PCTCustomerProgress3.getVisible())
            {
                // リストセル下段のデータを基準とします。

                inparam.set(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE,
                        String.valueOf(lst_PCTCustomerProgress3.getValue(LST_CUSTOMER)));
            }
            else if (lst_PCTCustomerProgress2.getVisible())
            {
                // リストセル下段のデータを基準とします。
                inparam.set(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE,
                        String.valueOf(lst_PCTCustomerProgress2.getValue(LST_CUSTOMER)));
            }
            else
            {
                // 下段のデータが存在しない場合、上段を基準とします。
                inparam.set(PCTCustomerProgressGraphSCHParams.CUSTOMER_CODE,
                        String.valueOf(lst_PCTCustomerProgress1.getValue(LST_CUSTOMER)));
            }

            txt_ConsignorCode.setValue(viewState.getString(ViewStateKeys.VS_CONSIGNOR_CODE));
            txt_ConsignorName.setValue(viewState.getString(ViewStateKeys.VS_CONSIGNOR_NAME));
            txt_RegularCustomerCode.setValue(viewState.getString(ViewStateKeys.VS_REGULAR_CUSTOMER_CODE));
            txt_RegularCustomerName.setValue(viewState.getString(ViewStateKeys.VS_REGULAR_CUSTOMER_NAME));
            _pdm_pul_AreaNo.setSelectedValue(viewState.getString(ViewStateKeys.VS_AREA_NO));
            txt_BatchNo.setValue(viewState.getString(ViewStateKeys.VS_BATCH_NO));
            txt_BatchSeqNo.setValue(viewState.getString(ViewStateKeys.VS_BATCH_SEQ_NO));
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // 存在チェック
            if (outparams.size() == 0)
            {
                // 表示データを削除
                clearView();

                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);

                // 6003011=対象データはありませんでした。
                message.setMsgResourceKey(WmsMessageFormatter.format(6003011));
                return;
            }

            // リストセット
            setViewData(outparams);

            // ボタン制御
            String button = outparams.get(0).getString(PCTCustomerProgressGraphSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {
                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {
                // 前頁ボタン(有効化)
                btn_PrevPage.setEnabled(true);
                // 次頁ボタン(無効化)
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {
                // 前頁ボタン(無効化)
                btn_PrevPage.setEnabled(false);
                // 次頁ボタン(有効化)
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {
                // 前頁ボタン(有効化)
                btn_PrevPage.setEnabled(true);
                // 次頁ボタン(有効化)
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
