// $Id: LstStockBusiness.java 6860 2010-01-22 11:22:23Z kanda $
package jp.co.daifuku.wms.stock.listbox.stocklist;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.Constants;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;

import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.location.WmsLocationFormat;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.common.WmsParam;

import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.wms.stock.dasch.LstStockDASCH;
import jp.co.daifuku.wms.stock.dasch.LstStockDASCHParams;

/**
 * 棚明細一覧の画面処理を行います。
 *
 * @version $Revision: 6860 $, $Date: 2010-01-22 20:22:23 +0900 (金, 22 1 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kanda $
 */
public class LstStockBusiness
        extends LstStock
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
    private static final ListCellKey KEY_LST_SEARCH_CONDITION = new ListCellKey("LST_SEARCH_CONDITION", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_2) */
    private static final ListCellKey KEY_LST_COLUMN_2 = new ListCellKey("LST_COLUMN_2", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_3) */
    private static final ListCellKey KEY_LST_COLUMN_3 = new ListCellKey("LST_COLUMN_3", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_4) */
    private static final ListCellKey KEY_LST_COLUMN_4 = new ListCellKey("LST_COLUMN_4", new StringCellColumn(), true, false);

    /** lst_LocationDetailList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_LocationDetailList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_LocationDetailList(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_LocationDetailList(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_LocationDetailList(LST_STOCK_CASE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_CASE_QTY = new ListCellKey("LST_STOCK_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_LocationDetailList(LST_STOCK_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_PIECE_QTY = new ListCellKey("LST_STOCK_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_LocationDetailList(LST_ALLOC_CASE_QTY) */
    private static final ListCellKey KEY_LST_ALLOC_CASE_QTY = new ListCellKey("LST_ALLOC_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_LocationDetailList(LST_ALLOC_PIECE_QTY) */
    private static final ListCellKey KEY_LST_ALLOC_PIECE_QTY = new ListCellKey("LST_ALLOC_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_LocationDetailList(LST_STORAGE_DATE) */
    private static final ListCellKey KEY_LST_STORAGE_DATE = new ListCellKey("LST_STORAGE_DATE", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG, DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_LocationDetailList(LST_STORAGE_TIME) */
    private static final ListCellKey KEY_LST_STORAGE_TIME = new ListCellKey("LST_STORAGE_TIME", new DateCellColumn(DateCellColumn.DATE_TYPE_NONE, DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_LocationDetailList(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_LocationDetailList(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_SearchCondition kyes */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
        KEY_LST_SEARCH_CONDITION,
        KEY_LST_COLUMN_2,
        KEY_LST_COLUMN_3,
        KEY_LST_COLUMN_4,
    };

    /** lst_LocationDetailList kyes */
    private static final ListCellKey[] LST_LOCATIONDETAILLIST_KEYS = {
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_STOCK_CASE_QTY,
        KEY_LST_ALLOC_CASE_QTY,
        KEY_LST_STORAGE_DATE,
        KEY_LST_JAN,
        KEY_LST_ITEM_NAME,
        KEY_LST_STOCK_PIECE_QTY,
        KEY_LST_ALLOC_PIECE_QTY,
        KEY_LST_STORAGE_TIME,
        KEY_LST_ITF,
    };

    // DFKLOOK ここから追加
    // 検索条件表示行番号
    /**
     * 検索条件表示行番号 : エリアNo.とエリア名称
     */
    private static final int CONDITION_AREA = 1;

    /**
     * 検索条件表示行番号 : 棚
     */
    private static final int CONDITION_LOCATION_NO = 2;

    /**
     * 検索条件用リスト列番号用
     */
    private static final int S_LIST1 = 1;

    /**
     * 検索条件用リスト列番号用
     */
    private static final int S_LIST2 = 2;

    /**
     * 検索条件用リスト列番号用
     */
    private static final int S_LIST3 = 3;

    /**
     * 検索条件用リスト列番号用
     */
    private static final int S_LIST4 = 4;
    // DFKLOOK ここまで追加

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

    /** ListCellModel lst_LocationDetailList */
    private ListCellModel _lcm_lst_LocationDetailList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstStockBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     *
     * @param e ActionEvent
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
     */
    public void btn_Close_D_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_D_Click_Process();
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
     * @throws Exception All the exceptions are reported.
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
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_LocationDetailList.
        _lcm_lst_LocationDetailList = new ListCellModel(lst_LocationDetailList, LST_LOCATIONDETAILLIST_KEYS, locale);
        _lcm_lst_LocationDetailList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_LocationDetailList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_LocationDetailList.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_LocationDetailList.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_LocationDetailList.setToolTipVisible(KEY_LST_STOCK_CASE_QTY, true);
        _lcm_lst_LocationDetailList.setToolTipVisible(KEY_LST_STOCK_PIECE_QTY, true);
        _lcm_lst_LocationDetailList.setToolTipVisible(KEY_LST_ALLOC_CASE_QTY, true);
        _lcm_lst_LocationDetailList.setToolTipVisible(KEY_LST_ALLOC_PIECE_QTY, true);
        _lcm_lst_LocationDetailList.setToolTipVisible(KEY_LST_STORAGE_DATE, true);
        _lcm_lst_LocationDetailList.setToolTipVisible(KEY_LST_STORAGE_TIME, true);
        _lcm_lst_LocationDetailList.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_LocationDetailList.setToolTipVisible(KEY_LST_ITF, true);

    }

    /**
     *
     * @throws Exception All the exceptions are reported.
     */
    private void initializePulldownModels()
            throws Exception
    {
    }

    /**
     *
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
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
     * @throws Exception All the exceptions are reported.
     */
    private void lst_LocationDetailList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
    }

    /**
     *
     * @throws Exception All the exceptions are reported.
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(pgr_U);

    }

    /**
     *
     * @throws Exception All the exceptions are reported.
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
        LstStockDASCH dasch = null;
        // DFKLOOK セッション保持フラグ追加
        boolean isSuccess = false;
        // DFKLOOK ここまで
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstStockDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstStockDASCHParams inparam = new LstStockDASCHParams();
            LstStockParams requestParam = new LstStockParams(request);
            inparam.set(LstStockDASCHParams.AREA_NO, requestParam.get(LstStockParams.AREA_NO));
            inparam.set(LstStockDASCHParams.LOCATION_NO, requestParam.get(LstStockParams.LOCATION_NO));
            // DFKLOOK ここから追加
            // 検索条件の表示編集処理
            _lcm_lst_SearchCondition.clear();
            lst_SearchCondition.setVisible(true);
            setSearchlist(conn, requestParam);
            // DFKLOOK ここまで追加

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_LocationDetailList.clear();

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

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LocationDetailList.getNewLine();
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstStockDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstStockDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(LstStockDASCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(LstStockDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(LstStockDASCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(LstStockDASCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_ALLOC_CASE_QTY, outparam.get(LstStockDASCHParams.ALLOC_CASE_QTY));
                line.setValue(KEY_LST_ALLOC_PIECE_QTY, outparam.get(LstStockDASCHParams.ALLOC_PIECE_QTY));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(LstStockDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(LstStockDASCHParams.STORAGE_TIME));
                line.setValue(KEY_LST_JAN, outparam.get(LstStockDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(LstStockDASCHParams.ITF));
                lst_LocationDetailList_SetLineToolTip(line);
                _lcm_lst_LocationDetailList.add(line);
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
            _lcm_lst_LocationDetailList.clear();
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
        LstStockDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstStockDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_LocationDetailList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LocationDetailList.getNewLine();
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstStockDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstStockDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(LstStockDASCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(LstStockDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(LstStockDASCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(LstStockDASCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_ALLOC_CASE_QTY, outparam.get(LstStockDASCHParams.ALLOC_CASE_QTY));
                line.setValue(KEY_LST_ALLOC_PIECE_QTY, outparam.get(LstStockDASCHParams.ALLOC_PIECE_QTY));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(LstStockDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(LstStockDASCHParams.STORAGE_TIME));
                line.setValue(KEY_LST_JAN, outparam.get(LstStockDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(LstStockDASCHParams.ITF));
                lst_LocationDetailList_SetLineToolTip(line);
                _lcm_lst_LocationDetailList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_LocationDetailList.clear();
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
     * @throws Exception All the exceptions are reported.
     */
    private void btn_Close_U_Click_Process()
            throws Exception
    {
        parentRedirect(null);
        dispose();

    }

    /**
     *
     * @throws Exception All the exceptions are reported.
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

            // DFKLOOK lbl_ListNameに変更
            lbl_ListName.setResourceKey(title);
        }
        else if (this.getTitleResourceKey() != null && !this.getTitleResourceKey().equals(""))
        {
            // リストボックスの場合はpage.xmlから取得する
            // DFKLOOK lbl_ListNameに変更
            lbl_ListName.setResourceKey(this.getTitleResourceKey());
        }
        else if (viewState.getString(Constants.M_TITLE_KEY) != null)
        {
            // 2画面遷移から戻ってきた場合はViewStateから取得する
            // DFKLOOK lbl_ListNameに変更
            lbl_ListName.setResourceKey(viewState.getString(Constants.M_TITLE_KEY));
        }
    }

    // DFKLOOK ここから追加
    // 検索条件を編集
    /**
     * 検索条件の表示をセットする時に使用するメソッドです。
     * <BR>
     * @param conn コネクション
     * @param param 表示検索条件
     * @throws Exception 全ての例外を報告します。
     */
    private void setSearchlist(Connection conn, LstStockParams param)
            throws Exception
    {
        lst_SearchCondition.clearRow();
        lst_SearchCondition.addRow();

        //エリア名称取得
        String areaName = null;
        String areaNo = param.getString(LstStockParams.AREA_NO);
        if (WmsParam.ALL_AREA_NO.equals(areaNo))
        {
            //全エリア
            areaName = DisplayText.getText("CMB-W0023");
        }
        else
        {
            //エリア名称取得
            AreaHandler handler = new AreaHandler(conn);
            AreaSearchKey sKey = new AreaSearchKey();

            // 取得条件
            sKey.setAreaNo(areaNo);
            sKey.setAreaNameCollect();

            Area[] areas = (Area[])handler.find(sKey);
            areaName = areas[0].getAreaName();
        }

        // １行目 : エリアNo.とエリア名称
        lst_SearchCondition.setCurrentRow(CONDITION_AREA);
        lst_SearchCondition.setValue(S_LIST1, DisplayText.getText("LBL-W0228"));
        lst_SearchCondition.setValue(S_LIST2, areaNo);
        lst_SearchCondition.setValue(S_LIST3, DisplayText.getText("LBL-W0229"));
        lst_SearchCondition.setValue(S_LIST4, areaName);

        // ２行目 : 棚
        lst_SearchCondition.addRow();
        lst_SearchCondition.setCurrentRow(CONDITION_LOCATION_NO);
        lst_SearchCondition.setValue(S_LIST1, DisplayText.getText("LBL-W0138"));
        // 棚No
        AreaController areac = new AreaController(conn, getClass());
        WmsLocationFormat wmsLoc =
            new WmsLocationFormat(areac.getLocationStyle(areaNo));
        lst_SearchCondition.setValue(S_LIST2, wmsLoc.format(param.getString(LstStockParams.LOCATION_NO), areaNo));
    }
    // DFKLOOK ここまで追加

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
