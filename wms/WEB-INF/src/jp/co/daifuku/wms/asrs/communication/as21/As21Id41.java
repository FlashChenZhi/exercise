// $Id: As21Id41.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「作業Mode切替え要求応答(WorkModeChangeRequestResponse) ID=41」電文を組み立てます。
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
 * Composes communication message "WorkModeChangeRequestResponse" (response to the request of changing work mode)
 * ID=41, according to AS21communication protocol.
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
public class As21Id41
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 応答区分を表すフィールド (正常受付)
     </jp>*/
    /**<en>
     * Field of response classification (normaly received)
     </en>*/
    public static final String GENERAL_RESEPTION = "00";

    /**<jp>
     * 応答区分を表すフィールド (Error (作業中))
     </jp>*/
    /**<en>
     * Field of response classification (Error (working))
     </en>*/
    public static final String ERROR_WORKING = "01";

    /**<jp>
     * 応答区分を表すフィールド (Error (Station No.))
     </jp>*/
    /**<en>
     * Field of response classification(Error (Station No.))
     </en>*/
    public static final String ERROR_STATION_NO = "02";

    /**<jp>
     * Station No.の長さ
     </jp>*/
    /**<en>
     * Length of the station No.
     </en>*/
    protected static final int STATION_NO = 4;

    /**<jp>
     * 応答区分の長さ
     </jp>*/
    /**<en>
     * Length of the response classification
     </en>*/
    protected static final int REQUEST_CLASSIFICATION = 2;

    /**<jp>
     * 作業Mode切替え要求応答電文のデータの長さ
     </jp>*/
    /**<en>
     * Length of data for the communication message "WorkModeChangeRequestResponse"
     </en>*/
    protected static final int LEN_TOTAL = STATION_NO + REQUEST_CLASSIFICATION;

    // Class variables -----------------------------------------------
    /**<jp>
     * Station No.を保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves the station No.
     </en>*/
    private String _stationNo;

    /**<jp>
     * 応答区分を保持する変数
     </jp>*/
    /**<en>
     * Variable which preserves the response classification
     </en>*/
    private String _reqinfo;

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
    public As21Id41()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 作業Mode切替え要求応答電文を作成します。
     * @return    作業Mode切替え要求応答電文
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Creates the communication message "WorkModeChangeRequestResponse".
     * @return    the communication message "WorkModeChangeRequestResponse"
     * @throws  InvalidProtocolException Notifies if the value used is not following the communication message protocol.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        //-------------------------------------------------
        //<jp> ここから先、順序が重要なので注意!</jp>
        //<en> Attention! the order of following must be observed!</en>
        //-------------------------------------------------
        // Station No.
        mbuf.append(_stationNo);
        //<jp> 応答区分</jp>
        //<en> Response classification</en>
        mbuf.append(_reqinfo);
        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Setting for sending message buffer</en>
        //-------------------------------------------------
        // ID
        setID("41");
        //<jp> ID 区分</jp>
        //<en> ID segment</en>
        setIDClass("00");
        //<jp> MC送信時刻</jp>
        //<en> MC sending time</en>
        setSendDate();
        //<jp> AGC送信時刻</jp>
        //<en> AGC sending time</en>
        setAGCSendDate();
        //<jp> テキスト内容</jp>
        //<en> text content</en>
        setContent(String.valueOf(mbuf));

        return (getFromBuffer(0, LEN_TOTAL + OFF_CONTENT));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * Station No.をセットします。
     * @param stationno StationNo.
     * @throws InvalidProtocolException  Station No.が指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Setse Station No.
     * @param stationno StationNo.
     * @throws InvalidProtocolException : Reports if Station No. is not the allowable length.
     </en>*/
    public void setStationNo(String stationno)
            throws InvalidProtocolException
    {
        _stationNo = stationno;
        //<jp> 文字列長チェック</jp>
        //<en> Checks the length of String </en>
        if (_stationNo.length() != STATION_NO)
        {
            throw new InvalidProtocolException("\n" + "Station No. = " + STATION_NO + "--->" + _stationNo);
        }
    }

    /**<jp>
     * 応答区分をセットします。
     * @param requestclass 応答区分
     * @throws InvalidProtocolException 不正な応答区分が指定された場合に報告されます。
     </jp>*/
    /**<en>
     * Sets the response classification
     * @param requestclass response classification
     * @throws InvalidProtocolException : Reports if entered response classification is invalid.
     </en>*/
    public void setRequestClass(String requestclass)
            throws InvalidProtocolException
    {
        _reqinfo = requestclass;
        if (_reqinfo == GENERAL_RESEPTION)
        {
            //<jp> 正常受付</jp>
            //<en> normal receipt</en>
        }
        else if (_reqinfo == ERROR_WORKING)
        {
            //<jp> Error (作業中)</jp>
            //<en> Error (under working)</en>
        }
        else if (_reqinfo == ERROR_STATION_NO)
        {
            // Error (Station No.)
        }
        else
        {
            throw new InvalidProtocolException("\n" + "Exception. Response classification = " + _reqinfo);
        }
    }
}
//end of class

