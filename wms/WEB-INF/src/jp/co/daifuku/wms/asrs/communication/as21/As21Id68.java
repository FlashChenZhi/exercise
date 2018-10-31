// $Id: As21Id68.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;


/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「作業表示Trigger(OperationDisplayTrigger) ID=68」電文を処理します。
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
 * Processes the communication message "OperationDisplayTrigger" ID=68 according to AS21 communication protocol.
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
public class As21Id68
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID68の電文長(STX,SEQ-No,BCC,ETXを除く)。
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><tdz>Station No.:</td><td>4 byte</td></tr>
     * <tr><tdz>制御情報:</td><td>30 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of comunication message ID68 (excluding STX, SEQ-No, BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><tdz>Station No.:</td><td>4 byte</td></tr>
     * <tr><tdz>control data:</td><td>30 byte</td></tr>
     * </table>
     * </p>
     </en>*/

    /**<jp>
     * ID68の電文長(STX,SEQ-No,BCC,ETXを除く)。
     </jp>*/
    /**<en>
     * Length of communication message ID68 (excluding STX, SEQ-No, BCC and ETX)
     </en>*/
    public static final int LEN_ID68 = 58;

    /**<jp>
     * ID68のMC KEYのオフセット定義
     </jp>*/
    /**<en>
     * Defines the offset of MC KEY of ID68
     </en>*/
    private static final int OFF_ID68_MCKEY = 0;

    /**<jp>
     * ID68のMC KEYの長さ(byte)
     </jp>*/
    /**<en>
     * Length of MC KEY of ID68 (byte)
     </en>*/
    private static final int LEN_ID68_MCKEY = 8;

    /**<jp>
     * ID68のStation No.のオフセット定義
     </jp>*/
    /**<en>
     * Defines the offset of station No. of ID68
     </en>*/
    private static final int OFF_ID68_STATION = OFF_ID68_MCKEY + LEN_ID68_MCKEY;

    /**<jp>
     * ID68のStation No.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of station No. of ID68 (byte)
     </en>*/
    private static final int LEN_ID68_STATION = 4;

    /**<jp>
     * ID68の制御情報のオフセット定義
     </jp>*/
    /**<en>
     * Defines the offset of control data of ID68
     </en>*/
    private static final int OFF_ID68_CONTROL_INFO = OFF_ID68_STATION + LEN_ID68_STATION;

    /**<jp>
     * ID68の制御情報の長さ(byte)
     </jp>*/
    /**<en>
     * Length of control data of (byte)
     </en>*/
    private static final int LEN_ID68_CONTROL_INFO = 30;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variables for the reservation of communication messages
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID68];

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
     * @param as21Id68 <code>as21Id68</code>  作業表示Trigger電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC; then initializes this class.
     * @param as21Id68 :<code>as21Id68</code>  communication message "OperationDisplayTrigger"
     </en>*/
    public As21Id68(byte[] as21Id68)
    {
        super();
        setReceiveMessage(as21Id68);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 作業表示Trigger電文からMC Keyを取得します。
     * @return    MC Key
     </jp>*/
    /**<en>
     * Acquires the MC Key from the communication message "OperationDisplayTrigger".
     * @return    MC Key
     </en>*/
    public String getMcKey()
    {
        String mcKey = getContent().substring(OFF_ID68_MCKEY, OFF_ID68_MCKEY + LEN_ID68_MCKEY);
        return (mcKey);
    }

    /**<jp>
     * 作業表示Trigger電文から搬送先Station No.を取得します。
     * @return    搬送先Station No.
     </jp>*/
    /**<en>
     * Acquires the receiving station No. from the communication message "OperationDisplayTrigger".
     * @return    receiving station No.
     </en>*/
    public String getStationNo()
    {
        String stationNo = getContent().substring(OFF_ID68_STATION, OFF_ID68_STATION + LEN_ID68_STATION);
        return (stationNo);
    }

    /**<jp>
     * 作業表示Trigger電文から制御情報を取得します。
     * @return    制御情報
     </jp>*/
    /**<en>
     * Acquires conrtol data from the communication message "OperationDisplayTrigger".
     * @return    conrtol data
     </en>*/
    public String getControlInformation()
    {
        String controlInformation =
                getContent().substring(OFF_ID68_CONTROL_INFO, OFF_ID68_CONTROL_INFO + LEN_ID68_CONTROL_INFO);
        return (controlInformation);

    }

    /**<jp>
     * 作業表示Trigger電文の内容を取得します。
     * @return 作業表示Trigger電文の内容
     </jp>*/
    /**<en>
     * Acquires contents of the communication message "OperationDisplayTrigger".
     * @return contents of communication message "OperationDisplayTrigger"
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg 作業表示Trigger電文
     </jp>*/
    /**<en>
     * ASets communication message received from AGC to the internal buffer.
     * @param rmsg the communication message "OperationDisplayTrigger"
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID68; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }
}
//end of class

