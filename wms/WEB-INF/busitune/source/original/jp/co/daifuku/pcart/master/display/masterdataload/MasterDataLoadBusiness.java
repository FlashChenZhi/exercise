// $Id: MasterDataLoadBusiness.java 7329 2010-03-02 05:19:24Z okayama $
package jp.co.daifuku.pcart.master.display.masterdataload;

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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.master.display.masterdataload.MasterDataLoad;
import jp.co.daifuku.pcart.master.schedule.MasterDataLoadSCH;
import jp.co.daifuku.pcart.master.schedule.MasterDataLoadSCHParams;
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
public class MasterDataLoadBusiness
        extends MasterDataLoad
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_LoadData(HIDDEN_DATA_TYPE) */
    private static final ListCellKey KEY_HIDDEN_DATA_TYPE = new ListCellKey("HIDDEN_DATA_TYPE", new StringCellColumn(), false, false);

    /** lst_LoadData(HIDDEN_DATA_COUNT) */
    private static final ListCellKey KEY_HIDDEN_DATA_COUNT = new ListCellKey("HIDDEN_DATA_COUNT", new NumberCellColumn(0), false, false);

    /** lst_LoadData(HIDDEN_CLASS_NAME) */
    private static final ListCellKey KEY_HIDDEN_CLASS_NAME = new ListCellKey("HIDDEN_CLASS_NAME", new StringCellColumn(), false, false);

    /** lst_LoadData(HIDDEN_AUTO_PRINT) */
    private static final ListCellKey KEY_HIDDEN_AUTO_PRINT = new ListCellKey("HIDDEN_AUTO_PRINT", new StringCellColumn(), false, false);

    /** lst_LoadData(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_LoadData(LST_LOAD_DATA_TYPE) */
    private static final ListCellKey KEY_LST_LOAD_DATA_TYPE = new ListCellKey("LST_LOAD_DATA_TYPE", new StringCellColumn(), true, false);

    /** lst_LoadData(LST_LOAD_FILE_NAME) */
    private static final ListCellKey KEY_LST_LOAD_FILE_NAME = new ListCellKey("LST_LOAD_FILE_NAME", new StringCellColumn(), true, false);

    /** lst_LoadData(LST_MESSAGE) */
    private static final ListCellKey KEY_LST_MESSAGE = new ListCellKey("LST_MESSAGE", new StringCellColumn(), true, false);

    /** lst_LoadData keys */
    private static final ListCellKey[] LST_LOADDATA_KEYS = {
        KEY_HIDDEN_DATA_TYPE,
        KEY_HIDDEN_DATA_COUNT,
        KEY_HIDDEN_CLASS_NAME,
        KEY_HIDDEN_AUTO_PRINT,
        KEY_LST_SELECT,
        KEY_LST_LOAD_DATA_TYPE,
        KEY_LST_LOAD_FILE_NAME,
        KEY_LST_MESSAGE,
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
    public MasterDataLoadBusiness()
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
        _lcm_lst_LoadData.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_LoadData.setToolTipVisible(KEY_LST_LOAD_DATA_TYPE, false);
        _lcm_lst_LoadData.setToolTipVisible(KEY_LST_LOAD_FILE_NAME, false);
        _lcm_lst_LoadData.setToolTipVisible(KEY_LST_MESSAGE, false);

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
        MasterDataLoadSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new MasterDataLoadSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            MasterDataLoadSCHParams inparam = new MasterDataLoadSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_LoadData.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LoadData.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(MasterDataLoadSCHParams.DATA_TYPE));
                line.setValue(KEY_HIDDEN_DATA_COUNT, outparam.get(MasterDataLoadSCHParams.DATA_COUNT));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(MasterDataLoadSCHParams.CLASS_NAME));
                line.setValue(KEY_HIDDEN_AUTO_PRINT, outparam.get(MasterDataLoadSCHParams.AUTO_PRINT));
                line.setValue(KEY_LST_SELECT, outparam.get(MasterDataLoadSCHParams.SELECT));
                line.setValue(KEY_LST_LOAD_DATA_TYPE, outparam.get(MasterDataLoadSCHParams.LOAD_DATA_TYPE));
                line.setValue(KEY_LST_LOAD_FILE_NAME, outparam.get(MasterDataLoadSCHParams.LOAD_FILE_NAME));
                line.setValue(KEY_LST_MESSAGE, outparam.get(MasterDataLoadSCHParams.MESSAGE));
                lst_LoadData_SetLineToolTip(line);
                _lcm_lst_LoadData.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_LoadData.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_LoadData.get(i);
                lst_LoadData.setCurrentRow(i);
                clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
                lst_LoadData_SetLineToolTip(clearLine);
                _lcm_lst_LoadData.set(i, clearLine);
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
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_LoadData.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_LoadData.get(i);
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
        MasterDataLoadSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new MasterDataLoadSCH(conn, this.getClass(), locale, ui);

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

                MasterDataLoadSCHParams lineparam = new MasterDataLoadSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(MasterDataLoadSCHParams.DATA_TYPE, line.getValue(KEY_HIDDEN_DATA_TYPE));
                lineparam.set(MasterDataLoadSCHParams.CLASS_NAME, line.getValue(KEY_HIDDEN_CLASS_NAME));
                lineparam.set(MasterDataLoadSCHParams.AUTO_PRINT, line.getValue(KEY_HIDDEN_AUTO_PRINT));
                lineparam.set(MasterDataLoadSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(MasterDataLoadSCHParams.LOAD_DATA_TYPE, line.getValue(KEY_LST_LOAD_DATA_TYPE));
                lineparam.set(MasterDataLoadSCHParams.LOAD_FILE_NAME, line.getValue(KEY_LST_LOAD_FILE_NAME));
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
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(MasterDataLoadSCHParams.DATA_TYPE));
                line.setValue(KEY_HIDDEN_DATA_COUNT, outparam.get(MasterDataLoadSCHParams.DATA_COUNT));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(MasterDataLoadSCHParams.CLASS_NAME));
                line.setValue(KEY_HIDDEN_AUTO_PRINT, outparam.get(MasterDataLoadSCHParams.AUTO_PRINT));
                line.setValue(KEY_LST_SELECT, outparam.get(MasterDataLoadSCHParams.SELECT));
                line.setValue(KEY_LST_LOAD_DATA_TYPE, outparam.get(MasterDataLoadSCHParams.LOAD_DATA_TYPE));
                line.setValue(KEY_LST_LOAD_FILE_NAME, outparam.get(MasterDataLoadSCHParams.LOAD_FILE_NAME));
                line.setValue(KEY_LST_MESSAGE, outparam.get(MasterDataLoadSCHParams.MESSAGE));
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
        MasterDataLoadSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new MasterDataLoadSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            MasterDataLoadSCHParams inparam = new MasterDataLoadSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_LoadData.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LoadData.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(MasterDataLoadSCHParams.DATA_TYPE));
                line.setValue(KEY_HIDDEN_DATA_COUNT, outparam.get(MasterDataLoadSCHParams.DATA_COUNT));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(MasterDataLoadSCHParams.CLASS_NAME));
                line.setValue(KEY_HIDDEN_AUTO_PRINT, outparam.get(MasterDataLoadSCHParams.AUTO_PRINT));
                line.setValue(KEY_LST_SELECT, outparam.get(MasterDataLoadSCHParams.SELECT));
                line.setValue(KEY_LST_LOAD_DATA_TYPE, outparam.get(MasterDataLoadSCHParams.LOAD_DATA_TYPE));
                line.setValue(KEY_LST_LOAD_FILE_NAME, outparam.get(MasterDataLoadSCHParams.LOAD_FILE_NAME));
                line.setValue(KEY_LST_MESSAGE, outparam.get(MasterDataLoadSCHParams.MESSAGE));
                lst_LoadData_SetLineToolTip(line);
                _lcm_lst_LoadData.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_LoadData.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_LoadData.get(i);
                lst_LoadData.setCurrentRow(i);
                clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
                lst_LoadData_SetLineToolTip(clearLine);
                _lcm_lst_LoadData.set(i, clearLine);
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
