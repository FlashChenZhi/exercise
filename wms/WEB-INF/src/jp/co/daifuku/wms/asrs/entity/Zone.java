// $Id: Zone.java 87 2008-10-04 03:07:38Z admin $
package jp.co.daifuku.wms.asrs.entity;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.handler.db.AbstractDBEntity;
import jp.co.daifuku.wms.handler.field.StoreDefineHandler;
import jp.co.daifuku.wms.handler.field.StoreMetaData;

/**<jp>
 * 棚のゾーンを管理するためのクラスです。
 * ゾーンは、倉庫内の棚ごとに割り当てられます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </jp>*/
/**<en>
 * This class is used for zone control of shelves.
 * Zone is assigned to each shlf of the warehouse.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author  $Author: admin $
 </en>*/
public class Zone
        extends AbstractDBEntity
{

    // Class fields --------------------------------------------------
    /**<jp>
     * 開始/終了棚のBank,Bay,Levelの要素番号 (Bank)
     </jp>*/
    /**<en>
     * Element numbers of start/end shelf of Bank
     </en>*/
    public static final int BANK = 0;

    /**<jp>
     * 開始/終了棚のBank,Bay,Levelの要素番号 (Bay)
     </jp>*/
    /**<en>
     * Element numbers of start/end shelf of Bay
     </en>*/
    public static final int BAY = 1;

    /**<jp>
     * 開始/終了棚のBank,Bay,Levelの要素番号 (Level)
     </jp>*/
    /**<en>
     * Element numbers of start/end shelf of Level
     </en>*/
    public static final int LEVEL = 2;

    /** テーブル名定義 */
    public static final String STORE_NAME = "DNHARDZONE";

    // Class variables -----------------------------------------------
    /**<jp>
     * ゾーン番号
     </jp>*/
    /**<en>
     * Zone no.
     </en>*/
    private String _softZoneID;

    /**<jp>
     * ゾーン名称
     </jp>*/
    /**<en>
     * Name of the zone
     </en>*/
    private String _name;

    /**<jp>
     * ステーションの所属する倉庫No
     </jp>*/
    /**<en>
     * Warehouse no, the station belongs to
     </en>*/
    private String _whStationNo;

    /**<jp>
     * 空棚検索方向
     </jp>*/
    /**<en>
     * Search direction in empty location search
     </en>*/
    private int _direction;

    /**<jp>
     * <CODE>HardZone</CODE>オブジェクト
     </jp>*/
    /**<en>
     * Object of <CODE>HardZone</CODE>
     </en>*/
    private HardZone _targetHardZone = null;

    /**<jp>
     * 範囲指定の為の開始バンク。種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * Start Bank
     </en>*/
    private String _startBank;

    /**<jp>
     * 範囲指定の為の開始ベイ。種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * Start Bay
     </en>*/
    private String _startBay;

    /**<jp>
     * 範囲指定の為の開始レベル。種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * Start Level
     </en>*/
    private String _startLevel;

    /**<jp>
     * 範囲指定の為の終了バンク。種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * End Bank
     </en>*/
    private String _endBank;

    /**<jp>
     * 範囲指定の為の終了ベイ。種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * End Bayk
     </en>*/
    private String _endBay;

    /**<jp>
     * 範囲指定の為の終了レベル。種別がソフトゾーンの場合のみ有効な変数です。
     </jp>*/
    /**<en>
     * End Level
     </en>*/
    private String _endLevel;

    /** Store meta data for this entity */
    public static StoreMetaData $storeMetaData = StoreDefineHandler.createStoreMetaData(STORE_NAME);

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
     * ゾーン番号を設定します。
     * @param zid 設定するゾーン番号
     </jp>*/
    /**<en>
     * Sets the zone no.
     * @param zid :zone no. to set
     </en>*/
    public void setSoftZoneID(String zid)
    {
        _softZoneID = zid;
    }

    /**<jp>
     * ゾーン番号を取得します。
     * @return ゾーン番号
     </jp>*/
    /**<en>
     * Gets the zone no.
     * @return :zone no.
     </en>*/
    public String getSoftZoneID()
    {
        return _softZoneID;
    }

    /**<jp>
     * ゾーン名称を設定します。
     * @param nm 設定するゾーン名称
     </jp>*/
    /**<en>
     * Sets name of zone.
     * @param nm :name of zone to set
     </en>*/
    public void setName(String nm)
    {
        _name = nm;
    }

    /**<jp>
     * ゾーン名称を取得します。
     * @return ゾーン名称
     </jp>*/
    /**<en>
     * Getse name of zone
     * @return :name of zone
     </en>*/
    public String getName()
    {
        return (_name);
    }

    /**<jp>
     * このゾーンが属している倉庫Noを設定します。
     * @param whnum 倉庫No
     </jp>*/
    /**<en>
     * Sets the warehouse no. this zone belongs to.
     * @param whnum :warehouse no.
     </en>*/
    public void setWHStationNo(String whnum)
    {
        _whStationNo = whnum;
    }

    /**<jp>
     * このゾーンが属している倉庫Noを取得します。
     * @return 倉庫No
     </jp>*/
    /**<en>
     * Gets the warehouse no. this zone belongs to.
     * @return warehouse no.
     </en>*/
    public String getWHStationNo()
    {
        return _whStationNo;
    }

    /**<jp>
     * 空棚検索方向をセットします。
     * @param di セットする空棚検索方向
     </jp>*/
    /**<en>
     * Sets the direction of empty location search.
     * @param di :direction of empty location search to set
     </en>*/
    public void setDirection(int di)
    {
        _direction = di;
    }

    /**<jp>
     * 空棚検索方向を取得します。
     * @return 空棚検索方向
     </jp>*/
    /**<en>
     * Gets direction of empty location search.
     * @return :direction of empty location search
     </en>*/
    public int getDirection()
    {
        return _direction;
    }

    /**<jp>
     * <CODE>HardZone</CODE>をセットします。
     * @param zone セットするHardZone
     </jp>*/
    /**<en>
     * Sets <CODE>HardZone</CODE>.
     * @param zone :HardZone to set
     </en>*/
    public void setHardZone(HardZone zone)
    {
        _targetHardZone = zone;
    }

    /**<jp>
     * <CODE>HardZone</CODE>を取得します。
     * @return HardZone
     </jp>*/
    /**<en>
     * Gets <CODE>HardZone</CODE>.
     * @return HardZone
     </en>*/
    public HardZone getHardZone()
    {
        return _targetHardZone;
    }

    /**<jp>
     * ソフトゾーンの開始バンクを設定します。
     * @param sbank 開始バンク
     </jp>*/
    /**<en>
     * Sets Start Bank.
     * @param sbank :Start Bank
     </en>*/
    public void setStartBank(String sbank)
    {
        _startBank = sbank;
    }

    /**<jp>
     * ソフトゾーンの開始バンクを取得します。
     * @return 開始バンク
     </jp>*/
    /**<en>
     * Gets Start Bank.
     * @return Start Bank
     </en>*/
    public String getStartBank()
    {
        return _startBank;
    }

    /**<jp>
     * ソフトゾーンの開始ベイを設定します。
     * @param sbay 開始ベイ
     </jp>*/
    /**<en>
     * Sets Start Bay.
     * @param sbay :Start Bay
     </en>*/
    public void setStartBay(String sbay)
    {
        _startBay = sbay;
    }

    /**<jp>
     * ソフトゾーンの開始ベイを取得します。
     * @return 開始ベイ
     </jp>*/
    /**<en>
     * Gets Start Bay.
     * @return Start Bay
     </en>*/
    public String getStartBay()
    {
        return _startBay;
    }

    /**<jp>
     * ソフトゾーンの開始レベルを設定します。
     * @param slevel 開始レベル
     </jp>*/
    /**<en>
     * Sets Start Level.
     * @param slevel :Start Level
     </en>*/
    public void setStartLevel(String slevel)
    {
        _startLevel = slevel;
    }

    /**<jp>
     * ソフトゾーンの開始レベルを取得します。
     * @return 開始レベル
     </jp>*/
    /**<en>
     * Gets Start Level.
     * @return Start Level
     </en>*/
    public String getStartLevel()
    {
        return _startLevel;
    }

    /**<jp>
     * ソフトゾーンの終了バンクを設定します。
     * @param ebank 終了バンク
     </jp>*/
    /**<en>
     * Sets End Bank.
     * @param ebank :End Bank
     </en>*/
    public void setEndBank(String ebank)
    {
        _endBank = ebank;
    }

    /**<jp>
     * ソフトゾーンの終了バンクを取得します。
     * @return 終了バンク
     </jp>*/
    /**<en>
     * Gets End Bank.
     * @return End Bank
     </en>*/
    public String getEndBank()
    {
        return _endBank;
    }

    /**<jp>
     * ソフトゾーンの終了ベイを設定します。
     * @param ebay 終了ベイ
     </jp>*/
    /**<en>
     * Sets End Bay.
     * @param ebay :End Bay
     </en>*/
    public void setEndBay(String ebay)
    {
        _endBay = ebay;
    }

    /**<jp>
     * ソフトゾーンの終了ベイを取得します。
     * @return 終了ベイ
     </jp>*/
    /**<en>
     * Gets End Bay.
     * @return End Bay
     </en>*/
    public String getEndBay()
    {
        return _endBay;
    }

    /**<jp>
     * ソフトゾーンの終了レベルを設定します。
     * @param elevel 終了レベル
     </jp>*/
    /**<en>
     * Sets End Level.
     * @param elevel :End Level
     </en>*/
    public void setEndLevel(String elevel)
    {
        _endLevel = elevel;
    }

    /**<jp>
     * ソフトゾーンの終了レベルを取得します。
     * @return 終了レベル
     </jp>*/
    /**<en>
     * Gets End Level.
     * @return End Level
     </en>*/
    public String getEndLevel()
    {
        return _endLevel;
    }

    // Package methods -----------------------------------------------

    /**
     * ストアメタデータを返します。
     * @return このエンティティ用ストアメタデータ
     */
    public StoreMetaData getStoreMetaData()
    {
        return $storeMetaData;
    }

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
}
//end of class

