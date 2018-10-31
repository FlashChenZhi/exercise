// $Id: RetrievalOrderStartSCH.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderStartSCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.co.daifuku.authentication.DfkUserInfo;
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
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.HostSendController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.controller.WorkInfoController;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.AllocatePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.AllocatePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WarenaviSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WarenaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WarenaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.AllocatePriority;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.WarenaviSystem;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;
import jp.co.daifuku.wms.replenish.dasch.AsReplenishWorkDASCH;
import jp.co.daifuku.wms.replenish.dasch.AsReplenishWorkDASCHParams;
import jp.co.daifuku.wms.replenish.dasch.ReplenishWorkDASCH;
import jp.co.daifuku.wms.replenish.dasch.ReplenishWorkDASCHParams;
import jp.co.daifuku.wms.replenish.exporter.AsrsReplenishmentListParams;
import jp.co.daifuku.wms.replenish.exporter.ReplenishmentListParams;
import jp.co.daifuku.wms.retrieval.allocate.AbstractAllocateOperator;
import jp.co.daifuku.wms.retrieval.allocate.EmergencyReplenishCaseOperator;
import jp.co.daifuku.wms.retrieval.allocate.RetrievalAllocateOperator;
import jp.co.daifuku.wms.retrieval.allocate.ShortageOperator;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalShortageInquiryDASCH;
import jp.co.daifuku.wms.retrieval.dasch.RetrievalShortageInquiryDASCHParams;
import jp.co.daifuku.wms.retrieval.exporter.ShortageCheckListParams;

