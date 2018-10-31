// $Id: As21Id66.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「出庫Trigger(RetrievalTrigger) ID=66」電文を処理します。
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
 * Processes the communication message "RetrievalTrigger" ID=66 according to AS21 communication protocol.
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
public class As21Id66
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID66の電文長(STX,SEQ-No,BCC,ETXを除く)。
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>Station NO.:</td><td>4 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of communication message ID66 (STX, SEQ-No, BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>Station NO.:</td><td>4 byte</td></tr>
     * </table>
     * </p>
     </en>*/

    /**<jp>
     * ID66の電文長(STX,SEQ-No,BCC,ETXを除く)。
     </jp>*/
    /**<en>
     * Length of communication message ID66 (excluding STX, SEQ-No, BCC and ETX)
     </en>*/
    public static final int LEN_ID66 = 20;

    /**<jp>
     * ID66のStation No.のオフセット定義
     </jp>*/
    /**<en>
     * Defines the offset of Station No. of ID66.
     </en>*/
    private static final int OFF_ID66_STATION = 0;

    /**<jp>
     * ID66のStation No.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of Station No. of ID66 (byte)
     </en>*/
    private static final int LEN_ID66_STATION = 4;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variables for the preservation of communication messages
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID66];

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
     * AGCから受信した電文セットし、このクラスの初期化を行います。
     * @param as21Id66 <code>as21Id66</code>  出庫Trigger電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC; then initializes this class.
     * @param as21Id66 :<code>as21Id66</code>  communication message "Retrieval Trigger"
     </en>*/
    public As21Id66(byte[] as21Id66)
    {
        super();
        setReceiveMessage(as21Id66);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 出庫Trigger電文から出庫を要求するStation No.を取得します。
     * @return    出庫Station No.
     </jp>*/
    /**<en>
     * Acquires station No., which requests the retrieval, from the communication message "Retrieval Trigger".
     * @return    Retrieval station No.
     </en>*/
    public String getStationNo()
    {
        String stationNo = getContent().substring(OFF_ID66_STATION, OFF_ID66_STATION + LEN_ID66_STATION);
        return (stationNo);
    }

    /**<jp>
     * 出庫Trigger電文の内容を取得します。
     * @return 出庫指示Trigger電文の内容
     </jp>*/
    /**<en>
     * Acquires the contents of communication message "Retrieval Trigger".
     * @return contents of communication message "Retrieval Trigger"
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg 出庫Trigger電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg communication message "Retrieval Trigger"
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID66; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

