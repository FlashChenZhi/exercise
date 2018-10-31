// $Id: WorkDayAutoUpdate.java 3266 2009-03-09 05:09:11Z okamura $
package jp.co.daifuku.wms.base.util;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.*;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.WarenaviSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WarenaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WarenaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WarenaviSystem;
import jp.co.daifuku.wms.handler.Entity;

import org.apache.log4j.xml.DOMConfigurator;


/**
 * Designer : E.Takeda <BR>
 * Maker : E.Takeda <BR>
 * <BR>
 * eWareNaviSystemのWMS作業日を更新するクラス<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2007/05/08</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 3266 $, $Date: 2009-03-09 14:09:11 +0900 (月, 09 3 2009) $
 * @author  etakeda
 * @author  Last commit: $Author: okamura $
 */


public class WorkDayAutoUpdate
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;


    /**
     * ユーザ情報で格納するユーザID
     */
    private static final String USER_ID = "Auto";

    /**
     * ユーザ情報に格納する端末IP
     */
    private static final String TERMINAL_IP = "localhost";

    /**
     * WareNaviシステム定義コントローラ
     */
    private WarenaviSystemController _wsysCtlr;

    /**
     * 終了コード：正常
     */
    private static final int EXIT_CODE_NOMAL = 0;

    /**
     * 終了コード： 作業日が未来
     * 未来の実績が存在しない
     */
    private static final int EXIT_CODE_FUTURE_WORKDAY = -1;

    /**
     * 終了コード：分析パッケージなし
     * 作業日が未来
     * 未来の実績が存在する
     */
    private static final int EXIT_CODE_EXISTRESULT = -2;

    /**
     * 終了コード：分析パッケージあり
     * 作業日が未来
     * 未来の実績が存在する
     */
    private static final int EXIT_CODE_NOCHANGEWORKDAY = -3;

    /**
     * 終了コード：分析パッケージあり
     * 未来の実績が存在した為、作業日変更なし
     */
    private static final int EXIT_CODE_ERR = -4;

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
    public WorkDayAutoUpdate()
    {
        super();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * WMS作業日更新モジュールを起動
     * @param args (未使用)
     */
    public static void main(String[] args)
    {
        // コネクション
        Connection conn = null;
        WorkDayAutoUpdate workDayAutoUpdate = null;
        try
        {
            // 2015/02/16 Y.Okamura start
            // eManagerでSQLログ出力するためにLog4jを使用しているので
            // 以下のコードを消すとWarningが出力されるが動作上問題ないのでコメントアウトする。
            // もしログを落としたい場合は、他のLog4jを使用しているプロセスとかぶらないように出力先ファイルパスを定義すること。
            //DOMConfigurator.configure(WmsParam.LOG4J_FILE_PATH);
            // 2015/02/16 Y.Okamura end

            // コネクション取得
            conn = WmsParam.getConnection();
            System.out.println("**** update WMS work day start!! ****");
            // このクラスのインスタンスを生成
            workDayAutoUpdate = new WorkDayAutoUpdate();

            // workDay:作業日
            String workDay = workDayAutoUpdate.getWorkDay(conn);

            // systemDay:システム日付
            String systemDay = DbDateUtil.getSystemDate();

            // 実績を検索し、未来の実績の有無をチェックします。
            // 実績が存在する場合
            if (workDayAutoUpdate.existResult(conn, workDay))
            {
                // 分析分析パッケージが導入されているかの確認を行います。
                if (workDayAutoUpdate.existAnalysisPack(conn))
                {
                    // 分析パッケージが導入されていて、かつ実績のある日より前に更新しようとしたとき
                    // LOG0084=当日より新しい日付の実績が存在したため、作業日を当日に変更できませんでした。
                    workDayAutoUpdate.setExitCode(EXIT_CODE_NOCHANGEWORKDAY);
                    System.out.println("**** failure!! ****");
                    return;
                }
                // 分析パッケージが導入されていない場合
                else
                {
                    // LOG0083=作業日を当日に変更しました。（当日より新しい日付の実績が存在します)
                    workDayAutoUpdate.setExitCode(EXIT_CODE_EXISTRESULT);
                }

            }
            // 実績が存在しない場合
            else
            {
                // 作業日がシステム日付より進んでいるかをチェックします。
                if (0 > systemDay.compareTo(workDay))
                {
                    // LOG0082=作業日を当日に変更しました。
                    workDayAutoUpdate.setExitCode(EXIT_CODE_FUTURE_WORKDAY);
                }
                // 過去の作業日を当日にした場合は、メッセージを出力なし。
                else
                {
                    workDayAutoUpdate.setExitCode(EXIT_CODE_NOMAL);
                }
            }
            // 作業日の更新を行います。
            workDayAutoUpdate.updateWorkDay(workDay, systemDay, conn);
            // コミット
            conn.commit();
            System.out.println("**** complete!! ****");

        }
        catch (Exception ex)
        {
            System.out.println("**** failure!! ****");
            workDayAutoUpdate.setExitCode(EXIT_CODE_ERR);
            // ロールバック
            try
            {
                conn.rollback();
            }
            catch (SQLException esql)
            {
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
                }
                // システム終了
                System.exit(workDayAutoUpdate.getExitCode());
            }
            catch (SQLException ex)
            {
                workDayAutoUpdate.setExitCode(EXIT_CODE_ERR);
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
     * 未来の実績の有無を確認します。
     * 
     * @param conn コネクション
     * @return 未来の実績あり、ture
     * 未来の実績なし、false
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private boolean existResult(Connection conn, String workDay)
            throws CommonException
    {
        // 未来の実績の有無を確認します。
        if (!getWarenaviSysCtlr(conn).checkResults(DbDateUtil.getSystemDate()))
        {
            return true;
        }
        return false;
    }

    /**
     * 分析パッケージの有無を確認します。
     * 
     * @param conn コネクション
     * @return 分析パッケージあり、ture
     * 分析パッケージなし、false
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private boolean existAnalysisPack(Connection conn)
            throws CommonException
    {
        // 未来の実績の有無を確認します。
        if (getWarenaviSysCtlr(conn).hasAnalysisPack())
        {
            return true;
        }
        return false;
    }

    /**
     * WarenaviSystemControllerを返します。
     * 
     * @return WarenaviSystemControllerを返します。
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    public WarenaviSystemController getWarenaviSysCtlr(Connection conn)
            throws CommonException
    {
        if (null == _wsysCtlr)
        {
            _wsysCtlr = new WarenaviSystemController(conn, getClass());
        }
        return _wsysCtlr;
    }

    /**
     * 作業日の取得を行います。
     * 
     * @param conn コネクション
     * @return 作業日を返却します。
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private String getWorkDay(Connection conn)
            throws CommonException
    {
        // 作業日の取得を行います。
        WarenaviSystemHandler wh = new WarenaviSystemHandler(conn);
        WarenaviSystemSearchKey key = new WarenaviSystemSearchKey();
        key.setSystemNo(SystemDefine.SYSTEM_NO_DEFAULT);

        Entity[] ents = wh.find(key);
        // DNWARENAVISYSTEM has only one record
        if (ents == null || ents.length != 1)
        {
            throw new ScheduleException(WarenaviSystem.STORE_NAME + " record not found or too many records.");
        }

        WarenaviSystem went = (WarenaviSystem)ents[0];

        return went.getWorkDay();
    }

    /**
     * 作業日の更新を行います。
     * 
     * @param workDay 作業日
     * @param systemDay システム日付
     * @param conn コネクション
     * @throws CommonException スケジュール処理内で予期しない例外が発生した場合に通知されます。
     */
    private void updateWorkDay(String workDay, String systemDay, Connection conn)
            throws CommonException
    {
        // 作業日の更新
        // ユーザ情報を設定
        WmsUserInfo wmsUserInfo = new WmsUserInfo();
        // ユーザ端末IP
        wmsUserInfo.setTerminalAddress(TERMINAL_IP);
        // ユーザ名
        wmsUserInfo.setUserId(USER_ID);

        WarenaviSystemHandler wh = new WarenaviSystemHandler(conn);
        WarenaviSystemAlterKey akey = new WarenaviSystemAlterKey();

        akey.setWorkDay(workDay);
        akey.setSystemNo(SYSTEM_NO_DEFAULT);
        akey.updateWorkDay(systemDay);

        wh.modify(akey);

        // eManagerの作業日(ログイン成功画面での表示用)を更新
        getWarenaviSysCtlr(conn).updateWorkDate(systemDay, wmsUserInfo);
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
        return "$Id: WorkDayAutoUpdate.java 3266 2009-03-09 05:09:11Z okamura $";
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
