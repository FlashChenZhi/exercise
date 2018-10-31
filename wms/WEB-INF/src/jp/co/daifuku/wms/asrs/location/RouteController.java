// $Id: RouteController.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Hashtable;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**<jp>
 * 設備の搬送ルートに関する情報を制御するクラスです。
 * 指定されたPalletの現在位置と作業場を元に、ステーション決定とルートチェックを行います。
 * 搬送先ステーションに作業場を指定された場合、作業場の形態に合わせて搬送先ステーションの決定を行います。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/01/22</TD><TD>M.Inoue</TD><TD>荷姿検知機がある場合スケジュールでは空棚検索を行わないように修正</TD></TR>
 * </TABLE>
 * <BR> * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
/**<en>
 * This class controls the carry route relevant information of equipment.
 * According to the current position of specified pallet and workshop, it determines stations and checks the route.
 * When the workshopis specified for sending station, ir determinese the receiving station according to the style
 * of workshop.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/01/22</TD><TD>M.Inoue</TD><TD>modify:in case of loadsizecheck,not search empty shelf by schedule</TD></TR>
 * </TABLE>
 * <BR> * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  $Author: kishimoto $
 </en>*/
public class RouteController
        extends Object
{
    // Class fields --------------------------------------------------
    /**<jp>
     * ルートチェックの結果を表すフィールド。<BR>
     * <code>ACTIVE</code>はルートが利用可能な状態を表します。
     </jp>*/
    /**<en>
     * Field for the results of route check<R>
     * <code>ACTIVE</code> : route is available
     </en>*/
    public static final int ACTIVE = 1;

    /**<jp>
     * ルートチェックの結果を表すフィールド。<BR>
     * <code>OFFLINE</code>は設備切離しによる利用不可能な状態を表します。
     </jp>*/
    /**<en>
     * Field for the results of route check<R>
     * <code>ACTIVE</code> : route is available
     </en>*/
    public static final int OFFLINE = 2;

    /**<jp>
     * ルートチェックの結果を表すフィールド。<BR>
     * <code>FAIL</code>は設備異常による利用不可能な状態を表します。
     </jp>*/
    /**<en>
     * Field for the results of route check<BR>
     * <code>FAIL</code> : unavailable due to equipment error
     </en>*/
    public static final int FAIL = 3;

    /**<jp>
     * ルートチェックの結果を表すフィールド。<BR>
     * <code>NO_STATION_INTO_WORKPLACE</code>は指定された作業場内に作業可能なステーションが存在しない状態を
     * 表します。
     </jp>*/
    /**<en>
     * Field for the results of route check<BR>
     * <code>NO_STATION_INTO_WORKPLACE</code> : There is no available station in specified workshop.
     </en>*/
    public static final int NO_STATION_INTO_WORKPLACE = 4;

    /**<jp>
     * ルートチェックの結果を表すフィールド。<BR>
     * <code>AISLE_INVENTORYCHEK</code>は指定された作業場に在庫確認中のアイルがある状態を表します。
     </jp>*/
    /**<en>
     * Field for the results of route check<BR>
     * <code>AISLE_INVENTORYCHEK</code>: there are aisles in specified workshops right in inventory checking
     </en>*/
    public static final int AISLE_INVENTORYCHECK = 5;

    /**<jp>
     * ルートチェックの結果を表すフィールド。<BR>
     * <code>AISLE_EMPTYLOCATIONCHECK</code>は指定された作業場に空棚確認中のアイルがある状態を表します。
     </jp>*/
    /**<en>
     * Field for the results of route check<BR>
     * <code>AISLE_EMPTYLOCATIONCHECK</code>: there are aisles in specified workshops where checking the empty location.
     </en>*/
    public static final int AISLE_EMPTYLOCATIONCHECK = 6;

    /**<jp>
     * ルートチェックの結果を表すフィールド。<BR>
     * <code>AGC_OFFLINE</code>は指定された作業場のステーションを管理しているAGCがオフライン状態であること表します。
     </jp>*/
    /**<en>
     * Field for the results of route check <BR>
     * <code>AGC_OFFLINE</code>: the AGC, which controls the station of specified workshop, is off-line.
     </en>*/
    public static final int AGC_OFFLINE = 7;

    /**<jp>
     * ルートの状態を表すフィールド。<code>UNKNOWN</code>はルートチェック未実施の状態を表します。
     </jp>*/
    /**<en>
     * Status of the route
     * <code>UNKNOWN</code> : Route check undone
     </en>*/
    public static final int UNKNOWN = -1;

    /**<jp>
     * ルートの状態を表すフィールド。<code>NOTFOUND</code>指定されたルート定義が見つからない状態を表します。
     </jp>*/
    /**<en>
     * Status of the route
     * <code>NOTFOUND</code>: Specified route definition was not found.
     </en>*/
    public static final int NOTFOUND = -2;

    /**<jp>
     * ルートの状態を表すフィールド。<code>LOCATION_EMPTY</code>空棚が見つからない状態を表します。
     </jp>*/
    /**<en>
     * Status of the rout
     * <code>LOCATION_EMPTY</code>: empty location cannot be found.
     </en>*/
    public static final int LOCATION_EMPTY = -3;

    // Class variables -----------------------------------------------
    /**<jp>
     * データベース接続用コネクション
     </jp>*/
    /**<en>
     * Connection with databse
     </en>*/
    private Connection _conn;

    /**<jp>
     * 搬送元ステーション
     </jp>*/
    /**<en>
     * Sending station
     </en>*/
    private Station _srcStation;

    /**<jp>
     * 搬送先ステーション
     </jp>*/
    /**<en>
     * Receiving station
     </en>*/
    private Station _destStation;

    /**<jp>
     * 搬送元ステーションと搬送先ステーション間のルートチェックの結果を保存する変数
     </jp>*/
    /**<en>
     * Variable which preserves the results of route check between sending station and receiving station.
     </en>*/
    private int _routeStatus = UNKNOWN;

    /**<jp>
     * 搬送ルートチェックの結果を記録する<code>Hashtable</code>インスタンス
     * 既にルートチェック済みのルートはこの中から取り出して返します。
     </jp>*/
    /**<en>
     * Instance of <code>Hashtable</code> which records the results of carry route check
     * The route which has already been checked will be retrieved from this instance and be returned.
     </en>*/
    private Hashtable _routeHashtable;

    /**<jp>
     * 搬送ルートチェックを常に行うかどうかを決定するフラグです。
     * trueの場合、常にルートチェック処理を実施します。
     * falseの場合、すでにチェック済みの搬送ルートに対するルートチェックは行いません。
     * デフォルトは常にルートチェック処理を実施します。
     </jp>*/
    /**<en>
     * The flag which determines whether/not the carry route check should always be done.
     * If 'true', it processes the route checks always.
     * If 'false', it does not process the route check on carry routes already checked.
     * Default to route check for always.
     </en>*/

    private boolean _alwaysCheck = true;

    /**<jp>
     * オフラインAGCに属するステーションであってもルートOKとして返すかどうかのフラグ
     </jp>*/
    /**<en>
     * Flag whether/not to return 'route available' when the station belongs to off-line AGC.
     </en>*/
    private boolean _offLineCheck = true;

    // 2010/04/08 add start
    /**<jp>
     * 棚検索に使用する<code>ShelfSelector</code>を保持するかどうかのフラグ<br>
     * trueの場合、<code>ShelfSelector</code>で内部的に使用される空棚数のリストを保持することで
     * 空棚数チェックを簡略化します。<br>
     * 再入庫画面（連続入庫処理）で使用します。<br>
     * デフォルトはfalseです。
     </jp>*/
    private boolean _saveflg = false;

    /**<jp>
     * ShelfSelector
     </jp>*/
    private ShelfSelector _ssel = null;

    // 2010/04/08 add end

    /**<jp>
     * 一度検索した<CODE>GroupController</CODE>を保持します。
     </jp>*/
    /**<en>
     * <CODE>GroupController</CODE> that has been searched out is preserved.
     </en>*/
    private Hashtable _hashGC = null;

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
        return ("$Revision: 8053 $,$Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * データベース接続用の<code>Connection</code>を設定します。
     * @param conn 設定するConnection
     </jp>*/
    /**<en>
     * Sets <code>Connection</code> to connect with database.
     * @param conn :Connection to set
     </en>*/
    public RouteController(Connection conn)
    {
        this(conn, true);
    }

    /**<jp>
     * データベース接続用の<code>Connection</code>とルートチェックを毎回実施するかどうかのフラグを
     * 設定しインスタンスを生成します。
     * @param conn 設定するConnection
     * @param always trueの場合常にルートチェック処理を実施します。
     *                falseの場合はチェック済みの搬送ルートに対するルートチェックは行いません。
     </jp>*/
    /**<en>
     * Sets <code>Connection</code> to connect with database and the flag to determine whether/not to
     * conduct route check evry time, then generate an instance.
     * @param conn :Connection to set
     * @param always true: always conducts the route check.
     *                false: Route check will not be done for the carry route the already checked.
     </en>*/
    public RouteController(Connection conn, boolean always)
    {
        setConnection(conn);
        _alwaysCheck = always;

        _routeHashtable = new Hashtable(10);
        _hashGC = new Hashtable();
    }

    /**<jp>
     * データベース接続用の<code>Connection</code>とルートチェックを毎回実施するかどうかのフラグと
     * <code>ShelfSelector</code>を保持するかどうかのフラグを設定しインスタンスを生成します。
     * @param conn 設定するConnection
     * @param always trueの場合常にルートチェック処理を実施します。
     *                falseの場合はチェック済みの搬送ルートに対するルートチェックは行いません。
     * @param save tureの場合<code>ShelfSelector</code>を保持します。
     </jp>*/
    public RouteController(Connection conn, boolean always, boolean save)
    {
        setConnection(conn);
        _alwaysCheck = always;
        _saveflg = save;

        _routeHashtable = new Hashtable(10);
        _hashGC = new Hashtable();
    }

    // Public methods ------------------------------------------------

    /**<jp>
     * 作業場が入力された場合AGCがオンライン、オフラインにかかわらずルートOKとして返すかどうかを設定します。
     * @param ofline falseをセットした場合オフライン、オンラインにかかわらずルートOKとして返します。
     </jp>*/
    /**<en>
     * Set whether/not to return 'route available' in case the workshop has been input - regardless of
     * AGC status (on/offline).
     * @param offline :if false is set, it returns 'route available' no matter AGC is off-line or on-line.
     </en>*/
    public void setControllerOffLineCheck(boolean offline)
    {
        _offLineCheck = offline;
    }

    /**<jp>
     * 入庫、直行の搬送ルート決定処理を行います。
     * 搬送対象パレット、搬送先ステーションNoを元にルートチェック及び搬送先決定を行います。
     * 搬送先ステーションに作業場を指定された場合、搬送先ステーションの決定を行います。
     * @param plt       搬送対象パレット
     * @param destStnum 搬送先ステーションNo
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Determining the carry route of storage/direct travel.
     * According to the destined pallet and receiving station no., conducts the route check and
     * determines the receiving station.
     * If workshop is selected as a receiving station, receiving station needs to be determined.
     * @param plt       :pallet to carry
     * @param destStnum :receiving staton no.
     * @return :true if receiving station is determined, or false if available station cannot be found.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if specified station no. is .
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public boolean storageDetermin(Pallet plt, String destStnum)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        Station frst = null;
        Station tost = null;
        try
        {
            Connection connection = getConnection();
            frst = StationFactory.makeStation(connection, plt.getCurrentStationNo());
            tost = StationFactory.makeStation(connection, destStnum);
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }

        return storageDetermin(plt, frst, tost);
    }

    /**<jp>
     * 制御処理専用の入庫、直行の搬送ルート決定処理を行います。
     * 搬送元ステーションは送信可能ステーションである必要があります。
     * 搬送先ステーションは倉庫または送信可能ステーションである必要があります。
     * 搬送対象パレット、搬送先ステーションNoを元にルートチェック及び搬送先決定を行います。
     * 搬送先が倉庫の場合、棚決定を行います。
     * @param plt 搬送対象パレット
     * @param frst 搬送元ステーション
     * @param tost 搬送先ステーション
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     * @throws InvalidDefineException tostで指定された搬送先ステーションNoが送信不可ステーションの場合通知されます。
     * @throws InvalidDefineException pltが参照する現在ステーションが送信不可ステーションの場合通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Determines the carry route for storage and direct travels dedicated for control process.
     * It is necessary that the sending station must be sendable station.
     * It is necessary that the receiving station must be either a warehouse or sendable station.
     * According to the carrying pallet and the receiving station no., route check to be conducted and
     * the destination needs to be determined.
     * IF the carry is destined to a warehouse, determine the location (shelf).
     * @param plt :pallet to carry
     * @param frst :sending station
     * @param tost :receiving station
     * @return :true if receiving station is determined, or 'false' if available station cannot be found.
     * @throws InvalidDefineException :Notifies if the specified station no. was .
     * @throws InvalidDefineException :Notifies if the receiving station no. specified by tost is not the
     * sendable station.
     * @throws InvalidDefineException :Notifies if current station that plt refers to is not the sendable station.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public boolean storageDetermin(Pallet plt, Station frst, Station tost)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        try
        {
            if (frst.isSendable() == false)
            {
                //<jp> 搬送元が送信可能ステーションでは無い場合、例外を返す。</jp>
                //<en> If the sender is not the sendable station, it returns excetion.</en>
                throw new InvalidDefineException("From station is not sendable Station");
            }

            //<jp> 搬送先が倉庫の場合、棚決定を行う。</jp>
            //<en> If the receiver is a warehouse, it determines the location.</en>
            if (tost instanceof WareHouse)
            {
                Shelf location = selectShelf(plt, true);
                Connection connection = getConnection();
                if (location != null)
                {
                    // フリーアロケーション運用の場合、荷幅、棚使用フラグを更新
                    WareHouse wh = (WareHouse)tost;
                    if (WareHouse.FREE_ALLOCATION_ON.equals(wh.getFreeAllocationType()))
                    {
                        FreeAllocationShelfOperator freeshelfop =
                                new FreeAllocationShelfOperator(getConnection(), location.getStationNo());
                        freeshelfop.alterWidth(plt);
                    }

                    //<jp> 検索した棚を入庫予約にする。</jp>
                    //<en> Reserves the searched location for the receiving.</en>
                    ShelfOperator shop = new ShelfOperator(connection, location.getStationNo());
                    shop.alterPresence(Shelf.LOCATION_STATUS_FLAG_RESERVATION);
                    _srcStation = frst;
                    _destStation = location;
                    _destStation.setAisleStationNo(location.getParentStationNo());
                    return true;
                }

                //<jp> 空棚がない場合、リジェクトステーションが定義されていればその値を搬送先にする。</jp>
                //<en> If there is no empty location, and if reject station is defined, set that value as </en>
                //<en> a destination.</en>
                Station currentst = StationFactory.makeStation(connection, plt.getCurrentStationNo());
                if (StringUtil.isBlank(currentst.getRejectStationNo()) == false)
                {
                    _srcStation = frst;
                    _destStation = StationFactory.makeStation(connection, currentst.getRejectStationNo());
                    _routeStatus = ACTIVE;
                    return true;
                }
                else
                {
                    //<jp> 空棚もリジェクトステーション定義もない場合は搬送ルートNG</jp>
                    //<en> If there is neither empty location or the definition of reject station, this </en>
                    //<en> carry route is not acceptable.</en>
                    _srcStation = null;
                    _destStation = null;
                    _routeStatus = LOCATION_EMPTY;
                    return false;
                }
            }
            else
            {
                if (tost.isSendable() == true)
                {
                    //<jp> 搬送元、搬送先ともに通常ステーションまたは棚であればルートチェックのみ行う。</jp>
                    //<en> If both sending station and receiving stations are the normal station or locations,</en>
                    //<en> it only conducts the route check.</en>
                    return routeDetermin(frst, tost);
                }
                else
                {
                    //<jp> 搬送先が倉庫でも送信可能ステーションでも無い場合、例外を返す。</jp>
                    //<en> If the receiving station is neither a warehouse or sendable station, it returns exception.</en>
                    throw new InvalidDefineException("To station is not sendable Station");
                }
            }
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
    }

    /**<jp>
     * スケジュール処理に必要な入庫棚決定処理を行います。
     * 搬送対象パレット、搬送先ステーションNoを元にルートチェック及び搬送先決定を行います。<BR>
     * 搬送元が作業場の場合、ステーション決定処理を行います。
     * @param plt  搬送元ステーションを含む<code>Pallet</code>インスタンス
     * @param tost 搬送先ステーション
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Processing the storing location designation required for schedule process.
     * According to the carrying pallet and the receiving station no., checks the route and designates the
     * receiving station.<BR>
     * If it is sent from workshop, it processes the station designation.
     * @param plt  :<code>Pallet</code> instance which includes the sending station
     * @param tost :receiving station
     * @return :true if receiving station is designated, or false if available station cannot be found.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if specified statio no. is Invalid.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public boolean storageDeterminSCH(Pallet plt, Station tost)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        return storageDeterminSCH(plt, tost, true);
    }

    /**<jp>
     * スケジュール処理に必要な入庫棚決定処理を行います。
     * 搬送対象パレット、搬送先ステーションNoを元にルートチェック及び搬送先決定を行います。<BR>
     * 搬送先が倉庫の場合、棚決定を行います。<BR>
     * 搬送先が棚の場合、搬送元ステーションと棚の間でルートチェックを行います。
     * @param plt    搬送元ステーションを含む<code>Pallet</code>インスタンス
     * @param tost   搬送先ステーション
     * @param determ 搬送元が作業場の場合にステーション決定を行う場合はtrueを指定します。<BR>
     *               ルートチェックのみ行う場合はfalseを指定します。
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Porcessing te storing location designation required for schedule process.
     * According to the carrying pallet and the receiving station no., checks the route and designates the
     * receiving station.<BR>
     * If it is received by a warehouse, it designates the location.<BR>
     * If the receiver is the shelf, checks the route between sending station and the shelf.
     * @param plt    :<code>Pallet</code> instance which contains the sending station
     * @param tost   :receiving station
     * @param determ :Specify 'true' if the sender is a workshop and station needs to be designated.<BR>
     *                Specify 'false' if only the route needs to be checked.
     * @return 'true' if receiving station is designated, or 'false' if available station cannot be found.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if the specified station no. is invalid.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public boolean storageDeterminSCH(Pallet plt, Station tost, boolean determ)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        try
        {
            Connection connection = getConnection();
            Station frst = StationFactory.makeStation(connection, plt.getCurrentStationNo());

            // 搬送元ステーションが指示情報として送信可能かどうかチェックします。
            if (frst.isSendable() == true)
            {
                if (tost instanceof WareHouse)
                {
                    //<jp> ステーションからの棚決定（空棚検索）</jp>
                    //<en> Designating location from station (empty location search)</en>
                    return shelfDeterminFromStation(plt, frst, (WareHouse)tost);
                }
                else if (tost instanceof Shelf)
                {
                    //<jp> ステーションからの棚決定（ルートチェック）</jp>
                    //<en> Designating location from station (route check)</en>
                    return shelfDeterminFromStation(plt, frst, (Shelf)tost);
                }
                else
                {
                    //<jp> FTTB ログメッセージ取得</jp>
                    //<jp> 指定された入庫先が不正です。StationNo={0}</jp>
                    //<en> Specified storign location is invalid. StationNo={0}</en>
                    throw new InvalidDefineException("the storing location is invalid.");
                }
            }
            else
            {
                if (frst instanceof WorkPlace)
                {
                    //<jp> 作業場からの棚決定</jp>
                    //<en> Designating location from workshop</en>
                    return shelfDeterminFromWorkPlace(plt, (WorkPlace)frst, tost, determ);
                }
                else
                {
                    //<jp> FTTB ログメッセージ取得</jp>
                    //<jp> 指定されたステーションは入庫作業可能な作業場またはステーションではありません。StationNo={0}</jp>
                    //<en> If the receiving station is neither a warehouse or sendable station, it returns exception.</en>
                    throw new InvalidDefineException("sending location is invalid.");
                }
            }
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
    }

    /**<jp>
     * 直行の搬送ルート決定処理を行います<BR>
     * 搬送対象パレット、搬送先ステーションNoを元にルートチェック及び搬送先決定を行います。<BR>
     * 搬送元または搬送先に作業場を指定された場合、それぞれステーション決定処理を行います。
     * 搬送先が作業場の場合、ステーション決定処理の結果をもとに最終使用ステーションを更新します。
     * @param frst 搬送元ステーションまたは作業場
     * @param tost 搬送先ステーションまたは作業場
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the carry route desugnation by direct travel.<BR>
     * According to the carrying pallet and receiving station no., it checks the route and designates the storing location.<BR>
     * If the workshop is selected for sending station or receiving station, station will be designated respectively.
     * If the receiving station is a workshop, it updates the station of end-use based on the result of station designatioln.
     * @param frst :sending station or workshop
     * @param tost :receiving station or workshop
     * @return :true if receiving station is designated, false if available station cannot be found.
     * @throws InvalidDefineException :Notifies if specified station no. is invalid.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public boolean directTrancefarDeterminSCH(Station frst, Station tost)
            throws ReadWriteException,
                InvalidDefineException
    {
        return directTrancefarDeterminSCH(frst, tost, true);
    }

    /**<jp>
     * 直行の搬送ルート決定処理を行います。<BR>
     * 搬送対象パレット、搬送先ステーションNoを元にルートチェック及び搬送先決定を行います。<BR>
     * 搬送元または搬送先に作業場を指定された場合、それぞれステーション決定処理を行います。
     * @param frst 搬送元ステーションまたは作業場
     * @param tost 搬送先ステーションまたは作業場
     * @param determ 搬送先が作業場でステーション決定を行う場合はtrue、チェックのみ行う場合はfalseをセットする。
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the carry route designation for direct travel.<BR>
     * According to the carrying pallet and receiving station no., it checks the route and designates the storing location.<BR>
     * If the workshop is selected for sending station or receiving station, station will be designated respectively.
     * @param frst :sending station or workshop
     * @param tost :receiving station or workshop
     * @param determ :Set true if the receiving station is a workshop and station needs to be designated, or false if only the
     * route needs to be checked.
     * @return :true if receiving station is designated, false if available station cannot be found.
     * @throws InvalidDefineException :Notifies if specified station no. is invalid.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public boolean directTrancefarDeterminSCH(Station frst, Station tost, boolean determ)
            throws ReadWriteException,
                InvalidDefineException
    {
        if (frst.isSendable() == true)
        {
            //<jp> 搬送元ステーション、搬送先ステーションまたは作業場とのルートチェック</jp>
            //<en> Checks the route between sending station and receiving station or the workshop.</en>
            return directTrancefarDeterminFromStation(frst, tost, determ);
        }
        else
        {
            //<jp> 搬送元作業場、搬送先ステーションまたは作業場とのルートチェック</jp>
            //<en> Checks th route between sending workshop and receiving station or workshop</en>
            if (frst instanceof WorkPlace)
            {
                return directTrancefarDeterminFromWorkPlace((WorkPlace)frst, tost, determ);
            }
            else
            {
                //<jp> FTTB ログメッセージ取得</jp>
                //<jp> 指定されたステーションは入庫作業可能な作業場またはステーションではありません。StationNo={0}</jp>
                //<en> If the receiving station is neither a warehouse or sendable station, it returns exception.</en>
                throw new InvalidDefineException("the sending staiton is invalid.");
            }
        }
    }

    /**<jp>
     * 出庫、棚間移動用の搬送ルート決定処理を行います。
     * 搬送対象パレット、搬送先ステーションNoを元にルートチェック及び搬送先決定を行います。
     * 搬送先ステーションに作業場を指定された場合、作業場の形態に合わせて搬送先ステーションの決定を行います。
     * 搬送対象パレットが棚以外の場所にある場合、どの棚に戻るか決定出来ないためにルートチェックは行いません。
     * @param plt  搬送対象パレット
     * @param tost 搬送先ステーションまたは作業場
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     </jp>*/
    /**<en>
     * Designates the carry route for retrieval and location to location move.
     * According to the carrying pallet and receiving station no., it checks the route and designates the receiving station.
     * If a workshop is selected as receiving station, it designates the recieving station according to the workshop style.
     * If the carrying pallet is located at anywhere other than shelves, the route will not be checked because it cannot
     * determine to which shelf it should return.
     * @param plt  :carrying pallet
     * @param tost :receiving station or workshop
     * @return :true if receiving station is designated, ot false if available station cannot be found.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if specified station no. is invalid.
     </en>*/
    public boolean retrievalDetermin(Pallet plt, Station tost)
            throws ReadWriteException,
                InvalidDefineException
    {
        return retrievalDetermin(plt, tost, true, false, false);
    }

    /**<jp>
     * 出庫、棚間移動用の搬送ルート決定処理を行います。
     * 搬送対象パレット、搬送先ステーションNoを元にルートチェック及び搬送先決定を行います。
     * 搬送先ステーションに作業場を指定された場合、作業場の形態に合わせて搬送先ステーションの決定を行います。
     * 搬送対象パレットが棚以外の場所にある場合、どの棚に戻るか決定出来ないためにルートチェックは行いません。
     * @param plt    搬送対象パレット
     * @param tost   搬送先ステーションまたは作業場
     * @param determ 搬送先が作業場の場合に作業場情報を更新する場合はtrue、チェックのみの場合はfalse
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     </jp>*/
    /**<en>
     * Designates the carry route for retrievals and location to location moves.
     * According to the carrying pallet and receiving station no., it checks the route and designates the receiving station.
     * If a workshop is selected as receiving station, it designates the recieving station according to the workshop style.
     * If the carrying pallet is located at anywhere other than shelves, the route will not be checked because it cannot
     * determine to which shelf it should return.
     * @param plt    :carrying pallet
     * @param tost   :receiving station or workshop
     * @param determ :true if a workshop has been selected as destination and renewing the workshop information, or false
     * if only the check will be done.
     * @return :true if the receiving station is designated, or false if available station cannot be found
     * @throws ReadWriteException Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if specified staion no. is invalid.
     </en>*/
    public boolean retrievalDetermin(Pallet plt, Station tost, boolean determ)
            throws ReadWriteException,
                InvalidDefineException
    {
        return retrievalDetermin(plt, tost, determ, false, false);
    }

    /**<jp>
     * 出庫、棚間移動用の搬送ルート決定処理を行います。
     * 搬送対象パレット、搬送先ステーションNoを元にルートチェック及び搬送先決定を行います。
     * 搬送先ステーションに作業場を指定された場合、作業場の形態に合わせて搬送先ステーションの決定を行います。
     * 搬送対象パレットが棚以外の場所にある場合、どの棚に戻るか決定出来ないためにルートチェックは行いません。
     * @param plt        搬送対象パレット
     * @param tost       搬送先ステーションまたは作業場
     * @param determ     搬送先が作業場の場合に作業場情報を更新する場合はtrue、チェックのみの場合はfalse
     * @param piconly    作業場内のピッキング出庫が可能なステーションのみをルートチェックの対象とする場合はtrueを<BR>
     *                    作業場内のユニット出庫ステーションを含めもよい場合はfalseを指定します。指定された搬送先が<code>WorkPlace<code><BR>
     *                    の場合に有効です。
     * @param removeonly 作業場内の払出し可能なステーションのみ対象ステーションとする場合はtrue。
     *                    払出し不可ステーションを加えてもよければfalseを指定します。指定された搬送先が<code>WorkPlace<code><BR>
     *                    の場合に有効です。
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Designates the carry route for retrievals and location to location moves.
     * According to the carrying pallet and receiving station no., it checks the route and designates the receiving station.
     * If a workshop is selected as receiving station, it designates the recieving station according to the workshop style.
     * If the carrying pallet is located at anywhere other than shelves, the route will not be checked because it cannot
     * determine to which shelf it should return.
     * @param plt        :carrying pallet
     * @param tost       :receiving station or workshop
     * @param determ     :true if a workshop has been selected as destination and renewing the workshop information, or false
     *                     if only the check will be done.
     * @param piconly    :Specify true if route-checking the stations where the pick retrieval is available in workshop only,
     *                     or specify false if unit retrieval stations shall be included. This is valid if specified destination is
     *                     <code>WorkPlace<code>.
     * @param removeonly :Specify true if selecting only the stations available for transfer in workshop, or specify
     *                     false if those station unavailable for transfer shall be included. This is valid if <code>WorkPlace<code> is specifed
     *                     as destination.<BR>
     * @return :true if the receiving station is designated, or false if available station cannot be found
     * @throws InvalidDefineException :Notifies if specified staion no. is invalid.
     * @throws ReadWriteException Notifies if error occured in connection with database.
     </en>*/
    public boolean retrievalDetermin(Pallet plt, Station tost, boolean determ, boolean piconly, boolean removeonly)
            throws ReadWriteException,
                InvalidDefineException
    {
        return retrievalDetermin(plt, tost, determ, piconly, removeonly, false);
    }

    /**<jp>
     * 出庫、棚間移動用の搬送ルート決定処理を行います。
     * 搬送対象パレット、搬送先ステーションNoを元にルートチェック及び搬送先決定を行います。
     * 搬送先ステーションに作業場を指定された場合、作業場の形態に合わせて搬送先ステーションの決定を行います。
     * 搬送対象パレットが棚以外の場所にある場合、どの棚に戻るか決定出来ないためにルートチェックは行いません。
     * @param plt           搬送対象パレット
     * @param tost          搬送先ステーションまたは作業場
     * @param determ        搬送先が作業場の場合に作業場情報を更新する場合はtrue、チェックのみの場合はfalse
     * @param piconly       作業場内のピッキング出庫が可能なステーションのみをルートチェックの対象とする場合はtrueを<BR>
     *                       作業場内のユニット出庫ステーションを含めもよい場合はfalseを指定します。指定された搬送先が<code>WorkPlace<code><BR>
     *                       の場合に有効です。
     * @param removeonly    作業場内の払出し可能なステーションのみ対象ステーションとする場合はtrue。
     *                       払出し不可ステーションを加えてもよければfalseを指定します。指定された搬送先が<code>WorkPlace<code><BR>
     *                       の場合に有効です。
     * @param inventoryflag 作業場の中の在庫確認中のステーションをチェックするかどうかのフラグです。false：在庫確認中のステーションをルートとして返しません。<BR>
     *                       true:在庫確認中かどうかを判断せずにルート決定を行います（在庫確認中のステーションも返します。）。<BR>
     *                       なおステーションが作業場の場合のみこのフラグは有効です。
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Designates the carry route for retrievals and location to location moves.
     * According to the carrying pallet and receiving station no., it checks the route and designates the receiving station.
     * If a workshop is selected as receiving station, it designates the recieving station according to the workshop style.
     * If the carrying pallet is located at anywhere other than shelves, the route will not be checked because it cannot
     * determine to which shelf it should return.
     * @param plt           :carrying pallet
     * @param tost          :receiving station or workshop
     * @param determ        :true if a workshop has been selected as destination and renewing the workshop information, or false
     *                        if only the check will be done.
     * @param piconly       :Specify true if route-checking the stations where the pick retrieval is available in workshop only,
     *                        or specify false if unit retrieval stations shall be included. This is valid if specified destination is
     *                        <code>WorkPlace<code>.
     * @param removeonly    :Specify true if selecting only the stations available for transfer in workshop, or specify
     *                        false if those station unavailable for transfer shall be included. This is valid if <code>WorkPlace<code> is specifed
     *                        as destination.<BR>
     * @param inventoryflag :Flag which indicates whether/not to check the stations in the inventory check procedure.
     *                        false: It will not include the station in inventory checking procedure in the route.<BR>
     *                        true: The route will be designated regardless of inventory checking is in progress at stations. (Therefore the route
     *                        may include stations on inventory checking.) This flag is valid only when the workshop is selected.
     * @return :true if the receiving station is designated, or false if available station cannot be found
     * @throws InvalidDefineException :Notifies if specified staion no. is invalid.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public boolean retrievalDetermin(Pallet plt, Station tost, boolean determ, boolean piconly, boolean removeonly,
            boolean inventoryflag)
            throws ReadWriteException,
                InvalidDefineException
    {
        return retrievalDetermin(plt, tost, determ, piconly, removeonly, inventoryflag, false, false);
    }

    /**<jp>
     * 出庫、棚間移動用の搬送ルート決定処理を行います。
     * 搬送対象パレット、搬送先ステーションNoを元にルートチェック及び搬送先決定を行います。
     * 搬送先ステーションに作業場を指定された場合、作業場の形態に合わせて搬送先ステーションの決定を行います。
     * 搬送対象パレットが棚以外の場所にある場合、どの棚に戻るか決定出来ないためにルートチェックは行いません。
     * @param plt           搬送対象パレット
     * @param tost          搬送先ステーションまたは作業場
     * @param determ        搬送先が作業場の場合に作業場情報を更新する場合はtrue、チェックのみの場合はfalse
     * @param piconly       作業場内のピッキング出庫が可能なステーションのみをルートチェックの対象とする場合はtrueを<BR>
     *                       作業場内のユニット出庫ステーションを含めもよい場合はfalseを指定します。指定された搬送先が<code>WorkPlace<code><BR>
     *                       の場合に有効です。
     * @param removeonly    作業場内の払出し可能なステーションのみ対象ステーションとする場合はtrue。
     *                       払出し不可ステーションを加えてもよければfalseを指定します。指定された搬送先が<code>WorkPlace<code><BR>
     *                       の場合に有効です。
     * @param inventoryflag 作業場の中の在庫確認中のステーションをチェックするかどうかのフラグです。false：在庫確認中のステーションをルートとして返しません。<BR>
     *                       true:在庫確認中かどうかを判断せずにルート決定を行います（在庫確認中のステーションも返します。）。<BR>
     *                       なおステーションが作業場の場合のみこのフラグは有効です。
     * @param modeflag      作業場の中のステーションのモードをチェックするかどうかのフラグです。false:不適切なモードをルートとして返しません。<BR>
     *                       true:モードが適切か判断せずにルートの決定を行います。
     * @param interruptflag  作業場の中のステーションの中断中のステーションをチェックするかどうかのフラグです。false:中断中のステーションをルートとして返しません。<BR>
     *                       true:中断中かどうか判断せずにルートの決定を行います。
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Designates the carry route for retrievals and location to location moves.
     * According to the carrying pallet and receiving station no., it checks the route and designates the receiving station.
     * If a workshop is selected as receiving station, it designates the recieving station according to the workshop style.
     * If the carrying pallet is located at anywhere other than shelves, the route will not be checked because it cannot
     * determine to which shelf it should return.
     * @param plt           :carrying pallet
     * @param tost          :receiving station or workshop
     * @param determ        :true if a workshop has been selected as destination and renewing the workshop information, or false
     *                        if only the check will be done.
     * @param piconly       :Specify true if route-checking the stations where the pick retrieval is available in workshop only,
     *                        or specify false if unit retrieval stations shall be included. This is valid if specified destination is
     *                        <code>WorkPlace<code>.
     * @param removeonly    :Specify true if selecting only the stations available for transfer in workshop, or specify
     *                        false if those station unavailable for transfer shall be included. This is valid if <code>WorkPlace<code> is specifed
     *                        as destination.<BR>
     * @param inventoryflag :Flag which indicates whether/not to check the stations in the inventory check procedure.
     *                        false: It will not include the station in inventory checking procedure in the route.<BR>
     *                        true: The route will be designated regardless of inventory checking is in progress at stations. (Therefore the route
     *                        may include stations on inventory checking.) This flag is valid only when the workshop is selected.
     * @param modeflag      :
     * @param interruptflag  :
     * @return :true if the receiving station is designated, or false if available station cannot be found
     * @throws InvalidDefineException :Notifies if specified staion no. is invalid.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     </en>*/
    public boolean retrievalDetermin(Pallet plt, Station tost, boolean determ, boolean piconly, boolean removeonly,
            boolean inventoryflag, boolean modeflag, boolean interruptflag)
            throws ReadWriteException,
                InvalidDefineException
    {
        _routeStatus = UNKNOWN;
        Station frst = null;
        Station curst = null;
        try
        {
            Connection connection = getConnection();
            //<jp> パレットの現在位置よりステーションを生成する。</jp>
            //<en> Generates station based on the current position of pallet.</en>
            curst = StationFactory.makeStation(connection, plt.getCurrentStationNo());

            //<jp> アイル独立ステーションの集合の場合、パレットから搬送可能なステーションを搬送先ステーションとする。</jp>
            //<en> If handling a set of stand alone style stations, destined station must be the stations</en>
            //<en> where the carry can be carried out from pallet.</en>
            if (curst instanceof Shelf)
            {
                //<jp> 現在位置が棚の場合、棚の所属する親ステーションを搬送元にする。</jp>
                //<en> In case the current position is a shelf, select the parent station as a sender</en>
                //<en> that the shlf belongs to.</en>
                frst = StationFactory.makeStation(connection, curst.getParentStationNo());
            }
            else
            {
                //<jp> 現在位置がステーションの場合、ステーションのアイルステーションNoを搬送元ステーションにする。</jp>
                //<en> In case the current position is a station, select the aisle station no. of the station</en>
                //<en> as sending station.</en>
                frst = StationFactory.makeStation(connection, curst.getAisleStationNo());
            }

            //<jp> 搬送先ステーションが作業場の場合、搬送先ステーション決定処理を行う。</jp>
            //<en> If the receiving station is workshop, should process the designation of receiving station.</en>
            if (tost instanceof WorkPlace)
            {
                WorkPlace wp = (WorkPlace)tost;
                String[] stnums = wp.getWPStations();

                /*<jp>-------------------------------------
                 * アイル独立型作業場のステーション決定
                 *-------------------------------------</jp>*/
                /*<en>--------------------------------------------------------
                 * Designation of station with stand alone type workshop
                 *--------------------------------------------------------</en>*/
                if (WorkPlace.WORKPLACE_TYPE_STAND_ALONE_STATIONS.equals(wp.getWorkplaceType()))
                {
                    //<jp> 搬送ルート定義の存在しないステーションの個数</jp>
                    //<en> Number of stations which has no definition for the carry route</en>
                    int notFoundCnt = 0;

                    //<jp> 正常なルートが見つかるまで繰り返す。</jp>
                    //<en> Repears until a normal route should be found.</en>
                    for (int stcnt = 0; stcnt < stnums.length; stcnt++)
                    {
                        //<jp> パレットの現在ステーションが所属するアイルステーションNoと、作業場内のステーションが所属する</jp>
                        //<jp> アイルステーションNoが一致すれば、搬送可能なステーションとしルートチェックを行う。</jp>
                        //<en> Determines it is the station that carry can be carried out if the aisle station no. which </en>
                        //<en> current station of pallet belongs to and the aisle no. that station in the workshop</en>
                        //<en> belongs to matches. Route also will be cheked.</en>
                        Station nextst = wp.getWPStation(connection);
                        if (nextst.getAisleStationNo().equals(frst.getStationNo()))
                        {
                            // 代表ステーションの場合、紐づくステーションのチェックを行う
                            // 一つでも出庫可能ステーションがあればOKとする
                            if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(nextst.getWorkplaceType()))
                            {
                                if (!mainStationDetermin(nextst, frst, piconly, removeonly, inventoryflag, modeflag,
                                        interruptflag))
                                {
                                    continue;
                                }
                            }
                            // 通常ステーション
                            else
                            {
                                // 出庫可能ステーションかチェックを行う
                                if (!checkRetrievalStation(nextst, piconly, removeonly, inventoryflag, modeflag,
                                        interruptflag))
                                {
                                    continue;
                                }

                                // 搬送ルートチェック
                                if (!routeDetermin(frst, nextst))
                                {
                                    continue;
                                }
                            }

                            if (determ)
                            {
                                //アイル独立型全体作業場でのルート決定時に、作業場ステーションのLastUsedStationNoを
                                //更新しても均等に作業を割り振れないので、子作業場の最終使用ステーションも更新する。
                                updateLastStaion(_destStation);

                                //<jp> 作業場の最終使用ステーションを更新</jp>
                                //<en> Updates the station of end-use in workshop.</en>
                                StationOperator sop = new StationOperator(connection, tost.getStationNo());
                                sop.alterLastUsedStation(_destStation.getStationNo());
                            }
                            return true;
                        }
                        else
                        {
                            //<jp> 作業場内のステーションが参照するアイルとパレットが存在する棚のアイルが一致しなければ</jp>
                            //<jp> カウントする。</jp>
                            //<en> Counts if the aisle which the station of workshop refers to differs from the aisle</en>
                            //<en> of shelf where the pallet exists.</en>
                            notFoundCnt++;
                        }
                    }

                    if (notFoundCnt >= stnums.length)
                    {
                        //<jp> 今回指定された作業場内のステーションとパレット間のルートが全て存在しない場合</jp>
                        //<jp> ルート定義無しを状態にセットする。</jp>
                        //<en> If any route between this specified station of workshop and the pallet does not exist at all,</en>
                        //<en> it sets 'no route is defined' in status.</en>
                        _routeStatus = NOTFOUND;
                    }

                    //<jp> 全ステーション搬送不可</jp>
                    //<jp> wRouteStatusにはNO_STATION_INTO_WORKPLACEまたはルートチェックの結果がセットされたものが</jp>
                    //<jp> セットされているはずなのでそのまま返す。</jp>
                    //<en> Carry unavailable at all stations.</en>
                    //<en> NO_STATION_INTO_WORKPLACE or the result of route check must have been set in wRouteStatus, </en>
                    //<en> therefore, it returns as it is.</en>
                    return false;
                }
                /*<jp>-------------------------------------
                 * アイル結合型作業場のステーション決定
                 *-------------------------------------</jp>*/
                /*<en>------------------------------------------------------------
                 * Designation of station with aisle connected type workshop
                 *------------------------------------------------------------</en>*/
                else
                {
                    //<jp> アイル結合ステーションの集合の場合、優先ステーションの決定を行う。</jp>
                    //<en> If handling a set of aisle connected type stations, designates the prioritized stations.</en>
                    for (int i = 0; i < stnums.length; i++)
                    {
                        //<jp> 作業場より優先ステーションを取得する</jp>
                        //<en> Retrieves prioritized station from the workshop.</en>
                        Station nextst = wp.getWPStation(connection);

                        // 代表ステーションの場合、紐づくステーションのチェックを行う
                        // 一つでも出庫可能ステーションがあればOKとする
                        if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(nextst.getWorkplaceType()))
                        {
                            if (!mainStationDetermin(nextst, frst, piconly, removeonly, inventoryflag, modeflag,
                                    interruptflag))
                            {
                                continue;
                            }
                        }
                        // 通常ステーション
                        else
                        {
                            // 出庫可能ステーションかチェックを行う
                            if (!checkRetrievalStation(nextst, piconly, removeonly, inventoryflag, modeflag,
                                    interruptflag))
                            {
                                continue;
                            }
                        }

                        // 搬送ルートチェック
                        if (routeDetermin(frst, nextst))
                        {
                            if (determ)
                            {
                                String pst = tost.getStationNo();

                                // 全体作業場/全ステーションの場合
                                if (WmsParam.ALL_STATION.equals(tost.getStationNo()))
                                {
                                    // 搬送先が属する作業場を取得
                                    StationSearchKey pstKey = new StationSearchKey();
                                    pstKey.setParentStationNoCollect();
                                    pstKey.setStationNo(_destStation.getStationNo());

                                    Station[] st = (Station[])(new StationHandler(getConnection())).find(pstKey);

                                    if (!StringUtil.isBlank(st[0].getParentStationNo()))
                                    {
                                        pst = st[0].getParentStationNo();

                                        Station chkst = StationFactory.makeStation(connection, pst);
                                        if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(chkst.getWorkplaceType()))
                                        {
                                            // 代表ステーションの場合は最終使用ステーションの更新不要
                                            return true;
                                        }
                                    }
                                    else
                                    {
                                        // 作業場なしの場合は最終使用ステーションの更新不要
                                        return true;
                                    }
                                }

                                //<jp> 作業場の最終使用ステーションを更新</jp>
                                //<en> Updates the station of end-use in workshop.</en>
                                StationOperator sop = new StationOperator(connection, pst);
                                sop.alterLastUsedStation(_destStation.getStationNo());
                            }
                            return true;
                        }
                    }

                    //<jp> 全搬送ルートNG</jp>
                    //<en> All carry route are not acceptable.</en>
                    return false;
                }
            }
            //<jp> 生成された搬送先ステーションが通常ステーションの場合、そのステーションを搬送先とする。</jp>
            //<en> If a normal station is generated for a destined station, it selects that station as destination.</en>
            else
            {
                // 代表ステーションの場合、紐づくステーションのチェックを行う
                // 一つでも出庫可能ステーションがあればOKとする
                if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(tost.getWorkplaceType()))
                {
                    return mainStationDetermin(tost, frst, piconly, removeonly, inventoryflag, modeflag, interruptflag);
                }
                // 通常ステーション
                else
                {
                    //<jp> 搬送先ステーションがアイル独立かアイル結合レイアウトか確認</jp>
                    //<en> Verifies the type of the destined station : either stand alone type or aisle connected type</en>
                    if (StringUtil.isBlank(tost.getAisleStationNo()))
                    {
                        return routeDetermin(frst, tost);
                    }
                    else
                    {
                        //<jp> 搬送先ステーションに親ステーションNo（アイルステーション）がセットされている場合</jp>
                        //<jp> アイル独立ステーションなので、そのアイルに属する棚のみ搬送可能とする。</jp>
                        //<en> If the parent station no.(aisle station) is set as destined station,</en>
                        //<en> it should be stand alone type station; carry is available only for shelves that belong to that aisle.</en>
                        if (tost.getAisleStationNo().equals(frst.getStationNo()))
                        {
                            return routeDetermin(frst, tost);
                        }

                        //<jp> 搬送ルートがみつからない場合、搬送ルート無しの状態をセットしてfalseで戻る</jp>
                        //<en> If no carry route is found, set 'there is no carry route' to the status and return by false.</en>
                        _routeStatus = NOTFOUND;
                        return false;
                    }
                }
            }
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
    }

    /**<jp>
     * 入力されたステーションが入庫が可能なステーションであるかどうかを判断します。<BR>
     * このメソッドは現在RouteControllerから呼ばれています。<BR>
     * スケジューラは上の2つのメソッド（isInStation、isStorageMode）をそれぞれ呼んでください。<BR>
     * スケジューラで２つのメソッドを呼ぶ理由はタイプが違うのかモードが違うのかをメッセージ表示させるためです。<BR
     * @param st    確認するステーション
     * @param check オンラインチェックを行うかどうかのフラグ。trueオンラインチェックを行う。false:オンラインチェックを行わない。
     * @return 入力されたステーションが入庫可能ステーションならばtrueそれ以外ならfalseを返します。
     </jp>*/
    /**<en>
     * Determines whether/not the entered station is storage ready.<BR>
     * This method is currently called by RouteController.<BR>
     * Please call 2 methods above (isInStation and isStorageMode) respectively for scheduler.<BR>
     * These 2 methods are called as a message is wanted which indicates either the type differs or
     * the mode differs.<BR
     * @param st    :station to check
     * @param check :flag which indicates whether/not to on-line check. It returns true if on-line check
     * to be done and false if no on-line check is to be done.
     * @return :true if entered station is storage ready, or false for other cases.
     </en>*/
    public boolean isStorageStation(Station st, boolean check)
    {
        try
        {
            Connection connection = getConnection();
            //<jp> オンラインチェックを行う。</jp>
            //<en> On-line checking</en>
            if (check)
            {
                GroupController groupCon = null;
                if (_hashGC.containsKey(st.getStationNo()))
                {
                    groupCon = (GroupController)_hashGC.get(st.getStationNo());
                }
                else
                {
                    groupCon = GroupController.getInstance(connection, st.getControllerNo());
                    _hashGC.put(st.getStationNo(), groupCon);
                }

                //<jp> システム状態のチェック</jp>
                //<en> Checks the status of the system.</en>
                if (groupCon.getStatus() != GroupController.STATUS_ONLINE)
                {
                    _routeStatus = AGC_OFFLINE; // AGC OFFLINE
                    return false;
                }
            }

            //<jp> モードのチェック</jp>
            //<en> Checks the mode.</en>
            if (!st.isStorageMode())
            {
                return false;
            }

            //<jp> タイプのチェック</jp>
            //<en> Checks the type.</en>
            if (!st.isInStation())
            {
                return false;
            }

            //<jp> 中断中フラグのチェック</jp>
            //<en> Checks the suspention flag.</en>
            if (st.isSuspend())
            {
                return false;
            }

            if (StringUtil.isBlank(st.getAisleStationNo()))
            {
                //<jp> アイルステーションがセットされていない場合は、アイル結合レイアウトなので</jp>
                //<jp> 作業ステーションの状態のみチェックする。</jp>
                //<en> If the aisle station is not set, the station should be the aisle connected style.</en>
                //<en> Therefore it only checks the status of work station.</en>
                if (!Station.STATION_STATUS_NORMAL.equals(st.getStatus()))
                {
                    //<jp> 切り離し区分をセットする。</jp>
                    //<en> Sets the AGC off-line classification.</en>
                    _routeStatus = OFFLINE;
                    return false;
                }
            }
            else
            {
                //<jp> アイルステーションが設定されている場合</jp>
                //<jp> 作業ステーションとアイルステーション間で搬送ルートチェックを実施</jp>
                //<en> If hte aisle station is set, </en>
                //<en> checks the carry route between work station and the aisle station.</en>
                Station tst = StationFactory.makeStation(connection, st.getAisleStationNo());
                if (routeDetermin(st, tst) == false)
                {
                    return false;
                }
            }

            AisleOperator aop = new AisleOperator();

            //<jp> 在庫確認中かどうかのチェック</jp>
            //<en> Checks whether/not the inventory is being checked.</en>
            if (aop.isInventoryCheck(connection, st.getStationNo()))
            {
                //<jp> 在庫確認中のメッセージをセット</jp>
                //<en> Sets the message classification for inventory check in progress</en>
                _routeStatus = AISLE_INVENTORYCHECK;
                return false;
            }

            //<jp> 空棚確認中かどうかのチェック</jp>
            //<en> Checks whether/not the empty location is being checked.</en>
            if (aop.isEmptyLocationCheck(connection, st.getStationNo()))
            {
                //<jp> 空棚確認中のメッセージをセット</jp>
                //<en> Sets the message classification for empty check in progress</en>
                _routeStatus = AISLE_EMPTYLOCATIONCHECK;
                return false;
            }
            return true;
        }
        catch (ReadWriteException e)
        {
            return false;
        }
        catch (InvalidDefineException e)
        {
            return false;
        }
        catch (NotFoundException e)
        {
            return false;
        }
    }

    /**<jp>
     * 入力されたステーションが出庫が可能であるステーションであるかどうかを判断します。<BR>
     * このメソッドは現在RouteControllerから呼ばれています。<BR>
     * スケジューラは上の2つのメソッド（isOutStation、isRetrievalMode）をそれぞれ呼んでください。<BR>
     * スケジューラで２つのメソッドを呼ぶ理由はタイプが違うのかモードが違うのかをメッセージで表示させるためです。<BR>
     * @param st            確認するステーション
     * @param inventoryflag 在庫確認中をチェックするかどうかのフラグ true:チェックしない。false：在庫確認中かどうかをチェックする。
     * @param check         オンラインチェックを行うかどうかのフラグ。trueオンラインチェックを行う。false:オンラインチェックを行わない。
     * @param modeflag      作業場の中のステーションのモードをチェックするかどうかのフラグです。false:不適切なモードをルートとして返しません。<BR>
     * @param interruptflag  作業場の中のステーションの中断中のステーションをチェックするかどうかのフラグです。false:中断中のステーションをルートとして返しません。<BR>
     * @return 入力されたステーションが出庫可能ステーションならばtrueそれ以外ならfalseを返します。
     </jp>*/
    /**<en>
     * Determines if the entered station is retrieval ready.<BR>
     * This method is currently called by RouteController.<BR>
     * Scheduler shuold call 2 methods above (isOutStation and isRetrievalMode) respectively.<BR>
     * These 2 methods are called by scheduler as a message is wanted which indicates either the type
     * differs or the mode differs. <BR>
     * @param st            :station to check
     * @param inventoryflag :Flag which indicates whether/not to check the inventory checking status.
     *                        It returns - true: no check to be done, false: check to see if the inventory check is being done.
     * @param check         :Flag which indicates whrther/not to on-line check. it returns true : on-line check
     *                        to be done, false: no on-line check is to be done.
     * @param modeflag       :
     * @param interruptflag  :
     * @return :returns true if enterd station is retrieval ready. or false for any other cases.
     </en>*/

    public boolean isRetrievalStation(Station st, boolean inventoryflag, boolean check, boolean modeflag,
            boolean interruptflag)
    {
        try
        {
            Connection connection = getConnection();
            //<jp> オンラインチェックを行う。</jp>
            //<en> On-line checks.</en>
            if (check)
            {
                GroupController groupCon = null;
                if (_hashGC.containsKey(st.getStationNo()))
                {
                    groupCon = (GroupController)_hashGC.get(st.getStationNo());
                }
                else
                {
                    groupCon = GroupController.getInstance(connection, st.getControllerNo());
                    _hashGC.put(st.getStationNo(), groupCon);
                }

                //<jp> システム状態のチェック</jp>
                //<en> Checks the sytem status.</en>
                if (groupCon.getStatus() != GroupController.STATUS_ONLINE)
                {
                    _routeStatus = AGC_OFFLINE; // AGC OFFLINE
                    return false;
                }
            }

            if (!modeflag)
            {
                //<jp> モードのチェック</jp>
                //<en> Checks the mode.</en>
                if (!st.isRetrievalMode())
                {
                    return false;
                }
            }

            //<jp> タイプのチェック</jp>
            //<en> Checks the type.</en>
            if (!st.isOutStation())
            {
                return false;
            }

            if (!interruptflag)
            {
                //<jp> 中断中フラグのチェック</jp>
                //<en> Checks the suspention flag.</en>
                if (st.isSuspend())
                {
                    return false;
                }
            }

            AisleOperator aop = new AisleOperator();

            if (!inventoryflag)
            {
                //<jp> 在庫確認中かどうかのチェック</jp>
                //<en> Checks whether/not the inventory check is done.</en>
                if (aop.isInventoryCheck(connection, st.getStationNo()))
                {
                    //<jp> 在庫確認中のメッセージをセット</jp>
                    //<en> Sets the message classification for inventory check in progress</en>
                    _routeStatus = AISLE_INVENTORYCHECK;
                    return false;
                }
            }
            //<jp> 空棚確認中かどうかのチェック</jp>
            //<en> Checks whether/not the empty location check is done.</en>
            if (aop.isEmptyLocationCheck(connection, st.getStationNo()))
            {
                //<jp> 空棚確認中のメッセージをセット</jp>
                //<en> Sets the message classification for empty check in progress</en>
                _routeStatus = AISLE_EMPTYLOCATIONCHECK;
                return false;
            }
            return true;
        }
        catch (ReadWriteException e)
        {
            return false;
        }
        catch (InvalidDefineException e)
        {
            return false;
        }
        catch (NotFoundException e)
        {
            return false;
        }
    }

    /**<jp>
     * 搬送ルート決定処理を行います。
     * 搬送対象パレット、搬送先ステーションを元にルートチェック及び搬送先決定を行います。
     * ルートが使用可能であれば、搬送元、搬送先をインスタンス変数に登録します。
     * ルートチェックの結果にかかわらず、ルートの状態を記憶します。
     * @param frst 搬送元ステーション
     * @param tost 搬送先ステーション
     * @return 搬送ルートが使用可能な場合はtrue、搬送ルートが使用不可の場合はfalse
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException クラス定義が、正しくなかった場合に通知されます。
     * @throws NotFoundException 該当のクラスが見つからなかった場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the carry route designation.
     * According to the carrying apllet and destined station, it checks routes and designates the destination.
     * If the route is usable, it registers the sending station and destined station as instance variables.
     * Regardless of the results of route checks, it stores the status of routes.
     * @param frst :sending station
     * @param tost :destined station
     * @return :'true' if the carry route is usable, or 'false' if the route is unusable.
     * @throws ReadWriteException     :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if the definition of the class was incorrect.
     * @throws NotFoundException   :Notifies if such class was not found.
     </en>*/
    public boolean routeDetermin(Station frst, Station tost)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        //<jp> ルートチェック用ステーション定義</jp>
        //<en> Definition of station for route checks</en>
        Station fromStation = null;
        Station toStation = null;

        Connection connection = getConnection();
        //<jp> 搬送元、搬送先ステーションがそれぞれ棚の場合、ルートチェックに使えないので親ステーションを取得する。</jp>
        //<en> If the sending station and the destined are both shelves, they cannot be used in route checks.</en>
        //<en> Therefore the parent station must be retrieved.</en>
        if (frst instanceof Shelf)
        {
            fromStation = StationFactory.makeStation(connection, frst.getParentStationNo());
        }
        else
        {
            fromStation = frst;
        }

        if (tost instanceof Shelf)
        {
            toStation = StationFactory.makeStation(connection, tost.getParentStationNo());
        }
        else
        {
            toStation = tost;
        }

        // 棚間移動でなければ同一アイルチェック
        if ((!(frst instanceof Shelf) && !(frst instanceof Aisle))
                || (!(tost instanceof Shelf) && !(tost instanceof Aisle)))
        {
            // アイル結合型STの場合は同一アイルかチェックする
            if (!StringUtil.isBlank(fromStation.getAisleStationNo())
                    && !StringUtil.isBlank(toStation.getAisleStationNo()))
            {
                if (!fromStation.getAisleStationNo().equals(toStation.getAisleStationNo()))
                {
                    _routeStatus = NOTFOUND;
                    return false;
                }
            }
        }

        //<jp> ルートチェック保持結果を記録するHashtableを検索するキーを生成</jp>
        //<en> Generates a key to search Hashtable in order to store the results of route checks.</en>
        String routeKey = fromStation.getStationNo() + toStation.getStationNo();

        //<jp> 既にルートチェック済みのルートの場合、保持している結果を返す。</jp>
        //<en> If the route has been already route-checked, it should return the result preserved.</en>
        if (_alwaysCheck == false)
        {
            if (_routeHashtable.containsKey(routeKey))
            {
                Object obj = _routeHashtable.get(routeKey);
                Route saveRoute = (Route)obj;
                if (saveRoute.getStatus())
                {
                    _srcStation = frst;
                    _destStation = tost;
                    _routeStatus = ACTIVE;
                    return true;
                }
                else
                {
                    _srcStation = null;
                    _destStation = null;
                    switch (saveRoute.getRouteStatus())
                    {
                        case Route.ACTIVE:
                            _routeStatus = ACTIVE;
                            break;

                        case Route.NOT_ACTIVE_OFFLINE:
                            _routeStatus = OFFLINE;
                            break;

                        case Route.NOT_ACTIVE_FAIL:
                            _routeStatus = FAIL;
                            break;

                        case Route.UNKNOWN:
                            _routeStatus = UNKNOWN;
                            break;

                        case Route.NOTFOUND:
                            _routeStatus = NOTFOUND;
                            break;

                        case Route.PROHIBITED:
                            _routeStatus = NOTFOUND;
                            break;

                        default:
                            throw new InvalidDefineException(
                                    "Unexpected status was returned by route check. status code=");
                    }

                    return false;
                }
            }
        }

        // ReservedRouteのチェックを行う、開始Stから終了St間の特別ルートセットを行っている場合、優先的にチェックする。
        ReservedRoute routeSp = ReservedRoute.getInstance(connection, fromStation, toStation);
        if (!StringUtil.isBlank(routeSp))
        {
            if (routeSp.check())
            {
                //<jp> ルートが使用可能であれば、搬送元、搬送先を登録</jp>
                //<en> If the route is useable, it registers the sending station and the destined station.</en>
                _srcStation = frst;
                _destStation = tost;
                _routeStatus = ACTIVE;

                return true;
            }
            else
            {
                if (routeSp.getRouteStatus() == ReservedRoute.PROHIBITED)
                {
                    _routeStatus = NOTFOUND;
                    return false;
                }
            }
        }
        //<jp> まだルートチェック未実施の場合はルートチェックを実施</jp>
        //<en> If the route check has not been done, it should conduct the route-check.</en>
        Route route = Route.getInstance(connection, fromStation, toStation);
        if (route.check())
        {
            //<jp> ルートが使用可能であれば、搬送元、搬送先を登録</jp>
            //<en> If the route is useable, it registers the sending station and the destined station.</en>
            _srcStation = frst;
            _destStation = tost;
            _routeStatus = ACTIVE;
            //<jp> 常にルートチェックをしない場合、今回の結果を記録</jp>
            //<en> If the route will not always be checked, it registers the result of this.</en>
            if (_alwaysCheck == false)
            {
                _routeHashtable.put(routeKey, route);
            }

            return true;
        }
        else
        {
            //<jp> ルートが使用不可であれば、搬送元、搬送先にNULLをセット</jp>
            //<en> If the route is unusable, set NULL for sending station and the destined station.</en>
            _srcStation = null;
            _destStation = null;
            switch (route.getRouteStatus())
            {
                case Route.NOT_ACTIVE_OFFLINE:
                    _routeStatus = OFFLINE;
                    break;

                case Route.NOT_ACTIVE_FAIL:
                    _routeStatus = FAIL;
                    break;

                case Route.UNKNOWN:
                    _routeStatus = UNKNOWN;
                    break;

                case Route.NOTFOUND:
                    _routeStatus = NOTFOUND;
                    break;

                case Route.PROHIBITED:
                    _routeStatus = NOTFOUND;
                    break;

                default:
                    throw new InvalidDefineException("Unexpected status was returned by route check. status code=");
            }

            //<jp> 常にルートチェックをしない場合、今回の結果を記録</jp>
            //<en> If the route will not always be checked, it registers the result of this.</en>
            if (_alwaysCheck == false)
            {
                _routeHashtable.put(routeKey, route);
            }

            return false;
        }
    }

    /**<jp>
     *  ルート決定処理で決定された搬送元ステーションを返します。
     * @return 搬送元ステーション
     </jp>*/
    /**<en>
     * Returns the sending station designated in route designation process.
     * @return :sending station
     </en>*/
    public Station getSrcStation()
    {
        return _srcStation;
    }

    /**<jp>
     *  ルート決定処理で決定された搬送先ステーションを返します。
     * @return 搬送先ステーション
     </jp>*/
    /**<en>
     * Returns the desitned station designated in route designation process.
     * @return :destined station
     </en>*/
    public Station getDestStation()
    {
        return _destStation;
    }

    /**<jp>
     * ルートチェック処理の結果セットされた状態を返します。<BR>
     * ルートチェックを行っていない場合は<code>UNKNOWN</code>が返ります。<BR>
     * @return ルートチェックの結果。ルートチェックを行っていない場合は<code>UNKNOWN</code>が返ります。
     </jp>*/
    /**<en>
     * Returns status which has been set with result of route checks.<BR>
     * If route check has not been done, <code>UNKNOWN</code> will return.<BR>
     * @return :result of route check.  <code>UNKNOWN</code> will return if route check has not been done.
     </en>*/
    public int getRouteStatus()
    {
        return _routeStatus;
    }

    /**<jp>
     * 倉庫の中から、空棚を検索します。
     * このインスタンスに保持されているゾーン管理種別に応じて<code>ShelfSelector</code>クラスの生成を行い、<BR>
     * 空棚検索を行います。ShelfSelectorで必要となるコネクションはこのインスタンス内のハンドラクラスより取得します。
     * @param plt  空棚検索対象のパレット。
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @return 検索された棚のインスタンス。
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Searches the empty location in the warehouse.
     * The empty location will be searched by generating the <code>ShelfSelector</code> class
     * according to the zone control type preserved by this instance.<BR>
     * The connection required in ShelfSelector will be obtained in handler class in this instance.
     * @param plt   :pallet subject to empty location search
     * @param empLocDeterm     :true if a empty location has been selected as destination and renewing the shelf information, or false
     *                     if only the check will be done.
     * @return      :instance of searched shelf
     * @throws ReadWriteException :Notifies if exception occured when processing for database.
     * @throws InvalidDefineException :Notifies if there are any data inconsistency in table.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    public Shelf selectShelf(Pallet plt, boolean empLocDeterm)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        ShelfSelector ssel = null;
        if (_saveflg && _ssel != null)
        {
            //<jp> 保持しているShelfSelectorをセットする。</jp>
            ssel = _ssel;
        }
        else
        {
            //<jp> ゾーン一覧を取得する。</jp>
            //<en> Obtains a list of zone.</en>
            ZoneSelector zsel = getZoneSelector();
            ssel = new DepthShelfSelector(getConnection(), zsel, _saveflg);

            if (_saveflg)
            {
                //<jp> ShelfSelectorを保持する。</jp>
                _ssel = ssel;
            }
        }

        //<jp> 空棚検索処理を開始する。</jp>
        //<en> Start the empty location search.</en>
        return selectShelf(ssel, plt, empLocDeterm);
    }

    /**<jp>
     * 倉庫の中から、棚を検索します。
     * 実際の検索には、内部でShelfSelectorを利用しています。
     * @param ssel 倉庫検索のための<code>ShelfSelector</code>。
     * @param plt  対象のパレット。
     * @param empLocDeterm  空棚を決定し更新処理を行なう場合はtrue、チェックのみの場合はfalse
     * @return 検索された棚のインスタンス。
     * @throws ReadWriteException データベースの処理で発生した場合に通知されます。
     * @throws InvalidDefineException テーブル内のデータに不整合があった場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     * @see ShelfSelector
     </jp>*/
    /**<en>
     * Searches the shelf in the warehouse.
     * For actulasearch, it internally utilizes the ShelfSelector.
     * @param ssel :<code>ShelfSelector</code> to search warehouse
     * @param plt  :target pallet
     * @param empLocDeterm     :true if a empty location has been selected as destination and renewing the shelf information, or false
     *                     if only the check will be done.
     * @return  :instance of the searched shelf
     * @throws ReadWriteException :Notifies if exception occured when processing for database.
     * @throws InvalidDefineException :Notifies if there are any data inconsistency in table.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     * @see ShelfSelector
     </en>*/
    public Shelf selectShelf(ShelfSelector ssel, Pallet plt, boolean empLocDeterm)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        Shelf fshelf = null;

        WareHouseSearchKey key = new WareHouseSearchKey();
        key.setStationNo(plt.getWhStationNo());
        WareHouseHandler whHandler = new WareHouseHandler(getConnection());
        WareHouse[] wh = (WareHouse[])whHandler.find(key);
        fshelf = ssel.select(plt, wh[0], empLocDeterm);

        return fshelf;
    }

    /**<jp>
     * <code>ZoneSelector</code>クラスのインスタンス生成を行います。
     * <code>ZoneSelector</code>で必要となるコネクションはこのインスタンス内のハンドラクラスより取得します。
     * @return 決定された<code>ZoneSelector</code>クラスのインスタンス
     </jp>*/
    /**<en>
     * Generates an instance of <code>ZoneSelector</code> class.
     * The connection which will be required in <code>ZoneSelector</code> will be obtained by handler calss
     * of this instance.
     * @return instance of <code>ZoneSelector</code> class, designated
     </en>*/
    public ZoneSelector getZoneSelector()
    {
        //<jp> ソフトゾーン、ハードゾーン併用のZoneSelectorインスタンスの生成を行う。</jp>
        //<en> Generates the instance of ZoneSelector for simultaneous use for soft zone/hard zone.</en>
        ZoneSelector zsel = new CombineZoneSelector(getConnection());

        return zsel;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**<jp>
     * 入庫の搬送ルートチェック処理を行います。
     * 搬送元ステーション、搬送先ステーションを元にルートチェックを行います。<BR>
     * @param plt   空棚検索情報を含む<code>Pallet</code>インスタンス
     * @param frst  搬送元ステーション
     * @param toshf 搬送先ステーション
     * @return 搬送元ステーションと搬送先ステーション間での搬送が可能な場合はtrue、搬送不可能な場合はfalse
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the carry route check for storage.
     * It checks the route according to the sending station and the destined station.<BR>
     * @param plt   :<code>Pallet</code> instance which containes the empty search information.
     * @param frst  :sending station
     * @param toshf :destined station
     * @return : 'true' if the carry is possible between the sending station and the destined station,
     * or 'false' if the carry is not possible.
     * @throws InvalidDefineException :Notifies if specified station no. is invalid.
     * @throws ReadWriteException  :Notifies if error occured in connection with database.
     </en>*/
    protected boolean shelfDeterminFromStation(Pallet plt, Station frst, Shelf toshf)
            throws ReadWriteException,
                InvalidDefineException
    {
        try
        {
            //<jp> frstが入庫可能可能ステーションかどうかをチェックします。</jp>
            //<en> Checks if frst is available for storage.</en>
            if (!isStorageStation(frst, _offLineCheck))
            {
                _srcStation = null;
                _destStation = null;
                return false;
            }

            Connection connection = getConnection();

            //<jp> 搬送元ステーションがアイル独立かアイル結合レイアウトか確認</jp>
            //<en> Verifies the type of sending station, either the stand alone type or the aisle connected type.</en>
            if (StringUtil.isBlank(frst.getAisleStationNo()))
            {
                //<jp> 搬送元と棚の親ステーション（アイル）でルートチェック実行</jp>
                //<en> Carries out the route-check for sending station and the parent station of the shelf (aisle)</en>
                if (routeDetermin(frst, StationFactory.makeStation(connection, toshf.getParentStationNo())))
                {
                    //<jp> ルートチェックに成功すると、搬送先がアイルとなるため</jp>
                    //<jp> 棚に戻しておく</jp>
                    //<en> If the route is successfully checked, the destination will </en>
                    //<en> be swithced to aisle; therefore it shuold reset to a shelf again.</en>
                    _destStation = toshf;
                    return true;
                }

                return false;
            }
            else
            {
                //<jp> 搬送先ステーションに親ステーションNo（アイルステーション）がセットされている場合</jp>
                //<jp> アイル独立ステーションなので、そのアイルに属する棚のみ搬送可能とする。</jp>
                //<en> If a parent station no.(aisle station) is set as destined station,</en>
                //<en> the station should be the stand alone type; it regards that only shelves that belong to that </en>
                //<en> aisle are available for the carry.</en>
                if (frst.getAisleStationNo().equals(toshf.getParentStationNo()))
                {
                    //<jp> 現在位置が棚の場合、棚の親ステーション（アイル）と搬送元ステーションでルートチェック実行</jp>
                    //<en> If a shelf is the current position, route-checks for the parent station of the shelf (aisle)</en>
                    //<en> and the sending station.</en>
                    if (routeDetermin(frst, StationFactory.makeStation(connection, toshf.getParentStationNo())))
                    {
                        //<jp> ルートチェックに成功すると、搬送先がアイルとなるため</jp>
                        //<jp> 棚に戻しておく</jp>
                        //<en> If the route is successfully checked, the destination will </en>
                        //<en> be swithced to aisle; therefore it shuold reset to a shelf again.</en>
                        _destStation = toshf;
                        return true;
                    }

                    return false;
                }
                else
                {
                    //<jp> 今回指定されたステーションとアイルの繋がりが存在しない場合</jp>
                    //<jp> ルート定義無しを状態にセットする。</jp>
                    //<en> If there is no relation between this station specified and the aisle,</en>
                    //<en> set 'no route is defined' to the status.</en>
                    _routeStatus = NOTFOUND;
                    return false;
                }
            }
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
    }

    /**<jp>
     * 入庫空棚検索処理を行います。
     * 搬送元ステーションと搬送先となる倉庫を元に入庫棚の決定を行います。<BR>
     * @param plt  空棚検索情報を含む<code>Pallet</code>インスタンス
     * @param frst 搬送元ステーション
     * @param wh   入庫先の倉庫を示す<code>WareHouse</code>インスタンス
     * @return 搬送元ステーションと入庫先倉庫との搬送が可能な場合はtrue、搬送不可能な場合はfalse
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Processing the empty location search for storage.
     * According to the sending station and the warehouse destined, it designates the shelf for the storage.<BR>
     * @param plt  :instance of <code>Pallet</code> which containes the information of empty location search.
     * @param frst :sending station
     * @param wh   :<code>WareHouse</code> instance which indicates the warehouse to store.
     * @return : true if the carry for sending station and storing warehouse is possible, or false if carry is not possible.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws InvalidDefineException : Notifies if specified station no. is invalid.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    protected boolean shelfDeterminFromStation(Pallet plt, Station frst, WareHouse wh)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        try
        {
            //<jp> frstが入庫可能可能ステーションかどうかをチェックします。</jp>
            //<en> Checks if frst is available for storage.</en>
            if (!isStorageStation(frst, _offLineCheck))
            {
                _srcStation = null;
                _destStation = null;
                return false;
            }

            // 空棚決定
            boolean empLocDeterm = true;
            Connection connection = getConnection();
            //<jp> 搬送元ステーションが荷姿検出有りの場合、空棚検索は行わない</jp>
            //<jp> アイル独立型ステーションの場合はルートチェックを行う。</jp>
            //<en> If the sending station operates the load size detections, it does not conduct the empty location search.</en>
            //<en> Route will be checked if the station is stand alone type.</en>
            if (frst.isLoadSizeCheck())
            {
                // 荷姿検知器ありの場合は空棚決定は行なわず、空棚存在チェックのみ行う
                empLocDeterm = false;
                _destStation = wh;
                // アイル内（アイル独立時）または倉庫内（アイル結合時）全てを検索したいのでゾーンには0をセットする
                plt.setHeight(0);
                plt.setWidth(0);
            }
            else
            {
                if (StringUtil.isBlank(frst.getAisleStationNo()))
                {
                    // アイル結合ならば、その倉庫にダブルディープのアイルが有るか。
                    AisleSearchKey aisleKey = new AisleSearchKey();
                    AisleHandler aisleHandler = new AisleHandler(getConnection());
                    aisleKey.setDoubleDeepKind(Aisle.DOUBLE_DEEP_KIND_DOUBLE);
                    aisleKey.setWhStationNo(frst.getWhStationNo());
                    if (aisleHandler.count(aisleKey) > 0)
                    {
                        // ダブルディープの場合は空棚決定は行なわず、空棚存在チェックのみ行う
                        // 荷姿検知器は無いのでゾーンは更新しない。
                        empLocDeterm = false;
                        _destStation = wh;
                    }
                }
                else
                {
                    try
                    {
                        // アイル独立ならば、そのアイルがダブルディープである。
                        AisleSearchKey aisleKey = new AisleSearchKey();
                        AisleHandler aisleHandler = new AisleHandler(getConnection());
                        aisleKey.setStationNo(frst.getAisleStationNo());
                        Aisle al = (Aisle)aisleHandler.findPrimary(aisleKey);
                        if (al != null)
                        {
                            if (Aisle.DOUBLE_DEEP_KIND_DOUBLE.equals(al.getDoubleDeepKind()))
                            {
                                // ダブルディープの場合は空棚決定は行なわず、空棚存在チェックのみ行う
                                // 荷姿検知器は無いのでゾーンは更新しない。
                                empLocDeterm = false;
                                _destStation = wh;
                            }
                        }
                        else
                        {
                            throw new ReadWriteException();
                        }
                    }
                    catch (NoPrimaryException e)
                    {
                        throw new ReadWriteException(e);
                    }
                }
            }

            Shelf location = selectShelf(plt, empLocDeterm);
            if (location != null)
            {
                //<jp> 空棚決定時は検索した棚を入庫予約にする。</jp>
                //<en> Reserves the searched shelf for storage.</en>
                _srcStation = frst;
                _routeStatus = ACTIVE;
                if (empLocDeterm)
                {
                    // フリーアロケーション運用の場合、荷幅、棚使用フラグを更新
                    if (WareHouse.FREE_ALLOCATION_ON.equals(wh.getFreeAllocationType()))
                    {
                        FreeAllocationShelfOperator freeshelfop =
                                new FreeAllocationShelfOperator(getConnection(), location.getStationNo());
                        freeshelfop.alterWidth(plt);
                    }

                    ShelfOperator shop = new ShelfOperator(connection, location.getStationNo());
                    shop.alterPresence(Shelf.LOCATION_STATUS_FLAG_RESERVATION);
                    _destStation = location;
                }
                return true;
            }

            //<jp> 空棚がない場合、リジェクトステーションが定義されていればその値を搬送先にする。</jp>
            //<en> If there is no empty locations, and if reject station is defined, use the value </en>
            //<en> for destination.</en>
            Station currentst = StationFactory.makeStation(connection, plt.getCurrentStationNo());
            if (StringUtil.isBlank(currentst.getRejectStationNo()) == false)
            {
                _srcStation = frst;
                _destStation = StationFactory.makeStation(connection, currentst.getRejectStationNo());
                _routeStatus = ACTIVE;
                return true;
            }

            //<jp> 空棚もリジェクトステーション定義もない場合は搬送ルートNG</jp>
            //<jp> メッセージは空棚無しとする。</jp>
            //<en> The carry route is not acceptable if there is either no empty location or reject station.</en>
            //<en> Sets in message that there is no empty location.</en>
            _srcStation = null;
            _destStation = null;
            _routeStatus = LOCATION_EMPTY;
            return false;
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
    }

    /**<jp>
     * 搬送元が作業場の場合の入庫の搬送ルートチェック処理を行います。
     * 作業場に含まれる搬送元ステーションと搬送先を元に入庫棚決定処理を行います。<BR>
     * 全ての搬送元ステーションで入庫搬送先が決定できなかった場合はfalseを返します。
     * @param plt    空棚検索情報を含む<code>Pallet</code>インスタンス
     * @param wp     搬送元作業場となる<code>WorkPlace</code>インスタンス
     * @param tost   搬送先ステーション
     * @param determ 搬送元が作業場の場合にステーション決定を行う場合はtrueを指定します。<BR>
     *                ルートチェックのみ行う場合はfalseを指定します。
     * @return 搬送可能な場合はtrue、搬送不可能な場合はfalse
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     </jp>*/
    /**<en>
     * Processing the carry route check in case a workshop is the sending station.
     * Designates the storing location according to the sending station contained in workshop and the destined station .<BR>
     * It returns false if the storing location could not be designates at all sending stations.
     * @param plt    :<code>Pallet</code> instance which contains the information for empty location search.
     * @param wp     :<code>WorkPlace</code> instance which is to be the sending workshop.
     * @param tost   :destined station
     * @param determ :specifies true if designating station as sending station is a workshop.<BR>
     *                :sprcifies false if only the route check will be done.
     * @return  :true if possible to carry, or false if not possible to carry
     * @throws ReadWriteException     :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if specified station no. is invalid.
     * @throws LockTimeOutException  :Notifies if lock timeout occured.
     </en>*/
    protected boolean shelfDeterminFromWorkPlace(Pallet plt, WorkPlace wp, Station tost, boolean determ)
            throws ReadWriteException,
                InvalidDefineException,
                LockTimeOutException
    {
        Connection connection = getConnection();
        if (tost instanceof WareHouse)
        {
            for (int i = 0; i < wp.getWPStations().length; i++)
            {
                Station nextst = wp.getWPStation(connection);
                //<jp> 棚指定の入庫先決定処理</jp>
                //<jp> 空棚検索で必要なので選択されたステーションをセットする。</jp>
                //<en> Designating the store location by sprcific shelves.</en>
                //<en> As it is also required in emtpty search, it sets the selected station.</en>
                plt.setCurrentStationNo(nextst.getStationNo());
                if (shelfDeterminFromStation(plt, nextst, (WareHouse)tost))
                {
                    if (determ)
                    {
                        //<jp> 作業場の最終使用ステーションを更新</jp>
                        //<en> Updates the station of end-use in workshop.</en>
                        try
                        {
                            //アイル独立型全体作業場でのルート決定時に、作業場ステーションのLastUsedStationNumberを
                            //更新しても均等に作業を割り振れないので、子作業場の最終使用ステーションも更新する。
                            updateLastStaion(nextst);

                            StationOperator sop = new StationOperator(connection, wp.getStationNo());
                            sop.alterLastUsedStation(nextst.getStationNo());
                        }
                        catch (NotFoundException e)
                        {
                            throw new InvalidDefineException(e.getMessage());
                        }
                    }

                    return true;
                }
            }

            //<jp> 入庫可能なステーションでない場合</jp>
            if (_routeStatus == UNKNOWN)
            {
                // チェック結果（wRouteStatus）が搬送ルートチェックでセットされていない場合は、
                // NO_STATION_INTO_WORKPLACEをセットする。
                _routeStatus = NO_STATION_INTO_WORKPLACE;
            }
        }
        else if (tost instanceof Shelf)
        {
            for (int i = 0; i < wp.getWPStations().length; i++)
            {
                Station nextst = wp.getWPStation(connection);
                //<jp> 棚指定の入庫先決定処理</jp>
                //<en> Designation of store location by specific shelves.</en>
                if (shelfDeterminFromStation(plt, nextst, (Shelf)tost))
                {
                    if (determ)
                    {
                        //<jp> 作業場の最終使用ステーションを更新</jp>
                        //<en> Updates the station of end-use in workshop.</en>
                        try
                        {
                            //アイル独立型全体作業場でのルート決定時に、作業場ステーションのLastUsedStationNumberを
                            //更新しても均等に作業を割り振れないので、子作業場の最終使用ステーションも更新する。
                            updateLastStaion(nextst);

                            StationOperator sop = new StationOperator(connection, wp.getStationNo());
                            sop.alterLastUsedStation(nextst.getStationNo());
                        }
                        catch (NotFoundException e)
                        {
                            throw new InvalidDefineException(e.getMessage());
                        }
                    }
                    return true;
                }
            }
            if (_routeStatus == UNKNOWN)
            {
                // チェック結果（wRouteStatus）が搬送ルートチェックでセットされていない場合は、
                // NO_STATION_INTO_WORKPLACEをセットする。
                _routeStatus = NO_STATION_INTO_WORKPLACE;
            }
        }
        else
        {
            //<jp> FTTB ログメッセージ取得</jp>
            //<jp> 指定された入庫先が不正です。StationNo={0}</jp>
            //<en> Specified store location is invalid. StationNo={0}</en>
            throw new InvalidDefineException("The store location is invalid.");
        }

        return false;
    }

    /**<jp>
     * 搬送元がステーションの場合の直行搬送ルート決定処理を行います。<BR>
     * 搬送先に作業場を指定された場合、ステーション決定処理を行います。
     * 搬送元がSENDABLE（送信可能）ステーションではない場合、InvalidDefineExceptionをthrowします。
     * 搬送先がNOT_SENDABLE（送信不可）ステーションでかつ<code>WorkPlace</code>インスタンスではない場合、
     * InvalidDefineExceptionをthrowします。
     * @param frst   搬送元ステーション
     * @param tost   搬送先ステーションまたは作業場
     * @param determ 搬送先が作業場でステーション決定を行う場合はtrue、チェックのみ行う場合はfalseをセットする。
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the carry route designation for direct travel, when the sender is a station.<BR>
     * If a workshop is selected for the destination station, the station must be designated.
     * If the sending stationsis not a SENDABLE station, it throws InvalidDefineException.
     * If the destination is NOT_SENDABLE station and if it is not the instance of <code>WorkPlace</code>,
     * it throws InvalidDefineException.
     * @param frst   :sending station
     * @param tost   :destined station or workshop
     * @param determ :sets true if station is designated as destination is a workshop, or false if only
     *                 the check will be done.
     * @return  :true if destined station has been designated, or false if no station available for carry can be found.
     * @throws ReadWriteException :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if specified station no. is invalid.
     </en>*/
    protected boolean directTrancefarDeterminFromStation(Station frst, Station tost, boolean determ)
            throws ReadWriteException,
                InvalidDefineException
    {
        try
        {
            if (frst.isSendable() == false)
            {
                //<jp> FTTB ログメッセージ取得</jp>
                //<jp> 指定された搬送元ステーションは送信可能ではありません</jp>
                //<en> The sending station specified is not sendable.</en>
                throw new InvalidDefineException("The sending station specified is not sendable.");
            }

            if (tost.isSendable() == true)
            {
                //<jp> 搬送元ステーション，搬送先ステーションとのルートチェック</jp>
                //<en> Checks the route from sending station to the destined station.</en>
                return routeDetermin(frst, tost);
            }
            else
            {
                if (tost instanceof WorkPlace)
                {
                    //<jp> 搬送元ステーション、搬送先ステーションとのルートチェック</jp>
                    //<en> Checks the route from sending station to the destined station.</en>
                    WorkPlace wp = (WorkPlace)tost;
                    int stnums = wp.getWPStations().length;
                    //<jp> 搬送ルート定義の存在しないステーションの個数</jp>
                    //<en> Number of stations which has no definition for the carry route</en>
                    int notFoundCnt = 0;

                    for (int i = 0; i < stnums; i++)
                    {
                        Connection connection = getConnection();
                        Station st = wp.getWPStation(connection);
                        /*<jp>-------------------------------------
                         * アイル独立型作業場のステーション
                         *-------------------------------------</jp>*/
                        /*<en>--------------------------------------------------------
                         * Station with stand alone type workshop
                         *--------------------------------------------------------</en>*/
                        if (WorkPlace.WORKPLACE_TYPE_STAND_ALONE_STATIONS.equals(wp.getWorkplaceType()))
                        {
                            if (!st.getAisleStationNo().equals(frst.getAisleStationNo()))
                            {
                                notFoundCnt++;
                                continue;
                            }
                        }

                        //<jp> 入庫または入出庫兼用ステーションが対象</jp>
                        //<en> Checking the store stations or in/out stations</en>
                        if (st.getStationType().equals(Station.STATION_TYPE_OUT)
                                || st.getStationType().equals(Station.STATION_TYPE_INOUT))
                        {
                            //<jp> 払出し専用ステーションでない場合はＮＧ</jp>
                            //<en> Not acceptable of the station is NOT dedicated for trannfer.</en>
                            if (st.isRemove() == true)
                            {
                                //<jp> 倉庫指定の入庫先決定処理（空棚検索）</jp>
                                //<en> Processing the store location designation with specific warehouse (empty location search)</en>
                                if (routeDetermin(frst, st))
                                {
                                    if (determ)
                                    {
                                        //<jp> 作業場の最終使用ステーションを更新</jp>
                                        //<en> Updates the station of end-use in workshop.</en>
                                        try
                                        {
                                            StationOperator sop = new StationOperator(connection, tost.getStationNo());
                                            sop.alterLastUsedStation(_destStation.getStationNo());
                                        }
                                        catch (NotFoundException e)
                                        {
                                            throw new InvalidDefineException(e.getMessage());
                                        }

                                        return true;
                                    }

                                    return true;
                                }
                                else
                                {
                                    if (_routeStatus == NOTFOUND)
                                    {
                                        notFoundCnt++;
                                    }
                                }
                            }
                        }
                    }

                    if (notFoundCnt >= stnums)
                    {
                        //<jp> 今回指定された作業場内のステーションとパレット間のルートが全て存在しない場合</jp>
                        //<jp> ルート定義無しを状態にセットする。</jp>
                        //<en> If any route between this specified station of workshop and the pallet does not exist at all,</en>
                        //<en> it sets 'no route is defined' in status.</en>
                        _routeStatus = NOTFOUND;
                    }
                    return false;
                }
                else
                {
                    //<jp> 送信不可ステーションが作業場でなかった場合は例外を通知</jp>
                    //<jp> FTTB ログメッセージ取得</jp>
                    //<jp> 指定されたステーションは入庫作業可能な作業場またはステーションではありません。StationNo={0}</jp>
                    //<en> If the NOT sendable station was not a worlshop, it notifies of ezception.</en>
                    //<en> The specified station is not available for storage. StationNo={0}</en>
                    throw new InvalidDefineException("the sender is invalid.");
                }
            }
        }
        catch (NotFoundException e)
        {
            throw new ReadWriteException();
        }
    }

    /**<jp>
     * 搬送元が作業場の場合の直行搬送ルート決定処理を行います。<BR>
     * このメソッドは作業場内より有効なステーションを決定し<code>directTrancefarDeterminFromStation</code>を実行します。
     * 作業場内のいずれかのステーションでルートチェックに成功した場合はtrueを返します。
     * 作業場内の全てのステーションが使用できない場合はfalseを返します。
     * @param wp     搬送元作業場となる<code>WorkPlace</code>インスタンス
     * @param tost   搬送先ステーションまたは作業場
     * @param determ 搬送先が作業場でステーション決定を行う場合はtrue、チェックのみ行う場合はfalseをセットする。
     * @return 搬送先ステーションが決定した場合はtrue、搬送可能なステーションが見つからない場合はfalse
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 指定されたステーションNoが不正な場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the carry route designation for direct travel, when the sender is a station.<BR>
     * This method specifies the valid station in the workshop, then carries out <code>directTrancefarDeterminFromStation</code>.
     * It returns true if the route is successfully checked at any stations in the workshop.
     * Or it returns false if all station of that workshop is not usable.
     * @param wp     :<code>WorkPlace</code> instance which should be the sender workshop.
     * @param tost   :destined station or workshop
     * @param determ :Sets true if station is designated since a workshop is the destination, or false of only the check
     * will be done.
     * @return : true if destined station was designated or false if available station for the carry cannot be found.
     * @throws ReadWriteException     :Notifies if error occured in connection with database.
     * @throws InvalidDefineException :Notifies if specified station no. is invalid.
     </en>*/
    protected boolean directTrancefarDeterminFromWorkPlace(WorkPlace wp, Station tost, boolean determ)
            throws ReadWriteException,
                InvalidDefineException
    {
        boolean status = false;

        //<jp> 搬送元ステーション、搬送先ステーションとのルートチェック</jp>
        //<en> Checks the route from sending station to the destined station.</en>
        for (int i = 0; i < wp.getWPStations().length; i++)
        {
            Connection connection = getConnection();
            Station nextst = wp.getWPStation(connection);

            //<jp> 搬送先ステーション以外</jp>
            //<en> Stations other than receiving station</en>
            if (!nextst.getStationNo().equals(tost.getStationNo()))
            {
                //<jp> nextstが入庫可能ステーションかどうかをチェックします。</jp>
                //<en> Checks if nextst is available for storage.</en>
                if (isStorageStation(nextst, _offLineCheck))
                {
                    //<jp> 搬送先決定処理</jp>
                    //<en> Destination designation process</en>
                    status = directTrancefarDeterminFromStation(nextst, tost, determ);
                    if (status)
                    {
                        if (determ)
                        {
                            //<jp> 作業場の最終使用ステーションを更新</jp>
                            //<en> Updates the station of end-use in workshop.</en>
                            try
                            {
                                StationOperator sop = new StationOperator(connection, wp.getStationNo());
                                sop.alterLastUsedStation(nextst.getStationNo());
                            }
                            catch (NotFoundException e)
                            {
                                throw new InvalidDefineException(e.getMessage());
                            }
                        }

                        return true;
                    }
                }
            }
        }

        //<jp> 全てのステーションが搬送ＮＧ、状態メッセージをセットしてfalseを返す。</jp>
        //<en> Carry is not accepted at all stations; sets status message, then retuns false.</en>
        if (_routeStatus == UNKNOWN)
        {
            // チェック結果（wRouteStatus）が搬送ルートチェックでセットされていない場合は、
            // NO_STATION_INTO_WORKPLACEをセットする。
            _routeStatus = NO_STATION_INTO_WORKPLACE;
        }

        return false;
    }

    /**<jp>
     * 出庫ステーションのチェック処理を行います。
     * 出庫可能ステーション、ピッキング可能ステーション、払出し可能ステーションチェックを行います。<BR>
     * @param retrievalst   出庫ステーション
     * @param piconly       作業場内のピッキング出庫が可能なステーションのみをルートチェックの対象とする場合はtrueを<BR>
     *                       作業場内のユニット出庫ステーションを含めもよい場合はfalseを指定します。指定された搬送先が<code>WorkPlace<code><BR>
     *                       の場合に有効です。
     * @param removeonly    作業場内の払出し可能なステーションのみ対象ステーションとする場合はtrue。
     *                       払出し不可ステーションを加えてもよければfalseを指定します。指定された搬送先が<code>WorkPlace<code><BR>
     *                       の場合に有効です。
     * @param inventoryflag 作業場の中の在庫確認中のステーションをチェックするかどうかのフラグです。false：在庫確認中のステーションをルートとして返しません。<BR>
     *                       true:在庫確認中かどうかを判断せずにルート決定を行います（在庫確認中のステーションも返します。）。<BR>
     *                       なおステーションが作業場の場合のみこのフラグは有効です。
     * @param modeflag      作業場の中のステーションのモードをチェックするかどうかのフラグです。false:不適切なモードをルートとして返しません。<BR>
     *                       true:モードが適切か判断せずにルートの決定を行います。
     * @param interruptflag  作業場の中のステーションの中断中のステーションをチェックするかどうかのフラグです。false:中断中のステーションをルートとして返しません。<BR>
     *                       true:中断中かどうか判断せずにルートの決定を行います。
     * @return 出庫可能ステーションであればtrue、搬送不可能な場合はfalse
     </jp>*/
    protected boolean checkRetrievalStation(Station retrievalst, boolean piconly, boolean removeonly,
            boolean inventoryflag, boolean modeflag, boolean interruptflag)
    {
        //<jp> 出庫可能ステーションでない場合はcontinue</jp>
        //<en> Checks if nextst is available for retrieval.</en>
        //<en> Continue if it is not available.</en>
        if (!isRetrievalStation(retrievalst, inventoryflag, _offLineCheck, modeflag, interruptflag))
        {
            if (_routeStatus == UNKNOWN)
            {
                // 出庫可能ステーションチェック結果がfalseでisRetrievalStation内で
                // チェック結果（wRouteStatus）がセットされていない場合は、NO_STATION_INTO_WORKPLACE
                // をセットする。
                _routeStatus = NO_STATION_INTO_WORKPLACE;
            }
            return false;
        }

        if (piconly)
        {
            //<jp> ピッキング出庫可能ステーションのみ有効の指定が行われた場合、</jp>
            //<jp> ステーションがユニット出庫専用であればルートチェック対象としない。</jp>
            //<en> If it is specified that only station available for pick retrieval should be valid, </en>
            //<en> it excludes from stations to route-check those dedicated for unit retrievals.</en>
            if (retrievalst.isUnitOnly())
            {
                _routeStatus = NO_STATION_INTO_WORKPLACE;
                return false;
            }
        }

        if (removeonly)
        {
            //<jp> 払出しＯＫステーションのみ有効の指定が行われた場合、</jp>
            //<jp> ステーションが払出しＮＧであればルートチェック対象としない。</jp>
            //<en> If it is specified that only station available for transfer should be valid, </en>
            //<en> it excludes from stations to route-check those unavailable for transfer.</en>
            if (!retrievalst.isRemove())
            {
                _routeStatus = NO_STATION_INTO_WORKPLACE;
                return false;
            }
        }

        return true;
    }

    /**
     * 代表ステーション決定処理
     * @param tost          搬送先ステーション
     * @param frst          搬送元ステーション
     * @param piconly       作業場内のピッキング出庫が可能なステーションのみをルートチェックの対象とする場合はtrueを<BR>
     *                       作業場内のユニット出庫ステーションを含めもよい場合はfalseを指定します。指定された搬送先が<code>WorkPlace<code><BR>
     *                       の場合に有効です。
     * @param removeonly    作業場内の払出し可能なステーションのみ対象ステーションとする場合はtrue。
     *                       払出し不可ステーションを加えてもよければfalseを指定します。指定された搬送先が<code>WorkPlace<code><BR>
     *                       の場合に有効です。
     * @param inventoryflag 作業場の中の在庫確認中のステーションをチェックするかどうかのフラグです。false：在庫確認中のステーションをルートとして返しません。<BR>
     *                       true:在庫確認中かどうかを判断せずにルート決定を行います（在庫確認中のステーションも返します。）。<BR>
     *                       なおステーションが作業場の場合のみこのフラグは有効です。
     * @param modeflag      作業場の中のステーションのモードをチェックするかどうかのフラグです。false:不適切なモードをルートとして返しません。<BR>
     *                       true:モードが適切か判断せずにルートの決定を行います。
     * @param interruptflag  作業場の中のステーションの中断中のステーションをチェックするかどうかのフラグです。false:中断中のステーションをルートとして返しません。<BR>
     *                       true:中断中かどうか判断せずにルートの決定を行います。
     * @return 代表ステーションが決定した場合はtrue、代表ステーションが搬送可能でない場合はfalse
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException  変更対象がデータベースに見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     */
    protected boolean mainStationDetermin(Station tost, Station frst, boolean piconly, boolean removeonly,
            boolean inventoryflag, boolean modeflag, boolean interruptflag)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException
    {
        // 代表ステーションに紐づくステーションNo.を取得する
        StationSearchKey sskey = new StationSearchKey();
        StationHandler wph = new StationHandler(getConnection());
        sskey.setParentStationNo(tost.getStationNo());
        sskey.setStationNoOrder(true);
        Station[] mainst = (Station[])wph.find(sskey);

        //<jp> 搬送ルート定義の存在しないステーションの個数</jp>
        //<en> Number of stations which has no definition for the carry route</en>
        int notFoundCnt = 0;

        //<jp> アイル結合ステーションの集合の場合、優先ステーションの決定を行う。</jp>
        //<en> If handling a set of aisle connected type stations, designates the prioritized stations.</en>
        for (Station nextst : mainst)
        {
            // 搬送先ステーションがアイル独立の場合、パレットの現在ステーションが所属するアイルステーションNoと、
            // 代表ステーション内のステーションが所属するアイルステーションNoが一致すれば、搬送可能なステーション
            // としルートチェックを行う。
            if (!StringUtil.isBlank(nextst.getAisleStationNo())
                    && !(nextst.getAisleStationNo().equals(frst.getStationNo())))
            {
                //<jp> 作業場内のステーションが参照するアイルとパレットが存在する棚のアイルが一致しなければ</jp>
                //<jp> カウントする。</jp>
                //<en> Counts if the aisle which the station of workshop refers to differs from the aisle</en>
                //<en> of shelf where the pallet exists.</en>
                notFoundCnt++;
                continue;
            }

            // 出庫可能ステーションかチェックを行う
            if (!checkRetrievalStation(nextst, piconly, removeonly, inventoryflag, modeflag, interruptflag))
            {
                continue;
            }

            // 搬送ルートチェック
            if (routeDetermin(frst, nextst))
            {
                // 搬送ルートで決定したステーションではなく、元々の代表ステーションをセットし直す
                _destStation = tost;
                return true;
            }
        }

        if (notFoundCnt >= mainst.length)
        {
            //<jp> 今回指定された作業場内のステーションとパレット間のルートが全て存在しない場合</jp>
            //<jp> ルート定義無しを状態にセットする。</jp>
            //<en> If any route between this specified station of workshop and the pallet does not exist at all,</en>
            //<en> it sets 'no route is defined' in status.</en>
            _routeStatus = NOTFOUND;
        }

        //<jp> 全搬送ルートNG</jp>
        //<en> All carry route are not acceptable.</en>
        return false;
    }

    /**
     * データベースの最終使用ステーションNoを変更します。これはアイル独立型作業場専用です。
     * @param  st 最終使用ステーション
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws NotFoundException  変更対象がデータベースに見つからない場合に通知されます。
     * @throws InvalidDefineException 更新内容がセットされていない場合に通知されます。
     * @since eWareNavi 3.0 (2007-04-24)<br>
     * ASStationHandlerから移動されました。
     */
    protected void updateLastStaion(Station st)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        //    UPDATE {0} SET LASTUSEDSTATIONNUMBER = {1}  WHERE STATIONNUMBER =
        //      ( SELECT PARENTSTATIONNUMBER FROM {0} WHERE STATIONNUMBER = {1} );
        String stno = st.getStationNo();

        // create key for SUB QUERY (get parent station for this station)
        StationSearchKey pstKey = new StationSearchKey();
        pstKey.setParentStationNoCollect();
        pstKey.setStationNo(stno);

        // create alter key (update "last used station" for station that match to Subquery)
        StationAlterKey staKey = new StationAlterKey();
        staKey.setKey(Station.STATION_NO, pstKey);
        staKey.updateLastUsedStationNo(stno);

        StationHandler stH = new StationHandler(getConnection());
        stH.modify(staKey);
    }

    /**
     * connを設定します。
     * @param conn conn
     */
    protected void setConnection(Connection conn)
    {
        _conn = conn;
    }

    /**
     * connを返します。
     * @return connを返します。
     */
    protected Connection getConnection()
    {
        return _conn;
    }

    // Private methods -----------------------------------------------

    // Inner Class ---------------------------------------------------
}
//end of class

