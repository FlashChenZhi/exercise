//$Id: TraceWriter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.common.trace;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.*;
import java.util.*;


/**
 * スタックトレースを書き込むためのクラスです。
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- 変更履歴 -->
 * <tr><td nowrap>2005/07/13</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class TraceWriter
        extends Thread
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //	private String	$classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** アイドル時の待ち合わせ時間 (秒) */
    public static final int IDLE_WAIT_SEC = 30;


    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    /** トレースファイル */
    private File p_traceFile;

    /** 例外(Exception)バッファ用リスト */
    private List p_exceptionList;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private boolean _terminate = false;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * トレースファイルを指定してインスタンスを生成します。<br>
     * このコンストラクタ内で、トレースファイル書き込み用の
     * スレッドを作成・スタートさせます。
     * @param traceFile トレースファイル
     */
    public TraceWriter(File traceFile)
    {
        super();

        setDaemon(true);

        setTraceFile(traceFile);
        setExceptionList(createExceptionList());

        start();
    }


    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 例外を書き込みます。
     * @param tri 書き込み対象の例外
     */
    public synchronized void write(TraceInfo tri)
    {
        getExceptionList().add(tri);
        notify();
    }


    /**
     * 非同期にトレースファイルを書き込みます。
     * @see java.lang.Thread#run()
     */
    public void run()
    {
        List exList = getExceptionList();

        while (!_terminate)
        {
            if (exList.size() == 0)
            {
                timedWait(IDLE_WAIT_SEC * 1000);
            }
            writeTrace();
        }
    }

    /**
     * トレース書き込みスレッドを終了させます。<br>
     * すべてのトレースが書き込み終わった後、スレッドは終了します。
     */
    public synchronized void terminate()
    {
        _terminate = true;
        notify();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return traceFileを返します。
     */
    public synchronized File getTraceFile()
    {
        return p_traceFile;
    }

    /**
     * @param traceFile traceFileを設定します。
     */
    public synchronized void setTraceFile(File traceFile)
    {
        p_traceFile = traceFile;
    }

    /**
     * @return exceptionListを返します。
     */
    protected List getExceptionList()
    {
        return p_exceptionList;
    }

    /**
     * @param exceptionList exceptionListを設定します。
     */
    protected void setExceptionList(List exceptionList)
    {
        p_exceptionList = exceptionList;
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    protected List createExceptionList()
    {
        return Collections.synchronizedList(new ArrayList());
    }

    /**
     * リストから例外を取得後、トレースファイルを書き込みます。
     */
    public void writeTrace()
    {
        /*3.0仕様でtrace.logを出力しない
        List exList = getExceptionList();
        if (exList.size() == 0)
        {
            return;
        }
         PrintWriter wr = null ;
         
         try
         {
         
         wr = open(getTraceFile()) ;
         while (exList.size() > 0)
         {
         Object tmpobj = exList.remove(0) ;
         if (tmpobj instanceof TraceInfo)
         {
         TraceInfo tri = (TraceInfo)tmpobj ;
         TraceRecord.writeTrace(wr, tri) ;
         }
         }
         wr.flush() ;
         }
         catch (IOException e)
         {
         e.printStackTrace() ;
         }
         
         finally
         {
         close(wr) ;
         }
         */
    }

    /**
     * トレース情報を一件書き込みます。
     * 
     * @param traceFile 書き込み先トレースファイル
     * @param tri トレース情報
     * @throws IOException 
     */
    public static void writeOneTrace(File traceFile, TraceInfo tri)
            throws IOException
    {
        PrintWriter wr = open(traceFile);
        try
        {
            TraceRecord.writeTrace(wr, tri);
            wr.flush();
        }
        finally
        {
            close(wr);
        }
    }

    /**
     * 一定時間待ち合わせます。
     * @param time ミリ秒
     */
    protected synchronized void timedWait(long time)
    {
        try
        {
            wait(time);
        }
        catch (InterruptedException e)
        {
        }
    }


    /**
     * トレースファイルをクローズします。 
     * @param wr トレースファイルライター
     */
    protected static void close(Writer wr)
    {
        try
        {
            if (wr != null)
            {
                wr.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * トレース書き込み用のWriterを作成します。
     * @param trFile トレースファイル
     * @return PrintWriter トレース書き込み用のPrintWriter
     * @throws IOException
     */
    protected static PrintWriter open(File trFile)
            throws IOException
    {
        Writer fw = new BufferedWriter(new FileWriter(trFile, true));
        PrintWriter prt = new PrintWriter(fw);
        return prt;
    }

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
        return "$Id: TraceWriter.java 87 2008-10-04 03:07:38Z admin $";
    }
}
