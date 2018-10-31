// $Id: SoftZonePriorityParameter.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.Parameter;

/**<jp>
 * ゾーンパラメータを保持するクラスです。
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class preserves the zone parameters.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 4122 $, $Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $
 * @author  $Author: ota $
 </en>*/
public class SoftZonePriorityParameter
        extends Parameter
{
    // Class fields --------------------------------------------------
    // Class variables -----------------------------------------------
    /**<jp>
     * ゾーン番号
     </jp>*/
    /**<en>
     * Zone no.
     </en>*/
    protected int wZoneID;

    /**<jp>
     * 優先ゾーン
     </jp>*/
    /**<en>
     * Priority zone no.
     </en>*/
    protected int wPriorityZone;

    /**<jp>
     * 優先順位
     </jp>*/
    /**<en>
     * Priority.
     </en>*/
    private int wPriority = 0;

    /**<jp>
     * ゾーンの所属する倉庫No
     </jp>*/
    /**<en>
     * Warehouse no. that the station belongs to
     </en>*/
    protected String wWareHouseStationNo;

    /**<jp>
     * ゾーンの所属する倉庫名称
     </jp>*/
    /**<en>
     * Name of the warehouse no. that the station belongs to
     </en>*/
    protected String wWareHouseName;

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
        return ("$Revision: 4122 $,$Date: 2009-04-10 19:58:38 +0900 (金, 10 4 2009) $");
    }

    // Constructors --------------------------------------------------

    /**<jp>
     * このクラスの初期化を行ないます。
     * @param conn データベースとのコネクションオブジェクト
     * @param kind 処理区分
     </jp>*/
    /**<en>
     * Initialize this class.
     * @param conn :connection object with database
     * @param kind :process type
     </en>*/
    public SoftZonePriorityParameter()
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * ゾーン番号を設定します。
     * @param zid   設定するゾーン番号
     </jp>*/
    /**<en>
     * Set the zone no.
     * @param zid   :zone no. to set
     </en>*/
    public void setZoneID(int zid)
    {
        wZoneID = zid;
    }

    /**<jp>
     * ゾーン番号を取得します。
     * @return    ゾーン番号
     </jp>*/
    /**<en>
     * Retrieve the zone no.
     * @return    zone no.
     </en>*/
    public int getZoneID()
    {
        return wZoneID;
    }

    /**<jp>
     * 優先ゾーンを設定します。
     * @param pzid   設定する優先ゾーン
     </jp>*/
    /**<en>
     * Set the priority zone no.
     * @param pzid   :priority zone no. to set
     </en>*/
    public void setPriorityZone(int pzid)
    {
        wPriorityZone = pzid;
    }

    /**<jp>
     * 優先ゾーンを取得します。
     * @return    優先ゾーン
     </jp>*/
    /**<en>
     * Retrieve the priority zone no.
     * @return    priority zone no.
     </en>*/
    public int getPriorityZone()
    {
        return wPriorityZone;
    }

    /**<jp>
     * ゾーン検索優先順序を設定します。
     * @param sno ゾーン検索優先順序
     </jp>*/
    /**<en>
     * Set the prioritized zone search order.
     * @param pri :prioritized zone search order
     </en>*/
    public void setPriority(int pri)
    {
        wPriority = pri;
    }

    /**<jp>
     * ゾーン検索優先順序を取得します。
     * @return ゾーン検索優先順序
     </jp>*/
    /**<en>
     * Retrieve the prioritized zone search order.
     * @return :prioritized zone search order
     </en>*/
    public int getPriority()
    {
        return wPriority;
    }

    /**<jp>
     * このゾーンが属している倉庫Noを設定します。
     * @param wh 倉庫No
     </jp>*/
    /**<en>
     * Set the warehouse no. that this zone belongs to.
     * @param whnum warehouse no.
     </en>*/
    public void setWareHouseStationNo(String whnum)
    {
        wWareHouseStationNo = whnum;
    }

    /**<jp>
     * このゾーンが属している倉庫Noを取得します。
     * @return 倉庫No
     </jp>*/
    /**<en>
     * Retrieve the warehouse no. that this zone belongs to.
     * @return warehouse no.
     </en>*/
    public String getWareHouseStationNo()
    {
        return wWareHouseStationNo;
    }

    /**<jp>
     * このゾーンが属している倉庫名称を設定します。
     * @param  倉庫名称
     </jp>*/
    /**<en>
     * Set the name of the warehouse that this zobne belongs to.
     * @param  the name of the warehouse
     </en>*/
    public void setWareHouseName(String wh)
    {
        wWareHouseName = wh;
    }

    /**<jp>
     * このゾーンが属している倉庫名称を取得します。
     * @return 倉庫名称
     </jp>*/
    /**<en>
     * Retrieve the name of the warehouse that this zobne belongs to.
     * @return the name of the warehouse
     </en>*/
    public String getWareHouseName()
    {
        return wWareHouseName;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

