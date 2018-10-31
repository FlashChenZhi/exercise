// $Id: As21Id32.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「出庫指示応答(RetrievalCommandResponse) ID=32」電文を処理します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * Processes the communication message "Reponse to the Retrieval Comand", ID=32 acording to AS21 communication protocol.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class As21Id32
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID32の電文長(STX,SEQ-No,BCC,ETXを除く)。
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte * 2</td></tr>
     * <tr><td>応答区分:</td><td>2 byte * 2</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of communication message ID32 (excluding STX, SEQ-No, BCC and ETX).
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>sent time to AGC:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte * 2</td></tr>
     * <tr><td>response classification:</td><td>2 byte * 2</td></tr>
     * </table>
     * </p>
     </en>*/
    public static final int LEN_ID32 = 36;

    /**<jp>
     * MC Keyのオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset of MC Key.
     </en>*/
    private static final int OFF_ID32_MCKEY = 0;

    /**<jp>
     * MC Keyの長さ(Byte)
     </jp>*/
    /**<en>
     * Length of MC Key(in byte)
     </en>*/
    private static final int LEN_ID32_MCKEY = 8;

    /**<jp>
     * 応答区分のオフセットの定義
     </jp>*/
    /**<en>
     * Defines the offset in response classification
     </en>*/
    private static final int OFF_ID32_CLASS = OFF_ID32_MCKEY + LEN_ID32_MCKEY;

    /**<jp>
     * 応答区分の長さ(Byte)
     </jp>*/
    /**<en>
     * Length of response classification (in byte)
     </en>*/
    private static final int LEN_ID32_CLASS = 2;

    /**<jp>
     * 搬送データ２件目の開始位置
     </jp>*/
    /**<en>
     * Position to start 2nd carry data
     </en>*/
    private static final int OFF_ID32_2 = OFF_ID32_CLASS + LEN_ID32_CLASS;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文バッファ
     </jp>*/
    /**<en>
     * Communication message buffer
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID32];

    /**<jp>
     * 電文の件数
     </jp>*/
    /**<en>
     * Number of communication messages
     </en>*/
    private int _countOfData;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public As21Id32()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文をコンストラクタに渡します。
     * @param as21Id32 <code>as21Id32</code>  出庫指示応答(RetrievalCommandResponse) ID=32 電文
     </jp>*/
    /**<en>
     * Passes communication message received from AGC to the constructor.
     * @param as21Id32 :<code>as21Id32</code>  the communication message "Reponse to the Retrieval Command", ID=32 
     </en>*/
    public As21Id32(byte[] as21Id32)
    {
        super();
        setReceiveMessage(as21Id32);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 出庫指示応答(RetrievalCommandResponse)電文からMC Keyを取得します。
     * 出庫指示応答電文により応答Dataが最大２件返されます。
     * @return MC Key
     * @throws InvalidProtocolException  プロトコル上、不都合な情報があった場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires MC Key from the communication message "Reponse to the Retrieval Comand".
     * Response data, up to 2 data, will return according to the message "Reponse to the Retrieval Comand".
     * @return MC Key
     * @throws InvalidProtocolException  : Notifies if communication message includes improper contents from the protocol aspect.
     </en>*/
    public String[] getMcKey()
            throws InvalidProtocolException
    {
        return (getResponseInfo(OFF_ID32_MCKEY, LEN_ID32_MCKEY));
    }

    /**<jp>
     * 出庫指示応答(RetrievalCommandResponse)電文から応答Dataを取得します。
     * @return
     * <table>
     * <tr><td>0:</td><td>正常</td></tr>
     * <tr><td>3:</td><td>多重設定</td></tr>
     * <tr><td>6:</td><td>OFF-Line中</td></tr>
     * <tr><td>11:</td><td>BufferFull</td></tr>
     * <tr><td>99:</td><td>DataError</td></tr>
     * </table>
     * @throws InvalidProtocolException 応答区分が数字でない場合に報告されます。
     </jp>*/
    /**<en>
     * Acquires response data from the message RetrievalCommandResponse.
     * @return
     * <table>
     * <tr><td>0:</td><td>normal</td></tr>
     * <tr><td>3:</td><td>nultiple set</td></tr>
     * <tr><td>6:</td><td>off-line</td></tr>
     * <tr><td>11:</td><td>Buffer Full</td></tr>
     * <tr><td>99:</td><td>Data Error</td></tr>
     * </table>
     * @throws InvalidProtocolException : Reports if numeric value was not provided for response classification.
     </en>*/
    public int[] getResponseData()
            throws InvalidProtocolException
    {
        String[] responceClassification = getResponseInfo(OFF_ID32_CLASS, LEN_ID32_CLASS);
        int[] rclass = new int[responceClassification.length];
        try
        {
            for (int i = 0; i < responceClassification.length; i++)
            {
                rclass[i] = Integer.parseInt(responceClassification[i]);
            }
        }
        catch (Exception e)
        {
            throw (new InvalidProtocolException("Invalid Response:" + responceClassification));
        }
        return (rclass);
    }

    /**<jp>
     * 出庫指示応答電文の内容を取得します。
     * @return  出庫指示応答TEXTの内容
     </jp>*/
    /**<en>
     * Acquires the content of the message RetrievalCommandResponse("Reponse to the Retrieval Command").
     * @return  text content of the message RetrievalCommandResponse
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * 応答件数にあわせて、1件または2件の情報を内部バッファから取得します。
     * @param offset  1件目の情報へのオフセット。2件目のオフセットは自動的に計算されます。
     * @param len  取得する情報の長さ(Byte)
     * @return 通信電文の内容部分
     * @throws InvalidProtocolException  プロトコル上、不都合な情報があった場合に通知されます。
     </jp>*/
    /**<en>
     * Acquires data for 1 or 2 from internal buffer in accordance with the number of response.
     * @param offset  oFfsetting the 1st data. Offsetting of 2nd data will automatically be calculated.
     * @param len  Length of acquiring data (in byte)
     * @return contents of the communication message
     * @throws InvalidProtocolException  : Notifies if communication message includes improper contents from the protocol aspect.
     </en>*/
    protected String[] getResponseInfo(int offset, int len)
            throws InvalidProtocolException
    {
        String[] rst = new String[_countOfData];
        try
        {
            for (int i = 0; i < _countOfData; i++)
            {
                int toff = offset + (OFF_ID32_2 * i);
                rst[i] = getContent().substring(toff, toff + len);
            }
        }
        catch (Exception e)
        {
            throw (new InvalidProtocolException("Encode error"));
        }
        return (rst);
    }

    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg AGCから受け取った電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer
     * @param rmsg message received from AGC 
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID32; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;

        //<jp> 応答Dataをカウント</jp>
        //<en> counting response data</en>
        int offset = OFF_ID32_MCKEY;
        String mckey = getContent().substring(offset, offset + LEN_ID32_MCKEY);
        if (mckey.equals(NULL_MC_KEY))
        {
            _countOfData = 0;
        }
        else
        {
            _countOfData = 1;

            offset = OFF_ID32_2 + OFF_ID32_MCKEY;
            mckey = getContent().substring(offset, offset + LEN_ID32_MCKEY);
            if (!mckey.equals(NULL_MC_KEY))
            {
                _countOfData++;
            }
        }
    }
}
//end of class

