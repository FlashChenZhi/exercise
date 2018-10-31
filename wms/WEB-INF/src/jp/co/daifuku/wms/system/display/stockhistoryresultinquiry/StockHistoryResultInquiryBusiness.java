// $Id: StockHistoryResultInquiryBusiness.java 7618 2010-03-16 13:13:38Z shibamoto $
package jp.co.daifuku.wms.system.display.stockhistoryresultinquiry;

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

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkFlagPullDownModel;
import jp.co.daifuku.wms.system.dasch.StockHistoryResultInquiryDASCH;
import jp.co.daifuku.wms.system.dasch.StockHistoryResultInquiryDASCHParams;
import jp.co.daifuku.wms.system.exporter.ResultInqListParams;
import jp.co.daifuku.wms.system.listbox.user.LstSystemUserParams;

/**
 * 入出庫実績照会の画面処理を行います。
 *
 * @version $Revision: 7618 $, $Date: 2010-03-16 22:13:38 +0900 (火, 16 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class StockHistoryResultInquiryBusiness
        extends StockHistoryResultInquiry
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

    /** lst_StorageRetrievalResultList(CONSIGNOR_CODE) */
    private static final ListCellKey KEY_CONSIGNOR_CODE = new ListCellKey("CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_StorageRetrievalResultList(LST_REGIST_DATE) */
    private static final ListCellKey KEY_LST_REGIST_DATE = new ListCellKey("LST_REGIST_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_StorageRetrievalResultList(LST_REGIST_TIME) */
    private static final ListCellKey KEY_LST_REGIST_TIME = new ListCellKey("LST_REGIST_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, false);

    /** lst_StorageRetrievalResultList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_StorageRetrievalResultList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_StorageRetrievalResultList(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_StorageRetrievalResultList(LST_INC_DEC_TYPE) */
    private static final ListCellKey KEY_LST_INC_DEC_TYPE = new ListCellKey("LST_INC_DEC_TYPE", new NumberCellColumn(0), true, false);

    /** lst_StorageRetrievalResultList(LST_JOB_TYPE) */
    private static final ListCellKey KEY_LST_JOB_TYPE = new ListCellKey("LST_JOB_TYPE", new StringCellColumn(), true, false);

    /** lst_StorageRetrievalResultList(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_StorageRetrievalResultList(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_StorageRetrievalResultList(LST_INC_DEC_QTY) */
    private static final ListCellKey KEY_LST_INC_DEC_QTY = new ListCellKey("LST_INC_DEC_QTY", new NumberCellColumn(0), true, false);

    /** lst_StorageRetrievalResultList(LST_STORAGE_DATE) */
    private static final ListCellKey KEY_LST_STORAGE_DATE = new ListCellKey("LST_STORAGE_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_StorageRetrievalResultList(LST_STORAGE_TIME) */
    private static final ListCellKey KEY_LST_STORAGE_TIME = new ListCellKey("LST_STORAGE_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, false);

    /** lst_StorageRetrievalResultList(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_StorageRetrievalResultList keys */
    private static final ListCellKey[] LST_STORAGERETRIEVALRESULTLIST_KEYS = {
        KEY_CONSIGNOR_CODE,
        KEY_LST_REGIST_DATE,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_INC_DEC_TYPE,
        KEY_LST_JOB_TYPE,
        KEY_LST_AREA_NO,
        KEY_LST_INC_DEC_QTY,
        KEY_LST_STORAGE_DATE,
        KEY_LST_USER_NAME,
        KEY_LST_REGIST_TIME,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOCATION_NO,
        KEY_LST_STORAGE_TIME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** PullDownModel pul_WorkFlag */
    //DFKLOOK:ここから修正
    private WmsWorkFlagPullDownModel _pdm_pul_WorkFlag ;
    //DFKLOOK:ここまで修正

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_StorageRetrievalResultList */
    private ListCellModel _lcm_lst_StorageRetrievalResultList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public StockHistoryResultInquiryBusiness()
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
        if (eventSource.equals("btn_PSearch_Click"))
        {
            // process call.
            btn_PSearch_Click_DlgBack(dialogParams);
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
    public void btn_PSearch_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_PSearch_Click_Process();
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
    public void pgr_U_First(ActionEvent e)
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
    public void pgr_U_Prev(ActionEvent e)
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
    public void pgr_U_Next(ActionEvent e)
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
    public void pgr_U_Last(ActionEvent e)
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
    public void lst_StorageRetrievalResultList_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StorageRetrievalResultList_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_First(ActionEvent e)
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
    public void pgr_D_Prev(ActionEvent e)
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
    public void pgr_D_Next(ActionEvent e)
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
    public void pgr_D_Last(ActionEvent e)
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
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // initialize pul_Area.
        _pdm_pul_Area = new WmsAreaPullDownModel(pul_Area, locale, ui);

        // initialize pul_WorkFlag.
        //DFKLOOK:ここから修正
        _pdm_pul_WorkFlag = new WmsWorkFlagPullDownModel(pul_WorkFlag, locale, ui);
        //DFKLOOK:ここまで修正

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_StorageRetrievalResultList.
        _lcm_lst_StorageRetrievalResultList = new ListCellModel(lst_StorageRetrievalResultList, LST_STORAGERETRIEVALRESULTLIST_KEYS, locale);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_REGIST_DATE, true);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_REGIST_TIME, true);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_INC_DEC_TYPE, true);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_JOB_TYPE, true);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_INC_DEC_QTY, true);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_STORAGE_DATE, true);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_STORAGE_TIME, true);
        _lcm_lst_StorageRetrievalResultList.setToolTipVisible(KEY_LST_USER_NAME, true);

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

            // load pul_Area.
            //DFKLOOK:ここから修正
            _pdm_pul_Area.init(conn, AreaType.ALL);
            //DFKLOOK:ここまで修正

            // load pul_WorkFlag.
            //DFKLOOK:ここから修正
            _pdm_pul_WorkFlag.init(conn, false);
            //DFKLOOK:ここまで修正

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
    private void lst_StorageRetrievalResultList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);
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
        setFocus(txt_SearchDateFrom);

    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        txt_UserName.setReadOnly(true);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_PSearch_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstSystemUserParams inparam = new LstSystemUserParams();
        inparam.set(LstSystemUserParams.USER_NAME, txt_UserName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_PSearch_Click");
        redirect("/system/listbox/user/LstSystemUser.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_PSearch_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstSystemUserParams outparam = new LstSystemUserParams(dialogParams);
        txt_UserName.setValue(outparam.get(LstSystemUserParams.USER_NAME));

        // set focus.
        setFocus(txt_UserName);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // DFKLOOK ここから
        WmsChecker chk = new WmsChecker() ;

        if (!chk.checkDate(txt_SearchDateFrom.getDate(), txt_SearchTimeFrom.getTime()))
        {
            message.setMsgResourceKey(chk.getMessage()) ;
            return ;
        }
        if (!chk.checkDate(txt_SearchDateTo.getDate(), txt_SearchTimeTo.getTime()))
        {
            message.setMsgResourceKey(chk.getMessage()) ;
            return ;
        }
        // input validation.
        txt_ItemCode.validate(this, false) ;
        txt_LotNo.validate(this, false) ;
        pul_Area.validate(this, true);
        pul_WorkFlag.validate(this, true);
        // DFKLOOK ここまで

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StockHistoryResultInquiryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new StockHistoryResultInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            StockHistoryResultInquiryDASCHParams inparam = new StockHistoryResultInquiryDASCHParams();
            inparam.set(StockHistoryResultInquiryDASCHParams.SEARCH_DAY_FROM, txt_SearchDateFrom.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.SEARCH_TIME_FROM, txt_SearchTimeFrom.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.SEARCH_DAY_TO, txt_SearchDateTo.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.SEARCH_TIME_TO, txt_SearchTimeTo.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.JOB_TYPE, _pdm_pul_WorkFlag.getSelectedValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.USER_NAME, txt_UserName.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_StorageRetrievalResultList.clear();

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
            _lcm_lst_StorageRetrievalResultList.clear();
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
        StockHistoryResultInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (StockHistoryResultInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_StorageRetrievalResultList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StorageRetrievalResultList.getNewLine();
                line.setValue(KEY_CONSIGNOR_CODE, outparam.get(StockHistoryResultInquiryDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_LST_REGIST_DATE, outparam.get(StockHistoryResultInquiryDASCHParams.REGIST_DATE));
                line.setValue(KEY_LST_REGIST_TIME, outparam.get(StockHistoryResultInquiryDASCHParams.REGIST_DATE));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(StockHistoryResultInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(StockHistoryResultInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(StockHistoryResultInquiryDASCHParams.LOT_NO));
                line.setValue(KEY_LST_INC_DEC_TYPE, outparam.get(StockHistoryResultInquiryDASCHParams.INC_DEC_TYPE));
                line.setValue(KEY_LST_JOB_TYPE, outparam.get(StockHistoryResultInquiryDASCHParams.JOB_TYPE));
                line.setValue(KEY_LST_AREA_NO, outparam.get(StockHistoryResultInquiryDASCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(StockHistoryResultInquiryDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_INC_DEC_QTY, outparam.get(StockHistoryResultInquiryDASCHParams.INC_DEC_QTY));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(StockHistoryResultInquiryDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(StockHistoryResultInquiryDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_USER_NAME, outparam.get(StockHistoryResultInquiryDASCHParams.USER_NAME));
                lst_StorageRetrievalResultList_SetLineToolTip(line);
                _lcm_lst_StorageRetrievalResultList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_StorageRetrievalResultList.clear();
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
        txt_ItemCode.validate(this, false);
        txt_LotNo.validate(this, false);
        pul_Area.validate(this, true);
        pul_WorkFlag.validate(this, true);
        
        // DFKLOOK ここから
        WmsChecker chk = new WmsChecker() ;

        if (!chk.checkDate(txt_SearchDateFrom.getDate(), txt_SearchTimeFrom.getTime()))
        {
            message.setMsgResourceKey(chk.getMessage()) ;
            return ;
        }
        if (!chk.checkDate(txt_SearchDateTo.getDate(), txt_SearchTimeTo.getTime()))
        {
            message.setMsgResourceKey(chk.getMessage()) ;
            return ;
        }
        // DFKLOOK ここまで

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StockHistoryResultInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new StockHistoryResultInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            StockHistoryResultInquiryDASCHParams inparam = new StockHistoryResultInquiryDASCHParams();
            inparam.set(StockHistoryResultInquiryDASCHParams.SEARCH_DAY_FROM, txt_SearchDateFrom.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.SEARCH_TIME_FROM, txt_SearchTimeFrom.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.SEARCH_DAY_TO, txt_SearchDateTo.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.SEARCH_TIME_TO, txt_SearchTimeTo.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.JOB_TYPE, _pdm_pul_WorkFlag.getSelectedValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.USER_NAME, txt_UserName.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

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

            // DFKLOOK:検索日時の格納
            Date[] tmp = WmsFormatter.getFromTo(txt_SearchDateFrom.getDate(),
                    txt_SearchTimeFrom.getTime(), txt_SearchDateTo.getDate(),
                    txt_SearchTimeTo.getTime()) ;

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("ResultInqList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ResultInqListParams expparam = new ResultInqListParams();
                expparam.set(ResultInqListParams.DFK_DS_NO, outparam.get(StockHistoryResultInquiryDASCHParams.DFK_DS_NO));
                expparam.set(ResultInqListParams.DFK_USER_ID, outparam.get(StockHistoryResultInquiryDASCHParams.DFK_USER_ID));
                expparam.set(ResultInqListParams.DFK_USER_NAME, outparam.get(StockHistoryResultInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(ResultInqListParams.SYS_DAY, outparam.get(StockHistoryResultInquiryDASCHParams.SYS_DAY));
                expparam.set(ResultInqListParams.SYS_TIME, outparam.get(StockHistoryResultInquiryDASCHParams.SYS_TIME));
                expparam.set(ResultInqListParams.FROM_GENERATION_DAY, txt_SearchDateFrom.getValue());
                expparam.set(ResultInqListParams.FROM_GENERATION_TIME, txt_SearchTimeFrom.getValue());
                expparam.set(ResultInqListParams.TO_GENERATION_DAY, txt_SearchDateTo.getValue());
                expparam.set(ResultInqListParams.TO_GENERATION_TIME, txt_SearchTimeTo.getValue());
                expparam.set(ResultInqListParams.GENERATION_DAY, outparam.get(StockHistoryResultInquiryDASCHParams.REGIST_DATE));
                expparam.set(ResultInqListParams.GENERATION_TIME, outparam.get(StockHistoryResultInquiryDASCHParams.REGIST_DATE));
                expparam.set(ResultInqListParams.ITEM_CODE, outparam.get(StockHistoryResultInquiryDASCHParams.ITEM_CODE));
                expparam.set(ResultInqListParams.ITEM_NAME, outparam.get(StockHistoryResultInquiryDASCHParams.ITEM_NAME));
                expparam.set(ResultInqListParams.LOT_NO, outparam.get(StockHistoryResultInquiryDASCHParams.LOT_NO));
                expparam.set(ResultInqListParams.INC_DEC_TYPE, outparam.get(StockHistoryResultInquiryDASCHParams.INC_DEC_TYPE));
                expparam.set(ResultInqListParams.JOB_TYPE, outparam.get(StockHistoryResultInquiryDASCHParams.JOB_TYPE));
                expparam.set(ResultInqListParams.AREA_NO, outparam.get(StockHistoryResultInquiryDASCHParams.AREA_NO));
                expparam.set(ResultInqListParams.LOCATION_NO, outparam.get(StockHistoryResultInquiryDASCHParams.LOCATION_NO));
                expparam.set(ResultInqListParams.WORK_QTY, outparam.get(StockHistoryResultInquiryDASCHParams.INC_DEC_QTY));
                expparam.set(ResultInqListParams.STORAGE_DAY, outparam.get(StockHistoryResultInquiryDASCHParams.STORAGE_DATE));
                expparam.set(ResultInqListParams.STORAGE_TIME, outparam.get(StockHistoryResultInquiryDASCHParams.STORAGE_DATE));
                expparam.set(ResultInqListParams.USER_NAME, outparam.get(StockHistoryResultInquiryDASCHParams.USER_NAME));
                // DFKLOOK:検索日時をセット
                expparam.set(ResultInqListParams.FROM_GENERATION_DAY, tmp[0]) ;
                expparam.set(ResultInqListParams.FROM_GENERATION_TIME, tmp[0]) ;
                expparam.set(ResultInqListParams.TO_GENERATION_DAY, tmp[1]) ;
                expparam.set(ResultInqListParams.TO_GENERATION_TIME, tmp[1]) ;
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
            part11List.add(txt_SearchDateFrom.getStringValue(), txt_SearchTimeFrom.getStringValue(), "");
            part11List.add(txt_SearchDateTo.getStringValue(), txt_SearchTimeTo.getStringValue(), "");
            part11List.add(txt_ItemCode.getStringValue(), "");
            part11List.add(txt_LotNo.getStringValue(), "");
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_WorkFlag.getSelectedStringValue(), "");
            part11List.add(txt_UserName.getStringValue(), "");
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
        txt_ItemCode.validate(this, false);
        txt_LotNo.validate(this, false);
        pul_Area.validate(this, true);
        pul_WorkFlag.validate(this, true);
        
        // DFKLOOK ここから
        WmsChecker chk = new WmsChecker() ;

        if (!chk.checkDate(txt_SearchDateFrom.getDate(), txt_SearchTimeFrom.getTime()))
        {
            message.setMsgResourceKey(chk.getMessage()) ;
            return ;
        }
        if (!chk.checkDate(txt_SearchDateTo.getDate(), txt_SearchTimeTo.getTime()))
        {
            message.setMsgResourceKey(chk.getMessage()) ;
            return ;
        }
        // DFKLOOK ここまで

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        StockHistoryResultInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new StockHistoryResultInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            StockHistoryResultInquiryDASCHParams inparam = new StockHistoryResultInquiryDASCHParams();
            inparam.set(StockHistoryResultInquiryDASCHParams.SEARCH_DAY_FROM, txt_SearchDateFrom.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.SEARCH_TIME_FROM, txt_SearchTimeFrom.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.SEARCH_DAY_TO, txt_SearchDateTo.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.SEARCH_TIME_TO, txt_SearchTimeTo.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.LOT_NO, txt_LotNo.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.JOB_TYPE, _pdm_pul_WorkFlag.getSelectedValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.USER_NAME, txt_UserName.getValue());
            inparam.set(StockHistoryResultInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

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
            exporter = factory.newExcelExporter("ResultInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ResultInqListParams expparam = new ResultInqListParams();
                expparam.set(ResultInqListParams.GENERATION_DAY, outparam.get(StockHistoryResultInquiryDASCHParams.REGIST_DATE));
                expparam.set(ResultInqListParams.GENERATION_TIME, outparam.get(StockHistoryResultInquiryDASCHParams.REGIST_DATE));
                expparam.set(ResultInqListParams.ITEM_CODE, outparam.get(StockHistoryResultInquiryDASCHParams.ITEM_CODE));
                expparam.set(ResultInqListParams.ITEM_NAME, outparam.get(StockHistoryResultInquiryDASCHParams.ITEM_NAME));
                expparam.set(ResultInqListParams.LOT_NO, outparam.get(StockHistoryResultInquiryDASCHParams.LOT_NO));
                expparam.set(ResultInqListParams.INC_DEC_TYPE, outparam.get(StockHistoryResultInquiryDASCHParams.INC_DEC_TYPE));
                expparam.set(ResultInqListParams.JOB_TYPE, outparam.get(StockHistoryResultInquiryDASCHParams.JOB_TYPE));
                expparam.set(ResultInqListParams.AREA_NO, outparam.get(StockHistoryResultInquiryDASCHParams.AREA_NO));
                expparam.set(ResultInqListParams.LOCATION_NO, outparam.get(StockHistoryResultInquiryDASCHParams.LOCATION_NO));
                expparam.set(ResultInqListParams.WORK_QTY, outparam.get(StockHistoryResultInquiryDASCHParams.INC_DEC_QTY));
                expparam.set(ResultInqListParams.STORAGE_DAY, outparam.get(StockHistoryResultInquiryDASCHParams.STORAGE_DATE));
                expparam.set(ResultInqListParams.STORAGE_TIME, outparam.get(StockHistoryResultInquiryDASCHParams.STORAGE_DATE));
                expparam.set(ResultInqListParams.USER_NAME, outparam.get(StockHistoryResultInquiryDASCHParams.USER_NAME));
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
            part11List.add(txt_SearchDateFrom.getStringValue(), txt_SearchTimeFrom.getStringValue(), "");
            part11List.add(txt_SearchDateTo.getStringValue(), txt_SearchTimeTo.getStringValue(), "");
            part11List.add(txt_ItemCode.getStringValue(), "");
            part11List.add(txt_LotNo.getStringValue(), "");
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_WorkFlag.getSelectedStringValue(), "");
            part11List.add(txt_UserName.getStringValue(), "");
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
        txt_SearchDateFrom.setValue(null);
        txt_SearchTimeFrom.setValue(null);
        txt_SearchDateTo.setValue(null);
        txt_SearchTimeTo.setValue(null);
        txt_ItemCode.setValue(null);
        txt_LotNo.setValue(null);
        _pdm_pul_Area.setSelectedValue(null);
        _pdm_pul_WorkFlag.setSelectedValue(null);
        txt_UserName.setValue(null);

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
