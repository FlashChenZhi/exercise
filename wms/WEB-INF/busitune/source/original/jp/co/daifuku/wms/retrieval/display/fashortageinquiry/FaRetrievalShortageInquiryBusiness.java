// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.retrieval.display.fashortageinquiry;

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
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
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
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
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
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalShortageInquiryDASCH;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalShortageInquiryDASCHParams;
import jp.co.daifuku.wms.retrieval.display.fashortageinquiry.FaRetrievalShortageInquiry;
import jp.co.daifuku.wms.retrieval.exporter.ShortageListParams;
import jp.co.daifuku.wms.retrieval.listbox.startdate.LstRetrievalStartDateParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaRetrievalShortageInquiryBusiness
        extends FaRetrievalShortageInquiry
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_FaShortageInquiry(LST_BATCH_NO) */
    private static final ListCellKey KEY_LST_BATCH_NO = new ListCellKey("LST_BATCH_NO", new StringCellColumn(), true, false);

    /** lst_FaShortageInquiry(LST_TICKET_NO) */
    private static final ListCellKey KEY_LST_TICKET_NO = new ListCellKey("LST_TICKET_NO", new StringCellColumn(), true, false);

    /** lst_FaShortageInquiry(LST_LINE_NO) */
    private static final ListCellKey KEY_LST_LINE_NO = new ListCellKey("LST_LINE_NO", new NumberCellColumn(0), true, false);

    /** lst_FaShortageInquiry(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaShortageInquiry(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_FaShortageInquiry(LST_PLAN_QTY) */
    private static final ListCellKey KEY_LST_PLAN_QTY = new ListCellKey("LST_PLAN_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaShortageInquiry(LST_SHORTAGE_QTY) */
    private static final ListCellKey KEY_LST_SHORTAGE_QTY = new ListCellKey("LST_SHORTAGE_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaShortageInquiry keys */
    private static final ListCellKey[] LST_FASHORTAGEINQUIRY_KEYS = {
        KEY_LST_BATCH_NO,
        KEY_LST_TICKET_NO,
        KEY_LST_LINE_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_PLAN_QTY,
        KEY_LST_SHORTAGE_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_FaShortageInquiry */
    private ScrollListCellModel _lcm_lst_FaShortageInquiry;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaRetrievalShortageInquiryBusiness()
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
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        // get event source.
        DialogParameters dialogParams = ((DialogEvent)e).getDialogParameters();
        String eventSource = dialogParams.getParameter(_KEY_POPUPSOURCE);
        if (StringUtil.isBlank(eventSource))
        {
            return;
        }

        // choose process.
        if (eventSource.equals("btn_RetrievalDateSearch_Click"))
        {
            // process call.
            btn_RetrievalDateSearch_Click_DlgBack(dialogParams);
        }
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
    public void btn_RetrievalDateSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_RetrievalDateSearch_Click_Process();
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
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_FaShortageInquiry_First(ActionEvent e)
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
    public void pgr_FaShortageInquiry_Prev(ActionEvent e)
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
    public void pgr_FaShortageInquiry_Next(ActionEvent e)
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
    public void pgr_FaShortageInquiry_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        pager_SetPage();
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
        _pager = new PagerModel(new Pager[]{pgr_FaShortageInquiry}, locale);

        // initialize lst_FaShortageInquiry.
        _lcm_lst_FaShortageInquiry = new ScrollListCellModel(lst_FaShortageInquiry, LST_FASHORTAGEINQUIRY_KEYS, locale);
        _lcm_lst_FaShortageInquiry.setToolTipVisible(KEY_LST_BATCH_NO, false);
        _lcm_lst_FaShortageInquiry.setToolTipVisible(KEY_LST_TICKET_NO, false);
        _lcm_lst_FaShortageInquiry.setToolTipVisible(KEY_LST_LINE_NO, false);
        _lcm_lst_FaShortageInquiry.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_FaShortageInquiry.setToolTipVisible(KEY_LST_ITEM_NAME, false);
        _lcm_lst_FaShortageInquiry.setToolTipVisible(KEY_LST_PLAN_QTY, false);
        _lcm_lst_FaShortageInquiry.setToolTipVisible(KEY_LST_SHORTAGE_QTY, false);

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
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH);
            dasch.close();
            DBUtil.close(dasch.getConnection());
        }
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_FaShortageInquiry_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
        if (eventSource.equals("btn_Display_Click"))
        {
            // process call.
            btn_Display_Click_SetList();
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
        setFocus(txt_SearchDate);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_RetrievalDateSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstRetrievalStartDateParams inparam = new LstRetrievalStartDateParams();

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_RetrievalDateSearch_Click");
        redirect("/retrieval/listbox/startdate/LstRetrievalStartDate.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_RetrievalDateSearch_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstRetrievalStartDateParams outparam = new LstRetrievalStartDateParams(dialogParams);

        // set focus.
        setFocus(txt_BatchNo);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaRetrievalShortageInquiryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaRetrievalShortageInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaRetrievalShortageInquiryDASCHParams inparam = new FaRetrievalShortageInquiryDASCHParams();
            inparam.set(FaRetrievalShortageInquiryDASCHParams.SETTING_DAY, txt_SearchDate.getValue());
            inparam.set(FaRetrievalShortageInquiryDASCHParams.SETTING_TIME, txt_SearchTime.getValue());
            inparam.set(FaRetrievalShortageInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(FaRetrievalShortageInquiryDASCHParams.FROM_TICKET_NO, txt_FromTicketNo.getValue());
            inparam.set(FaRetrievalShortageInquiryDASCHParams.TO_TICKET_NO, txt_ToTicketNo.getValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_FaShortageInquiry.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
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
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaShortageInquiry.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                btn_Display_Click_SetList();
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
    private void btn_Display_Click_SetList()
            throws Exception
    {
        FaRetrievalShortageInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaRetrievalShortageInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_FaShortageInquiry.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaShortageInquiry.getNewLine();
                line.setValue(KEY_LST_BATCH_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.BATCH_NO));
                line.setValue(KEY_LST_TICKET_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.TICKET_NO));
                line.setValue(KEY_LST_LINE_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.LINE_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaRetrievalShortageInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(FaRetrievalShortageInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(FaRetrievalShortageInquiryDASCHParams.PLAN_QTY));
                line.setValue(KEY_LST_SHORTAGE_QTY, outparam.get(FaRetrievalShortageInquiryDASCHParams.SHORTAGE_QTY));
                lst_FaShortageInquiry_SetLineToolTip(line);
                _lcm_lst_FaShortageInquiry.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaShortageInquiry.clear();
            disposeDasch();
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
        txt_SearchDate.validate(this, true);
        txt_SearchTime.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_FromTicketNo.validate(this, false);
        txt_ToTicketNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaRetrievalShortageInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaRetrievalShortageInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaRetrievalShortageInquiryDASCHParams inparam = new FaRetrievalShortageInquiryDASCHParams();
            inparam.set(FaRetrievalShortageInquiryDASCHParams.SETTING_DAY, txt_SearchDate.getValue());
            inparam.set(FaRetrievalShortageInquiryDASCHParams.SETTING_TIME, txt_SearchTime.getValue());
            inparam.set(FaRetrievalShortageInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(FaRetrievalShortageInquiryDASCHParams.FROM_TICKET_NO, txt_FromTicketNo.getValue());
            inparam.set(FaRetrievalShortageInquiryDASCHParams.TO_TICKET_NO, txt_ToTicketNo.getValue());

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
            exporter = factory.newPrinterExporter("ShortageList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ShortageListParams expparam = new ShortageListParams();
                expparam.set(ShortageListParams.DFK_DS_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.DFK_DS_NO));
                expparam.set(ShortageListParams.DFK_USER_ID, outparam.get(FaRetrievalShortageInquiryDASCHParams.DFK_USER_ID));
                expparam.set(ShortageListParams.DFK_USER_NAME, outparam.get(FaRetrievalShortageInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(ShortageListParams.SYS_DAY, outparam.get(FaRetrievalShortageInquiryDASCHParams.SYS_DAY));
                expparam.set(ShortageListParams.SYS_TIME, outparam.get(FaRetrievalShortageInquiryDASCHParams.SYS_TIME));
                expparam.set(ShortageListParams.SETTING_DAY, outparam.get(FaRetrievalShortageInquiryDASCHParams.SETTING_DAY));
                expparam.set(ShortageListParams.SETTING_TIME, outparam.get(FaRetrievalShortageInquiryDASCHParams.SETTING_TIME));
                expparam.set(ShortageListParams.BATCH_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.BATCH_NO));
                expparam.set(ShortageListParams.TICKET_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.TICKET_NO));
                expparam.set(ShortageListParams.LINE_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.LINE_NO));
                expparam.set(ShortageListParams.ITEM_CODE, outparam.get(FaRetrievalShortageInquiryDASCHParams.ITEM_CODE));
                expparam.set(ShortageListParams.ITEM_NAME, outparam.get(FaRetrievalShortageInquiryDASCHParams.ITEM_NAME));
                expparam.set(ShortageListParams.LOT_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.LOT_NO));
                expparam.set(ShortageListParams.PICKING_QTY, outparam.get(FaRetrievalShortageInquiryDASCHParams.PICKING_QTY));
                expparam.set(ShortageListParams.SHORTAGE_QTY, outparam.get(FaRetrievalShortageInquiryDASCHParams.SHORTAGE_QTY));
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
            part11List.add(txt_SearchDate.getStringValue(), txt_SearchTime.getStringValue(), "");
            part11List.add(txt_BatchNo.getStringValue(), "");
            part11List.add(txt_FromTicketNo.getStringValue(), "");
            part11List.add(txt_ToTicketNo.getStringValue(), "");
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
    private void btn_XLS_Click_Process()
            throws Exception
    {
        // input validation.
        txt_SearchDate.validate(this, true);
        txt_SearchTime.validate(this, true);
        txt_BatchNo.validate(this, false);
        txt_FromTicketNo.validate(this, false);
        txt_ToTicketNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaRetrievalShortageInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaRetrievalShortageInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaRetrievalShortageInquiryDASCHParams inparam = new FaRetrievalShortageInquiryDASCHParams();
            inparam.set(FaRetrievalShortageInquiryDASCHParams.SETTING_DAY, txt_SearchDate.getValue());
            inparam.set(FaRetrievalShortageInquiryDASCHParams.SETTING_TIME, txt_SearchTime.getValue());
            inparam.set(FaRetrievalShortageInquiryDASCHParams.BATCH_NO, txt_BatchNo.getValue());
            inparam.set(FaRetrievalShortageInquiryDASCHParams.FROM_TICKET_NO, txt_FromTicketNo.getValue());
            inparam.set(FaRetrievalShortageInquiryDASCHParams.TO_TICKET_NO, txt_ToTicketNo.getValue());

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
            exporter = factory.newExcelExporter("ShortageList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ShortageListParams expparam = new ShortageListParams();
                expparam.set(ShortageListParams.BATCH_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.BATCH_NO));
                expparam.set(ShortageListParams.TICKET_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.TICKET_NO));
                expparam.set(ShortageListParams.LINE_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.LINE_NO));
                expparam.set(ShortageListParams.ITEM_CODE, outparam.get(FaRetrievalShortageInquiryDASCHParams.ITEM_CODE));
                expparam.set(ShortageListParams.ITEM_NAME, outparam.get(FaRetrievalShortageInquiryDASCHParams.ITEM_NAME));
                expparam.set(ShortageListParams.LOT_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.LOT_NO));
                expparam.set(ShortageListParams.PICKING_QTY, outparam.get(FaRetrievalShortageInquiryDASCHParams.PICKING_QTY));
                expparam.set(ShortageListParams.SHORTAGE_QTY, outparam.get(FaRetrievalShortageInquiryDASCHParams.SHORTAGE_QTY));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(txt_SearchDate.getStringValue(), txt_SearchTime.getStringValue(), "");
            part11List.add(txt_BatchNo.getStringValue(), "");
            part11List.add(txt_FromTicketNo.getStringValue(), "");
            part11List.add(txt_ToTicketNo.getStringValue(), "");
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
        txt_BatchNo.setValue(null);
        txt_FromTicketNo.setValue(null);
        txt_SearchDate.setValue(null);
        txt_SearchTime.setValue(null);
        txt_ToTicketNo.setValue(null);

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
