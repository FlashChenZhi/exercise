// $Id: ShippingResultABCAnalysisSCH.java 3357 2009-03-12 01:38:10Z arai $
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

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.foundation.common.Params;

import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.CustomerController;
import jp.co.daifuku.wms.analysis.operator.ShippingHistSearchParameter;
import jp.co.daifuku.wms.analysis.operator.ShippingHistAccessor;
import jp.co.daifuku.wms.analysis.operator.ShippingHistSearchResult;
import jp.co.daifuku.wms.analysis.operator.AnalysisIniFileHandler;

/**
 * 出荷実績ABC分析のスケジュール処理を行います。
 *
 * @version $Revision: 3357 $, $Date: 2009-03-12 10:38:10 +0900 (木, 12 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class ShippingResultABCAnalysisSCH
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
    public ShippingResultABCAnalysisSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    // DFKLOOK ここから追加
    /**
     * サブクラスでオーバーライドされていなければ、ScheduleExceptionがスローされます。
     * @param p  サブクラスで定義されます。
     * @return  サブクラスで定義されます。
     * @throws CommonException  サブクラスで定義されます。
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
            setMessage(WmsMessageFormatter.format(6007031));
            return null;
        }

        Params oparams = new Params();

        // Aランクしきい値を取得します。
        int thA = new Integer(iniHandler.getThresholdA()).intValue();
        oparams.set(ShippingResultABCAnalysisSCHParams.THRESHOLD_A, thA);
        // Bランクしきい値を取得します。
        int thB = new Integer(iniHandler.getThresholdB()).intValue();
        oparams.set(ShippingResultABCAnalysisSCHParams.THRESHOLD_B, thB);

        return oparams;
    }

    /** 
     * マスタに登録されているかチェックを行います。<BR>
     * 正常の時はtrue、異常の時はfalseを返します。
     *
     * @param checkParam 入力されたデータを持つ<CODE>ScheduleParams</CODE>クラスのインスタンス。<BR>
     * @return 入力チェックの結果を返します。正常はtrue、異常はfalse<BR>
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。<BR>
     */
    @Override
    public boolean check(ScheduleParams checkParam)
            throws CommonException
    {
        // checkParamの型を変換
        ShippingResultABCAnalysisSCHParams param = (ShippingResultABCAnalysisSCHParams)checkParam;

        String consignorCode = param.getString(ShippingResultABCAnalysisSCHParams.CONSIGNOR_CODE);
        ConsignorController conCtl = new ConsignorController(getConnection(), this.getClass());

        if (!conCtl.exists(consignorCode))
        {
            // 6023040=荷主コードがマスタに登録されていません。
            setMessage(WmsMessageFormatter.format(6023040));
            return false;
        }

        String customerCode = param.getString(ShippingResultABCAnalysisSCHParams.CUSTOMER_CODE);
        if (!StringUtil.isBlank(customerCode))
        {
            CustomerController cusCtl = new CustomerController(getConnection(), this.getClass());
            if (!cusCtl.exists(customerCode, consignorCode))
            {
                // 6023138=出荷先コードがマスタに登録されていません。
                setMessage(WmsMessageFormatter.format(6023138));
                return false;
            }
        }

        return true;
    }

    /** 
     * マスタに登録されているかチェックを行います。<BR>
     * 正常の時はtrue、異常の時はfalseを返します。
     *
     * @param checkParam 入力されたデータを持つ<CODE>ShippingResultABCAnalysisParameter</CODE>クラスのインスタンス。<BR>
     * @return 入力チェックの結果を返します。正常はtrue、異常はfalse<BR>
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。<BR>
     */
    @Override
    public boolean nextCheck(ScheduleParams checkParam)
            throws CommonException
    {
        return check(checkParam);
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
        // DFKLOOK ここから追加
        // 検索パラメータクラスを生成します。
        ShippingHistSearchParameter paramHist = new ShippingHistSearchParameter();

        ShippingResultABCAnalysisSCHParams param = (ShippingResultABCAnalysisSCHParams)p;
        AnalysisOutParameter outParams = new AnalysisOutParameter();
        // 分析種別
        outParams.setAnalysisType(param.getString(ShippingResultABCAnalysisSCHParams.ANALYSIS_TYPE));

        // 分析開始日付と分析終了日付を取得
        Date[] fromToDate = WmsFormatter.getFromTo((Date)param.getDate(ShippingResultABCAnalysisSCHParams.FROM_DATE),
                (Date)param.getDate(ShippingResultABCAnalysisSCHParams.TO_DATE));
        // 分析開始日付
        outParams.setFromDate(WmsFormatter.toParamDate(fromToDate[0]));
        paramHist.setStartDate(WmsFormatter.toParamDate(fromToDate[0]));
        // 分析終了日付
        outParams.setToDate(WmsFormatter.toParamDate(fromToDate[1]));
        paramHist.setEndDate(WmsFormatter.toParamDate(fromToDate[1]));

        // 荷主コード
        outParams.setConsignorCode(param.getString(ShippingResultABCAnalysisSCHParams.CONSIGNOR_CODE));
        paramHist.setConsignorCode(param.getString(ShippingResultABCAnalysisSCHParams.CONSIGNOR_CODE));
        // 出荷先コード
        outParams.setCustomerCode(param.getString(ShippingResultABCAnalysisSCHParams.CUSTOMER_CODE));
        paramHist.setCustomerCode(param.getString(ShippingResultABCAnalysisSCHParams.CUSTOMER_CODE));
        // Aランクしきい値
        outParams.setThresholdA(param.getInt(ShippingResultABCAnalysisSCHParams.THRESHOLD_A));
        // Bランクしきい値
        outParams.setThresholdB(param.getInt(ShippingResultABCAnalysisSCHParams.THRESHOLD_B));

        // 結果格納用配列のクリア
        outParams.clearAbcAnaList();

        ShippingHistAccessor histAccessor = new ShippingHistAccessor(getConnection());
        ShippingHistSearchResult result = (ShippingHistSearchResult)histAccessor.search(paramHist);

        if (result.count() <= 0)
        {
            //  6003011=対象データはありませんでした。
            setMessage(WmsMessageFormatter.format(6003011));
            return null;
        }

        // ABC分析用最小アイテム数をファイルから読み込みます。
        AnalysisIniFileHandler iniP = new AnalysisIniFileHandler();
        if (!iniP.load())
        {
            // 6007031 = ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            return null;
        }

        // 正常に読み込まれても、設定値が数値変換出来ない場合は入出力エラーとする
        if (!StringUtil.isNumberFormat(iniP.getABCAnalysisMinItemNum()))
        {
            // ログへ記録 6026090=Analysis.iniファイルの項目に不正な値が設定されています。項目：{0} 値:{1}
            String message =
                    WmsMessageFormatter.format(6026090, "ABCAnalysisMinItemNum", iniP.getABCAnalysisMinItemNum());
            RmiMsgLogClient.write(message, getClass().getName());
            // 6007031 = ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            return null;
        }

        int minItemNum = Integer.parseInt(iniP.getABCAnalysisMinItemNum());

        // 検索結果件数がABC分析用の最小アイテム数以下かをチェック
        if (result.count() <= minItemNum)
        {
            // 6003108={0}件該当しました。件数が{1}件以下のため、グラフを表示できません。
            String message =
                    WmsMessageFormatter.format(6003108, WmsFormatter.getNumFormat(result.count()),
                            WmsFormatter.getNumFormat(minItemNum));
            setMessage(message);
            return null;
        }

        Object data = null;
        while (null != (data = result.next()))
        {
            // ABC分析用データセット追加
            outParams.addToAbcAnaList(result.getCustomerCode(data), result.getCustomerName(data),
                    result.getItemCode(data), result.getItemName(data), result.getShippingQty(data),
                    result.getWorkQty(data), result.getShippingCnt(data));
        }
        // ABC分析処理
        outParams.calcAbcAnaList(outParams.getThresholdA(), outParams.getThresholdB());

        List<Params> lstOutParams = new ArrayList<Params>();
        lstOutParams.add((Params)outParams);
        return lstOutParams;
        // ここまで追加
    }

    // ここから追加
    /**
     * スケジュールを開始します。Analysis.iniへ書き込みます。<BR>
     * 正しく書き込めた場合はTrueを、書き込めなかった場合はメッセージをセットしFalseを返します。
     *
     * @param startParams 画面で入力された内容が含まれたパラメータクラス
     * @return 正しく書き込めた場合はTrueを、書き込めなかった場合はFalseを返します。
     */
    public boolean startSCH(ScheduleParams startParams)
    {
        ShippingResultABCAnalysisSCHParams param = (ShippingResultABCAnalysisSCHParams)startParams;

        AnalysisIniFileHandler iniP = new AnalysisIniFileHandler();

        iniP.setThresholdA(String.valueOf(param.getInt(ShippingResultABCAnalysisSCHParams.THRESHOLD_A)));
        iniP.setThresholdB(String.valueOf(param.getInt(ShippingResultABCAnalysisSCHParams.THRESHOLD_B)));

        if (!iniP.saveAbcAnalysis())
        {
            String message = WmsMessageFormatter.format(6006020, WmsParam.ANALYSIS_INI_PATH);
            RmiMsgLogClient.write(message, getClass().getName());
            // 6007031 = ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage("6007031");
            return false;
        }
        return true;
    }
    // ここまで追加

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
