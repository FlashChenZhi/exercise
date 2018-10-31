// $Id: Id27Process.java 8057 2013-05-24 10:18:15Z kishimoto $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id27;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.location.FreeAllocationShelfOperator;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;

/**<jp>
 * 搬送先変更要求を処理するクラスです。
 * CarryInformationの搬送区分を元に処理を行います。
 * 入庫の場合、他の入庫棚を探し、搬送変更指示を送信します。見つからない場合はリジェクトステーションの定義が存在すれば、
 * そのステーションを代替搬送先とします。いずれも見つからない場合は搬送先変更不可で搬送先変更指示を送信します。
 * 出庫、棚間移動、直行の場合の搬送先変更処理は標準では行いません。
 * 搬送先変更不可で搬送先変更指示を送信します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2006/02/02</TD><TD>Y.Okamura</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 8057 $, $Date: 2013-05-24 19:18:15 +0900 (金, 24 5 2013) $
 * @author  $Author: kishimoto $
 </jp>*/
public class Id27Process
        extends IdProcess
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**<jp>
     * エリアコントローラ
     </jp>*/
    /**<en>
     * area control class
     </en>*/
    private AreaController _areaCtlr;

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
        return ("$Revision: 8057 $,$Date: 2013-05-24 19:18:15 +0900 (金, 24 5 2013) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * デフォルトコンストラクタ
     * AGCNoにGroupController.DEFAULT_AGC_NUMBERをセット
     </jp>*/
    /**<en>
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     </en>*/
    public Id27Process()
    {
        super();
    }

    /**<jp>
     * 引数で渡されたAGCNoをセットし、このクラスの初期化を行います。
     * @param agcNumber AGCNo
     </jp>*/
    /**<en>
     * Sets the AGCNo passed through parameter, then initialize this class.
     * @param agcNo AGCNo
     </en>*/
    public Id27Process(String agcNo)
    {
        super(agcNo);
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Accessor methods -----------------------------------------------
    /**
     * AreaControllerを返します。
     *
     * @return AreaController
     * @throws ReadWriteException データベース接続エラー
     */
    private AreaController getAreaCtlr()
            throws ReadWriteException
    {
        if (null == _areaCtlr)
        {
            _areaCtlr = new AreaController(getConnection(), getClass());
        }
        return _areaCtlr;
    }

    // Protected methods ---------------------------------------------
    /**<jp>
     * 搬送先変更要求を処理します。
     * 受信した電文のMC Keyから、<code>CarryInformation</code>を検索しそれぞれの処理を行います。
     * ただし、トランザクションのコミット・ロールバックは行っていませんので、
     * 呼び出し元で行う必要があります。
     * @param 受信電文
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Processing the desination change request.
     * According to the MC Key in received communication message, it searches <code>CarryInformation</code> and
     * processes respectively.
     * However the call source needs to commit or rollback the transaction, as they are not done here.
     * @param rdt :communication message received
     * @throws  Exception  :in case any error occured
     </en>*/
    @SuppressWarnings("cast")
    @Override
    protected void processReceivedInfo(byte[] rdt)
            throws Exception
    {
        try
        {
            CarryInfo ci;
            As21Id27 id27dt = new As21Id27(rdt);
            CarryInfoHandler cih = new CarryInfoHandler(getConnection());
            CarryInfoSearchKey cskey = new CarryInfoSearchKey();

            //<jp> 搬送先変更要求を受信したことをロギング</jp>
            //<en> Logging that desination change request was received.</en>
            Object[] tObj = new Object[1];
            tObj[0] = id27dt.getMcKey();
            // 6022026=搬送先変更要求を受信しました。mckey={0}
            //<jp> 6022026=搬送先変更要求を受信しました。mckey={0}</jp>
            //<en> 6022026=Transfer destination change request is received. mckey={0}</en>
            RmiMsgLogClient.write(6022026, LogMessage.F_NOTICE, getClass().getName(), tObj);

            //<jp> MC keyを検索条件として設定</jp>
            //<en> Sets MC key as a search condition.</en>
            String mckey = id27dt.getMcKey();
            cskey.setCarryKey(mckey);

            //<jp> 該当のCarryInfoを取得</jp>
            //<en> Obtains the corresponding CarryInfo.</en>
            CarryInfo[] earr = (CarryInfo[])cih.find(cskey);

            //<jp> 該当データなし</jp>
            //<en> There is no corresponding data.</en>
            if (earr.length == 0)
            {
                tObj = new Object[1];
                tObj[0] = mckey;
                //<jp> 6026038=指定されたmckeyの搬送データが存在しません。mckey={0}</jp>
                //<en> 6026038=Transfer data for the designated MCKey does not exist. mckey={0}</en>
                RmiMsgLogClient.write(6026038, LogMessage.F_ERROR, getClass().getName(), tObj);
                return;
            }

            if (earr[0] instanceof CarryInfo)
            {
                ci = (CarryInfo)earr[0];
                //<jp> 搬送区分を確認</jp>
                //<en> Checks the transport section.</en>

                //<jp> 入庫</jp>
                //<en> Storage</en>
                if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag()))
                {
                    storageChange(id27dt, ci);
                }
                // 出庫、直行、棚間移動
                else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag())
                        || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag())
                        || CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
                {
                    // 標準システムでは、出庫、直行および棚間移動の搬送先変更指示には対応しない
                    tObj = new Object[2];
                    tObj[0] = new Integer(ci.getCarryFlag());
                    tObj[1] = ci.getCarryKey();
                    //<jp> 6022027=搬送先変更要求を処理できません。搬送区分={0} mckey={1}</jp>
                    //<en> 6022027=Transfer destination change request cannot be processed. Transfer category={0} mckey={1}</en>
                    RmiMsgLogClient.write(6022027, LogMessage.F_NOTICE, getClass().getName(), tObj);
                }

            }
            else
            {
                tObj[0] = "CarryInformation";
                //<jp> 6006008={0}以外のオブジェクトが返されました。</jp>
                //<en> 6006008=Object other than {0} was returned.</en>
                RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, getClass().getName(), tObj);
                throw new InvalidProtocolException(WmsMessageFormatter.format(6006008, tObj));
            }
        }
        catch (Exception e)
        {
            // コントローラクリア処理
            clearCtlr();

            throw e;
        }
    }

    // Private methods -----------------------------------------------
    /**<jp>
     * 入庫時の変更処理を行います。今回入庫しようとしたアイル以外の棚を検索します。
     * 代替棚を決定した場合、その棚に対して代替棚指示をAGCに送ります。代替棚が存在しない場合、<BR>
     * 搬送元ステーションにリジェクトステーションが定義されている場合はリジェクトステーションに対して
     * 代替棚指示を送ります。代替棚＆代替ステーションがなければ代替搬送先無しとして代替棚指示を送ります。
     * @param  Id27dt 搬送先変更要求テキストの内容
     * @param  ci     搬送データ
     * @throws  Exception  何か異常が発生した場合。
     </jp>*/
    /**<en>
     * Modification process when storing. It searches the location other than the aisle the storage was attempted this time.
     * If the alternative location is determined, the instruction of alternative location must be sent to AGC.
     * If there is no alterenative locations, and if the reject station is defined in sending station,
     * it shoud send submit the instruction for alternative location to the reject station.
     * If there is no wither alternative locations or stations, it should send the instuction with no alterenative destinations.
     * @param  id27dt :Contents of destination change request
     * @param  ci     :Carry data
     * @throws  Exception  :in case any error occured
     </en>*/
    private void storageChange(As21Id27 id27dt, CarryInfo ci)
            throws Exception
    {
        //<jp> 変数の定義 -------------------------------------</jp>
        //<en> Definition of variable -------------------------------------</en>
        CarryInfoHandler cih = new CarryInfoHandler(getConnection());
        CarryInfoAlterKey cakey = new CarryInfoAlterKey();

        PalletSearchKey pSKey = new PalletSearchKey();
        PalletAlterKey pAKey = new PalletAlterKey();
        PalletHandler pHandle = new PalletHandler(getConnection());

        StockAlterKey stkAKey = new StockAlterKey();
        StockHandler stkHandle = new StockHandler(getConnection());

        ShelfAlterKey shelfAKey = new ShelfAlterKey();
        ShelfHandler shelfHandle = new ShelfHandler(getConnection());

        AisleAlterKey aisleAKey = new AisleAlterKey();
        AisleHandler aisleHandle = new AisleHandler(getConnection());

        //<jp> 搬送元ステーションインスタンス取得</jp>
        //<en> Obtaining the instance of sending station</en>
        Station fromSt = StationFactory.makeStation(getConnection(), ci.getSourceStationNo());
        //<jp> 搬送先ステーションインスタンス取得</jp>
        //<en> Obtaining the instance of receiving station</en>
        Station destSt = StationFactory.makeStation(getConnection(), ci.getDestStationNo());

        // V3.5.3 搬送先変更不可かどうか
        boolean isNoDest = false;
        try
        {
            if (destSt instanceof Shelf)
            {
                pSKey.clear();
                pSKey.setPalletId(ci.getPalletId());
                Pallet pl = (Pallet)pHandle.findPrimary(pSKey);

                boolean isUpdateAisle = false;
                Shelf destShelf = (Shelf)destSt;
                Station destAisle = StationFactory.makeStation(getConnection(), destShelf.getParentStationNo());
                if (Station.STATION_STATUS_NORMAL.equals(destAisle.getStatus()))
                {
                    //<jp> 今回入庫しようとしたアイルを空棚検索で検索対象から外すため</jp>
                    //<jp> 状態が使用可能であれば使用不可能に変更する。</jp>
                    //<en> Alter the status of this aisle, attempted for the storage this time, to 'unavailable'</en>
                    //<en> so that it should be excluded from empty location search.</en>
                    aisleAKey.clear();
                    aisleAKey.setStationNo(destAisle.getStationNo());
                    aisleAKey.updateStatus(Station.STATION_STATUS_DISCONNECTED);
                    aisleHandle.modify(aisleAKey);

                    isUpdateAisle = true;
                }

                //<jp> 再度空棚検索を行なうために、パレットの現在位置に搬送元ステーションをセット</jp>
                //<en> Set the sending station to current position of pallet in order to search the empty location again.</en>
                pl.setCurrentStationNo(fromSt.getStationNo());

                //<jp> 代替棚を検索</jp>
                //<en> Search the alternative location.</en>
                RouteController rc = new RouteController(getConnection());
                if (rc.storageDetermin(pl, destShelf.getWhStationNo()))
                {
                    //<jp> 検索した代替棚またはリジェクトステーションを搬送先ステーションに変更</jp>
                    //<en> Alter the searched alternative location or the reject station to destined station.</en>
                    cakey.clear();
                    cakey.setCarryKey(ci.getCarryKey());
                    cakey.updateDestStationNo(rc.getDestStation().getStationNo());
                    cakey.updateLastUpdatePname(getClassName());
                    cih.modify(cakey);

                    if (rc.getDestStation() instanceof Shelf)
                    {
                        //<jp> 代替棚を決定した場合、アイルステーションも更新</jp>
                        //<en> If alternative location is determined, aisle satation must be also updated.</en>
                        cakey.clear();
                        cakey.updateAisleStationNo(rc.getDestStation().getParentStationNo());
                        cakey.setCarryKey(ci.getCarryKey());
                        cakey.updateLastUpdatePname(getClassName());
                        cih.modify(cakey);

                        pAKey.clear();
                        pAKey.setPalletId(pl.getPalletId());
                        pAKey.updateCurrentStationNo(rc.getDestStation().getStationNo());
                        pAKey.updateLastUpdatePname(getClassName());
                        pHandle.modify(pAKey);

                        stkAKey.clear();
                        stkAKey.setPalletId(pl.getPalletId());
                        stkAKey.updateLocationNo(getAreaCtlr().toParamLocation(rc.getDestStation().getStationNo()));
                        stkHandle.modify(stkAKey);
                    }
                    else
                    {
                        //<jp> 代替搬送先がリジェクトステーションの場合、</jp>
                        //<jp> 搬送区分を直行に変更</jp>
                        //<en> If reject station is selected as alternate destination, transport section must be </en>
                        //<en> altered to 'direct travel'.</en>
                        cakey.clear();
                        cakey.setCarryKey(ci.getCarryKey());
                        cakey.updateCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL);
                        cakey.updateLastUpdatePname(getClassName());
                        cih.modify(cakey);

                        pAKey.clear();
                        pAKey.setPalletId(pl.getPalletId());
                        pAKey.updateCurrentStationNo(fromSt.getStationNo());
                        pAKey.updateLastUpdatePname(getClassName());
                        pHandle.modify(pAKey);

                        stkAKey.clear();
                        stkAKey.setPalletId(pl.getPalletId());
                        stkAKey.updateLocationNo(getAreaCtlr().toParamLocation(fromSt.getStationNo()));
                        stkHandle.modify(stkAKey);
                    }
                }
                else
                {
                    // V3.5.3 搬送先なし時に、先ステーションをクリアしない
                    isNoDest =true;

                    //<jp> 状態を異常に更新</jp>
                    //<en> Renew the status to 'error'.</en>
                    cakey.clear();
                    cakey.setCarryKey(ci.getCarryKey());
                    cakey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                    cakey.updateLastUpdatePname(getClassName());
                    cih.modify(cakey);
                }

                //<jp> 一時使用不可の状態にしていたのであれば、アイルを使用可能に戻す。</jp>
                //<en> If the aisle was kept 'unavailable' temporarily, then reset to 'available' again.</en>
                if (isUpdateAisle)
                {
                    aisleAKey.clear();
                    aisleAKey.setStationNo(destAisle.getStationNo());
                    aisleAKey.updateStatus(Station.STATION_STATUS_NORMAL);
                    aisleHandle.modify(aisleAKey);
                }

                FreeAllocationShelfOperator freeshelfop =
                        new FreeAllocationShelfOperator(getConnection(), destShelf.getStationNo());
                // フリーアロケーション運用の場合、荷幅、棚使用フラグをフリーに更新
                if (freeshelfop.isFreeAllocationStation())
                {
                    freeshelfop.alterFreeWidth();
                }

                // V3.5.3 搬送先なし時は、元の搬送先ロケーションを空棚に戻さない
                if (!isNoDest)
                {
                    //<jp> 元の搬送先ロケーションを空棚に戻す。</jp>
                    //<en> Set back the previous receiving location to empty location.</en>
                    shelfAKey.clear();
                    shelfAKey.setStationNo(destShelf.getStationNo());
                    shelfAKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
                    shelfHandle.modify(shelfAKey);
                }
            }
            else
            {
                // V3.5.3 例外時も先ステーションをクリアしない
                isNoDest =true;

                //<jp> 生成された搬送先が棚以外の場合は例外</jp>
                //<jp> 状態を異常に更新</jp>
                //<en> Exception if the receiving station generated was anything other than the location.</en>
                //<en> Renew the status to 'error'.</en>
                cakey.clear();
                cakey.setCarryKey(ci.getCarryKey());
                cakey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
                cakey.updateLastUpdatePname(getClassName());
                cih.modify(cakey);

                //<jp> 6026035=取得したインスタンスが不正です。インスタンス={0}</jp>
                //<en> 6026035=Acquired instance is invalid. Instance={0}</en>
                Object[] tObj = new Object[1];
                tObj[0] = destSt.getClass().getName();
                RmiMsgLogClient.write(6026035, LogMessage.F_WARN, getClass().getName(), tObj);
                throw new InvalidDefineException(WmsMessageFormatter.format(6026035, tObj));
            }
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            // V3.5.3 例外時も先ステーションをクリアしない
            isNoDest =true;

            //<jp> 状態を異常に更新</jp>
            //<en> Renew the status to 'error'.</en>
            cakey.clear();
            cakey.updateCmdStatus(CarryInfo.CMD_STATUS_ERROR);
            cakey.setCarryKey(ci.getCarryKey());
            cakey.updateLastUpdatePname(getClassName());
            cih.modify(cakey);
        }
        finally
        {
            CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
            ciKey.setCarryKey(ci.getCarryKey());
            CarryInfo resultCi = (CarryInfo)cih.findPrimary(ciKey);
            //<jp> 搬送先変更指示送信</jp>
            //<en> Submit the instruction of destination change.</en>
            // V3.5.3 搬送先変更フラグを引数に渡す
            SystemTextTransmission.id08send(resultCi, id27dt.getAgcData(), isNoDest, getConnection());

            Object[] tObj = new Object[2];
            tObj[0] = fromSt.getStationNo();
            if (isNoDest)
            {
                tObj[1] = " ";
            }
            else
            {
                tObj[1] = resultCi.getDestStationNo();
            }
            //<jp> 6022028=搬送先変更指示を送信しました。ステーションNo. {0} ---> {1}</jp>
            //<en> 6022028=Transfer destination change instruction is sent. ST No. {0} ---> {1}</en>
            RmiMsgLogClient.write(6022028, LogMessage.F_NOTICE, getClass().getName(), tObj);
        }
    }

    /**<jp>
     * コントローラをクリアします。
     </jp>*/
    private void clearCtlr()
    {
        _areaCtlr = null;
    }

}
//end of class

