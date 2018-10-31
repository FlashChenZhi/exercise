//$Id: HostDBDataLoadSCH.java 7650 2010-03-17 09:31:17Z okayama $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.system.schedule.HostDBDataLoadSCHParams.*;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DateOperator;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.DBDataLoader;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_terminalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostToEWNFinder;
import jp.co.daifuku.wms.base.dbhandler.HostToEWNHandler;
import jp.co.daifuku.wms.base.dbhandler.HostToEWNSearchKey;
import jp.co.daifuku.wms.base.entity.Com_terminal;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.HostToEWN;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DBDataLoadLogFileWriter;
import jp.co.daifuku.wms.base.util.DataLoadLogFileWriter;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.system.dasch.DBDataLoadDASCH;
import jp.co.daifuku.wms.system.dasch.DataLoadDASCHParams;
import jp.co.daifuku.wms.system.exporter.HostDBCheckListParams;
import jp.co.daifuku.wms.system.exporter.WMS0806Params;

/**
 * DBデータ取込のスケジュール処理を行います。
 *
 * @version $Revision: 7650 $, $Date: 2010-03-17 18:31:17 +0900 (水, 17 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class HostDBDataLoadSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * Customer Database connection
     */
    private Connection _customerConn = null;

    /**
     * 取込エラー情報用データベースコネクション
     */
    private Connection _errorInfoConn = null;

    /**
     * WMSサーバの端末No.
     */
    protected static final String SERVER_TERMINAL_NO = "Svr";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * Use this date for limiting the DNLOADERROR report query to this session's results only
     */
    private Date _startDate = null;

    /**
     * Host to eWareNavi Handler
     */
    private HostToEWNHandler _Hdlr;

    /**
     * Host to eWareNavi Finder
     */
    private HostToEWNFinder _Fndr;

    /**
     * Host to eWareNavi Search Key
     */
    private HostToEWNSearchKey _SKey;

    /**
     * 取込エラーログファイル用
     */
    private DBDataLoadLogFileWriter _rawlist = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * Constructor to create SCH object
     * @param conn Database Connection
     * @param customerConn Customer Connection
     * @param parent Caller Class
     * @param locale Browser Locale
     * @param ui DfkUserInfo
     * @throws CommonException Reports if an unexpected exception occurs when checkging.
     */
    public HostDBDataLoadSCH(Connection conn, Connection customerConn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
        setCustomerConnection(customerConn);
        _Hdlr = new HostToEWNHandler(customerConn);
        _Fndr = new HostToEWNFinder(customerConn);
        _SKey = new HostToEWNSearchKey();
    }

    /**
     * Constructor to create SCH object
     * @param conn Database Connection
     * @param customerConn Customer Connection
     * @param parent Caller Class
     * @param locale Browser Locale
     * @param ui DfkUserInfo
     * @throws CommonException Reports if an unexpected exception occurs when checkging.
     */
    public HostDBDataLoadSCH(Connection conn, Connection customerConn, Connection errInfoConn, Class parent,
            Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
        setCustomerConnection(customerConn);
        setErrorInfoConn(errInfoConn);
        _Hdlr = new HostToEWNHandler(customerConn);
        _Fndr = new HostToEWNFinder(customerConn);
        _SKey = new HostToEWNSearchKey();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * Receives data that was entered on the screen as parameter,
     * and retrieves output data of the List Area from the database, and return it.<BR>
     *
     * @param p Receiving condition for display data saved in <CODE>ScheduleParams</CODE>.<BR>
     * @return retrieved data saved in <CODE>Params</CODE> array.<BR>
     *          Returns 0 array if no applicable record was found.<BR>
     *          Displays up to the maximum number of display if the retrieved output exceeds the maximum.<BR>
     *          Retuns null if an error occurs in the middle of entering condition.<BR>
     * @throws CommonException Reports if an unexpected exception occurs when checkging.
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // 交換データ環境定義情報ハンドラの生成
        ExchangeEnvironmentHandler exHdl = new ExchangeEnvironmentHandler(getConnection());
        // 交換データ環境定義情報検索キーの生成
        ExchangeEnvironmentSearchKey skey = new ExchangeEnvironmentSearchKey();

        // 返却パラメータ配列の生成
        List<Params> result = new ArrayList<Params>();
        // 返却パラメータの生成
        Params param = null;

        // 取得項目
        skey.setJobTypeCollect();
        skey.setClassNameCollect();
        skey.setDataNameCollect();

        // 検索条件
        skey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_RECEIVE);
        skey.setDataType(ExchangeEnvironment.DATA_DB);

        // 並び順
        skey.setJobTypeOrder(true);

        HostToEWNFinder finder = null;
        try
        {
            // 件数を取得する変数を生成
            finder = new HostToEWNFinder(getCustomerConnection());
            finder.open(true);
            int totalMessages = 0;

            // 検索を行い件数分繰り返す
            ExchangeEnvironment[] exEnvs = (ExchangeEnvironment[])exHdl.find(skey);
            for (ExchangeEnvironment exEnv : exEnvs)
            {
                // 返却パラメータの生成
                param = new Params();

                // 件数の取得
                int count = getMessageCount(finder, exEnv.getJobType());
                totalMessages += count;

                // 取込区分
                param.set(DATA_TYPE, exEnv.getJobType());
                // 取込内容
                param.set(IMPORT_DATA_TYPE, exEnv.getDataName());
                // データ数
                param.set(NO_OF_IMPORT_MSGS, count);
                // クラス名
                param.set(CLASS_NAME, exEnv.getClassName());
                // 自動印刷
                param.set(AUTO_PRINT, exEnv.getAutoPrintErrorList());

                // 生成したパラメータを配列に格納
                result.add(param);
            }

            // Terminate if there is no data
            if (totalMessages <= 0)
            {
                // 6003011=対象データはありませんでした。
                setMessage("6003011");
            }
            else
            {
                // 6001013=表示しました。
                setMessage("6001013");
            }

            // 生成した返却パラメータ配列を返却
            return result;
        }
        catch (Exception e)
        // catch (SQLException e)
        {
            throw new ReadWriteException(e);
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * スケジュールを開始します。<CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、<BR>
     * スケジュール処理を行います。スケジュール処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
     * スケジュール処理が成功した場合は結果をセットした<CODE>Parameter</CODE>を返します。<BR>
     * 条件エラーなどでスケジュール処理が失敗した場合はnullを返します。<BR>
     * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
     * 詳しい動作はクラス説明の項を参照してください。<BR>
     * @param ps 検索条件をもつ<CODE>Parameter</CODE>クラスを継承したクラス
     * @return スケジュール処理に成功した場合は<CODE>Parameter</CODE>を返します。失敗した場合はnullを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public List<Params> startSCHgetParams(ScheduleParams... ps)
            throws CommonException
    {
        // 返却パラメータ用ArrayList
        List<Params> hostDBLoadOutList = new ArrayList<Params>();

        // 取込処理成否フラグ
        boolean isResultFlag = true;
        boolean isWarningFlag = false;
        boolean isSkipFlag = false;
        boolean isRegistFlag = false;
        // 印刷処理成否フラグ
        boolean isPrintFlg = true;

        // 取り込み中フラグが自クラスで更新されているかを判定する為のフラグ
        boolean isUpdateLoadDataFlag = false;

        // 取込エラー情報コネクション
        Connection errorInfoConn = null;

        try
        {
            // システム定義情報のコンストラクタ
            WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

            // 処理開始チェック
            if (!canStart())
            {
                return null;
            }
            // 搬送データクリアチェック
            if (isAllocationClear())
            {
                return null;
            }
            // 取込中チェック
            if (isLoadData())
            {
                return null;
            }
            // 取込中フラグ更新
            if (!doLoadStart())
            {
                return null;
            }
            doCommit(this.getClass());
            isUpdateLoadDataFlag = true;

            // 自動取込フラグ
            boolean autoLoad = false;

            try
            {
                errorInfoConn = ConnectionManager.getConnection(WMSConstants.DATASOURCE_NAME);
            }
            catch (SQLException e)
            {
                autoLoad = true;
                errorInfoConn = getErrorInfoConn();
            }
            // 各取込処理パッケージのインスタンスを生成
            DBDataLoader dataLoader = null;

            String time = DateOperator.getSysdateTime();

            //For making a file copy of the raw HostToEWN data
            _rawlist = new DBDataLoadLogFileWriter();
            String fileName = ("HostToEWN" + time + SystemInParameter.EXTENSION);
            _rawlist.setPlainFileName(fileName);

            //Create and initialize an outparam for each inparam, and add the outparam to a messagetype map
            Map<String, Params> m = new HashMap<String, Params>();

            for (ScheduleParams p : ps)
            {
                Params outP = new Params();
                String myDataType = p.getString(DATA_TYPE);
                outP.set(DATA_TYPE, myDataType);
                outP.set(SELECT, p.getString(SELECT));
                outP.set(IMPORT_DATA_TYPE, DisplayResource.getLoadDataType(outP.getString(DATA_TYPE),
                        Locale.getDefault()));
                outP.set(NO_OF_IMPORT_MSGS, 0);
                outP.set(CUMULATIVE_REGIST_FLAG, false);
                outP.set(CUMULATIVE_SKIP_FLAG, false);
                outP.set(CUMULATIVE_RESULT_FLAG, true);
                outP.set(EXPECTED_LOADLINES_COUNT, p.getInt(NO_OF_IMPORT_MSGS));
                outP.set(ATTEMPTED_LOADLINES_COUNT, 0);
                outP.set(REGIST_TIME, time);
                outP.set(CUMULATIVE_WARNING_FLAG, false);
                outP.set(RESULT_OF_IMPORT, "");
                dataLoader = getDataLoader(errorInfoConn, myDataType, p.getString(CLASS_NAME));
                outP.set(THE_LOADER, dataLoader);
                if (dataLoader != null)
                {
                    //meaningless; date is null at this point inParam.setLoadStartDate(dataLoader.getStartDate());
                    outP.set(THE_LOADERS_LIMIT, dataLoader.getMyLoadingLimit());
                }
                else
                {
                    outP.set(THE_LOADERS_LIMIT, 0); //we don't know what the non-existent loader's limit is
                }
                m.put(myDataType, outP);
                hostDBLoadOutList.add(outP);
            }

            //SupplierDataLoader dataLoader = null;
            _SKey.clear();
            //_SKey.setStatusFlag(STATUS_FLAG_UNPROCESSED);
            // Primary Sort
            _SKey.setMessageDateOrder(true);
            // Secondary sort by sequence
            _SKey.setSequenceNoOrder(true);

            try
            //Finder open/close
            {
                _startDate = DbDateUtil.getTimeStamp(); //mark the beginning of this session so that we can query DNLOADERROR later
                _Fndr.open(true);
                _Fndr.searchForUpdate(_SKey, HostToEWNFinder.WAIT_SEC_NOWAIT);

                boolean aLimitWasReached = false;

                while (_Fndr.hasNext() && !aLimitWasReached)
                {
                    HostToEWN[] fromHostList = (HostToEWN[])_Fndr.getEntities(100); //just 100 at a time so we don't overflow mem.

                    //Dump the raw HostToEWN data to a file
                    try
                    {
                        for (HostToEWN hostMessage : fromHostList)
                        {
                            String myLineData = hostMessage.getEntireRecord();
                            _rawlist.addStatusFile(DataLoadLogFileWriter.STATUS_NORMAL, myLineData, "");
                        }
                    }
                    finally
                    {
                        _rawlist.writeStatusFile();
                    }
                    for (HostToEWN hostMessage : fromHostList)
                    {
                        //get the outparam for this message type
                        String myMessageID = hostMessage.getMessageId();
                        Params myOutParam = m.get(myMessageID);
                        if (myOutParam != null)
                        {
                            dataLoader = (DBDataLoader)myOutParam.get(THE_LOADER);
                            myOutParam.set(ATTEMPTED_LOADLINES_COUNT, myOutParam.getInt(ATTEMPTED_LOADLINES_COUNT) + 1);

                            //See if we need to stop loading (prevent us from dropping records that we can't process)
                            if (myOutParam.getInt(ATTEMPTED_LOADLINES_COUNT) > myOutParam.getInt(THE_LOADERS_LIMIT))
                            {
                                aLimitWasReached = true;
                                myOutParam.set(CUMULATIVE_RESULT_FLAG, false); //freeze the message for this import type
                                myOutParam.set(RESULT_OF_IMPORT, MessageResource.getMessage(WmsMessageFormatter.format(
                                        6001027, WmsFormatter.getNumFormat(myOutParam.getInt(THE_LOADERS_LIMIT)),
                                        Locale.getDefault())));
                                break;
                            }

                            try
                            {
                                _Hdlr.drop(hostMessage);
                                // commit.
                                getCustomerConnection().commit();
                            }
                            catch (ReadWriteException e)
                            {
                                myOutParam.set(CUMULATIVE_RESULT_FLAG, false); //freeze the message for this import type
                                // (6007002)"Database error occurred. See the log."
                                myOutParam.set(RESULT_OF_IMPORT, MessageResource.getMessage(
                                        WmsMessageFormatter.format(6007002), Locale.getDefault()));
                                // rollback.
                                getCustomerConnection().rollback();
                                continue;
                            }
                            // データが存在しなかった場合
                            catch (NotFoundException e)
                            {
                                myOutParam.set(CUMULATIVE_RESULT_FLAG, false); //freeze the message for this import type
                                // (6003006)"Unable to process this data. It has been updated by another terminal."
                                myOutParam.set(RESULT_OF_IMPORT, MessageResource.getMessage(
                                        WmsMessageFormatter.format(6003006), Locale.getDefault()));
                                // rollback.
                                getCustomerConnection().rollback();
                                continue;
                            }

                            try
                            {
                                // データ取込処理
                                if (dataLoader.loadEntity(sysController, hostMessage, myOutParam))
                                {
                                    isResultFlag = true;
                                    if (!StringUtil.isBlank(dataLoader.getMessage()))
                                    {
                                        setMessage(dataLoader.getMessage());
                                    }
                                    // 1レコード単位でコミット
                                    doCommit(this.getClass());

                                    //increment the count for this message type
                                    myOutParam.set(NO_OF_IMPORT_MSGS, myOutParam.getInt(NO_OF_IMPORT_MSGS) + 1);
                                }
                                else
                                {
                                    isResultFlag = false;
                                    if (!StringUtil.isBlank(dataLoader.getMessage()))
                                    {
                                        setMessage(dataLoader.getMessage());
                                    }
                                    // 1レコード単位でロールバック
                                    doRollBack(this.getClass());
                                }
                            }
                            catch (Exception e)
                            {
                                System.out.println(e.getMessage());
                                throw new RuntimeException(e);
                            }

                            // 取込エラー情報コネクションをコミット
                            errorInfoConn.commit();

                            // パッケージ単位のメッセージセットを行います。
                            if (isResultFlag)
                            {
                                //update cumulative skip result for this message type
                                //if this ever comes back true we need to set a flag in the outparam (false by default)
                                if (dataLoader.isSkipFlag())
                                {
                                    myOutParam.set(CUMULATIVE_SKIP_FLAG, true);//flag indicating that at least one line of a specific loading type was skipped.
                                }
                                //update cumulative loading result for this message type
                                //if this ever comes back true we need to remember that we loaded something 
                                //set the flag in the pertinent outparam to true (false by default)
                                if (dataLoader.isRegistFlag())
                                {
                                    myOutParam.set(CUMULATIVE_REGIST_FLAG, true);//Flag indicating that at least one line of a specific loading type was accepted.
                                }

                            }
                            else
                            {
                                myOutParam.set(CUMULATIVE_RESULT_FLAG, false); //freeze the message for this import type

                                // 取込が異常終了した場合、メッセージを取得する
                                if (!StringUtil.isBlank(dataLoader.getMessage()))
                                {
                                    myOutParam.set(RESULT_OF_IMPORT, MessageResource.getMessage(
                                            dataLoader.getMessage(), Locale.getDefault()));
                                }
                            }
                        }
                    }
                    _Fndr.searchForUpdate(_SKey, HostToEWNFinder.WAIT_SEC_NOWAIT); //re-fetch, since we dropped some records
                }

                //screen doesn't want to see the total lines of each type loaded
                for (Params outparam : hostDBLoadOutList)
                {
                    outparam.set(NO_OF_IMPORT_MSGS, getMessageCount(_Fndr, outparam.getString(DATA_TYPE)));
                }
            }
            finally
            //Finder open/close
            {
                _Fndr.close();
            }

            //check the cumulative data for each datatype and update some type-wise messages to the operator
            isSkipFlag = false;
            isRegistFlag = false;
            isResultFlag = true;
            isWarningFlag = false;
            boolean attemptedLoad = false;

            for (Params myOutParam : hostDBLoadOutList)
            {
                //                if (myOutParam.getInt(ATTEMPTED_LOADLINES_COUNT) > 0 && !autoLoad)
                if (myOutParam.getInt(ATTEMPTED_LOADLINES_COUNT) > 0)
                {
                    attemptedLoad = true; //the loading process attempted to load something
                    if (!myOutParam.getBoolean(CUMULATIVE_RESULT_FLAG))
                    {
                        isWarningFlag = true;
                        //we've already set the type-wise message for warnings
                    }
                    else if (!myOutParam.getBoolean(CUMULATIVE_SKIP_FLAG))
                    {
                        if (myOutParam.getBoolean(CUMULATIVE_REGIST_FLAG))
                        {
                            if (!autoLoad)
                            {
                                // 6021011=Data download completed successfully.
                                myOutParam.set(RESULT_OF_IMPORT, MessageResource.getMessage(
                                        WmsMessageFormatter.format(6021011), Locale.getDefault()));
                            }
                        }
                        else
                        {
                            isWarningFlag = true;
                            if (!autoLoad)
                            {
                                // 6003011 = Target data was not found.
                                myOutParam.set(RESULT_OF_IMPORT, MessageResource.getMessage(
                                        WmsMessageFormatter.format(6003011), Locale.getDefault()));
                            }
                        }
                    }
                    else
                    {
                        isSkipFlag = true;
                        if (myOutParam.getBoolean(CUMULATIVE_REGIST_FLAG))
                        {
                            isRegistFlag = true;
                            if (!autoLoad)
                            {
                                // 6021012=一Skipped part of the data and the download completed.
                                myOutParam.set(RESULT_OF_IMPORT, MessageResource.getMessage(
                                        WmsMessageFormatter.format(6021012), Locale.getDefault()));
                            }
                        }
                        else
                        {
                            if (!autoLoad)
                            {
                                // 6021013=一Skipped part of the data and no target data found.
                                myOutParam.set(RESULT_OF_IMPORT, MessageResource.getMessage(
                                        WmsMessageFormatter.format(6021013), Locale.getDefault()));
                            }
                        }
                    }
                    //write a cumulative BSR entry for each loader
                    dataLoader = (DBDataLoader)myOutParam.get(THE_LOADER);
                    if (dataLoader != null)
                    {
                        dataLoader.doBSRLog(myOutParam.getBoolean(CUMULATIVE_SKIP_FLAG),
                                myOutParam.getBoolean(CUMULATIVE_REGIST_FLAG));
                    }
                } //if (myOutParam.getInt(ATTEMPTED_LOADLINES_COUNT) > 0)
            }
            //Generate an error report

            if (autoLoad)
            {
                this.getUserInfo().setTerminalPrinterName(this.getTerminalPrinterName(errorInfoConn));
            }

            // 取込チェックリストの印刷
            DBDataLoadDASCH dasch = new DBDataLoadDASCH(errorInfoConn, this.getClass(), null, this.getUserInfo());
            dasch.setForwardOnly(true);

            DataLoadDASCHParams listParam = new DataLoadDASCHParams();
            listParam.set(DataLoadDASCHParams.LOAD_START_DATE, _startDate);

            if (dasch.count(listParam) > 0)
            {
                dasch.search(listParam);

                ExporterFactory factory = new WmsExporterFactory(Locale.getDefault(), this.getUserInfo());
                PrintExporter exporter = factory.newPrinterExporter("HostDBCheckList", false);
                exporter.open();

                // export.
                int linesReported = 0;

                int max_num = WmsParam.MAX_NUMBER_OF_DATALOAD_ERROR_LIST;

                while (dasch.next() && linesReported < max_num)
                {
                    linesReported++;
                    Params daschParam = dasch.get();
                    HostDBCheckListParams expparam = new HostDBCheckListParams();
                    expparam.set(WMS0806Params.DATA, daschParam.get(DataLoadDASCHParams.DATA));
                    expparam.set(WMS0806Params.DFK_DS_NO, daschParam.get(DataLoadDASCHParams.DFK_DS_NO));
                    expparam.set(WMS0806Params.DFK_USER_ID, daschParam.get(DataLoadDASCHParams.DFK_USER_ID));
                    expparam.set(WMS0806Params.DFK_USER_NAME, daschParam.get(DataLoadDASCHParams.DFK_USER_NAME));
                    expparam.set(WMS0806Params.ERROR_DETAIL, daschParam.get(DataLoadDASCHParams.ERROR_DETAIL));
                    expparam.set(WMS0806Params.ERROR_LEVEL, daschParam.get(DataLoadDASCHParams.ERROR_LEVEL));
                    expparam.set(WMS0806Params.FILE_LINE_NO, daschParam.get(DataLoadDASCHParams.FILE_LINE_NO));
                    expparam.set(WMS0806Params.FILE_NAME, daschParam.get(DataLoadDASCHParams.FILE_NAME));
                    expparam.set(WMS0806Params.ITEM_NO, daschParam.get(DataLoadDASCHParams.ITEM_NO));
                    expparam.set(WMS0806Params.LOAD_TYPE, daschParam.get(DataLoadDASCHParams.LOAD_TYPE));
                    expparam.set(WMS0806Params.START_DAY, daschParam.get(DataLoadDASCHParams.START_DAY));
                    expparam.set(WMS0806Params.START_TIME, daschParam.get(DataLoadDASCHParams.START_TIME));
                    expparam.set(WMS0806Params.SYS_DAY, daschParam.get(DataLoadDASCHParams.SYS_DAY));
                    expparam.set(WMS0806Params.SYS_TIME, daschParam.get(DataLoadDASCHParams.SYS_TIME));

                    if (!exporter.write(expparam))
                    {
                        break;
                    }
                }
                // execute print.
                try
                {
                    exporter.print();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

            // Set the message for the main status bar
            if (isWarningFlag)
            {
                // 6026100 = 取込処理が正常に終了できませんでした。
                setMessage(WmsMessageFormatter.format(6026100));
            }
            else if (isSkipFlag)
            {
                if (isRegistFlag)
                {
                    // 6021012=一部のデータをスキップし、データの取込みは終了しました。
                    setMessage(WmsMessageFormatter.format(6021012));
                }
                else
                {
                    // 6021013=一部のデータをスキップした結果、対象データはありませんでした。
                    setMessage(WmsMessageFormatter.format(6021013));
                }
            }
            else if (!isPrintFlg)
            {
                // writerクラスのエラーメッセージを上書きしない。
            }
            else if (isResultFlag)
            {
                if (attemptedLoad)
                {
                    // 6021011=Data download completed successfully.
                    setMessage(WmsMessageFormatter.format(6021011));
                }
                else
                {
                    //6003011=Target data was not found.
                    setMessage(WmsMessageFormatter.format(6003011));
                }
            }
            else
            {
                // 6026100 = Failed to terminate loading process.
                setMessage(WmsMessageFormatter.format(6026100));
            }
            return hostDBLoadOutList;
        }
        catch (SQLException e)
        {
            throw new ReadWriteException(e);
        }
        catch (Exception e)
        {
            //            throw new Exception(e);
            e.printStackTrace();
            throw new ReadWriteException(e);
        }
        finally
        {
            // 取込エラー情報コネクションクローズ
            if (errorInfoConn != null)
            {
                try
                {
                    errorInfoConn.rollback();
                    errorInfoConn.close();
                }
                catch (SQLException ex)
                {
                    throw new ReadWriteException(ex);
                }
            }

            if (!isResultFlag)
            {
                doRollBack(this.getClass());
            }

            // 取り込み中フラグが自クラスで更新されたものであるならば、
            // 取り込み中フラグを、0：停止中にする
            if (isUpdateLoadDataFlag)
            {
                doLoadEnd();
                doCommit(this.getClass());
            }
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * Establish connection to the customer's database
     * @param conn Database Connection
     */
    protected void setCustomerConnection(Connection conn)
    {
        this._customerConn = conn;
    }

    /**
     * Returns host database connection
     * @return Database Connection
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
     * errorInfoConnを返します。
     * @return errorInfoConnを返します。
     */
    protected Connection getErrorInfoConn()
    {
        return _errorInfoConn;
    }

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
     * Get the count of this message type
     *
     * @param finder HostToEWNFinder
     * @param messageID データ
     * @return int
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private int getMessageCount(HostToEWNFinder finder, String messageID)
            throws ReadWriteException
    {
        HostToEWNSearchKey searchKey = new HostToEWNSearchKey();
        searchKey.setMessageId(messageID);
        return finder.search(searchKey);
    }

    /**
     * パッケージごとに <code>DataLoader</code>クラスを生成します。
     *
     * @param errorInfoConn エラー内容を出力するためのDBコネクション。エラー発生時もCommitします。
     * @param dataType データ区分
     * @param userInfo ユーザ情報
     * @return <code>DataLoader</code>
     */
    protected DBDataLoader getDataLoader(Connection errorInfoConn, String dataType, String className)
    {
        // コンストラクタクラス
        Constructor constructor = null;
        // インスタンス保持
        Object obj = null;

        try
        {
            // 文字列からクラスを生成
            Class<?> loaderClass = Class.forName(className);
            // コンストラクタを取得
            constructor = loaderClass.getConstructor(Connection.class, Connection.class, DfkUserInfo.class);
            // インスタンス生成
            obj = constructor.newInstance(getConnection(), errorInfoConn, this.getUserInfo());

            // 生成したインスタンスを返却
            return (DBDataLoader)obj;
        }
        // 問題があれば-1件として通知
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * WMSサーバの端末プリンタ名を取得します。
     * @param データベースコネクション
     * @return WMSサーバの端末プリンタ名
     * @throws ReadWriteException データベースエラーが発生した場合に通知されます。
     * @throws NoPrimaryException 検索結果が複数件存在した場合に通知されます。
     */
    private String getTerminalPrinterName(Connection conn)
            throws ReadWriteException,
                NoPrimaryException

    {
        Com_terminalHandler tHandler = new Com_terminalHandler(conn);
        Com_terminalSearchKey tKey = new Com_terminalSearchKey();

        tKey.setTerminalnumber(SERVER_TERMINAL_NO);
        Com_terminal terminal = (Com_terminal)tHandler.findPrimary(tKey);

        return terminal.getPrintername();
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns current repository info for this class
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class