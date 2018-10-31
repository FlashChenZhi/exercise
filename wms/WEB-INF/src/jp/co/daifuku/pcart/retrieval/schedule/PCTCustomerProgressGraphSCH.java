// $Id: PCTCustomerProgressGraphSCH.java 6900 2010-01-25 11:21:11Z kumano $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.pcart.retrieval.schedule.PCTCustomerProgressGraphSCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
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
import jp.co.daifuku.wms.base.common.WmsParam;
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
 * 出荷先別作業進捗(横棒)のスケジュール処理を行います。
 *
 * Designer : H.Okayama<BR>
 * Maker : H.Okayama<BR>
 * @version $Revision: 6900 $, $Date:: 2010-01-25 20:21:11 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class PCTCustomerProgressGraphSCH
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
    public PCTCustomerProgressGraphSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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

        // ループ変数
        int i = 0;

        // 出荷先コード(基準)
        String standardCustomerCode = getStandardCustomerCode(p);
        // ボタン制御を行います。
        String button = buttonControl(p, standardCustomerCode);

        // 出荷先毎のデータを取得
        PCTRetPlan[] entities = getSeparateCustomer(p, standardCustomerCode);
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
            whereSql = createWhereSql(p, ent.getCustomerCode());

            // オーダー件数の取得
            Entity[] countEntity = getOrderCountEntity(whereSql);
            int planOrderCount = countEntity[0].getBigDecimal(new FieldName("", "PLAN_ORDER_COUNT")).intValue();
            int resultOrderCount = countEntity[0].getBigDecimal(new FieldName("", "RESULT_ORDER_COUNT")).intValue();

            // 箱数の取得
            int resultBoxCount = getBoxCount(p, ent.getCustomerCode());

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
        return getSortList(arrayParam, button);
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
     * 画面にて指定された情報によりPCT出庫作業情報から
     * 出荷先コードの最小値を取得します。
     * 
     * @param p 画面情報
     * @return String 出荷先コード(最小値)
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected String getStandardCustomerCode(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫作業情報検索キー
        PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();
        // PCT出庫作業情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());

        // 取得項目
        // 出荷先コード
        planSKey.setCustomerCodeCollect();

        // 検索条件
        // 荷主コード
        planSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 得意先コード
        if (!StringUtil.isBlank(p.getString(REGULAR_CUSTOMER_CODE)))
        {
            planSKey.setRegularCustomerCode(p.getString(REGULAR_CUSTOMER_CODE));
        }
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                planSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            planSKey.setBatchNo(p.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            if (!WmsParam.ALL_BATCH_SEQ_NO.equals(p.getString(BATCH_SEQ_NO)))
            {
                planSKey.setBatchSeqNo(p.getString(BATCH_SEQ_NO));
            }
        }
        // 状態フラグ
        planSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        // 出荷先コード
        if (!StringUtil.isBlank(p.getString(CUSTOMER_CODE)))
        {
            planSKey.setCustomerCode(p.getString(CUSTOMER_CODE));
        }

        // 集約条件
        // 出荷先コード
        planSKey.setCustomerCodeGroup();

        // 表示条件
        // 出荷先コード
        planSKey.setCustomerCodeOrder(true);

        // 検索結果を取得
        PCTRetPlan[] entity = (PCTRetPlan[])planHandler.find(planSKey, 1);
        if (entity == null || entity.length == 0)
        {
            return null;
        }
        // 出荷先コード(最小値)を返却
        return entity[0].getCustomerCode();
    }

    /**
     * 基準日の前後データ数を取得し前頁、次頁の制御を行います
     * 
     * @param inParam 表示基準日
     * @return btnControl ボタン制御フラグ
     * @throws CommonException 全ての例外をスローします。
     */
    protected String buttonControl(ScheduleParams p, String customerCode)
            throws CommonException
    {
        // 前頁想定件数
        int lowData = getLowPlanInfo(p, customerCode);;
        // 次頁想定件数
        int highData = getHighPlanInfo(p, customerCode);

        // 表示ボタン押下時
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
        // 前頁ボタン押下時
        else if (PCTRetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            if (lowData <= MAX_DISPLAY_PROGRESS)
            {
                // 前頁ボタン使用不可
                return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_PREVIOUS_OFF;
            }
        }
        // 次頁ボタン押下時
        else if (PCTRetrievalInParameter.PROCESS_FLAG_NEXT_PAGE.equals(p.getString(PROCESS_FLAG)))
        {
            if (highData <= MAX_DISPLAY_PROGRESS)
            {
                // 次頁ボタン使用不可
                return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_NEXT_OFF;
            }
        }
        // 条件に合わない場合は全ボタン有効
        return PCTRetrievalOutParameter.BUTTON_CONTROL_FLAG_ALL_ON;
    }

    /**
     * 基準日前のデータを取得します。
     * 
     * @param p 画面情報
     * @param customerCode 出荷先コード(基準)
     * @retrun int 検索結果件数
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected int getLowPlanInfo(ScheduleParams p, String customerCode)
            throws CommonException
    {
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey lowPlanSKey = new PCTRetPlanSearchKey();
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());

        // 検索条件
        // 荷主コード
        lowPlanSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 得意先コード
        if (!StringUtil.isBlank(p.getString(REGULAR_CUSTOMER_CODE)))
        {
            lowPlanSKey.setRegularCustomerCode(p.getString(REGULAR_CUSTOMER_CODE), "=");
        }
        // 予定エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選ばれている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                lowPlanSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            lowPlanSKey.setBatchNo(p.getString(BATCH_NO));
        }
        // バッチseqNo.
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            if (!WmsParam.ALL_BATCH_SEQ_NO.equals(p.getString(BATCH_SEQ_NO)))
            {
                lowPlanSKey.setBatchSeqNo(p.getString(BATCH_SEQ_NO), "=");
            }
        }
        // 出荷先コード
        lowPlanSKey.setCustomerCode(customerCode, "<");
        // 状態フラグ
        lowPlanSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

        // 集約条件
        // 出荷先コード
        lowPlanSKey.setCustomerCodeGroup();

        // 検索を行い、該当件数を返却
        return planHandler.count(lowPlanSKey);
    }

    /**
     * 基準日後のデータを取得します。
     * 
     * @param p 画面情報
     * @param customerCode 出荷先コード(基準)
     * @retrun int 検索結果件数
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected int getHighPlanInfo(ScheduleParams p, String customerCode)
            throws CommonException
    {
        // PCT出庫予定情報検索キー
        PCTRetPlanSearchKey highPlanSKey = new PCTRetPlanSearchKey();
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());

        // 検索条件
        // 荷主コード
        highPlanSKey.setConsignorCode(p.getString(CONSIGNOR_CODE), "=");
        // 得意先コード
        if (!StringUtil.isBlank(p.getString(REGULAR_CUSTOMER_CODE)))
        {
            highPlanSKey.setRegularCustomerCode(p.getString(REGULAR_CUSTOMER_CODE), "=");
        }
        // 予定エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選ばれている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                highPlanSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            highPlanSKey.setBatchNo(p.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            if (!WmsParam.ALL_BATCH_SEQ_NO.equals(p.getString(BATCH_SEQ_NO)))
            {
                highPlanSKey.setBatchSeqNo(p.getString(BATCH_SEQ_NO), "=");
            }
        }
        // 出荷先コード
        highPlanSKey.setCustomerCode(customerCode, ">");
        // 状態フラグ
        highPlanSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

        // 集約条件
        // 出荷先コード
        highPlanSKey.setCustomerCodeGroup();

        // 検索を行い、該当件数を返却
        return planHandler.count(highPlanSKey);
    }

    /**
     * 出庫予定情報に存在する出荷先毎のデータを取得します。
     * 
     * @param p 画面情報
     * @param customerCode 出荷先コード(最小値)
     * @return PCTRetPlan[] 検索結果
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected PCTRetPlan[] getSeparateCustomer(ScheduleParams p, String customerCode)
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
        // 出荷先コード
        planSKey.setCustomerCodeCollect();
        // 出荷先名称
        planSKey.setCollect(PCTRetPlan.CUSTOMER_NAME, "MAX", PCTRetPlan.CUSTOMER_NAME);

        // 検索条件
        // 荷主コード
        planSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 得意先コード
        if (!StringUtil.isBlank(p.getString(REGULAR_CUSTOMER_CODE)))
        {
            planSKey.setRegularCustomerCode(p.getString(REGULAR_CUSTOMER_CODE));
        }
        // エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選択されている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                planSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            planSKey.setBatchNo(p.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            if (!WmsParam.ALL_BATCH_SEQ_NO.equals(p.getString(BATCH_SEQ_NO)))
            {
                planSKey.setBatchSeqNo(p.getString(BATCH_SEQ_NO));
            }
        }
        // 状態フラグ
        planSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        // 出荷先コード
        if (!StringUtil.isBlank(customerCode))
        {
            if (PCTRetrievalInParameter.PROCESS_FLAG_VIEW.equals(p.getString(PROCESS_FLAG)))
            {
                // 出荷先コード(以上)
                planSKey.setCustomerCode(customerCode, ">=");
                // 出荷先コード(昇順)
                planSKey.setCustomerCodeOrder(true);
            }
            else if (PCTRetrievalInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(p.getString(PROCESS_FLAG)))
            {
                // 出荷先コード(未満)
                planSKey.setCustomerCode(customerCode, "<");
                // 出荷先コード(降順)
                planSKey.setCustomerCodeOrder(false);
                // 前頁フラグを成立
                previousFlag = true;
            }
            else if (PCTRetrievalInParameter.PROCESS_FLAG_NEXT_PAGE.equals(p.getString(PROCESS_FLAG)))
            {
                // 出荷先コード(より上)
                planSKey.setCustomerCode(customerCode, ">");
                // 出荷先コード(昇順)
                planSKey.setCustomerCodeOrder(true);
            }
        }

        // 集約条件
        // 出荷先コード
        planSKey.setCustomerCodeGroup();

        // 検索実行
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
     * パラメータ毎のWHERE句を生成します。
     * 
     * @param p 画面情報
     * @param customerCode 出荷先コード
     * @return StringBuffer SQLのWHERE句
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected StringBuffer createWhereSql(ScheduleParams p, String customerCode)
            throws CommonException
    {
        // 返却文字列の生成
        StringBuffer where = new StringBuffer();

        // 検索条件
        where.append(" WHERE ");
        // 荷主コード
        where.append("  DNPCTRETPLAN.CONSIGNOR_CODE = ");
        where.append(DBFormat.format(p.getString(CONSIGNOR_CODE))).append(" ");
        // 出荷先コード
        where.append("  AND DNPCTRETPLAN.CUSTOMER_CODE = ");
        where.append(DBFormat.format(customerCode)).append(" ");
        // 状態フラグ
        where.append("  AND DNPCTRETPLAN.STATUS_FLAG != ");
        where.append(DBFormat.format(PCTRetrievalInParameter.STATUS_FLAG_DELETE)).append(" ");
        // 得意先コード
        if (!StringUtil.isBlank(p.getString(REGULAR_CUSTOMER_CODE)))
        {
            where.append(" AND DNPCTRETPLAN.REGULAR_CUSTOMER_CODE = ");
            where.append(DBFormat.format(p.getString(REGULAR_CUSTOMER_CODE))).append(" ");
        }
        // エリアno.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選択されている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                where.append(" AND DNPCTRETPLAN.PLAN_AREA_NO = ");
                where.append(DBFormat.format(p.getString(AREA_NO))).append(" ");
            }
        }
        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            where.append(" AND DNPCTRETPLAN.BATCH_NO = ");
            where.append(DBFormat.format(p.getString(BATCH_NO))).append(" ");
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            if (!WmsParam.ALL_BATCH_SEQ_NO.equals(p.getString(BATCH_SEQ_NO)))
            {
                where.append(" AND DNPCTRETPLAN.BATCH_SEQ_NO = ");
                where.append(DBFormat.format(p.getString(BATCH_SEQ_NO))).append(" ");
            }
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
    protected int getBoxCount(ScheduleParams p, String customerCode)
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
        // 荷主コード
        workSKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 出荷先コード
        workSKey.setCustomerCode(customerCode);
        // 得意先コード
        if (!StringUtil.isBlank(p.getString(REGULAR_CUSTOMER_CODE)))
        {
            workSKey.setRegularCustomerCode(p.getString(REGULAR_CUSTOMER_CODE));
        }
        // エリアNo.
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            // 全エリア以外が選択されている場合
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                workSKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            workSKey.setBatchNo(p.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            if (!WmsParam.ALL_BATCH_SEQ_NO.equals(p.getString(BATCH_SEQ_NO)))
            {
                workSKey.setBatchSeqNo(p.getString(BATCH_SEQ_NO));
            }
        }
        // 状態フラグ(完了 or メンテ完了)
        workSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION, "=", "(", "", false);
        workSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION, "=", "", ")", false);

        // 集約条件
        // 実績オーダーNo.
        workSKey.setResultOrderNoGroup();

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
        DefaultDDBHandler ddbHandler = null;
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
     * リスト内データの並び替えを行います。
     * 
     * @param param 取得データ
     * @return List<Params> 並び替え後リスト
     * @throws CommonException ユーザ定義の例外を通知します
     */
    protected List<Params> getSortList(PCTRetrievalOutParameter[] param, String button)
            throws CommonException
    {
        // 返却パラメータ配列
        List<Params> result = new ArrayList<Params>();
        // 進捗率格納用
        BigDecimal bdRate = new BigDecimal(0);

        // パラメータ分繰り返す
        // ここでは出荷先コードの桁数を合わせる
        for (int i = 0; i < param.length; i++)
        {
            // パラメータの生成
            Params listParam = new Params();

            // 出荷先コード
            listParam.set(CUSTOMER_CODE, param[i].getCustomerCode());
            // 出荷先名称
            listParam.set(CUSTOMER_NAME, param[i].getCustomerName());
            // 進捗率
            bdRate = getProgressRate((int)param[i].getPlanLotCnt(), (int)param[i].getResultLotCnt());
            listParam.set(PROGRESS_RATE, bdRate);
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
            // ボタン制御
            listParam.set(BUTTON_FLAG, button);

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
