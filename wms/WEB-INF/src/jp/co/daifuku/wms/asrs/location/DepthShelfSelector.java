// $Id: DepthShelfSelector.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.dbhandler.ASShelfHandler;
import jp.co.daifuku.wms.asrs.dbhandler.DoubleDeepShelfHandler;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SoftZonePriority;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.handler.db.SQLErrors;

/**<jp>
 * ゾーンの中で手前/奥から空棚を探します。
 * ゾーンとは、倉庫を一定のルールに基づいて分割・管理するための単位です。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </jp>*/
/**<en>
 * This class searches the empty locations in the zone, from front/rear. 
 * Zone is a unit of devided area used for warehouse control according to the fixed rules.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 </en>*/
public class DepthShelfSelector
        implements ShelfSelector
{
    // Class fields --------------------------------------------------
    /**<jp>
     * アイル結合用の複数結合印
     </jp>*/
    public static final String DELM_AISLE = ":";


    // Class variables -----------------------------------------------
    /**<jp>
     * データベース接続用のコネクション
     </jp>*/
    /**<en>
     * Connection with database
     </en>*/
    protected Connection _conn;

    /**<jp>
     * ステートメントを管理する変数。
     </jp>*/
    /**<en>
     * Variable which controls the statement
     </en>*/
    private Statement _statement = null;

    /**<jp>
     * ゾーン検索用、ZoneSelector
     </jp>*/
    /**<en>
     * ZoneSelector for zone search
     </en>*/
    protected ZoneSelector _zoneSelector;
    
    // 2010/04/08 add start
    /**<jp>
     * 空棚数リストを保持するかどうかのフラグ
     </jp>*/
    private boolean _isSavePossibleList = false;
    
    /**<jp>
     * 空棚数リストを保持するためのリスト
     </jp>*/
    private ArrayList _saveListSPossible = null;
    
    /**<jp>
     * 保持している空棚数リストの荷幅(フリーアローケーション用)
     </jp>*/
    private int _saveWidth = 0;
    // 2010/04/08 add end

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
        return ("$Revision: 7996 $,$Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用のConnectionインスタンスを引数としてインスタンスを生成します。
     * トランザクション制御は内部で行っていませんので、外部でCommitする必要があります。
     * @param conn  データベース接続用の<code>Connection</code>
     * @param zs    ゾーン検索用のZoneSelector
     </jp>*/
    /**<en>
     * Generates the instance according to the parameter, the connection instance to connect with database.
     * As transaction control is not internally conducted, it is necessary to commit transaction control externally.
     * @param conn  <code>Connection</code> to connect with database
     * @param zs    ZoneSelector to connect with zone
     </en>*/
    public DepthShelfSelector(Connection conn, ZoneSelector zs)
    {
        _conn = conn;
        _zoneSelector = zs;
    }
    
    /**<jp>
     * データベース接続用のConnectionインスタンスを引数としてインスタンスを生成します。
     * トランザクション制御は内部で行っていませんので、外部でCommitする必要があります。
     * @param conn  データベース接続用の<code>Connection</code>
     * @param zs    ゾーン検索用のZoneSelector
     * @param noSaveFlg    空棚リストの保存フラグ
     </jp>*/
    /**<en>
     * Generates the instance according to the parameter, the connection instance to connect with database.
     * As transaction control is not internally conducted, it is necessary to commit transaction control externally.
     * @param conn  <code>Connection</code> to connect with database
     * @param zs    ZoneSelector to connect with zone
     * @param saveflg    NoSaveFlg
     </en>*/
    public DepthShelfSelector(Connection conn, ZoneSelector zs, boolean saveflg)
    {
        _conn = conn;
        _zoneSelector = zs;
        // 2010/04/08 add start
        _isSavePossibleList = saveflg;
        // 2010/04/08 add end
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * 指定されたパレットの現在位置、倉庫から棚を検索し、返します。
     * @param plt        空棚検索対象パレット
     * @param wh         空棚検索対象倉庫インスタンス
     * @return 検索棚
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Searches location from the specified warehouse and current position of pallet, then return.
     * @param plt        :pallet subject to empty search
     * @param wh         :instance of warehouse subject to empty search
     *                     if only the check will be done.
     * @return :location to search
     * @throws ReadWriteException :Notifies if it occured in database processing.
     * @throws ReadWriteException :Notifies if loading of route definition file failed.
     * @throws InvalidDefineException :Notifies if there are inconsistencies in table data.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public Shelf select(Pallet plt, WareHouse wh)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 2009/09/26 Y.Osawa UPD ST
        return select(plt, wh, true, false);
        // 2009/09/26 Y.Osawa UPD ED
    }

    /**<jp>
     * 指定されたパレットの現在位置、倉庫から棚を検索し、返します。
     * @param plt        空棚検索対象パレット
     * @param wh         空棚検索対象倉庫インスタンス
     * @param aisleNo    アイルNo
     * @param hardZoneId ハードゾーンID
     * @param softZoneId ソフトゾーンID
     * @return 検索棚
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    public Shelf select(Pallet plt, WareHouse wh, String aisleNo, String hardZoneId, String softZoneId)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        return null;
    }

    // 2009/09/26 Y.Osawa ADD ST
    /**<jp>
     * 指定されたパレットの現在位置、倉庫から棚を検索し、返します。
     * @param plt        空棚検索対象パレット
     * @param wh         空棚検索対象倉庫インスタンス
     * @param aisleNo    アイルNo
     * @param hardZoneId ハードゾーンID
     * @param softZoneId ソフトゾーンID
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return 検索棚
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    public Shelf select(Pallet plt, WareHouse wh, String aisleNo, String hardZoneId, String softZoneId, boolean alternativeLoc)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        return null;
    }
    // 2009/09/26 Y.Osawa ADD ED

    /**<jp>
     * 指定されたパレットの現在位置、倉庫から棚を検索し、返します。
     * @param plt        空棚検索対象パレット
     * @param wh         空棚検索対象倉庫インスタンス
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @return 検索棚
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Searches location from the specified warehouse and current position of pallet, then return.
     * @param plt        :pallet subject to empty search
     * @param wh         :instance of warehouse subject to empty search
     * @param empLocDeterm     :true if a empty location has been selected as destination and renewing the shelf information, or false
     *                     if only the check will be done.
     * @return :location to search
     * @throws ReadWriteException :Notifies if it occured in database processing.
     * @throws ReadWriteException :Notifies if loading of route definition file failed.
     * @throws InvalidDefineException :Notifies if there are inconsistencies in table data.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public Shelf select(Pallet plt, WareHouse wh, boolean empLocDeterm)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 2009/09/26 Y.Osawa UPD ST
        return select(plt, wh, empLocDeterm, false);
        // 2009/09/26 Y.Osawa UPD ED
    }

    // 2009/09/26 Y.Osawa ADD ST
    /**<jp>
     * 指定されたパレットの現在位置、倉庫から棚を検索し、返します。
     * @param plt        空棚検索対象パレット
     * @param wh         空棚検索対象倉庫インスタンス
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return 検索棚
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Searches location from the specified warehouse and current position of pallet, then return.
     * @param plt        :pallet subject to empty search
     * @param wh         :instance of warehouse subject to empty search
     * @param empLocDeterm     :true if a empty location has been selected as destination and renewing the shelf information, or false
     *                     if only the check will be done.
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return :location to search
     * @throws ReadWriteException :Notifies if it occured in database processing.
     * @throws ReadWriteException :Notifies if loading of route definition file failed.
     * @throws InvalidDefineException :Notifies if there are inconsistencies in table data.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public Shelf select(Pallet plt, WareHouse wh, boolean empLocDeterm, boolean alternativeLoc)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        try
        {
            Shelf tShelf;
            WareHouse tWH = (WareHouse)wh;

            // パレットおよび倉庫よりゾーン情報を取得
            Zone[] zone = _zoneSelector.select(plt, tWH);

            // ゾーン検索で見つからなかった場合は棚なしで返す。
            if (zone == null)
            {
                return null;
            }

            // 空棚検索優先順
            if (SystemDefine.LOCATION_SEARCH_AISLE.equals(tWH.getLocationSearchType()))
            {
                // アイル優先
                // 検索する方は配列でゾーンがくると仮定しているため入庫可能全ゾーンを引数に渡して検索する
                tShelf = findShelf(tWH, zone, plt, empLocDeterm, false, alternativeLoc);
                if (tShelf != null)
                {
                    // 棚が見つかった場合、Shelfインスタンスを返す。
                    return (tShelf);
                }
                else
                {
                    // 空棚なし
                    return (null);
                }
            }
            else
            {
                // ゾーン優先
                for (Zone zones : zone)
                {
                    Zone[] zoneList = new Zone[1];
                    zoneList[0] = zones;
                    // 指定されたゾーンを優先するため
                    tShelf = findShelf(tWH, zoneList, plt, empLocDeterm, false ,alternativeLoc);
                    if (tShelf != null)
                    {
                        // 棚が見つかった場合、Shelfインスタンスを返す。
                        return (tShelf);
                    }
                }
                // 空棚なし
                return (null);
            }

        }
        catch (NotFoundException e)
        {
            throw new ReadWriteException();
        }
    }


    /**<jp>
     * このクラスで利用する、<code>ZoneSelector</code>を設定します。
     * @param zs 設定する<code>ZoneSelector</code>
     </jp>*/
    /**<en>
     * Set <code>ZoneSelector</code> to use in this class.
     * @param zs :<code>ZoneSelector</code> to set
     </en>*/
    public void setZoneSelector(ZoneSelector zs)
    {
        _zoneSelector = zs;
    }

    /**<jp>
     * <code>ZoneSelector</code>を取得します。
     * @return 設定されている<code>ZoneSelector</code>
     </jp>*/
    /**<en>
     * Retrieves <code>ZoneSelector</code>.
     * @return :<code>ZoneSelector</code> to set
     </en>*/
    public ZoneSelector getZoneSelector()
    {
        return (_zoneSelector);
    }


    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 指定されたパレットの搬送元ステーションから空棚検索可能なアイルの一覧を取得し、そのアイルから空棚を取得します。
     * 空棚が見つからない場合、nullを返します。
     * @param wh         空棚検索対象倉庫インスタンス
     * @param targetZone 空棚検索対象ゾーンインスタンスの配列
     * @param plt        空棚検索対象パレットインスタンス
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @param rackToRack  空棚検索の手前棚検索時はtrue、それ以外はfalse
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return 検索棚
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Retrieves a list of aisle searchable for empty locations from teh sendinf stations of specified pallet, 
     * then retrieves the empty location from that aisle.
     * It returns null if there is no empty location.
     * @param wh         Instance of warehouse subject to empty location search
     * @param targetZone Zone instance array subject to empty location search
     * @param plt        Pallet instance subject to empty location search
     * @param empLocDeterm     :true if a empty location has been selected as destination and renewing the shelf information, or false
     *                     if only the check will be done.
     * @param rackToRack  空棚検索の手前棚検索時はtrue、それ以外はfalse
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return location to search
     * @throws ReadWriteException :Notifies if occured during the database processing.
     * @throws ReadWriteException :Notifies if loading of route definition file failed.
     * @throws InvalidDefineException :Notifies if there are inconsistencies in table data.
     * @throws NotFoundException      :Notifies if there is no such data.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    // 2009/09/26 Y.Osawa UPD ST
    protected Shelf findShelf(WareHouse wh, Zone[] targetZone, Pallet plt, boolean empLocDeterm, boolean rackToRack, boolean alternativeLoc)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException,
                LockTimeOutException
    // 2009/09/26 Y.Osawa UPD ED
    {
        //<jp> パレットの現在位置より搬送元ステーションのインスタンス生成</jp>
        //<en> Generates instance of sending station based on the current position of pallet.</en>
        Station st = null;
        ArrayList listSPossible = new ArrayList();
        ArrayList listCCarry = new ArrayList();
        Boolean blnCCarry = false;

        try
        {
            st = StationFactory.makeStation(_conn, plt.getCurrentStationNo());
        }
        catch (NotFoundException e)
        {
            throw new ReadWriteException();
        }

        //<jp> 搬送元ステーションより空棚検索可能なアイルの一覧を取得</jp>
        //<en> Retrieves the list of aisle searchable for hte empty location search from sending station.</en>
        AisleSelector asel = new AisleSelector(_conn, wh, st);

        //<jp> 再入庫される予定の搬送数を取得する</jp>
        listCCarry = getCarryRestoringCount(st.getWhStationNo(), plt);
        
        // 再入庫搬送データが存在する場合は空棚検索条件処理を行います
        if (listCCarry.size() != 0)
        {
            //<jp> パレットIDより空棚検索条件処理を行う搬送情報が存在するかチェック</jp>
            CarryInfoHandler carrH = new CarryInfoHandler(_conn);
            CarryInfoSearchKey carrykey = new CarryInfoSearchKey();
            carrykey.setPalletId(plt.getPalletId());
            carrykey.setRetrievalDetailCollect();
            carrykey.setCarryFlagCollect();
            carrykey.setCmdStatusCollect();
            carrykey.setRetrievalDetailGroup();
            carrykey.setCarryFlagGroup();
            carrykey.setCmdStatusGroup();
            carrykey.setRetrievalDetailOrder(false);
            CarryInfo[] carries = (CarryInfo[])carrH.find(carrykey);

            //<jp> 下記条件の場合は戻り入庫の搬送指示ではない可能性があるため空棚検索条件処理を行う</jp>
            //<jp> 1.パレットに該当する搬送情報が存在しない(荷姿検知なし新規入庫)</jp>
            //<jp> 2.出庫指示詳細が新規入庫のデータが存在する</jp>
            //<jp> 3.搬送区分が入庫、搬送状態が応答待ちまたは指示済みが存在する(二重格納等、二回目以降の空棚検索)</jp>
            if (carries.length == 0)
            {
                blnCCarry = true;
            }
            else
            {
                for (CarryInfo carry : carries)
                {
                    if ((CarryInfo.RETRIEVAL_DETAIL_UNKNOWN.equals(carry.getRetrievalDetail()))
                            || (CarryInfo.CARRY_FLAG_STORAGE.equals(carry.getCarryFlag()) && (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(carry.getCmdStatus()) || CarryInfo.CMD_STATUS_INSTRUCTION.equals(carry.getCmdStatus()))))
                    {
                        blnCCarry = true;
                        break;
                    }
                }
            }

            //<jp> 空棚検索条件処理を行う場合、以下処理を行う</jp>
            if (blnCCarry)
            {
// 2010/04/08 add start
                boolean isCreate = true;
                if (_isSavePossibleList && _saveListSPossible != null)
                {
                    isCreate = false;
                    // フリーアロケーション倉庫の場合、荷幅が違えば再取得する必要あり
                    if (WareHouse.FREE_ALLOCATION_ON.equals(wh.getFreeAllocationType()) && plt.getWidth() != _saveWidth)
                    {
                        isCreate = true;
                    }
                }
                
                if (isCreate)
                {
// 2010/04/08 add end
                    AisleSearchKey akey = new AisleSearchKey();
                    AisleHandler ahandl = new AisleHandler(_conn);
                    akey.setWhStationNo(st.getWhStationNo());
                    akey.setStationNoOrder(true);
                    Aisle[] wksts = (Aisle[])ahandl.find(akey);
    
                    for (int multiCount = 0; multiCount < wksts.length; multiCount++)
                    {
                        //<jp> ダブルディープ対応</jp>
                        if (Aisle.DOUBLE_DEEP_KIND_DOUBLE.equals(wksts[multiCount].getDoubleDeepKind()))
                        {
                            //<jp> 各アイルステーション・ハードゾーン毎の空棚数を取得する</jp>
                            DoubleDeepShelfHandler ddsHandle = new DoubleDeepShelfHandler(_conn);
                            listSPossible =
                                    ddsHandle.getVacantCountDoubleDeep(wksts[multiCount].getStationNo(), listSPossible);
                        }
                        else
                        {
                            //<jp> 各アイルステーション・ハードゾーン毎の空棚数を取得する</jp>
                            ASShelfHandler sHandle = new ASShelfHandler(_conn);
                            // フリーアロケーション倉庫（荷幅指定）
                            if (WareHouse.FREE_ALLOCATION_ON.equals(wh.getFreeAllocationType()) && plt.getWidth() != 0)
                            {
                                listSPossible =
                                        sHandle.getVacantCount(wksts[multiCount].getStationNo(), plt.getWidth(),
                                                listSPossible);
                            }
                            else
                            {
                                listSPossible = sHandle.getVacantCount(wksts[multiCount].getStationNo(), listSPossible);
                            }
                        }
                    }

                    //<jp> 対象ステーションより、入庫可能なアイルステーションを取得する
                    //     複数アイルステーションに対して入庫可能なパターンを検索し、空棚数情報テーブルに加算する</jp>
                    listSPossible = getObjectAisleStation(wh, listCCarry, listSPossible);

// 2010/04/08 add start
                    if (_isSavePossibleList)
                    {
                        // 空棚数リストを保持する場合
                        _saveListSPossible = new ArrayList();
                        for (int i = 0; i < listSPossible.size(); i++)
                        {
                            _saveListSPossible.add(listSPossible.get(i));
                        }
                        if (WareHouse.FREE_ALLOCATION_ON.equals(wh.getFreeAllocationType()))
                        {
                            _saveWidth = plt.getWidth();
                        }
                    }
                }
                else
                {
                    // 保持している空棚数リストを使用する
                    for (int i = 0; i < _saveListSPossible.size(); i++)
                    {
                        listSPossible.add(_saveListSPossible.get(i));
                    }
                }
// 2010/04/08 add end
                
                //<jp> 搬送中分（再入庫）、各入庫可能数から減算処理を行います。</jp>
                listSPossible = getPossibleQtySubtraction(listCCarry, listSPossible);
                
            }
        }

        // ダブルディープの最小確保空棚数
        int minVacantCnt = 2;
        if (rackToRack)
        {
            // 棚間移動でも搬送データが存在すれば二重格納か荷姿不一致の代替棚検索なので通常検索と同様2棚必要
            CarryInfoHandler carrH = new CarryInfoHandler(_conn);
            CarryInfoSearchKey carrykey = new CarryInfoSearchKey();
            carrykey.setPalletId(plt.getPalletId());
            carrykey.setCarryFlag(CarryInfo.CARRY_FLAG_RACK_TO_RACK);
            if (carrH.count(carrykey) == 0)
            {
                minVacantCnt = 1;
            }
        }

        //<jp> アイルの優先順に空棚検索を行う</jp>
        //<en> Searches the empty location according to the prioritized order of aisles.</en>
        while (asel.next())
        {
            Shelf tShelf = null;
            // フリーアロケーション倉庫
            if (WareHouse.FREE_ALLOCATION_ON.equals(wh.getFreeAllocationType()))
            {
                tShelf =
                        // 2009/09/26 Y.Osawa UPD ST
                        asel.findShelf(targetZone, blnCCarry, listSPossible, plt.getWidth(), empLocDeterm, minVacantCnt, alternativeLoc);
                        // 2009/09/26 Y.Osawa UPD ED
            }
            // フリーアロケーション倉庫以外
            else
            {
                // 2009/09/26 Y.Osawa UPD ST
                tShelf = asel.findShelf(targetZone, blnCCarry, listSPossible, empLocDeterm, minVacantCnt, alternativeLoc);
                // 2009/09/26 Y.Osawa UPD ED
            }

            if (tShelf != null)
            {
                //<jp> 棚確定、今回検索したアイルステーションNoを次回空棚検索では検索順の最後にする。</jp>
                //<en> Fixing the location; place the aisle station no. used in this search must be put to the last of </en>
                //<en> search order in the nezxt empty location search.</en>
                if (empLocDeterm)
                {
                    asel.determin();
                }
                return (tShelf);
            }
        }

        //<jp> 空棚無しの場合はnullを返す</jp>
        //<en> Retunrs null if there is no wmpty location.</en>
        return (null);
    }

    /**<jp>
     * 対象ステーションより入庫可能アイルステーションを獲得します
     * @param wh         空棚検索対象倉庫インスタンス
     * @param listCCarry 搬送中データ取得ステーション一覧データ
     * @param listPossible 空棚数アイル一覧データ
     * @return ArrayList 複数アイルステーション対象データ
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    protected ArrayList getObjectAisleStation(WareHouse wh, ArrayList listCCarry, ArrayList listPossible)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException,
                LockTimeOutException
    {
        ArrayList listParea = new ArrayList();
        Station st = null;

        // 取得したリスト領域を一時領域に格納する
        listParea = listPossible;

        // 取得した搬送中データのあるアイルステーション分、以下処理を行います
        for (int intCarryCount = 0; intCarryCount < listCCarry.size(); intCarryCount++)
        {
            try
            {
                String strStation = String.valueOf(listCCarry.get(intCarryCount));

                // ステーション情報の先頭位置を取得
                int intObjStart = strStation.indexOf("{");
                intObjStart = intObjStart + 1;
                // ステーション情報の終了位置を取得
                int intObjEnd = strStation.indexOf("}");

                // 対象ステーション取得
                st = StationFactory.makeStation(_conn, strStation.substring(intObjStart, intObjEnd));

                // アイル独立の場合は空棚数アイル一覧データに存在するはずなので追加しない
                if (!StringUtil.isBlank(st.getAisleStationNo()))
                {
                    continue;
                }
            }
            catch (NotFoundException e)
            {
                throw new ReadWriteException();
            }

            //<jp> 搬送元ステーションより空棚検索可能なアイルの一覧を取得</jp>
            //<en> Retrieves the list of aisle searchable for hte empty location search from sending station.</en>
            AisleSelector asel = new AisleSelector(_conn, wh, st);

            //<jp> アイルステーションの一覧及び、入庫可能数を取得する</jp>
            //<en> Searches the empty location according to the prioritized order of aisles.</en>
            // ステーション一覧格納
            String strAisleStationNo = "";
            // データ件数獲得
            int intOne = 0;

            while (asel.next())
            {
                // アイルステーション編集
                if (intOne == 0)
                {
                    strAisleStationNo = "{" + strAisleStationNo + asel.getStation() + DELM_AISLE;
                    intOne++;
                }
                else
                {
                    strAisleStationNo = strAisleStationNo + asel.getStation() + DELM_AISLE;
                }
            }
            // １件もデータが検出されなかった場合はNGとし、次データを検索する
            if (intOne == 0)
            {
                continue;
            }
            else
            {
                strAisleStationNo = strAisleStationNo + "}";
            }

            // 空棚数アイル一覧データに格納する
            Boolean blnFlg = true;
            for (int intPCount = 0; intPCount < listParea.size(); intPCount++)
            {
                // 空棚数アイル一覧データを１行取得
                String strAisle = String.valueOf(listParea.get(intPCount));

                // ステーション情報の先頭位置を取得
                int intStart = strAisle.indexOf("{");

                // ステーション情報の終了位置を取得
                int intEnd = strAisle.indexOf("}");
                intEnd = intEnd + 1;

                // 既に同じパターンデータが一覧データのテーブルに存在する場合、追加処理は行わない
                if (strAisleStationNo.equals(strAisle.substring(intStart, intEnd)))
                {
                    blnFlg = false;
                    break;
                }
            }

            // 上記検索にて空棚アイルステーションに重複するパターンデータがない場合、追加します
            if (blnFlg == true)
            {
                // ソフトゾーンの種類を検索します
                ShelfSearchKey skey = new ShelfSearchKey();
                ShelfHandler shandl = new ShelfHandler(_conn);
                skey.setWhStationNo(st.getWhStationNo());
                skey.setSoftZoneIdCollect();
                skey.setSoftZoneIdGroup();
                skey.setSoftZoneIdOrder(true);
                Shelf[] wkSoft = (Shelf[])shandl.find(skey);

                // ハードゾーンの種類を検索します
                HardZoneSearchKey hkey = new HardZoneSearchKey();
                HardZoneHandler hhandl = new HardZoneHandler(_conn);
                hkey.setWhStationNo(st.getWhStationNo());
                hkey.setHardZoneIdOrder(true);
                HardZone[] wkHard = (HardZone[])hhandl.find(hkey);

                for (int softZoneCount = 0; softZoneCount < wkSoft.length; softZoneCount++)
                {
                    // ソフトゾーン編集格納
                    String strSoftZoneId = wkSoft[softZoneCount].getSoftZoneId();

                    for (int zoneCount = 0; zoneCount < wkHard.length; zoneCount++)
                    {
                        // ハードゾーン編集格納
                        String strHarZoneId = wkHard[zoneCount].getHardZoneId();
                        // 入庫可能数格納
                        int intPossibleQty = 0;

                        // 入庫可能数編集
                        for (int intPCount = 0; intPCount < listParea.size(); intPCount++)
                        {
                            // 空棚数アイル一覧データを１行取得
                            String str = String.valueOf(listParea.get(intPCount));

                            // ステーション情報の先頭位置を取得
                            int intStationStart = str.indexOf("{");
                            intStationStart = intStationStart + 1;
                            // ステーション情報の終了位置を取得
                            int intStationEnd = str.indexOf("}");
                            // ソフトゾーン情報の先頭位置を取得
                            int intSoftZoneStart = str.indexOf("[");
                            intSoftZoneStart = intSoftZoneStart + 1;
                            // ソフトゾーン情報の終了位置を取得
                            int intSoftZoneEnd = str.indexOf("]");
                            // ハードゾーン情報の先頭位置を取得
                            int intHardZoneStart = str.indexOf("<");
                            intHardZoneStart = intHardZoneStart + 1;
                            // ハードゾーン情報の終了位置を取得
                            int intHardZoneEnd = str.indexOf(">");
                            // 入庫格納数情報の開始位置を取得
                            int intQtyStart = str.indexOf("(");
                            intQtyStart = intQtyStart + 1;
                            // 入庫格納数情報の終了位置を取得
                            int intQtyEnd = str.indexOf(")");

                            // 検索したパターンと同じパターンの入庫数を取得します
                            if ((strAisleStationNo.indexOf(str.substring(intStationStart, intStationEnd)) >= 0)
                                    && (strSoftZoneId.indexOf(str.substring(intSoftZoneStart, intSoftZoneEnd)) >= 0)
                                    && (strHarZoneId.indexOf(str.substring(intHardZoneStart, intHardZoneEnd)) >= 0))
                            {
                                intPossibleQty =
                                        intPossibleQty + Integer.valueOf(str.substring(intQtyStart, intQtyEnd));
                            }
                        }

                        // リストテーブルデータ編集
                        listPossible.add(strAisleStationNo + "," + "[" + strSoftZoneId + "]" + "," + "<" + strHarZoneId
                                + ">" + "," + "(" + intPossibleQty + ")");
                    }
                }
            }
        }

        //<jp> 取得した追加分のアイルステーション情報テーブルを返します</jp>
        return listPossible;
    }

    /**<jp>
     * 搬送中分（再入庫）、各入庫可能数から減算処理を行います
     * @param listCCarry 搬送中データ取得ステーション一覧データ
     * @param listPossible 空棚数アイル一覧データ
     * @return ArrayList 複数アイルステーション対象データ
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    protected ArrayList getPossibleQtySubtraction(ArrayList listCCarry, ArrayList listPossible)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException,
                LockTimeOutException
    {
        try
        {
            // 取得した搬送中データのあるアイルステーション分、以下処理を行います
            for (int intCarryCount = 0; intCarryCount < listCCarry.size(); intCarryCount++)
            {
                // 搬送中検索にて獲得したデータを１行取得
                String strCarry = String.valueOf(listCCarry.get(intCarryCount));

                // ステーション情報の先頭位置を取得
                int intStationStart = strCarry.indexOf("{");
                intStationStart = intStationStart + 1;
                // ステーション情報の終了位置を取得
                int intStationEnd = strCarry.indexOf("}");
                // ソフトゾーン情報の先頭位置を取得
                int intSoftZoneStart = strCarry.indexOf("[");
                intSoftZoneStart = intSoftZoneStart + 1;
                // ソフトゾーン情報の終了位置を取得
                int intSoftZoneEnd = strCarry.indexOf("]");
                // ハードゾーン情報の先頭位置を取得
                int intHardZoneStart = strCarry.indexOf("<");
                intHardZoneStart = intHardZoneStart + 1;
                // ハードゾーン情報の終了位置を取得
                int intHardZoneEnd = strCarry.indexOf(">");
                // 出庫指示詳細情報の開始位置を取得
                int intRdetailStart = strCarry.indexOf("*");
                intRdetailStart = intRdetailStart + 1;
                // 出庫指示詳細情報の終了位置を取得
                int intRdetailEnd = strCarry.indexOf("+");
                // 搬送数情報の開始位置を取得
                int intQtyStart = strCarry.indexOf("(");
                intQtyStart = intQtyStart + 1;
                // 搬送数情報の終了位置を取得
                int intQtyEnd = strCarry.indexOf(")");

                // 各項目を取得
                // ステーションNo
                String strStation = strCarry.substring(intStationStart, intStationEnd);
                // 指定ソフトゾーンNo
                String strSoftZone = strCarry.substring(intSoftZoneStart, intSoftZoneEnd);
                // 指定ハードゾーンNo
                String strHardZone = strCarry.substring(intHardZoneStart, intHardZoneEnd);
                // 出庫指示詳細
                String strRdetail = strCarry.substring(intRdetailStart, intRdetailEnd);
                // 搬送数
                int intCarryQty = Integer.valueOf(strCarry.substring(intQtyStart, intQtyEnd));

                // ステーション情報を検索します
                StationSearchKey skey = new StationSearchKey();
                StationHandler shandl = new StationHandler(_conn);
                skey.setStationNo(strStation);
                Station wksts = (Station)shandl.findPrimary(skey);

                HardZone[] wkhzone = null;
                // ピッキング作業の場合、搬送中データのゾーンと一致する行を検索
                if (strRdetail.equals(CarryInfo.RETRIEVAL_DETAIL_PICKING))
                {
                    wkhzone = new HardZone[1];
                    wkhzone[0] = new HardZone();
                    wkhzone[0].setHardZoneId(strHardZone);
                }
                // 積増入庫、新規入庫作業の場合、荷姿の大きい順にゾーンを検索
                else
                {
                    // ハードゾーンの種類を検索します
                    HardZoneSearchKey hkey = new HardZoneSearchKey();
                    HardZoneHandler hhandl = new HardZoneHandler(_conn);
                    hkey.setWhStationNo(wksts.getWhStationNo());
                    hkey.setHeightOrder(false);
                    wkhzone = (HardZone[])hhandl.find(hkey);
                }

                SoftZonePriority[] wkszone = null;
                // 2010/08/06 modify start フリーソフトゾーン指定の場合
                if (SystemDefine.SOFT_ZONE_ALL.equals(strSoftZone))
                {
                    // フリーソフトゾーンの場合は、ソフトゾーンIDをセット
                    wkszone = new SoftZonePriority[1];
                    wkszone[0] = new SoftZonePriority();
                    wkszone[0].setWhStationNo(wksts.getWhStationNo());
                    wkszone[0].setSoftZoneId(SystemDefine.SOFT_ZONE_ALL);
                    wkszone[0].setPrioritySoftZone(SystemDefine.SOFT_ZONE_ALL);
                    wkszone[0].setPriority(1);
                }
                // 新規入庫以外場合、搬送中データのゾーンと一致する行を検索
                else if (!strRdetail.equals(CarryInfo.RETRIEVAL_DETAIL_UNKNOWN))
                // 2010/08/06 modify end
                {
                    wkszone = new SoftZonePriority[1];
                    wkszone[0] = new SoftZonePriority();
                    wkszone[0].setWhStationNo(wksts.getWhStationNo());
                    wkszone[0].setSoftZoneId(strSoftZone);
                    wkszone[0].setPrioritySoftZone(strSoftZone);
                    wkszone[0].setPriority(1);
                }
                // ソフトゾーン指定の場合、ソフトゾーンの優先順にゾーンを検索
                else
                {
                    // ソフトゾーンの種類を検索します
                    SoftZonePrioritySearchKey szkey = new SoftZonePrioritySearchKey();
                    SoftZonePriorityHandler szhandl = new SoftZonePriorityHandler(_conn);
                    szkey.setWhStationNo(wksts.getWhStationNo());
                    // 2010/08/06 modify start
//                    if (!SystemDefine.SOFT_ZONE_ALL.equals(strSoftZone))
//                    {
                        szkey.setSoftZoneId(strSoftZone);
//                    }
                    // 2010/08/06 modify end
                    szkey.setPriorityOrder(true);
                    wkszone = (SoftZonePriority[])szhandl.find(szkey);
                }

                boolean nextZone = false;
                int wkCarryQty = intCarryQty;
                // ソフトゾーンをデータ分、検索します
                for (int szoneCount = 0; szoneCount < wkszone.length; szoneCount++)
                {
                    // ハードゾーンをデータ分、検索します
                    for (int zoneCount = 0; zoneCount < wkhzone.length; zoneCount++)
                    {
                        // 空棚数アイルデータ一覧をデータ分、検索します
                        boolean subtraction = false;
                        for (int intPCount = 0; intPCount < listPossible.size(); intPCount++)
                        {
                            // 空棚数アイル一覧データを1行取得
                            String strPossible = String.valueOf(listPossible.get(intPCount));

                            // ステーション情報の先頭位置を取得
                            int intPoStationStart = strPossible.indexOf("{");
                            intPoStationStart = intPoStationStart + 1;
                            // ステーション情報の終了位置を取得
                            int intPoStationEnd = strPossible.indexOf("}");
                            // ソフトゾーン情報の先頭位置を取得
                            int intPoSoftZoneStart = strPossible.indexOf("[");
                            intPoSoftZoneStart = intPoSoftZoneStart + 1;
                            // ソフトゾーン情報の終了位置を取得
                            int intPoSoftZoneEnd = strPossible.indexOf("]");
                            // ハードゾーン情報の先頭位置を取得
                            int intPoHardZoneStart = strPossible.indexOf("<");
                            intPoHardZoneStart = intPoHardZoneStart + 1;
                            // ハードゾーン情報の終了位置を取得
                            int intPoHardZoneEnd = strPossible.indexOf(">");
                            // 入庫格納数情報の開始位置を取得
                            int intPoQtyStart = strPossible.indexOf("(");
                            intPoQtyStart = intPoQtyStart + 1;
                            // 入庫格納数情報の終了位置を取得
                            int intPoQtyEnd = strPossible.indexOf(")");

                            String checkAisle = null;
                            // アイル独立の場合、搬送中データのアイルと一致する行を検索
                            if (!StringUtil.isBlank(wksts.getAisleStationNo()))
                            {
                                checkAisle = wksts.getAisleStationNo();
                            }
                            // アイル結合の場合、複数結合印の存在する行を検索
                            else
                            {
                                checkAisle = DELM_AISLE;
                            }

                            // 搬送中データテーブルの「ステーションのアイル」が一致する行を検索し
                            // 搬送数分の減算を行います
                            // <一致条件:アイル>
                            // アイル独立の場合、今回搬送中の対象ステーションからステーション情報を検索して取得したアイルステーションが存在
                            // アイル結合の場合、複数結合印(:)が存在
                            // <一致条件:ゾーン>
                            // ピッキング出庫の場合は搬送中データのゾーンが存在
                            // 積増入庫、新規入庫の場合はハードゾーン情報から取得したMAX荷姿のゾーンが存在
                            if ((strPossible.substring(intPoStationStart, intPoStationEnd).indexOf(checkAisle) >= 0)
                                    // 2010/08/06 modify start <一致条件:ゾーン>にフリーソフトゾーン指定の場合を追加
                                    && (SystemDefine.SOFT_ZONE_ALL.equals(wkszone[szoneCount].getPrioritySoftZone())
                                            || (strPossible.substring(intPoSoftZoneStart, intPoSoftZoneEnd).indexOf(
                                                    wkszone[szoneCount].getPrioritySoftZone()) >= 0))
                                    // 2010/08/06 modify end
                                    && (strPossible.substring(intPoHardZoneStart, intPoHardZoneEnd).indexOf(
                                            wkhzone[zoneCount].getHardZoneId()) >= 0))
                            {
                                // アイル独立STの搬送中データを減算する場合、アイル独立空棚リストから減算できていないアイル結合空棚リストは減算しない
                                if (!checkAisle.equals(DELM_AISLE))
                                {
                                    if (!strPossible.substring(intPoStationStart, intPoStationEnd).equals(checkAisle)
                                            && subtraction == false)
                                    {
                                        continue;
                                    }
                                }

                                // 今回検索の空棚数から搬送中データ分を減算します
                                int intSubtractionQty =
                                        Integer.valueOf(strPossible.substring(intPoQtyStart, intPoQtyEnd));
                                if (intSubtractionQty > 0)
                                {
                                    // 今回検索の空棚数から搬送中データ分を減算します
                                    intSubtractionQty = intSubtractionQty - intCarryQty;
                                    // 空棚数より搬送数が大きい場合
                                    if (intSubtractionQty < 0)
                                    {
                                        // 次ゾーン検索分の搬送数
                                        wkCarryQty = -1 * intSubtractionQty;
                                        // アイル独立で減算できた分をアイル結合減算分とする
                                        if (!checkAisle.equals(DELM_AISLE))
                                        {
                                            intCarryQty = intCarryQty + intSubtractionQty;
                                        }
                                        intSubtractionQty = 0;
                                    }
                                    // 空棚数から搬送数分減算できた場合
                                    else
                                    {
                                        // アイル結合STの搬送データ減算時、減算できた場合にフラグON
                                        if (checkAisle.equals(DELM_AISLE))
                                        {
                                            nextZone = true;
                                        }
                                        // アイル独立STの搬送データ減算時、アイル独立空棚リストから減算できた場合にフラグON
                                        else
                                        {
                                            if (strPossible.substring(intPoStationStart, intPoStationEnd).equals(
                                                    checkAisle))
                                            {
                                                nextZone = true;
                                            }
                                        }
                                    }

                                    subtraction = true;
                                    // 上記減算結果より行を新しく編集し直します
                                    String strNewPossible =
                                            "{" + strPossible.substring(intPoStationStart, intPoStationEnd) + "}" + ","
                                                    + "[" + strPossible.substring(intPoSoftZoneStart, intPoSoftZoneEnd)
                                                    + "]" + "," + "<"
                                                    + strPossible.substring(intPoHardZoneStart, intPoHardZoneEnd) + ">"
                                                    + "," + "(" + intSubtractionQty + ")";

                                    // 今回検索した空棚数テーブルの行を上記減算結果から新しいデータに置き換える
                                    listPossible.set(intPCount, strNewPossible);
                                }
                            }
                        }

                        // 空棚数減算に成功した場合は次のゾーンを検索しない
                        if (nextZone)
                        {
                            break;
                        }
                        else
                        {
                            // 残った搬送数分、次のゾーンを検索する
                            intCarryQty = wkCarryQty;
                        }
                    }
                    // 空棚数減算に成功した場合は次のゾーンを検索しない 
                    if (nextZone)
                    {
                        break;
                    }
                }
            }

            //<jp> 取得した追加分のアイルステーション情報テーブルを返します</jp>
            return listPossible;
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException();
        }
    }

    /**<jp>
     * 再入庫される予定の搬送数を取得する
     * @param pWhStationNo ステーションNo
     * @param plt        空棚検索対象パレットインスタンス
     * @return ArrayList 再入庫予定の搬送数テーブル
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws ReadWriteException ルート定義ファイルの読込みに失敗した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    protected ArrayList getCarryRestoringCount(String pWhStationNo, Pallet plt)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException,
                LockTimeOutException
    {
        ArrayList listCcount = new ArrayList();
        ResultSet rset = null;
        String sqlstring = null;

        try
        {
            // 2010/08/06 modify start ソフトゾーン、ハードゾーンはパレットから取得するように修正
            String fmtSQL =
                    "SELECT DNCARRYINFO.DEST_STATION_NO STATION_NO, "
                            + " DNPALLET.SOFT_ZONE_ID SOFT_ZONE_ID, "
//                            + " DMSHELF.SOFT_ZONE_ID SOFT_ZONE_ID, "
                            + " DMSHELF.HARD_ZONE_ID HARD_ZONE_ID, "
                            + " DNCARRYINFO.RETRIEVAL_DETAIL RETRIEVAL_DETAIL, "
                            + " DNCARRYINFO.AISLE_STATION_NO AISLE_STATION_NO, "
                            + " COUNT(*) CARRY_COUNT "
                            + " FROM DNCARRYINFO, DMSHELF, DNPALLET "
//                            + " FROM DNCARRYINFO, DMSHELF "
                            + " WHERE DNCARRYINFO.CARRY_FLAG = "
                            + DBFormat.format(CarryInfo.CARRY_FLAG_RETRIEVAL)
                            + " AND   DNCARRYINFO.CMD_STATUS = "
                            + DBFormat.format(CarryInfo.CMD_STATUS_COMP_RETRIEVAL)
                            + " AND   DNCARRYINFO.RETRIEVAL_DETAIL IN ("
                            + DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_PICKING)
                            + ","
                            + DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING)
                            + ") "
                            + " AND   DNCARRYINFO.RESTORING_FLAG = "
                            + DBFormat.format(CarryInfo.RESTORING_FLAG_NOT_SAME_LOC)
                            + " AND   DMSHELF.WH_STATION_NO = "
                            + DBFormat.format(pWhStationNo)
                            + " AND   DNCARRYINFO.PALLET_ID = DNPALLET.PALLET_ID "
                            + " AND   DNCARRYINFO.RETRIEVAL_STATION_NO = DMSHELF.STATION_NO "
                            + " GROUP BY DNCARRYINFO.AISLE_STATION_NO, DNCARRYINFO.DEST_STATION_NO, DNPALLET.SOFT_ZONE_ID, DMSHELF.HARD_ZONE_ID, DNCARRYINFO.RETRIEVAL_DETAIL "
//                            + " GROUP BY DNCARRYINFO.AISLE_STATION_NO, DNCARRYINFO.DEST_STATION_NO, DMSHELF.SOFT_ZONE_ID, DMSHELF.HARD_ZONE_ID, DNCARRYINFO.RETRIEVAL_DETAIL "
                            + " UNION "
                            + " SELECT DNCARRYINFO.SOURCE_STATION_NO STATION_NO, "
                            + " DNPALLET.SOFT_ZONE_ID SOFT_ZONE_ID, "
//                            + " DMSHELF.SOFT_ZONE_ID SOFT_ZONE_ID, "
                            + " DMSHELF.HARD_ZONE_ID HARD_ZONE_ID, "
                            + " DNCARRYINFO.RETRIEVAL_DETAIL RETRIEVAL_DETAIL, "
                            + " DNCARRYINFO.AISLE_STATION_NO AISLE_STATION_NO, "
                            + " COUNT(*) CARRY_COUNT "
                            + " FROM DNCARRYINFO, DMSHELF, DNPALLET "
//                            + " FROM DNCARRYINFO, DMSHELF "
                            + " WHERE DNCARRYINFO.CARRY_FLAG = "
                            + DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE)
                            + " AND   (DNCARRYINFO.CMD_STATUS = "
                            + DBFormat.format(CarryInfo.CMD_STATUS_ARRIVAL)
                            + " OR   DNCARRYINFO.CMD_STATUS = "
                            + DBFormat.format(CarryInfo.CMD_STATUS_START)
                            + ") "
                            + " AND   DNCARRYINFO.RETRIEVAL_DETAIL IN ("
                            + DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_PICKING)
                            + ","
                            + DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING)
                            + ") "
                            + " AND   DNCARRYINFO.RESTORING_FLAG = "
                            + DBFormat.format(CarryInfo.RESTORING_FLAG_NOT_SAME_LOC)
                            + " AND   DNPALLET.WH_STATION_NO = "
//                            + " AND   DMSHELF.WH_STATION_NO = "
                            + DBFormat.format(pWhStationNo)
                            + " AND   DNCARRYINFO.PALLET_ID = DNPALLET.PALLET_ID "
                            + " AND   DNCARRYINFO.RETRIEVAL_STATION_NO = DMSHELF.STATION_NO "
                            + " GROUP BY DNCARRYINFO.AISLE_STATION_NO, DNCARRYINFO.SOURCE_STATION_NO, DNPALLET.SOFT_ZONE_ID, DMSHELF.HARD_ZONE_ID, DNCARRYINFO.RETRIEVAL_DETAIL "
//                            + " GROUP BY DNCARRYINFO.AISLE_STATION_NO, DNCARRYINFO.SOURCE_STATION_NO, DMSHELF.SOFT_ZONE_ID, DMSHELF.HARD_ZONE_ID, DNCARRYINFO.RETRIEVAL_DETAIL "
                            + " UNION "
                            + " SELECT DNCARRYINFO.SOURCE_STATION_NO STATION_NO, "
                            + " DNPALLET.SOFT_ZONE_ID SOFT_ZONE_ID, "
                            + " '0' HARD_ZONE_ID, "
                            + " DNCARRYINFO.RETRIEVAL_DETAIL RETRIEVAL_DETAIL, "
                            + " DNCARRYINFO.AISLE_STATION_NO AISLE_STATION_NO, "
                            + " COUNT(*) CARRY_COUNT "
                            + " FROM DNCARRYINFO, DNPALLET "
                            + " WHERE DNCARRYINFO.CARRY_FLAG = "
                            + DBFormat.format(CarryInfo.CARRY_FLAG_STORAGE)
                            + " AND   DNCARRYINFO.RETRIEVAL_DETAIL = "
                            + DBFormat.format(CarryInfo.RETRIEVAL_DETAIL_UNKNOWN)
                            + " AND   DNCARRYINFO.DEST_STATION_NO = "
                            + DBFormat.format(pWhStationNo)
                            + " AND   DNCARRYINFO.PALLET_ID < "
                            + DBFormat.format(plt.getPalletId())
                            + " AND   DNCARRYINFO.PALLET_ID = DNPALLET.PALLET_ID "
                            + " GROUP BY DNCARRYINFO.AISLE_STATION_NO, DNCARRYINFO.SOURCE_STATION_NO, DNCARRYINFO.RETRIEVAL_DETAIL, DNPALLET.SOFT_ZONE_ID"
                            + " ORDER BY AISLE_STATION_NO, RETRIEVAL_DETAIL, STATION_NO, SOFT_ZONE_ID, HARD_ZONE_ID  ";
            //        SELECT
            //            DNCARRYINFO.DEST_STATION_NO STATION_NO
            //            ,DNPALLET.SOFT_ZONE_ID SOFT_ZONE_ID
            //            ,DMHARDZONE.HARD_ZONE_ID HARD_ZONE_ID
            //            ,DNCARRYINFO.RETRIEVAL_DETAIL RETRIEVAL_DETAIL
            //            ,DNCARRYINFO.AISLE_STATION_NO AISLE_STATION_NO
            //            ,COUNT( * ) CARRY_COUNT
            //        FROM
            //            DNCARRYINFO
            //            ,DNPALLET
            //            ,DMHARDZONE
            //        WHERE
            //            DNCARRYINFO.CARRY_FLAG = '2'
            //            AND DNCARRYINFO.CMD_STATUS = '5'
            //            AND DNCARRYINFO.RETRIEVAL_DETAIL IN (
            //                '2'
            //                ,'3'
            //            )
            //            AND DNCARRYINFO.RESTORING_FLAG = '0'
            //            AND DNPALLET.WH_STATION_NO = '9000'
            //            AND DNCARRYINFO.PALLET_ID = DNPALLET.PALLET_ID
            //            AND DMHARDZONE.WH_STATION_NO = DNPALLET.WH_STATION_NO
            //            AND DMHARDZONE.HEIGHT = DNPALLET.HEIGHT
            //        GROUP BY
            //            DNCARRYINFO.AISLE_STATION_NO
            //            ,DNCARRYINFO.DEST_STATION_NO
            //            ,DNPALLET.SOFT_ZONE_ID
            //            ,DMHARDZONE.HARD_ZONE_ID
            //            ,DNCARRYINFO.RETRIEVAL_DETAIL
            //    UNION
            //    SELECT
            //            DNCARRYINFO.SOURCE_STATION_NO STATION_NO
            //            ,DNPALLET.SOFT_ZONE_ID SOFT_ZONE_ID
            //            ,DMHARDZONE.HARD_ZONE_ID HARD_ZONE_ID
            //            ,DNCARRYINFO.RETRIEVAL_DETAIL RETRIEVAL_DETAIL
            //            ,DNCARRYINFO.AISLE_STATION_NO AISLE_STATION_NO
            //            ,COUNT( * ) CARRY_COUNT
            //        FROM
            //            DNCARRYINFO
            //            ,DNPALLET
            //            ,DMHARDZONE
            //        WHERE
            //            DNCARRYINFO.CARRY_FLAG = '1'
            //            AND
            //            (
            //                DNCARRYINFO.CMD_STATUS = '6'
            //                OR DNCARRYINFO.CMD_STATUS = '1'
            //            )
            //            AND DNCARRYINFO.RETRIEVAL_DETAIL IN (
            //                '2'
            //                ,'3'
            //            )
            //            AND DNCARRYINFO.RESTORING_FLAG = '0'
            //            AND DNPALLET.WH_STATION_NO = '9000'
            //            AND DNCARRYINFO.PALLET_ID = DNPALLET.PALLET_ID
            //            AND DMHARDZONE.WH_STATION_NO = DNPALLET.WH_STATION_NO
            //            AND DMHARDZONE.HEIGHT = DNPALLET.HEIGHT
            //        GROUP BY
            //            DNCARRYINFO.AISLE_STATION_NO
            //            ,DNCARRYINFO.SOURCE_STATION_NO
            //            ,DNPALLET.SOFT_ZONE_ID
            //            ,DMHARDZONE.HARD_ZONE_ID
            //            ,DNCARRYINFO.RETRIEVAL_DETAIL
            //    UNION
            //    SELECT
            //            DNCARRYINFO.SOURCE_STATION_NO STATION_NO
            //            ,DNPALLET.SOFT_ZONE_ID SOFT_ZONE_ID
            //            ,'0' HARD_ZONE_ID
            //            ,DNCARRYINFO.RETRIEVAL_DETAIL RETRIEVAL_DETAIL
            //            ,DNCARRYINFO.AISLE_STATION_NO AISLE_STATION_NO
            //            ,COUNT( * ) CARRY_COUNT
            //        FROM
            //            DNCARRYINFO
            //            ,DNPALLET
            //        WHERE
            //            DNCARRYINFO.CARRY_FLAG = '1'
            //            AND DNCARRYINFO.RETRIEVAL_DETAIL = '9'
            //            AND DNCARRYINFO.DEST_STATION_NO = '9000'
            //            AND DNCARRYINFO.PALLET_ID < '00000643'
            //            AND DNCARRYINFO.PALLET_ID = DNPALLET.PALLET_ID
            //        GROUP BY
            //            DNCARRYINFO.AISLE_STATION_NO
            //            ,DNCARRYINFO.SOURCE_STATION_NO
            //            ,DNCARRYINFO.RETRIEVAL_DETAIL
            //            ,DNPALLET.SOFT_ZONE_ID
            //        ORDER BY
            //            AISLE_STATION_NO
            //            ,RETRIEVAL_DETAIL
            //            ,STATION_NO
            //            ,SOFT_ZONE_ID
            //            ,HARD_ZONE_ID
            // 2010/08/06 modify end ソフトゾーンはパレットから取得するように修正

            Object[] fmtObj = new Object[1];
            sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

            //queryを実行し、結果セットの先頭データより結果表のインスタンスを生成する。
            rset = executeSQL(sqlstring, true);

            while (rset.next())
            {
                // 上記条件より取得した各ステーション、指定ゾーン、出庫指示詳細の括り搬送数データを格納します。
                listCcount.add("{" + rset.getString("STATION_NO") + "}" + "," + "[" + rset.getString("SOFT_ZONE_ID")
                        + "]" + "," + "<" + rset.getString("HARD_ZONE_ID") + ">" + "," + "*"
                        + rset.getString("RETRIEVAL_DETAIL") + "+" + "," + "(" + rset.getInt("CARRY_COUNT") + ")");
            }

        }
        catch (SQLException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (NotFoundException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        catch (DataExistsException e)
        {
            //Findなので、起こらないはず
            e.printStackTrace();
            throw new ReadWriteException();
        }
        finally
        {
            try
            {
                if (rset != null)
                {
                    rset.close();
                    rset = null;
                }

                closeStatement();
            }
            catch (SQLException e)
            {
                //<jp>6126001 = データベースエラーが発生しました。{0}</jp>
                //<en>6126001 = Database error occured. {0}</en>
                RmiMsgLogClient.write(new TraceHandler(6126001, e), this.getClass().getName());
                //<jp>ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。</jp>
                //<en>Here, the ReadWriteException will be thrown with an error message.</en>
                throw (new ReadWriteException());
            }
        }

        //<jp> 取得した搬送数の情報を格納します。</jp>
        return listCcount;
    }

    /**<jp>
     * インスタンス変数であるwStatementをクローズします。
     * executeSQLメソッドで生成されたカーソルはこのメソッドを呼び出し、必ずクローズする必要があります。
     </jp>*/
    /**<en>
     * Close the wStatement, which is the instance variable.
     * The cursor generated by executeSQL method must call this method to close.
     </en>*/
    protected void closeStatement()
    {
        try
        {
            if (_statement != null)
            {
                _statement.close();
            }
            _statement = null;
        }
        catch (SQLException e)
        {
            //<jp>6006002 = データベースエラーが発生しました。{0}</jp>
            //<en>6006002 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6006002, e), "ASShelfHandler");
        }
        catch (NullPointerException e)
        {
            //<jp> エラーログの出力処理も行う。</jp>
            //<en> Also perform the outputting og error log.</en>
            Object[] tObj = new Object[1];
            if (_statement != null)
            {
                tObj[0] = String.valueOf(_statement);
            }
            else
            {
                tObj[0] = "null";
            }
            RmiMsgLogClient.write(6016066, LogMessage.F_ERROR, "ASShelfHandler", tObj);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**<jp>
     * SQL文を受け取って、実行します。
     * @param sqlstr 実行するSQL文
     * @param query 問い合わせの場合は true
     * @return 結果の<code>ResultSet</code> それ以外はnull
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException 実行結果が0件の場合に通知されます。
     * @throws DataExistsException Insert時に、ユニーク制約に違反した場合に通知されます。
     </jp>*/
    /**<en>
     * Accept the SQL statement and execute.
     * @param sqlstr :SQL statement to execute
     * @param query : true if it is query
     * @return <code>ResultSet</code> of the result.  null for anything else
     * @throws ReadWriteException :Notifies if error occured in connection with database. 
     * @throws NotFoundException  :Notifies if executed result was 0.
     * @throws DataExistsException :Notifies if it broke the uniqye restriction at Insert.
     </en>*/
    protected ResultSet executeSQL(String sqlstr, boolean query)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException
    {
        ResultSet rset = null;
        DEBUG.MSG(DEBUG.HANDLER, sqlstr);
        try
        {
            //<jp> queryでfirst() で0行を見るためにはスクロールカーソルが必要</jp>
            //<en> Scroll cursor is required in order to view line 0 by first() in query.</en>
            _statement = _conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            if (query)
            {
                // SELECT
                rset = _statement.executeQuery(sqlstr);
            }
            else
            {
                int rrows = _statement.executeUpdate(sqlstr);
                //<jp> 更新行がなかった場合カーソルをCloseしていなかったところを修正</jp>
                //<en> When there is no updating line, the place which was not closing cursor is corrected</en>
                closeStatement();
                if (rrows == 0)
                {
                    throw (new NotFoundException("6003018"));
                }
            }
        }
        catch (SQLException e)
        {
            if (e.getErrorCode() == SQLErrors.ERR_DUPLICATED)
            {
                // 6026034=すでに同一データが存在するため、登録できません。
                RmiMsgLogClient.write(6026034, LogMessage.F_ERROR, "ASShelfHandler", null);
                throw (new DataExistsException("6026034"));
            }
            //<jp>6006002 = データベースエラーが発生しました。{0}</jp>
            //<en>6006002 = Database error occured.{0}</en>
            RmiMsgLogClient.write(new TraceHandler(6006002, e), "ASShelfHandler");
            //<jp> データベースエラーが発生しました。</jp>
            //<en> Database error occured.</en>
            throw new ReadWriteException(e);
        }

        return (rset);
    }
    // Private methods -----------------------------------------------
}
//end of class

