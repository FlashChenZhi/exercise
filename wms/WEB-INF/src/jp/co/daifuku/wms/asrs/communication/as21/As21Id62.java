// $Id: As21Id62.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「作業Mode切替え指示応答(WorkModeChangeCommandResponse) ID=62」電文を処理します。
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
 * Processes the communication message "WorkModeChangeCommandResponse" ID=62, response to the command to 
 * change the work mode, according to AS21 communication protocol.
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
public class As21Id62
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     *ID62の電文長(STX,SEQ-No,BCC,ETXを除く)
     </jp>*/
    /**<en>
     *Length of communication message ID62 (excluding STX, SEQ-No, BCC and ETX)
     </en>*/
    public static final int LEN_ID62 = 22;

    /**<jp>
     *ID62のStation No.のオフセット定義
     </jp>*/
    /**<en>
     *Defines offset of station No. of ID62
     </en>*/
    private static final int OFF_ID62_STATION = 0;

    /**<jp>
     *ID62のStation No.の長さ(byte)
     </jp>*/
    /**<en>
     *Length of station No. of ID62 (byte)
     </en>*/
    private static final int LEN_ID62_STATION = 4;

    /**<jp>
     *ID62応答区分のオフセット定義
     </jp>*/
    /**<en>
     *Defines offset of resopnse classification of ID62
     </en>*/
    private static final int OFF_ID62_REQUEST = OFF_ID62_STATION + LEN_ID62_STATION;

    /**<jp>
     *ID62の応答区分の長さ(byte)
     </jp>*/
    /**<en>
     *Length of response classification of ID62 (byte)
     </en>*/
    private static final int LEN_ID62_REQUEST = 2;

    /**<jp>
     *応答区分の"正常受付"の定義
     </jp>*/
    /**<en>
     *Defines response classification for "received as normal status"
     </en>*/
    public static final String NORMAL = "00";

    /**<jp>
     *応答区分の"Error（Mode切替中）"の定義
     </jp>*/
    /**<en>
     *Defines response classification for "error (mode is being changed)"
     </en>*/
    public static final String MODE_CHANGE = "01";

    /**<jp>
     *応答区分の"Error（Station No.Error）"の定義
     </jp>*/
    /**<en>
     *Defines response classification for "error (station No. error)
     </en>*/
    public static final String STATION_ERROR = "02";

    /**<jp>
     *応答区分の"Error（指示Mode）"の定義
     </jp>*/
    /**<en>
     *Defines response classification for "error (instruction mode)"
     </en>*/
    public static final String MISION_MODE = "03";

    /**<jp>
     *応答区分のError"（搬送Data有り）"の定義
     </jp>*/
    /**<en>
     *Defines response classification for "error (carrying data exists)"
     </en>*/
    public static final String H_MODE = "04";

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variable for the preservation of communication messages
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID62];

    // Class method --------------------------------------------------
    /**<jp> このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en> Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * AGCから受信した電文セットし、このクラスの初期化を行います。
     * @param as21Id62 <code>as21Id62</code>  作業Mode切替え指示応答電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC; then initializes this class.
     * @param as21Id62 :<code>as21Id62</code>  the communication message "WorkModeChangeCommandResponse"
     </en>*/
    public As21Id62(byte[] as21Id62)
    {
        super();
        setReceiveMessage(as21Id62);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 作業Mode切替え指示応答からStation No.を取得します。
     * @return    Station No.
     </jp>*/
    /**<en>
     * Acquires the station No. from the reponse to WorkModeChangeCommand.
     * @return    Station No.
     </en>*/
    public String getStationNo()
    {
        String stationNo = getContent().substring(OFF_ID62_STATION, OFF_ID62_STATION + LEN_ID62_STATION);
        return (stationNo);
    }

    /**<jp>
     * 作業Mode切替え指示応答から応答区分を取得します。
     * @return     応答区分<BR>
     *              00:正常受付<BR>
     *              01:Error(Mode切替え中)<BR>
     *              02:Error(Station No. Error)<BR>
     *              03:Error(指示Mode)<BR>
     *              04:Error(搬送Data有り)
     </jp>*/
    /**<en>
     * Acquires response classification from "WorkModeChangeCommandResponse".
     * @return     Response classification<BR>
     *              00:Received as normal status<BR>
     *              01:Error(Mode is being changed)<BR>
     *              02:Error(Station No. Error)<BR>
     *              03:Error(Instruction Mode)<BR>
     *              04:Error(Carrying data exists)
     </en>*/
    public String getResponseClassification()
    {
        String responseClassification = getContent().substring(OFF_ID62_REQUEST, OFF_ID62_REQUEST + LEN_ID62_REQUEST);

        return (responseClassification);
    }

    /**<jp>
     * 作業Mode切替え指示応答電文の内容を取得します。
     * @return 作業Mode切替え指示応答電文の内容
     </jp>*/
    /**<en>
     * Acquires the contents of the communication message "WorkModeChangeCommandResponse".
     * @return  contents of he communication message "WorkModeChangeCommandResponse"
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg 作業Mode切替え指示応答電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg the communication message "WorkModeChangeCommandResponse"
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID62; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

