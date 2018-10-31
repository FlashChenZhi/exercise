// $Id: MasterDataReportSCH.java 7247 2010-02-26 05:46:27Z okayama $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.master.schedule.MasterDataReportSCHParams.*;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractReportDataCreator;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.ReportDataCreator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.ExchangeHistory;
import jp.co.daifuku.wms.base.entity.PCTSystem;

/**
 * マスタデータ報告のスケジュール処理を行います。
 *
 * @version $Revision: 7247 $, $Date:: 2010-02-26 14:46:27 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class MasterDataReportSCH
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
    public MasterDataReportSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        skey.setDataIdCollect();
        skey.setClassNameCollect();

        // 検索条件
        skey.setExchangeType(ExchangeEnvironment.EXCHANGE_TYPE_SEND);
        skey.setDataType(ExchangeEnvironment.DATA_MASTER);

        // 検索を行い件数分繰り返す
        ExchangeEnvironment[] exEnvs = (ExchangeEnvironment[])exHdl.find(skey);
        for (ExchangeEnvironment exEnv : exEnvs)
        {
            param = new Params();

            // データ区分
            param.set(DATA_TYPE, exEnv.getJobType());
            param.set(REPORT_DATA_TYPE, exEnv.getDataName());
            param.set(REPORT_FILE_NAME, exEnv.getDataId());
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
        Params returnParam = null;

        // 報告データ作成中フラグが自クラスで更新されているかを判定する為のフラグ
        boolean updateReportCreatorFlag = false;
        // 報告処理成否フラグ
        boolean resultFlag = false;
        // エラーフラグ
        boolean errorFlag = false;

        try
        {
            // 日次更新中のチェックをします。
            if (!canStart())
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

            // PCT商品マスタチェック
            if (!itemload())
            {
                return null;
            }

            doCommit(this.getClass());
            updateReportCreatorFlag = true;

            for (Params p : ps)
            {
                // 選択されてる場合のみ
                if (p.getBoolean(SELECT))
                {
                    try
                    {

                        // 報告クラスの生成
                        MasterReportDateCreate creater = getInstance(p.getString(CLASS_NAME));

                        // 実績ファイルのディレクトリ、Prefix名をセットします。
                        creater.setResultReportFilePath();
                        // 一時ファイル数取得チェック
                        int restCnt = creater.existTempFilesCount();
                        // 一時ファイルチェック処理結果
                        boolean tempFileCheck = true;
                        for (int i = 0; i < restCnt; i++)
                        {
                            if (!creater.isExistTempFile())
                            {
                                break;
                            }
                            // 実績ファイルの作成
                            if (!creater.sendReportFile())
                            {
                                // 6006020=ファイルの入出力エラーが発生しました。{0}
                                RmiMsgLogClient.write("6006020", this.getClass().getName());

                                returnParam = new Params();
                                // データ区分
                                returnParam.set(DATA_TYPE, p.getString(DATA_TYPE));
                                // パッケージ選択
                                returnParam.set(SELECT, p.getString(SELECT));
                                // データ区分
                                returnParam.set(REPORT_DATA_TYPE, p.getString(REPORT_DATA_TYPE));
                                // 報告ファイル名
                                returnParam.set(REPORT_FILE_NAME, p.getString(REPORT_FILE_NAME));
                                // メッセージ
                                // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
                                returnParam.set(
                                        MESSAGE,
                                        MessageResource.getMessage(WmsMessageFormatter.format(Integer.parseInt("6007031"))));

                                result.add(returnParam);

                                tempFileCheck = false;
                                errorFlag = true;
                                break;
                            }
                        }
                        if (!tempFileCheck)
                        {
                            continue;
                        }

                        // 報告データの作成
                        if (creater.report())
                        {
                            //コミット
                            doCommit(this.getClass());
                            // 実績ファイルの作成
                            if (creater.isReport())
                            {
                                resultFlag = true;
                                // 実績ファイルの送信
                                if (!creater.sendReportFile())
                                {
                                    // 6006020=ファイルの入出力エラーが発生しました。{0}
                                    RmiMsgLogClient.write("6006020", this.getClass().getName());

                                    returnParam = new Params();
                                    // データ区分
                                    returnParam.set(DATA_TYPE, p.getString(DATA_TYPE));
                                    // パッケージ選択
                                    returnParam.set(SELECT, p.getString(SELECT));
                                    // データ区分
                                    returnParam.set(REPORT_DATA_TYPE, p.getString(REPORT_DATA_TYPE));
                                    // 報告ファイル名
                                    returnParam.set(REPORT_FILE_NAME, p.getString(REPORT_FILE_NAME));
                                    // クラス名
                                    returnParam.set(CLASS_NAME, p.getString(CLASS_NAME));
                                    // メッセージ
                                    // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
                                    returnParam.set(
                                            MESSAGE,
                                            MessageResource.getMessage(WmsMessageFormatter.format(Integer.parseInt("6007031"))));

                                    result.add(returnParam);

                                    errorFlag = true;
                                    continue;
                                }
                                createExchangeHistory(ExchangeHistory.STATUS_NORMAL, p, creater);
                            }
                        }
                        else
                        {
                            errorFlag = true;
                        }
                        //メッセジをセットする。
                        setMessage(creater.getMessage());
                    }
                    catch (IOException e)
                    {
                        // 6027009=予期しないエラーが発生しました。ログを参照してください。
                        setMessage("6027009");
                        return null;
                    }
                }

                returnParam = new Params();
                // データ区分
                returnParam.set(DATA_TYPE, p.getString(DATA_TYPE));
                // パッケージ選択
                returnParam.set(SELECT, p.getString(SELECT));
                // データ区分
                returnParam.set(REPORT_DATA_TYPE, p.getString(REPORT_DATA_TYPE));
                // 報告ファイル名
                returnParam.set(REPORT_FILE_NAME, p.getString(REPORT_FILE_NAME));
                // クラス名
                returnParam.set(CLASS_NAME, p.getString(CLASS_NAME));
                // 選択されてる場合のみ
                if (p.getBoolean(SELECT))
                {
                    // メッセージ
                    returnParam.set(MESSAGE,
                            MessageResource.getMessage(WmsMessageFormatter.format(Integer.parseInt(getMessage()))));
                }

                result.add(returnParam);
            }

            if (!errorFlag)
            {
                if (resultFlag)
                {
                    // 6001009=データの書き込みは正常に終了しました。
                    setMessage("6001009");
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
     * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
     * PCTシステム情報を検索し、商品マスタ取込みフラグを確認します。
     * 
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     * @throws NoPrimaryException NoPrimaryException 一意項目が重複している場合にスローされます。
     */
    protected boolean itemload()
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException
    {
        // PCTシステム情報ハンドラ類インスタンス生成
        PCTSystemHandler wHandler = new PCTSystemHandler(getConnection());
        PCTSystemSearchKey wSKey = new PCTSystemSearchKey();
        PCTSystem pctSystem = (PCTSystem)wHandler.findPrimary(wSKey);

        if (pctSystem != null)
        {
            if (PCTSystem.PCTMASTER_LOAD_FLAG_LOAD.equals(pctSystem.getPctmasterLoadFlag()))
            {
                // 6024068=ロード処理中のため処理できません。
                setMessage(WmsMessageFormatter.format(6024068));
                return false;
            }
        }
        return true;
    }

    /**
     * ExchangeHistory（交換データ通信履歴）を作成します。
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
        ex.setDataType(ExchangeEnvironment.DATA_MASTER);
        ex.setDataName(p.getString(REPORT_FILE_NAME));
        ex.setExchangeType(ExchangeHistory.EXCHANGE_TYPE_SEND);
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
    protected MasterReportDateCreate getInstance(String className)
            throws CommonException
    {
        // コンストラクタクラス
        Constructor constructor = null;

        try
        {
            // 文字列からクラスを生成
            Class<?> loaderClass = Class.forName(className);
            // コンストラクタを取得
            constructor = loaderClass.getConstructor(Connection.class);
            // インスタンス生成
            Object obj = constructor.newInstance(getConnection());

            // 結果を返却(件数取得のため返却値はIntegerとなる)
            return (MasterReportDateCreate)obj;
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
