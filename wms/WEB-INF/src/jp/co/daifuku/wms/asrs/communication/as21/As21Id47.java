// $Id: As21Id47.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「出庫Trigger再送要求(RetrievalTriggerRepetitionRequest) ID=47」電文を組み立てます。<BR>
 * <FONT SIZE="5" COLOR="RED">未実装となっています。注意して下さい。</FONT>
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
 * Composes communication message "RetrievalTriggerRepetitionRequest" ID=47 (request for the repetition of 
 * retrieval trigger) according to AS21 communication protocol.<BR>
 * <FONT SIZE="5" COLOR="RED"> Not implemented; please be careful.</FONT>
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
public class As21Id47
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * Station Noの長さ(byte)
     </jp>*/
    /**<en>
     * Length of station No. (in byte)
     </en>*/
    private static final int LEN_STATION_NO = 4;

    /**<jp>
     * 個々の出庫Trigger再送要求電文長
     </jp>*/
    /**<en>
     * Length of respective communication message "RetrievalTriggerRepetitionRequest"
     </en>*/
    private static final int LEN_TOTAL = LEN_STATION_NO;

    // Class variables -----------------------------------------------
    /**<jp>
     * Station Noを保持するための変数
     </jp>*/
    /**<en>
     * Variable for the preservation of station No.
     </en>*/
    private static String _stationNo;

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
    public As21Id47()
    {
        super();
    }

    /**<jp>
     * 出庫Triggerによる自動出庫運用のStationNo.をセットしこのクラスを初期化します。
     * @param stno <code>stno</code> 出庫Triggerによる自動出庫運用のStationNo.
     </jp>*/
    /**<en>
     * Sets the station No. for the automatic retrieval operation driven by
     * auto-retrieval trigger; then initializes this class.
     * @param stno :<code>stno</code> Station No. for automatic retrieval operation
     * using retrieval trigger.
     </en>*/
    public As21Id47(String stno)
    {
        super();
        _stationNo = stno;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 出庫Trigger再送要求電文を作成します。
     * <p>出庫Trigger再送要求を出すのに必要な</p>
     * <ul>
     * <li>Station No
     * </ul>
     * <p>は、コンストラクタで渡されたStation Noから情報を入手します。
     * </p>
     * @return   出庫Trigger再送要求電文
     * @throws InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知します。
     </jp>*/
    /**<en>
     * Creates the communication message "RetrievalTriggerRepetitionRequest".
     * <p> </p>required to release RetrievalTriggerRepetitionRequest
     * <ul>
     * <li>Station No
     * </ul>
     * <p> acquires data from the Station No that has been passed in constructor.
     * </p>
     * @return   communication message "RetrievalTriggerRepetitionRequest"
     * @throws InvalidProtocolException : Notifies if provided value is not following the communication message protocol.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        // Station No.
        mbuf.append(getStationNo());
        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Setting for the sending message buffer</en>
        //-------------------------------------------------
        // ID
        setID("47");
        //<jp> ID 区分</jp>
        //<en> ID segment</en>
        setIDClass("00");
        //<jp> MC送信時刻</jp>
        //<en> MC sending time</en>
        setSendDate();
        //<jp> AGC送信時刻</jp>
        //<en> AGC sending time</en>
        setAGCSendDate("000000");
        //<jp> 電文内容</jp>
        //<en> text contents</en>
        setContent(String.valueOf(mbuf));

        return (getFromBuffer(0, LEN_TOTAL + OFF_CONTENT));
    }

    /**<jp>
     * Station No.をsetします。
     * @param str Station No.
     </jp>*/
    /**<en>
     * Sets station No.
     * @param str Station No.
     </en>*/
    public void setStationNo(String str)
    {
        _stationNo = str;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * 出庫Trigger再送要求電文からStaionNoを取得します。
     * @return    StationNo
     * @throws InvalidProtocolException  Station Noが指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Acquires station No. from the communication message "RetrievalTriggerRepetitionRequest"
     * @return    StationNo
     * @throws InvalidProtocolException : Reports if station No. is not the allowable length.
     </en>*/
    private String getStationNo()
            throws InvalidProtocolException
    {
        if (_stationNo.length() == LEN_STATION_NO)
        {
            return (_stationNo);
        }
        else
        {
            throw new InvalidProtocolException("stationNo=" + LEN_STATION_NO + "--->" + _stationNo);
        }
    }

}
//end of class
