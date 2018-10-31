// $Id: PCTFloorProgressSCH.java 6900 2010-01-25 11:21:11Z kumano $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.pcart.retrieval.schedule.PCTFloorProgressSCHParams.*;

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
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * エリア別作業進捗(グラフなし)のスケジュール処理を行います。
 *
 * @version $Revision: 6900 $, $Date:: 2010-01-25 20:21:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class PCTFloorProgressSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // private static String $classVar ;
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
    public PCTFloorProgressSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
     * @param p 検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException 全ての例外を報告します。
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

        // フロア別データを取得します。
        outParams = setFloorUnitData(p);

        if (outParams == null || outParams.size() == 0)
        {
            return null;
        }

        // 取得件数に応じてメッセージを設定
        canLowerDisplay(outParams.size());

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
     * @param p 検索条件パラメータ
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
     * フロア別データを取得します。<BR>
     * @param inParam 検索パラメータ
     * @return 表示データ
     * @throws CommonException 全ての例外をスローします。
     */
    protected List<Params> setFloorUnitData(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey planshKy = new PCTRetPlanSearchKey();
        PCTRetPlan[] planEntity = null;

        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = new DefaultDDBHandler(getConnection());

        // 検索条件をセット
        // 検索キー初期化
        planshKy.clear();
        // 荷主コード
        planshKy.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // バッチNo
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            planshKy.setBatchNo(p.getString(BATCH_NO));
        }
        // バッチSeqNo
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            planshKy.setBatchSeqNo(p.getString(BATCH_SEQ_NO));
        }
        planshKy.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        planshKy.setPlanAreaNoCollect();
        planshKy.setPlanZoneNoCollect();
        planshKy.setPlanAreaNoGroup();
        planshKy.setPlanZoneNoGroup();
        planshKy.setPlanAreaNoOrder(true);
        planshKy.setPlanZoneNoOrder(true);

        // 検索結果を取得
        planEntity = (PCTRetPlan[])planHandler.find(planshKy);
        if (planEntity == null || planEntity.length == 0)
        {
            return null;
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
            // バッチNo
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
                BigDecimal decResultQty = new BigDecimal((resultQty + shortageQty));
                BigDecimal decPlanQty = new BigDecimal(planQty);

                BigDecimal dbQtyPer = new BigDecimal(0);
                if ((resultQty + shortageQty) > 0 && planQty > 0)
                {
                    dbQtyPer =
                            decResultQty.divide(decPlanQty, 3, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
                    productionRate = dbQtyPer.divide(new BigDecimal(1), 3, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                param.set(PROGRESS_RATE, WmsFormatter.toProductionRate(productionRate));

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
            outParams.add(param);
        }

        return outParams;
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
