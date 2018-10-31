// $Id: DoubleDeepRetrievalSender.java 7946 2010-05-24 09:08:29Z ota $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.RemoteException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.control.DoubleDeepChecker;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeHandler;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.LoadSize;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**<jp>
 * ダブルディープ運用を含むシステムでの出庫指示送信処理を行うクラスです。<BR>
 * CarryInformationからAGCに送るべき出庫情報を得て、AGCに出庫指示を行います。
 * ダブルディープ倉庫の場合、奥棚の出庫時は手前棚の状態を確認する必要があるため、
 * 手前棚の状態をチェックする処理を持ちます。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/08</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7946 $, $Date: 2010-05-24 18:08:29 +0900 (月, 24 5 2010) $
 * @author  $Author: ota $
 </jp>*/
/**<en>
 * This class operates the system transmission of retrieval instructions including the double deep operaration.
 * It gets the retrieval data to send to AGC from CarryInformation, then releases AGC the retrieval instruction.
 * In case the warehouse is double deep, and when the instructed retrieval is working with the rear rack location,
 * status of the front rack needs to be checked. Therefore, this class also preserves the checking process of
 * front rack.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/08</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7946 $, $Date: 2010-05-24 18:08:29 +0900 (月, 24 5 2010) $
 * @author  $Author: ota $
 </en>*/
