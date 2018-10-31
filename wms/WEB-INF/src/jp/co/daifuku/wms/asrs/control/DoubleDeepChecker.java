// $Id: DoubleDeepChecker.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.asrs.control;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.entity.DoubleDeepShelf;
import jp.co.daifuku.wms.asrs.location.FreeAllocationShelfOperator;
import jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator;
import jp.co.daifuku.wms.asrs.location.InOutStationOperator;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.ShelfOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.StationOperatorFactory;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.CarryInfoController;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.entity.BankSelect;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.handler.db.SQLErrors;
import jp.co.daifuku.wms.handler.util.HandlerUtil;

/**
 * ダブルディープ運用における入庫条件チェック、出庫条件チェックを行うクラスです。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/21</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/07/28</TD><TD>M.INOUE</TD><TD>ダブルディープ対応追加</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 */
public class DoubleDeepChecker
{
    // Class fields --------------------------------------------------

    // Class variables -----------------------------------------------
    /**
     * データベース接続用のコネクション
     */
    private Connection _conn = null;

    /**
     * 搬送データ操作用ハンドラクラス
     */
    private CarryInfoHandler _carryInfoHandler = null;

    /**
     * パレットデータ操作用ハンドラクラス
     */
    private PalletHandler _palletHandler = null;

    // 2010/05/11 Y.Osawa ADD ST
    /**<jp>
     * 出庫指示可能数（ステーションの最大出庫指示可能数からそのステーションへの搬送数を引いた数）
     </jp>*/
    private int _palletMaxQty = 0;

    // 2010/05/11 Y.Osawa ADD ED

