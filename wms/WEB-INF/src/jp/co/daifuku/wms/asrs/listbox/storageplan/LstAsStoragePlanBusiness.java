package jp.co.daifuku.wms.asrs.listbox.storageplan;

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
import jp.co.daifuku.bluedog.model.table.AreaCellColumn;
import jp.co.daifuku.bluedog.model.table.DateCellColumn;
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
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dasch.LstAsStoragePlanDASCH;
import jp.co.daifuku.wms.asrs.dasch.LstAsStoragePlanDASCHParams;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * AS/RS 入庫予定検索の画面処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:10:25 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class LstAsStoragePlanBusiness
        extends LstAsStoragePlan
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
    private static final ListCellKey KEY_LST_SEARCH_CONDITION =
            new ListCellKey("LST_SEARCH_CONDITION", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_2) */
    private static final ListCellKey KEY_LST_COLUMN_2 =
            new ListCellKey("LST_COLUMN_2", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_3) */
    private static final ListCellKey KEY_LST_COLUMN_3 =
            new ListCellKey("LST_COLUMN_3", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_4) */
    private static final ListCellKey KEY_LST_COLUMN_4 =
            new ListCellKey("LST_COLUMN_4", new StringCellColumn(), true, false);

    /** lst_ASRSStoragePlanList(LST_SELECT) */
    private static final ListCellKey KEY_LST_SELECT =
            new ListCellKey("LST_SELECT", new StringCellColumn(), true, false);

    /** lst_ASRSStoragePlanList(LST_PLAN_DAY) */
    private static final ListCellKey KEY_LST_PLAN_DAY =
            new ListCellKey("LST_PLAN_DAY", new DateCellColumn(DateCellColumn.DATE_TYPE_LONG,
                    DateCellColumn.TIME_TYPE_NONE), true, false);

    /** lst_ASRSStoragePlanList(LST_ITEM_CODE) */
    private static final ListCellKey KEY_LST_ITEM_CODE =
            new ListCellKey("LST_ITEM_CODE", new StringCellColumn(), true, false);

    /** lst_ASRSStoragePlanList(LST_ITEM_NAME) */
    private static final ListCellKey KEY_LST_ITEM_NAME =
            new ListCellKey("LST_ITEM_NAME", new StringCellColumn(), true, false);

    /** lst_ASRSStoragePlanList(LST_PLAN_LOT_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOT_NO =
            new ListCellKey("LST_PLAN_LOT_NO", new StringCellColumn(), true, false);

    /** lst_ASRSStoragePlanList(LST_ENTERING_QTY) */
    private static final ListCellKey KEY_LST_ENTERING_QTY =
            new ListCellKey("LST_ENTERING_QTY", new NumberCellColumn(0), true, false);

    /** lst_ASRSStoragePlanList(LST_CASE_QTY) */
    private static final ListCellKey KEY_LST_CASE_QTY =
            new ListCellKey("LST_CASE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ASRSStoragePlanList(LST_PIECE_QTY) */
    private static final ListCellKey KEY_LST_PIECE_QTY =
            new ListCellKey("LST_PIECE_QTY", new NumberCellColumn(0), true, false);

    /** lst_ASRSStoragePlanList(LST_PLAN_AREA_NO) */
    private static final ListCellKey KEY_LST_PLAN_AREA_NO =
            new ListCellKey("LST_PLAN_AREA_NO", new AreaCellColumn(), true, false);

    /** lst_ASRSStoragePlanList(LST_PLAN_LOCATION_NO) */
    private static final ListCellKey KEY_LST_PLAN_LOCATION_NO =
            new ListCellKey("LST_PLAN_LOCATION_NO", new LocationCellColumn(), true, false);

    /** lst_SearchCondition kyes */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
            KEY_LST_SEARCH_CONDITION,
            KEY_LST_COLUMN_2,
            KEY_LST_COLUMN_3,
            KEY_LST_COLUMN_4,
    };

    /** lst_ASRSStoragePlanList kyes */
    private static final ListCellKey[] LST_ASRSSTORAGEPLANLIST_KEYS = {
            KEY_LST_SELECT,
            KEY_LST_PLAN_DAY,
            KEY_LST_ITEM_CODE,
            KEY_LST_PLAN_LOT_NO,
            KEY_LST_ENTERING_QTY,
            KEY_LST_CASE_QTY,
            KEY_LST_PLAN_AREA_NO,
            KEY_LST_ITEM_NAME,
            KEY_LST_PIECE_QTY,
            KEY_LST_PLAN_LOCATION_NO,
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

    /** ListCellModel lst_ASRSStoragePlanList */
    private ListCellModel _lcm_lst_ASRSStoragePlanList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstAsStoragePlanBusiness()
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
    public void lbl_SettingName_Server(ActionEvent e)
            throws Exception
    {
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
    public void lst_SearchCondition_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchCondition_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchCondition_InputComplete(ActionEvent e)
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
    public void lst_SearchCondition_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_SearchCondition_Change(ActionEvent e)
            throws Exception
    {
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
    public void btn_Close_U_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ASRSStoragePlanList_Click(ActionEvent e)
            throws Exception
    {
        // get event source column.
        int activeCol = lst_ASRSStoragePlanList.getActiveCol();

        // choose process.
        if (_lcm_lst_ASRSStoragePlanList.getColumnIndex(KEY_LST_SELECT) == activeCol)
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
    public void lst_ASRSStoragePlanList_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ASRSStoragePlanList_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ASRSStoragePlanList_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ASRSStoragePlanList_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ASRSStoragePlanList_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_ASRSStoragePlanList_Change(ActionEvent e)
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
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void btn_Close_D_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lbl_ListName_Server(ActionEvent e)
            throws Exception
    {
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
    // DFKLOOK ここから修正
    /**
     * <code>outParam</code>配列の値をリストセルエリアに設定します。<BR>
     * <BR>
     * 概要:<BR>
     * <DIR>
     *     1.リストセルエリアのクリア<BR>
     *     2.配列データをリストセルエリアへ設定<BR>
     *     <BR>
     *     項目名[初期値]<BR>
     *     <DIR>
     *         選択[チェックなし]<BR>
     *     </DIR>
     * </DIR>
     * @param inParam 入庫出力パラメータクラス
     * @throws Exception 全ての例外を報告します。
     */
    protected void lst_SearchCondition_Make(LstAsStoragePlanDASCHParams inParam, Connection conn)
            throws Exception
    {

        // リストセルエリアをクリアする
        lst_SearchCondition.clearRow();

        // 行の追加
        lst_SearchCondition.addRow();
        lst_SearchCondition.setCurrentRow(1);

        //入庫予定日
        lst_SearchCondition.setValue(1, DisplayText.getText("LBL-W0161"));
        lst_SearchCondition.setValue(2, WmsFormatter.toDispDate(inParam.getDate(LstAsStoragePlanDASCHParams.PLAN_DAY),
                getHttpRequest().getLocale()));

        //商品コード
        lst_SearchCondition.addRow();
        lst_SearchCondition.setCurrentRow(2);

        lst_SearchCondition.setValue(1, DisplayText.getText("LBL-W0189"));
        lst_SearchCondition.setValue(2, inParam.getString(LstAsStoragePlanDASCHParams.ITEM_CODE));

        lst_SearchCondition.setValue(3, DisplayText.getText("LBL-W0130"));
        if (!StringUtil.isBlank(inParam.getString(LstAsStoragePlanDASCHParams.ITEM_CODE)))
        {
            ItemController itemCon = new ItemController(conn, this.getClass());
            lst_SearchCondition.setValue(4, itemCon.getItemName(
                    inParam.getString(LstAsStoragePlanDASCHParams.ITEM_CODE),
                    inParam.getString(LstAsStoragePlanDASCHParams.CONSIGNOR_CODE)));
        }

        //ロットNo.
        lst_SearchCondition.addRow();
        lst_SearchCondition.setCurrentRow(3);

        lst_SearchCondition.setValue(1, DisplayText.getText("LBL-W0196"));
        lst_SearchCondition.setValue(2, inParam.getString(LstAsStoragePlanDASCHParams.PLAN_LOT_NO));
    }

    // DFKLOOK ここまで修正

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
        _pager = new PagerModel(new Pager[] {
                pgr_U,
                pgr_D
        }, locale);

        // initialize lst_ASRSStoragePlanList.
        _lcm_lst_ASRSStoragePlanList = new ListCellModel(lst_ASRSStoragePlanList, LST_ASRSSTORAGEPLANLIST_KEYS, locale);
        _lcm_lst_ASRSStoragePlanList.setToolTipVisible(KEY_LST_SELECT, true);
        _lcm_lst_ASRSStoragePlanList.setToolTipVisible(KEY_LST_PLAN_DAY, true);
        _lcm_lst_ASRSStoragePlanList.setToolTipVisible(KEY_LST_ITEM_CODE, true);
        _lcm_lst_ASRSStoragePlanList.setToolTipVisible(KEY_LST_ITEM_NAME, true);
        _lcm_lst_ASRSStoragePlanList.setToolTipVisible(KEY_LST_PLAN_LOT_NO, true);
        _lcm_lst_ASRSStoragePlanList.setToolTipVisible(KEY_LST_ENTERING_QTY, true);
        _lcm_lst_ASRSStoragePlanList.setToolTipVisible(KEY_LST_CASE_QTY, true);
        _lcm_lst_ASRSStoragePlanList.setToolTipVisible(KEY_LST_PIECE_QTY, true);
        _lcm_lst_ASRSStoragePlanList.setToolTipVisible(KEY_LST_PLAN_AREA_NO, true);
        _lcm_lst_ASRSStoragePlanList.setToolTipVisible(KEY_LST_PLAN_LOCATION_NO, true);

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
    private void lst_ASRSStoragePlanList_SetLineToolTip(ListCellLine line)
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
        LstAsStoragePlanDASCH dasch = null;
        // DFKLOOK セッション保持フラグ追加
        boolean isSuccess = false;
        // DFKLOOK ここまで
        try
        {
            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstAsStoragePlanDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstAsStoragePlanDASCHParams inparam = new LstAsStoragePlanDASCHParams();
            LstAsStoragePlanParams requestParam = new LstAsStoragePlanParams(request);
            inparam.set(LstAsStoragePlanDASCHParams.PLAN_DAY, requestParam.get(LstAsStoragePlanParams.PLAN_DAY));
            inparam.set(LstAsStoragePlanDASCHParams.CONSIGNOR_CODE,
                    requestParam.get(LstAsStoragePlanParams.CONSIGNOR_CODE));
            inparam.set(LstAsStoragePlanDASCHParams.ITEM_CODE, requestParam.get(LstAsStoragePlanParams.ITEM_CODE));
            inparam.set(LstAsStoragePlanDASCHParams.PLAN_LOT_NO, requestParam.get(LstAsStoragePlanParams.PLAN_LOT_NO));

            // get count.
            int count = dasch.count(inparam);
            _pager.setMax(count);
            _lcm_lst_ASRSStoragePlanList.clear();

            // DFKLOOK ここから修正
            lst_SearchCondition_Make(inparam, conn);
            // DFKLOOK ここまで修正

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
                ListCellLine line = _lcm_lst_ASRSStoragePlanList.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(LstAsStoragePlanDASCHParams.SELECT));
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(LstAsStoragePlanDASCHParams.PLAN_DAY));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstAsStoragePlanDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstAsStoragePlanDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(LstAsStoragePlanDASCHParams.PLAN_LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(LstAsStoragePlanDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(LstAsStoragePlanDASCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(LstAsStoragePlanDASCHParams.PIECE_QTY));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(LstAsStoragePlanDASCHParams.PLAN_AREA_NO));
                line.setValue(KEY_LST_PLAN_LOCATION_NO, outparam.get(LstAsStoragePlanDASCHParams.PLAN_LOCATION_NO));
                lst_ASRSStoragePlanList_SetLineToolTip(line);
                _lcm_lst_ASRSStoragePlanList.add(line);
            }

            // save session.
            session.setAttribute(_KEY_DASCH_LIST, dasch);
            // DFKLOOK フラグ更新
            isSuccess = true;
            // DFKLOOK ここまで
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ASRSStoragePlanList.clear();
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
            // DFKLOOK ここまで
        }
    }

    /**
     *
     * @throws Exception
     */
    private void page_Load_SetList()
            throws Exception
    {
        LstAsStoragePlanDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstAsStoragePlanDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_ASRSStoragePlanList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_ASRSStoragePlanList.getNewLine();
                line.setValue(KEY_LST_SELECT, outparam.get(LstAsStoragePlanDASCHParams.SELECT));
                line.setValue(KEY_LST_PLAN_DAY, outparam.get(LstAsStoragePlanDASCHParams.PLAN_DAY));
                line.setValue(KEY_LST_ITEM_CODE, outparam.get(LstAsStoragePlanDASCHParams.ITEM_CODE));
                line.setValue(KEY_LST_ITEM_NAME, outparam.get(LstAsStoragePlanDASCHParams.ITEM_NAME));
                line.setValue(KEY_LST_PLAN_LOT_NO, outparam.get(LstAsStoragePlanDASCHParams.PLAN_LOT_NO));
                line.setValue(KEY_LST_ENTERING_QTY, outparam.get(LstAsStoragePlanDASCHParams.ENTERING_QTY));
                line.setValue(KEY_LST_CASE_QTY, outparam.get(LstAsStoragePlanDASCHParams.CASE_QTY));
                line.setValue(KEY_LST_PIECE_QTY, outparam.get(LstAsStoragePlanDASCHParams.PIECE_QTY));
                line.setValue(KEY_LST_PLAN_AREA_NO, outparam.get(LstAsStoragePlanDASCHParams.PLAN_AREA_NO));
                line.setValue(KEY_LST_PLAN_LOCATION_NO, outparam.get(LstAsStoragePlanDASCHParams.PLAN_LOCATION_NO));
                lst_ASRSStoragePlanList_SetLineToolTip(line);
                _lcm_lst_ASRSStoragePlanList.add(line);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_ASRSStoragePlanList.clear();
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
        DataAccessSCH dasch = (DataAccessSCH)session.getAttribute(_KEY_DASCH_LIST);
        if (dasch != null)
        {
            session.removeAttribute(_KEY_DASCH_LIST);
            dasch.close();
            // DFKLOOK コネクションクローズ追加
            DBUtil.close(dasch.getConnection());
            // DFKLOOK ここまで
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
        int row = lst_ASRSStoragePlanList.getActiveRow();
        lst_ASRSStoragePlanList.setCurrentRow(row);
        ListCellLine line = _lcm_lst_ASRSStoragePlanList.get(row);

        // output parameter.
        LstAsStoragePlanParams outparam = new LstAsStoragePlanParams();
        outparam.set(LstAsStoragePlanParams.SELECT, line.getValue(KEY_LST_SELECT));
        outparam.set(LstAsStoragePlanParams.PLAN_DAY, line.getValue(KEY_LST_PLAN_DAY));
        outparam.set(LstAsStoragePlanParams.ITEM_CODE, line.getValue(KEY_LST_ITEM_CODE));
        outparam.set(LstAsStoragePlanParams.ITEM_NAME, line.getValue(KEY_LST_ITEM_NAME));
        outparam.set(LstAsStoragePlanParams.PLAN_LOT_NO, line.getValue(KEY_LST_PLAN_LOT_NO));
        outparam.set(LstAsStoragePlanParams.ENTERING_QTY, line.getValue(KEY_LST_ENTERING_QTY));
        outparam.set(LstAsStoragePlanParams.CASE_QTY, line.getValue(KEY_LST_CASE_QTY));
        outparam.set(LstAsStoragePlanParams.PIECE_QTY, line.getValue(KEY_LST_PIECE_QTY));
        outparam.set(LstAsStoragePlanParams.PLAN_AREA_NO, line.getValue(KEY_LST_PLAN_AREA_NO));
        outparam.set(LstAsStoragePlanParams.PLAN_LOCATION_NO, line.getValue(KEY_LST_PLAN_LOCATION_NO));

        ForwardParameters forwardParam = outparam.toForwardParameters();
        forwardParam.setParameter(_KEY_POPUPSOURCE, viewState.getString(_KEY_POPUPSOURCE));
        parentRedirect(forwardParam);
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
