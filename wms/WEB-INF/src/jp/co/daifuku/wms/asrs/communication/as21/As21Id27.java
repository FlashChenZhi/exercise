// $Id: As21Id27.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「搬送先変更要求(ReceivingStationChangeRequest) ID=27」電文を処理します。
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
 * Processes communication messages "Request for receiving station change, ID=27" according to AS21 communication protocol.
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
public class As21Id27
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID27の電文長(STX,SEQ-No,BCC,ETXを除く)。
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>ロケーションNo.:</td><td>12 byte</td></tr>
     * <tr><td>ステーションNo.:</td><td>4 byte</td></tr>
     * <tr><td>AGC Data:</td><td>6 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of communicarion message ID27 (excluding STX, SEQ-No, BCC and ETX).
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>location No.:</td><td>12 byte</td></tr>
     * <tr><td>station No.:</td><td>4 byte</td></tr>
     * <tr><td>AGC Data:</td><td>6 byte</td></tr>
     * </table>
     * </p>
     </en>*/
    public static final int LEN_ID27 = 46;

    /**<jp>
     * MC Keyのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of MC Key.
     </en>*/
    private static final int OFF_ID27_MCKEY = 0;

    /**<jp>
     * MC Keyの長さ(Byte)
     </jp>*/
    /**<en>
     * Length of MC Key (in byte)
     </en>*/
    private static final int LEN_ID27_MCKEY = 8;

    /**<jp>
     * ロケーションNoのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of location No.
     </en>*/
    private static final int OFF_ID27_LOCATION = OFF_ID27_MCKEY + LEN_ID27_MCKEY;

    /**<jp>
     * ロケーションNoの長さ(Byte)
     </jp>*/
    /**<en>
     * Length of location No. (in byte)
     </en>*/
    private static final int LEN_ID27_LOCATION = 12;

    /**<jp>
     * ステーションNoのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of station No.
     </en>*/
    private static final int OFF_ID27_STATION = OFF_ID27_LOCATION + LEN_ID27_LOCATION;

    /**<jp>
     * ステーションNoの長さ(Byte)
     </jp>*/
    /**<en>
     * Length of station No. (in byte)
     </en>*/
    private static final int LEN_ID27_STATION = 4;

    /**<jp>
     * AGC DATAのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of AGC DATA
     </en>*/
    private static final int OFF_ID27_AGC_DATA = OFF_ID27_STATION + LEN_ID27_STATION;

    /**<jp>
     * AGC DATAの長さ(Byte)
     </jp>*/
    /**<en>
     * Length of AGC DATA, in byte
     </en>*/
    private static final int LEN_ID27_AGC_DATA = 6;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文バッファ
     </jp>*/
    /**<en>
     * communication message
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID27];

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
    public As21Id27()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文をコンストラクタに渡します。
     * @param as21Id27 <code>as21Id27</code>  搬送先変更要求(ReceivingStationChangeRequest) ID=27 電文
     </jp>*/
    /**<en>
     * Passes the message received from AGC to the constructor.
     * @param as21Id27 :<code>as21Id27</code> communiation message "Request for receiving station change" ID=27 
     </en>*/
    public As21Id27(byte[] as21Id27)
    {
        super();
        setReceiveMessage(as21Id27);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 搬送先変更要求(ReceivingStationChangeRequest)電文からMC Keyを取得します。
     * @return    MC Key
     </jp>*/
    /**<en>
     * Acquires MC Key from communication message Request for receiving station change.
     * @return    MC Key
     </en>*/
    public String getMcKey()
    {
        String mcKey = getContent().substring(OFF_ID27_MCKEY, OFF_ID27_MCKEY + LEN_ID27_MCKEY);
        return (mcKey);
    }

    /**<jp>
     * 搬送先変更要求(ReceivingStationChangeRequest)電文からLocation No.を取得します。
     * @return    Location No.
     </jp>*/
    /**<en>
     * Acquires location No. from communication message Request for receiving station change.
     * @return    Location No.
     </en>*/
    public String getLocationNo()
    {
        String locationNo = getContent().substring(OFF_ID27_LOCATION, OFF_ID27_LOCATION + LEN_ID27_LOCATION);
        return (locationNo);
    }

    /**<jp>
     * 搬送先変更要求(ReceivingStationChangeRequest)電文からStation No.を取得します。
     * @return    Station No.
     </jp>*/
    /**<en>
     * Acquires station No. from communication message Request for receiving station change.
     * @return    Station No.
     </en>*/
    public String getStationNumber()
    {
        String stationNo = getContent().substring(OFF_ID27_STATION, OFF_ID27_STATION + LEN_ID27_STATION);
        return (stationNo);
    }

    /**<jp>
     * 搬送先変更要求(ReceivingStationChangeRequest)電文からAGC Dataを取得します。
     * @return    AGC Data
     </jp>*/
    /**<en>
     * Acquires AGC Data from the message, Request for receiving station change.
     * @return    AGC Data
     </en>*/
    public String getAgcData()
    {
        String agcData = getContent().substring(OFF_ID27_AGC_DATA, OFF_ID27_AGC_DATA + LEN_ID27_AGC_DATA);
        return (agcData);
    }

    /**<jp>
     * 搬送先変更要求電文の文字列表現を返します。
     * @return   搬送先変更要求電文
     </jp>*/
    /**<en>
     * Returns the string representaion of conmmunication message Requesting the receiving station change.
     * @return   conmmunication message Requesting the receiving station change.
     </en>*/
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
        for (int i = 0; i < LEN_ID27; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

