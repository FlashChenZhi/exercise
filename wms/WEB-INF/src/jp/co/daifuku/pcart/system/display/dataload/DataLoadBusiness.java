// $Id: DataLoadBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.system.display.dataload;

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
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
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
import jp.co.daifuku.pcart.system.schedule.DataLoadSCH;
import jp.co.daifuku.pcart.system.schedule.DataLoadSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * データ取込（ファイル）の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class DataLoadBusiness
        extends DataLoad
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

    /** lst_LoadData(HIDDEN_DATA_TYPE) */
    private static final ListCellKey KEY_HIDDEN_DATA_TYPE =
            new ListCellKey("HIDDEN_DATA_TYPE", new StringCellColumn(), false, false);

    /** lst_LoadData(HIDDEN_FILE_COUNT) */
    private static final ListCellKey KEY_HIDDEN_FILE_COUNT =
            new ListCellKey("HIDDEN_FILE_COUNT", new StringCellColumn(), false, false);

    /** lst_LoadData(HIDDEN_CLASS_NAME) */
    private static final ListCellKey KEY_HIDDEN_CLASS_NAME =
            new ListCellKey("HIDDEN_CLASS_NAME", new StringCellColumn(), false, false);

    /** lst_LoadData(HIDDEN_AUTO_PRINT) */
    private static final ListCellKey KEY_HIDDEN_AUTO_PRINT =
            new ListCellKey("HIDDEN_AUTO_PRINT", new StringCellColumn(), false, false);

    /** lst_LoadData(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_LoadData(LST_LOAD_DATA_TYPE) */
    private static final ListCellKey KEY_LST_LOAD_DATA_TYPE =
            new ListCellKey("LST_LOAD_DATA_TYPE", new StringCellColumn(), true, false);

    /** lst_LoadData(LST_LOAD_FILE_NAME) */
    private static final ListCellKey KEY_LST_LOAD_FILE_NAME =
            new ListCellKey("LST_LOAD_FILE_NAME", new StringCellColumn(), true, false);

    /** lst_LoadData(LST_MESSAGE) */
    private static final ListCellKey KEY_LST_MESSAGE =
            new ListCellKey("LST_MESSAGE", new StringCellColumn(), true, false);

    /** lst_LoadData keys */
    private static final ListCellKey[] LST_LOADDATA_KEYS = {
            KEY_HIDDEN_DATA_TYPE,
            KEY_HIDDEN_FILE_COUNT,
            KEY_HIDDEN_CLASS_NAME,
            KEY_HIDDEN_AUTO_PRINT,
            KEY_LST_SELECT,
            KEY_LST_LOAD_DATA_TYPE,
            KEY_LST_LOAD_FILE_NAME,
            KEY_LST_MESSAGE,
    };

    // DFKLOOK:ここから修正
    /**
     * メッセージ
     */
    private static final int MESSAGE = 4;

    // DFKLOOK:ここまで修正

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_LoadData */
    private ListCellModel _lcm_lst_LoadData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public DataLoadBusiness()
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

    // DFKLOOK start
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
        if (eventSource.startsWith("btn_Start_Click"))
        {
            btn_Start_Click_Process(eventSource);
        }
    }
    // DFKLOOK end
    
    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Start_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Start_Click_Process(null);
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
        _lcm_lst_LoadData = new ListCellModel(lst_LoadData, LST_LOADDATA_KEYS, locale);
        _lcm_lst_LoadData.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_LoadData.setToolTipVisible(KEY_LST_LOAD_DATA_TYPE, false);
        _lcm_lst_LoadData.setToolTipVisible(KEY_LST_LOAD_FILE_NAME, false);
        _lcm_lst_LoadData.setToolTipVisible(KEY_LST_MESSAGE, false);

        // DFKLOOK:ここから修正
        // フォーカスセット
        setFocus(lst_LoadData);
        // DFKLOOK:ここまで修正

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
        DataLoadSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DataLoadSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DataLoadSCHParams inparam = new DataLoadSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_LoadData.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LoadData.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(DataLoadSCHParams.DATA_TYPE));
                line.setValue(KEY_HIDDEN_FILE_COUNT, outparam.get(DataLoadSCHParams.FILE_COUNT));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(DataLoadSCHParams.CLASS_NAME));
                line.setValue(KEY_HIDDEN_AUTO_PRINT, outparam.get(DataLoadSCHParams.AUTO_PRINT));
                line.setValue(KEY_LST_LOAD_DATA_TYPE, outparam.get(DataLoadSCHParams.LOAD_DATA_TYPE));
                line.setValue(KEY_LST_LOAD_FILE_NAME, outparam.get(DataLoadSCHParams.LOAD_FILE_NAME));
                line.setValue(KEY_LST_MESSAGE, outparam.get(DataLoadSCHParams.MESSAGE));
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
    private void btn_Start_Click_Process(String eventSource)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // 選択チェック
        boolean checkflg = false;
        for (int i = 1; i < lst_LoadData.getMaxRows(); i++)
        {
            lst_LoadData.setCurrentRow(i);
            if (lst_LoadData.getChecked(i))
            {
                checkflg = true;
                break;
            }
        }
        if (!checkflg)
        {
            // 6003001 = データを選択してください。
            message.setMsgResourceKey("6003001");

            // ﾒｯｾｰｼﾞ項目のクリア            
            for (int i = 1; i < lst_LoadData.getMaxRows(); i++)
            {
                if (Integer.valueOf(_lcm_lst_LoadData.get(i).getStringValue(KEY_HIDDEN_FILE_COUNT)) == 0)
                {
                    lst_LoadData.setValue(MESSAGE, "");
                }
            }

            return;
        }

        if (StringUtil.isBlank(eventSource))
        {         
            // 開始しますか?
            this.setConfirm("MSG-W0031", false, true);
            viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click");
            return;         
        }
        // DFKLOOK:ここまで修正

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        DataLoadSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DataLoadSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_LoadData.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_LoadData.get(i);
                //DFKLOOK:ここから修正
                //                if (!(line.isAppend() || line.isEdited()))
                //                {
                //                    continue;
                //                }
                //DFKLOOK:ここまで修正

                DataLoadSCHParams lineparam = new DataLoadSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(DataLoadSCHParams.DATA_TYPE, line.getValue(KEY_HIDDEN_DATA_TYPE));
                lineparam.set(DataLoadSCHParams.FILE_COUNT, line.getValue(KEY_HIDDEN_FILE_COUNT));
                lineparam.set(DataLoadSCHParams.CLASS_NAME, line.getValue(KEY_HIDDEN_CLASS_NAME));
                lineparam.set(DataLoadSCHParams.AUTO_PRINT, line.getValue(KEY_HIDDEN_AUTO_PRINT));
                lineparam.set(DataLoadSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(DataLoadSCHParams.LOAD_DATA_TYPE, line.getValue(KEY_LST_LOAD_DATA_TYPE));
                lineparam.set(DataLoadSCHParams.LOAD_FILE_NAME, line.getValue(KEY_LST_LOAD_FILE_NAME));
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
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(DataLoadSCHParams.DATA_TYPE));
                line.setValue(KEY_HIDDEN_FILE_COUNT, outparam.get(DataLoadSCHParams.FILE_COUNT));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(DataLoadSCHParams.CLASS_NAME));
                line.setValue(KEY_HIDDEN_AUTO_PRINT, outparam.get(DataLoadSCHParams.AUTO_PRINT));
                line.setValue(KEY_LST_SELECT, outparam.get(DataLoadSCHParams.SELECT));
                line.setValue(KEY_LST_LOAD_DATA_TYPE, outparam.get(DataLoadSCHParams.LOAD_DATA_TYPE));
                line.setValue(KEY_LST_LOAD_FILE_NAME, outparam.get(DataLoadSCHParams.LOAD_FILE_NAME));
                line.setValue(KEY_LST_MESSAGE, outparam.get(DataLoadSCHParams.MESSAGE));
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
        DataLoadSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new DataLoadSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            DataLoadSCHParams inparam = new DataLoadSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_LoadData.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LoadData.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(DataLoadSCHParams.DATA_TYPE));
                line.setValue(KEY_HIDDEN_FILE_COUNT, outparam.get(DataLoadSCHParams.FILE_COUNT));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(DataLoadSCHParams.CLASS_NAME));
                line.setValue(KEY_HIDDEN_AUTO_PRINT, outparam.get(DataLoadSCHParams.AUTO_PRINT));
                line.setValue(KEY_LST_LOAD_DATA_TYPE, outparam.get(DataLoadSCHParams.LOAD_DATA_TYPE));
                line.setValue(KEY_LST_LOAD_FILE_NAME, outparam.get(DataLoadSCHParams.LOAD_FILE_NAME));
                line.setValue(KEY_LST_MESSAGE, outparam.get(DataLoadSCHParams.MESSAGE));
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
