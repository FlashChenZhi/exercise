// $Id: Converter.java 8075 2014-09-19 07:16:57Z okayama $
package jp.co.daifuku.wms.asrs.tool.converter;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

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
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.entity.SystemDefine;

/**<jp>
 * テーブルをテキストに変換、テキストをテーブルに挿入などを行うクラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/17</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/12</TD><TD>hondo</TD><TD>テキストファイルからテーブルの登録を速くしました<BR>
 * 今のテーブル名称と一つ前のテーブル名称が一緒の場合、DATE型に変換するチェックをしないように変更しました。<BR>
 * 以前はテキストのデータを全て読んでチェックし、テーブルに登録していました。<BR>
 * 今回はテキストから１行読み、チェックしてテーブルに書き込むように変更しました。
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8075 $, $Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $
 * @author  $Author: okayama $
 </jp>*/
/**<en>
 * This class converts the table into texts and inserts texts in tables.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/17</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/12</TD><TD>hondo</TD><TD>Modification has been added to enable the quicker registration processing of text file in DB.<BR>
 * Modification has been added ; it skips the check of DATE type conversion if the same table names were given consecutively.<BR>
 * Modification has been added.<BR>
 * Former processing : All data of text was read, checking and registration.<BR>
 * Current processing : Reading, checking and registration is done by each line of the text.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8075 $, $Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $
 * @author  $Author: okayama $
 </en>*/
