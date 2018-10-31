// $Id: MainteLogListBusiness.java 3965 2009-04-06 02:55:05Z admin $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.display.web.logview.listbox;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.co.daifuku.bluedog.ui.control.ScrollListCell;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.emanager.database.entity.LogInfo;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.LogTempHandler;
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
public class MainteLogListBusiness
        extends MainteLogList
{
    /**
     * 検索条件(開始日付)
     */
    public static final String LOGDATESTART = "LOGDATESTART";

    /**
     * 検索条件(開始時刻)
     */
    public static final String LOGDATESTARTTIME = "LOGDATESTARTTIME";

    /**
     * 検索条件(終了日付)
     */
    public static final String LOGDATEEND = "LOGDATEEND";

    /**
     * 検索条件(終了時刻)
     */
    public static final String LOGDATEENDTIME = "LOGDATEENDTIME";

    /**
     * 検索条件(ユーザID)
     */
    public static final String USERID = "USERID";

    /**
     * 検索条件(ログ種別)
     */
    public static final String LOGCLASS = "LOGCLASS";

    /**
     * メッセージ
     */
    public String dummyMessage = "";

    /**
     * 詳細
     */
    public String dummyDetails = "";


    // Public methods ------------------------------------------------

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

        //画面名称をセットする
        this.lbl_ListName.setResourceKey("TLE-T0104");

        txa_Message.setText("");
        txa_Detail.setText("");

        //String startDate = request.getParameter("LOGDATESTART");        
        String startTime = request.getParameter("LOGDATESTARTTIME");
        //String endDate = request.getParameter("LOGDATEEND");        
        String endTime = request.getParameter("LOGDATEENDTIME");
        String logClass = request.getParameter("LOGCLASS");
        String userId = request.getParameter("USERID");

        Date startDate = (Date)this.getSession().getAttribute(LOGDATESTART);
        Date endDate = (Date)this.getSession().getAttribute(LOGDATEEND);

        //this.getViewState().setString("LOGDATESTART",startDate);
        this.getViewState().setString("LOGDATESTARTTIME", startTime);
        // this.getViewState().setString("LOGDATEEND",endDate);
        this.getViewState().setString("LOGDATEENDTIME", endTime);
        this.getViewState().setString("LOGCLASS", logClass);
        this.getViewState().setString("USERID", userId);


        int logClassInt = 0;
        Timestamp stTime = null;
        Timestamp edTime = null;

        //     ログ区分パラメータ作成
        if (!"6".equals(logClass))
        {
            logClassInt = Integer.parseInt(logClass);
        }

        //検索期間パラメータ作成
        Calendar stCal = this.strToCalendar(startDate, startTime, false);
        Calendar edCal = this.strToCalendar(endDate, endTime, true);
        if (stCal != null)
        {
            stTime = new Timestamp(stCal.getTimeInMillis());
            stTime.setNanos(0);
        }
        if (edCal != null)
        {
            edTime = new Timestamp(edCal.getTimeInMillis());
            edTime.setNanos(0);
        }
        Connection conn = null;

        try
        {
            conn = EmConnectionHandler.getPageDbConnection(this);
            LogTempHandler handler = EmHandlerFactory.getLogTempHandler(conn);

            // 合計数
            int total = 0;
            //合計数取得
            // CSVのデータだけではなくデータベースのデータも含めるか？ 
            if (this.getViewState().getString("Database").equals("ON"))
            {
                // 合計数取得
                total = handler.findCountMainteView(stTime, edTime, userId, logClassInt);
            }
            else
            {
                // 合計数取得				
                total = handler.findCountMainteTemp(stTime, edTime, userId, logClassInt);
            }
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
                pager.setMax(0); //最大件数
                pager.setPage(0); //1Page表示件数
                pager.setIndex(0); //開始位置				
                //ヘッダーを隠します
                lst_LogList.setVisible(false);

                //対象データはありませんでした
                message.setMsgResourceKey("6403077");
            }
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }
    }


    //	 Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------	
    /** 
     * リストセルに値をセットします
     * @param roleHandler 
     * @param authHandler 
     * @param next_index int
     * @param next_end int 
     * @throws Exception 
     */
    private void setList(LogTempHandler logHandler, int next_index, int next_end)
            throws Exception
    {
        txa_Message.setText("");
        txa_Detail.setText("");

        next_index++;

        //表を削除
        lst_LogList.clearRow();

        //String startDate = this.getViewState().getString("LOGDATESTART");        
        String startTime = this.getViewState().getString("LOGDATESTARTTIME");
        //String endDate = this.getViewState().getString("LOGDATEEND");        
        String endTime = this.getViewState().getString("LOGDATEENDTIME");
        String logClass = this.getViewState().getString("LOGCLASS");
        String userId = this.getViewState().getString("USERID");

        Date startDate = (Date)this.getSession().getAttribute(LOGDATESTART);
        Date endDate = (Date)this.getSession().getAttribute(LOGDATEEND);

        int logClassInt = 0;
        Timestamp stTime = null;
        Timestamp edTime = null;

        //	      ログ区分パラメータ作成
        if (!"6".equals(logClass))
        {
            logClassInt = Integer.parseInt(logClass);
        }

        //検索期間パラメータ作成
        Calendar stCal = this.strToCalendar(startDate, startTime, false);
        Calendar edCal = this.strToCalendar(endDate, endTime, true);
        if (stCal != null)
        {
            stTime = new Timestamp(stCal.getTimeInMillis());
            stTime.setNanos(0);
        }
        if (edCal != null)
        {
            edTime = new Timestamp(edCal.getTimeInMillis());
            edTime.setNanos(0);
        }


        LogInfo logInfo_Array[] = null;

        //logInfo_Array = logHandler.findAuthView(stTime, edTime, userId, logClassInt,next_index,next_end);

        //	CSVのデータだけではなくデータベースのデータも含めるか？ 
        if (this.getViewState().getString("Database").equals("ON"))
        {
            // 合計数取得
            logInfo_Array = logHandler.findMainteView(stTime, edTime, userId, logClassInt, next_index, next_end);
        }
        else
        {
            // 合計数取得
            logInfo_Array = logHandler.findAllMainteTemp(stTime, edTime, userId, logClassInt, next_index, next_end);
        }
        //ロールを取得できたか確認
        if (logInfo_Array == null)
        {
            setPagerValue(0, 0, 0);
            this.lst_LogList.setVisible(false);
            return;
        }

        //１件以上表示するデータがあるか
        boolean dispList = false;
        int no = next_index;
        //			 get date display format based on the country
        String dateFormat = EManagerUtil.getDateFormat(this.httpRequest.getLocale(), 0);
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        //表にセット
        for (int i = 0; i < logInfo_Array.length; i++)
        {
            if (logInfo_Array[i] == null)
            {
                continue;
            }
            //行追加
            this.lst_LogList.addRow();

            lst_LogList.setCurrentRow(lst_LogList.getMaxRows() - 1);

            // set data to ScrollListcell
            lst_LogList.setValue(1, Integer.toString(no++));
            String value = formatter.format(logInfo_Array[i].getLogDate());
            lst_LogList.setValue(2, value);
            lst_LogList.setValue(3, logInfo_Array[i].getUserId());
            lst_LogList.setValue(4, logInfo_Array[i].getIpAddress());
            lst_LogList.setValue(5, logInfo_Array[i].getTerminalNumber());
            lst_LogList.setValue(6, logClass);

            String temp[] = new String[2];
            temp[0] = logInfo_Array[i].getMessage();
            temp[1] = logInfo_Array[i].getDetails();
            // if message is null , set default value
            if (temp[0] == null)
            {
                temp[0] = this.dummyMessage;
            }
            // if details are null , set default value
            if (temp[1] == null)
            {
                temp[1] = this.dummyDetails;
            }

            // convert log message and log details to CSV mode
            String hiddenData = EManagerUtil.getCsvFromStringArray(temp);
            // set log message and log details as hidden data
            lst_LogList.setValue(0, hiddenData);

            //１件以上表示する列がある。
            dispList = true;
        }

        if (!dispList)
        {
            //表示する列が１件もない場合
            setPagerValue(0, 0, 0);
            this.lst_LogList.clearRow();
            this.lst_LogList.setVisible(false);
            return;
        }
    }

    /** 
     * リストが押下されたときに呼ばれます
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void lst_LogList_Click(ActionEvent e)
            throws Exception
    {
        //現在の行をセット
        int activeRow = lst_LogList.getActiveRow();
        lst_LogList.setCurrentRow(activeRow);

        // highlight the selected row in scroll listcell 

        lst_LogList.setHighlight(activeRow);
        // get log message and log details from hidden data
        String hiddenData = lst_LogList.getValue(0);
        // convert log message and details from CSV to string array
        String temp[] = EManagerUtil.getStringArrayFromCsv(hiddenData);
        // set message to message area		
        txa_Message.setText(temp[0]);
        // set detials to message area
        txa_Detail.setText(temp[1]);

        this.setListCellFocus(lst_LogList, activeRow, 1);

        message.setMsgResourceKey("6401013");
    }

    /**
     * リストセルのセルに対してフォーカスをセットします。
     * @param listcell リストセル
     * @param row 行
     * @param col 列
     */
    private void setListCellFocus(ScrollListCell listcell, int row, int col)
    {
        String script = "_setListCellFocus('" + listcell.getId() + "'," + row + "," + col + ");";
        super.addOnloadScript(script);
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
            conn = EmConnectionHandler.getPageDbConnection(this);
            conn = EmConnectionHandler.getPageDbConnection(this);
            LogTempHandler handler = EmHandlerFactory.getLogTempHandler(conn);
            setList(handler, next_index, next_end);
        }
        finally
        {
            //コネクションを開放
            EmConnectionHandler.closeConnection(conn);
        }
    }

    /** 
     * 日付型の文字列をカレンダー型に変換します。
     * @param date 日付文字列
     * @param time 時間文字列
     * @param toFlag 時間を23時59分59秒にするフラグ
     * @throws Exception 
     */
    private Calendar strToCalendar(Date date, String time, boolean toFlag)
    {
        int hour = 0;
        int minite = 0;
        int second = 0;
        // 日付の分解
        // 日時の分解
        if (time.length() == 8)
        {
            hour = Integer.parseInt(time.substring(0, 2));
            minite = Integer.parseInt(time.substring(3, 5));
            second = Integer.parseInt(time.substring(6, 8));

        }
        else if (time.length() == 0)
        {
            // 時間が未入力でも日付が入力していた場合は処理継続
            // 終了日時の場合は時間を23時59分59秒に設定
            if (toFlag)
            {
                hour = 23;
                minite = 59;
                second = 59;
            }
        }
        else
        {
            return null;
        }

        // カレンダーオブジェクトの取得
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);

        // カレンダーオブジェクトの設定
        calendar.set(year, month, day, hour, minite, second);

        // カレンダーの返却
        return calendar;
    }

    /** 
     * Pagerの次へボタンが押下されたときに呼ばれます
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
     * Pagerの前へボタンが押下されたときに呼ばれます
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
     * Pagerの最後ボタンが押下されたときに呼ばれます
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
     * Pagerの最初ボタンが押下されたときに呼ばれます
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
     * 閉じるボタンが押下されたときに呼ばれます。
     * @param e ActionEvent 
     * @throws Exception 
     */
    public void btn_Close_Click(ActionEvent e)
            throws Exception
    {
        //呼び出し元の画面へ遷移します
        parentRedirect(null);
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
        txa_Message.setText("");
        txa_Detail.setText("");
    }
    
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Event handler methods -----------------------------------------

}
//end of class
