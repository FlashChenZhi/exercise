// $Id: LogHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.common ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.common.MessageResource;

/**<jp>
 * メッセージログ・ファイルへの書き込みと、読み込みを行うためのクラスです。
 * ログ・ファイルの形式は、以下のようになります。デリミタ文字は、タブとなっており、
 * メッセージ・パラメータ内の、タブ,改行は すべてスペースに置き換えられます。
 * <pre>
 *    日付 メッセージ番号 メッセージ・パラメータ [...]
 * </pre>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class is used to write in/read the message log file.
 * Log file is formatted as below. Tabs are used as delimiters; all tabs and breaks
 * in the message paramters can be replaced with space.
 * <pre>
 *    Date Message no. Message parameter [...]
 * </pre>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class LogHandler extends Object
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ログ・ファイルのデリミタ
     </jp>*/
    /**<en>
     * Delimiter of the log file
     </en>*/
    protected static final String LOG_DELIM = "\t" ;
    /**<jp>
     * ログ日付のフォーマット形式
     </jp>*/
    /**<en>
     * Format type of log date
     </en>*/
    protected static final String DATE_FORMAT = "yyyy MM dd HH mm ss" ;
    /**<jp>
     * ログのエンコード形式
     </jp>*/
    /**<en>
     * Encoding of the log
     </en>*/
    protected static final String LOG_ENCODE = "UTF-8" ;
    /**<jp>
     * 日付のフォーマットを行うためのフォーマッタ
     </jp>*/
    /**<en>
     * Formatter which formats the dates
     </en>*/
    protected static SimpleDateFormat MSG_DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    // Class variables -----------------------------------------------
    /**<jp>
     * メッセージのロケールを保持する変数。
     </jp>*/
    /**<en>
     * Variables which preserve locale of messages.
     </en>*/
    protected Locale wLocale ;
    /**<jp>
     * メッセージログ・ファイル名を保持する変数。
     </jp>*/
    /**<en>
     *Variables which preserve the name of message log file
     </en>*/
    protected String wLogfileName ;
    /**<jp>
     * メッセージリソースを保持する変数
     </jp>*/
    /**<en>
     * Variables which preserve the message resource
     </en>*/
    protected MessageResource wMsgRes ;
    /**<jp>
     * メッセージログ・ファイルを読み込むための<code>Reader</code>
     </jp>*/
    /**<en>
     * the <code>Reader</code> in order to read the message log file
     </en>*/
    protected LineNumberReader wLNReader ;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルト・ロケールで、メッセージを読み書きするように初期化します。
     * @param logfile 対象のログファイル名
     </jp>*/
    /**<en>
     * Initialize so that message will be written/ read by default locale.
     * @param logfile :target log file
     </en>*/
    public LogHandler(String logfile)
    {
        this(logfile, Locale.getDefault()) ;
    }

    /**<jp>
     * ロケールを指定して、メッセージを読みこむように初期化します。
     * @param locale  指定するロケール
     * @param logfile 対象のログファイル名
     </jp>*/
    /**<en>
     * Initialize so that message will be read by specified locale
     * @param locale  :locale to specify
     * @param logfile :name of the target log file
     </en>*/
    public LogHandler(String logfile, Locale locale)
    {
        wLocale = locale ;
        wLogfileName = logfile ;
        wMsgRes = new MessageResource(locale) ;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 現在書き込みを行っているファイルのサイズを取得します。
     * @return ファイルサイズ
     </jp>*/
    /**<en>
     * Retrieve the size of the file currently in writing process.
     * @return :file size
     </en>*/
    public long getFileSize()
    {
        File file = new File(wLogfileName);
        return file.length();
    }

    /**<jp>
     * メッセージを書き込みます。メッセージは、リソースとパラメータ・配列から
     * フォーマットされ、ファイルに書き込まれます。
     * このとき、パラメータの数が合わない場合は、パラメータ情報が捨てられるか、
     * 埋め込みが完了していない状態でログが書き込まれます。
     * ログ・ファイルには、パラメータがデリミタ・文字で区切られた文字列として格納されます。
     * @param settingname Setting name
     * @param tablename Table name
     * @param msg  メッセージ
     * @throws IOException
     </jp>*/
    /**<en>
     * Write the message. The message will be formatted according to the resource and parameter
     * array, then will be written in the file.
     * If the number of parameters does not match then, either the parameter data will be thrown,
     * or the log will be written before the embedding complete.
     * The parameters will be stored in the log file as a string delimited by delimiters or characters.
     * @param settingname Setting name
     * @param tablename Table name
     * @param msg  message
     * @throws IOException
     </en>*/
    public void write(String settingname, String tablename, String msg) throws IOException
    {
        //<jp> Open streams</jp>
        //<en> Open streams</en>
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(wLogfileName, true), LOG_ENCODE) ;

        //<jp> Create msg parameter string</jp>
        //<en> Create msg parameter string</en>
        StringBuffer msgbuf = new StringBuffer(256) ;

        //<jp> Date first</jp>
        //<en> Date first</en>
        Date ndate = new Date() ;
        msgbuf.append(MSG_DATE_FORMATTER.format(ndate)) ;
        
        //<jp> Setting name.</jp>
        //<en> Setting name.</en>
        msgbuf.append(LOG_DELIM + settingname) ;
        
        //<jp> Table name.</jp>
        //<en> Table name.</en>
        msgbuf.append(LOG_DELIM + tablename) ;
        
        //<jp>Get formatted message.</jp>
        //<en>Get formatted message.</en>
        String msginfo = MessageResource.getMessage(msg);
        //<jp> message number</jp>
        //<en> message number</en>
        msgbuf.append(LOG_DELIM + msginfo) ;

        //<jp> write parameter string to logfile</jp>
        //<en> write parameter string to logfile</en>
        String pmsg = String.valueOf(msgbuf) + "\n" ;

        synchronized (osw)
        {
            osw.write(pmsg, 0, pmsg.length()) ;
            //<jp> finish : close streams</jp>
            //<en> finish : close streams</en>
            osw.flush() ;
        }
        osw.close() ;
        osw = null ;
    }


    // Public accsesser methods --------------------------------------
    /**<jp>
     * 現在使用中のログ・ファイル名を取得します。
     * @return    ログ・ファイル名
     </jp>*/
    /**<en>
     * Retrieve the name of log file which is used at moment.
     * @return    :name of the log file
     </en>*/
    public String getLogfile()
    {
        return (wLogfileName) ;
    }
    /**<jp>
     * 現在使用中のロケールを取得します。
     * @return    <code>Locale</code>
     </jp>*/
    /**<en>
     * Retrieve the locale which is used at moment.
     * @return    <code>Locale</code>
     </en>*/
    public Locale getLocale()
    {
        return (wLocale) ;
    }
    /**<jp>
     * 現在使用中のメッセージ・リソースを取得します。
     * @return    <code>MessageResource</code>
     </jp>*/
    /**<en>
     * Retrieve the message resource which is used at moment.
     * @return    <code>MessageResource</code>
     </en>*/
    public MessageResource getMessageResource()
    {
        return (wMsgRes) ;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    // Debug methods -----------------------------------------------
    /*<jp>
    public static void main(String[] argv)
    {
        try
        {
            MessageLogHandler mlog = new MessageLogHandler("test.log") ;
            Object[] tObj = new Object[3] ;
            for (int i=0; i < 10; i++)
            {
                tObj[0] = new Integer(1) ;
                tObj[1] = new Integer(11000)  ;
                mlog.write(99999, tObj) ;
            }

            mlog.readOpen() ;
            LogMessage msg ;
            while ((msg = mlog.readNext()) != null)
            {
                System.out.print(msg.getDate()) ;
                System.out.print(":") ;
                System.out.print(msg.getMessageNumber()) ;
                System.out.print(":") ;
            }
            mlog.readClose() ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }
    </jp>*/
    /*<en>
    public static void main(String[] argv)
    {
        try
        {
            MessageLogHandler mlog = new MessageLogHandler("test.log") ;
            Object[] tObj = new Object[3] ;
            for (int i=0; i < 10; i++)
            {
                tObj[0] = new Integer(1) ;
                tObj[1] = new Integer(11000)  ;
                mlog.write(99999, tObj) ;
            }

            mlog.readOpen() ;
            LogMessage msg ;
            while ((msg = mlog.readNext()) != null)
            {
                System.out.print(msg.getDate()) ;
                System.out.print(":") ;
                System.out.print(msg.getMessageNumber()) ;
                System.out.print(":") ;
            }
            mlog.readClose() ;
        }
        catch (Exception e)
        {
            e.printStackTrace() ;
        }
    }
    </en>*/

}
//end of class

