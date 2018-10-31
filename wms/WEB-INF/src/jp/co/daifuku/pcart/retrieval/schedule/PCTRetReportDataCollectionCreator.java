// $Id: PCTRetReportDataCollectionCreator.java 7247 2010-02-26 05:46:27Z okayama $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTRetHostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetHostSend;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.PCTReportRetrieval;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * PCT出庫実績 予定単位（集約）のデータ報告作成処理を行う。<BR>
 * <BR>
 * Designer : k.bingo <BR>
 * Maker : k.bingo
 * @version $Revision: 7247 $, $Date: 2010-02-26 14:46:27 +0900 (金, 26 2 2010) $
 * @author admin
 * @author Last commit: $Author: okayama $
 */
public class PCTRetReportDataCollectionCreator
        extends PCTRetReportDataCreator
{
    // ------------------------------------------------------------
    // fields (upper case only)
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // class variables (prefix '$')
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // instance variables (prefix '_')
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * @param conn データベースConnection<BR>
     * @param fileInfo REPORT-ID<BR>
     * @param caller 
     */
    public PCTRetReportDataCollectionCreator(Connection conn, String fileInfo, Class caller)
    {
        super(conn, fileInfo, caller);
    }

    /**
     * @param conn データベースConnection<BR>
     * @param fileInfo REPORT-ID<BR>
     * @param inParam 検索条件<BR>
     * @param caller 
     */
    public PCTRetReportDataCollectionCreator(Connection conn, String fileInfo, PCTRetrievalInParameter[] inParam,
            Class caller)
    {
        super(conn, fileInfo, inParam, caller);
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * 出庫実績 予定単位（集約）のデータ報告作成処理を行います。<BR>
     * 
     * @return boolean 出庫報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        // ローカルファイル削除フラグ
        boolean deleteFile = false;

        // PCT出庫実績ファイルエンティティクラスの生成
        PCTReportRetrieval rPCTRetEntity = new PCTReportRetrieval();
        // PCT出庫実績ファイルエンティティクラスを元に、ファイルハンドラクラス生成
        FileHandler handler = AbstractFileHandler.getInstance(rPCTRetEntity);

        // 報告データ件数を初期化します。
        setReportCount(0);

        // 出庫予定情報から報告データを抽出する為のFinderを作成します。
        PCTRetPlanFinder pFinder = new PCTRetPlanFinder(getConnection());

        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(getReportId());

            // PCT出庫実績情報の取得
            PCTRetPlanSearchKey hKey = new PCTRetPlanSearchKey();

            setSearchKey(hKey);

            pFinder.open(true);

            // PCT出庫予定情報を検索します。
            if (pFinder.search(hKey) <= 0)
            {
                // 対象データはありませんでした。
                setMessage(WmsMessageFormatter.format(6003011));
                return true;
            }
            // PCT出庫実績報告ファイル作成処理
            while (pFinder.hasNext())
            {
                // 検索結果を100件づつ取得し、ファイルへ出力していく。
                PCTRetPlan[] retrievalPlan = (PCTRetPlan[])pFinder.getEntities(RESULT_READ_QTY);

                // PCT出庫予定情報取得件数分以下の処理を繰り返す。
                for (PCTRetPlan sPlan : retrievalPlan)
                {
                    if (getReportCount() == 0)
                    {
                        try
                        {
                            // 初回の出力（出力が0件の）の場合
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                            String sysTime = df.format(new java.util.Date(System.currentTimeMillis()));

                            // ファイル名のセット
                            setResultFileName(getFileName() + sysTime + PCTRetrievalInParameter.EXTENSION);
                            // 一時保存用ファイルに書き込み
                            // ディレクトリが存在しない場合は作成
                            prepareDir(WmsParam.HOSTDATA_LOCAL_PATH + getResultFileName());
                            // PCT出庫実績報告ファイルのopen。
                            handler.open(WmsParam.HOSTDATA_LOCAL_PATH, getResultFileName());
                            // ファイルを作成したのでエラー発生時には削除
                            deleteFile = true;
                        }
                        catch (ReadWriteException e)
                        {
                            // 6003019=指定されたフォルダは無効です。
                            setMessage(WmsMessageFormatter.format(6003019));
                            return false;
                        }
                        // PCT出庫実績報告ファイルの初期化。
                        handler.clear();
                    }

                    // PCT出庫実績報告ファイルに出力処理
                    if (csvWrite(handler, rPCTRetEntity, sPlan))
                    {
                        setReportCount(getReportCount() + 1);

                        // PCT出庫実績送信情報の実績報告区分を送信済みに更新します。
                        PCTRetHostSendAlterKey hstAKey = new PCTRetHostSendAlterKey();
                        hstAKey.setPlanUkey(sPlan.getPlanUkey());
                        updatePCTRetHostSendReportFlag(hstAKey);

                        // 出庫予定情報の実績報告区分を送信済みに更新します。
                        PCTRetPlanAlterKey planAKey = new PCTRetPlanAlterKey();
                        planAKey.setPlanUkey(sPlan.getPlanUkey());
                        updatePCTRetPlanReportFlag(planAKey);

                        // 6001009=データの書き込みは正常に終了しました。
                        setMessage(WmsMessageFormatter.format(6001009));
                    }
                }
            }

            // 処理が正常に完了したのでファイルは削除しない
            deleteFile = false;
        }
        catch (ReadWriteException e)
        {
            // 6007002=データベースエラーが発生しました。メッセージログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            return false;
        }
        catch (NotFoundException e)
        {
            // 6007002=データベースエラーが発生しました。メッセージログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            return false;
        }
        catch (ScheduleException e)
        {
            // 6027009=予期しないエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6027009));
            return false;
        }
        finally
        {
            pFinder.close();

            if (handler.isOpen())
            {
                handler.close();
            }

            // ファイル作成後にExceptionが発生した場合など、作成したファイルを削除する
            if (deleteFile)
            {
                deleteFile(new File(WmsParam.HOSTDATA_LOCAL_PATH, getResultFileName()));
            }
        }
        return true;
    }

    /**
     * @param pKey
     */
    protected void setSearchKey(PCTRetPlanSearchKey pKey)
    {
        // PCT出庫予定情報から予定単位（集約）で抽出する為の条件を作成します。
        // PCT予定情報の状態フラグ = 完了 AND 実績報告区分 = 未報告
        pKey.setKey(PCTRetPlan.STATUS_FLAG, PCTRetPlan.STATUS_FLAG_COMPLETION);
        pKey.setKey(PCTRetPlan.REPORT_FLAG, PCTRetPlan.REPORT_FLAG_NOT_REPORT);

        // PCT予定情報とPCT出庫実績情報の結合
        // 予定一意キー
        pKey.setJoin(PCTRetPlan.PLAN_UKEY, PCTRetHostSend.PLAN_UKEY);

        PCTRetrievalInParameter[] inParam = getInParemeter();

        if (!ArrayUtil.isEmpty(inParam))
        {
            List<String> batchNoList = new ArrayList<String>();
            for (int lc = 0; lc < inParam.length; lc++)
            {
                if (!StringUtil.isBlank(inParam[lc].getBatchNo()))
                {
                    String key_Batchno = inParam[lc].getBatchNo();
                    batchNoList.add(key_Batchno);
                }
            }
            // 複数バッチNoのいずれかと一致する情報のみ、対象とする。
            if (batchNoList.size() > 0)
            {
                pKey.setBatchNo(batchNoList.toArray(new String[batchNoList.size()]), true);
            }
        }

        // 取得項目
        // PCT出庫実績送信情報の状態フラグ
        pKey.setCollect(PCTRetHostSend.STATUS_FLAG, "MAX", PCTRetHostSend.STATUS_FLAG);
        // PCT出庫予定情報の出庫予定日
        pKey.setPlanDayCollect();
        // PCT出庫予定情報の予定一意キー
        pKey.setPlanUkeyCollect();
        // PCT出庫予定情報の荷主コード
        pKey.setConsignorCodeCollect();
        // PCT出庫予定情報の荷主名称
        pKey.setConsignorNameCollect("MAX");
        // PCT出庫予定情報の得意先コード
        pKey.setRegularCustomerCodeCollect();
        // PCT出庫予定情報の得意先名称
        pKey.setRegularCustomerNameCollect("MAX");
        // PCT出庫予定情報の出荷先コード
        pKey.setCustomerCodeCollect();
        // PCT出庫予定情報の出荷先名称
        pKey.setCustomerNameCollect("MAX");
        // PCT出庫予定情報の出荷先分類
        pKey.setCustomerCategoryCollect();
        // PCT出庫予定情報の出荷伝票No.
        pKey.setShipTicketNoCollect();
        // PCT出庫予定情報の出荷伝票行No.
        pKey.setShipLineNoCollect();
        // PCT出庫予定情報の作業枝番
        pKey.setBranchNoCollect("MAX");
        // PCT出庫予定情報のバッチNo.
        pKey.setBatchNoCollect("MAX");
        // PCT出庫予定情報のバッチSeqNo.
        pKey.setBatchSeqNoCollect();
        // PCT出庫予定情報の予定オーダーNo
        pKey.setPlanOrderNoCollect("MAX");
        // PCT出庫予定情報のオーダー情報コメント
        pKey.setOrderInfoCollect("MAX");
        // PCT出庫予定情報の通番
        pKey.setThroughNoCollect("MAX");
        // PCT出庫予定情報のオーダー内商品数
        pKey.setOrderItemQtyCollect("MAX");
        // PCT出庫予定情報のオーダー通番
        pKey.setOrderThroughNoCollect("MAX");
        // PCT出庫予定情報のオーダー通番合計
        pKey.setOrderThroughNoCntCollect("MAX");
        // PCT出庫予定情報の汎用フラグ
        pKey.setGeneralFlagCollect("MAX");
        // PCT出庫予定情報のシュートNo
        pKey.setShootNoCollect("MAX");
        // PCT出庫予定情報の予定エリア
        pKey.setPlanAreaNoCollect();
        // PCT出庫予定情報の予定ゾーン
        pKey.setPlanZoneNoCollect("MAX");
        // PCT出庫予定情報の作業ゾーン
        pKey.setWorkZoneNoCollect("MAX");
        // PCT出庫予定情報の予定棚
        pKey.setPlanLocationNoCollect();
        // PCT出庫予定情報の商品コード
        pKey.setItemCodeCollect();
        // PCT出庫予定情報の商品名称
        pKey.setItemNameCollect("MAX");
        // PCT出庫予定情報のケース入数
        pKey.setEnteringQtyCollect("MAX");
        // PCT出庫予定情報のボール入数
        pKey.setBundleEnteringQtyCollect("MAX");
        // PCT出庫予定情報のロット入数
        pKey.setLotEnteringQtyCollect("MAX");
        // PCT出庫予定情報のJANコード
        pKey.setJanCollect("MAX");
        // PCT出庫予定情報のケースITF
        pKey.setItfCollect("MAX");
        // PCT出庫予定情報のボールITF
        pKey.setBundleItfCollect("MAX");
        // PCT出庫予定情報の基準日付
        pKey.setUseByDateCollect("MAX");
        // PCT出庫予定情報のアイテム情報コメント
        pKey.setItemInfoCollect("MAX");
        // PCT出庫予定情報の予定ロットNo
        pKey.setPlanLotNoCollect("MAX");
        // PCT出庫予定情報の予定数
        pKey.setPlanQtyCollect("MAX");
        // PCT出庫予定情報の実績数
        pKey.setResultQtyCollect("MAX");
        // PCT出庫予定情報の欠品数
        pKey.setShortageQtyCollect("MAX");
        // PCT出庫予定情報の作業秒数
        pKey.setCollect(PCTRetHostSend.WORK_SECOND, "SUM", PCTRetHostSend.WORK_SECOND);

        // 集約条件
        // 予定一意キー
        pKey.setPlanUkeyGroup();
        // 予定日
        pKey.setPlanDayGroup();
        // 荷主コード
        pKey.setConsignorCodeGroup();
        // 得意先コード
        pKey.setRegularCustomerCodeGroup();
        // 出荷先コード
        pKey.setCustomerCodeGroup();
        // 出荷先分類
        pKey.setCustomerCategoryGroup();
        // バッチSeqNo.
        pKey.setBatchSeqNoGroup();
        // 予定オーダーNo
        pKey.setPlanOrderNoGroup();
        // 出荷伝票No.
        pKey.setShipTicketNoGroup();
        // 出荷伝票行No.
        pKey.setShipLineNoGroup();
        // 商品コード
        pKey.setItemCodeGroup();
        // エリアNo.
        pKey.setPlanAreaNoGroup();
        // 棚No.
        pKey.setPlanLocationNoGroup();

        // 取得順序
        // 予定日
        pKey.setPlanDayOrder(true);
        // 荷主コード
        pKey.setConsignorCodeOrder(true);
        // 得意先コード
        pKey.setRegularCustomerCodeOrder(true);
        // 出荷先コード
        pKey.setCustomerCodeOrder(true);
        // 出荷先分類
        pKey.setCustomerCategoryOrder(true);
        // バッチSeqNo.
        pKey.setBatchSeqNoOrder(true);
        // 予定オーダーNo
        pKey.setPlanOrderNoOrder(true);
        // 出荷伝票No.
        pKey.setShipTicketNoOrder(true);
        // 出荷伝票行No.
        pKey.setShipLineNoOrder(true);
        // 商品コード
        pKey.setItemCodeOrder(true);
        // エリアNo.
        pKey.setPlanAreaNoOrder(true);
        // 棚No.
        pKey.setPlanLocationNoOrder(true);
    }

    // ------------------------------------------------------------
    // accessor methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // package methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // protected methods
    // ------------------------------------------------------------

    /**
     * PCT出庫実績報告の出力内容をエンティティにセットし、PCT出庫実績報告CSVファイルに出力ます。<BR>
     * 
     * @param handler ファイルハンドラ<BR>
     * @param rPCTRetEntity 出力エンティティ<BR>
     * @param pctRetPlan PCT出庫予定情報エンティティ<BR>
     * @return boolean CSVファイルの出力に成功した場合は、Trueを返します。<BR>
     * @throws ReadWriteException ファイルアクセスでエラーが発生した場合に通知されます。<BR>
     */
    protected boolean csvWrite(FileHandler handler, PCTReportRetrieval rPCTRetEntity, PCTRetPlan pctRetPlan)
            throws ReadWriteException
    {
        // PCT出庫実績報告CSVファイルの出力内容を編集します。
        // 取消区分
        rPCTRetEntity.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        // 予定日
        rPCTRetEntity.setPlanDay(pctRetPlan.getPlanDay());
        // 荷主コード
        rPCTRetEntity.setConsignorCode(pctRetPlan.getConsignorCode());
        // 荷主名称
        rPCTRetEntity.setConsignorName(pctRetPlan.getConsignorName());
        // 得意先コード
        rPCTRetEntity.setRegularCustomerCode(pctRetPlan.getRegularCustomerCode());
        // 得意先名称
        rPCTRetEntity.setRegularCustomerName(pctRetPlan.getRegularCustomerName());
        // 出荷先コード
        rPCTRetEntity.setCustomerCode(pctRetPlan.getCustomerCode());
        // 出荷先名称
        rPCTRetEntity.setCustomerName(pctRetPlan.getCustomerName());
        // 出荷先分類
        rPCTRetEntity.setCustomerCategory(pctRetPlan.getCustomerCategory());
        // 出荷伝票No.
        rPCTRetEntity.setShipTicketNo(pctRetPlan.getShipTicketNo());
        // 出荷伝票行No.
        rPCTRetEntity.setShipLineNo(pctRetPlan.getShipLineNo());
        // 作業枝番
        rPCTRetEntity.setBranchNo(pctRetPlan.getBranchNo());
        // バッチNo.
        rPCTRetEntity.setBatchNo(pctRetPlan.getBatchNo());
        // バッチSeqNo.
        rPCTRetEntity.setBatchSeqNo(pctRetPlan.getBatchSeqNo());
        // 予定オーダーNo.
        rPCTRetEntity.setPlanOrderNo(pctRetPlan.getPlanOrderNo());
        // オーダー情報コメント
        rPCTRetEntity.setOrderInfo(pctRetPlan.getOrderInfo());
        // 通番
        rPCTRetEntity.setThroughNo(pctRetPlan.getThroughNo());
        // オーダー内商品数
        rPCTRetEntity.setOrderItemQty(pctRetPlan.getOrderItemQty());
        // オーダー通番
        rPCTRetEntity.setOrderThroughNo(pctRetPlan.getOrderThroughNo());
        // オーダー通番合計
        rPCTRetEntity.setOrderThroughNoCnt(pctRetPlan.getOrderThroughNoCnt());
        // 汎用フラグ
        rPCTRetEntity.setGeneralFlag(pctRetPlan.getGeneralFlag());
        // シュートNo
        rPCTRetEntity.setShootNo(pctRetPlan.getShootNo());
        // 予定エリア
        rPCTRetEntity.setPlanAreaNo(pctRetPlan.getPlanAreaNo());
        // 予定ゾーン
        rPCTRetEntity.setPlanZoneNo(pctRetPlan.getPlanZoneNo());
        // 作業ゾーン
        rPCTRetEntity.setWorkZoneNo(pctRetPlan.getWorkZoneNo());
        // 予定棚
        rPCTRetEntity.setPlanLocationNo(pctRetPlan.getPlanLocationNo());
        // 商品コード
        rPCTRetEntity.setItemCode(pctRetPlan.getItemCode());
        // 商品名称
        rPCTRetEntity.setItemName(pctRetPlan.getItemName());
        // ケース入数
        rPCTRetEntity.setEnteringQty(pctRetPlan.getEnteringQty());
        // ボール入数
        rPCTRetEntity.setBundleEnteringQty(pctRetPlan.getBundleEnteringQty());
        // ロット入数
        rPCTRetEntity.setLotEnteringQty(pctRetPlan.getLotEnteringQty());
        // JANコード
        rPCTRetEntity.setJan(pctRetPlan.getJan());
        // ケースITF
        rPCTRetEntity.setItf(pctRetPlan.getItf());
        // ボールITF
        rPCTRetEntity.setBundleItf(pctRetPlan.getBundleItf());
        // 基準日付
        rPCTRetEntity.setUseByDate(pctRetPlan.getUseByDate());
        // アイテム情報コメント
        rPCTRetEntity.setItemInfo(pctRetPlan.getItemInfo());
        // 予定ロットNo.
        rPCTRetEntity.setPlanLotNo(pctRetPlan.getPlanLotNo());
        // 予定数
        rPCTRetEntity.setPlanQty(pctRetPlan.getPlanQty());
        // 実績オーダーNo
        rPCTRetEntity.setResultOrderNo(pctRetPlan.getPlanOrderNo());
        // 完了区分：完了
        rPCTRetEntity.setCompleteFlag(pctRetPlan.getStatusFlag());
        // 実績数
        rPCTRetEntity.setResultQty(pctRetPlan.getResultQty());
        // 欠品数
        rPCTRetEntity.setShortageQty(pctRetPlan.getShortageQty());
        // 作業秒数
        rPCTRetEntity.setWorkSecond(pctRetPlan.getBigDecimal(PCTRetHostSend.WORK_SECOND).intValue());

        // CSVファイルに出力します。
        handler.lock();
        handler.create(rPCTRetEntity);
        handler.unLock();

        return true;
    }

    // ------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // utility methods
    // ------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * 
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PCTRetReportDataCollectionCreator.java 7247 2010-02-26 05:46:27Z okayama $";
    }
}
