// $Id: VariableFileHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.RecordFormatException;

/**
 * 可変長ファイルのためのファイルハンドラです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class VariableFileHandler
        extends AbstractFileHandler
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private BufferedReader _reader;

    private BufferedWriter _writer;

    /**
     * 初期フラグ<br>
     * デフォルトでオーバーライトモードとなるファイルハンドラで
     * トランケートするタイミングを得るために使用します。
     */
    private boolean _first = true;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ディレクトリ指定をシステム定義から読み込んでインスタンスを生成します。<br>
     * アクセスする前に必ずopen()を行ってください。
     */
    protected VariableFileHandler()
    {
        super();
    }

    /**
     * ディレクトリを指定して、インスタンスを生成します。<br>
     * アクセスする前に必ずopen()を行ってください。
     * @param dirName ファイル作成/読み込みを行う対象ディレクトリ
     */
    protected VariableFileHandler(String dirName)
    {
        super(dirName);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public int create(Entity ent)
            throws ReadWriteException
    {
        checkOpen();
        boolean isMyLock = false;
        try
        {
            // lock the file
            isMyLock = oneShotLock();

            // truncate the file
            truncate();

            // append record to the file
            String rec = getRecordFormatter().format(ent);

            // write string with converted charset
            BufferedWriter writer = getWriter();
            writer.write(rec);
            writer.newLine();

            return RETURN_OK;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026094=ファイルの書き込み時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026094, e), getClass().getName());
            throw new ReadWriteException(e);
        }
        finally
        {
            // unlock the file
            if (isMyLock)
            {
                unLock();
            }
        }
    }

    /*
     * ファイルの最後にレコードを追加します。
     public void create2(Entity ent)
     throws ReadWriteException
     {
     checkOpen() ;
     try
     {
     // lock the file
     lock() ;

     RandomAccessFile raf = getRandomAccessFile() ;

     // seek to end of the file
     raf.seek(raf.length()) ;

     // append record to the file
     String rec = getRecordFormatter().format(ent) ;

     // write string with converted charset
     getWriter().write(rec) ;

     raf.write(rec.getBytes(getCharset())) ;
     raf.write(CRLF_BYTES) ;
     }
     catch (IOException e)
     {
     e.printStackTrace() ;
     // 6026094=ファイルの書き込み時にIOエラーが発生しました。
     RmiMsgLogClient.write(new TraceHandler(6026094, e), getClass().getName()) ;
     throw new ReadWriteException(e) ;
     }
     finally
     {
     // unlock the file
     unLock() ;
     }
     }
     */

    /**
     * {@inheritDoc}
     */
    public void read()
            throws ReadWriteException
    {
        checkOpen();
        setReadPointer(-1);

        try
        {
            //createStream();
            RandomAccessFile raf = getRandomAccessFile();
            raf.seek(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026093=ファイルの読み込み時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026093, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Entity read(int point)
            throws ReadWriteException,
                RecordFormatException
    {
        checkOpen();
        try
        {
            // seek to top of the file
            read();

            // skip records
            for (int i = 0; i < point; i++)
            {
                if (null == getReader().readLine())
                {
                    return null;
                }
            }

            // read line
            Entity rEnt = next();

            return rEnt;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026093=ファイルの読み込み時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026093, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /*
     * 指定されたポインタでレコードを読み込みます。
     
     public Entity read2(int point)
     throws ReadWriteException,
     ParseException
     {
     checkOpen() ;
     try
     {
     // seek to top of the file
     read() ;

     // skip records
     for (int i = 0; i < point; i++)
     {
     if (null == getRandomAccessFile().readLine())
     {
     return null ;
     }
     }

     // read line
     Entity rEnt = next() ;

     return rEnt ;
     }
     catch (IOException e)
     {
     e.printStackTrace() ;
     // 6026093=ファイルの読み込み時にIOエラーが発生しました。
     RmiMsgLogClient.write(new TraceHandler(6026093, e), getClass().getName()) ;
     throw new ReadWriteException(e);
     }
     }
     */

    /**
     * {@inheritDoc}
     */
    public Entity next()
            throws ReadWriteException,
                RecordFormatException
    {
        checkOpen();
        try
        {
            String rec = getReader().readLine();
            if (rec == null)
            {
                return null;
            }

            Entity rEnt = getRecordFormatter().parse(rec.getBytes(getCharset()));
            return rEnt;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026093=ファイルの読み込み時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026093, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /*
     * 次のレコードを読み込みます。
     
     public Entity next2()
     throws ReadWriteException,
     ParseException
     {
     checkOpen() ;
     try
     {
     RandomAccessFile raf = getRandomAccessFile() ;

     int idx = super.getReadIndex() ;
     if (idx < 0)
     {
     raf.seek(0) ;
     }

     String rec = raf.readLine() ;
     if (rec == null)
     {
     return null ;
     }

     Entity rEnt = getRecordFormatter().parse(rec) ;
     return rEnt ;
     }
     catch (IOException e)
     {
     e.printStackTrace() ;
     // 6026093=ファイルの読み込み時にIOエラーが発生しました。
     RmiMsgLogClient.write(new TraceHandler(6026093, e), getClass().getName()) ;
     throw new ReadWriteException(e);
     }
     }
     */

    /**
     * {@inheritDoc}
     */
    public Entity previous()
            throws ReadWriteException,
                RecordFormatException
    {
        notImplemented();
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void clear()
            throws ReadWriteException
    {
        checkOpen();

        RandomAccessFile raf = getRandomAccessFile();
        try
        {
            raf.setLength(0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026095=ファイルのクリア時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026095, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void open(Entity targetEntity, String charset)
            throws ReadWriteException
    {
        super.open(targetEntity, charset);

        try
        {
            createStream();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026092=ファイルのオープン時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026092, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void open(String dirPath, String name, String charset)
            throws ReadWriteException
    {
        super.open(dirPath, name, charset);

        try
        {
            createStream();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026092=ファイルのオープン時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026092, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void close()
    {
        try
        {
            Writer wr = getWriter();
            if (null != wr)
            {
                flush();

                getReader().close();
                getWriter().close();

                super.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            setReader(null);
            setWriter(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void flush()
            throws ReadWriteException
    {
        try
        {
            getWriter().flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026094=ファイルの書き込み時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026094, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 現在の行数を正しく取得することが出来ないため、
     * このメソッドは実装されません。
     * @return 0を返します。
     */
    public int getReadPointer()
    {
        notImplemented();
        return 0;
    }

    /**
     * @return readerを返します。
     */
    public BufferedReader getReader()
    {
        return _reader;
    }

    /**
     * @param reader readerを設定します。
     */
    protected void setReader(BufferedReader reader)
    {
        _reader = reader;
    }

    /**
     * @return writerを返します。
     */
    public BufferedWriter getWriter()
    {
        return _writer;
    }

    /**
     * @param writer writerを設定します。
     */
    protected void setWriter(BufferedWriter writer)
    {
        _writer = writer;
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
     * 入力/出力ストリームを作成します。<br>
     * 既に存在するときは、クローズ後作成します。
     * @throws IOException 入出力エラーが発生した場合
     */
    protected void createStream()
            throws IOException
    {
        // 入出力ストリームを作成するタイミングで、初期フラグをtrueにします。
        _first = true;

        InputStream fis = Channels.newInputStream(getFileChannel());
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, getCharset()), 1024);
        setReader(reader);

        OutputStream fos = Channels.newOutputStream(getFileChannel());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, getCharset()), 1024);
        setWriter(writer);
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * トランケート<br>
     * ファイル書き込み時に呼ばれ、これが初回の書き込みであれば
     * トランケート処理を行います。　この時、書き込みポジションが
     * 先頭であることもチェックします。
     * @throws IOException 入出力エラーが発生した場合
     */
    private void truncate()
            throws IOException
    {
        if (_first)
        {
            _first = false;
            FileChannel channel = getFileChannel();
            long pos = channel.position();
            if (0 == pos)
            {
                channel.truncate(0);
            }
        }
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
        return "$Id: VariableFileHandler.java 87 2008-10-04 03:07:38Z admin $";
    }
}
