// $Id: LstPCTUserResultConsignorDASCH.java 5739 2009-11-12 13:28:36Z kumano $
package jp.co.daifuku.pcart.retrieval.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.dasch.LstPCTUserResultConsignorDASCHParams.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.pcart.retrieval.dbhandler.PCTUserResultConsignorFinder;
import jp.co.daifuku.pcart.retrieval.dbhandler.PCTUserResultOrderInfo;
import jp.co.daifuku.pcart.retrieval.dbhandler.PCTUserResultOrderInfoSearchKey;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.entity.PCTPickingResult;
import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

/**
 * 荷主別実績照会に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 5739 $, $Date:: 2009-11-12 22:28:36 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class LstPCTUserResultConsignorDASCH
        extends AbstractWmsDASCH
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
     * DB Finder
     */
    private AbstractDBFinder _finder = null;

    /**
     * 現在点
     */
    private int _current = -1;

    /**
     * レコード総数
     */
    private int _total = -1;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでDASCHを作成します。
     * @param conn Database DBコネクション
     * @param parent Caller 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     */
    public LstPCTUserResultConsignorDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
     * 帳票出力、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params p)
            throws CommonException
    {
        // Create Finder Object
        _finder = new PCTUserResultConsignorFinder(getConnection());

        // Initialize record counts
        // データ件数初期化(Excel大量出力対応)
        _finder.open(isForwardOnly());

        // Create Search Key and search for Records
        _finder.search(createSearchKey(p, true));

        _current = -1;
    }

    /**
     * 次のデータが存在するかどうかを判定します。<BR>
     * 帳票出力で使用します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        _current++;
        return _total > _current;
    }

    /**
     * データを1件返します。<BR>
     * 帳票出力で使用します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        PCTUserResultOrderInfo[] totalEnts = (PCTUserResultOrderInfo[])_finder.getEntities(0, _total);
        PCTPickingResult total = totalEnts[0].getTotal();
        // get Next entity from finder class
        PCTUserResultOrderInfo[] ents = (PCTUserResultOrderInfo[])_finder.getEntities(_current, _current + 1);
        Params p = new Params();
        // conver Entity to Param object
        for (PCTUserResultOrderInfo ent : ents)
        {
            PCTPickingResult detail = ent.getDetail();

            // 詳細部分の編集
            convertDetail(p, detail);

            // 平均部分の編集
            convertAvg(p, total, ent.getCount());

            p.set(SYS_DAY, getPrintDate());
            p.set(SYS_TIME, getPrintDate());
            break;
        }
        // return Pram objstc
        return p;
    }

    /**
     *
     * finder,Connection close
     */
    public void close()
    {
        if (_finder != null)
        {
            _finder.close();
        }
        super.close();
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
     * データ件数を返します。
     * 帳票発行、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @return データ件数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected int actualCount(Params p)
            throws CommonException
    {
        _finder = new PCTUserResultConsignorFinder(getConnection());

        if (_finder == null)
        {
            _finder.open(isForwardOnly());
        }

        // find count
        _total = _finder.search(createSearchKey(p, false));

        return _total;
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * 一覧表示で使用します。
     *
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        List<Params> params = new ArrayList<Params>();
        PCTUserResultOrderInfo[] ents = (PCTUserResultOrderInfo[])_finder.getEntities(start, start + cnt);

        PCTPickingResult total = ents[0].getTotal();


        for (PCTUserResultOrderInfo ent : ents)
        {
            Params p = new Params();

            PCTPickingResult detail = ent.getDetail();

            // 詳細部分の編集
            convertDetail(p, detail);

            // 平均部分の編集
            convertAvg(p, total, ent.getCount());

            params.add(p);
        }
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 検索条件のセットを行います。<BR>
     * @param param 検索条件を含むパラメータ
     * @param isSet 件数確認の場合はfalse、出力用データ取得の場合はtrueをセットします
     * @return SearchKey 検索キー
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    private SearchKey createSearchKey(Params param, boolean isSet)
            throws CommonException
    {
        PCTUserResultOrderInfoSearchKey key = new PCTUserResultOrderInfoSearchKey();

        // パラメータをセット
        PCTRetrievalInParameter inParam = new PCTRetrievalInParameter();

        // 作業日の大小変換チェック
        String[] day =
                WmsFormatter.getFromTo(WmsFormatter.toParamDate(param.getDate(WORKDAY_FROM)),
                        WmsFormatter.toParamDate(param.getDate(WORKDAY_TO)));

        // 作業日
        inParam.setWorkDay(day[0]);
        // 作業日(TO)
        inParam.setToWorkDay(day[1]);
        // 曜日指定フラグ
        inParam.setUseDayOfWeekFlag(param.getBoolean(USEDAY_OF_WEEK_FLAG));
        // 月曜選択フラグ
        inParam.setMondayFlag(param.getBoolean(MONDAY));
        // 火曜選択フラグ
        inParam.setTuesdayFlag(param.getBoolean(TUESDAY));
        // 水曜選択フラグ
        inParam.setWednesdayFlag(param.getBoolean(WEDNESDAY));
        // 木曜選択フラグ
        inParam.setThursdayFlag(param.getBoolean(THURSDAY));
        // 金曜選択フラグ
        inParam.setFridayFlag(param.getBoolean(FRIDAY));
        // 土曜選択フラグ
        inParam.setSaturdayFlag(param.getBoolean(SATURDAY));
        // 日曜選択フラグ
        inParam.setSundayFlag(param.getBoolean(SUNDAY));
        // 荷主コード
        inParam.setConsignorCode(param.getString(CONSIGNOR_CODE));
        // エリアNo.
        inParam.setPlanAreaNo(param.getString(AREA_NO));
        // バッチNo.
        inParam.setBatchNo(param.getString(BATCH_NO));
        // レベルA選択フラグ
        inParam.setLevelAFlag(param.getBoolean(LEVEL_A));
        // レベルB選択フラグ
        inParam.setLevelBFlag(param.getBoolean(LEVEL_B));
        // レベルC選択フラグ
        inParam.setLevelCFlag(param.getBoolean(LEVEL_C));
        // 表示ランク
        inParam.setDisplayRank(param.getString(DISPLAY_RANK));
        // 集約単位
        inParam.setCollectCondition(param.getString(COLLECT_CONDITION));


        key.setInParameter(inParam);

        // 検索条件、集約条件をセットする
        // where, group by

        if (isSet)
        {
            // OrderByや、collect項目を記載する
        }

        return key;
    }

    /**
     * PCTUserResultOrderInfoエンティティの詳細部分をRetrievalOutParameterにセットし返します。<BR>
     * 
     * @param ioParam PCTRetrievalOutParameter編集結果
     * @param inResult PCTPickingResult検索結果データ
     */
    protected void convertDetail(Params ioParam, PCTPickingResult inResult)
    {
        try
        {
            // 実稼働時間(時)
            // (稼働時間 - バッテリ交換時間 - 休憩時間) / 60 / 60
            BigDecimal hour =
                    new BigDecimal(inResult.getOperateTime() - inResult.getBatteryChangeTime()
                            - inResult.getBreakTime());
            if (hour.doubleValue() > 0)
            {
                hour = hour.divide(new BigDecimal(3600), 3, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                hour = new BigDecimal(0);
            }
            BigDecimal planOrderCnt = new BigDecimal(inResult.getPlanOrderCnt());
            BigDecimal lineCnt = new BigDecimal(inResult.getWorkCnt());

            // 荷主コード
            ioParam.set(CONSIGNOR_CODE, inResult.getConsignorCode());
            // 荷主名称
            ioParam.set(CONSIGNOR_NAME, inResult.getConsignorName());
            // 生産率
            BigDecimal rate = inResult.getBigDecimal(PCTUserResultOrderInfo.PRODUCTION_RATE, new BigDecimal(0));
            ioParam.set(PRODUCTION_RATE, WmsFormatter.toProductionRate(rate.doubleValue()));
            // 人数
            ioParam.set(WORKER_COUNT, formatNumber(inResult.getBigDecimal(PCTUserResultOrderInfo.COUNT_WORKER,
                    new BigDecimal(0)).intValue()));
            // 台数
            ioParam.set(TERMINAL_COUNT, formatNumber(inResult.getBigDecimal(PCTUserResultOrderInfo.COUNT_TERMINAL,
                    new BigDecimal(0)).intValue()));
            // 開始日時
            ioParam.set(START_TIME, (inResult.getStartTime()));
            // 終了日時
            ioParam.set(END_TIME, (inResult.getEndTime()));
            // 作業時間(延べ)
            ioParam.set(OPERATE_TIME, WmsFormatter.toDispHourMinute(inResult.getWorkTime()));

            // オーダー数
            ioParam.set(ORDER_COUNT, formatNumber(inResult.getPlanOrderCnt()));
            // 箱数
            ioParam.set(BOX_COUNT, formatNumber(inResult.getOrderCnt()));
            // 行数
            ioParam.set(LINE_COUNT, formatNumber(inResult.getWorkCnt()));
            // 数量(ロット)
            ioParam.set(LOT_QTY, formatNumber(inResult.getWorkQty()));
            // 数量(バラ)
            ioParam.set(PIECE_QTY, formatNumber(inResult.getPieceQty()));

            /* 
             * 実稼働時間やオーダー数、行数等で除算した値をセットする
             */
            // 一時作業領域定義
            BigDecimal tmpQty = new BigDecimal(0);

            // オーダー数/H
            if (hour.doubleValue() > 0 && inResult.getPlanOrderCnt() > 0)
            {
                tmpQty = inResult.getBigDecimal(PCTPickingResult.PLAN_ORDER_CNT, new BigDecimal(0));
                tmpQty = tmpQty.divide(hour, 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(ORDER_COUNT_PER_HOUR, formatNumber(tmpQty.doubleValue()));
            // 箱数/H
            if (hour.doubleValue() > 0 && inResult.getOrderCnt() > 0)
            {
                tmpQty = inResult.getBigDecimal(PCTPickingResult.ORDER_CNT, new BigDecimal(0));
                tmpQty = tmpQty.divide(hour, 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(BOX_COUNT_PER_HOUR, formatNumber(tmpQty.doubleValue()));
            // 行数/H
            if (hour.doubleValue() > 0 && inResult.getWorkCnt() > 0)
            {
                tmpQty = inResult.getBigDecimal(PCTPickingResult.WORK_CNT, new BigDecimal(0));
                tmpQty = tmpQty.divide(hour, 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(LINE_COUNT_PER_HOUR, formatNumber(tmpQty.doubleValue()));
            // 数量(ロット)/H
            if (hour.doubleValue() > 0 && inResult.getWorkQty() > 0)
            {
                tmpQty = inResult.getBigDecimal(PCTPickingResult.WORK_QTY, new BigDecimal(0));
                tmpQty = tmpQty.divide(hour, 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(LOT_QTY_PER_HOUR, formatNumber(tmpQty.doubleValue()));
            // 数量(バラ)/H
            if (hour.doubleValue() > 0 && inResult.getPieceQty() > 0)
            {
                tmpQty = inResult.getBigDecimal(PCTPickingResult.PIECE_QTY, new BigDecimal(0));
                tmpQty = tmpQty.divide(hour, 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(PIECE_QTY_PER_HOUR, formatNumber(tmpQty.doubleValue()));
            // 箱/オーダー
            if (planOrderCnt.doubleValue() > 0 && inResult.getOrderCnt() > 0)
            {
                tmpQty = inResult.getBigDecimal(PCTPickingResult.ORDER_CNT, new BigDecimal(0));
                tmpQty = tmpQty.divide(planOrderCnt, 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(BOX_COUNT_PER_ORDER, formatNumber(tmpQty.doubleValue()));
            // 行/オーダー
            if (planOrderCnt.doubleValue() > 0 && inResult.getWorkCnt() > 0)
            {
                tmpQty = inResult.getBigDecimal(PCTPickingResult.WORK_CNT, new BigDecimal(0));
                tmpQty = tmpQty.divide(planOrderCnt, 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(LINE_COUNT_PER_ORDER, formatNumber(tmpQty.doubleValue()));
            // ロット/行
            if (lineCnt.doubleValue() > 0 && inResult.getWorkQty() > 0)
            {
                tmpQty = inResult.getBigDecimal(PCTPickingResult.WORK_QTY, new BigDecimal(0));
                tmpQty = tmpQty.divide(lineCnt, 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(LOT_QTY_PER_LINE_COUNT, formatNumber(tmpQty.doubleValue()));
            // バラ/行
            if (lineCnt.doubleValue() > 0 && inResult.getPieceQty() > 0)
            {
                tmpQty = inResult.getBigDecimal(PCTPickingResult.PIECE_QTY, new BigDecimal(0));
                tmpQty = tmpQty.divide(lineCnt, 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(PIECE_QTY_PER_LINE_COUNT, formatNumber(tmpQty.doubleValue()));
            // ミス率
            if (inResult.getWorkQty() > 0 && inResult.getMissScanCnt() > 0)
            {
                tmpQty = inResult.getBigDecimal(PCTPickingResult.MISS_SCAN_CNT, new BigDecimal(0));
                tmpQty =
                        tmpQty.divide(inResult.getBigDecimal(PCTPickingResult.WORK_QTY), 2, BigDecimal.ROUND_HALF_UP).multiply(
                                new BigDecimal(100));
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(MISS_RATE, WmsFormatter.toMissRate(tmpQty.intValue()));

            // 荷主名称
            ioParam.set(CONSIGNOR_NAME, (String)inResult.getValue(PCTPickingResult.CONSIGNOR_NAME, ""));
            // エリア名称
            ioParam.set(AREA_NAME, (String)inResult.getValue(PCTPickingResult.AREA_NAME, ""));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //MSG=データベースエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            return;
        }
    }

    /**
     * PCTUserResultOrderInfoエンティティの集計部分をRetrievalOutParameterにセットし返します。<BR>
     * 
     * @param ioParam PCTRetrievalOutParameter編集結果
     * @param inResult PCTPickingResult検索結果データ
     * @param count 検索結果データ件数
     */
    protected void convertAvg(Params ioParam, PCTPickingResult inResult, int count)
    {
        try
        {
            // 一時作業領域定義
            int tmpInt = 0;
            BigDecimal tmpQty = new BigDecimal(0);
            BigDecimal tmpDec = new BigDecimal(0);

            /* 
             * 平均値をセットする
             */
            // 生産率(平均)は100%固定
            BigDecimal rateAvg = new BigDecimal(100);
            ioParam.set(AVG_PRODUCTION_RATE, WmsFormatter.toProductionRate(rateAvg.doubleValue()));

            // 人数(平均)
            tmpInt = inResult.getBigDecimal(PCTUserResultOrderInfo.COUNT_WORKER, new BigDecimal(0)).intValue();
            if (inResult.getWorkTime() > 0 && count > 0)
            {
                ioParam.set(AVG_WORKER_COUNT, formatNumber(tmpInt / count));
            }
            else
            {
                ioParam.set(AVG_WORKER_COUNT, formatNumber(0));
            }
            // 台数(平均)
            tmpInt = inResult.getBigDecimal(PCTUserResultOrderInfo.COUNT_TERMINAL, new BigDecimal(0)).intValue();
            if (inResult.getWorkTime() > 0 && count > 0)
            {
                ioParam.set(AVG_CART_COUNT, formatNumber(tmpInt / count));
            }
            else
            {
                ioParam.set(AVG_CART_COUNT, formatNumber(0));
            }
            // 作業時間(延べ)(平均)
            if (inResult.getWorkTime() > 0 && count > 0)
            {
                ioParam.set(AVG_OPERATE_TIME, WmsFormatter.toDispHourMinute(inResult.getWorkTime() / count));
            }
            else
            {
                ioParam.set(AVG_OPERATE_TIME, WmsFormatter.toDispHourMinute(inResult.getWorkTime()));
            }
            // オーダー数(平均)
            tmpInt = inResult.getBigDecimal(PCTUserResultOrderInfo.PLAN_ORDER_TOTAL, new BigDecimal(0)).intValue();
            if (tmpInt > 0 && count > 0)
            {
                ioParam.set(AVG_ORDER_COUNT, formatNumber(tmpInt / count));
            }
            else
            {
                ioParam.set(AVG_ORDER_COUNT, formatNumber(0));
            }
            // 箱数(平均)
            tmpInt = inResult.getBigDecimal(PCTUserResultOrderInfo.BOX_TOTAL, new BigDecimal(0)).intValue();
            if (tmpInt > 0 && count > 0)
            {
                ioParam.set(AVG_BOX_COUNT, formatNumber(tmpInt / count));
            }
            else
            {
                ioParam.set(AVG_BOX_COUNT, formatNumber(0));
            }
            // 行数(平均)
            tmpInt = inResult.getBigDecimal(PCTUserResultOrderInfo.LINE_TOTAL, new BigDecimal(0)).intValue();
            if (tmpInt > 0 && count > 0)
            {
                ioParam.set(AVG_LINE_COUNT, formatNumber(tmpInt / count));
            }
            else
            {
                ioParam.set(AVG_LINE_COUNT, formatNumber(0));
            }
            // 数量(ロット)(平均)
            tmpInt = inResult.getBigDecimal(PCTUserResultOrderInfo.WORK_TOTAL, new BigDecimal(0)).intValue();
            if (tmpInt > 0 && count > 0)
            {
                ioParam.set(AVG_LOT_QTY, formatNumber(tmpInt / count));
            }
            else
            {
                ioParam.set(AVG_LOT_QTY, formatNumber(0));
            }
            // 数量(バラ)(平均)
            tmpInt = inResult.getBigDecimal(PCTUserResultOrderInfo.PIECE_TOTAL, new BigDecimal(0)).intValue();
            if (tmpInt > 0 && count > 0)
            {
                ioParam.set(AVG_PIECE_QTY, formatNumber(tmpInt / count));
            }
            else
            {
                ioParam.set(AVG_PIECE_QTY, formatNumber(0));
            }

            // オーダー数/H(平均)
            tmpDec = inResult.getBigDecimal(PCTUserResultOrderInfo.PLAN_ORDER_HOUR, new BigDecimal(0));
            if (tmpDec.doubleValue() > 0 && count > 0)
            {
                tmpQty = tmpDec.divide(new BigDecimal(count), 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(AVG_ORDER_COUNT_PER_HOUR, formatNumber(tmpQty.doubleValue()));
            // 箱数/H(平均)
            tmpDec = inResult.getBigDecimal(PCTUserResultOrderInfo.BOX_HOUR, new BigDecimal(0));
            if (tmpDec.doubleValue() > 0 && count > 0)
            {
                tmpQty = tmpDec.divide(new BigDecimal(count), 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(AVG_BOX_COUNT_PER_HOUR, formatNumber(tmpQty.doubleValue()));
            // 行数/H(平均)
            tmpDec = inResult.getBigDecimal(PCTUserResultOrderInfo.LINE_HOUR, new BigDecimal(0));
            if (tmpDec.doubleValue() > 0 && count > 0)
            {
                tmpQty = tmpDec.divide(new BigDecimal(count), 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(AVG_LINE_COUNT_PER_HOUR, formatNumber(tmpQty.doubleValue()));
            // 数量(ロット)/H(平均)
            tmpDec = inResult.getBigDecimal(PCTUserResultOrderInfo.WORK_HOUR, new BigDecimal(0));
            if (tmpDec.doubleValue() > 0 && count > 0)
            {
                tmpQty = tmpDec.divide(new BigDecimal(count), 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(AVG_LOT_QTY_PER_HOUR, formatNumber(tmpQty.doubleValue()));
            // 数量(バラ)/H(平均)
            tmpDec = inResult.getBigDecimal(PCTUserResultOrderInfo.PIECE_HOUR, new BigDecimal(0));
            if (tmpDec.doubleValue() > 0 && count > 0)
            {
                tmpQty = tmpDec.divide(new BigDecimal(count), 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(AVG_PIECE_QTY_PER_HOUR, formatNumber(tmpQty.doubleValue()));
            // 箱/オーダー(平均)
            tmpDec = inResult.getBigDecimal(PCTUserResultOrderInfo.BOX_PLAN_ORDER, new BigDecimal(0));
            if (tmpDec.doubleValue() > 0 && count > 0)
            {
                tmpQty = tmpDec.divide(new BigDecimal(count), 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(AVG_BOX_COUNT_PER_ORDER, formatNumber(tmpQty.doubleValue()));
            // 行/オーダー(平均)
            tmpDec = inResult.getBigDecimal(PCTUserResultOrderInfo.LINE_PLAN_ORDER, new BigDecimal(0));
            if (tmpDec.doubleValue() > 0 && count > 0)
            {
                tmpQty = tmpDec.divide(new BigDecimal(count), 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(AVG_LINE_COUNT_PER_ORDER, formatNumber(tmpQty.doubleValue()));
            // ロット/行(平均)
            tmpDec = inResult.getBigDecimal(PCTUserResultOrderInfo.LOT_LINE, new BigDecimal(0));
            if (tmpDec.doubleValue() > 0 && count > 0)
            {
                tmpQty = tmpDec.divide(new BigDecimal(count), 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(AVG_LOT_QTY_PER_LINE_COUNT, formatNumber(tmpQty.doubleValue()));
            // バラ/行(平均)
            tmpDec = inResult.getBigDecimal(PCTUserResultOrderInfo.PIECE_LINE, new BigDecimal(0));
            if (tmpDec.doubleValue() > 0 && count > 0)
            {
                tmpQty = tmpDec.divide(new BigDecimal(count), 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(AVG_PIECE_QTY_PER_LINE_COUNT, formatNumber(tmpQty.doubleValue()));
            // ミス率(平均)
            tmpDec = inResult.getBigDecimal(PCTUserResultOrderInfo.MISS_PER, new BigDecimal(0));
            if (tmpDec.doubleValue() > 0 && count > 0)
            {
                tmpQty = tmpDec.divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                tmpQty = new BigDecimal(0);
            }
            ioParam.set(AVG_MISS_RATE, WmsFormatter.toMissRate(tmpQty.intValue()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //MSG=データベースエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            return;
        }
    }

    /**
     * フォーマットを行います。
     * 
     * @param num 数値
     * @return フォーマット後の数値
     */
    protected String formatNumber(int num)
    {
        return WmsFormatter.getNumFormat(num);
    }

    /**
     * フォーマットを行います。
     * 
     * @param num 数値
     * @return フォーマット後の数値
     */
    protected String formatNumber(double num)
    {
        return WmsFormatter.toDispNumber(num, 1);
    }

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
