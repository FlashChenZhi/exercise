// $Id: AsInventoryCheckListBusiness.java 7552 2010-03-15 04:13:00Z ota $
package jp.co.daifuku.wms.asrs.display.inventorychecklist;

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
import jp.co.daifuku.common.ExceptionHandler;
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
import jp.co.daifuku.wms.asrs.dasch.AsInventoryCheckListDASCH;
import jp.co.daifuku.wms.asrs.dasch.AsInventoryCheckListDASCHParams;
import jp.co.daifuku.wms.asrs.exporter.AsrsInventoryCheckListParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * AS/RS 在庫確認作業リスト発行の画面処理を行います。
 * 
 * @version $Revision: 7552 $, $Date: 2010-03-15 13:13:00 +0900 (月, 15 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class AsInventoryCheckListBusiness
        extends AsInventoryCheckList
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_CONFIRMSOURCE = "_KEY_CONFIRMSOURCE";

    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ASRSInventoryCheckWorkList(LST_WORK_NO) */
    private static final ListCellKey KEY_LST_WORK_NO = new ListCellKey("LST_WORK_NO", new StringCellColumn(), true, false);

    /** lst_ASRSInventoryCheckWorkList(LST_PLAN_LOCATION_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOCATION_NO = new ListCellKey("LST_PLAN_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_ASRSInventoryCheckWorkList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ASRSInventoryCheckWorkList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ASRSInventoryCheckWorkList(LST_PLAN_LOT_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOT_NO = new ListCellKey("LST_PLAN_LOT_NO", new StringCellColumn(), true, false);

    /** lst_ASRSInventoryCheckWorkList(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_ASRSInventoryCheckWorkList(LST_STOCK_CASE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_CASE_QTY = new ListCellKey("LST_STOCK_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ASRSInventoryCheckWorkList(LST_STOCK_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_PIECE_QTY = new ListCellKey("LST_STOCK_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ASRSInventoryCheckWorkList kyes */
    private static final ListCellKey[] LST_ASRSINVENTORYCHECKWORKLIST_KEYS = {
        KEY_LST_WORK_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_PLAN_LOT_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_STOCK_CASE_QTY,
        KEY_LST_PLAN_LOCATION_NO,
        KEY_LST_ITEM_NAME,
        KEY_LST_STOCK_PIECE_QTY,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PullDownModel pul_Area */
    private WmsAreaPullDownModel _pdm_pul_Area;

    /** PullDownModel pul_WorkPlace */
    private WmsWorkspacePullDownModel _pdm_pul_WorkPlace;

    /** PullDownModel pul_Station */
    private WmsStationPullDownModel _pdm_pul_Station;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_ASRSInventoryCheckWorkList */
    private ListCellModel _lcm_lst_ASRSInventoryCheckWorkList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsInventoryCheckListBusiness()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
	 * 画面の初期化を行います。
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

        // DFKLOOK ここから
        // ロジック追加
        // Pager Clear
        _pager.clear();
        // DFKLOOK ここまで
    }

    /**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
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
	 * ダイアログボタンから、戻ってくるときにこのメソッドが呼ばれます。<BR>
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
	 * 表示ボタンが押下されたときに呼ばれます。<BR>
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
	 * 印刷ボタンが押下されたときに呼ばれます。<BR>
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
    public void btn_Print_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
	 * クリアボタンが押下されたときに呼ばれます。<BR>
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
	 * [<<] ボタンが押下されたときに呼ばれます。<BR>
	 * 先頭ページの情報を表示します。<BR>
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        btn_Display_Click_SetList();
    }

    /**
	 * [<] ボタンが押下されたときに呼ばれます。<BR>
	 * 前ページの情報を表示します。<BR>
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        btn_Display_Click_SetList();
    }

    /**
	 * [>] ボタンが押下されたときに呼ばれます。<BR>
	 * 次ページの情報を表示します。<BR>
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        btn_Display_Click_SetList();
    }

    /**
	 * [>>] ボタンが押下されたときに呼ばれます。<BR>
	 * 最終ページの情報を表示します。<BR>
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_U_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        btn_Display_Click_SetList();
    }

    /**
	 * [<<] ボタンが押下されたときに呼ばれます。<BR>
	 * 先頭ページの情報を表示します。<BR>
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_First(ActionEvent e)
            throws Exception
    {
        _pager.first();
        btn_Display_Click_SetList();
    }

    /**
	 * [<] ボタンが押下されたときに呼ばれます。<BR>
	 * 前ページの情報を表示します。<BR>
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_Prev(ActionEvent e)
            throws Exception
    {
        _pager.previous();
        btn_Display_Click_SetList();
    }

    /**
	 * [>] ボタンが押下されたときに呼ばれます。<BR>
	 * 次ページの情報を表示します。<BR>
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_Next(ActionEvent e)
            throws Exception
    {
        _pager.next();
        btn_Display_Click_SetList();
    }

    /**
	 * [>>] ボタンが押下されたときに呼ばれます。<BR>
	 * 最終ページの情報を表示します。<BR>
     * @param e ActionEvent
     * @throws Exception
     */
    public void pgr_D_Last(ActionEvent e)
            throws Exception
    {
        _pager.last();
        btn_Display_Click_SetList();
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

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
	 * 画面の初期化を行います。
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

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);
        _pdm_pul_WorkPlace.setParent(_pdm_pul_Area);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
        _pdm_pul_Station.setParent(_pdm_pul_WorkPlace);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_ASRSInventoryCheckWorkList.
        _lcm_lst_ASRSInventoryCheckWorkList = new ListCellModel(lst_ASRSInventoryCheckWorkList, LST_ASRSINVENTORYCHECKWORKLIST_KEYS, locale);
        _lcm_lst_ASRSInventoryCheckWorkList.setToolTipVisible(KEY_LST_WORK_NO, true);
        _lcm_lst_ASRSInventoryCheckWorkList.setToolTipVisible(KEY_LST_PLAN_LOCATION_NO, true);
        _lcm_lst_ASRSInventoryCheckWorkList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ASRSInventoryCheckWorkList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ASRSInventoryCheckWorkList.setToolTipVisible(KEY_LST_PLAN_LOT_NO, true);
        _lcm_lst_ASRSInventoryCheckWorkList.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_ASRSInventoryCheckWorkList.setToolTipVisible(KEY_LST_STOCK_CASE_QTY, true);
        _lcm_lst_ASRSInventoryCheckWorkList.setToolTipVisible(KEY_LST_STOCK_PIECE_QTY, true);

    }

    /**
	 * プルダウンの設定を行います。
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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.INVENTORY_CHECK, "", false);

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.INVENTORY_CHECK);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.INVENTORY_CHECK, Distribution.UNUSE);

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
	 * ツールチップの設定を行います。
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_ASRSInventoryCheckWorkList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
    }

    /**
	 * フォーカスの設定を行います。
     * @throws Exception
     */
    private void page_Initialize_Process()
            throws Exception
    {
        // set focus.
        setFocus(pul_Area);

    }

    /**
	 * 表示ボタンが押下された時の処理です。
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
    	
        // DFKLOOK ここから
        // 表示ボタン時、入力チェックをBusiTuneにて定義できない為、ロジック追加
        txt_SearchDate.validate(this, false);
        txt_SearchTime.validate(this, false);
        txt_ToSearchDate.validate(this, false);
        txt_ToSearchTime.validate(this, false);
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);

        WmsChecker chk = new WmsChecker();

        if (!chk.checkDate(txt_SearchDate.getDate(), txt_SearchTime.getTime()))
        {
        	message.setMsgResourceKey(chk.getMessage());
            return;
        }

        if (!chk.checkDate(txt_ToSearchDate.getDate(), txt_ToSearchTime.getTime()))
        {
        	message.setMsgResourceKey(chk.getMessage());
            return;
        }
        // DFKLOOK ここまで

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        AsInventoryCheckListDASCH dasch = null;
        // DFKLOOK セッション保持フラグ追加
        boolean isSuccess = false;
        // DFKLOOK ここまで
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new AsInventoryCheckListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            AsInventoryCheckListDASCHParams inparam = new AsInventoryCheckListDASCHParams();
            inparam.set(AsInventoryCheckListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsInventoryCheckListDASCHParams.WORK_PLACE_NO, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsInventoryCheckListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsInventoryCheckListDASCHParams.FROM_SEARCH_DATE, txt_SearchDate.getValue());
            inparam.set(AsInventoryCheckListDASCHParams.FROM_SEARCH_TIME, txt_SearchTime.getValue());
            inparam.set(AsInventoryCheckListDASCHParams.TO_SEARCH_DATE, txt_ToSearchDate.getValue());
            inparam.set(AsInventoryCheckListDASCHParams.TO_SEARCH_TIME, txt_ToSearchTime.getValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_ASRSInventoryCheckWorkList.clear();

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
                ListCellLine line = _lcm_lst_ASRSInventoryCheckWorkList.getNewLine();
                line.setValue(KEY_LST_WORK_NO, outparam.get(AsInventoryCheckListDASCHParams.WORK_NO));
                line.setValue(KEY_LST_PLAN_LOCATION_NO, outparam.get(AsInventoryCheckListDASCHParams.PLAN_LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(AsInventoryCheckListDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(AsInventoryCheckListDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(AsInventoryCheckListDASCHParams.PLAN_LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsInventoryCheckListDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(AsInventoryCheckListDASCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(AsInventoryCheckListDASCHParams.STOCK_PIECE_QTY));
                lst_ASRSInventoryCheckWorkList_SetLineToolTip(line);
                _lcm_lst_ASRSInventoryCheckWorkList.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            // DFKLOOK フラグ更新
            isSuccess = true;
            // DFKLOOK ここまで
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
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
	 * リストセルに値を設定する処理です。
     * @throws Exception
     */
    private void btn_Display_Click_SetList()
            throws Exception
    {
        AsInventoryCheckListDASCH dasch = null;
        try
        {
            // get session.
            dasch = (AsInventoryCheckListDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_ASRSInventoryCheckWorkList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ASRSInventoryCheckWorkList.getNewLine();
                line.setValue(KEY_LST_WORK_NO, outparam.get(AsInventoryCheckListDASCHParams.WORK_NO));
                line.setValue(KEY_LST_PLAN_LOCATION_NO, outparam.get(AsInventoryCheckListDASCHParams.PLAN_LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(AsInventoryCheckListDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(AsInventoryCheckListDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(AsInventoryCheckListDASCHParams.PLAN_LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsInventoryCheckListDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(AsInventoryCheckListDASCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(AsInventoryCheckListDASCHParams.STOCK_PIECE_QTY));
                lst_ASRSInventoryCheckWorkList_SetLineToolTip(line);
                _lcm_lst_ASRSInventoryCheckWorkList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
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
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH);
            dasch.close();
            // DFKLOOK コネクションクローズ追加
            DBUtil.close(dasch.getConnection());
            // DFKLOOK ここまで
        }
    }

    /**
	 * 印刷ボタン押下時の処理です。
     * @param confirm
     * @throws Exception
     */
    private void btn_Print_Click_Process(boolean confirm)
            throws Exception
    {
        // input validation.
        txt_SearchDate.validate(this, false);
        txt_SearchTime.validate(this, false);
        txt_ToSearchDate.validate(this, false);
        txt_ToSearchTime.validate(this, false);
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);

        WmsChecker chk = new WmsChecker();

        if (!chk.checkDate(txt_SearchDate.getDate(), txt_SearchTime.getTime()))
        {
            message.setMsgResourceKey(chk.getMessage());
            return;
        }

        if (!chk.checkDate(txt_ToSearchDate.getDate(), txt_ToSearchTime.getTime()))
        {
            message.setMsgResourceKey(chk.getMessage());
            return;
        }
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsInventoryCheckListDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsInventoryCheckListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsInventoryCheckListDASCHParams inparam = new AsInventoryCheckListDASCHParams();
            inparam.set(AsInventoryCheckListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsInventoryCheckListDASCHParams.WORK_PLACE_NO, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsInventoryCheckListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsInventoryCheckListDASCHParams.FROM_SEARCH_DATE, txt_SearchDate.getValue());
            inparam.set(AsInventoryCheckListDASCHParams.FROM_SEARCH_TIME, txt_SearchTime.getValue());
            inparam.set(AsInventoryCheckListDASCHParams.TO_SEARCH_DATE, txt_ToSearchDate.getValue());
            inparam.set(AsInventoryCheckListDASCHParams.TO_SEARCH_TIME, txt_ToSearchTime.getValue());

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
            exporter = factory.newPrinterExporter("AsrsInventoryCheckList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                AsrsInventoryCheckListParams expparam = new AsrsInventoryCheckListParams();
                expparam.set(AsrsInventoryCheckListParams.DFK_DS_NO, outparam.get(AsInventoryCheckListDASCHParams.DFK_DS_NO));
                expparam.set(AsrsInventoryCheckListParams.DFK_USER_ID, outparam.get(AsInventoryCheckListDASCHParams.DFK_USER_ID));
                expparam.set(AsrsInventoryCheckListParams.DFK_USER_NAME, outparam.get(AsInventoryCheckListDASCHParams.DFK_USER_NAME));
                expparam.set(AsrsInventoryCheckListParams.AREA_NO, outparam.get(AsInventoryCheckListDASCHParams.AREA_NO));
                expparam.set(AsrsInventoryCheckListParams.AREA_NAME, outparam.get(AsInventoryCheckListDASCHParams.AREA_NAME));
                expparam.set(AsrsInventoryCheckListParams.SYS_DAY, outparam.get(AsInventoryCheckListDASCHParams.SYS_DAY));
                expparam.set(AsrsInventoryCheckListParams.SYS_TIME, outparam.get(AsInventoryCheckListDASCHParams.SYS_TIME));
                expparam.set(AsrsInventoryCheckListParams.STATION_NO, outparam.get(AsInventoryCheckListDASCHParams.STATION_NO));
                expparam.set(AsrsInventoryCheckListParams.STATION_NAME, outparam.get(AsInventoryCheckListDASCHParams.STATION_NAME));
                expparam.set(AsrsInventoryCheckListParams.JOB_NO, outparam.get(AsInventoryCheckListDASCHParams.JOB_NO));
                expparam.set(AsrsInventoryCheckListParams.LOCATION_NO, outparam.get(AsInventoryCheckListDASCHParams.LOCATION_NO));
                expparam.set(AsrsInventoryCheckListParams.ITEM_CODE, outparam.get(AsInventoryCheckListDASCHParams.ITEM_CODE));
                expparam.set(AsrsInventoryCheckListParams.ITEM_NAME, outparam.get(AsInventoryCheckListDASCHParams.ITEM_NAME));
                expparam.set(AsrsInventoryCheckListParams.LOT_NO, outparam.get(AsInventoryCheckListDASCHParams.LOT_NO));
                expparam.set(AsrsInventoryCheckListParams.ENTERING_QTY, outparam.get(AsInventoryCheckListDASCHParams.ENTERING_QTY));
                expparam.set(AsrsInventoryCheckListParams.STOCK_CASE_QTY, outparam.get(AsInventoryCheckListDASCHParams.STOCK_CASE_QTY));
                expparam.set(AsrsInventoryCheckListParams.STOCK_PIECE_QTY, outparam.get(AsInventoryCheckListDASCHParams.STOCK_PIECE_QTY));
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
            part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
            part11List.add(txt_SearchDate.getStringValue(), txt_SearchTime.getStringValue(), "");
            part11List.add(txt_ToSearchDate.getStringValue(), txt_ToSearchTime.getStringValue(), "");
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
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        _pdm_pul_Area.setSelectedValue(null);
        _pdm_pul_WorkPlace.setSelectedValue(null);
        _pdm_pul_Station.setSelectedValue(null);
        txt_SearchDate.setValue(null);
        txt_SearchTime.setValue(null);
        txt_ToSearchDate.setValue(null);
        txt_ToSearchTime.setValue(null);

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
