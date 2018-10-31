// $Id: DeleteLogFile.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util;

import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.WmsParam;

/*
 * Copyright 2002-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ログファイルや履歴ファイルの削除を行うクラス
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/03</TD><TD></TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class DeleteLogFile
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }


    /**
     * 各種のログファイルの削除を行います。
     * @throws ScheduleException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    protected static void deleteLog() throws ScheduleException
    {
        try
        {
            /*
             * ログファイルの削除を行います。
             */
            /* 保持期間を超えたIIS(FTP)ログファイルの削除 */
            FileCleaner.clearIISLog();

            /* 保持期間を超えたその他ログファイルの削除 */
            FileCleaner.clearOtherLog();

            /* FTP履歴ファイルの圧縮 */
            CompressToZipFile.makeZipFile(WmsParam.FTP_FILE_HISTORY_PATH);

            /* 保持期間を超えた FTP履歴ファイルの削除 */
            FileCleaner.clearFTPBackupFile();
        }
        catch (Exception e)
        {
            throw new ScheduleException(e.getMessage());
        }
    }

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * ログファイルや履歴ファイルの削除を行うクラスです。<BR>
     * 保持期間を超えた各種ログファイルを削除します。
     * @param args (未使用)
     */
    public static void main(String[] args)
    {
        try
        {
            System.out.println("DeleteLogFile Start.");
            deleteLog();
            System.out.println("DeleteLogFile Complete.");
        }
        catch (ScheduleException e)
        {
            System.out.println("DeleteLogFile [Exception occurred!]");
        }
    }


    // Package methods -----------------------------------------------
    // Protected methods ---------------------------------------------
    // Private methods -----------------------------------------------
}
//end of class
