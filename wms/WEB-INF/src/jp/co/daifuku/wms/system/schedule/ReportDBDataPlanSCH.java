// $Id: ReportDBDataPlanSCH.java 7735 2010-03-26 06:22:49Z okayama $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.system.schedule.ReportDBDataPlanSCHParams.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractReportDBDataCreator;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.ReportDBDataCreator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistoryHandler;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.ExchangeHistory;

/**
 * DB予定作業報告のスケジュール処理を行います。
 *
 * @version $Revision: 7735 $, $Date: 2010-03-26 15:22:49 +0900 (金, 26 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class ReportDBDataPlanSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private Connection _customerConn;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public ReportDBDataPlanSCH(Connection conn, Connection customerConn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
        setCustomerConnection(customerConn);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
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
        skey.setDataNameCollect();
        skey.setClassNameCollect();

        // 検索条件
        skey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_SEND);
        skey.setDataType(ExchangeEnvironment.DATA_DB);
        skey.setJobType(Parameter.DATA_TYPE_CROSSDOCK, "=", "(", "", false);
        skey.setJobType(Parameter.DATA_TYPE_RECEIVE, "=", "", "", false);
        skey.setJobType(Parameter.DATA_TYPE_STORAGE, "=", "", "", false);
        skey.setJobType(Parameter.DATA_TYPE_RETRIEVAL, "=", "", ")", true);

        // 検索を行い件数分繰り返す
        ExchangeEnvironment[] exEnvs = (ExchangeEnvironment[])exHdl.find(skey);
        for (ExchangeEnvironment exEnv : exEnvs)
        {
            param = new Params();

            // データ区分
            param.set(DATA_TYPE, exEnv.getJobType());
            param.set(REPORT_DATA_TYPE, exEnv.getDataName());
            param.set(CLASS_NAME, exEnv.getClassName());
            result.add(param);
        }
        // 生成した配列を返却
        return result;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     * 
     * @param ps 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return スケジュール結果を持つ<CODE>Params</CODE>配列。<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException スケジュール内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> startSCHgetParams(ScheduleParams... ps)
            throws CommonException
    {
        // 返却パラメータ用
        List<Params> result = new ArrayList<Params>();

        // 報告データ作成中フラグが自クラスで更新されているかを判定する為のフラグ
        boolean updateReportCreatorFlag = false;

        // 報告処理成否フラグ
        boolean resultFlag = false;

        // エラーフラグ
        boolean errorFlag = false;
        try
        {
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
            // 報告データ作成中チェック
            if (isReportData())
            {
                return null;
            }
            // 報告データ作成中フラグ更新
            if (!doReportStart())
            {
                return null;
            }

            // 報告データ更新を確定しフラグを成立
            doCommit(this.getClass());
            updateReportCreatorFlag = true;

            // 画面にて指定された件数分繰り返し
            for (Params p : ps)
            {

                boolean flag = false;

                // 選択されている場合のみ処理実行
                if (p.getBoolean(SELECT))
                {
                    // 報告クラスを生成
                    ReportDBDataCreator reportCreator = getReportClass(p.getString(CLASS_NAME), p.getString(DATA_TYPE));

                    // 統計情報の取得を行います。
                    reportCreator.statics();
                    try
                    {
                        // 一時ファイルのファイル数取得
                        int restCnt = reportCreator.existTempFilesCount();

                        // 件数分繰り返す
                        for (int i = 0; i < restCnt; i++)
                        {
                            // 一時ファイル存在チェック
                            if (reportCreator.isExistTempFile())
                            {
                                // 実績ファイルの送信
                                if (!reportCreator.sendReportFile())
                                {
                                    // ロギング 6006020=ファイルの入出力エラーが発生しました。{0}
                                    RmiMsgLogClient.write("6006020", this.getClass().getName());
                                }
                            }
                        }

                        // 実績報告データを作成します。
                        if (reportCreator.report())
                        {
                            // パッケージ単位でコミット
                            doCommit(this.getClass());

                            // メッセージをセットする。
                            setMessage(reportCreator.getMessage());

                            // 報告データを作成した場合のみ
                            if (reportCreator.isReport())
                            {
                                resultFlag = true;

                                // 実績ファイルの送信
                                if (!reportCreator.sendReportFile())
                                {
                                    // ロギング 6006020=ファイルの入出力エラーが発生しました。{0}
                                    RmiMsgLogClient.write("6006020", this.getClass().getName());
                                    // 実績ファイルの作成ミス時は、メッセージ再セット
                                    // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
                                    setMessage("6007031");
                                }
                                flag = true;
                                createExchangeHistory(ExchangeHistory.STATUS_NORMAL, p.getString(DATA_TYPE),
                                        reportCreator);
                            }
                            else
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            // パッケージ単位でロールバック
                            doRollBack(this.getClass());

                            // メッセージをセットする。
                            setMessage(reportCreator.getMessage());
                            errorFlag = true;
                            flag = false;
                        }
                    }
                    catch (IOException e)
                    {
                        // ロギング 6006020=ファイルの入出力エラーが発生しました。{0}
                        RmiMsgLogClient.write("6006020", this.getClass().getName());
                        errorFlag = true;
                    }

                    // 画面メッセージをセット
                    Params returnParam = new Params();
                    returnParam.set(DATA_TYPE, p.getString(DATA_TYPE));
                    returnParam.set(SELECT, p.getBoolean(SELECT));
                    returnParam.set(REPORT_DATA_TYPE, p.getString(REPORT_DATA_TYPE));
                    returnParam.set(LOG_FLAG, flag);
                    returnParam.set(MESSAGE,
                            MessageResource.getMessage(WmsMessageFormatter.format(Integer.parseInt(getMessage()))));
                    returnParam.set(CLASS_NAME, p.getString(CLASS_NAME));

                    result.add(returnParam);
                }
                else
                {
                    Params returnParam = new Params();
                    returnParam.set(DATA_TYPE, p.getString(DATA_TYPE));
                    returnParam.set(SELECT, p.getBoolean(SELECT));
                    returnParam.set(REPORT_DATA_TYPE, p.getString(REPORT_DATA_TYPE));
                    returnParam.set(LOG_FLAG, flag);
                    returnParam.set(CLASS_NAME, p.getString(CLASS_NAME));

                    result.add(returnParam);
                }
            }

            if (!errorFlag)
            {
                if (resultFlag)
                {
                    // 6001028=設定しました。詳細は下記メッセージを参照して下さい。
                    setMessage("6001028");
                }
                else
                {
                    // 対象データはありませんでした。
                    setMessage("6003011");
                }
            }
            else
            {
                // 6027009=予期しないエラーが発生しました。ログを参照してください。
                setMessage("6027009");
            }
            return result;
        }
        finally
        {
            if (errorFlag)
            {
                doRollBack(this.getClass());
            }
            // 報告データ作成中フラグが自クラスで更新されたものであるならば、
            // 報告データ作成中フラグを、0：停止中にする
            if (updateReportCreatorFlag)
            {
                if (!doReportEnd())
                {
                    // 6023013=データ報告中のため、処理できません。
                    setMessage("6023013");
                    return null;
                }
                doCommit(this.getClass());
            }
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * Returns the holder for the customer's database connection
     * 
     * @return customer's database connection
     */
    public Connection getCustomerConnection()
    {
        return _customerConn;
    }

    /**
     * Set the connection to the customer's database
     * 
     * @param connection Database Connection
     */
    public void setCustomerConnection(Connection connection)
    {
        _customerConn = connection;
    }
    
    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * ExchangeHistory（交換データ通信履歴）を作成します。
     * 
     * @throws DataExistsException 
     * @throws ReadWriteException
     */
    protected void createExchangeHistory(String status, String jobType, ReportDBDataCreator creator)
            throws DataExistsException,
                ReadWriteException
    {
        AbstractReportDBDataCreator abst = (AbstractReportDBDataCreator)creator;

        // 交換データ通信履歴作成
        ExchangeHistoryHandler exHandler = new ExchangeHistoryHandler(getConnection());
        ExchangeHistory ex = new ExchangeHistory();

        // 交換データ環境定義情報ハンドラの生成
        ExchangeEnvironmentHandler exHdl = new ExchangeEnvironmentHandler(getConnection());
        // 交換データ環境定義情報検索キーの生成
        ExchangeEnvironmentSearchKey skey = new ExchangeEnvironmentSearchKey();

        // 取得項目
        skey.setJobTypeCollect();
        skey.setDataTypeCollect();
        skey.setDataNameCollect();
        skey.setReportTypeCollect();

        // 検索条件
        skey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_SEND);
        skey.setDataType(ExchangeEnvironment.DATA_DB);
        skey.setJobType(jobType);
        
        // 検索を行い件数分繰り返す
        ExchangeEnvironment[] exEnvs = (ExchangeEnvironment[])exHdl.find(skey);
        
        ex.setStartDate(abst.getStartDate());
        ex.setJobType(exEnvs[0].getJobType());
        ex.setExchangeType(ExchangeHistory.EXCHANGE_TYPE_SEND);
        ex.setDataType(exEnvs[0].getDataType());
        ex.setDataName(exEnvs[0].getDataName());
        ex.setReportType(exEnvs[0].getReportType());
        String filename = abst.getResultFileName();
        if (filename != null && filename.length() > 40)
        {
            filename = new StringBuilder(filename).substring(0, 40);
        }
        ex.setFileName(filename);
        ex.setStatus(status);
        ex.setRegistPname(this.getClass().getSimpleName());
        ex.setLastUpdatePname(this.getClass().getSimpleName());

        exHandler.create(ex);
    }

    /**
     * 交換データ環境定義情報に定義されている報告処理クラスを生成し、<BR>
     * 報告クラスを返却します。<BR>
     * <BR>
     * @param className
     * @return
     * @throws CommonException
     */
    protected ReportDBDataCreator getReportClass(String className, String exchangeJob)
            throws CommonException
    {
        // コンストラクタクラス
        Constructor constructor = null;
        // メソッドクラス
        Method method = null;
        // インスタンス保持
        Object obj = null;

        try
        {
            // 文字列からクラスを生成
            Class<?> loaderClass = Class.forName(className);
            // コンストラクタを取得
            constructor = loaderClass.getConstructor(Connection.class, Connection.class, Class.class);
            // インスタンス生成
            obj = constructor.newInstance(getConnection(), getCustomerConnection(), this.getParent());
            // getFileCount()呼び出し
            method = obj.getClass().getMethod("getReportClass", new Class<?>[]{});

            // 結果を返却(件数取得のため返却値はIntegerとなる)
            return (ReportDBDataCreator)method.invoke(obj, new Object[]{});
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
