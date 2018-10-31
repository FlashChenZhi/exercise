// $Id: RingFileHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.io.RandomAccessFile;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.file.header.FileHeader;

/**
 * 固定長リングファイルのためのファイルハンドラです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public class RingFileHandler
        extends FixedFileHandler
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
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ディレクトリ指定をシステム定義から読み込んでインスタンスを生成します。<br>
     * アクセスする前に必ずopen()を行ってください。
     */
    protected RingFileHandler()
    {
        super();
    }

    /**
     * ディレクトリを指定して、インスタンスを生成します。<br>
     * アクセスする前に必ずopen()を行ってください。
     * @param dirName ファイル作成/読み込みを行う対象ディレクトリ
     */
    protected RingFileHandler(String dirName)
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
        int recSize = getRecordSize();

        RandomAccessFile raf = getRandomAccessFile();

        boolean isMyLock = false;
        try
        {
            String rec = getRecordFormatter().format(ent) + LINE_SEPARATOR;

            isMyLock = oneShotLock();

            int idx = getFileHeader().getNextWrite();
            if (idx == FileHeader.RP_FILE_FULL)
            {
                return RETURN_FILE_FULL;
            }

            long point = idx * recSize;
            point += getFileHeader().length();

            raf.seek(point);

            raf.write(rec.getBytes(getCharset()));

            // getFileHeader().write() ;

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
            if (isMyLock)
            {
                unLock();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public void clear()
            throws ReadWriteException
    {
        checkOpen();

        RandomAccessFile raf = getRandomAccessFile();

        boolean isMyLock = false;
        try
        {
            isMyLock = oneShotLock();

            FileHeader header = getFileHeader();
            header.init();
            header.write();

            int recsize = getRecordSize();
            int maxrec = getMaxRecordNumber();
            long fsize = recsize * maxrec;

            fsize += header.length();

            raf.setLength(fsize);

        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026095=ファイルのクリア時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026095, e), getClass().getName());
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

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public int getMaxRecordNumber()
            throws ReadWriteException
    {
        return getStoreMetaData().getMaxRecordNumber();
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

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: RingFileHandler.java 87 2008-10-04 03:07:38Z admin $";
    }
}
