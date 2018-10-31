// $Id: AsSystemForcedDownBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.wms.asrs.display.systemforceddown;

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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.display.systemforceddown.AsSystemForcedDown;
import jp.co.daifuku.wms.asrs.schedule.AsSystemForcedDownSCH;
import jp.co.daifuku.wms.asrs.schedule.AsSystemForcedDownSCHParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class AsSystemForcedDownBusiness
        extends AsSystemForcedDown
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_AsrsForcedDown(HIDDEN_SYSTEM_STATUS) */
    private static final ListCellKey KEY_HIDDEN_SYSTEM_STATUS = new ListCellKey("HIDDEN_SYSTEM_STATUS", new StringCellColumn(), false, false);

    /** lst_AsrsForcedDown(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 = new ListCellKey("LST_COLUMN_1", new CheckBoxColumn(), true, true);

    /** lst_AsrsForcedDown(LST_AGC) */
    private static final ListCellKey KEY_LST_AGC = new ListCellKey("LST_AGC", new StringCellColumn(), true, false);

    /** lst_AsrsForcedDown(LST_AGC_STATUS) */
    private static final ListCellKey KEY_LST_AGC_STATUS = new ListCellKey("LST_AGC_STATUS", new StringCellColumn(), true, false);

    /** lst_AsrsForcedDown(LST_PENDING) */
    private static final ListCellKey KEY_LST_PENDING = new ListCellKey("LST_PENDING", new StringCellColumn(), true, false);

    /** lst_AsrsForcedDown keys */
    private static final ListCellKey[] LST_ASRSFORCEDDOWN_KEYS = {
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
    /** ListCellModel lst_AsrsForcedDown */
    private ScrollListCellModel _lcm_lst_AsrsForcedDown;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsSystemForcedDownBusiness()
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

        // initialize lst_AsrsForcedDown.
        _lcm_lst_AsrsForcedDown = new ScrollListCellModel(lst_AsrsForcedDown, LST_ASRSFORCEDDOWN_KEYS, locale);
        _lcm_lst_AsrsForcedDown.setToolTipVisible(KEY_LST_COLUMN_1, false);
        _lcm_lst_AsrsForcedDown.setToolTipVisible(KEY_LST_AGC, false);
        _lcm_lst_AsrsForcedDown.setToolTipVisible(KEY_LST_AGC_STATUS, false);
        _lcm_lst_AsrsForcedDown.setToolTipVisible(KEY_LST_PENDING, false);

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
    private void lst_AsrsForcedDown_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
        AsSystemForcedDownSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsSystemForcedDownSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsSystemForcedDownSCHParams inparam = new AsSystemForcedDownSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_AsrsForcedDown.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsrsForcedDown.getNewLine();
                line.setValue(KEY_LST_AGC, outparam.get(AsSystemForcedDownSCHParams.CONTROLLER_NO));
                line.setValue(KEY_LST_AGC_STATUS, outparam.get(AsSystemForcedDownSCHParams.STATUS_FLAG));
                line.setValue(KEY_LST_PENDING, outparam.get(AsSystemForcedDownSCHParams.WORK_COUNT));
                line.setValue(KEY_HIDDEN_SYSTEM_STATUS, outparam.get(AsSystemForcedDownSCHParams.SYSTEM_STATUS));
                lst_AsrsForcedDown_SetLineToolTip(line);
                _lcm_lst_AsrsForcedDown.add(line);
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
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_AsrsForcedDown.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_AsrsForcedDown.get(i);
            lst_AsrsForcedDown.setCurrentRow(i);
            clearLine.setValue(KEY_LST_COLUMN_1, Boolean.TRUE);
            lst_AsrsForcedDown_SetLineToolTip(clearLine);
            _lcm_lst_AsrsForcedDown.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_AsrsForcedDown.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_AsrsForcedDown.get(i);
            lst_AsrsForcedDown.setCurrentRow(i);
            clearLine.setValue(KEY_LST_COLUMN_1, Boolean.FALSE);
            lst_AsrsForcedDown_SetLineToolTip(clearLine);
            _lcm_lst_AsrsForcedDown.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_AsrsForcedDown.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_AsrsForcedDown.get(i);
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
        AsSystemForcedDownSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsSystemForcedDownSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_AsrsForcedDown.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_AsrsForcedDown.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                AsSystemForcedDownSCHParams lineparam = new AsSystemForcedDownSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(AsSystemForcedDownSCHParams.CONTROLLER_NO, line.getValue(KEY_LST_AGC));
                lineparam.set(AsSystemForcedDownSCHParams.PROCESS_TYPE, "0");
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
                _lcm_lst_AsrsForcedDown.resetEditRow();
                _lcm_lst_AsrsForcedDown.resetHighlight();
                _lcm_lst_AsrsForcedDown.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_AsrsForcedDown.resetEditRow();
            _lcm_lst_AsrsForcedDown.resetHighlight();

            // set input parameters.
            AsSystemForcedDownSCHParams inparam = new AsSystemForcedDownSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_AsrsForcedDown.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsrsForcedDown.getNewLine();
                line.setValue(KEY_LST_AGC, outparam.get(AsSystemForcedDownSCHParams.CONTROLLER_NO));
                line.setValue(KEY_LST_AGC_STATUS, outparam.get(AsSystemForcedDownSCHParams.STATUS_FLAG));
                line.setValue(KEY_LST_PENDING, outparam.get(AsSystemForcedDownSCHParams.WORK_COUNT));
                line.setValue(KEY_HIDDEN_SYSTEM_STATUS, outparam.get(AsSystemForcedDownSCHParams.SYSTEM_STATUS));
                lst_AsrsForcedDown_SetLineToolTip(line);
                _lcm_lst_AsrsForcedDown.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_AsrsForcedDown.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_AsrsForcedDown.get(i);
                lst_AsrsForcedDown.setCurrentRow(i);
                clearLine.setValue(KEY_LST_COLUMN_1, Boolean.FALSE);
                lst_AsrsForcedDown_SetLineToolTip(clearLine);
                _lcm_lst_AsrsForcedDown.set(i, clearLine);
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
        AsSystemForcedDownSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new AsSystemForcedDownSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            AsSystemForcedDownSCHParams inparam = new AsSystemForcedDownSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_AsrsForcedDown.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AsrsForcedDown.getNewLine();
                line.setValue(KEY_LST_AGC, outparam.get(AsSystemForcedDownSCHParams.CONTROLLER_NO));
                line.setValue(KEY_LST_AGC_STATUS, outparam.get(AsSystemForcedDownSCHParams.STATUS_FLAG));
                line.setValue(KEY_LST_PENDING, outparam.get(AsSystemForcedDownSCHParams.WORK_COUNT));
                line.setValue(KEY_HIDDEN_SYSTEM_STATUS, outparam.get(AsSystemForcedDownSCHParams.SYSTEM_STATUS));
                lst_AsrsForcedDown_SetLineToolTip(line);
                _lcm_lst_AsrsForcedDown.add(line);
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
