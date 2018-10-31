//$Id: TraceFile.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.text.DecimalFormat;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RandomAcsFileHandler;
import jp.co.daifuku.wms.base.common.WmsParam;

/**
 * 通信トレースを記録するファイルを操作する。
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class TraceFile
{
    // Class fields --------------------------------------------------
    /**
     * リソース名の定義(トレースON)
     */
    public static final String TRACE_ON = "RFT_TRACE_ON";

    /**
     * リソース名の定義(トレースファイル名)
     */
    public static final String TRACE_NAME = "RFT_TRACE_NAME";

    /**
     * リソース名の定義(トレースポインタファイル名)
     */
    public static final String POINTER_NAME = "RFT_TRACE_POINTER_NAME";

    /**
     * リソース名の定義(トレースファイルのMaxサイズ(Byte))
     */
    public static final String MAX_SIZE = "TRACE_MAX_SIZE";

    /**
     * 受信トレース
     */
    public static final String RECIEVE = "R";

    /**
     * 送信トレース
     */
    public static final String SEND = "S";

    /**
     * ＲＦＴNo.のFormat 定義
     */
    public static final String RFT_FORMAT = "000";

    /**
     * RFT名を定義します。
     */
    public static final String RFT_NAME = "RFT";

    // Class variables -----------------------------------------------
    /**
     * トレースファイルを落とすディレクトリ
     */
    protected String LogDir;

    /**
     * RFTへの電文をトレースファイルに落とすか否かのフラグ
     */
    protected boolean sTrOn;

    /**
     * RFTへの電文トレースファイル名
     */
    protected String sTrName;

    /**
     * RFTへの電文トレースポインタファイル名
     */
    protected String sPntName;

    /**
     * RFTへの電文トレースファイルの為のLogHandler
     */
    protected RandomAcsFileHandler TrcHandler = null;

    /**
     * RFTとのトレースファイルの為のパラメータ
     */
    protected static Object[] trcParam = new Object[1];

    /**
     * トレースファイルのMaxサイズ(Byte)。
     */
    protected int maxFileSize;

    // Class method --------------------------------------------------
    // Constructors --------------------------------------------------
    /**
     * コンストラクタ
     * 通信トレース情報を作成する。
     * @param rftNumber RFT号機№
     */
    public TraceFile(int rftNumber)
    {
        // RFTNo.のFormat "000" にてコンストラクタ
        DecimalFormat dfmtRFT = new DecimalFormat(RFT_FORMAT);
        String swRFTNo = dfmtRFT.format(rftNumber);

        // トレースファイルを落とすディレクトリをセット
        LogDir = WmsParam.WMS_LOGS_PATH;

        // トレースファイルのMAXサイズをセット
        maxFileSize = WmsParam.RFT_TRACE_MAX_SIZE;

        // ＲＦＴ通信トレースファイルに落とすか否かのフラグをセット
        Boolean sTrOnB = new Boolean(WmsParam.RFT_TRACE_ON);
        sTrOn = sTrOnB.booleanValue();
        if (sTrOn)
        {
            // ＲＦＴ通信トレースファイル名をセット
            sTrName = LogDir + RFT_NAME + swRFTNo + WmsParam.RFT_TRACE_NAME;
            // ＲＦＴ通信トレースポインタファイル名をセット

            // 現在使用していませんが、将来拡張用のため消さないでください
            sPntName = LogDir + RFT_NAME + swRFTNo + WmsParam.RFT_TRACE_POINTER_NAME;
        }
    }

    // Public methods ------------------------------------------------
    /**
     * RFTとの通信内容をトレースする。
     * @param   type    トレース種別 S:送信 R:受信
     * @param   data    トレース内容
     */
    public void write(String type, String data)
    {
        try
        {
            if (sTrOn && (TrcHandler == null))
            {
                TrcHandler = new RandomAcsFileHandler(sTrName, maxFileSize);
            }
            if (!(TrcHandler == null))
            {
                synchronized (TrcHandler)
                {
                    trcParam[0] = data;
                    TrcHandler.writeUTF(0, LogMessage.F_INFO, type, trcParam);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error of Trace Log writeing !! [" + data + "]");
        }
    }
    // Package methods -----------------------------------------------
    // Protected methods ---------------------------------------------
    // Private methods -----------------------------------------------
}
//end of class
