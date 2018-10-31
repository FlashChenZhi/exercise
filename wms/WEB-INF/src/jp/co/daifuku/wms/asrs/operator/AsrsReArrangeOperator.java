// $Id: AsrsReArrangeOperator.java 7926 2010-05-14 08:09:04Z shibamoto $
package jp.co.daifuku.wms.asrs.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.asrs.communication.as21.ControlInfo;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.wms.asrs.location.CombineZoneSelector;
import jp.co.daifuku.wms.asrs.location.FreeAllocationShelfOperator;
import jp.co.daifuku.wms.asrs.location.RackToRackMoveReArrangeSelector;
import jp.co.daifuku.wms.asrs.location.ShelfOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.ZoneSelector;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.asrs.schedule.AsrsOutParameter;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeHandler;
import jp.co.daifuku.wms.base.dbhandler.LoadSizeSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReArrangePlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ReArrangePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReArrangePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReArrangeSettingAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReArrangeSettingFinder;
import jp.co.daifuku.wms.base.dbhandler.ReArrangeSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.ReArrangeSettingSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.LoadSize;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.ReArrangePlan;
import jp.co.daifuku.wms.base.entity.ReArrangeSetting;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.SoftZonePriority;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.SysDate;

/**
 * AS/RS配置替え作業を行うためのオペレータクラスです。
 *
 * @version $Revision: 7926 $, $Date: 2010-05-14 17:09:04 +0900 (金, 14 5 2010) $
 * @author  ssuzuki@SOFTECS
 * @author  Last commit: $Author: shibamoto $
 */
