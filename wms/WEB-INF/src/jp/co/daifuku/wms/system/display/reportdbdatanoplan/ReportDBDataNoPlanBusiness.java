// $Id: ReportDBDataNoPlanBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.system.display.reportdbdatanoplan;

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
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
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
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.system.display.reportdbdatanoplan.ReportDBDataNoPlan;
import jp.co.daifuku.wms.system.schedule.ReportDBDataNoPlanSCH;
import jp.co.daifuku.wms.system.schedule.ReportDBDataNoPlanSCHParams;

/**
 * DB予定外作業報告の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
@SuppressWarnings("serial")
public class ReportDBDataNoPlanBusiness
        extends ReportDBDataNoPlan
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    // DFKLOOK start
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";
    // DFKLOOK end
    
    /** lst_NoPlanReport(HIDDEN_DATA_TYPE) */
    private static final ListCellKey KEY_HIDDEN_DATA_TYPE = new ListCellKey("HIDDEN_DATA_TYPE", new StringCellColumn(), false, false);

    /** lst_NoPlanReport(HIDDEN_LOG_FLAG) */
    private static final ListCellKey KEY_HIDDEN_LOG_FLAG = new ListCellKey("HIDDEN_LOG_FLAG", new StringCellColumn(), false, false);

    /** lst_NoPlanReport(HIDDEN_CLASS_NAME) */
    private static final ListCellKey KEY_HIDDEN_CLASS_NAME = new ListCellKey("HIDDEN_CLASS_NAME", new StringCellColumn(), false, false);

    /** lst_NoPlanReport(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_NoPlanReport(LST_REPORT_DATA_TYPE) */
    private static final ListCellKey KEY_LST_REPORT_DATA_TYPE = new ListCellKey("LST_REPORT_DATA_TYPE", new StringCellColumn(), true, false);

    /** lst_NoPlanReport(LST_MESSAGE) */
    private static final ListCellKey KEY_LST_MESSAGE = new ListCellKey("LST_MESSAGE", new StringCellColumn(), true, false);

    /** lst_NoPlanReport keys */
    private static final ListCellKey[] LST_NOPLANREPORT_KEYS = {
        KEY_HIDDEN_DATA_TYPE,
        KEY_HIDDEN_LOG_FLAG,
        KEY_HIDDEN_CLASS_NAME,
        KEY_LST_SELECT,
        KEY_LST_REPORT_DATA_TYPE,
        KEY_LST_MESSAGE,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_NoPlanReport */
    private ListCellModel _lcm_lst_NoPlanReport;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ReportDBDataNoPlanBusiness()
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
        if (eventSource.startsWith("btn_Start_Click"))
        {
            // process call.
            btn_Start_Click_Process(eventSource);
        }
        // DFKLOOK end
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

        // initialize lst_NoPlanReport.
        _lcm_lst_NoPlanReport = new ListCellModel(lst_NoPlanReport, LST_NOPLANREPORT_KEYS, locale);
        _lcm_lst_NoPlanReport.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_NoPlanReport.setToolTipVisible(KEY_LST_REPORT_DATA_TYPE, false);
        _lcm_lst_NoPlanReport.setToolTipVisible(KEY_LST_MESSAGE, false);

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
    @SuppressWarnings("all")
    private void dispose()
            throws Exception
    {
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_NoPlanReport_SetLineToolTip(ListCellLine line)
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
        Connection customerConn = null;
        ReportDBDataNoPlanSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            customerConn = WmsParam.getCustomerConnection(); 
            sch = new ReportDBDataNoPlanSCH(conn, customerConn, this.getClass(), locale, ui);

            // set input parameters.
            ReportDBDataNoPlanSCHParams inparam = new ReportDBDataNoPlanSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_NoPlanReport.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_NoPlanReport.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(ReportDBDataNoPlanSCHParams.DATA_TYPE));
                line.setValue(KEY_HIDDEN_LOG_FLAG, outparam.get(ReportDBDataNoPlanSCHParams.LOG_FLAG));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(ReportDBDataNoPlanSCHParams.CLASS_NAME));
                line.setValue(KEY_LST_REPORT_DATA_TYPE, outparam.get(ReportDBDataNoPlanSCHParams.REPORT_DATA_TYPE));
                line.setValue(KEY_LST_MESSAGE, outparam.get(ReportDBDataNoPlanSCHParams.MESSAGE));
                lst_NoPlanReport_SetLineToolTip(line);
                _lcm_lst_NoPlanReport.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_NoPlanReport.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_NoPlanReport.get(i);
                lst_NoPlanReport.setCurrentRow(i);
                clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
                lst_NoPlanReport_SetLineToolTip(clearLine);
                _lcm_lst_NoPlanReport.set(i, clearLine);
            }

            // set focus.
