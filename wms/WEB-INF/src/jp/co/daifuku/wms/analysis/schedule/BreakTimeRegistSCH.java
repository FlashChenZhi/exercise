// $Id: BreakTimeRegistSCH.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.analysis.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.analysis.operator.AnalysisIniFileHandler;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 休憩時間設定のスケジュール処理を行います。
 * 
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: arai $
 */
public class BreakTimeRegistSCH
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
    public BreakTimeRegistSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // 分析設定ファイルハンドラ
        AnalysisIniFileHandler iniP = new AnalysisIniFileHandler();

        // 分析設定ファイル読込処理
        if (!iniP.load())
        {
            // RMIログ出力
            String message = WmsMessageFormatter.format(6006020, WmsParam.ANALYSIS_INI_PATH);
            RmiMsgLogClient.write(message, getClass().getName());

            // (6007031)ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));

            // 異常の場合falseを返却
            return false;
        }

        // 分析設定ファイルの休憩時間をクリア
        iniP.clearBreakTime();

        // 休憩時間開始時刻
        String saveStart = "";
        // 休憩時間終了時刻
        String saveEnd = "";
        // ループカウント
        int lCnt = 0;

        // パラメータで指定された休憩時間を設定します。
        for (Params param : ps)
        {
            // インクリメント
            lCnt++;

            // 保持時間クリア
            // 休憩開始時刻
            saveStart = "";
            // 休憩終了時刻
            saveEnd = "";

            // 休憩時間開始取得
            saveStart = param.getString(BreakTimeRegistSCHParams.BREAK_START_TIME);
            // 休憩時間終了取得
            saveEnd = param.getString(BreakTimeRegistSCHParams.BREAK_END_TIME);

            // 休憩時間の設定
            iniP.setBreakTime(lCnt - 1, saveStart, saveEnd);

            // 休憩回数上限と同数の場合
            if (lCnt == WmsParam.BREAKTIME_MAX)
            {
                // 休憩回数上限の場合処理抜け
                break;
            }
        }

        // 休憩時間の保存
        if (iniP.saveBreakTime())
        {
            // (6001006)設定しました。
            setMessage("6001006");

            // 正常に保存できた場合trueを返却
            return true;
        }
        else
        {
            // RIMログ出力
            String message = WmsMessageFormatter.format(6006020, WmsParam.ANALYSIS_INI_PATH);
            RmiMsgLogClient.write(message, getClass().getName());

            // (6007031)ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6007031));

            // 異常の場合falseを返却
            return false;
        }
    }

    /**
     * 画面から入力された内容とリストセルエリアのデータをパラメータとして受け取り、
     * チェックを行います。<BR>
     *
     * @param p 入力パラメータ
     * @param ps リストセルエリアのパラメータ
     * @return 入力チェック、オーバーフロー、重複、商品マスタ・入庫棚エラーでない場合はtrueを返す。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public boolean check(ScheduleParams p, ScheduleParams... ps)
            throws CommonException
    {
        // ためうち表示件数チェック
        if (ps != null && ps.length + 1 > WmsParam.BREAKTIME_MAX)
        {
            // (6023019)件数が{0}件を超えるため、入力できません。
            setMessage(WmsMessageFormatter.format(6023019, WmsFormatter.getNumFormat(WmsParam.BREAKTIME_MAX)));
            return false;
        }

        // 入力した開始時刻
        String strStartTime = p.getString(BreakTimeRegistSCHParams.BREAK_START_TIME);
        // 入力した終了時刻
        String strEndTime = p.getString(BreakTimeRegistSCHParams.BREAK_END_TIME);
        // リストの開始時刻
        String strStartTimeList = "";
        // リストの終了時刻
        String strEndTimeList = "";

        // 重複、範囲チェック
        if (ps != null)
        {
            // リスト件数分、繰り返す
            for (ScheduleParams schParam : ps)
            {
                strStartTimeList = schParam.getString(BreakTimeRegistSCHParams.BREAK_START_TIME);
                strEndTimeList = schParam.getString(BreakTimeRegistSCHParams.BREAK_END_TIME);

                // 入力した開始時刻のチェック
                if (withinTimes(strStartTime, strStartTimeList, strEndTimeList))
                {
                    // (6023604)設定した時刻範囲は、既に登録されている休憩時間の範囲と重複しています。
                    setMessage(WmsMessageFormatter.format(6023604));
                    return false;
                }

                // 入力した終了時刻のチェック
                if (withinTimes(strEndTime, strStartTimeList, strEndTimeList))
                {
                    // (6023604)設定した時刻範囲は、既に登録されている休憩時間の範囲と重複しています。
                    setMessage(WmsMessageFormatter.format(6023604));
                    return false;
                }

                // リストの開始時刻チェック
                if (withinTimes(strStartTimeList, strStartTime, strEndTime))
                {
                    // (6023604)設定した時刻範囲は、既に登録されている休憩時間の範囲と重複しています。
                    setMessage(WmsMessageFormatter.format(6023604));
                    return false;
                }

                // リストの終了時刻チェック
                if (withinTimes(strEndTimeList, strStartTime, strEndTime))
                {
                    // (6023604)設定した時刻範囲は、既に登録されている休憩時間の範囲と重複しています。
                    setMessage(WmsMessageFormatter.format(6023604));
                    return false;
                }

                // 開始時刻、終了時刻の同一チェック
                if (strStartTime.equals(strEndTime))
                {
                    // (6023607)休憩開始時刻と休憩終了時刻が同一のため、入力できません。
                    setMessage(WmsMessageFormatter.format(6023607));
                    return false;
                }
            }
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
     * 第１引数が第２引数と第３引数の範囲に含まれているかどうかを返します。<BR>
     * 引数は、"HH:MM"形式の２４時間制時刻です。<BR>
     * <BR>
     * @param target チェック対象時刻
     * @param from 開始時刻
     * @param to 終了時刻
     * @return チェック結果（true:含まれている false:含まれていない）
     */
    private boolean withinTimes(String target, String from, String to)
    {
        // 開始 < 終了の関係の場合
        if (from.compareTo(to) <= 0)
        {
            // 入力した時刻が既に登録されている時刻の
            // From以降であり、To以前の場合
            if (target.compareTo(from) >= 0 && target.compareTo(to) <= 0)
            {
                // 含まれているため、trueを返却
                return true;
            }
        }
        // 上記以外の場合
        else
        {
            // 入力した時刻が既に登録されている時刻の
            // From以降であり、24時以前の場合
            if (target.compareTo(from) >= 0 && target.compareTo("24:00") < 0)
            {
                // 含まれているため、trueを返却
                return true;
            }

            // 入力した時刻が既に登録されている時刻の
            // 0時以降であり、To以前の場合
            if (target.compareTo("00:00") >= 0 && target.compareTo(to) <= 0)
            {
                // 含まれているため、trueを返却
                return true;
            }
        }
        // 含まれていないため、falseを返却
        return false;
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
