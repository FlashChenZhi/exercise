// $Id: InventoryMovementSCH.java 3208 2009-03-02 05:42:52Z arai $
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
import java.util.Calendar;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.foundation.common.Params;

import jp.co.daifuku.wms.analysis.operator.InventoryHistAccessor;
import jp.co.daifuku.wms.analysis.operator.InventoryHistSearchParameter;
import jp.co.daifuku.wms.analysis.operator.InventoryHistSearchResult;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.util.WmsFormatter;

import jp.co.daifuku.foundation.common.ScheduleParams;

/**
 * 在庫推移分析のスケジュール処理を行います。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class InventoryMovementSCH
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
    public InventoryMovementSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
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
        // DFKLOOK ここから追加
        // searchParamの型を変換
        InventoryMovementSCHParams param = (InventoryMovementSCHParams)p;
        AnalysisOutParameter anaParam = new AnalysisOutParameter();

        // 検索パラメータクラスを生成します。
        InventoryHistSearchParameter paramHist = new InventoryHistSearchParameter();

        // 分析単位
        anaParam.setAnalysisMode(param.getString(InventoryMovementSCHParams.ANALYSIS_MODE));
        paramHist.setSummaryType(param.getString(InventoryMovementSCHParams.ANALYSIS_MODE));
        // 検索開始年月日
        anaParam.setAnalysisStartDate(param.getString(InventoryMovementSCHParams.ANALYSIS_START_DATE));
        paramHist.setStartDate(param.getString(InventoryMovementSCHParams.ANALYSIS_START_DATE));
        // 検索終了年月日
        anaParam.setAnalysisEndDate(param.getString(InventoryMovementSCHParams.ANALYSIS_END_DATE));
        paramHist.setEndDate(param.getString(InventoryMovementSCHParams.ANALYSIS_END_DATE));
        // 荷主コード
        anaParam.setConsignorCode(param.getString(InventoryMovementSCHParams.CONSIGNOR_CODE));
        paramHist.setConsignorCode(param.getString(InventoryMovementSCHParams.CONSIGNOR_CODE));
        // 商品コード
        anaParam.setItemCode(param.getString(InventoryMovementSCHParams.ITEM_CODE));
        paramHist.setItemCode(param.getString(InventoryMovementSCHParams.ITEM_CODE));
        // ""（空文字列）をセットすることで全件対象となる。（InventoryHistSearchParameterの仕様）
        paramHist.setLotNo("");
        // 検索条件
        paramHist.setSearchType(param.getString(InventoryMovementSCHParams.ITEM_LIST_CONDITION));

        // 荷主名称と商品名称の再取得
        anaParam.setConsignorName(this.getConsignorName(anaParam.getConsignorCode()));
        anaParam.setItemName(this.getItemName(anaParam.getConsignorCode(), anaParam.getItemCode()));

        // 結果格納用配列のクリア
        anaParam.clearInventoryList();

        // 月単位表示は、各月最終日の在庫数推移を表示することにする。
        // 月単位表示モードの判断は、検索開始年と終了年が違うことで判断する。
        // （検索単位を日単位で設定するように変更した為）
        // ただし作業日以降のデータは破棄し、作業日の前日時点の在庫数を表示する。
        // また、入庫数、出庫数は月単位で集計することとする。
        WarenaviSystemController controller = new WarenaviSystemController(getConnection(), getClass());
        // 作業日の前日取得
        Calendar cal = Calendar.getInstance();
        cal.setTime(WmsFormatter.toDate(controller.getWorkDay()));
        cal.add(Calendar.DATE, -1);
        String workDate = WmsFormatter.toParamDate(cal.getTime());

        String startYear = anaParam.getAnalysisStartDate().substring(0, 4);
        String endYear = anaParam.getAnalysisEndDate().substring(0, 4);
        boolean isUnitMonthMode = !startYear.equals(endYear);
        long storageSum = 0;
        long retrievalSum = 0;

        InventoryHistAccessor histAccessor = new InventoryHistAccessor(getConnection());
        InventoryHistSearchResult result = (InventoryHistSearchResult)histAccessor.search(paramHist);

        int dataCount = 0;
        Object data = null;
        while (null != (data = result.next()))
        {
            String currDate = result.getTimeStamp(data);
            storageSum += result.getStorageQty(data);
            if (storageSum < 0)
            {
                storageSum = 0;
            }
            retrievalSum += result.getRetrievalQty(data);
            if (retrievalSum < 0)
            {
                retrievalSum = 0;
            }

            // 月単位表示は、各月最終日の在庫数推移を表示することにするので、それ以外のデータは読み捨てる。
            if (isUnitMonthMode)
            {
                String lastDay = getLastDay(currDate);
                if (!currDate.endsWith(lastDay) && !currDate.equals(workDate))
                {
                    continue;
                }
            }

            // 在庫推移分析用データセットを追加します。
            dataCount++;
            anaParam.addToInventoryList(result.getTimeStamp(data), result.getStockQty(data), storageSum,
                    retrievalSum, result.getStockUbQty(data), result.getStockLbQty(data));

            storageSum = 0;
            retrievalSum = 0;

            // 月単位表示は、作業日を越えるデータは表示しない。
            if (isUnitMonthMode && currDate.equals(workDate))
            {
                break;
            }
        }

        if (0 == dataCount)
        {
            // 6003011=対象データはありませんでした。
            setMessage("6003011");
        }
        else
        {
            // 6001013=表示しました。
            setMessage("6001013");
        }

        List<Params> analysisParams = new ArrayList<Params>();
        analysisParams.add((Params)anaParam);
        return analysisParams;
        // DFKLOOK ここまで追加
   }

    /**
     * マスタに登録されているかチェックを行います。
     *
     * @param p 入力されたデータを持つScheduleParams
     * @return 入力チェックの結果を返します。正常はtrue、異常はfalse
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public boolean check(ScheduleParams p)
            throws CommonException
    {
        // searchParamの型を変換
        InventoryMovementSCHParams param = (InventoryMovementSCHParams)p;

        ConsignorController conCtl = new ConsignorController(getConnection(), this.getClass());
        if (!conCtl.exists(param.getString(InventoryMovementSCHParams.CONSIGNOR_CODE)))
        {
            // 6023040=荷主コードがマスタに登録されていません。
            setMessage(WmsMessageFormatter.format(6023040));
            return false;
        }

        ItemController itemCtl = new ItemController(getConnection(), this.getClass());
        if (!itemCtl.exists(param.getString(InventoryMovementSCHParams.ITEM_CODE),
                            param.getString(InventoryMovementSCHParams.CONSIGNOR_CODE)))
        {
            // 6023021=商品コードがマスタに登録されていません。
            setMessage("6023021");
            return false;
        }

        return true;
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
     * パラメータで指定された月の最終日を返します。
     * @param date 指定日(yyyyMMdd) 
     * @return 最終日
     */
    private String getLastDay(String date)
    {
        Calendar cal = Calendar.getInstance();
        String firstDate = date.substring(0, 6) + "01";
        cal.setTime(WmsFormatter.toDate(firstDate));
        cal.add(Calendar.MONTH, +1);
        cal.add(Calendar.DATE, -1);
        String lastDay = WmsFormatter.toParamDate(cal.getTime());
        return lastDay.substring(6);
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
