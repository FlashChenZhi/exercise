// $Id: FaRetrievalStartSCH.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.retrieval.schedule.FaRetrievalStartSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.da.ExporterFactory;
import jp.co.daifuku.foundation.print.PrintExporter;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.asrs.operator.AsrsOperator;
import jp.co.daifuku.wms.asrs.schedule.AbstractAsrsSCH;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.asrs.schedule.AsrsOutParameter;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.controller.AsWorkInfoController;
import jp.co.daifuku.wms.base.dbhandler.*;
import jp.co.daifuku.wms.base.entity.*;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;
import jp.co.daifuku.wms.base.report.WmsExporterFactory;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;
import jp.co.daifuku.wms.retrieval.allocate.AbstractAllocateOperator;
import jp.co.daifuku.wms.retrieval.allocate.ReleaseAllocateOperator;
import jp.co.daifuku.wms.retrieval.allocate.RetrievalAllocateOperator;
import jp.co.daifuku.wms.retrieval.allocate.ShortageOperator;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalListDASCH;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalListDASCHParams;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalShortageInquiryDASCH;
import jp.co.daifuku.wms.retrieval.dasch.FaRetrievalShortageInquiryDASCHParams;
import jp.co.daifuku.wms.retrieval.exporter.AsRetrievalWorkListParams;
import jp.co.daifuku.wms.retrieval.exporter.ShortageListParams;

