// $Id: DsNoListBusiness.java 3965 2009-04-06 02:55:05Z admin $

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
import jp.co.daifuku.emanager.database.entity.DsNo;
import jp.co.daifuku.emanager.database.handler.DsNoHandler;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.util.DispResourceMap;
import jp.co.daifuku.emanager.util.EManagerUtil;
import jp.co.daifuku.emanager.util.EmProperties;


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
public class DsNoListBusiness
        extends DsNoList
{
    // Class fields --------------------------------------------------

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
    public static final String DS_NO = "DS_NO";

    /**
     * 画面名称を保持するキー
     */
    public static final String PAGE_NAME_KEY = "PAGE_NAME_KEY";

    /**
     * テーブル名を保持するキー
     */
    public static final String TABLE_NAME = "TABLE_NAME";

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
        // 検索処理用のDS番号を取得
        String searchDsNo = dsNo;
        if (EManagerUtil.isLikeSearch(dsNo))
        {
            searchDsNo = EManagerUtil.getLikeSearchString(dsNo);
        }

        this.lbl_In_SettingName.setResourceKey("TLE-T0138");
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

        // if search dates are not null, check if end date is before start date
        if (startDate != null && endDate != null && endDate.before(startDate))
        {
            setPagerValue(0, 0, 0);
            message.setMsgResourceKey("6403083");
            lst_DsNoList.setVisible(false);
            return;
        }

        //convert the dates to String format  based on locale 
        String dateFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), Constants.F_DATE_TIME);
        String startDateStr = startDate != null ? EManagerUtil.getDateFormat(startDate, dateFormat)
                                               : null;
        String endDateStr = endDate != null ? EManagerUtil.getDateFormat(endDate, dateFormat)
                                           : null;

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
        this.getViewState().setString(TABLE_NAME, searchTableName);

        Connection conn = null;
        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            DsNoHandler handler = EmHandlerFactory.getDsNoHandler(conn);

            String oracleDateFormat =
                    EManagerUtil.getOracleDateFormat(this.httpRequest.getLocale(), Constants.F_DATE_TIME);
            // 合計数
            int total =
                    handler.findCountDsNo(searchTableName, startDateStr, endDateStr, userId, searchDsNo,
                            oracleDateFormat, EManagerUtil.isLikeSearch(dsNo));
            //1ページの表示件数
            int page = Integer.parseInt(EmProperties.getProperty("ListboxSearchCount"));

            //			最初のページの表示終了位置
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
                pager.setMax(0); //最大件数
                pager.setPage(0); //1Page表示件数
                pager.setIndex(0); //開始位置				
                //ヘッダーを隠します
                lst_DsNoList.setVisible(false);

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
    private void setList(DsNoHandler handler, int next_index, int next_end)
            throws Exception
    {
        next_index++;
        //表を削除
        lst_DsNoList.clearRow();
        String startDate = this.getViewState().getString(START_DATE);
        String endDate = this.getViewState().getString(END_DATE);
        String dsNo = this.getViewState().getString(DS_NO);
        String userId = this.getViewState().getString(USER_ID);
        String searchTableName = this.getViewState().getString(TABLE_NAME);
        String searchDsNo = dsNo;
        if (EManagerUtil.isLikeSearch(dsNo))
        {
            searchDsNo = EManagerUtil.getLikeSearchString(dsNo);
        }

        //check part11 view type:: DB or CSV (import) else //if value is neither DB nor IMP .. Error 
        if (searchTableName == null || "".equals(searchTableName)) //if Table name is null or empty .. Error 
        {
            message.setMsgResourceKey("6404001");
            return;
        }
        // 合計数取得
        String oracleDateFormat = EManagerUtil.getOracleDateFormat(this.httpRequest.getLocale(), Constants.F_DATE_TIME);
        DsNo[] ds_Array =
                handler.findDsNo(searchTableName, startDate, endDate, userId, searchDsNo, next_index, next_end,
                        oracleDateFormat, EManagerUtil.isLikeSearch(dsNo));

        //ロールを取得できたか確認
        if (ds_Array == null)
        {
            setPagerValue(0, 0, 0);
            this.lst_DsNoList.setVisible(false);
            return;
        }

        int no = next_index;
        //表にセット
        for (int i = 0; i < ds_Array.length; i++)
        {
            if (ds_Array[i] == null)
            {
                continue;
            }
            //行追加
            this.lst_DsNoList.addRow();

            lst_DsNoList.setCurrentRow(lst_DsNoList.getMaxRows() - 1);

            // set data to ScrollListcell
            lst_DsNoList.setValue(1, Integer.toString(no++));
            lst_DsNoList.setValue(2, ds_Array[i].getDsNo());
            String screenName = DispResourceMap.getText(ds_Array[i].getPageNameResourceKey());
            lst_DsNoList.setValue(3, screenName);
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
            DsNoHandler handler = EmHandlerFactory.getDsNoHandler(conn);
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
        pager.setIndex(index);
        pager.setMax(total);
        pager.setPage(page);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        this.session.removeAttribute(START_DATE);
        this.session.removeAttribute(END_DATE);

        //呼び出し元の画面へ遷移します
        parentRedirect(null);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_DsNoList_Click(ActionEvent e)
            throws Exception
    {
        // 現在の行をセット
        lst_DsNoList.setCurrentRow(lst_DsNoList.getActiveRow());
        // 呼び出し元の画面へ渡すパラメータ作成
        ForwardParameters param = new ForwardParameters();
        // ファイル名設定
        param.setParameter(DS_NO, lst_DsNoList.getValue(2));
        param.setParameter(PAGE_NAME_KEY, lst_DsNoList.getValue(3));

        // 呼び出し元の画面へ遷移します
        parentRedirect(param);
    }

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_Next(ActionEvent e)
            throws Exception
    {
        int total = pager.getMax();
        int page = pager.getPage();
        int index = pager.getIndex();
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

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_Prev(ActionEvent e)
            throws Exception
    {
        int total = pager.getMax();
        int page = pager.getPage();
        int index = pager.getIndex();
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

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_Last(ActionEvent e)
            throws Exception
    {
        int total = pager.getMax();
        int page = pager.getPage();

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

    /** 
     * 
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void pager_First(ActionEvent e)
            throws Exception
    {
        int total = pager.getMax();
        int page = pager.getPage();

        //Pagerに値をセット
        setPagerValue(1, total, page);
        //		リストデータをセット
        setList(0, page);
    }
}
//end of class
