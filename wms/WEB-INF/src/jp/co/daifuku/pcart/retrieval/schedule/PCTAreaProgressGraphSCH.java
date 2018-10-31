// $Id: PCTAreaProgressGraphSCH.java 6900 2010-01-25 11:21:11Z kumano $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.pcart.retrieval.schedule.PCTAreaProgressGraphSCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * エリア別作業進捗(グラフあり)のスケジュール処理を行います。
 *
 * @version $Revision: 6900 $, $Date:: 2010-01-25 20:21:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class PCTAreaProgressGraphSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 進捗リストセルの最大表示件数
     */
    private static final int MAX_DISPLAY_PROGRESS = 4;


    /** 保存用のフィールド 荷主名称(<code>CONSIGNOR_NAME</code>) */
    private FieldName _CONSIGNOR_NAME = new FieldName("", "CONSIGNOR_NAME");

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
    public PCTAreaProgressGraphSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 初期表示を行います。<BR>
     * <BR>
     * 概要：PCT出庫状況情報に該当荷主が1件しかない場合は初期表示を行います。<BR>
     * 
     * @param p 検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException
     *             全ての例外を報告します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        int count = count(p);
        if (count == 1)
        {
            Params[] outParam = queryConsignor(p);
            if (!ArrayUtil.isEmpty(outParam))
            {
                return outParam[0];
            }
        }
        return null;
    }

    /**
     * PCT出庫作業件数を取得する。<BR>
     * @param p 検索条件パラメータ
     * @return 対象データの件数
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    public int count(ScheduleParams p)
            throws CommonException
    {

        // PCT出庫作業情報ハンドラ
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey shKy = new PCTRetPlanSearchKey();

        // 荷主コード
        if (!StringUtil.isBlank(p.getString(CONSIGNOR_CODE)))
        {
            shKy.setConsignorCode(p.getString(CONSIGNOR_CODE));
        }
        // 予定エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選ばれている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                shKy.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // 作業状態(未削除)
        shKy.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        shKy.setConsignorCodeGroup();

        return handler.count(shKy);
    }

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
        List<Params> outParams = new ArrayList<Params>();

        String[] planNo = standardArea(p);

        if (StringUtil.isBlank(planNo[0]))
        {
            return null;
        }

        p.set(AREA_NO, planNo[0]);
        p.set(ZONE_NO, planNo[1]);

        // ボタン制御を行います。
        String button = buttonControl(p);

        // フロア別データを取得します。
        outParams = setFloorUnitData(p, button);

        // 6001013=表示しました。
        setMessage("6001013");

        return outParams;
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
     * PCT出庫作業情報より荷主コードと名称を取得します。<BR>
     * @param param 検索条件パラメータ
     * @return 表示パラメータ データがない場合、要素数0の配列を返す
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowします。
     */
    protected Params[] queryConsignor(ScheduleParams p)
            throws CommonException
    {

        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey shKy = new PCTRetPlanSearchKey();
        // 荷主コード
        if (!StringUtil.isBlank(p.getString(CONSIGNOR_CODE)))
        {
            shKy.setConsignorCode(p.getString(CONSIGNOR_CODE));
        }
        // 作業状態(未削除)
        shKy.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        // 集約条件
        shKy.setConsignorCodeGroup();
        // 取得項目
        shKy.setConsignorCodeCollect();
        shKy.setCollect(PCTRetPlan.CONSIGNOR_NAME, "MAX", _CONSIGNOR_NAME);
        // 検索結果を取得
        PCTRetPlan[] entity = (PCTRetPlan[])handler.find(shKy);
        if (entity == null || entity.length == 0)
        {
            return null;
        }

        // set input parameters.
        List<Params> inparamList = new ArrayList<Params>();

        for (int i = 0; i < entity.length; i++)
        {
            Params lineparam = new Params();
            lineparam.set(CONSIGNOR_CODE, entity[i].getConsignorCode());
            lineparam.set(CONSIGNOR_NAME, entity[i].getConsignorName());
            inparamList.add(lineparam);
        }
        Params[] inparams = new Params[inparamList.size()];
        inparamList.toArray(inparams);

        return inparams;
    }


    /**
     * 基準日を基に最小のエリアNo.を取得します。
     * @param inParam PCTRetrievalInParameter
     * @return 基準となる最小のバッチNo.
     * @throws CommonException 全ての例外をスローします。
     */
    protected String[] standardArea(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫作業情報ハンドラ
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey shKy = new PCTRetPlanSearchKey();

        shKy.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 検索条件

        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            shKy.setBatchNo(p.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            shKy.setBatchSeqNo(p.getString(BATCH_SEQ_NO));
        }
        // 予定エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            shKy.setPlanAreaNo(p.getString(AREA_NO));
        }

        // ゾーンNo.
        if (!StringUtil.isBlank(p.getString(ZONE_NO)))
        {
            shKy.setPlanZoneNo(p.getString(ZONE_NO));
        }

        shKy.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

        shKy.setPlanAreaNoCollect();
        shKy.setPlanAreaNoGroup();
        shKy.setPlanAreaNoOrder(true);

        shKy.setPlanZoneNoCollect();
        shKy.setPlanZoneNoGroup();
        shKy.setPlanZoneNoOrder(true);

        // 検索結果を取得
        PCTRetPlan[] entity = (PCTRetPlan[])handler.find(shKy, 1);

        String[] planNo = new String[2];

        if (entity == null || entity.length == 0)
        {
            return planNo;
        }
        planNo[0] = entity[0].getPlanAreaNo();
        planNo[1] = entity[0].getPlanZoneNo();

        return planNo;
    }

    /**
     * 基準日の前後データ数を取得し前頁、次頁の制御を行います
     * 
     * @param inParam
     *            表示基準日
     * @return btnControl ボタン制御フラグ
     * @throws CommonException
     *             全ての例外をスローします。
     */
    protected String buttonControl(ScheduleParams p)
            throws CommonException
    {
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey lowShKy = new PCTRetPlanSearchKey();
        PCTRetPlanSearchKey highShKy = new PCTRetPlanSearchKey();
        int lowData = 0;
        int highData = 0;

        lowShKy.clearKeys();
        // 荷主コード
        lowShKy.setConsignorCode(p.getString(CONSIGNOR_CODE));

        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            lowShKy.setBatchNo(p.getString(BATCH_NO), "=");
        }
        // バッチseqNo.
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            lowShKy.setBatchSeqNo(p.getString(BATCH_SEQ_NO), "=");
        }
        // 予定エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選ばれている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                lowShKy.setPlanAreaNo(p.getString(AREA_NO), "=");
            }
        }
        // ゾーンNo.
        if (!StringUtil.isBlank(p.getString(ZONE_NO)))
        {
            lowShKy.setPlanZoneNo(p.getString(ZONE_NO), "<");
        }
        lowShKy.setPlanAreaNoGroup();
        lowShKy.setPlanZoneNoGroup();
        lowShKy.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        lowData = handler.count(lowShKy);

        lowShKy.clearKeys();
        // 荷主コード
        lowShKy.setConsignorCode(p.getString(CONSIGNOR_CODE));

        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            lowShKy.setBatchNo(p.getString(BATCH_NO), "=");
        }
        // バッチseqNo.
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            lowShKy.setBatchSeqNo(p.getString(BATCH_SEQ_NO), "=");
        }
        // 予定エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選ばれている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                lowShKy.setPlanAreaNo(p.getString(AREA_NO), "<");
            }
        }
        lowShKy.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        lowData = lowData + handler.count(lowShKy);

        // 基準日より大きいデータ件数を取得します。
        highShKy.setConsignorCode(p.getString(CONSIGNOR_CODE), "=");

        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            highShKy.setBatchNo(p.getString(BATCH_NO), "=");
        }
        // バッチSeqNo
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            highShKy.setBatchSeqNo(p.getString(BATCH_SEQ_NO), "=");
        }
        // 予定エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選ばれている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                highShKy.setPlanAreaNo(p.getString(AREA_NO), "=");
            }
        }
        // ゾーンNo.
        if (!StringUtil.isBlank(p.getString(ZONE_NO)))
        {
            highShKy.setPlanZoneNo(p.getString(ZONE_NO), ">");
        }

        // 状態
        highShKy.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

        highShKy.setPlanAreaNoGroup();
        highShKy.setPlanZoneNoGroup();
        highData = handler.count(highShKy);

        highShKy.clear();
        highShKy.setConsignorCode(p.getString(CONSIGNOR_CODE), "=");

        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            highShKy.setBatchNo(p.getString(BATCH_NO), "=");
        }
        // バッチSeqNo
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            highShKy.setBatchSeqNo(p.getString(BATCH_SEQ_NO), "=");
        }
        // 予定エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選ばれている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                highShKy.setPlanAreaNo(p.getString(AREA_NO), ">");
            }
        }

        // 状態
        highShKy.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        highShKy.setPlanAreaNoGroup();
        highShKy.setPlanZoneNoGroup();

        highData = highData + handler.count(highShKy);

        if (PCTRetrievalInParameter.PROCESS_FLAG_VIEW.equals(p.getString(PROCESS_FLAG)))
        {
            // 前後データ件数が0件の場合
            if (lowData == 0 && highData < MAX_DISPLAY_PROGRESS)
            {
                // 前後ボタン使用不可
                return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_ALL_OFF;
            }
            // 前データが0件の場合
            if (lowData == 0 && highData >= MAX_DISPLAY_PROGRESS)
            {
                // 前頁ボタン使用不可
                return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF;
            }
            // 後データが表示可能件数より少ない
            if (lowData > 0 && highData < MAX_DISPLAY_PROGRESS)
            {
                // 次頁ボタン使用不可
                return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF;
            }
        }
        else if (PCTRetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            if (lowData <= MAX_DISPLAY_PROGRESS)
            {
                // 前頁ボタン使用不可
                return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF;
            }
        }
        else if (PCTRetrievalInParameter.PROCESS_FLAG_NEXT_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            if (highData <= MAX_DISPLAY_PROGRESS)
            {
                // 次頁ボタン使用不可
                return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF;
            }
        }
        return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_ALL_ON;
    }

    /**
     * フロア別データを取得します。<BR>
     * @param inParam 検索パラメータ
     * @return 表示データ
     * @throws CommonException 全ての例外をスローします。
     */
    protected List<Params> setFloorUnitData(ScheduleParams p, String button)
            throws CommonException
    {
        // PCT出庫予定情報ハンドラ
        PCTRetPlan[] planEntity = null;

        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;

        // 検索結果を取得
        planEntity = areaZoneUnitSql(p);
        if (ArrayUtil.isEmpty(planEntity))
        {
            return null;
        }

        // 配列の順序を逆順にします。
        if (PCTRetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            planEntity = turnArray(planEntity);
        }

        List<Params> outParams = new ArrayList<Params>();

        // エリア毎に出力データを取得します。
        for (int i = 0; i < planEntity.length; i++)
        {
            Params param = new Params();

            // 1.エリアNo
            param.set(AREA_NO, planEntity[i].getPlanAreaNo());
            // 2.ゾーン
            param.set(ZONE_NO, planEntity[i].getPlanZoneNo());

            // レコード毎の検索条件をセットします。
            StringBuffer whereSql = new StringBuffer();
            whereSql.append(" WHERE ");
            whereSql.append("    DNPCTRETPLAN.CONSIGNOR_CODE = ").append(DBFormat.format(p.getString(CONSIGNOR_CODE)));
            whereSql.append("    AND DNPCTRETPLAN.PLAN_AREA_NO = ").append(
                    DBFormat.format(planEntity[i].getPlanAreaNo()));
            whereSql.append("    AND DNPCTRETPLAN.PLAN_ZONE_NO = ").append(
                    DBFormat.format(planEntity[i].getPlanZoneNo()));
            // バッチSeqNo
            if (!StringUtil.isBlank(p.getString(BATCH_NO)))
            {
                whereSql.append("    AND DNPCTRETPLAN.BATCH_NO = ").append(DBFormat.format(p.getString(BATCH_NO)));
            }
            // バッチSeqNo
            if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
            {
                whereSql.append("    AND DNPCTRETPLAN.BATCH_SEQ_NO = ").append(
                        DBFormat.format(p.getString(BATCH_SEQ_NO)));
            }
            whereSql.append("     AND DNPCTRETPLAN.STATUS_FLAG != '").append(PCTRetrievalInParameter.STATUS_FLAG_DELETE).append(
                    "'");

            // 3.行数を取得
            // 予定行数=DNPCTRETPLAN.レコードの件数
            // 実績行数=状態フラグが完了のDNPCTRETPLAN.レコードの件数
            // 4.ロット数を取得
            // 予定ロット数=DNPCTRETPLAN.予定数;
            // 実績ロット数=DNPCTRETPLAN.実績数 + DNPCTRETPLAN.欠品数;
            StringBuffer sql = getLineCntSql(whereSql);

            try
            {
                ddbHandler = new DefaultDDBHandler(getConnection());
                // SQLの実行
                Entity[] countEntity = ddbHandler.getEntities(1, new PCTRetPlan());
                ddbHandler.execute(String.valueOf(sql));
                countEntity = ddbHandler.getEntities(1, new PCTRetPlan());
                int planLineCnt = countEntity[0].getBigDecimal(new FieldName("", "PLAN_LINE_CNT")).intValue();
                int resultLineCnt = countEntity[0].getBigDecimal(new FieldName("", "RESULT_LINE_CNT")).intValue();
                int planQty = countEntity[0].getBigDecimal(new FieldName("", "PLAN_QTY")).intValue();
                int resultQty = countEntity[0].getBigDecimal(new FieldName("", "RESULT_QTY")).intValue();
                int shortageQty = countEntity[0].getBigDecimal(new FieldName("", "SHORTAGE_QTY")).intValue();

                String lineCnt = "";
                String lotCnt = "";
                
                lineCnt =
                        WmsFormatter.getNumFormat(resultLineCnt).concat("/").concat(
                                WmsFormatter.getNumFormat(planLineCnt));

                param.set(LINE_COUNT, lineCnt);

                lotCnt =
                        WmsFormatter.getNumFormat((resultQty + shortageQty)).concat("/").concat(
                                WmsFormatter.getNumFormat(planQty));

                param.set(LOT_COUNT, lotCnt);

                // 5.進捗率を取得
                double productionRate = 0;
                BigDecimal decResultQty = new BigDecimal(resultQty + shortageQty);
                BigDecimal decPlanQty = new BigDecimal(planQty);

                BigDecimal dbQtyPer = new BigDecimal(0);
                if ((resultQty + shortageQty) > 0 && planQty > 0)
                {
                    dbQtyPer =
                            decResultQty.divide(decPlanQty, 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                    productionRate = dbQtyPer.divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                param.set(PROGRESS_RATE, productionRate);

            }
            catch (Exception ex)
            {
                throw new ReadWriteException(ex);
            }
            finally
            {
                if (ddbHandler != null)
                {
                    ddbHandler.close();
                }
            }
            param.set(BUTTON_CONTROL_FLAG, button);

            outParams.add(param);

        }

        return outParams;
    }

    /**
     * エリアNo.とゾーンNo.を取得するメソッドです。<BR>
     * 表示ボタン、前頁ボタン、次頁ボタンの押下時に表示するエリアNo.とゾーンNo.を取得します。<BR>
     * 
     * @param inParam
     *            検索パラメータ
     * @return 表示用パラメータ
     * @throws CommonException
     *             全ての例外をスローします。
     */
    protected PCTRetPlan[] areaZoneUnitSql(ScheduleParams p)
            throws CommonException
    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = null;

        StringBuffer sql = null;
        // 検索条件をセット
        if (PCTRetrievalInParameter.PROCESS_FLAG_VIEW.equals(p.getString(PROCESS_FLAG)))
        {
            sql = new StringBuffer();
            sql.append("SELECT ");
            sql.append("        DNPCTRETPLAN.PLAN_AREA_NO PLAN_AREA_NO");
            sql.append("        ,DNPCTRETPLAN.PLAN_ZONE_NO PLAN_ZONE_NO");
            sql.append("    FROM");
            sql.append("        DNPCTRETPLAN");
            sql.append("    WHERE");
            sql.append("        ((DNPCTRETPLAN.PLAN_AREA_NO = ").append(DBFormat.format(p.getString(AREA_NO)));
            sql.append("        AND DNPCTRETPLAN.PLAN_ZONE_NO >= ").append(DBFormat.format(p.getString(ZONE_NO))).append(
                    ")");
            sql.append("        OR DNPCTRETPLAN.PLAN_AREA_NO > ").append(DBFormat.format(p.getString(AREA_NO))).append(
                    ")");
            sql.append("        AND DNPCTRETPLAN.CONSIGNOR_CODE = ").append(
                    DBFormat.format(p.getString(CONSIGNOR_CODE)));
            // ﾊﾞｯﾁNo.
            if (!StringUtil.isBlank(p.getString(BATCH_NO)))
            {
                sql.append("  AND DNPCTRETPLAN.BATCH_NO = ").append(DBFormat.format(p.getString(BATCH_NO)));
            }
            // ﾊﾞｯﾁSeqNo.
            if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
            {
                sql.append("  AND DNPCTRETPLAN.BATCH_SEQ_NO = ").append(DBFormat.format(p.getString(BATCH_SEQ_NO)));
            }
            sql.append("        AND DNPCTRETPLAN.STATUS_FLAG != '").append(PCTRetrievalInParameter.STATUS_FLAG_DELETE).append(
                    "'");
            sql.append("    GROUP BY");
            sql.append("        DNPCTRETPLAN.PLAN_AREA_NO");
            sql.append("        ,DNPCTRETPLAN.PLAN_ZONE_NO");
            sql.append("    ORDER BY");
            sql.append("        DNPCTRETPLAN.PLAN_AREA_NO ASC");
            sql.append("        ,DNPCTRETPLAN.PLAN_ZONE_NO ASC");
        }
        else if (PCTRetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            sql = new StringBuffer();
            sql.append("SELECT ");
            sql.append("        DNPCTRETPLAN.PLAN_AREA_NO PLAN_AREA_NO");
            sql.append("        ,DNPCTRETPLAN.PLAN_ZONE_NO PLAN_ZONE_NO");
            sql.append("    FROM");
            sql.append("        DNPCTRETPLAN");
            sql.append("    WHERE");
            sql.append("        ((DNPCTRETPLAN.PLAN_AREA_NO = ").append(DBFormat.format(p.getString(AREA_NO)));
            sql.append("        AND DNPCTRETPLAN.PLAN_ZONE_NO < ").append(DBFormat.format(p.getString(ZONE_NO))).append(
                    ")");
            sql.append("        OR DNPCTRETPLAN.PLAN_AREA_NO < ").append(DBFormat.format(p.getString(AREA_NO))).append(
                    ")");
            sql.append("        AND DNPCTRETPLAN.CONSIGNOR_CODE = ").append(
                    DBFormat.format(p.getString(CONSIGNOR_CODE)));
            // ﾊﾞｯﾁNo.
            if (!StringUtil.isBlank(p.getString(BATCH_NO)))
            {
                sql.append("  AND DNPCTRETPLAN.BATCH_NO = ").append(DBFormat.format(p.getString(BATCH_NO)));
            }
            // ﾊﾞｯﾁSeqNo.
            if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
            {
                sql.append("  AND DNPCTRETPLAN.BATCH_SEQ_NO = ").append(DBFormat.format(p.getString(BATCH_SEQ_NO)));
            }
            sql.append("        AND DNPCTRETPLAN.STATUS_FLAG != '").append(PCTRetrievalInParameter.STATUS_FLAG_DELETE).append(
                    "'");
            sql.append("    GROUP BY");
            sql.append("        DNPCTRETPLAN.PLAN_AREA_NO");
            sql.append("        ,DNPCTRETPLAN.PLAN_ZONE_NO");
            sql.append("    ORDER BY");
            sql.append("        DNPCTRETPLAN.PLAN_AREA_NO DESC");
            sql.append("        ,DNPCTRETPLAN.PLAN_ZONE_NO DESC");
        }
        else if (PCTRetrievalInParameter.PROCESS_FLAG_NEXT_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            sql = new StringBuffer();
            sql.append("SELECT ");
            sql.append("        DNPCTRETPLAN.PLAN_AREA_NO PLAN_AREA_NO");
            sql.append("        ,DNPCTRETPLAN.PLAN_ZONE_NO PLAN_ZONE_NO");
            sql.append("    FROM");
            sql.append("        DNPCTRETPLAN");
            sql.append("    WHERE");
            sql.append("        ((DNPCTRETPLAN.PLAN_AREA_NO = ").append(DBFormat.format(p.getString(AREA_NO)));
            sql.append("        AND DNPCTRETPLAN.PLAN_ZONE_NO > ").append(DBFormat.format(p.getString(ZONE_NO))).append(
                    ")");
            sql.append("        OR DNPCTRETPLAN.PLAN_AREA_NO > ").append(DBFormat.format(p.getString(AREA_NO))).append(
                    ")");
            sql.append("        AND DNPCTRETPLAN.CONSIGNOR_CODE = ").append(
                    DBFormat.format(p.getString(CONSIGNOR_CODE)));
            // ﾊﾞｯﾁNo.
            if (!StringUtil.isBlank(p.getString(BATCH_NO)))
            {
                sql.append("  AND DNPCTRETPLAN.BATCH_NO = ").append(DBFormat.format(p.getString(BATCH_NO)));
            }
            // ﾊﾞｯﾁSeqNo.
            if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
            {
                sql.append("  AND DNPCTRETPLAN.BATCH_SEQ_NO = ").append(DBFormat.format(p.getString(BATCH_SEQ_NO)));
            }
            sql.append("        AND DNPCTRETPLAN.STATUS_FLAG != '").append(PCTRetrievalInParameter.STATUS_FLAG_DELETE).append(
                    "'");
            sql.append("    GROUP BY");
            sql.append("        DNPCTRETPLAN.PLAN_AREA_NO");
            sql.append("        ,DNPCTRETPLAN.PLAN_ZONE_NO");
            sql.append("    ORDER BY");
            sql.append("        DNPCTRETPLAN.PLAN_AREA_NO ASC");
            sql.append("        ,DNPCTRETPLAN.PLAN_ZONE_NO ASC");
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
     * 配列の順序を逆順にします<BR>
     * @param array 対象配列
     * @return 逆順にした配列
     */
    protected PCTRetPlan[] turnArray(PCTRetPlan[] array)
    {
        int index = 0;
        PCTRetPlan[] subArray = new PCTRetPlan[array.length];
        for (int i = subArray.length - 1; i >= 0; i--)
        {
            subArray[index++] = array[i];
        }
        return subArray;
    }

    /**
     * 行数、ロット数取得SQLを返します
     * 
     * @param whereSql 検索条件
     * @return 行数、ロット数取得SQL
     */
    protected StringBuffer getLineCntSql(StringBuffer whereSql)
            throws CommonException
    {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("    COUNT(DNPCTRETPLAN.PLAN_UKEY) PLAN_LINE_CNT ");
        sql.append("    ,SUM(DNPCTRETPLAN.PLAN_QTY) PLAN_QTY ");
        sql.append("    ,SUM(DNPCTRETPLAN.RESULT_QTY) RESULT_QTY ");
        sql.append("    ,SUM(DNPCTRETPLAN.SHORTAGE_QTY) SHORTAGE_QTY ");
        sql.append("    ,SUM(DNPCTRETPLAN.RESULT_LINE_CNT) RESULT_LINE_CNT ");
        sql.append("FROM ");
        sql.append("    (SELECT ");
        sql.append("        DNPCTRETPLAN.* ");
        sql.append("        ,CASE DNPCTRETPLAN.STATUS_FLAG ");
        sql.append("            WHEN '").append(PCTRetrievalInParameter.BATCHSTATUS_FLAG_COMPLETION).append("' ");
        sql.append("            THEN 1 ");
        sql.append("            ELSE 0 END RESULT_LINE_CNT ");
        sql.append("    FROM ");
        sql.append("        DNPCTRETPLAN ").append(whereSql);
        sql.append("    ) DNPCTRETPLAN ");

        return sql;
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
