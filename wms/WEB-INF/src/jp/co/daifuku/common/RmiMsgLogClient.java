// $Id: RmiMsgLogClient.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.common;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.IOException;

import jp.co.daifuku.common.text.CSVTokenizer;
import jp.co.daifuku.common.trace.TraceFile;
import jp.co.daifuku.common.trace.TraceInfo;
import jp.co.daifuku.common.trace.TraceWriter;
import jp.co.daifuku.rmi.RmiSendClient;

/**<jp>
 * メッセージログ・サーバに、メッセージを書き込むためのクラスです。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/05/23</TD><TD>sawa</TD><TD>write(int mnum, String ci, Object[] param)メソッド追加(CVS v1.8から)</TD></TR>
 * <TR><TD>2002/06/21</TD><TD>sawa</TD><TD>メッセージ・ログへ書き込むパラメータにnullがセットされた時のバグを修正</TD></TR>
 * <TR><TD>2002/06/27</TD><TD>sawa</TD><TD>警告メッセージログ書き込みメソッド writeWarningMessage(String unsupop) 追加</TD></TR>
 * <TR><TD>2004/01/19</TD><TD>Kawashima</TD><TD>When the RMIMsgServer is down. The write method didn't check size of log. Add check logic at write method.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </jp>*/
/**<en>
 * This class implements the writing of messages in the message log server.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * <TR><TD>2002/05/23</TD><TD>sawa</TD><TD>write(int mnum, String ci, Object[] param) added method(CVS v1.8-)</TD></TR>
 * <TR><TD>2002/06/21</TD><TD>sawa</TD><TD>corrected bug; at setting null for parameter to write in message log</TD></TR>
 * <TR><TD>2002/06/27</TD><TD>sawa</TD><TD>method of writing WarningMessage(String unsupop) added</TD></TR>
 * <TR><TD>2004/01/19</TD><TD>Kawashima</TD><TD>When the RMIMsgServer is down. The write method didn't check size of log. Add check logic at write method.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </en>*/
