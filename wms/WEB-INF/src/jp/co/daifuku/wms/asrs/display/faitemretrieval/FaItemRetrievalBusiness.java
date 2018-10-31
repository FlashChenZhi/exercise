// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.asrs.display.faitemretrieval;

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
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.Constants;
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
import jp.co.daifuku.wms.asrs.communication.as21.SendRequestor;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.asrs.schedule.FaItemRetrievalSCH;
import jp.co.daifuku.wms.asrs.schedule.FaItemRetrievalSCHParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.itemname.LstItemNameParams;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * 商品コード指定出庫設定の画面処理を行います。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaItemRetrievalBusiness
        extends FaItemRetrieval
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaItemRetrieval(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_FaItemRetrieval(HIDDEN_TYPE) */
    private static final ListCellKey KEY_HIDDEN_TYPE = new ListCellKey("HIDDEN_TYPE", new StringCellColumn(), false, false);

    /** lst_FaItemRetrieval(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval(LST_RETRIEVAL_RESULT_QTY) */
    private static final ListCellKey KEY_LST_RETRIEVAL_RESULT_QTY = new ListCellKey("LST_RETRIEVAL_RESULT_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaItemRetrieval(LST_TYPE) */
    private static final ListCellKey KEY_LST_TYPE = new ListCellKey("LST_TYPE", new StringCellColumn(), true, false);

    /** lst_FaItemRetrieval keys */
    private static final ListCellKey[] LST_FAITEMRETRIEVAL_KEYS = {
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_HIDDEN_TYPE,
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOT_NO,
        KEY_LST_RETRIEVAL_RESULT_QTY,
        KEY_LST_TYPE,
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

    /** PullDownModel pul_PriorityFlag */
    private DefaultPullDownModel _pdm_pul_PriorityFlag;

    /** ListCellModel lst_FaItemRetrieval */
    private ListCellModel _lcm_lst_FaItemRetrieval;
    
    // DFKLOOK start
    /** 入力商品チェック済みフラグ */
    private boolean _isItemCheck = false;
    // DFKLOOK end

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaItemRetrievalBusiness()
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
        if (eventSource.equals("btn_SearchItemCode_Click"))
        {
            // process call.
            btn_SearchItemCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchItemName_Click"))
        {
            // process call.
            btn_SearchItemName_Click_DlgBack(dialogParams);
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

        // DFKLOOK start
        // choose process.
        if (eventSource.startsWith("btn_Input_Click"))
        {
            // process call.
            btn_Input_Click_Process(eventSource);
        }
        if (eventSource.startsWith("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(eventSource);
        }
        else if (eventSource.startsWith("checkItem"))
        {
            // process call.
            _isItemCheck = true;
            btn_Input_Click_Process(eventSource);
        }
        // DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ItemCode_EnterKey(ActionEvent e)
            throws Exception
    {
        // process call.
        txt_ItemCode_EnterKey_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItemCode_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchItemName_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItemName_Click_Process();
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
    public void btn_Input_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
        // process call.
        btn_Input_Click_Process(null);
        // DFKLOOK end
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
        // process call.
    	//DFKLOOK:メソッド引数追加
        btn_Set_Click_Process(null);
        //DFKLOOK:ここまで修正
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
    public void lst_FaItemRetrieval_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_FaItemRetrieval.getActiveCol();

        // choose process.
        if (_lcm_lst_FaItemRetrieval.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_FaItemRetrieval.getColumnIndex(KEY_LST_CANCEL) == activeCol)
        {
            // process call.
            lst_Cancel_Click_Process();
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_FaItemRetrieval_ColumClick(ActionEvent e)
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

        // initialize pul_PriorityFlag.
        _pdm_pul_PriorityFlag = new DefaultPullDownModel(pul_PriorityFlag, locale, ui);

        // initialize lst_FaItemRetrieval.
        _lcm_lst_FaItemRetrieval = new ListCellModel(lst_FaItemRetrieval, LST_FAITEMRETRIEVAL_KEYS, locale);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_MODIFY, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_CANCEL, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_NO, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_ITEM_NAME, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_LOT_NO, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_RETRIEVAL_RESULT_QTY, false);
        _lcm_lst_FaItemRetrieval.setToolTipVisible(KEY_LST_TYPE, false);

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
            //DFKLOOK:ここから修正
            _pdm_pul_Area.init(conn, AreaType.NOT_MOVING_TERM, StationType.ITEM_RETRIEVAL, "", true);
            //DFKLOOK:ここまで修正

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.ITEM_RETRIEVAL,"", Distribution.UNUSE, true);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.ITEM_RETRIEVAL, Distribution.ALL, Distribution.ALL,"" ,true);

            // load pul_PriorityFlag.
            _pdm_pul_PriorityFlag.init(conn);

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
    private void lst_FaItemRetrieval_SetLineToolTip(ListCellLine line)
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
        btn_AllClear.setEnabled(false);
        chk_WorkListPrint.setEnabled(false);
        chk_ShortageListPrint.setEnabled(false);
        txt_EnteredLines.setReadOnly(true);
        txt_EnterableLines.setReadOnly(true);
        txt_TotalStockQty.setReadOnly(true);
        // DFKLOOK start
        if (WmsParam.MULTI_ALLOCATE_FLAG)
        {
            // マルチ引当ありの場合、プルダウン選択なし
            pul_PriorityFlag.setEnabled(false);            
        }
        else 
        {
            // マルチ引当なしの場合、プルダウン選択あり
            pul_PriorityFlag.setEnabled(true);
        }

        // DFKLOOK end
        
        // DFKLOOK start
        // TODO:入力数、入力可能数のセット
        // 入力数/入力可能数の設定
        txt_EnteredLines.setInt(0);
        txt_EnterableLines.setInt(WmsParam.MAX_NUMBER_OF_DISP);
        
        // デフォルトとしてリスト発行にチェック
        chk_WorkListPrint.setChecked(true);
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // 画面定義テーブルをチェック
            sch = new FaItemRetrievalSCH(conn, this.getClass(), locale, ui);
            
            FaItemRetrievalSCHParams inparam = new FaItemRetrievalSCHParams();
            inparam.set(FaItemRetrievalSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
            
            Params outParam = sch.initFind(inparam);
            
            if (outParam != null)
            {
                String printflg = outParam.getString(FaItemRetrievalSCHParams.WORK_LIST_PRINT);
                if (SystemDefine.KEYDATA_OFF.equals(printflg))
                {
                    // 前回、チェックOFFだった場合は更新
                    chk_WorkListPrint.setChecked(false);
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
        // DFKLOOK end
    }

    /**
     *
     * @throws Exception
     */
    private void txt_ItemCode_EnterKey_Process()
            throws Exception
    {
        // DFKLOOK start
        // set focus.
        setFocus(txt_LotNo);
    	getItemName();
    	// DFKLOOK end
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));

        // set focus.
        setFocus(txt_ItemCode);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchItemName_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemNameParams inparam = new LstItemNameParams();
        inparam.set(LstItemNameParams.ITEM_NAME, txt_ItemName.getValue());
        inparam.set(LstItemNameParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItemName_Click");
        redirect("/base/listbox/itemname/LstItemName.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItemName_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemNameParams outparam = new LstItemNameParams(dialogParams);
        txt_ItemName.setValue(outparam.get(LstItemNameParams.ITEM_NAME));
        txt_ItemCode.setValue(outparam.get(LstItemNameParams.ITEM_CODE));

        // set focus.
        setFocus(txt_ItemName);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);
        txt_ItemCode.validate(this, true);
        txt_LotNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaItemRetrievalSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaItemRetrievalSCHParams inparam = new FaItemRetrievalSCHParams();
            inparam.set(FaItemRetrievalSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(FaItemRetrievalSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(FaItemRetrievalSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_TotalStockQty.setValue(outparam.get(FaItemRetrievalSCHParams.TOTAL_STOCK_QTY));
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
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    // DFKLOOK start
    private void btn_Input_Click_Process(String eventSource)
            throws Exception
    // DFKLOOK end
    {
        // input validation.
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_LotNo.validate(this, false);
        txt_TotalStockQty.validate(this, false);
        txt_PickingQty.validate(this, true);
        pul_PriorityFlag.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaItemRetrievalSCH(conn, this.getClass(), locale, ui);

            // DFKLOOK start
            // 入力商品情報のチェック
            if ((StringUtil.isBlank(eventSource) || !eventSource.equals("btn_Input_Click_SCH")) && !checkItem(conn))
            {
                return;
            }
            // DFKLOOK end
            
            // set input parameters.
            FaItemRetrievalSCHParams inparam = new FaItemRetrievalSCHParams();
            inparam.set(FaItemRetrievalSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(FaItemRetrievalSCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(FaItemRetrievalSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(FaItemRetrievalSCHParams.TOTAL_STOCK_QTY, txt_TotalStockQty.getValue());
            inparam.set(FaItemRetrievalSCHParams.RETRIEVAL_QTY, txt_PickingQty.getValue());
            inparam.set(FaItemRetrievalSCHParams.PRIORITY_FLAG, _pdm_pul_PriorityFlag.getSelectedValue());
            inparam.set(FaItemRetrievalSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // DFKLOOK start 総在庫数の補完
            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_TotalStockQty.setValue(outparam.get(FaItemRetrievalSCHParams.TOTAL_STOCK_QTY));
            }
            // DFKLOOK end

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaItemRetrieval.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_FaItemRetrieval.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_FaItemRetrieval.get(i);
                FaItemRetrievalSCHParams lineparam = new FaItemRetrievalSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(FaItemRetrievalSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(FaItemRetrievalSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaItemRetrievalSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(FaItemRetrievalSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(FaItemRetrievalSCHParams.RETRIEVAL_RESULT_QTY, line.getValue(KEY_LST_RETRIEVAL_RESULT_QTY));
                lineparam.set(FaItemRetrievalSCHParams.TYPE, line.getValue(KEY_LST_TYPE));
                lineparam.set(FaItemRetrievalSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            if ((StringUtil.isBlank(eventSource) || eventSource.equals("checkItem")) && !sch.check(inparam, inparams))
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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_Input_Click_SCH");
                    return;
                }
            }

            message.setMsgResourceKey(sch.getMessage());

            // output display.
            int editRow = _lcm_lst_FaItemRetrieval.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_FaItemRetrieval.getNewLine()
                                        : _lcm_lst_FaItemRetrieval.get(editRow);
            line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
            line.setValue(KEY_LST_ITEM_NAME, txt_ItemName.getValue());
            line.setValue(KEY_LST_LOT_NO, txt_LotNo.getValue());
            line.setValue(KEY_LST_RETRIEVAL_RESULT_QTY, txt_PickingQty.getValue());
            // DFKLOOK DisplayResourceを取得
            line.setValue(KEY_LST_TYPE, DisplayResource.getPriority(_pdm_pul_PriorityFlag.getSelectedStringValue()));
            //DFKLOOK:ここまで修正
            line.setValue(KEY_HIDDEN_TYPE, _pdm_pul_PriorityFlag.getSelectedStringValue());

            // add new row or update editing row.
            lst_FaItemRetrieval_SetLineToolTip(line);
            if (newline)
            {
                // DFKLOOK No列のセット
            	line.setValue(KEY_LST_NO, lst_FaItemRetrieval.getMaxRows());
                // 入力数の加算
                txt_EnteredLines.setInt(txt_EnteredLines.getInt() + 1);
                //DFKLOOK:ここまで修正
                _lcm_lst_FaItemRetrieval.add(line, true);
            }
            else
            {
                // DFKLOOK No列のセット
                line.setValue(KEY_LST_NO, _lcm_lst_FaItemRetrieval.getEditRow());
                //DFKLOOK:ここまで修正
                _lcm_lst_FaItemRetrieval.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_FaItemRetrieval.resetEditRow();
            _lcm_lst_FaItemRetrieval.resetHighlight();

            // clear.
            btn_Set.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_WorkListPrint.setEnabled(true);
            chk_ShortageListPrint.setEnabled(true);
            pul_Area.setEnabled(false);
            pul_WorkPlace.setEnabled(false);
            pul_Station.setEnabled(false);
            // DFKLOOK start
            // マルチ引当なし対応
            if (!WmsParam.MULTI_ALLOCATE_FLAG)
            {
            	pul_PriorityFlag.setEnabled(false);
            }
            // DFKLOOK end

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
        txt_LotNo.setValue(null);
        txt_TotalStockQty.setValue(null);
        txt_PickingQty.setValue(null);
        // DFKLOOK start
        // マルチ引当なし対応
        if (WmsParam.MULTI_ALLOCATE_FLAG || _lcm_lst_FaItemRetrieval.size() == 0)
        {
            _pdm_pul_PriorityFlag.setSelectedValue(null);
        }
        // DFKLOOK end

    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK メソッド引数追加
    private void btn_Set_Click_Process(String eventSource)
    //DFKLOOK:ここまで修正
            throws Exception
    {
        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message. 設定しますか？
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;
        }
        // DFKLOOK:ここまで修正
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemRetrievalSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaItemRetrievalSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaItemRetrieval.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaItemRetrieval.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                FaItemRetrievalSCHParams lineparam = new FaItemRetrievalSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(FaItemRetrievalSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(FaItemRetrievalSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaItemRetrievalSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(FaItemRetrievalSCHParams.RETRIEVAL_RESULT_QTY, line.getValue(KEY_LST_RETRIEVAL_RESULT_QTY));
//                lineparam.set(FaItemRetrievalSCHParams.TYPE, line.getValue(KEY_LST_TYPE));
                lineparam.set(FaItemRetrievalSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
                lineparam.set(FaItemRetrievalSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
                lineparam.set(FaItemRetrievalSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
                lineparam.set(FaItemRetrievalSCHParams.WORK_LIST_PRINT, chk_WorkListPrint.getValue());
                lineparam.set(FaItemRetrievalSCHParams.SHORTAGE_LIST_PRINT, chk_ShortageListPrint.getValue());
                // DFKLOOK start
                lineparam.set(FaItemRetrievalSCHParams.TYPE, line.getValue(KEY_HIDDEN_TYPE));
                lineparam.set(FaItemRetrievalSCHParams.FUNCTION_ID, viewState.getString(Constants.M_FUNCTIONID_KEY));
                // DFKLOOK end
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // DFKLOOK start
            if (eventSource.equals("btn_Set_Click") && !sch.check(inparams))
            {
            	if(sch.isConfirm())
            	{
            		// ダイアログメッセージ
            		this.setConfirm(sch.getDispMessage(), false, true);
            		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click_SCH");
                    return;
                }
            	message.setMsgResourceKey(sch.getMessage());
            	return;
            }
            // DFKLOOK end
            
            // SCH call.
            if (!sch.startSCH(inparams))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_FaItemRetrieval.resetEditRow();
                _lcm_lst_FaItemRetrieval.resetHighlight();
                _lcm_lst_FaItemRetrieval.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaItemRetrieval.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaItemRetrieval.get(i);
                lst_FaItemRetrieval.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
                // DFKLOOK start
                // 平置エリアが選択されている場合
                if (_pdm_pul_WorkPlace.getSelectedStringValue().startsWith(AsrsInParameter.SELECT_STATION_NONE))
                {
                	// 作業場・ステーションは空白で登録
	                part11List.add("", "");
	                part11List.add("", "");
                }
                else
                {
	                // 作業場はテキストを:で分割を行いステーションNo.を取得する。
	                part11List.add(pul_WorkPlace.getItem(_pdm_pul_WorkPlace.getSelectedIndex())
							.getText().split(":")[0], "");
					part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
                }
                // DFKLOOK end
                
                if (chk_WorkListPrint.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                if (chk_ShortageListPrint.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_RETRIEVAL_RESULT_QTY), "");
                part11List.add(line.getViewString(KEY_HIDDEN_TYPE), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // DFKLOOK start
            if(sch.isStartAsrsWork())
            {
	            // 出庫指示送信へRMIメッセージを使用して出庫要求を行います。
	            SendRequestor req = new SendRequestor();
	            req.retrieval();
            }
            txt_EnteredLines.setInt(0);
            // DFKLOOK end           
            
            // reset editing row.
            _lcm_lst_FaItemRetrieval.resetEditRow();
            _lcm_lst_FaItemRetrieval.resetHighlight();

            // clear.
            _lcm_lst_FaItemRetrieval.clear();
            btn_Set.setEnabled(false);
            btn_AllClear.setEnabled(false);
            chk_WorkListPrint.setEnabled(false);
            chk_ShortageListPrint.setEnabled(false);
            pul_Area.setEnabled(true);
            pul_WorkPlace.setEnabled(true);
            pul_Station.setEnabled(true);
            
            // DFKLOOK start
            // マルチ引当なし対応
            if (!WmsParam.MULTI_ALLOCATE_FLAG)
            {
                pul_PriorityFlag.setEnabled(true);
            }
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

    /**
     *
     * @throws Exception
     */
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_FaItemRetrieval.clear();
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_WorkListPrint.setEnabled(false);
        chk_ShortageListPrint.setEnabled(false);
        pul_Area.setEnabled(true);
        pul_WorkPlace.setEnabled(true);
        pul_Station.setEnabled(true);
        // DFKLOOK start
        // 入力数の初期化
        txt_EnteredLines.setInt(0);
        
        // マルチ引当なし対応
        if (!WmsParam.MULTI_ALLOCATE_FLAG)
        {
            pul_PriorityFlag.setEnabled(true);
        }
        // DFKLOOK end
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Modify_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_FaItemRetrieval.getActiveRow();
        lst_FaItemRetrieval.setCurrentRow(row);
        ListCellLine line = _lcm_lst_FaItemRetrieval.get(row);

        // output display.
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_LotNo.setValue(line.getValue(KEY_LST_LOT_NO));
        txt_PickingQty.setValue(line.getValue(KEY_LST_RETRIEVAL_RESULT_QTY));
        _pdm_pul_PriorityFlag.setSelectedValue(line.getValue(KEY_HIDDEN_TYPE));

        // highlight active row.
        _lcm_lst_FaItemRetrieval.resetHighlight();
        _lcm_lst_FaItemRetrieval.addHighlight(row);
        _lcm_lst_FaItemRetrieval.setEditRow(row);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_FaItemRetrieval.getActiveRow();
        lst_FaItemRetrieval.setCurrentRow(row);

        // reset editing row.
        lst_FaItemRetrieval.removeRow(row);
        _lcm_lst_FaItemRetrieval.resetEditRow();
        _lcm_lst_FaItemRetrieval.resetHighlight();

        // DFKLOOK start
        // 取消ボタンでためうちが0件になったときの処理追加
        if(_lcm_lst_FaItemRetrieval.size() == 0)
        {
        	btn_Set.setEnabled(false);
        	btn_AllClear.setEnabled(false);
        	chk_ShortageListPrint.setEnabled(false);
        	chk_WorkListPrint.setEnabled(false);
        	pul_Area.setEnabled(true);
        	pul_Station.setEnabled(true);
        	pul_WorkPlace.setEnabled(true);
            // DFKLOOK start
            // マルチ引当なし対応
            if (!WmsParam.MULTI_ALLOCATE_FLAG)
            {
                pul_PriorityFlag.setEnabled(true);
            }
            // DFKLOOK end
        }
        else
        {
        	// リストセルのNo.を再セット
        	resetListNo(row);
        }
        txt_EnteredLines.setInt(txt_EnteredLines.getInt() - 1);
        // DFKLOOK end
    }

    // DFKLOOK start メソッド追加
    /**
     * 商品コードテキストに入力されている商品コードで商品名称を検索し、
     * 結果を商品名称テキストにセットします。
     * @throws Exception
     */
    private void getItemName()
            throws Exception
    {
        if (StringUtil.isBlank(txt_ItemCode.getValue()))
        {
            setFocus(txt_ItemName);
            return;
        }
 
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            
            Item item = ItemController.getItemInfo(txt_ItemCode.getText(), WmsParam.DEFAULT_CONSIGNOR_CODE, conn);
 
            if (item == null)
            {
                // 6023021 = 商品コードがマスタに登録されていません。
                message.setMsgResourceKey("6023021");
                setFocus(txt_ItemCode);
                return;
            }
            
            // output display.
            txt_ItemName.setValue(item.getItemName());
 
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
     * リストセルのNo.を再セットします。
     * 
     * @param row 取消行
     */
    private void resetListNo(int row)
    {
        for (int i = row; i <= _lcm_lst_FaItemRetrieval.size(); i++)
        {
            ListCellLine line = _lcm_lst_FaItemRetrieval.get(i);
            line.setValue(KEY_LST_NO, i);
            
            _lcm_lst_FaItemRetrieval.set(i, line);
        }
    }
    
    /**
     * 入力されている商品情報のチェックを行います。<br>
     * 該当商品が存在しない場合は、エラーメッセージを表示します。<br>
     * 入力商品コードと名称が一致しない場合、確認ダイアログを表示します。<br>
     * 上記のダイアログで確認後は、商品名称を補完します。
     * 
     * @param conn データベースコネクション
     * @return チェック結果
     * @throws Exception 
     */
    private boolean checkItem(Connection conn)
            throws Exception
    {
        ItemController itemCon = new ItemController(conn, this.getClass());
        String consignor_code = WmsParam.DEFAULT_CONSIGNOR_CODE;
        String item_code = txt_ItemCode.getText();
        
        if (!_isItemCheck && !txt_ItemName.getText().equals(""))
        {
            if (itemCon.exists(item_code, consignor_code))
            {
                // 入力商品コードと商品名称が一致するかチェック
                String item_name = itemCon.getItemName(item_code, consignor_code);
                if (!item_name.equals(txt_ItemName.getText()))
                {
                    // 一致しない場合は、確認ダイアログを表示する
                    this.setConfirm("MSG-W9112", false, true);
                    viewState.setString(_KEY_CONFIRMSOURCE, "checkItem");
                    return false;
                }
            }
            else
            {
                // 6023021 = 商品コードがマスタに登録されていません。
                message.setMsgResourceKey("6023021");
                return false;
            }
        }
        else
        {
            // 商品情報の補完する
            Item item = ItemController.getItemInfo(item_code, consignor_code, conn);

            if (item == null)
            {
                // 6023021 = 商品コードがマスタに登録されていません。
                message.setMsgResourceKey("6023021");
                return false;
            }
            
            // output display.
            txt_ItemName.setValue(item.getItemName());
        }

        // フラグ初期化
        _isItemCheck = false;
        return true;
    }
    // DFKLOOK end

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
