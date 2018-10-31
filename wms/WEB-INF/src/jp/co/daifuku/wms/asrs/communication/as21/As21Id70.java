// $Id: As21Id70.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「Message Data ID=70」電文を処理します。
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
 * Processes the communication message "Message Data" ID=70 according to AS21 communication protocol.
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
public class As21Id70
        extends ReceiveIdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 電文の長さ(byte)の初期設定
     </jp>*/
    /**<en>
     * Length of communication message in initial setting(byte)
     </en>*/
    public int LEN_ID70 = 0;

    /**<jp>
     * Message Dataのオフセットの定義
     </jp>*/
    /**<en>
     * Defines theoffset of Message Data
     </en>*/
    private static final int OFF_ID70_MESSAGE_DATA = 0;

    // Class variables -----------------------------------------------
    /**<jp>
     * 電文を保持するための変数。
     </jp>*/
    /**<en>
     * Variable for the reservation of communication messages
     </en>*/
    private byte[] _localBuffer;

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
     * AGCから受信した電文セットし、このクラスの初期化を行います。
     * @param as21Id70 <code>as21Id70</code>  Message Data電文
     </jp>*/
    /**<en>
     * Sets the communication message received from AGC; then implements the initialization
     * of thisclass.
     * @param as21Id70 :<code>as21Id70</code>  the communication message "Message Data"
     </en>*/
    public As21Id70(byte[] as21Id70)
    {
        super();
        setReceiveMessage(as21Id70);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * Message Data電文からMessageDataを取得します。内容はSystemごとに定義されます。
     * @return    Message Data
     </jp>*/
    /**<en>
     * Acquires the Message Data from the communication message "Message Data". Contents are
     * defined by each system.
     * @return    Message Data
     </en>*/
    public String getMessageData()
    {
        int messageLength = getContent().length();
        String messageData = getContent().substring(OFF_ID70_MESSAGE_DATA, messageLength);
        return (messageData);
    }

    /**<jp>
     * Message Data電文の内容を取得します。
     * @return  Message Data電文の内容
     </jp>*/
    /**<en>
     * Acquires the content of communication message "Message Data".
     * @return  the content of communication message "Message Data"
     </en>*/
    public String toString()
    {
        return (new String(_localBuffer));
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param rmsg Message Date電文
     </jp>*/
    /**<en>
     * Sets communication message received from AGC to the internal buffer.
     * @param rmsg communication message "Message Data"
     </en>*/
    protected void setReceiveMessage(byte[] rmsg)
    {
        //<jp> 受け取った電文の長さ</jp>
        //<en> Length of the communication message received </en>
        LEN_ID70 = rmsg.length;

        //<jp> 電文バッファにデータをセット</jp>
        //<en> Sets data to communication message buffer</en>
        _localBuffer = new byte[LEN_ID70];
        for (int i = 0; i < LEN_ID70; i++)
        {
            _localBuffer[i] = rmsg[i];
        }
        _dataBuffer = _localBuffer;
    }

    // Private methods -----------------------------------------------

}
//end of class

