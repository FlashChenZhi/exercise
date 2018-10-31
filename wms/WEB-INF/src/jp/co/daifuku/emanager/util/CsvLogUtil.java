// $Id
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

package jp.co.daifuku.emanager.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.daifuku.bluedog.ui.control.Message;
import jp.co.daifuku.emanager.database.entity.LogInfo;
import jp.co.daifuku.emanager.database.handler.EmConnectionHandler;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.LogHandler;
import jp.co.daifuku.emanager.database.handler.LogTempHandler;


/**
 * <jp>認証ログ・メンテナンスログのCSVエクスポート・インポートを行うクラスです。 <br></jp>
 * <en>It is a class that does the CSV export import of authenticationlog and maintenancelog <br></en>
 * <br>
 * <table border="1" cellpadding="3" cellspacing="0">
 * <tr bgcolor="#CCCCFF" class="TableHeadingColor"><td>Date</td><td>Name</td><td>Comment</td></tr>
 * <tr><td>2004/06/23</td><td>M.Kawashima</td><td>created this class</td></tr>
 * </table>
 * <br>
 * @version $Revision
 * @author  $author$
 */
public class CsvLogUtil
{
    /**<jp>CSV出力フォルダのパスを格納する。 &nbsp;&nbsp;</jp><en>Passing output folder is stored. &nbsp;&nbsp;</en> */
    private static final String CSV_EXPORT_PATH = "CsvLogExportPath";

    /**<jp>CSV入力フォルダのパスを格納する。 &nbsp;&nbsp;</jp><en>Passing input folder is stored. &nbsp;&nbsp;</en> */
    private static final String CSV_IMPORT_PATH = "CsvLogImportPath";

    /**<jp>認証ログのテーブル名。 &nbsp;&nbsp;</jp><en>It is a table name of authenticationlog. &nbsp;&nbsp;</en> */
    public static final String AUTH_LOG_TABLE = "COM_AUTHENTICATIONLOG";

    /**<jp>メンテナンスログのテーブル名。 &nbsp;&nbsp;</jp><en>It is a table name of maintenancelog. &nbsp;&nbsp;</en> */
    public static final String MAINTENANCE_LOG_TABLE = "COM_MAINTENANCELOG";

    /**<jp>CSVファイルの区切り文字です。 &nbsp;&nbsp;</jp><en>It is a delimiter of the CSV file. &nbsp;&nbsp;</en> */
    protected static final String DELIM_CHAR = ",";

    /**<jp>CSVファイルの置換文字です。 &nbsp;&nbsp;</jp><en>It is a substitution character of the CSV file. &nbsp;&nbsp;</en> */
    protected static final String REPLACE_CHAR = "@@@";

    /**<jp>CSVファイルの改行文字です。 &nbsp;&nbsp;</jp><en>It is a line-feed character of the CSV file. &nbsp;&nbsp;</en> */
    protected static final String RETURN_CODE = "\r";

    /**<jp>CSVファイルの列数です。 &nbsp;&nbsp;</jp><en>It is a number of rows of CSV files. &nbsp;&nbsp;</en> */
    protected static final int FIELD_CNT = 11;

