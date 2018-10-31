// $Id: PatliteLogFormatter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.patlite;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * パトライトでのログ出力フォーマッタクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */
public class PatliteLogFormatter
        extends Formatter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;
    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     */
    public PatliteLogFormatter()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
     */
    @Override
    public String format(LogRecord arg0)
    {
        StringBuilder msg = new StringBuilder();
        // フォーマッタは static にするとスレッドセーフにならないため
        // メソッド内で インスタンスを生成
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);

        msg.append(df.format(new Date(arg0.getMillis())));
        msg.append(' ');
        msg.append(String.valueOf(arg0.getLevel()));

        /* この部分の出力は、PatliteLoggerを使用している限りにおいて不要
         msg.append(' ');
         String className = arg0.getSourceClassName();
         if (null != className)
         {
         msg.append(className);
         }
         else
         {
         msg.append(arg0.getLoggerName());
         }

         String methodName = arg0.getSourceMethodName();
         if (null != methodName)
         {
         msg.append("[");
         msg.append(methodName);
         msg.append("]");
         }
         */

        msg.append(' ');
        msg.append(formatMessage(arg0));
        msg.append("\n");

        Throwable throwable = arg0.getThrown();
        if (null != throwable)
        {
            for (StackTraceElement element : throwable.getStackTrace())
            {
                msg.append(String.valueOf(element));
                msg.append("\n");
            }
        }

        return String.valueOf(msg);
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

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PatliteLogFormatter.java 87 2008-10-04 03:07:38Z admin $";
    }
}
