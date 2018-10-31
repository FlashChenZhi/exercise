// $Id: Dasch_ja.java 5085 2009-10-01 08:09:16Z okamura $
package jp.co.daifuku.wms.system.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.system.dasch.MessageLogInquiryDASCHParams.FROM_SEARCH_DAY;
import static jp.co.daifuku.wms.system.dasch.MessageLogInquiryDASCHParams.FROM_SEARCH_TIME;
import static jp.co.daifuku.wms.system.dasch.MessageLogInquiryDASCHParams.TO_SEARCH_DAY;
import static jp.co.daifuku.wms.system.dasch.MessageLogInquiryDASCHParams.TO_SEARCH_TIME;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.system.schedule.SystemInParameter;


/**
 * ログ照会に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 5085 $, $Date:: 2009-10-01 17:09:16 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okamura $
 */
public class MessageLogInquiryDASCH
        extends AbstractWmsDASCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * メッセージログファイル名
     */
    private static final String MESSAGE_LOG_PATH = WmsParam.LOGS_PATH + "/" + WmsParam.MESSAGELOG_FILE;

    /**
     * ログ日付のフォーマット形式
     */
    private static final String DATE_FORMAT = "yyyy MM dd HH mm ss SSS z";

    /**
     * メッセージログアクセスエラー発生時に返す定数
     */
    private static final int FILE_ERROR = -1;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * DB Finder
     */
    private AbstractDBFinder _finder = null;

    /**
     * 現在点
     */
    private int _current = -1;

    /**
     * レコード総数
     */
    private int _total = 0;

    /**
     *  印刷内容パラメータ
     */
    private List<Params> _param;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでDASCHを作成します。
     * @param conn Database DBコネクション
     * @param parent Caller 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public MessageLogInquiryDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
     * 帳票出力、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params startparam)
            throws CommonException
    {
        RandomAccessFile rAFile = null;
        BufferedReader br = null;

        String dateFormat = "yyyyMMddhhmmss";
        SimpleDateFormat logDtFmt = new SimpleDateFormat(dateFormat);

        String fileDate = "";
        // ファイルポインタ
        long l1 = 0;
        // ファイル内容格納List
        List<Params> params = new ArrayList<Params>();
        // 最新の日付順にセットしなおす。
        //_param = new ArrayList<Params>();

        int stard = _total - WmsParam.MAX_NUMBER_OF_DISP_MESSAGE_LOG - 1;

        try
        {
            // パラメータの検索条件
            SystemInParameter wParam = new SystemInParameter();

            MessageLogInquiryDASCHParams startParam = (MessageLogInquiryDASCHParams)startparam;
            // 開始検索日
            Date fromDate = startParam.getDate(MessageLogInquiryDASCHParams.FROM_SEARCH_DAY);
            // 開始検索時刻
            Date fromTime = startParam.getDate(MessageLogInquiryDASCHParams.FROM_SEARCH_TIME);
            // 終了検索日
            Date toDate = startParam.getDate(MessageLogInquiryDASCHParams.TO_SEARCH_DAY);
            // 終了検索時刻
            Date toTime = startParam.getDate(MessageLogInquiryDASCHParams.TO_SEARCH_TIME);
            // 表示条件
            wParam.setDisplayCondition(startParam.getString(MessageLogInquiryDASCHParams.LOG));

            Date[] tmp = WmsFormatter.getFromTo(fromDate, fromTime, toDate, toTime);

            Date from = tmp[0];
            Date to = tmp[1];

            // 開始日、終了日を取得する
            String fromDateToken = this.changeDateFormat(from);
            String toDateToken = this.changeDateFormat(to);

            String fileData;
            //読み込むだけのRAFファイルを作成。
            rAFile = new RandomAccessFile(MESSAGE_LOG_PATH, "r");
            //ログ表示処理フラグ
            boolean flag = false;
            //開始検索時間が有る場合は印刷フラグをONにする。
            if (fromDateToken.length() == 19)
            {
                flag = true;
            }
            else
            {
                //開始検索時間が正常で無い場合はファイルポインタをゼロにする。
                l1 = rAFile.getFilePointer();
                //ファイルシークポイントをセット
                rAFile.seek(l1);
            }
            //先頭から識算子でログファイルを読み込んで入力開始時間とログファイルに有る時間を合わせる。
            while ((flag == true) && (!StringUtil.isBlank(fileData = rAFile.readLine())))
            {

                StringTokenizer sToken = new StringTokenizer(fileData, "\t");
                if (sToken.hasMoreTokens())
                {

                    try
                    {
                        // ファイルに書き込まれている日付を取得
                        StringBuilder buf = new StringBuilder();
                        // 年
                        buf.append(fileData.substring(0, 4));
                        // 月
                        buf.append(fileData.substring(5, 7));
                        // 日
                        buf.append(fileData.substring(8, 10));
                        // 時
                        buf.append(fileData.substring(11, 13));
                        // 分
                        buf.append(fileData.substring(14, 16));
                        // 秒
                        buf.append(fileData.substring(17, 19));

                        // ファイルに書き込まれている日付が削除日付よりも新しい場合
                        // ワークファイルに一時書き込み
                        String recordDate = String.valueOf(buf);

                        // ワーク日付をフォーマット
                        Date recDate = logDtFmt.parse(recordDate);

                        //ログファイルに有る開始時間が入力時間より大きかった場合はファイルポインタをセットする。
                        if ((fromDateToken.length() > 0) && from.compareTo(recDate) <= 0)
                        {
                            //シークポイントをセット（ピリオドでワイルドカードをセット？）し、最終行に戻り "\r"と "\n"をセットします。
                            try
                            {
                                rAFile.seek(l1);
                            }
                            catch (Exception e)
                            {
                                throw new ScheduleException(WmsMessageFormatter.format(6003011));
                            }
                            flag = false;
                        }

                    }
                    catch (ParseException e)
                    {
                        continue;
                    }
                    catch (StringIndexOutOfBoundsException e)
                    {
                        continue;
                    }


                }
                //ファイルポインタを保持
                l1 = rAFile.getFilePointer();
            }
            //            //先頭から識算子でログファイルを読み込んで入力開始時間とログファイルに有る時間を合わせる。
            //            while ((flag == true) && (!StringUtil.isBlank(fileData = rAFile.readLine())))
            //            {
            //                StringTokenizer sToken = new StringTokenizer(fileData, "\t");
            //                if (sToken.hasMoreTokens())
            //                {
            //                    String t1 = sToken.nextToken();
            //                    //grab date & time from the first token
            //                    StringTokenizer st1 = new StringTokenizer(t1, " ");
            //                    if (st1.hasMoreTokens())
            //                    {
            //                        String date = st1.nextToken() + " " + st1.nextToken() + " " + st1.nextToken();
            //                        String time = st1.nextToken() + " " + st1.nextToken() + " " + st1.nextToken();
            //                        fileDate = date + " " + time;
            //                    }
            //                    //ログファイルに有る開始時間が入力時間より大きかった場合はファイルポインタをセットする。
            //                    if ((fromDateToken.length() > 0) && fromDateToken.compareTo(fileDate) <= 0)
            //                    {
            //                        //シークポイントをセット（ピリオドでワイルドカードをセット？）し、最終行に戻り "\r"と "\n"をセットします。
            //                        try
            //                        {
            //                            rAFile.seek(l1);
            //                        }
            //                        catch (Exception e)
            //                        {
            //                            throw new ScheduleException(WmsMessageFormatter.format(6003011));
            //                        }
            //                        flag = false;
            //                    }
            //                }
            //                //ファイルポインタを保持
            //                l1 = rAFile.getFilePointer();
            //            }

            String bufferedData;
            //日本語を読み込む為にFileInputStreamリーダを作成する。
            br = new BufferedReader(new InputStreamReader(new FileInputStream(rAFile.getFD()), "utf-8"));
            //ログ表示処理フラグをONする

            flag = true;
            // 表示件数が最大数を越えたかどうかのフラグ
            //boolean isOverMaxDisplay = false;

            int row_count = 0;

            while (!StringUtil.isBlank(bufferedData = br.readLine()) && (flag == true))
            {
                StringTokenizer wDivide = new StringTokenizer(bufferedData, "\t");
                while ((wDivide.hasMoreElements()) && (flag == true))
                {
                    String t1 = wDivide.nextToken();
                    //最初のトークンから日時を取得（グラブ）します。
                    //                  StringTokenizer st1 = new StringTokenizer(t1, " ");

                    try
                    {
                        // ファイルに書き込まれている日付を取得
                        StringBuilder buf = new StringBuilder();
                        // 年
                        buf.append(t1.substring(0, 4));
                        // 月
                        buf.append(t1.substring(5, 7));
                        // 日
                        buf.append(t1.substring(8, 10));
                        // 時
                        buf.append(t1.substring(11, 13));
                        // 分
                        buf.append(t1.substring(14, 16));
                        // 秒
                        buf.append(t1.substring(17, 19));

                        // ファイルに書き込まれている日付が削除日付よりも新しい場合
                        // ワークファイルに一時書き込み
                        String recordDate = String.valueOf(buf);

                        // ワーク日付をフォーマット
                        Date recDate = logDtFmt.parse(recordDate);

                        //ログファイルに有る開始時間が入力時間より大きかった場合はファイルポインタをセットする。
                        if ((toDateToken.length() > 0) && to.compareTo(recDate) < 0)
                        {
                            flag = false;
                        }
                    }
                    catch (ParseException e)
                    {
                        continue;
                    }
                    catch (StringIndexOutOfBoundsException e)
                    {
                        continue;
                    }
                    //                    //最初のトークンから日時を取得（グラブ）します。
                    //                    StringTokenizer st1 = new StringTokenizer(t1, " ");
                    //                    if (st1.hasMoreTokens())
                    //                    {
                    //                        String date = st1.nextToken() + " " + st1.nextToken() + " " + st1.nextToken();
                    //                        String time = st1.nextToken() + " " + st1.nextToken() + " " + st1.nextToken();
                    //                        fileDate = date + " " + time;
                    //                    }
                    //                    //ログファイルに有るエラー発生日時が入力時間より大きかった場合はログ表示処理フラグをoffにする
                    //                    if ((toDateToken.length() > 0) && toDateToken.compareTo(fileDate) < 0)
                    //                    {
                    //                        flag = false;
                    //                    }
                    if (flag)
                    {


                        // エラーコード
                        String wErrCode = "";
                        // ファシリティコード
                        String wFacilityCode = "";
                        // 発生クラス
                        String wClassInfo = "";

                        Date wWriteDate = null;

                        String wMessageLog = "";

                        Params p = new Params();

                        try
                        {

                            // エラーコード
                            wErrCode = wDivide.nextToken();
                            // ファシリティコード
                            wFacilityCode = wDivide.nextToken();
                            // 発生クラス
                            wClassInfo = wDivide.nextToken();
                            // パラメータ
                            int count = wDivide.countTokens();
                            String[] wParams = new String[count];
                            if (wDivide.hasMoreTokens())
                            {
                                for (int j = 0; j < count; j++)
                                {
                                    wParams[j] = wDivide.nextToken();
                                }
                            }
                            // 書込み日時をString型からDate型に変換する。
                            wWriteDate = new SimpleDateFormat(DATE_FORMAT).parse(t1, new ParsePosition(0));
                            try
                            {
                                // メッセージを取得する
                                wMessageLog = SimpleFormat.format(MessageResource.getMessage(wErrCode), wParams);
                            }
                            catch (MissingResourceException e)
                            {
                                // リソース番号が無かった場合メッセージを空白にする
                                wMessageLog = "";
                            }
                            // ファシリティコード表示用変数
                            String wDispFacility = "";
                            // 処理続行フラグ（True：処理続行 False：CONTINUEし次データ取得）
                            boolean wNext = false;
                            wDispFacility = changeFacility(wFacilityCode, wParam);
                            if (!StringUtil.isBlank(wDispFacility))
                            {
                                wNext = true;
                            }
                            // 続行フラグがONの場合、次の処理に進む
                            if (wNext)
                            {
                                // 開始日時、終了日時を取得
                                Date wStrDate = wParam.getSearchDate();
                                Date wEndDate = wParam.getToSearchDate();

                                wNext = checkDateTiem(wWriteDate, wStrDate, wEndDate);
                                // 続行フラグがONの場合、処理を続行
                                if (wNext)
                                {
                                    if (_total > WmsParam.MAX_NUMBER_OF_DISP_MESSAGE_LOG)
                                    {
                                        if (row_count++ > stard)
                                        {
                                            // 表示するデータを返却パラメータにセットする。
                                            p.set(MessageLogInquiryDASCHParams.DATE, wWriteDate);

                                            // 内容を格納します
                                            String sdcondition = "";
                                            if (SystemInParameter.DISPLAY_CONDITION_INFORMATION.equals(wDispFacility))
                                            {
                                                // 案内
                                                sdcondition = DisplayText.getText("RDB-W0021");
                                            }
                                            else if (SystemInParameter.DISPLAY_CONDITION_ATTENTION.equals(wDispFacility))
                                            {
                                                // 注意
                                                sdcondition = DisplayText.getText("RDB-W0022");
                                            }
                                            else if (SystemInParameter.DISPLAY_CONDITION_WARNING.equals(wDispFacility))
                                            {
                                                // 警告
                                                sdcondition = DisplayText.getText("RDB-W0023");
                                            }
                                            else if (SystemInParameter.DISPLAY_CONDITION_ERROR.equals(wDispFacility))
                                            {
                                                // 異常
                                                sdcondition = DisplayText.getText("RDB-W0024");
                                            }
                                            p.set(MessageLogInquiryDASCHParams.MESSAGE_NO, wErrCode);
                                            p.set(MessageLogInquiryDASCHParams.LEVEL, sdcondition);
                                            p.set(MessageLogInquiryDASCHParams.CLASS_NAME, wClassInfo);
                                            p.set(MessageLogInquiryDASCHParams.MESSAGE, wMessageLog);
                                            params.add(p);
                                        }
                                    }
                                    else
                                    {
                                        // 表示するデータを返却パラメータにセットする。
                                        p.set(MessageLogInquiryDASCHParams.DATE, wWriteDate);

                                        // 内容を格納します
                                        String sdcondition = "";
                                        if (SystemInParameter.DISPLAY_CONDITION_INFORMATION.equals(wDispFacility))
                                        {
                                            // 案内
                                            sdcondition = DisplayText.getText("RDB-W0021");
                                        }
                                        else if (SystemInParameter.DISPLAY_CONDITION_ATTENTION.equals(wDispFacility))
                                        {
                                            // 注意
                                            sdcondition = DisplayText.getText("RDB-W0022");
                                        }
                                        else if (SystemInParameter.DISPLAY_CONDITION_WARNING.equals(wDispFacility))
                                        {
                                            // 警告
                                            sdcondition = DisplayText.getText("RDB-W0023");
                                        }
                                        else if (SystemInParameter.DISPLAY_CONDITION_ERROR.equals(wDispFacility))
                                        {
                                            // 異常
                                            sdcondition = DisplayText.getText("RDB-W0024");
                                        }
                                        p.set(MessageLogInquiryDASCHParams.MESSAGE_NO, wErrCode);
                                        p.set(MessageLogInquiryDASCHParams.LEVEL, sdcondition);
                                        p.set(MessageLogInquiryDASCHParams.CLASS_NAME, wClassInfo);
                                        p.set(MessageLogInquiryDASCHParams.MESSAGE, wMessageLog);
                                        params.add(p);

                                    }

                                }
                            }
                        }
                        catch (NoSuchElementException e)
                        {
                            continue;
                        }
                        catch (StringIndexOutOfBoundsException e)
                        {
                            continue;
                        }
                    }
                }
            }

            if (_total > WmsParam.MAX_NUMBER_OF_DISP_MESSAGE_LOG)
            {
                _total = WmsParam.MAX_NUMBER_OF_DISP_MESSAGE_LOG;
            }

            _param = new ArrayList<Params>();

            for (int i = params.size() - 1; i >= 0; i--)
            {
                _param.add(params.get(i));
            }
            setMessage(WmsMessageFormatter.format(6003011));
        }
        catch (Exception e)
        {
            // 6006020 = ファイルの入出力エラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getSimpleName());
            // 6007031 = ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            throw new ScheduleException();
        }
        finally
        {
            try
            {
                if (br != null)
                {
                    // ファイルのクローズ
                    br.close();
                }
                //close raf
                if (rAFile != null)
                {
                    rAFile.close();
                }
            }
            catch (IOException e)
            {
                // 6006020 = ファイルの入出力エラーが発生しました。{0}
                RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
                // 6007031 = ファイルの入出力エラーが発生しました。ログを参照してください。
                setMessage(WmsMessageFormatter.format(6007031));
                throw new ScheduleException();
            }
        }
    }

    /**
     * 次のデータが存在するかどうかを判定します。<BR>
     * 帳票出力で使用します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        _current++;
        return _total > _current;
    }

    /**
     * データを1件返します。<BR>
     * 帳票出力で使用します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        Params p = new Params();

        p.set(MessageLogInquiryDASCHParams.DFK_DS_NO, getDsNumber());
        p.set(MessageLogInquiryDASCHParams.DFK_USER_ID, getUserId());
        p.set(MessageLogInquiryDASCHParams.DFK_USER_NAME, getUserName());
        p.set(MessageLogInquiryDASCHParams.SYS_DAY, getPrintDate());
        p.set(MessageLogInquiryDASCHParams.SYS_TIME, getPrintDate());

        p.set(MessageLogInquiryDASCHParams.MESSAGE_NO,
                _param.get(_current).get(MessageLogInquiryDASCHParams.MESSAGE_NO));
        p.set(MessageLogInquiryDASCHParams.LEVEL, _param.get(_current).get(MessageLogInquiryDASCHParams.LEVEL));
        p.set(MessageLogInquiryDASCHParams.CLASS_NAME,
                _param.get(_current).get(MessageLogInquiryDASCHParams.CLASS_NAME));
        p.set(MessageLogInquiryDASCHParams.MESSAGE, _param.get(_current).get(MessageLogInquiryDASCHParams.MESSAGE));
        p.set(MessageLogInquiryDASCHParams.DATE, _param.get(_current).get(MessageLogInquiryDASCHParams.DATE));
        p.set(MessageLogInquiryDASCHParams.NO, _current + 1);

        return p;
    }

    /**
     *
     * finder,Connection close
     */
    public void close()
    {
        if (_finder != null)
        {
            _finder.close();
        }
        super.close();
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
    /**
     * データ件数を返します。
     * 帳票発行、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @return データ件数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected int actualCount(Params startparam)
            throws CommonException
    {
        RandomAccessFile rAFile = null;
        BufferedReader br = null;

        String dateFormat = "yyyyMMddhhmmss";
        SimpleDateFormat logDtFmt = new SimpleDateFormat(dateFormat);

        String fileDate = "";
        // ファイルポインタ
        long l1 = 0;
        // ファイル内容格納List
        List<Params> params = new ArrayList<Params>();
        // 最新の日付順にセットしなおす。

        try
        {
            // パラメータの検索条件
            SystemInParameter wParam = new SystemInParameter();

            MessageLogInquiryDASCHParams startParam = (MessageLogInquiryDASCHParams)startparam;
            // 開始検索日
            Date fromDate = startParam.getDate(MessageLogInquiryDASCHParams.FROM_SEARCH_DAY);
            // 開始検索時刻
            Date fromTime = startParam.getDate(MessageLogInquiryDASCHParams.FROM_SEARCH_TIME);
            // 終了検索日
            Date toDate = startParam.getDate(MessageLogInquiryDASCHParams.TO_SEARCH_DAY);
            // 終了検索時刻
            Date toTime = startParam.getDate(MessageLogInquiryDASCHParams.TO_SEARCH_TIME);
            // 表示条件
            wParam.setDisplayCondition(startParam.getString(MessageLogInquiryDASCHParams.LOG));

            Date[] tmp = WmsFormatter.getFromTo(fromDate, fromTime, toDate, toTime);

            Date from = tmp[0];
            Date to = tmp[1];

            // 開始日、終了日を取得する
            String fromDateToken = this.changeDateFormat(from);
            String toDateToken = this.changeDateFormat(to);

            String fileData;
            //読み込むだけのRAFファイルを作成。
            rAFile = new RandomAccessFile(MESSAGE_LOG_PATH, "r");
            //ログ表示処理フラグ
            boolean flag = false;
            //開始検索時間が有る場合は印刷フラグをONにする。
            if (fromDateToken.length() == 19)
            {
                flag = true;
            }
            else
            {
                //開始検索時間が正常で無い場合はファイルポインタをゼロにする。
                l1 = rAFile.getFilePointer();
                //ファイルシークポイントをセット
                rAFile.seek(l1);
            }
            //先頭から識算子でログファイルを読み込んで入力開始時間とログファイルに有る時間を合わせる。
            while ((flag == true) && (!StringUtil.isBlank(fileData = rAFile.readLine())))
            {

                StringTokenizer sToken = new StringTokenizer(fileData, "\t");
                if (sToken.hasMoreTokens())
                {

                    try
                    {
                        // ファイルに書き込まれている日付を取得
                        StringBuilder buf = new StringBuilder();
                        // 年
                        buf.append(fileData.substring(0, 4));
                        // 月
                        buf.append(fileData.substring(5, 7));
                        // 日
                        buf.append(fileData.substring(8, 10));
                        // 時
                        buf.append(fileData.substring(11, 13));
                        // 分
                        buf.append(fileData.substring(14, 16));
                        // 秒
                        buf.append(fileData.substring(17, 19));

                        // ファイルに書き込まれている日付が削除日付よりも新しい場合
                        // ワークファイルに一時書き込み
                        String recordDate = String.valueOf(buf);

                        // ワーク日付をフォーマット
                        Date recDate = logDtFmt.parse(recordDate);

                        //ログファイルに有る開始時間が入力時間より大きかった場合はファイルポインタをセットする。
                        if ((fromDateToken.length() > 0) && from.compareTo(recDate) <= 0)
                        {
                            //シークポイントをセット（ピリオドでワイルドカードをセット？）し、最終行に戻り "\r"と "\n"をセットします。
                            try
                            {
                                rAFile.seek(l1);
                            }
                            catch (Exception e)
                            {
                                throw new ScheduleException(WmsMessageFormatter.format(6003011));
                            }
                            flag = false;
                        }

                    }
                    catch (ParseException e)
                    {
                        continue;
                    }
                    catch (StringIndexOutOfBoundsException e)
                    {
                        continue;
                    }


                }
                //ファイルポインタを保持
                l1 = rAFile.getFilePointer();
            }

            String bufferedData;
            //日本語を読み込む為にFileInputStreamリーダを作成する。
            br = new BufferedReader(new InputStreamReader(new FileInputStream(rAFile.getFD()), "utf-8"));
            //ログ表示処理フラグをONする

            flag = true;

            while (!StringUtil.isBlank(bufferedData = br.readLine()) && (flag == true))
            {
                StringTokenizer wDivide = new StringTokenizer(bufferedData, "\t");
                while ((wDivide.hasMoreElements()) && (flag == true))
                {
                    String t1 = wDivide.nextToken();
                    //最初のトークンから日時を取得（グラブ）します。
                    //                  StringTokenizer st1 = new StringTokenizer(t1, " ");

                    try
                    {
                        // ファイルに書き込まれている日付を取得
                        StringBuilder buf = new StringBuilder();
                        // 年
                        buf.append(t1.substring(0, 4));
                        // 月
                        buf.append(t1.substring(5, 7));
                        // 日
                        buf.append(t1.substring(8, 10));
                        // 時
                        buf.append(t1.substring(11, 13));
                        // 分
                        buf.append(t1.substring(14, 16));
                        // 秒
                        buf.append(t1.substring(17, 19));

                        // ファイルに書き込まれている日付が削除日付よりも新しい場合
                        // ワークファイルに一時書き込み
                        String recordDate = String.valueOf(buf);

                        // ワーク日付をフォーマット
                        Date recDate = logDtFmt.parse(recordDate);

                        //ログファイルに有る開始時間が入力時間より大きかった場合はファイルポインタをセットする。
                        if ((toDateToken.length() > 0) && to.compareTo(recDate) < 0)
                        {
                            flag = false;
                        }
                    }
                    catch (ParseException e)
                    {
                        continue;
                    }
                    catch (StringIndexOutOfBoundsException e)
                    {
                        continue;
                    }

                    if (flag)
                    {


                        // エラーコード
                        String wErrCode = "";
                        // ファシリティコード
                        String wFacilityCode = "";
                        // 発生クラス
                        String wClassInfo = "";

                        Date wWriteDate = null;

                        String wMessageLog = "";

                        Params p = new Params();

                        try
                        {

                            // エラーコード
                            wErrCode = wDivide.nextToken();
                            // ファシリティコード
                            wFacilityCode = wDivide.nextToken();
                            // 発生クラス
                            wClassInfo = wDivide.nextToken();
                            // パラメータ
                            int count = wDivide.countTokens();
                            String[] wParams = new String[count];
                            if (wDivide.hasMoreTokens())
                            {
                                for (int j = 0; j < count; j++)
                                {
                                    wParams[j] = wDivide.nextToken();
                                }
                            }
                            // 書込み日時をString型からDate型に変換する。
                            wWriteDate = new SimpleDateFormat(DATE_FORMAT).parse(t1, new ParsePosition(0));

                            // ファシリティコード表示用変数
                            String wDispFacility = "";
                            // 処理続行フラグ（True：処理続行 False：CONTINUEし次データ取得）
                            boolean wNext = false;
                            wDispFacility = changeFacility(wFacilityCode, wParam);
                            if (!StringUtil.isBlank(wDispFacility))
                            {
                                wNext = true;
                            }
                            // 続行フラグがONの場合、次の処理に進む
                            if (wNext)
                            {
                                // 開始日時、終了日時を取得
                                Date wStrDate = wParam.getSearchDate();
                                Date wEndDate = wParam.getToSearchDate();

                                wNext = checkDateTiem(wWriteDate, wStrDate, wEndDate);
                                // 続行フラグがONの場合、処理を続行
                                if (wNext)
                                {
                                    // 表示メッセージ件数カウンタに1プラス
                                    _total++;
                                }
                            }
                        }
                        catch (NoSuchElementException e)
                        {
                            continue;
                        }
                        catch (StringIndexOutOfBoundsException e)
                        {
                            continue;
                        }
                    }
                }
            }
            for (int i = params.size() - 1; i >= 0; i--)
            {
                _param.add(params.get(i));
            }
            setMessage(WmsMessageFormatter.format(6003011));
        }
        //ファイルオープンエラー
        catch (FileNotFoundException e)
        {
            // 6006020 = ファイルの入出力エラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getSimpleName());
            // 6006038 = 対象データはありませんでした。
            setMessage(WmsMessageFormatter.format(6006038));
            return FILE_ERROR;
        }
        // ファイル操作時エラー
        catch (IOException e)
        {
            // 6006020 = ファイルの入出力エラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getSimpleName());
            // 6007031 = ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            return FILE_ERROR;
        }
        catch (Exception e)
        {
            // 6006020 = ファイルの入出力エラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getSimpleName());
            // 6007031 = ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            throw new ScheduleException();
        }
        finally
        {
            try
            {
                if (br != null)
                {
                    // ファイルのクローズ
                    br.close();
                }
                //close raf
                if (rAFile != null)
                {
                    rAFile.close();
                }
            }
            catch (IOException e)
            {
                // 6006020 = ファイルの入出力エラーが発生しました。{0}
                RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
                // 6007031 = ファイルの入出力エラーが発生しました。ログを参照してください。
                setMessage(WmsMessageFormatter.format(6007031));
                throw new ScheduleException();
            }
        }

        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * 一覧表示で使用します。
     *
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        List<Params> params = new ArrayList<Params>();

        return params;
    }

    /**
     * メッセージセット
     *
     * @param isOverMaxDisplay エラーフラグ
     * @param wCnt 最大件数
     */
    protected void setMSG(boolean isOverMaxDisplay, int wCnt)
    {
        if (isOverMaxDisplay)
        {
            // 6001023={0}件該当しました。件数が{1}件を超えるため、{2}件の情報を表示しています。
            String msg =
                    WmsMessageFormatter.format(6021031, WmsFormatter.getNumFormat(wCnt),
                            WmsParam.MAX_NUMBER_OF_DISP_MESSAGE_LOG,
                            WmsFormatter.getNumFormat(WmsParam.MAX_NUMBER_OF_DISP_MESSAGE_LOG - 1));
            setMessage(msg);
        }
        else
        {
            // 6001013 = 表示しました。
            setMessage("6001013");
        }
    }


    /**
     * 日付、時間入力値のチェック
     *
     * @param wWriteDate ログ書き込み日時
     * @param wStrDate 入力開始時刻
     * @param wEndDate 入力終了時刻
     * @return boolean 入力条件の合否
     */
    protected boolean checkDateTiem(Date wWriteDate, Date wStrDate, Date wEndDate)
    {
        boolean wNext = true;
        // 日付チェック
        // 開始日時のみ入力
        if (!StringUtil.isBlank(wStrDate) && StringUtil.isBlank(wEndDate))
        {
            if (wStrDate.compareTo(wWriteDate) <= 0)
            {
                wNext = true;
            }
            else
            {
                wNext = false;
            }
        }
        // 終了日時のみ入力
        else if (StringUtil.isBlank(wStrDate) && !StringUtil.isBlank(wEndDate))
        {
            if (wEndDate.compareTo(wWriteDate) > 0)
            {
                wNext = true;
            }
            else
            {
                wNext = false;
            }
        }
        // 開始日時、終了日時両方入力
        else if (!StringUtil.isBlank(wStrDate) && !StringUtil.isBlank(wEndDate))
        {
            if (wStrDate.compareTo(wEndDate) == 0)
            {
                wNext = true;
            }
            else
            {
                if (wStrDate.compareTo(wWriteDate) < 0 && wEndDate.compareTo(wWriteDate) > 0)
                {
                    wNext = true;
                }
                else
                {
                    wNext = false;
                }
            }
        }
        // 日時入力無し
        else if (StringUtil.isBlank(wStrDate) && StringUtil.isBlank(wEndDate))
        {
            wNext = true;
        }
        return wNext;
    }

    /**
     * 表示条件の判別を行います。
     *
     *
     *
     * @param wFacilityCode ログファイル内ファシリティ
     * @param wParam 入力条件ファシリティ
     * @return String 表示条件
     */
    protected String changeFacility(String wFacilityCode, SystemInParameter wParam)
    {
        String wDispFacility = "";

        // 全て表示
        if (SystemInParameter.DISPLAY_CONDITION_ALL.equals(wParam.getDisplayCondition()))
        {
            // ファシリティコード表示用変数にSystemParameterに定義された値をセット
            if (LogMessage.F_DEBUG.equals(wFacilityCode))
            {
                wDispFacility = SystemInParameter.DISPLAY_CONDITION_ALL;
            }
            else if (LogMessage.F_INFO.equals(wFacilityCode))
            {
                // 案内
                wDispFacility = SystemInParameter.DISPLAY_CONDITION_INFORMATION;
            }
            else if (LogMessage.F_NOTICE.equals(wFacilityCode))
            {
                // 注意
                wDispFacility = SystemInParameter.DISPLAY_CONDITION_ATTENTION;
            }
            else if (LogMessage.F_WARN.equals(wFacilityCode))
            {
                // 警告
                wDispFacility = SystemInParameter.DISPLAY_CONDITION_WARNING;
            }
            else if (LogMessage.F_ERROR.equals(wFacilityCode))
            {
                // 異常
                wDispFacility = SystemInParameter.DISPLAY_CONDITION_ERROR;
            }
        }
        // 案内のみ表示
        else if (SystemInParameter.DISPLAY_CONDITION_INFORMATION.equals(wParam.getDisplayCondition()))
        {
            if (LogMessage.F_INFO.equals(wFacilityCode))
            {
                wDispFacility = SystemInParameter.DISPLAY_CONDITION_INFORMATION;
            }
            else
            {
                wDispFacility = null;
            }
        }
        // 注意のみ表示
        else if (SystemInParameter.DISPLAY_CONDITION_ATTENTION.equals(wParam.getDisplayCondition()))
        {
            if (wFacilityCode.equals(LogMessage.F_NOTICE))
            {
                wDispFacility = SystemInParameter.DISPLAY_CONDITION_ATTENTION;
            }
            else
            {
                wDispFacility = null;
            }
        }
        // 警告のみ表示
        else if (SystemInParameter.DISPLAY_CONDITION_WARNING.equals(wParam.getDisplayCondition()))
        {
            if (wFacilityCode.equals(LogMessage.F_WARN))
            {
                wDispFacility = SystemInParameter.DISPLAY_CONDITION_WARNING;
            }
            else
            {
                wDispFacility = null;
            }
        }
        // 異常のみ表示
        else if (SystemInParameter.DISPLAY_CONDITION_ERROR.equals(wParam.getDisplayCondition()))
        {
            if (wFacilityCode.equals(LogMessage.F_ERROR))
            {
                wDispFacility = SystemInParameter.DISPLAY_CONDITION_ERROR;
            }
            else
            {
                wDispFacility = null;
            }
        }
        return wDispFacility;
    }

    /**
     * 日付と時間の文字列を"yyyy MM DD HH mm ss"フォーマットにして連結します。
     * @param searchDate 検索日時
     * @return 日付と時間の文字列を連結した値
     */
    protected String changeDateFormat(Date searchDate)
    {
        String dateStr = WmsFormatter.toParamDate(searchDate);
        String timeStr = WmsFormatter.toParamTime(searchDate);

        String date_s = "";
        String deLimiter = " ";

        if (dateStr.length() == 8)
        {
            date_s =
                    dateStr.substring(0, 4) + deLimiter + dateStr.substring(4, 6) + deLimiter + dateStr.substring(6, 8);

            if (timeStr.length() == 6)
            {
                date_s +=
                        deLimiter + timeStr.substring(0, 2) + deLimiter + timeStr.substring(2, 4) + deLimiter
                                + timeStr.substring(4, 6);
            }
            else
            {
                date_s += deLimiter + "00" + deLimiter + "00" + deLimiter + "00";
            }
        }
        return date_s;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}

//end of class
