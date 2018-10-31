// $Id: StorageReportDBDataWorkCreator.java 7735 2010-03-26 06:22:49Z okayama $
package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.IOException;
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
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.entity.EWNToHost;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.fileentity.ReportStorage;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * <BR>
 * 入庫実績データ報告処理クラスです。<br>
 * 報告単位は作業単位です<BR>
 * <BR>
 * Designer : nakai <BR>
 * Maker : nakai <BR>
 * @version $Revision: 7735 $, $Date: 2010-03-26 15:22:49 +0900 (金, 26 3 2010) $
 * @author  Last commit: $Author: okayama $
 */
public class StorageReportDBDataWorkCreator
        extends StorageReportDBDataDetailCreator
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
    private EWNToHostHandler _EWNToHostHandler = null;

    private HostSendFinder _StorHostSendFinder = null;

    private HostSendSearchKey _StorHostSendSKey = null;

    private StoragePlanAlterKey _PlanAKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * @param conn データベースConnection<BR>
     */
    public StorageReportDBDataWorkCreator(Connection conn, Connection customerConn)
    {
        super(conn, customerConn);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB
        // Creates Finder to pick out report data from result send information.
        _StorHostSendFinder = new HostSendFinder(getConnection());
        _StorHostSendSKey = new HostSendSearchKey();
        _PlanAKey = new StoragePlanAlterKey();
    }

    /**
     * @param conn データベースConnection<BR>
     * @param caller 呼び出し元クラス
     */
    public StorageReportDBDataWorkCreator(Connection conn, Connection customerConn, Class caller)
    {
        super(conn, customerConn, caller);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB
        // Creates Finder to pick out report data from result send information.
        _StorHostSendFinder = new HostSendFinder(getConnection());
        _StorHostSendSKey = new HostSendSearchKey();
        _PlanAKey = new StoragePlanAlterKey();
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 入庫実績 作業単位のデータ報告作成処理を行います。<BR>
     * @return boolean 入庫報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        // ローカルファイル削除フラグ
        boolean deleteFile = false;

        // 報告データエンティティを指定してファイルハンドラ作成
        ReportStorage rStorageEntity = new ReportStorage();
        FileHandler handler = AbstractFileHandler.getInstance(rStorageEntity);

        // 報告データ件数を初期化します。
        setReportCount(0);

        try
        {
            // handler
            HostSendHandler hostHnad = new HostSendHandler(getConnection());

            // 報告データファイルの環境情報を取得します。
            acquireFileInfo(StorageInParameter.DATA_TYPE_STORAGE);

            // 実績送信情報から作業単位で抽出する為の条件を作成します。
            // 取得するカラムを指定します。
            HostSendSearchKey sendSKey = new HostSendSearchKey();
            sendSKey.setCollect(new FieldName(HostSend.STORE_NAME, FieldName.ALL_FIELDS));
            sendSKey.setCollect(StoragePlan.STATUS_FLAG);

            // 実績報告区分 = 未送信
            sendSKey.setReportFlag(HostSend.REPORT_FLAG_NOT_REPORT);

            // 実績送信情報と入庫予定情報の結合条件を指定します。
            sendSKey.setJoin(HostSend.PLAN_UKEY, "", StoragePlan.PLAN_UKEY, "");

            // 予定日 > スケジュールNo > スケジュールSeqNo > 作業枝番 > 登録日時 順
            sendSKey.setPlanDayOrder(true);
            sendSKey.setReceiveTicketNoOrder(true);
            sendSKey.setReceiveLineNoOrder(true);
            sendSKey.setReceiveBranchNoOrder(true);
            sendSKey.setRegistDateOrder(true);

            _StorHostSendFinder.open(true);

            // 実績送信情報を検索します。
            if (_StorHostSendFinder.search(sendSKey) <= 0)
            {
                // 対象データはありませんでした。
                setMessage("6003011");
                return true;
            }

            boolean flag = false;

            while (_StorHostSendFinder.hasNext())
            {
                // 検索結果を100件づつ取得し、ファイルへ出力していく。
                HostSend[] hostSend = (HostSend[])_StorHostSendFinder.getEntities(100);

                for (HostSend hSend : hostSend)
                {
                    if (getReportCount() == 0)
                    {
                        try
                        {
                            // 初回の出力時にファイルのオープンを行います。
                            // Suresh.K 修正
                            // 実績ファイル名の生成（一時ファイル保存フォルダに作成する)
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                            String sysTime = df.format(new java.util.Date(System.currentTimeMillis()));

                            // ファイル名のセット
                            setResultFileName(getFileName() + sysTime + getExtention());
                            // 一時保存用ファイルに書き込み
                            // ディレクトリが存在しない場合は作成
                            prepareDir(WmsParam.HOSTDATA_LOCAL_PATH + getResultFileName());
                            handler.open(WmsParam.HOSTDATA_LOCAL_PATH, getResultFileName());
                            // ファイルを作成したのでエラー発生時には削除
                            deleteFile = true;
                        }
                        catch (ReadWriteException e)
                        {
                            //6003019=指定されたフォルダは無効です。
                            setMessage("6003019");
                            return false;
                        }
                        handler.clear();
                    }
                    // 入庫実績報告ファイルに出力します。
                    if (csvWrite(handler, rStorageEntity, hSend))
                    {
                        setReportCount(getReportCount() + 1);

                        // 入出庫実績送信情報の実績報告区分を送信済みに更新します。
                        HostSendAlterKey hstAKey = new HostSendAlterKey();
                        hstAKey.setPlanUkey(hSend.getPlanUkey());
                        updateHostSendReportFlag(hstAKey);

                        String status = String.valueOf(hSend.getValue(StoragePlan.STATUS_FLAG, ""));
                        if (status.equals(StoragePlan.STATUS_FLAG_COMPLETION))
                        {
                            _StorHostSendSKey.clear();
                            _StorHostSendSKey.setPlanUkey(hSend.getPlanUkey());
                            _StorHostSendSKey.setReportFlag(HostSend.REPORT_FLAG_NOT_REPORT);

                            if (hostHnad.count(_StorHostSendSKey) <= 0)
                            {
                                // Updates result report type of receiving plan information "reported"
                                _PlanAKey.clear();
                                _PlanAKey.setPlanUkey(hSend.getPlanUkey());
                                updateStoragePlanReportFlag(_PlanAKey);
                            }
                        }
                        setMessage("6001009");

                        flag = true;

                        //rStorageEntity now has the host send stuff mapped to entity fields, let's use it again
                        String rec = handler.getRecordFormatter().format(rStorageEntity);
                        if (!rec.equals(""))
                        {
                            EWNToHost entity = new EWNToHost();

                            // MESSAGE_DATE
                            entity.setMessageDate(new SysDate());
                            // SEQUENCE_NO
                            entity.setSequenceNo(getNextInReportSequence());
                            // MESSAGE_ID
                            entity.setMessageId(StorageInParameter.DATA_TYPE_STORAGE);
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
            // 6007002=データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return false;
        }
        catch (NotFoundException e)
        {
            // 6007002=データベースエラーが発生しました。メッセージログを参照してください。
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
            // 6027009=予期しないエラーが発生しました。ログを参照してください。
            setMessage("6027009");
            return false;
        }
        catch (SQLException e)
        {
            // 6007002=データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return false;
        }
        finally
        {
            _StorHostSendFinder.close();

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
            acquireFileInfo(StorageInParameter.DATA_TYPE_STORAGE);
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
        itemLog.add(StorageInParameter.DATA_TYPE_STORAGE);

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
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: StorageReportDBDataWorkCreator.java 7735 2010-03-26 06:22:49Z okayama $";
    }
}
