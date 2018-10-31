// $Id: DeadStockInquiryBusiness.java 7521 2010-03-13 05:53:55Z shibamoto $
package jp.co.daifuku.wms.stock.display.deadstockinquiry;

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
import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.ExceptionHandler;
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
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.stock.dasch.DeadStockInquiryDASCH;
import jp.co.daifuku.wms.stock.dasch.DeadStockInquiryDASCHParams;
import jp.co.daifuku.wms.stock.exporter.DeadStockInqListParams;

/**
 * 長期滞留品在庫照会の画面処理を行います。
 * 
 * @version $Revision: 7521 $, $Date: 2010-03-13 14:53:55 +0900 (土, 13 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class DeadStockInquiryBusiness
        extends DeadStockInquiry
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

    /** lst_LongDwellList(LST_RETRIEVAL_DAY) */
    private static final ListCellKey KEY_LST_RETRIEVAL_DAY = new ListCellKey("LST_RETRIEVAL_DAY", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG, DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_LongDwellList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_LongDwellList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_LongDwellList(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_LongDwellList(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_LongDwellList(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_LongDwellList(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_LongDwellList(LST_STOCK_CASE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_CASE_QTY = new ListCellKey("LST_STOCK_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_LongDwellList(LST_STOCK_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STOCK_PIECE_QTY = new ListCellKey("LST_STOCK_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_LongDwellList(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_LongDwellList(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_LongDwellList kyes */
    private static final ListCellKey[] LST_LONGDWELLLIST_KEYS = {
        KEY_LST_RETRIEVAL_DAY,
        KEY_LST_ITEM_CODE,
        KEY_LST_LOT_NO,
        KEY_LST_AREA_NO,
        KEY_LST_ENTERING_QTY,
        KEY_LST_STOCK_CASE_QTY,
        KEY_LST_JAN,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOCATION_NO,
        KEY_LST_STOCK_PIECE_QTY,
        KEY_LST_ITF,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_LongDwellList */
    private ListCellModel _lcm_lst_LongDwellList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public DeadStockInquiryBusiness()
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
	 * ＸＬＳボタンが押下されたときに呼ばれます。<BR>
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Xls_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Xls_Click_Process();
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
        Locale locale = httpRequest.getLocale();

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_LongDwellList.
        _lcm_lst_LongDwellList = new ListCellModel(lst_LongDwellList, LST_LONGDWELLLIST_KEYS, locale);
        _lcm_lst_LongDwellList.setToolTipVisible(KEY_LST_RETRIEVAL_DAY, true);
        _lcm_lst_LongDwellList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_LongDwellList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_LongDwellList.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_LongDwellList.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_LongDwellList.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_LongDwellList.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_LongDwellList.setToolTipVisible(KEY_LST_STOCK_CASE_QTY, true);
        _lcm_lst_LongDwellList.setToolTipVisible(KEY_LST_STOCK_PIECE_QTY, true);
        _lcm_lst_LongDwellList.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_LongDwellList.setToolTipVisible(KEY_LST_ITF, true);

    }

    /**
	 * プルダウンの設定を行います。
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
	 * ツールチップの設定を行います。
     * @param line ListCellLine
     * @throws Exception
     */
    private void lst_LongDwellList_SetLineToolTip(ListCellLine line)
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
        setFocus(txt_LastRetrievalDate);

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
        // input validation.
        txt_LastRetrievalDate.validate(this, true);
        // DFKLOOK ここまで
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        DeadStockInquiryDASCH dasch = null;
        // DFKLOOK セッション保持フラグ追加
        boolean isSuccess = false;
        // DFKLOOK ここまで
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new DeadStockInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            DeadStockInquiryDASCHParams inparam = new DeadStockInquiryDASCHParams();
            inparam.set(DeadStockInquiryDASCHParams.LAST_RETRIEVAL_DATE, txt_LastRetrievalDate.getValue());
            inparam.set(DeadStockInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_LongDwellList.clear();

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
                ListCellLine line = _lcm_lst_LongDwellList.getNewLine();
                line.setValue(KEY_LST_RETRIEVAL_DAY, outparam.get(DeadStockInquiryDASCHParams.RETRIEVAL_DAY));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(DeadStockInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(DeadStockInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(DeadStockInquiryDASCHParams.LOT_NO));
                line.setValue(KEY_LST_AREA_NO, outparam.get(DeadStockInquiryDASCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(DeadStockInquiryDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(DeadStockInquiryDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(DeadStockInquiryDASCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(DeadStockInquiryDASCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_JAN, outparam.get(DeadStockInquiryDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(DeadStockInquiryDASCHParams.ITF));
                lst_LongDwellList_SetLineToolTip(line);
                _lcm_lst_LongDwellList.add(line);
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
        DeadStockInquiryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (DeadStockInquiryDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_LongDwellList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LongDwellList.getNewLine();
                line.setValue(KEY_LST_RETRIEVAL_DAY, outparam.get(DeadStockInquiryDASCHParams.RETRIEVAL_DAY));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(DeadStockInquiryDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(DeadStockInquiryDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(DeadStockInquiryDASCHParams.LOT_NO));
                line.setValue(KEY_LST_AREA_NO, outparam.get(DeadStockInquiryDASCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(DeadStockInquiryDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(DeadStockInquiryDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STOCK_CASE_QTY, outparam.get(DeadStockInquiryDASCHParams.STOCK_CASE_QTY));
                line.setValue(KEY_LST_STOCK_PIECE_QTY, outparam.get(DeadStockInquiryDASCHParams.STOCK_PIECE_QTY));
                line.setValue(KEY_LST_JAN, outparam.get(DeadStockInquiryDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(DeadStockInquiryDASCHParams.ITF));
                lst_LongDwellList_SetLineToolTip(line);
                _lcm_lst_LongDwellList.add(line);
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
        txt_LastRetrievalDate.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        DeadStockInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new DeadStockInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            DeadStockInquiryDASCHParams inparam = new DeadStockInquiryDASCHParams();
            inparam.set(DeadStockInquiryDASCHParams.LAST_RETRIEVAL_DATE, txt_LastRetrievalDate.getValue());
            inparam.set(DeadStockInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

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
            exporter = factory.newPrinterExporter("DeadStockInqList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                DeadStockInqListParams expparam = new DeadStockInqListParams();
                expparam.set(DeadStockInqListParams.DFK_DS_NO, outparam.get(DeadStockInquiryDASCHParams.DFK_DS_NO));
                expparam.set(DeadStockInqListParams.DFK_USER_ID, outparam.get(DeadStockInquiryDASCHParams.DFK_USER_ID));
                expparam.set(DeadStockInqListParams.DFK_USER_NAME, outparam.get(DeadStockInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(DeadStockInqListParams.TO_RETRIEVAL_DAY, inparam.get(DeadStockInquiryDASCHParams.LAST_RETRIEVAL_DATE));
                expparam.set(DeadStockInqListParams.SYS_DAY, outparam.get(DeadStockInquiryDASCHParams.SYS_DAY));
                expparam.set(DeadStockInqListParams.SYS_TIME, outparam.get(DeadStockInquiryDASCHParams.SYS_TIME));
                expparam.set(DeadStockInqListParams.RETRIEVAL_DAY, outparam.get(DeadStockInquiryDASCHParams.RETRIEVAL_DAY));
                expparam.set(DeadStockInqListParams.ITEM_CODE, outparam.get(DeadStockInquiryDASCHParams.ITEM_CODE));
                expparam.set(DeadStockInqListParams.ITEM_NAME, outparam.get(DeadStockInquiryDASCHParams.ITEM_NAME));
                expparam.set(DeadStockInqListParams.LOT_NO, outparam.get(DeadStockInquiryDASCHParams.LOT_NO));
                expparam.set(DeadStockInqListParams.AREA_NO, outparam.get(DeadStockInquiryDASCHParams.AREA_NO));
                expparam.set(DeadStockInqListParams.LOCATION_NO, outparam.get(DeadStockInquiryDASCHParams.LOCATION_NO));
                expparam.set(DeadStockInqListParams.ENTERING_QTY, outparam.get(DeadStockInquiryDASCHParams.ENTERING_QTY));
                expparam.set(DeadStockInqListParams.STOCK_CASE_QTY, outparam.get(DeadStockInquiryDASCHParams.STOCK_CASE_QTY));
                expparam.set(DeadStockInqListParams.STOCK_PIECE_QTY, outparam.get(DeadStockInquiryDASCHParams.STOCK_PIECE_QTY));
                expparam.set(DeadStockInqListParams.JAN, outparam.get(DeadStockInquiryDASCHParams.JAN));
                expparam.set(DeadStockInqListParams.ITF, outparam.get(DeadStockInquiryDASCHParams.ITF));
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
            part11List.add(txt_LastRetrievalDate.getStringValue(), "");
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
            DBUtil.close(conn);
        }
    }

    /**
	 * XLSボタン押下時の処理です。
     * @throws Exception
     */
    private void btn_Xls_Click_Process()
            throws Exception
    {
        // input validation.
        txt_LastRetrievalDate.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        DeadStockInquiryDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new DeadStockInquiryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            DeadStockInquiryDASCHParams inparam = new DeadStockInquiryDASCHParams();
            inparam.set(DeadStockInquiryDASCHParams.LAST_RETRIEVAL_DATE, txt_LastRetrievalDate.getValue());
            inparam.set(DeadStockInquiryDASCHParams.CONSIGNOR_CODE, WmsParam.DEFAULT_CONSIGNOR_CODE);

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
            exporter = factory.newExcelExporter("DeadStockInqList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                DeadStockInqListParams expparam = new DeadStockInqListParams();
                expparam.set(DeadStockInqListParams.RETRIEVAL_DAY, outparam.get(DeadStockInquiryDASCHParams.RETRIEVAL_DAY));
                expparam.set(DeadStockInqListParams.ITEM_CODE, outparam.get(DeadStockInquiryDASCHParams.ITEM_CODE));
                expparam.set(DeadStockInqListParams.ITEM_NAME, outparam.get(DeadStockInquiryDASCHParams.ITEM_NAME));
                expparam.set(DeadStockInqListParams.LOT_NO, outparam.get(DeadStockInquiryDASCHParams.LOT_NO));
                expparam.set(DeadStockInqListParams.AREA_NO, outparam.get(DeadStockInquiryDASCHParams.AREA_NO));
                expparam.set(DeadStockInqListParams.LOCATION_NO, outparam.get(DeadStockInquiryDASCHParams.LOCATION_NO));
                expparam.set(DeadStockInqListParams.ENTERING_QTY, outparam.get(DeadStockInquiryDASCHParams.ENTERING_QTY));
                expparam.set(DeadStockInqListParams.STOCK_CASE_QTY, outparam.get(DeadStockInquiryDASCHParams.STOCK_CASE_QTY));
                expparam.set(DeadStockInqListParams.STOCK_PIECE_QTY, outparam.get(DeadStockInquiryDASCHParams.STOCK_PIECE_QTY));
                expparam.set(DeadStockInqListParams.JAN, outparam.get(DeadStockInquiryDASCHParams.JAN));
                expparam.set(DeadStockInqListParams.ITF, outparam.get(DeadStockInquiryDASCHParams.ITF));
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
            part11List.add(txt_LastRetrievalDate.getStringValue(), "");
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
            DBUtil.close(conn);
        }
    }

    /**
	 * クリアボタン押下時の処理です。
     * @throws Exception
     */
    private void btn_Clear_Click_Process()
            throws Exception
    {
        // clear.
        txt_LastRetrievalDate.setValue(null);

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
