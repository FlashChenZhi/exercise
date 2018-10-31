// $Id: FaRetrievalRMCancelSCH.java 6710 2010-01-19 09:20:43Z shibamoto $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.asrs.schedule.FaRetrievalRMCancelSCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ExceptionHandler;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator;
import jp.co.daifuku.wms.asrs.dasch.FaRetrievalRMCancelDASCH;
import jp.co.daifuku.wms.asrs.dasch.FaRetrievalRMCancelDASCHParams;
import jp.co.daifuku.wms.asrs.exporter.FaRetrievalCancelListParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalCancelListHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalCancelListSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.RetrievalCancelList;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.retrieval.allocate.ReleaseAllocateOperator;

/**
 * RMキャンセルのスケジュール処理を行います
 *
 * @version $Revision: 6710 $, $Date:: 2010-01-19 18:20:43 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class FaRetrievalRMCancelSCH
        extends AbstractAsrsSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** キャンセルリストハンドラ */
    private RetrievalCancelListHandler _listHandler = null;
    

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public FaRetrievalRMCancelSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。<BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }
        
        CarryInfoFinder carryFinder = null;

        try
        {
            // キャンセル処理が可能かをチェック
            if (!checkCancelProcess(ps[0]))
            {
                return false;
            }
            
            carryFinder = new CarryInfoFinder(getConnection());
            
            // キャンセル対象の搬送データを取得
            if (searchCancelCarryInfo(carryFinder, ps[0]) == 0)
            {
                // 6003011=対象データはありませんでした。
                setMessage("6003011");
                return false;
            }
            
            // キャンセルできたかどうか
            boolean isCancel = false;
            // 印刷で例外が発生していないかどうか
            boolean hasPrintError = false;
            
            // 1件ずつキャンセル処理を行っていく
            while (carryFinder.hasNext())
            {
                CarryInfo[] carries = (CarryInfo[])carryFinder.getEntities(100);
                
                for (CarryInfo carry : carries)
                {
                    // 引当解除直前の搬送データを取得しなおす
                    CarryInfo[] ci = getCarryInfo(carry.getCarryKey(), ps[0]);
                    
                    // キャンセル対象搬送データがなくなっていた(処理中に開始されたなど)場合は処理しない
                    if (ci != null && ci.length != 0)
                    {
                        if (CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(ci[0].getRetrievalDetail()))
                        {
                            // 積増入庫の場合
                            // CarryCompleteOperator.drop()で参照するキャンセル要求区分をセットする
                            ci[0].setCancelRequest(CarryInfo.CANCEL_REQUEST_RELEASE);
                            ci[0].setMaintenanceTerminal(getTerminalNo());

                            // 積増の引当解除設定時は、予定在庫が実在庫になってないので実績を作成しないを指定する
                            CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());
                            carryCompOpe.drop(ci[0], false);
                        }
                        else
                        {
                            // 引当解除処理
                            ReleaseAllocateOperator releaseOpe = new ReleaseAllocateOperator(getConnection(), getClass());
                            releaseOpe.allocateClearOfCarryKey(ci[0]);
                        }
                        
                        isCancel = true;
                        
                        try
                        {
                            // 出力に失敗した場合は以降印刷処理を行わない
                            if (hasPrintError)
                            {
                                continue;
                            }
                            
                            insertCancelListData(ci);
                            
                        }
                        catch (Exception e)
                        {
                            hasPrintError = true;
                        }
                    }
                }
            }
            
            if (isCancel)
            {
                if (!hasPrintError && print(ps[0].getString(AREA_NO), ps[0].getString(RM_NO)))
                {
                    // 6001006=設定しました。
                    setMessage("6001006");
                }
                else
                {
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    setMessage("6007042");
                }
            }
            else
            {
                // 6003011=対象データはありませんでした。
                setMessage("6003011");
            }
        }
        catch (NotFoundException e)
        {
            // 6023115=他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return false;
        }
        finally
        {
            // キャンセルリストの全データを削除
            deleteCancelListData();
            // finder close
            closeFinder(carryFinder);
        }

        
        return true;
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
     * キャンセルリストのデータを全て削除します。
     *
     */
    protected void deleteCancelListData()
    {
        try
        {
            RetrievalCancelListHandler handler = new RetrievalCancelListHandler(getConnection());
            RetrievalCancelListSearchKey key = new RetrievalCancelListSearchKey();
            
            handler.drop(key);
        }
        catch (NotFoundException e)
        {
            // 問題ないのでスルー
        }
        catch (Exception e)
        {
            try
            {
                ExceptionHandler.getDisplayMessage(e, this);
            }
            catch (ValidateException ex)
            {
                // 発生しないのでスルー
            }
            
        }
    }
    
    /**
     * 搬送に紐付く作業情報をキャンセルリストの一時テーブルに登録します。
     * 
     * @param cis 紐付く作業情報を保持した搬送情報
     * @return true：登録成功、false：登録失敗
     * @throws Exception
     */
    protected boolean insertCancelListData(CarryInfo[] cis) throws Exception
    {
        boolean isWrite = false;
     
        if (_listHandler == null)
        {
            _listHandler = new RetrievalCancelListHandler(getConnection());
        }
        
        for (CarryInfo ci : cis)
        {
            String jobType = ci.getValue(WorkInfo.JOB_TYPE, "").toString();
            
            // 在庫確認だった場合は、印刷対象外とする
            if (jobType.equals(WorkInfo.JOB_TYPE_ASRS_INVENTORYCHECK))
            {
                continue;
            }

            RetrievalCancelList list = new RetrievalCancelList();
            list.setBatchNo(ci.getValue(WorkInfo.BATCH_NO, "").toString());
            list.setShipTicketNo(ci.getValue(WorkInfo.SHIP_TICKET_NO, "").toString());
            list.setShipLineNo(ci.getBigDecimal(WorkInfo.SHIP_LINE_NO, new BigDecimal(0)).intValue());
            list.setItemCode(ci.getValue(WorkInfo.ITEM_CODE, "").toString());
            list.setItemName(ci.getValue(Item.ITEM_NAME, "").toString());
            if (jobType.equals(WorkInfo.JOB_TYPE_RETRIEVAL))
            {
                list.setLotNo(ci.getValue(RetrievalPlan.PLAN_LOT_NO, "").toString());
            }
            else
            {
                list.setLotNo(ci.getValue(WorkInfo.PLAN_LOT_NO, "").toString());
            }
            // DASCHでは"max"で取得する
            list.setPlanQty(ci.getBigDecimal(RetrievalPlan.PLAN_QTY, new BigDecimal(0)).intValue());
            // DASCHでは"sum"で取得する
            list.setCancelQty(ci.getBigDecimal(WorkInfo.PLAN_QTY, new BigDecimal(0)).intValue());
            // DASCHでは"max"で取得する
            list.setResultQty(ci.getBigDecimal(RetrievalPlan.RESULT_QTY, new BigDecimal(0)).intValue());
            list.setJobType(jobType);

            _listHandler.create(list);
            
            isWrite = true;
            
        }
        
        return isWrite;
            
    }
    
    /**
     * 印刷処理を行います。
     * RetrievalCancelList情報を読み込み、出力を行います。
     * 
     * @param inputAreaNo 画面入力 エリアNo.（ヘッダに出力します）
     * @param inputRmNo 画面入力 RMNo.（全てを指定時以外にヘッダに出力します）
     * @return true：印刷成功、false：印刷失敗
     */
    protected boolean print(String inputAreaNo, String inputRmNo)
    {
        // get locale.
        DfkUserInfo ui = (DfkUserInfo)getUserInfo();
        Locale locale = getLocale();

        FaRetrievalRMCancelDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            dasch = new FaRetrievalRMCancelDASCH(getConnection(), this.getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaRetrievalRMCancelDASCHParams inparam = new FaRetrievalRMCancelDASCHParams();

            // check count.
            if (dasch.count(inparam) == 0)
            {
                return true;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("FaRetrievalCancelList", false);
            exporter.open();

            String rmNo = "";
            if (!WmsParam.ALL_AISLE_NO.equals(inputRmNo))
            {
                rmNo = inputRmNo;
            }
            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                FaRetrievalCancelListParams expparam = new FaRetrievalCancelListParams();
                expparam.set(FaRetrievalCancelListParams.DFK_DS_NO, outparam.get(FaRetrievalRMCancelDASCHParams.DFK_DS_NO));
                expparam.set(FaRetrievalCancelListParams.DFK_USER_ID, outparam.get(FaRetrievalRMCancelDASCHParams.DFK_USER_ID));
                expparam.set(FaRetrievalCancelListParams.DFK_USER_NAME, outparam.get(FaRetrievalRMCancelDASCHParams.DFK_USER_NAME));
                expparam.set(FaRetrievalCancelListParams.SYS_DAY, outparam.get(FaRetrievalRMCancelDASCHParams.SYS_DATE));
                expparam.set(FaRetrievalCancelListParams.SYS_TIME, outparam.get(FaRetrievalRMCancelDASCHParams.SYS_DATE));
                expparam.set(FaRetrievalCancelListParams.AREA_NO, inputAreaNo);
                expparam.set(FaRetrievalCancelListParams.RM_NO, rmNo);
                expparam.set(FaRetrievalCancelListParams.BATCH_NO, outparam.get(FaRetrievalRMCancelDASCHParams.BATCH_NO));
                expparam.set(FaRetrievalCancelListParams.TICKET_NO, outparam.get(FaRetrievalRMCancelDASCHParams.TICKET_NO));
                expparam.set(FaRetrievalCancelListParams.LINE_NO, outparam.get(FaRetrievalRMCancelDASCHParams.LINE_NO));
                expparam.set(FaRetrievalCancelListParams.ITEM_CODE, outparam.get(FaRetrievalRMCancelDASCHParams.ITEM_CODE));
                expparam.set(FaRetrievalCancelListParams.ITEM_NAME, outparam.get(FaRetrievalRMCancelDASCHParams.ITEM_NAME));
                expparam.set(FaRetrievalCancelListParams.LOT_NO, outparam.get(FaRetrievalRMCancelDASCHParams.LOT_NO));
                expparam.set(FaRetrievalCancelListParams.PLAN_QTY, outparam.get(FaRetrievalRMCancelDASCHParams.PLAN_QTY));
                expparam.set(FaRetrievalCancelListParams.CANCEL_QTY, outparam.get(FaRetrievalRMCancelDASCHParams.CANCEL_QTY));
                expparam.set(FaRetrievalCancelListParams.RESULT_QTY, outparam.get(FaRetrievalRMCancelDASCHParams.RESULT_QTY));
                expparam.set(FaRetrievalCancelListParams.WORK_TYPE, outparam.get(FaRetrievalRMCancelDASCHParams.WORK_TYPE));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
                return true;
            }
            catch (Exception ex)
            {
                return false;
            }

        }
        catch (Exception ex)
        {
            try
            {
                ExceptionHandler.getDisplayMessage(ex, this);
            }
            catch (ValidateException e)
            {
                // 発生しないのでスルー
            }
            return false;
        }
        finally
        {
            // コネクションのクローズはBusinessで行うため、ここではfinderのcloseは行わない
            if (exporter != null)
            {
                exporter.close();
            }
        }
    }

    /**
     * 指定されたRMNo.からキャンセル対象の搬送情報を検索します。<br>
     * 検索条件は<code>createCarryInfoCancelKey(ScheduleParams p)</code>によりセットされます。
     * 
     * @param finder <code>CarryInfoFinder</cade>
     * @param p <code>ScheduleParams</cade>
     * @return 実行可能ならtrueを返す。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkCancelProcess(ScheduleParams p)
            throws CommonException
    {
        // キャンセル対象データがあるか
        CarryInfoHandler ch = new CarryInfoHandler(getConnection());
        if (ch.count(createCarryInfoCancelKey(p)) == 0)
        {
            // 6003011=対象データはありませんでした。
            setMessage("6003011");
            return false;
        }
        
        // RMの切り離し中チェック
        if (!checkAisle(p))
        {
            // 6023316=RM状態が切り離し中でなければ、設定できません。
            setMessage("6023316");
            return false;        	
        }
        
        return true;
    }

    /**
     * キャンセル対象RMの切り離し中チェックを行います。
     * 
     * @param p <code>ScheduleParams</cade>
     * @return 対象のRMが全て切り離し中ならtrueを返す。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkAisle(ScheduleParams p)
            throws CommonException
    {
        // キャンセル対象データから作業RMを取得
        AisleHandler ah = new AisleHandler(getConnection());
        AisleSearchKey ak = new AisleSearchKey();
        
        // エリア
        ak.setKey(Area.AREA_NO, p.getString(AREA_NO));
        
        if (!WmsParam.ALL_AISLE_NO.equals(p.getString(RM_NO)))
        {
            // アイルステーションNo.
        	ak.setAisleNo(p.getString(RM_NO));
        }
        
        // 状態 0：搬送不可 以外
        ak.setStatus(SystemDefine.AISLE_STATUS_DISCONNECTED, "!=");
        
        // 結合条件
        ak.setJoin(Aisle.WH_STATION_NO, Area.WHSTATION_NO);
        
        Aisle[] aisles = (Aisle[])ah.find(ak);
        
        return (aisles.length == 0);
    }
    
    /**
     * 指定されたRMNo.からキャンセル対象の搬送情報を検索します。<br>
     * 検索条件は<code>createCarryInfoCancelKey(ScheduleParams p)</code>によりセットされます。
     * 
     * @param finder <code>CarryInfoFinder</cade>
     * @param p <code>ScheduleParams</cade>
     * @return 検索結果件数
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected int searchCancelCarryInfo(CarryInfoFinder finder, ScheduleParams p)
            throws CommonException
    {
        finder.open(true);
        
        CarryInfoSearchKey skey = createCarryInfoCancelKey(p);
        
        skey.setCarryKeyOrder(true);

        return finder.search(skey);
    }
    
    /**
     * 指定された搬送キーの搬送情報を取得します。<br>
     * 検索条件は<code>createCarryInfoCancelKey(ScheduleParams p)</code>によりセットされます。
     * 
     * @param key 搬送キー
     * @param p <code>ScheduleParams</cade>
     * @return 搬送情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected CarryInfo[] getCarryInfo(String key, ScheduleParams p)
            throws CommonException
    {
        CarryInfoHandler ch = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey skey = createCarryInfoCancelKey(p);
        
        skey.setCarryKey(key);
        
        // 紐づく作業情報も同時に取得する
        skey.setJoin(CarryInfo.CARRY_KEY, WorkInfo.SYSTEM_CONN_KEY);
        skey.setJoin(WorkInfo.PLAN_UKEY, "", RetrievalPlan.PLAN_UKEY, "(+)");
        skey.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);
        skey.setJoin(Aisle.WH_STATION_NO, Area.WHSTATION_NO);
        skey.setCollect(new FieldName(CarryInfo.STORE_NAME, FieldName.ALL_FIELDS));
        skey.setCollect(Aisle.AISLE_NO); // createCarryInfoCancelKeyの中で結合済
        skey.setCollect(Area.AREA_NO);
        skey.setCollect(Area.AREA_NAME);
        skey.setCollect(WorkInfo.BATCH_NO);
        skey.setCollect(WorkInfo.SHIP_TICKET_NO);
        skey.setCollect(WorkInfo.SHIP_LINE_NO);
        skey.setCollect(WorkInfo.ITEM_CODE);
        skey.setCollect(Item.ITEM_NAME);
        skey.setCollect(WorkInfo.PLAN_LOT_NO);
        skey.setCollect(RetrievalPlan.PLAN_LOT_NO);
        skey.setCollect(RetrievalPlan.PLAN_QTY);
        skey.setCollect(WorkInfo.PLAN_QTY);
        skey.setCollect(RetrievalPlan.RESULT_QTY);
        skey.setCollect(WorkInfo.JOB_TYPE);
        
        return (CarryInfo[])ch.find(skey);
    }
    
    /**
     * キャンセル対象となる搬送情報検索キーを生成して返します。<br>
     * <ol>以下の条件をセットします。
     * <li>アイル情報.アイルNo.
     * <li>搬送情報.搬送区分(出庫、棚間移動)
     * <li>搬送情報.搬送状態(引当、開始)
     * <li>搬送情報.アイルステーションNo. = アイル情報.ステーションNo.
     * </ol>
     * 
     * @param finder <code>CarryInfoFinder</cade>
     * @param p <code>ScheduleParams</cade>
     * @return 検索結果件数
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected CarryInfoSearchKey createCarryInfoCancelKey(ScheduleParams p)
    {
        CarryInfoSearchKey key = new CarryInfoSearchKey();
        
        // エリア
        key.setKey(Area.AREA_NO, p.getString(AREA_NO));
        
        if (!WmsParam.ALL_AISLE_NO.equals(p.getString(RM_NO)))
        {
            // アイルステーションNo.
            key.setKey(Aisle.AISLE_NO, p.getString(RM_NO));
        }
        
        // 搬送区分
        String[] carry_flag = {
            CarryInfo.CARRY_FLAG_RETRIEVAL,
            CarryInfo.CARRY_FLAG_RACK_TO_RACK,
        };
        key.setCarryFlag(carry_flag, true);
        
        // 搬送状態
        String[] cmd = {
            CarryInfo.CMD_STATUS_ALLOCATION,
            CarryInfo.CMD_STATUS_START,
        };
        key.setCmdStatus(cmd, true);
        
        // 結合
        key.setJoin(CarryInfo.AISLE_STATION_NO, Aisle.STATION_NO);
        key.setJoin(Aisle.WH_STATION_NO, Area.WHSTATION_NO);
        
        return key;
    }
    
    
    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
