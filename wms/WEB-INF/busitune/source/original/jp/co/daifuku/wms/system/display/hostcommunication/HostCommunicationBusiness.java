// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.system.display.hostcommunication;

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
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
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
import jp.co.daifuku.wms.system.display.hostcommunication.HostCommunication;
import jp.co.daifuku.wms.system.schedule.HostCommunicationSCH;
import jp.co.daifuku.wms.system.schedule.HostCommunicationSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class HostCommunicationBusiness
        extends HostCommunication
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_HostCommunication(HIDDEN_CYCLE) */
    private static final ListCellKey KEY_HIDDEN_CYCLE = new ListCellKey("HIDDEN_CYCLE", new NumberCellColumn(0), false, false);

    /** lst_HostCommunication(HIDDEN_TIME) */
    private static final ListCellKey KEY_HIDDEN_TIME = new ListCellKey("HIDDEN_TIME", new StringCellColumn(), false, false);

    /** lst_HostCommunication(HIDDEN_ISSUE_LIST) */
    private static final ListCellKey KEY_HIDDEN_ISSUE_LIST = new ListCellKey("HIDDEN_ISSUE_LIST", new StringCellColumn(), false, false);

    /** lst_HostCommunication(HIDDEN_JOB_TYPE) */
    private static final ListCellKey KEY_HIDDEN_JOB_TYPE = new ListCellKey("HIDDEN_JOB_TYPE", new StringCellColumn(), false, false);

    /** lst_HostCommunication(HIDDEN_EXCHANGE_TYPE) */
    private static final ListCellKey KEY_HIDDEN_EXCHANGE_TYPE = new ListCellKey("HIDDEN_EXCHANGE_TYPE", new StringCellColumn(), false, false);

    /** lst_HostCommunication(HIDDEN_CLASS_NAME) */
    private static final ListCellKey KEY_HIDDEN_CLASS_NAME = new ListCellKey("HIDDEN_CLASS_NAME", new StringCellColumn(), false, false);

    /** lst_HostCommunication(HIDDEN_IS_DEFINED) */
    private static final ListCellKey KEY_HIDDEN_IS_DEFINED = new ListCellKey("HIDDEN_IS_DEFINED", new StringCellColumn(), false, false);

    /** lst_HostCommunication(LST_DATA_NAME) */
    private static final ListCellKey KEY_LST_DATA_NAME = new ListCellKey("LST_DATA_NAME", new StringCellColumn(), true, false);

    /** lst_HostCommunication(LST_CYCLE) */
    private static final ListCellKey KEY_LST_CYCLE = new ListCellKey("LST_CYCLE", new NumberCellColumn(0), true, true);

    /** lst_HostCommunication(LST_TIME) */
    private static final ListCellKey KEY_LST_TIME = new ListCellKey("LST_TIME", new StringCellColumn(), true, true);

    /** lst_HostCommunication(LST_ISSUE_LIST) */
    private static final ListCellKey KEY_LST_ISSUE_LIST = new ListCellKey("LST_ISSUE_LIST", new CheckBoxColumn(), true, true);

    /** lst_HostCommunication(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS = new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_HostCommunication keys */
    private static final ListCellKey[] LST_HOSTCOMMUNICATION_KEYS = {
        KEY_HIDDEN_CYCLE,
        KEY_HIDDEN_TIME,
        KEY_HIDDEN_ISSUE_LIST,
        KEY_HIDDEN_JOB_TYPE,
        KEY_HIDDEN_EXCHANGE_TYPE,
        KEY_HIDDEN_CLASS_NAME,
        KEY_HIDDEN_IS_DEFINED,
        KEY_LST_DATA_NAME,
        KEY_LST_CYCLE,
        KEY_LST_TIME,
        KEY_LST_ISSUE_LIST,
        KEY_LST_STATUS,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_HostCommunication */
    private ScrollListCellModel _lcm_lst_HostCommunication;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public HostCommunicationBusiness()
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
        if (eventSource.equals("btn_CommunicationSuspendRestar_Click"))
        {
            // process call.
            btn_CommunicationSuspendRestar_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_CommunicationSuspendRestar_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_CommunicationSuspendRestar_Click_Process(true);
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

        // initialize lst_HostCommunication.
        _lcm_lst_HostCommunication = new ScrollListCellModel(lst_HostCommunication, LST_HOSTCOMMUNICATION_KEYS, locale);
        _lcm_lst_HostCommunication.setToolTipVisible(KEY_LST_DATA_NAME, false);
        _lcm_lst_HostCommunication.setToolTipVisible(KEY_LST_CYCLE, false);
        _lcm_lst_HostCommunication.setToolTipVisible(KEY_LST_TIME, false);
        _lcm_lst_HostCommunication.setToolTipVisible(KEY_LST_ISSUE_LIST, false);
        _lcm_lst_HostCommunication.setToolTipVisible(KEY_LST_STATUS, false);

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
    private void lst_HostCommunication_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_CommunicationSuspendRestar);

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
        HostCommunicationSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new HostCommunicationSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            HostCommunicationSCHParams inparam = new HostCommunicationSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_HostCommunication.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_HostCommunication.getNewLine();
                line.setValue(KEY_LST_DATA_NAME, outparam.get(HostCommunicationSCHParams.DATA_NAME));
                line.setValue(KEY_LST_CYCLE, outparam.get(HostCommunicationSCHParams.CYCLE));
                line.setValue(KEY_LST_TIME, outparam.get(HostCommunicationSCHParams.SPECIFIED_TIME));
                line.setValue(KEY_LST_STATUS, outparam.get(HostCommunicationSCHParams.ERROR_STATUS));
                txt_CommunicationSuspendRestar.setValue(outparam.get(HostCommunicationSCHParams.HOST_DISABLED));
                line.setValue(KEY_LST_ISSUE_LIST, outparam.get(HostCommunicationSCHParams.ISSUE_LIST));
                line.setValue(KEY_HIDDEN_CYCLE, outparam.get(HostCommunicationSCHParams.CYCLE));
                line.setValue(KEY_HIDDEN_TIME, outparam.get(HostCommunicationSCHParams.SPECIFIED_TIME));
                line.setValue(KEY_HIDDEN_ISSUE_LIST, outparam.get(HostCommunicationSCHParams.ISSUE_LIST));
                line.setValue(KEY_HIDDEN_JOB_TYPE, outparam.get(HostCommunicationSCHParams.JOB_TYPE));
                line.setValue(KEY_HIDDEN_EXCHANGE_TYPE, outparam.get(HostCommunicationSCHParams.EXCHANGE_TYPE));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(HostCommunicationSCHParams.CLASS_NAME));
                line.setValue(KEY_HIDDEN_IS_DEFINED, outparam.get(HostCommunicationSCHParams.IS_DEFINED));
                lst_HostCommunication_SetLineToolTip(line);
                _lcm_lst_HostCommunication.add(line);
            }

            // set focus.
            setFocus(lst_HostCommunication);

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
    private void btn_CommunicationSuspendRestar_Click_Process(boolean confirm)
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        HostCommunicationSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new HostCommunicationSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            HostCommunicationSCHParams inparam = new HostCommunicationSCHParams();
            inparam.setProcessFlag(ProcessFlag.UPDATE);
            inparam.set(HostCommunicationSCHParams.HOST_DISABLED, txt_CommunicationSuspendRestar.getValue());

            // SCH call.
            if (confirm && !sch.check(inparam))
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
                    viewState.setString(_KEY_CONFIRMSOURCE, "btn_CommunicationSuspendRestar_Click");
                    return;
                }
            }

            // SCH call.
            if (!sch.startSCH(inparam))
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_CommunicationSuspendRestar.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // set input parameters.
            inparam = new HostCommunicationSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            for (Params outparam : outparams)
            {
                txt_CommunicationSuspendRestar.setValue(outparam.get(HostCommunicationSCHParams.HOST_DISABLED));
            }

            // set focus.
            setFocus(lst_HostCommunication);

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
    private void btn_Set_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_HostCommunication.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_HostCommunication.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_HostCommunication.setCurrentRow(i);
            lst_HostCommunication.validate(checkline.getIndex(KEY_LST_CYCLE), false);
            lst_HostCommunication.validate(checkline.getIndex(KEY_LST_TIME), false);
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
        HostCommunicationSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new HostCommunicationSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_HostCommunication.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_HostCommunication.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                HostCommunicationSCHParams lineparam = new HostCommunicationSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(HostCommunicationSCHParams.DATA_NAME, line.getValue(KEY_LST_DATA_NAME));
                lineparam.set(HostCommunicationSCHParams.CYCLE, line.getValue(KEY_LST_CYCLE));
                lineparam.set(HostCommunicationSCHParams.SPECIFIED_TIME, line.getValue(KEY_LST_TIME));
                lineparam.set(HostCommunicationSCHParams.ISSUE_LIST, line.getValue(KEY_LST_ISSUE_LIST));
                lineparam.set(HostCommunicationSCHParams.HIDDEN_CYCLE, line.getValue(KEY_HIDDEN_CYCLE));
                lineparam.set(HostCommunicationSCHParams.HIDDEN_TIME, line.getValue(KEY_HIDDEN_TIME));
                lineparam.set(HostCommunicationSCHParams.HIDDEN_ISSUE_LIST, line.getValue(KEY_HIDDEN_ISSUE_LIST));
                lineparam.set(HostCommunicationSCHParams.JOB_TYPE, line.getValue(KEY_HIDDEN_JOB_TYPE));
                lineparam.set(HostCommunicationSCHParams.EXCHANGE_TYPE, line.getValue(KEY_HIDDEN_EXCHANGE_TYPE));
                lineparam.set(HostCommunicationSCHParams.CLASS_NAME, line.getValue(KEY_HIDDEN_CLASS_NAME));
                lineparam.set(HostCommunicationSCHParams.IS_DEFINED, line.getValue(KEY_HIDDEN_IS_DEFINED));
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
                _lcm_lst_HostCommunication.resetEditRow();
                _lcm_lst_HostCommunication.resetHighlight();
                _lcm_lst_HostCommunication.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_HostCommunication.size(); i++)
            {
                ListCellLine line = _lcm_lst_HostCommunication.get(i);
                lst_HostCommunication.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_HIDDEN_JOB_TYPE), "");
                part11List.add(line.getViewString(KEY_LST_CYCLE), "");
                part11List.add(line.getViewString(KEY_LST_TIME), "");

                if (lst_HostCommunication.getChecked(_lcm_lst_HostCommunication.getColumnIndex(KEY_LST_ISSUE_LIST)))
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
            _lcm_lst_HostCommunication.resetEditRow();
            _lcm_lst_HostCommunication.resetHighlight();

            // set input parameters.
            HostCommunicationSCHParams inparam = new HostCommunicationSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_HostCommunication.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_HostCommunication.getNewLine();
                line.setValue(KEY_LST_DATA_NAME, outparam.get(HostCommunicationSCHParams.DATA_NAME));
                line.setValue(KEY_LST_CYCLE, outparam.get(HostCommunicationSCHParams.CYCLE));
                line.setValue(KEY_LST_TIME, outparam.get(HostCommunicationSCHParams.SPECIFIED_TIME));
                line.setValue(KEY_LST_STATUS, outparam.get(HostCommunicationSCHParams.ERROR_STATUS));
                line.setValue(KEY_LST_ISSUE_LIST, outparam.get(HostCommunicationSCHParams.ISSUE_LIST));
                txt_CommunicationSuspendRestar.setValue(outparam.get(HostCommunicationSCHParams.HOST_DISABLED));
                line.setValue(KEY_HIDDEN_CYCLE, outparam.get(HostCommunicationSCHParams.CYCLE));
                line.setValue(KEY_HIDDEN_TIME, outparam.get(HostCommunicationSCHParams.SPECIFIED_TIME));
                line.setValue(KEY_HIDDEN_ISSUE_LIST, outparam.get(HostCommunicationSCHParams.ISSUE_LIST));
                line.setValue(KEY_HIDDEN_JOB_TYPE, outparam.get(HostCommunicationSCHParams.JOB_TYPE));
                line.setValue(KEY_HIDDEN_EXCHANGE_TYPE, outparam.get(HostCommunicationSCHParams.EXCHANGE_TYPE));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(HostCommunicationSCHParams.CLASS_NAME));
                line.setValue(KEY_HIDDEN_IS_DEFINED, outparam.get(HostCommunicationSCHParams.IS_DEFINED));
                lst_HostCommunication_SetLineToolTip(line);
                _lcm_lst_HostCommunication.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_HostCommunication.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_HostCommunication.get(i);
                lst_HostCommunication.setCurrentRow(i);
                lst_HostCommunication.setCellReadOnly(_lcm_lst_HostCommunication.getColumnIndex(KEY_LST_CYCLE), false);
                lst_HostCommunication.setCellReadOnly(_lcm_lst_HostCommunication.getColumnIndex(KEY_LST_TIME), false);
                lst_HostCommunication_SetLineToolTip(clearLine);
                _lcm_lst_HostCommunication.set(i, clearLine);
            }

            // set focus.
            setFocus(lst_HostCommunication);

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
