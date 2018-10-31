// $Id: ReceivingOperator.java 5342 2009-10-30 08:02:30Z kishimoto $
package jp.co.daifuku.wms.receiving.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.common.Parameter.COMPLETION_FLAG_DECISION;
import static jp.co.daifuku.wms.base.entity.SystemDefine.HARDWARE_TYPE_LIST;
import static jp.co.daifuku.wms.base.entity.SystemDefine.HARDWARE_TYPE_RFT;
import static jp.co.daifuku.wms.base.entity.SystemDefine.JOB_TYPE_RECEIVING;
import static jp.co.daifuku.wms.base.entity.SystemDefine.STATUS_FLAG_NOWWORKING;
import static jp.co.daifuku.wms.base.exception.OperatorException.ERR_ALREADY_UPDATED;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
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
import jp.co.daifuku.wms.base.controller.ReceivingPlanController;
import jp.co.daifuku.wms.base.controller.ReceivingWorkInfoController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.ItfToJanConverter;
import jp.co.daifuku.wms.handler.db.BasicDatabaseFinder;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.receiving.schedule.ReceivingInParameter;
import jp.co.daifuku.wms.receiving.schedule.ReceivingOutParameter;


/**
 * 入荷作業のためのオペレータクラスです。 
 *
 * @version $Revision: 5342 $, $Date: 2009-10-30 17:02:30 +0900 (金, 30 10 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */
