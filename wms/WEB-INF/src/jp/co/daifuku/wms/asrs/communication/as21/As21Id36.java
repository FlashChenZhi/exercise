// $Id: As21Id36.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「一斉起動不可報告(TheReportOfTheNotAcceptedSystemStartUp) ID=36」電文を処理します。
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
 * Processes communication message "TheReportOfTheNotAcceptedSystemStartUp" (report that all system start up was not accepted),
 * ID=36, according to AS21 communication protocol.
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
public class As21Id36
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 一斉起動不可報告電文の長さ(byte)
     </jp>*/
    /**<en>
     * Length of the communication message for ReportOfTheNotAcceptedSystemStartUp
     * in byte
     </en>*/
    public static final int LEN_ID36 = 18;

    /**<jp>
     * 起動不可理由のオフセットの定義
     </jp>*/
    /**<en>
     * Defines offset for the reason for not being able to sart up
     </en>*/
    private static final int OFF_ID36_NSTRES_CLASS = 0;

    /**<jp>
     * 起動不可理由の長さ(byte)
     </jp>*/
    /**<en>
     * Length of the reason for not being able to start up (in byte)
     </en>*/
    private static final int LEN_ID36_NSTRES_CLASS = 2;

    /**<jp>
     * 起動不可理由（作業開始状態でない）
     </jp>*/
    /**<en>
     * the reason for not being able to start up (not in condition to start working)
     </en>*/
    public static final String NOT_START_CONDITION = "01";

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variable which preserves the communication message
     </en>*/
    private byte[] _localBuffer = new byte[LEN_ID36];

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
    public As21Id36()
    {
        super();
    }

    /**<jp>
     * AGCから受信した電文セットし、このクラスの初期化を行います。
     * @param as21Id36 <code>as21Id36</code>  一斉起動不可報告電文
     </jp>*/
    /**<en>
     * Sets the communication message from AGC, then initialize this class.
     * @param as21Id36 :<code>as21Id36</code>  communication message for ReportOfTheNotAcceptedSystemStartUp
     </en>*/
    public As21Id36(byte[] as21Id36)
    {
        super();
        setReceiveMessage(as21Id36);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 一斉起動不可報告電文から起動不可理由を取得します。
     * @return    起動不可理由
     </jp>*/
    /**<en>
     * Acquires the reason for not being able to start up from the message for 
     * ReportOfTheNotAcceptedSystemStartUp.
     * @return    reason for not being able to start up
     </en>*/
    public String getReason()
    {
        String reason = getContent().substring(OFF_ID36_NSTRES_CLASS, LEN_ID36_NSTRES_CLASS);
        return (reason);
    }

    /**<jp>
     * 一斉起動不可報告電文の内容を取得します。
     * @return  一斉起動不可報告電文の内容
     </jp>*/
    /**<en>
     * Acquires the contents of message for ReportOfTheNotAcceptedSystemStartUp.
     * @return  contents of message for ReportOfTheNotAcceptedSystemStartUp
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg 一斉起動不可報告電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg message for ReportOfTheNotAcceptedSystemStartUp
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        for (int i = 0; i < LEN_ID36; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

