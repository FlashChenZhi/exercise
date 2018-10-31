// $Id: AsStorageListBusiness.java 7596 2010-03-16 01:14:55Z ota $
package jp.co.daifuku.wms.asrs.display.storagelist;

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
import jp.co.daifuku.bluedog.model.DefaultPullDownModel;
import jp.co.daifuku.bluedog.model.PagerModel;
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
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
import jp.co.daifuku.wms.asrs.dasch.AsNoPlanStorageListDASCH;
import jp.co.daifuku.wms.asrs.dasch.AsNoPlanStorageListDASCHParams;
import jp.co.daifuku.wms.asrs.dasch.AsStorageListDASCH;
import jp.co.daifuku.wms.asrs.dasch.AsStorageListDASCHParams;
import jp.co.daifuku.wms.asrs.exporter.ASRSNoPlanStorageListParams;
import jp.co.daifuku.wms.asrs.exporter.ASRSStorageListParams;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.Distribution;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;

/**
 * AS/RS 入庫作業リスト発行の画面処理を行います。
 *
 * @version $Revision: 7596 $, $Date:: 2010-03-16 10:14:55 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class AsStorageListBusiness
        extends AsStorageList
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

    /** lst_ASRSStorageWorkList(HIDDEN_AREA_NO) */
    private static final ListCellKey KEY_HIDDEN_AREA_NO = new ListCellKey("HIDDEN_AREA_NO", new AreaCellColumn(), false, false);

    /** lst_ASRSStorageWorkList(LST_WORK_NO) */
    private static final ListCellKey KEY_LST_WORK_NO = new ListCellKey("LST_WORK_NO", new StringCellColumn(), true, false);

    /** lst_ASRSStorageWorkList(LST_STORAGE_LOCATION_NO) */
    private static final ListCellKey KEY_LST_STORAGE_LOCATION_NO = new ListCellKey("LST_STORAGE_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_ASRSStorageWorkList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ASRSStorageWorkList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ASRSStorageWorkList(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY = new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_ASRSStorageWorkList(LST_STORAGE_CASE_QTY) */
    private static final ListCellKey KEY_LST_STORAGE_CASE_QTY = new ListCellKey("LST_STORAGE_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ASRSStorageWorkList(LST_STORAGE_PIECE_QTY) */
    private static final ListCellKey KEY_LST_STORAGE_PIECE_QTY = new ListCellKey("LST_STORAGE_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ASRSStorageWorkList(LST_PLAN_LOT_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOT_NO = new ListCellKey("LST_PLAN_LOT_NO", new StringCellColumn(), true, false);

    /** lst_ASRSStorageWorkList keys */
    private static final ListCellKey[] LST_ASRSSTORAGEWORKLIST_KEYS = {
        KEY_HIDDEN_AREA_NO,
        KEY_LST_WORK_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ENTERING_QTY,
        KEY_LST_STORAGE_CASE_QTY,
        KEY_LST_PLAN_LOT_NO,
        KEY_LST_STORAGE_LOCATION_NO,
        KEY_LST_ITEM_NAME,
        KEY_LST_STORAGE_PIECE_QTY,
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

    /** PullDownModel pul_FStorageWorkKind */
    private DefaultPullDownModel _pdm_pul_FStorageWorkKind;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_ASRSStorageWorkList */
    private ListCellModel _lcm_lst_ASRSStorageWorkList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public AsStorageListBusiness()
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
        // DFKLOOK:ここから修正
        pgr_Common();
        // DFKLOOK:ここまで修正
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
        // DFKLOOK:ここから修正
        pgr_Common();
        // DFKLOOK:ここまで修正
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
        // DFKLOOK:ここから修正
        pgr_Common();
        // DFKLOOK:ここまで修正
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
        // DFKLOOK:ここから修正
        pgr_Common();
        // DFKLOOK:ここまで修正
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ASRSStorageWorkList_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ASRSStorageWorkList_ColumClick(ActionEvent e)
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
        // DFKLOOK:ここから修正
        pgr_Common();
        // DFKLOOK:ここまで修正
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
        // DFKLOOK:ここから修正
        pgr_Common();
        // DFKLOOK:ここまで修正
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
        // DFKLOOK:ここから修正
        pgr_Common();
        // DFKLOOK:ここまで修正
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
        // DFKLOOK:ここから修正
        pgr_Common();
        // DFKLOOK:ここまで修正
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

        // initialize pul_FStorageWorkKind.
        _pdm_pul_FStorageWorkKind = new DefaultPullDownModel(pul_FStorageWorkKind, locale, ui);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_ASRSStorageWorkList.
        _lcm_lst_ASRSStorageWorkList = new ListCellModel(lst_ASRSStorageWorkList, LST_ASRSSTORAGEWORKLIST_KEYS, locale);
        _lcm_lst_ASRSStorageWorkList.setToolTipVisible(KEY_LST_WORK_NO, true);
        _lcm_lst_ASRSStorageWorkList.setToolTipVisible(KEY_LST_STORAGE_LOCATION_NO, true);
        _lcm_lst_ASRSStorageWorkList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ASRSStorageWorkList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ASRSStorageWorkList.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_ASRSStorageWorkList.setToolTipVisible(KEY_LST_STORAGE_CASE_QTY, true);
        _lcm_lst_ASRSStorageWorkList.setToolTipVisible(KEY_LST_STORAGE_PIECE_QTY, true);
        _lcm_lst_ASRSStorageWorkList.setToolTipVisible(KEY_LST_PLAN_LOT_NO, true);

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
            _pdm_pul_Area.init(conn, AreaType.ASRS, StationType.STORAGE, "", false);

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.STORAGE);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.STORAGE, Distribution.UNUSE);

            // load pul_FStorageWorkKind.
            _pdm_pul_FStorageWorkKind.init(conn);

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
    private void lst_ASRSStorageWorkList_SetLineToolTip(ListCellLine line)
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
        setFocus(pul_Area);

    }

    /**
     *
     * @throws Exception
     */
    private void btn_Display_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // 作業種別：入庫
        if (SystemDefine.JOB_TYPE_STORAGE.equals(pul_FStorageWorkKind.getSelectedValue()))
        {
            storage();
        }
        // 作業種別：予定外入庫
        if (SystemDefine.JOB_TYPE_NOPLAN_STORAGE.equals(pul_FStorageWorkKind.getSelectedValue()))
        {
            noPlanStorage();
        }
        // DFKLOOK:ここまで修正
    }

    // DFKLOOK ここから
    /**
     * AS/RS入庫作業リストを表示します。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void storage()
            throws Exception
    {
        // 表示ボタン時、入力チェックをBusiTuneにて定義できない為、ロジック追加
        txt_SearchDateFrom.validate(this, false);
        txt_SearchTimeFrom.validate(this, false);
        txt_SearchDateTo.validate(this, false);
        txt_SearchTimeTo.validate(this, false);
        pul_Area.validate(this, true);
        pul_WorkPlace.validate(this, true);
        pul_Station.validate(this, true);

        WmsChecker chek = new WmsChecker();
        if (!chek.checkDate(txt_SearchDateFrom.getDate(), txt_SearchTimeFrom.getTime()))
        {
            message.setMsgResourceKey(chek.getMessage());
            return;
        }
        if (!chek.checkDate(txt_SearchDateTo.getDate(), txt_SearchTimeTo.getTime()))
        {
            message.setMsgResourceKey(chek.getMessage());
            return;
        }

        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsStorageListDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new AsStorageListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            AsStorageListDASCHParams inparam = new AsStorageListDASCHParams();
            inparam.set(AsStorageListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsStorageListDASCHParams.WORK_PLACE_NO, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsStorageListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsStorageListDASCHParams.FROM_SEARCH_DATE, txt_SearchDateFrom.getValue());
            inparam.set(AsStorageListDASCHParams.FROM_SEARCH_TIME, txt_SearchTimeFrom.getValue());
            inparam.set(AsStorageListDASCHParams.TO_SEARCH_DATE, txt_SearchDateTo.getValue());
            inparam.set(AsStorageListDASCHParams.TO_SEARCH_TIME, txt_SearchTimeTo.getValue());
            inparam.set(AsStorageListDASCHParams.STORAGE_WORK_KIND, _pdm_pul_FStorageWorkKind.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_STORAGE_WORK_KIND, _pdm_pul_FStorageWorkKind.getSelectedValue());
            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_ASRSStorageWorkList.clear();

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

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ASRSStorageWorkList.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                storage_SetList();
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
     * AS/RS予定外入庫作業リストを表示します。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void noPlanStorage()
            throws Exception
    {
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

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        AsNoPlanStorageListDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new AsNoPlanStorageListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            AsNoPlanStorageListDASCHParams inparam = new AsNoPlanStorageListDASCHParams();
            inparam.set(AsNoPlanStorageListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsNoPlanStorageListDASCHParams.WORK_PLACE_NO, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsNoPlanStorageListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsNoPlanStorageListDASCHParams.FROM_SEARCH_DATE, txt_SearchDateFrom.getValue());
            inparam.set(AsNoPlanStorageListDASCHParams.FROM_SEARCH_TIME, txt_SearchTimeFrom.getValue());
            inparam.set(AsNoPlanStorageListDASCHParams.TO_SEARCH_DATE, txt_SearchDateTo.getValue());
            inparam.set(AsNoPlanStorageListDASCHParams.TO_SEARCH_TIME, txt_SearchTimeTo.getValue());
            inparam.set(AsNoPlanStorageListDASCHParams.STORAGE_WORK_KIND, _pdm_pul_FStorageWorkKind.getSelectedValue());
            viewState.setObject(ViewStateKeys.VS_STORAGE_WORK_KIND, _pdm_pul_FStorageWorkKind.getSelectedValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_ASRSStorageWorkList.clear();

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
            _lcm_lst_ASRSStorageWorkList.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                noPlanStorage_SetList();
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
     *  AS/RS入庫作業リストを表示します。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void storage_SetList()
            throws Exception
    {
        AsStorageListDASCH dasch = null;
        try
        {
            // get session.
            dasch = (AsStorageListDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_ASRSStorageWorkList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ASRSStorageWorkList.getNewLine();
                line.setValue(KEY_LST_WORK_NO, outparam.get(AsStorageListDASCHParams.WORK_NO));
                line.setValue(KEY_LST_STORAGE_LOCATION_NO, outparam.get(AsStorageListDASCHParams.STORAGE_LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(AsStorageListDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(AsStorageListDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsStorageListDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STORAGE_CASE_QTY, outparam.get(AsStorageListDASCHParams.STORAGE_CASE_QTY));
                line.setValue(KEY_LST_STORAGE_PIECE_QTY, outparam.get(AsStorageListDASCHParams.STORAGE_PIECE_QTY));
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(AsStorageListDASCHParams.PLAN_LOT_NO));
                line.setValue(KEY_HIDDEN_AREA_NO, outparam.get(AsStorageListDASCHParams.AREA_NO));
                lst_ASRSStorageWorkList_SetLineToolTip(line);
                _lcm_lst_ASRSStorageWorkList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ASRSStorageWorkList.clear();
            disposeDasch();
        }
    }

    /**
     * AS/RS予定外入庫作業リストを表示します。
     * 
     * @throws Exception 全ての例外を報告します。
     */
    private void noPlanStorage_SetList()
            throws Exception
    {
        AsNoPlanStorageListDASCH dasch = null;
        try
        {
            // get session.
            dasch = (AsNoPlanStorageListDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_ASRSStorageWorkList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ASRSStorageWorkList.getNewLine();
                line.setValue(KEY_LST_WORK_NO, outparam.get(AsNoPlanStorageListDASCHParams.WORK_NO));
                line.setValue(KEY_LST_STORAGE_LOCATION_NO,
                        outparam.get(AsNoPlanStorageListDASCHParams.STORAGE_LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(AsNoPlanStorageListDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(AsNoPlanStorageListDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(AsNoPlanStorageListDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_STORAGE_CASE_QTY, outparam.get(AsNoPlanStorageListDASCHParams.STORAGE_CASE_QTY));
                line.setValue(KEY_LST_STORAGE_PIECE_QTY, outparam.get(AsNoPlanStorageListDASCHParams.STORAGE_PIECE_QTY));
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(AsNoPlanStorageListDASCHParams.PLAN_LOT_NO));
                line.setValue(KEY_HIDDEN_AREA_NO, outparam.get(AsStorageListDASCHParams.AREA_NO));
                lst_ASRSStorageWorkList_SetLineToolTip(line);
                _lcm_lst_ASRSStorageWorkList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ASRSStorageWorkList.clear();
            disposeDasch();
        }
    }
    // DFKLOOK:ここまで修正

    /**
     * 印刷ボタンが押された時に呼ばれます。
     *
     * @param confirm
     * @throws Exception
     */
    private void btn_Print_Click_Process(boolean confirm)
            throws Exception
    {
        // DFKLOOK:ここから修正
        // 作業種別：入庫
        if (SystemDefine.JOB_TYPE_STORAGE.equals(pul_FStorageWorkKind.getSelectedValue()))
        {
            printStorageList(confirm);
        }
        // 作業種別：予定外入庫
        if (SystemDefine.JOB_TYPE_NOPLAN_STORAGE.equals(pul_FStorageWorkKind.getSelectedValue()))
        {
            printNoPlanStorageList(confirm);
        }
        // DFKLOOK:ここまで修正
    }

    /**
     * XLSボタンが押された時に呼ばれます。
     *
     * @throws Exception
     */
    private void btn_XLS_Click_Process()
            throws Exception
    {
        // DFKLOOK:ここから修正
        // 作業種別：入庫
        if (SystemDefine.JOB_TYPE_STORAGE.equals(pul_FStorageWorkKind.getSelectedValue()))
        {
            xlsStorage();
        }
        // 作業種別：予定外入庫
        if (SystemDefine.JOB_TYPE_NOPLAN_STORAGE.equals(pul_FStorageWorkKind.getSelectedValue()))
        {
            xlsNoPlanStorage();
        }
        // DFKLOOK:ここまで修正
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
        _pdm_pul_FStorageWorkKind.setSelectedValue(null);

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

    // DFKLOOK:ここから修正
    /**
     * ページャーボタンが押下された時に呼ばれます。
     * 作業種別により、それぞれのリストを表示します。
     *
     * @throws Exception 全ての例外を報告します。
     */
    private void pgr_Common()
            throws Exception
    {
        // 作業種別：入庫
        if (SystemDefine.JOB_TYPE_STORAGE.equals(viewState.getString(ViewStateKeys.VS_STORAGE_WORK_KIND)))
        {
            storage_SetList();
        }
        // 作業種別：予定外入庫
        if (SystemDefine.JOB_TYPE_NOPLAN_STORAGE.equals(viewState.getString(ViewStateKeys.VS_STORAGE_WORK_KIND)))
        {
            noPlanStorage_SetList();
        }
    }

    /**
     * AS/RS入庫作業をXLS出力します。
     * 
     * @throws Exception 全ての例外を報告します。
     */
    private void xlsStorage()
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

        WmsChecker chek = new WmsChecker();
        if (!chek.checkDate(txt_SearchDateFrom.getDate(), txt_SearchTimeFrom.getTime()))
        {
            message.setMsgResourceKey(chek.getMessage());
            return;
        }
        if (!chek.checkDate(txt_SearchDateTo.getDate(), txt_SearchTimeTo.getTime()))
        {
            message.setMsgResourceKey(chek.getMessage());
            return;
        }
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsStorageListDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsStorageListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsStorageListDASCHParams inparam = new AsStorageListDASCHParams();
            inparam.set(AsStorageListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsStorageListDASCHParams.WORK_PLACE_NO, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsStorageListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsStorageListDASCHParams.FROM_SEARCH_DATE, txt_SearchDateFrom.getValue());
            inparam.set(AsStorageListDASCHParams.FROM_SEARCH_TIME, txt_SearchTimeFrom.getValue());
            inparam.set(AsStorageListDASCHParams.TO_SEARCH_DATE, txt_SearchDateTo.getValue());
            inparam.set(AsStorageListDASCHParams.TO_SEARCH_TIME, txt_SearchTimeTo.getValue());
            inparam.set(AsStorageListDASCHParams.STORAGE_WORK_KIND, _pdm_pul_FStorageWorkKind.getSelectedValue());

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
            exporter = factory.newExcelExporter("ASRSStorageList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ASRSStorageListParams expparam = new ASRSStorageListParams();
                expparam.set(ASRSStorageListParams.AREA_NO, outparam.get(AsStorageListDASCHParams.AREA_NO));
                expparam.set(ASRSStorageListParams.AREA_NAME, outparam.get(AsStorageListDASCHParams.AREA_NAME));
                expparam.set(ASRSStorageListParams.STATION_NO, outparam.get(AsStorageListDASCHParams.STATION_NO));
                expparam.set(ASRSStorageListParams.STATION_NAME, outparam.get(AsStorageListDASCHParams.STATION_NAME));
                expparam.set(ASRSStorageListParams.JOB_NO, outparam.get(AsStorageListDASCHParams.WORK_NO));
                expparam.set(ASRSStorageListParams.STORAGE_LOCATION_NO, outparam.get(AsStorageListDASCHParams.STORAGE_LOCATION_NO));
                expparam.set(ASRSStorageListParams.ITEM_CODE, outparam.get(AsStorageListDASCHParams.ITEM_CODE));
                expparam.set(ASRSStorageListParams.ITEM_NAME, outparam.get(AsStorageListDASCHParams.ITEM_NAME));
                expparam.set(ASRSStorageListParams.LOT_NO, outparam.get(AsStorageListDASCHParams.PLAN_LOT_NO));
                expparam.set(ASRSStorageListParams.STORAGE_PLAN_DAY, outparam.get(AsStorageListDASCHParams.STORAGE_PLAN_DAY));
                expparam.set(ASRSStorageListParams.ENTERING_QTY, outparam.get(AsStorageListDASCHParams.ENTERING_QTY));
                expparam.set(ASRSStorageListParams.STORAGE_CASE_QTY, outparam.get(AsStorageListDASCHParams.STORAGE_CASE_QTY));
                expparam.set(ASRSStorageListParams.STORAGE_PIECE_QTY, outparam.get(AsStorageListDASCHParams.STORAGE_PIECE_QTY));
                expparam.set(ASRSStorageListParams.JAN, outparam.get(AsStorageListDASCHParams.JAN));
                expparam.set(ASRSStorageListParams.ITF, outparam.get(AsStorageListDASCHParams.ITF));
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
            part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
            part11List.add(txt_SearchDateFrom.getStringValue(), txt_SearchTimeFrom.getStringValue(), "");
            part11List.add(txt_SearchDateTo.getStringValue(), txt_SearchTimeTo.getStringValue(), "");
            part11List.add(_pdm_pul_FStorageWorkKind.getSelectedStringValue(), "");
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
     * AS/RS予定外入庫作業をXLS出力します。
     * 
     * @throws Exception 全ての例外を報告します。
     */
    private void xlsNoPlanStorage()
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

        WmsChecker chek = new WmsChecker();
        if (!chek.checkDate(txt_SearchDateFrom.getDate(), txt_SearchTimeFrom.getTime()))
        {
            message.setMsgResourceKey(chek.getMessage());
            return;
        }
        if (!chek.checkDate(txt_SearchDateTo.getDate(), txt_SearchTimeTo.getTime()))
        {
            message.setMsgResourceKey(chek.getMessage());
            return;
        }
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsNoPlanStorageListDASCH dasch = null;
        Exporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsNoPlanStorageListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsNoPlanStorageListDASCHParams inparam = new AsNoPlanStorageListDASCHParams();
            inparam.set(AsNoPlanStorageListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsNoPlanStorageListDASCHParams.WORK_PLACE_NO, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsNoPlanStorageListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsNoPlanStorageListDASCHParams.FROM_SEARCH_DATE, txt_SearchDateFrom.getValue());
            inparam.set(AsNoPlanStorageListDASCHParams.FROM_SEARCH_TIME, txt_SearchTimeFrom.getValue());
            inparam.set(AsNoPlanStorageListDASCHParams.TO_SEARCH_DATE, txt_SearchDateTo.getValue());
            inparam.set(AsNoPlanStorageListDASCHParams.TO_SEARCH_TIME, txt_SearchTimeTo.getValue());
            inparam.set(AsNoPlanStorageListDASCHParams.STORAGE_WORK_KIND, _pdm_pul_FStorageWorkKind.getSelectedValue());

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
            exporter = factory.newExcelExporter("ASRSNoPlanStorageList", getSession());
            File downloadFile = exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ASRSNoPlanStorageListParams expparam = new ASRSNoPlanStorageListParams();
                expparam.set(ASRSNoPlanStorageListParams.AREA_NO, outparam.get(AsNoPlanStorageListDASCHParams.AREA_NO));
                expparam.set(ASRSNoPlanStorageListParams.AREA_NAME, outparam.get(AsNoPlanStorageListDASCHParams.AREA_NAME));
                expparam.set(ASRSNoPlanStorageListParams.STATION_NO, outparam.get(AsNoPlanStorageListDASCHParams.STATION_NO));
                expparam.set(ASRSNoPlanStorageListParams.STATION_NAME, outparam.get(AsNoPlanStorageListDASCHParams.STATION_NAME));
                expparam.set(ASRSNoPlanStorageListParams.JOB_NO, outparam.get(AsNoPlanStorageListDASCHParams.WORK_NO));
                expparam.set(ASRSNoPlanStorageListParams.STORAGE_LOCATION_NO,
                        outparam.get(AsNoPlanStorageListDASCHParams.STORAGE_LOCATION_NO));
                expparam.set(ASRSNoPlanStorageListParams.ITEM_CODE, outparam.get(AsNoPlanStorageListDASCHParams.ITEM_CODE));
                expparam.set(ASRSNoPlanStorageListParams.ITEM_NAME, outparam.get(AsNoPlanStorageListDASCHParams.ITEM_NAME));
                expparam.set(ASRSNoPlanStorageListParams.LOT_NO, outparam.get(AsNoPlanStorageListDASCHParams.PLAN_LOT_NO));
                expparam.set(ASRSNoPlanStorageListParams.ENTERING_QTY, outparam.get(AsNoPlanStorageListDASCHParams.ENTERING_QTY));
                expparam.set(ASRSNoPlanStorageListParams.STORAGE_CASE_QTY,
                        outparam.get(AsNoPlanStorageListDASCHParams.STORAGE_CASE_QTY));
                expparam.set(ASRSNoPlanStorageListParams.STORAGE_PIECE_QTY,
                        outparam.get(AsNoPlanStorageListDASCHParams.STORAGE_PIECE_QTY));
                expparam.set(ASRSNoPlanStorageListParams.JAN, outparam.get(AsNoPlanStorageListDASCHParams.JAN));
                expparam.set(ASRSNoPlanStorageListParams.ITF, outparam.get(AsNoPlanStorageListDASCHParams.ITF));
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
            part11List.add(_pdm_pul_WorkPlace.getSelectedStringValue(), "");
            part11List.add(_pdm_pul_Station.getSelectedStringValue(), "");
            part11List.add(txt_SearchDateFrom.getStringValue(), txt_SearchTimeFrom.getStringValue(), "");
            part11List.add(txt_SearchDateTo.getStringValue(), txt_SearchTimeTo.getStringValue(), "");
            part11List.add(_pdm_pul_FStorageWorkKind.getSelectedStringValue(), "");
            part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
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
                dasch.closeFinder();
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
     * AS/RS入庫作業リストを印刷します。
     * 
     * @param confirm
     * @throws Exception 全ての例外を報告します。
     */
    private void printStorageList(boolean confirm)
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

        
        WmsChecker chek = new WmsChecker();
        if (!chek.checkDate(txt_SearchDateFrom.getDate(), txt_SearchTimeFrom.getTime()))
        {
            message.setMsgResourceKey(chek.getMessage());
            return;
        }
        if (!chek.checkDate(txt_SearchDateTo.getDate(), txt_SearchTimeTo.getTime()))
        {
            message.setMsgResourceKey(chek.getMessage());
            return;
        }
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsStorageListDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsStorageListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsStorageListDASCHParams inparam = new AsStorageListDASCHParams();
            inparam.set(AsStorageListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsStorageListDASCHParams.WORK_PLACE_NO, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsStorageListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsStorageListDASCHParams.FROM_SEARCH_DATE, txt_SearchDateFrom.getValue());
            inparam.set(AsStorageListDASCHParams.FROM_SEARCH_TIME, txt_SearchTimeFrom.getValue());
            inparam.set(AsStorageListDASCHParams.TO_SEARCH_DATE, txt_SearchDateTo.getValue());
            inparam.set(AsStorageListDASCHParams.TO_SEARCH_TIME, txt_SearchTimeTo.getValue());
            inparam.set(AsStorageListDASCHParams.STORAGE_WORK_KIND, _pdm_pul_FStorageWorkKind.getSelectedValue());

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
            exporter = factory.newPrinterExporter("ASRSStorageList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ASRSStorageListParams expparam = new ASRSStorageListParams();
                expparam.set(ASRSStorageListParams.DFK_DS_NO, outparam.get(AsStorageListDASCHParams.DFK_DS_NO));
                expparam.set(ASRSStorageListParams.DFK_USER_ID, outparam.get(AsStorageListDASCHParams.DFK_USER_ID));
                expparam.set(ASRSStorageListParams.DFK_USER_NAME, outparam.get(AsStorageListDASCHParams.DFK_USER_NAME));
                expparam.set(ASRSStorageListParams.AREA_NO, outparam.get(AsStorageListDASCHParams.AREA_NO));
                expparam.set(ASRSStorageListParams.AREA_NAME, outparam.get(AsStorageListDASCHParams.AREA_NAME));
                expparam.set(ASRSStorageListParams.SYS_DAY, outparam.get(AsStorageListDASCHParams.SYS_DAY));
                expparam.set(ASRSStorageListParams.SYS_TIME, outparam.get(AsStorageListDASCHParams.SYS_TIME));
                expparam.set(ASRSStorageListParams.STATION_NO, outparam.get(AsStorageListDASCHParams.STATION_NO));
                expparam.set(ASRSStorageListParams.STATION_NAME, outparam.get(AsStorageListDASCHParams.STATION_NAME));
                expparam.set(ASRSStorageListParams.JOB_NO, outparam.get(AsStorageListDASCHParams.WORK_NO));
                expparam.set(ASRSStorageListParams.STORAGE_LOCATION_NO, outparam.get(AsStorageListDASCHParams.STORAGE_LOCATION_NO));
                expparam.set(ASRSStorageListParams.ITEM_CODE, outparam.get(AsStorageListDASCHParams.ITEM_CODE));
                expparam.set(ASRSStorageListParams.ITEM_NAME, outparam.get(AsStorageListDASCHParams.ITEM_NAME));
                expparam.set(ASRSStorageListParams.LOT_NO, outparam.get(AsStorageListDASCHParams.PLAN_LOT_NO));
                expparam.set(ASRSStorageListParams.STORAGE_PLAN_DAY, outparam.get(AsStorageListDASCHParams.STORAGE_PLAN_DAY));
                expparam.set(ASRSStorageListParams.ENTERING_QTY, outparam.get(AsStorageListDASCHParams.ENTERING_QTY));
                expparam.set(ASRSStorageListParams.STORAGE_CASE_QTY, outparam.get(AsStorageListDASCHParams.STORAGE_CASE_QTY));
                expparam.set(ASRSStorageListParams.STORAGE_PIECE_QTY, outparam.get(AsStorageListDASCHParams.STORAGE_PIECE_QTY));
                expparam.set(ASRSStorageListParams.JAN, outparam.get(AsStorageListDASCHParams.JAN));
                expparam.set(ASRSStorageListParams.ITF, outparam.get(AsStorageListDASCHParams.ITF));
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
            part11List.add(_pdm_pul_FStorageWorkKind.getSelectedStringValue(), "");
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
     * AS/RS予定外入庫作業リストを印刷します。
     * 
     * @param confirm
     * @throws Exception 全ての例外を報告します。
     */
    private void printNoPlanStorageList(boolean confirm)
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

        WmsChecker chek = new WmsChecker();
        if (!chek.checkDate(txt_SearchDateFrom.getDate(), txt_SearchTimeFrom.getTime()))
        {
            message.setMsgResourceKey(chek.getMessage());
            return;
        }
        if (!chek.checkDate(txt_SearchDateTo.getDate(), txt_SearchTimeTo.getTime()))
        {
            message.setMsgResourceKey(chek.getMessage());
            return;
        }
        
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        AsNoPlanStorageListDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new AsNoPlanStorageListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsNoPlanStorageListDASCHParams inparam = new AsNoPlanStorageListDASCHParams();
            inparam.set(AsNoPlanStorageListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(AsNoPlanStorageListDASCHParams.WORK_PLACE_NO, _pdm_pul_WorkPlace.getSelectedValue());
            inparam.set(AsNoPlanStorageListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(AsNoPlanStorageListDASCHParams.FROM_SEARCH_DATE, txt_SearchDateFrom.getValue());
            inparam.set(AsNoPlanStorageListDASCHParams.FROM_SEARCH_TIME, txt_SearchTimeFrom.getValue());
            inparam.set(AsNoPlanStorageListDASCHParams.TO_SEARCH_DATE, txt_SearchDateTo.getValue());
            inparam.set(AsNoPlanStorageListDASCHParams.TO_SEARCH_TIME, txt_SearchTimeTo.getValue());
            inparam.set(AsNoPlanStorageListDASCHParams.STORAGE_WORK_KIND, _pdm_pul_FStorageWorkKind.getSelectedValue());

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
            exporter = factory.newPrinterExporter("ASRSNoPlanStorageList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ASRSNoPlanStorageListParams expparam = new ASRSNoPlanStorageListParams();
                expparam.set(ASRSNoPlanStorageListParams.DFK_DS_NO, outparam.get(AsNoPlanStorageListDASCHParams.DFK_DS_NO));
                expparam.set(ASRSNoPlanStorageListParams.DFK_USER_ID, outparam.get(AsNoPlanStorageListDASCHParams.DFK_USER_ID));
                expparam.set(ASRSNoPlanStorageListParams.DFK_USER_NAME, outparam.get(AsNoPlanStorageListDASCHParams.DFK_USER_NAME));
                expparam.set(ASRSNoPlanStorageListParams.AREA_NO, outparam.get(AsNoPlanStorageListDASCHParams.AREA_NO));
                expparam.set(ASRSNoPlanStorageListParams.AREA_NAME, outparam.get(AsNoPlanStorageListDASCHParams.AREA_NAME));
                expparam.set(ASRSNoPlanStorageListParams.SYS_DAY, outparam.get(AsNoPlanStorageListDASCHParams.SYS_DAY));
                expparam.set(ASRSNoPlanStorageListParams.SYS_TIME, outparam.get(AsNoPlanStorageListDASCHParams.SYS_TIME));
                expparam.set(ASRSNoPlanStorageListParams.STATION_NO, outparam.get(AsNoPlanStorageListDASCHParams.STATION_NO));
                expparam.set(ASRSNoPlanStorageListParams.STATION_NAME, outparam.get(AsNoPlanStorageListDASCHParams.STATION_NAME));
                expparam.set(ASRSNoPlanStorageListParams.JOB_NO, outparam.get(AsNoPlanStorageListDASCHParams.WORK_NO));
                expparam.set(ASRSNoPlanStorageListParams.STORAGE_LOCATION_NO,
                        outparam.get(AsNoPlanStorageListDASCHParams.STORAGE_LOCATION_NO));
                expparam.set(ASRSNoPlanStorageListParams.ITEM_CODE, outparam.get(AsNoPlanStorageListDASCHParams.ITEM_CODE));
                expparam.set(ASRSNoPlanStorageListParams.ITEM_NAME, outparam.get(AsNoPlanStorageListDASCHParams.ITEM_NAME));
                expparam.set(ASRSNoPlanStorageListParams.LOT_NO, outparam.get(AsNoPlanStorageListDASCHParams.PLAN_LOT_NO));
                expparam.set(ASRSNoPlanStorageListParams.ENTERING_QTY, outparam.get(AsNoPlanStorageListDASCHParams.ENTERING_QTY));
                expparam.set(ASRSNoPlanStorageListParams.STORAGE_CASE_QTY,
                        outparam.get(AsNoPlanStorageListDASCHParams.STORAGE_CASE_QTY));
                expparam.set(ASRSNoPlanStorageListParams.STORAGE_PIECE_QTY,
                        outparam.get(AsNoPlanStorageListDASCHParams.STORAGE_PIECE_QTY));
                expparam.set(ASRSNoPlanStorageListParams.JAN, outparam.get(AsNoPlanStorageListDASCHParams.JAN));
                expparam.set(ASRSNoPlanStorageListParams.ITF, outparam.get(AsNoPlanStorageListDASCHParams.ITF));
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
            part11List.add(_pdm_pul_FStorageWorkKind.getSelectedStringValue(), "");
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
                dasch.closeFinder();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
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
