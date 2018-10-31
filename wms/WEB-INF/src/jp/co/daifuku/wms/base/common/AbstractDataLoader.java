// $Id: AbstractDataLoader.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2009 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.PrePostFileFilter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dasch.FaDataLoadDASCH;
import jp.co.daifuku.wms.base.dasch.FaDataLoadDASCHParams;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistoryHandler;
import jp.co.daifuku.wms.base.display.ExceptionHandler;
import jp.co.daifuku.wms.base.entity.Com_terminal;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.ExchangeHistory;
import jp.co.daifuku.wms.base.exporter.FaDataLoadCheckListParams;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.CheckConnection;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.FileUtil;
import jp.co.daifuku.wms.base.util.timekeeper.ScheduleJob;
import jp.co.daifuku.wms.handler.AbstractEntity;

/**
 * データ取込み処理を開始するためのクラスです。<br>
 *
 *
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: kishimoto $
 */
public abstract class AbstractDataLoader implements ScheduleJob
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * WMSサーバの端末No.
     */
    private static final String SERVER_TERMINAL_NO = "Svr";

    /**
     * 取込単位キーの桁数
     */
    private static final int LOAD_UNIT_KEY_LENGTH = 24;

    /**
     * 取込がどういう状態かを表します。
     */
    private enum EXCHANGE_STATUS
    {
        /** 未処理 */
        UNSTART("UNSTART"),

        /** 正常処理完了 */
        NORMAL(ExchangeHistory.STATUS_NORMAL),

        /** 一部スキップ */
        SKIP(ExchangeHistory.STATUS_SKIP),

        /** 全スキップ */
        ALL_SKIP(ExchangeHistory.STATUS_ALL_SKIP),

        /** ファイル名不正 */
        ERR_FILE_NAME(ExchangeHistory.STATUS_ERR_FILE_NAME),

        /** ファイルダウンロード失敗 */
        ERR_DOWNLOAD(ExchangeHistory.STATUS_ERR_DOWNLOAD),

        /** 同一ファイル２度受信 */
        ERR_DUPPLICATION(ExchangeHistory.STATUS_ERR_DUPPLICATION),

        /** 取込データにエラーあり */
        ERR_DATA(ExchangeHistory.STATUS_ERR_DATA),

        /** ファイル削除時にエラー */
        ERR_DELETE_FILE(ExchangeHistory.STATUS_ERR_DELETE_FILE),

        /** 例外発生 */
        EXCEPTION(ExchangeHistory.STATUS_EXCEPTION);

        private String _status = "";

        private EXCHANGE_STATUS(String status)
        {
            _status = status;
        }

        public String toString()
        {
            return _status;
        }
    }

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * 取込ファイルのトランザクションを管理するコネクション
     */
    private Connection _conn = null;

    /**
     * DnExchangeHistoryとDnLoadErrorInfoのトランザクションを管理するコネクション
     */
    private Connection _errConn = null;

    /**
     * 交換情報（取込の種別）
     */
    private String _exchangeJob = null;

    /**
     * 取込対象のファイル情報リスト
     */
    private Map<String, FileInfo> _fileList = new HashMap<String, FileInfo>();

    /**
     * 取込状態
     */
    private EXCHANGE_STATUS _exchangeStatus = EXCHANGE_STATUS.UNSTART;

    /** 例外があった場合 */
    private boolean _hasException = false;

    /** 取り込みファイル数 */
    private String _fileName = "";

    /**
     * ユーザ情報
     */
    private DfkUserInfo _userInfo = null;

    /**
     * Locale
     */
    private Locale _locale = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     *
     * @param exchangeJob 作業種別
     */
    protected AbstractDataLoader(String exchangeJob)
    {
        super();
        _exchangeJob = exchangeJob;
    }

    /**
     *
     * @param exchangeJob 作業種別
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    protected AbstractDataLoader(String exchangeJob, DfkUserInfo userinfo, Locale locale)
    {
        super();
        _exchangeJob = exchangeJob;
        _userInfo = userinfo;
        _locale = locale;
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.util.timekeeper.ScheduleJob#execute(java.lang.String[])
     */
    public void execute(String[] args)
    {
        boolean isUpdateLoadDataFlag = false;
        ExchangeEnvironment env = null;
        WarenaviSystemController wmsController = null;

        try
        {
            // コネクションの接続を行う
            connect();

            // 処理対象ファイルを取得するために取込環境定義を取得する
            env = getExchangeEnvironment();

            // 処理対象ファイルが１つもない場合、処理をぬける
            if (ArrayUtil.isEmpty(getSourceFiles(env)))
            {
                return;
            }

            wmsController = new WarenaviSystemController(getConnection(), this.getClass());
            // 処理開始できる状態かをチェックし問題なければ取りこみ中フラグをONにする。
            if (!getLock(wmsController))
            {
                return;
            }
            isUpdateLoadDataFlag = true;

            // 6020068=取込処理を開始します。
            RmiMsgLogClient.write(6020068, getClass().getName());

            // 必要な変数を初期化
            File sourceFile = null;
            File workFile = null;
            FileInfo fl = null;
            _fileList = new HashMap<String, FileInfo>();

            // 対象ファイルがある限り処理を続ける
            while (true)
            {
                try
                {
                    // 処理ごとにコネクションの再接続を行う
                    connect();

                    // 新しい処理を開始するので状態を「未開始」にする
                    _exchangeStatus = EXCHANGE_STATUS.UNSTART;
                    sourceFile = null;
                    workFile = null;
                    fl = null;
                    _fileName = null;

                    // 処理対象ファイルを取得できなかった場合は、処理対象なしとして処理を終了する
                    sourceFile = getSourceFile(env);
                    if (sourceFile == null)
                    {
                        break;
                    }

                    // 処理対象データとしてリストに登録する
                    // 正常に登録できた場合は、処理結果を登録する。
                    fl = new FileInfo();
                    fl.setSourceFile(sourceFile);
                    fl.setStartDate(DbDateUtil.getTimeStamp());
                    _fileName = sourceFile.getName();
                    _fileList.put(_fileName, fl);

                    // ファイルを作業フォルダにダウンロードする
                    // ダウンロードに失敗した場合は、次のファイルの処理に移る
                    workFile = download(sourceFile, env);
                    if (workFile == null)
                    {
                        continue;
                    }

                    // 取り込み処理を行う。
                    if (load(workFile, env.getMaxRecord()))
                    {
                        fl.setDataCnt(getDataCnt());

                        if (FileUtil.delete(sourceFile))
                        {
							// 処理した行がある場合、操作ログを落とす
				            if (getInsertedCnt() > 0)
				            {
				            	log_write(workFile.getName());
				            }

                            // 全処理が正常に行えたのでコミットする
                            getConnection().commit();
                            getConnectionForError().commit();
                            // 統計情報の取得を行います。
                            statics();
                            // コミットを正常に行えたので今回のファイルが登録できたとして記憶する。
                            fl.setRegist(true);
                            fl.setInsertedCnt(getInsertedCnt());

                            if (fl.getDataCnt() == fl.getInsertedCnt())
                            {
                                _exchangeStatus = EXCHANGE_STATUS.NORMAL;
                            }
                            else if (fl.getInsertedCnt() == 0)
                            {
                                _exchangeStatus = EXCHANGE_STATUS.ALL_SKIP;
                            }
                            else if (fl.getInsertedCnt() < fl.getDataCnt())
                            {
                                _exchangeStatus = EXCHANGE_STATUS.SKIP;
                            }
                        }
                        else
                        {
                            _exchangeStatus = EXCHANGE_STATUS.ERR_DELETE_FILE;
                            getConnection().rollback();
                            getConnectionForError().commit();
                        }
                    }
                    else
                    {
                        // 処理が失敗した場合、ロールバックする。
                        _exchangeStatus = EXCHANGE_STATUS.ERR_DATA;
                        getConnection().rollback();
                        getConnectionForError().commit();
                    }

                }
                catch (CommonException e)
                {
                    _exchangeStatus = EXCHANGE_STATUS.EXCEPTION;
                    exceptionSetMessage(e);
                }
                catch (SQLException e)
                {
                    _exchangeStatus = EXCHANGE_STATUS.EXCEPTION;
                    exceptionSetMessage(e);
                }
                catch (Exception e)
                {
                    _exchangeStatus = EXCHANGE_STATUS.EXCEPTION;
                    exceptionSetMessage(e);
                }
                finally
                {
                    try
                    {
                        getConnection().rollback();
                        getConnectionForError().commit();

                        // 対象データなしで読み飛ばした場合は実績作成処理は不要のため終了する
                        if (_fileName == null)
                        {
                            return;
                        }

                        // 処理結果をExchangeHistoryに記録し、帳票の自動発行を行う
                        createExchangeHistory(env);
                        getConnectionForError().commit();
                        if (env.getAutoPrintErrorList().equals(ExchangeEnvironment.ERROR_LIST_AUTO_PRINT))
                        {
                            try
                            {
                                print();
                            }
                            catch (Exception e)
                            {
                                // 印刷処理でのエラーはログ記録のみ。
                                try
                                {
                                    ExceptionHandler.getDisplayMessage(e, this);
                                }
                                catch (ValidateException ve)
                                {
                                    // 絶対に発生しないのでスルー
                                }
                            }
                        }
                    }
                    // このタイミングで発生した例外はログに落とすだけで、処理をNGにはしない。
                    catch (CommonException e)
                    {
                        exceptionSetMessage(e);
                    }
                    catch (SQLException e)
                    {
                        exceptionSetMessage(e);
                    }
                    catch (Exception e)
                    {
                        exceptionSetMessage(e);
                    }
                    finally
                    {
                        // 正常に処理が行えなかった場合は作業ファイルを削除、もとファイルの拡張子を変更する
                        if ((fl != null && !fl.isRegist()))
                        {
                            if (workFile != null)
                            {
                                FileUtil.delete(workFile);
                            }
                            FileUtil.changeExtention(sourceFile, env.getExtention(), ".err");
                        }
                    }
                }
            }
        }
        catch (CommonException e)
        {
            exceptionSetMessage(e);
        }
        catch (SQLException e)
        {
            exceptionSetMessage(e);
        }
        catch (Exception e)
        {
            exceptionSetMessage(e);
        }
        finally
        {
            try
            {
                // 取り込み中フラグが自クラスで更新されたものであるならば、
                // 取り込み中フラグを、0：停止中にする
                if (isUpdateLoadDataFlag)
                {
                    // 6020069=取込処理を終了します。
                    RmiMsgLogClient.write(6020069, getClass().getName());
                    try
                    {
                    	wmsController.updateDataLoading(false);
                    }
                    catch (NotFoundException e)
                    {
                    }
                    getConnection().commit();
                }
                if (getConnection() != null && !getConnection().isClosed())
                {
                    getConnection().rollback();
                    getConnection().close();
                }
                if (getConnectionForError() != null && !getConnectionForError().isClosed())
                {
                	getConnectionForError().rollback();
                	getConnectionForError().close();
                }
            }
            catch (CommonException e)
            {
                exceptionSetMessage(e);
            }
            catch (SQLException e)
            {
                exceptionSetMessage(e);
            }
        }
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.util.timekeeper.ScheduleJob#terminate()
     */
    public void terminate()
    {
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
    /**
     * 処理を開始可能かチェックします。 <BR>
     * また、処理開始可能の場合、取込中フラグをONに更新しトランザクションを確定します。 <BR>
     * このクラスで処理を開始できるまで状態チェックを続けます。
     *
     * @param wmsController
     * @return true:処理可能, false:処理不可
     * @throws CommonException
     */
    private boolean getLock(WarenaviSystemController wmsController) throws CommonException, SQLException
    {
        boolean isFirst = true;
        while (true)
        {
            if (!isFirst)
            {
                try
                {
                    Thread.sleep(5000);
                    wmsController.read();
                }
                catch (InterruptedException e)
                {
                    exceptionSetMessage(e);
                    return false;
                }
            }
            else
            {
                isFirst = false;
            }

            if (!wmsController.isHostEnabled())
            {
                continue;
            }
            if (wmsController.isDailyUpdating())
            {
                continue;
            }
            if (wmsController.isAllocationClear())
            {
                continue;
            }
            if (wmsController.isDataLoading())
            {
                continue;
            }
            if (wmsController.isEndProcessing())
            {
                continue;
            }
            try
            {
                // 他の取込との排他を取るため予定データ取込中フラグをONにする。
                wmsController.updateDataLoading(true);
                getConnection().commit();
                return true;
            }
            catch (NotFoundException e)
            {
                continue;
            }
        }
    }

    /**
     * 取り込み対象のファイルをFileクラスとして返します。<BR>
     * 取りこみ対象のファイルがない場合、nullを返します。<BR>
     *
     * @return 取りこみ対象ファイル
     * @throws CommonException
     */
    private File getSourceFile(ExchangeEnvironment env) throws CommonException
    {
        File[] files = getSourceFiles(env);

        for (File file : files)
        {
            if (_fileList.containsKey(file.getName()))
            {
                continue;
            }
            else
            {
                return file;
            }
        }
        return null;
    }

    /**
     * 取り込み対象のファイルを上位との共有エリアから処理を行う一時領域にコピーします。
     *
     */
    private File download(File sourceFile, ExchangeEnvironment env)  throws ReadWriteException
    {
        // コピー元ファイルが消されてしまった場合は対象ファイルなし。エラーにはしない。
        if (!sourceFile.exists())
        {
            return null;
        }

        // 取り込みファイル名の文字数チェック
        // ファイル名がDBに登録可能な長さかをチェックする。登録可能な長さを超えている場合は取り込みNGとする。
        // 取込単位キー(24桁)以内かどうかをチェックします。
        if (LOAD_UNIT_KEY_LENGTH < StringUtil.getLength(sourceFile.getName()) - env.getExtention().length())
        {
            _exchangeStatus = EXCHANGE_STATUS.ERR_FILE_NAME;
            return null;
        }

        // コピー先のファイル名を作成する。
        // プレフィクス指定の場合は、元ファイルと同じ名前で、
        // プレフィクス指定でない場合は、元ファイルにシステム日時(yyyyMMddHHmmss)を付与した名前とする。
        String destFileName = "";
        if (env.getIsPrefix().equals(ExchangeEnvironment.IS_PREFIX_TRUE))
        {
            destFileName = sourceFile.getName();
        }
        else
        {
            StringBuffer fileNameBuff = new StringBuffer(sourceFile.getName());
            int idx = sourceFile.getName().lastIndexOf(".");
            destFileName = fileNameBuff.insert(idx, DbDateUtil.getSystemDateTime()).toString();
        }

        String destPath = WmsParam.HISTORY_HOSTDATA_PATH + destFileName;
        // コピー先に同じファイル名があった場合は二重取込みとする。
        if (new File(destPath).exists())
        {
            _exchangeStatus = EXCHANGE_STATUS.ERR_DUPPLICATION;
            return null;
        }

        // エラーなしなのでファイルコピーを行う
        try
        {
            FileUtil.copyFile(sourceFile.getPath(), destPath);
            return new File(destPath);
        }
        catch (IOException e)
        {
            _exchangeStatus = EXCHANGE_STATUS.ERR_DOWNLOAD;
            return null;
        }
    }

    /**
     * 取込処理を行います。
     *
     * @param workFile 処理対象ファイル
     * @param max 取り込み最大件数
     * @return true:コミットする false:ロールバックする
     * @throws CommonException
     */
    abstract protected boolean load(File workFile, int max) throws CommonException;

    /**
     * 現在取り込み処理を行っているファイルの総行数を取得します。
     *
     * @return 取り込み中ファイルの総行数
     */
    abstract protected int getDataCnt();

    /**
     * 取り込みが終了したファイルの取り込んだ件数を取得します。
     *
     * @return 取り込み済件数（ファイル単位）
     */
    abstract protected int getInsertedCnt();

    /**
     * 統計情報の取得を行います。
     *
     * @throws CommonException
     */
    abstract protected void statics() throws CommonException;

    /**
     * 対象Entityの取得
     *
     * @return AbstractEntity 対象Entityの実態
     * @throws CommonException
     */
    abstract protected AbstractEntity getEntity() throws CommonException;

    /**
     * ExchangeHistory（交換データ通信履歴）を作成します。<BR>
     *
     * @throws DataExistsException
     * @throws ReadWriteException
     */
    private void createExchangeHistory (ExchangeEnvironment env) throws DataExistsException, ReadWriteException
    {
        ExchangeHistoryHandler exHandler = new ExchangeHistoryHandler(getConnectionForError());
        ExchangeHistory ex = new ExchangeHistory();

        ex.setStartDate(getStartDate());
        ex.setJobType(_exchangeJob);
        ex.setExchangeType(ExchangeHistory.EXCHANGE_TYPE_RECEIVE);
        ex.setDataType(env.getDataType());
        ex.setDataName(env.getDataName());
        String filename = getSourceFileName();
        if (filename.length() > 40)
        {
            filename = new StringBuilder(filename).substring(0, 40);
        }
        ex.setFileName(filename);
        ex.setStatus(_exchangeStatus.toString());
        ex.setRegistPname(this.getClass().getSimpleName());
        ex.setLastUpdatePname(this.getClass().getSimpleName());

        exHandler.create(ex);
    }

    /**
     * 取込み対象の作業種別を取得します。
     *
     * @return 作業種別
     */
    protected String getExchangeJob()
    {
        return _exchangeJob;
    }

    /**
     * コネクションの再接続を行います
     *
     * @throws SQLException
     */
    private void connect() throws SQLException
    {
        CheckConnection checker = new CheckConnection();
        if (checker.check(_conn, "1".equals(WmsParam.WMS_DB_CONNECT_CHECK)))
        {
            _conn = WmsParam.getConnection();
        }
        if (checker.check(_errConn, "1".equals(WmsParam.WMS_DB_CONNECT_CHECK)))
        {
            _errConn = WmsParam.getConnection();
        }
    }

    /**
     * コネクションを取得します
     *
     * @return Connection
     */
    protected Connection getConnection()
    {
        return _conn;
    }

    /**
     * DnExchangeHistoryとDnLoadErrorInfo用のコネクションを取得します
     * @return Connection
     */
    protected Connection getConnectionForError()
    {
        return _errConn;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 環境定義を取得します。
     * 環境定義が取得できなかった場合、環境定義が正しくない場合は例外を通知します。
     *
     * @return 環境定義エンティティ
     * @throws ScheduleException 環境定義が取得できなかった場合に通知します。
     * @throws {@link InvalidDefineException} 環境定義が正しくない場合に通知します。
     */
    private ExchangeEnvironment getExchangeEnvironment() throws CommonException
    {
        ExchangeEnvironmentHandler envHandler = new ExchangeEnvironmentHandler(getConnection());
        ExchangeEnvironmentSearchKey key = new ExchangeEnvironmentSearchKey();

        key.setJobType(_exchangeJob);
        key.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_RECEIVE);

        ExchangeEnvironment env = (ExchangeEnvironment) envHandler.findPrimary(key);

        // 環境定義が取得できたか、またその定義が正しかったかをチェックする
        if (env == null)
        {
            throw new ScheduleException();
        }
        else if (StringUtil.isBlank(env.getFolderName()) || StringUtil.isBlank(env.getDataId()))
        {
            throw new InvalidDefineException();
        }
        else
        {
            return env;
        }
    }

    /**
     * 環境定義からファイル一覧を取得します。
     * 対象ファイルがない場合は要素数0のFile配列を返却します。
     *
     * @param env 環境定義
     * @return ファイル一覧
     */
    private File[] getSourceFiles (ExchangeEnvironment env)
    {
        // プレフィクスに一致するファイルを検索し、該当したファイルの先頭を返す。
        if (env.getIsPrefix().equals(ExchangeEnvironment.IS_PREFIX_TRUE))
        {
            // パスを示すFileとPrefixを保持するFilenameFileterを使い、一致するリストを生成する
            File temp = new File(env.getFolderName());
            FilenameFilter filter = new PrePostFileFilter(env.getDataId(), env.getExtention());

            File[] fileList = temp.listFiles(filter);

            if (!ArrayUtil.isEmpty(fileList))
            {
                return fileList;
            }
            else
            {
                return new File[0];
            }

        }
        // 指定ファイル名に完全一致するファイルを検索し該当ファイルを対象とする。
        else
        {
            File file = new File(env.getFolderName(), env.getDataId() + env.getExtention());

            if (file.exists())
            {
                File[] files = {file};
                return files;
            }
            else
            {
                return new File[0];
            }

        }

    }

    /**
     * 例外発生時のログの書き込みを行います。
     *
     * @param e 発生している例外
     */
    private void exceptionSetMessage(Exception e)
    {
        if (!_hasException)
        {
            _hasException = true;
        }

        try
        {
            ExceptionHandler.getDisplayMessage(e, this);
        }
        catch (ValidateException ve)
        {
            // 絶対に発生しないのでスルー
        }
    }

    /**
     * 取込エラーチェックリストの発行（自動処理用）を行います。
     *
     */
    private void print() throws Exception
    {
        Connection conn = null;
        FaDataLoadDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // get locale.
            DfkUserInfo ui = getUserInfo();

            // open connection.
            conn = WmsParam.getConnection();
            dasch = new FaDataLoadDASCH(conn, this.getClass(), _locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaDataLoadDASCHParams inparam = new FaDataLoadDASCHParams();

            inparam.set(FaDataLoadDASCHParams.START_LOAD_DATE, getStartDate());
            inparam.set(FaDataLoadDASCHParams.FILE_NAME, getSourceFileName());

            // check count.
            if (dasch.count(inparam) == 0)
            {
                return;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(_locale, ui);
            exporter = factory.newPrinterExporter("FaDataLoadCheckList", false);
            exporter.open();

            // 帳票の最大出力件数を取得
            int max_num = WmsParam.MAX_NUMBER_OF_DATALOAD_ERROR_LIST;
            int cnt = 0;

            // export.
            while (dasch.next() && (cnt < max_num))
            {
                Params outparam = dasch.get();
                FaDataLoadCheckListParams expparam = new FaDataLoadCheckListParams();
                expparam.set(FaDataLoadCheckListParams.DFK_DS_NO, outparam.get(FaDataLoadDASCHParams.DFK_DS_NO));
                expparam.set(FaDataLoadCheckListParams.DFK_USER_ID, outparam.get(FaDataLoadDASCHParams.DFK_USER_ID));
                expparam.set(FaDataLoadCheckListParams.DFK_USER_NAME, outparam.get(FaDataLoadDASCHParams.DFK_USER_NAME));
                expparam.set(FaDataLoadCheckListParams.LOAD_TYPE, outparam.get(FaDataLoadDASCHParams.LOAD_TYPE));
                expparam.set(FaDataLoadCheckListParams.SYS_DAY, outparam.get(FaDataLoadDASCHParams.SYS_DATE));
                expparam.set(FaDataLoadCheckListParams.SYS_TIME, outparam.get(FaDataLoadDASCHParams.SYS_DATE));
                expparam.set(FaDataLoadCheckListParams.FILE_NAME, outparam.get(FaDataLoadDASCHParams.FILE_NAME));
                expparam.set(FaDataLoadCheckListParams.START_DAY, outparam.get(FaDataLoadDASCHParams.START_DATE));
                expparam.set(FaDataLoadCheckListParams.START_TIME, outparam.get(FaDataLoadDASCHParams.START_DATE));
                expparam.set(FaDataLoadCheckListParams.STATUS, outparam.get(FaDataLoadDASCHParams.STATUS));
                expparam.set(FaDataLoadCheckListParams.FILE_LINE_NO, outparam.get(FaDataLoadDASCHParams.FILE_LINE_NO));
                expparam.set(FaDataLoadCheckListParams.ERROR_LEVEL, outparam.get(FaDataLoadDASCHParams.ERROR_LEVEL));
                expparam.set(FaDataLoadCheckListParams.ERROR_DETAIL, outparam.get(FaDataLoadDASCHParams.ERROR_DETAIL));
                expparam.set(FaDataLoadCheckListParams.ITEM_NO, outparam.get(FaDataLoadDASCHParams.ITEM_NO));
                expparam.set(FaDataLoadCheckListParams.DATA, outparam.get(FaDataLoadDASCHParams.DATA));

                cnt++;

                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            exporter.print();

        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
            DBUtil.rollback(conn);
            DBUtil.close(conn);
        }
    }

    /**
     * ユーザ情報を取得します。
     *
     * @return ユーザ情報
     */
    private DfkUserInfo getUserInfo() throws CommonException
    {
        // ユーザ情報がある場合も無い場合も念のため取得
        Com_terminalHandler com = new Com_terminalHandler(getConnection());
        Com_terminalSearchKey comKey = new Com_terminalSearchKey();
        comKey.setTerminalnumber(SERVER_TERMINAL_NO);
        Com_terminal comInfo = (Com_terminal) com.findPrimary(comKey);

        // 画面からUserInfoが指定された場合 かつ
        // 保持している情報とDBのプリンター名が同一の場合は生成しない
        if (_userInfo != null && comInfo.getPrintername().equals(_userInfo.getTerminalPrinterName()))
        {
            return _userInfo;
        }

        _userInfo = new DfkUserInfo();
        _userInfo.setDsNumber(DsNumberDefine.DS_AUTOLOAD);
        _userInfo.setUserId(WmsParam.SYS_USER_ID);
        _userInfo.setUserName(WmsParam.SYS_USER_NAME);
        _userInfo.setTerminalNumber(SERVER_TERMINAL_NO);
        _userInfo.setTerminalName(WmsParam.SYS_TERMINAL_NAME);
        _userInfo.setTerminalAddress(WmsParam.SYS_IP_ADDRESS);
        _userInfo.setTerminalPrinterName(comInfo.getPrintername());
        _userInfo.setPageNameResourceKey(DsNumberDefine.PAGERESOUCE_AUTOLOAD);

        return _userInfo;
    }

    /**
     * 処理結果のメッセージを返します。
     * このクラスのメッセージは概要のみ返します。
     * 詳細はログを参照してください。
     *
     * @return メッセージ
     */
    public String getMessage()
    {
        if (_hasException)
        {
            // 何かしら例外が発生した
            // 6027005=内部エラーが発生しました。ログを参照してください。
            return "6027005";
        }
        // 対象データがなかった場合
        if (_fileList.isEmpty())
        {
            // 6006038=対象データはありませんでした。
            return "6006038";
        }
        // 取り込みを行ったファイルでエラーがあったものがあるか？
        boolean hasRollBack = false;
        int dataCnt = 0;
        int insertedCnt = 0;
        for (String key : _fileList.keySet())
        {
            if (_fileList.get(key).isRegist())
            {
                dataCnt += _fileList.get(key).getDataCnt();
                insertedCnt += _fileList.get(key).getInsertedCnt();
            }
            else
            {
                hasRollBack = true;
            }
        }
        if (hasRollBack)
        {
            // 6027003=取り込みを行えないファイルがありました。
            return "6027003";
        }
        else if (insertedCnt == 0)
        {
            // 6021015=一部のデータをスキップした結果、対象データはありませんでした。
            return "6021015";
        }
        else if (dataCnt != insertedCnt)
        {
            // 6021014=一部のデータをスキップし、データの取込みは終了しました。
            return "6021014";
        }
        else
        {
            // 6021011=データの取込みは正常に終了しました。
            return "6021011";
        }

    }

    /**
     * 取込み対象ファイルの件数を取得します。
     *
     * @return 取込み対象ファイルの件数
     * @throws CommonException 環境定義情報取得に失敗した場合に通知します。
     */
    public int getFileCount() throws CommonException
    {
        try
        {
            // DBコネクト
            connect();

            File[] files = getSourceFiles(getExchangeEnvironment());

            if (ArrayUtil.isEmpty(files))
            {
                return 0;
            }
            else
            {
                return files.length;
            }
        }
        catch (SQLException e)
        {
            exceptionSetMessage(e);
            return -1;
        }

    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractDataLoader.java 8053 2013-05-15 01:00:52Z kishimoto $";
    }

    /**
     * 取込み中のファイル名を取得します
     * @return 取込み中のファイル名
     */
    protected String getSourceFileName()
    {
        return _fileList.get(_fileName).getSourceFile().getName();
    }

    /**
     * 取込み中のファイル名を拡張子を除いて取得します
     * @return 取込み中のファイル名(拡張子を除く)
     */
    protected String getSourceFileNameOutOfExtention() throws CommonException
    {
        String fileName = getSourceFileName();
        String fileNameOutOfExtention =
            fileName.substring(0, (fileName.length() - getExchangeEnvironment().getExtention().length()));
        return fileNameOutOfExtention;
    }

    /**
     * 取込み中ファイルの取込開始日時を取得します
     * @return 取込み中ファイルの取込開始日時
     */
    protected Date getStartDate()
    {
        return _fileList.get(_fileName).getStartDate();
    }


    /**
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param fileName ファイル名
     * @throws SQLException SQLでエラーが発生した場合に通知されます。
     * @throws CommonException ユーザ情報の取得に失敗した場合に通知されます。
     */
    private void log_write(String fileName)
            throws SQLException,
                CommonException
    {
        P11LogWriter part11Writer = new P11LogWriter(getConnection());
        Part11List part11List = new Part11List();

        // データ区分
        part11List.add(_exchangeJob, "");
        // ファイル名
        part11List.add(fileName, "");

        // 常駐処理からの自動起動時、ユーザ情報がセットされないのでWareNavi固定値にて再編集を行います。
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();

        part11Writer.createOperationLog(ui, EmConstants.OPELOG_CLASS_SETTING, part11List);

    }

    /**
     * 取り込みを行ったファイルの情報を保持します。<BR>
     *
     *
     * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
     * @author  Y.Okamura
     * @author  Last commit: $Author: kishimoto $
     */
    private static class FileInfo
    {
        /**
         * 取込ファイル
         */
        private File _sourceFile = null;
        /**
         * 取込処理開始日時
         */
        private Date _startDate = null;
        /**
         * データを登録したかどうか
         */
        private boolean _isRegist = false;
        /**
         * 取込ファイル内のデータ行数(isRegistがfalseの場合無効)
         */
        private int _dataCnt = 0;
        /**
         * 取り込んだデータ数(isRegistがfalseの場合無効)
         */
        private int _insertedCnt = 0;

        /**
         * コンストラクタ
         */
        private FileInfo()
        {
            super();
        }

        /**
         * 取込ファイルを返します。
         * @return sourceFileを返します。
         */
        private File getSourceFile()
        {
            return _sourceFile;
        }
        /**
         * 取込ファイルを設定します。
         * @param sourceFile sourceFile
         */
        private void setSourceFile(File sourceFile)
        {
            _sourceFile = sourceFile;
        }

       /**
         * 取込ファイル内のデータ行数を返します。
         * @return dataCntを返します。
         */
        private int getDataCnt()
        {
            return _dataCnt;
        }
        /**
         * 取込ファイル内のデータ行数を設定します。
         * @param dataCnt dataCnt
         */
        private void setDataCnt(int dataCnt)
        {
            _dataCnt = dataCnt;
        }
        /**
         * 取り込んだデータ数を返します。
         * @return insertedCntを返します。
         */
        private int getInsertedCnt()
        {
            return _insertedCnt;
        }
        /**
         * 取り込んだデータ数を設定します。
         * @param insertedCnt insertedCnt
         */
        private void setInsertedCnt(int insertedCnt)
        {
            _insertedCnt = insertedCnt;
        }
        /**
         * startDateを返します。
         * @return startDateを返します。
         */
        private Date getStartDate()
        {
            return _startDate;
        }
        /**
         * startDateを設定します。
         * @param startDate startDate
         */
        private void setStartDate(Date startDate)
        {
            _startDate = startDate;
        }

        /**
         * isRegistを返します。
         * @return isRegistを返します。
         */
        private boolean isRegist()
        {
            return _isRegist;
        }

        /**
         * isRegistを設定します。
         * @param isRegist isRegist
         */
        private void setRegist(boolean isRegist)
        {
            this._isRegist = isRegist;
        }

    }

}