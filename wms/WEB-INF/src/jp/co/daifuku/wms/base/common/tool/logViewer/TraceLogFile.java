package jp.co.daifuku.wms.base.common.tool.logViewer;
/*
 * Copyright 2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.io.EOFException;
import java.io.IOException;

import jp.co.daifuku.common.DateOperator;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RandomAcsFileHandler;


/**
 * 通信トレースログ一情報を取得し、<BR>
 * 通信トレースログ一覧画面にて指定された検索条件より
 * 表示対象となる情報を検索し、通信トレースログデータクラスへセットし、<BR>
 * 通信トレースログデータクラスより、通信トレースログ一覧クラスへ
 * 情報の引渡しを行う。
 * 
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 */

public class TraceLogFile
{
    // Class fields --------------------------------------------------
    /**
     * 最大表示行数
     */
    protected final static int MAX_DISPLAY = 10000;
    
    /**
     * トレースログファイル名
     */
    protected final static String TraceLogFileName = "RftCommunicationTrace.txt";
    
    /**
     * ファイル読み込み開始行
     */
    protected final static int READ_START = 0;
    
    /**
     * トレースファイルMAXサイズ
     */
    protected final static int TRACE_FILE_MAX_SIZE = 32000000;
    
    /**
     * STX
     */
    protected final static byte STX = 0x02;
    
    /**
     * ETX
     */
    protected final static byte ETX = 0x03;
    
    /**
     * [
     */
    protected final static byte START_BYTE = '[';
    
    /**
     * ]
     */
    protected final static byte END_BYTE = ']';
    
    /**
     * 開始日時フォーマットエラー
     */
    public static final int START_FORMAT_ERROR = -1;

    /**
     * 終了日時フォーマットエラー
     */
    public static final int END_FORMAT_ERROR = -2;

    /**
     * 開始日付の方が終了日付より遅い
     */
    public static final int DATE_INPUT_ERROR = 0;

    /**
     * 開始日付のみ入力あり
     */
    public static final int INPUT_START_DATE = 1;

    /**
     * 終了日付のみ入力あり
     */
    public static final int INPUT_END_DATE = 2;

    /**
     * 開始日付、終了日付両方入力あり
     */
    public static final int INPUT_START_END = 3;
    
    /**
     * 開始日付、終了日付両方入力なし
     */
    public static final int NULL_START_END = 4;
    
    /**
     * IDの長さ
     */
    public static final int IdLength = LogViewerParam.IdFigure;
    
    /**
     * デフォルトのIDの桁数
     */
    protected final static int DefaultIdFigure = 4;

    //-----------------
    // 検索条件変数定義
    //-----------------
    
    /**
     * RFT号機NO
     */
    protected String rftNo;
    
    /**
     * ID
     */
    protected String idNo1;
    protected String idNo2;
    
    /**
     * 検索開始日時
     */
    protected String startProcessDate;
    protected String startProcessTime;
    
    /**
     * 検索終了日時
     */
    protected String endProcessDate;
    protected String endProcessTime;
    
    /**
     * 表示条件
     */
    protected int displayCondition;
    
    /**
     * ファイル名
     */
    protected String traceFileName;
    
    /**
     * インスタンスを生成します
     */
    public TraceLogFile()
    {
        super();
    }

    /**
     * Rft号機NOを設定します
     * @param value RFT号機NO
     */
    public void setRftNo(String value)
    {
         rftNo = value;
    }
    
    /**
     * Rft号機NOを取得します
     * @return Rft号機NO
     */
    public String getRftNo()
    {
        return rftNo;
    }
    
    /**
     * IDNO1を設定します
     * @param value IDNO1
     */
    public void setIdNo1(String value)
    {
        idNo1 = value;
    }
    
    /**
     * IDNo1を取得します
     * @return IDNo1
     */
    public String getIdNo1()
    {
        return idNo1;
    }
    
    /**
     * IDNo2を設定します
     * @param value IDNO2
     */
    public void setIdNo2(String value)
    {
        idNo2 = value;
    }
    
