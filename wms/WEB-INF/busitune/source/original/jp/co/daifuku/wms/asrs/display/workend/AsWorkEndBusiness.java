// $Id: AsWorkEndBusiness.java 7404 2010-03-05 13:25:51Z shibamoto $
package jp.co.daifuku.wms.asrs.display.workend;

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
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
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
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.workend.AsWorkEnd;
import jp.co.daifuku.wms.asrs.display.workend.ViewStateKeys;
import jp.co.daifuku.wms.asrs.schedule.AsWorkEndSCH;
import jp.co.daifuku.wms.asrs.schedule.AsWorkEndSCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7404 $, $Date:: 2010-03-05 22:25:51 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsWorkEndBusiness
        extends AsWorkEnd
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_AsrsWorkEnd(HIDDEN_SYSTEM_STATUS) */
    private static final ListCellKey KEY_HIDDEN_SYSTEM_STATUS = new ListCellKey("HIDDEN_SYSTEM_STATUS", new StringCellColumn(), false, false);

    /** lst_AsrsWorkEnd(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 = new ListCellKey("LST_COLUMN_1", new CheckBoxColumn(), true, true);

    /** lst_AsrsWorkEnd(LST_AGC) */
    private static final ListCellKey KEY_LST_AGC = new ListCellKey("LST_AGC", new StringCellColumn(), true, false);

    /** lst_AsrsWorkEnd(LST_AGC_STATUS) */
    private static final ListCellKey KEY_LST_AGC_STATUS = new ListCellKey("LST_AGC_STATUS", new StringCellColumn(), true, false);

    /** lst_AsrsWorkEnd(LST_PENDING) */
    private static final ListCellKey KEY_LST_PENDING = new ListCellKey("LST_PENDING", new StringCellColumn(), true, false);

    /** lst_AsrsWorkEnd keys */
    private static final ListCellKey[] LST_ASRSWORKEND_KEYS = {
        KEY_HIDDEN_SYSTEM_STATUS,
        KEY_LST_COLUMN_1,
        KEY_LST_AGC,
        KEY_LST_AGC_STATUS,
        KEY_LST_PENDING,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel ASRSMode */
    private RadioButtonGroup _grp_ASRSMode;

    /** ListCellModel lst_AsrsWorkEnd */
    private ScrollListCellModel _lcm_lst_AsrsWorkEnd;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsWorkEndBusiness()
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
        if (eventSource.equals("btn_RemainWork_Click"))
        {
            // process call.
            btn_RemainWork_Click_DlgBack(dialogParams);
        }
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
    public void btn_Set2_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Set2_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ReDisplayFunc_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplayFunc_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_RemainWork_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_RemainWork_Click_Process();
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

        // initialize ASRSMode.
        _grp_ASRSMode = new RadioButtonGroup(new RadioButton[]{rdo_WorkingEndUsual, rdo_WorkingEnd_Date, rdo_WorkingSeparate}, locale);

        // initialize lst_AsrsWorkEnd.
        _lcm_lst_AsrsWorkEnd = new ScrollListCellModel(lst_AsrsWorkEnd, LST_ASRSWORKEND_KEYS, locale);
        _lcm_lst_AsrsWorkEnd.setToolTipVisible(KEY_LST_COLUMN_1, false);
        _lcm_lst_AsrsWorkEnd.setToolTipVisible(KEY_LST_AGC, false);
        _lcm_lst_AsrsWorkEnd.setToolTipVisible(KEY_LST_AGC_STATUS, false);
        _lcm_lst_AsrsWorkEnd.setToolTipVisible(KEY_LST_PENDING, false);
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
    private void lst_AsrsWorkEnd_SetLineToolTip(ListCellLine line)
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
        setFocus(rdo_WorkingEndUsual);
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
        AsWorkEndSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsWorkEndSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsWorkEndSCHParams inparam = new AsWorkEndSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_AsrsWorkEnd.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsrsWorkEnd.getNewLine();
                line.setValue(KEY_LST_AGC, outparam.get(AsWorkEndSCHParams.CONTROLLER_NO));
                line.setValue(KEY_LST_AGC_STATUS, outparam.get(AsWorkEndSCHParams.STATUS_FLAG));
                line.setValue(KEY_LST_PENDING, outparam.get(AsWorkEndSCHParams.WORK_COUNT));
                line.setValue(KEY_HIDDEN_SYSTEM_STATUS, outparam.get(AsWorkEndSCHParams.SYSTEM_STATUS));
                lst_AsrsWorkEnd_SetLineToolTip(line);
                _lcm_lst_AsrsWorkEnd.add(line);
            }

            // clear.
            rdo_WorkingEndUsual.setChecked(true);
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
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_AsrsWorkEnd.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_AsrsWorkEnd.get(i);
            lst_AsrsWorkEnd.setCurrentRow(i);
            clearLine.setValue(KEY_LST_COLUMN_1, Boolean.TRUE);
            lst_AsrsWorkEnd_SetLineToolTip(clearLine);
            _lcm_lst_AsrsWorkEnd.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_AsrsWorkEnd.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_AsrsWorkEnd.get(i);
            lst_AsrsWorkEnd.setCurrentRow(i);
            clearLine.setValue(KEY_LST_COLUMN_1, Boolean.FALSE);
            lst_AsrsWorkEnd_SetLineToolTip(clearLine);
            _lcm_lst_AsrsWorkEnd.set(i, clearLine);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set2_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_AsrsWorkEnd.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_AsrsWorkEnd.get(i);
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
        AsWorkEndSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsWorkEndSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_AsrsWorkEnd.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_AsrsWorkEnd.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsWorkEndSCHParams lineparam = new AsWorkEndSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(AsWorkEndSCHParams.CONTROLLER_NO, line.getValue(KEY_LST_AGC));
                lineparam.set(AsWorkEndSCHParams.PROCESS_TYPE, "0");
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
                _lcm_lst_AsrsWorkEnd.resetEditRow();
                _lcm_lst_AsrsWorkEnd.resetHighlight();
                _lcm_lst_AsrsWorkEnd.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_AsrsWorkEnd.size(); i++)
            {
                ListCellLine line = _lcm_lst_AsrsWorkEnd.get(i);
                lst_AsrsWorkEnd.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_AGC), "");
                part11List.add(AsrsInParameter.PROCESS_TYPE_WORK_END, "", rdo_WorkingEndUsual.getChecked());
                part11List.add(AsrsInParameter.PROCESS_TYPE_WORK_END_DATAKEEP, "", rdo_WorkingEnd_Date.getChecked());
                part11List.add(AsrsInParameter.PROCESS_TYPE_WORK_ONLYEND, "", rdo_WorkingSeparate.getChecked());
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_AsrsWorkEnd.resetEditRow();
            _lcm_lst_AsrsWorkEnd.resetHighlight();

            // set input parameters.
            AsWorkEndSCHParams inparam = new AsWorkEndSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_AsrsWorkEnd.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsrsWorkEnd.getNewLine();
                line.setValue(KEY_LST_AGC, outparam.get(AsWorkEndSCHParams.CONTROLLER_NO));
                line.setValue(KEY_LST_AGC_STATUS, outparam.get(AsWorkEndSCHParams.STATUS_FLAG));
                line.setValue(KEY_LST_PENDING, outparam.get(AsWorkEndSCHParams.WORK_COUNT));
                lst_AsrsWorkEnd_SetLineToolTip(line);
                _lcm_lst_AsrsWorkEnd.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_AsrsWorkEnd.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_AsrsWorkEnd.get(i);
                lst_AsrsWorkEnd.setCurrentRow(i);
                clearLine.setValue(KEY_LST_COLUMN_1, Boolean.FALSE);
                lst_AsrsWorkEnd_SetLineToolTip(clearLine);
                _lcm_lst_AsrsWorkEnd.set(i, clearLine);
            }
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
    private void btn_ReDisplayFunc_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsWorkEndSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsWorkEndSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsWorkEndSCHParams inparam = new AsWorkEndSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_AsrsWorkEnd.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsrsWorkEnd.getNewLine();
                line.setValue(KEY_LST_AGC, outparam.get(AsWorkEndSCHParams.CONTROLLER_NO));
                line.setValue(KEY_LST_AGC_STATUS, outparam.get(AsWorkEndSCHParams.STATUS_FLAG));
                line.setValue(KEY_LST_PENDING, outparam.get(AsWorkEndSCHParams.WORK_COUNT));
                viewState.setObject(ViewStateKeys.ASRS_MODE, _grp_ASRSMode.getSelectedValue());
                line.setValue(KEY_HIDDEN_SYSTEM_STATUS, outparam.get(AsWorkEndSCHParams.SYSTEM_STATUS));
                lst_AsrsWorkEnd_SetLineToolTip(line);
                _lcm_lst_AsrsWorkEnd.add(line);
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
    private void btn_RemainWork_Click_Process()
            throws Exception
    {
        // show dialog.
        ForwardParameters forwardParam = new ForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_RemainWork_Click");
        redirect("/asrs/listbox/carrynobtn/LstAsCarryNoBtn.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_RemainWork_Click_DlgBack(DialogParameters dialogParams)
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
