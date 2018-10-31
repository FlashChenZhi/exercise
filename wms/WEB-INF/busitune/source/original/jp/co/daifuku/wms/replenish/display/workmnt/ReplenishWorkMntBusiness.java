// $Id: ReplenishWorkMntBusiness.java 7544 2010-03-15 00:25:30Z ota $
package jp.co.daifuku.wms.replenish.display.workmnt;

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
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
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
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
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
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.replenish.dasch.ReplenishWorkDASCH;
import jp.co.daifuku.wms.replenish.dasch.ReplenishWorkDASCHParams;
import jp.co.daifuku.wms.replenish.display.workmnt.ReplenishWorkMnt;
import jp.co.daifuku.wms.replenish.display.workmnt.ViewStateKeys;
import jp.co.daifuku.wms.replenish.exporter.ReplenishmentListParams;
import jp.co.daifuku.wms.replenish.schedule.ReplenishWorkMntSCH;
import jp.co.daifuku.wms.replenish.schedule.ReplenishWorkMntSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7544 $, $Date:: 2010-03-15 09:25:30 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class ReplenishWorkMntBusiness
        extends ReplenishWorkMnt
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ReplenishmentCancelRePrint(LST_AREA_TYPE) */
    private static final ListCellKey KEY_LST_AREA_TYPE = new ListCellKey("LST_AREA_TYPE", new StringCellColumn(), false, false);

    /** lst_ReplenishmentCancelRePrint(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_ReplenishmentCancelRePrint(LST_SETTING_UNIT_KEY) */
    private static final ListCellKey KEY_LST_SETTING_UNIT_KEY = new ListCellKey("LST_SETTING_UNIT_KEY", new StringCellColumn(), true, false);

    /** lst_ReplenishmentCancelRePrint(LST_RETRIEVAL_AREA_NO) */
    private static final ListCellKey KEY_LST_RETRIEVAL_AREA_NO = new ListCellKey("LST_RETRIEVAL_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_ReplenishmentCancelRePrint(LST_FROM_LOCATION_NO) */
    private static final ListCellKey KEY_LST_FROM_LOCATION_NO = new ListCellKey("LST_FROM_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_ReplenishmentCancelRePrint(LST_TO_LOCATION_NO) */
    private static final ListCellKey KEY_LST_TO_LOCATION_NO = new ListCellKey("LST_TO_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_ReplenishmentCancelRePrint(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ReplenishmentCancelRePrint(LST_START_DATETIME) */
    private static final ListCellKey KEY_LST_START_DATETIME = new ListCellKey("LST_START_DATETIME", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_ReplenishmentCancelRePrint(LST_ITEM_COUNT) */
    private static final ListCellKey KEY_LST_ITEM_COUNT = new ListCellKey("LST_ITEM_COUNT", new NumberCellColumn(0), true, false);

    /** lst_ReplenishmentCancelRePrint keys */
    private static final ListCellKey[] LST_REPLENISHMENTCANCELREPRINT_KEYS = {
        KEY_LST_AREA_TYPE,
        KEY_LST_SELECT,
        KEY_LST_SETTING_UNIT_KEY,
        KEY_LST_RETRIEVAL_AREA_NO,
        KEY_LST_FROM_LOCATION_NO,
        KEY_LST_TO_LOCATION_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_START_DATETIME,
        KEY_LST_ITEM_COUNT,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel ReplenishmentWorkFlag */
    private RadioButtonGroup _grp_ReplenishmentWorkFlag;

    /** ListCellModel lst_ReplenishmentCancelRePrint */
    private ListCellModel _lcm_lst_ReplenishmentCancelRePrint;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ReplenishWorkMntBusiness()
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
    public void btn_RePrint_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_RePrint_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_WorkCancel_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_WorkCancel_Click_Process();
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
    public void btn_AllClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllClear_Click_Process();
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
        Locale locale = httpRequest.getLocale();

        // initialize ReplenishmentWorkFlag.
        _grp_ReplenishmentWorkFlag = new RadioButtonGroup(new RadioButton[]{rdo_ReplenishmentWorkFlagPlan, rdo_ReplenishmentWorkFlagUgt}, locale);

        // initialize lst_ReplenishmentCancelRePrint.
        _lcm_lst_ReplenishmentCancelRePrint = new ListCellModel(lst_ReplenishmentCancelRePrint, LST_REPLENISHMENTCANCELREPRINT_KEYS, locale);
        _lcm_lst_ReplenishmentCancelRePrint.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_ReplenishmentCancelRePrint.setToolTipVisible(KEY_LST_SETTING_UNIT_KEY, false);
        _lcm_lst_ReplenishmentCancelRePrint.setToolTipVisible(KEY_LST_RETRIEVAL_AREA_NO, false);
        _lcm_lst_ReplenishmentCancelRePrint.setToolTipVisible(KEY_LST_FROM_LOCATION_NO, false);
        _lcm_lst_ReplenishmentCancelRePrint.setToolTipVisible(KEY_LST_TO_LOCATION_NO, false);
        _lcm_lst_ReplenishmentCancelRePrint.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_ReplenishmentCancelRePrint.setToolTipVisible(KEY_LST_START_DATETIME, false);
        _lcm_lst_ReplenishmentCancelRePrint.setToolTipVisible(KEY_LST_ITEM_COUNT, false);
    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
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
    private void lst_ReplenishmentCancelRePrint_SetLineToolTip(ListCellLine line)
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
        setFocus(rdo_ReplenishmentWorkFlagPlan);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        rdo_ReplenishmentWorkFlagPlan.setChecked(true);
        btn_RePrint.setEnabled(false);
        btn_WorkCancel.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
        _lcm_lst_ReplenishmentCancelRePrint.clear();
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReplenishWorkMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReplenishWorkMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ReplenishWorkMntSCHParams inparam = new ReplenishWorkMntSCHParams();
            inparam.set(ReplenishWorkMntSCHParams.SELECTED_JOB_TYPE, _grp_ReplenishmentWorkFlag.getSelectedValue());
            inparam.set(ReplenishWorkMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ReplenishmentCancelRePrint.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReplenishmentCancelRePrint.getNewLine();
                line.setValue(KEY_LST_AREA_TYPE, outparam.get(ReplenishWorkMntSCHParams.AREA_TYPE));
                line.setValue(KEY_LST_SETTING_UNIT_KEY, outparam.get(ReplenishWorkMntSCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_LST_RETRIEVAL_AREA_NO, outparam.get(ReplenishWorkMntSCHParams.RETRIEVAL_AREA_NO));
                line.setValue(KEY_LST_FROM_LOCATION_NO, outparam.get(ReplenishWorkMntSCHParams.FROM_LOCATION_NO));
                line.setValue(KEY_LST_TO_LOCATION_NO, outparam.get(ReplenishWorkMntSCHParams.TO_LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(ReplenishWorkMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_START_DATETIME, outparam.get(ReplenishWorkMntSCHParams.START_DATETIME));
                line.setValue(KEY_LST_ITEM_COUNT, outparam.get(ReplenishWorkMntSCHParams.ITEM_COUNT));
                viewState.setObject(ViewStateKeys.SELECTED_JOB_TYPE, _grp_ReplenishmentWorkFlag.getSelectedValue());
                viewState.setObject(ViewStateKeys.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lst_ReplenishmentCancelRePrint_SetLineToolTip(line);
                _lcm_lst_ReplenishmentCancelRePrint.add(line);
            }

            // clear.
            btn_RePrint.setEnabled(true);
            btn_WorkCancel.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
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
    private void btn_RePrint_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_ReplenishmentCancelRePrint.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_ReplenishmentCancelRePrint.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReplenishWorkDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new ReplenishWorkDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            main: for (int i = 1; i <= _lcm_lst_ReplenishmentCancelRePrint.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ReplenishmentCancelRePrint.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // set input parameters.
                ReplenishWorkDASCHParams inparam = new ReplenishWorkDASCHParams();
                inparam.set(ReplenishWorkDASCHParams.SETTING_UKEYS, line.getValue(KEY_LST_SETTING_UNIT_KEY));

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
                if (exporter == null)
                {
                    ExporterFactory factory = new WmsExporterFactory(locale, ui);
                    exporter = factory.newPrinterExporter("ReplenishmentList", false);
                    exporter.open();
                }

                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    ReplenishmentListParams expparam = new ReplenishmentListParams();
                    expparam.set(ReplenishmentListParams.DFK_DS_NO, outparam.get(ReplenishWorkDASCHParams.DFK_DS_NO));
                    expparam.set(ReplenishmentListParams.DFK_USER_ID, outparam.get(ReplenishWorkDASCHParams.DFK_USER_ID));
                    expparam.set(ReplenishmentListParams.DFK_USER_NAME, outparam.get(ReplenishWorkDASCHParams.DFK_USER_NAME));
                    expparam.set(ReplenishmentListParams.SYS_DAY, outparam.get(ReplenishWorkDASCHParams.SYS_DAY));
                    expparam.set(ReplenishmentListParams.SYS_TIME, outparam.get(ReplenishWorkDASCHParams.SYS_TIME));
                    expparam.set(ReplenishmentListParams.JOB_NO, outparam.get(ReplenishWorkDASCHParams.JOB_NO));
                    expparam.set(ReplenishmentListParams.REP_RETRIEVAL_AREA_NO, outparam.get(ReplenishWorkDASCHParams.REP_RETRIEVAL_AREA_NO));
                    expparam.set(ReplenishmentListParams.REP_RETRIEVAL_AREA_NAME, outparam.get(ReplenishWorkDASCHParams.REP_RETRIEVAL_AREA_NAME));
                    expparam.set(ReplenishmentListParams.JOB_TYPE, outparam.get(ReplenishWorkDASCHParams.JOB_TYPE));
                    expparam.set(ReplenishmentListParams.REP_RETRIEVAL_LOCATION_NO, outparam.get(ReplenishWorkDASCHParams.REP_RETRIEVAL_LOCATION_NO));
                    expparam.set(ReplenishmentListParams.ITEM_CODE, outparam.get(ReplenishWorkDASCHParams.ITEM_CODE));
                    expparam.set(ReplenishmentListParams.ITEM_NAME, outparam.get(ReplenishWorkDASCHParams.ITEM_NAME));
                    expparam.set(ReplenishmentListParams.LOT_NO, outparam.get(ReplenishWorkDASCHParams.LOT_NO));
                    expparam.set(ReplenishmentListParams.ENTERING_QTY, outparam.get(ReplenishWorkDASCHParams.ENTERING_QTY));
                    expparam.set(ReplenishmentListParams.PLAN_CASE_QTY, outparam.get(ReplenishWorkDASCHParams.PLAN_CASE_QTY));
                    expparam.set(ReplenishmentListParams.PLAN_PIECE_QTY, outparam.get(ReplenishWorkDASCHParams.PLAN_PIECE_QTY));
                    expparam.set(ReplenishmentListParams.REP_STORAGE_AREA_NO, outparam.get(ReplenishWorkDASCHParams.REP_STORAGE_AREA_NO));
                    expparam.set(ReplenishmentListParams.REP_LOCATION_NO, outparam.get(ReplenishWorkDASCHParams.REP_LOCATION_NO));
                    if (!exporter.write(expparam))
                    {
                        break main;
                    }
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
            for (int i = 1; i <= _lcm_lst_ReplenishmentCancelRePrint.size(); i++)
            {
                ListCellLine line = _lcm_lst_ReplenishmentCancelRePrint.get(i);
                lst_ReplenishmentCancelRePrint.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                if (rdo_ReplenishmentWorkFlagPlan.getChecked())
                {
                    part11List.add(SystemDefine.JOB_TYPE_NORMAL_REPLENISHMENT, "");
                    part11List.add(line.getViewString(KEY_LST_SETTING_UNIT_KEY), "");
                    part11List.add(line.getViewString(KEY_LST_RETRIEVAL_AREA_NO), "");
                    part11List.add(line.getViewString(KEY_LST_FROM_LOCATION_NO), "");
                    part11List.add(line.getViewString(KEY_LST_TO_LOCATION_NO), "");
                    part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                    part11List.add(line.getViewString(KEY_LST_START_DATETIME), "");
                    part11List.add(line.getViewString(KEY_LST_ITEM_COUNT), "");
                }
                else
                {
                    part11List.add(SystemDefine.JOB_TYPE_EMERGENCY_REPLENISHMENT, "");
                    part11List.add(line.getViewString(KEY_LST_SETTING_UNIT_KEY), "");
                    part11List.add(line.getViewString(KEY_LST_RETRIEVAL_AREA_NO), "");
                    part11List.add(line.getViewString(KEY_LST_FROM_LOCATION_NO), "");
                    part11List.add(line.getViewString(KEY_LST_TO_LOCATION_NO), "");
                    part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                    part11List.add(line.getViewString(KEY_LST_START_DATETIME), "");
                    part11List.add(line.getViewString(KEY_LST_ITEM_COUNT), "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
            }
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
    private void btn_WorkCancel_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReplenishWorkMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReplenishWorkMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ReplenishmentCancelRePrint.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ReplenishmentCancelRePrint.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                ReplenishWorkMntSCHParams lineparam = new ReplenishWorkMntSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(ReplenishWorkMntSCHParams.SETTING_UNIT_KEY, line.getValue(KEY_LST_SETTING_UNIT_KEY));
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
                _lcm_lst_ReplenishmentCancelRePrint.resetEditRow();
                _lcm_lst_ReplenishmentCancelRePrint.resetHighlight();
                _lcm_lst_ReplenishmentCancelRePrint.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ReplenishmentCancelRePrint.size(); i++)
            {
                ListCellLine line = _lcm_lst_ReplenishmentCancelRePrint.get(i);
                lst_ReplenishmentCancelRePrint.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                if (rdo_ReplenishmentWorkFlagPlan.getChecked())
                {
                    part11List.add(SystemDefine.JOB_TYPE_NORMAL_REPLENISHMENT, "");
                    part11List.add(line.getViewString(KEY_LST_SETTING_UNIT_KEY), "");
                    part11List.add(line.getViewString(KEY_LST_RETRIEVAL_AREA_NO), "");
                    part11List.add(line.getViewString(KEY_LST_FROM_LOCATION_NO), "");
                    part11List.add(line.getViewString(KEY_LST_TO_LOCATION_NO), "");
                    part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                    part11List.add(line.getViewString(KEY_LST_START_DATETIME), "");
                    part11List.add(line.getViewString(KEY_LST_ITEM_COUNT), "");
                }
                else
                {
                    part11List.add(SystemDefine.JOB_TYPE_EMERGENCY_REPLENISHMENT, "");
                    part11List.add(line.getViewString(KEY_LST_SETTING_UNIT_KEY), "");
                    part11List.add(line.getViewString(KEY_LST_RETRIEVAL_AREA_NO), "");
                    part11List.add(line.getViewString(KEY_LST_FROM_LOCATION_NO), "");
                    part11List.add(line.getViewString(KEY_LST_TO_LOCATION_NO), "");
                    part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                    part11List.add(line.getViewString(KEY_LST_START_DATETIME), "");
                    part11List.add(line.getViewString(KEY_LST_ITEM_COUNT), "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ReplenishmentCancelRePrint.resetEditRow();
            _lcm_lst_ReplenishmentCancelRePrint.resetHighlight();

            // set input parameters.
            ReplenishWorkMntSCHParams inparam = new ReplenishWorkMntSCHParams();
            inparam.set(ReplenishWorkMntSCHParams.CONSIGNOR_CODE, viewState.getObject(ViewStateKeys.CONSIGNOR_CODE));
            inparam.set(ReplenishWorkMntSCHParams.SELECTED_JOB_TYPE, viewState.getObject(ViewStateKeys.SELECTED_JOB_TYPE));

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_ReplenishmentCancelRePrint.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReplenishmentCancelRePrint.getNewLine();
                line.setValue(KEY_LST_AREA_TYPE, outparam.get(ReplenishWorkMntSCHParams.AREA_TYPE));
                line.setValue(KEY_LST_SETTING_UNIT_KEY, outparam.get(ReplenishWorkMntSCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_LST_RETRIEVAL_AREA_NO, outparam.get(ReplenishWorkMntSCHParams.RETRIEVAL_AREA_NO));
                line.setValue(KEY_LST_FROM_LOCATION_NO, outparam.get(ReplenishWorkMntSCHParams.FROM_LOCATION_NO));
                line.setValue(KEY_LST_TO_LOCATION_NO, outparam.get(ReplenishWorkMntSCHParams.TO_LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(ReplenishWorkMntSCHParams.ITEM_CODE));
                line.setValue(KEY_LST_START_DATETIME, outparam.get(ReplenishWorkMntSCHParams.START_DATETIME));
                line.setValue(KEY_LST_ITEM_COUNT, outparam.get(ReplenishWorkMntSCHParams.ITEM_COUNT));
                lst_ReplenishmentCancelRePrint_SetLineToolTip(line);
                _lcm_lst_ReplenishmentCancelRePrint.add(line);
            }

            // clear.
            btn_RePrint.setEnabled(false);
            btn_WorkCancel.setEnabled(false);
            btn_AllCheck.setEnabled(false);
            btn_AllCheckClear.setEnabled(false);
            btn_AllClear.setEnabled(false);
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
        for (int i = 1; i <= _lcm_lst_ReplenishmentCancelRePrint.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_ReplenishmentCancelRePrint.get(i);
            lst_ReplenishmentCancelRePrint.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_ReplenishmentCancelRePrint_SetLineToolTip(clearLine);
            _lcm_lst_ReplenishmentCancelRePrint.set(i, clearLine);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_ReplenishmentCancelRePrint.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_ReplenishmentCancelRePrint.get(i);
            lst_ReplenishmentCancelRePrint.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_ReplenishmentCancelRePrint_SetLineToolTip(clearLine);
            _lcm_lst_ReplenishmentCancelRePrint.set(i, clearLine);
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
        _lcm_lst_ReplenishmentCancelRePrint.clear();
        btn_RePrint.setEnabled(false);
        btn_WorkCancel.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
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
