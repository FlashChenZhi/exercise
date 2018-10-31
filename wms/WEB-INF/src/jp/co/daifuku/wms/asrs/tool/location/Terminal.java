// $Id: Terminal.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.location ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

/**<jp>
 * 端末を管理するためのクラスです。
 * 端末No.は、端末ごとに割り当てられますw・
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class controls the terminals.
 * The terminal numbers are assigned to each terminal.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Terminal extends ToolEntity
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * 端末No.
     </jp>*/
    /**<en>
     * terminal no.
     </en>*/
    protected String wTerminalNumber ;
    
    /**<jp>
     * 端末名
     </jp>*/
    /**<en>
     * name of the terminal
     </en>*/
    protected String wTerminalName ;

    /**<jp>
     * IPアドレス
     </jp>*/
    /**<en>
     * IP address
     </en>*/
    protected String wIPAddress ;

    /**<jp>
     * プリンタ名
     </jp>*/
    /**<en>
     * printer (name)
     </en>*/
    protected String wPrinterName ;

    /**<jp>
     * ロールID
     </jp>*/
    /**<en>
     * role ID
     </en>*/
    protected String wRoleId ;

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
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<CODE>Terminal</CODE>を構築します。
     </jp>*/
    /**<en>
     * Construct the new <CODE>Terminal</CODE>.
     </en>*/
    public Terminal()
    {
    }
    
    /**<jp>
     * 新しい<CODE>Terminal</CODE>を構築します。
     * @param terminalno     端末No
     * @param terminalname   端末名
     * @param ip             IPアドレス
     * @param printer        プリンタ名
     * @param roleid         ロールID
     </jp>*/
    /**<en>
     * Construct the new <CODE>Terminal</CODE>.
     * @param terminalno     :terminal no.
     * @param terminalname   :name of the terminal
     * @param ip             :IP address
     * @param terminalname   :printer name
     * @param terminalname   :role ID
     </en>*/
    public Terminal(String         terminalno,
                    String        terminalname,
                    String         ip,
                    String         printer,
                    String      roleid
                )
    {
        //<jp> インスタンス変数にセット</jp>
        //<en> Set as an instance variable.</en>
        setTerminalNumber(terminalno);
        setTerminalName(terminalname);
        setIPAddress(ip);
        setPrinterName(printer);
        setRoleId(roleid);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 端末No.を設定します。
     * @param terminalnum 端末No.
     </jp>*/
    /**<en>
     * Set the terminal no.
     * @param terminalnum :terminal no.
     </en>*/
    public void setTerminalNumber(String terminalnum)
    {
        wTerminalNumber = terminalnum;
    }

    /**<jp>
     * 端末No.を取得します。
     * @return 端末No.
     </jp>*/
    /**<en>
     * Retrieve the terminal no.
     * @return :terminal no.
     </en>*/
    public String getTerminalNumber()
    {
        return wTerminalNumber;
    }

    /**<jp>
     * 端末名を取得します。
     * @return 端末名
     </jp>*/
    /**<en>
     * Retrieve the name of the terminal.
     * @return :name of the terminalNo.
     </en>*/
    public String getTerminalName()
    {
        return wTerminalName;
    }

    /**<jp>
     * 端末名を設定します。
     * @param terminalname 端末名
     </jp>*/
    /**<en>
     * Set the name of the terminal.
     * @param terminalname :name of the terminal
     </en>*/
    public void setTerminalName(String terminalname)
    {
        wTerminalName = terminalname;
    }

    /**<jp>
     * IPアドレスを設定します。
     * @param ipaddress   設定するIPアドレス
     </jp>*/
    /**<en>
     * Set the IP address.
     * @param ipaddress   :IP address to set
     </en>*/
    public void setIPAddress(String ipaddress)
    {
        wIPAddress = ipaddress ;
    }

    /**<jp>
     * IPアドレスを取得します。
     * @return    IPアドレス
     </jp>*/
    /**<en>
     * Retrieve the IP address.
     * @return    :IP address
     </en>*/
    public String getIPAddress()
    {
        return wIPAddress ;
    }

    /**<jp>
     * プリンタ名を設定します。
     * @param printer   設定するプリンタ名
     </jp>*/
    /**<en>
     * Set the printer (name).
     * @param printer   :printer (name) to set
     </en>*/
    public void setPrinterName(String printer)
    {
        wPrinterName = printer ;
    }

    /**<jp>
     * プリンタ名を取得します。
     * @return    プリンタ名
     </jp>*/
    /**<en>
     * Retrieve the printer (name).
     * @return    printer (name)
     </en>*/
    public String getPrinterName()
    {
        return wPrinterName ;
    }

    /**<jp>
     * ロールIDを設定します。
     * @param id   設定するロールID
     </jp>*/
    /**<en>
     * Set the role ID.
     * @param id   :role ID to set
     </en>*/
    public void setRoleId(String id)
    {
        wRoleId = id ;
    }

    /**<jp>
     * ロールIDを取得します。
     * @return    ロールID
     </jp>*/
    /**<en>
     * Retrieve the role ID.
     * @return    role ID
     </en>*/
    public String getRoleId()
    {
        return wRoleId ;
    }

    /**<jp>
     * Terminalの文字列表現を返します。
     * @return    文字列表現
     </jp>*/
    /**<en>
     * Return the string representation of Terminal.
     * @return    string representation
     </en>*/
    public String toString()
    {
        StringBuffer buf = new StringBuffer(100) ;
        buf.append("\nTerminalNumber:" + wTerminalNumber) ;
        buf.append("\nTerminalName:" + wTerminalName) ;
        buf.append("\nIPAddress:" + wIPAddress) ;
        buf.append("\nPrinterName:" + wPrinterName) ;
        return buf.toString() ;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

