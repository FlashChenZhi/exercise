// $Id: AccessLogListBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview.listbox;

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.Constants;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.AccessLog;
import jp.co.daifuku.emanager.database.handler.AccessLogHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.util.DispResourceMap;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmProperties;
import jp.co.daifuku.ui.web.ToolTipHelper;

/** <jp>
 * ツールが生成した画面クラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
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
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 </en> */
public class AccessLogListBusiness
        extends AccessLogList
        implements EmConstants
{
    /**
     * 検索条件(開始日付)
     */
    public final static String START_DATE = "START_DATE";

    /**
     * 検索条件(終了日付)
     */
    public final static String END_DATE = "ENDT_DATE";

    /**
     * 検索条件(ユーザID)
     */
    public final static String USER_ID = "USER_ID";

    /**
     * 検索条件(DS番号)
     */
    public final static String DS_NO = "DS_NO";

    /**
     * 検索条件(テーブル名)
     */
    public final static String TABLE_NAME = "TABLE_NAME";

    /**
     * 画面タイトル
     */
    public final static String TITLE = "TITLE";


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
        // if user id is "", set User id as null
        String userId = "".equals(request.getParameter(USER_ID)) ? null
                                                                : request.getParameter(USER_ID);
        // if ds number is is "", set User id as null
        String dsNo = "".equals(request.getParameter(DS_NO)) ? null
                                                            : request.getParameter(DS_NO);
        // String screenName = request.getParameter(SCREEN_NAME);
        String pageTitle = httpRequest.getParameter(TITLE);

        this.lbl_ListName.setResourceKey("TLE-T0146");
        String searchTableName = httpRequest.getParameter(TABLE_NAME);

        if (searchTableName == null || "".equals(searchTableName)) //if Table name is null or empty .. Error 
        {
            message.setMsgResourceKey("6404001");
            return;
        }

        // if date is not null, do cast
        Date startDate = this.session.getAttribute(START_DATE) == null ? null
                                                                      : (Date)this.session.getAttribute(START_DATE);
        Date endDate = this.session.getAttribute(END_DATE) == null ? null
                                                                  : (Date)this.session.getAttribute(END_DATE);

        //convert the dates to String format  based on locale 
        String dateFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), Constants.F_DATE_TIME);
        String startDateStr = startDate != null ? EManagerUtil.getDateFormat(startDate, dateFormat)
                                               : null;
        String endDateStr = endDate != null ? EManagerUtil.getDateFormat(endDate, dateFormat)
                                           : null;
        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            AccessLogHandler handler = EmHandlerFactory.getAccessLogHandler(conn);

            String screenName = this.getScreenName(searchTableName, dsNo, handler);
            // display search conditions on list cell
            this.lst_SearchCondition_Display(startDateStr, endDateStr, userId, dsNo, screenName);

            // if search dates are not null, check if end date is before start date
            if (startDate != null && endDate != null && endDate.before(startDate))
            {
                message.setMsgResourceKey("6403083");
                setPagerValue(0, 0, 0);
                lst_ScreenAccessLog.setVisible(false);
                return;
            }

            //get date in million seconds for DB search
            dateFormat = dateFormat + ".SSS";
            startDateStr = startDate != null ? EManagerUtil.getDateFormat(startDate, dateFormat)
                                            : null;
            endDateStr = endDate != null ? EManagerUtil.getDateFormat(endDate, dateFormat)
                                        : null;

            // set values to ViewState for feature reference
            this.getViewState().setString(START_DATE, startDateStr);
            this.getViewState().setString(END_DATE, endDateStr);
            this.getViewState().setString(USER_ID, userId);
            this.getViewState().setString(DS_NO, dsNo);
            this.getViewState().setString(TITLE, pageTitle);
            this.getViewState().setString(TABLE_NAME, searchTableName);

            // 合計数
            int total = 0;

            String oracleDateFormat =
                    EManagerUtil.getOracleDateFormat(this.httpRequest.getLocale(), Constants.F_DATE_TIME);

            total =
                    handler.findCountAccessLog(searchTableName, startDateStr, endDateStr, userId, dsNo,
                            oracleDateFormat);
            //1ページの表示件数
            int page = Integer.parseInt(EmProperties.getProperty("ListboxSearchCount"));

            //          最初のページの表示終了位置
            int end = 0;
            //データがある場合
            if (total > 0)
            {
                if (total <= page)
                {
                    end = total;
                }
                else
                {
                    end = page;
                }

                //リストデータをセット
                setList(handler, 0, end);
                //pagerに値をセット
                setPagerValue(1, total, page);
            }
            else
            {
                //Pagerへの値セット
                pgr_PagerSync.setMax(0); //最大件数
                pgr_PagerSync.setPage(0); //1Page表示件数
                pgr_PagerSync.setIndex(0); //開始位置               
                //ヘッダーを隠します
                lst_ScreenAccessLog.setVisible(false);

                //対象データはありませんでした
                message.setMsgResourceKey("6403077");
            }
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * リストセルに値をセットします
     * @param roleHandler 
     * @param authHandler 
     * @param next_index int
     * @param next_end int 
     * @throws Exception 
     */
    private void setList(AccessLogHandler logHandler, int next_index, int next_end)
            throws Exception
    {
        next_index++;
        //表を削除
        lst_ScreenAccessLog.clearRow();
        String startDate = this.getViewState().getString(START_DATE);
        String endDate = this.getViewState().getString(END_DATE);
        String dsNo = this.getViewState().getString(DS_NO);
        String userId = this.getViewState().getString(USER_ID);
        String searchTableName = this.getViewState().getString(TABLE_NAME);

        //check part11 view type:: DB or CSV (import) else //if value is neither DB nor IMP .. Error 
        if (searchTableName == null || "".equals(searchTableName)) //if Table name is null or empty .. Error 
        {
            message.setMsgResourceKey("6404001");
            return;
        }

        String oracleDateFormat = EManagerUtil.getOracleDateFormat(this.httpRequest.getLocale(), Constants.F_DATE_TIME);
        AccessLog[] logInfo_Array =
                logHandler.findAccessLog(searchTableName, startDate, endDate, userId, dsNo, next_index, next_end,
                        oracleDateFormat);

        if (logInfo_Array == null)
        {
            setPagerValue(0, 0, 0);
            this.lst_ScreenAccessLog.setVisible(false);
            return;
        }

        //get date display format based on the country
        String dateFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), Constants.F_DATE);
        //String timeFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), Constants.F_TIME);
        String timeFormat = EmConstants.MILLISECOND_FORMAT;

        int no = next_index;
        //表にセット
        for (int i = 0; i < logInfo_Array.length; i++)
        {
            if (logInfo_Array[i] == null)
            {
                continue;
            }
            //行追加
            this.lst_ScreenAccessLog.addRow();

            lst_ScreenAccessLog.setCurrentRow(lst_ScreenAccessLog.getMaxRows() - 1);

            // set data to ScrollListcell
            lst_ScreenAccessLog.setValue(1, Integer.toString(no++));
            String date = EManagerUtil.getDateFormat(logInfo_Array[i].getLogDate(), dateFormat);
            lst_ScreenAccessLog.setValue(2, date);
            lst_ScreenAccessLog.setValue(3, this.getAccessLogType(logInfo_Array[i].getAccessType()));
            lst_ScreenAccessLog.setValue(4, logInfo_Array[i].getUserId());
            lst_ScreenAccessLog.setValue(5, logInfo_Array[i].getIpAddress());
            lst_ScreenAccessLog.setValue(6, logInfo_Array[i].getDsNumber());
            String time = EManagerUtil.getDateFormat(logInfo_Array[i].getLogDate(), timeFormat);
            lst_ScreenAccessLog.setValue(7, time);
            lst_ScreenAccessLog.setValue(8, logInfo_Array[i].getUserName());
            lst_ScreenAccessLog.setValue(9, logInfo_Array[i].getTerminalName());
            String screenName = DispResourceMap.getText(logInfo_Array[i].getPageName());
            lst_ScreenAccessLog.setValue(10, screenName);

            // ToolTip設定
            ToolTipHelper toolTip = new ToolTipHelper();
            // user Name
            toolTip.add(DispResourceMap.getText("LBL-T0140"), logInfo_Array[i].getUserName());
            // TerminalName
            toolTip.add(DispResourceMap.getText("LBL-T0141"), logInfo_Array[i].getTerminalName());
            // Screen name
            toolTip.add(DispResourceMap.getText("LBL-T0142"), screenName);

            lst_ScreenAccessLog.setToolTip(i + 1, toolTip.getText());
        }

        message.setMsgResourceKey("6401016");
    }

    /** 
     * リストセルに値をセットします
     * @param next_index int
     * @param next_end int 
     * @throws Exception 
     */
    private void setList(int next_index, int next_end)
            throws Exception
    {
        Connection conn = null;
        try
        {
            //コネクションを取得
            //conn = EmConnectionHandler.getPageDbConnection( this );
            conn = EmConnectionHandler.getPageDbConnection(this);
            AccessLogHandler handler = EmHandlerFactory.getAccessLogHandler(conn);
            setList(handler, next_index, next_end);
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /**
     * 検索条件のリストセルを作成します。
     * @param param 検索条件
     */
    private void lst_SearchCondition_Display(String startDate, String endDate, String userId, String dsNumebr,
            String screenName)
            throws Exception
    {

        lst_SearchConditionTwoColumn.addRow();
        lst_SearchConditionTwoColumn.setCurrentRow(1);
        // 開始入庫日
        lst_SearchConditionTwoColumn.setValue(1, DispResourceMap.getText("LBL-T0090"));


        lst_SearchConditionTwoColumn.setValue(2, startDate);

        // 終了入庫日
        lst_SearchConditionTwoColumn.setValue(3, DispResourceMap.getText("LBL-T0091"));
        lst_SearchConditionTwoColumn.setValue(4, endDate);

        // set user information
        lst_SearchConditionTwoColumn.addRow();
        lst_SearchConditionTwoColumn.setCurrentRow(2);

        lst_SearchConditionTwoColumn.setValue(1, DispResourceMap.getText("LBL-T0093"));
        lst_SearchConditionTwoColumn.setValue(2, userId);

        lst_SearchConditionTwoColumn.addRow();
        lst_SearchConditionTwoColumn.setCurrentRow(3);

        //DS Number and Screen Name
        lst_SearchConditionTwoColumn.setValue(1, DispResourceMap.getText("LBL-T0131"));
        lst_SearchConditionTwoColumn.setValue(2, dsNumebr);

        lst_SearchConditionTwoColumn.setValue(3, DispResourceMap.getText("LBL-T0142"));
        lst_SearchConditionTwoColumn.setValue(4, screenName);
    }

    /** 
     * ページャーに値をセットします
     * @param index int
     * @param total int
     * @param page int
     */
    private void setPagerValue(int index, int total, int page)
    {
        pgr_PagerSync.setIndex(index);
        pgr_PagerSync.setMax(total);
        pgr_PagerSync.setPage(page);
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
    public void pgr_PagerSync_Next(ActionEvent e)
            throws Exception
    {
        int total = pgr_PagerSync.getMax();
        int page = pgr_PagerSync.getPage();
        int index = pgr_PagerSync.getIndex();
        int next_index = 0;
        int next_end = 0;

        if (index + page * 2 <= total)
        {
            next_index = index + page - 1;
            next_end = index + page * 2 - 1;
        }
        else
        {
            next_index = index + page - 1;
            next_end = total;
        }

        //Pagerに値をセット
        setPagerValue(next_index + 1, total, page);

        //リストデータをセット
        setList(next_index, next_end);

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
    public void pgr_PagerSync_Prev(ActionEvent e)
            throws Exception
    {

        int total = pgr_PagerSync.getMax();
        int page = pgr_PagerSync.getPage();
        int index = pgr_PagerSync.getIndex();
        int next_index = 0;
        int next_end = 0;

        if (index - page <= 0)
        {
            next_index = 0;
            next_end = page;
        }
        else
        {
            next_index = index - page - 1;
            next_end = index - 1;
        }

        //Pagerに値をセット
        setPagerValue(next_index + 1, total, page);

        //リストデータをセット
        setList(next_index, next_end);
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
    public void pgr_PagerSync_Last(ActionEvent e)
            throws Exception
    {
        int total = pgr_PagerSync.getMax();
        int page = pgr_PagerSync.getPage();

        int next_index = 0;
        int next_end = 0;
        if (total % page == 0)
        {
            next_index = total - page;
            next_end = total;
        }
        else
        {
            next_index = total - (total % page);
            next_end = total;
        }

        //Pagerに値をセット
        setPagerValue(next_index + 1, total, page);

        //リストデータをセット
        setList(next_index, next_end);
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
    public void pgr_PagerSync_First(ActionEvent e)
            throws Exception
    {
        int total = pgr_PagerSync.getMax();
        int page = pgr_PagerSync.getPage();
        //Pagerに値をセット
        setPagerValue(1, total, page);
        //      リストデータをセット
        setList(0, page);
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
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {

        this.session.removeAttribute(AccessLogListBusiness.START_DATE);
        this.session.removeAttribute(AccessLogListBusiness.END_DATE);

        lst_SearchConditionTwoColumn.setCurrentRow(3);
        // 呼び出し元の画面へ渡すパラメータ作成
        ForwardParameters param = new ForwardParameters();
        // ファイル名設定
        param.setParameter(DsNoListBusiness.DS_NO, lst_SearchConditionTwoColumn.getValue(2));
        param.setParameter(DsNoListBusiness.PAGE_NAME_KEY, lst_SearchConditionTwoColumn.getValue(4));
        // 親画面に戻る
        parentRedirect(param);
    }

    /**
     * アクセス種別から表示名称を取得します。
     * 
     * @param accessType アクセス種別
     * @return アクセス種別表示名称
     * @throws Exception
     */
    private String getAccessLogType(int accessType)
            throws Exception
    {
        String accessTypeStr = "";

        switch (accessType)
        {
            // ログイン or 認証無しログイン
            case EmConstants.AUTHLOG_CLASS_LOGIN:
            case EmConstants.AUTHLOG_CLASS_NOSCREENACESS:
                accessTypeStr = DispResourceMap.getText("LBL-T0181");
                break;
            // ログアウト
            case EmConstants.AUTHLOG_CLASS_LOGOUT:
                accessTypeStr = DispResourceMap.getText("LBL-T0182");
                break;
            // セッションタイムアウト
            case EmConstants.AUTHLOG_CLASS_TIMEOUT:
                accessTypeStr = DispResourceMap.getText("LBL-T0183");
                break;
            // 認証失敗
            case EmConstants.AUTHLOG_CLASS_FAILED:
                accessTypeStr = DispResourceMap.getText("LBL-T0184");
                break;
        }

        return accessTypeStr;
    }

    /** <en>
     * 
     * This method returns the page name for a given dsNumber  
     *  
     </en> */
    private String getScreenName(String tableName, String dsNumber, AccessLogHandler handler)
            throws Exception
    {
        String resourceKey = handler.findPageNameByDSNum(tableName, dsNumber);
        return DispResourceMap.getText(resourceKey);
    }
}
//end of class
