// $Id: TKExecuteLog.java 994 2008-12-02 06:08:29Z mshimizu@softecs.jp $
// $LastChangedRevision: 994 $

package jp.co.daifuku.wms.base.util.timekeeper;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.common.text.StringUtil;

/**
 * TimeKeeperの実行ログの読み込みと書き込みを行うクラスです。<br>
 * 実行ログファイルはTimeKeeper設定ファイルに記述します。<br>
 * レコードフォーマットは以下の通りです。<br>
 * 区切文字 = \t
 * <pre>
 * クラス名 \t yyyy-MM-dd HH:mm:ss.SSS \n
 * </pre>
 *
 * @version $Revision: 994 $, $Date: 2008-12-02 15:08:29 +0900 (火, 02 12 2008) $
 * @author  ss
 * @author  Last commit: $Author: mshimizu@softecs.jp $
 */

public class TKExecuteLog
        extends AbstractTKHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    private static final String EXECUTELOG_FILE = "EXECUTELOG_FILE";

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    private static final String DELIMITER = "\t";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private File _logFile = null;

    //------------------------------------------------------------
    // process at class loaded.
    //------------------------------------------------------------
    // static
    // {
    // TODO initialize class
    // }

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ログの書込先を初期化してインスタンスを生成します。
     */
    public TKExecuteLog()
    {
        super();

        try
        {
            IniFileOperator tkIniOperator = TimeKeeperDefines.getTKIniOperator();
            final String executeLogFilePath = tkIniOperator.get(EXECUTELOG_FILE);
            _logFile = new File(executeLogFilePath);
            if (!_logFile.exists())
            {
                _logFile.createNewFile();
            }
        }
        catch (ReadWriteException e)
        {
            // TimeKeeper設定ファイルが正しく配置されていません。
            e.printStackTrace();
            throw new RuntimeException("environmental setting is not correctly.");
        }
        catch (IOException e)
        {
            // 実行ログファイルの作成に失敗しました。
            throw new RuntimeException("failed in create the TimeKeeper Execute Log.");
        }
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 最終実行時刻を書き出します。
     *
     * @param jobs 書込対象ジョブ一覧
     */
    public void write(JobParam[] jobs)
    {
        if ((null == jobs) || (0 == jobs.length))
        {
            return;
        }

        try
        {
            synchronized (ExecuteLogMutex.getInstance())
            {
                Map<String, Date> map = load();

                final Date execTime = Calendar.getInstance().getTime();
                for (JobParam job : jobs)
                {
                    map.put(job.getClassName(), execTime);
                }

                save(map);
            }
        }
        catch (Exception e)
        {
            // ファイルの入出力エラーが発生しました。{0}
            final String[] ins = {
                getLogFile().getPath(),
            };
            RmiMsgLogClient.write(6006020, getClass().getName(), ins);
        }
    }

    /**
     * 最終実行時刻を書き出します。
     *
     * @param job 実行クラスパス
     */
    public void write(String job)
    {
        if (!StringUtil.isBlank(job))
        {
            try
            {
                synchronized (ExecuteLogMutex.getInstance())
                {
                    Map<String, Date> map = load();

                    final Date execTime = Calendar.getInstance().getTime();
                    map.put(job, execTime);

                    save(map);
                }
            }
            catch (Exception e)
            {
                // ファイルの入出力エラーが発生しました。{0}
                final String[] ins = {
                    getLogFile().getPath(),
                };
                RmiMsgLogClient.write(6006020, getClass().getName(), ins);
            }
        }
    }

    /**
     * 処理毎の最終実行時刻を読み出します。<br>
     *
     * @return 処理毎の最終実行時刻
     * @throws Exception 実行ログファイルの読み込みに失敗した場合に通知されます。
     */
    public Map<String, Date> read()
            throws Exception
    {
        try
        {
            synchronized (ExecuteLogMutex.getInstance())
            {
                final Map<String, Date> exMap = load();
                return exMap;
            }
        }
        catch (Exception e)
        {
            // ファイルの入出力エラーが発生しました。{0}
            final String[] ins = {
                getLogFile().getPath(),
            };
            RmiMsgLogClient.write(6006020, getClass().getName(), ins);
            throw e;
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * @return 実行ログファイルを取得します。
     */
    private File getLogFile()
    {
        return _logFile;
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
     * 実行ログファイルを読み込みます。
     *
     * @return 読み込んだ実行ログ
     * @throws IOException 実行ログファイルの読み込みに失敗した場合に通知されます。
     * @throws ParseException 実行ログファイルに記録されている日時の読込に失敗した場合に通知されます。
     */
    private Map<String, Date> load()
            throws IOException,
                ParseException
    {
        Map<String, Date> map = new HashMap<String, Date>();

        BufferedReader reader = null;
        RandomAccessFile raf = null;
        FileLock lock = null;
        final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        try
        {
            // ファイル読込においても、排他ロックのために"rw"でファイルオブジェクトを生成する。
            raf = new RandomAccessFile(getLogFile(), "rw");
            final FileInputStream in = new FileInputStream(raf.getFD());
            lock = raf.getChannel().lock();
            reader = new BufferedReader(new InputStreamReader(in), 1024);

            String record;
            while (null != (record = reader.readLine()))
            {
                final String[] datas = record.split(DELIMITER);
                if (2 == datas.length)
                {
                    final Date date = format.parse(datas[1]);
                    map.put(datas[0], date);
                }
            }
        }
        catch (IOException e)
        {
            // 実行ログファイルの読み込みに失敗しました。
            RmiMsgLogClient.write(new TraceHandler(6007031, e), getClass().getName());
            throw e;
        }
        catch (ParseException e)
        {
            // 実行ログに記録されている日時の読込に失敗しました。
            RmiMsgLogClient.write(new TraceHandler(6007031, e), getClass().getName());
            throw e;
        }
        finally
        {
            release(lock);
            close(reader, raf);
        }

        return map;
    }

    /**
     * 実行ログファイルに指定された保存データを書き込みます。
     *
     * @param map 保存データ
     * @throws IOException 実行ログファイルの書き込みに失敗した場合に通知されます。
     */
    private void save(Map<String, Date> map)
            throws IOException
    {
        if ((null == map) || map.isEmpty())
        {
            return;
        }

        final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        BufferedWriter writer = null;
        FileLock lock = null;
        try
        {
            final FileOutputStream out = new FileOutputStream(getLogFile(), false);
            lock = out.getChannel().lock();
            writer = new BufferedWriter(new OutputStreamWriter(out), 1024);

            for (String key : map.keySet())
            {
                final String lastExecTime = format.format(map.get(key));

                StringBuilder record = new StringBuilder(key);
                record.append(DELIMITER);
                record.append(lastExecTime);
                writer.write(String.valueOf(record));
                writer.newLine();
            }
        }
        catch (IOException e)
        {
            // 実行ログファイルの書き込みに失敗しました。
            RmiMsgLogClient.write(new TraceHandler(6007031, e), getClass().getName());
            throw e;
        }
        finally
        {
            release(lock);
            close(writer);
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * 実行ログに格納されている日時のフォーマットを適用して文字列に変換して返します。
     *
     * @param date 変換対象日時
     * @return 変換後文字列
     */
    public static String formatDate(Date date)
    {
        if (null == date)
        {
            return "";
        }

        final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: TKExecuteLog.java 994 2008-12-02 06:08:29Z mshimizu@softecs.jp $";
    }
}
