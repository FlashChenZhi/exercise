// $Id: RingFileHeader.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.handler.file.header;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;


/**
 * 読み込みポインタを管理しないリングファイル用のヘッダクラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  ss
 * @author  Last commit: $Author: admin $
 */


public class RingFileHeader
        extends RPRingFileHeader
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルトコンストラクタ
     */
    public RingFileHeader()
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
        if (isBottom())
        {
            return RP_NO_RECORD_TO_READ;
        }

        // 現行読み込みポインタの保存
        int currRP = getReadPointer();

        if (currRP < 0)
        {
            currRP = 0;
        }

        // ファイル終端判定
        int next = getNextPointer(currRP);

        if (useHeader)
        {
            // 現行書き込みポインタの取得
            read();
            // 読み込みポインタの復元
            setReadPointer(currRP);

            int hWP = getWritePointer();
            setBottom((next == hWP));
        }

        setReadPointer(next);

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
            return RP_NO_RECORD_TO_READ;
        }

        int prev = 0;

        // 現行読み込みポインタの保存
        int currRP = getReadPointer();

        read();

        int hWP = getWritePointer();
        int hRP = getReadPointer();

        // リング内がまだすべて有効データでない時 true.
        boolean notFill = (hRP == RP_NO_RECORD_TO_READ);

        // 一つ前のポインタを計算
        prev = currRP - 1;
        if (prev < 0)
        {
            if (notFill)
            {
                // リング内に未確定データがあるのでループしない
                return RP_NO_RECORD_TO_READ;
            }
            prev = getMaxRecord() - 1;
        }

        setTop((prev == hWP));

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
        // 読み込みポインタの保存
        int currRP = getReadPointer();

        // ファイルポインタの読み込み
        read();

        // 現行書き込みポインタの取得
        int currWP = getWritePointer();

        // ファイル終端判定
        int next = getNextPointer(currWP);

        // 書き込みポインタが先頭に戻った場合または
        // すでにリングファイルがすべて有効のときは
        // 読み込みポインタを書き込みポインタに合わせる
        if (next == 0 || getReadPointer() != RP_NO_RECORD_TO_READ)
        {
            setReadPointer(next);
        }

        // ヘッダの書き込み
        setWritePointer(next);
        write();

        // 読み込みポインタの復元
        setReadPointer(currRP);

        return currWP;
    }

    /**
     * {@inheritDoc}
     */
    public void init()
            throws ReadWriteException
    {
        setReadPointer(RP_NO_RECORD_TO_READ);
        setWritePointer(POINTER_INIT);

        setTop(false);
        setBottom(false);

        write();
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
     * ヘッダレコードを更新します。
     * @param rp 読み込みレコードNo.
     * @param wp 書き込みレコードNo.
     * @throws ReadWriteException
     */
    protected void updateHeader(int rp, int wp)
            throws ReadWriteException
    {
        // do nothing.
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
        return "$Id: RingFileHeader.java 87 2008-10-04 03:07:38Z admin $";
    }
}
