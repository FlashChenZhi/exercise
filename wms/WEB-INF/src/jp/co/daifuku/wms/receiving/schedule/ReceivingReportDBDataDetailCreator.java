// $Id: ReceivingReportDBDataDetailCreator.java 7735 2010-03-26 06:22:49Z okayama $
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
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanAlterKey;
import jp.co.daifuku.wms.base.entity.EWNToHost;
import jp.co.daifuku.wms.base.entity.ReceivingHostSend;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.ReportReceiving;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * <BR>
 * A class to report receiving result data.<BR>
 * Report Unit is Plan Unit (Detail).<BR>
 * <BR>
 * Designer : nakai <BR>
 * Maker : nakai <BR>
 * @version $Revision: 7735 $, $Date: 2010-03-26 15:22:49 +0900 (金, 26 3 2010) $
 * @author  Last commit: $Author: okayama $
 */

public class ReceivingReportDBDataDetailCreator
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
    public ReceivingReportDBDataDetailCreator(Connection conn, Connection customerConn)
    {
        super(conn, customerConn);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB
    }

    /**
     * @param conn  DB Connection<BR>
     * @param caller 呼び出し元クラス
     */
    public ReceivingReportDBDataDetailCreator(Connection conn, Connection customerConn, Class caller)
    {
        super(conn, customerConn, caller);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * Creates data reports of receiving result by the plan unit (Detail)<BR>
     * @return boolean True when a creation of receiving report data is successful.  Otherwise false.<BR>
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

        // Creates Finder to pick out report data from  result send information
        ReceivingHostSendFinder hFinder = new ReceivingHostSendFinder(getConnection());

        try
        {
            // Gets environment information of a report data file
            acquireFileInfo(ReceivingInParameter.DATA_TYPE_RECEIVE);

            // Creates conditions to pick out  by the plan unit (detail) from result send information
            // Specifies a column to get
            ReceivingHostSendSearchKey sendSKey = new ReceivingHostSendSearchKey();
            sendSKey.setCollect(new FieldName(ReceivingHostSend.STORE_NAME, FieldName.ALL_FIELDS));
            sendSKey.setCollect(ReceivingPlan.STATUS_FLAG);

            // Result report type = "Unreported"
            sendSKey.setReportFlag(ReceivingHostSend.REPORT_FLAG_NOT_REPORT);

            // Specifies joint condtions of result send information and receiving plan information
            sendSKey.setJoin(ReceivingHostSend.PLAN_UKEY, "", ReceivingPlan.PLAN_UKEY, "");

            // Order: Planned date > Receiving Ticket No. > Receiving Line No. > Registered date
            sendSKey.setPlanDayOrder(true);
            sendSKey.setSupplierCodeOrder(true);
            sendSKey.setReceiveTicketNoOrder(true);
            sendSKey.setReceiveLineNoOrder(true);
            sendSKey.setRegistDateOrder(true);

            hFinder.open(true);

            // Searches result send information
            if (hFinder.search(sendSKey) <= 0)
            {
                // 6003011 = "Target data was not found."
                setMessage("6003011");
                return true;
            }

            String planUKey = "";
            boolean flag = false;

            while (hFinder.hasNext())
            {
                // Gets search result by 100 and outputs them to a file
                ReceivingHostSend[] hostSend = (ReceivingHostSend[])hFinder.getEntities(100);

                for (ReceivingHostSend hSend : hostSend)
                {
                    String status = String.valueOf(hSend.getValue(ReceivingPlan.STATUS_FLAG, ""));
                    if (status.equals(ReceivingPlan.STATUS_FLAG_COMPLETION))
                    {
                        if (getReportCount() == 0)
                        {
                            try
                            {
                                // Opens a file when an initial output occurs
                                // Suresh.K modified
                                // Generates a result file name（Creates a temp file store folder)
                                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                                String sysTime = df.format(new java.util.Date(System.currentTimeMillis()));

                                // Sets a file name
                                setResultFileName(getFileName() + sysTime + getExtention());
                                // Writes to a file to store temporary
                                // Creates a new directory when the temp folder does not exist
                                prepareDir(WmsParam.HOSTDATA_LOCAL_PATH + getResultFileName());
                                handler.open(WmsParam.HOSTDATA_LOCAL_PATH, getResultFileName());
                                // ファイルを作成したのでエラー発生時には削除
                                deleteFile = true;
                            }
                            catch (ReadWriteException e)
                            {
                                //6003019 = "Selected folder is invalid."
                                setMessage("6003019");
                                return false;
                            }
                            handler.clear();
                        }
                        // Outputs data to a receiving result report file
                        if (csvWrite(handler, rReceivingEntity, hSend))
                        {
                            setReportCount(getReportCount() + 1);

                            // Updates result report type of receiving result send information to "reported"
                            ReceivingHostSendAlterKey hstAKey = new ReceivingHostSendAlterKey();
                            hstAKey.setPlanUkey(hSend.getPlanUkey());
                            updateHostSendReportFlag(hstAKey);

                            if (!hSend.getPlanUkey().equals(planUKey))
                            {
                                // Updates result report type of receiving plan information to "reported"
                                ReceivingPlanAlterKey planAKey = new ReceivingPlanAlterKey();
                                planAKey.setPlanUkey(hSend.getPlanUkey());
                                updateReceivingPlanReportFlag(planAKey);
                                planUKey = hSend.getPlanUkey();
                            }
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
            // 6007002= "Database error occurred.  Please check the message log."
            setMessage("6007002");
            return false;
        }
        finally
        {
            hFinder.close();

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
     * Creates a data report result file.<BR>
     * Gets environment information of a report data file, and creates a data report result file.<BR> 
     * Actural creating process is done by a method, <CODE>createResultReportFile()</CODE>, in <CODE>AbstractReportDBDataCreator</CODE> class.<BR>
     * @return True when a process is completed correctly. Otherwise false.
     * @throws IOException Thrown when an I/O exception  happens<BR>
     * @throws ReadWriteException Thrown when an error in a file asscess happens.<BR>
     */
    public boolean sendReportFile()
            throws IOException,
                ReadWriteException
    {
        // Gets environment information of a report data file
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
     * Sets outputs of receiving result report to an entity, rReceivingEntity (ReportReceiving class), and outputs them to a receiving result reports CSV file.<BR>
     * @param handler File Handler<BR>
     * @param rReceivingEntity Output entity<BR>
     * @param hostSend Receiving result send information entity<BR>
     * @return boolean True if CSV file output is successful.<BR>
     * @throws ReadWriteException Thrown when an error in a file asscess happens.<BR>
     */
    protected boolean csvWrite(FileHandler handler, ReportReceiving rReceivingEntity, ReceivingHostSend hostSend)
            throws ReadWriteException
    {
        // Edits outputs of a receiving result report CSV file
        rReceivingEntity.setValue(ReportReceiving.CANCEL_FLAG, SystemDefine.CANCEL_FLAG_NORMAL);
        rReceivingEntity.setValue(ReportReceiving.PLAN_DAY, hostSend.getPlanDay());
        rReceivingEntity.setValue(ReportReceiving.SUPPLIER_CODE, hostSend.getSupplierCode());
        rReceivingEntity.setValue(ReportReceiving.SUPPLIER_NAME, hostSend.getSupplierName());
        rReceivingEntity.setValue(ReportReceiving.RECEIVE_TICKET_NO, hostSend.getReceiveTicketNo());
        rReceivingEntity.setValue(ReportReceiving.RECEIVE_LINE_NO, new BigDecimal(hostSend.getReceiveLineNo()));
        rReceivingEntity.setValue(ReportReceiving.ITEM_CODE, hostSend.getItemCode());
        rReceivingEntity.setValue(ReportReceiving.ITEM_NAME, hostSend.getItemName());
        rReceivingEntity.setValue(ReportReceiving.JAN, hostSend.getJan());
        rReceivingEntity.setValue(ReportReceiving.ITF, hostSend.getItf());
        rReceivingEntity.setValue(ReportReceiving.ENTERING_QTY, new BigDecimal(hostSend.getEnteringQty()));
        rReceivingEntity.setValue(ReportReceiving.PLAN_LOT_NO, hostSend.getPlanLotNo());
        rReceivingEntity.setValue(ReportReceiving.PLAN_QTY, new BigDecimal(hostSend.getPlanQty()));
        rReceivingEntity.setValue(ReportReceiving.RESULT_QTY, new BigDecimal(hostSend.getResultQty()));
        rReceivingEntity.setValue(ReportReceiving.WORK_DAY, hostSend.getWorkDay());

        // Edits detail parts if a flag is "detail".
        rReceivingEntity.setValue(ReportReceiving.RESULT_LOT_NO, hostSend.getResultLotNo());
        rReceivingEntity.setValue(ReportReceiving.USER_ID, hostSend.getUserId());
        rReceivingEntity.setValue(ReportReceiving.TERMINAL_NO, hostSend.getTerminalNo());

        // Outputs them to a CSV file
        handler.lock();
        handler.create(rReceivingEntity);
        handler.unLock();

        return true;
    }

    /**
     * Write to the operation log <BR>
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
     * Returns a revision of this class
     * @return String of a revision
     */
    public static String getVersion()
    {
        return "$Id: ReceivingReportDBDataDetailCreator.java 7735 2010-03-26 06:22:49Z okayama $";
    }
}
