// $Id: MasterDataReportEnvironmentBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.master.display.masterdatareportenvironment;

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
import jp.co.daifuku.pcart.master.display.masterdatareportenvironment.MasterDataReportEnvironment;
import jp.co.daifuku.pcart.master.schedule.MasterDataReportEnvironmentSCH;
import jp.co.daifuku.pcart.master.schedule.MasterDataReportEnvironmentSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * マスタデータ報告環境設定の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class MasterDataReportEnvironmentBusiness
        extends MasterDataReportEnvironment
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

    /** lst_DataReportFileEnvironment(HIDDEN_DATA_TYPE) */
    private static final ListCellKey KEY_HIDDEN_DATA_TYPE = new ListCellKey("HIDDEN_DATA_TYPE", new StringCellColumn(), false, false);

    /** lst_DataReportFileEnvironment(LST_MASTER) */
    private static final ListCellKey KEY_LST_MASTER = new ListCellKey("LST_MASTER", new StringCellColumn(), true, false);

    /** lst_DataReportFileEnvironment(LST_DESTINATION_FOLDER) */
    private static final ListCellKey KEY_LST_DESTINATION_FOLDER = new ListCellKey("LST_DESTINATION_FOLDER", new StringCellColumn(), true, true);

    /** lst_DataReportFileEnvironment(LST_DATA_ID_NAME) */
    private static final ListCellKey KEY_LST_DATA_ID_NAME = new ListCellKey("LST_DATA_ID_NAME", new StringCellColumn(), true, false);

    /** lst_DataReportFileEnvironment keys */
    private static final ListCellKey[] LST_DATAREPORTFILEENVIRONMENT_KEYS = {
        KEY_HIDDEN_DATA_TYPE,
        KEY_LST_MASTER,
        KEY_LST_DESTINATION_FOLDER,
        KEY_LST_DATA_ID_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_DataReportFileEnvironment */
    private ListCellModel _lcm_lst_DataReportFileEnvironment;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public MasterDataReportEnvironmentBusiness()
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
        if (eventSource.startsWith("btn_Set_Click"))
        {
            btn_Set_Click_Process(eventSource);
        }
    }
    // DFKLOOK end

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_DataReportFileEnvironment_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_DataReportFileEnvironment_ColumClick(ActionEvent e)
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

        // initialize lst_DataReportFileEnvironment.
        _lcm_lst_DataReportFileEnvironment = new ListCellModel(lst_DataReportFileEnvironment, LST_DATAREPORTFILEENVIRONMENT_KEYS, locale);
        _lcm_lst_DataReportFileEnvironment.setToolTipVisible(KEY_LST_MASTER, false);
        _lcm_lst_DataReportFileEnvironment.setToolTipVisible(KEY_LST_DESTINATION_FOLDER, false);
        _lcm_lst_DataReportFileEnvironment.setToolTipVisible(KEY_LST_DATA_ID_NAME, false);

        // DFKLOOK:ここから修正
        // フォーカスセット
        // set focus.
        setFocus(lst_DataReportFileEnvironment);
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
    private void lst_DataReportFileEnvironment_SetLineToolTip(ListCellLine line)
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
        MasterDataReportEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new MasterDataReportEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            MasterDataReportEnvironmentSCHParams inparam = new MasterDataReportEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_DataReportFileEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_DataReportFileEnvironment.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(MasterDataReportEnvironmentSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_MASTER, outparam.get(MasterDataReportEnvironmentSCHParams.MASTER));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(MasterDataReportEnvironmentSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_DATA_ID_NAME, outparam.get(MasterDataReportEnvironmentSCHParams.PREFIX_NAME));
                lst_DataReportFileEnvironment_SetLineToolTip(line);
                _lcm_lst_DataReportFileEnvironment.add(line);
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
        for (int i = 1; i <= _lcm_lst_DataReportFileEnvironment.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_DataReportFileEnvironment.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_DataReportFileEnvironment.setCurrentRow(i);
            lst_DataReportFileEnvironment.validate(checkline.getIndex(KEY_LST_DESTINATION_FOLDER), true);
            lst_DataReportFileEnvironment.validate(checkline.getIndex(KEY_LST_DATA_ID_NAME), true);
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
        MasterDataReportEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new MasterDataReportEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_DataReportFileEnvironment.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_DataReportFileEnvironment.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                MasterDataReportEnvironmentSCHParams lineparam = new MasterDataReportEnvironmentSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(MasterDataReportEnvironmentSCHParams.DATA_TYPE, line.getValue(KEY_HIDDEN_DATA_TYPE));
                lineparam.set(MasterDataReportEnvironmentSCHParams.MASTER, line.getValue(KEY_LST_MASTER));
                lineparam.set(MasterDataReportEnvironmentSCHParams.DESTINATION_FOLDER, line.getValue(KEY_LST_DESTINATION_FOLDER));
                lineparam.set(MasterDataReportEnvironmentSCHParams.PREFIX_NAME, line.getValue(KEY_LST_DATA_ID_NAME));
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
                _lcm_lst_DataReportFileEnvironment.resetEditRow();
                _lcm_lst_DataReportFileEnvironment.resetHighlight();
                _lcm_lst_DataReportFileEnvironment.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_DataReportFileEnvironment.resetEditRow();
            _lcm_lst_DataReportFileEnvironment.resetHighlight();

            // set input parameters.
            MasterDataReportEnvironmentSCHParams inparam = new MasterDataReportEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // output display.
            _lcm_lst_DataReportFileEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_DataReportFileEnvironment.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(MasterDataReportEnvironmentSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_MASTER, outparam.get(MasterDataReportEnvironmentSCHParams.MASTER));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(MasterDataReportEnvironmentSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_DATA_ID_NAME, outparam.get(MasterDataReportEnvironmentSCHParams.PREFIX_NAME));
                lst_DataReportFileEnvironment_SetLineToolTip(line);
                _lcm_lst_DataReportFileEnvironment.add(line);
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
        MasterDataReportEnvironmentSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new MasterDataReportEnvironmentSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            MasterDataReportEnvironmentSCHParams inparam = new MasterDataReportEnvironmentSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_DataReportFileEnvironment.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_DataReportFileEnvironment.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(MasterDataReportEnvironmentSCHParams.DATA_TYPE));
                line.setValue(KEY_LST_MASTER, outparam.get(MasterDataReportEnvironmentSCHParams.MASTER));
                line.setValue(KEY_LST_DESTINATION_FOLDER, outparam.get(MasterDataReportEnvironmentSCHParams.DESTINATION_FOLDER));
                line.setValue(KEY_LST_DATA_ID_NAME, outparam.get(MasterDataReportEnvironmentSCHParams.PREFIX_NAME));
                lst_DataReportFileEnvironment_SetLineToolTip(line);
                _lcm_lst_DataReportFileEnvironment.add(line);
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
