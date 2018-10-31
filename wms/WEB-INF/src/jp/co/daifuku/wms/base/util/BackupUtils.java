//$Id: BackupUtils.java 87 2008-10-04 03:07:38Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

package jp.co.daifuku.wms.base.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.co.daifuku.dbhandler.BaseHandler;
import jp.co.daifuku.logging.Logger;
import jp.co.daifuku.util.Formatter;

/** 
 * <jp>データベースのバックアップ、リストアを行うためのユーティティクラスです。<br></jp>
 * <en>This is a utility class for backup and restore the database.<br></en>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/05/09</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
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
		return "";
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
	public void mkFile(String filePath, String tableName, Connection conn) throws SQLException, IOException
	{
		
		List sqlList = this.getSQLDataList(tableName, conn);

		String fileName = filePath + tableName + FILE_EXTENTION;
		
		//<jp> ファイル出力</jp>
		//<en> File export </en>
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false));
		bw.write("Truncate table " + tableName + ";");
		bw.newLine();
		for(Iterator it = sqlList.iterator(); it.hasNext();)
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
	public void ldFile(String filePath, String tableName, Connection conn) throws SQLException, FileNotFoundException, IOException
	{
		BaseHandler handle = new BaseHandler();		
		InputStreamReader isr =null;
		BufferedReader br = null;

		//<jp> 読込文字列</jp>
		//<en> file path</en>
		String sql = "";
		String fileName = filePath + tableName + BackupUtils.FILE_EXTENTION;

		//<jp> ファイル読み込み<jp>
		//<en> Load the file</en>
		isr = new InputStreamReader(new FileInputStream(fileName));
		br = new BufferedReader(isr,10);
		while(( sql = br.readLine()) != null)
		{
			if(sql.endsWith(";"))
			{
				sql = sql.substring(0, sql.length() -1);
			}
			handle.executeUpdate(sql, conn);
		}
		br.close();
		isr.close();
	}
	
	/**
	 * <jp>引数で指定したテーブルのデータを復元するためのINSERT文を返します。<br></jp>
	 * <en>The INSERT statement to restore data on the table specified in the argument is returned.<br></en>
	 * 
	 * @param tablename Table Name
	 * @param conn Connection
	 * @return INSERT statement
	 * @throws SQLException <jp>例外。&nbsp;&nbsp;</jp><en>The exception. &nbsp;&nbsp;</en>
	 * @deprecated
	 */
	private List getSQLDataList(String tablename, Connection conn) throws SQLException
	{
		Statement statement  = null;
		ResultSet resultSet = null;
		try
		{	
			String sql = "SELECT * FROM " + tablename;
			// create SQL statement 
			statement = conn.createStatement();
			// execute SQL query
			resultSet = statement.executeQuery(sql);
			
			Logger.sqlInfo(sql); // write the SQL logs
			
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int cols = rsmd.getColumnCount();
			List list = new ArrayList();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			while (resultSet.next())
			{
				StringBuffer sbValues = new StringBuffer();
				StringBuffer sbColumn = new StringBuffer();

				sbValues.append("INSERT INTO " + tablename + " (");
				sbColumn.append("VALUES (");

				for (int i = 1; i <= cols; i++)
				{
					String key = rsmd.getColumnName(i);
					int type = rsmd.getColumnType(i);

					if (type == Types.DATE)
					{
						Timestamp times = resultSet.getTimestamp(i);
						if (times != null)
						{
							sbColumn.append("TO_DATE('").append(
								df.format(new java.util.Date(times.getTime())));
							sbColumn.append("','").append("YYYY-MM-DD HH24:MI:SS").append("'),");
						}
						else
						{
							sbColumn.append("null,");
						}
					} 
					else if (type == Types.TIMESTAMP)
					{
						Timestamp times = resultSet.getTimestamp(i);
						if (times != null)
						{
							sbColumn.append("TO_TIMESTAMP('").append(df.format(new Timestamp(times.getTime())));
							sbColumn.append("','").append("YYYY-MM-DD HH24:MI:SS.FF6").append("'),");
						}
						else
						{
							sbColumn.append("null,");
						}
					}
					else
					{
						sbColumn.append(this.format(Formatter.getRtrimmedString(resultSet.getString(i)))).append(",");
					}
					sbValues.append(key).append(",");
				}
				sbValues.delete(sbValues.length() - 1, sbValues.length());
				sbColumn.delete(sbColumn.length() - 1, sbColumn.length());
				sbValues.append(" ) ");
				sbColumn.append(" );");
				
				list.add(sbValues.toString() + sbColumn.toString());
			}
			return list;
		}
		finally
		{
			if (resultSet != null)
			{
				resultSet.close();
			}
			if (statement != null)
			{
				statement.close();
			}
		}
	}
	
	/**
	 * <jp>文字列をフォーマットします。データベースに登録するために文字の両端に
	 * 'をセットします。ただし与えられた文字列がnullの場合はnullを。0バイトの文字列だった場合、文字列' 'を返します。
	 * 	エスケープ文字'が入力された場合は''に変換されます。
	 * <br></jp>
	 * <en>Formatting string: In order to keep records in database, set ' on both ends of 
	 * characters. But, when the string given to it is null, null. A string '' is returned when it is the string of 0 byte.
	 * If 'Esc' is entered, it will be conversed to ''. 
	 * <br></en>
	 * 
	 * @param fmt  <jp>フォーマットの対象となる文字列を指定します。 &nbsp;&nbsp;</jp><en>Specifying target string to format &nbsp;&nbsp;</en>
	 * @return <jp>フォーマットされた文字列を返します。 &nbsp;&nbsp;</jp><en>Returning the formatted string &nbsp;&nbsp;</en>
	 */
	private String format(String fmt)
	{
		String str = null;
		if (fmt == null)
		{
			return "null";
		}
		if (fmt.equals(""))
		{
			return ("' '");
		}
		else if (fmt.indexOf("TO_DATE") >= 0)
		{
			return fmt;
		}
		else
		{
			int num = fmt.indexOf("'");
			if (num >= 0)
			{
				fmt = fmt.replaceAll("'", "''");
			}
			// add K.Fukumori 2005/11/17 start.
//			fmt = jp.co.daifuku.util.Formatter.getRtrimmedString(fmt);
			// add K.Fukumori 2005/11/17 end.
			int len = fmt.length();
			StringBuffer stbf = new StringBuffer(len + 2);
			stbf.append("'");
			stbf.append(fmt);
			stbf.append("'");
			str = stbf.toString();
		}
		return str;
	}

}
