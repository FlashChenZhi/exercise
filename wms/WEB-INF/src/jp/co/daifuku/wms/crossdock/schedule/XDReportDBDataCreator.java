// $Id: XDReportDBDataCreator.java 7733 2010-03-26 06:21:56Z okayama $
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
import jp.co.daifuku.wms.base.common.AbstractReportDBDataCreator;
import jp.co.daifuku.wms.base.common.EnvironmentInfoDefine;
import jp.co.daifuku.wms.base.common.ReportDBDataCreator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.SortHostSendHandler;

/**
 * <BR>
 * TC実績データ報告処理クラスです。<br>
 * <BR>
 * Designer : M.Itokawa <BR>
 * Maker : M.Itokawa <BR>
 * @version $Revision: 7733 $, $Date: 2010-03-26 15:21:56 +0900 (金, 26 3 2010) $
 * @author  Last commit: $Author: okayama $
 */

public class XDReportDBDataCreator
        extends AbstractReportDBDataCreator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルトコンストラクタ
     */
    public XDReportDBDataCreator()
    {
        this(null, null);
    }

    /**
     * @param conn データベースConnection<BR>
     */
    public XDReportDBDataCreator(Connection conn, Connection customerConn)
    {
        super(conn, customerConn);
    }

    /**
     * @param conn データベースConnection<BR>
     * @param caller 呼び出し元クラス
     */
    public XDReportDBDataCreator(Connection conn, Connection customerConn, Class caller)
    {
        super(conn, customerConn, caller);
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
     * @return ReportDBDataCreator 入庫報告データ作成クラス<BR>
     */
    public ReportDBDataCreator getReportClass()
    {
        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireFileInfo(XDInParameter.DATA_TYPE_CROSSDOCK);

            // 報告単位は予定単位（明細）か？
            if (getReportType().equals(EnvironmentInfoDefine.COLLECT_CONDITION_DETAIL))
            {
                return new XDReportDBDataDetailCreator(getConnection(), getCustomerConnection(),
                        XDReportDBDataDetailCreator.class);
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
        return "$Id: XDReportDBDataCreator.java 7733 2010-03-26 06:21:56Z okayama $";
    }
}
