// $Id: PCTUserResultWorkerFinder.java 5738 2009-11-12 13:28:08Z kumano $
// Handler v3.8
package jp.co.daifuku.pcart.retrieval.dbhandler;

/*
 * Copyright 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTPickingResultFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTPickingResultSearchKey;
import jp.co.daifuku.wms.base.entity.PCTPickingResult;
import jp.co.daifuku.wms.base.entity.PCTSystem;


/**
 * データベースからPCTPickingResult表を検索しTestViewにマッピングするためのクラスです。
 * 画面に検索結果を一覧表示する場合このクラスを使用します。
 *
 * @version $Revision: 5738 $, $Date: 2009-11-12 22:28:08 +0900 (木, 12 11 2009) $
 * @author  matsuse
 * @author  Last commit: $Author: kumano $
 */
public class PCTUserResultWorkerFinder
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
    public PCTUserResultWorkerFinder(Connection conn)
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
     * @return UserId ユーザID
     */
    @Override
    protected String getKey(PCTPickingResult info)
    {
        return info.getUserId();
    }

    /**
     * PCTPickingResultを検索します。
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

        key.setUserIdCollect();
        // ユーザ名称
        key.setUserNameCollect();
        // MIN(作業開始日付)
        key.setStartTimeCollect("MIN");
        // MAX(作業終了日付)
        key.setEndTimeCollect("MAX");
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
        key.setRankCollect();
        
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
            key.setWorkDay(inParam.getWorkDay(), ">=");
        }
        // 作業日(TO)
        if (!StringUtil.isBlank(inParam.getToWorkDay()))
        {
            key.setWorkDay(inParam.getToWorkDay(), "<=");
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

        // グループ条件
        // ユーザID
        key.setUserIdGroup();
        // ユーザ名称
        key.setUserNameGroup();
        // ランク
        key.setRankGroup();

        // ソート条件
        // ユーザIDの昇順
        key.setUserIdOrder(true);

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

    /**
     * ランクの設定を行います。
     * @param info PCTPickingResult
     * @param system PCTSystem
     */
    @Override
    protected void setRank(PCTPickingResult info, PCTSystem system)
    {
        int rate = info.getBigDecimal(PCTUserResultOrderInfo.PRODUCTION_RATE).intValue();

        // ランクの設定を行う
        if (rate >= system.getARankStandardValue())
        {
            info.setValue(PCTUserResultOrderInfo.RANK, DisplayText.getText("LBL-P0087"), false);
        }
        else if (rate < system.getBRankStandardValue())
        {
            info.setValue(PCTUserResultOrderInfo.RANK, DisplayText.getText("LBL-P0089"), false);
        }
        else
        {
            info.setValue(PCTUserResultOrderInfo.RANK, DisplayText.getText("LBL-P0088"), false);
        }
    }

    /**
     * ランクによるフィルタリングを行います。
     * @param map 検索対象のPCTPickingResultを格納したmap
     * @param allowRank 表示可能なランク
     */
    @Override
    protected void filterByRank(LinkedHashMap<String, PCTPickingResult> map, String allowRank)
    {
        // 全ての場合はフィルタリングを行わない
        if (PCTRetrievalInParameter.RANK_ALL.equals(allowRank))
        {
            return;
        }

        // 各明細の生産性を設定
        for (Iterator<PCTPickingResult> it = map.values().iterator(); it.hasNext();)
        {
            PCTPickingResult info = it.next();
            String rank = (String)info.getValue(PCTUserResultOrderInfo.RANK, "");

            // ランクによる表示設定
            if (PCTRetrievalInParameter.RANK_A.equals(allowRank) && !DisplayText.getText("LBL-P0087").equals(rank))
            {
                it.remove();
            }
            else if (PCTRetrievalInParameter.RANK_B.equals(allowRank) && !DisplayText.getText("LBL-P0088").equals(rank))
            {
                it.remove();
            }
            else if (PCTRetrievalInParameter.RANK_C.equals(allowRank) && !DisplayText.getText("LBL-P0089").equals(rank))
            {
                it.remove();
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
        return ("$Revision: 5738 $,$Date: 2009-11-12 22:28:08 +0900 (木, 12 11 2009) $");
    }
}
