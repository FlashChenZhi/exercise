// $Id: StorageReportDataCollectionCreator.java 7241 2010-02-26 05:32:14Z okayama $
package jp.co.daifuku.wms.storage.schedule;

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
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.ReportStorage;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * 入庫実績データ報告処理クラスです。<BR>
 * 報告単位は予定単位（集約）です。<BR>
 * <BR>
 * Designer : nakai<BR>
 * Maker : nakai
 * @version $Revision: 7241 $, $Date: 2010-02-26 14:32:14 +0900 (金, 26 2 2010) $
 * @author  Last commit: $Author: okayama $
 */
public class StorageReportDataCollectionCreator
        extends StorageReportDataCreator
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * @param conn データベースConnection<BR>
     */
    public StorageReportDataCollectionCreator(Connection conn)
    {
        super(conn);
    }

    /**
     * @param conn データベースConnection<BR>
     * @param caller 呼び出し元クラス
     */
    public StorageReportDataCollectionCreator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 入庫実績 予定単位（集約）のデータ報告作成処理を行います。<BR>
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

        // 入庫予定情報から報告データを抽出する為のFinderを作成します。
        StoragePlanFinder sFinder = new StoragePlanFinder(getConnection());

        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(StorageInParameter.DATA_TYPE_STORAGE);

            // 入庫予定情報から予定単位（集約）で抽出する為のSQLを作成します。
            StoragePlanSearchKey planSKey = new StoragePlanSearchKey();
            setSerarchKey(planSKey);

            sFinder.open(true);

            // 入庫予定情報を検索します。
            if (sFinder.search(planSKey) <= 0)
            {
                // 対象データはありませんでした。
                setMessage("6003011");
                return true;
            }

            boolean flag = false;

            while (sFinder.hasNext())
            {
                // 検索結果を100件づつ取得し、ファイルへ出力していく。
                StoragePlan[] storagePlan = (StoragePlan[])sFinder.getEntities(RESULT_READ_QTY);

                for (StoragePlan sPlan : storagePlan)
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
                    if (csvWrite(handler, rStorageEntity, sPlan))
                    {
                        setReportCount(getReportCount() + 1);

                        // 入出庫実績送信情報の実績報告区分を送信済みに更新します。
                        HostSendAlterKey hstAKey = new HostSendAlterKey();
                        hstAKey.setPlanUkey(sPlan.getPlanUkey());
                        updateHostSendReportFlag(hstAKey);

                        // 入庫予定情報の実績報告区分を送信済みに更新します。
                        StoragePlanAlterKey planAKey = new StoragePlanAlterKey();
                        planAKey.setPlanUkey(sPlan.getPlanUkey());
                        updateStoragePlanReportFlag(planAKey);
                        setMessage("6001009");

                        flag = true;
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
     * 
     * @param planSKey
     */
    protected void setSerarchKey(StoragePlanSearchKey planSKey)
    {
        // 取得項目
        planSKey.setPlanUkeyCollect("MAX");
        planSKey.setPlanDayCollect();
        planSKey.setReceiveTicketNoCollect();
        planSKey.setReceiveLineNoCollect();
        planSKey.setBranchNoCollect();
        planSKey.setItemCodeCollect("MAX");
        planSKey.setCollect(HostSend.ITEM_NAME, "MAX", HostSend.ITEM_NAME);
        planSKey.setCollect(HostSend.JAN, "MAX", HostSend.JAN);
        planSKey.setCollect(HostSend.ITF, "MAX", HostSend.ITF);
        planSKey.setCollect(HostSend.ENTERING_QTY, "MAX", HostSend.ENTERING_QTY);
        planSKey.setPlanLotNoCollect("MAX");
        planSKey.setPlanQtyCollect("MAX");
        planSKey.setPlanAreaNoCollect("MAX");
        planSKey.setPlanLocationNoCollect("MAX");
        planSKey.setResultQtyCollect("MAX");
        planSKey.setWorkDayCollect("MAX");

        // 検索条件
        // 状態フラグ = 完了 AND 実績報告区分 = 未送信
        planSKey.setStatusFlag(StoragePlan.STATUS_FLAG_COMPLETION);
        planSKey.setReportFlag(StoragePlan.REPORT_FLAG_NOT_REPORT);
        planSKey.setJoin(StoragePlan.PLAN_UKEY, HostSend.PLAN_UKEY);

        // 集約条件
        planSKey.setPlanDayGroup();
        planSKey.setReceiveTicketNoGroup();
        planSKey.setReceiveLineNoGroup();
        planSKey.setBranchNoGroup();

        // 検索順
        // 予定日 > スケジュールNo > スケジュールSeqNo > 作業枝番 順
        planSKey.setPlanDayOrder(true);
        planSKey.setReceiveTicketNoOrder(true);
        planSKey.setReceiveLineNoOrder(true);
        planSKey.setBranchNoOrder(true);
    }

    /**
     * データ報告実績ファイルの作成処理を行ないます。<BR>
     * 報告データファイルの環境情報を取得し、データ報告実績ファイルの作成を行ないます。<BR>
     * 実際の作成処理は<CODE>AbstractReportDataCreator</CODE>クラスの<CODE>createResultReportFile()</CODE>メソッドを使用します。<BR>
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
            acquireExchangeEnvironment(StorageInParameter.DATA_TYPE_STORAGE);
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
     * 入庫実績報告の出力内容をエンティティにセットし、入庫実績報告CSVファイルに出力ます。<BR>
     * @param handler ファイルハンドラ<BR>
     * @param rStorageEntity 出力エンティティ<BR>
     * @param storagePlan 入庫予定情報エンティティ<BR>
     * @return boolean CSVファイルの出力に成功した場合は、Trueを返します。<BR>
     * @throws ReadWriteException ファイルアクセスでエラーが発生した場合に通知されます。<BR>
     */
    protected boolean csvWrite(FileHandler handler, ReportStorage rStorageEntity, StoragePlan storagePlan)
            throws ReadWriteException
    {
        // 入庫実績報告CSVファイルの出力内容を編集します。
        rStorageEntity.setValue(ReportStorage.CANCEL_FLAG, SystemDefine.CANCEL_FLAG_NORMAL);
        rStorageEntity.setValue(ReportStorage.PLAN_DAY, storagePlan.getPlanDay());
        rStorageEntity.setValue(ReportStorage.RECEIVE_TICKET_NO, storagePlan.getReceiveTicketNo());
        rStorageEntity.setValue(ReportStorage.RECEIVE_LINE_NO, new BigDecimal(storagePlan.getReceiveLineNo()));
        rStorageEntity.setValue(ReportStorage.BRANCH_NO, new BigDecimal(storagePlan.getBranchNo()));
        rStorageEntity.setValue(ReportStorage.ITEM_CODE, storagePlan.getItemCode());
        rStorageEntity.setValue(ReportStorage.ITEM_NAME, storagePlan.getValue(HostSend.ITEM_NAME, ""));
        rStorageEntity.setValue(ReportStorage.JAN, storagePlan.getValue(HostSend.JAN, ""));
        rStorageEntity.setValue(ReportStorage.ITF, storagePlan.getValue(HostSend.ITF, ""));
        rStorageEntity.setValue(ReportStorage.ENTERING_QTY, storagePlan.getBigDecimal(HostSend.ENTERING_QTY));
        rStorageEntity.setValue(ReportStorage.PLAN_LOT_NO, storagePlan.getPlanLotNo());
        rStorageEntity.setValue(ReportStorage.PLAN_QTY, new BigDecimal(storagePlan.getPlanQty()));
        rStorageEntity.setValue(ReportStorage.PLAN_AREA_NO, storagePlan.getPlanAreaNo());
        rStorageEntity.setValue(ReportStorage.PLAN_LOCATION_NO, storagePlan.getPlanLocationNo());
        rStorageEntity.setValue(ReportStorage.RESULT_QTY, new BigDecimal(storagePlan.getResultQty()));
        rStorageEntity.setValue(ReportStorage.WORK_DAY, storagePlan.getWorkDay());

        // 報告単位が予定単位（集約）の場合は、明細部分に""をセットします。
        rStorageEntity.setValue(ReportStorage.RESULT_AREA_NO, "");
        rStorageEntity.setValue(ReportStorage.RESULT_LOCATION_NO, "");
        rStorageEntity.setValue(ReportStorage.RESULT_LOT_NO, "");
        rStorageEntity.setValue(ReportStorage.USER_ID, "");
        rStorageEntity.setValue(ReportStorage.TERMINAL_NO, "");

        // CSVファイルに出力します。
        handler.lock();
        handler.create(rStorageEntity);
        handler.unLock();

        return true;
    }

    /**
     * オペレーションログ情報の書込み処理を行います <BR>
     * @param   conn            データベースコネクション
     * @param   operationKind  操作区分
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
        return "$Id: StorageReportDataCollectionCreator.java 7241 2010-02-26 05:32:14Z okayama $";
    }
}
