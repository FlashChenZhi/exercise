// $Id: As21Id78.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「SystemRecovery開始応答(SystemRecoveryStartResponse) ID=78」電文を処理します。
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
 * Processes the communication message "SystemRecoveryStartResponse" ID=78, the response to the 
 * start of system recovery, according to AS21 communication protocol.
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
public class As21Id78
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID78の電文長(STX,SEQ-No,BCC,ETXを除く)。
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>応答区分:</td><td>2 byte</td></tr>
     * <tr><td>Error詳細:</td><td>2 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of communication message ID78 (excluding STX, SEQ-No, BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>Reponse classification:</td><td>2 byte</td></tr>
     * <tr><td>Error detail:</td><td>2 byte</td></tr>
     * </table>
     * </p>
     </en>*/

    /**<jp>
     * ID78の電文長(STX,SEQ-No,BCC,ETXを除く)。
     </jp>*/
    /**<en>
     * Length of communication message ID78 (excluding STX, SEQ-No, BCC and ETX)
     </en>*/
    public static final int LEN_ID78 = 20;

    /**<jp>
     * ID78の応答区分のオフセット定義
     </jp>*/
    /**<en>
     * Defines the offset of response classificaiton ID78
     </en>*/
    private static final int OFF_ID78_CLASS = 0;

    /**<jp>
     * ID78の応答区分の長さ(byte)
     </jp>*/
    /**<en>
     * Length of response classification ID78 (byte)
     </en>*/
    private static final int LEN_ID78_CLASS = 2;

    /**<jp>
     * ID78のError詳細のオフセット定義
     </jp>*/
    /**<en>
     * Defines the offset error detail of ID78
     </en>*/
    private static final int OFF_ID78_ERROR = OFF_ID78_CLASS + LEN_ID78_CLASS;

    /**<jp>
     * ID78のError詳細の長さ(byte)
     </jp>*/
    /**<en>
     * Length of error detail of ID78 (byte)
     </en>*/
    private static final int LEN_ID78_ERROR = 2;

    /**<jp>
     * 応答区分の"正常"を表す。
     </jp>*/
    /**<en>
     * "normal status" of response classification
     </en>*/
    public static final String NORMAL = "00";

    /**<jp>
     * 応答区分の"AGC状態異常"を表す。
     </jp>*/
    /**<en>
     * "AGC error" of response classification
     </en>*/
    public static final String AGC_ERROR = "03";

    /**<jp>
     * 応答区分の"Data Error"を表す。
     </jp>*/
    /**<en>
     * "Data Error" of response classification
     </en>*/
    public static final String DATA_ERROR = "99";

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variables for the preservation of communication messages
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID78];

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョン日付
     </jp>*/
    /**<en>
     * Returns the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Data: 2001/05/11 02:33:53 $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public As21Id78()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文セットし、このクラスの初期化を行います。
     * @param as21Id78 <code>as21Id78</code>  SystemRecovery開始応答電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC; then implements the initialization
     * of this class.
     * @param as21Id78 :<code>as21Id78</code>  communication message "SystemRecoveryStartResponse"
     </en>*/
    public As21Id78(byte[] as21Id78)
    {
        super();
        setReceiveMessage(as21Id78);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * SystemRecovery開始応答電文から応答区分を取得します。
     * @return     SystemRecovery開始応答電文の応答区分<BR>
     *              00:正常<BR>
     *              03:AGC状態異常<BR>
     *              99:Data Error
     </jp>*/
    /**<en>
     * Acquires the response classification from the communication message "SystemRecoveryStartResponse".
     * @return     response classification of the communication message "SystemRecoveryStartResponse"
     *              00:Normal<BR>
     *              03:AGC status error<BR>
     *              99:Data Error
     </en>*/
    public String getResponseClassification()
    {
        String responseClassification = "";
        responseClassification = getContent().substring(OFF_ID78_CLASS, OFF_ID78_CLASS + LEN_ID78_CLASS);
        return (responseClassification);
    }

    /**<jp>
     * SystemRecovery開始応答電文からError詳細を取得します。応答区分が03（AGC状態異常）のときのみ有効です。
     * @return    状態異常（作業開始できない）のAGC No. 
     </jp>*/
    /**<en>
     * Acquires the detail of error from the communication message "SystemRecoveryStartResponse". It is valid
     * only if 03 (AGC status error) is assigned to the response classification.
     * @return    AGC No. which has the status error (NOT able to start WORK) 
     </en>*/
    public String getErrorDetails()
    {
        String errorDetail = "00";
        if (getResponseClassification().equals(AGC_ERROR))
        {
            errorDetail = getContent().substring(OFF_ID78_ERROR, OFF_ID78_ERROR + LEN_ID78_ERROR);
        }
        return (errorDetail);

    }

    /**<jp>
     * System Recovery開始応答電文の内容を取得します。
     * @return  System Recovery開始応答TEXTの内容
     </jp>*/
    /**<en>
     * Acquires content of communication message "SystemRecoveryStartResponse".
     * @return  content of the text of "SystemRecoveryStartResponse"
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg SystemRecovery開始応答電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg the communication message "SystemRecoveryStartResponse"
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID78; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

