// $Id: PatliteLogger.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.base.util.patlite;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import jp.co.daifuku.common.text.IniFileOperator;
import jp.co.daifuku.wms.base.util.timekeeper.TimeKeeperDefines;

/**
 * パトライトの通信ログを書き込むためのクラスです。
 *
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  Softecs
 * @author  Last commit: $Author: kishimoto $
 */

public final class PatliteLogger
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    private static final String LOGGER_NAME = "PatliteLogger";

    private static final String PROPERTIES_FILE = "PatliteLogging";

    private static final String LOG_FILE_NAME = "Patlite";

    private static final String LOG_FILE_SUFFIX = ".log";

    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";

    private static final int DEFAULT_LIMIT = 500000;

    private static final int DEFAULT_COUNT = 2;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;
    private static Logger $logger = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    private PatliteLogger()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * ログ出力。<br>
     * パラメータで指定された通信データをログ出力します。
     * @param send 送信ログの場合、<code>true</code>を指定します。
     * @param id 識別ID
     * @param data 通信データ
     */
    public static synchronized void write(boolean send, String id, byte[] data)
    {
        StringBuilder message = new StringBuilder();
        if (send)
        {
            // send to Patlite
            message.append(id);
            message.append(" <- ");
        }
        else
        {
            // receive from Patlite
            message.append(id);
            message.append(" -> ");
        }
        message.append(PatliteCommandSet.toHexString(data));

        Logger plogger = getLogger();
        if (null != plogger)
        {
            plogger.info(String.valueOf(message));
        }
        else
        {
            // フォーマッタは static にするとスレッドセーフにならないため
            // メソッド内で インスタンスを生成
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);

            System.out.print(df.format(new Date()));
            System.out.print(" ");
            System.out.println(String.valueOf(message));
        }
    }

    /**
     * ログ出力。<br>
     * パラメータで指定された通信データをログ出力します。
     * @param send 送信ログの場合、<code>true</code>を指定します。
     * @param id 識別ID
     * @param data 通信データ
     * @param len データ長
     */
    public static void write(boolean send, String id, byte[] data, int len)
    {
        if (0 < len)
        {
            if (len < data.length)
            {
                byte[] temp = new byte[len];
                for (int i = 0; i < len; i++)
                {
                    temp[i] = data[i];
                }
                write(send, id, temp);
            }
            else
            {
                write(send, id, data);
            }
        }
    }

    /**
     * ログ出力。<br>
     * パラメータで指定されたメッセージをログ出力します。
     * @param level ログレベル
     * @param message メッセージ
     */
    public static void write(Level level, String message)
    {
        Logger plogger = getLogger();
        if (null != plogger)
        {
            plogger.log(level, message);
        }
        else
        {
            // フォーマッタは static にするとスレッドセーフにならないため
            // メソッド内で インスタンスを生成
            SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);

            System.err.print(df.format(new Date()));
            System.err.print(" ");
            System.err.print(String.valueOf(level));
            System.err.print(" ");
            System.err.println(message);
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

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * ログ出力処理取得。<br>
     * パトライト関連のログを出力する処理を取得します。<br>
     * ログ出力処理が取得できなかった場合は、<code>null</code>が返ります。
     * @return ログ出力処理
     */
    private static Logger getLogger()
    {
        Logger logger = LogManager.getLogManager().getLogger(LOGGER_NAME);
        if (null == logger)
        {
            logger = createLogger();
        }
        return logger;
    }

    /**
     * ログ出力処理生成。<br>
     * パトライト関連のログ出力処理を生成します。<br>
     * ログ出力処理の生成に失敗した場合は、<code>null</code>が返ります。
     * @return ログ出力処理
     */
    private static Logger createLogger()
    {
        int limit = DEFAULT_LIMIT;
        int count = DEFAULT_COUNT;
        String logfilePath = null;
        try
        {
            ResourceBundle resource = ResourceBundle.getBundle(PROPERTIES_FILE, Locale.getDefault());
            String limitParam = resource.getString("limit");
            if (null != limitParam)
            {
                limit = Integer.valueOf(limitParam).intValue();
            }
            String countParam = resource.getString("count");
            if (null != countParam)
            {
                count = Integer.valueOf(countParam).intValue();
            }
            logfilePath = resource.getString("log_path");
        }
        catch (MissingResourceException e)
        {
            // 何もしません
        }

        try
        {
            FileHandler handler = new FileHandler(getLogFileName(logfilePath), limit, count, true);
            handler.setFormatter(new PatliteLogFormatter());

            $logger  = Logger.getLogger(LOGGER_NAME);
            $logger.addHandler(handler);
            LogManager.getLogManager().addLogger($logger);

            StringBuilder msg = new StringBuilder(LOGGER_NAME);
            msg.append(" is configured.");
            $logger.info(String.valueOf(msg));

            return $logger;
        }
        catch (SecurityException e)
        {
            System.err.println("Faild in Patlite FileHandler create.");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.err.println("Faild in Patlite FileHandler create.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ログファイル名取得。<br>
     * パトライトのログを出力するファイル名を取得します。<br>
     * ログファイルの配置されるディレクトリが設定されていない場合は、
     * システムの一時ディレクトリを使用します。
     * @param path ログファイルの配置されるディレクトリ
     * @return ログファイル名
     */
    private static String getLogFileName(String path)
    {
        StringBuilder fileName = new StringBuilder();
        if (null != path)
        {
            fileName.append(path);
            fileName.append("/");
        }
        else
        {
            // システムの一時ディレクトリを使用する
            fileName.append("%t/");
        }

        try
        {
            IniFileOperator operator = TimeKeeperDefines.getTKIniOperator();
            File f = new File(operator.getFileName());
            String iniName = f.getName();
            String dirName = iniName.substring(0, iniName.length() - 4); // .iniの4桁を除去

            fileName.append(dirName);
            fileName.append("/");

            File dir = new File(String.valueOf(fileName));

            if (!dir.exists())
            {
                dir.mkdirs();
            }
        }
        catch (Exception e)
        {
            // 何もしない
        }

        fileName.append(LOG_FILE_NAME);
        fileName.append("%g");
        fileName.append(LOG_FILE_SUFFIX);

        return String.valueOf(fileName);
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
        return "$Id: PatliteLogger.java 8053 2013-05-15 01:00:52Z kishimoto $";
    }
}
