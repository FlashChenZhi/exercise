//$Id: TraceRecord.java 8075 2014-09-19 07:16:57Z okayama $
package jp.co.daifuku.common.trace;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * スタックトレースログファイルのレコード管理クラスです。
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
 * @version $Revision: 8075 $, $Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $
 * @author  ss
 * @author  Last commit: $Author: okayama $
 */
public class TraceRecord
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 日付フォーマット文字列 */
    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";

    /** トレース区切り文字列 */
    public static final String TRACE_DELIMITER = "--";

    /** クラス名ヘッダ文字列 */
    public static final String CLASS_HEADER = "CLASS:\t";

    /** リビジョンヘッダ文字列 */
    public static final String REVISION_HEADER = "REVISION:\t";

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private int p_recordIdx = 0;

    private Date p_logDate;

    private String p_className = "";

    private String p_revision = "";

    private List p_stackTrace = new ArrayList();

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * スタックトレースをプリントライタに書き込みます。
     * @param prw プリントライタ
     * @param tri トレース情報
     */
    public static void writeTrace(PrintWriter prw, TraceInfo tri)
    {
        //		SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT) ;
        //		String dateStr = fmt.format(tri.getLogDate()) ;
        //
        //		String caller = tri.getClassName() ;
        //		String rev = tri.getRevision() ;
        //
        //		prw.println(dateStr) ;
        //		prw.println(CLASS_HEADER + caller) ;
        //		prw.println(REVISION_HEADER + rev) ;
        //		tri.getException().printStackTrace(prw) ;
        //		prw.println(TRACE_DELIMITER) ;
    }


    /**
     * トレース一覧を取得します。
     * @param rdr
     * @return スタックトレース一覧
     * @throws IOException
     */
    public static TraceRecord[] readTrace(LineNumberReader rdr)
            throws IOException
    {
        final int ST_IDLE = 0;
        final int ST_NEXT_CLASS = 1;
        final int ST_NEXT_REVISION = 2;
        final int ST_NEXT_TRACE = 3;

        String line;
        List trList = new ArrayList();

        int status = ST_IDLE;
        TraceRecord aTrace = null;

        for (int lineIdx = 0; null != (line = rdr.readLine()); lineIdx++)
        {
            if (isDelimiter(line))
            {
                status = ST_IDLE;
                continue;
            }

            if (isDateRecord(line))
            {
                aTrace = new TraceRecord();
                trList.add(aTrace);

                aTrace.setRecordIdx(lineIdx);
                aTrace.setLogDate(getDate(line));

                status = ST_NEXT_CLASS;
            }
            else if (status == ST_NEXT_CLASS && isClassName(line))
            {
                aTrace.setClassName(getClassName(line));
                status = ST_NEXT_REVISION;
            }
            else if (status == ST_NEXT_REVISION && isRevision(line))
            {
                aTrace.setRevision(getRevision(line));
                status = ST_NEXT_TRACE;
            }
            else if (status == ST_NEXT_TRACE)
            {
                aTrace.addStackTrace(line);
            }
        }
        return (TraceRecord[])trList.toArray(new TraceRecord[0]);
    }

    /**
     * 内容の文字列表現を返します。
     * @return 属性の文字列表現
     */
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("Record:");
        buff.append(getRecordIdx());
        buff.append(",Date:");
        buff.append(getLogDate());
        buff.append(",Class:");
        buff.append(getClassName());
        buff.append(",Revision:");
        buff.append(getRevision());
        buff.append(",Trace:");
        buff.append(getStackTrace());

        return buff.toString();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------


    /**
     * @return classNameを返します。
     */
    public String getClassName()
    {
        return p_className;
    }

    /**
     * @param className classNameを設定します。
     */
    protected void setClassName(String className)
    {
        p_className = className;
    }

    /**
     * @return logDateを返します。
     */
    public Date getLogDate()
    {
        return p_logDate;
    }

    /**
     * @param logDate logDateを設定します。
     */
    protected void setLogDate(Date logDate)
    {
        p_logDate = logDate;
    }

    /**
     * @return recordIdxを返します。
     */
    public int getRecordIdx()
    {
        return p_recordIdx;
    }

    /**
     * @param recordIdx recordIdxを設定します。
     */
    protected void setRecordIdx(int recordIdx)
    {
        p_recordIdx = recordIdx;
    }

    /**
     * @return revisionを返します。
     */
    public String getRevision()
    {
        return p_revision;
    }

    /**
     * @param revision revisionを設定します。
     */
    protected void setRevision(String revision)
    {
        p_revision = revision;
    }

    /**
     * @return stackTraceを返します。
     */
    public String[] getStackTrace()
    {
        return (String[])p_stackTrace.toArray(new String[0]);
    }

    /**
     * @param stackTrace stackTraceを設定します。
     */
    protected void addStackTrace(String stackTrace)
    {
        p_stackTrace.add(stackTrace);
    }

    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    /**
     * 日付レコードかどうかをチェックします。
     * @param record 読み込んだレコード
     * @return 日付レコードの時 true.
     */
    protected static boolean isDateRecord(String record)
    {
        SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
        try
        {
            fmt.parse(record);
            return true;
        }
        catch (ParseException e)
        {
        }
        return false;
    }

    /**
     * 区切り行かどうかをチェックします。
     *
     * @param record 読み込んだレコード
     * @return 区切り行の時 true.
     */
    protected static boolean isDelimiter(String record)
    {
        return record.trim().equals(TRACE_DELIMITER);
    }

    /**
     * クラス名行かどうかをチェックします。
     *
     * @param record 読み込んだレコード
     * @return クラス名の時 true.
     */
    protected static boolean isClassName(String record)
    {
        return record.trim().startsWith(CLASS_HEADER);
    }

    /**
     * リビジョン行かどうかをチェックします。
     *
     * @param record 読み込んだレコード
     * @return リビジョンの時 true.
     */
    protected static boolean isRevision(String record)
    {
        return record.trim().startsWith(REVISION_HEADER);
    }

    /**
     * 日付レコードから日付を取得します。
     *
     * @param record 日付レコード
     * @return 日付, フォーマットが違うときは null.
     */
    protected static Date getDate(String record)
    {
        SimpleDateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
        try
        {
            return fmt.parse(record);
        }
        catch (ParseException e)
        {
        }
        return null;
    }

    /**
     * クラス名を取得します。
     *
     * @param record
     * @return クラス名, フォーマットが違うときは null.
     */
    protected static String getClassName(String record)
    {
        if (!isClassName(record))
        {
            return null;
        }
        return record.replaceFirst("^" + CLASS_HEADER, "").trim();
    }

    /**
     * リビジョンを取得します。
     *
     * @param record
     * @return リビジョン, フォーマットが違うときは null.
     */
    protected static String getRevision(String record)
    {
        if (!isRevision(record))
        {
            return null;
        }
        return record.replaceFirst("^" + REVISION_HEADER, "").trim();
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
        return "$Id: TraceRecord.java 8075 2014-09-19 07:16:57Z okayama $";
    }

    /**
     * デバッグメイン
     * @param args not used.
     * @throws Exception
     */
    public static void main(String[] args)
            throws Exception
    {
        testWrite();
        testRead();
    }


    private static void testRead()
            throws Exception
    {
        File logf = new File("c:/trace.txt");
        LineNumberReader rdr = null;
        try
        {
            rdr = new LineNumberReader(new FileReader(logf));
            TraceRecord[] traces = readTrace(rdr);

            for (int i = 0; i < traces.length; i++)
            {
                System.out.println(traces[i].getClassName());
            }
        }
        finally
        {
            try
            {
                if (rdr != null)
                {
                    rdr.close();
                }
            }
            catch (IOException e)
            {
                // 失敗した場合は何もしない
            }
        }
    }

    private static void testWrite()
            throws Exception
    {
        File logf = new File("c:/trace.txt");
        PrintWriter prw = new PrintWriter(new FileWriter(logf));
        for (int i = 0; i < 10; i++)
        {
            TraceInfo tri = new TraceInfo(new IOException("DUMMY IO"), TraceRecord.class.getName());
            writeTrace(prw, tri);
            Thread.sleep(600);
        }
        prw.close();

        System.out.println("FIN");
    }
}
