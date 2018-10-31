package jp.co.daifuku.wms.asrs.listbox.locationstock;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.model.PagerModel;
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
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.LstAsLocationStockDASCH;
import jp.co.daifuku.wms.asrs.dasch.LstAsLocationStockDASCHParams;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * 棚別在庫一覧の画面処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:10:03 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class LstAsLocationStockBusiness
        extends LstAsLocationStock
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

    /** lst_SearchCondition(LST_SEARCH_CONDITION) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION =
            new ListCellKey("LST_SEARCH_CONDITION", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_2) */
    private static final ListCellKey KEY_LST_COLUMN_2 =
            new ListCellKey("LST_COLUMN_2", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_3) */
    private static final ListCellKey KEY_LST_COLUMN_3 =
            new ListCellKey("LST_COLUMN_3", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_4) */
    private static final ListCellKey KEY_LST_COLUMN_4 =
            new ListCellKey("LST_COLUMN_4", new StringCellColumn(), true, false);

    /** lst_StockByLocationList(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT =
            new ListCellKey("LST_SELECT", new StringCellColumn(), true, false);

    /** lst_StockByLocationList(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO =
            new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_StockByLocationList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_StockByLocationList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_StockByLocationList(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO =
            new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_StockByLocationList(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY =
            new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockByLocationList(LST_STOCK_CASE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_CASE_QTY =
            new ListCellKey("LST_STOCK_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockByLocationList(LST_STOCK_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_PIECE_QTY =
            new ListCellKey("LST_STOCK_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StockByLocationList(LST_STORAGE_DATE) */
    private static final ListCellKey KEY_LST_STORAGE_DATE =
            new ListCellKey("LST_STORAGE_DATE", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_StockByLocationList(LST_STORAGE_TIME) */
    private static final ListCellKey KEY_LST_STORAGE_TIME =
            new ListCellKey("LST_STORAGE_TIME", new DateCellColumn(DateCellColumn.DATE_TYPE_NONE,
                    DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_StockByLocationList(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_StockByLocationList(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_SearchCondition kyes */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
            KEY_LST_SEARCH_CONDITION,
            KEY_LST_COLUMN_2,
            KEY_LST_COLUMN_3,
            KEY_LST_COLUMN_4,
    };

    /** lst_StockByLocationList kyes */
    private static final ListCellKey[] LST_STOCKBYLOCATIONLIST_KEYS = {
            KEY_LST_SELECT,
            KEY_LST_LOCATION_NO,
            KEY_LST_ITEM_CODE,
            KEY_LST_LOT_NO,
            KEY_LST_ENTERING_QTY,
            KEY_LST_STOCK_CASE_QTY,
            KEY_LST_STORAGE_DATE,
            KEY_LST_JAN,
            KEY_LST_ITEM_NAME,
            KEY_LST_STOCK_PIECE_QTY,
            KEY_LST_STORAGE_TIME,
            KEY_LST_ITF,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_SearchCondition */
    private ListCellModel _lcm_lst_SearchCondition;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_StockByLocationList */
    private ListCellModel _lcm_lst_StockByLocationList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstAsLocationStockBusiness()
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
        // initialize componenets.
        initializeComponents();

        // process call.
        page_Initialize_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_SettingName_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchCondition_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchCondition_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchCondition_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchCondition_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchCondition_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchCondition_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchCondition_Change(ActionEvent e)
            throws Exception
    {
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_U_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_U_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_U_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StockByLocationList_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_StockByLocationList.getActiveCol();

        // choose process.
        if (_lcm_lst_StockByLocationList.getColumnIndex(KEY_LST_SELECT) == activeCol)
        {
            // process call.
            lst_Select_Click_Process();
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StockByLocationList_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StockByLocationList_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StockByLocationList_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StockByLocationList_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StockByLocationList_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StockByLocationList_Change(ActionEvent e)
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
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
        page_Load_SetList();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_D_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_D_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_D_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_ListName_Server(ActionEvent e)
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
    // DFKLOOK ここから修正
    /**
     * 検索条件のリストセルを作成します。
     * @param param 検索条件
     * @throws Exception 全ての例外を報告します。 全ての例外を報告します。
     */
    protected void lst_SearchCondition_Make(LstAsLocationStockDASCHParams param, Connection conn)
            throws Exception
    {
        int line = 1;

        lst_SearchCondition.addRow();
        lst_SearchCondition.setCurrentRow(line);

        //エリア
        lst_SearchCondition.setValue(1, DisplayText.getText("LBL-W0228"));
        //エリア名称
        lst_SearchCondition.setValue(3, DisplayText.getText("LBL-W0229"));

        AreaController areacon = new AreaController(conn, this.getClass());
        lst_SearchCondition.setValue(2, param.getString(LstAsLocationStockDASCHParams.AREA_NO));
        lst_SearchCondition.setValue(4, areacon.getAreaName(param.getString(LstAsLocationStockDASCHParams.AREA_NO)));


        lst_SearchCondition.addRow();
        lst_SearchCondition.setCurrentRow(line + 1);

        // 商品コード
        lst_SearchCondition.setValue(1, DisplayText.getText("LBL-W0189"));
        // 商品名称
        lst_SearchCondition.setValue(3, DisplayText.getText("LBL-W0130"));

        ItemController item = new ItemController(conn, this.getClass());
        lst_SearchCondition.setValue(2, param.getString(LstAsLocationStockDASCHParams.ITEM_CODE));
        if (!StringUtil.isBlank(param.getString(LstAsLocationStockDASCHParams.ITEM_CODE)))
        {
            lst_SearchCondition.setValue(4, item.getItemName(param.getString(LstAsLocationStockDASCHParams.ITEM_CODE),
                    param.getString(LstAsLocationStockDASCHParams.CONSIGNOR_CODE)));
        }
    }

    // DFKLOOK ここまで修正
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

        // initialize lst_SearchCondition.
        _lcm_lst_SearchCondition = new ListCellModel(lst_SearchCondition, LST_SEARCHCONDITION_KEYS, locale);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_2, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_3, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_4, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale);

        // initialize lst_StockByLocationList.
        _lcm_lst_StockByLocationList = new ListCellModel(lst_StockByLocationList, LST_STOCKBYLOCATIONLIST_KEYS, locale);
        _lcm_lst_StockByLocationList.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_StockByLocationList.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_StockByLocationList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_StockByLocationList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_StockByLocationList.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_StockByLocationList.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_StockByLocationList.setToolTipVisible(KEY_LST_STOCK_CASE_QTY, true);
        _lcm_lst_StockByLocationList.setToolTipVisible(KEY_LST_STOCK_PIECE_QTY, true);
        _lcm_lst_StockByLocationList.setToolTipVisible(KEY_LST_STORAGE_DATE, true);
        _lcm_lst_StockByLocationList.setToolTipVisible(KEY_LST_STORAGE_TIME, true);
        _lcm_lst_StockByLocationList.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_StockByLocationList.setToolTipVisible(KEY_LST_ITF, true);

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
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_SearchCondition_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
    }

    /**
     *
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_StockByLocationList_SetLineToolTip(ListCellLine line)
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
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(pgr_U);

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

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        LstAsLocationStockDASCH dasch = null;
        // DFKLOOK セッション保持フラグ追加
        boolean isSuccess = false;
        // DFKLOOK ここまで
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstAsLocationStockDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstAsLocationStockDASCHParams inparam = new LstAsLocationStockDASCHParams();
            LstAsLocationStockParams requestParam = new LstAsLocationStockParams(request);
            inparam.set(LstAsLocationStockDASCHParams.AREA_NO, requestParam.get(LstAsLocationStockParams.AREA_NO));
            inparam.set(LstAsLocationStockDASCHParams.CONSIGNOR_CODE,
                    requestParam.get(LstAsLocationStockParams.CONSIGNOR_CODE));
            inparam.set(LstAsLocationStockDASCHParams.ITEM_CODE, requestParam.get(LstAsLocationStockParams.ITEM_CODE));
            inparam.set(LstAsLocationStockDASCHParams.LOCATION_NO,
                    requestParam.get(LstAsLocationStockParams.LOCATION_NO));

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_StockByLocationList.clear();

            // DFKLOOK ここから修正
            lst_SearchCondition_Make(inparam, conn);
            // DFKLOOK ここまで修正

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");
                return;
            }
            else if (count > _pager.getMax())
            {
                message.setMsgResourceKey("6001023\t" + Formatter.getNumFormat(count) + "\t"
                        + Formatter.getNumFormat(_pager.getMax()));
            }
            else
            {
                message.setMsgResourceKey("6001022\t" + Formatter.getNumFormat(count));
            }

            // DASCH call.
            dasch.search(inparam);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StockByLocationList.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(LstAsLocationStockDASCHParams.SELECT));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(LstAsLocationStockDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstAsLocationStockDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstAsLocationStockDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(LstAsLocationStockDASCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(LstAsLocationStockDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(LstAsLocationStockDASCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(LstAsLocationStockDASCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(LstAsLocationStockDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(LstAsLocationStockDASCHParams.STORAGE_TIME));
                line.setValue(KEY_LST_JAN, outparam.get(LstAsLocationStockDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(LstAsLocationStockDASCHParams.ITF));
                lst_StockByLocationList_SetLineToolTip(line);
                _lcm_lst_StockByLocationList.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH_LIST, dasch);
            // DFKLOOK フラグ更新
            isSuccess = true;
            // DFKLOOK ここまで
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_StockByLocationList.clear();
        }
        finally
        {
            // DFKLOOK finally追加
            if (!isSuccess)
            {
                if (dasch != null)
                {
                    dasch.close();
                }
                DBUtil.close(conn);
            }
            // DFKLOOK ここまで
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_SetList()
            throws Exception
    {
        LstAsLocationStockDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstAsLocationStockDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_StockByLocationList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StockByLocationList.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(LstAsLocationStockDASCHParams.SELECT));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(LstAsLocationStockDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstAsLocationStockDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstAsLocationStockDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(LstAsLocationStockDASCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(LstAsLocationStockDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(LstAsLocationStockDASCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(LstAsLocationStockDASCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(LstAsLocationStockDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(LstAsLocationStockDASCHParams.STORAGE_TIME));
                line.setValue(KEY_LST_JAN, outparam.get(LstAsLocationStockDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(LstAsLocationStockDASCHParams.ITF));
                lst_StockByLocationList_SetLineToolTip(line);
                _lcm_lst_StockByLocationList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_StockByLocationList.clear();
            disposeDasch();
        }
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
            // DFKLOOK コネクションクローズ追加
            DBUtil.close(dasch.getConnection());
            // DFKLOOK ここまで
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_U_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

    }

    /**
     *
     * @throws Exception
     */
    private void lst_Select_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_StockByLocationList.getActiveRow();
        lst_StockByLocationList.setCurrentRow(row);
        ListCellLine line = _lcm_lst_StockByLocationList.get(row);

        // output parameter.
        LstAsLocationStockParams outparam = new LstAsLocationStockParams();
        outparam.set(LstAsLocationStockParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
        outparam.set(LstAsLocationStockParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
        outparam.set(LstAsLocationStockParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
        outparam.set(LstAsLocationStockParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
        outparam.set(LstAsLocationStockParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
        outparam.set(LstAsLocationStockParams.STOCK_CASE_QTY, line.getValue(KEY_LST_STOCK_CASE_QTY));
        outparam.set(LstAsLocationStockParams.STOCK_PIECE_QTY, line.getValue(KEY_LST_STOCK_PIECE_QTY));
        outparam.set(LstAsLocationStockParams.STORAGE_DATE, line.getValue(KEY_LST_STORAGE_DATE));
        outparam.set(LstAsLocationStockParams.STORAGE_TIME, line.getValue(KEY_LST_STORAGE_TIME));
        outparam.set(LstAsLocationStockParams.JAN, line.getValue(KEY_LST_JAN));
        outparam.set(LstAsLocationStockParams.ITF, line.getValue(KEY_LST_ITF));

        ForwardParameters forwardParam = outparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, viewState.getString(_KEY_POPUPSOURCE));
        parentRedirect(forwardParam);
        dispose();

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_D_Click_Process()
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
