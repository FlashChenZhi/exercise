// $Id: ReceiveIdMessage.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの受信電文共通部分を組み立て・分解するためのスーパークラスです。<BR>
 * 実際の受信電文組み立てと分解は、このクラスを継承して各IDごとのサブクラスを用意してください。
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
 * This is the superclass which retrieves the common parts of each communication message
 * according to AS21 communicaiton protocol.
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
public abstract class ReceiveIdMessage
        extends IdMessage
{
    // Class fields --------------------------------------------------
    /**<jp>
     * 無効なMCKEYを識別するための定義です。
     </jp>*/
    /**<en>
     * This is the definition for identifying the invalid MCkeys.
     </en>*/
    protected static final String NULL_MC_KEY = "00000000";

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
     * Create instance. Internal buffer can be cleared by ' '.
     </en>*/
    public ReceiveIdMessage()
    {
        super();
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * AGCから受け取った電文を内部バッファにセットします。
     * @param  rmsg   電文
     </jp>*/
    /**<en>
     * Set the communication message received from AGC to the internal buffer
     * @param  rmsg   communication message
     </en>*/
    protected abstract void setReceiveMessage(byte[] rmsg);

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

