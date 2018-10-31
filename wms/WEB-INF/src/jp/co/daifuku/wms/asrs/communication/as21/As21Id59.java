// $Id: As21Id59.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「System Recovery終了要求(SystemRecoveryCompletionRequest) ID=59」電文を組み立てます。<BR>
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
 * Composes communication message "SystemRecoveryCompletionRequest" ID=59, request for the completion of 
 * system recovery, according to AS21 communication protocol.<BR>
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
public class As21Id59
        extends SendIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * System Recovery終了要求の電文長
     </jp>*/
    /**<en>
     * Length of the communication message "SystemRecoveryCompletionRequest"
     </en>*/
    protected static final int LEN_TOTAL = OFF_CONTENT;

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
     * デフォルトコンストラクタ
     </jp>*/
    /**<en>
     * Default constructor
     </en>*/
    public As21Id59()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * System Recovery終了要求電文を作成します。
     * @return    System Recovery終了要求電文
     </jp>*/
    /**<en>
     * Creates the communication message "SystemRecoveryCompletionRequest".
     * @return    communication message "SystemRecoveryCompletionRequest"
     </en>*/
    public String getSendMessage()
    {
        //<jp> テキストバッファ</jp>
        //<en> text buffer</en>
        StringBuffer mbuf = new StringBuffer(IdMessage.LEN_MAX_CONTENT);

        // -------------------------------------------------
        //<jp>  送信メッセージバッファに設定</jp>
        //<en>  Setting for the sending message buffer</en>
        // -------------------------------------------------
        // ID
        setID("59");
        //<jp> ID 区分</jp>
        //<en> ID segment</en>
        setIDClass("00");
        //<jp> MC送信時刻</jp>
        //<en> MC sending time</en>
        setSendDate();
        //<jp> AGC送信時刻</jp>
        //<en> AGC sending time</en>
        setAGCSendDate("000000");
        //<jp> テキスト内容</jp>
        //<en> text contents</en>
        setContent(String.valueOf(mbuf));

        return (getFromBuffer(0, LEN_TOTAL));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
// end of class
