// $Id: PCTCustomerProgressVLGraphSCH.java 6900 2010-01-25 11:21:11Z kumano $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.schedule.PCTCustomerProgressVLGraphSCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * 出荷先別作業進捗(縦棒)のスケジュール処理を行います。
 *
 * Designer : H.Okayama<BR>
 * Maker : H.Okayama<BR>
 * @version $Revision: 6900 $, $Date:: 2010-01-25 20:21:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class PCTCustomerProgressVLGraphSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * リストセルソート時のダミーデータ
     */
    protected static final String SORT_DUMMY_ADD_VAL = "00000";

    /**
     * リストセルソート時の生産率最大長(ZZ9.9)
     */
    protected static final int SORT_MAX_LEN_PRODUCTIONRATE = 3 + 1 + 1;

    /**
     * リストセルソート時のダミーデータ
     */
    protected static final String SORT_DUMMY_ADD_CUSTOMER = "0000000000000000";

    /**
     * リストセルソート時の出荷先コード最大長(XXXXXXXXXXXXXXXX)
     */
    protected static final int SORT_MAX_LEN_CUSTOMER = 16;

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
    public PCTCustomerProgressVLGraphSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // 出荷先毎のWHERE句保持
        StringBuffer whereSql = new StringBuffer();

        // カウント変数
        int i = 0;

        // 出荷先毎のデータを取得
        PCTRetPlan[] entities = getSeparateCustomer(p);
        if (ArrayUtil.isEmpty(entities))
        {
            return new ArrayList<Params>();
        }

        PCTRetrievalOutParameter[] arrayParam = new PCTRetrievalOutParameter[entities.length];
        // 出荷先毎にデータを取得します。
        for (PCTRetPlan ent : entities)
        {
            // パラメータの生成
            arrayParam[i] = new PCTRetrievalOutParameter();

            // 情報抽出SQLのWHERE句を生成
            whereSql = createWhereSql(ent.getCustomerCode());

            // オーダー件数の取得
            Entity[] countEntity = getOrderCountEntity(whereSql);
            int planOrderCount = countEntity[0].getBigDecimal(new FieldName("", "PLAN_ORDER_COUNT")).intValue();
            int resultOrderCount = countEntity[0].getBigDecimal(new FieldName("", "RESULT_ORDER_COUNT")).intValue();

            // 箱数の取得
            int resultBoxCount = getBoxCount(ent.getCustomerCode());

            // 行数・ロット数の取得
            countEntity = null;
            countEntity = getLineCountEntity(whereSql);
            int planLineCount = countEntity[0].getBigDecimal(new FieldName("", "PLAN_LINE_COUNT")).intValue();
            int resultLineCount = countEntity[0].getBigDecimal(new FieldName("", "RESULT_LINE_COUNT")).intValue();
            int planQty = countEntity[0].getBigDecimal(new FieldName("", "PLAN_QTY")).intValue();
            int resultQty = countEntity[0].getBigDecimal(new FieldName("", "RESULT_QTY")).intValue();
            int shortageQty = countEntity[0].getBigDecimal(new FieldName("", "SHORTAGE_QTY")).intValue();

            // パラメータのセット
            // 出荷先コード
            arrayParam[i].setCustomerCode(ent.getCustomerCode());
            // 出荷先名称
            arrayParam[i].setCustomerName(ent.getCustomerName());
            // 予定オーダー数
            arrayParam[i].setPlanOrderCnt(planOrderCount);
            // 完了オーダー数
            arrayParam[i].setResultOrderCnt(resultOrderCount);
            // 箱数
            arrayParam[i].setBoxCnt(resultBoxCount);
            // 予定行数
            arrayParam[i].setPlanLineCnt(planLineCount);
            // 実績行数
            arrayParam[i].setResultLineCnt(resultLineCount);
            // 予定ロット数
            arrayParam[i].setPlanLotCnt(planQty);
            // 実績ロット数
            arrayParam[i].setResultLotCnt((resultQty + shortageQty));

            // リスト件数
            i++;
        }
        // (6001013)表示しました。
        setMessage("6001013");

        // 取得したデータを並び替えて返却
        return getSortList(arrayParam);
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
     * 出庫予定情報に存在する出荷先毎のデータを取得します。
     * 
     * @param p 画面情報
     * @return PCTRetPlan[] 検索結果
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected PCTRetPlan[] getSeparateCustomer(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫予定情報エンティティ
        PCTRetPlan[] ent = null;
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();

        // 取得項目
        // 出荷先コード
        planSKey.setCustomerCodeCollect();
        // 出荷先名称
        planSKey.setCollect(PCTRetPlan.CUSTOMER_NAME, "MAX", PCTRetPlan.CUSTOMER_NAME);

        // 状態フラグ
        planSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

        // 集約条件
        // 出荷先コード
        planSKey.setCustomerCodeGroup();

        // 表示順
        // 出荷先コード
        planSKey.setCustomerCodeOrder(true);

        // 検索実行
        ent = (PCTRetPlan[])planHandler.find(planSKey);
        if (ArrayUtil.isEmpty(ent))
        {
            return null;
        }
        return ent;
    }

    /**
     * パラメータ毎のWHERE句を生成します。
     * 
     * @param p 画面情報
     * @param customerCode 出荷先コード
     * @return StringBuffer SQLのWHERE句
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected StringBuffer createWhereSql(String customerCode)
            throws CommonException
    {
        // 返却文字列の生成
        StringBuffer where = new StringBuffer();

        // 検索条件
        where.append(" WHERE ");
        // 出荷先コード
        where.append("  DNPCTRETPLAN.CUSTOMER_CODE = ");
        where.append(DBFormat.format(customerCode)).append(" ");
        // 状態フラグ
        where.append("  AND DNPCTRETPLAN.STATUS_FLAG != ");
        where.append(DBFormat.format(PCTRetrievalInParameter.STATUS_FLAG_DELETE)).append(" ");

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
        // 情報取得SQL
        StringBuffer sql = new StringBuffer();

        // SQLの生成
        // 取得項目
        sql.append("SELECT ");
        sql.append(" COUNT(DNPCTRETPLAN.PLAN_ORDER_NO) PLAN_ORDER_COUNT ");
        sql.append(" ,SUM(DNPCTRETPLAN.RESULT_ORDER_COUNT) RESULT_ORDER_COUNT ");
        // 対象テーブル
        sql.append("FROM ");
        sql.append(" (SELECT ");
        sql.append("   DNPCTRETPLAN.PLAN_ORDER_NO ");
        sql.append("   ,CASE MIN(DNPCTRETPLAN.STATUS_FLAG) ");
        sql.append("    WHEN ").append(DBFormat.format(PCTRetrievalInParameter.BATCHSTATUS_FLAG_COMPLETION)).append(" ");
        sql.append("    THEN 1 ");
        sql.append("   ELSE 0 END RESULT_ORDER_COUNT ");
        sql.append("  FROM ");
        sql.append("   DNPCTRETPLAN ").append(where);
        sql.append("  GROUP BY DNPCTRETPLAN.PLAN_ORDER_NO ");
        sql.append(" ) DNPCTRETPLAN ");

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
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
     * @param customerCode 出荷先コード
     * @return int 箱数
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected int getBoxCount(String customerCode)
            throws CommonException
    {
        // PCT出庫作業情報検索キー
        PCTRetWorkInfoSearchKey workSKey = new PCTRetWorkInfoSearchKey();
        // PCT出庫作業情報ハンドラ
        PCTRetWorkInfoHandler workHandler = new PCTRetWorkInfoHandler(getConnection());

        // 取得項目
        // 実績オーダーNo.
        workSKey.setResultOrderNoCollect();

        // 検索条件
        // 出荷先コード
        workSKey.setCustomerCode(customerCode);
        // 状態フラグ(完了 or メンテ完了)
        workSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION, "=", "(", "", false);
        workSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION, "=", "", ")", false);

        // SQL結果件数の返却
        return workHandler.count(workSKey);
    }

    /**
     * 行数・ロット数を取得します。
     * 
     * @param where WHERE句
     * @return Entity[] 検索結果
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected Entity[] getLineCountEntity(StringBuffer where)
            throws CommonException
    {
        // ダイレクトDBハンドラ
        DefaultDDBHandler ddbHandler = new DefaultDDBHandler(getConnection());
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
        sql.append("    WHEN ").append(DBFormat.format(PCTRetrievalInParameter.BATCHSTATUS_FLAG_COMPLETION)).append(" ");
        sql.append("    THEN 1 ");
        sql.append("   ELSE 0 END RESULT_LINE_COUNT ");
        sql.append("  FROM ");
        sql.append("   DNPCTRETPLAN ").append(where);
        sql.append(" ) DNPCTRETPLAN ");

        try
        {
            ddbHandler = new DefaultDDBHandler(getConnection());
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
     * リスト内データの並び替えを行います。
     * 
     * @param param 取得データ
     * @return List<Params> 並び替え後リスト
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected List<Params> getSortList(PCTRetrievalOutParameter[] param)
            throws CommonException
    {
        // テンポラリ
        TreeMap<String, PCTRetrievalOutParameter> tempMap = new TreeMap<String, PCTRetrievalOutParameter>();
        // ソート
        TreeMap<String, PCTRetrievalOutParameter> sortMap = new TreeMap<String, PCTRetrievalOutParameter>();

        // キー(ソート用)
        String sortKey = null;
        // 進捗率(ソート用)
        String sortRate = null;
        // 出荷先コード(ソート用)
        String sortCustomer = null;

        // パラメータ分繰り返す
        // ここでは出荷先コードの桁数を合わせる
        for (int i = 0; i < param.length; i++)
        {
            // 0埋め(16桁) + 出荷先コード
            sortCustomer = SORT_DUMMY_ADD_CUSTOMER + param[i].getCustomerCode();
            // 上記文字列から(出荷先コード - 出荷先コード(最大桁))の文字列を抜き出す 
            sortCustomer = sortCustomer.substring(sortCustomer.length() - SORT_MAX_LEN_CUSTOMER);
            // 上記文字列を出荷先コード(ソート用)とする
            sortKey = sortCustomer;
            // 出荷先コード(ソート用)をキーとして現在のデータを格納
            tempMap.put(sortKey, param[i]);
        }

        // 並び替えたMapをパラメータ形式に変更
        PCTRetrievalOutParameter[] tempParam = new PCTRetrievalOutParameter[param.length];
        int loop = 0;
        Iterator ittemp = tempMap.keySet().iterator();
        while (ittemp.hasNext())
        {
            Object object = ittemp.next();
            tempParam[loop] = (PCTRetrievalOutParameter)tempMap.get(object);
            loop++;
        }

        // 上記で並び替えた情報を更に進捗率で並び替え
        String strLoop = null;
        for (int i = 0; i < tempParam.length; i++)
        {
            sortRate = SORT_DUMMY_ADD_VAL + tempParam[i].getProductionRateVal();
            sortRate = sortRate.substring(sortRate.length() - SORT_MAX_LEN_PRODUCTIONRATE);
            strLoop = SORT_DUMMY_ADD_VAL + String.valueOf(i + 1);
            strLoop = strLoop.substring(strLoop.length() - SORT_MAX_LEN_PRODUCTIONRATE);
            sortKey = sortRate + strLoop;
            sortMap.put(sortKey, tempParam[i]);
        }

        // 並び替えたMapから返却パラメータに変換
        PCTRetrievalOutParameter[] sortParam = new PCTRetrievalOutParameter[tempParam.length];
        loop = 0;
        Iterator it = sortMap.keySet().iterator();
        List<Params> result = new ArrayList<Params>();

        while (it.hasNext())
        {
            Object object = it.next();
            sortParam[loop] = (PCTRetrievalOutParameter)sortMap.get(object);

            // 進捗率の取得
            BigDecimal progressRate =
                    getProgressRate((int)sortParam[loop].getPlanLotCnt(), (int)sortParam[loop].getResultLotCnt());

            // パラメータの生成
            Params listParam = new Params();
            // 出荷先コード
            listParam.set(CUSTOMER_CODE, sortParam[loop].getCustomerCode());
            // 出荷先名称
            listParam.set(CUSTOMER_NAME, sortParam[loop].getCustomerName());
            // 進捗率
            listParam.set(PROGRESS_RATE, progressRate);
            // オーダー件数
            String OrderCount =
                    WmsFormatter.getNumFormat(sortParam[loop].getResultOrderCnt()).concat("/").concat(
                            WmsFormatter.getNumFormat(sortParam[loop].getPlanOrderCnt()));
            listParam.set(ORDER_COUNT, OrderCount);
            // 箱数
            listParam.set(BOX_COUNT, sortParam[loop].getBoxCnt());
            // 行数
            String lineCount =
                    WmsFormatter.getNumFormat(sortParam[loop].getResultLineCnt()).concat("/").concat(
                            WmsFormatter.getNumFormat(sortParam[loop].getPlanLineCnt()));
            listParam.set(LINE_COUNT, lineCount);
            // ロット数
            String lotCount =
                    WmsFormatter.getNumFormat(sortParam[loop].getResultLotCnt()).concat("/").concat(
                            WmsFormatter.getNumFormat(sortParam[loop].getPlanLotCnt()));
            listParam.set(LOT_COUNT, lotCount);
            // ロット数
            listParam.set(LOT_QTY, sortParam[loop].getPlanLotCnt());

            // インクリメント
            loop++;

            // パラメータを配列に格納
            result.add(listParam);
        }
        // 生成したパラメータ配列を返却
        return result;
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
