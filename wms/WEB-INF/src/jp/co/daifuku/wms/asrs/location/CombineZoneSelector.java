// $Id: CombineZoneSelector.java 7633 2010-03-17 01:17:37Z ota $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.SoftZonePriority;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**<jp>
 * ソフトゾーン、ハードゾーン併用のゾーンを管理するためのクラスです。
 * ゾーンとは、倉庫を一定のルールに基づいて分割・管理するための単位です。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/12/15</TD><TD>T.Yamashita</TD>荷姿指定無しの場合、その倉庫全体のハードゾーンが対象とするようにした。<TD></TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7633 $, $Date: 2010-03-17 10:17:37 +0900 (水, 17 3 2010) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class is used for zone control.
 * Zone is a unit of devided area used for warehouse control according to the fixed rules.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7633 $, $Date: 2010-03-17 10:17:37 +0900 (水, 17 3 2010) $
 * @author  $Author: ota $
 </en>*/
public class CombineZoneSelector
        implements ZoneSelector
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * データベース接続用のコネクション
     </jp>*/
    /**<en>
     * Connection with database
     </en>*/
    private Connection _conn;

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
        return ("$Revision: 7633 $,$Date: 2010-03-17 10:17:37 +0900 (水, 17 3 2010) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用のConnectionインスタンスを引数としてインスタンスを生成します。
     * トランザクション制御は内部で行っていませんので、外部でCommitする必要があります。
     * @param conn  データベース接続用の<code>Connection</code>
     </jp>*/
    /**<en>
     * Generates the instance according to the parameter of Connection instance to connect with database.
     * As transaction control is not internally conducted, it is necessary to commit transaction control externally.
     * @param conn  :<code>Connection</code> to connect with database
     </en>*/
    public CombineZoneSelector(Connection conn)
    {
        _conn = conn;
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * <code>Zone</code>のインスタンスを検索します。
     * 引き渡された荷高からハードゾーン情報を取得します。
     * 取得したソフトゾーン情報にハードゾーン情報を付加した<code>Zone</code>を返します。<br>
     * ソフトゾーン運用、ハードゾーン運用を行わない場合でも
     * あらかじめZONE表、HARDZONE表にIDを定義しておく必要があります。
     * @param plt 空棚検索の対象となるPallet情報を含む<code>Pallet</code>インスタンス
     * @param wh  検索する対象ゾーンが含まれる倉庫情報
     * @return 引数に基づいて作成される<code>Zone</code>オブジェクトの配列
     * @throws ReadWriteException データアクセスに失敗した場合に通知されます。
     * @throws InvalidDefineException 該当するゾーン情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Searches the instance of <code>Zone</code>.
     * The search will be done by the article name master table in the stock table presented by pallet table
     * which is provided by paramter. 
     * In this method, it is presumpted that all Items on the Pallet are subject to the same classification.
     * Therefore, if there are any Item requiring different classificaitons, it may return zones unfitting to some 
     * Items.
     * It retrieves soft zone information from classificaiton data, and retrieves hard zone data from the load height 
     * of <code>Pallet</code>.
     * THen it returns <code>Zone</code>, the addition of retrieved soft zone data and hard zone data.<br>
     * It is required that ZONE table and HARDZONE table must be predefined with IDs eveb if the soft zone operation or
     * hard zone operation will not be performed.
     * @param plt :<code>Pallet</code> instance which includes the Pallet information subject to empty location search
     * @param wh  :warehous information which contains teh objective zone of search.
     * @return    :<code>Zone</code> object array, created according to the paremeter
     * @throws ReadWriteException :Notifies if it failed the data aceess.
     * @throws InvalidDefineException :Notifies if there is no such zone data exists.
     </en>*/
    public Zone[] select(Pallet plt, WareHouse wh)
            throws ReadWriteException,
                InvalidDefineException
    {
        // ゾーンインスタンスの生成を行います。
        Zone[] zone = makeZone(wh.getStationNo(), plt.getSoftZoneId());

        //<jp> 検索ゾーン一覧を取得する。</jp>
        //<en> Retrieves zone array</en>
        return getSearchZones(zone, plt.getHeight(), wh.getStationNo());
    }

    /**
     * <code>Zone</code>のインスタンスを検索します。
     * 引き渡されたゾーンからハードゾーン情報を取得します。
     * 取得したソフトゾーン情報にハードゾーン情報を付加した<code>Zone</code>を返します。<br>
     * ソフトゾーン運用、ハードゾーン運用を行わない場合でも
     * あらかじめZONE表、HARDZONE表にIDを定義しておく必要があります。
     * @param hzone ハードゾーン
     * @param szone ソフトゾーン
     * @param wh  検索する対象ゾーンが含まれる倉庫情報
     * @return 引数に基づいて作成される<code>Zone</code>オブジェクトの配列
     * @throws ReadWriteException データアクセスに失敗した場合に通知されます。
     * @throws InvalidDefineException 該当するゾーン情報が見つからない場合に通知されます。
     */
    public Zone[] selectZone(String hzone, String szone, WareHouse wh)
            throws ReadWriteException,
                InvalidDefineException
    {
        // ゾーンインスタンスの生成を行います。
        Zone[] zone = makeZone(wh.getStationNo(), szone, false);

        //<jp> 検索ゾーン一覧を取得する。</jp>
        //<en> Retrieves zone array</en>
        return getSearchZones(zone, hzone, wh.getStationNo());
    }

    /**<jp>
     * <code>Zone</code>のインスタンスを検索します(クローズ運用スケジュールで使用します)。
     * 引き渡された荷高からハードゾーン情報を取得します。
     * 取得したソフトゾーン情報にハードゾーン情報を付加した<code>Zone</code>を返します。<br>
     * ソフトゾーン運用、ハードゾーン運用を行わない場合でも
     * あらかじめZONE表、HARDZONE表にIDを定義しておく必要があります。
     * @param classid 分類ID（現在未使用）
     * @param height  荷高
     * @param wh      検索する対象ゾーンが含まれる倉庫情報
     * @return 引数に基づいて作成される<code>Zone</code>オブジェクトの配列
     * @throws ReadWriteException データアクセスに失敗した場合に通知されます。
     * @throws InvalidDefineException 該当するゾーン情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Searches <code>Zone</code> instance (to be used in close operation schedule).
     * Retrieves hard zone ID from provided load height.
     * Returns <code>Zone</code>, attachment of soft zone data and hard zone data. <br>
     * IT is necessary to predefine the ZONE table and HARDZONE table with IDs, even if the softzone operation or 
     * the hard zone operation are not carried out.
     * @param classid :classificaiton ID(unused at moment)
     * @param height  :load height
     * @param wh   :warehouse information which contains the zone to search.
     * @return :<code>Zone</code> object array created according to the parameter.
     * @throws ReadWriteException :Notifies if data access failed.
     * @throws InvalidDefineException :Notifies if no such zone data is found.
     </en>*/
    public Zone[] selectCloseZone(int classid, int height, WareHouse wh)
            throws ReadWriteException,
                InvalidDefineException
    {
        // ソフトゾーンを検索する
        Zone[] zone = makeZone(wh.getStationNo(), Integer.toString(classid));

        //<jp> 検索ゾーン一覧を取得する。</jp>
        //<en> Retrieves zone array</en>
        return getSearchZones(zone, height, wh.getStationNo());
    }

    /**<jp>
     * 棚検索用に<code>Zone</code>のインスタンスを検索します
     * ソフトゾーン、荷高、倉庫ステーションより、ソフトゾーンとハードゾーンを結合します。
     * 指定された荷高に対応するハードゾーン定義が見つからない場合でも
     * パレットの荷高情報に有効な値がセットされていない場合（Height=0)
     * エラーとせず、ソフトゾーンID=ハードゾーンIDとしてハードゾーンの検索を行ないます。
     * この場合はゾーン優先順位は無視されます。
     * この処理は、商品マスタから取得されるゾーンをハードゾーンとして使用する場合に有効です。
     * ソフトゾーン運用、ハードゾーン運用を行わない場合でも
     * あらかじめZONE表、HARDZONE表にIDを定義しておく必要があります。
     * @param zone   ソフトゾーンのゾーンインスタンス
     * @param height 荷高
     * @param whstno     検索する対象ゾーンが含まれる倉庫情報
     * @return 引数に基づいて作成される<code>Zone</code>オブジェクトの配列(ソフトゾーン+ハードゾーン)
     * @throws ReadWriteException データアクセスに失敗した場合に通知されます。
     * @throws InvalidDefineException 該当するゾーン情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Search the instance:<code>Zone</code> for location search use.
     * Soft zone and hard zone are connected according to soft zone, load size and warehouse station.
     * In case the hard zone definition that corresponds to the specified load size is not found,
     * unless the size is set with valid value (Height=0),
     * error will not occur but hard zone is searched regarding soft zone ID = hard zone ID.
     * In this case, the priority with zone is disregarded.
     * This process is useful when using the zone that is acquired from item master as a hard zone.
     * Even if the soft zone/hard zone operation are not used,
     * IDs must be defined in advance in ZONE table/HARDZONE table.
     * @param zone    :zone instance of the soft zone
     * @param height  :load size
     * @param whstno      :warehouse data that include the target zone of search
     * @return :arrya of <code>Zone</code> object that is created according to parameter(hard zone and soft zone)
     * @throws ReadWriteException :Notify if access to data failed.
     * @throws InvalidDefineException :Notify if corresponding zone data cannot be found.
     </en>*/
    public Zone[] getSearchZones(Zone[] zone, int height, String whstno)
            throws ReadWriteException,
                InvalidDefineException
    {
        //<jp> 倉庫、荷高よりハードゾーンを取得</jp>
        //<en> Get the hard zone based on the warehouse and load size.</en>
        HardZoneHandler hhandl = new HardZoneHandler(_conn);
        HardZoneSearchKey hkey = new HardZoneSearchKey();
        hkey.setWhStationNo(whstno);
        hkey.setHeight(height);
        HardZone[] hardzone = (HardZone[])hhandl.find(hkey);
        Zone[] arrayzone = null;

        if (hardzone.length > 0)
        {
            //<jp> priority項目（空棚検索順）を取得。ハードゾーンのheigtはユニークとなる。</jp>
            //<en> Get priority item(location search order). Height in hard zone will be unique.</en>
            String pri = hardzone[0].getPriority();
            //もしPriorityに自分自身のIDが含まれていない場合のみ追加する。
            if (pri.indexOf(hardzone[0].getHardZoneId()) == -1)
            {
                pri = hardzone[0].getHardZoneId() + pri;
            }
            List<HardZone> hardZoneList = new ArrayList<HardZone>();

            //<jp>hardZoneSearchKeyの初期化</jp>
            //<en>Initialize the hardZoneSearchKey</en>
            hkey = new HardZoneSearchKey();

            //<jp> 倉庫情報をセットしてハードゾーンを全件保持する。</jp>
            //<en> Set the warehous data then preserve all hard zone data.</en>
            hkey.setWhStationNo(whstno);
            HardZone[] tmpzone = (HardZone[])hhandl.find(hkey);

            for (int i = 0; i < pri.length(); i++)
            {
                String tmpzn = pri.substring(i, i + 1);

                for (int l = 0; l < tmpzone.length; l++)
                {
                    if (tmpzone[l].getHardZoneId().equals(tmpzn))
                    {
                        hardZoneList.add(tmpzone[l]);
                    }
                }
            }

            HardZone[] arrayhzone = (HardZone[])hardZoneList.toArray(new HardZone[0]);

            //<jp> ソフトゾーン情報にハードゾーン情報を付加する</jp>
            //<en> Add the hard zone data to the soft zone data.</en>
            List<Zone> zoneList = new ArrayList<Zone>();
            for (int i = 0; i < zone.length; i++)
            {
                for (int cnt = 0; cnt < arrayhzone.length; cnt++)
                {
                    Zone addZone = new Zone();
                    addZone.setDirection(zone[i].getDirection());
                    addZone.setHardZone(arrayhzone[cnt]);
                    addZone.setName(zone[i].getName());
                    addZone.setSoftZoneID(zone[i].getSoftZoneID());
                    addZone.setWHStationNo(zone[i].getWHStationNo());
                    zoneList.add(addZone);
                }
            }
            arrayzone = (Zone[])zoneList.toArray(new Zone[0]);
            return arrayzone;
        }
        else
        {
            //<jp> ゾーン定義が見つからない場合でも</jp>
            //<jp> パレットの荷高情報に有効な値がセットされていない場合は</jp>
            //<jp> エラーとせず、ソフトゾーンID=ハードゾーンIDとして</jp>
            //<jp> ハードゾーンの検索を行なう。このときは優先順位は無視する。</jp>
            //<jp> この処理は、商品マスタから取得されるゾーンをハードゾーンとして</jp>
            //<jp> 適用する場合に使用される。</jp>
            //<en> Even if the zone definition cannot be found,</en>
            //<en> unless valid value is not set for load size of pallets,</en>
            //<en> error will not occur but hard zone data will be searched regarding</en>
            //<en> the soft zone ID=hard zone ID. In this case, the priority will be disregarded.</en>
            //<en> This process is used when applying the zone acquired from item master as hard zone.</en>
            if (height == 0)
            {
                hkey = new HardZoneSearchKey();
                hkey.setWhStationNo(whstno);
                hardzone = (HardZone[])hhandl.find(hkey);
                if (hardzone.length == 0)
                {
                    //<jp> 6026060=指定された倉庫のゾーン定義がありません 倉庫={0}</jp>
                    //<en> 6026060=There is no zone definition for the specified warehouse. Warehouse={0}</en>
                    RmiMsgLogClient.write(WmsMessageFormatter.format(6026060, whstno), this.getClass().getName());
                    throw new InvalidDefineException(WmsMessageFormatter.format(6026060, whstno));
                }

                List<Zone> zoneList = new ArrayList<Zone>();
                for (int i = 0; i < zone.length; i++)
                {
                    for (int cnt = 0; cnt < hardzone.length; cnt++)
                    {
                        Zone addZone = new Zone();
                        addZone.setDirection(zone[i].getDirection());
                        addZone.setHardZone(hardzone[cnt]);
                        addZone.setName(zone[i].getName());
                        addZone.setSoftZoneID(zone[i].getSoftZoneID());
                        addZone.setWHStationNo(zone[i].getWHStationNo());
                        zoneList.add(addZone);
                    }
                }
                arrayzone = (Zone[])zoneList.toArray(new Zone[0]);
                return arrayzone;

            }
            //<jp> 6026060=指定された倉庫のゾーン定義がありません 倉庫={0}</jp>
            //<en> 6026060=There is no zone definition for the specified warehouse. Warehouse={0}</en>
            RmiMsgLogClient.write(WmsMessageFormatter.format(6026060, whstno), this.getClass().getName());
            throw new InvalidDefineException(WmsMessageFormatter.format(6026060, whstno));
        }
    }

    /**<jp>
     * ハードゾーンを指定した<code>Zone</code>のインスタンスを検索します
     * ハードゾーンより、ソフトゾーンとハードゾーンを結合します。
     * ソフトゾーン運用、ハードゾーン運用を行わない場合でも
     * あらかじめZONE表、HARDZONE表にIDを定義しておく必要があります。
     * @param zone   ソフトゾーンのゾーンインスタンス
     * @param hzone  ハードゾーン
     * @param whstno 検索する対象ゾーンが含まれる倉庫情報
     * @return 引数に基づいて作成される<code>Zone</code>オブジェクトの配列(ソフトゾーン+ハードゾーン)。
     *          ゾーン情報に該当のゾーンが見つからない場合、nullを返します。
     * @throws ReadWriteException データアクセスに失敗した場合に通知されます。
     </jp>*/
    public Zone[] getSearchZones(Zone[] zone, String hzone, String whstno)
            throws ReadWriteException
    {
        //<jp> ハードゾーンを取得</jp>
        //<en> Get the hard zone based on the warehouse and load size.</en>
        HardZoneHandler hhandl = new HardZoneHandler(_conn);
        HardZoneSearchKey hkey = new HardZoneSearchKey();
        hkey.setWhStationNo(whstno);
        hkey.setHardZoneId(hzone);
        HardZone[] hardzone = (HardZone[])hhandl.find(hkey);
        Zone[] arrayzone = null;

        if (hardzone.length > 0)
        {
            //<jp> ソフトゾーン情報にハードゾーン情報を付加する</jp>
            //<en> Add the hard zone data to the soft zone data.</en>
            List<Zone> zoneList = new ArrayList<Zone>();

            for (int i = 0; i < zone.length; i++)
            {
                for (int cnt = 0; cnt < hardzone.length; cnt++)
                {
                    Zone addZone = new Zone();
                    addZone.setDirection(zone[i].getDirection());
                    addZone.setHardZone(hardzone[cnt]);
                    addZone.setName(zone[i].getName());
                    addZone.setSoftZoneID(zone[i].getSoftZoneID());
                    addZone.setWHStationNo(zone[i].getWHStationNo());
                    zoneList.add(addZone);
                }
            }
            arrayzone = (Zone[])zoneList.toArray(new Zone[0]);
            return arrayzone;
        }
        else
        {
            //<jp> ゾーン定義が見つからない場合</jp>
            //<jp> 6027014=指定されたハードゾーンがゾーン定義にありません。倉庫:{0} ハードゾーンID:{1}</jp>
            RmiMsgLogClient.write(WmsMessageFormatter.format(6027014, whstno, hzone), this.getClass().getName());
            return null;
        }
    }

    /**<jp>
     * <code>Zone</code>のインスタンスを検索します。
     * 引き渡された荷高からハードゾーン情報を取得します。
     * 取得したソフトゾーン情報にハードゾーン情報を付加した<code>Zone</code>を返します。<br>
     * 指定された荷高に対するハードゾーンが定義されていない場合、全ハードゾーンで検索します。
     * このメソッドは空棚数のカウント時に使用します。
     * @param consignorCode 空棚検索の対象となるconsignorCode（現在未使用）
     * @param itemCode 空棚検索の対象となるitemCode（現在未使用）
     * @param height  荷高
     * @param wh      検索する対象ゾーンが含まれる倉庫情報
     * @return 引数に基づいて作成される<code>Zone</code>オブジェクトの配列
     * @throws ReadWriteException データアクセスに失敗した場合に通知されます。
     * @throws InvalidDefineException 該当するゾーン情報が見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Searches the instance of <code>Zone</code>.
     * Retrieves hard zone ID from provided load height.
     * Returns <code>Zone</code>, the addiion of soft zone data retrieved and the hard zone data.<br>
     * If hardzone is not defined for specified load height, search by 'all hard zone'
     * This method is used when counting the number of empty location.
     * @param consignorCode :temKey subject to empty location search(unused at moment)
     * @param itemCode :temKey subject to empty location search(unused at moment)
     * @param height  :load height
     * @param wh      :warehouse information which containes the objective zone to search.
     * @return :<code>Zone</code> object array created according to the parameter 
     * @throws ReadWriteException :Notifies if access with data failed.
     * @throws InvalidDefineException :Notifiese if such zone data cannot be found.
     </en>*/
    public Zone[] selectcount(String consignorCode, String itemCode, int height, WareHouse wh)
            throws ReadWriteException,
                InvalidDefineException
    {
        String whstno = wh.getStationNo();

        // 商品コードからソフトゾーンを取得する
        SoftZoneHandler shandl = new SoftZoneHandler(_conn);
        SoftZoneSearchKey skey = new SoftZoneSearchKey();
        skey.setJoin(SoftZone.SOFT_ZONE_ID, Item.SOFT_ZONE_ID);
        skey.setKey(Item.CONSIGNOR_CODE, consignorCode);
        skey.setKey(Item.ITEM_CODE, itemCode);
        SoftZone[] softZones = (SoftZone[])shandl.find(skey);

        if (ArrayUtil.isEmpty(softZones))
        {
            return null;
        }

        // ゾーンインスタンスの生成を行います。
        Zone[] zone = makeZone(wh.getStationNo(), softZones[0].getSoftZoneId());

        //<jp> 倉庫、荷高よりハードゾーンを取得</jp>
        //<en> Retrieves hard zone based on the warehouse and load height</en>
        HardZoneHandler hhandl = new HardZoneHandler(_conn);
        HardZoneSearchKey hkey = new HardZoneSearchKey();
        hkey.setWhStationNo(whstno);
        hkey.setHeight(height);
        HardZone[] hardzone = (HardZone[])hhandl.find(hkey);

        //<jp> 見つからない場合、全ハードゾーンで検索</jp>
        //<en> If there is no such data. search further by all hard zone.</en>
        if (hardzone.length == 0)
        {
            //<jp>hardZoneSearchKeyの初期化</jp>
            //<en>Initializing hardZoneSearchKey</en>
            hkey = new HardZoneSearchKey();
            hkey.setWhStationNo(whstno);
            hardzone = (HardZone[])hhandl.find(hkey);
            if (hardzone.length == 0)
            {
                //<jp> ゾーン定義が見つからない場合、例外</jp>
                //<en> If there is no definition of zone, throw exception:</en>
                //<jp> 6026060=指定された倉庫のゾーン定義がありません 倉庫={0}</jp>
                //<en> 6026060=There is no zone definition for the specified warehouse. Warehouse={0}</en>
                RmiMsgLogClient.write(WmsMessageFormatter.format(6026060, whstno), this.getClass().getName());
                throw new InvalidDefineException(WmsMessageFormatter.format(6026060, whstno));
            }

            List<Zone> zoneList = new ArrayList<Zone>();
            for (int i = 0; i < zone.length; i++)
            {
                for (int cnt = 0; cnt < hardzone.length; cnt++)
                {
                    Zone addZone = new Zone();
                    addZone.setDirection(zone[i].getDirection());
                    addZone.setHardZone(hardzone[cnt]);
                    addZone.setName(zone[i].getName());
                    addZone.setSoftZoneID(zone[i].getSoftZoneID());
                    addZone.setWHStationNo(zone[i].getWHStationNo());
                    zoneList.add(addZone);
                }
            }
            Zone[] arrayzone = (Zone[])zoneList.toArray(new Zone[0]);
            return arrayzone;
        }

        //<jp> priority項目を取得(ハードゾーンのheigtはユニークとなる)</jp>
        //<en> Retrieves the priority items (height of hard zone should be unique)</en>
        String pri = hardzone[0].getPriority();
        //もしPriorityに自分自身のIDが含まれていない場合のみ追加する。
        if (pri.indexOf(hardzone[0].getHardZoneId()) == -1)
        {
            pri = hardzone[0].getHardZoneId() + pri;
        }

        List<HardZone> hardZoneList = new ArrayList<HardZone>();

        //<jp>hardZoneSearchKeyの初期化</jp>
        //<en>Initializing hardZoneSearchKey</en>
        hkey = new HardZoneSearchKey();

        //<jp> 倉庫情報をセットしてハードゾーンを全件保持する。</jp>
        //<en> Sets the warehouse information adn the type(hard), and preserves all data of hard zone.</en>
        hkey.setWhStationNo(whstno);
        HardZone[] tmpzone = (HardZone[])hhandl.find(hkey);

        for (int i = 0; i < pri.length(); i++)
        {
            String tmpzn = pri.substring(i, i + 1);

            for (int l = 0; l < tmpzone.length; l++)
            {
                if (tmpzone[l].getHardZoneId().equals(Integer.parseInt(tmpzn)))
                {
                    hardZoneList.add(tmpzone[l]);
                }
            }
        }

        HardZone[] arrayhzone = (HardZone[])hardZoneList.toArray(new HardZone[0]);

        //<jp> ソフトゾーン情報にハードゾーン情報を付加する</jp>
        //<en> Put the soft zone data and hard zone data together.</en>
        List<Zone> zoneList = new ArrayList<Zone>();
        for (int i = 0; i < zone.length; i++)
        {
            for (int cnt = 0; cnt < arrayhzone.length; cnt++)
            {
                Zone addZone = new Zone();
                addZone.setDirection(zone[i].getDirection());
                addZone.setHardZone(arrayhzone[cnt]);
                addZone.setName(zone[i].getName());
                addZone.setSoftZoneID(zone[i].getSoftZoneID());
                addZone.setWHStationNo(zone[i].getWHStationNo());
                zoneList.add(addZone);
            }
        }

        Zone[] arrayzone = (Zone[])zoneList.toArray(new Zone[0]);

        return arrayzone;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**<jp>
     * ソフトゾーン優先順情報からゾーンを検索して、ソフトゾーン情報を作成します
     * 空棚検索方向はエリア情報より取得しセットします。
     * @param wh 検索する対象ゾーンが含まれる倉庫No.
     * @param softZone 検索する商品のソフトゾーン
     * @return <code>Zone</code>インスタンス
     * @throws InvalidDefineException 該当倉庫に対する空棚検索方向が定められていないときに通知します。
     * @throws ReadWriteException データアクセスに失敗した場合に通知されます。
     </jp>*/
    protected Zone[] makeZone(String wh, String softZone)
            throws InvalidDefineException,
                ReadWriteException
    {
        return makeZone(wh, softZone, true);
    }

    /**<jp>
     * ソフトゾーン情報を作成します
     * 空棚検索方向はエリア情報より取得しセットします。
     * @param wh 検索する対象ゾーンが含まれる倉庫No.
     * @param softZone 検索する商品のソフトゾーン
     * @param select ソフトゾーン優先順情報からゾーンを検索する場合はtrue, 指定ゾーンのみを使用する場合はfalse
     * @return <code>Zone</code>インスタンス
     * @throws InvalidDefineException 該当倉庫に対する空棚検索方向が定められていないときに通知します。
     * @throws ReadWriteException データアクセスに失敗した場合に通知されます。
     </jp>*/
    protected Zone[] makeZone(String wh, String softZone, boolean select)
            throws InvalidDefineException,
                ReadWriteException
    {
        // 空棚検索方向をエリア情報から取得する
        AreaHandler areaHandler = new AreaHandler(_conn);
        AreaSearchKey sKey = new AreaSearchKey();

        sKey.setVacantSearchTypeCollect();
        sKey.setWhstationNo(wh);

        Area area = new Area();

        try
        {
            area = (Area)areaHandler.findPrimary(sKey);
        }
        catch (NoPrimaryException e)
        {
            // FIXME 例外処理・ログ見直し
            throw new ReadWriteException(e);
        }

        // 検索方向が定義されているかチェックする
        if (area == null || StringUtil.isBlank(area.getVacantSearchType()))
        {
            // 6026080=定義エラー。指定された倉庫の空棚検索方向が定義されていません 倉庫={0}
            RmiMsgLogClient.write(WmsMessageFormatter.format(6026088, wh), this.getClass().getName());
            throw new InvalidDefineException(WmsMessageFormatter.format(6026088, wh));
        }

        // 空棚検索方向の定義が正しいかチェックする
        // 数値項目であること、ゾーン定義で定められた範囲内であること
        boolean correctDirection = true;
        int direction = -1;
        String strDirection = null;
        try
        {
            strDirection = area.getVacantSearchType();
            if (!SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_HP.equals(strDirection)
                    && !SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_OP.equals(strDirection)
                    && !SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_HP.equals(strDirection)
                    && !SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_OP.equals(strDirection))
            {
                correctDirection = false;
            }
            direction = Integer.parseInt(strDirection);
        }
        catch (NumberFormatException e)
        {
            correctDirection = false;
        }
        finally
        {
            if (!correctDirection)
            {
                // 6026089=定義エラー。指定された倉庫の空棚検索方向が不正です 倉庫={0} 空棚検索方向={1}
                RmiMsgLogClient.write(WmsMessageFormatter.format(6026089, wh, strDirection), this.getClass().getName());
                throw new InvalidDefineException(WmsMessageFormatter.format(6026089, wh, strDirection));
            }
        }

        Zone[] arrayzone = null;
        List<Zone> zoneList = new ArrayList<Zone>();

        // ソフトゾーン指定がない場合、または指定されたゾーンのみの場合
        if ((SoftZone.SOFT_ZONE_FREE.equals(softZone) )|| !select)
        {
            // 空のソフトゾーンを生成します。
            Zone zn = new Zone();

            // ソフトゾーンには指定されたソフトゾーンを設定します。
            zn.setSoftZoneID(softZone);
            // 空棚検索方向
            zn.setDirection(direction);

            zn.setWHStationNo(wh);

            zoneList.add(zn);
        }
        else
        {
            SoftZonePriorityHandler szHandler = new SoftZonePriorityHandler(_conn);
            SoftZonePrioritySearchKey szKey = new SoftZonePrioritySearchKey();

            szKey.setCollect(SoftZone.SOFT_ZONE_NAME);
            szKey.setPrioritySoftZoneCollect();
            szKey.setWhStationNo(wh);
            
            // ソフトゾーン管理なしの倉庫の場合
            if (szHandler.count(szKey) == 0)
            {
                // 空のソフトゾーンを生成します。
                Zone zn = new Zone();

                // ソフトゾーンには指定されたソフトゾーンを設定します。
                zn.setSoftZoneID(SoftZone.SOFT_ZONE_FREE);
                // 空棚検索方向
                zn.setDirection(direction);

                zn.setWHStationNo(wh);

                zoneList.add(zn);
            }
            else 
            {
                if (!SoftZone.SOFT_ZONE_FREE.equals(softZone) && !SoftZone.SOFT_ZONE_ALL.equals(softZone))
                {
                    szKey.setSoftZoneId(softZone);
                }
                szKey.setJoin(SoftZone.SOFT_ZONE_ID, SoftZonePriority.PRIORITY_SOFT_ZONE);
                szKey.setPriorityOrder(true);

                // ソフトゾーンを生成します。
                SoftZonePriority[] softZones = (SoftZonePriority[])szHandler.find(szKey);

                for (int i = 0; i < softZones.length; i++)
                {
                    Zone addZone = new Zone();
                    addZone.setDirection(direction);
                    addZone.setName((String)softZones[i].getValue(SoftZone.SOFT_ZONE_NAME));
                    addZone.setSoftZoneID(softZones[i].getPrioritySoftZone());
                    addZone.setWHStationNo(wh);
                    zoneList.add(addZone);
                }
            }
        }
        arrayzone = (Zone[])zoneList.toArray(new Zone[0]);

        return arrayzone;
    }
}
//end of class

