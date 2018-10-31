// $Id: PCTLstItemListBusiness.java 7707 2010-03-20 07:23:11Z okayama $
package jp.co.daifuku.pcart.master.listbox.pctitemlist;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.util.Date;
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
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.foundation.da.Exporter;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.pcart.base.util.PCTDisplayUtil;
import jp.co.daifuku.pcart.master.dasch.PCTLstItemListDASCH;
import jp.co.daifuku.pcart.master.dasch.PCTLstItemListDASCHParams;
import jp.co.daifuku.pcart.master.exporter.PctItemMasterListParams;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 商品マスタ一覧の画面処理を行います。
 *
 * @version $Revision: 7707 $, $Date:: 2010-03-20 16:23:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class PCTLstItemListBusiness
        extends PCTLstItemList
{

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** key */
    private static final String _KEY_DASCH = "_KEY_DASCH";

    /** key */
    private static final String _KEY_POPUPSOURCE = "_KEY_POPUPSOURCE";

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION_1) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_1 =
            new ListCellKey("LST_SEARCH_CONDITION_1", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION_2) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_2 =
            new ListCellKey("LST_SEARCH_CONDITION_2", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION_3) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_3 =
            new ListCellKey("LST_SEARCH_CONDITION_3", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn(LST_SEARCH_CONDITION_4) */
    private static final ListCellKey KEY_LST_SEARCH_CONDITION_4 =
            new ListCellKey("LST_SEARCH_CONDITION_4", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(HIDDEN_CONSIGNOR_CODE) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_CODE =
            new ListCellKey("HIDDEN_CONSIGNOR_CODE", new StringCellColumn(), false, false);

    /** lst_PCTItemMasterList(HIDDEN_CONSIGNOR_NAME) */
    private static final ListCellKey KEY_HIDDEN_CONSIGNOR_NAME =
            new ListCellKey("HIDDEN_CONSIGNOR_NAME", new StringCellColumn(), false, false);

    /** lst_PCTItemMasterList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_LOT_QTY) */
    private static final ListCellKey KEY_LST_LOT_QTY =
            new ListCellKey("LST_LOT_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTItemMasterList(LST_JAN) */
    private static final ListCellKey KEY_LST_JAN = new ListCellKey("LST_JAN", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_ITF) */
    private static final ListCellKey KEY_LST_ITF = new ListCellKey("LST_ITF", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_LOCATION_NO) */
    private static final ListCellKey KEY_LST_LOCATION_NO =
            new ListCellKey("LST_LOCATION_NO", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_SINGLE_WEIGHT) */
    private static final ListCellKey KEY_LST_SINGLE_WEIGHT =
            new ListCellKey("LST_SINGLE_WEIGHT", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_WEIGHT_DISTINCT_RATE) */
    private static final ListCellKey KEY_LST_WEIGHT_DISTINCT_RATE =
            new ListCellKey("LST_WEIGHT_DISTINCT_RATE", new NumberCellColumn(0), true, false);

    /** lst_PCTItemMasterList(LST_MAX_INSPECTION_QTY) */
    private static final ListCellKey KEY_LST_MAX_INSPECTION_QTY =
            new ListCellKey("LST_MAX_INSPECTION_QTY", new NumberCellColumn(0), true, false);

    /** lst_PCTItemMasterList(LST_LAST_UPDATE_DATE) */
    private static final ListCellKey KEY_LST_LAST_UPDATE_DATE =
            new ListCellKey("LST_LAST_UPDATE_DATE", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_PCTItemMasterList(LST_LAST_USED_DATE) */
    private static final ListCellKey KEY_LST_LAST_USED_DATE =
            new ListCellKey("LST_LAST_USED_DATE", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_PCTItemMasterList(LST_ITEM_PICTURE_REGIST) */
    private static final ListCellKey KEY_LST_ITEM_PICTURE_REGIST =
            new ListCellKey("LST_ITEM_PICTURE_REGIST", new StringCellColumn(), true, false);

    /** lst_PCTItemMasterList(LST_MESSAGE) */
    private static final ListCellKey KEY_LST_MESSAGE =
            new ListCellKey("LST_MESSAGE", new StringCellColumn(), true, false);

    /** lst_SearchConditionTwoColumn kyes */
    private static final ListCellKey[] LST_SEARCHCONDITIONTWOCOLUMN_KEYS = {
            KEY_LST_SEARCH_CONDITION_1,
            KEY_LST_SEARCH_CONDITION_2,
            KEY_LST_SEARCH_CONDITION_3,
            KEY_LST_SEARCH_CONDITION_4,
    };

    /** lst_PCTItemMasterList kyes */
    private static final ListCellKey[] LST_PCTITEMMASTERLIST_KEYS = {
            KEY_HIDDEN_CONSIGNOR_CODE,
            KEY_HIDDEN_CONSIGNOR_NAME,
            KEY_LST_ITEM_CODE,
            KEY_LST_LOT_QTY,
            KEY_LST_JAN,
            KEY_LST_LOCATION_NO,
            KEY_LST_SINGLE_WEIGHT,
            KEY_LST_MAX_INSPECTION_QTY,
            KEY_LST_LAST_UPDATE_DATE,
            KEY_LST_ITEM_PICTURE_REGIST,
            KEY_LST_MESSAGE,
            KEY_LST_ITEM_NAME,
            KEY_LST_ITF,
            KEY_LST_WEIGHT_DISTINCT_RATE,
            KEY_LST_LAST_USED_DATE,
    };

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

    /** ListCellModel lst_PCTItemMasterList */
    private ListCellModel _lcm_lst_PCTItemMasterList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public PCTLstItemListBusiness()
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
    public void pgr_U_First(ActionEvent e)
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
    public void pgr_U_Prev(ActionEvent e)
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
    public void pgr_U_Next(ActionEvent e)
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
    public void pgr_U_Last(ActionEvent e)
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
    public void pgr_D_First(ActionEvent e)
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
    public void pgr_D_Prev(ActionEvent e)
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
    public void pgr_D_Next(ActionEvent e)
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
    public void pgr_D_Last(ActionEvent e)
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
    public void btn_Close_D_Click(ActionEvent e)
            throws Exception
    {
        // process call.
        btn_Close_D_Click_Process();
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
    // DFKLOOK:ここから修正
    /**
     * 検索条件のリストセルを作成します。
     * 
     * @param param
     *            検索条件
     * @throws Exception
     *             全ての例外を報告します。
     */
    protected void lst_SearchCondition_Make(PCTLstItemListDASCHParams param, Connection conn, Locale locale)
            throws Exception
    {
        // 1行目の表示
        ListCellLine line = _lcm_lst_SearchConditionTwoColumn.getNewLine();
        // 商品コードの大小を入替え
        String[] itemCodeList =
                WmsFormatter.getFromTo(param.getString(PCTLstItemListDASCHParams.FROM_ITEM_CODE),
                        param.getString(PCTLstItemListDASCHParams.TO_ITEM_CODE));
        // 荷主コード
        line.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W1362"));
        line.setValue(KEY_LST_SEARCH_CONDITION_2, param.getString(PCTLstItemListDASCHParams.CONSIGNOR_CODE));
        // 開始商品コード
        line.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W0482"));
        line.setValue(KEY_LST_SEARCH_CONDITION_4, itemCodeList[0]);
        _lcm_lst_SearchConditionTwoColumn.add(line);

        // 2行目の表示
        line = _lcm_lst_SearchConditionTwoColumn.getNewLine();
        // 終了商品コード
        line.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-W0483"));
        line.setValue(KEY_LST_SEARCH_CONDITION_2, itemCodeList[1]);
        // ロット入数
        line.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-P0017"));
        if (!StringUtil.isBlank(param.getString(PCTLstItemListDASCHParams.LOT_QTY)))
        {
            line.setValue(KEY_LST_SEARCH_CONDITION_4,
                    WmsFormatter.getNumFormat(param.getInt(PCTLstItemListDASCHParams.LOT_QTY)));
        }
        _lcm_lst_SearchConditionTwoColumn.add(line);

        // 3行目の表示
        line = _lcm_lst_SearchConditionTwoColumn.getNewLine();
        // JANコード
        line.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-P0246"));
        line.setValue(KEY_LST_SEARCH_CONDITION_2, param.getString(PCTLstItemListDASCHParams.JAN));
        // ITF
        line.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-W0194"));
        line.setValue(KEY_LST_SEARCH_CONDITION_4, param.getString(PCTLstItemListDASCHParams.ITF));
        _lcm_lst_SearchConditionTwoColumn.add(line);

        // 4行目の表示
        line = _lcm_lst_SearchConditionTwoColumn.getNewLine();
        // 単重量の大小を入替え
        String[] singleWeightList =
                WmsFormatter.getFromTo(param.getString(PCTLstItemListDASCHParams.FROM_SINGLE_WEIGHT),
                        param.getString(PCTLstItemListDASCHParams.TO_SINGLE_WEIGHT));
        // 開始単重量
        line.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-P0242"));
        if (!StringUtil.isBlank(singleWeightList[0]))
        {
            line.setValue(KEY_LST_SEARCH_CONDITION_2,
                    WmsFormatter.getNumFormat(Double.parseDouble(singleWeightList[0])));
        }
        // 終了単重量
        line.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-P0243"));
        if (!StringUtil.isBlank(singleWeightList[1]))
        {
            line.setValue(KEY_LST_SEARCH_CONDITION_4,
                    WmsFormatter.getNumFormat(Double.parseDouble(singleWeightList[1])));
        }
        _lcm_lst_SearchConditionTwoColumn.add(line);

        // 5行目の表示
        line = _lcm_lst_SearchConditionTwoColumn.getNewLine();
        // 商品画像登録
        line.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-P0209"));
        line.setValue(KEY_LST_SEARCH_CONDITION_2, param.getString(PCTLstItemListDASCHParams.ITEM_PICTURE_REGIST));
        _lcm_lst_SearchConditionTwoColumn.add(line);


        // 6行目の表示
        line = _lcm_lst_SearchConditionTwoColumn.getNewLine();
        // 棚の大小を入替え
        String[] locationList =
                WmsFormatter.getFromTo(param.getString(PCTLstItemListDASCHParams.FROM_LOCATION_NO),
                        param.getString(PCTLstItemListDASCHParams.TO_LOCATION_NO));
        // 開始ロケーションNo.
        line.setValue(KEY_LST_SEARCH_CONDITION_1, DispResources.getText("LBL-P0244"));
        line.setValue(KEY_LST_SEARCH_CONDITION_2, WmsFormatter.toDispLocation(locationList[0],
                WmsParam.DEFAULT_LOCATE_STYLE));
        // 終了ロケーションNo.
        line.setValue(KEY_LST_SEARCH_CONDITION_3, DispResources.getText("LBL-P0245"));
        line.setValue(KEY_LST_SEARCH_CONDITION_4, WmsFormatter.toDispLocation(locationList[1],
                WmsParam.DEFAULT_LOCATE_STYLE));
        _lcm_lst_SearchConditionTwoColumn.add(line);
    }

    /**
     * ボタン制御を行います。
     */
    protected void disabledButton()
    {
        // プレビューボタン無効化
        btn_Preview.setEnabled(false);
        // 印刷ボタン無効化
        btn_Print.setEnabled(false);
        // XLSボタン無効化
        btn_XLS.setEnabled(false);
    }

    // DFKLOOK:ここまで修正

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
        _lcm_lst_SearchConditionTwoColumn =
                new ListCellModel(lst_SearchConditionTwoColumn, LST_SEARCHCONDITIONTWOCOLUMN_KEYS, locale);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_1, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_2, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_3, false);
        _lcm_lst_SearchConditionTwoColumn.setToolTipVisible(KEY_LST_SEARCH_CONDITION_4, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale);

        // initialize lst_PCTItemMasterList.
        _lcm_lst_PCTItemMasterList = new ListCellModel(lst_PCTItemMasterList, LST_PCTITEMMASTERLIST_KEYS, locale);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_LOT_QTY, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_JAN, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_ITF, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_LOCATION_NO, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_SINGLE_WEIGHT, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_MAX_INSPECTION_QTY, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_WEIGHT_DISTINCT_RATE, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_LAST_UPDATE_DATE, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_ITEM_PICTURE_REGIST, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_LAST_USED_DATE, true);
        _lcm_lst_PCTItemMasterList.setToolTipVisible(KEY_LST_MESSAGE, true);


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
    private void lst_PCTItemMasterList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0130", KEY_LST_ITEM_NAME);
        line.addToolTip("LBL-T0084", KEY_LST_MESSAGE);
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

        // dispose DASCH.
        disposeDasch();

        Connection conn = null;
        PCTLstItemListDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new PCTLstItemListDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            PCTLstItemListDASCHParams inparam = new PCTLstItemListDASCHParams();
            PCTLstItemListParams requestParam = new PCTLstItemListParams(request);
            inparam.set(PCTLstItemListDASCHParams.CONSIGNOR_CODE, requestParam.get(PCTLstItemListParams.CONSIGNOR_CODE));
            inparam.set(PCTLstItemListDASCHParams.FROM_ITEM_CODE, requestParam.get(PCTLstItemListParams.FROM_ITEM_CODE));
            inparam.set(PCTLstItemListDASCHParams.TO_ITEM_CODE, requestParam.get(PCTLstItemListParams.TO_ITEM_CODE));
            inparam.set(PCTLstItemListDASCHParams.LOT_QTY, requestParam.get(PCTLstItemListParams.LOT_QTY));
            inparam.set(PCTLstItemListDASCHParams.JAN, requestParam.get(PCTLstItemListParams.JAN));
            inparam.set(PCTLstItemListDASCHParams.ITF, requestParam.get(PCTLstItemListParams.ITF));
            inparam.set(PCTLstItemListDASCHParams.FROM_SINGLE_WEIGHT,
                    requestParam.get(PCTLstItemListParams.FROM_SINGLE_WEIGHT));
            inparam.set(PCTLstItemListDASCHParams.TO_SINGLE_WEIGHT,
                    requestParam.get(PCTLstItemListParams.TO_SINGLE_WEIGHT));

            // DFKLOOK:ここから修正
            inparam.set(
                    PCTLstItemListDASCHParams.ITEM_PICTURE_REGIST,
                    String.valueOf(PCTDisplayUtil.getItemPicture(requestParam.getInt(PCTLstItemListParams.ITEM_PICTURE_REGIST))));
            // DFKLOOK:ここまで修正

            inparam.set(PCTLstItemListDASCHParams.FROM_LOCATION_NO,
                    requestParam.get(PCTLstItemListParams.FROM_LOCATION_NO));
            inparam.set(PCTLstItemListDASCHParams.TO_LOCATION_NO, requestParam.get(PCTLstItemListParams.TO_LOCATION_NO));

            // DFKLOOK:ここから修正
            lst_SearchCondition_Make(inparam, conn, locale);
            // DFKLOOK:ここまで修正

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_PCTItemMasterList.clear();

            if (count == 0)
            {
                message.setMsgResourceKey("6003011");

                // DFKLOOK:ここから修正
                // ボタン制御
                disabledButton();
                // DFKLOOK:ここまで修正

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
                ListCellLine line = _lcm_lst_PCTItemMasterList.getNewLine();
                line.setValue(KEY_HIDDEN_CONSIGNOR_CODE, outparam.get(PCTLstItemListDASCHParams.CONSIGNOR_CODE));
                line.setValue(KEY_HIDDEN_CONSIGNOR_NAME, outparam.get(PCTLstItemListDASCHParams.CONSIGNOR_NAME));
                line.setValue(KEY_LST_MESSAGE, outparam.get(PCTLstItemListDASCHParams.MESSAGE));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(PCTLstItemListDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(PCTLstItemListDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(PCTLstItemListDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_JAN, outparam.get(PCTLstItemListDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(PCTLstItemListDASCHParams.ITF));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(PCTLstItemListDASCHParams.LOCATION_NO));
                line.setValue(KEY_LST_SINGLE_WEIGHT, outparam.get(PCTLstItemListDASCHParams.SINGLE_WEIGHT));
                line.setValue(KEY_LST_WEIGHT_DISTINCT_RATE,
                        outparam.get(PCTLstItemListDASCHParams.WEIGHT_DISTINCT_RATE));
                line.setValue(KEY_LST_MAX_INSPECTION_QTY, outparam.get(PCTLstItemListDASCHParams.MAX_INSPECTION_QTY));
                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(PCTLstItemListDASCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_LAST_USED_DATE, outparam.get(PCTLstItemListDASCHParams.LAST_USED_DATE));
                line.setValue(KEY_LST_ITEM_PICTURE_REGIST, outparam.get(PCTLstItemListDASCHParams.ITEM_PICTURE_REGIST));
                line.setValue(KEY_LST_MESSAGE, outparam.get(PCTLstItemListDASCHParams.MESSAGE));

                lst_PCTItemMasterList_SetLineToolTip(line);
                _lcm_lst_PCTItemMasterList.add(line);
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
            _lcm_lst_PCTItemMasterList.clear();
            if (dasch != null)
            {
                dasch.close();
            }
            DBUtil.close(conn);

            // DFKLOOK:ここから修正
            // ボタン制御
            disabledButton();
            // DFKLOOK:ここまで修正
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
        PCTLstItemListDASCH dasch = null;
        try
        {
            // get session.
            dasch = (PCTLstItemListDASCH)session.getAttribute(_KEY_DASCH);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_PCTItemMasterList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_PCTItemMasterList.getNewLine();
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(PCTLstItemListDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(PCTLstItemListDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_LOT_QTY, outparam.get(PCTLstItemListDASCHParams.LOT_QTY));
                line.setValue(KEY_LST_JAN, outparam.get(PCTLstItemListDASCHParams.JAN));
                line.setValue(KEY_LST_ITF, outparam.get(PCTLstItemListDASCHParams.ITF));
                line.setValue(KEY_LST_SINGLE_WEIGHT, outparam.get(PCTLstItemListDASCHParams.SINGLE_WEIGHT));
                line.setValue(KEY_LST_WEIGHT_DISTINCT_RATE,
                        outparam.get(PCTLstItemListDASCHParams.WEIGHT_DISTINCT_RATE));
                line.setValue(KEY_LST_MAX_INSPECTION_QTY, outparam.get(PCTLstItemListDASCHParams.MAX_INSPECTION_QTY));
                line.setValue(KEY_LST_LAST_UPDATE_DATE, outparam.get(PCTLstItemListDASCHParams.LAST_UPDATE_DATE));
                line.setValue(KEY_LST_LAST_USED_DATE, outparam.get(PCTLstItemListDASCHParams.LAST_USED_DATE));
                line.setValue(KEY_LST_ITEM_PICTURE_REGIST, outparam.get(PCTLstItemListDASCHParams.ITEM_PICTURE_REGIST));
                line.setValue(KEY_LST_MESSAGE, outparam.get(PCTLstItemListDASCHParams.MESSAGE));
                line.setValue(KEY_LST_LOCATION_NO, outparam.get(PCTLstItemListDASCHParams.LOCATION_NO));
                lst_PCTItemMasterList_SetLineToolTip(line);
                _lcm_lst_PCTItemMasterList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_PCTItemMasterList.clear();
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
    private void btn_Preview_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        PrintExporter exporter = null;
        try
        {
            // DFKLOOK:ここから修正
            Date sysDay = DbDateUtil.getTimeStamp();
            Date sysTime = DbDateUtil.getTimeStamp();
            // DFKLOOK:ここまで修正

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPVExporter("PctItemMasterList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTItemMasterList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTItemMasterList.get(i);
                PctItemMasterListParams expparam = new PctItemMasterListParams();
                expparam.set(PctItemMasterListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctItemMasterListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));

                // DFKLOOK:ここから修正
                expparam.set(PctItemMasterListParams.SYS_DAY, sysDay);
                expparam.set(PctItemMasterListParams.SYS_TIME, sysTime);
                // DFKLOOK:ここまで修正

                expparam.set(PctItemMasterListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctItemMasterListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctItemMasterListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctItemMasterListParams.JAN, line.getValue(KEY_LST_JAN));
                expparam.set(PctItemMasterListParams.ITF, line.getValue(KEY_LST_ITF));
                expparam.set(PctItemMasterListParams.SINGLE_WEIGHT, line.getValue(KEY_LST_SINGLE_WEIGHT));
                expparam.set(PctItemMasterListParams.WEGHT_DISTINCT_RATE, line.getValue(KEY_LST_WEIGHT_DISTINCT_RATE));
                expparam.set(PctItemMasterListParams.MAX_INSPECTION_UNIT_QTY, line.getValue(KEY_LST_MAX_INSPECTION_QTY));
                expparam.set(PctItemMasterListParams.LAST_UPDATE, line.getValue(KEY_LST_LAST_UPDATE_DATE));
                expparam.set(PctItemMasterListParams.WORK_DAY, line.getValue(KEY_LST_LAST_USED_DATE));
                expparam.set(PctItemMasterListParams.ITEM_PICTURE_FLAG, line.getValue(KEY_LST_ITEM_PICTURE_REGIST));
                expparam.set(PctItemMasterListParams.MESSAGE1, line.getValue(KEY_LST_MESSAGE));
                expparam.set(PctItemMasterListParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                if (!exporter.write(expparam))
                {
                    break;
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
            }

            // redirect.
            previewPDF(downloadFile.getPath());

            // DFKLOOK start
            setFocus(null);
            // DFKLOOK end
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (exporter != null)
            {
                exporter.close();
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_Print_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        PrintExporter exporter = null;
        try
        {
            // DFKLOOK:ここから修正
            Date sysDay = DbDateUtil.getTimeStamp();
            Date sysTime = DbDateUtil.getTimeStamp();
            // DFKLOOK:ここまで修正

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("PctItemMasterList", false);
            exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTItemMasterList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTItemMasterList.get(i);
                PctItemMasterListParams expparam = new PctItemMasterListParams();
                expparam.set(PctItemMasterListParams.CONSIGNOR_CODE, line.getValue(KEY_HIDDEN_CONSIGNOR_CODE));
                expparam.set(PctItemMasterListParams.CONSIGNOR_NAME, line.getValue(KEY_HIDDEN_CONSIGNOR_NAME));

                // DFKLOOK:ここから修正
                expparam.set(PctItemMasterListParams.SYS_DAY, sysDay);
                expparam.set(PctItemMasterListParams.SYS_TIME, sysTime);
                // DFKLOOK:ここまで修正

                expparam.set(PctItemMasterListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctItemMasterListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctItemMasterListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctItemMasterListParams.JAN, line.getValue(KEY_LST_JAN));
                expparam.set(PctItemMasterListParams.ITF, line.getValue(KEY_LST_ITF));
                expparam.set(PctItemMasterListParams.SINGLE_WEIGHT, line.getValue(KEY_LST_SINGLE_WEIGHT));
                expparam.set(PctItemMasterListParams.WEGHT_DISTINCT_RATE, line.getValue(KEY_LST_WEIGHT_DISTINCT_RATE));
                expparam.set(PctItemMasterListParams.MAX_INSPECTION_UNIT_QTY, line.getValue(KEY_LST_MAX_INSPECTION_QTY));
                expparam.set(PctItemMasterListParams.LAST_UPDATE, line.getValue(KEY_LST_LAST_UPDATE_DATE));
                expparam.set(PctItemMasterListParams.WORK_DAY, line.getValue(KEY_LST_LAST_USED_DATE));
                expparam.set(PctItemMasterListParams.ITEM_PICTURE_FLAG, line.getValue(KEY_LST_ITEM_PICTURE_REGIST));
                expparam.set(PctItemMasterListParams.MESSAGE1, line.getValue(KEY_LST_MESSAGE));
                expparam.set(PctItemMasterListParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
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
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
        }
        finally
        {
            if (exporter != null)
            {
                exporter.close();
            }
        }
    }

    /**
     *
     * @throws Exception
     */
    private void btn_XLS_Click_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Exporter exporter = null;
        try
        {
            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newExcelExporter("PctItemMasterList", getSession());
            File downloadFile = exporter.open();

            // export.
            for (int i = 1; i <= _lcm_lst_PCTItemMasterList.size(); i++)
            {
                ListCellLine line = _lcm_lst_PCTItemMasterList.get(i);
                PctItemMasterListParams expparam = new PctItemMasterListParams();
                expparam.set(PctItemMasterListParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
                expparam.set(PctItemMasterListParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
                expparam.set(PctItemMasterListParams.LOT_ENTERING_QTY, line.getValue(KEY_LST_LOT_QTY));
                expparam.set(PctItemMasterListParams.JAN, line.getValue(KEY_LST_JAN));
                expparam.set(PctItemMasterListParams.ITF, line.getValue(KEY_LST_ITF));
                expparam.set(PctItemMasterListParams.LOCATION_NO, line.getValue(KEY_LST_LOCATION_NO));
                expparam.set(PctItemMasterListParams.SINGLE_WEIGHT, line.getValue(KEY_LST_SINGLE_WEIGHT));
                expparam.set(PctItemMasterListParams.WEGHT_DISTINCT_RATE, line.getValue(KEY_LST_WEIGHT_DISTINCT_RATE));
                expparam.set(PctItemMasterListParams.MAX_INSPECTION_UNIT_QTY, line.getValue(KEY_LST_MAX_INSPECTION_QTY));
                expparam.set(PctItemMasterListParams.LAST_UPDATE, line.getValue(KEY_LST_LAST_UPDATE_DATE));
                expparam.set(PctItemMasterListParams.WORK_DAY, line.getValue(KEY_LST_LAST_USED_DATE));
                expparam.set(PctItemMasterListParams.ITEM_PICTURE_FLAG, line.getValue(KEY_LST_ITEM_PICTURE_REGIST));
                expparam.set(PctItemMasterListParams.MESSAGE1, line.getValue(KEY_LST_MESSAGE));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

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
            if (exporter != null)
            {
                exporter.close();
            }
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
    private void btn_Close_D_Click_Process()
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
