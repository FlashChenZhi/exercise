// $Id: As21Id22.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;

/**<jp>
 * AS21通信プロトコルでの「日付・時刻要求(DateTimeRequest) ID=22」電文を処理します。
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
 * Processes the communicarion message "Date and Time Request, ID=22" according to AS21 communication protocol.
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
public class As21Id22
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID22の電文長(STX,SEQ-NO,BCC,ETXを除く)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時間:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時間:</td><td>6 byte</td></tr>
     * <tr><td>要求区分:</td><td>1 byte</td></tr>
     * <tr><td>System Recovery報告:</td><td>1 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of communication message ID22 (excluding STX,SEQ-NO,BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>request classification:</td><td>1 byte</td></tr>
     * <tr><td>System Recovery report:</td><td>1 byte</td></tr>
     * </table>
     * </p>
     </en>*/

    /**<jp>
     * ID22のバイト総数(STX,SEQ-NO,BCC,ETXを除く)
     </jp>*/
    /**<en>
     * Total byte numbers of ID22 (excluding STX,SEQ-NO,BCC and ETX)
     </en>*/
    protected static final int LEN_ID22 = 18;

    /**<jp>
     * 要求区分のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset in request classification.
     </en>*/
    protected static final int OFF_ID22_REQUEST_CLASS = 0;

    /**<jp>
     * 要求区分の長さ(byte)
     </jp>*/
    /**<en>
     * Length of request classification (in byte)
     </en>*/
    protected static final int LEN_ID22_REQUEST_CLASS = 1;

    /**<jp>
     * System Recovery報告のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset in System Recovery report.
     </en>*/
    protected static final int OFF_ID22_SYSTEMRECOVERYREPORT = OFF_ID22_REQUEST_CLASS + LEN_ID22_REQUEST_CLASS;

    /**<jp>
     * System Recovery報告の長さ(byte)
     </jp>*/
    /**<en>
     * Length of System Recovery report (in byte)
     </en>*/
    protected static final int LEN_ID22_SYSTEMRECOVERYREPORT = 1;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文バッファ
     </jp>*/
    /**<en>
     * Communication message buffer
     </en>*/
    protected byte[] _localBuffer = new byte[LEN_ID22];

    /**<jp>
     * 要求区分の時刻修正を表す
     </jp>*/
    /**<en>
     * Indicates correction of time in request classification
     </en>*/
    public static final int TIME_CORRECT = 0;

    /**<jp>
     * 要求区分の作業開始を表す
     </jp>*/
    /**<en>
     * Indicates work start in request classification
     </en>*/
    public static final int W_START = 1;

    /**<jp>
     * System Recovery実施を表すフィールド
     </jp>*/
    /**<en>
     * Field of System Recovery implementation
     </en>*/
    public static final String S_R_REPORT = "1";

    /**<jp>
     * System Recovery報告の無しを表すフィールド
     </jp>*/
    /**<en>
     * Field to indicate there is no System Recovery report
     </en>*/
    public static final String SYS_R_REPORT = "0";

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します
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
    public As21Id22()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文をコンストラクタに渡します。
     * @param as21Id22 <code>as21Id22</code>  日付・時刻要求(DateTimeRequest) ID=22 電文
     </jp>*/
    /**<en>
     * Passes the communication message received from AGC to the constructor.
     * @param as21Id22 :<code>as21Id22</code>  communication message of Date and Time request ID=22
     </en>*/
    public As21Id22(byte[] as21Id22)
    {
        super();
        setReceiveMessage(as21Id22);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 日付・時刻要求(DateTimeRequest)電文から要求区分を取得します。
     * @return     0:時刻修正<BR>
     *              1:作業開始
     * @throws InvalidProtocolException 要求区分が数字でない場合に報告されます。
     </jp>*/
    /**<en>
     * Acquires request classification from the communication message of Date and Time request.
     * @return     0: time correction<BR>
     *              1: work start
     * @throws InvalidProtocolException : Reports if numeric value was not provided for request classification.
     </en>*/
    public int getRequestClassification()
            throws InvalidProtocolException
    {
        int rclass;
        String requestClassification =
                getContent().substring(OFF_ID22_REQUEST_CLASS, OFF_ID22_REQUEST_CLASS + LEN_ID22_REQUEST_CLASS);
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
     * 日付・時刻要求(DateTimeRequest)電文からSystem Recovery報告を取得します。
     * @return     True:報告無し<BR>
     *              False:System Recovery実施(搬送データ削除)
     </jp>*/
    /**<en> 
     * Acquires System Recovery report from the communication message of Date and Time request.
     * @return     True:No Report<BR>
     *              False:System Recovery implemented (carry data deleted)
     </en>*/
    public boolean isSystemRecoveryReports()
    {
        String sReport =
                getContent().substring(OFF_ID22_SYSTEMRECOVERYREPORT,
                        OFF_ID22_SYSTEMRECOVERYREPORT + LEN_ID22_SYSTEMRECOVERYREPORT);
        return (sReport.equals(S_R_REPORT));
    }

    /**<jp>
     * 日付・時刻要求電文の内容を取得します。
     * @return 日付・時刻要求TEXTの内容
     </jp>*/
    /**<en>
     * Acquires the content of communication message of Date and Time request.
     * @return text content of Date and Time request
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
     * Sets the communication message from AGC to the internal buffer.
     * @param rmsg communication message received from AGC
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID22; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }
    // Private methods -----------------------------------------------

}
//end of class

