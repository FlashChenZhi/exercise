// $Id: P11LogController.java 8041 2012-05-17 09:39:40Z nagao $
package jp.co.daifuku.emanager.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.bluedog.util.Validator;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.database.entity.LogExpImpSet;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.emanager.database.handler.LogExpImpSetHandler;
import jp.co.daifuku.emanager.database.handler.Part11LogHandler;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


/**
 * Part11ログのエクスポートに関する処理を行います。
 *
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  Last commit: $Author: nagao $
 */
public class P11LogController
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

    /**
     * DBコネクション
     */
    private Connection conn = null;

    /**
     * ログエクスポートインポート設定ハンドラ
     */
    private LogExpImpSetHandler expImpSetHandler = null;

    /**
     * Part11ログ情報ハンドラ
     */
    private Part11LogHandler p11LogHandler = null;

    /**
     * CSVファイルの接尾辞フォーマット(ex. yyyyMMdd)
     */
    private String fileSuffixFormat = EmProperties.getProperty(EmConstants.PART11LOG_FILE_SUFFIX_FORMAT);

    /**
     * ログ出力設定情報
     */
    private LogExpImpSet[] settings = null;

    /**
     * エクスポートファイルバックアップパス
     */
    private final String backupDir = EmProperties.getProperty(EmConstants.PART11LOG_BACKUP_PATH);

    /**
     * エクスポート作業用ディレクトリ
     */
    private final String workDir = backupDir + File.separator + "work";

    /**
     * バックアップメディアへコピーするファイルを格納するディレクトリ
     */
    private final String sendDir = backupDir + File.separator + "send";

    /**
     * バックアップメディア用のパス
     */
    private String backupMedia = EmProperties.getProperty(EmConstants.PART11LOG_EXTERNAL_DISK_PATH);

    /**
     * 今日の日付
     */
    private final Calendar today = Calendar.getInstance();

    /**
     * バックアップメディアへのコピーが失敗したかどうか
     */
    private boolean failedCopyToBackupMedia = false;

    /**
     * バックアップメディアへのコピーが失敗したファイル名
     */
    private String copyFailedFileName = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定したコネクションで本クラスを初期化します。
     *
     * @param conn DBコネクション
     */
    public P11LogController(Connection conn)
    {
        this.conn = conn;
        this.expImpSetHandler = EmHandlerFactory.getLogExpImpSetHandler(conn);
        this.p11LogHandler = EmHandlerFactory.getPart11LogHandler(conn);
    }

    //    // fot test
    //    public void setToday(Calendar cal)
    //    {
    //        today.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
    //    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------


    /**
     * ローカルのハードディスクに保存したCSVログファイルを
     * バックアップ用メディアに移動する処理が成功したかどうかを判定します。
     *
     * @return コピー失敗した場合はtrue、それ以外(コピー成功もしくはコピーが行われていない)の場合はfalse
     */
    public boolean isFailedCopyToBackupMedia()
    {
        return failedCopyToBackupMedia;
    }

    /**
     * ローカルのハードディスクに保存したCSVログファイルを
     * バックアップ用メディアに移動する処理が失敗した場合のファイル名を取得します。
     *
     * @return ファイル名
     */
    public String getCopyFailedFileName()
    {
        return copyFailedFileName;
    }

    /**
     * 前回の処理でDVDへコピー失敗したファイルが残っているかどうかを判定します。
     *
     * @return 前回の処理でDVDへのコピーが失敗していた場合はtrue、それ以外はfalse
     */
    public boolean isCopyFailedLastTime()
    {
        // Part11機能が有効かどうかの判定
        if (!P11Config.isPart11Log())
        {
            return false;
        }

        File dir = new File(sendDir);
        // 送信ディレクトリに前回送信できなかったファイルが存在する場合
        if (dir.exists() && dir.list() != null && dir.list().length > 0)
        {
            return true;
        }
        return false;
    }

    /**
     * 送信ディレクトリ内のファイルをDVDへコピーします。
     *
     * @throws IOException
     */
    public void copyToDvD()
            throws IOException
    {
        // Part11機能が有効かどうかの判定
        if (!P11Config.isPart11Log())
        {
            return;
        }

        File dir = new File(sendDir);
        if (dir.exists() && dir.isDirectory())
        {
            // 送信ディレクトリのファイルをバックアップメディアへ移動する
            if (!move(sendDir, backupMedia))
            {
                // 移動に失敗した場合(DVDがセットされていないか、容量がいっぱい)
                failedCopyToBackupMedia = true;
            }
        }
    }

    /**
     * 現在エクスポート処理を実行した場合にバックアップメディアに移動する
     * ログファイルが作成されるかどうかの判定を行います。
     *
     * @return trueの場合、今回の処理でバックアップメディアにバックアップするファイルが作成される
     * @throws SQLException DBエラーが発生
     */
    public boolean hasBackupData()
            throws SQLException
    {
        // Part11機能が有効かどうかの判定
        if (!P11Config.isPart11Log())
        {
            return false;
        }

        //System.out.println("今日は　：　" + new SimpleDateFormat(fileSuffixFormat).format(today.getTime()));

        File dir = new File(sendDir);
        // 送信ディレクトリに前回送信できなかったファイルが存在する場合
        if (dir.exists() && dir.list() != null && dir.list().length > 0)
        {
            return true;
        }

        // ログエクスポート情報を取得
        settings = expImpSetHandler.findAll();

        // 今回出力対象のFrom日付
        EmDate from = null;
        // 次回出力対象のFrom日付
        EmDate nextFrom = null;
        // 今回出力ファイルのファイル内保持期限
        EmDate csvEnd = null;

        // エクスポートファイルが設定されていない場合(システム稼動後一度もログファイルを出力していない場合)
        if (Validator.isEmptyCheck(settings[0].getExportFileName()))
        {
            // 初めてログ出力した日付を取得
            Date startDate = getFirstOutputLogDate();
            if (startDate == null)
            {
                return false;
            }
            else
            {
                EmDate emStartDate = new EmDate(startDate);
                // 現在DBに保持している期間を取得
                int dbHoldDays = EManagerUtil.getBetweenDays(emStartDate, today.getTime());
                // DB保持期間分ログを保持していない場合
                if (dbHoldDays <= P11Config.getDbLogHoldDays())
                {
                    return false;
                }
                from = emStartDate;
                csvEnd = new EmDate(emStartDate);
                csvEnd.add(Calendar.DATE, P11Config.getCsvLogHoldDays() - 1);
            }
        }
        else
        {
            // 現在DBに保持している期間を取得
            int dbHoldDays = EManagerUtil.getBetweenDays(settings[0].getNextExportLogDateFrom(), today.getTime());
            // DB保持期間分ログを保持していない場合は終了
            if (dbHoldDays <= P11Config.getDbLogHoldDays())
            {
                return false;
            }
            from = new EmDate(settings[0].getNextExportLogDateFrom());
            csvEnd = new EmDate(settings[0].getExportFileLogDateTo());
        }

        nextFrom = new EmDate(getLogHoldRangeFrom());

        boolean result = false;

        while (from.beforeDate(nextFrom))
        {
            // 現在出力中のCSVが現在の日付のログで完成する場合(CSVログ保持日付に達した場合)、
            // もしくは月の最終日の場合(ログファイルは月をまたがない)
            // 「バックアップからDBをリストアした場合は必ずしも正確に判定できるとは限らない」
            if (from.equalsDate(csvEnd) || from.isLastDateOfMonth())
            {
                result = true;
                break;
            }

            // 出力するログ日付を一日進める
            from = new EmDate(from.nextDate());
        }

        return result;
    }


    /**
     * Part11ログのエクスポートを行います。
     *
     * @throws SQLException DBエラーが発生
     * @throws IOException 入出力エラーが発生
     */
    public void exportLog()
            throws SQLException,
                IOException
    {
        // Part11機能が有効かどうかの判定
        if (!P11Config.isPart11Log())
        {
            return;
        }

        // ディレクトリの初期化処理と存在チェック
        createFolder();

        // 作業ディレクトリ内をクリアする(前回処理が失敗した時に残っているファイルをクリアする)
        clearDir(workDir);

        // 送信ディレクトリのファイルをバックアップメディアへ移動する(前回コピーに失敗した場合の処理)
        if (!move(sendDir, backupMedia))
        {
            // 移動に失敗した場合(DVDがセットされていないか、容量がいっぱいか)
            failedCopyToBackupMedia = true;
        }

        // ログエクスポート情報を取得
        settings = expImpSetHandler.findAll();

        //System.out.println("今日は　：　" + new SimpleDateFormat(fileSuffixFormat).format(today.getTime()));

        // エクスポートファイルが設定されていない場合(システム稼動後一度もログファイルを出力していない場合)
        // この初期化処理はシステムで一度だけ実行される
        if (Validator.isEmptyCheck(settings[0].getExportFileName()))
        {
            // 初めてログ出力した日付を取得
            Date startDate = getFirstOutputLogDate();
            if (startDate == null)
            {
                return;
            }
            else
            {
                EmDate emStartDate = new EmDate(startDate);
                // 現在DBに保持している期間を取得
                int dbHoldDays = EManagerUtil.getBetweenDays(emStartDate, today.getTime());
                //System.out.println("初期化前 DBログ保持期間　：　" + dbHoldDays);
                // DB保持期間分ログを保持していない場合は終了
                if (dbHoldDays <= P11Config.getDbLogHoldDays())
                {
                    return;
                }
                for (int i = 0; i < settings.length; i++)
                {
                    // エクスポートファイル情報を初期化
                    updateExportFileInfo(settings[i], startDate);
                    // 次回エクスポートデータの開始出力日時を初期化
                    settings[i].setNextExportLogDateFrom(emStartDate.getFirstTimeOfDate());
                }
            }
        }
        else
        {
            // 現在DBに保持している期間を取得
            int dbHoldDays = EManagerUtil.getBetweenDays(settings[0].getNextExportLogDateFrom(), today.getTime());
            //System.out.println("初期化後 DBログ保持期間　： " + dbHoldDays);
            // DB保持期間分ログを保持していない場合は終了
            // システム稼動後にDB保持期間を変更(期間を長くする)した場合を考慮
            // 又は一日に2回以上エクスポートした場合
            if (dbHoldDays <= P11Config.getDbLogHoldDays())
            {
                return;
            }
        }

        // ログファイル保持期間を満たしたファイル名を保持するリスト
        List compliteFilsList = new ArrayList();

        // DBログ保持期間の内最も古い日付の取得
        Date logHoldRangeFrom = getLogHoldRangeFrom();

        for (int i = 0; i < settings.length; i++)
        {
            // 現在出力中(ファイル内保持日数に達していない)のファイルの存在チェック
            File target = new File(backupDir, settings[i].getExportFileName());
            if (target.exists())
            {
                // 出力するログファイルを作業ディレクトリへコピー
                copy(backupDir, workDir, settings[i].getExportFileName());
            }

            // CSVに落とす日付(初期値に前回の処理の時に更新された、今回出力対象となる開始日付を設定する)
            EmDate currentDate = new EmDate(settings[i].getNextExportLogDateFrom());

            // 次回エクスポートデータの開始出力日時を更新
            settings[i].setNextExportLogDateFrom(logHoldRangeFrom);

            // エクスポートファイルに保持するログデータの出力日時の期限を取得
            EmDate csvLogDateTo = new EmDate(settings[i].getExportFileLogDateTo());

            // 最新のLOT_DATEを取得する
            Date latestLogDate = getLatestLogDate(settings[i].getCsvFilePrefix());

            while (currentDate.beforeDate(logHoldRangeFrom))
            {
                // 出力済みの日付じゃないかどうかをチェック
                // (バックアップからデータを戻した場合に同じ日付で2重に出力しないようにチェックする)
                // (通常の処理では必ずifの中が実行される)
                if (latestLogDate == null || currentDate.getFirstTimeOfDate().after(latestLogDate))
                {
                    // 一日分をログ出力
                    CsvLogUtil2.exportData(conn, workDir, settings[i].getExportFileName(),
                            settings[i].getExportTable(), currentDate.getFirstTimeOfDate(),
                            currentDate.getLastTimeOfDate());

                    // 現在出力中のCSVが現在の日付のログで完成する場合(CSVログ保持日付に達した場合)、
                    // もしくは月の最終日の場合(ログファイルは月をまたがない)
                    if (currentDate.equalsDate(csvLogDateTo) || currentDate.isLastDateOfMonth())
                    {
                        //System.out.println("CSVファイル作成完了 : " + settings[i].getExportFileName());
                        // 完成ファイルリストに追加
                        compliteFilsList.add(settings[i].getExportFileName());
                        // エクスポートファイル情報を更新
                        updateExportFileInfo(settings[i], currentDate.nextDate());
                        // エクスポートファイルに保持するログデータの出力日時の期限を更新
                        csvLogDateTo = new EmDate(settings[i].getExportFileLogDateTo());
                    }
                }
                // 既に出力済みの日時の場合は出力しない
                // (バックアップからデータを戻した場合)
                else
                {
                    // 既に出力済みの場合でもエクスポート情報は更新する
                    if (currentDate.equalsDate(csvLogDateTo) || currentDate.isLastDateOfMonth())
                    {
                        updateExportFileInfo(settings[i], currentDate.nextDate());
                        csvLogDateTo = new EmDate(settings[i].getExportFileLogDateTo());
                    }
                }

                // 出力するログ日付を一日進める
                currentDate = new EmDate(currentDate.nextDate());
            }

            // DBログ保持期間外のログを削除する
            p11LogHandler.deleteOutofRangeLog(settings[i].getExportTable(), logHoldRangeFrom);
        }

        // 作業ディレクトリのファイルをバックアップディレクトリへコピー
        copy(workDir, backupDir);

        if (compliteFilsList.size() > 0)
        {
            // バックアップディレクトリ内のファイルの内、今回処理されたファイルのみを読取り専用にします
            setReadOnlyInBackupDir(compliteFilsList);
        }

        // 作業ディレクトリ内をクリアする
        clearDir(workDir);

        for (int i = 0; i < compliteFilsList.size(); i++)
        {
            // 保持期間に達したファイルを送信ディレクトリへコピー
            copy(backupDir, sendDir, (String)compliteFilsList.get(i));
        }

        if (compliteFilsList.size() > 0)
        {
            // 送信ディレクトリ内のファイルの内、今回処理されたファイルのみ読み取り専用にします
            setReadOnlyInSendDir(compliteFilsList);
        }

        // 送信ディレクトリのファイルをバックアップメディアへ移動する
        if (!move(sendDir, backupMedia))
        {
            // 移動に失敗した場合(DVDがセットされていないか、容量がいっぱい)
            failedCopyToBackupMedia = true;
        }

        // ハードディスク保持期限を超過したファイルを削除する
        deleteOutofRangeFile();

        for (int i = 0; i < settings.length; i++)
        {
            // エクスポート情報の更新
            expImpSetHandler.updateExportInfo(settings[i]);
        }
    }

    /**
     * プレフィックスの種類のログファイルに含まれる最も新しいLOG_DATEを取得します。
     *
     * @param csvFilePrefix ファイルプレフィックス
     * @return 最も新しいLOG_DATE
     * @throws IOException
     */
    private Date getLatestLogDate(String csvFilePrefix)
            throws IOException
    {
        // バックアップディレクトリの取得
        File dir = new File(backupDir);
        // プレフィックスでファイルを絞り込む
        File[] files = dir.listFiles(new FilePrefixFilter(csvFilePrefix));

        if (files != null && files.length > 0)
        {
            // ファイル名に含まれる日付の昇順でソート
            Arrays.sort(files, new LatestFileComparator());
            // 最新のファイルを取得
            File latestFile = files[files.length - 1];

            // 最新のファイル名を取得
            String latestFileName = latestFile.getName();

            // 空のファイルではない場合
            if (latestFile.length() > 0)
            {
                // ファイル内の最終レコードを取得
                String lastRecord = getFileLastRecord(backupDir + File.separator + latestFileName);
                if (!Validator.isEmptyCheck(lastRecord))
                {
                    // レコードからLOG_DATEを取得
                    Date latestLogDate = CsvLogUtil2.getLogDateFromRecord(lastRecord);
                    return latestLogDate;
                }
                return null;
            }
            else
            {
                // 空のファイルの場合はファイル名に含まれる日付を取得する
                return getFileNameDate(latestFileName);
            }
        }

        return null;
    }


    // リカバリ用コード不要の為コメントアウト(Part11ログはリカバリしても削除しない)
    // 一応コードを残しておきます

    //    /**
    //     *
    //     * @throws SQLException
    //     * @throws IOException
    //     */
    //    public void recoveryLogFile()
    //            throws SQLException,
    //                IOException
    //    {
    //        // Part11機能が有効かどうかの判定
    //        if (!P11Config.isPart11Log())
    //        {
    //            return;
    //        }
    //
    //        // ログエクスポート情報を取得
    //        settings = expImpSetHandler.findAll();
    //
    //        // エクスポートファイルが設定されていない場合(システム稼動後一度もログファイルを出力していない場合)
    //        if (Validator.isEmptyCheck(settings[0].getExportFileName()))
    //        {
    //            return;
    //        }
    //
    //        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
    //        String recoveryDate = fmt.format(new Date());
    //        String recoveryDir = backupDir + File.separator + "recovery" + recoveryDate;
    //        // リカバリ用ディレクトリを作成
    //        new File(recoveryDir).mkdir();
    //
    //        File bDir = new File(backupDir);
    //        // 作業ディレクトリ内の削除対象となるファイルを取得
    //        File[] wList =
    //                bDir.listFiles(new DeleteBackupFileFilter(
    //                        P11LogController.getFileNameDate(settings[0].getExportFileName())));
    //
    //        if (wList != null && wList.length > 0)
    //        {
    //            for (int i = 0; i < wList.length; i++)
    //            {
    //                File dest = new File(recoveryDir, wList[i].getName());
    //                // 削除対象のファイルはリカバリ用ディレクトリに移動させる
    //                wList[i].renameTo(dest);
    //            }
    //
    //            for (int i = 0; i < settings.length; i++)
    //            {
    //                // 現在出力中ファイルの取得
    //                File currentFile = new File(recoveryDir, settings[i].getExportFileName());
    //                if (currentFile.exists())
    //                {
    //                    EmDate emFrom = new EmDate(P11LogController.getFileNameDate(settings[i].getExportFileName()));
    //                    EmDate emTo = new EmDate(settings[i].getNextExportLogDateFrom());
    //                    emFrom.add(Calendar.DATE, -1);
    //
    //                    // 現在出力中のログファイルを復元する日付の範囲
    //                    // リカバリ用ディレクトリに移動したファイルから、前回日次処理を実行した時点までログデータを復元する範囲
    //                    Date from = emFrom.getLastTimeOfDate();
    //                    Date to = emTo.getFirstTimeOfDate();
    //
    //                    BufferedWriter writer = null;
    //                    BufferedReader reader = null;
    //
    //                    try
    //                    {
    //                        writer = new BufferedWriter(new FileWriter(new File(backupDir, settings[i].getExportFileName())));
    //                        reader = new BufferedReader(new FileReader(currentFile));
    //
    //                        String record = null;
    //                        while ((record = reader.readLine()) != null)
    //                        {
    //                            // CSVの1行を配列に変換
    //                            String[] columns = CsvLogUtil2.getStringArrayFromCsv(record);
    //                            // 1列目をLOG_DATEとする
    //                            String strLogDate = columns[0];
    //
    //                            Date logDate = null;
    //
    //                            try
    //                            {
    //                                logDate = CsvLogUtil2.JAVA_TIMESTAMP_FORMAT.parse(strLogDate);
    //                            }
    //                            catch (ParseException e)
    //                            {
    //                                throw new RuntimeException("ParseDate : [" + strLogDate + "]", e);
    //                            }
    //
    //                            // 復元する対象の日付の場合
    //                            if (logDate.after(from) && logDate.before(to))
    //                            {
    //                                writer.write(record);
    //                                writer.newLine();
    //                            }
    //                        }
    //                    }
    //                    finally
    //                    {
    //                        if (writer != null)
    //                        {
    //                            writer.flush();
    //                            writer.close();
    //                        }
    //                        if (reader != null)
    //                        {
    //                            reader.close();
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //
    //        // 送信用のリカバリディレクトリの作成
    //        String recoverySendDir = recoveryDir + File.separator + "send";
    //        new File(recoverySendDir).mkdir();
    //
    //        File sDir = new File(sendDir);
    //        // 送信ディレクトリ内の削除対象となるファイルを取得
    //        File[] sList =
    //                sDir.listFiles(new DeleteBackupFileFilter(
    //                        P11LogController.getFileNameDate(settings[0].getExportFileName())));
    //
    //        if (sList != null && sList.length > 0)
    //        {
    //            for (int i = 0; i < sList.length; i++)
    //            {
    //                File dest = new File(recoverySendDir, sList[i].getName());
    //                // 削除対象のファイルはリカバリ用ディレクトリに移動させる
    //                sList[i].renameTo(dest);
    //            }
    //        }
    //    }

    /**
     * ファイル内の最終行のデータを取得します。
     * ファイルが存在しない場合、ファイルが空の場合はnullを返します。
     *
     * @param exportFilePath ファイル名
     * @return ファイル内の最終行
     */
    private String getFileLastRecord(String exportFilePath)
            throws IOException
    {
        if (new File(exportFilePath).exists())
        {
            BufferedReader reader = null;
            try
            {
                reader = new BufferedReader(new FileReader(exportFilePath));
                String line = null;
                String temp = null;
                while ((temp = reader.readLine()) != null)
                {
                    line = temp;
                }
                return line;
            }
            finally
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
        }
        return null;
    }

    /**
     * 送信ディレクトリ内にあるファイルを読取り専用にします。
     * 対象となるファイルは今回処理されたファイルのみです。
     *
     * @param compliteFilsList 今回処理されたファイル名を格納したリスト
     */
    private void setReadOnlyInSendDir(List compliteFilsList)
    {
        for (int i = 0; i < compliteFilsList.size(); i++)
        {
            String fileName = (String)compliteFilsList.get(i);
            File tgt = new File(sendDir, fileName);
            if (tgt.exists())
            {
                // 送信ディレクトリ内にあるファイルを読取り専用にする
                tgt.setReadOnly();
            }
        }
    }

    /**
     * バックアップディレクトリ内にあるファイルを読取り専用にします。
     * 対象となるファイルは今回処理されたファイルのみです。
     *
     * @param compliteFilsList 今回処理されたファイル名を格納したリスト
     */
    private void setReadOnlyInBackupDir(List compliteFilsList)
    {
        for (int i = 0; i < compliteFilsList.size(); i++)
        {
            String fileName = (String)compliteFilsList.get(i);
            File tgt = new File(backupDir, fileName);
            if (tgt.exists())
            {
                // 送信ディレクトリ内にあるファイルを読取り専用にする
                tgt.setReadOnly();
            }
        }
    }

    /**
     * エクスポート処理で使用するディレクトリの初期化処理を行います。
     *
     */
    private void createFolder()
    {
        // 存在しない場合ディレクトリ生成
        File work = new File(workDir);
        if (!work.exists())
        {
            work.mkdir();
        }
        File send = new File(sendDir);
        if (!send.exists())
        {
            send.mkdir();
        }

        // システムで定義しているバックアップディレクトリが存在しない場合はエラー
        if (!new File(backupDir).exists())
        {
            throw new RuntimeException("Part11 Log Folder is not found. [" + backupDir + "]");
        }
    }


    /**
     * ハードディスク保持期限を超過したファイルを削除します。
     */
    private void deleteOutofRangeFile()
    {
        // バックアップディレクトリ
        File dir = new File(backupDir);

        for (int i = 0; i < settings.length; i++)
        {
            File[] files = dir.listFiles(new OutofRangeFileFilter(settings[i].getCsvFilePrefix()));
            for (int j = 0; j < files.length; j++)
            {
                if (!files[j].delete())
                {
                    throw new RuntimeException("File Delete Error : [" + files[j].getName() + "]");
                }
            }
        }
    }

    /**
     * エクスポートログファイル名、エクスポートファイルに保持するログデータの出力日時の期限を初期化します。
     *
     * @param ログ出力開始日付
     */
    private void updateExportFileInfo(LogExpImpSet setting, Date startDate)
    {
        // ログファイル作成期間よりエクスポートファイルに保持するログデータの出力日時の期限を求める
        EmDate lastLogDate = new EmDate(startDate);
        lastLogDate.add(Calendar.DATE, P11Config.getCsvLogHoldDays() - 1);
        Date expFileLogDateTo = lastLogDate.getLastTimeOfDate();

        SimpleDateFormat fmt = new SimpleDateFormat(fileSuffixFormat);

        // エクスポートファイル名
        setting.setExportFileName(setting.getCsvFilePrefix() + fmt.format(startDate) + ".csv");
        // エクスポートファイルに保持するログデータの出力日時の期限
        setting.setExportFileLogDateTo(expFileLogDateTo);
    }

    /**
     * システムで初めてログ出力した日付を取得します。
     *
     * @return システムで初めてログ出力した日付
     * @throws SQLException DBエラーが発生
     */
    private Date getFirstOutputLogDate()
            throws SQLException
    {
        Date startDate = null;
        for (int i = 0; i < settings.length; i++)
        {
            // 各テーブルにある最小出力日時を取得
            Date temp = p11LogHandler.findMinLogDate(settings[i].getExportTable());
            if (startDate == null)
            {
                // 初期化
                startDate = temp;
            }
            if (startDate != null && temp != null && temp.before(startDate))
            {
                // より古い日付を保持する
                startDate = temp;
            }
        }
        return startDate;
    }

    /**
     * ディレクトリ内のファイルを削除します。
     *
     * @param dir 対象ディレクトリ
     */
    protected void clearDir(String dir)
    {
        File work = new File(dir);
        File[] files = work.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            if (files[i].isFile())
            {
                files[i].delete();
            }
        }
    }

    /**
     * 移動元ディレクトリ内の全てのファイルを移動先ディレクトリへコピーします。
     *
     * @param fromDir 移動元ディレクトリ
     * @param toDir 移動先ディレクトリ
     * @throws IOException 入出力エラーが発生
     */
    protected void copy(String fromDir, String toDir)
            throws IOException
    {
        File from = new File(fromDir);
        File[] files = from.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            if (files[i].isFile())
            {
                copy(fromDir, toDir, files[i].getName());
            }
        }
    }

    /**
     * ファイルのコピーを行います。
     *
     * @param fromDir コピー元ディレクトリ
     * @param toDir コピー先ディレクトリ
     * @param fileName コピー対象ファイル名
     * @throws IOException 入出力エラーが発生
     */
    protected void copy(String fromDir, String toDir, String fileName)
            throws IOException
    {
        File dest = new File(toDir, fileName);
        if (dest.exists() && !dest.canWrite())
        {
            // 読み取り専用の場合はコピーしない
            return;
        }

        byte[] buff = new byte[8192];

        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        try
        {
            in = new BufferedInputStream(new FileInputStream(new File(fromDir, fileName)));
            out = new BufferedOutputStream(new FileOutputStream(new File(toDir, fileName)));

            int len = -1;
            while ((len = in.read(buff)) != -1)
            {
                out.write(buff, 0, len);
            }
        }
        finally
        {
            if (in != null)
            {
                in.close();
            }
            if (out != null)
            {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * DVDドライブにアクセスできるかどうかを判定します。
     *
     * @param path DVDドライブのパス
     * @return アクセスできる場合はtrue
     */
    public static boolean canAccessDVDDrive(String path)
    {
        boolean result = true;
        try
        {
            File file_bacuDrive = new File(path);

            // if文　2010/04/12 追加 kanda
            if(file_bacuDrive.getUsableSpace() == 0 )
            {
                // メディア存在チェック
                // ※空き領域が0MBの場合、メディアが存在しないのか、容量がないのかが不明である。
                //   "FileSystemUtils"を使用することで、ファイル/フォルダがなければIOExceptionとなるため、
                //   その場合はメディアが存在しないと判断する。
                // 一度プログラムからアクセスしたDVDドライブが次回のアクセスの際に使用不可になっていた場合
                // windowsのエラーメッセージが表示される
                // commons-ioを使用することによりwindowsのエラーメッセージが表示されないようにしている
                FileSystemUtils.freeSpaceKb(path);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * 移動元ディレクトリ内の全てのファイルを移動先ディレクトリへ移動します。
     *
     * @param fromDir 移動元ディレクトリ
     * @param toDir 移動先ディレクトリ
     * @return 一つでも移動に失敗した場合はfalse、それ以外はtrue
     * @throws IOException IOエラーが発生
     */
    protected boolean move(String fromDir, String toDir)
            throws IOException
    {
        File from = new File(fromDir);
        File[] files = from.listFiles();

        boolean canAccessDvd = canAccessDVDDrive(backupMedia);

        for (int i = 0; i < files.length; i++)
        {
            if (files[i].isFile())
            {
                if (!canAccessDvd || !move(fromDir, toDir, files[i].getName()))
                {
                    // 移動に失敗したファイル名を設定
                    copyFailedFileName = files[i].getName();
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * ファイルの移動処理を行います。
     * 既に同名かつ同じ内容のファイルが存在する場合は
     * 移動元のファイルを削除しtrueを返します。。
     *
     * @param fromDir 移動元ディレクトリ
     * @param toDir 移動先ディレクトリ
     * @param fileName 移動対象ファイル名
     * @return 移動に失敗した場合はfalse、それ以外はtrue
     * @throws IOException IOエラーが発生
     */
    private boolean move(String fromDir, String toDir, String fileName)
            throws IOException
    {
        File from = new File(fromDir, fileName);
        File to = new File(toDir, fileName);

        // 既に同名のファイルが存在し、中身が同じ場合はfromのファイルを削除しtrueを返す
        // (バックアップからDBを戻した場合は同名のファイルが移動先に存在する可能性がある)
        // (その場合は移動元のファイルは削除し、移動処理は成功(true)したとして処理を終了する)
        if (to.exists() && FileUtils.contentEquals(from, to))
        {
            from.delete();
            return true;
        }
        return from.renameTo(to);
    }


    /**
     * ログデータ保持期間の最も古い日付を取得します。
     * この日付より古い日付のデータは保持期間対象外となります。
     *
     * @return ログデータ保持期間の最も古い日付
     */
    protected Date getLogHoldRangeFrom()
    {
        Calendar from = (Calendar)today.clone();

        // ログデータ保持期間の最も古い日付を求める
        from.add(Calendar.DATE, -1 * (P11Config.getDbLogHoldDays() - 1));

        //SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        //System.out.println("DBに保持する期間は：" + fmt.format(from.getTime()) + "から" + fmt.format(today.getTime()) + "です");

        Calendar result = (Calendar)from.clone();

        // 日付のみのデータにするため一度クリアする
        result.clear();
        // 年、月、日のみセットする
        result.set(Calendar.YEAR, from.get(Calendar.YEAR));
        result.set(Calendar.MONTH, from.get(Calendar.MONTH));
        result.set(Calendar.DATE, from.get(Calendar.DATE));

        return result.getTime();
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


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: P11LogController.java 8041 2012-05-17 09:39:40Z nagao $";
    }

    /**
     * ログファイル名に含まれている日付を取得します。
     *
     * @param fileName ログファイル名
     * @return ログファイル名に含まれている日付
     */
    static Date getFileNameDate(String fileName)
    {
        String dateFormat = EmProperties.getProperty(EmConstants.PART11LOG_FILE_SUFFIX_FORMAT);

        // CSVファイル名にある日付の取得
        int lastDot = fileName.lastIndexOf(".");
        if (lastDot != -1)
        {
            int dateIndex = lastDot - dateFormat.length();
            if (dateIndex >= 0)
            {
                String csvDate = fileName.substring(dateIndex, lastDot);
                SimpleDateFormat fmt = new SimpleDateFormat(dateFormat);
                try
                {
                    return fmt.parse(csvDate);
                }
                catch (ParseException e)
                {
                    throw new RuntimeException("ParseDate : [" + csvDate + "]", e);
                }
            }
        }
        return null;
    }
}


///**
// * データベースリカバリの際に不要となったログファイルを抽出するためのフィルターです。
// * 不要となったファイルとは、リカバリを行った結果(過去のデータの戻した結果)
// * 作成されているはずのないファイルのことです。
// *
// * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
// * @author  073019
// * @author  Last commit: $Author: nagao $
// */
//class DeleteBackupFileFilter
//        implements FilenameFilter
//{
//    /**
//     * 削除対象となる日付(この日付以降のファイルを対象とする)
//     */
//    private EmDate baseDate = null;
//
//    /**
//     * @param tgtDate
//     */
//    public DeleteBackupFileFilter(Date tgtDate)
//    {
//        baseDate = new EmDate(tgtDate);
//    }
//
//    /* (non-Javadoc)
//     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
//     */
//    public boolean accept(File dir, String name)
//    {
//        // CSVファイル名にある日付の取得
//        Date csvDate = P11LogController.getFileNameDate(name);
//        if (csvDate != null)
//        {
//            EmDate emCsvDate = new EmDate(csvDate);
//            // 削除基準日以降のファイルの場合
//            if (emCsvDate.equalsDate(baseDate) || emCsvDate.afterDate(baseDate))
//            {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//}


/**
 * ファイル名に含まれる日付で比較します。
 * 日付の昇順でソートします。
 *
 *
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  Last commit: $Author: nagao $
 */
class LatestFileComparator
        implements Comparator
{
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object o1, Object o2)
    {
        if (o1 instanceof File && o2 instanceof File)
        {
            File f1 = (File)o1;
            File f2 = (File)o2;

            Date d1 = P11LogController.getFileNameDate(f1.getName());
            Date d2 = P11LogController.getFileNameDate(f2.getName());

            if (d1 != null && d2 != null)
            {
                // d1 < d2
                if (d1.before(d2))
                {
                    return -1;
                }
                // d1 > d2
                else if (d1.after(d2))
                {
                    return 1;
                }
                // d1 == d2
                else
                {
                    return 0;
                }
            }
            throw new IllegalArgumentException("can't get date of file name : " + f1.getName() + " : " + f2.getName());
        }
        throw new IllegalArgumentException("parameter is not instanceof java.io.File : " + o1 + " : " + o2);
    }

}


/**
 * ファイルプレフィックスで絞り込む為のフィルターです。
 *
 *
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  Last commit: $Author: nagao $
 */
class FilePrefixFilter
        implements FilenameFilter
{
    /**
     * 対象ファイルを絞り込む為のファイルプレフィックス条件
     */
    private String prefix = null;

    /**
     *
     * @param prefix CSVファイルプレフィックス
     */
    FilePrefixFilter(String prefix)
    {
        this.prefix = prefix;
    }

    /* (non-Javadoc)
     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
     */
    public boolean accept(File dir, String name)
    {
        if (name.startsWith(prefix))
        {
            return true;
        }
        return false;
    }

}


/**
 * ハードディスク保持期間を超過したログファイルを抽出するためのフィルターです。
 *
 * @version $Revision: 8041 $, $Date: 2012-05-17 18:39:40 +0900 (木, 17 5 2012) $
 * @author  073019
 * @author  Last commit: $Author: nagao $
 */
class OutofRangeFileFilter
        implements FilenameFilter
{
    /**
     * 対象ファイルを絞り込む為のファイルプレフィックス条件
     */
    private String prefix = null;

    /**
     * 日付フォーマット
     */
    private final SimpleDateFormat fmt =
            new SimpleDateFormat(EmProperties.getProperty(EmConstants.PART11LOG_FILE_SUFFIX_FORMAT));

    /**
     * 削除基準日
     */
    private EmDate deleteBaseDate = null;

    /**
     *
     * @param prefix CSVプレフィックス
     */
    OutofRangeFileFilter(String prefix)
    {
        this.prefix = prefix;

        Calendar cal = Calendar.getInstance();;

        // ディスク保持期間の基準日を求める
        // 今日からディスク保持期間(年)を引き、さらにCSV保持期間を引いた日を基準とする
        // 運用後にCSV保持期間を変更した場合は削除されるタイミングがディスク保持期間と一致しない可能性もある
        cal.add(Calendar.YEAR, -1 * P11Config.getHdLogHoldYears());
        cal.add(Calendar.DATE, -1 * (P11Config.getCsvLogHoldDays() - 1));

        // 削除する基準日
        this.deleteBaseDate = new EmDate(cal.getTime());
    }

    /* (non-Javadoc)
     * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
     */
    public boolean accept(File dir, String name)
    {
        if (name.startsWith(prefix))
        {
            // CSVファイル名にある日付の取得
            String csvDate = name.substring(prefix.length(), name.lastIndexOf("."));
            EmDate emCsvDate = null;
            try
            {
                emCsvDate = new EmDate(fmt.parse(csvDate));
            }
            catch (ParseException e)
            {
                throw new RuntimeException("ParseDate : [" + csvDate + "]", e);
            }
            // 削除基準日より以前に作成されたファイルの場合
            if (emCsvDate.equalsDate(deleteBaseDate) || emCsvDate.beforeDate(deleteBaseDate))
            {
                return true;
            }
        }

        return false;
    }

}
