// $Id: As21Id40.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「伝送TEST要求(TransmissionTestRequest) ID=40」電文を処理します。
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
 * Processes communication message "TransmissionTestRequest" ID=40, according to AS21 communication protocol.
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
public class As21Id40
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID40のバイト総数(STX,SEQ-NO,BCC,ETXを除く)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時間:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時間:</td><td>6 byte</td></tr>
     * <tr><td>Teat data:</td><td>488 byte</td></tr>
     </jp>*/
    /**<en>
     * Total number of bytes in ID40 (excluding STX, SEQ-NO, BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>Test data:</td><td>488 byte</td></tr>
     </en>*/

    /**<jp>
     * ID40のバイト総数(STX,SEQ-NO,BCC,ETXを除く)
     </jp>*/
    /**<en>
     * Total number of bytes in ID40 (excluding STX, SEQ-NO, BCC and ETX)
     </en>*/
    public static final int LEN_ID40 = 504;

    /**<jp>
     * Test dataのオフセット定義
     </jp>*/
    /**<en>
     * Defines offsest of Test data.
     </en>*/
    private static final int OFF_ID40_TEST_DATA = 0;

    /**<jp>
     * Test dataの長さ(byte)
     </jp>*/
    /**<en>
     * Length of Test data (in byte)
     </en>*/
    private static final int LEN_ID40_TEST_DATA = 488;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variable that preserves the communication message
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID40];

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
     * @param as21Id40 <code>as21Id40</code>  伝送TEST要求電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC, then initialize this class.
     * @param as21Id40 :<code>as21Id40</code>  communication message "TransmissionTestRequest"
     </en>*/
    public As21Id40(byte[] as21Id40)
    {
        super();
        setReceiveMessage(as21Id40);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 伝送TEST要求電文からTest Dataを取得します。
     * @return    伝送TEST要求電文
     </jp>*/
    /**<en>
     * Acquires the Test Data from the communication message "TransmissionTestRequest".
     * @return    the communication message "TransmissionTestRequest"
     </en>*/
    public String getTestData()
    {
        String testData = getContent().substring(OFF_ID40_TEST_DATA, OFF_ID40_TEST_DATA + LEN_ID40_TEST_DATA);
        return (testData);
    }

    /**<jp>
     * 伝送Test要求電文の内容を取得します
     * @return 伝送Test要求電文の内容
     </jp>*/
    /**<en>
     * Acquires the contents of the communication message "TransmissionTestRequest"
     * @return contents of the communication message "TransmissionTestRequest"
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファーにセットします。
     * @param rmsg 伝送Test要求電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg the communication message "TransmissionTestRequest"
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID40; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

