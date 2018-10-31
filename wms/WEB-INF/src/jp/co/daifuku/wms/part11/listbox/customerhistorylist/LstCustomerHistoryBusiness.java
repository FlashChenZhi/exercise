// $Id: LstCustomerHistoryBusiness.java 7590 2010-03-15 13:47:52Z kishimoto $
package jp.co.daifuku.wms.part11.listbox.customerhistorylist;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

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
import jp.co.daifuku.bluedog.model.table.StringCellColumn;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Pager;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.DfkDateFormat.DATE_FORMAT;
import jp.co.daifuku.foundation.common.DfkDateFormat.TIME_FORMAT;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.da.DataAccessSCH;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.util.SessionUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.part11.dasch.LstCustomerHistoryDASCH;
import jp.co.daifuku.wms.part11.dasch.LstCustomerHistoryDASCHParams;

/**
 * 出荷先マスタ更新履歴詳細一覧の画面処理を行います。
 *
 * @version $Revision: 7590 $, $Date: 2010-03-15 22:47:52 +0900 (月, 15 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class LstCustomerHistoryBusiness
        extends LstCustomerHistory
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

    /** lst_SearchCondition(LST_COLUMN_1) */
    private static final ListCellKey KEY_LST_COLUMN_1 = new ListCellKey("LST_COLUMN_1", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_2) */
    private static final ListCellKey KEY_LST_COLUMN_2 = new ListCellKey("LST_COLUMN_2", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_3) */
    private static final ListCellKey KEY_LST_COLUMN_3 = new ListCellKey("LST_COLUMN_3", new StringCellColumn(), true, false);

    /** lst_SearchCondition(LST_COLUMN_4) */
    private static final ListCellKey KEY_LST_COLUMN_4 = new ListCellKey("LST_COLUMN_4", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_LOGDATE_DAY) */
    private static final ListCellKey KEY_LST_LOGDATE_DAY = new ListCellKey("LST_LOGDATE_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_CustomerHistoryList(LST_LOGDATE_TIME) */
    private static final ListCellKey KEY_LST_LOGDATE_TIME = new ListCellKey("LST_LOGDATE_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, false);

    /** lst_CustomerHistoryList(LST_UPDATE_KIND) */
    private static final ListCellKey KEY_LST_UPDATE_KIND = new ListCellKey("LST_UPDATE_KIND", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_USER_ID) */
    private static final ListCellKey KEY_LST_USER_ID = new ListCellKey("LST_USER_ID", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_IP_ADDRESS) */
    private static final ListCellKey KEY_LST_IP_ADDRESS = new ListCellKey("LST_IP_ADDRESS", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_TERMINAL_NAME) */
    private static final ListCellKey KEY_LST_TERMINAL_NAME = new ListCellKey("LST_TERMINAL_NAME", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_CUSTOMER_CODE) */
    private static final ListCellKey KEY_LST_CUSTOMER_CODE = new ListCellKey("LST_CUSTOMER_CODE", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_CUSTOMER_NAME = new ListCellKey("LST_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_UPDATE_CUSTOMER_NAME) */
    private static final ListCellKey KEY_LST_UPDATE_CUSTOMER_NAME = new ListCellKey("LST_UPDATE_CUSTOMER_NAME", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_ROUTE) */
    private static final ListCellKey KEY_LST_ROUTE = new ListCellKey("LST_ROUTE", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_UPDATE_ROUTE) */
    private static final ListCellKey KEY_LST_UPDATE_ROUTE = new ListCellKey("LST_UPDATE_ROUTE", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_POSTAL_CODE) */
    private static final ListCellKey KEY_LST_POSTAL_CODE = new ListCellKey("LST_POSTAL_CODE", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_UPDATE_POSTAL_CODE) */
    private static final ListCellKey KEY_LST_UPDATE_POSTAL_CODE = new ListCellKey("LST_UPDATE_POSTAL_CODE", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_PREFECTURE) */
    private static final ListCellKey KEY_LST_PREFECTURE = new ListCellKey("LST_PREFECTURE", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_UPDATE_PREFECTURE) */
    private static final ListCellKey KEY_LST_UPDATE_PREFECTURE = new ListCellKey("LST_UPDATE_PREFECTURE", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_ADDRESS1) */
    private static final ListCellKey KEY_LST_ADDRESS1 = new ListCellKey("LST_ADDRESS1", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_UPDATE_ADDRESS1) */
    private static final ListCellKey KEY_LST_UPDATE_ADDRESS1 = new ListCellKey("LST_UPDATE_ADDRESS1", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_ADDRESS2) */
    private static final ListCellKey KEY_LST_ADDRESS2 = new ListCellKey("LST_ADDRESS2", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_UPDATE_ADDRESS2) */
    private static final ListCellKey KEY_LST_UPDATE_ADDRESS2 = new ListCellKey("LST_UPDATE_ADDRESS2", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_TELEPHONE) */
    private static final ListCellKey KEY_LST_TELEPHONE = new ListCellKey("LST_TELEPHONE", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_UPDATE_TELEPHONE) */
    private static final ListCellKey KEY_LST_UPDATE_TELEPHONE = new ListCellKey("LST_UPDATE_TELEPHONE", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_CONTACT1) */
    private static final ListCellKey KEY_LST_CONTACT1 = new ListCellKey("LST_CONTACT1", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_UPDATE_CONTACT1) */
    private static final ListCellKey KEY_LST_UPDATE_CONTACT1 = new ListCellKey("LST_UPDATE_CONTACT1", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_CONTACT2) */
    private static final ListCellKey KEY_LST_CONTACT2 = new ListCellKey("LST_CONTACT2", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_UPDATE_CONTACT2) */
    private static final ListCellKey KEY_LST_UPDATE_CONTACT2 = new ListCellKey("LST_UPDATE_CONTACT2", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_SORTING_PLACE) */
    private static final ListCellKey KEY_LST_SORTING_PLACE = new ListCellKey("LST_SORTING_PLACE", new StringCellColumn(), true, false);

    /** lst_CustomerHistoryList(LST_UPDATE_SORTING_PLACE) */
    private static final ListCellKey KEY_LST_UPDATE_SORTING_PLACE = new ListCellKey("LST_UPDATE_SORTING_PLACE", new StringCellColumn(), true, false);

    /** lst_SearchCondition keys */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
        KEY_LST_COLUMN_1,
        KEY_LST_COLUMN_2,
        KEY_LST_COLUMN_3,
        KEY_LST_COLUMN_4,
    };

    /** lst_CustomerHistoryList keys */
    private static final ListCellKey[] LST_CUSTOMERHISTORYLIST_KEYS = {
        KEY_LST_NO,
        KEY_LST_LOGDATE_DAY,
        KEY_LST_UPDATE_KIND,
        KEY_LST_USER_ID,
        KEY_LST_IP_ADDRESS,
        KEY_LST_CUSTOMER_CODE,
        KEY_LST_CUSTOMER_NAME,
        KEY_LST_ROUTE,
        KEY_LST_POSTAL_CODE,
        KEY_LST_PREFECTURE,
        KEY_LST_ADDRESS1,
        KEY_LST_ADDRESS2,
        KEY_LST_TELEPHONE,
        KEY_LST_CONTACT1,
        KEY_LST_CONTACT2,
        KEY_LST_SORTING_PLACE,
        KEY_LST_LOGDATE_TIME,
        KEY_LST_USER_NAME,
        KEY_LST_TERMINAL_NAME,
        KEY_LST_UPDATE_CUSTOMER_NAME,
        KEY_LST_UPDATE_ROUTE,
        KEY_LST_UPDATE_POSTAL_CODE,
        KEY_LST_UPDATE_PREFECTURE,
        KEY_LST_UPDATE_ADDRESS1,
        KEY_LST_UPDATE_ADDRESS2,
        KEY_LST_UPDATE_TELEPHONE,
        KEY_LST_UPDATE_CONTACT1,
        KEY_LST_UPDATE_CONTACT2,
        KEY_LST_UPDATE_SORTING_PLACE,
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

    /** ListCellModel lst_CustomerHistoryList */
    private ListCellModel _lcm_lst_CustomerHistoryList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstCustomerHistoryBusiness()
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
    public void lst_CustomerHistoryList_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_CustomerHistoryList_EnterKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_CustomerHistoryList_TabKey(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_CustomerHistoryList_InputComplete(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_CustomerHistoryList_ColumClick(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_CustomerHistoryList_Server(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_CustomerHistoryList_Change(ActionEvent e)
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
        pager_SetPage();
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
        pager_SetPage();
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
        pager_SetPage();
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
        pager_SetPage();
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
    public void btn_Close_U_Click(ActionEvent e)
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
    // DFKLOOK ここから修正 メソッド追加
    /**
     * 日付と時刻の入力チェックと、エラー時のメッセージセットを行います<BR>
     * エラーの通知はScheduleExceptionにて行います
     * @param p
     * @throws ScheduleException
     */
    protected boolean checkDate(Params p)
            throws ScheduleException
    {
        WmsChecker chk = new WmsChecker();

        if (!chk.checkDate(WmsFormatter.toDate(p.getString(LstCustomerHistoryDASCHParams.DISPFROMDAY_KEY)),
                WmsFormatter.toTime(p.getString(LstCustomerHistoryDASCHParams.DISPFROMTIME_KEY))))
        {
            message.setMsgResourceKey(chk.getMessage());
            return false;
        }

        if (!chk.checkDate(WmsFormatter.toDate(p.getString(LstCustomerHistoryDASCHParams.DISPTODAY_KEY)),
                WmsFormatter.toTime(p.getString(LstCustomerHistoryDASCHParams.DISPTOTIME_KEY))))
        {
            message.setMsgResourceKey(chk.getMessage());
            return false;
        }

        return true;
    }

    /**
     * 検索条件のリストセルを作成します。
     * @param param 検索条件
     */
    protected boolean lst_SearchCondition_Make(LstCustomerHistoryDASCHParams param, Locale locale)
            throws ScheduleException
    {
        // 検索日時の補完処理
        Date[] tmp =
                WmsFormatter.getFromTo(WmsFormatter.toDate(param.getString(LstCustomerHistoryDASCHParams.DISPFROMDAY_KEY)),
                        WmsFormatter.toTime(param.getString(LstCustomerHistoryDASCHParams.DISPFROMTIME_KEY)),
                        WmsFormatter.toDate(param.getString(LstCustomerHistoryDASCHParams.DISPTODAY_KEY)),
                        WmsFormatter.toTime(param.getString(LstCustomerHistoryDASCHParams.DISPTOTIME_KEY)));
        Date from = tmp[0];
        Date to = tmp[1];

        int line = 1;

        // 1行目
        lst_SearchCondition.addRow();
        lst_SearchCondition.setCurrentRow(line);

        // 開始検索日時
        lst_SearchCondition.setValue(1, DisplayText.getText("LBL-W0397"));
        lst_SearchCondition.setValue(2, WmsFormatter.toDispDate(from, locale) + " "
                + WmsFormatter.toDispTime(from, locale));

        // 終了検索日時
        lst_SearchCondition.setValue(3, DisplayText.getText("LBL-W0398"));
        lst_SearchCondition.setValue(4, WmsFormatter.toDispDate(to, locale) + " " + WmsFormatter.toDispTime(to, locale));

        line++;

        // 2行目
        lst_SearchCondition.addRow();
        lst_SearchCondition.setCurrentRow(line);

        // ユーザID
        lst_SearchCondition.setValue(1, DisplayText.getText("LBL-T0031"));
        lst_SearchCondition.setValue(2, param.getString(LstCustomerHistoryDASCHParams.USERID_KEY));

        line++;

        // 3行目
        lst_SearchCondition.addRow();
        lst_SearchCondition.setCurrentRow(line);

        // DS番号
        lst_SearchCondition.setValue(1, DisplayText.getText("LBL-W8003"));
        lst_SearchCondition.setValue(2, param.getString(LstCustomerHistoryDASCHParams.DSNUMBER_KEY));

        // 画面名称
        lst_SearchCondition.setValue(3, DisplayText.getText("LBL-W8007"));
        if (!StringUtil.isBlank(param.getString(LstCustomerHistoryDASCHParams.PAGENAMERESOURCEKEY)))
        {
            lst_SearchCondition.setValue(4,
                    DisplayText.getText(param.getString(LstCustomerHistoryDASCHParams.PAGENAMERESOURCEKEY)));
        }

        return true;
    }

    // DFKLOOK ここまで修正 メソッド追加

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
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_1, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_2, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_3, false);
        _lcm_lst_SearchCondition.setToolTipVisible(KEY_LST_COLUMN_4, false);

        // initialize pager control.
        _pager = new PagerModel(new Pager[]{pgr_U, pgr_D}, locale);

        // initialize lst_CustomerHistoryList.
        _lcm_lst_CustomerHistoryList = new ListCellModel(lst_CustomerHistoryList, LST_CUSTOMERHISTORYLIST_KEYS, locale);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_NO, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_LOGDATE_DAY, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_LOGDATE_TIME, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_UPDATE_KIND, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_USER_ID, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_USER_NAME, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_IP_ADDRESS, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_TERMINAL_NAME, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_CUSTOMER_CODE, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_CUSTOMER_NAME, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_UPDATE_CUSTOMER_NAME, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_ROUTE, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_UPDATE_ROUTE, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_POSTAL_CODE, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_UPDATE_POSTAL_CODE, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_PREFECTURE, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_UPDATE_PREFECTURE, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_ADDRESS1, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_UPDATE_ADDRESS1, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_ADDRESS2, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_UPDATE_ADDRESS2, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_TELEPHONE, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_UPDATE_TELEPHONE, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_CONTACT1, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_UPDATE_CONTACT1, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_CONTACT2, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_UPDATE_CONTACT2, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_SORTING_PLACE, true);
        _lcm_lst_CustomerHistoryList.setToolTipVisible(KEY_LST_UPDATE_SORTING_PLACE, true);

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
    private void lst_CustomerHistoryList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);
        line.addToolTip("LBL-W8006", KEY_LST_TERMINAL_NAME);
        line.addToolTip("LBL-W8031", KEY_LST_CUSTOMER_NAME);
        line.addToolTip("LBL-W8032", KEY_LST_UPDATE_CUSTOMER_NAME);
        line.addToolTip("LBL-W0574", KEY_LST_POSTAL_CODE);
        line.addToolTip("LBL-W0575", KEY_LST_UPDATE_POSTAL_CODE);
        line.addToolTip("LBL-W0576", KEY_LST_PREFECTURE);
        line.addToolTip("LBL-W0577", KEY_LST_UPDATE_PREFECTURE);
        line.addToolTip("LBL-W0578", KEY_LST_ADDRESS1);
        line.addToolTip("LBL-W0590", KEY_LST_UPDATE_ADDRESS1);
        line.addToolTip("LBL-W0588", KEY_LST_ADDRESS2);
        line.addToolTip("LBL-W0589", KEY_LST_UPDATE_ADDRESS2);
        line.addToolTip("LBL-W0582", KEY_LST_TELEPHONE);
        line.addToolTip("LBL-W0583", KEY_LST_UPDATE_TELEPHONE);
        line.addToolTip("LBL-W0584", KEY_LST_CONTACT1);
        line.addToolTip("LBL-W0585", KEY_LST_UPDATE_CONTACT1);
        line.addToolTip("LBL-W0586", KEY_LST_CONTACT2);
        line.addToolTip("LBL-W0587", KEY_LST_UPDATE_CONTACT2);
        line.addToolTip("LBL-W0549", KEY_LST_SORTING_PLACE);
        line.addToolTip("LBL-W0550", KEY_LST_UPDATE_SORTING_PLACE);
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
    private void page_Load_Process()
            throws Exception
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = httpRequest.getLocale();

        Connection conn = null;
        LstCustomerHistoryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstCustomerHistoryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstCustomerHistoryDASCHParams inparam = new LstCustomerHistoryDASCHParams();
            LstCustomerHistoryParams requestParam = new LstCustomerHistoryParams(request);
            inparam.set(LstCustomerHistoryDASCHParams.TABLE_NAME, requestParam.get(LstCustomerHistoryParams.TABLE_NAME));
            inparam.set(LstCustomerHistoryDASCHParams.DISPFROMDAY_KEY, requestParam.get(LstCustomerHistoryParams.DISPFROMDAY_KEY));
            inparam.set(LstCustomerHistoryDASCHParams.DISPFROMTIME_KEY, requestParam.get(LstCustomerHistoryParams.DISPFROMTIME_KEY));
            inparam.set(LstCustomerHistoryDASCHParams.DISPTODAY_KEY, requestParam.get(LstCustomerHistoryParams.DISPTODAY_KEY));
            inparam.set(LstCustomerHistoryDASCHParams.DISPTOTIME_KEY, requestParam.get(LstCustomerHistoryParams.DISPTOTIME_KEY));
            inparam.set(LstCustomerHistoryDASCHParams.USERID_KEY, requestParam.get(LstCustomerHistoryParams.USERID_KEY));
            inparam.set(LstCustomerHistoryDASCHParams.DSNUMBER_KEY, requestParam.get(LstCustomerHistoryParams.DSNUMBER_KEY));
            inparam.set(LstCustomerHistoryDASCHParams.PAGENAMERESOURCEKEY, requestParam.get(LstCustomerHistoryParams.PAGENAMERESOURCEKEY));

            // DFKLOOK ここから修正
            // 日時入力チェック
            if (!checkDate(inparam))
            {
                _pager.clear();
                return;
            }

            // 検索条件一覧のセット
            lst_SearchCondition_Make(inparam, locale);
            // DFKLOOK ここまで修正

            // get count.
            int count = dasch.count(inparam);
            _pager.clear();
            _pager.setMax(count);
            _lcm_lst_CustomerHistoryList.clear();

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
            _lcm_lst_CustomerHistoryList.clear();
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
        LstCustomerHistoryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstCustomerHistoryDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() - 1, _pager.getDataCountPerPage());
            _lcm_lst_CustomerHistoryList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_CustomerHistoryList.getNewLine();
                line.setValue(KEY_LST_NO, outparam.get(LstCustomerHistoryDASCHParams.NO));
                line.setValue(KEY_LST_LOGDATE_DAY, outparam.get(LstCustomerHistoryDASCHParams.LOGDATE_DAY));
                line.setValue(KEY_LST_LOGDATE_TIME, outparam.get(LstCustomerHistoryDASCHParams.LOGDATE_TIME));
                line.setValue(KEY_LST_UPDATE_KIND, outparam.get(LstCustomerHistoryDASCHParams.UPDATE_KIND));
                line.setValue(KEY_LST_USER_ID, outparam.get(LstCustomerHistoryDASCHParams.USER_ID));
                line.setValue(KEY_LST_USER_NAME, outparam.get(LstCustomerHistoryDASCHParams.USER_NAME));
                line.setValue(KEY_LST_IP_ADDRESS, outparam.get(LstCustomerHistoryDASCHParams.IP_ADDRESS));
                line.setValue(KEY_LST_TERMINAL_NAME, outparam.get(LstCustomerHistoryDASCHParams.TERMINAL_NAME));
                line.setValue(KEY_LST_CUSTOMER_CODE, outparam.get(LstCustomerHistoryDASCHParams.CUSTOMER_CODE));
                line.setValue(KEY_LST_CUSTOMER_NAME, outparam.get(LstCustomerHistoryDASCHParams.CUSTOMER_NAME));
                line.setValue(KEY_LST_UPDATE_CUSTOMER_NAME, outparam.get(LstCustomerHistoryDASCHParams.UPDATE_CUSTOMER_NAME));
                line.setValue(KEY_LST_ROUTE, outparam.get(LstCustomerHistoryDASCHParams.ROUTE));
                line.setValue(KEY_LST_UPDATE_ROUTE, outparam.get(LstCustomerHistoryDASCHParams.UPDATE_ROUTE));
                line.setValue(KEY_LST_POSTAL_CODE, outparam.get(LstCustomerHistoryDASCHParams.POSTAL_CODE));
                line.setValue(KEY_LST_UPDATE_POSTAL_CODE, outparam.get(LstCustomerHistoryDASCHParams.UPDATE_POSTAL_CODE));
                line.setValue(KEY_LST_PREFECTURE, outparam.get(LstCustomerHistoryDASCHParams.PREFECTURE));
                line.setValue(KEY_LST_UPDATE_PREFECTURE, outparam.get(LstCustomerHistoryDASCHParams.UPDATE_PREFECTURE));
                line.setValue(KEY_LST_ADDRESS1, outparam.get(LstCustomerHistoryDASCHParams.ADDRESS1));
                line.setValue(KEY_LST_UPDATE_ADDRESS1, outparam.get(LstCustomerHistoryDASCHParams.UPDATE_ADDRESS1));
                line.setValue(KEY_LST_ADDRESS2, outparam.get(LstCustomerHistoryDASCHParams.ADDRESS2));
                line.setValue(KEY_LST_UPDATE_ADDRESS2, outparam.get(LstCustomerHistoryDASCHParams.UPDATE_ADDRESS2));
                line.setValue(KEY_LST_TELEPHONE, outparam.get(LstCustomerHistoryDASCHParams.TELEPHONE));
                line.setValue(KEY_LST_UPDATE_TELEPHONE, outparam.get(LstCustomerHistoryDASCHParams.UPDATE_TELEPHONE));
                line.setValue(KEY_LST_CONTACT1, outparam.get(LstCustomerHistoryDASCHParams.CONTACT1));
                line.setValue(KEY_LST_UPDATE_CONTACT1, outparam.get(LstCustomerHistoryDASCHParams.UPDATE_CONTACT1));
                line.setValue(KEY_LST_CONTACT2, outparam.get(LstCustomerHistoryDASCHParams.CONTACT2));
                line.setValue(KEY_LST_UPDATE_CONTACT2, outparam.get(LstCustomerHistoryDASCHParams.UPDATE_CONTACT2));
                line.setValue(KEY_LST_SORTING_PLACE, outparam.get(LstCustomerHistoryDASCHParams.SORTING_PLACE));
                line.setValue(KEY_LST_UPDATE_SORTING_PLACE, outparam.get(LstCustomerHistoryDASCHParams.UPDATE_SORTING_PLACE));
                lst_CustomerHistoryList_SetLineToolTip(line);
                _lcm_lst_CustomerHistoryList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_CustomerHistoryList.clear();
            disposeDasch();
        }
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
