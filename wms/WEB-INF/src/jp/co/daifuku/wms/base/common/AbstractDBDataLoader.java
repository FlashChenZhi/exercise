//$Id: AbstractDBDataLoader.java 7650 2010-03-17 09:31:17Z okayama $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.PrePostFileFilter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.DBUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LoadErrorInfoHandler;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.LoadErrorInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.IllegalDataException;
import jp.co.daifuku.wms.base.util.DBDataLoadLogFileWriter;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.bsr.BSRInfo;
import jp.co.daifuku.wms.base.util.bsr.BSRWriter;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.validator.ValidateError;
import jp.co.daifuku.wms.handler.field.validator.ValidateException;
import jp.co.daifuku.wms.handler.file.FileHandler;
import jp.co.daifuku.wms.system.dasch.DBDataLoadDASCH;
import jp.co.daifuku.wms.system.schedule.HostDBDataLoadSCH;
import jp.co.daifuku.wms.system.schedule.HostDBDataLoadSCHParams;

/**
 * Designer : Gary Muhlestein <BR>
 * Maker : Gary Muhlestein<BR>
 * <CODE>AbstractDBDataLoader</CODE>クラスは、データ取込み処理を行う抽象クラスです。<BR>
 * <CODE>DBDataLoader</CODE>インターフェースを実装し、このインターフェースの実装に必要な処理を実装します。<BR>
 * 共通メソッドはこのクラスに実装され、実際のメンテナンス処理など個別の振る舞いについては、<BR>
 * このクラスを継承したクラスによって実装されます。<BR>

 * <BR>
 * @version $Revision: 7650 $, $Date: 2010-03-17 18:31:17 +0900 (水, 17 3 2010) $
 * @author  wms
 * @author  Last commit: $Author: okayama $
 */
