// $Id: ReportDataBatchBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.system.display.reportdatabatch;

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
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
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
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.pcart.system.schedule.ReportDataBatchSCH;
import jp.co.daifuku.pcart.system.schedule.ReportDataBatchSCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * 予定作業報告(バッチ単位)の画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class ReportDataBatchBusiness
        extends ReportDataBatch
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ReportData(LST_REPORT) */
    private static final ListCellKey KEY_LST_REPORT = new ListCellKey("LST_REPORT", new StringCellColumn(), true, false);

    /** lst_ReportData(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_ReportData(LST_BATCH_SEQ_NO) */
    private static final ListCellKey KEY_LST_BATCH_SEQ_NO = new ListCellKey("LST_BATCH_SEQ_NO", new StringCellColumn(), true, false);

    /** lst_ReportData(LST_BATCH_COMPLETE_DATE) */
    private static final ListCellKey KEY_LST_BATCH_COMPLETE_DATE = new ListCellKey("LST_BATCH_COMPLETE_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_ReportData keys */
    private static final ListCellKey[] LST_REPORTDATA_KEYS = {
        KEY_LST_REPORT,
        KEY_LST_BATCH_NO,
        KEY_LST_BATCH_SEQ_NO,
        KEY_LST_BATCH_COMPLETE_DATE,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_ReportData */
    private ListCellModel _lcm_lst_ReportData;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public ReportDataBatchBusiness()
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
    public void lst_ReportData_Click(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        lst_ReportData_Click_Process();
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_ReDisplay_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_ReDisplay_Click_Process();
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

        // initialize lst_ReportData.
        _lcm_lst_ReportData = new ListCellModel(lst_ReportData, LST_REPORTDATA_KEYS, locale);
        _lcm_lst_ReportData.setToolTipVisible(KEY_LST_REPORT, false);
        _lcm_lst_ReportData.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_ReportData.setToolTipVisible(KEY_LST_BATCH_SEQ_NO, false);
        _lcm_lst_ReportData.setToolTipVisible(KEY_LST_BATCH_COMPLETE_DATE, false);

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
    private void lst_ReportData_SetLineToolTip(ListCellLine line)
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
        ReportDataBatchSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReportDataBatchSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ReportDataBatchSCHParams inparam = new ReportDataBatchSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // DFKLOOK:ここから修正
            if (outparams.size() == 0 || outparams == null)
            {
                lst_ReportData.clearRow();
                // 対象データはありませんでした。
                message.setMsgResourceKey(WmsMessageFormatter.format(6003011));

                return;
            }
            // DFKLOOK:ここまで修正

            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ReportData.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReportData.getNewLine();
                line.setValue(KEY_LST_BATCH_NO, outparam.get(ReportDataBatchSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(ReportDataBatchSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_BATCH_COMPLETE_DATE, outparam.get(ReportDataBatchSCHParams.BATCH_COMPLETE_DATE));
                lst_ReportData_SetLineToolTip(line);
                _lcm_lst_ReportData.add(line);
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
    private void btn_ReDisplay_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReportDataBatchSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReportDataBatchSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            ReportDataBatchSCHParams inparam = new ReportDataBatchSCHParams();

            // SCH call.
            List<Params> outparams = sch.query(inparam);

            // DFKLOOK:ここから修正
            if (outparams.size() == 0 || outparams == null)
            {
                lst_ReportData.clearRow();
                // 対象データはありませんでした。
                message.setMsgResourceKey(WmsMessageFormatter.format(6003011));

                return;
            }
            // DFKLOOK:ここまで修正

            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ReportData.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ReportData.getNewLine();
                line.setValue(KEY_LST_BATCH_NO, outparam.get(ReportDataBatchSCHParams.BATCH_NO));
                line.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(ReportDataBatchSCHParams.BATCH_SEQ_NO));
                line.setValue(KEY_LST_BATCH_COMPLETE_DATE, outparam.get(ReportDataBatchSCHParams.BATCH_COMPLETE_DATE));
                lst_ReportData_SetLineToolTip(line);
                _lcm_lst_ReportData.add(line);
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

    // DFKLOOK:ここから修正
    /**
     *
     * @throws Exception
     */
    private void lst_ReportData_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        ReportDataBatchSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new ReportDataBatchSCH(conn, this.getClass(), locale, ui);

            // get active row.
            int row = lst_ReportData.getActiveRow();
            lst_ReportData.setCurrentRow(row);
            ListCellLine line = _lcm_lst_ReportData.get(row);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();

            ReportDataBatchSCHParams lineparam = new ReportDataBatchSCHParams();
            lineparam.setRowIndex(row);
            lineparam.set(ReportDataBatchSCHParams.BATCH_NO, line.getValue(KEY_LST_BATCH_NO));
            lineparam.set(ReportDataBatchSCHParams.BATCH_SEQ_NO, line.getValue(KEY_LST_BATCH_SEQ_NO));
            inparamList.add(lineparam);

            ScheduleParams[] inparams = new ScheduleParams[inparamList.size()];
            inparamList.toArray(inparams);

            for (ScheduleParams inparam : inparams)
            {
                if (!sch.check(inparam))
                {
                    // rollback.
                    conn.rollback();
                    message.setMsgResourceKey(sch.getMessage());

                    // reset editing row or highligiting error row.
                    _lcm_lst_ReportData.resetEditRow();
                    _lcm_lst_ReportData.resetHighlight();
                    _lcm_lst_ReportData.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);

                    return;
                }
            }

            // SCH call.
            List<Params> outparams = sch.startSCHgetParams(inparams);

            if (outparams == null)
            {
                // rollback.
                conn.rollback();
                message.setMsgResourceKey(sch.getMessage());

                return;
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_ReportData.clear();
            for (Params outparam : outparams)
            {
                ListCellLine newline = _lcm_lst_ReportData.getNewLine();
                newline.setValue(KEY_LST_BATCH_NO, outparam.get(ReportDataBatchSCHParams.BATCH_NO));
                newline.setValue(KEY_LST_BATCH_SEQ_NO, outparam.get(ReportDataBatchSCHParams.BATCH_SEQ_NO));
                newline.setValue(KEY_LST_BATCH_COMPLETE_DATE,
                        outparam.get(ReportDataBatchSCHParams.BATCH_COMPLETE_DATE));
                lst_ReportData_SetLineToolTip(newline);
                _lcm_lst_ReportData.add(newline);
            }

            // reset editing row.
            _lcm_lst_ReportData.resetEditRow();
            _lcm_lst_ReportData.resetHighlight();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            if (sch != null && !StringUtil.isBlank(sch.getMessage()))
            {
                message.setMsgResourceKey(sch.getMessage());
            }
        }
        finally
        {
            DBUtil.close(conn);
        }
    }

    // DFKLOOK:ここまで修正

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
