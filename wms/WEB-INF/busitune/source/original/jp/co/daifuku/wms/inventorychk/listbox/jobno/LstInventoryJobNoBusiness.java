// $Id: LstInventoryJobNoBusiness.java 6811 2010-01-22 10:50:05Z kanda $
package jp.co.daifuku.wms.inventorychk.listbox.jobno;

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
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.inventorychk.dasch.LstInventoryJobNoDASCH;
import jp.co.daifuku.wms.inventorychk.dasch.LstInventoryJobNoDASCHParams;
import jp.co.daifuku.wms.inventorychk.listbox.jobno.LstInventoryJobNo;
import jp.co.daifuku.wms.inventorychk.listbox.jobno.LstInventoryJobNoParams;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 6811 $, $Date:: 2010-01-22 19:50:05 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kanda $
 */
public class LstInventoryJobNoBusiness
        extends LstInventoryJobNo
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

    /** lst_InventoryListNoList(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 = new ListCellKey("LST_COLUMN_1", new StringCellColumn(), true, false);

    /** lst_InventoryListNoList(LST_LIST) */
    private static final ListCellKey KEY_LST_LIST = new ListCellKey("LST_LIST", new StringCellColumn(), true, false);

    /** lst_InventoryListNoList(LST_AREA) */
    private static final ListCellKey KEY_LST_AREA = new ListCellKey("LST_AREA", new StringCellColumn(), true, false);

    /** lst_InventoryListNoList(LST_START_LOCATION) */
    private static final ListCellKey KEY_LST_START_LOCATION = new ListCellKey("LST_START_LOCATION", new StringCellColumn(), true, false);

    /** lst_InventoryListNoList(LST_END_LOCATION) */
    private static final ListCellKey KEY_LST_END_LOCATION = new ListCellKey("LST_END_LOCATION", new StringCellColumn(), true, false);

    /** lst_InventoryListNoList keys */
    private static final ListCellKey[] LST_INVENTORYLISTNOLIST_KEYS = {
        KEY_LST_COLUMN_1,
        KEY_LST_LIST,
        KEY_LST_AREA,
        KEY_LST_START_LOCATION,
        KEY_LST_END_LOCATION,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_InventoryListNoList */
    private ScrollListCellModel _lcm_lst_InventoryListNoList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstInventoryJobNoBusiness()
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
    public void lst_InventoryListNoList_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_InventoryListNoList.getActiveCol();

        // choose process.
        if (_lcm_lst_InventoryListNoList.getColumnIndex(KEY_LST_COLUMN_1) == activeCol)
        {
            // process call.
            lst_Column_1_Click_Process();
        }
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_Click_Process();
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

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_InventoryListNoList.
        _lcm_lst_InventoryListNoList = new ScrollListCellModel(lst_InventoryListNoList, LST_INVENTORYLISTNOLIST_KEYS, locale);
        _lcm_lst_InventoryListNoList.setToolTipVisible(KEY_LST_COLUMN_1, false);
        _lcm_lst_InventoryListNoList.setToolTipVisible(KEY_LST_LIST, false);
        _lcm_lst_InventoryListNoList.setToolTipVisible(KEY_LST_AREA, false);
        _lcm_lst_InventoryListNoList.setToolTipVisible(KEY_LST_START_LOCATION, false);
        _lcm_lst_InventoryListNoList.setToolTipVisible(KEY_LST_END_LOCATION, false);
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
    private void lst_InventoryListNoList_SetLineToolTip(ListCellLine line)
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
        setFocus(pager);
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
        LstInventoryJobNoDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstInventoryJobNoDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstInventoryJobNoDASCHParams inparam = new LstInventoryJobNoDASCHParams();
            LstInventoryJobNoParams requestParam = new LstInventoryJobNoParams(request);
            inparam.set(LstInventoryJobNoDASCHParams.LIST_WORK_NO, requestParam.get(LstInventoryJobNoParams.LIST_WORK_NO));
            inparam.set(LstInventoryJobNoDASCHParams.CONSIGNOR_CODE, requestParam.get(LstInventoryJobNoParams.CONSIGNOR_CODE));

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_InventoryListNoList.clear();

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
            _lcm_lst_InventoryListNoList.clear();
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
        LstInventoryJobNoDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstInventoryJobNoDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_InventoryListNoList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_InventoryListNoList.getNewLine();
                line.setValue(KEY_LST_COLUMN_1, outparam.get(LstInventoryJobNoDASCHParams.COLUMN_1));
                line.setValue(KEY_LST_LIST, outparam.get(LstInventoryJobNoDASCHParams.LIST_WORK_NO));
                line.setValue(KEY_LST_AREA, outparam.get(LstInventoryJobNoDASCHParams.AREA_NO));
                line.setValue(KEY_LST_START_LOCATION, outparam.get(LstInventoryJobNoDASCHParams.START_LOCATION));
                line.setValue(KEY_LST_END_LOCATION, outparam.get(LstInventoryJobNoDASCHParams.END_LOCATION));
                lst_InventoryListNoList_SetLineToolTip(line);
                _lcm_lst_InventoryListNoList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_InventoryListNoList.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Column_1_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_InventoryListNoList.getActiveRow();
        lst_InventoryListNoList.setCurrentRow(row);
        ListCellLine line = _lcm_lst_InventoryListNoList.get(row);

        // output parameter.
        LstInventoryJobNoParams outparam = new LstInventoryJobNoParams();
        outparam.set(LstInventoryJobNoParams.LIST_WORK_NO, line.getValue(KEY_LST_LIST));
        outparam.set(LstInventoryJobNoParams.AREA_NO, line.getValue(KEY_LST_AREA));
        outparam.set(LstInventoryJobNoParams.START_LOCATION, line.getValue(KEY_LST_START_LOCATION));
        outparam.set(LstInventoryJobNoParams.END_LOCATION, line.getValue(KEY_LST_END_LOCATION));

        ForwardParameters forwardParam = outparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, viewState.getString(_KEY_POPUPSOURCE));
        parentRedirect(forwardParam);
        dispose();
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Close_Click_Process()
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