/**
 * BusiTuneで生成されたSCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 8053 $, $Date:: 2013-05-15 10:00:52 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class FaRetrievalStartSCH
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
    /**
     * AS/RSの出庫開始処理を行ったかどうか
     */
    private boolean _startAsrsWork = false;

    /**
     * 作業があったかどうか
     */
    private boolean _hasWork = false;

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
    public FaRetrievalStartSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 初期データ検索を行います。
     *
     * @param p 検索条件を指定したパラメータ
     * @return 初期表示用データ
     * @throws CommonException アクセスエラーなどの例外発生時にスローされます。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        Params outParam = new Params();

        try
        {
            WebSettingHandler webhandler = new WebSettingHandler(getConnection());
            WebSettingSearchKey key = new WebSettingSearchKey();

            // 端末No.
            key.setTerminalNo(getWmsUserInfo().getTerminalNo());
            // 画面ID
            key.setFunctionid(p.getString(FUNCTION_ID));
            // キーデータ
            key.setKeydata(WebSetting.KEY_LIST_CHECK);

            WebSetting[] webset = (WebSetting[])webhandler.find(key);

            if (webset != null && webset.length > 0)
            {
                outParam.set(WORK_LIST_PRINT_FLAG, webset[0].getValue());
            }
        }
        catch (Exception e)
        {
            // 6006042=画面定義情報の参照に失敗しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006042, e), getClass().getName());
        }

        return outParam;
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
            // オンラインの出庫可能な設備が存在しない場合
            if (!hasOnlineNormalStatusEquipment(areas))
            {
                // 6023698=オンラインの出庫可能な設備が存在しません。
                setMessage("6023698");
                return false;
            }

            for (Area area : areas)
            {
                // オンラインで引当対象外の設備が存在す場合
                if (hasOnlineStatusNoAllocateEquipment(area))
                {
                    // MSG-W9111=出庫作業を行えない設備が存在します。よろしいですか？
                    setDispMessage("MSG-W9111");
                    return false;
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

                Station[] stations = getStations(area);

                if (hasOffLineAGC(stations))
                {
                    // オフラインのAGCが存在します。よろしいですか？
                    setDispMessage("MSG-W0037");
                    return false;
                }
                // オンラインで引当対象外の設備が存在す場合
                if (hasOnlineStatusNoAllocateEquipment(area))
                {
                    // MSG-W9111=出庫作業を行えない設備が存在します。よろしいですか？
                    setDispMessage("MSG-W9111");
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * AS/RSの出庫開始を行ったかを返します。
     *
     * @return AS/RS出庫開始を行った場合、true
     */
    public boolean isStartAsrsWork()
    {
        return _startAsrsWork;
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

        // 引当中フラグを更新
        if (!doRetrievalAllocateStart())
        {
            return false;
        }
        doCommit(getClass());

        try
        {
            RetrievalInParameter param = null;
            List<RetrievalInParameter> listParam = new ArrayList<RetrievalInParameter>();
            for (ScheduleParams p : ps)
            {
                param = new RetrievalInParameter(getWmsUserInfo());

                param.setRetrievalPlanDay(p.getString(PLAN_DAY));
                param.setConsignorCode(p.getString(CONSIGNOR_CODE));
                param.setBatchNo(p.getString(BATCH_NO));
                param.setOrderNo(p.getString(BATCH_NO)); // FA対応としてバッチNo.として扱います

                listParam.add(param);
            }

            RetrievalInParameter[] inParams = new RetrievalInParameter[listParam.size()];
            listParam.toArray(inParams);

            WMSSequenceHandler seq = new WMSSequenceHandler(getConnection());
            // 出庫開始単位キー
            String unitKey = seq.nextStartUnitKey();
            // 引当パターンNo.
            String pattern = ps[0].getString(ALLOCATED_PATTERN);
            // 欠品時の作業判定
            String shortage_flag = ps[0].getString(SHORTAGE_WORK_FLAG);
            // 出庫開始日時
            Date startDate = WmsFormatter.toDateTime(DbDateUtil.getSystemDateTime());


            // 引当オペレータ初期化
            RetrievalAllocateOperator allocOpe =
                    new RetrievalAllocateOperator(getConnection(), pattern, true, getClass());
            // 引当処理を行う
            boolean noShortage = allocate(allocOpe ,inParams, pattern, unitKey, startDate, shortage_flag);

            // ここでメッセージをセットしておく（欠品があるかは最後にセット）
            // 6021021=開始しました。
            setMessage("6021021");

            if (_hasWork)
            {
                // AS/RS作業の開始
                if (!startAsRetrieval(ps[0].getBoolean(WORK_LIST_PRINT_FLAG), ps))
                {
                    // 搬送が開始できなかったらロールバック
                    doRollBack(getClass());
                    return false;
                }
            }

            // 開始処理まで完了したところでコミット
            doCommit(getClass());


            try
            {
                // 出庫搬送情報とパレット情報の状態の不整合を補正します。トランザクションの切れ目を明確にするためスリープします。
                Thread.sleep(100);
                allocOpe.updatePalletState();
                doCommit(getClass());
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            // 欠品リスト発行はこのタイミングで行う
            if (!noShortage && ps[0].getBoolean(SHORTAGE_LIST_PRINT_FLAG))
            {
                if (!printShortageList(startDate))
                {
                    // メッセージは「開始しました。（欠品があるため～」を優先するため何もセットしない。
                }
            }

            // 欠品があったことを通知するメッセージをセット
            if (!_hasWork)
            {
                // 6021037=全欠品のため、引当処理を行えませんでした。
                setMessage("6021037");
            }
            else if (!noShortage)
            {
                // 6021036=開始しました。（欠品があるため欠品情報照会を参照してください。）
                setMessage("6021036");
            }

            // 画面定義情報を更新(作業リストのチェックのみ保持)
            String value = ps[0].getBoolean(WORK_LIST_PRINT_FLAG) ? WebSetting.KEYDATA_ON
                                                                 : WebSetting.KEYDATA_OFF;

            updateWebSetting(inParams[0].getTerminalNo(), ps[0].getString(FUNCTION_ID), value);
        }
        catch (LockTimeOutException e)
        {
            // rollback.
            doRollBack(getClass());
            // 6023114=他端末で処理中のため、処理を中断しました。
            setMessage("6023114");
            return false;
        }
        catch (NotFoundException e)
        {
            // rollback.
            doRollBack(getClass());
            // 6023115=他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return false;
        }
        catch (RouteException e)
        {
            // rollback.
            doRollBack(getClass());
            // 搬送ルート異常
            setMessage(getRouteErrorMessage(e.getRouteStatus()));
            return false;
        }
        catch (OperatorException e)
        {
            // rollback.
            doRollBack(getClass());
            //「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // 6023115=他端末で処理されたため、処理を中断しました。
                setMessage("6023115");
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
        catch (CommonException e)
        {
            // 予期しない例外が発生した場合
            // rollback.
            doRollBack(getClass());
            throw e;
        }
        catch (RuntimeException e)
        {
            // 実行時例外が発生した場合
            // rollback.
            doRollBack(getClass());
            throw e;
        }
        finally
        {
            // 引当中フラグを元に戻す
            doRetrievalAllocateEnd();
            // 引当中フラグの更新を確定
            doCommit(getClass());
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
     * 引当処理を行います。<br>
     * リストセルエリアの行単位にトランザクションの確定を行います。<br>
     * 該当行に１件でも欠品があった場合は、その行に対しては何も処理を行いません。<br>
     * ただし欠品発生時に欠品完了するように指定があった場合は、引当可能な分だけ引当を行い、<br>
     * 欠品が発生した行に対して欠品完了処理を行います。<br>
     * 欠品が発生した行があった場合、その行を<code>RetrievalInParameter</code>配列にセットして返します。<br>
     *
     * @param inParams リストセルエリアの行単位でのデータ
     * @param pattern 引当パターン
     * @param unitKey 出庫開始単位キー
     * @param startDate 出庫開始日時
     * @param shortage_flag 欠品時の作業判定
     * @return 全数引当完了の場合、trueを返す。
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected boolean allocate(RetrievalAllocateOperator allocOpe ,RetrievalInParameter[] inParams, String pattern, String unitKey, Date startDate,
            String shortage_flag)
            throws CommonException
    {
        RetrievalPlanFinder planfinder = null;
        RetrievalPlanFinder ticketfinder = null;

        try
        {
            planfinder = new RetrievalPlanFinder(getConnection());
            ticketfinder = new RetrievalPlanFinder(getConnection());

//            // 引当オペレータ初期化
//            RetrievalAllocateOperator allocOpe =
//                    new RetrievalAllocateOperator(getConnection(), pattern, true, getClass());

            // 欠品オペレータ初期化
            ShortageOperator shortageOpe =
                    new ShortageOperator(getConnection(), inParams, pattern, unitKey, startDate, true, getClass());

            // 戻り値の初期化
            boolean retShortage = true;

            // リストセルエリアの行数分処理を行う
            for (RetrievalInParameter inParam : inParams)
            {
                // 予定情報をロックする
                if (!lock(planfinder, inParam))
                {
                    throw new NotFoundException();
                }

                searchTicketByBatchNo(ticketfinder, inParam);

                // 予定データ１件づつ引当処理を行う。
                // 欠品完了以外で１件でも欠品があった場合、この行は処理を中止し、次の行へ処理を移す
                while (ticketfinder.hasNext())
                {

                    RetrievalPlan[] ticketNos = (RetrievalPlan[])ticketfinder.getEntities(100);

                    for (RetrievalPlan ticketNo : ticketNos)
                    {
                        // 伝票No.のスキップフラグ
                        boolean isSkipByTicket = false;
                        // 作成済み作業No.のリセット
                        allocOpe.resetCreateJobNoList();

                        // 伝票単位で予定情報を取得
                        searchRetrievalPlan(planfinder, inParam, ticketNo.getShipTicketNo());

                        while (planfinder.hasNext())
                        {

                            RetrievalPlan[] plans = (RetrievalPlan[])planfinder.getEntities(100);

                            for (RetrievalPlan plan : plans)
                            {
                                int alloc_qty = 0;
                                if (RetrievalInParameter.SHORTAGE_WORK_POSSIBLE_RETRIEVAL.equals(shortage_flag))
                                {
                                    // 可能な作業は出庫するの場合、引当要求数を記憶
                                    alloc_qty = AbstractAllocateOperator.getAllocationQty(getConnection(), plan);
                                }

                                // 在庫の引当を行う
                                int short_qty = allocOpe.allocate(plan, shortage_flag);
                                if (short_qty > 0)
                                {
                                    // 欠品ありの場合
                                    retShortage = false;

                                    if (RetrievalInParameter.SHORTAGE_WORK_TICKET_CANCEL.equals(shortage_flag))
                                    {
                                        isSkipByTicket = true;
                                        // 伝票No.単位で作業取消の場合、手動で元に戻す
                                        allocateCancel(allocOpe.getCreateJobNoList());
                                        break;
                                    }
                                    else if (RetrievalInParameter.SHORTAGE_WORK_POSSIBLE_RETRIEVAL.equals(shortage_flag))
                                    {
                                        // 可能な作業は出庫するの場合、欠品情報を作成し処理続行
                                        shortageOpe.createForPossibleRetrieval(allocOpe.getShortageQty(),
                                                allocOpe.getPlanQty(), allocOpe.getAllocatedQty(), plan);

                                        if (!_hasWork && alloc_qty != short_qty)
                                        {
                                            // 作業ありフラグON
                                            _hasWork = true;
                                        }
                                    }
                                }
                                else if (!_hasWork
                                        && RetrievalInParameter.SHORTAGE_WORK_POSSIBLE_RETRIEVAL.equals(shortage_flag))
                                {
                                    // 作業ありフラグON
                                    _hasWork = true;
                                }
                            }

                            if (isSkipByTicket)
                            {
                                // 既に伝票No.単位で欠品がありスキップする場合は、次の伝票へ
                                break;
                            }
                        }

                        if (!_hasWork && RetrievalInParameter.SHORTAGE_WORK_TICKET_CANCEL.equals(shortage_flag)
                                && !isSkipByTicket)
                        {
                            // 作業ありフラグON
                            _hasWork = true;
                        }
                    }
                }
            }

            // 搬送情報決定
            allocOpe.decideCarryInfo();

            // 伝票No.単位で作業取消で欠品発生時は欠品情報をここで作成
            if (!retShortage && RetrievalInParameter.SHORTAGE_WORK_TICKET_CANCEL.equals(shortage_flag))
            {
                for (RetrievalInParameter param : inParams)
                {
                    searchRetrievalPlan(planfinder, param, "");
                    shortageOpe.create(planfinder, false);
                }
            }

            return retShortage;
        }
        finally
        {
            // finder close
            closeFinder(ticketfinder);
            closeFinder(planfinder);
        }
    }

    /**
     * リストセルエリアの1行(バッチ単位)に該当する出庫予定情報のロックを行います。<br>
     * 引数の<code>RetrievalPlanFinder</code>には、該当バッチ内の伝票No.が格納されます。
     *
     * @param finder <code>RetrievalPlanFinder</code>
     * @param param 出庫入力パラメータ
     * @return ロック対象データがない場合、falseを返す。
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected boolean lock(RetrievalPlanFinder finder, RetrievalInParameter param)
            throws CommonException
    {
        finder.open(true);
        RetrievalPlanSearchKey key = new RetrievalPlanSearchKey();
        // set where
        // 状態フラグ(完了以外)
        key.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
        // 状態フラグ(削除以外)
        key.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        // スケジュール処理フラグ(未スケジュールor欠品予約)
        String[] sch_flag = {
                RetrievalPlan.SCH_FLAG_NOT_SCHEDULE,
                RetrievalPlan.SCH_FLAG_RESERVATION_SHORTAGE,
        };
        key.setSchFlag(sch_flag, true);
        // 荷主コード
        key.setConsignorCode(param.getConsignorCode());
        // 出庫予定日
        key.setPlanDay(param.getRetrievalPlanDay());
        // バッチNo.
        key.setBatchNo(param.getBatchNo());
        // オーダーNo.(FA対応としてバッチNo.として扱います。)
        key.setOrderNo(param.getOrderNo());

        if (finder.searchForUpdate(key, HandlerSysDefines.WAIT_SEC_NOWAIT) == 0)
        {
            // ロック対象がない場合はfalseを返す
            return false;
        }
        return true;
    }

    /**
     * 出庫予定情報の明細を検索します。<br>
     * 伝票No.の引数に値をセットした場合は、伝票No.単位で検索し、値を空で渡した場合は、
     * バッチNo.単位で検索を行います。<br>
     * バッチNo.、オーダーNo.、伝票No.、行No.、作業枝番、商品コード、ロットNo.の順に検索されます。
     *
     * @param finder <code>RetrievalPlanFinder</code>
     * @param inParams 出庫入力パラメータ
     * @param ticket 伝票No.
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected int searchRetrievalPlan(RetrievalPlanFinder finder, RetrievalInParameter param, String ticket)
            throws ReadWriteException
    {
        finder.open(true);
        RetrievalPlanSearchKey key = new RetrievalPlanSearchKey();
        // set where
        // 状態フラグ(完了以外)
        key.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
        // 状態フラグ(削除以外)
        key.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        // スケジュール処理フラグ(未スケジュールor欠品予約)
        String[] sch_flag = {
                RetrievalPlan.SCH_FLAG_NOT_SCHEDULE,
                RetrievalPlan.SCH_FLAG_RESERVATION_SHORTAGE,
        };
        key.setSchFlag(sch_flag, true);
        // 荷主コード
        key.setConsignorCode(param.getConsignorCode());
        // 出庫予定日
        key.setPlanDay(param.getRetrievalPlanDay());
        // バッチNo.
        key.setBatchNo(param.getBatchNo());
        // オーダーNo.(FA対応としてバッチNo.として扱います。)
        key.setOrderNo(param.getOrderNo());
        if (!StringUtil.isBlank(ticket))
        {
            // 伝票No.
            key.setShipTicketNo(ticket);
        }

        // set collect,group,order
        // バッチNo.
        key.setBatchNoOrder(true);
        // オーダーNo.
        key.setOrderNoOrder(true);
        // 伝票No.
        key.setShipTicketNoOrder(true);
        // 行No.
        key.setShipLineNoOrder(true);
        // 枝番
        key.setBranchNoOrder(true);
        // 商品
        key.setItemCodeOrder(true);
        // ロット
        key.setPlanLotNoOrder(true);

        return finder.search(key);
    }

    /**
     * 出庫予定情報の明細を検索します。<br>
     * 伝票No.の引数に値をセットした場合は、伝票No.単位で検索し、値を空で渡した場合は、
     * バッチNo.単位で検索を行います。<br>
     * バッチNo.、オーダーNo.、伝票No.、行No.、作業枝番、商品コード、ロットNo.の順に検索されます。
     *
     * @param finder <code>RetrievalPlanFinder</code>
     * @param inParams 出庫入力パラメータ
     * @param ticket 伝票No.
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected int searchTicketByBatchNo(RetrievalPlanFinder finder, RetrievalInParameter param)
            throws ReadWriteException
    {
        finder.open(true);
        RetrievalPlanSearchKey key = new RetrievalPlanSearchKey();
        // set where
        // 状態フラグ(完了以外)
        key.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
        // 状態フラグ(削除以外)
        key.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        // スケジュール処理フラグ(未スケジュールor欠品予約)
        String[] sch_flag = {
                RetrievalPlan.SCH_FLAG_NOT_SCHEDULE,
                RetrievalPlan.SCH_FLAG_RESERVATION_SHORTAGE,
        };
        key.setSchFlag(sch_flag, true);
        // 荷主コード
        key.setConsignorCode(param.getConsignorCode());
        // 出庫予定日
        key.setPlanDay(param.getRetrievalPlanDay());
        // バッチNo.
        key.setBatchNo(param.getBatchNo());
        // オーダーNo.(FA対応としてバッチNo.として扱います。)
        key.setOrderNo(param.getOrderNo());

        // set collect,group,order
        // 伝票No.
        key.setShipTicketNoCollect();
        key.setShipTicketNoGroup();
        key.setShipTicketNoOrder(true);

        return finder.search(key);
    }

    /**
     * 出庫作業の引当キャンセルを行ないます。<br>
     *
     * @param jobNo キャンセル対象の作業No.リスト
     */
    protected void allocateCancel(List<String> jobNoList)
            throws CommonException
    {
        if (jobNoList.isEmpty())
        {
            // キャンセルする対象がない場合は処理を終了する。
            return;
        }

        // キャンセルした予定一意キーを保持するList
        Set<String> ukeyset = new TreeSet<String>();
        ReleaseAllocateOperator releaseOpe = new ReleaseAllocateOperator(getConnection(), getClass());

        WorkInfoHandler wh = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wkey = new WorkInfoSearchKey();
        RetrievalPlanHandler ph = new RetrievalPlanHandler(getConnection());
        RetrievalPlanAlterKey pkey = new RetrievalPlanAlterKey();

        for (int i = 0; i < jobNoList.size(); i++)
        {
            wkey.clear();
            wkey.setJobNo(jobNoList.get(i));

            WorkInfo work = (WorkInfo)wh.findPrimary(wkey);

            if (releaseOpe.allocateClear(work.getStockId(), work.getPlanQty(), work.getSystemConnKey()))
            {
                // 更新に成功した場合、作業情報を削除
                releaseOpe.allocateWorkDelete(work.getJobNo(), work.getSystemConnKey());
                // 作業に紐付く予定一意キーをセット
                ukeyset.add(work.getPlanUkey());
            }
        }

        // 作業情報が削除された予定情報を更新する
        Iterator<String> it = ukeyset.iterator();

        while (it.hasNext())
        {
            pkey.clear();

            String ukey = it.next();
            pkey.setPlanUkey(ukey);
            if (releaseOpe.checkWorkInfo(ukey))
            {
                // 紐付く作業情報が存在する場合
                pkey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
                pkey.updateSchFlag(RetrievalPlan.SCH_FLAG_RESERVATION_SHORTAGE);
            }
            else
            {
                // 紐付く作業情報が存在しない場合
                pkey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
                pkey.updateSchFlag(RetrievalPlan.SCH_FLAG_NOT_SCHEDULE);
            }
            pkey.updateLastUpdatePname(getClass().getSimpleName());

            // 予定情報の更新
            ph.modify(pkey);
        }
    }

    /**
     * AS/RSの出庫開始処理とリスト発行を行います<br>
     * 設備状態により搬送を開始できなかった場合にfalseを返します。<br>
     *
     * @param isPrint 作業リストを発行の有無
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列
     * @throws CommonException 全ての例外を報告します
     * @return 搬送開始に失敗した場合false、それ以外はtrue。
     */
    protected boolean startAsRetrieval(boolean isPrint, ScheduleParams... ps)
            throws CommonException
    {
        WorkInfoFinder workfinder = null;

        try
        {
            workfinder = new WorkInfoFinder(getConnection());

            // ASRSの出庫情報を取得
            if (searchAsrsWorkInfo(workfinder, ps) == 0)
            {
                // AS/RS作業がない場合
                _startAsrsWork = false;
                // trueで処理を抜ける
                return true;
            }

            List<AsrsInParameter> paramlist = new ArrayList<AsrsInParameter>();
            while (workfinder.hasNext())
            {
                WorkInfo[] asworks = (WorkInfo[])workfinder.getEntities(100);
                for (WorkInfo aswork : asworks)
                {
                    AsrsInParameter param = new AsrsInParameter(getWmsUserInfo());

                    // 作業区分(予定出庫)
                    param.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
                    // 荷主コード
                    param.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));
                    // エリアNo.
                    param.setAreaNo(aswork.getPlanAreaNo());
                    // バッチNo.
                    param.setBatchNo(aswork.getBatchNo());
                    // オーダーNo.
                    param.setOrderNo(aswork.getOrderNo());
                    // 出庫予定日
                    param.setRetrievalDay(aswork.getPlanDay());
                    // ステーションNo.
                    param.setStationNo((String)aswork.getValue(CarryInfo.DEST_STATION_NO, ""));

                    // ステーションの出庫作業可能判定(引当中に変更される可能性があるため、ここでもチェック)
                    if (!retrievalStationCheck(param.getStationNo(), 0))
                    {
                        return false;
                    }

                    paramlist.add(param);
                }
            }

            // オペレータパラメータ生成
            AsrsInParameter[] inParams = new AsrsInParameter[paramlist.size()];
            paramlist.toArray(inParams);

            // 印刷用の設定単位キーを保持するリスト
            List<AsrsOutParameter> outparamlist = new ArrayList<AsrsOutParameter>();

            // オペレータ生成
            AsrsOperator operator = new AsrsOperator(getConnection(), getClass());

            for (AsrsInParameter inparam : inParams)
            {
                // オペレータ呼び出し
                AsrsOutParameter outParam = operator.webStartRetrieval(inparam);
                outparamlist.add(outParam);
            }

            if (isPrint && !outparamlist.isEmpty())
            {
                // 作業リスト印刷処理
                if (!printAsRetrievalWorkList(outparamlist))
                {
                    // 印刷に失敗した場合
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    setMessage("6007042");
                }
            }

            // AS/RS出庫開始を行ったことを記憶する。
            _startAsrsWork = true;

            return true;
        }
        finally
        {
            // finder close
            closeFinder(workfinder);
        }
    }

    /**
     * 作業情報からAS/RSの出庫作業を検索します。<br>
     *
     * @param finder <CODE>WorkInfoFinder</CODE>
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列
     * @throws CommonException
     */
    protected int searchAsrsWorkInfo(WorkInfoFinder finder, ScheduleParams... ps)
            throws CommonException
    {
        finder.open(true);

        // AsWorkInfoControllerのインスタンスを生成する。
        AsWorkInfoController asWorkInfo = new AsWorkInfoController(getConnection(), this.getClass());

        WorkInfo work = new WorkInfo();

        // 必須項目セット
        work.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
        work.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));

        // AsWorkInfoControllerにて検索条件をセットしてもらう。
        WorkInfoSearchKey key = (WorkInfoSearchKey)asWorkInfo.getRetrievalWorkKey(work, null, null, null);
        // ハードウェア区分にAS/RSを追加
        key.setHardwareType(WorkInfo.HARDWARE_TYPE_ASRS);

        // 検索実行
        return finder.search(key);
    }

    /**
     * 出庫作業リストを発行します。<br>
     *
     * @param paramlist 出庫出力パラメータのリスト
     * @return 印刷成功:true 印刷失敗:false
     */
    protected boolean printAsRetrievalWorkList(List<AsrsOutParameter> paramlist)
    {
        // get locale.
        DfkUserInfo ui = getUserInfo();
        Locale locale = getLocale();

        FaRetrievalListDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            AsrsOutParameter[] params = new AsrsOutParameter[paramlist.size()];
            paramlist.toArray(params);

            dasch = new FaRetrievalListDASCH(getConnection(), getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaRetrievalListDASCHParams inparam = new FaRetrievalListDASCHParams();

            // 発行条件をセット
            List<String> ukeys = new ArrayList<String>();
            for (AsrsOutParameter param : params)
            {
                ukeys.add(param.getSettingUnitKey());
            }
            inparam.set(FaRetrievalListDASCHParams.SETTING_UNIT_KEY, ukeys);
            inparam.set(FaRetrievalListDASCHParams.WORK_TYPE, RetrievalInParameter.SEARCH_ASRS_RETRIEVAL_LIST);

            // check count.
            if (dasch.count(inparam) == 0)
            {
                return false;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("AsRetrievalWorkList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                AsRetrievalWorkListParams expparam = new AsRetrievalWorkListParams();
                expparam.set(AsRetrievalWorkListParams.DFK_DS_NO, outparam.get(FaRetrievalListDASCHParams.DFK_DS_NO));
                expparam.set(AsRetrievalWorkListParams.DFK_USER_ID,
                        outparam.get(FaRetrievalListDASCHParams.DFK_USER_ID));
                expparam.set(AsRetrievalWorkListParams.DFK_USER_NAME,
                        outparam.get(FaRetrievalListDASCHParams.DFK_USER_NAME));
                expparam.set(AsRetrievalWorkListParams.SYS_DAY, outparam.get(FaRetrievalListDASCHParams.SYS_DAY));
                expparam.set(AsRetrievalWorkListParams.SYS_TIME, outparam.get(FaRetrievalListDASCHParams.SYS_TIME));
                expparam.set(AsRetrievalWorkListParams.STATION_NO, outparam.get(FaRetrievalListDASCHParams.STATION_NO));
                expparam.set(AsRetrievalWorkListParams.STATION_NAME,
                        outparam.get(FaRetrievalListDASCHParams.STATION_NAME));
                expparam.set(AsRetrievalWorkListParams.WORK_NO, outparam.get(FaRetrievalListDASCHParams.WORK_NO));
                expparam.set(AsRetrievalWorkListParams.LOCATION_NO,
                        outparam.get(FaRetrievalListDASCHParams.LOCATION_NO));
                expparam.set(AsRetrievalWorkListParams.ITEM_CODE, outparam.get(FaRetrievalListDASCHParams.ITEM_CODE));
                expparam.set(AsRetrievalWorkListParams.ITEM_NAME, outparam.get(FaRetrievalListDASCHParams.ITEM_NAME));
                expparam.set(AsRetrievalWorkListParams.LOT_NO, outparam.get(FaRetrievalListDASCHParams.LOT_NO));
                expparam.set(AsRetrievalWorkListParams.RETRIEVAL_COMMAND_DETAIL,
                        outparam.get(FaRetrievalListDASCHParams.RETRIEVAL_COMMAND_DETAIL));
                expparam.set(AsRetrievalWorkListParams.PRIORITY_FLAG,
                        outparam.get(FaRetrievalListDASCHParams.PRIORITY_FLAG));
                expparam.set(AsRetrievalWorkListParams.WORK_QTY, outparam.get(FaRetrievalListDASCHParams.WORK_QTY));
                expparam.set(AsRetrievalWorkListParams.STOCK_QTY, outparam.get(FaRetrievalListDASCHParams.STOCK_QTY));
                expparam.set(AsRetrievalWorkListParams.BATCH_NO, outparam.get(FaRetrievalListDASCHParams.BATCH_NO));
                expparam.set(AsRetrievalWorkListParams.TICKET_NO, outparam.get(FaRetrievalListDASCHParams.TICKET_NO));
                expparam.set(AsRetrievalWorkListParams.LINE_NO, outparam.get(FaRetrievalListDASCHParams.LINE_NO));
                expparam.set(AsRetrievalWorkListParams.AREA_NO, outparam.get(FaRetrievalListDASCHParams.AREA_NO));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return false;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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

        return true;
    }

    /**
     * 欠品リストを発行します。<br>
     *
     * @param paramlist 出庫出力パラメータのリスト
     * @return 印刷成功:true 印刷失敗:false
     */
    protected boolean printShortageList(Date startDate)
    {
        // get locale.
        DfkUserInfo ui = getUserInfo();
        Locale locale = getLocale();

        FaRetrievalShortageInquiryDASCH dasch = null;
        PrintExporter exporter = null;
        try
        {
            dasch = new FaRetrievalShortageInquiryDASCH(getConnection(), getClass(), locale, ui);
            dasch.setForwardOnly(true);

            // set input parameters.
            FaRetrievalShortageInquiryDASCHParams inparam = new FaRetrievalShortageInquiryDASCHParams();
            inparam.set(FaRetrievalShortageInquiryDASCHParams.SETTING_DAY, startDate);
            inparam.set(FaRetrievalShortageInquiryDASCHParams.SETTING_TIME, startDate);

            // check count.
            if (dasch.count(inparam) == 0)
            {
                return false;
            }

            // DASCH call.
            dasch.search(inparam);

            // open exporter.
            ExporterFactory factory = new WmsExporterFactory(locale, ui);
            exporter = factory.newPrinterExporter("ShortageList", false);
            exporter.open();

            // export.
            while (dasch.next())
            {
                Params outparam = dasch.get();
                ShortageListParams expparam = new ShortageListParams();
                expparam.set(ShortageListParams.DFK_DS_NO,
                        outparam.get(FaRetrievalShortageInquiryDASCHParams.DFK_DS_NO));
                expparam.set(ShortageListParams.DFK_USER_ID,
                        outparam.get(FaRetrievalShortageInquiryDASCHParams.DFK_USER_ID));
                expparam.set(ShortageListParams.DFK_USER_NAME,
                        outparam.get(FaRetrievalShortageInquiryDASCHParams.DFK_USER_NAME));
                expparam.set(ShortageListParams.SYS_DAY, outparam.get(FaRetrievalShortageInquiryDASCHParams.SYS_DAY));
                expparam.set(ShortageListParams.SYS_TIME, outparam.get(FaRetrievalShortageInquiryDASCHParams.SYS_TIME));
                expparam.set(ShortageListParams.SETTING_DAY,
                        outparam.get(FaRetrievalShortageInquiryDASCHParams.SETTING_DAY));
                expparam.set(ShortageListParams.SETTING_TIME,
                        outparam.get(FaRetrievalShortageInquiryDASCHParams.SETTING_TIME));
                expparam.set(ShortageListParams.BATCH_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.BATCH_NO));
                expparam.set(ShortageListParams.TICKET_NO,
                        outparam.get(FaRetrievalShortageInquiryDASCHParams.TICKET_NO));
                expparam.set(ShortageListParams.LINE_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.LINE_NO));
                expparam.set(ShortageListParams.ITEM_CODE,
                        outparam.get(FaRetrievalShortageInquiryDASCHParams.ITEM_CODE));
                expparam.set(ShortageListParams.ITEM_NAME,
                        outparam.get(FaRetrievalShortageInquiryDASCHParams.ITEM_NAME));
                expparam.set(ShortageListParams.LOT_NO, outparam.get(FaRetrievalShortageInquiryDASCHParams.LOT_NO));
                expparam.set(ShortageListParams.PLAN_QTY, outparam.get(FaRetrievalShortageInquiryDASCHParams.PLAN_QTY));
                expparam.set(ShortageListParams.SHORTAGE_QTY,
                        outparam.get(FaRetrievalShortageInquiryDASCHParams.SHORTAGE_QTY));
                if (!exporter.write(expparam))
                {
                    break;
                }
            }

            // execute print.
            try
            {
                exporter.print();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                return false;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
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

        return true;
    }

    /**
     * 画面定義情報を更新します。<br>
     * 更新処理で異常が発生した場合は、Exceptionをスローせず、ロギングのみ行います。
     *
     * @param term 端末No.
     * @param funcid 画面ID
     * @param value 更新値
     */
    protected void updateWebSetting(String term, String funcid, String value)
    {
        try
        {
            WebSettingHandler webhandler = new WebSettingHandler(getConnection());
            WebSettingAlterKey akey = new WebSettingAlterKey();

            akey.setTerminalNo(term);
            akey.setFunctionid(funcid);
            akey.setKeydata(WebSetting.KEY_LIST_CHECK);

            akey.updateValue(value);
            akey.updateLastUpdatePname(getClass().getSimpleName());

            try
            {
                webhandler.modify(akey);
            }
            catch (NotFoundException e)
            {
                // 更新対象がない場合は新規作成
                WebSetting newdata = new WebSetting();

                newdata.setTerminalNo(term);
                newdata.setFunctionid(funcid);
                newdata.setKeydata(WebSetting.KEY_LIST_CHECK);
                newdata.setValue(value);
                newdata.setRegistPname(getClass().getSimpleName());
                newdata.setLastUpdatePname(getClass().getSimpleName());

                webhandler.create(newdata);
            }
        }
        catch (Exception e)
        {
            // 6006043=画面定義情報の更新に失敗しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006043, e), getClass().getName());
        }
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

        areaKey.setCollect(new FieldName(Area.STORE_NAME, FieldName.ALL_FIELDS));
        areaKey.setCollect(AllocatePriority.STATION_NO);

        return (Area[])areah.find(areaKey);
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
            Station[] stations = getStations(area);
            // ステーションのチェック
            for (Station station : stations)
            {
                // ユニット出庫運用専用ステーションは対象外
                if (station.isUnitOnly())
                {
                    continue;
                }

                if (!isStationStatusNormal(station.getStatus()) || isStationSuspendOn(station.getSuspend()))
                {
                    // ステーションが正常でない場合
                    continue;
                }

                Station[] st = {
                    station
                };
                // アイルの取得
                Aisle[] aisles = getAisles(st);

                for (Aisle aisle : aisles)
                {
                    // AGCがオンラインかどうか
                    if (!isOnLine(aisle.getValue(GroupController.STATUS_FLAG)))
                    {
                        // AGCがオンラインでない場合
                        continue;
                    }

                    // アイルのチェック
                    if (isAisleStatusNormal(aisle.getStatus()))
                    {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    /**
     * オンラインで引当対象外の設備があるかどうかを判定します。<BR>
     * <ol>以下のチェックを行います。
     * <il>オンラインチェック
     * <il>ステーション状態チェック
     * <il>モードチェック
     * <il>中断中チェック
     * </ol>
     *
     * @param area エリア
     * @return 引当対象外の設備あり : true それ以外 : false
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException アイルに紐つくステーションが存在しない場合にスローされます。
     */
    protected boolean hasOnlineStatusNoAllocateEquipment(Area area)
            throws InvalidDefineException,
                ReadWriteException
    {
        // ステーションの取得
        Station[] stations = getStations(area);

        // アイルの取得
        Aisle[] aisles = getAisles(stations);

        for (Aisle aisle : aisles)
        {
            // AGCがオンラインかどうか
            if (!isOnLine(aisle.getValue(GroupController.STATUS_FLAG)))
            {
                continue;
            }

            // アイルのチェック
            // 異常な設備の場合
            if (isAisleStatusError(aisle.getStatus()))
            {
                return true;
            }
        }

        // ステーションのチェック
        for (Station station : stations)
        {
            // ユニット出庫運用専用ステーションは対象外
            if (station.isUnitOnly())
            {
                continue;
            }

            if (!isStationStatusNormal(station.getStatus()) || isStationSuspendOn(station.getSuspend())
                    || !isRetrievalModeEnable(station))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * 1件でもオフラインのAGCが存在するかどうかを判定します。<BR>
     *
     * @param stations ステーション
     * @return 1件でもオフラインのグループコントローラが存在するなら : true それ以外 : false
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException アイルに紐つくステーションが存在しない場合にスローされます。
     */
    protected boolean hasOffLineAGC(Station[] stations)
            throws InvalidDefineException,
                ReadWriteException
    {
        for (Station station : stations)
        {
            // オフラインの場合
            if (isOffLine(station.getValue(GroupController.STATUS_FLAG)))
            {
                return true;
            }
        }

        // ステーションに紐つくアイルの取得
        Aisle[] aisles = getAisles(stations);
        for (Aisle aisle : aisles)
        {
            // オフラインの場合
            if (isOffLine(aisle.getValue(GroupController.STATUS_FLAG)))
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
        if (!Aisle.AISLE_STATUS_NORMAL.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 指定されたステーションが中断中かどうかを判定します。
     *
     * @param status 判定するテーションの状態
     * @return 異常な場合ture、それ以外false
     */
    protected boolean isStationSuspendOn(Object status)
    {
        if (Station.SUSPEND_ON.equals(status))
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
    protected boolean isStationStatusNormal(Object status)
    {
        if (Station.STATION_STATUS_NORMAL.equals(status))
        {
            // 正常であればtrueを返す
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
     * エリアに紐つくステーションを取得します。<BR>
     *
     * @param area エリア
     * @return ステーション
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException エリア、作業場に紐つくステーションが存在しない場合にスローされます。
     */
    protected Station[] getStations(Area area)
            throws ReadWriteException,
                InvalidDefineException
    {
        StationHandler handler = new StationHandler(getConnection());
        StationSearchKey stKey = new StationSearchKey();

        // set where
        stKey.setSendable(Station.SENDABLE_TRUE); // 送信可
        stKey.setStationType(Station.STATION_TYPE_INOUT, "=", "(", "", false);
        stKey.setStationType(Station.STATION_TYPE_OUT, "=", "", ")", true);

        stKey.setKey(Area.AREA_NO, area.getAreaNo()); // 引当エリア

        String stno = (String)area.getValue(AllocatePriority.STATION_NO);
        // 引当パターンテーブルに作業場が指定されていた場合、限定する
        if (!StringUtil.isBlank(stno))
        {
            try
            {
                Station st = StationFactory.makeStation(getConnection(), stno);

                // 設定ステーションが作業場の場合のみステーションを限定する
                if (st instanceof WorkPlace)
                {
                    WorkPlace wp = (WorkPlace)st;

                    // 作業場にある全ステーションをセット
                    stKey.setStationNo(wp.getWPStations(), true);
                }
            }
            catch (NotFoundException e)
            {
                // ステーションが見つからなかった場合
                throw new InvalidDefineException();
            }
        }

        // set join
        stKey.setJoin(Station.WH_STATION_NO, Area.WHSTATION_NO);

        // set collect
        stKey.setCollect(Area.AREA_NO);
        stKey.setCollect(new FieldName(Station.STORE_NAME, FieldName.ALL_FIELDS));

        // set order by
        stKey.setStationNoOrder(true);

        return (Station[])handler.find(stKey);
    }

    /**
     * ステーションに紐つくアイルを取得します。<BR>
     *
     * @param stations ステーション
     * @return アイル
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException エリアに紐つくアイルが存在しない場合にスローされます。
     */
    protected Aisle[] getAisles(Station[] stations)
            throws ReadWriteException,
                InvalidDefineException
    {
        AisleSearchKey aisleKey = new AisleSearchKey();
        List<String> aisleList = new ArrayList<String>();
        boolean aisleSet = true;

        for (int i = 0; i < stations.length; i++)
        {
            if (StringUtil.isBlank(stations[i].getAisleStationNo()))
            {
                aisleSet = false;
                break;
            }
            if (!aisleList.contains(stations[i].getAisleStationNo()))
            {
                aisleList.add(stations[i].getAisleStationNo());
            }
        }

        // set where
        if (aisleSet)
        {
            // アイルNo.から検索する
            String[] aisles = new String[aisleList.size()];
            aisleList.toArray(aisles);
            aisleKey.setStationNo(aisles, true);
        }
        else
        {
            // 倉庫No.から検索する
            aisleKey.setWhStationNo(stations[0].getWhStationNo());
        }

        // set join
        aisleKey.setJoin(Aisle.CONTROLLER_NO, GroupController.CONTROLLER_NO);

        // set collect
        aisleKey.setCollect(new FieldName(Aisle.STORE_NAME, FieldName.ALL_FIELDS));
        aisleKey.setCollect(GroupController.CONTROLLER_NO);
        aisleKey.setCollect(GroupController.STATUS_FLAG);

        Aisle[] aisles = (Aisle[])new AisleHandler(getConnection()).find(aisleKey);

        if (ArrayUtil.isEmpty(aisles))
        {
            throw new InvalidDefineException("no aisle info of station");
        }

        return aisles;
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
