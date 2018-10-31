// $Id: $
// $LastChangedRevision: $

package jp.co.daifuku.wms.base.util.timekeeper;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 実行ログファイルの排他オブジェクトクラスです。
 *
 * @version $Revision: $, $Date: $
 * @author  Softecs
 * @author  Last commit: $Author: $
 */

public class ExecuteLogMutex
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
    private static class Instance
    {
        static final ExecuteLogMutex mutex = new ExecuteLogMutex();
    }

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    ExecuteLogMutex()
    {
        // コンストラクタでは何もしません。
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * このクラスのインスタンスを取得します。
     *
     * @return ExecuteLogMutexインスタンス
     */
    public static ExecuteLogMutex getInstance()
    {
        return Instance.mutex;
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
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
        return "$Id$";
    }
}
