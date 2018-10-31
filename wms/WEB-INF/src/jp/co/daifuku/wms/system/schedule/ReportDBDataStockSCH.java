// $Id: ReportDBDataStockSCH.java 7717 2010-03-24 06:23:08Z okayama $
package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.system.schedule.ReportDBDataStockSCHParams.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.stock.schedule.StockReportDBDataCreator;

/**
 * DB在庫情報報告のスケジュール処理を行います。
 *
 * @version $Revision: 7717 $, $Date: 2010-03-24 15:23:08 +0900 (水, 24 3 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class ReportDBDataStockSCH
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
    private Connection _customerConn;

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
    public ReportDBDataStockSCH(Connection conn, Connection customerConn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
        setCustomerConnection(customerConn);
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
        // 報告データ作成中フラグが自クラスで更新されているかを判定する為のフラグ
        boolean updateReportCreatorFlag = false;

        // エラーフラグ
        boolean errorFlag = false;

        try
        {
            // 日次更新中のチェックをします。
            if (!canStart())
            {
                return false;
            }
            // 報告データ作成中チェック
            if (isReportData())
            {
                return false;
            }

            // 報告データ作成中フラグ更新
            if (!doReportStart())
            {
                return false;
            }

            doCommit(this.getClass());
            updateReportCreatorFlag = true;

            // 在庫情報報告を実行します。
            // 報告データCSVファイルを作成します。
            // 在庫情報報告のCSVファイルを作成するクラスを生成します。
            StockReportDBDataCreator creater =
                    new StockReportDBDataCreator(this.getConnection(), this.getCustomerConnection());

            // 生成したクラスのCSVファイル作成メソッドを呼び出し、報告データを作成します。
            try
            {
                // 実績ファイルのディレクトリ、Prefix名をセットします。
                creater.setResultReportFilePath();

                // 一時ファイル数取得チェック
                int restCnt = creater.existTempFilesCount();

                for (int i = 0; i < restCnt; i++)
                {
                    if (!creater.isExistTempFile())
                    {
                        // 実績ファイルの作成
                        if (!creater.sendReportFile())
                        {
                            // 6006020=ファイルの入出力エラーが発生しました。{0}
                            RmiMsgLogClient.write("6006020", this.getClass().getName());
                        }
                    }
                }

                // レポートパラメータ作成
                SystemInParameter inParam = new SystemInParameter(getWmsUserInfo());
                inParam.setConsignorCode(ps[0].getString(CONSIGNOR_CODE));
                inParam.setAreaNo(ps[0].getString(AREA_NO));

                // 報告データの作成
                if (!errorFlag && creater.report(inParam))
                {
                    // コミット
                    doCommit(this.getClass());

                    // メッセージをセットする。
                    setMessage(creater.getMessage());

                    // 実績ファイルの作成
                    if (creater.isReport())
                    {
                        // 実績ファイルの送信
                        if (!creater.sendReportFile())
                        {
                            // 6006020=ファイルの入出力エラーが発生しました。{0}
                            RmiMsgLogClient.write("6006020", this.getClass().getName());
                            // 実績ファイルの作成ミス時は、メッセージ再セット
                            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
                            setMessage("6007031");
                        }
                        errorFlag = true;
                    }
                    else
                    {
                        errorFlag = false;
                    }
                }
                else
                {
                    // ロールバック
                    doRollBack(this.getClass());

                    // メッセージをセットする。
                    setMessage(creater.getMessage());
                    errorFlag = true;
                }
            }
            catch (IOException e)
            {
                // 6027009=予期しないエラーが発生しました。ログを参照してください。
                setMessage("6027009");
                return false;
            }

            return true;
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
                doReportEnd();
                doCommit(this.getClass());
            }
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * Returns the holder for the customer's database connection
     * 
     * @return customer's database connection
     */
    public Connection getCustomerConnection()
    {
        return _customerConn;
    }

    /**
     * Set the connection to the customer's database
     * 
     * @param connection Database Connection
     */
    public void setCustomerConnection(Connection connection)
    {
        _customerConn = connection;
    }

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