    // Class method --------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 7996 $,$Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $");
    }

    // Constructors --------------------------------------------------
    /**
     * 新しい<code>DoubleDeepChecker</code>のインスタンスを作成します
     * @param conn データベースの接続情報
     */
    public DoubleDeepChecker(Connection conn)
    {
        // ハンドラインスタンス生成
        _conn = conn;
        _carryInfoHandler = new CarryInfoHandler(_conn);
        _palletHandler = new PalletHandler(_conn);
    }

    // Public methods ------------------------------------------------
    /**
     * 搬送指示の条件チェックを行います。
     * 指定された<code>DoubleDeepShelf</code>インスタンスの位置より搬送指示が可能かどうかチェックします。
     * 搬送指示不可の場合、受け取ったCarryInformationの搬送先ステーションNoを倉庫ステーションNoに変更し
     * 搬送先ステーションNoで指定されたロケーションNoを空棚に変更します。
     * 呼び出し元には搬送指示可能であればtrue, 搬送指示不可であればfalseを返します。
     * @param  cInfo           搬送対象CarryInformationインスタンス
     * @param  shf             搬送先ロケーションNoを参照するDoubleDeepShelfインスタンス
     * @return                 搬送指示可能ならばtrue、搬送指示ができない場合はfalse。
     * @throws ReadWriteException データベースの読書きで障害が発生した場合に通知されます。
     * @throws InvalidDefineException インスタンスの更新条件に不正があった場合に通知されます。
     * @throws ScheduleException 一意データが複数存在した場合に通知されます。
     */
    public boolean storageCheck(CarryInfo cInfo, Shelf shf)
            throws ReadWriteException,
                InvalidDefineException,
                ScheduleException
    {
        Shelf pairFarShelf = null;

        // 搬送先がダブルディープ用棚インスタンスの場合、棚状態チェックを行う。
        if (!(shf instanceof DoubleDeepShelf))
        {
            // ダブルディープでないならば、搬送指示可能で返す。
            return true;
        }

        DoubleDeepShelf ddShelf = (DoubleDeepShelf)shf;
        // 搬送先が奥棚か？
        if (BankSelect.BANK_SELECT_FAR.equals(ddShelf.getSide()))
        {
            // 搬送先が奥棚の場合は搬送指示可能で返す。
            return true;
        }

        // ----------------------------------------------------------
        // -- ここからは、指定されたDoubleDeepShelfが手前棚の場合
        // ----------------------------------------------------------
        //
        // 搬送先が手前棚の場合、奥棚の状態に応じて処理を行う。
        // 他のタスクから更新されないために手前棚をロックしておく。
        ShelfHandler sHandle = new ShelfHandler(getConnection());
        ShelfSearchKey sKey = new ShelfSearchKey();
        sKey.setStationNo(ddShelf.getStationNo());
        try
        {
            sHandle.findForUpdate(sKey);
        }
        catch (LockTimeOutException e)
        {
            return false;
        }

        try
        {
            // 手前棚のペアの奥棚の棚情報を取得する。
            pairFarShelf = (Shelf)StationFactory.makeStation(getConnection(), ddShelf.getPairStationNo());
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }

        // 奥棚が使用禁止棚、又は、アクセス不可棚
        if (Shelf.PROHIBITION_FLAG_NG.equals(pairFarShelf.getProhibitionFlag())
                || Shelf.ACCESS_NG_FLAG_NG.equals(pairFarShelf.getAccessNgFlag()))
        {
            // 奥棚は使用出来ないので、搬送可能を返す。
            return true;
        }

        // 奥棚が空棚
        if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(pairFarShelf.getStatusFlag()))
        {
            // 指定された棚を開放する。
            releaseShelf(cInfo, ddShelf);
            return false;
        }

        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

        // 奥棚が空棚でない時は、ペアの奥棚の棚Noで入庫データ、出庫データのCarryInfoの読込みを行う。
        // 見つからない場合、実パレットと見なしそのまま入庫可能とする。

        // ペア棚（奥棚）のステーションNoで入庫搬送データを読み込む
        // 入庫で搬送先がペア棚の搬送データ
        carryKey.clear();
        carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE);
        carryKey.setDestStationNo(pairFarShelf.getStationNo());
        if (getCarryInfoHandler().count(carryKey) > 0)
        {
            // 奥棚に入庫搬送データが存在する場合、作業が完了するまで待つ。
            // 今回の搬送指示はNGとする。
            // 指定された棚を開放する。
            releaseShelf(cInfo, ddShelf);
            return false;
        }

        // ペア棚（奥棚）のステーションNoで出庫、棚間移動の搬送データを読み込む
        // ペア棚に関連する搬送データの先頭の内容を元に今回の搬送データが指示可能か判断する。
        carryKey.clear();
        carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL, "=", "(", "", false);
        carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RACK_TO_RACK, "=", "", ")", true);
        carryKey.setSourceStationNo(pairFarShelf.getStationNo());
        carryKey.setCmdStatusOrder(false);
        carryKey.setPriorityOrder(true);
        carryKey.setRegistDateOrder(true);
        carryKey.setCarryKeyOrder(true);
        CarryInfo[] carryArray = (CarryInfo[])getCarryInfoHandler().find(carryKey);
        if (carryArray.length <= 0)
        {
            // ペア棚（奥棚）のステーションNoで出庫、棚間移動の搬送データが無いので搬送可能で返す。
            return true;
        }

        // 搬送状態の判断は先頭データのみ使用する。
        CarryInfo carry = carryArray[0];

        if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(carry.getRetrievalDetail())
                && cInfo.getScheduleNo().equals(carry.getScheduleNo()))
        {
            // 奥棚の出庫搬送データが在庫確認の場合、奥棚の在庫確認作業が完了するまで入庫は待機する。
            // 指定された棚を開放する。
            releaseShelf(cInfo, ddShelf);
            return false;
        }

        // 奥棚の出庫搬送データの状態を確認する。

        // 引当
        if (CarryInfo.CMD_STATUS_ALLOCATION.equals(carry.getCmdStatus()))
        {
            // 特に処理しない
        }
        // 開始
        else if (CarryInfo.CMD_STATUS_START.equals(carry.getCmdStatus()))
        {
            // 開始の場合、奥棚の出庫搬送データが即出庫されるかチェックする。
            // 条件に合致する場合、今回の入庫は中断する。
            if (pairCarryCheck(carry))
            {
                if (!CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(cInfo.getRetrievalDetail()))
                {
                    // 今回の入庫搬送データが在庫確認以外ならば、入庫は待機する。
                    // 指定された棚を開放する。
                    releaseShelf(cInfo, ddShelf);
                    return false;
                }
            }
        }
        // 応答待ち、指示済み
        else if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(carry.getCmdStatus())
                || CarryInfo.CMD_STATUS_INSTRUCTION.equals(carry.getCmdStatus()))
        {
            // 指定された棚を開放する。
            releaseShelf(cInfo, ddShelf);
            return false;

        }
        // 掬い完了、出庫完了、到着
        else if (CarryInfo.CMD_STATUS_PICKUP.equals(carry.getCmdStatus())
                || CarryInfo.CMD_STATUS_COMP_RETRIEVAL.equals(carry.getCmdStatus())
                || CarryInfo.CMD_STATUS_ARRIVAL.equals(carry.getCmdStatus()))
        {
            // Pallet検索用
            PalletSearchKey palletKey = new PalletSearchKey();

            // 出庫完了状態で別のステーションから入庫された場合実棚となるためパレットの検索を行う
            CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
            CarryInfoSearchKey cskey = carryControl.getEmptyShelfPallet();
            palletKey.clear();
            palletKey.setCurrentStationNo(ddShelf.getPairStationNo());
            palletKey.setKey(Pallet.PALLET_ID, cskey, "!=", "", "", true);
            Pallet[] plt = (Pallet[])getPalletHandler().find(palletKey);
            if (plt.length > 0)
            {
                // 出庫予約、出庫中のパレットが存在する場合、該当パレットの搬送情報を検索する
                if (Pallet.PALLET_STATUS_RETRIEVAL_PLAN.equals(plt[0].getStatusFlag())
                        || Pallet.PALLET_STATUS_RETRIEVAL.equals(plt[0].getStatusFlag()))
                {
                    carryKey.clear();
                    carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
                    carryKey.setPalletId(plt[0].getPalletId());
                    carryKey.setCmdStatusOrder(false);
                    carryKey.setPriorityOrder(true);
                    carryKey.setRegistDateOrder(true);
                    carryKey.setCarryKeyOrder(true);
                    CarryInfo[] planCarry = (CarryInfo[])getCarryInfoHandler().find(carryKey);
                    // 開始
                    if (CarryInfo.CMD_STATUS_START.equals(planCarry[0].getCmdStatus()))
                    {
                        // 開始の場合、奥棚の出庫搬送データが即出庫されるかチェックする。
                        // 条件に合致する場合、今回の入庫は中断する。
                        if (pairCarryCheck(planCarry[0]))
                        {
                            if (!CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(cInfo.getRetrievalDetail()))
                            {
                                // 今回の入庫搬送データが在庫確認以外ならば、入庫は待機する。
                                // 指定された棚を開放する。
                                releaseShelf(cInfo, ddShelf);
                                return false;
                            }
                        }
                    }
                    // 応答待ち、指示済み
                    else if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(planCarry[0].getCmdStatus())
                            || CarryInfo.CMD_STATUS_INSTRUCTION.equals(planCarry[0].getCmdStatus()))
                    {
                        // 指定された棚を開放する。
                        releaseShelf(cInfo, ddShelf);
                        return false;
                    }
                }
            }
            // 既に出庫作業が完了しているため、パレットが存在しない場合は入庫指示可能
            // 特に処理しない
        }
        // 異常
        else if (CarryInfo.CMD_STATUS_ERROR.equals(carry.getCmdStatus()))
        {
            // 奥棚が異常の場合、手前棚には入庫可能なので搬送指示可能とする。
            // 特に処理しない
        }

        // 搬送指示可能
        return true;
    }

    /**
     * 出庫指示の条件チェックを行います。
     * 指定された<code>DoubleDeepShelf</code>インスタンスの位置より出庫指示が可能かどうかチェックします。
     * 出庫指示の前に手前棚の棚間移動が必要な場合は棚間移動スケジュールを行い、新たに生成したCarryInformationをデータベースに保存します。
     * （出庫指示は行いません）
     * 呼び出し元には出庫指示可能であればtrue, 出庫指示不可であればfalseを返します。
     * @param  cInfo           出庫指示の対象となるCarryInformationインスタンス
     * @param  shf             搬送元ロケーションNoを参照するDoubleDeepShelfインスタンス
     * @return                 搬送可能件数以下ならばtrue、搬送可能件数を超えていればfalseを返します。
     * @throws ReadWriteException データベースの読書きで障害が発生した場合に通知されます。
     * @throws InvalidDefineException インスタンスの更新条件に不正があった場合に通知されます。
     * @throws ScheduleException 一意データが複数存在した場合に通知されます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     * @throws NotFoundException 更新、削除すべきデータが見つからない場合に通知されます。
     */
    public boolean retrievalCheck(CarryInfo cInfo, Shelf shf)
            throws ReadWriteException,
                InvalidDefineException,
                ScheduleException,
                LockTimeOutException,
                NotFoundException
    {
        try
        {
            // 搬送元がダブルディープ用棚インスタンスの場合、棚状態チェックを行う。
            if (!(shf instanceof DoubleDeepShelf))
            {
                // ダブルディープではないので出庫指示可能
                return true;
            }

            DoubleDeepShelf ddShelf = (DoubleDeepShelf)shf;

            // 指定されたDoubleDeepShelfが手前棚の場合
            if (BankSelect.BANK_SELECT_NEAR.equals(ddShelf.getSide()))
            {
                try
                {
                    // 棚に対して同時に出庫指示が両方送信される可能性が有るため棚情報をロックする。
                    ShelfHandler slfh = new ShelfHandler(getConnection());
                    ShelfSearchKey sKey = new ShelfSearchKey();
                    sKey.setStationNo(ddShelf.getStationNo());
                    slfh.findForUpdate(sKey);

                    // ロック出来た場合は手前棚の棚間移動の搬送データが無いか確認する。
                    CarryInfoSearchKey cryKey = new CarryInfoSearchKey();
                    cryKey.setRetrievalStationNo(ddShelf.getStationNo());
                    cryKey.setCarryFlag(SystemDefine.CARRY_FLAG_RACK_TO_RACK);
                    cryKey.setCmdStatus(SystemDefine.CMD_STATUS_START);
                    CarryInfo[] cinfo = (CarryInfo[])_carryInfoHandler.find(cryKey);
                    if (cinfo.length > 0)
                    {
                        // 手前棚に棚間移動の搬送データが有ったので、出庫指示不可とする。
                        return false;
                    }

                    // 手前棚は、出庫指示可能とする。
                    return true;
                }
                catch (LockTimeOutException e)
                {
                    return false;
                }
            }

            // ----------------------------------------------------------
            // -- ここからは、指定されたDoubleDeepShelfが奥棚の場合
            // ----------------------------------------------------------

            // CarryInfo検索用
            CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

            // Pallet検索用
            PalletHandler palletHandelr = new PalletHandler(getConnection());
            PalletSearchKey palletKey = new PalletSearchKey();

            // 同時にペアの棚に対して入庫設定が行われた場合、同時に出庫指示、搬送指示が両方送信されるためテーブルをロックする。
            ShelfHandler sHandle = new ShelfHandler(getConnection());
            ShelfSearchKey sKey = new ShelfSearchKey();
            sKey.setStationNo(ddShelf.getPairStationNo());
            Shelf[] shfArray = null;
            try
            {
                shfArray = (Shelf[])sHandle.findForUpdate(sKey);
            }
            catch (LockTimeOutException e)
            {
                return false;
            }

            String pairStatusFlag = ddShelf.getPairStatusFlag();
            if (shfArray != null && shfArray.length >= 0)
            {
                pairStatusFlag = shfArray[0].getStatusFlag();
            }

            // ペア棚（手前棚）が空棚
            if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(pairStatusFlag))
            {
                if ((!cInfo.getRetrievalStationNo().equals(ddShelf.getStationNo()))
                        && (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(cInfo.getRetrievalDetail())))
                {
                    // 棚間移動後の在庫確認出庫
                    ShelfHandler slfh = new ShelfHandler(getConnection());
                    ShelfSearchKey slfKey = new ShelfSearchKey();
                    slfKey.setStationNo(cInfo.getRetrievalStationNo());
                    Shelf[] wslf = (Shelf[])slfh.find(slfKey);

                    // 棚間移動前のペア棚（奥棚）の在庫確認搬送データを取得
                    carryKey.clear();
                    carryKey.setRetrievalStationNo(wslf[0].getPairStationNo());
                    carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK);
                    carryKey.setScheduleNo(cInfo.getScheduleNo());
                    CarryInfo[] ci = (CarryInfo[])getCarryInfoHandler().find(carryKey);
                    if (ci.length != 0)
                    {
                        // 元々の棚のペア棚（奥棚）が出庫中、又は、入庫搬送中ならば、在庫確認
                        // する搬送先ステーションのタイプをみる。
                        Station inventDestSt = StationFactory.makeStation(getConnection(), cInfo.getDestStationNo());
                        StationOperator inventDestStOpe =
                                StationOperatorFactory.makeOperator(getConnection(), inventDestSt.getStationNo(),
                                        inventDestSt.getClassName());
                        if (inventDestStOpe instanceof InOutStationOperator)
                        {
                            // 在庫確認先が入出庫兼用ステーションの場合、
                            // 元々の棚のペア棚（奥棚）が出庫中、又は、入庫搬送中なので、出庫不可。
                            return false;
                        }
                        else if (inventDestStOpe instanceof FreeRetrievalStationOperator)
                        {
                            // 在庫確認先がコの字出庫側ステーション
                            FreeRetrievalStationOperator freeRetrieval = (FreeRetrievalStationOperator)inventDestStOpe;
                            if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci[0].getCarryFlag())
                                    && (freeRetrieval.getStation().getStationNo().equals(ci[0].getSourceStationNo()) || freeRetrieval.getFreeStorageStationNo().equals(
                                            ci[0].getSourceStationNo())))
                            {
                                // ペア棚（奥棚）の確認STが同一で、コの字出庫側に到着、又は、戻り入庫搬送中
                                // なので、手前棚は出庫可能
                                return true;
                            }
                            else
                            {
                                // ペア棚（奥棚）の確認STが同一でない。
                                // 又は、同一の確認STだけどコの字出庫側に到着前なので、出庫不可
                                return false;
                            }
                        }
                        else
                        {
                            // その他のタイプのステーション
                            // 元々の棚のペア棚（奥棚）が出庫中、又は、入庫搬送中なので、出庫不可。
                            return false;
                        }
                    }
                    else
                    {
                        // 棚間移動前のペア棚の在庫確認は終わっている。
                        return true;
                    }
                }
                // 出庫指示可能
                return true;
            }

            // ペア棚（手前棚）が空棚以外の場合、搬送データ or 棚データから状態をチェックする。

            //------------- ペア棚（手前棚）の棚間移動状態をチェックする
            carryKey.clear();
            carryKey.setRetrievalStationNo(ddShelf.getPairStationNo());
            carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RACK_TO_RACK);
            CarryInfo[] carryArray = (CarryInfo[])getCarryInfoHandler().find(carryKey);
            if (carryArray.length > 0)
            {
                if (CarryInfo.CMD_STATUS_COMP_RETRIEVAL.equals(carryArray[0].getCmdStatus()))
                {
                    // ペア棚（手前棚）は棚間移動中の出庫完了状態ならば、出庫指示可能とする。
                    // 在庫確認出庫で手前棚が棚間移動した時、手前棚は空棚でない（入庫予約棚）時の処理。
                    return true;
                }

                // ペア棚（手前棚）は棚間移動中の出庫動作中なので、出庫指示不可とする。
                return false;
            }

            //------------- ペア棚（手前棚）のステーションNoで入庫搬送データを読み込む
            carryKey.clear();
            // 2010/05/11 Y.Osawa UPD ST
            carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_STORAGE, "=", "(", "", false);
            carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RACK_TO_RACK, "=", "", ")", true);
            carryKey.setDestStationNo(ddShelf.getPairStationNo());
            carryArray = (CarryInfo[])getCarryInfoHandler().find(carryKey);
            if (carryArray.length > 0)
            {
                // 棚間移動データの場合、手前棚の作業が完了するまで待つ。
                if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(carryArray[0].getCarryFlag()))
                {
                    return false;
                }
                // 2010/05/11 Y.Osawa UPD ED

                // ペア棚（手前棚）が予約棚（入庫）の場合
                if (Shelf.LOCATION_STATUS_FLAG_RESERVATION.equals(pairStatusFlag))
                {
                    if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(carryArray[0].getRetrievalDetail()))
                    {
                        // 今回の出庫チェック中の奥棚が在庫確認出庫で、ペア棚（手前棚）も在庫確認の戻り入庫の状態
                        // ならば出庫可能とする。
                        if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(cInfo.getRetrievalDetail())
                                && cInfo.getScheduleNo().equals(carryArray[0].getScheduleNo()))
                        {
                            // ペア棚（手前棚）の在庫確認の戻り入庫の状態ならば出庫可能とする。
                            return true;
                        }
                    }
                }

                // ペア棚（手前棚）が在庫確認以外の入庫搬送データや、手前棚の戻り入庫が在庫確認で奥棚の出庫が
                // 在庫確認以外の場合、手前棚の作業が完了するまで待つ。
                // 今回の出庫は中断する。
                return false;
            }

            //------------- 出庫搬送要求をチェックする。

            // ペア棚（手前棚）の棚Noで出庫の搬送データを読み込む
            // 取得した配列は搬送状態の降順で並べられる。

            carryKey.clear();
            carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
            carryKey.setSourceStationNo(ddShelf.getPairStationNo());
            carryKey.setCmdStatusOrder(false);
            carryKey.setPriorityOrder(true);
            carryKey.setRegistDateOrder(true);
            carryKey.setCarryKeyOrder(true);
            carryArray = (CarryInfo[])getCarryInfoHandler().find(carryKey);
            if (carryArray.length <= 0)
            {
                // 搬送データが見つからない場合、
                // ペア棚（手前棚）に該当するパレットの検索を行い、検索パレットに対する棚間移動処理を行う。
                palletKey.clear();
                palletKey.setCurrentStationNo(ddShelf.getPairStationNo());
                Pallet[] plt = (Pallet[])getPalletHandler().find(palletKey);
                if (plt.length > 0)
                {
                    rackToRackMoveSchedule(plt[0]);
                    return false;
                }
                else
                {
                    // ペア棚（手前棚）が予約棚で、在庫が無い場合は、棚間移動が完了した状態なので、
                    // 奥棚は出庫指示可能とする。
                    if (Shelf.LOCATION_STATUS_FLAG_RESERVATION.equals(pairStatusFlag))
                    {
                        return true;
                    }

                    // Shelfの棚状態が「実棚」で、パレット情報が無いのでエラーメッセージを出力します。
                    // 6026101={0}テーブルに({1})の詳細情報が見つかりません。
                    Object[] mps = {
                        Pallet.STORE_NAME,
                        cInfo.getPalletId(),
                    };
                    RmiMsgLogClient.write(6026101, (String)this.getClass().getName(), mps);
                    throw new ScheduleException();
                }
            }
            else
            {
                // 搬送状態の判断は先頭データのみ使用する。
                CarryInfo nearCarry = carryArray[0];

                // ペア棚（手前棚）の出庫搬送データの状態を確認する。
                // 引当
                if (CarryInfo.CMD_STATUS_ALLOCATION.equals(nearCarry.getCmdStatus()))
                {
                    // 奥棚は当分出庫されないため、ペア棚（手前棚）の棚間移動を行う。
                    Pallet pallet = null;
                    palletKey.clear();
                    palletKey.setPalletId(nearCarry.getPalletId());
                    try
                    {
                        // 検索
                        pallet = (Pallet)palletHandelr.findPrimary(palletKey);
                    }
                    catch (NoPrimaryException e)
                    {
                        throw new ScheduleException(e.getMessage());
                    }
                    rackToRackMoveSchedule(pallet);

                    // 今回は棚間移動のみ行ない、出庫は中断する。
                    return false;
                }
                // 開始
                else if (CarryInfo.CMD_STATUS_START.equals(nearCarry.getCmdStatus()))
                {
                    // ペア棚（手前棚）の搬送データが在庫確認の場合
                    if (nearCarry.getRetrievalDetail().equals(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK))
                    {
                        if (!cInfo.getDestStationNo().equals(nearCarry.getDestStationNo()))
                        {
                            // 同一ステーションに在庫確認出庫しない場合は、先に手前棚を出庫するため
                            // 今回の奥棚出庫は中断する。
                            return false;
                        }
                        else
                        {
                            // 同一ステーションに在庫確認出庫する場合は、先に奥棚を出庫するため
                            // ペア棚（手前棚）を棚間移動する。
                            Pallet pallet = null;
                            palletKey.clear();
                            palletKey.setPalletId(nearCarry.getPalletId());
                            try
                            {
                                // 検索
                                pallet = (Pallet)palletHandelr.findPrimary(palletKey);
                            }
                            catch (NoPrimaryException e)
                            {
                                throw new ScheduleException(e.getMessage());
                            }
                            rackToRackMoveSchedule(pallet);
                            return false;
                        }
                    }

                    // 開始状態の場合、ペア棚（手前棚）の出庫搬送データが即出庫されるかチェックする。
                    // 条件に合致する場合、今回の出庫は中断する。
                    if (pairCarryCheck(nearCarry))
                    {
                        return false;
                    }

                    // 即出庫されるデータではない場合、ペア棚（手前棚）の棚間移動を行う。
                    Pallet pallet = null;
                    palletKey.clear();
                    palletKey.setPalletId(nearCarry.getPalletId());
                    try
                    {
                        // 検索
                        pallet = (Pallet)palletHandelr.findPrimary(palletKey);
                    }
                    catch (NoPrimaryException e)
                    {
                        throw new ScheduleException(e.getMessage());
                    }
                    rackToRackMoveSchedule(pallet);

                    // 今回は棚間移動のみ行ない、出庫は中断する。
                    return false;
                }
                // 応答待ち、指示済み
                else if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(nearCarry.getCmdStatus())
                        || CarryInfo.CMD_STATUS_INSTRUCTION.equals(nearCarry.getCmdStatus()))
                {
                    // ペア棚（手前棚）の搬送データが既に出庫指示済みの場合、今回の出庫は中断する。
                    return false;
                }
                // 掬い完了、出庫完了、到着
                else if (CarryInfo.CMD_STATUS_PICKUP.equals(nearCarry.getCmdStatus())
                        || CarryInfo.CMD_STATUS_COMP_RETRIEVAL.equals(nearCarry.getCmdStatus())
                        || CarryInfo.CMD_STATUS_ARRIVAL.equals(nearCarry.getCmdStatus()))
                {
                    // 在庫確認の場合は出庫指示可能
                    if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(cInfo.getRetrievalDetail()))
                    {
                        return true;
                    }

                    // 出庫完了状態で別のステーションから入庫された場合実棚となるためパレットの検索を行う
                    // ペア棚（手前棚）に該当するパレットの検索を行い、検索パレットに対する棚間移動処理を行う。
                    CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
                    CarryInfoSearchKey cskey = carryControl.getEmptyShelfPallet();
                    palletKey.clear();
                    palletKey.setCurrentStationNo(ddShelf.getPairStationNo());
                    palletKey.setKey(Pallet.PALLET_ID, cskey, "!=", "", "", true);
                    Pallet[] plt = (Pallet[])getPalletHandler().find(palletKey);
                    if (plt.length > 0)
                    {
                        // 出庫中の場合は応答待ち、指示済みのデータが存在するかチェックする
                        if (Pallet.PALLET_STATUS_RETRIEVAL.equals(plt[0].getStatusFlag()))
                        {
                            carryKey.clear();
                            carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
                            carryKey.setPalletId(plt[0].getPalletId());
                            String[] cmd = {
                                CarryInfo.CMD_STATUS_INSTRUCTION,
                                CarryInfo.CMD_STATUS_WAIT_RESPONSE
                            };
                            carryKey.setCmdStatus(cmd, true);
                            if (getCarryInfoHandler().count(carryKey) > 0)
                            {
                                // ペア棚（手前棚）の搬送データが既に出庫指示済みの場合、今回の出庫は中断する。
                                return false;
                            }
                        }
                        // 出庫予約の場合は即出庫かどうかチェックする
                        else if (Pallet.PALLET_STATUS_RETRIEVAL_PLAN.equals(plt[0].getStatusFlag()))
                        {
                            carryKey.clear();
                            carryKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
                            carryKey.setPalletId(plt[0].getPalletId());
                            carryKey.setCmdStatusOrder(false);
                            carryKey.setPriorityOrder(true);
                            carryKey.setRegistDateOrder(true);
                            carryKey.setCarryKeyOrder(true);
                            CarryInfo[] planCarry = (CarryInfo[])getCarryInfoHandler().find(carryKey);

                            // 開始状態の場合、ペア棚（手前棚）の出庫搬送データが即出庫されるかチェックする。
                            // 条件に合致する場合、今回の出庫は中断する。
                            if (pairCarryCheck(planCarry[0]))
                            {
                                return false;
                            }
                        }
                        // 棚間移動スケジュール
                        rackToRackMoveSchedule(plt[0]);
                        return false;
                    }
                    else
                    {
                        // 既に出庫作業が完了しているため、出庫指示可能
                        return true;
                    }
                }
                // 異常
                else if (CarryInfo.CMD_STATUS_ERROR.equals(nearCarry.getCmdStatus()))
                {
                    // ペア棚（手前棚）が異常の場合、今回の出庫は中断する。
                    return false;
                }
            }
        }
        catch (ReadWriteException e)
        {
            if ((Exception)e.getCause() instanceof SQLException)
            {
                // 元々の例外の原因がSQLExceptionなので、ロードしたSQLエラークラスに発生した
                // 例外のエラーコードを渡してWMS内部エラーコードに変換します。
                SQLErrors errorsClass = HandlerUtil.loadSQLErrors(HandlerUtil.loadStoreMetaData(Stock.STORE_NAME));
                if (errorsClass.parseErrorCode((SQLException)e.getCause()) == SQLErrors.ERR_DEADLOCK)
                {
                    // 6024046=デッドロックが発生しました。 mckey={0}
                    Object[] tObj = new Object[1];
                    tObj[0] = cInfo.getCarryKey();
                    RmiMsgLogClient.write(6024046, LogMessage.F_WARN, getClass().getName(), tObj);
                    return false;
                }
            }

            // デッドロック以外のExceptionの場合は、そのままExceptionをthrowします。
            throw e;
        }

        return false;

    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------
    /**
     * 指定された搬送データが即出庫されるデータか否かを検証します。以下の条件に全て合致すればtrueを、一つでも異なればfalseを返します。
     * ・搬送データが搬送先に対する先頭データ
     * ・搬送先ステーションが属するグループコントローラーがオンライン以外
     * ・搬送先ステーションが出庫モード
     * ・搬送先ステーションが中断中ではない
     * ・搬送先ステーションの出庫指示CarryInformationの件数が指示可能件数以下
     * @param  cInfo           搬送対象CarryInformation
     * @return                 即出庫される搬送データであればtrue、それ以外の場合はfalseを返します。
     * @throws ReadWriteException データベースの読書きで障害が発生した場合に通知されます。
     * @throws InvalidDefineException インスタンスの更新条件に不正があった場合に通知されます。
     */
    protected boolean pairCarryCheck(CarryInfo cInfo)
            throws ReadWriteException,
                InvalidDefineException
    {
        // CarryInfo検索用
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

        // 搬送先ステーションが同一でかつ搬送状態が開始
        // retrievalSenderと同じ昇順でソート
        carryKey.clear();
        carryKey.setDestStationNo(cInfo.getDestStationNo());
        carryKey.setCmdStatus(CarryInfo.CMD_STATUS_START);
        carryKey.setPriorityOrder(true);
        carryKey.setRegistDateOrder(true);
        carryKey.setCarryKeyOrder(true);

        // 搬送データを読み込み
        CarryInfo[] carryArray = (CarryInfo[])getCarryInfoHandler().find(carryKey);
        if (carryArray.length == 0)
        {
            // 搬送データが見つからなかった場合は例外を通知
            // 6026078=指定された{0}のインスタンスが見つかりません
            Object[] tObj = new Object[1];
            tObj[0] = "CarryInformation";
            RmiMsgLogClient.write(6026078, LogMessage.F_WARN, this.getClass().getName(), tObj);
            throw new InvalidDefineException(WmsMessageFormatter.format(6026078, tObj));
        }

        // 検索した搬送データとメソッド呼び出し時に指定された搬送データが同一のものであれば、
        // 指定された搬送データは先頭出庫データとみなす。
        if (carryArray[0].getCarryKey().equals(cInfo.getCarryKey()))
        {
            // 先頭出庫データが出庫予約の状態ならば棚に在ると判断する。
            PalletSearchKey pltkey = new PalletSearchKey();
            PalletHandler plth = getPalletHandler();
            pltkey.setPalletId(carryArray[0].getPalletId());
            pltkey.setStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN);

            Pallet pallet = null;
            try
            {
                pallet = (Pallet)plth.findPrimary(pltkey);
            }
            catch (NoPrimaryException e)
            {
                throw new ReadWriteException(e);
            }

            if (pallet == null)
            {
                //DFKLOOK 3.5 Start
                // 先頭出庫データが指示された可能性がある。
                return true;
                //DFKLOOK 3.5 End
            }

            Station destSt = null;
            try
            {
                destSt = StationFactory.makeStation(getConnection(), carryArray[0].getDestStationNo());
            }
            catch (NotFoundException e)
            {
                throw new InvalidDefineException(e.getMessage());
            }

            //            // 出庫搬送条件のチェック
            //            // 搬送先ステーションが属するグループコントローラーがオンライン以外の場合は搬送不可
            //            if (!GroupController.isOnLine(getConnection(), destSt.getControllerNo()))
            //            {
            //                return false;
            //            }
            //
            //            // 出庫搬送先ステーションの作業モード確認
            //            // 搬送先Stationが入出庫兼用ならばモードの確認が必要。入庫専用ステーションかモード管理無しならばモードは確認しない。
            //            if (Station.STATION_TYPE_INOUT.equals(destSt.getStationType())
            //                    && !destSt.getModeType().equals(Station.MODE_TYPE_NONE))
            //            {
            //                // 搬送先ステーションが以下のいずれかの状態であれば、即出庫は行われないと判断する。
            //                // 作業モード切替要求が入庫または出庫モード切替要求中
            //                // 作業モードがニュートラルまたは入庫モード
            //                if ((!Station.MODE_REQUEST_NONE.equals(destSt.getModeRequest()))
            //                        || (Station.CURRENT_MODE_NEUTRAL.equals(destSt.getCurrentMode()))
            //                        || (Station.CURRENT_MODE_STORAGE.equals(destSt.getCurrentMode())))
            //                {
            //                    return false;
            //                }
            //            }
            //
            //            // 出庫搬送先ステーションの中断中フラグを確認。
            //            if (Station.SUSPEND_ON.equals(destSt.getSuspend()))
            //            {
            //                // 中断中ならば即出庫は行われないと判断する。
            //                return false;
            //            }
            //
            //            // 検索キーセット
            //            carryKey.clear();
            //            carryKey.setPalletIdCollect();
            //            String[] cmd = {
            //                CarryInfo.CMD_STATUS_INSTRUCTION,
            //                CarryInfo.CMD_STATUS_WAIT_RESPONSE,
            //                CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
            //                CarryInfo.CMD_STATUS_ARRIVAL
            //            };
            //            carryKey.setCmdStatus(cmd, true);
            //            carryKey.setDestStationNo(destSt.getStationNo());
            //
            //            if (destSt.getMaxPalletQty() <= _carryInfoHandler.count(carryKey))
            //            {
            //                //<jp> 出庫指示可能数をオーバーしている。</jp>
            //                //<en> The number of retrieval instruction exceeded the available number set by the regulation</en>
            //                return false;
            //            }
            // 2010/05/11 Y.Osawa UPD ED

            //<jp> 搬送先ステーションの条件チェック</jp>
            //<en> Check the condition with the receiving station</en>
            if (!checkDestStation(cInfo, pallet, destSt))
            {
                //<jp> 搬送先条件NG</jp>
                //<en> if conditions are not met at the receiving station</en>
                return false;
            }

            // 先頭データでかつ出庫指示可能な場合はtrueを返す。
            return true;
        }
        else
        {
            // 先頭ではないので、falseを返す。
            return false;
        }
    }

    // 2010/05/11 Y.Osawa ADD ST
    /**
     * 搬送先ステーションチェック
     * 搬送先ステーションが即出庫可能かチェックします
     * @param  cInfo           搬送対象CarryInformation
     * @param  pallet          搬送対象Pallet
     * @param  checkSt         搬送先ステーション
     * @return 搬送先ステーションが即出庫可能であればtrue、不可能であればfalseを返します。
     * @throws ReadWriteException   データベースの読書きで障害が発生した場合に通知されます。
     * @throws InvalidDefineException インスタンスの更新条件に不正があった場合に通知されます。
     */
    protected boolean checkDestStation(CarryInfo cInfo, Pallet pallet, Station checkSt)
            throws ReadWriteException,
                InvalidDefineException
    {
        RouteController rc = new RouteController(getConnection(), true);

        Station[] stations = null;

        // 代表ステーションの場合
        if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(checkSt.getWorkplaceType()))
        {
            // 代表ステーションに紐づくステーションNo.を取得する
            StationSearchKey sskey = new StationSearchKey();
            StationHandler sth = new StationHandler(getConnection());
            sskey.setParentStationNo(checkSt.getStationNo());
            sskey.setStationNoOrder(true);
            stations = (Station[])sth.find(sskey);
        }
        // 通常ステーションの場合
        else
        {
            stations = new Station[1];
            stations[0] = checkSt;
        }

        int maxQty = 0;
        boolean stOk = false;

        // 代表ステーションに紐づくステーションのチェックを行う
        for (Station destSt : stations)
        {
            if (!rc.retrievalDetermin(pallet, destSt, false, false, false, false, false, false))
            {
                //<jp> 搬送ルートが無い</jp>
                //<en> Transport route cannot be found</en>
                continue;
            }

            //<jp> 搬送先ステーションの条件チェック</jp>
            //<en> Check the condition with the receiving station</en>
            if (!destRightStation(cInfo, destSt))
            {
                //<jp> 搬送先条件NG</jp>
                //<en> if conditions are not met at the receiving station</en>
                continue;
            }

            // 出庫指示可能数（ステーションの最大出庫指示可能数からそのステーションへの搬送数を引いた数）を取得します。
            maxQty = maxQty + getPalletMaxQty();

            // 1件でもOKなステーションが存在する場合は代表ステーションに搬送OKとする
            stOk = true;
        }

        // 全ステーションNG
        if (!stOk)
        {
            return false;
        }

        // 代表ステーションの場合、代表ステーションに対する指示数もチェックする
        Station mainSt = null;
        if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(checkSt.getWorkplaceType()))
        {
            mainSt = checkSt;
        }
        // 搬送先ステーションが通常ステーションかつ親ステーションが代表ステーションであれば、代表ステーションの指示数チェックも行なう
        else if (Station.WORKPLACE_TYPE_FLOOR.equals(checkSt.getWorkplaceType()))
        {
            StationSearchKey sKey = new StationSearchKey();
            StationHandler sth = new StationHandler(getConnection());
            sKey.setStationNo(checkSt.getParentStationNo());
            sKey.setWorkplaceType(Station.WORKPLACE_TYPE_MAIN_STATION);
            try
            {
                mainSt = (Station)sth.findPrimary(sKey);
            }
            catch (NoPrimaryException e)
            {
                throw new ReadWriteException(e);
            }
        }

        if (mainSt != null)
        {
            if (getRetrievalCount(mainSt) >= maxQty)
            {
                return false;
            }
        }

        return true;
    }

    /**<jp>
     * 搬送先ステーションの状態をチェックします。
     * 搬送先Stationが作業モード切替要求中ではない
     * 作業モード管理を行う場合、入出庫兼用ステーションなら搬送先Stationの作業モードが出庫
     * 搬送先Stationが中断中ではない
     * 出庫指示可能件数については搬送先ステーションに関連するCarryInformationが規定数を越えていないかをCheckする。
     * 該当ステーションが搬送元になっているCarryInformationについては考慮しない。
     * @param  cInfo       搬送対象CarryInformation
     * @param  destSt      搬送先ステーション
     * @return             搬送可能と確認できた場合はtrue、ダメな場合がfalse
     * @throws ReadWriteException   データベースの読書きで障害が発生した場合に通知されます。
     </jp>*/
    protected boolean destRightStation(CarryInfo cInfo, Station destSt)
            throws ReadWriteException
    {
        // 出庫搬送条件のチェック
        // 搬送先ステーションが属するグループコントローラーがオンライン以外の場合は搬送不可
        if (!GroupController.isOnLine(getConnection(), destSt.getControllerNo()))
        {
            return false;
        }

        // 出庫搬送先ステーションの作業モード確認
        // 搬送先Stationが入出庫兼用ならばモードの確認が必要。入庫専用ステーションかモード管理無しならばモードは確認しない。
        if (Station.STATION_TYPE_INOUT.equals(destSt.getStationType())
                && !destSt.getModeType().equals(Station.MODE_TYPE_NONE))
        {
            // 搬送先ステーションが以下のいずれかの状態であれば、即出庫は行われないと判断する。
            // 作業モード切替要求が入庫または出庫モード切替要求中
            // 作業モードがニュートラルまたは入庫モード
            if ((!Station.MODE_REQUEST_NONE.equals(destSt.getModeRequest()))
                    || (Station.CURRENT_MODE_NEUTRAL.equals(destSt.getCurrentMode()))
                    || (Station.CURRENT_MODE_STORAGE.equals(destSt.getCurrentMode())))
            {
                return false;
            }
        }

        // 出庫搬送先ステーションの中断中フラグを確認。
        if (Station.SUSPEND_ON.equals(destSt.getSuspend()))
        {
            // 中断中ならば即出庫は行われないと判断する。
            return false;
        }

        int carryCount = getRetrievalCount(destSt);
        if (destSt.getMaxPalletQty() <= carryCount)
        {
            //<jp> 出庫指示可能数をオーバーしている。</jp>
            //<en> The number of retrieval instruction exceeded the available number set by the regulation</en>
            return false;
        }

        // 出庫指示可能数（ステーションの最大出庫指示可能数からそのステーションへの搬送数を引いた数）をセットします。
        setPalletMaxQty(destSt.getMaxPalletQty() - carryCount);

        return true;
    }

    /**
     * 指定ステーションの出庫指示数取得処理
     * @param  destSt          指定ステーション
     * @return 指定ステーションに対する出庫指示数を返します。
     *         出庫指示から戻り入庫掬い完了までを出庫指示件数とします。但し、ユニット出庫は出庫完了以降のものを除きます。
     * @throws ReadWriteException DBアクセス時に例外が発生した場合に通知されます。
     */
    protected int getRetrievalCount(Station destSt)
            throws ReadWriteException
    {
        // CarryInfo検索用
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

        // ダブルディープかチェックする
        StationController stControll = new StationController(getConnection(), getClass());
        boolean isDouble = stControll.isDoubleDeepConnectStation(destSt);

        // 検索キーセット
        carryKey.clear();

        // ダブルディープかつ入出庫兼用ステーションの場合は戻り入庫のすくい完了までを出庫指示件数としてカウントする
        if (isDouble && Station.STATION_TYPE_INOUT.equals(destSt.getStationType()))
        {
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_INSTRUCTION, "=", "((", "", false);
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_WAIT_RESPONSE, "=", "", "", false);
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_COMP_RETRIEVAL, "=", "((", "", false);
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "=", "", ")", true);
            carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK, "=", "(", "", false);
            carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_PICKING, "=", "", "", false);
            carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING, "=", "", ")))", true);
            carryKey.setDestStationNo(destSt.getStationNo(), "=", "", ")", false);

            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_INSTRUCTION, "=", "((", "", false);
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_WAIT_RESPONSE, "=", "", "", false);
            carryKey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "=", "", ")", true);
            carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK, "=", "(", "", false);
            carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_ADD_STORING, "=", "", "", false);
            carryKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_PICKING, "=", "", ")", true);
            carryKey.setSourceStationNo(destSt.getStationNo(), "=", "", ")", false);
        }
        else
        {
            String[] cmd = {
                CarryInfo.CMD_STATUS_INSTRUCTION,
                CarryInfo.CMD_STATUS_WAIT_RESPONSE,
                CarryInfo.CMD_STATUS_COMP_RETRIEVAL,
                CarryInfo.CMD_STATUS_ARRIVAL
            };
            carryKey.setCmdStatus(cmd, true);
            carryKey.setDestStationNo(destSt.getStationNo());
        }

        int carryCount = _carryInfoHandler.count(carryKey);

        return carryCount;
    }
    // 2010/05/11 Y.Osawa ADD ED

    /**
     * 指定されたPalletに対する棚間移動搬送データの作成を行います。
     * 新たに生成したCarryInformationは出庫指示の対象となるようデータベースに登録します。
     * 搬送データ作成時に、入庫棚を決定します。
     * @param  plt 棚間移動対象Pallet
     * @return 棚間移動用搬送データ
     * @throws ReadWriteException データベースの読書きで障害が発生した場合に通知されます。
     * @throws InvalidDefineException インスタンスの更新条件に不正があった場合に通知されます。
     * @throws ScheduleException 該当エリアNo.が見つからなかったときスローされます
     * @throws LockTimeOutException ロックタイムアウトが発生した場合に通知します。
     */
    protected CarryInfo rackToRackMoveSchedule(Pallet plt)
            throws ReadWriteException,
                InvalidDefineException,
                ScheduleException,
                LockTimeOutException
    {
        Station aisle = null;
        CarryInfo newCarryInfo = new CarryInfo();

        String sourceShelf = "";

        try
        {
            // 搬送先決定処理を行い、搬送先の入庫棚を決定する。
            // 同一アイルから棚決定を行いたいので、アイルステーションNoを取得して現在位置にセット
            sourceShelf = plt.getCurrentStationNo();
            Shelf shelf = (Shelf)StationFactory.makeStation(getConnection(), plt.getCurrentStationNo());
            aisle = StationFactory.makeStation(getConnection(), shelf.getParentStationNo());
            plt.setCurrentStationNo(aisle.getStationNo());
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }

        try
        {
            // 棚決定処理を行う。
            WareHouse wareHouse = (WareHouse)StationFactory.makeStation(getConnection(), aisle.getWhStationNo());
            ShelfOperator cop = new ShelfOperator(getConnection());
            Station destStation = cop.findRackToRackMove(plt, wareHouse, sourceShelf);
            if (destStation != null)
            {
                // フリーアロケーション運用の場合、荷幅、棚使用フラグを更新
                if (WareHouse.FREE_ALLOCATION_ON.equals(wareHouse.getFreeAllocationType()))
                {
                    FreeAllocationShelfOperator freeshelfop =
                            new FreeAllocationShelfOperator(getConnection(), destStation.getStationNo());
                    freeshelfop.alterWidth(plt);
                }

                // 検索した棚間移動先棚を入庫予約にする。
                ShelfOperator shelfop = new ShelfOperator(getConnection(), destStation.getStationNo());
                shelfop.alterPresence(Shelf.LOCATION_STATUS_FLAG_RESERVATION);

                // 棚間移動先のロケーションが見つかったので搬送データを作成する。

                // 棚間移動先ロケーションが見つからない場合、奥棚指示をキャンセルするとパレット状態が残ってしまう。
                // よって、パレット状態変更を移動先ロケーション決定後とする。
                // パレットの状態を出庫予約、引当状態を引当済みに変更、最新更新日時を更新
                // Pallet検索・更新用
                PalletHandler palletHandelr = new PalletHandler(getConnection());
                PalletAlterKey palletAlterKey = new PalletAlterKey();

                // 検索キーセット
                palletAlterKey.clear();
                palletAlterKey.setPalletId(plt.getPalletId());
                palletAlterKey.updateAllocationFlag(Pallet.ALLOCATION_FLAG_ALLOCATED);
                palletAlterKey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
                palletAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                // 更新
                palletHandelr.modify(palletAlterKey);

                // 棚間移動先のShelfインスタンスを取得します。
                Shelf destShelf = (Shelf)destStation;
                // エリアNoを取得します。
                AreaController areaCtl = new AreaController(getConnection(), getClass());
                String areaNo = null;
                try
                {
                    areaNo = areaCtl.getAreaNoOfWarehouse(destShelf.getWhStationNo());
                }
                catch (NoPrimaryException e)
                {
                    throw new InvalidDefineException(e.getMessage());
                }
                String locationNo = areaCtl.toParamLocation(destShelf.getStationNo());

                // Stock検索用
                StockHandler stockHandler = new StockHandler(getConnection());
                StockSearchKey stockKey = new StockSearchKey();
                Stock[] stock = null;
                // 検索キーセット
                stockKey.clear();
                stockKey.setPalletId(plt.getPalletId());

                // 検索
                stock = (Stock[])stockHandler.find(stockKey);

                // CarryInformationインスタンス生成
                WMSSequenceHandler sequence = new WMSSequenceHandler(getConnection());
                String carryKey = sequence.nextCarryKey();

                // WorkInfo登録用
                WorkInfoHandler workInfoHandler = new WorkInfoHandler(getConnection());

                WarenaviSystemController wSys = new WarenaviSystemController(getConnection(), getClass());

                for (int i = 0; i < stock.length; i++)
                {
                    // WorkInformationインスタンス生成
                    WorkInfo wi = new WorkInfo();
                    // 作業No
                    wi.setJobNo(sequence.nextWorkInfoJobNo());
                    // 作業区分
                    wi.setJobType(WorkInfo.JOB_TYPE_ASRS_RACK_TO_RACK);
                    // 状態フラグ
                    wi.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);
                    // ハードウェア区分
                    wi.setHardwareType(WorkInfo.HARDWARE_TYPE_ASRS);
                    // 在庫ID
                    wi.setStockId(stock[i].getStockId());
                    // システム接続キー（搬送キー）
                    wi.setSystemConnKey(carryKey);
                    // 予定日
                    wi.setPlanDay(wSys.getWorkDay());
                    // 荷主コード
                    wi.setConsignorCode(stock[i].getConsignorCode());
                    // 予定エリア
                    wi.setPlanAreaNo(areaNo);
                    // 予定棚
                    wi.setPlanLocationNo(locationNo);
                    // 商品コード
                    wi.setItemCode(stock[i].getItemCode());
                    // ロットNo
                    wi.setPlanLotNo(stock[i].getLotNo());
                    // 作業数=0
                    wi.setResultQty(0);
                    // ユーザID
                    wi.setUserId(WmsParam.SYS_USER_ID);
                    // 端末No
                    wi.setTerminalNo(WmsParam.SYS_TERMINAL_NO);
                    // 登録処理名
                    wi.setRegistPname(getClass().getSimpleName());
                    // 最終更新処理名
                    wi.setLastUpdatePname(getClass().getSimpleName());

                    workInfoHandler.create(wi);
                }

                // CARRYINFO表登録
                newCarryInfo.setCarryKey(carryKey);
                newCarryInfo.setSourceStationNo(sourceShelf);

                // 作業種別
                newCarryInfo.setWorkType(CarryInfo.WORK_TYPE_RACKMOVE_FROM);
                // 出庫ロケーションNo
                newCarryInfo.setRetrievalStationNo(sourceShelf);
                // 搬送対象パレット
                newCarryInfo.setPalletId(plt.getPalletId());
                // 搬送先StationNo
                newCarryInfo.setDestStationNo(destStation.getStationNo());
                // アイルStationNo
                newCarryInfo.setAisleStationNo(destStation.getParentStationNo());
                // グループNo
                newCarryInfo.setGroupNo(0);
                // 搬送状態：開始
                newCarryInfo.setCmdStatus(CarryInfo.CMD_STATUS_START);
                // 優先区分
                newCarryInfo.setPriority(CarryInfo.PRIORITY_EMERGENCY);
                // 再入庫フラグ
                newCarryInfo.setRestoringFlag(CarryInfo.RESTORING_FLAG_NOT_SAME_LOC);
                // 搬送区分：棚間移動
                newCarryInfo.setCarryFlag(CarryInfo.CARRY_FLAG_RACK_TO_RACK);
                // 出庫指示詳細
                newCarryInfo.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_UNIT);
                // 作業NO
                WMSSequenceHandler seqHandler = new WMSSequenceHandler(getConnection());
                newCarryInfo.setWorkNo(seqHandler.nextWorkNo());
                // 到着時刻:null
                newCarryInfo.setArrivalDate(null);
                // 制御情報
                newCarryInfo.setControlinfo(null);
                // キャンセル区分:キャンセル要求なし
                newCarryInfo.setCancelRequest(CarryInfo.CANCEL_REQUEST_UNDEMAND);
                // キャンセル要求日時
                newCarryInfo.setCancelRequestDate(null);
                // スケジュールNo
                newCarryInfo.setScheduleNo(seqHandler.nextScheduleNo());
                // 最終ステーションNo
                newCarryInfo.setEndStationNo(destStation.getStationNo());
                // 登録処理名
                newCarryInfo.setRegistPname(getClass().getSimpleName());
                // 最終更新処理名
                newCarryInfo.setLastUpdatePname(getClass().getSimpleName());

                getCarryInfoHandler().create(newCarryInfo);

                // 棚間移動先が搬送指示送信可能かチェックを行う
                if (!storageCheck(newCarryInfo, destShelf))
                {
                    // 指示送信不可時はロールバックを行う
                    getConnection().rollback();
                    return null;
                }

                //<jp> テキスト送信前にトランザクションをCommit</jp>
                //<en>Commit the transaction before sending the text.</en>
                getConnection().commit();
            }
            else
            {
                return null;
            }
        }
        catch (DataExistsException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
        catch (SQLException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }

        return newCarryInfo;
    }

    /**
     * コネクションオブジェクトを返します。
     *
     * @return コネクションオブジェクト
     */
    protected Connection getConnection()
    {
        return _conn;
    }

    /**
     * carryInfoHandlerを返します。
     *
     * @return carryInfoHandler
     */
    protected CarryInfoHandler getCarryInfoHandler()
    {
        return _carryInfoHandler;
    }

    /**
     * carryInfoHandlerを返します。
     *
     * @return carryInfoHandler
     */
    protected PalletHandler getPalletHandler()
    {
        return _palletHandler;
    }

    // 2010/05/11 Y.Osawa ADD ST
    /**
     * 出庫指示可能数（ステーションの最大出庫指示可能数からそのステーションへの搬送数を引いた数）をセットします。
     * @param i 出庫指示可能数
     */
    protected void setPalletMaxQty(int i)
    {
        _palletMaxQty = i;
    }

    /**
     * 出庫指示可能数（ステーションの最大出庫指示可能数からそのステーションへの搬送数を引いた数）を返します。
     * @return _palletMaxQtyを返します。
     */
    protected int getPalletMaxQty()
    {
        return _palletMaxQty;
    }

    // 2010/05/11 Y.Osawa ADD ED

    // Private methods -----------------------------------------------
    /**
     * CarryInformationインスタンスの搬送先を棚から倉庫に変更します。また、指定された棚を空棚に更新します。
     * @param  cInfo           搬送データ
     * @param  shf             開放を行う<code>Shelf</code>インスタンス
     * @throws ReadWriteException データベースの読書きで障害が発生した場合に通知されます。
     * @throws InvalidDefineException インスタンスの更新条件に不正があった場合に通知されます。
     */
    private void releaseShelf(CarryInfo cInfo, Shelf shf)
            throws ReadWriteException,
                InvalidDefineException
    {
        try
        {
            // 在庫確認の場合、出庫棚に入庫する必要があるので
            // 棚の開放は行わない。
            if (!CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(cInfo.getRetrievalDetail()))
            {
                // CarryInfo更新用
                CarryInfoHandler carryInfoHandler = new CarryInfoHandler(_conn);
                CarryInfoAlterKey carryAlterKey = new CarryInfoAlterKey();
                // 検索キーセット
                carryAlterKey.clear();
                carryAlterKey.setCarryKey(cInfo.getCarryKey());
                carryAlterKey.updateDestStationNo(shf.getWhStationNo());
                carryAlterKey.updateAisleStationNo(null);
                carryAlterKey.updateLastUpdatePname(getClass().getSimpleName());
                // 更新
                carryInfoHandler.modify(carryAlterKey);

                // Shelf更新用
                ShelfHandler shelfHandler = new ShelfHandler(_conn);
                ShelfAlterKey shelfAlterKeyk = new ShelfAlterKey();
                // 検索キーセット
                shelfAlterKeyk.clear();
                shelfAlterKeyk.setStationNo(shf.getStationNo());
                shelfAlterKeyk.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
                // 更新
                shelfHandler.modify(shelfAlterKeyk);
            }
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
    }

    /**
     * ペア空棚なしのログを登録します。
     * @param  zone           ペア空棚の存在しないアイル、ゾーンを保持するShelfエンティティ
     */
    private void insertNoPairEmptyLog(Shelf zone)
    {
        // 6024070=ペアの空棚が無くなります。予定外出庫設定などでペア空棚を作成してください。Aisle={0} HardZone={1} SoftZone={2}
        Object[] tObj = new Object[3];
        tObj[0] = zone.getParentStationNo();
        tObj[1] = zone.getHardZoneId();
        tObj[2] = zone.getSoftZoneId();
        RmiMsgLogClient.write(6024070, LogMessage.F_WARN, getClass().getName(), tObj);
    }
}
//end of class