    /**
     * IDNO2を取得します
     * @return idNo2
     */
    public String getIdNo2()
    {
        return idNo2;
    }

    /**
     * 検索開始日を設定します
     * @param value 検索開始日
     */
    public void setStartProcessDate(String value)
    {
        startProcessDate = value;
    }
    
    /**
     * 検索開始時間を設定します
     * @param value 検索開始時間
     */
    public void setStartProcessTime(String value)
    {
        startProcessTime = value;
    }
    
    /**
     * 検索終了日を設定します
     * @param value 検索終了日
     */
    public void setEndProcessDate(String value)
    {
        endProcessDate = value;
    }
    
    /**
     * 検索終了時間を設定します
     * @param value 検索終了時間
     */
    public void setEndProcessTime(String value)
    {
        endProcessTime = value;
    }
    
    /**
     * 検索開始日を取得します
     * @return 検索開始日
     */
    public String getStartProcessDate()
    {
        return startProcessDate;
    }
    
    /**
     * 検索開始時間を取得します
     * @return 検索開始時間
     */
    public String getStartProcessTime()
    {
        return startProcessDate;
    }
    
    /**
     * 検索終了時間を設定します
     * @return 検索終了時間
     */
    public String getEndProcessDate()
    {
        return endProcessDate;
    }
    
    /**
     * 検索終了時間を設定します
     * @return 検索終了時間
     */
    public String getEndProcessTime()
    {
        return endProcessTime;
    }
    
    /**
     * 表示区分を設定します
     * @param value 表示区分
     */
    public void setDisplayCondition(int value)
    {
        displayCondition = value;
    }
    
    /**
     * 表示区分を取得します
     * @return 表示区分
     */
    public int getDisplayCondition()
    {
        return displayCondition;
    }
    
    /**
     * 通信トレースログデータを取得し、指定された条件に合致する
     * データを通信トレースログデータクラスへセットし、通信トレースログ
     * 一覧クラスへセットします。
     * @return 通信トレースログ 一覧クラス
	 * @throws Exception 全ての例外を報告します。 
     */
    public TraceList getTraceData() throws Exception
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
            traceFileName = LogViewerParam.RftLogPath + "\\RFT" + rftNo + TraceLogFileName;
            
            // ファイルの読み込み
            traceFile = new RandomAcsFileHandler(traceFileName, TRACE_FILE_MAX_SIZE);
            
            // ファイル読み込み終了位置取得
            int readEnd = traceFile.getSizeUTF();
            
            // ReadEndが負の場合はエラー処理
            if (readEnd < 0)
            {
                String param[] = new String[1];
                param[0] = "RFT" + rftNo + TraceLogFileName;
                String msg = MessageResourceFile.getText("6006020");
                msg = MessageFormat.format(msg, param);
                throw new Exception(msg);
            }
            else if(readEnd == 0)
            {
            	throw new Exception("6003011");
            }
            
            // ファイルをオープン
            boolean openFlg = true;
    
            if (traceFile.readOpenUTF(openFlg) == false)
            {
            	throw new Exception("6003011");
            }
            
