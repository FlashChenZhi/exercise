// $Id: As21Id21.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

import jp.co.daifuku.wms.base.common.DsNumberDefine;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「作業開始応答(WorkStartResponse) ID=21」電文を処理します。
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
 * Composes communication message "Response to work start, ID=21" according to AS21 communication protocol.
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
public class As21Id21
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID21の電文長（STX，SEQ-NO，BCC，ETXを除く）
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時間:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時間:</td><td>6 byte</td></tr>
     * <tr><td>応答区分:</td><td>2 byte</td></tr>
     * <tr><td>Error詳細:</td><td>2 byte</td</tr>
     * <tr><td>System Recovery報告:</td><td>1 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of communication message ID21 (except STX, SEQ-NO, BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>Response classification:</td><td>2 byte</td></tr>
     * <tr><td>Error detail:</td><td>2 byte</td</tr>
     * <tr><td>System Recovery report:</td><td>1 byte</td></tr>
     * </table>
     * </p>
     </en>*/

    /**<jp>
     * ID21のバイト総数（STX，SEQ-NO，BCC，ETXを除く）
     </jp>*/
    /**<en>
     * Total byte numbers of ID21 (excluding STX, SEQ-NO, BCC and ETX)
     </en>*/
    protected static final int LEN_ID21 = 21;

    /**<jp>
     * 応答区分のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset as a response classification.
     </en>*/
    protected static final int OFF_ID21_RESPONSE_CLASS = 0;

    /**<jp>
     * 応答区分の長さ(byte)
     </jp>*/
    /**<en>
     * Length of response classification (in byte)
     </en>*/
    protected static final int LEN_ID21_RESPONSE_CLASS = 2;

    /**<jp>
     * Error詳細のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset in error detail.
     </en>*/
    protected static final int OFF_ID21_ERRDETAILS = OFF_ID21_RESPONSE_CLASS + LEN_ID21_RESPONSE_CLASS;

    /**<jp>
     * Error詳細の長さ(byte)
     </jp>*/
    /**<en>
     * Length of Error detail (in byte)
     </en>*/
    protected static final int LEN_ID21_ERRDETAILS = 2;

    /**<jp> 
     * System Recovery報告オフセットの定義
     </jp>*/
    /**<en> 
     * Defines offset in System Recovery report.
     </en>*/
    protected static final int OFF_ID21_SYSTEMRECOVERYREPORT = OFF_ID21_ERRDETAILS + LEN_ID21_ERRDETAILS;

    /**<jp>
     * System Recovery報告の長さ(byte)
     </jp>*/
    /**<en>
     * Length of System Recovery report (in byte)
     </en>*/
    protected static final int LEN_ID21_SYSTEMRECOVERYREPORT = 1;

    /**<jp>
     * System Recovery実施を表すフィールド
     </jp>*/
    /**<en>
     * Field of System Recovery implementation
     </en>*/
    public static final String SYSTEM_RRCOVERY = "1";

    /**<jp>
     * 応答区分の正常を表す
     </jp>*/
    /**<en>
     * Field of normal response classifications.
     </en>*/
    public static final String NORMAL = "00";

    /**<jp>
     * 応答区分のAGC状態異常を表す
     </jp>*/
    /**<en>
     * Field for error in AGC status of response classification
     </en>*/
    public static final String AGC_CONDITION_ERR = "03";

    /**<jp>
     * 応答区分のData errorを表す
     </jp>*/
    /**<en>
     * Field to indicate data error in response classification
     </en>*/
    public static final String DATA_ERR = "99";

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文バッファ
     </jp>*/
    /**<en>
     * Communication message buffer
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID21];

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
    public As21Id21()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文をコンストラクタに渡します。
     * @param as21Id21 <code>as21Id21</code>  作業開始応答(WorkStartResponse) ID=21 電文
     </jp>*/
    /**<en>
     * Passes the communication message received from AGC to the constructor.
     * @param as21Id21 :<code>as21Id21</code>  communication message for Response to work start ID=21
     </en>*/
    public As21Id21(byte[] as21Id21)
    {
        super();
        setReceiveMessage(as21Id21);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 作業開始応答(WorkStartResponse)電文から応答区分を取得します。
     * @return     00:正常<BR>
     *              03:AGC異常<BR>
     *              99:データエラー
     </jp>*/
    /**<en>
     * Acquires response classification from communication message "Response to work start".
     * @return     00:Normal<BR>
     *              03:AGC abnormal<BR>
     *              99:data error
     </en>*/
    public String getResponseClassification()
    {
        String responseClassification = DATA_ERR;
        responseClassification =
                getContent().substring(OFF_ID21_RESPONSE_CLASS, OFF_ID21_RESPONSE_CLASS + LEN_ID21_RESPONSE_CLASS);
        return (responseClassification);
    }

    /**<jp>
     * 作業開始応答(WorkStartResponse)電文からError詳細を取得します。
     * @return    状態異常のAGC No.
     </jp>*/
    /**<en>
     * Acquires error detail from the communication message "Response to work start"
     * @return    AGC# of abnormal state
     </en>*/
    public String getErrorDetails()
    {
        String errorDetails = "00";
        if (getResponseClassification().equals(AGC_CONDITION_ERR))
        {
            errorDetails = getContent().substring(OFF_ID21_ERRDETAILS, OFF_ID21_ERRDETAILS + LEN_ID21_ERRDETAILS);
        }
        return (errorDetails);
    }

    /**<jp>
     * 作業開始応答(WorkStartResponse)電文からSystem Recovery報告を取得します。
     * @return     System Recovery報告<BR>
     </jp>*/
    public String getSystemRecoveryReport()
    {
        String systemRecoveryReport =
                getContent().substring(OFF_ID21_SYSTEMRECOVERYREPORT,
                        OFF_ID21_SYSTEMRECOVERYREPORT + LEN_ID21_SYSTEMRECOVERYREPORT);
        return (systemRecoveryReport);
    }

    /**<jp>
     * 作業開始応答(WorkStartResponse)電文からSystem Recovery報告を取得します。
     * @return     True:報告無し<BR>
     *              False:System Recovery実施(搬送データ消去)
     </jp>*/
    /**<en>
     * Acquires System Recovery report from the communication message "Response to work start".
     * @return     True:No report<BR>
     *              False:System Recovery iimplementation (carry data deleted)
     </en>*/
    public boolean isSystemRecoveryReport()
    {
        String systemRecoveryReport =
                getContent().substring(OFF_ID21_SYSTEMRECOVERYREPORT,
                        OFF_ID21_SYSTEMRECOVERYREPORT + LEN_ID21_SYSTEMRECOVERYREPORT);
        return (systemRecoveryReport.equals(SYSTEM_RRCOVERY));
    }

    /**<jp>
     * 作業開始応答電文の内容を取得します
     * @return  作業開始応答TEXTの内容
     </jp>*/
    /**<en>
     * Acquires the content of communication message "Response to work start".
     * @return  Content of text : communication message "Response to work start"
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    /**
     * オペレーションロギング用DS番号を返却します。
     * @return    DS_NUMBER
     */
    public String getDsNumber()
    {
        return (DsNumberDefine.DS_AGC_ID21);
    }

    /**
     * オペレーションロギング用リソース番号を返却します。
     * @return    DS_NUMBER
     */
    public String getResourceKey()
    {
        return (DsNumberDefine.PAGERESOUCE_AGC_ID21);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg AGCから受け取った電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC to the internal buffer.
     * @param rmsg communication message received from AGC
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID21; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

