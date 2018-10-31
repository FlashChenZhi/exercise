// $Id: AbstractReportDataCreator.java 7733 2010-03-26 06:21:56Z okayama $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.PrePostFileFilter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.ExchangeHistory;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.bsr.BSRInfo;
import jp.co.daifuku.wms.base.util.bsr.BSRWriter;
import jp.co.daifuku.wms.base.util.timekeeper.ScheduleJob;

/**
 * 報告データ作成処理のための仮想スーパークラスです。<br>
 * 上位への報告データ作成処理を行う実行処理を実装しています。<br>
 * 各種実績の報告処理は、このクラスを継承して実装して下さい。
 *
 * @version $Revision: 7733 $, $Date: 2010-03-26 15:21:56 +0900 (金, 26 3 2010) $
 * @author  admin
 * @author  Last commit: $Author: okayama $
 */
public abstract class AbstractReportDataCreator
        implements ReportDataCreator, ScheduleJob
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * <code>REPORTDATA_BSRCATEGORY</code><br>
     * 報告データ作成のBSRカテゴリ
     */
    public static final String BSRCATEGORY_REPORTDATA = "RESULTREPORT";

    /** Finderから実績データを一度に取得する件数 **/
    protected static final int RESULT_READ_QTY = 100;

    /** TMPファイル用拡張子 */
    protected static final String TMP_EXTENSION = ".tmp";

    /** エラーファイル用拡張子 */
    protected static final String ERR_EXTENSION = ".err";

    /** ピリオド */
    protected static final String PERIOD = ".";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    /** この抽象クラスを継承した具象クラス間での同期用のオブジェクトです。*/
    private static final Object $mutex = new Object();

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** 報告データ件数 */
    private int _reportCount = 0;

    /** 報告データファイルパス */
    private String _fileDirectory = "";

    /** 報告データファイル名 */
    private String _fileName = "";

    /** 報告単位 */
    private String _reportType = "";

    /** 実績報告最終ファイル名 */
    private String _resultFileName;

    /** 拡張子 */
    private String _extention = "";

    /** データ区分 */
    private String _exchangeJob = "";

    /** 送受信開始日時 */
    private Date _startDate = null;

    /** データベースコネクション */
    private Connection _conn = null;

    /** DnExchangeHistoryのトランザクションを管理するコネクション */
    private Connection _errConn = null;

    /**
     * メッセージ保持エリア.<br>
     * 各メソッドの呼び出しで条件エラー等が発生した場合にその内容を保持するために使用します。
     */
    private String _message = "";

    /** 呼び出し元クラス (直上のクラスではない場合がある) */
    private Class _caller;

    /** Prefixファイル検索タイプ（Prefix指定:true, ファイル指定:false） */
    private boolean _prefixSelectType = true;

    /** 取込ファイル名 */
    private String _dataName = null;

    /** 取込種別 */
    private String _dataType = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションを保持するインスタンスを生成します。
     * 
     * @param conn データベースコネクション
     */
    protected AbstractReportDataCreator(Connection conn)
    {
        super();
        setConnection(conn);
    }

    /**
     * データベースコネクションを保持するインスタンスを生成します。
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    protected AbstractReportDataCreator(Connection conn, Class caller)
    {
        super();
        setConnection(conn);
        setCaller(caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * <CODE>report</CODE>メソッドの実績報告データの作成有無を通知します。
     * 
     * @return 実績報告データを作成した場合は、true<br>
     * 報告データが無かった場合は、falseを返します.
     */
    public boolean isReport()
    {
        return (getReportCount() > 0);
    }

    /**
     * 一時ファイルが存在するかチェックを行ないます。
     * @return 存在する場合は「<CODE>true</CODE>」存在しない場合は「<CODE>false</CODE>」を返します。<br>
     */
    public boolean isExistTempFile()
    {
        // 一時ファイル保存パスを取得
        File file = new File(WmsParam.HOSTDATA_LOCAL_PATH);
        FilenameFilter ff = new PrePostFileFilter(getFileName(), getExtention());

        String[] tmpFiles = file.list(ff);
        if (ArrayUtil.isEmpty(tmpFiles))
        {
            return false;
        }
        setResultFileName(tmpFiles[0]);
        return true;
    }

    /**
     * 一時ファイルの数を取得します。<br>
     * @return int 検出したファイルの数を返します。
     */
    public int existTempFilesCount()
    {
        // 一時ファイル保存パスを取得
        File file = new File(WmsParam.HOSTDATA_LOCAL_PATH);
        FilenameFilter ff = new PrePostFileFilter(getFileName(), getExtention());

        String[] tmpFiles = file.list(ff);
        if (ArrayUtil.isEmpty(tmpFiles))
        {
            return 0;
        }
        return tmpFiles.length;
    }

    /**
     * データ報告実績ファイルの作成処理を行ないます。<br>
     * 一時ファイルを取得し、環境設定画面にて指定されたディレクトリにコピーし、
     * 実績ファイル名（Prefix名+TimeStamp+.txt)にリネームします。<br>
     * 実績ファイルが正常に作成できた場合は、一時ファイルをWmsParamに定義されている
     * HISTORY_HOSTDATA_PATHに実績ファイル名で退避します。
     * 
     * @return 正常に処理が完了した場合は「<CODE>True</CODE>」<br>
     *     それ以外は「<CODE>false</CODE>」を返します。
     * @throws IOException スローされません。
     * @throws ReadWriteException スローされません。
     */
    @SuppressWarnings("unused")
    public boolean sendReportFile()
            throws IOException,
                ReadWriteException
    {
        // 残っているエラーファイルを履歴ディレクトリに移動
        moveErrorFilesToHistory();

        // 一時ファイルパスの生成
        File localResult = new File(WmsParam.HOSTDATA_LOCAL_PATH, getResultFileName());
        // コピーファイル
        File copyFile = new File(getFileDirectory(), getFileName() + TMP_EXTENSION);

        // 対象データが存在しない場合は処理をしない
        if (existTempFilesCount() == 0)
        {
            return true;
        }
        // 作成データを環境設定のフォルダへコピー。(Prefix名 + .tmp)
        if (!copy(localResult, copyFile))
        {
            // ロギング 実績ファイルの送信に失敗しました。
            RmiMsgLogClient.write(6024039, getClass().getName());
            return false;
        }

        // 成功した場合は実績ファイルのリネーム(送信)、一時フォルダの退避処理を行なう。
        // 実績ファイルのリネーム
        File hostResultFile = new File(getFileDirectory(), getResultFileName());
        if (!moveFile(copyFile, hostResultFile))
        {
            // ロギング 実績ファイルの送信に失敗しました。
            RmiMsgLogClient.write(6024039, getClass().getName());
            return false;
        }

        // 実績ファイルのコピーが正常に終了した場合は履歴ファイルの退避
        // 実績ファイル完成後、一時ファイルを履歴に移動
        File histResult = new File(WmsParam.HISTORY_HOSTDATA_PATH, getResultFileName());
        if (!moveFile(localResult, histResult))
        {
            // ロギング 実績ファイルを履歴として保存できませんでした。
            RmiMsgLogClient.write(6024040, getClass().getName());

            String errFileName = replaceExt(localResult.getName(), ERR_EXTENSION);
            File errFile = new File(localResult.getParent(), errFileName);
            if (!moveFile(localResult, errFile))
            {
                // ロギング 実績ファイルをエラーファイルに改名できませんでした。
                RmiMsgLogClient.write(6024041, getClass().getName());
            }
            setExtention("");
            return false;
        }
        setExtention("");
        return true;
    }

    /**
     *  自動処理により、指定された時間に実行されるメソッドです。<br>
     *  実績の報告処理を呼び出して、ホストに実績を報告します。<br>
     *  この処理の内部では このクラスのフィールドをmutexとして、<br>
     *  処理のほとんどを同期しています。したがって、このクラスのサブクラス
     *  においては、送信処理が同時に実行されることはありません。
     *  
     *  @param  args 起動アーギュメント (未使用)
     */
    public void execute(String[] args)
    {
        synchronized ($mutex)
        {
            writeBSR("実績送信処理を開始します", BSRInfo.BEING_PROCESSED, 0); // TODO NLS

            boolean flagUpdated = false;
            WarenaviSystemController wsc = null;
            try
            {
                Connection conn = WmsParam.getConnection();
                setConnection(conn);
                _errConn = WmsParam.getConnection();

                // 残っているエラーファイルを履歴ディレクトリに移動
                moveErrorFilesToHistory();

                // 送信可能条件チェック
                wsc = new WarenaviSystemController(conn, getClass());
                if (!canSend(conn, wsc))
                {
                    return;
                }
                // 報告データ作成中フラグを「起動中」にする。
                if (!updateSendFlag(conn, wsc, true))
                {
                    // すでに報告データ作成中のため、処理を中止
                    writeBSR("実績送信中のため処理を中止します", BSRInfo.WARNINGLY_TERMINATED, 0); // TODO NLS
                    return;
                }
                flagUpdated = true;

                // 一時ファイルより実績ファイルを生成します。
                ReportDataCreator creator = getReportClass();
                int restCnt = creator.existTempFilesCount();

                // 残っている未送信実績を送信
                for (int i = 0; i < restCnt; i++)
                {
                    // 対象一時ファイルの決定
                    if (!creator.isExistTempFile())
                    {
                        continue;
                    }
                    // 未送信のデータ報告実績ファイルの送信を行います
                    if (!creator.sendReportFile())
                    {
                        // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
                        RmiMsgLogClient.write("6007031", getClass().getName());
                        continue; // これ以降に他の処理が入ったときのために continueしておく
                    }
                }

                // 実績作成処理を実行します
                if (report(conn, creator))
                {
                    writeBSR("実績送信処理を正常に完了しました", BSRInfo.NORMAL_COMPLETED, 0); // TODO NLS
                    conn.commit();
                    getConnectionForError().commit();
                }
                else
                {
                    writeBSR("実績送信処理でエラーが発生しました", BSRInfo.WARNINGLY_TERMINATED, 1); // TODO NLS
                    conn.rollback();
                    getConnectionForError().commit();
                }
            }
            catch (SQLException e)
            {
                RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
                // 6006002=データベースエラーが発生しました。{0}
                String msg = WmsMessageFormatter.format(6006002);
                writeBSR(msg, BSRInfo.ABNORMAL_TERMINATED, 2);
            }
            catch (ReadWriteException e)
            {
                RmiMsgLogClient.writeTraceOnly(e, getClass().getName());
                // 6006002=データベースエラーが発生しました。{0}
                String msg = WmsMessageFormatter.format(6006002);
                writeBSR(msg, BSRInfo.ABNORMAL_TERMINATED, 2);
            }
            catch (ScheduleException e)
            {
                // WarenaviSystemControllerのインスタンス化に失敗しました。
                RmiMsgLogClient.writeTraceOnly(e, getClass().getName());
                // 6006021=内部エラーが発生しました。{0}
                String msg = WmsMessageFormatter.format(6006021);
                writeBSR(msg, BSRInfo.ABNORMAL_TERMINATED, 3);
            }
            catch (IOException e)
            {
                // データ報告実績ファイルの生成に失敗しました。
                RmiMsgLogClient.writeTraceOnly(e, getClass().getName());
                // 6006020=ファイルの入出力エラーが発生しました。{0}
                String msg = WmsMessageFormatter.format(6006020);
                writeBSR(msg, BSRInfo.ABNORMAL_TERMINATED, 4);
            }
            catch (DataExistsException e)
            {
                RmiMsgLogClient.writeTraceOnly(e, getClass().getName());
                // 6006002=データベースエラーが発生しました。{0}
                String msg = WmsMessageFormatter.format(6006002);
                writeBSR(msg, BSRInfo.ABNORMAL_TERMINATED, 2);
            }
            finally
            {
                Connection conn = getConnection();
                // 実績送信中 OFF
                if (flagUpdated)
                {
                    updateSendFlag(conn, wsc, false);
                }
                close(conn);
                closeForErr(getConnectionForError());
            }
        }
    }

    /**
     * 呼び出し元クラスを返します。
     * @return 呼び出し元クラス
     */
    public Class getCaller()
    {
        return _caller;
    }

    /**
     * 呼び出し元クラスを設定します。
     * @param caller 呼び出し元クラス<br>
     * nullが指定されたときは、呼び出し先クラスがセットされます。
     */
    public void setCaller(Class caller)
    {
        Class actClass = (null == caller) ? getClass()
                                         : caller;
        _caller = actClass;
    }

    /**
     * 呼び出し元クラス名を返します。
     * @return 呼び出し元クラスの短縮名
     */
    public String getCallerName()
    {
        return getCaller().getSimpleName();
    }

    /**
     * BSRへのメッセージを書き込みます。
     * 
     * @param msg メッセージ
     * @param fac ファシリティ
     * @param status ステータス
     */
    private void writeBSR(String msg, int fac, int status)
    {
        try
        {
            BSRWriter.write(BSRCATEGORY_REPORTDATA, fac, status, msg, getClass());
        }
        catch (ReadWriteException e)
        {
            RmiMsgLogClient.writeTraceOnly(e, getClass().getName());
        }
    }

    /**
     * 実績データ報告処理を呼び出します。<br>
     * 
     * @param conn
     * @param creator
     * @return 処理が成功したとき true.
     * @throws IOException
     * @throws ReadWriteException
     */
    protected boolean report(Connection conn, ReportDataCreator creator)
            throws IOException,
                ReadWriteException,
                DataExistsException
    {
        // 実績データの作成
        if (creator.report())
        {
            if (creator.isReport())
            {
                // 6020070=報告処理を開始します。
                RmiMsgLogClient.write(6020070, getClass().getSimpleName());
                // 実績ファイルの送信
                if (!creator.sendReportFile())
                {
                    // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
                    RmiMsgLogClient.write(6007031, getClass().getName());
                    // 交換データ通信履歴作成(送信中にエラー発生)
                    createExchangeHistory(ExchangeHistory.STATUS_ERR_SEND, creator);
                }
                else
                {
                    // 交換データ通信履歴作成(正常)
                    createExchangeHistory(ExchangeHistory.STATUS_NORMAL, creator);
                }
                // 6020071=報告処理を終了します。
                RmiMsgLogClient.write(6020071, getClass().getSimpleName());
            }
            return true;
        }
        else
        {
            // 交換データ通信履歴作成(例外)
            createExchangeHistory(ExchangeHistory.STATUS_EXCEPTION, creator);
            return false;
        }
    }

    /**
     *  終了メソッド<br>
     *  終了時に自動処理より実行されるメソッドです。
     */
    public void terminate()
    {
        // 報告データ作成処理では、このメソッドで何も行いません。
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * _ReportCountを設定します。<br>
     * @param reportCount 報告データ件数<br>
     */
    protected void setReportCount(int reportCount)
    {
        _reportCount = reportCount;
    }

    /**
     * _ReportCountを返します。<br>
     * @return _reportCount<br>
     */
    protected int getReportCount()
    {
        return _reportCount;
    }

    /**
     * 実績報告データファイルディレクトリを設定します。<br>
     * @param dir ディレクトリ<br>
     */
    protected void setFileDirectory(String dir)
    {
        _fileDirectory = dir;
    }

    /**
     * 実績報告データファイルのディレクトリを返します。<br>
     * @return 実績報告データファイルのディレクトリ<br>
     */
    protected String getFileDirectory()
    {
        return _fileDirectory;
    }

    /**
     * 実績報告データファイル名を設定します。<br>
     * @param fileName 実績報告データファイル名<br>
     */
    protected void setFileName(String fileName)
    {
        _fileName = fileName;
    }

    /**
     * 実績報告データファイル名を返します。<br>
     * @return 実績報告データファイル名を返します。<br>
     */
    protected String getFileName()
    {
        return _fileName;
    }

    /**
     * 報告単位を設定します。<br>
     * @param reportType 報告単位<br>
     */
    protected void setReportType(String reportType)
    {
        _reportType = reportType;
    }

    /**
     * 報告単位を返します。<br>
     * @return 報告単位<br>
     */
    protected String getReportType()
    {
        return _reportType;
    }

    /**
     * 実績報告最終ファイル名を返します。
     * @return _resultFileNameを返します。
     */
    public String getResultFileName()
    {
        return _resultFileName;
    }

    /**
     * 実績報告最終ファイル名を設定します。
     * @param resutlFileName 実績報告最終ファイル名
     */
    protected void setResultFileName(String resultFileName)
    {
        _resultFileName = resultFileName;
    }

    /**
     * 拡張子を返します。
     * @return 拡張子
     */
    protected String getExtention()
    {
        return _extention;
    }

    /**
     * 拡張子を設定します。
     * @param extention 拡張子
     */
    protected void setExtention(String extention)
    {
        _extention = extention;
    }

    /**
     * データ区分を返します。
     * @return データ区分
     */
    protected String getExchangeJob()
    {
        return _exchangeJob;
    }

    /**
     * データ区分を設定します。
     * @param exchangeJob データ区分
     */
    protected void setExchangeJob(String exchangeJob)
    {
        _exchangeJob = exchangeJob;
    }

    /**
     * 送受信開始日時を取得します。
     * @return 送受信開始日時
     */
    public Date getStartDate()
    {
        return _startDate;
    }

    /**
     * 送受信開始日時を設定します。
     * @param startDate 送受信開始日時
     */
    protected void setStartDate(Date startDate)
    {
        _startDate = startDate;
    }

    /**
     * データベースのコネクションを設定します。<br>
     * @param conn データベースのコネクション<br>
     */
    protected void setConnection(Connection conn)
    {
        this._conn = conn;
    }

    /**
     * データベースのコネクションを返します。<br>
     * @return データベースのコネクション。<br>
     */
    protected Connection getConnection()
    {
        return _conn;
    }

    /**
     * DnExchangeHistory用のコネクションを取得します
     * @return Connection
     */
    protected Connection getConnectionForError()
    {
        return _errConn;
    }

    /**
     * メッセージをセットします。<br>
     * @param message セットするメッセージ<br>
     */
    protected void setMessage(String message)
    {
        _message = message;
    }

    /**
     * <CODE>startSCH()</CODE>メソッド、<CODE>check()</CODE>メソッドでfalseが戻された場合に、<br>
     * その理由を表示するためのメッセージを取得します。<br>
     * このメソッドは画面のメッセージエリアへの表示内容を取得するために使用します。<br>
     * @return メッセージ<br>
     */
    public String getMessage()
    {
        return _message;
    }

    /**
     * Prefixファイル検索タイプを返します。
     * @return fileSelectTypeを返します。
     */
    protected boolean getPrefixSelectType()
    {
        return _prefixSelectType;
    }

    /**
     * Prefixファイル検索タイプを設定します。
     * @param fileSelectType Prefixファイル検索タイプ
     */
    protected void setPrefixSelectType(boolean prefixSelectType)
    {
        _prefixSelectType = prefixSelectType;
    }

    /**
     * 取込ファイル名を返します。
     * @return 取込ファイル名
     */
    protected String getDataName()
    {
        return _dataName;
    }

    /**
     * 取込ファイル名を設定します。
     * @param dataName 取込ファイル名
     */
    protected void setDataName(String dataName)
    {
        _dataName = dataName;
    }

    /**
     * 取込種別を返します。
     * @return 取込種別
     */
    protected String getDataType()
    {
        return _dataType;
    }

    /**
     * 取込種別を設定します。
     * @param dataType 取込種別
     */
    protected void setDataType(String dataType)
    {
        _dataType = dataType;
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 対象のディレクトリが存在しない場合にディレクトリを作成します。
     * @param fileName  ファイル名(パス付き)
     */
    protected static void prepareDir(String fileName)
    {
        // パス名で指定されたディレクトリが存在しない場合は、ディレクトリを作成する。
        File fileObject = new File(fileName);
        if (!fileObject.exists())
        {
            File dirObject = new File(fileObject.getParent());
            if (!dirObject.isDirectory())
            {
                // ディレクトリを作成する
                dirObject.mkdirs();
            }
        }
    }

    /**
     * エラーファイルを履歴ディレクトリに移動します。
     */
    protected void moveErrorFilesToHistory()
    {
        File localDir = new File(WmsParam.HOSTDATA_LOCAL_PATH);
        FileFilter ff = new PrePostFileFilter(null, ERR_EXTENSION);
        File[] errFiles = localDir.listFiles(ff);
        String ext = getExtention();

        for (File errFile : errFiles)
        {
            String newFileName = replaceExt(errFile.getName(), ext);
            File histFile = new File(WmsParam.HISTORY_HOSTDATA_PATH, newFileName);
            if (!moveFile(errFile, histFile))
            {
                // エラーファイルを履歴ディレクトリに移動できませんでした。
                RmiMsgLogClient.write(6024042, getClass().getName());
            }
        }
    }

    /**
     * ファイル名の拡張子部分を入れ替えます。
     * 
     * @param org 元のファイル名
     * @param newExt 新しい拡張子 (ドット付を指定してください)
     * @return 拡張子を入れ替えたファイル名
     */
    protected String replaceExt(String org, String newExt)
    {
        int point = org.lastIndexOf(PERIOD);
        String fname = (point < 0) ? org
                                  : org.substring(0, point);
        String newFileName = fname + newExt;

        return newFileName;
    }

    /**
     * ファイルを指定先へコピーする処理を行います。<br>
     * 第１引数のinFileを第２引数outFileへコピーします。<br>
     * @param inFile    コピー元ファイル
     * @param outFile  コピー先ファイル
     * @return コピー成功[<CODE>true</CODE>]
     *          コピー失敗[<CODE>false</CODE>]
     */
    protected static boolean copy(File inFile, File outFile)
    {
        FileChannel srcChannel = null;
        FileChannel destChannel = null;
        try
        {
            srcChannel = new FileInputStream(inFile).getChannel();
            destChannel = new FileOutputStream(outFile).getChannel();

            srcChannel.transferTo(0, srcChannel.size(), destChannel);
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
        finally
        {
            close(srcChannel);
            close(destChannel);
        }
    }

    /**
     * クローズ可能なオブジェクトをクローズします。<br>
     * 内部で null チェックを行っていますので、null である
     * 可能性があってもエラーになりません。
     * 
     * @param ioc クローズ可能オブジェクト
     */
    protected static void close(Closeable ioc)
    {
        if (null != ioc)
        {
            try
            {
                ioc.close();
            }
            catch (IOException e)
            {
                // ignore errors on close.
            }
        }
    }

    /**
     * ファイルを移動します。<br>
     * 単純な移動に失敗したときはファイルをコピーしてから
     * 元のファイルを削除します。
     * 
     * @param srcFile 移動元ファイル
     * @param tgtFile 移動先ファイル
     * @return 正常に処理できたとき true.
     */
    protected boolean moveFile(File srcFile, File tgtFile)
    {
        // 単純移動
        if (srcFile.renameTo(tgtFile))
        {
            return true;
        }

        // 移動できなかったため、コピー&削除をトライ
        // ファイルのコピー
        if (copy(srcFile, tgtFile))
        {
            // コピー元を削除
            if (srcFile.delete())
            {
                return true;
            }
            else
            {
                // ファイルの削除ができませんでした {0}
                String[] mparr = {
                    srcFile.getAbsolutePath(),
                };
                RmiMsgLogClient.write(6024043, getClass().getName(), mparr);
            }
        }
        else
        {
            // ファイルのコピーができませんでした {0}-{1}
            String[] mparr = {
                    srcFile.getAbsolutePath(),
                    tgtFile.getAbsolutePath(),
            };
            RmiMsgLogClient.write(6024044, getClass().getName(), mparr);
        }
        return false;
    }

    /**
     * ローカルフォルダに最後に作成したファイルを削除します。<br>
     * 
     * @param delFile 削除ファイル
     * @return 正常に処理できたとき true.
     */
    public boolean deleteFile(File delFile)
    {
        // ファイルが存在しない
        if (!delFile.exists())
        {
            return true;
        }

        // ファイルを削除
        if (delFile.delete())
        {
            return true;
        }
        else
        {
            // ファイルの削除ができませんでした {0}
            String[] mparr = {
                delFile.getAbsolutePath(),
            };
            RmiMsgLogClient.write(6024043, getClass().getName(), mparr);
        }

        return false;
    }

    /**
     * 交換データ環境定義情報から報告データの環境情報を取得します。<br>
     * 
     * @param exchangeJob 報告データのデータ区分<br>
     * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知されます。<br>
     */
    protected void acquireExchangeEnvironment(String exchangeJob)
            throws ScheduleException
    {
        try
        {
            // データ区分セット
            setExchangeJob(exchangeJob);
            // 送受信開始日時セット
            setStartDate(DbDateUtil.getTimeStamp());

            // 初期化
            setFileDirectory(null);
            setFileName(null);
            setExtention(null);
            setDataName(null);
            setDataType(null);

            // 交換データ環境定義
            ExchangeEnvironmentHandler envHandler = new ExchangeEnvironmentHandler(getConnection());
            ExchangeEnvironmentSearchKey key = new ExchangeEnvironmentSearchKey();

            // 検索条件セット
            key.setJobType(exchangeJob);
            key.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_SEND);

            // 交換データ環境定義取得
            ExchangeEnvironment env = (ExchangeEnvironment)envHandler.findPrimary(key);

            // 定義情報チェック
            if (env == null)
            {
                // 6026005=環境定義情報が見つかりませんでした。
                RmiMsgLogClient.write(new TraceHandler(6026004, new ScheduleException()), "AbstractReportDataCreator");
                throw new ScheduleException();
            }
            else if (StringUtil.isBlank(env.getFolderName()) || StringUtil.isBlank(env.getDataId()))
            {
                throw new InvalidDefineException();
            }

            // ディレクトリ
            setFileDirectory(env.getFolderName());
            // ファイル名
            setFileName(env.getDataId());
            // 拡張子
            setExtention(env.getExtention());
            // 報告単位
            setReportType(env.getReportType());
            // 取込ファイル名
            setDataName(env.getDataName());
            // 取込種別
            setDataType(env.getDataType());

            return;
        }
        catch (ReadWriteException ex)
        {
            // 6026005=環境定義情報が見つかりませんでした。
            RmiMsgLogClient.write(new TraceHandler(6026005, ex), "AbstractReportDataCreator");
            throw new ScheduleException();
        }
        catch (NoPrimaryException ex)
        {
            // 6027006 = データの不整合が発生しました。ログを参照してください。TABLE={0}
            Object[] msg = new Object[1];
            msg[0] = ExchangeEnvironment.STORE_NAME;
            RmiMsgLogClient.write(new TraceHandler(6027006, ex), "AbstractReportDataCreator", msg);
            throw new ScheduleException();
        }
        catch (InvalidDefineException ex)
        {
            // 6027013=不正なパラメータが検出されたため、処理を中断しました。
            RmiMsgLogClient.write(new TraceHandler(6027013, ex), "AbstractReportDataCreator");
            throw new ScheduleException();
        }
    }

    /**
     * 入出庫実績送信情報の実績報告区分を送信済みに更新します。<br>
     * @param altKey 入出庫実績送信情報の更新キー<br>
     * @throws NotFoundException 情報が無かった場合に通知されます。<br>
     * @throws ReadWriteException データベースエラーが発生した場合に通知されます。<br>
     */
    protected void updateHostSendReportFlag(HostSendAlterKey altKey)
            throws NotFoundException,
                ReadWriteException
    {
        // 実績報告区分に報告済みをセットします。
        altKey.updateReportFlag(HostSend.REPORT_FLAG_REPORT);
        // 最終更新処理名をセットします。
        altKey.updateLastUpdatePname(getCallerName());
        HostSendHandler handler = new HostSendHandler(getConnection());
        handler.modify(altKey);

        return;
    }

    /**
     * 一時ファイルの拡張子を変更します。
     * @param moveFile 一時ファイル
     */
    protected void renameErrFile(File moveFile)
    {
        String mvFileName = moveFile.getName();
        boolean matchPrefix = mvFileName.startsWith(getFileName());
        boolean matchExt = mvFileName.endsWith(getExtention());
        // prefixと拡張子が一致するものがあるかチェック
        if (moveFile.isFile() && matchPrefix && matchExt)
        {
            int point = mvFileName.lastIndexOf(PERIOD);
            String fname = (point < 0) ? mvFileName
                                      : mvFileName.substring(0, point);
            String errFileName = fname + ERR_EXTENSION;
            moveFile.renameTo(new File(WmsParam.HOSTDATA_LOCAL_PATH, errFileName));
        }
    }

    /**
     * データベース接続をクローズします。<br>
     * トランザクションが未確定の場合、ロールバックされます。
     * 
     * @param conn データベースコネクション
     */
    protected void close(Connection conn)
    {
        try
        {
            if (null != conn && !conn.isClosed())
            {
                conn.rollback();
                conn.close();
                setConnection(null);
            }
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
        }
    }

    /**
     * データベース接続をクローズします。<br>
     * トランザクションが未確定の場合、ロールバックされます。
     * 
     * @param conn データベースコネクション
     */
    protected void closeForErr(Connection conn)
    {
        try
        {
            if (null != conn && !conn.isClosed())
            {
                conn.rollback();
                conn.close();
                _errConn = null;
            }
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
        }
    }

    /**
     * 報告データ作成中フラグを更新します。
     * 
     * @param conn データベースコネクション
     * @param wsc WarenaviSystemController
     * @param progress 起動中 = true.
     * @return 更新できたとき true.
     */
    protected boolean updateSendFlag(Connection conn, WarenaviSystemController wsc, boolean progress)
    {
        try
        {
            try
            {
                // 報告データ作成中フラグを「起動中/未起動」にする。
                wsc.updateDataReporting(progress);
                conn.commit();
                return true;
            }
            catch (NotFoundException e)
            {
                conn.rollback();
                return false;
            }
        }
        catch (ReadWriteException e)
        {
            // BSRでのException通知はStackTraceを出力します。
            RmiMsgLogClient.writeTraceOnly(e, getClass().getName());
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
        }
        return false;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * 送信可能チェックを行います。<br>
     * ホスト接続設定が有効であり、日次更新を行っていないことが条件です。
     * 
     * @param conn データベースコネクション
     * @param wsc WarenaviSystemController
     * @return チェック結果がOKの時、<code>true</code>を返します。 
     */
    private boolean canSend(Connection conn, WarenaviSystemController wsc)
    {
        if (!wsc.isHostEnabled())
        {
            return false;
        }
        if (wsc.isDailyUpdating())
        {
            writeBSR("日次更新を行っているため、処理できません。", BSRInfo.WARNINGLY_TERMINATED, 6); // TODO NLS
            return false;
        }
        return true;
    }

    /**
     * ExchangeHistory（交換データ通信履歴）を作成します。<BR>
     * FA/DA区分がFAの場合のみ作成します。
     * 
     * @throws DataExistsException 
     * @throws ReadWriteException
     */
    private void createExchangeHistory(String status, ReportDataCreator creator)
            throws DataExistsException,
                ReadWriteException
    {
        AbstractReportDataCreator abst = (AbstractReportDataCreator)creator;

        // 交換データ定義情報が不正の場合、処理終了
        if (StringUtil.isBlank(abst.getFileDirectory())) return;

        // 交換データ通信履歴作成
        ExchangeHistoryHandler exHandler = new ExchangeHistoryHandler(getConnectionForError());
        ExchangeHistory ex = new ExchangeHistory();

        ex.setStartDate(abst.getStartDate());
        ex.setJobType(abst.getExchangeJob());
        ex.setDataName(abst.getDataName());
        ex.setDataType(abst.getDataType());
        ex.setExchangeType(ExchangeHistory.EXCHANGE_TYPE_SEND);
        String filename = abst.getResultFileName();
        if (filename != null && filename.length() > 40) filename = new StringBuilder(filename).substring(0, 40);
        ex.setFileName(filename);
        ex.setStatus(status);
        ex.setRegistPname(this.getClass().getSimpleName());
        ex.setLastUpdatePname(this.getClass().getSimpleName());

        exHandler.create(ex);
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * 
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractReportDataCreator.java 7733 2010-03-26 06:21:56Z okayama $";
    }
}
