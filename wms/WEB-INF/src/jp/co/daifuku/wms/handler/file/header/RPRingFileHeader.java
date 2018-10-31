// $Id: RPRingFileHeader.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file.header;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;


/**
 * 読み込みポインタを管理するリングファイル用のヘッダクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class RPRingFileHeader
        extends AbstractFileHeader
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
    private int _headerLength = 0;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルトコンストラクタ 
     */
    public RPRingFileHeader()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    public int getNextRead(boolean useHeader)
            throws ReadWriteException
    {
        setTop(false);
        // 現行読み込みポインタの保存
        int currRP = getReadPointer();
        // 次読み込みポイント取得
        int next = getNextPointer(currRP);

        // ファイルヘッダの読み込み
        read();

        int hRP = getReadPointer();
        int hWP = getWritePointer();

        // 読み込みポインタ復旧
        setReadPointer(currRP);

        int nrp = (useHeader) ? getHeaderNextRead(currRP, next, hRP, hWP)
                             : getLocalNextRead(currRP, next, hRP, hWP);

        return nrp;
    }

    /**
     * 次読み込みポインタを取得します。
     * @param currRP 現行読み込みポインタ
     * @param next 次読み込みポイント
     * @param hRP ヘッダの読み込みポインタ
     * @param hWP ヘッダの書き込みポインタ
     * @return 読み込みポイント
     * @throws ReadWriteException
     */
    private int getLocalNextRead(int currRP, int next, int hRP, int hWP)
            throws ReadWriteException
    {
        if (isBottom())
        {
            return RP_NO_RECORD_TO_READ;
        }
        setBottom((next == hWP));

        // 次読み込みポイント設定
        setReadPointer(next);

        return currRP;
    }

    /**
     * 次読み込みポインタを取得します。
     * @param currRP 現行読み込みポインタ
     * @param next 次読み込みポイント
     * @param hRP ヘッダの読み込みポインタ
     * @param hWP ヘッダの書き込みポインタ
     * @return 読み込みポイント
     * @throws ReadWriteException
     */
    private int getHeaderNextRead(int currRP, int next, int hRP, int hWP)
            throws ReadWriteException
    {
        if (hWP == hRP)
        {
            // 書き込みポインタに追いついたときはデータ無し
            return RP_NO_RECORD_TO_READ;
        }

        // 次読み込みポイント設定
        setReadPointer(next);

        // ヘッダの更新
        // 書き込みポインタがFULLになっていれば、
        // 現行読み込みポイントに変更(1行空きが出来た)
        if (hWP == RP_FILE_FULL)
        {
            setWritePointer(hRP);
        }
        write();

        return currRP;
    }

    /**
     * {@inheritDoc}
     */
    public int getPreviousRead()
            throws ReadWriteException
    {
        setBottom(false);
        if (isTop())
        {
            // 先頭(書き込みポインタ)まで読み込み済み
            return RP_NO_RECORD_TO_READ;
        }

        // 現行読み込みポインタの保存
        int currRP = getReadPointer();

        // 一つ前のポインタを計算
        int prev = getPrevPointer(currRP);

        // ヘッダのポインタを読み込み
        read();
        int hRP = getReadPointer();
        int hWP = getWritePointer();

        // 読み込みポインタを復元
        setReadPointer(currRP);

        // 書き込みポインタより前は新しいデータ
        // ファイルフルの時は、読み込みポインタと比較
        int actWP = (hWP == RP_FILE_FULL) ? hRP
                                         : hWP;
        setTop((prev == actWP));

        // 現行ポインタを設定
        setReadPointer(prev);

        return prev;
    }

    /**
     * {@inheritDoc}
     */
    public int getNextWrite()
            throws ReadWriteException
    {
        // ファイルポインタの読み込み
        read();

        // 現行書き込みポインタの取得
        int currWP = getWritePointer();
        if (currWP == RP_FILE_FULL)
        {
            return RP_FILE_FULL;
        }

        // 現行読み込みポインタの取得
        int rdRP = getReadPointer();

        // ファイル終端判定
        int next = getNextPointer(currWP);
        if (next == rdRP)
        {
            // 読み込みポインタに追いついたときは空き無し
            // (次回書き込みが失敗する)
            next = RP_FILE_FULL;
        }

        // 書き込みポインタの保存
        setWritePointer(next);
        write();

        return currWP;
    }

    /**
     * {@inheritDoc}
     */
    public int length()
    {
        if (_headerLength == 0)
        {
            // TODO support charset for future.
            int hLength = HEADER_VERSION.getBytes().length;
            hLength += POINTER_LENGTH * 2;
            hLength += LINE_SEPARATOR.getBytes().length;

            _headerLength = hLength;
        }
        return _headerLength;
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
        return "$Id: RPRingFileHeader.java 87 2008-10-04 03:07:38Z admin $";
    }
}
