// $Id: AbstractFileHeader.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file.header;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.text.ParseException;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;

/**
 * ファイルヘッダ共通のスーパークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractFileHeader
        implements FileHeader
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 初期ポインタ */
    protected static final int RP_PREV_INIT = -99;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    private boolean _top = false;

    private boolean _bottom = false;

    private int _readPoint = -1;

    private int _writePoint = -1;

    private int _maxRecord = -1;

    private DecimalFormat _rpFormatter = new DecimalFormat(FMT_READ_POINTER);

    private DecimalFormat _wpFormatter = new DecimalFormat(FMT_WRITE_POINTER);

    private RandomAccessFile _randomAccessFile;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     *　デフォルトコンストラクタ 
     */
    public AbstractFileHeader()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * ファイルタイプに応じたファイルヘッダクラスを返します。
     * @param smeta ストアメタデータ
     * @param target 対象のファイルをハンドリングするRandomAccessFile
     * @return ファイルタイプごとのヘッダクラス
     * @see jp.co.daifuku.wms.handler.field.StoreMetaData
     */
    public static FileHeader getInstance(StoreMetaData smeta, RandomAccessFile target)
    {
        AbstractFileHeader rfh = null;

        String fileType = smeta.getType();
        int max = smeta.getMaxRecordNumber();

        if (StoreMetaData.STORE_TYPE_FILE.equals(fileType))
        {
            rfh = new NormalFileHeader();
        }
        else if (StoreMetaData.STORE_TYPE_FILE_RING.equals(fileType))
        {
            rfh = new RingFileHeader();
            rfh.setMaxRecord(max);
        }
        else if (StoreMetaData.STORE_TYPE_FILE_RP_RING.equals(fileType))
        {
            rfh = new RPRingFileHeader();
            rfh.setMaxRecord(max);
        }
        else
        {
            throw new RuntimeException("Unknown file type:" + fileType);
        }

        // initialize
        rfh.setRandomAccessFile(target);

        return rfh;
    }

    /**
     * {@inheritDoc}
     */
    public void read()
            throws ReadWriteException
    {
        RandomAccessFile raf = getRandomAccessFile();
        try
        {
            raf.seek(0L);
            String hStr = raf.readLine();
            parseHeader(hStr);
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
    public void write()
            throws ReadWriteException
    {
        RandomAccessFile raf = getRandomAccessFile();
        try
        {
            raf.seek(0L);
            String hStr = formatHeader();
            raf.writeBytes(hStr);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6026094=ファイルの書き込み時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026094, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void init()
            throws ReadWriteException
    {
        setReadPointer(POINTER_INIT);
        setWritePointer(POINTER_INIT);

        setTop(false);
        setBottom(false);

        write();
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return readPointFormatterを返します。
     */
    protected DecimalFormat getRpFormatter()
    {
        return _rpFormatter;
    }

    /**
     * @param readPointFormatter readPointFormatterを設定します。
     */
    protected void setRpFormatter(DecimalFormat readPointFormatter)
    {
        _rpFormatter = readPointFormatter;
    }

    /**
     * @return writePointFormatterを返します。
     */
    protected DecimalFormat getWpFormatter()
    {
        return _wpFormatter;
    }

    /**
     * @param writePointFormatter writePointFormatterを設定します。
     */
    protected void setWpFormatter(DecimalFormat writePointFormatter)
    {
        _wpFormatter = writePointFormatter;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isBottom()
    {
        return _bottom;
    }

    /**
     * {@inheritDoc}
     */
    public void setBottom(boolean bottom)
    {
        _bottom = bottom;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isTop()
    {
        return _top;
    }

    /**
     * {@inheritDoc}
     */
    public void setTop(boolean top)
    {
        _top = top;
    }

    /**
     * {@inheritDoc}
     */
    public int getReadPointer()
    {
        return _readPoint;
    }

    /**
     * {@inheritDoc}
     */
    public void setReadPointer(int rp)
    {
        _readPoint = rp;
    }

    /**
     * {@inheritDoc}
     */
    public int getWritePointer()
    {
        return _writePoint;
    }

    /**
     * {@inheritDoc}
     */
    public void setWritePointer(int wp)
    {
        _writePoint = wp;
    }

    /**
     * @return maxRecordを返します。
     */
    protected int getMaxRecord()
    {
        return _maxRecord;
    }

    /**
     * @param maxRecord maxRecordを設定します。
     */
    protected void setMaxRecord(int maxRecord)
    {
        if (maxRecord <= 0)
        {
            throw new RuntimeException("Invalid max record (" + maxRecord + ") defined.");
        }

        _maxRecord = maxRecord;
    }

    /**
     * @return randomAccessFileを返します。
     */
    protected RandomAccessFile getRandomAccessFile()
    {
        return _randomAccessFile;
    }

    /**
     * @param randomAccessFile randomAccessFileを設定します。
     */
    protected void setRandomAccessFile(RandomAccessFile randomAccessFile)
    {
        _randomAccessFile = randomAccessFile;
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
     * 最大レコード数との比較を行い、次のレコードNo.を返します。
     * @param currPoint 現レコードNo.
     * @return 次のレコードNo.
     */
    protected int getNextPointer(int currPoint)
    {
        int max = getMaxRecord();
        int next = (currPoint + 1) % max;

        return next;
    }

    /**
     * 現レコードNo.の1レコード前のレコードNo.を返します。
     * @param currRP 現レコードNo.
     * @return 前のレコードNo.
     */
    protected int getPrevPointer(int currRP)
    {
        int preRP = currRP - 1;
        int prep = (preRP < 0) ? getMaxRecord() - 1
                              : preRP;
        return prep;
    }

    /**
     * ヘッダレコードをフォーマットします。
     * @return フォーマット後のヘッダレコード
     */
    protected String formatHeader()
    {
        StringBuffer buff = new StringBuffer();

        buff.append(HEADER_VERSION);
        String rd = getRpFormatter().format(getReadPointer());
        buff.append(rd);
        String wt = getWpFormatter().format(getWritePointer());
        buff.append(wt);
        buff.append(LINE_SEPARATOR);

        return String.valueOf(buff);
    }

    /**
     * ヘッダレコードより管理レコードNo.を取得します。
     * @param rec ヘッダレコード
     */
    protected void parseHeader(String rec)
    {
        int idx = 0;

        try
        {
            String hv = rec.substring(idx, HEADER_VERSION.length());
            if (!hv.equals(HEADER_VERSION))
            {
                throw new RuntimeException("This file header is not supported.");
            }

            idx += HEADER_VERSION.length();

            String rpStr = rec.substring(idx, idx + POINTER_LENGTH);
            int rp = getRpFormatter().parse(rpStr).intValue();
            setReadPointer(rp);

            idx += POINTER_LENGTH;
            String wpStr = rec.substring(idx, idx + POINTER_LENGTH);
            int wp = getWpFormatter().parse(wpStr).intValue();
            setWritePointer(wp);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Header parse faild.");
        }
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
        return "$Id: AbstractFileHeader.java 87 2008-10-04 03:07:38Z admin $";
    }
}
