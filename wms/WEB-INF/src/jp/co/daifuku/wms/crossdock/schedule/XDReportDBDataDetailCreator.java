// $Id: XDReportDBDataDetailCreator.java 7733 2010-03-26 06:21:56Z okayama $
package jp.co.daifuku.wms.crossdock.schedule;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.EWNToHostHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.SortHostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortHostSendHandler;
import jp.co.daifuku.wms.base.entity.EWNToHost;
import jp.co.daifuku.wms.base.entity.ReceivingHostSend;
import jp.co.daifuku.wms.base.entity.SortHostSend;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.ReportCrossDock;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * <BR>
 * TC実績データ報告処理クラスです。<br>
 * 報告単位は予定単位（明細）です<BR>
 * <BR>
 * Designer : A.Ose <BR>
 * Maker : A.Ose <BR>
 * @version $Revision: 7733 $, $Date: 2010-03-26 15:21:56 +0900 (金, 26 3 2010) $
 * @author  Last commit: $Author: okayama $
 */
public class XDReportDBDataDetailCreator
        extends XDReportDBDataCreator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** log出力用 */
    private static final String BUSINESS_NAME = "business";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private CrossDockPlanAlterKey _crossDockAKey = null;

    private CrossDockPlanHandler _crossDockPHndler = null;

    private ReceivingHostSendAlterKey _receiveAKey = null;

    private ReceivingHostSendHandler _receiveHandler = null;

    private SortHostSendAlterKey _sortAKey = null;

    private SortHostSendHandler _sortHandler = null;

    private EWNToHostHandler _EWNToHostHandler = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * @param conn データベースConnection<BR>
     */
    public XDReportDBDataDetailCreator(Connection conn, Connection customerConn)
    {
        super(conn, customerConn);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB
    }

    /**
     * @param conn データベースConnection<BR>
     * @param caller 呼び出し元クラス
     */
    public XDReportDBDataDetailCreator(Connection conn, Connection customerConn, Class caller)
    {
        super(conn, customerConn, caller);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * TC実績 予定単位（明細）のデータ報告作成処理を行います。<BR>
     * 出荷実績送信情報にデータが存在する場合、出荷実績送信情報からデータ取得を行い、<BR>
     * 存在しない場合、仕分実績送信情報からデータ取得を行います。<BR>
     * @return boolean TC報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        // ローカルファイル削除フラグ
        boolean deleteFile = false;

        // 報告データエンティティを指定してファイルハンドラ作成
        ReportCrossDock rCrossDockEntity = new ReportCrossDock();
        FileHandler handler = AbstractFileHandler.getInstance(rCrossDockEntity);

        // 報告データ件数を初期化します。
        setReportCount(0);

        DefaultDDBFinder finder = new DefaultDDBFinder(getConnection(), new SortHostSend());

        try
        {
            // 報告データファイルの環境情報を取得
            acquireFileInfo(XDInParameter.DATA_TYPE_CROSSDOCK);

            finder.open(true);
            finder.search(getCountSQL().toString());
            long start = System.currentTimeMillis();
            ResultSet rs = finder.getResultSet();
            if (rs.next())
            {
                if (rs.getBigDecimal("COUNT").intValue() == 0)
                {
                    // 対象データはありませんでした。
                    setMessage("6003011");
                    return true;
                }
            }
            else
            {
                throw new ReadWriteException();
            }
            long cnt = System.currentTimeMillis();


            finder.search(getSelectSQL().toString());

            // 各ハンドラ類を生成する
            init();

            long find = System.currentTimeMillis();
            boolean writeFlag = false;

            String memUkey = "";
            while (finder.hasNext())
            {
                if (getReportCount() == 0)
                {
                    try
                    {
                        // 一時ファイル名の作成
                        // 現在システム日時を"yyyyMMddHHmmss"フォーマットで取得
                        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                        String sysTime = df.format(new Date(System.currentTimeMillis()));

                        // ファイル名のセット
                        setResultFileName(getFileName() + sysTime + getExtention());

                        // 対象のディレクトリが存在しない場合、ディレクトリを作成
                        prepareDir(WmsParam.HOSTDATA_LOCAL_PATH + getResultFileName());
                        handler.open(WmsParam.HOSTDATA_LOCAL_PATH, getResultFileName());
                        // ファイルを作成したのでエラー発生時には削除
                        deleteFile = true;
                    }
                    catch (ReadWriteException e)
                    {
                        // 6003019 = 指定されたフォルダは無効です。
                        setMessage(WmsMessageFormatter.format(6003019));
                        return false;
                    }
                    handler.clear();
                }

                // 検索結果を100件づつ取得し、ファイルへ出力していく。
                SortHostSend[] entities = (SortHostSend[])finder.getEntities(100);

                for (SortHostSend entity : entities)
                {
                    // 実績報告ファイルに出力します。
                    if (csvWrite(handler, rCrossDockEntity, entity))
                    {
                        setReportCount(getReportCount() + 1);

                        updateSortHostSendByJobNo(entity.getJobNo());

                        // 同一クロスドック一意キーで初回のみ更新
                        if (!memUkey.equals(entity.getCrossDockUkey()))
                        {
                            memUkey = entity.getCrossDockUkey();

                            updateCrossDockPlan(memUkey);

                            updateReceivingHostSend(memUkey);

                        }

                        // データの書き込みは正常に終了しました。
                        setMessage(WmsMessageFormatter.format(6001009));

                        // 正常終了
                        writeFlag = true;

                        //glm start
                        //rCrossDockEntity now has the host send stuff mapped to entity fields, let's use it again
                        String rec = handler.getRecordFormatter().format(rCrossDockEntity);
                        if (!rec.equals(""))
                        {
                            EWNToHost toHostEntity = new EWNToHost();

                            // MESSAGE_DATE
                            toHostEntity.setMessageDate(new SysDate());
                            // SEQUENCE_NO
                            toHostEntity.setSequenceNo(getNextInReportSequence());
                            // MESSAGE_ID
                            toHostEntity.setMessageId(XDInParameter.DATA_TYPE_CROSSDOCK);
                            // message DATA
                            toHostEntity.setData(rec);

                            _EWNToHostHandler.create(toHostEntity);
                            getCustomerConnection().commit();
                        }//glm end
                    }
                }
            }

            // 呼び出し元のクラス名を取得            
            String className = getCaller().getName().toLowerCase();

            // 書込みが正常終了でかつ、呼出元のクラス名がbusinessクラスでない場合
            if (writeFlag && 0 > className.indexOf(BUSINESS_NAME))
            {
                // オペレーションログ情報の書込みを行う
                log_write(getConnection(), EmConstants.OPELOG_CLASS_AUTO_REPORT);
            }
            long end = System.currentTimeMillis();
            DEBUG.MSG(DEBUG.SCH, "COUNT::: " + (cnt - start));
            DEBUG.MSG(DEBUG.SCH, "FIND:::: " + (find - cnt));
            DEBUG.MSG(DEBUG.SCH, "EXEC:::: " + (end - find));
            DEBUG.MSG(DEBUG.SCH, "ALL::::: " + (end - start));
            // 処理が正常に完了したのでファイルは削除しない
            deleteFile = false;
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            return false;
        }
        catch (NotFoundException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            return false;
        }
        //glm start
        //for writing to customer's DB
        catch (DataExistsException e)
        {
            // 6020043=
            setMessage("6020043");
            return false;
        }
        //glm end
        catch (ScheduleException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            return false;
        }
        catch (SQLException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage(WmsMessageFormatter.format(6007002));
            return false;
        }
        finally
        {
            finder.close();

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
     * データ報告実績ファイルの作成処理を行ないます。<BR>
     * 報告データファイルの環境情報を取得し、データ報告実績ファイルの作成を行ないます。<BR>
     * 実際の作成処理は<CODE>AbstractReportDBDataCreator</CODE>クラスの<CODE>createResultReportFile()</CODE>メソッドを使用します。<BR>
     * @return 正常に処理が完了した場合は「<CODE>True</CODE>」それ以外は「<CODE>false</CODE>」を返します。
     * @throws IOException 入出力の例外が発生した場合に通知されます。<BR>
     * @throws ReadWriteException ファイルアクセスでエラーが発生した場合に通知されます。<BR>
     */
    public boolean sendReportFile()
            throws IOException,
                ReadWriteException
    {
        // 報告データファイルの環境情報を取得します。
        try
        {
            acquireFileInfo(XDInParameter.DATA_TYPE_CROSSDOCK);
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
     * TC実績報告の出力内容をエンティティにセットし、TC実績報告CSVファイルに出力ます。
     * @param handler ファイルハンドラ
     * @param repEnt 出力エンティティ
     * @param sort 仕分実績送信情報エンティティ
     * @param ships 出荷実績送信情報エンティティ（配列）
     * @return boolean CSVファイルの出力に成功した場合は、Trueを返します。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected boolean csvWrite(FileHandler handler, ReportCrossDock repEnt, SortHostSend sort)
            throws ReadWriteException,
                ScheduleException
    {
        repEnt.setValue(ReportCrossDock.CANCEL_FLAG, SystemDefine.CANCEL_FLAG_NORMAL);
        repEnt.setValue(ReportCrossDock.PLAN_DAY, sort.getPlanDay());
        repEnt.setValue(ReportCrossDock.BATCH_NO, sort.getBatchNo());
        repEnt.setValue(ReportCrossDock.ITEM_CODE, sort.getItemCode());
        repEnt.setValue(ReportCrossDock.PLAN_LOT_NO, String.valueOf(sort.getValue(ReceivingHostSend.PLAN_LOT_NO, "")));
        repEnt.setValue(ReportCrossDock.SORTING_PLACE, sort.getSortingPlace());
        repEnt.setValue(ReportCrossDock.CUSTOMER_CODE, sort.getCustomerCode());
        repEnt.setValue(ReportCrossDock.SHIP_TICKET_NO, sort.getShipTicketNo());
        repEnt.setValue(ReportCrossDock.SHIP_LINE_NO, new BigDecimal(sort.getShipLineNo()));

        repEnt.setValue(ReportCrossDock.RECEIVE_TICKET_NO, sort.getValue(ReceivingHostSend.RECEIVE_TICKET_NO));
        repEnt.setValue(ReportCrossDock.RECEIVE_LINE_NO, new BigDecimal(
                sort.getValue(ReceivingHostSend.RECEIVE_LINE_NO).toString()));
        repEnt.setValue(ReportCrossDock.SUPPLIER_CODE,
                String.valueOf(sort.getValue(ReceivingHostSend.SUPPLIER_CODE, "")));
        repEnt.setValue(ReportCrossDock.SUPPLIER_NAME,
                String.valueOf(sort.getValue(ReceivingHostSend.SUPPLIER_NAME, "")));

        repEnt.setValue(ReportCrossDock.PLAN_QTY, new BigDecimal(sort.getPlanQty()));

        repEnt.setValue(ReportCrossDock.ITEM_NAME, sort.getItemName());
        repEnt.setValue(ReportCrossDock.JAN, sort.getJan());
        repEnt.setValue(ReportCrossDock.ITF, sort.getItf());
        repEnt.setValue(ReportCrossDock.ENTERING_QTY, new BigDecimal(sort.getEnteringQty()));
        repEnt.setValue(ReportCrossDock.CUSTOMER_NAME, sort.getCustomerName());

        repEnt.setValue(ReportCrossDock.RESULT_QTY, new BigDecimal(sort.getResultQty()));
        repEnt.setValue(ReportCrossDock.WORK_DAY, sort.getWorkDay());
        repEnt.setValue(ReportCrossDock.RESULT_LOT_NO, sort.getResultLotNo());
        repEnt.setValue(ReportCrossDock.USER_ID, sort.getUserId());
        repEnt.setValue(ReportCrossDock.TERMINAL_NO, sort.getTerminalNo());

        // CSVファイルに出力
        handler.lock();
        handler.create(repEnt);
        handler.unLock();

        return true;
    }

    /**
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param  conn データベースコネクション
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
        itemLog.add(XDInParameter.DATA_TYPE_CROSSDOCK);

        // ログ出力
        P11LogWriter opeLogWriter = new P11LogWriter(conn);
        opeLogWriter.createOperationLog(user, operationKind, itemLog);
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * 各ハンドラ類を生成します
     */
    private void init()
    {
        _crossDockAKey = new CrossDockPlanAlterKey();
        _crossDockPHndler = new CrossDockPlanHandler(getConnection());
        _receiveAKey = new ReceivingHostSendAlterKey();
        _receiveHandler = new ReceivingHostSendHandler(getConnection());
        _sortAKey = new SortHostSendAlterKey();
        _sortHandler = new SortHostSendHandler(getConnection());
    }

    /**
     * 入荷実績送信情報を更新
     * @param memUkey
     * @throws NotFoundException
     * @throws ReadWriteException
     */
    private void updateReceivingHostSend(String memUkey)
            throws NotFoundException,
                ReadWriteException
    {
        // 入荷実績送信情報.実績報告区分を報告済に更新
        _receiveAKey.clear();
        _receiveAKey.setCrossDockUkey(memUkey);
        _receiveAKey.updateReportFlag(SystemDefine.REPORT_FLAG_REPORT);
        _receiveAKey.updateLastUpdatePname(getCallerName());
        _receiveHandler.modify(_receiveAKey);
    }

    /**
     * TC予定情報を更新
     * @param memUkey
     * @throws NotFoundException
     * @throws ReadWriteException
     */
    private void updateCrossDockPlan(String memUkey)
            throws NotFoundException,
                ReadWriteException
    {
        // TC予定情報.実績報告区分を報告済に更新
        _crossDockAKey.clear();
        _crossDockAKey.setCrossDockUkey(memUkey);
        _crossDockAKey.updateReportFlag(SystemDefine.REPORT_FLAG_REPORT);
        _crossDockAKey.updateLastUpdatePname(getCallerName());
        _crossDockPHndler.modify(_crossDockAKey);
    }

    /**
     * 仕分実績送信情報を更新
     * @param entity
     * @throws NotFoundException
     * @throws ReadWriteException
     */
    private void updateSortHostSendByJobNo(String jobNo)
            throws NotFoundException,
                ReadWriteException
    {
        // 仕分実績送信情報.実績報告区分を報告済に更新
        _sortAKey.clear();
        _sortAKey.setJobNo(jobNo);
        _sortAKey.updateReportFlag(SystemDefine.REPORT_FLAG_REPORT);
        _sortAKey.updateLastUpdatePname(getCallerName());
        _sortHandler.modify(_sortAKey);
    }

    /**
     * 検索SQLを返します。
     * @return 検索SQL
     */
    private StringBuilder getSelectSQL()
    {
        StringBuilder select = new StringBuilder();
        select.append("select sort.*, re.* ");

        StringBuilder where = new StringBuilder();
        where.append("from ");
        where.append("    ( ");
        where.append("        select ");
        where.append("            max(receive_ticket_no) receive_ticket_no, ");
        where.append("            max(receive_line_no) receive_line_no, ");
        where.append("            max(supplier_code) supplier_code, ");
        where.append("            max(supplier_name) supplier_name, ");
        where.append("            max(plan_lot_no) plan_lot_no, ");
        where.append("            cross_dock_ukey ");
        where.append("        from dnreceivinghostsend ");
        where.append("        group by ");
        where.append("            cross_dock_ukey ");
        where.append("    ) re,  ");
        where.append("    ( ");
        where.append("        select ");
        where.append("            sort.* ");
        where.append("        from dnsorthostsend sort, dncrossdockplan xd ");
        where.append("        where  ");
        where.append("            sort.report_flag = '").append(SortHostSend.REPORT_FLAG_NOT_REPORT).append("' ");
        where.append("            and sort.plan_ukey = xd.plan_ukey ");
        where.append("            and xd.status_flag = '").append(SortHostSend.STATUS_FLAG_COMPLETION).append("' ");
        where.append("    ) sort ");
        where.append("where ");
        where.append("    re.cross_dock_ukey = sort.cross_dock_ukey ");

        StringBuilder order = new StringBuilder();
        order.append("order by ");
        order.append("    sort.plan_day, ");
        if (WmsParam.IS_UNIQUE_CHECK_SUPPLIER)
        {
            order.append(" re.supplier_code, ");
        }
        order.append("    re.receive_ticket_no, ");
        order.append("    re.receive_line_no, ");
        if (WmsParam.IS_UNIQUE_CHECK_CUSTOMER)
        {
            order.append(" sort.customer_code, ");
        }
        order.append("    sort.ship_ticket_no, ");
        order.append("    sort.ship_line_no, ");
        order.append("    sort.regist_date ");

        select.append(where).append(order);

        return select;
    }

    /**
     * 件数取得SQLを返します。
     * @return 件数取得SQL
     */
    private StringBuilder getCountSQL()
    {
        StringBuilder count = new StringBuilder();
        count.append("select count(*) COUNT ");

        StringBuilder where = new StringBuilder();
        where.append("from dnsorthostsend sort, dncrossdockplan xd ");
        where.append("where ");
        where.append("    sort.report_flag = '").append(SortHostSend.REPORT_FLAG_NOT_REPORT).append("' ");
        where.append("    and sort.plan_ukey = xd.plan_ukey ");
        where.append("    and xd.status_flag = '").append(SortHostSend.STATUS_FLAG_COMPLETION).append("' ");

        count.append(where);
        return count;
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
        return "$Id: XDReportDBDataDetailCreator.java 7733 2010-03-26 06:21:56Z okayama $";
    }
}
