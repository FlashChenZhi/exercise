// $Id: FaAsNoPlanStorageBusiness.java 7368 2010-03-05 04:18:46Z shibamoto $
package jp.co.daifuku.wms.asrs.display.fanoplanstorage;

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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
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
import jp.co.daifuku.wms.asrs.display.fanoplanstorage.FaAsNoPlanStorage;
import jp.co.daifuku.wms.asrs.display.fanoplanstorage.ViewStateKeys;
import jp.co.daifuku.wms.asrs.exporter.AsStorageWorkListParams;
import jp.co.daifuku.wms.asrs.schedule.FaAsNoPlanStorageSCH;
import jp.co.daifuku.wms.asrs.schedule.FaAsNoPlanStorageSCHParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.itemname.LstItemNameParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsSoftZonePullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;
import jp.co.daifuku.wms.storage.dasch.FaStorageListDASCH;
import jp.co.daifuku.wms.storage.dasch.FaStorageListDASCHParams;
import jp.co.daifuku.wms.storage.schedule.StorageInParameter;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7368 $, $Date:: 2010-03-05 13:18:46 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class FaAsNoPlanStorageBusiness
        extends FaAsNoPlanStorage
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaAsNoPlanStorage(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_FaAsNoPlanStorage(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_FaAsNoPlanStorage(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_FaAsNoPlanStorage(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_FaAsNoPlanStorage(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaAsNoPlanStorage(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_FaAsNoPlanStorage(LST_SOFT_ZONE_NAME) */
    private static final ListCellKey KEY_LST_SOFT_ZONE_NAME = new ListCellKey("LST_SOFT_ZONE_NAME", new StringCellColumn(), true, false);

    /** lst_FaAsNoPlanStorage(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_FaAsNoPlanStorage(LST_WORK_QTY) */
    private static final ListCellKey KEY_LST_WORK_QTY = new ListCellKey("LST_WORK_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaAsNoPlanStorage keys */
    private static final ListCellKey[] LST_FAASNOPLANSTORAGE_KEYS = {
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_SOFT_ZONE_NAME,
        KEY_LST_LOT_NO,
        KEY_LST_WORK_QTY,
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

    /** PullDownModel pul_LSoftZone */
    private WmsSoftZonePullDownModel _pdm_pul_LSoftZone;

    /** ListCellModel lst_FaAsNoPlanStorage */
    private ScrollListCellModel _lcm_lst_FaAsNoPlanStorage;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaAsNoPlanStorageBusiness()
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
        if (eventSource.equals("btn_PSearchItemCode_Click"))
        {
            // process call.
            btn_PSearchItemCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_PSearchItemName_Click"))
        {
            // process call.
            btn_PSearchItemName_Click_DlgBack(dialogParams);
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
    public void btn_PSearchItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchItemCode_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PSearchItemName_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearchItemName_Click_Process();
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
    public void lst_FaAsNoPlanStorage_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_FaAsNoPlanStorage.getActiveCol();

        // choose process.
        if (_lcm_lst_FaAsNoPlanStorage.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_FaAsNoPlanStorage.getColumnIndex(KEY_LST_CANCEL) == activeCol)
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

        // initialize pul_LSoftZone.
        _pdm_pul_LSoftZone = new WmsSoftZonePullDownModel(pul_LSoftZone, locale, ui);
        _pdm_pul_LSoftZone.setParent(_pdm_pul_Area);

        // initialize lst_FaAsNoPlanStorage.
        _lcm_lst_FaAsNoPlanStorage = new ScrollListCellModel(lst_FaAsNoPlanStorage, LST_FAASNOPLANSTORAGE_KEYS, locale);
        _lcm_lst_FaAsNoPlanStorage.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_FaAsNoPlanStorage.setToolTipVisible(KEY_LST_CANCEL, true);
        _lcm_lst_FaAsNoPlanStorage.setToolTipVisible(KEY_LST_NO, true);
        _lcm_lst_FaAsNoPlanStorage.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_FaAsNoPlanStorage.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_FaAsNoPlanStorage.setToolTipVisible(KEY_LST_SOFT_ZONE_NAME, true);
        _lcm_lst_FaAsNoPlanStorage.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_FaAsNoPlanStorage.setToolTipVisible(KEY_LST_WORK_QTY, true);
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
    private void lst_FaAsNoPlanStorage_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0597", KEY_LST_SOFT_ZONE_NAME);
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
        txt_SoftZoneName.setReadOnly(true);
        btn_Input.setEnabled(true);
        btn_Clear.setEnabled(true);
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        btn_WorkListPrint.setEnabled(false);
        chk_LWorkListPrint.setEnabled(false);
        txt_LMixedItems.setReadOnly(true);
        txt_LMaxMixedItems.setReadOnly(true);
        pul_LSoftZone.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearchItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));
        txt_SoftZoneName.setValue(outparam.get(LstItemParams.SOFT_ZONE_NAME));

        // set focus.
        setFocus(txt_ItemCode);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearchItemName_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemNameParams inparam = new LstItemNameParams();
        inparam.set(LstItemNameParams.ITEM_NAME, txt_ItemName.getValue());
        inparam.set(LstItemNameParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearchItemName_Click");
        redirect("/base/listbox/itemname/LstItemName.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearchItemName_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemNameParams outparam = new LstItemNameParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemNameParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemNameParams.ITEM_NAME));
        txt_SoftZoneName.setValue(outparam.get(LstItemNameParams.SOFT_ZONE_NAME));

        // set focus.
        setFocus(txt_ItemName);
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
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);
        txt_ItemCode.validate(this, true);
        txt_ItemName.validate(this, false);
        txt_SoftZoneName.validate(this, false);
        txt_LotNo.validate(this, false);
        txt_StorageQty.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaAsNoPlanStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaAsNoPlanStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaAsNoPlanStorageSCHParams inparam = new FaAsNoPlanStorageSCHParams();
            inparam.set(FaAsNoPlanStorageSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaAsNoPlanStorageSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(FaAsNoPlanStorageSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaAsNoPlanStorageSCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(FaAsNoPlanStorageSCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(FaAsNoPlanStorageSCHParams.WORK_QTY, txt_StorageQty.getValue());

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaAsNoPlanStorage.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_FaAsNoPlanStorage.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_FaAsNoPlanStorage.get(i);
                FaAsNoPlanStorageSCHParams lineparam = new FaAsNoPlanStorageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(FaAsNoPlanStorageSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(FaAsNoPlanStorageSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaAsNoPlanStorageSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
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
            int editRow = _lcm_lst_FaAsNoPlanStorage.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_FaAsNoPlanStorage.getNewLine()
                                        : _lcm_lst_FaAsNoPlanStorage.get(editRow);
            line.setValue(KEY_LST_ITEM_CODE, txt_ItemCode.getValue());
            line.setValue(KEY_LST_ITEM_NAME, txt_ItemName.getValue());
            line.setValue(KEY_LST_SOFT_ZONE_NAME, txt_SoftZoneName.getValue());
            line.setValue(KEY_LST_LOT_NO, txt_LotNo.getValue());
            line.setValue(KEY_LST_WORK_QTY, txt_StorageQty.getValue());
            line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // add new row or update editing row.
            lst_FaAsNoPlanStorage_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_FaAsNoPlanStorage.add(line, true);
            }
            else
            {
                _lcm_lst_FaAsNoPlanStorage.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_FaAsNoPlanStorage.resetEditRow();
            _lcm_lst_FaAsNoPlanStorage.resetHighlight();

            // clear.
            pul_Area.setEnabled(false);
            pul_WorkPlace.setEnabled(false);
            pul_Station.setEnabled(false);
            btn_Set.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_LWorkListPrint.setEnabled(true);
            pul_LSoftZone.setEnabled(true);
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
        txt_SoftZoneName.setValue(null);
        txt_LotNo.setValue(null);
        txt_StorageQty.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);
        pul_LSoftZone.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaAsNoPlanStorageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaAsNoPlanStorageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaAsNoPlanStorage.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaAsNoPlanStorage.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                FaAsNoPlanStorageSCHParams lineparam = new FaAsNoPlanStorageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(FaAsNoPlanStorageSCHParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                lineparam.set(FaAsNoPlanStorageSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaAsNoPlanStorageSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(FaAsNoPlanStorageSCHParams.WORK_QTY, line.getValue(KEY_LST_WORK_QTY));
                lineparam.set(FaAsNoPlanStorageSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
                lineparam.set(FaAsNoPlanStorageSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
                lineparam.set(FaAsNoPlanStorageSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
                lineparam.set(FaAsNoPlanStorageSCHParams.SOFT_ZONE_ID, _pdm_pul_LSoftZone.getSelectedValue());
                lineparam.set(FaAsNoPlanStorageSCHParams.PRINT_FLAG, chk_LWorkListPrint.getValue());
                lineparam.set(FaAsNoPlanStorageSCHParams.SETTING_UNIT_KEY, "");
                lineparam.set(FaAsNoPlanStorageSCHParams.JOB_TYPE, "");
                lineparam.set(FaAsNoPlanStorageSCHParams.FUNCTION_ID, "");
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
                _lcm_lst_FaAsNoPlanStorage.resetEditRow();
                _lcm_lst_FaAsNoPlanStorage.resetHighlight();
                _lcm_lst_FaAsNoPlanStorage.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaAsNoPlanStorage.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaAsNoPlanStorage.get(i);
                lst_FaAsNoPlanStorage.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
                part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
                part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_WORK_QTY), "");

                if (chk_LWorkListPrint.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                part11List.add(_pdm_pul_LSoftZone.getSelectedStringValue(), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_FaAsNoPlanStorage.resetEditRow();
            _lcm_lst_FaAsNoPlanStorage.resetHighlight();

            // clear.
            pul_Area.setEnabled(true);
            pul_WorkPlace.setEnabled(true);
            pul_Station.setEnabled(true);
            _lcm_lst_FaAsNoPlanStorage.clear();
            btn_Set.setEnabled(false);
            btn_AllClear.setEnabled(false);
            chk_LWorkListPrint.setEnabled(false);
            pul_LSoftZone.setEnabled(false);
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
        pul_Area.setEnabled(true);
        pul_WorkPlace.setEnabled(true);
        pul_Station.setEnabled(true);
        _lcm_lst_FaAsNoPlanStorage.clear();
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        chk_LWorkListPrint.setEnabled(false);
        pul_LSoftZone.setEnabled(false);
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
            inparam.set(FaStorageListDASCHParams.WORK_TYPE, StorageInParameter.SEARCH_ASRS_STORAGE_LIST);

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
            exporter = factory.newPrinterExporter("AsStorageWorkList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                AsStorageWorkListParams expparam = new AsStorageWorkListParams();
                expparam.set(AsStorageWorkListParams.DFK_DS_NO, outparam.get(FaStorageListDASCHParams.DFK_DS_NO));
                expparam.set(AsStorageWorkListParams.DFK_USER_ID, outparam.get(FaStorageListDASCHParams.DFK_USER_ID));
                expparam.set(AsStorageWorkListParams.DFK_USER_NAME, outparam.get(FaStorageListDASCHParams.DFK_USER_NAME));
                expparam.set(AsStorageWorkListParams.SYS_DAY, outparam.get(FaStorageListDASCHParams.SYS_DAY));
                expparam.set(AsStorageWorkListParams.SYS_TIME, outparam.get(FaStorageListDASCHParams.SYS_TIME));
                expparam.set(AsStorageWorkListParams.STATION_NO, outparam.get(FaStorageListDASCHParams.STATION_NO));
                expparam.set(AsStorageWorkListParams.STATION_NAME, outparam.get(FaStorageListDASCHParams.STATION_NAME));
                expparam.set(AsStorageWorkListParams.WORK_NO, outparam.get(FaStorageListDASCHParams.WORK_NO));
                expparam.set(AsStorageWorkListParams.LOCATION_NO, outparam.get(FaStorageListDASCHParams.LOCATION_NO));
                expparam.set(AsStorageWorkListParams.ITEM_CODE, outparam.get(FaStorageListDASCHParams.ITEM_CODE));
                expparam.set(AsStorageWorkListParams.ITEM_NAME, outparam.get(FaStorageListDASCHParams.ITEM_NAME));
                expparam.set(AsStorageWorkListParams.LOT_NO, outparam.get(FaStorageListDASCHParams.LOT_NO));
                expparam.set(AsStorageWorkListParams.WORK_QTY, outparam.get(FaStorageListDASCHParams.WORK_QTY));
                expparam.set(AsStorageWorkListParams.STOCK_QTY, outparam.get(FaStorageListDASCHParams.STOCK_QTY));
                expparam.set(AsStorageWorkListParams.AREA_NO, outparam.get(FaStorageListDASCHParams.AREA_NO));
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
        int row = lst_FaAsNoPlanStorage.getActiveRow();
        lst_FaAsNoPlanStorage.setCurrentRow(row);
        ListCellLine line = _lcm_lst_FaAsNoPlanStorage.get(row);

        // output display.
        txt_ItemCode.setValue(line.getValue(KEY_LST_ITEM_CODE));
        txt_ItemName.setValue(line.getValue(KEY_LST_ITEM_NAME));
        txt_SoftZoneName.setValue(line.getValue(KEY_LST_SOFT_ZONE_NAME));
        txt_LotNo.setValue(line.getValue(KEY_LST_LOT_NO));
        txt_StorageQty.setValue(line.getValue(KEY_LST_WORK_QTY));

        // highlight active row.
        _lcm_lst_FaAsNoPlanStorage.resetHighlight();
        _lcm_lst_FaAsNoPlanStorage.addHighlight(row);
        _lcm_lst_FaAsNoPlanStorage.setEditRow(row);
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_FaAsNoPlanStorage.getActiveRow();
        lst_FaAsNoPlanStorage.setCurrentRow(row);

        // reset editing row.
        lst_FaAsNoPlanStorage.removeRow(row);
        _lcm_lst_FaAsNoPlanStorage.resetEditRow();
        _lcm_lst_FaAsNoPlanStorage.resetHighlight();
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
