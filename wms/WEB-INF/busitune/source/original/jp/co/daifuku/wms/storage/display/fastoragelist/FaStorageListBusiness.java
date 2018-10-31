// $Id: FaStorageListBusiness.java 7372 2010-03-05 04:24:40Z shibamoto $
package jp.co.daifuku.wms.storage.display.fastoragelist;

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
import jp.co.daifuku.bluedog.model.table.CheckBoxColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
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
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.uimodel.WmsAreaPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsStationPullDownModel;
import jp.co.daifuku.wms.base.util.uimodel.WmsWorkspacePullDownModel;
import jp.co.daifuku.wms.storage.dasch.FaStorageListDASCH;
import jp.co.daifuku.wms.storage.dasch.FaStorageListDASCHParams;
import jp.co.daifuku.wms.storage.display.fastoragelist.FaStorageList;
import jp.co.daifuku.wms.storage.display.fastoragelist.ViewStateKeys;
import jp.co.daifuku.wms.storage.exporter.AsStorageWorkListParams;
import jp.co.daifuku.wms.storage.exporter.StorageWorkListParams;
import jp.co.daifuku.wms.storage.schedule.StorageInParameter;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7372 $, $Date:: 2010-03-05 13:24:40 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class FaStorageListBusiness
        extends FaStorageList
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

    /** lst_FaStorageWorkList(HIDDEN_SETTING_UNIT_KEY) */
    private static final ListCellKey KEY_HIDDEN_SETTING_UNIT_KEY = new ListCellKey("HIDDEN_SETTING_UNIT_KEY", new StringCellColumn(), false, false);

    /** lst_FaStorageWorkList(HIDDEN_WORK_TYPE) */
    private static final ListCellKey KEY_HIDDEN_WORK_TYPE = new ListCellKey("HIDDEN_WORK_TYPE", new StringCellColumn(), false, false);

    /** lst_FaStorageWorkList(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new CheckBoxColumn(), true, true);

    /** lst_FaStorageWorkList(LST_STATION_NO) */
    private static final ListCellKey KEY_LST_STATION_NO = new ListCellKey("LST_STATION_NO", new StringCellColumn(), true, false);

    /** lst_FaStorageWorkList(LST_WORK_TYPE_NAME) */
    private static final ListCellKey KEY_LST_WORK_TYPE_NAME = new ListCellKey("LST_WORK_TYPE_NAME", new StringCellColumn(), true, false);

    /** lst_FaStorageWorkList(LST_SETTING_DATE) */
    private static final ListCellKey KEY_LST_SETTING_DATE = new ListCellKey("LST_SETTING_DATE", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_FaStorageWorkList keys */
    private static final ListCellKey[] LST_FASTORAGEWORKLIST_KEYS = {
        KEY_HIDDEN_SETTING_UNIT_KEY,
        KEY_HIDDEN_WORK_TYPE,
        KEY_LST_SELECT,
        KEY_LST_STATION_NO,
        KEY_LST_WORK_TYPE_NAME,
        KEY_LST_SETTING_DATE,
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

    /** PullDownModel pul_FFaStorageWorkKind */
    private DefaultPullDownModel _pdm_pul_FFaStorageWorkKind;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_FaStorageWorkList */
    private ScrollListCellModel _lcm_lst_FaStorageWorkList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaStorageListBusiness()
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
    public void btn_Print_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Print_Click_Process();
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
    public void btn_AllCheck_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheck_Click_Process();
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_AllCheckClear_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_AllCheckClear_Click_Process();
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

        // initialize pul_WorkPlace.
        _pdm_pul_WorkPlace = new WmsWorkspacePullDownModel(pul_WorkPlace, locale, ui);
        _pdm_pul_WorkPlace.setParent(_pdm_pul_Area);

        // initialize pul_Station.
        _pdm_pul_Station = new WmsStationPullDownModel(pul_Station, locale, ui);
        _pdm_pul_Station.setParent(_pdm_pul_WorkPlace);

        // initialize pul_FFaStorageWorkKind.
        _pdm_pul_FFaStorageWorkKind = new DefaultPullDownModel(pul_FFaStorageWorkKind, locale, ui);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_FaStorageWorkList.
        _lcm_lst_FaStorageWorkList = new ScrollListCellModel(lst_FaStorageWorkList, LST_FASTORAGEWORKLIST_KEYS, locale);
        _lcm_lst_FaStorageWorkList.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_FaStorageWorkList.setToolTipVisible(KEY_LST_STATION_NO, false);
        _lcm_lst_FaStorageWorkList.setToolTipVisible(KEY_LST_WORK_TYPE_NAME, false);
        _lcm_lst_FaStorageWorkList.setToolTipVisible(KEY_LST_SETTING_DATE, false);
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

            // load pul_WorkPlace.
            _pdm_pul_WorkPlace.init(conn, StationType.FLOORANDSTORAGE);

            // load pul_Station.
            _pdm_pul_Station.init(conn, StationType.FLOORANDSTORAGE, Distribution.UNUSE);

            // load pul_FFaStorageWorkKind.
            _pdm_pul_FFaStorageWorkKind.init(conn);
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
    private void lst_FaStorageWorkList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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
    private void page_Load_Process()
            throws Exception
    {
        // clear.
        btn_Print.setEnabled(false);
        btn_Preview.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
        btn_AllClear.setEnabled(false);
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
        FaStorageListDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "btn_Display_Click");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaStorageListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaStorageListDASCHParams inparam = new FaStorageListDASCHParams();
            inparam.set(FaStorageListDASCHParams.AREA_NO, _pdm_pul_Area.getSelectedValue());
            inparam.set(FaStorageListDASCHParams.STATION_NO, _pdm_pul_Station.getSelectedValue());
            inparam.set(FaStorageListDASCHParams.FROM_SEARCH_DAY, txt_FromSearchDate.getValue());
            inparam.set(FaStorageListDASCHParams.FROM_SEARCH_TIME, txt_FromSearchTime.getValue());
            inparam.set(FaStorageListDASCHParams.TO_SEARCH_DAY, txt_ToSearchDate.getValue());
            inparam.set(FaStorageListDASCHParams.TO_SEARCH_TIME, txt_ToSearchTime.getValue());
            inparam.set(FaStorageListDASCHParams.WORK_TYPE, _pdm_pul_FFaStorageWorkKind.getSelectedValue());

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_FaStorageWorkList.clear();

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
            btn_Print.setEnabled(true);
            btn_Preview.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaStorageWorkList.clear();
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
        FaStorageListDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaStorageListDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_FaStorageWorkList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_FaStorageWorkList.getNewLine();
                line.setValue(KEY_HIDDEN_SETTING_UNIT_KEY, outparam.get(FaStorageListDASCHParams.SETTING_UNIT_KEY));
                line.setValue(KEY_HIDDEN_WORK_TYPE, outparam.get(FaStorageListDASCHParams.WORK_TYPE));
                line.setValue(KEY_LST_STATION_NO, outparam.get(FaStorageListDASCHParams.STATION_NO));
                line.setValue(KEY_LST_WORK_TYPE_NAME, outparam.get(FaStorageListDASCHParams.WORK_TYPE_NAME));
                line.setValue(KEY_LST_SETTING_DATE, outparam.get(FaStorageListDASCHParams.SETTING_DATE));
                viewState.setObject(ViewStateKeys.FROM_SEARCH_DAY, txt_FromSearchDate.getValue());
                viewState.setObject(ViewStateKeys.FROM_SEARCH_TIME, txt_FromSearchTime.getValue());
                viewState.setObject(ViewStateKeys.TO_SEARCH_DAY, txt_ToSearchDate.getValue());
                viewState.setObject(ViewStateKeys.TO_SEARCH_TIME, txt_ToSearchTime.getValue());
                lst_FaStorageWorkList_SetLineToolTip(line);
                _lcm_lst_FaStorageWorkList.add(line);
            }

            // clear.
            btn_Print.setEnabled(true);
            btn_Preview.setEnabled(true);
            btn_AllCheck.setEnabled(true);
            btn_AllCheckClear.setEnabled(true);
            btn_AllClear.setEnabled(true);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_FaStorageWorkList.clear();
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
        _pdm_pul_WorkPlace.setSelectedValue(null);
        txt_FromSearchDate.setValue(null);
        txt_FromSearchTime.setValue(null);
        txt_ToSearchDate.setValue(null);
        txt_ToSearchTime.setValue(null);
        _pdm_pul_FFaStorageWorkKind.setSelectedValue(null);
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Print_Click_Process()
            throws Exception
    {
        // input validation.
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_FaStorageWorkList.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_FaStorageWorkList.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
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
        FaStorageListDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaStorageListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            main: for (int i = 1; i <= _lcm_lst_FaStorageWorkList.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaStorageWorkList.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // set input parameters.
                FaStorageListDASCHParams inparam = new FaStorageListDASCHParams();
                inparam.set(FaStorageListDASCHParams.SETTING_UNIT_KEY, line.getValue(KEY_HIDDEN_SETTING_UNIT_KEY));
                inparam.set(FaStorageListDASCHParams.WORK_TYPE, StorageInParameter.SEARCH_ASRS_STORAGE_LIST);

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
                if (exporter == null)
                {
                    ExporterFactory factory = new WmsExporterFactory(locale, ui);
                    exporter = factory.newPrinterExporter("StorageWorkList", false);
                    exporter.open();
                }

                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    StorageWorkListParams expparam = new StorageWorkListParams();
                    expparam.set(StorageWorkListParams.DFK_DS_NO, outparam.get(FaStorageListDASCHParams.DFK_DS_NO));
                    expparam.set(StorageWorkListParams.DFK_USER_ID, outparam.get(FaStorageListDASCHParams.DFK_USER_ID));
                    expparam.set(StorageWorkListParams.DFK_USER_NAME, outparam.get(FaStorageListDASCHParams.DFK_USER_NAME));
                    expparam.set(StorageWorkListParams.SYS_DAY, outparam.get(FaStorageListDASCHParams.SYS_DAY));
                    expparam.set(StorageWorkListParams.SYS_TIME, outparam.get(FaStorageListDASCHParams.SYS_TIME));
                    expparam.set(StorageWorkListParams.LIST_NO, outparam.get(FaStorageListDASCHParams.SETTING_UNIT_KEY));
                    expparam.set(StorageWorkListParams.FROM_SEARCH_DAY, viewState.getObject(ViewStateKeys.FROM_SEARCH_DAY));
                    expparam.set(StorageWorkListParams.FROM_SEARCH_TIME, viewState.getObject(ViewStateKeys.FROM_SEARCH_TIME));
                    expparam.set(StorageWorkListParams.TO_SEARCH_DAY, viewState.getObject(ViewStateKeys.TO_SEARCH_DAY));
                    expparam.set(StorageWorkListParams.TO_SEARCH_TIME, viewState.getObject(ViewStateKeys.TO_SEARCH_TIME));
                    expparam.set(StorageWorkListParams.AREA_NO, outparam.get(FaStorageListDASCHParams.AREA_NO));
                    expparam.set(StorageWorkListParams.LOCATION_NO, outparam.get(FaStorageListDASCHParams.LOCATION_NO));
                    expparam.set(StorageWorkListParams.ITEM_CODE, outparam.get(FaStorageListDASCHParams.ITEM_CODE));
                    expparam.set(StorageWorkListParams.ITEM_NAME, outparam.get(FaStorageListDASCHParams.ITEM_NAME));
                    expparam.set(StorageWorkListParams.LOT_NO, outparam.get(FaStorageListDASCHParams.LOT_NO));
                    expparam.set(StorageWorkListParams.WORK_QTY, outparam.get(FaStorageListDASCHParams.WORK_QTY));
                    if (!exporter.write(expparam))
                    {
                        break main;
                    }
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
            for (int i = 1; i <= _lcm_lst_FaStorageWorkList.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaStorageWorkList.get(i);
                lst_FaStorageWorkList.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                if (lst_FaStorageWorkList.getChecked(_lcm_lst_FaStorageWorkList.getColumnIndex(KEY_LST_SELECT)))
                {
                    part11List.add(line.getViewString(KEY_LST_STATION_NO), "");
                    part11List.add(line.getViewString(KEY_HIDDEN_WORK_TYPE), "");
                    part11List.add(line.getViewString(KEY_LST_SETTING_DATE), "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PRINT), part11List);
            }
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
        boolean existEditedRow = false;
        for (int i = 1; i <= _lcm_lst_FaStorageWorkList.size(); i++)
        {
            ListCellLine checkline = _lcm_lst_FaStorageWorkList.get(i);
            if (checkline.isAppend() || checkline.isEdited())
            {
                existEditedRow = true;
                break;
            }
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
        FaStorageListDASCH dasch = null;
        PrintExporter exporter = null;
        File downloadFile = null;
        try
        {
            // open connection.
            conn = ConnectionManager.getRequestConnection(this);
            dasch = new FaStorageListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            main: for (int i = 1; i <= _lcm_lst_FaStorageWorkList.size(); i++)
            {
                // exclusion unmodified row.
                ListCellLine line = _lcm_lst_FaStorageWorkList.get(i);
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                // set input parameters.
                FaStorageListDASCHParams inparam = new FaStorageListDASCHParams();
                inparam.set(FaStorageListDASCHParams.SETTING_UNIT_KEY, line.getValue(KEY_HIDDEN_SETTING_UNIT_KEY));
                inparam.set(FaStorageListDASCHParams.WORK_TYPE, StorageInParameter.SEARCH_FLOOR_STORAGE_LIST);

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
                if (exporter == null)
                {
                    ExporterFactory factory = new WmsExporterFactory(locale, ui);
                    exporter = factory.newPVExporter("AsStorageWorkList", getSession());
                    downloadFile = exporter.open();
                }

                // export.
                while (dasch.next())
                {
                    Params outparam = dasch.get();
                    AsStorageWorkListParams expparam = new AsStorageWorkListParams();
                    expparam.set(AsStorageWorkListParams.DFK_DS_NO, outparam.get(FaStorageListDASCHParams.DFK_DS_NO));
                    expparam.set(AsStorageWorkListParams.DFK_USER_ID, outparam.get(FaStorageListDASCHParams.DFK_USER_ID));
                    expparam.set(AsStorageWorkListParams.DFK_USER_NAME, outparam.get(FaStorageListDASCHParams.DFK_USER_NAME));
                    expparam.set(AsStorageWorkListParams.SYS_DAY, outparam.get(FaStorageListDASCHParams.SYS_DAY));
                    expparam.set(AsStorageWorkListParams.SYS_TIME, outparam.get(FaStorageListDASCHParams.SYS_TIME));
                    expparam.set(AsStorageWorkListParams.STATION_NO, outparam.get(FaStorageListDASCHParams.STATION_NO));
                    expparam.set(AsStorageWorkListParams.STATION_NAME, outparam.get(FaStorageListDASCHParams.STATION_NAME));
                    expparam.set(AsStorageWorkListParams.FROM_SEARCH_DAY, viewState.getObject(ViewStateKeys.FROM_SEARCH_DAY));
                    expparam.set(AsStorageWorkListParams.FROM_SEARCH_TIME, viewState.getObject(ViewStateKeys.FROM_SEARCH_TIME));
                    expparam.set(AsStorageWorkListParams.TO_SEARCH_DAY, viewState.getObject(ViewStateKeys.TO_SEARCH_DAY));
                    expparam.set(AsStorageWorkListParams.TO_SEARCH_TIME, viewState.getObject(ViewStateKeys.TO_SEARCH_TIME));
                    expparam.set(AsStorageWorkListParams.WORK_NO, outparam.get(FaStorageListDASCHParams.WORK_NO));
                    expparam.set(AsStorageWorkListParams.LOCATION_NO, outparam.get(FaStorageListDASCHParams.LOCATION_NO));
                    expparam.set(AsStorageWorkListParams.ITEM_CODE, outparam.get(FaStorageListDASCHParams.ITEM_CODE));
                    expparam.set(AsStorageWorkListParams.ITEM_NAME, outparam.get(FaStorageListDASCHParams.ITEM_NAME));
                    expparam.set(AsStorageWorkListParams.LOT_NO, outparam.get(FaStorageListDASCHParams.LOT_NO));
                    expparam.set(AsStorageWorkListParams.WORK_QTY, outparam.get(FaStorageListDASCHParams.WORK_QTY));
                    expparam.set(AsStorageWorkListParams.STOCK_QTY, outparam.get(FaStorageListDASCHParams.STOCK_QTY));
                    expparam.set(AsStorageWorkListParams.AREA_NO, outparam.get(FaStorageListDASCHParams.AREA_NO));
                    if (!exporter.write(expparam))
                    {
                        break main;
                    }
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
            for (int i = 1; i <= _lcm_lst_FaStorageWorkList.size(); i++)
            {
                ListCellLine line = _lcm_lst_FaStorageWorkList.get(i);
                lst_FaStorageWorkList.setCurrentRow(i);

                // exclusion unmodified row.
                if (!(line.isAppend() || line.isEdited()))
                {
                    continue;
                }

                P11LogWriter part11Writer = new P11LogWriter(conn);
                Part11List part11List = new Part11List();
                if (lst_FaStorageWorkList.getChecked(_lcm_lst_FaStorageWorkList.getColumnIndex(KEY_LST_SELECT)))
                {
                    part11List.add(line.getViewString(KEY_LST_STATION_NO), "");
                    part11List.add(line.getViewString(KEY_HIDDEN_WORK_TYPE), "");
                    part11List.add(line.getViewString(KEY_LST_SETTING_DATE), "");
                }

                part11Writer.createOperationLog(ui, ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_PREVIEW), part11List);
            }
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
    private void btn_AllCheck_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_FaStorageWorkList.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaStorageWorkList.get(i);
            lst_FaStorageWorkList.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.TRUE);
            lst_FaStorageWorkList_SetLineToolTip(clearLine);
            _lcm_lst_FaStorageWorkList.set(i, clearLine);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_AllCheckClear_Click_Process()
            throws Exception
    {
        // clear.
        for (int i = 1; i <= _lcm_lst_FaStorageWorkList.size(); i++)
        {
            ListCellLine clearLine = _lcm_lst_FaStorageWorkList.get(i);
            lst_FaStorageWorkList.setCurrentRow(i);
            clearLine.setValue(KEY_LST_SELECT, Boolean.FALSE);
            lst_FaStorageWorkList_SetLineToolTip(clearLine);
            _lcm_lst_FaStorageWorkList.set(i, clearLine);
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
        _lcm_lst_FaStorageWorkList.clear();
        _pager.clear();
        btn_Print.setEnabled(false);
        btn_Preview.setEnabled(false);
        btn_AllCheck.setEnabled(false);
        btn_AllCheckClear.setEnabled(false);
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
