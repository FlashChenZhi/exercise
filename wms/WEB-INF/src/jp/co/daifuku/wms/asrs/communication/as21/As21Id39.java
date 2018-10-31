// $Id: As21Id39.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「伝送TEST応答(TransmissionTestResponse) ID=39」電文を処理します。
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
 * Processes the communication message "TransmissionTestResponse" ID=39, according to AS21 communication protocol.
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
public class As21Id39
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID39の電文長(STX,SEQ-NO,BCC,ETXを除く)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時間:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時間:</td><td>6 byte</td></tr>
     * <tr><td>Test data:</td><td>488 byte</td></tr>
     * </table>
     * <p>
     * Comment for field
     </jp>*/
    /**<en>
     * Length of communication message of ID39 (excluding STX, SEQ-NO, BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>Test data:</td><td>488 byte</td></tr>
     * </table>
     * <p>
     * Comment for field
     </en>*/

    /**<jp>
     * ID39のバイト総数(STX,SEQ-NO,BCC,ETXを除く)
     </jp>*/
    /**<en>
     * Total number of bytes in ID39 (excluding STX, SEQ-NO, BCC and ETX)
     </en>*/
    public static final int LEN_ID39 = 504;

    /**<jp>
     * Test dataoのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of Test data
     </en>*/
    private static final int OFF_ID39_TEST_DATA = 0;

    /**<jp>
     * Test dataoの長さ(byte)
     </jp>*/
    /**<en>
     * Length of Test data (in byte)
     </en>*/
    private static final int LEN_ID39_TEST_DATA = 488;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variable which preserves the communication messages
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID39];

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します
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
     * AGCから受信した電文セットし、このクラスの初期化を行います。
     * @param as21Id39 <code>as21Id39</code>  伝送TEST応答電文
     </jp>*/
    /**<en>
     * Sets the communication message received from  AGC, then initialize this class.
     * @param as21Id39 :<code>as21Id39</code>  communication message "TransmissionTestResponse"
     </en>*/
    public As21Id39(byte[] as21Id39)
    {
        super();
        setReceiveMessage(as21Id39);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 伝送TEST応答電文からTest Dataを取得します。
     * @return    Test Data
     </jp>*/
    /**<en>
     * Acquires test data from communication message "TransmissionTestResponse".
     * @return    Test Data
     </en>*/
    public String getTestData()
    {
        String testData = getContent().substring(OFF_ID39_TEST_DATA, OFF_ID39_TEST_DATA + LEN_ID39_TEST_DATA);
        return (testData);
    }

    /**<jp>
     * 伝送Test応答電文の内容を取得します
     * @return 伝送Test応答電文の内容
     </jp>*/
    /**<en>
     * Acquires the contents of communication message "TransmissionTestResponse".
     * @return contents of communication message "TransmissionTestResponse"
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファーにセットします。
     * @param rmsg 伝送Test応答電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg communication message "TransmissionTestResponse"
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID39; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

