// $Id: WorkingTimeSimuSCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.analysis.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Date;
import java.math.BigDecimal;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;

import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.analysis.operator.AnalysisIniFileHandler;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.StoragePlan;

/**
 * 作業時間予測（予測処理）のスケジュール処理を行います。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class WorkingTimeSimuSCH
        // DFKLOOK 継承先を変更
        extends AbstractAnalysisSCH
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
    public WorkingTimeSimuSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    // DFKLOOK ここから追加
    /**
     * 分析用設定ファイルから作業者数と作業開始時刻を取得して返します。
     * @param p ScheduleParams
     * @return  分析用設定ファイルの作業者数と作業開始時刻
     * @throws CommonException  予期しない例外が発生した場合に通知します。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // 分析用設定ファイルハンドラー
        AnalysisIniFileHandler iniHandler = null;

        // 分析設定ファイルハンドラが未設定の場合は、新規に生成します。
        iniHandler = new AnalysisIniFileHandler();

        // 分析設定ファイルの読み込みを行います。
        if (!iniHandler.load())
        {
            // 6007031 = ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage("6007031");
            return null;
        }

        WarenaviSystemController wmsCtl = new WarenaviSystemController(getConnection(), getClass());

        Params outParams = new Params();

        // 入庫作業
        if (wmsCtl.hasStoragePack())
        {
            outParams.set(WorkingTimeSimuSCHParams.STORAGE_WORKER_NUM, iniHandler.getStorageWorkerNum());
            outParams.set(WorkingTimeSimuSCHParams.STORAGE_WORK_START_TIME, iniHandler.getStorageWkStartTime());
        }

        // 出庫作業
        if (wmsCtl.hasRetrievalPack())
        {
            outParams.set(WorkingTimeSimuSCHParams.RETRIEVAL_WORKER_NUM, iniHandler.getRetrievalWorkerNum());
            outParams.set(WorkingTimeSimuSCHParams.RETRIEVAL_WORK_START_TIME, iniHandler.getRetrievalWkStartTime());
        }

        return outParams;
    }
    // DFKLOOK ここまで追加

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
        // ここから追加
        WorkingTimeSimuSCHParams inParam = (WorkingTimeSimuSCHParams)p;
        AnalysisOutParameter anaParams = new AnalysisOutParameter();

        // Businessからのパラメータを取得する
        Date planDate = inParam.getDate(WorkingTimeSimuSCHParams.WORK_PLAN_DATE);
        anaParams.setPlanDate(WmsFormatter.toParamDate(planDate));
        anaParams.setBeforePlanDate(inParam.getBoolean(WorkingTimeSimuSCHParams.BEFORE_PLAN_DATE));
        anaParams.setAfterPlanDate(inParam.getBoolean(WorkingTimeSimuSCHParams.AFTER_PLAN_DATE));

        WarenaviSystemController controller = new WarenaviSystemController(getConnection(), getClass());

        // 入庫作業
        long itemCount = 0;
        if (controller.hasStoragePack())
        {
            long[] qty =
                    getStorageQty(anaParams.getPlanDate(), anaParams.isCheckedBeforePlanDate(),
                            anaParams.isCheckedAfterPlanDate());
            anaParams.setStorageItemQty(qty[0]);
            anaParams.setStoragePieceQty(qty[1]);
            itemCount += qty[0];
        }

        // 出庫作業
        if (controller.hasRetrievalPack())
        {
            long[] qty =
                    getRetrievalQty(anaParams.getPlanDate(), anaParams.isCheckedBeforePlanDate(),
                            anaParams.isCheckedAfterPlanDate());
            anaParams.setRetrievalItemQty(qty[0]);
            anaParams.setRetrievalPieceQty(qty[1]);
            itemCount += qty[0];
        }

        if (0 == itemCount)
        {
            // 6003011=対象データはありませんでした。
            setMessage("6003011");
        }
        else
        {
            // 6001013=表示しました。
            setMessage("6001013");
        }

        List<Params> lParams = new ArrayList<Params>();
        lParams.add((anaParams));
        return lParams;
        // ここまで追加
    }

    // DFKLOOK ここから追加
    /**
     * 画面から入力された内容をパラメータとして受け取り、ファイルに書き込みます。
     *
     * @param startParams ScheduleParamsクラスのインスタンスの配列。
     * @return スケジュール結果
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    public boolean startSCH(ScheduleParams startParams)
            throws CommonException
    {
        WorkingTimeSimuSCHParams param = (WorkingTimeSimuSCHParams)startParams;

        // 分析設定ファイルハンドラーを生成します。
        AnalysisIniFileHandler iniP = new AnalysisIniFileHandler();
        // ファイルの読み込みを行います。
        if (!iniP.load())
        {
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage("6007031");
            return false;
        }

        WarenaviSystemController controller = new WarenaviSystemController(getConnection(), getClass());

        if (controller.hasReceivingPack())
        {
            iniP.setInstockWorkerNum(param.getString(WorkingTimeSimuSCHParams.INSTOCK_WORKER_NUM));
            iniP.setInstockWkStartTime(param.getString(WorkingTimeSimuSCHParams.INSTOCK_WK_START_TIME));
        }

        if (controller.hasStoragePack())
        {
            iniP.setStorageWorkerNum(param.getString(WorkingTimeSimuSCHParams.STORAGE_WORKER_NUM));
            iniP.setStorageWkStartTime(param.getString(WorkingTimeSimuSCHParams.STORAGE_WORK_START_TIME));
        }

        if (controller.hasRetrievalPack())
        {
            iniP.setRetrievalWorkerNum(param.getString(WorkingTimeSimuSCHParams.RETRIEVAL_WORKER_NUM));
            iniP.setRetrievalWkStartTime(param.getString(WorkingTimeSimuSCHParams.RETRIEVAL_WORK_START_TIME));
        }

        if (controller.hasSortingPack())
        {
            iniP.setSortingWorkerNum(param.getString(WorkingTimeSimuSCHParams.SORTING_WORKER_NUM));
            iniP.setSortingWkStartTime(param.getString(WorkingTimeSimuSCHParams.SORTING_WK_START_TIME));
        }

        if (controller.hasShippingPack())
        {
            iniP.setShippingWorkerNum(param.getString(WorkingTimeSimuSCHParams.SHIPPING_WORKER_NUM));
            iniP.setShippingWkStartTime(param.getString(WorkingTimeSimuSCHParams.SHIPPING_WK_START_TIME));
        }

        if (!iniP.saveWorkingTimeSimu())
        {
            String message = WmsMessageFormatter.format(6006020, WmsParam.ANALYSIS_INI_PATH);
            RmiMsgLogClient.write(message, getClass().getName());
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            return false;
        }

        return true;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * 分析設定ファイルに保存された分析パラメータを使用して各作業の所要時間と終了時間を予測します。<BR>
     *
     * @param ps ScheduleParamsクラスのインスタンス。
     * @return 予測結果を格納したパラメータ
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
//    public Parameter[] startSCHgetParams(Parameter[] startParams)
    public List<Params> startSCHgetParams(ScheduleParams... ps)
            throws CommonException
    {
        WorkingTimeSimuSCHParams param = (WorkingTimeSimuSCHParams)ps[0];

        AnalysisOutParameter anaParams = new AnalysisOutParameter();

        anaParams.setInstockItemQty(param.getLong(WorkingTimeSimuSCHParams.INSTOCK_ITEM_QTY));
        anaParams.setInstockPieceQty(param.getLong(WorkingTimeSimuSCHParams.INSTOCK_PIECE_QTY));
        anaParams.setStorageItemQty(param.getLong(WorkingTimeSimuSCHParams.STORAGE_ITEM_QTY_INP));
        anaParams.setStoragePieceQty(param.getLong(WorkingTimeSimuSCHParams.STORAGE_PIECE_QTY_INP));
        anaParams.setRetrievalItemQty(param.getLong(WorkingTimeSimuSCHParams.RETRIEVAL_ITEM_QTY_INP));
        anaParams.setRetrievalPieceQty(param.getLong(WorkingTimeSimuSCHParams.RETRIEVAL_PIECE_QTY_INP));
        anaParams.setSortingItemQty(param.getLong(WorkingTimeSimuSCHParams.SORTING_ITEM_QTY));
        anaParams.setSortingPieceQty(param.getLong(WorkingTimeSimuSCHParams.SORTING_PIECE_QTY));
        anaParams.setShippingItemQty(param.getLong(WorkingTimeSimuSCHParams.SHIPPING_ITEM_QTY));
        anaParams.setShippingPieceQty(param.getLong(WorkingTimeSimuSCHParams.SHIPPING_PIECE_QTY));

        // 分析設定ファイルハンドラーより予測パラメータを取得します。
        AnalysisIniFileHandler iniP = new AnalysisIniFileHandler();
        // ファイルの読み込みを行います。
        if (!iniP.load())
        {
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            return null;
        }

        // 予測時間計算用
        BigDecimal sec = new BigDecimal("60");

        // 入荷作業
        BigDecimal instockWokerNum = new BigDecimal(iniP.getInstockWorkerNum());// scale=1
        BigDecimal instockSecPerItem = new BigDecimal(iniP.getInstockSecPerItem());// scale=2
        BigDecimal instockSecPerPiece = new BigDecimal(iniP.getInstockSecPerPiece());// scale=2
        BigDecimal instockItemQty = new BigDecimal(String.valueOf(anaParams.getInstockItemQty()));// scale=0
        BigDecimal instockPieceQty = new BigDecimal(String.valueOf(anaParams.getInstockPieceQty()));// scale=0

        // 商品数＊商品毎時間＋ピース数＊ピース毎時間　/　作業者＊60（秒）
        // 切り上げで丸め込み
        BigDecimal instockRes =
                ((instockItemQty.multiply(instockSecPerItem)).add(instockPieceQty.multiply(instockSecPerPiece))).divide(
                        instockWokerNum.multiply(sec), 0, BigDecimal.ROUND_UP);

        // 所要時間が24時間(1440分)以上の場合は、メッセージを表示し計算を行わない。
        if (instockRes.longValue() > 1440)
        {
            // 6023511={0}の作業所要時間が24時間を越えるため表示できません。LBL-W1221=入荷作業
            String message = WmsMessageFormatter.format(6023511, DispResources.getText("LBL-W1221"));
            setMessage(message);
            return null;
        }
        else
        {
            anaParams.setInstockWorkingTime(String.valueOf(instockRes));
            anaParams.setInstockEndTime(iniP.getWorkEndTime(iniP.getInstockWkStartTime(),
                    instockRes.longValue() * 60));
        }

        // 入庫作業
        BigDecimal storageWokerNum = new BigDecimal(iniP.getStorageWorkerNum());
        BigDecimal storageSecPerItem = new BigDecimal(iniP.getStorageSecPerItem());
        BigDecimal storageSecPerPiece = new BigDecimal(iniP.getStorageSecPerPiece());
        BigDecimal storageItemQty = new BigDecimal(String.valueOf(anaParams.getStorageItemQty()));
        BigDecimal storagePieceQty = new BigDecimal(String.valueOf(anaParams.getStoragePieceQty()));

        // 商品数＊商品毎時間＋ピース数＊ピース毎時間　/　作業者＊60（秒）
        // 切り上げで丸め込み
        BigDecimal storageRes =
                ((storageItemQty.multiply(storageSecPerItem)).add(storagePieceQty.multiply(storageSecPerPiece))).divide(
                        storageWokerNum.multiply(sec), 0, BigDecimal.ROUND_UP);

        // 所要時間が24時間(1440分)以上の場合は、メッセージを表示し計算を行わない。
        if (storageRes.longValue() > 1440)
        {
            // 6023511={0}の作業所要時間が24時間を越えるため表示できません。LBL-W1222=入庫作業
            String message = WmsMessageFormatter.format(6023511, DispResources.getText("LBL-W1222"));
            setMessage(message);
            return null;
        }
        else
        {
            anaParams.setStorageWorkingTime(String.valueOf(storageRes));
            anaParams.setStorageEndTime(iniP.getWorkEndTime(iniP.getStorageWkStartTime(),
                    storageRes.longValue() * 60));
        }

        // 出庫作業
        BigDecimal retrievalWokerNum = new BigDecimal(iniP.getRetrievalWorkerNum());
        BigDecimal retrievalSecPerItem = new BigDecimal(iniP.getRetrievalSecPerItem());
        BigDecimal retrievalSecPerPiece = new BigDecimal(iniP.getRetrievalSecPerPiece());
        BigDecimal retrievalItemQty = new BigDecimal(String.valueOf(anaParams.getRetrievalItemQty()));
        BigDecimal retrievalPieceQty = new BigDecimal(String.valueOf(anaParams.getRetrievalPieceQty()));

        // 商品数＊商品毎時間＋ピース数＊ピース毎時間　/　作業者＊60（秒）
        // 切り上げで丸め込み
        BigDecimal retrievalRes =
                ((retrievalItemQty.multiply(retrievalSecPerItem)).add(retrievalPieceQty.multiply(retrievalSecPerPiece))).divide(
                        retrievalWokerNum.multiply(sec), 0, BigDecimal.ROUND_UP);

        // 所要時間が24時間(1440分)以上の場合は、メッセージを表示し計算を行わない。
        if (retrievalRes.longValue() > 1440)
        {
            // 6023511={0}の作業所要時間が24時間を越えるため表示できません。LBL-W1223=出庫作業
            String message = WmsMessageFormatter.format(6023511, DispResources.getText("LBL-W1223"));
            setMessage(message);
            return null;
        }
        else
        {
            anaParams.setRetrievalWorkingTime(String.valueOf(retrievalRes));
            anaParams.setRetrievalEndTime(iniP.getWorkEndTime(iniP.getRetrievalWkStartTime(),
                    retrievalRes.longValue() * 60));
        }

        // 仕分作業
        BigDecimal sortingWokerNum = new BigDecimal(iniP.getSortingWorkerNum());
        BigDecimal sortingSecPerItem = new BigDecimal(iniP.getSortingSecPerItem());
        BigDecimal sortingSecPerPiece = new BigDecimal(iniP.getSortingSecPerPiece());
        BigDecimal sortingItemQty = new BigDecimal(String.valueOf(anaParams.getSortingItemQty()));
        BigDecimal sortingPieceQty = new BigDecimal(String.valueOf(anaParams.getSortingPieceQty()));

        // 商品数＊商品毎時間＋ピース数＊ピース毎時間　/　作業者＊60（秒）
        // 切り上げで丸め込み
        BigDecimal sortingRes =
                ((sortingItemQty.multiply(sortingSecPerItem)).add(sortingPieceQty.multiply(sortingSecPerPiece))).divide(
                        sortingWokerNum.multiply(sec), 0, BigDecimal.ROUND_UP);

        // 所要時間が24時間(1440分)以上の場合は、メッセージを表示し計算を行わない。
        if (sortingRes.longValue() > 1440)
        {
            // 6023511={0}の作業所要時間が24時間を越えるため表示できません。LBL-W1224=仕分作業
            String message = WmsMessageFormatter.format(6023511, DispResources.getText("LBL-W1224"));
            setMessage(message);
            return null;
        }
        else
        {
            anaParams.setSortingWorkingTime(String.valueOf(sortingRes));
            anaParams.setSortingEndTime(iniP.getWorkEndTime(iniP.getSortingWkStartTime(),
                    sortingRes.longValue() * 60));
        }

        // 出荷作業
        BigDecimal shippingWokerNum = new BigDecimal(iniP.getShippingWorkerNum());
        BigDecimal shippingSecPerItem = new BigDecimal(iniP.getShippingSecPerItem());
        BigDecimal shippingSecPerPiece = new BigDecimal(iniP.getShippingSecPerPiece());
        BigDecimal shippingItemQty = new BigDecimal(String.valueOf(anaParams.getShippingItemQty()));
        BigDecimal shippingPieceQty = new BigDecimal(String.valueOf(anaParams.getShippingPieceQty()));

        // 商品数＊商品毎時間＋ピース数＊ピース毎時間　/　作業者＊60（秒）
        // 切り上げで丸め込み
        BigDecimal shippingRes =
                ((shippingItemQty.multiply(shippingSecPerItem)).add(shippingPieceQty.multiply(shippingSecPerPiece))).divide(
                        shippingWokerNum.multiply(sec), 0, BigDecimal.ROUND_UP);

        // 所要時間が24時間(1440分)以上の場合は、メッセージを表示し計算を行わない。
        if (shippingRes.longValue() > 1440)
        {
            // 6023511={0}の作業所要時間が24時間を越えるため表示できません。LBL-W1225=出荷作業
            String message = WmsMessageFormatter.format(6023511, DispResources.getText("LBL-W1225"));
            setMessage(message);
            return null;
        }
        else
        {
            anaParams.setShippingWorkingTime(String.valueOf(shippingRes));
            anaParams.setShippingEndTime(iniP.getWorkEndTime(iniP.getShippingWkStartTime(),
                    shippingRes.longValue() * 60));
        }

        // 6001021=作業時間予測を実行しました。
        setMessage(WmsMessageFormatter.format(6001021));

        List<Params> lParams = new ArrayList<Params>();
        lParams.add((Params)anaParams);
        return lParams;
    }
    // DFKLOOK ここまで追加

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
     * 入庫予定情報より指定した予定日の未開始データから商品数、総ピース数を取得します。
     *
     * @param planDate 予定日
     * @param containBefore 検索条件に作業日以前を含むか否か
     * @param containAfter 検索条件に作業日以降を含むか否か
     * @return [0]：商品数 [1]：総ピース数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private long[] getStorageQty(String planDate, boolean containBefore, boolean containAfter)
            throws ReadWriteException
    {
        long[] resultArr = {
            0,
            0
        };

        StoragePlanHandler handler = new StoragePlanHandler(getConnection());
        StoragePlanSearchKey searchKey = new StoragePlanSearchKey();

        // 検索条件をセットします。
        // 状態フラグ(未開始のみ)
        searchKey.setStatusFlag(StoragePlan.STATUS_FLAG_UNSTART, "=");

        // 予定日
        if (!StringUtil.isBlank(planDate))
        {
            if (containBefore && containAfter)
            {
                // 全件対象となります
            }
            else if (containBefore)
            {
                searchKey.setPlanDay(planDate, "<=");
            }
            else if (containAfter)
            {
                searchKey.setPlanDay(planDate, ">=");
            }
            else
            {
                searchKey.setPlanDay(planDate);
            }
        }

        // 商品数を取得します。
        resultArr[0] = handler.count(searchKey);

        // 作業数（作業予定数の総和）を取得します。
        searchKey.setPlanQtyCollect("SUM");
        StoragePlan[] resultEntity = (StoragePlan[])handler.find(searchKey);
        if (resultEntity.length > 0)
        {
            resultArr[1] = resultEntity[0].getPlanQty();
        }
        return resultArr;
    }

    /** 
     * 出庫予定情報より指定した予定日の未開始データから商品数、総ピース数を取得します。
     *
     * @param planDate 予定日
     * @param containBefore 検索条件に作業日以前を含むか否か
     * @param containAfter 検索条件に作業日以降を含むか否か
     * @return [0]：商品数 [1]：総ピース数
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    private long[] getRetrievalQty(String planDate, boolean containBefore, boolean containAfter)
            throws ReadWriteException
    {
        long[] resultArr = {
            0,
            0
        };

        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey searchKey = new RetrievalPlanSearchKey();

        // 検索条件をセットします。
        // 状態フラグ(未開始のみ)
        searchKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART, "=");

        // 予定日
        if (!StringUtil.isBlank(planDate))
        {
            if (containBefore && containAfter)
            {
                // 全件対象となります
            }
            else if (containBefore)
            {
                searchKey.setPlanDay(planDate, "<=");
            }
            else if (containAfter)
            {
                searchKey.setPlanDay(planDate, ">=");
            }
            else
            {
                searchKey.setPlanDay(planDate);
            }
        }

        // 商品数を取得します。
        resultArr[0] = handler.count(searchKey);

        // 作業数（作業予定数の総和）を取得します。
        searchKey.setPlanQtyCollect("SUM");
        RetrievalPlan[] resultEntity = (RetrievalPlan[])handler.find(searchKey);
        if (resultEntity.length > 0)
        {
            resultArr[1] = resultEntity[0].getPlanQty();
        }
        return resultArr;
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
