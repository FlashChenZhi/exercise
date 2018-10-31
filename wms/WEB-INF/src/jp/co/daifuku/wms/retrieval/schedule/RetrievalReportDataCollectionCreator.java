// $Id: RetrievalReportDataCollectionCreator.java 7241 2010-02-26 05:32:14Z okayama $
package jp.co.daifuku.wms.retrieval.schedule;

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
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.ReportRetrieval;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * 出庫実績データ報告処理クラスです。<BR>
 * 報告単位は予定単位（集約）です。<BR>
 * <BR>
 * Designer : nakai<BR>
 * Maker : nakai
 * @version $Revision: 7241 $, $Date: 2010-02-26 14:32:14 +0900 (金, 26 2 2010) $
 * @author  Last commit: $Author: okayama $
 */
public class RetrievalReportDataCollectionCreator
        extends RetrievalReportDataCreator
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
    public RetrievalReportDataCollectionCreator(Connection conn)
    {
        super(conn);
    }

    /**
     * @param conn データベースConnection<BR>
     * @param caller 呼び出し元クラス
     */
    public RetrievalReportDataCollectionCreator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 出庫実績 予定単位（集約）のデータ報告作成処理を行います。<BR>
     * @return boolean 出庫報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        // ローカルファイル削除フラグ
        boolean deleteFile = false;

        // 報告データエンティティを指定してファイルハンドラ作成
        ReportRetrieval rRetrievalEntity = new ReportRetrieval();
        FileHandler handler = AbstractFileHandler.getInstance(rRetrievalEntity);

        // 報告データ件数を初期化します。
        setReportCount(0);

        // 出庫予定情報から報告データを抽出する為のFinderを作成します。
        RetrievalPlanFinder sFinder = new RetrievalPlanFinder(getConnection());

        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(ExchangeEnvironment.DATA_TYPE_RETRIEVAL);

            // 出庫予定情報から予定単位（集約）で抽出する為のSQLを作成します。
            RetrievalPlanSearchKey planSKey = new RetrievalPlanSearchKey();
            setSearchKey(planSKey);

            sFinder.open(true);

            // 出庫予定情報を検索します。
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
                RetrievalPlan[] retrievalPlan = (RetrievalPlan[])sFinder.getEntities(RESULT_READ_QTY);

                for (RetrievalPlan sPlan : retrievalPlan)
                {
                    if (getReportCount() == 0)
                    {
                        try
                        {
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
                    // 出庫実績報告ファイルに出力します。
                    if (csvWrite(handler, rRetrievalEntity, sPlan))
                    {
                        setReportCount(getReportCount() + 1);

                        // 入出庫実績送信情報の実績報告区分を送信済みに更新します。
                        HostSendAlterKey hstAKey = new HostSendAlterKey();
                        hstAKey.setPlanUkey(sPlan.getPlanUkey());
                        updateHostSendReportFlag(hstAKey);

                        // 出庫予定情報の実績報告区分を送信済みに更新します。
                        RetrievalPlanAlterKey planAKey = new RetrievalPlanAlterKey();
                        planAKey.setPlanUkey(sPlan.getPlanUkey());
                        updateRetrievalPlanReportFlag(planAKey);
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
            return false;
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
    protected void setSearchKey(RetrievalPlanSearchKey planSKey)
    {
        // 取得項目
        planSKey.setPlanUkeyCollect("MAX");
        planSKey.setPlanDayCollect();
        planSKey.setCustomerCodeCollect("MAX");
        planSKey.setCollect(HostSend.CUSTOMER_NAME, "MAX", HostSend.CUSTOMER_NAME);
        planSKey.setShipTicketNoCollect();
        planSKey.setShipLineNoCollect();
        planSKey.setBranchNoCollect();
        planSKey.setBatchNoCollect("MAX");
        planSKey.setOrderNoCollect("MAX");
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
        planSKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
        planSKey.setReportFlag(RetrievalPlan.REPORT_FLAG_NOT_REPORT);
        planSKey.setJoin(RetrievalPlan.PLAN_UKEY, HostSend.PLAN_UKEY);

        // 集約条件
        planSKey.setPlanDayGroup();
        planSKey.setShipTicketNoGroup();
        planSKey.setShipLineNoGroup();
        planSKey.setBranchNoGroup();

        // 検索順
        // 予定日 > 出荷伝票No > 出荷伝票行No > 作業枝番 順
        planSKey.setPlanDayOrder(true);
        planSKey.setShipTicketNoOrder(true);
        planSKey.setShipLineNoOrder(true);
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
            acquireExchangeEnvironment(ExchangeEnvironment.DATA_TYPE_RETRIEVAL);
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
     * 出庫実績報告の出力内容をエンティティにセットし、出庫実績報告CSVファイルに出力ます。<BR>
     * @param handler ファイルハンドラ<BR>
     * @param rRetrievalEntity 出力エンティティ<BR>
     * @param retrievalPlan 出庫予定情報エンティティ<BR>
     * @param hostSend 入出庫実績送信情報エンティティ<BR>
     * @return boolean CSVファイルの出力に成功した場合は、Trueを返します。<BR>
     * @throws ReadWriteException ファイルアクセスでエラーが発生した場合に通知されます。<BR>
     */
    protected boolean csvWrite(FileHandler handler, ReportRetrieval rRetrievalEntity, RetrievalPlan retrievalPlan)
            throws ReadWriteException
    {
        // 出庫実績報告CSVファイルの出力内容を編集します。
        rRetrievalEntity.setValue(ReportRetrieval.CANCEL_FLAG, SystemDefine.CANCEL_FLAG_NORMAL);
        rRetrievalEntity.setValue(ReportRetrieval.PLAN_DAY, retrievalPlan.getPlanDay());
        rRetrievalEntity.setValue(ReportRetrieval.CUSTOMER_CODE, retrievalPlan.getCustomerCode());
        rRetrievalEntity.setValue(ReportRetrieval.CUSTOMER_NAME, retrievalPlan.getValue(HostSend.CUSTOMER_NAME, ""));
        rRetrievalEntity.setValue(ReportRetrieval.SHIP_TICKET_NO, retrievalPlan.getShipTicketNo());
        rRetrievalEntity.setValue(ReportRetrieval.SHIP_LINE_NO, new BigDecimal(retrievalPlan.getShipLineNo()));
        rRetrievalEntity.setValue(ReportRetrieval.BRANCH_NO, new BigDecimal(retrievalPlan.getBranchNo()));
        rRetrievalEntity.setValue(ReportRetrieval.BATCH_NO, retrievalPlan.getBatchNo());
        rRetrievalEntity.setValue(ReportRetrieval.ORDER_NO, retrievalPlan.getOrderNo());
        rRetrievalEntity.setValue(ReportRetrieval.ITEM_CODE, retrievalPlan.getItemCode());
        rRetrievalEntity.setValue(ReportRetrieval.ITEM_NAME, retrievalPlan.getValue(HostSend.ITEM_NAME, ""));
        rRetrievalEntity.setValue(ReportRetrieval.JAN, retrievalPlan.getValue(HostSend.JAN, ""));
        rRetrievalEntity.setValue(ReportRetrieval.ITF, retrievalPlan.getValue(HostSend.ITF, ""));
        rRetrievalEntity.setValue(ReportRetrieval.ENTERING_QTY, retrievalPlan.getBigDecimal(HostSend.ENTERING_QTY));
        rRetrievalEntity.setValue(ReportRetrieval.PLAN_LOT_NO, retrievalPlan.getPlanLotNo());
        rRetrievalEntity.setValue(ReportRetrieval.PLAN_QTY, new BigDecimal(retrievalPlan.getPlanQty()));
        rRetrievalEntity.setValue(ReportRetrieval.PLAN_AREA_NO, retrievalPlan.getPlanAreaNo());
        rRetrievalEntity.setValue(ReportRetrieval.PLAN_LOCATION_NO, retrievalPlan.getPlanLocationNo());
        rRetrievalEntity.setValue(ReportRetrieval.RESULT_QTY, new BigDecimal(retrievalPlan.getResultQty()));
        rRetrievalEntity.setValue(ReportRetrieval.WORK_DAY, retrievalPlan.getWorkDay());

        // 報告単位が予定単位（集約）の場合は、明細部分に""をセットします。
        rRetrievalEntity.setValue(ReportRetrieval.RESULT_AREA_NO, "");
        rRetrievalEntity.setValue(ReportRetrieval.RESULT_LOCATION_NO, "");
        rRetrievalEntity.setValue(ReportRetrieval.RESULT_LOT_NO, "");
        rRetrievalEntity.setValue(ReportRetrieval.USER_ID, "");
        rRetrievalEntity.setValue(ReportRetrieval.TERMINAL_NO, "");

        // CSVファイルに出力します。
        handler.lock();
        handler.create(rRetrievalEntity);
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
        itemLog.add(RetrievalInParameter.DATA_TYPE_RETRIEVAL);

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
        return "$Id: RetrievalReportDataCollectionCreator.java 7241 2010-02-26 05:32:14Z okayama $";
    }
}
