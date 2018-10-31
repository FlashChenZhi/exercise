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
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.RadioButtonGroup;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.ui.control.RadioButton;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.ParamKey;
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
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.listbox.item.LstItemParams;
import jp.co.daifuku.wms.base.listbox.itemname.LstItemNameParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.stock.dasch.FaItemWorkingInquiryDASCH;
import jp.co.daifuku.wms.stock.dasch.FaItemWorkingInquiryDASCHParams;
import jp.co.daifuku.wms.stock.exporter.StockByItemListParams;
import jp.co.daifuku.wms.stock.exporter.TotalStockByItemAndLotListParams;
import jp.co.daifuku.wms.stock.exporter.TotalStockByItemListParams;

/**
 * 商品別在庫照会の画面処理を行います。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
@SuppressWarnings("serial")
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

    //DFKLOOK:ここから修正
    // 商品名称
    private static final int LST_ITEM_NAME = 3;

    // ロットNo.
    private static final int LST_LOT_NO = 4;

    // 入庫日時
    private static final int LST_STORAGE_DATETIME = 5;

    // エリア
    private static final int LST_AREA_NO = 6;

    // 棚
    private static final int LST_LOCATION_NO = 7;

    // 総在庫数
    private static final int LST_TOTAL_STOCK_QTY = 8;
    
    // 在庫数
    private static final int LST_STOCK_QTY = 9;

    // 棚状態
    private static final int LST_LOCATION_STATUS = 10;

    //DFKLOOK:ここまで修正

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
    private ListCellModel _lcm_lst_FaStockInquiry;

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
            //DFKLOOK:ここから修正
            // process call.
            if (rdo_Search_CountByItemCode.getChecked())
            {
                btn_Print_Click_Process(false);
            }
            else if (rdo_Search_CountByItemCodeAndL.getChecked())
            {
                btn_Print_Click_Process_ItemLot(false);
            }
            else
            {
                btn_Print_Click_Process_DetailItem(false);
            }
            //DFKLOOK:ここまで修正
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_FromItemCode_EntEvent_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        if (StringUtil.isBlank(txt_FromItemCode_EntEvent.getValue()))
        {
            setFocus(txt_FromItemName);
            return;
        }

        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            Item item =
                    ItemController.getItemInfo(txt_FromItemCode_EntEvent.getText(), WmsParam.DEFAULT_CONSIGNOR_CODE,
                            conn);

            if (item == null)
            {
                // 6023021 = 商品コードがマスタに登録されていません。
                message.setMsgResourceKey("6023021");
                setFocus(txt_FromItemCode_EntEvent);
                return;
            }

            // output display.
            txt_FromItemName.setValue(item.getItemName());

            // set focus.
            setFocus(txt_ToItemCode_EntEvent);
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
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_FromItemCode_EntEvent_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_FromItemCode_EntEvent_AutoCompleteItemClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_FromItemCode_EntEvent_AutoComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_FromItemCode_EntEvent_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_FromItemCode_EntEvent_InputComplete(ActionEvent e)
            throws Exception
    {
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
    public void btn_SearchFromItemCode_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_FromItemName_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_FromItemName_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_FromItemName_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_FromItemName_AutoCompleteItemClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_FromItemName_AutoComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_FromItemName_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_FromItemName_InputComplete(ActionEvent e)
            throws Exception
    {
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
    public void btn_SearchFromItemName_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_ToItemCode_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ToItemCode_EntEvent_EnterKey(ActionEvent e)
            throws Exception
    {
        // DFKLOOK:ここから修正
        if (StringUtil.isBlank(txt_ToItemCode_EntEvent.getValue()))
        {
            setFocus(txt_ToItemName);
            return;
        }

        Connection conn = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);

            Item item =
                    ItemController.getItemInfo(txt_ToItemCode_EntEvent.getText(), WmsParam.DEFAULT_CONSIGNOR_CODE, conn);

            if (item == null)
            {
                // 6023021 = 商品コードがマスタに登録されていません。
                message.setMsgResourceKey("6023021");
                setFocus(txt_ToItemCode_EntEvent);
                return;
            }

            // output display.
            txt_ToItemName.setValue(item.getItemName());

            // set focus.
            setFocus(rdo_Search_CountByItemCode);
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
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ToItemCode_EntEvent_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ToItemCode_EntEvent_AutoCompleteItemClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ToItemCode_EntEvent_AutoComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ToItemCode_EntEvent_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ToItemCode_EntEvent_InputComplete(ActionEvent e)
            throws Exception
    {
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
    public void btn_SearchToItemCode_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_ToItemName_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ToItemName_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ToItemName_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ToItemName_AutoCompleteItemClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ToItemName_AutoComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ToItemName_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void txt_ToItemName_InputComplete(ActionEvent e)
            throws Exception
    {
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
    public void btn_SearchToItemName_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_Type_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Search_CountByItemCode_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Search_CountByItemCode_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Search_CountByItemCodeAndL_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Search_CountByItemCodeAndL_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Search_DetailsPerItemCode_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void rdo_Search_DetailsPerItemCode_Server(ActionEvent e)
            throws Exception
    {
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
    public void btn_Display_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Print_Click(ActionEvent e)
            throws Exception
    {
        //DFKLOOK:ここから修正
        // process call.
        if (rdo_Search_CountByItemCode.getChecked())
        {
            btn_Print_Click_Process(true);
        }
        else if (rdo_Search_CountByItemCodeAndL.getChecked())
        {
            btn_Print_Click_Process_ItemLot(true);
        }
        else
        {
            btn_Print_Click_Process_DetailItem(true);
        }
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Print_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Preview_Click(ActionEvent e)
            throws Exception
    {
        //DFKLOOK:ここから修正
        // process call.
        if (rdo_Search_CountByItemCode.getChecked())
        {
            btn_Preview_Click_Process();
        }
        else if (rdo_Search_CountByItemCodeAndL.getChecked())
        {
            btn_Preview_Click_Process_ItemLot();
        }
        else
        {
            btn_Preview_Click_Process_DetailItem();
        }
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Preview_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_XLS_Click(ActionEvent e)
            throws Exception
    {
        //DFKLOOK:ここから修正
        // process call.
        if (rdo_Search_CountByItemCode.getChecked())
        {
            btn_XLS_Click_Process();
        }
        else if (rdo_Search_CountByItemCodeAndL.getChecked())
        {
            btn_XLS_Click_Process_ItemLot();
        }
        else
        {
            btn_XLS_Click_Process_DetailItem();
        }
        //DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_XLS_Server(ActionEvent e)
            throws Exception
    {
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
    public void btn_Clear_Server(ActionEvent e)
            throws Exception
    {
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
    public void lst_FaStockInquiry_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_FaStockInquiry_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_FaStockInquiry_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_FaStockInquiry_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_FaStockInquiry_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_FaStockInquiry_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_FaStockInquiry_Change(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Help_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void tab_Click(ActionEvent e)
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
    /**
     * リスト表示項目を切り替えます。
     */
    protected void changeList()
    {
        if (rdo_Search_CountByItemCode.getChecked())
        {
            // 商品名称
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_ITEM_NAME, false);
            // ロットNo.
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_LOT_NO, true);
            // 入庫日時
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_STORAGE_DATETIME, true);
            // エリア
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_AREA_NO, true);
            // 棚
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_LOCATION_NO, true);
            // 総在庫数
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_TOTAL_STOCK_QTY, false);
            // 在庫数
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_STOCK_QTY, true);      
            // 棚状態
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_LOCATION_STATUS, true);
        }
        else if (rdo_Search_CountByItemCodeAndL.getChecked())
        {
            // 商品名称
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_ITEM_NAME, false);
            // ロットNo.
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_LOT_NO, false);
            // 入庫日時
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_STORAGE_DATETIME, true);
            // エリア
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_AREA_NO, true);
            // 棚
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_LOCATION_NO, true);
            // 総在庫数
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_TOTAL_STOCK_QTY, false);
            // 在庫数
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_STOCK_QTY, true);
            // 棚状態
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_LOCATION_STATUS, true);
        }
        else
        {
            // 商品名称
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_ITEM_NAME, true);
            // ロットNo.
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_LOT_NO, false);
            // 入庫日時
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_STORAGE_DATETIME, false);
            // エリア
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_AREA_NO, false);
            // 棚
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_LOCATION_NO, false);
            // 総在庫数
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_TOTAL_STOCK_QTY, true);
            // 在庫数
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_STOCK_QTY, false);
            // 棚状態
            _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_LOCATION_STATUS, false);
        }
    }

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
        _lcm_lst_FaStockInquiry = new ListCellModel(lst_FaStockInquiry, LST_FASTOCKINQUIRY_KEYS, locale);
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

            //DFKLOOK:ここから修正
            // load pul_Area.
            _pdm_pul_Area.init(conn, AreaType.ALL, StationType.STORAGE, "", true);
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
    private void lst_FaStockInquiry_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);

        //DFKLOOK:ここから修正
        line.addToolTip("LBL-W0130", KEY_HIDDEN_ITEM_NAME);
        //DFKLOOK:ここまで修正
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

        //DFKLOOK:ここから修正
        // リスト列を非表示
        // ロットNo.
        _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_LOT_NO, true);
        // 入庫日時
        _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_STORAGE_DATETIME, true);
        // エリア
        _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_AREA_NO, true);
        // 棚
        _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_LOCATION_NO, true);
        // 総在庫数
        _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_TOTAL_STOCK_QTY, false);
        // 在庫数
        _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_STOCK_QTY, true); 
        // 棚状態
        _lcm_lst_FaStockInquiry.getListCell().setColumnHidden(LST_LOCATION_STATUS, true);
        //DFKLOOK:ここまで修正
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
        //DFKLOOK:ここから修正
        txt_ToItemCode_EntEvent.validate(this, false);
        txt_FromItemCode_EntEvent.validate(this, false);
        //DFKLOOK:ここまで修正
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
                line.setValue(KEY_LST_STOCK_QTY, outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));
                lst_FaStockInquiry_SetLineToolTip(line);
                _lcm_lst_FaStockInquiry.add(line);
            }


            //DFKLOOK:ここから修正
            // リスト表示列を切り替え
            changeList();
            //DFKLOOK:ここまで修正
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
            exporter = factory.newPrinterExporter("TotalStockByItemList", false);
            exporter.open();

            //DFKLOOK:ここから修正
            String areaName = getAreaName(conn, _pdm_pul_Area.getSelectedValue().toString());
            String[] loc =
                    WmsFormatter.getFromTo(txt_FromItemCode_EntEvent.getText(), txt_ToItemCode_EntEvent.getText());
            //DFKLOOK:ここまで修正

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                TotalStockByItemListParams expparam = new TotalStockByItemListParams();
                expparam.set(TotalStockByItemListParams.DFK_DS_NO,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_DS_NO));
                expparam.set(TotalStockByItemListParams.DFK_USER_ID,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_ID));
                expparam.set(TotalStockByItemListParams.DFK_USER_NAME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(TotalStockByItemListParams.SYS_DAY, outparam.get(FaItemWorkingInquiryDASCHParams.SYS_DAY));
                expparam.set(TotalStockByItemListParams.SYS_TIME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.SYS_TIME));

                //DFKLOOK:ここから修正
                expparam.set(TotalStockByItemListParams.WAREHOUSE, areaName);
                expparam.set(TotalStockByItemListParams.FROM_ITEM_CODE, loc[0]);
                expparam.set(TotalStockByItemListParams.TO_ITEM_CODE, loc[1]);
                //DFKLOOK:ここまで修正

                expparam.set(TotalStockByItemListParams.ITEM_CODE,
                        outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(TotalStockByItemListParams.ITEM_NAME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(TotalStockByItemListParams.TOTAL_STOCK_QTY,
                        outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));
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
            exporter = factory.newPVExporter("TotalStockByItemList", getSession());
            File downloadFile = exporter.open();

            //DFKLOOK:ここから修正
            String areaName = getAreaName(conn, _pdm_pul_Area.getSelectedValue().toString());
            String[] loc =
                    WmsFormatter.getFromTo(txt_FromItemCode_EntEvent.getText(), txt_ToItemCode_EntEvent.getText());
            //DFKLOOK:ここまで修正

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                TotalStockByItemListParams expparam = new TotalStockByItemListParams();
                expparam.set(TotalStockByItemListParams.DFK_DS_NO,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_DS_NO));
                expparam.set(TotalStockByItemListParams.DFK_USER_ID,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_ID));
                expparam.set(TotalStockByItemListParams.DFK_USER_NAME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(TotalStockByItemListParams.SYS_DAY, outparam.get(FaItemWorkingInquiryDASCHParams.SYS_DAY));
                expparam.set(TotalStockByItemListParams.SYS_TIME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.SYS_TIME));

                //DFKLOOK:ここから修正
                expparam.set(TotalStockByItemListParams.WAREHOUSE, areaName);
                expparam.set(TotalStockByItemListParams.FROM_ITEM_CODE, loc[0]);
                expparam.set(TotalStockByItemListParams.TO_ITEM_CODE, loc[1]);
                //DFKLOOK:ここまで修正

                expparam.set(TotalStockByItemListParams.ITEM_CODE,
                        outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(TotalStockByItemListParams.ITEM_NAME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(TotalStockByItemListParams.TOTAL_STOCK_QTY,
                        outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));
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
            exporter = factory.newExcelExporter("TotalStockByItemList", getSession());
            File downloadFile = exporter.open();

            //DFKLOOK:ここから修正
            String areaName = getAreaName(conn, _pdm_pul_Area.getSelectedValue().toString());
            //DFKLOOK:ここまで修正

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                TotalStockByItemListParams expparam = new TotalStockByItemListParams();

                //DFKLOOK:ここから修正
                expparam.set(TotalStockByItemListParams.WAREHOUSE, areaName);
                //DFKLOOK:ここまで修正

                expparam.set(TotalStockByItemListParams.ITEM_CODE,
                        outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(TotalStockByItemListParams.ITEM_NAME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(TotalStockByItemListParams.TOTAL_STOCK_QTY,
                        outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));
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

    //DFKLOOK:ここから修正
    /**
     * 総括(商品コード、ロットNo.別集計)用
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Print_Click_Process_ItemLot(boolean confirm)
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
            exporter = factory.newPrinterExporter("TotalStockByItemAndLotList", false);
            exporter.open();

            //DFKLOOK:ここから修正
            String areaName = getAreaName(conn, _pdm_pul_Area.getSelectedValue().toString());
            String[] loc =
                    WmsFormatter.getFromTo(txt_FromItemCode_EntEvent.getText(), txt_ToItemCode_EntEvent.getText());
            //DFKLOOK:ここまで修正

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                TotalStockByItemAndLotListParams expparam = new TotalStockByItemAndLotListParams();
                expparam.set(TotalStockByItemAndLotListParams.DFK_DS_NO,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_DS_NO));
                expparam.set(TotalStockByItemAndLotListParams.DFK_USER_ID,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_ID));
                expparam.set(TotalStockByItemAndLotListParams.DFK_USER_NAME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(TotalStockByItemAndLotListParams.SYS_DAY,
                        outparam.get(FaItemWorkingInquiryDASCHParams.SYS_DAY));
                expparam.set(TotalStockByItemAndLotListParams.SYS_TIME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.SYS_TIME));

                //DFKLOOK:ここから修正
                expparam.set(TotalStockByItemAndLotListParams.WAREHOUSE, areaName);
                expparam.set(TotalStockByItemAndLotListParams.FROM_ITEM_CODE, loc[0]);
                expparam.set(TotalStockByItemAndLotListParams.TO_ITEM_CODE, loc[1]);
                //DFKLOOK:ここまで修正

                expparam.set(TotalStockByItemAndLotListParams.ITEM_CODE,
                        outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(TotalStockByItemAndLotListParams.ITEM_NAME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(TotalStockByItemAndLotListParams.LOT_NO,
                        outparam.get(FaItemWorkingInquiryDASCHParams.LOT_NO));
                expparam.set(TotalStockByItemAndLotListParams.TOTAL_STOCK_QTY,
                        outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));
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
     * 総括(商品コード、ロットNo.別集計)用
     *
     * @throws Exception
     */
    private void btn_Preview_Click_Process_ItemLot()
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
            exporter = factory.newPVExporter("TotalStockByItemAndLotList", getSession());
            File downloadFile = exporter.open();

            //DFKLOOK:ここから修正
            String areaName = getAreaName(conn, _pdm_pul_Area.getSelectedValue().toString());
            String[] loc =
                    WmsFormatter.getFromTo(txt_FromItemCode_EntEvent.getText(), txt_ToItemCode_EntEvent.getText());
            //DFKLOOK:ここまで修正

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                TotalStockByItemAndLotListParams expparam = new TotalStockByItemAndLotListParams();
                expparam.set(TotalStockByItemAndLotListParams.DFK_DS_NO,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_DS_NO));
                expparam.set(TotalStockByItemAndLotListParams.DFK_USER_ID,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_ID));
                expparam.set(TotalStockByItemAndLotListParams.DFK_USER_NAME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(TotalStockByItemAndLotListParams.SYS_DAY,
                        outparam.get(FaItemWorkingInquiryDASCHParams.SYS_DAY));
                expparam.set(TotalStockByItemAndLotListParams.SYS_TIME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.SYS_TIME));

                //DFKLOOK:ここから修正
                expparam.set(TotalStockByItemAndLotListParams.WAREHOUSE, areaName);
                expparam.set(TotalStockByItemAndLotListParams.FROM_ITEM_CODE, loc[0]);
                expparam.set(TotalStockByItemAndLotListParams.TO_ITEM_CODE, loc[1]);
                //DFKLOOK:ここまで修正

                expparam.set(TotalStockByItemAndLotListParams.ITEM_CODE,
                        outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(TotalStockByItemAndLotListParams.ITEM_NAME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(TotalStockByItemAndLotListParams.LOT_NO,
                        outparam.get(FaItemWorkingInquiryDASCHParams.LOT_NO));
                expparam.set(TotalStockByItemAndLotListParams.TOTAL_STOCK_QTY,
                        outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));
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
     * 総括(商品コード、ロットNo.別集計)用
     *
     * @throws Exception
     */
    private void btn_XLS_Click_Process_ItemLot()
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
            exporter = factory.newExcelExporter("TotalStockByItemAndLotList", getSession());
            File downloadFile = exporter.open();


            //DFKLOOK:ここから修正
            String areaName = getAreaName(conn, _pdm_pul_Area.getSelectedValue().toString());
            //DFKLOOK:ここまで修正

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                TotalStockByItemAndLotListParams expparam = new TotalStockByItemAndLotListParams();

                //DFKLOOK:ここから修正
                expparam.set(TotalStockByItemAndLotListParams.WAREHOUSE, areaName);
                //DFKLOOK:ここまで修正

                expparam.set(TotalStockByItemAndLotListParams.TO_ITEM_CODE,
                        outparam.get(FaItemWorkingInquiryDASCHParams.TO_ITEM_CODE));
                expparam.set(TotalStockByItemAndLotListParams.ITEM_CODE,
                        outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(TotalStockByItemAndLotListParams.ITEM_NAME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(TotalStockByItemAndLotListParams.LOT_NO,
                        outparam.get(FaItemWorkingInquiryDASCHParams.LOT_NO));
                expparam.set(TotalStockByItemAndLotListParams.TOTAL_STOCK_QTY,
                        outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));
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
     * 明細(商品コード別)用
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Print_Click_Process_DetailItem(boolean confirm)
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

            //DFKLOOK:ここから修正
            // 帳票のグループ印字対応
            Params previousRecord = null;
            boolean gotRecordFromDasch = false;
            ParamKey itemKey = FaItemWorkingInquiryDASCHParams.ITEM_CODE;
            int currentRow = 1;
            // 1ページ最大行数
            int maxRow = 31;
            // 明細行数
            int detailRow = 2;
            // 集計行数
            int grpRow = 1;

            String areaName = getAreaName(conn, _pdm_pul_Area.getSelectedValue().toString());
            String[] loc =
                    WmsFormatter.getFromTo(txt_FromItemCode_EntEvent.getText(), txt_ToItemCode_EntEvent.getText());
            //DFKLOOK:ここまで修正

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();

                // DFKLOOK start 帳票のグループ印字対応
                if (gotRecordFromDasch)
                {
                    boolean itemCodeWasChanged = !outparam.getString(itemKey).equals(previousRecord.getString(itemKey));
                    previousRecord.set(StockByItemListParams.IS_LAST_ITEM_CODE_IN_GROUP, itemCodeWasChanged);

                    // ページの最後の行の場合も線を引く
                    if (maxRow <= currentRow)
                    {
                        previousRecord.set(StockByItemListParams.IS_LAST_ITEM_CODE_IN_GROUP, true);
                        currentRow = 1;
                    }
                    if (itemCodeWasChanged)
                    {
                        // 集計行分+
                        currentRow += grpRow;
                        // ページの最後が集計行の場合
                        if (maxRow <= currentRow)
                        {
                            currentRow = 1;
                        }
                    }
                    if (!exporter.write(previousRecord))
                    {
                        break;
                    }
                }
                // DFKLOOK end

                StockByItemListParams expparam = new StockByItemListParams();
                expparam.set(StockByItemListParams.DFK_DS_NO, outparam.get(FaItemWorkingInquiryDASCHParams.DFK_DS_NO));
                expparam.set(StockByItemListParams.DFK_USER_ID,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_ID));
                expparam.set(StockByItemListParams.DFK_USER_NAME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(StockByItemListParams.SYS_DAY, outparam.get(FaItemWorkingInquiryDASCHParams.SYS_DAY));
                expparam.set(StockByItemListParams.SYS_TIME, outparam.get(FaItemWorkingInquiryDASCHParams.SYS_TIME));

                //DFKLOOK:ここから修正
                expparam.set(StockByItemListParams.SEARCH_KIND, areaName);
                expparam.set(StockByItemListParams.FROM_ITEM_CODE, loc[0]);
                expparam.set(StockByItemListParams.TO_ITEM_CODE, loc[1]);
                //DFKLOOK:ここまで修正

                expparam.set(StockByItemListParams.ITEM_CODE, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(StockByItemListParams.ITEM_NAME, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(StockByItemListParams.LOT_NO, outparam.get(FaItemWorkingInquiryDASCHParams.LOT_NO));
                expparam.set(StockByItemListParams.STORAGE_DATE,
                        outparam.get(FaItemWorkingInquiryDASCHParams.STORAGE_DATETIME));
                expparam.set(StockByItemListParams.AREA_NO, outparam.get(FaItemWorkingInquiryDASCHParams.AREA_NO));
                expparam.set(StockByItemListParams.LOCATION_NO,
                        outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_NO));
                expparam.set(StockByItemListParams.LOCATION_STATUS,
                        outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_STATUS));
                expparam.set(StockByItemListParams.STOCK_QTY,
                        outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));

                // DFKLOOK start 帳票のグループ印字対応
                previousRecord = expparam;
                gotRecordFromDasch = true;
                // 明細行分+
                currentRow += detailRow;
            }

            if (previousRecord != null)
            {
                // write last record.
                previousRecord.set(StockByItemListParams.IS_LAST_ITEM_CODE_IN_GROUP, true);
                exporter.write(previousRecord);
            }
            // DFKLOOK end

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
     * 明細(商品コード別)用
     *
     * @throws Exception
     */
    private void btn_Preview_Click_Process_DetailItem()
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

            //DFKLOOK:ここから修正
            // 帳票のグループ印字対応
            Params previousRecord = null;
            boolean gotRecordFromDasch = false;
            ParamKey itemKey = FaItemWorkingInquiryDASCHParams.ITEM_CODE;
            int currentRow = 1;
            // 1ページ最大行数
            int maxRow = 31;
            // 明細行数
            int detailRow = 2;
            // 集計行数
            int grpRow = 1;

            String areaName = getAreaName(conn, _pdm_pul_Area.getSelectedValue().toString());
            String[] loc =
                    WmsFormatter.getFromTo(txt_FromItemCode_EntEvent.getText(), txt_ToItemCode_EntEvent.getText());
            //DFKLOOK:ここまで修正

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();

                // DFKLOOK start 帳票のグループ印字対応
                if (gotRecordFromDasch)
                {
                    boolean itemCodeWasChanged = !outparam.getString(itemKey).equals(previousRecord.getString(itemKey));
                    previousRecord.set(StockByItemListParams.IS_LAST_ITEM_CODE_IN_GROUP, itemCodeWasChanged);

                    // ページの最後の行の場合も線を引く
                    if (maxRow <= currentRow)
                    {
                        previousRecord.set(StockByItemListParams.IS_LAST_ITEM_CODE_IN_GROUP, true);
                        currentRow = 1;
                    }
                    if (itemCodeWasChanged)
                    {
                        // 集計行分+
                        currentRow += grpRow;
                        // ページの最後が集計行の場合
                        if (maxRow <= currentRow)
                        {
                            currentRow = 1;
                        }
                    }
                    if (!exporter.write(previousRecord))
                    {
                        break;
                    }
                }
                // DFKLOOK end

                StockByItemListParams expparam = new StockByItemListParams();
                expparam.set(StockByItemListParams.DFK_DS_NO, outparam.get(FaItemWorkingInquiryDASCHParams.DFK_DS_NO));
                expparam.set(StockByItemListParams.DFK_USER_ID,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_ID));
                expparam.set(StockByItemListParams.DFK_USER_NAME,
                        outparam.get(FaItemWorkingInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(StockByItemListParams.SYS_DAY, outparam.get(FaItemWorkingInquiryDASCHParams.SYS_DAY));
                expparam.set(StockByItemListParams.SYS_TIME, outparam.get(FaItemWorkingInquiryDASCHParams.SYS_TIME));

                //DFKLOOK:ここから修正
                expparam.set(StockByItemListParams.SEARCH_KIND, areaName);
                expparam.set(StockByItemListParams.FROM_ITEM_CODE, loc[0]);
                expparam.set(StockByItemListParams.TO_ITEM_CODE, loc[1]);
                //DFKLOOK:ここまで修正

                expparam.set(StockByItemListParams.ITEM_CODE, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_CODE));
                expparam.set(StockByItemListParams.ITEM_NAME, outparam.get(FaItemWorkingInquiryDASCHParams.ITEM_NAME));
                expparam.set(StockByItemListParams.LOT_NO, outparam.get(FaItemWorkingInquiryDASCHParams.LOT_NO));
                expparam.set(StockByItemListParams.STORAGE_DATE,
                        outparam.get(FaItemWorkingInquiryDASCHParams.STORAGE_DATETIME));
                expparam.set(StockByItemListParams.AREA_NO, outparam.get(FaItemWorkingInquiryDASCHParams.AREA_NO));
                expparam.set(StockByItemListParams.LOCATION_NO,
                        outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_NO));
                expparam.set(StockByItemListParams.LOCATION_STATUS,
                        outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_STATUS));
                expparam.set(StockByItemListParams.STOCK_QTY,
                        outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));

                // DFKLOOK start 帳票のグループ印字対応
                previousRecord = expparam;
                gotRecordFromDasch = true;
                // 明細行分+
                currentRow += detailRow;
            }

            if (previousRecord != null)
            {
                // write last record.
                previousRecord.set(StockByItemListParams.IS_LAST_ITEM_CODE_IN_GROUP, true);
                exporter.write(previousRecord);
            }
            // DFKLOOK end

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
     * 明細(商品コード別)用
     *
     * @throws Exception
     */
    private void btn_XLS_Click_Process_DetailItem()
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
                expparam.set(StockByItemListParams.STORAGE_DATE,
                        outparam.get(FaItemWorkingInquiryDASCHParams.STORAGE_DATETIME));
                expparam.set(StockByItemListParams.AREA_NO, outparam.get(FaItemWorkingInquiryDASCHParams.AREA_NO));
                expparam.set(StockByItemListParams.LOCATION_NO,
                        outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_NO));
                expparam.set(StockByItemListParams.LOCATION_STATUS,
                        outparam.get(FaItemWorkingInquiryDASCHParams.LOCATION_STATUS));
                expparam.set(StockByItemListParams.STOCK_QTY,
                        outparam.get(FaItemWorkingInquiryDASCHParams.TOTAL_STOCK_QTY));
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
     * エリア名称を取得します。
     * 
     * @param conn コネクション
     * @param areaNo エリアNo.
     * @return String エリア名称
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    private String getAreaName(Connection conn, String areaNo)
            throws CommonException
    {
        AreaController aCtrl = new AreaController(conn, this.getClass());
        return aCtrl.getAreaName(areaNo);
    }

    //DFKLOOK:ここまで修正
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
