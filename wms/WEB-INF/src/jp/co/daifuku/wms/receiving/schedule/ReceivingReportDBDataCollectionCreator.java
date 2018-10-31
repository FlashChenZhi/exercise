// $Id: ReceivingReportDBDataCollectionCreator.java 7735 2010-03-26 06:22:49Z okayama $
package jp.co.daifuku.wms.receiving.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.EWNToHostHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.entity.EWNToHost;
import jp.co.daifuku.wms.base.entity.ReceivingHostSend;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.ReportReceiving;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * <BR>
 * A class to report receiving result data.<BR>
 * The report unit is planned unit (collective)<BR>
 * 
 * <BR>
 * Designer : nakai <BR>
 * Maker : nakai <BR>
 * @version $Revision: 7735 $, $Date: 2010-03-26 15:22:49 +0900 (金, 26 3 2010) $
 * @author  Last commit: $Author: okayama $
 */
public class ReceivingReportDBDataCollectionCreator
        extends ReceivingReportDBDataCreator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * log output use
     */
    private static final String BUSINESS_NAME = "business";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private EWNToHostHandler _EWNToHostHandler = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * @param conn DB connection<BR>
     */
    public ReceivingReportDBDataCollectionCreator(Connection conn, Connection customerConn)
    {
        super(conn, customerConn);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB
    }

    /**
     * @param conn database Connection<BR>
     * @param caller 呼び出し元クラス
     */
    public ReceivingReportDBDataCollectionCreator(Connection conn, Connection customerConn, Class caller)
    {
        super(conn, customerConn, caller);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * Creates reports of receiving result planned unit (collective) data.<BR>
     * @return boolean True if succeeds in create receiving report data.  Otherwise false.<BR>
     */
    public boolean report()
    {
        // ローカルファイル削除フラグ
        boolean deleteFile = false;

        // 報告データエンティティを指定してファイルハンドラ作成
        ReportReceiving rReceivingEntity = new ReportReceiving();
        FileHandler handler = AbstractFileHandler.getInstance(rReceivingEntity);

        // Initializes the number of report data
        setReportCount(0);

        // Creates Finder to pick out report data from receiving plan information
        ReceivingPlanFinder sFinder = new ReceivingPlanFinder(getConnection());

        try
        {
            // Gets environmental information of report data files
            acquireFileInfo(ReceivingInParameter.DATA_TYPE_RECEIVE);

            // Creates conditions to pick out report data from receiving plan information by the unit (collective)
            ReceivingPlanSearchKey planSKey = new ReceivingPlanSearchKey();
            setSearchKey(planSKey);

            sFinder.open(true);

            // Searches  receiving plan information
            if (sFinder.search(planSKey) <= 0)
            {
                // 6003011 = "Target data was not found."
                setMessage("6003011");
                return true;
            }

            boolean flag = false;

            while (sFinder.hasNext())
            {
                // Gets search results by 100, and outputs them into a file
                ReceivingPlan[] receivingPlan = (ReceivingPlan[])sFinder.getEntities(100);

                for (ReceivingPlan sPlan : receivingPlan)
                {
                    if (getReportCount() == 0)
                    {
                        try
                        {
                            // Opens a file when the first output happens
                            // Suresh.K Modified
                            // Generates a name of a result file（Creates it in a temp file store folder)
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                            String sysTime = df.format(new java.util.Date(System.currentTimeMillis()));

                            // Sets a file name
                            setResultFileName(getFileName() + sysTime + getExtention());
                            // Writes it in a temp file store folder
                            // If there exists no the folder, then creats it.
                            prepareDir(WmsParam.HOSTDATA_LOCAL_PATH + getResultFileName());
                            handler.open(WmsParam.HOSTDATA_LOCAL_PATH, getResultFileName());
                            // ファイルを作成したのでエラー発生時には削除
                            deleteFile = true;
                        }
                        catch (ReadWriteException e)
                        {
                            // 6003019 = "Selected folder is invalid."
                            setMessage("6003019");
                            return false;

                        }
                        handler.clear();
                    }
                    // Outputs data to a receiving result report file
                    if (csvWrite(handler, rReceivingEntity, sPlan))
                    {
                        setReportCount(getReportCount() + 1);

                        // Updates Receiving result send information type to  "reported"
                        ReceivingHostSendAlterKey hstAKey = new ReceivingHostSendAlterKey();
                        hstAKey.setPlanUkey(sPlan.getPlanUkey());
                        updateHostSendReportFlag(hstAKey);

                        // Updates Receiving plan information  result report type to "reported"
                        ReceivingPlanAlterKey planAKey = new ReceivingPlanAlterKey();
                        planAKey.setPlanUkey(sPlan.getPlanUkey());
                        updateReceivingPlanReportFlag(planAKey);
                        setMessage("6001009");

                        flag = true;

                        //rReceivingEntity now has the host send stuff mapped to entity fields, let's use it again
                        String rec = handler.getRecordFormatter().format(rReceivingEntity);
                        if (!rec.equals(""))
                        {
                            EWNToHost entity = new EWNToHost();

                            // MESSAGE_DATE
                            entity.setMessageDate(new SysDate());
                            // SEQUENCE_NO
                            entity.setSequenceNo(getNextInReportSequence());
                            // MESSAGE_ID
                            entity.setMessageId(ReceivingInParameter.DATA_TYPE_RECEIVE);
                            // message DATA
                            entity.setData(rec);

                            _EWNToHostHandler.create(entity);
                            getCustomerConnection().commit();
                        }
                    }
                }
            }

            String className = getCaller().getName().toLowerCase();
            if (flag && 0 > className.indexOf(BUSINESS_NAME))
            {
                log_write(getConnection(), EmConstants.OPELOG_CLASS_AUTO_REPORT);
            }

            // 処理が正常に完了したのでファイルは削除しない
            deleteFile = false;
        }
        catch (ReadWriteException e)
        {
            // 6007002 = "Database error occurred. See log."
            setMessage("6007002");
            return false;
        }
        catch (NotFoundException e)
        {
            // 6007002 = "Database error occurred. See log."
            setMessage("6007002");
            return false;
        }
        //for writing to customer's DB
        catch (DataExistsException e)
        {
            //6020043=Skipped. Target data being processed by another terminal.  File:{0} Line:{1}
            setMessage("6020043");
            return false;
        }
        catch (ScheduleException e)
        {
            // 6027009 = "Unexpected error occurred. Check the log."
            setMessage("6027009");
            return false;
        }
        catch (SQLException e)
        {
            // 6007002=A database error occurred.  Please check the log.
            setMessage("6007002");
        }
        finally
        {
            sFinder.close();

            if (handler.isOpen())
            {
                handler.close();
            }

            // ファイル作成後にExceptionが発生した場合など、作成したファイルを削除する
            if (deleteFile)
            {
                deleteFile(new File(WmsParam.HOSTDATA_LOCAL_PATH, getResultFileName()));
            }
        }
        return true;
    }

    /**
     * WHERE句の作成
     * 
     * @param planSKey
     */
    protected void setSearchKey(ReceivingPlanSearchKey planSKey)
    {
        // 取得項目
        planSKey.setPlanUkeyCollect("MAX");
        planSKey.setPlanDayCollect();
        planSKey.setSupplierCodeCollect();
        planSKey.setCollect(ReceivingHostSend.SUPPLIER_NAME, "MAX", ReceivingHostSend.SUPPLIER_NAME);
        planSKey.setReceiveTicketNoCollect();
        planSKey.setReceiveLineNoCollect();
        planSKey.setItemCodeCollect("MAX");
        planSKey.setCollect(ReceivingHostSend.ITEM_NAME, "MAX", ReceivingHostSend.ITEM_NAME);
        planSKey.setCollect(ReceivingHostSend.JAN, "MAX", ReceivingHostSend.JAN);
        planSKey.setCollect(ReceivingHostSend.ITF, "MAX", ReceivingHostSend.ITF);
        planSKey.setCollect(ReceivingHostSend.ENTERING_QTY, "MAX", ReceivingHostSend.ENTERING_QTY);
        planSKey.setPlanLotNoCollect("MAX");
        planSKey.setPlanQtyCollect("MAX");
        planSKey.setResultQtyCollect("MAX");
        planSKey.setWorkDayCollect("MAX");

        // 検索条件
        // 状態フラグ = 完了 AND 実績報告区分 = 未送信
        planSKey.setStatusFlag(ReceivingPlan.STATUS_FLAG_COMPLETION);
        planSKey.setReportFlag(ReceivingPlan.REPORT_FLAG_NOT_REPORT);
        planSKey.setJoin(ReceivingPlan.PLAN_UKEY, ReceivingHostSend.PLAN_UKEY);

        // 集約条件
        planSKey.setPlanDayGroup();
        planSKey.setSupplierCodeGroup();
        planSKey.setReceiveTicketNoGroup();
        planSKey.setReceiveLineNoGroup();

        // 検索順
        // Order: Planned date > Receiving Ticket No. > Receiving Line No.
        planSKey.setPlanDayOrder(true);
        planSKey.setSupplierCodeOrder(true);
        planSKey.setReceiveTicketNoOrder(true);
        planSKey.setReceiveLineNoOrder(true);
    }

    /**
     * Creates a data report result file.<BR>
     * Gets environment information fo a report data file, and creates a data report result file.<BR>
     * Actural creating process is done by a method, <CODE>createResultReportFile()</CODE>, in <CODE>AbstractReportDBDataCreator</CODE> class.
     * @return True when the process is completed correctly. Otherwise false.
     * @throws IOException Thrown when an I/O exception occcurs<BR>
     * @throws ReadWriteException Thrown when an error in DB access  occcurs<BR>
     */
    public boolean sendReportFile()
            throws IOException,
                ReadWriteException
    {
        // Gets environmental information of report data files
        try
        {
            acquireFileInfo(ReceivingInParameter.DATA_TYPE_RECEIVE);
        }
        catch (ScheduleException e)
        {
            return false;
        }
        return super.sendReportFile();
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
     * Sets outputs of receiving result reports to rReceivingEntity (ReportReceiving class), and outputs them to a receiving result report CSV file.
     * @param handler File Handler<BR>
     * @param rReceivingEntity Output Entity<BR>
     * @param receivingPlan Receiving plan information entity<BR>
     * @param hostSend Receiving result sending information entity<BR>
     * @return boolean True when CSV file output is successful. Otherwise false.<BR>
     * @throws ReadWriteException Thrown when an error in DB access  occcurs<BR>
     */
    protected boolean csvWrite(FileHandler handler, ReportReceiving rReceivingEntity, ReceivingPlan receivingPlan)
            throws ReadWriteException
    {
        // Edits outputs of a receiving result report CSV file
        rReceivingEntity.setValue(ReportReceiving.CANCEL_FLAG, SystemDefine.CANCEL_FLAG_NORMAL);
        rReceivingEntity.setValue(ReportReceiving.PLAN_DAY, receivingPlan.getPlanDay());
        rReceivingEntity.setValue(ReportReceiving.SUPPLIER_CODE, receivingPlan.getSupplierCode());
        rReceivingEntity.setValue(ReportReceiving.SUPPLIER_NAME, receivingPlan.getValue(
                ReceivingHostSend.SUPPLIER_NAME, ""));
        rReceivingEntity.setValue(ReportReceiving.RECEIVE_TICKET_NO, receivingPlan.getReceiveTicketNo());
        rReceivingEntity.setValue(ReportReceiving.RECEIVE_LINE_NO, new BigDecimal(receivingPlan.getReceiveLineNo()));
        rReceivingEntity.setValue(ReportReceiving.ITEM_CODE, receivingPlan.getItemCode());
        rReceivingEntity.setValue(ReportReceiving.ITEM_NAME, receivingPlan.getValue(ReceivingHostSend.ITEM_NAME, ""));
        rReceivingEntity.setValue(ReportReceiving.JAN, receivingPlan.getValue(ReceivingHostSend.JAN, ""));
        rReceivingEntity.setValue(ReportReceiving.ITF, receivingPlan.getValue(ReceivingHostSend.ITF, ""));
        rReceivingEntity.setValue(ReportReceiving.ENTERING_QTY,
                receivingPlan.getBigDecimal(ReceivingHostSend.ENTERING_QTY));
        rReceivingEntity.setValue(ReportReceiving.PLAN_LOT_NO, receivingPlan.getPlanLotNo());
        rReceivingEntity.setValue(ReportReceiving.PLAN_QTY, new BigDecimal(receivingPlan.getPlanQty()));
        rReceivingEntity.setValue(ReportReceiving.RESULT_QTY, new BigDecimal(receivingPlan.getResultQty()));
        rReceivingEntity.setValue(ReportReceiving.WORK_DAY, receivingPlan.getWorkDay());

        // Sets "" to detail parts if a report unit is a plan unit (collective)
        rReceivingEntity.setValue(ReportReceiving.RESULT_LOT_NO, "");
        rReceivingEntity.setValue(ReportReceiving.USER_ID, "");
        rReceivingEntity.setValue(ReportReceiving.TERMINAL_NO, "");

        // Outputs them to a CSV file
        handler.lock();
        handler.create(rReceivingEntity);
        handler.unLock();

        return true;
    }

    /**
     * Write operation log information <BR>
     * @param  conn DB Connection
     * @param  operationKind 操作区分
     * @throws ReadWriteException データベースエラーが発生した場合にスローされます。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     * @throws SQLException SQLでエラーが発生した場合に通知されます。
     */
    protected void log_write(Connection conn, int operationKind)
            throws ReadWriteException,
                ScheduleException,
                SQLException
    {
        DfkUserInfo user = new DfkUserInfo();

        // DS番号
        user.setDsNumber(DsNumberDefine.DS_AUTOREPORT);
        // ユーザID
        user.setUserId(WmsParam.SYS_USER_ID);
        // ユーザ名称
        user.setUserName(WmsParam.SYS_USER_NAME);
        // 端末No.
        user.setTerminalNumber(WmsParam.SYS_TERMINAL_NO);
        // 端末名称
        user.setTerminalName(WmsParam.SYS_TERMINAL_NAME);
        // IPアドレス
        user.setTerminalAddress(WmsParam.SYS_IP_ADDRESS);
        // 画面名称
        user.setPageNameResourceKey(DsNumberDefine.PAGERESOUCE_AUTOREPORT);

        // オペレーションログ出力
        List<String> itemLog = new ArrayList<String>();

        // データ区分
        itemLog.add(ReceivingInParameter.DATA_TYPE_RECEIVE);

        // ログ出力
        P11LogWriter opeLogWriter = new P11LogWriter(conn);
        opeLogWriter.createOperationLog(user, operationKind, itemLog);
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * Returns a revision of this class.
     * @return String　of a revision
     */
    public static String getVersion()
    {
        return "$Id: ReceivingReportDBDataCollectionCreator.java 7735 2010-03-26 06:22:49Z okayama $";
    }
}