public abstract class AbstractDBDataLoader
        implements DBDataLoader
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * <code>REPORTDATA_BSRCATEGORY</code><br>
     *データ取り込み処理のBSRカテゴリ
     */
    public static final String BSRCATEGORY_DATALOAD = "DATALOAD";

    /**
     * データ取り込みフォルダのセクション名
     */
    protected static final String DATALOAD_FOLDER = "DATALOAD_FOLDER";

    /**
     * データ取り込みファイル名のセクション名
     */
    protected static final String DATALOAD_FILENAME = "DATALOAD_FILENAME";

    /**
     * バックアップファイルのパス
     */
    protected static final String DATALOAD_BACKUP_FOLDER = WmsParam.HISTORY_HOSTDATA_PATH;

    /**
     * WMSサーバの端末No.
     */
    protected static final String SERVER_TERMINAL_NO = "Svr";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    /** この抽象クラスを継承した具象クラス間での同期用のオブジェクトです。*/
    private static Object $mutex = new Object();

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * 取込区分
     */
    private String _dataType = "";

    /**
     * スキップフラグ
     */
    private boolean _skipFlag = false;

    /**
     * 登録フラグ
     */
    private boolean _registFlag = false;

    /**
     * 異常終了フラグ
     */
    private boolean _errorFlag = false;

    /**
     * 取込開始日時
     */
    private Date _startDate = null;

    /**
     * 全件数
     **/
    private int _allItemCount = 0;

    /**
     * 項目数
     */
    private int _itemcount = 0;

    /**
     * 項目数
     */
    private int _sequenceno = 0;

    /**
     * 取込ファイルパス
     */
    private String _filePath = "";

    /**
     * 取込ファイル名
     */
    private String _fileName = "";

    /**
     * 取込行データ
     */
    private String _lineData = "";

    /**
     * データベースコネクション
     */
    private Connection _conn = null;

    /**
     * Connection to the customer's database
     */
    private Connection _customerConn = null;

    /**
     * 取込エラー情報用データベースコネクション
     */
    private Connection _errorInfoConn = null;

    /**
     * 取込エラー情報用
     */
    private LoadErrorInfoHandler _loadErrorInfoHandler = null;

    /**
     * 取込エラーログファイル用
     */
    private DBDataLoadLogFileWriter _errlist = null;

    /**
     * メッセージ保持エリア<BR>
     * 各メソッドの呼び出しで条件エラー等が発生した場合にその内容を保持するために使用します。
     */
    private String _message = "";

    /**
     * ユーザ情報保管エリア
     */
    private DfkUserInfo _userInfo = null;

    /**
     * Entry timestamp from the HostToEWN table
     */
    private String _entryTimestamp = "";

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * インスタンスを生成します。
     */
    public AbstractDBDataLoader()
    {
    }

    /**
     * データベースコネクションと呼び出し元クラスを指定してインスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param errorInfoConn 取込エラー情報用データベースコネクション
     */
    public AbstractDBDataLoader(Connection conn, Connection errorInfoConn)
    {
        setConnection(conn);

        // 取込エラー情報
        _loadErrorInfoHandler = new LoadErrorInfoHandler(errorInfoConn);

        // 取込みエラーファイル作成クラスインスタンス作成
        _errlist = new DBDataLoadLogFileWriter();
    }

    /**
     * データベースコネクションと呼び出し元クラスとユーザ情報を指定してインスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param userInfo ユーザ情報
     * @param errorInfoConn 取込エラー情報用データベースコネクション
     */
    public AbstractDBDataLoader(Connection conn, Connection errorInfoConn, DfkUserInfo userInfo)
    {
        setConnection(conn);

        // ユーザ情報保管
        setUserInfo(userInfo);

        // 取込エラー情報
        _loadErrorInfoHandler = new LoadErrorInfoHandler(errorInfoConn);

        // 取込みエラーファイル作成クラスインスタンス作成
        _errlist = new DBDataLoadLogFileWriter();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * データ取込み処理を行ないます。<BR>
     * データ取込み処理はパッケージによって異なるため、このメソッドは<code>AbstractDataLoader</code>
     * クラスを継承したクラスにより実装されます。パラメータチェックに成功した場合はtrue、
     * 失敗した場合はfalseを返します。
     * パラメータチェックに失敗した場合、その理由を<code>getMessage</code>で取得できます。
     * @param sysController  WarenaviSystemControllerオブジェクト
     * @param inEntity Entityクラス
     * @return 取込み処理に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean loadEntity(WarenaviSystemController sysController, AbstractEntity inEntity)
            throws CommonException
    {
        throw new ScheduleException("This method is not supported.");
    }

    /**
     * ファイルの自動取込開始時に呼ばれます。<BR>
     * コネクションの生成とコミット・クローズはこのメソッドで行ないます。<BR>
     * 取込処理が完了したら取込チェックリストの発行を行ないます。<BR>
     * @param args  このメソッドの引数
     */
    public void execute(String[] args)
    {
        synchronized ($mutex)
        {
            writeBSR("Begin import of plan", BSRInfo.BEING_PROCESSED, 0); // TODO NLS

            // 内部で保持してる値の初期化
            initData();

            // データ取込SCHクラス
            AbstractSCH dataLoadSCH = null;

            // 帳票出力DASCHクラス
            DBDataLoadDASCH dasch = null;
            // 帳票出力PrintExporterクラス
            PrintExporter exporter = null;

            // 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
            boolean isUpdateLoadDataFlag = false;

            try
            {
                // コネクションの取得
                setConnection(WmsParam.getConnection());
                // Acquire a connection to the customer's database
                setCustomerConnection(WmsParam.getCustomerConnection());
                // 取込エラー情報コネクションの取得
                setErrorInfoConn(WmsParam.getConnection());

                dataLoadSCH =
                        new HostDBDataLoadSCH(getConnection(), getCustomerConnection(), getErrorInfoConn(), getClass(),
                                null, getUserInfo());
                // 自動ホスト通信の有効/無効 チェック
                if (!dataLoadSCH.isHostCommEnabled())
                {
                    return;
                }
                // 処理開始チェック
                if (!dataLoadSCH.canStart())
                {
                    writeBSR("Could not process because currently doing daily cleanup.", BSRInfo.WARNINGLY_TERMINATED,
                            2); // TODO NLS
                    return;
                }
                // 搬送データクリアチェック
                if (dataLoadSCH.isAllocationClear())
                {
                    writeBSR("搬送データクリア中のため処理を行いませんでした。", BSRInfo.WARNINGLY_TERMINATED, 2); // TODO NLS
                    return;
                }
                // 取込中チェック
                if (dataLoadSCH.isLoadData())
                {
                    writeBSR("Could not process because another proc is importing.", BSRInfo.WARNINGLY_TERMINATED, 2); // TODO NLS
                    return;
                }
                // 取込中フラグ更新
                if (!dataLoadSCH.doLoadStart())
                {
                    writeBSR("他の取り込み処理中のため処理を行いませんでした。", BSRInfo.WARNINGLY_TERMINATED, 2); // TODO NLS
                    return;
                }
                dataLoadSCH.doCommit(getClass());
                isUpdateLoadDataFlag = true;

                // 6020068=取込処理を開始します。
                RmiMsgLogClient.write(6020068, getClass().getSimpleName());

                // set input parameters.
                HostDBDataLoadSCHParams inparam = new HostDBDataLoadSCHParams();

                // SCH call.
                List<Params> outparams = dataLoadSCH.query(inparam);

                // see if there is something to do
                boolean dataFound = false;
                for (Params outparam : outparams)
                {
                    if (outparam.getInt(HostDBDataLoadSCHParams.NO_OF_IMPORT_MSGS) > 0)
                    {
                        dataFound = true;
                    }
                }

                List<Params> backparams = null;

                if (!dataFound)
                {
                    return; //simply return, no use making any logging noise, since no-data condition is most frequent
                }
                else
                //feed the sched his own parameters and load all data
                {
                    setStartDate(DbDateUtil.getTimeStamp()); //not to be confused with a specific loading type's _startDate
                    ScheduleParams[] inparams = new ScheduleParams[outparams.size()];
                    outparams.toArray(inparams);
                    backparams = dataLoadSCH.startSCHgetParams(inparams);
                }
                _errlist = new DBDataLoadLogFileWriter(); //???

                if (backparams == null)
                {
                    // rollback.
                    getConnection().rollback();
                    getCustomerConnection().rollback();
                    //                    writeBSR("Import terminated abnormally.  See logs.", BSRInfo.WARNINGLY_TERMINATED, 2); // TODO NLS
                    return;
                }
                else
                {
                    // commit.
                    getConnection().commit();
                    getCustomerConnection().commit();
                    //                    writeBSR(dataLoadSCH.getMessage(), BSRInfo.NORMAL_COMPLETED, 0); // TODO NLS
                }

            }
            catch (Exception ex)
            {
                try
                {
                    ExceptionHandler.getDisplayMessage(ex, this);
                }
                catch (Exception ex2)
                {
                    // 6006021 = スケジュール処理中に予期しない例外が発生しました。{0}
                    createErrorLog(WmsMessageFormatter.format(6006021), getClass());

                    if (dataLoadSCH != null && !StringUtil.isBlank(dataLoadSCH.getMessage()))
                    {
                        writeBSR(dataLoadSCH.getMessage(), BSRInfo.WARNINGLY_TERMINATED, 2);
                    }
                }
            }
            finally
            {
                try
                {
                    // 取り込み中フラグが自クラスで更新されたものであるならば、
                    // 取り込み中フラグを、0：停止中にする
                    if (isUpdateLoadDataFlag)
                    {
                        dataLoadSCH.doLoadEnd();
                        dataLoadSCH.doCommit(getClass());
                        // 6020069=取込処理を終了します。
                        RmiMsgLogClient.write(6020069, getClass().getSimpleName());
                    }
                }
                catch (CommonException e)
                {
                    // 6006021 = スケジュール処理中に予期しない例外が発生しました。{0}
                    createErrorLog(WmsMessageFormatter.format(6006021), getClass());
                }
                DBUtil.rollback(getConnection());
                DBUtil.close(getConnection());
                DBUtil.rollback(getCustomerConnection());
                DBUtil.close(getCustomerConnection());

                // close
                if (dasch != null)
                {
                    dasch.close();
                }
                if (exporter != null)
                {
                    exporter.close();
                }
            } // end of try-catch-finally block
        } // end of synchronized block
    }

    /**
     * ファイルの自動取込終了時に呼ばれます。<BR>
     */
    public void terminate()
    {

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
            BSRWriter.write(BSRCATEGORY_DATALOAD, fac, status, msg, getClass());
        }
        catch (ReadWriteException e)
        {
            RmiMsgLogClient.writeTraceOnly(e, getClass().getName());
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * dataTypeを返します。
     * @return dataTypeを返します。
     */
    protected String getDataType()
    {
        return _dataType;
    }

    /**
     * dataTypeを設定します。
     * @param dataType dataType
     */
    protected void setDataType(String dataType)
    {
        _dataType = dataType;
    }

    /**
     * スキップフラグの値を取得します。
     * @return wSkipFlag
     */
    public boolean isSkipFlag()
    {
        return _skipFlag;
    }

    /**
     * スキップフラグに値をセットします。
     * @param skipFlag セットするスキップフラグ
     */
    public void setSkipFlag(boolean skipFlag)
    {
        _skipFlag = skipFlag;
    }

    /**
     * 登録フラグの値を取得します。
     * @return wRegistFlag
     */
    public boolean isRegistFlag()
    {
        return _registFlag;
    }

    /**
     * 登録フラグに値をセットします。
     * @param registFlag セットする登録フラグ
     */
    public void setRegistFlag(boolean registFlag)
    {
        _registFlag = registFlag;
    }

    /**
     * 異常終了フラグの値を取得します。
     * @return wErrorFlag
     */
    public boolean isErrorFlag()
    {
        return _errorFlag;
    }

    /**
     * 異常終了フラグに値をセットします。
     * @param errorFlag セットする登録フラグ
     */
    public void setErrorFlag(boolean errorFlag)
    {
        _errorFlag = errorFlag;
    }

    /**
     * startDateを返します。
     * @return startDateを返します。
     */
    public Date getStartDate()
    {
        return _startDate;
    }

    /**
     * startDateを設定します。
     * @param startDate startDate
     */
    public void setStartDate(Date startDate)
    {
        _startDate = startDate;
    }

    /**
     * allItemCountを返します。
     * @return allItemCountを返します。
     */
    protected int getAllItemCount()
    {
        return _allItemCount;
    }

    /**
     * allItemCountを設定します。
     * @param allItemCount allItemCount
     */
    protected void setAllItemCount(int allItemCount)
    {
        _allItemCount = allItemCount;
    }

    /**
     * itemcountを返します。
     * @return itemcountを返します。
     */
    protected int getItemcount()
    {
        return _itemcount;
    }

    /**
     * itemcountを設定します。
     * @param itemcount itemcount
     */
    protected void setItemcount(int itemcount)
    {
        _itemcount = itemcount;
    }

    /**
     * sequencenoを返します。
     * @return sequencenoを返します。
     */
    protected int getSequenceno()
    {
        return _sequenceno;
    }

    /**
     * sequencenoを設定します。
     * @param sequenceno sequenceno
     */
    protected void setSequenceno(int sequenceno)
    {
        _sequenceno = sequenceno;
    }

    /**
     * filePathを返します。
     * @return filePathを返します。
     */
    protected String getFilePath()
    {
        return _filePath;
    }

    /**
     * filePathを設定します。
     * @param filePath filePath
     */
    protected void setFilePath(String filePath)
    {
        _filePath = filePath;
    }

    /**
     * fileNameを返します。
     * @return fileNameを返します。
     */
    protected String getFileName()
    {
        return _fileName;
    }

    /**
     * fileNameを設定します。
     * @param fileName fileName
     */
    protected void setFileName(String fileName)
    {
        _fileName = fileName;
    }

    /**
     * lineDataを返します。
     * @return lineDataを返します。
     */
    protected String getLineData()
    {
        return _lineData;
    }

    /**
     * lineDataを設定します。
     * @param lineData lineData
     */
    protected void setLineData(String lineData)
    {
        _lineData = lineData;

        setSkipFlag(false);
        setRegistFlag(false);
    }

    /**
     * データベースのコネクションを設定します。
     * @param conn データベースのコネクション
     */
    protected void setConnection(Connection conn)
    {
        _conn = conn;
    }

    /**
     * データベースのコネクションを返します。
     * @return データベースのコネクション。
     */
    protected Connection getConnection()
    {
        return _conn;
    }

    /**
     * errorInfoConnを返します。
     * @return errorInfoConnを返します。
     */
    protected Connection getErrorInfoConn()
    {
        return _errorInfoConn;
    }

    /**
     * Set the connection to the customer's database
     * @param conn Customer's database connection
     */
    protected void setCustomerConnection(Connection conn)
    {
        _customerConn = conn;
    }

    /**
     * Return the connection to the customer's database
     * @return Customer's database connection
     */
    protected Connection getCustomerConnection()
    {
        return _customerConn;
    }

    /**
     * errorInfoConnを設定します。
     * @param errorInfoConn errorInfoConn
     */
    protected void setErrorInfoConn(Connection errorInfoConn)
    {
        _errorInfoConn = errorInfoConn;
    }

    /**
     * errlistを返します。
     * @return errlistを返します。
     */
    protected DBDataLoadLogFileWriter getErrlist()
    {
        return _errlist;
    }

    /**
     * errlistを設定します。
     * @param errlist errlist
     */
    protected void setErrlist(DBDataLoadLogFileWriter errlist)
    {
        _errlist = errlist;
    }

    /**
     * <CODE>load()</CODE>メソッドでfalseが戻された場合に、<BR>
     * その理由を表示するためのメッセージを取得します。<BR>
     * このメソッドは画面のメッセージエリアへの表示内容を取得するために使用します。<BR>
     * @return メッセージ
     */
    public String getMessage()
    {
        return _message;
    }

    /**
     * チェックが保持するメッセージへセットします。<BR>
     * チェックした内容がＮＧの場合に、その詳細をセットします。<BR>
     * @param msg メッセージの内容
     */
    public void setMessage(String msg)
    {
        _message = msg;
    }

    /**
     * Get the entry time for a line in the HostToEWN table
     * @return entryTimestamp
     */
    public String getEntryTimestamp()
    {
        return _entryTimestamp;
    }

    /**
     * Set the timestamp found from the HostToEWN table
     * @param aEntryTimestamp aEntryTimestamp
     */
    public void setEntryTimestamp(String aEntryTimestamp)
    {
        _entryTimestamp = aEntryTimestamp;
    }

    /**
     * ユーザ情報を返します。
     * @return userInfoを返します。
     */
    protected DfkUserInfo getUserInfo()
    {
        return _userInfo;
    }

    /**
     * ユーザ情報を設定します。
     * @param userInfo userInfo
     */
    protected void setUserInfo(DfkUserInfo userInfo)
    {
        _userInfo = userInfo;
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * EnvironmentInformation.propertiesに定義してある各データ取込キー名を取得します。
     * 
     * @return データ取込キー
     */
    protected abstract String getDataLoadKey();

    /**
     * エラー時のメッセージをRmiMsgと取込エラーファイルに出力します。<BR>
     * @param msg メッセージ番号
     * @param pClass クラス
     */
    protected void createErrorLog(String msg, Class pClass)
    {
        createErrorLog(msg, null, pClass);
    }

    /**
     * エラー時のメッセージをRmiMsgと取込エラーファイルに出力します。<BR>
     * @param msg メッセージ番号
     * @param data データ内容
     * @param pClass クラス
     */
    protected void createErrorLog(String msg, String data, Class pClass)
    {
        RmiMsgLogClient.write(msg, pClass.getSimpleName());

        if (!StringUtil.isBlank(data))
        {
            // 取込みエラーファイル作成クラスにエラー内容を追記する。
            _errlist.addStatusFile(DBDataLoadLogFileWriter.STATUS_ERROR, data, msg);
        }
    }

    /**
     * エラー時のメッセージを取込エラー情報に登録
     * @param errorFlag エラー区分
     * @param itemNo 項目番号
     * @param data データ内容
     * @param pClass クラス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void createLoadErrorInfo(String errorFlag, String itemNo, String data, Class pClass)
            throws ReadWriteException
    {
        // 取込エラー情報に登録
        LoadErrorInfo loadErrorInfoEntity = new LoadErrorInfo();
        try
        {
            String filePrefix = getFileName().substring(0, (getFileName().length() - Parameter.EXTENSION.length()));
            loadErrorInfoEntity.setStartDate(getStartDate());
            loadErrorInfoEntity.setFileName(filePrefix);
            loadErrorInfoEntity.setFileLineNo(getSequenceno());
            loadErrorInfoEntity.setJobType(getDataType());
            loadErrorInfoEntity.setErrorLevel(SystemDefine.ERROR_LEVEL_WARNING);
            loadErrorInfoEntity.setErrorFlag(errorFlag);
            loadErrorInfoEntity.setItemNo(String.valueOf(itemNo));
            loadErrorInfoEntity.setData(data);
            loadErrorInfoEntity.setRegistPname(pClass.getSimpleName());

            _loadErrorInfoHandler.create(loadErrorInfoEntity);
        }
        catch (DataExistsException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**
     * 取込処理の終了時に、BSRにログを出力します。<BR>
     * パトライトの点灯もこのメソッドで行ないます。<BR>
     * @param pClass クラス
     * @param isNoCheck trueなら登録件数チェックを行なわずに出力します。
     */
    protected void createBSRLog(Class pClass, boolean isNoCheck)
    {
        try
        {
            if (isNoCheck)
            {
                String logMessage = "";
                if (!isErrorFlag())
                {
                    if (!isSkipFlag())
                    {
                        if (isRegistFlag())
                        {
                            // 正常終了の場合
                            // 6020005=取込処理が終了しました。ファイル:{0} 総件数:{1} 追加:{2} 更新:{3} スキップ:{4}
                            logMessage =
                                    WmsMessageFormatter.format(6020005, getFileName(),
                                            WmsFormatter.getNumFormat(getAllItemCount()), "", "", "無");
                            BSRWriter.write(BSRCATEGORY_DATALOAD, BSRInfo.NORMAL_COMPLETED, BSRInfo.NORMAL_COMPLETED,
                                    logMessage, pClass);
                            return;
                        }
                        else
                        {
                            // 全てのフラグがOFFの場合
                            // 6026100=取込処理が正常に終了できませんでした。
                            logMessage = WmsMessageFormatter.format(6026100);
                            BSRWriter.write(BSRCATEGORY_DATALOAD, BSRInfo.ABNORMAL_TERMINATED,
                                    BSRInfo.ABNORMAL_TERMINATED, logMessage, pClass);
                            return;
                        }
                    }
                    else
                    {
                        // スキップしたデータがあった場合
                        // 6020005=取込処理が終了しました。ファイル:{0} 総件数:{1} 追加:{2} 更新:{3} スキップ:{4}
                        logMessage =
                                WmsMessageFormatter.format(6020005, getFileName(),
                                        WmsFormatter.getNumFormat(getAllItemCount()), "", "", "有");
                        BSRWriter.write(BSRCATEGORY_DATALOAD, BSRInfo.WARNINGLY_TERMINATED, BSRInfo.NORMAL_COMPLETED,
                                logMessage, pClass);
                        return;
                    }
                }
                else
                {
                    // 6026100=取込処理が正常に終了できませんでした。
                    logMessage = WmsMessageFormatter.format(6026100);
                    BSRWriter.write(BSRCATEGORY_DATALOAD, BSRInfo.ABNORMAL_TERMINATED, BSRInfo.ABNORMAL_TERMINATED,
                            logMessage, pClass);
                    return;
                }
            }
        }
        catch (ReadWriteException e)
        {
            //6026098=BSR情報への書き込みに失敗しました。詳細=({0})
            RmiMsgLogClient.write(new TraceHandler(6026098, e), pClass.getSimpleName());
            return;
        }
    }

    /**
     * 発生したValidateExceptionからエラーログを判別し、出力します。
     * @param ex ValidateExceptionクラス
     * @param pClass Class
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     */
    protected void validateExceptionMsg(ValidateException ex, Class pClass)
            throws ReadWriteException,
                IOException
    {
        ValidateError[] validateErrors = ex.getValidateErrors();
        if (validateErrors != null)
        {
            for (ValidateError validateError : validateErrors)
            {
                String msg = "";
                String errorFlag = "";
                switch (validateError.getErrorCode())
                {
                    case ValidateError.RETURN_REQUIRE_ERROR:
                        // 6026010=必須項目に値がないデータが存在しました。ファイル:{0} 行:{1} 項目:{2}
                        msg =
                                WmsMessageFormatter.format(6026010, getFileName(),
                                        WmsFormatter.getNumFormat(getAllItemCount()), validateError.getFieldName());
                        errorFlag = SystemDefine.ERROR_FLAG_INDISPENSABLE_ITEM_BLANK;
                        break;
                    case ValidateError.RETURN_TYPE_ERROR:
                        // 6026083=整数値の項目に有効ではないデータが存在しました。ファイル:{0} 行:{1} 項目:{2} 値:{3}
                        msg =
                                WmsMessageFormatter.format(6026083, getFileName(),
                                        WmsFormatter.getNumFormat(getAllItemCount()), validateError.getFieldName(),
                                        validateError.getFieldValue());
                        errorFlag = SystemDefine.ERROR_FLAG_VALIDATE_ERROR;
                        break;
                    case ValidateError.RETURN_LENGTH_ERROR:
                        // 6026080=桁数の無効なデータが存在しました。ファイル:{0} 行:{1} 項目:{2} 値:{3}
                        msg =
                                WmsMessageFormatter.format(6026080, getFileName(),
                                        WmsFormatter.getNumFormat(getAllItemCount()), validateError.getFieldName(),
                                        validateError.getFieldValue());
                        errorFlag = SystemDefine.ERROR_FLAG_VALIDATE_ERROR;
                        break;
                    case ValidateError.RETURN_RANGE_ERROR:
                        // 6026082=指定された有効範囲外のデータが存在しました。ファイル:{0} 行:{1} 項目:{2} 値:{3}
                        msg =
                                WmsMessageFormatter.format(6026082, getFileName(),
                                        WmsFormatter.getNumFormat(getAllItemCount()), validateError.getFieldName(),
                                        validateError.getFieldValue());
                        errorFlag = SystemDefine.ERROR_FLAG_VALIDATE_ERROR;
                        break;
                    case ValidateError.RETURN_TYPE_CLASS_ERROR:
                        // 6026091=文字列の項目に属性が有効ではないデータが存在しました。ファイル:{0} 行:{1} 項目:{2} 値:{3}
                        msg =
                                WmsMessageFormatter.format(6026091, getFileName(),
                                        WmsFormatter.getNumFormat(getAllItemCount()), validateError.getFieldName(),
                                        validateError.getFieldValue());
                        errorFlag = SystemDefine.ERROR_FLAG_VALIDATE_ERROR;
                        break;
                    case ValidateError.RETURN_SPACE_ERROR:
                        // 6026085=文字の間にスペースが含まれているデータが存在しました。ファイル:{0} 行:{1} 項目:{2} 値:{3}
                        msg =
                                WmsMessageFormatter.format(6026085, getFileName(),
                                        WmsFormatter.getNumFormat(getAllItemCount()), validateError.getFieldName(),
                                        validateError.getFieldValue());
                        errorFlag = SystemDefine.ERROR_FLAG_VALIDATE_ERROR;
                        break;
                    default:
                        break;
                }

                if (!StringUtil.isBlank(msg))
                {
                    createErrorLog(msg, getLineData(), getClass());
                    createLoadErrorInfo(errorFlag, getEntity().getStoreMetaData().getFieldMetaData(
                            validateError.getMetaData().getName()).getDescription(), getLineData(), getClass());
                }
            }
        }
    }

    /**
     * 取込ファイルのファイルチェックを行います。<BR>
     * 取込ファイルをローカルへコピーします。
     * @return 正常ならTrue、不正ならfalse
     * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     */
    protected boolean isFileCheck()
            throws ScheduleException,
                IOException
    {
        try
        {
            // 取込ファイルの存在チェック
            if (!isFileCheck(getDataType()))
            {
                throw new FileNotFoundException();
            }

            // 取込ファイルをローカルにコピー
            if (!fileCopy())
            {
                // 6026097=２重取り込みが発生しました。ファイル:{0}
                setMessage(WmsMessageFormatter.format(6026097, getFilePath() + getFileName()));
                createErrorLog(WmsMessageFormatter.format(6026097, getFilePath() + getFileName()), getClass());
                return false;
            }

            return true;
        }
        catch (ReadWriteException ex)
        {
            // BSR書き込み
            createBSRLog(getClass(), true);
            // 6026004 = 環境情報ファイルが見つかりませんでした。{0}
            Object[] msg = new Object[1];
            msg[0] = WmsParam.ENVIRONMENT;
            RmiMsgLogClient.write(new TraceHandler(6026004, ex), getClass().getSimpleName(), msg);
            throw new ScheduleException();
        }
    }

    /**
     * 指定された取込ファイルの削除を行ないます。
     * @param filePath 取込ファイルのパス
     * @return ファイル削除が正常に行なえたらtrue、それ以外はfalse
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     */
    protected boolean fileDelete(String filePath)
            throws IOException
    {
        // ファイルチェックを行なう
        File file = new File(filePath);
        if (!file.exists())
        {
            throw new FileNotFoundException();
        }
        // ファイルの削除を行なう
        if (file.delete())
        {
            return true;
        }

        return false;
    }

    /**
     * FieldMetaDataから指定したFieldNameのPositionを返します。
     * @param handler ファイルハンドラクラス
     * @param value 取得するFieldName
     * @return 取得したFieldMetaのPosition
     */
    protected int getPosition(FileHandler handler, FieldName value)
    {
        // フィールド名取得
        FieldMetaData[] fieldMetas = handler.getStoreMetaData().getFieldMetaDatas();

        for (FieldMetaData fieldMeta : fieldMetas)
        {
            if (fieldMeta.getName().equals(value.getName()))
            {
                return fieldMeta.getPosition();
            }
        }
        return 0;
    }

    /**
     * 項目値をチェックします。
     * @param value チェックする値
     * @param itemKey 項目キー
     * @param isRequire trueなら必須項目チェック
     * @return 値が正しければvalueを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ValidateException 指定された値が不正だった場合に通知します。
     * @throws IllegalDataException 指定された値が不正だった場合に通知します（ファイルハンドラでのチェック以外の項目）。 
     */
    protected String getValue(String value, FieldName itemKey, boolean isRequire)
            throws ReadWriteException,
                IOException,
                ValidateException,
                IllegalDataException
    {
        _itemcount++;

        // 必須項目チェックを行う。
        if (isRequire && StringUtil.isBlank(value))
        {
            // 6026010=必須項目に値がないデータが存在しました。ファイル:{0} 行:{1} 項目:{2}
            createErrorLog(WmsMessageFormatter.format(6026010, getFileName(),
                    WmsFormatter.getNumFormat(getAllItemCount()), itemKey), getLineData(), getClass());
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_INDISPENSABLE_ITEM_BLANK,
                    getEntity().getStoreMetaData().getFieldMetaData(itemKey.getName()).getDescription(), getLineData(),
                    getClass());

            throw new ValidateException(itemKey, value, ValidateError.RETURN_REQUIRE_ERROR, new FieldMetaData());
        }

        // 禁止文字チェックを行う。
        if (isPatternMatching(value))
        {
            // 6026081=禁止文字が含まれているデータが存在しました。ファイル:{0} 行:{1} 項目:{2} 値:{3}
            createErrorLog(WmsMessageFormatter.format(6026081, getFileName(),
                    WmsFormatter.getNumFormat(getAllItemCount()), itemKey, value), getLineData(), getClass());
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_PROHIBITION_CHARACTER,
                    getEntity().getStoreMetaData().getFieldMetaData(itemKey.getName()).getDescription(), getLineData(),
                    getClass());

            throw new IllegalDataException(IllegalDataException.ERROR_LEVEL_WARN);
        }

        return value;
    }

    /**
     * 項目値をチェックします。<BR>
     * 必須チェックは行ないません。<BR>
     * @param value チェックする値
     * @param itemKey 項目キー
     * @return 値が正しければvalueを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ValidateException 指定された値が不正だった場合に通知します。
     * @throws IllegalDataException 指定された値が不正だった場合に通知します（ファイルハンドラでのチェック以外の項目）。 
     */
    protected String getValue(String value, FieldName itemKey)
            throws ReadWriteException,
                IOException,
                ValidateException,
                IllegalDataException
    {
        return getValue(value, itemKey, false);
    }

    /**
     * 数値項目をチェックします。
     * @param value チェックする値
     * @param itemKey 項目キー
     * @return 値が正しければvalueを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ValidateException 指定された値が不正だった場合に通知します。
     */
    protected int getIntValue(int value, FieldName itemKey)
            throws ReadWriteException,
                IOException,
                ValidateException
    {
        _itemcount++;

        // 0より小さい値は異常
        if (value < 0)
        {
            // 6026087=整数値の項目にマイナス値のデータが存在しました。ファイル:{0} 行:{1} 項目:{2} 値:{3}
            createErrorLog(WmsMessageFormatter.format(6026087, getFileName(),
                    WmsFormatter.getNumFormat(getAllItemCount()), itemKey, value), getLineData(), getClass());
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                    getEntity().getStoreMetaData().getFieldMetaData(itemKey.getName()).getDescription(), getLineData(),
                    getClass());

            throw new ValidateException(itemKey, value, ValidateError.RETURN_RANGE_ERROR, new FieldMetaData());
        }

        return value;
    }

    /**
     * 日付項目をチェックします。
     * @param value チェックする値
     * @param itemKey 項目キー
     * @param isRequire trueなら必須項目チェック
     * @return 値が正しければvalueを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ValidateException 指定された値が不正だった場合に通知します。
     */
    protected String getDateValue(String value, FieldName itemKey, boolean isRequire)
            throws ReadWriteException,
                IOException,
                ValidateException
    {
        _itemcount++;

        // 必須項目チェックを行う。
        if (isRequire && StringUtil.isBlank(value))
        {
            // 6026010=必須項目に値がないデータが存在しました。ファイル:{0} 行:{1} 項目:{2}
            createErrorLog(WmsMessageFormatter.format(6026010, getFileName(),
                    WmsFormatter.getNumFormat(getAllItemCount()), itemKey), getLineData(), getClass());
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_INDISPENSABLE_ITEM_BLANK,
                    getEntity().getStoreMetaData().getFieldMetaData(itemKey.getName()).getDescription(), getLineData(),
                    getClass());

            throw new ValidateException(itemKey, value, ValidateError.RETURN_REQUIRE_ERROR, new FieldMetaData());
        }

        // 空白はそのまま返す
        if (value.equals(""))
        {
            return value;
        }

        // 日付型のチェックを行う
        WmsChecker wmsChecker = new WmsChecker();
        if (!wmsChecker.checkDay(value))
        {
            // 6026086=日付の項目に有効ではないデータが存在しました。ファイル:{0} 行:{1} 項目:{2} 値:{3}
            createErrorLog(WmsMessageFormatter.format(6026086, getFileName(),
                    WmsFormatter.getNumFormat(getAllItemCount()), itemKey, value), getLineData(), getClass());
            createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                    getEntity().getStoreMetaData().getFieldMetaData(itemKey.getName()).getDescription(), getLineData(),
                    getClass());

            throw new ValidateException(itemKey, value, ValidateError.RETURN_TYPE_ERROR, new FieldMetaData());
        }

        return value;
    }

    /**
     * 日付項目をチェックします。<BR>
     * 必須チェックは行ないません。<BR>
     * @param value チェックする値
     * @param itemKey 項目キー
     * @return 値が正しければvalueを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ValidateException 指定された値が不正だった場合に通知します。
     */
    protected String getDateValue(String value, FieldName itemKey)
            throws ReadWriteException,
                IOException,
                ValidateException
    {
        return getDateValue(value, itemKey, false);
    }

    /**
     * ASCII項目をチェックします。
     * @param value チェックする値
     * @param itemKey 項目キー
     * @param isRequire trueなら必須項目チェック
     * @return 値が正しければvalueを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ValidateException 指定された値が不正だった場合に通知します。
     * @throws IllegalDataException 指定された値が不正だった場合に通知します（ファイルハンドラでのチェック以外の項目）。 
     */
    protected String getAsciiValue(String value, FieldName itemKey, boolean isRequire)
            throws ReadWriteException,
                IOException,
                ValidateException,
                IllegalDataException
    {
        // 禁止文字チェック
        getValue(value, itemKey);

        // ASCIIコードチェック
        if (value != null)
        {
            if (!isAsciiChar(value))
            {
                // 6026084=半角英数字の項目に有効ではないデータが存在しました。ファイル:{0} 行:{1} 項目:{2} 値:{3}
                createErrorLog(WmsMessageFormatter.format(6026084, getFileName(),
                        WmsFormatter.getNumFormat(getAllItemCount()), itemKey, value), getLineData(), getClass());
                createLoadErrorInfo(SystemDefine.ERROR_FLAG_VALIDATE_ERROR,
                        getEntity().getStoreMetaData().getFieldMetaData(itemKey.getName()).getDescription(),
                        getLineData(), getClass());

                throw new ValidateException(itemKey, value, ValidateError.RETURN_TYPE_CLASS_ERROR, null);
            }
        }

        return value;
    }

    /**
     * ASCII項目をチェックします。<BR>
     * 必須チェックは行ないません。<BR>
     * @param value チェックする値
     * @param itemKey 項目キー
     * @return 値が正しければvalueを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ValidateException 指定された値が不正だった場合に通知します。
     * @throws IllegalDataException 指定された値が不正だった場合に通知します（ファイルハンドラでのチェック以外の項目）。 
     */
    protected String getAsciiValue(String value, FieldName itemKey)
            throws ReadWriteException,
                IOException,
                ValidateException,
                IllegalDataException
    {
        return getAsciiValue(value, itemKey, false);
    }

    /**
     * 指定された文字列内に、システムで定義された禁止文字が含まれているかどうかチェックします。<BR>
     * 禁止文字の定義は、CommonParamにて指定します。
     * @param value 対象となる文字列を指定します。
     * @return 文字列中に禁止文字が含まれる場合はtrue, 禁止文字が含まれない場合はfalseを返します。
     */
    protected boolean isPatternMatching(String value)
    {
        if (DisplayText.isPatternMatching(value))
        {
            return true;
        }
        return false;
    }

    /**
     * 内部で保持してる値の初期化<BR>
     */
    protected void initData()
    {
        setStartDate(null);
        setSkipFlag(false);
        setRegistFlag(false);
        setErrorFlag(false);
        setAllItemCount(0);
        setItemcount(0);
    }

    /**
     * 指定されたフォルダ内から指定された接頭辞と接尾辞のファイル名のファイル数を返します。<BR>
     *
     * @param targetFolder 検索フォルダ
     * @param filePrefix ファイル接頭辞
     * @param fileSuffix ファイル接尾辞
     * @return 見つかったファイル数
     */
    protected int existFileCount(String targetFolder, String filePrefix, String fileSuffix)
    {
        // 検索対象フォルダのFileインスタンスの生成
        File file = new File(targetFolder);

        // ファイルフィルター
        FilenameFilter filter = new PrePostFileFilter(filePrefix, fileSuffix);

        // フィルタでファイルを取得
        String[] fileList = file.list(filter);
        if (ArrayUtil.isEmpty(fileList))
        {
            return 0;
        }
        return fileList.length;
    }

    /**
     * 環境設定ファイルから格納フォルダ、ファイル名をセットします。<BR>
     *
     * @param targetFolder 検索フォルダ
     * @param filePrefix ファイル接頭辞
     * @param fileSuffix ファイル接尾辞
     * @return 正常にファイル名をセットの場合true、それ以外の場合はfalseを返します。
     */
    protected boolean createFileName(String targetFolder, String filePrefix, String fileSuffix)
    {
        // 検索対象フォルダのFileインスタンスの生成
        File file = new File(targetFolder);

        // ファイルフィルター
        FilenameFilter filter = new PrePostFileFilter(filePrefix, fileSuffix);

        // フィルタでファイルを取得
        String[] fileList = file.list(filter);

        if (ArrayUtil.isEmpty(fileList))
        {
            // 6003009=ファイルが見つかりませんでした。{0}
            setMessage(WmsMessageFormatter.format(6003009, filePrefix + " " + fileSuffix));
            setFileName("");
            setRegistFlag(false);
            return false;
        }

        setFilePath(targetFolder);
        setFileName(fileList[0]);

        return true;
    }

    /**
     * データ取込のfinally処理を行ないます。<BR>
     * エラーファイルの書き込み、取込ファイルの削除、BSRの書き込みを行ないます。
     * @param handler  ファイルハンドラオブジェクト
     * @param isFileCopy  ファイルコピー（取込場所→履歴フォルダ）が完了しているかのフラグ
     * @return ファイルの削除に成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws ScheduleException 例外が発生した場合に通知されます。
     */
    protected boolean loadFinallyProc(FileHandler handler, boolean isFileCopy)
            throws ScheduleException
    {
        // １件でも対象データがあった場合はエラーファイルに書き込む
        if (!StringUtil.isBlank(getErrlist().getFileName()))
        {
            getErrlist().writeStatusFile();
        }

        // 処理が終了したら取込ファイルを削除します。
        boolean deleteFile = true; //glm we're not deleting any files here, so return true (was false);
        if (!StringUtil.isBlank(getFileName()))
        {
            if (handler != null)
            {
                handler.close();
            }
            // 正常、注意の場合は削除を実行。エラー時は削除しない
            if (isFileCopy && !isErrorFlag())
            {
                try
                {
                    // 取込ファイルの削除
                    if (fileDelete(getFilePath() + "\\" + getFileName()))
                    {
                        deleteFile = true;
                    }
                    else
                    {
                        // 6026099=取込ファイルの削除に失敗しました。ファイル:{0}
                        setMessage(WmsMessageFormatter.format(6026099, getFilePath() + getFileName()));
                        createErrorLog(WmsMessageFormatter.format(6026099, getFilePath() + getFileName()),
                                this.getClass());
                        setErrorFlag(true);
                    }
                }
                catch (IOException e)
                {
                    // 6026099=取込ファイルの削除に失敗しました。ファイル:{0}
                    setMessage(WmsMessageFormatter.format(6026099, getFilePath() + getFileName()));
                    createErrorLog(WmsMessageFormatter.format(6026099, getFilePath() + getFileName()), this.getClass());
                    setErrorFlag(true);
                }
            }
        }

        return deleteFile;
    }

    /**
     * 取込区分に紐づく取込可能件数を取得します。
     * 
     * @param dataType 取込区分
     * @return 取込可能件数
     * @throws CommonException
     */
    public Integer getMaxRecord(String dataType)
            throws CommonException
    {
        // 交換データ環境定義情報ハンドラの生成
        ExchangeEnvironmentHandler exHdl = new ExchangeEnvironmentHandler(getConnection());
        // 交換データ環境定義情報検索キーの生成
        ExchangeEnvironmentSearchKey skey = new ExchangeEnvironmentSearchKey();
        
        // 取得項目
        skey.setMaxRecordCollect();

        // 検索条件
        skey.setJobType(dataType);
        skey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_RECEIVE);
        skey.setDataType(ExchangeEnvironment.DATA_DB);
        
        // 検索を行い件数分繰り返す
        ExchangeEnvironment exEnvs = (ExchangeEnvironment)exHdl.findPrimary(skey);
        
        // 取得した出力件数を返却
        return exEnvs.getMaxRecord();
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * WMSParamの環境情報ファイルから環境情報を取得します。<BR>
     * 取得したファイル名で始まるファイルを検索します。<BR>
     * @param loadDataType 取込パッケージ
     * @return ファイルが存在したらtrue、存在しなかったfalse
     * @throws ReadWriteException ファイルの入出力に失敗した場合に通知されます。
     */
    private boolean isFileCheck(String loadDataType)
            throws ReadWriteException
    {
        // 環境情報の取り込み
        IniFileOperator iniFileOperator = new IniFileOperator(WmsParam.ENVIRONMENT);
        // フォルダ名
        setFilePath(iniFileOperator.get(DATALOAD_FOLDER, getDataLoadKey()));
        // ファイル名
        String fileName = iniFileOperator.get(DATALOAD_FILENAME, getDataLoadKey());

        // 検索対象フォルダのFileインスタンスの生成
        File file = new File(getFilePath());

        // ファイルフィルター
        FilenameFilter filter = new PrePostFileFilter(fileName, Parameter.EXTENSION);

        // フィルタでファイルを取得
        String[] fileList = file.list(filter);

        if (ArrayUtil.isEmpty(fileList))
        {
            return false;
        }

        setFileName(fileList[0]);
        return true;
    }

    /**
     * 取込ファイルをWmsParam.HISTRY_HOSTDATA_PATHにコピーします。<BR>
     * すでに同名のファイルが存在したら、ファイル名+_日時(YYYYMMDDHHMMSS)を付けてコピーします。<BR>
     * @return ファイルコピーが正常に行なえたらtrue、コピー先に同名のファイルが存在したらfalse
     * @throws IOException ファイルの入出力に失敗した場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private boolean fileCopy()
            throws IOException,
                ReadWriteException
    {
        boolean fileCopy = false;

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try
        {
            // コピー元ファイルの存在チェックを行う
            if (new File(getFilePath() + getFileName()).exists())
            {
                fis = new FileInputStream(getFilePath() + getFileName());
            }
            else
            {
                throw new FileNotFoundException();
            }

            // コピー先に同名のファイルが存在するかチェック
            if (new File(DATALOAD_BACKUP_FOLDER + getFileName()).exists())
            {
                String tofile = DATALOAD_BACKUP_FOLDER + getFileName();
                // DBのシステム日付を取得
                String datestring = "_" + DbDateUtil.getSystemDateTime();
                int last = tofile.lastIndexOf(".");
                StringBuffer stBuf = new StringBuffer(tofile);
                String toFile = String.valueOf(stBuf.insert(last, datestring));

                // 取込ファイル名+_日時で作成
                fos = new FileOutputStream(toFile);
                fileCopy = false;
            }
            else
            {
                fos = new FileOutputStream(DATALOAD_BACKUP_FOLDER + getFileName());
                fileCopy = true;
            }

            int byt;
            while ((byt = fis.read()) != -1)
            {
                fos.write((byte)byt);
            }

            return fileCopy;
        }
        finally
        {
            if (fis != null)
            {
                fis.close();
            }
            if (fos != null)
            {
                fos.close();
            }
        }
    }

    /**
     * ASCII文字(半角英数字記号+半角スペース)かどうかを判別します。<BR>
     *
     *       ASCII CHARACTER<br>
     *       JIS  SJIS EUC   +0 +1 +2 +3 +4 +5 +6 +7 +8 +9 +A +B +C +D +E +F<BR>
     *       20   20   20       !  "  #  $  %  &  '  (  )  *  +  ,  -  .  /<BR>
     *       30   30   30    0  1  2  3  4  5  6  7  8  9  :  ;  <  =  >  ?<BR>
     *       40   40   40    @  A  B  C  D  E  F  G  H  I  J  K  L  M  N  O<BR>
     *       50   50   50    P  Q  R  S  T  U  V  W  X  Y  Z  [  \  ]  ^  _<BR>
     *       60   60   60    `  a  b  c  d  e  f  g  h  i  j  k  l  m  n  o<BR>
     *       70   70   70    p  q  r  s  t  u  v  w  x  y  z  {  |  }  ~   <BR>
     * @param  value  入力文字列
     * @return boolean ASCII文字の場合trueを返します。
     */
    private boolean isAsciiChar(String value)
    {
        for (int i = 0; i < value.length(); i++)
        {
            char charData = value.charAt(i);
            if ((charData < ' ') || (charData > '~'))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 改行コードを削除します。<BR>
     * @param str 改行コード削除文字列
     * @return 削除後の文字列
     */
    protected String cutCRLF(String str)
    {
        if (!StringUtil.isBlank(str))
        {
            if (str.endsWith("\r\n"))
            {
                // 末尾に改行コードCR+LFがあれば削除
                return str.substring(0, str.length() - 2);
            }
            else if (str.endsWith("\r") || str.endsWith("\n"))
            {
                // 末尾に改行コードCRもしくはLFがあれば削除
                return str.substring(0, str.length() - 1);
            }
            else
            {
                return str;
            }
        }
        else
        {
            return null;
        }

    }

    /**
     * 対象Entityの取得
     *
     * @return AbstractEntity 対象Entityの実態
     * @throws CommonException
     */
    abstract protected AbstractEntity getEntity();

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractDBDataLoader.java 7650 2010-03-17 09:31:17Z okayama $";
    }
}
