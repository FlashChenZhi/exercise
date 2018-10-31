// $Id: RouteParameter.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.tool.schedule ;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

/**<jp>
 * 機種情報設定で使用されるエンティティクラスです。
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
 * This is an entity class which will be used in machine information settings.
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
public class RouteParameter extends Parameter
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    
    /**<jp> ステーションNo. </jp>*/
    /**<en> Station no. </en>*/
    protected String wStationNo = "";

    /**<jp> 接続ステーションNo. </jp>*/
    /**<en> Connecting station no. </en>*/
    protected String wConnectStNumber = "";

    /**<jp> Route.txtのパス </jp>*/
    /**<en> Path to Route.txt </en>*/
    protected String wRouteTextPath = "";

    // Class method --------------------------------------------------
     /**<jp>
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     </jp>*/
     /**<en>
     * Reuturns the version of this class.
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
     * This constructor will be used.
     </en>*/
    public RouteParameter()   
    {
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * ステーションNoを取得します。
     * @return wStationNo
     </jp>*/
    /**<en>
     * Retrieve the station no.
     * @return wStationNo
     </en>*/
    public String getStationNo()
    {
        return wStationNo;
    }
    /**<jp>
     * ステーションNoをセットします。
     * @param ステーションNo
     </jp>*/
    /**<en>
     * Set the station no.
     * @param arg station no.
     </en>*/
    public void setStationNo(String arg)
    {
        wStationNo = arg;
    }

    /**<jp>
     * 接続ステーションNoを取得します。
     * @return wConnectStNumber
     </jp>*/
    /**<en>
     * Retrieve the connecting station no.
     * @return wConnectStNumber
     </en>*/
    public String getConnectStNumber()
    {
        return wConnectStNumber;
    }
    /**<jp>
     * 接続ステーションNoをセットします。
     * @param 接続ステーションNo
     </jp>*/
    /**<en>
     * Set the connecting station no.
     * @param arg connecting station no.
     </en>*/
    public void setConnectStNumber(String arg)
    {
        wConnectStNumber = arg;
    }

    /**<jp>
     * Route.txtのパスを取得します。
     * @return wRouteTextPath
     </jp>*/
    /**<en>
     * Retrieve the path to Route.txt.
     * @return wRouteTextPath
     </en>*/
    public String getRouteTextPath()
    {
        return wRouteTextPath;
    }
    /**<jp>
     * Route.txtのパスをセットします。
     * @param 接続ステーションNo
     </jp>*/
    /**<en>
     * Set the path to Route.txt.
     * @param arg connecting Station no.
     </en>*/
    public void setRouteTextPath(String arg)
    {
        wRouteTextPath = arg;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

    
}
//end of class
