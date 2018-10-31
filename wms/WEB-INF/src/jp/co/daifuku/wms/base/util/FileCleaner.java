// $Id: FileCleaner.java 7969 2010-06-04 00:27:32Z kishimoto $
package jp.co.daifuku.wms.base.util;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jp.co.daifuku.wms.base.common.WmsParam;

/**
 * ファイルの削除、バックアップを作成するクラスです。
 * 日締め処理で使用されます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/02/05</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7969 $, $Date: 2010-06-04 09:27:32 +0900 (金, 04 6 2010) $
 * @author  $Author: kishimoto $
 */
public class FileCleaner
{
    // Class fields --------------------------------------------------
    /** ログファイルのパス */
    private static String LOGFILE_PATH = WmsParam.LOGS_PATH + WmsParam.MESSAGELOG_FILE;

    /** 印刷データ保持日数（日次更新で出力したリストを削除しないように2日間保持） */
    private static final String PRINTDATA_KEEP_DAYS = "2";

    /**
     * UC/X用データファイル設定情報ファイル
     */
    private static final String INI_FILE = "SCHEMA.INI";

    // Class variables -----------------------------------------------

    // Public Class method -------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 7969 $,$Date: 2010-06-04 09:27:32 +0900 (金, 04 6 2010) $");
    }

    /**
     * システムで定義された保持期間をすぎたログファイルを削除します
     */
    public static void clearMessageLog()
    {
        // ログバックアップ保持日数
        String deltime = WmsParam.WMS_LOGFILE_KEEP_DAYS;

        File pathName = new File(LOGFILE_PATH);
        clearFile(pathName.getParent(), deltime);
    }

    /**
     * システムで定義された保持期間をすぎた外部データを削除します
     */
    public static void clearOutData()
    {
        // 外部データバックアップファイルパス
        String filename = WmsParam.HISTORY_HOSTDATA_PATH;
        // 外部データバックアップファイル保持日数
        String deltime = WmsParam.HOSTDATA_KEEP_DAYS;

        clearFile(filename, deltime);
    }

    /**
     * システムで定義された保持期間をすぎた印刷イメージファイルを削除します
     */
    public static void clearPrintHistory()
    {
        // バックアップファイル保持日数
        String deltime = WmsParam.PRINTHISTORY_KEEP_DAYS;
        // 印刷データは2日保持して削除、バックアップファイルはファイル削除日取得し削除
        clearPrintFile(getDate(PRINTDATA_KEEP_DAYS), getDate(deltime));
    }

    /**
     * システムで定義された保持期間をすぎたTomcatのログを削除します
     */
    public static void clearTomcatLog()
    {
        // Tomcatのログのパス
        String filename = WmsParam.TOMCAT_LOGS_PATH;
        // Tomcatのログの保持日数
        String deltime = WmsParam.TOMCATLOGS_KEEP_DAYS;

        clearFile(filename, deltime);
    }

    /**
     * システムで定義された保持期間をすぎたTomcat-RFTのログを削除します
     */
    public static void clearTomcatRFTLog()
    {
        // Tomcat-RFTのログのパス
        String[] filenames = convertToArray(WmsParam.TOMCAT_RFT_LOGS_PATH);
        // Tomcatのログの保持日数
        String deltime = WmsParam.TOMCATLOGS_KEEP_DAYS;

        for (String filename : filenames)
        {
            clearFile(filename, deltime);
        }
    }

    /**
     * システムで定義された保持期間をすぎたIIS(FTP)のログを削除します
     */
    public static void clearIISLog()
    {
        // IIS(FTP)のログファイルパス
        String filename = WmsParam.IIS_LOGS_PATH;
        // IIS(FTP)のログファイル保持日数
        String deltime = WmsParam.IIS_LOGFILE_KEEP_DAYS;

        clearFile(filename, deltime);
    }

    /**
     * システムで定義された保持期間を過ぎたその他のログを削除します
     */
    public static void clearOtherLog()
    {
        // その他のログファイルパス
        String filename = WmsParam.ETC_LOGS_PATH;
        // その他のログファイル保持日数
        String deltime = WmsParam.ETC_LOGFILE_KEEP_DAYS;

        clearFile(filename, deltime);
    }

    /**
     * システムで定義された保持期間を過ぎたFTP履歴ファイルを削除します
     */
    public static void clearFTPBackupFile()
    {
        // FTP履歴ファイルパス
        String filename = WmsParam.FTP_FILE_HISTORY_PATH;
        // FTP履歴ファイル保持日数
        String deltime = WmsParam.FTP_FILE_HISTORY_KEEP_DAYS;

        clearFile(filename, deltime);
    }

    /**
     * パラメータに従って履歴で有る帳票テキストファイルを削除します。
     * @param delDate     印刷データを削除する日付
     * @param delBackDate バックアップファイルを削除する日付
     */
    public static void clearPrintFile(Date delDate, Date delBackDate)
    {
        String fpath = WmsParam.BACKUP_DATA_FILE_PATH;

        // 印刷データ作成フォルダ
        File datapathName = new File(WmsParam.DATA_FILE_PATH);
        File[] datafnames = datapathName.listFiles();
        if (datafnames != null)
        {
            for (int i = 0; i < datafnames.length; i++)
            {
                String fname = datafnames[i].getName().toUpperCase();
                // schema.iniは削除しない。
                // また、ファイルのみ削除対象とする。
                if (!fname.equals(INI_FILE) && datafnames[i].isFile())
                {
                    if (datafnames[i].lastModified() < delDate.getTime())
                    {
                        datafnames[i].delete();
                    }
                }
            }
        }

        File pathName = new File(fpath);
        String[] fnames = pathName.list();
        if (fnames != null)
        {
            for (int i = 0; i < fnames.length; i++)
            {
                File tf = new File(pathName.getPath(), fnames[i]);

                if (tf.lastModified() < delBackDate.getTime())
                {
                    if (!tf.delete())
                    {
                        for (int retry = 0; retry < 100; retry++)
                        {
                            if (tf.delete())
                            {
                                break;
                            }
                            else
                            {
                                for (int l = 0; l < 1000000; l++)
                                {
                                    for (int m = 0; m < 5; m++);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * システムで定義された保持期間を過ぎたAS21TraceLogファイルを削除します
     */
    public static void clearAs21TraceLog()
    {
        // AS21TraceLogファイルパス
        String filename = WmsParam.LOGS_PATH;
        // AS21TraceLogファイル保持日数
        String deltime = WmsParam.AS21_TRACE_HISTORY_KEEP_DAYS;

        clearAS21File(filename, deltime);
    }

    // Private Class method ------------------------------------------
    /**
     * 最新更新日がdeltimeで指定された削除日以前のファイルを削除します。
     * @param path 削除対象ファイルパス
     * @param deltime 削除日
     */
    private static void clearFile(String path, String deltime)
    {
        // 削除日取得
        Date ddate = getDate(deltime);

        // 削除対象ファイルパス取得
        File pathName = new File(path);
        File backuppath = new File(pathName.getPath());

        // バックアップファイル名一覧を取得
        String[] fnames = backuppath.list();

        if (fnames != null)
        {
            for (int i = 0; i < fnames.length; i++)
            {
                File tf = new File(pathName.getPath(), fnames[i]);

                // バックアップファイルの最新更新日時と比較
                if (tf.lastModified() < ddate.getTime())
                {
                    if (!tf.delete())
                    {
                        for (int retry = 0; retry < 100; retry++)
                        {
                            if (tf.delete())
                            {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 指定されたファイルパスのファイルを削除します。
     * @param path 削除対象ファイルパス
     */
    private static void clearFile(String path)
    {

        File pathName = new File(path);
        // 削除対象ファイルパス取得
        File backuppath = new File(pathName.getPath());

        // バックアップファイル名一覧を取得
        String[] fnames = backuppath.list();

        if (fnames != null)
        {
            for (int i = 0; i < fnames.length; i++)
            {
                File tf = new File(pathName.getPath(), fnames[i]);

                if (!tf.delete())
                {
                    for (int retry = 0; retry < 100; retry++)
                    {
                        if (tf.delete())
                        {
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 指定されたファイルパスのファイルを削除します。
     * @param path 削除対象ファイルパス
     * @param deltime 削除日
     */
    private static void clearAS21File(String path, String deltime)
    {
        // 削除日取得
        Date ddate = getDate(deltime);

        // 削除対象ファイルパス取得
        File pathName = new File(path);
        File backuppath = new File(pathName.getPath());

        // バックアップファイル名一覧を取得
        String[] fnames = backuppath.list();

        if (fnames != null)
        {
            for (int i = 0; i < fnames.length; i++)
            {
                File tf = new File(pathName.getPath(), fnames[i]);

                // ここではメッセージファイルは消さない
                // ディレクトリは無視
                // 拡張子がtxtのファイル(バックアップファイル以外)は消さない
                // バックアップファイルの最新更新日時と比較
                if (!tf.getName().equals(WmsParam.MESSAGELOG_FILE) && tf.isFile()
                        && !"txt".equals(getExtension(tf.getName())) && tf.lastModified() < ddate.getTime())
                {
                    if (!tf.delete())
                    {
                        for (int retry = 0; retry < 100; retry++)
                        {
                            if (tf.delete())
                            {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 現在の日時から引数の期間を引いた日付を取得します。
     * @param keeptime 期間
     * @return 現在の日時から引数の期間を引いた日付
     */
    private static Date getDate(String keeptime)
    {
        // ファイル削除日取得
        Calendar curdate = Calendar.getInstance();
        int kday = Integer.parseInt("-" + keeptime);
        curdate.add(Calendar.DAY_OF_YEAR, kday + 1);
        curdate.set(Calendar.HOUR_OF_DAY, 0);
        curdate.set(Calendar.MINUTE, 0);
        curdate.set(Calendar.SECOND, 0);

        return (curdate.getTime());
    }

    /**
     * プレビュー用データを削除します
     */
    public static void clearPdfFile()
    {
        // PDFデータの作成先ディレクトリのパス
        String dirpath = WmsParam.PREVIEW_DATA_FILE_PATH;

        File dir = new File(dirpath);

        // 削除対象ディレクトリ一覧取得
        String[] ddirpaths = dir.list();

        if (ddirpaths == null)
        {
            return;
        }
        
        for(String ddir : ddirpaths)
        {
            clearFile(dirpath + "/" + ddir);
        }
    }


    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**
     * ArratListをString[]に変換して戻します。<BR>
     * 
     * @param aryParam ArrayList
     * @return String[]
     */
    protected static String[] convertToArray(ArrayList<String> aryParam)
    {
        if (aryParam.size() > 0)
        {
            // セットするリストセルデータがあれば値をセットします
            return aryParam.toArray(new String[aryParam.size()]);
        }
        else
        {
            // セットするリストセルデータがなければnullをセットします
            return new String[0];
        }
    }

    /**
     * ファイル名から拡張子を取り出します。<BR>
     * 
     * @param fileName String
     * @return String
     */
    protected static String getExtension(String fileName)
    {
        // nullなら処理なし
        if (fileName == null)
        {
            return null;
        }

        // .部分を検索
        int point = fileName.lastIndexOf(".");

        // 発見できれば取り出す
        if (point != -1)
        {
            return fileName.substring(point + 1);
        }
        return fileName;
    }

    // Private methods -----------------------------------------------

    // Debug methods -------------------------------------------------

}
//end of class
