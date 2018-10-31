// $Id: HostIOUtil.java 3136 2009-02-12 03:55:39Z ota $
package jp.co.daifuku.wms.base.util;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.RecordFormatException;
import jp.co.daifuku.wms.handler.file.VariableFileHandler;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;

/**
 * <CODE>HostIOUtil</CODE>クラスは、VariableFileHandlerクラスを継承したクラスです。<BR>
 * Designer : ota <BR>
 * Maker : ota <BR>
 * @version $Revision: 3136 $, $Date: 2009-02-12 12:55:39 +0900 (木, 12 2 2009) $
 * @author  Last commit: $Author: ota $
 */
public class HostIOUtil
        extends VariableFileHandler
{

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // properties (prefix 'p_')
    //------------------------------------------------------------


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
    public HostIOUtil()
    {
        String dir = HandlerSysDefines.SEQFILE_DIR;
        if (dir == null || dir.length() == 0)
        {
            throw new RuntimeException("No directory defined for sequential file.");
        }
        setDirectory(dir);
    }

    /**
     * {@inheritDoc}
     */
    public Entity next(String rec)
            throws ReadWriteException,
                RecordFormatException
    {
        checkOpen();
        try
        {
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

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns current repository info for this class
     *
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }
}
