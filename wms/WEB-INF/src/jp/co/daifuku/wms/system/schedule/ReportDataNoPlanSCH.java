// $Id: ReportDataNoPlanSCH.java 7505 2010-03-12 06:18:11Z shibamoto $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.system.schedule.ReportDataNoPlanSCHParams.*;

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
import jp.co.daifuku.wms.base.common.AbstractReportDataCreator;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.ReportDataCreator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistoryHandler;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.ExchangeHistory;
import jp.co.daifuku.wms.stock.schedule.StockReportDataCreator;

/**
 * 予定外作業報告のスケジュール処理を行います。<BR>
 * <BR>
 * @version $Revision: 7505 $, $Date: 2010-03-12 15:18:11 +0900 (金, 12 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class ReportDataNoPlanSCH
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
    public ReportDataNoPlanSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
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
        skey.setDataType(ExchangeEnvironment.DATA_NO_PLAN);

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

            doCommit(this.getClass());
            updateReportCreatorFlag = true;

            for (Params p : ps)
            {
                boolean flag = false;

                if (p.getBoolean(SELECT))
                {
                    try
                    {
                        // 在庫以外の場合
                        if (!Parameter.DATA_TYPE_STOCK.equals(p.getString(DATA_TYPE)))
                        {
                            // 報告クラスを生成
                            ReportDataCreator reportCreator = getReportClass(p.getString(CLASS_NAME));

                            // 統計情報の取得を行います。
                            reportCreator.statics();
                            // 残ファイル数を取得します。
                            int restCnt = reportCreator.existTempFilesCount();

                            for (int i = 0; i < restCnt; i++)
                            {
                                // 一時ファイル存在チェック（送信ファイルパスのセット）
                                if (reportCreator.isExistTempFile())
                                {
                                    // 実績ファイルの作成
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

                                if (reportCreator.isReport())
                                {
                                    resultFlag = true;

                                    // 実績ファイルの作成
                                    if (!reportCreator.sendReportFile())
                                    {
                                        // ロギング 6006020=ファイルの入出力エラーが発生しました。{0}
                                        RmiMsgLogClient.write("6006020", this.getClass().getName());
                                        // 実績ファイルの作成ミス時は、メッセージ再セット
                                        // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
                                        setMessage("6007031");
                                    }
                                    flag = true;
                                    createExchangeHistory(ExchangeHistory.STATUS_NORMAL, p,
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
                        // 在庫の場合
                        else if (Parameter.DATA_TYPE_STOCK.equals(p.getString(DATA_TYPE)))
                        {
                            // 報告クラスを生成
                            StockReportDataCreator creater = (StockReportDataCreator)getReportClass(p.getString(CLASS_NAME));
                            // 統計情報の取得を行います。
                            creater.statics();
                            // 実績ファイルのディレクトリ、Prefix名をセットします。
                            creater.setResultReportFilePath();
                            // 残ファイル数を取得します。
                            int restdCnt = creater.existTempFilesCount();

                            creater.setStockExtention("");

                            for (int i = 0; i < restdCnt; i++)
                            {
                                // 一時ファイル存在チェック（送信ファイルパスのセット）
                                if (creater.isExistTempFile())
                                {
                                    // 実績ファイルの作成
                                    if (!creater.sendReportFile())
                                    {
                                        // ロギング 6006020=ファイルの入出力エラーが発生しました。{0}
                                        RmiMsgLogClient.write("6006020", this.getClass().getName());
                                    }
                                }
                            }
                            // レポートパラメータ作成
                            SystemInParameter inParam = new SystemInParameter(getWmsUserInfo());
                            inParam.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
                            inParam.setAreaNo(WmsParam.ALL_AREA_NO);
                            // 在庫報告データを作成します。
                            if (creater.report(inParam))
                            {
                                // パッケージ単位でコミット
                                doCommit(this.getClass());
                                // メッセージをセットする。
                                setMessage(creater.getMessage());

                                if (creater.isReport())
                                {
                                    resultFlag = true;

                                    // 実績ファイルの作成
                                    if (!creater.sendReportFile())
                                    {
                                        // ロギング 6006020=ファイルの入出力エラーが発生しました。{0}
                                        RmiMsgLogClient.write("6006020", this.getClass().getName());
                                        // 実績ファイルの作成ミス時は、メッセージ再セット
                                        // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
                                        setMessage("6007031");
                                    }
                                    flag = true;
                                    createExchangeHistory(ExchangeHistory.STATUS_NORMAL, p,
                                            creater);
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
                                setMessage(creater.getMessage());
                                errorFlag = true;
                                flag = false;
                            }
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
                doReportEnd();
                doCommit(this.getClass());
            }
        }
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
     * ExchangeHistory（交換データ通信履歴）を作成します。<BR>
     * 
     * @throws DataExistsException 
     * @throws ReadWriteException
     */
    protected void createExchangeHistory(String status, Params p, ReportDataCreator creator)
            throws DataExistsException,
                ReadWriteException
    {
        AbstractReportDataCreator abst = (AbstractReportDataCreator)creator;

        // 交換データ通信履歴作成
        ExchangeHistoryHandler exHandler = new ExchangeHistoryHandler(getConnection());
        ExchangeHistory ex = new ExchangeHistory();

        ex.setStartDate(abst.getStartDate());
        ex.setJobType(p.getString(DATA_TYPE));
        ex.setDataName(p.getString(REPORT_DATA_TYPE));
        ex.setDataType(ExchangeEnvironment.DATA_NO_PLAN);
        ex.setExchangeType(ExchangeHistory.EXCHANGE_TYPE_SEND);
        String filename = abst.getResultFileName();
        if (filename != null && filename.length() > 40) filename = new StringBuilder(filename).substring(0, 40);
        ex.setFileName(filename);
        ex.setStatus(status);
        ex.setRegistPname(this.getClass().getSimpleName());
        ex.setLastUpdatePname(this.getClass().getSimpleName());

        exHandler.create(ex);
    }

    /**
     * 交換データ環境定義情報に定義されている報告処理クラスを生成し、<BR>
     * 報告クラスを返却します。(在庫以外用)<BR>
     * <BR>
     * @param className
     * @return
     * @throws CommonException
     */
    protected ReportDataCreator getReportClass(String className)
            throws CommonException
    {
        // コンストラクタクラス
        Constructor constructor = null;
        // メソッドクラス
        Method method = null;
        
        try
        {
            // 文字列からクラスを生成
            Class<?> loaderClass = Class.forName(className);
            // コンストラクタを取得
            constructor = loaderClass.getConstructor(Connection.class);
            // インスタンス生成
            Object obj = constructor.newInstance(getConnection());
            // getFileCount()呼び出し
            method = obj.getClass().getMethod("getReportClass", new Class<?>[]{});

            // 結果を返却(件数取得のため返却値はIntegerとなる)
            return (ReportDataCreator)method.invoke(obj, new Object[]{});
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
