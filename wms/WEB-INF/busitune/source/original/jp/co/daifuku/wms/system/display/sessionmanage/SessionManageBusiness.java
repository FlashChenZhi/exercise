// $Id: SessionManageBusiness.java 7746 2010-03-29 07:02:39Z shibamoto $
package jp.co.daifuku.wms.system.display.sessionmanage;

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
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.system.display.sessionmanage.SessionManage;
import jp.co.daifuku.wms.system.schedule.SessionManageSCH;
import jp.co.daifuku.wms.system.schedule.SessionManageSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7746 $, $Date:: 2010-03-29 16:02:39 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class SessionManageBusiness
        extends SessionManage
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_UserList(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_UserList(LST_RFT_NO) */
    private static final ListCellKey KEY_LST_RFT_NO = new ListCellKey("LST_RFT_NO", new StringCellColumn(), true, false);

    /** lst_UserList(LST_CLIENT_NAME) */
    private static final ListCellKey KEY_LST_CLIENT_NAME = new ListCellKey("LST_CLIENT_NAME", new StringCellColumn(), true, false);

    /** lst_UserList(LST_IP_ADDRESS) */
    private static final ListCellKey KEY_LST_IP_ADDRESS = new ListCellKey("LST_IP_ADDRESS", new StringCellColumn(), true, false);

    /** lst_UserList(LST_STATUS) */
    private static final ListCellKey KEY_LST_STATUS = new ListCellKey("LST_STATUS", new StringCellColumn(), true, false);

    /** lst_UserList(LST_SESSION_ID) */
    private static final ListCellKey KEY_LST_SESSION_ID = new ListCellKey("LST_SESSION_ID", new StringCellColumn(), true, false);

    /** lst_UserList keys */
    private static final ListCellKey[] LST_USERLIST_KEYS = {
        KEY_LST_SELECT,
        KEY_LST_RFT_NO,
        KEY_LST_CLIENT_NAME,
        KEY_LST_IP_ADDRESS,
        KEY_LST_STATUS,
        KEY_LST_SESSION_ID,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_TerminalName */
    private DefaultPullDownModel _pdm_pul_TerminalName;

    /** ListCellModel lst_UserList */
    private ListCellModel _lcm_lst_UserList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public SessionManageBusiness()
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
    public void btn_View_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_View_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Station_Stop_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Station_Stop_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Logoff_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Logoff_Click_Process();
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

        // initialize pul_TerminalName.
        _pdm_pul_TerminalName = new DefaultPullDownModel(pul_TerminalName, locale, ui);

        // initialize lst_UserList.
        _lcm_lst_UserList = new ListCellModel(lst_UserList, LST_USERLIST_KEYS, locale);
        _lcm_lst_UserList.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_UserList.setToolTipVisible(KEY_LST_RFT_NO, false);
        _lcm_lst_UserList.setToolTipVisible(KEY_LST_CLIENT_NAME, false);
        _lcm_lst_UserList.setToolTipVisible(KEY_LST_IP_ADDRESS, false);
        _lcm_lst_UserList.setToolTipVisible(KEY_LST_STATUS, false);
        _lcm_lst_UserList.setToolTipVisible(KEY_LST_SESSION_ID, false);
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

            // load pul_TerminalName.
            _pdm_pul_TerminalName.init(conn);
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
    private void lst_UserList_SetLineToolTip(ListCellLine line)
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
        // clear.
        _lcm_lst_UserList.clear();
        btn_Station_Stop.setEnabled(false);
        btn_Logoff.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_View_Click_Process()
            throws Exception
    {
        // input validation.
        pul_TerminalName.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SessionManageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SessionManageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            SessionManageSCHParams inparam = new SessionManageSCHParams();
            inparam.set(SessionManageSCHParams.SERVER_NAME, _pdm_pul_TerminalName.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_UserList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_UserList.getNewLine();
                line.setValue(KEY_LST_RFT_NO, outparam.get(SessionManageSCHParams.RFT_NO));
                line.setValue(KEY_LST_CLIENT_NAME, outparam.get(SessionManageSCHParams.CLIENT_NAME));
                line.setValue(KEY_LST_IP_ADDRESS, outparam.get(SessionManageSCHParams.IP_ADDRESS));
                line.setValue(KEY_LST_STATUS, outparam.get(SessionManageSCHParams.STATUS));
                line.setValue(KEY_LST_SESSION_ID, outparam.get(SessionManageSCHParams.SESSION_ID));
                lst_UserList_SetLineToolTip(line);
                _lcm_lst_UserList.add(line);
            }

            // clear.
            btn_Station_Stop.setEnabled(true);
            btn_Logoff.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
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
    private void btn_Station_Stop_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SessionManageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SessionManageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_UserList.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_UserList.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                SessionManageSCHParams lineparam = new SessionManageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(SessionManageSCHParams.SERVER_NAME, _pdm_pul_TerminalName.getSelectedValue());
                lineparam.set(SessionManageSCHParams.SESSION_ID, line.getValue(KEY_LST_SESSION_ID));
                lineparam.set(SessionManageSCHParams.PROCESS_FLAG, "DISCONNECT");
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
                _lcm_lst_UserList.resetEditRow();
                _lcm_lst_UserList.resetHighlight();
                _lcm_lst_UserList.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_UserList.resetEditRow();
            _lcm_lst_UserList.resetHighlight();

            // clear.
            _lcm_lst_UserList.clear();
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
    private void btn_Logoff_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        SessionManageSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new SessionManageSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_UserList.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_UserList.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                SessionManageSCHParams lineparam = new SessionManageSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(SessionManageSCHParams.SERVER_NAME, _pdm_pul_TerminalName.getSelectedValue());
                lineparam.set(SessionManageSCHParams.SESSION_ID, line.getValue(KEY_LST_SESSION_ID));
                lineparam.set(SessionManageSCHParams.PROCESS_FLAG, "RESET");
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
                _lcm_lst_UserList.resetEditRow();
                _lcm_lst_UserList.resetHighlight();
                _lcm_lst_UserList.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_UserList.resetEditRow();
            _lcm_lst_UserList.resetHighlight();

            // clear.
            _lcm_lst_UserList.clear();
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
        for (int i = 1; i <= _lcm_lst_UserList.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_UserList.get(i);
            lst_UserList.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_UserList_SetLineToolTip(clearLine);
            _lcm_lst_UserList.set(i, clearLine);
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
        for (int i = 1; i <= _lcm_lst_UserList.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_UserList.get(i);
            lst_UserList.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_UserList_SetLineToolTip(clearLine);
            _lcm_lst_UserList.set(i, clearLine);
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
