// $Id: WmsFirstExecutor.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.base.util.startup;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.entity.WarenaviSystem;


/**
 * eWareNavi開始処理クラス<br>
 *
 * Designer : E.Takeda <BR>
 * Maker : E.Takeda <BR>
 * <BR>
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  etakeda
 * @author  Last commit: $Author: kishimoto $
 */


public class WmsFirstExecutor
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * クラス名
     */
    private static final String CLASS_NAME = "WmsFirstExecutor";


    /**
     * 終了コード：正常
     */
    private static final int EXIT_CODE_NOMAL = 0;

    /**
     * 終了コード：エラー
     */
    private static final int EXIT_CODE_ERROR = -9;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * 終了コード
     */
    private int _exitCode;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ.
     */
    public WmsFirstExecutor()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * eWareNavi開始処理アプリを起動
     * @param args (未使用)
     */
    public static void main(String[] args)
    {
        // コネクション
        Connection conn = null;
        WmsFirstExecutor exec = null;

        try
        {
            // コネクション取得
            conn = WmsParam.getConnection();
            System.out.println("**** WmsFirstExecutor start!! ****");
            // このクラスのインスタンスを生成
            exec = new WmsFirstExecutor();

            // 各処理中フラグを初期化します
            exec.initWareNaviSystemFlag(conn);

            exec.setExitCode(EXIT_CODE_NOMAL);
            System.out.println("**** complete!! ****");


        }
        catch (Exception ex)
        {
            System.out.println("**** failure!! ****");
            // 6006036=修正できませんでした。
            RmiMsgLogClient.write(new TraceHandler(6006036, ex), CLASS_NAME);
            // ロールバック
            try
            {
                conn.rollback();
                exec.setExitCode(EXIT_CODE_ERROR);
            }
            catch (SQLException esql)
            {
                // エラーをログファイルに落とす
                RmiMsgLogClient.write(new TraceHandler(6006002, esql), CLASS_NAME);
            }
        }
        finally
        {
            try
            {
                if (conn != null)
                {
                    conn.close();
                    conn = null;
                    System.exit(exec.getExitCode());
                }
            }
            catch (SQLException ex)
            {
                // エラーをログファイルに落とす
                RmiMsgLogClient.write(new TraceHandler(6006002, ex), CLASS_NAME);
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


    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * システム定義情報の各処理中フラグ状態を初期化します。<BR>
     * @param conn コネクション
     *
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     */
    private void initWareNaviSystemFlag(Connection conn)
            throws ReadWriteException,
                ScheduleException
    {
        // システム定義情報アクセスコントローラのインスタンスを生成
        WarenaviSystemController warenaviSystemController = new WarenaviSystemController(conn, getClass());

        try
        {
            // 日次更新状態
            if (warenaviSystemController.isDailyUpdating())
            {
                warenaviSystemController.updateDailyUpdating(false);
                // 6024071=処理フラグが不正なデータが存在したため、強制的に初期化しました。テーブル:{0} 項目:{1}
                RmiMsgLogClient.write(WmsMessageFormatter.format(6024071, WarenaviSystem.STORE_NAME,
                        WarenaviSystem.DAILY_UPDATE), CLASS_NAME);
            }
            // 取込処理状態
            if (warenaviSystemController.isDataLoading())
            {
                warenaviSystemController.updateDataLoading(false);
                // 6024071=処理フラグが不正なデータが存在したため、強制的に初期化しました。テーブル:{0} 項目:{1}
                RmiMsgLogClient.write(WmsMessageFormatter.format(6024071, WarenaviSystem.STORE_NAME,
                        WarenaviSystem.LOAD_DATA), CLASS_NAME);
            }
            // 報告処理状態
            if (warenaviSystemController.isDataReporting())
            {
                warenaviSystemController.updateDataReporting(false);
                // 6024071=処理フラグが不正なデータが存在したため、強制的に初期化しました。テーブル:{0} 項目:{1}
                RmiMsgLogClient.write(WmsMessageFormatter.format(6024071, WarenaviSystem.STORE_NAME,
                        WarenaviSystem.REPORT_DATA), CLASS_NAME);
            }
            // 出庫開始状態
            if (warenaviSystemController.isRetrievalAllocating())
            {
                warenaviSystemController.updateRetrievalAllocating(false);
                // 6024071=処理フラグが不正なデータが存在したため、強制的に初期化しました。テーブル:{0} 項目:{1}
                RmiMsgLogClient.write(WmsMessageFormatter.format(6024071, WarenaviSystem.STORE_NAME,
                        WarenaviSystem.RETRIEVAL_ALLOCATE), CLASS_NAME);
            }
            // 搬送クリア状態
            if (warenaviSystemController.isAllocationClear())
            {
                warenaviSystemController.updateAllocationClear(false);
                // 6024071=処理フラグが不正なデータが存在したため、強制的に初期化しました。テーブル:{0} 項目:{1}
                RmiMsgLogClient.write(WmsMessageFormatter.format(6024071, WarenaviSystem.STORE_NAME,
                        WarenaviSystem.ALLOCATION_CLEAR), CLASS_NAME);
            }
            // 終了処理中状態
            if (warenaviSystemController.isEndProcessing())
            {
                // 終了処理中状態を元に戻します。
                warenaviSystemController.updateEndProcessing(false);
            }
        }
        catch (NotFoundException e)
        {
            // 更新対象なしの場合は無視
        }
    }

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: WmsFirstExecutor.java 8053 2013-05-15 01:00:52Z kishimoto $";
    }

    /**
     * 終了コードを返します。
     * @return _exitCode 終了コード
     */
    public int getExitCode()
    {
        return _exitCode;
    }

    /**
     * 終了コードを設定します。
     * @param exitCode 終了コード
     */
    public void setExitCode(int exitCode)
    {
        _exitCode = exitCode;
    }
}

