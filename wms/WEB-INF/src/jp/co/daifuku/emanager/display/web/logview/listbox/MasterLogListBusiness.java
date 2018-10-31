// $Id: MasterLogListBusiness.java 3965 2009-04-06 02:55:05Z admin $

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
import jp.co.daifuku.emanager.database.entity.UserMasterLog;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.UserMasterChangeLogHandler;
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
public class MasterLogListBusiness
        extends MasterLogList
{
    //	 Class variables -----------------------------------------------
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
     * DS番号を保持するキー
     */
    public final static String DS_NO = "DS_NO";

    /**
     * テーブル名を保持するキー
     */
    public final static String TABLE_NAME = "TABLE_NAME";

    /**
     * タイトルを保持するキー
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
        //		boolean  imp = false;
        // if user id is "", set User id as null
        String userId = "".equals(request.getParameter(USER_ID)) ? null
                                                                : request.getParameter(USER_ID);
        // if ds number is is "", set User id as null
        String dsNo = "".equals(request.getParameter(DS_NO)) ? null
                                                            : request.getParameter(DS_NO);
        //  String screenName = request.getParameter(SCREEN_NAME);
        String pageTitle = httpRequest.getParameter(TITLE);

        this.lbl_ListName.setResourceKey("TLE-T0133");
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
            UserMasterChangeLogHandler handler = EmHandlerFactory.getUserMasterChangeLogHandler(conn);
            String screenName = this.getScreenName(searchTableName, dsNo, handler);
            // display search conditions on list cell
            this.lst_SearchCondition_Display(startDateStr, endDateStr, userId, dsNo, screenName);
            
            // if search dates are not null, check if end date is before start date
            if (startDate != null && endDate != null && endDate.before(startDate))
            {
                setPagerValue(0, 0, 0);
                message.setMsgResourceKey("6403083");
                lst_UserMasterUpdateLog.setVisible(false);
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
                    handler.findCountUserMasterLog(searchTableName, startDateStr, endDateStr, userId, dsNo,
                            oracleDateFormat);
            //1ページの表示件数
            int page = Integer.parseInt(EmProperties.getProperty("ListboxSearchCount"));

            //最初のページの表示終了位置
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
                lst_UserMasterUpdateLog.setVisible(false);

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
     * リストセルに値をセットします
     * @param roleHandler 
     * @param authHandler 
     * @param next_index int
     * @param next_end int 
     * @throws Exception 
     */
    private void setList(UserMasterChangeLogHandler logHandler, int next_index, int next_end)
            throws Exception
    {
        next_index++;
        //表を削除
        lst_UserMasterUpdateLog.clearRow();
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
        // 合計数取得
        String oracleDateFormat = EManagerUtil.getOracleDateFormat(this.httpRequest.getLocale(), Constants.F_DATE_TIME);
        UserMasterLog[] logInfo_Array =
                logHandler.findUserMasterLog(searchTableName, startDate, endDate, userId, dsNo, next_index, next_end,
                        oracleDateFormat);


        //ロールを取得できたか確認
        if (logInfo_Array == null)
        {
            setPagerValue(0, 0, 0);
            this.lst_UserMasterUpdateLog.setVisible(false);
            return;
        }

        //get date display format based on the country
        String dateFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), Constants.F_DATE);
        //String timeFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), Constants.F_TIME);
        String timeFormat = EmConstants.MILLISECOND_FORMAT;
        //SimpleDateFormat  formatter = new SimpleDateFormat(dateFormat);

        int no = next_index;
        //表にセット
        for (int i = 0; i < logInfo_Array.length; i++)
        {
            if (logInfo_Array[i] == null)
            {
                continue;
            }
            //行追加
            this.lst_UserMasterUpdateLog.addRow();

            lst_UserMasterUpdateLog.setCurrentRow(lst_UserMasterUpdateLog.getMaxRows() - 1);

            // set data to ScrollListcell
            lst_UserMasterUpdateLog.setValue(1, Integer.toString(no++));
            String date = EManagerUtil.getDateFormat(logInfo_Array[i].getLogDate(), dateFormat);
            lst_UserMasterUpdateLog.setValue(2, date);
            lst_UserMasterUpdateLog.setValue(3, this.changeType(logInfo_Array[i].getUpdateKind()));
            lst_UserMasterUpdateLog.setValue(4, logInfo_Array[i].getUserId());
            lst_UserMasterUpdateLog.setValue(5, logInfo_Array[i].getIpAddress());
            lst_UserMasterUpdateLog.setValue(6, logInfo_Array[i].getDsNumber());

            lst_UserMasterUpdateLog.setValue(7, logInfo_Array[i].getMasterUserId());

            // 登録の場合はBeforeは全てブランク
            if (EmConstants.USERMASTER_CLASS_REGIST != logInfo_Array[i].getUpdateKind())
            {
                lst_UserMasterUpdateLog.setValue(8, logInfo_Array[i].getUserNameBefore());
                lst_UserMasterUpdateLog.setValue(9, logInfo_Array[i].getPasswordBefore());

                String passwordChangeInt = "";
                if (logInfo_Array[i].getPasswordChangeIntervalBefore() == EmConstants.PWDCHANGEINTERVAL_STATUS_NOLIMITED)
                {
                    passwordChangeInt = DispResourceMap.getText("RDB-T0022");
                }
                else if (logInfo_Array[i].getPasswordChangeIntervalBefore() == EmConstants.PWDCHANGEINTERVAL_STATUS_EXTEND)
                {
                    passwordChangeInt = DispResourceMap.getText("RDB-T0006");
                }
                else
                {
                    passwordChangeInt = String.valueOf(logInfo_Array[i].getPasswordChangeIntervalBefore());
                }

                lst_UserMasterUpdateLog.setValue(10, passwordChangeInt);

                String passwordExp =
                        logInfo_Array[i].getPwdExpiresBefore() == null ? ""
                                                                      : EManagerUtil.getDateFormat(
                                                                              logInfo_Array[i].getPwdExpiresBefore(),
                                                                              dateFormat);
                lst_UserMasterUpdateLog.setValue(11, passwordExp);

                lst_UserMasterUpdateLog.setValue(12, logInfo_Array[i].getRoleIdBefore());

                String sameUserMax = "";
                if (logInfo_Array[i].getSameUserLoginMaxBefore() == EmConstants.SAMEUSER_BLOCK_PERIOD_STATUS_NOLIMITED)
                {
                    sameUserMax = DispResourceMap.getText("RDB-T0022");
                }
                else
                {
                    sameUserMax = String.valueOf(logInfo_Array[i].getSameUserLoginMaxBefore());
                }

                lst_UserMasterUpdateLog.setValue(13, sameUserMax);

                String userLock = "";
// 2008/12/08,2009/01/20 K.Matsuda Start テーブル列名変更(USERLOCK_FLAG -> USERSTATUS)に伴い定数の名称変更
                if (logInfo_Array[i].getUserStatusBefore() == EmConstants.USERSTATUS_ACTIVE)
                {
                    // 有効
                    userLock = DispResourceMap.getText("LBL-T0124");
                }
                else if (logInfo_Array[i].getUserStatusBefore() == EmConstants.USERSTATUS_DISABLED)
                {
                    // 無効
                    userLock = DispResourceMap.getText("LBL-T0276");
                }
                else if (logInfo_Array[i].getUserStatusBefore() == EmConstants.USERSTATUS_LOCKED)
                {
                    // ロック中
                    userLock = DispResourceMap.getText("LBL-T0125");
                }
// 2008/12/08,2009/01/20 K.Matsuda End

                lst_UserMasterUpdateLog.setValue(14, userLock);

                String faieldLoginAttempts = "";
                if (logInfo_Array[i].getFailedLoginAttemptsBefore() == EmConstants.FAILED_ATTEMPTS_STATUS_NOLIMITED)
                {
                    faieldLoginAttempts = DispResourceMap.getText("RDB-T0022");
                }
                else if (logInfo_Array[i].getFailedLoginAttemptsBefore() == EmConstants.FAILED_ATTEMPTS_STATUS_EXTEND)
                {
                    faieldLoginAttempts = DispResourceMap.getText("RDB-T0006");
                }
                else
                {
                    faieldLoginAttempts = String.valueOf(logInfo_Array[i].getFailedLoginAttemptsBefore());
                }

                lst_UserMasterUpdateLog.setValue(15, faieldLoginAttempts);

                lst_UserMasterUpdateLog.setValue(16, logInfo_Array[i].getDepartmentBefore());
                lst_UserMasterUpdateLog.setValue(17, logInfo_Array[i].getRemarkBefore());
            }

            String time = EManagerUtil.getDateFormat(logInfo_Array[i].getLogDate(), timeFormat);
            lst_UserMasterUpdateLog.setValue(18, time);
            lst_UserMasterUpdateLog.setValue(19, logInfo_Array[i].getUserName());
            lst_UserMasterUpdateLog.setValue(20, logInfo_Array[i].getTerminalName());
            String screenName = DispResourceMap.getText(logInfo_Array[i].getPageName());
            lst_UserMasterUpdateLog.setValue(21, screenName);

            // 削除の場合はAfterは全てブランク
            if (EmConstants.USERMASTER_CLASS_DELETE != logInfo_Array[i].getUpdateKind())
            {
                lst_UserMasterUpdateLog.setValue(22, logInfo_Array[i].getUserNameAfter());
                lst_UserMasterUpdateLog.setValue(23, logInfo_Array[i].getPasswordAfter());

                String passwordChangeInt = "";
                if (logInfo_Array[i].getPasswordChangeIntervalAfter() == EmConstants.PWDCHANGEINTERVAL_STATUS_NOLIMITED)
                {
                    passwordChangeInt = DispResourceMap.getText("RDB-T0022");
                }
                else if (logInfo_Array[i].getPasswordChangeIntervalAfter() == EmConstants.PWDCHANGEINTERVAL_STATUS_EXTEND)
                {
                    passwordChangeInt = DispResourceMap.getText("RDB-T0006");
                }
                else
                {
                    passwordChangeInt = String.valueOf(logInfo_Array[i].getPasswordChangeIntervalAfter());
                }
                lst_UserMasterUpdateLog.setValue(24, passwordChangeInt);

                String passwordExp =
                        logInfo_Array[i].getPwdExpiresAfter() == null ? ""
                                                                     : EManagerUtil.getDateFormat(
                                                                             logInfo_Array[i].getPwdExpiresAfter(),
                                                                             dateFormat);
                lst_UserMasterUpdateLog.setValue(25, passwordExp);

                lst_UserMasterUpdateLog.setValue(26, logInfo_Array[i].getRoleIdAfter());

                String sameUserMax = "";
                if (logInfo_Array[i].getSameUserLoginMaxAfter() == EmConstants.SAMEUSER_BLOCK_PERIOD_STATUS_NOLIMITED)
                {
                    sameUserMax = DispResourceMap.getText("RDB-T0022");
                }
                else
                {
                    sameUserMax = String.valueOf(logInfo_Array[i].getSameUserLoginMaxAfter());
                }
                lst_UserMasterUpdateLog.setValue(27, sameUserMax);

                String userLock = "";
// 2008/12/08,2009/01/20 K.Matsuda Start テーブル列名変更(USERLOCK_FLAG -> USERSTATUS)に伴い定数の名称変更
                if (logInfo_Array[i].getUserStatusAfter() == EmConstants.USERSTATUS_ACTIVE)
                {
                    // 有効
                    userLock = DispResourceMap.getText("LBL-T0124");
                }
                else if (logInfo_Array[i].getUserStatusAfter() == EmConstants.USERSTATUS_DISABLED)
                {
                    // 無効
                    userLock = DispResourceMap.getText("LBL-T0276");
                }
                else if (logInfo_Array[i].getUserStatusAfter() == EmConstants.USERSTATUS_LOCKED)
                {
                    // ロック中
                    userLock = DispResourceMap.getText("LBL-T0125");
                }
// 2008/12/08,2009/01/20 K.Matsuda End
                lst_UserMasterUpdateLog.setValue(28, userLock);

                String faieldLoginAttempts = "";
                if (logInfo_Array[i].getFailedLoginAttemptsAfter() == EmConstants.FAILED_ATTEMPTS_STATUS_NOLIMITED)
                {
                    faieldLoginAttempts = DispResourceMap.getText("RDB-T0022");
                }
                else if (logInfo_Array[i].getFailedLoginAttemptsAfter() == EmConstants.FAILED_ATTEMPTS_STATUS_EXTEND)
                {
                    faieldLoginAttempts = DispResourceMap.getText("RDB-T0006");
                }
                else
                {
                    faieldLoginAttempts = String.valueOf(logInfo_Array[i].getFailedLoginAttemptsAfter());
                }

                lst_UserMasterUpdateLog.setValue(29, faieldLoginAttempts);

                lst_UserMasterUpdateLog.setValue(30, logInfo_Array[i].getDepartmentAfter());
                lst_UserMasterUpdateLog.setValue(31, logInfo_Array[i].getRemarkAfter());
            }

            // ToolTip設定
            ToolTipHelper toolTip = new ToolTipHelper();
            // user Name
            toolTip.add(DispResourceMap.getText("LBL-T0140"), logInfo_Array[i].getUserName());
            // TerminalName
            toolTip.add(DispResourceMap.getText("LBL-T0141"), logInfo_Array[i].getTerminalName());
            // Screen name
            toolTip.add(DispResourceMap.getText("LBL-T0142"), screenName);
            // user Name before and After
            toolTip.add(DispResourceMap.getText("LBL-T0146"), logInfo_Array[i].getUserNameBefore());
            toolTip.add(DispResourceMap.getText("LBL-T0147"), logInfo_Array[i].getUserNameAfter());
            //  Remarks before and After 
            toolTip.add(DispResourceMap.getText("LBL-T0158"), logInfo_Array[i].getRemarkBefore());
            toolTip.add(DispResourceMap.getText("LBL-T0159"), logInfo_Array[i].getRemarkAfter());
            // get Department before and after
            toolTip.add(DispResourceMap.getText("LBL-T0160"), logInfo_Array[i].getDepartmentBefore());
            toolTip.add(DispResourceMap.getText("LBL-T0161"), logInfo_Array[i].getDepartmentAfter());

            //int ss = lst_ScreenAccessLog.getActiveRow();
            lst_UserMasterUpdateLog.setToolTip(i + 1, toolTip.getText());

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
            UserMasterChangeLogHandler handler = EmHandlerFactory.getUserMasterChangeLogHandler(conn);
            setList(handler, next_index, next_end);
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
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
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        this.session.removeAttribute(AccessLogListBusiness.START_DATE);
        this.session.removeAttribute(AccessLogListBusiness.END_DATE);

        ForwardParameters param = new ForwardParameters();
        // ファイル名設定
        param.setParameter(DsNoListBusiness.DS_NO, lst_SearchConditionTwoColumn.getValue(2));
        param.setParameter(DsNoListBusiness.PAGE_NAME_KEY, lst_SearchConditionTwoColumn.getValue(4));
        // 親画面に戻る
        parentRedirect(param);
    }

    /**
     * 更新種別から表示名称を取得します。
     * 
     * @param updateClass 更新種別
     * @return 更新種別の表示名称
     * @throws Exception
     */
    private String changeType(int updateClass)
            throws Exception
    {
        String result = "";

        switch (updateClass)
        {
            // 登録
            case EmConstants.USERMASTER_CLASS_REGIST:
                result = DispResourceMap.getText("LBL-T0180");
                break;
            // 修正
            case EmConstants.USERMASTER_CLASS_MODIFY:
                result = DispResourceMap.getText("LBL-T0194");
                break;
            // 削除
            case EmConstants.USERMASTER_CLASS_DELETE:
                result = DispResourceMap.getText("LBL-T0195");
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
        //		リストデータをセット
        setList(0, page);
    }

    /** <en>
     * 
     * This method returns the page name for a given dsNumber  
     *  
     </en> */
    private String getScreenName(String tableName, String dsNumber, UserMasterChangeLogHandler handler)
            throws Exception
    {
        String resourceKey = handler.findPageNameByDSNum(tableName, dsNumber);
        return DispResourceMap.getText(resourceKey);
    }
}
//end of class
