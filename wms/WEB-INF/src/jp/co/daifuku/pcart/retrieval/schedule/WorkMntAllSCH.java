// $Id: WorkMntAllSCH.java 4812 2009-08-10 11:05:22Z kumano $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.pcart.retrieval.schedule.WorkMntAllSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.pcart.retrieval.operator.PCTRetOperator;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.SearchKey;

/**
 * 作業メンテナンス一括のスケジュール処理を行います。
 *
 * @version $Revision: 4812 $, $Date:: 2009-08-10 20:05:22 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kumano $
 */
public class WorkMntAllSCH
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
    public WorkMntAllSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {

        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }
        // 取り込み中チェック
        if (isLoadData())
        {
            return false;
        }

        // PCTRetrievalInParameterにキャスト
        PCTRetrievalInParameter[] inParams = new PCTRetrievalInParameter[ps.length];

        for (int i = 0; i < ps.length; i++)
        {
            inParams[i] = new PCTRetrievalInParameter();
            // 荷主コード
            inParams[i].setConsignorCode(ps[i].getString(CONSIGNOR_CODE));
            // 予定日
            inParams[i].setPlanDay(ps[i].getString(PLAN_DAY));
            // バッチNo.
            inParams[i].setBatchNo(ps[i].getString(BATCH_NO));
            // バッチSeqNo.
            inParams[i].setBatchSeqNo(ps[i].getString(BATCH_SEQ_NO));
            // エリアNo.
            inParams[i].setPlanAreaNo(ps[i].getString(AREA_NO));
            // 得意先コード
            inParams[i].setRegularCustomerCode(ps[i].getString(REGULAR_CUSTOMER_CODE));
            // 出荷先コード
            inParams[i].setCustomerCode(ps[i].getString(CUSTOMER_CODE));
            // 設定単位キー
            inParams[i].setSettingUnitKey(ps[i].getString(SETTING_UNIT_KEY));
            // 予定一意キー
            inParams[i].setPlanUkey(ps[i].getString(PLAN_UKEY));
            // 処理フラグ
            inParams[i].setProcessingDivision(ps[i].getString(PROCESS_FLAG));
            // 作業No.
            inParams[i].setJobNo(ps[i].getString(JOB_NO));
            // 予定一意キー
            inParams[i].setPlanUkey(ps[i].getString(PLAN_UKEY));
            // 設定単位キー
            inParams[i].setSettingUnitKey(ps[i].getString(SETTING_UNIT_KEY));
            // 予定オーダーNo.
            inParams[i].setPlanOrderNo(ps[i].getString(PLAN_ORDER_NO));
            // 実績オーダーNo.
            inParams[i].setResultOrderNo(ps[i].getString(RESULT_ORDER_NO));
            // 予定数
            inParams[i].setPlanQty(ps[i].getInt(PLAN_QTY));
            // 実績数
            inParams[i].setResultQty(ps[i].getInt(RESULT_QTY));
            // 状態フラグ
            inParams[i].setStatusFlag1(ps[i].getString(STATUS_FLAG));
            // ユーザ情報
            inParams[i].setUserInfo(getUserInfo());
        }

        // PCT出庫完了処理
        try
        {
            PCTRetOperator retrievalOperator = new PCTRetOperator(this.getConnection(), this.getClass());

            // 削除の場合
            if (PCTRetrievalInParameter.PROCESSING_DIVISION_DELETE.equals(ps[0].getString(PROCESS_FLAG)))
            {
                // 完了処理
                if (retrievalOperator.planLinkDelete(inParams))
                {
                    // 6121003=削除しました。
                    setMessage(WmsMessageFormatter.format(6121003));
                }
                else
                {
                    // 6003014=削除対象データがありませんでした。
                    setMessage(WmsMessageFormatter.format(6003014));
                }
            }
            else
            {
                // 完了処理
                retrievalOperator.completeAll(inParams);

                // 6001014=完了しました。
                setMessage(WmsMessageFormatter.format(6001014));
            }
            return true;
        }
        // ロックが開放されなかった場合に返される例外です。
        catch (LockTimeOutException e)
        {
            // 6023114=他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023114));
            return false;
        }
        catch (NotFoundException e)
        {
            // 例外を受け取った場合、処理を中断してnullを返す
            // MSG = "データベースエラーが発生しました。メッセージログを参照してください。"
            setMessage(WmsMessageFormatter.format(6007002));
            return false;
        }
        // オペレータクラスが生成する例外です。
        // 作業データの処理が正常に完了できない場合に、原因と詳細を保持して 画面・端末に通知するための例外です。
        catch (OperatorException e)
        {
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // 他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023115));
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
    }

    /**
     * 全完了・全欠品完了・削除の処理を行う際、更新対象のデータを検索します。
     *
     * @param p 取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return ScheduleParams 更新対象データをパラメータとして返します。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<ScheduleParams> queryUpdate(ScheduleParams p)
            throws CommonException
    {
        // 日次更新チェック
        if (!canStart())
        {
            return null;
        }
        // データ報告中チェック
        if (isReportData())
        {
            return null;
        }

        PCTRetWorkInfoFinder finder = null;

        // 処理フラグが削除
        if (PCTRetrievalInParameter.PROCESSING_DIVISION_DELETE.equals(p.get(PROCESS_FLAG)))
        {
            return queryDelete(p);
        }

        try
        {
            finder = new PCTRetWorkInfoFinder(getConnection());
            // カーソルオープン
            finder.open(true);

            // 検索処理実行
            int count = finder.search(createSearchKey(p));

            // 取得件数に応じてメッセージを設定
            if (count <= 0)
            {
                // 6403002=修正対象データがありませんでした。
                setMessage(WmsMessageFormatter.format(6403002));
                return null;
            }

            // Pカートで作業中のデータを取得
            // 1件でもあればメッセージを設定
            if (getWorkInfoCount(p) > 0)
            {
                // 6023633=ピッキングカートで作業中のデータが存在するため処理できません。
                setMessage(WmsMessageFormatter.format(6023633));
                return null;
            }

            // 作業情報取得結果
            PCTRetWorkInfo[] entities = (PCTRetWorkInfo[])finder.getEntities(count);

            List<ScheduleParams> result = new ArrayList<ScheduleParams>();

            // 件数文繰返し

            for (PCTRetWorkInfo ent : entities)
            {
                ScheduleParams param = new ScheduleParams();

                // 荷主コード
                param.set(CONSIGNOR_CODE, p.getString(CONSIGNOR_CODE));
                // 予定日
                if (!StringUtil.isBlank(p.getString(PLAN_DAY)))
                {
                    param.set(PLAN_DAY, WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
                }
                // バッチNo.
                if (!StringUtil.isBlank(p.getString(BATCH_NO)))
                {
                    param.set(BATCH_NO, p.getString(BATCH_NO));
                }
                // バッチSeqNo.
                if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
                {
                    param.set(BATCH_SEQ_NO, p.getString(BATCH_SEQ_NO));
                }
                // エリア
                if (!StringUtil.isBlank(p.getString(AREA_NO)))
                {
                    if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
                    {
                        param.set(AREA_NO, p.getString(AREA_NO));
                    }
                }
                // 得意先コード
                if (!StringUtil.isBlank(p.getString(REGULAR_CUSTOMER_CODE)))
                {
                    param.set(REGULAR_CUSTOMER_CODE, p.getString(REGULAR_CUSTOMER_CODE));
                }
                // 出荷先コード
                if (!StringUtil.isBlank(p.getString(CUSTOMER_CODE)))
                {
                    param.set(CUSTOMER_CODE, p.getString(CUSTOMER_CODE));
                }
                // 実績オーダーNo
                if (!StringUtil.isBlank(p.getString(ORDER_NO)))
                {
                    param.set(RESULT_ORDER_NO, p.getString(ORDER_NO));
                }
                // 作業No.
                param.set(JOB_NO, ent.getJobNo());
                // 予定一意キー
                param.set(PLAN_UKEY, ent.getPlanUkey());
                // 設定単位キー
                param.set(SETTING_UNIT_KEY, ent.getSettingUnitKey());
                // 予定数
                param.set(PLAN_QTY, ent.getPlanQty());
                // 実績数
                param.set(RESULT_QTY, ent.getResultQty());
                // 処理フラグが全完了
                if (PCTRetrievalInParameter.PROCESSING_DIVISION_COMPLETION.equals(p.get(PROCESS_FLAG)))
                {
                    param.set(PROCESS_FLAG, PCTRetrievalInParameter.PROCESSING_DIVISION_COMPLETION);
                }
                // 処理フラグが全欠品完了
                else if (PCTRetrievalInParameter.PROCESSING_DIVISION_STOCKOUT.equals(p.get(PROCESS_FLAG)))
                {
                    param.set(PROCESS_FLAG, PCTRetrievalInParameter.PROCESSING_DIVISION_STOCKOUT);
                }
                // 状態フラグ
                param.set(STATUS_FLAG, PCTRetrievalInParameter.STATUS_FLAG_UNWORK);

                result.add(param);
            }
            return result;
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * 作業メンテナンス一括<BR>
     * startParamsで指定されたパラメータの内容をもとに、PCT作業出庫データの取得処理を行う。<BR>
     * 正常完了した場合はtrueを、取得失敗、日次更新中など、処理条件に問題があり、<BR>
     * 処理を中断した場合はfalseを返す。処理結果のメッセージはgetMessage()メソッドで取得する。<BR>
     * 
     * @param p 検索条件
     * @return 検索結果
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    public List<ScheduleParams> queryDelete(ScheduleParams p)
            throws CommonException
    {

        PCTRetPlanFinder finder = null;

        try
        {
            finder = new PCTRetPlanFinder(getConnection());
            // カーソルオープン
            finder.open(true);

            // 検索処理実行
            int count = finder.search(createSearchKeyDelete(p));

            // 取得件数に応じてメッセージを設定
            if (count <= 0)
            {
                // 6403002=修正対象データがありませんでした。
                setMessage(WmsMessageFormatter.format(6403002));
                return null;
            }
            // Pカートで作業中のデータを取得
            // 1件でもあればメッセージを設定
            if (getWorkInfoCount(p) > 0)
            {
                // 6023633=ピッキングカートで作業中のデータが存在するため処理できません。
                setMessage(WmsMessageFormatter.format(6023633));
                return null;
            }

            List<ScheduleParams> result = new ArrayList<ScheduleParams>();

            ScheduleParams param = new ScheduleParams();

            // 荷主コード
            param.set(CONSIGNOR_CODE, p.getString(CONSIGNOR_CODE));
            // 予定日
            if (!StringUtil.isBlank(p.getString(PLAN_DAY)))
            {
                param.set(PLAN_DAY, WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
            }
            // バッチNo.
            if (!StringUtil.isBlank(p.getString(BATCH_NO)))
            {
                param.set(BATCH_NO, p.getString(BATCH_NO));
            }
            // バッチSeqNo.
            if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
            {
                param.set(BATCH_SEQ_NO, p.getString(BATCH_SEQ_NO));
            }
            // エリア
            if (!StringUtil.isBlank(p.getString(AREA_NO)))
            {
                if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
                {
                    param.set(AREA_NO, p.getString(AREA_NO));
                }
            }
            // 得意先コード
            if (!StringUtil.isBlank(p.getString(REGULAR_CUSTOMER_CODE)))
            {
                param.set(REGULAR_CUSTOMER_CODE, p.getString(REGULAR_CUSTOMER_CODE));
            }
            // 出荷先コード
            if (!StringUtil.isBlank(p.getString(CUSTOMER_CODE)))
            {
                param.set(CUSTOMER_CODE, p.getString(CUSTOMER_CODE));
            }
            // オーダーNo.
            if (!StringUtil.isBlank(p.getString(ORDER_NO)))
            {
                param.set(PLAN_ORDER_NO, p.getString(ORDER_NO));
            }
            // 処理フラグが削除
            if (PCTRetrievalInParameter.PROCESSING_DIVISION_DELETE.equals(p.getString(PROCESS_FLAG)))
            {
                param.set(PROCESS_FLAG, PCTRetrievalInParameter.PROCESSING_DIVISION_DELETE);
            }
            result.add(param);

            return result;
        }
        finally
        {
            closeFinder(finder);
        }
    }

    /**
     * 初期表示を行います。<BR>
     * <BR>
     * 概要：PCT出庫予定情報に該当荷主が1件しかない場合は初期表示を行います。<BR>
     * 
     * @param p 検索条件
     * @return 表示パラメータ データがない場合、nullを返す。
     * @throws CommonException
     *             全ての例外を報告します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // PCT出庫予定情報
        PCTRetPlanHandler pHandler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey pkey = new PCTRetPlanSearchKey();

        pkey.setConsignorCodeCollect();
        pkey.setConsignorCodeGroup();
        pkey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_UNWORK);
        pkey.setSchFlag(PCTRetrievalInParameter.SCH_FLAG_COMPLETION);

        if (pHandler.count(pkey) == 1)
        {
            PCTRetPlan ent = (PCTRetPlan)pHandler.findPrimary(pkey);

            Params lineparam = new Params();
            lineparam.set(CONSIGNOR_CODE, ent.getConsignorCode());

            return lineparam;
        }
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

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * PCT出庫作業情報を取得するための検索キークラスのインスタンスを生成します。<BR>
     * @param p 検索条件を含むScheduleParams
     * @return xxxSearchKey
     */
    private SearchKey createSearchKey(ScheduleParams p)
            throws CommonException
    {
        PCTRetWorkInfoSearchKey searchKey = new PCTRetWorkInfoSearchKey();

        // 検索条件のセット
        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 予定日
        if (!StringUtil.isBlank(p.getString(PLAN_DAY)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
        }
        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            searchKey.setBatchNo(p.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            searchKey.setBatchSeqNo(p.getString(BATCH_SEQ_NO));
        }
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                searchKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // 得意先コード
        if (!StringUtil.isBlank(p.getString(REGULAR_CUSTOMER_CODE)))
        {
            searchKey.setRegularCustomerCode(p.getString(REGULAR_CUSTOMER_CODE));
        }
        // 出荷先コード
        if (!StringUtil.isBlank(p.getString(CUSTOMER_CODE)))
        {
            searchKey.setCustomerCode(p.getString(CUSTOMER_CODE));
        }
        // オーダーNo.
        if (!StringUtil.isBlank(p.getString(ORDER_NO)))
        {
            searchKey.setResultOrderNo(p.getString(ORDER_NO));
        }
        // 処理フラグが削除以外の場合
        if (!PCTRetrievalInParameter.PROCESSING_DIVISION_DELETE.equals(p.get(PROCESS_FLAG)))
        {
            // 状態フラグ
            searchKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_UNWORK);
        }

        // 取得項目
        // 作業No
        searchKey.setCollect(PCTRetWorkInfo.JOB_NO);
        // 予定一意キー
        searchKey.setCollect(PCTRetWorkInfo.PLAN_UKEY);
        // 設定単位キー
        searchKey.setCollect(PCTRetWorkInfo.SETTING_UNIT_KEY);
        // 実績オーダーNo
        searchKey.setCollect(PCTRetWorkInfo.RESULT_ORDER_NO);
        // 予定数
        searchKey.setCollect(PCTRetWorkInfo.PLAN_QTY);
        // 実績数
        searchKey.setCollect(PCTRetWorkInfo.RESULT_QTY);
        // 最終更新日時
        searchKey.setCollect(PCTRetWorkInfo.LAST_UPDATE_DATE);

        // 順序項目
        searchKey.setOrder(PCTRetWorkInfo.RESULT_ORDER_NO, true);

        return searchKey;

    }

    /**
     * PCT出庫作業情報を取得するための検索キークラスのインスタンスを生成します。<BR>
     * <BR>
     * @param p 検索条件を含むScheduleParams
     * @return PCTRetPlanSearchKey PCT出庫作業情報を取得するための検索キークラスのインスタンス
     */
    private SearchKey createSearchKeyDelete(ScheduleParams p)
            throws CommonException
    {

        PCTRetPlanSearchKey searchKey = new PCTRetPlanSearchKey();

        // 検索条件のセット
        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 予定日
        if (!StringUtil.isBlank(p.getString(PLAN_DAY)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
        }
        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            searchKey.setBatchNo(p.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            searchKey.setBatchSeqNo(p.getString(BATCH_SEQ_NO));
        }
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                searchKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // 得意先コード
        if (!StringUtil.isBlank(p.getString(REGULAR_CUSTOMER_CODE)))
        {
            searchKey.setRegularCustomerCode(p.getString(REGULAR_CUSTOMER_CODE));
        }
        // 出荷先コード
        if (!StringUtil.isBlank(p.getString(CUSTOMER_CODE)))
        {
            searchKey.setCustomerCode(p.getString(CUSTOMER_CODE));
        }
        // オーダーNo.
        if (!StringUtil.isBlank(p.getString(ORDER_NO)))
        {
            searchKey.setPlanOrderNo(p.getString(ORDER_NO));
        }

        return searchKey;
    }

    /**
     * ピッキングカートで作業中の作業情報件数を取得します。
     * @param p PCT出庫入力パラメータ
     * @return Pカートで作業中の作業情報件数
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    private int getWorkInfoCount(ScheduleParams p)
            throws CommonException
    {
        PCTRetWorkInfoFinder finder = null;
        PCTRetWorkInfoSearchKey searchKey = new PCTRetWorkInfoSearchKey();

        // 検索条件のセット
        // 荷主コード
        // 荷主コード
        searchKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 予定日
        if (!StringUtil.isBlank(p.getString(PLAN_DAY)))
        {
            searchKey.setPlanDay(WmsFormatter.toParamDate(p.getDate(PLAN_DAY)));
        }
        // バッチNo.
        if (!StringUtil.isBlank(p.getString(BATCH_NO)))
        {
            searchKey.setBatchNo(p.getString(BATCH_NO));
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(p.getString(BATCH_SEQ_NO)))
        {
            searchKey.setBatchSeqNo(p.getString(BATCH_SEQ_NO));
        }
        // エリア
        if (!StringUtil.isBlank(p.getString(AREA_NO)))
        {
            if (!WmsParam.ALL_AREA_NO.equals(p.getString(AREA_NO)))
            {
                searchKey.setPlanAreaNo(p.getString(AREA_NO));
            }
        }
        // 得意先コード
        if (!StringUtil.isBlank(p.getString(REGULAR_CUSTOMER_CODE)))
        {
            searchKey.setRegularCustomerCode(p.getString(REGULAR_CUSTOMER_CODE));
        }
        // 出荷先コード
        if (!StringUtil.isBlank(p.getString(CUSTOMER_CODE)))
        {
            searchKey.setCustomerCode(p.getString(CUSTOMER_CODE));
        }
        // オーダーNo.
        if (!StringUtil.isBlank(p.getString(ORDER_NO)))
        {
            searchKey.setResultOrderNo(p.getString(ORDER_NO));
        }

        searchKey.setJoin(PCTRetWorkInfo.SETTING_UNIT_KEY, Rft.SETTING_UNIT_KEY);

        int cnt = 0;
        try
        {
            finder = new PCTRetWorkInfoFinder(getConnection());
            finder.open(true);

            cnt = finder.search(searchKey);
        }
        finally
        {
            finder.close();
        }

        return cnt;
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
