// $Id: RetrievalOrderAllocClearSCH.java 4995 2009-09-08 09:57:54Z shibamoto $
package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.retrieval.allocate.ReleaseAllocateOperator;
import static jp.co.daifuku.wms.retrieval.schedule.RetrievalOrderAllocClearSCHParams.*;

/**
 * 出庫キャンセルのスケジュール処理を行います。
 * 
 * @version $Revision: 4995 $, $Date: 2009-09-08 18:57:54 +0900 (火, 08 9 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class RetrievalOrderAllocClearSCH
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
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public RetrievalOrderAllocClearSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * <CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、出庫キャンセル処理を行います。<BR>
     * 引当解除に成功またはスキップされた場合はtrue、エラーなどで処理が出来なかった場合はfalseを返します。<BR>
     * @param ps 設定内容が含まれたパラメータクラスの配列
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        RetrievalOrderAllocClearSCHParams startParam = (RetrievalOrderAllocClearSCHParams)ps[0];

        RetrievalInParameter param = new RetrievalInParameter(getWmsUserInfo());
        param.setRetrievalPlanDay(WmsFormatter.toParamDate(startParam.getDate(RetrievalOrderAllocClearSCHParams.PLAN_DATE)));
        param.setBatchNo(startParam.getString(RetrievalOrderAllocClearSCHParams.BATCH_NO));
        String[] loc =
            WmsFormatter.getFromTo(startParam.getString(ORDER_NO_FROM),
                    startParam.getString(ORDER_NO_TO));
        
        param.setOrderNo(loc[0]);
        param.setToOrderNo(loc[1]);
        param.setConsignorCode(startParam.getString(RetrievalOrderAllocClearSCHParams.CONSIGNOR_CODE));

        // 日次更新をチェック
        if (isDailyUpdate())
        {
            return false;
        }

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        // AS/RSチェック
        if (!checkAsrs(param))
        {
            return false;
        }

        RetrievalPlanFinder planFinder = null;
        try
        {
            // 該当する予定、作業情報をロック
            if (!lockWorkPlanData(param))
            {
                // falseが返ってきた場合は対象データなし
                // 6003011=対象データはありませんでした。
                setMessage("6003011");
                return false;
            }

            // 予定情報のデータを取得する
            planFinder = new RetrievalPlanFinder(getConnection());
            RetrievalPlanSearchKey planKey = new RetrievalPlanSearchKey();
            WorkInfoSearchKey workKey = createWorkInfoKey(param);

            workKey.setPlanUkeyCollect("DISTINCT");
            planKey.setKey(RetrievalPlan.PLAN_UKEY, workKey);
            planKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION, "!=");
            planKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");

            // 予定情報の検索
            planFinder.open(true);
            int allDataCnt = planFinder.search(planKey);

            WorkInfoHandler workHandler = new WorkInfoHandler(getConnection());
            RetrievalPlanHandler planHandler = new RetrievalPlanHandler(getConnection());
            RetrievalPlanAlterKey planAltKey = new RetrievalPlanAlterKey();

            RetrievalPlan[] retrievalPlan = null;
            WorkInfo[] workInfo = null;

            ReleaseAllocateOperator releaseOpe = new ReleaseAllocateOperator(getConnection(), getClass());

            // 引当解除を行ったかどうかのフラグ
            boolean allocClearFlag = false;
            // 更新データ件数を保持する変数
            int updateDataCnt = 0;

            // 取得した予定情報がなくなるまでループする
            while (planFinder.hasNext())
            {
                // 結果から100件取り出す
                retrievalPlan = (RetrievalPlan[])planFinder.getEntities(100);

                for (int i = 0; i < retrievalPlan.length; i++)
                {
                    // 引当解除フラグの初期化
                    allocClearFlag = false;

                    // 紐付く作業情報を取得する
                    workKey.clear();

                    workKey.setPlanUkey(retrievalPlan[i].getPlanUkey());
                    workKey.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
                    workInfo = (WorkInfo[])workHandler.find(workKey);

                    for (int j = 0; j < workInfo.length; j++)
                    {
                        if (releaseOpe.allocateClear(workInfo[j].getStockId(), workInfo[j].getPlanQty(),
                                workInfo[j].getSystemConnKey()))
                        {
                            // 更新に成功した場合、作業情報を削除
                            releaseOpe.allocateWorkDelete(workInfo[j].getJobNo(), workInfo[j].getSystemConnKey());
                            // 引当解除フラグを設定
                            allocClearFlag = true;
                        }
                    }

                    // 引当解除データがあった場合
                    if (allocClearFlag)
                    {
                        // 予定情報を更新
                        planAltKey.clear();

                        planAltKey.setPlanUkey(retrievalPlan[i].getPlanUkey());
                        if (releaseOpe.checkWorkInfo(retrievalPlan[i].getPlanUkey()))
                        {
                            // 紐付く作業情報が存在する場合は、作業中にする
                            planAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
                        }
                        else
                        {
                            // 紐付く作業情報が存在しない場合は、未作業にする
                            planAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
                        }
                        planAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_NOT_SCHEDULE);
                        planAltKey.updateLastUpdatePname(getClass().getSimpleName());
                        planHandler.modify(planAltKey);

                        // 更新データを加算
                        updateDataCnt++;
                    }
                }
            }

            // メッセージのセット
            if (updateDataCnt == allDataCnt)
            {
                // 6001006=設定しました。
                setMessage("6001006");
            }
            else if (updateDataCnt == 0)
            {
                // 6021013=一部のデータをスキップした結果、対象データはありませんでした。
                setMessage("6021013");
            }
            else
            {
                // 6021020=設定しました。（一部のデータがスキップされました。）
                setMessage("6021020");
            }

            return true;

        }
        catch (LockTimeOutException e)
        {
            // 6027008=このデータは他の端末で更新中のため、処理できません。
            setMessage("6027008");
            return false;
        }
        finally
        {
            closeFinder(planFinder);
        }

    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * AS/RS系チェックを行います。<BR>
     * AS/RSパッケージ導入チェック、対象作業データにAS/RSデータが存在した場合のオフラインチェックを行います。<BR>
     * @param inputParam 入力チェックを行う内容が含まれたパラメータクラス
     * @return パラメータチェックに成功した場合はtrue、失敗した場合はfalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkAsrs(RetrievalInParameter inputParam)
            throws CommonException
    {
        // エリア情報検索
        AreaHandler areaHandler = new AreaHandler(getConnection());
        AreaSearchKey areaKey = new AreaSearchKey();

        // エリア情報にAS/RSタイプのエリアがない場合、非導入とする。
        areaKey.setAreaTypeCollect("DISTINCT");
        areaKey.setAreaType(Area.AREA_TYPE_ASRS);
        // エリア情報件数取得
        if (areaHandler.count(areaKey) == 0)
        {
            // AS/RSエリアが存在しない場合、trueを返す。
            return true;
        }

        // 作業情報検索
        WorkInfoHandler workHandler = new WorkInfoHandler(getConnection());
        // パラメータの検索キーを作成する
        WorkInfoSearchKey workKey = createWorkInfoKey(inputParam);
        // システム接続キー(null以外)
        workKey.setSystemConnKey((String)null, "!=");

        // 作業情報件数取得
        if (workHandler.count(workKey) == 0)
        {
            // AS/RS作業データがない場合、trueを返す。
            return true;
        }

        return true;
    }

    /**
     * 作業情報の検索キーを生成します。<BR>
     * @param inputParam   入力エリアの内容が含まれたパラメータクラス
     * @return 入力エリアの入力値を含んだ作業情報の検索キー <BR>
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected WorkInfoSearchKey createWorkInfoKey(RetrievalInParameter inputParam)
            throws CommonException
    {
        WorkInfoSearchKey sKey = new WorkInfoSearchKey();

        // 荷主コード
        if (!StringUtil.isBlank(inputParam.getConsignorCode()))
        {
            sKey.setConsignorCode(inputParam.getConsignorCode());
        }
        // 出庫予定日
        if (!StringUtil.isBlank(inputParam.getRetrievalPlanDay()))
        {
            sKey.setPlanDay(inputParam.getRetrievalPlanDay());
        }
        // バッチNo.
        if (!StringUtil.isBlank(inputParam.getBatchNo()))
        {
            sKey.setBatchNo(inputParam.getBatchNo());
        }
        // オーダーNo.(From)
        if (!StringUtil.isBlank(inputParam.getOrderNo()))
        {
            sKey.setOrderNo(inputParam.getOrderNo(), ">=");

        }
        // オーダーNo.(To)
        if (!StringUtil.isBlank(inputParam.getToOrderNo()))
        {
            sKey.setOrderNo(inputParam.getToOrderNo(), "<=");
        }

        // 作業状態(未作業)
        sKey.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);

        // 出庫作業
        sKey.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);

        return sKey;
    }

    /**
     * 作業情報、出庫予定情報の全情報に対してロック処理を行います。<BR>
     * ロックに失敗した場合は、LockTimeOutExceptionがスローされます。<BR>
     * @param inputParam   入力エリアの内容が含まれたパラメータクラス <BR>
     * @return ロック処理に成功した場合はtrue、ロック対象がなかった場合はfalseを返します。 <BR>
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean lockWorkPlanData(RetrievalInParameter inputParam)
            throws CommonException
    {
        WorkInfoFinder workFinder = null;
        try
        {
            workFinder = new WorkInfoFinder(getConnection());
            // パラメータの検索キーを作成する
            WorkInfoSearchKey workKey = createWorkInfoKey(inputParam);

            // 結合
            workKey.setJoin(WorkInfo.PLAN_UKEY, RetrievalPlan.PLAN_UKEY);

            // オープン
            workFinder.open(true);

            if (workFinder.searchForUpdate(workKey, WorkInfoHandler.WAIT_SEC_DEFAULT) == 0)
            {
                // 対象データがない場合、false
                return false;
            }

            return true;

        }
        finally
        {
            closeFinder(workFinder);
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
