// $Id: AsRetrievalOrderStartBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.display.orderstart;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
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
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.dasch.AsRetrievalOrderStartDASCH;
import jp.co.daifuku.wms.asrs.dasch.AsRetrievalOrderStartDASCHParams;
import jp.co.daifuku.wms.asrs.exporter.ASRSRetrievalListParams;
import jp.co.daifuku.wms.asrs.schedule.AsRetrievalOrderStartSCH;
import jp.co.daifuku.wms.asrs.schedule.AsRetrievalOrderStartSCHParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * AS/RS 出庫開始の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class AsRetrievalOrderStartBusiness
        extends AsRetrievalOrderStart
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

    /** lst_ASRSRetrievalStart(HIDDEN_LINE_COUNT) */
    private static final ListCellKey KEY_HIDDEN_LINE_COUNT =
            new ListCellKey("HIDDEN_LINE_COUNT", new NumberCellColumn(0), false, false);

    /** lst_ASRSRetrievalStart(HIDDEN_SETTING_UKEYS) */
    private static final ListCellKey KEY_HIDDEN_SETTING_UKEYS =
            new ListCellKey("HIDDEN_SETTING_UKEYS", new StringCellColumn(), false, false);

    /** lst_ASRSRetrievalStart(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_ASRSRetrievalStart(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO =
            new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalStart(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO =
            new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalStart(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE =
            new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalStart(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME =
            new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalStart(LST_STATION_NO) */
    private static final ListCellKey KEY_LST_STATION_NO =
            new ListCellKey("LST_STATION_NO", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalStart(LST_STATION_NAME) */
    private static final ListCellKey KEY_LST_STATION_NAME =
            new ListCellKey("LST_STATION_NAME", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalStart(LST_DETAIL_COUNT) */
    private static final ListCellKey KEY_LST_DETAIL_COUNT =
            new ListCellKey("LST_DETAIL_COUNT", new NumberCellColumn(0), true, false);

    /** lst_ASRSRetrievalStart kyes */
    private static final ListCellKey[] LST_ASRSRETRIEVALSTART_KEYS = {
            KEY_HIDDEN_LINE_COUNT,
            KEY_HIDDEN_SETTING_UKEYS,
            KEY_LST_SELECT,
            KEY_LST_BATCH_NO,
            KEY_LST_ORDER_NO,
            KEY_LST_CUSTOMER_CODE,
            KEY_LST_STATION_NO,
            KEY_LST_DETAIL_COUNT,
            KEY_LST_CUSTOMER_NAME,
            KEY_LST_STATION_NAME,
    };

    // DFKLOOK ここから
    /**
     * ダイアログ呼び出し元：設定ボタン
     */
    private static final String DIALOG_SET = "DIALOG_SET";

    // DFKLOOK ここまで
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

    /** ListCellModel lst_ASRSRetrievalStart */
    private ListCellModel _lcm_lst_ASRSRetrievalStart;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsRetrievalOrderStartBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     *
     * @param e ActionEvent
     * @throws Exception 例外を返します。
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
     * @throws Exception 例外を返します。
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
     * ダイアログボタンから、戻ってくるときにこのメソッドが呼ばれます。<BR>
     * <CODE>Page</CODE>に定義されている<CODE>page_ConfirmBack</CODE>をオーバライドします。<BR>
     * <BR>
     * @param e ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception 全ての例外を報告します。
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから
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


        // choose process.
        if (eventSource.equals("btn_RetrievalStart_Click_Check"))
        {

            // ダイアログの返答を記憶する
            this.getViewState().setBoolean(DIALOG_SET, isExecute);
        }
        else if (!isExecute)
        {
            return;
        }
        if (eventSource.startsWith("btn_RetrievalStart_Click"))
        {

            btn_RetrievalStart_Click_Process(eventSource);

        }
        // DFKLOOK: ここまで修正
    }


    /**
     *
     * @param e ActionEvent
     * @throws Exception 例外を返します。
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
     * @throws Exception 例外を返します。
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
     * @throws Exception 例外を返します。
     */
    public void btn_RetrievalStart_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK: ここから修正
        // process call.
        btn_RetrievalStart_Click_Process(null);
        // DFKLOOK: ここまで修正
    }


    /**
     *
     * @param e ActionEvent
     * @throws Exception 例外を返します。
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
     * @throws Exception 例外を返します。
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
     * @throws Exception 例外を返します。
     */
    public void btn_ListClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ListClear_Click_Process();
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
     * @throws Exception 例外を返します。
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

        // initialize lst_ASRSRetrievalStart.
        _lcm_lst_ASRSRetrievalStart = new ListCellModel(lst_ASRSRetrievalStart, LST_ASRSRETRIEVALSTART_KEYS, locale);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_ORDER_NO, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_CUSTOMER_CODE, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_CUSTOMER_NAME, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_STATION_NO, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_STATION_NAME, false);
        _lcm_lst_ASRSRetrievalStart.setToolTipVisible(KEY_LST_DETAIL_COUNT, false);

    }

    /**
     *
     * @throws Exception 例外を返します。
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
            // DFKLOOK ここから
            _pdm_pul_WorkPlace.init(conn, StationType.RETRIEVAL, "", Distribution.ALL);
            // DFKLOOK ここまで

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
     * @param line ListCellLine
     * @throws Exception 例外を返します。
     */
    private void lst_ASRSRetrievalStart_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @throws Exception 例外を返します。
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_RetrievalPlanDate);

    }

    /**
     *
     * @throws Exception 例外を返します。
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_RetrievalStart.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);
        chk_LIssueReport.setEnabled(false);
        chk_LIssueReport.setChecked(true);
        txt_LRRetrievalPlanDate.setReadOnly(true);
        // DFKLOOK: ここから
        this.txt_LRRetrievalPlanDate.setText("");

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsRetrievalOrderStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsRetrievalOrderStartSCH(conn, this.getClass(), locale, ui);

            AsRetrievalOrderStartSCHParams inParam = new AsRetrievalOrderStartSCHParams();
            inParam.set(AsRetrievalOrderStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));

            // 画面定義テーブルをチェック
            Params outParam = sch.initFind(inParam);

            if (outParam != null)
            {
                String printflg = outParam.getString(AsRetrievalOrderStartSCHParams.PRINT_FLAG);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_LIssueReport.setChecked(false);
                }
            }
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
        //DFKLOOK:ここまで修正

    }

    /**
     *
     * @throws Exception 例外を返します。
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
        AsRetrievalOrderStartSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsRetrievalOrderStartSCH(conn, this.getClass(), locale, ui);


            // DFKLOOK ここから
            // リストセルの出庫予定日を設定
            txt_LRRetrievalPlanDate.setDate(txt_RetrievalPlanDate.getDate());
            // リストセルをクリアする
            this.lst_ASRSRetrievalStart.clearRow();
            String areaNo = this.pul_Area.getSelectedValue();
            String stationNo = this.pul_Station.getSelectedValue();
            // DFKLOOK ここまで

            // set input parameters.
            AsRetrievalOrderStartSCHParams inparam = new AsRetrievalOrderStartSCHParams();
            inparam.set(AsRetrievalOrderStartSCHParams.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
            inparam.set(AsRetrievalOrderStartSCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(AsRetrievalOrderStartSCHParams.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
            inparam.set(AsRetrievalOrderStartSCHParams.ORDER_NO_TO, txt_OrderNoTo.getValue());
            inparam.set(AsRetrievalOrderStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            // DFKLOOK ここから
            inparam.set(AsRetrievalOrderStartSCHParams.AREA, areaNo);
            //搬送先ステーションNo. = ステーションNo.とセットする。
            inparam.set(AsRetrievalOrderStartSCHParams.STATION, stationNo);
            viewState.setObject(ViewStateKeys.RETRIEVAL_PLAN_DATE, txt_RetrievalPlanDate.getValue());
            viewState.setObject(ViewStateKeys.BATCH_NO, txt_BatchNo.getValue());
            viewState.setObject(ViewStateKeys.ORDER_NO_FROM, txt_OrderNoFrom.getValue());
            viewState.setObject(ViewStateKeys.ORDER_NO_TO, txt_OrderNoTo.getValue());
            viewState.setObject(ViewStateKeys.AREA, areaNo);
            viewState.setObject(ViewStateKeys.ORDER_NO_TO, txt_OrderNoTo.getValue());
            viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            viewState.setObject(ViewStateKeys.STATION, inparam.get(AsRetrievalOrderStartSCHParams.STATION));
            // DFKLOOK ここまで

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから
            if (outparams.size() == 0)
            {
                btn_RetrievalStart.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_ListClear.setEnabled(false);
                chk_LIssueReport.setEnabled(false);
                txt_LRRetrievalPlanDate.setText("");

                return;
            }
            // DFKLOOK: ここまで修正


            // output display.
            _lcm_lst_ASRSRetrievalStart.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ASRSRetrievalStart.getNewLine();
                line.setValue(KEY_LST_BATCH_NO, outparam.get(AsRetrievalOrderStartSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(AsRetrievalOrderStartSCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(AsRetrievalOrderStartSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(AsRetrievalOrderStartSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_STATION_NO, outparam.get(AsRetrievalOrderStartSCHParams.STATION_NO));
                line.setValue(KEY_LST_STATION_NAME, outparam.get(AsRetrievalOrderStartSCHParams.STATION_NAME));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(AsRetrievalOrderStartSCHParams.DETAIL_COUNT));
                line.setValue(KEY_HIDDEN_LINE_COUNT, outparam.get(AsRetrievalOrderStartSCHParams.LINE_COUNT));
                lst_ASRSRetrievalStart_SetLineToolTip(line);
                _lcm_lst_ASRSRetrievalStart.add(line);
            }

            // clear.
            btn_RetrievalStart.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_ListClear.setEnabled(true);
            chk_LIssueReport.setEnabled(true);

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
     * @throws Exception 例外を返します。
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
     * @throws Exception 例外を返します。
     */
    private void btn_RetrievalStart_Click_Process(String eventSource)
            throws Exception
    {

        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_ASRSRetrievalStart.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_ASRSRetrievalStart.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {

                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            // 6123103 = データを選択してください。
            message.setMsgResourceKey("6123103");
            return;
        }

        // DFKLOOK ここから
        if (StringUtil.isBlank(eventSource))
        {
            // MSG-W0031=開始しますか？
            this.setConfirm("MSG-W0031", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_RetrievalStart_Click");
            return;
        }

        if (eventSource.equals("btn_RetrievalStart_Click"))
        {
            // MSG-W0060=開始対象の搬送作業よりも、先に引当てられた未開始の搬送作業があった場合
            // 処理を中止しますか？中止する場合は「OK」、中止しない場合は「キャンセル」を選択してください。
            this.setConfirm("MSG-W0060", true, true);
            // 設定ボタンからダイアログ表示されたことを記憶する
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_RetrievalStart_Click_Check");
            //this.getViewState().setBoolean(DIALOG_SET, true);
            return;
        }
        // DFKLOOK ここまで

        Connection conn = null;
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();
        AsRetrievalOrderStartSCH sch = null;

        try
        {

            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsRetrievalOrderStartSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ASRSRetrievalStart.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ASRSRetrievalStart.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsRetrievalOrderStartSCHParams lineparam = new AsRetrievalOrderStartSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(AsRetrievalOrderStartSCHParams.LINE_COUNT, line.getValue(KEY_HIDDEN_LINE_COUNT));
                lineparam.set(AsRetrievalOrderStartSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(AsRetrievalOrderStartSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                lineparam.set(AsRetrievalOrderStartSCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                lineparam.set(AsRetrievalOrderStartSCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(AsRetrievalOrderStartSCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(AsRetrievalOrderStartSCHParams.STATION_NO, line.getValue(KEY_LST_STATION_NO));
                lineparam.set(AsRetrievalOrderStartSCHParams.STATION_NAME, line.getValue(KEY_LST_STATION_NAME));
                lineparam.set(AsRetrievalOrderStartSCHParams.DETAIL_COUNT, line.getValue(KEY_LST_DETAIL_COUNT));
                lineparam.set(AsRetrievalOrderStartSCHParams.AREA, viewState.getObject(ViewStateKeys.AREA));
                lineparam.set(AsRetrievalOrderStartSCHParams.ORDER_NO_FROM,
                        viewState.getObject(ViewStateKeys.ORDER_NO_FROM));
                lineparam.set(AsRetrievalOrderStartSCHParams.ORDER_NO_TO,
                        viewState.getObject(ViewStateKeys.ORDER_NO_TO));
                lineparam.set(AsRetrievalOrderStartSCHParams.STATION, viewState.getObject(ViewStateKeys.STATION));
                lineparam.set(AsRetrievalOrderStartSCHParams.WORK_PLACE, viewState.getObject(ViewStateKeys.WORK_PLACE));
                lineparam.set(AsRetrievalOrderStartSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(AsRetrievalOrderStartSCHParams.PRINT_FLAG, chk_LIssueReport.getValue());
                //  DFKLOOK ここから
                lineparam.set(AsRetrievalOrderStartSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
                //  DFKLOOK ここまで
                lineparam.set(AsRetrievalOrderStartSCHParams.ERROR_ALLOC_CARRY, DIALOG_SET);
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);
            // SCH call
            List<Params> schparams = sch.startSCHgetParams(inparams);
            if (schparams == null)
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highligiting error row.
                _lcm_lst_ASRSRetrievalStart.resetEditRow();
                _lcm_lst_ASRSRetrievalStart.resetHighlight();
                _lcm_lst_ASRSRetrievalStart.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ASRSRetrievalStart.size(); i++)
            {
                ListCellLine line = _lcm_lst_ASRSRetrievalStart.get(i);
                lst_ASRSRetrievalStart.setCurrentRow(i);

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

                if (chk_LIssueReport.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            if (chk_LIssueReport.getChecked())
            {
                // 印刷処理
                // DFKLOOK 3.5 UPD START
                List<Params> printParams = sch.sortPrintParams(schparams);
                printAsRetrievalOrderStartList(locale, ui, printParams);
                // DFKLOOK 3.5 UPD END
            }

            // 出庫指示送信へRMIメッセージを使用して出庫要求を行います。
            SendRequestor req = new SendRequestor();
            req.retrieval();

            // set input parameters.
            AsRetrievalOrderStartSCHParams inparam = new AsRetrievalOrderStartSCHParams();
            inparam.set(AsRetrievalOrderStartSCHParams.RETRIEVAL_PLAN_DATE,
                    viewState.getObject(ViewStateKeys.RETRIEVAL_PLAN_DATE));
            inparam.set(AsRetrievalOrderStartSCHParams.BATCH_NO, viewState.getObject(ViewStateKeys.BATCH_NO));
            inparam.set(AsRetrievalOrderStartSCHParams.ORDER_NO_FROM, viewState.getObject(ViewStateKeys.ORDER_NO_FROM));
            inparam.set(AsRetrievalOrderStartSCHParams.ORDER_NO_TO, viewState.getObject(ViewStateKeys.ORDER_NO_TO));
            inparam.set(AsRetrievalOrderStartSCHParams.AREA, viewState.getObject(ViewStateKeys.AREA));
            inparam.set(AsRetrievalOrderStartSCHParams.CONSIGNOR_CODE,
                    viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(AsRetrievalOrderStartSCHParams.STATION, viewState.getObject(ViewStateKeys.STATION));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            if (outparams.size() == 0)
            {
                // clear.
                btn_RetrievalStart.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_ListClear.setEnabled(false);
                chk_LIssueReport.setEnabled(false);
                txt_LRRetrievalPlanDate.setText("");
            }


            // output display.
            _lcm_lst_ASRSRetrievalStart.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ASRSRetrievalStart.getNewLine();
                line.setValue(KEY_HIDDEN_LINE_COUNT, outparam.get(AsRetrievalOrderStartSCHParams.LINE_COUNT));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(AsRetrievalOrderStartSCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(AsRetrievalOrderStartSCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(AsRetrievalOrderStartSCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(AsRetrievalOrderStartSCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_STATION_NO, outparam.get(AsRetrievalOrderStartSCHParams.STATION_NO));
                line.setValue(KEY_LST_STATION_NAME, outparam.get(AsRetrievalOrderStartSCHParams.STATION_NAME));
                line.setValue(KEY_LST_DETAIL_COUNT, outparam.get(AsRetrievalOrderStartSCHParams.DETAIL_COUNT));
                lst_ASRSRetrievalStart_SetLineToolTip(line);
                _lcm_lst_ASRSRetrievalStart.add(line);
            }

            // reset editing row.
            _lcm_lst_ASRSRetrievalStart.resetEditRow();
            _lcm_lst_ASRSRetrievalStart.resetHighlight();

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
     * @throws Exception 例外を返します。
     */
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_ASRSRetrievalStart.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_ASRSRetrievalStart.get(i);
            lst_ASRSRetrievalStart.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_ASRSRetrievalStart_SetLineToolTip(clearline);
            _lcm_lst_ASRSRetrievalStart.set(i, clearline);
        }

    }

    /**
     *
     * @throws Exception 例外を返します。
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_ASRSRetrievalStart.size(); i++)
        {
            ListCellLine clearline = _lcm_lst_ASRSRetrievalStart.get(i);
            lst_ASRSRetrievalStart.setCurrentRow(i);
            clearline.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_ASRSRetrievalStart_SetLineToolTip(clearline);
            _lcm_lst_ASRSRetrievalStart.set(i, clearline);
        }

    }

    /**
     *
     * @throws Exception 例外を返します。
     */
    private void btn_ListClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_ASRSRetrievalStart.clear();
        btn_RetrievalStart.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_ListClear.setEnabled(false);
        chk_LIssueReport.setEnabled(false);
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

    // DFKLOOK ここから
    // 帳票発行メソッド追加
    /**
     * AS/RS オーダーピッキングリストを発行します
     *
     * @param locale Locale
     * @param ui DfkUserInfo
     * @param schparams List
     * @exception Exception 例外を返します。
     */
    private void printAsRetrievalOrderStartList(Locale locale, DfkUserInfo ui, List<Params> schparams)
            throws Exception
    {
        Connection conn = null;
        AsRetrievalOrderStartDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsRetrievalOrderStartDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // 設定単位キーの取得
            Iterator<Params> it = schparams.iterator();
            int i = 1;
            while (it.hasNext())
            {
                Params pNextparam = (Params)it.next();
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ASRSRetrievalStart.get(i++);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // set input parameters.
                AsRetrievalOrderStartDASCHParams inparam = new AsRetrievalOrderStartDASCHParams();
                inparam.set(AsRetrievalOrderStartDASCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                inparam.set(AsRetrievalOrderStartDASCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                inparam.set(AsRetrievalOrderStartDASCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                inparam.set(AsRetrievalOrderStartDASCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                inparam.set(AsRetrievalOrderStartDASCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                //DFKLOOK 3.5 Start
                inparam.set(AsRetrievalOrderStartDASCHParams.STATION_NO, pNextparam.get(AsRetrievalOrderStartSCHParams.STATION_NO));
                //DFKLOOK 3.5 End
                inparam.set(AsRetrievalOrderStartDASCHParams.STATION_NAME, line.getValue(KEY_LST_STATION_NAME));
                inparam.set(AsRetrievalOrderStartDASCHParams.DETAIL_COUNT, line.getValue(KEY_LST_DETAIL_COUNT));
                inparam.set(AsRetrievalOrderStartDASCHParams.SETTING_UKEYS,
                        pNextparam.get(AsRetrievalOrderStartSCHParams.SETTING_UKEYS));

                // check count.
                int count = dasch.count(inparam);
                if (count == 0)
                {
                    message.setMsgResourceKey("6007042");
                    return;
                }
                // DASCH call.
                dasch.search(inparam);

                // open exporter.
                if (exporter == null)
                {
                    ExporterFactory factory = new WmsExporterFactory(locale, ui);
                    exporter = factory.newPrinterExporter("ASRSRetrievalList", false);
                    exporter.open();
                }
                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    ASRSRetrievalListParams expparam = new ASRSRetrievalListParams();
                    expparam.set(ASRSRetrievalListParams.DFK_DS_NO,
                            outparam.get(AsRetrievalOrderStartDASCHParams.DFK_DS_NO));
                    expparam.set(ASRSRetrievalListParams.DFK_USER_ID,
                            outparam.get(AsRetrievalOrderStartDASCHParams.DFK_USER_ID));
                    expparam.set(ASRSRetrievalListParams.DFK_USER_NAME,
                            outparam.get(AsRetrievalOrderStartDASCHParams.DFK_USER_NAME));
                    expparam.set(ASRSRetrievalListParams.AREA_NO,
                            outparam.get(AsRetrievalOrderStartDASCHParams.AREA_NO));
                    expparam.set(ASRSRetrievalListParams.AREA_NAME,
                            outparam.get(AsRetrievalOrderStartDASCHParams.AREA_NAME));
                    expparam.set(ASRSRetrievalListParams.STATION_NO,
                            outparam.get(AsRetrievalOrderStartDASCHParams.STATION_NO));
                    expparam.set(ASRSRetrievalListParams.STATION_NAME,
                            outparam.get(AsRetrievalOrderStartDASCHParams.STATION_NAME));
                    expparam.set(ASRSRetrievalListParams.SYS_DAY,
                            outparam.get(AsRetrievalOrderStartDASCHParams.SYS_DAY));
                    expparam.set(ASRSRetrievalListParams.SYS_TIME,
                            outparam.get(AsRetrievalOrderStartDASCHParams.SYS_TIME));
                    expparam.set(ASRSRetrievalListParams.BATCH_NO,
                            outparam.get(AsRetrievalOrderStartDASCHParams.BATCH_NO));
                    expparam.set(ASRSRetrievalListParams.ORDER_NO,
                            outparam.get(AsRetrievalOrderStartDASCHParams.ORDER_NO));
                    expparam.set(ASRSRetrievalListParams.CUSTOMER_CODE,
                            outparam.get(AsRetrievalOrderStartDASCHParams.CUSTOMER_CODE));
                    expparam.set(ASRSRetrievalListParams.CUSTOMER_NAME,
                            outparam.get(AsRetrievalOrderStartDASCHParams.CUSTOMER_NAME));
                    expparam.set(ASRSRetrievalListParams.JOB_NO, outparam.get(AsRetrievalOrderStartDASCHParams.JOB_NO));
                    expparam.set(ASRSRetrievalListParams.RETRIEVAL_LOCATION_NO,
                            outparam.get(AsRetrievalOrderStartDASCHParams.RETRIEVAL_LOCATION_NO));
                    expparam.set(ASRSRetrievalListParams.ITEM_CODE,
                            outparam.get(AsRetrievalOrderStartDASCHParams.ITEM_CODE));
                    expparam.set(ASRSRetrievalListParams.ITEM_NAME,
                            outparam.get(AsRetrievalOrderStartDASCHParams.ITEM_NAME));
                    expparam.set(ASRSRetrievalListParams.LOT_NO, outparam.get(AsRetrievalOrderStartDASCHParams.LOT_NO));
                    expparam.set(ASRSRetrievalListParams.RETRIEVAL_PLAN_DAY,
                            outparam.get(AsRetrievalOrderStartDASCHParams.RETRIEVAL_PLAN_DAY));
                    expparam.set(ASRSRetrievalListParams.ENTERING_QTY,
                            outparam.get(AsRetrievalOrderStartDASCHParams.ENTERING_QTY));
                    expparam.set(ASRSRetrievalListParams.RETRIEVAL_CASE_QTY,
                            outparam.get(AsRetrievalOrderStartDASCHParams.RETRIEVAL_CASE_QTY));
                    expparam.set(ASRSRetrievalListParams.RETRIEVAL_PIECE_QTY,
                            outparam.get(AsRetrievalOrderStartDASCHParams.RETRIEVAL_PIECE_QTY));
                    expparam.set(ASRSRetrievalListParams.JAN, outparam.get(AsRetrievalOrderStartDASCHParams.JAN));
                    expparam.set(ASRSRetrievalListParams.ITF, outparam.get(AsRetrievalOrderStartDASCHParams.ITF));
                    if (!exporter.write(expparam))
                    {
                        return;
                    }
                }
            }
            // execute print.
            try
            {
                exporter.print();
                message.setMsgResourceKey("6021021");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007042");
                return;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.close(conn);
        }
    }

    // DFKLOOK ここまで

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
