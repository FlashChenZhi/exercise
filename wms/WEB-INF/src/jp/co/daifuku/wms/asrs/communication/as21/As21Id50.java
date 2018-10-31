// $Id: As21Id50.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**<jp>
 * AS21通信プロトコルでの「Message Data ID=50」電文を組み立てます。<BR>
 * <FONT SIZE="5" COLOR="RED">未実装となっています。注意して下さい。</FONT>
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
 * Composes communication message "Message Data " ID=50 according to AS21 communication protocol.<BR>
 * <FONT SIZE="5" COLOR="RED"> NOT implemented; please be careful.</FONT>
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
public class As21Id50
        extends SendIdMessage
{
    // Class fields --------------------------------------------------

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
     * Message Data電文を作成します。
     * @param messageData <code>messageData</code> System毎に定義されたデータをセットする。
     * @return    Message Data電文
     </jp>*/
    /**<en>
     * Creates the communication message Message Data.
     * @param messageData :<code>messageData</code> Sets data defined by each System.
     * @return    the communication message "Message Data"
     </en>*/
    public String getAs21Id50(String messageData)
    {
        String as21Id50 = "";
        return (as21Id50);
    }

    /**<jp>
     * 文字列を返します。
     * @return "not yet attendes"
     </jp>*/
    /**<en>
     * Returns strings.
     * @return "not yet attendes"
     </en>*/
    public String getSendMessage()
    {
        String value = "not yet attendes";
        return value;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

