// $Id: AsNoPlanRetrievalBusiness.java 7811 2010-04-09 10:04:04Z okayama $
package jp.co.daifuku.wms.asrs.display.noplanretrieval;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.dasch.AsNoPlanRetrievalDASCH;
import jp.co.daifuku.wms.asrs.dasch.AsNoPlanRetrievalDASCHParams;
import jp.co.daifuku.wms.asrs.exporter.ASRSNoPlanRetrievalListParams;
import jp.co.daifuku.wms.asrs.schedule.AsNoPlanRetrievalSCH;
import jp.co.daifuku.wms.asrs.schedule.AsNoPlanRetrievalSCHParams;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * AS/RS 予定外出庫設定の画面処理を行います。
 *
 * @version $Revision: 7811 $, $Date: 2010-04-09 19:04:04 +0900 (金, 09 4 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
@SuppressWarnings("serial")
public class AsNoPlanRetrievalBusiness
        extends AsNoPlanRetrieval
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";
    // DFKLOOK ここから修正
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK ここまで修正

    /** lst_NoPlanRetrieval(LST_HDN_STOCK_ID) */
    private static final ListCellKey KEY_LST_HDN_STOCK_ID = new ListCellKey("LST_HDN_STOCK_ID", new StringCellColumn(), false, false);

    /** lst_NoPlanRetrieval(LST_HDN_LOCATION) */
    private static final ListCellKey KEY_LST_HDN_LOCATION = new ListCellKey("LST_HDN_LOCATION", new StringCellColumn(), false, false);

    /** lst_NoPlanRetrieval(LST_HDN_LOT) */
    private static final ListCellKey KEY_LST_HDN_LOT = new ListCellKey("LST_HDN_LOT", new StringCellColumn(), false, false);

    /** lst_NoPlanRetrieval(LST_HDN_PALLET_ID) */
    private static final ListCellKey KEY_LST_HDN_PALLET_ID = new ListCellKey("LST_HDN_PALLET_ID", new StringCellColumn(), false, false);

    /** lst_NoPlanRetrieval(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_NoPlanRetrieval(LST_STORAGE_DATE) */
    private static final ListCellKey KEY_LST_STORAGE_DATE = new ListCellKey("LST_STORAGE_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_NoPlanRetrieval(LST_STORAGE_TIME) */
    private static final ListCellKey KEY_LST_STORAGE_TIME = new ListCellKey("LST_STORAGE_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, false);

    /** lst_NoPlanRetrieval(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_NoPlanRetrieval(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_NoPlanRetrieval(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_NoPlanRetrieval(LST_ALLOC_CASE_QTY) */
    private static final ListCellKey KEY_LST_ALLOC_CASE_QTY = new ListCellKey("LST_ALLOC_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_NoPlanRetrieval(LST_ALLOC_PIECE_QTY) */
    private static final ListCellKey KEY_LST_ALLOC_PIECE_QTY = new ListCellKey("LST_ALLOC_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_NoPlanRetrieval(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, true);

    /** lst_NoPlanRetrieval(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, true);

    /** lst_NoPlanRetrieval(LST_ALL) */
    private static final ListCellKey KEY_LST_ALL = new ListCellKey("LST_ALL", new CheckBoxColumn(), true, true);

    /** lst_NoPlanRetrieval(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_NoPlanRetrieval(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_NoPlanRetrieval(LST_REASON) */
    private static final ListCellKey KEY_LST_REASON = new ListCellKey("LST_REASON", new StringCellColumn(), true, false);

    /** lst_NoPlanRetrieval keys */
    private static final ListCellKey[] LST_NOPLANRETRIEVAL_KEYS = {
        KEY_LST_HDN_STOCK_ID,
        KEY_LST_HDN_LOCATION,
        KEY_LST_HDN_LOT,
        KEY_LST_HDN_PALLET_ID,
        KEY_LST_LOT_NO,
        KEY_LST_STORAGE_DATE,
        KEY_LST_AREA_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_ALLOC_CASE_QTY,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_ALL,
        KEY_LST_JAN,
        KEY_LST_REASON,
        KEY_LST_STORAGE_TIME,
        KEY_LST_LOCATION_NO,
        KEY_LST_ALLOC_PIECE_QTY,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_ITF,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_RetrievalArea */
    private WmsAreaPullDownModel _pdm_pul_RetrievalArea;

    /** PullDownModel pul_WorkPlace */
    private WmsWorkspacePullDownModel _pdm_pul_WorkPlace;

    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    /** ListCellModel lst_NoPlanRetrieval */
    private ListCellModel _lcm_lst_NoPlanRetrieval;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsNoPlanRetrievalBusiness()
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
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
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
        if (eventSource.startsWith("btn_Set_Click"))
        {
            btn_Set_Click_Process(eventSource);
        }
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_RetrievalArea_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        // 棚の入力例を表示させます。
        lbl_In_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                _pdm_pul_RetrievalArea.getSelectedValue()));
        txt_FromLocation.setReadOnly(false);
        txt_ToLocation.setReadOnly(false);
        // DFKLOOK end
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
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから修正
        // process call.
        btn_Set_Click_Process(null);
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ClearListInput_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ClearListInput_Click_Process();
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
    public void btn_AllClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllClear_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_NoPlanRetrieval_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_NoPlanRetrieval_ColumClick(ActionEvent e)
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

        // initialize pul_RetrievalArea.
        _pdm_pul_RetrievalArea = new WmsAreaPullDownModel(pul_RetrievalArea, locale, ui);

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);
        _pdm_pul_WorkPlace.setParent(_pdm_pul_RetrievalArea);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
        _pdm_pul_Station.setParent(_pdm_pul_WorkPlace);

        // initialize lst_NoPlanRetrieval.
        _lcm_lst_NoPlanRetrieval = new ListCellModel(lst_NoPlanRetrieval, LST_NOPLANRETRIEVAL_KEYS, locale);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_LOT_NO, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_STORAGE_DATE, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_STORAGE_TIME, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_AREA_NO, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_LOCATION_NO, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_ENTERING_QTY, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_ALLOC_CASE_QTY, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_ALLOC_PIECE_QTY, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_ALL, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_JAN, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_ITF, false);
        _lcm_lst_NoPlanRetrieval.setToolTipVisible(KEY_LST_REASON, false);

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

            // load pul_RetrievalArea.
            _pdm_pul_RetrievalArea.init(conn, AreaType.ASRS, StationType.RETRIEVAL, "", false);

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.RETRIEVAL);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.RETRIEVAL, Distribution.AUTO);

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
    private void lst_NoPlanRetrieval_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_ItemCode);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Set.setEnabled(false);
        btn_ClearListInput.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        txt_LItemCode.setReadOnly(true);
        txt_LItemName.setReadOnly(true);
        chk_LIssueReport.setChecked(true);
        chk_LIssueReport.setEnabled(false);
        
        // DFKLOOK:ここから修正
        _pdm_pul_RetrievalArea.setSelectedValue(0);
        // 棚の入力例を表示させます。
        lbl_In_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                _pdm_pul_RetrievalArea.getSelectedValue()));
        txt_FromLocation.setReadOnly(false);
        txt_ToLocation.setReadOnly(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsNoPlanRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsNoPlanRetrievalSCH(conn, this.getClass(), locale, ui);
            
            AsNoPlanRetrievalSCHParams inParam = new AsNoPlanRetrievalSCHParams();
            inParam.set(AsNoPlanRetrievalSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));

            // 画面定義テーブルをチェック
            Params outParam = sch.initFind(inParam);
            
            if (outParam != null)
            {
                String printflg = outParam.getString(AsNoPlanRetrievalSCHParams.L_ISSUE_REPORT);
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
        txt_ItemCode.validate(this, true);
        txt_LotNo.validate(this, false);
        txt_FromLocation.validate(this, false);
        txt_ToLocation.validate(this, false);
        pul_RetrievalArea.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsNoPlanRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsNoPlanRetrievalSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsNoPlanRetrievalSCHParams inparam = new AsNoPlanRetrievalSCHParams();
            inparam.set(AsNoPlanRetrievalSCHParams.AREA_NO, _pdm_pul_RetrievalArea.getSelectedValue());
            inparam.set(AsNoPlanRetrievalSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsNoPlanRetrievalSCHParams.STATION, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsNoPlanRetrievalSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(AsNoPlanRetrievalSCHParams.LOT_NO, txt_LotNo.getValue());
            //DFKLOOK:ここから修正
            String fromloc =
                    WmsFormatter.toParamLocation(txt_FromLocation.getStringValue(),
                            lbl_In_LocationStyle.getStringValue());
            String toloc =
                    WmsFormatter.toParamLocation(txt_ToLocation.getStringValue(), lbl_In_LocationStyle.getStringValue());
            inparam.set(AsNoPlanRetrievalSCHParams.FROM_LOCATION, fromloc);
            inparam.set(AsNoPlanRetrievalSCHParams.TO_LOCATION, toloc);
            //DFKLOOK:ここまで修正
            inparam.set(AsNoPlanRetrievalSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_NoPlanRetrieval.clear();
            
            // DFKLOOK start
            if (outparams.size() == 0)
            {
                btn_Set.setEnabled(false);
                btn_ClearListInput.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                chk_LIssueReport.setEnabled(false);
                txt_LItemCode.setValue("");
                txt_LItemName.setValue("");
                return;
            }

            PulldownController pc = new PulldownController(conn, ui.getTerminalNumber(), locale, getClass());
            PullDownItem[] pul = pc.getReasonPulldownItem();
            int i = 1;
            // DFKLOOK end

            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_NoPlanRetrieval.getNewLine();
                line.setValue(KEY_LST_LOT_NO, outparam.get(AsNoPlanRetrievalSCHParams.LOT_NO));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(AsNoPlanRetrievalSCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(AsNoPlanRetrievalSCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_AREA_NO, outparam.get(AsNoPlanRetrievalSCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(AsNoPlanRetrievalSCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsNoPlanRetrievalSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_ALLOC_CASE_QTY, outparam.get(AsNoPlanRetrievalSCHParams.ALLOC_CASE_QTY));
                line.setValue(KEY_LST_ALLOC_PIECE_QTY, outparam.get(AsNoPlanRetrievalSCHParams.ALLOC_PIECE_QTY));
                line.setValue(KEY_LST_JAN, outparam.get(AsNoPlanRetrievalSCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(AsNoPlanRetrievalSCHParams.ITF));
                viewState.setObject(ViewStateKeys.RETRIEVAL_AREA, _pdm_pul_RetrievalArea.getSelectedValue());
                viewState.setObject(ViewStateKeys.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
                viewState.setObject(ViewStateKeys.STATION, _pdm_pul_Station.getSelectedValue());
                viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
                viewState.setObject(ViewStateKeys.LOT_NO, txt_LotNo.getValue());
                viewState.setObject(ViewStateKeys.FROM_LOCATION, fromloc);
                viewState.setObject(ViewStateKeys.TO_LOCATION, toloc);
                txt_LItemCode.setValue(outparam.get(AsNoPlanRetrievalSCHParams.ITEM_CODE));
                txt_LItemName.setValue(outparam.get(AsNoPlanRetrievalSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_HDN_STOCK_ID, outparam.get(AsNoPlanRetrievalSCHParams.STOCK_ID));
                line.setValue(KEY_LST_HDN_LOCATION, outparam.get(AsNoPlanRetrievalSCHParams.LOCATION_NO));
                line.setValue(KEY_LST_HDN_LOT, outparam.get(AsNoPlanRetrievalSCHParams.LOT_NO));
                line.setValue(KEY_LST_HDN_PALLET_ID, outparam.get(AsNoPlanRetrievalSCHParams.PALLET_ID));
                viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lst_NoPlanRetrieval_SetLineToolTip(line);
                _lcm_lst_NoPlanRetrieval.add(line);
                
                // DFKLOOK start add
                lst_NoPlanRetrieval.setCurrentRow(i);
                int entQty = outparam.getInt(AsNoPlanRetrievalSCHParams.ENTERING_QTY);
                if (entQty == 0)
                {
                    lst_NoPlanRetrieval.setCellReadOnly(6, true);
                }
                lst_NoPlanRetrieval.setPullDownItems(9, Arrays.asList(pul));

                i++;
                // DFKLOOK end
            }

            // clear.
            btn_Set.setEnabled(true);
            btn_ClearListInput.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_LIssueReport.setEnabled(true);

        }
        //DFKLOOK:ここから修正
        catch (LocationFormatException e) {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(e, this));
        }
        //DFKLOOK:ここまで修正
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
        txt_ItemCode.setValue(null);
        txt_LotNo.setValue(null);
        txt_FromLocation.setValue(null);
        txt_ToLocation.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK
        // reset editing row.
        _lcm_lst_NoPlanRetrieval.resetHighlight();

        // input validation.
        pul_Station.validate(this, true);

        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_NoPlanRetrieval.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_NoPlanRetrieval.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_NoPlanRetrieval.setCurrentRow(i);
            lst_NoPlanRetrieval.validate(checkline.getIndex(KEY_LST_LOT_NO), false);
            lst_NoPlanRetrieval.validate(checkline.getIndex(KEY_LST_PLAN_CASE_QTY), false);
            lst_NoPlanRetrieval.validate(checkline.getIndex(KEY_LST_PLAN_PIECE_QTY), false);
            lst_NoPlanRetrieval.validate(checkline.getIndex(KEY_LST_REASON), true);

            int workCase = 0;
            if(!StringUtil.isBlank(checkline.getValue(KEY_LST_PLAN_CASE_QTY)))
            {
            	workCase = checkline.getNumberValue(KEY_LST_PLAN_CASE_QTY).intValue();
            }
            int workPiece = 0;
            if(!StringUtil.isBlank(checkline.getValue(KEY_LST_PLAN_PIECE_QTY)))
            {
            	workPiece = checkline.getNumberValue(KEY_LST_PLAN_PIECE_QTY).intValue();
            }
            int entQty = checkline.getNumberValue(KEY_LST_ENTERING_QTY).intValue();
            int allocCase = checkline.getNumberValue(KEY_LST_ALLOC_CASE_QTY).intValue();
            int allocPiece = checkline.getNumberValue(KEY_LST_ALLOC_PIECE_QTY).intValue();

            int sumQty = workCase * entQty + workPiece;

            if (lst_NoPlanRetrieval.getChecked(checkline.getIndex(KEY_LST_ALL)))
            {
                // 全数にチェックがあって かつ
                // 出庫数に入力がある場合エラー
                if (sumQty > 0)
                {
                    // No.{0} 全数を選択する場合は、出庫数は入力できません。
                	message.setMsgResourceKey(WmsMessageFormatter.format(6023145, i));
                    _lcm_lst_NoPlanRetrieval.addHighlight(i, ControlColor.Warning);
                    return;
                }
                
                // 引当可能数を作業数とする
                workCase = allocCase;
                workPiece = allocPiece;
                sumQty = workCase * entQty + workPiece;
            }

            int allocateQty = allocCase * entQty + allocPiece;

            // 引当可能数が0の場合
            if (allocateQty == 0)
            {
                // 6023061=No.{0} 引当可能数が0の場合は、作業できません。
            	message.setMsgResourceKey(WmsMessageFormatter.format(6023061, i));
            	_lcm_lst_NoPlanRetrieval.addHighlight(i, ControlColor.Warning);
                return;
            }

            // 出庫数が0の場合
            if (sumQty <= 0)
            {
                // 6023046=No.{0} ケース数またはピース数には1以上の値を入力してください。
            	message.setMsgResourceKey(WmsMessageFormatter.format(6023046, i));
            	_lcm_lst_NoPlanRetrieval.addHighlight(i, ControlColor.Warning);
                return;
            }
            // 出庫数が1以上設定の場合
            else
            {
                // 出庫数が在庫数の最大値より大きい場合
                if (sumQty > WmsParam.MAX_STOCK_QTY)
                {
                    // 6023207=No.{0} 出庫数には作業上限数{1}以下の値を入力してください。
                	message.setMsgResourceKey(WmsMessageFormatter.format(6023207, i,
                            WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY)));
                	_lcm_lst_NoPlanRetrieval.addHighlight(i, ControlColor.Warning);
                    return;
                }

                // 出庫数（入力データ）が引当可能数より大きい
                if (sumQty > allocateQty)
                {
                    // 6023189=No.{0} 出庫数には引当可能数以下の値を入力してください。
                	message.setMsgResourceKey(WmsMessageFormatter.format(6023189, i));
                	_lcm_lst_NoPlanRetrieval.addHighlight(i, ControlColor.Warning);
                    return;
                }
            }
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6023195");
            return;
        }

        // DFKLOOK:ここから修正
        if (StringUtil.isBlank(eventSource))
        {
            // 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;
        }
        // DFKLOOK:ここまで修正
 
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsNoPlanRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsNoPlanRetrievalSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_NoPlanRetrieval.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_NoPlanRetrieval.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsNoPlanRetrievalSCHParams lineparam = new AsNoPlanRetrievalSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(AsNoPlanRetrievalSCHParams.STOCK_ID, line.getValue(KEY_LST_HDN_STOCK_ID));
                lineparam.set(AsNoPlanRetrievalSCHParams.LOCATION_NO, line.getValue(KEY_LST_HDN_LOCATION));
                lineparam.set(AsNoPlanRetrievalSCHParams.LOT_NO, line.getValue(KEY_LST_HDN_LOT));
                lineparam.set(AsNoPlanRetrievalSCHParams.PALLET_ID, line.getValue(KEY_LST_HDN_PALLET_ID));
                lineparam.set(AsNoPlanRetrievalSCHParams.RESULT_LOT, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(AsNoPlanRetrievalSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(AsNoPlanRetrievalSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(AsNoPlanRetrievalSCHParams.ALL, line.getValue(KEY_LST_ALL));
                lineparam.set(AsNoPlanRetrievalSCHParams.REASON, line.getValue(KEY_LST_REASON));
                lineparam.set(AsNoPlanRetrievalSCHParams.STATION, _pdm_pul_Station.getSelectedValue());
                lineparam.set(AsNoPlanRetrievalSCHParams.AREA_NO, line.getValue(KEY_LST_AREA_NO));
                lineparam.set(AsNoPlanRetrievalSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(AsNoPlanRetrievalSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(AsNoPlanRetrievalSCHParams.ALLOC_CASE_QTY, line.getValue(KEY_LST_ALLOC_CASE_QTY));
                lineparam.set(AsNoPlanRetrievalSCHParams.ALLOC_PIECE_QTY, line.getValue(KEY_LST_ALLOC_PIECE_QTY));
                lineparam.set(AsNoPlanRetrievalSCHParams.ITEM_CODE, txt_LItemCode.getValue());
                lineparam.set(AsNoPlanRetrievalSCHParams.L_ISSUE_REPORT, chk_LIssueReport.getValue());
                // DFKLOOK start
                lineparam.set(AsNoPlanRetrievalSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
                // DFKLOOK end
                inparamList.add(lineparam);
            }

            // DFKLOOK start add
            if (inparamList.size() <= 0)
            {
                // 6023195=出庫対象データがありません。
                message.setMsgResourceKey("6023195");
                return;
            }
            // DFKLOOK end add

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_NoPlanRetrieval.resetEditRow();
                _lcm_lst_NoPlanRetrieval.resetHighlight();
                _lcm_lst_NoPlanRetrieval.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_NoPlanRetrieval.size(); i++)
            {
                ListCellLine line = _lcm_lst_NoPlanRetrieval.get(i);
                lst_NoPlanRetrieval.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(_pdm_pul_RetrievalArea.getSelectedStringValue(), "");
                part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
                //DFKLOOK:ここから修正
                if (_pdm_pul_WorkPlace.getSelectedStringValue().equals(_pdm_pul_Station.getSelectedStringValue()))
                {
                    part11List.add(WmsParam.AUTO_SELECT_STATION, "");
                }
                else
                {
                    part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
                }
                //DFKLOOK:ここまで修正
                part11List.add(txt_ItemCode.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_STORAGE_DATE), line.getViewString(KEY_LST_STORAGE_TIME), "");
                part11List.add(line.getViewString(KEY_LST_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_ALLOC_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_ALLOC_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "0");

                if (lst_NoPlanRetrieval.getChecked(_lcm_lst_NoPlanRetrieval.getColumnIndex(KEY_LST_ALL)))
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }
                //DFKLOOK:ここから修正
                part11List.add(line.getViewString(KEY_LST_JAN), "");
                part11List.add(line.getViewString(KEY_LST_ITF), "");
                part11List.add(line.getViewString(KEY_LST_REASON), "");
                //DFKLOOK:ここまで修正

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

            // DFKLOOK start add
            // 出庫指示送信へRMIメッセージを使用して出庫要求を行います。
            SendRequestor req = new SendRequestor();
            req.retrieval();
            
            if (chk_LIssueReport.getChecked())
            {
                if (!startPrint(locale, ui, inparams[0].getString(AsNoPlanRetrievalSCHParams.SETTING_UKEY)))
                {
                    // メッセージの再セットのみ続きの処理を行う
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6007042));
                }
            }
            // DFKLOOK end
            
            // reset editing row.
            _lcm_lst_NoPlanRetrieval.resetEditRow();
            _lcm_lst_NoPlanRetrieval.resetHighlight();

            // set input parameters.
            AsNoPlanRetrievalSCHParams inparam = new AsNoPlanRetrievalSCHParams();
            inparam.set(AsNoPlanRetrievalSCHParams.FROM_LOCATION, viewState.getObject(ViewStateKeys.FROM_LOCATION));
            inparam.set(AsNoPlanRetrievalSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(AsNoPlanRetrievalSCHParams.LOT_NO, viewState.getObject(ViewStateKeys.LOT_NO));
            inparam.set(AsNoPlanRetrievalSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.RETRIEVAL_AREA));
            inparam.set(AsNoPlanRetrievalSCHParams.STATION, viewState.getObject(ViewStateKeys.STATION));
            inparam.set(AsNoPlanRetrievalSCHParams.TO_LOCATION, viewState.getObject(ViewStateKeys.TO_LOCATION));
            inparam.set(AsNoPlanRetrievalSCHParams.WORK_PLACE, viewState.getObject(ViewStateKeys.WORK_PLACE));
            inparam.set(AsNoPlanRetrievalSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_NoPlanRetrieval.clear();
            
            // DFKLOOK start add
            if (outparams.size() == 0)
            {
                btn_Set.setEnabled(false);
                btn_ClearListInput.setEnabled(false);
                btn_AllCheck.setEnabled(false);
                btn_AllCheckClear.setEnabled(false);
                btn_AllClear.setEnabled(false);
                chk_LIssueReport.setEnabled(false);
                return;
            }
            
            PulldownController pc = new PulldownController(conn, ui.getTerminalNumber(), locale, getClass());
            PullDownItem[] pul = pc.getReasonPulldownItem();

            int i = 1;
            // DFKLOOK end
            
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_NoPlanRetrieval.getNewLine();
                line.setValue(KEY_LST_HDN_STOCK_ID, outparam.get(AsNoPlanRetrievalSCHParams.STOCK_ID));
                line.setValue(KEY_LST_HDN_LOCATION, outparam.get(AsNoPlanRetrievalSCHParams.LOCATION_NO));
                line.setValue(KEY_LST_HDN_LOT, outparam.get(AsNoPlanRetrievalSCHParams.LOT_NO));
                line.setValue(KEY_LST_HDN_PALLET_ID, outparam.get(AsNoPlanRetrievalSCHParams.PALLET_ID));
                line.setValue(KEY_LST_LOT_NO, outparam.get(AsNoPlanRetrievalSCHParams.LOT_NO));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(AsNoPlanRetrievalSCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(AsNoPlanRetrievalSCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_AREA_NO, outparam.get(AsNoPlanRetrievalSCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(AsNoPlanRetrievalSCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsNoPlanRetrievalSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_ALLOC_CASE_QTY, outparam.get(AsNoPlanRetrievalSCHParams.ALLOC_CASE_QTY));
                line.setValue(KEY_LST_ALLOC_PIECE_QTY, outparam.get(AsNoPlanRetrievalSCHParams.ALLOC_PIECE_QTY));
                line.setValue(KEY_LST_JAN, outparam.get(AsNoPlanRetrievalSCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(AsNoPlanRetrievalSCHParams.ITF));
                // DFKLOOK del
                //txt_LItemName.setValue(outparam.get(AsNoPlanRetrievalSCHParams.SETTING_UKEY));
                lst_NoPlanRetrieval_SetLineToolTip(line);
                _lcm_lst_NoPlanRetrieval.add(line);

                // DFKLOOK start add
                lst_NoPlanRetrieval.setCurrentRow(i);
                int entQty = outparam.getInt(AsNoPlanRetrievalSCHParams.ENTERING_QTY);
                if (entQty == 0)
                {
                    lst_NoPlanRetrieval.setCellReadOnly(6, true);
                }
                lst_NoPlanRetrieval.setPullDownItems(9, Arrays.asList(pul));

                i++;
                // DFKLOOK end
            
            }

            // DFKLOOK start delete
            // clear.
            //btn_Set.setEnabled(false);
            //btn_ClearListInput.setEnabled(false);
            //btn_AllCheck.setEnabled(false);
            //btn_AllCheckClear.setEnabled(false);
            //btn_AllClear.setEnabled(false);
            //chk_LIssueReport.setEnabled(false);
            // DFKLOOK end

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

    // DFKLOOK add
    /**
     * AS/RS予定外出庫帳票を発行します。
     * 
     * @return true:印刷成功、false:印刷失敗
     * @throws ValidateException
     */
    private boolean startPrint(Locale locale, DfkUserInfo ui, String settingUkeys) throws ValidateException
    {
        Connection conn = null;
        AsNoPlanRetrievalDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsNoPlanRetrievalDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsNoPlanRetrievalDASCHParams inparam = new AsNoPlanRetrievalDASCHParams();
            inparam.set(AsNoPlanRetrievalDASCHParams.SETTING_UKEY, settingUkeys);

            // check count.
            int count = dasch.count(inparam);
            
            if (count == 0)
            {
                return false;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("ASRSNoPlanRetrievalList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ASRSNoPlanRetrievalListParams expparam = new ASRSNoPlanRetrievalListParams();
                expparam.set(ASRSNoPlanRetrievalListParams.DFK_DS_NO, outparam.get(AsNoPlanRetrievalDASCHParams.DFK_DS_NO));
                expparam.set(ASRSNoPlanRetrievalListParams.DFK_USER_ID, outparam.get(AsNoPlanRetrievalDASCHParams.DFK_USER_ID));
                expparam.set(ASRSNoPlanRetrievalListParams.DFK_USER_NAME, outparam.get(AsNoPlanRetrievalDASCHParams.DFK_USER_NAME));
                expparam.set(ASRSNoPlanRetrievalListParams.AREA_NO, outparam.get(AsNoPlanRetrievalDASCHParams.AREA_NO));
                expparam.set(ASRSNoPlanRetrievalListParams.AREA_NAME, outparam.get(AsNoPlanRetrievalDASCHParams.AREA_NAME));
                expparam.set(ASRSNoPlanRetrievalListParams.SYS_DAY, outparam.get(AsNoPlanRetrievalDASCHParams.SYS_DAY));
                expparam.set(ASRSNoPlanRetrievalListParams.SYS_TIME, outparam.get(AsNoPlanRetrievalDASCHParams.SYS_DAY));
                expparam.set(ASRSNoPlanRetrievalListParams.STATION_NO, outparam.get(AsNoPlanRetrievalDASCHParams.STATION_NO));
                expparam.set(ASRSNoPlanRetrievalListParams.STATION_NAME, outparam.get(AsNoPlanRetrievalDASCHParams.STATION_NAME));
                expparam.set(ASRSNoPlanRetrievalListParams.JOB_NO, outparam.get(AsNoPlanRetrievalDASCHParams.WORK_NO));
                expparam.set(ASRSNoPlanRetrievalListParams.RETRIEVAL_LOCATION_NO, outparam.get(AsNoPlanRetrievalDASCHParams.LOCATION_NO));
                expparam.set(ASRSNoPlanRetrievalListParams.ITEM_CODE, outparam.get(AsNoPlanRetrievalDASCHParams.ITEM_CODE));
                expparam.set(ASRSNoPlanRetrievalListParams.ITEM_NAME, outparam.get(AsNoPlanRetrievalDASCHParams.ITEM_NAME));
                expparam.set(ASRSNoPlanRetrievalListParams.LOT_NO, outparam.get(AsNoPlanRetrievalDASCHParams.LOT_NO));
                expparam.set(ASRSNoPlanRetrievalListParams.ENTERING_QTY, outparam.get(AsNoPlanRetrievalDASCHParams.ENTERING_QTY));
                expparam.set(ASRSNoPlanRetrievalListParams.RETRIEVAL_CASE_QTY, outparam.get(AsNoPlanRetrievalDASCHParams.CASE_QTY));
                expparam.set(ASRSNoPlanRetrievalListParams.RETRIEVAL_PIECE_QTY, outparam.get(AsNoPlanRetrievalDASCHParams.PIECE_QTY));
                expparam.set(ASRSNoPlanRetrievalListParams.JAN, outparam.get(AsNoPlanRetrievalDASCHParams.JAN));
                expparam.set(ASRSNoPlanRetrievalListParams.ITF, outparam.get(AsNoPlanRetrievalDASCHParams.ITF));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
                //message.setMsgResourceKey("6001010");
                return true;
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                //message.setMsgResourceKey("6007034");
                return false;
            }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            ExceptionHandler.getDisplayMessage(ex, this);
            return false;
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
    // DFKLOOK end

    /**
     *
     * @throws Exception
     */
    private void btn_ClearListInput_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_NoPlanRetrieval.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_NoPlanRetrieval.get(i);
            lst_NoPlanRetrieval.setCurrentRow(i);
            clearLine.setValue(KEY_LST_PLAN_PIECE_QTY, null);
            clearLine.setValue(KEY_LST_PLAN_CASE_QTY, null);
            clearLine.setValue(KEY_LST_ALL, Boolean.FALSE);
            lst_NoPlanRetrieval_SetLineToolTip(clearLine);
            _lcm_lst_NoPlanRetrieval.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_NoPlanRetrieval.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_NoPlanRetrieval.get(i);
            lst_NoPlanRetrieval.setCurrentRow(i);
            clearLine.setValue(KEY_LST_ALL, Boolean.TRUE);
            lst_NoPlanRetrieval_SetLineToolTip(clearLine);
            _lcm_lst_NoPlanRetrieval.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_NoPlanRetrieval.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_NoPlanRetrieval.get(i);
            lst_NoPlanRetrieval.setCurrentRow(i);
            clearLine.setValue(KEY_LST_ALL, Boolean.FALSE);
            lst_NoPlanRetrieval_SetLineToolTip(clearLine);
            _lcm_lst_NoPlanRetrieval.set(i, clearLine);
        }

    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_NoPlanRetrieval.clear();
        btn_Set.setEnabled(false);
        btn_ClearListInput.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_LIssueReport.setEnabled(false);
        txt_LItemCode.setValue(null);
        txt_LItemName.setValue(null);

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
