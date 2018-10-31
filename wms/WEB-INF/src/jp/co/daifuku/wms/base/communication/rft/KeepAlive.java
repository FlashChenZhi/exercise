// $Id: KeepAlive.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.RftAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.Rft;


/**
 * 端末の生存確認を行うクラスです。<BR>
 * pingコマンドを起動し、その出力を解析することで端末の生存確認を行います。<BR>
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  taki
 * @author  Last commit: $Author: admin $
 */
public class KeepAlive
        extends Thread
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * デフォルトのポーリング時間(秒）（WMSparamから取得出来なかった場合に使用）
     */
    private static final int DEFAULT_POLLING_TIME = 60;

    /**
     * Pingコマンド名
     */
    private static final String PING_COMMAND = "ping.exe";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * データベースとの接続コネクション
     */
    private Connection _conn = null;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     */
    public KeepAlive()
    {
        super();
        try
        {
            // データベース接続コネクションの取得
            _conn = WmsParam.getConnection();
            Runtime.getRuntime().addShutdownHook(new Thread()
            {
                public void run()
                {
                    closeConnection();
                }
            });
        }
        catch (SQLException ex)
        {
            // エラーをログファイルに落とす
            RmiMsgLogClient.write(new TraceHandler(6026012, ex), getClass().getSimpleName());
        }
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * <BR>
     * 生存確認を行う対象の端末は起動中のピッキングカートのみとします。
     * 応答がなかった場合、RFT情報の無線フラグを1に更新します。
     * <BR>
     * 生存確認を有効/無効のフラグおよび間隔はWMSparamから取得します。
     * 有効な場合、その間隔ごとに以下の処理を実行します。
     * 
     * <OL>
     *  <LI>RFT情報を検索し、ピッキングカートのIPアドレスのリストを取得します。
     *       （{@link #getTerminalList() getTerminalList()}）</LI>
     *  <LI>取得したIPアドレスに対し、pingを実行して通信可能かどうかの確認を行います。
     *       （{@link #isReachable(String) isReachable()}）</LI>
     *  <LI>取得した通信状態でRFT情報を更新します。
     *       （{@link #updateCommunicationCondition(Rft) updateCommunicationCondition()}）</LI>
     * </OL>
     * 
     */
    public void run()
    {
        //<jp> 6020036=生存確認監視処理を起動します。</jp>
        //<en> </en>
        RmiMsgLogClient.write(6020036, getClass().getSimpleName());

        // ポーリング間隔
        int polling;
        try
        {
            polling = WmsParam.RFT_KEEP_ALIVE_POLLING_TIME;
        }
        catch (NumberFormatException e)
        {
            // ポーリング時間の取得に失敗した場合はデフォルト値を使う
            polling = DEFAULT_POLLING_TIME;
        }

        try
        {
            while (true)
            {
                if (reConnect())
                {
                    // 起動中のピッキングカートのリスト取得
                    Rft[] rftInfoList = (Rft[])getTerminalList();
                    if (rftInfoList != null)
                    {
                        for (Rft rft : rftInfoList)
                        {
    
                            /* 通信可能かどうかチェック */
                            boolean isPossible = false;
                            try
                            {
                                InetAddress address = InetAddress.getByName(rft.getIpAddress());
                                isPossible = isReachable(address.getHostAddress());
                            }
                            catch (UnknownHostException e)
                            {
                                //IP アドレスが不正な場合
                                continue;
                            }
    
                            /* 無線状態フラグの更新 */
                            if (isPossible)
                            {
                                // 通信可能の場合
                                rft.setRadioFlag(Rft.RADIO_FLAG_IN);
                            }
                            else
                            {
                                // 通信不可能な場合
                                rft.setRadioFlag(Rft.RADIO_FLAG_OUT);
                            }
                            updateCommunicationCondition(rft);
                        }
                    }
                }
                // 定義された間隔だけ待機
                Thread.sleep(polling * 1000);
            }
        }
        catch (InterruptedException ex)
        {
            // 割り込みがあった場合はスレッドを終了する
        }

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
    /**
     * コネクションの再接続を行います。
     * 
     * @return true:コネクション取得成功 false:コネクション取得失敗
     */
    protected boolean reConnect()
    {
        try
        {
            if (_conn == null || _conn.isClosed())
            {
                _conn = WmsParam.getConnection();
            }
            return true;
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.write(new TraceHandler(6006002, e), getClass().getSimpleName());
            return false;
        }
    }

    /**
     * 通信状態を確認をする端末情報の一覧を取得します。<BR>
     * RFT情報を検索し、起動中のピッキングカートの一覧を取得します。<BR>
     * <BR>
     * [検索条件]
     * 状態フラグ: 起動中
     * 端末区分: ピッキングカート
     * 
     * @return  RFT情報の配列
     */
    protected Rft[] getTerminalList()
    {
        RftHandler rftHandler = new RftHandler(_conn);
        RftSearchKey skey = new RftSearchKey();
        Rft[] terminalList = null;

        try
        {
            //-----------
            // 検索条件
            //-----------
            skey.setStatusFlag(Rft.RFT_STATUS_FLAG_START);
            skey.setTerminalType(Rft.TERMINAL_TYPE_PCART);

            terminalList = (Rft[])rftHandler.find(skey);
        }
        catch (ReadWriteException ex)
        {
            RmiMsgLogClient.write(new TraceHandler(6026012, ex), getClass().getSimpleName());
        }
        return terminalList;
    }

    /**
     * RFT情報の通信状態を更新します。<BR>
     * <BR>
     * [検索条件] <BR>
     * 端末No <BR>
     *
     * <BR>
     * [更新内容] <BR>
     * 無線状態フラグ<BR>
     * 最終更新処理名
     * 
     * @param rftInfo   端末情報
     */
    protected void updateCommunicationCondition(Rft rftInfo)
    {
        RftHandler rftHandler = new RftHandler(_conn);
        RftAlterKey akey = new RftAlterKey();
        try
        {
            //-----------------
            // 検索条件
            //-----------------
            akey.setRftNo(rftInfo.getRftNo());
            //-----------------
            // 更新内容
            //-----------------
            akey.updateRadioFlag(rftInfo.getRadioFlag());
            akey.updateLastUpdatePname(getClass().getSimpleName());

            // 通信可能状態フラグの更新
            rftHandler.modify(akey);

            _conn.commit();
        }
        catch (ReadWriteException ex)
        {
            RmiMsgLogClient.write(new TraceHandler(6026012, ex), getClass().getSimpleName());
        }
        catch (NotFoundException ex)
        {
            RmiMsgLogClient.write(new TraceHandler(6026012, ex), getClass().getSimpleName());
        }
        catch (SQLException ex)
        {
            RmiMsgLogClient.write(new TraceHandler(6026012, ex), getClass().getSimpleName());
        }
    }

    /**
     * 指定されたIPアドレスの端末が通信可能状態であるかどうかを調べます。
     * 
     * @param ipAddress     IPアドレス
     * @return      通信可能な場合true、そうでない場合falseを返します。
     */
    public boolean isReachable(String ipAddress)
    {
        String line;

        try
        {
            // pingコマンドを実行する
            Process proc = Runtime.getRuntime().exec(PING_COMMAND + " " + ipAddress);

            // pingコマンドの出力を読み込む
            InputStream in = proc.getInputStream();
            BufferedReader din = new BufferedReader(new InputStreamReader(in));
            while ((line = din.readLine()) != null)
            {
                // pingの出力から、応答があったかどうかをチェックする
                boolean isAlive = line.matches(".*[rR][eE][pP][lL][yY] [fF][rR][oO][mM].*");
                if (isAlive)
                {
                    return true;
                }
            }
        }
        catch (IOException ex)
        {
            return false;
        }
        catch (IllegalArgumentException ex)
        {
            return false;
        }

        return false;
    }

    /**
     * コネクション開放処理
     *
     */
    protected void closeConnection()
    {
        if (_conn == null)
        {
            return;
        }
        try
        {
            _conn.close();
            //<jp> 6020037=生存確認監視処理を停止します。</jp>
            //<en> </en>
            RmiMsgLogClient.write(6020037, getClass().getSimpleName());

        }
        catch (SQLException ex)
        {
            RmiMsgLogClient.write(new TraceHandler(6026012, ex), getClass().getSimpleName());
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
        return "$Id: KeepAlive.java 87 2008-10-04 03:07:38Z admin $";
    }
}
