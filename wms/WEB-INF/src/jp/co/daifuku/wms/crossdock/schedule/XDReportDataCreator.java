// $Id: XDReportDataCreator.java 7506 2010-03-12 06:44:21Z shibamoto $
package jp.co.daifuku.wms.crossdock.schedule;

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
import jp.co.daifuku.wms.base.common.EnvironmentInfoDefine;
import jp.co.daifuku.wms.base.common.ReportDataCreator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.SortHostSendHandler;

/**
 * TC実績データ報告処理クラスです。<BR>
 * <BR>
 * Designer : M.Itokawa <BR>
 * Maker : M.Itokawa
 * @version $Revision: 7506 $, $Date: 2010-03-12 15:44:21 +0900 (金, 12 3 2010) $
 * @author  Last commit: $Author: shibamoto $
 */
public class XDReportDataCreator
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
    public XDReportDataCreator()
    {
        this(null);
    }

    /**
     * @param conn データベースConnection<BR>
     */
    public XDReportDataCreator(Connection conn)
    {
        super(conn);
    }

    /**
     * @param conn データベースConnection<BR>
     * @param caller 呼び出し元クラス
     */
    public XDReportDataCreator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * TC実績データ報告処理を行います。<BR>
     * @return boolean 入庫報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        return false;
    }

    /**
     * TC報告データを作成するクラスを生成し通知します。<BR>
     * @return ReportDataCreator 入庫報告データ作成クラス<BR>
     */
    public ReportDataCreator getReportClass()
    {
        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(XDInParameter.DATA_TYPE_CROSSDOCK);

            // 予定単位(明細)の場合
            if (EnvironmentInfoDefine.COLLECT_CONDITION_DETAIL.equals(getReportType()))
            {
                return new XDReportDataDetailCreator(getConnection(),
						XDReportDataDetailCreator.class);
            }
            return null;
        }
        catch (ScheduleException e)
        {
            // 6027009=予期しないエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6027009));
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
        // DNSortHostSend(仕分実績送信情報)の統計情報を取得します。
        SortHostSendHandler hostSendHandler = new SortHostSendHandler(getConnection());
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
        return "$Id: XDReportDataCreator.java 7506 2010-03-12 06:44:21Z shibamoto $";
    }
}
