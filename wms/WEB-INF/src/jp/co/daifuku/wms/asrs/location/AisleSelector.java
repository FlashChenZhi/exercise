// $Id: AisleSelector.java 6436 2009-12-11 08:21:48Z ota $
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

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.dbhandler.ASShelfHandler;
import jp.co.daifuku.wms.asrs.dbhandler.DoubleDeepShelfHandler;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**<jp>
 * アイル検索を行います。
 * このクラスは空棚検索可能なアイルの一覧を取得する場合に使用されます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/03/16</TD><TD>INOUE</TD><TD>getAisleStationNosメソッドの中で倉庫に対するアイルステーションが存在しない場合例外としていたが平置きの場合を考慮し例外とはしないように変更</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 6436 $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class conducts search for aisle.
 * This class is used to obtain the list of aisle searchable for empty location.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/03/16</TD><TD>INOUE</TD><TD><code>getAisleStationNos</code>Does not throw exception.even if it does not aisle station in warehouse</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 6436 $
 * @author  $Author: ota $
 </en>*/
public class AisleSelector
        extends Object
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    /**<jp>
     * データベース接続用のコネクション
     </jp>*/
    /**<en>
     * Connection to connect with database
     </en>*/
    private Connection _conn = null;

    /**<jp>
     * アイルが所属する倉庫情報
     </jp>*/
    /**<en>
     * Information of warehouse the aisle belongs to
     </en>*/
    private WareHouse _wareHouse = null;

    /**<jp>
     * 搬送元ステーション
     </jp>*/
    /**<en>
     * Sending station
     </en>*/
    private Station _fromStation = null;

    /**<jp>
     * 検索対象アイルステーションの一覧を保持します。
     * ステーションはアイル検索順に並びます。
     </jp>*/
    /**<en>
     * It preserves the list of aisle stations subject to search.
     * Stations are listed in order of searched aisles.
     </en>*/
    private Aisle[] _aisles = null;

    /**<jp>
     * ルートチェック用クラス
     </jp>*/
    /**<en>
     * Class for route check
     </en>*/
    private RouteController _routeController = null;

    /**<jp>
     * 検索対象アイルIndex
     </jp>*/
    /**<en>
     * Index of aisle subject to search
     </en>*/
    private int _aisleIndex = -1;


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
        return ("$Revision: 6436 $,$Date: 2009-12-11 17:21:48 +0900 (金, 11 12 2009) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用のConnectionインスタンス、倉庫インスタンス、搬送元ステーションインスタンスを
     * 引数としてインスタンスを生成します。
     * @param conn データベース接続用 Connection
     * @param wh 空棚検索対象倉庫インスタンス
     * @param st 搬送元ステーションインスタンス
     * @throws ReadWriteException     データベースへのアクセスで異常が発生した場合に通知します。
     * @throws InvalidDefineException 定義情報に不整合が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Generates the instance according to the parameter, the connection instance to connect with database, 
     * instance of warehouse and instance of sending station.
     * @param conn :Connection with database
     * @param wh :instance of warehouse subject to empty location search
     * @param st  :instance of sending station
     * @throws ReadWriteException     : Notifies if error occured when accessing database.
     * @throws InvalidDefineException : Notifies if inconsistency occured in definition information.
     * @throws NotFoundException      : Notifies if there is no such data.
     </en>*/
    public AisleSelector(Connection conn, WareHouse wh, Station st) throws ReadWriteException,
            InvalidDefineException,
            NotFoundException
    {
        _conn = conn;
        _fromStation = st;
        _wareHouse = wh;
        _routeController = new RouteController(conn);

        if (st.getWhStationNo().equals(wh.getStationNo()) == false)
        {
            // ステーションの持つWareHouseが一致しないため、指定されたステーションから倉庫へのアイル決定は不可
            Object[] tObj = new Object[2];
            tObj[0] = st.getStationNo();
            tObj[1] = wh.getStationNo();
            RmiMsgLogClient.write(6026079, LogMessage.F_ERROR, this.getClass().getName(), tObj);
            throw new InvalidDefineException(WmsMessageFormatter.format(6026079, tObj));
        }

        //<jp> アイルステーション一覧を生成</jp>
        //<en> Generates a list of aisle stations.</en>
        createAisleInformations();
    }


    // Public methods ------------------------------------------------
    /**<jp>
     * 検索対象となるアイルの決定を行います。getBank()メソッドの呼出前に本メソッドを呼び出して
     * 検索の対象となるアイルを決定する必要があります。
     * @return 検索可能なバンクが存在する場合はtrue、全てのアイル内のバンクを検索した場合はfalseを返します。
     * @throws ReadWriteException     データベースへのアクセスで異常が発生した場合に通知します。
     * @throws InvalidDefineException 定義情報に不整合が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Determines the aisles subject to search. It is necessary to call this method and determine the aisle
     * subject to search prior to calling getBank() method.
     * @return Returns true if searchable bank exists, or false if all banks of all aisle were searched.
     * @throws ReadWriteException     : Notifies if error occured when accessing database.
     * @throws InvalidDefineException : Notifies if inconsistency occured in the information of definition.
     * @throws NotFoundException      : Notifies if there is no such data.
     </en>*/
    public boolean next()
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        //<jp> ルート、在庫確認中フラグのチェックを実施</jp>
        //<en> Check the inventory checking flag.</en>
        _aisleIndex++;
        for (; _aisleIndex < _aisles.length; _aisleIndex++)
        {
            //<jp> 搬送元ステーションと搬送先ステーションが同じ場合、ルートチェックは行わない。</jp>
            //<en> Route check will not be done if the sending station and receiving station are the same station.</en>
            if (!_fromStation.getStationNo().equals(_aisles[_aisleIndex].getStationNo()))
            {
                if (!_routeController.routeDetermin(_fromStation, _aisles[_aisleIndex]))
                {
                    //<jp> 搬送ルート使用不能</jp>
                    //<en> Carry route unavailable</en>
                    //<jp> 6022013=搬送可能なルートがありません。</jp>
                    //<en> 6022013=There is no available transfer route.</en>
                    Object[] tObj = new Object[2];
                    tObj[0] = _fromStation;
                    tObj[1] = _aisles[_aisleIndex].getStationNo();
                    RmiMsgLogClient.write(6022013, LogMessage.F_DEBUG, "AisleSelector", tObj);
                    continue;
                }
            }

            return true;
        }

        //<jp> 全アイル検索済み</jp>
        //<en> Searched through all aisles.</en>
        return false;
    }

    /**<jp>
     * 現在検索対象になっているアイルステーションNoを、倉庫情報の最終使用ステーションに記憶します。
     * 空棚検索で棚決定が行われた場合に使用します。次回の空棚検索ではこのステーションがアイル検索順の最後になります。
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知します。
     </jp>*/
    /**<en>
     * Store in memory the aisle station no. currently subject to search as a station of end-use in 
     * warehouse information.
     * This is used when location is determined in empty location search. This station will remain the
     * last of aisle search order until the next empty location search.
     * @throws ReadWriteException :Notifies if error occured when accessing database.
     </en>*/
    public void determin()
            throws ReadWriteException
    {
        try
        {
            if ((_aisleIndex >= 0) && (_aisleIndex <= _aisles.length))
            {
                _wareHouse.setLastUsedStationNo(_aisles[_aisleIndex].getStationNo());
                WareHouseAlterKey wKey = new WareHouseAlterKey();
                WareHouseHandler whandle = new WareHouseHandler(_conn);
                wKey.setStationNo(_wareHouse.getStationNo());
                wKey.updateLastUsedStationNo(_aisles[_aisleIndex].getStationNo());
                whandle.modify(wKey);
            }
        }
        catch (NotFoundException e)
        {
            throw new ReadWriteException();
        }
    }


    /**<jp>
     * 指定された検索ゾーンより空棚検索を行います。
     * 棚の検索はこのインスタンスが現在示しているアイルに対して行われます。
     * 空棚が見つからない場合、nullを返します。
     * @param tZone 空棚検索対象ゾーン
     * @param blnCCarry 空棚検索条件処理判定 true:条件処理する false:条件処理しない
     * @param listSPossible 空棚数アイル一覧データ
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @param rackToRack  手前棚棚間移動の空棚検索の場合はtrue, 通常空棚検索はfalse
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知します。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知します。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Conducts empty location search within the specified search zone.
     * Location search will be done with the aisle this instance currently indicates.
     * If there is no empty location, it returns null.
     * @param tZone :zone subject to the empty location search
     * @param blnCCarry :shelf check true or false
     * @param listSPossible :shelf possible location area
     * @param empLocDeterm     :true if a empty location has been selected as destination and renewing the shelf information, or false
     *                     if only the check will be done.
     * @param minVacantCnt  ダブルディープの最小確保空棚数
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return :Shelf instance searched
     * @throws ReadWriteException :Notifies if error occured when accessing database.
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public Shelf findShelf(Zone[] tZone, Boolean blnCCarry, ArrayList listSPossible, boolean empLocDeterm,
            int minVacantCnt, boolean alternativeLoc)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 2009/09/26 Y.Osawa ADD ST
        // 代替棚検索時は除外対象棚を取得する
        int[] exclusionBay = null;
        if (alternativeLoc)
        {
            exclusionBay = getExclusionLocation(_aisles[_aisleIndex]);
        }
        // 2009/09/26 Y.Osawa ADD ED

        // ダブルディープ対応
        if (Aisle.DOUBLE_DEEP_KIND_DOUBLE.equals(_aisles[_aisleIndex].getDoubleDeepKind()))
        {
            DoubleDeepShelfHandler ddsHandle = new DoubleDeepShelfHandler(_conn);
            // 現在示しているアイル、ゾーンを指定して空棚検索を行う。
            return ddsHandle.findEmptyShelfForDoubleDeep(_aisles[_aisleIndex], tZone, blnCCarry, listSPossible,
                    empLocDeterm, minVacantCnt);
        }
        else
        {
            ShelfOperator sHandle = new ShelfOperator(_conn);
            // 現在示しているアイル、ゾーンを指定して空棚検索を行う。
            // 2009/09/26 Y.Osawa UPD ST
            return sHandle.findEmptyShelf(_aisles[_aisleIndex], tZone, blnCCarry, listSPossible, empLocDeterm,
                    exclusionBay);
            // 2009/09/26 Y.Osawa UPD ED
        }
    }

    /**<jp>
     * 指定された検索ゾーンより空棚検索を行います。
     * 棚の検索はこのインスタンスが現在示しているアイルに対して行われます。
     * 空棚が見つからない場合、nullを返します。
     * @param tZone 空棚検索対象ゾーン
     * @param blnCCarry 空棚検索条件処理判定 true:条件処理する false:条件処理しない
     * @param listSPossible 空棚数アイル一覧データ
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @param rackToRack  手前棚棚間移動の空棚検索の場合はtrue, 通常空棚検索はfalse
     * @param alternativeLoc 二重格納、荷姿不一致など代替棚検索時はtrue、新規検索時はfalse
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知します。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知します。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    public Shelf findShelf(Zone[] tZone, Boolean blnCCarry, ArrayList listSPossible, int width, boolean empLocDeterm,
            int minVacantCnt, boolean alternativeLoc)
    // 2009/09/26 Y.Osawa UPD ED
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        FreeAllocationShelfOperator sHandle = new FreeAllocationShelfOperator(_conn);

        // 2009/09/26 Y.Osawa ADD ST
        // 代替棚検索時は除外対象棚を取得する
        int[] exclusionBay = null;
        if (alternativeLoc)
        {
            exclusionBay = getExclusionLocation(_aisles[_aisleIndex]);
        }
        // 2009/09/26 Y.Osawa ADD ED

        // アイル内に空棚が存在するかチェックする（ゾーン無視）
        // 2009/09/26 Y.Osawa UPD ST
        if (!sHandle.checkEmptyShelfInAisle(_aisles[_aisleIndex], width, exclusionBay))
        // 2009/09/26 Y.Osawa UPD ED
        {
            return null;
        }

        // 現在示しているアイル、ゾーンを指定して空棚検索を行う。
        // 2009/09/26 Y.Osawa UPD ST
        return sHandle.findEmptyShelf(_aisles[_aisleIndex], tZone, blnCCarry, listSPossible, width, empLocDeterm,
                exclusionBay);
        // 2009/09/26 Y.Osawa UPD ED
    }

    /**<jp>
     * アイルステーションを返します。
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知します。
     </jp>*/
    public String getStation()
            throws ReadWriteException
    {
        String str = _aisles[_aisleIndex].getStationNo();
        return str;
    }

    /**<jp>
     * 指定された検索ゾーンより空パレット検索を行います。
     * 棚の検索はこのインスタンスが現在示しているアイルに対して行われます。
     * 空パレットが見つからない場合、nullを返します。
     * @param  tZone 空パレット検索対象ゾーン
     * @return 検索したPalletインスタンス
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知します。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知します。
     </jp>*/
    /**<en>
     * Conducts empty pallet search within the specified search zone, according to the direction of search.
     * Pallet search will be done with the aisle this instance currently indicates.
     * If there is no empty location, it returns null.
     * @param  tZone :zone subject to empty pallet search
     * @return :Pallet instance searched
     * @throws ReadWriteException :Notifies if error occured when accessing database.
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     </en>*/
    public Shelf findPallet(Zone[] tZone)
            throws ReadWriteException,
                InvalidDefineException
    {
        // ダブルディープ対応
        if (Aisle.DOUBLE_DEEP_KIND_DOUBLE.equals(_aisles[_aisleIndex].getDoubleDeepKind()))
        {
            return null;
        }
        else
        {
            ASShelfHandler sHandle = new ASShelfHandler(_conn);
            // 現在示しているアイル、ゾーンを指定して空パレット検索を行う。
            return sHandle.findEmptyPallet(_aisles[_aisleIndex], tZone);
        }
    }

    /**<jp>
     * 指定された検索ゾーンより棚間移動のための空棚検索を行います。
     * 棚の検索はこのインスタンスが現在示しているアイルに対して行われます。
     * 空棚が見つからない場合、nullを返します。
     * @param  tZone 空棚検索対象ゾーン
     * @param  aisleNo :棚間移動元棚のアイルNo
     * @param  hardZoneId :棚間移動元棚のハードゾーンId
     * @param  softZoneId 棚間移動元棚のソフトゾーンID
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Conducts empty location search within the specified search zone.
     * Location search will be done with the aisle this instance currently indicates.
     * If there is no empty location, it returns null.
     * @param  tZone :zone subject to empty location search
     * @param  aisleNo :aisle no
     * @param  hardZoneId :hard zone id
     * @param  softZoneId : soft Zone Id
     * @return :Pallet instance searched
     * @throws ReadWriteException :Notifies if error occured when accessing database.
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    // 2009/09/26 Y.Osawa UPD ST
    public Shelf findRackToRack(Zone[] tZone, String aisleNo, String hardZoneId, String softZoneId, boolean alternativeLoc)
    // 2009/09/26 Y.Osawa UPD ED
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 2009/09/26 Y.Osawa ADD ST
        // 代替棚検索時は除外対象棚を取得する
        int[] exclusionBay = null;
        if (alternativeLoc)
        {
            exclusionBay = getExclusionLocation(_aisles[_aisleIndex]);
        }
        // 2009/09/26 Y.Osawa ADD ED

        // ダブルディープ対応
        if (Aisle.DOUBLE_DEEP_KIND_DOUBLE.equals(_aisles[_aisleIndex].getDoubleDeepKind()))
        {
            DoubleDeepShelfHandler ddsHandle = new DoubleDeepShelfHandler(_conn);
            // 現在示しているアイル、ゾーンを指定して棚間移動用の空棚検索を行う。
            return ddsHandle.findEmptyShelfForRackToRack(_aisles[_aisleIndex], tZone, aisleNo, hardZoneId, softZoneId);
        }
        else
        {
            ShelfOperator sHandle = new ShelfOperator(_conn);
            // 現在示しているアイル、ゾーンを指定して棚間移動用の空棚検索を行う。
            // 2009/09/26 Y.Osawa UPD ST
            return sHandle.findEmptyShelf(_aisles[_aisleIndex], tZone, exclusionBay);
            // 2009/09/26 Y.Osawa UPD ED
        }
    }

    /**<jp>
     * 指定された検索ゾーンより棚間移動のための空棚検索を行います。
     * 棚の検索はこのインスタンスが現在示しているアイルに対して行われます。
     * 空棚が見つからない場合、nullを返します。
     * @param  tZone 空棚検索対象ゾーン
     * @param  aisleNo :棚間移動元棚のアイルNo
     * @param  hardZoneId :棚間移動元棚のハードゾーンId
     * @param  softZoneId 棚間移動元棚のソフトゾーンID
     * @return 検索したShelfインスタンス
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Conducts empty location search within the specified search zone.
     * Location search will be done with the aisle this instance currently indicates.
     * If there is no empty location, it returns null.
     * @param  tZone :zone subject to empty location search
     * @param  aisleNo :aisle no
     * @param  hardZoneId :hard zone id
     * @param  softZoneId : soft Zone Id
     * @return :Pallet instance searched
     * @throws ReadWriteException :Notifies if error occured when accessing database.
     * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    // 2009/09/26 Y.Osawa UPD ST
    public Shelf findRackToRack(Zone[] tZone, String aisleNo, String hardZoneId, String softZoneId, int width, boolean alternativeLoc)
    // 2009/09/26 Y.Osawa UPD ED
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        FreeAllocationShelfOperator sHandle = new FreeAllocationShelfOperator(_conn);
        
        // 2009/09/26 Y.Osawa ADD ST
        // 代替棚検索時は除外対象棚を取得する
        int[] exclusionBay = null;
        if (alternativeLoc)
        {
            exclusionBay = getExclusionLocation(_aisles[_aisleIndex]);
        }
        // 2009/09/26 Y.Osawa ADD ED

        
        // 現在示しているアイル、ゾーン、荷幅を指定して棚間移動用の空棚検索を行う。
        // 2009/09/26 Y.Osawa UPD ST
        return sHandle.findEmptyShelf(_aisles[_aisleIndex], tZone, width, exclusionBay);
        // 2009/09/26 Y.Osawa UPD ED
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 指定された搬送元ステーションからアイルステーションNo一覧を生成します。
     * 搬送元ステーションの定義情報内にアイルステーションが定義されている場合は、そのアイルステーションNoを使用します。
     * アイルステーションが定義されていない場合、倉庫ステーションNoを元にアイルステーションの一覧を取得します。
     * アイルステーションNoの並びはアイルステーションNoの順番となります。但し搬送元ステーションの保持する、
     * 倉庫情報内に最終使用ステーションがセットされている場合、そのステーションが最後になるように並び替えが行われます。
     * アイルステーションNoの並びは空棚検索順として使用されます。
     * 又、搬送元ステーションからのルートがNGのアイルステーションは一覧に含めません。
     * 例
     *   倉庫情報内の最終使用ステーションが未設定時のアイルステーションNoの並び
     *     9001
     *     9002
     *     9003
     *   倉庫情報内の最終使用ステーションが9001の時のアイルステーションNoの並び
     *     9002
     *     9003
     *     9001
     * @throws ReadWriteException     データベースへのアクセスで異常が発生した場合に通知されます。
     * @throws InvalidDefineException stに対するアイルステーションの定義が一つも見つからない場合に通知されます。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Generates a list of aisle station no. based on the specified sending station.
     * If the definition of the sending station includes the aisle station information, that aisle station no. will be used.
     * If the sending station is not defined for aisle station, a list of aisle stations will b retireved based on the warehouse
     * station no.
     * Aisle station no. will be lined in order of aisle station no. sequence. Except if the station of end-use has been set
     * in the warehouse information, which is preserved by sending station, that station will be put in the end of the list. 
     * This order of aisle station no. will be used as an order if empty location search.
     * Also the list does not include the aisle stations which the route from the sending station is found unavailable.
     * Example
     *   Order of aisle station no. when station of end-use is unset in warehouse information:
     *     9001
     *     9002
     *     9003
     *   Order of aisle station no. when 9001 has been set as the station of end-use in warehouse information:
     *     9002
     *     9003
     *     9001
     * @throws ReadWriteException     : Notifies if error occured when accessing database.
     * @throws InvalidDefineException : Notifies if there is no definition of aisle station for st at all.
     * @throws NotFoundException      : Notifies if there is no such data.
     </en>*/
    protected void createAisleInformations()
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        //<jp> 指示されたステーション情報内にアイルステーションの設定の有無で処理を分岐する。</jp>
        //<en> Branching the process according to whether/not the setting of aisle station is done in the specified station data.</en>
        if (!StringUtil.isBlank(_fromStation.getAisleStationNo()))
        {
            //<jp> アイルステーションNoの定義がある場合</jp>
            //<jp> アイルクラスのインスタンスかどうかチェックを行い、検索アイル一覧に格納</jp>
            //<en> If the definition of aisle station no. is set:</en>
            //<en> Check to see if it is the instance of aisle class, then store in the list of aisle search.</en>
            Station st = StationFactory.makeStation(_conn, _fromStation.getAisleStationNo());
            if (st instanceof Aisle)
            {
                _aisles = new Aisle[1];
                _aisles[0] = (Aisle)st;
            }
            else
            {
                //<jp> 定義がAisleクラスのインスタンスではない場合は例外</jp>
                //<en> Exception if the definition is not the instance of Aisle class.</en>
                //<jp> 6026049=アイルの情報が存在しません。 アイルNo.={0}</jp>
                //<en> 6026049=TAisle is not determined. AisleNo.={0}</en>
                Object[] tObj = new Object[1];
                tObj[0] = _fromStation.getAisleStationNo();
                RmiMsgLogClient.write(6026049, LogMessage.F_ERROR, "AisleSelector", tObj);
                throw new InvalidDefineException(WmsMessageFormatter.format(6026049, tObj));
            }
        }
        else
        {
            //<jp> アイルステーションが未定義であれば、倉庫情報を元にアイルステーション一覧を取得</jp>
            //<en> If aisle station is undefined, retrieve the list of aisle station according to the warehouse information.</en>
            AisleSearchKey akey = new AisleSearchKey();
            AisleHandler ahandl = new AisleHandler(_conn);
            akey.setWhStationNo(_fromStation.getWhStationNo());
            akey.setAisleNoOrder(SystemDefine.AISLE_SEARCH_ASCENDING.equals(_wareHouse.getAisleSearchType()));
            
            Aisle[] wksts = (Aisle[])ahandl.find(akey);
            if (wksts.length == 0)
            {
                //<jp> アイルステーションの定義が一つも見つからない場合は例外</jp>
                //<en> Exception if there is no definition for aisle station at all.</en>
                //<jp> 6026049=アイルの情報が存在しません。 アイルNo.={0}</jp>
                //<en> 6026049=TAisle is not determined. AisleNo.={0}</en>
                Object[] tObj = new Object[1];
                tObj[0] = _fromStation.getStationNo();
                RmiMsgLogClient.write(6026049, LogMessage.F_ERROR, "AisleSelector", tObj);
                throw new InvalidDefineException(WmsMessageFormatter.format(6026049, tObj));
            }

            //<jp> 倉庫情報内を元に、アイルステーションNoの並び替えを行う。</jp>
            //<en> Rearrange the order of aisle station no. according to the warehouse information.</en>
            _aisles =
                    sortWareHouseAisles((WareHouse)StationFactory.makeStation(_conn, _fromStation.getWhStationNo()),
                            wksts);
        }
    }

    /**<jp>
     * 指定された倉庫情報内の最終使用ステーションNoを元に、アイルステーションNoの並び替えを行います。
     * @param wh 倉庫情報
     * @param Aislests アイルステーションNoの一覧
     * @return 並び替えたアイルステーションNo
     </jp>*/
    /**<en>
     * According to the station no. of end-use in specified warehouse information, rearrange the order of aisle station no.
     * @param wh :warehouse information
     * @param aislests :list of aisle station no.
     * @return aisle station no. in order rearranged
     </en>*/
    protected Aisle[] sortWareHouseAisles(WareHouse wh, Aisle[] aislests)
    {
        if (StringUtil.isBlank(wh.getLastUsedStationNo()))
        {
            return (aislests);
        }
        else
        {
            //<jp> 倉庫情報内の最終使用ステーションNoがセットされていた場合，並び替えを行う。</jp>
            //<en> Rearrange the order of aisle station no. if the station no. of end-use has been set in warehouse information.</en>
            Aisle[] newsts = new Aisle[aislests.length];
            int pt = 0;
            for (int i = 0; i < newsts.length; i++)
            {
                //<jp> ステーションNoが一致すれば、その次のステーションを配列の先頭にする。</jp>
                //<en> If station no. match, place the following station in the beginning of array.</en>
                if (aislests[i].getStationNo().equals(wh.getLastUsedStationNo()))
                {
                    for (int j = i + 1; j < newsts.length; j++)
                    {
                        newsts[pt] = aislests[j];
                        pt++;
                    }
                    for (int k = 0; k < i + 1; k++)
                    {
                        newsts[pt] = aislests[k];
                        pt++;
                    }
                    return newsts;
                }
            }
        }

        //<jp> 倉庫情報内の最終使用ステーションがアイルステーション一覧内にが見つからなかった場合、そのまま返す。</jp>
        //<en> If the station of end-use in warehouse cannot be found in the aisle station list, return it as it is.</en>
        return (aislests);
    }

    // 2009/09/26 Y.Osawa ADD ST
    /**
     * 検索除外ベイを取得します。
     * @param tAisle 空棚検索対象アイル
     * @return 検索除外ベイ
     * @throws ReadWriteException データベースへのアクセスで異常が発生した場合に通知します。
     */
    protected int[] getExclusionLocation(Aisle tAisle)
            throws ReadWriteException
    {
        boolean exceptStart = false;
        boolean exceptEnd = false;

        try
        {
            // 開始ベイを空棚検索対象としない倉庫かどうかチェックする
            for (int i = 0; i < WmsParam.WAREHOUSE_ALTER_LOCATION_EXCEPT_START_BAY.size(); i++)
            {
                String line =
                        String.valueOf(WmsParam.WAREHOUSE_ALTER_LOCATION_EXCEPT_START_BAY.get(i));

                if (line == null)
                {
                    break;
                }

                if (_wareHouse.getStationNo().equals(line))
                {
                    exceptStart = true;
                    break;
                }
            }

            // 終了ベイを空棚検索対象としない倉庫かどうかチェックする
            for (int i = 0; i < WmsParam.WAREHOUSE_ALTER_LOCATION_EXCEPT_END_BAY.size(); i++)
            {
                String line =
                        String.valueOf(WmsParam.WAREHOUSE_ALTER_LOCATION_EXCEPT_END_BAY.get(i));

                if (line == null)
                {
                    break;
                }

                if (_wareHouse.getStationNo().equals(line))
                {
                    exceptEnd = true;
                    break;
                }
            }

            List<Integer> bayList = new ArrayList<Integer>();

            ShelfSearchKey shelfKey = new ShelfSearchKey();
            ShelfHandler shelfHdl = new ShelfHandler(getConnection());
            Shelf shelf = null;

            // 開始ベイを空棚検索対象としない場合
            if (exceptStart)
            {
                shelfKey.clear();
                shelfKey.setBayNoCollect("MIN");
                shelfKey.setParentStationNo(tAisle.getStationNo());
                shelf = (Shelf)shelfHdl.findPrimary(shelfKey);
                bayList.add(shelf.getBayNo());
            }

            // 終了ベイを空棚検索対象としない場合
            if (exceptEnd)
            {
                shelfKey.clear();
                shelfKey.setBayNoCollect("MAX");
                shelfKey.setParentStationNo(tAisle.getStationNo());
                shelf = (Shelf)shelfHdl.findPrimary(shelfKey);
                bayList.add(shelf.getBayNo());
            }

            int[] exclusionBay = new int[bayList.size()];
            for (int i = 0; i < bayList.size(); i++)
            {
                exclusionBay[i] = bayList.get(i);
            }

            return exclusionBay;
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException();
        }
    }
    // 2009/09/26 Y.Osawa ADD ED

    /**
     * データベースのコネクションを返します。
     * @return データベースのコネクション。
     */
    protected Connection getConnection()
    {
        return _conn;
    }

    /**
     * 搬送元ステーションインスタンスを返します。
     * @return ステーションインスタンス
     */
    protected Station getFromStation()
    {
        return _fromStation;
    }


    /**
     * 検索対象アイルステーションの一覧を返します。
     * @param aisles アイルステーションの一覧
     */
    protected void setAisles(Aisle[] aisles)
    {
        _aisles = aisles;
    }

    // Private methods -----------------------------------------------

}
