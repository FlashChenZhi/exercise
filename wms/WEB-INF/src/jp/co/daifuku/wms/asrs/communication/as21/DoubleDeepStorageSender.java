// $Id: DoubleDeepStorageSender.java 7946 2010-05-24 09:08:29Z ota $
package jp.co.daifuku.wms.asrs.communication.as21;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.rmi.RemoteException;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.control.DoubleDeepChecker;
import jp.co.daifuku.wms.asrs.entity.DoubleDeepShelf;
import jp.co.daifuku.wms.asrs.location.FreeAllocationShelfOperator;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.CarryInfoController;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.dbhandler.ArrivalHandler;
import jp.co.daifuku.wms.base.dbhandler.ArrivalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeHandler;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.Arrival;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.LoadSize;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**<jp>
 * ダブルディープ運用を含むシステムでの搬送指示送信処理を行うクラスです。<BR>
 * CarryInformationからAGCに送るべき情報を得て、AGCに搬送指示を行います。
 * ダブルディープ倉庫の場合、手前棚への入庫時は奥棚の状態を確認する必要があるため、
 * 奥棚の状態をチェックする処理を持ちます。
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
 * This class operates the system transmission of carrying instructions including double deep operation.
 * It gest data to send to AGC out of CarryInformation, then releases the carrying instruction to AGC.
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
public class DoubleDeepStorageSender
        extends StorageSender
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

    /**<jp>
     * ステーションコントローラ
     </jp>*/
    /**<en>
     * station control class
     </en>*/
    private StationController _stationCtlr;

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
     * 新しい<code>DoubleDeepStorageSender</code>のインスタンスを作成しデータベースとのコネクションを取得します。
     * コネクションはAS/RSシステムパラメータから取得します。
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Create new instance of <code>DoubleDeepStorageSender</code>.
     * The connection will be obtained  from parameter of AS/RS system out of resource.
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public DoubleDeepStorageSender() throws ReadWriteException,
            RemoteException
    {
        super();
    }

    /**<jp>
     * 新しい<code>DoubleDeepStorageSender</code>のインスタンスを作成しデータベースとのコネクションを取得します。
     * コネクションはAS/RSシステムパラメータから取得します。
     * @throws ReadWriteException データベース接続で例外が発生した場合に通知します。
     * @throws RemoteException  リモートメソッド呼び出しの実行中に発生する通信関連の例外
     </jp>*/
    /**<en>
     * Create new instance of <code>DoubleDeepStorageSender</code>.
     * The connection will be obtained  from parameter of AS/RS system out of resource.
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws RemoteException  Exception related to communication generated while executing remote method call
     </en>*/
    public DoubleDeepStorageSender(String agcNumber) throws ReadWriteException,
            RemoteException
    {
        super(agcNumber);
    }

    // Public methods ------------------------------------------------
    /**<jp>
     * このクラスで使用する各ハンドラインスタンスの生成を行います。
     </jp>*/
    /**<en>
     * Generate the each handler instance whickeih will be used in this class.
     </en>*/
    protected void initHandler()
    {
        //<jp> ハンドラインスタンス生成</jp>
        //<en> Generation of handler instance</en>
        super.initHandler();
        //<jp> ステーション コントローラをクリア</jp>
        _stationCtlr = null;
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    /**<jp>
     * 指定されたステーションに存在する搬送データの読み込みを行います。搬送情報が見つからなかった場合、nullが戻ります。
     * 到着報告有りのステーションの場合、ステーションに記録された搬送key（mckey）を元にCarryInformationの読込みを行ないます。
     * ダミー到着の場合、ダミー到着が存在する場合のみ、そのステーションが搬送元となるCarryInformationを探し、搬送作成日が最も古いものを搬送指示対象とします。
     * ステーションの到着情報にBCDATAおよび荷高が記録されている場合、その内容をCarryInformationが参照するPalletのBCDATAおよび荷高にセットします。
     * Palletインスタンスの更新に失敗した場合、読み込んだCarryInformationは異常に変更し、nullを返します。
     * 到着報告無しのステーションの場合、指定されたステーションが搬送元となるCarryInformationを読み込みます。
     * @param  chStation   搬送の有無を確認する搬送元ステーション
     * @return             搬送情報(CarryInformation)
     * @throws Exception   データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Read the carry data existing in specified station. If no carry data is found, it returns null.
     * If the station has arrival report, read the CarryInformation according to the carry key (mckey) recorded in station.
     * If it is dummy arrival, search the CarryInformation designating this station to be the sending station, then select one
     * with the oldest date of carry creation.
     * If BC data and the load size are recorded in the station'S arrival data, set these data as the BC data and load size
     * of pallet that CarryInformation will refer to.
     * If failed to renew the Pallet instance, change the CarryInformation already read to 'error' and return null.
     * If there is no arrival report at the station, load the CarryInformation designating this specified station to be the
     * sender.
     * @param  chStation   Sending station with which to confirm the carry type
     * @return             CarryInformation
     * @throws Exception   Notifies if trouble occurs in reading/writing in database.
     </en>*/
    protected CarryInfo getCarryInfo(Station chStation)
            throws Exception
    {
        CarryInfoSearchKey cskey = new CarryInfoSearchKey();

        // Pallet検索・更新用
        PalletHandler palletHandelr = new PalletHandler(getConnection());
        PalletAlterKey palletAlterKey = new PalletAlterKey();

        //<jp> 応答待ちのデータがステーションに存在した場合は指示を送信しない。</jp>
        //<en> If there is a data waiting for reponse in station, the instruction will not be sent.</en>
        // 検索キーセット
        cskey.clear();
        cskey.setSourceStationNo(chStation.getStationNo());
        cskey.setCmdStatus(CarryInfo.CMD_STATUS_WAIT_RESPONSE, "=", "(", "", false);
        cskey.setCmdStatus(CarryInfo.CMD_STATUS_ERROR, "=", "", ")", true);
        if (getCarryInfoHandler().count(cskey) > 0)
        {
            return null;
        }

        // Stationの数が多い場合、現在のステーションの状態と一致せず
        // 1回のダミー到着に対し、搬送指示を2回投げることがあるため
        // Stationの状態を再取得するよう修正
        chStation = StationFactory.makeStation(getConnection(), chStation.getStationNo());

        //<jp> 到着報告があるステーションなのかをチェック</jp>
        //<en> Check whether/not the station has the arrival report.</en>
        if (Station.ARRIVAL_ON.equals(chStation.getArrival()))
        {

            ArrivalSearchKey skey = new ArrivalSearchKey();
            ArrivalHandler aHand = new ArrivalHandler(getConnection());
            skey.setStationNo(chStation.getStationNo());
            skey.setSendFlag(Arrival.ARRIVAL_NOT_SEND);
            skey.setRegistDateOrder(true);
            if (aHand.count(skey) == 0)
            {
                return null;
            }
            Arrival[] arrival = (Arrival[])aHand.find(skey);

            //<jp> 搬送Keyが在れば、それを基にCarryInrormationを獲得する。</jp>
            //<en> If the carry key is found, get hte CarryInformation based on that.</en>
            String carryKey = arrival[0].getCarryKey();
            //<jp> Stationにある搬送Keyを基にCarryInformationテーブルから条件に会ったものを１つ獲得する。</jp>
            //<jp> Stationに到着が来ていないものとして何もしない。</jp>
            //<en> Based on the carry key at the station, get one out of CarryInformation table that meets the conditions.</en>
            //<en> Take no action as no arrival is reported to the station.</en>
            if (StringUtil.isBlank(carryKey))
            {
                return null;
            }
            //<jp> Stationにダミー到着が来ている。</jp>
            //<en> Station received the dummy arrival.</en>
            else if (carryKey.equals(WmsParam.DUMMY_MCKEY))
            {
                StationController stControll = new StationController(getConnection(), getClass());
                // ダブルディープ、又は、ステーションの搬送指示有無によりSQLが異なる
                if (stControll.isReStoringEmptyLocationSearch(chStation.getStationNo())
                        || Station.RESTORING_INSTRUCTION_AWC_STORAGE_SEND.equals(chStation.getRestoringInstruction()))
                {
                    // 検索キーセット
                    cskey.clear();
                    cskey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "(", "", false);
                    cskey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
                    cskey.setCmdStatus(CarryInfo.CMD_STATUS_START, "=", "(", "", false);
                    cskey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "=", "", ")", true);
                    cskey.setSourceStationNo(chStation.getStationNo());
                    cskey.setPriorityOrder(true);
                    cskey.setRegistDateOrder(true);
                    cskey.setCarryKeyOrder(true);
                }
                else
                {
                    // 検索キーセット
                    cskey.clear();
                    cskey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "(", "", false);
                    cskey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
                    cskey.setCmdStatus(CarryInfo.CMD_STATUS_START);
                    cskey.setSourceStationNo(chStation.getStationNo());
                    cskey.setPriorityOrder(true);
                    cskey.setRegistDateOrder(true);
                    cskey.setCarryKeyOrder(true);
                }

                // DFKLOOK:ここから修正
                // バーコード存在フラグ
                boolean existBcrFlag = false;

                // バーコード情報セット
                if (!StringUtil.isBlank(arrival[0].getBcrData()))
                {
                    PalletSearchKey sKey = new PalletSearchKey();
                    sKey.setKey(Pallet.BCR_DATA, arrival[0].getBcrData().trim(), "=", "(", "", false);
                    sKey.setKey(Pallet.BCR_DATA, arrival[0].getBcrData(), "=", "", ")", true);
                    if (palletHandelr.count(sKey) > 0)
                    {
                        cskey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
                        cskey.setKey(Pallet.BCR_DATA, arrival[0].getBcrData().trim(), "=", "(", "", false);
                        cskey.setKey(Pallet.BCR_DATA, arrival[0].getBcrData(), "=", "", ")", true);

                        // バーコードが存在した為、フラグを成立させる
                        existBcrFlag = true;
                    }
                }
                // DFKLOOK:ここから修正

                CarryInfo[] carry = (CarryInfo[])getCarryInfoHandler().find(cskey);
                // 該当データなし
                if (ArrayUtil.isEmpty(carry))
                {
                    // DFKLOOK:ここから修正
                    // ダミー到着でバーコードが指定されたが
                    // 搬送データが存在しなかった場合
                    if (existBcrFlag)
                    {
                        // 6026609=パレットのバーコード情報、または容器No.が重複している可能性があります。StationNo={0} バーコード情報={1}
                        Object[] tobj = new Object[2];
                        tobj[0] = arrival[0].getStationNo();
                        tobj[1] = arrival[0].getBcrData();
                        RmiMsgLogClient.write(6026609, LogMessage.F_ERROR, this.getClass().getName(), tobj);
                    }
                    // DFKLOOK:ここまで修正
                    return null;
                }
                // 該当データが存在する場合1件目を使用します
                else
                {
                    try
                    {
                        //<jp> Stationに保持しているBC Data、荷高情報をPalletにセット</jp>
                        //<en> Set the BC data and the load sizewhich are  preserved in Station to the pallet.</en>
                        if (!StringUtil.isBlank(arrival[0].getBcrData()))
                        {
                            if (!(arrival[0].getBcrData().equals("")))
                            {
                                // 検索キーセット
                                palletAlterKey.clear();
                                palletAlterKey.setPalletId(carry[0].getPalletId());
                                palletAlterKey.updateBcrData(arrival[0].getBcrData());
                                palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                                // 更新
                                palletHandelr.modify(palletAlterKey);
                            }
                        }

                        // フリーアロケーション運用かつ制御情報を保持している場合、荷幅、荷高をセットする
                        if (getStationCtlr().isFreeAllocationStation(chStation.getStationNo())
                                && !StringUtil.isBlank(arrival[0].getControlinfo()))
                        {
                            LoadSizeHandler lhandl = new LoadSizeHandler(getConnection());
                            LoadSizeSearchKey lkey = new LoadSizeSearchKey();

                            // 制御情報より荷姿検索
                            ControlInfo conInfo = new ControlInfo();
                            conInfo = conInfo.convertControlInfo(arrival[0].getControlinfo());

                            lkey.setLength(conInfo.getLength());
                            lkey.setHeight(conInfo.getHeight());
                            LoadSize[] loadsize = (LoadSize[])lhandl.find(lkey);

                            // 検索キーセット
                            palletAlterKey.clear();
                            palletAlterKey.setPalletId(carry[0].getPalletId());
                            palletAlterKey.updateHeight(loadsize[0].getLoadSize());
                            palletAlterKey.updateWidth(conInfo.getWidth());
                            palletAlterKey.updateControlinfo(arrival[0].getControlinfo());
                            palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                            // 更新
                            palletHandelr.modify(palletAlterKey);
                        }
                        // フリーアロケーション運用以外かつ荷高が0より大きい場合、荷高をセットする
                        else if (arrival[0].getHeight() > 0)
                        {
                            // 検索キーセット
                            palletAlterKey.clear();
                            palletAlterKey.setPalletId(carry[0].getPalletId());
                            palletAlterKey.updateHeight(arrival[0].getHeight());
                            palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                            // 更新
                            palletHandelr.modify(palletAlterKey);
                        }
                        return carry[0];
                    }
                    catch (NotFoundException e)
                    {
                        //<jp> 更新すべきデータが見つからない場合</jp>
                        //<en> If the data to delete cannot be found</en>
                        Object[] tObj = new Object[1];
                        tObj[0] = carry[0].getPalletId();
                        //<jp> 6026067=指定されたパレットIDのパレット情報が存在しません。PalletID={0}</jp>
                        //<en> 6026067=Pallet data for the designated Pallet ID does not exist. Pallet ID={0}</en>
                        RmiMsgLogClient.write(new TraceHandler(6026067, e), getClass().getName(), tObj);
                        carryFailure(carry[0]);
                        return null;
                    }
                }
            }
            //<jp> StationにあるMcKeyを基にCarryInformationテーブルから条件に会ったものを１つ獲得する。</jp>
            //<en> Based on the McKey at Station, get one out of CarryInformation table that meets the conditions.</en>
            else
            {
                StationController stControll = new StationController(getConnection(), getClass());
                // ダブルディープ、又は、ステーションの搬送指示有無によりSQLが異なる
                if (stControll.isReStoringEmptyLocationSearch(chStation.getStationNo())
                        || Station.RESTORING_INSTRUCTION_AWC_STORAGE_SEND.equals(chStation.getRestoringInstruction()))
                {
                    //<jp> 搬送状態が開始または到着のものを搬送指示送信対象とする</jp>
                    //<en> There is a target data that CarryInformation status is CMD_STATUS_START or CMD_STATUS_ARRIVAL</en>
                    cskey.clear();
                    cskey.setCmdStatus(CarryInfo.CMD_STATUS_START, "=", "(", "", false);
                    cskey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "=", "", ")", true);
                }
                else
                {
                    //<jp> 搬送状態が開始のものを搬送指示送信対象とする</jp>
                    //<en> There is a target data that CarryInformation status is CMD_STATUS_START</en>
                    cskey.clear();
                    cskey.setCmdStatus(CarryInfo.CMD_STATUS_START);
                }

                cskey.setCarryKey(carryKey);
                //<jp> 該当のCarryInfoを取得</jp>
                //<en> Getting the applicable CarryInfo</en>
                CarryInfo[] carry = (CarryInfo[])getCarryInfoHandler().find(cskey);
                //<jp> 該当データなし</jp>
                //<en> No such data is found.</en>
                if (ArrayUtil.isEmpty(carry))
                {
                    //<jp> 警告メッセージを出力する。</jp>
                    //<jp> 6026066=指定されたmckeyの搬送データ[McKey={0}]が存在しないのでStation[StationNo.={1}]から削除しました。</jp>
                    //<en> Outputting the warning message</en>
                    //<en> 6026066=No transfer data [MCKey={0}] for the specified MCKey. Deleted from the station [ST No.={1}].</en>
                    Object[] tobj = new Object[2];
                    tobj[0] = carryKey;
                    tobj[1] = chStation.getStationNo();
                    RmiMsgLogClient.write(6026066, LogMessage.F_ERROR, this.getClass().getName(), tobj);
                    //<jp> 指定されたmckeyの搬送データがないなら、消してしまう。</jp>
                    //<en> If specified carry data of specified mc key cannot be found, delete.</en>
                    try
                    {
                        System.out.println("delete if no carry data of specified mckey is found.St No. = "
                                + chStation.getStationNo());

                        // DFKLOOK 3.4
//                        // Station更新用
//                        StationHandler stationHandelr = new StationHandler(getConnection());
//                        StationAlterKey stationAlterKey = new StationAlterKey();
//
//
//                        // 検索キーセット
//                        stationAlterKey.clear();
//                        stationAlterKey.setStationNo(chStation.getStationNo());
//                        stationAlterKey.updateCarryKey(null);
//                        stationAlterKey.updateBcrData(null);
//                        stationAlterKey.updateHeight(0);
//
//                        // 更新
//                        stationHandelr.modify(stationAlterKey);
                        ArrivalHandler arrivalHand = new ArrivalHandler(getConnection());
                        ArrivalSearchKey arrivalSkey = new ArrivalSearchKey();
                        // 検索キーセット
                        arrivalSkey.setCarryKey(arrival[0].getCarryKey());
                        arrivalSkey.setStationNo(arrival[0].getStationNo());
                        // 削除
                        arrivalHand.drop(arrivalSkey);
                        // DFKLOOK 3.4

                    }
                    catch (NotFoundException e)
                    {
                        //<jp> 削除すべきデータが見つからない場合</jp>
                        //<en> If the data to delete cannot be found</en>
                        Object[] tObj = new Object[1];
                        tObj[0] = Station.STORE_NAME;
                        //<jp> 6006006=削除対象データがありません。テーブル名:{0}</jp>
                        //<en> 6006006=There is no data to delete. Table Name: {0}</en>
                        RmiMsgLogClient.write(new TraceHandler(6006006, e), getClass().getName(), tObj);
                    }
                    return null;
                }

                if (carry[0] instanceof CarryInfo)
                {
                    //<jp> 到着情報として記録されている搬送Keyより取得したCarryInformationを返す。</jp>
                    //<en> Return the CarryInformation acquired by the carry key that is recorded as arrival data. </en>
                    try
                    {
                        //<jp> Stationに保持しているBC Data、荷高情報を今回送信対象となるPalletにセット</jp>
                        //<en> Set the BC data and load size data preserved by the station to the target Pallet of sending data.  </en>
                        if (!StringUtil.isBlank(arrival[0].getBcrData()))
                        {
                            if (!(arrival[0].getBcrData().equals("")))
                            {
                                // 検索キーセット
                                palletAlterKey.clear();
                                palletAlterKey.setPalletId(carry[0].getPalletId());
                                palletAlterKey.updateBcrData(arrival[0].getBcrData());
                                palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                                // 更新
                                palletHandelr.modify(palletAlterKey);
                            }
                        }
                        if (arrival[0].getHeight() > 0)
                        {
                            // 検索キーセット
                            palletAlterKey.clear();
                            palletAlterKey.setPalletId(carry[0].getPalletId());
                            palletAlterKey.updateHeight(arrival[0].getHeight());
                            palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                            // 更新
                            palletHandelr.modify(palletAlterKey);
                        }
                        return carry[0];
                    }
                    catch (NotFoundException e)
                    {
                        //<jp> 更新すべきデータが見つからない場合</jp>
                        //<en> If the data to delete cannot be found</en>
                        Object[] tObj = new Object[1];
                        tObj[0] = carry[0].getPalletId();
                        //<jp> 6026067=指定されたパレットIDのパレット情報が存在しません。PalletID={0}</jp>
                        //<en> 6026067=Pallet data for the designated Pallet ID does not exist. Pallet ID={0}</en>
                        RmiMsgLogClient.write(new TraceHandler(6026067, e), getClass().getName(), tObj);
                        carryFailure(carry[0]);
                        return null;
                    }
                }
                else
                {
                    return null;
                }
            }
        }
        else
        {
            StationController stControll = new StationController(getConnection(), getClass());
            // ダブルディープ、又は、ステーションの搬送指示有無によりSQLが異なる
            if (stControll.isReStoringEmptyLocationSearch(chStation.getStationNo())
                    || Station.RESTORING_INSTRUCTION_AWC_STORAGE_SEND.equals(chStation.getRestoringInstruction()))
            {
                // 検索キーセット
                cskey.clear();
                cskey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "(", "", false);
                cskey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
                cskey.setCmdStatus(CarryInfo.CMD_STATUS_START, "=", "(", "", false);
                cskey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "=", "", ")", true);
                cskey.setSourceStationNo(chStation.getStationNo());
                cskey.setPriorityOrder(true);
                cskey.setRegistDateOrder(true);
                cskey.setCarryKeyOrder(true);
            }
            else
            {
                // 検索キーセット
                cskey.clear();
                cskey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "(", "", false);
                cskey.setCarryFlag(CarryInfo.CARRY_FLAG_DIRECT_TRAVEL, "=", "", ")", true);
                cskey.setCmdStatus(CarryInfo.CMD_STATUS_START);
                cskey.setSourceStationNo(chStation.getStationNo());
                cskey.setPriorityOrder(true);
                cskey.setRegistDateOrder(true);
                cskey.setCarryKeyOrder(true);
            }

            CarryInfo[] carryArray = (CarryInfo[])getCarryInfoHandler().find(cskey);

            // 該当データあり
            if (!ArrayUtil.isEmpty(carryArray))
            {
                return carryArray[0];
            }

            return null;
        }
    }

    /**<jp>
     * 搬送先のロケーションNoを決定し、搬送指示の条件チェックを行います。
     * 搬送先が棚でかつ搬送先のアイルがダブルディープの場合のは、棚の位置をもとに搬送指示可能かどうかチェックします。
     * 搬送指示可能であればtrue, 搬送指示不可であればfalseを返します。
     * 搬送指示不可の場合、受け取ったCarryInformationの搬送先ステーションNoを倉庫ステーションNoに戻し、
     * 搬送先ステーションNoで指定されたStationインスタンス（実態はShelf）を空棚にします。
     * @param  cInfo       搬送対象CarryInformation
     * @param  sourceSt    搬送元ステーション
     * @return             搬送指示可能ならばtrue、搬送指示ができない場合はfalse。
     * @throws Exception   データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    /**<en>
     * Designate the location number at the receiving station, then check the condition of carrying instruction.
     * If the load is destined to racks and the reeiver's aisle is double-deep layout, check to see if the carrying
     * can be carried out based on the position of the rack.
     * If carrying is available it returns 'true' and if is not available it returns 'false'.
     * In case the carrying is not available, change the receiving station number of the received CarryInformation
     * back to the warehouse no., then change the Station instance (practically a shelf), specified by the receiving
     * station number, to the empty location.
     * @param  cInfo       CarryInformation
     * @param  sourceSt    sending station
     * @return             returns 'true' if carrying instruction can be proceeded; or 'false' if not.
     * @throws Exception   Notifies if trouble occured in the reading/writing of database.
     </en>*/
    protected boolean destDetermin(CarryInfo cInfo, Station sourceSt)
            throws Exception
    {
        try
        {
            // Pallet検索・更新用
            PalletHandler palletHandelr = new PalletHandler(getConnection());
            PalletSearchKey palletKey = new PalletSearchKey();

            Station destSt = StationFactory.makeStation(getConnection(), cInfo.getDestStationNo());
            System.out.println("No. of receiving station in DoubleDeepStorageSender destDetermin = "
                    + cInfo.getDestStationNo());
            if (destSt instanceof Shelf)
            {
                StationController stControll = new StationController(getConnection(), getClass());
                //<jp> 搬送先が棚の場合でも、搬送元ステーションが荷姿チェックありでかつ、搬送データが在庫確認以外の場合</jp>
                //<jp> 又は、ダブルディープの有る倉庫の場合</jp>
                //<jp> 再度棚決定が必要なため、搬送先を倉庫に変更する。</jp>
                //<en> Though in case the carry is destined to the shelf, if sending station conducts the load size checking</en>
                //<en> and if the carry data is other than inventory checking:</en>
                //<en> Once again the determination of the location is necessary. change the destination to the warehouse.</en>
                if (stControll.isReStoringEmptyLocationSearch(sourceSt.getStationNo())
                        && !CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(cInfo.getRetrievalDetail()))
                {
                    //<jp> 元々の搬送先を空棚にする前に、他のパレットがその棚に入庫されていないかどうか </jp>
                    //<jp> 確認する処理を追加 </jp>
                    //<en> Add a confirmation process wheather other pallets have been stored in that location or not before </en>
                    //<en> emptying the original receiving location. </en>

                    //<jp> 倉庫インスタンス作成</jp>
                    WareHouse wh = (WareHouse)StationFactory.makeStation(getConnection(), destSt.getWhStationNo());

                    // 副問い合わせ用キー
                    // 同一棚に再入庫しないステーションへ出庫したピッキング出庫または積増入庫で、
                    // 出庫した棚が空棚（出庫完了または到着または開始（搬送区分が入庫））のパレット、
                    // またはユニット出庫の出庫完了状態のパレットは除外して検索する
                    CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
                    CarryInfoSearchKey cskey = carryControl.getEmptyShelfPallet();

                    ShelfSearchKey sSkey = new ShelfSearchKey();
                    ShelfHandler shelfHandler = new ShelfHandler(getConnection());

                    // 棚間移動で引き当てた棚を更新してしまう可能性があるのでロックを行う
                    sSkey.setStationNo(destSt.getStationNo());
                    shelfHandler.lock(sSkey);

                    // 主問い合わせ用キー
                    PalletSearchKey pkey = new PalletSearchKey();
                    pkey.setCurrentStationNo(destSt.getStationNo(), "=", "((", "", true);
                    pkey.setKey(Pallet.PALLET_ID, cskey, "!=", "", ")", false);
                    pkey.setKey(CarryInfo.DEST_STATION_NO, destSt.getStationNo(), "=", "(", "", true);
                    pkey.setKey(CarryInfo.CARRY_FLAG, CarryInfo.CARRY_FLAG_RACK_TO_RACK);
                    pkey.setKey(CarryInfo.PALLET_ID, Pallet.PALLET_ID, "=", "", "))", true);
                    pkey.setPalletId(cInfo.getPalletId(), "!=");
                    if (palletHandelr.count(pkey) == 0)
                    {
                        Shelf shf = (Shelf)destSt;

                        // フリーアロケーション運用の場合、荷幅、棚使用フラグをフリーに更新
                        if (WareHouse.FREE_ALLOCATION_ON.equals(wh.getFreeAllocationType()))
                        {
                            FreeAllocationShelfOperator freeshelfop =
                                    new FreeAllocationShelfOperator(getConnection(), shf.getStationNo());
                            freeshelfop.alterFreeWidth();
                        }

                        //<jp> 元々入庫予定の搬送先の棚の在荷をOFF（空棚）にする。</jp>
                        //<en> Change the load presence of the destined location to 'OFF, empty' initially planned for this storage.</en>
                        ShelfAlterKey sAkey = new ShelfAlterKey();
                        ShelfHandler sHandler = new ShelfHandler(getConnection());
                        sAkey.clear();
                        sAkey.setStationNo(shf.getStationNo());
                        sAkey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
                        sHandler.modify(sAkey);
                    }

                    //<jp> 搬送先を倉庫に変更</jp>
                    //<en> Change the destination to the warehouse.</en>
                    destSt = wh;
                    System.out.println("DoubleDeepStorageSender The station No. that empty location to be searched once again. = "
                            + destSt);
                }
            }

            // 2010/05/11 Y.Osawa ADD ST
            // 搬送先が代表ステーションの場合（直行）はルートチェックを行わない（別のロジックで紐づくステーションのチェックを行う）
            if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(destSt.getWorkplaceType()))
            {
                System.out.println("Designation of destination and tranport route check in DoubleDeepStorageSender destDetermin  ST No. = "
                        + destSt.getStationNo());
                //<jp> 搬送指示可能</jp>
                //<en> Able to send carry instruction</en>
                return true;
            }
            // 2010/05/11 Y.Osawa ADD ED

            //<jp> 搬送先決定処理および搬送ルートチェック。</jp>
            //<jp> ルートチェックのため一時的に搬送元ステーションを置換え</jp>
            //<en> Determination of the destination and check the transport route</en>
            //<en> Temporarily replace the sending station for route checking.</en>

            // 検索キーセット
            palletKey.clear();
            palletKey.setPalletId(cInfo.getPalletId());
            // 検索
            Pallet pallet = (Pallet)palletHandelr.findPrimary(palletKey);

            // 置き換え
            pallet.setCurrentStationNo(sourceSt.getStationNo());

            if (getRouteController().storageDetermin(pallet, sourceSt, destSt))
            {
                // リジェクトSTの場合は空棚なしとする
                if (!(getRouteController().getDestStation() instanceof Shelf))
                {
                    if ((destSt instanceof Shelf) || (destSt instanceof WareHouse))
                    {
                        // 6022036=空棚がありません。搬送元ステーション={0}
                        Object[] tObj = new Object[1];
                        tObj[0] = sourceSt.getStationNo();
                        RmiMsgLogClient.write(6022036, this.getClass().getSimpleName(), tObj);
                        System.out.println("DoubleDeepStorageSender Check for determination of receiving station/carry route determined unacceptable by destDetermin.");
                        return false;
                    }
                }

                System.out.println("Designation of destination and tranport route check in DoubleDeepStorageSender destDetermin  ST No. = "
                        + destSt.getStationNo());
                System.out.println("replace hte destination with the one RouteController has selected in DoubleDeepStorageSender destDetermin ST No. = "
                        + (getRouteController().getDestStation().getStationNo()));

                //<jp> 搬送先がダブルディープ棚の場合、ダブルディープ用の搬送条件チェックを実施する。</jp>
                //<en> In case the destination of the load is a shelf, condition check of carrying with double deep operation</en>
                //<en> should be done.</en>
                if (getRouteController().getDestStation() instanceof DoubleDeepShelf)
                {
                    DoubleDeepChecker ddChecker = new DoubleDeepChecker(getConnection());
                    Shelf shf = (Shelf)getRouteController().getDestStation();
                    if (!ddChecker.storageCheck(cInfo, shf))
                    {
                        return false;
                    }
                }

                //<jp> 搬送先をRouteControllerクラスが決定した搬送先に置き換える。</jp>
                //<en> Replace the destination with the new oner that RouteController has selected.</en>
                if (!destSt.getStationNo().equals(getRouteController().getDestStation().getStationNo()))
                {
                    //<jp> 搬送先StationNumber、アイルStaitonNumberをCarryInformationにセット</jp>
                    //<en> Set the receiving StationNumber and aisle StaitonNumber in CarryInformation.</en>
                    System.out.println("In DoubleDeepStorageSender destDetermin, set receiving StationNumber and aisle StationNumber in CarryInformation");

                    // RouteControllerが決定した搬送先に置き換える。
                    updateDestSt(cInfo, getRouteController());
                }
            }
            else
            {
                if (getRouteController().getRouteStatus() == RouteController.LOCATION_EMPTY)
                {
                    // 6022036=空棚がありません。搬送元ステーション={0}
                    Object[] tObj = new Object[1];
                    tObj[0] = sourceSt.getStationNo();
                    RmiMsgLogClient.write(6022036, this.getClass().getSimpleName(), tObj);
                }
                else
                {
                    // 6022035=搬送可能な搬送先が決定できませんでした。搬送元ステーション={0} ルートチェック結果={1}
                    Object[] tObj = new Object[2];
                    tObj[0] = sourceSt.getStationNo();
                    tObj[1] = getRouteController().getRouteStatus();
                    RmiMsgLogClient.write(6022035, this.getClass().getSimpleName(), tObj);
                }
                System.out.println("DoubleDeepStorageSender Check for determination of receiving station/carry route determined unacceptable by destDetermin.");
                return false;
            }

            // フリーアロケーション倉庫の場合、搬送情報の制御情報を更新する
            if (WareHouse.FREE_ALLOCATION_ON.equals(getAreaCtlr().getFreeAllocationOfWarehouse(
                    getRouteController().getDestStation().getWhStationNo())))
            {
                // 棚情報、荷姿マスタから荷幅、荷長、荷高を取得する
                ShelfSearchKey sskey = new ShelfSearchKey();
                ShelfHandler sHandler = new ShelfHandler(getConnection());
                sskey.clear();
                sskey.setStationNo(getRouteController().getDestStation().getStationNo());
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
        //<jp> データ更新条件に誤りがあった場合に発生する。</jp>
        //<en> Occurs if there is any error in updated data.</en>
        catch (InvalidDefineException e)
        {
            carryFailure(cInfo);
            return false;
        }
        //<jp> StationFactory.makeStationで発生</jp>
        //<en> Occurs in StationFactory.makeStation</en>
        catch (NotFoundException e)
        {
            carryFailure(cInfo);
            return false;
        }

        //<jp> 搬送指示可能</jp>
        //<en> Able to send carry instruction</en>
        return true;
    }

    /**
     * StationControllerを返します。
     *
     * @return StationController
     * @throws ReadWriteException データベース接続エラー
     */
    protected StationController getStationCtlr()
            throws ReadWriteException
    {
        if (null == _stationCtlr)
        {
            _stationCtlr = new StationController(getConnection(), getClass());
        }
        return _stationCtlr;
    }
}
//end of class
