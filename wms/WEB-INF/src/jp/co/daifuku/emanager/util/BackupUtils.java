//$Id: BackupUtils.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.dbhandler.BaseHandler;


/** 
 * <jp>データベースのバックアップ、リストアを行うためのユーティティクラスです。<br></jp>
 * <en>This is a utility class for backup and restore the database.<br></en>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/08</TD><TD>M.Kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3965 $, $Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $
 * @author  $Author: admin $
 */
public class BackupUtils
{
    // Class fields --------------------------------------------------
    /** <jp>ファイル拡張子 &nbsp;&nbsp;</jp><en>File extention &nbsp;&nbsp;</en> */
    public static final String FILE_EXTENTION = ".sql";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /** 
     * <jp>このクラスのバージョンを返します。<br></jp>
     * <en>Returns the version of this class and date.<br></en>
     * @return <jp>バージョンと日付&nbsp;&nbsp;</jp><en>version and date.&nbsp;&nbsp;</en> 
     */
    public static String getVersion()
    {
        return ("$Revision: 3965 $,$Date: 2009-04-06 11:55:05 +0900 (月, 06 4 2009) $");
    }

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /** 
     * <jp>指定されたテーブルのデータを元にテキストファイルを作成します。
     * テーブルのデータはSQL文（TRANCATE TABLE文、INSERT文）にて作成されます。
     * filePathで指定したフォルダに「tableName」.sqlというファイルを作成します。
     * <br></jp>
     * <en>
     * Make a text file based on specified table.  
     * Data on the table are made by the SQL sentence (TRANCATETABLE sentence and INSERT sentence).
     * The file of "tableName" .sql is made in the folder specified with filePath.
     * <br></en>
     * @param filePath <jp>出力するファイルの保存先。"/"で終了する必要があります。 &nbsp;&nbsp;</jp>
     *                  <en>A place of save of a file to output. You must finish it with "/". &nbsp;&nbsp;</en>
     * 
     * @param tableName <jp>SQL文を出力するテーブル名 &nbsp;&nbsp;</jp> 
     *                   <en>The table name which a SQL sentence is output. &nbsp;&nbsp;</en>
     * @param conn Connection
     * @throws SQLException 
     * @throws IOException 
     */
    public static void mkFile(String filePath, String tableName, Connection conn)
            throws SQLException,
                IOException
    {
        BaseHandler handle = new BaseHandler();

        // Call deprecated method in daifuku-commons.
        List sqlList = handle.getSQLDataList(tableName, conn);

        String fileName = filePath + tableName + FILE_EXTENTION;

        //<jp> ファイル出力</jp>
        //<en> File export </en>
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false));
        bw.write("Truncate table " + tableName + ";");
        bw.newLine();
        for (Iterator it = sqlList.iterator(); it.hasNext();)
        {
            String sqlString = (String)it.next();
            bw.write(sqlString);
            bw.newLine();
        }
        bw.close();
    }


    /** 
     * <jp>指定されたテーブル名のSQLファイルをデータベースへ反映します。
     * SQLファイルはTRANCATE TABLE文、INSERT文にて構成されます。
     * filePathで指定したフォルダの「tableName」.sqlというファイルを読み込み
     * データベースへ反映します。
     * <br></jp>
     * <en>The SQL file of the specified table name is reflected to the database.
     * A SQL file is composed of the TRANCATETABLE sentence and the INSERT sentence.
     * The file of "tableName".sql of the folder specified with filePath is reflected to the reading database.
     * <br></en>
     * @param filePath <jp>取り込むファイルの保存先。"/"で終了する必要があります。 &nbsp;&nbsp;</jp>
     *                  <en>A place of load of a file to output. Must finish it with "/". &nbsp;&nbsp;</en>
     * @param tableName <jp>データを反映するテーブル名 &nbsp;&nbsp;</jp>
     *                   <en>The table name which reflects data. &nbsp;&nbsp;</en>
     * @param conn Connection
     * @throws SQLException 
     * @throws FileNotFoundException
     * @throws IOException 
     </en> */
    public static void ldFile(String filePath, String tableName, Connection conn)
            throws SQLException,
                FileNotFoundException,
                IOException
    {
        BaseHandler handle = new BaseHandler();
        InputStreamReader isr = null;
        BufferedReader br = null;

        //<jp> 読込文字列</jp>
        //<en> file path</en>
        String sql = "";
        String fileName = filePath + tableName + BackupUtils.FILE_EXTENTION;

        //<jp> ファイル読み込み<jp>
        //<en> Load the file</en>
        isr = new InputStreamReader(new FileInputStream(fileName));
        br = new BufferedReader(isr, 10);
        while ((sql = br.readLine()) != null)
        {
            if (sql.endsWith(";"))
            {
                sql = sql.substring(0, sql.length() - 1);
            }
            handle.executeUpdate(sql, conn);
        }
        br.close();
        isr.close();
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------


}
