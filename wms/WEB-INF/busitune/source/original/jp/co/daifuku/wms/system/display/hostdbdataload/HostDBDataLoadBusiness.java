// $Id: HostDBDataLoadBusiness.java 7850 2010-04-21 05:32:46Z shibamoto $
package jp.co.daifuku.wms.system.display.hostdbdataload;

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
import jp.co.daifuku.wms.system.display.hostdbdataload.HostDBDataLoad;
import jp.co.daifuku.wms.system.schedule.HostDBDataLoadSCH;
import jp.co.daifuku.wms.system.schedule.HostDBDataLoadSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7850 $, $Date:: 2010-04-21 14:32:46 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class HostDBDataLoadBusiness
        extends HostDBDataLoad
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_LoadData(HIDDEN_CLASS_NAME) */
    private static final ListCellKey KEY_HIDDEN_CLASS_NAME = new ListCellKey("HIDDEN_CLASS_NAME", new StringCellColumn(), false, false);

    /** lst_LoadData(HIDDEN_DATA_TYPE) */
    private static final ListCellKey KEY_HIDDEN_DATA_TYPE = new ListCellKey("HIDDEN_DATA_TYPE", new StringCellColumn(), false, false);

    /** lst_LoadData(HIDDEN_AUTO_PRINT) */
    private static final ListCellKey KEY_HIDDEN_AUTO_PRINT = new ListCellKey("HIDDEN_AUTO_PRINT", new StringCellColumn(), false, false);

    /** lst_LoadData(LST_IMPORT_DATA_TYPE) */
    private static final ListCellKey KEY_LST_IMPORT_DATA_TYPE = new ListCellKey("LST_IMPORT_DATA_TYPE", new StringCellColumn(), true, false);

    /** lst_LoadData(LST_RESULT_OF_IMPORT) */
    private static final ListCellKey KEY_LST_RESULT_OF_IMPORT = new ListCellKey("LST_RESULT_OF_IMPORT", new StringCellColumn(), true, false);

    /** lst_LoadData(LST_NO_OF_IMPORT_MSGS) */
    private static final ListCellKey KEY_LST_NO_OF_IMPORT_MSGS = new ListCellKey("LST_NO_OF_IMPORT_MSGS", new NumberCellColumn(0), true, false);

    /** lst_LoadData keys */
    private static final ListCellKey[] LST_LOADDATA_KEYS = {
        KEY_HIDDEN_CLASS_NAME,
        KEY_HIDDEN_DATA_TYPE,
        KEY_HIDDEN_AUTO_PRINT,
        KEY_LST_IMPORT_DATA_TYPE,
        KEY_LST_RESULT_OF_IMPORT,
        KEY_LST_NO_OF_IMPORT_MSGS,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_LoadData */
    private ScrollListCellModel _lcm_lst_LoadData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public HostDBDataLoadBusiness()
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
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Start_Click_Process();
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

        // initialize lst_LoadData.
        _lcm_lst_LoadData = new ScrollListCellModel(lst_LoadData, LST_LOADDATA_KEYS, locale);
        _lcm_lst_LoadData.setToolTipVisible(KEY_LST_IMPORT_DATA_TYPE, false);
        _lcm_lst_LoadData.setToolTipVisible(KEY_LST_RESULT_OF_IMPORT, false);
        _lcm_lst_LoadData.setToolTipVisible(KEY_LST_NO_OF_IMPORT_MSGS, false);
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
    private void lst_LoadData_SetLineToolTip(ListCellLine line)
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
        HostDBDataLoadSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new HostDBDataLoadSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            HostDBDataLoadSCHParams inparam = new HostDBDataLoadSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_LoadData.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LoadData.getNewLine();
                line.setValue(KEY_LST_IMPORT_DATA_TYPE, outparam.get(HostDBDataLoadSCHParams.IMPORT_DATA_TYPE));
                line.setValue(KEY_LST_RESULT_OF_IMPORT, outparam.get(HostDBDataLoadSCHParams.RESULT_OF_IMPORT));
                line.setValue(KEY_LST_NO_OF_IMPORT_MSGS, outparam.get(HostDBDataLoadSCHParams.NO_OF_IMPORT_MSGS));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(HostDBDataLoadSCHParams.CLASS_NAME));
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(HostDBDataLoadSCHParams.DATA_TYPE));
                line.setValue(KEY_HIDDEN_AUTO_PRINT, outparam.get(HostDBDataLoadSCHParams.AUTO_PRINT));
                lst_LoadData_SetLineToolTip(line);
                _lcm_lst_LoadData.add(line);
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
    private void btn_Start_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        HostDBDataLoadSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new HostDBDataLoadSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_LoadData.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_LoadData.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                HostDBDataLoadSCHParams lineparam = new HostDBDataLoadSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(HostDBDataLoadSCHParams.CLASS_NAME, line.getValue(KEY_HIDDEN_CLASS_NAME));
                lineparam.set(HostDBDataLoadSCHParams.DATA_TYPE, line.getValue(KEY_HIDDEN_DATA_TYPE));
                lineparam.set(HostDBDataLoadSCHParams.AUTO_PRINT, line.getValue(KEY_HIDDEN_AUTO_PRINT));
                lineparam.set(HostDBDataLoadSCHParams.IMPORT_DATA_TYPE, line.getValue(KEY_LST_IMPORT_DATA_TYPE));
                lineparam.set(HostDBDataLoadSCHParams.NO_OF_IMPORT_MSGS, line.getValue(KEY_LST_NO_OF_IMPORT_MSGS));
                inparamList.add(lineparam);
            }

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            // SCH call.
            List<Params> outparams = sch.startSCHgetParams(inparams);

            if (outparams == null)
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                // reset editing row or highlighting error row.
                _lcm_lst_LoadData.resetEditRow();
                _lcm_lst_LoadData.resetHighlight();
                _lcm_lst_LoadData.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_LoadData.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LoadData.getNewLine();
                line.setValue(KEY_LST_IMPORT_DATA_TYPE, outparam.get(HostDBDataLoadSCHParams.IMPORT_DATA_TYPE));
                line.setValue(KEY_LST_RESULT_OF_IMPORT, outparam.get(HostDBDataLoadSCHParams.RESULT_OF_IMPORT));
                line.setValue(KEY_LST_NO_OF_IMPORT_MSGS, outparam.get(HostDBDataLoadSCHParams.NO_OF_IMPORT_MSGS));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(HostDBDataLoadSCHParams.CLASS_NAME));
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(HostDBDataLoadSCHParams.DATA_TYPE));
                line.setValue(KEY_HIDDEN_AUTO_PRINT, outparam.get(HostDBDataLoadSCHParams.AUTO_PRINT));
                lst_LoadData_SetLineToolTip(line);
                _lcm_lst_LoadData.add(line);
            }

            // reset editing row.
            _lcm_lst_LoadData.resetEditRow();
            _lcm_lst_LoadData.resetHighlight();
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
        HostDBDataLoadSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new HostDBDataLoadSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            HostDBDataLoadSCHParams inparam = new HostDBDataLoadSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_LoadData.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LoadData.getNewLine();
                line.setValue(KEY_LST_IMPORT_DATA_TYPE, outparam.get(HostDBDataLoadSCHParams.IMPORT_DATA_TYPE));
                line.setValue(KEY_LST_RESULT_OF_IMPORT, outparam.get(HostDBDataLoadSCHParams.RESULT_OF_IMPORT));
                line.setValue(KEY_LST_NO_OF_IMPORT_MSGS, outparam.get(HostDBDataLoadSCHParams.NO_OF_IMPORT_MSGS));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(HostDBDataLoadSCHParams.CLASS_NAME));
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(HostDBDataLoadSCHParams.DATA_TYPE));
                line.setValue(KEY_HIDDEN_AUTO_PRINT, outparam.get(HostDBDataLoadSCHParams.AUTO_PRINT));
                lst_LoadData_SetLineToolTip(line);
                _lcm_lst_LoadData.add(line);
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
