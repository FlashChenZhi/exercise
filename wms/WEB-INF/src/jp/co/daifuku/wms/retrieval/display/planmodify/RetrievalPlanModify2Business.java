// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.retrieval.display.planmodify;

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
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
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
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanModifySCH;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalPlanModifySCHParams;

/**
 * 出庫予定修正・削除（詳細情報）の画面処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:39:15 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class RetrievalPlanModify2Business
        extends RetrievalPlanModify2
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_RetrievalPlanModifyDelete(HDNIDX_UKEY) */
    private static final ListCellKey KEY_HDNIDX_UKEY = new ListCellKey("HDNIDX_UKEY", new StringCellColumn(), false, false);

    /** lst_RetrievalPlanModifyDelete(HDNIDX_UPDAY) */
    private static final ListCellKey KEY_HDNIDX_UPDAY = new ListCellKey("HDNIDX_UPDAY", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMSS), false, false);

    /** lst_RetrievalPlanModifyDelete(HDNIDX_AREA_NAME) */
    private static final ListCellKey KEY_HDNIDX_AREA_NAME = new ListCellKey("HDNIDX_AREA_NAME", new StringCellColumn(), false, false);

    /** lst_RetrievalPlanModifyDelete(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanModifyDelete(LST_DELETE) */
    private static final ListCellKey KEY_LST_DELETE = new ListCellKey("LST_DELETE", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanModifyDelete(LST_FILE_LINE_NO) */
    private static final ListCellKey KEY_LST_FILE_LINE_NO = new ListCellKey("LST_FILE_LINE_NO", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanModifyDelete(LST_BRANCH_NO) */
    private static final ListCellKey KEY_LST_BRANCH_NO = new ListCellKey("LST_BRANCH_NO", new NumberCellColumn(0), true, false);

    /** lst_RetrievalPlanModifyDelete(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanModifyDelete(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO = new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanModifyDelete(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanModifyDelete(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, true);

    /** lst_RetrievalPlanModifyDelete(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_RetrievalPlanModifyDelete(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, true);

    /** lst_RetrievalPlanModifyDelete(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, true);

    /** lst_RetrievalPlanModifyDelete(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, true);

    /** lst_RetrievalPlanModifyDelete(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, true);

    /** lst_RetrievalPlanModifyDelete(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, true);

    /** lst_RetrievalPlanModifyDelete(LST_PLAN_CASE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_CASE_QTY = new ListCellKey("LST_PLAN_CASE_QTY", new NumberCellColumn(0), true, true);

    /** lst_RetrievalPlanModifyDelete(LST_PLAN_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PLAN_PIECE_QTY = new ListCellKey("LST_PLAN_PIECE_QTY", new NumberCellColumn(0), true, true);

    /** lst_RetrievalPlanModifyDelete(LST_PLAN_AREA_NO) */
    private static final ListCellKey KEY_LST_PLAN_AREA_NO = new ListCellKey("LST_PLAN_AREA_NO", new AreaCellColumn(), true, true);

    /** lst_RetrievalPlanModifyDelete(LST_PLAN_LOCATION_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOCATION_NO = new ListCellKey("LST_PLAN_LOCATION_NO", new LocationCellColumn(), true, true);

    /** lst_RetrievalPlanModifyDelete keys */
    private static final ListCellKey[] LST_RETRIEVALPLANMODIFYDELETE_KEYS = {
        KEY_HDNIDX_UKEY,
        KEY_HDNIDX_UPDAY,
        KEY_HDNIDX_AREA_NAME,
        KEY_LST_MODIFY,
        KEY_LST_DELETE,
        KEY_LST_FILE_LINE_NO,
        KEY_LST_BATCH_NO,
        KEY_LST_ORDER_NO,
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_ITEM_CODE,
        KEY_LST_ENTERING_QTY,
        KEY_LST_JAN,
        KEY_LST_LOT_NO,
        KEY_LST_PLAN_CASE_QTY,
        KEY_LST_PLAN_AREA_NO,
        KEY_LST_BRANCH_NO,
        KEY_LST_CUSTOMER_NAME,
        KEY_LST_ITEM_NAME,
        KEY_LST_ITF,
        KEY_LST_PLAN_PIECE_QTY,
        KEY_LST_PLAN_LOCATION_NO,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_RetrievalArea */
    private WmsAreaPullDownModel _pdm_pul_RetrievalArea;

    /** ListCellModel lst_RetrievalPlanModifyDelete */
    private ListCellModel _lcm_lst_RetrievalPlanModifyDelete;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public RetrievalPlanModify2Business()
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
        // DFKLOOK ここから修正
        if (eventSource.startsWith("btn_ModifySet_Click"))
        {
            btn_ModifySet_Click_Process(eventSource);
        }
        // DFKLOOK ここまで修正
        if (eventSource.equals("btn_Input_Click"))
        {
            // process call.
            btn_Input_Click_Process(false);
        }
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
    public void btn_ModifySet_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ModifySet_Click_Process(null);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllDelete_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllDelete_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RetrievalPlanModifyDelete_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_RetrievalPlanModifyDelete.getActiveCol();

        // choose process.
        if (_lcm_lst_RetrievalPlanModifyDelete.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_RetrievalPlanModifyDelete.getColumnIndex(KEY_LST_DELETE) == activeCol)
        {
            // process call.
            lst_Delete_Click_Process();
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_RetrievalPlanModifyDelete_ColumClick(ActionEvent e)
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
    /**
     * 入力エリアの有効or無効をセットします。<BR>
     * 
     * @param enable True：有効  False：無効
     */
    protected void enableInputArea(boolean enable) 
    {
        if(!enable) 
        {
            txt_LineNo.setValue(null);
            txt_BranchNo.setValue(null);
            txt_BatchNo.setValue(null);
            txt_OrderNo.setValue(null);
            txt_CustomerCode.setValue(null);
            txt_CustomerName.setValue(null);
            txt_ItemCode.setValue(null);
            txt_ItemName.setValue(null);
            txt_EnteringQty.setValue(null);
            txt_JanCode.setValue(null);
            txt_CaseITF.setValue(null);
            txt_LotNo.setValue(null);
            txt_PlanCaseQty.setValue(null);
            txt_PlanPieceQty.setValue(null);
            _pdm_pul_RetrievalArea.setSelectedValue(null);
            txt_RetrievalPlanLocation.setValue(null);
        }
        
        btn_Input.setEnabled(enable);
        btn_Clear.setEnabled(enable);

        if (!this.getViewState().getBoolean(ViewStateKeys.MASTER))
        {
            if(StringUtil.isBlank(txt_CustomerCode.getText())) 
            {
                // マスタ管理なしで、出荷先コードが登録されていない場合、出荷先名は入力不可
                txt_CustomerName.setReadOnly(enable);
            }
        }
        
        if(!txt_CustomerName.getReadOnly()) 
        {
            setFocus(txt_CustomerName);
        }
        else if (!txt_ItemName.getReadOnly())
        {
            setFocus(txt_ItemName);
        }
        else
        {
            setFocus(txt_LotNo);
        }
    }
    // DFKLOOK ここまで修正 メソッド追加
    
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

        // initialize lst_RetrievalPlanModifyDelete.
        _lcm_lst_RetrievalPlanModifyDelete = new ListCellModel(lst_RetrievalPlanModifyDelete, LST_RETRIEVALPLANMODIFYDELETE_KEYS, locale);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_DELETE, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_FILE_LINE_NO, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_BRANCH_NO, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_BATCH_NO, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_ORDER_NO, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_ITF, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_PLAN_CASE_QTY, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_PLAN_PIECE_QTY, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_PLAN_AREA_NO, true);
        _lcm_lst_RetrievalPlanModifyDelete.setToolTipVisible(KEY_LST_PLAN_LOCATION_NO, true);

    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
        // DFKLOOK ここから修正 在庫パッケージごとに分岐
        // 在庫パッケージなしの場合のみエリアプルダウンを使用
        if (!this.getViewState().getBoolean(ViewStateKeys.STOCKPACK))
        {
            Connection conn = null;
            try
            {
                // open connection.
                conn = ConnectionManager.getRequestConnection(this);
                
                // load pul_RetrievalArea.
                _pdm_pul_RetrievalArea.init(conn, AreaType.FLOOR_AND_TEMP_AND_RECEIVE, StationType.ALL, "", false);
    
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
        else 
        {
            // エリアプルダウン無効
            pul_RetrievalArea.setEnabled(false);
        }
        // DFKLOOK ここまで修正 在庫パッケージごとに分岐
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
    private void lst_RetrievalPlanModifyDelete_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0115", KEY_LST_CUSTOMER_NAME);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0035", KEY_LST_LOT_NO);
        line.addToolTip("LBL-W0183", KEY_LST_PLAN_CASE_QTY);
        line.addToolTip("LBL-W0184", KEY_LST_PLAN_PIECE_QTY);
        line.addToolTip("LBL-W0116", KEY_LST_PLAN_AREA_NO);
        // DFKLOOK ここから修正
        // エリア名称
        line.addToolTip("LBL-W0229", KEY_HDNIDX_AREA_NAME);
        // DFKLOOK ここまで修正
        line.addToolTip("LBL-W0124", KEY_LST_PLAN_LOCATION_NO);
    }
    
    /**
    *
    * @throws Exception
    */
   private void page_Initialize_Process()
           throws Exception
   {
       // DFKLOOK ここから
       // マスタ管理の有無で、フォーカスセット
       if (this.getViewState().getBoolean(ViewStateKeys.MASTER))
       {   
           // set focus.
           setFocus(txt_LotNo);
       }
       else if (!txt_CustomerName.getReadOnly())
       {           
           // set focus.
           setFocus(txt_CustomerName);
       }
       else
       {
           setFocus(txt_ItemName);
       }
       // DFKLOOK ここまで
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
        RetrievalPlanModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalPlanModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalPlanModifySCHParams inparam = new RetrievalPlanModifySCHParams();
            inparam.set(RetrievalPlanModifySCHParams.PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
            inparam.set(RetrievalPlanModifySCHParams.SLIP_NUMBER, viewState.getObject(ViewStateKeys.SLIP_NUMBER));
            inparam.set(RetrievalPlanModifySCHParams.LINE_NO, viewState.getObject(ViewStateKeys.LINE_NO));
            inparam.set(RetrievalPlanModifySCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_RetrievalPlanModifyDelete.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalPlanModifyDelete.getNewLine();
                line.setValue(KEY_HDNIDX_UKEY, outparam.get(RetrievalPlanModifySCHParams.PLAN_U_KEY));
                line.setValue(KEY_HDNIDX_UPDAY, outparam.get(RetrievalPlanModifySCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_HDNIDX_AREA_NAME, outparam.get(RetrievalPlanModifySCHParams.AREA_NAME));
                line.setValue(KEY_LST_FILE_LINE_NO, outparam.get(RetrievalPlanModifySCHParams.FILE_LINE_NO));
                line.setValue(KEY_LST_BRANCH_NO, outparam.get(RetrievalPlanModifySCHParams.BRANCH_NO));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(RetrievalPlanModifySCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(RetrievalPlanModifySCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalPlanModifySCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalPlanModifySCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(RetrievalPlanModifySCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(RetrievalPlanModifySCHParams.ITEM_NAME));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(RetrievalPlanModifySCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_JAN, outparam.get(RetrievalPlanModifySCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(RetrievalPlanModifySCHParams.ITF));
                line.setValue(KEY_LST_LOT_NO, outparam.get(RetrievalPlanModifySCHParams.LOT_NO));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(RetrievalPlanModifySCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(RetrievalPlanModifySCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(RetrievalPlanModifySCHParams.PLAN_AREA_NO));
                line.setValue(KEY_LST_PLAN_LOCATION_NO, outparam.get(RetrievalPlanModifySCHParams.PLAN_LOCATION_NO));
                txt_R_DateFlat.setValue(viewState.getObject(ViewStateKeys.PLAN_DAY));
                lbl_In_TicketNo.setValue(viewState.getObject(ViewStateKeys.SLIP_NUMBER));
                lst_RetrievalPlanModifyDelete_SetLineToolTip(line);
                _lcm_lst_RetrievalPlanModifyDelete.add(line);
            }

            // clear.
            txt_LineNo.setReadOnly(true);
            txt_BranchNo.setReadOnly(true);
            txt_BatchNo.setReadOnly(true);
            txt_OrderNo.setReadOnly(true);
            txt_CustomerCode.setReadOnly(true);
            txt_ItemCode.setReadOnly(true);
            btn_Input.setEnabled(false);
            btn_Clear.setEnabled(false);

            // DFKLOOK ここから修正
            // 在庫パッケージの有無で、出庫エリア、出庫棚の有効無効をセット
            if (!this.getViewState().getBoolean(ViewStateKeys.STOCKPACK))
            {
                // 棚テキストボックス有効
                txt_RetrievalPlanLocation.setReadOnly(false);
            }
            else
            {
                // 棚テキストボックス無効
                txt_RetrievalPlanLocation.setReadOnly(true);
            }
            
            // マスタ管理の有無で、入力エリアの有効無効をセット
            if (this.getViewState().getBoolean(ViewStateKeys.MASTER))
            {
                // マスタ管理ありの場合
                txt_CustomerName.setReadOnly(true);
                txt_ItemName.setReadOnly(true);
                txt_EnteringQty.setReadOnly(true);
                txt_JanCode.setReadOnly(true);
                txt_CaseITF.setReadOnly(true);
            }
            else
            {
                // マスタ管理無しの場合
                txt_CustomerName.setReadOnly(false);
                txt_ItemName.setReadOnly(false);
                txt_EnteringQty.setReadOnly(false);
                txt_JanCode.setReadOnly(false);
                txt_CaseITF.setReadOnly(false);
            }
            // DFKLOOK ここまで修正
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
            forward("/retrieval/planmodify/RetrievalPlanModify.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
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
        txt_CustomerName.validate(this, false);
        txt_ItemName.validate(this, false);
        txt_EnteringQty.validate(this, false);
        txt_JanCode.validate(this, false);
        txt_CaseITF.validate(this, false);
        txt_LotNo.validate(this, false);
        txt_PlanCaseQty.validate(this, false);
        txt_PlanPieceQty.validate(this, false);

        // DFKLOOK ここから修正
        if(this.getViewState().getBoolean(ViewStateKeys.STOCKPACK))
        {
            txt_RetrievalPlanLocation.validate(this, false);
        }
        else 
        {
            txt_RetrievalPlanLocation.validate(this, true);
            pul_RetrievalArea.validate(this, true);
        }
        
        // 在庫ケース数と在庫ピース数が0の場合
        if (txt_PlanCaseQty.getInt() == 0 && txt_PlanPieceQty.getInt() == 0)
        {
            // 6023035=ケース数またはピース数には1以上の値を入力してください。
            message.setMsgResourceKey("6023035");
            return;
        }

        // ケース入数が0かつ在庫ケース数が1以上の場合
        if (txt_EnteringQty.getInt() == 0 && txt_PlanCaseQty.getInt() >= 1)
        {
            // 6023036=ケース入数が0の場合、ケース数は入力できません。
            message.setMsgResourceKey("6023036");
            return;
        }

        // オーバーフローチェック
        long allQty = (long)txt_EnteringQty.getInt() * (long)txt_PlanCaseQty.getInt() + (long)txt_PlanPieceQty.getInt();
        if (allQty > WmsParam.MAX_TOTAL_QTY)
        {
            // 6023184=予定数には作業上限数{0}以下の値を入力してしてください。
            message.setMsgResourceKey(WmsMessageFormatter.format(6023184,
                    WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY)));
            return;
        }
        // DFKLOOK ここまで修正
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalPlanModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalPlanModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalPlanModifySCHParams inparam = new RetrievalPlanModifySCHParams();
            inparam.set(RetrievalPlanModifySCHParams.CUSTOMER_CODE, txt_CustomerCode.getValue());
            inparam.set(RetrievalPlanModifySCHParams.CUSTOMER_NAME, txt_CustomerName.getValue());
            inparam.set(RetrievalPlanModifySCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(RetrievalPlanModifySCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(RetrievalPlanModifySCHParams.ENTERING_QTY, txt_EnteringQty.getValue());
            inparam.set(RetrievalPlanModifySCHParams.JAN_CODE, txt_JanCode.getValue());
            inparam.set(RetrievalPlanModifySCHParams.CASE_ITF, txt_CaseITF.getValue());
            inparam.set(RetrievalPlanModifySCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(RetrievalPlanModifySCHParams.PLAN_CASE_QTY, txt_PlanCaseQty.getValue());
            inparam.set(RetrievalPlanModifySCHParams.PLAN_PIECE_QTY, txt_PlanPieceQty.getValue());
            inparam.set(RetrievalPlanModifySCHParams.RETRIEVAL_AREA, _pdm_pul_RetrievalArea.getSelectedValue());
            inparam.set(RetrievalPlanModifySCHParams.RETRIEVAL_PLAN_LOCATION, txt_RetrievalPlanLocation.getValue());
            inparam.set(RetrievalPlanModifySCHParams.PLAN_U_KEY, viewState.getObject(ViewStateKeys.PLAN_U_KEY));
            inparam.set(RetrievalPlanModifySCHParams.LAST_UPDATE_DATE, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            inparam.set(RetrievalPlanModifySCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();

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
            int editRow = _lcm_lst_RetrievalPlanModifyDelete.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_RetrievalPlanModifyDelete.getNewLine()
                                        : _lcm_lst_RetrievalPlanModifyDelete.get(editRow);
            line.setValue(KEY_HDNIDX_UKEY, viewState.getObject(ViewStateKeys.PLAN_U_KEY));
            line.setValue(KEY_HDNIDX_UPDAY, viewState.getObject(ViewStateKeys.LAST_UPDATE_DATE));
            line.setValue(KEY_HDNIDX_AREA_NAME, _pdm_pul_RetrievalArea.getSelectedValue());
            line.setValue(KEY_LST_FILE_LINE_NO, txt_LineNo.getValue());
            line.setValue(KEY_LST_BRANCH_NO, txt_BranchNo.getValue());
            line.setValue(KEY_LST_BATCH_NO, txt_BatchNo.getValue());
            line.setValue(KEY_LST_ORDER_NO, txt_OrderNo.getValue());
            line.setValue(KEY_LST_CUSTOMER_CODE, txt_CustomerCode.getValue());
            line.setValue(KEY_LST_CUSTOMER_NAME, txt_CustomerName.getValue());
            line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
            line.setValue(KEY_LST_ITEM_NAME, txt_ItemName.getValue());
            line.setValue(KEY_LST_ENTERING_QTY, txt_EnteringQty.getValue());
            line.setValue(KEY_LST_JAN, txt_JanCode.getValue());
            line.setValue(KEY_LST_ITF, txt_CaseITF.getValue());
            line.setValue(KEY_LST_LOT_NO, txt_LotNo.getValue());
            line.setValue(KEY_LST_PLAN_CASE_QTY, txt_PlanCaseQty.getValue());
            line.setValue(KEY_LST_PLAN_PIECE_QTY, txt_PlanPieceQty.getValue());
            line.setValue(KEY_LST_PLAN_AREA_NO, _pdm_pul_RetrievalArea.getSelectedValue());
            line.setValue(KEY_LST_PLAN_LOCATION_NO, txt_RetrievalPlanLocation.getValue());

            // add new row or update editing row.
            lst_RetrievalPlanModifyDelete_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_RetrievalPlanModifyDelete.add(line, true);
            }
            else
            {
                _lcm_lst_RetrievalPlanModifyDelete.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_RetrievalPlanModifyDelete.resetEditRow();
            _lcm_lst_RetrievalPlanModifyDelete.resetHighlight();

            // clear.
            btn_Input.setEnabled(false);
            btn_Clear.setEnabled(false);
            txt_LineNo.setValue(null);
            txt_BranchNo.setValue(null);
            txt_BatchNo.setValue(null);
            txt_OrderNo.setValue(null);
            txt_CustomerCode.setValue(null);
            txt_CustomerName.setValue(null);
            txt_ItemCode.setValue(null);
            txt_ItemName.setValue(null);
            txt_EnteringQty.setValue(null);
            txt_JanCode.setValue(null);
            txt_CaseITF.setValue(null);
            txt_LotNo.setValue(null);
            txt_PlanCaseQty.setValue(null);
            txt_PlanPieceQty.setValue(null);
            _pdm_pul_RetrievalArea.setSelectedValue(null);
            txt_RetrievalPlanLocation.setValue(null);

            // DFKLOOK ここから修正
            enableInputArea(false);
            // DFKLOOK ここまで修正
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
        txt_LotNo.setValue(null);
        txt_PlanCaseQty.setValue(null);
        txt_PlanPieceQty.setValue(null);

        // DFKLOOK ここから修正
        // マスタ管理なしの場合、クリア箇所追加
        if(!this.getViewState().getBoolean(ViewStateKeys.MASTER))
        {
            txt_CustomerName.setValue(null);
            txt_ItemName.setValue(null);
            txt_EnteringQty.setValue(null);
            txt_JanCode.setValue(null);
            txt_CaseITF.setValue(null);
            
            if(!txt_CustomerName.getReadOnly())
            {
                setFocus(txt_CustomerName);
            }
            else 
            {
                setFocus(txt_ItemName);
            }
            
        }
        else 
        {
            setFocus(txt_LotNo);
        }
        
        // 在庫管理なしの場合、クリア箇所追加
        if(!this.getViewState().getBoolean(ViewStateKeys.STOCKPACK)) 
        {
            pul_RetrievalArea.setSelectedIndex(0);
            txt_RetrievalPlanLocation.setValue(null);
        }
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    // DFKLOOK ここから修正
    private void btn_ModifySet_Click_Process(String eventSource)
            throws Exception
   // DFKLOOK ここまで修正
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_RetrievalPlanModifyDelete.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_RetrievalPlanModifyDelete.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            // DFKLOOK ここから修正
            enableInputArea(false);
            _lcm_lst_RetrievalPlanModifyDelete.resetEditRow();
            _lcm_lst_RetrievalPlanModifyDelete.resetHighlight();
            message.setMsgResourceKey("6003013");
            // DFKLOOK ここまで修正
            return;
        }
        // DFKLOOK ここから修正
        if (StringUtil.isBlank(eventSource))
        {
            // show confirm message=修正登録しますか?
            this.setConfirm("MSG-W0013", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_ModifySet_Click");
            return;         
        }
        // DFKLOOK ここまで修正       
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalPlanModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalPlanModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_RetrievalPlanModifyDelete.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_RetrievalPlanModifyDelete.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                RetrievalPlanModifySCHParams lineparam = new RetrievalPlanModifySCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(RetrievalPlanModifySCHParams.PLAN_U_KEY, line.getValue(KEY_HDNIDX_UKEY));
                lineparam.set(RetrievalPlanModifySCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HDNIDX_UPDAY));
                lineparam.set(RetrievalPlanModifySCHParams.AREA_NAME, line.getValue(KEY_HDNIDX_AREA_NAME));
                lineparam.set(RetrievalPlanModifySCHParams.FILE_LINE_NO, line.getValue(KEY_LST_FILE_LINE_NO));
                lineparam.set(RetrievalPlanModifySCHParams.BRANCH_NO, line.getValue(KEY_LST_BRANCH_NO));
                lineparam.set(RetrievalPlanModifySCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
                lineparam.set(RetrievalPlanModifySCHParams.ORDER_NO, line.getValue(KEY_LST_ORDER_NO));
                lineparam.set(RetrievalPlanModifySCHParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
                lineparam.set(RetrievalPlanModifySCHParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
                lineparam.set(RetrievalPlanModifySCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(RetrievalPlanModifySCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(RetrievalPlanModifySCHParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
                lineparam.set(RetrievalPlanModifySCHParams.JAN, line.getValue(KEY_LST_JAN));
                lineparam.set(RetrievalPlanModifySCHParams.ITF, line.getValue(KEY_LST_ITF));
                lineparam.set(RetrievalPlanModifySCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(RetrievalPlanModifySCHParams.PLAN_CASE_QTY, line.getValue(KEY_LST_PLAN_CASE_QTY));
                lineparam.set(RetrievalPlanModifySCHParams.PLAN_PIECE_QTY, line.getValue(KEY_LST_PLAN_PIECE_QTY));
                lineparam.set(RetrievalPlanModifySCHParams.PLAN_AREA_NO, line.getValue(KEY_LST_PLAN_AREA_NO));
                lineparam.set(RetrievalPlanModifySCHParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_PLAN_LOCATION_NO));
                lineparam.set(RetrievalPlanModifySCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
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

                // DFKLOOK ここから修正
                // 修正中を解除
                enableInputArea(false);
                // DFKLOOK ここまで修正
                
                // reset editing row or highlighting error row.
                _lcm_lst_RetrievalPlanModifyDelete.resetEditRow();
                _lcm_lst_RetrievalPlanModifyDelete.resetHighlight();
                _lcm_lst_RetrievalPlanModifyDelete.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_RetrievalPlanModifyDelete.size(); i++)
            {
                ListCellLine line = _lcm_lst_RetrievalPlanModifyDelete.get(i);
                lst_RetrievalPlanModifyDelete.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(txt_R_DateFlat.getStringValue(), "");
                part11List.add(lbl_In_TicketNo.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_FILE_LINE_NO), "");
                part11List.add(line.getViewString(KEY_LST_BRANCH_NO), "");
                part11List.add(line.getViewString(KEY_LST_BATCH_NO), "");
                part11List.add(line.getViewString(KEY_LST_ORDER_NO), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "");
                part11List.add(line.getViewString(KEY_LST_JAN), "");
                part11List.add(line.getViewString(KEY_LST_ITF), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOCATION_NO), "");
                part11List.add(line.getStringValue(_lcm_lst_RetrievalPlanModifyDelete.getColumnKey(
                        _lcm_lst_RetrievalPlanModifyDelete.getColumnIndex(KEY_LST_LOT_NO)).getShadowKey()), "");
                part11List.add(line.getStringValue(_lcm_lst_RetrievalPlanModifyDelete.getColumnKey(
                        _lcm_lst_RetrievalPlanModifyDelete.getColumnIndex(KEY_LST_PLAN_CASE_QTY)).getShadowKey()), "");
                part11List.add(line.getStringValue(_lcm_lst_RetrievalPlanModifyDelete.getColumnKey(
                        _lcm_lst_RetrievalPlanModifyDelete.getColumnIndex(KEY_LST_PLAN_PIECE_QTY)).getShadowKey()), "");
                part11List.add(line.getStringValue(_lcm_lst_RetrievalPlanModifyDelete.getColumnKey(
                        _lcm_lst_RetrievalPlanModifyDelete.getColumnIndex(KEY_LST_PLAN_AREA_NO)).getShadowKey()), "");
                part11List.add(line.getStringValue(_lcm_lst_RetrievalPlanModifyDelete.getColumnKey(
                        _lcm_lst_RetrievalPlanModifyDelete.getColumnIndex(KEY_LST_PLAN_LOCATION_NO)).getShadowKey()),
                        "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY),
                        part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_RetrievalPlanModifyDelete.resetEditRow();
            _lcm_lst_RetrievalPlanModifyDelete.resetHighlight();

            // set input parameters.
            RetrievalPlanModifySCHParams inparam = new RetrievalPlanModifySCHParams();
            inparam.set(RetrievalPlanModifySCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(RetrievalPlanModifySCHParams.LINE_NO, viewState.getObject(ViewStateKeys.LINE_NO));
            inparam.set(RetrievalPlanModifySCHParams.PLAN_DAY, viewState.getObject(ViewStateKeys.PLAN_DAY));
            inparam.set(RetrievalPlanModifySCHParams.SLIP_NUMBER, viewState.getObject(ViewStateKeys.SLIP_NUMBER));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_RetrievalPlanModifyDelete.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_RetrievalPlanModifyDelete.getNewLine();
                line.setValue(KEY_HDNIDX_UKEY, outparam.get(RetrievalPlanModifySCHParams.PLAN_U_KEY));
                line.setValue(KEY_HDNIDX_UPDAY, outparam.get(RetrievalPlanModifySCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_HDNIDX_AREA_NAME, outparam.get(RetrievalPlanModifySCHParams.AREA_NAME));
                line.setValue(KEY_LST_FILE_LINE_NO, outparam.get(RetrievalPlanModifySCHParams.FILE_LINE_NO));
                line.setValue(KEY_LST_BRANCH_NO, outparam.get(RetrievalPlanModifySCHParams.BRANCH_NO));
                line.setValue(KEY_LST_BATCH_NO, outparam.get(RetrievalPlanModifySCHParams.BATCH_NO));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(RetrievalPlanModifySCHParams.ORDER_NO));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(RetrievalPlanModifySCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(RetrievalPlanModifySCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(RetrievalPlanModifySCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(RetrievalPlanModifySCHParams.ITEM_NAME));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(RetrievalPlanModifySCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_JAN, outparam.get(RetrievalPlanModifySCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(RetrievalPlanModifySCHParams.ITF));
                line.setValue(KEY_LST_LOT_NO, outparam.get(RetrievalPlanModifySCHParams.LOT_NO));
                line.setValue(KEY_LST_PLAN_CASE_QTY, outparam.get(RetrievalPlanModifySCHParams.PLAN_CASE_QTY));
                line.setValue(KEY_LST_PLAN_PIECE_QTY, outparam.get(RetrievalPlanModifySCHParams.PLAN_PIECE_QTY));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(RetrievalPlanModifySCHParams.PLAN_AREA_NO));
                line.setValue(KEY_LST_PLAN_LOCATION_NO, outparam.get(RetrievalPlanModifySCHParams.PLAN_LOCATION_NO));
                lst_RetrievalPlanModifyDelete_SetLineToolTip(line);
                _lcm_lst_RetrievalPlanModifyDelete.add(line);
            }

            // DFKLOOK ここから修正
            // clear.
            enableInputArea(false);
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

    /**
     *
     * @throws Exception
     */
    private void btn_AllDelete_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalPlanModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalPlanModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_RetrievalPlanModifyDelete.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_RetrievalPlanModifyDelete.get(i);
                RetrievalPlanModifySCHParams lineparam = new RetrievalPlanModifySCHParams();
                lineparam.setProcessFlag(ProcessFlag.DELETE_ALL);
                lineparam.setRowIndex(i);
                lineparam.set(RetrievalPlanModifySCHParams.PLAN_U_KEY, line.getValue(KEY_HDNIDX_UKEY));
                lineparam.set(RetrievalPlanModifySCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HDNIDX_UPDAY));
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

                // DFKLOOK ここから修正
                // 修正中を解除
                enableInputArea(false);
                // DFKLOOK ここまで修正
                
                // reset editing row or highlighting error row.
                _lcm_lst_RetrievalPlanModifyDelete.resetEditRow();
                _lcm_lst_RetrievalPlanModifyDelete.resetHighlight();
                _lcm_lst_RetrievalPlanModifyDelete.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_RetrievalPlanModifyDelete.size(); i++)
            {
                ListCellLine line = _lcm_lst_RetrievalPlanModifyDelete.get(i);
                lst_RetrievalPlanModifyDelete.setCurrentRow(i);


                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(txt_R_DateFlat.getStringValue(), "");
                part11List.add(lbl_In_TicketNo.getStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_FILE_LINE_NO), "");
                part11List.add(line.getViewString(KEY_LST_BRANCH_NO), "");
                part11List.add(line.getViewString(KEY_LST_BATCH_NO), "");
                part11List.add(line.getViewString(KEY_LST_ORDER_NO), "");
                part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "");
                part11List.add(line.getViewString(KEY_LST_JAN), "");
                part11List.add(line.getViewString(KEY_LST_ITF), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_PLAN_LOCATION_NO), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_RetrievalPlanModifyDelete.resetEditRow();
            _lcm_lst_RetrievalPlanModifyDelete.resetHighlight();

            // clear.
            _lcm_lst_RetrievalPlanModifyDelete.clear();
            btn_ModifySet.setEnabled(false);
            btn_AllDelete.setEnabled(false);
            btn_Input.setEnabled(false);
            btn_Clear.setEnabled(false);

            // DFKLOOK ここから修正
            enableInputArea(false);
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

    /**
     *
     * @throws Exception
     */
    private void lst_Modify_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_RetrievalPlanModifyDelete.getActiveRow();
        lst_RetrievalPlanModifyDelete.setCurrentRow(row);
        ListCellLine line = _lcm_lst_RetrievalPlanModifyDelete.get(row);

        // output display.
        viewState.setObject(ViewStateKeys.PLAN_U_KEY, line.getValue(KEY_HDNIDX_UKEY));
        viewState.setObject(ViewStateKeys.LAST_UPDATE_DATE, line.getValue(KEY_HDNIDX_UPDAY));
        txt_LineNo.setValue(line.getValue(KEY_LST_FILE_LINE_NO));
        txt_BranchNo.setValue(line.getValue(KEY_LST_BRANCH_NO));
        txt_BatchNo.setValue(line.getValue(KEY_LST_BATCH_NO));
        txt_OrderNo.setValue(line.getValue(KEY_LST_ORDER_NO));
        txt_CustomerCode.setValue(line.getValue(KEY_LST_CUSTOMER_CODE));
        txt_CustomerName.setValue(line.getValue(KEY_LST_CUSTOMER_NAME));
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_EnteringQty.setValue(line.getValue(KEY_LST_ENTERING_QTY));
        txt_PlanCaseQty.setValue(line.getValue(KEY_LST_PLAN_CASE_QTY));
        txt_PlanPieceQty.setValue(line.getValue(KEY_LST_PLAN_PIECE_QTY));
        txt_LotNo.setValue(line.getValue(KEY_LST_LOT_NO));
        txt_JanCode.setValue(line.getValue(KEY_LST_JAN));
        txt_CaseITF.setValue(line.getValue(KEY_LST_ITF));
        _pdm_pul_RetrievalArea.setSelectedValue(line.getValue(KEY_LST_PLAN_AREA_NO));
        txt_RetrievalPlanLocation.setValue(line.getValue(KEY_LST_PLAN_LOCATION_NO));

        // highlight active row.
        _lcm_lst_RetrievalPlanModifyDelete.resetHighlight();
        _lcm_lst_RetrievalPlanModifyDelete.addHighlight(row);
        _lcm_lst_RetrievalPlanModifyDelete.setEditRow(row);

        // DFKLOOK ここから修正
        enableInputArea(true);
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Delete_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_RetrievalPlanModifyDelete.getActiveRow();
        lst_RetrievalPlanModifyDelete.setCurrentRow(row);
        ListCellLine line = _lcm_lst_RetrievalPlanModifyDelete.get(row);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        RetrievalPlanModifySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new RetrievalPlanModifySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            RetrievalPlanModifySCHParams inparam = new RetrievalPlanModifySCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.setRowIndex(row);
            inparam.set(RetrievalPlanModifySCHParams.PLAN_U_KEY, line.getValue(KEY_HDNIDX_UKEY));
            inparam.set(RetrievalPlanModifySCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HDNIDX_UPDAY));

            // SCH call.
            if (!sch.check(inparam))
            {
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                
                // DFKLOOK ここから修正 修正中を解除
                enableInputArea(false);
                // DFKLOOK ここまで修正 修正中を解除

                // reset editing row or highlighting error row.
                _lcm_lst_RetrievalPlanModifyDelete.resetEditRow();
                _lcm_lst_RetrievalPlanModifyDelete.resetHighlight();
                _lcm_lst_RetrievalPlanModifyDelete.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_R_DateFlat.getStringValue(), "");
            part11List.add(lbl_In_TicketNo.getStringValue(), "");
            part11List.add(line.getViewString(KEY_LST_FILE_LINE_NO), "");
            part11List.add(line.getViewString(KEY_LST_BRANCH_NO), "");
            part11List.add(line.getViewString(KEY_LST_BATCH_NO), "");
            part11List.add(line.getViewString(KEY_LST_ORDER_NO), "");
            part11List.add(line.getViewString(KEY_LST_CUSTOMER_CODE), "");
            part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
            part11List.add(line.getViewString(KEY_LST_ENTERING_QTY), "");
            part11List.add(line.getViewString(KEY_LST_JAN), "");
            part11List.add(line.getViewString(KEY_LST_ITF), "");
            part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
            part11List.add(line.getViewString(KEY_LST_PLAN_CASE_QTY), "");
            part11List.add(line.getViewString(KEY_LST_PLAN_PIECE_QTY), "");
            part11List.add(line.getViewString(KEY_LST_PLAN_AREA_NO), "");
            part11List.add(line.getViewString(KEY_LST_PLAN_LOCATION_NO), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            lst_RetrievalPlanModifyDelete.removeRow(row);
            _lcm_lst_RetrievalPlanModifyDelete.resetEditRow();
            _lcm_lst_RetrievalPlanModifyDelete.resetHighlight();

            // DFKLOOK ここから修正
            enableInputArea(false);
            
            // タメうちが0件になった場合、修正登録、全削除ボタンを無効
            if(lst_RetrievalPlanModifyDelete.getMaxRows() <= 1) 
            {
                btn_ModifySet.setEnabled(false);
                btn_AllDelete.setEnabled(false);
            }
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
