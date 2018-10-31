// $Id: FaLstAddLocationBusiness.java 6863 2010-01-25 00:42:48Z okayama $
package jp.co.daifuku.wms.stock.listbox.add;

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
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.stock.dasch.FaLstAddLocationDASCH;
import jp.co.daifuku.wms.stock.dasch.FaLstAddLocationDASCHParams;
import jp.co.daifuku.wms.stock.listbox.add.FaLstAddLocation;
import jp.co.daifuku.wms.stock.listbox.add.FaLstAddLocationParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 6863 $, $Date:: 2010-01-25 09:42:48 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class FaLstAddLocationBusiness
        extends FaLstAddLocation
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

    /** lst_AddLocationList(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE = new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_AddLocationList(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new StringCellColumn(), true, false);

    /** lst_AddLocationList(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_AddLocationList(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO = new ListCellKey("LST_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_AddLocationList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_AddLocationList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_AddLocationList(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_AddLocationList(LST_STOCK_QTY) */
    private static final ListCellKey KEY_LST_STOCK_QTY = new ListCellKey("LST_STOCK_QTY", new NumberCellColumn(0), true, false);

    /** lst_AddLocationList(LST_STORAGE_DATETIME) */
    private static final ListCellKey KEY_LST_STORAGE_DATETIME = new ListCellKey("LST_STORAGE_DATETIME", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_SearchCondition keys */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
        KEY_LST_SEARCH_CONDITION,
        KEY_LST_COLUMN_2,
        KEY_LST_COLUMN_3,
        KEY_LST_COLUMN_4,
    };

    /** lst_AddLocationList keys */
    private static final ListCellKey[] LST_ADDLOCATIONLIST_KEYS = {
        KEY_HIDDEN_CONSIGNOR_CODE,
        KEY_LST_SELECT,
        KEY_LST_AREA_NO,
        KEY_LST_LOCATION_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOT_NO,
        KEY_LST_STOCK_QTY,
        KEY_LST_STORAGE_DATETIME,
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

    /** ListCellModel lst_AddLocationList */
    private ScrollListCellModel _lcm_lst_AddLocationList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaLstAddLocationBusiness()
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
    public void lst_AddLocationList_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_AddLocationList.getActiveCol();

        // choose process.
        if (_lcm_lst_AddLocationList.getColumnIndex(KEY_LST_SELECT) == activeCol)
        {
            // process call.
            lst_Select_Click_Process();
        }
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

        // initialize lst_SearchCondition.
        _lcm_lst_SearchCondition = new ListCellModel(lst_SearchCondition, LST_SEARCHCONDITION_KEYS, locale);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_2, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_3, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_4, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U}, locale);

        // initialize lst_AddLocationList.
        _lcm_lst_AddLocationList = new ScrollListCellModel(lst_AddLocationList, LST_ADDLOCATIONLIST_KEYS, locale);
        _lcm_lst_AddLocationList.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_AddLocationList.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_AddLocationList.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_AddLocationList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_AddLocationList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_AddLocationList.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_AddLocationList.setToolTipVisible(KEY_LST_STOCK_QTY, true);
        _lcm_lst_AddLocationList.setToolTipVisible(KEY_LST_STORAGE_DATETIME, true);
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
            DBUtil.close(dasch.getConnection());
        }
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
    private void lst_AddLocationList_SetLineToolTip(ListCellLine line)
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
        if (eventSource.equals("page_Load"))
        {
            // process call.
            page_Load_SetList();
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

        Connection conn = null;
        FaLstAddLocationDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new FaLstAddLocationDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            FaLstAddLocationDASCHParams inparam = new FaLstAddLocationDASCHParams();
            FaLstAddLocationParams requestParam = new FaLstAddLocationParams(request);
            inparam.set(FaLstAddLocationDASCHParams.AREA_NO, requestParam.get(FaLstAddLocationParams.AREA_NO));
            inparam.set(FaLstAddLocationDASCHParams.ITEM_CODE, requestParam.get(FaLstAddLocationParams.ITEM_CODE));
            inparam.set(FaLstAddLocationDASCHParams.LOCATION_NO, requestParam.get(FaLstAddLocationParams.LOCATION_NO));
            inparam.set(FaLstAddLocationDASCHParams.LOT_NO, requestParam.get(FaLstAddLocationParams.LOT_NO));
            inparam.set(FaLstAddLocationDASCHParams.CONSIGNOR_CODE, requestParam.get(FaLstAddLocationParams.CONSIGNOR_CODE));

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_AddLocationList.clear();

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
            session.setAttribute(_KEY_DASCH_LIST, dasch);
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_AddLocationList.clear();
        }
        finally
        {
            if (isSuccess)
            {
                // set list.
                page_Load_SetList();
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
    private void page_Load_SetList()
            throws Exception
    {
        FaLstAddLocationDASCH dasch = null;
        try
        {
            // get session.
            dasch = (FaLstAddLocationDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_AddLocationList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AddLocationList.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(FaLstAddLocationDASCHParams.SELECT));
                line.setValue(KEY_LST_AREA_NO, outparam.get(FaLstAddLocationDASCHParams.AREA_NO));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(FaLstAddLocationDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(FaLstAddLocationDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(FaLstAddLocationDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(FaLstAddLocationDASCHParams.LOT_NO));
                line.setValue(KEY_LST_STOCK_QTY, outparam.get(FaLstAddLocationDASCHParams.STOCK_QTY));
                line.setValue(KEY_LST_STORAGE_DATETIME, outparam.get(FaLstAddLocationDASCHParams.STORAGE_DATETIME));
                lst_AddLocationList_SetLineToolTip(line);
                _lcm_lst_AddLocationList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_AddLocationList.clear();
            disposeDasch();
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
        int row = lst_AddLocationList.getActiveRow();
        lst_AddLocationList.setCurrentRow(row);
        ListCellLine line = _lcm_lst_AddLocationList.get(row);

        // output parameter.
        FaLstAddLocationParams outparam = new FaLstAddLocationParams();
        outparam.set(FaLstAddLocationParams.AREA_NO, line.getValue(KEY_LST_AREA_NO));
        outparam.set(FaLstAddLocationParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
        outparam.set(FaLstAddLocationParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
        outparam.set(FaLstAddLocationParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
        outparam.set(FaLstAddLocationParams.LOT_NO, line.getValue(KEY_LST_LOT_NO));
        outparam.set(FaLstAddLocationParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
        outparam.set(FaLstAddLocationParams.STOCK_QTY, line.getValue(KEY_LST_STOCK_QTY));

        ForwardParameters forwardParam = outparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, viewState.getString(_KEY_POPUPSOURCE));
        parentRedirect(forwardParam);
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