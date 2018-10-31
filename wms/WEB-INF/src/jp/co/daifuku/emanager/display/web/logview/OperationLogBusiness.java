// $Id: OperationLogBusiness.java 7996 2011-07-06 00:52:24Z kitamaki $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.Constants;
import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.OperationLogHandler;
import jp.co.daifuku.emanager.database.handler.Part11LogHandler;
import jp.co.daifuku.emanager.display.web.logview.listbox.AccessLogListBusiness;
import jp.co.daifuku.emanager.display.web.logview.listbox.DsNoListBusiness;
import jp.co.daifuku.emanager.util.DispResourceMap;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.ui.web.BusinessClassHelper;
import jp.co.daifuku.ui.web.ToolTipHelper;
import jp.co.daifuku.util.CollectionUtils;

/** <jp>
 * ツールが生成した画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </jp> */
/** <en>
 * This screen class is created by the screen generator.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </en> */
public class OperationLogBusiness
        extends OperationLog
        implements EmConstants
{
    /**
     * 開始日時を保持するキー
     */
    public final static String START_DATE = "START_DATE";

    /**
     * 終了日時を保持するキー
     */
    public final static String END_DATE = "ENDT_DATE";

    /**
     * ユーザIDを保持するキー
     */
    public final static String USER_ID = "USER_ID";

    /**
     * ユーザ名を保持するキー
     */
    public final static String USER_NAME = "USER_NAME";

    /**
     * DS番号を保持するキー
     */
    public final static String DS_NO = "DS_NO";

    /**
     * 画面タイトルを保持するキー
     */
    public final static String TITLE = "TITLE";

    /**
     * IPアドレスを保持するキー
     */
    public final static String IPADDRESS = "IPADDRESS";

    /**
     * 端末名称を保持するキー
     */
    public final static String TERMINAL_NAME = "TERMINAL_NAME";

    /**
     * 操作区分を保持するキー
     */
    public final static String OPERATION_TYPE = "OPERATION_TYPE";

    /**
     * 操作ログの各項目を保持するキー
     */
    public final static String ITEM_DATA = "ITEM_DATA";

    /**
     * テーブル名を保持するキー
     */
    private final static String TABLE_NAME = "TABLE_NAME";

    /**
     * 終了インデックス
     */
    private final static String END_INDEX = "END_INDEX";

    /**
     * 開始インデックス
     */
    private final static String START_INDEX = "START_INDEX";

    /**
     * テーブル名(操作ログ)
     */
    private final static String TABLE_COM_OPERATIONLOG = "COM_OPERATIONLOG";

    /**
     * テーブル名(取込用操作ログ)
     */
    private final static String TABLE_COM_OPERATIONLOG_IMP = "COM_OPERATIONLOG_IMP";

    /** <jp>
     * 画面の初期化を行います。
     * @param e ActionEvent
     </jp> */
    /** <en>
     * This screen is initialized.
     * @param e ActionEvent
     </en> */
    public void page_Load(ActionEvent e)
            throws Exception
    {
        String menuparam = this.getHttpRequest().getParameter(EmConstants.MENUPARAM);

        if (menuparam != null)
        {
            //<jp>パラメータを取得</jp><en> A parameter is acquired. </en>
            String title = CollectionUtils.getMenuParam(0, menuparam);
            String functionID = CollectionUtils.getMenuParam(1, menuparam);
            String menuID = CollectionUtils.getMenuParam(2, menuparam);

            //<jp>ViewStateへ保持する</jp><en> It holds to ViewState. </en>
            this.getViewState().setString(EmConstants.M_MENUID_KEY, menuID);
            this.getViewState().setString(EmConstants.M_TITLE_KEY, title);

            //<jp>画面名称をセットする</jp><en> A screen name is set. </en>
            lbl_SettingName.setResourceKey(title);

            //<jp>ヘルプファイルへのパスをセットする</jp><en> The path to a help file is set. </en>
            btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()));
        }
        else if (!Validator.isEmptyCheck(this.getViewState().getString(EmConstants.M_TITLE_KEY)))
        {
            //<jp>画面名称をセットする</jp><en> A screen name is set. </en>
            lbl_SettingName.setResourceKey(this.getViewState().getString(EmConstants.M_TITLE_KEY));
        }

        // DB検索かCSV検索かのフラグ
        String part11Log =
                (String)httpRequest.getAttribute(PART11LOG_VIEW_KEY) != null ? (String)httpRequest.getAttribute(PART11LOG_VIEW_KEY)
                                                                            : PART11LOG_VIEW_DB;
        this.getViewState().setString(EmConstants.PART11LOG_VIEW_KEY, part11Log);

        // 日付形式の設定
        lbl_Day.setText(DisplayText.getText("LBL-W1365"));

        // 入力項目のクリア
        this.clearInputArea();

        // リストセルのクリア
        this.clearListCellArea();

        // 初期データ設定
        this.setInitValues(PART11LOG_VIEW_DB.equals(part11Log));

        this.setFocus(txt_RetrievalBeginning);
        
    }


    /**
     * リストセルエリアのクリア処理を行います。
     */
    private void clearListCellArea()
    {
        txt_RecordCount.setText("");

        btn_NextPage.setEnabled(false);
        btn_BackPage.setEnabled(false);

        this.lst_ScreenControlLog.clearRow();
    }


    /**
     * 入力項目のクリア処理を行います。
     */
    private void clearInputArea()
    {
        // 開始検索日時クリア
        txt_RetrievalBeginning.setText("");
        // 開始検索時刻クリア
        txt_TimeRetrievalBeginning.setText("");
        // 終了検索日時クリア
        txt_RetrievalEnd.setText("");
        // 終了検索時刻クリア
        txt_TimeRetrievalEnd.setText("");
        // 終了検索日時クリア
        txt_RetrievalEnd.setReadOnly(false);
        // 終了検索時刻txt_RetrievalEnd
        txt_TimeRetrievalEnd.setReadOnly(false);

        txt_UserId.setText("");
        txt_DSNo.setText("");
        txt_R_PageName.setText("");
        txt_R_PageName.setReadOnly(true);
    }


    /** 
     * 各コントロールイベント呼び出し前に呼び出されます。
     * @param e ActionEvent
     * @exception Exception
     */
    public void page_Initialize(ActionEvent e)
            throws Exception
    {
        this.setFocus(txt_RetrievalBeginning);
        this.disableTabs();
        
// 2008/12/25 K.Matsuda Start eAWCがPart11未対応のため、v2.1では在庫履歴は表示しない
        if(!EmConstants.PRODUCT_NAME_WMS.equals(EmProperties.getProperty(EmConstants.EMPARAMKEY_PRODUCT_NAME)))
        {
            // プロダクトがeWareNavi以外の場合は、在庫履歴のタブは非表示
            addOnloadScript("document.all('" + tab_InventoryLog.getId() + "').style.display=\"none\";");
        }
// 2008/12/25 K.Matsuda End
    }

    /**
     * set properties required to dispay initial screen 
     * 
     * @param isDBSearch DB検索かどうか
     * @throws SQLException DBエラーが発生
     */
    private void setInitValues(boolean isDBSearch)
            throws SQLException
    {
        // DB検索の場合
        if (isDBSearch)
        {
            Calendar cal = Calendar.getInstance();

            // 開始検索日時
            txt_RetrievalBeginning.setDate(cal.getTime());
            // 終了検索日時
            txt_RetrievalEnd.setDate(cal.getTime());
        }
        // CSV検索の場合
        else
        {
            Connection conn = null;
            try
            {
                conn = EmConnectionHandler.getPageDbConnection(this);
                Part11LogHandler p11Hdlr = EmHandlerFactory.getPart11LogHandler(conn);
                // 取込テーブル内の最小の日付を取得する
                Date minDate = p11Hdlr.findMinLogDate("COM_OPERATIONLOG_IMP");
                if (minDate != null)
                {
                    // 開始検索日時
                    txt_RetrievalBeginning.setDate(minDate);
                    // 終了検索日時
                    txt_RetrievalEnd.setDate(minDate);
                }
            }
            finally
            {
                EmConnectionHandler.closeConnection(conn);
            }
        }
    }

    /**
     * 当画面以外のTABを非選択状態にします。
     *
     */
    private void disableTabs()
    {
        // disable all tabs except OperationLog tab
        tab_AccessLog.setSelectedIndex(0);
        tab_MasterLog.setSelectedIndex(0);
        tab_InventoryLog.setSelectedIndex(0);
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_ToMenu_Click(ActionEvent e)
            throws Exception
    {
        forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_SearchPopup_Click(ActionEvent e)
            throws Exception
    {
        // DS number search parameter
        ForwardParameters param = new ForwardParameters();
        param.setParameter(DsNoListBusiness.DS_NO, txt_DSNo.getText());
        param.setParameter(AccessLogListBusiness.USER_ID, txt_UserId.getText());

        // if date is not null, do cast
        Date startDate = txt_RetrievalBeginning.getDate();
        Date startTime = txt_TimeRetrievalBeginning.getTime();
        Date endDate = txt_RetrievalEnd.getDate();
        Date endTime = txt_TimeRetrievalEnd.getTime();

        // if the view is not from Import data , set db data
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);

        // modify user entered dates to search dates based on conditions
        List searchDates = EManagerUtil.getSearchDates(startDate, startTime, endDate, endTime);

        //set Screen title and Table to access the data based on input condition
        if (part11LogKey != null && EmConstants.PART11LOG_VIEW_IMP.equals(part11LogKey))
        {
            param.setParameter(DsNoListBusiness.TABLE_NAME, TABLE_COM_OPERATIONLOG_IMP);
        }
        else if (part11LogKey != null && EmConstants.PART11LOG_VIEW_DB.equals(part11LogKey))
        {
            param.setParameter(DsNoListBusiness.TABLE_NAME, TABLE_COM_OPERATIONLOG);
        }

        this.session.setAttribute(DsNoListBusiness.START_DATE, searchDates.get(0));
        this.session.setAttribute(DsNoListBusiness.END_DATE, searchDates.get(1));

        redirect("/emanager/logview/listbox/DsNoList.do", param, "/Progress.do");
    }

    /**
     * ポップアップウインドから、戻ってくるときにこのメソッドが
     * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
     * @param e ActionEvent
     * @throws Exception
     */
    public void page_DlgBack(ActionEvent e)
            throws Exception
    {
        DialogParameters param = ((DialogEvent)e).getDialogParameters();
        // get DS number
        String dsNo = param.getParameter(DsNoListBusiness.DS_NO);
        if (!Validator.isEmptyCheck(dsNo))
        {
            txt_DSNo.setText(dsNo);
            setFocus(txt_DSNo);
        }
        //get Page resource Key
        String pageReousrceKey = param.getParameter(DsNoListBusiness.PAGE_NAME_KEY);
        if (!Validator.isEmptyCheck(pageReousrceKey))
        {
            // set page name
            txt_R_PageName.setText(pageReousrceKey);
        }
        if (Validator.isEmptyCheck(txt_DSNo.getText()))
        {
            // set page name
            txt_R_PageName.setText("");
        }
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_View_Click(ActionEvent e)
            throws Exception
    {
        Date startDate = txt_RetrievalBeginning.getDate();
        Date startTime = txt_TimeRetrievalBeginning.getTime();
        Date endDate = txt_RetrievalEnd.getDate();
        Date endTime = txt_TimeRetrievalEnd.getTime();
        //modify user entered dates to search dates based on conditions
        List searchDates = EManagerUtil.getSearchDates(startDate, startTime, endDate, endTime);

        // if search dates are not null, check if end date is before start date
        Date start = (Date)searchDates.get(0);
        Date end = (Date)searchDates.get(1);
        if (start != null && end != null && end.before(start))
        {
            message.setMsgResourceKey("6403083");
            return;
        }

        // if the view is not from Import data , set db data
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);
        String searchTable = null;
        // set Screen title and Table to access the data based on input condition
        if (part11LogKey != null && EmConstants.PART11LOG_VIEW_IMP.equals(part11LogKey))
        {
            searchTable = TABLE_COM_OPERATIONLOG_IMP;
        }
        else if (part11LogKey != null && EmConstants.PART11LOG_VIEW_DB.equals(part11LogKey))
        {
            searchTable = TABLE_COM_OPERATIONLOG;
        }
        //convert the dates to String format  based on locale 
        String dateFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), Constants.F_DATE_TIME);
        String startDateStr =
                searchDates.get(0) != null ? EManagerUtil.getDateFormat((Date)searchDates.get(0), dateFormat)
                                          : null;
        String endDateStr =
                searchDates.get(1) != null ? EManagerUtil.getDateFormat((Date)searchDates.get(1), dateFormat)
                                          : null;

        //get date in million seconds for DB search
        dateFormat = dateFormat + ".SSS";
        startDateStr = searchDates.get(0) != null ? EManagerUtil.getDateFormat((Date)searchDates.get(0), dateFormat)
                                                 : null;
        endDateStr = searchDates.get(1) != null ? EManagerUtil.getDateFormat((Date)searchDates.get(1), dateFormat)
                                               : null;

        // if user id is "", set User id as null
        String userId = "".equals(txt_UserId.getText()) ? null
                                                       : txt_UserId.getText();
        // if ds number is is "", set User id as null
        String dsNo = "".equals(txt_DSNo.getText()) ? null
                                                   : txt_DSNo.getText();

        // set values to View state
        this.getViewState().setString(START_DATE, startDateStr);
        this.getViewState().setString(END_DATE, endDateStr);
        this.getViewState().setString(USER_ID, userId);
        this.getViewState().setString(DS_NO, dsNo);
        this.getViewState().setString(TABLE_NAME, searchTable);

        this.lst_ScreenControlLog.clearRow();
        this.txt_RecordCount.setText("");

        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            OperationLogHandler handler = EmHandlerFactory.getOperationLogHandler(conn);
            // update screenName
            String screenName = this.getScreenName(searchTable, dsNo, handler);
            this.txt_R_PageName.setText(screenName);

            String oracleDateFormat =
                    EManagerUtil.getOracleDateFormat(this.httpRequest.getLocale(), Constants.F_DATE_TIME);
            int total =
                    handler.findCountOperationLog(searchTable, startDateStr, endDateStr, userId, dsNo, oracleDateFormat);

            if (total <= 0)
            {
                btn_NextPage.setEnabled(false);
                btn_BackPage.setEnabled(false);
                message.setMsgResourceKey("6403077");
                return;
            }
            else
            {
                txt_RecordCount.setInt(total);
            }
            int endIndex = Integer.parseInt(EmProperties.getProperty("ListboxSearchCount"));
            this.getViewState().setString(END_INDEX, String.valueOf(endIndex));
            int startIndex = 0;
            this.setList(handler, startIndex, endIndex);
            // if total records are more than listcell max display, enable next button
            if (total > endIndex)
            {
                btn_NextPage.setEnabled(true);
                btn_BackPage.setEnabled(false);
            }
            else
            {
                btn_NextPage.setEnabled(false);
                btn_BackPage.setEnabled(false);
            }

        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * リストセルに値をセットします	
     * @param startIndex int
     * @param endIndex int 
     * @throws Exception 
     */
    private void setList(OperationLogHandler logHandler, int startIndex, int endIndex)
            throws Exception
    {
        String startDate = this.getViewState().getString(START_DATE);
        String endDate = this.getViewState().getString(END_DATE);
        String dsNo = this.getViewState().getString(DS_NO);
        String userId = this.getViewState().getString(USER_ID);
        String searchTableName = this.getViewState().getString(TABLE_NAME);

        String oracleDateFormat = EManagerUtil.getOracleDateFormat(this.httpRequest.getLocale(), Constants.F_DATE_TIME);

        startIndex++;
        jp.co.daifuku.emanager.database.entity.OperationLog operationLog[] =
                logHandler.findOperationLog(searchTableName, startDate, endDate, userId, dsNo, startIndex, endIndex,
                        oracleDateFormat);
        //ロールを取得できたか確認
        if (operationLog == null)
        {
            message.setMsgResourceKey("6403077");
            return;
        }
        this.lst_ScreenControlLog.clearRow();
        //get date display format based on the country
        String dateFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), Constants.F_DATE);
        //String timeFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), Constants.F_TIME);
        String timeFormat = EmConstants.MILLISECOND_FORMAT;

        int no = startIndex;
        for (int i = 0; i < operationLog.length; i++)
        {
            if (operationLog[i] == null)
            {
                continue;
            }
            //行追加
            this.lst_ScreenControlLog.addRow();
            lst_ScreenControlLog.setCurrentRow(lst_ScreenControlLog.getMaxRows() - 1);

            // get ItemList
            List list = operationLog[i].getItemList();
            lst_ScreenControlLog.setValue(0, CollectionUtils.getConnectedString(list));

            // set data to ScrollListcell
            lst_ScreenControlLog.setValue(2, Integer.toString(no++));
            String date = EManagerUtil.getDateFormat(operationLog[i].getLogDate(), dateFormat);
            lst_ScreenControlLog.setValue(3, date);

            lst_ScreenControlLog.setValue(4, this.getOperationLogType(operationLog[i].getAccessType()));
            lst_ScreenControlLog.setValue(5, operationLog[i].getUserId());
            lst_ScreenControlLog.setValue(6, operationLog[i].getIpAddress());
            lst_ScreenControlLog.setValue(7, operationLog[i].getDsNumber());

            String time = EManagerUtil.getDateFormat(operationLog[i].getLogDate(), timeFormat);
            lst_ScreenControlLog.setValue(8, time);
            lst_ScreenControlLog.setValue(9, operationLog[i].getUserName());
            lst_ScreenControlLog.setValue(10, operationLog[i].getTerminalName());
            String screenName = DispResourceMap.getText(operationLog[i].getPageName());
            lst_ScreenControlLog.setValue(11, screenName);

            // ToolTip設定
            ToolTipHelper toolTip = new ToolTipHelper();
            // user Name
            toolTip.add(DispResourceMap.getText("LBL-T0140"), operationLog[i].getUserName());
            // TerminalName
            toolTip.add(DispResourceMap.getText("LBL-T0141"), operationLog[i].getTerminalName());
            // Screen name
            toolTip.add(DispResourceMap.getText("LBL-T0142"), screenName);
            //int ss = lst_ScreenAccessLog.getActiveRow();
            lst_ScreenControlLog.setToolTip(i + 1, toolTip.getText());
        }

        message.setMsgResourceKey("6401016");
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void lst_ScreenControlLog_Click(ActionEvent e)
            throws Exception
    {
        this.lst_ScreenControlLog.setCurrentRow(lst_ScreenControlLog.getActiveRow());

        ForwardParameters param = new ForwardParameters();
        String startTime = lst_ScreenControlLog.getValue(3) + " " + lst_ScreenControlLog.getValue(8);
        param.setParameter(START_DATE, startTime);
        param.setParameter(OPERATION_TYPE, lst_ScreenControlLog.getValue(4));
        param.setParameter(USER_ID, lst_ScreenControlLog.getValue(5));
        param.setParameter(USER_NAME, lst_ScreenControlLog.getValue(9));
        param.setParameter(DS_NO, lst_ScreenControlLog.getValue(7));
        param.setParameter(TITLE, lst_ScreenControlLog.getValue(11));
        param.setParameter(IPADDRESS, lst_ScreenControlLog.getValue(6));
        param.setParameter(TERMINAL_NAME, lst_ScreenControlLog.getValue(10));
        param.setParameter(ITEM_DATA, lst_ScreenControlLog.getValue(0));

        redirect("/emanager/logview/listbox/OperationLogList.do", param, "/Progress.do");
    }


    /**
     * 操作区分から表示名称を取得します。
     * 
     * @param operetionType 操作区分
     * @return 操作区分表示名称
     * @throws Exception
     */
    private String getOperationLogType(int operetionType)
            throws Exception
    {
        String result = "";

        switch (operetionType)
        {
            // 設定
            case EmConstants.OPELOG_CLASS_SETTING:
                result = DispResourceMap.getText("LBL-T0196");
                break;
            // 登録
            case EmConstants.OPELOG_CLASS_REGIST:
                result = DispResourceMap.getText("LBL-T0180");
                break;
            // 修正
            case EmConstants.OPELOG_CLASS_MODIFY:
                result = DispResourceMap.getText("LBL-T0194");
                break;
            // 削除
            case EmConstants.OPELOG_CLASS_DELETE:
                result = DispResourceMap.getText("LBL-T0195");
                break;
            // 全削除
            case EmConstants.OPELOG_CLASS_ALL_DELETE:
                result = DispResourceMap.getText("LBL-T0197");
                break;
            // キャンセル
            case EmConstants.OPELOG_CLASS_CANCEL:
                result = DispResourceMap.getText("LBL-T0198");
                break;
            // 印刷
            case EmConstants.OPELOG_CLASS_PRINT:
                result = DispResourceMap.getText("LBL-T0199");
                break;
            // XLS
            case EmConstants.OPELOG_CLASS_XLS:
                result = DispResourceMap.getText("LBL-T0200");
                break;
            // CSV
            case EmConstants.OPELOG_CLASS_CSV:
                result = DispResourceMap.getText("LBL-T0201");
                break;
            // プレビュー
            case EmConstants.OPELOG_CLASS_PREVIEW:
                result = DispResourceMap.getText("LBL-T0277");
                break;
            // 印刷(リストボックス)
            case EmConstants.OPELOG_CLASS_PRINT_LIST:
                result = DispResourceMap.getText("LBL-T0269");
                break;
            // XLS(リストボックス)
            case EmConstants.OPELOG_CLASS_XLS_LIST:
                result = DispResourceMap.getText("LBL-T0270");
                break;
            // CSV(リストボックス)
            case EmConstants.OPELOG_CLASS_CSV_LIST:
                result = DispResourceMap.getText("LBL-T0271");
                break;
            // プレビュー(リストボックス)
            case EmConstants.OPELOG_CLASS_PREVIEW_LIST:
                result = DispResourceMap.getText("LBL-T0202");
                break;
            // 切断
            case EmConstants.OPELOG_CLASS_DISCONNECT:
                result = DispResourceMap.getText("LBL-T0273");
                break;
            // リセット
            case EmConstants.OPELOG_CLASS_RESET:
                result = DispResourceMap.getText("LBL-T0274");
                break;
            // 自動取込
            case EmConstants.OPELOG_CLASS_AUTO_LOADING:
                result = DispResourceMap.getText("LBL-T0203");
                break;
            // 手動取込
            case EmConstants.OPELOG_CLASS_MANUAL_LOADING:
                result = DispResourceMap.getText("LBL-T0204");
                break;
            // 自動報告
            case EmConstants.OPELOG_CLASS_AUTO_REPORT:
                result = DispResourceMap.getText("LBL-T0205");
                break;
            // 手動報告
            case EmConstants.OPELOG_CLASS_MANUAL_REPORT:
                result = DispResourceMap.getText("LBL-T0206");
                break;
            // ログイン
            case EmConstants.OPELOG_CLASS_LOGIN:
                result = DispResourceMap.getText("LBL-T0207");
                break;
            // ログアウト
            case EmConstants.OPELOG_CLASS_LOGOUT:
                result = DispResourceMap.getText("LBL-T0208");
                break;
            // ロック解除
            case EmConstants.OPELOG_CLASS_LOCK_CANCELLATION:
                result = DispResourceMap.getText("LBL-T0209");
                break;
            // 再パスワード
            case EmConstants.OPELOG_CLASS_PASSWORD_REISSUE:
                result = DispResourceMap.getText("LBL-T0092");
                break;
            // 端末切替設定
            case EmConstants.OPELOG_CLASS_TERMINAL_SWITCH:
                result = DispResourceMap.getText("LBL-T0105");
                break;
            // 端末ユーザ設定
            case EmConstants.OPELOG_CLASS_TERMINAL_USER:
                result = DispResourceMap.getText("LBL-T0210");
                break;
            // 入力
            case EmConstants.OPELOG_CLASS_INPUT:
                result = DispResourceMap.getText("LBL-T0211");
                break;
            // 制御モジュール
            case EmConstants.OPELOG_CLASS_CONTROL_MODULE:
                result = DispResourceMap.getText("LBL-T0212");
                break;
        }

        return result;
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_NextPage_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //get last end Index from view state
            String endIndexStr = this.getViewState().getString(END_INDEX);
            //last end Index is next start index
            int startIndex = Integer.parseInt(endIndexStr);
            // maximum pages per listcell
            int maxCountPage = Integer.parseInt(EmProperties.getProperty("ListboxSearchCount"));
            //next end index is last end index + max list cell count
            int nextEndIndex = startIndex + maxCountPage;
            //int nextEndIndex=temp;
            // get total records
            int totalRecords = txt_RecordCount.getInt();
            // if next index is lesser than the total count.. next index will become total count
            if (totalRecords <= nextEndIndex)
            {
                nextEndIndex = totalRecords;
                // Since maximum records are reached.. disable next button 
                btn_NextPage.setEnabled(false);
            }
            //put end index and start index into view state
            this.getViewState().setString(END_INDEX, String.valueOf(nextEndIndex));
            this.getViewState().setString(START_INDEX, String.valueOf(startIndex));

            conn = EmConnectionHandler.getPageDbConnection(this);
            OperationLogHandler handler = EmHandlerFactory.getOperationLogHandler(conn);
            //set list to data
            setList(handler, startIndex, nextEndIndex);
            // enable previous button
            btn_BackPage.setEnabled(true);
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_BackPage_Click(ActionEvent e)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //get last start Index from view state
            String startIndexStr = this.getViewState().getString(START_INDEX);
            //last start index is end endex for this list 
            int endIndex = Integer.parseInt(startIndexStr);
            // maximum values per listcell
            int maxCountPage = Integer.parseInt(EmProperties.getProperty("ListboxSearchCount"));

            //next end index is last start index -  max list cell count
            int startIndex = endIndex - maxCountPage;

            //int startIndex =temp; 
            // if start index is zero or lesser .. end index will become max count
            if (startIndex <= 0)
            {
                endIndex = maxCountPage;
                // diable previous button
                btn_BackPage.setEnabled(false);
            }

            //put start and end index into view state
            this.getViewState().setString(END_INDEX, String.valueOf(endIndex));
            this.getViewState().setString(START_INDEX, String.valueOf(startIndex));

            conn = EmConnectionHandler.getPageDbConnection(this);
            OperationLogHandler handler = EmHandlerFactory.getOperationLogHandler(conn);
            //set list to data
            setList(handler, startIndex, endIndex);
            // enable next button 
            btn_NextPage.setEnabled(true);
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** <en>
     * 
     * This method returns the page name for a given dsNumber  
     *  
     </en> */
    private String getScreenName(String tableName, String dsNumber, OperationLogHandler handler)
            throws Exception
    {
        String resourceKey = handler.findPageNameByDSNum(tableName, dsNumber);
        return DispResourceMap.getText(resourceKey);
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void btn_Clear_Click(ActionEvent e)
            throws Exception
    {
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);

        // 入力項目のクリア
        this.clearInputArea();

        // 初期データ設定
        this.setInitValues(PART11LOG_VIEW_DB.equals(part11LogKey));

        this.setFocus(txt_RetrievalBeginning);
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void tab_AccessLog_Click(ActionEvent e)
            throws Exception
    {
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);
        httpRequest.setAttribute(EmConstants.PART11LOG_VIEW_KEY, part11LogKey);
        forward("/emanager/logview/AccessLog.do");
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void tab_MasterLog_Click(ActionEvent e)
            throws Exception
    {
        //if the view is not from Import data , set db data
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);
        httpRequest.setAttribute(EmConstants.PART11LOG_VIEW_KEY, part11LogKey);
        forward("/emanager/logview/MasterLog.do");
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void tab_InventoryLog_Click(ActionEvent e)
            throws Exception
    {
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);
        httpRequest.setAttribute(EmConstants.PART11LOG_VIEW_KEY, part11LogKey);
        forward("/emanager/logview/InventoryLog.do");
    }

    /** <jp>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </jp> */
    /** <en>
     * 
     * @param e ActionEvent 
     * @throws Exception 
     </en> */
    public void tab_OperationLog_Click(ActionEvent e)
            throws Exception
    {
        String part11LogKey = this.getViewState().getString(EmConstants.PART11LOG_VIEW_KEY);
        httpRequest.setAttribute(EmConstants.PART11LOG_VIEW_KEY, part11LogKey);
        this.page_Load(e);
    }
}
//end of class
