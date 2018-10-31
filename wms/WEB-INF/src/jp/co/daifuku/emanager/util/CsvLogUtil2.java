// $Id: CsvLogUtil2.java 7855 2010-04-22 05:22:13Z kishimoto $
/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.emanager.database.entity.LogExpImpSet;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.LogExpImpSetHandler;

/**
 * DBのテーブルをインポート／エクスポートするためのユーティリティクラスです。
 * @author K.Fukumori
 * @version NIPRO PHARMA Custom
 */
public class CsvLogUtil2
{
    // Class fields --------------------------------------------------
    /** ログ日時の列名 */
    private static final String LOGDATE_COLNAME = "LOG_DATE";

    /** TIMESTAMP型をCSVに出力するためのフォーマット */
    private static final DateFormat JAVA_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /** TIMESTAMP型をDBに出力するためのフォーマット */
    private static final String SQL_TIMESTAMP_FORMAT = "YYYYMMDDHH24MISSFF3";

    /** DATE型をCSVに出力するためのフォーマット */
    private static final DateFormat JAVA_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    /** LOG_DATEの配列でのindex */
    protected static final int LOG_DATE_INDEX = 0;

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    /**
     * DBからCSVファイルにエクスポートします。
     * @param conn DBコネクション
     * @param exportDirPath エクスポートするファイルのディレクトリのパス
     * @param exportFileName エクスポートするファイル名
     * @param tableName エクスポートするテーブル名
     * @param fromLogDate エクスポートするログ日時 From
     * @param toLogDate エクスポートするログ日時 To
     * @return エクスポート件数
     * @throws IOException ファイルIOエラー
     * @throws SQLException DBエラー
     */
    public static synchronized int exportData(Connection conn, String exportDirPath, String exportFileName,
            String tableName, Date fromLogDate, Date toLogDate)
            throws SQLException,
                IOException
    {
        // CSVファイルパスを生成
        String exportFilePath = exportDirPath + File.separator + exportFileName;

        // CSVファイル作成
        int exportCount = exportCsv(conn, exportFilePath, tableName, fromLogDate, toLogDate);

        return exportCount;
    }

