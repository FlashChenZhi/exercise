// $Id: As21Id26.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「到着報告(ArrivalReport) ID=26」電文を処理します。
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
 * Processes communication message "Arrival Report, ID=26" acording to AS21 communication protocol.
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
public class As21Id26
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID26の電文長(STX,SEQ-No,BCC,ETXを除く)。
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>ステーションNo.:</td><td>4 byte</td></tr>
     * <tr><td>荷姿情報:</td><td>2 byte</td></tr>
     * <tr><td>在荷情報:</td><td>1 byte</td></tr>
     * <tr><td>BC Data:</td><td>30 byte</td></tr>
     * <tr><td>制御情報:</td><td>30 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of message ID26 (excluding STX, SEQ-No, BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>station number:</td><td>4 byte</td></tr>
     * <tr><td>Load size:</td><td>2 byte</td></tr>
     * <tr><td>Load presence:</td><td>1 byte</td></tr>
     * <tr><td>BC Data:</td><td>30 byte</td></tr>
     * <tr><td>control information:</td><td>30 byte</td></tr>
     * </table>
     * </p>
     </en>*/
    public static final int LEN_ID26 = 91;

    /**<jp>
     * MC Keyのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of MC Key
     </en>*/
    private static final int OFF_ID26_MCKEY = 0;

    /**<jp>
     * MC Keyの長さ
     </jp>*/
    /**<en>
     * Length of MC Key
     </en>*/
    private static final int LEN_ID26_MCKEY = 8;

    /**<jp>
     * ステーションのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of station
     </en>*/
    private static final int OFF_ID26_STATION = OFF_ID26_MCKEY + LEN_ID26_MCKEY;

    /**<jp>
     * ステーションの長さ
     </jp>*/
    /**<en>
     * Length of station
     </en>*/
    private static final int LEN_ID26_STATION = 4;

    /**<jp>
     * 荷姿情報のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of load size
     </en>*/
    private static final int OFF_ID26_DIM_INFO = OFF_ID26_STATION + LEN_ID26_STATION;

    /**<jp>
     * 荷姿情報の長さ
     </jp>*/
    /**<en>
     * Length of load size
     </en>*/
    private static final int LEN_ID26_DIM_INFO = 2;

    /**<jp>
     * 在荷情報のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of load presence
     </en>*/
    private static final int OFF_ID26_PRESENCE_INFO = OFF_ID26_DIM_INFO + LEN_ID26_DIM_INFO;

    /**<jp>
     * 在荷情報の長さ
     </jp>*/
    /**<en>
     * Length of load presence
     </en>*/
    private static final int LEN_ID26_PRESENCE_INFO = 1;

    /**<jp>
     * BC Dataのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of BC Data
     </en>*/
    private static final int OFF_ID26_BC_DATA = OFF_ID26_PRESENCE_INFO + LEN_ID26_PRESENCE_INFO;

    /**<jp>
     * BC Dataの長さ
     </jp>*/
    /**<en>
     * Length of BC Data
     </en>*/
    private static final int LEN_ID26_BC_DATA = 30;

    /**<jp>
     * 制御情報のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of control information
     </en>*/
    private static final int OFF_ID26_CONTROL_INFO = OFF_ID26_BC_DATA + LEN_ID26_BC_DATA;

    /**<jp>
     * 制御情報の長さ
     </jp>*/
    /**<en>
     * Length of control information
     </en>*/
    private static final int LEN_ID26_CONTROL_INFO = 30;

    /**<jp>
     * 在荷ありを表すフィールド
     </jp>*/
    /**<en>
     * Field to indicate the presence of load.
     </en>*/
    public static final String C_PRESENCE = "1";

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文バッファ
     </jp>*/
    /**<en>
     * Communication message buffer
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID26];

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
    public As21Id26()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文をコンストラクタに渡します。
     * @param as21Id26 <code>as21Id26</code>  到着報告(ArrivalReport) ID=26 電文
     </jp>*/
    /**<en>
     * Passes communication message received from AGC to the constructor.
     * @param as21Id26 :<code>as21Id26</code>  communication message "Arrival Report ID=26"
     </en>*/
    public As21Id26(byte[] as21Id26)
    {
        super();
        setReceiveMessage(as21Id26);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 到着報告(ArrivalReport)電文からMC Keyを取得します。
     * @return    MC Key
     </jp>*/
    /**<en>
     * Acquires MC Key from communication message Arrival Report.
     * @return    MC Key
     </en>*/
    public String getMcKey()
    {
        String mcKey = getContent().substring(OFF_ID26_MCKEY, OFF_ID26_MCKEY + LEN_ID26_MCKEY);
        return (mcKey);
    }

    /**<jp>
     * 到着報告(ArrivalReport)電文からStation No.を取得します。
     * @return    Station No.
     </jp>*/
    /**<en>
     * Acquires station number from communication message Arrival Report.
     * @return    Station No.
     </en>*/
    public String getStationNo()
    {
        String stationNo = getContent().substring(OFF_ID26_STATION, OFF_ID26_STATION + LEN_ID26_STATION);
        return (stationNo);
    }

    /**<jp>
     * 到着報告(ArrivalReport)電文から荷姿情報を取得します。
     * @return    荷姿の検出結果
     </jp>*/
    /**<en>
     * Acquires load size from communication message communication message Arrival Report.
     * @return   detected results for load size
     </en>*/
    public String getDimensionInformation()
    {
        String dimensionInformation = getContent().substring(OFF_ID26_DIM_INFO, OFF_ID26_DIM_INFO + LEN_ID26_DIM_INFO);
        return (dimensionInformation);
    }

    /**<jp>
     * 到着報告(ArrivalReport)電文から在荷情報を取得します。
     * @return    搬送物の在荷情報が格納される。True:在荷なし False:在荷あり
     </jp>*/
    /**<en>
     * Acquires load presence from communication message Arrival Report.
     * @return    Load presence to convey will be stored. True: no load presence False: there are load presence
     </en>*/
    public boolean getLoad()
    {
        String wp = getContent().substring(OFF_ID26_PRESENCE_INFO, OFF_ID26_PRESENCE_INFO + LEN_ID26_PRESENCE_INFO);
        return (wp.equals(C_PRESENCE));
    }

    /**<jp>
     * 到着報告(ArrivalReport)電文からBC Dataを取得します。
     * @return    BC Data（バーコードデータ）
     </jp>*/
    /**<en>
     * Acquires BC data from the communication message ArrivalReport.
     * @return    BC Data (Bar code data)
     </en>*/
    public String getBcData()
    {
        String bcData = getContent().substring(OFF_ID26_BC_DATA, OFF_ID26_BC_DATA + LEN_ID26_BC_DATA);
        return (bcData);
    }

    /**<jp>
     * 到着報告(ArrivalReport)電文から制御情報を取得します。
     * @return    制御情報
     </jp>*/
    /**<en>
     * Acquires control information from message"ArrivalReport".
     * @return    control information
     </en>*/
    public String getControlInformation()
    {
        String controlInformation =
                getContent().substring(OFF_ID26_CONTROL_INFO, OFF_ID26_CONTROL_INFO + LEN_ID26_CONTROL_INFO);
        return (controlInformation);
    }

    /**<jp>
     * 到着報告電文の内容を取得します。
     * @return  出庫指示応答TEXTの内容
     </jp>*/
    /**<en>
     * Acquires the contents of communication message "Arrival Report".
     * @return  text content of retrieval instruction response
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
     * @param rmsg AGCから受け取った電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg message received from AGC 
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID26; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

}
//end of class

