// $Id: As21Id23.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

import jp.co.daifuku.wms.base.common.DsNumberDefine;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「作業終了応答(WorkCompletionResponse) ID=23」電文を処理します。
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
 * Processes communication message "Response to Work completion, ID=23" according to AS21 communication protocol.
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
public class As21Id23
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 応答区分の正常終了を表す
     </jp>*/
    /**<en>
     * Indicates 'normal end' of response classification.
     </en>*/
    public static final String NORMAL_END = "00";

    /**<jp>
     * 応答区分の終了不可を表す
     </jp>*/
    /**<en>
     * Indicates 'NOT able to end' of response classification.
     </en>*/
    public static final String END_IMPOSS = "01";

    /**<jp>
     * 応答区分のData Errorを表す
     </jp>*/
    /**<en>
     * Indicates 'data error' of response classification.
     </en>*/
    public static final String DATA_ERR = "99";

    /**<jp>
     * ID23の電文長(STX, SEQ-NO, BCC, ETXを除く)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時間:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時間:</td><td>6 byte</td></tr>
     * <tr><td>応答区分:</td><td>2 byte</td></tr>
     * <tr><td>機種CODE:</td><td>2 byte</td></tr>
     * <tr><td>号機NO.:</td><td>4 byte</tr></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of communication message ID23 (excluding STX, SEQ-NO, BCC and ETX)
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>response classification:</td><td>2 byte</td></tr>
     * <tr><td>model CODE:</td><td>2 byte</td></tr>
     * <tr><td>machine code:</td><td>4 byte</tr></tr>
     * </table>
     * </p>
     </en>*/
    private static final int LEN_ID23 = 24;

    /**<jp>
     * 応答区分のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset in response classification.
     </en>*/
    private static final int OFF_ID23_RESPONSE_CLASS = 0;

    /**<jp>
     * 応答区分の長さ(byte)
     </jp>*/
    /**<en>
     * Length of response classification (in byte)
     </en>*/
    private static final int LEN_ID23_RESPONSE_CLASS = 2;

    /**<jp>
     * 機種Codeのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of model code.
     </en>*/
    private static final int OFF_ID23_MODEL_CODE = OFF_ID23_RESPONSE_CLASS + LEN_ID23_RESPONSE_CLASS;

    /**<jp>
     * 機種Codeの長さ(byte)
     </jp>*/
    /**<en>
     * Length of model code (in byte)
     </en>*/
    private static final int LEN_ID23_MODEL_CODE = 2;

    /**<jp>
     * 号機NO.のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of machine code#.
     </en>*/
    private static final int OFF_ID23_MACHINE_CODE = OFF_ID23_MODEL_CODE + LEN_ID23_MODEL_CODE;

    /**<jp>
     * 号機NO.の長さ(byte)
     </jp>*/
    /**<en>
     * Length of machine code# in byte
     </en>*/
    private static final int LEN_ID23_MACHINE_CODE = 4;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文バッファ
     </jp>*/
    /**<en>
     * Communication message buffer
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID23];

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
    public As21Id23()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文をコンストラクタに渡します。
     * @param as21Id23 <code>as21Id23</code>  作業終了応答(WorkCompletionResponse) ID=23 電文
     </jp>*/
    /**<en>
     * Passes communication message received from AGC to the constructor.
     * @param as21Id23 :<code>as21Id23</code>  communication message of Work Completion Response, ID=23
     </en>*/
    public As21Id23(byte[] as21Id23)
    {
        super();
        setReceiveMessage(as21Id23);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 作業終了応答(WorkCompletionResponse)電文から応答区分を取得します。
     * @return    00:正常終了  01:終了不可  99:データエラー
     </jp>*/
    /**<en>
     * Acquires response classification from communication message of Work Completion Response.
     * @return    00:normal end  01:NOT able to end  99: error in data
     </en>*/
    public String getResponseClassification()
    {
        String responseClassification = DATA_ERR;
        responseClassification =
                getContent().substring(OFF_ID23_RESPONSE_CLASS, OFF_ID23_RESPONSE_CLASS + LEN_ID23_RESPONSE_CLASS);
        return (responseClassification);
    }

    /**<jp>
     * 作業終了応答(WorkCompletionResponse)電文から機種Codeを取得します。
     * @return    機種コード
     </jp>*/
    /**<en>
     * Acquires model code from the communication message Work Completion Response.
     * @return    model code
     </en>*/
    public String getModelCode()
    {
        String modelCode = getContent().substring(OFF_ID23_MODEL_CODE, OFF_ID23_MODEL_CODE + LEN_ID23_MODEL_CODE);

        return (modelCode);
    }

    /**<jp>
     * 作業終了応答(WorkCompletionResponse)電文から号機No.を取得します。
     * @return    号機No.
     </jp>*/
    /**<en>
     * Acquires machine code # from the communication message Work Completion Response.
     * @return    machine code #
     </en>*/
    public String getMachineNo()
    {

        String machineNo = getContent().substring(OFF_ID23_MACHINE_CODE, OFF_ID23_MACHINE_CODE + LEN_ID23_MACHINE_CODE);
        return (machineNo);
    }

    /**<jp>
     * 作業終了応答電文の内容を取得します。
     * @return 作業終了応答TEXTの内容
     </jp>*/
    /**<en>
     * Acquires content of communication message Work Completion Response.
     * @return Text content of Work Completion Response.
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
        return (DsNumberDefine.DS_AGC_ID23);
    }

    /**
     * オペレーションロギング用リソース番号を返却します。
     * @return    DS_NUMBER
     */
    public String getResourceKey()
    {
        return (DsNumberDefine.PAGERESOUCE_AGC_ID23);
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg AGCから受け取った電文
     </jp>*/
    /**<en>
     * Sets communication message reveived fro AGC to internal buffer.
     * @param rmsg communication message reveived fro AGC
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID23; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class