    /**
     * <jp>テーブルの情報をCSVファイルにエクスポートします。<br></jp>
     * <en>Information on Table is exported to the CSV file. <br></en>
     * @param tableName <jp>テーブル名 &nbsp;&nbsp;</jp><en>Table Name &nbsp;&nbsp;</en>
     * @param fileName <jp>ファイル名 &nbsp;&nbsp;</jp><en>File Name &nbsp;&nbsp;</en>
     * @throws SQLException 実行したSQLに問題が発生した場合にスローされます。
     * @throws IOException 入出力例外が発生した場合にスローされます。
     * @throws NullPointerException NULL値を参照しようとした場合にスローされます。
     * @throws Exception 何らかの例外が発生した場合にスローされます。
     * @return <jp>TRUE を返却します。 &nbsp;&nbsp;</jp><en>Return true &nbsp;&nbsp;</en>
     */
    public synchronized static boolean exportCsv(String tableName, String fileName)
            throws SQLException,
                IOException,
                NullPointerException,
                Exception
    {
        Connection conn = null;
        LogInfo[] logInfo = null;
        String exportPath = EmProperties.getProperty(CSV_EXPORT_PATH);

        // <jp>出力場所のチェック</jp>
        // <en>Check on output place.</en>
        File dir = new File(exportPath);

        // <jp>存在するか？</jp>
        // <en>Do it exist?</en>
        if (!dir.exists())
        {
            String message = "6407005" + Message.MSG_DELIM + exportPath;
            throw new Exception(message);

        }

        // <jp>ディレクトリか？</jp>
        // <en>Is it a directory?</en>
        if (!dir.isDirectory())
        {
            String message = "6407003" + Message.MSG_DELIM + exportPath;
            throw new Exception(message);
        }

        try
        {
            conn = EmConnectionHandler.getConnection();
            LogHandler logHandler = EmHandlerFactory.getLogHandler(conn);

            // <jp>認証ログ検索</jp>
            // <en>authenticationlog retrieval.</en>
            if (tableName.equals(CsvLogUtil.AUTH_LOG_TABLE))
            {
                logInfo = logHandler.findAllAuthLog();
            }

            // <jp>メンテナンスログ検索</jp>
            // <en>maintenancelog retrieval.</en>
            if (tableName.equals(CsvLogUtil.MAINTENANCE_LOG_TABLE))
            {
                logInfo = logHandler.findAllMainteLog();
            }
        }
        catch (SQLException ede)
        {
            throw ede;
        }

        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

        // <jp>検索結果が無ければ終了</jp>
        // <en>It ends if there is no retrieval result.</en>
        if (logInfo == null)
        {
            throw new NullPointerException("6401014");
        }


        // <jp>ファイル名作成</jp>
        // <en>File name making.</en>
        //	String fileName = tableName + "_" + CsvLogUtil.makeFileName() + ".csv";

        try
        {
            FileWriter fw = new FileWriter(dir.getPath() + "/" + fileName);
            BufferedWriter wr = new BufferedWriter(fw);

            for (int cnt = 0; cnt < logInfo.length; cnt++)
            {
                LogInfo info = logInfo[cnt];

                String logData = info.getLogDate().toString() == null ? " "
                                                                     : info.getLogDate().toString();
                String userID = info.getUserId() == null ? " "
                                                        : info.getUserId();
                String ipAddress = info.getIpAddress() == null ? " "
                                                              : info.getIpAddress();
                String logClass = String.valueOf(info.getLogClass()) == null ? " "
                                                                            : String.valueOf(info.getLogClass());
                String message = info.getMessage() == null ? " "
                                                          : info.getMessage();
                String detail = info.getDetails() == null ? " "
                                                         : info.getDetails();
                String updateDate = info.getUpdateDate().toString() == null ? " "
                                                                           : info.getUpdateDate().toString();
                String updateUser = info.getUpdateUser() == null ? " "
                                                                : info.getUpdateUser();
                String updateTerminal = info.getUpdateTerminal() == null ? " "
                                                                        : info.getUpdateTerminal();
                String updateKind = String.valueOf(info.getUpdateKind()) == null ? " "
                                                                                : String.valueOf(info.getUpdateKind());
                String updateProcess = info.getUpdateProcess() == null ? " "
                                                                      : info.getUpdateProcess();

                // <jp>ファイルストリームに書き出し</jp>
                // <en>It writes it to the file stream.</en>
                wr.write(logData);
                wr.write(DELIM_CHAR);

                wr.write(userID);
                wr.write(DELIM_CHAR);

                wr.write(ipAddress);
                wr.write(DELIM_CHAR);

                wr.write(logClass);
                wr.write(DELIM_CHAR);

                // <jp>置換処理</jp>
                // <en>Substitution processing</en>
                Pattern pattern = Pattern.compile(DELIM_CHAR);
                Matcher matcher = pattern.matcher(message);
                message = matcher.replaceAll(REPLACE_CHAR);

                wr.write(message);
                wr.write(DELIM_CHAR);

                matcher = pattern.matcher(detail);
                detail = matcher.replaceAll(REPLACE_CHAR);

                wr.write(detail);
                wr.write(DELIM_CHAR);

                wr.write(updateDate);
                wr.write(DELIM_CHAR);

                wr.write(updateUser);
                wr.write(DELIM_CHAR);

                wr.write(updateTerminal);
                wr.write(DELIM_CHAR);

                wr.write(updateKind);
                wr.write(DELIM_CHAR);

                wr.write(updateProcess);

                wr.write(RETURN_CODE);
            }

            wr.flush();

            // <jp>ファイルクローズ処理</jp>
            // <en>File close processing</en>
            wr.close();
            fw.close();
        }
        catch (IOException ioe)
        {
            throw new IOException("6407004");
        }

        try
        {
            conn = EmConnectionHandler.getConnection();
            LogHandler logHandler = EmHandlerFactory.getLogHandler(conn);

            // <jp>認証ログ削除</jp>
            // <en>authenticationlog deletion</en>
            if (tableName.equals(CsvLogUtil.AUTH_LOG_TABLE))
            {
                logHandler.deleteAuthLogByDates(logInfo[logInfo.length - 1].getLogDate(), logInfo[0].getLogDate());
            }

            // <jp>メンテナンスログ削除</jp>
            // <en>maintenancelog deletion</en>
            if (tableName.equals(CsvLogUtil.MAINTENANCE_LOG_TABLE))
            {
                logHandler.deleteMaitenLogByDates(logInfo[logInfo.length - 1].getLogDate(), logInfo[0].getLogDate());
            }
            conn.commit();
        }
        catch (SQLException ede)
        {
            throw ede;
        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);
        }

