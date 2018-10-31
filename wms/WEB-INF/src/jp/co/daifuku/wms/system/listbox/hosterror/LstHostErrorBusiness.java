// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.system.listbox.hosterror;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.exporter.FaDataLoadCheckListParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.system.dasch.LstHostErrorDASCH;
import jp.co.daifuku.wms.system.dasch.LstHostErrorDASCHParams;
import jp.co.daifuku.wms.system.listbox.hosterror.LstHostError;
import jp.co.daifuku.wms.system.listbox.hosterror.LstHostErrorParams;
import jp.co.daifuku.wms.system.listbox.hosterror.ViewStateKeys;

/**
 * 異常履歴一覧の画面処理を行います。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
@SuppressWarnings("serial")
public class LstHostErrorBusiness
        extends LstHostError
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH_LIST = "_KEY_DASCH_LIST";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_HostError(LST_LOAD_TYPE) */
    private static final ListCellKey KEY_LST_LOAD_TYPE = new ListCellKey("LST_LOAD_TYPE", new StringCellColumn(), false, false);

    /** lst_HostError(LST_FILE_NAME) */
    private static final ListCellKey KEY_LST_FILE_NAME = new ListCellKey("LST_FILE_NAME", new StringCellColumn(), false, false);

    /** lst_HostError(LST_START_DAY) */
    private static final ListCellKey KEY_LST_START_DAY = new ListCellKey("LST_START_DAY", new StringCellColumn(), false, false);

    /** lst_HostError(LST_START_TIME) */
    private static final ListCellKey KEY_LST_START_TIME = new ListCellKey("LST_START_TIME", new StringCellColumn(), false, false);

    /** lst_HostError(HIDDEN_ERROR_LEVEL) */
    private static final ListCellKey KEY_HIDDEN_ERROR_LEVEL = new ListCellKey("HIDDEN_ERROR_LEVEL", new StringCellColumn(), false, false);

    /** lst_HostError(HIDDEN_ERROR_DETAILS) */
    private static final ListCellKey KEY_HIDDEN_ERROR_DETAILS = new ListCellKey("HIDDEN_ERROR_DETAILS", new StringCellColumn(), false, false);

    /** lst_HostError(HIDDEN_STATUS) */
    private static final ListCellKey KEY_HIDDEN_STATUS = new ListCellKey("HIDDEN_STATUS", new StringCellColumn(), false, false);

    /** lst_HostError(LST_FILE_LINE_NO) */
    private static final ListCellKey KEY_LST_FILE_LINE_NO = new ListCellKey("LST_FILE_LINE_NO", new NumberCellColumn(0), true, false);

    /** lst_HostError(LST_ERROR_LEVEL) */
    private static final ListCellKey KEY_LST_ERROR_LEVEL = new ListCellKey("LST_ERROR_LEVEL", new StringCellColumn(), true, false);

    /** lst_HostError(LST_ERROR_DETAILS) */
    private static final ListCellKey KEY_LST_ERROR_DETAILS = new ListCellKey("LST_ERROR_DETAILS", new StringCellColumn(), true, false);

    /** lst_HostError(LST_ITEM_NO) */
    private static final ListCellKey KEY_LST_ITEM_NO = new ListCellKey("LST_ITEM_NO", new StringCellColumn(), true, false);

    /** lst_HostError(LST_DATA) */
    private static final ListCellKey KEY_LST_DATA = new ListCellKey("LST_DATA", new StringCellColumn(), true, false);

    /** lst_HostError keys */
    private static final ListCellKey[] LST_HOSTERROR_KEYS = {
        KEY_LST_LOAD_TYPE,
        KEY_LST_FILE_NAME,
        KEY_LST_START_DAY,
        KEY_LST_START_TIME,
        KEY_HIDDEN_ERROR_LEVEL,
        KEY_HIDDEN_ERROR_DETAILS,
        KEY_HIDDEN_STATUS,
        KEY_LST_FILE_LINE_NO,
        KEY_LST_ERROR_LEVEL,
        KEY_LST_ERROR_DETAILS,
        KEY_LST_ITEM_NO,
        KEY_LST_DATA,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_HostError */
    private ListCellModel _lcm_lst_HostError;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstHostErrorBusiness()
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
    public void Pager_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void Pager_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void Pager_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        pager_SetPage();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void Pager_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        pager_SetPage();
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
    public void btn_Print_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Print_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_Click_Process();
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

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{Pager}, locale);

        // initialize lst_HostError.
        _lcm_lst_HostError = new ListCellModel(lst_HostError, LST_HOSTERROR_KEYS, locale);
        _lcm_lst_HostError.setToolTipVisible(KEY_LST_FILE_LINE_NO, true);
        _lcm_lst_HostError.setToolTipVisible(KEY_LST_ERROR_LEVEL, true);
        _lcm_lst_HostError.setToolTipVisible(KEY_LST_ERROR_DETAILS, true);
        _lcm_lst_HostError.setToolTipVisible(KEY_LST_ITEM_NO, true);
        _lcm_lst_HostError.setToolTipVisible(KEY_LST_DATA, true);

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
        // dispose DASCH.
        disposeDasch();
    }

    /**
     *
     * @throws Exception
     */
    private void disposeDasch()
            throws Exception
    {
        // disposing DASCH.
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH_LIST);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH_LIST);
            dasch.close();
            DBUtil.close(dasch.getConnection());
        }
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_HostError_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W1325", KEY_LST_FILE_LINE_NO);
        line.addToolTip("LBL-W1328", KEY_LST_DATA);
    }

    /**
     *
     * @throws Exception
     */
    private void pager_SetPage()
            throws Exception
    {
        // get event source.
        String eventSource = viewState.getString(_KEY_PAGERSOURCE);
        if (eventSource == null)
        {
            return;
        }

        // choose process.
        if (eventSource.equals("page_Load"))
        {
            // process call.
            page_Load_SetList();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(Pager);

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
        LstHostErrorDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstHostErrorDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstHostErrorDASCHParams inparam = new LstHostErrorDASCHParams();
            LstHostErrorParams requestParam = new LstHostErrorParams(request);
            inparam.set(LstHostErrorDASCHParams.FILE_NAME, requestParam.get(LstHostErrorParams.FILE_NAME));
            inparam.set(LstHostErrorDASCHParams.START_DATE, requestParam.get(LstHostErrorParams.START_DATE));
            // DFKLOOK start
            viewState.setObject(ViewStateKeys.STATUS, requestParam.get(LstHostErrorParams.STATUS));
            // DFKLOOK end

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_HostError.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                // DFKLOOK:ここから修正
                disabledButton();
                // DFKLOOK:ここまで修正
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count)
                        + "\t" + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // save session.
            session.setAttribute(_KEY_DASCH_LIST, dasch);
            isSuccess = true;

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_HostError.clear();
            // DFKLOOK:ここから修正
            disabledButton();
            // DFKLOOK:ここまで修正
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                page_Load_SetList();
            }
            else
            {
                if (dasch != null)
                {
                    dasch.close();
                }
                DBUtil.close(conn);
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_SetList()
            throws Exception
    {
        LstHostErrorDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstHostErrorDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_HostError.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_HostError.getNewLine();
                line.setValue(KEY_LST_FILE_LINE_NO, outparam.get(LstHostErrorDASCHParams.FILE_LINE_NO));
                line.setValue(KEY_LST_ERROR_LEVEL, outparam.get(LstHostErrorDASCHParams.ERROR_LEVEL));
                line.setValue(KEY_LST_ERROR_DETAILS, outparam.get(LstHostErrorDASCHParams.ERROR_DETAILS));
                line.setValue(KEY_LST_ITEM_NO, outparam.get(LstHostErrorDASCHParams.ITEM_NO));
                line.setValue(KEY_LST_DATA, outparam.get(LstHostErrorDASCHParams.DATA));
                line.setValue(KEY_LST_LOAD_TYPE, outparam.get(LstHostErrorDASCHParams.LOAD_TYPE));
                line.setValue(KEY_LST_FILE_NAME, outparam.get(LstHostErrorDASCHParams.FILE_NAME));
                line.setValue(KEY_LST_START_DAY, outparam.get(LstHostErrorDASCHParams.START_DAY));
                line.setValue(KEY_LST_START_TIME, outparam.get(LstHostErrorDASCHParams.START_TIME));
                line.setValue(KEY_HIDDEN_ERROR_LEVEL, outparam.get(LstHostErrorDASCHParams.HIDDEN_ERROR_LEVEL));
                line.setValue(KEY_HIDDEN_ERROR_DETAILS, outparam.get(LstHostErrorDASCHParams.HIDDEN_ERROR_DETAILS));
//                viewState.setObject(ViewStateKeys.STATUS, outparam.get(LstHostErrorDASCHParams.STATUS));
                lst_HostError_SetLineToolTip(line);
                _lcm_lst_HostError.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_HostError.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Preview_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPVExporter("FaDataLoadCheckList", getSession());
            File downloadFile = exporter.open();

            // DFKLOOK:ここから修正
            Date sysDay = DbDateUtil.getTimeStamp();
            Date sysTime = DbDateUtil.getTimeStamp();
            // DFKLOOK:ここまで修正

            // export.
            for (int i = 1; i <= _lcm_lst_HostError.size(); i++)
            {
                ListCellLine line = _lcm_lst_HostError.get(i);
                FaDataLoadCheckListParams expparam = new FaDataLoadCheckListParams();
                // DFKLOOK ここから修正
                expparam.set(FaDataLoadCheckListParams.DFK_DS_NO, null);
                expparam.set(FaDataLoadCheckListParams.DFK_USER_ID, null);
                expparam.set(FaDataLoadCheckListParams.DFK_USER_NAME, null);
                // DFKLOOK ここまで修正
                expparam.set(FaDataLoadCheckListParams.LOAD_TYPE, line.getValue(KEY_LST_LOAD_TYPE));
                // DFKLOOK ここから修正
                expparam.set(FaDataLoadCheckListParams.SYS_DAY, sysDay);
                expparam.set(FaDataLoadCheckListParams.SYS_TIME, sysTime);
                // DFKLOOK ここまで修正
                expparam.set(FaDataLoadCheckListParams.FILE_NAME, line.getValue(KEY_LST_FILE_NAME));
                expparam.set(FaDataLoadCheckListParams.START_DAY, line.getValue(KEY_LST_START_DAY));
                expparam.set(FaDataLoadCheckListParams.START_TIME, line.getValue(KEY_LST_START_TIME));
                expparam.set(FaDataLoadCheckListParams.FILE_LINE_NO, line.getValue(KEY_LST_FILE_LINE_NO));
                expparam.set(FaDataLoadCheckListParams.ERROR_LEVEL, line.getValue(KEY_LST_ERROR_LEVEL));
                expparam.set(FaDataLoadCheckListParams.ERROR_DETAIL, line.getValue(KEY_LST_ERROR_DETAILS));
                expparam.set(FaDataLoadCheckListParams.ITEM_NO, line.getValue(KEY_LST_ITEM_NO));
                expparam.set(FaDataLoadCheckListParams.DATA, line.getValue(KEY_LST_DATA));
                
                // DFKLOOK start
                expparam.set(FaDataLoadCheckListParams.STATUS, viewState.getString(ViewStateKeys.STATUS));
                // DFKLOOK end
                
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
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_HostError.size(); i++)
            {
                ListCellLine line = _lcm_lst_HostError.get(i);
                lst_HostError.setCurrentRow(i);

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_FILE_LINE_NO), "");
                part11List.add(line.getViewString(KEY_HIDDEN_ERROR_LEVEL), "");
                part11List.add(line.getViewString(KEY_HIDDEN_ERROR_DETAILS), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_NO), "");
                part11List.add(line.getViewString(KEY_LST_DATA), "");
                // DFKLOOK start
                // 開始行
                part11List.add(String.valueOf(Pager.getIndex()), "");
                // 終了行(開始行 + リストの行数 - 1)
                part11List.add(String.valueOf(Pager.getIndex() + _lcm_lst_HostError.size() - 1), "");
                // 最大行
                part11List.add(String.valueOf(Pager.getMax()), "");
                // DFKLOOK end
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PREVIEW_LIST), part11List);
            }

            // commit.
            conn.commit();

            // redirect.
            previewPDF(downloadFile.getPath());

            // DFKLOOK start
            setFocus(null);
            // DFKLOOK end
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
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
    private void btn_Print_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("FaDataLoadCheckList", false);
            exporter.open();

            // DFKLOOK:ここから修正
            Date sysDay = DbDateUtil.getTimeStamp();
            Date sysTime = DbDateUtil.getTimeStamp();
            // DFKLOOK:ここまで修正

            // export.
            for (int i = 1; i <= _lcm_lst_HostError.size(); i++)
            {
                ListCellLine line = _lcm_lst_HostError.get(i);
                FaDataLoadCheckListParams expparam = new FaDataLoadCheckListParams();
                // DFKLOOK ここから修正
                expparam.set(FaDataLoadCheckListParams.DFK_DS_NO, null);
                expparam.set(FaDataLoadCheckListParams.DFK_USER_ID, null);
                expparam.set(FaDataLoadCheckListParams.DFK_USER_NAME, null);
                // DFKLOOK ここまで修正
                expparam.set(FaDataLoadCheckListParams.LOAD_TYPE, line.getValue(KEY_LST_LOAD_TYPE));
                // DFKLOOK ここから修正
                expparam.set(FaDataLoadCheckListParams.SYS_DAY, sysDay);
                expparam.set(FaDataLoadCheckListParams.SYS_TIME, sysTime);
                // DFKLOOK ここまで修正
                expparam.set(FaDataLoadCheckListParams.FILE_NAME, line.getValue(KEY_LST_FILE_NAME));
                expparam.set(FaDataLoadCheckListParams.START_DAY, line.getValue(KEY_LST_START_DAY));
                expparam.set(FaDataLoadCheckListParams.START_TIME, line.getValue(KEY_LST_START_TIME));
                expparam.set(FaDataLoadCheckListParams.FILE_LINE_NO, line.getValue(KEY_LST_FILE_LINE_NO));
                expparam.set(FaDataLoadCheckListParams.ERROR_LEVEL, line.getValue(KEY_LST_ERROR_LEVEL));
                expparam.set(FaDataLoadCheckListParams.ERROR_DETAIL, line.getValue(KEY_LST_ERROR_DETAILS));
                expparam.set(FaDataLoadCheckListParams.ITEM_NO, line.getValue(KEY_LST_ITEM_NO));
                expparam.set(FaDataLoadCheckListParams.DATA, line.getValue(KEY_LST_DATA));
                
                // DFKLOOK start
                expparam.set(FaDataLoadCheckListParams.STATUS, viewState.getString(ViewStateKeys.STATUS));
                // DFKLOOK end
                
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
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_HostError.size(); i++)
            {
                ListCellLine line = _lcm_lst_HostError.get(i);
                lst_HostError.setCurrentRow(i);

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_FILE_LINE_NO), "");
                part11List.add(line.getViewString(KEY_HIDDEN_ERROR_LEVEL), "");
                part11List.add(line.getViewString(KEY_HIDDEN_ERROR_DETAILS), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_NO), "");
                part11List.add(line.getViewString(KEY_LST_DATA), "");
                // DFKLOOK start
                // 開始行
                part11List.add(String.valueOf(Pager.getIndex()), "");
                // 終了行(開始行 + リストの行数 - 1)
                part11List.add(String.valueOf(Pager.getIndex() + _lcm_lst_HostError.size() - 1), "");
                // 最大行
                part11List.add(String.valueOf(Pager.getMax()), "");
                // DFKLOOK end
                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT_LIST), part11List);
            }

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
    private void btn_Close_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

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

    // DFKLOOK ここから修正
    /**
     * ボタン制御を行います。
     */
    private void disabledButton()
    {
        // プレビューボタン無効化
        btn_Preview.setEnabled(false);
        // 印刷ボタン無効化
        btn_Print.setEnabled(false);
    }
    // DFKLOOK ここまで修正

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
