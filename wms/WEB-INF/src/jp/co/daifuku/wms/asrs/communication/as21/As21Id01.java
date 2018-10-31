// $Id: As21Id01.java 87 2008-10-04 03:07:38Z admin $
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
 * AS21通信プロトコルでの「作業開始要求(WorkstartRequest) ID=01」電文を組み立てます。
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
 * Composes communication message "Request of work start ID=01" according to AS21 communication protocol.
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
public class As21Id01
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 日付の長さ 
     </jp>*/
    /**<en>
     * Length of date 
     </en>*/
    protected static final int DATE = 8;

    /**<jp>
     * 時刻の長さ 
     </jp>*/
    /**<en>
     * Length of time 
     </en>*/
    protected static final int TIME = 6;

    /**<jp>
     * 電文の中身の長さ
     </jp>*/
    /**<en>
     * Length of message contents
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
     * @return Version adn the date
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
    public As21Id01()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 作業開始要求電文を作成します。
     * <p><code>
     * 電文の内容は、以下のようになります。<br>
     * <table>
     * <tr><td>日付</td><td>8 byte</td></tr>
     * <tr><td>時刻</td><td>6 byte</td></tr>
     * </table></code></p>
     * @return    作業開始要求電文
     * @throws InvalidProtocolException  プロトコル上、不都合な情報があった場合に通知されます。
     </jp>*/
    /**<en>
     * Creates the communication message reqesting the work to start.
     * <p><code>
     * Communication message should contain the following;<br>
     * <table>
     * <tr><td>date</td><td>8 byte</td></tr>
     * <tr><td>time</td><td>6 byte</td></tr>
     * </table></code></p>
     * @return    Message requesting the work start
     * @throws InvalidProtocolException  : Notifies if improper information is included for protocol aspect.
     </en>*/
    public String getSendMessage()
            throws InvalidProtocolException
    {
        //<jp> テキストバッファ</jp>
        //<en> Text buffer</en>
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
        //<en> Sets up as sending message buffer</en>
        //-------------------------------------------------
        // id
        setID("01");
        //<jp> id 区分</jp>
        //<en> id segment</en>
        setIDClass("00");
        //<jp> 送信時刻</jp>
        //<en> time sent</en>
        setSendDate();
        //<jp> AGC送信時刻</jp>
        //<en> sent time to AGC</en>
        setAGCSendDate("000000");
        //<jp> テキスト内容</jp>
        //<en> contents of text</en>
        setContent(String.valueOf(mbuf));

        return (getFromBuffer(0, LEN_TOTAL + OFF_CONTENT));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * 現在の年月日を求める。
     * @return 年.月.日
     </jp>*/
    /**<en>
     * Requests current date (year, month, date)
     * @return year.month. date
     </en>*/
    private String getDate()
    {
        return (DateOperator.getSysdate());
    }

    /**<jp>
     * 現在の時分秒を求める。
     * @return 時.分.秒
     </jp>*/
    /**<en>
     * Requests current hour, minute, second
     * @return time.minute. second
     </en>*/
    private String getTime()
    {
        return (DateOperator.getSysdateTime().substring(8, 14));
    }

}
// end of class