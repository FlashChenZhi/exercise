// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.asrs.display.noplanaddstorage;

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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
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
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.dasch.AsNoPlanStorageDASCH;
import jp.co.daifuku.wms.asrs.dasch.AsNoPlanStorageDASCHParams;
import jp.co.daifuku.wms.asrs.exporter.ASRSNoPlanStorageListParams;
import jp.co.daifuku.wms.asrs.listbox.stockdetailnbtn.LstAsStockDetailNoBtnParams;
import jp.co.daifuku.wms.asrs.schedule.AsNoPlanAddStorageSCH;
import jp.co.daifuku.wms.asrs.schedule.AsNoPlanAddStorageSCHParams;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.ReasonController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsReasonPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * AS/RS 予定外入庫設定(積増)（入庫情報入力）の画面処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:06:27 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
@SuppressWarnings("serial")
public class AsNoPlanAddStorage2Business
        extends AsNoPlanAddStorage2
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_AsRsNoPlanStorage(HIDDEN_REASON_TYPE) */
    private static final ListCellKey KEY_HIDDEN_REASON_TYPE = new ListCellKey("HIDDEN_REASON_TYPE", new StringCellColumn(), false, false);

    /** lst_AsRsNoPlanStorage(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_AsRsNoPlanStorage(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_AsRsNoPlanStorage(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_AsRsNoPlanStorage(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_AsRsNoPlanStorage(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_AsRsNoPlanStorage(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_AsRsNoPlanStorage(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_AsRsNoPlanStorage(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_AsRsNoPlanStorage(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_AsRsNoPlanStorage(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_AsRsNoPlanStorage(LST_REASON_NAME) */
    private static final ListCellKey KEY_LST_REASON_NAME = new ListCellKey("LST_REASON_NAME", new StringCellColumn(), true, false);

    /** lst_AsRsNoPlanStorage keys */
    private static final ListCellKey[] LST_ASRSNOPLANSTORAGE_KEYS = {
        KEY_HIDDEN_REASON_TYPE,
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_JAN,
        KEY_LST_REASON_NAME,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_ITF,
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

    /** PullDownModel pul_Why */
    private WmsReasonPullDownModel _pdm_pul_Why;

    /** ListCellModel lst_AsRsNoPlanStorage */
    private ListCellModel _lcm_lst_AsRsNoPlanStorage;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsNoPlanAddStorage2Business()
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
        if (eventSource.equals("btn_LocationDetail_Click"))
        {
            // process call.
            btn_LocationDetail_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_ItemCodeSearch_Click"))
        {
            // process call.
            btn_ItemCodeSearch_Click_DlgBack(dialogParams);
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
        // choose process.
        if (eventSource.equals("btn_StorageStart_Click"))
        {
            btn_StorageStart_Click_Process(eventSource);
        }
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Back_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Back_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_LocationDetail_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_LocationDetail_Click_Process();
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
        // DFKLOOK ここから修正
        // process call.
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
    public void lst_AsRsNoPlanStorage_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_AsRsNoPlanStorage.getActiveCol();

        // choose process.
        if (_lcm_lst_AsRsNoPlanStorage.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_AsRsNoPlanStorage.getColumnIndex(KEY_LST_CANCEL) == activeCol)
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

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);
        _pdm_pul_WorkPlace.setParent(_pdm_pul_Area);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
        _pdm_pul_Station.setParent(_pdm_pul_WorkPlace);

        // initialize pul_Why.
        _pdm_pul_Why = new WmsReasonPullDownModel(pul_Why, locale, ui);

        // initialize lst_AsRsNoPlanStorage.
        _lcm_lst_AsRsNoPlanStorage = new ListCellModel(lst_AsRsNoPlanStorage, LST_ASRSNOPLANSTORAGE_KEYS, locale);
        _lcm_lst_AsRsNoPlanStorage.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_AsRsNoPlanStorage.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_AsRsNoPlanStorage.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_AsRsNoPlanStorage.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_AsRsNoPlanStorage.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_AsRsNoPlanStorage.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_AsRsNoPlanStorage.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_AsRsNoPlanStorage.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_AsRsNoPlanStorage.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_AsRsNoPlanStorage.setToolTipVisible(KEY_LST_ITF, true);
        _lcm_lst_AsRsNoPlanStorage.setToolTipVisible(KEY_LST_REASON_NAME, true);

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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.ADD_STORAGE, "", false);

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.ADD_STORAGE);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.ADD_STORAGE, Distribution.AUTO);

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
    private void lst_AsRsNoPlanStorage_SetLineToolTip(ListCellLine line)
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
    	// デフォルト帳票チェックON
        chk_IssueReport.setChecked(true);
    	// DFKLOOK end
    	
    	// get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsNoPlanAddStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsNoPlanAddStorageSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK ここから修正
            // チェックボックスのチェック状態を取得
            AsNoPlanAddStorageSCHParams inParam = new AsNoPlanAddStorageSCHParams();
            inParam.set(AsNoPlanAddStorageSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));

            // 画面定義テーブルをチェック
            Params outParam = sch.initFind(inParam);
            
            if (outParam != null)
            {
                String printflg = outParam.getString(AsNoPlanAddStorageSCHParams.PRINT_FLAG);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_IssueReport.setChecked(false);
                }
            }
            
            AreaController area = new AreaController(conn, this.getClass());
            String style = area.getLocationStyle(String.valueOf(viewState.getObject(ViewStateKeys.AREA_NO)));

            // output display.  
            lbl_In_Area.setValue(viewState.getObject(ViewStateKeys.AREA_NO) + ":"
                    + viewState.getObject(ViewStateKeys.AREA_NAME));
            lbl_In_Location.setValue(WmsFormatter.toDispLocation(
                    String.valueOf(viewState.getObject(ViewStateKeys.LOCATION_NO)), style));
            viewState.setObject(ViewStateKeys.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));
            viewState.setObject(ViewStateKeys.LOCATION_NO, viewState.getObject(ViewStateKeys.LOCATION_NO));
            _pdm_pul_Area.setSelectedValue(viewState.getObject(ViewStateKeys.AREA_NO));

            boolean master = sch.check(null);

            // マスタパッケージの有無を保持
            viewState.setBooleanObject(ViewStateKeys.MASTER, master);

            // マスタパッケージありの場合は、入力不可項目を設定
            if (viewState.getBooleanObject(ViewStateKeys.MASTER))
            {
                // clear.
                txt_ItemName.setReadOnly(true);
                txt_EnteringQty.setReadOnly(true);
                txt_JanCode.setReadOnly(true);
                txt_CaseITF.setReadOnly(true);
            }

            // DFKLOOK ここまで修正

            // clear.
            pul_Area.setVisible(false);
            btn_Input.setEnabled(true);
            btn_Clear.setEnabled(true);
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
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Back_Click_Process()
            throws Exception
    {
        try
        {
            // forward.
            forward("/asrs/noplanaddstorage/AsNoPlanAddStorage.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_LocationDetail_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstAsStockDetailNoBtnParams inparam = new LstAsStockDetailNoBtnParams();
        inparam.set(LstAsStockDetailNoBtnParams.LOCATION_NO, viewState.getObject(ViewStateKeys.LOCATION_NO));
        inparam.set(LstAsStockDetailNoBtnParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_LocationDetail_Click");
        redirect("/asrs/listbox/stockdetailnbtn/LstAsStockDetailNoBtn.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_LocationDetail_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // set focus.
        setFocus(txt_ItemCode);

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
        txt_StorageCaseQty.validate(this, false);
        txt_StoragePieceQty.validate(this, false);
        pul_Why.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsNoPlanAddStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsNoPlanAddStorageSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK ここから修正
            // マスタありの場合、補完する
            if (viewState.getBooleanObject(ViewStateKeys.MASTER))
            {
                AsNoPlanAddStorageSCHParams masterParam = new AsNoPlanAddStorageSCHParams();

                // 荷主コード
                masterParam.set(AsNoPlanAddStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                // 商品コード
                masterParam.set(AsNoPlanAddStorageSCHParams.ITEM_CODE, txt_ItemCode.getText());

                List<Params> outParam = sch.query(masterParam);

                if (outParam == null || outParam.size() > 0)
                {
                    for (Params param : outParam)
                    {
                        // 商品名称
                        txt_ItemName.setText(param.getString(AsNoPlanAddStorageSCHParams.ITEM_NAME));
                        // ケース入数
                        txt_EnteringQty.setText(String.valueOf(param.getString(AsNoPlanAddStorageSCHParams.ENTERING_QTY)));
                        // JANコード
                        txt_JanCode.setText(param.getString(AsNoPlanAddStorageSCHParams.JAN));
                        // ケースITF
                        txt_CaseITF.setText(param.getString(AsNoPlanAddStorageSCHParams.ITF));
                    }
                }
                else
                {
                    //MSG="商品コードが登録されていません"
                    message.setMsgResourceKey("6023021");
                    return;
                }
            }
            // DFKLOOK ここまで修正

            // set input parameters.
            AsNoPlanAddStorageSCHParams inparam = new AsNoPlanAddStorageSCHParams();
            inparam.set(AsNoPlanAddStorageSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));
            inparam.set(AsNoPlanAddStorageSCHParams.LOCATION_NO, viewState.getObject(ViewStateKeys.LOCATION_NO));
            inparam.set(AsNoPlanAddStorageSCHParams.PALLET_ID, viewState.getObject(ViewStateKeys.PALLET_ID));
            inparam.set(AsNoPlanAddStorageSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(AsNoPlanAddStorageSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(AsNoPlanAddStorageSCHParams.ENTERING_QTY, txt_EnteringQty.getValue());
            inparam.set(AsNoPlanAddStorageSCHParams.PLAN_CASE_QTY, txt_StorageCaseQty.getValue());
            inparam.set(AsNoPlanAddStorageSCHParams.PLAN_PIECE_QTY, txt_StoragePieceQty.getValue());

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_AsRsNoPlanStorage.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_AsRsNoPlanStorage.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_AsRsNoPlanStorage.get(i);
                AsNoPlanAddStorageSCHParams lineparam = new AsNoPlanAddStorageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(AsNoPlanAddStorageSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));
                lineparam.set(AsNoPlanAddStorageSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(AsNoPlanAddStorageSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
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

            // output display.
            int editRow = _lcm_lst_AsRsNoPlanStorage.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_AsRsNoPlanStorage.getNewLine()
                                        : _lcm_lst_AsRsNoPlanStorage.get(editRow);
            line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
            line.setValue(KEY_LST_ITEM_NAME, txt_ItemName.getValue());
            line.setValue(KEY_LST_LOT_NO, txt_LotNo.getValue());
            line.setValue(KEY_LST_ENTERING_QTY, txt_EnteringQty.getValue());
            line.setValue(KEY_LST_JAN, txt_JanCode.getValue());
            line.setValue(KEY_LST_ITF, txt_CaseITF.getValue());
            // DFKLOOK ここから修正
            line.setValue(KEY_LST_PLAN_CASE_QTY, txt_StorageCaseQty.getInt());
            line.setValue(KEY_LST_PLAN_PIECE_QTY, txt_StoragePieceQty.getInt());
            ReasonController reason = new ReasonController(conn, getClass());
            line.setValue(KEY_LST_REASON_NAME,
                    reason.getReasonName(Integer.parseInt(String.valueOf(_pdm_pul_Why.getSelectedValue()))));
            // DFKLOOK ここまで修正
            line.setValue(KEY_HIDDEN_REASON_TYPE, _pdm_pul_Why.getSelectedValue());

            // add new row or update editing row.
            lst_AsRsNoPlanStorage_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_AsRsNoPlanStorage.add(line, true);
            }
            else
            {
                _lcm_lst_AsRsNoPlanStorage.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_AsRsNoPlanStorage.resetEditRow();
            _lcm_lst_AsRsNoPlanStorage.resetHighlight();

            // clear.
            btn_StorageStart.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_IssueReport.setEnabled(true);

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
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        txt_EnteringQty.setValue(null);
        txt_JanCode.setValue(null);
        txt_CaseITF.setValue(null);
        txt_LotNo.setValue(null);
        txt_StorageCaseQty.setValue(null);
        txt_StoragePieceQty.setValue(null);
        _pdm_pul_Why.setSelectedValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_StorageStart_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);
   
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
        AsNoPlanAddStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsNoPlanAddStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_AsRsNoPlanStorage.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_AsRsNoPlanStorage.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsNoPlanAddStorageSCHParams lineparam = new AsNoPlanAddStorageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(AsNoPlanAddStorageSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(AsNoPlanAddStorageSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(AsNoPlanAddStorageSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(AsNoPlanAddStorageSCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(AsNoPlanAddStorageSCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(AsNoPlanAddStorageSCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(AsNoPlanAddStorageSCHParams.JAN, line.getValue(KEY_LST_JAN));
                lineparam.set(AsNoPlanAddStorageSCHParams.ITF, line.getValue(KEY_LST_ITF));
                lineparam.set(AsNoPlanAddStorageSCHParams.REASON_TYPE, line.getValue(KEY_HIDDEN_REASON_TYPE));
                lineparam.set(AsNoPlanAddStorageSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA_NO));
                lineparam.set(AsNoPlanAddStorageSCHParams.LOCATION_NO, viewState.getObject(ViewStateKeys.LOCATION_NO));
                lineparam.set(AsNoPlanAddStorageSCHParams.MASTER, viewState.getObject(ViewStateKeys.MASTER));
                lineparam.set(AsNoPlanAddStorageSCHParams.PALLET_ID, viewState.getObject(ViewStateKeys.PALLET_ID));
                lineparam.set(AsNoPlanAddStorageSCHParams.PRINT_FLAG, chk_IssueReport.getValue());
                lineparam.set(AsNoPlanAddStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(AsNoPlanAddStorageSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
                lineparam.set(AsNoPlanAddStorageSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
                lineparam.set(AsNoPlanAddStorageSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
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
                _lcm_lst_AsRsNoPlanStorage.resetEditRow();
                _lcm_lst_AsRsNoPlanStorage.resetHighlight();
                _lcm_lst_AsRsNoPlanStorage.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_AsRsNoPlanStorage.size(); i++)
            {
                ListCellLine line = _lcm_lst_AsRsNoPlanStorage.get(i);
                lst_AsRsNoPlanStorage.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
                part11List.add(lbl_In_Location.getStringValue(), "");
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
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "0");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "0");
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
            if (chk_IssueReport.getChecked())
            {
                if (!startPrint(locale, ui, inparams[0].getString(AsNoPlanAddStorageSCHParams.SETTING_UNITKEY),
                        inparams[0].getString(AsNoPlanAddStorageSCHParams.STATION_NO),
                        inparams[0].getString(AsNoPlanAddStorageSCHParams.RETRIEVAL_STATION_NO),
                        inparams[0].getString(AsNoPlanAddStorageSCHParams.STORAGE_STATION_NO)))
                {
                    System.out.println("false");
                    // メッセージの再セットのみ続きの処理を行う
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6007042));
                }
            }

            // 出庫指示送信を起動します。
            SendRequestor req = new SendRequestor();
            req.retrieval();
            // DFKLOOK ここまで修正

            // reset editing row.
            _lcm_lst_AsRsNoPlanStorage.resetEditRow();
            _lcm_lst_AsRsNoPlanStorage.resetHighlight();

            // clear.
            _lcm_lst_AsRsNoPlanStorage.clear();
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
        _lcm_lst_AsRsNoPlanStorage.clear();
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
        int row = lst_AsRsNoPlanStorage.getActiveRow();
        lst_AsRsNoPlanStorage.setCurrentRow(row);
        ListCellLine line = _lcm_lst_AsRsNoPlanStorage.get(row);

        // output display.
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_LotNo.setValue(line.getValue(KEY_LST_LOT_NO));
        txt_EnteringQty.setValue(line.getValue(KEY_LST_ENTERING_QTY));
        txt_StorageCaseQty.setValue(line.getValue(KEY_LST_PLAN_CASE_QTY));
        txt_StoragePieceQty.setValue(line.getValue(KEY_LST_PLAN_PIECE_QTY));
        txt_JanCode.setValue(line.getValue(KEY_LST_JAN));
        txt_CaseITF.setValue(line.getValue(KEY_LST_ITF));
        _pdm_pul_Why.setSelectedValue(line.getValue(KEY_HIDDEN_REASON_TYPE));

        // highlight active row.
        _lcm_lst_AsRsNoPlanStorage.resetHighlight();
        _lcm_lst_AsRsNoPlanStorage.addHighlight(row);
        _lcm_lst_AsRsNoPlanStorage.setEditRow(row);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_AsRsNoPlanStorage.getActiveRow();
        lst_AsRsNoPlanStorage.setCurrentRow(row);

        // reset editing row.
        lst_AsRsNoPlanStorage.removeRow(row);
        _lcm_lst_AsRsNoPlanStorage.resetEditRow();
        _lcm_lst_AsRsNoPlanStorage.resetHighlight();

        // DFKLOOK ここから修正
        // ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンは無効にする
        if (lst_AsRsNoPlanStorage.getMaxRows() == 1)
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
    /**
     * AS/RS予定外入庫作業リストを発行します
     * 
     * @param locale
     * @param ui
     * @param settingUkeys
     */
    private boolean startPrint(Locale locale, DfkUserInfo ui, Object settingUkeys, Object stationNo,
            Object retrievalStationNo, Object StorageStationNo)
            throws Exception
    {
        Connection conn = null;
        AsNoPlanStorageDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsNoPlanStorageDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsNoPlanStorageDASCHParams inparam = new AsNoPlanStorageDASCHParams();
            inparam.set(AsNoPlanStorageDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(AsNoPlanStorageDASCHParams.SETTING_UNITKEY, settingUkeys);
            inparam.set(AsNoPlanStorageDASCHParams.RETRIEVAL_STATION_NO, retrievalStationNo);
            inparam.set(AsNoPlanStorageDASCHParams.STORAGE_STATION_NO, StorageStationNo);
            inparam.set(AsNoPlanStorageDASCHParams.STATION_NO, stationNo);
            inparam.set(AsNoPlanStorageDASCHParams.WORK_TYPE, SystemDefine.WORK_TYPE_ADD_STORAGE);

            // check count.
            int count = dasch.count(inparam);
            //            if (confirm && count > 0)
            //            {
            //                // show confirm message.
            //                this.setConfirm(dasch.getDispMessage(), false, true);
            //                viewState.setString(_KEY_CONFIRMSOURCE, "btn_StorageStart_Click");
            //                return;
            //            }
            //            else
            if (count == 0)
            {
                return false;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("ASRSNoPlanStorageList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ASRSNoPlanStorageListParams expparam = new ASRSNoPlanStorageListParams();
                expparam.set(ASRSNoPlanStorageListParams.DFK_DS_NO, outparam.get(AsNoPlanStorageDASCHParams.DFK_DS_NO));
                expparam.set(ASRSNoPlanStorageListParams.DFK_USER_ID, outparam.get(AsNoPlanStorageDASCHParams.DFK_USER_ID));
                expparam.set(ASRSNoPlanStorageListParams.DFK_USER_NAME, outparam.get(AsNoPlanStorageDASCHParams.DFK_USER_NAME));
                expparam.set(ASRSNoPlanStorageListParams.AREA_NO, outparam.get(AsNoPlanStorageDASCHParams.AREA_NO));
                expparam.set(ASRSNoPlanStorageListParams.AREA_NAME, outparam.get(AsNoPlanStorageDASCHParams.AREA_NAME));
                expparam.set(ASRSNoPlanStorageListParams.SYS_DAY, outparam.get(AsNoPlanStorageDASCHParams.SYS_DAY));
                expparam.set(ASRSNoPlanStorageListParams.SYS_TIME, outparam.get(AsNoPlanStorageDASCHParams.SYS_TIME));
                expparam.set(ASRSNoPlanStorageListParams.STATION_NO, outparam.get(AsNoPlanStorageDASCHParams.STATION_NO));
                expparam.set(ASRSNoPlanStorageListParams.STATION_NAME, outparam.get(AsNoPlanStorageDASCHParams.STATION_NAME));
                expparam.set(ASRSNoPlanStorageListParams.JOB_NO, outparam.get(AsNoPlanStorageDASCHParams.JOB_NO));
                expparam.set(ASRSNoPlanStorageListParams.STORAGE_LOCATION_NO, outparam.get(AsNoPlanStorageDASCHParams.LOCATION_NO));
                expparam.set(ASRSNoPlanStorageListParams.ITEM_CODE, outparam.get(AsNoPlanStorageDASCHParams.ITEM_CODE));
                expparam.set(ASRSNoPlanStorageListParams.ITEM_NAME, outparam.get(AsNoPlanStorageDASCHParams.ITEM_NAME));
                expparam.set(ASRSNoPlanStorageListParams.LOT_NO, outparam.get(AsNoPlanStorageDASCHParams.LOT_NO));
                expparam.set(ASRSNoPlanStorageListParams.ENTERING_QTY, outparam.get(AsNoPlanStorageDASCHParams.ENTERING_QTY));
                expparam.set(ASRSNoPlanStorageListParams.STORAGE_CASE_QTY, outparam.get(AsNoPlanStorageDASCHParams.STORAGE_CASE_QTY));
                expparam.set(ASRSNoPlanStorageListParams.STORAGE_PIECE_QTY,
                        outparam.get(AsNoPlanStorageDASCHParams.STORAGE_PIECE_QTY));
                expparam.set(ASRSNoPlanStorageListParams.JAN, outparam.get(AsNoPlanStorageDASCHParams.JAN));
                expparam.set(ASRSNoPlanStorageListParams.ITF, outparam.get(AsNoPlanStorageDASCHParams.ITF));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }
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