public class AsrsReArrangeOperator
        extends AsrsOperator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    private static final int FINDER_READ_SIZE = 100;

    private static final int REARRANGE_RATE = 50;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと呼び出し元クラスを指定して
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public AsrsReArrangeOperator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * WEB画面配置替えスケジュール開始を行います。<br>
     * 配置替え設定ファイル、配置替え計画ファイルを登録し、スケジュールNo.を返します。<br>
     * 
     * <!--
     * 以下順で処理を行う。
     * ・搬送ファイルチェック
     * ・配置替え設定ファイル削除
     * ・ソフトゾーン最適化計画作成：ソフトゾーン最適化選択時
     * ・荷姿最適化計画作成：荷姿最適化選択時
     * ・空棚最適化計画作成：空棚最適化選択時
     * ・配置替え設定ファイル登録
     * -->
     * 
     * @param param パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>エリアNo.
     * <li>RM No.
     * </ol>
     * 
     * @return スケジュールNo.
     * 
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException システム定義が正しくない場合にスローされます。
     * @throws NoPrimaryException 対象パレット,ステーションが複数存在するときスローされます。
     * @throws InvalidDefineException パラメータが不正な場合にスローされます。
     * @throws OperatorException 他の端末で設定済みなど処理が続行できないときスローされます。
     * @throws RouteException 搬送ルートが確保できないときスローされます。
     * @throws DataExistsException 作業情報登録済み
     * @throws LockTimeOutException 在庫,パレット,棚情報のロックタイムアウト
     * @throws NotFoundException 更新対象データが見つからない
     */
    public AsrsOutParameter webStartReArrange(AsrsInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                RouteException,
                ScheduleException,
                NotFoundException,
                DataExistsException
    {
        ReArrangePlanSearchKey planKey = new ReArrangePlanSearchKey();
        ReArrangePlanHandler planHandler = new ReArrangePlanHandler(getConnection());

        ReArrangeSettingSearchKey settingKey = new ReArrangeSettingSearchKey();
        ReArrangeSettingHandler settingHandler = new ReArrangeSettingHandler(getConnection());

        // 採番用ハンドラ
        WMSSequenceHandler seqHandler = getSeqHandler();

        // スケジュールNo採番
        String schNo = seqHandler.nextScheduleNo();

        // アイル情報検索
        String whStationNo = getAreaCtlr().getWhStationNo(param.getAreaNo());

        AisleSearchKey wAisleKey = new AisleSearchKey();
        AisleHandler wAisleHandle = new AisleHandler(getConnection());
        wAisleKey.setWhStationNo(whStationNo);
        wAisleKey.setAisleNo(param.getRmNo());

        Aisle aisle = (Aisle)wAisleHandle.findPrimary(wAisleKey);
        String aisleNo = aisle.getStationNo();

        // 配置替え設定ファイル検索
        settingKey.setWhStationNo(whStationNo);
        settingKey.setAisleStationNo(aisleNo);
        ReArrangeSetting targeSetting = (ReArrangeSetting)settingHandler.findPrimary(settingKey);

        // データあり
        if (targeSetting != null)
        {
            // 開始、引当済かチェック
            if ((ReArrangeSetting.REARRANGE_STATUS_FLAG_START.equals(targeSetting.getStatusFlag()))
                    || (ReArrangeSetting.REARRANGE_STATUS_FLAG_ALLOCATED.equals(targeSetting.getStatusFlag())))
            {
                // 引当済み
                OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_ALLOCATED);
                throw ex;
            }

            // 配置替え設定ファイル削除
            settingKey.clear();
            settingKey.setWhStationNo(whStationNo);
            settingKey.setAisleStationNo(aisleNo);
            settingHandler.drop(settingKey);
        }

        // 配置替え設定ファイルEntity作成
        ReArrangeSetting setting = new ReArrangeSetting();

        setting.setScheduleNo(schNo);
        setting.setWhStationNo(whStationNo);
        setting.setAisleStationNo(aisleNo);

        // ソフトゾーン最適化区分
        String softZoneOptimum = param.isSoftZoneOptimum() ? ReArrangeSetting.OPTIMUM_ON
                                                          : ReArrangeSetting.OPTIMUM_OFF;
        setting.setSoftzoneType(softZoneOptimum);
        // 荷姿最適化区分
        String loadSizeOptimum = param.isLoadSizeOptimum() ? ReArrangeSetting.OPTIMUM_ON
                                                          : ReArrangeSetting.OPTIMUM_OFF;
        setting.setLoadsizeType(loadSizeOptimum);
        // 空棚最適化区分
        String vacantOptimum = param.isVacantOptimum() ? ReArrangeSetting.OPTIMUM_ON
                                                      : ReArrangeSetting.OPTIMUM_OFF;
        setting.setVacantType(vacantOptimum);

        // 搬送ファイルチェック
        if (!checkCarry(setting.getAisleStationNo()))
        {
            OperatorException ex = new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
            throw ex;
        }

        try
        {
            // 配置替え計画ファイル削除
            planKey.clear();
            planKey.setWhStationNo(setting.getWhStationNo());
            planKey.setAisleStationNo(setting.getAisleStationNo());
            planHandler.drop(planKey);
        }
        catch (NotFoundException ex)
        {
            // 削除データが存在しなくてもエラーとしない
        }

        // ソフトゾーン最適化計画作成（チェックボックス選択時）
        int softZoneCnt = 0;
        if (param.isSoftZoneOptimum())
        {
            softZoneCnt = createReArrangePlan(ReArrangePlan.REARRANGE_TYPE_SOFTZONE, setting);
        }

        // 荷姿最適化計画作成
        int loadSizeCnt = 0;
        if (param.isLoadSizeOptimum())
        {
            loadSizeCnt = createReArrangePlan(ReArrangePlan.REARRANGE_TYPE_LOADSIZE, setting);
        }

        // 空棚最適化計画作成
        int vacantCnt = 0;
        if (param.isVacantOptimum())
        {
            vacantCnt = createReArrangePlan(ReArrangePlan.REARRANGE_TYPE_VACANT, setting);
        }

        // 配置替え設定ファイル登録
        if (softZoneCnt + loadSizeCnt + vacantCnt > 0)
        {
            setting.setStatusFlag(ReArrangeSetting.REARRANGE_STATUS_FLAG_ALLOCATED);
        }
        else
        {
            setting.setStatusFlag(ReArrangeSetting.REARRANGE_STATUS_FLAG_COMPLETION);
        }

        setting.setSoftzoneAllocationQty(softZoneCnt);
        setting.setLoadsizeAllocationQty(loadSizeCnt);
        setting.setVacantAllocationQty(vacantCnt);
        setting.setSoftzoneResultQty(0);
        setting.setLoadsizeResultQty(0);
        setting.setVacantResultQty(0);
        setting.setRegistPname(getCallerName());
        setting.setRegistDate(new SysDate());
        setting.setLastUpdatePname(getCallerName());

        settingHandler.create(setting);

        // 出力パラメータに設定単位キーを追加
        AsrsOutParameter retParam = new AsrsOutParameter();
        retParam.setScheduleNo(schNo);

        return retParam;
    }

    /**
     * 配置替えスケジュール処理を行います。<br>
     * 配置替え設定ファイルをもとに配置替え計画データを作成します。<br>
     * 
     * <!--
     * 以下順で処理を行う。
     * ・配置替え設定ファイル取得（ロック）
     * ・搬送ファイルチェック
     * ・配置替え設定ファイル削除
     * ・ソフトゾーン最適化計画作成：ソフトゾーン最適化選択時
     * ・荷姿最適化計画作成：荷姿最適化選択時
     * ・空棚最適化計画作成：空棚最適化選択時
     * ・配置替え設定ファイル更新
     * -->
     * 
     * @throws ReadWriteException データベースアクセスエラー
     * @throws InvalidDefineException パラメータが不正な場合にスローされます。
     * @throws OperatorException 他の端末で設定済みなど処理が続行できないときスローされます。
     * @throws DataExistsException 作業情報登録済み
     * @throws LockTimeOutException 在庫,パレット,棚情報のロックタイムアウト
     * @throws NotFoundException 更新対象データが見つからない
     */
    public void reArrangeSchedule()
            throws ReadWriteException,
                InvalidDefineException,
                OperatorException,
                DataExistsException,
                LockTimeOutException,
                NotFoundException
    {
        ReArrangeSettingSearchKey settingSKey = new ReArrangeSettingSearchKey();
        ReArrangeSettingAlterKey settingAKey = new ReArrangeSettingAlterKey();
        ReArrangeSettingHandler settingHandler = new ReArrangeSettingHandler(getConnection());
        ReArrangeSettingFinder settingFinder = new ReArrangeSettingFinder(getConnection());

        ReArrangePlanSearchKey planSKey = new ReArrangePlanSearchKey();
        ReArrangePlanHandler planHandler = new ReArrangePlanHandler(getConnection());

        settingFinder.open(true);
        try
        {
            // 配置替え設定ファイル検索（ロック）
            settingSKey.setStatusFlag(ReArrangeSetting.REARRANGE_STATUS_FLAG_START);
            settingSKey.setWhStationNoOrder(true);
            settingSKey.setAisleStationNoOrder(true);

            int numSettings = settingFinder.searchForUpdate(settingSKey, ReArrangeSettingFinder.WAIT_SEC_DEFAULT);
            if (0 == numSettings)
            {
                // 更新対象データなし
                throw new NotFoundException();
            }

            while (settingFinder.hasNext())
            {
                // get entities (read ahead)
                ReArrangeSetting[] settingArray = (ReArrangeSetting[])settingFinder.getEntities(FINDER_READ_SIZE);

                for (ReArrangeSetting setting : settingArray)
                {
                    // 搬送ファイルチェック
                    if (!checkCarry(setting.getAisleStationNo()))
                    {
                        // 棚替以外の搬送情報あり
                        OperatorException ex = new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
                        throw ex;
                    }

                    try
                    {
                        // 配置替え計画ファイル削除
                        planSKey.clear();
                        planSKey.setWhStationNo(setting.getWhStationNo());
                        planSKey.setAisleStationNo(setting.getAisleStationNo());
                        planHandler.drop(planSKey);
                    }
                    catch (NotFoundException ex)
                    {
                        // 削除データが存在しなくてもエラーとしない
                    }

                    // ソフトゾーン最適化計画作成
                    int softZoneCnt = createReArrangePlan(ReArrangePlan.REARRANGE_TYPE_SOFTZONE, setting);

                    // 荷姿最適化計画作成
                    int loadSizeCnt = createReArrangePlan(ReArrangePlan.REARRANGE_TYPE_LOADSIZE, setting);

                    // 空棚最適化計画作成
                    int vacantCnt = createReArrangePlan(ReArrangePlan.REARRANGE_TYPE_VACANT, setting);

                    // 配置替え設定ファイル更新
                    settingAKey.clear();
                    if (softZoneCnt + loadSizeCnt + vacantCnt > 0)
                    {
                        settingAKey.updateStatusFlag(ReArrangeSetting.REARRANGE_STATUS_FLAG_ALLOCATED);
                    }
                    else
                    {
                        settingAKey.updateStatusFlag(ReArrangeSetting.REARRANGE_STATUS_FLAG_COMPLETION);
                    }

                    settingAKey.setWhStationNo(setting.getWhStationNo());
                    settingAKey.setAisleStationNo(setting.getAisleStationNo());
                    settingAKey.updateSoftzoneAllocationQty(softZoneCnt);
                    settingAKey.updateLoadsizeAllocationQty(loadSizeCnt);
                    settingAKey.updateVacantAllocationQty(vacantCnt);
                    settingAKey.updateSoftzoneResultQty(0);
                    settingAKey.updateLoadsizeResultQty(0);
                    settingAKey.updateVacantResultQty(0);
                    settingAKey.updateLastUpdatePname(getCallerName());

                    settingHandler.modify(settingAKey);
                }
            } // end while loop of Stock Finder
        }
        finally
        {
            settingFinder.close();
        }

        return;
    }

    /**
     * 配置替え引当処理を行います。<br>
     * 配置替え計画ファイルをもとに引当を行い、搬送情報を作成する。<br>
     * 
     * <!--
     * 以下順で処理を行う。
     * ・配置替え設定ファイル取得（ロック）
     * ・搬送ファイルチェック
     * ・配置替え設定ファイル削除
     * ・ソフトゾーン最適化計画作成：ソフトゾーン最適化選択時
     * ・荷姿最適化計画作成：荷姿最適化選択時
     * ・空棚最適化計画作成：空棚最適化選択時
     * ・配置替え設定ファイル更新
     * -->
     * 
     * @return 設定単位キー,搬送先ステーションNo.
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException 該当エリアNo.が見つからなかったときスローされます
     * @throws NoPrimaryException 該当するエリアNo.が複数存在するときスローされます。
     * @throws InvalidDefineException パラメータが不正な場合にスローされます。
     * @throws OperatorException 他の端末で設定済みなど処理が続行できないときスローされます。
     * @throws DataExistsException 登録済み
     * @throws LockTimeOutException ロックタイムアウト
     * @throws NotFoundException 更新対象データが見つからない
     */
    public int reArrangeAllocate()
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                DataExistsException,
                LockTimeOutException,
                NotFoundException
    {
        ReArrangeSettingSearchKey settingSKey = new ReArrangeSettingSearchKey();
        ReArrangeSettingAlterKey settingAKey = new ReArrangeSettingAlterKey();
        ReArrangeSettingFinder settingFinder = new ReArrangeSettingFinder(getConnection());
        ReArrangeSettingHandler settingHandler = new ReArrangeSettingHandler(getConnection());

        ReArrangePlanFinder planFinder = new ReArrangePlanFinder(getConnection());
        ReArrangePlanHandler planHandler = new ReArrangePlanHandler(getConnection());

        int numSettings = 0;

        settingFinder.open(true);
        planFinder.open(true);
        try
        {
            // 配置替え設定ファイル検索（ロック）
            settingSKey.setStatusFlag(ReArrangeSetting.REARRANGE_STATUS_FLAG_ALLOCATED);
            settingSKey.setWhStationNoOrder(true);
            settingSKey.setAisleStationNoOrder(true);

            numSettings = settingFinder.searchForUpdate(settingSKey, ReArrangeSettingFinder.WAIT_SEC_DEFAULT);
            if (0 == numSettings)
            {
                // 更新対象データなし
                return numSettings;
            }

            while (settingFinder.hasNext())
            {
                // get entities (read ahead)
                ReArrangeSetting[] settingArray = (ReArrangeSetting[])settingFinder.getEntities(FINDER_READ_SIZE);

                for (ReArrangeSetting setting : settingArray)
                {
                    String whStationNo = setting.getWhStationNo();
                    String aisleNo = setting.getAisleStationNo();

                    // オンラインチェック
                    if (!isControllerOnline(aisleNo))
                    {
                        // グループコントローラがオンラインでなければスキップ
                        continue;
                    }

                    // 搬送ファイルチェック
                    if (!checkCarry(aisleNo))
                    {
                        // 棚替以外の搬送情報が存在する場合はスキップ
                        continue;
                    }

                    // 完了搬送ファイルチェック
                    if (!checkCompleteCarry(aisleNo))
                    {
                        // 完了前の棚替搬送情報が存在する場合はスキップ
                        continue;
                    }

                    ReArrangePlanSearchKey planSKey = createPlanSearchKey(setting);
                    int numPlans = planFinder.searchForUpdate(planSKey, ReArrangePlanFinder.WAIT_SEC_DEFAULT);
                    if (0 == numPlans)
                    {
                        // 対象データがない場合、配置替え計画ファイル削除（パレットが別作業で引き当てられた場合を考慮）
                        planSKey.clear();
                        planSKey.setScheduleNo(setting.getScheduleNo());
                        planHandler.drop(planSKey);
                        continue;
                    }

                    int softZoneCnt = 0;
                    int loadSizeCnt = 0;
                    int vacantCnt = 0;

                    while (planFinder.hasNext())
                    {
                        // get entities (read ahead)
                        ReArrangePlan[] planArray = (ReArrangePlan[])planFinder.getEntities(1);

                        // 配置替え計画データ引当処理
                        if (allocate(planArray[0]))
                        {
                            // ソフトゾーン最適化実績数加算
                            if (ReArrangePlan.REARRANGE_TYPE_SOFTZONE.equals(planArray[0].getRearrangeType()))
                            {
                                softZoneCnt++;
                            }
                            // 荷姿最適化実績数加算
                            else if (ReArrangePlan.REARRANGE_TYPE_LOADSIZE.equals(planArray[0].getRearrangeType()))
                            {
                                loadSizeCnt++;
                            }
                            // 空棚最適化実績数加算
                            else if (ReArrangePlan.REARRANGE_TYPE_VACANT.equals(planArray[0].getRearrangeType()))
                            {
                                vacantCnt++;
                            }
                        }

                        // 配置替え計画ファイル削除
                        planSKey.clear();
                        planSKey.setPalletId(planArray[0].getPalletId());
                        planHandler.drop(planSKey);

                        // ループを抜ける（次のアイルへ）
                        break;
                    } // end while loop of ReArrangePlan Finder

                    // 配置替え設定ファイル更新
                    settingAKey.clear();
                    settingAKey.setWhStationNo(whStationNo);
                    settingAKey.setAisleStationNo(setting.getAisleStationNo());
                    settingAKey.setUpdateWithColumn(ReArrangeSetting.SOFTZONE_RESULT_QTY,
                            ReArrangeSetting.SOFTZONE_RESULT_QTY, new BigDecimal(softZoneCnt));
                    settingAKey.setUpdateWithColumn(ReArrangeSetting.LOADSIZE_RESULT_QTY,
                            ReArrangeSetting.LOADSIZE_RESULT_QTY, new BigDecimal(loadSizeCnt));
                    settingAKey.setUpdateWithColumn(ReArrangeSetting.VACANT_RESULT_QTY,
                            ReArrangeSetting.VACANT_RESULT_QTY, new BigDecimal(vacantCnt));
                    settingAKey.updateLastUpdatePname(getCallerName());

                    // 配置替え設定ファイルのスケジュールNo.で配置替え計画ファイル検索
                    planSKey.clear();
                    planSKey.setScheduleNo(setting.getScheduleNo());

                    if (planHandler.count(planSKey) == 0)
                    {
                        // 紐づく計画ファイルがなければ設定ファイルを完了に更新
                        settingAKey.updateStatusFlag(ReArrangeSetting.REARRANGE_STATUS_FLAG_COMPLETION);
                    }
                    settingHandler.modify(settingAKey);
                } // end for loop of ReArrangeSetting Entity
            } // end while loop of ReArrangeSetting Finder
        }
        finally
        {
            settingFinder.close();
            planFinder.close();
        }

        return numSettings;
    }

    /**
     * 配置替え引当処理を行います。<br>
     * 配置替え計画ファイルをもとに引当を行い、搬送情報を作成する。<br>
     * 
     * <!--
     * 以下順で処理を行う。
     * ・配置替え設定ファイル取得（ロック）
     * ・搬送ファイルチェック
     * ・配置替え設定ファイル削除
     * ・ソフトゾーン最適化計画作成：ソフトゾーン最適化選択時
     * ・荷姿最適化計画作成：荷姿最適化選択時
     * ・空棚最適化計画作成：空棚最適化選択時
     * ・配置替え設定ファイル更新
     * -->
     * @param agcNo AGCNo
     * 
     * @return 設定単位キー,搬送先ステーションNo.
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException 該当エリアNo.が見つからなかったときスローされます
     * @throws NoPrimaryException 該当するエリアNo.が複数存在するときスローされます。
     * @throws InvalidDefineException パラメータが不正な場合にスローされます。
     * @throws OperatorException 他の端末で設定済みなど処理が続行できないときスローされます。
     * @throws DataExistsException 登録済み
     * @throws LockTimeOutException ロックタイムアウト
     * @throws NotFoundException 更新対象データが見つからない
     */
    public int reArrangeAllocate(String agcNo)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                DataExistsException,
                LockTimeOutException,
                NotFoundException
    {
        ReArrangeSettingSearchKey settingSKey = new ReArrangeSettingSearchKey();
        ReArrangeSettingAlterKey settingAKey = new ReArrangeSettingAlterKey();
        ReArrangeSettingFinder settingFinder = new ReArrangeSettingFinder(getConnection());
        ReArrangeSettingHandler settingHandler = new ReArrangeSettingHandler(getConnection());

        ReArrangePlanFinder planFinder = new ReArrangePlanFinder(getConnection());
        ReArrangePlanHandler planHandler = new ReArrangePlanHandler(getConnection());

    	int numSettings = 0;

        settingFinder.open(true);
        planFinder.open(true);
        try
        {
            // 配置替え設定ファイル検索（ロック）
            settingSKey.setStatusFlag(ReArrangeSetting.REARRANGE_STATUS_FLAG_ALLOCATED);
            // 指定したAGCのアイルステーションを紐付ける。
        	settingSKey.setKey(Aisle.CONTROLLER_NO, agcNo);
            settingSKey.setKey(ReArrangeSetting.AISLE_STATION_NO, Aisle.STATION_NO);
            settingSKey.setWhStationNoOrder(true);
            settingSKey.setAisleStationNoOrder(true);

            numSettings = settingFinder.searchForUpdate(settingSKey, ReArrangeSettingFinder.WAIT_SEC_DEFAULT);
            if (0 == numSettings)
            {
                // 更新対象データなし
                return numSettings;
            }

            while (settingFinder.hasNext())
            {
                // get entities (read ahead)
                ReArrangeSetting[] settingArray = (ReArrangeSetting[])settingFinder.getEntities(FINDER_READ_SIZE);

                for (ReArrangeSetting setting : settingArray)
                {
                    String whStationNo = setting.getWhStationNo();
                    String aisleNo = setting.getAisleStationNo();

                    // オンラインチェック
                    if (!isControllerOnline(aisleNo))
                    {
                        // グループコントローラがオンラインでなければスキップ
                        continue;
                    }

                    // 搬送ファイルチェック
                    if (!checkCarry(aisleNo))
                    {
                        // 棚替以外の搬送情報が存在する場合はスキップ
                        continue;
                    }

                    // 完了搬送ファイルチェック
                    if (!checkCompleteCarry(aisleNo))
                    {
                        // 完了前の棚替搬送情報が存在する場合はスキップ
                        continue;
                    }

                    ReArrangePlanSearchKey planSKey = createPlanSearchKey(setting);
                    int numPlans = planFinder.searchForUpdate(planSKey, ReArrangePlanFinder.WAIT_SEC_DEFAULT);
                    if (0 == numPlans)
                    {
                        // 対象データがない場合、配置替え計画ファイル削除（パレットが別作業で引き当てられた場合を考慮）
                        planSKey.clear();
                        planSKey.setScheduleNo(setting.getScheduleNo());
                        planHandler.drop(planSKey);
                        continue;
                    }

                    int softZoneCnt = 0;
                    int loadSizeCnt = 0;
                    int vacantCnt = 0;

                    while (planFinder.hasNext())
                    {
                        // get entities (read ahead)
                        ReArrangePlan[] planArray = (ReArrangePlan[])planFinder.getEntities(1);

                        // 配置替え計画データ引当処理
                        if (allocate(planArray[0]))
                        {
                            // ソフトゾーン最適化実績数加算
                            if (ReArrangePlan.REARRANGE_TYPE_SOFTZONE.equals(planArray[0].getRearrangeType()))
                            {
                                softZoneCnt++;
                            }
                            // 荷姿最適化実績数加算
                            else if (ReArrangePlan.REARRANGE_TYPE_LOADSIZE.equals(planArray[0].getRearrangeType()))
                            {
                                loadSizeCnt++;
                            }
                            // 空棚最適化実績数加算
                            else if (ReArrangePlan.REARRANGE_TYPE_VACANT.equals(planArray[0].getRearrangeType()))
                            {
                                vacantCnt++;
                            }
                        }

                        // 配置替え計画ファイル削除
                        planSKey.clear();
                        planSKey.setPalletId(planArray[0].getPalletId());
                        planHandler.drop(planSKey);

                        // ループを抜ける（次のアイルへ）
                        break;
                    } // end while loop of ReArrangePlan Finder

                    // 配置替え設定ファイル更新
                    settingAKey.clear();
                    settingAKey.setWhStationNo(whStationNo);
                    settingAKey.setAisleStationNo(setting.getAisleStationNo());
                    settingAKey.setUpdateWithColumn(ReArrangeSetting.SOFTZONE_RESULT_QTY,
                            ReArrangeSetting.SOFTZONE_RESULT_QTY, new BigDecimal(softZoneCnt));
                    settingAKey.setUpdateWithColumn(ReArrangeSetting.LOADSIZE_RESULT_QTY,
                            ReArrangeSetting.LOADSIZE_RESULT_QTY, new BigDecimal(loadSizeCnt));
                    settingAKey.setUpdateWithColumn(ReArrangeSetting.VACANT_RESULT_QTY,
                            ReArrangeSetting.VACANT_RESULT_QTY, new BigDecimal(vacantCnt));
                    settingAKey.updateLastUpdatePname(getCallerName());

                    // 配置替え設定ファイルのスケジュールNo.で配置替え計画ファイル検索
                    planSKey.clear();
                    planSKey.setScheduleNo(setting.getScheduleNo());

                    if (planHandler.count(planSKey) == 0)
                    {
                        // 紐づく計画ファイルがなければ設定ファイルを完了に更新
                        settingAKey.updateStatusFlag(ReArrangeSetting.REARRANGE_STATUS_FLAG_COMPLETION);
                    }
                    settingHandler.modify(settingAKey);
                } // end for loop of ReArrangeSetting Entity
            } // end while loop of ReArrangeSetting Finder
        }
        finally
        {
            settingFinder.close();
            planFinder.close();
        }

        return numSettings;
    }

    /**<jp>
     * 配置替え引当タスクに対して、配置替え引当の要求を行ないます。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     </jp>*/
    public void allocateRequest()
            throws ReadWriteException
    {
        try
        {
            //<jp> ReArrangeAllocatorに要求メッセージを送る。</jp>
            //<en> Sends request message to ReArrangeAllocator.</en>
            RmiSendClient rmiSndC = new RmiSendClient();
            Object[] param = new Object[2];
            param[0] = null;
            rmiSndC.write("ReArrangeAllocator", param);
        }
        catch (Exception e)
        {
            //<jp> 呼出し元（arrivalメソッド)の構造を考慮し、</jp>
            //<jp> ここではReadWriteExceptionをthrowする。</jp>
            //<en> Taking the structure of the call source (arrival mthod) into account, </en>
            //<en> it throws ReadWriteException.</en>
            throw new ReadWriteException();
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 配置替え計画データ引当処理を行います。<BR>
     * 指定された計画情報より空棚を検索し、AS/RS配置替え棚間移動データを作成します。<BR>
     * @param plan 配置替え計画データ
     * @return AS/RS配置替え棚間移動データを作成できた場合はtrue、それ以外はfalse
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException 該当エリアNo.が見つからなかったときスローされます
     * @throws NoPrimaryException 該当するエリアNo.が複数存在するときスローされます。
     * @throws LockTimeOutException ロックタイムアウト
     * @throws InvalidDefineException パラメータが不正な場合にスローされます。
     * @throws DataExistsException 登録済み
     * @throws NotFoundException 対象データが見つからない
     */
    protected boolean allocate(ReArrangePlan plan)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException,
                LockTimeOutException,
                InvalidDefineException,
                DataExistsException,
                NotFoundException
    {
        // パレットの検索
        PalletSearchKey pskey = new PalletSearchKey();
        PalletHandler plH = new PalletHandler(getConnection());

        pskey.setPalletId(plan.getPalletId());
        Pallet plt = (Pallet)plH.findPrimary(pskey);

        Station aisle = null;

        // 棚決定処理を行う。
        WareHouse wareHouse = (WareHouse)StationFactory.makeStation(getConnection(), plt.getWhStationNo());
        try
        {
            // 搬送先決定処理を行い、搬送先の入庫棚を決定する。
            // 同一アイルから棚決定を行いたいので、アイルステーションNoを取得して現在位置にセット
            Shelf shelf = (Shelf)StationFactory.makeStation(getConnection(), plt.getCurrentStationNo());
            aisle = StationFactory.makeStation(getConnection(), shelf.getParentStationNo());
            plt.setCurrentStationNo(aisle.getStationNo());
        }
        catch (NotFoundException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }

        // 検索ゾーン作成
        String type = plan.getRearrangeType();
        Zone[] searchZone = makeZone(plt, type);

        // ソフトゾーン、ハードゾーン併用検索
        ZoneSelector zn = new CombineZoneSelector(getConnection());

        RackToRackMoveReArrangeSelector selector = new RackToRackMoveReArrangeSelector(getConnection(), zn);

        Shelf location = null;
        for (Zone zone : searchZone)
        {
            // 移動先の空棚検索
            location =
                    selector.select(plt, wareHouse, aisle.getStationNo(), zone.getHardZone().getHardZoneId(),
                            zone.getSoftZoneID());
            if (location != null)
            {
                pskey.clear();
                pskey.setPalletId(plan.getPalletId());
                Pallet movePallet = (Pallet)plH.findPrimary(pskey);

                ShelfOperator shelfop = new ShelfOperator(getConnection(), location.getStationNo());

                // 棚間移動先のShelfインスタンスを取得
                Shelf destShelf = (Shelf)location;
                // 移動元棚インスタンス作成
                Shelf sourceShelf =
                        (Shelf)StationFactory.makeStation(getConnection(), movePallet.getCurrentStationNo());

                // 取得した棚チェックを行う
                boolean reArrangeCheck = false;
                // ソフトゾーン最適化
                if (ReArrangePlan.REARRANGE_TYPE_SOFTZONE.equals(type))
                {
                    reArrangeCheck = checkSoftZoneShelf(movePallet, sourceShelf, destShelf);
                }
                // 荷姿最適化
                else if (ReArrangePlan.REARRANGE_TYPE_LOADSIZE.equals(type))
                {
                    reArrangeCheck = checkLoadSizeShelf(movePallet, sourceShelf, destShelf);
                }
                // 空棚最適化
                else if (ReArrangePlan.REARRANGE_TYPE_VACANT.equals(type))
                {
                    reArrangeCheck = checkVacantShelf(movePallet, sourceShelf, destShelf);
                }

                // チェック結果がNGの場合は空棚検索棚を予約棚から空棚に戻して終了            
                if (!reArrangeCheck)
                {
                    // フリーアロケーション運用の場合、荷幅、棚使用フラグをフリーに更新
                    if (WareHouse.FREE_ALLOCATION_ON.equals(wareHouse.getFreeAllocationType()))
                    {
                        FreeAllocationShelfOperator freeshelfop =
                                new FreeAllocationShelfOperator(getConnection(), location.getStationNo());
                        freeshelfop.alterFreeWidth();
                    }

                    // 検索した棚間移動先棚を空棚にする。
                    shelfop.alterPresence(Shelf.LOCATION_STATUS_FLAG_EMPTY);
                    continue;
                }

                // フリーアロケーション運用の場合、荷幅、棚使用フラグを更新
                if (WareHouse.FREE_ALLOCATION_ON.equals(wareHouse.getFreeAllocationType()))
                {
                    FreeAllocationShelfOperator freeshelfop =
                            new FreeAllocationShelfOperator(getConnection(), location.getStationNo());
                    freeshelfop.alterWidth(movePallet);
                }

                // 検索した棚間移動先棚を入庫予約にする。
                shelfop.alterPresence(Shelf.LOCATION_STATUS_FLAG_RESERVATION);

                // 配置替え棚間移動データ作成
                createAsrsReArrangeData(plan, wareHouse, movePallet, sourceShelf, destShelf);

                break;
            }
        }

        return true;
    }

    /**
     * 配置替え棚間移動データの作成を行います。<BR>
     * @param plan        配置替え計画データ
     * @param wareHouse   倉庫情報
     * @param pallet      搬送パレット情報
     * @param sourceShelf 搬送元棚情報
     * @param destShelf   搬送先棚情報
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException 該当エリアNo.が見つからなかったときスローされます
     * @throws NoPrimaryException 該当するエリアNo.が複数存在するときスローされます。
     * @throws LockTimeOutException ロックタイムアウト
     * @throws InvalidDefineException パラメータが不正な場合にスローされます。
     * @throws DataExistsException 登録済み
     * @throws NotFoundException 対象データが見つからない
     */
    protected void createAsrsReArrangeData(ReArrangePlan plan, WareHouse wareHouse, Pallet pallet, Shelf sourceShelf,
            Shelf destShelf)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException,
                LockTimeOutException,
                InvalidDefineException,
                DataExistsException,
                NotFoundException
    {
        CarryInfo newCarryInfo = new CarryInfo();
        // 棚間移動先のロケーションが見つかったので搬送データを作成する。

        // パレットの状態を出庫予約、引当状態を引当済みに変更、最新更新日時を更新
        // Pallet検索・更新用
        PalletHandler palletHandelr = new PalletHandler(getConnection());
        PalletAlterKey palletAlterKey = new PalletAlterKey();

        // 検索キーセット
        palletAlterKey.clear();
        palletAlterKey.setPalletId(pallet.getPalletId());
        palletAlterKey.updateAllocationFlag(Pallet.ALLOCATION_FLAG_ALLOCATED);
        palletAlterKey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
        palletAlterKey.updateLastUpdatePname(getCallerName());
        // 更新
        palletHandelr.modify(palletAlterKey);

        // エリアNoを取得します。
        String areaNo = null;
        try
        {
            areaNo = getAreaCtlr().getAreaNoOfWarehouse(destShelf.getWhStationNo());
        }
        catch (NoPrimaryException e)
        {
            throw new InvalidDefineException(e.getMessage());
        }
        String locationNo = getAreaCtlr().toParamLocation(destShelf.getStationNo());

        // Stock検索用
        StockHandler stockHandler = new StockHandler(getConnection());
        StockSearchKey stockKey = new StockSearchKey();
        Stock[] stockArray = null;
        // 検索キーセット
        stockKey.clear();
        stockKey.setPalletId(pallet.getPalletId());

        // 検索
        stockArray = (Stock[])stockHandler.find(stockKey);

        // CarryInformationインスタンス生成
        WMSSequenceHandler sequence = new WMSSequenceHandler(getConnection());
        String carryKey = sequence.nextCarryKey();

        // WorkInfo登録用
        WorkInfoHandler workInfoHandler = new WorkInfoHandler(getConnection());

        WarenaviSystemController wSys = new WarenaviSystemController(getConnection(), getClass());

        for (Stock stock : stockArray)
        {
            // WorkInformationインスタンス生成
            WorkInfo wi = new WorkInfo();
            // 作業No
            wi.setJobNo(sequence.nextWorkInfoJobNo());
            // 作業区分
            wi.setJobType(WorkInfo.JOB_TYPE_ASRS_REARRANGE);
            // 状態フラグ
            wi.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);
            // ハードウェア区分
            wi.setHardwareType(WorkInfo.HARDWARE_TYPE_ASRS);
            // 在庫ID
            wi.setStockId(stock.getStockId());
            // システム接続キー（搬送キー）
            wi.setSystemConnKey(carryKey);
            // 予定日
            wi.setPlanDay(wSys.getWorkDay());
            // 荷主コード
            wi.setConsignorCode(stock.getConsignorCode());
            // 予定エリア
            wi.setPlanAreaNo(areaNo);
            // 予定棚
            wi.setPlanLocationNo(locationNo);
            // 商品コード
            wi.setItemCode(stock.getItemCode());
            // ロットNo
            wi.setPlanLotNo(stock.getLotNo());
            // 作業数=0
            wi.setResultQty(0);
            // ユーザID
            wi.setUserId(WmsParam.SYS_USER_ID);
            // 端末No
            wi.setTerminalNo(WmsParam.SYS_TERMINAL_NO);
            // 登録日時
            wi.setRegistDate(new SysDate());
            // 登録処理名
            wi.setRegistPname(getCallerName());
            // 最終更新処理名
            wi.setLastUpdatePname(getCallerName());

            workInfoHandler.create(wi);
        }

        // CARRYINFO表登録
        newCarryInfo.setCarryKey(carryKey);
        newCarryInfo.setSourceStationNo(sourceShelf.getStationNo());

        // 作業種別
        newCarryInfo.setWorkType(CarryInfo.WORK_TYPE_RACKMOVE_FROM);
        // 出庫ロケーションNo
        newCarryInfo.setRetrievalStationNo(sourceShelf.getStationNo());
        // 搬送対象パレット
        newCarryInfo.setPalletId(pallet.getPalletId());
        // 搬送先StationNo
        newCarryInfo.setDestStationNo(destShelf.getStationNo());
        // アイルStationNo
        newCarryInfo.setAisleStationNo(destShelf.getParentStationNo());
        // グループNo
        newCarryInfo.setGroupNo(0);
        // 搬送状態：開始
        newCarryInfo.setCmdStatus(CarryInfo.CMD_STATUS_START);
        // 優先区分
        newCarryInfo.setPriority(CarryInfo.PRIORITY_NORMAL);
        // 再入庫フラグ
        newCarryInfo.setRestoringFlag(CarryInfo.RESTORING_FLAG_NOT_SAME_LOC);
        // 搬送区分：棚間移動
        newCarryInfo.setCarryFlag(CarryInfo.CARRY_FLAG_RACK_TO_RACK);
        // 出庫指示詳細
        newCarryInfo.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_UNIT);
        // 作業NO
        WMSSequenceHandler seqHandler = new WMSSequenceHandler(getConnection());
        newCarryInfo.setWorkNo(seqHandler.nextWorkNo());
        // 到着時刻:null
        newCarryInfo.setArrivalDate(null);
        // 制御情報（フリーアロケーション倉庫の場合）
        if (WareHouse.FREE_ALLOCATION_ON.equals(wareHouse.getFreeAllocationType()))
        {
            LoadSizeSearchKey lskey = new LoadSizeSearchKey();
            LoadSizeHandler lHandler = new LoadSizeHandler(getConnection());
            lskey.clear();
            lskey.setJoin(LoadSize.LOAD_SIZE, HardZone.HEIGHT);
            lskey.setKey(HardZone.HARD_ZONE_ID, sourceShelf.getHardZoneId());
            // 荷姿マスタ情報検索
            LoadSize loadsize = (LoadSize)lHandler.findPrimary(lskey);
            if (loadsize == null)
            {
                // 6026601=指定されたハードゾーンの荷高が荷姿マスタの荷姿にありません。ハードゾーンID:{0}
                Object[] tObj = new Object[1];
                tObj[0] = sourceShelf.getHardZoneId();
                RmiMsgLogClient.write(6022036, this.getClass().getSimpleName(), tObj);
                throw new InvalidDefineException();
            }

            // 制御情報作成
            ControlInfo conInfo = new ControlInfo();
            conInfo.setWidth(sourceShelf.getWidth());
            conInfo.setLength(loadsize.getLength());
            conInfo.setHeight(loadsize.getHeight());
            String controlInfo = conInfo.convertControlInfo(conInfo);

            newCarryInfo.setControlinfo(controlInfo);
        }
        // 制御情報（フリーアロケーション倉庫以外の場合）
        else
        {
            newCarryInfo.setControlinfo(null);
        }

        // キャンセル区分:キャンセル要求なし
        newCarryInfo.setCancelRequest(CarryInfo.CANCEL_REQUEST_UNDEMAND);
        // キャンセル要求日時
        newCarryInfo.setCancelRequestDate(null);
        // スケジュールNo
        newCarryInfo.setScheduleNo(plan.getScheduleNo());
        // 最終ステーションNo
        newCarryInfo.setEndStationNo(destShelf.getStationNo());
        // 登録処理名
        newCarryInfo.setRegistPname(getCallerName());
        // 最終更新処理名
        newCarryInfo.setLastUpdatePname(getCallerName());

        CarryInfoHandler cryHandler = new CarryInfoHandler(getConnection());
        cryHandler.create(newCarryInfo);
    }

    /**
     * グループコントローラのオンラインチェックを行います。<BR>
     * 指定されたアイルの属するグループコントローラの状態がオンラインかどうかを判定します。<BR>
     * @param checkAisle オンラインをチェックするアイルステーションNo.
     * @return オンライン : tue オンライン以外 : false
     * @throws ReadWriteException データベースアクセスエラー
     * @throws InvalidDefineException パラメータが不正な場合にスローされます。
     */
    protected boolean isControllerOnline(String checkAisle)
            throws ReadWriteException,
                InvalidDefineException
    {
        GroupControllerHandler groupHandler = new GroupControllerHandler(getConnection());
        GroupControllerSearchKey groupKey = new GroupControllerSearchKey();

        // オンラインチェック
        groupKey.clear();
        groupKey.setJoin(GroupController.CONTROLLER_NO, Aisle.CONTROLLER_NO);
        groupKey.setKey(Aisle.STATION_NO, checkAisle);

        // ステーションが属するグループコントローラNo.で検索を行う
        GroupController[] rGroupControll = (GroupController[])groupHandler.find(groupKey);
        if (rGroupControll == null || rGroupControll.length == 0)
        {
            throw new InvalidDefineException("groupcontroler is null || zero length");
        }
        // グループコントローラがオンライン
        if (GroupController.GC_STATUS_ONLINE.equals(rGroupControll[0].getStatusFlag()))
        {
            return true;
        }

        return false;
    }

    /**
     * 搬送情報検索チェックを行います。<BR>
     * 指定されたアイルが、棚替以外の作業を行っていないかをチェックします。<BR>
     * 棚替以外の搬送情報があった場合は、NGとします。<BR>
     * 
     * @param checkAisle 搬送情報をチェックするアイルステーションNo.
     * @return true 棚替以外の搬送情報はない <BR> false 棚替以外の搬送情報がある
     * @throws ReadWriteException データベースエラー
     */
    protected boolean checkCarry(String checkAisle)
            throws ReadWriteException
    {
        CarryInfoHandler cryHandler = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey cryKey = new CarryInfoSearchKey();

        cryKey.setAisleStationNo(checkAisle);
        cryKey.setJoin(CarryInfo.CARRY_KEY, WorkInfo.SYSTEM_CONN_KEY);
        cryKey.setKey(WorkInfo.JOB_TYPE, WorkInfo.JOB_TYPE_ASRS_REARRANGE, "!=", "", "", true);

        // 棚替以外の作業が1件でもある場合は、NG
        if (cryHandler.count(cryKey) > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 完了前の棚替え搬送データのチェックを行います。<BR>
     * 指定されたアイルが、完了前の棚替の作業を行っていないかをチェックします。<BR>
     * 完了前の棚替の搬送情報があった場合は、NGとします。<BR>
     * 
     * @param checkAisle 搬送情報をチェックするアイルステーションNo.
     * @return true 完了前の棚替の搬送情報はない <BR> false 完了前の棚替の搬送情報がある
     * @throws ReadWriteException データベースエラー
     */
    protected boolean checkCompleteCarry(String checkAisle)
            throws ReadWriteException
    {
        CarryInfoHandler cryHandler = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey cryKey = new CarryInfoSearchKey();

        cryKey.setAisleStationNo(checkAisle);
        cryKey.setCmdStatus(CarryInfo.CMD_STATUS_COMP_RETRIEVAL, "<");
        cryKey.setJoin(CarryInfo.CARRY_KEY, WorkInfo.SYSTEM_CONN_KEY);
        cryKey.setKey(WorkInfo.JOB_TYPE, WorkInfo.JOB_TYPE_ASRS_REARRANGE);

        // 完了前の棚替の作業が1件でもある場合は、NG
        if (cryHandler.count(cryKey) > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 配置替え計画情報検索チェックを行います。<BR>
     * 指定されたパレットIDが、配置替え計画情報に存在しないかをチェックします。<BR>
     * 
     * @param palletId パレットID
     * @return true 配置替え計画情報に存在しない <BR> false 配置替え計画情報に存在する
     * @throws ReadWriteException データベースエラー
     */
    protected boolean checkPlan(String palletId)
            throws ReadWriteException
    {
        ReArrangePlanHandler rapHandler = new ReArrangePlanHandler(getConnection());
        ReArrangePlanSearchKey rapKey = new ReArrangePlanSearchKey();

        rapKey.setPalletId(palletId);

        // 配置替え計画情報に同一パレットが存在する場合は、NG
        if (rapHandler.count(rapKey) > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * ソフトゾーン最適化用に取得した棚が妥当かチェックします。<BR>
     * 
     * @param checkPallet パレット
     * @param sourceShelf 移動元棚
     * @param destShelf   移動先棚
     * @return true 配置替え計画情報に存在しない <BR> false 配置替え計画情報に存在する
     * @throws ReadWriteException データベースエラー
     */
    protected boolean checkSoftZoneShelf(Pallet checkPallet, Shelf sourceShelf, Shelf destShelf)
            throws ReadWriteException
    {
        // 移動元棚と移動先棚のソフトゾーンが同じ場合は移動を行わない
        if (sourceShelf.getSoftZoneId().equals(destShelf.getSoftZoneId()))
        {
            return false;
        }

        SoftZonePriorityHandler szpHandler = new SoftZonePriorityHandler(getConnection());
        SoftZonePrioritySearchKey szpKey = new SoftZonePrioritySearchKey();

        // ソフトゾーン優先順検索
        szpKey.setWhStationNo(checkPallet.getWhStationNo());
        szpKey.setSoftZoneId(checkPallet.getSoftZoneId());
        szpKey.setPriorityOrder(true);

        SoftZonePriority[] priorityArray = (SoftZonePriority[])szpHandler.find(szpKey);

        for (SoftZonePriority priority : priorityArray)
        {
            // 移動元棚のソフトゾーンと同じ場合は移動を行わない（移動元棚より移動先棚が優先順が低い）
            if (sourceShelf.getSoftZoneId().equals(priority.getPrioritySoftZone()))
            {
                return false;
            }

            // 移動先棚のソフトゾーンが一致した場合は移動を行う（移動元棚より移動先棚が優先順が高い）
            if (destShelf.getSoftZoneId().equals(priority.getPrioritySoftZone()))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * 荷姿最適化用に取得した棚が妥当かチェックします。<BR>
     * 
     * @param checkPallet パレット
     * @param sourceShelf 移動元棚
     * @param destShelf   移動先棚
     * @return true 配置替え計画情報に存在しない <BR> false 配置替え計画情報に存在する
     * @throws ReadWriteException データベースエラー
     */
    protected boolean checkLoadSizeShelf(Pallet checkPallet, Shelf sourceShelf, Shelf destShelf)
            throws ReadWriteException
    {
        // 移動元棚と移動先棚のハードゾーン、荷幅が同じ場合は移動を行わない
        if (sourceShelf.getHardZoneId().equals(destShelf.getHardZoneId())
                && sourceShelf.getWidth() == destShelf.getWidth())
        {
            return false;
        }

        HardZoneHandler hzHandler = new HardZoneHandler(getConnection());
        HardZoneSearchKey hzKey = new HardZoneSearchKey();

        // ハードゾーン検索
        hzKey.setWhStationNo(checkPallet.getWhStationNo());
        hzKey.setHeight(checkPallet.getHeight());
        HardZone[] hardzone = (HardZone[])hzHandler.find(hzKey);

        if (hardzone.length == 0)
        {
            return false;
        }

        //<jp> 倉庫情報をセットしてハードゾーンを全件保持する。</jp>
        //<en> Set the warehous data then preserve all hard zone data.</en>
        hzKey.clear();
        hzKey.setWhStationNo(checkPallet.getWhStationNo());
        HardZone[] tmpzone = (HardZone[])hzHandler.find(hzKey);

        String pri = hardzone[0].getPriority();
        List<HardZone> hardZoneList = new ArrayList<HardZone>();

        // 優先順にハードゾーン情報を取得する
        for (int i = 0; i < pri.length(); i++)
        {
            String tmpzn = pri.substring(i, i + 1);

            for (int l = 0; l < tmpzone.length; l++)
            {
                if (tmpzone[l].getHardZoneId().equals(tmpzn))
                {
                    hardZoneList.add(tmpzone[l]);
                }
            }
        }

        HardZone[] arrayhzone = (HardZone[])hardZoneList.toArray(new HardZone[0]);

        for (HardZone zone : arrayhzone)
        {
            // 移動元棚のハードゾーンと異なり、移動先のハードゾーンと一致した場合は移動を行う（移動元棚より移動先棚が優先順が高い）
            if (!sourceShelf.getHardZoneId().equals(zone.getHardZoneId())
                    && destShelf.getHardZoneId().equals(zone.getHardZoneId()))
            {
                return true;
            }
            // 移動元棚のハードゾーン、移動先のハードゾーンと一致した場合は荷幅チェックを行う
            else if (sourceShelf.getHardZoneId().equals(zone.getHardZoneId())
                    && destShelf.getHardZoneId().equals(zone.getHardZoneId()))
            {
                // パレットと同じ荷幅の場合は移動を行う
                if (checkPallet.getWidth() == destShelf.getWidth())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            // 移動元棚のハードゾーンと一致し、移動先のハードゾーンと異なる場合は移動を行わない（移動元棚より移動先棚が優先順が低い）
            else if (sourceShelf.getHardZoneId().equals(zone.getHardZoneId())
                    && !destShelf.getHardZoneId().equals(zone.getHardZoneId()))
            {
                return false;
            }
        }

        return false;
    }

    /**
     * 空棚最適化用に取得した棚が妥当かチェックします。<BR>
     * 
     * @param checkPallet パレット
     * @param sourceShelf 移動元棚
     * @param destShelf   移動先棚
     * @return true 配置替え計画情報に存在しない <BR> false 配置替え計画情報に存在する
     * @throws ReadWriteException データベースエラー
     */
    protected boolean checkVacantShelf(Pallet checkPallet, Shelf sourceShelf, Shelf destShelf)
            throws ReadWriteException
    {
        // 移動元棚と移動先棚のバンク、ベイ、レベルが同じ場合は移動を行わない
        if (sourceShelf.getBankNo() == destShelf.getBankNo() && sourceShelf.getBayNo() == destShelf.getBayNo()
                && sourceShelf.getLevelNo() == destShelf.getLevelNo())
        {
            return false;
        }

        ShelfHandler sHandler = new ShelfHandler(getConnection());
        ShelfSearchKey sKey = new ShelfSearchKey();

        // 移動元棚の同一バンク、ベイ、レベル内の空棚以外数取得
        sKey.clear();
        sKey.setWhStationNo(sourceShelf.getWhStationNo());
        sKey.setBankNo(sourceShelf.getBankNo());
        sKey.setBayNo(sourceShelf.getBayNo());
        sKey.setLevelNo(sourceShelf.getLevelNo());
        sKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY, "!=");
        int sourceCnt = sHandler.count(sKey);

        // 移動先棚の同一バンク、ベイ、レベル内の空棚以外数取得
        sKey.clear();
        sKey.setWhStationNo(destShelf.getWhStationNo());
        sKey.setBankNo(destShelf.getBankNo());
        sKey.setBayNo(destShelf.getBayNo());
        sKey.setLevelNo(destShelf.getLevelNo());
        sKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY, "!=");
        int destCnt = sHandler.count(sKey);

        // 移動元棚の使用件数 <= 移動先棚の使用件数の場合は配置替えを行う 
        if (sourceCnt <= destCnt)
        {
            return true;
        }

        return false;
    }

    /**
     * 配置替え計画情報を登録します。<BR>
     * 配置替え区分に該当するパレットを検索し、計画情報を作成します。<BR>
     * 既に取得パレットの計画情報が存在する場合は作成しません。<BR>
     * 
     * @param type 配置替え区分
     * @param setting 配置替え設定情報
     * @return 配置替え計画情報登録件数
     * @throws ReadWriteException データベースエラー
     * @throws InvalidDefineException パラメータが不正であるとき
     * @throws DataExistsException 登録済み
     */
    protected int createReArrangePlan(String type, ReArrangeSetting setting)
            throws ReadWriteException,
                InvalidDefineException,
                DataExistsException
    {
        ReArrangePlanHandler rapHandler = new ReArrangePlanHandler(getConnection());

        String palletSql = null;
        // ソフトゾーン最適化、荷姿最適化
        if (ReArrangePlan.REARRANGE_TYPE_SOFTZONE.equals(type) || ReArrangePlan.REARRANGE_TYPE_LOADSIZE.equals(type))
        {
            palletSql = createDifferentPalletSql(type, setting);
        }
        // 空棚最適化
        else if (ReArrangePlan.REARRANGE_TYPE_VACANT.equals(type))
        {
            palletSql = createLowOccupancyRatePalletSql(setting);
        }
        else
        {
            throw new InvalidDefineException("parameter is null");
        }

        DefaultDDBFinder ddFinder = new DefaultDDBFinder(getConnection(), new Pallet());
        ddFinder.open(true);

        int createCnt = 0;

        try
        {
            ddFinder.search(palletSql);

            while (ddFinder.hasNext())
            {
                // get entities (read ahead)
                Pallet[] palletArray = (Pallet[])ddFinder.getEntities(FINDER_READ_SIZE);

                for (Pallet pallet : palletArray)
                {
                    // 配置替え計画情報に存在する場合は次のデータへ
                    if (!checkPlan(pallet.getPalletId()))
                    {
                        continue;
                    }

                    // 配置替え計画情報登録
                    ReArrangePlan plan = new ReArrangePlan();

                    plan.setScheduleNo(setting.getScheduleNo());
                    plan.setWhStationNo(setting.getWhStationNo());
                    plan.setAisleStationNo(setting.getAisleStationNo());
                    plan.setRearrangeType(type);
                    plan.setPalletId(pallet.getPalletId());
                    plan.setStatusFlag(ReArrangePlan.STATUS_FLAG_UNSTART);
                    plan.setRegistPname(getCallerName());
                    plan.setRegistDate(new SysDate());
                    plan.setLastUpdatePname(getCallerName());

                    rapHandler.create(plan);

                    createCnt++;
                }
            } // end while loop of Stock Finder
        }
        finally
        {
            ddFinder.close();
        }

        return createCnt;
    }

    /**
     * パレットと棚の値が異なるパレットを検索するSQLを生成します。<BR>
     * ソフトゾーン最適化の場合はソフトゾーンが異なるパレット、荷姿最適化の場合は
     * 荷幅か荷姿が異なるパレットを検索します。
     * 
     * @param type 配置替え区分
     * @param setting 配置替え設定情報
     * @return 検索Sql
     * @throws ReadWriteException データベースエラー
     */
    protected String createDifferentPalletSql(String type, ReArrangeSetting setting)
            throws ReadWriteException
    {
        StringBuffer palletSql = new StringBuffer();
        palletSql.append("SELECT DNPALLET.* ");
        palletSql.append(" FROM DNPALLET, DMSHELF ");
        // ソフトゾーン最適化
        if (ReArrangePlan.REARRANGE_TYPE_SOFTZONE.equals(type))
        {
            palletSql.append(", (SELECT DMSOFTZONEPRIORITY.WH_STATION_NO, DMSOFTZONEPRIORITY.SOFT_ZONE_ID, DMSOFTZONEPRIORITY.PRIORITY_SOFT_ZONE ");
            palletSql.append(" FROM DMSOFTZONEPRIORITY, ");
            palletSql.append(" (SELECT WH_STATION_NO, SOFT_ZONE_ID, MIN(PRIORITY) PRIORITY ");
            palletSql.append(" FROM DMSOFTZONEPRIORITY ");
            palletSql.append(" GROUP BY WH_STATION_NO, SOFT_ZONE_ID) A ");
            palletSql.append(" WHERE DMSOFTZONEPRIORITY.WH_STATION_NO = A.WH_STATION_NO ");
            palletSql.append(" AND DMSOFTZONEPRIORITY.SOFT_ZONE_ID = A.SOFT_ZONE_ID ");
            palletSql.append(" AND DMSOFTZONEPRIORITY.PRIORITY = A.PRIORITY) SOFTZONE ");
        }
        // 荷姿最適化
        else if (ReArrangePlan.REARRANGE_TYPE_LOADSIZE.equals(type))
        {
            palletSql.append(", DMHARDZONE");
        }
        palletSql.append(" WHERE ");
        palletSql.append(" DNPALLET.STATUS_FLAG = ");
        palletSql.append(DBFormat.format(Pallet.PALLET_STATUS_REGULAR));
        palletSql.append(" AND DNPALLET.ALLOCATION_FLAG = ");
        palletSql.append(DBFormat.format(Pallet.ALLOCATION_FLAG_NOT_ALLOCATED));
        palletSql.append(" AND DMSHELF.WH_STATION_NO = ");
        palletSql.append(DBFormat.format(setting.getWhStationNo()));
        palletSql.append(" AND DMSHELF.PARENT_STATION_NO = ");
        palletSql.append(DBFormat.format(setting.getAisleStationNo()));
        palletSql.append(" AND DMSHELF.PROHIBITION_FLAG = ");
        palletSql.append(DBFormat.format(Shelf.PROHIBITION_FLAG_OK));
        palletSql.append(" AND DMSHELF.STATUS_FLAG = ");
        palletSql.append(DBFormat.format(Shelf.LOCATION_STATUS_FLAG_STORAGED));
        palletSql.append(" AND DMSHELF.ACCESS_NG_FLAG = ");
        palletSql.append(DBFormat.format(Shelf.ACCESS_NG_FLAG_OK));
        palletSql.append(" AND DNPALLET.CURRENT_STATION_NO = DMSHELF.STATION_NO");
        // ソフトゾーン最適化
        if (ReArrangePlan.REARRANGE_TYPE_SOFTZONE.equals(type))
        {
            palletSql.append(" AND DNPALLET.WH_STATION_NO = SOFTZONE.WH_STATION_NO");
            palletSql.append(" AND DNPALLET.SOFT_ZONE_ID = SOFTZONE.SOFT_ZONE_ID");
            palletSql.append(" AND DMSHELF.SOFT_ZONE_ID != SOFTZONE.PRIORITY_SOFT_ZONE");
        }
        // 荷姿最適化
        else if (ReArrangePlan.REARRANGE_TYPE_LOADSIZE.equals(type))
        {
            palletSql.append(" AND DMSHELF.HARD_ZONE_ID = DMHARDZONE.HARD_ZONE_ID");
            palletSql.append(" AND (DNPALLET.WIDTH != DMSHELF.WIDTH");
            palletSql.append(" OR DNPALLET.HEIGHT != DMHARDZONE.HEIGHT)");
        }
        palletSql.append(" ORDER BY DNPALLET.CURRENT_STATION_NO");

        // ソフトゾーン最適化
        //        SELECT
        //        DNPALLET.*
        //    FROM
        //        DNPALLET
        //        ,DMSHELF
        //        ,(
        //            SELECT
        //                    DMSOFTZONEPRIORITY.WH_STATION_NO
        //                    ,DMSOFTZONEPRIORITY.SOFT_ZONE_ID
        //                    ,DMSOFTZONEPRIORITY.PRIORITY_SOFT_ZONE
        //                FROM
        //                    DMSOFTZONEPRIORITY
        //                    ,(
        //                        SELECT
        //                                WH_STATION_NO
        //                                ,SOFT_ZONE_ID
        //                                ,MIN(PRIORITY) PRIORITY
        //                            FROM
        //                                DMSOFTZONEPRIORITY
        //                            GROUP BY
        //                                WH_STATION_NO
        //                                ,SOFT_ZONE_ID
        //                    ) A
        //                WHERE
        //                    DMSOFTZONEPRIORITY.WH_STATION_NO = A.WH_STATION_NO
        //                    AND DMSOFTZONEPRIORITY.SOFT_ZONE_ID = A.SOFT_ZONE_ID
        //                    AND DMSOFTZONEPRIORITY.PRIORITY = A.PRIORITY
        //        ) SOFTZONE
        //    WHERE
        //        DNPALLET.STATUS_FLAG = '2'
        //        AND DNPALLET.ALLOCATION_FLAG = '0'
        //        AND DMSHELF.WH_STATION_NO = '9200'
        //        AND DMSHELF.PARENT_STATION_NO = '9201'
        //        AND DMSHELF.PROHIBITION_FLAG = '0'
        //        AND DMSHELF.STATUS_FLAG = '1'
        //        AND DMSHELF.ACCESS_NG_FLAG = '0'
        //        AND DNPALLET.CURRENT_STATION_NO = DMSHELF.STATION_NO
        //        AND DNPALLET.WH_STATION_NO = SOFTZONE.WH_STATION_NO
        //        AND DNPALLET.SOFT_ZONE_ID = SOFTZONE.SOFT_ZONE_ID
        //        AND DMSHELF.SOFT_ZONE_ID != SOFTZONE.PRIORITY_SOFT_ZONE
        //    ORDER BY
        //        DNPALLET.CURRENT_STATION_NO

        // 荷姿最適化
        //        SELECT
        //        DNPALLET.*
        //    FROM
        //        DNPALLET
        //        ,DMSHELF
        //        ,DMHARDZONE
        //    WHERE
        //        DNPALLET.STATUS_FLAG = '2'
        //        AND DNPALLET.ALLOCATION_FLAG = '0'
        //        AND DMSHELF.WH_STATION_NO = '9200'
        //        AND DMSHELF.PARENT_STATION_NO = '9201'
        //        AND DMSHELF.PROHIBITION_FLAG = '0'
        //        AND DMSHELF.STATUS_FLAG = '1'
        //        AND DMSHELF.ACCESS_NG_FLAG = '0'
        //        AND DNPALLET.CURRENT_STATION_NO = DMSHELF.STATION_NO
        //        AND DMSHELF.HARD_ZONE_ID = DMHARDZONE.HARD_ZONE_ID
        //        AND (
        //            DNPALLET.WIDTH != DMSHELF.WIDTH
        //            OR DNPALLET.HEIGHT != DMHARDZONE.HEIGHT
        //        )
        //    ORDER BY
        //        DNPALLET.CURRENT_STATION_NO

        return new String(palletSql);
    }

    /**
     * 収容数が半分以下のベイのパレットを検索するSQLを生成します。<BR>
     * 
     * @param setting 配置替え設定情報
     * @return 検索Sql
     * @throws ReadWriteException データベースエラー
     */
    protected String createLowOccupancyRatePalletSql(ReArrangeSetting setting)
            throws ReadWriteException
    {
        StringBuffer palletSql = new StringBuffer();
        palletSql.append("SELECT DNPALLET.* ");
        palletSql.append(" FROM DNPALLET, DMSHELF, DMWIDTH ");
        palletSql.append(" ,(SELECT DMSHELF.WH_STATION_NO, DMSHELF.BANK_NO, DMSHELF.BAY_NO, DMSHELF.LEVEL_NO, ");
        palletSql.append("     FLOOR(SUM(DECODE(DMSHELF.STATUS_FLAG, '1', 1, 0)) / ");
        palletSql.append("     SUM(DECODE(DMSHELF.LOCATION_USE_FLAG, '0', 1, 0)) * 100) RATE ");
        palletSql.append("     FROM DMSHELF ");
        palletSql.append("     GROUP BY DMSHELF.WH_STATION_NO, DMSHELF.BANK_NO, DMSHELF.BAY_NO, DMSHELF.LEVEL_NO) A ");
        palletSql.append(" WHERE ");
        palletSql.append(" DNPALLET.STATUS_FLAG = ");
        palletSql.append(DBFormat.format(Pallet.PALLET_STATUS_REGULAR));
        palletSql.append(" AND DNPALLET.ALLOCATION_FLAG = ");
        palletSql.append(DBFormat.format(Pallet.ALLOCATION_FLAG_NOT_ALLOCATED));
        palletSql.append(" AND DMSHELF.WH_STATION_NO = ");
        palletSql.append(DBFormat.format(setting.getWhStationNo()));
        palletSql.append(" AND DMSHELF.PARENT_STATION_NO = ");
        palletSql.append(DBFormat.format(setting.getAisleStationNo()));
        palletSql.append(" AND DMSHELF.PROHIBITION_FLAG = ");
        palletSql.append(DBFormat.format(Shelf.PROHIBITION_FLAG_OK));
        palletSql.append(" AND DMSHELF.STATUS_FLAG = ");
        palletSql.append(DBFormat.format(Shelf.LOCATION_STATUS_FLAG_STORAGED));
        palletSql.append(" AND DMSHELF.ACCESS_NG_FLAG = ");
        palletSql.append(DBFormat.format(Shelf.ACCESS_NG_FLAG_OK));
        palletSql.append(" AND DMSHELF.WIDTH != ");
        palletSql.append(Shelf.WIDTH_FREE);
        palletSql.append(" AND DNPALLET.CURRENT_STATION_NO = DMSHELF.STATION_NO ");
        palletSql.append(" AND DMSHELF.WH_STATION_NO = DMWIDTH.WH_STATION_NO ");
        palletSql.append(" AND DMSHELF.BANK_NO >= DMWIDTH.START_BANK_NO ");
        palletSql.append(" AND DMSHELF.BANK_NO <= DMWIDTH.END_BANK_NO ");
        palletSql.append(" AND DMSHELF.BAY_NO >= DMWIDTH.START_BAY_NO ");
        palletSql.append(" AND DMSHELF.BAY_NO <= DMWIDTH.END_BAY_NO ");
        palletSql.append(" AND DMSHELF.LEVEL_NO >= DMWIDTH.START_LEVEL_NO ");
        palletSql.append(" AND DMSHELF.LEVEL_NO <= DMWIDTH.END_LEVEL_NO ");
        palletSql.append(" AND DMSHELF.WIDTH = DMWIDTH.WIDTH ");
        palletSql.append(" AND DMSHELF.WH_STATION_NO = A.WH_STATION_NO ");
        palletSql.append(" AND DMSHELF.BANK_NO = A.BANK_NO ");
        palletSql.append(" AND DMSHELF.BAY_NO = A.BAY_NO ");
        palletSql.append(" AND DMSHELF.LEVEL_NO = A.LEVEL_NO ");
        palletSql.append(" AND A.RATE <= ");
        palletSql.append(REARRANGE_RATE);
        palletSql.append(" ORDER BY DNPALLET.CURRENT_STATION_NO");

        //        SELECT
        //        DNPALLET.*
        //    FROM
        //        DNPALLET
        //        ,DMSHELF
        //        ,DMWIDTH
        //        ,(
        //            SELECT
        //                    DMSHELF.WH_STATION_NO
        //                    ,DMSHELF.BANK_NO
        //                    ,DMSHELF.BAY_NO
        //                    ,DMSHELF.LEVEL_NO
        //                    ,FLOOR(SUM(DECODE(DMSHELF.STATUS_FLAG
        //                        ,'1', 1
        //                        ,0
        //                    )) / SUM(DECODE(DMSHELF.LOCATION_USE_FLAG
        //                        ,'0', 1
        //                        ,0
        //                    )) * 100) RATE
        //                FROM
        //                    DMSHELF
        //                GROUP BY
        //                    DMSHELF.WH_STATION_NO
        //                    ,DMSHELF.BANK_NO
        //                    ,DMSHELF.BAY_NO
        //                    ,DMSHELF.LEVEL_NO
        //        ) A
        //    WHERE
        //        DNPALLET.STATUS_FLAG = '2'
        //        AND DNPALLET.ALLOCATION_FLAG = '0'
        //        AND DMSHELF.WH_STATION_NO = '9200'
        //        AND DMSHELF.PARENT_STATION_NO = '9201'
        //        AND DMSHELF.PROHIBITION_FLAG = '0'
        //        AND DMSHELF.STATUS_FLAG = '1'
        //        AND DMSHELF.ACCESS_NG_FLAG = '0'
        //        AND DMSHELF.WIDTH != 0
        //        AND DNPALLET.CURRENT_STATION_NO = DMSHELF.STATION_NO
        //        AND DMSHELF.WH_STATION_NO = DMWIDTH.WH_STATION_NO
        //        AND DMSHELF.BANK_NO >= DMWIDTH.START_BANK_NO
        //        AND DMSHELF.BANK_NO <= DMWIDTH.END_BANK_NO
        //        AND DMSHELF.BAY_NO >= DMWIDTH.START_BAY_NO
        //        AND DMSHELF.BAY_NO <= DMWIDTH.END_BAY_NO
        //        AND DMSHELF.LEVEL_NO >= DMWIDTH.START_LEVEL_NO
        //        AND DMSHELF.LEVEL_NO <= DMWIDTH.END_LEVEL_NO
        //        AND DMSHELF.WIDTH = DMWIDTH.WIDTH
        //        AND DMSHELF.WH_STATION_NO = A.WH_STATION_NO
        //        AND DMSHELF.BANK_NO = A.BANK_NO
        //        AND DMSHELF.BAY_NO = A.BAY_NO
        //        AND DMSHELF.LEVEL_NO = A.LEVEL_NO
        //        AND A.RATE <= 50
        //    ORDER BY
        //        DNPALLET.CURRENT_STATION_NO

        return new String(palletSql);
    }

    /**
     * 配置替え計画情報を検索するキーを生成します。
     * 
     * @param setting 配置替え設定情報
     * <ol>
     * 以下の項目を参照します。
     * <li>スケジュールNo.
     * <li>倉庫ステーションNo.
     * </ol>
     * 
     * @return 検索キー
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException ステーション定義異常 
     * @throws NoPrimaryException エリアマスタに同一エリアが複数登録されている
     */
    protected ReArrangePlanSearchKey createPlanSearchKey(ReArrangeSetting setting)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException
    {
        ReArrangePlanSearchKey planSKey = new ReArrangePlanSearchKey();

        // 配置替え計画ファイル取得
        planSKey.clear();
        planSKey.setScheduleNo(setting.getScheduleNo());
        planSKey.setStatusFlag(ReArrangePlan.STATUS_FLAG_UNSTART);
        planSKey.setKey(Pallet.STATUS_FLAG, Pallet.PALLET_STATUS_REGULAR);
        planSKey.setKey(Pallet.ALLOCATION_FLAG, Pallet.ALLOCATION_FLAG_NOT_ALLOCATED);
        planSKey.setJoin(ReArrangePlan.PALLET_ID, Pallet.PALLET_ID);
        planSKey.setJoin(Pallet.CURRENT_STATION_NO, Shelf.STATION_NO);

        String strDirection = getAreaCtlr().getVacantSearchType(setting.getWhStationNo());
        if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_HP.equals(strDirection))
        {
            // レベル方向検索(HP側から):HP手前
            // LEVEL, BAY, BANK
            planSKey.setOrder(Shelf.BAY_NO, true);
            planSKey.setOrder(Shelf.LEVEL_NO, true);
            planSKey.setOrder(Shelf.BANK_NO, true);
        }
        else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_HP.equals(strDirection))
        {
            // ベイ方向検索(HP側から):HP下段
            // BAY, LEVEL, BANK
            planSKey.setOrder(Shelf.LEVEL_NO, true);
            planSKey.setOrder(Shelf.BAY_NO, true);
            planSKey.setOrder(Shelf.BANK_NO, true);
        }
        else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_LEVEL_OP.equals(strDirection))
        {
            // レベル方向検索(OP側から):OP手前
            // LEVEL, BAY DESC, BANK
            planSKey.setOrder(Shelf.BAY_NO, false);
            planSKey.setOrder(Shelf.LEVEL_NO, true);
            planSKey.setOrder(Shelf.BANK_NO, true);
        }
        else if (SystemDefine.VACANT_SEARCH_TYPE_ASRS_BAY_OP.equals(strDirection))
        {
            // ベイ方向検索(OP側から):OP下段
            // BAY DESC, LEVEL, BANK
            planSKey.setOrder(Shelf.LEVEL_NO, true);
            planSKey.setOrder(Shelf.BAY_NO, false);
            planSKey.setOrder(Shelf.BANK_NO, true);
        }
        planSKey.setOrder(Shelf.ADDRESS_NO, true);

        return planSKey;
    }

    /**<jp>
     * ゾーン情報を作成します
     * 配置替え区分によって優先ゾーンを作成します。
     * @param plt パレット情報
     * @param type 配置替え設定タイプ
     * @return <code>Zone</code>インスタンス
     * @throws InvalidDefineException 該当倉庫に対する空棚検索方向が定められていないときに通知します。
     * @throws ReadWriteException データアクセスに失敗した場合に通知されます。
     </jp>*/
    protected Zone[] makeZone(Pallet plt, String type)
            throws InvalidDefineException,
                ReadWriteException
    {
        Zone[] arrayzone = null;
        List<Zone> zoneList = new ArrayList<Zone>();

        String whNo = plt.getWhStationNo();

        // ソフトゾーン検索
        SoftZonePriorityHandler szHandler = new SoftZonePriorityHandler(getConnection());
        SoftZonePrioritySearchKey szKey = new SoftZonePrioritySearchKey();

        szKey.setPrioritySoftZoneCollect();
        szKey.setWhStationNo(whNo);
        if (!SoftZone.SOFT_ZONE_FREE.equals(plt.getSoftZoneId()))
        {
            szKey.setSoftZoneId(plt.getSoftZoneId());
        }
        szKey.setPriorityOrder(true);

        // ソフトゾーンを生成します。
        SoftZonePriority[] softZones = (SoftZonePriority[])szHandler.find(szKey);

        HardZoneHandler hzHandler = new HardZoneHandler(getConnection());
        HardZoneSearchKey hzKey = new HardZoneSearchKey();

        // ハードゾーン検索
        hzKey.setWhStationNo(whNo);
        hzKey.setHeight(plt.getHeight());
        HardZone[] hardzone = (HardZone[])hzHandler.find(hzKey);

        if (hardzone.length == 0)
        {
            return null;
        }

        hzKey.clear();
        hzKey.setWhStationNo(plt.getWhStationNo());
        HardZone[] tmpzone = (HardZone[])hzHandler.find(hzKey);

        String pri = hardzone[0].getPriority();
        List<HardZone> hardZoneList = new ArrayList<HardZone>();

        // 優先順にハードゾーン情報を取得する
        for (int i = 0; i < pri.length(); i++)
        {
            String tmpzn = pri.substring(i, i + 1);

            for (int l = 0; l < tmpzone.length; l++)
            {
                if (tmpzone[l].getHardZoneId().equals(tmpzn))
                {
                    hardZoneList.add(tmpzone[l]);
                }
            }
        }

        HardZone[] hardZones = (HardZone[])hardZoneList.toArray(new HardZone[0]);

        // ハードゾーン最適化の場合
        if (ReArrangePlan.REARRANGE_TYPE_LOADSIZE.equals(type))
        {
            // ハードゾーン優先
            for (HardZone hard : hardZones)
            {
                for (SoftZonePriority soft : softZones)
                {
                    Zone addZone = new Zone();
                    addZone.setSoftZoneID(soft.getPrioritySoftZone());
                    addZone.setHardZone(hard);
                    addZone.setWHStationNo(whNo);
                    zoneList.add(addZone);
                }
            }
        }
        // ハードゾーン最適化以外の場合
        else
        {
            // ソフトゾーン優先
            for (SoftZonePriority soft : softZones)
            {
                for (HardZone hard : hardZones)
                {
                    Zone addZone = new Zone();
                    addZone.setSoftZoneID(soft.getPrioritySoftZone());
                    addZone.setHardZone(hard);
                    addZone.setWHStationNo(whNo);
                    zoneList.add(addZone);
                }
            }
        }

        arrayzone = (Zone[])zoneList.toArray(new Zone[0]);

        return arrayzone;
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
        return "$Id: AsrsReArrangeOperator.java 7926 2010-05-14 08:09:04Z shibamoto $";
    }
}
