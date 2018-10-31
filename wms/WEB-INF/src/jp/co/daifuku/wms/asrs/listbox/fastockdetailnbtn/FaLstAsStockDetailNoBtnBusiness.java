// $Id: Business_ja.java 109 2008-10-06 10:49:13Z admin $
package jp.co.daifuku.wms.asrs.listbox.fastockdetailnbtn;

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
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellKey;
import jp.co.daifuku.bluedog.model.table.ListCellLine;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.NumberCellColumn;
import jp.co.daifuku.bluedog.model.table.ListCellModel;
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.LstAsStockDetailNoBtnDASCH;
import jp.co.daifuku.wms.asrs.dasch.LstAsStockDetailNoBtnDASCHParams;
import jp.co.daifuku.wms.asrs.listbox.stockdetailnbtn.LstAsStockDetailNoBtnParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.location.WmsLocationFormat;

/**
 * AS/RS 禁止棚設定のスケジュール処理を行います。
 *
 * @version $Revision: 109 $, $Date:: 2008-10-06 19:49:13 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaLstAsStockDetailNoBtnBusiness
        extends FaLstAsStockDetailNoBtn
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

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION = new ListCellKey("LST_SEARCH_CONDITION", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_COLUMN_2) */
    private static final ListCellKey KEY_LST_COLUMN_2 = new ListCellKey("LST_COLUMN_2", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_COLUMN_3) */
    private static final ListCellKey KEY_LST_COLUMN_3 = new ListCellKey("LST_COLUMN_3", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_COLUMN_4) */
    private static final ListCellKey KEY_LST_COLUMN_4 = new ListCellKey("LST_COLUMN_4", new StringCellColumn(), true, false);

    /** lst_LocationInquiryList(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_LocationInquiryList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE = new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_LocationInquiryList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME = new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_LocationInquiryList(LST_LOT_NO) */
    private static final ListCellKey KEY_LST_LOT_NO = new ListCellKey("LST_LOT_NO", new StringCellColumn(), true, false);

    /** lst_LocationInquiryList(LST_STORAGE_DATETIME) */
    private static final ListCellKey KEY_LST_STORAGE_DATETIME = new ListCellKey("LST_STORAGE_DATETIME", new DateCellColumn(DATE_FORMAT.LONG, TIME_FORMAT.HMS), true, false);

    /** lst_LocationInquiryList(LST_STOCK_QTY) */
    private static final ListCellKey KEY_LST_STOCK_QTY = new ListCellKey("LST_STOCK_QTY", new NumberCellColumn(0), true, false);

    /** lst_SearchConditionTwoColumn keys */
    private static final ListCellKey[] LST_SEARCHCONDITIONTWOCOLUMN_KEYS = {
        KEY_LST_SEARCH_CONDITION,
        KEY_LST_COLUMN_2,
        KEY_LST_COLUMN_3,
        KEY_LST_COLUMN_4,
    };

    /** lst_LocationInquiryList keys */
    private static final ListCellKey[] LST_LOCATIONINQUIRYLIST_KEYS = {
        KEY_LST_NO,
        KEY_LST_ITEM_CODE,
        KEY_LST_ITEM_NAME,
        KEY_LST_LOT_NO,
        KEY_LST_STORAGE_DATETIME,
        KEY_LST_STOCK_QTY,
    };

    // DFKLOOK ここから追加
    // 検索条件表示行番号
    /**
     * 検索条件表示行番号 : エリアNo.とエリア名称
     */
    private static final int CONDITION_AREA = 1;

    /**
     * 検索条件表示行番号 : 棚
     */
    private static final int CONDITION_LOCATION_NO = 2;

    /**
     * 検索条件用リスト列番号用
     */
    private static final int S_LIST1 = 1;

    /**
     * 検索条件用リスト列番号用
     */
    private static final int S_LIST2 = 2;

    /**
     * 検索条件用リスト列番号用
     */
    private static final int S_LIST3 = 3;

    /**
     * 検索条件用リスト列番号用
     */
    private static final int S_LIST4 = 4;

    // DFKLOOK ここまで追加

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** ListCellModel lst_SearchConditionTwoColumn */
    private ListCellModel _lcm_lst_SearchConditionTwoColumn;

    /** PagerModel */
    private PagerModel _pager;

    /** ListCellModel lst_LocationInquiryList */
    private ListCellModel _lcm_lst_LocationInquiryList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public FaLstAsStockDetailNoBtnBusiness()
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

        // initialize lst_SearchConditionTwoColumn.
        _lcm_lst_SearchConditionTwoColumn = new ListCellModel(lst_SearchConditionTwoColumn, LST_SEARCHCONDITIONTWOCOLUMN_KEYS, locale);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_COLUMN_2, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_COLUMN_3, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_COLUMN_4, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pager}, locale);

        // initialize lst_LocationInquiryList.
        _lcm_lst_LocationInquiryList = new ListCellModel(lst_LocationInquiryList, LST_LOCATIONINQUIRYLIST_KEYS, locale);
        _lcm_lst_LocationInquiryList.setToolTipVisible(KEY_LST_NO, true);
        _lcm_lst_LocationInquiryList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_LocationInquiryList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_LocationInquiryList.setToolTipVisible(KEY_LST_LOT_NO, true);
        _lcm_lst_LocationInquiryList.setToolTipVisible(KEY_LST_STORAGE_DATETIME, true);
        _lcm_lst_LocationInquiryList.setToolTipVisible(KEY_LST_STOCK_QTY, true);

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
    private void lst_SearchConditionTwoColumn_SetLineToolTip(ListCellLine line)
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
    private void lst_LocationInquiryList_SetLineToolTip(ListCellLine line)
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
        LstAsStockDetailNoBtnDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstAsStockDetailNoBtnDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstAsStockDetailNoBtnDASCHParams inparam = new LstAsStockDetailNoBtnDASCHParams();
            FaLstAsStockDetailNoBtnParams requestParam = new FaLstAsStockDetailNoBtnParams(request);
            inparam.set(LstAsStockDetailNoBtnDASCHParams.AREA_NO, requestParam.get(FaLstAsStockDetailNoBtnParams.AREA_NO));
            inparam.set(LstAsStockDetailNoBtnDASCHParams.LOCATION_NO, requestParam.get(FaLstAsStockDetailNoBtnParams.LOCATION_NO));
            inparam.set(LstAsStockDetailNoBtnDASCHParams.PALLET_ID, requestParam.get(FaLstAsStockDetailNoBtnParams.PALLET_ID));
            
            // DFKLOOK ここから追加
            _lcm_lst_SearchConditionTwoColumn.clear();
            lst_SearchConditionTwoColumn.setVisible(true);
            setSearchlist(conn, requestParam);
            // DFKLOOK ここまで追加

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_LocationInquiryList.clear();

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

            // DFKLOOK 開始行Noを設定
            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            int no = _pager.getIndex();

            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LocationInquiryList.getNewLine();
                line.setValue(KEY_LST_NO, no++);
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstAsStockDetailNoBtnDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstAsStockDetailNoBtnDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(LstAsStockDetailNoBtnDASCHParams.LOT_NO));
                line.setValue(KEY_LST_STORAGE_DATETIME, outparam.get(LstAsStockDetailNoBtnDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STOCK_QTY, outparam.get(LstAsStockDetailNoBtnDASCHParams.STOCK_QTY));
                lst_LocationInquiryList_SetLineToolTip(line);
                _lcm_lst_LocationInquiryList.add(line);
            }
            // DFKLOOK ここまで追加

            // save session.
            session.setAttribute(_KEY_DASCH_LIST, dasch);
            isSuccess = true;

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_LocationInquiryList.clear();
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
            // DFKLOOK ここまで追加
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_SetList()
            throws Exception
    {
        LstAsStockDetailNoBtnDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstAsStockDetailNoBtnDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_LocationInquiryList.clear();
            // DFKLOOK 開始行Noを設定
            int no = _pager.getIndex();
            // DFKLOOK ここまで追加

            //            _lcm_lst_SearchConditionTwoColumn.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_LocationInquiryList.getNewLine();
                // DFKLOOK ここから追加
                line.setValue(KEY_LST_NO, no++);
                // DFKLOOK ここまで追加
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstAsStockDetailNoBtnDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstAsStockDetailNoBtnDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_NO, outparam.get(LstAsStockDetailNoBtnDASCHParams.LOT_NO));
                line.setValue(KEY_LST_STORAGE_DATETIME, outparam.get(LstAsStockDetailNoBtnDASCHParams.STORAGE_DATE));
                line.setValue(KEY_LST_STOCK_QTY, outparam.get(LstAsStockDetailNoBtnDASCHParams.STOCK_QTY));
                lst_LocationInquiryList_SetLineToolTip(line);
                _lcm_lst_LocationInquiryList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_LocationInquiryList.clear();
            disposeDasch();
        }
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

    // DFKLOOK ここから追加
    /**
     * 検索条件の表示をセットする時に使用するメソッドです。
     * <BR>
     * @param conn コネクション
     * @param param 表示検索条件
     * @throws Exception 全ての例外を報告します。
     */
    private void setSearchlist(Connection conn, FaLstAsStockDetailNoBtnParams param)
            throws Exception
    {
        // エリアコントローラー
        AreaController areaCtl = new AreaController(conn, getClass());

        lst_SearchConditionTwoColumn.addRow();
        lst_SearchConditionTwoColumn.setCurrentRow(CONDITION_AREA);

        // エリアＮｏ．
        lst_SearchConditionTwoColumn.setValue(S_LIST1, DisplayText.getText("LBL-W0228"));
        lst_SearchConditionTwoColumn.setValue(S_LIST2, param.getString(LstAsStockDetailNoBtnParams.AREA_NO));
        // エリア名称
        lst_SearchConditionTwoColumn.setValue(S_LIST3, DisplayText.getText("LBL-W0229"));
        // エリア名称取得
        if (WmsParam.ALL_AREA_NO.equals(param.getString(LstAsStockDetailNoBtnParams.AREA_NO)))
        {
            //全エリア
            lst_SearchConditionTwoColumn.setValue(S_LIST4, DisplayText.getText("CMB-W0023"));
        }
        else
        {
            lst_SearchConditionTwoColumn.setValue(S_LIST4,
                    areaCtl.getAreaName(param.getString(LstAsStockDetailNoBtnParams.AREA_NO)));
        }

        lst_SearchConditionTwoColumn.addRow();
        lst_SearchConditionTwoColumn.setCurrentRow(CONDITION_LOCATION_NO);
        // 棚
        lst_SearchConditionTwoColumn.setValue(S_LIST1, DisplayText.getText("LBL-W0138"));

        String areaNo = param.getString(LstAsStockDetailNoBtnParams.AREA_NO);
        String dispLocation = areaCtl.toParamLocation(param.getString(LstAsStockDetailNoBtnParams.LOCATION_NO));
        WmsLocationFormat wmsLoc = new WmsLocationFormat(areaCtl.getLocationStyle(areaNo));
        lst_SearchConditionTwoColumn.setValue(S_LIST2, wmsLoc.format(dispLocation, areaNo));

        // 親画面から指定されなかった場合は設定しない
        if (!StringUtil.isBlank(param.getString(FaLstAsStockDetailNoBtnParams.SHELF_STATUS)))
        {
            lst_SearchConditionTwoColumn.setValue(S_LIST3, DisplayText.getText("LBL-W0299"));
            lst_SearchConditionTwoColumn.setValue(S_LIST4,
                    DisplayText.getText(param.getString(FaLstAsStockDetailNoBtnParams.SHELF_STATUS)));
        }
    }

    // DFKLOOK ここまで追加

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
