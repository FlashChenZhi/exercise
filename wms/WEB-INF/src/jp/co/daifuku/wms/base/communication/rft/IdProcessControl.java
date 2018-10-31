// $Id: IdProcessControl.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.base.communication.rft;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

/**
 * 要求電文受信後のID処理の振り分けを行う。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>A.Miyasita</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */
public class IdProcessControl
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**
     * コネクション
     */
    //Connection wConn = null;
    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**
     * ID振り分け処理のコンストラクタ。
     */
    public IdProcessControl()
    {
    }

    // Public methods ------------------------------------------------
    /**
     * ID振り分け処理を行う。<BR>
     * パッケージマネージャーから取得したパッケージリストと、
     * 引数で受け取ったIDからクラス名を生成し、その名前の処理クラスの
     * オブジェクトを生成する。<BR>
     * 生成したオブジェクトに対してDB接続オブジェクトをセットする。<BR>
     * 生成したオブジェクトに対して受信データを渡して処理を依頼する。<BR>
     * 対応するクラスのオブジェクトが生成できなかった場合、
     * 通信エラーとしてログにエラーメッセージを出力する。
     * 
     * @param id            受信電文のID番号 
     * @param recvByteArray 受信データSTX,ETX含む　
     * @param sendByteArray 送信データSTX,ETX含む　
     * @param dbConn        RFT号機毎のOracleDBConnection <code>Connection</code>の参照
     * @param rftNo         号機No
     * @return  正常時:true、異常時(例外発生時):False
     */
    public boolean executeIdProc(String id, byte[] recvByteArray, byte[] sendByteArray, Connection dbConn, String rftNo)
    {
        // 該当ID処理の起動を行う。
        IdProcess idProc = null;
        // 号機No文字列をセットした、要素数1のObject配列。エラーログで使用。
        Object[] rftArr = {
            rftNo
        };
        try
        {
            // 指定されたIDに対応するID処理クラスのインスタンスを生成する。
            String className = "Id" + id + "Process";
            idProc = (IdProcess)PackageManager.getObject(className, getClass());
            // DB接続オブジェクトを受け渡す。
            idProc.setConnection(dbConn);
        }
        catch (Exception e)
        {
            //6026104=<{0}号機> ＲＦＴサーバ通信モジュールで、IDプロセスクラスのインスタンス生成に失敗しました。{1}
            RmiMsgLogClient.write(new TraceHandler(6026104, e), getClass().getName(), rftArr);
            return false;
        }

        try
        {
            // 処理を実行する。
            // 同一のDBコネクションを使用して複数の処理が同時に実行されないように
            // 排他制御を行う。
            synchronized (dbConn)
            {
                idProc.processReceivedId(recvByteArray, sendByteArray);
            }
            return true;
        }
        catch (Exception e)
        {
            //6026105=<{0}号機> ID対応処理で処理されない例外が発生しました。電文クラスのインスタンス生成に失敗した可能性があります。{1}
            RmiMsgLogClient.write(new TraceHandler(6026105, e), getClass().getName(), rftArr);
            return false;
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class
