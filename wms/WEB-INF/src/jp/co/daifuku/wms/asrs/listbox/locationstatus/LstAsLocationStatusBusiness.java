// $Id: LstAsLocationStatusBusiness.java 7714 2010-03-23 05:50:03Z shibamoto $
package jp.co.daifuku.wms.asrs.listbox.locationstatus;

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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.LstAsLocationStatusDASCH;
import jp.co.daifuku.wms.asrs.dasch.LstAsLocationStatusDASCHParams;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * 棚別状態一覧の画面処理を行います。
 *
 * @version $Revision: 7714 $, $Date: 2010-03-23 14:50:03 +0900 (火, 23 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class LstAsLocationStatusBusiness
        extends LstAsLocationStatus
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

    /** lst_SearchConditionOneColumn(LST_SEARCH_CONDITION) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION =
            new ListCellKey("LST_SEARCH_CONDITION", new StringCellColumn(), true, false);

    /** lst_SearchConditionOneColumn(LST_COLUMN_2) */
    private static final ListCellKey KEY_LST_COLUMN_2 =
            new ListCellKey("LST_COLUMN_2", new StringCellColumn(), true, false);

    /** lst_SearchConditionOneColumn(LST_COLUMN_3) */
    private static final ListCellKey KEY_LST_COLUMN_3 =
            new ListCellKey("LST_COLUMN_3", new StringCellColumn(), true, false);

    /** lst_SearchConditionOneColumn(LST_COLUMN_4) */
    private static final ListCellKey KEY_LST_COLUMN_4 =
            new ListCellKey("LST_COLUMN_4", new StringCellColumn(), true, false);

    /** lst_StatusByLocationWithButton(HIDDEN_LOCATION_NO) */
    private static final ListCellKey KEY_HIDDEN_LOCATION_NO =
            new ListCellKey("HIDDEN_LOCATION_NO", new StringCellColumn(), false, false);

    /** lst_StatusByLocationWithButton(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT =
            new ListCellKey("LST_SELECT", new StringCellColumn(), true, false);

    /** lst_StatusByLocationWithButton(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO =
            new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_StatusByLocationWithButton(LST_STATUS_FLAG) */
    private static final ListCellKey KEY_LST_STATUS_FLAG =
            new ListCellKey("LST_STATUS_FLAG", new StringCellColumn(), true, false);

    /** lst_StatusByLocationWithButton(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_StatusByLocationWithButton(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_StatusByLocationWithButton(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO =
            new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_StatusByLocationWithButton(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY =
            new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_StatusByLocationWithButton(LST_STOCK_CASE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_CASE_QTY =
            new ListCellKey("LST_STOCK_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StatusByLocationWithButton(LST_STOCK_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_PIECE_QTY =
            new ListCellKey("LST_STOCK_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_StatusByLocationWithButton(LST_STORAGE_DATE) */
    private static final ListCellKey KEY_LST_STORAGE_DATE =
            new ListCellKey("LST_STORAGE_DATE", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_StatusByLocationWithButton(LST_STORAGE_TIME) */
    private static final ListCellKey KEY_LST_STORAGE_TIME =
            new ListCellKey("LST_STORAGE_TIME", new DateCellColumn(DateCellColumn.DATE_TYPE_NONE,
                    DateCellColumn.TIME_TYPE_SEC), true, false);

    /** lst_StatusByLocationWithButton(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_StatusByLocationWithButton(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_SearchConditionOneColumn kyes */
    private static final ListCellKey[] LST_SEARCHCONDITIONONECOLUMN_KEYS = {
            KEY_LST_SEARCH_CONDITION,
            KEY_LST_COLUMN_2,
            KEY_LST_COLUMN_3,
            KEY_LST_COLUMN_4,
    };

    /** lst_StatusByLocationWithButton kyes */
    private static final ListCellKey[] LST_STATUSBYLOCATIONWITHBUTTON_KEYS = {
            KEY_HIDDEN_LOCATION_NO,
            KEY_LST_SELECT,
            KEY_LST_LOCATION_NO,
            KEY_LST_STATUS_FLAG,
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
    /** ListCellModel lst_SearchConditionOneColumn */
    private ListCellModel _lcm_lst_SearchConditionOneColumn;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_StatusByLocationWithButton */
    private ListCellModel _lcm_lst_StatusByLocationWithButton;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstAsLocationStatusBusiness()
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
    public void lst_SearchConditionOneColumn_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchConditionOneColumn_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchConditionOneColumn_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchConditionOneColumn_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchConditionOneColumn_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchConditionOneColumn_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchConditionOneColumn_Change(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_up_First(ActionEvent e)
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
    public void pager_up_Prev(ActionEvent e)
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
    public void pager_up_Next(ActionEvent e)
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
    public void pager_up_Last(ActionEvent e)
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
    public void btn_Close_Up_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_Up_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_Up_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StatusByLocationWithButton_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_StatusByLocationWithButton.getActiveCol();

        // choose process.
        if (_lcm_lst_StatusByLocationWithButton.getColumnIndex(KEY_LST_SELECT) == activeCol)
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
    public void lst_StatusByLocationWithButton_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StatusByLocationWithButton_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StatusByLocationWithButton_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StatusByLocationWithButton_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StatusByLocationWithButton_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_StatusByLocationWithButton_Change(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void pager_down_First(ActionEvent e)
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
    public void pager_down_Prev(ActionEvent e)
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
    public void pager_down_Next(ActionEvent e)
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
    public void pager_down_Last(ActionEvent e)
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
    public void btn_Close_Down_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_Down_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_Down_Server(ActionEvent e)
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
        Locale locale = httpRequest.getLocale();

        // initialize lst_SearchConditionOneColumn.
        _lcm_lst_SearchConditionOneColumn =
                new ListCellModel(lst_SearchConditionOneColumn, LST_SEARCHCONDITIONONECOLUMN_KEYS, locale);
        _lcm_lst_SearchConditionOneColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION, false);
        _lcm_lst_SearchConditionOneColumn.setToolTipVisible(KEY_LST_COLUMN_2, false);
        _lcm_lst_SearchConditionOneColumn.setToolTipVisible(KEY_LST_COLUMN_3, false);
        _lcm_lst_SearchConditionOneColumn.setToolTipVisible(KEY_LST_COLUMN_4, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[] {
                pager_up,
                pager_down
        }, locale);

        // initialize lst_StatusByLocationWithButton.
        _lcm_lst_StatusByLocationWithButton =
                new ListCellModel(lst_StatusByLocationWithButton, LST_STATUSBYLOCATIONWITHBUTTON_KEYS, locale);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_STATUS_FLAG, true);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_STOCK_CASE_QTY, true);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_STOCK_PIECE_QTY, true);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_STORAGE_DATE, true);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_STORAGE_TIME, true);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_StatusByLocationWithButton.setToolTipVisible(KEY_LST_ITF, true);

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
    private void lst_SearchConditionOneColumn_SetLineToolTip(ListCellLine line)
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
    private void lst_StatusByLocationWithButton_SetLineToolTip(ListCellLine line)
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
        setFocus(pager_up);

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
        LstAsLocationStatusDASCH dasch = null;
        // DFKLOOK セッション保持フラグ追加
        boolean isSuccess = false;
        // DFKLOOK ここまで
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstAsLocationStatusDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstAsLocationStatusDASCHParams inparam = new LstAsLocationStatusDASCHParams();
            LstAsLocationStatusParams requestParam = new LstAsLocationStatusParams(request);
            inparam.set(LstAsLocationStatusDASCHParams.AREA_NO, requestParam.get(LstAsLocationStatusParams.AREA_NO));
            inparam.set(LstAsLocationStatusDASCHParams.PROHIBITION_EMPTY,
                    requestParam.get(LstAsLocationStatusParams.PROHIBITION_EMPTY));
            inparam.set(LstAsLocationStatusDASCHParams.PROHIBITION_EMPTY_PALLET,
                    requestParam.get(LstAsLocationStatusParams.PROHIBITION_EMPTY_PALLET));
            inparam.set(LstAsLocationStatusDASCHParams.PROHIBITION_STORAGED,
                    requestParam.get(LstAsLocationStatusParams.PROHIBITION_STORAGED));
            inparam.set(LstAsLocationStatusDASCHParams.PROHIBITION_ERROR,
                    requestParam.get(LstAsLocationStatusParams.PROHIBITION_ERROR));

            // DFKLOOK:ここから修正
            // 検索条件リスト設定
            setStatusSearchList(inparam);

            // エリアマスタ情報コントローラー
            AreaController areacon = new AreaController(conn, this.getClass());
            // DFKLOOK:ここまで修正

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_StatusByLocationWithButton.clear();

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
                ListCellLine line = _lcm_lst_StatusByLocationWithButton.getNewLine();
                // DFKLOOK:ここから修正
                String location =
                        areacon.toParamLocation(outparam.getString(LstAsLocationStatusDASCHParams.STATION_NO));
                line.setValue(KEY_HIDDEN_LOCATION_NO, location);
                location = areacon.toDispLocation(outparam.getString(LstAsLocationStatusDASCHParams.AREA_NO), location);
                line.setValue(KEY_LST_LOCATION_NO, location);
                line.setValue(KEY_LST_STATUS_FLAG, getResultStatusText(outparam));
                // DFKLOOK:ここまで修正
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstAsLocationStatusDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstAsLocationStatusDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(LstAsLocationStatusDASCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(LstAsLocationStatusDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(LstAsLocationStatusDASCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(LstAsLocationStatusDASCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(LstAsLocationStatusDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(LstAsLocationStatusDASCHParams.STORAGE_TIME));
                line.setValue(KEY_LST_JAN, outparam.get(LstAsLocationStatusDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(LstAsLocationStatusDASCHParams.ITF));
                line.setValue(KEY_LST_SELECT, outparam.get(LstAsLocationStatusDASCHParams.SELECT));
                lst_StatusByLocationWithButton_SetLineToolTip(line);
                _lcm_lst_StatusByLocationWithButton.add(line);
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
            // DFKLOOK:ここから修正
            // リストセル不可視対応
            lst_StatusByLocationWithButton.setVisible(false);
            // DFKLOOK:ここまで修正
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
        LstAsLocationStatusDASCH dasch = null;
        // DFKLOOK:ここから修正
        Connection conn = null;
        // DFKLOOK:ここまで修正

        try
        {
            // get session.
            dasch = (LstAsLocationStatusDASCH)session.getAttribute(_KEY_DASCH_LIST);
            // DFKLOOK:ここから修正
            conn = ConnectionManager.getSessionConnection(this);
            AreaController areacon = new AreaController(conn, this.getClass());
            // DFKLOOK:ここまで修正

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_StatusByLocationWithButton.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_StatusByLocationWithButton.getNewLine();
                // DFKLOOK:ここから修正
                String location =
                        areacon.toParamLocation(outparam.getString(LstAsLocationStatusDASCHParams.STATION_NO));
                line.setValue(KEY_HIDDEN_LOCATION_NO, location);
                location = areacon.toDispLocation(outparam.getString(LstAsLocationStatusDASCHParams.AREA_NO), location);
                line.setValue(KEY_LST_LOCATION_NO, location);
                // DFKLOOK:ここまで修正
                // DFKLOOK:ここから修正
                line.setValue(KEY_LST_STATUS_FLAG, getResultStatusText(outparam));
                // DFKLOOK:ここまで修正
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstAsLocationStatusDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstAsLocationStatusDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(LstAsLocationStatusDASCHParams.LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(LstAsLocationStatusDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(LstAsLocationStatusDASCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(LstAsLocationStatusDASCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_STORAGE_DATE, outparam.get(LstAsLocationStatusDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STORAGE_TIME, outparam.get(LstAsLocationStatusDASCHParams.STORAGE_TIME));
                line.setValue(KEY_LST_JAN, outparam.get(LstAsLocationStatusDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(LstAsLocationStatusDASCHParams.ITF));
                line.setValue(KEY_LST_SELECT, outparam.get(LstAsLocationStatusDASCHParams.SELECT));
                lst_StatusByLocationWithButton_SetLineToolTip(line);
                _lcm_lst_StatusByLocationWithButton.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            // DFKLOOK:ここから修正
            // リストセル不可視対応
            lst_StatusByLocationWithButton.setVisible(false);
            // DFKLOOK:ここまで修正
            disposeDasch();
        }
        finally
        {
            // クローズ
            DBUtil.close(conn);
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
    private void btn_Close_Up_Click_Process()
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
        int row = lst_StatusByLocationWithButton.getActiveRow();
        lst_StatusByLocationWithButton.setCurrentRow(row);
        ListCellLine line = _lcm_lst_StatusByLocationWithButton.get(row);

        // output parameter.
        LstAsLocationStatusParams outparam = new LstAsLocationStatusParams();
        // DFKLOOK start
        lst_SearchConditionOneColumn.setCurrentRow(1);
        outparam.set(LstAsLocationStatusParams.AREA_NO, lst_SearchConditionOneColumn.getValue(2));
        // DFKLOOK end
        outparam.set(LstAsLocationStatusParams.LOCATION_NO, line.getValue(KEY_HIDDEN_LOCATION_NO));

        ForwardParameters forwardParam = outparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, viewState.getString(_KEY_POPUPSOURCE));
        parentRedirect(forwardParam);
        dispose();

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_Down_Click_Process()
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

    // DFKLOOK:ここから修正
    /**
     * 検索条件リストセルを表示します。<BR>
     * <BR>
     * @param inParam 検索条件パラメータ
     * @throws Exception 全ての例外処理を報告します。
     */
    private void setStatusSearchList(LstAsLocationStatusDASCHParams inParam)
            throws Exception
    {
        // コネクションの生成
        Connection conn = null;

        try
        {
            //コネクションの取得
            conn = ConnectionManager.getRequestConnection(this);

            // エリアマスタ情報コントローラー
            AreaController areacon = new AreaController(conn, this.getClass());

            // リストセルエリアをクリアする
            lst_SearchConditionOneColumn.clearRow();

            // 行の追加
            lst_SearchConditionOneColumn.addRow();
            lst_SearchConditionOneColumn.setCurrentRow(1);

            // エリアNo
            lst_SearchConditionOneColumn.setValue(1, DisplayText.getText("LBL-W0228"));
            lst_SearchConditionOneColumn.setValue(2, inParam.getString(LstAsLocationStatusDASCHParams.AREA_NO));

            // エリア名称
            lst_SearchConditionOneColumn.setValue(3, DisplayText.getText("LBL-W0229"));
            lst_SearchConditionOneColumn.setValue(4,
                    areacon.getAreaName(inParam.getString(LstAsLocationStatusDASCHParams.AREA_NO)));

            lst_SearchConditionOneColumn.addRow();
            lst_SearchConditionOneColumn.setCurrentRow(2);

            // 棚状態
            lst_SearchConditionOneColumn.setValue(1, DisplayText.getText("LBL-W0299"));
            lst_SearchConditionOneColumn.setValue(2, getSearchStatusText(inParam));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            // リストボックスのクリアを行います
            lst_SearchConditionOneColumn.resetHighlight();
            lst_SearchConditionOneColumn.clearRow();
            // エラーメッセージの表示
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            // クローズ
            DBUtil.close(conn);
        }
    }

    // DFKLOOK:ここまで修正

    // DFKLOOK:ここから修正
    /**
     * 検索条件リストセルに格納する棚状態を返します。
     * @param inParam AsrsInParameter 検索条件パラメータ
     * @return String 棚状態
     */
    private String getSearchStatusText(LstAsLocationStatusDASCHParams inParam)
    {
        // 返却用パラメータの生成
        String dispStatus = new String();

        // 棚使用(空棚)
        if (inParam.getBoolean(LstAsLocationStatusDASCHParams.PROHIBITION_EMPTY))
        {
            // 「空棚」を追加
            dispStatus += DisplayText.getText("LBL-W0061");
        }
        // 棚使用(実棚)
        if (inParam.getBoolean(LstAsLocationStatusDASCHParams.PROHIBITION_EMPTY_PALLET))
        {
            // 既に追加されていた場合は間文字を追加
            if (dispStatus.trim().length() != 0)
            {
                dispStatus += " , ";
            }

            // 「空PB棚」を追加
            dispStatus += DisplayText.getText("LBL-W0060");
        }
        // 棚使用(異常)
        if (inParam.getBoolean(LstAsLocationStatusDASCHParams.PROHIBITION_ERROR))
        {
            // 既に追加されていた場合は間文字を追加
            if (dispStatus.trim().length() != 0)
            {
                dispStatus += " , ";
            }
            // 「異常棚」を追加
            dispStatus += DisplayText.getText("LBL-W0036");
        }
        // 棚使用(実棚)
        if (inParam.getBoolean(LstAsLocationStatusDASCHParams.PROHIBITION_STORAGED))
        {
            // 既に追加されていた場合は間文字を追加
            if (dispStatus.trim().length() != 0)
            {
                dispStatus += " , ";
            }

            // 「実棚」を追加
            dispStatus += DisplayText.getText("LBL-W0104");
        }
        // 生成された文字列を返却
        return dispStatus;
    }

    // DFKLOOK:ここまで修正

    // DFKLOOK:ここから修正
    /**
     * 棚別状態一覧の棚状態に格納する文字列を返します。
     * @param outParam 格納するパラメータ
     * @return 格納する棚状態文字列
     */
    private String getResultStatusText(Params outParam)
    {
        // 棚状態(アクセス不可棚)
        if (AsrsInParameter.STATUS_UNABLEACCESS.equals(outParam.getString(LstAsLocationStatusDASCHParams.LOCATION_STATUS)))
        {
            // 「アクセス不可棚」を返却
            return DisplayText.getText("LBL-W0010");
        }
        // 棚状態(禁止棚)
        else if (AsrsInParameter.STATUS_UNAVAILABLE.equals(outParam.getString(LstAsLocationStatusDASCHParams.LOCATION_STATUS)))
        {
            // 「禁止棚」を返却
            return DisplayText.getText("LBL-W0059");
        }
        // 棚状態(空棚)
        else if (AsrsInParameter.STATUS_EMPTY.equals(outParam.getString(LstAsLocationStatusDASCHParams.LOCATION_STATUS)))
        {
            // 「空棚」を返却
            return DisplayText.getText("LBL-W0061");
        }
        // 棚状態(空PB棚)
        else if (AsrsInParameter.STATUS_EMPTYPALLET.equals(outParam.getString(LstAsLocationStatusDASCHParams.LOCATION_STATUS)))
        {
            // 「空PB棚」を返却
            return DisplayText.getText("LBL-W0060");
        }
        // 棚状態(異常棚)
        else if (AsrsInParameter.STATUS_IRREGULAR.equals(outParam.getString(LstAsLocationStatusDASCHParams.LOCATION_STATUS)))
        {
            // 「異常棚」を返却
            return DisplayText.getText("LBL-W0036");
        }
        // 棚状態(実棚)
        else if (AsrsInParameter.STATUS_STORAGED.equals(outParam.getString(LstAsLocationStatusDASCHParams.LOCATION_STATUS)))
        {
            // 「実棚」を返却
            return DisplayText.getText("LBL-W0104");
        }
        // 該当しない場合は空文字を返却
        return "";

    }

    // DFKLOOK:ここまで修正

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
