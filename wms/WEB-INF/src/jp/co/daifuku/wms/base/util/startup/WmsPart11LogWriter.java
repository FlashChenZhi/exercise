// $Id: WmsPart11LogWriter.java 8083 2015-02-16 07:38:16Z okamura $
package jp.co.daifuku.wms.base.util.startup;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.WmsParam;


/**
 * eWareNavi終了処理<br>
 * Part11対応でデータベースのバックアップログを出力します。<br>
 *
 * @version $Revision: 8083 $, $Date: 2015-02-16 16:38:16 +0900 (月, 16 2 2015) $
 * @author  073064
 * @author  Last commit: $Author: okamura $
 */


public class WmsPart11LogWriter
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * クラス名
     */
    private static final String CLASS_NAME = "WmsPart11LogWriter";

    /**
     * 終了コード：正常
     */
    private static final int EXIT_CODE_NOMAL = 0;

    /**
     * 終了コード：Part11ログ書込み失敗
     */
    private static final int EXIT_CODE_PART11 = -5;

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
     * default constructor.
     */
    public WmsPart11LogWriter()
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
        WmsPart11LogWriter p11Writer = null;
        try
        {
            // 2015/02/16 Y.Okamura start
            // eManagerでSQLログ出力するためにLog4jを使用しているので
            // 以下のコードを消すとWarningが出力されるが動作上問題ないのでコメントアウトする。
            // もしログを落としたい場合は、他のLog4jを使用しているプロセスとかぶらないように出力先ファイルパスを定義すること。
            //DOMConfigurator.configure(WmsParam.LOG4J_FILE_PATH);
            // 2015/02/16 Y.Okamura end

            // PART11 Packageなし時、処理を行いません。
            if (!P11Config.isPart11Log())
            {
                return;
            }

            // コネクション取得
            conn = WmsParam.getConnection();
            System.out.println("**** WmsPart11Log start!! ****");
            // このクラスのインスタンスを生成
            p11Writer = new WmsPart11LogWriter();

            // ユーザ情報をセット
            DsNumberDefine ds = new DsNumberDefine();
            DfkUserInfo dfkUser = new DfkUserInfo();

            // デフォルトユーザ情報
            ds.setDefaultUserInfo(dfkUser);
            // DS番号
            dfkUser.setDsNumber(DsNumberDefine.DS_SYSTEMBACKUP);
            // ページリソースキー
            dfkUser.setPageNameResourceKey(DsNumberDefine.PAGERESOUCE_SYSTEMBACKUP);

            // ログ項目
            List<String> itemLog = new ArrayList<String>();

            // 操作ログに出力する
            P11LogWriter opeLogWriter = new P11LogWriter(conn);
            opeLogWriter.createOperationLog(dfkUser, EmConstants.OPELOG_CLASS_SETTING, itemLog);

            conn.commit();
            p11Writer.setExitCode(EXIT_CODE_NOMAL);

            System.out.println("**** complete!! ****");
        }
        catch (Exception ex)
        {
            p11Writer.setExitCode(EXIT_CODE_PART11);
            System.out.println("**** failure!! ****");
            // 6006002=データベースエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006002, ex), CLASS_NAME);
            // ロールバック
            try
            {
                conn.rollback();
            }
            catch (SQLException esql)
            {
                // エラーをログファイルに落とす
                // 6006002=データベースエラーが発生しました。{0}
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
                    System.exit(p11Writer.getExitCode());
                }
            }
            catch (SQLException ex)
            {
                // エラーをログファイルに落とす
                // 6006002=データベースエラーが発生しました。{0}
                RmiMsgLogClient.write(new TraceHandler(6006002, ex), CLASS_NAME);
                p11Writer.setExitCode(EXIT_CODE_PART11);
            }
        }
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
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: WmsPart11LogWriter.java 8083 2015-02-16 07:38:16Z okamura $";
    }
}
