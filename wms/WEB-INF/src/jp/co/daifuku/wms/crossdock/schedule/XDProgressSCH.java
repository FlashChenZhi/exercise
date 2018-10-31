// $Id: XDProgressSCH.java 7861 2010-04-22 10:53:14Z shibamoto $
package jp.co.daifuku.wms.crossdock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.field.FieldName;
import static jp.co.daifuku.wms.crossdock.schedule.XDProgressSCHParams.*;


/**
 * TC予定進捗のスケジュール処理を行います。
 *
 * @version $Revision: 7861 $, $Date: 2010-04-22 19:53:14 +0900 (木, 22 4 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class XDProgressSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 進捗リストセルの最大表示件数
     */
    private static final int MAX_DISPLAY_PROGRESS = 2;

    /**
     * 検索時の別名：入荷予定ケース
     */
    private static final FieldName PLAN_RECEIVE_CASE = new FieldName("DnCrossDockPlan", "PLAN_RECEIVE_CASE");

    /**
     * 検索時の別名：入荷予定ピース
     */
    private static final FieldName PLAN_RECEIVE_PIECE = new FieldName("DnCrossDockPlan", "PLAN_RECEIVE_PIECE");

    /**
     * 検索時の別名：入荷実績ケース
     */
    private static final FieldName RESULT_RECEIVE_CASE = new FieldName("DnCrossDockPlan", "RESULT_RECEIVE_CASE");

    /**
     * 検索時の別名：入荷実績ピース
     */
    private static final FieldName RESULT_RECEIVE_PIECE = new FieldName("DnCrossDockPlan", "RESULT_RECEIVE_PIECE");

    /**
     * 検索時の別名：仕分実績ケース
     */
    private static final FieldName RESULT_SORT_CASE = new FieldName("DnCrossDockPlan", "RESULT_SORT_CASE");

    /**
     * 検索時の別名：仕分実績ピース
     */
    private static final FieldName RESULT_SORT_PIECE = new FieldName("DnCrossDockPlan", "RESULT_SORT_PIECE");


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
    public XDProgressSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、ためうちエリア出力用のデータをデータベースから取得して返します。<BR>
     * 詳しい動作はクラス説明の項を参照してください。<BR>
     * @param searchParam 表示データ取得条件を持つ<CODE>XDInParameter</CODE>クラスのインスタンス。<BR>
     * @return 検索結果を持つ<CODE>StorageOutParameter</CODE>インスタンスの配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0の配列を返します。<BR>
     *          検索結果が最大表示件数を超えた場合か、入力条件にエラーが発生した場合はnullを返します。<BR>
     *          要素数0の配列またはnullが返された場合は、<CODE>getMessage()</CODE>メソッドでエラー内容をメッセージとして取得できます。
     * @throws CommonException 全ての例外が通知されます。
     */
    public List<Params> query(ScheduleParams searchParam)
            throws CommonException
    {

        List<Params> outParam = new ArrayList<Params>();

        // 初期表示、予定日が未表示の場合
        if (StringUtil.isBlank(searchParam.getString(PLAN_DAY)))
        {
            // 基準となる予定日を取得しセット
            searchParam.set(PLAN_DAY, standardDay(searchParam.getString(CONSIGNOR_CODE)));

            if (StringUtil.isBlank(searchParam.getString(PLAN_DAY)))
            {
                // 6003011=対象データはありませんでした。
                setMessage(WmsMessageFormatter.format(6003011));
                return null;
            }
        }

        // バッチNo.が未入力の場合
        if (StringUtil.isBlank(searchParam.getString(BATCH_NO)))
        {
            // 基準日を基に最小のバッチNo.を取得
            String standardBatchNo =
                    standardBatchNo(searchParam.getString(CONSIGNOR_CODE), searchParam.getString(PLAN_DAY));
            searchParam.set(BATCH_NO, standardBatchNo);
        }

        // ボタン制御を行います。
        buttonControl(searchParam);

        // データを取得します。
        outParam = setUnitData(searchParam);

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
     * 基準日を取得します。<BR>
     * 出庫予定情報を検索し、現在の作業日と等しいデータが存在すれば、その作業日を返します。<BR>
     * 無ければ、作業日に最も近い予定日を返します。<BR>
     * @param getConsignorCode 荷主
     * @return 基準日
     * @throws CommonException 全ての例外をスローします。
     */
    protected String standardDay(String getConsignorCode)
            throws CommonException
    {
        // 現在の作業日を取得します。
        WarenaviSystemController wmsContorl = new WarenaviSystemController(getConnection(), getClass());
        String standardDay = wmsContorl.getWorkDay();

        // 出庫予定情報ハンドラ
        CrossDockPlanHandler handler = new CrossDockPlanHandler(getConnection());
        // 出庫予定情報検索キー
        CrossDockPlanSearchKey sKey = new CrossDockPlanSearchKey();

        // 検索条件をセット
        sKey.setConsignorCode(getConsignorCode);
        sKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        sKey.setPlanDayCollect();
        sKey.setPlanDayOrder(true);

        CrossDockPlan[] plan = (CrossDockPlan[])handler.find(sKey, MAX_DISPLAY_PROGRESS + 1);
        if (ArrayUtil.isEmpty(plan))
        {
            return null;
        }
        else if (plan.length <= MAX_DISPLAY_PROGRESS)
        {
        	// 予定が進捗リストセルの最大表示件数より少ない場合は1件目の予定日を基準日とします。
            return plan[0].getPlanDay();
        }
        else
        {
            // 基準日と同一の予定データがあれば、基準日を返します。
            sKey.setPlanDay(standardDay);
        	sKey.setPlanDayGroup();

            plan = (CrossDockPlan[])handler.find(sKey, 1);
            if (plan.length > 0)
            {
                return standardDay;
            }

            // 基準日より小さい予定日を取得
            sKey.clearKeys();
            sKey.clearOrder();
            sKey.setConsignorCode(getConsignorCode);
            sKey.setPlanDay(standardDay, "<");
            sKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            sKey.setPlanDayOrder(false);
            plan = (CrossDockPlan[])handler.find(sKey, 1);

            Date smallDay = null;

            if (!ArrayUtil.isEmpty(plan))
            {
                smallDay = WmsFormatter.toDate(plan[0].getPlanDay());
            }
            else
            {
                smallDay = WmsFormatter.toDate(standardDay);
            }

            // 基準日より大きい予定日を取得
            sKey.clearKeys();
            sKey.clearOrder();
            sKey.setConsignorCode(getConsignorCode);
            sKey.setPlanDay(standardDay, ">");
            sKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            sKey.setPlanDayOrder(true);
            plan = (CrossDockPlan[])handler.find(sKey, 1);

            Date bigDay = null;
            if (!ArrayUtil.isEmpty(plan))
            {
                bigDay = WmsFormatter.toDate(plan[0].getPlanDay());
            }
            else
            {
                bigDay = WmsFormatter.toDate(standardDay);
            }

            // 基準日との差を求めます。
            long smallDiference = WmsFormatter.toDate(standardDay).getTime() - smallDay.getTime();
            long bigDiference = bigDay.getTime() - WmsFormatter.toDate(standardDay).getTime();
            
        	// 現在の作業日より前の予定が無い場合
            if (smallDiference == 0)
            {
            	return WmsFormatter.toParamDate(bigDay);
            }
        	// 現在の作業日より後の予定が無い場合
            if (bigDiference == 0)
            {
            	return WmsFormatter.toParamDate(smallDay);
            }
            // 基準日に近い方を返します。
            if (smallDiference < bigDiference)
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
        // TC予定情報ハンドラ
        CrossDockPlanHandler handler = new CrossDockPlanHandler(getConnection());
        CrossDockPlanSearchKey shKy = new CrossDockPlanSearchKey();

        // 検索条件
        shKy.setConsignorCode(consignorCode);
        shKy.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        shKy.setPlanDay(standardDay);
        shKy.setBatchNoCollect("MIN");

        // 検索結果を取得
        CrossDockPlan entity = (CrossDockPlan)handler.findPrimary(shKy);

        if (entity == null)
        {
            return null;
        }

        return entity.getBatchNo();
    }

    /**
     * 基準日の前後データ数を取得し前頁、次頁の制御を行います
     *
     * @param inParam 入力パラメータ
     * @throws CommonException 全ての例外をスローします。
     */
    protected void buttonControl(ScheduleParams inParam)
            throws CommonException
    {
        CrossDockPlanHandler handler = new CrossDockPlanHandler(getConnection());
        CrossDockPlanSearchKey lowShKy = new CrossDockPlanSearchKey();
        CrossDockPlanSearchKey highShKy = new CrossDockPlanSearchKey();

        // 前データ件数
        int lowData = 0;
        // 次データ件数
        int highData = 0;

        // 基準日より過去の日付のデータ件数を取得します
        lowShKy.clear();
        lowShKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        lowShKy.setPlanDayGroup();

        // 集約条件：予定日＋バッチNo.別の場合
        if (XDInParameter.COLLECT_BATCHNO_UNIT.equals(inParam.getString(COLLECT_FLAG)))
        {
            // 今日で、バッチNo.が若いもの or 今日より前のもの
            lowShKy.setPlanDay(inParam.getString(PLAN_DAY), "<", "((", ")", false);
            lowShKy.setPlanDay(inParam.getString(PLAN_DAY), "=", "(", "", true);
            lowShKy.setBatchNo(inParam.getString(BATCH_NO), "<", "", "))", true);

            lowShKy.setBatchNoGroup();
        }
        // 集約条件：予定日別の場合
        else if (XDInParameter.COLLECT_PLANDATE_UNIT.equals(inParam.getString(COLLECT_FLAG)))
        {
            lowShKy.setPlanDay(inParam.getString(PLAN_DAY), "<");
        }

        lowShKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");

        lowData = handler.count(lowShKy);

        // 基準日より未来の日付のデータ件数を取得します。
        highShKy.clear();
        highShKy.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
        highShKy.setPlanDayGroup();

        // 集約条件：予定日＋バッチNo.別の場合
        if (XDInParameter.COLLECT_BATCHNO_UNIT.equals(inParam.getString(COLLECT_FLAG)))
        {
            // 今日よりうしろ or 今日でバッチNo.があとのもの
            highShKy.setPlanDay(inParam.getString(PLAN_DAY), ">", "((", ")", false);
            highShKy.setPlanDay(inParam.getString(PLAN_DAY), "=", "(", "", true);
            highShKy.setBatchNo(inParam.getString(BATCH_NO), ">", "", "))", true);

            highShKy.setBatchNoGroup();
        }
        // 集約条件：予定日別の場合
        else if (XDInParameter.COLLECT_PLANDATE_UNIT.equals(inParam.getString(COLLECT_FLAG)))
        {
            highShKy.setPlanDay(inParam.getString(PLAN_DAY), ">");
        }

        highShKy.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
        highData = highData + handler.count(highShKy);

        // 初期表示、自動表示、表示ボタン、集約条件ラジオボタン押下時
        if (XDInParameter.PROCESS_FLAG_VIEW.equals(inParam.getString(PROCESS_FLAG)))
        {
        	// 前頁ボタンの制御 前データが存在するときtrue
        	inParam.set(ENABLED_PREV_FLAG, lowData > 0);

        	// 次頁ボタンの制御 次データが表示可能件数以上存在するときtrue
        	inParam.set(ENABLED_NEXT_FLAG, highData >= MAX_DISPLAY_PROGRESS);
        }
    	// 前頁ボタン押下時
        else if (XDInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {
        	// 前頁ボタンの制御 前データが表示可能件数以上存在するときtrue
            inParam.set(ENABLED_PREV_FLAG, lowData > MAX_DISPLAY_PROGRESS);

            // 次頁ボタンの制御
            inParam.set(ENABLED_NEXT_FLAG, true);
        }
    	// 次頁ボタン押下時
        else if (XDInParameter.PROCESS_FLAG_NEXT_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {
        	// 前頁ボタンの制御
            inParam.set(ENABLED_PREV_FLAG, true);
        	
            // 次頁ボタンの制御 次データが表示可能件数を超え存在するときtrue
            inParam.set(ENABLED_NEXT_FLAG, highData > MAX_DISPLAY_PROGRESS);
        }
    }

    /**
     * 予定日別データを取得します。<BR>
     * @param inParam 検索パラメータ
     * @return 表示データ
     * @throws CommonException 全ての例外をスローします。
     */
    protected List<Params> setUnitData(ScheduleParams inParam)
            throws CommonException
    {
        List<Params> outParams = new ArrayList<Params>();
        String sql = "";
        if (XDInParameter.COLLECT_BATCHNO_UNIT.equals(inParam.getString(COLLECT_FLAG)))
        {
            sql = getBatchUnitSql(inParam);
        }
        else
        {
            sql = getPlanDaySql(inParam);
        }

        DefaultDDBFinder finder = null;
        CrossDockPlan[] entity = null;
        try
        {
            finder = new DefaultDDBFinder(getConnection(), new CrossDockPlan());
            finder.open(true);
            // 検索結果を取得
            finder.search(sql);
            entity = (CrossDockPlan[])finder.getEntities(2);
            if (ArrayUtil.isEmpty(entity))
            {
                return null;
            }

            // 配列の順序を逆順にします。
            if (XDInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(inParam.getString(PROCESS_FLAG)))
            {
                entity = turnArray(entity);
            }

            CrossDockPlanSearchKey xdKey = new CrossDockPlanSearchKey();
            CrossDockPlanHandler xdHandler = new CrossDockPlanHandler(getConnection());
            CrossDockPlan[] xdPlan = null;

            for (int i = 0; i < entity.length; i++)
            {
                Params param = new Params();

                // 前頁有効フラグ
                param.set(ENABLED_PREV_FLAG, inParam.get(ENABLED_PREV_FLAG));

                // 次頁有効フラグ
                param.set(ENABLED_NEXT_FLAG, inParam.get(ENABLED_NEXT_FLAG));

                // 予定日
                param.set(PLAN_DAY, entity[i].getPlanDay());

                // バッチNo.
                param.set(BATCH_NO, entity[i].getBatchNo());

                // 荷主コード
                param.set(CONSIGNOR_CODE, entity[i].getConsignorCode());

                // 入荷ケース数(分母)
                param.set(PLAN_RECEIVE_CASE_KEY,
                        entity[i].getBigDecimal(PLAN_RECEIVE_CASE, new BigDecimal(0)).intValue());

                // 入荷ケース数(分子)
                param.set(RESULT_RECEIVE_CASE_KEY,
                        entity[i].getBigDecimal(RESULT_RECEIVE_CASE, new BigDecimal(0)).intValue());

                // 入荷ピース数(分母)
                param.set(PLAN_RECEIVE_PIECE_KEY,
                        entity[i].getBigDecimal(PLAN_RECEIVE_PIECE, new BigDecimal(0)).intValue());

                // 入荷ピース数(分子)
                param.set(RESULT_RECEIVE_PIECE_KEY,
                        entity[i].getBigDecimal(RESULT_RECEIVE_PIECE, new BigDecimal(0)).intValue());

                // 仕分ケース数(分母)
                param.set(PLAN_SORT_CASE_KEY, entity[i].getBigDecimal(PLAN_RECEIVE_CASE, new BigDecimal(0)).intValue());

                // 仕分ケース数(分子)
                param.set(RESULT_SORT_CASE_KEY, entity[i].getBigDecimal(RESULT_SORT_CASE, new BigDecimal(0)).intValue());

                // 仕分ピース数(分母)
                param.set(PLAN_SORT_PIECE_KEY,
                        entity[i].getBigDecimal(PLAN_RECEIVE_PIECE, new BigDecimal(0)).intValue());

                // 仕分ピース数(分子)
                param.set(RESULT_SORT_PIECE_KEY,
                        entity[i].getBigDecimal(RESULT_SORT_PIECE, new BigDecimal(0)).intValue());

                // 欠品数
                param.set(SHORTAGE_QTY, entity[i].getShortageQty());

                // 入荷進捗率
                double rProg =
                        ((double)(entity[i].getReceiveResultQty() + entity[i].getReceiveShortageQty()) / entity[i].getPlanQty()) * 100;
                param.set(RECEIVE_PROGRESS, rProg);

                // 仕分進捗率
                double sProg =
                        ((double)(entity[i].getReceiveShortageQty() + entity[i].getSortResultQty() + entity[i].getSortShortageQty()) / entity[i].getPlanQty()) * 100;
                param.set(SORT_PROGRESS, sProg);

                // 入荷明細数（分母）
                xdKey.clear();
                xdKey.setPlanUkeyCollect();
                xdKey.setConsignorCode(param.getString(CONSIGNOR_CODE));
                xdKey.setPlanDay(param.getString(PLAN_DAY));
                xdKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                if (XDInParameter.COLLECT_BATCHNO_UNIT.equals(inParam.getString(COLLECT_FLAG)))
                {
                    xdKey.setBatchNo(param.getString(BATCH_NO));
                }
                xdKey.setSupplierCodeGroup();
                xdKey.setReceiveTicketNoGroup();
                xdKey.setReceiveLineNoGroup();

                param.set(RECEIVE_COUNT_DENOMINATOR, xdHandler.count(xdKey));

                // 入荷明細数（分子）
                finder.search(getReceiveTicketSql(param, inParam.getString(COLLECT_FLAG)));
                xdPlan = (CrossDockPlan[])finder.getEntities(5);
                if (ArrayUtil.isEmpty(entity))
                {
                    return null;
                }
                param.set(RECEIVE_COUNT_MOLECULE, xdPlan[0].getBigDecimal(
                        new FieldName(ReceivingPlan.STORE_NAME, "cnt")).intValue());

                // 仕分明細数（分母）
                xdKey.clear();
                xdKey.setPlanUkeyCollect();
                xdKey.setConsignorCode(param.getString(CONSIGNOR_CODE));
                xdKey.setPlanDay(param.getString(PLAN_DAY));
                xdKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                if (XDInParameter.COLLECT_BATCHNO_UNIT.equals(inParam.getString(COLLECT_FLAG)))
                {
                    xdKey.setBatchNo(param.getString(BATCH_NO));
                }
                xdKey.setCustomerCodeGroup();
                xdKey.setShipTicketNoGroup();
                xdKey.setShipLineNoGroup();
                param.set(SORT_COUNT_DENOMINATOR, xdHandler.count(xdKey));

                // 仕分明細数（分子）
                xdKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
                param.set(SORT_COUNT_MOLECULE, xdHandler.count(xdKey));

                // 入荷明細数
                param.set(RECEIVE_DETAIL_COUNT, WmsFormatter.getNumFormat(param.getInt(RECEIVE_COUNT_MOLECULE)) + "/"
                        + WmsFormatter.getNumFormat(param.getInt(RECEIVE_COUNT_DENOMINATOR)));
                // 仕分明細数
                param.set(SORT_DETAIL_COUNT, WmsFormatter.getNumFormat(param.getInt(SORT_COUNT_MOLECULE)) + "/"
                        + WmsFormatter.getNumFormat(param.getInt(SORT_COUNT_DENOMINATOR)));
                // 入荷ｹｰｽ数
                param.set(RECEIVE_CASE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_RECEIVE_CASE_KEY)) + "/"
                        + WmsFormatter.getNumFormat(param.getInt(PLAN_RECEIVE_CASE_KEY)));
                // 入荷ﾋﾟｰｽ数
                param.set(RECEIVE_PIECE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_RECEIVE_PIECE_KEY)) + "/"
                        + WmsFormatter.getNumFormat(param.getInt(PLAN_RECEIVE_PIECE_KEY)));
                // 仕分ｹｰｽ数
                param.set(SORT_CASE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_SORT_CASE_KEY)) + "/"
                        + WmsFormatter.getNumFormat(param.getInt(PLAN_SORT_CASE_KEY)));
                // 仕分ﾋﾟｰｽ数
                param.set(SORT_PIECE_QTY, WmsFormatter.getNumFormat(param.getInt(RESULT_SORT_PIECE_KEY)) + "/"
                        + WmsFormatter.getNumFormat(param.getInt(PLAN_SORT_PIECE_KEY)));

                // 予定日
                param.set(PLAN_DAY, WmsFormatter.toDate(entity[i].getPlanDay()));

                outParams.add(param);
            }
            return outParams;
        }
        finally
        {
            finder.close();
        }
    }

    /**
     * 入荷伝票検索のSQLを作成します。
     * @param inParam Params
     * @param condition String
     * @return SQL
     */
    protected String getReceiveTicketSql(Params inParam, String condition)
    {
        StringBuffer query = new StringBuffer();

        query.append("SELECT ");
        query.append("  count(count(plan_ukey)) cnt ");
        query.append("FROM ");
        query.append("  DnCrossdockPlan plan ");
        query.append("WHERE ");
        query.append("  plan.consignor_code = '").append(inParam.getString(CONSIGNOR_CODE)).append("' and ");
        query.append("  plan.plan_day = '").append(inParam.getString(PLAN_DAY)).append("' and ");
        if (XDInParameter.COLLECT_BATCHNO_UNIT.equals(condition))
        {
            query.append("  plan.batch_no = '").append(inParam.getString(BATCH_NO)).append("' and ");
        }
        query.append("  plan.status_flag != '").append(CrossDockPlan.STATUS_FLAG_DELETE).append("' and ");
        query.append("  (plan.receive_result_qty + plan.receive_shortage_qty) = plan.plan_qty ");
        query.append("GROUP BY ");
        query.append("  plan.supplier_code, ");
        query.append("  plan.receive_ticket_no, ");
        query.append("  plan.receive_line_no ");

        return String.valueOf(query);

    }


    /**
     * 予定日＋バッチNo.別時の検索SQLを作成します
     * @param inParam ScheduleParams
     * @return SQL
     */
    protected String getBatchUnitSql(ScheduleParams inParam)
    {
        StringBuffer query = new StringBuffer();

        query.append("SELECT ");
        query.append("  plan.plan_day, ");
        query.append("  plan.batch_no, ");
        query.append("  sum(plan.plan_qty) plan_qty, ");
        query.append("  max(plan.consignor_code) consignor_code, ");
        query.append("  sum(shortage_qty) shortage_qty, ");
        query.append("  sum(receive_shortage_qty) receive_shortage_qty, ");
        query.append("  sum(receive_result_qty) receive_result_qty, ");
        query.append("  sum(sort_shortage_qty) sort_shortage_qty, ");
        query.append("  sum(sort_result_qty) sort_result_qty, ");
        query.append("  sum(decode(entering_qty, 0, 0, trunc(plan_qty / entering_qty, 0))) ").append(
                String.valueOf(PLAN_RECEIVE_CASE) + ","); // 入荷予定ケース
        query.append("  sum(decode(entering_qty, 0, plan_qty, mod(plan_qty, entering_qty))) ").append(
                String.valueOf(PLAN_RECEIVE_PIECE) + ","); // 入荷予定ピース
        query.append("  sum(decode(entering_qty, 0, 0, trunc(receive_result_qty / entering_qty, 0))) ").append(
                String.valueOf(RESULT_RECEIVE_CASE) + ","); // 入荷実績、仕分予定ケース
        query.append("  sum(decode(entering_qty, 0, receive_result_qty, mod(receive_result_qty, entering_qty))) ").append(
                String.valueOf(RESULT_RECEIVE_PIECE) + ","); // 入荷実績、仕分予定ピース
        query.append("  sum(decode(entering_qty, 0, 0, trunc(sort_result_qty / entering_qty, 0))) ").append(
                String.valueOf(RESULT_SORT_CASE) + ","); // 仕分実績ケース
        query.append("  sum(decode(entering_qty, 0, sort_result_qty, mod(sort_result_qty, entering_qty))) ").append(
                String.valueOf(RESULT_SORT_PIECE) + " "); // 仕分実績ピース
        query.append("FROM ");
        query.append("  DnCrossdockPlan plan, ");
        query.append("  DmItem item ");
        query.append("WHERE ");
        query.append("  item.consignor_code = plan.consignor_code and ");
        query.append("  item.item_code = plan.item_code and ");
        query.append("  plan.consignor_code = '").append(inParam.getString(CONSIGNOR_CODE)).append("' and ");
        query.append("  plan.status_flag != '").append(CrossDockPlan.STATUS_FLAG_DELETE).append("' and ");

        String order = "asc";
        if (XDInParameter.PROCESS_FLAG_VIEW.equals(inParam.getString(PROCESS_FLAG)))
        {
            // 表示
            // 今日より後ろ or 今日でバッチNo.が以降のもの
            query.append("  ((plan.plan_day > '").append(inParam.getString(PLAN_DAY)).append("') or ");
            query.append("  (plan.plan_day = '").append(inParam.getString(PLAN_DAY)).append("' and ");
            query.append("  plan.batch_no >= '").append(inParam.getString(BATCH_NO)).append("')) ");
        }
        else if (XDInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {
            // 前
            // 今日より前 or 今日でバッチNo.が前のもの
            query.append("  ((plan.plan_day < '").append(inParam.getString(PLAN_DAY)).append("') or ");
            query.append("  (plan.plan_day = '").append(inParam.getString(PLAN_DAY)).append("' and ");
            query.append("  plan.batch_no < '").append(inParam.getString(BATCH_NO)).append("')) ");
            order = "desc";
        }
        else if (XDInParameter.PROCESS_FLAG_NEXT_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {
            // 次
            // 今日より後ろ or 今日でバッチNo.があとのもの
            query.append("  ((plan.plan_day > '").append(inParam.getString(PLAN_DAY)).append("') or ");
            query.append("  (plan.plan_day = '").append(inParam.getString(PLAN_DAY)).append("' and ");
            query.append("  plan.batch_no > '").append(inParam.getString(BATCH_NO)).append("')) ");
        }

        query.append("GROUP BY ");
        query.append("  plan.plan_day, ");
        query.append("  plan.batch_no ");
        query.append("ORDER BY ");
        query.append("  plan.plan_day ").append(order).append(", ");
        query.append("  plan.batch_no ").append(order);

        return String.valueOf(query);

    }

    /**
     * 予定日別時の検索SQLを作成します
     * @param inParam ScheduleParams
     * @return SQL
     */
    protected String getPlanDaySql(ScheduleParams inParam)
    {
        StringBuffer query = new StringBuffer();

        query.append("SELECT ");
        query.append("  plan.plan_day, ");
        query.append("  sum(plan.plan_qty) plan_qty, ");
        query.append("  max(plan.consignor_code) consignor_code, ");
        query.append("  sum(shortage_qty) shortage_qty, ");
        query.append("  sum(receive_shortage_qty) receive_shortage_qty, ");
        query.append("  sum(receive_result_qty) receive_result_qty, ");
        query.append("  sum(sort_shortage_qty) sort_shortage_qty, ");
        query.append("  sum(sort_result_qty) sort_result_qty, ");
        query.append("  sum(decode(entering_qty, 0, 0, trunc(plan_qty / entering_qty, 0))) ").append(
                String.valueOf(PLAN_RECEIVE_CASE) + ","); // 入荷予定ケース
        query.append("  sum(decode(entering_qty, 0, plan_qty, mod(plan_qty, entering_qty))) ").append(
                String.valueOf(PLAN_RECEIVE_PIECE) + ","); // 入荷予定ピース
        query.append("  sum(decode(entering_qty, 0, 0, trunc(receive_result_qty / entering_qty, 0))) ").append(
                String.valueOf(RESULT_RECEIVE_CASE) + ","); // 入荷実績、仕分予定ケース
        query.append("  sum(decode(entering_qty, 0, receive_result_qty, mod(receive_result_qty, entering_qty))) ").append(
                String.valueOf(RESULT_RECEIVE_PIECE) + ","); // 入荷実績、仕分予定ピース
        query.append("  sum(decode(entering_qty, 0, 0, trunc(sort_result_qty / entering_qty, 0))) ").append(
                String.valueOf(RESULT_SORT_CASE) + ","); // 仕分実績ケース
        query.append("  sum(decode(entering_qty, 0, sort_result_qty, mod(sort_result_qty, entering_qty))) ").append(
                String.valueOf(RESULT_SORT_PIECE) + " "); // 仕分実績ピース
        query.append("FROM ");
        query.append("  DnCrossdockPlan plan, ");
        query.append("  DmItem item ");
        query.append("WHERE ");
        query.append("  item.consignor_code = plan.consignor_code and ");
        query.append("  item.item_code = plan.item_code and ");
        query.append("  plan.consignor_code = '").append(inParam.getString(CONSIGNOR_CODE)).append("' and ");
        query.append("  plan.status_flag != '").append(CrossDockPlan.STATUS_FLAG_DELETE).append("' and ");
        String order = "asc";
        if (XDInParameter.PROCESS_FLAG_VIEW.equals(inParam.getString(PROCESS_FLAG)))
        {
            query.append("  plan.plan_day >= '").append(inParam.getString(PLAN_DAY)).append("' ");
        }
        else if (XDInParameter.PROCESS_FLAG_PREVIOUS_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {
            query.append("  plan.plan_day < '").append(inParam.getString(PLAN_DAY)).append("' ");
            order = "desc";
        }
        else if (XDInParameter.PROCESS_FLAG_NEXT_PAGE.equals(inParam.getString(PROCESS_FLAG)))
        {
            query.append("  plan.plan_day > '").append(inParam.getString(PLAN_DAY)).append("' ");
        }
        query.append("GROUP BY ");
        query.append("  plan.plan_day ");
        query.append("ORDER BY ");
        query.append("  plan.plan_day ").append(order);

        return String.valueOf(query);
    }

    /**
     * 配列の順序を逆順にします<BR>
     * @param array 対象配列
     * @return 逆順にした配列
     */
    protected CrossDockPlan[] turnArray(CrossDockPlan[] array)
    {
        int index = 0;
        CrossDockPlan[] subArray = new CrossDockPlan[array.length];
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
