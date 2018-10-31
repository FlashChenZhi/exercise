// $Id: As21Id61.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「作業Mode切替え要求(WorkModeChangeRequest) ID=61」電文を処理します。
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
 * Processes the communication message "WorkModeChangeRequest" ID=61 according to AS21 communication protocol.
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
public class As21Id61
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    // Comment for field
    /**<jp>
     *ID61の電文長(STX,SEQ-No,BCC,ETXを除く)
     </jp>*/
    /**<en>
     *Length of ID61 (excluding STX, SEQ-No, BCC and ETX)
     </en>*/
    public static final int LEN_ID61 = 21;

    /**<jp>
     *ID61のStation No.のオフセット定義
     </jp>*/
    /**<en>
     *Defines offset of station No. of ID61
     </en>*/
    private static final int OFF_ID61_STATION = 0;

    /**<jp>
     *ID61のStation No.の長さ(byte)
     </jp>*/
    /**<en>
     *Length of station No. of ID61(byte)
     </en>*/
    private static final int LEN_ID61_STATION = 4;

    /**<jp>
     *ID61の要求区分のオフセット定義
     </jp>*/
    /**<en>
     *Defines offset of request classificaiton of ID61
     </en>*/
    private static final int OFF_ID61_REQUEST = OFF_ID61_STATION + LEN_ID61_STATION;

    /**<jp>
     *ID61の要求区分の長さ(byte)
     </jp>*/
    /**<en>
     *Length of request classificaton of ID61 (byte)
     </en>*/
    private static final int LEN_ID61_REQUEST = 1;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variable for the preservation of communication messages.
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID61];

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
     * @param as21Id61 <code>as21Id61</code>  作業Mode切替え要求電文
     </jp>*/
    /**<en>
     * Sets the communicatin message received from AGC, then initializes this class.
     * @param as21Id61 :<code>as21Id61</code>  the communicatin message WorkModeChangeRequest
     </en>*/
    public As21Id61(byte[] as21Id61)
    {
        super();
        setReceiveMessage(as21Id61);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 作業Mode切替え要求からStation No.を取得します。
     * @return    Station No.
     </jp>*/
    /**<en>
     * Acquires the station No. from WorkModeChangeRequest.
     * @return    Station No.
     </en>*/
    public String getStationNo()
    {
        String stationNo = getContent().substring(OFF_ID61_STATION, OFF_ID61_STATION + LEN_ID61_STATION);
        return (stationNo);
    }

    /**<jp>
     * 作業Mode切替え要求から要求区分を取得します。
     * @return     要求区分<BR>
     *              1:入庫<BR>
     *              2:出庫<BR>
     *              3:入庫要求Cancel<BR>
     *              4:出庫要求Cancel
     * @throws InvalidProtocolException 要求区分が数字でない場合に報告されます。
     </jp>*/
    /**<en>
     * Acquires the request classification from "WorkModeChangeRequest".
     * @return     request classification<BR>
     *              1:storage<BR>
     *              2:retrieval<BR>
     *              3:Cancel of storage request<BR>
     *              4:Cancel of retrieval request
     * @throws InvalidProtocolException : Reports if numeric value was not provided for request classification.
     </en>*/
    public int getRequestClassification()
            throws InvalidProtocolException
    {

        int rclass;
        String requestClassification = getContent().substring(OFF_ID61_REQUEST, OFF_ID61_REQUEST + LEN_ID61_REQUEST);
        try
        {
            rclass = Integer.parseInt(requestClassification);
        }
        catch (Exception e)
        {
            throw (new InvalidProtocolException("Invalid Response:" + requestClassification));
        }
        return (rclass);

    }

    /**<jp>
     * 作業Mode切替え要求電文の内容を取得します。
     * @return 作業Mode切替え要求電文の内容
     </jp>*/
    /**<en>
     * Acquires the content of the communication message "WorkModeChangeRequest".
     * @return content of the communication message "WorkModeChangeRequest"
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg 作業Mode切替え要求電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC to the internal buffer.
     * @param rmsg the communication message "WorkModeChangeRequest"
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID61; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class
