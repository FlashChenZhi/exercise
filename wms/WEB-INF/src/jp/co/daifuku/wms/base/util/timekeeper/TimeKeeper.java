// $Id: TimeKeeper.java 8083 2015-02-16 07:38:16Z okamura $
package jp.co.daifuku.wms.base.util.timekeeper;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is subject to
 * license terms.
 */
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.wms.base.common.WmsParam;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * 起動時に設定ファイルを読み込み、デーモン化して設定ファイルの指示に従って
 * インターフェイス<code>ScheduleJob</code>を実装したクラスを実行します。<br>
 *
 * @version $Revision: 8083 $, $Date: 2015-02-16 16:38:16 +0900 (月, 16 2 2015) $
 * @author 清水　正人
 * @author Last commit: $Author: okamura $
 */

public class TimeKeeper
        extends Thread
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /** 自動処理のスリープ時間をミリ秒で指定します。(デフォルト値) */
    private long _defSleepTime = 200;

    /**
     * 自動処理の指示ファイルチェック間隔を回数で指定します。(デフォルト値)<br>
     * <code>SLEEP_TIME</code> × <code>CHECK_COUNT</code> 間隔で 指示ファイルをチェックします。
     */
    private int _defCheckCount = 50;

    private static ScheduleFileHandler $schedules = new ScheduleFileHandler();

    private static InstructionFileHandler $instruction = new InstructionFileHandler();

    // private TKExecuteLog _log = null;

    private boolean _terminate = false;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 自動処理は、コンストラクタでデーモン化します。
     */
    private TimeKeeper()
    {
        setDaemon(true);
        prepare();
        start();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * インスタンスを生成し、スレッドの終了を待ち合わせます。
     *
     * @param args 使用していません<br>
     * @throws InterruptedException<br>
     */
    public static void main(final String[] args)
            throws InterruptedException
    {
        // 2015/02/16 Y.Okamura start TimeKeeper用のLOG4j定義追加
        // for eWareNavi
        DOMConfigurator.configure(WmsParam.TK_LOG4J_FILE_PATH);
        // 2015/02/16 Y.Okamura end TimeKeeper用のLOG4j定義追加

        final TimeKeeper tk = new TimeKeeper();
        tk.join();
    }

    /**
     * 設定ファイルの内容に従って処理時刻を監視し
     * 指定時間に到達していれば、該当する処理を実行します。
     */
    @Override
    public void run()
    {
        // check count limit
        final int checkLimit = _defCheckCount;
        // check counter
        int checkCount = 0;

        // 開始メッセージを出力します。
        // 6020047=自動処理を起動します。
        logWrite(6020047, null);
        //SAMRmiMsgLogClient.write(6000300, getClass().getName(), "") ; // start message

        boolean isExecuted = false;
        while (!isTerminate())
        {
            try
            {
                // 自動処理登録されているクラスで、処理時間に到達している
                // ものがあれば、実行します。
                isExecuted = schedule();

                // 規定時間間隔で指示ファイルのチェックを行います。
                checkCount = ++checkCount % checkLimit;
                if (0 == checkCount)
                {
                    // checkInstructionは、終了すべきとき trueを返します
                    if (checkInstruction())
                    {
                        terminate();
                    }
                }

                if (!isExecuted)
                {
                    // 実行ジョブがなければ一定時間、スレッドを停止します。
                    // 実行した後は、すぐに次のジョブをチェックします
                    sleepForTime();
                }
            }
            catch (RuntimeException e)
            {
                e.printStackTrace();
                // 6006021 = 内部エラーが発生しました。{0}
                RmiMsgLogClient.write(new TraceHandler(6006021, e), getClass().getName());
            }
        }

        // 6030021 = Threadを停止しました。
        //SAMRmiMsgLogClient.write(6030021, getClass().getName()) ;
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 本処理を終了させます。<br>
     */
    private void terminate()
    {
        // 終了メッセージを出力します。
        // 自動処理を終了します。
        logWrite(6020048, null);

        // 終了要求を設定します。
        _terminate = true;
    }

    /**
     * 終了指示が行われているかチェックします。<br>
     *
     * @return 終了指示が行われていれば<code>true</code>を返します。<br>
     */
    private boolean isTerminate()
    {
        return _terminate;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 初期化をおこないます。
     */
    private void prepare()
    {
        // Sleep時間とチェック回数を初期化します。
        try
        {
            final IniFileOperator operator = TimeKeeperDefines.getTKIniOperator();
            _defSleepTime = Long.valueOf(operator.get("SLEEP_TIME")).longValue();
            _defCheckCount = Integer.valueOf(operator.get("CHECK_COUNT")).intValue();
        }
        catch (ReadWriteException e)
        {
            // 何もしません。(デフォルト値で動作します。)
        }
        StringBuilder msg = new StringBuilder("start Timekeeper  sleep:");
        msg.append(_defSleepTime);
        msg.append("ms count:");
        msg.append(_defCheckCount);
        msg.append("times");
        System.out.println(msg);

        // 実行ログクラスをインスタンス化します。
        // _log = new TKExecuteLog();

        // 指示ファイルを初期化します。
        $instruction.resetInstruction();

        // 登録されている実行クラスをインスタンス化します。
        // loadJobs();
    }

    /**
     * 自動処理登録されているクラスついてインスタンス化します。<br>
     */
    private void loadJobs()
    {
        final JobParam[] jobs = $schedules.getSchedules();
        for (final JobParam job : jobs)
        {
            final ScheduleJob schedule = newInstance(job);
            job.setScheduleJob(schedule);
        }
    }

    /**
     * 指定された実行パラメータの実行ログを出力します。
     * @param param 実行パラメータ
     */
    private void writeExecLog(JobParam param)
    {
        /* 実行ログは、自動処理側で出力するものとします。
        final JobParam[] jobs = {
            param,
        };
        _log.write(jobs);
        */
    }

    /**
     * 自動処理登録されているクラスの中で、
     * 実行時刻に到達しているものがあれば 実行します。<br>
     * @return 実行したものがあれば true
     */
    private boolean schedule()
    {
        boolean isExecuted = false;
        final JobParam[] jobs = $schedules.getSchedules();

        // sort job (old executed checks first)
        Comparator<JobParam> jobComparator = new Comparator<JobParam>()
        {
            public int compare(JobParam o1, JobParam o2)
            {
                Date exec1 = o1.getInterval().getLastTiming();
                Date exec2 = o2.getInterval().getLastTiming();

                return exec1.compareTo(exec2);
            }
        };
        Arrays.sort(jobs, jobComparator);

        JOBLOOP: for (final JobParam job : jobs)
        {
            // 実行時刻に到達しているかチェックします。
            if (!job.isTime())
            {
                continue JOBLOOP;
            }

            ScheduleJob schedule = job.getScheduleJob();
            if (null == schedule)
            {
                // create instance for first executing
                schedule = newInstance(job);
                if (null == schedule)
                {
                    // instance create failed
                    continue JOBLOOP;
                }

                job.setScheduleJob(schedule);
            }

            final String[] args = job.getArgs();
            schedule.execute(args);
            writeExecLog(job);
            isExecuted = true;
        }
        return isExecuted;
    }

    /**
     * 自動処理登録されているクラスについて終了処理を実行します。<br>
     */
    private void terminateJobs()
    {
        final JobParam[] jobs = $schedules.getSchedules();
        for (final JobParam job : jobs)
        {
            final ScheduleJob schedule = job.getScheduleJob();
            if (null != schedule)
            {
                schedule.terminate();
            }
        }
    }

    /**
     * 指示ファイルより、自動処理への指示を取得し実行します。
     * 指示ファイルが存在しない場合、指示ファイルの内容が
     * 規定されているものと違う場合は、無視します。<br>
     *
     * @return 終了指示を受け取った時、<code>true</code>を返します。<br>
     */
    private boolean checkInstruction()
    {
        boolean terminate = false;

        // 有効な指示が書き込まれているかチェックします。
        final boolean check = $instruction.checkInstruction();
        if (check)
        {
            final int type = $instruction.getInstructionType();
            switch (type)
            {
                // 再読込み指示
                case InstructionFileHandler.TYPE_RELOAD:

                    // 自動処理の再読込み指示を受け付けました。
                    logWrite(6020049, null);

                    // まず、現実行クラスの終了処理を行います。
                    terminateJobs();

                    // 設定ファイルの読込を行います。
                    $schedules.reload();

                    // 登録されている実行クラスをインスタンス化します。
                    loadJobs();
                    break;

                // 終了指示
                case InstructionFileHandler.TYPE_TERMINATE:

                    // 自動処理の終了指示を受け付けました。
                    logWrite(6020050, null);

                    // 実行クラスの終了処理を行います。
                    terminateJobs();
                    terminate = true;
                    break;

                default:
                    break;
            }
        }

        // 指示ファイルを初期化します。
        $instruction.resetInstruction();

        // 全ての指示を実行した後、今回の処理が「終了指示」であれば
        // 自動処理自身も終了します。
        return terminate;
    }

    /**
     * 一定時間スリープします。
     */
    private void sleepForTime()
    {
        try
        {
            sleep(_defSleepTime);
        }
        catch (final InterruptedException e)
        {
            // 割り込みを受け付けましたが無視します。
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * 指定されたメッセージをログ出力します。<br>
     *
     * @param key メッセージキー<br>
     * @param ins 挿入メッセージ<br>
     */
    private void logWrite(final int key, final String[] ins)
    {
        RmiMsgLogClient.write(key, getClass().getName(), ins);
    }

    /**
     * パラメータで指定されたクラスをインスタンス化し、その参照を返します。<br>
     * インスタンス化に失敗した場合は、<code>null</code>を返します。<br>
     *
     * @param param 自動処理設定パラメータ<br>
     * @return インスタンス化されたクラス<br>
     */
    private ScheduleJob newInstance(final JobParam param)
    {
        String errmsg = "";
        try
        {
            // 自動処理設定パラメータよりクラス名を取得しインスタンス化します。
            final String job = param.getClassName();
            final Class<?> execClass = Class.forName(job);
            if (ScheduleJob.class.isAssignableFrom(execClass))
            {
                final Object execObj = execClass.newInstance();
                final ScheduleJob schedule = (ScheduleJob)execObj;

                return schedule;
            }
            else
            {
                // 設定ファイルに指定された自動実行処理クラスが、インターフェイスScheduleJobを
                // 実装していません。クラス名={0} {1}
                final String[] message = {
                        execClass.getName(),
                        " Interface ScheduleJob has not been implemented.",
                };
                logWrite(6026500, message);

                return null;
            }
        }
        catch (final ClassNotFoundException e)
        {
            errmsg = " ClassNotFoundException caught.";
        }
        catch (final InstantiationException e)
        {
            errmsg = " InstantiationException caught.";
        }
        catch (final IllegalAccessException e)
        {
            errmsg = " IllegalAccessException caught.";
        }

        // インスタンスの生成に失敗しました。クラス名={0} {1}
        final String[] message = {
                param.getClassName(),
                errmsg,
        };
        logWrite(6006003, message);

        return null;
    }

    /**
     * このクラスのリビジョンを返します。<br>
     *
     * @return リビジョン文字列。<br>
     */
    public static String getVersion()
    {
        return "$Id: TimeKeeper.java 8083 2015-02-16 07:38:16Z okamura $";
    }
}
