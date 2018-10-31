// $Id: PCTAllProgressSCH.java 6900 2010-01-25 11:21:11Z kumano $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.schedule.PCTAllProgressSCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.ParameterDefine;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;

/**
 * 作業進捗のスケジュール処理を行います。
 *
 * Designer : H.Okayama<BR>
 * Maker : H.Okayama<BR>
 * @version $Revision: 6900 $, $Date:: 2010-01-25 20:21:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class PCTAllProgressSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 進捗リストセルの最大表示件数
     */
    private static final int MAX_DISPLAY_PROGRESS = 4;

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
    public PCTAllProgressSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // ボタン制御
        String button = null;
        // データ取得配列
        PCTRetrievalOutParameter[] arrayParam = null;

        // 予定日が指定されていない場合
        if (StringUtil.isBlank(p.getString(PLAN_DATE)))
        {
            p.set(PLAN_DATE, getStandardPlanDay(p));
        }

        // 全予定日の場合
        if (PCTRetrievalInParameter.COLLECT_ALL_PLANDATE.equals(p.getString(COLLECT_FLAG)))
        {
            // ボタン制御フラグ(全て無効化)
            button = PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_ALL_OFF;

            // データの取得
            arrayParam = getAllPlanData(p);
        }
        // 予定日別の場合
        else if (PCTRetrievalInParameter.COLLECT_PLANDATE_UNIT.equals(p.getString(COLLECT_FLAG)))
        {
            // ボタン制御フラグの取得
            button = buttonControl(p);

            // データの取得
            arrayParam = setPlanUnitData(p);
        }
        // 予定日 + バッチSeqNo.別の場合
        else
        {
            // バッチSeqNo.が未入力の場合
            if (StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
            {
                // 基準日を基に最小のバッチSeqNo.を取得
                p.set(BATCH_SEQ_NO, getStandardBatchSeqNo(p));
            }

            // ボタン制御フラグの取得
            button = buttonControl(p);

            // データの取得
            arrayParam = setBatchUnitData(p);
        }
        // (6001013)表示しました。
        setMessage("6001013");

        // 取得したデータを並び替えて返却
        return getSortList(arrayParam, button, p);
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
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();

        // 取得項目
        // 荷主コード
        planSKey.setConsignorCodeCollect();
        // 荷主名称の最大値
        planSKey.setCollect(PCTRetPlan.CONSIGNOR_NAME, "MAX", PCTRetPlan.CONSIGNOR_NAME);

        // 検索条件
        // 状態フラグ(削除以外)
        planSKey.setStatusFlag(PCTRetPlan.STATUS_FLAG_DELETE, "!=");

        // 集約条件
        // 荷主コード
        planSKey.setConsignorCodeGroup();

        // 検索件数が1件の場合
        if (planHandler.count(planSKey) == 1)
        {
            // 返却パラメータを生成
            Params param = new Params();
            // 検索結果の取得
            PCTRetPlan ent = (PCTRetPlan)planHandler.findPrimary(planSKey);

            // 返却パラメータの設定
            // 荷主コード
            param.set(CONSIGNOR_CODE, ent.getConsignorCode());
            // 荷主名称
            param.set(CONSIGNOR_NAME, String.valueOf(ent.getValue(PCTRetPlan.CONSIGNOR_NAME, "")));

            // 設定した返却パラメータ返却
            return param;
        }
        // nullを返却
        return null;
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
     * 基準日を取得します。<BR>
     * 出庫予定情報を検索し、現在の作業日と等しいデータが存在すれば、その作業日を返します。<BR>
     * 無ければ、作業日に最も近い予定日を返します。<BR>
     * 
     * @param p 画面情報
     * @return 基準日
     * @throws CommonException 全ての例外をスローします。
     */
    protected String getStandardPlanDay(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());

        // システム定義情報コントローラ
        WarenaviSystemController wmsContorl = new WarenaviSystemController(getConnection(), getClass());

        // システム定義情報の予定日を取得
        String standardPlanDay = wmsContorl.getWorkDay();

        // 基準日前予定日
        Date lowPlanDay = null;
        // 基準日後予定日
        Date highPlanDay = null;

        // 基準予定日を取得
        PCTRetPlan[] planEnt = getPlanDayInfo(p);

        // データが取得できなかった場合
        if (ArrayUtil.isEmpty(planEnt))
        {
            return null;
        }
        // 最大表示件数以下の場合
        else if (planEnt.length <= MAX_DISPLAY_PROGRESS)
        {
            return planEnt[0].getPlanDay();
        }
        else
        {
            // 基準日と同一のデータがあれば、システム定義情報の予定日を返却
            planSKey.setPlanDay(standardPlanDay);
            planEnt = (PCTRetPlan[])planHandler.find(planSKey, 1);
            if (planEnt.length > 0)
            {
                // システム定義情報を予定日を返却
                return standardPlanDay;
            }

            // 基準日前予定日を取得
            lowPlanDay = getHighLowPlanDay(p, standardPlanDay, "<");

            // 基準日後予定日を取得
            highPlanDay = getHighLowPlanDay(p, standardPlanDay, ">");

            // 基準日から基準日前予定日までの差を算出
            long lowDiference = WmsFormatter.toDate(standardPlanDay).getTime() - lowPlanDay.getTime();
            // 基準日から基準日後予定日までの差を算出
            long HighDiference = highPlanDay.getTime() - WmsFormatter.toDate(standardPlanDay).getTime();

            // 基準日前予定日の差が近い場合
            if (lowDiference != 0 && HighDiference == 0)
            {
                return WmsFormatter.toParamDate(lowPlanDay);
            }
            // 基準日後予定日の差が近い場合
            else if (lowDiference == 0 && HighDiference != 0)
            {
                return WmsFormatter.toParamDate(highPlanDay);
            }
            // 基準日後予定日の差が遠い場合
            else if (lowDiference < HighDiference)
            {
                return WmsFormatter.toParamDate(lowPlanDay);
            }
            // 上記以外の場合
            else
            {
                return WmsFormatter.toParamDate(highPlanDay);
            }
        }
    }

    /**
     * バッチSeqNo.を取得します。<BR>
     * 
     * @param p 画面情報
     * @return バッチSeqNo.
     * @throws CommonException 全ての例外をスローします。
     */
    protected String getStandardBatchSeqNo(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());

        // 取得項目
        // バッチSeqNo.
        planSKey.setBatchSeqNoCollect();

        // 検索条件
        planSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 予定エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選ばれている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                planSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        planSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        planSKey.setPlanDay(p.getString(PLAN_DATE));

        // 集約条件
        // 予定日
        planSKey.setPlanDayGroup();
        // バッチSeqNo.
        planSKey.setBatchSeqNoGroup();

        // 表示順
        // バッチSeqNo.
        planSKey.setBatchSeqNoOrder(true);

        // 検索結果を取得
        PCTRetPlan[] entity = (PCTRetPlan[])planHandler.find(planSKey);
        if (ArrayUtil.isEmpty(entity))
        {
            return null;
        }

        // 取得データは最小値のため、index = 0
        String standardBatchSeqNo = entity[0].getBatchSeqNo();
        return standardBatchSeqNo;
    }

    /**
     * 基準予定日を取得します。
     * 
     * @param p 画面情報
     * @return PCT出庫予定情報
     * @throws CommonException 全ての例外をスローします。
     */
    protected PCTRetPlan[] getPlanDayInfo(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());

        // 取得項目
        // 予定日
        planSKey.setPlanDayCollect();
        // 予定エリアNo.
        planSKey.setPlanAreaNoCollect();

        // 検索条件
        // 荷主コード
        planSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が指定された場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                planSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // 状態フラグ
        planSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        // 集約条件が予定日+バッチSeqNo別の場合、バッチSeqNoもグループ化
        if (PCTRetrievalInParameter.COLLECT_BATCHNO_UNIT.equals(p.getString(COLLECT_FLAG)))
        {
            // 取得項目
            // バッチSeqNo.
            planSKey.setBatchSeqNoCollect();

            // 集約条件
            // バッチSeqNo.
            planSKey.setBatchSeqNoGroup();

            // 表示順
            // バッチSeqNo.
            planSKey.setBatchSeqNoOrder(true);
        }

        // 集約条件
        // 予定日
        planSKey.setPlanDayGroup();
        // 予定エリアNo.
        planSKey.setPlanAreaNoGroup();

        // 表示順
        // 予定日
        planSKey.setPlanDayOrder(true);
        // 予定エリアNo.
        planSKey.setPlanAreaNoOrder(true);

        // 検索結果を取得(必要データのみ取得(表示件数+1))
        return (PCTRetPlan[])planHandler.find(planSKey, MAX_DISPLAY_PROGRESS + 1);
    }

    /**
     * 基準日を取得します。(前後共通)
     * 
     * @param p 画面情報
     * @param standardPlanDay 基準予定日
     * @param operator 演算子
     * @return 基準日
     * @throws CommonException 全ての例外をスローします。
     */
    protected Date getHighLowPlanDay(ScheduleParams p, String standardPlanDay, String operator)
            throws CommonException
    {
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());

        // 検索条件
        // 荷主コード
        planSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                planSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // 状態フラグ
        planSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        // 予定日
        planSKey.setPlanDay(standardPlanDay, operator);

        // 表示順
        // 予定日
        planSKey.setPlanDayOrder(true);

        // 検索結果を取得
        PCTRetPlan[] rtvlPlan = (PCTRetPlan[])planHandler.find(planSKey, 1);
        if (ArrayUtil.isEmpty(rtvlPlan))
        {
            return WmsFormatter.toDate(standardPlanDay);
        }
        else
        {
            return WmsFormatter.toDate(rtvlPlan[0].getPlanDay());
        }
    }

    /**
     * 基準日の前後データ数を取得し前頁、次頁の制御を行います
     * 
     * @param inParam 表示基準日
     * @return btnControl ボタン制御フラグ
     * @throws CommonException 全ての例外をスローします。
     */
    protected String buttonControl(ScheduleParams p)
            throws CommonException
    {
        // 前頁想定件数
        int lowData = getHighLowPlanInfo(p, "<");;
        // 次頁想定件数
        int highData = getHighLowPlanInfo(p, ">");

        // 表示ボタンの場合
        if (PCTRetrievalInParameter.PROCESS_FLAG_VIEW.equals(p.getString(PROCESS_FLAG)))
        {
            // 前後データ件数が0件の場合
            if (lowData == 0 && highData < MAX_DISPLAY_PROGRESS)
            {
                // 前頁、次頁ボタン無効化
                return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_ALL_OFF;
            }
            // 前データが0件の場合
            if (lowData == 0 && highData >= MAX_DISPLAY_PROGRESS)
            {
                // 前頁ボタン無効化
                return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF;
            }
            // 後データが表示可能件数より少ない
            if (lowData > 0 && highData < MAX_DISPLAY_PROGRESS)
            {
                // 次頁ボタン無効化
                return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF;
            }
        }
        // 前頁ボタンの場合
        else if (PCTRetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            if (lowData <= MAX_DISPLAY_PROGRESS)
            {
                // 前頁ボタン無効化
                return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF;
            }
        }
        // 次頁ボタンの場合
        else if (PCTRetrievalInParameter.PROCESS_FLAG_NEXT_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            if (highData <= MAX_DISPLAY_PROGRESS)
            {
                // 次頁ボタン無効化
                return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF;
            }
        }
        // 前頁、次頁ボタン有効化
        return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_ALL_ON;
    }

    /**
     * 基準日前後のデータを取得します。
     * 
     * @param p 画面情報
     * @param operator 演算子
     * @retrun int 検索結果件数
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected int getHighLowPlanInfo(ScheduleParams p, String operator)
            throws CommonException
    {
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey lowPlanSKey = new PCTRetPlanSearchKey();
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());

        // データ件数
        int dataCount = 0;

        // 予定日 + バッチSeqNo.別の場合
        if (PCTRetrievalInParameter.COLLECT_BATCHNO_UNIT.equals(p.getString(COLLECT_FLAG)))
        {
            // 検索条件
            // 荷主コード
            lowPlanSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
            // エリア
            if (!StringUtil.isBlank(p.getString(AREA_NO)))
            {
                // 全エリア以外が選択されている場合
                if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
                {
                    lowPlanSKey.setPlanAreaNo(p.getString(AREA_NO));
                }
            }
            // 予定日
            lowPlanSKey.setPlanDay(p.getString(PLAN_DATE), "=");
            // バッチSeqNo.
            lowPlanSKey.setBatchSeqNo(p.getString(BATCH_SEQ_NO), operator);
            // 状態フラグ
            lowPlanSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

            // 集約条件
            // 予定日
            lowPlanSKey.setPlanDayGroup();
            // バッチSeqNo.
            lowPlanSKey.setBatchSeqNoGroup();

            // 検索実行
            dataCount = planHandler.count(lowPlanSKey);


            // 検索キーのクリア
            lowPlanSKey.clearKeys();
            // 検索条件
            // 荷主コード
            lowPlanSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
            // エリア
            if (!StringUtil.isBlank(p.getString(AREA_NO)))
            {
                // 全エリア以外が選択されている場合
                if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
                {
                    lowPlanSKey.setPlanAreaNo(p.getString(AREA_NO));
                }
            }
            // 予定日
            lowPlanSKey.setPlanDay(p.getString(PLAN_DATE), operator);
            // 状態フラグ
            lowPlanSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

            // SQL1とSQL2の件数を返却
            return dataCount + planHandler.count(lowPlanSKey);
        }
        else
        {
            // 検索条件
            // 荷主コード
            lowPlanSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
            // エリア
            if (!StringUtil.isBlank(p.getString(AREA_NO)))
            {
                // 全エリア以外が選択されている場合
                if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
                {
                    lowPlanSKey.setPlanAreaNo(p.getString(AREA_NO));
                }
            }
            // 予定日
            lowPlanSKey.setPlanDay(p.getString(PLAN_DATE), operator);
            // 状態フラグ
            lowPlanSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

            // 集約条件
            // 予定日
            lowPlanSKey.setPlanDayGroup();

            // 検索結果件数を返却
            return planHandler.count(lowPlanSKey);
        }
    }

    /**
     * 全予定日データを取得します。
     * 
     * @param p 検索パラメータ
     * @return 表示データ
     * @throws CommonException 全ての例外をスローします。
     */
    protected PCTRetrievalOutParameter[] getAllPlanData(ScheduleParams p)
            throws CommonException
    {
        // 返却パラメータ配列
        PCTRetrievalOutParameter[] outParam = new PCTRetrievalOutParameter[1];
        // 出荷先毎のWHERE句保持
        StringBuffer whereSql = new StringBuffer();

        // 情報取得エンティティ
        Entity[] countEntity = null;
        // 作業中フラグ
        boolean workingFlg = false;

        // PCT出庫予定情報の件数が0件の場合
        if (getCountPlanData(p) == 0)
        {
            return new PCTRetrievalOutParameter[0];
        }

        // パラメータの生成
        outParam[0] = new PCTRetrievalOutParameter();

        // 情報抽出SQLのWHERE句を生成
        whereSql = createWhereSql(p, null, null);

        // オーダー件数の取得
        countEntity = getOrderCountEntity(whereSql);
        int planOrderCnt = countEntity[0].getBigDecimal(new FieldName("", "PLAN_ORDER_COUNT")).intValue();
        int resultOrderCnt = countEntity[0].getBigDecimal(new FieldName("", "RESULT_ORDER_COUNT")).intValue();

        // 箱数の取得
        int resultBoxCount = getBoxCount(p, null, null);

        // 行数・ロット数の取得
        countEntity = null;
        countEntity = getLineCountEntity(whereSql);
        int planLineCnt = countEntity[0].getBigDecimal(new FieldName("", "PLAN_LINE_COUNT")).intValue();
        int resultLineCnt = countEntity[0].getBigDecimal(new FieldName("", "RESULT_LINE_COUNT")).intValue();
        int planQty = countEntity[0].getBigDecimal(new FieldName("", "PLAN_QTY")).intValue();
        int resultQty = countEntity[0].getBigDecimal(new FieldName("", "RESULT_QTY")).intValue();
        int shortageQty = countEntity[0].getBigDecimal(new FieldName("", "SHORTAGE_QTY")).intValue();

        // カート台数の取得
        int cartCount = getCartCount(p, null, null);

        // 終了予測時間の取得
        double endPlanTime = 0;
        // 作業中の場合
        if (getWorkingCount(p, null, null) == 0)
        {
            // 作業中フラグを成立
            workingFlg = true;
        }

        // ロット基準の場合
        if (PCTRetrievalInParameter.ENDPLANTIME_LOT_STANDARD.equals(p.getString(END_PLAN_TIME_FLAG)))
        {
            // 予定数
            outParam[0].setPlanQty(planQty);
            // 実績数
            outParam[0].setResultQty((resultQty + shortageQty));

            // 実績数 + 欠品数 = 予定数の場合、完了
            if (((resultQty + shortageQty == planQty) || resultQty + shortageQty == 0) && !workingFlg)
            {
                // 終了予測時間
                endPlanTime = 0;
            }
            else
            {
                // 終了予測時間
                endPlanTime = getEndPlanTimeForLot(whereSql);
            }
        }
        // オーダー基準の場合
        else if (PCTRetrievalInParameter.ENDPLANTIME_ORDER_STANDARD.equals(p.getString(END_PLAN_TIME_FLAG)))
        {
            // 予定数
            outParam[0].setPlanQty(planOrderCnt);
            // 実績数
            outParam[0].setResultQty(resultOrderCnt);

            // 予定オーダー件数 = 実績オーダー件数の場合、完了
            if (((planOrderCnt == resultOrderCnt) || resultOrderCnt == 0) && !workingFlg)
            {
                // 終了予測時間
                endPlanTime = 0;
            }
            else
            {
                // 終了予測時間
                endPlanTime = getEndPlanTimeForOrder(whereSql);
            }
        }
        // 行基準の場合
        else
        {
            // 予定数
            outParam[0].setPlanQty(planLineCnt);
            // 実績数
            outParam[0].setResultQty(resultLineCnt);

            // 実績行数 = 予定行数の場合、完了
            if (((planLineCnt == resultLineCnt) || resultLineCnt == 0) && !workingFlg)
            {
                // 終了予測時間
                endPlanTime = 0;
            }
            else
            {
                // 終了予測時間
                endPlanTime = getEndPlanTimeForLine(whereSql);
            }
        }
        // 0以下の場合は0を設定
        if (endPlanTime < 0)
        {
            endPlanTime = 0;
        }

        // 作業の状態チェック
        if (getWorkCompleteCount(p, null, null) == 0)
        {
            endPlanTime = -1;
        }

        // パラメータの設定
        // 予定オーダー数
        outParam[0].setPlanOrderCnt(planOrderCnt);
        // 完了オーダー数
        outParam[0].setResultOrderCnt(resultOrderCnt);
        // 箱数
        outParam[0].setBoxCnt(resultBoxCount);
        // 予定行数
        outParam[0].setPlanLineCnt(planLineCnt);
        // 実績行数
        outParam[0].setResultLineCnt(resultLineCnt);
        // 予定ロット数
        outParam[0].setPlanLotCnt(planQty);
        // 実績ロット数
        outParam[0].setResultLotCnt((resultQty + shortageQty));
        // 欠品ロット数
        outParam[0].setShotageQty(shortageQty);
        // カート台数
        outParam[0].setCartCnt(cartCount);
        // 終了予測時間
        outParam[0].setEndPlanTime(endPlanTime);

        // 生成したパラメータを返却
        return outParam;
    }

    /**
     * 予定日別データを取得します。<BR>
     * 
     * @param inParam 検索パラメータ
     * @return 表示データ
     * @throws CommonException 全ての例外をスローします。
     */
    protected PCTRetrievalOutParameter[] setPlanUnitData(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫予定情報エンティティ
        PCTRetPlan[] planEnt = null;
        // 出荷先毎のWHERE句保持
        StringBuffer whereSql = new StringBuffer();

        // 情報取得エンティティ
        Entity[] countEntity = null;
        // 作業中フラグ
        boolean workingFlg = false;

        // 予定日毎のデータを取得
        planEnt = getSeparatePlan(p);
        if (ArrayUtil.isEmpty(planEnt))
        {
            return new PCTRetrievalOutParameter[0];
        }

        // 返却用パラメータ配列
        PCTRetrievalOutParameter[] outParam = new PCTRetrievalOutParameter[planEnt.length];

        // 予定日毎に出力データを取得します。
        for (int i = 0; i < planEnt.length; i++)
        {
            // パラメータの生成
            outParam[i] = new PCTRetrievalOutParameter();

            // 情報抽出SQLのWHERE句を生成
            whereSql = createWhereSql(p, planEnt[i].getPlanDay(), null);

            // オーダー件数の取得
            countEntity = getOrderCountEntity(whereSql);
            int planOrderCnt = countEntity[0].getBigDecimal(new FieldName("", "PLAN_ORDER_COUNT")).intValue();
            int resultOrderCnt = countEntity[0].getBigDecimal(new FieldName("", "RESULT_ORDER_COUNT")).intValue();

            // 箱数の取得
            int resultBoxCount = getBoxCount(p, planEnt[i].getPlanDay(), null);

            // 行数・ロット数の取得
            countEntity = null;
            countEntity = getLineCountEntity(whereSql);
            int planLineCnt = countEntity[0].getBigDecimal(new FieldName("", "PLAN_LINE_COUNT")).intValue();
            int resultLineCnt = countEntity[0].getBigDecimal(new FieldName("", "RESULT_LINE_COUNT")).intValue();
            int planQty = countEntity[0].getBigDecimal(new FieldName("", "PLAN_QTY")).intValue();
            int resultQty = countEntity[0].getBigDecimal(new FieldName("", "RESULT_QTY")).intValue();
            int shortageQty = countEntity[0].getBigDecimal(new FieldName("", "SHORTAGE_QTY")).intValue();

            // カート台数の取得
            int cartCount = getCartCount(p, planEnt[i].getPlanDay(), null);

            // 終了予測時間の取得
            double endPlanTime = 0;
            // 作業中の場合
            if (getWorkingCount(p, planEnt[i].getPlanDay(), null) == 0)
            {
                // 作業中フラグを成立
                workingFlg = true;
            }

            // ロット基準の場合
            if (PCTRetrievalInParameter.ENDPLANTIME_LOT_STANDARD.equals(p.getString(END_PLAN_TIME_FLAG)))
            {
                // 予定数
                outParam[i].setPlanQty(planQty);
                // 実績数
                outParam[i].setResultQty((resultQty + shortageQty));

                // 実績数 + 欠品数 = 予定数の場合、完了
                if (((resultQty + shortageQty == planQty) || resultQty + shortageQty == 0) && !workingFlg)
                {
                    // 終了予測時間
                    endPlanTime = 0;
                }
                else
                {
                    // 終了予測時間
                    endPlanTime = getEndPlanTimeForLot(whereSql);
                }
            }
            // オーダー基準の場合
            else if (PCTRetrievalInParameter.ENDPLANTIME_ORDER_STANDARD.equals(p.getString(END_PLAN_TIME_FLAG)))
            {
                // 予定数
                outParam[i].setPlanQty(planOrderCnt);
                // 実績数
                outParam[i].setResultQty(resultOrderCnt);

                // 予定オーダー件数 = 実績オーダー件数の場合、完了
                if (((planOrderCnt == resultOrderCnt) || resultOrderCnt == 0) && !workingFlg)
                {
                    // 終了予測時間
                    endPlanTime = 0;
                }
                else
                {
                    // 終了予測時間
                    endPlanTime = getEndPlanTimeForOrder(whereSql);
                }
            }
            // 行基準の場合
            else
            {
                // 予定数
                outParam[i].setPlanQty(planLineCnt);
                // 実績数
                outParam[i].setResultQty(resultLineCnt);

                // 実績行数 = 予定行数の場合、完了
                if (((planLineCnt == resultLineCnt) || resultLineCnt == 0) && !workingFlg)
                {
                    // 終了予測時間
                    endPlanTime = 0;
                }
                else
                {
                    // 終了予測時間
                    endPlanTime = getEndPlanTimeForLine(whereSql);
                }
            }
            // 0以下の場合は0を設定
            if (endPlanTime < 0)
            {
                endPlanTime = 0;
            }

            // 作業の状態チェック
            if (getWorkCompleteCount(p, planEnt[i].getPlanDay(), null) == 0)
            {
                endPlanTime = -1;
            }

            // パラメータの設定
            // 予定オーダー数
            outParam[i].setPlanOrderCnt(planOrderCnt);
            // 完了オーダー数
            outParam[i].setResultOrderCnt(resultOrderCnt);
            // 箱数
            outParam[i].setBoxCnt(resultBoxCount);
            // 予定行数
            outParam[i].setPlanLineCnt(planLineCnt);
            // 実績行数
            outParam[i].setResultLineCnt(resultLineCnt);
            // 予定ロット数
            outParam[i].setPlanLotCnt(planQty);
            // 実績ロット数
            outParam[i].setResultLotCnt((resultQty + shortageQty));
            // 欠品ロット数
            outParam[i].setShotageQty(shortageQty);
            // カート台数
            outParam[i].setCartCnt(cartCount);
            // 終了予測時間
            outParam[i].setEndPlanTime(endPlanTime);
            // 予定日
            outParam[i].setPlanDay(planEnt[i].getPlanDay());
        }
        // 生成したパラメータ配列を返却
        return outParam;
    }

    /**
     * 予定日＋バッチSeqNo.別データを取得します。<BR>
     * 
     * @param p 検索パラメータ
     * @return 表示データ
     * @throws CommonException 全ての例外をスローします。
     */
    protected PCTRetrievalOutParameter[] setBatchUnitData(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫予定情報エンティティ
        PCTRetPlan[] planEnt = null;
        // 出荷先毎のWHERE句保持
        StringBuffer whereSql = new StringBuffer();

        // 情報取得エンティティ
        Entity[] countEntity = null;
        // 作業中フラグ
        boolean workingFlg = false;

        // 予定日毎のデータを取得
        planEnt = getSeparatePlanBatchSeqNo(p);
        if (ArrayUtil.isEmpty(planEnt))
        {
            return new PCTRetrievalOutParameter[0];
        }

        // 返却用パラメータ配列
        PCTRetrievalOutParameter[] outParam = new PCTRetrievalOutParameter[planEnt.length];

        // 予定日毎に出力データを取得します。
        for (int i = 0; i < planEnt.length; i++)
        {
            // パラメータの生成
            outParam[i] = new PCTRetrievalOutParameter();

            // 情報抽出SQLのWHERE句を生成
            whereSql = createWhereSql(p, planEnt[i].getPlanDay(), planEnt[i].getBatchSeqNo());

            // オーダー件数の取得
            countEntity = getOrderCountEntity(whereSql);
            int planOrderCnt = countEntity[0].getBigDecimal(new FieldName("", "PLAN_ORDER_COUNT")).intValue();
            int resultOrderCnt = countEntity[0].getBigDecimal(new FieldName("", "RESULT_ORDER_COUNT")).intValue();

            // 箱数の取得
            int resultBoxCount = getBoxCount(p, planEnt[i].getPlanDay(), planEnt[i].getBatchSeqNo());

            // 行数・ロット数の取得
            countEntity = null;
            countEntity = getLineCountEntity(whereSql);
            int planLineCnt = countEntity[0].getBigDecimal(new FieldName("", "PLAN_LINE_COUNT")).intValue();
            int resultLineCnt = countEntity[0].getBigDecimal(new FieldName("", "RESULT_LINE_COUNT")).intValue();
            int planQty = countEntity[0].getBigDecimal(new FieldName("", "PLAN_QTY")).intValue();
            int resultQty = countEntity[0].getBigDecimal(new FieldName("", "RESULT_QTY")).intValue();
            int shortageQty = countEntity[0].getBigDecimal(new FieldName("", "SHORTAGE_QTY")).intValue();

            // カート台数の取得
            int cartCount = getCartCount(p, planEnt[i].getPlanDay(), planEnt[i].getBatchSeqNo());

            // 終了予測時間の取得
            double endPlanTime = 0;
            // 作業中の場合
            if (getWorkingCount(p, planEnt[i].getPlanDay(), planEnt[i].getBatchSeqNo()) == 0)
            {
                // 作業中フラグを成立
                workingFlg = true;
            }

            // ロット基準の場合
            if (PCTRetrievalInParameter.ENDPLANTIME_LOT_STANDARD.equals(p.getString(END_PLAN_TIME_FLAG)))
            {
                // 予定数
                outParam[i].setPlanQty(planQty);
                // 実績数
                outParam[i].setResultQty((resultQty + shortageQty));

                // 実績数 + 欠品数 = 予定数の場合、完了
                if (((resultQty + shortageQty == planQty) || resultQty + shortageQty == 0) && !workingFlg)
                {
                    // 終了予測時間
                    endPlanTime = 0;
                }
                else
                {
                    // 終了予測時間
                    endPlanTime = getEndPlanTimeForLot(whereSql);
                }
            }
            // オーダー基準の場合
            else if (PCTRetrievalInParameter.ENDPLANTIME_ORDER_STANDARD.equals(p.getString(END_PLAN_TIME_FLAG)))
            {
                // 予定数
                outParam[i].setPlanQty(planOrderCnt);
                // 実績数
                outParam[i].setResultQty(resultOrderCnt);

                // 予定オーダー件数 = 実績オーダー件数の場合、完了
                if (((planOrderCnt == resultOrderCnt) || resultOrderCnt == 0) && !workingFlg)
                {
                    // 終了予測時間
                    endPlanTime = 0;
                }
                else
                {
                    // 終了予測時間
                    endPlanTime = getEndPlanTimeForOrder(whereSql);
                }
            }
            // 行基準の場合
            else
            {
                // 予定数
                outParam[i].setPlanQty(planLineCnt);
                // 実績数
                outParam[i].setResultQty(resultLineCnt);

                // 実績行数 = 予定行数の場合、完了
                if (((planLineCnt == resultLineCnt) || resultLineCnt == 0) && !workingFlg)
                {
                    // 終了予測時間
                    endPlanTime = 0;
                }
                else
                {
                    // 終了予測時間
                    endPlanTime = getEndPlanTimeForLine(whereSql);
                }
            }
            // 0以下の場合は0を設定
            if (endPlanTime < 0)
            {
                endPlanTime = 0;
            }

            // 作業の状態チェック
            if (getWorkCompleteCount(p, planEnt[i].getPlanDay(), planEnt[i].getBatchSeqNo()) == 0)
            {
                endPlanTime = -1;
            }

            // パラメータの設定
            // 予定オーダー数
            outParam[i].setPlanOrderCnt(planOrderCnt);
            // 完了オーダー数
            outParam[i].setResultOrderCnt(resultOrderCnt);
            // 箱数
            outParam[i].setBoxCnt(resultBoxCount);
            // 予定行数
            outParam[i].setPlanLineCnt(planLineCnt);
            // 実績行数
            outParam[i].setResultLineCnt(resultLineCnt);
            // 予定ロット数
            outParam[i].setPlanLotCnt(planQty);
            // 実績ロット数
            outParam[i].setResultLotCnt((resultQty + shortageQty));
            // 欠品ロット数
            outParam[i].setShotageQty(shortageQty);
            // カート台数
            outParam[i].setCartCnt(cartCount);
            // 終了予測時間
            outParam[i].setEndPlanTime(endPlanTime);
            // 予定日
            outParam[i].setPlanDay(planEnt[i].getPlanDay());
            // バッチNo.
            outParam[i].setBatchNo(planEnt[i].getBatchNo());
            // バッチSeqNo.
            outParam[i].setBatchSeqNo(planEnt[i].getBatchSeqNo());
        }
        // 生成したパラメータ配列を返却
        return outParam;
    }

    /**
     * 予定日件数を取得します。
     * 
     * @param p 画面情報
     * @param 予定日件数
     * @throws CommonException 全ての例外をスローします。
     */
    protected int getCountPlanData(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());

        // 検索条件
        // 荷主コード
        planSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                planSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // 状態フラグ
        planSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

        // 検索結果の件数を返却
        return planHandler.count(planSKey);
    }

    /**
     * パラメータ毎のWHERE句を生成します。
     * 
     * @param p 画面情報
     * @param planDay 予定日
     * @param batchSeqNo バッチSeqNo.
     * @return SQLのWHERE句
     * @throws CommonException 全ての例外をスローします。
     */
    protected StringBuffer createWhereSql(ScheduleParams p, String planDay, String batchSeqNo)
            throws CommonException
    {
        // 返却文字列の生成
        StringBuffer where = new StringBuffer();

        // 検索条件
        where.append(" WHERE ");
        where.append("  DNPCTRETPLAN.CONSIGNOR_CODE = ").append(DBFormat.format(p.getString(CONSIGNOR_CODE))).append(
                " ");
        where.append("  AND DNPCTRETPLAN.STATUS_FLAG != ");
        where.append(DBFormat.format(PCTRetrievalInParameter.STATUS_FLAG_DELETE)).append(" ");
        // 予定エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選ばれている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                where.append("  AND DNPCTRETPLAN.PLAN_AREA_NO = ").append(DBFormat.format(p.getString(AREA_NO))).append(
                        " ");
            }
        }
        // 予定日が指定された場合
        if (!StringUtil.isBlank(planDay))
        {
            where.append("   AND DNPCTRETPLAN.PLAN_DAY = ").append(DBFormat.format(planDay)).append(" ");
        }
        // バッチSeqNo.が指定された場合
        if (!StringUtil.isBlank(batchSeqNo))
        {
            where.append("   AND DNPCTRETPLAN.BATCH_SEQ_NO = ").append(DBFormat.format(batchSeqNo)).append(" ");
        }
        // 生成したWHERE句を返却
        return where;
    }

    /**
     * オーダー件数情報を取得します。
     * 
     * @param where WHERE句
     * @return Entity[] オーダー件数情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected Entity[] getOrderCountEntity(StringBuffer where)
            throws CommonException
    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;
        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // 情報取得SQL
            StringBuffer sql = new StringBuffer();

            // SQLの生成
            // 取得項目
            sql.append("SELECT ");
            sql.append(" COUNT(DNPCTRETPLAN.PLAN_ORDER_NO) PLAN_ORDER_COUNT ");
            sql.append(" ,SUM(DNPCTRETPLAN.RESULT_ORDER_COUNT) RESULT_ORDER_COUNT ");
            sql.append("FROM ");
            sql.append(" (SELECT ");
            sql.append("   DNPCTRETPLAN.PLAN_ORDER_NO ");
            sql.append("   ,CASE MIN(DNPCTRETPLAN.STATUS_FLAG) ");
            sql.append("     WHEN ").append(DBFormat.format(PCTRetrievalInParameter.BATCHSTATUS_FLAG_COMPLETION)).append(
                    " ");
            sql.append("     THEN 1 ");
            sql.append("   ELSE 0 END RESULT_ORDER_COUNT ");
            sql.append("  FROM ");
            sql.append("   DNPCTRETPLAN ").append(where);
            sql.append("  GROUP BY DNPCTRETPLAN.PLAN_ORDER_NO ");
            sql.append(" ) DNPCTRETPLAN ");

            // SQLの実行
            ddbHandler.execute(String.valueOf(sql));
            return ddbHandler.getEntities(1, new PCTRetPlan());
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
     * 箱数を取得します。
     * 
     * @param p 画面情報
     * @param planDay 予定日
     * @return int 箱数
     * @throws CommonException 全ての例外をスローします。
     */
    protected int getBoxCount(ScheduleParams p, String planDay, String batchSeqNo)
            throws CommonException
    {
        // PCT出庫作業情報検索キー
        PCTRetWorkInfoSearchKey workSKey = new PCTRetWorkInfoSearchKey();
        // PCT出庫作業情報ハンドラ
        PCTRetWorkInfoHandler workHandler = new PCTRetWorkInfoHandler(getConnection());

        // 取得条件
        // 実績オーダーNo.
        workSKey.setResultOrderNoCollect();

        // 検索条件
        // 荷主コード
        workSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                workSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // 予定日が指定されていた場合
        if (!StringUtil.isBlank(planDay))
        {
            workSKey.setPlanDay(planDay);
        }
        // バッチSeqNo.が指定されていた場合
        if (!StringUtil.isBlank(batchSeqNo))
        {
            workSKey.setBatchSeqNo(batchSeqNo);
        }
        // 状態フラグ
        workSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION, "=", "(", "", false);
        workSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION, "=", "", ")", false);

        // 集約条件
        // 実績オーダーNo.
        workSKey.setResultOrderNoGroup();

        // 検索結果の件数を返却
        return workHandler.count(workSKey);
    }

    /**
     * 行数・ロット数を取得します。
     * 
     * @param where WHERE句
     * @return Entity[] 検索結果
     * @throws CommonException 全ての例外をスローします。
     */
    protected Entity[] getLineCountEntity(StringBuffer where)
            throws CommonException
    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;
        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // 情報取得SQL
            StringBuffer sql = new StringBuffer();

            // SQLの生成
            // 取得項目
            sql.append("SELECT ");
            sql.append(" COUNT(DNPCTRETPLAN.PLAN_UKEY) PLAN_LINE_COUNT ");
            sql.append(" ,SUM(DNPCTRETPLAN.PLAN_QTY) PLAN_QTY ");
            sql.append(" ,SUM(DNPCTRETPLAN.RESULT_QTY) RESULT_QTY ");
            sql.append(" ,SUM(DNPCTRETPLAN.SHORTAGE_QTY) SHORTAGE_QTY ");
            sql.append(" ,SUM(DNPCTRETPLAN.RESULT_LINE_COUNT) RESULT_LINE_COUNT ");
            // 対象テーブル
            sql.append("FROM ");
            sql.append(" (SELECT ");
            sql.append("   DNPCTRETPLAN.* ");
            sql.append("   ,CASE DNPCTRETPLAN.STATUS_FLAG ");
            sql.append("     WHEN ").append(DBFormat.format(PCTRetrievalInParameter.BATCHSTATUS_FLAG_COMPLETION)).append(
                    " ");
            sql.append("     THEN 1 ");
            sql.append("   ELSE 0 END RESULT_LINE_COUNT ");
            sql.append("  FROM ");
            sql.append("   DNPCTRETPLAN ").append(where);
            sql.append(" ) DNPCTRETPLAN ");

            // SQLの実行
            ddbHandler.execute(String.valueOf(sql));
            return ddbHandler.getEntities(1, new PCTRetPlan());
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
     * カート台数を取得します。
     * 
     * @param p 画面情報
     * @param planDay 予定日
     * @param batchSeqNo バッチSeqNo.
     * @return int 箱数
     * @throws CommonException 全ての例外をスローします。
     */
    protected int getCartCount(ScheduleParams p, String planDay, String batchSeqNo)
            throws CommonException
    {
        // RFT管理情報検索キー
        RftSearchKey rftSKey = new RftSearchKey();
        // RFT管理情報ハンドラ
        RftHandler rftHandler = new RftHandler(getConnection());

        // 検索条件
        // 荷主コード
        rftSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                rftSKey.setAreaNo(p.getString(AREA_NO));
            }
        }
        // 状態フラグ
        rftSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKING);
        // 端末区分
        rftSKey.setTerminalType(ParameterDefine.TERMINAL_TYPE_PCART);

        // 全予定日の場合
        if (PCTRetrievalInParameter.COLLECT_ALL_PLANDATE.equals(p.getString(COLLECT_FLAG)))
        {
            // バッチSeqNo.
            if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
            {
                rftSKey.setBatchSeqNo(p.getString(BATCH_SEQ_NO));
            }
        }
        // 予定日別の場合
        else if (PCTRetrievalInParameter.COLLECT_PLANDATE_UNIT.equals(p.getString(COLLECT_FLAG)))
        {
            // PCT出庫予定情報検索キー
            PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();

            // 取得項目
            // バッチSeqNo.
            planSKey.setCollect(PCTRetPlan.BATCH_SEQ_NO);

            // 検索条件
            // 予定日
            planSKey.setPlanDay(planDay);
            // RFT管理情報とPCT出庫予定情報のバッチSeqNo.を結合
            planSKey.setJoin(Rft.BATCH_SEQ_NO, PCTRetPlan.BATCH_SEQ_NO);
            rftSKey.setKey(Rft.BATCH_SEQ_NO, planSKey);

            // バッチSeqNo.
            rftSKey.setBatchSeqNoCollect();
        }
        else
        {
            // バッチSeqNo.
            rftSKey.setBatchSeqNo(batchSeqNo);
        }
        // 検索結果の件数を返却
        return rftHandler.count(rftSKey);
    }

    /**
     * 作業中の予定情報件数を取得します。
     * 
     * @param p 画面情報
     * @param planDay 予定日
     * @param batchSeqNo バッチSeqNo.
     * @throws CommonException 全ての例外をスローします。
     */
    protected int getWorkingCount(ScheduleParams p, String planDay, String batchSeqNo)
            throws CommonException
    {
        // PCT出庫作業情報検索キー
        PCTRetWorkInfoSearchKey workSKey = new PCTRetWorkInfoSearchKey();
        // PCT出庫作業情報ハンドラ
        PCTRetWorkInfoHandler workHandler = new PCTRetWorkInfoHandler(getConnection());

        // 検索条件
        // 荷主コード
        workSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                workSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // 予定日が指定されていた場合
        if (!StringUtil.isBlank(planDay))
        {
            workSKey.setPlanDay(planDay);
        }
        // バッチSeqNo.が指定されていた場合
        if (!StringUtil.isBlank(batchSeqNo))
        {
            workSKey.setBatchSeqNo(batchSeqNo);
        }
        // 状態フラグ
        workSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKING, "=", "(", "", false);
        workSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_EXIST_WORKED, "=", "", "", false);
        workSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION, "=", "", "", false);
        workSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION, "=", "", ")", false);

        // 検索結果の件数を返却
        return workHandler.count(workSKey);
    }

    /**
     * ロット指定時の終了予測時間を取得します
     * 
     * @param whereSql 検索条件
     * @return double 終了予測時間基準値
     * @throws CommonException 全ての例外をスローします。
     */
    protected double getEndPlanTimeForLot(StringBuffer whereSql)
            throws CommonException
    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;
        // 1hあたりの作業基準値
        double endPlanTimeStandardValue = 0;
        // 予定日、バッチSeqNo.あたりの総作業基準値
        double standardValueTotal = 0;
        // 終了予測時間(時)
        double endPlanTime = 0;
        // 終了予測時間(Total)
        double endPlanTimeTotal = 0;
        // 荷主コード格納一時領域
        String tmpConsignor = null;
        // 予定日格納一時領域
        String tmpPlanDay = null;
        // バッチSeqNo格納一時領域
        String tmpBatchSeq = null;
        // エリア基準値件数
        int standardCnt = 0;
        // 予定数（Total）
        int planQtytotal = 0;
        // 実績数（Total）
        int resultQtyTotal = 0;
        // RFT号機数
        BigDecimal rftCnt = new BigDecimal(0);

        // SQLの生成
        StringBuffer sql = null;
        sql = new StringBuffer();
        // 取得項目
        sql.append("SELECT ");
        sql.append(" DNPCTRETPLAN.CONSIGNOR_CODE ");
        sql.append(" ,DNPCTRETPLAN.PLAN_AREA_NO ");
        sql.append(" ,DNPCTRETPLAN.PLAN_DAY ");
        sql.append(" ,DNPCTRETPLAN.BATCH_SEQ_NO ");
        sql.append(" ,SUM(DNPCTRETPLAN.PLAN_QTY) PLAN_QTY ");
        sql.append(" ,SUM(DNPCTRETPLAN.RESULT_QTY) RESULT_QTY ");
        sql.append(" ,SUM(DNPCTRETPLAN.SHORTAGE_QTY) SHORTAGE_QTY ");
        sql.append(" ,NVL(ALLUSERRESULT.LOT_STANDARD_VALUE, 0) LOT_STANDARD_VALUE ");
        sql.append(" ,NVL(MAX(DMRFT.COUNT), 0) RFT_COUNT ");
        // 対象テーブル
        sql.append("FROM ");
        sql.append(" (SELECT ");
        sql.append("   * ");
        sql.append("  FROM");
        sql.append("   DNPCTRETPLAN").append(whereSql);
        sql.append(" ) DNPCTRETPLAN ");
        sql.append(" ,(SELECT ");
        sql.append("    DNPCTALLUSERRESULT.CONSIGNOR_CODE ");
        sql.append("    ,DNPCTALLUSERRESULT.AREA_NO ");
        sql.append("    ,DNPCTALLUSERRESULT.LOT_STANDARD_VALUE ");
        sql.append("   FROM ");
        sql.append("    DNPCTALLUSERRESULT ");
        sql.append("   WHERE ");
        sql.append("    DNPCTALLUSERRESULT.RANK = ").append(DBFormat.format(SystemDefine.RANK_NO_B)).append(" ");
        sql.append("  ) ALLUSERRESULT ");
        sql.append("  ,(SELECT ");
        sql.append("     COUNT(*) COUNT ");
        sql.append("     ,DMRFT.BATCH_SEQ_NO ");
        sql.append("    FROM ");
        sql.append("     DMRFT ");
        sql.append("    GROUP BY ");
        sql.append("     DMRFT.BATCH_SEQ_NO ");
        sql.append("  ) DMRFT ");
        // 検索条件
        sql.append("WHERE ");
        sql.append(" DNPCTRETPLAN.CONSIGNOR_CODE = ALLUSERRESULT.CONSIGNOR_CODE(+) ");
        sql.append(" AND DNPCTRETPLAN.PLAN_AREA_NO = ALLUSERRESULT.AREA_NO(+) ");
        sql.append(" AND DNPCTRETPLAN.BATCH_SEQ_NO = DMRFT.BATCH_SEQ_NO(+) ");
        // 集約条件
        sql.append("GROUP BY");
        sql.append(" DNPCTRETPLAN.CONSIGNOR_CODE ");
        sql.append(" ,DNPCTRETPLAN.PLAN_AREA_NO ");
        sql.append(" ,DNPCTRETPLAN.PLAN_DAY ");
        sql.append(" ,DNPCTRETPLAN.BATCH_SEQ_NO ");
        sql.append(" ,ALLUSERRESULT.LOT_STANDARD_VALUE ");
        // 表示順
        sql.append("ORDER BY");
        sql.append(" DNPCTRETPLAN.CONSIGNOR_CODE ");
        sql.append(" ,DNPCTRETPLAN.PLAN_DAY ");
        sql.append(" ,DNPCTRETPLAN.BATCH_SEQ_NO ");
        sql.append(" ,DNPCTRETPLAN.PLAN_AREA_NO ");

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // SQLの実行
            Entity[] entity = ddbHandler.query(String.valueOf(sql), new PCTRetPlan());

            // 件数分繰り返す
            for (int i = 0; i < entity.length; i++)
            {
                int planQty = entity[i].getBigDecimal(new FieldName("", "PLAN_QTY")).intValue();
                int resultQty =
                        entity[i].getBigDecimal(new FieldName("", "RESULT_QTY")).intValue()
                                + entity[i].getBigDecimal(new FieldName("", "SHORTAGE_QTY")).intValue();
                endPlanTimeStandardValue = entity[i].getBigDecimal(new FieldName("", "LOT_STANDARD_VALUE")).doubleValue();

                // 初期処理
                if (i == 0)
                {
                    // 各一時領域にデータをセット
                    tmpConsignor = String.valueOf(entity[i].getValue(new FieldName("", "CONSIGNOR_CODE")));
                    tmpPlanDay = String.valueOf(entity[i].getValue(new FieldName("", "PLAN_DAY")));
                    tmpBatchSeq = String.valueOf(entity[i].getValue(new FieldName("", "BATCH_SEQ_NO")));
                    rftCnt = entity[i].getBigDecimal(new FieldName("", "RFT_COUNT"));
                }
                // 荷主コード、予定日、バッチSeqNoのいずれかが違う場合
                else if ((!tmpConsignor.equals(String.valueOf(entity[i].getValue(new FieldName("", "CONSIGNOR_CODE")))))
                        || (!tmpPlanDay.equals(String.valueOf(entity[i].getValue(new FieldName("", "PLAN_DAY")))))
                        || (!tmpBatchSeq.equals(String.valueOf(entity[i].getValue(new FieldName("", "BATCH_SEQ_NO"))))))
                {
                    tmpConsignor = String.valueOf(entity[i].getValue(new FieldName("", "CONSIGNOR_CODE")));
                    tmpPlanDay = String.valueOf(entity[i].getValue(new FieldName("", "PLAN_DAY")));
                    tmpBatchSeq = String.valueOf(entity[i].getValue(new FieldName("", "BATCH_SEQ_NO")));
                    // エリア基準値が1件でも存在すれば予定日、バッチSeqNo.単位で終了予測時刻の算出を行う
                    if ((standardCnt > 0) && (standardValueTotal > 0))
                    {
                        BigDecimal bdQty = new BigDecimal(planQtytotal - resultQtyTotal).multiply(new BigDecimal(3600));
                        BigDecimal bdStandardValue =
                                new BigDecimal(standardValueTotal).divide(new BigDecimal(standardCnt), 3,
                                        BigDecimal.ROUND_HALF_UP);
                        BigDecimal bd = bdQty.divide(bdStandardValue, 3, BigDecimal.ROUND_HALF_UP);
                        if (rftCnt.intValue() > 0)
                        {
                            bd = bd.divide(rftCnt, 3, BigDecimal.ROUND_HALF_UP);
                        }

                        endPlanTime = bd.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                    else
                    {
                        endPlanTime = 0;
                    }
                    endPlanTimeTotal += endPlanTime;

                    rftCnt = entity[i].getBigDecimal(new FieldName("", "RFT_COUNT"));
                    standardCnt = 0;
                    standardValueTotal = 0;
                    planQtytotal = 0;
                    resultQtyTotal = 0;
                }

                int restCnt = planQty - resultQty;
                if (restCnt > 0)
                {
                    standardCnt++;
                    // エリア基準値が登録されている場合は登録値を使用
                    if (endPlanTimeStandardValue > 0)
                    {
                        standardValueTotal += endPlanTimeStandardValue;
                    }
                    // エリア基準値が登録されていない場合は残数を使用
                    else
                    {
                        standardValueTotal += (planQty - resultQty);
                    }
                }
                planQtytotal += planQty;
                resultQtyTotal += resultQty;
            }
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }

        // エリア基準値が1件でも存在すれば予定日、バッチSeqNo.単位で終了予測時刻の算出を行う
        if ((standardCnt > 0) && (standardValueTotal > 0))
        {
            BigDecimal bdQty = new BigDecimal(planQtytotal - resultQtyTotal).multiply(new BigDecimal(3600));
            BigDecimal bdStandardValue =
                    new BigDecimal(standardValueTotal).divide(new BigDecimal(standardCnt), 3, BigDecimal.ROUND_HALF_UP);
            BigDecimal bd = bdQty.divide(bdStandardValue, 3, BigDecimal.ROUND_HALF_UP);
            if (rftCnt.intValue() > 0)
            {
                bd = bd.divide(rftCnt, 3, BigDecimal.ROUND_HALF_UP);
            }

            endPlanTime = bd.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        else
        {
            endPlanTime = 0;
        }
        endPlanTimeTotal += endPlanTime;

        return endPlanTimeTotal;
    }

    /**
     * オーダー指定時の終了予測時間を取得します
     * 
     * @param whereSql 検索条件
     * @return 終了予測時間基準値
     * @throws CommonException 全ての例外をスローします。
     */
    protected double getEndPlanTimeForOrder(StringBuffer whereSql)
            throws CommonException
    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;
        // 1hあたりの作業基準値
        double endPlanTimeStandardValue = 0;
        // 予定日、バッチSeqNo.あたりの総作業基準値
        double standardValueTotal = 0;
        // 終了予測時間(時)
        double endPlanTime = 0;
        // 終了予測時間(Total)
        double endPlanTimeTotal = 0;
        // 荷主コード格納一時領域
        String tmpConsignor = null;
        // 予定日格納一時領域
        String tmpPlanDay = null;
        // バッチSeqNo格納一時領域
        String tmpBatchSeq = null;
        // エリア基準値件数
        int standardCnt = 0;
        // 予定数（Total）
        int planQtytotal = 0;
        // 実績数（Total）
        int resultQtyTotal = 0;
        // RFT号機数
        BigDecimal rftCnt = new BigDecimal(0);

        // 荷主コード、エリアNo、予定日、バッチNo単位のオーダー予定数・実績数を取得
        /*
         * 出庫予定読込条件： 荷主コード = 画面にて入力された荷主コード エリアNo = 画面にて入力されたエリアNo 予定日 =
         * 読み込んできた出庫予定情報の予定日 バッチSeqNo = 読み込んできた出庫予定情報のバッチSeqNo 状態フラグ != 削除('9')
         * 荷主コード = ランクBの全ユーザー実績情報の荷主コード エリアNo = ランクBの全ユーザー実績情報のエリアNo
         * オーダーNo.で集約し、状態フラグの最小値が完了のデータのみ実績オーダー数として計上する
         */
        StringBuffer sql = null;
        sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("    DNPCTRETPLAN.CONSIGNOR_CODE ");
        sql.append("    ,DNPCTRETPLAN.PLAN_AREA_NO ");
        sql.append("    ,DNPCTRETPLAN.PLAN_DAY ");
        sql.append("    ,DNPCTRETPLAN.BATCH_SEQ_NO ");
        sql.append("    ,COUNT(DNPCTRETPLAN.PLAN_ORDER_NO) PLAN_ORDER_COUNT ");
        sql.append("    ,SUM(DNPCTRETPLAN.RESULT_ORDER_COUNT) RESULT_ORDER_COUNT ");
        sql.append("    ,NVL(ALLUSERRESULT.ORDER_STANDARD_VALUE, 0) ORDER_STANDARD_VALUE ");
        sql.append("    ,NVL(MAX(DMRFT.COUNT), 0) RFT_COUNT ");
        sql.append("FROM");
        sql.append("    (SELECT ");
        sql.append("        DNPCTRETPLAN.CONSIGNOR_CODE ");
        sql.append("        ,DNPCTRETPLAN.PLAN_AREA_NO ");
        sql.append("        ,DNPCTRETPLAN.PLAN_DAY ");
        sql.append("        ,DNPCTRETPLAN.BATCH_SEQ_NO ");
        sql.append("        ,DNPCTRETPLAN.PLAN_ORDER_NO ");
        sql.append("        ,CASE MIN(DNPCTRETPLAN.STATUS_FLAG) ");
        sql.append("            WHEN ").append(DBFormat.format(PCTRetrievalInParameter.BATCHSTATUS_FLAG_COMPLETION)).append(
                " ");
        sql.append("            THEN 1 ");
        sql.append("            ELSE 0 END RESULT_ORDER_COUNT ");
        sql.append("    FROM");
        sql.append("        DNPCTRETPLAN").append(whereSql);
        sql.append("    GROUP BY ");
        sql.append("        DNPCTRETPLAN.CONSIGNOR_CODE ");
        sql.append("        ,DNPCTRETPLAN.PLAN_AREA_NO ");
        sql.append("        ,DNPCTRETPLAN.PLAN_DAY ");
        sql.append("        ,DNPCTRETPLAN.BATCH_SEQ_NO ");
        sql.append("        ,DNPCTRETPLAN.PLAN_ORDER_NO ");
        sql.append("    ) DNPCTRETPLAN ");
        sql.append("    ,(SELECT ");
        sql.append("        DNPCTALLUSERRESULT.CONSIGNOR_CODE ");
        sql.append("        ,DNPCTALLUSERRESULT.AREA_NO ");
        sql.append("        ,DNPCTALLUSERRESULT.ORDER_STANDARD_VALUE ");
        sql.append("    FROM ");
        sql.append("        DNPCTALLUSERRESULT ");
        sql.append("    WHERE ");
        sql.append("        DNPCTALLUSERRESULT.RANK = ").append(DBFormat.format(SystemDefine.RANK_NO_B)).append(" ");
        sql.append("    ) ALLUSERRESULT ");
        sql.append("    ,(SELECT ");
        sql.append("        COUNT(*) COUNT ");
        sql.append("        ,DMRFT.BATCH_SEQ_NO ");
        sql.append("    FROM ");
        sql.append("        DMRFT ");
        sql.append("    GROUP BY ");
        sql.append("        DMRFT.BATCH_SEQ_NO ");
        sql.append("    ) DMRFT ");
        sql.append("WHERE ");
        sql.append("    DNPCTRETPLAN.CONSIGNOR_CODE = ALLUSERRESULT.CONSIGNOR_CODE(+) ");
        sql.append("    AND DNPCTRETPLAN.PLAN_AREA_NO = ALLUSERRESULT.AREA_NO(+) ");
        sql.append("    AND DNPCTRETPLAN.BATCH_SEQ_NO = DMRFT.BATCH_SEQ_NO(+) ");
        sql.append("GROUP BY ");
        sql.append("    DNPCTRETPLAN.CONSIGNOR_CODE ");
        sql.append("    ,DNPCTRETPLAN.PLAN_AREA_NO ");
        sql.append("    ,DNPCTRETPLAN.PLAN_DAY ");
        sql.append("    ,DNPCTRETPLAN.BATCH_SEQ_NO ");
        sql.append("    ,ALLUSERRESULT.ORDER_STANDARD_VALUE ");
        sql.append("ORDER BY ");
        sql.append("    DNPCTRETPLAN.CONSIGNOR_CODE ");
        sql.append("    ,DNPCTRETPLAN.PLAN_DAY ");
        sql.append("    ,DNPCTRETPLAN.BATCH_SEQ_NO ");
        sql.append("    ,DNPCTRETPLAN.PLAN_AREA_NO ");

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // 同一荷主コード、予定日、バッチSeqNo単位で各エリア毎の終了予測時間を取得し
            // その中の最大値を取得する
            // SQLの実行
            Entity[] entity = ddbHandler.query(String.valueOf(sql), new PCTRetPlan());

            // 同一荷主コード、予定日、バッチNo単位で各エリア毎の終了予測時間を取得し
            // その中の最大値を取得する
            for (int i = 0; i < entity.length; i++)
            {
                int planQty = entity[i].getBigDecimal(new FieldName("", "PLAN_ORDER_COUNT")).intValue();
                int resultQty = entity[i].getBigDecimal(new FieldName("", "RESULT_ORDER_COUNT")).intValue();
                endPlanTimeStandardValue = entity[i].getBigDecimal(new FieldName("", "ORDER_STANDARD_VALUE")).doubleValue();

                // 初期処理
                if (i == 0)
                {
                    // 各一時領域にデータをセット
                    tmpConsignor = String.valueOf(entity[i].getValue(new FieldName("", "CONSIGNOR_CODE")));
                    tmpPlanDay = String.valueOf(entity[i].getValue(new FieldName("", "PLAN_DAY")));
                    tmpBatchSeq = String.valueOf(entity[i].getValue(new FieldName("", "BATCH_SEQ_NO")));
                    rftCnt = entity[i].getBigDecimal(new FieldName("", "RFT_COUNT"));
                }
                // 荷主コード、予定日、バッチSeqNoのいずれかが違う場合
                else if ((!tmpConsignor.equals(String.valueOf(entity[i].getValue(new FieldName("", "CONSIGNOR_CODE")))))
                        || (!tmpPlanDay.equals(String.valueOf(entity[i].getValue(new FieldName("", "PLAN_DAY")))))
                        || (!tmpBatchSeq.equals(String.valueOf(entity[i].getValue(new FieldName("", "BATCH_SEQ_NO"))))))
                {
                    tmpConsignor = String.valueOf(entity[i].getValue(new FieldName("", "CONSIGNOR_CODE")));
                    tmpPlanDay = String.valueOf(entity[i].getValue(new FieldName("", "PLAN_DAY")));
                    tmpBatchSeq = String.valueOf(entity[i].getValue(new FieldName("", "BATCH_SEQ_NO")));
                    // エリア基準値が1件でも存在すれば予定日、バッチSeqNo.単位で終了予測時刻の算出を行う
                    if ((standardCnt > 0) && (standardValueTotal > 0))
                    {
                        BigDecimal bdQty = new BigDecimal(planQtytotal - resultQtyTotal).multiply(new BigDecimal(3600));
                        BigDecimal bdStandardValue =
                                new BigDecimal(standardValueTotal).divide(new BigDecimal(standardCnt), 3,
                                        BigDecimal.ROUND_HALF_UP);
                        BigDecimal bd = bdQty.divide(bdStandardValue, 3, BigDecimal.ROUND_HALF_UP);
                        if (rftCnt.intValue() > 0)
                        {
                            bd = bd.divide(rftCnt, 3, BigDecimal.ROUND_HALF_UP);
                        }

                        endPlanTime = bd.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                    else
                    {
                        endPlanTime = (planQtytotal - resultQtyTotal) * 3600;
                    }
                    endPlanTimeTotal += endPlanTime;

                    rftCnt = entity[i].getBigDecimal(new FieldName("", "RFT_COUNT"));
                    standardCnt = 0;
                    standardValueTotal = 0;
                    planQtytotal = 0;
                    resultQtyTotal = 0;
                }

                int restCnt = planQty - resultQty;
                if (restCnt > 0)
                {
                    standardCnt++;
                    // エリア基準値が登録されている場合は登録値を使用
                    if (endPlanTimeStandardValue > 0)
                    {
                        standardValueTotal += endPlanTimeStandardValue;
                    }
                    // エリア基準値が登録されていない場合は残数を使用
                    else
                    {
                        standardValueTotal += (planQty - resultQty);
                    }
                }
                planQtytotal += planQty;
                resultQtyTotal += resultQty;
            }
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }

        // エリア基準値が1件でも存在すれば予定日、バッチSeqNo.単位で終了予測時刻の算出を行う
        if ((standardCnt > 0) && (standardValueTotal > 0))
        {
            BigDecimal bdQty = new BigDecimal(planQtytotal - resultQtyTotal).multiply(new BigDecimal(3600));
            BigDecimal bdStandardValue =
                    new BigDecimal(standardValueTotal).divide(new BigDecimal(standardCnt), 3, BigDecimal.ROUND_HALF_UP);
            BigDecimal bd = bdQty.divide(bdStandardValue, 3, BigDecimal.ROUND_HALF_UP);
            if (rftCnt.intValue() > 0)
            {
                bd = bd.divide(rftCnt, 3, BigDecimal.ROUND_HALF_UP);
            }

            endPlanTime = bd.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        else
        {
            endPlanTime = (planQtytotal - resultQtyTotal) * 3600;
        }
        endPlanTimeTotal += endPlanTime;

        return endPlanTimeTotal;
    }

    /**
     * 行指定時の終了予測時間を取得します
     * 
     * @param whereSql 検索条件
     * @return 終了予測時間基準値
     * @throws CommonException 全ての例外をスローします。
     */
    protected double getEndPlanTimeForLine(StringBuffer whereSql)
            throws CommonException
    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;
        // 1hあたりの作業基準値
        double endPlanTimeStandardValue = 0;
        // 予定日、バッチSeqNo.あたりの総作業基準値
        double standardValueTotal = 0;
        // 終了予測時間(時)
        double endPlanTime = 0;
        // 終了予測時間(Total)
        double endPlanTimeTotal = 0;
        // 荷主コード格納一時領域
        String tmpConsignor = null;
        // 予定日格納一時領域
        String tmpPlanDay = null;
        // バッチSeqNo格納一時領域
        String tmpBatchSeq = null;
        // エリア基準値件数
        int standardCnt = 0;
        // 予定数（Total）
        int planQtytotal = 0;
        // 実績数（Total）
        int resultQtyTotal = 0;
        // RFT号機数
        BigDecimal rftCnt = new BigDecimal(0);

        // 荷主コード、エリアNo、予定日、バッチNo単位の行予定数・実績数を取得
        /*
         * 出庫予定読込条件： 荷主コード = 画面にて入力された荷主コード エリアNo = 画面にて入力されたエリアNo 予定日 =
         * 読み込んできた出庫予定情報の予定日 バッチSeqNo = 読み込んできた出庫予定情報のバッチSeqNo 状態フラグ != 削除('9')
         * 荷主コード = ランクBの全ユーザー実績情報の荷主コード エリアNo = ランクBの全ユーザー実績情報のエリアNo
         * 状態フラグが完了のデータのみ実績行数として計上する
         */
        StringBuffer sql = null;
        sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("    DNPCTRETPLAN.CONSIGNOR_CODE ");
        sql.append("    ,DNPCTRETPLAN.PLAN_AREA_NO ");
        sql.append("    ,DNPCTRETPLAN.PLAN_DAY ");
        sql.append("    ,DNPCTRETPLAN.BATCH_SEQ_NO ");
        sql.append("    ,COUNT(DNPCTRETPLAN.PLAN_UKEY) PLAN_LINE_COUNT ");
        sql.append("    ,SUM(DNPCTRETPLAN.RESULT_LINE_COUNT) RESULT_LINE_COUNT ");
        sql.append("    ,NVL(ALLUSERRESULT.LINE_STANDARD_VALUE, 0) LINE_STANDARD_VALUE ");
        sql.append("    ,NVL(MAX(DMRFT.COUNT), 0) RFT_COUNT ");
        sql.append("FROM ");
        sql.append("    (SELECT ");
        sql.append("        DNPCTRETPLAN.* ");
        sql.append("    ,CASE DNPCTRETPLAN.STATUS_FLAG ");
        sql.append("        WHEN ").append(DBFormat.format(PCTRetrievalInParameter.BATCHSTATUS_FLAG_COMPLETION)).append(
                " ");
        sql.append("        THEN 1 ");
        sql.append("        ELSE 0 END RESULT_LINE_COUNT ");
        sql.append("    FROM ");
        sql.append("        DNPCTRETPLAN ").append(whereSql);
        sql.append("    ) DNPCTRETPLAN ");
        sql.append("    ,(SELECT ");
        sql.append("        DNPCTALLUSERRESULT.CONSIGNOR_CODE ");
        sql.append("        ,DNPCTALLUSERRESULT.AREA_NO ");
        sql.append("        ,DNPCTALLUSERRESULT.LINE_STANDARD_VALUE ");
        sql.append("    FROM");
        sql.append("        DNPCTALLUSERRESULT ");
        sql.append("    WHERE");
        sql.append("        DNPCTALLUSERRESULT.RANK = ").append(DBFormat.format(SystemDefine.RANK_NO_B)).append(" ");
        sql.append("    ) ALLUSERRESULT ");
        sql.append("    ,(SELECT ");
        sql.append("        COUNT(*) COUNT ");
        sql.append("        ,DMRFT.BATCH_SEQ_NO ");
        sql.append("    FROM ");
        sql.append("        DMRFT ");
        sql.append("    GROUP BY ");
        sql.append("        DMRFT.BATCH_SEQ_NO ");
        sql.append("    ) DMRFT ");
        sql.append("WHERE ");
        sql.append("    DNPCTRETPLAN.CONSIGNOR_CODE = ALLUSERRESULT.CONSIGNOR_CODE(+) ");
        sql.append("    AND DNPCTRETPLAN.PLAN_AREA_NO = ALLUSERRESULT.AREA_NO(+) ");
        sql.append("    AND DNPCTRETPLAN.BATCH_SEQ_NO = DMRFT.BATCH_SEQ_NO(+) ");
        sql.append("GROUP BY ");
        sql.append("    DNPCTRETPLAN.CONSIGNOR_CODE ");
        sql.append("    ,DNPCTRETPLAN.PLAN_AREA_NO ");
        sql.append("    ,DNPCTRETPLAN.PLAN_DAY ");
        sql.append("    ,DNPCTRETPLAN.BATCH_SEQ_NO ");
        sql.append("    ,ALLUSERRESULT.LINE_STANDARD_VALUE ");
        sql.append("ORDER BY");
        sql.append("    DNPCTRETPLAN.CONSIGNOR_CODE ");
        sql.append("    ,DNPCTRETPLAN.PLAN_DAY ");
        sql.append("    ,DNPCTRETPLAN.BATCH_SEQ_NO ");
        sql.append("    ,DNPCTRETPLAN.PLAN_AREA_NO ");

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            // SQLの実行
            Entity[] entity = ddbHandler.query(String.valueOf(sql), new PCTRetPlan());

            // 同一荷主コード、予定日、バッチNo単位で各エリア毎の終了予測時間を取得し
            // その中の最大値を取得する
            for (int i = 0; i < entity.length; i++)
            {
                int planQty = entity[i].getBigDecimal(new FieldName("", "PLAN_LINE_COUNT")).intValue();
                int resultQty = entity[i].getBigDecimal(new FieldName("", "RESULT_LINE_COUNT")).intValue();
                endPlanTimeStandardValue = entity[i].getBigDecimal(new FieldName("", "LINE_STANDARD_VALUE")).doubleValue();

                // 初期処理
                if (i == 0)
                {
                    // 各一時領域にデータをセット
                    tmpConsignor = String.valueOf(entity[i].getValue(new FieldName("", "CONSIGNOR_CODE")));
                    tmpPlanDay = String.valueOf(entity[i].getValue(new FieldName("", "PLAN_DAY")));
                    tmpBatchSeq = String.valueOf(entity[i].getValue(new FieldName("", "BATCH_SEQ_NO")));
                    rftCnt = entity[i].getBigDecimal(new FieldName("", "RFT_COUNT"));
                }
                // 荷主コード、予定日、バッチSeqNoのいずれかが違う場合
                else if ((!tmpConsignor.equals(String.valueOf(entity[i].getValue(new FieldName("", "CONSIGNOR_CODE")))))
                        || (!tmpPlanDay.equals(String.valueOf(entity[i].getValue(new FieldName("", "PLAN_DAY")))))
                        || (!tmpBatchSeq.equals(String.valueOf(entity[i].getValue(new FieldName("", "BATCH_SEQ_NO"))))))
                {
                    tmpConsignor = String.valueOf(entity[i].getValue(new FieldName("", "CONSIGNOR_CODE")));
                    tmpPlanDay = String.valueOf(entity[i].getValue(new FieldName("", "PLAN_DAY")));
                    tmpBatchSeq = String.valueOf(entity[i].getValue(new FieldName("", "BATCH_SEQ_NO")));

                    // エリア基準値が1件でも存在すれば予定日、バッチSeqNo.単位で終了予測時刻の算出を行う
                    if ((standardCnt > 0) && (standardValueTotal > 0))
                    {
                        BigDecimal bdQty = new BigDecimal(planQtytotal - resultQtyTotal).multiply(new BigDecimal(3600));
                        BigDecimal bdStandardValue =
                                new BigDecimal(standardValueTotal).divide(new BigDecimal(standardCnt), 3,
                                        BigDecimal.ROUND_HALF_UP);
                        BigDecimal bd = bdQty.divide(bdStandardValue, 3, BigDecimal.ROUND_HALF_UP);
                        if (rftCnt.intValue() > 0)
                        {
                            bd = bd.divide(rftCnt, 3, BigDecimal.ROUND_HALF_UP);
                        }

                        endPlanTime = bd.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                    else
                    {
                        endPlanTime = (planQtytotal - resultQtyTotal) * 3600;
                    }
                    endPlanTimeTotal += endPlanTime;

                    rftCnt = entity[i].getBigDecimal(new FieldName("", "RFT_COUNT"));
                    standardCnt = 0;
                    standardValueTotal = 0;
                    planQtytotal = 0;
                    resultQtyTotal = 0;
                }

                int restCnt = planQty - resultQty;
                if (restCnt > 0)
                {
                    standardCnt++;
                    // エリア基準値が登録されている場合は登録値を使用
                    if (endPlanTimeStandardValue > 0)
                    {
                        standardValueTotal += endPlanTimeStandardValue;
                    }
                    // エリア基準値が登録されていない場合は残数を使用
                    else
                    {
                        standardValueTotal += (planQty - resultQty);
                    }
                }
                planQtytotal += planQty;
                resultQtyTotal += resultQty;
            }
        }
        finally
        {
            if (ddbHandler != null)
            {
                ddbHandler.close();
            }
        }

        // エリア基準値が1件でも存在すれば予定日、バッチSeqNo.単位で終了予測時刻の算出を行う
        if ((standardCnt > 0) && (standardValueTotal > 0))
        {
            BigDecimal bdQty = new BigDecimal(planQtytotal - resultQtyTotal).multiply(new BigDecimal(3600));
            BigDecimal bdStandardValue =
                    new BigDecimal(standardValueTotal).divide(new BigDecimal(standardCnt), 3, BigDecimal.ROUND_HALF_UP);
            BigDecimal bd = bdQty.divide(bdStandardValue, 3, BigDecimal.ROUND_HALF_UP);
            if (rftCnt.intValue() > 0)
            {
                bd = bd.divide(rftCnt, 3, BigDecimal.ROUND_HALF_UP);
            }

            endPlanTime = bd.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        else
        {
            endPlanTime = (planQtytotal - resultQtyTotal) * 3600;
        }
        endPlanTimeTotal += endPlanTime;

        return endPlanTimeTotal;
    }

    /**
     * 
     */
    protected int getWorkCompleteCount(ScheduleParams p, String planDay, String batchSeqNo)
            throws CommonException
    {
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());

        // 検索条件
        // 荷主コード
        planSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                planSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // 状態フラグ
        planSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        // スケジュール処理フラグ
        planSKey.setSchFlag(PCTRetrievalInParameter.SCH_FLAG_COMPLETION, "=");
        // 予定日が指定されていた場合
        if (!StringUtil.isBlank(planDay))
        {
            planSKey.setPlanDay(planDay);
        }
        // バッチSeqNo.が指定されていた場合
        if (!StringUtil.isBlank(batchSeqNo))
        {
            planSKey.setBatchSeqNo(batchSeqNo);
        }

        // 検索結果の件数を返却
        return planHandler.count(planSKey);
    }

    /**
     * 出庫予定情報に存在する出荷先毎のデータを取得します。
     * 
     * @param p 画面情報
     * @param customerCode 出荷先コード(最小値)
     * @return PCTRetPlan[] 検索結果
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected PCTRetPlan[] getSeparatePlan(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫予定情報エンティティ
        PCTRetPlan[] ent = null;
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());

        // 前頁処理フラグ
        boolean previousFlag = false;

        // 取得項目
        // 予定日
        planSKey.setPlanDayCollect();

        // 検索条件
        // 荷主コード
        planSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                planSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // 状態フラグ
        planSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        // 予定日
        if (PCTRetrievalInParameter.PROCESS_FLAG_VIEW.equals(p.getString(PROCESS_FLAG)))
        {
            // 検索条件
            planSKey.setPlanDay(p.getString(PLAN_DATE), ">=");

            // 表示順
            planSKey.setPlanDayOrder(true);
        }
        else if (PCTRetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            // 検索条件
            planSKey.setPlanDay(p.getString(PLAN_DATE), "<");

            // 表示順
            planSKey.setPlanDayOrder(false);

            // 前頁処理フラグを成立
            previousFlag = true;
        }
        else if (PCTRetrievalInParameter.PROCESS_FLAG_NEXT_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            // 検索条件
            planSKey.setPlanDay(p.getString(PLAN_DATE), ">");

            // 表示順
            planSKey.setPlanDayOrder(true);
        }

        // 集約条件
        // 予定日
        planSKey.setPlanDayGroup();

        // 検索結果を返却(画面の件数分のみ取得)
        ent = (PCTRetPlan[])planHandler.find(planSKey, MAX_DISPLAY_PROGRESS);
        if (ArrayUtil.isEmpty(ent))
        {
            return null;
        }

        // 前頁処理の場合は取得した情報を逆にして処理
        if (previousFlag)
        {
            ent = turnArray(ent);
        }
        // 生成したエンティティを返却
        return ent;
    }

    /**
     * 配列の順序を逆順にします。
     * 
     * @param ent 出荷先別リスト
     * @return PCTRetPlan[] 逆順にした配列
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected PCTRetPlan[] turnArray(PCTRetPlan[] ent)
            throws CommonException
    {
        // 処理カウント
        int index = 0;
        // 返却リストの生成
        PCTRetPlan[] subArray = new PCTRetPlan[ent.length];

        // 配列を最後尾から繰り返し
        for (int i = subArray.length - 1; i >= 0; i--)
        {
            // 取得した情報を格納
            subArray[index++] = ent[i];
        }
        // 設定したリストを返却
        return subArray;
    }

    /**
     * 出庫予定情報に存在する出荷先毎のデータを取得します。
     * 
     * @param p 画面情報
     * @param customerCode 出荷先コード(最小値)
     * @return PCTRetPlan[] 検索結果
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected PCTRetPlan[] getSeparatePlanBatchSeqNo(ScheduleParams p)
            throws CommonException
    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = new DefaultDDBHandler(getConnection());

        // SQL保持変数
        StringBuffer sql = null;

        // 前頁処理フラグ
        boolean previousFlag = false;

        // 検索条件をセット
        if (PCTRetrievalInParameter.PROCESS_FLAG_VIEW.equals(p.getString(PROCESS_FLAG)))
        {
            sql = new StringBuffer();
            sql.append("SELECT ");
            sql.append(" DNPCTRETPLAN.PLAN_DAY PLAN_DAY ");
            sql.append(" ,DNPCTRETPLAN.BATCH_SEQ_NO BATCH_SEQ_NO ");
            sql.append(" ,DNPCTRETPLAN.BATCH_NO BATCH_NO ");
            sql.append("FROM ");
            sql.append(" DNPCTRETPLAN ");
            sql.append("WHERE ");
            sql.append(" ((DNPCTRETPLAN.PLAN_DAY = ").append(DBFormat.format(p.getString(PLAN_DATE))).append(" ");
            sql.append("    AND DNPCTRETPLAN.BATCH_SEQ_NO >= ").append(DBFormat.format(p.getString(BATCH_SEQ_NO))).append(
                    ")");
            sql.append("    OR DNPCTRETPLAN.PLAN_DAY > ").append(DBFormat.format(p.getString(PLAN_DATE))).append(")");
            sql.append("    AND DNPCTRETPLAN.CONSIGNOR_CODE = ").append(DBFormat.format(p.getString(CONSIGNOR_CODE))).append(
                    " ");
            // 予定エリアNo.
            if (!StringUtil.isBlank(p.getString(AREA_NO)))
            {
                // 全エリア以外が選ばれている場合
                if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
                {
                    sql.append(" AND DNPCTRETPLAN.PLAN_AREA_NO = ").append(DBFormat.format(p.getString(AREA_NO))).append(
                            " ");
                }
            }
            sql.append(" AND DNPCTRETPLAN.STATUS_FLAG != ").append(
                    DBFormat.format(PCTRetrievalInParameter.STATUS_FLAG_DELETE)).append(" ");
            sql.append("GROUP BY");
            sql.append(" DNPCTRETPLAN.PLAN_DAY");
            sql.append(" ,DNPCTRETPLAN.BATCH_SEQ_NO");
            sql.append(" ,DNPCTRETPLAN.BATCH_NO ");
            sql.append("ORDER BY");
            sql.append(" DNPCTRETPLAN.PLAN_DAY ASC");
            sql.append(" ,DNPCTRETPLAN.BATCH_SEQ_NO ASC");
        }
        else if (PCTRetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            sql = new StringBuffer();
            sql.append("SELECT ");
            sql.append(" DNPCTRETPLAN.PLAN_DAY PLAN_DAY");
            sql.append(" ,DNPCTRETPLAN.BATCH_SEQ_NO BATCH_SEQ_NO");
            sql.append(" ,DNPCTRETPLAN.BATCH_NO BATCH_NO ");
            sql.append("FROM");
            sql.append(" DNPCTRETPLAN ");
            sql.append("WHERE");
            sql.append(" ((DNPCTRETPLAN.PLAN_DAY = ").append(DBFormat.format(p.getString(PLAN_DATE))).append(" ");
            sql.append("    AND DNPCTRETPLAN.BATCH_SEQ_NO < ").append(DBFormat.format(p.getString(BATCH_SEQ_NO))).append(
                    ")");
            sql.append("    OR DNPCTRETPLAN.PLAN_DAY < ").append(DBFormat.format(p.getString(PLAN_DATE))).append(")");
            sql.append("    AND DNPCTRETPLAN.CONSIGNOR_CODE = ").append(DBFormat.format(p.getString(CONSIGNOR_CODE))).append(
                    " ");
            // 予定エリアNo.
            if (!StringUtil.isBlank(p.getString(AREA_NO)))
            {
                // 全エリア以外が選ばれている場合
                if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
                {
                    sql.append(" AND DNPCTRETPLAN.PLAN_AREA_NO = ").append(DBFormat.format(p.getString(AREA_NO))).append(
                            " ");
                }
            }
            sql.append(" AND DNPCTRETPLAN.STATUS_FLAG != ").append(
                    DBFormat.format(PCTRetrievalInParameter.STATUS_FLAG_DELETE)).append(" ");
            sql.append("GROUP BY");
            sql.append(" DNPCTRETPLAN.PLAN_DAY");
            sql.append(" ,DNPCTRETPLAN.BATCH_SEQ_NO");
            sql.append(" ,DNPCTRETPLAN.BATCH_NO ");
            sql.append("ORDER BY");
            sql.append(" DNPCTRETPLAN.PLAN_DAY DESC");
            sql.append(" ,DNPCTRETPLAN.BATCH_SEQ_NO DESC");

            // 前頁処理フラグを成立
            previousFlag = true;
        }
        else if (PCTRetrievalInParameter.PROCESS_FLAG_NEXT_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            sql = new StringBuffer();
            sql.append("SELECT ");
            sql.append(" DNPCTRETPLAN.PLAN_DAY PLAN_DAY");
            sql.append(" ,DNPCTRETPLAN.BATCH_SEQ_NO BATCH_SEQ_NO");
            sql.append(" ,DNPCTRETPLAN.BATCH_NO BATCH_NO ");
            sql.append("FROM");
            sql.append(" DNPCTRETPLAN ");
            sql.append("WHERE");
            sql.append(" ((DNPCTRETPLAN.PLAN_DAY = ").append(DBFormat.format(p.getString(PLAN_DATE))).append(" ");
            sql.append("    AND DNPCTRETPLAN.BATCH_SEQ_NO > ").append(DBFormat.format(p.getString(BATCH_SEQ_NO))).append(
                    " )");
            sql.append("    OR DNPCTRETPLAN.PLAN_DAY > ").append(DBFormat.format(p.getString(PLAN_DATE))).append(")");
            sql.append("    AND DNPCTRETPLAN.CONSIGNOR_CODE = ").append(DBFormat.format(p.getString(CONSIGNOR_CODE))).append(
                    " ");
            // 予定エリアNo.
            if (!StringUtil.isBlank(p.getString(AREA_NO)))
            {
                // 全エリア以外が選ばれている場合
                if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
                {
                    sql.append(" AND DNPCTRETPLAN.PLAN_AREA_NO = ").append(DBFormat.format(p.getString(AREA_NO))).append(
                            " ");
                }
            }
            sql.append(" AND DNPCTRETPLAN.STATUS_FLAG != ").append(
                    DBFormat.format(PCTRetrievalInParameter.STATUS_FLAG_DELETE)).append(" ");
            sql.append("GROUP BY");
            sql.append(" DNPCTRETPLAN.PLAN_DAY");
            sql.append(" ,DNPCTRETPLAN.BATCH_SEQ_NO");
            sql.append(" ,DNPCTRETPLAN.BATCH_NO ");
            sql.append("ORDER BY");
            sql.append(" DNPCTRETPLAN.PLAN_DAY ASC");
            sql.append(" ,DNPCTRETPLAN.BATCH_SEQ_NO ASC");
        }
        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
            
            // SQLの実行
            ddbHandler.execute(String.valueOf(sql));
            Entity[] entity = ddbHandler.getEntities(MAX_DISPLAY_PROGRESS, new PCTRetPlan());
            PCTRetPlan[] plans = new PCTRetPlan[entity.length];
            for (int i = 0; i < plans.length; i++)
            {
                plans[i] = new PCTRetPlan();
                plans[i].setValueMap(entity[i].getValueMap());
            }

            // 前頁処理の場合は取得した情報を逆にして処理
            if (previousFlag)
            {
                plans = turnArray(plans);
            }
            // 生成したエンティティ情報を返却
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
     * リスト内データの並び替えを行います。
     * 
     * @param param 取得データ
     * @return List<Params> 並び替え後リスト
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected List<Params> getSortList(PCTRetrievalOutParameter[] param, String button, ScheduleParams p)
            throws CommonException
    {
        // 返却パラメータ配列
        List<Params> result = new ArrayList<Params>();
        // 進捗率格納用
        BigDecimal bdRate = new BigDecimal(0);
        // 進捗率
        double endTime = 0.0;
        // システム時刻取得
        long systemTime = Calendar.getInstance().getTimeInMillis();
        // ロットフラグ
        boolean rdoLotFlag = false;
        // オーダーフラグ
        boolean rdoOrderFlag = false;
        // 行フラグ
        boolean rdoLineFlag = false;

        // 画面で選択されたラジオにてフラグを成立させる
        if (PCTRetrievalInParameter.ENDPLANTIME_LOT_STANDARD.equals(p.getString(END_PLAN_TIME_FLAG)))
        {
            rdoLotFlag = true;
        }
        else if (PCTRetrievalInParameter.ENDPLANTIME_ORDER_STANDARD.equals(p.getString(END_PLAN_TIME_FLAG)))
        {
            rdoOrderFlag = true;
        }
        else
        {
            rdoLineFlag = true;
        }

        // パラメータ分繰り返す
        // ここでは出荷先コードの桁数を合わせる
        for (int i = 0; i < param.length; i++)
        {
            // パラメータの生成
            Params listParam = new Params();

            // 予定日
            listParam.set(PLAN_DATE, param[i].getPlanDay());
            // バッチSeqNo.
            listParam.set(BATCH_SEQ_NO, param[i].getBatchSeqNo());
            // バッチNo.
            listParam.set(BATCH_NO, param[i].getBatchNo());
            // オーダー件数
            String OrderCount = WmsFormatter.getNumFormat(param[i].getResultOrderCnt());
            OrderCount = OrderCount.concat("/").concat(WmsFormatter.getNumFormat(param[i].getPlanOrderCnt()));
            listParam.set(ORDER_COUNT, OrderCount);
            // 箱数
            listParam.set(BOX_COUNT, param[i].getBoxCnt());
            // 行数
            String lineCount = WmsFormatter.getNumFormat(param[i].getResultLineCnt());
            lineCount = lineCount.concat("/").concat(WmsFormatter.getNumFormat(param[i].getPlanLineCnt()));
            listParam.set(LINE_COUNT, lineCount);
            // ロット数
            String lotCount = WmsFormatter.getNumFormat(param[i].getResultLotCnt());
            lotCount = lotCount.concat("/").concat(WmsFormatter.getNumFormat(param[i].getPlanLotCnt()));
            listParam.set(LOT_COUNT, lotCount);
            // カート台数
            listParam.set(CART_COUNT, param[i].getCartCnt());
            // 進捗率
            bdRate = getProgressRate(param[i].getPlanQty(), param[i].getResultQty());
            listParam.set(PROGRESS_RATE, bdRate);
            // ボタン制御
            listParam.set(BUTTON_FLAG, button);
            // 終了予測時刻
            if (param[i].getEndPlanTime() == endTime)
            {
                if ((rdoLotFlag && (param[i].getPlanLotCnt() == param[i].getResultLotCnt()))
                        || (rdoOrderFlag && (param[i].getPlanOrderCnt() == param[i].getResultOrderCnt()))
                        || (rdoLineFlag && (param[i].getPlanLineCnt() == param[i].getResultLineCnt())))
                {
                    listParam.set(END_PLAN_TIME,
                            DisplayResource.getScheduleStatus(PCTRetrievalInParameter.SCH_FLAG_COMPLTE));
                }
                else
                {
                    listParam.set(END_PLAN_TIME,
                            DisplayResource.getScheduleStatus(PCTRetrievalInParameter.SCH_FLAG_NOT_SCHEDULE));
                }
            }
            else if (param[i].getEndPlanTime() < endTime)
            {
                listParam.set(END_PLAN_TIME,
                        DisplayResource.getScheduleStatus(PCTRetrievalInParameter.SCH_FLAG_NOT_SCHEDULE));
            }
            else
            {
                listParam.set(END_PLAN_TIME, WmsFormatter.toDispDateTime(WmsFormatter.toParamDateTime(getEndDateTime(
                        param[i].getEndPlanTime(), systemTime)), getLocale()));
            }

            // 生成したパラメータを配列に格納
            result.add(listParam);
        }
        // 生成した配列を返却
        return result;
    }

    /**
     * 進捗率を求めます。
     * 
     * @param planQty 予定数
     * @param resultQty 実績数
     * @return double 進捗率
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected BigDecimal getProgressRate(int planQty, int resultQty)
            throws CommonException
    {
        // 予定数
        BigDecimal bdPlanQty = new BigDecimal(planQty);
        // 実績数
        BigDecimal bdResultQty = new BigDecimal(resultQty);
        // 計算値
        BigDecimal bdQty = new BigDecimal(0);

        // 予定数と実績数が共に0以上の場合
        if (planQty > 0 && resultQty > 0)
        {
            // ((実績数 / 予定数(小数点3桁の四捨五入)) * (100))
            bdQty = bdResultQty.divide(bdPlanQty, 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }
        // 求めた率を返却
        return bdQty;
    }

    /**
     * システム時刻に終了予測時間を加算し、終了予測時刻を取得します。<BR>
     * 
     * @param endPlanTime 終了予測時間(時) <BR>
     * @param systemTime システム時刻(秒) <BR>
     * @return 終了予測時間<BR>
     */
    protected Date getEndDateTime(double endPlanTime, long systemTime)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(systemTime + (long)(endPlanTime * 1000));
        return calendar.getTime();
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
