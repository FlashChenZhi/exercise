// $Id: NoPlanStorageReportDataCreator.java 7506 2010-03-12 06:44:21Z shibamoto $
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
import jp.co.daifuku.wms.base.common.AbstractReportDataCreator;
import jp.co.daifuku.wms.base.common.ReportDataCreator;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;

/**
 * このクラスは予定外入庫の報告内容を作成するクラスです。<BR>
 * このクラスは報告データファイルの環境情報を取得し、予定外入庫<BR>
 * の作業単位報告クラスを返します。<BR>
 * <BR>
 * Designer : suresh <BR>
 * Maker : suresh
 * @version $Revision: 7506 $, $Date: 2010-03-12 15:44:21 +0900 (金, 12 3 2010) $
 * @author  Last commit: $Author: shibamoto $
 */
public class NoPlanStorageReportDataCreator
        extends AbstractReportDataCreator
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
    public NoPlanStorageReportDataCreator()
    {
        this(null);
    }

    /**
     * 1.コンストラクタ<BR>
     * インスタンス変数と処理結果のメッセージを初期化します。<BR>
     * @param  conn データベースとのコネクションオブジェクト
     */
    public NoPlanStorageReportDataCreator(Connection conn)
    {
        // コネクションセット
        super(conn);
        // メッセージの初期化
        setMessage(null);
    }

    /**
     * 1.コンストラクタ<BR>
     * インスタンス変数と処理結果のメッセージを初期化します。<BR>
     * @param conn データベースConnection<BR>
     * @param caller 呼び出し元クラス
     */
    public NoPlanStorageReportDataCreator(Connection conn, Class caller)
    {
        super(conn, caller);
        // メッセージの初期化
        setMessage(null);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 予定外入庫実績データ報告処理を行います。<BR>
     * @return boolean 予定外入庫報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        return false;
    }

    /**
     * 予定外入庫報告データを作成するクラスを生成し通知します。<BR>
     * @return ReportDataCreator 予定外入庫報告データ作成クラス<BR>
     */
    public ReportDataCreator getReportClass()
    {
        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(StockInParameter.DATA_TYPE_NOPLAN_STORAGE);
            //作業単位報告クラスを返す。
            return new NoPlanStorageReportDataWorkCreator(getConnection(),
					NoPlanStorageReportDataWorkCreator.class);
        }
        catch (ScheduleException e)
        {
            // 6027009=予期しないエラーが発生しました。ログを参照してください。
            setMessage("6027009");
            return null;
        }
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
        return "$Id: NoPlanStorageReportDataCreator.java 7506 2010-03-12 06:44:21Z shibamoto $";
    }
}
