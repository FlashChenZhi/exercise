// $Id: BreakTimeRegistBusiness.java 7451 2010-03-08 04:26:04Z okayama $
package jp.co.daifuku.wms.analysis.display.breaktimeregist;

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
import jp.co.daifuku.wms.analysis.display.breaktimeregist.BreakTimeRegist;
import jp.co.daifuku.wms.analysis.schedule.BreakTimeRegistSCH;
import jp.co.daifuku.wms.analysis.schedule.BreakTimeRegistSCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7451 $, $Date:: 2010-03-08 13:26:04 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class BreakTimeRegistBusiness
        extends BreakTimeRegist
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_BreakTimeList(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new StringCellColumn(), true, false);

    /** lst_BreakTimeList(LST_CANCEL) */
    private static final ListCellKey KEY_LST_CANCEL = new ListCellKey("LST_CANCEL", new StringCellColumn(), true, false);

    /** lst_BreakTimeList(LST_REST_BREAK_START) */
    private static final ListCellKey KEY_LST_REST_BREAK_START = new ListCellKey("LST_REST_BREAK_START", new StringCellColumn(), true, false);

    /** lst_BreakTimeList(LST_REST_BREAK_END) */
    private static final ListCellKey KEY_LST_REST_BREAK_END = new ListCellKey("LST_REST_BREAK_END", new StringCellColumn(), true, false);

    /** lst_BreakTimeList keys */
    private static final ListCellKey[] LST_BREAKTIMELIST_KEYS = {
        KEY_LST_MODIFY,
        KEY_LST_CANCEL,
        KEY_LST_REST_BREAK_START,
        KEY_LST_REST_BREAK_END,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_BreakTimeList */
    private ListCellModel _lcm_lst_BreakTimeList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public BreakTimeRegistBusiness()
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
    public void btn_Setting_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Setting_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_BreakTimeList_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_BreakTimeList.getActiveCol();

        // choose process.
        if (_lcm_lst_BreakTimeList.getColumnIndex(KEY_LST_MODIFY) == activeCol)
        {
            // process call.
            lst_Modify_Click_Process();
        }
        else if (_lcm_lst_BreakTimeList.getColumnIndex(KEY_LST_CANCEL) == activeCol)
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
        Locale locale = httpRequest.getLocale();

        // initialize lst_BreakTimeList.
        _lcm_lst_BreakTimeList = new ListCellModel(lst_BreakTimeList, LST_BREAKTIMELIST_KEYS, locale);
        _lcm_lst_BreakTimeList.setToolTipVisible(KEY_LST_MODIFY, false);
        _lcm_lst_BreakTimeList.setToolTipVisible(KEY_LST_CANCEL, false);
        _lcm_lst_BreakTimeList.setToolTipVisible(KEY_LST_REST_BREAK_START, false);
        _lcm_lst_BreakTimeList.setToolTipVisible(KEY_LST_REST_BREAK_END, false);

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
    private void lst_BreakTimeList_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_BreakStartTime);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_BreakStartTime.setValue(null);
        txt_BreakEndTime.setValue(null);
        btn_Setting.setEnabled(false);

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
        txt_BreakStartTime.validate(this, true);
        txt_BreakEndTime.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        BreakTimeRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new BreakTimeRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            BreakTimeRegistSCHParams inparam = new BreakTimeRegistSCHParams();
            inparam.set(BreakTimeRegistSCHParams.BREAK_START_TIME, txt_BreakStartTime.getValue());
            inparam.set(BreakTimeRegistSCHParams.BREAK_END_TIME, txt_BreakEndTime.getValue());

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_BreakTimeList.size(); i++)
            {
                // exclusion editing row.
                if (_lcm_lst_BreakTimeList.getEditRow() == i)
                {
                    continue;
                }

                ListCellLine line = _lcm_lst_BreakTimeList.get(i);
                BreakTimeRegistSCHParams lineparam = new BreakTimeRegistSCHParams();
                lineparam.setProcessFlag(ProcessFlag.INPUT);
                lineparam.setRowIndex(i);
                lineparam.set(BreakTimeRegistSCHParams.BREAK_START_TIME, line.getValue(KEY_LST_REST_BREAK_START));
                lineparam.set(BreakTimeRegistSCHParams.BREAK_END_TIME, line.getValue(KEY_LST_REST_BREAK_END));
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
            int editRow = _lcm_lst_BreakTimeList.getEditRow();
            Boolean newline = ListCellModel.EDIT_ROW_NONE == editRow;
            ListCellLine line = newline ? _lcm_lst_BreakTimeList.getNewLine()
                                        : _lcm_lst_BreakTimeList.get(editRow);
            line.setValue(KEY_LST_REST_BREAK_START, txt_BreakStartTime.getValue());
            line.setValue(KEY_LST_REST_BREAK_END, txt_BreakEndTime.getValue());

            // add new row or update editing row.
            lst_BreakTimeList_SetLineToolTip(line);
            if (newline)
            {
                _lcm_lst_BreakTimeList.add(line, true);
            }
            else
            {
                _lcm_lst_BreakTimeList.set(editRow, line);
            }

            // reset editing row.
            _lcm_lst_BreakTimeList.resetEditRow();
            _lcm_lst_BreakTimeList.resetHighlight();

            // clear.
            btn_Setting.setEnabled(true);

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
        txt_BreakStartTime.setValue(null);
        txt_BreakEndTime.setValue(null);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Setting_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        BreakTimeRegistSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new BreakTimeRegistSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_BreakTimeList.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_BreakTimeList.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                BreakTimeRegistSCHParams lineparam = new BreakTimeRegistSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(BreakTimeRegistSCHParams.BREAK_START_TIME, line.getValue(KEY_LST_REST_BREAK_START));
                lineparam.set(BreakTimeRegistSCHParams.BREAK_END_TIME, line.getValue(KEY_LST_REST_BREAK_END));
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
                _lcm_lst_BreakTimeList.resetEditRow();
                _lcm_lst_BreakTimeList.resetHighlight();
                _lcm_lst_BreakTimeList.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_BreakTimeList.size(); i++)
            {
                ListCellLine line = _lcm_lst_BreakTimeList.get(i);
                lst_BreakTimeList.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_REST_BREAK_START), "");
                part11List.add(line.getViewString(KEY_LST_REST_BREAK_END), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_BreakTimeList.resetEditRow();
            _lcm_lst_BreakTimeList.resetHighlight();

            // clear.
            txt_BreakStartTime.setValue(null);
            txt_BreakEndTime.setValue(null);
            btn_Setting.setEnabled(false);

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
        int row = lst_BreakTimeList.getActiveRow();
        lst_BreakTimeList.setCurrentRow(row);
        ListCellLine line = _lcm_lst_BreakTimeList.get(row);

        // output display.
        txt_BreakStartTime.setValue(line.getValue(KEY_LST_REST_BREAK_START));
        txt_BreakEndTime.setValue(line.getValue(KEY_LST_REST_BREAK_END));

        // highlight active row.
        _lcm_lst_BreakTimeList.resetHighlight();
        _lcm_lst_BreakTimeList.addHighlight(row);
        _lcm_lst_BreakTimeList.setEditRow(row);

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Cancel_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_BreakTimeList.getActiveRow();
        lst_BreakTimeList.setCurrentRow(row);

        // reset editing row.
        lst_BreakTimeList.removeRow(row);
        _lcm_lst_BreakTimeList.resetEditRow();
        _lcm_lst_BreakTimeList.resetHighlight();

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
