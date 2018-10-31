// $Id: MoveOperator.java 719 2008-10-27 08:39:55Z rnakai $
package jp.co.daifuku.wms.stock.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of
 * DAIFUKU Co.,Ltd. Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.MoveWorkInfoController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.stock.schedule.StockInParameter;
import jp.co.daifuku.wms.stock.schedule.StockOutParameter;

/**
 * 在庫移動作業のオペレータクラスです。
 * 
 * <!-- !!注意!!
 * 
 * このクラスでは、インスタンスに保持しているハンドラ・コントローラの 生成を必要時に行うようにしています。
 * したがって、このクラスのコンストラクタを呼び出した直後には
 * ハンドラ・コントローラのインスタンスは初期化されておらず、アクセッサ
 * を使用してハンドラ・コントローラを取得した段階でインスタンス化が
 * 行われるため、かならずアクセッサを通してハンドラ・コントローラを 取得・使用するようにしてください。
 * -->
 * 
 * @version $Revision: 719 $, $Date: 2008-10-27 17:39:55 +0900 (月, 27 10 2008) $
 * @author ss
 * @author Last commit: $Author: rnakai $
 */
// UPDATE_SS (2007-07-06)
public class MoveOperator
        extends AbstractOperator
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
    // instance variables (prefix '_')
    // ------------------------------------------------------------
    // ----------- WARNING -----------
    // DO 'NOT' USE HANDLER/CONTROLLER DIRECTLY.
    // PLEASE USE getXXX() METHOD TO GET THESE
    // HANSLER/CONTROLLER.
    // ----------- WARNING -----------
    private WMSSequenceHandler _seqHandler;

    private WarenaviSystemController _wmsSysCtlr;

    private AreaController _areaCtlr;

    private StockController _stockCtlr;

    private MoveWorkInfoController _moveWorkCtlr;

    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public MoveOperator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * 在庫移動入出庫作業完了処理。
     * 
     * @param params 完了処理パラメータ<br>
     *        <li>荷主コード
     *        <li>商品コード
     *        <li>ロットNo.
     *        <li>移動元エリア
     *        <li>移動元棚No.
     *        <li>移動数
     *        <li>移動先エリア
     *        <li>移動先棚No.
     *        <li>ユーザ情報 (ハードウエア除く)
     * @return 設定単位キー
     * @throws ReadWriteException データベースエラー
     * @throws ScheduleException Warenavisystemテーブルに不整合
     * @throws LockTimeOutException ロックタイムアウト
     * @throws OperatorException 処理が続行できないとき理由が通知されます
     */
    public StockOutParameter webComplete(StockInParameter[] params)
            throws ReadWriteException,
                ScheduleException,
                LockTimeOutException,
                OperatorException
    {
        StockOutParameter rparam = new StockOutParameter();

        // 設定単位キー取得 (= return value)
        String settingUkey = getSeqHandler().nextMoveSetUkey();
        rparam.setSettingUnitKey(settingUkey);

        // 現行作業日取得
        String workday = getWmsSysCtlr().getWorkDay();

        // Prepare Controllers
        StockController sctlr = getStockCtlr();
        MoveWorkInfoController mwictlr = getMoveWorkCtlr();

        // setting User Info
        WmsUserInfo ui = params[0].getWmsUserInfo();
        ui.setHardwareType(SystemDefine.HARDWARE_TYPE_LIST);

        // JOBTYPE (移動作業)
        String jobType = SystemDefine.JOB_TYPE_MOVEMENT;

        // Loop for parameters
        for (int i = 0; i < params.length; i++)
        {
            try
            {
                StockInParameter param = params[i];

                // get parameter values
                String consignorCode = param.getConsignorCode();
                String itemCode = param.getItemCode();
                String lotNo = param.getLotNo();

                String destAreaNo = param.getDestAreaNo();
                String destLocation = param.getDestLocation();
                String sourceAreaNo = param.getSourceAreaNo();
                String sourceLocation = param.getSourceLocation();

                int resQty = (int)param.getMovementQty();

                // retrieval processing -------------
                // locking Stock
                Stock retKeyStock = new Stock();
                retKeyStock.setAreaNo(sourceAreaNo); // SOURCE AREA
                retKeyStock.setLocationNo(sourceLocation); // SOURCE LOCATION
                retKeyStock.setConsignorCode(consignorCode); // CONSIGNOR
                retKeyStock.setItemCode(itemCode); // ITEM
                retKeyStock.setLotNo(lotNo); // LOT

                Stock[] retrStocks = sctlr.lock(retKeyStock);
                // no target stock found
                if (ArrayUtil.isEmpty(retrStocks))
                {
                    // catched on try..catch block.
                    throw new NotFoundException();
                }

                // check allocation qty
                Stock retrStock = retrStocks[0];

                if (resQty > retrStock.getAllocationQty())
                {
                    OperatorException ex = new OperatorException(OperatorException.ERR_SHORTAGE_ALLOCATION_QTY);
                    ex.setErrorLineNo(i + 1);
                    ex.setDetail(String.valueOf(retrStock.getAllocationQty()));
                    throw ex;
                }
                sctlr.retrieval(retrStock, jobType, resQty, 0, workday, true, ui);

                // stock processing -----------------
                // locking Stock
                Stock storStock = new Stock();
                storStock.setAreaNo(destAreaNo); // DEST AREA
                storStock.setLocationNo(destLocation); // DEST LOCATION
                storStock.setConsignorCode(consignorCode); // CONSIGNOR
                storStock.setItemCode(itemCode); // ITEM
                storStock.setLotNo(lotNo); // LOT

                Stock[] stStocks = sctlr.lock(storStock);

                // target stock found?
                boolean initStorage = (ArrayUtil.isEmpty(stStocks));

                // set new value
                Stock newStock = (Stock)retrStock.clone();

                newStock.setAreaNo(destAreaNo); // DEST AREA
                newStock.setLocationNo(destLocation); // DEST LOCATION
                newStock.setConsignorCode(consignorCode); // CONSIGNOR
                newStock.setItemCode(itemCode); // ITEM
                newStock.setLotNo(lotNo); // LOT

                newStock.setStockQty(resQty); // NEW QTY = MOVEMENT QTY
                newStock.setAllocationQty(resQty); // ALL QTY NOT ALLOCATED

                if (!StringUtil.isBlank(retrStock.getRetrievalDay()))
                {
                    newStock.setRetrievalDay(retrStock.getRetrievalDay());
                }

                String stockID = "";
                if (initStorage)
                {
                    stockID = sctlr.initStorage(newStock, jobType, ui, SystemDefine.DEFAULT_REASON_TYPE);
                }
                else
                {
                    // too many target found
                    if (1 != stStocks.length)
                    {
                        throw new NoPrimaryException();
                    }

                    Stock toAddStock = stStocks[0];
                    // get move date
                    MoveWorkInfo mwi = new MoveWorkInfo();
                    mwi.setStorageDay(toAddStock.getStorageDay());
                    mwi.setStorageDate(toAddStock.getStorageDate());
                    mwi.setRetrievalDay(toAddStock.getRetrievalDay());
                    // Align DATEs
                    newStock = sctlr.getMoveStockDate(newStock, mwi);

                    // add newStock
                    stockID =
                            sctlr.addStorage(toAddStock, newStock, jobType, false, ui, SystemDefine.DEFAULT_REASON_TYPE);
                }
                newStock.setStockId(stockID);
                // move work completion
                mwictlr.complete(retrStock, newStock, settingUkey, jobType, resQty, ui);
            }
            catch (OperatorException e)
            {
                e.setErrorLineNo(i + 1);
                throw e;
            }
            catch (NotFoundException e)
            {
                OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                ex.setErrorLineNo(i + 1);
                throw ex;
            }
            catch (DataExistsException e)
            {
                OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                ex.setErrorLineNo(i + 1);
                throw ex;
            }
            catch (NoPrimaryException e)
            {
                // master data duplicated
                throw new ScheduleException();
            }
        } // end for loop
        return rparam;
    }

    /**
     * RFT在庫移動出庫作業完了処理。<br>
     * 移動出庫作業を完了し、移動中エリアに在庫を移します。
     * 
     * @param param 設定パラメータ<br>
     *        <li>荷主コード
     *        <li>商品コード
     *        <li>ロットNo.
     *        <li>移動元エリア
     *        <li>移動元棚No.
     *        <li>移動数
     *        <li>ユーザ情報 (ハードウエア除く)
     * @return String S
     * @throws ScheduleException システム定義に不整合がある
     * @throws ReadWriteException データベースエラー
     * @throws OperatorException
     *         対象データが更新されている/出庫数不足/ロットNoが単一でない
     * @throws LockTimeOutException ロックタイムアウト
     * @throws NoPrimaryException 出庫または入庫先在庫が複数存在した
     */
    public String rftCompleteRetrieval(StockInParameter param)
            throws ReadWriteException,
                ScheduleException,
                OperatorException,
                LockTimeOutException,
                NoPrimaryException
    {
        // 現作業日取得
        String workday = getWmsSysCtlr().getWorkDay();

        // prepare controllers
        StockController sctlr = getStockCtlr();
        MoveWorkInfoController mwictlr = getMoveWorkCtlr();
        AreaController areaCtlr = getAreaCtlr();

        // fix hardware type
        WmsUserInfo ui = param.getWmsUserInfo();
        ui.setHardwareType(SystemDefine.HARDWARE_TYPE_RFT);

        String jobType = SystemDefine.JOB_TYPE_MOVEMENT;

        // get common information from parameter
        String consignorCode = param.getConsignorCode();
        String itemCode = param.getItemCode();
        String lotNo = param.getLotNo();
        String sourceAreaNo = param.getSourceAreaNo();
        String sourceLocation = param.getSourceLocation();
        int resQty = (int)param.getMovementQty();
        int second = param.getWorkSeconds();

        try
        {
            // retrieval processing -------------
            // locking Stock
            Stock retKeyStock = new Stock();
            retKeyStock.setAreaNo(sourceAreaNo); // SOURCE AREA
            if (!StringUtil.isBlank(sourceLocation))
            {
                retKeyStock.setLocationNo(sourceLocation); // SOURCE LOCATION    
            }

            retKeyStock.setConsignorCode(consignorCode); // CONSIGNOR
            retKeyStock.setItemCode(itemCode); // ITEM
            retKeyStock.setLotNo(lotNo); // LOT

            Stock[] retrStocks = sctlr.lock(retKeyStock);
            // no target stock found
            if (ArrayUtil.isEmpty(retrStocks))
            {
                // catched on try..catch block.
                throw new NotFoundException();
            }

            // check duplicate (it throws when duplicated)
            checkRetrievalDuplicate(retrStocks);

            // check allocation qty
            Stock retrStock = retrStocks[0];

            if (resQty > retrStock.getAllocationQty())
            {
                OperatorException ex = new OperatorException(OperatorException.ERR_SHORTAGE_ALLOCATION_QTY);
                ex.setDetail(String.valueOf(retrStock.getAllocationQty()));
                throw ex;
            }

            // RETR. DAY IS NOT updated on Stock controller, if job type is movement.
            sctlr.retrieval(retrStock, jobType, resQty, 0, workday, true, ui);

            // stock processing -----------------
            // get moving area
            String moveArea = areaCtlr.getMovingArea();
            String moveLoc = WmsParam.DEFAULT_LOCATION_NO;

            // locking Stock
            Stock storStock = new Stock();
            storStock.setAreaNo(moveArea); // DEST AREA
            storStock.setLocationNo(moveLoc); // DEST LOCATION
            storStock.setConsignorCode(consignorCode); // CONSIGNOR
            storStock.setItemCode(itemCode); // ITEM
            storStock.setLotNo(lotNo); // LOT

            Stock[] stStocks = sctlr.lock(storStock);

            // target stock found?
            boolean initStorage = (ArrayUtil.isEmpty(stStocks));

            // set new value
            Stock newStock = (Stock)retrStock.clone();
            newStock.setAreaNo(moveArea); // NEW AREA
            newStock.setLocationNo(moveLoc); // NEW LOCATION
            newStock.setConsignorCode(consignorCode); // CONSIGNOR
            newStock.setItemCode(itemCode); // ITEM
            newStock.setLotNo(lotNo); // LOT

            newStock.setStockQty(resQty); // NEW STOCK QTY
            // all stock allcated for move storage
            boolean allFlag = true;
            if (param.getAllocateQty() == 0)
            {
                newStock.setAllocationQty(0); // ALL QTY ALLOCATED for move storage
            }
            else
            {
                allFlag = false;
                newStock.setAllocationQty((int)param.getAllocateQty());
            }
            String stockID = "";
            if (initStorage)
            {
                stockID = sctlr.initStorage(newStock, jobType, ui, 0);
            }
            else
            {
                // too many target found
                if (1 != stStocks.length)
                {
                    throw new NoPrimaryException();
                }

                Stock toAddStock = stStocks[0];
                // get move date
                MoveWorkInfo mwi = new MoveWorkInfo();
                mwi.setStorageDay(toAddStock.getStorageDay());
                mwi.setStorageDate(toAddStock.getStorageDate());
                mwi.setRetrievalDay(toAddStock.getRetrievalDay());

                newStock = sctlr.getMoveStockDate(newStock, mwi);

                stockID =
                        sctlr.addStorage(toAddStock, newStock, jobType, allFlag, ui, SystemDefine.DEFAULT_REASON_TYPE);
            }

            // complete move retrieval work
            return mwictlr.completeRetrieval(retrStock, jobType, stockID, resQty, second, ui, param.getReceivingFlag());
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        catch (DataExistsException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * RFT在庫移動入庫開始処理。
     * 
     * @param param 設定パラメータ<br>
     *        <li>荷主コード
     *        <li>商品コード
     *        <li>作業No.
     *        <li>ユーザ情報 (ハードウエア除く)
     * 
     * @return 設定単位キー
     * @throws ReadWriteException データベースエラー
     * @throws LockTimeOutException 移動作業情報ロックタイムアウト
     * @throws NotFoundException 対象移動作業なし
     * @throws OperatorException 対象作業情報に続行できないデータがある
     */
    public StockOutParameter rftStartStorage(StockInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                OperatorException
    {
        // prepare controllers
        MoveWorkInfoController mwictlr = getMoveWorkCtlr();
        ItemController itemCtlr = new ItemController(getConnection(), getCaller());

        // get setting unit key and set return parameter
        StockOutParameter rparam = new StockOutParameter();

        String settingUKey = getSeqHandler().nextMoveSetUkey();
        rparam.setSettingUnitKey(settingUKey);

        // get parameter values
        String consignor = param.getConsignorCode();
        String item = param.getItemCode();
        String jobNo = param.getJobNo();
        String jobType = SystemDefine.JOB_TYPE_MOVEMENT;

        // fix hardware type
        WmsUserInfo ui = param.getWmsUserInfo();
        ui.setHardwareType(SystemDefine.HARDWARE_TYPE_RFT);
        List<String> keyItemList = new ArrayList<String>();

        // loop by max scan code
        boolean workFound = false;
        MoveWorkInfo[] mworks = null;
        for (int i = 0; i < ItemController.MAX_SCAN_INDEX; i++)
        {
            // use item code if scan index is 0.
            String keyItem = (i == 0) ? item
                                     : itemCtlr.getItemCode(param.getConsignorCode(), item, i);

            // item not found, then try next.
            if (StringUtil.isBlank(keyItem))
            {
                continue;
            }
            keyItemList.add(keyItem);

            // try to lock with current item code lock to start
            mworks = mwictlr.lockStorageStart(consignor, keyItem, jobNo, jobType);
            workFound = !ArrayUtil.isEmpty(mworks);
            if (workFound)
            {
                // found work info
                break;
            }
        }

        if (workFound)
        {
            // check duplicate (JobNo) it throws Exception, if duplicated.
            checkStorageDuplicate(mworks);

            String startJobNo = mworks[0].getJobNo();

            // start work for move storage
            try
            {
                mwictlr.start(startJobNo, settingUKey, SystemDefine.STATUS_FLAG_MOVE_STORAGE_WORKING, ui);
            }
            catch (InvalidDefineException e)
            {
                // not occurs.
            }
            return rparam;
        }
        else
        {
            // check cause of no work found.
            String[] items = keyItemList.toArray(new String[keyItemList.size()]);
            mwictlr.checkStorageStart(consignor, items, jobNo, jobType);
        }

        return null;
    }

    /**
     * 在庫移動入庫作業完了処理。
     * 
     * @param param パラメータ<br>
     *        <li>作業No.
     *        <li>作業秒数
     *        <li>移動数
     *        <li>移動先エリアNo.
     *        <li>移動先棚No.
     *        <li>ユーザ情報 <!-- RFT -->
     * @throws LockTimeOutException 移動作業情報のロックタイムアウト
     * @throws ReadWriteException データベースエラー
     * @throws OperatorException
     *         対象作業情報に続行できないデータがある/すでに他の端末で更新済み/出庫可能数不足
     * @throws ScheduleException システム定義/マスタに不整合あり
     * @throws NoPrimaryException 対象在庫が複数存在する
     */
    public void completeStorage(StockInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                ScheduleException,
                NoPrimaryException
    {
        // prepare controllers
        MoveWorkInfoController mwictlr = getMoveWorkCtlr();

        // get parameters
        String jobNo = param.getJobNo();
        int worksec = param.getWorkSeconds();
        int qty = (int)param.getMovementQty();
        String destArea = param.getDestAreaNo();
        String destLocation = param.getDestLocation();

        WmsUserInfo ui = param.getWmsUserInfo();
        String hwType = ui.getHardwareType();

        boolean rft = (SystemDefine.HARDWARE_TYPE_RFT.equals(hwType));
        String statusFlag = (rft) ? SystemDefine.STATUS_FLAG_MOVE_STORAGE_WORKING
                                 : SystemDefine.STATUS_FLAG_MOVE_STORAGE_WAITING;
        try
        {
            MoveWorkInfo work = mwictlr.lockCompleteJob(jobNo, statusFlag);

            String stockID = moveFromMoveArea(work, destArea, destLocation, qty, ui);
            String workday = getWmsSysCtlr().getWorkDay();

            try
            {
                mwictlr.completeStorage(work, stockID, destArea, destLocation, qty, workday, worksec, ui);
            }
            catch (NoPrimaryException e)
            {
                throw new ScheduleException();
            }
        }
        catch (DataExistsException e)
        {
            // 分割作業情報登録済み
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        catch (NotFoundException e)
        {
            // 対象作業情報なし
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 入庫作業中キャンセル処理。
     * 
     * @param param パラメータ<br>
     *        <li>作業No.
     *        <li>ユーザ情報
     * 
     * @throws NoPrimaryException 対象作業/在庫が複数存在する
     * @throws LockTimeOutException 移動作業ロックタイムアウト
     * @throws ReadWriteException データベースエラー
     * @throws OperatorException 他端末で更新済み
     */
    public void cancel(StockInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                OperatorException
    {
        MoveWorkInfoController mwictlr = getMoveWorkCtlr();

        // get parameter values
        String jobNo = param.getJobNo();
        WmsUserInfo ui = param.getWmsUserInfo();

        try
        {
            MoveWorkInfo work = mwictlr.lockCompleteJob(jobNo, SystemDefine.STATUS_FLAG_MOVE_STORAGE_WORKING);

            mwictlr.completeStorage(work, null, null, null, 0, null, 0, ui);
        }
        catch (NotFoundException e)
        {
            // 対象データなし
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        catch (DataExistsException e)
        {
            // 分割作業情報登録済み
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 入庫キャンセル処理。
     * 
     * @param param パラメータ<br>
     *        <li>作業No.
     *        <li>ユーザ情報
     * @throws NoPrimaryException 対象作業/在庫が複数存在する
     * @throws LockTimeOutException 作業情報のロックタイムアウト
     * @throws ReadWriteException データベースエラー
     * @throws OperatorException 他端末で更新済み
     * @throws ScheduleException システム定義に不整合あり
     */
    public void cancelStorage(StockInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                OperatorException,
                ScheduleException
    {
        MoveWorkInfoController mwictlr = getMoveWorkCtlr();

        String jobNo = param.getJobNo();
        WmsUserInfo ui = param.getWmsUserInfo();

        try
        {
            MoveWorkInfo work = mwictlr.lockCompleteJob(jobNo, SystemDefine.STATUS_FLAG_MOVE_STORAGE_WAITING);

            String area = work.getRetrievalAreaNo();
            String location = work.getRetrievalLocationNo();
            int qty = work.getRetrievalResultQty();

            moveFromMoveArea(work, area, location, qty, ui);

            mwictlr.cancelStorage(jobNo);
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        catch (DataExistsException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
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
     * 移動中エリアからの在庫移動を行います。
     * 
     * @param work 移動対象作業
     * @param area 作業エリア
     * @param location 対象棚
     * @param resQty 移動数
     * @param ui ユーザ情報
     * @return 移動後在庫ID
     * @throws ScheduleException システム定義異常
     * @throws ReadWriteException データベースアクセスエラー
     * @throws LockTimeOutException 在庫ロックタイムアウト
     * @throws OperatorException 処理が続行できない場合にスローされます
     *         (出庫可能数不足,更新済み)
     * @throws NoPrimaryException 対象在庫が特定できない
     */
    protected String moveFromMoveArea(MoveWorkInfo work, String area, String location, int resQty, WmsUserInfo ui)
            throws ScheduleException,
                ReadWriteException,
                LockTimeOutException,
                OperatorException,
                NoPrimaryException
    {
        try
        {
            StockController stockCtlr = getStockCtlr();
            String movearea = getAreaCtlr().getMovingArea();

            // MoveWorkInfo work = works[0];
            String consignor = work.getConsignorCode();
            String item = work.getItemCode();
            String lot = work.getLotNo();

            // lock target stock
            Stock keyStock = new Stock();
            keyStock.setAreaNo(movearea);
            keyStock.setLocationNo(WmsParam.DEFAULT_LOCATION_NO);
            keyStock.setConsignorCode(consignor);
            keyStock.setItemCode(item);
            keyStock.setLotNo(lot);

            Stock[] targets = stockCtlr.lock(keyStock);
            if (ArrayUtil.isEmpty(targets))
            {
                throw new NotFoundException();
            }

            Stock target = targets[0];
            // check stock qty
            if (target.getStockQty() < resQty)
            {
                OperatorException ex = new OperatorException(OperatorException.ERR_SHORTAGE_ALLOCATION_QTY);
                ex.setDetail(String.valueOf(target.getStockQty()));
                throw ex;
            }
            boolean allFlag = false;
            if (target.getAllocationQty() != 0)
            {
                allFlag = true;
            }

            String jobType = SystemDefine.JOB_TYPE_MOVEMENT;

            stockCtlr.retrieval(target, jobType, resQty, 0, null, allFlag, ui);

            // ---------------------------
            // lock dest stock
            keyStock = new Stock();
            keyStock.setAreaNo(area);
            keyStock.setLocationNo(location);
            keyStock.setConsignorCode(consignor);
            keyStock.setItemCode(item);
            keyStock.setLotNo(lot);

            Stock[] destStocks = stockCtlr.lock(keyStock);

            // target stock found?
            boolean initStorage = (ArrayUtil.isEmpty(destStocks));

            // set new value
            Stock newStock = (Stock)target.clone();
            newStock.setAreaNo(area);
            newStock.setLocationNo(location);
            newStock.setConsignorCode(consignor);
            newStock.setItemCode(item);
            newStock.setLotNo(lot);

            newStock.setStockQty(resQty);
            newStock.setAllocationQty(resQty); // ALL QTY NOT ALLOCATED

            String stockID = "";
            if (initStorage)
            {
                stockID = stockCtlr.initStorage(newStock, jobType, ui, 0);
            }
            else
            {
                // too many target found
                if (1 != destStocks.length)
                {
                    throw new NoPrimaryException();
                }

                Stock toAddStock = destStocks[0];
                // get move date
                MoveWorkInfo mwi = new MoveWorkInfo();
                mwi.setStorageDay(toAddStock.getStorageDay());
                mwi.setStorageDate(toAddStock.getStorageDate());
                mwi.setRetrievalDay(toAddStock.getRetrievalDay());

                newStock = stockCtlr.getMoveStockDate(newStock, mwi);

                stockID =
                        stockCtlr.addStorage(toAddStock, newStock, jobType, false, ui, SystemDefine.DEFAULT_REASON_TYPE);
            }
            return stockID;
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
        catch (DataExistsException e)
        {
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 移動出庫用重複チェックを行います。<br>
     * チェック対象フィールドは、ロットNo.です。
     * 
     * @param stocks チェック対象在庫
     * @throws DuplicateOperatorException
     *         重複項目があったときスローされます。
     */
    protected void checkRetrievalDuplicate(Stock[] stocks)
            throws DuplicateOperatorException
    {
        String errCode = OperatorException.ERR_LOT_NO_DUPLICATED;
        checkDuplicate(stocks, Stock.LOT_NO, errCode);
    }

    /**
     * 移動入庫用重複チェックを行います。<br>
     * チェック対象フィールドは、作業No.です。
     * 
     * @param works 重複確認対象作業
     * @throws DuplicateOperatorException
     *         重複項目があったときスローされます。
     */
    protected void checkStorageDuplicate(MoveWorkInfo[] works)
            throws DuplicateOperatorException
    {
        String errCode = OperatorException.ERR_JOB_NO_DUPLICATED;
        checkDuplicate(works, MoveWorkInfo.JOB_NO, errCode);
    }

    /**
     * エンティティのフィールドがすべて同じであるかチェックします。
     * 
     * @param targets チェック対象エンティティ
     * @param fld チェック対象フィールド
     * @param errCode OperatorExceptionにセットするエラーコード
     * @throws DuplicateOperatorException
     *         すべて同じでない場合にスローされます。
     */
    @SuppressWarnings("unchecked")
    protected void checkDuplicate(Entity[] targets, FieldName fld, String errCode)
            throws DuplicateOperatorException
    {
        Set uSet = new LinkedHashSet(targets.length);
        for (Entity work : targets)
        {
            uSet.add(work.getValue(fld));
        }
        if (1 < uSet.size())
        {
            DuplicateOperatorException ex = new DuplicateOperatorException(errCode);
            ex.setDetail(new ArrayList(uSet));
            throw ex;
        }
    }

    // ------------------------------------------------------------
    // accessors for Handler/Controller
    // ------------------------------------------------------------

    /**
     * @return WMSSequenceHandler
     */
    protected WMSSequenceHandler getSeqHandler()
    {
        if (null == _seqHandler)
        {
            _seqHandler = new WMSSequenceHandler(getConnection());
        }
        return _seqHandler;
    }

    /**
     * @return WarenaviSystemController
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException システム定義異常
     */
    protected WarenaviSystemController getWmsSysCtlr()
            throws ReadWriteException,
                ScheduleException
    {
        if (null == _wmsSysCtlr)
        {
            _wmsSysCtlr = new WarenaviSystemController(getConnection(), getCaller());
        }
        return _wmsSysCtlr;
    }

    /**
     * @return AreaController
     * @throws ReadWriteException データベースアクセスエラー
     */
    protected AreaController getAreaCtlr()
            throws ReadWriteException
    {
        if (null == _areaCtlr)
        {
            _areaCtlr = new AreaController(getConnection(), getCaller());
        }
        return _areaCtlr;
    }

    /**
     * @return StockController
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException システム定義異常
     */
    protected StockController getStockCtlr()
            throws ReadWriteException,
                ScheduleException
    {
        if (null == _stockCtlr)
        {
            _stockCtlr = new StockController(getConnection(), getCaller());
        }
        return _stockCtlr;
    }

    /**
     * @return MoveWorkInfoController
     */
    protected MoveWorkInfoController getMoveWorkCtlr()
    {
        if (null == _moveWorkCtlr)
        {
            _moveWorkCtlr = new MoveWorkInfoController(getConnection(), getCaller());
        }
        return _moveWorkCtlr;
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
        return "$Id: MoveOperator.java 719 2008-10-27 08:39:55Z rnakai $";
    }
}
