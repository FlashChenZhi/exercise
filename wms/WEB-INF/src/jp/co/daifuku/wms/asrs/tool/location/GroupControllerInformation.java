// $Id: GroupControllerInformation.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.location ;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;

/**<jp>
 * グループコントローラーを管理するためのクラスです。
 *
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
 * This class is used to control the group controllers.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class GroupControllerInformation extends ToolEntity
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * AGCNo.
     </jp>*/
    /**<en>
     * AGCNo.
     </en>*/
    protected int wControllerNo ;
    
    /**<jp>
     * 状態
     </jp>*/
    /**<en>
     * status
     </en>*/
    protected int wStatusFlag ;
    
    /**<jp>
     * ホスト名
     </jp>*/
    /**<en>
     * host name
     </en>*/
    protected String wIPAddress ;
    
    /**<jp>
     * ポート番号
     </jp>*/
    /**<en>
     * port no.
     </en>*/
    protected int wPort ;

    // Class method --------------------------------------------------
    /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
    /**<en>
     * Return the version of this class.
     * @return Version and the date
     </en>*/
    public static String getVersion()
    {
        return ("$Revision: 87 $,$Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $") ;
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<CODE>GroupControllerInformation</CODE>を構築します。
     </jp>*/
    /**<en>
     * Constcut new <CODE>GroupControllerInformation</CODE>.
     </en>*/
    public GroupControllerInformation()
    {
    }
    
    /**<jp>
     * 新しい<CODE>GroupControllerInformation</CODE>を構築します。
     * @param agcno       AGCNo
     * @param status      状態
     * @param ip       ホスト名
     * @param port       ポート番号
     </jp>*/
    /**<en>
     * Construct new <CODE>GroupControllerInformation</CODE>.
     * @param agcno       AGCNo
     * @param status      status
     * @param ip       host name
     * @param port       port no.
     </en>*/
    public GroupControllerInformation(int agcno,
                                      int status,
                                      String ip,
                                      int port
                                      )
    {
        //<jp> インスタンス変数にセット</jp>
        //<en> Set as an instance variable.</en>
        setControllerNo(agcno);
        setStatusFlag(status);
        setIPAddress(ip);
        setPort(port);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * AGCNo.を設定します。
     * @param agcnum  AGCNo.
     </jp>*/
    /**<en>
     * Set the AGCNo.
     * @param agcnum :AGCNo.
     </en>*/
    public void setControllerNo(int agcnum)
    {
        wControllerNo = agcnum;
    }

    /**<jp>
     * AGCNo.を取得します。
     * @return AGCNo.
     </jp>*/
    /**<en>
     * Retrieve the AGCNo.
     * @return :AGCNo.
     </en>*/
    public int getControllerNo()
    {
        return wControllerNo;
    }

    /**<jp>
     * 状態を設定します。
     * @param status 状態
     </jp>*/
    /**<en>
     * Set the status.
     * @param status :status
     </en>*/
    public void setStatusFlag(int status)
    {
        wStatusFlag = status ;
    }

    /**<jp>
     * 状態を取得します。
     * @return 状態
     </jp>*/
    /**<en>
     * Retrieve the status.
     * @return :status
     </en>*/
    public int getStatusFlag()
    {
        return wStatusFlag ;
    }

    /**<jp>
     * ホスト名を設定します。
     * @param ipaddress ホスト名
     </jp>*/
    /**<en>
     * Set the host name.
     * @param ipaddress :host name
     </en>*/
    public void setIPAddress(String ipaddress)
    {
        wIPAddress = ipaddress ;
    }

    /**<jp>
     * ホスト名を取得します。
     * @return ホスト名
     </jp>*/
    /**<en>
     * Retrieve the host name.
     * @return :host name
     </en>*/
    public String getIPAddress()
    {
        return wIPAddress ;
    }
    
    /**<jp>
     * ポート番号を設定します。
     * @param port ポート番号
     </jp>*/
    /**<en>
     * Set the port no.
     * @param port :port no
     </en>*/
    public void setPort(int port)
    {
        wPort = port ;
    }

    /**<jp>
     * ポート番号を取得します。
     * @return ポート番号
     </jp>*/
    /**<en>
     * Retrieve the port no.
     * @return :port no
     </en>*/
    public int getPort()
    {
        return wPort ;
    }

    /**<jp>
     * GroupControllerInformationの文字列表現を返します。
     * @return    文字列表現
     </jp>*/
    /**<en>
     * Return the string representation of GroupControllerInformation.
     * @return    string representation
     </en>*/
    public String toString()
    {
        StringBuffer buf = new StringBuffer(100) ;
        buf.append("\nControllerNumber:" + wControllerNo) ;
        buf.append("\nStatus:" + wStatusFlag) ;
        buf.append("\nIPAddress:" + wIPAddress) ;
        buf.append("\nPort:" + wPort) ;
        return buf.toString() ;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

