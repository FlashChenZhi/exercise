// $Id: ScheduleFileHandler.java 5527 2009-11-09 08:03:43Z ota $
package jp.co.daifuku.wms.base.util.timekeeper;

/*
 * Copyright(c) 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;

/**
 * 自動処理で使用する設定ファイルのハンドリングを行うクラスです。<br>
 * 
 * 2008-11-24 レコードフォーマット変更<br>
 * <pre>
 * FORMAT:
 * ENABLE TIMEFORMAT CLASS ARGS
 * 
 * 例: 有効,300秒間隔,引数="abc xyz"
 * true 300 jp.co.daifuku.wms.base.util.Test abc xzy
 * 例: 無効,毎日1:00,引数="abc xyz"
 * false 0 1 * * * jp.co.daifuku.wms.base.util.Test abc xzy
 * </pre>
 * 
 * @version $Revision: 5527 $, $Date: 2009-11-09 17:03:43 +0900 (月, 09 11 2009) $
 * @author Softecs
 * @author Last commit: $Author: ota $
 */

public class ScheduleFileHandler
        extends AbstractTKHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * <code>COMMENT_STR</code><br> 自動処理設定ファイルのコメント文字列<br>
     */
    private static final String COMMENT_STR = "#";

    /**
     * <code>SPLIT_FIELD</code><br> 自動処理設定ファイルのフィールド区切り文字列<br>
     */
    private static final String SPLIT_FIELD = " |\t";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    /** mutex object */
    protected static final Object $mutex = new Object();

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private File _scheduleFile;

    private final List<JobParam> _params = new ArrayList<JobParam>();

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 設定ファイルを読み込んで内部に保持します。<br>
     * 以降、設定ファイルの内容は、保持している情報をもとに提示されます。<br>
     */
    public ScheduleFileHandler()
    {
        try
        {
            // パラメータより設定ファイルの情報を取得します。
            _scheduleFile = TimeKeeperDefines.getScheduleFile();

            if (!_scheduleFile.exists())
            {
                _scheduleFile.createNewFile();
            }

            // 設定ファイル読込
            load();
        }
        catch (final ReadWriteException e)
        {
            throw new RuntimeException(e);
        }
        catch (final IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 設定ファイルを読み込んで内部に保持します。<br>
     * 以降、設定ファイルの内容は、保持している情報をもとに提示されます。<br>
     * @param iniFilePath TimeKeeper.iniファイルパス
     */
    public ScheduleFileHandler(String iniFilePath)
    {
        try
        {
            // パラメータより設定ファイルの情報を取得します。
            _scheduleFile = TimeKeeperDefines.getScheduleFile(iniFilePath);

            if (!_scheduleFile.exists())
            {
                _scheduleFile.createNewFile();
            }

            // 設定ファイル読込
            load();
        }
        catch (final ReadWriteException e)
        {
            throw new RuntimeException(e);
        }
        catch (final IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 設定ファイルより読み込んで内部に保持しているスケジュールを 取得します。<br>
     * 
     * @return 自動処理パラメータ(配列)<br>
     */
    public JobParam[] getSchedules()
    {
        return _params.toArray(new JobParam[_params.size()]);
    }

    /**
     * 内部に保持している情報を破棄して、
     * 設定ファイルの再読込を行います。<br>
     */
    public void reload()
    {
        load();
    }

    /**
     * 内部に保持しているスケジュールを指定された
     * パラメータで入れ換えて 設定ファイルに反映します。<br>
     * パラメータに<code>null</code>を指定すると、
     * 空の設定ファイルが 生成されます。<br>
     * 
     * @param params スケジュール<br>
     */
    public void replaceAll(final JobParam[] params)
    {
        synchronized ($mutex)
        {
            _params.clear();

            // パラメータにnullが指定された場合は、空の設定ファイルを生成
            // する様にします。
            JobParam[] innerParams = (null == params) ? new JobParam[0]
                                                     : params;

            // パラメータで指定されたスケジュールを設定ファイルに書き込みます。
            for (final JobParam innerParam : innerParams)
            {
                _params.add(innerParam);
            }
            save();
        }
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * プロパティ定義されているファイル名を取得します。<br>
     * 
     * @return ファイル名<br>
     */
    public String getScheduleFileName()
    {
        return _scheduleFile.getName();
    }

    /**
     * プロパティ定義されているファイルパスを取得します。<br>
     * 
     * @return ファイルパス<br>
     */
    public String getScheduleFilePath()
    {
        return _scheduleFile.getPath();
    }

    /**
     * プロパティ定義されているディレクトリ名を取得します。<br>
     * 
     * @return ディレクトリ名<br>
     */
    public String getTimeKeeperDir()
    {
        return _scheduleFile.getParent();
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
     * 内部に保持しているスケジュールを設定ファイルに書き込みます。
     */
    private void save()
    {
        BufferedWriter writer = null; // closed in finally block
        FileLock lock = null;
        try
        {
            synchronized ($mutex)
            {
                // 指示ファイルの内容を読み込みます。
                final FileOutputStream out = new FileOutputStream(_scheduleFile, false);
                lock = out.getChannel().lock();
                writer = new BufferedWriter(new OutputStreamWriter(out), 1024);

                // 内部に保持しているスケジュールを設定ファイルに書き込みます。
                for (JobParam param : _params)
                {
                    String line = format(param);
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
        catch (final IOException e)
        {
            // ファイルの入出力エラーが発生しました。{0}
            final String[] ins = new String[1];
            ins[0] = _scheduleFile.getPath();
            RmiMsgLogClient.write(6006020, getClass().getName(), ins);
            throw new RuntimeException(e);
        }
        finally
        {
            release(lock);
            close(writer);
        }
    }

    /**
     * JOBパラメータを文字列にフォーマットします。
     * 
     * @param param JOBパラメータ
     * @return ファイル書き込みようの文字列
     */
    private static String format(JobParam param)
    {
        final StringBuilder b = new StringBuilder();

        final Interval interval = param.getInterval();

        b.append(interval.isEnabled());
        b.append(" ");

        b.append(interval.getTimingString());
        b.append(" ");

        b.append(param.getClassName());

        final String[] args = param.getArgs();
        for (final String arg : args)
        {
            b.append(" ");
            b.append(arg);
        }
        return String.valueOf(b);
    }

    /**
     * パラメータファイルにより指定されている
     * 設定ファイルを読み込んで その内容を保持します。<br>
     */
    private void load()
    {
        RandomAccessFile raf = null; // closed in finally block
        BufferedReader reader = null; // closed in finally block
        FileLock lock = null;
        try
        {
            synchronized ($mutex)
            {
                _params.clear();

                // 取得した情報より設定ファイルを読み込んで内部に保持します。
                raf = new RandomAccessFile(_scheduleFile, "rw");
                final FileInputStream in = new FileInputStream(raf.getFD());
                lock = raf.getChannel().lock();
                reader = new BufferedReader(new InputStreamReader(in), 1024);
                int rp = 0;
                String line = "";

                // 設定ファイルを読み込みます。
                while (null != (line = reader.readLine()))
                {
                    rp++;

                    // 空白行、コメント行は無視します。
                    line = line.trim();
                    final boolean isComment = COMMENT_STR.equals(line.substring(0, COMMENT_STR.length()));
                    if (isComment || (0 == line.length()))
                    {
                        continue;
                    }

                    // 設定情報をチェックします。
                    final String[] fields = line.split(SPLIT_FIELD);
                    final JobParam param = parseFields(fields);
                    if (null == param)
                    {
                        // 指定されているフィールドが規定されているものでない場合
                        // この設定行を無視します。
                        final String[] ins = {
                            _scheduleFile.getPath(),
                            String.valueOf(rp),
                        };
                        RmiMsgLogClient.write(6003107, getClass().getName(), ins);
                        continue;
                    }

                    // 読み込んだ設定情報を自動処理リストに追加します。
                    _params.add(param);
                }
            }
        }
        catch (final IOException e)
        {
            // ファイルの入出力エラーが発生しました。{0}
            final String[] ins = {
                _scheduleFile.getPath(),
            };
            RmiMsgLogClient.write(6006020, getClass().getName(), ins);
            throw new RuntimeException(e);
        }
        finally
        {
            release(lock);
            close(reader, raf);
        }
    }

    /**
     * スケジュールデータを読み込んで、時刻間隔、
     * 実行クラス、 パラメータに分解します。
     * 
     * @param fields 読み込みデータフィールド
     * @return 自動処理設定オブジェクト
     */
    private static JobParam parseFields(final String[] fields)
    {
        // フィールド数が3以下であれば、エラーとします。
        // (フィールド数が足りません。)
        if (3 > fields.length)
        {
            return null;
        }
        try
        {
            int idx = 0;
            // 先頭は enable/disable
            String enableStr = fields[idx++];
            boolean isEnable = Boolean.valueOf(enableStr);

            // find class define
            int startIdx = idx;
            for (int i = idx; i < fields.length; i++)
            {
                if (fields[i].matches("^[a-z|A-Z].+"))
                {
                    idx = i;
                    break;
                }
            }
            if (idx == startIdx)
            {
                // no class define found
                return null;
            }

            // build timing string
            StringBuilder b = new StringBuilder();
            for (int i = startIdx; i < idx; i++)
            {
                b.append(fields[i]);
                b.append(" ");
            }
            String timingStr = String.valueOf(b).trim();

            // get class name
            String className = fields[idx++];

            // get class parameters
            int numParams = fields.length - idx;
            String[] args = new String[numParams];
            if (0 < numParams)
            {
                System.arraycopy(fields, idx, args, 0, numParams);
            }

            final JobParam job = new JobParam(timingStr, className, args, isEnable);

            return job;
        }
        catch (RuntimeException e)
        {
            // format error
            return null;
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。<br>
     * 
     * @return リビジョン文字列。<br>
     */
    public static String getVersion()
    {
        return "$Id: ScheduleFileHandler.java 5527 2009-11-09 08:03:43Z ota $";
    }
}
