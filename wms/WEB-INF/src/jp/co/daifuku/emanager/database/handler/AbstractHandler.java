// $Id: AbstractHandler.java 3965 2009-04-06 02:55:05Z admin $
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.emanager.database.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <jp>このクラスは全てのDBハンドラクラスの親クラスであり、メソッドをユーティリティに提供します。<br>
 * </jp> <en>This class is super class to all db handlers and also provide
 * utility methods.<br>
 * </en>
 * 
 * @author $Author: Muneendra
 */
public class AbstractHandler
{

    // instance variable declaration
    /** <jp>接続 &nbsp;&nbsp;</jp><en>Connection &nbsp;&nbsp;</en> */
    protected Connection connection = null;

    /** <jp>ステートメント &nbsp;&nbsp;</jp><en>Statement &nbsp;&nbsp;</en> */
    protected Statement statement = null;

    /** <jp>準備されたステートメント &nbsp;&nbsp;</jp><en>Prepared statement &nbsp;&nbsp;</en> */
    protected PreparedStatement preparedStatement = null;

    /** <jp>結果をセット &nbsp;&nbsp;</jp><en>Result set &nbsp;&nbsp;</en> */
    protected ResultSet resultset = null;

    /**
     * <jp>サブクラスによって開かれたステートメントと接続結果を閉じます。<br>
     * </jp> <jp>最終的にサブクラスのブロックから呼ばれます。<br>
     * </jp> <en>This method closes statement and result connections opened by
     * subclasses<br>
     * </en> <en>This method is called from finally block of subclasses<br>
     * </en>
     * 
     * @throws SQLException :
     *             <en>if a database access error occurs during modification.</en>
     */
    protected void closeStatment_Resultset()
            throws SQLException
    {

        try
        {
            if (statement != null)
            {
                statement.close();
            }
            if (resultset != null)
            {
                resultset.close();
            }
            if (preparedStatement != null)
            {
                preparedStatement.close();
            }
        }
        catch (SQLException e)
        {
            // throw new EmDbException("");
        }
    }

    /**
     * 日付型データをデータベースの日付型フィールドにセットするために、フォーマットします。
     * ORACLEのTO_DATE関数を使用する形に編集します。ただし与えられた文字列がnullだった場合、
     * 
     * @param dat
     *            フォーマットの対象となるDate型データを指定します。
     * @return フォーマットされた文字列を返します。
     */
    protected static String getDateFormat(Date dat)
    {
        String str = null;
        try
        {
            SimpleDateFormat sformatter = new SimpleDateFormat("yyyyMMddHHmmss");
            StringBuffer stbf = new StringBuffer(32);
            // TO_DATE("yyyymmddhhmmdd（実際には数値が入る）", 'yyyymmddhh24miss')の形に編集
            stbf.append("TO_DATE('" + sformatter.format(dat) + "', 'yyyymmddhh24miss')");
            str = stbf.toString();
        }
        catch (Exception exception)
        {
            // return null if any exception
        }
        return str;
    }

    /**
     * <jp>java.util.Dateをjava.sql.Date形式に変換します。<br>
     * </jp> <en>this method converts the java.util.Date to java.sql.Date format<br>
     * </en>
     * 
     * @param utilDate :
     *            java.util.Date object
     * @return java.sql.Date object..Returns null if any exception
     */
    protected static java.sql.Timestamp getSqlDate(Date utilDate)
    {
        java.sql.Timestamp sqlDate = null;
        try
        {
            long utilDateTemp = utilDate.getTime();
            sqlDate = new java.sql.Timestamp(utilDateTemp);
            return sqlDate;

        }
        catch (Exception exception)
        {
            // return null if any exception
        }

        return sqlDate;
    }

    /**
     * <jp>java.sql.Dateをjava.util.Date形式に変換します。<br>
     * </jp> <en>this method converts the java.sql.Date to java.util.Date format<br>
     * </en>
     * 
     * @param sqlDate :
     *            java.sql.Date object
     * @return java.util.Date object.. Returns null if any exception
     */
    protected static java.util.Date getUtilDate(java.sql.Timestamp sqlDate)
    {
        java.util.Date utilDate = null;
        try
        {
            long sqlDateTemp = sqlDate.getTime();
            utilDate = new java.util.Date(sqlDateTemp);

        }
        catch (Exception exception)
        {
            // return null if any exception
        }
        return utilDate;
    }
}
