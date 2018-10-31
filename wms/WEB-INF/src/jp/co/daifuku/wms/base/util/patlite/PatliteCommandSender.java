// $Id: PatliteCommandSender.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.util.patlite;

import java.io.IOException;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.util.patlite.PatliteOperator.PatliteStatus;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * パトライト通信のプロトコルに応じてコマンドの送信を行う抽象クラスです。
 *
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  Softecs
 * @author  Last commit: $Author: admin $
 */

public abstract class PatliteCommandSender
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;
    private static final long INTVAL = 500;

    private static final int RETRY = 3;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private PatliteSettingHolder _settingHolder = null;

    private String _resultString = "";

    private String _id = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルトコンストラクタ。<br>
     * デフォルトコンストラクタは使用しません。
     */
    private PatliteCommandSender()
    {
        super();
    }

    /**
     * コンストラクタ。<br>
     * パトライト設定管理クラスを指定してインスタンス化します。
     * @param holder パトライト設定管理クラス
     */
    public PatliteCommandSender(PatliteSettingHolder holder)
    {
        super();
        _settingHolder = holder;
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * コマンド送信。<br>
     * パトライトへのコマンドを送信します。
     * @param id パトライトID
     * @param command 送信コマンド
     * @return コマンド送信ステータス
     */
    public PatliteStatus commandSend(String id, byte[] command)
    {
        setId(id);
        boolean success = false;
        for (int send = 0; send < RETRY; send++)
        {
            try
            {
                patliteOpen(id);
                PatliteStatus status = patliteSend(command);
                if (PatliteStatus.ACK == status)
                {
                    success = true;
                }
            }
            catch (IOException e)
            {
                //e.printStackTrace();
                //_resultString = "警告灯(" + id + ")の通信回線オープンに失敗しました";
                success = false;
            }
            finally
            {
                try
                {
                    patliteClose();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    // 6016102 = 致命的なエラーが発生しました。{0}
                    RmiMsgLogClient.write(new TraceHandler(6016102, e), this.getClass().getName());
                    //_resultString = "警告灯(" + id + ")との通信回線クローズ時にエラーを検出しました";
                    _resultString = "Error in Patlite(" + id + ") closing process.";
                    return PatliteStatus.NAK;
                }
            }
            if (success)
            {
                return PatliteStatus.ACK;
            }

            // コマンド送信失敗で、リトライします
            try
            {
                Thread.sleep(INTVAL);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        // 6016102 = 致命的なエラーが発生しました。{0}
        String[] message = {
            //"(リトライオーバー)"
                "(Retry over)"
            };
        RmiMsgLogClient.write(6016102, this.getClass().getName(), message);
        //_resultString = "警告灯(" + id + ")への指示コマンド送信でリトライオーバーとなりました";
        _resultString = "Retry over in Patlite(" + id + ") sending process.";
        return PatliteStatus.NAK;
    }

    /**
     * オープン。<br>
     * パトライトへの通信ポートをオープンします。
     * @param id パトライトID
     * @throws IOException オープン時に異常を検出した場合にスローされます
     */
    protected abstract void patliteOpen(String id)
            throws IOException;

    /**
     * クローズ。<br>
     * パトライトとの通信ポートをクローズします。
     * @throws IOException クローズ時に異常を検出した場合にスローされます
     */
    protected abstract void patliteClose()
            throws IOException;

    /**
     * コマンド送信。<br>
     * パトライトへのコマンドを送信します。
     * @param command 送信コマンド
     * @return コマンド送信ステータス
     */
    protected abstract PatliteStatus patliteSend(byte[] command);

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * パトライト設定管理クラスを返します。
     * @return パトライト設定管理クラス
     */
    protected PatliteSettingHolder getSettingHolder()
    {
        return _settingHolder;
    }

    /**
     * パトライト設定管理クラスを設定します。
     * @param holder パトライト設定管理クラス
     */
    protected void setSettingHolder(PatliteSettingHolder holder)
    {
        _settingHolder = holder;
    }

    /**
     * 結果文字列を返します。
     * @return 結果文字列
     */
    public String getResultString()
    {
        return _resultString;
    }

    /**
     * 結果文字列を設定します。
     * @param resultString 結果文字列
     */
    public void setResultString(String resultString)
    {
        _resultString = resultString;
    }

    /**
     * パトライトIDを返します。
     * @return パトライトID
     */
    protected String getId()
    {
        return _id;
    }

    /**
     * パトライトIDを設定します。
     * @param id パトライトID
     */
    private void setId(String id)
    {
        _id = id;
    }

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
        return "$Id: PatliteCommandSender.java 87 2008-10-04 03:07:38Z admin $";
    }
}
