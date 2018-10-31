// $Id: As21Id63.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「作業Mode切替え完了報告(WorkModeChangeCompletionReport) ID=63」電文を処理します。
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
 * Processes the communication message "WorkModeChangeCompletionReport" ID=63 according to AS21 communication protocol.
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
public class As21Id63
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------

    // Comment for field
    /**<jp>
     *ID63の電文長(STX,SEQ-No,BCC,ETXを除く)
     </jp>*/
    /**<en>
     *Length of ID63 (excluding STX, SEQ-No, BCC and ETX)
     </en>*/
    public static final int LEN_ID63 = 21;

    /**<jp>
     *ID63のStation No.のオフセット定義
     </jp>*/
    /**<en>
     *Defines offset of the station No. of ID63.
     </en>*/
    private static final int OFF_ID63_STATION = 0;

    /**<jp>
     *ID63のStation No.の長さ(byte)
     </jp>*/
    /**<en>
     *Length of station No. of ID63 (byte)
     </en>*/
    private static final int LEN_ID63_STATION = 4;

    /**<jp>
     *ID63の完了MODEのオフセット定義
     </jp>*/
    /**<en>
     *Defines offset of completion mode of ID63.
     </en>*/
    private static final int OFF_ID63_MODE = OFF_ID63_STATION + LEN_ID63_STATION;

    /**<jp>
     *ID63の完了MODEの長さ(byte)
     </jp>*/
    /**<en>
     *Length of completion mode of ID63 (byte)
     </en>*/
    private static final int LEN_ID63_MODE = 1;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variables for the preservation of communication messages
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID63];

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
     * AGCから受信した電文セットし、このクラスの初期化を行います。
     * @param as21Id63 <code>as21Id63</code>  作業Mode切替え完了報告電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC; then initializes this class.
     * @param as21Id63 :<code>as21Id63</code>  the communication message "WorkModeChangeCompletionReport"
     </en>*/
    public As21Id63(byte[] as21Id63)
    {
        super();
        setReceiveMessage(as21Id63);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 作業Mode切替え完了報告からStation No.を取得します。
     * @return    作業Mode切り替えが完了したStation No.
     </jp>*/
    /**<en>
     * Acquires the station No. from the report of completion of work mode change.
     * @return    the station No. the work mode change completed.
     </en>*/
    public String getStationNo()
    {
        String stationNo = getContent().substring(OFF_ID63_STATION, OFF_ID63_STATION + LEN_ID63_STATION);
        return (stationNo);
    }

    /**<jp>
     * 作業Mode切替え完了報告から完了Modeを取得します。
     * @return     完了Mode<BR>
     *              1:入庫<BR>
     *              2:出庫
     * @throws InvalidProtocolException 完了Modeが数字でない場合に報告されます。
     </jp>*/
    /**<en>
     * Acquires the completion mode from the report of completion of work mode change.
     * @return     Completion Mode<BR>
     *              1:Storage<BR>
     *              2:Retrieval
     * @throws InvalidProtocolException : Reports if numeric value was not provided for completion mode.
     </en>*/
    public int getCompletionMode()
            throws InvalidProtocolException
    {

        int rclass;
        String completionMode = getContent().substring(OFF_ID63_MODE, OFF_ID63_MODE + LEN_ID63_MODE);
        try
        {
            rclass = Integer.parseInt(completionMode);
        }
        catch (Exception e)
        {
            throw (new InvalidProtocolException("Invalid Response:" + completionMode));
        }
        return (rclass);

    }

    /**<jp>
     * 作業Mode切替え完了報告電文の内容を取得します。
     * @return 作業Mode切替え完了報告電文の内容
     </jp>*/
    /**<en>
     * Acquires the contents of the communication message "WorkModeChangeCompletionReport".
     * @return contents of the communication message "WorkModeChangeCompletionReport"
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg 作業Mode切替え完了報告電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg the communication message "WorkModeChangeCompletionReport"
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID63; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
// end of class

