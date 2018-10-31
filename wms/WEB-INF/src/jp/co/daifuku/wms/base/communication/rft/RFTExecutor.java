// $Id: RFTExecutor.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

/**
 * 通信モジュールを起動するクラス<br>
 *
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  taki
 * @author  Last commit: $Author: admin $
 */


public class RFTExecutor
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------


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
        return "$Id: RFTExecutor.java 87 2008-10-04 03:07:38Z admin $";
    }

    /**
     * 通信モジュールを起動します。
     * @param args 未使用
     */
    public static void main(String[] args)
    {
        try
        {
            // RFTとの接続用Utilityクラスのインスタンス獲得
            // 接続監視用スレッド作成＆起動
            RftWatcher rftwach = new RftWatcher();
            rftwach.start();
        }
        catch (Exception ex)
        {
            // 6026011=ＲＦＴサーバ通信モジュールの起動に失敗しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6026011, ex), "RftExecutor");
        }
    }
}
