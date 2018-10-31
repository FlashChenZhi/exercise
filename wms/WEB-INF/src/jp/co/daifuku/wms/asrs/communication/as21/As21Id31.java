// $Id: As21Id31.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「代替棚指示応答(AlternativeLocationCommandResponse) ID=31」電文を処理します。
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
 * Processes tje communication message "Response to the command: alternative location", ID-31 according to 
 * AS21 communciation protocol.
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
public class As21Id31
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID31の電文長(STX,SEQ-No,BCC,ETXを除く)。
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
     * Length of the communication message ID31 (excluding STX, SEQ-No, BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>response classification:</td><td>2 byte</td></tr>
     * </table>
     * </p>
     </en>*/
    public static final int LEN_ID31 = 26;

    /**<jp>
     * MC Keyのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of MC Key.
     </en>*/
    private static final int OFF_ID31_MCKEY = 0;

    /**<jp>
     * MC Keyの長さ(Byte)
     </jp>*/
    /**<en>
     * Length of MC Key (in byte)
     </en>*/
    private static final int LEN_ID31_MCKEY = 8;

    /**<jp>
     * 応答区分のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of response classification
     </en>*/
    private static final int OFF_ID31_CLASS = OFF_ID31_MCKEY + LEN_ID31_MCKEY;

    /**<jp>
     * 応答区分の長さ(Byte)
     </jp>*/
    /**<en>
     * Length of response classification (in byte)
     </en>*/
    private static final int LEN_ID31_CLASS = 2;

    /**<jp>
     * 応答区分を表すフィールド（正常受付）
     </jp>*/
    /**<en>
     * Field of response classification (normaly received)
     </en>*/
    public static final String CLASS_NORMAL_RECEIVE = "00";

    /**<jp>
     * 応答区分を表すフィールド（該当搬送データなし）
     </jp>*/
    /**<en>
     * Field of response classification (no such carry data is found)
     </en>*/
    public static final String CLASS_NOT_DATA = "01";

    /**<jp>
     * 応答区分を表すフィールド（指示区分異常）
     </jp>*/
    /**<en>
     * Field of response classification (instruction classification error)
     </en>*/
    public static final String CLASS_INSTRUCT_ERROR = "02";

    /**<jp>
     * 応答区分を表すフィールド（状態異常）
     </jp>*/
    /**<en>
     * Field of response classification  (status error)
     </en>*/
    public static final String CLASS_STATE_ERROR = "03";

    /**<jp>
     * 応答区分を表すフィールド（該当Locationへの搬送Routeなし）
     </jp>*/
    /**<en>
     * Field of response classification (there is no carry route to the selected location)
     </en>*/
    public static final String CLASS_NOT_ROUTE_LOCATION = "04";

    /**<jp>
     * 応答区分を表すフィールド（該当Stationへの搬送Routeなし）
     </jp>*/
    /**<en>
     * Field of response classification (there is no carry route to the selected station)
     </en>*/
    public static final String CLASS_NOT_ROUTE_STATION = "05";

    /**<jp>
     * 応答区分を表すフィールド（該当LocationへAccess不可）
     </jp>*/
    /**<en>
     * Field of response classification (NOT able to access to the selected location)
     </en>*/
    public static final String CLASS_NOT_ACCESS = "06";

    /**<jp>
     * 応答区分を表すフィールド（Data Error）
     </jp>*/
    /**<en>
     * Field of response classification (Data Error)
     </en>*/
    public static final String CLASS_DATA_ERROR = "99";

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文バッファ
     </jp>*/
    /**<en>
     * Communication message
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID31];

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
    public As21Id31()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文をコンストラクタに渡します。
     * @param as21Id31 <code>as21Id31</code>  代替棚指示応答(AlternativeLocationCommandResponse) ID=31 電文
     </jp>*/
    /**<en>
     * Passes communication message received from AGC to the constructor.
     * @param as21Id31 :<code>as21Id31</code>  communication message "Response to the command: alternative location", ID-31
     </en>*/
    public As21Id31(byte[] as21Id31)
    {
        super();
        setReceiveMessage(as21Id31);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 代替棚指示応答(AlternativeLocationCommandResponse)電文からMC Keyを取得します。
     * @return    MC Key
     </jp>*/
    /**<en>
     * Acquires MC Key from the communication message "Response to the command: alternative location".
     * @return    MC Key
     </en>*/
    public String getMcKey()
    {
        String mcKey = getContent().substring(OFF_ID31_MCKEY, OFF_ID31_MCKEY + LEN_ID31_MCKEY);
        return (mcKey);
    }

    /**<jp>
     * 代替棚指示応答(AlternativeLocationCommandResponse)電文から応答区分を取得します。
     * @return     00:正常受付<BR>
     *              01:該当搬送Dataなし<BR>
     *              02:指示区分異常<BR>
     *              03:状態異常<BR>
     *              04:該当LocationへのRouteなし<BR>
     *              05:該当StationへのRouteなし<BR>
     *              06:該当LocationへAccess不可<BR>
     *              99:Data Error
     </jp>*/
    /**<en>
     * Acquires response classification from communication message "Response to the command: alternative location"
     * @return     00:normaly received<BR>
     *              01:No such carry data is found<BR>
     *              02:instruction classification error<BR>
     *              03:Status error<BR>
     *              04:there is no route to the selected location<BR>
     *              05:there is no route to the selected station<BR>
     *              06:NOT able to access to the selected location<BR>
     *              99:Data Error
     </en>*/
    public String getResponseClassification()
    {
        String responseClassification = getContent().substring(OFF_ID31_CLASS, OFF_ID31_CLASS + LEN_ID31_CLASS);
        return (responseClassification);
    }

    /**<jp>
     * 代替棚指示応答電文の文字列表現を返します。
     * @return 代替棚指示応答電文の文字列表現
     </jp>*/
    /**<en>
     * Returns string representation of the communication message "Response to the command: alternative location"
     * @return string representation of the communication message "Response to the command: alternative location"
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
     * ets communication message received from AGC to the internal buffer.
     * @param rmsg message received from AGC
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID31; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

