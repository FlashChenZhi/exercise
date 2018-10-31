//$Id: AbstractFileHandler.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.NotImplementedException;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.file.header.AbstractFileHeader;
import jp.co.daifuku.wms.handler.file.header.FileHeader;
import jp.co.daifuku.wms.handler.file.record.AbstractRecordFormatter;
import jp.co.daifuku.wms.handler.file.record.RecordFormatter;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;

// import sun.reflect.generics.reflectiveObjects.NotImplementedException ;

/**
 * ファイルハンドラ共通のスーパークラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */

public abstract class AbstractFileHandler
        implements FileHandler
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
    private StoreMetaData _storeMetaData = null;

    private RecordFormatter _recordFormatter = null;

    private Entity _targetEntity = null;

    private String _directory = null;

    private String _filename = null;

    private File _targetFile = null;

    private RandomAccessFile _randomAccessFile = null;

    private FileChannel _fileChannel = null;

    private FileLock _fileLock = null;

    private String _charset = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private FileHeader _fileHeader;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * ディレクトリ指定をシステム定義から読み込んでインスタンスを生成します。<br>
     * アクセスする前に必ずopen()を行ってください。
     */
    protected AbstractFileHandler()
    {
        String dir = HandlerSysDefines.SEQFILE_DIR;
        if (dir == null || dir.length() == 0)
        {
            throw new RuntimeException("No directory defined for sequential file.");
        }
        setDirectory(dir);
    }

    /**
     * ディレクトリを指定して、インスタンスを生成します。<br>
     * アクセスする前に必ずopen()を行ってください。
     * @param dirName ファイル作成/読み込みを行う対象ディレクトリ
     */
    protected AbstractFileHandler(String dirName)
    {
        setDirectory(dirName);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public void open(Entity targetEntity, String charset)
            throws ReadWriteException
    {
        // メタ情報の読み込みと保存
        prepare(targetEntity);
        // ファイルオープン
        open(charset);
    }

    /**
     * {@inheritDoc}
     */
    public void open(Entity targetEntity)
            throws ReadWriteException
    {
        open(targetEntity, null);
    }

    /**
     * {@inheritDoc}
     */
    public void open(String dirPath, String name, String charset)
            throws ReadWriteException
    {
        // 対象ファイルとディレクトリの保存を行います
        // メタ情報は、インスタンスを生成した時点のものを利用します
        File tgtFile = new File(dirPath, name);
        prepare(tgtFile);
        // ファイルオープン
        open(charset);
    }

    /**
     * {@inheritDoc}
     */
    public void open(String dirPath, String name)
            throws ReadWriteException
    {
        open(dirPath, name, null);
    }

    /**
     * {@inheritDoc}
     */
    public void close()
    {
        try
        {
            // FileChannel fc = getFileChannel() ;
            RandomAccessFile raf = getRandomAccessFile();
            if (raf != null)
            {
                raf.close();
            }
        }
        catch (IOException e)
        {
            // do nothing on close error.
        }
        finally
        {
            setFileChannel(null);
            setRandomAccessFile(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void lock()
            throws ReadWriteException
    {
        checkOpen();
        try
        {
            FileLock currentLock = getFileLock();
            if (currentLock == null || !currentLock.isValid())
            {
                currentLock = getFileChannel().lock();
                setFileLock(currentLock);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6006020=ファイルのロックに失敗しました。
            RmiMsgLogClient.write(new TraceHandler(6006020, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean tryLock()
            throws ReadWriteException
    {
        checkOpen();
        try
        {
            FileLock currentLock = getFileLock();
            if (currentLock != null && currentLock.isValid())
            {
                return true;
            }
            currentLock = getFileChannel().tryLock();
            setFileLock(currentLock);

            return (currentLock != null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6006020=ファイルのロックに失敗しました。
            RmiMsgLogClient.write(new TraceHandler(6006020, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void unLock()
    {
        checkOpen();
        try
        {
            FileLock currentLock = getFileLock();
            if (currentLock != null && currentLock.isValid())
            {
                currentLock.release();
                setFileLock(null);
            }
        }
        catch (Exception e)
        {
            // 6006020=ファイルのロック解除に失敗しました
            RmiMsgLogClient.write(new TraceHandler(6006020, e), getClass().getName());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void modify(Entity ent, int point)
            throws ReadWriteException
    {
        notImplemented();
    }

    /**
     * {@inheritDoc}
     */
    public void modify(Entity ent)
            throws ReadWriteException
    {
        notImplemented();
    }

    /**
     * ガベージコレクション時の処理
     */
    protected void finalize()
    {
        if (null != getFileChannel())
        {
            close();
        }
        try
        {
            super.finalize();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    /**
     * エンティティのメタデータから、対応するハンドラを生成します。
     * @param targetEntity ハンドラの対象とするエンティティ
     * @return ファイルハンドラ
     */
    public static FileHandler getInstance(Entity targetEntity)
    {
        String stype = targetEntity.getStoreMetaData().getType();
        FileHandler rHandler = null;

        if (StoreMetaData.STORE_TYPE_FILE.equals(stype))
        {
            String sclass = targetEntity.getStoreMetaData().getStoreClass();
            if (FileHandler.FILE_CLASS_CSV.equals(sclass))
            {
                rHandler = new VariableFileHandler();
            }
            else if (FileHandler.FILE_CLASS_FIXED.equals(sclass) || (FileHandler.FILE_CLASS_FIXED_CSV.equals(sclass)))
            {
                rHandler = new FixedFileHandler();
            }
            else if (FileHandler.FILE_CLASS_FIXED_CSV.equals(sclass))
            {
                rHandler = new FixedFileHandler();
            }
        }
        else if (StoreMetaData.STORE_TYPE_FILE_RING.equals(stype)
                || StoreMetaData.STORE_TYPE_FILE_RP_RING.equals(stype))
        {
            rHandler = new RingFileHandler();
        }
        if (null != rHandler)
        {
            rHandler.setTargetEntity(targetEntity);
            rHandler.setStoreMetaData(targetEntity.getStoreMetaData());
        }

        return rHandler;
    }

    /**
     * {@inheritDoc}
     */
    public long length()
            throws ReadWriteException
    {
        checkOpen();

        long siz = 0;
        try
        {
            siz = getRandomAccessFile().length();
            return siz;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            // 6006020=ファイル長の取得に失敗しました
            RmiMsgLogClient.write(new TraceHandler(6006020, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public StoreMetaData getStoreMetaData()
    {
        return _storeMetaData;
    }

    /**
     * ハンドリングする対象のストアメタデータを設定します。
     * @param storeMetaData ハンドリングする対象のストアメタデータ
     */
    public void setStoreMetaData(StoreMetaData storeMetaData)
    {
        _storeMetaData = storeMetaData;
    }

    /**
     * {@inheritDoc}
     */
    public String getStorename()
    {
        return _filename;
    }

    /**
     * ハンドリングする対象のファイル名を設定します。
     * @param storeName ハンドリングする対象のファイル名
     */
    public void setStorename(String storeName)
    {
        _filename = storeName;
    }

    /**
     * {@inheritDoc}
     */
    public RecordFormatter getRecordFormatter()
    {
        return _recordFormatter;
    }

    /**
     * {@inheritDoc}
     */
    public void setRecordFormatter(RecordFormatter recordFormatter)
    {
        _recordFormatter = recordFormatter;
    }

    /**
     * {@inheritDoc}
     */
    public Entity getTargetEntity()
    {
        return _targetEntity;
    }

    /**
     * ハンドリングする対象のエンティティを設定します。
     * @param targetEntity ハンドリングする対象のエンティティ
     */
    public void setTargetEntity(Entity targetEntity)
    {
        _targetEntity = targetEntity;
    }

    /**
     * {@inheritDoc}
     */
    public String getDirectory()
    {
        return _directory;
    }

    /**
     * 対象のディレクトリを設定します。
     * @param directory 対象のディレクトリ
     */
    protected void setDirectory(String directory)
    {
        _directory = directory;

        String filename = getStorename();
        if (filename != null)
        {
            File target = new File(directory, filename);
            setFile(target);
        }
    }

    /**
     * 対象のファイル名を設定します。
     * @param filename ファイル名
     */
    public void setFilename(String filename)
    {
        _filename = filename;

        String dir = getDirectory();
        if (dir != null)
        {
            File target = new File(dir, filename);
            setFile(target);
        }
    }

    /**
     * {@inheritDoc}
     */
    public File getFile()
    {
        return _targetFile;
    }

    /**
     * ハンドリングする対象のファイルを設定します。
     * @param targetFile ハンドリングする対象のファイル
     */
    protected void setFile(File targetFile)
    {
        _targetFile = targetFile;
    }

    /**
     * ファイルチャネルを取得します。
     * @return ファイルチャネルを返します。
     */
    public FileChannel getFileChannel()
    {
        return _fileChannel;
    }

    /**
     * ファイルチャネルを設定します。
     * @param fileChannel ファイルチャネル
     */
    public void setFileChannel(FileChannel fileChannel)
    {
        _fileChannel = fileChannel;
    }

    /**
     * {@inheritDoc}
     */
    public RandomAccessFile getRandomAccessFile()
    {
        return _randomAccessFile;
    }

    /**
     * ランダムアクセスファイルを設定します。
     * @param randomAccessFile ランダムアクセスファイル
     */
    public void setRandomAccessFile(RandomAccessFile randomAccessFile)
    {
        _randomAccessFile = randomAccessFile;
    }

    /**
     * {@inheritDoc}
     */
    public FileLock getFileLock()
    {
        return _fileLock;
    }

    /**
     * ファイルロックを設定します。
     * @param fileLock ファイルロック
     */
    public void setFileLock(FileLock fileLock)
    {
        _fileLock = fileLock;
    }

    /**
     * 読み込みポインタを取得します。
     * @return 読み込みポインタ
     */
    protected int getReadPointer()
    {
        return getFileHeader().getReadPointer();
    }

    /**
     * 読み込みポインタを設定します。
     * @param readIndex 読み込みポインタ
     */
    protected void setReadPointer(int readIndex)
    {
        getFileHeader().setReadPointer(readIndex);
    }

    /**
     * {@inheritDoc}
     */
    public FileHeader getFileHeader()
    {
        return _fileHeader;
    }

    /**
     * ファイルヘッダを設定します。
     * @param fileHeader ファイルヘッダ
     */
    public void setFileHeader(FileHeader fileHeader)
    {
        _fileHeader = fileHeader;
    }

    /**
     * {@inheritDoc}
     */
    public String getCharset()
    {
        return _charset;
    }

    /**
     * {@inheritDoc}
     */
    public void setCharset(String charset)
    {
        if (!Charset.isSupported(charset))
        {
            throw new RuntimeException("Charset (" + charset + ") is not supported.");
        }

        _charset = charset;
    }

    /**
     * {@inheritDoc}
     */
    public Entity createEntity()
    {
        Object tmpEnt;
        try
        {
            tmpEnt = getTargetEntity().getClass().newInstance();
            if (tmpEnt instanceof Entity)
            {
                Entity newEnt = (Entity)tmpEnt;
                newEnt.clear();
                return newEnt;
            }
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
            // TODO
            // 致命的なエラー
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            // TODO
            // 致命的なエラー
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isOpen()
    {
        FileChannel fc = getFileChannel();
        boolean open = (fc != null) && (fc.isOpen());

        return open;
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
     * ファイルがオープンされているかどうかチェックします。<br>
     * ファイルがオープンされていなければ、<code>RuntimeException</code>
     * をスローします。
     */
    protected void checkOpen()
    {
        if (!isOpen())
        {
            throw new RuntimeException("File is not open.");
        }
    }

    /**
     * ロックされていないときはロックします。<br>
     * 既にロックされているときはロックを行いません。
     * @return ロックしたとき<code>true</code>を返します。
     * @throws ReadWriteException
     */
    protected boolean oneShotLock()
            throws ReadWriteException
    {
        FileLock lock = getFileLock();
        if (lock == null || !lock.isValid())
        {
            lock();
            return true;
        }
        return false;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * インスタンスの初期化を行います。
     * @param targetEntity
     */
    private void prepare(Entity targetEntity)
    {
        // 対象エンティティの保存
        setTargetEntity(targetEntity);
        // ストアメタデータの保存
        StoreMetaData metaData = targetEntity.getStoreMetaData();
        setStoreMetaData(metaData);
        // 対象ストアタイプのチェック
        checkStoreMetaData();
        // レコードフォーマッタのロード
        loadRecordFormatter(metaData);

        // ファイル名のセット
        String filename = metaData.getName();
        setFilename(filename);
    }

    /**
     * インスタンスの初期化を行います
     * @param targetFile
     */
    private void prepare(File targetFile)
    {
        // 対象ファイルの情報を設定します
        setDirectory(targetFile.getParent());
        setFilename(targetFile.getName());
        setFile(targetFile);

        // 対象エンティティはインスタンス生成時のものを用います
        Entity targetEntity = getTargetEntity();

        // ストアメタデータの保存
        StoreMetaData metaData = targetEntity.getStoreMetaData();
        setStoreMetaData(metaData);
        // 対象ストアタイプのチェック
        checkStoreMetaData();
        // レコードフォーマッタのロード
        loadRecordFormatter(metaData);
    }

    /**
     * 対象ストアタイプのチェック
     */
    private void checkStoreMetaData()
    {
        StoreMetaData metaData = getStoreMetaData();

        String stype = metaData.getType();

        boolean correct =
                stype.equals(StoreMetaData.STORE_TYPE_FILE) || stype.equals(StoreMetaData.STORE_TYPE_FILE_RING)
                        || stype.equals(StoreMetaData.STORE_TYPE_FILE_RP_RING);
        if (!correct)
        {
            String msg = "This Handler does not support type:" + stype;
            throw new RuntimeException(msg);
        }

        // ring file supports only fixed length record.
        String recType = metaData.getStoreClass();

        boolean ring =
                stype.equals(StoreMetaData.STORE_TYPE_FILE_RING) || stype.equals(StoreMetaData.STORE_TYPE_FILE_RP_RING);

        boolean forRingRec =
                recType.equals(FileHandler.FILE_CLASS_FIXED) || recType.equals(FileHandler.FILE_CLASS_FIXED_CSV);
        if (ring && !forRingRec)
        {
            String msg = "Ring file does not support record type:" + recType;
            throw new RuntimeException(msg);
        }
    }

    /**
     * レコードフォーマッタのロード
     * @param metaData
     */
    private void loadRecordFormatter(StoreMetaData metaData)
    {
        RecordFormatter fmtr = AbstractRecordFormatter.getInstance(this);
        setRecordFormatter(fmtr);
    }

    /**
     * ハンドリング対象ファイルのキャラクタセットを指定してオープンします。
     * @param charset 使用するキャラクタセット
     * @throws ReadWriteException オープンに失敗したときスローされます。
     */
    private void open(String charset)
            throws ReadWriteException
    {
        // キャラクタセットの保存
        String saveCharset = charset;
        if (charset == null)
        {
            saveCharset = getStoreMetaData().getCharset();
        }
        setCharset(saveCharset);

        // ファイルのオープン
        try
        {
            File target = getFile();
            // write data only  (w/o metadata of filesystem)
            RandomAccessFile raf = new RandomAccessFile(target, "rwd");
            // save random access file object
            setRandomAccessFile(raf);
            // save file channel object
            setFileChannel(raf.getChannel());

            // get and save header class
            FileHeader fh = AbstractFileHeader.getInstance(getStoreMetaData(), getRandomAccessFile());

            setFileHeader(fh);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            // 6026092=ファイルのオープン時にIOエラーが発生しました。
            RmiMsgLogClient.write(new TraceHandler(6026092, e), getClass().getName());
            throw new ReadWriteException(e);
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * NotImplementedExceptionをスローします。
     */
    protected void notImplemented()
    {
        throw new NotImplementedException();
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractFileHandler.java 87 2008-10-04 03:07:38Z admin $";
    }
}
