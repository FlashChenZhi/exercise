//$Id: FileHandler.java 6918 2010-01-27 01:49:40Z okamura $
package jp.co.daifuku.wms.handler.file;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.RecordFormatException;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.file.header.FileHeader;
import jp.co.daifuku.wms.handler.file.record.RecordFormatter;

/**
 * シーケンシャルアクセス用のエンティティ・ハンドラインターフェースです。
 *
 * @version $Revision: 6918 $, $Date: 2010-01-27 10:49:40 +0900 (水, 27 1 2010) $
 * @author  ss
 * @author  Last commit: $Author: okamura $
 */

public interface FileHandler
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** システムの改行コード */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /** ファイルレコードタイプ (ストアクラス:CSV) */
    public static final String FILE_CLASS_CSV = "csv";

    /** ファイルレコードタイプ (ストアクラス:FIXED CSV) */
    public static final String FILE_CLASS_FIXED_CSV = "fixed_csv";

    /** ファイルレコードタイプ (ストアクラス:FIXED) */
    public static final String FILE_CLASS_FIXED = "fixed";

    /** 返値(正常) */
    public static final int RETURN_OK = 0;

    /** 返値(ファイルが満量) */
    public static final int RETURN_FILE_FULL = 1;

    /** エンティティにセットされるオリジナルレコード内容のフィールド */
    public static final FieldName FIELD_ORIGINAL_RECORD = new FieldName("", "__FIELD_ORIGINAL_RECORD__");

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * ハンドリング対象のエンティティと、キャラクタセットを
     * 指定してファイルをオープンします。
     * @param targetEntity 対象のエンティティ
     * @param charset 使用するキャラクタセット
     * @throws ReadWriteException オープンに失敗したときスローされます。
     */
    public void open(Entity targetEntity, String charset)
            throws ReadWriteException;

    /**
     * ハンドリング対象のエンティティを指定してファイルを
     * オープンします。<br>
     * <code>stores.xml</code>(charset)で定義されたキャラクタセットを使用します。<br>
     * <code>stores.xml</code>で指定されていない場合は、システムのデフォルト
     * キャラクタセットを使用します。
     * @param targetEntity 対象のエンティティ
     * @throws ReadWriteException オープンに失敗したときスローされます。
     */
    public void open(Entity targetEntity)
            throws ReadWriteException;

    /**
     * ハンドリング対象のディレクトリと、ファイル名、キャラクタセットを
     * 指定してファイルをオープンします。
     * @param dirPath 対象のディレクトリ
     * @param name 対象のファイル名
     * @param charset 使用するキャラクタセット
     * @throws ReadWriteException オープンに失敗したときスローされます。
     */
    public void open(String dirPath, String name, String charset)
            throws ReadWriteException;

    /**
     * ハンドリング対象のディレクトリと、ファイル名を指定してファイルを
     * オープンします。<br>
     * <code>stores.xml</code>(charset)で定義されたキャラクタセットを使用します。<br>
     * <code>stores.xml</code>で指定されていない場合は、システムのデフォルト
     * キャラクタセットを使用します。
     * @param dirPath 対象のディレクトリ
     * @param name 対象のファイル名
     * @throws ReadWriteException オープンに失敗したときスローされます。
     */
    public void open(String dirPath, String name)
            throws ReadWriteException;

    /**
     * ファイルをクローズします。
     */
    public void close();

    /**
     * ファイルにレコードを追加します。
     * @param ent 追加するレコードデータ
     * @return レコード作成結果
     * @throws ReadWriteException ファイルIOエラーが発生したときスローされます。
     */
    public int create(Entity ent)
            throws ReadWriteException;

    /**
     * ポインタを指定してファイルレコードを更新します。<br>
     * 固定長ファイルのみで有効です。
     * @param ent 更新データ
     * @param point 対象レコードポインタ
     * @throws ReadWriteException
     */
    public void modify(Entity ent, int point)
            throws ReadWriteException;

    /**
     * 現在のファイルレコードを更新します。<br>
     * 固定長ファイルのみで有効です。
     * @param ent 更新データ
     * @throws ReadWriteException ファイルIOエラーが発生したときスローされます。
     */
    public void modify(Entity ent)
            throws ReadWriteException;

    /**
     * ファイルの先頭位置からの読み込みを開始します。<br>
     * このメソッドでは、読み込みポインタを初期化するのみで、
     * 実際のデータ読み込みは、next()で行います。
     * @throws ReadWriteException ファイルIOエラーが発生したときスローされます。
     */
    public void read()
            throws ReadWriteException;

    /**
     * ポインタを指定して読み込みを行います。<br>
     * 指定されたポインタは内部で保存され、このメソッドの
     * あとでnext()を呼び出すと、point+1のレコードが
     * 読み出されます。
     * @param point 読み込みポインタ
     * @return 対象データ
     * @throws ReadWriteException ファイルIOエラーが発生したときスローされます。
     * @throws RecordFormatException レコードのパースに失敗したときスローされます。
     */
    public Entity read(int point)
            throws ReadWriteException,
                RecordFormatException;

    /**
     * レコードを読み込み、内部ポインタを1つ進めます。
     * @return 対象データ
     * @throws ReadWriteException ファイルIOエラーが発生したときスローされます。
     * @throws RecordFormatException レコードのパースに失敗したときスローされます。
     */
    public Entity next()
            throws ReadWriteException,
                RecordFormatException;

    /**
     * 内部ポインタを1つ戻し、レコードを読み込みます。
     * @return 対象データ
     * @throws ReadWriteException ファイルIOエラーが発生したときスローされます。
     * @throws RecordFormatException レコードのパースに失敗したときスローされます。
     */
    public Entity previous()
            throws ReadWriteException,
                RecordFormatException;

    /**
     * ファイルを初期状態にします。
     * @throws ReadWriteException ファイルIOエラーが発生したときスローされます。
     */
    public void clear()
            throws ReadWriteException;

    /**
     * ファイルをロックします。<br>
     * 排他ロックが成功するまでは、ブロックされます。
     * @throws ReadWriteException ロック中に異常が発生したときスローされます。
     */
    public void lock()
            throws ReadWriteException;

    /**
     * ロックを試みます。<br>
     * 既にロック済みの場合は何もせずに<code>true</code>で返ります。
     * @return ロックが成功したとき<code>true</code>を返します。
     * @throws ReadWriteException ロック中に異常が発生したときスローされます。
     */
    public boolean tryLock()
            throws ReadWriteException;

    /**
     * ロックしたファイルを解放します。
     */
    public void unLock();

    /**
     * ファイルサイズ(byte)を返します。
     * @return ファイルサイズ
     * @throws ReadWriteException ファイルIOエラーが発生したときスローされます。
     */
    public long length()
            throws ReadWriteException;

    /**
     * バッファの内容をフラッシュします。
     * @throws ReadWriteException
     */
    public void flush()
            throws ReadWriteException;

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * @return targetEntityを返します。
     */
    public Entity getTargetEntity();

    /**
     * @param targetEntity targetEntityを設定します。
     */
    public void setTargetEntity(Entity targetEntity);

    /**
     * エンティティを生成します。
     * @return 空のエンティティ
     */
    public Entity createEntity();

    /**
     * ハンドリングする対象のストアメタデータを返します。
     * @return ストアメタデータ
     */
    public StoreMetaData getStoreMetaData();

    /**
     * ハンドリングする対象のストアメタデータを設定します。
     * @param storeMetaData ストアメタデータ
     */
    public void setStoreMetaData(StoreMetaData storeMetaData);

    /**
     * ハンドリング対象のファイルを返します。
     * @return ハンドリング対象のファイル
     */
    public File getFile();

    /**
     * ハンドリング対象のファイル名を返します。
     * @return ファイル名
     */
    public String getStorename();

    /**
     * 対象のディレクトリを返します。
     * @return ディレクトリ名
     */
    public String getDirectory();

    /**
     * @return charsetを返します。
     */
    public String getCharset();

    /**
     * @param charset charsetを設定します。
     */
    public void setCharset(String charset);

    /**
     * ファイルがオープンされているかどうかチェックします。
     * @return オープンされていれば <code>true</code>を返します。
     */
    public boolean isOpen();

    /**
     * ファイルヘッダを取得します。
     * @return ファイルヘッダ
     */
    public FileHeader getFileHeader();

    /**
     * ファイルロックを取得します。
     * @return ファイルロック
     */
    public FileLock getFileLock();

    /**
     * ランダムアクセスファイルを取得します。
     * @return ランダムアクセスファイル
     */
    public RandomAccessFile getRandomAccessFile();

    /**
     * @return recordFormatterを返します。
     */
    public RecordFormatter getRecordFormatter();

    /**
     * @param recordFormatter recordFormatterを設定します。
     */
    public void setRecordFormatter(RecordFormatter recordFormatter);

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
}
