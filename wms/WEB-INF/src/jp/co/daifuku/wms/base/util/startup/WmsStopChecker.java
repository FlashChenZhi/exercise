// $Id: WmsStopChecker.java 8053 2013-05-15 01:00:52Z kishimoto $
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
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WarenaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WarenaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.SystemDefine;


/**
 *
 * eWareNavi終了チェック処理.<br>
 *
 * Designer : E.Takeda <BR>
 * Maker : E.Takeda <BR>
 * <BR>
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  etakeda
 * @author  Last commit: $Author: kishimoto $
 */


public class WmsStopChecker
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * クラス名
     */
    private static final String CLASS_NAME = "WmsStopChecker";

    /**
     * 終了コード：正常
     */
    private static final int EXIT_CODE_NOMAL = 0;

    /**
     * 終了コード：日次更新中
     */
    private static final int EXIT_CODE_DAILYUPDATING = -1;

    /**
     * 終了コード：予定データ取込中
     */
    private static final int EXIT_CODE_DATALODING = -2;

    /**
     * 終了コード：報告データ作成中
     */
    private static final int EXIT_CODE_DATAREPORTING = -3;

    /**
     * 終了コード：出庫開始中
     */
    private static final int EXIT_CODE_RETRIEVALALLOCATING = -4;

    /**
     * 終了コード：システムオンライン
     */
    private static final int EXIT_CODE_SYSTEMONLINE = -5;

    /**
     * 終了コード：エラー
     */
    private static final int EXIT_CODE_ERROR = -9;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    /**
     * 終了コード
     */
    private int _exitCode;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ.
     */
    public WmsStopChecker()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * eWareNavi終了処理アプリを起動
     * @param args (未使用)
     */
    public static void main(String[] args)
    {
        // コネクション
        Connection conn = null;
        WmsStopChecker cheker = null;
        try
        {
            // コネクション取得
            conn = WmsParam.getConnection();
            System.out.println("**** WmsStopChecker start!! ****");
            // このクラスのインスタンスを生成
            cheker = new WmsStopChecker();

            // チェック前に、各処理中フラグが処理中に更新されないようにロック
            WarenaviSystemHandler wnHandler = new WarenaviSystemHandler(conn);
            WarenaviSystemSearchKey wnKey = new WarenaviSystemSearchKey();
            wnKey.setSystemNo(SystemDefine.SYSTEM_NO_DEFAULT);
            wnHandler.lock(wnKey);

            // チェック処理を行います
            if (cheker.check(conn))
            {
                // 終了処理中フラグを処理中に設定する。
                cheker.updateEndProcessingFlag(conn);
                conn.commit();
                System.out.println("**** complete!! ****");
            }
            else
            {
                System.out.println("**** failure!! ****");
            }

        }
        catch (Exception ex)
        {
            System.out.println("**** failure!! ****");
            cheker.setExitCode(EXIT_CODE_ERROR);
            // 6006036=修正できませんでした。
            RmiMsgLogClient.write(new TraceHandler(6006036, ex), CLASS_NAME);
            // ロールバック
            try
            {
                conn.rollback();
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
                    conn.rollback();
                    conn.close();
                    conn = null;
                }
                // システム終了
                System.exit(cheker.getExitCode());
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
     * システム定義情報の処理フラグチェック処理<BR>
     * @param conn コネクション
     * @return boolean 終了処理可：<CODE>true</CODE>、終了処理不可：<CODE>false</CODE>
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     */
    private boolean check(Connection conn)
            throws ReadWriteException,
                ScheduleException

    {
        // システム定義情報アクセスコントローラのインスタンスを生成
        WarenaviSystemController warenaviSystemController = new WarenaviSystemController(conn, getClass());

        //グループコントローラのハンドラのインスタンスを生成します。
        GroupControllerHandler grdbHandle = new GroupControllerHandler(conn);

        // AS/RSグループコントローラー情報検索キー
        GroupControllerSearchKey grKey = new GroupControllerSearchKey();

        // いずれかの処理が実行（1：起動中）の場合、Falseを返す。
        /***** 日次更新中 *****/
        if (warenaviSystemController.isDailyUpdating())
        {
            // 6026120=日次更新を行っているため終了処理を中断しました。
            RmiMsgLogClient.write("6026120", CLASS_NAME);
            setExitCode(EXIT_CODE_DAILYUPDATING);
            System.out.println("**** Error DailyUpdating ****");
            return false;
        }
        /***** 予定データ取込中 *****/
        if (warenaviSystemController.isDataLoading())
        {
            // 6023012=データ取込み中のため終了処理を中断しました。。
            RmiMsgLogClient.write("6026121", CLASS_NAME);
            setExitCode(EXIT_CODE_DATALODING);
            System.out.println("**** Error DataLoading ****");
            return false;
        }
        /***** 報告データ作成中 *****/
        if (warenaviSystemController.isDataReporting())
        {
            // 6023013=データ報告中のため終了処理を中断しました。
            RmiMsgLogClient.write("6026122", CLASS_NAME);
            setExitCode(EXIT_CODE_DATAREPORTING);
            System.out.println("**** Error DataReporting ****");
            return false;
        }
        /***** 出庫開始中 *****/
        if (warenaviSystemController.isRetrievalAllocating())
        {
            // 6023101=出庫開始中のため終了処理を中断しました。
            RmiMsgLogClient.write("6026123", CLASS_NAME);
            setExitCode(EXIT_CODE_RETRIEVALALLOCATING);
            System.out.println("**** Error RetrievalAllcating ****");
            return false;
        }
        /**
         *  システムオンライン
         *  AS/RSパッケージフラグ１の場合のみチェックします。
         */
        if( warenaviSystemController.hasAsrsPack())
        {
            //検索キーをセットします。(状態:オンライン)
            grKey.setStatusFlag(GroupController.GC_STATUS_ONLINE);
            //オンラインのグループコントローラの数をチェックします。0でなければ、falseを返します。
            if(0 != grdbHandle.count(grKey))
            {
                //6024050=システムがオンラインのため終了処理を中断しました。
                RmiMsgLogClient.write("6024050", CLASS_NAME);
                setExitCode(EXIT_CODE_SYSTEMONLINE);
                System.out.println("**** Error SystemOnline ****");
                return false;
            }
        }
        // 終了コード正常
        setExitCode(EXIT_CODE_NOMAL);
        return true;
    }

    /**
     * システム定義情報の終了処理中フラグを処理中に更新します。<BR>
     * @param conn コネクション
     *
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     */
    private void updateEndProcessingFlag(Connection conn)
            throws ReadWriteException,
                ScheduleException
    {
        // システム定義情報アクセスコントローラのインスタンスを生成
        WarenaviSystemController warenaviSystemController = new WarenaviSystemController(conn, getClass());

        try
        {
            // 終了処理フラグを更新する。
            warenaviSystemController.updateEndProcessing(true);
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
        return "$Id: WmsStopChecker.java 8053 2013-05-15 01:00:52Z kishimoto $";
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
