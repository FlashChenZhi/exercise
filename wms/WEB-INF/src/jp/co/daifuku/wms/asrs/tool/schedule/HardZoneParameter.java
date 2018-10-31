// $Id: HardZoneParameter.java 4122 2009-04-10 10:58:38Z ota $
package jp.co.daifuku.wms.asrs.tool.schedule;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.Parameter;
import jp.co.daifuku.wms.asrs.tool.location.Zone;

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
public class HardZoneParameter
        extends Parameter
{
    // Class fields --------------------------------------------------
    // Class variables -----------------------------------------------

    /**<jp>
     * Zoneインスタンス
     </jp>*/
    /**<en>
     * Zone instance
     </en>*/
    private Zone wInstance;

    /**<jp>
     * ゾーン番号
     </jp>*/
    /**<en>
     * Zone no.
     </en>*/
    protected int wZoneID;

    /**<jp>
     * ゾーン名称
     </jp>*/
    /**<en>
     * Zone name
     </en>*/
    protected String wZoneName;

    /**<jp>
     * 荷高
     </jp>*/
    /**<en>
     * Load height
     </en>*/
    protected int wHeight;

    /**<jp>
     * ステーションの所属する倉庫No
     </jp>*/
    /**<en>
     * Warehouse no. that the station belongs to
     </en>*/
    protected String wWareHouseStationNo;

    /**<jp>
     * ステーションの所属する倉庫名称
     </jp>*/
    /**<en>
     * Name of the warehouse no. that the station belongs to
     </en>*/
    protected String wWareHouseName;

    /**<jp>
     * 空棚検索方向
     </jp>*/
    /**<en>
     * Direction of search for empty locations
     </en>*/
    protected int wDirection;

    /**<jp>
     * 範囲指定の為の開始バンク。
     </jp>*/
    /**<en>
     * Starting bank which specifies the range
     </en>*/
    protected int wStartBank;

    /**<jp>
     * 範囲指定の為の開始ベイ。
     </jp>*/
    /**<en>
     * Starting bay which specifies the range
     </en>*/
    protected int wStartBay;

    /**<jp>
     * 範囲指定の為の開始レベル。
     </jp>*/
    /**<en>
     * Starting level which specifies the range
     </en>*/
    protected int wStartLevel;

    /**<jp>
     * 範囲指定の為の終了バンク。
     </jp>*/
    /**<en>
     * Ending bank which specifies the range
     </en>*/
    protected int wEndBank;

    /**<jp>
     * 範囲指定の為の終了ベイ。
     </jp>*/
    /**<en>
     * Ending bay which specifies the range
     </en>*/
    protected int wEndBay;

    /**<jp>
     * 範囲指定の為の終了レベル。
     </jp>*/
    /**<en>
     * Ending level which specifies the range
     </en>*/
    private int wEndLevel;

    /**<jp>
     * ハードゾーン運用の優先順位を保持します。種別がハードゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * Preserve the priority in hard zone operation. 
     * This variable is valid only when the hard zone is the type specified.
     </en>*/
    private String wPriority = "          ";


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
    public HardZoneParameter()
    {
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * Zoneインスタンスをセットします。
     * @param objセットするインスタンス
     </jp>*/
    /**<en>
     * Set the Zone instance.
     * @param obj :instance to set
     </en>*/
    public void setInstance(Zone obj)
    {
        wInstance = obj;
    }

    /**<jp>
     * Zoneインスタンスを取得します。
     * @return Zoneインスタンス
     </jp>*/
    /**<en>
     * Retrieve the Zone instance.
     * @return Zone instance
     </en>*/
    public Zone getInstance()
    {
        return wInstance;
    }

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
     * ゾーン名称を設定します。
     * @param nm 設定するゾーン名称
     </jp>*/
    /**<en>
     * Set the zone name.
     * @param nm :zone name to set
     </en>*/
    public void setZoneName(String nm)
    {
        wZoneName = nm;
    }

    /**<jp>
     * ゾーン名称を取得します。
     * @return    ゾーン名称
     </jp>*/
    /**<en>
     * Retrieve the zone name.
     * @return    zone name
     </en>*/
    public String getZoneName()
    {
        return (wZoneName);
    }

    /**<jp>
     * 荷高を設定します。
     * @param ht 設定する荷高
     </jp>*/
    /**<en>
     * Set the load height.
     * @param ht :load height to set
     </en>*/
    public void setHeight(int ht)
    {
        wHeight = ht;
    }

    /**<jp>
     * 荷高を取得します。
     * @return    荷高
     </jp>*/
    /**<en>
     * Retrieve the load height.
     * @return    load height
     </en>*/
    public int getHeight()
    {
        return (wHeight);
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

    /**<jp>
     * 空棚検索方向をセットします。
     * @param di セットする空棚検索方向
     </jp>*/
    /**<en>
     * Set the search direction for empty locations.
     * @param di :the search direction for empty locations to set
     </en>*/
    public void setDirection(int di)
    {
        wDirection = di;
    }

    /**<jp>
     * 空棚検索方向を取得します。
     * @return 空棚検索方向
     </jp>*/
    /**<en>
     * Retrieve the search direction for empty locations.
     * @return :the search direction for empty locations
     </en>*/
    public int getDirection()
    {
        return wDirection;
    }

    /**<jp>
     * ソフトゾーンの開始バンクを設定します。
     * @param sbank 開始バンク
     </jp>*/
    /**<en>
     * Set the starting bank of soft zone.
     * @param sbank :starting bank
     </en>*/
    public void setStartBank(int sbank)
    {
        wStartBank = sbank;
    }

    /**<jp>
     * ソフトゾーンの開始バンクを取得します。
     * @return 開始バンク
     </jp>*/
    /**<en>
     * Retrieve the starting bank of soft zone.
     * @return :starting bank
     </en>*/
    public int getStartBank()
    {
        return wStartBank;
    }

    /**<jp>
     * ソフトゾーンの開始ベイを設定します。
     * @param sbay 開始ベイ
     </jp>*/
    /**<en>
     * Set the starting bay of soft zone.
     * @param sbay :starting bay
     </en>*/
    public void setStartBay(int sbay)
    {
        wStartBay = sbay;
    }

    /**<jp>
     * ソフトゾーンの開始ベイを取得します。
     * @return 開始ベイ
     </jp>*/
    /**<en>
     * Retrieve the starting bay of soft zone.
     * @return :starting bay
     </en>*/
    public int getStartBay()
    {
        return wStartBay;
    }

    /**<jp>
     * ソフトゾーンの開始レベルを設定します。
     * @param slevel 開始レベル
     </jp>*/
    /**<en>
     * Set the starting level of soft zone.
     * @param slevel :satrting level
     </en>*/
    public void setStartLevel(int slevel)
    {
        wStartLevel = slevel;
    }

    /**<jp>
     * ソフトゾーンの開始レベルを取得します。
     * @return 開始レベル
     </jp>*/
    /**<en>
     * Retrieve the starting level of soft zone.
     * @return :satrting level
     </en>*/
    public int getStartLevel()
    {
        return wStartLevel;
    }

    /**<jp>
     * ソフトゾーンの終了バンクを設定します。
     * @param ebank 終了バンク
     </jp>*/
    /**<en>
     * Set the ending bank of soft zone.
     * @param ebank :ending bank
     </en>*/
    public void setEndBank(int ebank)
    {
        wEndBank = ebank;
    }

    /**<jp>
     * ソフトゾーンの終了バンクを取得します。
     * @return 終了バンク
     </jp>*/
    /**<en>
     * Retrieve the ending bank of soft zone.
     * @return :ending bank
     </en>*/
    public int getEndBank()
    {
        return wEndBank;
    }

    /**<jp>
     * ソフトゾーンの終了ベイを設定します。
     * @param ebay 終了ベイ
     </jp>*/
    /**<en>
     * Set the ending bay of soft zone.
     * @param ebay :ending bay
     </en>*/
    public void setEndBay(int ebay)
    {
        wEndBay = ebay;
    }

    /**<jp>
     * ソフトゾーンの終了ベイを取得します。
     * @return 終了ベイ
     </jp>*/
    /**<en>
     * Retrieve the ending bay of soft zone.
     * @return :ending bay
     </en>*/
    public int getEndBay()
    {
        return wEndBay;
    }

    /**<jp>
     * ソフトゾーンの終了レベルを設定します。
     * @param elevel 終了レベル
     </jp>*/
    /**<en>
     * Set the ending level of soft zone.
     * @param elevel :ending level
     </en>*/
    public void setEndLevel(int elevel)
    {
        wEndLevel = elevel;
    }

    /**<jp>
     * ソフトゾーンの終了レベルを取得します。
     * @return 終了レベル
     </jp>*/
    /**<en>
     * Retrieve the ending bank of soft zone.
     * @return :ending level
     </en>*/
    public int getEndLevel()
    {
        return wEndLevel;
    }

    /**<jp>
     * ゾーン検索優先順序を設定します。
     * @param sno ゾーン検索優先順序
     </jp>*/
    /**<en>
     * Set the prioritized zone search order.
     * @param pri :prioritized zone search order
     </en>*/
    public void setPriority(String pri)
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
    public String getPriority()
    {
        return wPriority;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

