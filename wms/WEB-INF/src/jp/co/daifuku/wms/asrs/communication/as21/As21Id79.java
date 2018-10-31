// $Id: As21Id79.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「SystemRecovery終了応答(SystemRecoveryCompletionResponse) ID=79」電文を処理します。
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
 * Processes the communication message "SystemRecoveryCompletionResponse" ID=79 according to 
 * AS21 communication protocol.
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
public class As21Id79
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID79の電文長(STX,SEQ-No,BCC,ETXを除く)。
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>応答区分:</td><td>2 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of communication message ID79 (excluding STX, SEQ-No, BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>response classification:</td><td>2 byte</td></tr>
     * </table>
     * </p>
     </en>*/

    /**<jp>
     * ID79の電文長(STX,SEQ-No,BCC,ETXを除く)。
     </jp>*/
    /**<en>
     * Length of the ccommunication message ID79 (excluding STX, SEQ-No, BCC and ETX)
     </en>*/
    public static final int LEN_ID79 = 18;

    /**<jp>
     * ID79の応答区分のオフセット定義
     </jp>*/
    /**<en>
     * Defines the offset for response classification of ID79
     </en>*/
    private static final int OFF_ID79_CLASS = 0;

    /**<jp>
     * ID79の応答区分の長さ(byte)
     </jp>*/
    /**<en>
     * Length of response classification ID79 (byte)
     </en>*/
    private static final int LEN_ID79_CLASS = 2;

    /**<jp>
     * 応答区分で正常受付を表す。
     </jp>*/
    /**<en>
     * "Received in Normal status" in response classification
     </en>*/
    public static final String NORMAL_RECEIVE = "00";

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variable for the preservation of communication messages
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID79];

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョン日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Data: 2001/05/11 02:33:53 $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public As21Id79()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文セットし、このクラスの初期化を行います。
     * @param as21Id79 <code>as21Id79</code>  SystemRecovery終了応答電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC; then implements the initialization
     * of this class.
     * @param as21Id79 :<code>as21Id79</code>  the communication message "SystemRecoveryCompletionResponse"
     </en>*/
    public As21Id79(byte[] as21Id79)
    {
        super();
        setReceiveMessage(as21Id79);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * SystemRecovery終了応答電文から応答区分を取得します。
     * @return    SystemRecovery終了応答電文の応答区分（00:正常受付）
     * <table>
     * <tr><td>0:</td><td>正常受付</td></tr>
     * </table>
     </jp>*/
    /**<en>
     * Acquires the response classification from the communication message "SystemRecoveryCompletionResponse"
     * @return    the response classification of the message "SystemRecoveryCompletionResponse" (00: recieved normal status)
     * <table>
     * <tr><td>0:</td><td>received in normal status</td></tr>
     * </table>
     </en>*/
    public String getResponseClassification()
    {
        String responseClassification = "99";
        responseClassification = getContent().substring(OFF_ID79_CLASS, OFF_ID79_CLASS + LEN_ID79_CLASS);
        return (responseClassification);
    }

    /**<jp>
     * SystemRecovery終了応答電文の内容を取得します。
     * @return System Recovery終了応答TEXTの内容
     </jp>*/
    /**<en>
     * Acquires the content of the communication message "SystemRecoveryCompletionResponse"
     * @return the content of message text "SystemRecoveryCompletionResponse"
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg SystemRecovery終了応答電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg the communication message "SystemRecoveryCompletionResponse"
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID79; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

