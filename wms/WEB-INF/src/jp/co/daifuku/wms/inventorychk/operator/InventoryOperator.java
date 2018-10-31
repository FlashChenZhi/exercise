// $Id: InventoryOperator.java 846 2008-10-28 08:52:58Z ota $
package jp.co.daifuku.wms.inventorychk.operator;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.InventSettingController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventResultHandler;
import jp.co.daifuku.wms.base.dbhandler.InventSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.InventSettingSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.InventResult;
import jp.co.daifuku.wms.base.entity.InventSetting;
import jp.co.daifuku.wms.base.entity.InventWorkInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;
import jp.co.daifuku.wms.inventorychk.schedule.InventoryInParameter;

/**
 * 棚卸開始、キャンセル、結果確定のためのオペレータクラスです。<BR>
 * <BR>
 * @version $Revision: 846 $, $Date: 2008-10-28 17:52:58 +0900 (火, 28 10 2008) $
 * @author  taki
 * @author  Last commit: $Author: ota $
 */
public class InventoryOperator
        extends AbstractOperator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

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
     */
    public InventoryOperator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 棚卸開始処理を行います。
     * 
     * @param param 棚卸開始条件パラメータ
     * @throws ReadWriteException   データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException   １件以上該当しないはずの処理で複数件該当した場合にスローされます。
     *                                  (エリアマスタに同じエリアNoのデータが複数該当した場合)
     * @throws InvalidStatusException 棚卸対象エリア外(平置き又は仮置きエリア以外)のときスローされます。
     * @throws OperatorException    エラーコード"42"(棚卸範囲重複)＝既に棚卸開始している範囲と重複している場合にスローされます。
     * @throws DataExistsException  すでに登録済みであったときスローされます。
     * @throws NotFoundException    更新対象が見つからない場合にスローされます。
     * @throws ScheduleException    DNWarenaviSystemに整合性がないときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     */
    public void createInventoryWorkInfo(InventoryInParameter param)
            throws ReadWriteException,
                NoPrimaryException,
                InvalidStatusException,
                OperatorException,
                DataExistsException,
                NotFoundException,
                ScheduleException,
                LockTimeOutException
    {
        /* エリアのチェック */
        AreaSearchKey key = new AreaSearchKey();
        key.setAreaNo(param.getAreaNo());
        String[] str = {
                SystemDefine.AREA_TYPE_FLOOR,
                SystemDefine.AREA_TYPE_TEMPORARY,
                SystemDefine.AREA_TYPE_RECEIVING
        };
        key.setAreaType(str, false);
        Area area = (Area)new AreaHandler(getConnection()).findPrimary(key);
        if (null == area)
        {
            throw new InvalidStatusException();
        }

        /* InventSettingControllerを使って、棚卸設定情報に追加する。発生した例外はそのままスローする */
        InventSetting iSet = new InventSetting();
        iSet.setConsignorCode(param.getConsignorCode());
        iSet.setAreaNo(param.getAreaNo());
        iSet.setFromLocationNo(param.getLocationNo());
        iSet.setToLocationNo(param.getLocationNoTo());
        InventSettingController iSetController = new InventSettingController(getConnection(), getCaller());
        String schNo = iSetController.insertInventSetting(iSet);

        /* マスタ管理なしの場合、荷主マスタに登録する */
        WarenaviSystemController wsc = new WarenaviSystemController(getConnection(), getCaller());
        boolean hasMaster = wsc.hasMasterPack();
        if (!hasMaster)
        {
            ConsignorHandler conHdl = new ConsignorHandler(getConnection());
            ConsignorSearchKey conKey = new ConsignorSearchKey();
            conKey.setConsignorCode(param.getConsignorCode());
            // 荷主マスタにデータがない場合のみ登録する
            if (conHdl.count(conKey) == 0)
            {
                ConsignorController cc = new ConsignorController(getConnection(), getCaller());
                Consignor newCons = new Consignor();
                newCons.setConsignorCode(param.getConsignorCode());
                newCons.setConsignorName(param.getConsignorName());
                cc.autoCreate(newCons, param.getWmsUserInfo());
            }
        }

        /* 必要なハンドラー生成 */
        // 棚卸作業情報ハンドラー
        InventWorkInfoHandler inventHandler = new InventWorkInfoHandler(getConnection());
        // シーケンスハンドラー
        WMSSequenceHandler wSeqHandler = new WMSSequenceHandler(getConnection());

        /* 在庫データから、棚卸作業情報を作成 */
        // 検索条件の指定
        StockSearchKey sskey = new StockSearchKey();
        sskey.setConsignorCode(param.getConsignorCode());
        sskey.setAreaNo(param.getAreaNo());
        sskey.setLocationNo(param.getLocationNo(), ">=");
        sskey.setLocationNo(param.getLocationNoTo(), "<=");
        sskey.setStockQty(0, ">");
        // 取得項目の指定
        sskey.setConsignorCodeCollect();
        sskey.setAreaNoCollect();
        sskey.setLocationNoCollect();
        sskey.setItemCodeCollect();
        sskey.setLotNoCollect();
        sskey.setStockQtyCollect();
        sskey.setAllocationQtyCollect();
        // 在庫情報、棚卸作業のFinder定義
        StockFinder stockFinder = new StockFinder(getConnection());
        InventWorkInfoFinder inventFinder = new InventWorkInfoFinder(getConnection());

        // 検索結果を取得
        try
        {
            stockFinder.open(true);
            if (stockFinder.search(sskey) > 0)
            {
                while (stockFinder.hasNext())
                {
                    // 検索結果を100件ずつ取得する
                    Stock[] stock = (Stock[])stockFinder.getEntities(100);

                    for (int i = 0; i < stock.length; i++)
                    {
                        /* 棚卸作業情報にデータ追加 */
                        // 作業No.採番
                        String jobNo = wSeqHandler.nextInventJobNo();

                        // 棚卸作業情報エンティティにデータをセット
                        InventWorkInfo inventEntity = new InventWorkInfo();
                        inventEntity.setJobNo(jobNo);
                        inventEntity.setScheduleNo(schNo);
                        inventEntity.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
                        inventEntity.setConsignorCode(stock[i].getConsignorCode());
                        inventEntity.setAreaNo(stock[i].getAreaNo());
                        inventEntity.setLocationNo(stock[i].getLocationNo());
                        inventEntity.setItemCode(stock[i].getItemCode());
                        inventEntity.setLotNo(stock[i].getLotNo());
                        inventEntity.setStockQty(stock[i].getStockQty());
                        inventEntity.setAllocationQty(stock[i].getAllocationQty());
                        inventEntity.setRegistPname(getCallerName());
                        inventEntity.setLastUpdatePname(getCallerName());
                        // 棚卸作業情報登録
                        inventHandler.create(inventEntity);
                    }
                }
            }

            /* 空棚データから、棚卸作業情報を作成 */
            // 検索条件の指定
            LocateSearchKey lskey = new LocateSearchKey();
            lskey.setAreaNo(param.getAreaNo());
            lskey.setLocationNo(param.getLocationNo(), ">=");
            lskey.setLocationNo(param.getLocationNoTo(), "<=");
            lskey.setStatusFlag(SystemDefine.LOCATION_STATUS_FLAG_EMPTY);
            // 取得項目の指定
            lskey.setAreaNoCollect();
            lskey.setLocationNoCollect();
            // 棚マスタ情報のHANDLER定義
            LocateHandler locateHandler = new LocateHandler(getConnection());
            // 検索結果を取得
            Locate[] locate = (Locate[])locateHandler.find(lskey);

            for (Locate inventItem : locate)
            {
                /* 棚卸作業情報にデータ追加 */
                // 作業No.採番
                String jobNo = wSeqHandler.nextInventJobNo();

                // 棚卸作業情報エンティティにデータをセット
                InventWorkInfo inventEntity = new InventWorkInfo();
                inventEntity.setJobNo(jobNo);
                inventEntity.setScheduleNo(schNo);
                inventEntity.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
                inventEntity.setConsignorCode(param.getConsignorCode());
                inventEntity.setAreaNo(inventItem.getAreaNo());
                inventEntity.setLocationNo(inventItem.getLocationNo());
                inventEntity.setRegistPname(getCallerName());
                inventEntity.setLastUpdatePname(getCallerName());
                // 棚卸作業情報登録
                inventHandler.create(inventEntity);
            }

            /* リスト作業No(設定単位キー)のセット */
            // 検索条件の指定
            InventWorkInfoSearchKey iskey = new InventWorkInfoSearchKey();
            iskey.setScheduleNo(schNo);
            // 取得項目の指定
            iskey.setJobNoCollect();
            // 取得順序の指定
            iskey.setAreaNoOrder(true);
            iskey.setLocationNoOrder(true);
            iskey.setItemCodeOrder(true);
            iskey.setLotNoOrder(true);
            // 検索結果を取得

            inventFinder.open(true);

            if (inventFinder.search(iskey) > 0)
            {
                while (inventFinder.hasNext())
                {
                    // 検索結果を100件ずつ取得する
                    InventWorkInfo[] inventWorkInfo = (InventWorkInfo[])inventFinder.getEntities(100);

                    // リストの最大行数取得(この数の単位で、リスト作業Noを採番する)
                    int maxLineQty = WmsParam.INVENTORYCHKLIST_MAX_ROW;
                    if (maxLineQty <= 0)
                    {
                        maxLineQty = 1;
                    }
                    // リスト作業No 保持変数
                    String setUkey = "";
                    for (int i = 0; i < inventWorkInfo.length; i++)
                    {
                        // リスト作業No(設定単位キー)採番
                        if (0 == i % maxLineQty)
                        {
                            setUkey = wSeqHandler.nextInventSetUkey();
                        }
                        // 棚卸作業情報更新キー
                        InventWorkInfoAlterKey aKey = new InventWorkInfoAlterKey();
                        // 検索条件の指定
                        aKey.setJobNo(inventWorkInfo[i].getJobNo());
                        // 更新項目の指定
                        aKey.updateSettingUnitKey(setUkey);
                        aKey.updateLastUpdatePname(getCallerName());
                        // 棚卸作業情報更新
                        inventHandler.modify(aKey);
                    }
                }
            }
        }
        finally
        {
            stockFinder.close();
            inventFinder.close();
        }
    }

    /**
     * 棚卸キャンセル処理を行います。
     * 
     * @param params キャンセル対象パラメータ
     * 
     * @throws OperatorException    キャンセル処理に失敗したとき詳細が返されます。
     * @throws ReadWriteException   データベースアクセスエラーが発生したときにスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws NotFoundException    更新や削除で対象データが見つからない場合にスローされます。(ロックしているので通常考えられません。)
     */
    public void deleteInventoryWorkInfo(InventoryInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                NotFoundException
    {
        // 棚卸設定情報ハンドラー
        InventSettingHandler isHandler = new InventSettingHandler(getConnection());

        /* 対象をロックする */
        for (InventoryInParameter param : params)
        {
            // 棚卸設定情報検索キー
            InventSettingSearchKey isKey = new InventSettingSearchKey();
            // スケジュールNo.
            isKey.setScheduleNo(param.getScheduleNo());
            // 最終更新日時
            isKey.setLastUpdateDate(param.getLastUpdateDate());

            // 棚卸設定情報と棚卸作業情報結合
            isKey.setJoin(InventSetting.SCHEDULE_NO, "", InventWorkInfo.SCHEDULE_NO, "(+)");
            // ステータスフラグ
            isKey.setCollect(InventWorkInfo.STATUS_FLAG);

            // 棚卸設定情報ロック
            InventSetting[] inventSetting =
                    (InventSetting[])isHandler.findForUpdate(isKey, HandlerSysDefines.WAIT_SEC_NOWAIT);

            // 棚卸設定情報が他で更新された場合
            if (ArrayUtil.isEmpty(inventSetting))
            {
                OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                ex.setErrorLineNo(param.getRowNo());
                throw ex;
            }

            /* 作業中が含まれていた場合は、OperatorException をスローする。 */
            for (InventSetting invSet : inventSetting)
            {
                if (SystemDefine.STATUS_FLAG_INVENTORY_WORKING.equals(invSet.getValue(InventWorkInfo.STATUS_FLAG)))
                {
                    OperatorException ex = new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
                    ex.setErrorLineNo(param.getRowNo());
                    throw ex;
                }
            }
        }

        // 棚卸作業情報ハンドラー
        InventWorkInfoHandler iwHandler = new InventWorkInfoHandler(getConnection());

        /* 削除処理 */
        for (InventoryInParameter param : params)
        {
            /* 棚卸作業情報を削除 */
            // 棚卸作業情報検索キー
            InventWorkInfoSearchKey iwKey = new InventWorkInfoSearchKey();
            iwKey.setScheduleNo(param.getScheduleNo());
            // 棚卸作業情報削除
            try
            {
                iwHandler.drop(iwKey);
            }
            catch (NotFoundException e)
            {
                //在庫も、棚も登録されていない範囲を棚卸開始した場合は、作業情報が存在しない。この場合は該当無しでも問題ない。
            }

            /* 棚卸設定情報を削除に更新 */
            InventSettingAlterKey iaKey = new InventSettingAlterKey();
            iaKey.setScheduleNo(param.getScheduleNo());
            iaKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
            iaKey.updateLastUpdatePname(getCallerName());
            isHandler.modify(iaKey);
        }
    }

    /**
     * 棚卸結果確定処理を行います。
     * 
     * @param params 完了パラメータ
     * <ol>
     * 以下の項目が参照されます
     * <li>作業No
     * <li>荷主コード
     * <li>エリアNo
     * <li>棚
     * <li>商品コード
     * <li>ロットNo
     * <li>棚卸ケース数
     * <li>棚卸ピース数
     * <li>ケース入数
     * <li>在庫数(棚卸結果確定画面の、"現在庫数")
     * <li>ユーザ情報
     * </ol>
     * 
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws OperatorException 他でデータが更新されたときにスローされます。(エラーコード10:他端末で更新済み)
     * @throws DataExistsException 集約作業完了時に作業情報が登録済みのときスローされます。
     * @throws NoPrimaryException 集約作業完了時に対象データが1件でないときスローされます。
     * @throws NotFoundException 対象作業データなし(他端末で更新された)
     * @throws ScheduleException システム定義不整合
     */
    public void complete(InventoryInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException,
                ScheduleException
    {
        // 棚卸設定情報ハンドラー
        InventSettingHandler isHandler = new InventSettingHandler(getConnection());
        // 棚卸作業情報ハンドラー
        InventWorkInfoHandler iwHandler = new InventWorkInfoHandler(getConnection());
        // 棚卸実績情報ハンドラー
        InventResultHandler irHandler = new InventResultHandler(getConnection());
        // 在庫情報コントローラ
        StockController stockCtr = new StockController(getConnection(), getClass());

        // システム定義情報コントローラ
        WarenaviSystemController wsc = new WarenaviSystemController(getConnection(), getClass());

        // 棚卸確定日時
        Date confirmWorkDate = DbDateUtil.getTimeStamp();

        /********************/
        /* 棚卸結果確定処理 */
        /********************/
        for (InventoryInParameter compParam : params)
        {
            /* 作業Noをキーに、棚卸作業情報をロック */
            InventWorkInfoSearchKey iwsKey = new InventWorkInfoSearchKey();
            iwsKey.setJobNo(compParam.getJobNo());
            iwsKey.setStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_WORKING_COMPLETED);
            InventWorkInfo invWorkInfo =
                    (InventWorkInfo)iwHandler.findPrimaryForUpdate(iwsKey, HandlerSysDefines.WAIT_SEC_NOWAIT);
            if (null == invWorkInfo)
            {
                // 対象の棚卸作業情報が見つからない場合(他端末で更新された場合)
                OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                ex.setErrorLineNo(compParam.getRowNo());
                throw ex;
            }

            /* -------------------------- */
            /* 在庫の更新(新規追加、削除) */
            /* -------------------------- */
            if (wsc.hasStockPack() && !StringUtil.isBlank(compParam.getItemCode()))
            {
                // 棚卸実績数
                long resultQty =
                        compParam.getResultCaseQty() * compParam.getEnteringQty() + compParam.getResultPieceQty();

                if (resultQty != invWorkInfo.getResultStockQty())
                {
                    // パラメータの棚卸数と、棚卸作業情報ＤＢの棚卸数を比較し、異なる場合は例外
                    OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                    ex.setErrorLineNo(compParam.getRowNo());
                    throw ex;
                }
                // 在庫情報のロック
                Stock stock = new Stock();
                stock.setAreaNo(compParam.getAreaNo());
                stock.setLocationNo(compParam.getLocationNo());
                stock.setConsignorCode(compParam.getConsignorCode());
                stock.setItemCode(compParam.getItemCode());
                stock.setLotNo(compParam.getLotNo());
                Stock[] tgtStocks = stockCtr.lock(stock);

                if (0 >= tgtStocks.length)
                {
                    // 在庫開始時は在庫があったのに棚卸確定の時にはなくなっている場合は例外
                    if (compParam.getStockQty() > 0)
                    {
                        // パラメータの在庫数と、在庫情報ＤＢの在庫数を比較し、異なる場合は例外
                        OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                        ex.setErrorLineNo(compParam.getRowNo());
                        throw ex;
                    }

                    if (0 < resultQty)
                    {
                        // 新規在庫作成
                        stock.setStorageDay(wsc.getWorkDay());
                        stock.setStorageDate((DbDateUtil.getTimeStamp()));
                        stock.setStockQty((int)resultQty);
                        stock.setAllocationQty((int)resultQty);
                        stockCtr.insert(stock, SystemDefine.INC_DEC_TYPE_STOCK_INCREMENT,
                                SystemDefine.JOB_TYPE_INVENTORY_PLUS, compParam.getWmsUserInfo(),
                                SystemDefine.DEFAULT_REASON_TYPE);
                    }
                }
                else if (1 < tgtStocks.length)
                {
                    // ユニークになる項目で検索しているので複数該当する事は無いはず
                    throw new ScheduleException();
                }
                else
                {
                    Stock tgtStock = tgtStocks[0];
                    if (compParam.getStockQty() != tgtStock.getStockQty())
                    {
                        // パラメータの在庫数と、在庫情報ＤＢの在庫数を比較し、異なる場合は例外
                        OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                        ex.setErrorLineNo(compParam.getRowNo());
                        throw ex;
                    }
                    // 棚卸数と在庫数が異なる場合、在庫数、引当可能数を更新する
                    if (resultQty != tgtStock.getStockQty())
                    {
                        long newAllocateQty = resultQty - (tgtStock.getStockQty() - tgtStock.getAllocationQty());
                        if (0 > newAllocateQty)
                        {
                            // 引当可能数がマイナス値になってしまうような場合
                            OperatorException ex = new OperatorException(OperatorException.ERR_SHORTAGE_ALLOCATION_QTY);
                            ex.setErrorLineNo(compParam.getRowNo());
                            throw ex;
                        }
                        // 在庫数が0になる場合は、在庫を削除する
                        if (0 == resultQty)
                        {
                            stock.setStockId(tgtStock.getStockId());
                            stockCtr.delete(stock, SystemDefine.INC_DEC_TYPE_STOCK_DECREMENT,
                                    SystemDefine.JOB_TYPE_INVENTORY_MINUS, compParam.getWmsUserInfo());
                        }
                        // 在庫数が1以上の場合は、在庫を更新する
                        else
                        {
                            String incType;
                            String jobType;
                            if (resultQty > tgtStock.getStockQty())
                            {
                                incType = SystemDefine.INC_DEC_TYPE_STOCK_INCREMENT;
                                jobType = SystemDefine.JOB_TYPE_INVENTORY_PLUS;
                            }
                            else
                            {
                                incType = SystemDefine.INC_DEC_TYPE_STOCK_DECREMENT;
                                jobType = SystemDefine.JOB_TYPE_INVENTORY_MINUS;
                            }
                            stock.setStockQty((int)resultQty);
                            stock.setAllocationQty((int)newAllocateQty);
                            stockCtr.update(tgtStock, stock, incType, jobType, compParam.getWmsUserInfo(),
                                    SystemDefine.DEFAULT_REASON_TYPE);
                        }
                    }
                }
            }

            /* ------------------ */
            /* 棚卸作業情報の更新 */
            /* ------------------ */
            InventWorkInfoAlterKey aKey = new InventWorkInfoAlterKey();
            aKey.setJobNo(invWorkInfo.getJobNo());
            aKey.updateStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_ALREADY_COMPLETED);
            aKey.updateConfirmUserId(compParam.getUserId());
            aKey.updateConfirmWorkDay(wsc.getWorkDay());
            aKey.updateConfirmWorkDate(confirmWorkDate);
            aKey.updateLastUpdatePname(getClass().getSimpleName());
            iwHandler.modify(aKey);

            /* ------------------ */
            /* 棚卸実績情報の作成 */
            /* ------------------ */
            // 必要な項目（荷主名称など）の取得
            iwsKey = createInventWorkInfoSearchKey(compParam.getJobNo(), compParam.getItemCode());

            // 棚卸作業情報を検索
            invWorkInfo = (InventWorkInfo)iwHandler.findPrimary(iwsKey);
            if (invWorkInfo == null)
            {
                // 作業Noで検索しているので、必ずデータ取得できるはず。
                // (取得できないのは、DBに対して何かしたか、SearchKeyの組立ミス。)            
                throw new ScheduleException();
            }
            // 棚卸実績情報エンティティ
            InventResult invResult = new InventResult();
            invResult.setWorkDay(invWorkInfo.getConfirmWorkDay());
            invResult.setJobNo(invWorkInfo.getJobNo());
            invResult.setScheduleNo(invWorkInfo.getScheduleNo());
            invResult.setSettingUnitKey(invWorkInfo.getSettingUnitKey());
            invResult.setStatusFlag(invWorkInfo.getStatusFlag());
            invResult.setAreaNo(invWorkInfo.getAreaNo());
            invResult.setLocationNo(invWorkInfo.getLocationNo());
            invResult.setConsignorCode(invWorkInfo.getConsignorCode());
            invResult.setConsignorName((String)invWorkInfo.getValue(Consignor.CONSIGNOR_NAME));
            invResult.setItemCode(invWorkInfo.getItemCode());
            invResult.setItemName((String)invWorkInfo.getValue(Item.ITEM_NAME));
            invResult.setJan((String)invWorkInfo.getValue(Item.JAN));
            invResult.setItf((String)invWorkInfo.getValue(Item.ITF));
            invResult.setBundleItf((String)invWorkInfo.getValue(Item.BUNDLE_ITF));
            invResult.setEnteringQty(invWorkInfo.getBigDecimal(Item.ENTERING_QTY, new BigDecimal(0)).intValue());
            invResult.setBundleEnteringQty(invWorkInfo.getBigDecimal(Item.BUNDLE_ENTERING_QTY, new BigDecimal(0)).intValue());
            invResult.setLotNo(invWorkInfo.getLotNo());
            invResult.setStockQty(invWorkInfo.getStockQty());
            invResult.setAllocationQty(invWorkInfo.getAllocationQty());
            invResult.setResultStockQty(invWorkInfo.getResultStockQty());
            invResult.setUserId(invWorkInfo.getUserId());
            invResult.setUserName((String)invWorkInfo.getValue(Com_loginuser.USERNAME));
            invResult.setTerminalNo(invWorkInfo.getTerminalNo());
            invResult.setConfirmUserId(compParam.getWmsUserInfo().getUserId());
            invResult.setConfirmUserName(compParam.getWmsUserInfo().getUserName());
            invResult.setConfirmWorkDate(invWorkInfo.getConfirmWorkDate());
            invResult.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
            invResult.setWorkSecond(invWorkInfo.getWorkSecond());
            invResult.setRegistDate(new SysDate());
            invResult.setRegistPname(getClass().getSimpleName());
            invResult.setLastUpdatePname(getClass().getSimpleName());
            // 棚卸実績情報 作成
            irHandler.create(invResult);
        }

        /**********************************************/
        /* 全確定したデータを削除(スケジュールNo単位) */
        /**********************************************/
        InventSettingSearchKey issKey = new InventSettingSearchKey();
        issKey.setStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_SETTING_START);
        InventSetting[] invSettings = (InventSetting[])isHandler.find(issKey);
        for (InventSetting invSetting : invSettings)
        {
            // 棚卸作業情報検索キー
            InventWorkInfoSearchKey iwsKey = new InventWorkInfoSearchKey();
            FieldName minStatusField = new FieldName("", "MIN_STATUS_");
            iwsKey.setScheduleNo(invSetting.getScheduleNo());

            // 状態フラグの最小値を取得
            iwsKey.setCollect(InventWorkInfo.STATUS_FLAG, "MIN", minStatusField);
            Entity[] recs = iwHandler.find(iwsKey);
            String minStat = (String)recs[0].getValue(minStatusField, "");
            // 状態フラグの最小値が確定済み以上であれば完了とする
            if ((!StringUtil.isBlank(minStat))
                    && (InventWorkInfo.STATUS_FLAG_INVENTORY_ALREADY_COMPLETED.compareTo(minStat) <= 0))
            {
                // スケジュールNoをキーに、棚卸作業情報を削除
                InventWorkInfoSearchKey iwdelKey = new InventWorkInfoSearchKey();
                iwdelKey.setScheduleNo(invSetting.getScheduleNo());
                iwHandler.drop(iwdelKey);

                // スケジュールNoをキーに、棚卸設定情報を4:完了 に更新
                InventSettingAlterKey isaKey = new InventSettingAlterKey();
                isaKey.setScheduleNo(invSetting.getScheduleNo());
                isaKey.updateStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
                isaKey.updateLastUpdatePname(getCallerName());
                isHandler.modify(isaKey);
            }
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
     * 棚卸作業情報を名称の項目を含めて取得するための検索キークラスのインスタンスを生成します。<BR>
     * @param jobNo     作業No
     * @param itemCode  商品コード(商品マスタ情報を取得する/しないの判断用)
     * @return 棚卸作業情報検索キークラスのインスタンス
     */
    protected InventWorkInfoSearchKey createInventWorkInfoSearchKey(String jobNo, String itemCode)
    {
        InventWorkInfoSearchKey iwsKey = new InventWorkInfoSearchKey();
        iwsKey.setJobNo(jobNo);
        iwsKey.setStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_ALREADY_COMPLETED);
        /* 結合条件の指定 */
        // 荷主マスタ.荷主コード
        iwsKey.setJoin(InventWorkInfo.CONSIGNOR_CODE, Consignor.CONSIGNOR_CODE);
        // ログインユーザ.ユーザID
        iwsKey.setJoin(InventWorkInfo.USER_ID, Com_loginuser.USERID);
        /* 取得項目と集約条件の指定 */
        // 棚卸確定日
        iwsKey.setConfirmWorkDayCollect();
        // 作業No.
        iwsKey.setJobNoCollect();
        // スケジュールNo.
        iwsKey.setScheduleNoCollect();
        // 設定単位キー
        iwsKey.setSettingUnitKeyCollect();
        // 状態フラグ
        iwsKey.setStatusFlagCollect();
        // エリアNo.
        iwsKey.setAreaNoCollect();
        // 棚No.
        iwsKey.setLocationNoCollect();
        // 荷主コード
        iwsKey.setConsignorCodeCollect();
        // 荷主名称
        iwsKey.setCollect(Consignor.CONSIGNOR_NAME);
        // 棚卸ユーザID
        iwsKey.setUserIdCollect();
        // 棚卸ユーザ名称
        iwsKey.setCollect(Com_loginuser.USERNAME);
        // 棚卸端末No.、RFTNo.
        iwsKey.setTerminalNoCollect();
        // 棚卸確定ユーザID
        // 棚卸確定ユーザ名称
        // 棚卸確定日時
        iwsKey.setConfirmWorkDateCollect();
        // 実績報告区分
        // 作業秒数
        iwsKey.setWorkSecondCollect();
        // 登録日時
        iwsKey.setRegistDateCollect();
        // 登録処理名
        iwsKey.setRegistPnameCollect();

        if (!StringUtil.isBlank(itemCode))
        {
            // 商品マスタ.荷主コード
            iwsKey.setJoin(InventWorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
            // 商品マスタ.商品コード
            iwsKey.setJoin(InventWorkInfo.ITEM_CODE, Item.ITEM_CODE);

            // 商品コード
            iwsKey.setItemCodeCollect();
            // 商品名称
            iwsKey.setCollect(Item.ITEM_NAME);
            // JANコード
            iwsKey.setCollect(Item.JAN);
            // ケースITF
            iwsKey.setCollect(Item.ITF);
            // ボールITF
            iwsKey.setCollect(Item.BUNDLE_ITF);
            // ケース入数
            iwsKey.setCollect(Item.ENTERING_QTY);
            // ボール入数
            iwsKey.setCollect(Item.BUNDLE_ENTERING_QTY);
            // ロットNo.
            iwsKey.setLotNoCollect();
            // 在庫数
            iwsKey.setStockQtyCollect();
            // 出庫可能数
            iwsKey.setAllocationQtyCollect();
            // 棚卸結果数
            iwsKey.setResultStockQtyCollect();
        }

        return iwsKey;
    }

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
        return "$Id: InventoryOperator.java 846 2008-10-28 08:52:58Z ota $";
    }

}
