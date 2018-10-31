// $Id: ReleaseAllocateOperator.java 7952 2010-05-24 09:10:31Z ota $
package jp.co.daifuku.wms.retrieval.allocate;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import java.math.BigDecimal;
import java.sql.Connection;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 引当解除を行うクラスです。<BR>
 *
 * @version $Revision: 7952 $, $Date: 2010-05-24 18:10:31 +0900 (月, 24 5 2010) $
 * @author  kishimoto
 * @author  Last commit: $Author: ota $
 */


public class ReleaseAllocateOperator
        extends AbstractOperator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと呼び出し元クラスを指定して
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public ReleaseAllocateOperator(Connection conn, Class caller)
            throws CommonException

    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 在庫情報の引当解除を行います。<BR>
     * AS/RSデータの場合、紐付くパレット情報の更新も行います。<BR>
     * @param stockId 在庫ID
     * @param clearQty 引当解除数
     * @param carryKey 搬送キー
     * @return 引当を解除した場合、true。出来なかった場合は、falseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean allocateClear(String stockId, int clearQty, String carryKey)
            throws CommonException
    {
        try
        {
            // 在庫情報のロック
            Stock stock = lock(stockId);

            if (stock == null)
            {
                return false;
            }

            // 在庫情報の更新
            updateStock(stockId, clearQty);

            // パレット情報の更新
            String palletId = stock.getPalletId();
            if (!StringUtil.isBlank(palletId))
            {
                updatePallet(palletId, carryKey);
            }

            return true;
        }
        catch (LockTimeOutException e)
        {
            // ロック処理に失敗時は、falseを返す
            return false;
        }
    }

    /**
     * 作業情報の削除を行います。<BR>
     * AS/RSデータの場合、紐付く搬送情報の削除も行います。<BR>
     * @param jobNo 作業No.
     * @param sysConnKey システム接続キー
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public void allocateWorkDelete(String jobNo, String sysConnKey)
            throws CommonException
    {
        // 作業情報の削除
        deleteWorkInfo(jobNo);

        // 搬送情報の削除
        if (!StringUtil.isBlank(sysConnKey))
        {
            deleteCarryInfo(sysConnKey);
        }
    }

    /**
     * 移動作業情報、作業情報、搬送情報の削除を行います。<BR>
     *
     * @param moveJobNo 移動作業情報の作業No.
     * @param workJobNo 作業情報の作業No.
     * @param carryKey システム接続キー
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public void allocateWorkDelete(String moveJobNo, String workJobNo, String carryKey)
            throws CommonException
    {
        // 移動作業情報の削除
        deleteMoveWorkInfo(moveJobNo);

        // 作業情報の削除
        if (!StringUtil.isBlank(workJobNo))
        {
            deleteWorkInfo(workJobNo);
        }

        // 搬送情報の削除
        if (!StringUtil.isBlank(carryKey))
        {
            deleteCarryInfo(carryKey);
        }
    }

    /**
     * 搬送キー単位での引当解除を行います。<BR>
     * @param ci 搬送キー
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public void allocateClearOfCarryKey(CarryInfo ci)
            throws CommonException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wkey = new WorkInfoSearchKey();
        wkey.setSystemConnKey(ci.getCarryKey());
        wkey.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING, "=", "(", "", false);
        wkey.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART, "=", "", ")", false);
        // 補充の場合は、移動作業情報と結合する
        if (ci.getWorkType().equals(CarryInfo.WORK_TYPE_NORMAL_REPLENISHMENT)
                || ci.getWorkType().equals(CarryInfo.WORK_TYPE_EMERGENCY_REPLENISHMENT))
        {
            wkey.setCollect(new FieldName(WorkInfo.STORE_NAME, FieldName.ALL_FIELDS));
            wkey.setCollect(MoveWorkInfo.JOB_NO);
            wkey.setJoin(WorkInfo.JOB_NO, MoveWorkInfo.WORK_CONN_KEY);
        }
        wkey.setPlanUkeyOrder(true);

        // 搬送キーに紐付く作業情報を取得
        WorkInfo[] works = (WorkInfo[])wih.find(wkey);

        int workNum = works.length;
        if (works == null || workNum == 0)
        {
            // 6006005=更新対象データがありません。テーブル名:{0}
            Object[] tObj = {
                WorkInfo.STORE_NAME
            };
            RmiMsgLogClient.write(6006005, LogMessage.F_WARN, getCallerName(), tObj);
            throw new NotFoundException();
        }

        // 次の予定一意キー
        String nextPlanUkey = "";

        for (int i = 0; i < workNum; i++)
        {
            // 取得した作業情報に紐付く在庫の更新
            updateStock(works[i].getStockId(), works[i].getPlanQty());

            // パレット情報の更新
            updatePallet(ci.getPalletId(), ci.getCarryKey());

            // 搬送が補充作業の場合、移動作業情報を削除する
            if (ci.getWorkType().equals(CarryInfo.WORK_TYPE_NORMAL_REPLENISHMENT)
                    || ci.getWorkType().equals(CarryInfo.WORK_TYPE_EMERGENCY_REPLENISHMENT))
            {
                String moveJobNo = (String)works[i].getValue(MoveWorkInfo.JOB_NO, "");
                if (!StringUtil.isBlank(moveJobNo))
                {
                    deleteMoveWorkInfo(moveJobNo);
                }
            }

            // 作業情報の削除
            deleteWorkInfo(works[i].getJobNo());

            // 搬送情報の削除
            deleteCarryInfo(ci.getCarryKey());

            // 出庫作業の引当解除の場合
            // 作業情報の更新がすべて処理されてから紐付く予定情報の更新を行う
            if (WorkInfo.JOB_TYPE_RETRIEVAL.equals(works[i].getJobType()))
            {
                // 次回予定一意キーのセット
                if (i == workNum - 1)
                {
                    nextPlanUkey = "";
                }
                else
                {
                    nextPlanUkey = works[i + 1].getPlanUkey();
                }

                // 今回と次回の予定一意キーが異なる場合は予定情報の更新を行なう
                if (!nextPlanUkey.equals(works[i].getPlanUkey()))
                {
                    // 予定情報の更新
                    updateRetrievalPlan(works[i].getPlanUkey());
                }
            }
            if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
            {
                // 棚間移動の引当解除は、棚間移動先の棚を空棚に戻す。
                updateShelfStatusFlag(ci.getDestStationNo(), Shelf.LOCATION_STATUS_FLAG_EMPTY);
            }
            // 2010/05/11 Y.Osawa ADD ST
            else
            {
                // 在庫確認かつ出庫元棚が入庫予約の場合は空棚に更新する
                if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail()))
                {
                    updateShelfStatusFlag(ci.getRetrievalStationNo(), Shelf.LOCATION_STATUS_FLAG_RESERVATION,
                            Shelf.LOCATION_STATUS_FLAG_EMPTY);
                }
            }
            // 2010/05/11 Y.Osawa ADD ED
        }

        // 在庫確認または空棚確認の場合
        if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail())
                || CarryInfo.PRIORITY_CHECK_EMPTY.equals(ci.getPriority()))
        {
            // アイル情報の更新
            updateAisleInventoryCheckOff(ci.getAisleStationNo());

            // 在庫確認情報の更新
            updateInventoryCheckStatusOff(ci.getScheduleNo());
        }
    }

    /**
     * 出庫予定情報に紐付く作業情報の作業状態をチェックします。<BR>
     * 一件でも作業中または完了が存在した場合、trueを返します。<BR>
     * そうでない場合は、falseを返します。<BR>
     * @param planUKey 作業No.
     * @return 作業中または完了データが存在:true そうでない:false
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean checkWorkInfo(String planUKey)
            throws CommonException
    {
        WorkInfoSearchKey workKey = new WorkInfoSearchKey();
        workKey.setJobNoCollect("DISTINCT");
        workKey.setPlanUkey(planUKey);
        workKey.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
        workKey.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING, "=", "(", "", false);
        workKey.setStatusFlag(WorkInfo.STATUS_FLAG_COMPLETION, "=", "", ")", false);

        if ((new WorkInfoHandler(getConnection())).count(workKey) > 0)
        {
            // 1件以上
            return true;
        }
        else
        {
            return false;
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 在庫情報をロックします。<BR>
     * また、AS/RSパッケージが導入されていた場合はパレット情報もロックを行います。<BR>
     * ロック待機する時間、リトライする回数は<code>WmsParam</code>に定義されています。<BR>
     * @param stockId 在庫ID
     * @return ロックした在庫情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected Stock lock(String stockId)
            throws CommonException
    {
        StockSearchKey stockKey = new StockSearchKey();
        stockKey.setStockId(stockId);
        // AS/RS導入チェック
        if ((new WarenaviSystemController(getConnection(), getClass())).hasAsrsPack())
        {
            stockKey.setJoin(Stock.PALLET_ID, "", Pallet.PALLET_ID, "(+)");
        }

        StockController stCon = new StockController(getConnection(), getClass());

        Stock[] stocks =
                (Stock[])stCon.retryLock(stockKey, new StockHandler(getConnection()), WmsParam.ALLOCATE_SLEEP_TIME,
                        WmsParam.ALLOCATE_RETRY_COUNT);
        if (stocks == null || stocks.length == 0)
        {
            return null;
        }
        return stocks[0];
    }

    /**
     * 在庫情報を更新します。<BR>
     * @param stockId 在庫ID
     * @param clearQty 引当解除数
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void updateStock(String stockId, int clearQty)
            throws CommonException
    {
        StockAlterKey stAltKey = new StockAlterKey();
        // 出庫可能数の加算
        stAltKey.setStockId(stockId);
        stAltKey.updateAllocationQtyWithColumn(Stock.ALLOCATION_QTY, BigDecimal.valueOf(clearQty));
        stAltKey.updateLastUpdatePname(getCallerName());

        (new StockHandler(getConnection())).modify(stAltKey);
    }

    /**
     * パレット情報を更新します。<BR>
     * @param palletId パレットID
     * @param carryKey 搬送キー
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void updatePallet(String palletId, String carryKey)
            throws CommonException
    {
        // パレットIDに紐付く搬送キーが複数存在するか調べる
        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
        ciKey.setCarryKeyCollect("DISTINCT");
        ciKey.setPalletId(palletId);
        ciKey.setCarryKey(carryKey, "!=");
        CarryInfoHandler ciH = new CarryInfoHandler(getConnection());

        PalletAlterKey ptAltKey = new PalletAlterKey();
        if (ciH.count(ciKey) == 0)
        {
            // 複数のデータが存在しない場合は、パレット情報を未引当の状態に更新
            ptAltKey.updateAllocationFlag(Pallet.ALLOCATION_FLAG_NOT_ALLOCATED);

            StockHandler stkh = new StockHandler(getConnection());
            StockSearchKey stkKey = new StockSearchKey();
            stkKey.setPalletId(palletId);
            Stock[] stks = (Stock[])stkh.find(stkKey);

            if (stks != null && stks.length == 1 && stks[0].getConsignorCode().equals(WmsParam.IRREGULAR_CONSIGNORCODE)
                    && stks[0].getItemCode().equals(WmsParam.IRREGULAR_ITEMCODE))
            {
                ptAltKey.updateStatusFlag(Pallet.PALLET_STATUS_IRREGULAR);
            }
            else
            {
                ptAltKey.updateStatusFlag(Pallet.PALLET_STATUS_REGULAR);
            }

        }
        else
        {
            // 2010/05/11 Y.Osawa UPD ST
            //            // 複数の搬送データが存在する場合は、応答待ち、指示済みのデータが存在するかチェックする
            //            String[] strobj = {
            //                CarryInfo.CMD_STATUS_WAIT_RESPONSE,
            //                CarryInfo.CMD_STATUS_INSTRUCTION,
            //            };
            //            ciKey.setCmdStatus(strobj, true);
            // 出庫、棚間移動の引当、開始以外が存在するかチェックする
            ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL, "=", "(", "", false);
            ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_RACK_TO_RACK, "=", "", ")", true);
            ciKey.setCmdStatus(CarryInfo.CMD_STATUS_ALLOCATION, "!=", "(", "", true);
            ciKey.setCmdStatus(CarryInfo.CMD_STATUS_START, "!=", "", ")", true);
            // 2010/05/11 Y.Osawa UPD ED
            if (ciH.count(ciKey) > 0)
            {
                // 出庫の引当、開始以外が存在する場合はパレット情報を更新しない
                return;
            }
            // 搬送データが引当、開始のみの場合は、パレット情報の在庫状態を出庫予約に更新
            ptAltKey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
        }
        ptAltKey.setPalletId(palletId);
        ptAltKey.updateLastUpdatePname(getCallerName());

        (new PalletHandler(getConnection())).modify(ptAltKey);
    }

    /**
     * 出庫予定情報を更新します。<BR>
     * @param planUKey 予定一意キー
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void updateRetrievalPlan(String planUKey)
            throws CommonException
    {
        RetrievalPlanAlterKey rpAltKey = new RetrievalPlanAlterKey();

        rpAltKey.setPlanUkey(planUKey);
        if (!checkWorkInfo(planUKey))
        {
            // 紐付く作業情報が存在しない場合は、未作業にする
            rpAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
        }
        rpAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_NOT_SCHEDULE);
        rpAltKey.updateLastUpdatePname(getCallerName());

        (new RetrievalPlanHandler(getConnection())).modify(rpAltKey);
    }

    /**
     * アイル情報の在庫確認中フラグを未作業に更新します。<BR>
     * 指定されたアイル中に在庫確認又は空棚確認搬送データの搬送データが存在しなければ
     * アイル情報の在庫確認中フラグを在庫確認未作業に変更します。
     * @param aisleStationNo アイルステーションNo.
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void updateAisleInventoryCheckOff(String aisleStationNo)
            throws CommonException
    {
        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
        ciKey.setAisleStationNo(aisleStationNo);
        ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
        ciKey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "<");
        ciKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK, "=", "(", "", false);
        ciKey.setPriority(CarryInfo.PRIORITY_CHECK_EMPTY, "=", "", ")", false);

        if ((new CarryInfoHandler(getConnection())).count(ciKey) == 0)
        {
            // アイル情報の在庫確認中フラグを未作業にする
            AisleAlterKey akey = new AisleAlterKey();

            akey.setStationNo(aisleStationNo);
            akey.updateInventoryCheckFlag(Aisle.INVENTORY_CHECK_FLAG_UNSTART);
            (new AisleHandler(getConnection())).modify(akey);
        }
    }

    /**
     * AS/RS在庫確認情報の状態を未作業に更新します。<BR>
     * 指定されたスケジュールNo.中に在庫確認又は空棚確認搬送データの搬送データが存在しなければ
     * AS/RS在庫確認情報の状態を未作業に変更します。
     * @param scheduleNo スケジュールNo.
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void updateInventoryCheckStatusOff(String scheduleNo)
            throws CommonException
    {
        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
        ciKey.setScheduleNo(scheduleNo);
        ciKey.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
        ciKey.setCmdStatus(CarryInfo.CMD_STATUS_ARRIVAL, "<");
        ciKey.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK, "=", "(", "", false);
        ciKey.setPriority(CarryInfo.PRIORITY_CHECK_EMPTY, "=", "", ")", true);

        if ((new CarryInfoHandler(getConnection())).count(ciKey) == 0)
        {
            InventoryCheckAlterKey invChkAKey = new InventoryCheckAlterKey();

            invChkAKey.setScheduleNo(scheduleNo);
            invChkAKey.updateStatusFlag(InventoryCheck.INVENTORY_CHECK_FLAG_UNSTART);
            invChkAKey.updateLastUpdatePname(getCallerName());

            (new InventoryCheckHandler(getConnection())).modify(invChkAKey);
        }
    }

    /**
     * 作業情報を削除します。<BR>
     * @param jobNo 作業No.
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void deleteWorkInfo(String jobNo)
            throws CommonException
    {
        WorkInfoSearchKey workKey = new WorkInfoSearchKey();
        workKey.setJobNo(jobNo);

        (new WorkInfoHandler(getConnection())).drop(workKey);
    }

    /**
     * 移動作業情報を削除します。<BR>
     * @param moveJobNo 作業No.
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void deleteMoveWorkInfo(String moveJobNo)
            throws CommonException
    {
        MoveWorkInfoSearchKey workKey = new MoveWorkInfoSearchKey();
        workKey.setJobNo(moveJobNo);

        (new MoveWorkInfoHandler(getConnection())).drop(workKey);
    }

    /**
     * 搬送情報を削除します。<BR>
     * @param sysConnKey システム接続キー
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void deleteCarryInfo(String sysConnKey)
            throws CommonException
    {
        // 紐付く作業情報が他にない場合に削除
        WorkInfoSearchKey workKey = new WorkInfoSearchKey();
        workKey.setSystemConnKey(sysConnKey);
        workKey.setStatusFlag(WorkInfo.STATUS_FLAG_COMPLETION, "!=");
        workKey.setStatusFlag(WorkInfo.STATUS_FLAG_DELETE, "!=");
        workKey.setJobNoCollect("DISTINCT");

        if ((new WorkInfoHandler(getConnection()).count(workKey) == 0))
        {
            CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
            ciKey.setCarryKey(sysConnKey);

            CarryInfoHandler ciHandler = new CarryInfoHandler(getConnection());

            ciHandler.drop(ciKey);
        }
    }

    /**
     * 指定された棚No.の棚状態を更新します
     *
     * @param stno 棚No.
     * @param status 棚状態
     * @throws ReadWriteException
     *         データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void updateShelfStatusFlag(String stno, String status)
            throws ReadWriteException
    {
        try
        {
            ShelfHandler shelfH = new ShelfHandler(getConnection());
            ShelfAlterKey shelfAKey = new ShelfAlterKey();

            shelfAKey.setStationNo(stno);
            shelfAKey.updateStatusFlag(status);

            shelfH.modify(shelfAKey);
        }
        catch (NotFoundException e)
        {
            // 該当棚が存在しない場合は、処理を行いません。
        }
    }

    // 2010/05/11 Y.Osawa ADD ST
    /**
     * 指定された棚No.、棚状態の棚状態を更新します
     *
     * @param stno 指定棚No.
     * @param status 指定棚状態
     * @param status 更新棚状態
     * @throws ReadWriteException
     *         データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void updateShelfStatusFlag(String stno, String currentStatus, String status)
            throws ReadWriteException
    {
        try
        {
            ShelfHandler shelfH = new ShelfHandler(getConnection());
            ShelfAlterKey shelfAKey = new ShelfAlterKey();

            shelfAKey.setStationNo(stno);
            shelfAKey.setStatusFlag(currentStatus);
            shelfAKey.updateStatusFlag(status);

            shelfH.modify(shelfAKey);
        }
        catch (NotFoundException e)
        {
            // 該当棚が存在しない場合は、処理を行いません。
        }
    }
    // 2010/05/11 Y.Osawa ADD ED

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: ReleaseAllocateOperator.java 7952 2010-05-24 09:10:31Z ota $";
    }
}