public class Converter
        extends Object
{
    // Class fields --------------------------------------------------
    /**<jp> メッセージの作成に用いるデリミタ </jp>*/
    /**<en> Delimiters used when creating the messages </en>*/
    protected final String wDelim = MessageResource.DELIM;

    /**<jp> TOOLバージョン </jp>*/
    /**<en> TOOL versin </en>*/
    public static final String VERSION = "1.00";

    /**<jp> テキストの区切り文字 </jp>*/
    /**<en> Delimiters for text </en>*/
    protected static final String DELIM = ",";

    /**<jp> ファイルの拡張子 </jp>*/
    /**<en> File extensions </en>*/
    protected static final String EXTENSION = ".txt";

    /**<jp> ファイルエンコーディング </jp>*/
    /**<en> File encoding </en>*/
    protected static final String ENCODING = "SJIS";

    /**<jp> 日付のフォーマット </jp>*/
    /**<en> Format of dates </en>*/
    protected static final String DATEFORMAT = "YYYY/MM/DD HH24:MI:SS";

    protected static final String TIMESTAMPFORMAT = "yyyymmddhh24missff3";

    /**<jp> ソフトゾーンのテーブル名 </jp>*/
    private static final String softZoneTableName = "DMSOFTZONE";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Versin and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 8075 $,$Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $");
    }

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /**<jp>
     * 引数で指定したテーブルをテキスト形式に変換、保存します。
     *
     * @param <code>conn</code>オブジェクト
     * @param tableName テーブル名
     * @param filename ファイル名。パスも含めた形で指定します。例c:\dbtext\warehouse.txt
     * @throws Exception 例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Conver the table, which was specified by parameter, in text format and store.
     *
     * @param conn <code>Connection</code object
     * @param tableName :name of the table
     * @param filename  :file name. Specified with path included. Example:c\dbtext\warehouse.txt
     * @throws Exception
     </en>*/
    @SuppressWarnings({
            "null",
            "unchecked",
            "rawtypes"
    })
    public static void tableToText(Connection conn, String tableName, String filename)
            throws Exception
    {
        System.out.println("Making " + tableName + " text..");
        //File name is Table name + extension(.txt)
        String fileName = filename;

        //Get column name of Table.
        Vector columnNames = getColumnNames(conn, tableName);

        FileOutputStream fos = new FileOutputStream(fileName);
        OutputStreamWriter osw = new OutputStreamWriter(fos, ENCODING);
        BufferedWriter bw = new BufferedWriter(osw);

        //Write version.
        bw.write(VERSION + "\n");
        //Write column name.

        bw.write(getDelimitedText(columnNames));

        //Query sql
        String sql = makeSQL(conn, tableName, columnNames);
        Statement stmt = conn.createStatement();
        //Excute Query
        ResultSet resultSet = stmt.executeQuery(sql);

        // ソフトゾーンテーブルの場合デフォルトデータを登録します
        if (tableName.equals("TEMP_" + softZoneTableName))
        {
            Vector values = new Vector(2);

            // SOFT_ZONE_ID
            values.add(SystemDefine.SOFT_ZONE_ALL);
            // SOFT_ZONE_NAME
            values.add("Free Soft Zone");

            bw.write(getDelimitedText(values));
        }

        while (resultSet.next())
        {
            Vector values = new Vector(10);
            for (int i = 0; i < columnNames.size(); i++)
            {
                String data = resultSet.getString((String)columnNames.get(i));
                //When the data is null. convert to space.
                if (data == null)
                {
                    data = " ";
                }

                values.add(data);
            }
            bw.write(getDelimitedText(values));
        }

        if (resultSet != null)
        {
            resultSet.close();
            resultSet = null;
        }

        bw.close();
        osw.close();
        fos.close();
        stmt.close();
    }

    /**<jp>
     * 引数で指定したテキストを読込み、引数で指定したテーブルへそのデータを挿入します。
     *
     * @param <code>Connection</code>オブジェクト
     * @param tableName テーブル名
     * @param filename ファイル名。パスも含めた形で指定します。例c:\dbtext\warehouse.txt
     * @throws Exception 例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Read the text which was specified by parameter, then insert this data to the table that
     * parameter specified.
     *
     * @param conn object
     * @param tablename :table name
     * @param filename  :file name. Specified with path included. Example:c\dbtext\warehouse.txt
     * @throws Exception
     </en>*/
    @SuppressWarnings({
            "rawtypes",
            "unchecked"
    })
    public static void textToTable(Connection conn, String tablename, String filename)
            throws Exception
    {
        System.out.println("Making " + filename.substring(0, (filename.lastIndexOf(EXTENSION))) + " table...");

        Statement stmt = null;

        FileInputStream fis = null;
        InputStreamReader ir = null;
        BufferedReader br = null;
        try
        {
            stmt = conn.createStatement();

            //<jp>フォーマット部分の読込</jp>
            //<en>Read the text data.</en>
            String format = getFormat(filename);

            //<jp>ヘッダーの各項目を配列に分離する。</jp>
            //<en>Read the text data.</en>
            String[] key = getSeparatedItem(format);

            File file = new File(filename);
            if (file.exists())
            {
                //<jp>ファイル読み込み</jp>
                //<en>Reading file</en>
                fis = new FileInputStream(filename);
                ir = new InputStreamReader(fis, ENCODING);
                br = new BufferedReader(ir);
                String buf = "";
                int count = 0;
                while ((buf = br.readLine()) != null)
                {
                    //<jp> データ部分はテキストファイルの3行目から</jp>
                    //<en>The data begins as of the 3rd line of the text file.</en>
                    if (count >= 2)
                    {
                        Vector vector = new Vector(5);
                        Hashtable hash = new Hashtable();
                        String[] data = getSeparatedItem(buf);
                        //<jp>フォーマット部の項目数とデータの項目数が異なる場合</jp>
                        //<en>If the number of items in format and the number of items differs,</en>
                        if (data.length != key.length)
                        {
                            String errormsg =
                                    "Text Format Error [" + filename + "] : The number of items is " + data.length
                                            + ". This must be " + key.length + " .";
                            throw new IllegalArgumentException(errormsg);
                        }
                        // ソフトゾーンテーブルの場合
                        if (filename.endsWith(softZoneTableName + EXTENSION))
                        {
                            // デフォルトソフトゾーン(SystemDefine.SOFT_ZONE_ALL)の場合は無視する。
                            // data[0]:SOFT_ZONE_ID
                            if (data[0].equals(SystemDefine.SOFT_ZONE_ALL))
                            {
                                continue;
                            }
                        }
                        for (int j = 0; j < key.length; j++)
                        {
                            hash.put(key[j], data[j]);
                        }
                        vector.add(hash);

                        Hashtable hs = (Hashtable)vector.get(0);
                        String sql = makeSQL(conn, tablename, filename, hs, key);
                        stmt.executeUpdate(sql);
                    }
                    count++;
                }
                fis.close();
                ir.close();
                br.close();
            }
            else
            {
                //throw new FileNotFoundException("File Not Found Error [" + filename + "] : File not found. ");
                System.out.println("File Not Found Error [" + filename + "] : File not found. ");
            }
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                // 失敗した場合は何もしない
            }

            try
            {
                if (fis != null)
                {
                    fis.close();
                }
            }
            catch (IOException e)
            {
                // 失敗した場合は何もしない
            }

            try
            {
                if (ir != null)
                {
                    ir.close();
                }
            }
            catch (IOException e)
            {
                // 失敗した場合は何もしない
            }

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
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * DatabaseMetaDataを使用して、引数で指定されたテーブルの列名を取得します。
     * @param conn <code>Connection</code>オブジェクト
     * @param tableName
     * @return   列名をVectorに格納して返します
     * @throws Exception 例外が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Retrieve the name of table column that parameter specified by using DatabaseMetaData.
     * @param conn Connection object
     * @param tableName table name
     * @return   :store the column in Vector, then return.
     * @throws Exception
     </en>*/
    @SuppressWarnings({
            "null",
            "unchecked",
            "rawtypes"
    })
    public static Vector getColumnNames(Connection conn, String tableName)
            throws Exception
    {
        //<jp> メタデータ情報</jp>
        //<en> Metadata information</en>
        DatabaseMetaData dmd = conn.getMetaData();
        Vector columnNames = new Vector();

        //<jp> テーブル情報を抽出します。</jp>
        //<en> Extract the table information.</en>
        ResultSet resultSet = dmd.getColumns(conn.getCatalog(), null, tableName, "%");
        while (resultSet.next())
        {
            String column = resultSet.getString(4);

            if (!columnNames.contains(column))
            {
                columnNames.addElement(column);
            }
        }

        if (resultSet != null)
        {
            resultSet.close();
            resultSet = null;
        }

        return columnNames;
    }

    /**<jp>
     * テーブル取得するためのSQL文を作成します。
     * @param conn
     * @param tableName
     * @param columnNames
     * @return   SQL文
     </jp>*/
    /**<en>
     * Create the SQL string which will be used to retrieve the table.
     * @param conn Connection object
     * @param tableName table name
     * @param columnNames column name
     * @return   SQL string
     * @throws Exception
     </en>*/
    @SuppressWarnings("rawtypes")
    private static String makeSQL(Connection conn, String tableName, Vector columnNames)
            throws Exception
    {
        String fmtSQL = "SELECT ";
        String columnSQL = "";
        for (int i = 0; i < columnNames.size(); i++)
        {
            String columnName = (String)columnNames.get(i);
            if (i == 0)
            {
                if (getColumnType(conn, tableName, columnName).equals("DATE"))
                {
                    columnSQL = "TO_CHAR(" + columnName + ",'" + DATEFORMAT + "') " + columnName;
                }
                else
                {
                    if (getColumnType(conn, tableName, columnName).equals("TIMESTAMP(3)"))
                    {
                        columnSQL = "TO_CHAR(" + columnName + ",'" + TIMESTAMPFORMAT + "') " + columnName;
                    }
                    else
                    {
                        columnSQL = columnName;
                    }
                }
            }
            else
            {
                if (getColumnType(conn, tableName, columnName).equals("DATE"))
                {
                    columnSQL = columnSQL + "," + "TO_CHAR(" + columnName + ",'" + DATEFORMAT + "') " + columnName;
                }
                else
                {
                    if (getColumnType(conn, tableName, columnName).equals("TIMESTAMP(3)"))
                    {
                        columnSQL =
                                columnSQL + "," + "TO_CHAR(" + columnName + ",'" + TIMESTAMPFORMAT + "') " + columnName;
                    }
                    else
                    {
                        columnSQL = columnSQL + "," + columnName;
                    }
                }
            }
        }
        fmtSQL = fmtSQL + columnSQL + " FROM " + tableName;

        return fmtSQL;
    }

    /**
     *
     * @param vec テキストを格納したVector
     * @return 区切り文字で結合したテキスト
     */
    @SuppressWarnings("rawtypes")
    private static String getDelimitedText(Vector vec)
    {
        String fmt = "";
        for (int i = 0; i < vec.size(); i++)
        {
            if (i == 0)
            {
                fmt = (String)vec.get(0);
            }
            else
            {
                fmt = fmt + DELIM + (String)vec.get(i);
            }
        }
        return fmt + "\n";
    }

    /**
     *
     * @param conn conn <code>Connection</code>オブジェクト
     * @param tableName テーブル名
     * @param columnName カラム
     * @return カラムタイプ
     * @throws Exception 例外を通知します。
     */
    @SuppressWarnings("null")
    private static String getColumnType(Connection conn, String tableName, String columnName)
            throws Exception
    {
        //<jp> メタデータ情報</jp>
        //<en> Metadata information</en>
        DatabaseMetaData dmd = conn.getMetaData();

        //<jp> テーブル情報を抽出します。</jp>
        //<en> Extract the table information.</en>
        ResultSet resultSet =
                dmd.getColumns(conn.getCatalog(), null, tableName.toUpperCase(), columnName.toUpperCase());
        String typeName = "";
        while (resultSet.next())
        {
            typeName = resultSet.getString("TYPE_NAME");
        }

        if (resultSet != null)
        {
            resultSet.close();
            resultSet = null;
        }

        return typeName;

    }

    /**<jp>
     * 引数で指定されたString文字列を、所定の区切り文字で分離し、その結果をStringの配列で返します。
     * さらに、DisplayText.trimメソッドにより、文字列の後ろに空白が有る場合は自動的に削除されます。
     * 例<BR>
     * "AAA,BBB   ,CCC" => ret[0] = "AAA", ret[1] = "BBB", ret[2] = "CCC"<BR>
     * @param buf 分離する文字列
     * @return 作成されたStringの配列
     </jp>*/
    /**<en>
     * Sprit the String specified by parameter at specified delimtiers, then return the outcome
     * in form of String array.
     * Also it automatically delets spaces which follow the strings, if there are any, by
     * DisplayText.trim method.
     * Example<BR>
     * "AAA,BBB   ,CCC" => ret[0] = "AAA", ret[1] = "BBB", ret[2] = "CCC"<BR>
     * @param buf :string which will be split
     * @return    :array of the created String
     </en>*/
    @SuppressWarnings({
            "unchecked",
            "rawtypes"
    })
    public static String[] getSeparatedItem(String buf)
    {
        Vector bufVec = new Vector();
        //<jp>区切り文字が連続している場合には、空白１バイトを挿入する。</jp>
        //<en>If there are consecutive delimiters, insert the space of 1 byte.</en>
        String buff = buf;
        buff = delimiterCheck(buf, DELIM);
        StringTokenizer stk = new StringTokenizer(buff, DELIM, false);
        while (stk.hasMoreTokens())
        {
            bufVec.addElement(trim(stk.nextToken()));
        }
        String[] array = new String[bufVec.size()];
        bufVec.copyInto(array);
        return array;
    }

    /**<jp>
     * 引数として受け取ったStringでデリミタが連続しているところを
     * ひとつスペースを空ける。<BR>
     * 例 item0001,100,200,,300, → item0001,100,200, ,300,
     </jp>*/
    /**<en>
     * Replace the consecutive delimeters, of the String received as parameter,
     * with a space.<BR>
     * Ex. item0001,100,200,,300, --> item0001,100,200, ,300,
     </en>*/
    private static String delimiterCheck(String str, String delim)
    {
        //<jp>デリミタが無い場合はそのまま返す</jp>
        //<en>If there are no delimiters, return itself as it is.</en>
        if (str.indexOf(delim) == -1)
        {
            return str;
        }
        StringBuffer sb = new StringBuffer(str);
        int len = sb.length();
        int i = 0;
        for (i = 0; i < len; i++)
        {
            if (i < len - 1)
            {
                if (sb.substring(i, i + 2).equals(delim + delim))
                {
                    sb.replace(i, i + 2, delim + " " + delim);
                }
            }
            len = sb.length();
        }
        if (sb.substring(len - 1, len).equals(delim))
        {
            sb = sb.append(" ");
        }
        return (sb.toString());
    }

    /**<jp>
     * 文字列の右端から空白を除去します。
     * 例 "   1 22   33333     " → "   1 22   33333"
     * @param str 編集対象文字列
     * @return str 空白を除去された文字列。Nullが引数の場合はそのままNullを返します。
     </jp>*/
    /**<en>
     * Delete spaces from the end of the string.
     * ex "   1 22   33333     " --> "   1 22   33333"
     * @param str  :the string to edit
     * @return str :the string from which the space was deleted. If the parameter is Null,
     * return Null without any modification.
     </en>*/
    private static String trim(String str)
    {
        if (str == null)
        {
            return null;
        }

        String buf = str;
        int len = str.length();
        try
        {
            while (str.lastIndexOf(" ", len) == (len - 1))
            {
                len--;
                buf = str.substring(0, len);
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            buf = "";
        }
        return buf;
    }

    /**<jp>
     * テーブルを作成するためのSQL文を作成します。
     * @param filename
     * @param hs
     * @return   SQL文
     </jp>*/
    /**<en>
     * Create the SQL string which is for the creation of table.
     * @param conn Databse Connection Object
     * @param tablename table name
     * @param filename
     * @param hs
     * @return   SQL string
     </en>*/
    @SuppressWarnings("javadoc")
    static String[] columtype = new String[999];

    static String tableName0 = "";

    @SuppressWarnings("rawtypes")
    private static String makeSQL(Connection conn, String tablename, String filename, Hashtable hs, String[] key)
            throws Exception
    {
        String[] columnNames = key;
        //<jp>ファイル名から拡張子を省く</jp>
        //<en>Ommit the extension from the file name.</en>
        String tableName = tablename;

        String fmtSQL = "INSERT INTO " + tableName;
        String columNameSQL = "";
        for (int i = 0; i < columnNames.length; i++)
        {
            if (i == 0)
            {
                columNameSQL = columnNames[0];
            }
            else
            {
                columNameSQL = columNameSQL + " , " + columnNames[i];
            }
        }
        fmtSQL = fmtSQL + " ( " + columNameSQL + " ) values ";

        //<jp> 今のテーブル名称と一つ前のテーブル名称が一緒の場合、カラムの型をチェックする必要はありません。</jp>
        //<en>The check of a column type is not necessary when the name of the current table is the same as the one previously handled.</en>
        if (!tableName0.equals(tableName))
        {
            for (int i = 0; i < hs.size(); i++)
            {
                columtype[i] = getColumnType(conn, tableName, columnNames[i]);
            }
            tableName0 = tableName;
        }

        String columValueSQL = "";
        for (int j = 0; j < hs.size(); j++)
        {
            if (j == 0)
            {
                if (columtype[0].equals("DATE") || columtype[0].equals("TIMESTAMP(3)"))
                {
                    columValueSQL = " TO_DATE('" + (String)hs.get(columnNames[0]) + "','" + TIMESTAMPFORMAT + "')";
                }
                else
                {
                    columValueSQL = "'" + (String)hs.get(columnNames[0]) + "'";
                }
            }
            else
            {
                if (columtype[j].equals("DATE") || columtype[j].equals("TIMESTAMP(3)"))
                {
                    columValueSQL =
                            columValueSQL + "," + " TO_TIMESTAMP('" + (String)hs.get(columnNames[j]) + "','"
                                    + TIMESTAMPFORMAT + "')";
                }
                else
                {
                    columValueSQL = columValueSQL + "," + "'" + (String)hs.get(columnNames[j]) + "'";
                }
            }
        }
        return fmtSQL + " ( " + columValueSQL + " )";
    }

    /**<jp>
     * テキストファイルのヘッダー部分(フォーマット部)を読込返します。
     * @param filename 読み込みを行うファイル名
     * @return ヘッダー部分
     * @throws Exception 例外が発生した場合
     </jp>*/
    /**<en>
     * Read and return the header (format information) of text file.
     * @param filename :name of the file which will be read
     * @return :header
     * @throws Exception :if exception occurred.
     </en>*/
    private static String getFormat(String filename)
            throws Exception
    {
        String[] buf = getStringArray(filename, 1);
        return buf[1];
    }

    /**<jp>
     * 指定されたファイルの読み込みを行い、その結果をStringの配列で返します。
     * @param filename 読み込みを行うファイル名
     * @param option 0:バージョンを取得 1:フォーマット部を取得 2:データ部を取得
     * @return 作成されたStringの配列
     * @throws FileNotFoundException 指定されたファイルが見つからない場合
     * @throws IllegalArgumentException 不正な引数、または不適切な引数をメソッドに渡したことを示すためにスローされます。
     * @throws IOException 入出力エラーが発生した場合
     </jp>*/
    /**<en>
     * Read the specified file and return the result in String array.
     * @param filename :name of the file which will be read
     * @param option 0:version retrieved, 1:format information retrieved, 2:data retrieved.
     * @return :array of created String
     * @throws FileNotFoundException :if specified data cannot be found.
     * @throws IllegalArgumentException :this will bethrown in order to indicate that incorrect/inproper
     * parameter was passed to the method.
     * @throws IOException :if input/output error occurred.
     </en>*/
    @SuppressWarnings({
            "unchecked",
            "rawtypes"
    })
    private static String[] getStringArray(String filename, int option)
            throws FileNotFoundException,
                IllegalArgumentException,
                IOException
    {
        File file = new File(filename);
        if (file.exists())
        {
            //<jp>ファイル読み込み</jp>
            //<en>Reading file</en>
            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader ir = new InputStreamReader(fis, ENCODING);
            BufferedReader br = new BufferedReader(ir);
            Vector vec = new Vector(10);
            String buf = "";
            int count = 0;
            while ((buf = br.readLine()) != null)
            {
                vec.add(buf);
                if (option == 0 && count == 0)
                {
                    break;
                }
                else if (option == 1 && count == 1)
                {
                    break;
                }
                count++;
            }
            fis.close();
            ir.close();
            br.close();
            //<jp>サイズのチェック</jp>
            //<en>Check the size</en>
            if (option != 0 && count == 0)
            {
                String errormsg = "Text Format Error [" + filename + "] : Can't find VERSION AREA.";
                throw new IllegalArgumentException(errormsg);
            }
            else if (option != 1 && count == 1)
            {
                String errormsg = "Text Format Error [" + filename + "] : Can't find FORMAT AREA.";
                throw new IllegalArgumentException(errormsg);
            }

            String[] array = new String[vec.size()];
            vec.copyInto(array);
            return array;
        }
        else
        {
            throw new FileNotFoundException("File Not Found Error [" + filename + "] : File not found. ");
        }
    }

}
//end of class


