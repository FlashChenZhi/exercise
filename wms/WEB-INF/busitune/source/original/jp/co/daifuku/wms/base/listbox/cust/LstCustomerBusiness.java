// $Id: LstCustomerBusiness.java 5290 2009-10-28 04:29:37Z kishimoto $
package jp.co.daifuku.wms.base.listbox.cust;

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
import jp.co.daifuku.Constants;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.controller.PulldownController.AreaType;
import jp.co.daifuku.wms.base.controller.PulldownController.StationType;
import jp.co.daifuku.wms.base.controller.PulldownController;
import jp.co.daifuku.wms.base.dasch.LstCustomerDASCH;
import jp.co.daifuku.wms.base.dasch.LstCustomerDASCHParams;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomer;
import jp.co.daifuku.wms.base.listbox.cust.LstCustomerParams;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 5290 $, $Date:: 2009-10-28 13:29:37 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class LstCustomerBusiness
        extends LstCustomer
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

    /** lst_SortCustomer(HIDDEN_ROUTE) */
    private static final ListCellKey KEY_HIDDEN_ROUTE = new ListCellKey("HIDDEN_ROUTE", new StringCellColumn(), false, false);

    /** lst_SortCustomer(HIDDEN_POSTAL_CODE) */
    private static final ListCellKey KEY_HIDDEN_POSTAL_CODE = new ListCellKey("HIDDEN_POSTAL_CODE", new StringCellColumn(), false, false);

    /** lst_SortCustomer(HIDDEN_PREFECTURE) */
    private static final ListCellKey KEY_HIDDEN_PREFECTURE = new ListCellKey("HIDDEN_PREFECTURE", new StringCellColumn(), false, false);

    /** lst_SortCustomer(HIDDEN_ADDRESS1) */
    private static final ListCellKey KEY_HIDDEN_ADDRESS1 = new ListCellKey("HIDDEN_ADDRESS1", new StringCellColumn(), false, false);

    /** lst_SortCustomer(HIDDEN_ADDRESS2) */
    private static final ListCellKey KEY_HIDDEN_ADDRESS2 = new ListCellKey("HIDDEN_ADDRESS2", new StringCellColumn(), false, false);

    /** lst_SortCustomer(HIDDEN_TELEPHONE) */
    private static final ListCellKey KEY_HIDDEN_TELEPHONE = new ListCellKey("HIDDEN_TELEPHONE", new StringCellColumn(), false, false);

    /** lst_SortCustomer(HIDDEN_CONTACT1) */
    private static final ListCellKey KEY_HIDDEN_CONTACT1 = new ListCellKey("HIDDEN_CONTACT1", new StringCellColumn(), false, false);

    /** lst_SortCustomer(HIDDEN_CONTACT2) */
    private static final ListCellKey KEY_HIDDEN_CONTACT2 = new ListCellKey("HIDDEN_CONTACT2", new StringCellColumn(), false, false);

    /** lst_SortCustomer(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new StringCellColumn(), true, false);

    /** lst_SortCustomer(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_SortCustomer(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_SortCustomer(LST_SORT_PLACE) */
    private static final ListCellKey KEY_LST_SORT_PLACE = new ListCellKey("LST_SORT_PLACE", new StringCellColumn(), true, false);

    /** lst_SortCustomer keys */
    private static final ListCellKey[] LST_SORTCUSTOMER_KEYS = {
        KEY_HIDDEN_ROUTE,
        KEY_HIDDEN_POSTAL_CODE,
        KEY_HIDDEN_PREFECTURE,
        KEY_HIDDEN_ADDRESS1,
        KEY_HIDDEN_ADDRESS2,
        KEY_HIDDEN_TELEPHONE,
        KEY_HIDDEN_CONTACT1,
        KEY_HIDDEN_CONTACT2,
        KEY_LST_SELECT,
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_CUSTOMER_NAME,
        KEY_LST_SORT_PLACE,
    };

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_SortCustomer */
    private ScrollListCellModel _lcm_lst_SortCustomer;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstCustomerBusiness()
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
    public void lst_SortCustomer_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_SortCustomer.getActiveCol();

        // choose process.
        if (_lcm_lst_SortCustomer.getColumnIndex(KEY_LST_SELECT) == activeCol)
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

        // initialize lst_SortCustomer.
        _lcm_lst_SortCustomer = new ScrollListCellModel(lst_SortCustomer, LST_SORTCUSTOMER_KEYS, locale);
        _lcm_lst_SortCustomer.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_SortCustomer.setToolTipVisible(KEY_LST_CUSTOMER_CODE, false);
        _lcm_lst_SortCustomer.setToolTipVisible(KEY_LST_CUSTOMER_NAME, false);
        _lcm_lst_SortCustomer.setToolTipVisible(KEY_LST_SORT_PLACE, false);

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
    private void lst_SortCustomer_SetLineToolTip(ListCellLine line)
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
        LstCustomerDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstCustomerDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstCustomerDASCHParams inparam = new LstCustomerDASCHParams();
            LstCustomerParams requestParam = new LstCustomerParams(request);
            inparam.set(LstCustomerDASCHParams.CUSTOMER_CODE, requestParam.get(LstCustomerParams.CUSTOMER_CODE));
            inparam.set(LstCustomerDASCHParams.CONSIGNOR_CODE, requestParam.get(LstCustomerParams.CONSIGNOR_CODE));
            inparam.set(LstCustomerDASCHParams.FROM_TO_FLAG, requestParam.get(LstCustomerParams.FROM_TO_FLAG));
            inparam.set(LstCustomerDASCHParams.TO_CUSTOMER_CODE, requestParam.get(LstCustomerParams.TO_CUSTOMER_CODE));

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_SortCustomer.clear();

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
            _lcm_lst_SortCustomer.clear();
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
        LstCustomerDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstCustomerDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_SortCustomer.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_SortCustomer.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(LstCustomerDASCHParams.SELECT));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(LstCustomerDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(LstCustomerDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_SORT_PLACE, outparam.get(LstCustomerDASCHParams.SORT_PLACE));
                line.setValue(KEY_HIDDEN_ROUTE, outparam.get(LstCustomerDASCHParams.ROUTE));
                line.setValue(KEY_HIDDEN_POSTAL_CODE, outparam.get(LstCustomerDASCHParams.POSTAL_CODE));
                line.setValue(KEY_HIDDEN_PREFECTURE, outparam.get(LstCustomerDASCHParams.PREFECTURE));
                line.setValue(KEY_HIDDEN_ADDRESS1, outparam.get(LstCustomerDASCHParams.ADDRESS1));
                line.setValue(KEY_HIDDEN_ADDRESS2, outparam.get(LstCustomerDASCHParams.ADDRESS2));
                line.setValue(KEY_HIDDEN_TELEPHONE, outparam.get(LstCustomerDASCHParams.TELEPHONE));
                line.setValue(KEY_HIDDEN_CONTACT1, outparam.get(LstCustomerDASCHParams.CONTACT1));
                line.setValue(KEY_HIDDEN_CONTACT2, outparam.get(LstCustomerDASCHParams.CONTACT2));
                lst_SortCustomer_SetLineToolTip(line);
                _lcm_lst_SortCustomer.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_SortCustomer.clear();
            disposeDasch();
        }
    }

    /**
     *
     * @throws Exception
     */
    private void lst_Select_Click_Process()
            throws Exception
    {
        // get active row.
        int row = lst_SortCustomer.getActiveRow();
        lst_SortCustomer.setCurrentRow(row);
        ListCellLine line = _lcm_lst_SortCustomer.get(row);

        // output parameter.
        LstCustomerParams outparam = new LstCustomerParams();
        outparam.set(LstCustomerParams.CUSTOMER_CODE, line.getValue(KEY_LST_CUSTOMER_CODE));
        outparam.set(LstCustomerParams.CUSTOMER_NAME, line.getValue(KEY_LST_CUSTOMER_NAME));
        outparam.set(LstCustomerParams.SORT_PLACE, line.getValue(KEY_LST_SORT_PLACE));
        outparam.set(LstCustomerParams.ROUTE, line.getValue(KEY_HIDDEN_ROUTE));
        outparam.set(LstCustomerParams.POSTAL_CODE, line.getValue(KEY_HIDDEN_POSTAL_CODE));
        outparam.set(LstCustomerParams.PREFECTURE, line.getValue(KEY_HIDDEN_PREFECTURE));
        outparam.set(LstCustomerParams.ADDRESS1, line.getValue(KEY_HIDDEN_ADDRESS1));
        outparam.set(LstCustomerParams.ADDRESS2, line.getValue(KEY_HIDDEN_ADDRESS2));
        outparam.set(LstCustomerParams.TELEPHONE, line.getValue(KEY_HIDDEN_TELEPHONE));
        outparam.set(LstCustomerParams.CONTACT1, line.getValue(KEY_HIDDEN_CONTACT1));
        outparam.set(LstCustomerParams.CONTACT2, line.getValue(KEY_HIDDEN_CONTACT2));

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
