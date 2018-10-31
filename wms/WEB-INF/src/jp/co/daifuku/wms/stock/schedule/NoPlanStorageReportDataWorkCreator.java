// $Id: NoPlanStorageReportDataWorkCreator.java 7506 2010-03-12 06:44:21Z shibamoto $
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
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.HostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.HostSendFinder;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.fileentity.ReportNoPlanStorage;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * このクラスは予定外出庫実績作業単位報告を作成するクラスです。<BR>
 * <BR>
 * Designer : suresh <BR>
 * Maker : suresh
 * @version $Revision: 7506 $, $Date: 2010-03-12 15:44:21 +0900 (金, 12 3 2010) $
 * @author  Last commit: $Author: shibamoto $
 */
public class NoPlanStorageReportDataWorkCreator
        extends NoPlanStorageReportDataCreator
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
    public NoPlanStorageReportDataWorkCreator(Connection conn)
    {
        super(conn);
    }

    /**
     * @param conn データベースConnection
     * @param caller 呼び出し元クラス
     */
    public NoPlanStorageReportDataWorkCreator(Connection conn, Class caller)
    {
        super(conn, caller);
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
            acquireExchangeEnvironment(StockInParameter.DATA_TYPE_NOPLAN_STORAGE);

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
                    // 予定外入庫実績報告ファイルに出力します。
                    if (csvWrite(handler, rNoplanStorageEntity, hSend))
                    {
                        setReportCount(getReportCount() + 1);

                        // 入出庫実績送信情報の実績報告区分を送信済みに更新します。
                        HostSendAlterKey hstAKey = new HostSendAlterKey();
                        hstAKey.setJobNo(hSend.getJobNo());
                        updateHostSendReportFlag(hstAKey);
                        setMessage("6001009");
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
        // 報告データファイルの環境情報を取得します。
        try
        {
            acquireExchangeEnvironment(StockInParameter.DATA_TYPE_NOPLAN_STORAGE);
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
        return "$Id: NoPlanStorageReportDataWorkCreator.java 7506 2010-03-12 06:44:21Z shibamoto $";
    }
}
