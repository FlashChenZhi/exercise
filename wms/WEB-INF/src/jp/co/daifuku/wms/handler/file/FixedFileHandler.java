// $Id: FixedFileHandler.java 87 2008-10-04 03:07:38Z admin $
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
import java.nio.channels.Channels;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.RecordFormatException;
import jp.co.daifuku.wms.handler.file.header.FileHeader;

/**
 * 固定長ファイルのためのファイルハンドラです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class FixedFileHandler
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

    private int _recordSize = 0;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private boolean _useHeader = false;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ディレクトリ指定をシステム定義から読み込んでインスタンスを生成します。<br>
     * アクセスする前に必ずopen()を行ってください。
     */
    protected FixedFileHandler()
    {
        super();
    }

    /**
     * ディレクトリを指定して、インスタンスを生成します。<br>
     * アクセスする前に必ずopen()を行ってください。
     * @param dirName ファイル作成/読み込みを行う対象ディレクトリ
     */
    protected FixedFileHandler(String dirName)
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
        BufferedWriter writer = getWriter();
        try
        {
            // lock the file
            isMyLock = oneShotLock();

            String rec = entityToString(ent);

            // write string with converted charset
            writer.write(rec);

            return RETURN_OK;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026093=ファイルの読み込み時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026093, e), getClass().getName());
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

    /**
     * {@inheritDoc}
     */
    public void read()
            throws ReadWriteException
    {
        checkOpen();

        _useHeader = true;

        // ファイルヘッダから現在の読み込みポインタを取得
        getFileHeader().read();
        getFileHeader().setTop(false);
        getFileHeader().setBottom(false);
    }

    /**
     * {@inheritDoc}
     */
    public Entity read(int point)
            throws ReadWriteException,
                RecordFormatException
    {
        checkOpen();

        _useHeader = false;
        setReadPointer(point);
        Entity rent = next();
        return rent;
    }

    /**
     * {@inheritDoc}
     */
    public Entity next()
            throws ReadWriteException,
                RecordFormatException
    {
        Entity ent = readContinue(true);
        return ent;
    }

    /**
     * {@inheritDoc}
     */
    public Entity previous()
            throws ReadWriteException,
                RecordFormatException
    {
        Entity ent = readContinue(false);
        return ent;
    }

    /**
     * {@inheritDoc}
     */
    public void modify(Entity ent)
            throws ReadWriteException
    {
        checkOpen();

        // calc. the write point
        int recSize = getRecordSize();

        RandomAccessFile raf = getRandomAccessFile();

        boolean isMyLock = false;
        try
        {
            // prepare the data to write
            String rec = entityToString(ent);
            byte[] wbuff = rec.getBytes(getCharset());

            isMyLock = oneShotLock();

            int idx = getReadPointer() - 1;
            long point = idx * recSize;
            point += getFileHeader().length();

            raf.seek(point);
            raf.write(wbuff);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026093=ファイルの読み込み時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026093, e), getClass().getName());
            throw new ReadWriteException(e);
        }
        finally
        {
            if (isMyLock)
            {
                unLock();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void modify(Entity ent, int point)
            throws ReadWriteException
    {
        setReadPointer(point + 1);
        modify(ent);
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

            int recSize = getRecordFormatter().length();
            setRecordSize(recSize);
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

            int recSize = getRecordFormatter().length();
            setRecordSize(recSize);
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
            flush();
            getReader().close();
            getWriter().close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        super.close();
    }

    /**
     * このファイルの最大レコード番号を返します。
     * @return 最大レコードNo.
     * @throws ReadWriteException
     */
    public int getMaxRecordNumber()
            throws ReadWriteException
    {
        try
        {
            long fsize = getRandomAccessFile().length() - getFileHeader().length();
            int recsize = getRecordFormatter().length();

            return (int)(fsize / recsize);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026096=ファイルの最大レコードNo.取得時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026096, e), getClass().getName());
            throw new ReadWriteException(e);
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

    /**
     * @return recordSizeを返します。
     */
    public int getRecordSize()
    {
        return _recordSize;
    }

    /**
     * @param recordSize recordSizeを設定します。
     */
    protected void setRecordSize(int recordSize)
    {
        _recordSize = recordSize;
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
     * レコードを読み込みます。
     * @param forward 先に読み進める場合は<code>true</code>を指定します。
     * @return 読み込んだエンティティ
     * @throws ReadWriteException 
     * @throws RecordFormatException
     */
    protected Entity readContinue(boolean forward)
            throws ReadWriteException,
                RecordFormatException
    {
        checkOpen();

        int recSize = getRecordSize();
        byte[] rbuff = new byte[recSize];

        RandomAccessFile raf = getRandomAccessFile();

        boolean isMyLock = false;
        try
        {
            isMyLock = oneShotLock();

            FileHeader header = getFileHeader();
            int idx = (forward) ? header.getNextRead(_useHeader)
                               : header.getPreviousRead();

            if (idx == FileHeader.RP_NO_RECORD_TO_READ)
            {
                return null;
            }

            long point = idx * recSize;
            point += getFileHeader().length();

            raf.seek(point);

            int rbyte = raf.read(rbuff);
            if (rbyte < 0)
            {
                return null;
            }

            // String rec = new String(rbuff, getCharset()) ;
            Entity rent = getRecordFormatter().parse(rbuff);

            return rent;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026093=ファイルの読み込み時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026093, e), getClass().getName());
            throw new ReadWriteException(e);
        }
        finally
        {
            if (isMyLock)
            {
                unLock();
            }
        }
    }

    /**
     * 指定されたエンティティをファイルに書き込むレコード用文字列に変換します。
     * @param ent 元データのエンティティ
     * @return 改行までを含むレコード用文字列
     */
    protected String entityToString(Entity ent)
    {
        String rec = getRecordFormatter().format(ent) + LINE_SEPARATOR;
        return rec;
    }

    /**
     * 入力/出力ストリームを作成します。<br>
     * 既に入力/出力ストリームが存在するときは、クローズ後作成します。
     * @throws IOException 入出力エラーが発生した場合
     */
    protected void createStream()
            throws IOException
    {
        BufferedReader reader = getReader();
        if (reader != null)
        {
            reader.close();
        }

        InputStream fis = Channels.newInputStream(getFileChannel());
        reader = new BufferedReader(new InputStreamReader(fis, getCharset()), 1024);
        setReader(reader);

        BufferedWriter writer = getWriter();
        if (writer != null)
        {
            writer.close();
        }

        OutputStream fos = Channels.newOutputStream(getFileChannel());
        writer = new BufferedWriter(new OutputStreamWriter(fos, getCharset()), 1024);
        setWriter(writer);
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
        return "$Id: FixedFileHandler.java 87 2008-10-04 03:07:38Z admin $";
    }
}
