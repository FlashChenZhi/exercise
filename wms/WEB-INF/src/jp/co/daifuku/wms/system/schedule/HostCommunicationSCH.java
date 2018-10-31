// $Id: Sch_ja.java 117 2008-10-06 11:00:54Z admin $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.system.schedule.HostCommunicationSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeEnvironmentSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.ExchangeHistorySearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.ExchangeHistory;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.util.timekeeper.InstructionFileHandler;
import jp.co.daifuku.wms.base.util.timekeeper.JobParam;
import jp.co.daifuku.wms.base.util.timekeeper.ScheduleFileHandler;

/**
 * 上位通信設定のスケジュール処理を行います。
 *
 * @version $Revision: 117 $, $Date:: 2008-10-06 20:00:54 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class HostCommunicationSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 時間間隔フィールド分割文字列<br>
     */
    private static final String SPLIT_FIELD = " |\t";

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
    public HostCommunicationSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // 返却データ
        List<Params> result = new ArrayList<Params>();

        // システム定義情報
        WarenaviSystemController sysController = new WarenaviSystemController(getConnection(), getClass());

        // 交換データ環境定義情報
        ExchangeEnvironmentHandler exEnvHandler = new ExchangeEnvironmentHandler(getConnection());
        ExchangeEnvironmentSearchKey exEnvKey = new ExchangeEnvironmentSearchKey();

        // 条件セット
        exEnvKey.setJobTypeCollect();
        exEnvKey.setExchangeTypeCollect();
        exEnvKey.setTimeKeeperPathCollect();
        exEnvKey.setAutoPrintErrorListCollect();
        exEnvKey.setClassNameCollect();
        exEnvKey.setJobTypeOrder(true);
        exEnvKey.setExchangeTypeOrder(false);

        // 交換データ環境定義情報取得
        ExchangeEnvironment[] exEnv = (ExchangeEnvironment[])exEnvHandler.find(exEnvKey);

        if (ArrayUtil.isEmpty(exEnv))
        {
            // 6003011=対象データはありませんでした。
            setMessage(WmsMessageFormatter.format(6003011));
            return result;
        }

        // ScheduleFileHandler
        ScheduleFileHandler fileHandler = null;
        JobParam[] jobParams = null;
        String tempPath = null;

        WarenaviSystemController wmsCon = new WarenaviSystemController(getConnection(), this.getClass());

        // 返却データセット
        int rowIndex = 1;
        for (ExchangeEnvironment ent : exEnv)
        {
            Params param = new Params();

            // ホスト接続状態
            param.set(HOST_DISABLED, sysController.isHostEnabled());
            // データ名称
            param.set(
                    DATA_NAME,
                    SystemDefine.EXCHANGE_TYPE_SEND.equals(ent.getExchangeType()) ? wmsCon.isFaDaEnabled() ? DisplayResource.getFaReportData(ent.getJobType())
                                                                                                          : DisplayResource.getReportData(ent.getJobType())
                                                                                 : wmsCon.isFaDaEnabled() ? DisplayResource.getFaLoadDataType(ent.getJobType())
                                                                                                         : DisplayResource.getLoadDataType(ent.getJobType()));
            // 定義有無(初期化)
            param.set(IS_DEFINED, false);

            // リスト発行
            param.set(ISSUE_LIST, SystemDefine.PRINT_OUTPUT.equals(ent.getAutoPrintErrorList()) ? true
                                                                                               : false);
            // 状態
            param.set(ERROR_STATUS, getStatus(ent.getJobType(), ent.getExchangeType()));
            // データ区分
            param.set(JOB_TYPE, ent.getJobType());
            // 送受信区分
            param.set(EXCHANGE_TYPE, ent.getExchangeType());
            // クラス名
            param.set(CLASS_NAME, ent.getClassName());

            if (!StringUtil.isBlank(ent.getTimeKeeperPath()))
            {
                try
                {
                    // TimeKeeperCron読込
                    if (tempPath == null || !ent.getTimeKeeperPath().equals(tempPath))
                    {
                        fileHandler = new ScheduleFileHandler(ent.getTimeKeeperPath());
                        jobParams = fileHandler.getSchedules();
                        tempPath = ent.getTimeKeeperPath();
                    }

                    // 周期/時間指定
                    for (JobParam job : jobParams)
                    {
                        if (job.getClassName().equals(ent.getClassName()))
                        {
                            // 定義有無
                            param.set(IS_DEFINED, true);

                            if (job.getInterval().isSimpleInterval())
                            {
                                // 周期
                                if (job.getInterval().getSimpleIntervalSec() > 0)
                                {
                                    param.set(CYCLE, (job.getInterval().getSimpleIntervalSec() / 60)
                                            + (job.getInterval().getSimpleIntervalSec() % 60 == 0 ? 0
                                                                                                 : 1));
                                }
                            }
                            else
                            {
                                // 時間指定
                                String intervalStr = job.getInterval().getTimingString();
                                String[] fields = intervalStr.split(SPLIT_FIELD);
                                param.set(SPECIFIED_TIME, WmsFormatter.toDispTime(toTime(fields[1], fields[0]),
                                        getLocale()));
                            }
                            break;
                        }
                    }
                }
                catch (RuntimeException e)
                {
                    //                    // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
                    //                    setMessage(WmsMessageFormatter.format(6007031));
                    //                    if (getErrorRowIndex() <= 0)
                    //                    {
                    //                        setErrorRowIndex(rowIndex);
                    //                        setNgCellRow(rowIndex);
                    //                    }
                    // 定義読み込みエラー時は破棄する


                }
            }

            result.add(param);
            rowIndex++;
        }

        if (getErrorRowIndex() <= 0)
        {
            // 6001013=表示しました。
            setMessage(WmsMessageFormatter.format(6001013));
        }
        return result;
    }

    /**
     * スケジュール前にチェック判定を行います。
     * <CODE>startParams</CODE>で指定されたパラメータにセットされた内容に従い、<BR>
     * チェック処理を行います。チェック処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
     * チェック処理が成功した場合はtrueを返します。<BR>
     * 条件エラーなどでチェック処理が失敗した場合はfalseを返します。<BR>
     * 詳しい動作はクラス説明の項を参照してください。<BR>
     * @param startParams スケジュールパラメータ
     * @throws CommonException 例外が発生した場合に通知されます。
     * @return チェック処理が正常した場合はtrue、チェック処理が失敗した場合はfalseを返します。
     */
    public boolean check(ScheduleParams startParams)
            throws CommonException
    {
        return true;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        if (ProcessFlag.REGIST.equals(ps[0].getProcessFlag()))
        {
            // ホスト通信状態更新
            updateHostCommunicationStatus(ps[0]);
            // 6001006 = 設定しました。
            setMessage(WmsMessageFormatter.format(6001006));
            return true;
        }

        // ScheduleFileHandler
        ScheduleFileHandler fileHandler = null;
        JobParam[] jobParams = null;
        String tempPath = null;

        for (ScheduleParams param : ps)
        {
            // 入力内容、他端末チェック(更新先がファイルとなる為、一旦全てチェックを行う)
            if (!check(param, jobParams, fileHandler, tempPath))
            {
                return false;
            }
        }

        for (ScheduleParams param : ps)
        {
            // 交換データ定義情報更新
            if (!updateExchangeEnvironment(param, jobParams, fileHandler, tempPath))
            {
                return false;
            }
        }

        // 6001006 = 設定しました。
        setMessage(WmsMessageFormatter.format(6001006));
        return true;
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
     * 交換データ通信履歴の状態を取得します。
     * @param jobType データ区分
     * @param exchangeType 送受信区分
     * @return 状態
     */
    protected String getStatus(String jobType, String exchangeType)
            throws ReadWriteException
    {
        // ハンドラインスタンス生成
        ExchangeHistoryHandler exHandler = new ExchangeHistoryHandler(getConnection());
        ExchangeHistorySearchKey exKey = new ExchangeHistorySearchKey();

        // 検索条件セット
        exKey.setStartDateCollect("MAX");

        // データ区分
        exKey.setJobType(jobType);
        // 送受信区分
        exKey.setExchangeType(exchangeType);
        // 状態
        exKey.setStatus(SystemDefine.EXCHANGE_STATUS_NORMAL, "!=");

        // 取得
        ExchangeHistory[] exHistory = (ExchangeHistory[])exHandler.find(exKey);

        if (ArrayUtil.isEmpty(exHistory) || (exHistory != null && StringUtil.isBlank(exHistory[0].getStartDate())))
        {
            return "";
        }

        return SimpleFormat.format(DisplayText.getText("LBL-W6007"), new String[] {
            WmsFormatter.toDispDateTime(WmsFormatter.toParamDateTime(exHistory[0].getStartDate()), this.getLocale())
        });
    }

    /**
     * ホスト通信状態を更新します。
     * @param p 更新データ条件を持つ<CODE>ScheduleParams</CODE><BR>
     */
    protected void updateHostCommunicationStatus(ScheduleParams p)
            throws ReadWriteException,
                ScheduleException,
                NotFoundException
    {
        WarenaviSystemController wareNaviSystem = new WarenaviSystemController(getConnection(), this.getClass());

        if (!p.getBoolean(HOST_DISABLED) && !wareNaviSystem.isHostEnabled())
        {
            wareNaviSystem.updateHostEnabled(true);
        }
        else if (p.getBoolean(HOST_DISABLED) && wareNaviSystem.isHostEnabled())
        {
            wareNaviSystem.updateHostEnabled(false);
        }
    }

    /**
     * 入力内容のチェックを行います。
     * @param p 更新データ条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @param jobParams 自動処理設定内容保持クラス
     * @param fileHandler ScheduleFileHandlerクラス
     * @param tempPath TimeKeeper.iniファイル保持パス
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    protected boolean check(ScheduleParams p, JobParam[] jobParams, ScheduleFileHandler fileHandler, String tempPath)
            throws ReadWriteException
    {
        try
        {
            // 型変換
            p.set(ISSUE_LIST, p.getBoolean(ISSUE_LIST) ? SystemDefine.PRINT_OUTPUT
                                                      : SystemDefine.PRINT_NOOUTPUT);
            p.set(HIDDEN_ISSUE_LIST, p.getBoolean(HIDDEN_ISSUE_LIST) ? SystemDefine.PRINT_OUTPUT
                                                                    : SystemDefine.PRINT_NOOUTPUT);

            // 周期、指定時刻未入力
            if (p.getBoolean(IS_DEFINED)
                    && (StringUtil.isBlank(p.get(CYCLE)) && StringUtil.isBlank(p.get(SPECIFIED_TIME))))
            {
                // 6023263　=　No.{0} 周期か指定時刻のどちらかを入力して下さい。
                setErrorRowIndex(p.getRowIndex());
                setMessage(WmsMessageFormatter.format(6023263, p.getRowIndex()));
                setNgCellRow(p.getRowIndex());
                return false;
            }

            // 周期、指定時刻入力チェック
            if (p.getBoolean(IS_DEFINED)
                    && (!StringUtil.isBlank(p.get(CYCLE)) && !StringUtil.isBlank(p.get(SPECIFIED_TIME))))
            {
                // 6023264 = No.{0} 周期と指定時刻の両方を指定することは出来ません。
                setErrorRowIndex(p.getRowIndex());
                setMessage(WmsMessageFormatter.format(6023264, p.getRowIndex()));
                setNgCellRow(p.getRowIndex());
                return false;
            }

            // 交換データ環境定義情報
            ExchangeEnvironmentHandler exEnvHandler = new ExchangeEnvironmentHandler(getConnection());
            ExchangeEnvironmentSearchKey exEnvKey = new ExchangeEnvironmentSearchKey();

            // データ区分
            exEnvKey.setJobType(p.getString(JOB_TYPE));
            // 送受信区分
            exEnvKey.setExchangeType(p.getString(EXCHANGE_TYPE));

            // 交換データ環境定義情報取得
            ExchangeEnvironment exEnv = (ExchangeEnvironment)exEnvHandler.findPrimary(exEnvKey);

            // 他端末チェック
            if (exEnv == null)
            {
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setErrorRowIndex(p.getRowIndex());
                setMessage(WmsMessageFormatter.format(6023015, p.getRowIndex()));
                setNgCellRow(p.getRowIndex());
                return false;
            }
            if (!exEnv.getAutoPrintErrorList().equals(p.getString(HIDDEN_ISSUE_LIST)))
            {
                // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                setErrorRowIndex(p.getRowIndex());
                setMessage(WmsMessageFormatter.format(6023015, p.getRowIndex()));
                setNgCellRow(p.getRowIndex());
                return false;
            }

            // TimeKeeperPathをセット
            p.set(TIMEKEEPER_PATH, exEnv.getTimeKeeperPath());

            // TimeKeeperCron読込
            if (p.getBoolean(IS_DEFINED)
                    && (tempPath == null || (!StringUtil.isBlank(exEnv.getTimeKeeperPath()) && !exEnv.getTimeKeeperPath().equals(
                            tempPath))))
            {
                fileHandler = new ScheduleFileHandler(exEnv.getTimeKeeperPath());
                jobParams = fileHandler.getSchedules();
                tempPath = exEnv.getTimeKeeperPath();
            }

            if (p.getBoolean(IS_DEFINED))
            {
                for (JobParam job : jobParams)
                {
                    if (job.getClassName().equals(p.get(CLASS_NAME)))
                    {
                        // 周期
                        if (job.getInterval().isSimpleInterval()
                                && job.getInterval().getSimpleIntervalSec() / 60
                                        + (job.getInterval().getSimpleIntervalSec() % 60 == 0 ? 0
                                                                                             : 1) != p.getInt(HIDDEN_CYCLE))
                        {
                            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                            setErrorRowIndex(p.getRowIndex());
                            setMessage(WmsMessageFormatter.format(6023015, p.getRowIndex()));
                            setNgCellRow(p.getRowIndex());
                            return false;
                        }

                        // 指定時間
                        if (!job.getInterval().isSimpleInterval())
                        {
                            String intervalStr = job.getInterval().getTimingString();
                            String[] fields = intervalStr.split(SPLIT_FIELD);
                            String time = null;
                            try
                            {
                                time = WmsFormatter.toDispTime(toTime(fields[1], fields[0]), getLocale());


                                if (!time.equals(p.get(HIDDEN_TIME)))
                                {
                                    // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                                    setErrorRowIndex(p.getRowIndex());
                                    setMessage(WmsMessageFormatter.format(6023015, p.getRowIndex()));
                                    setNgCellRow(p.getRowIndex());
                                    return false;
                                }
                            }
                            catch (Exception e)
                            {
                                // 定義エラーはスルーする
                            }
                        }
                        break;
                    }
                }
            }
            return true;
        }
        catch (NoPrimaryException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setErrorRowIndex(p.getRowIndex());
            setMessage(WmsMessageFormatter.format(6023015, p.getRowIndex()));
            setNgCellRow(p.getRowIndex());
            return false;
        }
        catch (RuntimeException e)
        {
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            setErrorRowIndex(p.getRowIndex());
            setNgCellRow(p.getRowIndex());
            return false;
        }
    }

    /**
     * 交換データ環境定義情報を更新します。
     * @param p 更新データ条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @param jobParams 自動処理設定内容保持クラス
     * @param fileHandler ScheduleFileHandlerクラス
     * @param tempPath TimeKeeper.iniファイル保持パス
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    protected boolean updateExchangeEnvironment(ScheduleParams p, JobParam[] jobParams,
            ScheduleFileHandler fileHandler, String tempPath)
            throws ReadWriteException
    {
        try
        {
            // TimeKeeperCron読込
            if (p.getBoolean(IS_DEFINED)
                    && (tempPath == null || (!StringUtil.isBlank(p.getString(TIMEKEEPER_PATH)) && !p.getString(
                            TIMEKEEPER_PATH).equals(tempPath))))
            {
                fileHandler = new ScheduleFileHandler(p.getString(TIMEKEEPER_PATH));
                jobParams = fileHandler.getSchedules();
                tempPath = p.getString(TIMEKEEPER_PATH);
            }

            if (p.getBoolean(IS_DEFINED))
            {
                // JobParamインデックス
                int jobIndex = 0;
                // 自動処理設定内容保持クラス
                JobParam jobParam = null;

                for (JobParam job : jobParams)
                {
                    if (job.getClassName().equals(p.get(CLASS_NAME)))
                    {
                        jobParam = job;
                        break;
                    }
                    jobIndex++;
                }

                // JobParam更新
                StringBuffer intervalStr = new StringBuffer("");
                if (!StringUtil.isBlank(p.get(CYCLE)))
                {
                    intervalStr.append(p.getInt(CYCLE) * 60);
                }
                else
                {
                    String time = WmsFormatter.toParamTime(p.getDate(SPECIFIED_TIME));
                    intervalStr.append(time.substring(2, 4) + " ");
                    intervalStr.append(time.subSequence(0, 2) + " ");
                    String day = "* * *";

                    // TimeKeeper設定ツールにて設定された内容を保持
                    if (jobParam != null && !jobParam.getInterval().isSimpleInterval())
                    {
                        String timeStr = jobParam.getInterval().getTimingString();
                        String[] fields = timeStr.split(SPLIT_FIELD);
                        day = fields[2] + " " + fields[3] + " " + fields[4];
                    }
                    intervalStr.append(day);
                }

                // TimeKeeperCron更新
                int length = jobParams.length <= jobIndex ? jobIndex + 1
                                                         : jobParams.length;
                JobParam[] updateJobs = new JobParam[length];
                System.arraycopy(jobParams, 0, updateJobs, 0, jobParams.length);
                updateJobs[jobIndex] = new JobParam(intervalStr.toString(), p.getString(CLASS_NAME), null, true);
                fileHandler.replaceAll(updateJobs);

                // TimeKeeperCron再読み込み
                InstructionFileHandler instHandler = new InstructionFileHandler(p.getString(TIMEKEEPER_PATH));
                instHandler.putReload();
            }

            // 交換データ環境定義情報
            ExchangeEnvironmentHandler exEnvHandler = new ExchangeEnvironmentHandler(getConnection());
            ExchangeEnvironmentAlterKey exEnvAKey = new ExchangeEnvironmentAlterKey();

            // データ区分
            exEnvAKey.setJobType(p.getString(JOB_TYPE));
            // 送受信区分
            exEnvAKey.setExchangeType(p.getString(EXCHANGE_TYPE));
            // エラーリスト自動発行
            exEnvAKey.updateAutoPrintErrorList(p.getString(ISSUE_LIST));

            // 交換データ環境定義情報更新
            exEnvHandler.modify(exEnvAKey);

            return true;
        }
        catch (NotFoundException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setErrorRowIndex(p.getRowIndex());
            setMessage(WmsMessageFormatter.format(6023015, p.getRowIndex()));
            setNgCellRow(p.getRowIndex());
            return false;
        }
        catch (RuntimeException e)
        {
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            setErrorRowIndex(p.getRowIndex());
            setNgCellRow(p.getRowIndex());
            return false;
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * HHmmss文字列に変換します。
     * @param hour 時
     * @param min 分
     * @return HHmmss文字列
     */
    private String toTime(String hour, String min)
    {
        if (hour.indexOf("*") > -1)
        {
            hour = hour.replaceAll("\\*", "0");
        }

        if (min.indexOf("*") > -1)
        {
            min = min.replaceAll("\\*", "0");
        }

        if (hour.length() < 2)
        {
            hour = "0" + hour;
        }

        if (min.length() < 2)
        {
            min = "0" + min;
        }
        return hour + min + "00";
    }

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
