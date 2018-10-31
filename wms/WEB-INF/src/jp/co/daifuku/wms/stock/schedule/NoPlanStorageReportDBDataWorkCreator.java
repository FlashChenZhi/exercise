// $Id: NoPlanStorageReportDBDataWorkCreator.java 7735 2010-03-26 06:22:49Z okayama $
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.EWNToHostHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.entity.EWNToHost;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.fileentity.ReportNoPlanStorage;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;


/**
 * このクラスは予定外出庫実績作業単位報告を作成するクラスです。<br>
 * Designer : suresh <BR>
 * Maker : suresh <BR>
 * @version $Revision: 7735 $, $Date: 2010-03-26 15:22:49 +0900 (金, 26 3 2010) $
 * @author  Last commit: $Author: okayama $
 */
public class NoPlanStorageReportDBDataWorkCreator
        extends NoPlanStorageReportDBDataCreator
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
    private EWNToHostHandler _EWNToHostHandler = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * @param conn データベースConnection
     */
    public NoPlanStorageReportDBDataWorkCreator(Connection conn, Connection customerConn)
    {
        super(conn, customerConn);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB
    }

    /**
     * @param conn データベースConnection
     */
    public NoPlanStorageReportDBDataWorkCreator(Connection conn, Connection customerConn, Class caller)
    {
        super(conn, customerConn, caller);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 予定外入庫実績 作業単位のデータ報告作成処理を行います。<BR>
     * @return boolean 予定外入庫報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        // ローカルファイル削除フラグ
        boolean deleteFile = false;

        ReportNoPlanStorage rNoplanStorageEntity = new ReportNoPlanStorage();
        //報告データファイル名を指定
        FileHandler handler = AbstractFileHandler.getInstance(rNoplanStorageEntity);

        // 報告データ件数を初期化します。
        setReportCount(0);

        // 実績送信情報から報告データを抽出する為のFinderを作成します。
        HostSendFinder hFinder = new HostSendFinder(getConnection());

        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireFileInfo(StockInParameter.DATA_TYPE_NOPLAN_STORAGE);

            // 実績送信情報から作業単位で抽出する為の条件を作成します。
            // 取得するカラムを指定します。
            HostSendSearchKey sendSKey = new HostSendSearchKey();
            sendSKey.setCollect(new FieldName(HostSend.STORE_NAME, FieldName.ALL_FIELDS));

            //実績報告区分 = 未送信
            sendSKey.setReportFlag(HostSend.REPORT_FLAG_NOT_REPORT);
            //作業区分 = 予定外入庫
            sendSKey.setJobType(HostSend.JOB_TYPE_NOPLAN_STORAGE);

            //ソート順　登録日時
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
                HostSend[] hostSend = (HostSend[])hFinder.getEntities(100);

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
                    // 予定外入庫実績報告ファイルに出力します。
                    if (csvWrite(handler, rNoplanStorageEntity, hSend))
                    {
                        setReportCount(getReportCount() + 1);

                        // 入出庫実績送信情報の実績報告区分を送信済みに更新します。
                        HostSendAlterKey hstAKey = new HostSendAlterKey();
                        hstAKey.setJobNo(hSend.getJobNo());
                        updateHostSendReportFlag(hstAKey);
                        setMessage("6001009");

                        //glm start
                        //rNoPlanStorageEntity now has the host send stuff mapped to entity fields, let's use it again
                        String rec = handler.getRecordFormatter().format(rNoplanStorageEntity);
                        if (!rec.equals(""))
                        {
                            EWNToHost entity = new EWNToHost();

                            // MESSAGE_DATE
                            entity.setMessageDate(new SysDate());
                            // SEQUENCE_NO
                            entity.setSequenceNo(getNextInReportSequence());
                            // MESSAGE_ID
                            entity.setMessageId(StockInParameter.DATA_TYPE_NOPLAN_STORAGE);
                            // message DATA
                            entity.setData(rec);

                            _EWNToHostHandler.create(entity);
                            getCustomerConnection().commit();
                        }//glm end

                    }
                }
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
        //glm start
        //for writing to customer's DB
        catch (DataExistsException e)
        {
            // 6020043=他端末で処理中のため、スキップしました。ファイル:{0} 行:{1}
            setMessage("6020043");
            return false;
        }
        catch (SQLException e)
        {
            // (6127005)データベースエラーが発生しました。
            setMessage("6127005");
            try
            {
                getCustomerConnection().rollback();
            }
            catch (SQLException ex)
            {
                RmiMsgLogClient.writeSQLTrace(ex, getClass().getName());
                return false;
            }
            return false;
        }
        //glm end
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
            acquireFileInfo(StockInParameter.DATA_TYPE_NOPLAN_STORAGE);
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
     * 予定外入庫実績報告の出力内容をエンティティにセットし、予定外入庫実績報告CSVファイルに出力ます。<BR>
     * @param handler ファイルハンドラ<BR>
     * @param rNoplanStorageEntity 出力エンティティ<BR>
     * @param hostSend 入出庫実績送信情報エンティティ<BR>
     * @return boolean CSVファイルの出力に成功した場合は、Trueを返します。<BR>
     * @throws ReadWriteException ファイルアクセスでエラーが発生した場合に通知されます。<BR>
     */
    protected boolean csvWrite(FileHandler handler, ReportNoPlanStorage rNoplanStorageEntity, HostSend hostSend)
            throws ReadWriteException
    {
        // 予定外入庫実績報告CSVファイルの出力内容を編集します。
        rNoplanStorageEntity.setValue(ReportNoPlanStorage.ITEM_CODE, hostSend.getItemCode());
        rNoplanStorageEntity.setValue(ReportNoPlanStorage.ITEM_NAME, hostSend.getItemName());
        rNoplanStorageEntity.setValue(ReportNoPlanStorage.JAN, hostSend.getJan());
        rNoplanStorageEntity.setValue(ReportNoPlanStorage.ITF, hostSend.getItf());
        rNoplanStorageEntity.setValue(ReportNoPlanStorage.ENTERING_QTY, new BigDecimal(hostSend.getEnteringQty()));
        rNoplanStorageEntity.setValue(ReportNoPlanStorage.RESULT_LOT_NO, hostSend.getResultLotNo());
        rNoplanStorageEntity.setValue(ReportNoPlanStorage.RESULT_AREA_NO, hostSend.getResultAreaNo());
        rNoplanStorageEntity.setValue(ReportNoPlanStorage.RESULT_LOCATION_NO, hostSend.getResultLocationNo());
        rNoplanStorageEntity.setValue(ReportNoPlanStorage.RESULT_QTY, new BigDecimal(hostSend.getResultQty()));
        rNoplanStorageEntity.setValue(ReportNoPlanStorage.WORK_DAY, hostSend.getWorkDay());
        rNoplanStorageEntity.setValue(ReportNoPlanStorage.USER_ID, hostSend.getUserId());
        rNoplanStorageEntity.setValue(ReportNoPlanStorage.TERMINAL_NO, hostSend.getTerminalNo());

        // CSVファイルに出力します。
        handler.lock();
        handler.create(rNoplanStorageEntity);
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
        return "$Id: NoPlanStorageReportDBDataWorkCreator.java 7735 2010-03-26 06:22:49Z okayama $";
    }
}
