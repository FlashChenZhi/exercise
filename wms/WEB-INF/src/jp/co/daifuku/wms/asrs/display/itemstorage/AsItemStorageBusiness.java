// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.asrs.display.itemstorage;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.LinkedPullDownItem;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.AsItemStorageDASCH;
import jp.co.daifuku.wms.asrs.dasch.AsItemStorageDASCHParams;
import jp.co.daifuku.wms.asrs.exporter.ASRSStorageListParams;
import jp.co.daifuku.wms.asrs.listbox.storageplan.LstAsStoragePlanParams;
import jp.co.daifuku.wms.asrs.schedule.AsItemStorageSCH;
import jp.co.daifuku.wms.asrs.schedule.AsItemStorageSCHParams;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.SoftZoneType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsHardZonePullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsSoftZonePullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * AS/RS 入庫開始の画面処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:06:14 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class AsItemStorageBusiness
        extends AsItemStorage
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ASRSStorageSet(HIDDEN_AREA_NO) */
    private static final ListCellKey KEY_HIDDEN_AREA_NO = new ListCellKey("HIDDEN_AREA_NO", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(HIDDEN_LOCATION) */
    private static final ListCellKey KEY_HIDDEN_LOCATION = new ListCellKey("HIDDEN_LOCATION", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(HIDDEN_PLAN_LOT_NO) */
    private static final ListCellKey KEY_HIDDEN_PLAN_LOT_NO = new ListCellKey("HIDDEN_PLAN_LOT_NO", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(HIDDEN_JAN_CODE) */
    private static final ListCellKey KEY_HIDDEN_JAN_CODE = new ListCellKey("HIDDEN_JAN_CODE", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(HIDDEN_ITF) */
    private static final ListCellKey KEY_HIDDEN_ITF = new ListCellKey("HIDDEN_ITF", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(HIDDEN_DISP_LOCATION) */
    private static final ListCellKey KEY_HIDDEN_DISP_LOCATION = new ListCellKey("HIDDEN_DISP_LOCATION", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(HIDDEN_SOFT_ZONE_NAME) */
    private static final ListCellKey KEY_HIDDEN_SOFT_ZONE_NAME = new ListCellKey("HIDDEN_SOFT_ZONE_NAME", new StringCellColumn(), false, false);

    /** lst_ASRSStorageSet(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_ASRSStorageSet(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_ASRSStorageSet(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY = new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_ASRSStorageSet(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ASRSStorageSet(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ASRSStorageSet(LST_PLAN_LOT_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOT_NO = new ListCellKey("LST_PLAN_LOT_NO", new StringCellColumn(), true, false);

    /** lst_ASRSStorageSet(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0, 0), true, false);

    /** lst_ASRSStorageSet(LST_CASE_QTY) */
    private static final ListCellKey KEY_LST_CASE_QTY = new ListCellKey("LST_CASE_QTY", new NumberCellColumn(0, 0), true, false);

    /** lst_ASRSStorageSet(LST_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PIECE_QTY = new ListCellKey("LST_PIECE_QTY", new NumberCellColumn(0, 0), true, false);

    /** lst_ASRSStorageSet keys */
    private static final ListCellKey[] LST_ASRSSTORAGESET_KEYS = {
        KEY_HIDDEN_AREA_NO,
        KEY_HIDDEN_LOCATION,
        KEY_HIDDEN_PLAN_LOT_NO,
        KEY_HIDDEN_JAN_CODE,
        KEY_HIDDEN_ITF,
        KEY_HIDDEN_DISP_LOCATION,
        KEY_HIDDEN_SOFT_ZONE_NAME,
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_PLAN_DAY,
        KEY_LST_ITEM_CODE,
        KEY_LST_PLAN_LOT_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_CASE_QTY,
        KEY_LST_ITEM_NAME,
        KEY_LST_PIECE_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** PullDownModel pul_Zone */
    private WmsHardZonePullDownModel _pdm_pul_Zone;

    /** PullDownModel pul_WorkPlace */
    private WmsWorkspacePullDownModel _pdm_pul_WorkPlace;

    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    /** PullDownModel pul_LSoftZone */
    private WmsSoftZonePullDownModel _pdm_pul_LSoftZone;

    /** ListCellModel lst_ASRSStorageSet */
    private ListCellModel _lcm_lst_ASRSStorageSet;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsItemStorageBusiness()
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
        if (eventSource.equals("btn_PSearchPlan_Click"))
        {
            // process call.
            btn_PSearchPlan_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
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
        if (eventSource.equals("btn_Input_Click"))
        {
            // process call.
            btn_Input_Click_Process(false);
        }
        // DFKLOOK end
        if (eventSource.startsWith("btn_StorageStart_Click"))
        {
            btn_StorageStart_Click_Process(eventSource);
        }       
        // DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PSearchPlan_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchPlan_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Input_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Input_Click_Process(true);
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
    public void btn_StorageStart_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        // process call.
        btn_StorageStart_Click_Process(null);
        // DFKLOOK end
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
    public void lst_ASRSStorageSet_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_ASRSStorageSet.getActiveCol();

        // choose process.
        if (_lcm_lst_ASRSStorageSet.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_ASRSStorageSet.getColumnIndex(KEY_LST_CANCEL) == activeCol)
        {
            // process call.
            lst_Cancel_Click_Process();
        }
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
    // DFKLOOK ここから修正
    // 残数取得用メソッド追加
    /**
     * ケース入数、残数の表示を行うメソッドです。 <BR>
     * <BR>
     * 概要:ケース入数、残数の表示を行います。 <BR>
     * @param outParam ケース入数
     * @throws Exception 全ての例外を報告します。 
     */
    protected void dispRemainders(Params outParam)
            throws Exception
    {
        if (null == outParam)
        {
            return;
        }
        int remainders = getRemainders(outParam);
        int enteringQty = outParam.getInt(AsItemStorageSCHParams.ENTERING_QTY);
        // ケース入数
        txt_InEnteringQty.setInt(enteringQty);
        // 残ケース数
        txt_InRestCaseQty.setInt(DisplayUtil.getCaseQty(remainders, enteringQty));
        // 残ピース数
        txt_InRestPieceQty.setInt(DisplayUtil.getPieceQty(remainders, enteringQty));
    }

    /**
     * 残数の取得を行うメソッドです。 <BR>
     * <BR>
     * 概要:作業情報の検索結果のキー項目に対する残数の取得を行います。 <BR>
     * @return 残数
     * @throws Exception 全ての例外を報告します。 
     */
    protected Params getDispData()
            throws Exception
    {
        Connection conn = null;
        AsItemStorageSCH schedule = null;
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        try
        {
            // コネクション取得
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            AsItemStorageSCHParams searchParam = new AsItemStorageSCHParams();

            // 荷主コード
            searchParam.set(AsItemStorageSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            // 入庫予定日
            searchParam.set(AsItemStorageSCHParams.STORAGE_PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
            // 商品コード
            searchParam.set(AsItemStorageSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            // 予定ロットNo.（常に検索条件とする）
            searchParam.set(AsItemStorageSCHParams.PLAN_LOT_NO, viewState.getObject(ViewStateKeys.PLAN_LOT_NO));
            // 予定エリアNo.
            searchParam.set(AsItemStorageSCHParams.PLAN_AREA_NO, viewState.getObject(ViewStateKeys.PLAN_AREA));
            // 予定棚No.
            searchParam.set(AsItemStorageSCHParams.LOCATION, viewState.getString(ViewStateKeys.PLAN_LOCATION));

            // Scheduleクラスのインスタンス生成
            schedule = new AsItemStorageSCH(conn, this.getClass(), locale, ui);
            // 検索処理実行
            List<Params> params = schedule.query(searchParam);

            // エラー判定
            if (null == params || params.size() == 0)
            {
                return null;
            }

            return params.get(0);
            
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return null;
        }
        finally
        {
            try
            {
                // コネクションクローズ
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException eS)
            {
                eS.printStackTrace();
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(eS, this));
            }
        }
    }

    /**
     * 残数の取得を行うメソッドです。 <BR>
     * <BR>
     * 概要:パラメータのキー項目に対する残数の取得を行います。 <BR>
     * @param outParam キー項目
     * @return 残数
     */
    protected int getRemainders(Params param)
    {
        int remainders = param.getInt(AsItemStorageSCHParams.PLAN_QTY);
        
        // 残数計算
        int lineNo = _lcm_lst_ASRSStorageSet.getEditRow();
        for (int plc = 1; plc < lst_ASRSStorageSet.getMaxRows(); plc++)
        {

            // exclusion unmodified row.
            ListCellLine line = _lcm_lst_ASRSStorageSet.get(plc);
            // 修正対象行は除く
            if (plc == lineNo)
            {
                continue;
            }

            // 行指定
            lst_ASRSStorageSet.setCurrentRow(plc);

            // キー項目が一致しない場合は、減算の対象外
            if (!line.getValue(KEY_LST_PLAN_DAY).equals(param.getDate(AsItemStorageSCHParams.STORAGE_PLAN_DAY))
                    || !line.getValue(KEY_LST_ITEM_CODE).equals(param.get(AsItemStorageSCHParams.ITEM_CODE))
                    || !line.getValue(KEY_HIDDEN_AREA_NO).equals(param.get(AsItemStorageSCHParams.PLAN_AREA_NO))
                    || !line.getValue(KEY_HIDDEN_LOCATION).equals(param.get(AsItemStorageSCHParams.LOCATION))
                    || !line.getValue(KEY_HIDDEN_PLAN_LOT_NO).equals(param.get(AsItemStorageSCHParams.PLAN_LOT_NO)))
            {
                continue;
            }
            
            remainders -= line.getNumberValue(KEY_LST_CASE_QTY).intValue()
                        * line.getNumberValue(KEY_LST_ENTERING_QTY).intValue() 
                        + line.getNumberValue(KEY_LST_PIECE_QTY).intValue();
        }
        if (lineNo >= 1)
        {
            lst_ASRSStorageSet.setCurrentRow(lineNo);
        }

        return remainders;

    }

    // DFKLOOK ここまで修正

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

        // initialize pul_Zone.
        _pdm_pul_Zone = new WmsHardZonePullDownModel(pul_Zone, locale, ui);
        _pdm_pul_Zone.setParent(_pdm_pul_Area);

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);
        _pdm_pul_WorkPlace.setParent(_pdm_pul_Area);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
        _pdm_pul_Station.setParent(_pdm_pul_WorkPlace);

        // initialize pul_LSoftZone.
        _pdm_pul_LSoftZone = new WmsSoftZonePullDownModel(pul_LSoftZone, locale, ui);
        _pdm_pul_LSoftZone.setParent(_pdm_pul_Area);

        // initialize lst_ASRSStorageSet.
        _lcm_lst_ASRSStorageSet = new ListCellModel(lst_ASRSStorageSet, LST_ASRSSTORAGESET_KEYS, locale);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_PLAN_DAY, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_PLAN_LOT_NO, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_CASE_QTY, true);
        _lcm_lst_ASRSStorageSet.setToolTipVisible(KEY_LST_PIECE_QTY, true);

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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.STORAGE, "", false);

            // load pul_Zone.
            _pdm_pul_Zone.init(conn);

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.STORAGE);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.STORAGE, Distribution.AUTO);

            // load pul_LSoftZone.
            _pdm_pul_LSoftZone.init(conn, SoftZoneType.AREA);

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
    private void lst_ASRSStorageSet_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        // DFKLOOK ここから修正
        line.addToolTip("LBL-W0379", KEY_HIDDEN_AREA_NO);
        line.addToolTip("LBL-W0381", KEY_HIDDEN_PLAN_LOT_NO);
        line.addToolTip("LBL-W0002", KEY_HIDDEN_JAN_CODE);
        line.addToolTip("LBL-W0017", KEY_HIDDEN_ITF);
        line.addToolTip("LBL-W0380", KEY_HIDDEN_DISP_LOCATION);
        line.addToolTip("LBL-W1364", KEY_HIDDEN_SOFT_ZONE_NAME);
        // DFKLOOK ここまで修正
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_StoragePlanDate);

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
        AsItemStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsItemStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsItemStorageSCHParams inparam = new AsItemStorageSCHParams();

            // SCH call.
            // DFKLOOK start
            inparam.set(AsItemStorageSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
            Params outParam = sch.initFind(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            viewState.setBoolean(ViewStateKeys.NEED_PUL_CHANGE, outParam.getBoolean(AsItemStorageSCHParams.NEED_PUL_CHANGE));
            // DFKLOOK end

            // clear.
            txt_InEnteringQty.setReadOnly(true);
            btn_StorageStart.setEnabled(false);
            btn_AllClear.setEnabled(false);
            chk_IssueReport.setChecked(true);
            chk_IssueReport.setEnabled(false);
            pul_LSoftZone.setEnabled(false);
            

            // DFKLOOK:ここから修正 
            if (outParam != null)
            {
                String printflg = outParam.getString(AsItemStorageSCHParams.PRINT_FLAG);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_IssueReport.setChecked(false);
                }
            }
            // DFKLOOK:ここまで修正

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
    private void btn_PSearchPlan_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstAsStoragePlanParams inparam = new LstAsStoragePlanParams();
        inparam.set(LstAsStoragePlanParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstAsStoragePlanParams.PLAN_DAY, txt_StoragePlanDate.getValue());
        inparam.set(LstAsStoragePlanParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstAsStoragePlanParams.PLAN_LOT_NO, txt_StoragePlanLotNo.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchPlan_Click");
        redirect("/asrs/listbox/storageplan/LstAsStoragePlan.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchPlan_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstAsStoragePlanParams outparam = new LstAsStoragePlanParams(dialogParams);
        txt_StoragePlanDate.setValue(outparam.get(LstAsStoragePlanParams.PLAN_DAY));
        txt_ItemCode.setValue(outparam.get(LstAsStoragePlanParams.ITEM_CODE));
        txt_StoragePlanLotNo.setValue(outparam.get(LstAsStoragePlanParams.PLAN_LOT_NO));
        // DFKLOOK ここから修正
        //txt_InEnteringQty.setValue(outparam.get(LstAsStoragePlanParams.ENTERING_QTY));
        //txt_StorageLotNo.setValue(outparam.get(LstAsStoragePlanParams.PLAN_LOT_NO));
        //txt_StorageCaseQty.setValue(outparam.get(LstAsStoragePlanParams.CASE_QTY));
        //txt_StoragePieceQty.setValue(outparam.get(LstAsStoragePlanParams.PIECE_QTY));
        // リストボックスから選択されたパラメータを取得
        // 入庫予定ロットNo.
        String planlot = (String)outparam.get(LstAsStoragePlanParams.PLAN_LOT_NO);
        // 入庫ケース数（残ケース数としても使用）
        String caseqty = String.valueOf(outparam.get(LstAsStoragePlanParams.CASE_QTY));
        // 入庫ピース数（残ピース数としても使用）-
        String pieceqty = String.valueOf(outparam.get(LstAsStoragePlanParams.PIECE_QTY));
        // ケース入数
        String enteringqty = String.valueOf(outparam.get(LstAsStoragePlanParams.ENTERING_QTY));

        Connection conn = null;
        try
        {
            // ケース入数、入庫ケース数、入庫ピース数(入庫予定検索)
            if (!StringUtil.isBlank(enteringqty) && !StringUtil.isBlank(caseqty) && !StringUtil.isBlank(pieceqty))
            {
                conn = ConnectionManager.getRequestConnection(this);
                // キー項目セット
                viewState.setObject(ViewStateKeys.ENTERING_QTY, outparam.get(LstAsStoragePlanParams.ENTERING_QTY));
                viewState.setObject(ViewStateKeys.CASE_QTY, outparam.get(LstAsStoragePlanParams.CASE_QTY));
                viewState.setObject(ViewStateKeys.PIECE_QTY, outparam.get(LstAsStoragePlanParams.PIECE_QTY));
                viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                viewState.setObject(ViewStateKeys.PLAN_DAY, outparam.get(LstAsStoragePlanParams.PLAN_DAY));
                viewState.setObject(ViewStateKeys.ITEM_CODE, outparam.get(LstAsStoragePlanParams.ITEM_CODE));
                viewState.setObject(ViewStateKeys.PLAN_AREA, outparam.get(LstAsStoragePlanParams.PLAN_AREA_NO));
                viewState.setObject(ViewStateKeys.PLAN_LOCATION, outparam.get(LstAsStoragePlanParams.PLAN_LOCATION_NO));
                viewState.setObject(ViewStateKeys.PLAN_LOT_NO, outparam.get(LstAsStoragePlanParams.PLAN_LOT_NO));
                // 残数表示
                dispRemainders(getDispData());;

                // オーバーフローチェック
                long allQty =
                        txt_InEnteringQty.getLong() * txt_InRestCaseQty.getLong() + txt_InRestPieceQty.getLong();
                // 残総数が在庫上限数以上の場合、在庫上限数を入庫数とする
                if (allQty > WmsParam.MAX_TOTAL_QTY)
                {
                    txt_StorageCaseQty.setInt(DisplayUtil.getCaseQty(WmsParam.MAX_TOTAL_QTY, txt_InEnteringQty.getInt()));
                    txt_StoragePieceQty.setInt(DisplayUtil.getPieceQty(WmsParam.MAX_TOTAL_QTY, txt_InEnteringQty.getInt()));
                }
                else
                {
                    txt_StorageCaseQty.setInt(txt_InRestCaseQty.getInt());
                    txt_StoragePieceQty.setInt(txt_InRestPieceQty.getInt());
                }


                // 入庫ロットNo.
                txt_StorageLotNo.setText(planlot);

                // 入庫ケース数にフォーカスセット
                setFocus(txt_StorageLotNo);
            }
        }
        catch (Exception ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException es)
            {
                message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(es, this));
            }
        }
        // DFKLOOK ここまで修正

    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Input_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_StoragePlanDate.validate(this, true);
        txt_ItemCode.validate(this, true);
        txt_StoragePlanLotNo.validate(this, false);
        txt_InEnteringQty.validate(this, false);
        txt_StorageLotNo.validate(this, false);
        txt_StorageCaseQty.validate(this, false);
        txt_StoragePieceQty.validate(this, false);
        pul_Area.validate(this, true);
        pul_Zone.validate(this, true);
        pul_Station.validate(this, true);
        pul_WorkPlace.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsItemStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsItemStorageSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK ここから修正
            AsItemStorageSCHParams searchParam = new AsItemStorageSCHParams();

            // 入庫日、商品、予定ロットがViewStateと同じ場合はViewStateより検索条件取得
            if (txt_StoragePlanDate.getDate().equals(viewState.getDate(ViewStateKeys.PLAN_DAY))
                    && (txt_ItemCode.getText().equals(viewState.getString(ViewStateKeys.ITEM_CODE))) 
                    && (txt_StoragePlanLotNo.getText().equals(viewState.getString(ViewStateKeys.PLAN_LOT_NO))))
            {
                // 荷主コード
                searchParam.set(AsItemStorageSCHParams.CONSIGNOR_CODE,
                        viewState.getString(ViewStateKeys.CONSIGNOR_CODE));
                // 入庫予定日                
                searchParam.set(AsItemStorageSCHParams.STORAGE_PLAN_DAY, viewState.getDate(ViewStateKeys.PLAN_DAY));
                // 商品コード
                searchParam.set(AsItemStorageSCHParams.ITEM_CODE, viewState.getString(ViewStateKeys.ITEM_CODE));
                // 予定ロットNo.（常に検索条件とする）
                searchParam.set(AsItemStorageSCHParams.PLAN_LOT_NO, viewState.getString(ViewStateKeys.PLAN_LOT_NO));
                // 予定エリアNo.
                searchParam.set(AsItemStorageSCHParams.PLAN_AREA_NO, viewState.getString(ViewStateKeys.PLAN_AREA));
                // 予定棚No.
                searchParam.set(AsItemStorageSCHParams.LOCATION, viewState.getString(ViewStateKeys.PLAN_LOCATION));
            }
            else
            {
                // 荷主コード
                searchParam.set(AsItemStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                // 入庫予定日
                searchParam.set(AsItemStorageSCHParams.STORAGE_PLAN_DAY, txt_StoragePlanDate.getDate());
                // 商品コード
                searchParam.set(AsItemStorageSCHParams.ITEM_CODE, txt_ItemCode.getText());
                // 予定ロットNo.（ブランクの場合は検索条件としない）
                if (!StringUtil.isBlank(txt_StoragePlanLotNo.getText()))
                {
                    searchParam.set(AsItemStorageSCHParams.PLAN_LOT_NO, txt_StoragePlanLotNo.getText());
                }
                else
                {
                    searchParam.set(AsItemStorageSCHParams.PLAN_LOT_NO, null);
                }
                // 予定エリアNo.
                searchParam.set(AsItemStorageSCHParams.PLAN_AREA_NO, null);
                // 予定棚No.
                searchParam.set(AsItemStorageSCHParams.LOCATION, null);

                // キー項目クリア
                viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, "");
                viewState.setObject(ViewStateKeys.PLAN_DAY, null);
                viewState.setObject(ViewStateKeys.ITEM_CODE, "");
                viewState.setObject(ViewStateKeys.PLAN_AREA, "");
                viewState.setObject(ViewStateKeys.PLAN_LOCATION, "");
                viewState.setObject(ViewStateKeys.PLAN_LOT_NO, "");
            }

            // 検索処理実行
            List<Params> outParams = sch.query(searchParam);

            // エラー判定
            if (null == outParams || outParams.size() == 0)
            {
                // メッセージを設定
                message.setMsgResourceKey(sch.getMessage());
                return;
            }
            Params outParam = outParams.get(0);
            
            // キー項目セット
            viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, outParam.get(AsItemStorageSCHParams.CONSIGNOR_CODE));
            viewState.setObject(ViewStateKeys.PLAN_DAY, outParam.get(AsItemStorageSCHParams.STORAGE_PLAN_DAY));
            viewState.setObject(ViewStateKeys.ITEM_CODE, outParam.get(AsItemStorageSCHParams.ITEM_CODE));
            viewState.setObject(ViewStateKeys.PLAN_AREA, outParam.get(AsItemStorageSCHParams.PLAN_AREA_NO));
            viewState.setObject(ViewStateKeys.PLAN_LOCATION, outParam.get(AsItemStorageSCHParams.LOCATION));
            viewState.setObject(ViewStateKeys.PLAN_LOT_NO, outParam.get(AsItemStorageSCHParams.PLAN_LOT_NO));

            AsItemStorageSCHParams inparam = new AsItemStorageSCHParams();
            inparam.set(AsItemStorageSCHParams.STORAGE_PLAN_DAY, txt_StoragePlanDate.getDate());
            inparam.set(AsItemStorageSCHParams.ITEM_CODE, txt_ItemCode.getText());
            inparam.set(AsItemStorageSCHParams.PLAN_LOT_NO, outParam.get(AsItemStorageSCHParams.PLAN_LOT_NO));
            inparam.set(AsItemStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(AsItemStorageSCHParams.PLAN_AREA_NO, outParam.get(AsItemStorageSCHParams.PLAN_AREA_NO));
            inparam.set(AsItemStorageSCHParams.LOCATION, outParam.get(AsItemStorageSCHParams.LOCATION));
            inparam.set(AsItemStorageSCHParams.AREA_NO, pul_Area.getSelectedValue());
            inparam.set(AsItemStorageSCHParams.ZONE_NO, pul_Zone.getSelectedValue());
            // ステーション
            if (pul_Station.getSelectedValue().equals(WmsParam.AUTO_SELECT_STATION))
            {
                // ステーションに「自動振分け」が指定された場合は、作業場をセットする。
                inparam.set(AsItemStorageSCHParams.STATION_NO, pul_WorkPlace.getSelectedValue());
            }
            else
            {
                inparam.set(AsItemStorageSCHParams.STATION_NO, pul_Station.getSelectedValue());
            }
            // 実績ロットNo.
            inparam.set(AsItemStorageSCHParams.LOT_NO, txt_StorageLotNo.getText());
            // 入庫ケース数
            inparam.set(AsItemStorageSCHParams.STORAGE_CASE_QTY, txt_StorageCaseQty.getInt());
            // 入庫ピース数
            inparam.set(AsItemStorageSCHParams.STORAGE_PIECE_QTY, txt_StoragePieceQty.getInt());
            // ケース入数
            inparam.set(AsItemStorageSCHParams.ENTERING_QTY, outParam.get(AsItemStorageSCHParams.ENTERING_QTY));
            // 予定数(残数)
            inparam.set(AsItemStorageSCHParams.PLAN_QTY, getRemainders(outParam));

            // DFKLOOK ここまで修正

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ASRSStorageSet.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_ASRSStorageSet.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_ASRSStorageSet.get(i);
                AsItemStorageSCHParams lineparam = new AsItemStorageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                // DFKLOOK start
                //lineparam.set(AsItemStorageSCHParams.STORAGE_PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
                //lineparam.set(AsItemStorageSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
                //lineparam.set(AsItemStorageSCHParams.PLAN_LOT_NO, viewState.getObject(ViewStateKeys.PLAN_LOT_NO));
                //lineparam.set(AsItemStorageSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
                //lineparam.set(AsItemStorageSCHParams.PLAN_AREA_NO, viewState.getObject(ViewStateKeys.PLAN_AREA));
                //lineparam.set(AsItemStorageSCHParams.LOCATION, viewState.getObject(ViewStateKeys.PLAN_LOCATION));
                lineparam.set(AsItemStorageSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                // DFKLOOK end
                lineparam.set(AsItemStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(AsItemStorageSCHParams.JOB_TYPE, "");
                lineparam.set(AsItemStorageSCHParams.STORAGE_PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                lineparam.set(AsItemStorageSCHParams.PLAN_AREA_NO, line.getValue(KEY_HIDDEN_AREA_NO));
                lineparam.set(AsItemStorageSCHParams.LOCATION, line.getValue(KEY_HIDDEN_LOCATION));
                lineparam.set(AsItemStorageSCHParams.PLAN_LOT_NO, line.getValue(KEY_HIDDEN_PLAN_LOT_NO));
                lineparam.set(AsItemStorageSCHParams.LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
                lineparam.set(AsItemStorageSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(AsItemStorageSCHParams.STORAGE_CASE_QTY, line.getValue(KEY_LST_CASE_QTY));
                lineparam.set(AsItemStorageSCHParams.STORAGE_PIECE_QTY, line.getValue(KEY_LST_PIECE_QTY));
                lineparam.set(AsItemStorageSCHParams.STORAGE_QTY, 0);
                lineparam.set(AsItemStorageSCHParams.PLAN_QTY, 0);
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if (confirm && !sch.check(inparam, inparams))
            {
                if (StringUtil.isBlank(sch.getDispMessage()))
                {
                    // show message.
                    message.setMsgResourceKey(sch.getMessage());
                    return;
                }
                else
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Input_Click");
                    return;
                }
            }

            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK start
            if(viewState.getBoolean(ViewStateKeys.NEED_PUL_CHANGE))
            {
                // 現状のプルダウンを保存する
                List<LinkedPullDownItem> save = new ArrayList<LinkedPullDownItem>();
                List<LinkedPullDownItem> items = pul_LSoftZone.getItems();
                for (int i = 0; i < items.size(); i++)
                {
                    save.add(items.get(i));
                }
                
            	// 新規入力の時、またはためうちの1件目を修正した時、true
	            if (_lcm_lst_ASRSStorageSet.getListCell().getMaxRows() == 1
	            		|| _lcm_lst_ASRSStorageSet.getEditRow() == 1)
	            {
	                // 空パレ以外の場合ソフトゾーンを商品指定で検索
	                if (!WmsParam.EMPTYPB_ITEMCODE.equals(txt_ItemCode.getText()))
	                {
                        List<String> item_list = ListCellItems();
                        item_list.add(0, txt_ItemCode.getText());
	                    _pdm_pul_LSoftZone.clear();
	                    _pdm_pul_LSoftZone.init(conn, SoftZoneType.AREA, item_list);
	                }
	                // 空パレの場合、全ソフトゾーンを検索
	                else
	                {
	                    _pdm_pul_LSoftZone.clear();
	                    _pdm_pul_LSoftZone.init(conn, SoftZoneType.AREA);
	                }
	                
	                if (pul_LSoftZone.getItems().isEmpty())
	                {
                        // プルダウンを元に戻す
                        for (int i = 0; i < save.size(); i++)
                        {
                            pul_LSoftZone.addItem(save.get(i));
                        }
	                    // 6023112=入庫可能な空棚がありません。
	                    message.setMsgResourceKey("6023112");
	                    return;
	                }
	            }
                // 2件以上のとき
                else if (_lcm_lst_ASRSStorageSet.getListCell().getMaxRows() > 1)
                {
                    // プルダウンの再検索
                    List<String> item_list = ListCellItems();
                    // 入力エリアの商品を追加
                    item_list.add(txt_ItemCode.getText());
                    
                    _pdm_pul_LSoftZone.clear();
                    _pdm_pul_LSoftZone.init(conn, SoftZoneType.AREA, item_list);
                    
                    if (pul_LSoftZone.getItems().isEmpty())
                    {
                        // プルダウンを元に戻す
                        for (int i = 0; i < save.size(); i++)
                        {
                            pul_LSoftZone.addItem(save.get(i));
                        }
                        // 6023262=共通ソフトゾーンが存在しないため、混載できません。
                        message.setMsgResourceKey("6023262");
                        return;
                    }
                }
            }
            // DFKLOOK end

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                int editRow = _lcm_lst_ASRSStorageSet.getEditRow();
                Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
                ListCellLine line = newline ? _lcm_lst_ASRSStorageSet.getNewLine()
                                            : _lcm_lst_ASRSStorageSet.get(editRow);
                line.setValue(KEY_LST_PLAN_DAY, txt_StoragePlanDate.getValue());
                line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsItemStorageSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_CASE_QTY, txt_StorageCaseQty.getValue());
                line.setValue(KEY_LST_PLAN_LOT_NO, txt_StorageLotNo.getValue());
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(AsItemStorageSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PIECE_QTY, txt_StoragePieceQty.getValue());
                line.setValue(KEY_HIDDEN_AREA_NO, outparam.get(AsItemStorageSCHParams.PLAN_AREA_NO));
                line.setValue(KEY_HIDDEN_LOCATION, outparam.get(AsItemStorageSCHParams.LOCATION));
                line.setValue(KEY_HIDDEN_PLAN_LOT_NO, outparam.get(AsItemStorageSCHParams.PLAN_LOT_NO));
                line.setValue(KEY_HIDDEN_JAN_CODE, outparam.get(AsItemStorageSCHParams.JAN_CODE));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(AsItemStorageSCHParams.ITF));
                line.setValue(KEY_HIDDEN_DISP_LOCATION, outparam.get(AsItemStorageSCHParams.DISP_LOCATION));
                // DFKLOOK start
                //_pdm_pul_LSoftZone.setSelectedValue(txt_ItemCode.getValue());
                //_pdm_pul_LSoftZone.setSelectedValue(outparam.get(AsItemStorageSCHParams.NEET_PUL_CHANGE));
                // DFKLOOK end
                line.setValue(KEY_HIDDEN_SOFT_ZONE_NAME, outparam.get(AsItemStorageSCHParams.SOFT_ZONE_NAME));

                // add new row or update editing row.
                lst_ASRSStorageSet_SetLineToolTip(line);
                if (newline)
                {
                    _lcm_lst_ASRSStorageSet.add(line, true);
                }
                else
                {
                    _lcm_lst_ASRSStorageSet.set(editRow, line);
                }
                break;
            }

            // reset editing row.
            _lcm_lst_ASRSStorageSet.resetEditRow();
            _lcm_lst_ASRSStorageSet.resetHighlight();

            // clear.
            btn_StorageStart.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_IssueReport.setEnabled(true);
            pul_LSoftZone.setEnabled(true);

            // DFKLOOK ここから修正
            // 残数表示
            dispRemainders(getDispData());;
            // DFKLOOK ここまで修正

            // set focus.
            setFocus(txt_StorageLotNo);

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
        txt_StoragePlanDate.setValue(null);
        txt_ItemCode.setValue(null);
        txt_StoragePlanLotNo.setValue(null);
        txt_InEnteringQty.setValue(null);
        txt_InRestCaseQty.setValue(null);
        txt_InRestPieceQty.setValue(null);
        txt_StorageLotNo.setValue(null);
        txt_StorageCaseQty.setValue(null);
        txt_StoragePieceQty.setValue(null);

        // DFKLOOK ここから修正
        // キー項目セット
        viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, null);
        viewState.setObject(ViewStateKeys.PLAN_DAY, null);
        viewState.setObject(ViewStateKeys.ITEM_CODE, null);
        viewState.setObject(ViewStateKeys.PLAN_AREA, null);
        viewState.setObject(ViewStateKeys.PLAN_LOCATION, null);
        viewState.setObject(ViewStateKeys.PLAN_LOT_NO, null);
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK start
    private void btn_StorageStart_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK end
    {
    	pul_Area.validate(this, true);
    	pul_Station.validate(this, true);
    	pul_WorkPlace.validate(this, true);
    	pul_Zone.validate(this, true);
        
        // DFKLOOK start  	
        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message. 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_StorageStart_Click");
            return;         
        }
        // DFKLOOK end
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsItemStorageSCH sch = null;
        try
        {
            // DFKLOOK start
            if (StringUtil.isBlank(pul_LSoftZone.getSelectedItem()))
            {
                // 6023112=入庫可能な空棚がありません。
                message.setMsgResourceKey("6023112");
                return;
            }
            // DFKLOOK end
            
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsItemStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ASRSStorageSet.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ASRSStorageSet.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsItemStorageSCHParams lineparam = new AsItemStorageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(AsItemStorageSCHParams.PRINT_FLAG, chk_IssueReport.getValue());
                lineparam.set(AsItemStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                // DFKLOOK ここから修正
                lineparam.set(AsItemStorageSCHParams.JOB_TYPE, SystemDefine.JOB_TYPE_STORAGE);
                // DFKLOOK ここまで修正
                lineparam.set(AsItemStorageSCHParams.STORAGE_PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
                lineparam.set(AsItemStorageSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(AsItemStorageSCHParams.PLAN_AREA_NO, line.getValue(KEY_HIDDEN_AREA_NO));
                lineparam.set(AsItemStorageSCHParams.LOCATION, line.getValue(KEY_HIDDEN_LOCATION));
                lineparam.set(AsItemStorageSCHParams.PLAN_LOT_NO, line.getValue(KEY_HIDDEN_PLAN_LOT_NO));
                lineparam.set(AsItemStorageSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
                lineparam.set(AsItemStorageSCHParams.ZONE_NO, _pdm_pul_Zone.getSelectedValue());
                lineparam.set(AsItemStorageSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
                lineparam.set(AsItemStorageSCHParams.LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
                lineparam.set(AsItemStorageSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(AsItemStorageSCHParams.STORAGE_CASE_QTY, line.getValue(KEY_LST_CASE_QTY));
                lineparam.set(AsItemStorageSCHParams.STORAGE_PIECE_QTY, line.getValue(KEY_LST_PIECE_QTY));
                lineparam.set(AsItemStorageSCHParams.STORAGE_QTY, 0);
                lineparam.set(AsItemStorageSCHParams.SOFT_ZONE_ID, _pdm_pul_LSoftZone.getSelectedValue());
                lineparam.set(AsItemStorageSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
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

                // reset editing row or highlighting error row.
                _lcm_lst_ASRSStorageSet.resetEditRow();
                _lcm_lst_ASRSStorageSet.resetHighlight();
                _lcm_lst_ASRSStorageSet.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ASRSStorageSet.size(); i++)
            {
                ListCellLine line = _lcm_lst_ASRSStorageSet.get(i);
                lst_ASRSStorageSet.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
                part11List.add(_pdm_pul_Zone.getSelectedStringValue(), "");
                part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");

                //DFKLOOK:ここから修正
                // 作業場とステーションが同一の場合は[自動振分け]と判断
                if (_pdm_pul_WorkPlace.getSelectedStringValue().equals(_pdm_pul_Station.getSelectedStringValue()))
                {
                    part11List.add(WmsParam.AUTO_SELECT_STATION, "");
                }
                else
                {
                    part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
                }
                //DFKLOOK:ここまで修正

                part11List.add(line.getViewString(KEY_LST_PLAN_DAY), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PIECE_QTY), "0");
                part11List.add(line.getViewString(KEY_HIDDEN_JAN_CODE), "");
                part11List.add(line.getViewString(KEY_HIDDEN_ITF), "");
                part11List.add(line.getViewString(KEY_HIDDEN_AREA_NO), "");
                //DFKLOOK:ここから修正
                String style =
                        SuperLocationHolder.getInstance().getLocationFormat(line.getViewString(KEY_HIDDEN_AREA_NO));
                part11List.add(WmsFormatter.toDispLocation(line.getViewString(KEY_HIDDEN_LOCATION), style), "");
                //DFKLOOK:ここまで修正
                part11List.add(line.getViewString(KEY_HIDDEN_PLAN_LOT_NO), "");

                if (chk_IssueReport.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }
                part11List.add(_pdm_pul_LSoftZone.getSelectedStringValue(), "");

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING),
                        part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK ここから修正
            if (chk_IssueReport.getChecked())
            {
                if (!startPrint(locale, ui, inparams[0].getString(AsItemStorageSCHParams.SETTING_UKEYS)))
                {
                    // メッセージの再セットのみ続きの処理を行う
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6007042));
                }
            }
            // DFKLOOK ここまで修正

            // reset editing row.
            _lcm_lst_ASRSStorageSet.resetEditRow();
            _lcm_lst_ASRSStorageSet.resetHighlight();

            // clear.
            _lcm_lst_ASRSStorageSet.clear();
            btn_StorageStart.setEnabled(false);
            btn_AllClear.setEnabled(false);
            chk_IssueReport.setEnabled(false);
            pul_LSoftZone.setEnabled(false);

            // DFKLOOK ここから修正
            // 残数表示
            dispRemainders(getDispData());;
            // DFKLOOK ここまで修正

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

    // DFKLOOK ここから
    /**
     * AS/RS予定外入庫作業リストを発行します
     * 
     * @param locale
     * @param ui
     * @param settingUkeys
     */
    private boolean startPrint(Locale locale, DfkUserInfo ui, Object settingUkeys)
            throws Exception
    {
        Connection conn = null;
        AsItemStorageDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsItemStorageDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsItemStorageDASCHParams inparam = new AsItemStorageDASCHParams();
            inparam.set(AsItemStorageDASCHParams.SETTING_UNITKEY, settingUkeys);
            inparam.set(AsItemStorageDASCHParams.SEARCH_DATE, "");
            inparam.set(AsItemStorageDASCHParams.TO_SEARCH_DATE, "");
            inparam.set(AsItemStorageDASCHParams.AREA_NO, "");
            inparam.set(AsItemStorageDASCHParams.STATION_NO, "");
            inparam.set(AsItemStorageDASCHParams.WORK_PLACE, "");

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
            exporter = factory.newPrinterExporter("ASRSStorageList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ASRSStorageListParams expparam = new ASRSStorageListParams();
                expparam.set(ASRSStorageListParams.DFK_DS_NO, outparam.get(AsItemStorageDASCHParams.DFK_DS_NO));
                expparam.set(ASRSStorageListParams.DFK_USER_ID, outparam.get(AsItemStorageDASCHParams.DFK_USER_ID));
                expparam.set(ASRSStorageListParams.DFK_USER_NAME, outparam.get(AsItemStorageDASCHParams.DFK_USER_NAME));
                expparam.set(ASRSStorageListParams.AREA_NO, outparam.get(AsItemStorageDASCHParams.AREA_NO));
                expparam.set(ASRSStorageListParams.AREA_NAME, outparam.get(AsItemStorageDASCHParams.AREA_NAME));
                expparam.set(ASRSStorageListParams.SYS_DAY, outparam.get(AsItemStorageDASCHParams.SYS_DAY));
                expparam.set(ASRSStorageListParams.SYS_TIME, outparam.get(AsItemStorageDASCHParams.SYS_TIME));
                expparam.set(ASRSStorageListParams.STATION_NO, outparam.get(AsItemStorageDASCHParams.STATION_NO));
                expparam.set(ASRSStorageListParams.STATION_NAME, outparam.get(AsItemStorageDASCHParams.STATION_NAME));
                expparam.set(ASRSStorageListParams.JOB_NO, outparam.get(AsItemStorageDASCHParams.JOB_NO));
                expparam.set(ASRSStorageListParams.STORAGE_LOCATION_NO, outparam.get(AsItemStorageDASCHParams.LOCATION_NO));
                expparam.set(ASRSStorageListParams.ITEM_CODE, outparam.get(AsItemStorageDASCHParams.ITEM_CODE));
                expparam.set(ASRSStorageListParams.ITEM_NAME, outparam.get(AsItemStorageDASCHParams.ITEM_NAME));
                expparam.set(ASRSStorageListParams.LOT_NO, outparam.get(AsItemStorageDASCHParams.LOT_NO));
                expparam.set(ASRSStorageListParams.STORAGE_PLAN_DAY, outparam.get(AsItemStorageDASCHParams.PLAN_DAY));
                expparam.set(ASRSStorageListParams.ENTERING_QTY, outparam.get(AsItemStorageDASCHParams.ENTERING_QTY));
                expparam.set(ASRSStorageListParams.STORAGE_CASE_QTY, outparam.get(AsItemStorageDASCHParams.CASE_QTY));
                expparam.set(ASRSStorageListParams.STORAGE_PIECE_QTY, outparam.get(AsItemStorageDASCHParams.PIECE_QTY));
                expparam.set(ASRSStorageListParams.JAN, outparam.get(AsItemStorageDASCHParams.JAN));
                expparam.set(ASRSStorageListParams.ITF, outparam.get(AsItemStorageDASCHParams.ITF));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            exporter.print();
            return true;

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

    // DFKLOOK ここまで

    /**
     *
     * @throws Exception
     */
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_ASRSStorageSet.clear();
        btn_StorageStart.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_IssueReport.setEnabled(false);
        pul_LSoftZone.setEnabled(false);

        // DFKLOOK ここから修正
        // 残数表示
        dispRemainders(getDispData());;
        // DFKLOOK ここまで修正

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Modify_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_ASRSStorageSet.getActiveRow();
        lst_ASRSStorageSet.setCurrentRow(row);
        ListCellLine line = _lcm_lst_ASRSStorageSet.get(row);

        // output display.
        txt_StoragePlanDate.setValue(line.getValue(KEY_LST_PLAN_DAY));
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_StoragePlanLotNo.setValue(line.getValue(KEY_HIDDEN_PLAN_LOT_NO));
        txt_InEnteringQty.setValue(line.getValue(KEY_LST_ENTERING_QTY));
        txt_StorageLotNo.setValue(line.getValue(KEY_LST_PLAN_LOT_NO));
        txt_StorageCaseQty.setValue(line.getValue(KEY_LST_CASE_QTY));
        txt_StoragePieceQty.setValue(line.getValue(KEY_LST_PIECE_QTY));

        // highlight active row.
        _lcm_lst_ASRSStorageSet.resetHighlight();
        _lcm_lst_ASRSStorageSet.addHighlight(row);
        _lcm_lst_ASRSStorageSet.setEditRow(row);

        // DFKLOOK ここから修正
        viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        viewState.setObject(ViewStateKeys.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
        viewState.setObject(ViewStateKeys.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
        viewState.setObject(ViewStateKeys.PLAN_AREA, line.getValue(KEY_HIDDEN_AREA_NO));
        viewState.setObject(ViewStateKeys.PLAN_LOCATION, line.getValue(KEY_HIDDEN_LOCATION));
        viewState.setObject(ViewStateKeys.PLAN_LOT_NO, line.getValue(KEY_HIDDEN_PLAN_LOT_NO));

        // 残数表示
        dispRemainders(getDispData());;
        // DFKLOOK ここまで修正

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // DFKLOOK ここから修正
        Connection conn = null;
        
        try
        {
            // get active row.
            int row = lst_ASRSStorageSet.getActiveRow();
            lst_ASRSStorageSet.setCurrentRow(row);
    
            // reset editing row.
            lst_ASRSStorageSet.removeRow(row);
            _lcm_lst_ASRSStorageSet.resetEditRow();
            _lcm_lst_ASRSStorageSet.resetHighlight();
    
            if (lst_ASRSStorageSet.getMaxRows() == 1)
            {
                btn_StorageStart.setEnabled(false);
                btn_AllClear.setEnabled(false);
                chk_IssueReport.setEnabled(false);
                pul_LSoftZone.setEnabled(false);
            }
            else
            {
                // 商品に対応するソフトゾーンを検索する設定の時、新しくソフトゾーンを設定する
                if (viewState.getBoolean(ViewStateKeys.NEED_PUL_CHANGE))
                {
                    conn = ConnectionManager.getRequestConnection(this);
                    List<String> item_list = ListCellItems();
                    _pdm_pul_LSoftZone.clear();
                    _pdm_pul_LSoftZone.init(conn, SoftZoneType.AREA, item_list);
                }
            }

            // 残数表示
            dispRemainders(getDispData());
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
    /**
     * リストセル行の商品コードを返します。
     * 編集中の行は含みません。
     * 
     * @return 商品コード
     */
    private List<String> ListCellItems()
    {
        List<String> list = new ArrayList<String>();
        
        for (int i = 1; i <= _lcm_lst_ASRSStorageSet.size(); i++)
        {
            if (_lcm_lst_ASRSStorageSet.getEditRow() == i)
            {
                continue;
            }
            
            // exclusion unmodified row.
            ListCellLine line = _lcm_lst_ASRSStorageSet.get(i);
            
            list.add(line.getStringValue(KEY_LST_ITEM_CODE));
        }
        
        return list;
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