        return true;
    }

    /**
     * <jp>CSVファイルの情報をテーブルにインポートします。<br></jp>
     * <en>Information on the CSV file is done and the import is done to the table. <br></en>
     * @param fileName <jp>ファイル名 &nbsp;&nbsp;</jp><en>File Name &nbsp;&nbsp;</en>
     * @param user <jp>ユーザ名 &nbsp;&nbsp;</jp><en>User Name &nbsp;&nbsp;</en>
     * @param terminal <jp>端末名 &nbsp;&nbsp;</jp><en>Terminal Name &nbsp;&nbsp;</en>
     * @throws FileNotFoundException ファイルが存在しなかった場合にスローされます。
     * @throws IOException 入出力例外が発生した場合にスローされます。
     * @throws NumberFormatException 数字の変換に失敗した場合にスローされます。
     * @throws SQLException 実行したSQLに問題が発生した場合にスローされます。
     * @throws Exception 何らかの例外が発生した場合にスローされます。
     * @return <jp>TRUE を返却します。 &nbsp;&nbsp;</jp><en>Return true &nbsp;&nbsp;</en>
     */
    public synchronized static boolean importCsv(String fileName, String user, String terminal)
            throws FileNotFoundException,
                IOException,
                NumberFormatException,
                SQLException,
                Exception
    {
        LogInfo info = new LogInfo();

        // <jp>出力先を取得</jp>
        // <en>The output destination is acquired.</en>
        String exportPath = EmProperties.getProperty(CSV_IMPORT_PATH);

        Connection conn = null;
        int conter = 0;

        BufferedReader br = null;
        try
        {
            // <jp>ファイルオープン処理</jp>
            // <en>File opening processing</en>
            FileReader fr = new FileReader(exportPath + "/" + fileName);
            br = new BufferedReader(fr);

            // <jp>読み込み日時の作成</jp>
            // <en>Making at reading date</en>
            Calendar calendar = Calendar.getInstance();
            Timestamp time = new Timestamp(calendar.getTimeInMillis());

            // <jp>コネクション取得</jp>
            // <en>Connection acquisition</en>
            conn = EmConnectionHandler.getConnection();
            LogTempHandler handler = EmHandlerFactory.getLogTempHandler(conn);

            // <jp>認証ログテーブル</jp>
            // <en>authenticationlog table</en>
            if (fileName.indexOf(AUTH_LOG_TABLE) >= 0)
            {
                LogInfo[] logInfos = handler.findAllAuthLogbyDistinct();

                if (logInfos != null)
                {
                    for (int cnt = 0; cnt < logInfos.length; cnt++)
                    {
                        LogInfo logInfo = logInfos[cnt];

                        if (logInfo.getFileName().equals(fileName))
                        {
                            return false;
                        }
                    }
                }
            }

            // <jp>メンテナンスログテーブル</jp>
            // <en>maintenancelog table</en>
            if (fileName.indexOf(MAINTENANCE_LOG_TABLE) >= 0)
            {
                LogInfo[] logInfos = handler.findAllMainteLogbyDistinct();

                if (logInfos != null)
                {
                    for (int cnt = 0; cnt < logInfos.length; cnt++)
                    {
                        LogInfo logInfo = logInfos[cnt];

                        if (logInfo.getFileName().equals(fileName))
                        {
                            return false;
                        }
                    }
                }
            }


            while (br.ready())
            {
                conter = 0;

                // <jp>1行読込</jp>
                // <en>One line reading</en>
                String line = br.readLine();
                StringTokenizer st = new StringTokenizer(line, DELIM_CHAR);

                // <jp>整合性確認</jp>
                // <en>Adjustment Check.</en>
                if (st.countTokens() != FIELD_CNT)
                {
                    throw new Exception();
                }

                while (st.hasMoreTokens())
                {
                    // <jp>データ取得</jp>
                    // <en>Data acquisition</en>
                    String data = st.nextToken();

                    // <jp>出力日時</jp>
                    // <en>LOGDATE</en>
                    if (conter == 0)
                    {
                        // 日付取得
                        Calendar logDate = CsvLogUtil.strToCalendar(data);
                        Timestamp logtime = new Timestamp(logDate.getTimeInMillis());
                        info.setLogDate(logtime);
                    }
                    // <jp>ユーザID</jp>
                    // <en>USERID</en>
                    if (conter == 1)
                    {
                        info.setUserId(data);
                    }
                    // <jp>IPアドレス</jp>
                    // <en>IPADDRESS</en>
                    if (conter == 2)
                    {
                        info.setIpAddress(data);
                    }
                    // <jp>ログ区分</jp>
                    // <en>LOGCLASS</en>
                    if (conter == 3)
                    {
                        info.setLogClass(Integer.parseInt(data));
                    }
                    // <jp>メッセージ</jp>
                    // <en>MESSAGE</en>
                    if (conter == 4)
                    {
                        // <jp>置換処理</jp>
                        // <en>Substitution processing</en>
                        Pattern pattern = Pattern.compile(REPLACE_CHAR);
                        Matcher matcher = pattern.matcher(data);
                        data = matcher.replaceAll(DELIM_CHAR);

                        info.setMessage(data);
                    }
                    // <jp>詳細</jp>
                    // <en>DETAIL</en>
                    if (conter == 5)
                    {
                        // <jp>置換処理</jp>
                        // <en>Substitution processing</en>
                        Pattern pattern = Pattern.compile(REPLACE_CHAR);
                        Matcher matcher = pattern.matcher(data);
                        data = matcher.replaceAll(DELIM_CHAR);

                        info.setDetails(data);
                    }
                    // <jp>更新日時</jp>
                    // <en>UPDATE_DATE</en>
                    if (conter == 6)
                    {
                        info.setUpdateDate(time);
                    }
                    // <jp>更新ユーザ</jp>
                    // <en>UPDATE_USER</en>
                    if (conter == 7)
                    {
                        info.setUpdateUser(user);
                    }
                    // <jp>更新端末IP</jp>
                    // <en>UPDATE_TERMINAL</en>
                    if (conter == 8)
                    {
                        info.setUpdateTerminal(terminal);
                    }
                    // <jp>更新区分</jp>
                    // <en>UPDATE_KIND</en>
                    if (conter == 9)
                    {
                        info.setUpdateKind(1);
                    }
                    // <jp>更新処理名</jp>
                    // <en>UPDATE_PROCESS</en>
                    if (conter == 10)
                    {
                        info.setUpdateProcess("EmLogUtils");
                    }

                    conter++;
                }

                // <jp>ファイル名登録</jp>
                // <en>File name registration</en>
                info.setFileName(fileName);

                // <jp>認証ログテーブル</jp>
                // <en>authenticationlog table</en>
                if (fileName.indexOf(AUTH_LOG_TABLE) >= 0)
                {
                    handler.createAuthLogTemp(info);
                }

                // <jp>メンテナンスログテーブル</jp>
                // <en>maintenancelog table</en>
                if (fileName.indexOf(MAINTENANCE_LOG_TABLE) >= 0)
                {
                    handler.createMainteLogTemp(info);
                }
            }

            // <jp>コミット</jp>
            // <en>commit</en>
            conn.commit();
        }
        catch (FileNotFoundException e)
        {
            if (conn != null)
            {
                try
                {
                    conn.rollback();
                    throw e;
                }
                catch (SQLException se)
                {
                    throw se;
                }
            }
        }
        catch (IOException e)
        {
            if (conn != null)
            {
                try
                {
                    conn.rollback();
                    throw e;
                }
                catch (SQLException se)
                {
                    throw se;
                }
            }
        }
        catch (NumberFormatException e)
        {
            if (conn != null)
            {
                try
                {
                    conn.rollback();
                    throw e;
                }
                catch (SQLException se)
                {
                    throw se;
                }
            }
        }
        catch (SQLException e)
        {
            if (conn != null)
            {
                try
                {
                    conn.rollback();
                    throw e;
                }
                catch (SQLException se)
                {
                    throw se;

                }
            }
        }
        catch (Exception e)
        {
            if (conn != null)
            {
                try
                {
                    conn.rollback();
                    throw e;
                }
                catch (SQLException se)
                {
                    throw se;
                }
            }

        }
        finally
        {
            EmConnectionHandler.closeConnection(conn);

            try
            {
                if (br != null)
                {
                    br.close();
                }
            }
            catch (IOException e)
            {
                // 失敗した場合は何もしない
            }
        }


        return true;

    }

    /**
     * <jp>文字列日付をカレンダー型に変換します。<br></jp>
     * <en>The character string date is converted into the calendar type. <br></en>
     * @param strCalendar <jp>文字列型日付 &nbsp;&nbsp;</jp><en>Character string type date &nbsp;&nbsp;</en>
     * @return <jp>カレンダーオブジェクト &nbsp;&nbsp;</jp><en>Calendar object &nbsp;&nbsp;</en>
     */
    private static Calendar strToCalendar(String strCalendar)
    {
        if (strCalendar.length() == 21)
        {
            // <jp>文字列を分解</jp>
            // <en>The character string is resolved.</en>
            int year = Integer.parseInt(strCalendar.substring(0, 4));
            int month = Integer.parseInt(strCalendar.substring(5, 7));
            int day = Integer.parseInt(strCalendar.substring(8, 10));
            int hour = Integer.parseInt(strCalendar.substring(11, 13));
            int minite = Integer.parseInt(strCalendar.substring(14, 16));
            int second = Integer.parseInt(strCalendar.substring(17, 19));

            // <jp>カレンダーオブジェクトの取得</jp>
            // <en>Acquisition of calendar object</en>
            Calendar calendar = Calendar.getInstance();

            // <jp>カレンダーオブジェクトの設定</jp>
            // <en>Setting of calendar object</en>
            calendar.set(year, month - 1, day, hour, minite, second);

            // <jp>カレンダーの返却</jp>
            // <en>Return of calendar</en>
            return calendar;
        }

        throw new NumberFormatException();
    }

}
