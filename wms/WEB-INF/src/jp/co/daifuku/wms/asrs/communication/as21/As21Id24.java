// $Id: As21Id24.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「搬送DateCancel応答(TransportDataCancelResponse) ID=24」電文を処理します。
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
 * Processes the communication message "Response to carry data cancel, ID=24" according to AS21 communication protocol.
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
public class As21Id24
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ID24の電文長(STX,SEQ-No,BCC,ETXを除く)。
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID区分:</td><td>2 byte</td></tr>
     * <tr><td>MC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>AGC送信時刻:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>Cancel結果:</td><td>2 byte</td></tr>
     * </table>
     * </p>
     </jp>*/
    /**<en>
     * Length of communication message ID24 (excluding STX, SEQ-No, BCC and ETX).
     * <p>
     * <table>
     * <tr><td>ID:</td><td>2 byte</td></tr>
     * <tr><td>ID segment:</td><td>2 byte</td></tr>
     * <tr><td>MC sending time:</td><td>6 byte</td></tr>
     * <tr><td>AGC sending time:</td><td>6 byte</td></tr>
     * <tr><td>MC Key:</td><td>8 byte</td></tr>
     * <tr><td>Cancel result:</td><td>2 byte</td></tr>
     * </table>
     * </p>
     </en>*/
    public static final int LEN_ID24 = 26;

    /**<jp>
     * Cancel結果を表すフィールド（正常完了）
     </jp>*/
    /**<en>
     * Field to indicate the result of Cancel (normal end)
     </en>*/
    public static final String RESULT_COMP_NORMAL = "00";

    /**<jp>
     * Cancel結果を表すフィールド（該当Dataはすでに搬送開始済みのため、Cancel不可）
     </jp>*/
    /**<en>
     * Field to indicate the result of Cancel ( NOT able to cancel,
     * as corresponding data has already started being carried.)
     </en>*/
    public static final String RESULT_NOT_CANCEL = "01";

    /**<jp>
     * Cancel結果を表すフィールド（該当搬送Data無し）
     </jp>*/
    /**<en>
     * Field to indicate the result of Cancel ( no corresponding data found)
     </en>*/
    public static final String RESULT_NOT_DATA = "02";

    /**<jp>
     * MC Keyのオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of MC Key.
     </en>*/
    private static final int OFF_ID24_MCKEY = 0;

    /**<jp>
     * MC Keyの長さ(Byte)
     </jp>*/
    /**<en>
     * Length of MC Key (in byte)
     </en>*/
    private static final int LEN_ID24_MCKEY = 8;

    /**<jp>
     * Cancel結果のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset of Cancel results
     </en>*/
    private static final int OFF_ID24_RESULT = OFF_ID24_MCKEY + LEN_ID24_MCKEY;

    /**<jp>
     * Cancel結果の長さ(Byte)
     </jp>*/
    /**<en>
     * Length of Cancel result (in byte)
     </en>*/
    private static final int LEN_ID24_RESULT = 2;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文バッファ
     </jp>*/
    /**<en>
     * Communication message buffer
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID24];

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
    public As21Id24()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文をコンストラクタに渡します。
     * @param as21Id24 <code>as21Id24</code>  搬送DateCancel応答(TransportDataCancelResponse) ID=24 電文
     </jp>*/
    /**<en>
     * Passes communication message received from AGC to the constructor.
     * @param as21Id24 :<code>as21Id24</code>  communication message "Response to carry data cancel"ID=24
     </en>*/
    public As21Id24(byte[] as21Id24)
    {
        super();
        setReceiveMessage(as21Id24);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 搬送DateCancel応答(TransportDataCancelResponse)電文からMC Keyを取得します。
     * @return    MC Key
     </jp>*/
    /**<en>
     * Acquires MC Key from the communication message "Response to carry data cancel".
     * @return    MC Key
     </en>*/
    public String getMcKey()
    {
        String mcKey = getContent().substring(OFF_ID24_MCKEY, OFF_ID24_MCKEY + LEN_ID24_MCKEY);
        return (mcKey);
    }

    /**<jp>
     * 搬送DateCancel応答(TransportDataCancelResponse)電文からCancel結果を取得します。
     * @return    00:正常完了<BR>
     *            01:該当Dataはすでに搬送開始済みのため、Cancel不可<BR>
     *            02:該当搬送Data無し
     </jp>*/
    /**<en>
     * Acquires the result of cancel from the communication message "Response to carry data cancel"
     * @return    00:normal end<BR>
     *            01:NOT able to cancel; corresponding data has already started being carried.<BR>
     *            02:No corresponding data is found.
     </en>*/
    public String getCancellationResults()
    {
        String cancellationResults = getContent().substring(OFF_ID24_RESULT, OFF_ID24_RESULT + LEN_ID24_RESULT);
        return (cancellationResults);
    }

    /**<jp>
     * 搬送DateCancel応答電文の文字列表現を返します。
     * @return 搬送DateCancel応答電文の文字列表現
     </jp>*/
    /**<en>
     * Returns string representation of the communication message "Response to carry data cancel"
     * @return string representation of the communication message "Response to carry data cancel"
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
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg communication message received from AGC 
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID24; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

