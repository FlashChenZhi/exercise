// $Id: WorkingTimeConfigSCH.java 6586 2009-12-21 09:21:19Z okayama $
package jp.co.daifuku.wms.analysis.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.analysis.operator.AnalysisIniFileHandler;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkerResultSearchKey;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 作業時間予測（条件入力）のスケジュール処理を行います。
 *
 * @version $Revision: 6586 $, $Date: 2009-12-21 18:21:19 +0900 (月, 21 12 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class WorkingTimeConfigSCH
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
    public WorkingTimeConfigSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面初期表示時に必要なデータを取得する操作に対応するメソッドです。<BR>
     * 商品毎平均時間、商品毎平均ピース数、ピース毎平均時間にセットする値を返します。<BR>
     * 該当データが存在しなかった場合はnullを返します。 <BR>
     *
     * このクラスでは内容は実装されていません。<br>
     * サブクラスでオーバーライドされていなければ、ScheduleExceptionがスローされます。
     * @param p  表示データ取得条件を持つクラス
     * @return  検索結果が含まれたParams
     * @throws CommonException  サブクラスで定義されます。
     */
    public Params initFind(ScheduleParams p)
            throws CommonException
    {
        // DFKLOOK ここから追加
        // パラメータの型を変換
        WorkingTimeConfigSCHParams params = (WorkingTimeConfigSCHParams)p;

        AnalysisOutParameter anaParams = new AnalysisOutParameter();

        // 作業種別
        anaParams.setKindOfWork(params.getString(WorkingTimeConfigSCHParams.KIND_WORK));

        // 作業日を読み込み1ヶ月前の日付文字列を作成します。
        Calendar curDate = Calendar.getInstance();
        curDate.setTime(WmsFormatter.toDate(getWorkDate()));
        curDate.add(Calendar.MONTH, -1);
        String fromDate = WmsFormatter.toParamDate(curDate.getTime());

        // 作業者実績より日別に実績を集計します。
        WorkerResultSearchKey wrSKey = new WorkerResultSearchKey();
        WorkerResultHandler wrHndl = new WorkerResultHandler(getConnection());
        wrSKey.setJobType(getJobType(params.getString(WorkingTimeConfigSCHParams.KIND_WORK)));
        wrSKey.setWorkDay(fromDate, ">=");
        wrSKey.setWorkDayCollect();
        wrSKey.setWorkTimeCollect("SUM");
        wrSKey.setWorkQtyCollect("SUM");
        wrSKey.setWorkCntCollect("SUM");
        wrSKey.setWorkDayGroup();
        wrSKey.setWorkDayOrder(true);
        WorkerResult[] wrEntity = (WorkerResult[])wrHndl.find(wrSKey);

        if (wrEntity.length <= 0)
        {
            return null;
        }

        // 検索結果を実績グラフデータ配列へセットする。
        double totalWkTimeAll = 0;
        double totalPiecesAll = 0;
        double itemQtyAll = 0;
        for (int i = 0; i < wrEntity.length; i++)
        {
            double totalWkTime = new Float(wrEntity[i].getWorkTime()).doubleValue();
            double totalPieces = new Float(wrEntity[i].getWorkQty()).doubleValue();
            double itemQty = new Float(wrEntity[i].getWorkCnt()).doubleValue();
            totalWkTimeAll += totalWkTime;
            totalPiecesAll += totalPieces;
            itemQtyAll += itemQty;
        }

        // 平均算出
        if (itemQtyAll > 0)
        {
            anaParams.setAveSecPerItem(totalWkTimeAll / itemQtyAll);
            anaParams.setAvePiecesPerItem(totalPiecesAll / itemQtyAll);
            anaParams.setAveSecPerPiece(totalWkTimeAll / totalPiecesAll);
        }

        // 6001013=表示しました。
        setMessage(WmsMessageFormatter.format(6001013));

        return (Params)anaParams;
        // DFKLOOK ここまで追加
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、ファイルに書き込みます。<BR>
     * 同時に作業者コードとパスワードチェックを行い、失敗した場合はfalseを返します。<BR>
     * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
     *
     * @param startParams 設定内容を持つ<CODE>ScheduleParams</CODE>クラス
     * @return 処理結果
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    public boolean startSCH(ScheduleParams startParams)
            throws CommonException
    {
        // DFKLOOK ここから追加
        WorkingTimeConfigSCHParams params = (WorkingTimeConfigSCHParams)startParams;

        // 分析設定ファイルハンドラーを生成します。
        AnalysisIniFileHandler iniP = new AnalysisIniFileHandler();
        // 前回値の保持のためにファイルの読み込みます。
        if (!iniP.load())
        {
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            return false;
        }

        WarenaviSystemController controller = new WarenaviSystemController(getConnection(), getClass());
        if (controller.hasReceivingPack())
        {
            iniP.setInstockSecPerItem(params.getString(WorkingTimeConfigSCHParams.INSTOCK_SEC_PER_ITEM));
            iniP.setInstockSecPerPiece(params.getString(WorkingTimeConfigSCHParams.INSTOCK_SEC_PER_PIECE));
        }

        if (controller.hasStoragePack())
        {
            iniP.setStorageSecPerItem(params.getString(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_ITEM));
            iniP.setStorageSecPerPiece(params.getString(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_PIECE));
        }

        if (controller.hasRetrievalPack())
        {
            iniP.setRetrievalSecPerItem(params.getString(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_ITEM));
            iniP.setRetrievalSecPerPiece(params.getString(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_PIECE));
        }

        if (controller.hasSortingPack())
        {
            iniP.setSortingSecPerItem(params.getString(WorkingTimeConfigSCHParams.SORTING_SEC_PER_ITEM));
            iniP.setSortingSecPerPiece(params.getString(WorkingTimeConfigSCHParams.SORTING_SEC_PER_PIECE));
        }

        if (controller.hasShippingPack())
        {
            iniP.setShippingSecPerItem(params.getString(WorkingTimeConfigSCHParams.SHIPPING_SEC_PER_ITEM));
            iniP.setShippingSecPerPiece(params.getString(WorkingTimeConfigSCHParams.SHIPPING_SEC_PER_PIECE));
        }

        if (!iniP.saveWorkingTimeSimu())
        {
            String message = WmsMessageFormatter.format(6006020, WmsParam.ANALYSIS_INI_PATH);
            RmiMsgLogClient.write(message, getClass().getName());
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            return false;
        }

        // 6001006=設定しました。
        setMessage(WmsMessageFormatter.format(6001006));

        return true;
        // DFKLOOK ここまで追加
    }

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
        // DFKLOOK ここから追加
        WorkingTimeConfigSCHParams param = (WorkingTimeConfigSCHParams)p;

        // パラメータの型を変換
        AnalysisOutParameter anaParam = new AnalysisOutParameter();
        anaParam.setInstockSecPerItem(param.getString(WorkingTimeConfigSCHParams.INSTOCK_SEC_PER_ITEM));
        anaParam.setInstockSecPerPiece(param.getString(WorkingTimeConfigSCHParams.INSTOCK_SEC_PER_PIECE));
        anaParam.setStorageSecPerItem(param.getString(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_ITEM));
        anaParam.setStorageSecPerPiece(param.getString(WorkingTimeConfigSCHParams.STORAGE_SEC_PER_PIECE));
        anaParam.setRetrievalSecPerItem(param.getString(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_ITEM));
        anaParam.setRetrievalSecPerPiece(param.getString(WorkingTimeConfigSCHParams.RETRIEVAL_SEC_PER_PIECE));
        anaParam.setSortingSecPerItem(param.getString(WorkingTimeConfigSCHParams.SORTING_SEC_PER_ITEM));
        anaParam.setSortingSecPerPiece(param.getString(WorkingTimeConfigSCHParams.SORTING_SEC_PER_PIECE));
        anaParam.setShippingSecPerItem(param.getString(WorkingTimeConfigSCHParams.SHIPPING_SEC_PER_ITEM));
        anaParam.setShippingSecPerPiece(param.getString(WorkingTimeConfigSCHParams.SHIPPING_SEC_PER_PIECE));

        // 作業日を読み込み1ヶ月前の日付文字列を作成します。
        Calendar curDate = Calendar.getInstance();
        curDate.setTime(WmsFormatter.toDate(getWorkDate()));
        curDate.add(Calendar.MONTH, -1);
        String fromDate = WmsFormatter.toParamDate(curDate.getTime());

        // 作業者実績より日別に実績を集計します。
        anaParam.setKindOfWork(param.getString(WorkingTimeConfigSCHParams.KIND_WORK));
        WorkerResultSearchKey wrSKey = new WorkerResultSearchKey();
        WorkerResultHandler wrHndl = new WorkerResultHandler(getConnection());
        wrSKey.setJobType(getJobType(param.getString(WorkingTimeConfigSCHParams.KIND_WORK)));
        wrSKey.setWorkDay(fromDate, ">=");
        wrSKey.setWorkDayCollect();
        wrSKey.setWorkTimeCollect("SUM");
        wrSKey.setWorkQtyCollect("SUM");
        wrSKey.setWorkCntCollect("SUM");
        wrSKey.setWorkDayCollect();
        wrSKey.setWorkDayGroup(); // ←追加
        wrSKey.setWorkDayOrder(true); // ←追加
        WorkerResult[] wrEntity = (WorkerResult[])wrHndl.find(wrSKey);

        if (wrEntity.length <= 0)
        {
            // 6003011=対象データはありませんでした。
            setMessage(WmsMessageFormatter.format(6003011));
            return null;
        }

        // 検索結果を実績グラフデータ配列へセットします。
        double totalWkTimeAll = 0;
        double totalPiecesAll = 0;
        double itemQtyAll = 0;
        for (int i = 0; i < wrEntity.length; i++)
        {
            double totalWkTime = new Float(wrEntity[i].getWorkTime()).doubleValue();
            double totalPieces = new Float(wrEntity[i].getWorkQty()).doubleValue();
            double itemQty = new Float(wrEntity[i].getWorkCnt()).doubleValue();
            // 作業数、ピース数にdouble値を使用すると、計算結果に誤差が生じるため、
            // 実績数からの作業予測を求めるため、ここではintを使用します。
            double simuTime = getSimuWorkingTime(anaParam, wrEntity[i].getWorkCnt(), wrEntity[i].getWorkQty());
            anaParam.addToAbcAnaList(wrEntity[i].getWorkDay(), totalWkTime, itemQty, totalPieces, simuTime);
            totalWkTimeAll += totalWkTime;
            totalPiecesAll += totalPieces;
            itemQtyAll += itemQty;
        }

        // 平均算出
        if (itemQtyAll > 0)
        {
            anaParam.setAveSecPerItem(totalWkTimeAll / itemQtyAll);
            anaParam.setAvePiecesPerItem(totalPiecesAll / itemQtyAll);
            anaParam.setAveSecPerPiece(totalWkTimeAll / totalPiecesAll);
        }

        // 6001013=表示しました。
        setMessage(WmsMessageFormatter.format(6001013));

        List<Params> analysisParams = new ArrayList<Params>();
        analysisParams.add((Params)anaParam);
        return analysisParams;
        // DFKLOOK ここまで追加
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
    // DFKLOOK ここから追加
    /** 
     * 作業種別に応じたJOB_TYPEを返します。<BR>
     * 
     * @param kindOfWork 作業種別<BR>
     * @return JOB_TYPE<BR>
     */
    private String getJobType(String kindOfWork)
    {
        if (kindOfWork.equals(DispResources.getText("LBL-W1221")))
        {
            return WorkerResult.JOB_TYPE_RECEIVING;
        }
        else if (kindOfWork.equals(DispResources.getText("LBL-W1222")))
        {
            return WorkerResult.JOB_TYPE_STORAGE;
        }
        else if (kindOfWork.equals(DispResources.getText("LBL-W1223")))
        {
            return WorkerResult.JOB_TYPE_RETRIEVAL;
        }
        else if (kindOfWork.equals(DispResources.getText("LBL-W1224")))
        {
            return WorkerResult.JOB_TYPE_SORTING;
        }
        else if (kindOfWork.equals(DispResources.getText("LBL-W1225")))
        {
            return WorkerResult.JOB_TYPE_SHIPPING;
        }
        return "";
    }

    /** 
     * 作業種別に応じた作業予測時間を返します。<BR>
     * 小数点つきのまま返します。
     * 
     * @param param AnalysisInParameter
     * @param pItemQty 商品数
     * @param pPieces 総ピース数
     * @return 作業予測時間<BR>
     */
    private double getSimuWorkingTime(AnalysisOutParameter param, int pItemQty, int pPieces)
    {
        String kindOfWork = param.getKindOfWork();

        BigDecimal itemQty = new BigDecimal(String.valueOf(pItemQty));
        BigDecimal pieces = new BigDecimal(String.valueOf(pPieces));

        BigDecimal secPerItem = null;
        BigDecimal secPerPiece = null;

        if (kindOfWork.equals(DispResources.getText("LBL-W1221")))
        {
            secPerItem = new BigDecimal(param.getInstockSecPerItem());
            secPerPiece = new BigDecimal(param.getInstockSecPerPiece());
        }
        else if (kindOfWork.equals(DispResources.getText("LBL-W1222")))
        {
            secPerItem = new BigDecimal(param.getStorageSecPerItem());
            secPerPiece = new BigDecimal(param.getStorageSecPerPiece());
        }
        else if (kindOfWork.equals(DispResources.getText("LBL-W1223")))
        {
            secPerItem = new BigDecimal(param.getRetrievalSecPerItem());
            secPerPiece = new BigDecimal(param.getRetrievalSecPerPiece());
        }
        else if (kindOfWork.equals(DispResources.getText("LBL-W1224")))
        {
            secPerItem = new BigDecimal(param.getSortingSecPerItem());
            secPerPiece = new BigDecimal(param.getSortingSecPerPiece());
        }
        else if (kindOfWork.equals(DispResources.getText("LBL-W1225")))
        {
            secPerItem = new BigDecimal(param.getShippingSecPerItem());
            secPerPiece = new BigDecimal(param.getShippingSecPerPiece());
        }
        else
        {
            return 0;
        }
        // scale::: max((0+2), (0+2))
        return (itemQty.multiply(secPerItem)).add(pieces.multiply(secPerPiece)).doubleValue();
    }

    /**
     * 作業日を取得します。
     * @return 作業日
     * @throws ReadWriteException データベースの接続でエラーを検出した場合に通知されます。
     * @throws ScheduleException 予期しないエラーを検出した場合に通知されます。
     */
    private String getWorkDate()
            throws ReadWriteException,
                ScheduleException
    {
        WarenaviSystemController controller = new WarenaviSystemController(getConnection(), getClass());
        return controller.getWorkDay();
    }

    // DFKLOOK ここまで追加

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
