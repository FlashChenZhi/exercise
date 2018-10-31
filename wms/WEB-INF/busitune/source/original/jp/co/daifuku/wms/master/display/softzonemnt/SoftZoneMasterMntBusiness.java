// $Id: SoftZoneMasterMntBusiness.java 7401 2010-03-05 12:08:12Z shibamoto $
package jp.co.daifuku.wms.master.display.softzonemnt;

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
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
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
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsSoftZonePullDownModel;
import jp.co.daifuku.wms.master.display.softzonemnt.SoftZoneMasterMnt;
import jp.co.daifuku.wms.master.schedule.SoftZoneMasterMntSCH;
import jp.co.daifuku.wms.master.schedule.SoftZoneMasterMntSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7401 $, $Date:: 2010-03-05 21:08:12 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class SoftZoneMasterMntBusiness
        extends SoftZoneMasterMnt
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ZoneMaintenanceRange(HIDDEN_BANK_FROM) */
    private static final ListCellKey KEY_HIDDEN_BANK_FROM = new ListCellKey("HIDDEN_BANK_FROM", new StringCellColumn(), false, false);

    /** lst_ZoneMaintenanceRange(HIDDEN_BANK_TO) */
    private static final ListCellKey KEY_HIDDEN_BANK_TO = new ListCellKey("HIDDEN_BANK_TO", new StringCellColumn(), false, false);

    /** lst_ZoneMaintenanceRange(HIDDEN_BAY_FROM) */
    private static final ListCellKey KEY_HIDDEN_BAY_FROM = new ListCellKey("HIDDEN_BAY_FROM", new StringCellColumn(), false, false);

    /** lst_ZoneMaintenanceRange(HIDDEN_BAY_TO) */
    private static final ListCellKey KEY_HIDDEN_BAY_TO = new ListCellKey("HIDDEN_BAY_TO", new StringCellColumn(), false, false);

    /** lst_ZoneMaintenanceRange(HIDDEN_LEVEL_FROM) */
    private static final ListCellKey KEY_HIDDEN_LEVEL_FROM = new ListCellKey("HIDDEN_LEVEL_FROM", new StringCellColumn(), false, false);

    /** lst_ZoneMaintenanceRange(HIDDEN_LEVEL_TO) */
    private static final ListCellKey KEY_HIDDEN_LEVEL_TO = new ListCellKey("HIDDEN_LEVEL_TO", new StringCellColumn(), false, false);

    /** lst_ZoneMaintenanceRange(HIDDEN_SOFT_ZONE) */
    private static final ListCellKey KEY_HIDDEN_SOFT_ZONE = new ListCellKey("HIDDEN_SOFT_ZONE", new StringCellColumn(), false, false);

    /** lst_ZoneMaintenanceRange(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_ZoneMaintenanceRange(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_ZoneMaintenanceRange(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_ZoneMaintenanceRange(LST_SOFT_ZONE_NAME) */
    private static final ListCellKey KEY_LST_SOFT_ZONE_NAME = new ListCellKey("LST_SOFT_ZONE_NAME", new StringCellColumn(), true, false);

    /** lst_ZoneMaintenanceRange(LST_BANK) */
    private static final ListCellKey KEY_LST_BANK = new ListCellKey("LST_BANK", new StringCellColumn(), true, false);

    /** lst_ZoneMaintenanceRange(LST_BAY) */
    private static final ListCellKey KEY_LST_BAY = new ListCellKey("LST_BAY", new StringCellColumn(), true, false);

    /** lst_ZoneMaintenanceRange(LST_LEVEL) */
    private static final ListCellKey KEY_LST_LEVEL = new ListCellKey("LST_LEVEL", new StringCellColumn(), true, false);

    /** lst_ZoneMaintenanceRange keys */
    private static final ListCellKey[] LST_ZONEMAINTENANCERANGE_KEYS = {
        KEY_HIDDEN_BANK_FROM,
        KEY_HIDDEN_BANK_TO,
        KEY_HIDDEN_BAY_FROM,
        KEY_HIDDEN_BAY_TO,
        KEY_HIDDEN_LEVEL_FROM,
        KEY_HIDDEN_LEVEL_TO,
        KEY_HIDDEN_SOFT_ZONE,
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_AREA_NO,
        KEY_LST_SOFT_ZONE_NAME,
        KEY_LST_BANK,
        KEY_LST_BAY,
        KEY_LST_LEVEL,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** PullDownModel pul_SoftZone */
    private WmsSoftZonePullDownModel _pdm_pul_SoftZone;

    /** ListCellModel lst_ZoneMaintenanceRange */
    private ListCellModel _lcm_lst_ZoneMaintenanceRange;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public SoftZoneMasterMntBusiness()
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
    public void lst_ZoneMaintenanceRange_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_ZoneMaintenanceRange.getActiveCol();

        // choose process.
        if (_lcm_lst_ZoneMaintenanceRange.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_ZoneMaintenanceRange.getColumnIndex(KEY_LST_CANCEL) == activeCol)
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

        // initialize pul_SoftZone.
        _pdm_pul_SoftZone = new WmsSoftZonePullDownModel(pul_SoftZone, locale, ui);
        _pdm_pul_SoftZone.setParent(_pdm_pul_Area);

        // initialize lst_ZoneMaintenanceRange.
        _lcm_lst_ZoneMaintenanceRange = new ListCellModel(lst_ZoneMaintenanceRange, LST_ZONEMAINTENANCERANGE_KEYS, locale);
        _lcm_lst_ZoneMaintenanceRange.setToolTipVisible(KEY_LST_MODIFY, false);
        _lcm_lst_ZoneMaintenanceRange.setToolTipVisible(KEY_LST_CANCEL, false);
        _lcm_lst_ZoneMaintenanceRange.setToolTipVisible(KEY_LST_AREA_NO, false);
        _lcm_lst_ZoneMaintenanceRange.setToolTipVisible(KEY_LST_SOFT_ZONE_NAME, false);
        _lcm_lst_ZoneMaintenanceRange.setToolTipVisible(KEY_LST_BANK, false);
        _lcm_lst_ZoneMaintenanceRange.setToolTipVisible(KEY_LST_BAY, false);
        _lcm_lst_ZoneMaintenanceRange.setToolTipVisible(KEY_LST_LEVEL, false);

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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.ALL, "", false);

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
    private void lst_ZoneMaintenanceRange_SetLineToolTip(ListCellLine line)
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
        setFocus(pul_Area);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        _pdm_pul_Area.setSelectedValue(null);
        _pdm_pul_SoftZone.setSelectedValue(null);
        txt_BankFrom.setValue(null);
        txt_BankTo.setValue(null);
        txt_BayFrom.setValue(null);
        txt_BayTo.setValue(null);
        txt_LevelFrom.setValue(null);
        txt_LevelTo.setValue(null);
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        _lcm_lst_ZoneMaintenanceRange.clear();

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
        pul_SoftZone.validate(this, true);
        txt_BankFrom.validate(this, true);
        txt_BankTo.validate(this, true);
        txt_BayFrom.validate(this, true);
        txt_BayTo.validate(this, true);
        txt_LevelFrom.validate(this, true);
        txt_LevelTo.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SoftZoneMasterMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SoftZoneMasterMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SoftZoneMasterMntSCHParams inparam = new SoftZoneMasterMntSCHParams();
            inparam.set(SoftZoneMasterMntSCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(SoftZoneMasterMntSCHParams.SOFT_ZONE, _pdm_pul_SoftZone.getSelectedValue());
            inparam.set(SoftZoneMasterMntSCHParams.BANK_FROM, txt_BankFrom.getValue());
            inparam.set(SoftZoneMasterMntSCHParams.BANK_TO, txt_BankTo.getValue());
            inparam.set(SoftZoneMasterMntSCHParams.BAY_FROM, txt_BayFrom.getValue());
            inparam.set(SoftZoneMasterMntSCHParams.BAY_TO, txt_BayTo.getValue());
            inparam.set(SoftZoneMasterMntSCHParams.LEVEL_FROM, txt_LevelFrom.getValue());
            inparam.set(SoftZoneMasterMntSCHParams.LEVEL_TO, txt_LevelTo.getValue());

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ZoneMaintenanceRange.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_ZoneMaintenanceRange.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_ZoneMaintenanceRange.get(i);
                SoftZoneMasterMntSCHParams lineparam = new SoftZoneMasterMntSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(SoftZoneMasterMntSCHParams.AREA_NO, line.getValue(KEY_LST_AREA_NO));
                lineparam.set(SoftZoneMasterMntSCHParams.SOFT_ZONE, line.getValue(KEY_HIDDEN_SOFT_ZONE));
                lineparam.set(SoftZoneMasterMntSCHParams.BANK_FROM, line.getValue(KEY_HIDDEN_BANK_FROM));
                lineparam.set(SoftZoneMasterMntSCHParams.BANK_TO, line.getValue(KEY_HIDDEN_BANK_TO));
                lineparam.set(SoftZoneMasterMntSCHParams.BAY_FROM, line.getValue(KEY_HIDDEN_BAY_FROM));
                lineparam.set(SoftZoneMasterMntSCHParams.BAY_TO, line.getValue(KEY_HIDDEN_BAY_TO));
                lineparam.set(SoftZoneMasterMntSCHParams.LEVEL_FROM, line.getValue(KEY_HIDDEN_LEVEL_FROM));
                lineparam.set(SoftZoneMasterMntSCHParams.LEVEL_TO, line.getValue(KEY_HIDDEN_LEVEL_TO));
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
            int editRow = _lcm_lst_ZoneMaintenanceRange.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_ZoneMaintenanceRange.getNewLine()
                                        : _lcm_lst_ZoneMaintenanceRange.get(editRow);
            line.setValue(KEY_LST_AREA_NO, _pdm_pul_Area.getSelectedValue());
            line.setValue(KEY_LST_SOFT_ZONE_NAME, _pdm_pul_SoftZone.getSelectedValue());
            line.setValue(KEY_LST_BANK, txt_BankTo.getValue());
            line.setValue(KEY_LST_BAY, txt_BayTo.getValue());
            line.setValue(KEY_LST_LEVEL, txt_LevelTo.getValue());
            line.setValue(KEY_HIDDEN_BANK_FROM, txt_BankFrom.getValue());
            line.setValue(KEY_HIDDEN_BANK_TO, txt_BankTo.getValue());
            line.setValue(KEY_HIDDEN_BAY_FROM, txt_BayFrom.getValue());
            line.setValue(KEY_HIDDEN_BAY_TO, txt_BayTo.getValue());
            line.setValue(KEY_HIDDEN_LEVEL_FROM, txt_LevelFrom.getValue());
            line.setValue(KEY_HIDDEN_LEVEL_TO, txt_LevelTo.getValue());
            line.setValue(KEY_HIDDEN_SOFT_ZONE, _pdm_pul_SoftZone.getSelectedValue());

            // add new row or update editing row.
            lst_ZoneMaintenanceRange_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_ZoneMaintenanceRange.add(line, true);
            }
            else
            {
                _lcm_lst_ZoneMaintenanceRange.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_ZoneMaintenanceRange.resetEditRow();
            _lcm_lst_ZoneMaintenanceRange.resetHighlight();

            // clear.
            btn_Set.setEnabled(true);
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        _pdm_pul_Area.setSelectedValue(null);
        _pdm_pul_SoftZone.setSelectedValue(null);
        txt_BankFrom.setValue(null);
        txt_BankTo.setValue(null);
        txt_BayFrom.setValue(null);
        txt_BayTo.setValue(null);
        txt_LevelFrom.setValue(null);
        txt_LevelTo.setValue(null);

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
        SoftZoneMasterMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SoftZoneMasterMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ZoneMaintenanceRange.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ZoneMaintenanceRange.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                SoftZoneMasterMntSCHParams lineparam = new SoftZoneMasterMntSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(SoftZoneMasterMntSCHParams.AREA_NO, line.getValue(KEY_LST_AREA_NO));
                lineparam.set(SoftZoneMasterMntSCHParams.SOFT_ZONE, line.getValue(KEY_HIDDEN_SOFT_ZONE));
                lineparam.set(SoftZoneMasterMntSCHParams.BANK_FROM, line.getValue(KEY_HIDDEN_BANK_FROM));
                lineparam.set(SoftZoneMasterMntSCHParams.BANK_TO, line.getValue(KEY_HIDDEN_BANK_TO));
                lineparam.set(SoftZoneMasterMntSCHParams.BAY_FROM, line.getValue(KEY_HIDDEN_BAY_FROM));
                lineparam.set(SoftZoneMasterMntSCHParams.BAY_TO, line.getValue(KEY_HIDDEN_BAY_TO));
                lineparam.set(SoftZoneMasterMntSCHParams.LEVEL_FROM, line.getValue(KEY_HIDDEN_LEVEL_FROM));
                lineparam.set(SoftZoneMasterMntSCHParams.LEVEL_TO, line.getValue(KEY_HIDDEN_LEVEL_TO));
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
                _lcm_lst_ZoneMaintenanceRange.resetEditRow();
                _lcm_lst_ZoneMaintenanceRange.resetHighlight();
                _lcm_lst_ZoneMaintenanceRange.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ZoneMaintenanceRange.size(); i++)
            {
                ListCellLine line = _lcm_lst_ZoneMaintenanceRange.get(i);
                lst_ZoneMaintenanceRange.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_AREA_NO), "");
                part11List.add(line.getViewString(KEY_HIDDEN_SOFT_ZONE), "");
                part11List.add(line.getViewString(KEY_HIDDEN_BANK_FROM), "");
                part11List.add(line.getViewString(KEY_HIDDEN_BANK_TO), "");
                part11List.add(line.getViewString(KEY_HIDDEN_BAY_FROM), "");
                part11List.add(line.getViewString(KEY_HIDDEN_BAY_TO), "");
                part11List.add(line.getViewString(KEY_HIDDEN_LEVEL_FROM), "");
                part11List.add(line.getViewString(KEY_HIDDEN_LEVEL_TO), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ZoneMaintenanceRange.resetEditRow();
            _lcm_lst_ZoneMaintenanceRange.resetHighlight();

            // clear.
            _lcm_lst_ZoneMaintenanceRange.clear();
            btn_Set.setEnabled(false);
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
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_ZoneMaintenanceRange.clear();
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Modify_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_ZoneMaintenanceRange.getActiveRow();
        lst_ZoneMaintenanceRange.setCurrentRow(row);
        ListCellLine line = _lcm_lst_ZoneMaintenanceRange.get(row);

        // output display.
        _pdm_pul_Area.setSelectedValue(line.getValue(KEY_LST_AREA_NO));
        _pdm_pul_SoftZone.setSelectedValue(line.getValue(KEY_HIDDEN_SOFT_ZONE));
        txt_BankFrom.setValue(line.getValue(KEY_HIDDEN_BANK_FROM));
        txt_BankTo.setValue(line.getValue(KEY_HIDDEN_BANK_TO));
        txt_BayFrom.setValue(line.getValue(KEY_HIDDEN_BAY_FROM));
        txt_BayTo.setValue(line.getValue(KEY_HIDDEN_BAY_TO));
        txt_LevelFrom.setValue(line.getValue(KEY_HIDDEN_LEVEL_FROM));
        txt_LevelTo.setValue(line.getValue(KEY_HIDDEN_LEVEL_TO));

        // highlight active row.
        _lcm_lst_ZoneMaintenanceRange.resetHighlight();
        _lcm_lst_ZoneMaintenanceRange.addHighlight(row);
        _lcm_lst_ZoneMaintenanceRange.setEditRow(row);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_ZoneMaintenanceRange.getActiveRow();
        lst_ZoneMaintenanceRange.setCurrentRow(row);

        // reset editing row.
        lst_ZoneMaintenanceRange.removeRow(row);
        _lcm_lst_ZoneMaintenanceRange.resetEditRow();
        _lcm_lst_ZoneMaintenanceRange.resetHighlight();

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
