// $Id: ReceivingReportDataCreator.java 7506 2010-03-12 06:44:21Z shibamoto $
package jp.co.daifuku.wms.receiving.schedule;

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
import jp.co.daifuku.wms.base.common.AbstractReportDataCreator;
import jp.co.daifuku.wms.base.common.EnvironmentInfoDefine;
import jp.co.daifuku.wms.base.common.ReportDataCreator;
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanHandler;
import jp.co.daifuku.wms.base.entity.ReceivingHostSend;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;

/**
 * A class to report receiving result data.
 * 
 * Designer : nakai
 * Maker : nakai
 * @version $Revision: 7506 $, $Date: 2010-03-12 15:44:21 +0900 (金, 12 3 2010) $
 * @author  Last commit: $Author: shibamoto $
 */
public class ReceivingReportDataCreator
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
     * Default constructor
     */
    public ReceivingReportDataCreator()
    {
        this(null);
    }

    /**
     * Constructor
     * @param conn DB connection<BR>
     */
    public ReceivingReportDataCreator(Connection conn)
    {
        super(conn);
    }

    /**
     * @param conn データベースConnection<BR>
     * @param caller 呼び出し元クラス
     */
    public ReceivingReportDataCreator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * Reports receiving result data.<BR>
     * @return boolean True if a creation of receiving report data is successful.  Otherwise false.<BR.
     */
    public boolean report()
    {
        return false;
    }

    /**
     * Generates a class that creates receiving report data and returns it.<BR>
     * @return ReportDataCreator Class to create receiving report data<BR>
     */
    public ReportDataCreator getReportClass()
    {
        try
        {
            // Gets environment information of a report data file
            acquireExchangeEnvironment(ReceivingInParameter.DATA_TYPE_RECEIVE);

            // Checks if Report Unit is Plan Unit (Collective) or not.
            if (EnvironmentInfoDefine.COLLECT_CONDITION_PLAN.equals(getReportType()))
            {
                return new ReceivingReportDataCollectionCreator(getConnection(),
						ReceivingReportDataCollectionCreator.class);
            }
            // Checks if Report Unit is Plan Unit (Detail) or not.
            else if (EnvironmentInfoDefine.COLLECT_CONDITION_DETAIL.equals(getReportType()))
            {
                return new ReceivingReportDataDetailCreator(getConnection(),
						ReceivingReportDataDetailCreator.class);
            }
            // Checks if Report Unit is Work Unit or not.
            else if (EnvironmentInfoDefine.COLLECT_CONDITION_WORK.equals(getReportType()))
            {
                return new ReceivingReportDataWorkCreator(getConnection(),
						ReceivingReportDataWorkCreator.class);
            }
            return null;
        }
        catch (ScheduleException e)
        {
            // 6027009 = "Unexpected error occurred. Check the log."
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
        // DNReceivingHostSend(入荷実績送信情報)の統計情報を取得します。
        ReceivingHostSendHandler hostSendHandler = new ReceivingHostSendHandler(getConnection());
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
     * Updates a result report type of receiving plan information to "reported".<BR>
     * @param altKey <BR>
     * @throws NotFoundException Thrown when there is no informtion<BR>
     * @throws ReadWriteException thrown when a DB error occurs<BR>
     */
    protected void updateReceivingPlanReportFlag(ReceivingPlanAlterKey altKey)
            throws NotFoundException,
                ReadWriteException
    {
        // Sets  a result report type "reported"
        altKey.updateReportFlag(ReceivingPlan.REPORT_FLAG_REPORT);
        // 最終更新処理名をセット
        altKey.updateLastUpdatePname(getCallerName());
        ReceivingPlanHandler handler = new ReceivingPlanHandler(getConnection());
        handler.modify(altKey);

        return;
    }

    /**
     * Updates a result report type of receiving result send information.<BR>
     * @param altKey updateKey fro receiving result send information.<BR>
     * @throws NotFoundException Thrown when there is no informtion<br>
     * @throws ReadWriteException thrown when a DB error occurs<br>
     * glm Overloads method from super class
     */
    protected void updateHostSendReportFlag(ReceivingHostSendAlterKey altKey)
            throws NotFoundException,
                ReadWriteException
    {
        // Sets  a result report type "reported"
        altKey.updateReportFlag(ReceivingHostSend.REPORT_FLAG_REPORT);
        // 最終更新処理名をセット
        altKey.updateLastUpdatePname(getCallerName());
        ReceivingHostSendHandler handler = new ReceivingHostSendHandler(getConnection());
        handler.modify(altKey);

        return;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns a revision of this class
     * @return String of a revision
     */
    public static String getVersion()
    {
        return "$Id: ReceivingReportDataCreator.java 7506 2010-03-12 06:44:21Z shibamoto $";
    }
}
