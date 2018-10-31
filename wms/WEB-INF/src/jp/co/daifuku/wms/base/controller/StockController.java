// $Id: StockController.java 7876 2010-04-27 04:00:37Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of
 * DAIFUKU Co.,Ltd. Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.*;
import static jp.co.daifuku.wms.base.exception.OperatorException.*;

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.Part11StockHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.ReasonHandler;
import jp.co.daifuku.wms.base.dbhandler.ReasonSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.Part11StockHistory;
import jp.co.daifuku.wms.base.entity.Reason;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StockHistory;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.SQLSearchKey;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 在庫情報コントローラクラスです。
 * 
 * 
 * @version $Revision: 7876 $, $Date: 2007/06/25
 *          10:26:00 $
 * @author ss
 * @author Last commit: $Author: kishimoto $
 */
// UPDATE_SS (2007-07-06)
public class StockController
        extends AbstractController
{
    // ------------------------------------------------------------
    // fields (upper case only)
    // ------------------------------------------------------------
    // public static final int
    // FIELD_VALUE = 1 ;

    // ------------------------------------------------------------
    // class variables (prefix '$')
    // ------------------------------------------------------------
    // private static String $classVar ;

    // ------------------------------------------------------------
    // instance variables (prefix '_')
    // ------------------------------------------------------------
    /** WarenaviSystemController */
    private WarenaviSystemController _wareNaviSystemCtlr = null;

    /** LocateController */
    private LocateController _locationCtlr = null;

    /** AreaController */
    private AreaController _areaCtlr = null;

    /** WMSSequenceHandler */
    private WMSSequenceHandler _seqHandler = null;

    /** PalletHandler */
    private PalletHandler _palletHandler = null;

    /** ShelfHandler */
    private ShelfHandler _shelfHandler = null;

    /** StockHandler */
    private StockHandler _stockHandler = null;

    /** StockHistoryHandler */
    private StockHistoryHandler _stockHistoryHandler;

    /** Part11StockHistoryHandler */
    private Part11StockHistoryHandler _part11StockHistoryHandler;

    /** 作業区分 : 減算用INDEX */
    public static final int JOB_TYPE_DECREMENT_INDEX = 0;

    /** 作業区分 : 加算用INDEX */
    public static final int JOB_TYPE_INCREMENT_INDEX = 1;

    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、
     * 呼び出し元クラス(ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ScheduleException システム定義不整合
     * @throws ReadWriteException データベースエラー
     */
    public StockController(Connection conn, Class caller)
            throws ReadWriteException,
                ScheduleException
    {
        super(conn, caller);
        prepare();
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * パラメータのキー項目に合致する在庫・棚マスタ情報をロックします。
     * 
     * @param keyStock キー項目をセットした在庫インスタンス
     * <ol>
     * キー項目(セットされている項目はすべてキーとして使用します)
     * <li>エリア
     * <li>棚
     * <li>荷主コード
     * <li>商品コード
     * <li>ロットNo.
     * </ol>
     * @return 該当する在庫
     * @throws ReadWriteException データベースエラー
     * @throws LockTimeOutException ロックタイムアウト
     */
    public Stock[] lock(Stock keyStock)
            throws ReadWriteException,
                LockTimeOutException
    {
        SQLSearchKey key = new StockSearchKey();

        FieldName[] keyFields = {
            Stock.STOCK_ID,
            Stock.AREA_NO,
            Stock.LOCATION_NO,
            Stock.CONSIGNOR_CODE,
            Stock.ITEM_CODE,
            Stock.LOT_NO,
        };

        key = createKey(keyStock, key, keyFields);

        key.setJoin(Stock.AREA_NO, "", Locate.AREA_NO, "(+)");
        key.setJoin(Stock.LOCATION_NO, "", Locate.LOCATION_NO, "(+)");

        return (Stock[])retryLock(key, getStockHandler());
    }

    /**
     * 新規入庫を行います。
     * 
     * @param newStock 在庫情報
     * @param jobType 作業タイプ
     * @param ui ユーザ情報
     * @param reasonType 作業理由区分
     * @return 在庫ID<br>
     *         在庫パッケージ未導入のときは、nullが返されます。
     * @throws ReadWriteException データベースエラー
     * @throws OperatorException 入庫数オーバーフロー,
     * @throws DataExistsException 在庫登録済み
     * @throws NoPrimaryException マスタデータ重複
     * @throws NotFoundException マスタデータなし
     */
    public String initStorage(Stock newStock, String jobType, WmsUserInfo ui, int reasonType)
            throws ReadWriteException,
                OperatorException,
                DataExistsException,
                NotFoundException,
                NoPrimaryException
    {
        // check stock package
        if (!getWareNaviSystemCtlr().hasStockPack())
        {
            return null;
        }

        // check overflow
        int stQty = newStock.getStockQty();
        if (WmsParam.MAX_STOCK_QTY < stQty || 0 > stQty)
        {
            throw new OperatorException(OperatorException.ERR_OVERFLOW);
        }

        return insert(newStock, INC_DEC_TYPE_STOCK_INCREMENT, jobType, ui, reasonType);
    }

    /**
     * 積み増し入庫を行います。
     * 
     * @param target 積み増し対象
     * @param newStock 新在庫
     *        <ol>
     *        以下の項目を参照します。
     *        <li>入庫日
     *        <li>入庫日時
     *        <li>在庫数 (入庫数)
     *        <li>最終出庫日
     *        </ol>
     * 
     * @param jobType 作業タイプ
     * @param alloc 引き当てフラグ
     *        <ul>
     *        <li>true:入庫時に引き当てを行う(出庫可能数を更新しません)
     *        <li>false:入庫時に引き当てを行わない(出庫可能数を更新します)
     *        </ul>
     * @param ui ユーザ情報
     * @param reasonType 作業区分
     * 
     * @return 在庫ID<br>
     *         在庫パッケージ未導入のときは、nullが返されます。
     * 
     * @throws ReadWriteException データベースエラー
     * @throws OperatorException 入庫数オーバーフロー
     * @throws NotFoundException 更新対象在庫なし
     * @throws DataExistsException 在庫更新履歴登録済み
     * @throws NoPrimaryException マスタデータ重複
     */
    public String addStorage(Stock target, Stock newStock, String jobType, boolean alloc, WmsUserInfo ui, int reasonType)
            throws ReadWriteException,
                OperatorException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        // check stock package
        if (!getWareNaviSystemCtlr().hasStockPack())
        {
            return null;
        }

        // parameter for update() method
        // (Stock QTY, Allocation QTY)
        Stock updateStock = (Stock)newStock.clone();

        // reset uncontrolled values
        updateStock.setValue(Stock.PLAN_QTY, null);
        updateStock.setValue(Stock.PALLET_ID, null);

        // new stock qty check and set.
        int newStockQty = target.getStockQty() + newStock.getStockQty();
        boolean stQtyOver = (WmsParam.MAX_STOCK_QTY < newStockQty || 0 > newStockQty);
        if (stQtyOver)
        {
            OperatorException e = new OperatorException(ERR_OVERFLOW);
            // can store qty
            e.setDetail(Integer.valueOf(WmsParam.MAX_STOCK_QTY - target.getStockQty()));
            throw e;
        }
        updateStock.setStockQty(newStockQty);

        // allocation qty.
        if (!alloc)
        {
            int allocQty = target.getAllocationQty() + newStock.getStockQty();
            boolean allocOver = (WmsParam.MAX_STOCK_QTY < allocQty || 0 > allocQty);
            if (allocOver)
            {
                throw new OperatorException(ERR_OVERFLOW);
            }

            updateStock.setAllocationQty(allocQty);
        }

        // update stock
        update(target, updateStock, INC_DEC_TYPE_STOCK_INCREMENT, jobType, ui, reasonType);

        return target.getStockId();
    }

    /**
     * 出庫処理を行います。
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
    @SuppressWarnings("unused")
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
        // check stock package
        if (!getWareNaviSystemCtlr().hasStockPack())
        {
            return;
        }

        if (null == retrieval)
        {
            throw new NotFoundException();
        }

        if ((WmsParam.MAX_STOCK_QTY < qty || 0 > qty) || (WmsParam.MAX_STOCK_QTY < shortage || 0 > shortage))
        {
            throw new OperatorException(OperatorException.ERR_OVERFLOW);
        }

        // with Retrieval Qty
        boolean removed = false;
        if (0 < qty)
        {
            removed = stockUpdateRetrieval(retrieval, jobType, qty, workDay, alloc, ui);
        }

        // with Shortage Qty
        if (0 < shortage)
        {
            if (removed)
            {
                // if remove stock by retrieval, usually no shortage.
                // FIXME logging.
                throw new ScheduleException();
            }
            stockUpdateShortage(retrieval, jobType, shortage, workDay, ui);
        }
    }

    /**
     * 出庫予約を行います。<br>
     * 対象在庫の出庫可能数を予約数分だけ減算して在庫を更新します。
     * 
     * @param target 予約対象在庫
     * @param reserve 予約数
     * @throws NotFoundException 予約対象在庫が見つからない
     * @throws OperatorException 引き当て数オーバーフロー
     * @throws ReadWriteException データベースアクセスエラー
     */
    public void retrievalReserve(Stock target, int reserve)
            throws NotFoundException,
                OperatorException,
                ReadWriteException
    {
        // check stock package
        if (!getWareNaviSystemCtlr().hasStockPack())
        {
            return;
        }

        if (null == target)
        {
            throw new NotFoundException();
        }

        int currAlloc = target.getAllocationQty();
        int rQty = currAlloc - reserve;

        if (0 > rQty)
        {
            throw new OperatorException(OperatorException.ERR_SHORTAGE_ALLOCATION_QTY);
        }

        Stock resStock = new Stock();
        resStock.setAllocationQty(rQty);

        // with allocation Qty
        try
        {
            update(target, resStock, null, null, null, SystemDefine.DEFAULT_REASON_TYPE);
        }
        catch (NoPrimaryException e)
        {
            // never occurs
        }
        catch (DataExistsException e)
        {
            // never occurs
        }
    }

    /**
     * 在庫情報を登録します。
     * 
     * @param stock 登録する在庫情報
     *        <ol>
     *        以下の情報は上書きされます。
     *        <li>在庫ID
     *        <li>パレットID
     *        <li>登録処理名
     *        <li>最終更新処理名
     *        </ol>
     * 
     * @param incType 増減区分
     * @param jobType 作業タイプ
     * @param ui ユーザ情報
     * @param reasonType 作業理由区分
     * @return 在庫ID
     * @throws ReadWriteException データベースエラー
     * @throws DataExistsException 該当在庫登録済み
     * @throws NoPrimaryException マスタデータ重複
     * @throws NotFoundException マスタデータなし
     */
    public String insert(Stock stock, String incType, String jobType, WmsUserInfo ui, int reasonType)
            throws ReadWriteException,
                DataExistsException,
                NotFoundException,
                NoPrimaryException
    {
        String stockId = getSeqHandler().nextStockId();
        String regPname = getCallerName();

        if (!StringUtil.isBlank(stock.getStorageDate()))
        {
            stock.setNewestStorageDate(stock.getStorageDate());
        }


        Stock newStock = (Stock)stock.clone();

        newStock.setStockId(stockId);
        newStock.setLastUpdatePname(regPname);
        newStock.setRegistPname(regPname);
        newStock.setRegistDate(new SysDate());

        getStockHandler().create(newStock);

        // 作業区分がメンテ増、メンテ減の場合のみ実績送信情報を作成する。
        if (SystemDefine.JOB_TYPE_MAINTENANCE_PLUS.equals(jobType))
        {
            // 実績送信情報コントローラの生成
            HostSendController hsc = new HostSendController(getConnection(), this.getClass());
            hsc.insertByStock(newStock, jobType, Math.abs(newStock.getStockQty()), ui);
        }


        if (!StringUtil.isBlank(incType))
        {
            try
            {
                getLocationCtlr().increaseStock(newStock.getAreaNo(), newStock.getLocationNo());
            }
            catch (NotFoundException e)
            {
                // ignore not found.
            }

            insertHistory(newStock, incType, jobType, newStock.getStockQty(), true, ui, reasonType);

            // PART11 Packageなし又は、在庫履歴不要指定時、処理を行いません。
            if (P11Config.isPart11Log() && P11Config.isStockFlag())
            {
                // create Part11history
                insertPart11History(newStock, incType, jobType, newStock.getStockQty(), ui.getDfkUserInfo(), null,
                        null, reasonType);
            }
        }

        return stockId;
    }

    /**
     * 在庫情報を更新します。<br>
     * incTypeがnullのとき、「在庫加減算処理」,「在庫更新履歴の登録」は行いません。
     * 
     * @param oldStock 更新対象キー項目
     *        <ol>
     *        <li>在庫ID
     *        </ol>
     * 
     * @param newStock 在庫更新内容
     *        <ol>
     *        nullでない項目のみ更新されます。
     *        <li>入庫日
     *        <li>入庫日時
     *        <li>最終出庫日
     *        <li>在庫数
     *        <li>出庫可能数
     *        <li>入庫予定数
     *        <li>パレットID
     *        </ol>
     * 
     * @param incType 増減区分
     * @param jobType 作業タイプ
     * @param ui ユーザ情報
     * @param reasonType 作業区分
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 更新対象在庫なし
     * @throws DataExistsException 履歴登録済み
     * @throws NoPrimaryException マスタデータ重複
     */
    public void update(Stock oldStock, Stock newStock, String incType, String jobType, WmsUserInfo ui, int reasonType)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        StockAlterKey akey = new StockAlterKey();
        // 増減区分が入庫在庫増の場合
        if (SystemDefine.JOB_TYPE_NOPLAN_STORAGE.equals(jobType) || SystemDefine.JOB_TYPE_STORAGE.equals(jobType))
        {
            if (!StringUtil.isBlank(newStock.getStorageDate()))
            {
                newStock.setNewestStorageDate(newStock.getStorageDate());
            }
            else
            {
                newStock.setNewestStorageDate(new SysDate());
            }
        }
        // akey.setUpdateValues(newStock);
        FieldName[] upFields = {
            Stock.STORAGE_DAY,
            Stock.STORAGE_DATE,
            Stock.NEWEST_STORAGE_DATE,
            Stock.LOCATION_NO,
            Stock.RETRIEVAL_DAY,
            Stock.STOCK_QTY,
            Stock.ALLOCATION_QTY,
            Stock.PLAN_QTY,
            Stock.PALLET_ID
        };
        createAlterKey(newStock, akey, upFields);
        akey.updateLastUpdatePname(getCallerName());

        // set key field
        akey.setStockId(oldStock.getStockId());

        int modQty = 0;

        // update stock
        getStockHandler().modify(akey);

        // 作業区分がメンテ増、メンテ減の場合のみ実績送信情報を作成する。
        if (SystemDefine.JOB_TYPE_MAINTENANCE_PLUS.equals(jobType)
                || SystemDefine.JOB_TYPE_MAINTENANCE_MINUS.equals(jobType))
        {
            // 実績送信情報コントローラの生成
            HostSendController hsc = new HostSendController(getConnection(), this.getClass());
            hsc.insertByStock(oldStock, jobType, Math.abs(oldStock.getStockQty() - newStock.getStockQty()), ui);
        }

        if (!StringUtil.isBlank(incType))
        {
            // 在庫増減
            modQty = newStock.getStockQty() - oldStock.getStockQty();

            if (modQty != 0)
            {
                String area = oldStock.getAreaNo();
                String location = oldStock.getLocationNo();
                try
                {
                    LocateController locateController = getLocationCtlr();
                    if (0 > modQty)
                    {
                        locateController.decreaseStock(area, location);
                    }
                    else
                    {
                        locateController.increaseStock(area, location);
                    }
                }
                catch (NotFoundException e)
                {
                    // ignore not found.
                }

                // create history
                Stock hist = (Stock)oldStock.clone();
                for (FieldName fld : upFields)
                {
                    Object nv = newStock.getValue(fld);
                    if (null != nv)
                    {
                        hist.setValue(fld, nv);
                    }
                }
                insertHistory(hist, incType, jobType, Math.abs(modQty), true, ui, reasonType);

                // PART11 Packageなし又は、在庫履歴不要指定時、処理を行いません。
                if (P11Config.isPart11Log() && P11Config.isStockFlag())
                {
                    // create Part11history
                    insertPart11History(hist, incType, jobType, Math.abs(modQty), ui.getDfkUserInfo(), oldStock, null,
                            reasonType);
                }
            }
            else
            {
                if (ui.getDfkUserInfo() != null)
                {
                    // 在庫情報修正・削除の在庫数以外の変更時
                    if (DsNumberDefine.DS_STOCK_MNT.equals(ui.getDfkUserInfo().getDsNumber())
                            || DsNumberDefine.DS_SHELF_MNT.equals(ui.getDfkUserInfo().getDsNumber()))
                    {
                        // create history
                        Stock hist = (Stock)oldStock.clone();
                        // PART11 Packageなし又は、在庫履歴不要指定時、処理を行いません。
                        if (P11Config.isPart11Log() && P11Config.isStockFlag())
                        {
                            // create Part11history
                            insertPart11History(hist, incType, jobType, Math.abs(modQty), ui.getDfkUserInfo(),
                                    oldStock, newStock, reasonType);
                        }
                    }
                }
            }
        }
    }

    /**
     * 在庫情報を削除します。
     * 
     * @param delStock 削除対象項目
     *        <ol>
     *        キーは以下のとおりです。
     *        <li>在庫ID
     *        </ol>
     * 
     * @param incType 増減区分
     * @param jobType 作業タイプ
     * @param analysFlg 分析対象フラグ true:分析対象　false:分析対象外
     * @param ui ユーザ情報
     * 
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 削除対象在庫なし
     * @throws DataExistsException 在庫更新履歴登録済み
     * @throws NoPrimaryException マスタデータ重複
     */
    public void delete(Stock delStock, String incType, String jobType, boolean analysFlg, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        // 作業区分がメンテ増、メンテ減の場合のみ実績送信情報を作成する。
        if (SystemDefine.JOB_TYPE_MAINTENANCE_MINUS.equals(jobType))
        {
            // 在庫から実績送信情報を作成します。
            HostSendController hsc = new HostSendController(getConnection(), this.getClass());
            hsc.insertByStock(delStock, jobType, delStock.getStockQty(), ui);
        }
        StockSearchKey key = new StockSearchKey();

        // get stock first.
        key.setStockId(delStock.getStockId());

        Stock target = delStock;

        // drop stock.
        getStockHandler().drop(key);

        try
        {
            getLocationCtlr().decreaseStock(target.getAreaNo(), target.getLocationNo());
        }
        catch (NotFoundException e)
        {
            // ignore not found.
        }

        if (StringUtil.isBlank(incType))
        {
            // not create history if incType is null
            return;
        }

        Stock upStock = (Stock)target.clone();
        int qty = delStock.getStockQty();
        // set StockQty to ZERO (stock deleted)
        upStock.setStockQty(0);

        if (!JOB_TYPE_MOVEMENT.equals(jobType) && !JOB_TYPE_EMERGENCY_REPLENISHMENT.equals(jobType)
                && !JOB_TYPE_NORMAL_REPLENISHMENT.equals(jobType))
        {
            // set Last Retrieval Day to Current Work Day.
            upStock.setRetrievalDay(getWareNaviSystemCtlr().getWorkDay());
        }

        insertHistory(upStock, incType, jobType, qty, analysFlg, ui, SystemDefine.DEFAULT_REASON_TYPE);

        // PART11 Packageなし又は、在庫履歴不要指定時、処理を行いません。
        if (P11Config.isPart11Log() && P11Config.isStockFlag())
        {
            // create Part11history
            insertPart11History(upStock, incType, jobType, qty, ui.getDfkUserInfo(), delStock, null,
                    SystemDefine.DEFAULT_REASON_TYPE);
        }
    }

    /**
     * 在庫情報を削除します。<br>
     * 在庫更新履歴は分析対象で登録します。
     * 
     * @param delStock 削除対象項目
     *        <ol>
     *        キーは以下のとおりです。
     *        <li>在庫ID
     *        </ol>
     * 
     * @param incType 増減区分
     * @param jobType 作業タイプ
     * @param ui ユーザ情報
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 削除対象在庫なし
     * @throws DataExistsException 在庫更新履歴登録済み
     * @throws NoPrimaryException マスタデータ重複
     */
    public void delete(Stock delStock, String incType, String jobType, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        delete(delStock, incType, jobType, true, ui);
    }

    /**
     * 移動在庫の日時を取得します。
     * 
     * @param stock 対象在庫
     * @param comp 比較対象移動作業情報
     * @return 入庫日,入庫日時,最終出庫日 セット済み在庫 (stockのclone())
     * @throws ReadWriteException データベースエラー
     */
    public Stock getMoveStockDate(Stock stock, MoveWorkInfo comp)
            throws ReadWriteException
    {
        // storage day
        String rStorageDay = stock.getStorageDay();
        String cStorageDay = comp.getStorageDay();

        if (StringUtil.isBlank(rStorageDay) && StringUtil.isBlank(cStorageDay))
        {
            rStorageDay = getWareNaviSystemCtlr().getWorkDay(); // 通常は存在しない条件
        }
        else
        {
            rStorageDay = (String)max(rStorageDay, comp.getStorageDay());
        }

        // storage date
        Date rStorageDate = stock.getStorageDate();
        Date cStorageDate = comp.getStorageDate();

        if ((null == rStorageDate) && (null == cStorageDate))
        {
            rStorageDate = DbDateUtil.getTimeStamp(); // 通常は存在しない条件
        }
        else
        {
            rStorageDate = (Date)max(rStorageDate, comp.getStorageDate());
        }

        // retrieval day
        String rRetrDay = stock.getRetrievalDay();
        String cRetrDay = comp.getRetrievalDay();
        if (StringUtil.isBlank(rRetrDay) && StringUtil.isBlank(cRetrDay))
        {
            rRetrDay = null;
        }
        else
        {
            rRetrDay = (String)max(rRetrDay, cRetrDay);
        }

        // setup stock to return
        stock.setStorageDay(rStorageDay);
        stock.setStorageDate(rStorageDate);
        stock.setRetrievalDay(rRetrDay);

        return stock;
    }

    /**
     * 補充棚検索キーを取得します。<br>
     * パラメータの在庫情報エンティティのエリアに該当する、
     * 在庫数が1以上の在庫情報を検索するための検索キーを返します。<br>
     * 在庫情報エンティティの以下の項目がNULLの場合は検索条件とせず、
     * それ以外の場合は比較条件として検索キーに追加します。<br>
     * <ol>
     * <li>棚
     * <li>商品コード
     * <li>荷主コード
     * <li>ロットNo.
     * </ol>
     * 使用例)
     * 
     * <pre>
     * private void sample()
     * {
     *     // 棚の完全一致検索の場合
     *     Stock entity = new Stock();
     *     entity.setLocationNo(&quot;11111111&quot;);
     *     getRepLocationKey(entity, &quot;=&quot;);
     * 
     *     // 棚の比較検索の場合
     *     Stock entity = new Stock();
     *     entity.setLocationNo(&quot;11111111&quot;);
     *     getRepLocationKey(entity, &quot;&gt;=&quot;);
     * 
     *     // 棚のあいまい検索の場合
     *     Stock entity = new Stock();
     *     entity.setLocationNo(&quot;1111*&quot;);
     *     getRepLocationKey(entity, &quot;=&quot;);
     * }
     * </pre>
     * 
     * @param searchLocate 在庫情報エンティティ
     *        <ol>
     *        参照される項目は以下の通りです
     *        <li>エリア
     *        <li>棚
     *        <li>商品コード
     *        <li>荷主コード
     *        <li>ロットNo.
     *        </ol>
     * 
     * @param locateCompCode 棚比較条件(">=",etc.)<br>
     *        (LIKE検索の場合は棚に「<code>*</code>」を付与し、 「<code>=</code>」を使用してください)
     * @return 在庫情報検索キー
     */
    public StockSearchKey getRepLocationKey(Stock searchLocate, String locateCompCode)
    {
        // 在庫情報検索キー
        StockSearchKey repLocationKey = new StockSearchKey();

        // 検索条件の指定

        // エリア
        repLocationKey.setAreaNo(searchLocate.getAreaNo());
        // 棚が指定されている場合
        if (!StringUtil.isBlank(searchLocate.getLocationNo()))
        {
            repLocationKey.setLocationNo(searchLocate.getLocationNo(), locateCompCode);
        }
        // 商品コードが指定されている場合
        if (!StringUtil.isBlank(searchLocate.getItemCode()))
        {
            repLocationKey.setItemCode(searchLocate.getItemCode());
        }
        // 荷主コードが指定されている場合
        if (!StringUtil.isBlank(searchLocate.getConsignorCode()))
        {
            repLocationKey.setConsignorCode(searchLocate.getConsignorCode());
        }
        // ロットNo.が指定されている場合
        if (!StringUtil.isBlank(searchLocate.getLotNo()))
        {
            repLocationKey.setLotNo(searchLocate.getLotNo());
        }
        // 在庫数(1以上)
        repLocationKey.setStockQty(1, ">=");

        // ソート順の指定

        // エリア(昇順)
        repLocationKey.setAreaNoOrder(true);
        // 棚(昇順)
        repLocationKey.setLocationNoOrder(true);

        return repLocationKey;
    }

    /**
     * 新規入庫予約を行います。<br>
     * パラメータの在庫情報に対して入庫予約処理を行い、在庫IDを返します。
     * 
     * @param newStock 入庫情報
     * @param reasonType 作業理由区分
     * @return 在庫ID
     * @throws ReadWriteException
     *         データベース処理でエラーが発生した場合にスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     * @throws DataExistsException
     *         データが既に登録済みの場合にスローされます。
     * @throws NoPrimaryException
     *         一意の項目に対してデータが複数件存在した場合にスローされます。
     * @throws OperatorException
     *         オペレータ処理でエラーが発生した場合にスローされます。 <br>
     *         エラーコード
     *         <ul>
     *         <li>21 : オーバーフロー発生
     *         </ul>
     */
    public String initStorageReserve(Stock newStock, int reasonType)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException,
                OperatorException,
                NoPrimaryException
    {
        // 在庫パッケージが導入されていない場合は処理しない
        if (!getWareNaviSystemCtlr().hasStockPack())
        {
            return null;
        }

        // 在庫数量チェック
        if (newStock.getPlanQty() > WmsParam.MAX_STOCK_QTY || newStock.getPlanQty() < 0)
        {
            OperatorException e = new OperatorException(OperatorException.ERR_OVERFLOW);
            e.setDetail(Integer.valueOf(WmsParam.MAX_STOCK_QTY));
            throw e;
        }

        // 在庫情報登録
        return insert(newStock, null, null, null, reasonType);
    }

    /**
     * 積増入庫予約処理を行います。<br>
     * パラメータの在庫情報に対して積増入庫予約処理を行い、在庫IDを返す。
     * 
     * @param target 元在庫情報
     * @param newStock 入庫情報
     * @return 在庫ID
     * @throws ReadWriteException
     *         データベース処理でエラーが発生した場合にスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     * @throws DataExistsException
     *         データが既に登録済みの場合にスローされます。
     * @throws NoPrimaryException
     *         一意の項目に対してデータが複数件存在した場合にスローされます。
     * @throws OperatorException
     *         オペレータ処理でエラーが発生した場合にスローされます。 <br>
     *         エラーコード
     *         <ol>
     *         <li>21 : オーバーフロー発生
     *         </ol>
     */
    public String addStorageReserve(Stock target, Stock newStock)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException,
                OperatorException,
                NoPrimaryException
    {
        // 在庫パッケージが導入されていない場合は処理しない
        if (!getWareNaviSystemCtlr().hasStockPack())
        {
            return null;
        }

        int newPlanQty = target.getPlanQty() + newStock.getPlanQty();
        int chkPlanQty = newPlanQty + target.getStockQty();
        // 在庫数量チェック
        if (chkPlanQty > WmsParam.MAX_STOCK_QTY || chkPlanQty < 0)
        {
            OperatorException e = new OperatorException(OperatorException.ERR_OVERFLOW);
            e.setDetail(Integer.valueOf(WmsParam.MAX_STOCK_QTY - (target.getStockQty() + target.getPlanQty())));
            throw e;
        }

        Stock updStock = (Stock)newStock.clone();

        updStock.setPlanQty(newPlanQty); // 予定数

        // 在庫情報更新
        update(target, updStock, null, null, null, SystemDefine.DEFAULT_REASON_TYPE);

        return target.getStockId();
    }

    /**
     * 積み増し入庫を行う際に入庫日、入庫日時、最終出庫日の項目について
     * 積み増す在庫のものを採用するか、積み増される在庫のものを採用するかを決定し
     * その情報をセットしたStockインスタンスを取得します。
     *
     * @param source 比較対象のStockオブジェクト
     * @param dest 比較対象のStockオブジェクト
     * @return 比較後のStockオブジェクト
     * @throws ReadWriteException データベースエラー
     */
    public Stock getMoveStockDate(Stock source, Stock dest)
            throws ReadWriteException
    {
        Stock resultStock = new Stock();

        // storage day
        String rStorageDay = source.getStorageDay();
        String cStorageDay = dest.getStorageDay();

        if (StringUtil.isBlank(rStorageDay) && StringUtil.isBlank(cStorageDay))
        {
            rStorageDay = getWareNaviSystemCtlr().getWorkDay(); // 通常は存在しない条件
        }
        else
        {
            rStorageDay = (String)max(rStorageDay, dest.getStorageDay());
        }

        // storage date
        Date rStorageDate = source.getStorageDate();
        Date cStorageDate = dest.getStorageDate();

        if ((null == rStorageDate) && (null == cStorageDate))
        {
            rStorageDate = DbDateUtil.getTimeStamp(); // 通常は存在しない条件
        }
        else
        {
            rStorageDate = (Date)max(rStorageDate, dest.getStorageDate());
        }

        // retrieval day
        String rRetrDay = source.getRetrievalDay();
        String cRetrDay = dest.getRetrievalDay();
        if (StringUtil.isBlank(rRetrDay) && StringUtil.isBlank(cRetrDay))
        {
            rRetrDay = null;
        }
        else
        {
            rRetrDay = (String)max(rRetrDay, cRetrDay);
        }

        // setup stock to return
        resultStock.setStorageDay(rStorageDay);
        resultStock.setStorageDate(rStorageDate);
        resultStock.setRetrievalDay(rRetrDay);
        resultStock.setStockQty(source.getStockQty());

        return resultStock;
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
     * インスタンス生成時の初期化を行います。
     * 
     * @throws ScheduleException システム定義不整合
     * @throws ReadWriteException データベースエラー
     */
    protected void prepare()
            throws ReadWriteException,
                ScheduleException
    {
        Connection conn = getConnection();

        _seqHandler = new WMSSequenceHandler(conn);

        _palletHandler = new PalletHandler(conn);
        _shelfHandler = new ShelfHandler(conn);
        _stockHandler = new StockHandler(conn);
        _stockHistoryHandler = new StockHistoryHandler(conn);
        
        // PART11 Packageなし又は、在庫履歴不要指定時、生成を行いません。
        if (P11Config.isPart11Log() && P11Config.isStockFlag())
        {
            _part11StockHistoryHandler = new Part11StockHistoryHandler(conn);
        }

        _wareNaviSystemCtlr = new WarenaviSystemController(conn, getCaller());
        _areaCtlr = new AreaController(conn, getCaller());
        _locationCtlr = new LocateController(conn, getCaller());
    }

    /**
     * 在庫更新履歴情報登録処理を行います。
     * 
     * @param stock 登録対象在庫
     * @param incType 増減区分
     * @param jobType 作業タイプ
     * @param qty 在庫増減数
     * @param analysFlg 分析対象フラグ true:分析対象　false:分析対象外
     * @param ui ユーザ情報
     * @param reasonType 作業理由区分
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException
     *         在庫情報がnullのとき,エリアなし,荷主マスタ/商品マスタなし
     * @throws NoPrimaryException エリア重複
     * @throws DataExistsException 在庫更新履歴情報登録済み
     */
    protected void insertHistory(Stock stock, String incType, String jobType, int qty, boolean analysFlg,
            WmsUserInfo ui, int reasonType)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        if (null == stock)
        {
            throw new NotFoundException();
        }

        // getting area
        AreaSearchKey askey = new AreaSearchKey();
        askey.setAreaTypeCollect();
        askey.setTemporaryAreaTypeCollect();
        askey.setAreaNo(stock.getAreaNo());
        Area areaent = (Area)new AreaHandler(getConnection()).findPrimary(askey);

        // getting consignor
        ConsignorSearchKey cokey = new ConsignorSearchKey();
        cokey.setConsignorNameCollect();
        cokey.setConsignorCode(stock.getConsignorCode());
        Consignor consent = (Consignor)new ConsignorHandler(getConnection()).findPrimary(cokey);
        if (null == consent)
        {
            // 6026101={0}テーブルに({1})の詳細情報が見つかりません。
            Object[] mps = {
                Consignor.STORE_NAME,
                stock.getConsignorCode(),
            };
            RmiMsgLogClient.write(6026101, getCaller().getName(), mps);
            throw new NotFoundException();
        }

        // getting item
        ItemSearchKey itkey = new ItemSearchKey();
        itkey.setItemNameCollect();
        itkey.setJanCollect();
        itkey.setItfCollect();
        itkey.setBundleItfCollect();
        itkey.setEnteringQtyCollect();
        itkey.setBundleEnteringQtyCollect();

        itkey.setConsignorCode(stock.getConsignorCode());
        itkey.setItemCode(stock.getItemCode());

        Item itement = (Item)new ItemHandler(getConnection()).findPrimary(itkey);
        if (null == itement)
        {
            // 6026102={0}テーブルに({1},
            // {2})の詳細情報が見つかりません。
            Object[] mps = {
                Item.STORE_NAME,
                stock.getConsignorCode(),
                stock.getItemCode(),
            };
            RmiMsgLogClient.write(6026102, getCaller().getName(), mps);
            throw new NotFoundException();
        }

        Reason reasonent = new Reason();
        ReasonSearchKey rekey = new ReasonSearchKey();
        rekey.setReasonNameCollect();
        rekey.setReasonType(reasonType);

        reasonent = (Reason)new ReasonHandler(getConnection()).findPrimary(rekey);

        // create entity for insert
        StockHistory hist = new StockHistory();

        hist.setWorkDay(getWareNaviSystemCtlr().getWorkDay());

        hist.setIncDecType(incType);
        hist.setJobType(jobType);
        if (checkAnslysisType(incType, jobType, areaent) && analysFlg)
        {
            hist.setAnalysisType(StockHistory.ANALYSIS_TYPE_ANALYSIS);
        }
        else
        {
            hist.setAnalysisType(StockHistory.ANALYSIS_TYPE_NO_ANALYSIS);
        }
        hist.setUpdateStockQty(stock.getStockQty());
        hist.setIncDecQty(qty);

        copy(hist, stock, Stock.STOCK_ID);
        copy(hist, stock, Stock.AREA_NO);
        copy(hist, stock, Stock.LOCATION_NO);
        copy(hist, stock, Stock.LOT_NO);
        copy(hist, stock, Stock.STORAGE_DAY);
        copy(hist, stock, Stock.STORAGE_DATE);
        copy(hist, stock, Stock.NEWEST_STORAGE_DATE); 
        copy(hist, stock, Stock.RETRIEVAL_DAY);
        copy(hist, stock, Stock.CONSIGNOR_CODE);
        copy(hist, stock, Stock.ITEM_CODE);
        copy(hist, stock, Stock.PALLET_ID);

        copy(hist, areaent, Area.AREA_TYPE);
        copy(hist, consent, Consignor.CONSIGNOR_NAME);

        hist.setReasonType(reasonType);
        copy(hist, reasonent, Reason.REASON_NAME);

        copy(hist, itement, Item.ITEM_NAME);
        copy(hist, itement, Item.JAN);
        copy(hist, itement, Item.ITF);
        copy(hist, itement, Item.BUNDLE_ITF);
        copy(hist, itement, Item.ENTERING_QTY);
        copy(hist, itement, Item.BUNDLE_ENTERING_QTY);

        hist.setUserId(ui.getUserId());
        hist.setUserName(ui.getUserName());
        hist.setTerminalNo(ui.getTerminalNo());
        hist.setTerminalName(ui.getTerminalName());
        hist.setIpAddress(ui.getTerminalAddress());

        hist.setRegistPname(getCallerName());
        hist.setRegistDate(new SysDate());

        getStockHistoryHandler().create(hist);
    }

    /**
     * Part11用在庫更新履歴情報登録処理を行います。
     * 
     * @param stock 登録対象在庫
     * @param incType 増減区分
     * @param jobType 作業タイプ
     * @param qty 在庫増減数
     * @param ui ユーザ情報
     * @param bstock 更新前在庫
     * @param updateStock 更新対象在庫（在庫数変化なしの場合のみ使用）
     * @param reasonType 作業理由区分
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException
     *         在庫情報がnullのとき,エリアなし,荷主マスタ/商品マスタなし
     * @throws NoPrimaryException エリア重複
     * @throws DataExistsException 在庫更新履歴情報登録済み
     * 
     */
    protected void insertPart11History(Stock stock, String incType, String jobType, int qty, DfkUserInfo ui,
            Stock bstock, Stock updateStock, int reasonType)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        DsNumberDefine ds = new DsNumberDefine();

        // RFTの場合は、処理を行わず復帰します。

        if (ds.fromRft(this.getCaller()))
        {
            return;
        }

        if (null == stock)
        {
            throw new NotFoundException();
        }

        // getting area
        AreaSearchKey askey = new AreaSearchKey();
        askey.setAreaTypeCollect();
        askey.setTemporaryAreaTypeCollect();
        askey.setAreaNameCollect();
        askey.setAreaNo(stock.getAreaNo());
        Area areaent = (Area)new AreaHandler(getConnection()).findPrimary(askey);

        // getting consignor
        ConsignorSearchKey cokey = new ConsignorSearchKey();
        cokey.setConsignorNameCollect();
        cokey.setConsignorCode(stock.getConsignorCode());
        Consignor consent = (Consignor)new ConsignorHandler(getConnection()).findPrimary(cokey);
        if (null == consent)
        {
            // 6026101={0}テーブルに({1})の詳細情報が見つかりません。
            Object[] mps = {
                Consignor.STORE_NAME,
                stock.getConsignorCode(),
            };
            RmiMsgLogClient.write(6026101, getCaller().getName(), mps);
            throw new NotFoundException();
        }

        // getting item
        ItemSearchKey itkey = new ItemSearchKey();
        itkey.setItemNameCollect();
        itkey.setJanCollect();
        itkey.setItfCollect();
        itkey.setBundleItfCollect();
        itkey.setEnteringQtyCollect();
        itkey.setBundleEnteringQtyCollect();

        itkey.setConsignorCode(stock.getConsignorCode());
        itkey.setItemCode(stock.getItemCode());

        Item itement = (Item)new ItemHandler(getConnection()).findPrimary(itkey);
        if (null == itement)
        {
            // 6026102={0}テーブルに({1},
            // {2})の詳細情報が見つかりません。
            Object[] mps = {
                Item.STORE_NAME,
                stock.getConsignorCode(),
                stock.getItemCode(),
            };
            RmiMsgLogClient.write(6026102, getCaller().getName(), mps);
            throw new NotFoundException();
        }

        Reason reasonent = new Reason();
        ReasonSearchKey rekey = new ReasonSearchKey();
        rekey.setReasonNameCollect();
        rekey.setReasonType(reasonType);

        reasonent = (Reason)new ReasonHandler(getConnection()).findPrimary(rekey);

        Part11StockHistory hist = new Part11StockHistory();

        hist.setReasonType(reasonType);
        copy(hist, reasonent, Reason.REASON_NAME);;

        hist.setWorkDay(getWareNaviSystemCtlr().getWorkDay());
        hist.setJobType(jobType);
        copy(hist, stock, Stock.STOCK_ID);
        copy(hist, stock, Stock.AREA_NO);
        copy(hist, areaent, Area.AREA_NAME);
        copy(hist, stock, Stock.LOCATION_NO);
        copy(hist, areaent, Area.AREA_TYPE);
        copy(hist, stock, Stock.CONSIGNOR_CODE);
        copy(hist, consent, Consignor.CONSIGNOR_NAME);
        copy(hist, stock, Stock.ITEM_CODE);
        copy(hist, itement, Item.ITEM_NAME);
        copy(hist, itement, Item.JAN);
        copy(hist, itement, Item.ITF);
        copy(hist, itement, Item.BUNDLE_ITF);
        copy(hist, itement, Item.ENTERING_QTY);
        copy(hist, itement, Item.BUNDLE_ENTERING_QTY);
        copy(hist, stock, Stock.LOT_NO);
        hist.setUpdateStockQty(stock.getStockQty());
        hist.setIncDecQty(qty);

        // 在庫数の変化がない場合
        if (qty == 0 && updateStock != null)
        {
            if (null == updateStock)
            {
                // 在庫数
                hist.setStockQty(bstock.getStockQty());
                // 入庫日時
                hist.setStorageDate(bstock.getStorageDate());
                // 出庫日
                hist.setRetrievalDay(bstock.getRetrievalDay());
            }
            else
            {
                hist.setStockQty(stock.getStockQty());
                if (!bstock.getStorageDate().equals(updateStock.getStorageDate()))
                {
                    // 入庫日時
                    hist.setStorageDate(bstock.getStorageDate());
                    // 更新後入庫日時
                    hist.setUpdateStorageDate(updateStock.getStorageDate());
                    // 出庫日
                    hist.setRetrievalDay(bstock.getRetrievalDay());
                    // 更新後出庫日
                    hist.setUpdateRetrievalDay(bstock.getRetrievalDay());
                }
                else if (!bstock.getRetrievalDay().equals(updateStock.getRetrievalDay()))
                {
                    // 入庫日時
                    hist.setStorageDate(bstock.getStorageDate());
                    // 更新後入庫日時
                    hist.setUpdateStorageDate(bstock.getStorageDate());
                    // 出庫日
                    hist.setRetrievalDay(bstock.getRetrievalDay());
                    // 更新後出庫日
                    hist.setUpdateRetrievalDay(updateStock.getRetrievalDay());
                }
            }
        }
        // 新規在庫の場合
        else if (null == bstock)
        {
            hist.setStorageDate(stock.getStorageDate());
            hist.setRetrievalDay(stock.getRetrievalDay());

            hist.setUpdateStorageDate(stock.getStorageDate());
            hist.setUpdateRetrievalDay(stock.getRetrievalDay());
        }
        // 在庫更新の場合
        else if (stock.getStockQty() != 0)
        {
            // 在庫数
            copy(hist, bstock, Stock.STOCK_QTY);

            // 入庫日時
            hist.setStorageDate(bstock.getStorageDate());
            // 更新後入庫日時
            hist.setUpdateStorageDate(stock.getStorageDate());
            // 出庫日
            hist.setRetrievalDay(bstock.getRetrievalDay());
            // 更新後出庫日
            hist.setUpdateRetrievalDay(stock.getRetrievalDay());
        }
        // 在庫削除の場合
        else
        {
            // 在庫数
            hist.setStockQty(bstock.getStockQty());
            // 入庫日時
            hist.setStorageDate(bstock.getStorageDate());
            // 出庫日
            hist.setRetrievalDay(bstock.getRetrievalDay());
        }

        copy(hist, stock, Stock.PALLET_ID);
        // 棚フォーマット
        AreaController areacon = new AreaController(getConnection(), getClass());
        hist.setLocationStyle(areacon.getLocationStyle(stock.getAreaNo()));

        // MC-AGC通信からの呼び出し
        if (ds.fromAGC(this.getCaller()))
        {
            // ユーザID
            hist.setUserId(WmsParam.SYS_USER_ID);
            // ユーザ名称
            hist.setUserName(WmsParam.SYS_USER_NAME);
            // 端末No
            hist.setTerminalNo(WmsParam.SYS_TERMINAL_NO);
            // 端末名称
            hist.setTerminalName(WmsParam.SYS_TERMINAL_NAME);
            // 端末IPアドレス
            hist.setIpAddress(WmsParam.SYS_IP_ADDRESS);
            // ID番号よりDS番号、リソース番号を取得
            ds.setInfo(this.getCaller());
            // DS番号
            hist.setDsNo(ds.getDsNum());
            // リソース番号
            hist.setPagenameResourcekey(ds.getPageResouceKey());
        }
        // ユーザID
        else if (ui != null)
        {
            // ユーザID
            hist.setUserId(ui.getUserId());
            // ユーザ名称
            hist.setUserName(ui.getUserName());
            // 端末No
            hist.setTerminalNo(ui.getTerminalNumber());
            // 端末名称
            hist.setTerminalName(ui.getTerminalName());
            // 端末IPアドレス
            hist.setIpAddress(ui.getTerminalAddress());
            // DS番号
            hist.setDsNo(ui.getDsNumber());
            // リソース番号
            hist.setPagenameResourcekey(ui.getPageNameResourceKey());
        }
        // ASRS Web画面からの呼び出し
        else if (ds.fromAsWork(this.getCaller()))
        {
            // ユーザID
            hist.setUserId(WmsParam.SYS_USER_ID);
            // ユーザ名称
            hist.setUserName(WmsParam.SYS_USER_NAME);
            // 端末No
            hist.setTerminalNo(WmsParam.SYS_TERMINAL_NO);
            // 端末名称
            hist.setTerminalName(WmsParam.SYS_TERMINAL_NAME);
            // 端末IPアドレス
            hist.setIpAddress(WmsParam.SYS_IP_ADDRESS);
            // ID番号よりDS番号、リソース番号を取得
            ds.setInfo(this.getCaller());
            // DS番号
            hist.setDsNo(ds.getDsNum());
            // リソース番号
            hist.setPagenameResourcekey(ds.getPageResouceKey());
        }
        // デフォルトユーザID
        else
        {
            // ユーザID
            hist.setUserId(WmsParam.SYS_USER_ID);
            // ユーザ名称
            hist.setUserName(WmsParam.SYS_USER_NAME);
            // 端末No
            hist.setTerminalNo(WmsParam.SYS_TERMINAL_NO);
            // 端末名称
            hist.setTerminalName(WmsParam.SYS_TERMINAL_NAME);
            // 端末IPアドレス
            hist.setIpAddress(WmsParam.SYS_IP_ADDRESS);
            // DS番号
            hist.setDsNo(WmsParam.SYS_DS_NUMBER);
            // リソース番号
            hist.setPagenameResourcekey(WmsParam.SYS_PAGE_RESOUCE_KEY);
        }
        // 登録処理名
        hist.setRegistPname(getCallerName());

        getPart11StockHistoryHandler().create(hist);

    }

    /**
     * 出庫在庫更新(欠品数)処理を行います。
     * 
     * @param retrieval 出庫完了データ<br>
     *        在庫数が欠品数と同じで、予定数が0のとき、在庫が削除されます。
     * @param jobType 作業区分
     * @param shortage 欠品数
     * @param workDay 作業日
     * @param ui ユーザ情報
     * @return 在庫を削除したとき true
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 更新対象在庫なし
     * @throws DataExistsException 在庫更新履歴登録済み
     * @throws NoPrimaryException マスタデータ重複
     */
    protected boolean stockUpdateShortage(Stock retrieval, String jobType, int shortage, String workDay, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        // update stock
        Stock newValue = new Stock();

        newValue.setAllocationQty(retrieval.getAllocationQty() + shortage);

        update(retrieval, newValue, null, jobType, ui, SystemDefine.DEFAULT_REASON_TYPE);

        return false;
    }

    /**
     * 出庫在庫更新(出庫数)処理を行います。
     * 
     * @param retrieval 出庫完了データ<br>
     *        在庫数が出庫数と同じで、予定数が0のとき、在庫が削除されます。
     * @param jobType 作業区分
     * @param qty 出庫数
     * @param workDay 作業日
     * @param alloc 引き当てを行うときtrue<br>
     *        出庫可能数が更新されます。
     * @param ui ユーザ情報
     * @return 在庫を削除したとき true
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 更新対象在庫なし
     * @throws DataExistsException 在庫更新履歴登録済み
     * @throws NoPrimaryException マスタデータ重複
     */
    public boolean stockUpdateRetrieval(Stock retrieval, String jobType, int qty, String workDay, boolean alloc,
            WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        // check REMOVE or UPDATE
        if ((qty == retrieval.getStockQty()) && (0 == retrieval.getPlanQty()))
        {
            // remove stock
            delete(retrieval, INC_DEC_TYPE_STOCK_DECREMENT, jobType, ui);
            return true;
        }
        else
        {
            // update stock
            Stock newValue = new Stock();

            newValue.setStockQty(retrieval.getStockQty() - qty);

            if (!JOB_TYPE_MOVEMENT.equals(jobType) && !JOB_TYPE_EMERGENCY_REPLENISHMENT.equals(jobType)
                    && !JOB_TYPE_NORMAL_REPLENISHMENT.equals(jobType))
            {
                newValue.setRetrievalDay(workDay);
            }

            if (alloc)
            {
                // update allocation if allocation flag is true.
                newValue.setAllocationQty(retrieval.getAllocationQty() - qty);
            }

            update(retrieval, newValue, INC_DEC_TYPE_STOCK_DECREMENT, jobType, ui, SystemDefine.DEFAULT_REASON_TYPE);
        }
        return false;
    }

    /**
     * 分析対象チェック処理を行います。<br>
     * センター内在庫移動は分析対象外とします。
     * 
     * @param incType 増減区分
     * @param jobType 作業タイプ
     * @param area エリア情報
     * @return 分析対象データの場合true、分析対象外の場合はfalse
     */
    protected boolean checkAnslysisType(String incType, String jobType, Area area)
    {
        // 作業区分がセンター内の移動実績は分析対象外
        if (StockHistory.JOB_TYPE_MOVEMENT.equals(jobType)
                || StockHistory.JOB_TYPE_NORMAL_REPLENISHMENT.equals(jobType)
                || StockHistory.JOB_TYPE_EMERGENCY_REPLENISHMENT.equals(jobType)
                || StockHistory.JOB_TYPE_DIRECT_TRAVEL.equals(jobType)
                || StockHistory.JOB_TYPE_ASRS_RACK_TO_RACK.equals(jobType)
                || StockHistory.JOB_TYPE_ASRS_REARRANGE.equals(jobType))
        {
            return false;
        }

        // 出庫、予定外出庫、強制払い出し、搬送データ削除は仮置エリア移動を対象外とする
        if (StockHistory.JOB_TYPE_RETRIEVAL.equals(jobType) || StockHistory.JOB_TYPE_NOPLAN_RETRIEVAL.equals(jobType)
                || StockHistory.JOB_TYPE_ASRS_EXPENDITURE.equals(jobType)
                || StockHistory.JOB_TYPE_ASRS_CARRYINFODELETE.equals(jobType))
        {
            // 仮置在庫を作成するエリアの場合
            if (Area.TEMPORARY_AREA_TYPE_ALL.equals(area.getTemporaryAreaType()))
            {
                return false;
            }

            // 仮置エリアかつ在庫加算（入庫）の場合
            if (Area.AREA_TYPE_TEMPORARY.equals(area.getAreaType())
                    && (StockHistory.INC_DEC_TYPE_STOCK_INCREMENT.equals(incType)))
            {
                return false;
            }
        }

        return true;
    }

    // ------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // utility methods
    // ------------------------------------------------------------

    /**
     * Entity間でフィールドの内容をコピーします。<br>
     * フィールドの内容がnullのときは何も行いません。
     * 
     * @param tgt コピー先
     * @param src コピー元
     * @param name コピーするフィールド
     */
    protected void copy(Entity tgt, Entity src, FieldName name)
    {
        if (null == src)
        {
            return;
        }
        Object val = src.getValue(name);
        if (val != null)
        {
            tgt.setValue(name, val);
        }
    }

    /**
     * areaCtlrを返します。
     * 
     * @return areaCtlrを返します。
     */
    protected AreaController getAreaCtlr()
    {
        return _areaCtlr;
    }

    /**
     * locationCtlrを返します。
     * 
     * @return locationCtlrを返します。
     */
    protected LocateController getLocationCtlr()
    {
        return _locationCtlr;
    }

    /**
     * palletHandlerを返します。
     * 
     * @return palletHandlerを返します。
     */
    protected PalletHandler getPalletHandler()
    {
        return _palletHandler;
    }

    /**
     * seqHandlerを返します。
     * 
     * @return seqHandlerを返します。
     */
    protected WMSSequenceHandler getSeqHandler()
    {
        return _seqHandler;
    }

    /**
     * shelfHandlerを返します。
     * 
     * @return shelfHandlerを返します。
     */
    protected ShelfHandler getShelfHandler()
    {
        return _shelfHandler;
    }

    /**
     * stockHandlerを返します。
     * 
     * @return stockHandlerを返します。
     */
    protected StockHandler getStockHandler()
    {
        return _stockHandler;
    }

    /**
     * wareNaviSystemCtlrを返します。
     * 
     * @return wareNaviSystemCtlrを返します。
     */
    protected WarenaviSystemController getWareNaviSystemCtlr()
    {
        return _wareNaviSystemCtlr;
    }

    /**
     * stockHistoryHandlerを返します。
     * 
     * @return stockHistoryHandlerを返します。
     */
    protected StockHistoryHandler getStockHistoryHandler()
    {
        return _stockHistoryHandler;
    }

    /**
     * part11StockHistoryHandlerを返します。
     * @return part11StockHistoryHandlerを返します。
     */
    protected Part11StockHistoryHandler getPart11StockHistoryHandler()
    {
        return _part11StockHistoryHandler;
    }

    /**
     * オブジェクトの比較を行います。
     * 
     * @param o1 比較対象1
     * @param o2 比較対照2
     * @return 大きいほうのオブジェクト
     */
    @SuppressWarnings("unchecked")
    protected static synchronized Object max(Comparable o1, Comparable o2)
    {
        if (null != o1 && null != o2)
        {
            Object bigger = (0 < o1.compareTo(o2)) ? o1
                                                  : o2;
            return bigger;
        }
        else if (null == o1 && null == o2)
        {
            return null;
        }
        else if (null == o1 && null != o2)
        {
            return o2;
        }
        else if (null != o1 && null == o2)
        {
            return o1;
        }
        return null;
    }

    /**
     * このクラスのリビジョンを返します。
     * 
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: StockController.java 7876 2010-04-27 04:00:37Z kishimoto $";
    }

}