/**
 * 出庫開始のスケジュール処理を行います。
 *
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class RetrievalOrderStartSCH
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

    /**
     * 画面の行No.と引当結果の対応表
     */
    private Map<Integer, String> _rowStatusMap = new HashMap<Integer, String>();

    /**
     * 確認ダイアログを表示するかどうか
     */
    private boolean _confirmFlag = false;

    /**
     * 引当結果を保持する
     */
    private RetrievalOutParameter[] _outParam = null;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public RetrievalOrderStartSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
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
        RetrievalOrderStartSCHParams inParam = (RetrievalOrderStartSCHParams)p;
        AbstractDBFinder finder = null;
        try
        {
            finder = new RetrievalPlanFinder(getConnection());
            finder.open(true);

            // search db and get display count.
            int dispCount = finder.search(createSearchKey(inParam));
            // check and set message by the display count.
            if (!canLowerDisplay(dispCount))
            {
                return new ArrayList<Params>();
            }

            return getDisplayData(finder);
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
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

        // 引当中フラグを更新した為1回コミットする
        doCommit(getClass());

        try
        {
            RetrievalInParameter param = null;

            List<RetrievalInParameter> listParam = new ArrayList<RetrievalInParameter>();

            for (ScheduleParams startParam : ps)
            {
                param = new RetrievalInParameter(getWmsUserInfo());

                param.setRetrievalPlanDay(WmsFormatter.toParamDate(startParam.getDate(RetrievalOrderStartSCHParams.RETRIEVAL_PLAN_DATE)));
                param.setConsignorCode(startParam.getString(RetrievalOrderStartSCHParams.CONSIGNOR_CODE));
                param.setBatchNo(startParam.getString(RetrievalOrderStartSCHParams.BATCH_NO));
                param.setOrderNo(startParam.getString(RetrievalOrderStartSCHParams.ORDER_NO));
                param.setCustomerCode(startParam.getString(RetrievalOrderStartSCHParams.CUSTOMER_CODE));
                param.setCustomerName(startParam.getString(RetrievalOrderStartSCHParams.CUSTOMER_NAME));
                param.setAllocateNo(startParam.getString(RetrievalOrderStartSCHParams.ALLOCATED_PATTERN));
                param.setShortageCompletionFlag(startParam.getBoolean(RetrievalOrderStartSCHParams.SHORTAGE_COMPLETION_FLAG));
                param.setRowNo(startParam.getRowIndex());
                listParam.add(param);
            }
            RetrievalInParameter[] inParams = new RetrievalInParameter[listParam.size()];
            listParam.toArray(inParams);
            WMSSequenceHandler seq = new WMSSequenceHandler(getConnection());

            boolean isShortageFinish = inParams[0].isShortageCompletionFlag(); // 欠品完了フラグ
            String patternNo = inParams[0].getAllocateNo(); // 引当パターンNo.
            String startUnitKey = seq.nextStartUnitKey();// 出庫開始単位キー
            Date retrievalStartDate = WmsFormatter.toDateTime(DbDateUtil.getSystemDateTime()); // 出庫開始日時

            // 引当処理を行う
            RetrievalInParameter[] shortages =
                    allocate(inParams, patternNo, startUnitKey, retrievalStartDate, isShortageFinish);

            // 欠品がなかった場合は正常終了
            if (shortages == null)
            {
                // 6021021=開始しました。
                setMessageNoOverride("6021021");
                return true;
            }

            // 欠品完了しない場合
            if (!isShortageFinish)
            {
                // 補充処理を行う
                replenish(shortages, patternNo, startUnitKey, retrievalStartDate);
            }

            // 6021021=開始しました。
            setMessageNoOverride("6021021");

            return true;
        }
        finally
        {
            // 引当中フラグを元に戻す
            updateRetrievalAllocate(WarenaviSystem.PROCESS_COMPLETED);
            // 引当中フラグの更新を確定
            doCommit(getClass());

            // 行No.と引当結果の対応表をパラメータ配列に変換する
            List<RetrievalOutParameter> outList = new ArrayList<RetrievalOutParameter>();

            Iterator<Map.Entry<Integer, String>> it = _rowStatusMap.entrySet().iterator();

            while (it.hasNext())
            {
                Map.Entry<Integer, String> entry = it.next();

                RetrievalOutParameter out = new RetrievalOutParameter();
                // 行No.
                out.setRowNo(entry.getKey());
                // 引当結果
                out.setAllocateResult(entry.getValue());

                outList.add(out);
            }

            _outParam = new RetrievalOutParameter[outList.size()];
            outList.toArray(_outParam);
        }
    }

    /**
     * パラメータの内容が正しいかチェックを行います。<BR>
     * <CODE>checkParam</CODE>で指定されたパラメータにセットされた内容に従い、
     * パラメータ入力内容チェック処理を行います。<BR>
     * @param p 入力チェックを行う内容が含まれたパラメータクラス
     * @return true：入力内容が正常な場合  false：そうでない場合
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean check(ScheduleParams p)
            throws CommonException
    {
        //        RetrievalInParameter inParam = (RetrievalInParameter)checkParam;

        // 引当パターンに紐つくエリアを取得
        Area[] areas = getAllocatePatternArea(p.getString(ALLOCATED_PATTERN));

        // ASRSエリアが存在しないならチェックしない
        if (!hasAsrsArea(areas))
        {
            return true;
        }

        // 全てASRSエリアの場合
        if (isAllAsrsArea(areas))
        {
            // オンラインの正常設備が存在しない場合
            if (!hasOnlineNormalStatusEquipment(areas))
            {
                // オンラインの正常な設備が存在しません。
                setMessage("6023118");
                _confirmFlag = false;
                return false;
            }

            for (Area area : areas)
            {
                Aisle[] aisles = getAisles(area);
                for (Aisle aisle : aisles)
                {
                    // オンラインの故障中(異常)の設備が存在す場合
                    if (hasOnlineStatusErrorEquipment(aisle))
                    {
                        // 故障中の設備が存在します。よろしいですか？
                        setMessage("MSG-W0038");
                        _confirmFlag = true;
                        return false;
                    }
                }
            }
        }
        // 平置きとASRSが混合している場合
        else
        {
            for (Area area : areas)
            {
                // 平置きの場合はチェックしない
                if (!Area.AREA_TYPE_ASRS.equals(area.getAreaType()))
                {
                    continue;
                }

                Aisle[] aisles = getAisles(area);

                for (Aisle aisle : aisles)
                {
                    if (hasOffLineAGC(aisle))
                    {
                        // オフラインのAGCが存在します。よろしいですか？
                        setMessage("MSG-W0037");
                        _confirmFlag = true;
                        return false;
                    }
                    // オンラインの故障中(異常)の設備が存在す場合
                    if (hasOnlineStatusErrorEquipment(aisle))
                    {
                        // 故障中の設備が存在します。よろしいですか？
                        setMessage("MSG-W0038");
                        _confirmFlag = true;
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * 未作業の緊急補充作業が存在するかどうかのチェックを行います。<BR>
     *
     * @param checkParam チェックする値が設定されているパラメータ
     * @return 未作業の緊急補充作業が存在する場合はtrue
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public boolean hasEmergencyReplenishment(Parameter checkParam)
            throws CommonException
    {
        MoveWorkInfoHandler handler = new MoveWorkInfoHandler(getConnection());
        MoveWorkInfoSearchKey key = new MoveWorkInfoSearchKey();

        // 作業区分(緊急補充)
        key.setJobType(MoveWorkInfo.JOB_TYPE_EMERGENCY_REPLENISHMENT);
        // 状態フラグ(未開始)
        key.setStatusFlag(MoveWorkInfo.STATUS_FLAG_UNSTART);

        if (handler.count(key) != 0)
        {
            return true;
        }
        return false;
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * 確認フラグを返します。
     * @return 確認フラグを返します。
     */
    public boolean isConfirmFlag()
    {
        return _confirmFlag;
    }

    /**
     * 引当結果を返します。
     * @return 引当結果を返します。
     */
    public RetrievalOutParameter[] getOutParam()
    {
        return _outParam;
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(AbstractDBFinder finder)
            throws ReadWriteException
    {
        // 最大表示件数分検索結果を取得する
        RetrievalPlan[] entities = (RetrievalPlan[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);
        List<Params> result = new ArrayList<Params>();

        for (RetrievalPlan ent : entities)
        {
            Params param = new Params();

            // 返却データをセットする

            // バッチNo.
            param.set(RetrievalOrderStartSCHParams.BATCH_NO, ent.getBatchNo());
            // オーダーNo.
            param.set(RetrievalOrderStartSCHParams.ORDER_NO, ent.getOrderNo());
            // 出荷先コード
            param.set(RetrievalOrderStartSCHParams.CUSTOMER_CODE, ent.getCustomerCode());
            // 出荷先名称
            param.set(RetrievalOrderStartSCHParams.CUSTOMER_NAME, ent.getValue(Customer.CUSTOMER_NAME));
            // 明細数
            param.set(RetrievalOrderStartSCHParams.DETAIL_COUNT, Formatter.getLong(ent.getPlanUkey()));
            // 引当結果(初期表示は未引当)
            param.set(RetrievalOrderStartSCHParams.ALLOCATION_RESULT,
                    DisplayResource.getAllocateResult(RetrievalInParameter.ALLOCATE_RESULT_UNSTART));

            result.add(param);
        }

        return result;
    }

    /**
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {

        RetrievalPlanSearchKey searchKey = new RetrievalPlanSearchKey();
        // set collect
        searchKey.setConsignorCodeCollect();
        searchKey.setPlanDayCollect();
        searchKey.setBatchNoCollect();
        searchKey.setOrderNoCollect();
        searchKey.setCustomerCodeCollect();
        searchKey.setCollect(Customer.CUSTOMER_NAME);
        searchKey.setPlanUkeyCollect("COUNT");

        // set join
        searchKey.setJoin(RetrievalPlan.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        searchKey.setJoin(RetrievalPlan.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");

        // set where
        searchKey.setConsignorCode(p.getString(RetrievalOrderStartSCHParams.CONSIGNOR_CODE));
        searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(RetrievalOrderStartSCHParams.RETRIEVAL_PLAN_DATE)));
        if (!StringUtil.isBlank(p.getString(RetrievalOrderStartSCHParams.BATCH_NO)))
        {
            searchKey.setBatchNo(p.getString(RetrievalOrderStartSCHParams.BATCH_NO));
        }

        String[] loc =
                WmsFormatter.getFromTo(p.getString(RetrievalOrderStartSCHParams.ORDER_NO_FROM),
                        p.getString(RetrievalOrderStartSCHParams.ORDER_NO_TO));

        // オーダーNo.(From)
        if (!StringUtil.isBlank(loc[0]))
        {

            searchKey.setOrderNo(loc[0], ">=");

        }
        // オーダーNo.(To)
        if (!StringUtil.isBlank(loc[1]))
        {

            searchKey.setOrderNo(loc[1], "<=");

        }

        // 削除以外
        searchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        // 未スケジュール
        searchKey.setSchFlag(RetrievalPlan.SCH_FLAG_NOT_SCHEDULE);

        // set group
        searchKey.setConsignorCodeGroup();
        searchKey.setPlanDayGroup();
        searchKey.setBatchNoGroup();
        searchKey.setOrderNoGroup();
        searchKey.setCustomerCodeGroup();
        searchKey.setGroup(Customer.CUSTOMER_NAME);

        // set order by
        searchKey.setBatchNoOrder(true);
        searchKey.setOrderNoOrder(true);
        searchKey.setCustomerCodeOrder(true);

        return searchKey;
    }


    /**
     * オンラインの正常な設備が存在するかどうかを判定します。<BR>
     *
     * @param areas エリア
     * @return グループコントローラがオンラインでかつ1件でも正常な状態の設備あり : true それ以外 : false
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException エリアに紐つくアイルが存在しない場合又は、アイルに紐つくステーションが存在しない場合にスローされます。
     */
    protected boolean hasOnlineNormalStatusEquipment(Area[] areas)
            throws ReadWriteException,
                InvalidDefineException
    {
        for (Area area : areas)
        {
            // エリアに紐つくアイルの取得
            Aisle[] aisles = getAisles(area);
            for (Aisle aisle : aisles)
            {
                // オンラインの場合
                if (isOnLine(aisle.getValue(GroupController.STATUS_FLAG)))
                {
                    // 通常設備の場合
                    if (isAisleStatusNormal(aisle.getStatus()))
                    {
                        // アイルに紐つくステーションを取得
                        Station[] stations = getStations(aisle);
                        for (Station station : stations)
                        {
                            // オンラインの正常な設備の場合
                            if (isOnLine(station.getValue(GroupController.STATUS_FLAG))
                                    && isStatusStatusNormal(station.getStatus()))
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * オンラインで異常な設備があるかどうかを判定します。<BR>
     *
     * @param aisle アイル
     * @return 故障中の設備あり : true それ以外 : false
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException アイルに紐つくステーションが存在しない場合にスローされます。
     */
    protected boolean hasOnlineStatusErrorEquipment(Aisle aisle)
            throws InvalidDefineException,
                ReadWriteException
    {
        if (isOnLine(aisle.getValue(GroupController.STATUS_FLAG)))
        {
            if (isAisleStatusError(aisle.getStatus()))
            {
                return true;
            }

            Station[] stations = getStations(aisle);
            for (Station station : stations)
            {
                if (isOnLine(station.getValue(GroupController.STATUS_FLAG))
                        && isStationStatusError(station.getStatus()))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 1件でもオフラインのAGCが存在するかどうかを判定します。<BR>
     *
     * @param aisle アイル
     * @return 1件でもオフラインのグループコントローラが存在するなら : true それ以外 : false
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException アイルに紐つくステーションが存在しない場合にスローされます。
     */
    protected boolean hasOffLineAGC(Aisle aisle)
            throws InvalidDefineException,
                ReadWriteException
    {
        if (isOffLine(aisle.getValue(GroupController.STATUS_FLAG)))
        {
            return true;
        }

        Station[] stations = getStations(aisle);
        for (Station station : stations)
        {
            if (isOffLine(station.getValue(GroupController.STATUS_FLAG)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定されたステータスがオンラインかどうかを判定します。
     *
     * @param status 判定するステータス
     * @return オンラインの場合ture、それ以外false
     */
    protected boolean isOnLine(Object status)
    {
        if (GroupController.GC_STATUS_ONLINE.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 指定されたステータスがオフラインかどうかを判定します。
     *
     * @param status 判定するステータス
     * @return オフラインの場合ture、それ以外false
     */
    protected boolean isOffLine(Object status)
    {
        if (GroupController.GC_STATUS_OFFLINE.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 指定されたアイルの状態が異常かどうかを判定します。
     *
     * @param status 判定するアイルの状態
     * @return 異常な場合ture、それ以外false
     */
    protected boolean isAisleStatusError(Object status)
    {
        if (Aisle.AISLE_STATUS_ERROR.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 指定されたステーションの状態が異常かどうかを判定します。
     *
     * @param status 判定するテーションの状態
     * @return 異常な場合ture、それ以外false
     */
    protected boolean isStationStatusError(Object status)
    {
        if (Station.STATION_STATUS_ERROR.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 指定されたアイルの状態が正常かどうかを判定します。
     *
     * @param status 判定するアイルの状態
     * @return 正常な場合ture、それ以外false
     */
    protected boolean isAisleStatusNormal(Object status)
    {
        if (Aisle.AISLE_STATUS_NORMAL.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 指定されたステーションの状態が正常かどうかを判定します。
     *
     * @param status 判定するテーションの状態
     * @return 正常な場合ture、それ以外false
     */
    protected boolean isStatusStatusNormal(Object status)
    {
        if (Station.STATION_STATUS_NORMAL.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * アイルに紐つくステーションを取得します。<BR>
     *
     * @param aisle アイル
     * @return ステーション
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException アイルに紐つくステーションが存在しない場合にスローされます。
     */
    protected Station[] getStations(Aisle aisle)
            throws ReadWriteException,
                InvalidDefineException
    {
        StationSearchKey stKey = new StationSearchKey();

        // set where
        stKey.setWhStationNo(aisle.getWhStationNo());
        stKey.setSendable(Station.SENDABLE_TRUE);

        stKey.setAisleStationNo(new String[] {
                aisle.getStationNo(),
                null
        }, true);

        // set join
        stKey.setJoin(Station.CONTROLLER_NO, GroupController.CONTROLLER_NO);

        // set collect
        stKey.setCollect(new FieldName(Station.STORE_NAME, FieldName.ALL_FIELDS));
        stKey.setCollect(GroupController.CONTROLLER_NO);
        stKey.setCollect(GroupController.STATUS_FLAG);

        Station[] stations = (Station[])new StationHandler(getConnection()).find(stKey);

        if (ArrayUtil.isEmpty(stations))
        {
            throw new InvalidDefineException("no station info in aisle : " + aisle.getStationNo());
        }

        return stations;
    }

    /**
     * エリアに紐つくアイルを取得します。<BR>
     *
     * @param area エリア
     * @return アイル
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException エリアに紐つくアイルが存在しない場合にスローされます。
     */
    protected Aisle[] getAisles(Area area)
            throws ReadWriteException,
                InvalidDefineException
    {
        AisleSearchKey aisleKey = new AisleSearchKey();
        AreaSearchKey areaKey = new AreaSearchKey();

        // set where
        areaKey.setAreaNo(area.getAreaNo());
        aisleKey.setKey(areaKey);

        // set join
        aisleKey.setJoin(Aisle.WH_STATION_NO, Area.WHSTATION_NO);
        aisleKey.setJoin(Aisle.CONTROLLER_NO, GroupController.CONTROLLER_NO);

        // set collect
        aisleKey.setCollect(new FieldName(Aisle.STORE_NAME, FieldName.ALL_FIELDS));
        aisleKey.setCollect(GroupController.CONTROLLER_NO);
        aisleKey.setCollect(GroupController.STATUS_FLAG);

        Aisle[] aisles = (Aisle[])new AisleHandler(getConnection()).find(aisleKey);

        if (ArrayUtil.isEmpty(aisles))
        {
            throw new InvalidDefineException("no aisle info in area : " + area.getAreaNo());
        }

        return aisles;
    }

    /**
     * 指定されたエリアが全てASRSエリアかどうかを判定します。<BR>
     *
     * @param areas エリアエンティティ
     * @return 全てASRSエリア : true それ以外 : false
     */
    protected boolean isAllAsrsArea(Area[] areas)
    {
        for (Area area : areas)
        {
            if (!Area.AREA_TYPE_ASRS.equals(area.getAreaType()))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 指定されたエリア内にASRSのエリアが存在するかどうかを判定します。<BR>
     *
     * @param areas チェックするエリア配列
     * @return ASRSのエリアが存在する場合 : true 存在しない場合 : false
     */
    protected boolean hasAsrsArea(Area[] areas)
    {
        for (Area area : areas)
        {
            if (Area.AREA_TYPE_ASRS.equals(area.getAreaType()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定された引当パターンに紐つくエリアを取得します。<BR>
     *
     * @param allocateNo 引当パターン
     * @return 引当パターンに紐つくエリア
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected Area[] getAllocatePatternArea(String allocateNo)
            throws ReadWriteException
    {
        AreaHandler areah = new AreaHandler(getConnection());
        AreaSearchKey areaKey = new AreaSearchKey();
        AllocatePrioritySearchKey allocKey = new AllocatePrioritySearchKey();

        allocKey.setAllocateNo(allocateNo);
        allocKey.setReplenishmentAreaType(AllocatePriority.REPLENISHMENT_AREA_TYPE_NORMAL_AREA);
        areaKey.setKey(allocKey);

        areaKey.setJoin(Area.AREA_NO, AllocatePriority.ALLOCATE_AREA);

        return (Area[])areah.find(areaKey);
    }

    /**
     * 表示情報問合せ時の検索キーのセットを行います。
     *
     * @param inParam 画面入力データ
     * @return 出庫予定情報検索キー
     */
    protected SearchKey createSearchKey(RetrievalInParameter inParam)
    {
        RetrievalPlanSearchKey key = new RetrievalPlanSearchKey();
        // set collect
        key.setConsignorCodeCollect();
        key.setPlanDayCollect();
        key.setBatchNoCollect();
        key.setOrderNoCollect();
        key.setCustomerCodeCollect();
        key.setCollect(Customer.CUSTOMER_NAME);
        key.setPlanUkeyCollect("COUNT");

        // set join
        key.setJoin(RetrievalPlan.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        key.setJoin(RetrievalPlan.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");

        // set where
        key.setConsignorCode(inParam.getConsignorCode());
        key.setPlanDay(inParam.getRetrievalPlanDay());
        if (!StringUtil.isBlank(inParam.getBatchNo()))
        {
            key.setBatchNo(inParam.getBatchNo());
        }
        // オーダーNo.(From)
        if (!StringUtil.isBlank(inParam.getOrderNo()))
        {

            key.setOrderNo(inParam.getOrderNo(), ">=");

        }
        // オーダーNo.(To)
        if (!StringUtil.isBlank(inParam.getToOrderNo()))
        {

            key.setOrderNo(inParam.getToOrderNo(), "<=");

        }

        // 削除以外
        key.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        // 未スケジュール
        key.setSchFlag(RetrievalPlan.SCH_FLAG_NOT_SCHEDULE);

        // set group
        key.setConsignorCodeGroup();
        key.setPlanDayGroup();
        key.setBatchNoGroup();
        key.setOrderNoGroup();
        key.setCustomerCodeGroup();
        key.setGroup(Customer.CUSTOMER_NAME);

        // set order by
        key.setBatchNoOrder(true);
        key.setOrderNoOrder(true);
        key.setCustomerCodeOrder(true);

        return key;
    }

    /**
     * 表示情報問合せ時の画面表示用の返却データをセットします。
     *
     * @param finder 検索結果を含んだFinderクラス。
     * @return Businessへ返すパラメータ
     * @throws ReadWriteException データベースエラーが発生した場合にスローされます。
     */
    protected Parameter[] convertEntityToParam(AbstractDBFinder finder)
            throws ReadWriteException
    {
        RetrievalPlan[] plans = (RetrievalPlan[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);

        RetrievalOutParameter[] outParams = new RetrievalOutParameter[plans.length];

        for (int i = 0; i < outParams.length; i++)
        {
            outParams[i] = new RetrievalOutParameter();

            // バッチNo.
            outParams[i].setBatchNo(plans[i].getBatchNo());
            // オーダーNo.
            outParams[i].setOrderNo(plans[i].getOrderNo());
            // 出荷先コード
            outParams[i].setCustomerCode(plans[i].getCustomerCode());
            // 出荷先名称
            outParams[i].setCustomerName((String)plans[i].getValue(Customer.CUSTOMER_NAME));
            // 明細数
            outParams[i].setDetailCnt(Formatter.getLong(plans[i].getPlanUkey()));
            // 引当結果(初期表示は未引当)
            outParams[i].setAllocateResult(RetrievalInParameter.ALLOCATE_RESULT_UNSTART);
        }

        return outParams;
    }

    /**
     * 引当処理を行います。<br>
     * リストセルエリアの行単位にトランザクションの確定を行います。<br>
     * 該当行に１件でも欠品があった場合は、その行に対しては何も処理を行いません。<br>
     * ただし欠品発生時に欠品完了するように指定があった場合は、引当可能な分だけ引当を行い、<br>
     * 欠品が発生した行に対して欠品完了処理を行います。<br>
     * 欠品が発生した行があった場合、その行を<code>RetrievalInParameter</code>配列にセットして返します。<br>
     *
     * @param inParams リストセルエリアの行単位でのデータ
     * @param pattern 引当パターン
     * @param startUnitKey 出庫開始単位キー
     * @param retrievalStartDate 出庫開始日時
     * @param isShortageFinish 欠品完了フラグ
     * @return 欠品が発生した行の画面データ
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected RetrievalInParameter[] allocate(RetrievalInParameter[] inParams, String pattern, String startUnitKey,
            Date retrievalStartDate, boolean isShortageFinish)
            throws CommonException
    {
        RetrievalPlanFinder finder = null;

        try
        {
            finder = new RetrievalPlanFinder(getConnection());

            // 欠品が発生した行を保持するためのリスト
            List<RetrievalInParameter> shortageList = new ArrayList<RetrievalInParameter>();

            // 引当オペレータ初期化
            RetrievalAllocateOperator allocOpe =
                    new RetrievalAllocateOperator(getConnection(), pattern, this.getClass());

            // 欠品オペレータ初期化
            ShortageOperator shortageOpe =
                    new ShortageOperator(getConnection(), pattern, startUnitKey, retrievalStartDate, this.getClass());

            // 作業日を取得する
            WarenaviSystemController system = new WarenaviSystemController(getConnection(), getClass());
            String workDay = system.getWorkDay();

            WmsUserInfo userInfo = inParams[0].getWmsUserInfo();
            DfkUserInfo dfkUserInfo = getUserInfo();

            // リストセルエリアの行数分処理を行う
            for (int i = 0; i < inParams.length; i++)
            {
                try
                {
                    // 予定情報をロックする
                    if (lock(finder, inParams[i]) == 0)
                    {
                        // No.{0} 他端末で処理されたため、処理を中断しました。
                        setMessageNoOverride(WmsMessageFormatter.format(6023015, inParams[i].getRowNo()));
                        _rowStatusMap.put(inParams[i].getRowNo(), RetrievalInParameter.ALLOCATE_RESULT_ERROR); // エラー

                        continue;
                    }
                }
                catch (LockTimeOutException e)
                {
                    // No.{0} 他端末で処理中のため、処理を中断しました。
                    setMessageNoOverride(WmsMessageFormatter.format(6023014, inParams[i].getRowNo()));
                    _rowStatusMap.put(inParams[i].getRowNo(), RetrievalInParameter.ALLOCATE_RESULT_ERROR); // エラー

                    continue;
                }

                // 補充作業の有無
                boolean hasReplenishemnt = false;
                // 欠品の有無
                boolean hasShortage = false;

                // 予定データ１件づつ引当処理を行う。
                // 欠品完了以外で１件でも欠品があった場合、この行は処理を中止し、次の行へ処理を移す
                while (finder.hasNext())
                {
                    RetrievalPlan[] plans = (RetrievalPlan[])finder.getEntities(100);

                    for (int j = 0; j < plans.length; j++)
                    {
                        // 引当出来なかった場合
                        if (allocOpe.allocate(plans[j]) > 0)
                        {
                            // 欠品あり
                            hasShortage = true;

                            // 欠品完了する場合は欠品が発生しても処理続行
                            if (isShortageFinish)
                            {
                                //
                                shortageOpe.createShortageComplete(allocOpe.getShortageQty(), allocOpe.getPlanQty(),
                                        allocOpe.getAllocatedQty(), plans[j]);
                                decideShortage(plans[j], workDay, userInfo);
                            }
                            else
                            {
                                // 欠品完了しない場合はその行は処理終了
                                hasReplenishemnt = true;
                                break;
                            }
                        }
                    }
                    if (hasReplenishemnt)
                    {
                        break;
                    }
                }

                // 行ごとにトランザクションを確定する
                if (hasReplenishemnt)
                {
                    // 補充作業がある場合(欠品あり)、欠品があった行を記憶し、ロールバックする
                    shortageList.add(inParams[i]);
                    doRollBack(getClass());
                    // 欠品
                    _rowStatusMap.put(inParams[i].getRowNo(), RetrievalInParameter.ALLOCATE_RESULT_SHORTAGE);
                }
                else
                {
                    // 搬送情報決定
                    allocOpe.decideCarryInfo();
                    doCommit(getClass());
                    if (hasShortage)
                    {
                        _rowStatusMap.put(inParams[i].getRowNo(), RetrievalInParameter.ALLOCATE_RESULT_SHORTAGECOMP); // 欠品完了
                    }
                    else
                    {
                        _rowStatusMap.put(inParams[i].getRowNo(), RetrievalInParameter.ALLOCATE_RESULT_COMPLETION); // 引当済み
                    }

                    try
                    {
                        // 出庫搬送情報とパレット情報の状態の不整合を補正します。指示応答で在庫情報を作成する場合に発生する不正の補正です。
                        // トランザクションの切れ目を明確にするためスリープします。
                        Thread.sleep(100);
                        allocOpe.updatePalletState();
                        doCommit(getClass());
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                // スケジュールNo.のリセット
                allocOpe.resetScheduleNo();
            }

            // 欠品完了指定があり実際に欠品が発生していた場合は欠品リストを発行する
            if (hasShortage(startUnitKey) && isShortageFinish)
            {
                printShortageList(startUnitKey, userInfo.getTerminalNo(), dfkUserInfo, false);
            }

            if (shortageList.size() > 0)
            {
                return shortageList.toArray(new RetrievalInParameter[shortageList.size()]);
            }
            else
            {
                return null;
            }
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * 指定された出庫開始単位キーの欠品情報が存在するかどうかを判定します。<BR>
     *
     * @param startUnitKey 出庫開始単位キー
     * @return 欠品情報が存在する場合はture
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected boolean hasShortage(String startUnitKey)
            throws ReadWriteException
    {
        ShortageInfoHandler handler = new ShortageInfoHandler(getConnection());
        ShortageInfoSearchKey key = new ShortageInfoSearchKey();

        key.setStartUnitKey(startUnitKey);

        return handler.count(key) != 0;
    }

    /**
     * 欠品完了処理を行います
     * @param plan 欠品があった出庫予定情報
     * @param workDay 作業日
     * @param userInfo WMSユーザ情報
     * @throws ReadWriteException データベースエラーが発生した場合にスローされます。
     * @throws DataExistsException 登録データが既に存在した場合にスローされます。
     * @throws ScheduleException スケジュール処理でエラーが発生した場合にスローされます。
     * @throws NotFoundException 更新対象データが存在しない場合にスローされます。
     * @throws NoPrimaryException 該当するデータが複数存在するときスローされます。
     */
    protected void decideShortage(RetrievalPlan plan, String workDay, WmsUserInfo userInfo)
            throws ReadWriteException,
                DataExistsException,
                ScheduleException,
                NotFoundException,
                NoPrimaryException
    {
        HostSendController hsc = new HostSendController(getConnection(), this.getClass());
        WorkInfoController wic = new WorkInfoController(getConnection(), this.getClass());
        WMSSequenceHandler sequence = new WMSSequenceHandler(getConnection());
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());

        int assigningQty = getAssignQty(plan);

        String jobNo = sequence.nextWorkInfoJobNo();

        // 作業情報を欠品完了する
        WorkInfo tempwi = new WorkInfo();
        tempwi.setJobNo(jobNo);
        tempwi.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
        tempwi.setStatusFlag(WorkInfo.STATUS_FLAG_COMPLETION);
        tempwi.setHardwareType(WorkInfo.HARDWARE_TYPE_UNSTART);
        tempwi.setPlanUkey(plan.getPlanUkey());
        tempwi.setPlanDay(plan.getPlanDay());
        tempwi.setConsignorCode(plan.getConsignorCode());
        tempwi.setCustomerCode(plan.getCustomerCode());
        tempwi.setShipTicketNo(plan.getShipTicketNo());
        tempwi.setShipLineNo(plan.getShipLineNo());
        tempwi.setShipBranchNo(plan.getBranchNo());
        tempwi.setBatchNo(plan.getBatchNo());
        tempwi.setOrderNo(plan.getOrderNo());
        tempwi.setPlanAreaNo(plan.getPlanAreaNo());
        tempwi.setPlanLocationNo(plan.getPlanLocationNo());
        tempwi.setItemCode(plan.getItemCode());
        tempwi.setPlanLotNo(plan.getPlanLotNo());
        tempwi.setPlanQty(assigningQty);
        tempwi.setResultQty(0);
        tempwi.setShortageQty(assigningQty);
        tempwi.setWorkDay(workDay);
        tempwi.setUserId(userInfo.getUserId());
        tempwi.setTerminalNo(userInfo.getTerminalNo());
        tempwi.setRegistPname(this.getClass().getSimpleName());
        tempwi.setLastUpdatePname(this.getClass().getSimpleName());
        wih.create(tempwi);

        // 実績送信情報を作成する
        hsc.insertByWorkInfo(jobNo, userInfo);

        // 出庫予定情報を更新する
        RetrievalPlanAlterKey rpAKey = new RetrievalPlanAlterKey();
        RetrievalPlanHandler rph = new RetrievalPlanHandler(getConnection());

        String status = "";
        try
        {
            status = wic.getPlanStatus(plan.getPlanUkey());
        }
        catch (NotFoundException e)
        {
            // 作業情報が存在しない場合は「未作業」
            status = RetrievalPlan.STATUS_FLAG_UNSTART;
        }

        rpAKey.setPlanUkey(plan.getPlanUkey());
        rpAKey.updateStatusFlag(status);
        rpAKey.updateSchFlag(RetrievalPlan.SCH_FLAG_SCHEDULE);
        rpAKey.updateShortageQtyWithColumn(RetrievalPlan.SHORTAGE_QTY, BigDecimal.valueOf(assigningQty));
        rpAKey.updateLastUpdatePname(this.getClass().getSimpleName());
        rph.modify(rpAKey);
    }

    /**
     * 欠品情報の作成と補充作業情報の作成を行います。
     *
     * @param shortages 欠品があった出庫入力パラメータ
     * @param patternNo 引当パターンNo.
     * @param startUnitKey 出庫開単位キー
     * @param retrievalStartDate 出庫開始日時
     * @throws CommonException 処理エラーがあった場合にスローされます。
     */
    protected void replenish(RetrievalInParameter[] shortages, String patternNo, String startUnitKey,
            Date retrievalStartDate)
            throws CommonException
    {
        // 補充オペレータ初期化
        EmergencyReplenishCaseOperator operator =
                new EmergencyReplenishCaseOperator(getConnection(), patternNo, this.getClass());

        String terminalNo = shortages[0].getWmsUserInfo().getTerminalNo();

        DfkUserInfo dfkUserInfo = getUserInfo();

        RetrievalPlanFinder finder = null;
        ShortageInfoFinder siFinder = null;

        boolean hasShortage = false;

        try
        {
            finder = new RetrievalPlanFinder(getConnection());

            // 欠品オペレータ初期化
            ShortageOperator shortageOperator =
                    new ShortageOperator(getConnection(), shortages, patternNo, startUnitKey, retrievalStartDate,
                            this.getClass());

            // 引当パターンが補充可能かどうか
            boolean hasReplenishmentArea = hasReplenishmentArea(patternNo);

            // 補充処理を行う
            for (int i = 0; i < shortages.length; i++)
            {
                try
                {
                    // 予定情報のロック
                    if (lock(finder, shortages[i]) == 0)
                    {
                        // No.{0} 他端末で処理されたため、処理を中断しました。
                        setMessageNoOverride(WmsMessageFormatter.format(6023015, shortages[i].getRowNo()));
                        // 「エラー」を設定
                        _rowStatusMap.put(shortages[i].getRowNo(), RetrievalInParameter.ALLOCATE_RESULT_ERROR);

                        continue;
                    }

                    // 欠品情報作成
                    shortageOperator.create(finder, hasReplenishmentArea);
                }
                catch (LockTimeOutException e)
                {
                    // No.{0} 他端末で処理中のため、処理を中断しました。
                    setMessageNoOverride(WmsMessageFormatter.format(6023014, shortages[i].getRowNo()));
                    // 「エラー」を設定
                    _rowStatusMap.put(shortages[i].getRowNo(), RetrievalInParameter.ALLOCATE_RESULT_ERROR);

                    continue;
                }
            }

            // 引当パターンに補充エリアが設定されていない場合は補充引当は行わない
            if (!hasReplenishmentArea)
            {
                printShortageList(startUnitKey, terminalNo, dfkUserInfo, false);
                return;
            }

            // 先ほど作成した欠品情報をアイテム別で取得
            siFinder = getShortageInfoFinder(startUnitKey);
            while (siFinder.hasNext())
            {
                ShortageInfo[] sinfos = (ShortageInfo[])siFinder.getEntities(100);
                for (ShortageInfo sInfo : sinfos)
                {
                    operator.allocate(sInfo);

                    if (operator.hasShortage())
                    {
                        hasShortage = true;
                    }
                }
            }

            // 引当結果を求める
            setRowStatusInfo(shortages, startUnitKey);

            // 搬送情報決定
            operator.decideCarryInfo();

            // トランザクションの確定を行う
            doCommit(getClass());
        }
        finally
        {
            closeFinder(finder);
            closeFinder(siFinder);
        }

        if (!ArrayUtil.isEmpty(operator.getSettingUnitKeys()))
        {
            // 補充リスト発行(補充データがある場合のみ)
            printReplenishmentList(operator.getSettingUnitKeys(), terminalNo, dfkUserInfo);
        }
        if (!ArrayUtil.isEmpty(operator.getAsrsSettingUnitKeys()))
        {
            // ASRS補充リスト発行(ASRS補充データがある場合のみ)
            printAsrsReplenishmentList(operator.getAsrsSettingUnitKeys(), terminalNo, dfkUserInfo);
        }
        // 補充引当欠品が発生した場合
        if (hasShortage)
        {
            // 補充処理の欠品リスト発行
            printShortageList(startUnitKey, terminalNo, dfkUserInfo, true);
        }
    }

    /**
     * 欠品チェックリストの発行を行います。<BR>
     *
     * @param startUnitKey 出庫開始単位キー
     * @param terminalNo 端末No.
     * @param userInfo ユーザ情報
     * @param replenishmentShortageFlag 補充欠品了フラグ(補充で欠品が発生した場合にtrue)
     * @return 正常終了の場合true、それ以外false
     */
    protected boolean printShortageList(String startUnitKey, String terminalNo, DfkUserInfo userInfo,
            boolean replenishmentShortageFlag)
    {
        RetrievalShortageInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            dasch = new RetrievalShortageInquiryDASCH(this.getConnection(), this.getClass(), getLocale(), userInfo);
            dasch.setForwardOnly(true);

            // set input parameters.
            RetrievalShortageInquiryDASCHParams inparam = new RetrievalShortageInquiryDASCHParams();
            inparam.set(RetrievalShortageInquiryDASCHParams.START_UNIT_KEY, startUnitKey);

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(getLocale(), userInfo);
            exporter = factory.newPrinterExporter("ShortageCheckList", false);
            exporter.open();

            dasch.count(inparam);

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ShortageCheckListParams expparam = new ShortageCheckListParams();
                expparam.set(ShortageCheckListParams.DFK_DS_NO,
                        outparam.get(RetrievalShortageInquiryDASCHParams.DFK_DS_NO));
                expparam.set(ShortageCheckListParams.DFK_USER_ID,
                        outparam.get(RetrievalShortageInquiryDASCHParams.DFK_USER_ID));
                expparam.set(ShortageCheckListParams.DFK_USER_NAME,
                        outparam.get(RetrievalShortageInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(ShortageCheckListParams.PLAN_DAY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_DAY));
                expparam.set(ShortageCheckListParams.SYS_DAY, outparam.get(RetrievalShortageInquiryDASCHParams.SYS_DAY));
                expparam.set(ShortageCheckListParams.SYS_TIME,
                        outparam.get(RetrievalShortageInquiryDASCHParams.SYS_TIME));
                expparam.set(ShortageCheckListParams.START_DAY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.START_DAY));
                expparam.set(ShortageCheckListParams.START_TIME,
                        outparam.get(RetrievalShortageInquiryDASCHParams.START_TIM));
                expparam.set(ShortageCheckListParams.BATCH_NO, outparam.get(RetrievalShortageInquiryDASCHParams.BATCH));
                expparam.set(ShortageCheckListParams.ALLOCATE_NO,
                        outparam.get(RetrievalShortageInquiryDASCHParams.ALLOCATED_PATTERN_NO));
                expparam.set(ShortageCheckListParams.ORDER_NO, outparam.get(RetrievalShortageInquiryDASCHParams.ORDER));
                expparam.set(ShortageCheckListParams.CUSTOMER_CODE,
                        outparam.get(RetrievalShortageInquiryDASCHParams.CUSTOMER_CODE));
                expparam.set(ShortageCheckListParams.CUSTOMER_NAME,
                        outparam.get(RetrievalShortageInquiryDASCHParams.CUSTOMER_NAME));
                expparam.set(ShortageCheckListParams.ITEM_CODE,
                        outparam.get(RetrievalShortageInquiryDASCHParams.ITEM_CODE));
                expparam.set(ShortageCheckListParams.ITEM_NAME,
                        outparam.get(RetrievalShortageInquiryDASCHParams.ITEM_NAME));
                expparam.set(ShortageCheckListParams.PLAN_LOT_NO,
                        outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_LOT_NO));
                expparam.set(ShortageCheckListParams.ENTERING_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.CASE_PACK));
                expparam.set(ShortageCheckListParams.PLAN_CASE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_CASE_QTY));
                expparam.set(ShortageCheckListParams.PLAN_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.PLAN_PIECE_QTY));
                expparam.set(ShortageCheckListParams.REP_CASE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.REP_CASE_QTY));
                expparam.set(ShortageCheckListParams.REP_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.REP_PIECE_QTY));
                expparam.set(ShortageCheckListParams.SHORTAGE_CASE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.SHORTAGE_CASE_QTY));
                expparam.set(ShortageCheckListParams.SHORTAGE_PIECE_QTY,
                        outparam.get(RetrievalShortageInquiryDASCHParams.SHORTAGE_PIECE_QTY));
                expparam.set(ShortageCheckListParams.ALLOCATION,
                        outparam.get(RetrievalShortageInquiryDASCHParams.ALLOCATED));
                if (!exporter.write(expparam))
                {
                    setMessage("6007042");
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
                ex.printStackTrace();
                setMessage("6007042");
                return false;
            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            return false;
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
        }
    }

    /**
     * 補充リストの発行を行います。<BR>
     *
     * @param settingUnitKeys 設定単位キー
     * @param terminalNo 端末No.
     * @param userInfo ユーザ情報
     * @return 正常終了の場合true、それ以外false
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected boolean printReplenishmentList(String[] settingUnitKeys, String terminalNo, DfkUserInfo userInfo)
            throws ReadWriteException
    {
        ReplenishWorkDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            dasch = new ReplenishWorkDASCH(this.getConnection(), this.getClass(), getLocale(), userInfo);
            dasch.setForwardOnly(true);

            // set input parameters.
            ReplenishWorkDASCHParams inparam = new ReplenishWorkDASCHParams();
            inparam.set(ReplenishWorkDASCHParams.SETTING_UKEYS, settingUnitKeys);

            dasch.count(inparam);

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            if (exporter == null)
            {
                ExporterFactory factory = new WmsExporterFactory(getLocale(), userInfo);
                exporter = factory.newPrinterExporter("ReplenishmentList", false);
                exporter.open();
            }
            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ReplenishmentListParams expparam = new ReplenishmentListParams();
                expparam.set(ReplenishmentListParams.DFK_DS_NO, outparam.get(ReplenishWorkDASCHParams.DFK_DS_NO));
                expparam.set(ReplenishmentListParams.DFK_USER_ID, outparam.get(ReplenishWorkDASCHParams.DFK_USER_ID));
                expparam.set(ReplenishmentListParams.DFK_USER_NAME,
                        outparam.get(ReplenishWorkDASCHParams.DFK_USER_NAME));
                expparam.set(ReplenishmentListParams.SYS_DAY, outparam.get(ReplenishWorkDASCHParams.SYS_DAY));
                expparam.set(ReplenishmentListParams.SYS_TIME, outparam.get(ReplenishWorkDASCHParams.SYS_TIME));
                expparam.set(ReplenishmentListParams.JOB_NO, outparam.get(ReplenishWorkDASCHParams.JOB_NO));
                expparam.set(ReplenishmentListParams.REP_RETRIEVAL_AREA_NO,
                        outparam.get(ReplenishWorkDASCHParams.REP_RETRIEVAL_AREA_NO));
                expparam.set(ReplenishmentListParams.REP_RETRIEVAL_AREA_NAME,
                        outparam.get(ReplenishWorkDASCHParams.REP_RETRIEVAL_AREA_NAME));
                expparam.set(ReplenishmentListParams.JOB_TYPE, outparam.get(ReplenishWorkDASCHParams.JOB_TYPE));
                expparam.set(ReplenishmentListParams.REP_RETRIEVAL_LOCATION_NO,
                        outparam.get(ReplenishWorkDASCHParams.REP_RETRIEVAL_LOCATION_NO));
                expparam.set(ReplenishmentListParams.ITEM_CODE, outparam.get(ReplenishWorkDASCHParams.ITEM_CODE));
                expparam.set(ReplenishmentListParams.ITEM_NAME, outparam.get(ReplenishWorkDASCHParams.ITEM_NAME));
                expparam.set(ReplenishmentListParams.LOT_NO, outparam.get(ReplenishWorkDASCHParams.LOT_NO));
                expparam.set(ReplenishmentListParams.ENTERING_QTY, outparam.get(ReplenishWorkDASCHParams.ENTERING_QTY));
                expparam.set(ReplenishmentListParams.PLAN_CASE_QTY,
                        outparam.get(ReplenishWorkDASCHParams.PLAN_CASE_QTY));
                expparam.set(ReplenishmentListParams.PLAN_PIECE_QTY,
                        outparam.get(ReplenishWorkDASCHParams.PLAN_PIECE_QTY));
                expparam.set(ReplenishmentListParams.REP_STORAGE_AREA_NO,
                        outparam.get(ReplenishWorkDASCHParams.REP_STORAGE_AREA_NO));
                expparam.set(ReplenishmentListParams.REP_LOCATION_NO,
                        outparam.get(ReplenishWorkDASCHParams.REP_LOCATION_NO));
                if (!exporter.write(expparam))
                {
                    setMessage("6007042");
                    return false;
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
                ex.printStackTrace();
                setMessage("6007042");
                return false;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //            setMessage(ExceptionHandler.getDisplayMessage(ex, this));
            if (dasch != null && !StringUtil.isBlank(dasch.getMessage()))
            {
                setMessage(dasch.getMessage());
            }
            return false;
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
        }
    }

    /**
     * ASRS補充リストの発行を行います。<BR>
     *
     * @param settingUnitKeys 設定単位キー
     * @param terminalNo 端末No.
     * @param userInfo ユーザ情報
     * @return 正常終了の場合true、それ以外false
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected boolean printAsrsReplenishmentList(String[] settingUnitKeys, String terminalNo, DfkUserInfo userInfo)
            throws ReadWriteException
    {
        AsReplenishWorkDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            // open connection.
            dasch = new AsReplenishWorkDASCH(this.getConnection(), this.getClass(), getLocale(), userInfo);
            dasch.setForwardOnly(true);

            // set input parameters.
            AsReplenishWorkDASCHParams inparam = new AsReplenishWorkDASCHParams();
            inparam.set(AsReplenishWorkDASCHParams.AS_SETTING_UKEYS, settingUnitKeys);

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(getLocale(), userInfo);
            exporter = factory.newPrinterExporter("AsrsReplenishmentList", false);
            exporter.open();

            dasch.count(inparam);

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                AsrsReplenishmentListParams expparam = new AsrsReplenishmentListParams();
                expparam.set(AsrsReplenishmentListParams.DFK_DS_NO, outparam.get(AsReplenishWorkDASCHParams.DFK_DS_NO));
                expparam.set(AsrsReplenishmentListParams.DFK_USER_ID,
                        outparam.get(AsReplenishWorkDASCHParams.DFK_USER_ID));
                expparam.set(AsrsReplenishmentListParams.DFK_USER_NAME,
                        outparam.get(AsReplenishWorkDASCHParams.DFK_USER_NAME));
                expparam.set(AsrsReplenishmentListParams.SYS_DAY, outparam.get(AsReplenishWorkDASCHParams.SYS_DAY));
                expparam.set(AsrsReplenishmentListParams.SYS_TIME, outparam.get(AsReplenishWorkDASCHParams.SYS_TIME));
                expparam.set(AsrsReplenishmentListParams.SERCH_JOB_NO, outparam.get(AsReplenishWorkDASCHParams.LIST_NO));
                expparam.set(AsrsReplenishmentListParams.STATION_NO,
                        outparam.get(AsReplenishWorkDASCHParams.STATION_NO));
                expparam.set(AsrsReplenishmentListParams.REP_RETRIEVAL_AREA_NO,
                        outparam.get(AsReplenishWorkDASCHParams.RETRIEVAL_AREA_NO));
                expparam.set(AsrsReplenishmentListParams.REP_RETRIEVAL_AREA_NAME,
                        outparam.get(AsReplenishWorkDASCHParams.RETRIEVAL_AREA_NAME));
                expparam.set(AsrsReplenishmentListParams.JOB_TYPE, outparam.get(AsReplenishWorkDASCHParams.JOB_TYPE));
                expparam.set(AsrsReplenishmentListParams.JOB_NO, outparam.get(AsReplenishWorkDASCHParams.JOB_NO));
                expparam.set(AsrsReplenishmentListParams.REP_RETRIEVAL_LOCATION_NO,
                        outparam.get(AsReplenishWorkDASCHParams.RETRIEVAL_LOCATION_NO));
                expparam.set(AsrsReplenishmentListParams.ITEM_CODE, outparam.get(AsReplenishWorkDASCHParams.ITEM_CODE));
                expparam.set(AsrsReplenishmentListParams.ITEM_NAME, outparam.get(AsReplenishWorkDASCHParams.ITEM_NAME));
                expparam.set(AsrsReplenishmentListParams.LOT_NO, outparam.get(AsReplenishWorkDASCHParams.LOT_NO));
                expparam.set(AsrsReplenishmentListParams.ENTERING_QTY,
                        outparam.get(AsReplenishWorkDASCHParams.ENTERING_QTY));
                expparam.set(AsrsReplenishmentListParams.PLAN_CASE_QTY,
                        outparam.get(AsReplenishWorkDASCHParams.PLAN_CASE_QTY));
                expparam.set(AsrsReplenishmentListParams.PLAN_PIECE_QTY,
                        outparam.get(AsReplenishWorkDASCHParams.PLAN_PIECE_QTY));
                expparam.set(AsrsReplenishmentListParams.REP_STORAGE_AREA_NO,
                        outparam.get(AsReplenishWorkDASCHParams.STORAGE_AREA_NO));
                expparam.set(AsrsReplenishmentListParams.REP_LOCATION_NO,
                        outparam.get(AsReplenishWorkDASCHParams.STORAGE_LOCATION_NO));
                if (!exporter.write(expparam))
                {
                    setMessage("6007042");
                    return false;
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
                ex.printStackTrace();
                setMessage("6007042");
                return false;
            }
        }
        catch (Exception ex)
        {
            if (dasch != null && !StringUtil.isBlank(dasch.getMessage()))
            {
                setMessage(dasch.getMessage());
            }
            return false;
        }
        finally
        {
            if (dasch != null)
            {
                dasch.close();
            }
            if (exporter != null)
            {
                exporter.close();
            }
        }
    }

    /**
     * 欠品があった行の引当結果を設定します。<BR>
     * 指定された出庫入力パラメータと出庫開始単位キーで欠品情報を検索し、欠品数と補充数を元に引当結果を判定します。<BR>
     * 欠品数 &gt; 補充数 の欠品情報が存在する場合はその行は補充引当欠品が発生したとみなし引当結果に欠品を設定します。<BR>
     * それ以外の場合は引当結果に補充を設定します。<BR>
     *
     * @param shortages 欠品があった出庫入力パラメータ
     * @param startUnitKey 出庫開始単位キー
     * @throws ReadWriteException DBエラーがあった場合にスローされます。
     */
    protected void setRowStatusInfo(RetrievalInParameter[] shortages, String startUnitKey)
            throws ReadWriteException
    {
        ShortageInfoHandler sih = new ShortageInfoHandler(getConnection());
        ShortageInfoSearchKey siKey = new ShortageInfoSearchKey();

        for (RetrievalInParameter inParam : shortages)
        {
            siKey.clear();
            // 出庫開始単位キー
            siKey.setStartUnitKey(startUnitKey);
            // オーダーNo
            siKey.setOrderNo(inParam.getOrderNo());
            // バッチNo
            siKey.setBatchNo(inParam.getBatchNo());
            // 出荷先コード
            siKey.setCustomerCode(inParam.getCustomerCode());

            ShortageInfo[] siInfos = (ShortageInfo[])sih.find(siKey);
            boolean hasShortage = false;
            for (ShortageInfo siInfo : siInfos)
            {
                // 予定数 > (在庫数 + 補充数)の場合
                if (siInfo.getPlanQty() > (siInfo.getStockQty() + siInfo.getReplenishmentQty()))
                {
                    hasShortage = true;
                    break;
                }
            }
            if (hasShortage)
            {
                // 引当結果「欠品」
                _rowStatusMap.put(inParam.getRowNo(), RetrievalInParameter.ALLOCATE_RESULT_SHORTAGE);
            }
            else
            {
                // 引当結果「補充」
                _rowStatusMap.put(inParam.getRowNo(), RetrievalInParameter.ALLOCATE_RESULT_REPLENISHMENT);
            }
        }
    }

    /**
     * 引当パターンの補充可能チェックを行います。<BR>
     * 引当パターンには補充元エリアが含まれている必要があります。<BR>
     * 又、補充元エリアが含まれており、かつ引当パターンの通常エリアに
     * 固定棚管理のエリアが存在する必要があります。<BR>
     *
     * @param patternNo 引当パターンNo.
     * @return true : 補充可 false : 補充不可
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException 引当パターンに補充エリアが含まれているが、
     * 引当パターンの通常エリアに固定棚管理のエリアが存在しない場合にスローされます。
     */
    protected boolean hasReplenishmentArea(String patternNo)
            throws ReadWriteException,
                InvalidDefineException
    {
        boolean check = false;

        AllocatePriorityHandler apHan = new AllocatePriorityHandler(getConnection());
        AllocatePrioritySearchKey apKey = new AllocatePrioritySearchKey();

        // 引当パターン
        apKey.setAllocateNo(patternNo);
        // 補充エリア
        apKey.setReplenishmentAreaType(AllocatePriority.REPLENISHMENT_AREA_TYPE_REPLENISHMENT_AREA);

        // 補充エリアが存在すること
        if (apHan.count(apKey) > 0)
        {
            // チェックOK
            check = true;

            // 通常エリアに固定棚管理のエリアが存在すること
            apKey.clear();
            // 引当パターン
            apKey.setAllocateNo(patternNo);
            // 通常エリア
            apKey.setReplenishmentAreaType(AllocatePriority.REPLENISHMENT_AREA_TYPE_NORMAL_AREA);
            AreaSearchKey areaKey = new AreaSearchKey();
            // 固定棚管理
            areaKey.setLocationType(Area.LOCATION_TYPE_FIXED);
            apKey.setKey(areaKey);
            apKey.setJoin(AllocatePriority.ALLOCATE_AREA, Area.AREA_NO);

            if (apHan.count(apKey) == 0)
            {
                check = false;
            }
        }

        return check;
    }


    /**
     * 出庫開始単位設定キーに紐づく欠品情報を検索し、欠品情報検索クラスを返します。<BR>
     * 欠品情報は荷主、商品毎に集約して検索します。<BR>
     *
     * @param startUnitKey 出庫開始設定単位キー
     * @return 欠品情報該当データがない場合はnullを返します。
     * @throws ReadWriteException データベースエラーが発生した場合にスローされます。
     */
    protected ShortageInfoFinder getShortageInfoFinder(String startUnitKey)
            throws ReadWriteException
    {

        ShortageInfoFinder finder = new ShortageInfoFinder(getConnection());
        finder.open(true);
        ShortageInfoSearchKey siKey = new ShortageInfoSearchKey();

        siKey.clear();
        // set where
        siKey.setStartUnitKey(startUnitKey);
        // set collecct
        siKey.setShortageQtyCollect("SUM");
        siKey.setStockQtyCollect("SUM");
        siKey.setConsignorCodeCollect();
        siKey.setItemCodeCollect();
        siKey.setStartUnitKeyCollect();
        // set group by
        siKey.setConsignorCodeGroup();
        siKey.setItemCodeGroup();
        siKey.setStartUnitKeyGroup();

        finder.search(siKey);

        return finder;
    }

    /**
     * 出庫開始が出来るかどうかの判定を行います。<BR>
     *
     * @return 出庫開始が出来る場合はtrue
     * @throws CommonException 処理エラーがあった場合にスローされます。
     */
    protected boolean canStart()
            throws CommonException
    {
        // 日次更新チェック
        if (!super.canStart())
        {
            return false;
        }

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        // TWNSの引当フラグをチェック。
        // ロックしてから他端末から引当処理中でないかをチェックする
        WarenaviSystemHandler systemh = new WarenaviSystemHandler(getConnection());
        WarenaviSystemSearchKey systemKey = new WarenaviSystemSearchKey();
        systemKey.setSystemNo(WarenaviSystem.SYSTEM_NO_DEFAULT);
        // ロック取得する
        WarenaviSystem system = (WarenaviSystem)systemh.findPrimaryForUpdate(systemKey);
        if (system == null)
        {
            throw new InvalidDefineException(WarenaviSystem.STORE_NAME + " record not found");
        }
        // 処理中の場合
        if (WarenaviSystem.PROCESS_IN_PROGRESS.equals(system.getRetrievalAllocate()))
        {
            // 出庫引当中の為処理できません
            setMessage("6023101");
            return false;
        }
        // 処理中ではない場合処理中に変更する
        else
        {
            updateRetrievalAllocate(WarenaviSystem.PROCESS_IN_PROGRESS);
            return true;
        }
    }

    /**
     * 出庫開始フラグの更新を行ないます。<BR>
     *
     * @param retrievalAllocate 更新する値
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected void updateRetrievalAllocate(String retrievalAllocate)
            throws CommonException
    {
        WarenaviSystemHandler systemh = new WarenaviSystemHandler(getConnection());
        WarenaviSystemAlterKey systemAltKey = new WarenaviSystemAlterKey();

        systemAltKey.setSystemNo(WarenaviSystem.SYSTEM_NO_DEFAULT);
        systemAltKey.updateRetrievalAllocate(retrievalAllocate);

        systemh.modify(systemAltKey);
    }

    /**
     * リストセルエリアの1行に該当する出庫予定情報のロックを行います。<BR>
     * 荷主、商品、ロット、伝票No.、行No.、枝番の順で検索します。<BR>
     *
     * @param finder 出庫予定情報ファインダー
     * @param inParams 出庫入力パラメータ
     * @return ロックした出庫予定情報
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合にスローされます。
     */
    protected int lock(RetrievalPlanFinder finder, RetrievalInParameter inParams)
            throws ReadWriteException,
                LockTimeOutException
    {
        finder.open(true);
        RetrievalPlanSearchKey key = new RetrievalPlanSearchKey();

        // set where
        // 状態フラグ(完了以外)
        key.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
        // 状態フラグ(削除以外)
        key.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        // スケジュール処理フラグ(未スケジュール)
        key.setSchFlag(RetrievalPlan.SCH_FLAG_NOT_SCHEDULE);
        // 荷主コード
        key.setConsignorCode(inParams.getConsignorCode());
        // 出庫予定日
        key.setPlanDay(inParams.getRetrievalPlanDay());
        // バッチNo.
        key.setBatchNo(inParams.getBatchNo());
        // オーダーNo
        key.setOrderNo(inParams.getOrderNo());

        // set order by
        // 荷主
        key.setConsignorCodeOrder(true);
        // 商品
        key.setItemCodeOrder(true);
        // ロット
        key.setPlanLotNoOrder(true);
        // 伝票No
        key.setShipTicketNoOrder(true);
        // 行No
        key.setShipLineNoOrder(true);
        // 枝番
        key.setBranchNoOrder(true);

        return finder.searchForUpdate(key, HandlerSysDefines.WAIT_SEC_NOWAIT);
    }

    /**
     * 指定された出庫予定情報の引当済の作業から、今回欠品分を取得します。<BR>
     *
     * @param plan 出庫予定情報
     * @return 今回欠品数
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected int getAssignQty(RetrievalPlan plan)
            throws ReadWriteException
    {
        return AbstractAllocateOperator.getAllocationQty(getConnection(), plan);
    }

    /**
     * 現在メッセージが設定されていない場合のみメッセージをセットします。<BR>
     *
     * @param msg セットするメッセージ
     */
    protected void setMessageNoOverride(String msg)
    {
        if (StringUtil.isBlank(getMessage()))
        {
            setMessage(msg);
        }
    }


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
