// $Id: RandomAcsFileHandler.java 8066 2013-08-05 09:57:03Z kishimoto $
package jp.co.daifuku.common;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;


/**<jp>
 * <CODE>RandomAccessFile</CODE>クラスを使用して、ランダムアクセスファイルへの書き込みと、読み込みを行うためのクラスです。<BR>
 * 書き込み時のエンコード形式はUTF-8を使用します。
 * ログ、トレースなどこのクラスを使用してリングバッファにするには、書き込む内容を固定長にする必要があります。
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/03/04</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8066 $, $Date: 2013-08-05 18:57:03 +0900 (月, 05 8 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * This class implements the reading and writing in random access file by useing the class:<CODE>RandomAccessFile</CODE.<BR>
 * Encoding type UTF-8 is used for writing.
 * If creating the ring-buffer using this class as logs and traces, it is required to fix the length of text that could
 * be written in.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/03/04</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8066 $, $Date: 2013-08-05 18:57:03 +0900 (月, 05 8 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class RandomAcsFileHandler
        extends Object
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ログ・ファイルのデリミタ
     * <CODE>MessageLogHandler</CODE>クラスの<CODE>LOG_DELIM</CODE>を使用しています。
     </jp>*/
    /**<en>
     * Delimiter of log file
     * <CODE>LOG_DELIM</CODE> of the class<CODE>MessageLogHandler</CODE> is used.
     </en>*/
    protected static final String LOG_DELIM = MessageLogHandler.LOG_DELIM;

    /**<jp>
     * 日付のフォーマットを行うためのフォーマッタ
     * <CODE>MessageLogHandler</CODE>クラスの<CODE>MSG_DATE_FORMATTER</CODE>を使用しています。
     </jp>*/
    /**<en>
     * Formatter for dates
     * <CODE>MSG_DATE_FORMATTER</CODE> of the class <CODE>MessageLogHandler</CODE> is used.
     </en>*/
