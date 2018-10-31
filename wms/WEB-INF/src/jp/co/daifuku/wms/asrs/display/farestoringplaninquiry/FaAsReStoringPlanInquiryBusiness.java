// $Id: FaAsReStoringPlanInquiryBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.display.farestoringplaninquiry;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.Constants;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.FaAsReStoringPlanInquiryDASCH;
import jp.co.daifuku.wms.asrs.dasch.FaAsReStoringPlanInquiryDASCHParams;
import jp.co.daifuku.wms.asrs.display.farestoringplaninquiry.FaAsReStoringPlanInquiry;
import jp.co.daifuku.wms.asrs.exporter.ReStoringPlanInqListParams;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7996 $, $Date:: 2011-07-06 09:52:24 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
@SuppressWarnings("serial")
public class FaAsReStoringPlanInquiryBusiness
        extends FaAsReStoringPlanInquiry
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

    /** lst_FaAsReStoringPlanInquiry(HIDDEN_ITEM_NAME) */
    private static final ListCellKey KEY_HIDDEN_ITEM_NAME = new ListCellKey("HIDDEN_ITEM_NAME", new StringCellColumn(), false, false);

    /** lst_FaAsReStoringPlanInquiry(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_FaAsReStoringPlanInquiry(LST_STATION_NO) */
    private static final ListCellKey KEY_LST_STATION_NO = new ListCellKey("LST_STATION_NO", new StringCellColumn(), true, false);

    /** lst_FaAsReStoringPlanInquiry(LST_WORK_NO) */
    private static final ListCellKey KEY_LST_WORK_NO = new ListCellKey("LST_WORK_NO", new StringCellColumn(), true, false);

    /** lst_FaAsReStoringPlanInquiry(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaAsReStoringPlanInquiry(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_FaAsReStoringPlanInquiry(LST_STORAGE_DATE) */
    private static final ListCellKey KEY_LST_STORAGE_DATE = new ListCellKey("LST_STORAGE_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_FaAsReStoringPlanInquiry(LST_PLAN_QTY) */
    private static final ListCellKey KEY_LST_PLAN_QTY = new ListCellKey("LST_PLAN_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaAsReStoringPlanInquiry(LST_REMOVE_DATE) */
    private static final ListCellKey KEY_LST_REMOVE_DATE = new ListCellKey("LST_REMOVE_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_FaAsReStoringPlanInquiry keys */
    private static final ListCellKey[] LST_FAASRESTORINGPLANINQUIRY_KEYS = {
        KEY_HIDDEN_ITEM_NAME,
        KEY_LST_NO,
        KEY_LST_STATION_NO,
        KEY_LST_WORK_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_STORAGE_DATE,
        KEY_LST_PLAN_QTY,
        KEY_LST_REMOVE_DATE,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_FaAsReStoringPlanInquiry */
    private ListCellModel _lcm_lst_FaAsReStoringPlanInquiry;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaAsReStoringPlanInquiryBusiness()
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
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_First(ActionEvent e)
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
    public void pager_Prev(ActionEvent e)
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
    public void pager_Next(ActionEvent e)
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
    public void pager_Last(ActionEvent e)
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
    public void lst_FaAsReStoringPlanInquiry_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_FaAsReStoringPlanInquiry_ColumClick(ActionEvent e)
            throws Exception
    {
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_FaAsReStoringPlanInquiry.
        _lcm_lst_FaAsReStoringPlanInquiry = new ListCellModel(lst_FaAsReStoringPlanInquiry, LST_FAASRESTORINGPLANINQUIRY_KEYS, locale);
        _lcm_lst_FaAsReStoringPlanInquiry.setToolTipVisible(KEY_LST_NO, true);
        _lcm_lst_FaAsReStoringPlanInquiry.setToolTipVisible(KEY_LST_STATION_NO, true);
        _lcm_lst_FaAsReStoringPlanInquiry.setToolTipVisible(KEY_LST_WORK_NO, true);
        _lcm_lst_FaAsReStoringPlanInquiry.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_FaAsReStoringPlanInquiry.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_FaAsReStoringPlanInquiry.setToolTipVisible(KEY_LST_STORAGE_DATE, true);
        _lcm_lst_FaAsReStoringPlanInquiry.setToolTipVisible(KEY_LST_PLAN_QTY, true);
        _lcm_lst_FaAsReStoringPlanInquiry.setToolTipVisible(KEY_LST_REMOVE_DATE, true);

    }

    /**
     *
     * @throws Exception
     */
    private void initializePulldownModels()
            throws Exception
    {
        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.RESTORING_MNT, Distribution.ALL);

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
    @SuppressWarnings("all")
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
    private void lst_FaAsReStoringPlanInquiry_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        // DFKLOOK start
        line.addToolTip("LBL-W0130", KEY_HIDDEN_ITEM_NAME);
        // DFKLOOK end
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
        setFocus(pul_Station);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // DFKLOOK start
        pul_Station.validate(this, true);
        txt_WorkNo.validate(this, false);
        // DFKLOOK end
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaAsReStoringPlanInquiryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaAsReStoringPlanInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaAsReStoringPlanInquiryDASCHParams inparam = new FaAsReStoringPlanInquiryDASCHParams();
            inparam.set(FaAsReStoringPlanInquiryDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaAsReStoringPlanInquiryDASCHParams.WORK_NO, txt_WorkNo.getValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_FaAsReStoringPlanInquiry.clear();

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
            _lcm_lst_FaAsReStoringPlanInquiry.clear();
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
        FaAsReStoringPlanInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaAsReStoringPlanInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_FaAsReStoringPlanInquiry.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaAsReStoringPlanInquiry.getNewLine();
                line.setValue(KEY_HIDDEN_ITEM_NAME, outparam.get(FaAsReStoringPlanInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.NO));
                line.setValue(KEY_LST_STATION_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.STATION_NO));
                line.setValue(KEY_LST_WORK_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.WORK_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaAsReStoringPlanInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.LOT_NO));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(FaAsReStoringPlanInquiryDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_PLAN_QTY, outparam.get(FaAsReStoringPlanInquiryDASCHParams.PLAN_QTY));
                line.setValue(KEY_LST_REMOVE_DATE, outparam.get(FaAsReStoringPlanInquiryDASCHParams.REMOVE_DATE));
                lst_FaAsReStoringPlanInquiry_SetLineToolTip(line);
                _lcm_lst_FaAsReStoringPlanInquiry.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaAsReStoringPlanInquiry.clear();
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
        pul_Station.validate(this, true);
        txt_WorkNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaAsReStoringPlanInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaAsReStoringPlanInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaAsReStoringPlanInquiryDASCHParams inparam = new FaAsReStoringPlanInquiryDASCHParams();
            inparam.set(FaAsReStoringPlanInquiryDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaAsReStoringPlanInquiryDASCHParams.WORK_NO, txt_WorkNo.getValue());

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
            exporter = factory.newPrinterExporter("ReStoringPlanInqList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ReStoringPlanInqListParams expparam = new ReStoringPlanInqListParams();
                expparam.set(ReStoringPlanInqListParams.DFK_DS_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.DFK_DS_NO));
                expparam.set(ReStoringPlanInqListParams.DFK_USER_ID, outparam.get(FaAsReStoringPlanInquiryDASCHParams.DFK_USER_ID));
                expparam.set(ReStoringPlanInqListParams.DFK_USER_NAME, outparam.get(FaAsReStoringPlanInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(ReStoringPlanInqListParams.SYS_DAY, outparam.get(FaAsReStoringPlanInquiryDASCHParams.SYS_DAY));
                expparam.set(ReStoringPlanInqListParams.SYS_TIME, outparam.get(FaAsReStoringPlanInquiryDASCHParams.SYS_TIME));
                expparam.set(ReStoringPlanInqListParams.STATION_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.STATION_NO));
                expparam.set(ReStoringPlanInqListParams.STATION_NAME, outparam.get(FaAsReStoringPlanInquiryDASCHParams.STATION_NAME));
                expparam.set(ReStoringPlanInqListParams.WORK_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.WORK_NO));
                expparam.set(ReStoringPlanInqListParams.ITEM_CODE, outparam.get(FaAsReStoringPlanInquiryDASCHParams.ITEM_CODE));
                expparam.set(ReStoringPlanInqListParams.ITEM_NAME, outparam.get(FaAsReStoringPlanInquiryDASCHParams.ITEM_NAME));
                expparam.set(ReStoringPlanInqListParams.LOT_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.LOT_NO));
                expparam.set(ReStoringPlanInqListParams.STORAGE_DATE, outparam.get(FaAsReStoringPlanInquiryDASCHParams.STORAGE_DATE));
                expparam.set(ReStoringPlanInqListParams.REMOVE_DATE, outparam.get(FaAsReStoringPlanInquiryDASCHParams.REMOVE_DATE));
                expparam.set(ReStoringPlanInqListParams.PLAN_QTY, outparam.get(FaAsReStoringPlanInquiryDASCHParams.PLAN_QTY));
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
            part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
            part11List.add(txt_WorkNo.getStringValue(), "");
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
        pul_Station.validate(this, true);
        txt_WorkNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaAsReStoringPlanInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaAsReStoringPlanInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaAsReStoringPlanInquiryDASCHParams inparam = new FaAsReStoringPlanInquiryDASCHParams();
            inparam.set(FaAsReStoringPlanInquiryDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaAsReStoringPlanInquiryDASCHParams.WORK_NO, txt_WorkNo.getValue());

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
            exporter = factory.newPVExporter("ReStoringPlanInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ReStoringPlanInqListParams expparam = new ReStoringPlanInqListParams();
                expparam.set(ReStoringPlanInqListParams.DFK_DS_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.DFK_DS_NO));
                expparam.set(ReStoringPlanInqListParams.DFK_USER_ID, outparam.get(FaAsReStoringPlanInquiryDASCHParams.DFK_USER_ID));
                expparam.set(ReStoringPlanInqListParams.DFK_USER_NAME, outparam.get(FaAsReStoringPlanInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(ReStoringPlanInqListParams.ITEM_CODE, outparam.get(FaAsReStoringPlanInquiryDASCHParams.ITEM_CODE));
                expparam.set(ReStoringPlanInqListParams.ITEM_NAME, outparam.get(FaAsReStoringPlanInquiryDASCHParams.ITEM_NAME));
                expparam.set(ReStoringPlanInqListParams.LOT_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.LOT_NO));
                expparam.set(ReStoringPlanInqListParams.PLAN_QTY, outparam.get(FaAsReStoringPlanInquiryDASCHParams.PLAN_QTY));
                expparam.set(ReStoringPlanInqListParams.REMOVE_DATE, outparam.get(FaAsReStoringPlanInquiryDASCHParams.REMOVE_DATE));
                expparam.set(ReStoringPlanInqListParams.STATION_NAME, outparam.get(FaAsReStoringPlanInquiryDASCHParams.STATION_NAME));
                expparam.set(ReStoringPlanInqListParams.STATION_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.STATION_NO));
                expparam.set(ReStoringPlanInqListParams.STORAGE_DATE, outparam.get(FaAsReStoringPlanInquiryDASCHParams.STORAGE_DATE));
                expparam.set(ReStoringPlanInqListParams.SYS_DAY, outparam.get(FaAsReStoringPlanInquiryDASCHParams.SYS_DAY));
                expparam.set(ReStoringPlanInqListParams.SYS_TIME, outparam.get(FaAsReStoringPlanInquiryDASCHParams.SYS_TIME));
                expparam.set(ReStoringPlanInqListParams.WORK_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.WORK_NO));
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
            part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
            part11List.add(txt_WorkNo.getStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PREVIEW), part11List);
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
        pul_Station.validate(this, true);
        txt_WorkNo.validate(this, false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaAsReStoringPlanInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaAsReStoringPlanInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaAsReStoringPlanInquiryDASCHParams inparam = new FaAsReStoringPlanInquiryDASCHParams();
            inparam.set(FaAsReStoringPlanInquiryDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaAsReStoringPlanInquiryDASCHParams.WORK_NO, txt_WorkNo.getValue());

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
            exporter = factory.newExcelExporter("ReStoringPlanInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ReStoringPlanInqListParams expparam = new ReStoringPlanInqListParams();
                expparam.set(ReStoringPlanInqListParams.STATION_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.STATION_NO));
                expparam.set(ReStoringPlanInqListParams.STATION_NAME, outparam.get(FaAsReStoringPlanInquiryDASCHParams.STATION_NAME));
                expparam.set(ReStoringPlanInqListParams.WORK_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.WORK_NO));
                expparam.set(ReStoringPlanInqListParams.ITEM_CODE, outparam.get(FaAsReStoringPlanInquiryDASCHParams.ITEM_CODE));
                expparam.set(ReStoringPlanInqListParams.ITEM_NAME, outparam.get(FaAsReStoringPlanInquiryDASCHParams.ITEM_NAME));
                expparam.set(ReStoringPlanInqListParams.LOT_NO, outparam.get(FaAsReStoringPlanInquiryDASCHParams.LOT_NO));
                expparam.set(ReStoringPlanInqListParams.STORAGE_DATE, outparam.get(FaAsReStoringPlanInquiryDASCHParams.STORAGE_DATE));
                expparam.set(ReStoringPlanInqListParams.REMOVE_DATE, outparam.get(FaAsReStoringPlanInquiryDASCHParams.REMOVE_DATE));
                expparam.set(ReStoringPlanInqListParams.PLAN_QTY, outparam.get(FaAsReStoringPlanInquiryDASCHParams.PLAN_QTY));
                if (!exporter.write(expparam))
                {
                    //DFKLOOK start
                    // XLS最大件数出力メッセージ対応
                    message.setMsgResourceKey("6001031\t"
                            + Formatter.getNumFormat(count) + "\t"
                            + Formatter.getNumFormat(CommonParam.getIntParam("MAX_NUMBER_OF_XLS")
                                    - exporter.getHeaderRowsCount()));
                    //DFKLOOK end
                    break;
                }
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
            part11List.add(txt_WorkNo.getStringValue(), "");
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
        _pdm_pul_Station.setSelectedValue(null);
        txt_WorkNo.setValue(null);

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
