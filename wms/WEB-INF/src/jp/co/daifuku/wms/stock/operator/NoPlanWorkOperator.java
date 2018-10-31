// $Id: NoPlanWorkOperator.java 5990 2009-11-17 11:40:10Z nagao $
package jp.co.daifuku.wms.stock.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 *
 * This software is the proprietary information of
 * DAIFUKU Co.,Ltd. Use is subject to license terms.
 */
import static jp.co.daifuku.wms.base.entity.SystemDefine.HARDWARE_TYPE_RFT;
import static jp.co.daifuku.wms.base.entity.SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL;
import static jp.co.daifuku.wms.base.entity.SystemDefine.JOB_TYPE_NOPLAN_STORAGE;
import static jp.co.daifuku.wms.base.entity.SystemDefine.REPORT_FLAG_NOT_REPORT;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_COMPLETION;
import static jp.co.daifuku.wms.base.exception.OperatorException.ERR_ALREADY_UPDATED;
import static jp.co.daifuku.wms.base.exception.OperatorException.ERR_LOT_NO_DUPLICATED;
import static jp.co.daifuku.wms.base.exception.OperatorException.ERR_SHORTAGE_ALLOCATION_QTY;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.HostSendController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReasonHandler;
import jp.co.daifuku.wms.base.dbhandler.ReasonSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkListHandler;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Reason;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkList;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.stock.schedule.StockInParameter;
import jp.co.daifuku.wms.stock.schedule.StockOutParameter;

/**
 * 予定外入出庫作業を行うためのオペレータクラスです。
 *
 *
 * @version $Revision: 5990 $, $Date: 2009-11-17 20:40:10 +0900 (火, 17 11 2009) $
 * @author ss
 * @author Last commit: $Author: nagao $
 */
