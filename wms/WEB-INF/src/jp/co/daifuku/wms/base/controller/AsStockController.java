// $Id: AsStockController.java,v 1.14 2007/06/05
// 08:40:04 yoshida Exp $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of
 * DAIFUKU Co.,Ltd. Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.wms.asrs.location.FreeAllocationShelfOperator;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AreaController.TEMPORARY_AREA_TYPE;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StockHistory;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RSパッケージ向けの在庫コントローラクラスです。
 * 
 * 
 * @version $Revision: 5342 $, $Date: 2009-10-30 17:02:30 +0900 (金, 30 10 2009) $
 * @author ss
 * @author Last commit: $Author: kishimoto $
 */
// UPDATE_SS (2007-07-06)
public class AsStockController
        extends StockController
{
    // ------------------------------------------------------------
    // fields (upper case only)
    // ------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    // ------------------------------------------------------------
    // class variables (prefix '$')
    // ------------------------------------------------------------
    // private static String $classVar ;

    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ScheduleException システム定義不整合
     * @throws ReadWriteException データベースエラー
     */
    public AsStockController(Connection conn, Class caller) throws ReadWriteException,
            ScheduleException
    {
        super(conn, caller);
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * 出庫処理を行います。<br>
     * スーパークラスの出庫処理を行った後、エリアの仮置在庫作成区分に
     * 基づいて仮置在庫を作成します。<br>
     * また、出庫後に空になったパレットについては、パレット情報を削除します。
     * 
     * @param retrieval 出庫対象在庫データ
     * @param jobType 作業タイプ
     * @param qty 出庫数
     * @param shortage 欠品数
     * @param workDay 作業日
     * @param alloc 引き当てフラグ (引き当てを行うときtrue)
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 在庫情報がnullのとき
     * @throws OperatorException 出庫数が多すぎるか、または負の場合
     * @throws ScheduleException 全部出庫
     * @throws DataExistsException 在庫更新履歴登録済み
     * @throws NoPrimaryException マスタデータ重複
     * @throws LockTimeOutException ロックタイムアウト
     */
    @Override
    public void retrieval(Stock retrieval, String jobType, int qty, int shortage, String workDay, boolean alloc,
            WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                OperatorException,
                ScheduleException,
                NoPrimaryException,
                DataExistsException,
                LockTimeOutException
    {
        // 通常の出庫処理を実行
        super.retrieval(retrieval, jobType, qty, shortage, workDay, alloc, ui);

        // エリアの仮置在庫作成区分が"すべて仮置在庫にする"
        // かつ作業区分がメンテナンス減以外の場合に仮置在庫を作成
        String areaNo = retrieval.getAreaNo();
        if (TEMPORARY_AREA_TYPE.CREATE_TMP_ANY == getAreaCtlr().getTemporaryAreaType(areaNo)
                && !jobType.equals(SystemDefine.JOB_TYPE_EMERGENCY_REPLENISHMENT)
                && !jobType.equals(SystemDefine.JOB_TYPE_NORMAL_REPLENISHMENT))
        {
            // 欠品完了は仮置在庫を作成しない
            if (0 < qty)
            {
                Stock tmpStock = (Stock)retrieval.clone();
                tmpStock.setStockQty(qty);
                insertTempStock(tmpStock, jobType, workDay, ui);
            }
        }

        // pallet processing
        deleteEmptyPallet(retrieval.getPalletId());
    }

    /**
     * 該当パレット上に在庫が存在しないとき、パレット情報を削除します。
     * 
     * @param pallet パレット
     * @return パレットを削除した場合 true.<br>
     * 削除しなかった場合 false.
     * @throws ReadWriteException データベースエラー
     */
    public boolean deleteEmptyPallet(Pallet pallet)
            throws ReadWriteException
    {
        if (null == pallet)
        {
            return false;
        }
        return deleteEmptyPallet(pallet.getPalletId());
    }

    /**
     * 該当パレット上に在庫が存在しないとき、パレット情報を削除します。
     * 
     * @param pltId パレット
     * @return パレットを削除した場合 true.<br>
     * 削除しなかった場合 false.
     * @throws ReadWriteException データベースエラー
     */
    private boolean deleteEmptyPallet(String pltId)
            throws ReadWriteException
    {
        if (StringUtil.isBlank(pltId))
        {
            return false;
        }

        StockSearchKey sskey = new StockSearchKey();
        sskey.setPalletId(pltId);

        int numStock = getStockHandler().count(sskey);

        // remove pallet and change to empty if no
        // stock on the pallet
        if (0 != numStock)
        {
            return false;
        }

        try
        {
            PalletSearchKey plKey = new PalletSearchKey();
            plKey.setPalletId(pltId);
            (new PalletHandler(getConnection())).drop(plKey);
        }
        catch (NotFoundException e)
        {
            // do nothing when not found
        }
        return true;
    }

    /**
     * 対象パレット上の在庫をロックします。<br>
     * パレットの現在ステーションNo.が、asLocationパラメータと同じレコードを
     * 在庫情報,AS/RS棚情報とともにロックして、在庫情報を返します。
     * 
     * @param asLocation パレットの棚No.
     * @param palletId   パレットID
     * @return パレット上のロック済み在庫
     * @throws LockTimeOutException ロックタイムアウト
     * @throws ReadWriteException データベースアクセスエラー
     */
    public Stock[] lockPallet(String asLocation, String palletId)
            throws ReadWriteException,
                LockTimeOutException
    {
        StockSearchKey sskey = new StockSearchKey();

        // pickup ALL field of Stock
        sskey.setCollect(new FieldName(Stock.STORE_NAME, FieldName.ALL_FIELDS));

        // setting JOIN
        sskey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
        sskey.setJoin(Pallet.CURRENT_STATION_NO, Shelf.STATION_NO);

        // setting main key (current station number)
        sskey.setKey(Pallet.CURRENT_STATION_NO, asLocation);

        // パレットIDがセットされている場合は検索条件とする
        if (!StringUtil.isBlank(palletId))
        {
            sskey.setKey(Pallet.PALLET_ID, palletId);
        }

        Stock[] tStocks = (Stock[])getStockHandler().findForUpdate(sskey);
        return tStocks;
    }

    /**
     * 在庫情報を削除します。<br>
     * パレット上の在庫がなくなったときは、パレット情報を削除し、該当の棚を空棚に更新します。
     * 
     * @param delStock 削除対象項目
     *        <ol>
     *        キーは以下のとおりです。
     *        <li>在庫ID
     *        </ol>
     * @param incType 増減区分
     * @param jobType 作業タイプ
     * @param plt ロック済みパレット情報<br>
     *        最後の在庫が削除された場合でも、パレットの削除を行わない場合には、
     *        nullをセットしてください。
     *        <ol>
     *        キーは以下のとおりです。
     *        <li>パレットID
     *        <li>現在ステーションNo.
     *        </ol>
     * @param inoutJobType 更新元作業情報、搬送情報の作業タイプ
     * @param ui ユーザ情報
     * @return パレットを削除した場合、true.<br>
     *         パレットを削除しなかったときはfalseが返ります。<br>
     *         pltがnullのときは、必ずfalseが返ります。
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 削除対象在庫またはパレットなし
     * @throws DataExistsException 在庫更新履歴登録済み
     * @throws NoPrimaryException マスタデータ重複
     * @throws LockTimeOutException ロックタイムアウト
     * @throws ScheduleException AS/RS棚情報定義異常
     * @throws OperatorException 仮在庫の作成に失敗
     */
    public boolean delete(Stock delStock, Pallet plt, String incType, String jobType, String inoutJobType,
            WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException,
                LockTimeOutException,
                ScheduleException,
                OperatorException
    {
        // 直行以外は分析対象
        boolean analysFlg = !(SystemDefine.JOB_TYPE_DIRECT_TRAVEL.equals(inoutJobType));

        // delete stock
        super.delete(delStock, incType, jobType, analysFlg, ui);

        // create temporary stock, if needed
        // 仮置在庫作成区分 : 全ての在庫を作成する : 必ず仮置在庫を作成
        // : 作成しない : 仮置在庫を作成しません
        String areaNo = delStock.getAreaNo();
        TEMPORARY_AREA_TYPE areaType = getAreaCtlr().getTemporaryAreaType(areaNo);
        switch (areaType)
        {
            case CREATE_TMP_ANY:
                // 作業区分がメンテナンス減の場合は仮置在庫を作成しない
                if (SystemDefine.JOB_TYPE_MAINTENANCE_MINUS.equals(jobType))
                {
                    break;
                }
                // 更新元の作業タイプが直行の場合は仮置在庫を作成しない。
                if (SystemDefine.JOB_TYPE_DIRECT_TRAVEL.equals(inoutJobType))
                {
                    break;
                }

                // 仮在庫作成
                String workDay = getWareNaviSystemCtlr().getWorkDay();

                // create stock on temporary area.
                insertTempStock(delStock, jobType, workDay, ui);

                break;

            case CREATE_TMP_NONE:
                // nothing to do
                break;

            default:
                break;
        }

        // pallet processing
        return deleteEmptyPallet(plt);
    }

    /**
     * 仮置在庫を作成します。
     * 
     * @param tmpNewStock 作成対象在庫
     * @param jobType 作業区分
     * @param workDay 作業日
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 削除対象在庫またはパレットなし
     * @throws DataExistsException 在庫更新履歴登録済み
     * @throws NoPrimaryException マスタデータ重複
     * @throws LockTimeOutException ロックタイムアウト
     * @throws ScheduleException AS/RS棚情報定義異常
     * @throws OperatorException 仮在庫の作成に失敗
     */
    public void insertTempStock(Stock tmpNewStock, String jobType, String workDay, WmsUserInfo ui)
            throws ScheduleException,
                ReadWriteException,
                LockTimeOutException,
                OperatorException,
                DataExistsException,
                NotFoundException,
                NoPrimaryException
    {
        String areaNo = tmpNewStock.getAreaNo();
        String tempAreaNo = getAreaCtlr().getTemporaryArea(areaNo);

        Stock tmpStock = new Stock();
        tmpStock.setAreaNo(tempAreaNo);
        tmpStock.setConsignorCode(tmpNewStock.getConsignorCode());
        tmpStock.setItemCode(tmpNewStock.getItemCode());
        tmpStock.setLocationNo(WmsParam.DEFAULT_LOCATION_NO);
        tmpStock.setLotNo(tmpNewStock.getLotNo());

        // lock stock for same location
        Stock[] tgtStock = lock(tmpStock);
        boolean noTarget = ArrayUtil.isEmpty(tgtStock);

        tmpStock.setStorageDay(workDay);
        tmpStock.setStockQty(tmpNewStock.getStockQty());
        tmpStock.setAllocationQty(tmpNewStock.getStockQty());
        tmpStock.setStorageDate(new SysDate());
        tmpStock.setPalletId(null);
        if (noTarget)
        {
            // create new stock
            initStorage(tmpStock, jobType, ui, 0);
        }
        else
        {
            // add to exists stock
            Stock addTarget = tgtStock[0];
            // DFKLOOK v3.4 積み増しの場合は、入庫日時を更新しないように修正
            tmpStock.setStorageDay(null);
            tmpStock.setStorageDate(null);
            addStorage(addTarget, tmpStock, jobType, false, ui, SystemDefine.DEFAULT_REASON_TYPE);
        }
    }

    /**
     * パレット情報を削除し、該当の棚を空棚に更新します。
     * 
     * @param plt 削除対象パレット情報エンティティ (ロック済みであること)
     * @throws NotFoundException 削除対象パレットまたは棚なし
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException 該当棚マスタデータなし (定義エラー)
     */
    protected void deletePallet(Pallet plt)
            throws ReadWriteException,
                NotFoundException,
                ScheduleException
    {
        // delete the pallet
        PalletSearchKey plkey = new PalletSearchKey();
        plkey.setPalletId(plt.getPalletId());
        getPalletHandler().drop(plkey);

        // update shelf status to empty.
        String currentStationNo = plt.getCurrentStationNo();
        try
        {
            ShelfAlterKey shakey = new ShelfAlterKey();

            FreeAllocationShelfOperator freeshelfop =
                    new FreeAllocationShelfOperator(getConnection(), currentStationNo);
            // フリーアロケーション運用の場合、荷幅、棚使用フラグをフリーに更新
            if (freeshelfop.isFreeAllocationStation())
            {
                freeshelfop.alterFreeWidth();
            }

            shakey.setStationNo(currentStationNo);
            shakey.updateStatusFlag(SystemDefine.LOCATION_STATUS_FLAG_EMPTY);
            getShelfHandler().modify(shakey);
        }
        catch (NotFoundException e)
        {
            throw new ScheduleException("No such location found : " + currentStationNo);
        }
    }

    /**
     * 在庫情報を登録します。<br>
     * 新規のパレットの場合は、パレット情報を登録し、 該当の棚を実棚に更新します。
     * 
     * @param stock 登録する在庫情報
     *        <ol>
     *        以下の情報は上書きされます。
     *        <li>在庫ID
     *        <li>パレットID (新規パレットの場合)
     *        <li>登録処理名
     *        <li>最終更新処理名
     *        </ol>
     * @param plt パレット情報<br>
     *        新規パレットの場合は、以下の情報を指定してください。<br>
     *        そのほかの項目はこのメソッド内で上書きされます。
     *        <ol>
     *        <li>荷高
     *        <li>バーコード情報
     *        </ol>
     * 既存のパレットに対して在庫を登録する場合は、nullをセットしてください。
     * @param incType 増減区分
     * @param jobType 作業タイプ
     * @param ui ユーザ情報
     * @return 在庫ID
     * @throws ReadWriteException データベースエラー
     * @throws DataExistsException 該当在庫登録済み
     * @throws NoPrimaryException マスタデータ重複
     * @throws NotFoundException
     *         マスタデータなしもしくは他端末でAS/RS棚情報更新済み
     * @throws ScheduleException 新規パレットIDが重複した,
     *         棚No.の内容が不正
     * @throws LockTimeOutException パレット上在庫ロックタイムアウト
     */
    public String insert(Stock stock, Pallet plt, String incType, String jobType, WmsUserInfo ui)
            throws ReadWriteException,
                DataExistsException,
                NotFoundException,
                NoPrimaryException,
                ScheduleException,
                LockTimeOutException
    {
        // convert area/location to AS/RS location
        String area = stock.getAreaNo();
        String stLoc = stock.getLocationNo();

        AreaController areaCtlr = getAreaCtlr();
        String shelfNo = areaCtlr.toAsrsLocation(area, stLoc);
        String whStNo = areaCtlr.getWhStationNo(area);

        // check empty location
        // find stocks on the shelf
        Stock[] stocks = new Stock[0];
        if (!StringUtil.isBlank(plt.getPalletId()))
        {
            stocks = lockPallet(shelfNo, plt.getPalletId());
        }

        boolean empLoc = ArrayUtil.isEmpty(stocks);

        // check empty P/B
        boolean empPB = (1 == stocks.length) && isEmptyPB(stocks[0]);

        String actIncType = incType;
        // find or create pallet

        if (empLoc)
        {
            // 棚が空棚であった場合

            // create pallet
            plt.setCurrentStationNo(shelfNo);
            plt.setWhStationNo(whStNo);
            // パレットの状態が異常でなければ実棚で登録する。
            if (!SystemDefine.PALLET_STATUS_IRREGULAR.equals(plt.getStatusFlag()))
            {
                plt.setStatusFlag(SystemDefine.PALLET_STATUS_REGULAR);
            }
            plt.setAllocationFlag(SystemDefine.ALLOCATION_FLAG_NOT_ALLOCATED);
            plt.setEmptyFlag(SystemDefine.EMPTY_FLAG_NORMAL);

            String pltId = insertPallet(plt);
            stock.setPalletId(pltId);

            actIncType = SystemDefine.INC_DEC_TYPE_STOCK_INCREMENT;
        }
        else if (empPB)
        {
            // 棚の在庫が空P/Bであった場合

            String pltId = stock.getPalletId();
            // empty P/B to normal pallet
            // delete empPB
            delete(stocks[0], SystemDefine.INC_DEC_TYPE_STOCK_DECREMENT, jobType, ui);

            // update pallet to normal from empty
            PalletAlterKey pakey = new PalletAlterKey();
            pakey.setPalletId(pltId);
            pakey.updateEmptyFlag(SystemDefine.EMPTY_FLAG_NORMAL);

            getPalletHandler().modify(pakey);

            actIncType = SystemDefine.INC_DEC_TYPE_STOCK_INCREMENT;
        }
        else
        {
            // normal pallet
            // nothing to do.
        }

        String stockId = super.insert(stock, actIncType, jobType, ui, SystemDefine.DEFAULT_REASON_TYPE);
        return stockId;

    }

    /**
     * AS/RS棚変更<br>
     * パレット情報、在庫情報、マルチ引当の作業情報の棚を更新し、在庫更新履歴を登録する。
     * 
     * @param ci 搬送情報
     * @param jobType 作業区分:作業区分未指定の場合は、作業情報の作業区分を使用する。
     * @param history 在庫更新履歴を登録する場合はtrue、それ以外はfalse
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 更新対象在庫なし
     * @throws DataExistsException 在庫更新履歴登録済み
     * @throws NoPrimaryException マスタデータ重複
     */
    public void updateAsrsLocation(CarryInfo ci, String[] jobType, boolean history)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        // 対象作業を取得します
        WorkInfoHandler wiH = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
        wiKey.setSystemConnKey(ci.getCarryKey());
        WorkInfo[] works = (WorkInfo[])wiH.find(wiKey);
        WorkInfo priWork = works[0]; // 代表作業として1件目を使用
        String userId = priWork.getUserId();
        String termNo = priWork.getTerminalNo();
        WmsUserInfo ui = WmsUserInfo.buildUserInfo(getConnection(), userId, SystemDefine.HARDWARE_TYPE_ASRS, termNo);

        String asrsLocation = ci.getDestStationNo();
        String paramLocation = getAreaCtlr().toParamLocation(asrsLocation);

        // 予定棚がブランクの場合は作業情報を更新する
        WorkInfoAlterKey wiAlterKey = new WorkInfoAlterKey();
        wiAlterKey.setSystemConnKey(ci.getCarryKey());
        wiAlterKey.setPlanLocationNo("");
        wiAlterKey.updatePlanLocationNo(paramLocation);
        try
        {
            wiH.modify(wiAlterKey);
        }
        catch (NotFoundException e)
        {
            // do nothing when not found
        }

        // パレットの現在ステーションNo.を移動後の棚Noに更新する。
        PalletAlterKey pltAKey = new PalletAlterKey();
        PalletHandler pltH = new PalletHandler(getConnection());
        pltAKey.setPalletId(ci.getPalletId());
        pltAKey.updateCurrentStationNo(asrsLocation);
        pltAKey.updateLastUpdatePname(getCallerName());
        pltH.modify(pltAKey);

        // 在庫情報の棚Noを移動先の棚Noに変更する。
        StockAlterKey stkAltKey = new StockAlterKey();
        StockHandler stkh = new StockHandler(getConnection());

        stkAltKey.setPalletId(ci.getPalletId());
        stkAltKey.updateLocationNo(paramLocation);
        stkAltKey.updateLastUpdatePname(getCallerName());
        stkh.modify(stkAltKey);

        // 在庫更新履歴の登録
        if (history)
        {
            String decJobType;
            String incJobType;
            // 引数の作業区分が未指定の場合は作業情報の作業区分を使用
            if (ArrayUtil.isEmpty(jobType))
            {
                decJobType = priWork.getJobType();
                incJobType = priWork.getJobType();
            }
            else
            {
                decJobType = jobType[JOB_TYPE_DECREMENT_INDEX];
                incJobType = jobType[JOB_TYPE_INCREMENT_INDEX];
            }

            StockSearchKey stkKey = new StockSearchKey();
            stkKey.setPalletId(ci.getPalletId());

            Stock[] stocks = (Stock[])stkh.find(stkKey);

            AreaController areaC = new AreaController(getConnection(), this.getClass());
            for (Stock oldStock : stocks)
            {
                Stock newStock = (Stock)oldStock.clone();

                int stockQty = oldStock.getStockQty();

                // 減算分
                newStock.setLocationNo(areaC.toParamLocation(ci.getRetrievalStationNo()));
                newStock.setStockQty(0);
                insertHistory(newStock, StockHistory.INC_DEC_TYPE_STOCK_DECREMENT, decJobType, stockQty, false, ui,
                        SystemDefine.DEFAULT_REASON_TYPE);

                if (P11Config.isPart11Log() && P11Config.isMasterLog())
                {
                    // create Part11history
                    insertPart11History(newStock, StockHistory.INC_DEC_TYPE_STOCK_DECREMENT, decJobType, stockQty,
                            ui.getDfkUserInfo(), oldStock, null, SystemDefine.DEFAULT_REASON_TYPE);
                }

                // 加算分
                newStock.setLocationNo(areaC.toParamLocation(ci.getDestStationNo()));
                newStock.setStockQty(stockQty);
                insertHistory(newStock, StockHistory.INC_DEC_TYPE_STOCK_INCREMENT, incJobType, stockQty, false, ui, 0);

                if (P11Config.isPart11Log() && P11Config.isMasterLog())
                {
                    // create Part11history
                    insertPart11History(newStock, StockHistory.INC_DEC_TYPE_STOCK_INCREMENT, incJobType, stockQty,
                            ui.getDfkUserInfo(), null, null, SystemDefine.DEFAULT_REASON_TYPE);
                }
            }
        }
    }

    // ------------------------------------------------------------
    // accessor methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // package methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // protected methods
    // ------------------------------------------------------------

    /**
     * 新規パレットを登録します。パレットIDは内部で採番されます。<br>
     * パレットの現在地ステーションに該当する棚状態を実棚に変更します。
     * 
     * @param plt 登録対象パレット<br>
     *        <ol>
     *        以下は上書きされます
     *        <li>パレットID
     *        <li>登録処理名
     *        <li>最終更新処理名
     *        </ol>
     * 
     * @return 採番したパレットID
     * 
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException 採番したパレットIDが登録済み
     *         (シーケンス異常)
     * @throws NotFoundException AS/RS棚情報がすでに更新されている
     */
    protected String insertPallet(Pallet plt)
            throws ReadWriteException,
                ScheduleException,
                NotFoundException
    {
        updateShelfToFill(plt.getCurrentStationNo());

        String pltId = getSeqHandler().nextPalletId();
        try
        {
            plt.setPalletId(pltId);
            plt.setRegistPname(getCallerName());
            plt.setLastUpdatePname(getCallerName());

            getPalletHandler().create(plt);
            return pltId;
        }
        catch (DataExistsException e)
        {
            throw new ScheduleException("Pallet id duplicate : " + pltId);
        }
    }

    /**
     * 棚状態を空棚から実棚に更新します。
     * 
     * @param shelfStat 棚のステーションNo.(棚No)
     * @throws NotFoundException 棚が見つからない
     * @throws ReadWriteException データベースアクセスエラー
     */
    protected void updateShelfToFill(String shelfStat)
            throws NotFoundException,
                ReadWriteException
    {
        ShelfAlterKey shakey = new ShelfAlterKey();
        shakey.setStationNo(shelfStat);
        shakey.updateStatusFlag(SystemDefine.LOCATION_STATUS_FLAG_STORAGED);

        getShelfHandler().modify(shakey);
    }

    /**
     * 空P/Bの在庫であるかどうかチェックします。
     * 
     * @param aStock チェック対象在庫
     * @return 空P/Bのとき true
     */
    public static boolean isEmptyPB(Stock aStock)
    {
        return WmsParam.EMPTYPB_ITEMCODE.equals(aStock.getItemCode());
    }

    // ------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // utility methods
    // ------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * 
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AsStockController.java 5342 2009-10-30 08:02:30Z kishimoto $";
    }
}
