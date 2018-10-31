// $Id: ClientTerminal.java 8075 2014-09-19 07:16:57Z okayama $
package jp.co.daifuku.wms.base.communication.rft;

/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * クライアント端末に関する情報を定義する。
 *
 * @version $Revision: 8075 $, $Date: 2014-09-19 16:16:57 +0900 (金, 19 9 2014) $
 * @author  $Author: okayama $
 */
public class ClientTerminal
{
    // Class fields --------------------------------------------------
    /**
     * ダミーRFT号機№
     */
    public static final Integer DUMMY_RFT_NO = new Integer(-1);

    /**
     * メッセージ
     */
    public static final int NEW_MESSAGE = 0;

    /**
     * メッセージ:同一電文処理中
     */
    public static final int MESSAGE_PROCESSING = 1;

    /**
     * メッセージ:同一電文処理済
     */
    public static final int MESSAGE_RESPONSED = 2;

    // Class variables -----------------------------------------------
    /**
     * 端末情報
     */
    private class ClientInfo
    {
        Connection conn;

        //SeqNoOperator seqNo;

        byte[] recvMsg = null;

        byte[] sendMsg = null;
    }

    Hashtable<Integer, ClientInfo> clientInfoTbl = new Hashtable<Integer, ClientInfo>();

    // Class method --------------------------------------------------
    //    /**
    //     * 受信データのRFT 号機No.をCheckし、整数型に変換した号機No.を返す。<BR>
    //     * 受信データからバイト列でRFT号機No.を切り出す。<BR>
    //     * 切り出した号機No.を文字列に変換する。<BR>
    //     * 文字列型の号機No.を整数型に変換する。<BR>
    //     * 変換できなかった場合、および号機No.が最小値から最大値の間でなかった場合は
    //     * RET_NGを返す。
    //     * @param rData 受信電文
    //     * @return 号機No.。エラーの場合はRET_NGを返す。
    //     * @throws    NumberFormatException   整数値に返還できない場合に通知されます。
    //     */
    //    public static int checkRftNo(byte[] rData)
    //            throws NumberFormatException
    //    {
    //        try
    //        {
    //            // 受信伝文中のRFT号機№を取り出し、Intに変換
    //            byte[] RftNoWk = new byte[IdMessageBase.LEN_RFTNO];
    //            for (int i = 0, j = IdMessageBase.OFF_RFTNO; i < IdMessageBase.LEN_RFTNO; i++, j++)
    //            {
    //                RftNoWk[i] = rData[j];
    //            }
    //            String RftNoString = new String(RftNoWk);
    //            int recvRftNoInt = Integer.parseInt(RftNoString.trim());
    //
    //            // RFT号機№をチェックする。
    //            if ((recvRftNoInt < MIN_RFT_NO) || (recvRftNoInt > MAX_RFT_NO))
    //            {
    //                return RET_NG;
    //            }
    //
    //            return (recvRftNoInt);
    //        }
    //        catch (NumberFormatException e)
    //        {
    //            return RET_NG;
    //        }
    //        catch (Exception e)
    //        {
    //            return RET_NG;
    //        }
    //    }

    // Constructors --------------------------------------------------
    /**
     * コンストラクタ
     * @throws SQLException データベースアクセスエラーまたはその他のエラーが発生した場合に通知されます。
     * @throws ReadWriteException データベースアクセスエラーが発生した場合に通知されます。
     */
    @SuppressWarnings("synthetic-access")
    public ClientTerminal() throws SQLException,
            ReadWriteException
    {
        Connection conn = null;
        try
        {
            RftSearchKey skey = new RftSearchKey();
            skey.setTerminalType(SystemDefine.TERMINAL_TYPE_PCART, "!=");
            skey.setRftNoOrder(true);
            conn = WmsParam.getConnection();
            RftHandler handler = new RftHandler(conn);
            Rft[] rftList = (Rft[])handler.find(skey);
            System.out.print("Entry terminal No. = ");
            for (int i = 0; i < rftList.length; i++)
            {
                ClientInfo cInfo = new ClientInfo();
                cInfo.conn = WmsParam.getConnection();
                //cInfo.seqNo = new SeqNoOperator();
                Integer intRft = new Integer(rftList[i].getRftNo());
                clientInfoTbl.put(intRft, cInfo);
                System.out.print(rftList[i].getRftNo() + " ");
            }
            System.out.println(" (total:" + rftList.length + ")");

            // ダミーのコネクションを1つ登録する。未登録のRFT号機Noの場合に使用する。
            ClientInfo cInfoDmy = new ClientInfo();
            cInfoDmy.conn = WmsParam.getConnection();
            clientInfoTbl.put(DUMMY_RFT_NO, cInfoDmy);
        }
        finally
        {
            if (conn != null && !conn.isClosed())
            {
                conn.close();
            }
        }
    }

