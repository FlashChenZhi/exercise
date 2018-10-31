// $Id: FileHeader.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file.header;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * 固定長ファイルのためのファイルヘッダを管理するインターフェースです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public interface FileHeader
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 読み込みポインタ (読み込み対象レコード無し) */
    public static final int RP_NO_RECORD_TO_READ = -1;

    /** 書き込みポインタ (ファイルフル) */
    public static final int RP_FILE_FULL = -1;

    /** ヘッダ内の読み込み・書き込みポインタのバイト数 */
    public static final int POINTER_LENGTH = 18;

    /** 読み込みポインタのフォーマットパターン */
    public static final String FMT_READ_POINTER = "  READ: 0000000000;  READ:-0000000000";

    /** 書き込みポインタのフォーマットパターン */
    public static final String FMT_WRITE_POINTER = " WRITE: 0000000000; WRITE:-0000000000";

    /** このヘッダのバージョン */
    public static final String HEADER_VERSION = "v03.04";

    /** 改行コード */
    public static final String LINE_SEPARATOR = FileHandler.LINE_SEPARATOR;

    /** ポインタ初期値 */
    public static final int POINTER_INIT = 0;

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------
    // private String p_Name ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * ファイルヘッダを読み込みます。
     * @throws ReadWriteException 
     */
    public void read()
            throws ReadWriteException;

    /**
     * ファイルヘッダを書き込みます。
     * @throws ReadWriteException 
     */
    public void write()
            throws ReadWriteException;

    /**
     * ファイルヘッダを初期化します。
     * @throws ReadWriteException 
     */
    public void init()
            throws ReadWriteException;

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * 読み込みポインタを取得します。
     * @return 現在の読み込みポインタ (先頭=0)
     */
    public int getReadPointer();

    /**
     * 読み込みポインタを設定します。
     * @param rp 設定する読み込みポインタ (先頭=0)
     */
    public void setReadPointer(int rp);

    /**
     * 書き込みポインタを取得します。
     * @return 現在の書き込みポインタ (先頭=0)
     */
    public int getWritePointer();

    /**
     * 書き込みポインタを設定します。
     * @param wp 設定する書き込みポインタ (先頭=0)
     */
    public void setWritePointer(int wp);

    /**
     * 次の読み込みポインタを返します。<br>
     * このメソッドを呼び出した後、読み込みポインタは1つ進みます。
     * @param useHeader ヘッダを更新する場合は<code>true</code>を返します。
     * @return 次に読み込みを行うポインタ
     * @throws ReadWriteException 
     */
    public int getNextRead(boolean useHeader)
            throws ReadWriteException;

    /**
     * 前の読み込みポインタを返します。<br>
     * このメソッドを呼び出した後、読み込みポインタは1つ戻ります。<br>
     * ヘッダは更新されません。
     * @return 次に読み込みを行うポインタ
     * @throws ReadWriteException 
     */
    public int getPreviousRead()
            throws ReadWriteException;

    /**
     * 次の書き込みポインタを返します。<br>
     * このメソッドを呼び出した後、書き込みポインタは1つ進みます。
     * @return 次に書き込みを行うポインタ
     * @throws ReadWriteException 
     */
    public int getNextWrite()
            throws ReadWriteException;

    /**
     * ヘッダの長さを返します。
     * @return ヘッダのバイト数
     */
    public int length();

    /**
     * 最後のレコードに達したかチェックします。
     * @return 最後のレコードに達した場合は<code>true</code>を返します。
     */
    public boolean isBottom();

    /**
     * 最後のレコードに達したかどうかを設定します。
     * @param bottom 最後のレコードに達した場合に<code>true</code>を設定します。
     */
    public void setBottom(boolean bottom);

    /**
     * 先頭レコードかチェックします。
     * @return 先頭レコードであれば<code>true</code>を返します。
     */
    public boolean isTop();

    /**
     * 先頭レコードかどうかを設定します。
     * @param top 先頭レコードの場合に<code>true</code>を設定します。
     */
    public void setTop(boolean top);
}