//            setFocus(btn_ToMenu);

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
        // DFKLOOK start
        // input validation.
        boolean checkflg = false;
        for (int i = 1; i < lst_NoPlanReport.getMaxRows(); i++)
        {
            lst_NoPlanReport.setCurrentRow(i);
            if (lst_NoPlanReport.getChecked(1))
            {
                checkflg = true;
                break;
            }
        }
        if (!checkflg)
        {
            // 6003001 = データを選択してください。
            message.setMsgResourceKey("6003001");
            return;
        }
        // DFKLOOK end
        
        // DFKLOOK start
        if(StringUtil.isBlank(eventSource))
        {
        	// 開始しますか？
            this.setConfirm("MSG-W0031", true, true);
    		viewState.setString(_KEY_CONFIRMSOURCE, "btn_Start_Click");
            // 「処理中です」メッセージ表示
            message.setMsgResourceKey("6001017");
            return;
        }
        // DFKLOOK end

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        Connection customerConn = null;
        ReportDBDataNoPlanSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            customerConn = WmsParam.getCustomerConnection(); 
            sch = new ReportDBDataNoPlanSCH(conn, customerConn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_NoPlanReport.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_NoPlanReport.get(i);
                // DFKLOOK start
//                if (!(line.isAppend() || line.isEdited()))
//                {
//                    continue;
//                }
                // DFKLOOK end

                ReportDBDataNoPlanSCHParams lineparam = new ReportDBDataNoPlanSCHParams();
                lineparam.setProcessFlag(ProcessFlag.REGIST);
                lineparam.setRowIndex(i);
                lineparam.set(ReportDBDataNoPlanSCHParams.DATA_TYPE, line.getValue(KEY_HIDDEN_DATA_TYPE));
                lineparam.set(ReportDBDataNoPlanSCHParams.SELECT, line.getValue(KEY_LST_SELECT));
                lineparam.set(ReportDBDataNoPlanSCHParams.CLASS_NAME, line.getValue(KEY_HIDDEN_CLASS_NAME));
                lineparam.set(ReportDBDataNoPlanSCHParams.REPORT_DATA_TYPE, line.getValue(KEY_LST_REPORT_DATA_TYPE));
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
                _lcm_lst_NoPlanReport.resetEditRow();
                _lcm_lst_NoPlanReport.resetHighlight();
                _lcm_lst_NoPlanReport.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_NoPlanReport.size(); i++)
            {
                ListCellLine line = _lcm_lst_NoPlanReport.get(i);
                lst_NoPlanReport.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }
                // DFKLOOK start
                // 取り込みを行えていない行は対象外
                if (!outparams.get(i - 1).getBoolean(ReportDBDataNoPlanSCHParams.LOG_FLAG))
                {
                	continue;
                }
                // DFKLOOK end

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_HIDDEN_DATA_TYPE), "");
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_NoPlanReport.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_NoPlanReport.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(ReportDBDataNoPlanSCHParams.DATA_TYPE));
                line.setValue(KEY_HIDDEN_LOG_FLAG, outparam.get(ReportDBDataNoPlanSCHParams.LOG_FLAG));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(ReportDBDataNoPlanSCHParams.CLASS_NAME));
                line.setValue(KEY_LST_SELECT, outparam.get(ReportDBDataNoPlanSCHParams.SELECT));
                line.setValue(KEY_LST_REPORT_DATA_TYPE, outparam.get(ReportDBDataNoPlanSCHParams.REPORT_DATA_TYPE));
                line.setValue(KEY_LST_MESSAGE, outparam.get(ReportDBDataNoPlanSCHParams.MESSAGE));
                lst_NoPlanReport_SetLineToolTip(line);
                _lcm_lst_NoPlanReport.add(line);
            }

            // reset editing row.
            _lcm_lst_NoPlanReport.resetEditRow();
            _lcm_lst_NoPlanReport.resetHighlight();

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
        Connection customerConn = null;
        
        ReportDBDataNoPlanSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            customerConn = WmsParam.getCustomerConnection(); 
            sch = new ReportDBDataNoPlanSCH(conn, customerConn, this.getClass(), locale, ui);

            // set input parameters.
            ReportDBDataNoPlanSCHParams inparam = new ReportDBDataNoPlanSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_NoPlanReport.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_NoPlanReport.getNewLine();
                line.setValue(KEY_HIDDEN_DATA_TYPE, outparam.get(ReportDBDataNoPlanSCHParams.DATA_TYPE));
                line.setValue(KEY_HIDDEN_LOG_FLAG, outparam.get(ReportDBDataNoPlanSCHParams.LOG_FLAG));
                line.setValue(KEY_HIDDEN_CLASS_NAME, outparam.get(ReportDBDataNoPlanSCHParams.CLASS_NAME));
                line.setValue(KEY_LST_REPORT_DATA_TYPE, outparam.get(ReportDBDataNoPlanSCHParams.REPORT_DATA_TYPE));
                line.setValue(KEY_LST_MESSAGE, outparam.get(ReportDBDataNoPlanSCHParams.MESSAGE));
                lst_NoPlanReport_SetLineToolTip(line);
                _lcm_lst_NoPlanReport.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_NoPlanReport.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_NoPlanReport.get(i);
                lst_NoPlanReport.setCurrentRow(i);
                clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
                lst_NoPlanReport_SetLineToolTip(clearLine);
                _lcm_lst_NoPlanReport.set(i, clearLine);
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