// UPDATE_SS (2007-07-06)
public class NoPlanWorkOperator
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
    private ConsignorController _consCtlr;

    private ItemController _itemCtlr;

    private StockController _stockCtlr;

    private WarenaviSystemController _WSysCtlr;

    private WMSSequenceHandler _seqHandler;

    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * データベースコネクションと呼び出し元クラスを指定して インスタンスを生成します。
     *
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public NoPlanWorkOperator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * 予定外入庫完了処理を行います。
     *
     * @param params 完了パラメータ
     *        <ol>
     *        以下の項目が参照されます
     *        <li>荷主コード
     *        <li>荷主名称 (マスタなしのとき)
     *        <li>商品コード
     *        <li>商品名称 (マスタなしのとき)
     *        <li>JAN (マスタなしのとき)
     *        <li>ケース入数 (マスタなしのとき)
     *        <li>ボール入数 (マスタなしのとき)
     *        <li>ケースITF (マスタなしのとき)
     *        <li>ボールITF (マスタなしのとき)
     *        <li>エリアNo
     *        <li>棚No
     *        <li>ロットNo
     *        <li>予定ロットNo
     *        <li>実績数
     *        <li>作業秒数
     *        <li>ユーザID
     *        <li>ユーザ名
     *        <li>端末No
     *        </ol>
     *
     * @return 設定単位キー
     * @throws ScheduleException システムに整合性がないときスローされます。
     * @throws ReadWriteException データベースエラー
     * @throws OperatorException 設定対象データがすでに更新されたときスローされます
     * @throws NoPrimaryException 対象データが複数のときスローされます。
     * @throws LockTimeOutException ロックタイムアウトが発生したときスローされます。
     * @throws DataExistsException 入庫作業情報登録済み
     */
    public StockOutParameter completeStorage(StockInParameter[] params)
            throws ReadWriteException,
                ScheduleException,
                OperatorException,
                LockTimeOutException,
                NoPrimaryException,
                DataExistsException
    {
        // controllers
        initControllers();

        WarenaviSystemController wsc = _WSysCtlr;
        StockController sc = _stockCtlr;
        ConsignorController cc = _consCtlr;
        ItemController ic = _itemCtlr;

        // 設定単位キーの採番
        String settingUkey = _seqHandler.nextWorkInfoSetUkey();

        StockOutParameter retParam = new StockOutParameter();
        retParam.setSettingUnitKey(settingUkey);

        // 共通データの取得
        String workDay = wsc.getWorkDay();
        boolean hasMaster = wsc.hasMasterPack();
        String jobType = JOB_TYPE_NOPLAN_STORAGE;
        Date storageDate = new SysDate();
        String storageDay = wsc.getWorkDay();

        for (int i = 0; i < params.length; i++)
        {
            try
            {
                StockInParameter iparam = params[i];

                // マスタパッケージなしのとき自動登録
                if (!hasMaster)
                {
                    autoCreateConsignor(iparam, cc);
                    autoCreateItem(iparam, ic);
                }

                if (WmsParam.MAX_STOCK_QTY < iparam.getResultQty() || 0 > iparam.getResultQty())
                {
                    throw new OperatorException(OperatorException.ERR_OVERFLOW);
                }
                // lock target stock and check
                Stock newStock = buildStock(iparam);
                // 共通データをセット
                newStock.setStorageDate(storageDate);
                newStock.setStorageDay(storageDay);
                Stock[] targetStocks = sc.lock(newStock);
                if (1 < ArrayUtil.length(targetStocks))
                {
                    // too many target
                    throw new NoPrimaryException();
                }
                boolean noTarget = ArrayUtil.isEmpty(targetStocks);

                String stockId;
                if (noTarget)
                {
                    // create new stock
                    newStock.setStorageDay(workDay);
                    newStock.setAllocationQty(newStock.getStockQty());
                    newStock.setStorageDate(new SysDate());

                    stockId = sc.initStorage(newStock, jobType, iparam.getWmsUserInfo(), iparam.getReasonType());
                }
                else
                {
                    // add to exists stock
                    Stock targetStock = targetStocks[0];

                    // DFKLOOK v3.4 積み増しの場合は、入庫日時を更新しないように修正
                    newStock.setStorageDay(null);
                    newStock.setStorageDate(null);
                    stockId =
                            sc.addStorage(targetStock, newStock, jobType, false, iparam.getWmsUserInfo(),
                                    iparam.getReasonType());
                }
                iparam.setStockID(stockId);
                createHostSend(iparam, settingUkey, jobType, workDay);
            }
            catch (OperatorException e)
            {
                e.setErrorLineNo(i + 1);
                throw e;
            }
            catch (NotFoundException e)
            {
                OperatorException ex = new OperatorException(ERR_ALREADY_UPDATED);
                ex.setErrorLineNo(i + 1);
                throw ex;
            }
            catch (DataExistsException e)
            {
                OperatorException ex = new OperatorException(ERR_ALREADY_UPDATED);
                ex.setErrorLineNo(i + 1);
                throw ex;
            }
        }

        // 作業リスト情報作成メソッド
        createWorkListByHostSend(settingUkey);

        return retParam;
    }

    /**
     * 予定外出庫完了処理を行います。
     *
     * @param params 完了パラメータ<br>
     *        <ol>
     *        以下の項目が参照されます
     *        <li>荷主コード
     *        <li>商品コード
     *        <li>エリアNo
     *        <li>棚No
     *        <li>ロットNo
     *        <li>予定ロットNo
     *        <li>実績数
     *        <li>作業秒数
     *        <li>ユーザID
     *        <li>ユーザ名
     *        <li>端末No
     *        </ol>
     *
     * @return 設定単位キー
     * @throws ScheduleException システムに整合性がないときスローされます。
     * @throws ReadWriteException データベースエラー
     * @throws DuplicateOperatorException
     *         ロットNo.チェック時に重複があった場合にスローされます。
     * @throws OperatorException 作業が続行できないとき詳細が通知されます。<br>
     *         詳細には対象行番号(1スタート)が文字としてセットされています。
     * @throws NoPrimaryException 対象データが複数のときスローされます。
     * @throws LockTimeOutException ロックタイムアウト
	 * @throws DataExistsException 作業情報登録済み
     */
    public StockOutParameter completeRetrieval(StockInParameter[] params)
            throws ReadWriteException,
                ScheduleException,
                DuplicateOperatorException,
                OperatorException,
                NoPrimaryException,
                LockTimeOutException,
                DataExistsException
    {
        // controllers
        initControllers();

        WarenaviSystemController wsc = _WSysCtlr;
        StockController sc = _stockCtlr;

        // 設定単位キーの採番
        String settingUkey = _seqHandler.nextWorkInfoSetUkey();

        StockOutParameter retParam = new StockOutParameter();
        retParam.setSettingUnitKey(settingUkey);

        String workDay = wsc.getWorkDay();

        for (int i = 0; i < params.length; i++)
        {
            try
            {
                StockInParameter iparam = params[i];

                Stock stparam = buildStock(iparam);

                Stock[] targetStocks = sc.lock(stparam);
                if (ArrayUtil.isEmpty(targetStocks))
                {
                    // stock not found.
                    throw new NotFoundException();
                }

                int resultQty = iparam.getResultQty();
                WmsUserInfo ui = iparam.getWmsUserInfo();

                // check duplicate if hardware is RFT
                String hw = ui.getHardwareType();
                if (HARDWARE_TYPE_RFT.equals(hw))
                {
                    checkDuplicate(targetStocks);
                }

                // check over pick
                Stock targetStock = targetStocks[0];
                boolean overPick = targetStock.getAllocationQty() < resultQty;
                if (overPick)
                {
                    OperatorException ex = new OperatorException(ERR_SHORTAGE_ALLOCATION_QTY);
                    ex.setErrorLineNo(i + 1);
                    ex.setDetail(String.valueOf(targetStock.getAllocationQty()));
                    throw ex;
                }

                String jobType = JOB_TYPE_NOPLAN_RETRIEVAL;

                // process retrieval
                sc.retrieval(targetStock, jobType, resultQty, 0, workDay, true, ui);

                // HostSendに、出庫した在庫のStockIDをセットできるように、パラメータにセット
                iparam.setStockID(targetStock.getStockId());
                createHostSend(iparam, settingUkey, jobType, workDay);

                // find temporary area
                AreaController arc = new AreaController(getConnection(), getCaller());
                String tempAreaNo = arc.getTemporaryArea(iparam.getAreaNo());

                // do create if temporary area found.
                if (!StringUtil.isBlank(tempAreaNo))
                {
                    Stock tmpNewStock = buildStock(iparam);
                    tmpNewStock.setAreaNo(tempAreaNo);
                    tmpNewStock.setLocationNo(WmsParam.DEFAULT_LOCATION_NO);

                    Stock[] tgtStock = sc.lock(tmpNewStock);
                    boolean noTarget = ArrayUtil.isEmpty(tgtStock);

                    tmpNewStock.setStorageDay(workDay);
                    tmpNewStock.setStockQty(resultQty);
                    tmpNewStock.setAllocationQty(tmpNewStock.getStockQty());
                    tmpNewStock.setStorageDate(new SysDate());
                    if (noTarget)
                    {
                        // create new stock
                        sc.initStorage(tmpNewStock, jobType, ui, 0);
                    }
                    else
                    {
                        // add to exists stock
                        Stock addTarget = tgtStock[0];

                        // DFKLOOK v3.4 積み増しの場合は、入庫日時を更新しないように修正
                        tmpNewStock.setStorageDay(null);
                        tmpNewStock.setStorageDate(null);
                        sc.addStorage(addTarget, tmpNewStock, jobType, false, ui, iparam.getReasonType());
                    }
                }
            }
            catch (OperatorException e)
            {
                e.setErrorLineNo(i + 1);
                throw e;
            }
            catch (NotFoundException e)
            {
                OperatorException ex = new OperatorException(ERR_ALREADY_UPDATED);
                ex.setErrorLineNo(i + 1);
                throw ex;
            }
            catch (DataExistsException e)
            {
                OperatorException ex = new OperatorException(ERR_ALREADY_UPDATED);
                ex.setErrorLineNo(i + 1);
                throw ex;
            }
        }

        // 作業リスト情報作成メソッド
        createWorkListByHostSend(settingUkey);
        return retParam;
    }

    /**
     * 入庫即出庫完了処理を行います。
     *
     *        <ol>
     *        以下の項目が参照されます。
     *        <li>仕入先コード
     *        <li>仕入先名称
     *        <li>商品コード
     *        <li>商品名称
     *        <li>JANコード
     *        <li>ケース入り数
     *        <li>ボール入り数
     *        <li>ケースITF
     *        <li>ボールITF
     *        <li>エリアNo.
     *        <li>棚No.
     *        <li>ロットNo.
     *        <li>予定ロットNo.
     *        <li>実績数
     *        <li>作業秒数
     *        <li>ユーザID
     *        <li>ユーザ名称
     *        <li>号機No.
     *        </ol>
     *
     * @param params 入力パラメータ
     * @return 出力パラメータ
     * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知される例外です。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DuplicateOperatorException データが重複した場合にスローされます。
     * @throws OperatorException 作業データの処理が正常に完了できない場合に通知される例外です。
     * @throws NoPrimaryException 定義情報が異常な場合にスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     */
    public StockOutParameter completeShipping(StockInParameter[] params)
            throws ReadWriteException,
                ScheduleException,
                DuplicateOperatorException,
                OperatorException,
                LockTimeOutException,
                NoPrimaryException
    {
        // controllers
        initControllers();

        WarenaviSystemController wsc = _WSysCtlr;
        StockController sc = _stockCtlr;
        ConsignorController cc = _consCtlr;
        ItemController ic = _itemCtlr;

        // Get the key index
        String settingUkey = _seqHandler.nextWorkInfoSetUkey();  //SELECT WORKINFO_SETUKEY_SEQ.NEXTVAL FROM DUAL

        StockOutParameter retParam = new StockOutParameter();
        retParam.setSettingUnitKey(settingUkey);

        // Get the common data
        String workDay = wsc.getWorkDay();
        boolean hasMaster = wsc.hasMasterPack();
        String jobType = "";
        Date storageDate = new SysDate();
        String storageDay = wsc.getWorkDay();

        for (int i = 0; i < params.length; i++)
        {
            try
            {
                jobType = JOB_TYPE_NOPLAN_STORAGE;
                StockInParameter iparam = params[i];

                // Auto-register when there is no master package
                if (!hasMaster)
                {
                    autoCreateConsignor(iparam, cc);
                    // if item exists (search by item code), update, else insert
                    autoCreateItem(iparam, ic);
                }

                // lock target stock and check
                Stock newStock = buildStock(iparam);
                // Set common data
                newStock.setStorageDate(storageDate);
                newStock.setStorageDay(storageDay);

                Stock[] targetStocks = null;
                if (!StringUtil.isBlank(iparam.getLocation()))
                {
                    targetStocks = sc.lock(newStock); //SELECT DNSTOCK.* FROM DNSTOCK,DMLOCATE WHERE...AND DNSTOCK.AREA_NO=DMLOCATE.AREA_NO(+) AND DNSTOCK.LOCATION_NO=DMLOCATE.LOCATION_NO(+)  FOR UPDATE WAIT 1
                    if (1 < ArrayUtil.length(targetStocks))
                    {
                        // too many target
                        throw new NoPrimaryException();
                    }
                }
                boolean noTarget = ArrayUtil.isEmpty(targetStocks);

                //glm Borrow this from CompleteStorage.  If there is existing stock at
                //    the location, then merge this qty to what is already there,
                //    otherwise create a new stock item.  That should work for shipping as
                //    well, but since we automatically pick at shipping, there should be
                //    no existing stock at the shipping dock.
                String stockId;
                if (noTarget)
                {
                    // create new stock
                    newStock.setStorageDay(workDay);
                    newStock.setAllocationQty(newStock.getStockQty());
                    newStock.setStorageDate(new SysDate());

//                  INSERT INTO DNSTOCK; UPDATE DMLOCATE; INSERT INTO DNSTOCKHISTORY
                    stockId = sc.initStorage(newStock, jobType, iparam.getWmsUserInfo(), Reason.DEFAULT_REASON_TYPE);
                    newStock.setStockId(stockId);
                }
                else
                {
                    // add to existing stock
                    Stock targetStock = targetStocks[0];

                    // DFKLOOK v3.4 積み増しの場合は、入庫日時を更新しないように修正
                    newStock.setStorageDay(null);
                    newStock.setStorageDate(null);
                    stockId = sc.addStorage(targetStock, newStock, jobType, false, iparam.getWmsUserInfo(), Reason.DEFAULT_REASON_TYPE);
                }
                iparam.setStockID(stockId);
                createHostSend(iparam, settingUkey, jobType, workDay);  //INSERT INTO DNHOSTSEND

                //glm Now let's pick it
                targetStocks = sc.lock(newStock);  //we probably just added new stock at the dock; freshen the list
                if (ArrayUtil.isEmpty(targetStocks))
                {
                    // stock not found.
                    throw new NotFoundException();
                }

                int resultQty = iparam.getResultQty();
                WmsUserInfo ui = iparam.getWmsUserInfo();

                // check duplicate if hardware is RFT
                String hw = ui.getHardwareType();
                if (HARDWARE_TYPE_RFT.equals(hw))
                {
                    checkDuplicate(targetStocks);
                }

                // check over pick (glm no such thing for "no plan", hope this doesn't hang us up)
                Stock targetStock = targetStocks[0];
                boolean overPick = targetStock.getAllocationQty() < resultQty;
                if (overPick)
                {
                    OperatorException ex = new OperatorException(ERR_SHORTAGE_ALLOCATION_QTY);
                    ex.setErrorLineNo(i + 1);
                    ex.setDetail(String.valueOf(targetStock.getAllocationQty()));
                    throw ex;
                }

                jobType = JOB_TYPE_NOPLAN_RETRIEVAL;

                // process retrieval
                sc.retrieval(targetStock, jobType, resultQty, 0, workDay, true, ui);

                // HostSendに、出庫した在庫のStockIDをセットできるように、パラメータにセット
                iparam.setStockID(targetStock.getStockId());
                createHostSend(iparam, settingUkey, jobType, workDay); //INSERT INTO DNHOSTSEND

                // find temporary area
                AreaController arc = new AreaController(getConnection(), getCaller());
                String tempAreaNo = arc.getTemporaryArea(iparam.getAreaNo());

                // do create if temporary area found.
                //glm Maybe we don't need the storage section above if we're creating temporary stock here
                if (!StringUtil.isBlank(tempAreaNo))
                {
                    Stock tmpNewStock = buildStock(iparam);
                    tmpNewStock.setAreaNo(tempAreaNo);
                    tmpNewStock.setLocationNo(WmsParam.DEFAULT_LOCATION_NO);

                    Stock[] tgtStock = sc.lock(tmpNewStock);
                    noTarget = ArrayUtil.isEmpty(tgtStock);

                    tmpNewStock.setStorageDay(workDay);
                    tmpNewStock.setStockQty(resultQty);
                    tmpNewStock.setAllocationQty(tmpNewStock.getStockQty());
                    tmpNewStock.setStorageDate(new SysDate());
                    if (noTarget)
                    {
                        // create new stock
                        sc.initStorage(tmpNewStock, jobType, ui, Reason.DEFAULT_REASON_TYPE);
                    }
                    else
                    {
                        // add to exists stock
                        Stock addTarget = tgtStock[0];

                        // DFKLOOK v3.4 積み増しの場合は、入庫日時を更新しないように修正
                        tmpNewStock.setStorageDay(null);
                        tmpNewStock.setStorageDate(null);
                        sc.addStorage(addTarget, tmpNewStock, jobType, false, ui, Reason.DEFAULT_REASON_TYPE);
                    }
                }

            }
            catch (OperatorException e)
            {
                e.setErrorLineNo(i + 1);
                throw e;
            }
            catch (NotFoundException e)
            {
                OperatorException ex = new OperatorException(ERR_ALREADY_UPDATED);
                ex.setErrorLineNo(i + 1);
                throw ex;
            }
            catch (DataExistsException e)
            {
                OperatorException ex = new OperatorException(ERR_ALREADY_UPDATED);
                ex.setErrorLineNo(i + 1);
                throw ex;
            }
        }
        return retParam;
    }



    /**
     * 在庫情報の重複不可項目のチェックを行います。
     *
     * @param stocks チェック対象在庫
     * @throws DuplicateOperatorException
     *         チェックの結果、重複項目があるときスローされます。<br>
     */
    protected void checkDuplicate(Stock[] stocks)
            throws DuplicateOperatorException
    {
        // check lot number
        // オペレータ例外へのデータがLot numberなので、Stockインスタンスを
        // 保持する必要はありませんが、将来拡張用にインスタンスをマップに保持します。
        Map<String, Stock> lotNoMap = new HashMap<String, Stock>();

        // チェック結果保持マップ
        Map[] checkMaps = {
            lotNoMap,
        };

        // 各データについて重複している内容を収集
        for (Stock stock : stocks)
        {
            // チェック対象データ一覧
            String[] keyValues = {
                stock.getLotNo(),
            };

            for (int i = 0; i < checkMaps.length; i++)
            {
                Map checkMap = checkMaps[i];
                String key = keyValues[i];

                // 未登録のキーならば、在庫情報を保持
                if (!checkMap.containsKey(key))
                {
                    checkMap.put(key, stock);
                }
            }

        }

        // check duplicate and throw OperatorException if duplicated.
        if (lotNoMap.size() > 1)
        {
            // exception for report check result.
            DuplicateOperatorException ex = new DuplicateOperatorException();
            ex.setErrorCode(ERR_LOT_NO_DUPLICATED);

            // set lot number (String) to the list
            List<String> dupLotNoList = new ArrayList<String>(lotNoMap.keySet());
            ex.setDetail(dupLotNoList);

            throw ex;
        }
    }

    /**
     * 実績送信情報を作成します。
     *
     * @param param 実績元情報
     *        <ol>
     *        以下の情報が参照されます
     *        <li>荷主コード
     *        <li>商品コード
     *        <li>エリアNo
     *        <li>棚No
     *        <li>予定ロットNo
     *        <li>実績数
     *        <li>ロットNo
     *        <li>作業秒数
     *        <li>ユーザID
     *        <li>ユーザ名
     *        <li>端末No
     *        </ol>
     *
     * @param settingUkey 設定単位キー
     * @param jobType 作業タイプ
     * @param workDay 作業日
     * @throws NoPrimaryException 荷主コードに対して荷主が複数
     * @throws ReadWriteException データベースエラー
     * @throws DataExistsException
     *         同様の実績がすでに登録済みのときスローされます。
     * @throws ScheduleException システムに整合性がないときスローされます。
     */
    protected void createHostSend(StockInParameter param, String settingUkey, String jobType, String workDay)
            throws ReadWriteException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        // getting keys from in-parameter
        StockInParameter siparam = param;
        String consignorCode = siparam.getConsignorCode();
        String itemCode = siparam.getItemCode();
        int reasonType = siparam.getReasonType();

        // get consignor name
        String consignorName = getConsignorName(consignorCode);

        // get item master info
        ItemHandler ith = new ItemHandler(getConnection());
        ItemSearchKey itkey = new ItemSearchKey();
        itkey.setConsignorCode(consignorCode);
        itkey.setItemCode(itemCode);

        Item itement = (Item)ith.findPrimary(itkey);
        if (null == itement)
        {
            // 商品マスタが不整合であっても、動作は続ける
            itement = new Item();
            // 6026102={0}テーブルに({1}, {2})の詳細情報が見つかりません。
            Object[] mps = {
                    Item.STORE_NAME,
                    consignorCode,
                    itemCode,
            };
            RmiMsgLogClient.write(6026102, getCaller().getName(), mps);
        }

        // get reason master info
        Reason reasonent = new Reason();
        ReasonHandler reh = new ReasonHandler(getConnection());
        ReasonSearchKey rekey = new ReasonSearchKey();
        rekey.setReasonType(reasonType);
        reasonent = (Reason)reh.findPrimary(rekey);

        // get user info from master
        String userId = siparam.getUserId();
        String userName = siparam.getUserName();

        WMSSequenceHandler seqh = _seqHandler;

        // find hardware_type
        AreaController arc = new AreaController(getConnection(), getCaller());
        String area_type = arc.getAreaType(siparam.getAreaNo());
        if(area_type.equals(SystemDefine.AREA_TYPE_ASRS))
        {
            siparam.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS);
        }
        else if(area_type.equals(SystemDefine.AREA_TYPE_FLOOR))
        {
            siparam.setHardwareType(SystemDefine.HARDWARE_TYPE_LIST);
        }

        // getting next job number
        String jobNo = seqh.nextWorkInfoJobNo();
        // 集約作業No.採番
        String collectJobNo = seqh.nextWorkInfoCollectJobNo();

        // create hostsend
        HostSend hsendent = new HostSend();
        hsendent.setWorkDay(workDay);
        hsendent.setJobNo(jobNo);
        hsendent.setCollectJobNo(collectJobNo);
        hsendent.setSettingUnitKey(settingUkey);
        hsendent.setStockId(siparam.getStockID());
        hsendent.setJobType(jobType);
        hsendent.setStatusFlag(STATUS_FLAG_COMPLETION);
        hsendent.setPlanDay(workDay);
        hsendent.setConsignorCode(consignorCode);
        hsendent.setConsignorName(consignorName);
        hsendent.setPlanAreaNo(siparam.getAreaNo());
        hsendent.setPlanLocationNo(siparam.getLocation());

        hsendent.setItemCode(itemCode);
        hsendent.setItemName(itement.getItemName());
        hsendent.setJan(itement.getJan());
        hsendent.setItf(itement.getItf());
        hsendent.setBundleItf(itement.getBundleItf());
        hsendent.setEnteringQty(itement.getEnteringQty());
        hsendent.setBundleEnteringQty(itement.getBundleEnteringQty());
        hsendent.setPlanLotNo(siparam.getLotNo());
        hsendent.setPlanQty(siparam.getResultQty());
        hsendent.setResultQty(siparam.getResultQty());
        hsendent.setShortageQty(0);
        hsendent.setResultAreaNo(siparam.getAreaNo());
        hsendent.setResultLocationNo(siparam.getLocation());
        hsendent.setResultLotNo(siparam.getResultLotNo());
        hsendent.setReportFlag(REPORT_FLAG_NOT_REPORT);
        hsendent.setUserId(userId);
        hsendent.setUserName(userName);
        hsendent.setHardwareType(siparam.getHardwareType());
        hsendent.setTerminalNo(siparam.getTerminalNo());
        hsendent.setWorkSecond(siparam.getWorkSeconds());
        hsendent.setRegistPname(getCallerName());
        hsendent.setRegistDate(new SysDate());
        hsendent.setLastUpdatePname(getCallerName());

        hsendent.setReasonType(reasonType);
        hsendent.setReasonName(reasonent.getReasonName());

        // excludes StockId

        HostSendController hsc = new HostSendController(getConnection(), getCaller());
        hsc.insert(hsendent);
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
     * コントローラを準備します。
     *
     * @throws ScheduleException システム定義異常
     * @throws ReadWriteException データベースアクセスエラー
     */
    protected void initControllers()
            throws ReadWriteException,
                ScheduleException
    {
        // create instances if not created.
        if (null == _consCtlr)
        {
            Connection conn = getConnection();
            Class caller = getCaller();

            _consCtlr = new ConsignorController(conn, caller);
            _itemCtlr = new ItemController(conn, caller);
            _stockCtlr = new StockController(conn, caller);
            _WSysCtlr = new WarenaviSystemController(conn, caller);
            _seqHandler = new WMSSequenceHandler(conn);
        }
    }

    /**
     * getting name of consignor.
     *
     * @param consignorCode 荷主コード
     * @return 荷主名称
     *
     * @throws NoPrimaryException 対象の荷主が複数見つかった
     * @throws ReadWriteException データベースアクセスエラー
     */
    protected String getConsignorName(String consignorCode)
            throws ReadWriteException,
                NoPrimaryException
    {
        ConsignorHandler coh = new ConsignorHandler(getConnection());
        ConsignorSearchKey cokey = new ConsignorSearchKey();

        cokey.setConsignorNameCollect();
        cokey.setConsignorCode(consignorCode);

        Consignor coent = (Consignor)coh.findPrimary(cokey);

        // 荷主マスタが不整合であっても、動作は続ける
        if (null == coent)
        {
            // 6026101={0}テーブルに({1})の詳細情報が見つかりません。
            Object[] mps = {
                    Consignor.STORE_NAME,
                    consignorCode,
            };
            RmiMsgLogClient.write(6026101, getCaller().getName(), mps);
            return "";
        }
        return coent.getConsignorName();
    }

    /**
     * 商品マスターに自動登録を行います。
     *
     * @param iparam 入力パラメータ<br>
     *        <ol>
     *        <li>荷主コード
     *        <li>商品コード
     *        <li>商品名称
     *        <li>JAN
     *        <li>ケース入数
     *        <li>ボール入数
     *        <li>ケースITF
     *        <li>ボールITF
     *        </ol>
     *
     * @param ic ItemController
     * @throws DataExistsException すでにマスタ登録済み
     * @throws NotFoundException 更新対象マスタデータなし
     * @throws NoPrimaryException 対象マスタデータが複数存在した
     * @throws LockTimeOutException ロックタイムアウト
     * @throws ReadWriteException データベースアクセスエラー
     *
     * @see ItemController#autoCreate(Item, jp.co.daifuku.wms.base.common.WmsUserInfo)
     */
    protected void autoCreateItem(StockInParameter iparam, ItemController ic)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                NotFoundException,
                DataExistsException
    {
        Item newItem = new Item();

        newItem.setConsignorCode(iparam.getConsignorCode());
        newItem.setItemCode(iparam.getItemCode());
        newItem.setItemName(iparam.getItemName());
        newItem.setJan(iparam.getJanCode());
        newItem.setEnteringQty(iparam.getEnteringQty());
        newItem.setBundleEnteringQty(iparam.getBundleEnteringQty());
        newItem.setItf(iparam.getItf());
        newItem.setBundleItf(iparam.getBundleItf());

        ic.autoCreate(newItem, iparam.getWmsUserInfo());
    }

    /**
     * 荷主マスターに自動登録を行います。
     *
     * @param iparam 入力パラメータ
     * @param cc 荷主コントローラ
     *
     * @throws DataExistsException マスタ登録済み
     * @throws NotFoundException 更新対象マスタなし
     * @throws NoPrimaryException 対象マスタが複数存在する
     * @throws LockTimeOutException マスタロックタイムアウト
     * @throws ReadWriteException データベースアクセスエラー
     *
     * @see ConsignorController#autoCreate(Consignor, jp.co.daifuku.wms.base.common.WmsUserInfo)
     */
    protected void autoCreateConsignor(StockInParameter iparam, ConsignorController cc)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                NotFoundException,
                DataExistsException
    {
        Consignor newCons = new Consignor();

        newCons.setConsignorCode(iparam.getConsignorCode());
        newCons.setConsignorName(iparam.getConsignorName());

        cc.autoCreate(newCons, iparam.getWmsUserInfo());
    }

    /**
     * パラメータから在庫情報を組み立てて返します。
     *
     * @param iparam 入力パラメータ
     *        <ol>
     *        <li>エリアNo
     *        <li>棚No
     *        <li>荷主コード
     *        <li>商品コード
     *        <li>ロットNo
     *        <li>実績数
     *        </ol>
     * @return 在庫情報
     */
    protected Stock buildStock(StockInParameter iparam)
    {
        Stock stparam = new Stock();

        stparam.setAreaNo(iparam.getAreaNo());
        stparam.setLocationNo(iparam.getLocation());
        stparam.setConsignorCode(iparam.getConsignorCode());
        stparam.setItemCode(iparam.getItemCode());
        stparam.setLotNo(iparam.getLotNo());

        // not key data
        stparam.setStockQty(iparam.getResultQty());

        return stparam;
    }

    /**
     * 設定単位キーを元に、入出庫実績送信情報の検索を行い、作業リスト情報を作成します。<br>
     * <ul>
     * 以下のDB情報を検索します。
     * <li>入出庫実績送信情報
     * </ul>
     *
     * @param key 設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     * @throws ScheduleException 作業情報からマスタを検索できなかったときスローされます。
     */
    protected void createWorkListByHostSend(String key)
            throws ReadWriteException,
                DataExistsException,
                ScheduleException
    {
        HostSendHandler hsh = new HostSendHandler(getConnection());
        HostSendSearchKey hkey = new HostSendSearchKey();

        // 取得項目一覧の作成
        FieldName[] collects = {
            HostSend.JOB_NO,
            HostSend.SETTING_UNIT_KEY,
            HostSend.COLLECT_JOB_NO,
            HostSend.JOB_TYPE,
            HostSend.PLAN_UKEY,
            HostSend.STOCK_ID,
            HostSend.PLAN_DAY,
            HostSend.CONSIGNOR_CODE,
            HostSend.CONSIGNOR_NAME,
            HostSend.CUSTOMER_CODE,
            HostSend.CUSTOMER_NAME,
            HostSend.SHIP_TICKET_NO,
            HostSend.SHIP_LINE_NO,
            HostSend.SHIP_BRANCH_NO,
            HostSend.BATCH_NO,
            HostSend.ORDER_NO,
            HostSend.PLAN_AREA_NO,
            HostSend.PLAN_LOCATION_NO,
            HostSend.ITEM_CODE,
            HostSend.ITEM_NAME,
            HostSend.JAN,
            HostSend.ITF,
            HostSend.BUNDLE_ITF,
            HostSend.ENTERING_QTY,
            HostSend.BUNDLE_ENTERING_QTY,
            HostSend.PLAN_LOT_NO,
            HostSend.PLAN_QTY,
            HostSend.REASON_TYPE,
            HostSend.REASON_NAME,
            HostSend.USER_ID,
            HostSend.USER_NAME,
            HostSend.TERMINAL_NO,
        };

        // 取得フィールドのセット
        for (FieldName fld : collects)
        {
        	hkey.setCollect(fld);
        }

        // 検索条件のセット
        hkey.setSettingUnitKey(key);

        // ソート
        hkey.setStockIdOrder(true);
        hkey.setJobNoOrder(true);

        // 検索実行
        Entity[] readEnts = hsh.find(hkey);
        if (null == readEnts || readEnts.length == 0)
        {
            // 見つからなかった場合は、エラー
            throw new ScheduleException();
        }

        WorkListHandler wlh = new WorkListHandler(getConnection());
        String cname = getCallerName();

        String stock_id = "";
        int stock_qty = 0;
        int alloc_qty = 0;
        int work_qty = 0;
        for (Entity readEnt : readEnts)
        {
            WorkList wlEnt = new WorkList();
            wlEnt.setValueMap(readEnt.getValueMap());

            // 在庫情報のセット
            if (WorkList.JOB_TYPE_RETRIEVAL.equals(wlEnt.getJobType()) ||
                    WorkList.JOB_TYPE_NOPLAN_RETRIEVAL.equals(wlEnt.getJobType()))
            {
                // 出庫、予定外出庫の場合
                if (StringUtil.isBlank(stock_id) || !stock_id.equals(wlEnt.getStockId()))
                {
                    stock_id = wlEnt.getStockId();
                    
                    Stock stk = getStock(stock_id);
                    int total_qty = getTotalPlanQty(key, wlEnt.getStockId());
                    if (stk == null)
                    {
                        // 平置き作業の場合、在庫がなくなっていたら全数出庫
                        stock_qty = total_qty;
                        // 作業前の引当可能数
                        alloc_qty = total_qty;
                    }
                    else
                    {
                        stock_qty = stk.getStockQty() + total_qty;
                        // 作業前の引当可能数
                        alloc_qty = total_qty + stk.getAllocationQty();
                    }
                }
                else
                {
                    // 引当可能数の減算
                    alloc_qty -= work_qty;
                }
                
                work_qty = wlEnt.getPlanQty();
                
                // 在庫数、引当可能数のセット
                wlEnt.setAllocationQty(alloc_qty);
                wlEnt.setStockQty(stock_qty);
            }
            else if (WorkList.JOB_TYPE_STORAGE.equals(wlEnt.getJobType()) ||
                    WorkList.JOB_TYPE_NOPLAN_STORAGE.equals(wlEnt.getJobType()))
            {
                // 入庫、予定外入庫の場合
                if (StringUtil.isBlank(stock_id) || !stock_id.equals(wlEnt.getStockId()))
                {
                    stock_id = wlEnt.getStockId();
                    
                    Stock stk = getStock(stock_id);
                    int total_qty = getTotalPlanQty(key, wlEnt.getStockId());
                    if (stk == null)
                    {
                        // 平置き作業の場合、在庫情報が見つからなかったらエラー
                        throw new ScheduleException();
                    }
                    else
                    {
                        stock_qty = stk.getStockQty() - total_qty;
                        // 作業前の引当可能数
                        alloc_qty = stk.getAllocationQty() - total_qty;
                    }
                }
                else
                {
                    // 在庫数、引当可能数の加算
                    stock_qty += work_qty;
                    alloc_qty += work_qty;
                }
                
                work_qty = wlEnt.getPlanQty();
                
                // 在庫数、引当可能数のセット
                wlEnt.setAllocationQty(alloc_qty);
                wlEnt.setStockQty(stock_qty);
            }
            
            wlEnt.setRegistDate(new SysDate());
            wlEnt.setRegistPname(cname);

            // insert
            wlh.create(wlEnt);
        }
    }
    
    /**
     * 同一在庫を対象とした作業の合計実績数を取得します。
     * 
     * @param setting_ukey
     * @param stock_id
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected int getTotalPlanQty(String setting_ukey, String stock_id)
            throws ReadWriteException
    {
        HostSendSearchKey hkey = new HostSendSearchKey();
        
        hkey.setSettingUnitKey(setting_ukey);
        hkey.setStockId(stock_id);
        hkey.setPlanQtyCollect("SUM");
        
        HostSend[] hostsend = (HostSend[])(new HostSendHandler(getConnection())).find(hkey);
        
        return hostsend[0].getPlanQty();
    }
    
    /**
     * 在庫IDから在庫情報を取得します。<br>
     * 対象データが無い場合はnullを返します。
     * 
     * @param stock_id
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected Stock getStock(String stock_id)
            throws ReadWriteException
    {
        StockSearchKey skey = new StockSearchKey();
        
        skey.setStockId(stock_id);
        
        Stock[] stk = (Stock[])(new StockHandler(getConnection())).find(skey);
        
        if (stk == null || stk.length == 0)
        {
            return null;
        }
        
        return stk[0];
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
        return "$Id: NoPlanWorkOperator.java 5990 2009-11-17 11:40:10Z nagao $";
    }
}
