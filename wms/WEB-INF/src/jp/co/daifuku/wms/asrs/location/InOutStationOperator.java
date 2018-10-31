// $Id: InOutStationOperator.java 7481 2010-03-09 02:26:20Z okayama $
package jp.co.daifuku.wms.asrs.location;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.handler.db.SysDate;

/**<jp>
 * 入出庫兼用ステーションの動作を定義したステーションです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/10/25</TD><TD>K.Mori</TD><TD>eWareNavi対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7481 $, $Date: 2010-03-09 11:26:20 +0900 (火, 09 3 2010) $
 * @author  $Author: okayama $
 </jp>*/
/**<en>
 * Defined in this class of station is the behaviour of station which handles both storage and retrieval.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/10/25</TD><TD>K.Mori</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7481 $, $Date: 2010-03-09 11:26:20 +0900 (火, 09 3 2010) $
 * @author  $Author: okayama $
 </en>*/
public class InOutStationOperator
        extends StationOperator
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------

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
        return ("$Revision: 7481 $,$Date: 2010-03-09 11:26:20 +0900 (火, 09 3 2010) $");
    }

    // Constructors --------------------------------------------------
    /**<jp>
     * 新しい<code>Station</code>のインスタンスを作成します。既に定義されているステーションを
     * 持つインスタンスが必要な場合は、<code>StationFactory</code>クラスを利用してください。
     * @param conn データベースコネクション
     * @param snum 保持する自ステーション番号
     * @throws ReadWriteException     データベースに対する処理で発生した場合に通知します。
     * @see StationFactory
     </jp>*/
    /**<en>
     * Creates a nw instance of <code>Station</code>. If the instance which already has the defined
     * station is needed, please use <code>StationFactory</code> class.
     * @param  conn     :Connection with database
     * @param  snum     :own station no. preserved
     * @throws ReadWriteException     : Notifies if any trouble occured in data access. 
     * @see StationFactory
     </en>*/
    public InOutStationOperator(Connection conn, String snum)
            throws ReadWriteException
    {
        super(conn, snum);
    }

    /**
     * 新しい<code>InOutStationOperator</code>のインスタンスを作成します。
     * 引数で渡された、Stationインスタンスを保持します。
     * @param conn データベースコネクション
     * @param st   ステーションインスタンス
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     */
    public InOutStationOperator(Connection conn, Station st)
            throws ReadWriteException
    {
        super(conn, st);
    }

    /**<jp>
     * 入出庫兼用ステーションにおける到着処理です。
     * 到着した搬送データの更新処理を行います。
     * 受取った搬送データの搬送区分に基づいてデ－タ更新を行います。
     * @param  ci 更新対象CarryInformation
     * @param  plt Palletインスタンス
     * @throws InvalidDefineException ステーションが到着報告無しの場合に入庫、直行（起点）またはダミー到着データを受取った場合に通知されます。
     * @throws ReadWriteException     データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * This is the arrival process at the station which handles both storage and retrieval.
     * Update process of the carry data arrived.
     * Data will be updated according to the transport section in the carry data received.
     * @param ci  :CarryInformation to update
     * @param plt :Pallet instance
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if the station does not operate arrival report and if they
     * received storage, direct travel of dummy arrival data.
     * @throws ReadWriteException     :Notifies if any exception occur in processing with database.
     * @throws NotFoundException      :Notifies if there is no such data.
     * @throws ScheduleException
     </en>*/
    public void arrival(CarryInfo ci, Pallet plt, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                ScheduleException
    {
        String pName = getClass().getSimpleName();
        if (ci.getCarryKey().equals(WmsParam.DUMMY_MCKEY))
        {
            //<jp> ステーションの到着チェック有無を確認する。</jp>
            //<en> Checks to see whether/not there was the arrival check at station.</en>
            if (getStation().isArrivalCheck())
            {
                //<jp> 到着報告有りのステーションであれば、到着データを記録</jp>
                //<en> If the station operates the arrival reporting, it records the arrival data.</en>
                registArrival(ci.getCarryKey(), plt);

                //<jp> 搬送指示送信要求を行なう。</jp>
                //<en> Requests to transmit the carry instruction.</en>
                carryRequest();
            }
            else
            {
                //<jp> 到着報告無しのステーションでダミーパレットの到着は無効</jp>
                //<en> If the station does not operate arrival reports, arrival of dummy pallets are invalid.</en>
                //<jp> 6024019=到着報告無しのステーションです。ダミー到着は無効です。StationNo={0} mckey={1}</jp>
                //<en> 6024019=No arrival report for the station. Dummy arrival is invalid. ST No={0} mckey={1}</en>
                Object[] tObj = new Object[2];
                tObj[0] = getStationNo();
                tObj[1] = ci.getCarryKey();
                RmiMsgLogClient.write(6024019, LogMessage.F_WARN, "InOutStationOperator", tObj);
                throw new InvalidDefineException(WmsMessageFormatter.format(6024019, tObj));
            }
        }
        else
        {
            //<jp> CarryInformationの搬送区分を元に処理を分岐</jp>
            //<en> Branches the process according to the transport sections in CarryInformation.</en>

            //<jp> 入庫の場合、ステーションに到着データを記録</jp>
            //<en> If storing , it records the arrival data in station.</en>
            if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag()))
            {
                //<jp> ステーションの到着チェック有無を確認する。</jp>
                //<en> Checks to see whether/not there is arrival checks at the station.</en>
                if (getStation().isArrivalCheck())
                {
                    //<jp> 到着報告有りのステーションであれば、到着データを記録</jp>
                    //<en> If the station operates arrival reporting, thne records hte arrival data.</en>
                    registArrival(ci.getCarryKey(), plt);

                    //<jp> 搬送指示送信要求を行なう。</jp>
                    //<en> And requests to transmit the carry instruction.</en>
                    carryRequest();
                }
                else
                {
                    //<jp> 到着報告無しのステーションで入庫パレットの到着は無効</jp>
                    //<en> If the station does not operate the arival reporting, te arrival of dummy pallet is invalid.</en>
                    //<jp> 6024020=到着報告無しのステーションです。入庫到着は無効です。StationNo={0} mckey={1}</jp>
                    //<en> 6024020=No arrival report for the station. Storage arrival is invalid. ST No={0} mckey={1}</en>
                    Object[] tObj = new Object[2];
                    tObj[0] = getStationNo();
                    tObj[1] = ci.getCarryKey();
                    RmiMsgLogClient.write(6024020, LogMessage.F_NOTICE, "InOutStationOperator", tObj);
                    throw new InvalidDefineException(WmsMessageFormatter.format(6024020, tObj));
                }
            }
            //<jp> 直行の場合、搬送先ステーションNoと現在ステーション（到着ステーションNo）が一致すれば</jp>
            //<jp> 搬送先に到着したと判断し、搬送データを削除する。</jp>
            //<jp> 一致しない場合は次の搬送があるものとし、到着データを記録する。</jp>
            //<en> In case of direct travel, if receiving station no. is the same as current station(arrival station no.),</en>
            //<en> it determines the load has arrived atdestination, therefore it deletesthe carry data.</en>
            //<en> If there are different no., it determines there still follows another carryinfg process, therefore</en>
            //<en> it records the arrival data.</en>
            else if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
            {
                if (ci.getDestStationNo().equals(getStationNo()))
                {
                    //<jp> 作業表示無しか、作業表示のみの運用の場合、到着処理を実施する。</jp>
                    //<en> If the sattion does not operate on-line indication, or it only operates on-line indication,</en>
                    //<en> arrival process should be carried out.</en>
                    if ((Station.OPERATION_DISPLAY_NONE.equals(getOperationDisplay()))
                            || (Station.OPERATION_DISPLAY_DISP_ONLY.equals(getOperationDisplay())))
                    {
                        //<jp> 搬送データを削除（ユニット出庫払い出しと同じ扱い）</jp>
                        //<en> Deletes the carry data (handled just as unit retrieval transfer)</en>
                        CarryCompleteOperator carryOperate = new CarryCompleteOperator(getConnection(), getClass());
                        // 再入庫データは作成しない
                        carryOperate.unitRetrieval(ci, false);
                    }
                }
                else
                {
                    //<jp> ステーションの到着チェック有無を確認する。</jp>
                    //<en> Check to see whether/not there is arrival checikng at station.</en>
                    if (getStation().isArrivalCheck())
                    {
                        //<jp> 到着報告有りのステーションであれば、到着データを記録</jp>
                        //<en> If the station operates the arrival reporting, it should record the arrival data.</en>
                        registArrival(ci.getCarryKey(), plt);

                        //<jp> 搬送指示送信要求を行なう。</jp>
                        //<en> Requests to submit the carry instruction.</en>
                        carryRequest();

                        //<jp> 搬送状態を到着に変更</jp>
                        //<jp> 到着日時を現在の時間に変更</jp>
                        //<en> Modify the carry status to 'arrival'.</en>
                        //<en> Modify the arrival date to the present day and time.</en>
                        CarryInfoHandler chandl = new CarryInfoHandler(getConnection());
                        CarryInfoAlterKey altkey = new CarryInfoAlterKey();
                        altkey.setCarryKey(ci.getCarryKey());
                        altkey.updateCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL);
                        altkey.updateArrivalDate(new SysDate());

                        altkey.updateLastUpdatePname(pName);
                        chandl.modify(altkey);
                    }
                    else
                    {
                        //<jp> 到着報告無しのステーションで搬送開始地点の直行パレットの到着は無効</jp>
                        //<en> If the station does not operate the arrival reporting, the arrival of direct traveling</en>
                        //<en> pallet of start point of carry is invalid.</en>
                        //<jp> 6024021=到着報告無しのステーションです。搬送元の直行到着は無効です。StationNo={0} mckey={1}</jp>
                        //<en> 6024021=Arrival of the station without arrival report is invalid. ST No={0} mckey={1}</en>
                        Object[] tObj = new Object[2];
                        tObj[0] = getStationNo();
                        tObj[1] = ci.getCarryKey();
                        RmiMsgLogClient.write(6024021, LogMessage.F_NOTICE, "InOutStationOperator", tObj);
                        throw new InvalidDefineException(WmsMessageFormatter.format(6024021, tObj));
                    }
                }
            }
            //<jp> 出庫の場合、出庫到着処理を呼び出す。</jp>
            //<en> If retrieving, call the retrieval arrival process.</en>
            else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag()))
            {
                updateArrival(ci, caller);
            }
            //<jp> それ以外の区分の場合は例外を返す。</jp>
            //<en> Or it returns exception for all other transport sections.</en>
            else
            {
                //<jp> 6024018=取得したインスタンス{0}の属性{1}の値が不正です。{1}={2}</jp>
                //<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
                Object[] tObj = new Object[3];
                tObj[0] = "CarryInfomation";
                tObj[1] = "CarryKind";
                tObj[2] = new Integer(ci.getCarryFlag());
                RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "InOutStationOperator", tObj);
                throw new InvalidDefineException(WmsMessageFormatter.format(6024018, tObj));
            }
        }
    }

    /**<jp>
     * 出庫データの出庫口到着処理です。
     * 入出庫兼用ステーションにおける搬送データの更新処理を行います。
     * 受取った搬送データの出庫指示詳細に基づいてデ－タ更新を行います。
     * @param  ci 更新対象CarryInformation
     * @param  caller 呼び出し元クラス
     * @throws InvalidDefineException 定義に不整合がある場合に通知されます。
     * @throws ReadWriteException     データベースに対する処理で発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Processing the retrieval data for hte arrival of retrieval station.
     * It updates the carry data at the station which handles both storage and retrieval.
     * Data will be updated according to the retrieval instruction detail in the carry data received.
     * @param ci :CarryInformation to update
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if there are any inconsistency in definition.
     * @throws ReadWriteException     :Notifies if exception occured in processing with database.
     * @throws NotFoundException      :Notifies if there is no such data.
     * @throws ScheduleException 
     </en>*/
    public void updateArrival(CarryInfo ci, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException,
                ScheduleException
    {
        try
        {
            CarryCompleteOperator carryOperate = new CarryCompleteOperator(getConnection(), caller);

            //<jp> 出庫指示詳細を元に処理の分岐を行う。</jp>
            //<en> Branches the process according to the retrieval instruction detail.</en>

            //<jp> ユニット出庫</jp>
            //<en> Unit retrieval</en>
            if (CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(ci.getRetrievalDetail()))
            {
                if (getStation().isReStoringOperation())
                {
                    //<jp> ユニット出庫在庫更新、搬送データ削除（入出庫実績データ作成あり）</jp>
                    //<en> Update of unit retrieval stocks, deleting carry data (with data creation for actual in/out)</en>
                    carryOperate.unitRetrieval(ci, true);
                }
                else
                {
                    //<jp> ユニット出庫在庫更新、搬送データ削除（入出庫実績データ作成なし）</jp>
                    //<en> Update of unit retrieval stocks, deleting carry data (with no data creation for actual in/out)</en>
                    carryOperate.unitRetrieval(ci, false);
                }
            }
            //<jp> ピッキング出庫、積増入庫、在庫確認</jp>
            //<en> Pick retrieval, replenishing storage, inventory checking</en>
            else if (CarryInfo.RETRIEVAL_DETAIL_PICKING.equals(ci.getRetrievalDetail())
                    || CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(ci.getRetrievalDetail())
                    || CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail()))
            {
                //<jp> 在庫数の更新を行う。</jp>
                //<en> Update the stock quantity.</en>
                carryOperate.updateStock(ci);

                CarryInfoHandler chandl = new CarryInfoHandler(getConnection());
                PalletHandler phandl = new PalletHandler(getConnection());

                //<jp> 搬送状態を到着に変更</jp>
                //<en> Modify the carry status to 'arrival'.</en>
                CarryInfoAlterKey cinfoAltKey = new CarryInfoAlterKey();
                cinfoAltKey.setCarryKey(ci.getCarryKey());
                cinfoAltKey.updateCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL);

                //<jp> CarryInformation更新</jp>
                //<en> Renew CarryInformation.</en>
                //<jp> 搬送元ステーションNoをこのステーションにする。</jp>
                //<en> Select this station for sending station no.</en>
                cinfoAltKey.updateSourceStationNo(getStationNo());
                //<jp> 搬送区分を入庫に変更</jp>
                //<en> Modify the transport section to 'storage'.</en>
                cinfoAltKey.updateCarryFlag(CarryInfo.CARRY_FLAG_STORAGE);
                //<jp> 到着日時を現在の時間に変更</jp>
                //<en> Modify the arrival date to present day and time.</en>
                cinfoAltKey.updateArrivalDate(new SysDate());

                //<jp> 出庫ロケーションNoに値が設定されていない場合、</jp>
                //<jp> パレットの現在位置を搬送先にする。</jp>
                //<jp> イレギュラーな操作なので警告ログを出力</jp>
                //<en>in case of retrieval station number is blank</en>
                //<en>modify carryinformation's deststation to Pallet's currentstation number</en>
                if (StringUtil.isBlank(ci.getRetrievalStationNo()))
                {
                    //<jp> 6020033=デバッグ用出力：{0}</jp>
                    //<en> 6020033=Output for debug: {0}</en>
                    Object[] tObj = new Object[1];
                    tObj[0] = "RetrievalStationNo Blank CmdStatus() = " + ci.getCmdStatus();
                    RmiMsgLogClient.write(6020033, LogMessage.F_WARN, "InOutStationOperator", tObj);

                    PalletSearchKey skey = new PalletSearchKey();
                    skey.setPalletId(ci.getPalletId());
                    Pallet[] pallet = (Pallet[])phandl.find(skey);

                    cinfoAltKey.setDestStationNo(pallet[0].getCurrentStationNo());
                }
                else
                {
                    //<jp> 搬送先ステーションNoを出庫ステーションNo（棚No）にする。</jp>
                    //<en> Select retrieval station no.(location no.) for receiving station.</en>
                    cinfoAltKey.updateDestStationNo(ci.getRetrievalStationNo());

                    //<jp> 在庫確認</jp>
                    //<en> inventory checking</en>
                    if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail()))
                    {
                        StockHandler stkdl = new StockHandler(getConnection());
                        StockAlterKey stkAltKey = new StockAlterKey();
                        AreaController getAreaCtlr = new AreaController(getConnection(), getClass());

                        stkAltKey.setPalletId(ci.getPalletId());
                        stkAltKey.updateLocationNo(getAreaCtlr.toParamLocation(ci.getRetrievalStationNo()));
                        stkdl.modify(stkAltKey);

                        PalletHandler plth = new PalletHandler(getConnection());
                        PalletAlterKey pltAKey = new PalletAlterKey();
                        pltAKey.setPalletId(ci.getPalletId());
                        pltAKey.updateCurrentStationNo(ci.getRetrievalStationNo());
                        plth.modify(pltAKey);
                    }
                }

                String pName = getClass().getSimpleName();

                cinfoAltKey.updateLastUpdatePname(pName);
                chandl.modify(cinfoAltKey);

                //<jp> ピッキング出庫、積増入庫、在庫確認で、同一棚に再入庫しないのであれば、
                // 戻り入庫の搬送指示を行う為にステーションに到着データを記録</jp>
                //<en> Record the arrival data if carry instruction is required on eWareNavi side 
                // and the station operates the arrival reporting.</en>
                if (CarryInfo.RESTORING_FLAG_NOT_SAME_LOC.equals(ci.getRestoringFlag()))
                {
                    PalletSearchKey skey = new PalletSearchKey();
                    skey.setPalletId(ci.getPalletId());
                    if (phandl.count(skey) > 0)
                    {
                        
                        Pallet[] pallet = (Pallet[]) phandl.find(skey);
                        registArrival(ci.getCarryKey(), pallet[0]);
                    }
                    else
                    {
                        registArrival(ci.getCarryKey(), new Pallet());
                    }
                    //<jp> 搬送指示送信要求を行なう。</jp>
                    //<en> Requests to submit the carry instruction.</en>
                    carryRequest();
                }

                //<jp> パレットを入庫中にする。</jp>
                //<en> Modify the pallet to 'storing'.</en>
                PalletAlterKey pakey = new PalletAlterKey();
                pakey.setPalletId(ci.getPalletId());
                pakey.updateStatusFlag(Pallet.PALLET_STATUS_STORAGE_PLAN);

                pakey.updateLastUpdatePname(pName);
                phandl.modify(pakey);

                // 在庫確認、空棚確認に関係するテーブルをチェック・更新します。
                carryOperate.updateInventoryCheckInfo(ci);
            }
            else
            {
                //<jp> 6024018=取得したインスタンス{0}の属性{1}の値が不正です。{1}={2}</jp>
                //<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
                Object[] tObj = {
                        "CarryInfomation",
                        "RetrievalDetail",
                        String.valueOf(ci.getRetrievalDetail()),
                };
                RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "InOutStationOperator", tObj);
                throw new InvalidDefineException(WmsMessageFormatter.format(6024018, tObj));
            }

        }
        catch (InvalidStatusException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
    }

    /**<jp>
     * このステーションに対する作業表示および作業指示更新処理を行ないます。
     * 入出庫兼用ステーションの作業指示更新処理は以下の作業を行ないます。
     * （搬送区分が入庫の場合）
     *   1.搬送データの搬送状態を開始に変更する。
     *   2.搬送データのCARRYKEYと一致する作業表示データ(<code>OperationDisplay</code>)を削除する。
     *   3.搬送指示送信要求を行う。
     * （搬送区分が出庫の場合）
     *   1.搬送データのCARRYKEYと一致する作業表示データ(<code>OperationDisplay</code>)を削除する。
     *   2.MC作業完了報告を送信する。
     * （搬送区分が直行の場合）
     *   1.搬送データの状態が引当の場合は入庫と同じ処理を行なう。
     *   2.搬送データの状態が引当以外の場合は出庫と同じ処理を行なう。
     * ステーションの作業表示属性が作業表示なしの場合にこのメソッドが呼び出された場合は、
     * InvalidDefineExceptionを通知します。
     * @param ci 対象CarryInformationインスタンス
     * @param  caller 呼び出し元クラス
     * @throws InvalidDefineException インスタンス内の情報に不整合がある場合に通知されます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      対象データが見つからない場合に通知されます。
     </jp>*/
    /**<en>
     * Update processing of on-line indication and job instruction for this station.
     * Following procedures are taken to update the job instruction for station which handles both storage and retrieval.
     * (when the transport section is 'storage')
     *   1.Modify the carry state in carry data to 'start'.
     *   2.Delete data of on-line indication(<code>OperationDisplay</code>) that matches the CARRYKEY of carry data.
     *   3.Then submits the request for carry instruction.
     * (when the transport section is 'retrieval')
     *   1.Delete data of on-line indiciaton(<code>OperationDisplay</code>) that matches the CARRYKEY of carry data.
     *   2.Submit the MC work vompletion report.
     * (when transport section is 'direct travel')
     *   1.If the status of carry data is 'allocated', follow the same proccess as storage.
     *   2.If the status of carry data is anything other than 'allocated', follow the process for retrieval.
     * If this method is called for the station which has the attribute of no on-line indication, 
     * it notifies InvalidDefineException.
     * @param ci :objective CarryInformation instance
     * @param caller :called Class
     * @throws InvalidDefineException :Notifies if there are any data inconsistency in the instance.
     * @throws ReadWriteException     :Notifies if any rtouble occured in data access. 
     * @throws NotFoundException      :Notifies if there is no such data. 
     </en>*/
    public void operationDisplayUpdate(CarryInfo ci, Class caller)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        CarryInfoHandler cih = new CarryInfoHandler(getConnection());
        CarryInfoAlterKey ciAKey = new CarryInfoAlterKey();
        OperationDisplayHandler odh = new OperationDisplayHandler(getConnection());
        OperationDisplaySearchKey odKey = new OperationDisplaySearchKey();

        //<jp> 本メソッドはステーションが作業指示有りの運用の場合のみ有効</jp>
        //<en> This method is valid only for stations which operates the on-line indication.</en>
        if (Station.OPERATION_DISPLAY_INSTRUCTION.equals(getOperationDisplay()))
        {
            //<jp> 入庫の場合、搬送データの搬送状態を開始に変更し、作業表示データを削除する。</jp>
            //<en> If storing, modify the carry status of carry data to 'start' and delete the data of</en>
            //<en> on-line indication.</en>
            if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag()))
            {
                //<jp> 搬送状態を開始に変更する</jp>
                //<en> alter the status of carry data to 'start'</en>
                ciAKey.setCarryKey(ci.getCarryKey());
                ciAKey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                ciAKey.updateLastUpdatePname(getClass().getSimpleName());
                cih.modify(ciAKey);

                //<jp> 作業表示データの削除</jp>
                //<en> Deleting the on-line indicaiton data</en>
                odKey.setCarryKey(ci.getCarryKey());
                odh.drop(odKey);

                //<jp> 搬送指示送信要求を行う。</jp>
                //<en> Requests to submit the carry instruction.</en>
                carryRequest();
            }
            //<jp> 出庫の場合、作業表示データを削除する。</jp>
            //<jp> また、MC作業完了報告を送信する。</jp>
            //<en> If retrieving, delete the data of on-line indication.</en>
            //<en> Then submit the MC work completion report.</en>
            else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag()))
            {
                odKey.setCarryKey(ci.getCarryKey());
                odh.drop(odKey);

                try
                {
                    SystemTextTransmission.id45send(ci, getConnection());
                }
                catch (Exception e)
                {
                    throw new ReadWriteException();
                }
            }
            //<jp> 直行の場合、搬送状態に基づいて処理を行います。</jp>
            //<en> In case of direct travel, carry out the process according to the transfer status</en>
            else if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
            {
                //<jp> 搬送状態が引当の場合は入庫作業表示と同じ扱いとする。</jp>
                //<jp> 搬送データの搬送状態を開始に変更し、作業表示データを削除する。</jp>
                //<en> If carry status is 'allocation', handle just as the storage.</en>
                //<en> Modify the carry status of carry data to 'start' and delete data of on-line indication.</en>
                if (CarryInfo.CMD_STATUS_ALLOCATION.equals(ci.getCmdStatus()))
                {
                    //<jp> 搬送状態を開始に変更する</jp>
                    //<en> alter the status of carry data to 'start'</en>
                    ciAKey.setCarryKey(ci.getCarryKey());
                    ciAKey.updateCmdStatus(CarryInfo.CMD_STATUS_START);
                    ciAKey.updateLastUpdatePname(getClass().getSimpleName());
                    cih.modify(ciAKey);

                    //<jp> 作業表示データの削除</jp>
                    //<en> Deleting the on-line indicaiton data</en>
                    odKey.setCarryKey(ci.getCarryKey());
                    odh.drop(odKey);

                    //<jp> 搬送指示送信要求を行う。</jp>
                    //<en> Then submits the request for carry instruction.</en>
                    carryRequest();
                }
                //<jp> それ以外の状態であれば、出庫作業表示と同じ扱いとする。</jp>
                //<jp> 作業表示データを削除する。</jp>
                //<jp> また、MC作業完了報告を送信する。</jp>
                //<en> If the status indicates any other status, handle as retrieval.</en>
                //<en> Therefore, delete data of on-line indication.</en>
                //<en> Then submit the MC work completion report.</en>
                else
                {
                    odKey.setCarryKey(ci.getCarryKey());
                    odh.drop(odKey);
                    try
                    {
                        SystemTextTransmission.id45send(ci, getConnection());
                    }
                    catch (Exception e)
                    {
                        throw new ReadWriteException();
                    }
                }
            }
            else
            {
                //<jp> FTTB ログメッセージ出力</jp>
                //<jp> 上記以外の状態は処理できない作業種別なので例外をスローする。</jp>
                //<en> For all other status other than above, their work types are unavailable for processing.</en>
                //<en> It therefore throws exception.</en>
                throw new InvalidDefineException("");
            }

        }
        else
        {
            //<jp> FTTB ログメッセージ出力</jp>
            //<jp> 作業表示無しのステーションに対してこのメソッドが呼ばれた場合はをスローする。</jp>
            //<en> If this method is called for the station which does not operate the on-line indications, </en>
            //<en> throws exception.</en>
            throw new InvalidDefineException("");
        }
    }

    // Public methods ------------------------------------------------

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------

}
//end of class

