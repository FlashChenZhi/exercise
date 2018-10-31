// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.stock.display.fastockmnt;

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
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
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
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.itemname.LstItemNameParams;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.stock.dasch.FaStockMntDASCH;
import jp.co.daifuku.wms.stock.dasch.FaStockMntDASCHParams;
import jp.co.daifuku.wms.stock.display.fastockmnt.FaStockMnt;
import jp.co.daifuku.wms.stock.display.fastockmnt.ViewStateKeys;
import jp.co.daifuku.wms.stock.schedule.FaStockMntSCH;
import jp.co.daifuku.wms.stock.schedule.FaStockMntSCHParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaStockMntBusiness
        extends FaStockMnt
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ShelfMnt(HIDDEN_STOCK_ID) */
    private static final ListCellKey KEY_HIDDEN_STOCK_ID = new ListCellKey("HIDDEN_STOCK_ID", new StringCellColumn(), false, false);

    /** lst_ShelfMnt(HIDDEN_LAST_UPDATE_DATE) */
    private static final ListCellKey KEY_HIDDEN_LAST_UPDATE_DATE = new ListCellKey("HIDDEN_LAST_UPDATE_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMSS), false, false);

    /** lst_ShelfMnt(HIDDEN_SOFZONE_ID) */
    private static final ListCellKey KEY_HIDDEN_SOFZONE_ID = new ListCellKey("HIDDEN_SOFZONE_ID", new StringCellColumn(), false, false);

    /** lst_ShelfMnt(LST_MODIFY) */
    private static final ListCellKey KEY_LST_MODIFY = new ListCellKey("LST_MODIFY", new CheckBoxColumn(), true, true);

    /** lst_ShelfMnt(LST_DELETE) */
    private static final ListCellKey KEY_LST_DELETE = new ListCellKey("LST_DELETE", new CheckBoxColumn(), true, true);

    /** lst_ShelfMnt(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_ShelfMnt(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_ShelfMnt(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ShelfMnt(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ShelfMnt(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_ShelfMnt(LST_STOCK_QTY) */
    private static final ListCellKey KEY_LST_STOCK_QTY = new ListCellKey("LST_STOCK_QTY", new NumberCellColumn(0), true, false);

    /** lst_ShelfMnt(LST_ALLOCATION_QTY) */
    private static final ListCellKey KEY_LST_ALLOCATION_QTY = new ListCellKey("LST_ALLOCATION_QTY", new NumberCellColumn(0), true, false);

    /** lst_ShelfMnt(LST_MODIFIED_QTY) */
    private static final ListCellKey KEY_LST_MODIFIED_QTY = new ListCellKey("LST_MODIFIED_QTY", new NumberCellColumn(0), true, true);

    /** lst_ShelfMnt(LST_LAST_PICKING_DATE) */
    private static final ListCellKey KEY_LST_LAST_PICKING_DATE = new ListCellKey("LST_LAST_PICKING_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, true);

    /** lst_ShelfMnt(LST_STORAGE_DATE) */
    private static final ListCellKey KEY_LST_STORAGE_DATE = new ListCellKey("LST_STORAGE_DATE", new DateCellColumn(DATE_FORMAT.LONG, null), true, true);

    /** lst_ShelfMnt(LST_STORAGE_TIME) */
    private static final ListCellKey KEY_LST_STORAGE_TIME = new ListCellKey("LST_STORAGE_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, true);

    /** lst_ShelfMnt(LST_SOFTZONE_NAME) */
    private static final ListCellKey KEY_LST_SOFTZONE_NAME = new ListCellKey("LST_SOFTZONE_NAME", new StringCellColumn(), true, false);

    /** lst_ShelfMnt keys */
    private static final ListCellKey[] LST_SHELFMNT_KEYS = {
        KEY_HIDDEN_STOCK_ID,
        KEY_HIDDEN_LAST_UPDATE_DATE,
        KEY_HIDDEN_SOFZONE_ID,
        KEY_LST_MODIFY,
        KEY_LST_DELETE,
        KEY_LST_AREA_NO,
        KEY_LST_LOCATION_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOT_NO,
        KEY_LST_STOCK_QTY,
        KEY_LST_ALLOCATION_QTY,
        KEY_LST_MODIFIED_QTY,
        KEY_LST_LAST_PICKING_DATE,
        KEY_LST_STORAGE_DATE,
        KEY_LST_STORAGE_TIME,
        KEY_LST_SOFTZONE_NAME,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_ShelfMnt */
    private ScrollListCellModel _lcm_lst_ShelfMnt;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaStockMntBusiness()
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
        if (eventSource.equals("btn_SearchItemCode_Click"))
        {
            // process call.
            btn_SearchItemCode_Click_DlgBack(dialogParams);
        }
        else if (eventSource.equals("btn_SearchItemName_Click"))
        {
            // process call.
            btn_SearchItemName_Click_DlgBack(dialogParams);
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchItemCode_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItemCode_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_SearchItemName_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_SearchItemName_Click_Process();
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
    public void btn_Add_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Add_Click_Process();
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
        btn_Set_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllClear_Click_Process();
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

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_ShelfMnt.
        _lcm_lst_ShelfMnt = new ScrollListCellModel(lst_ShelfMnt, LST_SHELFMNT_KEYS, locale);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_MODIFY, true);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_DELETE, true);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_STOCK_QTY, true);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_ALLOCATION_QTY, true);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_MODIFIED_QTY, false);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_LAST_PICKING_DATE, false);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_STORAGE_DATE, false);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_STORAGE_TIME, false);
        _lcm_lst_ShelfMnt.setToolTipVisible(KEY_LST_SOFTZONE_NAME, true);
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
            _pdm_pul_Area.init(conn, AreaType.NOT_MOVING, StationType.ALL, "", true);
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
    private void lst_ShelfMnt_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
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
        else if (eventSource.equals("btn_Set_Click"))
        {
            // process call.
            btn_Set_Click_SetList();
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
        setFocus(pul_Area);
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
        txt_Location.setValue(null);
        lbl_In_LocationStyle.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
        _lcm_lst_ShelfMnt.clear();
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchItemCode_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemParams inparam = new LstItemParams();
        inparam.set(LstItemParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstItemParams.ITEM_CODE, txt_ItemCode.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItemCode_Click");
        redirect("/base/listbox/item/LstItem.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItemCode_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemParams outparam = new LstItemParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemParams.ITEM_NAME));

        // set focus.
        setFocus(txt_ItemCode);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_SearchItemName_Click_Process()
            throws Exception
    {
        // dialog parameters set.
        LstItemNameParams inparam = new LstItemNameParams();
        inparam.set(LstItemNameParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
        inparam.set(LstItemNameParams.ITEM_NAME, txt_ItemName.getValue());

        // show dialog.
        ForwardParameters forwardParam = inparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, "btn_SearchItemName_Click");
        redirect("/base/listbox/itemname/LstItemName.do", forwardParam, "/Progress.do");
    }

    /**
     *
     * @param dialogParams DialogParameters
     */
    private void btn_SearchItemName_Click_DlgBack(DialogParameters dialogParams)
            throws Exception
    {
        // output display.
        LstItemNameParams outparam = new LstItemNameParams(dialogParams);
        txt_ItemCode.setValue(outparam.get(LstItemNameParams.ITEM_CODE));
        txt_ItemName.setValue(outparam.get(LstItemNameParams.ITEM_NAME));

        // set focus.
        setFocus(txt_ItemName);
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
        FaStockMntDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaStockMntDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaStockMntDASCHParams inparam = new FaStockMntDASCHParams();
            inparam.set(FaStockMntDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaStockMntDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaStockMntDASCHParams.LOCATION_NO, txt_Location.getValue());
            inparam.set(FaStockMntDASCHParams.ITEM_CODE, txt_ItemCode.getValue());
            inparam.set(FaStockMntDASCHParams.ITEM_NAME, txt_ItemName.getValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_ShelfMnt.clear();

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

            // clear.
            btn_Set.setEnabled(true);
            btn_AllClear.setEnabled(true);

            for (int i = 1; i <= _lcm_lst_ShelfMnt.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_ShelfMnt.get(i);
                lst_ShelfMnt.setCurrentRow(i);
                clearLine.setValue(KEY_LST_MODIFY, Boolean.FALSE);
                clearLine.setValue(KEY_LST_DELETE, Boolean.FALSE);
                lst_ShelfMnt.setCellReadOnly(_lcm_lst_ShelfMnt.getColumnIndex(KEY_LST_STORAGE_DATE), true);
                lst_ShelfMnt.setCellReadOnly(_lcm_lst_ShelfMnt.getColumnIndex(KEY_LST_STORAGE_TIME), true);
                lst_ShelfMnt.setCellReadOnly(_lcm_lst_ShelfMnt.getColumnIndex(KEY_LST_MODIFIED_QTY), true);
                lst_ShelfMnt_SetLineToolTip(clearLine);
                _lcm_lst_ShelfMnt.set(i, clearLine);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ShelfMnt.clear();
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
        FaStockMntDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaStockMntDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_ShelfMnt.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ShelfMnt.getNewLine();
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(FaStockMntDASCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_HIDDEN_STOCK_ID, outparam.get(FaStockMntDASCHParams.STOCK_ID));
                line.setValue(KEY_LST_AREA_NO, outparam.get(FaStockMntDASCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(FaStockMntDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaStockMntDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(FaStockMntDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaStockMntDASCHParams.LOT_NO));
                line.setValue(KEY_LST_STOCK_QTY, outparam.get(FaStockMntDASCHParams.STOCK_QTY));
                line.setValue(KEY_LST_ALLOCATION_QTY, outparam.get(FaStockMntDASCHParams.ALLOCATION_QTY));
                line.setValue(KEY_LST_MODIFIED_QTY, outparam.get(FaStockMntDASCHParams.MODIFIED_QTY));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(FaStockMntDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(FaStockMntDASCHParams.STORAGE_TIME));
                line.setValue(KEY_LST_SOFTZONE_NAME, outparam.get(FaStockMntDASCHParams.SOFTZONE_NAME));
                line.setValue(KEY_LST_SOFTZONE_NAME, outparam.get(FaStockMntDASCHParams.ASRS_FLAG));
                viewState.setObject(ViewStateKeys.VS_DISP_AREA_NO, _pdm_pul_Area.getSelectedValue());
                viewState.setObject(ViewStateKeys.VS_DISP_LOCATION_NO, txt_Location.getValue());
                viewState.setObject(ViewStateKeys.VS_DISP_ITEM_CODE, txt_ItemCode.getValue());
                viewState.setObject(ViewStateKeys.VS_DISP_ITEM_NAME, txt_ItemName.getValue());
                lst_ShelfMnt_SetLineToolTip(line);
                _lcm_lst_ShelfMnt.add(line);
            }

            // clear.
            btn_Set.setEnabled(true);
            btn_AllClear.setEnabled(true);

            for (int i = 1; i <= _lcm_lst_ShelfMnt.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_ShelfMnt.get(i);
                lst_ShelfMnt.setCurrentRow(i);
                clearLine.setValue(KEY_LST_MODIFY, Boolean.FALSE);
                clearLine.setValue(KEY_LST_DELETE, Boolean.FALSE);
                lst_ShelfMnt.setCellReadOnly(_lcm_lst_ShelfMnt.getColumnIndex(KEY_LST_STORAGE_DATE), true);
                lst_ShelfMnt.setCellReadOnly(_lcm_lst_ShelfMnt.getColumnIndex(KEY_LST_STORAGE_TIME), true);
                lst_ShelfMnt.setCellReadOnly(_lcm_lst_ShelfMnt.getColumnIndex(KEY_LST_MODIFIED_QTY), true);
                lst_ShelfMnt_SetLineToolTip(clearLine);
                _lcm_lst_ShelfMnt.set(i, clearLine);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ShelfMnt.clear();
            disposeDasch();
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
        _pdm_pul_Area.setSelectedValue(null);
        txt_Location.setValue(null);
        txt_ItemCode.setValue(null);
        txt_ItemName.setValue(null);
        lbl_In_LocationStyle.setValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Add_Click_Process()
            throws Exception
    {
        // input validation.
        pul_Area.validate(this, false);
        txt_Location.validate(this, true);

        try
        {
            // set ViewState parameters.
            viewState.setObject(ViewStateKeys.VS_AREA_NO, _pdm_pul_Area.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_LOCATION_NO, txt_Location.getValue());

            // forward.
            forward("/stock/fastockmnt/FaStockMnt2.do");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Set_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_ShelfMnt.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_ShelfMnt.get(i);
            if (!(checkline.isAppend() || checkline.isEdited()))
            {
                continue;
            }

            existEditedRow = true;
            lst_ShelfMnt.setCurrentRow(i);
            lst_ShelfMnt.validate(checkline.getIndex(KEY_LST_MODIFIED_QTY), false);
            lst_ShelfMnt.validate(checkline.getIndex(KEY_LST_LAST_PICKING_DATE), false);
            lst_ShelfMnt.validate(checkline.getIndex(KEY_LST_STORAGE_DATE), false);
            lst_ShelfMnt.validate(checkline.getIndex(KEY_LST_STORAGE_TIME), false);
        }
        if (!existEditedRow)
        {
            message.setMsgResourceKey("6003001");
            return;
        }

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        FaStockMntSCH sch = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            sch = new FaStockMntSCH(conn, this.getClass(), locale, ui);

            // set input parameters.
            List<ScheduleParams> inparamList = new ArrayList<ScheduleParams>();
            for (int i = 1; i <= _lcm_lst_ShelfMnt.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_ShelfMnt.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                FaStockMntSCHParams lineparam = new FaStockMntSCHParams();
                lineparam.setProcessFlag(ProcessFlag.UPDATE);
                lineparam.setRowIndex(i);
                lineparam.set(FaStockMntSCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
                lineparam.set(FaStockMntSCHParams.AREA_NO, line.getValue(KEY_LST_AREA_NO));
                lineparam.set(FaStockMntSCHParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                lineparam.set(FaStockMntSCHParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                lineparam.set(FaStockMntSCHParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                lineparam.set(FaStockMntSCHParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
                lineparam.set(FaStockMntSCHParams.STOCK_QTY, line.getValue(KEY_LST_STOCK_QTY));
                lineparam.set(FaStockMntSCHParams.ALLOCATION_QTY, line.getValue(KEY_LST_ALLOCATION_QTY));
                lineparam.set(FaStockMntSCHParams.MODIFIED_QTY, line.getValue(KEY_LST_MODIFIED_QTY));
                lineparam.set(FaStockMntSCHParams.LAST_RETRIEVAL_DATE, line.getValue(KEY_LST_LAST_PICKING_DATE));
                lineparam.set(FaStockMntSCHParams.STORAGE_DATE, line.getValue(KEY_LST_STORAGE_DATE));
                lineparam.set(FaStockMntSCHParams.STORAGE_TIME, line.getValue(KEY_LST_STORAGE_TIME));
                lineparam.set(FaStockMntSCHParams.SOFTZONE_NAME, line.getValue(KEY_LST_SOFTZONE_NAME));
                lineparam.set(FaStockMntSCHParams.STOCK_ID, line.getValue(KEY_HIDDEN_STOCK_ID));
                lineparam.set(FaStockMntSCHParams.LAST_UPDATE_DATE, line.getValue(KEY_HIDDEN_LAST_UPDATE_DATE));
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
                _lcm_lst_ShelfMnt.resetEditRow();
                _lcm_lst_ShelfMnt.resetHighlight();
                _lcm_lst_ShelfMnt.addHighlight(sch.getErrorRowIndex(), ControlColor.Warning);
                return;
            }

            // write part11 log.
            for (int i = 1; i <= _lcm_lst_ShelfMnt.size(); i++)
            {
                ListCellLine line = _lcm_lst_ShelfMnt.get(i);
                lst_ShelfMnt.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                part11List.add(line.getViewString(KEY_LST_AREA_NO), "");
                part11List.add(line.getViewString(KEY_LST_LOCATION_NO), "");
                part11List.add(line.getViewString(KEY_LST_ITEM_CODE), "");
                part11List.add(line.getViewString(KEY_LST_LOT_NO), "");
                part11List.add(line.getViewString(KEY_LST_STOCK_QTY), "");
                part11List.add(line.getViewString(KEY_LST_ALLOCATION_QTY), "");
                part11List.add(line.getViewString(KEY_LST_MODIFIED_QTY), "");
                part11List.add(line.getViewString(KEY_LST_LAST_PICKING_DATE), "");
                part11List.add(line.getViewString(KEY_LST_STORAGE_DATE), line.getViewString(KEY_LST_STORAGE_TIME), "");
                part11List.add(line.getViewString(KEY_HIDDEN_SOFZONE_ID), "");

                if (lst_ShelfMnt.getChecked(_lcm_lst_ShelfMnt.getColumnIndex(KEY_LST_MODIFY)))
                {
                    part11List.add(line.getViewString(KEY_LST_MODIFIED_QTY), "");
                    part11List.add(line.getViewString(KEY_LST_LAST_PICKING_DATE), "");
                    part11List.add(line.getViewString(KEY_LST_STORAGE_DATE), line.getViewString(KEY_LST_STORAGE_TIME), "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_MODIFY), part11List);
            }

            // commit.
            conn.commit();
            message.setMsgResourceKey(sch.getMessage());

            // reset editing row.
            _lcm_lst_ShelfMnt.resetEditRow();
            _lcm_lst_ShelfMnt.resetHighlight();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            return;
        }
        finally
        {
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }

        boolean isSuccess = false;
        FaStockMntDASCH dasch = null;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Set_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaStockMntDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaStockMntDASCHParams inparam = new FaStockMntDASCHParams();
            inparam.set(FaStockMntDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);
            inparam.set(FaStockMntDASCHParams.AREA_NO, viewState.getObject(ViewStateKeys.VS_DISP_AREA_NO));
            inparam.set(FaStockMntDASCHParams.ITEM_CODE, viewState.getObject(ViewStateKeys.VS_DISP_ITEM_CODE));
            inparam.set(FaStockMntDASCHParams.ITEM_NAME, viewState.getObject(ViewStateKeys.VS_DISP_ITEM_NAME));
            inparam.set(FaStockMntDASCHParams.LOCATION_NO, viewState.getObject(ViewStateKeys.VS_DISP_LOCATION_NO));

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _lcm_lst_ShelfMnt.clear();

            if (count == 0)
            {
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // set list.
            session.setAttribute(_KEY_DASCH, dasch);
            _pager.setMax(count);
            btn_Set_Click_SetList();
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ShelfMnt.clear();
        }
        finally
        {
            if (!isSuccess)
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
    private void btn_Set_Click_SetList()
            throws Exception
    {
        FaStockMntDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaStockMntDASCH)session.getAttribute(_KEY_DASCH);
            _lcm_lst_ShelfMnt.clear();

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ShelfMnt.getNewLine();
                line.setValue(KEY_HIDDEN_STOCK_ID, outparam.get(FaStockMntDASCHParams.STOCK_ID));
                line.setValue(KEY_HIDDEN_LAST_UPDATE_DATE, outparam.get(FaStockMntDASCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_AREA_NO, outparam.get(FaStockMntDASCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(FaStockMntDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaStockMntDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(FaStockMntDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaStockMntDASCHParams.LOT_NO));
                line.setValue(KEY_LST_STOCK_QTY, outparam.get(FaStockMntDASCHParams.STOCK_QTY));
                line.setValue(KEY_LST_ALLOCATION_QTY, outparam.get(FaStockMntDASCHParams.ALLOCATION_QTY));
                line.setValue(KEY_LST_MODIFIED_QTY, outparam.get(FaStockMntDASCHParams.MODIFIED_QTY));
                line.setValue(KEY_LST_LAST_PICKING_DATE, outparam.get(FaStockMntDASCHParams.LAST_RETRIEVAL_DATE));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(FaStockMntDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(FaStockMntDASCHParams.STORAGE_TIME));
                line.setValue(KEY_LST_SOFTZONE_NAME, outparam.get(FaStockMntDASCHParams.SOFTZONE_NAME));
                line.setValue(KEY_LST_SOFTZONE_NAME, outparam.get(FaStockMntDASCHParams.ASRS_FLAG));
                line.setValue(KEY_HIDDEN_SOFZONE_ID, outparam.get(FaStockMntDASCHParams.SOFTZONE_ID));
                lst_ShelfMnt_SetLineToolTip(line);
                _lcm_lst_ShelfMnt.add(line);
            }

            // clear.
            for (int i = 1; i <= _lcm_lst_ShelfMnt.size(); i++)
            {
                ListCellLine clearLine = _lcm_lst_ShelfMnt.get(i);
                lst_ShelfMnt.setCurrentRow(i);
                clearLine.setValue(KEY_LST_MODIFY, Boolean.FALSE);
                clearLine.setValue(KEY_LST_DELETE, Boolean.FALSE);
                lst_ShelfMnt.setCellReadOnly(_lcm_lst_ShelfMnt.getColumnIndex(KEY_LST_MODIFIED_QTY), true);
                clearLine.setValue(KEY_LST_LAST_PICKING_DATE, null);
                lst_ShelfMnt.setCellReadOnly(_lcm_lst_ShelfMnt.getColumnIndex(KEY_LST_STORAGE_DATE), true);
                lst_ShelfMnt.setCellReadOnly(_lcm_lst_ShelfMnt.getColumnIndex(KEY_LST_STORAGE_TIME), true);
                lst_ShelfMnt_SetLineToolTip(clearLine);
                _lcm_lst_ShelfMnt.set(i, clearLine);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ShelfMnt.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllClear_Click_Process()
            throws Exception
    {
        // clear.
        _lcm_lst_ShelfMnt.clear();
        _pager.clear();
        btn_Set.setEnabled(false);
        btn_AllClear.setEnabled(false);
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
