// $Id: LstAreaHistoryBusiness.java 7747 2010-03-29 08:08:07Z kishimoto $
package jp.co.daifuku.wms.part11.listbox.areahistorylist;

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
import jp.co.daifuku.wms.part11.dasch.LstAreaHistoryDASCH;
import jp.co.daifuku.wms.part11.dasch.LstAreaHistoryDASCHParams;

/**
 * エリアマスタ更新履歴詳細一覧の画面処理を行います。
 *
 * @version $Revision: 7747 $, $Date: 2010-03-29 17:08:07 +0900 (月, 29 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class LstAreaHistoryBusiness
        extends LstAreaHistory
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

    /** lst_AreaHistoryList(LST_NO) */
    private static final ListCellKey KEY_LST_NO = new ListCellKey("LST_NO", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_LOGDATE_DAY) */
    private static final ListCellKey KEY_LST_LOGDATE_DAY = new ListCellKey("LST_LOGDATE_DAY", new DateCellColumn(DATE_FORMAT.LONG, null), true, false);

    /** lst_AreaHistoryList(LST_LOGDATE_TIME) */
    private static final ListCellKey KEY_LST_LOGDATE_TIME = new ListCellKey("LST_LOGDATE_TIME", new DateCellColumn(null, TIME_FORMAT.HMS), true, false);

    /** lst_AreaHistoryList(LST_UPDATE_KIND) */
    private static final ListCellKey KEY_LST_UPDATE_KIND = new ListCellKey("LST_UPDATE_KIND", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_USER_ID) */
    private static final ListCellKey KEY_LST_USER_ID = new ListCellKey("LST_USER_ID", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_USER_NAME) */
    private static final ListCellKey KEY_LST_USER_NAME = new ListCellKey("LST_USER_NAME", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_IP_ADDRESS) */
    private static final ListCellKey KEY_LST_IP_ADDRESS = new ListCellKey("LST_IP_ADDRESS", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_TERMINAL_NAME) */
    private static final ListCellKey KEY_LST_TERMINAL_NAME = new ListCellKey("LST_TERMINAL_NAME", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_AREA_NO) */
    private static final ListCellKey KEY_LST_AREA_NO = new ListCellKey("LST_AREA_NO", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_AREA_NAME) */
    private static final ListCellKey KEY_LST_AREA_NAME = new ListCellKey("LST_AREA_NAME", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_UPDATE_AREA_NAME) */
    private static final ListCellKey KEY_LST_UPDATE_AREA_NAME = new ListCellKey("LST_UPDATE_AREA_NAME", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_TEMPORARY_AREA_TYPE) */
    private static final ListCellKey KEY_LST_TEMPORARY_AREA_TYPE = new ListCellKey("LST_TEMPORARY_AREA_TYPE", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_UPDATE_TEMP_AREA_TYPE) */
    private static final ListCellKey KEY_LST_UPDATE_TEMP_AREA_TYPE = new ListCellKey("LST_UPDATE_TEMP_AREA_TYPE", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_TEMPORARY_AREA) */
    private static final ListCellKey KEY_LST_TEMPORARY_AREA = new ListCellKey("LST_TEMPORARY_AREA", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_UPDATE_TEMP_AREA) */
    private static final ListCellKey KEY_LST_UPDATE_TEMP_AREA = new ListCellKey("LST_UPDATE_TEMP_AREA", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_VACANT_SEARCH_TYPE) */
    private static final ListCellKey KEY_LST_VACANT_SEARCH_TYPE = new ListCellKey("LST_VACANT_SEARCH_TYPE", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_UPDATE_VACANT_SEARCH_TYPE) */
    private static final ListCellKey KEY_LST_UPDATE_VACANT_SEARCH_TYPE = new ListCellKey("LST_UPDATE_VACANT_SEARCH_TYPE", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_RECEIVING_AREA) */
    private static final ListCellKey KEY_LST_RECEIVING_AREA = new ListCellKey("LST_RECEIVING_AREA", new StringCellColumn(), true, false);

    /** lst_AreaHistoryList(LST_UPDATE_RECEIVING_AREA) */
    private static final ListCellKey KEY_LST_UPDATE_RECEIVING_AREA = new ListCellKey("LST_UPDATE_RECEIVING_AREA", new StringCellColumn(), true, false);

    /** lst_SearchCondition keys */
    private static final ListCellKey[] LST_SEARCHCONDITION_KEYS = {
        KEY_LST_COLUMN_1,
        KEY_LST_COLUMN_2,
        KEY_LST_COLUMN_3,
        KEY_LST_COLUMN_4,
    };

    /** lst_AreaHistoryList keys */
    private static final ListCellKey[] LST_AREAHISTORYLIST_KEYS = {
        KEY_LST_NO,
        KEY_LST_LOGDATE_DAY,
        KEY_LST_UPDATE_KIND,
        KEY_LST_USER_ID,
        KEY_LST_IP_ADDRESS,
        KEY_LST_AREA_NO,
        KEY_LST_AREA_NAME,
        KEY_LST_TEMPORARY_AREA_TYPE,
        KEY_LST_TEMPORARY_AREA,
        KEY_LST_VACANT_SEARCH_TYPE,
        KEY_LST_RECEIVING_AREA,
        KEY_LST_LOGDATE_TIME,
        KEY_LST_USER_NAME,
        KEY_LST_TERMINAL_NAME,
        KEY_LST_UPDATE_AREA_NAME,
        KEY_LST_UPDATE_TEMP_AREA_TYPE,
        KEY_LST_UPDATE_TEMP_AREA,
        KEY_LST_UPDATE_VACANT_SEARCH_TYPE,
        KEY_LST_UPDATE_RECEIVING_AREA,
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

    /** ListCellModel lst_AreaHistoryList */
    private ListCellModel _lcm_lst_AreaHistoryList;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Default constructor
     */
    public LstAreaHistoryBusiness()
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
    public void lst_AreaHistoryList_Click(ActionEvent e)
            throws Exception
    {
    }

    /**
     *
     * @param e ActionEvent
     * @throws Exception
     */
    public void lst_AreaHistoryList_ColumClick(ActionEvent e)
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
    protected boolean checkDate(Params p) throws ScheduleException
    {
        WmsChecker chk = new WmsChecker();
        
        if (!chk.checkDate(WmsFormatter.toDate(p.getString(LstAreaHistoryDASCHParams.DISPFROMDAY_KEY)),
                WmsFormatter.toDate(p.getString(LstAreaHistoryDASCHParams.DISPFROMTIME_KEY))))
        {
            message.setMsgResourceKey(chk.getMessage());
            return false;
        }

        if (!chk.checkDate(WmsFormatter.toDate(p.getString(LstAreaHistoryDASCHParams.DISPTODAY_KEY)),
                WmsFormatter.toDate(p.getString(LstAreaHistoryDASCHParams.DISPTOTIME_KEY))))
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
    protected boolean lst_SearchCondition_Make(LstAreaHistoryDASCHParams param, Locale locale) throws ScheduleException
    {
        // 検索日時の補完処理
        Date[] tmp =
                WmsFormatter.getFromTo(WmsFormatter.toDate(param.getString(LstAreaHistoryDASCHParams.DISPFROMDAY_KEY)),
                        WmsFormatter.toTime(param.getString(LstAreaHistoryDASCHParams.DISPFROMTIME_KEY)),
                        WmsFormatter.toDate(param.getString(LstAreaHistoryDASCHParams.DISPTODAY_KEY)),
                        WmsFormatter.toTime(param.getString(LstAreaHistoryDASCHParams.DISPTOTIME_KEY)));
        Date from = tmp[0];
        Date to = tmp[1];
        
        int line = 1;

        // 1行目
        lst_SearchCondition.addRow();
        lst_SearchCondition.setCurrentRow(line);

        // 開始検索日時
        lst_SearchCondition.setValue(1, DisplayText.getText("LBL-W0397"));
        lst_SearchCondition.setValue(2, WmsFormatter.toDispDate(from, locale) + " " + WmsFormatter.toDispTime(from, locale));

        // 終了検索日時
        lst_SearchCondition.setValue(3, DisplayText.getText("LBL-W0398"));
        lst_SearchCondition.setValue(4,WmsFormatter.toDispDate(to, locale) + " " + WmsFormatter.toDispTime(to, locale));

        line++;

        // 2行目
        lst_SearchCondition.addRow();
        lst_SearchCondition.setCurrentRow(line);

        // ユーザID
        lst_SearchCondition.setValue(1, DisplayText.getText("LBL-T0031"));
        lst_SearchCondition.setValue(2, param.getString(LstAreaHistoryDASCHParams.USERID_KEY));

        line++;

        // 3行目
        lst_SearchCondition.addRow();
        lst_SearchCondition.setCurrentRow(line);

        // DS番号
        lst_SearchCondition.setValue(1, DisplayText.getText("LBL-W8003"));
        lst_SearchCondition.setValue(2, param.getString(LstAreaHistoryDASCHParams.DSNUMBER_KEY));

        // 画面名称
        lst_SearchCondition.setValue(3, DisplayText.getText("LBL-W8007"));
        if (!StringUtil.isBlank(param.getString(LstAreaHistoryDASCHParams.PAGENAMERESOURCEKEY)))
        {
            lst_SearchCondition.setValue(4, DisplayText.getText(param.getString(LstAreaHistoryDASCHParams.PAGENAMERESOURCEKEY)));
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

        // initialize lst_AreaHistoryList.
        _lcm_lst_AreaHistoryList = new ListCellModel(lst_AreaHistoryList, LST_AREAHISTORYLIST_KEYS, locale);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_NO, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_LOGDATE_DAY, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_LOGDATE_TIME, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_UPDATE_KIND, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_USER_ID, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_USER_NAME, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_IP_ADDRESS, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_TERMINAL_NAME, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_AREA_NO, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_AREA_NAME, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_UPDATE_AREA_NAME, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_TEMPORARY_AREA_TYPE, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_UPDATE_TEMP_AREA_TYPE, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_TEMPORARY_AREA, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_UPDATE_TEMP_AREA, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_VACANT_SEARCH_TYPE, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_UPDATE_VACANT_SEARCH_TYPE, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_RECEIVING_AREA, true);
        _lcm_lst_AreaHistoryList.setToolTipVisible(KEY_LST_UPDATE_RECEIVING_AREA, true);

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
    private void lst_AreaHistoryList_SetLineToolTip(ListCellLine line)
            throws Exception
    {
        // set ToolTip content.
        line.setToolTip(null, null);
        line.addToolTip("LBL-W0033", KEY_LST_USER_NAME);
        line.addToolTip("LBL-W8006", KEY_LST_TERMINAL_NAME);
        line.addToolTip("LBL-W8048", KEY_LST_AREA_NAME);
        line.addToolTip("LBL-W8049", KEY_LST_UPDATE_AREA_NAME);
        line.addToolTip("LBL-W8033", KEY_LST_TEMPORARY_AREA_TYPE);
        line.addToolTip("LBL-W8034", KEY_LST_UPDATE_TEMP_AREA_TYPE);
        line.addToolTip("LBL-W8035", KEY_LST_TEMPORARY_AREA);
        line.addToolTip("LBL-W8036", KEY_LST_UPDATE_TEMP_AREA);
        line.addToolTip("LBL-W8037", KEY_LST_VACANT_SEARCH_TYPE);
        line.addToolTip("LBL-W8038", KEY_LST_UPDATE_VACANT_SEARCH_TYPE);
        line.addToolTip("LBL-W8050", KEY_LST_RECEIVING_AREA);
        line.addToolTip("LBL-W8051", KEY_LST_UPDATE_RECEIVING_AREA);
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
        LstAreaHistoryDASCH dasch = null;
        boolean isSuccess = false;
        try
        {
            // dispose DASCH.
            disposeDasch();

            // save a pager event source.
            viewState.setString(_KEY_PAGERSOURCE, "page_Load");

            // open connection.
            conn = ConnectionManager.getSessionConnection(this);
            dasch = new LstAreaHistoryDASCH(conn, this.getClass(), locale, ui);
            dasch.setForwardOnly(false);

            // set input parameters.
            LstAreaHistoryDASCHParams inparam = new LstAreaHistoryDASCHParams();
            LstAreaHistoryParams requestParam = new LstAreaHistoryParams(request);
            inparam.set(LstAreaHistoryDASCHParams.TABLE_NAME, requestParam.get(LstAreaHistoryParams.TABLE_NAME));
            inparam.set(LstAreaHistoryDASCHParams.DISPFROMDAY_KEY, requestParam.get(LstAreaHistoryParams.DISPFROMDAY_KEY));
            inparam.set(LstAreaHistoryDASCHParams.DISPFROMTIME_KEY, requestParam.get(LstAreaHistoryParams.DISPFROMTIME_KEY));
            inparam.set(LstAreaHistoryDASCHParams.DISPTODAY_KEY, requestParam.get(LstAreaHistoryParams.DISPTODAY_KEY));
            inparam.set(LstAreaHistoryDASCHParams.DISPTOTIME_KEY, requestParam.get(LstAreaHistoryParams.DISPTOTIME_KEY));
            inparam.set(LstAreaHistoryDASCHParams.USERID_KEY, requestParam.get(LstAreaHistoryParams.USERID_KEY));
            inparam.set(LstAreaHistoryDASCHParams.DSNUMBER_KEY, requestParam.get(LstAreaHistoryParams.DSNUMBER_KEY));
            inparam.set(LstAreaHistoryDASCHParams.PAGENAMERESOURCEKEY, requestParam.get(LstAreaHistoryParams.PAGENAMERESOURCEKEY));

            // DFKLOOK ここから修正
            // 日時入力チェック
            if(!checkDate(inparam)) 
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
            _lcm_lst_AreaHistoryList.clear();

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
            _lcm_lst_AreaHistoryList.clear();
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
        LstAreaHistoryDASCH dasch = null;
        try
        {
            // get session.
            dasch = (LstAreaHistoryDASCH)session.getAttribute(_KEY_DASCH_LIST);

            // output display.
            List<Params> outparams = dasch.get(_pager.getIndex() -1, _pager.getDataCountPerPage());
            _lcm_lst_AreaHistoryList.clear();
            for (Params outparam : outparams)
            {
                ListCellLine line = _lcm_lst_AreaHistoryList.getNewLine();
                line.setValue(KEY_LST_LOGDATE_DAY, outparam.get(LstAreaHistoryDASCHParams.LOGDATE_DAY));
                line.setValue(KEY_LST_LOGDATE_TIME, outparam.get(LstAreaHistoryDASCHParams.LOGDATE_TIME));
                line.setValue(KEY_LST_UPDATE_KIND, outparam.get(LstAreaHistoryDASCHParams.UPDATE_KIND));
                line.setValue(KEY_LST_USER_ID, outparam.get(LstAreaHistoryDASCHParams.USER_ID));
                line.setValue(KEY_LST_USER_NAME, outparam.get(LstAreaHistoryDASCHParams.USER_NAME));
                line.setValue(KEY_LST_IP_ADDRESS, outparam.get(LstAreaHistoryDASCHParams.IP_ADRESS));
                line.setValue(KEY_LST_TERMINAL_NAME, outparam.get(LstAreaHistoryDASCHParams.TERMINAL_NAME));
                line.setValue(KEY_LST_AREA_NO, outparam.get(LstAreaHistoryDASCHParams.AREA_NO));
                line.setValue(KEY_LST_AREA_NAME, outparam.get(LstAreaHistoryDASCHParams.AREA_NAME));
                line.setValue(KEY_LST_UPDATE_AREA_NAME, outparam.get(LstAreaHistoryDASCHParams.UPDATE_AREA_NAME));
                line.setValue(KEY_LST_TEMPORARY_AREA_TYPE, outparam.get(LstAreaHistoryDASCHParams.TEMPORARY_AREA_TYPE));
                line.setValue(KEY_LST_UPDATE_TEMP_AREA_TYPE, outparam.get(LstAreaHistoryDASCHParams.UPDATE_TEMP_AREA_TYPE));
                line.setValue(KEY_LST_TEMPORARY_AREA, outparam.get(LstAreaHistoryDASCHParams.TEMPORARY_AREA));
                line.setValue(KEY_LST_UPDATE_TEMP_AREA, outparam.get(LstAreaHistoryDASCHParams.UPDATE_TEMP_AREA));
                line.setValue(KEY_LST_VACANT_SEARCH_TYPE, outparam.get(LstAreaHistoryDASCHParams.VACANT_SEARCH_TYPE));
                line.setValue(KEY_LST_UPDATE_VACANT_SEARCH_TYPE, outparam.get(LstAreaHistoryDASCHParams.UPDATE_VACANT_SEARCH_TYPE));
                line.setValue(KEY_LST_RECEIVING_AREA, outparam.get(LstAreaHistoryDASCHParams.RECEIVING_AREA));
                line.setValue(KEY_LST_UPDATE_RECEIVING_AREA, outparam.get(LstAreaHistoryDASCHParams.UPDATE_RECEIVING_AREA));
                line.setValue(KEY_LST_NO, outparam.get(LstAreaHistoryDASCHParams.NO));
                lst_AreaHistoryList_SetLineToolTip(line);
                _lcm_lst_AreaHistoryList.add(line);
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
            _pager.clear();
            _lcm_lst_AreaHistoryList.clear();
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
