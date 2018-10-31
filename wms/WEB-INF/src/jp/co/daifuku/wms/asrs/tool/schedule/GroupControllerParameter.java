// $Id: GroupControllerParameter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.schedule ;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

/**<jp>
 * グループコントローラーで使用されるエンティティクラスです。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This is an entity class which will be used in group controller.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class GroupControllerParameter extends Parameter
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    
    /** AGCNo. */
    protected int wControllerNumber = 0;
    
    /**<jp> ホスト名 </jp>*/
    /**<en> host name </en>*/
    protected String wIPAddress = "";

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
     * このコンストラクタを使用します。
     </jp>*/
    /**<en>
     * This consructor will be used.
     </en>*/
    public GroupControllerParameter()   
    {
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * AGCNo.を取得します。
     * @return wControllerNumber
     </jp>*/
    /**<en>
     * Retrieve the AGCNo.
     * @return wControllerNumber
     </en>*/
    public int getControllerNumber()
    {
        return wControllerNumber;
    }
    
    /**<jp>
     * AGCNo.をセットします。
     * @param ControllerNumber
     </jp>*/
    /**<en>
     * Set the AGCNo.
     * @param arg ControllerNumber
     </en>*/
    public void setControllerNumber(int arg)
    {
        wControllerNumber = arg;
    }
    
    /**<jp>
     * ホスト名を取得します。
     * @return wIPAddress
     </jp>*/
    /**<en>
     * Retrieve the host name.
     * @return wIPAddress
     </en>*/
    public String getIPAddress()
    {
        return wIPAddress;
    }
    
    /**<jp>
     * ホスト名をセットします。
     * @param IPAddress
     </jp>*/
    /**<en>
     * Set the host name.
     * @param arg IPAddress
     </en>*/
    public void setIPAddress(String arg)
    {
        wIPAddress = arg;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    
}
//end of class
