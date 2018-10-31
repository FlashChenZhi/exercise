// $Id: FaNoPlanStorageBusiness.java 7375 2010-03-05 04:33:07Z shibamoto $
package jp.co.daifuku.wms.stock.display.fanoplanstorage;

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
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.itemname.LstItemNameParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.stock.display.fanoplanstorage.FaNoPlanStorage;
import jp.co.daifuku.wms.stock.display.fanoplanstorage.ViewStateKeys;
import jp.co.daifuku.wms.stock.exporter.StorageWorkListParams;
import jp.co.daifuku.wms.stock.listbox.add.FaLstAddLocationParams;
import jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCH;
import jp.co.daifuku.wms.stock.schedule.FaNoPlanStorageSCHParams;
import jp.co.daifuku.wms.storage.dasch.FaStorageListDASCH;
import jp.co.daifuku.wms.storage.dasch.FaStorageListDASCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7375 $, $Date:: 2010-03-05 13:33:07 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class FaNoPlanStorageBusiness
        extends FaNoPlanStorage
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaNoPlanStorage(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_FaNoPlanStorage(HIDDEN_AREA_NO) */
    private static final ListCellKey KEY_HIDDEN_AREA_NO = new ListCellKey("HIDDEN_AREA_NO", new AreaCellColumn(), false, false);

    /** lst_FaNoPlanStorage(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_FaNoPlanStorage(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_FaNoPlanStorage(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_FaNoPlanStorage(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaNoPlanStorage(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_FaNoPlanStorage(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_FaNoPlanStorage(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_FaNoPlanStorage(LST_STORAGE_QTY) */
    private static final ListCellKey KEY_LST_STORAGE_QTY = new ListCellKey("LST_STORAGE_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaNoPlanStorage keys */
    private static final ListCellKey[] LST_FANOPLANSTORAGE_KEYS = {
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_HIDDEN_AREA_NO,
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOT_NO,
        KEY_LST_LOCATION_NO,
        KEY_LST_STORAGE_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** ListCellModel lst_FaNoPlanStorage */
    private ScrollListCellModel _lcm_lst_FaNoPlanStorage;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaNoPlanStorageBusiness()
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
        else if (eventSource.equals("btn_AddLocation_Click"))
        {
            // process call.
            btn_AddLocation_Click_DlgBack(dialogParams);
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
        else if (eventSource.equals("btn_WorkListPrint_Click"))
        {
            // process call.
            btn_WorkListPrint_Click_Process(false);
        }
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
    public void btn_AddLocation_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AddLocation_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_StockDisplay_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_StockDisplay_Click_Process();
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
    public void btn_Set_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set_Click_Process();
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
    public void btn_WorkListPrint_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_WorkListPrint_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_FaNoPlanStorage_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_FaNoPlanStorage.getActiveCol();

        // choose process.
        if (_lcm_lst_FaNoPlanStorage.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_FaNoPlanStorage.getColumnIndex(KEY_LST_CANCEL) == activeCol)
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

        // initialize lst_FaNoPlanStorage.
        _lcm_lst_FaNoPlanStorage = new ScrollListCellModel(lst_FaNoPlanStorage, LST_FANOPLANSTORAGE_KEYS, locale);
        _lcm_lst_FaNoPlanStorage.setToolTipVisible(KEY_LST_MODIFY, false);
        _lcm_lst_FaNoPlanStorage.setToolTipVisible(KEY_LST_CANCEL, false);
        _lcm_lst_FaNoPlanStorage.setToolTipVisible(KEY_LST_NO, false);
        _lcm_lst_FaNoPlanStorage.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_FaNoPlanStorage.setToolTipVisible(KEY_LST_ITEM_NAME, false);
        _lcm_lst_FaNoPlanStorage.setToolTipVisible(KEY_LST_LOT_NO, false);
        _lcm_lst_FaNoPlanStorage.setToolTipVisible(KEY_LST_LOCATION_NO, false);
        _lcm_lst_FaNoPlanStorage.setToolTipVisible(KEY_LST_STORAGE_QTY, false);
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
    private void lst_FaNoPlanStorage_SetLineToolTip(ListCellLine line)
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
        txt_StockQty.setReadOnly(true);
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        btn_WorkListPrint.setEnabled(false);
        chk_WorkListPrint.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void txt_ItemCode_EnterKey_Process()
            throws Exception
    {
        // input validation.
        txt_ItemCode.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaNoPlanStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaNoPlanStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaNoPlanStorageSCHParams inparam = new FaNoPlanStorageSCHParams();
            inparam.set(FaNoPlanStorageSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(FaNoPlanStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_ItemName.setValue(outparam.get(FaNoPlanStorageSCHParams.ITEM_NAME));
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
    private void btn_AddLocation_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        FaLstAddLocationParams inparam = new FaLstAddLocationParams();
        inparam.set(FaLstAddLocationParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
        inparam.set(FaLstAddLocationParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(FaLstAddLocationParams.LOT_NO, txt_LotNo.getValue());
        inparam.set(FaLstAddLocationParams.LOCATION_NO, txt_Location.getValue());
        inparam.set(FaLstAddLocationParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_AddLocation_Click");
        redirect("/stock/listbox/add/FaLstAddLocation.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_AddLocation_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        FaLstAddLocationParams outparam = new FaLstAddLocationParams(dialogParams);
        _pdm_pul_Area.setSelectedValue(outparam.get(FaLstAddLocationParams.AREA_NO));
        txt_ItemCode.setValue(outparam.get(FaLstAddLocationParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(FaLstAddLocationParams.ITEM_NAME));
        txt_LotNo.setValue(outparam.get(FaLstAddLocationParams.LOT_NO));
        txt_Location.setValue(outparam.get(FaLstAddLocationParams.LOCATION_NO));
        txt_StockQty.setValue(outparam.get(FaLstAddLocationParams.STOCK_QTY));

        // set focus.
        setFocus(txt_Location);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_StockDisplay_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, true);
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_LotNo.validate(this, false);
        txt_Location.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaNoPlanStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaNoPlanStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaNoPlanStorageSCHParams inparam = new FaNoPlanStorageSCHParams();
            inparam.set(FaNoPlanStorageSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaNoPlanStorageSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(FaNoPlanStorageSCHParams.ITEM_NAME, txt_ItemName.getValue());
            inparam.set(FaNoPlanStorageSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(FaNoPlanStorageSCHParams.LOCATION_NO, txt_Location.getValue());
            inparam.set(FaNoPlanStorageSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_StockQty.setValue(outparam.get(FaNoPlanStorageSCHParams.STOCK_QTY));
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
    private void btn_Input_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, true);
        txt_ItemCode.validate(this, true);
        txt_LotNo.validate(this, false);
        txt_Location.validate(this, true);
        txt_StorageQty.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaNoPlanStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaNoPlanStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaNoPlanStorageSCHParams inparam = new FaNoPlanStorageSCHParams();
            inparam.set(FaNoPlanStorageSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaNoPlanStorageSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(FaNoPlanStorageSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(FaNoPlanStorageSCHParams.LOCATION_NO, txt_Location.getValue());
            inparam.set(FaNoPlanStorageSCHParams.STORAGE_QTY, txt_StorageQty.getValue());

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaNoPlanStorage.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_FaNoPlanStorage.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_FaNoPlanStorage.get(i);
                FaNoPlanStorageSCHParams lineparam = new FaNoPlanStorageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(FaNoPlanStorageSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(FaNoPlanStorageSCHParams.LST_ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaNoPlanStorageSCHParams.LST_LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(FaNoPlanStorageSCHParams.LST_LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
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

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                int editRow = _lcm_lst_FaNoPlanStorage.getEditRow();
                Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
                ListCellLine line = newline ? _lcm_lst_FaNoPlanStorage.getNewLine()
                                            : _lcm_lst_FaNoPlanStorage.get(editRow);
                line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(FaNoPlanStorageSCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, txt_LotNo.getValue());
                line.setValue(KEY_LST_LOCATION_NO, txt_Location.getValue());
                line.setValue(KEY_LST_STORAGE_QTY, txt_StorageQty.getValue());
                line.setValue(KEY_HIDDEN_AREA_NO, _pdm_pul_Area.getSelectedValue());

                // add new row or update editing row.
                lst_FaNoPlanStorage_SetLineToolTip(line);
                if (newline)
                {
                    _lcm_lst_FaNoPlanStorage.add(line, true);
                }
                else
                {
                    _lcm_lst_FaNoPlanStorage.set(editRow, line);
                }
                break;
            }

            // reset editing row.
            _lcm_lst_FaNoPlanStorage.resetEditRow();
            _lcm_lst_FaNoPlanStorage.resetHighlight();

            // clear.
            btn_Set.setEnabled(true);
            btn_AllClear.setEnabled(true);
            pul_Area.setEnabled(false);
            chk_WorkListPrint.setEnabled(true);
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
        txt_Location.setValue(null);
        txt_StorageQty.setValue(null);
        txt_StockQty.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaNoPlanStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaNoPlanStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaNoPlanStorage.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaNoPlanStorage.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                FaNoPlanStorageSCHParams lineparam = new FaNoPlanStorageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(FaNoPlanStorageSCHParams.AREA_NO, line.getValue(KEY_HIDDEN_AREA_NO));
                lineparam.set(FaNoPlanStorageSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(FaNoPlanStorageSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaNoPlanStorageSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(FaNoPlanStorageSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(FaNoPlanStorageSCHParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                lineparam.set(FaNoPlanStorageSCHParams.STORAGE_QTY, line.getValue(KEY_LST_STORAGE_QTY));
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
                _lcm_lst_FaNoPlanStorage.resetEditRow();
                _lcm_lst_FaNoPlanStorage.resetHighlight();
                _lcm_lst_FaNoPlanStorage.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaNoPlanStorage.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaNoPlanStorage.get(i);
                lst_FaNoPlanStorage.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");

                if (chk_WorkListPrint.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_STORAGE_QTY), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_FaNoPlanStorage.resetEditRow();
            _lcm_lst_FaNoPlanStorage.resetHighlight();

            // clear.
            _lcm_lst_FaNoPlanStorage.clear();
            btn_Set.setEnabled(false);
            btn_AllClear.setEnabled(false);
            pul_Area.setEnabled(true);
            btn_WorkListPrint.setEnabled(true);
            chk_WorkListPrint.setEnabled(true);
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
        _lcm_lst_FaNoPlanStorage.clear();
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        pul_Area.setEnabled(true);
        chk_WorkListPrint.setEnabled(false);
    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_WorkListPrint_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaStorageListDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaStorageListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaStorageListDASCHParams inparam = new FaStorageListDASCHParams();
            inparam.set(FaStorageListDASCHParams.SETTING_UNIT_KEY, viewState.getObject(ViewStateKeys.VS_SETTING_UNIT_KEY));
            inparam.set(FaStorageListDASCHParams.WORK_TYPE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // check count.
            int count = dasch.count(inparam);
            if (confirm && count > 0)
            {
                // show confirm message.
                this.setConfirm("MSG-W0018\t" + Formatter.getNumFormat(count), false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_WorkListPrint_Click");
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
            exporter = factory.newPrinterExporter("StorageWorkList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                StorageWorkListParams expparam = new StorageWorkListParams();
                expparam.set(StorageWorkListParams.DFK_DS_NO, outparam.get(FaStorageListDASCHParams.DFK_DS_NO));
                expparam.set(StorageWorkListParams.DFK_USER_ID, outparam.get(FaStorageListDASCHParams.DFK_USER_ID));
                expparam.set(StorageWorkListParams.DFK_USER_NAME, outparam.get(FaStorageListDASCHParams.DFK_USER_NAME));
                expparam.set(StorageWorkListParams.SYS_DAY, outparam.get(FaStorageListDASCHParams.SYS_DAY));
                expparam.set(StorageWorkListParams.SYS_TIME, outparam.get(FaStorageListDASCHParams.SYS_TIME));
                expparam.set(StorageWorkListParams.LIST_NO, outparam.get(FaStorageListDASCHParams.LIST_NO));
                expparam.set(StorageWorkListParams.FROM_SEARCH_DAY, outparam.get(FaStorageListDASCHParams.FROM_SEARCH_DAY));
                expparam.set(StorageWorkListParams.FROM_SEARCH_TIME, outparam.get(FaStorageListDASCHParams.FROM_SEARCH_TIME));
                expparam.set(StorageWorkListParams.TO_SEARCH_DAY, outparam.get(FaStorageListDASCHParams.TO_SEARCH_DAY));
                expparam.set(StorageWorkListParams.TO_SEARCH_TIME, outparam.get(FaStorageListDASCHParams.TO_SEARCH_TIME));
                expparam.set(StorageWorkListParams.AREA_NO, outparam.get(FaStorageListDASCHParams.AREA_NO));
                expparam.set(StorageWorkListParams.LOCATION_NO, outparam.get(FaStorageListDASCHParams.LOCATION_NO));
                expparam.set(StorageWorkListParams.ITEM_CODE, outparam.get(FaStorageListDASCHParams.ITEM_CODE));
                expparam.set(StorageWorkListParams.ITEM_NAME, outparam.get(FaStorageListDASCHParams.ITEM_NAME));
                expparam.set(StorageWorkListParams.LOT_NO, outparam.get(FaStorageListDASCHParams.LOT_NO));
                expparam.set(StorageWorkListParams.WORK_QTY, outparam.get(FaStorageListDASCHParams.WORK_QTY));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

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
            part11List.add("", "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
            // commit.
            conn.commit();
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
        int row = lst_FaNoPlanStorage.getActiveRow();
        lst_FaNoPlanStorage.setCurrentRow(row);
        ListCellLine line = _lcm_lst_FaNoPlanStorage.get(row);

        // output display.
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_LotNo.setValue(line.getValue(KEY_LST_LOT_NO));
        txt_Location.setValue(line.getValue(KEY_LST_LOCATION_NO));
        txt_StorageQty.setValue(line.getValue(KEY_LST_STORAGE_QTY));

        // highlight active row.
        _lcm_lst_FaNoPlanStorage.resetHighlight();
        _lcm_lst_FaNoPlanStorage.addHighlight(row);
        _lcm_lst_FaNoPlanStorage.setEditRow(row);

        // clear.
        txt_StockQty.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_FaNoPlanStorage.getActiveRow();
        lst_FaNoPlanStorage.setCurrentRow(row);

        // reset editing row.
        lst_FaNoPlanStorage.removeRow(row);
        _lcm_lst_FaNoPlanStorage.resetEditRow();
        _lcm_lst_FaNoPlanStorage.resetHighlight();
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
