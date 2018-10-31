// $Id: RetrievalReportDBDataCreator.java 7735 2010-03-26 06:22:49Z okayama $
package jp.co.daifuku.wms.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.AbstractReportDBDataCreator;
import jp.co.daifuku.wms.base.common.EnvironmentInfoDefine;
import jp.co.daifuku.wms.base.common.ReportDBDataCreator;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;

/**
 * <BR>
 * 出庫実績データ報告処理クラスです。<BR>
 * <BR>
 * Designer : nakai <BR>
 * Maker : nakai <BR>
 * @version $Revision: 7735 $, $Date: 2010-03-26 15:22:49 +0900 (金, 26 3 2010) $
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalReportDBDataCreator
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
    private RetrievalPlanHandler _RtrvPlanHandler = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * デフォルトコンストラクタ
     */
    public RetrievalReportDBDataCreator()
    {
        this(null, null);
    }

    /**
     * @param conn データベースConnection<BR>
     */
    public RetrievalReportDBDataCreator(Connection conn, Connection customerConn)
    {
        super(conn, customerConn);
        _RtrvPlanHandler = new RetrievalPlanHandler(getConnection());
    }

    /**
     * @param conn データベースConnection<BR>
     * @param caller 呼び出し元クラス
     */
    public RetrievalReportDBDataCreator(Connection conn, Connection customerConn, Class caller)
    {
        super(conn, customerConn, caller);
        _RtrvPlanHandler = new RetrievalPlanHandler(getConnection());
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 出庫実績データ報告処理を行います。<BR>
     * @return boolean 出庫報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        return false;
    }

    /**
     * 出庫報告データを作成するクラスを生成し通知します。<BR>
     * @return ReportDBDataCreator 出庫報告データ作成クラス<BR>
     */
    public ReportDBDataCreator getReportClass()
    {
        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireFileInfo(RetrievalInParameter.DATA_TYPE_RETRIEVAL);

            // 報告単位は予定単位（集約）か？
            if (getReportType().equals(EnvironmentInfoDefine.COLLECT_CONDITION_PLAN))
            {
                return new RetrievalReportDBDataCollectionCreator(getConnection(), getCustomerConnection(),
                        RetrievalReportDBDataCollectionCreator.class);
            }
            else
            {
                // 報告単位は予定単位（明細）か？
                if (getReportType().equals(EnvironmentInfoDefine.COLLECT_CONDITION_DETAIL))
                {
                    return new RetrievalReportDBDataDetailCreator(getConnection(), getCustomerConnection(),
                            RetrievalReportDBDataDetailCreator.class);
                }
                else
                {
                    // 報告単位は作業単位か？
                    if (getReportType().equals(EnvironmentInfoDefine.COLLECT_CONDITION_WORK))
                    {
                        return new RetrievalReportDBDataWorkCreator(getConnection(), getCustomerConnection(),
                                RetrievalReportDBDataWorkCreator.class);
                    }
                }
            }

            return null;
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
    /**
     * 出庫予定情報の実績報告区分を送信済みに更新します。<BR>
     * @param altKey <BR>
     * @throws NotFoundException 情報が無かった場合に通知されます。<BR>
     * @throws ReadWriteException データベースエラーが発生した場合に通知されます。<BR>
     */
    protected void updateRetrievalPlanReportFlag(RetrievalPlanAlterKey altKey)
            throws NotFoundException,
                ReadWriteException
    {
        // 実績報告区分に報告済みをセットします。
        altKey.updateReportFlag(RetrievalPlan.REPORT_FLAG_REPORT);
        // 最終更新処理名をセットします。
        altKey.updateLastUpdatePname(getCallerName());
        _RtrvPlanHandler.modify(altKey);

        return;
    }

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
        return "$Id: RetrievalReportDBDataCreator.java 7735 2010-03-26 06:22:49Z okayama $";
    }
}
