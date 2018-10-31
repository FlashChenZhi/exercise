// $Id: As21Id02.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.DateOperator;
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「日付・時刻Data(DateTimeData) ID=02」電文を組み立てます。
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
 * Composes communication message "Data of date and time) ID=02" according to AS21 communication protocol.
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
public class As21Id02
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 日付の長さ
     </jp>*/
    /**<en>
     * Length of the date
     </en>*/
    protected static final int DATE = 8;

    /**<jp>
     * 時刻の長さ
     </jp>*/
    /**<en>
     * Length of the time
     </en>*/
    protected static final int TIME = 6;

    /**<jp>
     * 個々の日付・時刻データの電文長
     </jp>*/
    /**<en>
     * Data length of each date and time in the communication message
     </en>*/
    protected static final int LEN_TOTAL = DATE + TIME;

    // Class variables -----------------------------------------------

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
     * コンストラクタ
     </jp>*/
    /**<en>
     * Constructor
     </en>*/
    public As21Id02()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 日付・時刻Data電文を作成します。
     * <p><code>
     * 電文の内容は、以下のようになります。<br>
     * <table>
     * <tr><td>日付</td><td>8 byte</td></tr>
     * <tr><td>時刻</td><td>6 byte</td></tr>
     * </table></code></p>
     * @return 日付・時刻Data
     * @throws InvalidProtocolException  プロトコル上、不都合な情報があった場合に通知されます。
     </jp>*/
    /**<en>
     * Creates the communication message of date and time data.
     * <p><code>
     * Communication message should contain the following;<br>
     * <table>
     * <tr><td>date</td><td>8 byte</td></tr>
     * <tr><td>time</td><td>6 byte</td></tr>
     * </table></code></p>
     * @return date and time Data
     * @throws InvalidProtocolException  : Notifies if improper information is included for protocol aspect.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        //-------------------------------------------------
        //<jp> ここから先、順序が重要なので注意!</jp>
        //<en> Attention: the order of contents must be observed!</en>
        //-------------------------------------------------
        //<jp> 日付</jp>
        //<en> Date</en>
        mbuf.append(getDate());
        //<jp> 時刻</jp>
        //<en> Time</en>
        mbuf.append(getTime());
        //-------------------------------------------------
        //<jp> 送信メッセージバッファに設定</jp>
        //<en> Sets as sending message buffer</en>
        //-------------------------------------------------
        // id
        setID("02");
        //<jp> id 区分</jp>
        //<en> id segment</en>
        setIDClass("00");
        //<jp> 送信時刻</jp>
        //<en> time sent</en>
        setSendDate();
        //<jp> AGC送信時刻</jp>
        //<en> time sent to AGC</en>
        setAGCSendDate("000000");
        //<jp> テキスト内容</jp>
        //<en> contents of text</en>
        setContent(String.valueOf(mbuf));

        return (getFromBuffer(0, LEN_TOTAL + OFF_CONTENT));
    }

    /**<jp>
     * 日付を作成します。
     * @return    年.月.日
     </jp>*/
    /**<en>
     * Creates the date.
     * @return    year.month.day
     </en>*/
    public String getDate()
    {
        return (DateOperator.getSysdate());
    }

    /**<jp>
     * 時刻を作成します。
     * @return    時.分.秒
     </jp>*/
    /**<en>
     * Creates time.
     * @return    hour.minute. second
     </en>*/
    public String getTime()
    {

        return (DateOperator.getSysdateTime().substring(8, 14));
    }
    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
// end of class