    // Public methods ------------------------------------------------
    /**
     * 指定された号機用のDB接続コネクションを返します。
     * 設定されていなければ新たにコネクションを取得し、それを返します。
     * コネクションが無降の場合は新たにコネクションを取得し、それを返します。
     * @param rftNo    RFT号機No
     * @return         DB接続コネクション
     * @throws SQLException データベースアクセスエラーまたはその他のエラーが発生した場合に通知されます。
     */
    public Connection getConnection(int rftNo)
            throws SQLException
    {
        ClientInfo cInfo = clientInfoTbl.get(new Integer(rftNo));
        if (null == cInfo)
        {
            cInfo = clientInfoTbl.get(DUMMY_RFT_NO);
        }

        if (cInfo.conn == null || cInfo.conn.isClosed())
        {
            cInfo.conn = WmsParam.getConnection();
        }

        return cInfo.conn;
    }

    /**
     * 指定された号機用のシーケンスオペレータを返します。
     *
     * @param rftNo     RFT号機No
     * @return         シーケンスオペレータ
     */
    //    public SeqNoOperator getSeqNo(int rftNo)
    //    {
    //        ClientInfo cInfo = (ClientInfo)ClientInfoTbl.get(new Integer(rftNo));
    //        if (cInfo != null)
    //        {
    //            return cInfo.seqNo;
    //        }
    //        else
    //        {
    //            return null;
    //        }
    //    }
    /**
     * 受信した電文に対する処理状態をチェックします。
     *
     * @param rftNo    RFT号機No
     * @param msg      受信電文
     * @return         受信した電文と同一の電文を処理中の場合1、応答送信済みの場合2、
     *                  新規電文の場合0を返します。
     */
    public int checkStatus(int rftNo, byte[] msg)
    {
        int ret = NEW_MESSAGE;
        ClientInfo cInfo = clientInfoTbl.get(new Integer(rftNo));
        if (cInfo != null)
        {
            if (cInfo.recvMsg != null)
            {
                for (int i = 0; i < cInfo.recvMsg.length; i++)
                {
                    if (cInfo.recvMsg[i] != msg[i])
                    {
                        // 新規電文(1つでも違った時点でNew MessageなのでReturnする)
                        return NEW_MESSAGE;
                    }
                }

                if (cInfo.sendMsg == null)
                {
                    // 同一電文の処理中
                    ret = MESSAGE_PROCESSING;
                }
                else
                {
                    // 同一電文応答済み
                    ret = MESSAGE_RESPONSED;
                }
            }
        }
        else
        {
            ret = NEW_MESSAGE;
        }
        return ret;
    }

    /**
     * 受信した電文を号機ごとに保存します。
     * この時応答電文はクリアします。（処理中の状態にする）
     *
     * @param rftNo     RFT号機No
     * @param msg       受信電文
     */
    public void setReceivedMessage(int rftNo, byte[] msg)
    {
        ClientInfo cInfo = clientInfoTbl.get(new Integer(rftNo));
        if (cInfo != null)
        {
            cInfo.recvMsg = msg;
            cInfo.sendMsg = null;
        }
    }

    /**
     * 送信した電文を号機ごとに保存します。
     *
     * @param rftNo     RFT号機No
     * @param msg       送信電文
     */
    public void setSendedMessage(int rftNo, byte[] msg)
    {
        ClientInfo cInfo = clientInfoTbl.get(new Integer(rftNo));
        if (cInfo != null)
        {
            cInfo.sendMsg = msg;
        }
    }

    /**
     * 指定された号機の前回処理時に送信済みの応答電文を取得します。
     *
     * @param rftNo     RFT号機No
     * @return          送信済み応答
     */
    public byte[] getSendedMessage(int rftNo)
    {
        ClientInfo cInfo = clientInfoTbl.get(new Integer(rftNo));
        if (cInfo != null)
        {
            return cInfo.sendMsg;
        }
        else
        {
            return null;
        }
    }

    /**
     * DBコネクションの開放を行います。
     *
     * @throws SQLException データベースアクセスエラーまたはその他のエラーが発生した場合に通知されます。
     */
    @SuppressWarnings("rawtypes")
    public void closeConnections()
            throws SQLException
    {
        for (Enumeration e = clientInfoTbl.elements(); e.hasMoreElements();)
        {
            ClientInfo cInfo = (ClientInfo)e.nextElement();

            if (cInfo.conn != null && !cInfo.conn.isClosed())
            {
                cInfo.conn.rollback();
                cInfo.conn.close();
            }
        }
    }

    // Package methods -----------------------------------------------
    // Protected methods ---------------------------------------------
    /**
     * 使用しているコネクションが有効か確認します。
     * @param conn コネクション
     * @return true:コネクション無効　false:コネクション有効
     */
    @SuppressWarnings("static-method")
    protected boolean chkConnAlive(Connection conn)
    {
        Statement stmt = null;
        try
        {
            stmt = HandlerUtil.createStatement(conn, false, true);

            String sqlstring = "SELECT 1 FROM DUAL";

            DEBUG.MSG(DEBUG.HANDLER, sqlstring);
            stmt.executeQuery(sqlstring);
        }
        catch (SQLException e)
        {
            return true;
        }
        finally
        {
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (SQLException e)
            {
                // 失敗した場合は何もしない
            }
        }
        return false;
    }
    // Private methods -----------------------------------------------
}
//end of class
