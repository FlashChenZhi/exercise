// $Id: Part11LogHandler.java 3965 2009-04-06 02:55:05Z admin $
package jp.co.daifuku.emanager.database.handler;

import java.sql.SQLException;
import java.util.Date;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * <jp>Part11ログに関する情報を取得するためのinterfaceです。 <br></jp>
 * 
 * @author $Author: T.Ogawa
 */
public interface Part11LogHandler
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
    // public methods
    //------------------------------------------------------------

    /**
     * 最小のログ出力日付を取得します。
     * 
     * @param table テーブル名
     * @return 最小のログ出力日付
     * @throws SQLException
     */
    public Date findMinLogDate(String table)
            throws SQLException;

    /**
     * 基準日以前のレコードを削除します。
     * 
     * @param table テーブル名
     * @param baseDate 基準日
     * @return 削除件数
     * @throws SQLException
     */
    public int deleteOutofRangeLog(String table, Date baseDate)
            throws SQLException;

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
}
