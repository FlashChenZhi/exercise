// $Id: NoPlanStorageBusiness.java 7663 2010-03-17 11:36:27Z shibamoto $
package jp.co.daifuku.wms.stock.display.noplanstorage;

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
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.LocationFormatException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ReasonController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsReasonPullDownModel;
import jp.co.daifuku.wms.stock.dasch.NoPlanStorageDASCH;
import jp.co.daifuku.wms.stock.dasch.NoPlanStorageDASCHParams;
import jp.co.daifuku.wms.stock.exporter.NoPlanStorageListParams;
import jp.co.daifuku.wms.stock.listbox.emp.LstEmpLocationParams;
import jp.co.daifuku.wms.stock.listbox.rep.LstRepLocationParams;
import jp.co.daifuku.wms.stock.schedule.NoPlanStorageSCH;
import jp.co.daifuku.wms.stock.schedule.NoPlanStorageSCHParams;

/**
 * 予定外入庫設定の画面処理を行います。
 * 
 * @version $Revision: 7663 $, $Date: 2010-03-17 20:36:27 +0900 (水, 17 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
@SuppressWarnings("serial")
public class NoPlanStorageBusiness
        extends NoPlanStorage
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_NoPlanStorage(HIDDEN_REASON_TYPE) */
    private static final ListCellKey KEY_HIDDEN_REASON_TYPE = new ListCellKey("HIDDEN_REASON_TYPE", new StringCellColumn(), false, false);

    /** lst_NoPlanStorage(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_NoPlanStorage(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_NoPlanStorage(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_NoPlanStorage(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_NoPlanStorage(LST_PLAN_LOT_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOT_NO = new ListCellKey("LST_PLAN_LOT_NO", new StringCellColumn(), true, false);

    /** lst_NoPlanStorage(LST_PLAN_AREA_NO) */
    private static final ListCellKey KEY_LST_PLAN_AREA_NO = new ListCellKey("LST_PLAN_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_NoPlanStorage(LST_PLAN_LOCATION_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOCATION_NO = new ListCellKey("LST_PLAN_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_NoPlanStorage(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_NoPlanStorage(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_NoPlanStorage(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_NoPlanStorage(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_NoPlanStorage(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_NoPlanStorage(LST_REASON_NAME) */
    private static final ListCellKey KEY_LST_REASON_NAME = new ListCellKey("LST_REASON_NAME", new StringCellColumn(), true, false);

    /** lst_NoPlanStorage keys */
    private static final ListCellKey[] LST_NOPLANSTORAGE_KEYS = {
        KEY_HIDDEN_REASON_TYPE,
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_ITEM_CODE,
        KEY_LST_PLAN_LOT_NO,
        KEY_LST_PLAN_AREA_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_JAN,
        KEY_LST_REASON_NAME,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_LOCATION_NO,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_ITF,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_StorageArea */
    private WmsAreaPullDownModel _pdm_pul_StorageArea;

    /** PullDownModel pul_Why */
    private WmsReasonPullDownModel _pdm_pul_Why;

    /** ListCellModel lst_NoPlanStorage */
    private ListCellModel _lcm_lst_NoPlanStorage;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public NoPlanStorageBusiness()
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
        if (eventSource.equals("btn_ItemCodeSearch_Click"))
        {
            // process call.
            btn_ItemCodeSearch_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_RepnishmentCandidacy_Click"))
        {
            // process call.
            btn_RepnishmentCandidacy_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_EmptyLocationCandidacy_Click"))
        {
            // process call.
            btn_EmptyLocationCandidacy_Click_DlgBack(dialogParams);
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

        // DFKLOOK ここから修正
        if (eventSource.startsWith("btn_StorageStart_Click"))
        {
            // process call.
            btn_StorageStart_Click_Process(eventSource);
        }
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ItemCodeSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ItemCodeSearch_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_StorageArea_Change(ActionEvent e)
            throws Exception
    {
		// DFKLOOK start
	    // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_StorageArea.getSelectedValue()));
        // DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_RepnishmentCandidacy_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_RepnishmentCandidacy_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_EmptyLocationCandidacy_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_EmptyLocationCandidacy_Click_Process();
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
        // process call.
        // DFKLOOK ここから修正
        // 引数追加
        btn_StorageStart_Click_Process(null);
        // DFKLOOK ここまで修正
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
    public void lst_NoPlanStorage_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_NoPlanStorage.getActiveCol();

        // choose process.
        if (_lcm_lst_NoPlanStorage.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_NoPlanStorage.getColumnIndex(KEY_LST_CANCEL) == activeCol)
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

        // initialize pul_StorageArea.
        _pdm_pul_StorageArea = new WmsAreaPullDownModel(pul_StorageArea, locale, ui);

        // initialize pul_Why.
        _pdm_pul_Why = new WmsReasonPullDownModel(pul_Why, locale, ui);

        // initialize lst_NoPlanStorage.
        _lcm_lst_NoPlanStorage = new ListCellModel(lst_NoPlanStorage, LST_NOPLANSTORAGE_KEYS, locale);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_PLAN_LOT_NO, true);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_PLAN_AREA_NO, true);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_PLAN_LOCATION_NO, true);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_ITF, true);
        _lcm_lst_NoPlanStorage.setToolTipVisible(KEY_LST_REASON_NAME, true);

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

            // load pul_StorageArea.
            _pdm_pul_StorageArea.init(conn, AreaType.FLOOR_AND_TEMP_AND_RECEIVE, StationType.ALL, "", false);

            // load pul_Why.
            _pdm_pul_Why.init(conn, "");

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
    private void lst_NoPlanStorage_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
        setFocus(txt_ItemCode);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // DFKLOOK start
        _pdm_pul_StorageArea.setSelectedValue(null);
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_StorageArea.getSelectedValue()));
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        NoPlanStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new NoPlanStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            NoPlanStorageSCHParams inparam = new NoPlanStorageSCHParams();

            
            inparam.set(NoPlanStorageSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
            // DFKLOOK ここから修正
            // SCH call.
            Params outparam = sch.initFind(inparam);

            // output display.
            viewState.setBoolean(ViewStateKeys.MASTER, outparam.getBoolean(NoPlanStorageSCHParams.MASTER_FLAG));

            // マスタパッケージありの場合は、入力不可項目を設定
            if (getViewState().getBoolean(ViewStateKeys.MASTER))
            {
                txt_ItemName.setReadOnly(true);
                txt_EnteringQty.setReadOnly(true);
                txt_JanCode.setReadOnly(true);
                txt_CaseITF.setReadOnly(true);
            }
            // DFKLOOK ここまで修正

            // clear.
            btn_Input.setEnabled(true);
            btn_Clear.setEnabled(true);
            btn_StorageStart.setEnabled(false);
            btn_AllClear.setEnabled(false);
            chk_IssueReport.setChecked(true);
            chk_IssueReport.setEnabled(false);

            // DFKLOOK ここから
            // 生成したプルダウンの初期値を取得。
            _pdm_pul_StorageArea.setSelectedValue(0);
            // 棚の入力例を表示させます。
            lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                    _pdm_pul_StorageArea.getSelectedValue()));
            


            // 画面定義テーブルをチェック
            
            if (outparam != null)
            {
                String printflg = outparam.getString(NoPlanStorageSCHParams.PRINT_FLAG);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_IssueReport.setChecked(false);
                }
            }
            //DFKLOOK:ここまで修正

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
    private void btn_ItemCodeSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_ItemCodeSearch_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_ItemCodeSearch_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));
        txt_EnteringQty.setValue(outparam.get(LstItemParams.ENTERING_QTY));
        txt_JanCode.setValue(outparam.get(LstItemParams.JAN));
        txt_CaseITF.setValue(outparam.get(LstItemParams.ITF));

        // set focus.
        setFocus(txt_ItemCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_RepnishmentCandidacy_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstRepLocationParams inparam = new LstRepLocationParams();
        inparam.set(LstRepLocationParams.AREA_NO, _pdm_pul_StorageArea.getSelectedValue());
        // DFKLOOK start
        String loc = WmsFormatter.toParamLocation(txt_StorageLocation.getStringValue(), lbl_LocationStyle.getStringValue());
        inparam.set(LstRepLocationParams.LOCATION_NO, loc);
        // DFKLOOK end
        inparam.set(LstRepLocationParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstRepLocationParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstRepLocationParams.LOT_NO, txt_LotNo.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_RepnishmentCandidacy_Click");
        redirect("/stock/listbox/rep/LstRepLocation.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_RepnishmentCandidacy_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstRepLocationParams outparam = new LstRepLocationParams(dialogParams);
        // DFKLOOK start
        String style = SuperLocationHolder.getInstance().getLocationFormat(outparam.get(LstRepLocationParams.AREA_NO));
        String loc = outparam.getString(LstRepLocationParams.LOCATION_NO);
        txt_StorageLocation.setValue(WmsFormatter.toDispLocation(loc, style));
        // DFKLOOK end

        // set focus.
        setFocus(txt_StorageLocation);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_EmptyLocationCandidacy_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstEmpLocationParams inparam = new LstEmpLocationParams();
        inparam.set(LstEmpLocationParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstEmpLocationParams.AREA_NO, _pdm_pul_StorageArea.getSelectedValue());
        // DFKLOOK start
        String loc = WmsFormatter.toParamLocation(txt_StorageLocation.getStringValue(), lbl_LocationStyle.getStringValue());
        inparam.set(LstEmpLocationParams.LOCATION_NO, loc);
        // DFKLOOK end

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_EmptyLocationCandidacy_Click");
        redirect("/stock/listbox/emp/LstEmpLocation.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_EmptyLocationCandidacy_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstEmpLocationParams outparam = new LstEmpLocationParams(dialogParams);
        // DFKLOOK start
        String style = SuperLocationHolder.getInstance().getLocationFormat(outparam.getString(LstEmpLocationParams.AREA_NO));
        String loc = outparam.getString(LstEmpLocationParams.LOCATION_NO);
        txt_StorageLocation.setValue(WmsFormatter.toDispLocation(loc, style));
        // DFKLOOK end

        // set focus.
        setFocus(txt_StorageLocation);

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
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_EnteringQty.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        txt_LotNo.validate(this, false);
        pul_StorageArea.validate(this, true);
        txt_StorageLocation.validate(this, true);
        txt_StorageCaseQty.validate(this, false);
        txt_StoragePieceQty.validate(this, false);
        pul_Why.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        NoPlanStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new NoPlanStorageSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK ここから修正
            if (confirm)
            {
                // マスタありの場合、補完する
                if (viewState.getBoolean(ViewStateKeys.MASTER))
                {
                    NoPlanStorageSCHParams masterParam = new NoPlanStorageSCHParams();

                    // 荷主コード
                    masterParam.set(NoPlanStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                    // 商品コード
                    masterParam.set(NoPlanStorageSCHParams.ITEM_CODE, txt_ItemCode.getText());

                    List<Params> outParam = sch.query(masterParam);

                    if (outParam == null || outParam.size() > 0)
                    {
                        for (Params param : outParam)
                        {
                            // 商品名称
                            txt_ItemName.setText(param.getString(NoPlanStorageSCHParams.ITEM_NAME));
                            // ケース入数
                            txt_EnteringQty.setText(String.valueOf(param.getString(NoPlanStorageSCHParams.ENTERING_QTY)));
                            // JANコード
                            txt_JanCode.setText(param.getString(NoPlanStorageSCHParams.JAN));
                            // ケースITF
                            txt_CaseITF.setText(param.getString(NoPlanStorageSCHParams.ITF));
                        }
                    }
                    else
                    {
                        //MSG="商品コードが登録されていません"
                        message.setMsgResourceKey("6023021");
                        return;
                    }
                }
            }
            // DFKLOOK ここまで修正

            // set input parameters.
            NoPlanStorageSCHParams inparam = new NoPlanStorageSCHParams();
            inparam.set(NoPlanStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(NoPlanStorageSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(NoPlanStorageSCHParams.PLAN_LOT_NO, txt_LotNo.getValue());
            inparam.set(NoPlanStorageSCHParams.PLAN_AREA_NO, _pdm_pul_StorageArea.getSelectedValue());
            // DFKLOOK start
            String loc = WmsFormatter.toParamLocation(txt_StorageLocation.getStringValue(), lbl_LocationStyle.getStringValue());
            inparam.set(NoPlanStorageSCHParams.PLAN_LOCATION_NO, loc);
            // DFKLOOK end
            inparam.set(NoPlanStorageSCHParams.ENTERING_QTY, txt_EnteringQty.getValue());
            inparam.set(NoPlanStorageSCHParams.PLAN_CASE_QTY, txt_StorageCaseQty.getValue());
            inparam.set(NoPlanStorageSCHParams.PLAN_PIECE_QTY, txt_StoragePieceQty.getValue());

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_NoPlanStorage.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_NoPlanStorage.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_NoPlanStorage.get(i);
                NoPlanStorageSCHParams lineparam = new NoPlanStorageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(NoPlanStorageSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(NoPlanStorageSCHParams.PLAN_LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
                lineparam.set(NoPlanStorageSCHParams.PLAN_AREA_NO, line.getValue(KEY_LST_PLAN_AREA_NO));
                lineparam.set(NoPlanStorageSCHParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_PLAN_LOCATION_NO));
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
            // DFKLOOK ここから修正
            if (confirm)
            {
                message.setMsgResourceKey(sch.getMessage());
            }
            else
            {
                message.setMsgResourceKey("6001019");
            }
            // DFKLOOK ここまで修正

            // output display.
            int editRow = _lcm_lst_NoPlanStorage.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_NoPlanStorage.getNewLine()
                                        : _lcm_lst_NoPlanStorage.get(editRow);
            line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
            line.setValue(KEY_LST_ITEM_NAME, txt_ItemName.getValue());
            line.setValue(KEY_LST_PLAN_LOT_NO, txt_LotNo.getValue());
            line.setValue(KEY_LST_PLAN_AREA_NO, _pdm_pul_StorageArea.getSelectedValue());
            // DFKLOOK ここから修正
            line.setValue(KEY_LST_PLAN_LOCATION_NO, loc);
            line.setValue(KEY_LST_ENTERING_QTY, txt_EnteringQty.getInt());
            line.setValue(KEY_LST_PLAN_CASE_QTY, txt_StorageCaseQty.getInt());
            line.setValue(KEY_LST_PLAN_PIECE_QTY, txt_StoragePieceQty.getInt());
            // DFKLOOK ここまで修正
            line.setValue(KEY_LST_JAN, txt_JanCode.getValue());
            line.setValue(KEY_LST_ITF, txt_CaseITF.getValue());
            // DFKLOOK ここから修正
            ReasonController reason = new ReasonController(conn, getClass());
            line.setValue(KEY_LST_REASON_NAME,
                    reason.getReasonName(Integer.parseInt(String.valueOf(_pdm_pul_Why.getSelectedValue()))));
            // DFKLOOK ここまで修正
            line.setValue(KEY_HIDDEN_REASON_TYPE, _pdm_pul_Why.getSelectedValue());

            // add new row or update editing row.
            lst_NoPlanStorage_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_NoPlanStorage.add(line, true);
            }
            else
            {
                _lcm_lst_NoPlanStorage.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_NoPlanStorage.resetEditRow();
            _lcm_lst_NoPlanStorage.resetHighlight();

            // clear.
            btn_StorageStart.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_IssueReport.setEnabled(true);

        }
        //DFKLOOK:ここから修正
        // 棚フォーマットで投げられたExceptionをここでキャッチ
        catch (LocationFormatException ex)
        {
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
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
        txt_ItemName.setValue(null);
        txt_EnteringQty.setValue(null);
        txt_JanCode.setValue(null);
        txt_CaseITF.setValue(null);
        txt_LotNo.setValue(null);
        _pdm_pul_StorageArea.setSelectedValue(null);
        txt_StorageLocation.setValue(null);
        txt_StorageCaseQty.setValue(null);
        txt_StoragePieceQty.setValue(null);
        _pdm_pul_Why.setSelectedValue(null);

        // DFKLOOK start
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_StorageArea.getSelectedValue()));
        // DFKLOOK end
    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK ここから修正
    // 引数追加
    private void btn_StorageStart_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK ここまで修正
    {

        // DFKLOOK:ここから修正
        if (StringUtil.isBlank(eventSource))
        {
            // 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_StorageStart_Click");
            return;
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        NoPlanStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new NoPlanStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_NoPlanStorage.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_NoPlanStorage.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                NoPlanStorageSCHParams lineparam = new NoPlanStorageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(NoPlanStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(NoPlanStorageSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(NoPlanStorageSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(NoPlanStorageSCHParams.PLAN_LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
                lineparam.set(NoPlanStorageSCHParams.PLAN_AREA_NO, line.getValue(KEY_LST_PLAN_AREA_NO));
                lineparam.set(NoPlanStorageSCHParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_PLAN_LOCATION_NO));
                lineparam.set(NoPlanStorageSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(NoPlanStorageSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(NoPlanStorageSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(NoPlanStorageSCHParams.JAN, line.getValue(KEY_LST_JAN));
                lineparam.set(NoPlanStorageSCHParams.ITF, line.getValue(KEY_LST_ITF));
                lineparam.set(NoPlanStorageSCHParams.HARD_WARE_TYPE, SystemDefine.HARDWARE_TYPE_LIST);
                lineparam.set(NoPlanStorageSCHParams.PRINT_FLAG, chk_IssueReport.getValue());
                lineparam.set(NoPlanStorageSCHParams.RESULT_LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
                lineparam.set(NoPlanStorageSCHParams.REASON_TYPE, line.getValue(KEY_HIDDEN_REASON_TYPE));
                // DFKLOOK:ここから
                lineparam.set(NoPlanStorageSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
                // DFKLOOK:ここまで
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // DFKLOOK ここから修正
            if (eventSource.equals("btn_StorageStart_Click"))
            {
                boolean hasNg = false;
                for (ScheduleParams inparam : inparams)
                {
                    // SCH call.
                    if (!sch.check(inparam))
                    {
                        hasNg = true;
                        DisplayUtil.addHighlight(lst_NoPlanStorage, inparam.getRowIndex(), ControlColor.Warning);
                    }
                }

                if (hasNg)
                {
                    // show confirm message.
                    this.setConfirm(sch.getDispMessage(), false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_StorageStart_Click_Check");
                    return;
                }
            }
            // DFKLOOK ここまで修正

            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_NoPlanStorage.resetEditRow();
                _lcm_lst_NoPlanStorage.resetHighlight();
                _lcm_lst_NoPlanStorage.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_NoPlanStorage.size(); i++)
            {
                ListCellLine line = _lcm_lst_NoPlanStorage.get(i);
                lst_NoPlanStorage.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_JAN), "");
                part11List.add(line.getViewString(KEY_LST_ITF), "");
                part11List.add(line.getViewString(KEY_HIDDEN_REASON_TYPE), "");

                if (chk_IssueReport.getChecked())
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

            // DFKLOOK ここから修正
            // 帳票発行追加
            if (chk_IssueReport.getChecked())
            {
                if (!startPrint(locale, ui, inparams[0].getString(NoPlanStorageSCHParams.SETTING_UKEYS)))
                {
                    // メッセージの再セットのみ続きの処理を行う
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6007042));
                }
            }
            // DFKLOOK ここまで修正

            // reset editing row.
            _lcm_lst_NoPlanStorage.resetEditRow();
            _lcm_lst_NoPlanStorage.resetHighlight();

            // clear.
            _lcm_lst_NoPlanStorage.clear();
            btn_StorageStart.setEnabled(false);
            btn_AllClear.setEnabled(false);
            chk_IssueReport.setEnabled(false);

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
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_NoPlanStorage.clear();
        btn_StorageStart.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_IssueReport.setEnabled(false);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Modify_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_NoPlanStorage.getActiveRow();
        lst_NoPlanStorage.setCurrentRow(row);
        ListCellLine line = _lcm_lst_NoPlanStorage.get(row);

        // output display.
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_LotNo.setValue(line.getValue(KEY_LST_PLAN_LOT_NO));
        _pdm_pul_StorageArea.setSelectedValue(line.getValue(KEY_LST_PLAN_AREA_NO));
        // DFKLOOK start
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_StorageArea.getSelectedValue()));
        String style = SuperLocationHolder.getInstance().getLocationFormat(line.getValue(KEY_LST_PLAN_AREA_NO));
        String loc = line.getStringValue(KEY_LST_PLAN_LOCATION_NO);
        txt_StorageLocation.setValue(WmsFormatter.toDispLocation(loc, style));
        // DFKLOOK end
        txt_EnteringQty.setValue(line.getValue(KEY_LST_ENTERING_QTY));
        txt_StorageCaseQty.setValue(line.getValue(KEY_LST_PLAN_CASE_QTY));
        txt_StoragePieceQty.setValue(line.getValue(KEY_LST_PLAN_PIECE_QTY));
        txt_JanCode.setValue(line.getValue(KEY_LST_JAN));
        txt_CaseITF.setValue(line.getValue(KEY_LST_ITF));
        _pdm_pul_Why.setSelectedValue(line.getValue(KEY_HIDDEN_REASON_TYPE));

        // highlight active row.
        _lcm_lst_NoPlanStorage.resetHighlight();
        _lcm_lst_NoPlanStorage.addHighlight(row);
        _lcm_lst_NoPlanStorage.setEditRow(row);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_NoPlanStorage.getActiveRow();
        lst_NoPlanStorage.setCurrentRow(row);

        // reset editing row.
        lst_NoPlanStorage.removeRow(row);
        _lcm_lst_NoPlanStorage.resetEditRow();
        _lcm_lst_NoPlanStorage.resetHighlight();

        // DFKLOOK ここから修正
        // ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンは無効にする
        if (lst_NoPlanStorage.getMaxRows() == 1)
        {
            // リストセルのボタン押下を不可にする
            btn_StorageStart.setEnabled(false);
            btn_AllClear.setEnabled(false);
            chk_IssueReport.setEnabled(false);
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

    // DFKLOOK ここから修正
    // 帳票発行メソッド追加
    /**
     * 予定外入庫作業リストを発行します
     * 
     * @param locale
     * @param ui
     * @param settingUkeys
     */
    private boolean startPrint(Locale locale, DfkUserInfo ui, Object settingUkeys)
            throws Exception
    {
        Connection conn = null;
        NoPlanStorageDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new NoPlanStorageDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            ListCellLine line = _lcm_lst_NoPlanStorage.get(1);
            if (!(line.isAppend() || line.isEdited()))
            {
                return false;
            }

            // set input parameters.
            NoPlanStorageDASCHParams inparam = new NoPlanStorageDASCHParams();
            inparam.set(NoPlanStorageDASCHParams.JOB_TYPE, SystemDefine.JOB_TYPE_NOPLAN_STORAGE);
            inparam.set(NoPlanStorageDASCHParams.HARD_WARE_TYPE, SystemDefine.HARDWARE_TYPE_LIST);
            inparam.set(NoPlanStorageDASCHParams.SEARCH_DATE, "");
            inparam.set(NoPlanStorageDASCHParams.TO_SEARCH_DATE, "");
            inparam.set(NoPlanStorageDASCHParams.AREA_NO, line.getValue(KEY_LST_PLAN_AREA_NO));
            inparam.set(NoPlanStorageDASCHParams.LOCATION_NO, line.getValue(KEY_LST_PLAN_LOCATION_NO));
            inparam.set(NoPlanStorageDASCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
            inparam.set(NoPlanStorageDASCHParams.SETTING_UNIT_KEY, settingUkeys);
            inparam.set(NoPlanStorageDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                return false;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            if (exporter == null)
            {
                ExporterFactory factory = new WmsExporterFactory(locale, ui);
                exporter = factory.newPrinterExporter("NoPlanStorageList", false);
                exporter.open();
            }

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                NoPlanStorageListParams expparam = new NoPlanStorageListParams();
                expparam.set(NoPlanStorageListParams.DFK_DS_NO, outparam.get(NoPlanStorageDASCHParams.DFK_DS_NO));
                expparam.set(NoPlanStorageListParams.DFK_USER_ID, outparam.get(NoPlanStorageDASCHParams.DFK_USER_ID));
                expparam.set(NoPlanStorageListParams.DFK_USER_NAME,
                        outparam.get(NoPlanStorageDASCHParams.DFK_USER_NAME));
                expparam.set(NoPlanStorageListParams.LISTNO, outparam.get(NoPlanStorageDASCHParams.LISTNO));
                expparam.set(NoPlanStorageListParams.STORAGE_DAY, outparam.get(NoPlanStorageDASCHParams.STORAGE_DAY));
                expparam.set(NoPlanStorageListParams.SYS_DAY, outparam.get(NoPlanStorageDASCHParams.SYS_DAY));
                expparam.set(NoPlanStorageListParams.SYS_TIME, outparam.get(NoPlanStorageDASCHParams.SYS_TIME));
                expparam.set(NoPlanStorageListParams.ITEM_CODE, outparam.get(NoPlanStorageDASCHParams.ITEM_CODE));
                expparam.set(NoPlanStorageListParams.ITEM_NAME, outparam.get(NoPlanStorageDASCHParams.ITEM_NAME));
                expparam.set(NoPlanStorageListParams.STORAGE_AREA_NO, outparam.get(NoPlanStorageDASCHParams.AREA_NO));
                expparam.set(NoPlanStorageListParams.STORAGE_LOCATION_NO,
                        outparam.get(NoPlanStorageDASCHParams.LOCATION_NO));
                expparam.set(NoPlanStorageListParams.LOT_NO, outparam.get(NoPlanStorageDASCHParams.LOT_NO));
                expparam.set(NoPlanStorageListParams.ENTERING_QTY, outparam.get(NoPlanStorageDASCHParams.ENTERING_QTY));
                expparam.set(NoPlanStorageListParams.STORAGE_CASE_QTY,
                        outparam.get(NoPlanStorageDASCHParams.PLAN_CASE_QTY));
                expparam.set(NoPlanStorageListParams.STORAGE_PIECE_QTY,
                        outparam.get(NoPlanStorageDASCHParams.PLAN_PIECE_QTY));
                expparam.set(NoPlanStorageListParams.JAN, outparam.get(NoPlanStorageDASCHParams.JAN));
                expparam.set(NoPlanStorageListParams.ITF, outparam.get(NoPlanStorageDASCHParams.ITF));
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
    // DFKLOOK ここまで修正

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
