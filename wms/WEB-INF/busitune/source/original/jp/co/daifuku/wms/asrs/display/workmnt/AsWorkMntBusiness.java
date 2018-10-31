// $Id: AsWorkMntBusiness.java 7837 2010-04-21 02:13:53Z kishimoto $
package jp.co.daifuku.wms.asrs.display.workmnt;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
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
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.AsWorkMntExporterDASCH;
import jp.co.daifuku.wms.asrs.dasch.AsWorkMntExporterDASCHParams;
import jp.co.daifuku.wms.asrs.display.workmnt.AsWorkMnt;
import jp.co.daifuku.wms.asrs.exporter.CarryListParams;
import jp.co.daifuku.wms.asrs.listbox.carry.LstAsCarryParams;
import jp.co.daifuku.wms.asrs.listbox.workdetail.LstAsWorkDetailParams;
import jp.co.daifuku.wms.asrs.schedule.AsWorkMntSCH;
import jp.co.daifuku.wms.asrs.schedule.AsWorkMntSCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7837 $, $Date:: 2010-04-21 11:13:53 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class AsWorkMntBusiness
        extends AsWorkMnt
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_WorkPlace */
    private WmsWorkspacePullDownModel _pdm_pul_WorkPlace;

    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    /** RadioButtonGroupModel ASRSProcess */
    private RadioButtonGroup _grp_ASRSProcess;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsWorkMntBusiness()
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
        if (eventSource.equals("btn_PInquiry_Click"))
        {
            // process call.
            btn_PInquiry_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_PSTOperation_Click"))
        {
            // process call.
            btn_PSTOperation_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_Details_Click"))
        {
            // process call.
            btn_Details_Click_DlgBack(dialogParams);
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
        if (eventSource.equals("btn_Print_Click"))
        {
            // process call.
            btn_Print_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_PInquiry_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PInquiry_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Print_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Print_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_XLS_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_XLS_Click_Process();
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
    public void btn_ReDisplay_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplay_Click_Process();
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
    public void btn_PSTOperation_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSTOperation_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Details_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Details_Click_Process();
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

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
        _pdm_pul_Station.setParent(_pdm_pul_WorkPlace);

        // initialize ASRSProcess.
        _grp_ASRSProcess = new RadioButtonGroup(new RadioButton[]{rdo_ASRSProcess_NoIndication, rdo_ASRSProcess_CancelAllocate, rdo_ASRSProcess_TrackingDelete, rdo_ASRSProcess_Finished}, locale);

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

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.WORK_MNT, WmsParam.ALL_STATION);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.WORK_MNT, Distribution.ALL);

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
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(pul_WorkPlace);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        rdo_ASRSProcess_NoIndication.setChecked(true);
        btn_Set.setEnabled(false);
        btn_ReDisplay.setEnabled(false);
        btn_Clear.setEnabled(false);
        txt_FromCarrying.setReadOnly(true);
        txt_ToCarrying.setReadOnly(true);
        txt_Location.setReadOnly(true);
        txt_WorkKind.setReadOnly(true);
        txt_RetrievalDetail.setReadOnly(true);
        txt_WorkNo.setReadOnly(true);
        btn_Details.setEnabled(false);
        txt_ScheduleNo.setReadOnly(true);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_PInquiry_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstAsCarryParams inparam = new LstAsCarryParams();
        inparam.set(LstAsCarryParams.WORK_PLACE_KEY, _pdm_pul_WorkPlace.getSelectedValue());
        inparam.set(LstAsCarryParams.STATION_NO_KEY, _pdm_pul_Station.getSelectedValue());
        inparam.set(LstAsCarryParams.TERMINAL_NO_KEY, "TERMINALNUMBER");

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PInquiry_Click");
        redirect("/asrs/listbox/carry/LstAsCarry.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PInquiry_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstAsCarryParams outparam = new LstAsCarryParams(dialogParams);
        txt_CarryingKey.setValue(outparam.get(LstAsCarryParams.CARRY_KEY));
        txt_WorkNo.setValue(outparam.get(LstAsCarryParams.WORK_NO));
        txt_WorkKind.setValue(outparam.get(LstAsCarryParams.WORK_TYPE_NAME));
        txt_RetrievalDetail.setValue(outparam.get(LstAsCarryParams.RETRIEVAL_DETAIL_NAME));
        txt_FromCarrying.setValue(outparam.get(LstAsCarryParams.SOURCE_STATION_NAME));
        txt_ToCarrying.setValue(outparam.get(LstAsCarryParams.DEST_STATION_NAME));
        txt_CarryingFlag.setValue(outparam.get(LstAsCarryParams.CARRY_FLAG_NAME));
        txt_CarryingStatus.setValue(outparam.get(LstAsCarryParams.CMD_STATUS_NAME));
        txt_Location.setValue(outparam.get(LstAsCarryParams.LOCATION_NO));
        txt_ScheduleNo.setValue(outparam.get(LstAsCarryParams.SCHEDULE_NO));

    }

    /**
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Print_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsWorkMntExporterDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsWorkMntExporterDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsWorkMntExporterDASCHParams inparam = new AsWorkMntExporterDASCHParams();
            inparam.set(AsWorkMntExporterDASCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsWorkMntExporterDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsWorkMntExporterDASCHParams.TERMINAL_NO, "TERMINAL_NO");

            // check count.
            int count = dasch.count(inparam);
            if (confirm && count > 0)
            {
                // show confirm message.
                this.setConfirm("MSG-W0018\t" + Formatter.getNumFormat(count), false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Print_Click");
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
            exporter = factory.newPrinterExporter("CarryList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                CarryListParams expparam = new CarryListParams();
                expparam.set(CarryListParams.DFK_DS_NO, outparam.get(AsWorkMntExporterDASCHParams.DFK_DS_NO));
                expparam.set(CarryListParams.DFK_USER_ID, outparam.get(AsWorkMntExporterDASCHParams.DFK_USER_ID));
                expparam.set(CarryListParams.DFK_USER_NAME, outparam.get(AsWorkMntExporterDASCHParams.DFK_USER_NAME));
                expparam.set(CarryListParams.FROM_STATION, outparam.get(AsWorkMntExporterDASCHParams.FROM_STATION_NO));
                expparam.set(CarryListParams.ITEM_CODE, outparam.get(AsWorkMntExporterDASCHParams.ITEM_CODE));
                expparam.set(CarryListParams.ITEM_NAME, outparam.get(AsWorkMntExporterDASCHParams.ITEM_NAME));
                expparam.set(CarryListParams.LOCATION_NO, outparam.get(AsWorkMntExporterDASCHParams.LOCATION_NO));
                expparam.set(CarryListParams.LOT_NO, outparam.get(AsWorkMntExporterDASCHParams.LOT_NO));
                expparam.set(CarryListParams.PRIORITY_FLAG, outparam.get(AsWorkMntExporterDASCHParams.PRIORITY_FLAG));
                expparam.set(CarryListParams.RETRIEVAL_COMMAND_DETAIL, outparam.get(AsWorkMntExporterDASCHParams.RETRIEVAL_COMMAND_DETAIL));
                expparam.set(CarryListParams.STATION_NAME, _pdm_pul_Station.getSelectedValue());
                expparam.set(CarryListParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
                expparam.set(CarryListParams.SYS_DAY, outparam.get(AsWorkMntExporterDASCHParams.SYS_DAY));
                expparam.set(CarryListParams.SYS_TIME, outparam.get(AsWorkMntExporterDASCHParams.SYS_TIME));
                expparam.set(CarryListParams.TO_LOCATION_NO, outparam.get(AsWorkMntExporterDASCHParams.TO_LOCATION_NO));
                expparam.set(CarryListParams.TO_STATION, outparam.get(AsWorkMntExporterDASCHParams.TO_STATION_NO));
                expparam.set(CarryListParams.TRANSPORT_TYPE, outparam.get(AsWorkMntExporterDASCHParams.TRANSPORT_TYPE));
                expparam.set(CarryListParams.WORK_AREA, _pdm_pul_WorkPlace.getSelectedValue());
                expparam.set(CarryListParams.WORK_NO, outparam.get(AsWorkMntExporterDASCHParams.WORK_NO));
                expparam.set(CarryListParams.WORK_QTY, outparam.get(AsWorkMntExporterDASCHParams.WORK_QTY));
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
            part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
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
    private void btn_XLS_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsWorkMntExporterDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsWorkMntExporterDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsWorkMntExporterDASCHParams inparam = new AsWorkMntExporterDASCHParams();
            inparam.set(AsWorkMntExporterDASCHParams.WORK_PLACE, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsWorkMntExporterDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsWorkMntExporterDASCHParams.TERMINAL_NO, "TERMINAL_NO");

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("CarryList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                CarryListParams expparam = new CarryListParams();
                expparam.set(CarryListParams.DFK_DS_NO, outparam.get(AsWorkMntExporterDASCHParams.DFK_DS_NO));
                expparam.set(CarryListParams.DFK_USER_ID, outparam.get(AsWorkMntExporterDASCHParams.DFK_USER_ID));
                expparam.set(CarryListParams.DFK_USER_NAME, outparam.get(AsWorkMntExporterDASCHParams.DFK_USER_NAME));
                expparam.set(CarryListParams.FROM_STATION, outparam.get(AsWorkMntExporterDASCHParams.FROM_STATION_NO));
                expparam.set(CarryListParams.ITEM_CODE, outparam.get(AsWorkMntExporterDASCHParams.ITEM_CODE));
                expparam.set(CarryListParams.ITEM_NAME, outparam.get(AsWorkMntExporterDASCHParams.ITEM_NAME));
                expparam.set(CarryListParams.LOCATION_NO, outparam.get(AsWorkMntExporterDASCHParams.LOCATION_NO));
                expparam.set(CarryListParams.LOT_NO, outparam.get(AsWorkMntExporterDASCHParams.LOT_NO));
                expparam.set(CarryListParams.PRIORITY_FLAG, outparam.get(AsWorkMntExporterDASCHParams.PRIORITY_FLAG));
                expparam.set(CarryListParams.RETRIEVAL_COMMAND_DETAIL, outparam.get(AsWorkMntExporterDASCHParams.RETRIEVAL_COMMAND_DETAIL));
                expparam.set(CarryListParams.STATION_NAME, _pdm_pul_Station.getSelectedValue());
                expparam.set(CarryListParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
                expparam.set(CarryListParams.SYS_DAY, outparam.get(AsWorkMntExporterDASCHParams.SYS_DAY));
                expparam.set(CarryListParams.SYS_TIME, outparam.get(AsWorkMntExporterDASCHParams.SYS_TIME));
                expparam.set(CarryListParams.TO_LOCATION_NO, outparam.get(AsWorkMntExporterDASCHParams.TO_LOCATION_NO));
                expparam.set(CarryListParams.TO_STATION, outparam.get(AsWorkMntExporterDASCHParams.TO_STATION_NO));
                expparam.set(CarryListParams.TRANSPORT_TYPE, outparam.get(AsWorkMntExporterDASCHParams.TRANSPORT_TYPE));
                expparam.set(CarryListParams.WORK_AREA, _pdm_pul_WorkPlace.getSelectedValue());
                expparam.set(CarryListParams.WORK_NO, outparam.get(AsWorkMntExporterDASCHParams.WORK_NO));
                expparam.set(CarryListParams.WORK_QTY, outparam.get(AsWorkMntExporterDASCHParams.WORK_QTY));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_XLS), part11List);
            // commit.
            conn.commit();

            // redirect.
            download(downloadFile.getPath());

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
    private void btn_Set_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsWorkMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsWorkMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsWorkMntSCHParams inparam = new AsWorkMntSCHParams();
            inparam.setProcessFlag(ProcessFlag.REGIST);
            inparam.set(AsWorkMntSCHParams.ASRS_PROCESS, _grp_ASRSProcess.getSelectedValue());
            inparam.set(AsWorkMntSCHParams.CARRYING_KEY, txt_CarryingKey.getValue());

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // clear.
            btn_Set.setEnabled(false);
            btn_ReDisplay.setEnabled(false);
            btn_Clear.setEnabled(false);
            txt_FromCarrying.setValue(null);
            txt_FromCarrying.setReadOnly(true);
            txt_ToCarrying.setValue(null);
            txt_ToCarrying.setReadOnly(true);
            txt_Location.setValue(null);
            txt_Location.setReadOnly(true);
            txt_CarryingFlag.setValue(null);
            txt_CarryingStatus.setValue(null);
            txt_WorkKind.setValue(null);
            txt_WorkKind.setReadOnly(true);
            txt_RetrievalDetail.setValue(null);
            txt_RetrievalDetail.setReadOnly(true);
            txt_WorkNo.setValue(null);
            txt_WorkNo.setReadOnly(true);
            txt_ScheduleNo.setValue(null);
            txt_ScheduleNo.setReadOnly(true);
            btn_Details.setEnabled(false);
            txt_CarryingKey.setValue(null);
            rdo_ASRSProcess_NoIndication.setChecked(true);

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
    private void btn_ReDisplay_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsWorkMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsWorkMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsWorkMntSCHParams inparam = new AsWorkMntSCHParams();
            inparam.set(AsWorkMntSCHParams.CARRYING_KEY, txt_CarryingKey.getValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            for (Params outparam : outparams)
            {
                txt_FromCarrying.setValue(outparam.get(AsWorkMntSCHParams.FROM_CARRYING_NAME));
                txt_ToCarrying.setValue(outparam.get(AsWorkMntSCHParams.TO_CARRYING_NAME));
                txt_Location.setValue(outparam.get(AsWorkMntSCHParams.LOCATION));
                txt_CarryingFlag.setValue(outparam.get(AsWorkMntSCHParams.CARRYING_FLAG_NAME));
                txt_CarryingStatus.setValue(outparam.get(AsWorkMntSCHParams.CARRYING_STATUS_NAME));
                txt_WorkKind.setValue(outparam.get(AsWorkMntSCHParams.WORK_KIND_NAME));
                txt_RetrievalDetail.setValue(outparam.get(AsWorkMntSCHParams.RETRIEVAL_DETAIL_NAME));
                txt_WorkNo.setValue(outparam.get(AsWorkMntSCHParams.WORK_NO));
                txt_ScheduleNo.setValue(outparam.get(AsWorkMntSCHParams.SCHEDULE_NO));
                txt_CarryingKey.setValue(outparam.get(AsWorkMntSCHParams.CARRYING_KEY));
            }

            // clear.
            rdo_ASRSProcess_NoIndication.setEnabled(true);
            rdo_ASRSProcess_Finished.setEnabled(true);
            rdo_ASRSProcess_CancelAllocate.setEnabled(true);
            rdo_ASRSProcess_TrackingDelete.setEnabled(true);
            btn_Details.setEnabled(true);

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
        btn_Set.setEnabled(false);
        btn_ReDisplay.setEnabled(false);
        btn_Clear.setEnabled(false);
        btn_Details.setEnabled(false);
        txt_FromCarrying.setValue(null);
        txt_ToCarrying.setValue(null);
        txt_Location.setValue(null);
        txt_CarryingFlag.setValue(null);
        txt_CarryingStatus.setValue(null);
        txt_WorkKind.setValue(null);
        txt_RetrievalDetail.setValue(null);
        txt_WorkNo.setValue(null);
        txt_ScheduleNo.setValue(null);
        txt_CarryingKey.setValue(null);
        txt_FromCarrying.setReadOnly(true);
        txt_ToCarrying.setReadOnly(true);
        txt_Location.setReadOnly(true);
        txt_WorkKind.setReadOnly(true);
        txt_RetrievalDetail.setReadOnly(true);
        txt_ScheduleNo.setReadOnly(true);
        txt_WorkNo.setReadOnly(true);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSTOperation_Click_Process()
            throws Exception
    {
        // show dialog.
        ForwardParameters forwardParam = new ForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSTOperation_Click");
        redirect("/asrs/changestationpopup/AsChangeStationPopup.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSTOperation_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Details_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstAsWorkDetailParams inparam = new LstAsWorkDetailParams();
        inparam.set(LstAsWorkDetailParams.WORK_NO, txt_WorkNo.getValue());
        inparam.set(LstAsWorkDetailParams.CARRY_KEY, txt_CarryingKey.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_Details_Click");
        redirect("/asrs/listbox/workdetail/LstAsWorkDetail.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_Details_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
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
