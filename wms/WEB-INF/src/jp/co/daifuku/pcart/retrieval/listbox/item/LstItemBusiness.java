// $Id: LstItemBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.pcart.retrieval.listbox.item;

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
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstItemDASCH;
import jp.co.daifuku.pcart.retrieval.dasch.LstItemDASCHParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;

/**
 * 商品検索リストボックスの画面処理を行います。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kitamaki $
 */
public class LstItemBusiness
        extends LstItem
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_ItemList(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT =
            new ListCellKey("LST_SELECT", new StringCellColumn(), true, false);

    /** lst_ItemList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ItemList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ItemList(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY =
            new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_ItemList(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_ItemList(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_ItemList kyes */
    private static final ListCellKey[] LST_ITEMLIST_KEYS = {
            KEY_LST_SELECT,
            KEY_LST_ITEM_CODE,
            KEY_LST_ITEM_NAME,
            KEY_LST_LOT_QTY,
            KEY_LST_JAN,
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

    /** ListCellModel lst_ItemList */
    private ListCellModel _lcm_lst_ItemList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstItemBusiness()
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
    public void pager_First(ActionEvent e)
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
    public void pager_Prev(ActionEvent e)
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
    public void pager_Next(ActionEvent e)
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
    public void pager_Last(ActionEvent e)
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
    public void lst_ItemList_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_ItemList.getActiveCol();

        // choose process.
        if (_lcm_lst_ItemList.getColumnIndex(KEY_LST_SELECT) == activeCol)
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
        _pager = new PagerModel(new Pager[] {
            pager
        }, locale);

        // initialize lst_ItemList.
        _lcm_lst_ItemList = new ListCellModel(lst_ItemList, LST_ITEMLIST_KEYS, locale);
        _lcm_lst_ItemList.setToolTipVisible(KEY_LST_SELECT, false);
        _lcm_lst_ItemList.setToolTipVisible(KEY_LST_ITEM_CODE, false);
        _lcm_lst_ItemList.setToolTipVisible(KEY_LST_ITEM_NAME, false);
        _lcm_lst_ItemList.setToolTipVisible(KEY_LST_LOT_QTY, false);
        _lcm_lst_ItemList.setToolTipVisible(KEY_LST_JAN, false);
        _lcm_lst_ItemList.setToolTipVisible(KEY_LST_ITF, false);

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
    private void lst_ItemList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
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

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        LstItemDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstItemDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstItemDASCHParams inparam = new LstItemDASCHParams();
            LstItemParams requestParam = new LstItemParams(request);
            inparam.set(LstItemDASCHParams.CONSIGNOR_CODE, requestParam.get(LstItemParams.CONSIGNOR_CODE));
            inparam.set(LstItemDASCHParams.WORK_DAY, requestParam.get(LstItemParams.WORK_DAY));
            inparam.set(LstItemDASCHParams.PLAN_DAY, requestParam.get(LstItemParams.PLAN_DAY));
            inparam.set(LstItemDASCHParams.AREA_NO, requestParam.get(LstItemParams.AREA_NO));
            inparam.set(LstItemDASCHParams.REGULAR_CUSTOMER_CODE, requestParam.get(LstItemParams.REGULAR_CUSTOMER_CODE));
            inparam.set(LstItemDASCHParams.CUSTOMER_CODE, requestParam.get(LstItemParams.CUSTOMER_CODE));
            inparam.set(LstItemDASCHParams.ORDER_NO, requestParam.get(LstItemParams.ORDER_NO));
            inparam.set(LstItemDASCHParams.ITEM_CODE, requestParam.get(LstItemParams.ITEM_CODE));
            inparam.set(LstItemDASCHParams.ITEM_NAME, requestParam.get(LstItemParams.ITEM_NAME));
            inparam.set(LstItemDASCHParams.SEQ_NO, requestParam.get(LstItemParams.SEQ_NO));
            inparam.set(LstItemDASCHParams.STATUS_FLAG, requestParam.get(LstItemParams.STATUS_FLAG));
            inparam.set(LstItemDASCHParams.STATUS_FLAG2, requestParam.get(LstItemParams.STATUS_FLAG2));
            inparam.set(LstItemDASCHParams.BATCH_SEQ_NO, requestParam.get(LstItemParams.BATCH_SEQ_NO));
            inparam.set(LstItemDASCHParams.BATCH_NO, requestParam.get(LstItemParams.BATCH_NO));
            inparam.set(LstItemDASCHParams.SEARCHTABLE, requestParam.get(LstItemParams.SEARCHTABLE));
            inparam.set(LstItemDASCHParams.JAN, requestParam.get(LstItemParams.JAN));
            inparam.set(LstItemDASCHParams.ITF, requestParam.get(LstItemParams.ITF));

            //DFKLOOK start
            // 親画面からエリアNoが渡されていない場合
            if (StringUtil.isBlank(requestParam.getString(LstItemParams.AREA_NO)))
            {
                // 対象データはありませんでした。
                message.setMsgResourceKey("6003011");
                return;
            }
            //DFKLOOK end
            
            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_ItemList.clear();

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
                ListCellLine line = _lcm_lst_ItemList.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(LstItemDASCHParams.SELECT));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstItemDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstItemDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstItemDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_JAN, outparam.get(LstItemDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(LstItemDASCHParams.ITF));
                lst_ItemList_SetLineToolTip(line);
                _lcm_lst_ItemList.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH, dasch);
            isSuccess = true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ItemList.clear();
        }
        finally
        {
            if (!isSuccess)
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
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_ItemList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ItemList.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(LstItemDASCHParams.SELECT));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstItemDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstItemDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(LstItemDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_JAN, outparam.get(LstItemDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(LstItemDASCHParams.ITF));
                lst_ItemList_SetLineToolTip(line);
                _lcm_lst_ItemList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ItemList.clear();
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
            DBUtil.close(dasch.getConnection());
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
        int row = lst_ItemList.getActiveRow();
        lst_ItemList.setCurrentRow(row);
        ListCellLine line = _lcm_lst_ItemList.get(row);

        // output parameter.
        LstItemParams outparam = new LstItemParams();
        outparam.set(LstItemParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
        outparam.set(LstItemParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
        outparam.set(LstItemParams.LOT_QTY, line.getValue(KEY_LST_LOT_QTY));
        outparam.set(LstItemParams.JAN, line.getValue(KEY_LST_JAN));
        outparam.set(LstItemParams.ITF, line.getValue(KEY_LST_ITF));

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
