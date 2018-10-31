// $Id: As21Id25.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「搬送指示応答(TransportCommandResponse) ID=25」電文を処理します。
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
 * Processes communication message "Transport  Command Response, ID=25" according to AS21 communication protocol.
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
public class As21Id25
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID25の電文長(STX,SEQ-No,BCC,ETXを除く)。
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>応答区分:</td><td>2 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of communication message ID25 (excluding STX, SEQ-No,BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>Response classification:</td><td>2 byte</td></tr>
     * </table>
     * </p>
     </en>*/
    public static final int LEN_ID25 = 26;

    /**<jp>
     * MC Keyのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of MC Key
     </en>*/
    private static final int OFF_ID25_MCKEY = 0;

    /**<jp>
     * MC Keyの長さ(Byte)
     </jp>*/
    /**<en>
     * Length of MC Key (in byte)
     </en>*/
    private static final int LEN_ID25_MCKEY = 8;

    /**<jp>
     * 応答区分のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of response classification
     </en>*/
    private static final int OFF_ID25_CLASS = OFF_ID25_MCKEY + LEN_ID25_MCKEY;

    /**<jp>
     * 応答区分の長さ
     </jp>*/
    /**<en>
     * Length of response classification
     </en>*/
    private static final int LEN_ID25_CLASS = 2;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文バッファ
     </jp>*/
    /**<en>
     * Communication message buffer
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID25];

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
    public As21Id25()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文をコンストラクタに渡します。
     * 電文には、STX,SEQ-No,BCC,ETXなどは含まれないようにしてください。
     * @param as21Id25 <code>as21Id25</code>  搬送指示応答(TransportCommandResponse) ID=25 電文
     </jp>*/
    /**<en>
     * Passes communication message received fro AGC to the constructor.
     * Make sure that the message does not include STX, SEQ-No, BCC and ETX.
     * @param as21Id25 :<code>as21Id25</code>  communication message "Transport Command Response" ID=25
     </en>*/
    public As21Id25(byte[] as21Id25)
    {
        super();
        setReceiveMessage(as21Id25);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 搬送指示応答(TransportCommandResponse)電文からMC Keyを取得します。
     * @return    MC Key
     </jp>*/
    /**<en>
     * Acquires MC Key from the communication message Transport Command Response.
     * @return    MC Key
     </en>*/
    public String getMcKey()
    {
        String mcKey = getContent().substring(OFF_ID25_MCKEY, OFF_ID25_MCKEY + LEN_ID25_MCKEY);
        return (mcKey);
    }

    /**<jp>
     * 搬送指示応答(TransportCommandResponse)電文から応答区分を取得します。
     * @return
     * <table>
     * <tr><td>0:</td><td>正常</td></tr>
     * <tr><td>1:</td><td>在荷エラー</td></tr>
     * <tr><td>3:</td><td>多重設定</td></tr>
     * <tr><td>4:</td><td>故障中</td></tr>
     * <tr><td>5:</td><td>切り離し中</td></tr>
     * <tr><td>6:</td><td>OFF-Line中</td></tr>
     * <tr><td>7:</td><td>条件エラー</td></tr>
     * <tr><td>11:</td><td>BufferFull</td></tr>
     * <tr><td>99:</td><td>DataError</td></tr>
     * </table>
     * @throws InvalidProtocolException 応答区分が数字でない場合に報告されます。
     </jp>*/
    /**<en>
     * Acquires response classification from the message Transport Command Response.
     * @return
     * <table>
     * <tr><td>0:</td><td>normal</td></tr>
     * <tr><td>1:</td><td>load presence error</td></tr>
     * <tr><td>3:</td><td>multiple set</td></tr>
     * <tr><td>4:</td><td>out of order</td></tr>
     * <tr><td>5:</td><td>cutting loose</td></tr>
     * <tr><td>6:</td><td>turning off-Line</td></tr>
     * <tr><td>7:</td><td>condition error</td></tr>
     * <tr><td>11:</td><td>BufferFull</td></tr>
     * <tr><td>99:</td><td>DataError</td></tr>
     * </table>
     * @throws InvalidProtocolException : Reports if numeric value was not provided for response classification.
     </en>*/
    public int getResponseClassification()
            throws InvalidProtocolException
    {
        int rclass = 99;
        String responseClassification = getContent().substring(OFF_ID25_CLASS, OFF_ID25_CLASS + LEN_ID25_CLASS);
        try
        {
            rclass = Integer.parseInt(responseClassification);
        }
        catch (Exception e)
        {
            throw (new InvalidProtocolException("Invalid Response:" + responseClassification));
        }
        return (rclass);
    }

    /**
     * ローカルバッファを返します。
     * @return ローカルバッファ
     */
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg AGCから受け取った電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg communication message received from AGC
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID25; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

    // debug methods -----------------------------------------------

}
//end of class

