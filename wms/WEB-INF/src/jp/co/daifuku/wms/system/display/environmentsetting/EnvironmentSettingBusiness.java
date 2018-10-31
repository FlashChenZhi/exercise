// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.system.display.environmentsetting;

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
import jp.co.daifuku.wms.system.display.environmentsetting.EnvironmentSetting;
import jp.co.daifuku.wms.system.schedule.EnvironmentSettingSCH;
import jp.co.daifuku.wms.system.schedule.EnvironmentSettingSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class EnvironmentSettingBusiness
        extends EnvironmentSetting
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_HostEnvironment(HDN_DATA_TYPE) */
    private static final ListCellKey KEY_HDN_DATA_TYPE =
            new ListCellKey("HDN_DATA_TYPE", new StringCellColumn(), false, false);

    /** lst_HostEnvironment(LST_DATA_NAME) */
    private static final ListCellKey KEY_LST_DATA_NAME =
            new ListCellKey("LST_DATA_NAME", new StringCellColumn(), true, false);

    /** lst_HostEnvironment(LST_DATA_TYPE) */
    private static final ListCellKey KEY_LST_DATA_TYPE =
            new ListCellKey("LST_DATA_TYPE", new StringCellColumn(), true, false);

    /** lst_HostEnvironment(LST_DESTINATION_FOLDER) */
    private static final ListCellKey KEY_LST_DESTINATION_FOLDER =
            new ListCellKey("LST_DESTINATION_FOLDER", new StringCellColumn(), true, true);

    /** lst_HostEnvironment(LST_PREFIX_NAME) */
    private static final ListCellKey KEY_LST_PREFIX_NAME =
            new ListCellKey("LST_PREFIX_NAME", new StringCellColumn(), true, false);

    /** lst_HostEnvironment keys */
    private static final ListCellKey[] LST_HOSTENVIRONMENT_KEYS = {
        KEY_HDN_DATA_TYPE,
        KEY_LST_DATA_NAME,
        KEY_LST_DATA_TYPE,
        KEY_LST_DESTINATION_FOLDER,
        KEY_LST_PREFIX_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_HostEnvironment */
    private ListCellModel _lcm_lst_HostEnvironment;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public EnvironmentSettingBusiness()
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
    public void lbl_SettingName_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ToMenu_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_HostEnvironment_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_HostEnvironment_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_HostEnvironment_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_HostEnvironment_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_HostEnvironment_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_HostEnvironment_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_HostEnvironment_Change(ActionEvent e)
            throws Exception
    {
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
    public void btn_Set2_Server(ActionEvent e)
            throws Exception
    {
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
    public void btn_Clear_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Help_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_3_Change(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_3_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_3_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_3_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_3_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_3_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_3_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void tab_Set_Click(ActionEvent e)
            throws Exception
    {
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

        // initialize lst_HostEnvironment.
        _lcm_lst_HostEnvironment = new ListCellModel(lst_HostEnvironment, LST_HOSTENVIRONMENT_KEYS, locale);
        _lcm_lst_HostEnvironment.setToolTipVisible(KEY_LST_DATA_NAME, false);
        _lcm_lst_HostEnvironment.setToolTipVisible(KEY_LST_DATA_TYPE, false);
        _lcm_lst_HostEnvironment.setToolTipVisible(KEY_LST_DESTINATION_FOLDER, false);
        _lcm_lst_HostEnvironment.setToolTipVisible(KEY_LST_PREFIX_NAME, false);

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
    private void lst_HostEnvironment_SetLineToolTip(ListCellLine line)
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
        EnvironmentSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new EnvironmentSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            EnvironmentSettingSCHParams inparam = new EnvironmentSettingSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_HostEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_HostEnvironment.getNewLine();
                line.setValue(KEY_HDN_DATA_TYPE, outparam.get(EnvironmentSettingSCHParams.HDN_TYPE));
                line.setValue(KEY_LST_DATA_NAME, outparam.get(EnvironmentSettingSCHParams.DATA_NAME));
                line.setValue(KEY_LST_DATA_TYPE, outparam.get(EnvironmentSettingSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(EnvironmentSettingSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_PREFIX_NAME, outparam.get(EnvironmentSettingSCHParams.PREFIX_NAME));
                lst_HostEnvironment_SetLineToolTip(line);
                _lcm_lst_HostEnvironment.add(line);
            }

            // set focus.
            setFocus(lst_HostEnvironment);

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
    private void btn_Set2_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_HostEnvironment.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_HostEnvironment.get(i);
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
        EnvironmentSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new EnvironmentSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_HostEnvironment.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_HostEnvironment.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                EnvironmentSettingSCHParams lineparam = new EnvironmentSettingSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(EnvironmentSettingSCHParams.HDN_TYPE, line.getValue(KEY_HDN_DATA_TYPE));
                lineparam.set(EnvironmentSettingSCHParams.DESTINATION_FOLDER, line.getValue(KEY_LST_DESTINATION_FOLDER));
                lineparam.set(EnvironmentSettingSCHParams.PREFIX_NAME, line.getValue(KEY_LST_PREFIX_NAME));
                lineparam.set(EnvironmentSettingSCHParams.DATA_TYPE, line.getValue(KEY_LST_DATA_TYPE));
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
                _lcm_lst_HostEnvironment.resetEditRow();
                _lcm_lst_HostEnvironment.resetHighlight();
                _lcm_lst_HostEnvironment.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_HostEnvironment.resetEditRow();
            _lcm_lst_HostEnvironment.resetHighlight();

            // set input parameters.
            EnvironmentSettingSCHParams inparam = new EnvironmentSettingSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_HostEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_HostEnvironment.getNewLine();
                line.setValue(KEY_HDN_DATA_TYPE, outparam.get(EnvironmentSettingSCHParams.HDN_TYPE));
                line.setValue(KEY_LST_DATA_NAME, outparam.get(EnvironmentSettingSCHParams.DATA_NAME));
                line.setValue(KEY_LST_DATA_TYPE, outparam.get(EnvironmentSettingSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(EnvironmentSettingSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_PREFIX_NAME, outparam.get(EnvironmentSettingSCHParams.PREFIX_NAME));
                lst_HostEnvironment_SetLineToolTip(line);
                _lcm_lst_HostEnvironment.add(line);
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        EnvironmentSettingSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new EnvironmentSettingSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            EnvironmentSettingSCHParams inparam = new EnvironmentSettingSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_HostEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_HostEnvironment.getNewLine();
                line.setValue(KEY_HDN_DATA_TYPE, outparam.get(EnvironmentSettingSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_DATA_NAME, outparam.get(EnvironmentSettingSCHParams.DATA_NAME));
                line.setValue(KEY_LST_DATA_TYPE, outparam.get(EnvironmentSettingSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(EnvironmentSettingSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_PREFIX_NAME, outparam.get(EnvironmentSettingSCHParams.PREFIX_NAME));
                lst_HostEnvironment_SetLineToolTip(line);
                _lcm_lst_HostEnvironment.add(line);
            }
            // set focus.
            setFocus(lst_HostEnvironment);

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
