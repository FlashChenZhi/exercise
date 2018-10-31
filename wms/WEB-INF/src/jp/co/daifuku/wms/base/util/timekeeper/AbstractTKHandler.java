// $Id: AbstractTKHandler.java 983 2008-11-19 00:24:32Z mshimizu@softecs.jp $
// $LastChangedRevision: 983 $

package jp.co.daifuku.wms.base.util.timekeeper;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.Closeable;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;

/**
 * Timekeeperに関連するファイルのハンドリングを行うクラスの
 * ための仮想スーパークラスです。
 *
 *
 * @version $Revision: 983 $, $Date: 2008-11-19 09:24:32 +0900 (水, 19 11 2008) $
 * @author  ss
 * @author  Last commit: $Author: mshimizu@softecs.jp $
 */

public abstract class AbstractTKHandler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // process at class loaded.
    //------------------------------------------------------------
    // static
    // {
    // TODO initialize class
    // }

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 特別な処理は行いません。
     */
    protected AbstractTKHandler()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    /**
     * release a lock.
     * 
     * @param lock release target.
     */
    protected void release(final FileLock lock)
    {
        try
        {
            if (null != lock)
            {
                lock.release();
            }
        }
        catch (final IOException e)
        {
            // ignore release error
        }
    }

    /**
     * close a stream.
     * @param target for close.
     */
    protected void close(final Closeable target)
    {
        close(target, null);
    }

    /**
     * close a stream or random access file.
     * @param target for close.
     * @param raf random access file.
     */
    protected void close(final Closeable target, RandomAccessFile raf)
    {
        try
        {
            if (null != target)
            {
                target.close();
            }
            else if (null != raf)
            {
                raf.close();
            }
        }
        catch (final IOException e)
        {
            // ignore close error
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
        return "$Id: AbstractTKHandler.java 983 2008-11-19 00:24:32Z mshimizu@softecs.jp $";
    }
}
