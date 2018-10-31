// $Id: As21Id35.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「搬送Data削除報告(TransportDataDeletionReport) ID=35」電文を処理します。
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
 * Processes the communication message "TransportDataDeletionReport", ID=35 according to AS21 communicaiton protocol.
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
public class As21Id35
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID35の電文長(STX,SEQ-No,BCC,ETXを除く)。
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>削除要因:</td><td>1 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>搬送先ステーションNo.:</td><td>4 byte</td></tr>
     * <tr><td>制御情報:</td><td>30 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of communication message ID35 (excluding STX, SEQ-No, BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>factor of deletion:</td><td>1 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>receiving station No.:</td><td>4 byte</td></tr>
     * <tr><td>control data:</td><td>30 byte</td></tr>
     * </table>
     * </p>
     </en>*/

    /**<jp>
     * ID35の電文長(STX,SEQ-No,BCC,ETXを除く)。
     </jp>*/
    /**<en>
     * Length of communication message ID35 (excluding STX, SEQ-No, BCC and ETX)
     </en>*/
    public static final int LEN_ID35 = 59;

    /**<jp>
     * ID35の削除要因のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset in factor of deletion with ID35
     </en>*/
    private static final int OFF_ID35_CASE_OF_DELETION = 0;

    /**<jp>
     * ID35の削除要因の長さ(byte)
     </jp>*/
    /**<en>
     * Length of deletion factor with ID35 (in byte)
     </en>*/
    private static final int LEN_ID35_CASE_OF_DELETION = 1;

    /**<jp>
     * ID35のMC KEYのオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of MC Key with ID35
     </en>*/
    private static final int OFF_ID35_MCKEY = OFF_ID35_CASE_OF_DELETION + LEN_ID35_CASE_OF_DELETION;

    /**<jp>
     * ID35のMC KEYの長さ(byte)
     </jp>*/
    /**<en>
     * Length of MC Key with ID35 (in byte)
     </en>*/
    private static final int LEN_ID35_MCKEY = 8;

    /**<jp>
     * ID35の搬送先Station No.のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of receiving station with ID35
     </en>*/
    private static final int OFF_ID35_TO_STATION_NO = OFF_ID35_MCKEY + LEN_ID35_MCKEY;

    /**<jp>
     * ID35の搬送先Station No.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of receiving station No. with ID35 (in byte)
     </en>*/
    private static final int LEN_ID35_TO_STATION_NO = 4;

    /**<jp>
     * ID35の制御情報のオフセット定義
     </jp>*/
    /**<en>
     * Defines offset of control data with D35
     </en>*/
    private static final int OFF_ID35_CONTROL_INFO = OFF_ID35_TO_STATION_NO + LEN_ID35_TO_STATION_NO;

    /**<jp>
     * ID35の制御情報の長さ(byte)
     </jp>*/
    /**<en>
     * Length of control data with ID35 (in byte)
     </en>*/
    private static final int LEN_ID35_CONTROL_INFO = 30;

    /**<jp>
     * 削除要因（削除OPERATIONによる削除）
     </jp>*/
    /**<en>
     * Facto of deletion ( deleted by the deleting opereration) 
     </en>*/
    public static final String C_PRESENCE = "1";

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variables that preserves the communication message
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID35];

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
    public As21Id35()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文セットし、このクラスの初期化を行います。
     * @param as21Id35 <code>as21Id35</code>  搬送Data削除報告電文
     </jp>*/
    /**<en>
     * Sets the communication messages received from the AGC, thne intializes this class.
     * @param as21Id35 :<code>as21Id35</code>  the communication message "TransportDataDeletionReport"
     </en>*/
    public As21Id35(byte[] as21Id35)
    {
        super();
        setReceiveMessage(as21Id35);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 搬送Data削除報告電文からMC Keyを取得します。
     * @return    MC Key
     </jp>*/
    /**<en>
     * Acquires the MC Key from the the communication message "TransportDataDeletionReport"
     * @return    MC Key
     </en>*/
    public String getMcKey()
    {
        String mcKey = getContent().substring(OFF_ID35_MCKEY, OFF_ID35_MCKEY + LEN_ID35_MCKEY);
        return (mcKey);

    }

    /**<jp>
     * 搬送Data削除報告電文から削除要因を取得します。
     * @return     削除要因<BR>
     *              (True):削除Operationによる削除<BR>
     *              (Fauls):MV Down時運用による削除
     </jp>*/
    /**<en>
     * Acquires the factor of deletion from the communication message "TransportDataDeletionReport"
     * @return     factor of deletion<BR>
     *              (True): deleted in Operation<BR>
     *              (Fauls): deleted by MV Down 
     </en>*/
    public boolean getTheCaseOfDeletion()
    {
        String wp =
                getContent().substring(OFF_ID35_CASE_OF_DELETION, OFF_ID35_CASE_OF_DELETION + LEN_ID35_CASE_OF_DELETION);
        return (wp.equals(C_PRESENCE));
    }

    /**<jp>
     * 搬送Data削除報告電文から搬送先StationNo.を取得します。
     * @return    搬送先StationNo.
     </jp>*/
    /**<en>
     * Acquires receiving station No. from the communication message "TransportDataDeletionReport"
     * @return    Receiving station No.
     </en>*/
    public String getReceivingStationNo()
    {
        String receivingStationNo =
                getContent().substring(OFF_ID35_TO_STATION_NO, OFF_ID35_TO_STATION_NO + LEN_ID35_TO_STATION_NO);
        return (receivingStationNo);
    }

    /**<jp>
     * 搬送Data削除報告電文から制御情報を取得します。
     * getMcKeyで返されたMC Keyをセットして最大２件のデータをチェックします。
     * @return    制御情報
     </jp>*/
    /**<en>
     * Acquires the control data from the communication message "TransportDataDeletionReport"
     * Sets MC Key which returned by "getMcKey", then checks up to 2 data.
     * @return    control data
     </en>*/
    public String getControlInformation()
    {
        String controlInformation =
                getContent().substring(OFF_ID35_CONTROL_INFO, OFF_ID35_CONTROL_INFO + LEN_ID35_CONTROL_INFO);
        return (controlInformation);

    }

    /**<jp>
     * 搬送Data削除報告電文の内容を取得します。
     * @return 搬送Data削除報告電文の内容
     </jp>*/
    /**<en>
     * Acquires the contents of the communication message "TransportDataDeletionReport".
     * @return  contents of the communication message "TransportDataDeletionReport"
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
     * @param rmsg 搬送Data削除報告電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg the communication message "TransportDataDeletionReport"
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID35; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }
}
//end of class

