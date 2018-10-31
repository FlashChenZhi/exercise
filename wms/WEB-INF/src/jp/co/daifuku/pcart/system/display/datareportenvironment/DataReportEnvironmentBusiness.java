// $Id: DataReportEnvironmentBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.system.display.datareportenvironment;

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
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.pcart.system.schedule.DataReportEnvironmentSCH;
import jp.co.daifuku.pcart.system.schedule.DataReportEnvironmentSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * データ報告環境設定（ファイル）の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class DataReportEnvironmentBusiness
        extends DataReportEnvironment
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK:start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK:end

    /** lst_DataReportEnvironment(HIDDEN_DATA_TYPE) */
    private static final ListCellKey KEY_HIDDEN_DATA_TYPE = new ListCellKey("HIDDEN_DATA_TYPE", new StringCellColumn(), false, false);

    /** lst_DataReportEnvironment(LST_PACKAGE_NAME) */
    private static final ListCellKey KEY_LST_PACKAGE_NAME = new ListCellKey("LST_PACKAGE_NAME", new StringCellColumn(), true, false);

    /** lst_DataReportEnvironment(LST_DESTINATION_FOLDER) */
    private static final ListCellKey KEY_LST_DESTINATION_FOLDER = new ListCellKey("LST_DESTINATION_FOLDER", new StringCellColumn(), true, true);

    /** lst_DataReportEnvironment(LST_DATA_FILE_NAME) */
    private static final ListCellKey KEY_LST_DATA_FILE_NAME = new ListCellKey("LST_DATA_FILE_NAME", new StringCellColumn(), true, true);

    /** lst_DataReportEnvironment(LST_REPORT_UNIT) */
    private static final ListCellKey KEY_LST_REPORT_UNIT = new ListCellKey("LST_REPORT_UNIT", new StringCellColumn(), true, false);

    /** lst_DataReportEnvironment keys */
    private static final ListCellKey[] LST_DATAREPORTENVIRONMENT_KEYS = {
        KEY_HIDDEN_DATA_TYPE,
        KEY_LST_PACKAGE_NAME,
        KEY_LST_DESTINATION_FOLDER,
        KEY_LST_DATA_FILE_NAME,
        KEY_LST_REPORT_UNIT,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_DataReportEnvironment */
    private ListCellModel _lcm_lst_DataReportEnvironment;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public DataReportEnvironmentBusiness()
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

        // DFKLOOK:ここから修正
        // フォーカスセット
        // set focus.
        setFocus(lst_DataReportEnvironment);
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_ConfirmBack(ActionEvent e)
            throws Exception
    {
        // DFKLOOK start
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
        if (eventSource.startsWith("btn_Set_Click"))
        {
        	btn_Set_Click_Process(eventSource);
        }
        // DFKLOOK end
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_DataReportEnvironment_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_DataReportEnvironment_ColumClick(ActionEvent e)
            throws Exception
    {
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
        btn_Set_Click_Process(null);
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

        // initialize lst_DataReportEnvironment.
        _lcm_lst_DataReportEnvironment = new ListCellModel(lst_DataReportEnvironment, LST_DATAREPORTENVIRONMENT_KEYS, locale);
        _lcm_lst_DataReportEnvironment.setToolTipVisible(KEY_LST_PACKAGE_NAME, false);
        _lcm_lst_DataReportEnvironment.setToolTipVisible(KEY_LST_DESTINATION_FOLDER, false);
        _lcm_lst_DataReportEnvironment.setToolTipVisible(KEY_LST_DATA_FILE_NAME, false);
        _lcm_lst_DataReportEnvironment.setToolTipVisible(KEY_LST_REPORT_UNIT, false);

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
    private void lst_DataReportEnvironment_SetLineToolTip(ListCellLine line)
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
        DataReportEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DataReportEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DataReportEnvironmentSCHParams inparam = new DataReportEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_DataReportEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_DataReportEnvironment.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(DataReportEnvironmentSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_PACKAGE_NAME, outparam.get(DataReportEnvironmentSCHParams.PACKAGE_NAME));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(DataReportEnvironmentSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_DATA_FILE_NAME, outparam.get(DataReportEnvironmentSCHParams.DATA_FILE_NAME));
                line.setValue(KEY_LST_REPORT_UNIT, outparam.get(DataReportEnvironmentSCHParams.REPORT_UNIT));
                lst_DataReportEnvironment_SetLineToolTip(line);
                _lcm_lst_DataReportEnvironment.add(line);
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
    private void btn_Set_Click_Process(String eventSource)
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_DataReportEnvironment.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_DataReportEnvironment.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_DataReportEnvironment.setCurrentRow(i);
            lst_DataReportEnvironment.validate(checkline.getIndex(KEY_LST_DESTINATION_FOLDER), true);
            lst_DataReportEnvironment.validate(checkline.getIndex(KEY_LST_DATA_FILE_NAME), true);
        }
        if (!existEditedRow)
        {
            // DFKLOOK:ここから修正
            // メッセージセット
            // 6023059=更新対象データがありません。
            message.setMsgResourceKey("6023059");
            // DFKLOOK:ここまで修正
            return;
        }

        // DFKLOOK start
        if (StringUtil.isBlank(eventSource))
        {         
            // 設定しますか?
            this.setConfirm("MSG-W9000", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Set_Click");
            return;         
        }
        // DFKLOOK end
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        DataReportEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DataReportEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_DataReportEnvironment.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_DataReportEnvironment.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                DataReportEnvironmentSCHParams lineparam = new DataReportEnvironmentSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(DataReportEnvironmentSCHParams.DATA_TYPE, line.getValue(KEY_HIDDEN_DATA_TYPE));
                lineparam.set(DataReportEnvironmentSCHParams.PACKAGE_NAME, line.getValue(KEY_LST_PACKAGE_NAME));
                lineparam.set(DataReportEnvironmentSCHParams.DESTINATION_FOLDER, line.getValue(KEY_LST_DESTINATION_FOLDER));
                lineparam.set(DataReportEnvironmentSCHParams.DATA_FILE_NAME, line.getValue(KEY_LST_DATA_FILE_NAME));
                lineparam.set(DataReportEnvironmentSCHParams.REPORT_UNIT, line.getValue(KEY_LST_REPORT_UNIT));
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
                _lcm_lst_DataReportEnvironment.resetEditRow();
                _lcm_lst_DataReportEnvironment.resetHighlight();
                _lcm_lst_DataReportEnvironment.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_DataReportEnvironment.resetEditRow();
            _lcm_lst_DataReportEnvironment.resetHighlight();

            // set input parameters.
            DataReportEnvironmentSCHParams inparam = new DataReportEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_DataReportEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_DataReportEnvironment.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(DataReportEnvironmentSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_PACKAGE_NAME, outparam.get(DataReportEnvironmentSCHParams.PACKAGE_NAME));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(DataReportEnvironmentSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_DATA_FILE_NAME, outparam.get(DataReportEnvironmentSCHParams.DATA_FILE_NAME));
                line.setValue(KEY_LST_REPORT_UNIT, outparam.get(DataReportEnvironmentSCHParams.REPORT_UNIT));
                lst_DataReportEnvironment_SetLineToolTip(line);
                _lcm_lst_DataReportEnvironment.add(line);
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
        DataReportEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DataReportEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DataReportEnvironmentSCHParams inparam = new DataReportEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_DataReportEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_DataReportEnvironment.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(DataReportEnvironmentSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_PACKAGE_NAME, outparam.get(DataReportEnvironmentSCHParams.PACKAGE_NAME));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(DataReportEnvironmentSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_DATA_FILE_NAME, outparam.get(DataReportEnvironmentSCHParams.DATA_FILE_NAME));
                line.setValue(KEY_LST_REPORT_UNIT, outparam.get(DataReportEnvironmentSCHParams.REPORT_UNIT));
                lst_DataReportEnvironment_SetLineToolTip(line);
                _lcm_lst_DataReportEnvironment.add(line);
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
