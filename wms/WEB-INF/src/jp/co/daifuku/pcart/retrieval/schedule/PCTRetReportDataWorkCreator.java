// $Id: PCTRetReportDataWorkCreator.java 7247 2010-02-26 05:46:27Z okayama $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.sql.Connection;
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
import jp.co.daifuku.wms.base.dbhandler.PCTRetHostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetHostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanAlterKey;
import jp.co.daifuku.wms.base.entity.PCTRetHostSend;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.fileentity.PCTReportRetrieval;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * PCT出庫実績 作業単位のデータ報告作成処理を行う。<BR>
 * <BR>
 * Designer : k.bingo <BR>
 * Maker : k.bingo
 * @version $Revision: 7247 $, $Date: 2010-02-26 14:46:27 +0900 (金, 26 2 2010) $
 * @author admin
 * @author Last commit: $Author: okayama $
 */
public class PCTRetReportDataWorkCreator
        extends PCTRetReportDataDetailCreator
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
    public PCTRetReportDataWorkCreator(Connection conn, String fileInfo, Class caller)
    {
        super(conn, fileInfo, caller);
    }

    /**
     * @param conn データベースConnection<BR>
     * @param fileInfo REPORT-ID<BR>
     * @param inParam 検索条件<BR>
     * @param caller 
     */
    public PCTRetReportDataWorkCreator(Connection conn, String fileInfo, PCTRetrievalInParameter[] inParam, Class caller)
    {
        super(conn, fileInfo, inParam, caller);
        setPrefixSelectType(false);
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * PCT出庫実績 作業単位のデータ報告作成処理を行います。<BR>
     * 
     * @return boolean 成功:True、失敗:False
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
        PCTRetHostSendFinder pFinder = new PCTRetHostSendFinder(getConnection());

        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(getReportId());

            // PCT出庫実績情報の取得
            PCTRetHostSendSearchKey hKey = new PCTRetHostSendSearchKey();

            // PCT出庫実績送信情報から予定単位（集約）で抽出する為の条件を作成します。
            // PCT予定情報の状態フラグ = 完了 AND 実績報告区分 = 未報告
            hKey.setKey(PCTRetPlan.STATUS_FLAG, PCTRetPlan.STATUS_FLAG_COMPLETION);
            hKey.setKey(PCTRetPlan.REPORT_FLAG, PCTRetPlan.REPORT_FLAG_NOT_REPORT);

            PCTRetrievalInParameter[] inParam = getInParemeter();

            if (!ArrayUtil.isEmpty(inParam))
            {
                List<String> batchSeqNoList = new ArrayList<String>();
                for (int lc = 0; lc < inParam.length; lc++)
                {
                    if (!StringUtil.isBlank(inParam[lc].getBatchSeqNo()))
                    {
                        batchSeqNoList.add(inParam[lc].getBatchSeqNo());
                    }
                }
                // 複数バッチNoのいずれかと一致する情報のみ、対象とする。
                if (batchSeqNoList.size() > 0)
                {
                    hKey.setBatchSeqNo(batchSeqNoList.toArray(new String[batchSeqNoList.size()]), true);
                }
            }

            // PCT予定情報とPCT出庫実績情報の結合
            // 予定一意キー
            hKey.setJoin(PCTRetPlan.PLAN_UKEY, PCTRetHostSend.PLAN_UKEY);

            // 取得項目
            // PCT出庫実績送信情報全て
            hKey.setCollect(new FieldName(PCTRetHostSend.STORE_NAME, FieldName.ALL_FIELDS));

            // 集約条件なし

            // 取得順序
            // 予定日
            hKey.setPlanDayOrder(true);
            // 荷主コード
            hKey.setConsignorCodeOrder(true);
            // 得意先コード
            hKey.setRegularCustomerCodeOrder(true);
            // 出荷先コード
            hKey.setCustomerCodeOrder(true);
            // 出荷先分類
            hKey.setCustomerCategoryOrder(true);
            // バッチSeqNo.
            hKey.setBatchSeqNoOrder(true);
            // 実績オーダーNo
            hKey.setResultOrderNoOrder(true);
            // 出荷伝票No.
            hKey.setShipTicketNoOrder(true);
            // 出荷伝票行No.
            hKey.setShipLineNoOrder(true);
            // 商品コード
            hKey.setItemCodeOrder(true);
            // エリアNo.
            hKey.setPlanAreaNoOrder(true);
            // 棚No.
            hKey.setPlanLocationNoOrder(true);
            // 登録日時
            hKey.setRegistDateOrder(true);

            pFinder.open(true);

            // PCT出庫実績送信情報を検索します。
            if (pFinder.search(hKey) <= 0)
            {
                // 対象データはありませんでした。
                setMessage(WmsMessageFormatter.format(6003011));
                return true;
            }

            String w_planUkey = "";
            // PCT出庫実績送信情報作成処理
            while (pFinder.hasNext())
            {
                // 検索結果を100件づつ取得し、ファイルへ出力していく。
                PCTRetHostSend[] entitySend = (PCTRetHostSend[])pFinder.getEntities(RESULT_READ_QTY);

                // PCT出庫実績送信情報取得件数分以下の処理を繰り返す。
                for (PCTRetHostSend sSend : entitySend)
                {
                    if (getReportCount() == 0)
                    {
                        try
                        {
                            // ファイル名のセット
                            setResultFileName(getFileName() + PCTRetrievalInParameter.EXTENSION);
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
                    if (csvWrite(handler, rPCTRetEntity, sSend))
                    {
                        setReportCount(getReportCount() + 1);

                        if (!sSend.getPlanUkey().equals(w_planUkey))
                        {
                            // PCT出庫実績送信情報の実績報告区分を送信済みに更新します。
                            PCTRetHostSendAlterKey hstAKey = new PCTRetHostSendAlterKey();
                            hstAKey.setPlanUkey(sSend.getPlanUkey());
                            updatePCTRetHostSendReportFlag(hstAKey);

                            // 出庫予定情報の実績報告区分を送信済みに更新します。
                            PCTRetPlanAlterKey planAKey = new PCTRetPlanAlterKey();
                            planAKey.setPlanUkey(sSend.getPlanUkey());
                            updatePCTRetPlanReportFlag(planAKey);

                            // 6001009=データの書き込みは正常に終了しました。
                            setMessage(WmsMessageFormatter.format(6001009));

                            w_planUkey = sSend.getPlanUkey();
                        }
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

    // ------------------------------------------------------------
    // accessor methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // package methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // protected methods
    // ------------------------------------------------------------

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
        return "$Id: PCTRetReportDataWorkCreator.java 7247 2010-02-26 05:46:27Z okayama $";
    }
}
