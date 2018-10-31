// $Id: StorageOperator.java 5342 2009-10-30 08:02:30Z kishimoto $
package jp.co.daifuku.wms.storage.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.common.Parameter.COMPLETION_FLAG_DECISION;
import static jp.co.daifuku.wms.base.entity.SystemDefine.HARDWARE_TYPE_LIST;
import static jp.co.daifuku.wms.base.entity.SystemDefine.HARDWARE_TYPE_RFT;
import static jp.co.daifuku.wms.base.entity.SystemDefine.JOB_TYPE_STORAGE;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_NOWWORKING;
import static jp.co.daifuku.wms.base.exception.OperatorException.ERR_ALREADY_UPDATED;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
import jp.co.daifuku.wms.base.common.OutParameter;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.StoragePlanController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.controller.WorkInfoController;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.db.BasicDatabaseFinder;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.storage.schedule.StorageInParameter;
import jp.co.daifuku.wms.storage.schedule.StorageOutParameter;


/**
 * 入庫作業のためのオペレータクラスです。
 *
 *
 * @version $Revision: 5342 $, $Date: 2009-10-30 17:02:30 +0900 (金, 30 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */
public class StorageOperator
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
    public StorageOperator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面用の入庫作業開始を行います。
     * 
     * @param param 入庫開始条件パラメータ
     * @return 情報なしのOutParameterインスタンス
     * @throws OperatorException 開始処理ができなかったときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     */
    public OutParameter webStart(StorageInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                OperatorException
    {
        // setup hardware type
        param.getWmsUserInfo().setHardwareType(HARDWARE_TYPE_LIST);

        // get and lock target records
        WorkInfoController wic = new WorkInfoController(getConnection(), getCaller());

        WorkInfo ent = new WorkInfo();
        ent.setConsignorCode(param.getConsignorCode());
        ent.setPlanDay(param.getStoragePlanDay());
        ent.setItemCode(param.getItemCode());

        WorkInfo[] twinfos = wic.lockStorageStart(ent);
        boolean recordsFound = !ArrayUtil.isEmpty(twinfos);

        if (recordsFound)
        {
            // target records found.
            int numCollect = WmsParam.MAX_RFT_JOBCOLLECT_UNLIMITED;
            OutParameter op = start(numCollect, twinfos, param.getWmsUserInfo());
            return op;
        }
        else
        {
            // no target found.
            // check cause
            wic.checkStorageStart(ent, param.getItemCode());

            // this will not reached.
            return null;
        }
    }

    /**
     * RFT用の入庫作業開始を行います。
     * 
     * @param param 入庫開始条件パラメータ
     * <ol>
     * 以下の項目が参照されます
     * <li>荷主コード
     * <li>入庫予定日
     * <li>入庫エリアNo
     * <li>入庫棚No
     * <li>ロットNo
     * <li>商品コード
     * <li>ユーザ情報 (ハードウエア区分を除く)
     * </ol>
     * 
     * @return 設定単位キー
     * @throws OperatorException 設定不可の場合に詳細が返されます。
     * @throws DuplicateOperatorException 重複データありのときにスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     */
    public OutParameter rftStart(StorageInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                DuplicateOperatorException,
                OperatorException
    {
        // setup hardware type
        param.getWmsUserInfo().setHardwareType(HARDWARE_TYPE_RFT);

        // get and lock target records
        ItemController itemc = new ItemController(getConnection(), getCaller());
        WorkInfoController wic = new WorkInfoController(getConnection(), getCaller());

        WorkInfo[] stWorkInfos = null;

        // create work info key entity from parameter
        WorkInfo keyWork = new WorkInfo();

        keyWork.setConsignorCode(param.getConsignorCode()); // 荷主
        keyWork.setPlanDay(param.getStoragePlanDay()); // 入庫予定日
        keyWork.setPlanAreaNo(param.getStorageAreaNo()); // エリア
        keyWork.setPlanLocationNo(param.getStorageLocation()); // 棚
        keyWork.setPlanLotNo(param.getLotNo()); // ロットNo.

        List<String> keyItemList = new ArrayList<String>();

        // loop by max scan code
        boolean workFound = false;
        for (int i = 0; i < ItemController.MAX_SCAN_INDEX; i++)
        {
            String inItemCode = param.getItemCode();

            // use item code if scan index is 0.
            String keyItem = (i == 0) ? inItemCode
                                     : itemc.getItemCode(param.getConsignorCode(), inItemCode, i);

            // item not found, then try next.
            if (StringUtil.isBlank(keyItem))
            {
                continue;
            }
            keyItemList.add(keyItem);

            // set current item code
            keyWork.setItemCode(keyItem);

            // try to lock with current item code
            stWorkInfos = wic.lockStorageStart(keyWork);
            workFound = !ArrayUtil.isEmpty(stWorkInfos);
            if (workFound)
            {
                // found work info
                break;
            }
        }

        if (workFound)
        {
            // target records found.
            checkDuplicate(stWorkInfos);

            // duplicate check OK.
            // max collect getting from WmsParam
            return start(WmsParam.MAX_RFT_JOBCOLLECT, stWorkInfos, param.getWmsUserInfo());
        }
        else
        {
            // if work does not found, check cause
            String[] items = (String[])keyItemList.toArray(new String[0]);

            // it should be throw OperatorException
            wic.checkStorageStart(keyWork, items);

            // this will not reached.
            return null;
        }
    }

    /**
     * 入庫作業完了処理を行います。
     * 
     * @param params 完了パラメータ
     * <ol>
     * 以下の項目が参照されます
     * <li>設定単位キー
     * <li>集約作業No
     * <li>完了数
     * <li>作業秒数
     * <li>完了フラグ
     * <li>入庫エリアNo
     * <li>入庫棚No
     * <li>ロットNo
     * <li>ユーザ情報
     * <li>商品コード
     * </ol>
     * 
     * @throws OperatorException 完了処理で異常が発生したとき、要素番号を返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws InvalidDefineException パラメータがStorageInParameter[]でないときスローされます。
     * @throws DataExistsException 集約作業完了時に作業情報が登録済みのときスローされます。
     * @throws NoPrimaryException 集約作業完了時に対象データが1件でないときスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     * @throws NotFoundException 対象作業データなし(他端末で更新された)
     */
    public void complete(StorageInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                InvalidDefineException,
                ScheduleException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException
    {
        WorkInfoController wic = new WorkInfoController(getConnection(), getCaller());

        for (int i = 0; i < params.length; i++)
        {
            StorageInParameter siparam = params[i];

            try
            {
                // lock at first
                if (i == 0)
                {
                    String settingKey = siparam.getSettingUnitKey();
                    wic.lockComplete(settingKey, JOB_TYPE_STORAGE);
                }

                completeCollectData(siparam);
            }
            catch (OperatorException e)
            {
                e.setErrorLineNo(i + 1);
                throw e;
            }
            catch (NotFoundException e)
            {
                OperatorException opex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
                opex.setErrorLineNo(i + 1);
                throw opex;
            }
        }
    }

    /**
     * 入庫作業キャンセルを行います。
     * 
     * @param param 設定単位キーが参照されます。
     * 
     * @throws OperatorException キャンセル処理に失敗したとき詳細が返されます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws InvalidDefineException パラメータがStorageInParameterでないときスローされます。
     */
    public void cancel(StorageInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                InvalidDefineException
    {
        // check parameter type
        String settingKey = param.getSettingUnitKey();
        try
        {
            WorkInfoController wic = new WorkInfoController(getConnection(), getCaller());

            // lock target records
            WorkInfo[] works = wic.lockComplete(settingKey, JOB_TYPE_STORAGE);
            // cancel work info
            wic.cancelWorkInfo(settingKey);

            // cancel storage plan
            StoragePlanController sp = new StoragePlanController(getConnection(), getCaller());
            sp.cancelPlan(works);
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(ERR_ALREADY_UPDATED);
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
     * 入出庫作業情報を集約して開始処理を行います。
     * 
     * @param maxCollect 集約作業No.の最大
     * @param works 入出庫作業情報
     * <ol>
     * 以下の項目が参照されます
     * <li>予定一意キー
     * <li>エリア
     * <li>棚
     * <li>ロットNo.
     * <li>作業No.
     * </ol>
     * <ol>
     * 以下の項目は更新されます
     * <li>設定単位キー
     * <li>集約作業No
     * </ol>
     * 
     * @param ui ユーザ情報
     * 
     * @return 設定単位キー
     * @throws OperatorException 開始処理ができなかったときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    protected OutParameter start(int maxCollect, WorkInfo[] works, WmsUserInfo ui)
            throws OperatorException,
                ReadWriteException
    {
        StorageOutParameter rParam = new StorageOutParameter();

        WorkInfoController wic = new WorkInfoController(getConnection(), getCaller());
        StoragePlanController sc = new StoragePlanController(getConnection(), getCaller());
        WMSSequenceHandler seqh = new WMSSequenceHandler(getConnection());

        // 設定単位キー採番
        String settingUnitKey = seqh.nextWorkInfoSetUkey();
        rParam.setSettingUnitKey(settingUnitKey);

        StorageInParameter saveParam = new StorageInParameter(ui);

        String currPlanKey = "";
        String collectJobNo = "";
        int numcoll = 0;
        for (WorkInfo currWork : works)
        {
            boolean same = saveParam.setKeys(currWork);

            if (!same)
            {
                // check max collect
                if (0 < maxCollect && ++numcoll > maxCollect)
                {
                    break;
                }
                // 集約作業Noの採番
                collectJobNo = seqh.nextWorkInfoCollectJobNo();
            }
            try
            {
                currWork.setSettingUnitKey(settingUnitKey);
                currWork.setCollectJobNo(collectJobNo);
                wic.startWorkInfo(currWork, ui);
            }
            catch (NotFoundException e)
            {
                throw new OperatorException(ERR_ALREADY_UPDATED);
            }
            String newPlanKey = currWork.getPlanUkey();
            if (!currPlanKey.equals(newPlanKey))
            {
                // next plan read, exec start
                try
                {
                    sc.startPlan(newPlanKey);
                }
                catch (NotFoundException e)
                {
                    throw new OperatorException(ERR_ALREADY_UPDATED);
                }
                currPlanKey = newPlanKey;
            }
        }
        return rParam;
    }

    /**
     * 集約作業完了処理を行います。<br>
     * 集約作業No.に該当するデータに対して入出庫作業情報を検索し、
     * 完了処理を行います。
     * 
     * @param param 集約作業No.<br>
     * <ol>
     * 以下の項目が参照されます
     * <li>集約作業No
     * <li>完了数
     * <li>作業秒数
     * <li>完了フラグ
     * <li>入庫エリアNo
     * <li>入庫棚No
     * <li>ロットNo
     * <li>ユーザ情報
     * <li>商品コード
     * </ol>
     * 
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 対象データが1件でないときスローされます。
     * @throws OperatorException 完了処理で問題があったときスローされます。
     * @throws DataExistsException すでに登録済みの時スローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     * @throws LockTimeOutException 在庫のロックタイムアウト
     */
    protected void completeCollectData(StorageInParameter param)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException,
                DataExistsException,
                ScheduleException,
                LockTimeOutException
    {
        // getting work info
        WorkInfoFinder wf = new WorkInfoFinder(getConnection());
        wf.open(true);

        try
        {
            StockController sc = new StockController(getConnection(), getCaller());

            WorkInfoSearchKey key = new WorkInfoSearchKey();
            key.setCollectJobNo(param.getCollectJobNo());
            key.setStatusFlag(STATUS_FLAG_NOWWORKING);
            key.setPlanUkeyOrder(true);
            key.setJobNoOrder(true);

            int numrec = wf.search(key, BasicDatabaseFinder.LIMIT_UNLIMTED);
            if (numrec == 0)
            {
                throw new OperatorException(ERR_ALREADY_UPDATED);
            }

            WorkInfoController wic = new WorkInfoController(getConnection(), getCaller());
            StoragePlanController spc = new StoragePlanController(getConnection(), getCaller());

            // getting work day
            WarenaviSystemController wsc = new WarenaviSystemController(getConnection(), getCaller());
            String systemWorkDay = wsc.getWorkDay();

            // pickup parameter
            int inResultQty = param.getResultQty();
            int inWorkSeconds = param.getWorkSeconds();
            String inCompFlag = param.getCompletionFlag();

            // get actual remain
            int actResultRemain = inResultQty;

            // init valiables
            int resultQty = 0;
            int shortage = 0;
            int workSec = 0;

            // loop for all work info
            while (wf.hasNext())
            {
                WorkInfo wient = (WorkInfo)(wf.getEntities(1))[0];
                int wiPlanQty = wient.getPlanQty();
                if (actResultRemain < wiPlanQty)
                {
                    resultQty = actResultRemain;
                    // ProcessFlag == COMPLETE ?
                    if (COMPLETION_FLAG_DECISION.equals(inCompFlag))
                    {
                        shortage = wiPlanQty - resultQty;
                    }
                }
                else
                {
                    resultQty = wiPlanQty;
                }
                actResultRemain -= resultQty;

                // setup result work info
                WorkInfo resultWorkinfo = new WorkInfo();
                if (0 < inWorkSeconds && resultQty != 0)
                {
                    float wsecd = (float)inWorkSeconds / (float)inResultQty;
                    workSec = Math.round(wsecd * resultQty);
                }

                resultWorkinfo.setWorkSecond(Math.round(workSec));
                resultWorkinfo.setResultAreaNo(param.getStorageAreaNo());
                resultWorkinfo.setResultLocationNo(param.getStorageLocation());
                resultWorkinfo.setResultLotNo(param.getLotNo());
                resultWorkinfo.setResultQty(resultQty);
                resultWorkinfo.setShortageQty(shortage);
                resultWorkinfo.setWorkDay(systemWorkDay);
                // update work info

                wic.completeWorkInfo(wient, resultWorkinfo, inCompFlag, param.getWmsUserInfo());
                // update plan
                completePlan(spc, systemWorkDay, resultQty, shortage, wient.getPlanUkey());
                // update stock
                if (0 < resultQty)
                {
                    updateStock(param, sc, systemWorkDay, resultQty);
                }
            }
            // end of loop
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(ERR_ALREADY_UPDATED);
        }
        finally
        {
            wf.close();
        }
    }

    /**
     * call StoragePlanController.completePlan().
     * 
     * @param spc controller fo Storage Plan
     * @param systemWorkDay 作業日
     * @param planResult 完了数
     * @param planShortage 欠品数
     * @param planUkey 設定単位キー
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NotFoundException 該当する予定データがない
     */
    protected void completePlan(StoragePlanController spc, String systemWorkDay, int planResult, int planShortage,
            String planUkey)
            throws ReadWriteException,
                NotFoundException
    {
        WorkInfo pwinfo = new WorkInfo();
        pwinfo.setPlanUkey(planUkey);
        pwinfo.setResultQty(planResult);
        pwinfo.setShortageQty(planShortage);
        pwinfo.setWorkDay(systemWorkDay);

        spc.completePlan(pwinfo);
    }

    /**
     * 入出庫作業情報に重複不可の項目がないかチェックします。
     * 
     * @param works チェック対象作業情報
     * @throws DuplicateOperatorException チェックの結果、重複項目があるときスローされます。<br>
     */
    protected void checkDuplicate(WorkInfo[] works)
            throws DuplicateOperatorException
    {
        DuplicateOperatorException ex = new DuplicateOperatorException();

        List dup = checkDuplicate(works, WorkInfo.PLAN_DAY);
        if (dup != null)
        {
            ex.setErrorCode(OperatorException.ERR_PLAN_DAY_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }

        dup = checkDuplicate(works, WorkInfo.PLAN_AREA_NO, WorkInfo.PLAN_LOCATION_NO);
        if (dup != null)
        {
            ex.setErrorCode(OperatorException.ERR_AREA_LOCATION_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }

        dup = checkDuplicate(works, WorkInfo.PLAN_LOT_NO);
        if (dup != null)
        {
            ex.setErrorCode(OperatorException.ERR_LOT_NO_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }
    }

    /**
     * update or cerate a stock.
     * 
     * @param param パラメータ
     * <ol>
     * 以下の項目が参照されます。
     * <li>入庫エリアNo
     * <li>入庫棚No
     * <li>ロットNo
     * <li>商品コード
     * <li>ユーザ情報
     * </ol>
     * 
     * @param sc 在庫コントローラ
     * @param systemWorkDay  作業日
     * @param resultQty 完了数
     * @throws ReadWriteException データベースアクセスエラー
     * @throws LockTimeOutException 在庫ロックタイムアウト
     * @throws OperatorException 入庫数オーバーフローで入庫作業が続行できない場合にスローされます。<br>
     * {@link StockController#initStorage(Stock, String, WmsUserInfo)}<br>
     * {@link StockController#addStorage(Stock, Stock, String, boolean, WmsUserInfo)}<br>
     * @throws ScheduleException 積み増し対象在庫が複数
     * @throws OperatorException 入庫数オーバーフロー,
     * @throws DataExistsException 在庫登録済み
     * @throws NoPrimaryException マスタデータ重複
     * @throws NotFoundException マスタデータなし
     */
    protected void updateStock(StorageInParameter param, StockController sc, String systemWorkDay, int resultQty)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                DataExistsException,
                NotFoundException,
                NoPrimaryException,
                ScheduleException
    {
        Stock stkey = new Stock();
        stkey.setAreaNo(param.getStorageAreaNo());
        stkey.setConsignorCode(param.getConsignorCode());
        stkey.setLocationNo(param.getStorageLocation());
        stkey.setLotNo(param.getLotNo());
        stkey.setItemCode(param.getItemCode());

        // lock target stock
        Stock[] stocks = sc.lock(stkey);

        WmsUserInfo ui = param.getWmsUserInfo();

        Stock newStock = stkey;
        newStock.setStockQty(resultQty);
        newStock.setStorageDay(systemWorkDay);
        newStock.setStorageDate(new SysDate());

        if (ArrayUtil.isEmpty(stocks))
        {
            // initial storage
            newStock.setAllocationQty(newStock.getStockQty());
            sc.initStorage(newStock, JOB_TYPE_STORAGE, ui, 0);
        }
        else
        {
            // add to exists storage
            if (1 < stocks.length)
            {
                // not unique
                throw new ScheduleException();
            }
            Stock currStock = stocks[0];
            // DFKLOOK v3.4 積み増しの場合は、入庫日時を更新しないように修正
            newStock.setStorageDay(null);
            newStock.setStorageDate(null);
            sc.addStorage(currStock, newStock, JOB_TYPE_STORAGE, false, ui, SystemDefine.DEFAULT_REASON_TYPE);
        }
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
        return "$Id: StorageOperator.java 5342 2009-10-30 08:02:30Z kishimoto $";
    }

    /**
     * 
     * エリアと棚の比較を行うためのクラスです。
     *
     *
     * @version $Revision: 5342 $, $Date: 2009-10-30 17:02:30 +0900 (金, 30 10 2009) $
     * @author  ss
     * @author  Last commit: $Author: kishimoto $
     */
    class AreaLocation
    {
        private String _area = "";

        private String _location = "";

        /**
         * エリアと棚を保持します。
         * 
         * @param area
         * @param location
         */
        public AreaLocation(String area, String location)
        {
            _area = area;
            _location = location;
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        public boolean equals(Object o)
        {
            if (o instanceof AreaLocation)
            {
                AreaLocation al2 = (AreaLocation)o;
                return _area.equals(al2._area) && _location.equals(_location);
            }
            return false;
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        public int hashCode()
        {
            return _area.hashCode() + _location.hashCode();
        }
    }
}