    /**
     * CSVファイルからDBへインポートします。
     * @param conn DBコネクション
     * @param importDirPath インポートする CSV ファイルのディレクトリのパス
     * @param importFileName インポートするファイル名
     * @param tableName テーブル名
     * @return インポート件数
     * @throws ParseException 日付フォーマットエラー
     * @throws IOException ファイルIOエラー
     * @throws SQLException DBエラー
     */
    public static synchronized int importData(Connection conn, String importDirPath, String importFileName,
            String tableName)
            throws SQLException,
                IOException,
                ParseException
    {
        // インポートファイルパスを生成
        String importFilePath = importDirPath + File.separator + importFileName;

        // CSVファイル取込
        int importCount = importCsv(conn, importFilePath, tableName);

        return importCount;
    }

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    /**
     * CSVファイルからDBへインポートする。
     * 
     * @param conn DBコネクション 
     * @param importFilePath インポートするファイルのパス 
     * @param tableName テーブル名
     * @return インポート件数
     * @throws SQLException DBエラー
     * @throws IOException ファイルIOエラー
     * @throws ParseException TIMESTAMP型、DATE型のフォーマットエラー
     */
    private static int importCsv(Connection conn, String importFilePath, String tableName)
            throws SQLException,
                IOException,
                ParseException
    {
        int importCount = 0;
        BufferedReader reader = null;
        StringBuffer sql = null;

        // 取込みファイル名
        String fileName = importFilePath.substring(importFilePath.lastIndexOf(File.separatorChar) + 1);

        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try
        {
            stmt = conn.createStatement();

            List colNameList = new ArrayList();
            List colTypeList = new ArrayList();

            // テーブルのデータを削除 --------------------------------------------------
            sql = new StringBuffer();
            sql.append("truncate table " + tableName);
            EmLog4jLogger.sqlModify(CsvLogUtil2.class.getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            stmt.executeUpdate(sql.toString());

            // テーブルの列情報を取得 --------------------------------------------------
            sql = new StringBuffer();
            sql.append("select * from " + tableName + " where 1=0");
            EmLog4jLogger.sqlFind(CsvLogUtil2.class.getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            rset = stmt.executeQuery(sql.toString());
            ResultSetMetaData rsmd = rset.getMetaData();
            int colCount = rsmd.getColumnCount();

            for (int i = 1; i < colCount + 1; i++)
            {
                colNameList.add(rsmd.getColumnName(i));
                colTypeList.add(new Integer(rsmd.getColumnType(i)));
            }

            // INSERT のための PreparedStatement用 SQL生成 ------------------------------
            sql = new StringBuffer();
            sql.append("insert into " + tableName + "(");
            for (int i = 0; i < colCount; i++)
            {
                sql.append(colNameList.get(i));
                if (i < colCount - 1)
                {
                    sql.append(", ");
                }
            }
            sql.append(") values (");
            for (int i = 0; i < colCount; i++)
            {
                sql.append("?");
                if (i < colCount - 1)
                {
                    sql.append(", ");
                }
            }
            sql.append(")");

            // PreparedStatement を作成
            pstmt = conn.prepareStatement(sql.toString());

            // CSVファイルのリーダを取得
            reader = getReader(importFilePath);

            // はじめの行をロード
            String csv = reader.readLine();

            while (csv != null)
            {
                // １行分のデータをロードしCSVを展開 ------------------------------------
                String data[] = getStringArrayFromCsv(csv);

                // ステートメントのパラメータをクリア
                pstmt.clearParameters();

                // CSVのデータの内容をパラメータとしてセット ----------------------------
                int col;
                for (col = 1; col < data.length + 1; col++)
                {
                    int colType = ((Integer)colTypeList.get(col - 1)).intValue();

                    if (data[col - 1] == null || "".equals(data[col - 1]))
                    {
                        pstmt.setNull(col, colType);
                    }
                    else if (colType == Types.TIMESTAMP)
                    { // TIMESTAMP型の場合
                        // 日時文字列からDate型に変換し、その後 TIMESTAMP型に変換
                        Date date = JAVA_TIMESTAMP_FORMAT.parse(data[col - 1]);
                        Timestamp ts = new Timestamp(date.getTime());
                        pstmt.setTimestamp(col, ts);
                    }
                    else if (colType == Types.DATE)
                    { // DATE型の場合
                        Date date = JAVA_DATE_FORMAT.parse(data[col - 1]);
//                        java.sql.Date sDate = new java.sql.Date(date.getTime());
//                        pstmt.setDate(col, sDate);
                        Timestamp ts = new Timestamp(date.getTime());
                        pstmt.setTimestamp(col, ts);
                    }
                    else
                    { // その他
                        pstmt.setString(col, data[col - 1]);
                    }
                }

                // ステートメントの実行 -------------------------------------------------
                EmLog4jLogger.sqlModify(CsvLogUtil2.class.getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString()
                        + " <-- [" + csv + "]");
                pstmt.executeUpdate();
                importCount++;

                // 次の行を読む
                csv = reader.readLine();
            }

            LogExpImpSetHandler logSetHandler = EmHandlerFactory.getLogExpImpSetHandler(conn);

            LogExpImpSet setting = new LogExpImpSet();
            setting.setImportFileName(fileName);
            setting.setImportTable(tableName);
            // LogExpImpSetの更新
            logSetHandler.updateImportInfo(setting);

            // コミット
            conn.commit();
        }
        catch (SQLException e)
        {
            EmLog4jLogger.sqlError(e.getMessage(), e);
            // DBエラー
            throw e;
        }
        catch (IOException e)
        {
            // IOエラー
            throw e;
        }
        catch (ParseException e)
        {
            // TIMESTAMP, DATE のフォーマットエラー
            throw e;
        }
        finally
        {
            // CSVファイルをクローズ
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            // DBリソースをクローズ
            if (rset != null)
            {
                rset.close();
            }
            if (stmt != null)
            {
                stmt.close();
            }
            if (pstmt != null)
            {
                pstmt.close();
            }
        }

        return importCount;
    }

    /**
     * DBからCSVにエクスポートする
     * @param conn DBコネクション
     * @param exportFilePath エクスポートするファイルのパス
     * @param tableName テーブル名
     * @param fromLogDate エクスポートする日付 From
     * @param toLogDate エクスポートする日付 To
     * @return エクスポート件数
     * @throws SQLException DBエラー
     * @throws IOException ファイルIOエラー
     */
    private static int exportCsv(Connection conn, String exportFilePath, String tableName, Date fromLogDate,
            Date toLogDate)
            throws SQLException,
                IOException
    {
        int exportCount = 0;

        BufferedWriter writer = null;

        StringBuffer condition = new StringBuffer();
        condition.append("where ");
        condition.append(LOGDATE_COLNAME + ">=" + "to_timestamp( '" + JAVA_TIMESTAMP_FORMAT.format(fromLogDate) + "','"
                + SQL_TIMESTAMP_FORMAT + "') ");
        condition.append("and " + LOGDATE_COLNAME + "<=" + "to_timestamp( '" + JAVA_TIMESTAMP_FORMAT.format(toLogDate)
                + "','" + SQL_TIMESTAMP_FORMAT + "') ");
        condition.append("order by " + LOGDATE_COLNAME + " ");

        // SQL組立て
        StringBuffer sql = new StringBuffer();
        sql.append("select * from " + tableName + " " + condition + "for update ");

        Statement stmt = null;
        ResultSet rset = null;

        try
        {
            stmt = conn.createStatement();

            // エクスポートデータ検索 -------------------------------------------------
            EmLog4jLogger.sqlFind(CsvLogUtil2.class.getName() + EmLog4jLogger.LOG_SEPARATOR + sql.toString());
            rset = stmt.executeQuery(sql.toString());

            // 結果列数を取得
            ResultSetMetaData rsmd = rset.getMetaData();
            int colCount = rsmd.getColumnCount();

            // 1レコード分のデータを格納するList
            List recodeList = null;

            // CSVファイルのライタを取得
            writer = getWriter(exportFilePath);

            // CSV 1行分の文字列
            String csvLineStr = null;
            while (rset.next())
            {
                // １行分のデータをロード ----------------------------------------------
                recodeList = new ArrayList();
                for (int i = 1; i < colCount + 1; i++)
                {
                    // 列の型を取得
                    int colType = rsmd.getColumnType(i);

                    if (colType == Types.TIMESTAMP)
                    { // TIMESTAMP型の場合
                        Date ts = rset.getTimestamp(i);
                        if (ts != null)
                        {
                            recodeList.add(JAVA_TIMESTAMP_FORMAT.format(ts));
                        }
                        else
                        {
                            recodeList.add("");
                        }

                    }
                    else if (colType == Types.DATE)
                    { // DATE型の場合
                        Date date = rset.getTimestamp(i);
                        if (date != null)
                        {
                            recodeList.add(JAVA_DATE_FORMAT.format(date));
                        }
                        else
                        {
                            recodeList.add("");
                        }
                    }
                    else
                    { // それ以外は文字列として取得
                        String str = rset.getString(i);
                        if (str != null)
                        {
                            str = str.replaceAll("\r", "");
                            str = str.replaceAll("\n", "");
                            recodeList.add(str);
                        }
                        else
                        {
                            recodeList.add("");
                        }
                    }
                }

                // １レコードをCSV１行の文字列に変換しファイル出力 ----------------------
                String arr[] = (String[])recodeList.toArray(new String[recodeList.size()]);
                csvLineStr = getCsvFromStringArray(arr);
                writer.write(csvLineStr);
                writer.newLine();

                exportCount++;
            }
            // CSVファイルをフラッシュ
            writer.flush();
        }
        catch (SQLException e)
        {
            // DBエラー
            EmLog4jLogger.sqlError(e.getMessage(), e);
            throw e;
        }
        catch (IOException e)
        {
            // IOエラー
            throw e;
        }
        finally
        {
            // CSVファイルをクローズ
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            // DBリソースをクローズ
            if (rset != null)
            {
                rset.close();
            }
            if (stmt != null)
            {
                stmt.close();
            }
        }
        return exportCount;
    }

    /**
     * エクスポートするCSVファイルのライタを生成する。
     * @param exportFilePath エクスポートするファイルのパス
     * @return BufferedWriter
     * @throws FileNotFoundException ファイルが見つからなかったとき 
     */
    private static BufferedWriter getWriter(String exportFilePath)
            throws FileNotFoundException
    {
        // ファイルを開く
        FileOutputStream fos = null;
        // 追記書き込みする
        fos = new FileOutputStream(exportFilePath, true);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);

        return bw;
    }

    /**
     * インポートするCSVファイルのリーダを生成する。
     * @param importFilePath インポートファイルのパス
     * @return BufferedReader
     * @throws FileNotFoundException ファイルが見つからなかったとき 
     */
    private static BufferedReader getReader(String importFilePath)
            throws FileNotFoundException
    {
        File file = new File(importFilePath);
        InputStreamReader isr = null;

        isr = new InputStreamReader(new FileInputStream(file));

        BufferedReader br = new BufferedReader(isr);
        return br;
    }

    /**
     * CSVファイルの一行からLOG_DATEを取得します。
     * 
     * @param record CSVファイルの1行
     * @return LOG_DATE
     */
    protected static Date getLogDateFromRecord(String record)
    {
        String[] array = getStringArrayFromCsv(record);
        String strLogDate = array[LOG_DATE_INDEX];
        Date logDate = null;
        try
        {
            logDate = JAVA_TIMESTAMP_FORMAT.parse(strLogDate);
        }
        catch (ParseException e)
        {
            logDate = null;
        }
        return logDate;
    }

    /**
     * Stringの配列をCSV形式に変換します。
     * 文字列に , (カンマ) "(ダブルコート) が含まれる場合は "" で囲います。
     * また、文字列に " (ダブルコート) が含まれる場合は "" (ダブルコート２つ) に変換します。
     * @param stringArray 変換するString配列
     * @return CSV形式の文字列
     */
    private static String getCsvFromStringArray(String[] stringArray)
    {
        StringBuffer csv = new StringBuffer();
        for (int i = 0; stringArray != null && i < stringArray.length; i++)
        {
            if (stringArray[i] != null)
            {
                // 連結
                if (stringArray[i].indexOf(",") >= 0 || stringArray[i].indexOf("\"") >= 0)
                {
                    csv.append("\"" + stringArray[i].replaceAll("\"", "\"\"") + "\"");
                }
                else
                {
                    csv.append(stringArray[i]);
                }
            }

            // , を出力
            if (i != stringArray.length - 1)
            {
                csv.append(",");
            }
        }
        return csv.toString();
    }

    /**
     * CSV文字列からString配列に変換します。
     * @param csv CSV形式の文字列
     * @return String配列
     */
    private static String[] getStringArrayFromCsv(String csv)
    {
        if (csv == null)
        {
            return null;
        }

        if (csv.indexOf("\"") == -1 && csv.lastIndexOf(",") != csv.length() - 1)
        {
            return csv.split(",");
        }

        List list = new ArrayList();
        int start = 0;
        int nextComma = 0;
        int quotCount = 0;

        while (true)
        {
            // １データの切り出し
            int index = start;
            while (true)
            {
                // 次の , 位置を取得
                nextComma = csv.indexOf(",", index);
                if (nextComma == -1)
                {
                    nextComma = csv.length();
                }

                // 次の , までの " の数をカウント
                for (int i = index; i < nextComma; i++)
                {
                    if (csv.charAt(i) == '\"')
                    {
                        quotCount++;
                    }
                }

                // "" 内の , であればスキップ
                if (quotCount % 2 != 0 && nextComma != csv.length())
                {
                    index = nextComma + 1;
                    continue;
                }

                break;
            }

            // リストに追加
            if (quotCount > 0)
            {
                list.add(csv.substring(start + 1, nextComma - 1).replaceAll("\"\"", "\""));
            }
            else
            {
                list.add(csv.substring(start, nextComma));
            }

            // 終了条件
            if (nextComma == csv.length())
            {
                break;
            }
            else
            {
                start = nextComma + 1;
                quotCount = 0;
            }
        }

        return (String[])list.toArray(new String[list.size()]);
    }
}
// end of class
