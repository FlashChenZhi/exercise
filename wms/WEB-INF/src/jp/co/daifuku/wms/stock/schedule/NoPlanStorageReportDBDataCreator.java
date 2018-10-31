// $Id: NoPlanStorageReportDBDataCreator.java 7735 2010-03-26 06:22:49Z okayama $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.AbstractReportDBDataCreator;
import jp.co.daifuku.wms.base.common.ReportDBDataCreator;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;

/**
 * このクラスは予定外入庫の報告内容を作成するクラスです。<br>
 * このクラスは報告データファイルの環境情報を取得し、予定外入庫
 * の作業単位報告クラスを返します。
 * Designer : suresh <BR>
 * Maker : suresh <BR>
 * @version $Revision: 7735 $, $Date: 2010-03-26 15:22:49 +0900 (金, 26 3 2010) $
 * @author  Last commit: $Author: okayama $
 */
public class NoPlanStorageReportDBDataCreator
        extends AbstractReportDBDataCreator
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
     * デフォルトコンストラクタ
     */
    public NoPlanStorageReportDBDataCreator()
    {
        this(null, null);
    }

    /**
     * 1.コンストラクタ<BR>
     * インスタンス変数と処理結果のメッセージを初期化します。<BR>
     * @param  conn データベースとのコネクションオブジェクト
     */
    public NoPlanStorageReportDBDataCreator(Connection conn, Connection customerConn)
    {
        // コネクションセット
        super(conn, customerConn);
        // メッセージの初期化
        setMessage(null);
    }

    /**
     * 1.コンストラクタ<BR>
     * インスタンス変数と処理結果のメッセージを初期化します。<BR>
     * @param  conn データベースとのコネクションオブジェクト
     */
    public NoPlanStorageReportDBDataCreator(Connection conn, Connection customerConn, Class caller)
    {
        // コネクションセット
        super(conn, customerConn, caller);
        // メッセージの初期化
        setMessage(null);
    }

    /**
     * 統計情報の取得を行います。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public void statics()
            throws CommonException
    {
        // 取得するテーブルのハンドラーを生成して下さい。
        // DNHostSend(入出庫実績送信情報)の統計情報を取得します。
        HostSendHandler hostSendHandler = new HostSendHandler(getConnection());
        hostSendHandler.getStatics();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 予定外入庫報告データを作成するクラスを生成し通知します。<BR>
     * @return ReportDBDataCreator 予定外入庫報告データ作成クラス<BR>
     */
    public ReportDBDataCreator getReportClass()
    {
        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireFileInfo(StockInParameter.DATA_TYPE_NOPLAN_STORAGE);
            //作業単位報告クラスを返す。
            return new NoPlanStorageReportDBDataWorkCreator(getConnection(), getCustomerConnection(),
                    NoPlanStorageReportDBDataWorkCreator.class);
        }
        catch (ScheduleException e)
        {
            // 6027009=予期しないエラーが発生しました。ログを参照してください。
            setMessage("6027009");
            return null;
        }
    }

    /**
     * 予定外入庫実績データ報告処理を行います。<BR>
     * @return boolean 予定外入庫報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        return false;
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
        return "$Id: NoPlanStorageReportDBDataCreator.java 7735 2010-03-26 06:22:49Z okayama $";
    }
}
