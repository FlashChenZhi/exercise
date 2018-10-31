// $Id: LstShippingResultABCAnalysisDASCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.analysis.dasch;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.wms.handler.db.AbstractDBFinder;

import jp.co.daifuku.wms.base.report.AbstractWmsDASCH;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.analysis.schedule.AnalysisOutParameter;
import jp.co.daifuku.wms.analysis.schedule.ShippingResultABCAnalysisSCH;
import jp.co.daifuku.wms.analysis.schedule.ShippingResultABCAnalysisSCHParams;
import jp.co.daifuku.wms.analysis.operator.ShippingHistSearchParameter;
import jp.co.daifuku.wms.analysis.operator.ShippingHistAccessor;
import jp.co.daifuku.wms.analysis.operator.ShippingHistSearchResult;

import static jp.co.daifuku.wms.analysis.dasch.LstShippingResultABCAnalysisDASCHParams.*;

/**
 * 出荷実績ABC分析一覧に必要なリストボックスや帳票の検索処理を行います。
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class LstShippingResultABCAnalysisDASCH
        extends AbstractWmsDASCH
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

    /**
     * DB Finder
     */
    private AbstractDBFinder _finder = null;

    /**
     * 現在点
     */
    private int _current = -1;

    /**
     * レコード総数
     */

    private int _total = -1;

    // DFKLOOK ここから追加
    /**
     * 検索結果データ格納用パラメータクラス
     */
    private AnalysisOutParameter _result = null;
    // DFKLOOK ここまで追加

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでDASCHを作成します。
     * @param conn Database DBコネクション
     * @param parent Caller 呼び出し元クラスクラス情報
     * @param locale ロケーる
     * @param ui ユーザ情報
     */
    public LstShippingResultABCAnalysisDASCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * DBの検索を行います。
     * 帳票出力、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public void search(Params p)
            throws CommonException
    {
        find(p);
        _current = -1;

        setMessage("6001013");
    }

    /**
     * 次のデータが存在するかどうかを判定します。<BR>
     * 帳票出力で使用します。
     * @return データが存在する場合はtrueを返します。
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public boolean next()
            throws CommonException
    {
        _current++;
        return _total > _current;
    }

    /**
     * データを1件返します。<BR>
     * 帳票出力で使用します。
     * @return 取得データ
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    public Params get()
            throws CommonException
    {
        throw new ScheduleException("This method is not implemented.");
    }

    /**
     *
     * finder,Connection close
     */
    public void close()
    {
        if (_finder != null)
        {
            _finder.close();
        }
        super.close();
    }

    // DFKLOOK ここから追加
    /**
     * ABC分析した結果を返します。
     *
     * @return <code>AnalysisOutParameter ABC</code>分析結果
     */
    public AnalysisOutParameter getResult()
    {
        // 検索パラメータクラスを生成します。
        return _result;
    }

    /**
     * ABC分析結果に値をセットします。
     *
     * @param param <code>AnalysisOutParameter ABC</code>分析結果
     */
    public void setResult(AnalysisOutParameter param)
    {
        // 検索パラメータクラスを生成します。
        _result = param;
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
    /**
     * データ件数を返します。
     * 帳票発行、一覧表示で使用します。
     *
     * @param p 検索条件パラメータ
     * @return データ件数
     * @throws CommonException 全てのユーザ定義例外を報告します。
     */
    protected int actualCount(Params p)
            throws CommonException
    {
        // DFKLOOK ここから追加
        // 分析一覧データを取得する。
        find(p);

        // 結果の件数を返す。
        if (_result != null)
        {
            return _result.getSizeOfAbcAnaList();
        }
        else
        {
            return 0;
        }
        // DFKLOOK ここまで追加
    }

    /**
     * 検索したデータのうち、start番目からcntで指定された件数のデータを返します。
     * 一覧表示で使用します。
     *
     * @param start 開始インデックス
     * @param cnt 件数
     * @return 対象データのリスト
     * @throws CommonException エラーを検出した場合に通知されます。
     */
    protected List<Params> rangeGet(int start, int cnt)
            throws CommonException
    {
        List<Params> params = new ArrayList<Params>();

        for (int i = start; i < start + cnt; i++)
        {
          Params p = new Params();
          p.set(NO, i + 1);
          p.set(ITEM_CODE, _result.getItemCodeFromAbcAnaList(i));
          p.set(ITEM_NAME, _result.getItemNameFromAbcAnaList(i));
          p.set(CLASS, _result.getRankFromAbcAnaList(i));
          p.set(SHIP_QTY, _result.getShippingQtyFromAbcAnaList(i));
          p.set(WORK_QTY, _result.getWorkingQtyFromAbcAnaList(i));
          p.set(SHIP_COUNT, _result.getShippingCntFromAbcAnaList(i));
          p.set(PERCENT, _result.getRatioFromAbcAnaList(i));
          p.set(CUMULATIVE_PERCENT, _result.getAccumRatioFromAbcAnaList(i));
          params.add(p);
          if (i + 1 >= _result.getSizeOfAbcAnaList())
          {
              break;
          }
            
        }
        return params;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    // DFKLOOK ここから追加
    /**
     * 入力されたパラメータをもとにABC分析を行ないます。
     *
     * @param param <code>ShippingResultABCAnalysisParameter</code>検索結果を含むパラメータ
     * @throws CommonException データベースの接続でエラーを検出した場合に通知されます。
     */
    protected void find(Params param)
            throws CommonException
    {
        // 検索パラメータクラスを生成します。
        ShippingHistSearchParameter paramHist = new ShippingHistSearchParameter();

        // 開始分析対象期間と、終了分析対象期間の大小関係を補正して取得します。
        Date[] fromToDate = WmsFormatter.getFromTo(param.getDate(LstShippingResultABCAnalysisDASCHParams.FROM_DATE),
                                                    param.getDate(LstShippingResultABCAnalysisDASCHParams.TO_DATE));
        // 分析開始日付
        paramHist.setStartDate(WmsFormatter.toParamDate(fromToDate[0]));
        // 分析終了日付
        paramHist.setEndDate(WmsFormatter.toParamDate(fromToDate[1]));
        // 荷主コード
        paramHist.setConsignorCode(param.getString(LstShippingResultABCAnalysisDASCHParams.CONSIGNOR_CODE));
        // 出荷先コード
        paramHist.setCustomerCode(param.getString(LstShippingResultABCAnalysisDASCHParams.CUSTOMER_CODE));

        ShippingResultABCAnalysisSCHParams inparam = new ShippingResultABCAnalysisSCHParams();

        inparam.set(ShippingResultABCAnalysisSCHParams.ANALYSIS_TYPE
                        , param.getString(LstShippingResultABCAnalysisDASCHParams.ANALYSIS_TYPE));
        inparam.set(ShippingResultABCAnalysisSCHParams.CONSIGNOR_CODE
                , param.getString(LstShippingResultABCAnalysisDASCHParams.CONSIGNOR_CODE));
        inparam.set(ShippingResultABCAnalysisSCHParams.CUSTOMER_CODE
                , param.getString(LstShippingResultABCAnalysisDASCHParams.CUSTOMER_CODE));
        inparam.set(ShippingResultABCAnalysisSCHParams.THRESHOLD_A
                , param.getInt(LstShippingResultABCAnalysisDASCHParams.THRESHOLD_A));
        inparam.set(ShippingResultABCAnalysisSCHParams.THRESHOLD_B
                , param.getInt(LstShippingResultABCAnalysisDASCHParams.THRESHOLD_B));

        // スケジュールの宣言
        ShippingResultABCAnalysisSCH
            schedule = new ShippingResultABCAnalysisSCH(getConnection(), this.getClass()
                                                            , getLocale(), getUserInfo());

        // マスタ登録チェック
        if (!schedule.check(inparam))
        {
            setMessage(schedule.getMessage());
            // 初期化
            _current = 0;
            return;
        }

        // 荷主名称と出荷先名称のセット
        ShippingResultABCAnalysisSCHParams params = new ShippingResultABCAnalysisSCHParams();
        params.set(ShippingResultABCAnalysisSCHParams.THRESHOLD_A
                , param.getInt(LstShippingResultABCAnalysisDASCHParams.THRESHOLD_A));
        params.set(ShippingResultABCAnalysisSCHParams.THRESHOLD_B
                , param.getInt(LstShippingResultABCAnalysisDASCHParams.THRESHOLD_B));

        // スケジュールを開始します。
        if (!schedule.startSCH(params))
        {
            // 保存に失敗
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));
            // 初期化
            _current = 0;
            return;
        }

        // 結果格納用配列のクリア
        AnalysisOutParameter sParam = new AnalysisOutParameter();
        sParam.clearAbcAnaList();

        ShippingHistAccessor histAccessor = new ShippingHistAccessor(getConnection());
        ShippingHistSearchResult result = (ShippingHistSearchResult)histAccessor.search(paramHist);

        Object data = null;
        while (null != (data = result.next()))
        {
            // ABC分析用データセット追加
            sParam.addToAbcAnaList(result.getCustomerCode(data), result.getCustomerName(data),
                    result.getItemCode(data), result.getItemName(data), result.getShippingQty(data),
                    result.getWorkQty(data), result.getShippingCnt(data));
        }
        sParam.setAnalysisType(param.getString(LstShippingResultABCAnalysisDASCHParams.ANALYSIS_TYPE));
        // ABC分析処理
        int tha = param.getBigDecimal(ShippingResultABCAnalysisSCHParams.THRESHOLD_A).intValue();
        int thb = param.getBigDecimal(ShippingResultABCAnalysisSCHParams.THRESHOLD_B).intValue();
        sParam.calcAbcAnaList(tha, thb);

        // Sessionに結果を保持する。
        setResult(sParam);
        setMessage("");

        // 初期化
        _current = 0;

        _total = _result.getSizeOfAbcAnaList();
    }
    //  DFKLOOK ここまで追加

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
