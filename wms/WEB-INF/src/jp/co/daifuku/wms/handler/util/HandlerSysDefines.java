// $Id: HandlerSysDefines.java 3153 2009-02-12 07:38:22Z tanaka $
package jp.co.daifuku.wms.handler.util;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.CommonParam;

/**
 * Handlerで参照されるシステム定義を記述します。
 *
 * @version $Revision: 3153 $, $Date: 2009-02-12 16:38:22 +0900 (木, 12 2 2009) $
 * @author  ss
 * @author  Last commit: $Author: tanaka $
 */
public class HandlerSysDefines
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------ 

    /** シーケンシャルファイルのデフォルト位置 */
    public static final String SEQFILE_DIR = CommonParam.getParam("SEQFILE_DIR");

    /** ハンドラ定義ファイルのデフォルト位置 */
    public static final String DEFINE_DIR = CommonParam.getParam("HANDLER_DEFINE_DIR");

    /** システムのワイルドカード */
    public static final String PATTERNMATCHING_CHAR = CommonParam.getParam("PATTERNMATCHING_CHAR");

    /** ロック待ち時間 (待ち時間なし) */
    public static final int WAIT_SEC_NOWAIT = -1;

    /** ロック待ち時間 (無期限) */
    public static final int WAIT_SEC_UNLIMITED = 0;

    /** デフォルトのロック待ち時間 (秒数) */
    public static final int WAIT_SEC_DEFAULT = CommonParam.getIntParam("HANDLER_DB_LOCK_TIMEOUT");

    /** リストボックス用最大取得件数 */
    public static final int MAX_NUMBER_OF_DISP_LISTBOX = CommonParam.getIntParam("MAX_NUMBER_OF_DISP_LISTBOX");

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private String $classVar ;

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

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

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
        return "$Id: HandlerSysDefines.java 3153 2009-02-12 07:38:22Z tanaka $";
    }
}
