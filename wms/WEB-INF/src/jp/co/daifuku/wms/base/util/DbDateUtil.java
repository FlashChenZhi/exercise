// $Id: DbDateUtil.java 6901 2010-01-25 11:22:02Z kumano $
package jp.co.daifuku.wms.base.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * DBから日付を取得するクラスです。<br>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/03/02</TD><TD>T.kishimoto</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 6901 $, $Date: 2010-01-25 20:22:02 +0900 (月, 25 1 2010) $
 * @author  kishimoto
 * @author  Last commit: $Author: kumano $
 */

public final class DbDateUtil
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     */
    private DbDateUtil()
    {

    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBからシステム日時を取得し、yyyyMMdd形式の文字列を返します。
     * @return 取得日時(yyyyMMdd)
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public static String getSystemDate()
            throws ReadWriteException
    {
        return DbDateUtil.getTimeStampFormat("yyyyMMdd");
    }

    /**
     * DBからシステム日時を取得し、yyyyMMddHHmmss形式の文字列を返します。
     * @return 取得日時(yyyyMMddHHmmss)
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public static String getSystemDateTime()
            throws ReadWriteException
    {
        return DbDateUtil.getTimeStampFormat("yyyyMMddHHmmss");
    }

    /**
     * DBからシステム日時を取得し、yyyyMMddHHmmssSSS形式の文字列を返します。
     * @return 取得日時(yyyyMMddHHmmssSSS)
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public static String getSystemDateTimeStamp()
            throws ReadWriteException
    {
        return DbDateUtil.getTimeStampFormat("yyyyMMddHHmmssSSS");
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


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * DBからシステム日時を取得し、指定されたフォーマットの文字列を返します。
     * @param fmt フォーマット形式
     * @return 取得日時
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private static String getTimeStampFormat(String fmt)
            throws ReadWriteException
    {
        // フォーマット形式のセット
        SimpleDateFormat frmt = new SimpleDateFormat(fmt);

        // 取得したデータをフォーマット
        return frmt.format(getTimeStamp());
    }

    /**
     * DBからシステム日時を取得して返します。
     * @return 取得日時
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public static synchronized Date getTimeStamp()
            throws ReadWriteException
    {
        Connection conn = null;
        DefaultDDBHandler ddbHandler = null;
        ResultSet resultset = null;
        try
        {
            // コネクションの取得
            conn = WmsParam.getConnection();

            ddbHandler = new DefaultDDBHandler(conn);

            // SQLの実行
            ddbHandler.execute("SELECT SYSTIMESTAMP FROM DUAL");

            resultset = ddbHandler.getResultSet();

            // フォーマット形式のセット
            while (resultset.next())
            {
                // 取得したデータを返します
                return resultset.getTimestamp(1);
            }
            throw new RuntimeException("'SELECT SYSTIMESTAMP FROM DUAL' returns no record.");
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, DbDateUtil.class.getName());
            throw new ReadWriteException(e);
        }
        finally
        {
            try
            {
                // コネクションのクローズを行う
                if (conn != null)
                {
                    ddbHandler.close();
                    conn.close();
                }
            }
            catch (SQLException e)
            {
                // ignore close exception.
            }
        }
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: DbDateUtil.java 6901 2010-01-25 11:22:02Z kumano $";
    }
}
