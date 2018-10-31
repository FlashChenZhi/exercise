// $Id: AsRetrievalOrderCancelBusiness.java 7851 2010-04-21 08:49:56Z shibamoto $
package jp.co.daifuku.wms.asrs.display.ordercancel;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.schedule.AsRetrievalOrderCancelSCH;
import jp.co.daifuku.wms.asrs.schedule.AsRetrievalOrderCancelSCHParams;
import jp.co.daifuku.wms.asrs.schedule.AsRetrievalOrderStartSCHParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * AS/RS 出庫キャンセルの画面処理を行います。
 *
 * @version $Revision: 7851 $, $Date: 2010-04-21 17:49:56 +0900 (水, 21 4 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsRetrievalOrderCancelBusiness
        extends AsRetrievalOrderCancel
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK:ここから
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    // DFKLOOK:ここまで

    /** lst_ASRSRetrievalCancel(HIDDEN_LINE_COUNT) */
    private static final ListCellKey KEY_HIDDEN_LINE_COUNT =
            new ListCellKey("HIDDEN_LINE_COUNT", new NumberCellColumn(0), false, false);

    /** lst_ASRSRetrievalCancel(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_ASRSRetrievalCancel(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO =
            new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalCancel(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO =
            new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalCancel(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE =
            new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalCancel(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME =
            new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalCancel(LST_STATION_NO) */
    private static final ListCellKey KEY_LST_STATION_NO =
            new ListCellKey("LST_STATION_NO", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalCancel(LST_STATION_NAME) */
    private static final ListCellKey KEY_LST_STATION_NAME =
            new ListCellKey("LST_STATION_NAME", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalCancel(LST_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT =
            new ListCellKey("LST_DETAIL_COUNT", new NumberCellColumn(0), true, false);

    /** lst_ASRSRetrievalCancel kyes */
    private static final ListCellKey[] LST_ASRSRETRIEVALCANCEL_KEYS = {
            KEY_HIDDEN_LINE_COUNT,
            KEY_LST_SELECT,
            KEY_LST_BATCH_NO,
            KEY_LST_ORDER_NO,
            KEY_LST_CUSTOMER_CODE,
            KEY_LST_STATION_NO,
            KEY_LST_DETAIL_COUNT,
            KEY_LST_CUSTOMER_NAME,
            KEY_LST_STATION_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** PullDownModel pul_WorkPlace */
    private WmsWorkspacePullDownModel _pdm_pul_WorkPlace;

    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    /** ListCellModel lst_ASRSRetrievalCancel */
    private ListCellModel _lcm_lst_ASRSRetrievalCancel;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsRetrievalOrderCancelBusiness()
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
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正

        // get event source.
        String eventSource = viewState.getString(_KEY_CONFIRMSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // remove event source.
        viewState.remove(_KEY_CONFIRMSOURCE);

        // check result.
        boolean isExecute = new Boolean(String.valueOf(e.getEventArgs().get(0))).booleanValue();
        if (!isExecute)
        {
            return;
        }

        // choose process.
        if (eventSource.startsWith("btn_RetrievalCancel_Click"))
        {
            btn_RetrievalCancel_Click_Process(eventSource);
        }
        // DFKLOOK：ここまで修正
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
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Clear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_RetrievalCancel_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_RetrievalCancel_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheck_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheck_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheckClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheckClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ListClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ASRSRetrievalCancel_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ASRSRetrievalCancel_ColumClick(ActionEvent e)
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
     * @throws Exception
     */
    private void initializeComponents()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);
        _pdm_pul_WorkPlace.setParent(_pdm_pul_Area);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
        _pdm_pul_Station.setParent(_pdm_pul_WorkPlace);

        // initialize lst_ASRSRetrievalCancel.
        _lcm_lst_ASRSRetrievalCancel = new ListCellModel(lst_ASRSRetrievalCancel, LST_ASRSRETRIEVALCANCEL_KEYS, locale);
        _lcm_lst_ASRSRetrievalCancel.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_ASRSRetrievalCancel.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_ASRSRetrievalCancel.setToolTipVisible(KEY_LST_ORDER_NO, false);
        _lcm_lst_ASRSRetrievalCancel.setToolTipVisible(KEY_LST_CUSTOMER_CODE, false);
        _lcm_lst_ASRSRetrievalCancel.setToolTipVisible(KEY_LST_CUSTOMER_NAME, false);
        _lcm_lst_ASRSRetrievalCancel.setToolTipVisible(KEY_LST_STATION_NO, false);
        _lcm_lst_ASRSRetrievalCancel.setToolTipVisible(KEY_LST_STATION_NAME, false);
        _lcm_lst_ASRSRetrievalCancel.setToolTipVisible(KEY_LST_DETAIL_COUNT, false);

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

            // load pul_Area.
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.RETRIEVAL, "", false);

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.RETRIEVAL, "", Distribution.ALL);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.RETRIEVAL, Distribution.ALL, Distribution.ALL);

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
    private void dispose()
            throws Exception
    {
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_ASRSRetrievalCancel_SetLineToolTip(ListCellLine line)
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
        // set focus.
        setFocus(txt_RetrievalPlanDate);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_RetrievalCancel.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);
        txt_LRRetrievalPlanDate.setReadOnly(true);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_RetrievalPlanDate.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_OrderNoFrom.validate(this, false);
        txt_OrderNoTo.validate(this, false);
        pul_Area.validate(this, true);
        pul_Station.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsRetrievalOrderCancelSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsRetrievalOrderCancelSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK ここから
            String areaNo = this.pul_Area.getSelectedValue();
            String stationNo = this.pul_Station.getSelectedValue();

            // set input parameters.
            AsRetrievalOrderCancelSCHParams inparam = new AsRetrievalOrderCancelSCHParams(ui);
            inparam.set(AsRetrievalOrderCancelSCHParams.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
            inparam.set(AsRetrievalOrderCancelSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(AsRetrievalOrderCancelSCHParams.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
            inparam.set(AsRetrievalOrderCancelSCHParams.ORDER_NO_TO, txt_OrderNoTo.getValue());
            inparam.set(AsRetrievalOrderCancelSCHParams.AREA, areaNo);
            inparam.set(AsRetrievalOrderCancelSCHParams.STATION, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsRetrievalOrderCancelSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            txt_LRRetrievalPlanDate.setText(txt_RetrievalPlanDate.getText());
            // 搬送先ステーションNo. = 作業場とセットする。
            inparam.set(AsRetrievalOrderCancelSCHParams.STATION, stationNo);
            // 再検索用
            viewState.setObject(ViewStateKeys.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
            viewState.setObject(ViewStateKeys.BATCH_NO, txt_BatchNo.getValue());
            viewState.setObject(ViewStateKeys.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
            viewState.setObject(ViewStateKeys.ORDER_NO_TO, txt_OrderNoTo.getValue());
            viewState.setObject(ViewStateKeys.AREA, areaNo);
            viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            viewState.setObject(ViewStateKeys.STATION, inparam.get(AsRetrievalOrderStartSCHParams.STATION));
            // DFKLOOK ここまで

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ASRSRetrievalCancel.clear();

            // DFKLOOK ここから
            if (outparams.size() == 0)
            {
                // ListCellボタン無効
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_ListClear.setEnabled(false);
                btn_RetrievalCancel.setEnabled(false);
                txt_LRRetrievalPlanDate.setText("");

                return;
            }
            // DFKLOOK ここまで


            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ASRSRetrievalCancel.getNewLine();

                line.setValue(KEY_LST_BATCH_NO, outparam.get(AsRetrievalOrderCancelSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(AsRetrievalOrderCancelSCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(AsRetrievalOrderCancelSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(AsRetrievalOrderCancelSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_STATION_NO, outparam.get(AsRetrievalOrderCancelSCHParams.STATION_NO));
                line.setValue(KEY_LST_STATION_NAME, outparam.get(AsRetrievalOrderCancelSCHParams.STATION_NAME));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(AsRetrievalOrderCancelSCHParams.DETAIL_COUNT));
                line.setValue(KEY_HIDDEN_LINE_COUNT, outparam.get(AsRetrievalOrderCancelSCHParams.LINE_COUNT));
                lst_ASRSRetrievalCancel_SetLineToolTip(line);
                _lcm_lst_ASRSRetrievalCancel.add(line);
            }

            // clear.
            btn_RetrievalCancel.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_ListClear.setEnabled(true);

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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_RetrievalPlanDate.setValue(null);
        txt_BatchNo.setValue(null);
        txt_OrderNoFrom.setValue(null);
        txt_OrderNoTo.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);
        _pdm_pul_WorkPlace.setSelectedValue(null);
        _pdm_pul_Station.setSelectedValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_RetrievalCancel_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_ASRSRetrievalCancel.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_ASRSRetrievalCancel.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            // DFKLOOK ここから
            // 6123103 = データを選択してください。
            message.setMsgResourceKey("6123103");
            // DFKLOOK ここまで 
            return;
        }

        // DFKLOOK:ここから修正
        if (StringUtil.isBlank(eventSource))
        {
            // 設定しますか？
            this.setConfirm("MSG-W9000", true, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_RetrievalCancel_Click");
            // 「処理中です」メッセージ表示
            message.setMsgResourceKey("6001017");
            return;
        }
        // DFKLOOK:ここまで修正


        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsRetrievalOrderCancelSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsRetrievalOrderCancelSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ASRSRetrievalCancel.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ASRSRetrievalCancel.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsRetrievalOrderCancelSCHParams lineparam = new AsRetrievalOrderCancelSCHParams(ui);
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(AsRetrievalOrderCancelSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(AsRetrievalOrderCancelSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                lineparam.set(AsRetrievalOrderCancelSCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                lineparam.set(AsRetrievalOrderCancelSCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(AsRetrievalOrderCancelSCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(AsRetrievalOrderCancelSCHParams.STATION_NO, line.getValue(KEY_LST_STATION_NO));
                lineparam.set(AsRetrievalOrderCancelSCHParams.STATION_NAME, line.getValue(KEY_LST_STATION_NAME));
                lineparam.set(AsRetrievalOrderCancelSCHParams.DETAIL_COUNT, line.getValue(KEY_LST_DETAIL_COUNT));
                lineparam.set(AsRetrievalOrderCancelSCHParams.AREA, viewState.getObject(ViewStateKeys.AREA));
                lineparam.set(AsRetrievalOrderCancelSCHParams.ORDER_NO_FROM,
                        viewState.getObject(ViewStateKeys.ORDER_NO_FROM));
                lineparam.set(AsRetrievalOrderCancelSCHParams.RETRIEVAL_PLAN_DATE,
                        viewState.getObject(ViewStateKeys.RETRIEVAL_PLAN_DATE));
                lineparam.set(AsRetrievalOrderCancelSCHParams.STATION, viewState.getObject(ViewStateKeys.STATION));
                lineparam.set(AsRetrievalOrderCancelSCHParams.WORK_PLACE, viewState.getObject(ViewStateKeys.WORK_PLACE));
                lineparam.set(AsRetrievalOrderCancelSCHParams.LINE_COUNT, line.getValue(KEY_HIDDEN_LINE_COUNT));
                lineparam.set(AsRetrievalOrderCancelSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                // DFKLOOK
                // オーダーNo.(to)(オペレータ内で使用する(オーダーNoと同一の値を設定))
                lineparam.set(AsRetrievalOrderCancelSCHParams.ORDER_NO_TO, line.getValue(KEY_LST_ORDER_NO));
                // ここまで
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highligiting error row.
                _lcm_lst_ASRSRetrievalCancel.resetEditRow();
                _lcm_lst_ASRSRetrievalCancel.resetHighlight();
                _lcm_lst_ASRSRetrievalCancel.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ASRSRetrievalCancel.size(); i++)
            {
                ListCellLine line = _lcm_lst_ASRSRetrievalCancel.get(i);
                lst_ASRSRetrievalCancel.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(viewState.getString(ViewStateKeys.AREA), "");
                part11List.add(line.getViewString(KEY_LST_STATION_NO), "");
                part11List.add(txt_LRRetrievalPlanDate.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_BATCH_NO), "");
                part11List.add(line.getViewString(KEY_LST_ORDER_NO), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());


            // reset editing row.
            _lcm_lst_ASRSRetrievalCancel.resetEditRow();
            _lcm_lst_ASRSRetrievalCancel.resetHighlight();

            // set input parameters.
            AsRetrievalOrderCancelSCHParams inparam = new AsRetrievalOrderCancelSCHParams(ui);
            inparam.set(AsRetrievalOrderCancelSCHParams.AREA, viewState.getObject(ViewStateKeys.AREA));
            inparam.set(AsRetrievalOrderCancelSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.BATCH_NO));
            inparam.set(AsRetrievalOrderCancelSCHParams.ORDER_NO_FROM, viewState.getObject(ViewStateKeys.ORDER_NO_FROM));
            inparam.set(AsRetrievalOrderCancelSCHParams.ORDER_NO_TO, viewState.getObject(ViewStateKeys.ORDER_NO_TO));
            inparam.set(AsRetrievalOrderCancelSCHParams.RETRIEVAL_PLAN_DATE,
                    viewState.getObject(ViewStateKeys.RETRIEVAL_PLAN_DATE));
            inparam.set(AsRetrievalOrderCancelSCHParams.STATION, viewState.getObject(ViewStateKeys.STATION));
            inparam.set(AsRetrievalOrderCancelSCHParams.CONSIGNOR_CODE,
                    viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // DFKLOOK ここから
            if (outparams.size() == 0)
            {
                // ListCellボタン無効
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_ListClear.setEnabled(false);
                btn_RetrievalCancel.setEnabled(false);
                txt_LRRetrievalPlanDate.setText("");
            }
            // DFKLOOK ここまで

            // output display.
            _lcm_lst_ASRSRetrievalCancel.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ASRSRetrievalCancel.getNewLine();
                line.setValue(KEY_LST_BATCH_NO, outparam.get(AsRetrievalOrderCancelSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(AsRetrievalOrderCancelSCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(AsRetrievalOrderCancelSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(AsRetrievalOrderCancelSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_STATION_NO, outparam.get(AsRetrievalOrderCancelSCHParams.STATION_NO));
                line.setValue(KEY_LST_STATION_NAME, outparam.get(AsRetrievalOrderCancelSCHParams.STATION_NAME));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(AsRetrievalOrderCancelSCHParams.DETAIL_COUNT));
                lst_ASRSRetrievalCancel_SetLineToolTip(line);
                _lcm_lst_ASRSRetrievalCancel.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_ASRSRetrievalCancel.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_ASRSRetrievalCancel.get(i);
            lst_ASRSRetrievalCancel.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_ASRSRetrievalCancel_SetLineToolTip(clearline);
            _lcm_lst_ASRSRetrievalCancel.set(i, clearline);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_ASRSRetrievalCancel.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_ASRSRetrievalCancel.get(i);
            lst_ASRSRetrievalCancel.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_ASRSRetrievalCancel_SetLineToolTip(clearline);
            _lcm_lst_ASRSRetrievalCancel.set(i, clearline);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_ListClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_ASRSRetrievalCancel.clear();
        btn_RetrievalCancel.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);
        txt_LRRetrievalPlanDate.setValue(null);

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
