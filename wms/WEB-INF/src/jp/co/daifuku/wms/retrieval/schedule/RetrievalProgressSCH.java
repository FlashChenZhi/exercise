// $Id: RetrievalProgressSCH.java 6901 2010-01-25 11:22:02Z kumano $
package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.retrieval.schedule.RetrievalProgressSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.util.DisplayUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;

/**
 * 出庫作業進捗のスケジュール処理を行います。
 *
 * @version $Revision: 6901 $, $Date: 2010-01-25 20:22:02 +0900 (月, 25 1 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class RetrievalProgressSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 進捗リストセルの最大表示件数
     */
    private static final int MAX_DISPLAY_PROGRESS = 2;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

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
    public RetrievalProgressSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 表示用パラメータを取得します。<BR>
     * @param searchParam 検索パラメータ
     * @return 表示用パラメータ
     * @throws CommonException 全ての例外をスローします。
     */
    public List<Params> query(ScheduleParams searchParam)
            throws CommonException
    {
        List<Params> outParam = new ArrayList<Params>();

        // 初期表示、予定日が未表示の場合
        if (StringUtil.isBlank(searchParam.getString(PLAN_DAY)))
        {
            // 基準となる予定日を取得し、セットします。
            searchParam.set(PLAN_DAY, standardDay(searchParam));
        }

        if (RetrievalInParameter.COLLECT_ALL_PLANDATE.equals(searchParam.getString(COLLECT_FLAG)))
        {
            // 全予定日集約データを取得します。
            outParam =
                    setAllPlanData(searchParam.getString(CONSIGNOR_CODE),
                            RetrievalOutParameter.BUTTON_CONTROL_FLAG_ALL_OFF);
        }
        else if (RetrievalInParameter.COLLECT_PLANDATE_UNIT.equals(searchParam.getString(COLLECT_FLAG)))
        {
            // ボタン制御を行います。
            String button = buttonControl(searchParam);
            // 予定日別データを取得します。
            outParam = setPlanUnitData(searchParam, button);
        }
        else
        {
            // バッチNo.が未入力の場合
            if (StringUtil.isBlank(searchParam.getString(BATCH_NO)))
            {
                // 基準日を基に最小のバッチNo.を取得
                searchParam.set(BATCH_NO, standardBatchNo(searchParam.getString(CONSIGNOR_CODE),
                        searchParam.getString(PLAN_DAY)));
            }

            // ボタン制御を行います。
            String button = buttonControl(searchParam);
            // 予定日＋バッチNo.別データを取得します。
            outParam = setBatchUnitData(searchParam, button);
        }
        return outParam;
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
     * 全予定日データを取得します。<BR>
     * @param consignorCode 検索パラメータ
     * @param button ボタン制御フラグ
     * @return 表示データ
     * @throws CommonException 全ての例外をスローします。
     */
    protected List<Params> setAllPlanData(String consignorCode, String button)
            throws CommonException
    {
        List<Params> outParams = new ArrayList<Params>();

        // 出庫予定ハンドラ
        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey shKy = new RetrievalPlanSearchKey();
        RetrievalPlan[] entity = null;

        // 返却用パラメータ(全予定日の場合、取得データ件数は1件)
        Params param = new Params();

        // 予定データ件数を取得
        shKy.setConsignorCode(consignorCode);
        shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        int cnt = handler.count(shKy);
        if (cnt == 0)
        {
            return outParams;
        }

        // ボタン制御フラグ
        param.set(BUTTON_CONTROL_FLAG, button);

        // オーダー件数を取得します
        shKy.clear();
        shKy.setConsignorCode(consignorCode);
        shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        shKy.setOrderNoGroup();
        shKy.setOrderNoCollect();
        RetrievalPlan[] planOrder = (RetrievalPlan[])handler.find(shKy);
        param.set(PLAN_ORDER_COUNT, planOrder.length);
        // 実績オーダー件数;
        int resultCnt = 0;
        for (int i = 0; i < planOrder.length; i++)
        {
            shKy.clear();
            shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            shKy.setOrderNo(planOrder[i].getOrderNo());
            int planCnt = handler.count(shKy);
            shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
            int compCnt = handler.count(shKy);
            if (planCnt == compCnt)
            {
                resultCnt++;
            }
        }
        param.set(RESULT_ORDER_COUNT, resultCnt);

        // 伝票数を取得します。
        shKy.clear();
        shKy.setConsignorCode(consignorCode);
        shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        shKy.setShipTicketNoGroup();
        param.set(TICKET_COUNT, handler.count(shKy));

        // 予定商品数を取得します。
        shKy.clear();
        shKy.setConsignorCode(consignorCode);
        shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        param.set(PLAN_ITEM_CONT, handler.count(shKy));
        // 実績商品数を取得します。
        shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
        param.set(RESULT_ITEM_CONT, handler.count(shKy));

        // 出庫数、欠品数を取得します。
        shKy.clear();
        shKy.setConsignorCode(consignorCode);
        shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        shKy.setPlanQtyCollect("SUM");
        shKy.setResultQtyCollect("SUM");
        shKy.setShortageQtyCollect("SUM");
        entity = (RetrievalPlan[])handler.find(shKy);
        param.set(PLAN_QTY, entity[0].getPlanQty());
        param.set(SHORTAGE_QTY, entity[0].getShortageQty());
        param.set(RESULT_QTY, entity[0].getResultQty());
        //進捗率 : ((実績数+欠品数)/予定数)*100
        double progress =
                (((double)(entity[0].getResultQty() + entity[0].getShortageQty()) / entity[0].getPlanQty()) * 100);
        param.set(PROGRESS_RATE1, progress);

        // ケース数、ピース数を取得します。
        shKy.setItemCodeCollect();
        shKy.setItemCodeGroup();
        shKy.setGroup(Item.ENTERING_QTY);
        shKy.setJoin(RetrievalPlan.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        shKy.setJoin(RetrievalPlan.ITEM_CODE, Item.ITEM_CODE);
        shKy.setCollect(Item.ENTERING_QTY);
        entity = (RetrievalPlan[])handler.find(shKy);

        int planCaseQty = 0;
        int planPieceQty = 0;
        int resultCaseQty = 0;
        int resultPieceQty = 0;
        for (int i = 0; i < entity.length; i++)
        {
            int enteringQty = entity[i].getBigDecimal(Item.ENTERING_QTY).intValue();

            planCaseQty = planCaseQty + DisplayUtil.getCaseQty(entity[i].getPlanQty(), enteringQty);
            planPieceQty = planPieceQty + DisplayUtil.getPieceQty(entity[i].getPlanQty(), enteringQty);
            resultCaseQty = resultCaseQty + DisplayUtil.getCaseQty(entity[i].getResultQty(), enteringQty);
            resultPieceQty = resultPieceQty + DisplayUtil.getPieceQty(entity[i].getResultQty(), enteringQty);
        }

        param.set(PLAN_CASE_QTY, planCaseQty);
        param.set(PLAN_PIECE_QTY, planPieceQty);
        param.set(RESULT_CASE_QTY, resultCaseQty);
        param.set(RESULT_PIECE_QTY, resultPieceQty);

        // オーダー件数
        param.set(ORDER_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_ORDER_COUNT)) + "/"
                + WmsFormatter.getNumFormat(param.getInt(PLAN_ORDER_COUNT)));
        // 明細数
        param.set(DETAIL_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_ITEM_CONT)) + "/"
                + WmsFormatter.getNumFormat(param.getInt(PLAN_ITEM_CONT)));
        // 出庫数
        param.set(RETRIEVAL_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_QTY)) + "/"
                + WmsFormatter.getNumFormat(param.getInt(PLAN_QTY)));
        // ｹｰｽ数
        param.set(CASE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_CASE_QTY)) + "/"
                + WmsFormatter.getNumFormat(param.getInt(PLAN_CASE_QTY)));
        // ﾋﾟｰｽ数
        param.set(PIECE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_PIECE_QTY)) + "/"
                + WmsFormatter.getNumFormat(param.getInt(PLAN_PIECE_QTY)));

        outParams.add(param);

        return outParams;
    }

    /**
     * 予定日別データを取得します。<BR>
     * @param inParam 検索パラメータ
     * @param button ボタン制御フラグ
     * @return 表示データ
     * @throws CommonException 全ての例外をスローします。
     */
    protected List<Params> setPlanUnitData(ScheduleParams inParam, String button)
            throws CommonException
    {
        List<Params> outParams = new ArrayList<Params>();

        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey shKy = new RetrievalPlanSearchKey();

        // 検索条件をセット
        if (RetrievalInParameter.PROCESS_FLAG_VIEW.equals(inParam.getString(PROCESS_FLAG)))
        {
            shKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            shKy.setPlanDay(inParam.getString(PLAN_DAY), ">=");
            shKy.setPlanDayOrder(true);
        }
        else if (RetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {

            shKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            shKy.setPlanDay(inParam.getString(PLAN_DAY), "<");
            shKy.setPlanDayOrder(false);
        }
        else if (RetrievalInParameter.PROCESS_FLAG_NEXT_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {

            shKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            shKy.setPlanDay(inParam.getString(PLAN_DAY), ">");
            shKy.setPlanDayOrder(true);
        }
        shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        shKy.setPlanDayCollect();
        shKy.setPlanDayGroup();

        // 検索結果を取得
        RetrievalPlan[] entity = (RetrievalPlan[])handler.find(shKy, MAX_DISPLAY_PROGRESS);
        if (entity == null || entity.length == 0)
        {
            return outParams;
        }

        // 配列の順序を逆順にします。
        if (RetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {
            entity = turnArray(entity);
        }


        for (int i = 0; i < entity.length; i++)
        {
            Params param = new Params();
            // 予定日
            param.set(PLAN_DAY, WmsFormatter.toDate(entity[i].getPlanDay()));
            // ボタン制御フラグ
            param.set(BUTTON_CONTROL_FLAG, button);

            // オーダー件数を取得します
            shKy.clear();
            shKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            shKy.setPlanDay(entity[i].getPlanDay());
            shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            shKy.setOrderNoGroup();
            shKy.setOrderNoCollect();
            RetrievalPlan[] planOrder = (RetrievalPlan[])handler.find(shKy);
            param.set(PLAN_ORDER_COUNT, planOrder.length);
            // 実績オーダー件数;
            int resultCnt = 0;
            for (int j = 0; j < planOrder.length; j++)
            {
                shKy.clear();
                shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
                shKy.setPlanDay(entity[i].getPlanDay());
                shKy.setOrderNo(planOrder[j].getOrderNo());
                int planCnt = handler.count(shKy);
                shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
                int compCnt = handler.count(shKy);
                if (planCnt == compCnt)
                {
                    resultCnt++;
                }
            }
            param.set(RESULT_ORDER_COUNT, resultCnt);

            // 伝票数を取得します。
            shKy.clear();
            shKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            shKy.setPlanDay(entity[i].getPlanDay());
            shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            shKy.setShipTicketNoGroup();
            param.set(TICKET_COUNT, handler.count(shKy));

            // 予定商品数を取得します。
            shKy.clear();
            shKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            shKy.setPlanDay(entity[i].getPlanDay());
            shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            param.set(PLAN_ITEM_CONT, handler.count(shKy));
            // 実績商品数を取得します。
            shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
            param.set(RESULT_ITEM_CONT, handler.count(shKy));

            // 出庫数、欠品数を取得します。
            shKy.clear();
            shKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            shKy.setPlanDay(entity[i].getPlanDay());
            shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            shKy.setPlanQtyCollect("SUM");
            shKy.setResultQtyCollect("SUM");
            shKy.setShortageQtyCollect("SUM");
            RetrievalPlan[] qtyEnt = (RetrievalPlan[])handler.find(shKy);
            param.set(PLAN_QTY, qtyEnt[0].getPlanQty());
            param.set(SHORTAGE_QTY, qtyEnt[0].getShortageQty());
            param.set(RESULT_QTY, qtyEnt[0].getResultQty());
            //進捗率 : ((実績数+欠品数)/予定数)*100
            double progress =
                    (((double)(qtyEnt[0].getResultQty() + qtyEnt[0].getShortageQty()) / qtyEnt[0].getPlanQty()) * 100);
            param.set(PROGRESS_RATE1, progress);

            // ケース数、ピース数を取得します。
            shKy.setItemCodeCollect();
            shKy.setItemCodeGroup();
            shKy.setGroup(Item.ENTERING_QTY);
            shKy.setJoin(RetrievalPlan.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
            shKy.setJoin(RetrievalPlan.ITEM_CODE, Item.ITEM_CODE);
            shKy.setCollect(Item.ENTERING_QTY);
            qtyEnt = (RetrievalPlan[])handler.find(shKy);

            int planCaseQty = 0;
            int planPieceQty = 0;
            int resultCaseQty = 0;
            int resultPieceQty = 0;
            for (int j = 0; j < qtyEnt.length; j++)
            {
                int enteringQty = qtyEnt[j].getBigDecimal(Item.ENTERING_QTY).intValue();

                planCaseQty = planCaseQty + DisplayUtil.getCaseQty(qtyEnt[j].getPlanQty(), enteringQty);
                planPieceQty = planPieceQty + DisplayUtil.getPieceQty(qtyEnt[j].getPlanQty(), enteringQty);
                resultCaseQty = resultCaseQty + DisplayUtil.getCaseQty(qtyEnt[j].getResultQty(), enteringQty);
                resultPieceQty = resultPieceQty + DisplayUtil.getPieceQty(qtyEnt[j].getResultQty(), enteringQty);

            }
            param.set(PLAN_CASE_QTY, planCaseQty);
            param.set(PLAN_PIECE_QTY, planPieceQty);
            param.set(RESULT_CASE_QTY, resultCaseQty);
            param.set(RESULT_PIECE_QTY, resultPieceQty);

            // オーダー件数
            param.set(ORDER_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_ORDER_COUNT)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_ORDER_COUNT)));
            // 明細数
            param.set(DETAIL_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_ITEM_CONT)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_ITEM_CONT)));
            // 出庫数
            param.set(RETRIEVAL_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_QTY)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_QTY)));
            // ｹｰｽ数
            param.set(CASE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_CASE_QTY)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_CASE_QTY)));
            // ﾋﾟｰｽ数
            param.set(PIECE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_PIECE_QTY)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_PIECE_QTY)));

            outParams.add(param);
        }
        return outParams;
    }

    /**
     * 予定日＋バッチNo.別データを取得します。<BR>
     * @param inParam 検索パラメータ
     * @param button ボタン制御フラグ
     * @return 表示データ
     * @throws CommonException 全ての例外をスローします。
     */
    protected List<Params> setBatchUnitData(ScheduleParams inParam, String button)
            throws CommonException
    {
        List<Params> outParams = new ArrayList<Params>();

        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey shKy = new RetrievalPlanSearchKey();

        // 検索結果を取得
        RetrievalPlan[] entity = butchNoUnitSql(inParam);
        if (entity == null || entity.length == 0)
        {
            return outParams;
        }

        // 配列の順序を逆順にします。
        if (RetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {
            entity = turnArray(entity);
        }

        for (int i = 0; i < entity.length; i++)
        {
            Params param = new Params();
            // 予定日
            param.set(PLAN_DAY, WmsFormatter.toDate(entity[i].getPlanDay()));
            // バッチNo.
            param.set(BATCH_NO, entity[i].getBatchNo());
            // ボタン制御フラグ
            param.set(BUTTON_CONTROL_FLAG, button);

            // オーダー件数を取得します
            shKy.clear();
            shKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            shKy.setPlanDay(entity[i].getPlanDay());
            shKy.setBatchNo(entity[i].getBatchNo());
            shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            shKy.setOrderNoGroup();
            shKy.setOrderNoCollect();
            RetrievalPlan[] planOrder = (RetrievalPlan[])handler.find(shKy);
            param.set(PLAN_ORDER_COUNT, planOrder.length);
            // 実績オーダー件数;
            int resultCnt = 0;
            for (int j = 0; j < planOrder.length; j++)
            {
                shKy.clear();
                shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
                shKy.setPlanDay(entity[i].getPlanDay());
                shKy.setBatchNo(entity[i].getBatchNo());
                shKy.setOrderNo(planOrder[j].getOrderNo());
                int planCnt = handler.count(shKy);
                shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
                int compCnt = handler.count(shKy);
                if (planCnt == compCnt)
                {
                    resultCnt++;
                }
            }
            param.set(RESULT_ORDER_COUNT, resultCnt);

            // 伝票数を取得します。
            shKy.clear();
            shKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            shKy.setPlanDay(entity[i].getPlanDay());
            shKy.setBatchNo(entity[i].getBatchNo());
            shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            shKy.setShipTicketNoGroup();
            param.set(TICKET_COUNT, handler.count(shKy));

            // 明細数を取得します。
            shKy.clear();
            shKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            shKy.setPlanDay(entity[i].getPlanDay());
            shKy.setBatchNo(entity[i].getBatchNo());
            shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            param.set(PLAN_ITEM_CONT, handler.count(shKy));
            // 実績明細数
            shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
            param.set(RESULT_ITEM_CONT, handler.count(shKy));

            // 出庫数、欠品数を取得します。
            shKy.clear();
            shKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            shKy.setPlanDay(entity[i].getPlanDay());
            shKy.setBatchNo(entity[i].getBatchNo());
            shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            shKy.setPlanQtyCollect("SUM");
            shKy.setResultQtyCollect("SUM");
            shKy.setShortageQtyCollect("SUM");
            RetrievalPlan[] qtyEnt = (RetrievalPlan[])handler.find(shKy);
            param.set(PLAN_QTY, qtyEnt[0].getPlanQty());
            param.set(SHORTAGE_QTY, qtyEnt[0].getShortageQty());
            param.set(RESULT_QTY, qtyEnt[0].getResultQty());
            //進捗率 : ((実績数+欠品数)/予定数)*100
            double progress =
                    (((double)(qtyEnt[0].getResultQty() + qtyEnt[0].getShortageQty()) / qtyEnt[0].getPlanQty()) * 100);
            param.set(PROGRESS_RATE1, progress);

            // ケース数、ピース数を取得します。
            shKy.setItemCodeCollect();
            shKy.setItemCodeGroup();
            shKy.setGroup(Item.ENTERING_QTY);
            shKy.setJoin(RetrievalPlan.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
            shKy.setJoin(RetrievalPlan.ITEM_CODE, Item.ITEM_CODE);
            shKy.setCollect(Item.ENTERING_QTY);
            qtyEnt = (RetrievalPlan[])handler.find(shKy);

            int planCaseQty = 0;
            int planPieceQty = 0;
            int resultCaseQty = 0;
            int resultPieceQty = 0;
            for (int j = 0; j < qtyEnt.length; j++)
            {
                int enteringQty = qtyEnt[j].getBigDecimal(Item.ENTERING_QTY).intValue();

                planCaseQty = planCaseQty + DisplayUtil.getCaseQty(qtyEnt[j].getPlanQty(), enteringQty);
                planPieceQty = planPieceQty + DisplayUtil.getPieceQty(qtyEnt[j].getPlanQty(), enteringQty);
                resultCaseQty = resultCaseQty + DisplayUtil.getCaseQty(qtyEnt[j].getResultQty(), enteringQty);
                resultPieceQty = resultPieceQty + DisplayUtil.getPieceQty(qtyEnt[j].getResultQty(), enteringQty);

            }
            param.set(PLAN_CASE_QTY, planCaseQty);
            param.set(PLAN_PIECE_QTY, planPieceQty);
            param.set(RESULT_CASE_QTY, resultCaseQty);
            param.set(RESULT_PIECE_QTY, resultPieceQty);

            // オーダー件数
            param.set(ORDER_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_ORDER_COUNT)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_ORDER_COUNT)));
            // 明細数
            param.set(DETAIL_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_ITEM_CONT)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_ITEM_CONT)));
            // 出庫数
            param.set(RETRIEVAL_COUNT, WmsFormatter.getNumFormat(param.getInt(RESULT_QTY)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_QTY)));
            // ｹｰｽ数
            param.set(CASE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_CASE_QTY)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_CASE_QTY)));
            // ﾋﾟｰｽ数
            param.set(PIECE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_PIECE_QTY)) + "/"
                    + WmsFormatter.getNumFormat(param.getInt(PLAN_PIECE_QTY)));

            outParams.add(param);
        }
        return outParams;
    }

    /**
     * 予定日＋バッチNo.別集約のボタン制御による予定日とバッチNo.を取得するメソッドです。<BR>
     * 表示ボタン、前頁ボタン、次頁ボタンの押下時に表示する予定日とバッチNo.を取得します。<BR>
     * @param inParam 検索パラメータ
     * @return 表示用パラメータ
     * @throws CommonException 全ての例外をスローします。
     */
    protected RetrievalPlan[] butchNoUnitSql(ScheduleParams inParam)
            throws CommonException
    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;

        StringBuffer sql = null;
        // 検索条件をセット
        if (RetrievalInParameter.PROCESS_FLAG_VIEW.equals(inParam.getString(PROCESS_FLAG)))
        {
            sql = new StringBuffer();
            sql.append("SELECT ");
            sql.append("DNRETRIEVALPLAN.PLAN_DAY PLAN_DAY");
            sql.append(" ,DNRETRIEVALPLAN.BATCH_NO BATCH_NO");
            sql.append(" FROM");
            sql.append(" DNRETRIEVALPLAN");
            sql.append(" WHERE");
            sql.append(" ((DNRETRIEVALPLAN.PLAN_DAY = ");
            sql.append(DBFormat.format(inParam.getString(PLAN_DAY)));
            sql.append(" AND DNRETRIEVALPLAN.BATCH_NO >= ");
            sql.append(DBFormat.format(inParam.getString(BATCH_NO)));
            sql.append(")");
            sql.append(" OR DNRETRIEVALPLAN.PLAN_DAY > ");
            sql.append(DBFormat.format(inParam.getString(PLAN_DAY)));
            sql.append(")");
            sql.append(" AND DNRETRIEVALPLAN.CONSIGNOR_CODE = ");
            sql.append(DBFormat.format(inParam.getString(CONSIGNOR_CODE)));
            sql.append(" AND DNRETRIEVALPLAN.STATUS_FLAG != ");
            sql.append(DBFormat.format(RetrievalPlan.STATUS_FLAG_DELETE));
            sql.append(" GROUP BY");
            sql.append(" DNRETRIEVALPLAN.PLAN_DAY");
            sql.append(" ,DNRETRIEVALPLAN.BATCH_NO");
            sql.append(" ORDER BY");
            sql.append(" DNRETRIEVALPLAN.PLAN_DAY ASC");
            sql.append(" ,DNRETRIEVALPLAN.BATCH_NO ASC");

        }
        else if (RetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {
            sql = new StringBuffer();
            sql.append("SELECT ");
            sql.append("DNRETRIEVALPLAN.PLAN_DAY PLAN_DAY");
            sql.append(" ,DNRETRIEVALPLAN.BATCH_NO BATCH_NO");
            sql.append(" FROM");
            sql.append(" DNRETRIEVALPLAN");
            sql.append(" WHERE");
            sql.append(" ((DNRETRIEVALPLAN.PLAN_DAY = ");
            sql.append(DBFormat.format(inParam.getString(PLAN_DAY)));
            sql.append(" AND DNRETRIEVALPLAN.BATCH_NO < ");
            sql.append(DBFormat.format(inParam.getString(BATCH_NO)));
            sql.append(")");
            sql.append(" OR DNRETRIEVALPLAN.PLAN_DAY < ");
            sql.append(DBFormat.format(inParam.getString(PLAN_DAY)));
            sql.append(")");
            sql.append(" AND DNRETRIEVALPLAN.CONSIGNOR_CODE = ");
            sql.append(DBFormat.format(inParam.getString(CONSIGNOR_CODE)));
            sql.append(" AND DNRETRIEVALPLAN.STATUS_FLAG != ");
            sql.append(DBFormat.format(RetrievalPlan.STATUS_FLAG_DELETE));
            sql.append(" GROUP BY");
            sql.append(" DNRETRIEVALPLAN.PLAN_DAY");
            sql.append(" ,DNRETRIEVALPLAN.BATCH_NO");
            sql.append(" ORDER BY");
            sql.append(" DNRETRIEVALPLAN.PLAN_DAY DESC");
            sql.append(" ,DNRETRIEVALPLAN.BATCH_NO DESC");

        }
        else if (RetrievalInParameter.PROCESS_FLAG_NEXT_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {
            sql = new StringBuffer();
            sql.append("SELECT ");
            sql.append("DNRETRIEVALPLAN.PLAN_DAY PLAN_DAY");
            sql.append(" ,DNRETRIEVALPLAN.BATCH_NO BATCH_NO");
            sql.append(" FROM");
            sql.append(" DNRETRIEVALPLAN");
            sql.append(" WHERE");
            sql.append(" ((DNRETRIEVALPLAN.PLAN_DAY = ");
            sql.append(DBFormat.format(inParam.getString(PLAN_DAY)));
            sql.append(" AND DNRETRIEVALPLAN.BATCH_NO > ");
            sql.append(DBFormat.format(inParam.getString(BATCH_NO)));
            sql.append(")");
            sql.append(" OR DNRETRIEVALPLAN.PLAN_DAY > ");
            sql.append(DBFormat.format(inParam.getString(PLAN_DAY)));
            sql.append(")");
            sql.append(" AND DNRETRIEVALPLAN.CONSIGNOR_CODE = ");
            sql.append(DBFormat.format(inParam.getString(CONSIGNOR_CODE)));
            sql.append(" AND DNRETRIEVALPLAN.STATUS_FLAG != ");
            sql.append(DBFormat.format(RetrievalPlan.STATUS_FLAG_DELETE));
            sql.append(" GROUP BY");
            sql.append(" DNRETRIEVALPLAN.PLAN_DAY");
            sql.append(" ,DNRETRIEVALPLAN.BATCH_NO");
            sql.append(" ORDER BY");
            sql.append(" DNRETRIEVALPLAN.PLAN_DAY ASC");
            sql.append(" ,DNRETRIEVALPLAN.BATCH_NO ASC");

        }
        
        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // SQLの実行
            ddbHandler.execute(String.valueOf(sql));
            Entity[] entity = ddbHandler.getEntities(MAX_DISPLAY_PROGRESS, new RetrievalPlan());
            RetrievalPlan[] plans = new RetrievalPlan[entity.length];
            for (int i = 0; i < plans.length; i++)
            {
                plans[i] = new RetrievalPlan();
                plans[i].setValueMap(entity[i].getValueMap());
            }
            return plans;
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }
    }

    /**
     * 基準日を取得します。<BR>
     * 出庫予定情報を検索し、現在の作業日と等しいデータが存在すれば、その作業日を返します。<BR>
     * 無ければ、作業日に最も近い予定日を返します。<BR>
     * @param searchParam パラメータ
     * @return 基準日
     * @throws CommonException 全ての例外をスローします。
     */
    protected String standardDay(ScheduleParams searchParam)
            throws CommonException
    {
        // 現在の予定日を取得します。
        WarenaviSystemController wmsContorl = new WarenaviSystemController(getConnection(), getClass());
        String standardDay = wmsContorl.getWorkDay();

        // 出庫予定情報ハンドラ
        RetrievalPlanHandler retrievalPlanHndl = new RetrievalPlanHandler(getConnection());
        // 出庫予定情報検索キー
        RetrievalPlanSearchKey retrievalPlanShKy = new RetrievalPlanSearchKey();
        // 検索条件をセット
        retrievalPlanShKy.setConsignorCode(searchParam.getString(CONSIGNOR_CODE));
        retrievalPlanShKy.setPlanDayCollect();
        retrievalPlanShKy.setPlanDayGroup();
        retrievalPlanShKy.setPlanDayOrder(true);
        if (RetrievalInParameter.COLLECT_BATCHNO_UNIT.equals(searchParam.getString(COLLECT_FLAG)))
        {
            retrievalPlanShKy.setBatchNoCollect();
            retrievalPlanShKy.setBatchNoGroup();
            retrievalPlanShKy.setBatchNoOrder(true);
        }
        retrievalPlanShKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");

        // 検索結果を取得(必要データのみ取得(表示件数+1))
        RetrievalPlan[] rtvlPlan = (RetrievalPlan[])retrievalPlanHndl.find(retrievalPlanShKy, MAX_DISPLAY_PROGRESS + 1);
        if (rtvlPlan == null || rtvlPlan.length == 0)
        {
            return null;
        }
        else if (rtvlPlan.length <= MAX_DISPLAY_PROGRESS)
        {
            return rtvlPlan[0].getPlanDay();
        }
        else
        {
            // 基準日と同一の予定データがあれば、基準日を返します。
            retrievalPlanShKy.setPlanDay(standardDay);
            rtvlPlan = (RetrievalPlan[])retrievalPlanHndl.find(retrievalPlanShKy, 1);
            if (rtvlPlan.length > 0)
            {
                return standardDay;
            }

            // 基準日より小さい予定日を取得
            retrievalPlanShKy.clearKeys();
            retrievalPlanShKy.clearOrder();
            retrievalPlanShKy.setConsignorCode(searchParam.getString(CONSIGNOR_CODE));
            retrievalPlanShKy.setPlanDay(standardDay, "<");
            retrievalPlanShKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            retrievalPlanShKy.setPlanDayOrder(false);
            rtvlPlan = (RetrievalPlan[])retrievalPlanHndl.find(retrievalPlanShKy, 1);
            Date smallDay = null;
            if (rtvlPlan != null && rtvlPlan.length > 0)
            {
                smallDay = WmsFormatter.toDate(rtvlPlan[0].getPlanDay());
            }
            else
            {
                smallDay = WmsFormatter.toDate(standardDay);
            }

            // 基準日より大きい予定日を取得
            retrievalPlanShKy.clearKeys();
            retrievalPlanShKy.clearOrder();
            retrievalPlanShKy.setConsignorCode(searchParam.getString(CONSIGNOR_CODE));
            retrievalPlanShKy.setPlanDay(standardDay, ">");
            retrievalPlanShKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            retrievalPlanShKy.setPlanDayOrder(true);
            rtvlPlan = (RetrievalPlan[])retrievalPlanHndl.find(retrievalPlanShKy, 1);
            Date bigDay = null;
            if (rtvlPlan != null && rtvlPlan.length > 0)
            {
                bigDay = WmsFormatter.toDate(rtvlPlan[0].getPlanDay());
            }
            else
            {
                bigDay = WmsFormatter.toDate(standardDay);
            }

            // 基準日に近い方を返します。
            long smallDiference = WmsFormatter.toDate(standardDay).getTime() - smallDay.getTime();
            long bigDiference = bigDay.getTime() - WmsFormatter.toDate(standardDay).getTime();
            if (smallDiference != 0 && bigDiference == 0)
            {
                return WmsFormatter.toParamDate(smallDay);
            }
            else if (smallDiference == 0 && bigDiference != 0)
            {
                return WmsFormatter.toParamDate(bigDay);
            }
            else if (smallDiference < bigDiference)
            {
                return WmsFormatter.toParamDate(smallDay);
            }
            else
            {
                return WmsFormatter.toParamDate(bigDay);
            }
        }
    }


    /**
     * 基準日を基に最小のバッチNo.を取得します。
     * @param consignorCode 荷主コード
     * @param standardDay 基準日
     * @return 基準となる最小のバッチNo.
     * @throws CommonException 全ての例外をスローします。
     */
    protected String standardBatchNo(String consignorCode, String standardDay)
            throws CommonException
    {
        // 出庫予定情報ハンドラ
        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey shKy = new RetrievalPlanSearchKey();

        // 検索条件
        shKy.setConsignorCode(consignorCode);
        shKy.setPlanDay(standardDay);
        shKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        shKy.setBatchNoCollect();
        shKy.setPlanDayGroup();
        shKy.setBatchNoGroup();
        shKy.setBatchNoOrder(true);

        // 検索結果を取得
        RetrievalPlan[] entity = (RetrievalPlan[])handler.find(shKy, 1);
        if (entity == null || entity.length == 0)
        {
            return null;
        }

        // 取得データは最小値のため、index = 0
        String standardBatchNo = entity[0].getBatchNo();
        return standardBatchNo;
    }

    /**
     * 基準日の前後データ数を取得し前頁、次頁の制御を行います
     *
     * @param inParam 表示基準日
     * @return btnControl ボタン制御フラグ
     * @throws CommonException 全ての例外をスローします。
     */
    protected String buttonControl(ScheduleParams inParam)
            throws CommonException
    {
        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey lowShKy = new RetrievalPlanSearchKey();
        RetrievalPlanSearchKey highShKy = new RetrievalPlanSearchKey();

        int lowData = 0;
        int highData = 0;

        // 基準日より小さいデータ件数を取得します。
        if (RetrievalInParameter.COLLECT_BATCHNO_UNIT.equals(inParam.getString(COLLECT_FLAG)))
        {
            // 基準日且つ、指定バッチNo.より小さいデータ件数を取得します。
            lowShKy.clear();
            lowShKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            lowShKy.setPlanDay(inParam.getString(PLAN_DAY));
            lowShKy.setBatchNo(inParam.getString(BATCH_NO), "<");
            lowShKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            lowShKy.setPlanDayGroup();
            lowShKy.setBatchNoGroup();

            lowData = handler.count(lowShKy);
        }

        // 基準日より前の日付のデータ件数を取得します
        lowShKy.clear();
        lowShKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        lowShKy.setPlanDay(inParam.getString(PLAN_DAY), "<");
        lowShKy.setPlanDayGroup();
        if (RetrievalInParameter.COLLECT_BATCHNO_UNIT.equals(inParam.getString(COLLECT_FLAG)))
        {
            lowShKy.setBatchNoGroup();
        }
        lowShKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");

        lowData = lowData + handler.count(lowShKy);

        // 基準日より大きいデータ件数を取得します。
        if (RetrievalInParameter.COLLECT_BATCHNO_UNIT.equals(inParam.getString(COLLECT_FLAG)))
        {
            // 基準日且つ、指定バッチNo.より大きいデータを取得します。
            highShKy.clear();
            highShKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            highShKy.setPlanDay(inParam.getString(PLAN_DAY));
            highShKy.setBatchNo(inParam.getString(BATCH_NO), ">");
            highShKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
            highShKy.setPlanDayGroup();
            highShKy.setBatchNoGroup();

            highData = handler.count(highShKy);
        }

        // 基準日より未来の日付のデータ件数を取得します。
        highShKy.clear();
        highShKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        highShKy.setPlanDay(inParam.getString(PLAN_DAY), ">");
        highShKy.setPlanDayGroup();
        if (RetrievalInParameter.COLLECT_BATCHNO_UNIT.equals(inParam.getString(COLLECT_FLAG)))
        {
            highShKy.setBatchNoGroup();
        }
        highShKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");

        highData = highData + handler.count(highShKy);

        if (RetrievalInParameter.PROCESS_FLAG_VIEW.equals(inParam.getString(PROCESS_FLAG)))
        {
            // 前後データ件数が0件の場合
            if (lowData == 0 && highData < MAX_DISPLAY_PROGRESS)
            {
                // 前後ボタン使用不可
                return RetrievalOutParameter.BUTTON_CONTROL_FLAG_ALL_OFF;
            }
            // 前データが0件の場合
            if (lowData == 0 && highData >= MAX_DISPLAY_PROGRESS)
            {
                // 前頁ボタン使用不可
                return RetrievalOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF;
            }
            // 後データが表示可能件数より少ない
            if (lowData > 0 && highData < MAX_DISPLAY_PROGRESS)
            {
                // 次頁ボタン使用不可
                return RetrievalOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF;
            }
        }
        else if (RetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {
            if (lowData <= MAX_DISPLAY_PROGRESS)
            {
                // 前頁ボタン使用不可
                return RetrievalOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF;
            }
        }
        else if (RetrievalInParameter.PROCESS_FLAG_NEXT_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {
            if (highData <= MAX_DISPLAY_PROGRESS)
            {
                // 次頁ボタン使用不可
                return RetrievalOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF;
            }
        }
        return RetrievalOutParameter.BUTTON_CONTROL_FLAG_ALL_ON;
    }

    /**
     * 配列の順序を逆順にします<BR>
     * @param array 対象配列
     * @return 逆順にした配列
     */
    protected RetrievalPlan[] turnArray(RetrievalPlan[] array)
    {
        int index = 0;
        RetrievalPlan[] subArray = new RetrievalPlan[array.length];
        for (int i = subArray.length - 1; i >= 0; i--)
        {
            subArray[index++] = array[i];
        }
        return subArray;
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
