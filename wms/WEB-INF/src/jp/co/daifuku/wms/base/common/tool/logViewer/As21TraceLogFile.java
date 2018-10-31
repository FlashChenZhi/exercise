package jp.co.daifuku.wms.base.common.tool.logViewer;

/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.EOFException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Vector;

import jp.co.daifuku.common.DateOperator;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RandomAcsFileHandler;


/**
 * 通信トレースログ一情報を取得し、<BR>
 * 通信トレースログ一覧画面にて指定された検索条件より
 * 表示対象となる情報を検索し、通信トレースログデータクラスへセットし、
 * 通信トレースログデータクラスより、通信トレースログ一覧クラスへ
 * 情報の引渡しを行います。
 * 
 * <BR>
 * @version $Revision: 7041 $, $Date: 2010-02-17 11:44:19 +0900 (水, 17 2 2010) $
 * @author  $Author: kumano $
 */

public class As21TraceLogFile
        extends TraceLogFile
{
    // Class fields --------------------------------------------------

    /**
     * 電文用トレースログファイル名
     */
    protected final static String AS21TraceLogFileName = "Trace.txt";

    /**
     * デフォルトのIDの桁数
     */
    protected final static int DefaultIdFigure = 2;

    //-----------------
    //Class Method
    //-----------------
    public As21TraceLogFile()
    {
        super();
    }

    /**
     * 通信トレースログデータを取得し、指定された条件に合致する
     * データを通信トレースログデータクラスへセットし、通信トレースログ
     * 一覧クラスへセットします。
     * 
     * @return 通信トレースログ一覧
     * @throws Exception 全ての例外を報告します。 
     */
    public TraceList getTraceData()
            throws Exception
    {
        String fileName = null;

        // ファイル名の作成
        fileName = rftNo + LogViewerParam.AS21TraceLogFileName;

        return getTraceData(fileName);
    }

    /**
     * 通信トレースログデータを取得し、指定された条件に合致する
     * データを通信トレースログデータクラスへセットし、通信トレースログ
     * 一覧クラスへセットします。
     * 
     * @param fileName ファイル名
     * @return 通信トレースログデータ
     * @throws Exception 全ての例外を報告します。 
     */
    public TraceList getTraceData(String fileName)
            throws Exception
    {
        // 書き込みカウンタの初期化
        int count = 0;

        int maxDisplay = MAX_DISPLAY;

        try
        {
            maxDisplay = LogViewerParam.MaxLineCnt;
        }
        catch (NumberFormatException e)
        {
        }

        // トレースファイル格納変数
        RandomAcsFileHandler traceFile = null;

        TraceList cFile = null;

        try
        {
            // ファイル名の作成
            traceFileName = LogViewerParam.RftLogPath + "\\" + fileName;

            // ファイルの読み込み
            traceFile = new RandomAcsFileHandler(traceFileName, TRACE_FILE_MAX_SIZE);

            // ファイル読み込み終了位置取得
            int readEnd = traceFile.getSize();

            // ReadEndが負の場合はエラー処理
            if (readEnd < 0)
            {
                String param[] = {
                    fileName
                };
                String msg = MessageResourceFile.getText("6006020");
                msg = MessageFormat.format(msg, param);
                throw new Exception(msg);
            }
            else if (readEnd == 0)
            {
                return null;
            }

            // ファイルをオープン
            boolean openFlg = true;

            if (traceFile.readOpen(openFlg) == false)
            {
                throw new Exception("6003011");
            }

            // ファイル内格納Vector
            Vector vec = new Vector();
            vec = traceFile.read(READ_START, readEnd);

            // LogMessage型配列にファイル内容をセット
            LogMessage[] msg = new LogMessage[vec.size()];
            vec.copyInto(msg);

            // ファイルのクローズ
            traceFile.readClose();

            // 該当件数が最大表示件数を超えたか判断するﾌﾗｸﾞ
            boolean isOverMaxDisplay = false;

            cFile = new TraceList(msg.length);

            // 日付が空白の場合
            if (startProcessDate == null)
            {
                startProcessDate = "";
            }
            if (startProcessTime == null)
            {
                startProcessTime = "";
            }
            if (endProcessDate == null)
            {
                endProcessDate = "";
            }
            if (endProcessTime == null)
            {
                endProcessTime = "";
            }

            // 開始日時、終了日時を取得
            java.util.Date startDate = getDate(startProcessDate, startProcessTime, true);
            java.util.Date endDate = getDate(endProcessDate, endProcessTime, false);

            // nullの場合空白に置き換える
            if (idNo1 == null)
            {
                idNo1 = "";
            }
            if (idNo2 == null)
            {
                idNo2 = "";
            }

            // 配列のサイズでループさせる
            for (int i = 0; i < msg.length; i++)
            {

                // 配列内データの確保
                // 書き込み時間
                java.util.Date writeDate = msg[i].getDate();

                // 送受信区分
                String sendReceiveFlag = null;
                // メッセージ番号が0の時は、送信
                if (0 == msg[i].getMessageNumber())
                {
                    sendReceiveFlag = "S";
                }
                // メッセージ番号が1(その他)の時は、受信
                else
                {
                    sendReceiveFlag = "R";
                }
                //String sendReceiveFlag = Integer.toString(msg[i].getMessageNumber());
                // 電文
                byte[] communicateMessage = null;

                // 作業Bufferの生成
                byte[] byteBuf = new String(msg[i].getMessage().getBytes("8859_1"), "SJIS").getBytes();

                // STX,ETXをそれぞれ"[","]"に変換
                if (byteBuf.length != 0)
                {

                    for (int j = 0; j < byteBuf.length; j++)
                    {
                        // STEが見つかった時
                        if (byteBuf[j] == STX)
                        {
                            byteBuf[j] = START_BYTE;
                        }
                        if (byteBuf[j] == ETX)
                        {
                            byteBuf[j] = END_BYTE;
                            communicateMessage = byteBuf;
                            break;
                        }
                        communicateMessage = byteBuf;
                    }

                    // 送受信表示用変数
                    String sendReceive = "";

                    // 処理続行フラグ
                    boolean next = false;

                    // 日付チェック
                    if (startDate != null)
                    {
                        if (endDate == null)
                        {
                            // 開始日時のみ入力されている場合
                            if (startDate.compareTo(writeDate) <= 0)
                            {
                                // 続行フラグON
                                next = true;
                            }
                            else
                            {
                                // 続行フラグOFF
                                next = false;
                            }
                        }
                        else
                        {
                            // 開始日時、終了日時両方入力されている場合
                            if (startDate.compareTo(writeDate) <= 0 && endDate.compareTo(writeDate) > 0)
                            {
                                // 続行フラグON
                                next = true;
                            }
                            else
                            {
                                // 続行フラグOFF
                                next = false;
                            }
                        }
                    }
                    else
                    {
                        if (endDate == null)
                        {
                            // 日時入力なしの場合
                            next = true;
                        }
                        else
                        {
                            // 終了日時のみ入力されている場合
                            if (endDate.compareTo(writeDate) > 0)
                            {
                                // 続行フラグON
                                next = true;
                            }
                            else
                            {
                                // 続行フラグOFF
                                next = false;
                            }
                        }
                    }

                    // 電文からID番号を取得する
                    byte idbuf[] = new byte[IdLength];
                    for (int j = 0; j < IdLength; j++)
                    {
                        idbuf[j] = communicateMessage[j + 4];
                    }
                    String id = new String(idbuf);

                    // 続行フラグがONの場合、次の処理に進む
                    if (next == true)
                    {
                        // idNoのチェック
                        if (idNo1.length() != 0)
                        {
                            if (id.equals(idNo1) || id.equals(idNo2))
                            {
                                next = true;
                            }
                            else
                            {
                                next = false;
                            }
                        }
                        else if (idNo2.length() != 0)
                        {
                            if (id.equals(idNo2))
                            {
                                next = true;
                            }
                            else
                            {
                                next = false;
                            }

                        }
                        else
                        {
                            next = true;
                        }
                    }

                    // 続行ﾌﾗｸﾞがON
                    if (next == true)
                    {
                        // 送受信ﾌﾗｸﾞの変換
                        sendReceive = sendReceiveFlag;

                        if (!isOverMaxDisplay)
                        {
                            // 書き込みカウンタに１プラス
                            count++;

                            // 書き込み最大数を超えた場合
                            if (count > maxDisplay)
                            {
                                isOverMaxDisplay = true;
                            }
                            else
                            {

                                // 表示データを保持
                                TraceData data = new TraceData();
                                data.setProcessDate(DateOperator.changeDate(writeDate));
                                data.setProcessTime(DateOperator.changeDateTimeMillis(writeDate));
                                data.setSendRecvDivision(sendReceive);
                                data.setIdNo(new String(id));
                                data.setCommunicateMessage(communicateMessage);

                                cFile.add(data);
                            }
                        }
                        else
                        {
                            LogViewer.setTraceLogFileDispCnt(count);
                            return cFile;
                        }
                    }
                }
                else
                {
                    LogViewer.setTraceLogFileDispCnt(count);
                    return cFile;
                }
            }
            LogViewer.setTraceLogFileDispCnt(count);
            if (count == 0)
            {
                return null;
            }

            return cFile;
        }
        catch (EOFException e1)
        {
            String param[] = {
                fileName
            };
            String msg = "";
            msg = MessageResourceFile.getText("6006020");
            msg = MessageFormat.format(msg, param);
            throw new Exception(msg);
        }
        catch (IOException e1)
        {
            // ダイアログにエラーメッセージを表示する
            e1.printStackTrace();
        }

        return null;
    }

    /**
     * 送信データと受信データのマージを行います。
     * 
     * @param sendData 送信データ
     * @param recvData 受信データ
     * @return 通信トレースログ一覧
     * @throws Exception 全ての例外を報告します。 
     */
    public TraceList merge(TraceList sendData, TraceList recvData)
            throws Exception
    {
        // マージするリストが片方しかなかった場合はそれをそのまま返す。
        if (sendData == null)
        {
            if (recvData == null)
            {
                // 検索の結果、該当データがなかった場合は対象データなしを返す。
                throw new Exception("6003011");
            }

            for (int i = 0; i < recvData.getSize(); i++)
            {
                recvData.getTraceData(i).setSendRecvDivision("R");
            }
            return recvData;
        }
        else if (recvData == null)
        {
            for (int i = 0; i < sendData.getSize(); i++)
            {
                sendData.getTraceData(i).setSendRecvDivision("S");
            }
            return sendData;
        }

        TraceList mergedList = new TraceList();
        int sendIndex = 0;
        int recvIndex = 0;
        int sendDataSize = sendData.getSize();
        int recvDataSize = recvData.getSize();

        int maxDisplay = LogViewerParam.MaxLineCnt;

        for (int i = 0; i < maxDisplay; i++)
        {
            TraceData send = null;
            TraceData recv = null;
            if (sendIndex < sendDataSize)
            {
                send = sendData.getTraceData(sendIndex);
                send.setSendRecvDivision("S");
            }
            if (recvIndex < recvDataSize)
            {
                recv = recvData.getTraceData(recvIndex);
                recv.setSendRecvDivision("R");
            }

            if (send == null && recv == null)
            {
                // どちらのリストにもデータがなかった場合にはマージ処理を終了する。
                break;
            }

            if (send == null)
            {
                mergedList.add(recv);
                recvIndex++;
                continue;
            }
            else if (recv == null)
            {
                mergedList.add(send);
                sendIndex++;
                continue;
            }

            if (send.getProcessDate().compareTo(recv.getProcessDate()) > 0)
            {
                mergedList.add(send);
                sendIndex++;
            }
            else if (send.getProcessDate().compareTo(recv.getProcessDate()) == 0)
            {
                if (send.getProcessTime().compareTo(recv.getProcessTime()) > 0)
                {
                    mergedList.add(send);
                    sendIndex++;
                }
                else
                {
                    mergedList.add(recv);
                    recvIndex++;
                }
            }
            if (send.getProcessDate().compareTo(recv.getProcessDate()) < 0)
            {
                mergedList.add(recv);
                recvIndex++;
            }
        }

        return mergedList;
    }
}