public class RmiMsgLogClient
        extends Object
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 7996 $,$Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $");
    }

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------
    /**<jp>
     * RMIレジストリ・サーバを指定してメッセージログを書き込みます。
     * リモートのメッセージログ・サーバへの書き込みが失敗した場合は、
     * ローカルのカレント・ディレクトリにログファイルを書き込みます。
     * ログファイル名は、RmiMsgLogServerのデフォルト・ログファイル名です。
     * @param rmireg  RMIレジストリ・サーバ名
     * @param mnum  メッセージ番号
     * @param fac  ファシリティ・コード。詳細は<code>LogMessage</code>クラスに定義されています。
     * @param ci  Class情報
     * @param param メッセージ・ログへ書き込むパラメータ
     * @return   メッセージログ・サーバへの書き込みが成功した場合は true
     * @see    jp.co.daifuku.common.RmiMsgLogServer
     * @see    jp.co.daifuku.common.LogMessage
     </jp>*/
    /**<en>
     * Specifies RMI registry server and writes the message log.
     * If the writing in the remote message log server failed,
     * log file should be written in current directory of local.
     * Log fie name should be the default log file name of RmiMsgLogServer.
     * @param rmireg  RMI registry server name
     * @param mnum  Message number
     * @param fac  Facility code; detail is defined by the class <code>LogMessage</code>.
     * @param ci  Class information
     * @param param Parameter to write in the message log
     * @return   'true' if the writing in message log server succeeded.
     * @see    jp.co.daifuku.common.RmiMsgLogServer
     * @see    jp.co.daifuku.common.LogMessage
     </en>*/
    public static synchronized boolean write(String rmireg, int mnum, String fac, String ci, Object[] param)
    {
        try
        {
            if (!(fac.equals(LogMessage.F_DEBUG)))
            {
                // when param is null, number of param is zero
                int paramlen = 1;
                if (param != null)
                {
                    paramlen += param.length;
                }
                else
                {
                    param = new Object[0];
                }

                RmiSendClient rct = new RmiSendClient(rmireg);
                Object[] sparams = new Object[paramlen + RmiMsgLogServer.N_FIX_PARAM];

                // process id (message log)
                sparams[RmiMsgLogServer.I_PROCESS] = new Integer(RmiMsgLogServer.PROCESS_MSGLOG);
                // Message number
                sparams[RmiMsgLogServer.I_MESSAGE_NUM] = new Integer(mnum);
                // facility code
                sparams[RmiMsgLogServer.I_FACILITY] = fac;
                // class information
                sparams[RmiMsgLogServer.I_CLASS_INFO] = ci;

                // parameters
                for (int i = 0; i < param.length; i++)
                {
                    sparams[i + RmiMsgLogServer.N_FIX_PARAM] = param[i];
                }
                rct.write(RmiMsgLogServer.LOGSERVER_NAME, sparams);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //<jp> リモートが失敗したのでローカルへ書き込み</jp>
            //<en> As remote action failed, it will write in local.</en>
            try
            {
                /*  2004/01/19  MODIFY  M.KAWASHIMA START  */
                File logfile =
                        RmiMsgLogServer.getCurrentLogFile(RmiMsgLogServer.MESSAGELOG_PATH,
                                RmiMsgLogServer.MESSAGELOG_FILENAME);
                MessageLogHandler mh = new MessageLogHandler(logfile);
                //Check file size. If this is over the MESSAGELOG_MAXSIZE, Get new file name.
                if (mh.getFileSize() > RmiMsgLogServer.MESSAGELOG_MAXSIZE)
                {
                    // 2010/07/29 Y.Osawa UPD ST
                    //                    logfile = new File(RmiMsgLogServer.MESSAGELOG_PATH, RmiMsgLogServer.getNewLogFileName());
                    //                    mh = new MessageLogHandler(logfile);
                    // サイズを超えた分はタイムスタンプつきのファイル名にリネーム
                    new File(mh.getLogfile()).renameTo(new File(RmiMsgLogServer.MESSAGELOG_PATH,
                            RmiMsgLogServer.getNewLogFileName()));
                    // 2010/07/29 Y.Osawa UPD ED
                }
                mh.write(mnum, fac, ci, param);
                Object[] wo = new Object[1];
                wo[0] = String.valueOf(e);
                mh.write(6006013, LogMessage.F_ERROR, "RmiMsgLogClient", wo);
                /*  2004/01/19  MODIFY  M.KAWASHIMA END  */
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
            return (false);
        }
        return (true);
    }

    /**<jp>
     * デフォルトのRMIレジストリ・サーバを利用してしてメッセージログを書き込みます。
     * @param mnum  メッセージ番号
     * @param fac  ファシリティ・コード。詳細は<code>LogMessage</code>クラスに定義されています。
     * @param ci  Class情報
     * @param param メッセージ・ログへ書き込むパラメータ
     * @return   メッセージログ・サーバへの書き込みが成功した場合は true
     </jp>*/
    /**<en>
     * Writes message log using the default RMI registory server
     * @param mnum  Message number
     * @param fac  Facility code; detail is defined by class <code>LogMessage</code>.
     * @param ci  Class information
     * @param param Parameter to write in message log.
     * @return   'true' if the writing in the message log server succeeded.
     </en>*/
    public static boolean write(int mnum, String fac, String ci, Object[] param)
    {
        return (write(RmiSendClient.RMI_REG_LOG_SERVER, mnum, fac, ci, param));
    }

    /**<jp>
     * デフォルトのRMIレジストリ・サーバを利用してしてメッセージログを書き込みます。
     * ファシリティ・コードはメッセージ番号から取得します。
     * @param mnum  メッセージ番号
     * @param ci  Class情報
     * @param param メッセージ・ログへ書き込むパラメータ
     * @return   メッセージログ・サーバへの書き込みが成功した場合は true
     </jp>*/
    /**<en>
     * Writes message log using the default RMI registory server.
     * Facility code is retrieved accrding to the message number.
     * @param mnum  Message number
     * @param ci  Class information
     * @param param Parameter to write in the message log
     * @return   'true' if the writing in the message log server succeeded.
     </en>*/
    public static boolean write(int mnum, String ci, Object[] param)
    {
        return (write(RmiSendClient.RMI_REG_LOG_SERVER, mnum, LogMessage.getFacility(mnum), ci, param));
    }

    /**<jp>
     * デフォルトのRMIレジストリ・サーバを利用してしてメッセージログを書き込みます。
     * @param mnum  メッセージ番号
     * @param fac  ファシリティ・コード。詳細は<code>LogMessage</code>クラスに定義されています。
     * @param ci  Class情報
     * @return   メッセージログ・サーバへの書き込みが成功した場合は true
     </jp>*/
    /**<en>
     * Writes message log using the default RMI registory server.
     * @param mnum  Message number
     * @param fac  Facility code; detail is defined by class<code>LogMessage</code>.
     * @param ci  Class information
     * @return   'true' if the writing in the message log server succeeded.
     </en>*/
    public static boolean write(int mnum, String fac, String ci)
    {
        return (write(RmiSendClient.RMI_REG_LOG_SERVER, mnum, fac, ci, null));
    }

    /**<jp>
     * デフォルトのRMIレジストリ・サーバを利用してしてメッセージログを書き込みます。
     * @param mnum  メッセージ番号
     * @param ci  Class情報
     * @return   メッセージログ・サーバへの書き込みが成功した場合は true
     </jp>*/
    /**<en>
     * Writes message log using the default RMI registory server.
     * @param mnum  Message number
     * @param ci  Class information
     * @return   'true' if the writing in the message log server succeeded.
     </en>*/
    public static boolean write(int mnum, String ci)
    {
        return (write(RmiSendClient.RMI_REG_LOG_SERVER, mnum, LogMessage.getFacility(mnum), ci, null));
    }

    /**<jp>
     * デフォルトのRMIレジストリ・サーバを利用してしてメッセージログを書き込みます。<BR>
     * このメソッドはメッセージのパラメータにStackTraceをセットする時にしか使用できません。<BR>
     * メッセージ番号は<CODE>TraceHandler</CODE>オブジェクトから取得します。<BR>
     * ファシリティ・コードはメッセージ番号から取得し、<BR>
     * パラメータには<CODE>TraceHandler</CODE>オブジェクトに保持しているStackTraceをセットします。
     * @param tracehdl <CODE>TraceHandler</CODE>オブジェクト
     * @param ci  Class情報
     * @return   メッセージログ・サーバへの書き込みが成功した場合は true
     </jp>*/
    /**<en>
     * Writes message log using the default RMI registory server.<BR>
     * This method can only be used for setting StackTrace as a message parameter.<BR>
     * Message number will be retrieved from <CODE>TraceHandler</CODE> object.<BR>
     * Facility code will be retrieved by message number and <BR>
     * set StackTrace preserved in <CODE>TraceHandler</CODE> object for the parameter.
     * @param tracehdl <CODE>TraceHandler</CODE> object
     * @param ci  Class information
     * @return   'true' if the writing in the message log server succeeded.
     </en>*/
    public static boolean write(TraceHandler tracehdl, String ci)
    {
        Object[] param = new Object[1];
        param[0] = tracehdl.getStackTrace();
        int mnum = tracehdl.getMessageNumber();
        String fac = LogMessage.getFacility(mnum);
        write(RmiSendClient.RMI_REG_LOG_SERVER, mnum, fac, ci, param);
        // トレースファイルにも出力を行う。
        return (writeTraceOnly(tracehdl.getException(), ci));
    }

    /**<jp>
     * デフォルトのRMIレジストリ・サーバを利用してしてメッセージログを書き込みます。<BR>
     * このメソッドはメッセージのパラメータにStackTraceをセットする時にしか使用できません。<BR>
     * メッセージ番号は<CODE>TraceHandler</CODE>オブジェクトから取得します。<BR>
     * ファシリティ・コードはメッセージ番号から取得し、<BR>
     * パラメータには<CODE>TraceHandler</CODE>オブジェクトに保持しているStackTraceをセットします。
     * @param tracehdl <CODE>TraceHandler</CODE>オブジェクト
     * @param ci  Class情報
     * @param param メッセージ・ログへ書き込むパラメータ
     * @return   メッセージログ・サーバへの書き込みが成功した場合は true
     </jp>*/
    /**<en>
     * Writes message log using the default RMI registory server.<BR>
     * This method can only be used for setting StackTrace as a message parameter.<BR>
     * Message number will be retrieved from <CODE>TraceHandler</CODE> object.<BR>
     * Facility code will be retrieved by message number and <BR>
     * set StackTrace preserved in <CODE>TraceHandler</CODE> object for the parameter.
     * @param tracehdl <CODE>TraceHandler</CODE> object
     * @param ci  Class information
     * @param param Parameter to write in the message log
     * @return   'true' if the writing in the message log server succeeded.
     </en>*/
    public static boolean write(TraceHandler tracehdl, String ci, Object[] param)
    {
        Object[] sparams = new Object[param.length + 1];
        // parameters
        for (int i = 0; i < param.length; i++)
        {
            sparams[i] = param[i];
        }
        sparams[param.length] = tracehdl.getStackTrace();
        int mnum = tracehdl.getMessageNumber();
        String fac = LogMessage.getFacility(mnum);
        write(RmiSendClient.RMI_REG_LOG_SERVER, mnum, fac, ci, sparams);
        // トレースファイルにも出力を行う。
        return (writeTraceOnly(tracehdl.getException(), ci));
    }

    /**<jp>
     * デフォルトのRMIレジストリ・サーバを利用してしてメッセージログを書き込みます。<BR>
     * このメソッドはパラメータ付きメッセージをログに書き込む際に使用します。<BR>
     * メッセージ番号、ファシリティ・コード、パラメータはmsgから取得します。<BR>
     * msgはこのようにセットしてください 例 "70000^9000^1001" -> [メッセージ番号:70000, パラメータ1:9000, パラメータ2:1001]<BR>
     * ^は区切り文字を表します。 デフォルトはタブ(\t)区切り<BR>
     * @param msg ログへ書き込むメッセージ番号＋パラメータ
     * @param ci  Class情報
     * @return   メッセージログ・サーバへの書き込みが成功した場合は true
     </jp>*/
    /**<en>
     * Writes message log using the default RMI registory server.<BR>
     * This method can be used when writing the message with parameter into the log.<BR>
     * Message number, facility code and parameter will be retrieved from msg. <BR>
     * Please set msg as follows. e.x., "70000^9000^1001" -> [mesage number:70000, parameter1:9000, parameter:1001]<BR>
     * ^ signifies delimiters. Default deviding with tab(\t).<BR>
     * @param msg Message number to write in the log plus parameter
     * @param ci  Class information
     * @return   'true' if the writing in the message log server succeeded.
     </en>*/
    public static boolean write(String msg, String ci)
    {
        try
        {
            //Mod 2005/03/14 Kawashima
            //StringTokenizer stk = new StringTokenizer(msg, MessageResource.DELIM, false) ;

            //本来MessageResource.DELIMをcharで再定義するべきですが、影響範囲が大きい。
            //また、このwriteメソッドは将来廃止されることを想定していますので、この形で
            //実装しています。第３引数はあえて使用しない文字\u0000をセットしています。
            CSVTokenizer stk = new CSVTokenizer(msg, MessageResource.DELIM.charAt(0), '\u0000');
            //End 2005/03/14 Kawashima

            Object[] params = new Object[stk.countTokens()];

            //<jp> メッセージ番号</jp>
            //<en> Message number</en>
            int mnum = Integer.parseInt(stk.nextToken());

            //<jp> パラメータ</jp>
            //<en> Parameter</en>
            int i = 0;
            while (stk.hasMoreTokens())
            {
                params[i] = stk.nextToken();
                i++;
            }
            return (write(RmiSendClient.RMI_REG_LOG_SERVER, mnum, LogMessage.getFacility(mnum), ci, params));
        }
        catch (Exception e)
        {
            //<jp>6025001 = メッセージログ・サーバへの書き込みに失敗しました。詳細=({0})</jp>
            //<en>6025001 = Writing in message log server failed. Detail=({0})</en>
            write(new TraceHandler(6025001, e), "RmiMsgLogClient");
            return false;
        }
    }

    /**<jp>
     * デフォルトのRMIレジストリ・サーバを利用してして警告メッセージログを書き込みます。<BR>
     * 現在はメッセージ番号6020047を指定して呼び出し元クラス、推奨されないメソッドをログに書き込みます。
     * 呼び出し元クラス名と推奨されないメソッドを使用されたクラス名は<CODE>SecurityManager</CODE>を生成し
     * 取得します。
     * @param unsupop 推奨されないメソッド名
     * @return        メッセージログ・サーバへの書き込みが成功した場合は true
     * @see jp.co.daifuku.common.MySecurityManager
     </jp>*/
    /**<en>
     * Writes warning message log by using default RMI registory server.<BR>
     * Currently, message number 6020047 is assigned ; former class and unrecommended methods to be
     * written in the log.
     * Former class name (caller) and the other class of the unrecommended method now generates
     * <CODE>SecurityManager</CODE> and retrieves.
     * @param unsupop Unrecommended method name
     * @return        'true' if the writing in the message log server succeeded.e
     * @see jp.co.daifuku.common.MySecurityManager
     </en>*/
    public static boolean writeWarningMessage(String unsupop)
    {
        Class[] stack = getClassContext();
        Object[] param = new Object[2];
        param[0] = stack[4].getName();
        param[1] = unsupop;
        return (write(RmiSendClient.RMI_REG_LOG_SERVER, 6020047, LogMessage.getFacility(6020047), stack[3].getName(),
                param));
    }

    /**<jp>
     * <CODE>SecurityManager</CODE>を生成し<CODE>ClassContext</CODE>を取得します。
     * @retun class情報
     </jp>*/
    /**<en>
     * Generates <CODE>SecurityManager</CODE> and retrieving <CODE>ClassContext</CODE>
     * @return class information.
     </en>*/
    private static Class[] getClassContext()
    {
        System.setSecurityManager(new MySecurityManager());
        SecurityManager securityManager = System.getSecurityManager();
        Class[] stack = ((MySecurityManager)securityManager).getStackTrace();

        return (stack);
    }

    /**
     * 例外スタックトレースをロギングします。
     * ハンドラクラスが使用するメソッドなので、ハンドラクラス以外は使用しないでください。
     * @param ex 例外
     * @param ci クラス情報
     * @return メッセージログ・サーバへの書き込みが成功した場合は true
     */
    public static boolean writeSQLTrace(Exception ex, String ci)
    {
        RmiMsgLogClient.write(new TraceHandler(6006002, ex), ci);
        // TODO データベースエラーとしたいので、上記番号"6006002"に変更しておく
        // ファイルハンドラもこれを使用するのであれば、下のメッセージを使用する。
        // write(6006030, ci);
        return true;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**
     * トレースログをローカル環境に書き込みます。
     * @param tri ロギングするトレース情報
     */
    protected static synchronized void writeTraceLocal(TraceInfo tri)
    {
        File traceFile = TraceFile.getCurrentLogFile(TraceFile.TRACELOG_PATH, TraceFile.TRACELOG_FILENAME);

        try
        {
            TraceWriter.writeOneTrace(traceFile, tri);
        }
        catch (IOException e)
        {
            // this exception will not write to disk.
            e.printStackTrace();
        }
    }

    /**
     * 例外スタックトレースをトレースファイルに書き込みます。
     * @param ex 例外
     * @param ci クラス情報
     * @return メッセージログ・サーバへの書き込みが成功した場合は true
     */
    public static boolean writeTraceOnly(Exception ex, String ci)
    {
        TraceInfo tri = new TraceInfo(ex, ci);

        RmiSendClient rct = new RmiSendClient(RmiSendClient.RMI_REG_LOG_SERVER);

        Object[] sparams = new Object[2];
        sparams[RmiMsgLogServer.I_PROCESS] = new Integer(RmiMsgLogServer.PROCESS_TRACE);
        sparams[RmiMsgLogServer.I_TRACE_TRACEINFO] = tri;
        try
        {
            // RMIサーバに書き込みを依頼
            rct.write(RmiMsgLogServer.LOGSERVER_NAME, sparams);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // ローカルに書き込み
            writeTraceLocal(tri);
        }
        return false;
    }
    // Private methods -----------------------------------------------

}
//end of class
