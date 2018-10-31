// $Id: InventoryInputBusiness.java 7828 2010-04-16 10:10:58Z shibamoto $
package jp.co.daifuku.wms.inventorychk.display.input;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
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
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.location.SuperLocationHolder;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.InventSettingController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.inventorychk.dasch.InventoryCheckDASCH;
import jp.co.daifuku.wms.inventorychk.dasch.InventoryCheckDASCHParams;
import jp.co.daifuku.wms.inventorychk.display.dataadd.InventoryDataAddParams;
import jp.co.daifuku.wms.inventorychk.exporter.InventoryCheckListParams;
import jp.co.daifuku.wms.inventorychk.listbox.jobno.LstInventoryJobNoParams;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryInParameter;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryInputSCH;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryInputSCHParams;

/**
 * 棚卸結果入力の画面処理を行います。
 * 
 * @version $Revision: 7828 $, $Date: 2010-04-16 19:10:58 +0900 (金, 16 4 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class InventoryInputBusiness
        extends InventoryInput
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_InventoryResultInput(HIDDEN_JOBNO) */
    private static final ListCellKey KEY_HIDDEN_JOBNO = new ListCellKey("HIDDEN_JOBNO", new StringCellColumn(), false, false);

    /** lst_InventoryResultInput(HIDDEN_STATUSFLAG) */
    private static final ListCellKey KEY_HIDDEN_STATUSFLAG = new ListCellKey("HIDDEN_STATUSFLAG", new StringCellColumn(), false, false);

    /** lst_InventoryResultInput(HIDDEN_INVENTCASEQTY) */
    private static final ListCellKey KEY_HIDDEN_INVENTCASEQTY = new ListCellKey("HIDDEN_INVENTCASEQTY", new NumberCellColumn(0), false, false);

    /** lst_InventoryResultInput(HIDDEN_INVENTPIECEQTY) */
    private static final ListCellKey KEY_HIDDEN_INVENTPIECEQTY = new ListCellKey("HIDDEN_INVENTPIECEQTY", new NumberCellColumn(0), false, false);

    /** lst_InventoryResultInput(HIDDEN_NEWDATE_FLAG) */
    private static final ListCellKey KEY_HIDDEN_NEWDATE_FLAG = new ListCellKey("HIDDEN_NEWDATE_FLAG", new StringCellColumn(), false, false);

    /** lst_InventoryResultInput(LST_CYCLE_COUNT) */
    private static final ListCellKey KEY_LST_CYCLE_COUNT = new ListCellKey("LST_CYCLE_COUNT", new CheckBoxColumn(), true, true);

    /** lst_InventoryResultInput(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_DELETE) */
    private static final ListCellKey KEY_LST_DELETE = new ListCellKey("LST_DELETE", new StringCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS = new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_AREA) */
    private static final ListCellKey KEY_LST_AREA = new ListCellKey("LST_AREA", new AreaCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_LOCATION) */
    private static final ListCellKey KEY_LST_LOCATION = new ListCellKey("LST_LOCATION", new LocationCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_LOT) */
    private static final ListCellKey KEY_LST_LOT = new ListCellKey("LST_LOT", new StringCellColumn(), true, false);

    /** lst_InventoryResultInput(LST_CASE_PACK) */
    private static final ListCellKey KEY_LST_CASE_PACK = new ListCellKey("LST_CASE_PACK", new NumberCellColumn(0), true, false);

    /** lst_InventoryResultInput(LST_STOCK_CASE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_CASE_QTY = new ListCellKey("LST_STOCK_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_InventoryResultInput(LST_STOCK_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_PIECE_QTY = new ListCellKey("LST_STOCK_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_InventoryResultInput(LST_CYCLE_COUNT_CASE_QTY) */
    private static final ListCellKey KEY_LST_CYCLE_COUNT_CASE_QTY = new ListCellKey("LST_CYCLE_COUNT_CASE_QTY", new NumberCellColumn(0), true, true);

    /** lst_InventoryResultInput(LST_CYCLE_COUNT_PIECE_QTY) */
    private static final ListCellKey KEY_LST_CYCLE_COUNT_PIECE_QTY = new ListCellKey("LST_CYCLE_COUNT_PIECE_QTY", new NumberCellColumn(0), true, true);

    /** lst_InventoryResultInput keys */
    private static final ListCellKey[] LST_INVENTORYRESULTINPUT_KEYS = {
        KEY_HIDDEN_JOBNO,
        KEY_HIDDEN_STATUSFLAG,
        KEY_HIDDEN_INVENTCASEQTY,
        KEY_HIDDEN_INVENTPIECEQTY,
        KEY_HIDDEN_NEWDATE_FLAG,
        KEY_LST_CYCLE_COUNT,
        KEY_LST_CANCEL,
        KEY_LST_DELETE,
        KEY_LST_STATUS,
        KEY_LST_AREA,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT,
        KEY_LST_CASE_PACK,
        KEY_LST_STOCK_CASE_QTY,
        KEY_LST_CYCLE_COUNT_CASE_QTY,
        KEY_LST_LOCATION,
        KEY_LST_ITEM_NAME,
        KEY_LST_STOCK_PIECE_QTY,
        KEY_LST_CYCLE_COUNT_PIECE_QTY,
    };

    // DFKLOOK ここから修正
    /**
     * HIDDEN項目用：リスト作業No
     */
    protected static final int HIDDEN_JOBNO = 0;

    /**
     * HEDDEN項目用：状態フラグ
     */
    protected static final int HIDDEN_STATUSFLAG = 1;

    /**
     * HEDDEN項目用：棚卸ケース数
     */
    protected static final int HIDDEN_INVENTCASEQTY = 2;

    /**
     * HEDDEN項目用：棚卸ピース数
     */
    protected static final int HIDDEN_INVENTPIECEQTY = 3;

    /**
     * リストボックスセル番号 HIDDEN項目
     */
    protected static final int LST_HIDEN = 0;

    /**
     * リストボックスセル番号 棚卸チェックボックス
     */
    protected static final int LST_CHECK = 1;

    /**
     * リストボックスセル番号 取消ボタン
     */
    protected static final int LST_CANCEL = 2;

    /**
     * リストボックスセル番号 削除ボタン
     */
    protected static final int LST_DELETE = 3;

    /**
     * リストボックスセル番号 状態
     */
    protected static final int LST_STATUS = 4;

    /**
     * リストボックスセル番号 エリア
     */
    protected static final int LST_AREA = 5;

    /**
     * リストボックスセル番号 商品コード
     */
    protected static final int LST_ITEMCODE = 6;

    /**
     * リストボックスセル番号 ロットNo
     */
    protected static final int LST_LOT = 7;

    /**
     * リストボックスセル番号 ケース入数
     */
    protected static final int LST_ENTQTY = 8;

    /**
     * リストボックスセル番号 在庫ケース数
     */
    protected static final int LST_STOCKCASE = 9;

    /**
     * リストボックスセル番号 棚卸ケース数
     */
    protected static final int LST_INVENTCASE = 10;

    /**
     * リストボックスセル番号 棚
     */
    protected static final int LST_LOCATION = 11;

    /**
     * リストボックスセル番号 商品名称
     */
    protected static final int LST_ITMENAME = 12;

    /**
     * リストボックスセル番号 在庫ピース数
     */
    protected static final int LST_STOCKPIECE = 13;

    /**
     * リストボックスセル番号 棚卸ピース数
     */
    protected static final int LST_INVENTPIECE = 14;

    // DFKLOOK ここまで修正

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel Inventory */
    private RadioButtonGroup _grp_Inventory;

    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** ListCellModel lst_InventoryResultInput */
    private ListCellModel _lcm_lst_InventoryResultInput;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public InventoryInputBusiness()
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

            if (rdo_JobNo.getChecked())
            {
                setFocus(rdo_JobNo);
            }
            else
            {
                setFocus(rdo_LocationRange);
            }
            return;
        }

        // choose process.
        if (eventSource.equals("btn_P_Search_JobNo_Click"))
        {
            // process call.
            btn_P_Search_JobNo_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_P_AddNewData_Click"))
        {
            // process call.
            btn_P_AddNewData_Click_DlgBack(dialogParams);
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
        if (eventSource.equals("btn_InventoryListIssue_Click"))
        {
            // process call.
            btn_InventoryListIssue_Click_Process(false);
        }
        if (eventSource.equals("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_Process(eventSource);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_JobNo_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_JobNo_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_LocationRange_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        rdo_LocationRange_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_P_Search_JobNo_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_Search_JobNo_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pul_Area_Change(ActionEvent e)
            throws Exception
    {
        // DFKLOOK ここから
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
        //DFKLOOK:ここまで修正
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
    public void btn_InventoryListIssue_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_InventoryListIssue_Click_Process(true);
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
        btn_Set_Click_Process(null);
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
    public void btn_P_AddNewData_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_P_AddNewData_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_InventoryResultInput_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_InventoryResultInput.getActiveCol();

        // choose process.
        if (_lcm_lst_InventoryResultInput.getColumnIndex(KEY_LST_CANCEL) == activeCol)
        {
            // process call.
            lst_Cancel_Click_Process();
        }
        else if (_lcm_lst_InventoryResultInput.getColumnIndex(KEY_LST_DELETE) == activeCol)
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
    public void lst_InventoryResultInput_ColumClick(ActionEvent e)
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

        // initialize Inventory.
        _grp_Inventory = new RadioButtonGroup(new RadioButton[]{rdo_JobNo, rdo_LocationRange}, locale);

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // initialize lst_InventoryResultInput.
        _lcm_lst_InventoryResultInput = new ListCellModel(lst_InventoryResultInput, LST_INVENTORYRESULTINPUT_KEYS, locale);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_CYCLE_COUNT, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_DELETE, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_STATUS, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_AREA, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_LOCATION, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_LOT, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_CASE_PACK, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_STOCK_CASE_QTY, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_STOCK_PIECE_QTY, true);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_CYCLE_COUNT_CASE_QTY, false);
        _lcm_lst_InventoryResultInput.setToolTipVisible(KEY_LST_CYCLE_COUNT_PIECE_QTY, false);

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
            _pdm_pul_Area.init(conn, AreaType.FLOOR_AND_TEMP_AND_RECEIVE, StationType.ALL, "", false);

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
    private void lst_InventoryResultInput_SetLineToolTip(ListCellLine line)
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
        setFocus(rdo_JobNo);

        // DFKLOOK ここから修正
        if (rdo_JobNo.getChecked())
        {
            setFocus(rdo_JobNo);
        }
        else if (rdo_LocationRange.getChecked())
        {
            setFocus(rdo_LocationRange);
        }
        // DFKLOOK ここまで修正

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
        InventoryInputSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryInputSCH(conn, this.getClass(), locale, ui);

            // SCH call.
            // DFKLOOK ここから修正
            List<Params> outparams = sch.initCheck();
            // DFKLOOK ここまで修正
            // output display.
            for (Params outparam : outparams)
            {
                viewState.setObject(ViewStateKeys.MASTER, outparam.get(InventoryInputSCHParams.STOCK_MASTER));
            }

            // clear.
            rdo_JobNo.setChecked(true);
            txt_ListWorkNo.setReadOnly(false);
            btn_P_Search_JobNo.setEnabled(true);
            pul_Area.setEnabled(false);
            txt_LocationFrom.setReadOnly(true);
            txt_LocationTo.setReadOnly(true);
            txt_ItemCode.setReadOnly(true);
            chk_InventoryOnlyDisp.setEnabled(false);
            btn_Set.setEnabled(false);
            btn_AllCheck.setEnabled(false);
            btn_AllCheckClear.setEnabled(false);
            btn_P_AddNewData.setEnabled(false);

            // DFKLOOK ここから
            // 生成したプルダウンの初期値を取得。
            _pdm_pul_Area.setSelectedValue(0);
            // 棚の入力例を表示させます。
            lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(
                    _pdm_pul_Area.getSelectedValue()));
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
    private void rdo_JobNo_Click_Process()
            throws Exception
    {
        // clear.
        txt_ListWorkNo.setReadOnly(false);
        btn_P_Search_JobNo.setEnabled(true);
        pul_Area.setEnabled(false);
        txt_LocationFrom.setReadOnly(true);
        txt_LocationTo.setReadOnly(true);
        txt_ItemCode.setReadOnly(true);
        chk_InventoryOnlyDisp.setEnabled(false);

        txt_LocationFrom.setText("");
        txt_LocationTo.setText("");
        txt_ItemCode.setText("");
        
        
        // set focus.
        setFocus(txt_ListWorkNo);

    }

    /**
     *
     * @throws Exception
     */
    private void rdo_LocationRange_Click_Process()
            throws Exception
    {
        // clear.
        txt_ListWorkNo.setReadOnly(true);
        btn_P_Search_JobNo.setEnabled(false);
        pul_Area.setEnabled(true);
        txt_LocationFrom.setReadOnly(false);
        txt_LocationTo.setReadOnly(false);
        txt_ItemCode.setReadOnly(false);
        chk_InventoryOnlyDisp.setEnabled(true);
        
        txt_ListWorkNo.setText("");
        

        // DFKLOOK 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
        //DFKLOOK:ここまで修正


        // set focus.
        setFocus(pul_Area);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_Search_JobNo_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstInventoryJobNoParams inparam = new LstInventoryJobNoParams();
        inparam.set(LstInventoryJobNoParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
        inparam.set(LstInventoryJobNoParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_Search_JobNo_Click");
        redirect("/inventorychk/listbox/jobno/LstInventoryJobNo.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_Search_JobNo_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstInventoryJobNoParams outparam = new LstInventoryJobNoParams(dialogParams);
        txt_ListWorkNo.setValue(outparam.get(LstInventoryJobNoParams.LIST_WORK_NO));

        // set focus.
        setFocus(txt_ListWorkNo);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.

        // DFKLOOK ここから修正
        if (rdo_JobNo.getChecked())
        {
            txt_ListWorkNo.validate(this, true);
        }
        else
        {
            pul_Area.validate(this, true);
        }
        // DFKLOOK ここまで修正
        txt_LocationFrom.validate(this, false);
        txt_LocationTo.validate(this, false);
        txt_ItemCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryInputSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryInputSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryInputSCHParams inparam = new InventoryInputSCHParams();
            inparam.set(InventoryInputSCHParams.SEARCH_CRITERIA, _grp_Inventory.getSelectedValue());
            inparam.set(InventoryInputSCHParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
            inparam.set(InventoryInputSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            // DFKLOOK start
            if (!StringUtil.isBlank(lbl_LocationStyle.getValue()))
            {
                // 棚のフォーマットチェック
                String loc =
                        WmsFormatter.toParamLocation(txt_LocationFrom.getStringValue(),
                                SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
                inparam.set(InventoryInputSCHParams.LOCATION_FROM, loc);
                loc =
                        WmsFormatter.toParamLocation(txt_LocationTo.getStringValue(),
                                SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
                inparam.set(InventoryInputSCHParams.LOCATION_TO, loc);
            }
            // DFKLOOK end
            inparam.set(InventoryInputSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(InventoryInputSCHParams.INVENTORY_ONLY_DISP, chk_InventoryOnlyDisp.getValue());
            inparam.set(InventoryInputSCHParams.INVENTORY_QTY_INPUT, chk_InventoryQtyInput.getValue());
            inparam.set(InventoryInputSCHParams.INVENTORY_STOCK_QTY_REPORT, chk_InventoryStockQtyReport.getValue());
            inparam.set(InventoryInputSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(InventoryInputSCHParams.CONDITION_SELECT, _grp_Inventory.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_InventoryResultInput.clear();

            // DFKLOOK ここから修正
            if (outparams.size() > 0)
            {

                // ラジオボタンによりviewState保存条件の分岐
                if (rdo_JobNo.getChecked())
                {
                    viewState.setObject(ViewStateKeys.SEARCH_CRITERIA, _grp_Inventory.getSelectedValue());
                    viewState.setObject(ViewStateKeys.LIST_WORK_NO, txt_ListWorkNo.getValue());
                    viewState.setObject(ViewStateKeys.AREA, outparams.get(0).get(InventoryInputSCHParams.AREA_NO));
                }
                else
                {
                    viewState.setObject(ViewStateKeys.SEARCH_CRITERIA, _grp_Inventory.getSelectedValue());
                    viewState.setObject(ViewStateKeys.AREA, outparams.get(0).get(InventoryInputSCHParams.AREA_NO));
                    viewState.setObject(ViewStateKeys.LOCATION_FROM, txt_LocationFrom.getValue());
                    viewState.setObject(ViewStateKeys.LOCATION_TO, txt_LocationTo.getValue());
                    viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
                }

                setList(outparams);

            }
            else
            {

                if (rdo_JobNo.getChecked())
                {
                    viewState.setObject(ViewStateKeys.SEARCH_CRITERIA, _grp_Inventory.getSelectedValue());
                    viewState.setObject(ViewStateKeys.LIST_WORK_NO, txt_ListWorkNo.getValue());
                }
                else
                {
                    viewState.setObject(ViewStateKeys.SEARCH_CRITERIA, _grp_Inventory.getSelectedValue());
                    viewState.setObject(ViewStateKeys.AREA, pul_Area.getSelectedValue());
                    viewState.setObject(ViewStateKeys.LOCATION_FROM, txt_LocationFrom.getValue());
                    viewState.setObject(ViewStateKeys.LOCATION_TO, txt_LocationTo.getValue());
                    viewState.setObject(ViewStateKeys.ITEM_CODE, txt_ItemCode.getValue());
                }


                if (sch.count(inparam) > 0)
                {
                    btn_Set.setEnabled(false);
                    btn_AllCheck.setEnabled(false);
                    btn_AllCheckClear.setEnabled(false);
                    btn_P_AddNewData.setEnabled(true);
                    message.setMsgResourceKey(sch.getMessage());
                }
                else
                {
                    enableCng_btn(false);
                }
            }
            if (rdo_JobNo.getChecked())
            {
                setFocus(rdo_JobNo);
            }
            else
            {
                setFocus(rdo_LocationRange);
            }
            // DFKLOOK ここまで修正

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
     * @param confirm
     * @throws Exception
     */
    private void btn_InventoryListIssue_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        // DFKLOOK ここから修正
        if (rdo_JobNo.getChecked())
        {
            txt_ListWorkNo.validate(this, true);
        }
        else
        {
            pul_Area.validate(this, true);
        }
        // DFKLOOK ここまで修正
        txt_LocationFrom.validate(this, false);
        txt_LocationTo.validate(this, false);
        txt_ItemCode.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryCheckDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new InventoryCheckDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            InventoryCheckDASCHParams inparam = new InventoryCheckDASCHParams();
            inparam.set(InventoryCheckDASCHParams.LIST_WORK_NO, txt_ListWorkNo.getValue());
            inparam.set(InventoryCheckDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            // DFKLOOK start
            if (!StringUtil.isBlank(lbl_LocationStyle.getValue()))
            {
                // 棚のフォーマットチェック
                String loc =
                        WmsFormatter.toParamLocation(txt_LocationFrom.getStringValue(),
                                SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
                inparam.set(InventoryInputSCHParams.LOCATION_FROM, loc);
                loc =
                        WmsFormatter.toParamLocation(txt_LocationTo.getStringValue(),
                                SuperLocationHolder.getInstance().getLocationFormat(pul_Area.getSelectedValue()));
                inparam.set(InventoryInputSCHParams.LOCATION_TO, loc);
            }
            // DFKLOOK end
            inparam.set(InventoryCheckDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(InventoryCheckDASCHParams.INVENTORY_ONLY_DISP, chk_InventoryOnlyDisp.getValue());
            inparam.set(InventoryCheckDASCHParams.INVENTORY_STOCK_QTY_REPORT, chk_InventoryStockQtyReport.getValue());
            inparam.set(InventoryCheckDASCHParams.INVENTORY_QTY_INPUT, chk_InventoryQtyInput.getValue());
            inparam.set(InventoryCheckDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(InventoryCheckDASCHParams.INVENTORY, _grp_Inventory.getSelectedValue());

            // check count.
            int count = dasch.count(inparam);
            if (confirm && count > 0)
            {
                // show confirm message.
                this.setConfirm("MSG-W0018\t" + Formatter.getNumFormat(count), false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_InventoryListIssue_Click");
                return;
            }
            else if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("InventoryCheckList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                InventoryCheckListParams expparam = new InventoryCheckListParams();
                expparam.set(InventoryCheckListParams.DFK_DS_NO, outparam.get(InventoryCheckDASCHParams.DFK_DS_NO));
                expparam.set(InventoryCheckListParams.DFK_USER_ID, outparam.get(InventoryCheckDASCHParams.DFK_USER_ID));
                expparam.set(InventoryCheckListParams.JOB_NO, outparam.get(InventoryCheckDASCHParams.JOB_NO));
                expparam.set(InventoryCheckListParams.AREA_NAME, outparam.get(InventoryCheckDASCHParams.AREA_NAME));
                expparam.set(InventoryCheckListParams.LOCATION_NO, outparam.get(InventoryCheckDASCHParams.LOCATION_NO));
                expparam.set(InventoryCheckListParams.ITEM_CODE, outparam.get(InventoryCheckDASCHParams.ITEM_CODE));
                expparam.set(InventoryCheckListParams.ITEM_NAME, outparam.get(InventoryCheckDASCHParams.ITEM_NAME));
                expparam.set(InventoryCheckListParams.LOT_NO, outparam.get(InventoryCheckDASCHParams.LOT_NO));
                expparam.set(InventoryCheckListParams.ENTERING_QTY,
                        outparam.get(InventoryCheckDASCHParams.ENTERING_QTY));
                if (chk_InventoryStockQtyReport.getChecked())
                {
                    expparam.set(InventoryCheckListParams.STOCK_CASE_QTY,
                            outparam.get(InventoryCheckDASCHParams.STOCK_CASE_QTY));
                    expparam.set(InventoryCheckListParams.STOCK_PIECE_QTY,
                            outparam.get(InventoryCheckDASCHParams.STOCK_PIECE_QTY));
                }
                else
                {
                    expparam.set(InventoryCheckListParams.STOCK_CASE_QTY, "");
                    expparam.set(InventoryCheckListParams.STOCK_PIECE_QTY, "");
                }
                expparam.set(InventoryCheckListParams.AREA_NO, outparam.get(InventoryCheckDASCHParams.AREA_NO));
                expparam.set(InventoryCheckListParams.SYS_DAY, outparam.get(InventoryCheckDASCHParams.SYS_DAY));
                expparam.set(InventoryCheckListParams.SYS_TIME, outparam.get(InventoryCheckDASCHParams.SYS_TIME));
                expparam.set(InventoryCheckListParams.DFK_USER_NAME,
                        outparam.get(InventoryCheckDASCHParams.DFK_USER_NAME));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // DFKLOOK ここから修正
            if (rdo_JobNo.getChecked())
            {
                setFocus(rdo_JobNo);
            }
            else
            {
                setFocus(rdo_LocationRange);
            }
            // DFKLOOK ここまで修正
            // execute print.
            try
            {
                exporter.print();
                message.setMsgResourceKey("6001010");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            if (rdo_JobNo.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }
            //DFKLOOK:ここから修正
            part11List.add(txt_ListWorkNo.getStringValue(), "");
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
            part11List.add(txt_LocationFrom.getStringValue(), "");
            part11List.add(txt_LocationTo.getStringValue(), "");
            part11List.add(txt_ItemCode.getStringValue(), "");
            //DFKLOOK:ここまで修正

            if (chk_InventoryStockQtyReport.getChecked())
            {
                part11List.add("1", "");
            }
            else
            {
                part11List.add("0", "");
            }

            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
            // commit.
            conn.commit();

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
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.rollback(conn);
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
        rdo_JobNo.setChecked(true);
        txt_ListWorkNo.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);
        txt_LocationFrom.setValue(null);
        txt_LocationTo.setValue(null);
        txt_ItemCode.setValue(null);
        chk_InventoryOnlyDisp.setChecked(false);
        chk_InventoryQtyInput.setChecked(false);
        chk_InventoryStockQtyReport.setChecked(false);
        txt_ListWorkNo.setReadOnly(false);
        btn_P_Search_JobNo.setEnabled(true);
        pul_Area.setEnabled(false);
        txt_LocationFrom.setReadOnly(true);
        txt_LocationTo.setReadOnly(true);
        txt_ItemCode.setReadOnly(true);
        chk_InventoryOnlyDisp.setEnabled(false);

        // set focus.
        setFocus(txt_ListWorkNo);

        // DFKLOOK ここから
        // 棚の入力例を表示させます。
        lbl_LocationStyle.setValue(SuperLocationHolder.getInstance().getLocationFormat(_pdm_pul_Area.getSelectedValue()));
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_InventoryResultInput.size(); i++)
        {

            // DFKLOOK ここから修正
            // チェックが入っていない行は除く対象行は除く
            lst_InventoryResultInput.setCurrentRow(i);
            if (!lst_InventoryResultInput.getChecked(LST_CHECK))
            {
                continue;
            }

            existEditedRow = true;

            String inventPiece = lst_InventoryResultInput.getValue(LST_INVENTPIECE);
            String inventCase = lst_InventoryResultInput.getValue(LST_INVENTCASE);
            String itemCode = lst_InventoryResultInput.getValue(LST_ITEMCODE);

            if (!StringUtil.isBlank(itemCode))
            {
                if ((inventCase == null || "".equals(inventCase)) && (inventPiece == null || "".equals(inventPiece)))
                {
                    //6023220=No.{0} 棚卸ケース数または棚卸ピース数を入力してください。
                    message.setMsgResourceKey(WmsMessageFormatter.format(6023220, i));
                    _lcm_lst_InventoryResultInput.resetEditRow();
                    _lcm_lst_InventoryResultInput.resetHighlight();
                    _lcm_lst_InventoryResultInput.addHighlight(i, ControlColor.Warning);

                    return;
                }
            }

            // DFKLOOK ここまで修正
        }
        if (!existEditedRow)
        {
            // DFKLOOK ここから修正
            if (rdo_JobNo.getChecked())
            {
                setFocus(rdo_JobNo);
            }
            else
            {
                setFocus(rdo_LocationRange);
            }
            // DFKLOOK ここまで修正

            message.setMsgResourceKey("6003001");
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
        InventoryInputSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryInputSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_InventoryResultInput.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_InventoryResultInput.get(i);

                // DFKLOOK ここから修正
                lst_InventoryResultInput.setCurrentRow(i);
                if (!lst_InventoryResultInput.getChecked(LST_CHECK))
                {
                    continue;
                }
                // DFKLOOK ここまで修正

                InventoryInputSCHParams lineparam = new InventoryInputSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(InventoryInputSCHParams.JOBNO, line.getValue(KEY_HIDDEN_JOBNO));
                lineparam.set(InventoryInputSCHParams.STATUSFLAG, line.getValue(KEY_HIDDEN_STATUSFLAG));
                lineparam.set(InventoryInputSCHParams.INVENTCASEQTY, line.getValue(KEY_HIDDEN_INVENTCASEQTY));
                lineparam.set(InventoryInputSCHParams.INVENTPIECEQTY, line.getValue(KEY_HIDDEN_INVENTPIECEQTY));
                lineparam.set(InventoryInputSCHParams.CYCLE_COUNT, line.getValue(KEY_LST_CYCLE_COUNT));
                lineparam.set(InventoryInputSCHParams.STATUS_FLAG, line.getValue(KEY_LST_STATUS));
                lineparam.set(InventoryInputSCHParams.AREA_NO, line.getValue(KEY_LST_AREA));
                lineparam.set(InventoryInputSCHParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION));
                lineparam.set(InventoryInputSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(InventoryInputSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(InventoryInputSCHParams.LOT_NO, line.getValue(KEY_LST_LOT));
                lineparam.set(InventoryInputSCHParams.ENTERING_QTY, line.getValue(KEY_LST_CASE_PACK));
                lineparam.set(InventoryInputSCHParams.STOCK_CASE_QTY, line.getValue(KEY_LST_STOCK_CASE_QTY));
                lineparam.set(InventoryInputSCHParams.STOCK_PIECE_QTY, line.getValue(KEY_LST_STOCK_PIECE_QTY));
                lineparam.set(InventoryInputSCHParams.RESULT_CASE_QTY, line.getValue(KEY_LST_CYCLE_COUNT_CASE_QTY));
                lineparam.set(InventoryInputSCHParams.RESULT_PIECE_QTY, line.getValue(KEY_LST_CYCLE_COUNT_PIECE_QTY));
                lineparam.set(InventoryInputSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA));
                lineparam.set(InventoryInputSCHParams.INVENTORY_ONLY_DISP,
                        viewState.getObject(ViewStateKeys.INVENTORY_ONLY_DISP));
                lineparam.set(InventoryInputSCHParams.INVENTORY_QTY_INPUT,
                        viewState.getObject(ViewStateKeys.INVENTORY_QTY_INPUT));
                lineparam.set(InventoryInputSCHParams.INVENTORY_STOCK_QTY_REPORT,
                        viewState.getObject(ViewStateKeys.INVENTORY_STOCK_QTY_REPORT));
                lineparam.set(InventoryInputSCHParams.LIST_WORK_NO, viewState.getObject(ViewStateKeys.LIST_WORK_NO));
                lineparam.set(InventoryInputSCHParams.LOCATION_FROM, viewState.getObject(ViewStateKeys.LOCATION_FROM));
                lineparam.set(InventoryInputSCHParams.LOCATION_TO, viewState.getObject(ViewStateKeys.LOCATION_TO));
                lineparam.set(InventoryInputSCHParams.SEARCH_CRITERIA,
                        viewState.getObject(ViewStateKeys.SEARCH_CRITERIA));
                lineparam.set(InventoryInputSCHParams.NEWDATE_FLAG, line.getValue(KEY_HIDDEN_NEWDATE_FLAG));
                lineparam.set(InventoryInputSCHParams.PROCESS_FLAG, InventoryInParameter.PROCESS_FLAG_INVENTORY_INPUT);
                lineparam.set(InventoryInputSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(InventoryInputSCHParams.MASTER, viewState.getObject(ViewStateKeys.MASTER));
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
                _lcm_lst_InventoryResultInput.resetEditRow();
                _lcm_lst_InventoryResultInput.resetHighlight();
                _lcm_lst_InventoryResultInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_InventoryResultInput.size(); i++)
            {
                ListCellLine line = _lcm_lst_InventoryResultInput.get(i);
                lst_InventoryResultInput.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // DFKLOOK start
                lst_InventoryResultInput.setCurrentRow(i);
                if (!lst_InventoryResultInput.getChecked(LST_CHECK))
                {
                    continue;
                }
                // DFKLOOK end
                
                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_AREA), "");
                part11List.add(line.getViewString(KEY_LST_LOCATION), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT), "");
                part11List.add(line.getViewString(KEY_LST_CASE_PACK), "");
                part11List.add(line.getViewString(KEY_LST_STOCK_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_STOCK_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_CYCLE_COUNT_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_CYCLE_COUNT_PIECE_QTY), "");

                if (Boolean.parseBoolean(line.getStringValue(KEY_HIDDEN_NEWDATE_FLAG)))
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

            // reset editing row.
            _lcm_lst_InventoryResultInput.resetEditRow();
            _lcm_lst_InventoryResultInput.resetHighlight();

            // set input parameters.
            InventoryInputSCHParams inparam = new InventoryInputSCHParams();
            inparam.set(InventoryInputSCHParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA));
            inparam.set(InventoryInputSCHParams.INVENTORY_ONLY_DISP,
                    viewState.getObject(ViewStateKeys.INVENTORY_ONLY_DISP));
            inparam.set(InventoryInputSCHParams.INVENTORY_QTY_INPUT,
                    viewState.getObject(ViewStateKeys.INVENTORY_QTY_INPUT));
            inparam.set(InventoryInputSCHParams.INVENTORY_STOCK_QTY_REPORT,
                    viewState.getObject(ViewStateKeys.INVENTORY_STOCK_QTY_REPORT));
            inparam.set(InventoryInputSCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
            inparam.set(InventoryInputSCHParams.LIST_WORK_NO, viewState.getObject(ViewStateKeys.LIST_WORK_NO));
            inparam.set(InventoryInputSCHParams.LOCATION_FROM, viewState.getObject(ViewStateKeys.LOCATION_FROM));
            inparam.set(InventoryInputSCHParams.LOCATION_TO, viewState.getObject(ViewStateKeys.LOCATION_TO));
            inparam.set(InventoryInputSCHParams.CONDITION_SELECT, viewState.getObject(ViewStateKeys.SEARCH_CRITERIA));
            inparam.set(InventoryInputSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_InventoryResultInput.clear();

            // DFKLOOK ここから修正
            setList(outparams);

            if (rdo_JobNo.getChecked())
            {
                setFocus(rdo_JobNo);
            }
            else
            {
                setFocus(rdo_LocationRange);
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
     *
     * @throws Exception
     */
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_InventoryResultInput.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_InventoryResultInput.get(i);
            lst_InventoryResultInput.setCurrentRow(i);
            clearLine.setValue(KEY_LST_CYCLE_COUNT, Boolean.TRUE);
            lst_InventoryResultInput_SetLineToolTip(clearLine);
            _lcm_lst_InventoryResultInput.set(i, clearLine);
        }
        // DFKLOOK ここから修正

        if (rdo_JobNo.getChecked())
        {
            setFocus(rdo_JobNo);
        }
        else
        {
            setFocus(rdo_LocationRange);
        }
        // DFKLOOK ここまで修正

    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_InventoryResultInput.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_InventoryResultInput.get(i);
            lst_InventoryResultInput.setCurrentRow(i);
            clearLine.setValue(KEY_LST_CYCLE_COUNT, Boolean.FALSE);
            lst_InventoryResultInput_SetLineToolTip(clearLine);
            _lcm_lst_InventoryResultInput.set(i, clearLine);
        }
        // DFKLOOK ここから修正
        if (rdo_JobNo.getChecked())
        {
            setFocus(rdo_JobNo);
        }
        else
        {
            setFocus(rdo_LocationRange);
        }
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void btn_P_AddNewData_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        InventoryDataAddParams inparam = new InventoryDataAddParams();
        inparam.set(InventoryDataAddParams.LIST_WORK_NO, viewState.getObject(ViewStateKeys.LIST_WORK_NO));
        inparam.set(InventoryDataAddParams.AREA_NO, viewState.getObject(ViewStateKeys.AREA));
        inparam.set(InventoryDataAddParams.LOCATION_FROM, viewState.getObject(ViewStateKeys.LOCATION_FROM));
        inparam.set(InventoryDataAddParams.LOCATION_TO, viewState.getObject(ViewStateKeys.LOCATION_TO));
        inparam.set(InventoryDataAddParams.ITEM_CODE, viewState.getObject(ViewStateKeys.ITEM_CODE));
        inparam.set(InventoryDataAddParams.CONDITION, viewState.getObject(ViewStateKeys.SEARCH_CRITERIA));
        inparam.set(InventoryDataAddParams.MASTER, viewState.getObject(ViewStateKeys.MASTER));

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_P_AddNewData_Click");
        redirect("/inventorychk/dataadd/InventoryDataAdd.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_P_AddNewData_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        InventoryDataAddParams outparam = new InventoryDataAddParams(dialogParams);
        // DFKLOOK ここから修正

        _lcm_lst_InventoryResultInput.resetHighlight();

        List<Params> result = new ArrayList<Params>();

        result.add(outparam);

        Connection conn = null;
        try
        {
            // コネクション取得
            conn = ConnectionManager.getRequestConnection(this);

            InventoryInParameter[] paramList = createParamList(conn, false, 0);
            if (paramList != null)
            {
                for (int i = 0; i < paramList.length; i++)
                {
                    if (outparam.getString(InventoryDataAddParams.AREA_NO).equals(paramList[i].getAreaNo())
                            && outparam.getString(InventoryDataAddParams.LOCATION_NO).equals(
                                    paramList[i].getLocationNo())
                            && outparam.getString(InventoryDataAddParams.ITEM_CODE).equals(paramList[i].getItemCode())
                            && outparam.getString(InventoryDataAddParams.LOT_NO).equals(paramList[i].getLotNo()))
                    {
                        DisplayUtil.addHighlight(lst_InventoryResultInput, i + 1, ControlColor.Warning);
                        //6023202=すでに同一データが存在するため、追加できません。
                        message.setMsgResourceKey("6023202");
                        return;
                    }
                }
            }

            //棚卸範囲に入っているか確認
            InventSettingController inventScon = null;
            try
            {
                inventScon = new InventSettingController(conn, getClass());
                inventScon.rangeCheck(WmsParam.DEFAULT_CONSIGNOR_CODE,
                        outparam.getString(InventoryDataAddParams.AREA_NO),
                        outparam.getString(InventoryDataAddParams.LOCATION_NO));
            }
            catch (OperatorException e)
            {
                if (OperatorException.ERR_INVENT_LOCATION_OUTSIDE_RANGE.equals(e.getErrorCode()))
                {
                    //指定された棚は棚卸中でありません。
                    message.setMsgResourceKey("6023132");
                    return;
                }
                // 上記以外は例外をそのまま投げる
                throw e;
            }


            //表示データがリストセル表示最大数の場合
            if (lst_InventoryResultInput.getMaxRows() > WmsParam.MAX_NUMBER_OF_DISP)
            {

                //6023176=件数が{1}件を超えるため、新規データ追加できません。
                message.setMsgResourceKey(WmsMessageFormatter.format(6023176, WmsParam.MAX_NUMBER_OF_DISP));
                return;
            }

            setList(result);

            if (rdo_JobNo.getChecked())
            {
                setFocus(rdo_JobNo);
            }
            else
            {
                setFocus(rdo_LocationRange);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
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
        // DFKLOOK ここまで修正
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_InventoryResultInput.getActiveRow();
        lst_InventoryResultInput.setCurrentRow(row);

        // reset editing row.
        lst_InventoryResultInput.removeRow(row);
        _lcm_lst_InventoryResultInput.resetEditRow();
        _lcm_lst_InventoryResultInput.resetHighlight();
        // DFKLOOK ここから修正
        if (lst_InventoryResultInput.getMaxRows() == 1)
        {
            // ボタン押下を不可にする
            enableCng_btn(false);
        }
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
        int row = lst_InventoryResultInput.getActiveRow();
        lst_InventoryResultInput.setCurrentRow(row);
        ListCellLine line = _lcm_lst_InventoryResultInput.get(row);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        InventoryInputSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new InventoryInputSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            InventoryInputSCHParams inparam = new InventoryInputSCHParams();
            inparam.setProcessFlag(ProcessFlag.DELETE);
            inparam.setRowIndex(row);
            inparam.set(InventoryInputSCHParams.AREA_NO, line.getValue(KEY_LST_AREA));
            inparam.set(InventoryInputSCHParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION));
            inparam.set(InventoryInputSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
            inparam.set(InventoryInputSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
            inparam.set(InventoryInputSCHParams.LOT_NO, line.getValue(KEY_LST_LOT));
            inparam.set(InventoryInputSCHParams.ENTERING_QTY, line.getValue(KEY_LST_CASE_PACK));
            inparam.set(InventoryInputSCHParams.STOCK_CASE_QTY, line.getValue(KEY_LST_STOCK_CASE_QTY));
            inparam.set(InventoryInputSCHParams.STOCK_PIECE_QTY, line.getValue(KEY_LST_STOCK_PIECE_QTY));
            inparam.set(InventoryInputSCHParams.RESULT_CASE_QTY, line.getValue(KEY_LST_CYCLE_COUNT_CASE_QTY));
            inparam.set(InventoryInputSCHParams.RESULT_PIECE_QTY, line.getValue(KEY_LST_CYCLE_COUNT_PIECE_QTY));
            inparam.set(InventoryInputSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            // DFKLOOK ここから修正
            inparam.set(InventoryInputSCHParams.PROCESS_FLAG, InventoryInParameter.PROCESS_FLAG_INVENTORY_DELETE);
            // DFKLOOK ここまで修正

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_InventoryResultInput.resetEditRow();
                _lcm_lst_InventoryResultInput.resetHighlight();
                _lcm_lst_InventoryResultInput.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }
            // DFKLOOK ここから修正
            else
            {
                // 削除対象行をカレント行に設定
                lst_InventoryResultInput.setCurrentRow(lst_InventoryResultInput.getActiveRow());

                // write part11 log.
                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_AREA), "");
                part11List.add(line.getViewString(KEY_LST_LOCATION), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT), "");
                part11List.add(line.getViewString(KEY_LST_CASE_PACK), "");
                part11List.add(line.getViewString(KEY_LST_STOCK_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_STOCK_PIECE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_CYCLE_COUNT_CASE_QTY), "");
                part11List.add(line.getViewString(KEY_LST_CYCLE_COUNT_PIECE_QTY), "");
                part11List.add("1", "");

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE),
                        part11List);

                conn.commit();
                message.setMsgResourceKey(sch.getMessage());

                int countCell = lst_InventoryResultInput.getActiveRow();
                if (chk_InventoryOnlyDisp.getChecked() || !sch.check(inparam)
                        || checkListDiff(inparam, conn, countCell))
                {
                    // リスト削除
                    lst_InventoryResultInput.removeRow(countCell);
                }
                else
                {
                    sch = new InventoryInputSCH(conn, this.getClass(), locale, ui);
                    inparam.set(KEY_LST_ITEM_CODE, "");
                    List<Params> outparams = sch.query(inparam);
                    if (outparams.size() != 0)
                    {
                        for (Params outparam : outparams)
                        {
                            // 値をセットする行を選択する
                            lst_InventoryResultInput.setCurrentRow(countCell);

                            ListCellLine aline = _lcm_lst_InventoryResultInput.get(countCell);
                            aline.setValue(KEY_HIDDEN_NEWDATE_FLAG, outparam.get(InventoryInputSCHParams.NEWDATE_FLAG));
                            // true:表示(使用可)　false:非表示(使用不可)
                            boolean isInventry = true;
                            boolean isCancel = false;
                            boolean isDelete = false;
                            boolean isVisibleCaseQty = true;
                            boolean isVIsiblePieceQty = true;
                            boolean isReadOnlyCaseQty = false;
                            boolean isReadOnlyPieceQty = false;
                            //空棚用
                            String itemCode = outparam.getString(InventoryInputSCHParams.ITEM_CODE);
                            String itemName = null;
                            aline.setValue(KEY_LST_AREA, outparam.get(InventoryInputSCHParams.AREA_NO));
                            aline.setValue(KEY_LST_ITEM_CODE, outparam.get(InventoryInputSCHParams.ITEM_CODE));
                            aline.setValue(KEY_LST_LOT, outparam.get(InventoryInputSCHParams.LOT_NO));
                            aline.setValue(KEY_LST_LOCATION, outparam.get(InventoryInputSCHParams.LOCATION_NO));
                            //空棚用
                            if (itemCode.equals(""))
                            {
                                itemName = "";
                                aline.setValue(KEY_LST_CASE_PACK, "");
                                aline.setValue(KEY_LST_STOCK_CASE_QTY, "");
                                aline.setValue(KEY_LST_STOCK_PIECE_QTY, "");
                            }
                            else
                            {
                                itemName = outparam.getString(InventoryInputSCHParams.ITEM_NAME);
                                aline.setValue(KEY_LST_CASE_PACK, outparam.get(InventoryInputSCHParams.ENTERING_QTY));
                                aline.setValue(KEY_LST_STOCK_CASE_QTY,
                                        outparam.get(InventoryInputSCHParams.STOCK_CASE_QTY));
                                aline.setValue(KEY_LST_STOCK_PIECE_QTY,
                                        outparam.get(InventoryInputSCHParams.STOCK_PIECE_QTY));
                            }
                            aline.setValue(KEY_LST_ITEM_NAME, itemName);
                            //状態を日本語に変更する
                            String status = outparam.getString(InventoryInputSCHParams.STATUSFLAG);
                            aline.setValue(KEY_LST_STATUS, outparam.get(InventoryInputSCHParams.STATUS_FLAG));
                            //未作業の場合
                            if ((InventoryInParameter.STATUS_FLAG_INVENTORY_UNSTART).equals(status))
                            {
                                isInventry = true;

                                //棚卸数の初期入力にチェックが入っている場合
                                if (chk_InventoryQtyInput.getChecked())
                                {
                                    aline.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY,
                                            outparam.get(InventoryInputSCHParams.STOCK_CASE_QTY));
                                    aline.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY,
                                            outparam.get(InventoryInputSCHParams.STOCK_PIECE_QTY));
                                }
                            }
                            //作業中の場合
                            else if (InventoryInParameter.STATUS_FLAG_INVENTORY_WORKING.equals(status))
                            {
                                isInventry = false;
                                isVisibleCaseQty = false;
                                isVIsiblePieceQty = false;

                                isReadOnlyCaseQty = true;
                                isReadOnlyPieceQty = true;
                                aline.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY,
                                        outparam.get(InventoryInputSCHParams.RESULT_CASE_QTY));
                                aline.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY,
                                        outparam.get(InventoryInputSCHParams.RESULT_PIECE_QTY));
                            }
                            //作業済の場合
                            else if ((InventoryInParameter.STATUS_FLAG_INVENTORY_WORKING_COMPLETED).equals(status))
                            {
                                isInventry = true;
                                aline.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY,
                                        outparam.get(InventoryInputSCHParams.RESULT_CASE_QTY));
                                aline.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY,
                                        outparam.get(InventoryInputSCHParams.RESULT_PIECE_QTY));
                                //作業済で新規追加データの場合
                                if (!StringUtil.isBlank(itemCode)
                                        && ((outparam.getInt(InventoryInputSCHParams.ENTERING_QTY) * outparam.getInt(InventoryInputSCHParams.STOCK_CASE_QTY)) + outparam.getInt(InventoryInputSCHParams.STOCK_PIECE_QTY)) == 0)
                                {
                                    isDelete = true;
                                }
                            }
                            //確定済の場合
                            else if ((InventoryInParameter.STATUS_FLAG_INVENTORY_ALREADY_COMPLETED).equals(status))
                            {
                                isInventry = false;
                                isReadOnlyCaseQty = true;
                                isReadOnlyPieceQty = true;
                                aline.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY,
                                        outparam.get(InventoryInputSCHParams.RESULT_CASE_QTY));
                                aline.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY,
                                        outparam.get(InventoryInputSCHParams.RESULT_PIECE_QTY));
                            }
                            //新規データの場合
                            else
                            {
                                isInventry = true;
                                isCancel = true;
                                aline.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY,
                                        outparam.get(InventoryInputSCHParams.RESULT_CASE_QTY));
                                aline.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY,
                                        outparam.get(InventoryInputSCHParams.RESULT_PIECE_QTY));
                            }
                            //空棚の場合
                            if (itemCode.equals(""))
                            {
                                isInventry = true;
                                isVisibleCaseQty = false;
                                isVIsiblePieceQty = false;
                                isReadOnlyCaseQty = true;
                                isReadOnlyPieceQty = true;
                            }

                            // リストセル入力不可項目セット
                            if (outparam.getInt(InventoryInputSCHParams.ENTERING_QTY) == 0)
                            {
                                // ケース入数が０の場合は常に入力不可
                                isReadOnlyCaseQty = false;
                                //棚卸数の初期入力にチェックが入っていない場合
                                if (!chk_InventoryQtyInput.getChecked())
                                {
                                    aline.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY, "");
                                }
                            }
                            lst_InventoryResultInput_SetLineToolTip(aline);
                            _lcm_lst_InventoryResultInput.set(countCell, aline);
                            _lcm_lst_InventoryResultInput.getListCell().setCellEnabled(LST_CANCEL, isCancel);
                            _lcm_lst_InventoryResultInput.getListCell().setCellEnabled(LST_DELETE, isDelete);

                            if (isInventry)
                            {
                                _lcm_lst_InventoryResultInput.getListCell().setChecked(LST_CHECK, true);
                            }
                            else
                            {
                                _lcm_lst_InventoryResultInput.getListCell().setCellVisible(LST_CHECK, false);
                            }
                            if (!isVisibleCaseQty)
                            {
                                _lcm_lst_InventoryResultInput.getListCell().setCellVisible(LST_INVENTCASE,
                                        isVisibleCaseQty);
                            }
                            if (!isVIsiblePieceQty)
                            {
                                _lcm_lst_InventoryResultInput.getListCell().setCellVisible(LST_INVENTPIECE,
                                        isVIsiblePieceQty);
                            }
                            if (isReadOnlyCaseQty)
                            {
                                _lcm_lst_InventoryResultInput.getListCell().setCellReadOnly(LST_INVENTCASE,
                                        isReadOnlyCaseQty);
                            }
                            if (isReadOnlyPieceQty)
                            {
                                _lcm_lst_InventoryResultInput.getListCell().setCellReadOnly(LST_INVENTPIECE,
                                        isReadOnlyPieceQty);
                            }
                        }
                    }
                }
            }
            if (rdo_JobNo.getChecked())
            {
                setFocus(rdo_JobNo);
            }
            else
            {
                setFocus(rdo_LocationRange);
            }

            // write part11 log.
            //P11LogWriter part11Writer = new P11LogWriter(conn);
            //Part11List part11List = new Part11List();
            //part11List.add(line.getViewString(KEY_LST_AREA), "");
            //part11List.add(line.getViewString(KEY_LST_LOCATION), "");
            //part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
            //part11List.add(line.getViewString(KEY_LST_LOT), "");
            //part11List.add(line.getViewString(KEY_LST_CASE_PACK), "");
            //part11List.add(line.getViewString(KEY_LST_STOCK_CASE_QTY), "");
            //part11List.add(line.getViewString(KEY_LST_STOCK_PIECE_QTY), "");
            //part11List.add(line.getViewString(KEY_LST_CYCLE_COUNT_CASE_QTY), "");
            //part11List.add(line.getViewString(KEY_LST_CYCLE_COUNT_PIECE_QTY), "");
            //part11List.add("1", "");

            //part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_DELETE), part11List);

            // commit.
            //conn.commit();
            // DFKLOOK ここまで修正

            // reset editing row.
            _lcm_lst_InventoryResultInput.resetEditRow();
            _lcm_lst_InventoryResultInput.resetHighlight();

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

    // DFKLOOK ここから修正
    /**
     * リストボックスのパラメータを入力します。<BR>
     * @param conn コネクション
     * @param flag チェック対象行のみ取得の場合true、全データ取得の場合false
     * @param rowNo 行No
     * @return パラメータ
     * @throws Exception 全ての例外を報告します。
     */
    private InventoryInParameter[] createParamList(Connection conn, boolean flag, int rowNo)
            throws Exception
    {
        List<InventoryInParameter> arrayparam = new ArrayList<InventoryInParameter>();

        AreaController areacon = new AreaController(conn, this.getClass());

        WarenaviSystemController systemController = new WarenaviSystemController(conn, this.getClass());

        for (int i = 1; i < lst_InventoryResultInput.getMaxRows(); i++)
        {
            // 行指定
            lst_InventoryResultInput.setCurrentRow(i);

            ListCellLine line = _lcm_lst_InventoryResultInput.get(i);

            if (rowNo > 0 && rowNo != i)
            {
                continue;
            }

            if (flag)
            {
                // チェックが入っていない行は除く対象行は除く
                if (!lst_InventoryResultInput.getChecked(LST_CHECK))
                {
                    continue;
                }
            }

            String inventPiece = lst_InventoryResultInput.getValue(LST_INVENTPIECE);
            String inventCase = lst_InventoryResultInput.getValue(LST_INVENTCASE);
            String itemCode = lst_InventoryResultInput.getValue(LST_ITEMCODE);
            if (flag)
            {
                if (!StringUtil.isBlank(itemCode))
                {
                    if ((inventCase == null || "".equals(inventCase))
                            && (inventPiece == null || "".equals(inventPiece)))
                    {
                        //6023220=No.{0} 棚卸ケース数または棚卸ピース数を入力してください。
                        message.setMsgResourceKey(WmsMessageFormatter.format(6023220, i));
                        _lcm_lst_InventoryResultInput.addHighlight(i, ControlColor.Warning);

                        return null;
                    }
                }
            }

            // スケジュールパラメータへセット
            InventoryInParameter param = new InventoryInParameter(null);

            // 荷主コード
            param.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            // マスタパッケージ導入フラグ
            param.setMasterFlag(systemController.hasMasterPack());
            // リスト作業No
            if (line.getStringValue(KEY_HIDDEN_JOBNO) != null)
            {
                param.setSettingUnitKey(line.getStringValue(KEY_HIDDEN_JOBNO));
            }
            // エリアNo
            String areaNo = lst_InventoryResultInput.getValue(LST_AREA);
            param.setAreaNo(areaNo);
            // 棚
            String location = lst_InventoryResultInput.getValue(LST_LOCATION);
            location = areacon.toParamLocation(areaNo, location);
            param.setLocationNo(location);
            // 商品コード
            param.setItemCode(lst_InventoryResultInput.getValue(LST_ITEMCODE));
            // 商品名称
            param.setItemName(lst_InventoryResultInput.getValue(LST_ITMENAME));
            // ロットNo
            param.setLotNo(lst_InventoryResultInput.getValue(LST_LOT));
            // ケース入数
            int enteringQty = line.getNumberValue(KEY_LST_CASE_PACK).intValue();
            // 在庫ケース数
            int caseQty = line.getNumberValue(KEY_LST_STOCK_CASE_QTY).intValue();
            param.setStockCaseQty(caseQty);
            // 在庫ピース数
            int pieceQty = line.getNumberValue(KEY_LST_STOCK_PIECE_QTY).intValue();
            param.setStockPieceQty(pieceQty);
            // 在庫数
            param.setStockQty(enteringQty * caseQty + pieceQty);
            // 棚卸ケース数
            caseQty = line.getNumberValue(KEY_LST_CYCLE_COUNT_CASE_QTY).intValue();
            param.setResultCaseQty(caseQty);
            // 棚卸ピース数
            pieceQty = line.getNumberValue(KEY_LST_CYCLE_COUNT_PIECE_QTY).intValue();
            param.setResultPieceQty(pieceQty);
            // 棚卸数
            param.setInventoryInsResultQtyLong((long)((long)enteringQty * (long)caseQty + (long)pieceQty));
            // チェック用棚卸数
            caseQty = line.getNumberValue(KEY_HIDDEN_INVENTCASEQTY).intValue();
            pieceQty = line.getNumberValue(KEY_HIDDEN_INVENTPIECEQTY).intValue();
            param.setInventoryQty(enteringQty * caseQty + pieceQty);
            // 処理フラグ : 結果入力
            param.setProcessFlag(InventoryInParameter.PROCESS_FLAG_INVENTORY_INPUT);

            // 新規データフラグ
            if (Boolean.valueOf(lst_InventoryResultInput.getCellEnabled(LST_CANCEL)))
            {
                param.setAddDataFlag(true);
            }
            // 行No
            param.setRowNo(i);

            arrayparam.add(param);
        }

        if (arrayparam.size() > 0)
        {
            // セットするためうちデータがあれば値をセット
            InventoryInParameter[] listparam = new InventoryInParameter[arrayparam.size()];
            arrayparam.toArray(listparam);

            return listparam;
        }
        else
        {
            // セットするためうちデータがなければnullをセット

            return null;
        }
    }


    /**
     * リストエリアを作成します。<BR>
     * @param outparams 表示用パラメータ
     * @throws Exception 全ての例外を報告します。
     */
    private void setList(List<Params> outparams)
            throws Exception
    {

        for (Params outparam : outparams)
        {
            ListCellLine line = _lcm_lst_InventoryResultInput.getNewLine();

            // 行の編集、ハイライトクリア
            _lcm_lst_InventoryResultInput.resetEditRow();
            _lcm_lst_InventoryResultInput.resetHighlight();

            viewState.setObject(ViewStateKeys.INVENTORY_ONLY_DISP, chk_InventoryOnlyDisp.getValue());
            viewState.setObject(ViewStateKeys.INVENTORY_QTY_INPUT, chk_InventoryQtyInput.getValue());
            viewState.setObject(ViewStateKeys.INVENTORY_STOCK_QTY_REPORT, chk_InventoryStockQtyReport.getValue());
            line.setValue(KEY_HIDDEN_NEWDATE_FLAG, outparam.get(InventoryInputSCHParams.NEWDATE_FLAG));

            // 選択チェックボックス
            boolean isInventry = true;
            boolean isReadOnly = true;
            // キャンセルボタン
            boolean isCancel = false;
            // 削除ボタン
            boolean isDelete = false;
            // 表示・非表示フラグ
            boolean isVisibleCaseQty = true;
            boolean isVIsiblePieceQty = true;
            // 使用可・使用不可フラグ
            boolean isReadOnlyCaseQty = false;
            boolean isReadOnlyPieceQty = false;

            //空棚用
            String itemCode = outparam.getString(InventoryInputSCHParams.ITEM_CODE);
            String itemName = null;

            line.setValue(KEY_LST_AREA, outparam.get(InventoryInputSCHParams.AREA_NO));
            line.setValue(KEY_LST_ITEM_CODE, outparam.get(InventoryInputSCHParams.ITEM_CODE));
            line.setValue(KEY_LST_LOT, outparam.get(InventoryInputSCHParams.LOT_NO));

            line.setValue(KEY_LST_LOCATION, outparam.get(InventoryInputSCHParams.LOCATION_NO));

            //空棚用
            if (itemCode.equals(""))
            {
                itemName = "";
                line.setValue(KEY_LST_CASE_PACK, "");
                line.setValue(KEY_LST_STOCK_CASE_QTY, "");
                line.setValue(KEY_LST_STOCK_PIECE_QTY, "");
            }
            else
            {
                itemName = outparam.getString(InventoryInputSCHParams.ITEM_NAME);
                line.setValue(KEY_LST_CASE_PACK, outparam.get(InventoryInputSCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(InventoryInputSCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(InventoryInputSCHParams.STOCK_PIECE_QTY));
            }
            line.setValue(KEY_LST_ITEM_NAME, itemName);
            //状態を日本語に変更する
            String status = outparam.getString(InventoryInputSCHParams.STATUSFLAG);
            line.setValue(KEY_LST_STATUS, outparam.get(InventoryInputSCHParams.STATUS_FLAG));

            //未作業の場合
            if ((InventoryInParameter.STATUS_FLAG_INVENTORY_UNSTART).equals(status))
            {
                isInventry = true;

                //棚卸数の初期入力にチェックが入っている場合
                if (chk_InventoryQtyInput.getChecked())
                {
                    line.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY, outparam.get(InventoryInputSCHParams.STOCK_CASE_QTY));
                    line.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY, outparam.get(InventoryInputSCHParams.STOCK_PIECE_QTY));
                }
            }
            //作業中の場合
            else if (InventoryInParameter.STATUS_FLAG_INVENTORY_WORKING.equals(status))
            {
                isInventry = false;
                isVisibleCaseQty = false;
                isVIsiblePieceQty = false;

                isReadOnlyCaseQty = true;
                isReadOnlyPieceQty = true;

                line.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY, outparam.get(InventoryInputSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY, outparam.get(InventoryInputSCHParams.RESULT_PIECE_QTY));
            }
            //作業済の場合
            else if ((InventoryInParameter.STATUS_FLAG_INVENTORY_WORKING_COMPLETED).equals(status))
            {
                isInventry = true;
                line.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY, outparam.get(InventoryInputSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY, outparam.get(InventoryInputSCHParams.RESULT_PIECE_QTY));
                //作業済で新規追加データの場合
                if (!StringUtil.isBlank(itemCode)
                        && ((outparam.getInt(InventoryInputSCHParams.ENTERING_QTY) * outparam.getInt(InventoryInputSCHParams.STOCK_CASE_QTY)) + outparam.getInt(InventoryInputSCHParams.STOCK_PIECE_QTY)) == 0)
                {
                    isDelete = true;
                }
            }
            //確定済の場合
            else if ((InventoryInParameter.STATUS_FLAG_INVENTORY_ALREADY_COMPLETED).equals(status))
            {
                isInventry = false;
                isReadOnly = false;
                isReadOnlyCaseQty = true;
                isReadOnlyPieceQty = true;
                line.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY, outparam.get(InventoryInputSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY, outparam.get(InventoryInputSCHParams.RESULT_PIECE_QTY));
            }
            //新規データの場合
            else
            {
                isInventry = true;
                isCancel = true;
                line.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY, outparam.get(InventoryInputSCHParams.RESULT_CASE_QTY));
                line.setValue(KEY_LST_CYCLE_COUNT_PIECE_QTY, outparam.get(InventoryInputSCHParams.RESULT_PIECE_QTY));
            }
            //空棚の場合
            if (itemCode.equals(""))
            {
                isInventry = true;
                isVisibleCaseQty = false;
                isVIsiblePieceQty = false;

                isReadOnlyCaseQty = true;
                isReadOnlyPieceQty = true;
            }

            // リストセル入力不可項目セット
            if (outparam.getInt(InventoryInputSCHParams.ENTERING_QTY) == 0)
            {
                // ケース入数が０の場合は常に入力不可
                isReadOnlyCaseQty = true;
                //棚卸数の初期入力にチェックが入っていない場合
                if (!chk_InventoryQtyInput.getChecked())
                {
                    line.setValue(KEY_LST_CYCLE_COUNT_CASE_QTY, "");
                }
            }


            lst_InventoryResultInput_SetLineToolTip(line);
            _lcm_lst_InventoryResultInput.add(line);

            // キャンセル・削除ボタンの使用可・不可設定
            _lcm_lst_InventoryResultInput.getListCell().setCellEnabled(LST_CANCEL, isCancel);
            _lcm_lst_InventoryResultInput.getListCell().setCellEnabled(LST_DELETE, isDelete);

            if (isInventry)
            {
                if (isReadOnly)
                {
                    // チェックボックス選択
                    _lcm_lst_InventoryResultInput.getListCell().setChecked(LST_CHECK, true);
                }
                else
                {
                    // チェックボックス未選択
                    _lcm_lst_InventoryResultInput.getListCell().setCellEnabled(LST_CHECK, false);
                }
            }
            else
            {
                // チェックボックス使用不可
                _lcm_lst_InventoryResultInput.getListCell().setCellEnabled(LST_CHECK, isInventry);

            }
            // ケース数入力　使用可/不可
            _lcm_lst_InventoryResultInput.getListCell().setCellReadOnly(LST_INVENTCASE, isReadOnlyCaseQty);
            // ピース数入力　使用可/不可
            _lcm_lst_InventoryResultInput.getListCell().setCellReadOnly(LST_INVENTPIECE, isReadOnlyPieceQty);
            // ケース数表示　表示/非表示
            _lcm_lst_InventoryResultInput.getListCell().setCellVisible(LST_INVENTCASE, isVisibleCaseQty);
            // ピース数表示　表示/非表示
            _lcm_lst_InventoryResultInput.getListCell().setCellVisible(LST_INVENTPIECE, isVIsiblePieceQty);
        }

        if (_lcm_lst_InventoryResultInput.getListCell().getMaxRows() > 1)
        {
            btn_Set.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_P_AddNewData.setEnabled(true);
        }
        else
        {
            btn_Set.setEnabled(false);
            btn_AllCheck.setEnabled(false);
            btn_AllCheckClear.setEnabled(false);
            btn_P_AddNewData.setEnabled(false);
        }
    }


    /**
     * リストセルに同一エリア、同一棚、商品コード違い(同一商品コード、ロットNo違い)のデータが存在した場合
     * @param inparam リストセルデータ
     * @param conn コネクション
     * @param correntRow 現在行
     * @return 存在しない場合false 存在した場合true
     * @throws Exception 全ての例外を報告する
     */
    private boolean checkListDiff(InventoryInputSCHParams inparam, Connection conn, int correntRow)
            throws Exception
    {
        //データの重複チェック
        InventoryInParameter[] paramList = createParamList(conn, false, 0);
        if (paramList != null)
        {
            for (int i = 0; i < paramList.length; i++)
            {
                if (i == correntRow - 1)
                {
                    continue;
                }
                if (inparam.getString(InventoryInputSCHParams.AREA_NO).equals(paramList[i].getAreaNo())
                        && inparam.getString(InventoryInputSCHParams.LOCATION_NO).equals(paramList[i].getLocationNo()))
                {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * リストボックス側のボタンを有効、無効を切替えるメソッドです。<BR>
     * @param flag 有効、無効の切替え
     */
    private void enableCng_btn(boolean flag)
    {
        btn_Set.setEnabled(flag);
        btn_AllCheck.setEnabled(flag);
        btn_AllCheckClear.setEnabled(flag);
        btn_P_AddNewData.setEnabled(flag);
    }

    // DFKLOOK ここまで修正

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
