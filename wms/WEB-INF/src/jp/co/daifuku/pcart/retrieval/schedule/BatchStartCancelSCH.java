// $Id: BatchStartCancelSCH.java 4403 2009-06-08 03:49:16Z ota $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.schedule.BatchStartCancelSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.retrieval.controller.PCTOrderNoController;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.LocationNumber;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTCustomerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTMaxWorkCountHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTMaxWorkCountSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.PCTCustomer;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTMaxWorkCount;
import jp.co.daifuku.wms.base.entity.PCTOrderInfo;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.PCTRetrieval;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.field.StoreMetaData;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;

/**
 * バッチ作業開始/ｷｬﾝｾﾙ処理をスケジュール処理を行います。<BR>
 * <BR>
 * @version $Revision: 4403 $, $Date: 2009/02/10 08:54:55 +0900$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class BatchStartCancelSCH
        extends AbstractSCH
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /** カラム定義 最終更新日時(<code>LAST_UPDATE_DATE</code>) */
    private static final FieldName _LAST_UPDATE_DATE = new FieldName("", "MAX(LAST_UPDATE_DATE)");

    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public BatchStartCancelSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    /**
     * 荷主コードを検索し、該当件数が一件の場合のみデータを返却します。
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE><BR>
     *          荷主コードと荷主名称を保持する。<BR>
     *          一件以上が該当、もしくはデータが無かった場合はnull値を返却します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        WarenaviSystemController wmsCont = new WarenaviSystemController(getConnection(), getClass());
        boolean masterFlag = wmsCont.hasPCTMasterPack();

        // 返却パラメータを生成
        Params param = new Params();
        // 検索結果の取得
        // PCTマスタフラグ
        param.set(MASTER_FLAG, masterFlag);

        // 設定した返却パラメータ返却
        return param;

    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // ハンドラインスタンス生成
        AbstractDBFinder finder = null;
        try
        {
            finder = new PCTRetPlanFinder(getConnection());
            finder.open(true);
            // 検索処理実行
            // 取得件数に応じてメッセージを設定
            if (0 == finder.search(createSearchKey(p)))
            {
                // 6003011=対象データはありませんでした。
                setMessage("6003011");
                return new ArrayList<Params>();
            }

            // エンティティを画面表示用にパラメータクラスにセットし返す
            return getDisplayData(finder);
        }
        finally
        {
            // 検索で使用したFinderをcloseする
            closeFinder(finder);
        }
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param startParams 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        PCTRetPlanFinder lockFinder = null;
        PCTRetPlanFinder workingFinder = null;

        int rowNo = 0;
        try
        {
            // 日次更新処理中のチェック
            if (!canStart())
            {
                return false;
            }
            // 取り込み中チェック
            if (isLoadData())
            {
                return false;
            }

            lockFinder = new PCTRetPlanFinder(getConnection());
            workingFinder = new PCTRetPlanFinder(getConnection());

            // バッチ開始日時を取得
            Date batchStartTime = WmsFormatter.toDateTime(DbDateUtil.getSystemDateTimeStamp());

            // オーダーNo.をNaviで採番するシステムかどうか取得する
            PCTRetrieval file = new PCTRetrieval();
            StoreMetaData meta = file.getStoreMetaData();
            FieldMetaData fmeta = meta.getFieldMetaData("PLAN_ORDER_NO");
            boolean createOrderNo = !fmeta.isEnabled();

            // Finder環境の初期化
            lockFinder.open(true);
            workingFinder.open(true);

            for (rowNo = 0; rowNo < ps.length; rowNo++)
            {
                //開始処理
                if (SystemDefine.SCH_FLAG_NOT_SCHEDULE.equals(ps[rowNo].getString(SCHEDULE_FLAG)))
                {
                    // ロック、他端末更新チェック
                    if (lock(lockFinder, ps[rowNo]) <= 0)
                    {
                        // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                        setMessage(WmsMessageFormatter.format(6023015, ps[rowNo].getRowIndex()));
                        setNgCellRow(ps[rowNo].getRowIndex());
                        return false;
                    }
                    // オーダーNo未指定情報の更新処理を行う。
                    if (createOrderNo)
                    {
                        if (!updatePlanBatchNo(getConnection(), ps[rowNo]))
                        {
                            return false;
                        }
                    }

                    // オーダーNo更新後の最新情報を取得します。
                    searchRetPlan(workingFinder, ps[rowNo]);

                    String tmpPlanOrder = "";
                    while (workingFinder.hasNext())
                    {
                        PCTRetPlan[] planEnt = (PCTRetPlan[])workingFinder.getEntities(1);

                        // PCT出庫作業情報データを作成
                        createPCTRetWorkInfo(planEnt[0], batchStartTime);
                        // 予定オーダーNo.が同一の場合は処理を行わない。        
                        if (!planEnt[0].getPlanOrderNo().equals(tmpPlanOrder))
                        {
                            // PCTオーダー情報データを作成
                            createPCTOrderInfo(planEnt[0], batchStartTime);
                        }

                        tmpPlanOrder = planEnt[0].getPlanOrderNo();

                    }
                    // PCT出庫予定情報更新処理を行う
                    updatePCTPlan(ps[rowNo].getString(BATCH_SEQ_NO));

                    // 6021021=開始しました。
                    setMessage(WmsMessageFormatter.format(6021021));

                }
                // キャンセル処理
                else if (SystemDefine.SCH_FLAG_SCHEDULE.equals(ps[rowNo].getString(SCHEDULE_FLAG)))
                {
                    // 対象情報のロック後最終チェックを行う。
                    // ロック、他端末更新チェック
                    if (lock(lockFinder, ps[rowNo]) <= 0)
                    {
                        // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                        setMessage(WmsMessageFormatter.format(6023015, ps[rowNo].getRowIndex()));
                        setNgCellRow(ps[rowNo].getRowIndex());
                        return false;
                    }

                    // 作業中データチェック
                    if (!workingCheck(ps[rowNo]))
                    {
                        // 6023628=No.{0} 作業中データが存在するため、キャンセルできません。
                        setMessage(WmsMessageFormatter.format(6023628, ps[rowNo].getRowIndex()));
                        setNgCellRow(ps[rowNo].getRowIndex());
                        return false;
                    }

                    // 完了データチェック
                    if (!completionCheck(ps[rowNo]))
                    {
                        // 6023629=No.{0} 既に完了したデータが存在するため、キャンセルできません。
                        setMessage(WmsMessageFormatter.format(6023629, ps[rowNo].getRowIndex()));
                        setNgCellRow(ps[rowNo].getRowIndex());
                        return false;
                    }

                    // PCT出庫作業情報データキャンセル処理
                    cancelPCTRetWorkInfo(ps[rowNo]);
                    // PCTオーダー情報データキャンセル処理
                    cancelPCTOrderInfo(ps[rowNo]);
                    // PCT出庫予定情報更新処理を行う
                    updatePCTPlan(ps[rowNo], createOrderNo);
                    // 6121004=設定しました。
                    setMessage(WmsMessageFormatter.format(6121004));
                }
            }
            return true;
        }
        catch (NotFoundException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, ps[rowNo].getRowIndex()));
            setNgCellRow(ps[rowNo].getRowIndex());
            return false;
        }
        catch (LockTimeOutException e)
        {
            // No.{0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, ps[rowNo].getRowIndex()));
            setNgCellRow(ps[rowNo].getRowIndex());
            return false;
        }
        finally
        {
            closeFinder(lockFinder);
            closeFinder(workingFinder);
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
     * 他の端末で既に変更されたかどうかの確認を行います。<BR>
     * パラメータにセットされている最終更新日時と現在のDBから取得した最終更新日時を比較します。<BR>
     * 比較の結果、双方の最終更新日時が等しい場合は他の端末で変更されていないとし、<BR>
     * 等しくない場合は他の端末で既に変更されていると見なします。<BR>
     * @param lockFinder    PCT出荷予定情報Finder
     * @param inPram 　PCT出庫用入力パラメーター  
     * @return データロックした件数を通知します。
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected int lock(PCTRetPlanFinder lockFinder, Params inPram)
            throws CommonException
    {
        PCTRetPlanSearchKey searchKey = new PCTRetPlanSearchKey();

        // データのロック
        searchKey.clear();
        // バッチNo.
        searchKey.setBatchNo(inPram.getString(BATCH_NO));
        // バッチSeqNo.
        searchKey.setBatchSeqNo(inPram.getString(BATCH_SEQ_NO));
        // 状態フラグ = 未開始
        searchKey.setStatusFlag(inPram.getString(STATUS_FLAG));
        // スケジュール処理フラグ 
        searchKey.setSchFlag(inPram.getString(SCHEDULE_FLAG));

        return (lockFinder.searchForUpdate(searchKey, HandlerSysDefines.WAIT_SEC_NOWAIT));
    }

    /**
     * オーダーNoが未指定情報に対して、オーダーNoの採番処理を行います。<BR>
     * @param conn    データベースとのコネクションオブジェクト
     * @param inParam 　PCT出庫用入力パラメーター
     * @return boollean 正常終了時true 異常発生時false
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected boolean updatePlanBatchNo(Connection conn, ScheduleParams inParam)
            throws CommonException
    {
        PCTRetPlanFinder planFinder = null;
        PCTRetPlanFinder planModifyFinder = null;
        try
        {
            // PCT出庫予定情報（予定バッチNo：未指定）取得用
            planFinder = new PCTRetPlanFinder(conn);
            PCTRetPlanHandler planHandler = new PCTRetPlanHandler(conn);
            PCTRetPlanSearchKey searchKey = new PCTRetPlanSearchKey();
            PCTRetPlanAlterKey alterKey = new PCTRetPlanAlterKey();

            // PCT作業コントローラクラス
            PCTOrderNoController wkOrderCtrl = new PCTOrderNoController(getConnection());

            // 検索条件クリア
            searchKey.clear();
            // バッチNo.
            searchKey.setBatchNo(inParam.getString(BATCH_NO));
            // バッチSeqNo.
            searchKey.setBatchSeqNo(inParam.getString(BATCH_SEQ_NO));
            // 状態フラグ = 未開始
            searchKey.setStatusFlag(PCTRetPlan.STATUS_FLAG_UNSTART);
            // スケジュール処理フラグ = 未スケジュール
            searchKey.setSchFlag(PCTRetPlan.SCH_FLAG_NOT_SCHEDULE);
            // 予定オーダーNo：未指定
            searchKey.setPlanOrderNo("");

            // 集約条件及び取得順序定義
            setGroupOrder(searchKey);

            planFinder.open(true);
            // 集約条件毎の対象情報を取得する。
            if (planFinder.search(searchKey) == 0)
            {
                return true;
            }

            planModifyFinder = new PCTRetPlanFinder(conn);
            planModifyFinder.open(true);

            while (planFinder.hasNext())
            {
                PCTRetPlan[] planGroup = (PCTRetPlan[])planFinder.getEntities(100);

                for (PCTRetPlan pGroup : planGroup)
                {
                    // 集約条件毎の対象情報件数を取得する。
                    PCTRetPlanSearchKey countKey = new PCTRetPlanSearchKey();

                    // 検索条件クリア
                    countKey.clear();
                    // バッチNo.
                    countKey.setBatchNo(inParam.getString(BATCH_NO));
                    // バッチSeqNo.
                    countKey.setBatchSeqNo(inParam.getString(BATCH_SEQ_NO));
                    // 状態フラグ = 未開始
                    countKey.setStatusFlag(PCTRetPlan.STATUS_FLAG_UNSTART);
                    // スケジュール処理フラグ = 未スケジュール
                    countKey.setSchFlag(PCTRetPlan.SCH_FLAG_NOT_SCHEDULE);
                    //出庫予定日
                    countKey.setPlanDay(pGroup.getPlanDay());
                    // 荷主コード
                    countKey.setConsignorCode(pGroup.getConsignorCode());
                    // 出荷先コード
                    countKey.setCustomerCode(pGroup.getCustomerCode());
                    // エリアNo
                    countKey.setPlanAreaNo(pGroup.getPlanAreaNo());

                    // 取得項目
                    // 予定一意キー
                    countKey.setPlanUkeyCollect();

                    // 取得順序
                    // 登録日時の昇順
                    countKey.setRegistDateOrder(true);
                    // 予定一意キーの昇順
                    countKey.setPlanUkeyOrder(true);

                    // 対象情報取得（トータル件数取得）
                    int orderCount = planModifyFinder.search(countKey);

                    // 対象のエリアNoより、最大作業数を取得します。
                    // 最大作業件数が取得できない場合は、開始不可とします。
                    int maxWorkCnt = getMaxDataCount(conn, pGroup.getPlanAreaNo());
                    if (maxWorkCnt <= 0)
                    {
                        // 6023626=No.{0} のバッチ内のエリア{1}はエリア最大作業数が取得できません。
                        setMessage(WmsMessageFormatter.format(6023626, inParam.getRowIndex(), pGroup.getPlanAreaNo()));
                        setNgCellRow(inParam.getRowIndex());
                        return false;
                    }

                    // 集約条件毎の対象情報件数より、１オーダー件数を計算します。
                    int[] countTbl = getOrderCountTable(orderCount, maxWorkCnt);

                    // 割り振るオーダー件数分ループ
                    for (int oneOrder : countTbl)
                    {
                        String planOrderNo = "";
                        // 1オーダー件数内の予定データ件数分ループ
                        for (int lc = 0; lc < oneOrder; lc++)
                        {
                            PCTRetPlan[] rPlan = (PCTRetPlan[])planModifyFinder.getEntities(1);

                            if (lc == 0)
                            {
                                // オーダーNo採番処理を行います。
                                planOrderNo =
                                        wkOrderCtrl.makePlanOrderNo(conn, pGroup.getConsignorCode(),
                                                pGroup.getCustomerCode(), pGroup.getConsignorName(),
                                                pGroup.getCustomerName(), getClass());
                            }

                            // 更新条件クリア
                            alterKey.clear();
                            // 条件
                            // 予定一意キーが一致
                            alterKey.setPlanUkey(rPlan[0].getPlanUkey());
                            // 更新値：予定オーダーNo
                            alterKey.updatePlanOrderNo(planOrderNo);
                            // 最終更新処理名
                            alterKey.updateLastUpdatePname(this.getClass().getSimpleName());

                            planHandler.modify(alterKey);
                        }
                    }
                }
            }
        }
        finally
        {
            closeFinder(planModifyFinder);
            closeFinder(planFinder);
        }

        return true;
    }

    /**
     * パラメータにセットされている情報にて、出庫開始情報の最新を取得します。<BR>
     * @param workingFinder    PCT出荷予定情報Finder
     * @param inPram 　PCT出庫用入力パラメーター
     * @return 対象出庫予定情報の件数
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected int searchRetPlan(PCTRetPlanFinder workingFinder, ScheduleParams inPram)
            throws CommonException
    {
        PCTRetPlanSearchKey searchKey = new PCTRetPlanSearchKey();

        // 検索条件のクリア
        searchKey.clear();
        // バッチNo.
        searchKey.setBatchNo(inPram.getString(BATCH_NO));
        // バッチSeqNo.
        searchKey.setBatchSeqNo(inPram.getString(BATCH_SEQ_NO));
        // 状態フラグ = 未開始
        searchKey.setStatusFlag(PCTRetPlan.STATUS_FLAG_UNSTART);
        // スケジュール処理フラグ = 未スケジュール
        searchKey.setSchFlag(PCTRetPlan.SCH_FLAG_NOT_SCHEDULE);
        // 予定オーダーNo順
        searchKey.setPlanOrderNoOrder(true);
        // 登録日時順
        searchKey.setRegistDateOrder(true);
        // 予定一意キー順
        searchKey.setPlanUkeyOrder(true);

        return (workingFinder.search(searchKey));
    }

    /**
     * PCT出庫予定情報を元にPCT出庫作業情報データを作成する
     * @param plan PCT出庫予定情報
     * @param batchStartTime バッチ開始日時
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected void createPCTRetWorkInfo(PCTRetPlan plan, Date batchStartTime)
            throws CommonException
    {
        // PCT出庫作業情報ハンドラ生成
        PCTRetWorkInfoHandler infoHandler = new PCTRetWorkInfoHandler(getConnection());
        // PCT作業コントローラクラス
        PCTOrderNoController wkOrderCtrl = new PCTOrderNoController(getConnection());

        String[] tmpString = new String[2];

        // PCT出庫作業Noの取得
        WMSSequenceHandler seqHandler = new WMSSequenceHandler(getConnection());
        String pctJobNo = seqHandler.nextPCTRetWorkInfoJobNo();
        // 検索結果でPCT出庫作業情報登録処理を行う。
        // PCT出庫作業情報エンティティクラス
        PCTRetWorkInfo workEnt = new PCTRetWorkInfo();
        // 登録項目(40項目)
        // 作業No.
        workEnt.setJobNo(pctJobNo);
        // 設定単位キー
        workEnt.setSettingUnitKey(null);
        // 集約作業No.
        workEnt.setCollectJobNo(pctJobNo);
        // 状態フラグ(未作業)
        workEnt.setStatusFlag(PCTRetWorkInfo.STATUS_FLAG_UNSTART);
        // ハードウェア区分(未作業) 
        workEnt.setHardwareType(PCTRetWorkInfo.HARDWARE_TYPE_UNSTART);
        // 予定一意キー
        workEnt.setPlanUkey(plan.getPlanUkey());
        // 在庫ID
        workEnt.setStockId(null);
        // システム接続キー 
        workEnt.setSystemConnKey(null);
        // 予定日
        workEnt.setPlanDay(plan.getPlanDay());
        // 荷主コード
        workEnt.setConsignorCode(plan.getConsignorCode());
        // 得意先コード
        workEnt.setRegularCustomerCode(plan.getRegularCustomerCode());
        // 出荷先コード
        workEnt.setCustomerCode(plan.getCustomerCode());
        // 出荷先分類コード
        workEnt.setCustomerCategory(plan.getCustomerCategory());
        // 出荷伝票No.
        workEnt.setShipTicketNo(plan.getShipTicketNo());
        // 出荷伝票行
        workEnt.setShipLineNo(plan.getShipLineNo());
        // 出荷伝票作業枝番
        workEnt.setShipBranchNo(plan.getBranchNo());
        // バッチNo.
        workEnt.setBatchNo(plan.getBatchNo());
        // バッチSeqNo.
        workEnt.setBatchSeqNo(plan.getBatchSeqNo());
        // オーダーNo.
        tmpString = wkOrderCtrl.dividesOrderNo(plan.getPlanOrderNo());
        workEnt.setOrderNo(tmpString[0]);
        // オーダーSeqNo.
        workEnt.setOrderSeq(tmpString[1]);
        // オーダー情報コメント
        workEnt.setOrderInfo(plan.getOrderInfo());
        // 予定オーダーNo.
        workEnt.setPlanOrderNo(plan.getPlanOrderNo());
        // 実績オーダーNo.
        workEnt.setResultOrderNo(plan.getPlanOrderNo());
        // 予定エリア
        workEnt.setPlanAreaNo(plan.getPlanAreaNo());
        // 予定ゾーン
        workEnt.setPlanZoneNo(plan.getPlanZoneNo());
        // 作業ゾーン
        workEnt.setWorkZoneNo(plan.getWorkZoneNo());
        // 予定棚
        workEnt.setPlanLocationNo(plan.getPlanLocationNo());
        // 棚分割処理
        AreaController areaCtlr = new AreaController(getConnection(), this.getClass());
        LocationNumber location = new LocationNumber(areaCtlr.getLocationStyle(plan.getPlanAreaNo()));
        location = new LocationNumber(areaCtlr.getLocationStyle(plan.getPlanAreaNo()));
        location.parseParam(plan.getPlanLocationNo());
        String locationStyle[] = location.getLocation();

        // 棚分割1
        if (locationStyle.length < 1)
        {
            workEnt.setLocSeparate1("");
        }
        else
        {
            workEnt.setLocSeparate1(locationStyle[0]);
        }
        // 棚分割2
        if (locationStyle.length < 2)
        {
            workEnt.setLocSeparate2("");
        }
        else
        {
            workEnt.setLocSeparate2(locationStyle[1]);
        }
        // 棚分割3
        if (locationStyle.length < 3)
        {
            workEnt.setLocSeparate3("");
        }
        else
        {
            workEnt.setLocSeparate3(locationStyle[2]);
        }
        // 棚分割4
        if (locationStyle.length < 4)
        {
            workEnt.setLocSeparate4("");
        }
        else
        {
            workEnt.setLocSeparate4(locationStyle[3]);
        }
        // 商品コード
        workEnt.setItemCode(plan.getItemCode());
        // 基準日付
        workEnt.setUseByDate(plan.getUseByDate());
        // アイテム情報コメント
        workEnt.setItemInfo(plan.getItemInfo());
        // 予定ロットNo.
        workEnt.setPlanLotNo(plan.getPlanLotNo());
        // 予定数
        workEnt.setPlanQty(plan.getPlanQty());
        // 実績数
        workEnt.setResultQty(plan.getResultQty());
        // 欠品数
        workEnt.setShortageQty(plan.getShortageQty());
        // 作業日
        workEnt.setWorkDay(null);
        // ユーザーID
        workEnt.setUserId(null);
        // 端末No.、RFTNo.
        workEnt.setTerminalNo(null);
        // 作業秒数
        workEnt.setWorkSecond(0);
        // 登録処理名
        workEnt.setRegistPname(this.getClass().getSimpleName());
        // 登録日時
        workEnt.setRegistDate(batchStartTime);
        // 最終更新処理名
        workEnt.setLastUpdatePname(this.getClass().getSimpleName());

        infoHandler.create(workEnt);
    }

    /**
     * PCT出庫予定情報を元にPCTオーダー情報データを作成する
     * @param plan PCT出庫予定情報
     * @param batchStartTime バッチ開始日時
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected void createPCTOrderInfo(PCTRetPlan plan, Date batchStartTime)
            throws CommonException
    {
        // PCTオーダー情報,エリアマスタハンドラ,エリアマスタ検索キー生成
        PCTOrderInfoHandler orderHandler = new PCTOrderInfoHandler(getConnection());

        // 検索結果でPCTオーダー情報登録処理を行う。        
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey planshKey = new PCTRetPlanSearchKey();

        // データのロック
        planshKey.clear();
        // バッチNo.
        planshKey.setBatchNo(plan.getBatchNo());
        // バッチSeqNo.
        planshKey.setBatchSeqNo(plan.getBatchSeqNo());
        // 予定オーダーNo.
        planshKey.setPlanOrderNo(plan.getPlanOrderNo());
        // 状態フラグ = 未開始
        planshKey.setStatusFlag(PCTRetPlan.STATUS_FLAG_UNSTART);
        // スケジュール処理フラグ = 未スケジュール
        planshKey.setSchFlag(PCTRetPlan.SCH_FLAG_NOT_SCHEDULE);
        // 最小作業ゾーン
        planshKey.setWorkZoneNoCollect("MIN");
        // 最小ゾーン
        planshKey.setPlanZoneNoCollect("MIN");
        // 最小棚No
        planshKey.setPlanLocationNoCollect("MIN");

        PCTRetPlan[] tmpEntity = (PCTRetPlan[])planHandler.find(planshKey);

        // PCTオーダー情報エンティティクラス
        PCTOrderInfo orderEnt = new PCTOrderInfo();

        // 登録項目(29項目)
        // 作業開始日時
        orderEnt.setWorkStarttime(null);
        // 作業終了日時
        orderEnt.setWorkEndtime(null);
        // 状態フラグ
        orderEnt.setStatusFlag(PCTOrderInfo.STATUS_FLAG_UNSTART);
        // ユーザーID
        orderEnt.setUserId(null);
        // 端末No.、RFTNo.            
        orderEnt.setTerminalNo(null);
        // 荷主コード
        orderEnt.setConsignorCode(plan.getConsignorCode());
        // 荷主名称
        orderEnt.setConsignorName(plan.getConsignorName());
        // エリアNo.
        orderEnt.setAreaNo(plan.getPlanAreaNo());
        // エリア名称
        AreaController aControl = new AreaController(getConnection(), getClass());
        orderEnt.setAreaName(aControl.getAreaName(plan.getPlanAreaNo()));
        // バッチNo.            
        orderEnt.setBatchNo(plan.getBatchNo());
        // バッチSeqNo.            
        orderEnt.setBatchSeqNo(plan.getBatchSeqNo());
        // 得意先コード
        orderEnt.setRegularCustomerCode(plan.getRegularCustomerCode());
        // 得意先名称
        orderEnt.setRegularCustomerName(plan.getRegularCustomerName());
        // 出荷先コード
        orderEnt.setCustomerCode(plan.getCustomerCode());
        // 出荷先名称
        orderEnt.setCustomerName(plan.getCustomerName());
        // 出荷先優先度
        orderEnt.setCustomerPriority(getCustPriority(plan));
        // 予定オーダーNo.
        orderEnt.setPlanOrderNo(plan.getPlanOrderNo());
        // 実績オーダーNo.
        orderEnt.setResultOrderNo(plan.getPlanOrderNo());
        // 設定単位キー
        orderEnt.setSettingUnitKey(null);
        // 作業数量
        orderEnt.setWorkQty(0);
        // 作業数量(バラ数)
        orderEnt.setPieceQty(0);
        // 作業回数(明細数)
        orderEnt.setWorkCnt(0);
        // 集品箱数
        orderEnt.setBoxCnt(0);
        // 作業時間(秒)
        orderEnt.setWorkTime(0);
        // 実作業時間(秒)
        orderEnt.setRealWorkTime(0);
        // ミススキャン数
        orderEnt.setMissScanCnt(0);
        // 最小作業ゾーン
        orderEnt.setMinWorkZoneNo(tmpEntity[0].getWorkZoneNo());
        // 最小ゾーン
        orderEnt.setMinZoneNo(tmpEntity[0].getPlanZoneNo());
        // 最小棚No.
        orderEnt.setMinLocationNo(tmpEntity[0].getPlanLocationNo());
        // 登録処理名
        orderEnt.setRegistPname(this.getClass().getSimpleName());
        // 登録日時
        orderEnt.setRegistDate(batchStartTime);
        // 最終更新処理名
        orderEnt.setLastUpdatePname(this.getClass().getSimpleName());

        orderHandler.create(orderEnt);
    }

    /**
     * PCT出庫予定情報を更新する
     * @param batchSeqNo バッチSeqNo.
     * @param batchStartTime バッチ開始日時
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected void updatePCTPlan(String batchSeqNo)
            throws CommonException
    {
        // PCT出庫予定情報ハンドラ類のインスタンス生成
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanAlterKey alterKey = new PCTRetPlanAlterKey();

        // 検索条件
        // バッチSeqNo.
        alterKey.setBatchSeqNo(batchSeqNo);
        // スケジュール処理フラグ　＝　未スケジュール
        alterKey.setSchFlag(PCTRetPlan.SCH_FLAG_NOT_SCHEDULE);
        // 状態フラグ = 未開始
        alterKey.setStatusFlag(PCTRetPlan.STATUS_FLAG_UNSTART);
        // 更新項目：スケジュール状態フラグ　スケジュール済みに更新
        alterKey.updateSchFlag(SystemDefine.SCH_FLAG_SCHEDULE);
        // 最終更新処理名
        alterKey.updateLastUpdatePname(this.getClass().getSimpleName());

        planHandler.modify(alterKey);
    }

    /**
     * 状態フラグが作業中のDNPCTRETPLANのカウントを行う
     * @param inPram パラメータ情報
     * @return boolean 該当しなければtrue、1以上(該当有り)ならfalseを返す
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected boolean workingCheck(ScheduleParams inPram)
            throws CommonException
    {
        // PCT出庫予定情報ハンドラ類生成
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey planSearchKey = new PCTRetPlanSearchKey();
        // 条件
        // バッチNo.
        planSearchKey.setBatchNo(inPram.getString(BATCH_NO));
        // バッチSeqNo.
        planSearchKey.setBatchSeqNo(inPram.getString(BATCH_SEQ_NO));
        // 状態フラグ:作業中
        planSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
        // カウントを取得
        int count = planHandler.count(planSearchKey);

        if (count > 0)
        {
            return false;
        }
        return true;
    }

    /**
     * 状態フラグが完了のDNPCTRETPLANのカウントを行う
     * @param inPram パラメータ情報
     * @return boolean 該当しなければtrue、1以上(該当有り)ならfalseを返す
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected boolean completionCheck(ScheduleParams inPram)
            throws CommonException
    {
        // PCT出庫予定情報ハンドラ類生成
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey planSearchKey = new PCTRetPlanSearchKey();
        // 条件
        // バッチNo.
        planSearchKey.setBatchNo(inPram.getString(BATCH_NO));
        // バッチSeqNo.
        planSearchKey.setBatchSeqNo(inPram.getString(BATCH_SEQ_NO));
        // 状態フラグ:完了
        planSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
        // カウントを取得
        int count = planHandler.count(planSearchKey);

        if (count > 0)
        {
            return false;
        }
        return true;
    }

    /**
     * PCT出庫予定情報を元にPCT出庫作業情報データのキャンセル処理を行う
     * @param inPram パラメータ情報
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected void cancelPCTRetWorkInfo(ScheduleParams inPram)
            throws CommonException
    {
        // PCT出庫作業情報ハンドラ類生成
        PCTRetWorkInfoHandler infoHandler = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoSearchKey infoSearchKey = new PCTRetWorkInfoSearchKey();

        // PCT出庫作業情報キャンセル処理を行う
        infoSearchKey.clear();
        // 削除条件
        // バッチNo.
        infoSearchKey.setBatchNo(inPram.getString(BATCH_NO));
        // バッチSeqNo.
        infoSearchKey.setBatchSeqNo(inPram.getString(BATCH_SEQ_NO));

        // 削除
        infoHandler.drop(infoSearchKey);
    }

    /**
     * PCT出庫予定情報を元にPCTオーダー情報データのキャンセル処理を行う
     * @param inPram パラメータ情報
     * @throws CommonException  処理中に何らかの例外が発生した場合にthrowします。
     */
    protected void cancelPCTOrderInfo(ScheduleParams inPram)
            throws CommonException
    {
        // PCTオーダー情報ハンドラ類生成
        PCTOrderInfoHandler orderHandler = new PCTOrderInfoHandler(getConnection());
        PCTOrderInfoSearchKey orderSearchKey = new PCTOrderInfoSearchKey();

        // PCTオーダー情報キャンセル処理を行う
        orderSearchKey.clear();
        // 削除条件
        // バッチNo.
        orderSearchKey.setBatchNo(inPram.getString(BATCH_NO));
        // バッチSeqNo.
        orderSearchKey.setBatchSeqNo(inPram.getString(BATCH_SEQ_NO));

        // 削除
        orderHandler.drop(orderSearchKey);
    }

    /**
     * PCT出庫予定情報を更新する
     * @param inPram パラメータ情報
     * @param createOrderNo オーダーNo.採番フラグ（オーダーを採番する場合はtrue、上位指定の場合はfalse）
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected void updatePCTPlan(ScheduleParams inPram, boolean createOrderNo)
            throws CommonException
    {
        // PCT出庫予定情報ハンドラ類のインスタンス生成
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanAlterKey alterKey = new PCTRetPlanAlterKey();

        // スケジュール処理フラグ　＝　スケジュール済み
        alterKey.setSchFlag(SystemDefine.SCH_FLAG_SCHEDULE);
        // 状態フラグ　＝未開始
        alterKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        // バッチNo.
        alterKey.setBatchNo(inPram.getString(BATCH_NO));
        // バッチSeqNo.
        alterKey.setBatchSeqNo(inPram.getString(BATCH_SEQ_NO));
        // 更新項目
        // スケジュール処理フラグ　未スケジュールに更新
        alterKey.updateSchFlag(SystemDefine.SCH_FLAG_NOT_SCHEDULE);
        // 予定オーダーNo. オーダー採番時はブランクに更新
        if (createOrderNo)
        {
            alterKey.updatePlanOrderNo("");
        }
        // 最終更新処理名
        alterKey.updateLastUpdatePname(this.getClass().getSimpleName());

        // 更新
        planHandler.modify(alterKey);
    }

    /**
     * オーダーNo採番用の集約条件及び取得順序を定義します。<BR>
     * 変更を容易にするため、専用Methodを作成。
     * @param sKey 　PCT出庫予定検索用SearchKey
     */
    protected void setGroupOrder(PCTRetPlanSearchKey sKey)
    {
        // 集約条件
        //出庫予定日
        sKey.setPlanDayGroup();
        // 荷主コード
        sKey.setConsignorCodeGroup();
        // 荷主名称
        sKey.setConsignorNameCollect("MAX");
        // 出荷先コード
        sKey.setCustomerCodeGroup();
        // 出荷先名称
        sKey.setCustomerNameCollect("MAX");
        // バッチSeqNo
        sKey.setBatchSeqNoGroup();
        // エリアNo
        sKey.setPlanAreaNoGroup();

        // 取得順序
        //出庫予定日
        sKey.setPlanDayOrder(true);
        // 荷主コード
        sKey.setConsignorCodeOrder(true);
        // 出荷先コード
        sKey.setCustomerCodeOrder(true);
        // バッチSeqNo
        sKey.setBatchSeqNoOrder(true);
        // エリアNo
        sKey.setPlanAreaNoOrder(true);

        // 取得項目
        //出庫予定日
        sKey.setPlanDayCollect();
        // 荷主コード
        sKey.setConsignorCodeCollect();
        // 出荷先コード
        sKey.setCustomerCodeCollect();
        // バッチSeqNo
        sKey.setBatchSeqNoCollect();
        // エリアNo
        sKey.setPlanAreaNoCollect();
    }

    /**
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        PCTRetPlanSearchKey searchKey = new PCTRetPlanSearchKey();

        // 取得項目
        // 最終更新日時の最大値
        searchKey.setLastUpdateDateCollect("MAX");
        // バッチNo.
        searchKey.setBatchNoCollect("");
        // バッチSeqNo.
        searchKey.setBatchSeqNoCollect("");
        // 状態フラグ
        searchKey.setStatusFlagCollect("MIN");
        // スケジュールフラグ
        searchKey.setSchFlagCollect("");
        // 実績報告区分
        searchKey.setReportFlagCollect("");

        // 集約条件
        // バッチNo.でグループ化
        searchKey.setBatchNoGroup();
        // バッチSeqNo.でグループ化
        searchKey.setBatchSeqNoGroup();
        // スケジュールフラグ
        searchKey.setSchFlagGroup();
        // 報告フラグ
        searchKey.setReportFlagGroup();

        // 表示順
        // 報告フラグ
        searchKey.setReportFlagOrder(true);
        // 最終更新日時で昇順
        searchKey.setOrder(_LAST_UPDATE_DATE, true);
        // バッチNo.で昇順
        searchKey.setBatchNoOrder(true);
        // バッチSeqNo.で昇順
        searchKey.setBatchSeqNoOrder(true);

        // 検索条件
        // 状態フラグ
        searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws CommonException 
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws CommonException
    {
        // オーダーNo.をNaviで採番するシステムかどうか取得する
        PCTRetrieval file = new PCTRetrieval();
        StoreMetaData meta = file.getStoreMetaData();
        FieldMetaData fmeta = meta.getFieldMetaData("PLAN_ORDER_NO");
        boolean createOrderNo = !fmeta.isEnabled();

        List<Params> result = new ArrayList<Params>();

        while (finder.hasNext())
        {

            // 検索結果を100件づつ取得し、ファイルへ出力していく。
            PCTRetPlan[] plan = (PCTRetPlan[])finder.getEntities(100);

            for (PCTRetPlan plans : plan)
            {
                Params param = new Params();

                // バッチNo
                param.set(BATCH_NO, plans.getBatchNo());
                // バッチSeqNo
                param.set(BATCH_SEQ_NO, plans.getBatchSeqNo());
                // 最終更新日時
                param.set(LAST_UPDATE_DATE, plans.getLastUpdateDate());
                // オーダー数
                if (createOrderNo)
                {
                    // eWareNaviでオーダーNo.を採番する場合はオーダー件数を表示しない
                    param.set(ORDER_QTY, 1);
                }
                else
                {
                    // オーダー数取得メソッド呼び出し
                    param.set(ORDER_QTY, getOrderCount(plans));
                }
                // 行数取得メソッド呼び出し
                param.set(LINE_NO, getLineCount(plans));

                // 状態
                // スケジュールフラグ = 未開始の場合
                if (SystemDefine.SCH_FLAG_NOT_SCHEDULE.equals(plans.getSchFlag()))
                {
                    // 未開始
                    param.set(STATUS, DisplayResource.getPCTStatus(SystemDefine.SCH_FLAG_NOT_SCHEDULE, "", ""));
                }
                // スケジュールフラグ = 開始済 状態 = 未開始
                else if (SystemDefine.SCH_FLAG_SCHEDULE.equals(plans.getSchFlag())
                        && SystemDefine.STATUS_FLAG_UNSTART.equals(plans.getStatusFlag()))
                {
                    // 開始済
                    param.set(STATUS, DisplayResource.getPCTStatus(SystemDefine.SCH_FLAG_SCHEDULE,
                            SystemDefine.STATUS_FLAG_UNSTART, ""));
                }
                // スケジュールフラグ = 開始済　状態 = 作業中
                else if (SystemDefine.SCH_FLAG_SCHEDULE.equals(plans.getSchFlag())
                        && SystemDefine.STATUS_FLAG_NOWWORKING.equals(plans.getStatusFlag()))
                {
                    // 作業中
                    param.set(STATUS, DisplayResource.getPCTStatus(SystemDefine.SCH_FLAG_SCHEDULE,
                            SystemDefine.STATUS_FLAG_NOWWORKING, ""));
                }
                // 完了場合
                else if (SystemDefine.SCH_FLAG_SCHEDULE.equals(plans.getSchFlag())
                        && SystemDefine.STATUS_FLAG_COMPLETION.equals(plans.getStatusFlag()))
                {
                    // 報告フラグ = 未報告
                    if (SystemDefine.REPORT_FLAG_NOT_REPORT.equals(plans.getReportFlag()))
                    {
                        param.set(STATUS, DisplayResource.getPCTStatus("", "", SystemDefine.REPORT_FLAG_NOT_REPORT));
                    }
                    else
                    {
                        param.set(STATUS, DisplayResource.getPCTStatus("", "", SystemDefine.REPORT_FLAG_REPORT));

                    }
                }
                // 単重量未登録フラグ
                boolean unregistFlag = getWeightUnregist(plans.getBatchNo(), plans.getBatchSeqNo());
                
                param.set(WEIGHT_UNREGISTERED, DisplayResource.getWeightFlag(unregistFlag));
                // 未登録フラグ
                param.set(WEIGHT_FLAG, unregistFlag);
                // オーダーNo.採番フラグ
                param.set(ORDER_NO_NUMBERING_FLAG, createOrderNo);
                // 状態フラグ
                param.set(STATUS_FLAG, plans.getStatusFlag());
                // スケジュールフラグ
                param.set(SCHEDULE_FLAG, plans.getSchFlag());
                // 報告フラグ
                param.set(REPORT_FLAG, plans.getReportFlag());

                result.add(param);
            }
        }
        return result;
    }

    /**
     * オーダー数のカウントを取得する
     * @param inParam entity情報
     * @return count オーダー数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected int getOrderCount(PCTRetPlan inParam)
            throws ReadWriteException
    {
        // PCT出庫予定情報からバッチNo.、バッチSeqNo.別のオーダー数を取得
        PCTRetPlanHandler pHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey skey = new PCTRetPlanSearchKey();
        PCTRetPlan[] plan = null;

        // 検索条件セット
        // オーダーNo(DISTINCT)
        skey.setPlanOrderNoCollect("DISTINCT");
        // バッチNo.
        skey.setBatchNo(inParam.getBatchNo());
        // バッチSeqNo.
        skey.setBatchSeqNo(inParam.getBatchSeqNo());
        // スケジュール状態フラグ 
        skey.setSchFlag(inParam.getSchFlag());
        // 状態フラグ 
        skey.setStatusFlag(inParam.getStatusFlag());

        plan = (PCTRetPlan[])pHandler.find(skey);

        return plan.length;
    }

    /**
     * 行数のカウントを取得する
     * @param inParam パラメータ情報
     * @return count バッチNoのカウント
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected int getLineCount(PCTRetPlan inParam)
            throws ReadWriteException
    {
        // PCT出庫予定情報からバッチNo.、バッチSeqNo.別のレコード数を取得
        PCTRetPlanHandler pHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey skey = new PCTRetPlanSearchKey();

        // 検索条件セット
        // バッチNo.
        skey.setBatchNo(inParam.getBatchNo());
        // バッチSeqNo.
        skey.setBatchSeqNo(inParam.getBatchSeqNo());
        // スケジュール状態フラグ 
        skey.setSchFlag(inParam.getSchFlag());
        // 状態フラグ 
        skey.setStatusFlag(inParam.getStatusFlag());

        return pHandler.count(skey);
    }

    /**
     * 単重量未登録商品が存在するかチェック
     * @param batNo パラメータ情報
     * @return boolen true:未登録商品あり false:未登録商品なし
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean getWeightUnregist(String batchNo, String batchSeqNo)
            throws CommonException
    {
        Connection conn = getConnection();
        
        // WareNaviシステムコントロール
        WarenaviSystemController wSysCtrl = new WarenaviSystemController(conn, this.getClass());
        // PCTマスタパッケージが無効の場合、チェックしない
        if (!wSysCtrl.hasPCTMasterPack())
        {
            return false;
        }

        // PCT出庫予定情報のカウントを行う
        PCTRetPlanHandler pHandler = new PCTRetPlanHandler(conn);
        PCTRetPlanSearchKey skey = new PCTRetPlanSearchKey();

        // 検索条件セット
        // バッチNo.0
        skey.setBatchNo(batchNo);
        // バッチSeqNo.
        skey.setBatchSeqNo(batchSeqNo);
        // 荷主コード
        skey.setJoin(PCTRetPlan.CONSIGNOR_CODE, PCTItem.CONSIGNOR_CODE);
        // 商品コード
        skey.setJoin(PCTRetPlan.ITEM_CODE, PCTItem.ITEM_CODE);
        // ロット入数
        skey.setJoin(PCTRetPlan.LOT_ENTERING_QTY, PCTItem.LOT_ENTERING_QTY);
        // 単重量
        skey.setKey(PCTItem.SINGLE_WEIGHT, 0.0, "<=", "", "", true);
        if (pHandler.count(skey) == 0)
        {
            return false;
        }
        return true;
    }

    /**
     * 出荷先コードに対する出荷先優先度を取得します。
     * @param plan PCT出庫予定情報
     * @return int 出荷先優先度
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected int getCustPriority(PCTRetPlan plan)
            throws CommonException
    {
        // WareNaviSystem
        WarenaviSystemController wSysCtrl = new WarenaviSystemController(getConnection(), this.getClass());
        // マスタパッケージが導入されていない場合
        // デフォルト値(中:2)を返す
        if (!wSysCtrl.hasPCTMasterPack())
        {
            return PCTCustomer.JOB_PRIORITY_INSIDE;
        }
        // PCT出荷先マスタ検索
        PCTCustomerHandler cHandler = new PCTCustomerHandler(getConnection());
        PCTCustomerSearchKey skey = new PCTCustomerSearchKey();

        // 検索条件セット
        // 荷主コード
        skey.setConsignorCode(plan.getConsignorCode());
        // 出荷先コード
        skey.setCustomerCode(plan.getCustomerCode());

        // 検索結果
        PCTCustomer custEntity = (PCTCustomer)cHandler.findPrimaryForUpdate(skey);
        // マスタに対象データが無い場合
        // デフォルト値(中:2)を返す
        if (custEntity == null)
        {
            return PCTCustomer.JOB_PRIORITY_INSIDE;
        }

        // 出荷先マスタの出荷先優先度を返す
        return custEntity.getJobPriority();
    }

    /**
     * エリアNoより、最大作業数を取得します。<BR>
     * @param conn    データベースとのコネクションオブジェクト
     * @param pAreaNo 　PCTｴﾘｱNo
     * @return PCTRetPlan 出庫予定情報
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected int getMaxDataCount(Connection conn, String pAreaNo)
            throws CommonException
    {
        PCTMaxWorkCountHandler workCountHandler = new PCTMaxWorkCountHandler(conn);
        PCTMaxWorkCountSearchKey searchKey = new PCTMaxWorkCountSearchKey();

        // 検索条件のクリア
        searchKey.clear();
        // エリアNo
        searchKey.setAreaNo(pAreaNo);

        // 情報取得
        PCTMaxWorkCount[] workEntity = (PCTMaxWorkCount[])workCountHandler.find(searchKey);

        if (ArrayUtil.isEmpty(workEntity))
        {
            // 最大作業数取得不可
            return -1;
        }
        else
        {
            return workEntity[0].getMaxWorkCnt();
        }
    }

    /**
     * 最大作業数及び予定件数より、オーダー毎の件数を計算します。<BR>
     * @param pOrderCount    予定件数
     * @param pMaxCount 　最大作業数
     * @return PCTRetPlan 出庫予定情報
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected int[] getOrderCountTable(int pOrderCount, int pMaxCount)
            throws CommonException
    {
        int[] rOrderTbl = null;

        // 予定件数／最大作業数＝オーダー数
        int tblCount = pOrderCount / pMaxCount;

        // 余りが存在する場合は、オーダー数切り上げ
        if ((pOrderCount % pMaxCount) > 0)
        {
            tblCount++;
        }

        // 予定件数／オーダー数にて、１オーダーの平均を取得
        int average = pOrderCount / tblCount;
        int toomuch = pOrderCount % tblCount;

        rOrderTbl = new int[tblCount];
        for (int lc = 0; lc < tblCount; lc++)
        {
            if (lc < toomuch)
            {
                rOrderTbl[lc] = average + 1;
            }
            else
            {
                rOrderTbl[lc] = average;
            }
        }

        return rOrderTbl;
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
