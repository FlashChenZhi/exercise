package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.system.schedule.MessageLogInquirySCHParams.CLASS;
import static jp.co.daifuku.wms.system.schedule.MessageLogInquirySCHParams.CONTENT;
import static jp.co.daifuku.wms.system.schedule.MessageLogInquirySCHParams.LOG;
import static jp.co.daifuku.wms.system.schedule.MessageLogInquirySCHParams.LOG_DATE;
import static jp.co.daifuku.wms.system.schedule.MessageLogInquirySCHParams.MESSAGE;
import static jp.co.daifuku.wms.system.schedule.MessageLogInquirySCHParams.SEARCH_END_DAY;
import static jp.co.daifuku.wms.system.schedule.MessageLogInquirySCHParams.SEARCH_END_TIME;
import static jp.co.daifuku.wms.system.schedule.MessageLogInquirySCHParams.SEARCH_START_DAY;
import static jp.co.daifuku.wms.system.schedule.MessageLogInquirySCHParams.SEARCH_START_TIME;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.util.Converter;
import jp.co.daifuku.bluedog.webapp.ThreadManager;
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
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * ログ照会のスケジュール処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:52:43 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: nagao $
 */
public class MessageLogInquirySCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * メッセージログファイル名
     */
    protected static final String MESSAGE_LOG_PATH = WmsParam.LOGS_PATH + "/" + WmsParam.MESSAGELOG_FILE;

    /**
     * ログ日付のフォーマット形式
     */
    protected static final String DATE_FORMAT = "yyyy MM dd HH mm ss SSS z";

    /**
     * デフォルトリソース
     */
    private static String wResource = "wms";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    /**
     * 読み込むPropertiesファイルの名前です。<code>.properties</code>
     * は含みません。
     */
    private String baseName = null;

    /**
     * <code>java.text.MessageFormat</code>のキャッシュ用Mapです。
     * キーはロケールとリソースキーを結合したものです。
     */
    @SuppressWarnings("rawtypes")
    protected Map formats = new HashMap();

    /**
     * ファイルから読み込んだデータのキャッシュ用Mapです。
     * キーはロケールです。
     */
    protected Map<Locale, Properties> cacheMap = new HashMap<Locale, Properties>();

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    @SuppressWarnings("rawtypes")
    public MessageLogInquirySCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param startParams 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    @Override
    public List<Params> query(ScheduleParams startParams)
            throws CommonException
    {
        RandomAccessFile rAFile = null;
        BufferedReader br = null;

        String dateFormat = "yyyyMMddhhmmss";
        SimpleDateFormat logDtFmt = new SimpleDateFormat(dateFormat);

        // ファイルポインタ
        long l1 = 0;
        // ファイル内容格納List
        List<Params> params = new ArrayList<Params>();
        // 最新の日付順にセットしなおす。
        List<Params> param = new ArrayList<Params>();
        // 表示メッセージ件数カウンタ
        int wCnt = 0;
        try
        {
            // パラメータの検索条件
            SystemInParameter wParam = new SystemInParameter(getWmsUserInfo());

            MessageLogInquirySCHParams startParam = (MessageLogInquirySCHParams)startParams;
            // 開始検索日
            Date fromDate = startParam.getDate(SEARCH_START_DAY);
            // 開始検索時刻
            Date fromTime = startParam.getDate(SEARCH_START_TIME);
            // 終了検索日
            Date toDate = startParam.getDate(SEARCH_END_DAY);
            // 終了検索時刻
            Date toTime = startParam.getDate(SEARCH_END_TIME);
            // 表示条件
            wParam.setDisplayCondition(startParam.getString(LOG));

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
            // 表示件数が最大数を越えたかどうかのフラグ
            boolean isOverMaxDisplay = false;
            while (!StringUtil.isBlank(bufferedData = br.readLine()) && (flag == true))
            {
                StringTokenizer wDivide = new StringTokenizer(bufferedData, "\t");
                while ((wDivide.hasMoreElements()) && (flag == true))
                {
                    String t1 = wDivide.nextToken();
                    //最初のトークンから日時を取得（グラブ）します。
                    //                    StringTokenizer st1 = new StringTokenizer(t1, " ");

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

                            try
                            {
                                // 2010/07/29 Y.Osawa UPD ST
                                //                            //リソースタイプの選別（eWareNavi or WCS）
                                //                            String resourcetype = wErrCode.substring(1, 3);
                                //                            //機種コード：05，55･･･WCS
                                //                            if (resourcetype.equals(SystemDefine.RESOURCE_WCS)
                                //                                    || resourcetype.equals(SystemDefine.RESOURCE_WCS_CUSTOM))
                                //                            {
                                //                                // メッセージを取得する
                                //                                wMessageLog = SimpleFormat.format(getText(wErrCode, "", "wcs"), wParams);
                                //                            }
                                //                            //機種コード：その他･･･eWareNavi
                                //                            else
                                //                            {
                                //                                // メッセージを取得する
                                //                                wMessageLog = SimpleFormat.format(MessageResource.getMessage(wErrCode), wParams);
                                //                            }
                                String msg = "";
                                //リソースタイプの選別（eWareNavi or WCS）
                                String resourcetype = wErrCode.substring(1, 3);
                                //機種コード：05，55･･･WCS
                                if (resourcetype.equals(SystemDefine.RESOURCE_WCS)
                                        || resourcetype.equals(SystemDefine.RESOURCE_WCS_CUSTOM))
                                {
                                    // メッセージを取得する
                                    msg = getText(wErrCode, "", "wcs");
                                }
                                //機種コード：その他･･･eWareNavi
                                else
                                {
                                    // メッセージを取得する
                                    msg = MessageResource.getMessage(wErrCode);
                                }

                                if (StringUtil.isBlank(msg))
                                {
                                    // フォーマットエラー、リソース番号が存在しなかった場合はメッセージにエラーコードをセットする
                                    wMessageLog = wErrCode;
                                }
                                else
                                {
                                    // フォーマット
                                    wMessageLog = SimpleFormat.format(msg, wParams);
                                }
                            }
                            catch (MissingResourceException e)
                            {
                                //                            // リソース番号が無かった場合メッセージを空白にする
                                //                            wMessageLog = "";
                                // リソース番号が無かった場合メッセージにエラーコードをセットする
                                wMessageLog = wErrCode;
                                // 2010/07/29 Y.Osawa UPD ED
                            }
                            // 2010/07/29 Y.Osawa ADD ST
                            catch (FileNotFoundException e)
                            {
                                // リソースファイル読み込み時にエラーが発生した場合はメッセージにエラーコードをセットする
                                wMessageLog = wErrCode;
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

                        // 2010/07/29 Y.Osawa ADD ED
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
                                wCnt++;
                                // 最大表示件数を超えた場合
                                if (wCnt > WmsParam.MAX_NUMBER_OF_DISP_MESSAGE_LOG)
                                {
                                    // 最大表示件数をこえたためフラグをONにする
                                    isOverMaxDisplay = true;
                                    // 先頭データ(一番古いデータ)を削除する
                                    params.remove(0);
                                }
                                // 表示するデータを返却パラメータにセットする。
                                p.set(LOG_DATE, wWriteDate);

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
                                p.set(CONTENT, sdcondition);
                                p.set(CLASS, wClassInfo);
                                p.set(MESSAGE, wMessageLog);
                                params.add(p);
                            }
                        }
                    }
                }
            }
            for (int i = params.size() - 1; i >= 0; i--)
            {
                param.add(params.get(i));
            }
            if (wCnt > 0)
            {
                setMSG(isOverMaxDisplay, wCnt);
            }
            else
            {
                setMessage(WmsMessageFormatter.format(6003011));
            }
            return param;
        }
        //ファイルオープンエラー
        catch (FileNotFoundException e)
        {
            // 6006020 = ファイルの入出力エラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getSimpleName());
            // 6006038 = 対象データはありませんでした。
            setMessage(WmsMessageFormatter.format(6006038));
            return null;
        }
        // ファイル操作時エラー
        catch (IOException e)
        {
            // 6006020 = ファイルの入出力エラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getSimpleName());
            // 6007031 = ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            return null;
        }
        // その他のエラー
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
     * カレントスレッドのリクエストロケールに対応する
     * プロパティーファイルから、引数keyのメッセージを取得し、
     * {0}等を引数の置換文字で置換処理をした文字列を返します。
     * 直接指定時は置換文字は4つまで指定可能であり、対応する{0}は～{3}まで指定可能です。
     * 置換文字は配列で指定することも可能です。
     * @param key キー文字列
     * @param arg0 {0}に対応する置換文字列
     * @param arg1 {1}に対応する置換文字列
     * @return 置換後の文字列
     * @throws java.lang.NullPointerException
     *     <code>baseName</code>か<code>locale</code>が<code>null</code>の場合
     * @throws MissingResourceException
     *     リソースが見つからないか、リソースにキーが存在しない場合
     * @throws FileNotFoundException
     *     指定されたパス名で示されるファイルが開けなかったことを通知する
     */
    @SuppressWarnings("javadoc")
    public String getText(String key, Object arg, String rsctype)
            throws NullPointerException,
                MissingResourceException,
                FileNotFoundException
    {
        return getText(ThreadManager.getClientLocale(), key, arg, rsctype);
    }

    /**
     * 引数で指定されたロケールに対応するプロパティーファイルから、
     * 引数keyのメッセージを取得し、
     * {0}等を引数の置換文字で置換処理をした文字列を返します。<br>
     * 置換処理は<code>java.text.MessageFormat</code>を使用します。
     * <code>MessageFormat</code>のインスタンスはロケールと
     * リソースのキーを結合したものをキーとしてMapにキャッシュされます。
     * @param locale ロケール
     * @param key リソースキー
     * @param args 置換文字列の配列
     * @return 置換後の文字列
     * @throws java.lang.NullPointerException
     *     <code>baseName</code>か<code>locale</code>が<code>null</code>の場合
     * @throws MissingResourceException
     *     リソースが見つからないか、リソースにキーが存在しない場合
     * @throws FileNotFoundException
     *     指定されたパス名で示されるファイルが開けなかったことを通知する
     */
    @SuppressWarnings("javadoc")
    public String getText(Locale locale, String key, Object args, String rsctype)
            throws NullPointerException,
                MissingResourceException,
                FileNotFoundException
    {
        wResource = rsctype;

        MessageFormat format = null;
        String formatKey = messageKey(locale, key);
        synchronized (formats)
        {
            format = (MessageFormat)formats.get(formatKey);
            if (format == null)
            {
                String formatString = getText(locale, key);
                if (formatString == null)
                {
                    /*
                     リソースから値を取得できなかった場合
                     */
                    return null;
                }
                format = new MessageFormat(escape(formatString));

                return formatString;
            }
        }
        setResource(rsctype);
        return format.format(args);
    }

    /**
     * 引数で指定されたロケールに対応する
     * ファイルから、引数keyのメッセージを取得します。
     * リソースにより拡張子の変更(eAWC=プロパティファイル、WCS=テキストファイル)
     * @param locale ロケール
     * @param key リソースキー
     * @return リソース文字列
     * @throws java.lang.NullPointerException
     *     <code>locale</code>が<code>null</code>の場合
     * @throws MissingResourceException
     *     リソースが見つからないか、リソースにキーが存在しない場合
     * @throws FileNotFoundException
     *     指定されたパス名で示されるファイルが開けなかったことを通知する
     */
    public String getText(Locale locale, String key)
            throws NullPointerException,
                MissingResourceException,
                FileNotFoundException
    {
        if (locale == null)
        {
            throw new NullPointerException("[locale] is null.");
        }

        Properties p = null;

        // ロケールに対応するファイルの情報をキャッシュしている場合はキャッシュから値を取得します。
        if (cacheMap.containsKey(locale))
        {
            p = cacheMap.get(locale);
        }
        else
        {
            p = new Properties();
            // ロケールに対応するファイルを読み込み、Mapにキャッシュします。
            synchronized (cacheMap)
            {
                getResource();
                String rsctype = wResource;

                InputStream rin = null;
                if (rsctype == "wcs")
                {
                    rin = new FileInputStream(new File(WmsParam.RESOURCE_WCS));
                }
                else
                {
                    rin = new FileInputStream(new File(WmsParam.RESOURCE_WMS));
                }

                BufferedInputStream bin = null;

                try
                {
                    bin = new BufferedInputStream(rin);
                    p.load(bin);
                    // 取得した情報をキャッシュします。
                    cacheMap.put(locale, p);
                }
                catch (IOException e)
                {
                    final String className = this.getClass().getName();
                    final String pattern = escape("Can't read bundle for base name {0}, locale {1}");
                    final MessageFormat format = new MessageFormat(pattern);
                    Object[] arguments = new Object[] {
                            this.baseName,
                            locale
                    };
                    String message = format.format(arguments);
                    throw new MissingResourceException(message, className, key);
                }
                finally
                {
                    try
                    {
                        rin.close();
                        if (bin != null)
                        {
                            bin.close();
                        }
                    }
                    catch (Exception e)
                    {
                        //
                    }
                }
            }
        }

        if (!p.containsKey(key))
        {
            return null;
        }

        return p.getProperty(key);
    }

    // リソースの種類を保持
    @SuppressWarnings({
            "static-method",
            "javadoc"
    })
    public void setResource(String rsctype)
    {
        wResource = rsctype;
    }

    @SuppressWarnings({
            "static-method",
            "javadoc"
    })
    public String getResource()
    {
        return wResource;
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
     * 日付、時間入力値のチェック
     *
     * @param wWriteDate ログ書き込み日時
     * @param wStrDate 入力開始時刻
     * @param wEndDate 入力終了時刻
     * @return boolean 入力条件の合否
     */
    @SuppressWarnings("static-method")
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
    @SuppressWarnings("static-method")
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
    @SuppressWarnings("static-method")
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

    /**
     * メッセージセット
     *
     * @param isOverMaxDisplay エラーフラグ
     * @param wCnt 最大件数
     */
    @SuppressWarnings({
            "static-access",
            "boxing"
    })
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

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * <code>java.text.MessageFormat</code>のキャッシュ用Mapのキーを生成します。
     * キーは<code>locale</code>と<code>key</code>を「<code>.</code>」で
     * 結合したものです。
     * <code>locale</code>が<code>null</code>の場合は「<code>.</code>」と
     * <code>key</code>が結合されます。
     * @param locale 参照するリソースのロケール
     * @param key リソースキー
     * @return 生成した文字列
     */
    @SuppressWarnings("static-method")
    private String messageKey(Locale locale, String key)
    {
        return (Converter.nullToStr(locale, "") + "." + key);
    }

    /**
     * <code>java.text.MessageFormat</code>のコンストラクタ用に
     * シングルクォートをエスケープします。
     * @param string エスケープ対象文字列
     * @return エスケープ後文字列
     */
    @SuppressWarnings("static-method")
    private String escape(String string)
    {
        if ((string == null) || (string.indexOf('\'') < 0)) return (string);
        int n = string.length();
        StringBuffer sb = new StringBuffer(n);
        for (int i = 0; i < n; i++)
        {
            char ch = string.charAt(i);
            if (ch == '\'') sb.append('\'');
            sb.append(ch);
        }
        return (sb.toString());
    }

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
