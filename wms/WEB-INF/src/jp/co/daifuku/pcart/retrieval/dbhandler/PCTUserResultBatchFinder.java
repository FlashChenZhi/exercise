// $Id: PCTUserResultBatchFinder.java 6438 2009-12-11 09:32:59Z kumano $
// Handler v3.8
package jp.co.daifuku.pcart.retrieval.dbhandler;

/*
 * Copyright 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.List;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTPickingResultFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTPickingResultSearchKey;
import jp.co.daifuku.wms.base.entity.PCTPickingResult;
import jp.co.daifuku.wms.base.entity.PCTUserRanking;


/**
 * データベースからPCTPickingResult、PCTUserResultを検索するクラスです。
 * 画面に検索結果を一覧表示する場合このクラスを使用します。
 *
 * @version $Revision: 6438 $, $Date: 2009-12-11 18:32:59 +0900 (金, 11 12 2009) $
 * @author  matsuse
 * @author  Last commit: $Author: kumano $
 */
public class PCTUserResultBatchFinder
        extends PCTUserResultOrderInfoFinder
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * データベースコネクションを指定してインスタンスを生成します。
     * @param conn 接続済みのデータベースコネクション
     */
    public PCTUserResultBatchFinder(Connection conn)
    {
        super(conn);
    }

    //------------------------------------------------------------
    // public methods
    // use {@inheritDoc} in the comment, If the method is overridden.
    //------------------------------------------------------------

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
     * @param info PCTPickingResult
     * @return BatchNo バッチNo
     */
    @Override
    protected String getKey(PCTPickingResult info)
    {
        return info.getBatchNo();
    }

    /**
     * PCTORDERINFOを検索します。
     * 
     * @param inParam 入力パラメータ
     * @return PCTPickingResult[] 検索結果のPCTPickingResult[]
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    @Override
    protected PCTPickingResult[] searchDetails(PCTRetrievalInParameter inParam)
            throws ReadWriteException
    {
        PCTPickingResultSearchKey key = new PCTPickingResultSearchKey();

        // 取得項目
        // バッチNo
        key.setBatchNoCollect();
        // MIN(作業開始日付)
        key.setStartTimeCollect("MIN");
        // MAX(作業終了日付)
        key.setEndTimeCollect("MAX");
        // COUNT(DISTINCT ユーザID)
        key.setCollect(PCTPickingResult.USER_ID, "COUNT(DISTINCT {0})", PCTUserResultOrderInfo.COUNT_WORKER);
        // COUNT(DISTINCT 端末No.)
        key.setCollect(PCTPickingResult.TERMINAL_NO, "COUNT(DISTINCT {0})", PCTUserResultOrderInfo.COUNT_TERMINAL);
        // SUM(稼働時間)
        key.setOperateTimeCollect("SUM");
        // SUM(作業数量)
        key.setWorkQtyCollect("SUM");
        // SUM(作業数量(バラ))
        key.setPieceQtyCollect("SUM");
        // SUM(作業回数(明細数))
        key.setWorkCntCollect("SUM");
        // SUM(作業回数(作業オーダー数))
        key.setOrderCntCollect("SUM");
        // SUM(作業回数(予定オーダー数))
        key.setPlanOrderCntCollect("SUM");
        // SUM(作業時間)
        key.setWorkTimeCollect("SUM");
        // SUM(実作業時間)
        key.setRealWorkTimeCollect("SUM");
        // SUM(ミススキャン数)
        key.setMissScanCntCollect("SUM");
        // SUM(バッテリ交換時間)
        key.setBatteryChangeTimeCollect("SUM");
        // SUM(休憩時間)
        key.setBreakTimeCollect("SUM");
        // ユーザーレベル
        key.setRankCollect("MAX");

        // 検索条件
        // 荷主コード
        if (!StringUtil.isBlank(inParam.getConsignorCode()))
        {
            // 荷主名称
            key.setConsignorNameCollect("MAX");

            key.setConsignorCode(inParam.getConsignorCode());
        }
        // エリアNo.
        if (!StringUtil.isBlank(inParam.getPlanAreaNo()))
        {
            if (!WmsParam.ALL_AREA_NO.equals(inParam.getPlanAreaNo()))
            {
                // エリア名称
                key.setAreaNameCollect("MAX");

                key.setAreaNo(inParam.getPlanAreaNo());
            }
        }
        // バッチNo.
        if (!StringUtil.isBlank(inParam.getBatchNo()))
        {
            key.setBatchNo(inParam.getBatchNo());
        }

        // 作業日(From)
        if (!StringUtil.isBlank(inParam.getWorkDay()))
        {
            key.setWorkDay(inParam.getWorkDay(), ">=", "", "", true);
        }
        // 作業日(TO)
        if (!StringUtil.isBlank(inParam.getToWorkDay()))
        {
            key.setWorkDay(inParam.getToWorkDay(), "<=", "", "", true);
        }

        // 曜日指定
        List<Integer> list = toDayOfWeekCondition(inParam);
        if (!list.isEmpty())
        {
            int[] tmpWeek = new int[list.size()];
            for (int i = 0; i < list.size(); i++)
            {
                tmpWeek[i] = list.get(i);
            }
            key.setDayOfWeek(tmpWeek, true);
        }
        else
        {
            if (inParam.isUseDayOfWeekFlag())
            {
                // 何もチェックされていない場合は曜日0を検索
                key.setDayOfWeek(PCTRetrievalInParameter.NO_DAY);
            }
        }

        // レベル条件
        if (inParam.isLevelAFlag() || inParam.isLevelBFlag() || inParam.isLevelCFlag())
        {
            List<String> listLevel = toLevelCondition(inParam);
            if (!listLevel.isEmpty())
            {
                String[] tmpLevel = new String[listLevel.size()];
                for (int i = 0; i < listLevel.size(); i++)
                {
                    tmpLevel[i] = listLevel.get(i);
                }
                key.setRank(tmpLevel, false);
            }
        }

        // 集計条件
        // バッチNo
        key.setBatchNoGroup();

        // ソート条件
        // バッチNoの昇順
        key.setBatchNoOrder(true);

        PCTPickingResultFinder finder = null;
        try
        {
            finder = new PCTPickingResultFinder(getConnection());
            finder.open(true);

            int count = finder.search(key);
            if (count == 0)
            {
                return new PCTPickingResult[0];
            }

            return (PCTPickingResult[])finder.getEntities(count);
        }
        finally
        {
            if (finder != null)
            {
                finder.close();
            }
        }
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
        return ("$Revision: 6438 $,$Date: 2009-12-11 18:32:59 +0900 (金, 11 12 2009) $");
    }
}
