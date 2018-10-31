// $Id: SendIdMessage.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.ReadWriteException;

/**<jp>
 * AS21通信プロトコルでの送信電文共通部分を組み立て・分解するためのスーパークラスです。<BR>
 * 実際の送信電文組み立てと分解は、このクラスを継承して各IDごとのサブクラスを用意してください。
 *  
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class provides the utility method which can be used to compose the common part of sending messages 
 * according to AS21 communication protocol. <BR>
 * Please use the subclasses of each ID for actual composition of the messages.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public abstract class SendIdMessage
        extends IdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * BCデータ（空白）
     </jp>*/
    /**<en>
     * BC data (blank)
     </en>*/
    protected static final String BLANK_BCDATA = "                              ";

    /**<jp>
     * 制御情報（空白）
     </jp>*/
    /**<en>
     * Control data (blank)
     </en>*/
    protected static final String BLANK_CONTROL_INFO = "                              ";

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
     * インスタンスを生成します。内部バッファは、 ' ' でクリアされます。
     </jp>*/
    /**<en>
     * Returns the version of this class.
     </en>*/
    public SendIdMessage()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 送信するための電文を取得します。詳細は、各サブクラスで実装する必要があります。
     * @return    構築された電文
     * @throws InvalidProtocolException  プロトコル規定に違反した情報がある場合に通知されます。
     * @throws ReadWriteException データベースに対する処理で発生した場合に通知します。
     </jp>*/
    /**<en>
     * Get the communication message to send. The detail needs to be implemented under each subclass.
     * @return    Communication message composed
     * @throws  InvalidProtocolException : Notifies if there is any data violating the protocol regulations.
     * @throws ReadWriteException :Notifies if exception occured when processing for database.
     </en>*/
    public abstract String getSendMessage()
            throws InvalidProtocolException,
                ReadWriteException;

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * メッセージを指定の長さに整形します。
     * オリジナルが指定より長い場合は切り捨てられ、短い場合は必要数分スペースが埋められます。
     * @param   src   オリジナル・メッセージ
     * @param   len   文字列長さ指定
     * @return  整形後の文字列
     </jp>*/
    /**<en>
     * Adjust the message length to the size specified.
     * Cut off the original message if its length is over sthe specified size. If shorter, use space to fill
     * to the required length.
     * @param   src   oroginal message
     * @param   len   Specifying the length of the string
     * @return  String after adjustment
     </en>*/
    protected String operateMessage(String src, int len)
    {
        int dif = len - src.length();
        if (dif <= 0)
        {
            //<jp> オリジナルメッセージが長い</jp>
            //<en> src too long</en>
            return (src.substring(0, len));
        }
        else
        {
            //<jp> オリジナルメッセージが短い</jp>
            //<en> src too short</en>
            byte[] wb = new byte[dif];
            for (int i = 0; i < dif; i++)
            {
                wb[i] = ' ';
            }
            return (src + new String(wb));
        }
    }

    /**<jp>
     * byte配列のバッファの off で表されるオフセットに対して、srcの内容をセットします。
     * @param  buff セットされるバッファ
     * @param  off  セットされるバッファのオフセット
     * @param  src  設定する情報
     * @param  len  設定する情報の長さ
     </jp>*/
    /**<en>
     * Set the src contents to the offset which is indicated by 'off' in the byte array of buffer
     * @param  buff : buffer to be set
     * @param  off  : offset the buffer to be set
     * @param  src  : data to set
     * @param  len  : length of the data to set
     </en>*/
    protected void setByteArray(byte[] buff, int off, byte[] src, int len)
    {
        for (int i = 0; i < len; i++)
        {
            buff[off + i] = src[i];
        }
    }

    // Private methods -----------------------------------------------
}
//end of class

