// $Id: NoPlanRetrievalReportDataWorkCreator.java 2821 2009-01-20 08:49:24Z kishimoto $
package jp.co.daifuku.wms.stock.schedule;

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
import java.text.SimpleDateFormat;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanAlterKey;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.fileentity.FaReportNoPlanInOut;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * このクラスは予定外入出庫実績作業単位報告を作成するクラスです。<BR>
 * <BR>
 * Designer : Yoshida <BR>
 * Maker : Yoshida
 * @version $Revision: 2821 $, $Date: 2009-01-20 17:49:24 +0900 (火, 20 1 2009) $
 * @author  Last commit: $Author: kishimoto $
 */
public class FaNoPlanInOutReportDataWorkCreator
        extends FaNoPlanInOutReportDataCreator
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
     * @param conn データベースConnection
     */
    public FaNoPlanInOutReportDataWorkCreator(Connection conn)
    {
        super(conn);
    }

    /**
     * コンストラクタ
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    protected FaNoPlanInOutReportDataWorkCreator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 予定外入出庫実績 作業単位のデータ報告作成処理を行います。<BR>
     * @return boolean 予定外入出庫実績データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        // ローカルファイル削除フラグ
        boolean deleteFile = false;

        FaReportNoPlanInOut rNoplanInOutEntity = new FaReportNoPlanInOut();
        //報告データファイル名を指定
        FileHandler handler = AbstractFileHandler.getInstance(rNoplanInOutEntity);

        // 報告データ件数を初期化します。
        setReportCount(0);

        // 実績送信情報から報告データを抽出する為のFinderを作成します。
        HostSendFinder hFinder = new HostSendFinder(getConnection());

        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(ExchangeEnvironment.DATA_TYPE_NO_PLAN_INOUT);

            // 実績送信情報から作業単位で抽出する為の条件を作成します。
            // 取得するカラムを指定します。
            HostSendSearchKey sendSKey = new HostSendSearchKey();
            sendSKey.setCollect(new FieldName(HostSend.STORE_NAME, FieldName.ALL_FIELDS));

            // 実績報告区分 = 未送信
            sendSKey.setReportFlag(HostSend.REPORT_FLAG_NOT_REPORT);
            // 作業区分 = 予定外入庫、予定外出庫、再入庫
            String[] jobType = {
                    HostSend.JOB_TYPE_NOPLAN_STORAGE,
                    HostSend.JOB_TYPE_NOPLAN_RETRIEVAL,
                    HostSend.JOB_TYPE_RESTORING,
                    HostSend.JOB_TYPE_MAINTENANCE_PLUS,
                    HostSend.JOB_TYPE_MAINTENANCE_MINUS,
                    HostSend.JOB_TYPE_ASRS_EXPENDITURE,
                    HostSend.JOB_TYPE_ASRS_CARRYINFODELETE
            };
            sendSKey.setJobType(jobType, false);

            // ソート順　登録日時
            sendSKey.setRegistDateOrder(true);
            hFinder.open(true);

            // 実績送信情報を検索します。
            if (hFinder.search(sendSKey) <= 0)
            {
                // 対象データはありませんでした。
                setMessage("6003011");
                return true;
            }

            while (hFinder.hasNext())
            {
                // 検索結果を100件づつ取得し、ファイルへ出力していく。
                HostSend[] hostSend = (HostSend[])hFinder.getEntities(RESULT_READ_QTY);

                for (HostSend hSend : hostSend)
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
                    // 予定外入出庫実績報告ファイルに出力します。
                    if (csvWrite(handler, rNoplanInOutEntity, hSend))
                    {
                        setReportCount(getReportCount() + 1);

                        // 入出庫実績送信情報の実績報告区分を送信済みに更新します。
                        HostSendAlterKey hstAKey = new HostSendAlterKey();
                        hstAKey.setJobNo(hSend.getJobNo());
                        updateHostSendReportFlag(hstAKey);
                        
                        if (HostSend.JOB_TYPE_RESTORING.equals(hSend.getJobType()))
                        {
                            // 再入庫の場合は、必ず再入庫予定情報も送信済みに更新する
                            ReStoringPlanAlterKey rsAKey = new ReStoringPlanAlterKey();
                            rsAKey.setPlanUkey(hSend.getPlanUkey());
                            updateReStoringPlanReportFlag(rsAKey);
                        }
                        
                        setMessage("6001009");
                    }
                }
            }

            // 処理が正常に完了したのでファイルは削除しない
            deleteFile = false;

            if (StringUtil.isBlank(getMessage()))
            {
                // 対象データはありませんでした。
                setMessage("6003011");
            }

        }
        catch (ReadWriteException e)
        {
            // 6007002=データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            // (6007002)データベースエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6007002, e), getClass().getName());
            return false;
        }
        catch (NotFoundException e)
        {
            // 6007002=データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            // (6007002)データベースエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6007002, e), getClass().getName());
            return false;
        }
        catch (Exception e)
        {
            // 6027009=予期しないエラーが発生しました。ログを参照してください。
            setMessage("6027009");
            // (6006001)予期しないエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006001, e), getClass().getName());
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
        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(ExchangeEnvironment.DATA_TYPE_NO_PLAN_INOUT);
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
     * 予定外入出庫実績報告の出力内容をエンティティにセットし、予定外出庫実績報告CSVファイルに出力ます。<BR>
     * @param handler ファイルハンドラ<BR>
     * @param rNoplanInOutEntity 出力エンティティ<BR>
     * @param hostSend 入出庫実績送信情報エンティティ<BR>
     * @return boolean CSVファイルの出力に成功した場合は、Trueを返します。<BR>
     * @throws ReadWriteException ファイルアクセスでエラーが発生した場合に通知されます。<BR>
     */
    protected boolean csvWrite(FileHandler handler, FaReportNoPlanInOut rNoplanInOutEntity, HostSend hostSend)
            throws ReadWriteException
    {

        // 予定外入出庫実績報告CSVファイルの出力内容を編集します。
        rNoplanInOutEntity.setValue(FaReportNoPlanInOut.JOB_TYPE, hostSend.getJobType());
        rNoplanInOutEntity.setValue(FaReportNoPlanInOut.ITEM_CODE, hostSend.getItemCode());
        rNoplanInOutEntity.setValue(FaReportNoPlanInOut.ITEM_NAME, hostSend.getItemName());
        rNoplanInOutEntity.setValue(FaReportNoPlanInOut.LOT_NO, hostSend.getResultLotNo());
        rNoplanInOutEntity.setValue(FaReportNoPlanInOut.AREA_NO, hostSend.getResultAreaNo());
        rNoplanInOutEntity.setValue(FaReportNoPlanInOut.LOCATION_NO, hostSend.getResultLocationNo());
        rNoplanInOutEntity.setValue(FaReportNoPlanInOut.RESULT_QTY, new BigDecimal(hostSend.getResultQty()));
        rNoplanInOutEntity.setValue(FaReportNoPlanInOut.WORK_DAY, hostSend.getWorkDay());
        rNoplanInOutEntity.setValue(FaReportNoPlanInOut.USER_ID, hostSend.getUserId());
        rNoplanInOutEntity.setValue(FaReportNoPlanInOut.TERMINAL_NO, hostSend.getTerminalNo());

        // CSVファイルに出力します。
        handler.lock();
        handler.create(rNoplanInOutEntity);
        handler.unLock();

        return true;
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
        return "$Id: NoPlanRetrievalReportDataWorkCreator.java 2821 2009-01-20 08:49:24Z kishimoto $";
    }
}