public class ReceivingOperator
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
     * コンストラクタ
     * コンストラクタ
     * @param conn DBコネクション
     * @param caller 呼び出し元クラス
     */
    public ReceivingOperator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面の入力情報を元に入荷処理を行います。
     * 
     * @param param 入力パラメータ
     * @return 出力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws OperatorException 他の端末で設定済みなど処理が続行できないときスローされます。
     */
    public OutParameter webStart(ReceivingInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                OperatorException
    {
        // setup hardware type
        param.getWmsUserInfo().setHardwareType(HARDWARE_TYPE_LIST);

        // get and lock target records
        ReceivingWorkInfoController wic = new ReceivingWorkInfoController(getConnection(), getCaller());

        ReceivingWorkInfo ent = new ReceivingWorkInfo();
        ent.setConsignorCode(param.getConsignorCode());
        ent.setPlanDay(param.getReceivingPlanDay());
        ent.setSupplierCode(param.getSupplierCode());
        ent.setReceiveTicketNo(param.getTicketNo());
        ent.setReceiveLineNo(param.getTicketLineNo());
        ent.setTcdcFlag(ReceivingWorkInfo.TCDC_FLAG_DC);

        ReceivingWorkInfo[] twinfos = wic.lockWebReceivingStart(ent);
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
            wic.checkReceivingStart(ent, new String[0]);

            // this will not reached.
            return null;
        }
    }

    /**
     * RFTの入力情報を元に入荷処理を行います。
     * <ol>
     * 以下の項目を参照します。
     * <li>荷主コード
     * <li>入荷予定日
     * <li>ロットNo.
     * <li>商品コード
     * <li>ユーザ情報(ハードウェアタイプ)
     * </ol>
     * 
     * @param param 入力パラメータ
     * @return 出力パラメータ
     * @throws OperatorException 他の端末で設定済みなど処理が続行できないとき通知されます。
     * @throws DuplicateOperatorException データが重複した場合に通知されます。
     * @throws ReadWriteException データベースアクセスエラーが発生したとき通知されます。
     * @throws NotFoundException 該当のデータがなかったとき通知されます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合に通知されます。
     */
    public OutParameter rftStart(ReceivingInParameter param)
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
        ReceivingWorkInfoController wic = new ReceivingWorkInfoController(getConnection(), getCaller());

        ReceivingWorkInfo[] vapRecvWorkInfos = null;

        // create work info key entity from parameter
        ReceivingWorkInfo keyWork = new ReceivingWorkInfo();

        // Consignor
        keyWork.setConsignorCode(param.getConsignorCode());
        // Planned Receiving Date
        keyWork.setPlanDay(param.getReceivingPlanDay());
        // Supplier Code
        keyWork.setSupplierCode(param.getSupplierCode());
        // Lot No.
        keyWork.setPlanLotNo(param.getLotNo());
        // TC/DC Flag
        keyWork.setTcdcFlag(ReceivingWorkInfo.TCDC_FLAG_DC);

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
            vapRecvWorkInfos = wic.lockReceivingStart(keyWork);
            workFound = !ArrayUtil.isEmpty(vapRecvWorkInfos);
            if (workFound)
            {
                // found work info
                break;
            }
        }

        // itf to jan
        String itfToJan = ItfToJanConverter.itfToJan(param.getItemCode());
        if (!workFound && param.getItfToJan() && !StringUtil.isBlank(itfToJan))
        {
            String keyItem = itemc.getItemCode(param.getConsignorCode(), itfToJan, 1);
            
            // item not found, then try next.
            if (!StringUtil.isBlank(keyItem))
            {
                keyItemList.add(keyItem);

                // set current item code
                keyWork.setItemCode(keyItem);

                // try to lock with current item code
                vapRecvWorkInfos = wic.lockReceivingStart(keyWork);
                workFound = !ArrayUtil.isEmpty(vapRecvWorkInfos);
            }
        }

        if (workFound)
        {
            // target records found.
            checkDuplicate(vapRecvWorkInfos);

            // duplicate check OK.
            // max collect getting from WmsParam
            return start(WmsParam.MAX_RFT_JOBCOLLECT, vapRecvWorkInfos, param.getWmsUserInfo());
        }
        else
        {
            // if work does not found, check cause
            String[] items = (String[])keyItemList.toArray(new String[0]);

            // it should be throw OperatorException
            wic.checkReceivingStart(keyWork, items);

            // this will not reached.
            return null;
        }
    }

    /**
     * RFT入荷作業ができるかどうかチェックを行います。
     * <ol>
     * 以下の項目を参照します。
     * <li>荷主コード
     * <li>入荷予定日
     * <li>ロットNo.
     * <li>商品コード
     * <li>ユーザ情報(ハードウェアタイプ)
     * </ol>
     * 
     * @param param 入力パラメータ
     * @return 出力パラメータ
     * @throws OperatorException 作業データの処理が正常に完了できない場合に通知される例外です。
     * @throws DuplicateOperatorException データが重複した場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     */
    public OutParameter rftQuery(ReceivingInParameter param)
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
        ReceivingWorkInfoController wic = new ReceivingWorkInfoController(getConnection(), getCaller());

        ReceivingWorkInfo[] vapRecvWorkInfos = null;

        // create work info key entity from parameter
        ReceivingWorkInfo keyWork = new ReceivingWorkInfo();

        // Consignor
        keyWork.setConsignorCode(param.getConsignorCode());
        // Planned Receiving Date
        keyWork.setPlanDay(param.getReceivingPlanDay());
        // Supplier Code
        keyWork.setSupplierCode(param.getSupplierCode());
        // Lot No.
        keyWork.setPlanLotNo(param.getLotNo());
        // TC/DC Flag
        keyWork.setTcdcFlag(ReceivingWorkInfo.TCDC_FLAG_DC);

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
            vapRecvWorkInfos = wic.lockReceivingStart(keyWork);
            workFound = !ArrayUtil.isEmpty(vapRecvWorkInfos);
            if (workFound)
            {
                // found work info
                break;
            }
        }

        // itf to jan
        String itfToJan = ItfToJanConverter.itfToJan(param.getItemCode());
        if (!workFound && param.getItfToJan() && !StringUtil.isBlank(itfToJan))
        {
            String keyItem = itemc.getItemCode(param.getConsignorCode(), itfToJan, 1);
            
            // item not found, then try next.
            if (!StringUtil.isBlank(keyItem))
            {
                keyItemList.add(keyItem);

                // set current item code
                keyWork.setItemCode(keyItem);

                // try to lock with current item code
                vapRecvWorkInfos = wic.lockReceivingStart(keyWork);
                workFound = !ArrayUtil.isEmpty(vapRecvWorkInfos);
            }
        }
        
        if (workFound)
        {
            // target records found.
            checkDuplicate(vapRecvWorkInfos);

            // duplicate check OK.
            return null;
        }
        else
        {
            // if work does not found, check cause
            String[] items = (String[])keyItemList.toArray(new String[0]);

            // it should be throw OperatorException
            wic.checkReceivingStart(keyWork, items);

            // this will not reached.
            return null;
        }
    }

    /**
     * 入荷完了処理を行います。
     * 
     * @param params 入力パラメータ
     * @throws OperatorException 作業データの処理が正常に完了できない場合に通知される例外です。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws InvalidDefineException 定義情報が異常な場合に使用する例外です。
     * @throws DataExistsException 情報を登録しようとした際に、既に同じ情報が登録済みの場合に発生する例外です。
     * @throws NoPrimaryException 定義情報が異常な場合にスローされます。
     * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知される例外です。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void complete(ReceivingInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                InvalidDefineException,
                ScheduleException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException
    {
        ReceivingWorkInfoController wic = new ReceivingWorkInfoController(getConnection(), getCaller());

        for (int i = 0; i < params.length; i++)
        {
            ReceivingInParameter siparam = params[i];

            try
            {
                // lock at first
                if (i == 0)
                {
                    String settingKey = siparam.getSettingUnitKey();
                    wic.lockReceivingComplete(settingKey, JOB_TYPE_RECEIVING);
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
     * 入荷予定情報のキャンセル処理を行います。
     * 
     * @param param 入力パラメータ
     * @throws OperatorException 作業データの処理が正常に完了できない場合に通知される例外です。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws InvalidDefineException 定義情報が異常な場合に使用する例外です。
     */
    public void cancel(ReceivingInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                InvalidDefineException
    {
        // check parameter type
        String settingKey = param.getSettingUnitKey();
        try
        {
            ReceivingWorkInfoController wic = new ReceivingWorkInfoController(getConnection(), getCaller());

            // lock target records
            ReceivingWorkInfo[] works = wic.lockReceivingComplete(settingKey, JOB_TYPE_RECEIVING);
            // cancel work info
            wic.cancelWorkInfo(settingKey);

            // cancel receiving plan
            ReceivingPlanController sp = new ReceivingPlanController(getConnection(), getCaller());
            sp.cancelPlan(works);
        }
        catch (NotFoundException e)
        {
            throw new OperatorException(ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 入荷エリアから指定された他のエリアへの在庫移動を行います。
     * 
     * @param inParam 入力パラメータ
     * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知される例外です。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     * @throws OperatorException 作業データの処理が正常に完了できない場合に通知される例外です。
     * @throws NoPrimaryException 定義情報が異常な場合にスローされます。
     */
    public void changeReceivingArea(ReceivingInParameter inParam)
            throws ScheduleException,
                ReadWriteException,
                LockTimeOutException,
                OperatorException,
                NoPrimaryException
    {
        try
        {
            StockController stockCtlr = new StockController(getConnection(), getClass());

            WmsUserInfo ui = inParam.getWmsUserInfo();
            String area = inParam.getNewReceivingAreaNo();

            // lock target stock
            Stock keyStock = new Stock();
            keyStock.setAreaNo(inParam.getReceivingAreaNo());
            keyStock.setLocationNo(WmsParam.DEFAULT_LOCATION_NO);
            keyStock.setConsignorCode(inParam.getConsignorCode());
            keyStock.setItemCode(inParam.getItemCode());
            keyStock.setLotNo(inParam.getLotNo());

            Stock[] targets = stockCtlr.lock(keyStock);
            if (ArrayUtil.isEmpty(targets))
            {
                throw new NotFoundException();
            }
            Stock target = targets[0];

            // check stock qty
            if (target.getAllocationQty() < inParam.getResultQty())
            {
                OperatorException ex = new OperatorException(OperatorException.ERR_SHORTAGE_ALLOCATION_QTY);
                ex.setDetail(String.valueOf(target.getAllocationQty()));
                throw ex;
            }

            stockCtlr.retrieval(target, SystemDefine.JOB_TYPE_MOVEMENT, inParam.getResultQty(), 0, null, true, ui);

            // ------------------------------------------------------------------------------------------
            // lock dest stock
            keyStock = new Stock();
            keyStock.setAreaNo(area);
            keyStock.setLocationNo(WmsParam.DEFAULT_LOCATION_NO);
            keyStock.setConsignorCode(inParam.getConsignorCode());
            keyStock.setItemCode(inParam.getItemCode());
            keyStock.setLotNo(inParam.getLotNo());

            Stock[] destStocks = stockCtlr.lock(keyStock);

            // target stock found?
            boolean initStorage = (ArrayUtil.isEmpty(destStocks));

            // set new value
            Stock newStock = (Stock)target.clone();
            newStock.setAreaNo(area);
            newStock.setLocationNo(WmsParam.DEFAULT_LOCATION_NO);
            newStock.setConsignorCode(inParam.getConsignorCode());
            newStock.setItemCode(inParam.getItemCode());
            newStock.setLotNo(inParam.getLotNo());

            newStock.setStockQty(inParam.getResultQty());
            newStock.setAllocationQty(inParam.getResultQty()); // ALL QTY NOT ALLOCATED

            if (initStorage)
            {
                stockCtlr.initStorage(newStock, SystemDefine.JOB_TYPE_MOVEMENT, ui, 0);
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

                stockCtlr.addStorage(toAddStock, newStock, SystemDefine.JOB_TYPE_MOVEMENT, false, ui,
                        SystemDefine.DEFAULT_REASON_TYPE);
            }
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
     * Starts a process, putting together receiving job information
     * @param maxCollect Maximum value of Intensive Work No.
     * @param works Receiving job information
     * <ol>
     * The following headings are referred
     * <li>Planned Unit Key
     * <li>??? Area
     * <li>??? Location
     * <li>Lot No.
     * <li>Job No.
     * </ol>
     * <ol>
     * The following headings will be updated
     * <li>Setting Unit Key
     * <li>Intensive Work No.
     * </ol>
     * 
     * @param ui User Information
     * 
     * @return Setting Unit Key
     * @throws OperatorException 他端末で更新済みの場合にスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    protected OutParameter start(int maxCollect, ReceivingWorkInfo[] works, WmsUserInfo ui)
            throws OperatorException,
                ReadWriteException
    {
        ReceivingOutParameter rParam = new ReceivingOutParameter();

        ReceivingWorkInfoController wic = new ReceivingWorkInfoController(getConnection(), getCaller());
        ReceivingPlanController sc = new ReceivingPlanController(getConnection(), getCaller());
        WMSSequenceHandler seqh = new WMSSequenceHandler(getConnection());

        // Gets Setting Unit Key
        String settingUnitKey = seqh.nextReceivingSetUkey();
        rParam.setSettingUnitKey(settingUnitKey);

        ReceivingInParameter saveParam = new ReceivingInParameter(ui);

        String currPlanKey = "";
        String collectJobNo = "";
        int numcoll = 0;
        for (ReceivingWorkInfo currWork : works)
        {
            boolean same = saveParam.setKeys(currWork);

            if (!same)
            {
                // check max collect
                if (0 < maxCollect && ++numcoll > maxCollect)
                {
                    break;
                }
                // Gets Intinsive work No.
                collectJobNo = seqh.nextReceivingCollectJobNo();
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
        rParam.setCollectJobNo(collectJobNo);
        return rParam;
    }

    /**
     * Process to complete intensive work
     * Searches receiving work information about data corresponding to Intensive Work No.
     * and do a completion process.
     * 
     * @param param Intensive Work No..<br>
     * <ol>
     * The following headings are referred
     * <li>Intensive Work No.
     * <li>Number of completion
     * <li>Number of seconds for a job
     * <li>Completion flag
     * <li>??? Receiving Area No
     * <li>??? Receiving Location No
     * <li>Lot No.
     * <li>User Information
     * <li>Item Code
     * </ol>
     * 
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NoPrimaryException 定義情報が異常な場合に使用する例外です。
     * @throws OperatorException 作業データの処理が正常に完了できない場合に通知される例外です。
     * @throws DataExistsException 情報を登録しようとした際に、既に同じ情報が登録済みの場合に発生する例外です。
     * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知される例外です。
     * @throws LockTimeOutException すでにレコードがロックされていた場合にスローされます。
     */
    protected void completeCollectData(ReceivingInParameter param)
            throws ReadWriteException,
                NoPrimaryException,
                OperatorException,
                DataExistsException,
                ScheduleException,
                LockTimeOutException
    {
        // getting work info
        ReceivingWorkInfoFinder wf = new ReceivingWorkInfoFinder(getConnection());
        wf.open(true);

        try
        {
            StockController sc = new StockController(getConnection(), getCaller());

            ReceivingWorkInfoSearchKey key = new ReceivingWorkInfoSearchKey();
            key.setCollectJobNo(param.getCollectJobNo());
            key.setStatusFlag(STATUS_FLAG_NOWWORKING);
            key.setJobNo(param.getJobNo());
            key.setPlanUkeyOrder(true);
            key.setJobNoOrder(true);

            int numrec = wf.search(key, BasicDatabaseFinder.LIMIT_UNLIMTED);
            if (numrec == 0)
            {
                throw new OperatorException(ERR_ALREADY_UPDATED);
            }

            ReceivingWorkInfoController wic = new ReceivingWorkInfoController(getConnection(), getCaller());
            ReceivingPlanController spc = new ReceivingPlanController(getConnection(), getCaller());

            // getting work day
            WarenaviSystemController wsc = new WarenaviSystemController(getConnection(), getCaller());
            String systemWorkDay = wsc.getWorkDay();

            // pickup parameter
            int inResultQty = param.getResultQty();
            int inWorkSeconds = param.getWorkSeconds();
            String inCompFlag = param.getCompletionFlag();

            // get actual remain
            int actResultRemain = inResultQty;

            // init variables
            int resultQty = 0;
            int shortage = 0;
            int workSec = 0;

            // loop for all work info
            while (wf.hasNext())
            {
                ReceivingWorkInfo wient = (ReceivingWorkInfo)(wf.getEntities(1))[0];
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
                    resultQty = actResultRemain;
                }
                actResultRemain -= resultQty;

                // setup result work info
                ReceivingWorkInfo resultWorkinfo = new ReceivingWorkInfo();
                if (0 < inWorkSeconds && resultQty != 0)
                {
                    float wsecd = (float)inWorkSeconds / (float)inResultQty;
                    workSec = Math.round(wsecd * resultQty);
                }

                resultWorkinfo.setWorkSecond(Math.round(workSec));
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
     * call ReceivingPlanController.completePlan().
     * 
     * @param spc controller fo Receiving Plan
     * @param systemWorkDay Work date
     * @param planResult Number of completion
     * @param planShortage Number of shortage
     * @param planUkey Setting Unit Key
     * @throws ReadWriteException DB access error
     * @throws NotFoundException There is no applicable data
     */
    protected void completePlan(ReceivingPlanController spc, String systemWorkDay, int planResult, int planShortage,
            String planUkey)
            throws ReadWriteException,
                NotFoundException
    {
        ReceivingWorkInfo pwinfo = new ReceivingWorkInfo();
        pwinfo.setPlanUkey(planUkey);
        pwinfo.setResultQty(planResult);
        pwinfo.setShortageQty(planShortage);
        pwinfo.setWorkDay(systemWorkDay);

        spc.completePlan(pwinfo);
    }

    /**
     * Detects duplication data about receiving work information.
     * @param works a target work information
     * @throws DuplicateOperatorException Thrown when there are duplicate data items.<br>
     */
    protected void checkDuplicate(ReceivingWorkInfo[] works)
            throws DuplicateOperatorException
    {
        DuplicateOperatorException ex = new DuplicateOperatorException();

        List dup = checkDuplicate(works, ReceivingWorkInfo.PLAN_DAY);
        if (dup != null)
        {
            ex.setErrorCode(OperatorException.ERR_PLAN_DAY_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }

        dup = checkDuplicate(works, ReceivingWorkInfo.SUPPLIER_CODE);
        if (dup != null)
        {
            ex.setErrorCode(OperatorException.ERR_SUPPLIER_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }
        
        dup = checkDuplicate(works, ReceivingWorkInfo.PLAN_LOT_NO);
        if (dup != null)
        {
            ex.setErrorCode(OperatorException.ERR_LOT_NO_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }
    }

    /**
     * update or create a stock.
     * 
     * @param param Parameter
     * <ol>
     * The following headings are referred。
     * <li>??? Receiving Area No
     * <li>??? Receiving Location No
     * <li>Lot No.
     * <li>Item Code
     * <li>User Information
     * </ol>
     * 
     * @param sc Receiving controller
     * @param systemWorkDay  Work date
     * @param resultQty Number of completion
     * 
     * @throws ReadWriteException DB access error
     * @throws LockTimeOutException Receiving lock times out
     * @throws OperatorException Thrown when receiving jobs cannot be continued because the number of receiving overflows.<BR>
     * {@link StockController#initReceiving(Stock, String, WmsUserInfo)}<br>
     * {@link StockController#addReceiving(Stock, Stock, String, boolean, WmsUserInfo)}<br>
     * @throws ScheduleException ??? 積み増し対象在庫が複数 (積み増し対象入荷が複数)
     * @throws OperatorException The number of Receiving overflows 
     * @throws DataExistsException Receiving Register done
     * @throws NoPrimaryException Duplicated Master Data
     * @throws NotFoundException No Master data
     */
    protected void updateStock(ReceivingInParameter param, StockController sc, String systemWorkDay, int resultQty)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                DataExistsException,
                NotFoundException,
                NoPrimaryException,
                ScheduleException
    {
        Stock stkey = new Stock();
        stkey.setAreaNo(param.getReceivingAreaNo());
        stkey.setConsignorCode(param.getConsignorCode());
        stkey.setLocationNo(param.getReceivingLocation());
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
            // initial receiving
            newStock.setAllocationQty(newStock.getStockQty());
            sc.initStorage(newStock, JOB_TYPE_RECEIVING, ui, 0);
        }
        else
        {
            // add to exists receiving
            if (1 < stocks.length)
            {
                // not unique
                throw new ScheduleException();
            }
            Stock currStock = stocks[0];
            // DFKLOOK v3.4 積み増しの場合は、入庫日時を更新しないように修正
            newStock.setStorageDay(null);
            newStock.setStorageDate(null);
            sc.addStorage(currStock, newStock, JOB_TYPE_RECEIVING, false, ui, SystemDefine.DEFAULT_REASON_TYPE);
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns revision about this class
     * @return String of revision
     */
    public static String getVersion()
    {
        return "$Id: ReceivingOperator.java 5342 2009-10-30 08:02:30Z kishimoto $";
    }

}
// End of class
