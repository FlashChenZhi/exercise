// $Id: FaAsReStoringBusiness.java 7471 2010-03-08 10:10:21Z kishimoto $
package jp.co.daifuku.wms.asrs.display.farestoring;

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
import jp.co.daifuku.wms.asrs.display.farestoring.FaAsReStoring;
import jp.co.daifuku.wms.asrs.display.farestoring.ViewStateKeys;
import jp.co.daifuku.wms.asrs.exporter.ReStoringWorkListParams;
import jp.co.daifuku.wms.asrs.listbox.farestoringplandetail.FaLstAsReStoringPlanDetailParams;
import jp.co.daifuku.wms.asrs.schedule.FaAsReStoringSCH;
import jp.co.daifuku.wms.asrs.schedule.FaAsReStoringSCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
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
 * @version $Revision: 7471 $, $Date:: 2010-03-08 19:10:21 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class FaAsReStoringBusiness
        extends FaAsReStoring
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaAsReStoring(HIDDEN_SOFT_ZONE_ID) */
    private static final ListCellKey KEY_HIDDEN_SOFT_ZONE_ID = new ListCellKey("HIDDEN_SOFT_ZONE_ID", new StringCellColumn(), false, false);

    /** lst_FaAsReStoring(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_FaAsReStoring(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_FaAsReStoring(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_FaAsReStoring(LST_WORK_NO) */
    private static final ListCellKey KEY_LST_WORK_NO = new ListCellKey("LST_WORK_NO", new StringCellColumn(), true, false);

    /** lst_FaAsReStoring(LST_SOFT_ZONE_NAME) */
    private static final ListCellKey KEY_LST_SOFT_ZONE_NAME = new ListCellKey("LST_SOFT_ZONE_NAME", new StringCellColumn(), true, false);

    /** lst_FaAsReStoring keys */
    private static final ListCellKey[] LST_FAASRESTORING_KEYS = {
        KEY_HIDDEN_SOFT_ZONE_ID,
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_NO,
        KEY_LST_WORK_NO,
        KEY_LST_SOFT_ZONE_NAME,
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

    /** PullDownModel pul_SoftZone */
    private WmsSoftZonePullDownModel _pdm_pul_SoftZone;

    /** ListCellModel lst_FaAsReStoring */
    private ScrollListCellModel _lcm_lst_FaAsReStoring;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaAsReStoringBusiness()
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
        if (eventSource.equals("btn_PWorkNoDetail_Click"))
        {
            // process call.
            btn_PWorkNoDetail_Click_DlgBack(dialogParams);
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
    public void btn_PWorkNoDetail_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PWorkNoDetail_Click_Process();
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
    public void lst_FaAsReStoring_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_FaAsReStoring.getActiveCol();

        // choose process.
        if (_lcm_lst_FaAsReStoring.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_FaAsReStoring.getColumnIndex(KEY_LST_CANCEL) == activeCol)
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

        // initialize pul_SoftZone.
        _pdm_pul_SoftZone = new WmsSoftZonePullDownModel(pul_SoftZone, locale, ui);
        _pdm_pul_SoftZone.setParent(_pdm_pul_Area);

        // initialize lst_FaAsReStoring.
        _lcm_lst_FaAsReStoring = new ScrollListCellModel(lst_FaAsReStoring, LST_FAASRESTORING_KEYS, locale);
        _lcm_lst_FaAsReStoring.setToolTipVisible(KEY_LST_MODIFY, false);
        _lcm_lst_FaAsReStoring.setToolTipVisible(KEY_LST_CANCEL, false);
        _lcm_lst_FaAsReStoring.setToolTipVisible(KEY_LST_NO, false);
        _lcm_lst_FaAsReStoring.setToolTipVisible(KEY_LST_WORK_NO, false);
        _lcm_lst_FaAsReStoring.setToolTipVisible(KEY_LST_SOFT_ZONE_NAME, false);
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

            // load pul_SoftZone.
            _pdm_pul_SoftZone.init(conn, SoftZoneType.AREA);
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
    private void lst_FaAsReStoring_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_WorkNo);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Input.setEnabled(true);
        btn_Clear.setEnabled(true);
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        btn_WorkListPrint.setEnabled(false);
        chk_LWorkListPrint.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_PWorkNoDetail_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        FaLstAsReStoringPlanDetailParams inparam = new FaLstAsReStoringPlanDetailParams();
        inparam.set(FaLstAsReStoringPlanDetailParams.WORK_NO, txt_WorkNo.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PWorkNoDetail_Click");
        redirect("/asrs/listbox/farestoringplandetail/FaLstAsReStoringPlanDetail.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PWorkNoDetail_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // set focus.
        setFocus(txt_WorkNo);
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
        txt_WorkNo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaAsReStoringSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaAsReStoringSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            FaAsReStoringSCHParams inparam = new FaAsReStoringSCHParams();
            inparam.set(FaAsReStoringSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaAsReStoringSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(FaAsReStoringSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaAsReStoringSCHParams.WORK_NO, txt_WorkNo.getValue());
            inparam.set(FaAsReStoringSCHParams.SOFT_ZONE_ID, _pdm_pul_SoftZone.getSelectedValue());

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaAsReStoring.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_FaAsReStoring.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_FaAsReStoring.get(i);
                FaAsReStoringSCHParams lineparam = new FaAsReStoringSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(FaAsReStoringSCHParams.WORK_NO, line.getValue(KEY_LST_WORK_NO));
                lineparam.set(FaAsReStoringSCHParams.SOFT_ZONE_ID, line.getValue(KEY_HIDDEN_SOFT_ZONE_ID));
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
            int editRow = _lcm_lst_FaAsReStoring.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_FaAsReStoring.getNewLine()
                                        : _lcm_lst_FaAsReStoring.get(editRow);
            line.setValue(KEY_LST_WORK_NO, txt_WorkNo.getValue());
            line.setValue(KEY_LST_SOFT_ZONE_NAME, _pdm_pul_SoftZone.getSelectedValue());
            line.setValue(KEY_HIDDEN_SOFT_ZONE_ID, _pdm_pul_SoftZone.getSelectedValue());

            // add new row or update editing row.
            lst_FaAsReStoring_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_FaAsReStoring.add(line, true);
            }
            else
            {
                _lcm_lst_FaAsReStoring.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_FaAsReStoring.resetEditRow();
            _lcm_lst_FaAsReStoring.resetHighlight();

            // clear.
            pul_Area.setEnabled(false);
            pul_WorkPlace.setEnabled(false);
            pul_Station.setEnabled(false);
            btn_Set.setEnabled(true);
            btn_AllClear.setEnabled(true);
            chk_LWorkListPrint.setEnabled(true);
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
        txt_WorkNo.setValue(null);
        _pdm_pul_SoftZone.setSelectedValue(null);
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

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaAsReStoringSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaAsReStoringSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_FaAsReStoring.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaAsReStoring.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                FaAsReStoringSCHParams lineparam = new FaAsReStoringSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(FaAsReStoringSCHParams.SOFT_ZONE_ID, line.getValue(KEY_HIDDEN_SOFT_ZONE_ID));
                lineparam.set(FaAsReStoringSCHParams.WORK_NO, line.getValue(KEY_LST_WORK_NO));
                lineparam.set(FaAsReStoringSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
                lineparam.set(FaAsReStoringSCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
                lineparam.set(FaAsReStoringSCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
                lineparam.set(FaAsReStoringSCHParams.PRINT_FLAG, chk_LWorkListPrint.getValue());
                lineparam.set(FaAsReStoringSCHParams.SETTING_UNIT_KEY, "");
                lineparam.set(FaAsReStoringSCHParams.FUNCTION_ID, "");
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
                _lcm_lst_FaAsReStoring.resetEditRow();
                _lcm_lst_FaAsReStoring.resetHighlight();
                _lcm_lst_FaAsReStoring.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_FaAsReStoring.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaAsReStoring.get(i);
                lst_FaAsReStoring.setCurrentRow(i);

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

                if (chk_LWorkListPrint.getChecked())
                {
                    part11List.add("1", "");
                }
                else
                {
                    part11List.add("0", "");
                }

                part11List.add(line.getViewString(KEY_LST_WORK_NO), "");
                part11List.add(line.getViewString(KEY_HIDDEN_SOFT_ZONE_ID), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_FaAsReStoring.resetEditRow();
            _lcm_lst_FaAsReStoring.resetHighlight();

            // clear.
            pul_Area.setEnabled(true);
            pul_WorkPlace.setEnabled(true);
            pul_Station.setEnabled(true);
            _lcm_lst_FaAsReStoring.clear();
            btn_Set.setEnabled(false);
            btn_AllClear.setEnabled(false);
            chk_LWorkListPrint.setEnabled(false);
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
        _lcm_lst_FaAsReStoring.clear();
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        btn_WorkListPrint.setEnabled(false);
        chk_LWorkListPrint.setEnabled(false);
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
            inparam.set(FaStorageListDASCHParams.WORK_TYPE, StorageInParameter.SEARCH_ASRS_RESTORING_LIST);

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
            exporter = factory.newPrinterExporter("ReStoringWorkList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ReStoringWorkListParams expparam = new ReStoringWorkListParams();
                expparam.set(ReStoringWorkListParams.DFK_DS_NO, outparam.get(FaStorageListDASCHParams.DFK_DS_NO));
                expparam.set(ReStoringWorkListParams.DFK_USER_ID, outparam.get(FaStorageListDASCHParams.DFK_USER_ID));
                expparam.set(ReStoringWorkListParams.DFK_USER_NAME, outparam.get(FaStorageListDASCHParams.DFK_USER_NAME));
                expparam.set(ReStoringWorkListParams.SYS_DAY, outparam.get(FaStorageListDASCHParams.SYS_DAY));
                expparam.set(ReStoringWorkListParams.SYS_TIME, outparam.get(FaStorageListDASCHParams.SYS_TIME));
                expparam.set(ReStoringWorkListParams.STATION_NO, outparam.get(FaStorageListDASCHParams.STATION_NO));
                expparam.set(ReStoringWorkListParams.STATION_NAME, outparam.get(FaStorageListDASCHParams.STATION_NAME));
                expparam.set(ReStoringWorkListParams.WORK_NO, outparam.get(FaStorageListDASCHParams.WORK_NO));
                expparam.set(ReStoringWorkListParams.LOCATION_NO, outparam.get(FaStorageListDASCHParams.LOCATION_NO));
                expparam.set(ReStoringWorkListParams.ITEM_CODE, outparam.get(FaStorageListDASCHParams.ITEM_CODE));
                expparam.set(ReStoringWorkListParams.ITEM_NAME, outparam.get(FaStorageListDASCHParams.ITEM_NAME));
                expparam.set(ReStoringWorkListParams.LOT_NO, outparam.get(FaStorageListDASCHParams.LOT_NO));
                expparam.set(ReStoringWorkListParams.WORK_QTY, outparam.get(FaStorageListDASCHParams.WORK_QTY));
                expparam.set(ReStoringWorkListParams.AREA_NO, outparam.get(FaStorageListDASCHParams.AREA_NO));
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
        int row = lst_FaAsReStoring.getActiveRow();
        lst_FaAsReStoring.setCurrentRow(row);
        ListCellLine line = _lcm_lst_FaAsReStoring.get(row);

        // output display.
        _pdm_pul_SoftZone.setSelectedValue(line.getValue(KEY_HIDDEN_SOFT_ZONE_ID));
        txt_WorkNo.setValue(line.getValue(KEY_LST_WORK_NO));

        // highlight active row.
        _lcm_lst_FaAsReStoring.resetHighlight();
        _lcm_lst_FaAsReStoring.addHighlight(row);
        _lcm_lst_FaAsReStoring.setEditRow(row);
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_FaAsReStoring.getActiveRow();
        lst_FaAsReStoring.setCurrentRow(row);

        // reset editing row.
        lst_FaAsReStoring.removeRow(row);
        _lcm_lst_FaAsReStoring.resetEditRow();
        _lcm_lst_FaAsReStoring.resetHighlight();
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