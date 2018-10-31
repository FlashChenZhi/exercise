// $Id: PCTAllProgressBusiness.java 4737 2009-07-24 04:18:22Z shibamoto $
package jp.co.daifuku.pcart.retrieval.display.pctallprogress;

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
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.base.controller.PCTConsignorController;
import jp.co.daifuku.pcart.retrieval.schedule.PCTAllProgressSCH;
import jp.co.daifuku.pcart.retrieval.schedule.PCTAllProgressSCHParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;

/**
 * 作業進捗の画面処理を行います。
 *
 * Designer : H.Okayama<BR>
 * Maker : H.Okayama<BR>
 * @version $Revision: 4737 $, $Date:: 2009-07-24 13:18:22 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class PCTAllProgressBusiness
        extends PCTAllProgress
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_PCTAllProgress1(LST_PLAN_DATE_1) */
    private static final ListCellKey KEY_LST_PLAN_DATE_1 =
            new ListCellKey("LST_PLAN_DATE_1", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_PCTAllProgress1(LST_BATCH_SEQ_NO_1) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO_1 =
            new ListCellKey("LST_BATCH_SEQ_NO_1", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress1(LST_BATCH_NO_1) */
    private static final ListCellKey KEY_LST_BATCH_NO_1 =
            new ListCellKey("LST_BATCH_NO_1", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress1(LST_ORDER_COUNT_1) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_1 =
            new ListCellKey("LST_ORDER_COUNT_1", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress1(LST_BOX_COUNT_1) */
    private static final ListCellKey KEY_LST_BOX_COUNT_1 =
            new ListCellKey("LST_BOX_COUNT_1", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress1(LST_LINE_COUNT_1) */
    private static final ListCellKey KEY_LST_LINE_COUNT_1 =
            new ListCellKey("LST_LINE_COUNT_1", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress1(LST_LOT_COUNT_1) */
    private static final ListCellKey KEY_LST_LOT_COUNT_1 =
            new ListCellKey("LST_LOT_COUNT_1", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress1(LST_END_PLAN_TIME_1) */
    private static final ListCellKey KEY_LST_END_PLAN_TIME_1 =
            new ListCellKey("LST_END_PLAN_TIME_1", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress1(LST_CART_COUNT_1) */
    private static final ListCellKey KEY_LST_CART_COUNT_1 =
            new ListCellKey("LST_CART_COUNT_1", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress2(LST_PLAN_DATE_2) */
    private static final ListCellKey KEY_LST_PLAN_DATE_2 =
            new ListCellKey("LST_PLAN_DATE_2", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_PCTAllProgress2(LST_BATCH_SEQ_NO_2) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO_2 =
            new ListCellKey("LST_BATCH_SEQ_NO_2", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress2(LST_BATCH_NO_2) */
    private static final ListCellKey KEY_LST_BATCH_NO_2 =
            new ListCellKey("LST_BATCH_NO_2", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress2(LST_ORDER_COUNT_2) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_2 =
            new ListCellKey("LST_ORDER_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress2(LST_BOX_COUNT_2) */
    private static final ListCellKey KEY_LST_BOX_COUNT_2 =
            new ListCellKey("LST_BOX_COUNT_2", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress2(LST_LINE_COUNT_2) */
    private static final ListCellKey KEY_LST_LINE_COUNT_2 =
            new ListCellKey("LST_LINE_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress2(LST_LOT_COUNT_2) */
    private static final ListCellKey KEY_LST_LOT_COUNT_2 =
            new ListCellKey("LST_LOT_COUNT_2", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress2(LST_END_PLAN_TIME_2) */
    private static final ListCellKey KEY_LST_END_PLAN_TIME_2 =
            new ListCellKey("LST_END_PLAN_TIME_2", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress2(LST_CART_COUNT_2) */
    private static final ListCellKey KEY_LST_CART_COUNT_2 =
            new ListCellKey("LST_CART_COUNT_2", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress3(LST_PLAN_DATE_3) */
    private static final ListCellKey KEY_LST_PLAN_DATE_3 =
            new ListCellKey("LST_PLAN_DATE_3", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_PCTAllProgress3(LST_BATCH_SEQ_NO_3) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO_3 =
            new ListCellKey("LST_BATCH_SEQ_NO_3", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress3(LST_BATCH_NO_3) */
    private static final ListCellKey KEY_LST_BATCH_NO_3 =
            new ListCellKey("LST_BATCH_NO_3", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress3(LST_ORDER_COUNT_3) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_3 =
            new ListCellKey("LST_ORDER_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress3(LST_BOX_COUNT_3) */
    private static final ListCellKey KEY_LST_BOX_COUNT_3 =
            new ListCellKey("LST_BOX_COUNT_3", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress3(LST_LINE_COUNT_3) */
    private static final ListCellKey KEY_LST_LINE_COUNT_3 =
            new ListCellKey("LST_LINE_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress3(LST_LOT_COUNT_3) */
    private static final ListCellKey KEY_LST_LOT_COUNT_3 =
            new ListCellKey("LST_LOT_COUNT_3", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress3(LST_END_PLAN_TIME_3) */
    private static final ListCellKey KEY_LST_END_PLAN_TIME_3 =
            new ListCellKey("LST_END_PLAN_TIME_3", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress3(LST_CART_COUNT_3) */
    private static final ListCellKey KEY_LST_CART_COUNT_3 =
            new ListCellKey("LST_CART_COUNT_3", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress4(LST_PLAN_DATE_4) */
    private static final ListCellKey KEY_LST_PLAN_DATE_4 =
            new ListCellKey("LST_PLAN_DATE_4", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_PCTAllProgress4(LST_BATCH_SEQ_NO_4) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO_4 =
            new ListCellKey("LST_BATCH_SEQ_NO_4", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress4(LST_BATCH_NO_4) */
    private static final ListCellKey KEY_LST_BATCH_NO_4 =
            new ListCellKey("LST_BATCH_NO_4", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress4(LST_ORDER_COUNT_4) */
    private static final ListCellKey KEY_LST_ORDER_COUNT_4 =
            new ListCellKey("LST_ORDER_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress4(LST_BOX_COUNT_4) */
    private static final ListCellKey KEY_LST_BOX_COUNT_4 =
            new ListCellKey("LST_BOX_COUNT_4", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress4(LST_LINE_COUNT_4) */
    private static final ListCellKey KEY_LST_LINE_COUNT_4 =
            new ListCellKey("LST_LINE_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress4(LST_LOT_COUNT_4) */
    private static final ListCellKey KEY_LST_LOT_COUNT_4 =
            new ListCellKey("LST_LOT_COUNT_4", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress4(LST_END_PLAN_TIME_4) */
    private static final ListCellKey KEY_LST_END_PLAN_TIME_4 =
            new ListCellKey("LST_END_PLAN_TIME_4", new StringCellColumn(), true, false);

    /** lst_PCTAllProgress4(LST_CART_COUNT_4) */
    private static final ListCellKey KEY_LST_CART_COUNT_4 =
            new ListCellKey("LST_CART_COUNT_4", new NumberCellColumn(0), true, false);

    /** lst_PCTAllProgress1 kyes */
    private static final ListCellKey[] LST_PCTALLPROGRESS1_KEYS = {
            KEY_LST_PLAN_DATE_1,
            KEY_LST_BATCH_SEQ_NO_1,
            KEY_LST_BATCH_NO_1,
            KEY_LST_ORDER_COUNT_1,
            KEY_LST_BOX_COUNT_1,
            KEY_LST_LINE_COUNT_1,
            KEY_LST_LOT_COUNT_1,
            KEY_LST_END_PLAN_TIME_1,
            KEY_LST_CART_COUNT_1,
    };

    /** lst_PCTAllProgress2 kyes */
    private static final ListCellKey[] LST_PCTALLPROGRESS2_KEYS = {
            KEY_LST_PLAN_DATE_2,
            KEY_LST_BATCH_SEQ_NO_2,
            KEY_LST_BATCH_NO_2,
            KEY_LST_ORDER_COUNT_2,
            KEY_LST_BOX_COUNT_2,
            KEY_LST_LINE_COUNT_2,
            KEY_LST_LOT_COUNT_2,
            KEY_LST_END_PLAN_TIME_2,
            KEY_LST_CART_COUNT_2,
    };

    /** lst_PCTAllProgress3 kyes */
    private static final ListCellKey[] LST_PCTALLPROGRESS3_KEYS = {
            KEY_LST_PLAN_DATE_3,
            KEY_LST_BATCH_SEQ_NO_3,
            KEY_LST_BATCH_NO_3,
            KEY_LST_ORDER_COUNT_3,
            KEY_LST_BOX_COUNT_3,
            KEY_LST_LINE_COUNT_3,
            KEY_LST_LOT_COUNT_3,
            KEY_LST_END_PLAN_TIME_3,
            KEY_LST_CART_COUNT_3,
    };

    /** lst_PCTAllProgress4 kyes */
    private static final ListCellKey[] LST_PCTALLPROGRESS4_KEYS = {
            KEY_LST_PLAN_DATE_4,
            KEY_LST_BATCH_SEQ_NO_4,
            KEY_LST_BATCH_NO_4,
            KEY_LST_ORDER_COUNT_4,
            KEY_LST_BOX_COUNT_4,
            KEY_LST_LINE_COUNT_4,
            KEY_LST_LOT_COUNT_4,
            KEY_LST_END_PLAN_TIME_4,
            KEY_LST_CART_COUNT_4,
    };

    // DFKLOOK:ここから修正
    /**
     * 予定日のリストセル列番号
     */
    private static final int LST_PLANDAY = 1;

    /**
     * バッチSeqNo.のリストセル列番号
     */
    private static final int LST_BATCHSEQNO = 2;

    /**
     * 進捗リストセルの最大表示件数
     */
    private static final int MAX_DISPLAY_PROGRESS = 4;

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

    /** RadioButtonGroupModel Group */
    private RadioButtonGroup _grp_Group;

    /** RadioButtonGroupModel EndPlanTime */
    private RadioButtonGroup _grp_EndPlanTime;

    /** ListCellModel lst_PCTAllProgress1 */
    private ListCellModel _lcm_lst_PCTAllProgress1;

    /** ListCellModel lst_PCTAllProgress2 */
    private ListCellModel _lcm_lst_PCTAllProgress2;

    /** ListCellModel lst_PCTAllProgress3 */
    private ListCellModel _lcm_lst_PCTAllProgress3;

    /** ListCellModel lst_PCTAllProgress4 */
    private ListCellModel _lcm_lst_PCTAllProgress4;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTAllProgressBusiness()
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

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();

            // 表示項目と保持項目を比較
            if (txt_ConsignorCode.getText().equals(viewState.getString(ViewStateKeys.VS_CONSIGNOR_CODE))
                    && _pdm_pul_AreaNo.getSelectedValue().equals(viewState.getString(ViewStateKeys.VS_AREA_NO)))
            {
                // リストが表示されている場合
                if (lst_PCTAllProgress1.getVisible())
                {
                    inparam.set(PCTAllProgressSCHParams.PLAN_DATE, WmsFormatter.toParamDate(
                            lst_PCTAllProgress1.getValue(LST_PLANDAY), locale));
                    inparam.set(PCTAllProgressSCHParams.BATCH_SEQ_NO, lst_PCTAllProgress1.getValue(LST_BATCHSEQNO));
                }
            }
            else
            {
                // 画面項目を保持
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getText());
                viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            }
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, checkEndPlanTimeStandardFlag());
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // データが存在しない場合
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

                // 処理抜け
                return;
            }

            // 進捗グラフを表示します
            setViewData(outparams, inparam);

            // ボタン制御
            String button = outparams.get(0).getString(PCTAllProgressSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {

                btn_PrevPage.setEnabled(true);
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

            setFocus(pul_AreaNo);
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
    public void rdo_BatchNoUnit_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_BatchNoUnit_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_PlanDateUnit_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_PlanDateUnit_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_AllPlanDate_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_AllPlanDate_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_LotStandard_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_LotStandard_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_OrderStandard_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_OrderStandard_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_LineStandard_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_LineStandard_Click_Process();
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
     * 
     * @throws Exception
     *             全ての例外を報告します。
     */
    protected void clearView()
            throws Exception
    {
        lst_PCTAllProgress1.clearRow();
        lst_PCTAllProgress2.clearRow();
        lst_PCTAllProgress3.clearRow();
        lst_PCTAllProgress4.clearRow();
        hbc_PCTAllTask1.setValue(0);
        hbc_PCTAllTask2.setValue(0);
        hbc_PCTAllTask3.setValue(0);
        hbc_PCTAllTask4.setValue(0);
        hbc_PCTAllTask1.createChart(httpRequest);
        hbc_PCTAllTask2.createChart(httpRequest);
        hbc_PCTAllTask3.createChart(httpRequest);
        hbc_PCTAllTask4.createChart(httpRequest);

        // 非表示
        lst_PCTAllProgress1.setVisible(false);
        hbc_PCTAllTask1.setVisible(false);
        txt_ProgressRate1.setVisible(false);
        lbl_Percent1.setVisible(false);

        lst_PCTAllProgress2.setVisible(false);
        hbc_PCTAllTask2.setVisible(false);
        txt_ProgressRate2.setVisible(false);
        lbl_Percent2.setVisible(false);

        lst_PCTAllProgress3.setVisible(false);
        hbc_PCTAllTask3.setVisible(false);
        txt_ProgressRate3.setVisible(false);
        lbl_Percent3.setVisible(false);

        lst_PCTAllProgress4.setVisible(false);
        hbc_PCTAllTask4.setVisible(false);
        txt_ProgressRate4.setVisible(false);
        lbl_Percent4.setVisible(false);

        lst_PCTAllProgress1.setReverseView(true);
        lst_PCTAllProgress2.setReverseView(true);
        lst_PCTAllProgress3.setReverseView(true);
        lst_PCTAllProgress4.setReverseView(true);

        // 表示有効
        btn_Display.setEnabled(true);
        // 前頁無効
        btn_PrevPage.setEnabled(false);
        // 次頁無効
        btn_NextPage.setEnabled(false);
    }

    /**
     * パラメータを取得し、画面に表示します。<BR>
     * 
     * @param viewParam グラフ、リストエリアに表示するパラメータ
     * @throws Exception 全ての例外を報告します。
     */
    protected void setViewData(List<Params> outParams, PCTAllProgressSCHParams inParam)
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

        // ロケールを取得
        Locale locale = getHttpRequest().getLocale();

        for (Params param : outParams)
        {
            // 進捗率を取得
            bdRate = param.getBigDecimal(PCTAllProgressSCHParams.PROGRESS_RATE);
            // BigDecimal → doubleへ変換
            rate = bdRate.divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP).doubleValue();

            // 1段目
            if (i == NUMBER_ZERO)
            {
                // リスト行
                // 行生成
                line = _lcm_lst_PCTAllProgress1.getNewLine();
                // リスト可視化
                _lcm_lst_PCTAllProgress1.getListCell().setVisible(true);
                // 進捗バー可視化
                hbc_PCTAllTask1.setVisible(true);
                // 進捗率可視化
                txt_ProgressRate1.setVisible(true);
                // %ラベル可視化
                lbl_Percent1.setVisible(true);

                // 設定(リスト)
                // 予定日
                if (!StringUtil.isBlank(param.getString(PCTAllProgressSCHParams.PLAN_DATE)))
                {
                    line.setValue(KEY_LST_PLAN_DATE_1, WmsFormatter.toDispDate(
                            param.getString(PCTAllProgressSCHParams.PLAN_DATE), locale));
                }
                // バッチSeqNo.
                if (!StringUtil.isBlank(param.getString(PCTAllProgressSCHParams.BATCH_SEQ_NO)))
                {
                    line.setValue(KEY_LST_BATCH_SEQ_NO_1, param.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                }
                // バッチNo.
                if (!StringUtil.isBlank(param.getString(PCTAllProgressSCHParams.BATCH_NO)))
                {
                    line.setValue(KEY_LST_BATCH_NO_1, param.get(PCTAllProgressSCHParams.BATCH_NO));
                }
                line.setValue(KEY_LST_ORDER_COUNT_1, param.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_1, param.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_1, param.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_1, param.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_CART_COUNT_1, param.get(PCTAllProgressSCHParams.CART_COUNT));

                // 終了予測時間
                if (StringUtil.isBlank(inParam.getString(PCTAllProgressSCHParams.AREA_NO)))
                {
                    line.setValue(KEY_LST_END_PLAN_TIME_1, "");
                }
                else
                {
                    line.setValue(KEY_LST_END_PLAN_TIME_1, param.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                }

                // 設定(進捗バー)
                hbc_PCTAllTask1.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_PCTAllTask1.setValue(rate);
                hbc_PCTAllTask1.createChart(httpRequest);

                // 進捗率
                txt_ProgressRate1.setText(WmsFormatter.toProductionRate(rate));
                txt_ProgressRate1.setReadOnly(true);

                // 行追加                
                _lcm_lst_PCTAllProgress1.add(line);
            }
            else if (i == NUMBER_ONE)
            {
                // リスト行
                // 行生成
                line = _lcm_lst_PCTAllProgress2.getNewLine();
                // リスト可視化
                _lcm_lst_PCTAllProgress2.getListCell().setVisible(true);
                // 進捗バー可視化
                hbc_PCTAllTask2.setVisible(true);
                // 進捗率可視化
                txt_ProgressRate2.setVisible(true);
                // %ラベル可視化
                lbl_Percent2.setVisible(true);

                // 設定(リスト)
                // 予定日
                if (!StringUtil.isBlank(param.getString(PCTAllProgressSCHParams.PLAN_DATE)))
                {
                    line.setValue(KEY_LST_PLAN_DATE_2, WmsFormatter.toDispDate(
                            param.getString(PCTAllProgressSCHParams.PLAN_DATE), locale));
                }
                // バッチSeqNo.
                if (!StringUtil.isBlank(param.getString(PCTAllProgressSCHParams.BATCH_SEQ_NO)))
                {
                    line.setValue(KEY_LST_BATCH_SEQ_NO_2, param.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                }
                // バッチNo.
                if (!StringUtil.isBlank(param.getString(PCTAllProgressSCHParams.BATCH_NO)))
                {
                    line.setValue(KEY_LST_BATCH_NO_2, param.get(PCTAllProgressSCHParams.BATCH_NO));
                }
                line.setValue(KEY_LST_ORDER_COUNT_2, param.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_2, param.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_2, param.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_2, param.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_CART_COUNT_2, param.get(PCTAllProgressSCHParams.CART_COUNT));

                // 終了予測時間
                if (StringUtil.isBlank(inParam.getString(PCTAllProgressSCHParams.AREA_NO)))
                {
                    line.setValue(KEY_LST_END_PLAN_TIME_2, "");
                }
                else
                {
                    line.setValue(KEY_LST_END_PLAN_TIME_2, param.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                }

                // 設定(進捗バー)
                hbc_PCTAllTask2.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_PCTAllTask2.setValue(rate);
                hbc_PCTAllTask2.createChart(httpRequest);

                // 進捗率
                txt_ProgressRate2.setText(WmsFormatter.toProductionRate(rate));
                txt_ProgressRate2.setReadOnly(true);

                // 行追加                
                _lcm_lst_PCTAllProgress2.add(line);
            }
            else if (i == NUMBER_TWO)
            {
                // リスト行
                // 行生成
                line = _lcm_lst_PCTAllProgress3.getNewLine();
                // リスト可視化
                _lcm_lst_PCTAllProgress3.getListCell().setVisible(true);
                // 進捗バー可視化
                hbc_PCTAllTask3.setVisible(true);
                // 進捗率可視化
                txt_ProgressRate3.setVisible(true);
                // %ラベル可視化
                lbl_Percent3.setVisible(true);

                // 設定(リスト)
                // 予定日
                if (!StringUtil.isBlank(param.getString(PCTAllProgressSCHParams.PLAN_DATE)))
                {
                    line.setValue(KEY_LST_PLAN_DATE_3, WmsFormatter.toDispDate(
                            param.getString(PCTAllProgressSCHParams.PLAN_DATE), locale));
                }
                // バッチSeqNo.
                if (!StringUtil.isBlank(param.getString(PCTAllProgressSCHParams.BATCH_SEQ_NO)))
                {
                    line.setValue(KEY_LST_BATCH_SEQ_NO_3, param.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                }
                // バッチNo.
                if (!StringUtil.isBlank(param.getString(PCTAllProgressSCHParams.BATCH_NO)))
                {
                    line.setValue(KEY_LST_BATCH_NO_3, param.get(PCTAllProgressSCHParams.BATCH_NO));
                }
                line.setValue(KEY_LST_ORDER_COUNT_3, param.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_3, param.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_3, param.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_3, param.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_CART_COUNT_3, param.get(PCTAllProgressSCHParams.CART_COUNT));

                // 終了予測時間
                if (StringUtil.isBlank(inParam.getString(PCTAllProgressSCHParams.AREA_NO)))
                {
                    line.setValue(KEY_LST_END_PLAN_TIME_3, "");
                }
                else
                {
                    line.setValue(KEY_LST_END_PLAN_TIME_3, param.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                }

                // 設定(進捗バー)
                hbc_PCTAllTask3.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_PCTAllTask3.setValue(rate);
                hbc_PCTAllTask3.createChart(httpRequest);

                // 進捗率
                txt_ProgressRate3.setText(WmsFormatter.toProductionRate(rate));
                txt_ProgressRate3.setReadOnly(true);

                // 行追加                
                _lcm_lst_PCTAllProgress3.add(line);
            }
            else if (i == NUMBER_THREE)
            {
                // リスト行
                // 行生成
                line = _lcm_lst_PCTAllProgress4.getNewLine();
                // リスト可視化
                _lcm_lst_PCTAllProgress4.getListCell().setVisible(true);
                // 進捗バー可視化
                hbc_PCTAllTask4.setVisible(true);
                // 進捗率可視化
                txt_ProgressRate4.setVisible(true);
                // %ラベル可視化
                lbl_Percent4.setVisible(true);

                // 設定(リスト)
                // 予定日
                if (!StringUtil.isBlank(param.getString(PCTAllProgressSCHParams.PLAN_DATE)))
                {
                    line.setValue(KEY_LST_PLAN_DATE_4, WmsFormatter.toDispDate(
                            param.getString(PCTAllProgressSCHParams.PLAN_DATE), locale));
                }
                // バッチSeqNo.
                if (!StringUtil.isBlank(param.getString(PCTAllProgressSCHParams.BATCH_SEQ_NO)))
                {
                    line.setValue(KEY_LST_BATCH_SEQ_NO_4, param.get(PCTAllProgressSCHParams.BATCH_SEQ_NO));
                }
                // バッチNo.
                if (!StringUtil.isBlank(param.getString(PCTAllProgressSCHParams.BATCH_NO)))
                {
                    line.setValue(KEY_LST_BATCH_NO_4, param.get(PCTAllProgressSCHParams.BATCH_NO));
                }
                line.setValue(KEY_LST_ORDER_COUNT_4, param.get(PCTAllProgressSCHParams.ORDER_COUNT));
                line.setValue(KEY_LST_BOX_COUNT_4, param.get(PCTAllProgressSCHParams.BOX_COUNT));
                line.setValue(KEY_LST_LINE_COUNT_4, param.get(PCTAllProgressSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_LOT_COUNT_4, param.get(PCTAllProgressSCHParams.LOT_COUNT));
                line.setValue(KEY_LST_CART_COUNT_4, param.get(PCTAllProgressSCHParams.CART_COUNT));

                // 終了予測時間
                if (StringUtil.isBlank(inParam.getString(PCTAllProgressSCHParams.AREA_NO)))
                {
                    line.setValue(KEY_LST_END_PLAN_TIME_4, "");
                }
                else
                {
                    line.setValue(KEY_LST_END_PLAN_TIME_4, param.get(PCTAllProgressSCHParams.END_PLAN_TIME));
                }

                // 設定(進捗バー)
                hbc_PCTAllTask4.setGraphPaint(WmsParam.PROGRESS_COLOR_TYPE);
                hbc_PCTAllTask4.setValue(rate);
                hbc_PCTAllTask4.createChart(httpRequest);

                // 進捗率
                txt_ProgressRate4.setText(WmsFormatter.toProductionRate(rate));
                txt_ProgressRate4.setReadOnly(true);

                // 行追加                
                _lcm_lst_PCTAllProgress4.add(line);
            }
            // カウント変数(インクリメント)
            i++;
        }

        if (outParams.size() < MAX_DISPLAY_PROGRESS)
        {
            // 非表示
            for (i = outParams.size(); i < MAX_DISPLAY_PROGRESS; i++)
            {
                if (i == NUMBER_ZERO)
                {
                    lst_PCTAllProgress1.setVisible(false);
                    hbc_PCTAllTask1.setVisible(false);
                    txt_ProgressRate1.setVisible(false);
                    lbl_Percent1.setVisible(false);
                }
                if (i == NUMBER_ONE)
                {
                    lst_PCTAllProgress2.setVisible(false);
                    hbc_PCTAllTask2.setVisible(false);
                    txt_ProgressRate2.setVisible(false);
                    lbl_Percent2.setVisible(false);
                }
                if (i == NUMBER_TWO)
                {
                    lst_PCTAllProgress3.setVisible(false);
                    hbc_PCTAllTask3.setVisible(false);
                    txt_ProgressRate3.setVisible(false);
                    lbl_Percent3.setVisible(false);
                }
                if (i == NUMBER_THREE)
                {
                    lst_PCTAllProgress4.setVisible(false);
                    hbc_PCTAllTask4.setVisible(false);
                    txt_ProgressRate4.setVisible(false);
                    lbl_Percent4.setVisible(false);
                }
            }
        }
    }

    /**
     * 集約チェックボックスの値により、データを返します。<BR>
     * (通常用)
     * 
     * @return 集約条件フラグ
     */
    protected String checkCollectFlag()
    {
        if (rdo_AllPlanDate.getChecked())
        {
            return PCTRetrievalInParameter.COLLECT_ALL_PLANDATE;
        }
        else if (rdo_PlanDateUnit.getChecked())
        {
            return PCTRetrievalInParameter.COLLECT_PLANDATE_UNIT;
        }
        else if (rdo_BatchNoUnit.getChecked())
        {
            return PCTRetrievalInParameter.COLLECT_BATCHNO_UNIT;
        }
        else
        {
            return null;
        }
    }

    /**
     * 終了予測時間チェックボックスの値により、データを返します。<BR>
     * (通常用)
     * 
     * @return 終了予測時間基準フラグ
     */
    protected String checkEndPlanTimeStandardFlag()
    {
        if (rdo_LotStandard.getChecked())
        {
            return PCTRetrievalInParameter.ENDPLANTIME_LOT_STANDARD;
        }
        else if (rdo_OrderStandard.getChecked())
        {
            return PCTRetrievalInParameter.ENDPLANTIME_ORDER_STANDARD;
        }
        else if (rdo_LineStandard.getChecked())
        {
            return PCTRetrievalInParameter.ENDPLANTIME_LINE_STANDARD;
        }
        else
        {
            return null;
        }
    }

    /**
     * 集約チェックボックスの値により、データを返します。<BR>
     * (ViewState用)
     * 
     * @return 集約条件フラグ
     */
    protected String checkVsCollectFlag()
    {
        if ("1".equals(viewState.getString(ViewStateKeys.VS_GROUP)))
        {
            return PCTRetrievalInParameter.COLLECT_ALL_PLANDATE;
        }
        else if ("2".equals(viewState.getString(ViewStateKeys.VS_GROUP)))
        {
            return PCTRetrievalInParameter.COLLECT_PLANDATE_UNIT;
        }
        else if ("3".equals(viewState.getString(ViewStateKeys.VS_GROUP)))
        {
            return PCTRetrievalInParameter.COLLECT_BATCHNO_UNIT;
        }
        else
        {
            return null;
        }
    }

    /**
     * 終了予測時間チェックボックスの値により、データを返します。<BR>
     * (ViewState用)
     * 
     * @return 終了予測時間基準フラグ
     */
    protected String checkVsEndPlanTimeStandardFlag()
    {
        if ("1".equals(viewState.getString(ViewStateKeys.VS_END_PLAN_TIME)))
        {
            return PCTRetrievalInParameter.ENDPLANTIME_LOT_STANDARD;
        }
        else if ("2".equals(viewState.getString(ViewStateKeys.VS_END_PLAN_TIME)))
        {
            return PCTRetrievalInParameter.ENDPLANTIME_ORDER_STANDARD;
        }
        else if ("3".equals(viewState.getString(ViewStateKeys.VS_END_PLAN_TIME)))
        {
            return PCTRetrievalInParameter.ENDPLANTIME_LINE_STANDARD;
        }
        else
        {
            return null;
        }
    }

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

        // initialize Group.
        _grp_Group = new RadioButtonGroup(new RadioButton[] {
                rdo_BatchNoUnit,
                rdo_PlanDateUnit,
                rdo_AllPlanDate
        }, locale);

        // initialize EndPlanTime.
        _grp_EndPlanTime = new RadioButtonGroup(new RadioButton[] {
                rdo_LotStandard,
                rdo_OrderStandard,
                rdo_LineStandard
        }, locale);

        // initialize lst_PCTAllProgress1.
        _lcm_lst_PCTAllProgress1 = new ListCellModel(lst_PCTAllProgress1, LST_PCTALLPROGRESS1_KEYS, locale);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_PLAN_DATE_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_BATCH_SEQ_NO_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_BATCH_NO_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_ORDER_COUNT_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_BOX_COUNT_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_LINE_COUNT_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_LOT_COUNT_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_END_PLAN_TIME_1, false);
        _lcm_lst_PCTAllProgress1.setToolTipVisible(KEY_LST_CART_COUNT_1, true);

        // initialize lst_PCTAllProgress2.
        _lcm_lst_PCTAllProgress2 = new ListCellModel(lst_PCTAllProgress2, LST_PCTALLPROGRESS2_KEYS, locale);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_PLAN_DATE_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_BATCH_SEQ_NO_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_BATCH_NO_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_ORDER_COUNT_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_BOX_COUNT_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_LINE_COUNT_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_LOT_COUNT_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_END_PLAN_TIME_2, false);
        _lcm_lst_PCTAllProgress2.setToolTipVisible(KEY_LST_CART_COUNT_2, false);

        // initialize lst_PCTAllProgress3.
        _lcm_lst_PCTAllProgress3 = new ListCellModel(lst_PCTAllProgress3, LST_PCTALLPROGRESS3_KEYS, locale);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_PLAN_DATE_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_BATCH_SEQ_NO_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_BATCH_NO_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_ORDER_COUNT_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_BOX_COUNT_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_LINE_COUNT_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_LOT_COUNT_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_END_PLAN_TIME_3, false);
        _lcm_lst_PCTAllProgress3.setToolTipVisible(KEY_LST_CART_COUNT_3, false);

        // initialize lst_PCTAllProgress4.
        _lcm_lst_PCTAllProgress4 = new ListCellModel(lst_PCTAllProgress4, LST_PCTALLPROGRESS4_KEYS, locale);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_PLAN_DATE_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_BATCH_SEQ_NO_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_BATCH_NO_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_ORDER_COUNT_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_BOX_COUNT_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_LINE_COUNT_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_LOT_COUNT_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_END_PLAN_TIME_4, false);
        _lcm_lst_PCTAllProgress4.setToolTipVisible(KEY_LST_CART_COUNT_4, false);

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
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();

            // SCH call.
            // DFKLOOK:ここから修正
            // 表示データを削除
            clearView();

            // query → initFind
            Params outparam = sch.initFind(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            if (outparam != null)
            {
                txt_ConsignorCode.setValue(outparam.get(PCTAllProgressSCHParams.CONSIGNOR_CODE));
                txt_ConsignorName.setValue(outparam.get(PCTAllProgressSCHParams.CONSIGNOR_NAME));
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE,
                        outparam.get(PCTAllProgressSCHParams.CONSIGNOR_CODE));
                viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME,
                        outparam.get(PCTAllProgressSCHParams.CONSIGNOR_NAME));

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

            // プルダウン選択
            pul_AreaNo.setSelectedIndex(0);
            // DFKLOOK:ここまで修正

            // clear.
            rdo_BatchNoUnit.setChecked(true);
            rdo_LotStandard.setChecked(true);

            // DFKLOOK:ここから修正
            // 荷主コードがあればリストを表示する
            if (!StringUtil.isBlank(txt_ConsignorCode.getText()))
            {
                btn_Display_Click(null);
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
            // 表示ボタン(無効化)
            btn_Display.setEnabled(false);
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
        viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
        viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
        viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
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
        txt_ConsignorCode.validate(this, true);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void rdo_BatchNoUnit_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            // DFKLOOK:ここから修正
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, PCTRetrievalInParameter.COLLECT_BATCHNO_UNIT);
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, checkEndPlanTimeStandardFlag());
            // DFKLOOK:ここまで修正   
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // DFKLOOK:ここから修正
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getText());
            viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // データが存在しない場合
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

                // 処理抜け
                return;
            }

            // 進捗グラフを表示します
            setViewData(outparams, inparam);

            // ボタン制御
            String button = outparams.get(0).getString(PCTAllProgressSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {

                btn_PrevPage.setEnabled(true);
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
    private void rdo_PlanDateUnit_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            // DFKLOOK:ここから修正
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, PCTRetrievalInParameter.COLLECT_PLANDATE_UNIT);
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, checkEndPlanTimeStandardFlag());
            // DFKLOOK:ここまで修正
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // DFKLOOK:ここから修正
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getText());
            viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // データが存在しない場合
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

                // 処理抜け
                return;
            }

            // 進捗グラフを表示します
            setViewData(outparams, inparam);

            // ボタン制御
            String button = outparams.get(0).getString(PCTAllProgressSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {

                btn_PrevPage.setEnabled(true);
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
    private void rdo_AllPlanDate_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            // DFKLOOK:ここから修正
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, PCTRetrievalInParameter.COLLECT_ALL_PLANDATE);
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, checkEndPlanTimeStandardFlag());
            // DFKLOOK:ここから修正
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // DFKLOOK:ここから修正
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getText());
            viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // データが存在しない場合
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

                // 処理抜け
                return;
            }

            // 進捗グラフを表示します
            setViewData(outparams, inparam);

            // ボタン制御
            String button = outparams.get(0).getString(PCTAllProgressSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {

                btn_PrevPage.setEnabled(true);
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
    private void rdo_LotStandard_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            // DFKLOOK:ここから修正
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, PCTRetrievalInParameter.ENDPLANTIME_LOT_STANDARD);
            // DFKLOOK:ここまで修正
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // DFKLOOK:ここから修正
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getText());
            viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // データが存在しない場合
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

                // 処理抜け
                return;
            }

            // 進捗グラフを表示します
            setViewData(outparams, inparam);

            // ボタン制御
            String button = outparams.get(0).getString(PCTAllProgressSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {

                btn_PrevPage.setEnabled(true);
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
    private void rdo_OrderStandard_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            // DFKLOOK:ここから修正
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, PCTRetrievalInParameter.ENDPLANTIME_ORDER_STANDARD);
            // DFKLOOK:ここまで修正
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // DFKLOOK:ここから修正
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getText());
            viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // データが存在しない場合
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

                // 処理抜け
                return;
            }

            // 進捗グラフを表示します
            setViewData(outparams, inparam);

            // ボタン制御
            String button = outparams.get(0).getString(PCTAllProgressSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {

                btn_PrevPage.setEnabled(true);
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
    private void rdo_LineStandard_Click_Process()
            throws Exception
    {
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            // DFKLOOK:ここから修正
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, PCTRetrievalInParameter.ENDPLANTIME_LINE_STANDARD);
            // DFKLOOK:ここまで修正
            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // DFKLOOK:ここから修正
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getText());
            viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // データが存在しない場合
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

                // 処理抜け
                return;
            }

            // 進捗グラフを表示します
            setViewData(outparams, inparam);

            // ボタン制御
            String button = outparams.get(0).getString(PCTAllProgressSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {

                btn_PrevPage.setEnabled(true);
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
        // input validation.
        txt_ConsignorCode.validate(this, true);
        pul_AreaNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, txt_ConsignorCode.getValue());
            inparam.set(PCTAllProgressSCHParams.AREA_NO, _pdm_pul_AreaNo.getSelectedValue());

            // DFKLOOK:ここから修正
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, checkCollectFlag());
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, checkEndPlanTimeStandardFlag());
            // DFKLOOK:ここまで修正

            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_VIEW);

            // DFKLOOK:ここから修正
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_CODE, txt_ConsignorCode.getText());
            viewState.setObject(ViewStateKeys.VS_CONSIGNOR_NAME, txt_ConsignorName.getText());
            viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_AreaNo.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_GROUP, _grp_Group.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_END_PLAN_TIME, _grp_EndPlanTime.getSelectedValue());
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // データが存在しない場合
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

                // 処理抜け
                return;
            }

            // 進捗グラフを表示します
            setViewData(outparams, inparam);

            // ボタン制御
            String button = outparams.get(0).getString(PCTAllProgressSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {

                btn_PrevPage.setEnabled(true);
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
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, viewState.getString(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTAllProgressSCHParams.AREA_NO, viewState.getString(ViewStateKeys.VS_AREA_NO));

            // DFKLOOK:ここから修正
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, checkVsCollectFlag());
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, checkVsEndPlanTimeStandardFlag());
            inparam.set(PCTAllProgressSCHParams.PLAN_DATE, WmsFormatter.toParamDate(
                    lst_PCTAllProgress1.getValue(LST_PLANDAY), locale));
            inparam.set(PCTAllProgressSCHParams.BATCH_SEQ_NO, lst_PCTAllProgress1.getValue(LST_BATCHSEQNO));
            // DFKLOOK:ここまで修正

            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_PREVIOUS_PAGE);

            // DFKLOOK:ここから修正
            txt_ConsignorCode.setValue(viewState.getString(ViewStateKeys.VS_CONSIGNOR_CODE));
            txt_ConsignorName.setValue(viewState.getString(ViewStateKeys.VS_CONSIGNOR_NAME));
            _pdm_pul_AreaNo.setSelectedValue(viewState.getString(ViewStateKeys.VS_AREA_NO));
            _grp_Group.setSelectedValue(viewState.getString(ViewStateKeys.VS_GROUP));
            _grp_EndPlanTime.setSelectedValue(viewState.getString(ViewStateKeys.VS_END_PLAN_TIME));
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // データが存在しない場合
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

                // 処理抜け
                return;
            }

            // 進捗グラフを表示します
            setViewData(outparams, inparam);

            // ボタン制御
            String button = outparams.get(0).getString(PCTAllProgressSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {

                btn_PrevPage.setEnabled(true);
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
        // input validation.
        txt_ConsignorCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PCTAllProgressSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new PCTAllProgressSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            PCTAllProgressSCHParams inparam = new PCTAllProgressSCHParams();
            inparam.set(PCTAllProgressSCHParams.CONSIGNOR_CODE, viewState.getString(ViewStateKeys.VS_CONSIGNOR_CODE));
            inparam.set(PCTAllProgressSCHParams.AREA_NO, viewState.getString(ViewStateKeys.VS_AREA_NO));

            // DFKLOOK:ここから修正
            inparam.set(PCTAllProgressSCHParams.COLLECT_FLAG, checkVsCollectFlag());
            inparam.set(PCTAllProgressSCHParams.END_PLAN_TIME_FLAG, checkVsEndPlanTimeStandardFlag());

            if (lst_PCTAllProgress4.getVisible())
            {
                // リストセル下段のデータを基準とします。
                inparam.set(PCTAllProgressSCHParams.PLAN_DATE, WmsFormatter.toParamDate(
                        lst_PCTAllProgress4.getValue(LST_PLANDAY), locale));
                inparam.set(PCTAllProgressSCHParams.BATCH_SEQ_NO, lst_PCTAllProgress4.getValue(LST_BATCHSEQNO));
            }
            else if (lst_PCTAllProgress3.getVisible())
            {
                // リストセル下段のデータを基準とします。
                inparam.set(PCTAllProgressSCHParams.PLAN_DATE, WmsFormatter.toParamDate(
                        lst_PCTAllProgress3.getValue(LST_PLANDAY), locale));
                inparam.set(PCTAllProgressSCHParams.BATCH_SEQ_NO, lst_PCTAllProgress3.getValue(LST_BATCHSEQNO));
            }
            else if (lst_PCTAllProgress2.getVisible())
            {
                // リストセル下段のデータを基準とします。
                inparam.set(PCTAllProgressSCHParams.PLAN_DATE, WmsFormatter.toParamDate(
                        lst_PCTAllProgress2.getValue(LST_PLANDAY), locale));
                inparam.set(PCTAllProgressSCHParams.BATCH_SEQ_NO, lst_PCTAllProgress2.getValue(LST_BATCHSEQNO));
            }
            else
            {
                // 下段のデータが存在しない場合、上段を基準とします。
                inparam.set(PCTAllProgressSCHParams.PLAN_DATE, WmsFormatter.toParamDate(
                        lst_PCTAllProgress1.getValue(LST_PLANDAY), locale));
                inparam.set(PCTAllProgressSCHParams.BATCH_SEQ_NO, lst_PCTAllProgress1.getValue(LST_BATCHSEQNO));
            }
            // DFKLOOK:ここまで修正

            inparam.set(PCTAllProgressSCHParams.PROCESS_FLAG, InParameter.PROCESS_FLAG_NEXT_PAGE);

            // DFKLOOK:ここから修正
            txt_ConsignorCode.setValue(viewState.getString(ViewStateKeys.VS_CONSIGNOR_CODE));
            txt_ConsignorName.setValue(viewState.getString(ViewStateKeys.VS_CONSIGNOR_NAME));
            _pdm_pul_AreaNo.setSelectedValue(viewState.getString(ViewStateKeys.VS_AREA_NO));
            _grp_Group.setSelectedValue(viewState.getString(ViewStateKeys.VS_GROUP));
            _grp_EndPlanTime.setSelectedValue(viewState.getString(ViewStateKeys.VS_END_PLAN_TIME));
            // DFKLOOK:ここまで修正

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK:ここから修正
            // データが存在しない場合
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

                // 処理抜け
                return;
            }

            // 進捗グラフを表示します
            setViewData(outparams, inparam);

            // ボタン制御
            String button = outparams.get(0).getString(PCTAllProgressSCHParams.BUTTON_FLAG);
            if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_NEXT_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(true);
                btn_NextPage.setEnabled(false);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF.equals(button))
            {

                btn_PrevPage.setEnabled(false);
                btn_NextPage.setEnabled(true);
            }
            else if (PCTRetrievalInParameter.BUTTON_CONTROL_FLAG_ALL_ON.equals(button))
            {

                btn_PrevPage.setEnabled(true);
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
