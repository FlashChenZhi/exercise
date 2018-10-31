// $Id: As21Id46.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「出庫Trigger応答(RetrievalTriggerResponse) ID=46」電文を組み立てます。
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
 * Composes communication message "RetrievalTriggerResponse" ID=46 according to AS21 communication protocol.
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
public class As21Id46
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * Station Noの長さ
     </jp>*/
    /**<en>
     * Length of station No.
     </en>*/
    protected static final int STATION_NO = 4;

    /**<jp>
     * 応答区分の長さ
     </jp>*/
    /**<en>
     * Length of response classification
     </en>*/
    protected static final int RESPONSE_CLASS = 2;

    /**<jp>
     * 電文長
     </jp>*/
    /**<en>
     * Length of the communication message
     </en>*/
    protected static final int LEN_TOTAL = STATION_NO + RESPONSE_CLASS;

    // Class variables -----------------------------------------------
    /**<jp>
     * StationNoを保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves Station No.
     </en>*/
    private String _stationNo;

    /**<jp>
     * 応答区分を保持する変数
     </jp>*/
    /**<en>
     * Variable that preserves response classification
     </en>*/
    private String _responseClassification;

    /**<jp>
     * 応答区分を表すフィールド (正常受付)
     </jp>*/
    /**<en>
     * Field for the response classification (received as normal status)
     </en>*/
    public static final String NORMAL_RESEPTION = "00";

    /**<jp>
     * 応答区分を表すフィールド (Data Error)
     </jp>*/
    /**<en>
     * Field for the response classification (Data Error)
     </en>*/
    public static final String DATA_ERROR = "99";

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
    public As21Id46()
    {
        super();
    }

    /**<jp>
     * StationNoと
     * 応答区分を指定して、このクラスのインスタンスを生成します。
     * @param sn Station No
     * @param rc 応答区分
     </jp>*/
    /**<en>
     * Generates the instance of this class by specifying Station No. and the
     * response classification.
     * @param sn Station No.
     * @param rc response classification
     </en>*/
    public As21Id46(String sn, String rc)
    {
        super();
        _stationNo = sn;
        _responseClassification = rc;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 出庫Trigger応答電文を作成します。
     * @return    出庫Trigger応答電文
     * @throws  InvalidProtocolException 通信電文プロトコルで規定されている値とは異なる場合に通知されます。
     </jp>*/
    /**<en>
     * Creates the communication message "RetrievalTriggerResponse".
     * @return    communication message "RetrievalTriggerResponse"
     * @throws  InvalidProtocolException :Notifies if provided value is not following the communication message protocol.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        //-------------------------------------------------
        //<jp> ここから先、順序が重要なので注意!</jp>
        //<en> Attention! The order of the following must be observed!</en>
        //-------------------------------------------------
        // Station No.
        mbuf.append(getStation());
        //<jp> 応答区分</jp>
        //<en> response classification</en>
        mbuf.append(getResponseClass());

        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Setting for the sending message buffer</en>
        //------------------------------------------------
        // ID
        setID("46");
        //<jp> ID 区分</jp>
        //<en> ID segment</en>
        setIDClass("00");
        //<jp> MC送信時刻</jp>
        //<en> MC sending time</en>
        setSendDate();
        //<jp> AGC送信時刻</jp>
        //<en> AGC sending time</en>
        setAGCSendDate("000000");
        //<jp> AGC送信時刻</jp>
        //<en> text contents</en>
        setContent(String.valueOf(mbuf));

        return (getFromBuffer(0, LEN_TOTAL + OFF_CONTENT));
    }

    /**<jp>
     * Station No.をsetします。
     * @param st Station No.
     </jp>*/
    /**<en>
     * Sets station No.
     * @param st Station No.
     </en>*/
    public void setStation(String st)
    {
        _stationNo = st;
    }

    /**<jp>
     * 応答区分をsetします。
     * @param re 応答区分（true:正常終了 false:Data Error）
     </jp>*/
    /**<en>
     * Sets response classification.
     * @param re response classification (true: normal end, false: data error)
     </en>*/
    public void setResponseClassification(boolean re)
    {
        if (re)
        {
            //<jp>正常終了</jp>
            //<en>Normal End</en>
            _responseClassification = NORMAL_RESEPTION;
        }
        else
        {
            //Data Error
            _responseClassification = DATA_ERROR;
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * Station No を取得します。
     * @return Station No
     * @throws InvalidProtocolException  Station Noが指定の長さでなかった場合に報告されます。
     </jp>*/
    /**<en>
     * Acquires station No.
     * @return Station No
     * @throws InvalidProtocolException : Reports if station No. is not the allowable length.
     </en>*/
    private String getStation()
            throws InvalidProtocolException
    {
        if (_stationNo.length() != STATION_NO)
        {
            throw new InvalidProtocolException("stationNumber=" + STATION_NO + "---->" + _stationNo);
        }
        return (_stationNo);
    }

    /**<jp>
     * 応答区分を取得します。
     * @return 応答区分
     </jp>*/
    /**<en>
     * Acquires response classification.
     * @return response classification
     </en>*/
    private String getResponseClass()
    {
        return (_responseClassification);
    }

}
//end of class