public class DoubleDeepRetrievalSender
        extends RetrievalSender
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * 出庫指示テキスト内に一度に指示出来る件数。
     </jp>*/
    /**<en>
     * number of instructions to set in each retrieval instruction text
     </en>*/
    private static final int INSTRUCTION_MAX = 1;

    /**<jp>
     * ダブルディープ運用時の搬送チェックを行うクラス
     </jp>*/
    /**<en>
     * The class which checks the carrying with double deep operation
     </en>*/
    private DoubleDeepChecker _doubleDeepChecker = null;

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
        return ("$Revision: 7946 $,$Date: 2010-05-24 18:08:29 +0900 (月, 24 5 2010) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<code>DoubleDeepRetrievalSender</code>のインスタンスを作成しデータベースとのコネクションを取得します。
     * コネクションはAS/RSシステムパラメータから取得します。
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Create new instance of <code>DoubleDeepRetrievalSender</code>.
     * The connection will be obtained  from parameter of AS/RS system out of resource.
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public DoubleDeepRetrievalSender() throws ReadWriteException,
            RemoteException
    {
        super();
        _doubleDeepChecker = new DoubleDeepChecker(getConnection());

        // 出庫指示テキスト内に一度に指示出来る件数をセットします。
        setInstructionMax(INSTRUCTION_MAX);
    }

    /**<jp>
     * 新しい<code>DoubleDeepRetrievalSender</code>のインスタンスを作成しデータベースとのコネクションを取得します。
     * コネクションはAS/RSシステムパラメータから取得します。
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Create new instance of <code>DoubleDeepRetrievalSender</code>.
     * The connection will be obtained  from parameter of AS/RS system out of resource.
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public DoubleDeepRetrievalSender(String agcNumber) throws ReadWriteException,
            RemoteException
    {
        super(agcNumber);
        _doubleDeepChecker = new DoubleDeepChecker(getConnection());

        // 出庫指示テキスト内に一度に指示出来る件数をセットします。
        setInstructionMax(INSTRUCTION_MAX);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    /**<jp>
     * 出庫指示の条件チェックを行います。現在指示済みの搬送データ件数と搬送可能件数との比較を行い、搬送可能件数以下ならばtrue
     * 搬送可能件数を超えていればfalseを返します。
     * @param  cInfo           搬送対象CarryInformation
     * @param  destSt          搬送先ステーション
     * @return                 搬送可能件数以下ならばtrue、搬送可能件数を超えていればfalseを返します。
     * @throws Exception       データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Check the conditions of retrieval instructions. Compare the number of carrying data that instuctions have already been
     * released and the MAX. number of carrying operations acceptable ; if the number of data is less than operationally
     * available work volume, returns 'true'.
     * Returns 'false' if the data exceeded the available operation number.
     * @param  cInfo           CarryInformation
     * @param  destSt          Receiving station
     * @return                 'true' if the data is less than MAX. operation work number to handle, or 'false' if exceeded.
     * @throws Exception       Notifies if trouble occured while reading database.
     </en>*/
    protected boolean destDetermin(CarryInfo cInfo, Station destSt)
            throws Exception
    {
        try
        {
            System.out.println("Double Deep RetrievalSender destDetermin()");
            _doubleDeepChecker = new DoubleDeepChecker(getConnection());

            // 出庫指示条件チェック
            //<jp> 今回出庫指示対象となるCarryInfornationの搬送区分によって</jp>
            //<jp> 出庫指示条件チェックを変える。</jp>
            //<en> The conditions of retrieval instrucitons to check should be changed depending on the transport section of its</en>
            //<en> carrying information applicable.</en>
            if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(cInfo.getCarryFlag()))
            {
                //<jp> 棚間移動の場合、グループコントローラーの状態チェックのみ行う。</jp>
                //<jp> 搬送先となる棚の状態をチェックし搬送可能か判断する。</jp>
                //<en> In case of location to location move, check should be done only for hte status of group controller.</en>
                //<en> By checking the location state of the receiving, determine whether/not the carrying can be carried out.</en>
                Station station = StationFactory.makeStation(getConnection(), cInfo.getAisleStationNo());

                //<jp> グループコントローラーがオンライン以外の場合は搬送不可</jp>
                //<en> Carrying is not available if the group controller has no on-line environment.</en>
                GroupController groupController =
                        GroupController.getInstance(getConnection(), station.getControllerNo());
                if (groupController.getStatus() != GroupController.STATUS_ONLINE)
                {
                    return false;
                }
            }
            else
            {
                //<jp> 出庫の場合、搬送先決定および搬送先ステーションのチェックを行う。</jp>
                //<jp> 搬送先決定および搬送ルートチェック</jp>
                //<en> In case of retrieval, determine the receiving station and check its status.</en>
                //<en> Designation of the receiving station and check the transport route</en>
                // Pallet検索用
                PalletHandler palletHandler = new PalletHandler(getConnection());
                PalletSearchKey palletKey = new PalletSearchKey();

                // 検索キーセット
                palletKey.clear();
                palletKey.setPalletId(cInfo.getPalletId());
                Pallet pallet = (Pallet)palletHandler.findPrimary(palletKey);

                RouteController routeController = getRouteController();
                Station upDestSt = destSt;
                // 2010/05/11 Y.Osawa ADD ST
                // 代表ステーションかつ在庫確認時は在庫確認中チェックを行わない
                boolean routeOK = false;
                if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(upDestSt.getWorkplaceType())
                        && CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(cInfo.getRetrievalDetail()))
                {
                    routeOK = routeController.retrievalDetermin((Pallet)pallet, upDestSt, true, false, false, true);
                }
                else
                {
                    routeOK = routeController.retrievalDetermin((Pallet)pallet, upDestSt);
                }
                // 2010/05/11 Y.Osawa ADD ED

                // 2010/05/11 Y.Osawa UPD ST
                //if (routeController.retrievalDetermin((Pallet)pallet, upDestSt))
                if (routeOK)
                // 2010/05/11 Y.Osawa UPD ST
                {
                    //<jp> 搬送先をRouteControllerクラスが決定した搬送先に置き換える。</jp>
                    //<en> Replace the receiving station with the one that RouteController class designated.</en>
                    if (!upDestSt.getStationNo().equals(routeController.getDestStation().getStationNo()))
                    {
                        upDestSt = routeController.getDestStation();
                        //<jp> 決定した搬送先StationNumberをCarryInformationにセット</jp>
                        //<en> Set the designated receiving Station Number to the CarryInformation</en>
                        CarryInfoAlterKey carryKey = new CarryInfoAlterKey();
                        // 検索キーセット
                        carryKey.clear();
                        carryKey.setCarryKey(cInfo.getCarryKey());
                        carryKey.updateDestStationNo(upDestSt.getStationNo());
                        carryKey.updateLastUpdatePname(getClass().getSimpleName());
                        // 更新
                        getCarryInfoHandler().modify(carryKey);
                    }
                }
                else
                {
                    //<jp> 搬送ルートが無い</jp>
                    //<en> There is no transport route</en>
                    return false;
                }

                //<jp> 搬送先ステーションの条件チェック</jp>
                //<en> Check the condition with the receiving station</en>
                if (!destRightStation(cInfo, upDestSt))
                {
                    //<jp> 搬送先条件NG</jp>
                    //<en> if conditions are not met at the receiving station</en>
                    return false;
                }

                //<jp> 搬送元である棚状態のチェックを行う。</jp>
                //<en> Check the location status of the sending station</en>
                if (routeController.getSrcStation() instanceof Shelf)
                {
                    Shelf shf = (Shelf)routeController.getSrcStation();
                    if (!_doubleDeepChecker.retrievalCheck(cInfo, shf))
                    {
                        return false;
                    }
                }
                // ダブルディープ対応
                //搬送元にアイルが返ってきた場合はパレットの現在ステーションをもとにチェックを行う
                else if (routeController.getSrcStation() instanceof Aisle)
                {
                    Shelf shf = (Shelf)StationFactory.makeStation(getConnection(), pallet.getCurrentStationNo());
                    if (!_doubleDeepChecker.retrievalCheck(cInfo, shf))
                    {
                        return false;
                    }
                }
                else
                {
                    //<jp> 6026035=取得したインスタンスが不正です。インスタンス={0}</jp>
                    //<en> 6026035=Acquired instance is invalid. Instance={0}</en>
                    Object[] tObj = new Object[1];
                    tObj[0] = routeController.getSrcStation().getClass().getName();
                    RmiMsgLogClient.write(6026035, LogMessage.F_ERROR, this.getClass().getName(), tObj);
                    carryFailure(cInfo);
                    return false;
                }

                // フリーアロケーション倉庫の場合、搬送情報の制御情報を更新する
                AreaController areaCtlr = new AreaController(getConnection(), getClass());
                if (WareHouse.FREE_ALLOCATION_ON.equals(areaCtlr.getFreeAllocationOfWarehouse(pallet.getWhStationNo())))
                {
                    // 棚情報、荷姿マスタから荷幅、荷長、荷高を取得する
                    ShelfSearchKey sskey = new ShelfSearchKey();
                    ShelfHandler sHandler = new ShelfHandler(getConnection());
                    sskey.clear();
                    sskey.setStationNo(pallet.getCurrentStationNo());
                    // AS/RS棚情報検索
                    Shelf shelf = (Shelf)sHandler.findPrimary(sskey);

                    LoadSizeSearchKey lskey = new LoadSizeSearchKey();
                    LoadSizeHandler lHandler = new LoadSizeHandler(getConnection());
                    lskey.clear();
                    lskey.setJoin(LoadSize.LOAD_SIZE, HardZone.HEIGHT);
                    lskey.setKey(HardZone.HARD_ZONE_ID, shelf.getHardZoneId());
                    // 荷姿マスタ情報検索
                    LoadSize loadsize = (LoadSize)lHandler.findPrimary(lskey);
                    if (loadsize == null)
                    {
                        // 6026601=指定されたハードゾーンの荷高が荷姿マスタの荷姿にありません。ハードゾーンID:{0}
                        Object[] tObj = new Object[1];
                        tObj[0] = shelf.getHardZoneId();
                        RmiMsgLogClient.write(6022036, this.getClass().getSimpleName(), tObj);
                        return false;
                    }

                    // 制御情報作成
                    ControlInfo conInfo = new ControlInfo();
                    conInfo.setWidth(shelf.getWidth());
                    conInfo.setLength(loadsize.getLength());
                    conInfo.setHeight(loadsize.getHeight());
                    String controlInfo = conInfo.convertControlInfo(conInfo);

                    // CarryInfo更新用
                    CarryInfoAlterKey carryAlterKey = new CarryInfoAlterKey();
                    // 検索キーセット
                    carryAlterKey.clear();
                    carryAlterKey.setCarryKey(cInfo.getCarryKey());
                    carryAlterKey.updateControlinfo(controlInfo);
                    carryAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                    // 更新
                    getCarryInfoHandler().modify(carryAlterKey);

                    // Entityも更新する
                    cInfo.setControlinfo(controlInfo);
                }
            }
        }
        // CarryInformation更新時に発生
        catch (InvalidStatusException e)
        {
            carryFailure(cInfo);
            return false;
        }
        // Station定義が不正の場合に発生
        catch (InvalidDefineException e)
        {
            carryFailure(cInfo);
            return false;
        }
        // Station未定義の場合に発生
        catch (NotFoundException e)
        {
            carryFailure(cInfo);
            return false;
        }

        return true;
    }
}
//end of class
