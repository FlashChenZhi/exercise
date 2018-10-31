// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.stock.display.faitemworkinginquiry;

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
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ScrollListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
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
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.itemname.LstItemNameParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.stock.dasch.FaItemWorkingInquiryDASCH;
import jp.co.daifuku.wms.stock.dasch.FaItemWorkingInquiryDASCHParams;
import jp.co.daifuku.wms.stock.display.faitemworkinginquiry.FaItemWorkingInquiry;
import jp.co.daifuku.wms.stock.exporter.StockByItemListParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaItemWorkingInquiryBusiness
        extends FaItemWorkingInquiry
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

    /** lst_FaStockInquiry(HIDDEN_ITEM_NAME) */
    private static final ListCellKey KEY_HIDDEN_ITEM_NAME = new ListCellKey("HIDDEN_ITEM_NAME", new StringCellColumn(), false, false);

    /** lst_FaStockInquiry(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_FaStockInquiry(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_FaStockInquiry(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_FaStockInquiry(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_FaStockInquiry(LST_STORAGE_DATETIME) */
    private static final ListCellKey KEY_LST_STORAGE_DATETIME = new ListCellKey("LST_STORAGE_DATETIME", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_FaStockInquiry(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_FaStockInquiry(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_FaStockInquiry(LST_TOTAL_STOCK_QTY) */
    private static final ListCellKey KEY_LST_TOTAL_STOCK_QTY = new ListCellKey("LST_TOTAL_STOCK_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaStockInquiry(LST_STOCK_QTY) */
    private static final ListCellKey KEY_LST_STOCK_QTY = new ListCellKey("LST_STOCK_QTY", new NumberCellColumn(0), true, false);

    /** lst_FaStockInquiry(LST_LOCATION_STATUS) */
    private static final ListCellKey KEY_LST_LOCATION_STATUS = new ListCellKey("LST_LOCATION_STATUS", new StringCellColumn(), true, false);

    /** lst_FaStockInquiry keys */
    private static final ListCellKey[] LST_FASTOCKINQUIRY_KEYS = {
        KEY_HIDDEN_ITEM_NAME,
        KEY_LST_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOT_NO,
        KEY_LST_STORAGE_DATETIME,
        KEY_LST_AREA_NO,
        KEY_LST_LOCATION_NO,
        KEY_LST_TOTAL_STOCK_QTY,
        KEY_LST_STOCK_QTY,
        KEY_LST_LOCATION_STATUS,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** RadioButtonGroupModel SearchCondition */
    private RadioButtonGroup _grp_SearchCondition;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_FaStockInquiry */
    private ScrollListCellModel _lcm_lst_FaStockInquiry;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaItemWorkingInquiryBusiness()
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
        if (eventSource.equals("btn_SearchFromItemCode_Click"))
        {
            // process call.
            btn_SearchFromItemCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchFromItemName_Click"))
        {
            // process call.
            btn_SearchFromItemName_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchToItemCode_Click"))
        {
            // process call.
            btn_SearchToItemCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchToItemName_Click"))
        {
            // process call.
            btn_SearchToItemName_Click_DlgBack(dialogParams);
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
    public void btn_SearchFromItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchFromItemCode_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchFromItemName_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchFromItemName_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchToItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchToItemCode_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchToItemName_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchToItemName_Click_Process();
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

        // initialize SearchCondition.
        _grp_SearchCondition = new RadioButtonGroup(new RadioButton[]{rdo_Search_CountByItemCode, rdo_Search_CountByItemCodeAndL, rdo_Search_DetailsPerItemCode}, locale);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_FaStockInquiry.
        _lcm_lst_FaStockInquiry = new ScrollListCellModel(lst_FaStockInquiry, LST_FASTOCKINQUIRY_KEYS, locale);
        _lcm_lst_FaStockInquiry.setToolTipVisible(KEY_LST_NO, true);
        _lcm_lst_FaStockInquiry.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_FaStockInquiry.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_FaStockInquiry.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_FaStockInquiry.setToolTipVisible(KEY_LST_STORAGE_DATETIME, true);
        _lcm_lst_FaStockInquiry.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_FaStockInquiry.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_FaStockInquiry.setToolTipVisible(KEY_LST_TOTAL_STOCK_QTY, true);
        _lcm_lst_FaStockInquiry.setToolTipVisible(KEY_LST_STOCK_QTY, true);
        _lcm_lst_FaStockInquiry.setToolTipVisible(KEY_LST_LOCATION_STATUS, true);
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
            _pdm_pul_Area.init(conn, AreaType.NOT_MOVING, StationType.STORAGE, "", false);
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
    private void lst_FaStockInquiry_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("", KEY_HIDDEN_ITEM_NAME);
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
        setFocus(txt_FromItemCode_EntEvent);
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        _pdm_pul_Area.setSelectedValue(null);
        txt_FromItemCode_EntEvent.setValue(null);
        txt_FromItemName.setValue(null);
        txt_ToItemCode_EntEvent.setValue(null);
        txt_ToItemName.setValue(null);
        rdo_Search_CountByItemCode.setChecked(true);
        _pager.clear();
        _lcm_lst_FaStockInquiry.clear();
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchFromItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstItemParams.ITEM_CODE, txt_FromItemCode_EntEvent.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchFromItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchFromItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_FromItemCode_EntEvent.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_FromItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));

        // set focus.
        setFocus(txt_FromItemCode_EntEvent);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchFromItemName_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemNameParams inparam = new LstItemNameParams();
        inparam.set(LstItemNameParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstItemNameParams.ITEM_NAME, txt_FromItemName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchFromItemName_Click");
        redirect("/base/listbox/itemname/LstItemName.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchFromItemName_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemNameParams outparam = new LstItemNameParams(dialogParams);
        txt_FromItemCode_EntEvent.setValue(outparam.get(LstItemNameParams.ITEM_CODE));
        txt_FromItemName.setValue(outparam.get(LstItemNameParams.ITEM_NAME));

        // set focus.
        setFocus(txt_FromItemName);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchToItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstItemParams.ITEM_CODE, txt_ToItemCode_EntEvent.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchToItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchToItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ToItemCode_EntEvent.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ToItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));

        // set focus.
        setFocus(txt_ToItemCode_EntEvent);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchToItemName_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemNameParams inparam = new LstItemNameParams();
        inparam.set(LstItemNameParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstItemNameParams.ITEM_NAME, txt_ToItemName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchToItemName_Click");
        redirect("/base/listbox/itemname/LstItemName.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchToItemName_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemNameParams outparam = new LstItemNameParams(dialogParams);
        txt_ToItemCode_EntEvent.setValue(outparam.get(LstItemNameParams.ITEM_CODE));
        txt_ToItemName.setValue(outparam.get(LstItemNameParams.ITEM_NAME));

        // set focus.
        setFocus(txt_ToItemName);
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
        FaItemWorkingInquiryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaItemWorkingInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaItemWorkingInquiryDASCHParams inparam = new FaItemWorkingInquiryDASCHParams();
            inparam.set(FaItemWorkingInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaItemWorkingInquiryDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.FROM_ITEM_CODE, txt_FromItemCode_EntEvent.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.FROM_ITEM_NAME, txt_FromItemName.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.TO_ITEM_CODE, txt_ToItemCode_EntEvent.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.TO_ITEM_NAME, txt_ToItemName.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.SEARCH_CONDITION, _grp_SearchCondition.getSelectedValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_FaStockInquiry.clear();

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
            _lcm_lst_FaStockInquiry.clear();
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
        FaItemWorkingInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaItemWorkingInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_FaStockInquiry.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaStockInquiry.getNewLine();
                line.setValue(KEY_LST_NO, outparam.get(FaItemWorkingInquiryDASCHParams.NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaItemWorkingInquiryDASCHParams.LOT_NO));
                line.setValue(KEY_LST_STORAGE_DATETIME, outparam.get(FaItemWorkingInquiryDASCHParams.STORAGE_DATETIME));
                line.setValue(KEY_LST_AREA_NO, outparam.get(FaItemWorkingInquiryDASCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_TOTAL_STOCK_QTY, outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));
                line.setValue(KEY_LST_LOCATION_STATUS, outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_STATUS));
                line.setValue(KEY_HIDDEN_ITEM_NAME, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_STOCK_QTY, outparam.get(FaItemWorkingInquiryDASCHParams.STOCK_QTY));
                lst_FaStockInquiry_SetLineToolTip(line);
                _lcm_lst_FaStockInquiry.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaStockInquiry.clear();
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
        txt_FromItemCode_EntEvent.validate(this, false);
        txt_FromItemName.validate(this, false);
        txt_ToItemCode_EntEvent.validate(this, false);
        txt_ToItemName.validate(this, false);
        rdo_Search_CountByItemCode.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemWorkingInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaItemWorkingInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaItemWorkingInquiryDASCHParams inparam = new FaItemWorkingInquiryDASCHParams();
            inparam.set(FaItemWorkingInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaItemWorkingInquiryDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.FROM_ITEM_CODE, txt_FromItemCode_EntEvent.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.FROM_ITEM_NAME, txt_FromItemName.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.TO_ITEM_CODE, txt_ToItemCode_EntEvent.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.TO_ITEM_NAME, txt_ToItemName.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.SEARCH_CONDITION, _grp_SearchCondition.getSelectedValue());

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
            exporter = factory.newPrinterExporter("StockByItemList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                StockByItemListParams expparam = new StockByItemListParams();
                expparam.set(StockByItemListParams.DFK_DS_NO, outparam.get(FaItemWorkingInquiryDASCHParams.DFK_DS_NO));
                expparam.set(StockByItemListParams.DFK_USER_ID, outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_ID));
                expparam.set(StockByItemListParams.DFK_USER_NAME, outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(StockByItemListParams.SYS_DAY, outparam.get(FaItemWorkingInquiryDASCHParams.SYS_DAY));
                expparam.set(StockByItemListParams.SYS_TIME, outparam.get(FaItemWorkingInquiryDASCHParams.SYS_TIME));
                expparam.set(StockByItemListParams.SEARCH_KIND, _pdm_pul_Area.getSelectedValue());
                expparam.set(StockByItemListParams.FROM_ITEM_CODE, txt_FromItemCode_EntEvent.getValue());
                expparam.set(StockByItemListParams.TO_ITEM_CODE, txt_ToItemCode_EntEvent.getValue());
                expparam.set(StockByItemListParams.ITEM_CODE, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(StockByItemListParams.ITEM_NAME, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(StockByItemListParams.LOT_NO, outparam.get(FaItemWorkingInquiryDASCHParams.LOT_NO));
                expparam.set(StockByItemListParams.STORAGE_DATE, outparam.get(FaItemWorkingInquiryDASCHParams.STORAGE_DATETIME));
                expparam.set(StockByItemListParams.AREA_NO, outparam.get(FaItemWorkingInquiryDASCHParams.AREA_NO));
                expparam.set(StockByItemListParams.LOCATION_NO, outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_NO));
                expparam.set(StockByItemListParams.LOCATION_STATUS, outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_STATUS));
                expparam.set(StockByItemListParams.STOCK_QTY, outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));
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
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
            part11List.add(txt_FromItemCode_EntEvent.getStringValue(), "");
            part11List.add(txt_ToItemCode_EntEvent.getStringValue(), "");
            part11List.add("0", "", rdo_Search_CountByItemCode.getChecked());
            part11List.add("1", "", rdo_Search_CountByItemCodeAndL.getChecked());
            part11List.add("2", "", rdo_Search_DetailsPerItemCode.getChecked());
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
        txt_FromItemCode_EntEvent.validate(this, false);
        txt_FromItemName.validate(this, false);
        txt_ToItemCode_EntEvent.validate(this, false);
        txt_ToItemName.validate(this, false);
        rdo_Search_CountByItemCode.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemWorkingInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaItemWorkingInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaItemWorkingInquiryDASCHParams inparam = new FaItemWorkingInquiryDASCHParams();
            inparam.set(FaItemWorkingInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaItemWorkingInquiryDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.FROM_ITEM_CODE, txt_FromItemCode_EntEvent.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.FROM_ITEM_NAME, txt_FromItemName.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.TO_ITEM_CODE, txt_ToItemCode_EntEvent.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.TO_ITEM_NAME, txt_ToItemName.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.SEARCH_CONDITION, _grp_SearchCondition.getSelectedValue());

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
            exporter = factory.newPVExporter("StockByItemList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                StockByItemListParams expparam = new StockByItemListParams();
                expparam.set(StockByItemListParams.DFK_DS_NO, outparam.get(FaItemWorkingInquiryDASCHParams.DFK_DS_NO));
                expparam.set(StockByItemListParams.DFK_USER_ID, outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_ID));
                expparam.set(StockByItemListParams.DFK_USER_NAME, outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(StockByItemListParams.SYS_DAY, outparam.get(FaItemWorkingInquiryDASCHParams.SYS_DAY));
                expparam.set(StockByItemListParams.SYS_TIME, outparam.get(FaItemWorkingInquiryDASCHParams.SYS_TIME));
                expparam.set(StockByItemListParams.SEARCH_KIND, _pdm_pul_Area.getSelectedValue());
                expparam.set(StockByItemListParams.FROM_ITEM_CODE, txt_FromItemCode_EntEvent.getValue());
                expparam.set(StockByItemListParams.TO_ITEM_CODE, txt_ToItemCode_EntEvent.getValue());
                expparam.set(StockByItemListParams.ITEM_CODE, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(StockByItemListParams.ITEM_NAME, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(StockByItemListParams.LOT_NO, outparam.get(FaItemWorkingInquiryDASCHParams.LOT_NO));
                expparam.set(StockByItemListParams.STORAGE_DATE, outparam.get(FaItemWorkingInquiryDASCHParams.STORAGE_DATETIME));
                expparam.set(StockByItemListParams.AREA_NO, outparam.get(FaItemWorkingInquiryDASCHParams.AREA_NO));
                expparam.set(StockByItemListParams.LOCATION_NO, outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_NO));
                expparam.set(StockByItemListParams.LOCATION_STATUS, outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_STATUS));
                expparam.set(StockByItemListParams.STOCK_QTY, outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));
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
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
            part11List.add(txt_FromItemCode_EntEvent.getStringValue(), "");
            part11List.add(txt_ToItemCode_EntEvent.getStringValue(), "");
            part11List.add("0", "", rdo_Search_CountByItemCode.getChecked());
            part11List.add("1", "", rdo_Search_CountByItemCodeAndL.getChecked());
            part11List.add("2", "", rdo_Search_DetailsPerItemCode.getChecked());
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
        txt_FromItemCode_EntEvent.validate(this, false);
        txt_FromItemName.validate(this, false);
        txt_ToItemCode_EntEvent.validate(this, false);
        txt_ToItemName.validate(this, false);
        rdo_Search_CountByItemCode.validate(false);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaItemWorkingInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaItemWorkingInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaItemWorkingInquiryDASCHParams inparam = new FaItemWorkingInquiryDASCHParams();
            inparam.set(FaItemWorkingInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaItemWorkingInquiryDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.FROM_ITEM_CODE, txt_FromItemCode_EntEvent.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.FROM_ITEM_NAME, txt_FromItemName.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.TO_ITEM_CODE, txt_ToItemCode_EntEvent.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.TO_ITEM_NAME, txt_ToItemName.getValue());
            inparam.set(FaItemWorkingInquiryDASCHParams.SEARCH_CONDITION, _grp_SearchCondition.getSelectedValue());

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
            exporter = factory.newExcelExporter("StockByItemList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                StockByItemListParams expparam = new StockByItemListParams();
                expparam.set(StockByItemListParams.ITEM_CODE, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(StockByItemListParams.ITEM_NAME, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(StockByItemListParams.LOT_NO, outparam.get(FaItemWorkingInquiryDASCHParams.LOT_NO));
                expparam.set(StockByItemListParams.STORAGE_DATE, outparam.get(FaItemWorkingInquiryDASCHParams.STORAGE_DATETIME));
                expparam.set(StockByItemListParams.AREA_NO, outparam.get(FaItemWorkingInquiryDASCHParams.AREA_NO));
                expparam.set(StockByItemListParams.LOCATION_NO, outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_NO));
                expparam.set(StockByItemListParams.LOCATION_STATUS, outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_STATUS));
                expparam.set(StockByItemListParams.STOCK_QTY, outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
            part11List.add(txt_FromItemCode_EntEvent.getStringValue(), "");
            part11List.add(txt_ToItemCode_EntEvent.getStringValue(), "");
            part11List.add("0", "", rdo_Search_CountByItemCode.getChecked());
            part11List.add("1", "", rdo_Search_CountByItemCodeAndL.getChecked());
            part11List.add("2", "", rdo_Search_DetailsPerItemCode.getChecked());
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
        txt_FromItemCode_EntEvent.setValue(null);
        txt_FromItemName.setValue(null);
        txt_ToItemCode_EntEvent.setValue(null);
        txt_ToItemName.setValue(null);
        rdo_Search_CountByItemCode.setChecked(true);
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
