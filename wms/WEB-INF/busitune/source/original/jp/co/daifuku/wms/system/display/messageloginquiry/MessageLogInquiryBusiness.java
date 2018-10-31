// $Id: MessageLogInquiryBusiness.java 7403 2010-03-05 12:51:08Z shibamoto $
package jp.co.daifuku.wms.system.display.messageloginquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.ControlColor;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.bluedog.webapp.ViewState;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.system.dasch.MessageLogInquiryDASCH;
import jp.co.daifuku.wms.system.dasch.MessageLogInquiryDASCHParams;
import jp.co.daifuku.wms.system.display.messageloginquiry.MessageLogInquiry;
import jp.co.daifuku.wms.system.exporter.ErrorLogListParams;
import jp.co.daifuku.wms.system.schedule.MessageLogInquirySCH;
import jp.co.daifuku.wms.system.schedule.MessageLogInquirySCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7403 $, $Date:: 2010-03-05 21:51:08 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class MessageLogInquiryBusiness
        extends MessageLogInquiry
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_Log(LST_LOG_DATE) */
    private static final ListCellKey KEY_LST_LOG_DATE = new ListCellKey("LST_LOG_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMSS), true, false);

    /** lst_Log(LST_CONTENT) */
    private static final ListCellKey KEY_LST_CONTENT = new ListCellKey("LST_CONTENT", new StringCellColumn(), true, false);

    /** lst_Log(LST_CLASS) */
    private static final ListCellKey KEY_LST_CLASS = new ListCellKey("LST_CLASS", new StringCellColumn(), true, false);

    /** lst_Log(LST_MESSAGE) */
    private static final ListCellKey KEY_LST_MESSAGE = new ListCellKey("LST_MESSAGE", new StringCellColumn(), true, false);

    /** lst_Log keys */
    private static final ListCellKey[] LST_LOG_KEYS = {
        KEY_LST_LOG_DATE,
        KEY_LST_CONTENT,
        KEY_LST_CLASS,
        KEY_LST_MESSAGE,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** RadioButtonGroupModel Log */
    private RadioButtonGroup _grp_Log;

    /** ListCellModel lst_Log */
    private ScrollListCellModel _lcm_lst_Log;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public MessageLogInquiryBusiness()
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

        // process call.
        page_Initialize_Process();
    }

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
        if (eventSource.equals("btn_Print_Click"))
        {
            // process call.
            btn_Print_Click_Process(false);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Display_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Display_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Print_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Print_Click_Process(true);
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Preview_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Preview_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_XLS_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_XLS_Click_Process();
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

        // initialize Log.
        _grp_Log = new RadioButtonGroup(new RadioButton[]{rdo_Log_All, rdo_Log_Information, rdo_Log_Caution, rdo_Log_Warning, rdo_Log_Error}, locale);

        // initialize lst_Log.
        _lcm_lst_Log = new ScrollListCellModel(lst_Log, LST_LOG_KEYS, locale);
        _lcm_lst_Log.setToolTipVisible(KEY_LST_LOG_DATE, true);
        _lcm_lst_Log.setToolTipVisible(KEY_LST_CONTENT, true);
        _lcm_lst_Log.setToolTipVisible(KEY_LST_CLASS, true);
        _lcm_lst_Log.setToolTipVisible(KEY_LST_MESSAGE, true);
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
    private void lst_Log_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0149", KEY_LST_LOG_DATE);
        line.addToolTip("LBL-W0148", KEY_LST_CONTENT);
        line.addToolTip("LBL-W0162", KEY_LST_CLASS);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(txt_FromSearchDate);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        rdo_Log_All.setChecked(true);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // input validation.
        txt_FromSearchDate.validate(this, false);
        txt_FromSearchTime.validate(this, false);
        txt_ToSearchDate.validate(this, false);
        txt_ToSearchTime.validate(this, false);
        rdo_Log_All.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        MessageLogInquirySCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new MessageLogInquirySCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            MessageLogInquirySCHParams inparam = new MessageLogInquirySCHParams();
            inparam.set(MessageLogInquirySCHParams.SEARCH_START_DAY, txt_FromSearchDate.getValue());
            inparam.set(MessageLogInquirySCHParams.SEARCH_START_TIME, txt_FromSearchTime.getValue());
            inparam.set(MessageLogInquirySCHParams.SEARCH_END_DAY, txt_ToSearchDate.getValue());
            inparam.set(MessageLogInquirySCHParams.SEARCH_END_TIME, txt_ToSearchTime.getValue());
            inparam.set(MessageLogInquirySCHParams.LOG, _grp_Log.getSelectedValue());

            // SCH call.
            List<Params> outparams = sch.query(inparam);
            message.setMsgResourceKey(sch.getMessage());

            // output display.
            _lcm_lst_Log.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_Log.getNewLine();
                line.setValue(KEY_LST_LOG_DATE, outparam.get(MessageLogInquirySCHParams.LOG_DATE));
                line.setValue(KEY_LST_CONTENT, outparam.get(MessageLogInquirySCHParams.CONTENT));
                line.setValue(KEY_LST_CLASS, outparam.get(MessageLogInquirySCHParams.CLASS));
                line.setValue(KEY_LST_MESSAGE, outparam.get(MessageLogInquirySCHParams.MESSAGE));
                lst_Log_SetLineToolTip(line);
                _lcm_lst_Log.add(line);
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
     * @param confirm
     * @throws Exception
     */
    private void btn_Print_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_FromSearchDate.validate(this, false);
        txt_FromSearchTime.validate(this, false);
        txt_ToSearchDate.validate(this, false);
        txt_ToSearchTime.validate(this, false);
        rdo_Log_All.validate(true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        MessageLogInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new MessageLogInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            MessageLogInquiryDASCHParams inparam = new MessageLogInquiryDASCHParams();
            inparam.set(MessageLogInquiryDASCHParams.FROM_SEARCH_DAY, txt_FromSearchDate.getValue());
            inparam.set(MessageLogInquiryDASCHParams.FROM_SEARCH_TIME, txt_FromSearchTime.getValue());
            inparam.set(MessageLogInquiryDASCHParams.TO_SEARCH_DAY, txt_ToSearchDate.getValue());
            inparam.set(MessageLogInquiryDASCHParams.TO_SEARCH_TIME, txt_ToSearchTime.getValue());
            inparam.set(MessageLogInquiryDASCHParams.LOG, _grp_Log.getSelectedValue());

            // check count.
            int count = dasch.count(inparam);
            if (confirm && count > 0)
            {
                // show confirm message.
                this.setConfirm("MSG-W0018\t" + Formatter.getNumFormat(count), false, true);
                viewState.setString(_KEY_CONFIRMSOURCE, "btn_Print_Click");
                return;
            }
            else if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("ErrorLogList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ErrorLogListParams expparam = new ErrorLogListParams();
                expparam.set(ErrorLogListParams.DFK_DS_NO, outparam.get(MessageLogInquiryDASCHParams.DFK_DS_NO));
                expparam.set(ErrorLogListParams.DFK_USER_ID, outparam.get(MessageLogInquiryDASCHParams.DFK_USER_ID));
                expparam.set(ErrorLogListParams.DFK_USER_NAME, outparam.get(MessageLogInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(ErrorLogListParams.SYS_DAY, outparam.get(MessageLogInquiryDASCHParams.SYS_DAY));
                expparam.set(ErrorLogListParams.SYS_TIME, outparam.get(MessageLogInquiryDASCHParams.SYS_TIME));
                expparam.set(ErrorLogListParams.FROM_SEARCH_DAY, txt_FromSearchDate.getValue());
                expparam.set(ErrorLogListParams.FROM_SEARCH_TIME, txt_FromSearchTime.getValue());
                expparam.set(ErrorLogListParams.TO_SEARCH_DAY, txt_ToSearchDate.getValue());
                expparam.set(ErrorLogListParams.TO_SEARCH_TIME, txt_ToSearchTime.getValue());
                expparam.set(ErrorLogListParams.NO, outparam.get(MessageLogInquiryDASCHParams.NO));
                expparam.set(ErrorLogListParams.DATE, outparam.get(MessageLogInquiryDASCHParams.DATE));
                expparam.set(ErrorLogListParams.MESSAGE_NO, outparam.get(MessageLogInquiryDASCHParams.MESSAGE_NO));
                expparam.set(ErrorLogListParams.LEVEL, outparam.get(MessageLogInquiryDASCHParams.LEVEL));
                expparam.set(ErrorLogListParams.CLASS_NAME, outparam.get(MessageLogInquiryDASCHParams.CLASS_NAME));
                expparam.set(ErrorLogListParams.MESSAGE, outparam.get(MessageLogInquiryDASCHParams.MESSAGE));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
                message.setMsgResourceKey("6001010");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_FromSearchDate.getStringValue(), txt_FromSearchTime.getStringValue(), "");
            part11List.add(txt_ToSearchDate.getStringValue(), txt_ToSearchTime.getStringValue(), "");
            part11List.add(SystemInParameter.DISPLAY_CONDITION_ALL, "", rdo_Log_All.getChecked());
            part11List.add(SystemInParameter.DISPLAY_CONDITION_INFORMATION, "", rdo_Log_Information.getChecked());
            part11List.add(SystemInParameter.DISPLAY_CONDITION_ATTENTION, "", rdo_Log_Caution.getChecked());
            part11List.add(SystemInParameter.DISPLAY_CONDITION_WARNING, "", rdo_Log_Warning.getChecked());
            part11List.add(SystemInParameter.DISPLAY_CONDITION_ERROR, "", rdo_Log_Error.getChecked());
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
            // commit.
            conn.commit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Preview_Click_Process()
            throws Exception
    {
        // input validation.
        txt_FromSearchDate.validate(this, false);
        txt_FromSearchTime.validate(this, false);
        txt_ToSearchDate.validate(this, false);
        txt_ToSearchTime.validate(this, false);
        rdo_Log_All.validate(true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        MessageLogInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new MessageLogInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            MessageLogInquiryDASCHParams inparam = new MessageLogInquiryDASCHParams();
            inparam.set(MessageLogInquiryDASCHParams.FROM_START_DAY, txt_FromSearchDate.getValue());
            inparam.set(MessageLogInquiryDASCHParams.FROM_SEARCH_TIME, txt_FromSearchTime.getValue());
            inparam.set(MessageLogInquiryDASCHParams.TO_SEARCH_DAY, txt_ToSearchDate.getValue());
            inparam.set(MessageLogInquiryDASCHParams.TO_SEARCH_TIME, txt_ToSearchTime.getValue());
            inparam.set(MessageLogInquiryDASCHParams.LOG, _grp_Log.getSelectedValue());

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPVExporter("ErrorLogList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ErrorLogListParams expparam = new ErrorLogListParams();
                expparam.set(ErrorLogListParams.DFK_DS_NO, outparam.get(MessageLogInquiryDASCHParams.DFK_DS_NO));
                expparam.set(ErrorLogListParams.DFK_USER_ID, outparam.get(MessageLogInquiryDASCHParams.DFK_USER_ID));
                expparam.set(ErrorLogListParams.DFK_USER_NAME, outparam.get(MessageLogInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(ErrorLogListParams.SYS_DAY, outparam.get(MessageLogInquiryDASCHParams.SYS_DAY));
                expparam.set(ErrorLogListParams.SYS_TIME, outparam.get(MessageLogInquiryDASCHParams.SYS_TIME));
                expparam.set(ErrorLogListParams.FROM_SEARCH_DAY, txt_FromSearchDate.getValue());
                expparam.set(ErrorLogListParams.FROM_SEARCH_TIME, txt_FromSearchTime.getValue());
                expparam.set(ErrorLogListParams.TO_SEARCH_DAY, txt_ToSearchDate.getValue());
                expparam.set(ErrorLogListParams.TO_SEARCH_TIME, txt_ToSearchTime.getValue());
                expparam.set(ErrorLogListParams.NO, outparam.get(MessageLogInquiryDASCHParams.NO));
                expparam.set(ErrorLogListParams.DATE, outparam.get(MessageLogInquiryDASCHParams.DATE));
                expparam.set(ErrorLogListParams.MESSAGE_NO, outparam.get(MessageLogInquiryDASCHParams.MESSAGE_NO));
                expparam.set(ErrorLogListParams.LEVEL, outparam.get(MessageLogInquiryDASCHParams.LEVEL));
                expparam.set(ErrorLogListParams.CLASS_NAME, outparam.get(MessageLogInquiryDASCHParams.CLASS_NAME));
                expparam.set(ErrorLogListParams.MESSAGE, outparam.get(MessageLogInquiryDASCHParams.MESSAGE));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                downloadFile = exporter.print();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                message.setMsgResourceKey("6007034");
                return;
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_FromSearchDate.getStringValue(), txt_FromSearchTime.getStringValue(), "");
            part11List.add(txt_ToSearchDate.getStringValue(), txt_ToSearchTime.getStringValue(), "");
            part11List.add(SystemInParameter.DISPLAY_CONDITION_ALL, "", rdo_Log_All.getChecked());
            part11List.add(SystemInParameter.DISPLAY_CONDITION_INFORMATION, "", rdo_Log_Information.getChecked());
            part11List.add(SystemInParameter.DISPLAY_CONDITION_ATTENTION, "", rdo_Log_Caution.getChecked());
            part11List.add(SystemInParameter.DISPLAY_CONDITION_WARNING, "", rdo_Log_Warning.getChecked());
            part11List.add(SystemInParameter.DISPLAY_CONDITION_ERROR, "", rdo_Log_Error.getChecked());
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PREVIEW), part11List);
            // commit.
            conn.commit();

            // redirect.
            previewPDF(downloadFile.getPath());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_XLS_Click_Process()
            throws Exception
    {
        // input validation.
        txt_FromSearchDate.validate(this, false);
        txt_FromSearchTime.validate(this, false);
        txt_ToSearchDate.validate(this, false);
        txt_ToSearchTime.validate(this, false);
        rdo_Log_All.validate(true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        MessageLogInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new MessageLogInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            MessageLogInquiryDASCHParams inparam = new MessageLogInquiryDASCHParams();
            inparam.set(MessageLogInquiryDASCHParams.FROM_SEARCH_DAY, txt_FromSearchDate.getValue());
            inparam.set(MessageLogInquiryDASCHParams.FROM_SEARCH_TIME, txt_FromSearchTime.getValue());
            inparam.set(MessageLogInquiryDASCHParams.TO_SEARCH_DAY, txt_ToSearchDate.getValue());
            inparam.set(MessageLogInquiryDASCHParams.TO_SEARCH_TIME, txt_ToSearchTime.getValue());
            inparam.set(MessageLogInquiryDASCHParams.LOG, _grp_Log.getSelectedValue());

            // check count.
            int count = dasch.count(inparam);
            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("ErrorLogList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ErrorLogListParams expparam = new ErrorLogListParams();
                expparam.set(ErrorLogListParams.NO, outparam.get(MessageLogInquiryDASCHParams.NO));
                expparam.set(ErrorLogListParams.DATE, outparam.get(MessageLogInquiryDASCHParams.DATE));
                expparam.set(ErrorLogListParams.MESSAGE_NO, outparam.get(MessageLogInquiryDASCHParams.MESSAGE_NO));
                expparam.set(ErrorLogListParams.LEVEL, outparam.get(MessageLogInquiryDASCHParams.LEVEL));
                expparam.set(ErrorLogListParams.CLASS_NAME, outparam.get(MessageLogInquiryDASCHParams.CLASS_NAME));
                expparam.set(ErrorLogListParams.MESSAGE, outparam.get(MessageLogInquiryDASCHParams.MESSAGE));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_FromSearchDate.getStringValue(), txt_FromSearchTime.getStringValue(), "");
            part11List.add(txt_ToSearchDate.getStringValue(), txt_ToSearchTime.getStringValue(), "");
            part11List.add(SystemInParameter.DISPLAY_CONDITION_ALL, "", rdo_Log_All.getChecked());
            part11List.add(SystemInParameter.DISPLAY_CONDITION_INFORMATION, "", rdo_Log_Information.getChecked());
            part11List.add(SystemInParameter.DISPLAY_CONDITION_ATTENTION, "", rdo_Log_Caution.getChecked());
            part11List.add(SystemInParameter.DISPLAY_CONDITION_WARNING, "", rdo_Log_Warning.getChecked());
            part11List.add(SystemInParameter.DISPLAY_CONDITION_ERROR, "", rdo_Log_Error.getChecked());
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_XLS), part11List);
            // commit.
            conn.commit();

            // redirect.
            download(downloadFile.getPath());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
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
        // clear.
        txt_FromSearchDate.setValue(null);
        txt_FromSearchTime.setValue(null);
        txt_ToSearchDate.setValue(null);
        txt_ToSearchTime.setValue(null);
        rdo_Log_All.setChecked(true);
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
