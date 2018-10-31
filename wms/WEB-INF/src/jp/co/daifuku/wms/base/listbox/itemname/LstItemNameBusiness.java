package jp.co.daifuku.wms.base.listbox.itemname;

// $Id: skeltenBusiness.java 87 2008-10-04 03:07:38Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.dasch.LstItemDASCH;
import jp.co.daifuku.wms.base.dasch.LstItemDASCHParams;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * BusiTuneでジェネレートされたクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class LstItemNameBusiness
        extends LstItemName
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    //DFKLOOK:ここから修正
    // 親画面の保持値と同一のため変更
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH_LIST";
    //DFKLOOK:ここまで修正

    /** key */
    private static final String _KEY_PAGERSOURCE = "_KEY_PAGERSOURCE";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_SearchCondition(LST_SEARCH_CONDITION) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION = new ListCellKey("LST_SEARCH_CONDITION", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_2) */
    private static final ListCellKey KEY_LST_COLUMN_2 = new ListCellKey("LST_COLUMN_2", new StringCellColumn(), true, false);

    /** lst_Item(HIDDEN_ENTERING_QTY) */
    private static final ListCellKey KEY_HIDDEN_ENTERING_QTY = new ListCellKey("HIDDEN_ENTERING_QTY", new NumberCellColumn(0), false, false);

    /** lst_Item(HIDDEN_JAN) */
    private static final ListCellKey KEY_HIDDEN_JAN = new ListCellKey("HIDDEN_JAN", new StringCellColumn(), false, false);

    /** lst_Item(HIDDEN_ITF) */
    private static final ListCellKey KEY_HIDDEN_ITF = new ListCellKey("HIDDEN_ITF", new StringCellColumn(), false, false);

    /** lst_Item(HIDDEN_UPPER_QTY) */
    private static final ListCellKey KEY_HIDDEN_UPPER_QTY = new ListCellKey("HIDDEN_UPPER_QTY", new NumberCellColumn(0), false, false);

    /** lst_Item(HIDDEN_TEMPORARY_TYPE) */
    private static final ListCellKey KEY_HIDDEN_TEMPORARY_TYPE = new ListCellKey("HIDDEN_TEMPORARY_TYPE", new StringCellColumn(), false, false);

    /** lst_Item(HIDDEN_LOWER_QTY) */
    private static final ListCellKey KEY_HIDDEN_LOWER_QTY = new ListCellKey("HIDDEN_LOWER_QTY", new NumberCellColumn(0), false, false);

    /** lst_Item(HIDDEN_SOFT_ZONE_ID) */
    private static final ListCellKey KEY_HIDDEN_SOFT_ZONE_ID = new ListCellKey("HIDDEN_SOFT_ZONE_ID", new StringCellColumn(), false, false);

    /** lst_Item(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT = new ListCellKey("LST_SELECT", new StringCellColumn(), true, false);

    /** lst_Item(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_Item(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_Item(LST_SOFTZONE_NAME) */
    private static final ListCellKey KEY_LST_SOFTZONE_NAME = new ListCellKey("LST_SOFTZONE_NAME", new StringCellColumn(), true, false);

    /** lst_Item(LST_TEMPORARY_TYPE_NAME) */
    private static final ListCellKey KEY_LST_TEMPORARY_TYPE_NAME = new ListCellKey("LST_TEMPORARY_TYPE_NAME", new StringCellColumn(), true, false);

    /** lst_SearchCondition keys */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
        KEY_LST_SEARCH_CONDITION,
        KEY_LST_COLUMN_2,
    };

    /** lst_Item keys */
    private static final ListCellKey[] LST_ITEM_KEYS = {
        KEY_HIDDEN_ENTERING_QTY,
        KEY_HIDDEN_JAN,
        KEY_HIDDEN_ITF,
        KEY_HIDDEN_UPPER_QTY,
        KEY_HIDDEN_TEMPORARY_TYPE,
        KEY_HIDDEN_LOWER_QTY,
        KEY_HIDDEN_SOFT_ZONE_ID,
        KEY_LST_SELECT,
        KEY_LST_ITEM_NAME,
        KEY_LST_ITEM_CODE,
        KEY_LST_SOFTZONE_NAME,
        KEY_LST_TEMPORARY_TYPE_NAME,
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

    /** ListCellModel lst_Item */
    private ListCellModel _lcm_lst_Item;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstItemNameBusiness()
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
    public void lst_SearchCondition_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchCondition_ColumClick(ActionEvent e)
            throws Exception
    {
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
    public void lst_Item_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_Item.getActiveCol();

        // choose process.
        if (_lcm_lst_Item.getColumnIndex(KEY_LST_SELECT) == activeCol)
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
    public void lst_Item_ColumClick(ActionEvent e)
            throws Exception
    {
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

        // initialize lst_SearchCondition.
        _lcm_lst_SearchCondition = new ListCellModel(lst_SearchCondition, LST_SEARCHCONDITION_KEYS, locale);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_SEARCH_CONDITION, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_2, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_Item.
        _lcm_lst_Item = new ListCellModel(lst_Item, LST_ITEM_KEYS, locale);
        _lcm_lst_Item.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_Item.setToolTipVisible(KEY_LST_ITEM_NAME, false);
        _lcm_lst_Item.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_Item.setToolTipVisible(KEY_LST_SOFTZONE_NAME, false);
        _lcm_lst_Item.setToolTipVisible(KEY_LST_TEMPORARY_TYPE_NAME, false);

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
    private void lst_Item_SetLineToolTip(ListCellLine line)
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
        LstItemDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstItemDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstItemDASCHParams inparam = new LstItemDASCHParams();
            LstItemNameParams requestParam = new LstItemNameParams(request);
            inparam.set(LstItemDASCHParams.ITEM_NAME, requestParam.get(LstItemNameParams.ITEM_NAME));
            inparam.set(LstItemDASCHParams.CONSIGNOR_CODE, requestParam.get(LstItemNameParams.CONSIGNOR_CODE));
            // DFKLOOK start
            // 商品コード検索か商品名称検索かのフラグを追加
            inparam.set(LstItemDASCHParams.IS_ITEM_CODE_SEARCH, Boolean.FALSE);

            
            // 検索条件リストにセット
            int line = 1;
            lst_SearchCondition.addRow();
            lst_SearchCondition.setCurrentRow(line);
            // 商品名称
            lst_SearchCondition.setValue(1, DispResources.getText("LBL-W0130"));
            lst_SearchCondition.setValue(2, inparam.getString(LstItemDASCHParams.ITEM_NAME));
            // DFKLOOK end

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_Item.clear();

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
            _lcm_lst_Item.clear();
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
        LstItemDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstItemDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_Item.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_Item.getNewLine();
                line.setValue(KEY_HIDDEN_ENTERING_QTY, outparam.get(LstItemDASCHParams.ENTERING_QTY));
                line.setValue(KEY_HIDDEN_JAN, outparam.get(LstItemDASCHParams.JAN));
                line.setValue(KEY_HIDDEN_ITF, outparam.get(LstItemDASCHParams.ITF));
                line.setValue(KEY_HIDDEN_TEMPORARY_TYPE, outparam.get(LstItemDASCHParams.TEMPORARY_TYPE));
                line.setValue(KEY_HIDDEN_UPPER_QTY, outparam.get(LstItemDASCHParams.UPPER_QTY));
                line.setValue(KEY_HIDDEN_LOWER_QTY, outparam.get(LstItemDASCHParams.LOWER_QTY));
                line.setValue(KEY_LST_SELECT, outparam.get(LstItemDASCHParams.SELECT));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstItemDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstItemDASCHParams.ITEM_NAME));
                line.setValue(KEY_HIDDEN_SOFT_ZONE_ID, outparam.get(LstItemDASCHParams.SOFT_ZONE_ID));
                line.setValue(KEY_LST_SOFTZONE_NAME, outparam.get(LstItemDASCHParams.SOFT_ZONE_NAME));
                line.setValue(KEY_LST_TEMPORARY_TYPE_NAME, outparam.get(LstItemDASCHParams.TEMPORARY_TYPE_NAME));
                lst_Item_SetLineToolTip(line);
                _lcm_lst_Item.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_Item.clear();
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
        int row = lst_Item.getActiveRow();
        lst_Item.setCurrentRow(row);
        ListCellLine line = _lcm_lst_Item.get(row);

        // output parameter.
        LstItemNameParams outparam = new LstItemNameParams();
        outparam.set(LstItemNameParams.ENTERING_QTY, line.getValue(KEY_HIDDEN_ENTERING_QTY));
        outparam.set(LstItemNameParams.JAN, line.getValue(KEY_HIDDEN_JAN));
        outparam.set(LstItemNameParams.ITF, line.getValue(KEY_HIDDEN_ITF));
        outparam.set(LstItemNameParams.TEMPORARY_TYPE, line.getValue(KEY_HIDDEN_TEMPORARY_TYPE));
        outparam.set(LstItemNameParams.UPPER_QTY, line.getValue(KEY_HIDDEN_UPPER_QTY));
        outparam.set(LstItemNameParams.LOWER_QTY, line.getValue(KEY_HIDDEN_LOWER_QTY));
        outparam.set(LstItemNameParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
        outparam.set(LstItemNameParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
        outparam.set(LstItemNameParams.SOFT_ZONE_ID, line.getValue(KEY_HIDDEN_SOFT_ZONE_ID));
        outparam.set(LstItemNameParams.SOFT_ZONE_NAME, line.getValue(KEY_LST_SOFTZONE_NAME));
        outparam.set(LstItemNameParams.TEMPORARY_TYPE_NAME, line.getValue(KEY_LST_TEMPORARY_TYPE_NAME));

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
