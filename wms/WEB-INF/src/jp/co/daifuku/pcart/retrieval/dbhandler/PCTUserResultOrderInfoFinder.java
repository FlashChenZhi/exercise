// $Id: PCTUserResultOrderInfoFinder.java 5738 2009-11-12 13:28:08Z kumano $
// Handler v3.8
package jp.co.daifuku.pcart.retrieval.dbhandler;

/*
 * Copyright 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.PCTPickingResult;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;


/**
 * データベースからPCTPickingResult表を検索しTestViewにマッピングするためのクラスです。
 * 画面に検索結果を一覧表示する場合このクラスを使用します。
 *
 * @version $Revision: 5738 $, $Date: 2009-11-12 22:28:08 +0900 (木, 12 11 2009) $
 * @author  matsuse
 * @author  Last commit: $Author: kumano $
 */
public abstract class PCTUserResultOrderInfoFinder
        extends AbstractDBFinder
{
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    //  private String  $classVar ;

    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (Prefix '_')
    //------------------------------------------------------------
    //  private String  _instanceVar ;
    /**
     * 検索結果格納用のリスト
     */
    private List<PCTUserResultOrderInfo> _results;

    /**
     * メッセージ
     */
    private String _message = "";

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * データベースコネクションを指定してインスタンスを生成します。
     * @param conn 接続済みのデータベースコネクション
     */
    public PCTUserResultOrderInfoFinder(Connection conn)
    {
        super(conn, PCTPickingResult.$storeMetaData);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * データベースを検索し、対象データを取得します。<br>
     * 取得上限は無制限です。(LIMIT_UNLIMTED)
     * 
     * @param key 検索のためのKey
     * @return 検索結果の件数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public int search(SearchKey key)
            throws ReadWriteException
    {
        PCTRetrievalInParameter inParam = ((PCTUserResultOrderInfoSearchKey)key).getInParam();

        // 対象データを検索する
        PCTPickingResult[] infos = searchDetails(inParam);
        if (infos.length == 0)
        {
            return 0;
        }

        // 合計用のエンティティ
        PCTPickingResult total = new PCTPickingResult();

        // システム情報の取得
        PCTSystem system = searchPCTSystem();

        // 生産率の設定
        rankProductionRate(infos, total, system, inParam);

        // ソートを行なう
        PCTPickingResult[] sortInfo = listSort(infos);

        _results = new ArrayList<PCTUserResultOrderInfo>();
        for (PCTPickingResult info : sortInfo)
        {
            PCTUserResultOrderInfo result = new PCTUserResultOrderInfo();

            _results.add(result);

            result.setCount(sortInfo.length);
            result.setDetail(info);
            result.setTotal(total);
        }

        // 結果の件数を返す
        return _results.size();
    }

    /**
     * 検索結果を取得します。
     * @param start 取得開始行 (0 = 先頭)
     * @param end 取得終了行 (0 = 最後まで取得)
     * @return 検索結果
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public Entity[] getEntities(int start, int end)
            throws ReadWriteException
    {
        int startIndex = start <= _results.size() ? start
                                                 : _results.size();

        int endIndex = end;
        if (end == 0 || end > _results.size())
        {
            endIndex = _results.size();
        }

        return _results.subList(startIndex, endIndex).toArray(new PCTUserResultOrderInfo[0]);
    }

    //------------------------------------------------------------
    // accessor methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------
    /**
     * キーを返します。
     */
    protected abstract String getKey(PCTPickingResult info);

    /**
     * 明細一覧を検索します。
     * 
     * @param inParam 入力パラメータ
     * @return PCTPickingResult[] 検索結果のPCTPickingResult[]
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected abstract PCTPickingResult[] searchDetails(PCTRetrievalInParameter inParam)
            throws ReadWriteException;

    /**
     * ランクの設定をします。
     * @param info PCTPickingResult
     * @param system PCTSystem
     */
    protected void setRank(PCTPickingResult info, PCTSystem system)
    {
    }

    /**
     * ランクによるフィルタリングを行います。<br>
     * 必要な場合のみオーバーライドしてください。
     * @param map 検索対象のPCTPickingResultを格納したmap
     * @param allowRank 表示可能なランク
     */
    protected void filterByRank(LinkedHashMap<String, PCTPickingResult> map, String allowRank)
    {
    }

    /**
     * @see AbstractDBFinder#createEntity()
     */
    protected AbstractEntity createEntity()
    {
        return (new PCTPickingResult());
    }

    /**
     * 曜日検索条件に変換します。
     * 
     * @param inParam 入力パラメータ
     * @return 曜日検索条件
     */
    protected List<Integer> toDayOfWeekCondition(PCTRetrievalInParameter inParam)
    {
        List<Integer> list = new ArrayList<Integer>();
        if (inParam.isUseDayOfWeekFlag())
        {
            if (inParam.isMondayFlag())
            {
                list.add(Integer.parseInt(PCTRetrievalInParameter.MONDAY));
            }
            if (inParam.isTuesdayFlag())
            {
                list.add(Integer.parseInt(PCTRetrievalInParameter.TUESDAY));
            }
            if (inParam.isWednesdayFlag())
            {
                list.add(Integer.parseInt(PCTRetrievalInParameter.WEDNESDAY));
            }
            if (inParam.isThursdayFlag())
            {
                list.add(Integer.parseInt(PCTRetrievalInParameter.THURSDAY));
            }
            if (inParam.isFridayFlag())
            {
                list.add(Integer.parseInt(PCTRetrievalInParameter.FRIDAY));
            }
            if (inParam.isSaturdayFlag())
            {
                list.add(Integer.parseInt(PCTRetrievalInParameter.SATURDAY));
            }
            if (inParam.isSundayFlag())
            {
                list.add(Integer.parseInt(PCTRetrievalInParameter.SUNDAY));

            }
        }
        return list;
    }

    /**
     * レベル検索条件に変換します。
     * 
     * @param inParam 入力パラメータ
     * @return レベル検索条件
     */
    protected List<String> toLevelCondition(PCTRetrievalInParameter inParam)
    {
        List<String> list = new ArrayList<String>();

        if (inParam.isLevelAFlag())
        {
            list.add(PCTRetrievalInParameter.LEVEL_A);
        }
        if (inParam.isLevelBFlag())
        {
            list.add(PCTRetrievalInParameter.LEVEL_B);
        }
        if (inParam.isLevelCFlag())
        {
            list.add(PCTRetrievalInParameter.LEVEL_C);
        }
        return list;
    }

    /**
     * 合計を計算して返します。
     * 
     * @param map 加算対象一覧
     * @param total 加算結果表
     */
    protected void calculateTotal(PCTPickingResult[] infos, PCTPickingResult total)
    {
        total.clear();

        for (PCTPickingResult info : infos)
        {
            BigDecimal tmpDec = new BigDecimal(0);

            // 人数
            BigDecimal countWorker = total.getBigDecimal(PCTUserResultOrderInfo.COUNT_WORKER, new BigDecimal(0));
            countWorker = countWorker.add(info.getBigDecimal(PCTUserResultOrderInfo.COUNT_WORKER, new BigDecimal(0)));
            total.setValue(PCTUserResultOrderInfo.COUNT_WORKER, countWorker, false);
            // 台数
            BigDecimal countTerminal = total.getBigDecimal(PCTUserResultOrderInfo.COUNT_TERMINAL, new BigDecimal(0));
            countTerminal =
                    countTerminal.add(info.getBigDecimal(PCTUserResultOrderInfo.COUNT_TERMINAL, new BigDecimal(0)));
            total.setValue(PCTUserResultOrderInfo.COUNT_TERMINAL, countTerminal, false);
            // 稼働時間
            total.setValue(PCTPickingResult.OPERATE_TIME,
                    new BigDecimal(info.getOperateTime() + total.getOperateTime()), false);
            // 作業数量(ロット)
            BigDecimal workTotal = total.getBigDecimal(PCTUserResultOrderInfo.WORK_TOTAL, new BigDecimal(0));
            workTotal = workTotal.add(info.getBigDecimal(PCTPickingResult.WORK_QTY, new BigDecimal(0)));
            total.setValue(PCTUserResultOrderInfo.WORK_TOTAL, workTotal, false);
            // 作業数量(バラ)
            BigDecimal pieceTotal = total.getBigDecimal(PCTUserResultOrderInfo.PIECE_TOTAL, new BigDecimal(0));
            pieceTotal = pieceTotal.add(info.getBigDecimal(PCTPickingResult.PIECE_QTY, new BigDecimal(0)));
            total.setValue(PCTUserResultOrderInfo.PIECE_TOTAL, pieceTotal, false);
            // 作業回数(明細数)
            BigDecimal lineTotal = total.getBigDecimal(PCTUserResultOrderInfo.LINE_TOTAL, new BigDecimal(0));
            lineTotal = lineTotal.add(info.getBigDecimal(PCTPickingResult.WORK_CNT, new BigDecimal(0)));
            total.setValue(PCTUserResultOrderInfo.LINE_TOTAL, lineTotal, false);
            // 作業回数(オーダー数)
            BigDecimal orderTotal = total.getBigDecimal(PCTUserResultOrderInfo.ORDER_TOTAL, new BigDecimal(0));
            orderTotal = orderTotal.add(info.getBigDecimal(PCTPickingResult.ORDER_CNT, new BigDecimal(0)));
            total.setValue(PCTUserResultOrderInfo.ORDER_TOTAL, orderTotal, false);
            // 作業回数(予定オーダー数)
            BigDecimal planOrderTotal = total.getBigDecimal(PCTUserResultOrderInfo.PLAN_ORDER_TOTAL, new BigDecimal(0));
            planOrderTotal = planOrderTotal.add(info.getBigDecimal(PCTPickingResult.PLAN_ORDER_CNT, new BigDecimal(0)));
            total.setValue(PCTUserResultOrderInfo.PLAN_ORDER_TOTAL, planOrderTotal, false);
            // 集品箱数
            BigDecimal boxTotal = total.getBigDecimal(PCTUserResultOrderInfo.BOX_TOTAL, new BigDecimal(0));
            boxTotal = boxTotal.add(info.getBigDecimal(PCTPickingResult.ORDER_CNT, new BigDecimal(0)));
            total.setValue(PCTUserResultOrderInfo.BOX_TOTAL, boxTotal, false);
            // 作業時間
            total.setValue(PCTPickingResult.WORK_TIME, new BigDecimal(info.getWorkTime() + total.getWorkTime()), false);
            // 実作業時間
            total.setValue(PCTPickingResult.REAL_WORK_TIME, new BigDecimal(info.getRealWorkTime()
                    + total.getRealWorkTime()), false);
            // ミススキャン数
            total.setValue(PCTPickingResult.MISS_SCAN_CNT, new BigDecimal(info.getMissScanCnt()
                    + total.getMissScanCnt()), false);
            // バッテリ交換時間
            total.setValue(PCTPickingResult.BATTERY_CHANGE_TIME, new BigDecimal(info.getBatteryChangeTime()
                    + total.getBatteryChangeTime()), false);
            // 休憩時間
            total.setValue(PCTPickingResult.BREAK_TIME, new BigDecimal(info.getBreakTime() + total.getBreakTime()),
                    false);

            /* 
             * 実稼働時間やオーダー数、行数等で除算した値をセットする
             */
            BigDecimal tmpQty = new BigDecimal(0);

            // 実稼働時間(時)
            // (稼働時間 - バッテリ交換時間 - 休憩時間) / 60 / 60
            BigDecimal realOperateTime =
                    new BigDecimal(info.getOperateTime() - info.getBatteryChangeTime() - info.getBreakTime());
            if (realOperateTime.doubleValue() > 0)
            {
                realOperateTime = realOperateTime.divide(new BigDecimal(3600), 3, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                realOperateTime = new BigDecimal(0);
            }

            // 作業数量(ロット)/H
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.WORK_HOUR, new BigDecimal(0));
            if (realOperateTime.doubleValue() > 0 && info.getWorkQty() > 0)
            {
                tmpQty = new BigDecimal(info.getWorkQty());
                tmpQty = tmpQty.divide(realOperateTime, 1, BigDecimal.ROUND_HALF_UP).add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.WORK_HOUR, tmpQty, false);
            // 作業数量(バラ)/H
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.PIECE_HOUR, new BigDecimal(0));
            if (realOperateTime.doubleValue() > 0 && info.getPieceQty() > 0)
            {
                tmpQty = new BigDecimal(info.getPieceQty());
                tmpQty = tmpQty.divide(realOperateTime, 1, BigDecimal.ROUND_HALF_UP).add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.PIECE_HOUR, tmpQty, false);
            // 作業回数(明細数)/H
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.LINE_HOUR, new BigDecimal(0));
            if (realOperateTime.doubleValue() > 0 && info.getWorkCnt() > 0)
            {
                tmpQty = new BigDecimal(info.getWorkCnt());
                tmpQty = tmpQty.divide(realOperateTime, 1, BigDecimal.ROUND_HALF_UP).add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.LINE_HOUR, tmpQty, false);
            // 作業回数(オーダー数)/H
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.ORDER_HOUR, new BigDecimal(0));
            if (realOperateTime.doubleValue() > 0 && info.getOrderCnt() > 0)
            {
                tmpQty = new BigDecimal(info.getOrderCnt());
                tmpQty = tmpQty.divide(realOperateTime, 1, BigDecimal.ROUND_HALF_UP).add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.ORDER_HOUR, tmpQty, false);
            // 作業回数(予定オーダー数)/H
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.PLAN_ORDER_HOUR, new BigDecimal(0));
            if (realOperateTime.doubleValue() > 0 && info.getPlanOrderCnt() > 0)
            {
                tmpQty = new BigDecimal(info.getPlanOrderCnt());
                tmpQty = tmpQty.divide(realOperateTime, 1, BigDecimal.ROUND_HALF_UP).add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.PLAN_ORDER_HOUR, tmpQty, false);
            // 集品箱数/H
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.BOX_HOUR, new BigDecimal(0));
            if (realOperateTime.doubleValue() > 0 && info.getOrderCnt() > 0)
            {
                tmpQty = new BigDecimal(info.getOrderCnt());
                tmpQty = tmpQty.divide(realOperateTime, 1, BigDecimal.ROUND_HALF_UP).add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.BOX_HOUR, tmpQty, false);
            // 集品箱数/オーダー
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.BOX_ORDER, new BigDecimal(0));
            if (info.getOrderCnt() > 0 && info.getOrderCnt() > 0)
            {
                tmpQty = new BigDecimal(info.getOrderCnt());
                tmpQty = tmpQty.divide(new BigDecimal(info.getOrderCnt()), 1, BigDecimal.ROUND_HALF_UP).add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.BOX_ORDER, tmpQty, false);
            // 集品箱数/予定オーダー
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.BOX_PLAN_ORDER, new BigDecimal(0));
            if (info.getPlanOrderCnt() > 0 && info.getOrderCnt() > 0)
            {
                tmpQty = new BigDecimal(info.getOrderCnt());
                tmpQty = tmpQty.divide(new BigDecimal(info.getPlanOrderCnt()), 1, BigDecimal.ROUND_HALF_UP).add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.BOX_PLAN_ORDER, tmpQty, false);
            // 作業回数(明細数)/オーダー
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.LINE_ORDER, new BigDecimal(0));
            if (info.getOrderCnt() > 0 && info.getWorkCnt() > 0)
            {
                tmpQty = new BigDecimal(info.getWorkCnt());
                tmpQty = tmpQty.divide(new BigDecimal(info.getOrderCnt()), 1, BigDecimal.ROUND_HALF_UP).add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.LINE_ORDER, tmpQty, false);
            // 作業回数(明細数)/予定オーダー
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.LINE_PLAN_ORDER, new BigDecimal(0));
            if (info.getPlanOrderCnt() > 0 && info.getWorkCnt() > 0)
            {
                tmpQty = new BigDecimal(info.getWorkCnt());
                tmpQty = tmpQty.divide(new BigDecimal(info.getPlanOrderCnt()), 1, BigDecimal.ROUND_HALF_UP).add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.LINE_PLAN_ORDER, tmpQty, false);
            // 作業数量(ロット)/行
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.LOT_LINE, new BigDecimal(0));
            if (info.getWorkCnt() > 0 && info.getWorkQty() > 0)
            {
                tmpQty = new BigDecimal(info.getWorkQty());
                tmpQty = tmpQty.divide(new BigDecimal(info.getWorkCnt()), 1, BigDecimal.ROUND_HALF_UP).add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.LOT_LINE, tmpQty, false);
            // 作業数量(バラ)/行
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.PIECE_LINE, new BigDecimal(0));
            if (info.getWorkCnt() > 0 && info.getPieceQty() > 0)
            {
                tmpQty = new BigDecimal(info.getPieceQty());
                tmpQty = tmpQty.divide(new BigDecimal(info.getWorkCnt()), 1, BigDecimal.ROUND_HALF_UP).add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.PIECE_LINE, tmpQty, false);
            // ミス率
            tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.MISS_PER, new BigDecimal(0));
            if (info.getWorkQty() > 0 && info.getMissScanCnt() > 0)
            {
                tmpQty = new BigDecimal(info.getMissScanCnt());
                tmpQty = tmpQty.divide(new BigDecimal(info.getWorkQty()), 3, BigDecimal.ROUND_HALF_UP);
                tmpQty = tmpQty.multiply(new BigDecimal(100));
                tmpQty = tmpQty.add(tmpDec);
            }
            else
            {
                tmpQty = tmpDec.add(new BigDecimal(0));
            }
            total.setValue(PCTUserResultOrderInfo.MISS_PER, tmpQty, false);
        }
    }

    /**
     * PCTSYSTEMを検索します。
     * 
     * @return PCTSystem 検索結果のPCTSystem
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected PCTSystem searchPCTSystem()
            throws ReadWriteException
    {
        PCTSystemHandler handler = new PCTSystemHandler(getConnection());

        PCTSystemSearchKey key = new PCTSystemSearchKey();
        key.setARankStandardValueCollect();
        key.setBRankStandardValueCollect();

        return (PCTSystem)handler.find(key)[0];
    }

    /**
     * 生産率の計算を行います。
     * 
     * @param map 対象のPCTPickingResult一覧
     * @param total 対象のPCTPickingResultの集計
     * @param system 対象のPCTSystem一覧
     * @param inParam 対象のPCTRetrievalInParameter一覧
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void rankProductionRate(PCTPickingResult[] infos, PCTPickingResult total, PCTSystem system,
            PCTRetrievalInParameter inParam)
            throws ReadWriteException
    {
        String displayRank = inParam.getDisplayRank();
        String collect = inParam.getCollectCondition();
        
        // 基準値を取得
        BigDecimal standard;
        BigDecimal tmpDec = new BigDecimal(0);

        if (infos.length != 0)
        {
            // 合計の計算
            calculateTotal(infos, total);

            // 表示ランクがロット基準の場合
            if (PCTRetrievalInParameter.DISPLAY_RANK_LOT.equals(displayRank))
            {
                // ロット/Hの平均値を基準値とする
                tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.WORK_HOUR, new BigDecimal(0));
            }
            // 表示ランクがオーダー基準の場合
            else if (PCTRetrievalInParameter.DISPLAY_RANK_ORDER.equals(displayRank))
            {
                if (PCTRetrievalInParameter.COLLECT_CONDITION_WORKER.equals(collect))
                {
                    // オーダー数/Hの平均値を基準値とする
                    tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.ORDER_HOUR, new BigDecimal(0));
                }
                else
                {
                    // 予定オーダー数/Hの平均値を基準値とする
                    tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.PLAN_ORDER_HOUR, new BigDecimal(0));
                }
                    
            }
            // 表示ランクが行基準の場合
            else
            {
                // 行/Hの平均値を基準値とする
                tmpDec = total.getBigDecimal(PCTUserResultOrderInfo.LINE_HOUR, new BigDecimal(0));
            }

            if (tmpDec.doubleValue() > 0)
            {
                standard = tmpDec.divide(new BigDecimal(infos.length), 1, BigDecimal.ROUND_HALF_UP);
            }
            else
            {
                standard = new BigDecimal(0);
            }

            // 各明細の生産性を設定
            for (PCTPickingResult info : infos)
            {
            	info.setValue(PCTUserResultOrderInfo.LEVEL, info.getRank(), false);
                rankProductionRate(info, standard, displayRank, collect, system);
            }
        }
    }

    /**
     * 表示ランクよりレートを設定します。
     * @param info 対象
     * @param standard 基準値
     * @param displayRank 表示ランク
     * @param collect 集約単位
     * @param system PCTSystem
     */
    protected void rankProductionRate(PCTPickingResult info, BigDecimal standard, String displayRank, String collect, PCTSystem system)
    {
        // 計算対象を取得
        BigDecimal base;
        // 表示ランクがロット基準の場合
        if (PCTRetrievalInParameter.DISPLAY_RANK_LOT.equals(displayRank))
        {
            base = info.getBigDecimal(PCTPickingResult.WORK_QTY, new BigDecimal(0));
        }
        // 表示ランクがオーダー基準の場合
        else if (PCTRetrievalInParameter.DISPLAY_RANK_ORDER.equals(displayRank))
        {
            if (PCTRetrievalInParameter.COLLECT_CONDITION_WORKER.equals(collect))
            {
                base = info.getBigDecimal(PCTPickingResult.ORDER_CNT, new BigDecimal(0));
            }
            else
            {
                base = info.getBigDecimal(PCTPickingResult.PLAN_ORDER_CNT, new BigDecimal(0));
            }
        }
        // 表示ランクが行基準の場合
        else
        {
            base = info.getBigDecimal(PCTPickingResult.WORK_CNT, new BigDecimal(0));
        }

        // 実稼働時間(時)
        // (稼働時間 - バッテリ交換時間 - 休憩時間) / 60 / 60
        BigDecimal hour = new BigDecimal(info.getOperateTime() - info.getBatteryChangeTime() - info.getBreakTime());
        if (hour.doubleValue() > 0)
        {
            hour = hour.divide(new BigDecimal(3600), 3, BigDecimal.ROUND_HALF_UP);
        }
        else
        {
            hour = new BigDecimal(0);
        }

        // 生産性
        // 表示ランクがロット基準の場合
        // 　1明細の計算対象（作業数量 / 実稼働時間）/ 基準値 * 100
        // 表示ランクがオーダー基準の場合
        // 　1明細の計算対象（作業回数(オーダー回数) / 実稼働時間）/ 基準値 * 100
        // 表示ランクが行基準の場合
        // 　1明細の計算対象（作業回数(明細数) / 実稼働時間）/ 基準値 * 100
        BigDecimal rate = new BigDecimal(0);

        if (base.doubleValue() > 0 && hour.doubleValue() > 0 && standard.doubleValue() > 0)
        {
            rate = base.divide(hour, 1, BigDecimal.ROUND_HALF_UP);
            rate = rate.divide(standard, 3, BigDecimal.ROUND_HALF_UP);
            rate = rate.multiply(new BigDecimal(100));
        }

        info.setValue(PCTUserResultOrderInfo.PRODUCTION_RATE, rate, false);

        setRank(info, system);
    }

    /**
     * リストセルに表示するデータを降順にソートします。<BR>
     * ・生産率の降順<BR>
     * <BR>
     * 
     * @param param
     *            ソート前のオブジェクト
     * @throws Exception
     *             データベースエラーが発生した場合に報告します。
     * @return sortParam[] 降順ソート後のオブジェクト
     */
    @SuppressWarnings("unchecked")
    protected PCTPickingResult[] listSort(PCTPickingResult[] infos)
    {
        Comparator desc = new Comparator()
        {
            public int compare(Object obj0, Object obj1)
            {
                Double data1 =
                        (Double)((PCTPickingResult)obj0).getBigDecimal(PCTUserResultOrderInfo.PRODUCTION_RATE).doubleValue();
                Double data2 =
                        (Double)((PCTPickingResult)obj1).getBigDecimal(PCTUserResultOrderInfo.PRODUCTION_RATE).doubleValue();

                int ret = 0;
                // カナの昇順
                if ((ret = data2.compareTo(data1)) == 0)
                {
                    // カナが同じ場合はIDの昇順
                    String id0 = getKey((PCTPickingResult)obj0);
                    String id1 = getKey((PCTPickingResult)obj1);;
                    ret = id0.compareTo(id1);
                }
                return ret;
            }
        };

        // 配列をソート
        Arrays.sort(infos, desc);

        return infos;
    }

    /**
     * 現在のメッセージを設定します。
     * @param message セットするメッセージ
     */
    protected void setMessage(String message)
    {
        this._message = message;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 5738 $,$Date: 2009-11-12 22:28:08 +0900 (木, 12 11 2009) $");
    }

    /**
     * メッセージを取得します。
     * @return メッセージ
     */
    public String getMessage()
    {
        return _message;
    }
}
