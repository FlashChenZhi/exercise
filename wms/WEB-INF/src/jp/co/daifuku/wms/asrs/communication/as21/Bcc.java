// $Id: Bcc.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

import jp.co.daifuku.wms.asrs.common.HexFormat;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでのBCCコードの作成および、BCCチェックを行なうクラスです。<BR>
 * このクラスは、送受信テキストの処理に使用します。
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
 * Creates BBC and checks according to AS21 communication protocol.
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
public class Bcc
        extends Object
{
    // Class fields --------------------------------------------------
    /**<jp>
     * AS21でのBCC値(文字列)を定義します。
     </jp>*/
    /**<en>
     * Defines the BBC value (string) according to AS21.
     </en>*/
    protected static final String BCC_STRING = "00";

    /**<jp>
     * AS21での電文中のBCC文字列長を定義します。
     </jp>*/
    /**<en>
     * Defines the length of BBC string in comunication message according to AS21.
     </en>*/
    protected static final int BCC_LENGTH = 2;

    /**<jp>
     * 渡される電文の先頭から末 BCC_LENGTH までをBCC計算します。
     </jp>*/
    /**<en>
     * BCC calcuates from teh beginning of the message to the end (BCC_LENGTH).
     </en>*/
    private static final int BCC_START = 0;

    /**<jp>
     * 電文長を定義します。
     </jp>*/
    /**<en>
     * Defines the length of communication message.
     </en>*/
    private static final int AS21_TEXT_LENGTH = 512;

    /**<jp>
     * STX長を定義します。
     </jp>*/
    /**<en>
     * Defines the length of STX.
     </en>*/
    private static final int AS21_STX_LENGTH = 1;

    /**<jp>
     * ETX長を定義します。
     </jp>*/
    /**<en>
     * Defines the length of ETX.
     </en>*/
    private static int AS21_ETX_LENGTH = 1;

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

    // Public methods ------------------------------------------------

    /**<jp>
     * Bcc値を２Byteの文字として作成します。(計算値中がa～fとなった場合には大文字へ変換します)
     * @param  b           送信電文(Byte列)
     * @param  makeLength  送信電文長
     * @return    Bccの文字列
     </jp>*/
    /**<en>
     * Creates Bcc value in form of 2 Byte character. ( lower case characters 
     * appeared during the calculation, e.g., a-f, will be converted to uper case
     * characters.)
     * @param  b : communication message to send (Byte array)
     * @param  makeLength : length of communication message to send
     * @return  : Bcc string
     </en>*/
    public String make(byte[] b, int makeLength)
    {
        //<jp> 最初の値をまず計算用にセット</jp>
        //<en> Set the initila value for calculation</en>
        int cBcc = new Byte(b[BCC_START]).intValue();
        //<jp> 次の配列値から順に計算</jp>
        //<en> calculate sequentially from the following array</en>
        for (int i = BCC_START + 1; i < makeLength; i++)
        {
            cBcc = cBcc ^ new Byte(b[i]).intValue();
        }

        HexFormat hformat = new HexFormat(BCC_STRING);
        String sBcc = hformat.format(cBcc).toUpperCase();
        return (sBcc);
    }

    /**<jp>
     * 受取った電文中のBcc値と受取ったテキスト内容から計算したBCC値とを比較して正しいか否かをCheckします。
     * @param     受取った電文(STX,ETX を外したByte列)
     * @param     受取った電文長
     * @return    Bccの可否
     </jp>*/
    /**<en>
     * Compares the Bcc value included in the received message and the other Bcc value calculated from the 
     * received text content; use them to check the correctness.
     * @param  wkb : received communication message (Byte array which excluded STX and ETX)
     * @param  rcvLength : length of received communication message
     * @return    Correctness of Bcc
     </en>*/
    public boolean check(byte[] wkb, int rcvLength)
    {
        String sBcc = null;
        //<jp> 受信電文中のBcc値を保管</jp>
        //<en> Store the Bcc value included in the received communication messages</en>
        byte[] bccByte = new byte[BCC_LENGTH];

        bccByte[0] = wkb[rcvLength - 2];
        bccByte[1] = wkb[rcvLength - 1];
        String rBcc = new String(bccByte);

        //<jp> Bcc作成のメソッドを呼ぶ</jp>
        //<en> Calls the method for the creation of Bcc.</en>
        sBcc = make(wkb, rcvLength - 2);

        if (rBcc.equals(sBcc))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class
