// $Id: AsRetrievalListBusiness.java 7452 2010-03-08 04:28:56Z okayama $
package jp.co.daifuku.wms.asrs.display.retrievallist;

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
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.PullDownModel;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.LocationCellColumn;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
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
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.AsRetrievalListDASCH;
import jp.co.daifuku.wms.asrs.dasch.AsRetrievalListDASCHParams;
import jp.co.daifuku.wms.asrs.display.retrievallist.AsRetrievalList;
import jp.co.daifuku.wms.asrs.display.retrievallist.ViewStateKeys;
import jp.co.daifuku.wms.asrs.exporter.ASRSRetrievalListParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7452 $, $Date:: 2010-03-08 13:28:56 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class AsRetrievalListBusiness
        extends AsRetrievalList
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

    /** lst_ASRSRetrievalWorkList(HIDDEN_AREA_NO) */
    private static final ListCellKey KEY_HIDDEN_AREA_NO = new ListCellKey("HIDDEN_AREA_NO", new AreaCellColumn(), false, false);

    /** lst_ASRSRetrievalWorkList(HIDDEN_CUSTOMER_CODE) */
    private static final ListCellKey KEY_HIDDEN_CUSTOMER_CODE = new ListCellKey("HIDDEN_CUSTOMER_CODE", new StringCellColumn(), false, false);

    /** lst_ASRSRetrievalWorkList(HIDDEN_CUSTOMER_NAME) */
    private static final ListCellKey KEY_HIDDEN_CUSTOMER_NAME = new ListCellKey("HIDDEN_CUSTOMER_NAME", new StringCellColumn(), false, false);

    /** lst_ASRSRetrievalWorkList(LST_WORK_NO) */
    private static final ListCellKey KEY_LST_WORK_NO = new ListCellKey("LST_WORK_NO", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalWorkList(LST_PICKING_LOCATION_NO) */
    private static final ListCellKey KEY_LST_PICKING_LOCATION_NO = new ListCellKey("LST_PICKING_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_ASRSRetrievalWorkList(LST_ORDER_NO) */
    private static final ListCellKey KEY_LST_ORDER_NO = new ListCellKey("LST_ORDER_NO", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalWorkList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalWorkList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalWorkList(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_ASRSRetrievalWorkList(LST_PICKING_CASE_QTY) */
    private static final ListCellKey KEY_LST_PICKING_CASE_QTY = new ListCellKey("LST_PICKING_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ASRSRetrievalWorkList(LST_PICKING_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PICKING_PIECE_QTY = new ListCellKey("LST_PICKING_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ASRSRetrievalWorkList(LST_PLAN_LOT_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOT_NO = new ListCellKey("LST_PLAN_LOT_NO", new StringCellColumn(), true, false);

    /** lst_ASRSRetrievalWorkList keys */
    private static final ListCellKey[] LST_ASRSRETRIEVALWORKLIST_KEYS = {
        KEY_HIDDEN_AREA_NO,
        KEY_HIDDEN_CUSTOMER_CODE,
        KEY_HIDDEN_CUSTOMER_NAME,
        KEY_LST_WORK_NO,
        KEY_LST_ORDER_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ENTERING_QTY,
        KEY_LST_PICKING_CASE_QTY,
        KEY_LST_PLAN_LOT_NO,
        KEY_LST_PICKING_LOCATION_NO,
        KEY_LST_ITEM_NAME,
        KEY_LST_PICKING_PIECE_QTY,
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

    /** PullDownModel pul_FRetrievalWorkKind */
    private DefaultPullDownModel _pdm_pul_FRetrievalWorkKind;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_ASRSRetrievalWorkList */
    private ListCellModel _lcm_lst_ASRSRetrievalWorkList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsRetrievalListBusiness()
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

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);
        _pdm_pul_WorkPlace.setParent(_pdm_pul_Area);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
        _pdm_pul_Station.setParent(_pdm_pul_WorkPlace);

        // initialize pul_FRetrievalWorkKind.
        _pdm_pul_FRetrievalWorkKind = new DefaultPullDownModel(pul_FRetrievalWorkKind, locale, ui);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_ASRSRetrievalWorkList.
        _lcm_lst_ASRSRetrievalWorkList = new ListCellModel(lst_ASRSRetrievalWorkList, LST_ASRSRETRIEVALWORKLIST_KEYS, locale);
        _lcm_lst_ASRSRetrievalWorkList.setToolTipVisible(KEY_LST_WORK_NO, true);
        _lcm_lst_ASRSRetrievalWorkList.setToolTipVisible(KEY_LST_PICKING_LOCATION_NO, true);
        _lcm_lst_ASRSRetrievalWorkList.setToolTipVisible(KEY_LST_ORDER_NO, true);
        _lcm_lst_ASRSRetrievalWorkList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ASRSRetrievalWorkList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ASRSRetrievalWorkList.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_ASRSRetrievalWorkList.setToolTipVisible(KEY_LST_PICKING_CASE_QTY, true);
        _lcm_lst_ASRSRetrievalWorkList.setToolTipVisible(KEY_LST_PICKING_PIECE_QTY, true);
        _lcm_lst_ASRSRetrievalWorkList.setToolTipVisible(KEY_LST_PLAN_LOT_NO, true);
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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.RETRIEVAL, "", false);

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.RETRIEVAL);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.RETRIEVAL, Distribution.UNUSE);

            // load pul_FRetrievalWorkKind.
            _pdm_pul_FRetrievalWorkKind.init(conn);
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
    private void lst_ASRSRetrievalWorkList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("", KEY_HIDDEN_CUSTOMER_CODE);
        line.addToolTip("", KEY_HIDDEN_CUSTOMER_NAME);
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
    private void btn_Display_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsRetrievalListDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new AsRetrievalListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            AsRetrievalListDASCHParams inparam = new AsRetrievalListDASCHParams();
            inparam.set(AsRetrievalListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsRetrievalListDASCHParams.WORK_PLACE_NO, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsRetrievalListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsRetrievalListDASCHParams.FROM_SEARCH_DATE, txt_SearchDateFrom.getValue());
            inparam.set(AsRetrievalListDASCHParams.FROM_SEARCH_TIME, txt_SearchTimeFrom.getValue());
            inparam.set(AsRetrievalListDASCHParams.TO_SEARCH_DATE, txt_SearchDateTo.getValue());
            inparam.set(AsRetrievalListDASCHParams.TO_SEARCH_TIME, txt_SearchTimeTo.getValue());
            inparam.set(AsRetrievalListDASCHParams.RETRIEVAL_WORK_KIND, _pdm_pul_FRetrievalWorkKind.getSelectedValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_ASRSRetrievalWorkList.clear();

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
            _lcm_lst_ASRSRetrievalWorkList.clear();
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
        AsRetrievalListDASCH dasch = null;
        try
        {
            // get session.
            dasch = (AsRetrievalListDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_ASRSRetrievalWorkList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ASRSRetrievalWorkList.getNewLine();
                line.setValue(KEY_LST_WORK_NO, outparam.get(AsRetrievalListDASCHParams.WORK_NO));
                line.setValue(KEY_LST_PICKING_LOCATION_NO, outparam.get(AsRetrievalListDASCHParams.RETRIEVAL_LOCATION_NO));
                line.setValue(KEY_LST_ORDER_NO, outparam.get(AsRetrievalListDASCHParams.ORDER_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(AsRetrievalListDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(AsRetrievalListDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsRetrievalListDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_PICKING_CASE_QTY, outparam.get(AsRetrievalListDASCHParams.RETRIEVAL_CASE_QTY));
                line.setValue(KEY_LST_PICKING_PIECE_QTY, outparam.get(AsRetrievalListDASCHParams.RETRIEVAL_PIECE_QTY));
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(AsRetrievalListDASCHParams.PLAN_LOT_NO));
                viewState.setObject(ViewStateKeys.VS_STORAGE_WORK_KIND, _pdm_pul_FRetrievalWorkKind.getSelectedValue());
                line.setValue(KEY_HIDDEN_AREA_NO, outparam.get(AsRetrievalListDASCHParams.AREA_NO));
                line.setValue(KEY_HIDDEN_CUSTOMER_CODE, outparam.get(AsRetrievalListDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_HIDDEN_CUSTOMER_NAME, outparam.get(AsRetrievalListDASCHParams.CUSTOMER_NAME));
                lst_ASRSRetrievalWorkList_SetLineToolTip(line);
                _lcm_lst_ASRSRetrievalWorkList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ASRSRetrievalWorkList.clear();
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
        txt_SearchDateFrom.validate(this, false);
        txt_SearchTimeFrom.validate(this, false);
        txt_SearchDateTo.validate(this, false);
        txt_SearchTimeTo.validate(this, false);
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsRetrievalListDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsRetrievalListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsRetrievalListDASCHParams inparam = new AsRetrievalListDASCHParams();
            inparam.set(AsRetrievalListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsRetrievalListDASCHParams.WORK_PLACE_NO, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsRetrievalListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsRetrievalListDASCHParams.FROM_SEARCH_DATE, txt_SearchDateFrom.getValue());
            inparam.set(AsRetrievalListDASCHParams.FROM_SEARCH_TIME, txt_SearchTimeFrom.getValue());
            inparam.set(AsRetrievalListDASCHParams.TO_SEARCH_DATE, txt_SearchDateTo.getValue());
            inparam.set(AsRetrievalListDASCHParams.TO_SEARCH_TIME, txt_SearchTimeTo.getValue());
            inparam.set(AsRetrievalListDASCHParams.RETRIEVAL_WORK_KIND, _pdm_pul_FRetrievalWorkKind.getSelectedValue());

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
            exporter = factory.newPrinterExporter("ASRSRetrievalList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ASRSRetrievalListParams expparam = new ASRSRetrievalListParams();
                expparam.set(ASRSRetrievalListParams.DFK_DS_NO, outparam.get(AsRetrievalListDASCHParams.DFK_DS_NO));
                expparam.set(ASRSRetrievalListParams.DFK_USER_ID, outparam.get(AsRetrievalListDASCHParams.DFK_USER_ID));
                expparam.set(ASRSRetrievalListParams.DFK_USER_NAME, outparam.get(AsRetrievalListDASCHParams.DFK_USER_NAME));
                expparam.set(ASRSRetrievalListParams.AREA_NO, outparam.get(AsRetrievalListDASCHParams.AREA_NO));
                expparam.set(ASRSRetrievalListParams.AREA_NAME, outparam.get(AsRetrievalListDASCHParams.AREA_NAME));
                expparam.set(ASRSRetrievalListParams.STATION_NO, outparam.get(AsRetrievalListDASCHParams.STATION_NO));
                expparam.set(ASRSRetrievalListParams.STATION_NAME, outparam.get(AsRetrievalListDASCHParams.STATION_NAME));
                expparam.set(ASRSRetrievalListParams.SYS_DAY, outparam.get(AsRetrievalListDASCHParams.SYS_DAY));
                expparam.set(ASRSRetrievalListParams.SYS_TIME, outparam.get(AsRetrievalListDASCHParams.SYS_TIME));
                expparam.set(ASRSRetrievalListParams.BATCH_NO, outparam.get(AsRetrievalListDASCHParams.BATCH_NO));
                expparam.set(ASRSRetrievalListParams.ORDER_NO, outparam.get(AsRetrievalListDASCHParams.ORDER_NO));
                expparam.set(ASRSRetrievalListParams.CUSTOMER_CODE, outparam.get(AsRetrievalListDASCHParams.CUSTOMER_CODE));
                expparam.set(ASRSRetrievalListParams.CUSTOMER_NAME, outparam.get(AsRetrievalListDASCHParams.CUSTOMER_NAME));
                expparam.set(ASRSRetrievalListParams.JOB_NO, outparam.get(AsRetrievalListDASCHParams.WORK_NO));
                expparam.set(ASRSRetrievalListParams.RETRIEVAL_LOCATION_NO, outparam.get(AsRetrievalListDASCHParams.RETRIEVAL_LOCATION_NO));
                expparam.set(ASRSRetrievalListParams.ITEM_CODE, outparam.get(AsRetrievalListDASCHParams.ITEM_CODE));
                expparam.set(ASRSRetrievalListParams.ITEM_NAME, outparam.get(AsRetrievalListDASCHParams.ITEM_NAME));
                expparam.set(ASRSRetrievalListParams.LOT_NO, outparam.get(AsRetrievalListDASCHParams.PLAN_LOT_NO));
                expparam.set(ASRSRetrievalListParams.RETRIEVAL_PLAN_DAY, outparam.get(AsRetrievalListDASCHParams.RETRIEVAL_PLAN_DAY));
                expparam.set(ASRSRetrievalListParams.ENTERING_QTY, outparam.get(AsRetrievalListDASCHParams.ENTERING_QTY));
                expparam.set(ASRSRetrievalListParams.RETRIEVAL_CASE_QTY, outparam.get(AsRetrievalListDASCHParams.RETRIEVAL_CASE_QTY));
                expparam.set(ASRSRetrievalListParams.RETRIEVAL_PIECE_QTY, outparam.get(AsRetrievalListDASCHParams.RETRIEVAL_PIECE_QTY));
                expparam.set(ASRSRetrievalListParams.JAN, outparam.get(AsRetrievalListDASCHParams.JAN));
                expparam.set(ASRSRetrievalListParams.ITF, outparam.get(AsRetrievalListDASCHParams.ITF));
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
            part11List.add(txt_SearchDateFrom.getStringValue(), txt_SearchTimeFrom.getStringValue(), "");
            part11List.add(txt_SearchDateTo.getStringValue(), txt_SearchTimeTo.getStringValue(), "");
            part11List.add(_pdm_pul_FRetrievalWorkKind.getSelectedStringValue(), "");
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
        txt_SearchDateFrom.validate(this, false);
        txt_SearchTimeFrom.validate(this, false);
        txt_SearchDateTo.validate(this, false);
        txt_SearchTimeTo.validate(this, false);
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsRetrievalListDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsRetrievalListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsRetrievalListDASCHParams inparam = new AsRetrievalListDASCHParams();
            inparam.set(AsRetrievalListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsRetrievalListDASCHParams.WORK_PLACE_NO, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsRetrievalListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsRetrievalListDASCHParams.FROM_SEARCH_DATE, txt_SearchDateFrom.getValue());
            inparam.set(AsRetrievalListDASCHParams.FROM_SEARCH_TIME, txt_SearchTimeFrom.getValue());
            inparam.set(AsRetrievalListDASCHParams.TO_SEARCH_DATE, txt_SearchDateTo.getValue());
            inparam.set(AsRetrievalListDASCHParams.TO_SEARCH_TIME, txt_SearchTimeTo.getValue());
            inparam.set(AsRetrievalListDASCHParams.RETRIEVAL_WORK_KIND, _pdm_pul_FRetrievalWorkKind.getSelectedValue());

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
            exporter = factory.newExcelExporter("ASRSRetrievalList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ASRSRetrievalListParams expparam = new ASRSRetrievalListParams();
                expparam.set(ASRSRetrievalListParams.AREA_NO, outparam.get(AsRetrievalListDASCHParams.AREA_NO));
                expparam.set(ASRSRetrievalListParams.AREA_NAME, outparam.get(AsRetrievalListDASCHParams.AREA_NAME));
                expparam.set(ASRSRetrievalListParams.STATION_NO, outparam.get(AsRetrievalListDASCHParams.STATION_NO));
                expparam.set(ASRSRetrievalListParams.STATION_NAME, outparam.get(AsRetrievalListDASCHParams.STATION_NAME));
                expparam.set(ASRSRetrievalListParams.BATCH_NO, outparam.get(AsRetrievalListDASCHParams.BATCH_NO));
                expparam.set(ASRSRetrievalListParams.ORDER_NO, outparam.get(AsRetrievalListDASCHParams.ORDER_NO));
                expparam.set(ASRSRetrievalListParams.CUSTOMER_CODE, outparam.get(AsRetrievalListDASCHParams.CUSTOMER_CODE));
                expparam.set(ASRSRetrievalListParams.CUSTOMER_NAME, outparam.get(AsRetrievalListDASCHParams.CUSTOMER_NAME));
                expparam.set(ASRSRetrievalListParams.JOB_NO, outparam.get(AsRetrievalListDASCHParams.WORK_NO));
                expparam.set(ASRSRetrievalListParams.RETRIEVAL_LOCATION_NO, outparam.get(AsRetrievalListDASCHParams.RETRIEVAL_LOCATION_NO));
                expparam.set(ASRSRetrievalListParams.ITEM_CODE, outparam.get(AsRetrievalListDASCHParams.ITEM_CODE));
                expparam.set(ASRSRetrievalListParams.ITEM_NAME, outparam.get(AsRetrievalListDASCHParams.ITEM_NAME));
                expparam.set(ASRSRetrievalListParams.LOT_NO, outparam.get(AsRetrievalListDASCHParams.PLAN_LOT_NO));
                expparam.set(ASRSRetrievalListParams.RETRIEVAL_PLAN_DAY, outparam.get(AsRetrievalListDASCHParams.RETRIEVAL_PLAN_DAY));
                expparam.set(ASRSRetrievalListParams.ENTERING_QTY, outparam.get(AsRetrievalListDASCHParams.ENTERING_QTY));
                expparam.set(ASRSRetrievalListParams.RETRIEVAL_CASE_QTY, outparam.get(AsRetrievalListDASCHParams.RETRIEVAL_CASE_QTY));
                expparam.set(ASRSRetrievalListParams.RETRIEVAL_PIECE_QTY, outparam.get(AsRetrievalListDASCHParams.RETRIEVAL_PIECE_QTY));
                expparam.set(ASRSRetrievalListParams.JAN, outparam.get(AsRetrievalListDASCHParams.JAN));
                expparam.set(ASRSRetrievalListParams.ITF, outparam.get(AsRetrievalListDASCHParams.ITF));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // write part11 log.
            P11LogWriter part11Writer = new P11LogWriter(conn);
            Part11List part11List = new Part11List();
            part11List.add(_pdm_pul_Area.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
            part11List.add(txt_SearchDateFrom.getStringValue(), txt_SearchTimeFrom.getStringValue(), "");
            part11List.add(txt_SearchDateTo.getStringValue(), txt_SearchTimeTo.getStringValue(), "");
            part11List.add(_pdm_pul_FRetrievalWorkKind.getSelectedStringValue(), "");
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
        _pdm_pul_Area.setSelectedValue(null);
        _pdm_pul_WorkPlace.setSelectedValue(null);
        _pdm_pul_Station.setSelectedValue(null);
        txt_SearchDateFrom.setValue(null);
        txt_SearchTimeFrom.setValue(null);
        txt_SearchDateTo.setValue(null);
        txt_SearchTimeTo.setValue(null);
        _pdm_pul_FRetrievalWorkKind.setSelectedValue(null);

        // set focus.
        setFocus(pul_Area);
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