//    protected static SimpleDateFormat MSG_DATE_FORMATTER = MessageLogHandler.MSG_DATE_FORMATTER;

    /**<jp>
     * ログ（トレース）ファイルの1行サイズ（byte）
     </jp>*/
    /**<en>
     * Line length of the log (trace) file (byte)
     </en>*/
    public static final int TRACE_LINE_LENGTH = 1024;

    /**<jp>
     * 文字コードがUTF-8のログ（トレース）ファイルの1行サイズ（byte）
     </jp>*/
    /**<en>
     * Line length of the log (trace)file with the character code UTF-8(byte)
     </en>*/
    public static final int TRACE_LINE_LENGTH_UTF = TRACE_LINE_LENGTH + 2;

    /**<jp>
     * トレースファイルの管理部サイズ（byte）
     </jp>*/
    /**<en>
     * Header size of the trace file(byte)
     </en>*/
    public static final int TRACE_CONTROL_LENGTH = 64;

    // Class variables -----------------------------------------------
    /**<jp>
     * ログ（トレース）ファイル名を保持する変数。
     </jp>*/
    /**<en>
     * Variable that preserves the log (trace) file name
     </en>*/
    protected String wLogfileName;

    /**<jp>
     * ログ（トレース）ファイルのMaxサイズを保持する変数。
     * デフォルトは0Byte
     </jp>*/
    /**<en>
     * Variable that preserves the MAX. size of the log (trace) file
     * Default 0 Byte
     </en>*/
    protected int wFileSize = 0;

    /**<jp>
     * ポインタファイル名を保持する変数。
     </jp>*/
    /**<en>
     * Variable that preserves pointer file name
     </en>*/
    //	protected String wPointerfileName ;
    /**<jp>
     * ログ（トレース）ファイルを読み込むための<code>RandomAccessFile</code>
     </jp>*/
    /**<en>
     * <code>RandomAccessFile</code> in order to reade log (trace) file
     </en>*/
    protected RandomAccessFile wRandAcsFReader;

    /**<jp>
     * ログ（トレース）ファイルの1行サイズ（byte）
     </jp>*/
    /**<en>
     * Line length of the log (trace) file (byte)
     </en>*/
    protected int wLineLength = TRACE_LINE_LENGTH;

    /**<jp>
     * 文字コードがUTF-8のログ（トレース）ファイルの1行サイズ（byte）
     </jp>*/
    /**<en>
     * Line length of the log (trace) file with character code UTF-8(byte)
     </en>*/
    protected int wLineLengthUTF = TRACE_LINE_LENGTH_UTF;

    /**<jp>
     * ログ（トレース）ファイルのリードポインタ
     </jp>*/
    /**<en>
     * Read pointer of the log (trace) file
     </en>*/
    protected int wReadPointer = 0;

    /**<jp>
     * ログ（トレース）ファイルリードポインタ
     * 読み開始位置を保持します。次へ前への遷移でも変更されません。
     * 初期化はreadOpenメソッド呼び出し時に行い、初期値はwReadPointer（最終書き込み完了位置）
     * となります。
     </jp>*/
    /**<en>
     * Read pointer of the log (trace) file
     * It preserves the position where to start reading. It will not be changed by transitions
     * such as 'to the next' or 'back to the previous'.
     * Initialization will be performed when readOpen method is called. The initial value
     * shall be wReadPointer (position where the writing to be finalized).
     </en>*/
    protected int wOffsetStartPoint = 0;

    /**<jp>
     * ログ（トレース）ファイルの読み込み開始リードポインタ
     </jp>*/
    /**<en>
     * Read pointer to start reading the log (trace) file
     </en>*/
    protected String wStartReadPointer = null;

    /**<jp>
     * メッセージリソースを保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves the message resource
     </en>*/
    protected MessageResource wMsgRes;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 8066 $,$Date: 2013-08-05 18:57:03 +0900 (月, 05 8 2013) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトのファイルMaxサイズで、メッセージを読み書きするように初期化します。
     * @param logfile 対象のログファイル名
     * @param filesize 許容するログ（トレース）の最大サイズ(byte)
     </jp>*/
    /**<en>
     * Initialization shall be performed in order to read/write messages
     * to the file's MAX size in default.
     * @param logfile Objected log file name
     * @param filesize Acceptable MAX size of the log (trace) (byte)
     </en>*/
    public RandomAcsFileHandler(String logfile, int filesize)
    {
        wLogfileName = logfile;
        wFileSize = filesize;
        wMsgRes = new MessageResource(Locale.getDefault());
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * サイズを取得します。
     *
     </jp>*/
    /**<en>
     * Retrieves the size.
     *
     </en>*/
    public int getSize()
    {
        int size = 0;
        try
        {
            // close stream if opened
            if (wRandAcsFReader != null)
            {
                this.readClose();
            }
            //<jp>読み込みファイルオブジェクト</jp>
            //<en>Reading file object</en>
            File f = new File(wLogfileName);
            wRandAcsFReader = new RandomAccessFile(f, "r");

            //<jp>最大件数</jp>
            //<en>MAX. total number of communication trace and message log file</en>
            size = ((int)wRandAcsFReader.length() - TRACE_CONTROL_LENGTH) / TRACE_LINE_LENGTH;
        }
        catch (Exception e)
        {
        }
        finally
        {
            readClose();
        }
        return size;
    }

    /**<jp>
     * 文字コードがUTF-8の場合のファイルサイズを取得します。
     *
     </jp>*/
    /**<en>
     * Retrieves the file size when character code UTF-8 is used.
     *
     </en>*/
    public int getSizeUTF()
    {
        int size = 0;
        try
        {
            // close stream if opened
            if (wRandAcsFReader != null)
            {
                this.readClose();
            }
            //<jp>読み込みファイルオブジェクト</jp>
            //<en>Reading file object</en>
            File f = new File(wLogfileName);
            wRandAcsFReader = new RandomAccessFile(f, "r");

            //<jp>最大件数</jp>
            //<en>MAX. total number of communication trace and message log file</en>
            size = ((int)wRandAcsFReader.length() - TRACE_CONTROL_LENGTH) / TRACE_LINE_LENGTH_UTF;
        }
        catch (Exception e)
        {
            //<jp> エラーがあった場合は-1を返す</jp>
            //<en> It returns '-1' if error was found.</en>
            size = -1;
        }
        finally
        {
            readClose();
        }
        return size;
    }


    /**<jp>
     * メッセージを書き込みます。メッセージは、リソースとパラメータ・配列から
     * フォーマットされ、ファイルに書き込まれます。
     * このとき、パラメータの数が合わない場合は、パラメータ情報が捨てられるか、
     * 埋め込みが完了していない状態でログが書き込まれます。
     * ログ・ファイルには、パラメータがデリミタ・文字で区切られた文字列として格納されます。
     * @param msgnum  メッセージ番号
     * @param fac  ファシリティ・コード
     * @param ci  Class情報
     * @param param  パラメータ・配列
     * @throws IOException           ログ(トレース)ファイルに書込みが失敗した時に通知されます。
     * @throws NumberFormatException ポインタファイルを読み込み、ポインタ値が異常の場合通知されます。
     </jp>*/
    /**<en>
     * It writes the message. Message will be formatted with resource and parameter array, then
     * will be written in the file.
     * If the number of parameter do not match, data of this parameter will be either abondonned
     * or the writing of the log will be proceeded while embedding be incomplete.
     * In the log file, parameter will be stored in form of string devided by delimiter character.
     * @param msgnum  Message number
     * @param fac  Facility code
     * @param ci  Class information
     * @param param  Parameter array
     * @throws IOException           It notifies when it failed to write in log (trace) file.
     * @throws NumberFormatException It notifies when it reads pointer file and the pointer value was abnormal.
     </en>*/
    public synchronized void write(int msgnum, String fac, String ci, Object[] param)
            throws IOException,
                NumberFormatException
    {
        // create RandomAccessFile object
        File f = new File(wLogfileName);
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        // get the writing pointer
        String wp = this.getWritePointer(raf);
        // create msg parameter string
        //		StringBuffer msgbuf = new StringBuffer(256) ;
        // 2009/08/06 UPDATE K.Mori
        // 能力UPのため、StringBufferからStringBuidlerに変更し、初期バッファサイズを1024にする。
        StringBuilder msgbuf = new StringBuilder(1024);

        // date first
        Date ndate = new Date();
        msgbuf.append(new SimpleDateFormat(MessageLogHandler.DATE_FORMAT).format(ndate));

        // message number
        msgbuf.append(LOG_DELIM + Integer.toString(msgnum));

        // facility code
        msgbuf.append(LOG_DELIM + fac);

        // class information
        msgbuf.append(LOG_DELIM + ci);

        // set parameters to string buffer
        if (param != null)
        {
            for (int i = 0; i < param.length; i++)
            {
                if (param[i] != null)
                {
                    // remove delimiter charactor from parameter and triming space char.
                    String pst =
                            param[i].toString().replace(LOG_DELIM.charAt(0), ' ').replace('\n', ' ').replace('\r', ' ').trim();
                    msgbuf.append(LOG_DELIM + pst);
                }
            }
        }

        // write parameter string to logfile
        String tmpmsg = msgbuf.toString();
        //<jp> ファイルの1行サイズ分スペースを付加</jp>
        //<en> It adds space of a line in that file</en>
        for (int i = 0; i < wLineLength; i++)
        {
            tmpmsg += " ";
        }
        String pmsg = tmpmsg.substring(0, wLineLength - 1) + "\n";

        // set the writing pointer and write in a RandomAccessFile
        raf.seek(Integer.parseInt(wp));
        //		raf.skipBytes(Integer.parseInt(pointer)) ;
        raf.writeBytes(pmsg);
        // set the writing pointer
        int pointer = (int)raf.getFilePointer();
        this.setWritePointer(pointer, raf);

        // close stream
        raf.close();

        // 2009/08/06 Add Start K.Mori
        // 書込み位置がファイルの最大サイズを超えていた場合、リネームする。
        if (pointer >= this.getFileSize())
        {
            logRename();
        }
        // 2009/08/06 Add End
    }

    /**<jp>
     * メッセージを書き込みます。メッセージは、リソースとパラメータ・配列から
     * フォーマットされ、ファイルに書き込まれます。
     * このとき、パラメータの数が合わない場合は、パラメータ情報が捨てられるか、
     * 埋め込みが完了していない状態でログが書き込まれます。
     * ログ・ファイルには、パラメータがデリミタ・文字で区切られた文字列として格納されます。
     * 書込みはＵＴＦにて書込みを行います。
     * @param msgnum  メッセージ番号
     * @param fac  ファシリティ・コード
     * @param ci  Class情報
     * @param param  パラメータ・配列
     * @throws IOException           ログ(トレース)ファイルに書込みが失敗した時に通知されます。
     * @throws NumberFormatException ポインタファイルを読み込み、ポインタ値が異常の場合通知されます。
     </jp>*/
    /**<en>
     * Writes the message. Message will be formatted with resource and parameter array, then
     * will be written in the file.
     * If the number of parameter do not match, data of this parameter will be either abondonned
     * or the writing of the log will be proceeded while embedding be incomplete.
     * In the log file, parameter will be stored in form of string devided by delimiter character.
     * Writing will be performed by UTF.
     * @param msgnum  Message number
     * @param fac  Facility code
     * @param ci  Class information
     * @param param  Parameter array
     * @throws IOException           It notifies when it failed to write in log (trace) file.
     * @throws NumberFormatException It notifies when it reads pointer file and the pointer value was abnormal.
     </en>*/
    public synchronized void writeUTF(int msgnum, String fac, String ci, Object[] param)
            throws IOException,
                NumberFormatException
    {
        // create RandomAccessFile object
        File f = new File(wLogfileName);
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        // get the writing pointer
        String wp = this.getWritePointer(raf);
        // create msg parameter string
        StringBuffer msgbuf = new StringBuffer(256);

        //<jp> 3バイト文字数を保持する変数</jp>
        //<en> Variable that preserves number of 3 bytes character</en>
        int charCount = 0;

        // date first
        Date ndate = new Date();
        msgbuf.append(new SimpleDateFormat(MessageLogHandler.DATE_FORMAT).format(ndate));

        // message number
        msgbuf.append(LOG_DELIM + Integer.toString(msgnum));

        // facility code
        msgbuf.append(LOG_DELIM + fac);

        // class information
        msgbuf.append(LOG_DELIM + ci);

        // set parameters to string buffer
        if (param != null)
        {
            for (int i = 0; i < param.length; i++)
            {
                if (param[i] != null)
                {
                    // remove delimiter charactor from parameter and triming space char.
                    String pst = param[i].toString();
                    msgbuf.append(LOG_DELIM + pst);
                }
            }
        }

        // write parameter string to logfile
        String tmpmsg = msgbuf.toString();

        //<jp> 3バイト文字(日本語)の数を数える</jp>
        //<en> It counts the 3-byte characters (Japanese).</en>
        for (int i = 0; i < tmpmsg.length(); i++)
        {
            char charBuf = tmpmsg.charAt(i);

            //<jp> 3バイト文字かどうかを判別</jp>
            //<en> It identifies whether/not it is 3-byte character</en>
            if (charBuf >= 0x800 && charBuf <= 0xFFFF)
            {
                charCount++;

                // 半角カナ文字 or 全角アルファベット
                if ((charBuf >= 0xFF61) & (charBuf <= 0xFF9F))
                {
                    charCount++;
                }
            }
        }

        //<jp> 3バイト文字の個数分だけ1行分のサイズから引く</jp>
        //<en> Shortening the line length by subtracting the number of 3-byte characters.</en>
        wLineLength = wLineLength - charCount;

        //<jp> ファイルの1行サイズ分のスペースデータを作る</jp>
        //<en> Creating the space data equivalent to the line length in that file.</en>
        byte[] buf = new byte[wLineLength];

        for (int i = 0; i < wLineLength; i++)
        {
            buf[i] = ' ';
        }
        //<jp> 最後の1バイトに改行を入れる</jp>
        //<en> Entering linefeed in the last 1 byte.</en>
        buf[wLineLength - 1] = '\n';

        //<jp> wLineLengthを初期化</jp>
        //<en> Initialization of wLineLength</en>
        wLineLength = TRACE_LINE_LENGTH;

        //<jp> 格納データをbyte型で作成</jp>
        //<en> Creating the storing data of byte style.</en>
        byte[] data = tmpmsg.getBytes();

        //<jp> スペースデータに格納データを上書き</jp>
        //<en> Overwriting the storing data on the space data.</en>
        for (int j = 0; j < data.length; j++)
        {
            buf[j] = data[j];
        }

        //<jp> byte型をString型に変換</jp>
        //<en> Translation from byte style to String style</en>
        String pmsg = new String(buf);

        // set the writing pointer and write in a RandomAccessFile
        raf.seek(Integer.parseInt(wp));
        raf.writeUTF(pmsg);
        // set the writing pointer
        this.setWritePointer((int)raf.getFilePointer(), raf);

        // close stream
        raf.close();
    }

    /**<jp>
     * ログ(トレース)ファイルが存在するかを確認します。
     * @return ファイルが存在しない場合はfalseを返します。
     * @throws IOException ファイル読み込み時の異常を通知されます。
     </jp>*/
    /**<en>
     * Verifies whether/not there is the corresponding log (trace) file.
     * @return It returns 'false' if there is no such file.
     * @throws IOException It notifies the abnormality in the reading of file.
     </en>*/
    public boolean isFileExist()
            throws IOException
    {
        //<jp>読み込みファイルオブジェクト</jp>
        //<en>Reading file object</en>
        File f = new File(this.getLogfile());
        //<jp>ファイルが存在しない場合</jp>
        //<en>If there is no such file</en>
        if (!f.exists())
        {
            return false;
        }
        return true;
    }

    /**<jp>
     * ログ(トレース)ファイルを読み込むためにオープンします。
     * @return ファイルが存在しない場合はfalseを返します。
     * @throws IOException ファイル読み込み時の異常を通知されます。
     </jp>*/
    /**<en>
     * Opens the log (trace) file for reading.
     * @return Returns 'false' if there is no such file.
     * @throws IOException Notifies the abnormality in the reading of file.
     </en>*/
    public boolean readOpen()
            throws IOException
    {
        return readOpen(true);
    }

    /**<jp>
     * ログ(トレース)ファイルを読み込むためにオープンします。
     * @param readFlag wOffsetStartPointの初期化を行うときはtrueを指定します。
     * @return ファイルが存在しない場合はfalseを返します。
     * @throws IOException ファイル読み込み時の異常を通知されます。
     </jp>*/
    /**<en>
     * Opens log (trace) file to read.
     * @param readFlag Should specify 'true' if wOffsetStartPoint to be initialized.
     * @return It returns 'false' if there is no such file.
     * @throws IOException It notifies the abnormality in the reading of file.
     </en>*/
    public boolean readOpen(boolean readFlag)
            throws IOException
    {
        // close stream if opened
        if (wRandAcsFReader != null)
        {
            readClose();
        }
        //<jp>読み込みファイルオブジェクト</jp>
        //<en>Reading file object</en>
        File f = new File(this.getLogfile());
        //<jp>ファイルが存在しない場合</jp>
        //<en>If the file does not reside</en>
        if (!f.exists())
        {
            return false;
        }

        wRandAcsFReader = new RandomAccessFile(f, "r");

        //<jp>最終書き込み位置</jp>
        //<en>Position where the writing should be finalized</en>
        wReadPointer = Integer.parseInt(this.getWritePointer(wRandAcsFReader)) - wLineLength;

        //<jp>wOffsetStartPoint初期化</jp>
        //<en>Initialization of wOffsetStartPoint</en>
        if (readFlag)
        {
            wOffsetStartPoint = wReadPointer;
        }
        return true;
    }

    /**<jp>
     * 文字コードがUTF-8のログ(トレース)ファイルを読み込むためにオープンします。
     * @return ファイルが存在しない場合はfalseを返します。
     * @throws IOException ファイル読み込み時の異常を通知されます。
     </jp>*/
    /**<en>
     * Opens log (trace) file with character code UTF-8 to read.
     * @return It returns 'false' if there is no such file.
     * @throws IOException It notifies the abnormality in reading file.
     </en>*/
    public boolean readOpenUTF()
            throws IOException
    {
        return readOpenUTF(true);
    }

    /**<jp>
     * 文字コードがUTF-8のログ(トレース)ファイルを読み込むためにオープンします。
     * @param readFlag wOffsetStartPointの初期化を行うときはtrueを指定します。
     * @return ファイルが存在しない場合はfalseを返します。
     * @throws IOException ファイル読み込み時の異常を通知されます。
     </jp>*/
    /**<en>
     * Opens log (trace) file of character code UTF-8 to read.
     * @param readFlag Need to sprcify 'true' if initializing wOffsetStartPoint.
     * @return If there is no file, it returns 'false'.
     * @throws IOException It notifies the abnormality in reading file.
     </en>*/
    public boolean readOpenUTF(boolean readFlag)
            throws IOException
    {
        // close stream if opened
        if (wRandAcsFReader != null)
        {
            readClose();
        }
        //<jp>読み込みファイルオブジェクト</jp>
        //<en>Reading file object</en>
        File f = new File(this.getLogfile());
        //<jp>ファイルが存在しない場合</jp>
        //<en>If there is no such file</en>
        if (!f.exists())
        {
            return false;
        }

        wRandAcsFReader = new RandomAccessFile(f, "r");

        //<jp>最終書き込み位置</jp>
        //<en>Where the final writing should go</en>
        wReadPointer = Integer.parseInt(this.getWritePointer(wRandAcsFReader)) - wLineLengthUTF;

        //<jp>wOffsetStartPoint初期化</jp>
        //<en>Initializaiton of wOffsetStartPoint</en>
        if (readFlag)
        {
            wOffsetStartPoint = wReadPointer;
        }
        return true;
    }


    /**<jp>
     * 読み込みのためにオープンされているログ(トレース)ファイルをクローズします。
     </jp>*/
    /**<en>
     * Closes the log (trace) file opened for reading process.
     </en>*/
    public void readClose()
    {
        try
        {
            if (wRandAcsFReader != null)
            {
                wRandAcsFReader.close();
            }
        }
        catch (Exception e)
        {
        }
        wRandAcsFReader = null;
    }

    /**<jp>
     * ログ・ファイルから次の行のメッセージを読み込みます。
     * このメソッドでは、１行読込後にポインタ位置を自動的に次の読込位置へ変更します。
     * @return  ログ・メッセージ。最後まで読み込みが完了している場合は、null
     * @throws IOException ファイル読み込み時の異常を通知されます。
     </jp>*/
    /**<en>
     * Reads message of next line in the log file.
     * In this method, pointer should move automatically to the next line as it finished reading
     * each line.
     * @return  Log message In case the reading is completed, 'null' will be provided.
     * @throws IOException It notifies the abnormality in reading file.
     </en>*/
    public LogMessage readNext()
            throws IOException
    {
        int cuurentlen = (int)wRandAcsFReader.length();
        //<jp> 現在のファイルサイズ >= 指定したMaxファイルサイズでかつ
        // リード・ポインタが0より小さい場合
        // ファイルの最後の行にポインタを移動</jp>
        //<en> If current file size is equal to the specified MAX. file size or greater and
        // If read pointer is less than 0,
        // it moves the pointer to the last line of the file.</en>
        if (cuurentlen >= this.getFileSize() && wReadPointer < 0)
        {
            wReadPointer = cuurentlen - wLineLength;
        }
        //<jp> 現在のファイルサイズ < 指定したMaxファイルサイズでかつ
        // リード・ポインタが0より小さい場合
        // ファイルにWriteされている全データの読み込みが完と判断する</jp>
        //<en> If Current file size is smaller than specified MAX file size and
        // If read pointer is less than 0,
        // it determines that reading of all data written in the file has been completed.</en>
        else if (cuurentlen < this.getFileSize() && wReadPointer < 0)
        {
            return (null);
        }

        //      //<jp> 読込開始リード・ポインタがNULLの場合
        //		// リード・ポインタを読込開始リード・ポインタにセット</jp>
        //      //<en> If the read pointer for start-reading is 'NULL'
        //		// Setting the read pointer to the start-reading</en>
        //		if(wStartReadPointer == null)
        //		{
        //			wStartReadPointer = Integer.toString(wReadPointer);
        //		}
        //      //<jp> 読込開始リード・ポインタ==リード・ポインタの場合
        //		// すべてのデータを読み込んだど判断する。</jp>
        //      //<en> If start-reading pointer = Read pointer,
        //		// it determines all data has been read.</en>
        //		else if(wReadPointer == Integer.parseInt(wStartReadPointer))
        //		{
        //System.out.println("wStartReadPointer="+wStartReadPointer);
        //			return (null) ;
        //		}

        // moving the writing pointer
        wRandAcsFReader.seek(wReadPointer);
        // read 1 line from logfile (format parameter)
        String smsg = wRandAcsFReader.readLine();

        // check end of file
        if (smsg == null)
        {
            return (null);
        }

        wReadPointer = wReadPointer - (smsg.length() + 1);

        StringTokenizer stk = new StringTokenizer(smsg, LOG_DELIM, false);
        // getting date
        String wst = stk.nextToken();
        Date dt = new SimpleDateFormat(MessageLogHandler.DATE_FORMAT).parse(wst, new ParsePosition(0));
        // getting message number
        wst = stk.nextToken();
        int msgnum = Integer.parseInt(wst);
        // getting facility code
        String fac = stk.nextToken();
        // getting class info
        String ci = stk.nextToken();

        // getting parameter objects
        String[] params = new String[stk.countTokens()];
        for (int i = 0; i < params.length; i++)
        {
            params[i] = stk.nextToken();
        }
        // getting message format string from resource
        //<jp> メッセージNoが0のものが存在するので、0の時も表示</jp>
        //<en> As message number '0' also in use, indication for 0 should be included.</en>
        if (msgnum == 0 || msgnum == 1)
        {
            String msgstr = "";
            for (int i = 0; i < params.length; i++)
            {
                msgstr = msgstr + params[i];
            }
            return (new LogMessage(dt, msgnum, fac, ci, msgstr));
        }
        else
        {
            String msgstr = wMsgRes.getMessage(msgnum, params);

            return (new LogMessage(dt, msgnum, fac, ci, msgstr));
        }
    }

    /**<jp>
     * 文字コードがUTF-8のログ・ファイルから次の行のメッセージを読み込みます。
     * このメソッドでは、１行読込後にポインタ位置を自動的に次の読込位置へ変更します。
     * @return  ログ・メッセージ。最後まで読み込みが完了している場合は、null
     * @throws IOException ファイル読み込み時の異常を通知されます。
     </jp>*/
    /**<en>
     * Reads the message of the following line in the log file of character code UTF-8.
     * In this method, pointer should move automatically to the next line as it finished reading
     * each line.
     * @return  Log message or 'null' if all reading is finished.
     * @throws IOException It notifies the abnormality in reading file.
     </en>*/
    public LogMessage readNextUTF()
            throws IOException
    {
        //<jp> 3バイト文字数を保持する変数</jp>
        //<en> Variable preserving the number of 3-byte characters</en>
        int charCount = 0;

        int cuurentlen = (int)wRandAcsFReader.length();
        //<jp> 現在のファイルサイズ >= 指定したMaxファイルサイズでかつ
        // リード・ポインタが0より小さい場合
        // ファイルの最後の行にポインタを移動</jp>
        //<en> If current file size is equal to the specified MAX file size or greater and
        // if read pointer is less than 0,
        // it should move hte pointer to the last line of that file.</en>
        if (cuurentlen >= this.getFileSize() && wReadPointer < 0)
        {
            wReadPointer = cuurentlen - wLineLengthUTF;
        }
        //<jp> 現在のファイルサイズ < 指定したMaxファイルサイズでかつ
        // リード・ポインタが0より小さい場合
        // ファイルにWriteされている全データの読み込みが完と判断する</jp>
        //<en> If current file size is smaller than specified MAX file size and
        // if read pointer is less than 0,
        // it determines that reading of all written data in this file has been completed.</en>
        else if (cuurentlen < this.getFileSize() && wReadPointer < 0)
        {
            return (null);
        }

        // <jp> 読込開始リード・ポインタがNULLの場合
        // リード・ポインタを読込開始リード・ポインタにセット</jp>
        // <en>If the read pointer for start-reading is NULL,
        // it should set the read pointer for start-reading.</en>
        if (wStartReadPointer == null)
        {
            wStartReadPointer = Integer.toString(wReadPointer);
        }
        // <jp> 読込開始リード・ポインタ==リード・ポインタの場合
        // すべてのデータを読み込んだど判断する。</jp>
        // <en>If start-reading read pointer = read pointer,
        // it determines that all data has been read.</en>
        else if (wReadPointer == Integer.parseInt(wStartReadPointer))
        {
            return (null);
        }

        // moving the writing pointer
        wRandAcsFReader.seek(wReadPointer);
        // read 1 line from logfile (format parameter)
        String smsg = wRandAcsFReader.readUTF();

        // check end of file
        if (smsg == null)
        {
            return (null);
        }

        //<jp> 3バイト文字(日本語)の数を数える</jp>
        //<en> Counting the number of 3-byte characters(Japanese).</en>
        for (int i = 0; i < smsg.length(); i++)
        {
            char charBuf = smsg.charAt(i);

            //<jp> 3バイト文字かどうかを判別</jp>
            //<en> Identifying whether/not it is 3-byte character</en>
            if (charBuf >= 0x800 && charBuf <= 0xFFFF)
            {
                charCount++;

                // 半角カナ or 全角アルファベット
                if ((charBuf >= 0xFF61) & (charBuf <= 0xFF9F))
                {
                    charCount++;
                }
            }
        }

        //<jp> 文字列のバイトサイズをとる</jp>
        //<en> Retrieving the byte size of the string</en>
        byte[] size = smsg.getBytes();

        //wReadPointer = wReadPointer - (smsg.length() + 1) ;
        wReadPointer = wReadPointer - (size.length + charCount + 2);

        StringTokenizer stk = new StringTokenizer(smsg, LOG_DELIM, false);
        // getting date
        String wst = stk.nextToken();
        Date dt = new SimpleDateFormat(MessageLogHandler.DATE_FORMAT).parse(wst, new ParsePosition(0));
        // getting message number
        wst = stk.nextToken();
        int msgnum = Integer.parseInt(wst);
        // getting facility code
        String fac = stk.nextToken();
        // getting class info
        String ci = stk.nextToken();

        // getting parameter objects
        String[] params = new String[stk.countTokens()];
        for (int i = 0; i < params.length; i++)
        {
            params[i] = stk.nextToken();
        }
        // getting message format string from resource
        //<jp> メッセージNoが0のものが存在するので、0の時も表示</jp>
        //<en> As message number includes '0', it should indicate the case of 0 as well.</en>
        if (msgnum == 0 || msgnum == 1)
        {
            String msgstr = "";
            for (int i = 0; i < params.length; i++)
            {
                msgstr = msgstr + params[i];
            }
            return (new LogMessage(dt, msgnum, fac, ci, msgstr));
        }
        else
        {
            String msgstr = wMsgRes.getMessage(msgnum, params);

            return (new LogMessage(dt, msgnum, fac, ci, msgstr));
        }
    }

    /**<jp>
     * ログ・ファイルから次の行のメッセージを読み込みます。
     * @return  ログ・メッセージ。最後まで読み込みが完了している場合は、null
     * @throws IOException ファイル読み込み時の異常を通知されます。
     </jp>*/
    /**<en>
     * Reads message of the next line from the log file.
     * @return  Log message. NULL if all the reading is complete.
     * @throws IOException It notifies the abnormality in reading file
     </en>*/
    public Vector read(int start, int end)
            throws IOException
    {
        System.out.println("----- read -----");
        System.out.println("start=" + start + " end=" + end);
        Vector vec = new Vector(100);
        int loopcount = end - start;
        int cuurentlen = (int)wRandAcsFReader.length();
        wReadPointer = wOffsetStartPoint - start * wLineLength;
        //<jp>ポインタがヘッダーサイズよりも小さい場合、読込範囲がファイルの最後
        //をまたがっていることを意味する。この時は正確な読込開始位置を計算する</jp>
        //<en>In case the pointer is smaller than the header size, it means that specified
        //range to read is exceeding the size of the file. If this happens,
        //the starting position should be correctly calculated.</en>
        if (wReadPointer < TRACE_CONTROL_LENGTH)
        {
            wReadPointer = (cuurentlen) + (wReadPointer - TRACE_CONTROL_LENGTH);
        }

        for (int i = 0; i < loopcount; i++)
        {

            LogMessage logmsg = readNext();
            if (logmsg != null)
            {
                vec.add(logmsg);
            }
            else
            {
                break;
            }
        }
        return vec;
    }

    /**<jp>
     * 文字コードがUTF-8のログ・ファイルから次の行のメッセージを読み込みます。
     * @return  ログ・メッセージ。最後まで読み込みが完了している場合は、null
     * @throws IOException ファイル読み込み時の異常を通知されます。
     </jp>*/
    /**<en>
     * It shall read the message of next line in the log file of character code UTF-8.
     * @return  Log message  'null' is given if all the reading has been completed.
     * @throws IOException It notifies the abnormality in reading file.
     </en>*/
    public Vector readUTF(int start, int end)
            throws IOException
    {
        Vector vec = new Vector(100);
        int loopcount = end - start;
        int cuurentlen = (int)wRandAcsFReader.length();
        wReadPointer = wOffsetStartPoint - start * wLineLengthUTF;

        //<jp>ポインタがヘッダーサイズよりも小さい場合、読込範囲がファイルの最後
        //をまたがっていることを意味する。この時は正確な読込開始位置を計算する</jp>
        //<en>In case the pointer is smaller than the header size, it means that data
        //range of reading is crossing the last part of files. If this happens,
        //the starting position should be correctly calculated.</en>
        if (wReadPointer < TRACE_CONTROL_LENGTH)
        {
            wReadPointer = (cuurentlen) + (wReadPointer - TRACE_CONTROL_LENGTH);
        }

        for (int i = 0; i < loopcount; i++)
        {
            LogMessage logmsg = readNextUTF();
            if (logmsg != null)
            {
                vec.add(logmsg);
            }
            else
            {
                break;
            }
        }
        return vec;
    }

    /**<jp>
     * ログ・ファイルから次の行のメッセージを読み込みます。
     * readメソッドで読み込んだVectorの順序を逆にして返します。
     *
     * @return  ログ・メッセージ。最後まで読み込みが完了している場合は、null
     * @throws IOException ファイル読み込み時の異常を通知されます。
     </jp>*/
    /**<en>
     * Reads the message of next line from the log file.
     * It returns Vector, which was read in the method, but in reverse order.
     *
     * @return  Log message  null if all reading has been completed.
     * @throws IOException It notifies the abnormality in reading file.
     </en>*/
    public Vector readBack(int start, int end)
            throws IOException
    {
        Vector vec = read(start, end);
        Vector ret = new Vector(100);

        for (int i = 0; i < vec.size(); i++)
        {
            int index = (vec.size() - 1) - i;
            ret.addElement(vec.elementAt(index));
        }
        return ret;
    }

    // Public accsesser methods --------------------------------------
    /**<jp>
     * 現在使用中のログ・ファイル名を取得します。
     * @return    ログ・ファイル名
     </jp>*/
    /**<en>
     * Retrieves the log file name currently in use.
     * @return    log file name
     </en>*/
    public String getLogfile()
    {
        return (wLogfileName);
    }

    /**<jp>
     * 現在使用中のログ（トレース）ファイルのMaxサイズを取得します。
     * @return    ログ（トレース）ファイルのMaxサイズ
     </jp>*/
    /**<en>
     * Retrieves the MAX. size of log (trace) file currently in use.
     * @return    MAX. size of log (trace) file
     </en>*/
    public int getFileSize()
    {
        return (wFileSize);
    }

    public String getWritePointer(RandomAccessFile raFile)
            throws IOException
    {
        String pointer = "0";
        try
        {
            raFile.seek(0);
            // read 1 line from logfile (format parameter)
            pointer = raFile.readLine();

            //<jp> ファイルが存在しない場合</jp>
            //<en> If there is no such file,</en>
            if (pointer == null)
            {
                setWritePointer(TRACE_CONTROL_LENGTH, raFile);
                return Integer.toString(TRACE_CONTROL_LENGTH);
            }
        }
        catch (FileNotFoundException fe)
        {
            //<jp> ポインタファイルが存在しない場合新規作成</jp>
            //<en> If there is no pointer file, it should create a new file.</en>
            this.setWritePointer(TRACE_CONTROL_LENGTH, raFile);
        }

        return pointer.trim();
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    /**<jp>
     * ポインタ・ファイルを読み込みライト・ポインタを取得します。
     * @return    ライト・ポインタ
     * @throws IOException ファイル読み込み時の異常を通知されます。
     </jp>*/
    /**<en>
     * It reads the pointer file and retrieves the write pointer.
     * @return    Write pointer
     * @throws IOException It notifies the abnormality in reading file.
     </en>*/
    private void setWritePointer(int size, RandomAccessFile raFile)
            throws IOException
    {
        if (size >= this.getFileSize())
        {
            size = TRACE_CONTROL_LENGTH;

            //			// トレースログファイルが存在する場合、履歴保存用にトレースログをリネームする。
            //			// 新規作成時は何もしない。
            //			if (new File(wLogfileName).exists())
            //			{
            //				logRename(raFile);
            //			}
        }

        String pointermsg = fillChar(TRACE_CONTROL_LENGTH - 1, Integer.toString(size));
        raFile.seek(0);
        raFile.writeBytes(pointermsg + "\n");
    }

    // 2009/08/06 Add Start K.Mori
    /**<jp>
     * トレースログファイルのリネームを行い、日付(YYYYMMDDHHMISS)つきのファイルに変更します。
     * トレースログの履歴保存用に追加したメソッドです。
     </jp>*/
    private void logRename()
    {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date();
        String dateString = formatter.format(curDate);

        String newFname = wLogfileName + dateString;
        File newFile = new File(newFname);
        // ログを日付きでリネームし、退避する。
        if (new File(wLogfileName).renameTo(newFile))
        {
            System.out.println("As21 Trace file rename was sucessful [" + newFname + "]");
        }
        else
        {
            System.out.println("As21 Trace file rename was fail [" + newFname + "]");
        }

    }

    // 2009/08/06 Add End

    /**<jp>
     * 引数で指定された文字列項目を整形します。
     * 桁数を5、項目を"ABC"と指定すると"ABC  "を返します。
     * @param  digit 桁数
     * @param  str 項目
     * @return 整形された文字列
     * @throws InvalidStatusException 項目の長さが規定している桁数を超えたときにスローされます
     </jp>*/
    /**<en>
     * Fixing the specified string item by arguments.
     * It returns "ABC" IF the 5 digits and item "ABC" is assigned.
     * @param  digit number of digit
     * @param  str item
     * @return Adjusted string
     * @throws InvalidStatusException It throws when the length of item exceeded the
     * the regulated number of digits.
     </en>*/
    private String fillChar(int digit, String str)
            throws IOException
    {
        //<jp>項目の長さを取得（バイト数）</jp>
        //<en>Retrieving the length of the item (byte)</en>
        int str_length = getLength(str);

        if (digit - str_length >= 0)
        {
            return str + makeSpace(digit - str_length);
        }
        //<jp>桁数を項目の長さが超えた時は例外をスローする。</jp>
        //<en>Exception will be thrown if item length exceeds the number of digits.</en>
        else
        {
            throw (new IOException("The length of Pointer was over the TRACE_CONTROL_LENGTH"));
        }
    }

    /**<jp>
     * 引数で指定された長さのスペースを返します。
     * 引数で５を指定すると"     "を返します。
     * @param  val　スペースの数
     * @return 引数で指定された長さのスペース
     </jp>*/
    /**<en>
     * It returns spaces with the length given by argument.
     * If argument gives 5, it returns "     ".
     * @param  val Number of spaces
     * @return Spaces of the length given by argument.
     </en>*/
    private String makeSpace(int val)
    {
        String buf = "";

        for (int i = 0; i < val; i++)
        {
            buf = buf + " ";
        }
        return buf;
    }

    /**<jp>
     * 文字の長さ取得します。<BR>
     * 全角文字もstr.length()では1文字と数えてしまうのでバイト数を返します。
     * @param str
     * @return バイト数
     </jp>*/
    /**<en>
     * It retrieves the length of characters.<BR>
     * It returns number of byte for double-size character since they are counted 1 by str.length().
     * @param str
     * @return number of byte
     </en>*/
    private int getLength(String str)
    {
        byte[] b = str.getBytes();
        return b.length;
    }
}
//end of class