            // ファイル内格納Vector
            Vector vec = new Vector();
            vec = traceFile.readUTF(READ_START, readEnd);
            
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
            for (int i = 0; i < msg.length ; i++)
            {

                // 配列内データの確保
                // 書き込み時間
                java.util.Date writeDate = msg[i].getDate();
                
                // 送受信区分
                String sendReceiveFlag = msg[i].getClassInfo();
                
                // 電文
                byte[] communicateMessage = null;
                
                //作業Bufferの生成
                byte[] byteBuf = msg[i].getMessage().getBytes();
                
                // STX,ETXをそれぞれ"[","]"に変換
                if (byteBuf.length != 0)
                {

                    for (int j =0; j < byteBuf.length; j++)
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
                    
                    // 送受信チェック
                    // 全て表示
                    if (displayCondition == 1)
					{
						// 続行フラグON
						next = true;
					}

                    // 送信のみ表示
                    else if(displayCondition == 2)
                    {
                        // 送受信表示用変数に値をセット
                        if(sendReceiveFlag.equals("S"))
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
                    // 受信のみ表示
                    else if(displayCondition == 3)
                    {
                        // 送受信表示用変数に値をセット
                        if(sendReceiveFlag.equals("S"))
                        {
                            // 続行フラグOFF
                            next = false;
                        }
                        else
                        {
                            // 続行フラグON
                            next = true;
                        }
                    }

                    // 続行フラグがONの場合、次の処理に進む
                    if (next == true)
                    {
                        // 日付チェック
                        if (startDate != null)
                        {
                        	if (endDate == null)
                        	{
                        		// 開始日時のみ入力されている場合
                                if(startDate.compareTo(writeDate) <= 0)
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
                        		if(startDate.compareTo(writeDate) <= 0 && endDate.compareTo(writeDate) > 0)
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
                                if(endDate.compareTo(writeDate) > 0)
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
                    }
                        
                    // 電文からID番号を取得する
                	byte idbuf[] = new byte[IdLength];
                	for (int j = 0; j < IdLength; j ++)
                	{
                		idbuf[j] = communicateMessage[j + 5];
                	}
                	String id = new String (idbuf);

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
            	// 検索の結果、該当データがなかった場合は対象データなしを返す。
            	throw new Exception("6003011");
            }

            return cFile;
        }
        catch (EOFException e1)
        {
            String param[] = null;
            param = new String[1];
            param[0] = "RFT" + rftNo + TraceLogFileName;
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
     * String型(YYYYMMDD)とString型(HHMISS)からjava.util.Dateを作成します。 <BR>
     * トレース照会の日時検索に使用します。 <BR>
     * パラメータのdateFlagがTrueの場合、
     *   年月日、時分秒ともに入力時はそのまま<BR>
     *   年月日のみ入力時は入力された年月日＋00:00:00<BR>
     *   年月日、時分秒ともに未入力の場合はnullを返します。<BR>
     * パラメータのdateFlagがFalseの場合、
     *   年月日、時分秒ともに入力時は現在日時＋入力された時分秒+1秒<BR>
     *   年月日のみ入力時は入力された年月日＋1日＋00:00:00<BR>
     *   年月日、時分秒ともに未入力の場合はnullを返します。
     * @param  date 日付
     * @param  time 時間
     * @param  dateFlag 日付フラグ True：開始日時 False：終了日時
     * @return  java.util.Date
	 * @throws Exception 全ての例外を報告します。 
     */
    public static Date getDate(String date, String time, boolean dateFlag) throws Exception
    {
        java.util.Date wDate = null;
        
        // 年月日が空白でない
        if (date.length() != 0)
		{
			// 時分秒が空白でない
			if (time.length() != 0)
			{
				// 全入力時
				if (date.length() == 8 && time.length() == 6)
				{
					GregorianCalendar wCalendar =
						new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
												Integer.parseInt(date.substring(4, 6)) - 1,
												Integer.parseInt(date.substring(6, 8)),
												Integer.parseInt(time.substring(0, 2)),
												Integer.parseInt(time.substring(2, 4)),
												Integer.parseInt(time.substring(4, 6)));
					if (!dateFlag)
					{
						wCalendar.add(Calendar.SECOND, 1);
					}
					wDate = wCalendar.getTime();
				}
			}
			//時分秒が空白
			else
			{
				if (date.length() == 8)
				{
					GregorianCalendar wCalendar =
						new GregorianCalendar(Integer.parseInt(date.substring(0, 4)),
												Integer.parseInt(date.substring(4, 6)) - 1,
												Integer.parseInt(date.substring(6, 8)),
												0, 0, 0);
					if (!dateFlag)
					{
						wCalendar.add(Calendar.DATE, 1);
					}
					wDate = wCalendar.getTime();
				}
			}
		}
		// 年月日が空白
		else
		{
			// 時分秒が空白でない
			if (time.length() != 0)
			{
				throw new Exception("6023510");
			}
		}
		return wDate;
    }
}