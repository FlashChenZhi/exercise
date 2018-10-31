// $Id: ReportDataBatchSCH.java 7247 2010-02-26 05:46:27Z okayama $
package jp.co.daifuku.pcart.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.system.schedule.ReportDataBatchSCHParams.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetReportDataCreator;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.ReportDataCreator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;

/**
 * 予定作業報告(バッチ単位)のスケジュール処理を行います。
 *
 * @version $Revision: 7247 $, $Date:: 2010-02-26 14:46:27 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class ReportDataBatchSCH
        extends AbstractSCH
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
    public ReportDataBatchSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // 表示データのセット
        List<Params> result = new ArrayList<Params>();

        // PCT出庫予定情報及びPCT出庫実績報告情報より、取得する。
        DefaultDDBFinder finder = null;

        try
        {
            finder = new DefaultDDBFinder(getConnection(), new PCTRetPlan());
            finder.open(true);

            // 検索処理実行
            finder.search(getQuerySql());

            while (finder.hasNext())
            {
                PCTRetPlan[] entities = (PCTRetPlan[])finder.getEntities(0, WmsParam.MAX_NUMBER_OF_DISP);

                for (PCTRetPlan ent : entities)
                {
                    Params param = new Params();

                    // バッチNo
                    param.set(BATCH_NO, ent.getBatchNo());
                    // バッチSeqNoNo
                    param.set(BATCH_SEQ_NO, ent.getBatchSeqNo());
                    // バッチ完了日時
                    param.set(BATCH_COMPLETE_DATE, ent.getLastUpdateDate());

                    // 戻り値へデータのセット
                    result.add(param);
                }
            }

            // 実績情報作成可能チェック
            if (checkResultMake())
            {
                // 6001013 = 表示しました。
                setMessage("6001013");
            }

            return result;
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * 選択条件にて処理可能か判定します。
     * 
     * @param p ScheduleParams
     * @return boolean 処理可能時:true 不可能時:falseを通知します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean check(ScheduleParams p)
            throws CommonException
    {
        // 実績情報作成可能チェック
        if (!checkResultMake())
        {
            return false;
        }

        // PCT出庫予定情報及びPCT出庫実績報告情報より、取得する。
        PCTRetPlanHandler pPlanHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey pPlanSearchKey = new PCTRetPlanSearchKey();

        // 検索条件
        // 初期化
        pPlanSearchKey.clear();
        // 状態フラグが削除以外
        pPlanSearchKey.setStatusFlag(PCTRetPlan.STATUS_FLAG_DELETE, "!=");
        // バッチSeqNoが一致
        pPlanSearchKey.setBatchSeqNo(p.getString(BATCH_SEQ_NO));

        // 取得項目
        // 予定情報の状態フラグ：最小値
        pPlanSearchKey.setStatusFlagCollect("MIN");
        // 予定情報の報告区分：最小値
        pPlanSearchKey.setReportFlagCollect("MIN");

        // 集約条件
        // バッチNo.
        pPlanSearchKey.setBatchSeqNoGroup();
        PCTRetPlan entityPlan = (PCTRetPlan)pPlanHandler.findPrimary(pPlanSearchKey);

        if (entityPlan == null)
        {
            // 6024063=No.{0} の予定情報が更新されています。
            setMessage(WmsMessageFormatter.format(6024063, p.getRowIndex()));
            setErrorRowIndex(p.getRowIndex());
            return false;
        }

        // 状態フラグが完了以外の場合は報告不可
        if (!PCTRetPlan.STATUS_FLAG_COMPLETION.equals(entityPlan.getStatusFlag()))
        {
            // 6024064=No.{0} のバッチSeqは未完了状態のため、報告できません。
            setMessage(WmsMessageFormatter.format(6024064, p.getRowIndex()));
            setErrorRowIndex(p.getRowIndex());
            return false;
        }

        if (!PCTRetPlan.REPORT_FLAG_NOT_REPORT.equals(entityPlan.getReportFlag()))
        {
            // 6024065=No.{0} のバッチSeqは報告対象情報が存在しません。
            setMessage(WmsMessageFormatter.format(6024065, p.getRowIndex()));
            setErrorRowIndex(p.getRowIndex());
            return false;
        }

        return true;
    }

    /**
     * PCT出庫実績データ報告処理
     * 
     * @param ps ScheduleParams[]
     * @throws CommonException Message
     * @return boolean
     */
    // @Override
    public List<Params> startSCHgetParams(ScheduleParams... ps)
            throws CommonException
    {
        // 報告データ作成中フラグが自クラスで更新されているかを判定する為のフラグ
        boolean updateReportCreatorFlag = false;

        // 報告処理成否フラグ
        boolean resultFlag = false;

        // エラーフラグ
        boolean errorFlag = false;

        // 処理条件チェック・報告フラグ更新処理を行う
        try
        {
            // 処理開始チェック
            // AbstractSCHに定義されているメソッドcanStart()メソッドを呼び出す。
            // trueが戻った場合は処理を続行する。falseが戻された場合は処理を中断し、nullを返して終了する。
            if (!canStart())
            {
                return null;
            }
            // 報告データ作成中チェック
            // AbstractSCHに定義されているメソッドisReportData()メソッドを呼び出す。
            // trueが戻った場合は処理を続行する。falseが戻された場合は処理を中断し、nullを返して終了する。
            if (isReportData())
            {
                return null;
            }
            // 報告データ作成中フラグ更新
            // AbstracrSCHに定義されているメソッドdoReportStart()メソッドを呼び出す。
            // Trueが戻った場合は処理を続行する。falseが戻された場合は処理を中断して、nullを返して終了する。
            if (!doReportStart())
            {
                return null;
            }
            doCommit(this.getClass());
            updateReportCreatorFlag = true;

            // 実績報告データの作成
            PCTRetrievalInParameter[] setParam = new PCTRetrievalInParameter[ps.length];
            for (int i = 0; i < ps.length; i++)
            {
                setParam[i] = new PCTRetrievalInParameter();
                setParam[i].setBatchSeqNo(ps[i].getString(BATCH_SEQ_NO));
            }

            ReportDataCreator reportCreator = null;
            // PCT出庫データ
            // PCT実績データ報告クリエータークラスのよりPCT実績データ報告作成クラス生成処理(getReportClass)を呼び出す
            PCTRetReportDataCreator rdc =
                    new PCTRetReportDataCreator(getConnection(),
                            PCTSystemInParameter.DATA_TYPE_PCTRETRIEVAL_RESULT_FILE,
                            (PCTRetrievalInParameter[])setParam, this.getClass());
            reportCreator = rdc.getReportClass();

            // 統計情報の取得を行います。
            reportCreator.statics();
            try
            {
                // 実績情報作成可能チェック
                if (!checkResultMake())
                {
                    return null;
                }

                // 実績報告データを作成します。
                if (reportCreator.report())
                {
                    // パッケージ単位でコミット
                    doCommit(this.getClass());
                    if (reportCreator.isReport())
                    {
                        // 実績ファイルの送信
                        if (!reportCreator.sendReportFile())
                        {
                            // 6006020=ファイルの入出力エラーが発生しました。{0}
                            RmiMsgLogClient.write("6006020", this.getClass().getName());
                            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
                            setMessage(WmsMessageFormatter.format(6007031));
                            return null;
                        }
                        resultFlag = true;
                    }
                    // メッセージをセット
                    setMessage(reportCreator.getMessage());
                }
                else
                {
                    // パッケージ単位でロールバック
                    doRollBack(this.getClass());
                    setMessage(reportCreator.getMessage());
                    errorFlag = true;
                }
            }
            catch (IOException e)
            {
                errorFlag = true;
            }

            ReportDataBatchSCHParams inparam = new ReportDataBatchSCHParams();
            List<Params> systemOutParam = this.query(inparam);
            if (!errorFlag)
            {
                if (resultFlag)
                {
                    // 6001009=データの書き込みは正常に終了しました。
                    setMessage(WmsMessageFormatter.format(6001009));
                }
                else
                {
                    // 対象データはありませんでした。
                    setMessage(WmsMessageFormatter.format(6003011));
                }
            }
            else
            {
                // 6027009=予期しないエラーが発生しました。ログを参照してください。
                setMessage(WmsMessageFormatter.format(6027009));
            }

            return systemOutParam;
        }
        finally
        {
            if (errorFlag)
            {
                doRollBack(this.getClass());
            }
            // 報告データ作成中フラグが自クラスで更新されたものであるならば、
            // 報告データ作成中フラグを、0：停止中にする
            if (updateReportCreatorFlag)
            {
                if (!doReportEnd())
                {
                    // 6023013=データ報告中のため、処理できません。
                    setMessage("6023013");
                    return null;
                }
                doCommit(this.getClass());
            }
        }
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
     * 表示データ取得用のSQLを取得します。<BR>
     * 
     * @return 表示データ取得用のSQL
     */
    protected String getQuerySql()
    {
        StringBuffer sql = new StringBuffer();

        sql.append("SELECT ");
        sql.append("DNPCTRETPLAN.BATCH_SEQ_NO AS BATCH_SEQ_NO, ");
        sql.append("DNPCTRETPLAN.BATCH_NO AS BATCH_NO, ");
        sql.append("MAX(DNPCTRETPLAN.LAST_UPDATE_DATE) AS LAST_UPDATE_DATE ");
        sql.append("FROM ");
        sql.append("DNPCTRETPLAN ");
        sql.append("WHERE ");
        sql.append("DNPCTRETPLAN.STATUS_FLAG != '").append(PCTRetPlan.STATUS_FLAG_DELETE).append("' ");
        sql.append("GROUP BY ");
        sql.append("DNPCTRETPLAN.BATCH_SEQ_NO, ");
        sql.append("DNPCTRETPLAN.BATCH_NO ");
        sql.append("HAVING MIN(DNPCTRETPLAN.REPORT_FLAG) ='").append(PCTRetPlan.REPORT_FLAG_NOT_REPORT).append("' ");
        sql.append("AND MIN(DNPCTRETPLAN.STATUS_FLAG) ='").append(PCTRetPlan.STATUS_FLAG_COMPLETION).append("' ");
        sql.append("ORDER BY ");
        sql.append("MAX(DNPCTRETPLAN.LAST_UPDATE_DATE) ASC, ");
        sql.append("DNPCTRETPLAN.BATCH_NO ASC, ");
        sql.append("DNPCTRETPLAN.BATCH_SEQ_NO ASC ");

        return String.valueOf(sql);
    }

    /**
     * 実績データ作成可能チェック
     * 
     * @throws CommonException Message
     * @return boolean
     */
    protected boolean checkResultMake()
            throws CommonException
    {
        try
        {
            // PCT出庫データ
            // PCT実績データ報告クリエータークラスのよりPCT実績データ報告作成クラス生成処理(getReportClass)を呼び出す
            PCTRetReportDataCreator rdc =
                    new PCTRetReportDataCreator(getConnection(),
                            PCTSystemInParameter.DATA_TYPE_PCTRETRIEVAL_RESULT_FILE, this.getClass());

            // 実績送信ファイル有無をチェック
            if (!rdc.isSendFiles())
            {
                // 6024066=作成済みの報告情報が存在するため、作成できません。
                setMessage(WmsMessageFormatter.format(6024066));
                return false;
            }

            // 一時ファイルのファイル数取得
            int restCnt = rdc.existTempFilesCount();
            // 一時ファイルチェック処理結果
            for (int i = 0; i < restCnt; i++)
            {
                // 一時ファイル存在チェック
                if (rdc.isExistTempFile())
                {
                    // 実績ファイルの作成
                    if (!rdc.sendReportFile())
                    {
                        // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
                        RmiMsgLogClient.write("6006020", this.getClass().getName());
                        // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
                        setMessage(WmsMessageFormatter.format(6007031));
                        return false;
                    }
                    // 一時ファイルから実績ファイルを作成したので、報告データ作成済みメッセージを表示する。
                    // 6024066=作成済みの報告情報が存在するため、作成できません。
                    setMessage(WmsMessageFormatter.format(6024066));
                    return false;
                }
            }
        }
        catch (IOException e)
        {
            return false;
        }

        return true;
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
