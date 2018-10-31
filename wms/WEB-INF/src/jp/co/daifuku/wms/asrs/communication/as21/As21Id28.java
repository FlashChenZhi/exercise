// $Id: As21Id28.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「搬送先変更指示応答(ReceivingStationChangeCommandResponse) ID=28」電文を処理します。
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
 * Processes communication message "Response to the command to change the receiving station, ID=28"
 * according to AS21 communication protocol.
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
public class As21Id28
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID28の電文長(STX,SEQ-No,BCC,ETXを除く)。
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>指示区分:</td><td>1 byte</td></tr>
     * <tr><td>ロケーションNo.:</td><td>12 byte</td></tr>
     * <tr><td>ステーションNo.:</td><td>4 byte</td></tr>
     * <tr><td>応答区分:</td><td>2 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of the communication message ID28 (excluding STX, SEQ-No, BCC and ETX).
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>instruction classification:</td><td>1 byte</td></tr>
     * <tr><td>location No.:</td><td>12 byte</td></tr>
     * <tr><td>station No.:</td><td>4 byte</td></tr>
     * <tr><td>response classification:</td><td>2 byte</td></tr>
     * </table>
     * </p>
     </en>*/
    public static final int LEN_ID28 = 43;

    /**<jp>
     * MC Keyのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of MC Key.
     </en>*/
    private static final int OFF_ID28_MCKEY = 0;

    /**<jp>
     * MC Keyの長さ(Byte)
     </jp>*/
    /**<en>
     * Length of MC Key (in byte)
     </en>*/
    private static final int LEN_ID28_MCKEY = 8;

    /**<jp>
     * 指示区分のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of instruction classification.
     </en>*/
    private static final int OFF_ID28_INS_CLASS = OFF_ID28_MCKEY + LEN_ID28_MCKEY;

    /**<jp>
     * 指示区分の長さ(Byte)
     </jp>*/
    /**<en>
     * Length of instruction classification (in buyte)
     </en>*/
    private static final int LEN_ID28_INS_CLASS = 1;

    /**<jp>
     * ロケーションNoのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of location No.
     </en>*/
    private static final int OFF_ID28_LOCATION = OFF_ID28_INS_CLASS + LEN_ID28_INS_CLASS;

    /**<jp>
     * ロケーションNoの長さ(Byte)
     </jp>*/
    /**<en>
     * Length of location (in byte)
     </en>*/
    private static final int LEN_ID28_LOCATION = 12;

    /**<jp>
     * ステーションNoのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of station No.
     </en>*/
    private static final int OFF_ID28_STATION = OFF_ID28_LOCATION + LEN_ID28_LOCATION;

    /**<jp>
     * ステーションNoの長さ(Byte)
     </jp>*/
    /**<en>
     * Length of station No. (in byte)
     </en>*/
    private static final int LEN_ID28_STATION = 4;

    /**<jp>
     * 応答区分のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of response classification
     </en>*/
    private static final int OFF_ID28_CLASS = OFF_ID28_STATION + LEN_ID28_STATION;

    /**<jp>
     * 応答区分の長さ(Byte)
     </jp>*/
    /**<en>
     * Length of response classification (in byte)
     </en>*/
    private static final int LEN_ID28_CLASS = 2;

    /**<jp>
     * 応答区分を表すフィールド（正常受付）
     </jp>*/
    /**<en>
     * Field that indicates the response classification (normaly received)
     </en>*/
    public static final String CLASS_NORMAL_RECEIVE = "00";

    /**<jp>
     * 応答区分を表すフィールド（該当搬送データなし）
     </jp>*/
    /**<en>
     * Field that indicates the response classification (no corresponding carry data is found)
     </en>*/
    public static final String CLASS_NOT_DATA = "01";

    /**<jp>
     * 応答区分を表すフィールド（該当Locationなし）
     </jp>*/
    /**<en>
     * Field that indicates the response classification (no corresponding location)
     </en>*/
    public static final String CLASS_NOT_LOCATION = "02";

    /**<jp>
     * 応答区分を表すフィールド（該当Stationなし）
     </jp>*/
    /**<en>
     * Field that indicates the response classification (there is no corresponding station)
     </en>*/
    public static final String CLASS_NOT_STATION = "03";

    /**<jp>
     * 応答区分を表すフィールド（該当Locationへの搬送Routeなし）
     </jp>*/
    /**<en>
     * Field that indicates the response classification (No carrying route to the corresponding location)
     </en>*/
    public static final String CLASS_NOT_ROUTE_LOCATION = "04";

    /**<jp>
     * 応答区分を表すフィールド（該当Stationへの搬送Routeなし）
     </jp>*/
    /**<en>
     * Field to indicates response classification ( no route to convey load to the corresponding station)
     </en>*/
    public static final String CLASS_NOT_ROUTE_STATION = "05";

    /**<jp>
     * 応答区分を表すフィールド（該当LocationへAccess不可）
     </jp>*/
    /**<en>
     * Field to indicates response classification (NOT able to access the corresponding location)
     </en>*/
    public static final String CLASS_NOT_ACCESS = "06";

    /**<jp>
     * 応答区分を表すフィールド（Data Error）
     </jp>*/
    /**<en>
     * Field to indicates response classification (Data Error)
     </en>*/
    public static final String CLASS_DATA_ERROR = "99";

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文バッファ
     </jp>*/
    /**<en>
     * communication message buffer
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID28];

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
    public As21Id28()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文をコンストラクタに渡します。
     * @param as21Id28 <code>as21Id28</code>  搬送先変更指示応答(ReceivingStationChangeCommandResponse) ID=28 電文
     </jp>*/
    /**<en>
     * Passes communication message received from AGC to the constructor.
     * @param as21Id28 :<code>as21Id28</code>  message "Response to the Command to change Receiving Station" ID=28
     </en>*/
    public As21Id28(byte[] as21Id28)
    {
        super();
        setReceiveMessage(as21Id28);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 搬送先変更指示応答(ReceivingStationChangeCommandResponse)電文からMC Keyを取得します。
     * @return    MC Key
     </jp>*/
    /**<en>
     * Acquires MC Key from message "Response to the Command to change Receiving Station".
     * @return    MC Key
     </en>*/
    public String getMcKey()
    {
        String mcKey = getContent().substring(OFF_ID28_MCKEY, OFF_ID28_MCKEY + LEN_ID28_MCKEY);
        return (mcKey);
    }

    /**<jp>
     * 搬送先変更指示応答(ReceivingStationChangeCommandResponse)電文から指示区分を取得します。
     * @return    指示区分
     * @throws InvalidProtocolException 指示区分が数字でない場合に報告されます。
     </jp>*/
    /**<en>
     * Acquires instruction classification from the message "Response to the Command to change Receiving Station".
     * @return    instruction classification
     * @throws InvalidProtocolException : Reports if numeric value was not provided for instruction classification.
     </en>*/
    public int getInstructionClassification()
            throws InvalidProtocolException
    {
        int rclass = 9;
        String instructionClassification =
                getContent().substring(OFF_ID28_INS_CLASS, OFF_ID28_INS_CLASS + LEN_ID28_INS_CLASS);
        try
        {
            rclass = Integer.parseInt(instructionClassification);
        }
        catch (Exception e)
        {
            throw (new InvalidProtocolException("Invalid Response:" + instructionClassification));
        }
        return (rclass);
    }

    /**<jp>
     * 搬送先変更指示応答(ReceivingStationChangeCommandResponse)電文からLocation No.を取得します。
     * @return    Location No.
     </jp>*/
    /**<en>
     * Acquires location No. from the message "Response to the Command to change Receiving Station".
     * @return    Location No.
     </en>*/
    public String getLocationNo()
    {
        String locationNo = getContent().substring(OFF_ID28_LOCATION, OFF_ID28_LOCATION + LEN_ID28_LOCATION);
        return (locationNo);
    }

    /**<jp>
     * 搬送先変更指示応答(ReceivingStationChangeCommandResponse)電文からStation No.を取得します。
     * @return    Station No.
     </jp>*/
    /**<en>
     * Acquires station No. from the message "Response to the Command to change Receiving Station".
     * @return    Station No.
     </en>*/
    public String getStationNo()
    {
        String stationNo = getContent().substring(OFF_ID28_STATION, OFF_ID28_STATION + LEN_ID28_STATION);
        return (stationNo);
    }

    /**<jp>
     * 搬送先変更指示応答(ReceivingStationChangeCommandResponse)電文から応答区分を取得します。
     * @return     00:正常受付<BR>
     *              01:該当搬送Dataなし<BR>
     *              02:該当Locationなし<BR>
     *              03:該当Stationなし<BR>
     *              04:該当Locationへの搬送Routeなし<BR>
     *              05:該当Stationへの搬送Routeなし<BR>
     *              06:該当LocationへAccess不可<BR>
     *              99:Data Error
     </jp>*/
    /**<en>
     * Acquires the response classification from the message "Response to the Command to change Receiving Station".
     * @return     00:normaly received<BR>
     *              01:No such carry data is found<BR>
     *              02:No such location<BR>
     *              03:No such station<BR>
     *              04:No carry route to the selected location<BR>
     *              05:No carry route to the selected station<BR>
     *              06:Not able to access to the selected location<BR>
     *              99:Data Error
     </en>*/
    public String getResponseClassification()
    {
        String responseClassification = getContent().substring(OFF_ID28_CLASS, OFF_ID28_CLASS + LEN_ID28_CLASS);
        return (responseClassification);
    }

    /**<jp>
     * 搬送先変更指示応答電文の文字列表現を返します。
     * @return 搬送先変更指示応答電文の文字列表現
     </jp>*/
    /**<en>
     * Returns string representation of the message "Response to the Command to change Receiving Station".
     * @return string representation of the message "Response to the Command to change Receiving Station"
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
        for (int i = 0; i < LEN_ID28; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

