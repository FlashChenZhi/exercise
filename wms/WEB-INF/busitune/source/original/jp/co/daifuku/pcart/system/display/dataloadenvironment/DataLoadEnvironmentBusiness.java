// $Id: DataLoadEnvironmentBusiness.java 7329 2010-03-02 05:19:24Z okayama $
package jp.co.daifuku.pcart.system.display.dataloadenvironment;

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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.system.display.dataloadenvironment.DataLoadEnvironment;
import jp.co.daifuku.pcart.system.schedule.DataLoadEnvironmentSCH;
import jp.co.daifuku.pcart.system.schedule.DataLoadEnvironmentSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7329 $, $Date:: 2010-03-02 14:19:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class DataLoadEnvironmentBusiness
        extends DataLoadEnvironment
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_DataLoadEnvironment(HIDDEN_DATA_TYPE) */
    private static final ListCellKey KEY_HIDDEN_DATA_TYPE = new ListCellKey("HIDDEN_DATA_TYPE", new StringCellColumn(), false, false);

    /** lst_DataLoadEnvironment(LST_PACKAGE) */
    private static final ListCellKey KEY_LST_PACKAGE = new ListCellKey("LST_PACKAGE", new StringCellColumn(), true, false);

    /** lst_DataLoadEnvironment(LST_DESTINATION_FOLDER) */
    private static final ListCellKey KEY_LST_DESTINATION_FOLDER = new ListCellKey("LST_DESTINATION_FOLDER", new StringCellColumn(), true, true);

    /** lst_DataLoadEnvironment(LST_DATA_FILE_NAME) */
    private static final ListCellKey KEY_LST_DATA_FILE_NAME = new ListCellKey("LST_DATA_FILE_NAME", new StringCellColumn(), true, true);

    /** lst_DataLoadEnvironment keys */
    private static final ListCellKey[] LST_DATALOADENVIRONMENT_KEYS = {
        KEY_HIDDEN_DATA_TYPE,
        KEY_LST_PACKAGE,
        KEY_LST_DESTINATION_FOLDER,
        KEY_LST_DATA_FILE_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_DataLoadEnvironment */
    private ScrollListCellModel _lcm_lst_DataLoadEnvironment;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public DataLoadEnvironmentBusiness()
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

        // initialize lst_DataLoadEnvironment.
        _lcm_lst_DataLoadEnvironment = new ScrollListCellModel(lst_DataLoadEnvironment, LST_DATALOADENVIRONMENT_KEYS, locale);
        _lcm_lst_DataLoadEnvironment.setToolTipVisible(KEY_LST_PACKAGE, false);
        _lcm_lst_DataLoadEnvironment.setToolTipVisible(KEY_LST_DESTINATION_FOLDER, false);
        _lcm_lst_DataLoadEnvironment.setToolTipVisible(KEY_LST_DATA_FILE_NAME, false);
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
    private void lst_DataLoadEnvironment_SetLineToolTip(ListCellLine line)
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
        DataLoadEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DataLoadEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DataLoadEnvironmentSCHParams inparam = new DataLoadEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_DataLoadEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_DataLoadEnvironment.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(DataLoadEnvironmentSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_PACKAGE, outparam.get(DataLoadEnvironmentSCHParams.PACKAGE_NAME));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(DataLoadEnvironmentSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_DATA_FILE_NAME, outparam.get(DataLoadEnvironmentSCHParams.DATA_FILE_NAME));
                lst_DataLoadEnvironment_SetLineToolTip(line);
                _lcm_lst_DataLoadEnvironment.add(line);
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
    private void btn_Set_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_DataLoadEnvironment.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_DataLoadEnvironment.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_DataLoadEnvironment.setCurrentRow(i);
            lst_DataLoadEnvironment.validate(checkline.getIndex(KEY_LST_DESTINATION_FOLDER), true);
            lst_DataLoadEnvironment.validate(checkline.getIndex(KEY_LST_DATA_FILE_NAME), true);
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
        DataLoadEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DataLoadEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_DataLoadEnvironment.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_DataLoadEnvironment.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                DataLoadEnvironmentSCHParams lineparam = new DataLoadEnvironmentSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(DataLoadEnvironmentSCHParams.DATA_TYPE, line.getValue(KEY_HIDDEN_DATA_TYPE));
                lineparam.set(DataLoadEnvironmentSCHParams.PACKAGE_NAME, line.getValue(KEY_LST_PACKAGE));
                lineparam.set(DataLoadEnvironmentSCHParams.DESTINATION_FOLDER, line.getValue(KEY_LST_DESTINATION_FOLDER));
                lineparam.set(DataLoadEnvironmentSCHParams.DATA_FILE_NAME, line.getValue(KEY_LST_DATA_FILE_NAME));
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
                _lcm_lst_DataLoadEnvironment.resetEditRow();
                _lcm_lst_DataLoadEnvironment.resetHighlight();
                _lcm_lst_DataLoadEnvironment.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_DataLoadEnvironment.resetEditRow();
            _lcm_lst_DataLoadEnvironment.resetHighlight();

            // set input parameters.
            DataLoadEnvironmentSCHParams inparam = new DataLoadEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_DataLoadEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_DataLoadEnvironment.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(DataLoadEnvironmentSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_PACKAGE, outparam.get(DataLoadEnvironmentSCHParams.PACKAGE_NAME));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(DataLoadEnvironmentSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_DATA_FILE_NAME, outparam.get(DataLoadEnvironmentSCHParams.DATA_FILE_NAME));
                lst_DataLoadEnvironment_SetLineToolTip(line);
                _lcm_lst_DataLoadEnvironment.add(line);
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
        DataLoadEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DataLoadEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DataLoadEnvironmentSCHParams inparam = new DataLoadEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_DataLoadEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_DataLoadEnvironment.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(DataLoadEnvironmentSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_PACKAGE, outparam.get(DataLoadEnvironmentSCHParams.PACKAGE_NAME));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(DataLoadEnvironmentSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_DATA_FILE_NAME, outparam.get(DataLoadEnvironmentSCHParams.DATA_FILE_NAME));
                lst_DataLoadEnvironment_SetLineToolTip(line);
                _lcm_lst_DataLoadEnvironment.add(line);
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
