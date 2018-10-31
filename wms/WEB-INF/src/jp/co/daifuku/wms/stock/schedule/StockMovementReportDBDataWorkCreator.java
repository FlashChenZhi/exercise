// $Id: StockMovementReportDBDataWorkCreator.java 7735 2010-03-26 06:22:49Z okayama $
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
import jp.co.daifuku.wms.base.dbhandler.MoveResultAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MoveResultFinder;
import jp.co.daifuku.wms.base.dbhandler.MoveResultHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveResultSearchKey;
import jp.co.daifuku.wms.base.entity.EWNToHost;
import jp.co.daifuku.wms.base.entity.MoveResult;
import jp.co.daifuku.wms.base.fileentity.ReportMove;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * このクラスは在庫移動実績の報告内容を作成するクラスです。<br>
 * このクラスは在庫移動報告データファイルを実績情報はら作成します。
 * Designer : suresh <BR>
 * Maker : suresh <BR>
 * @version $Revision: 7735 $, $Date: 2010-03-26 15:22:49 +0900 (金, 26 3 2010) $
 * @author  Last commit: $Author: okayama $
 */
public class StockMovementReportDBDataWorkCreator
        extends StockMovementReportDBDataCreator
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

    private MoveResultHandler _MoveResultandler = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     * @param conn データベースConnection
     */
    public StockMovementReportDBDataWorkCreator(Connection conn, Connection customerConn)
    {
        super(conn, customerConn);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB
        _MoveResultandler = new MoveResultHandler(getConnection());
    }

    /**
     * コンストラクタ
     * @param conn データベースConnection
     */
    public StockMovementReportDBDataWorkCreator(Connection conn, Connection customerConn, Class caller)
    {
        super(conn, customerConn, caller);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB
        _MoveResultandler = new MoveResultHandler(getConnection());
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 移動実績 作業単位のデータ報告作成処理を行います。<BR>
     * このメソッドは移動実績情報からデータを作成します。(DNMoveResult)
     * 検索条件 <BR>
     * 報告区分 : 未送信<BR>
     * 作業区分 : 移動
     * 報告データを作成後報告区分を送信済にセットする。
     * @return boolean 移動報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        // ローカルファイル削除フラグ
        boolean deleteFile = false;

        ReportMove rMove = new ReportMove();
        //報告データファイル名を指定
        FileHandler handler = AbstractFileHandler.getInstance(rMove);

        // 報告データ件数を初期化します。
        setReportCount(0);

        // 移動実績情報から報告データを抽出する為のFinderを作成します。
        MoveResultFinder mRFinder = new MoveResultFinder(getConnection());

        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireFileInfo(StockInParameter.DATA_TYPE_MOVEMENT);

            // 移動実績情報から作業単位で抽出する為の条件を作成します。
            // 取得するカラムを指定します。
            MoveResultSearchKey sendSKey = new MoveResultSearchKey();
            sendSKey.setCollect(new FieldName(MoveResult.STORE_NAME, FieldName.ALL_FIELDS));

            //実績報告区分 = 未送信
            sendSKey.setReportFlag(MoveResult.REPORT_FLAG_NOT_REPORT);
            //作業区分 = 移動
            sendSKey.setJobType(MoveResult.JOB_TYPE_MOVEMENT);

            //ソート順　登録日時
            sendSKey.setRegistDateOrder(true);
            mRFinder.open(true);

            // 実績送信情報を検索します。
            if (mRFinder.search(sendSKey) <= 0)
            {
                // 対象データはありませんでした。
                setMessage("6003011");
                return true;
            }

            while (mRFinder.hasNext())
            {
                // 検索結果を100件づつ取得し、ファイルへ出力していく。
                MoveResult[] moveResult = (MoveResult[])mRFinder.getEntities(100);

                for (MoveResult mResult : moveResult)
                {
                    if (getReportCount() == 0)
                    {
                        // 初回の出力時にファイルのオープンを行います。
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
                    // 移動実績報告ファイルに出力します。
                    if (csvWrite(handler, rMove, mResult))
                    {
                        setReportCount(getReportCount() + 1);

                        // 移動実績情報の実績報告区分を送信済みに更新します。
                        MoveResultAlterKey mResultAKey = new MoveResultAlterKey();
                        mResultAKey.setJobNo(mResult.getJobNo());
                        updateMoveResultReportFlag(mResultAKey);
                        setMessage("6001009");

                        //glm start
                        //rNoPlanStorageEntity now has the host send stuff mapped to entity fields, let's use it again
                        String rec = handler.getRecordFormatter().format(rMove);
                        if (!rec.equals(""))
                        {
                            EWNToHost entity = new EWNToHost();

                            // MESSAGE_DATE
                            entity.setMessageDate(new SysDate());
                            // SEQUENCE_NO
                            entity.setSequenceNo(getNextInReportSequence());
                            // MESSAGE_ID
                            entity.setMessageId(StockInParameter.DATA_TYPE_MOVEMENT);
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
            mRFinder.close();

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
            acquireFileInfo(StockInParameter.DATA_TYPE_MOVEMENT);
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
     * 移動実績報告の出力内容をエンティティにセットし、移動実績報告CSVファイルに出力ます。<BR>
     * @param handler ファイルハンドラ<BR>
     * @param rMove 出力エンティティ<BR>
     * @param moveResult 移動実績送信情報エンティティ<BR>
     * @return boolean CSVファイルの出力に成功した場合は、Trueを返します。<BR>
     * @throws ReadWriteException ファイルアクセスでエラーが発生した場合に通知されます。<BR>
     */
    protected boolean csvWrite(FileHandler handler, ReportMove rMove, MoveResult moveResult)
            throws ReadWriteException
    {
        // 移動実績報告CSVファイルの出力内容を編集します。
        rMove.setValue(ReportMove.ITEM_CODE, moveResult.getItemCode());
        rMove.setValue(ReportMove.ITEM_NAME, moveResult.getItemName());
        rMove.setValue(ReportMove.JAN, moveResult.getJan());
        rMove.setValue(ReportMove.ITF, moveResult.getItf());
        rMove.setValue(ReportMove.ENTERING_QTY, new BigDecimal(moveResult.getEnteringQty()));
        rMove.setValue(ReportMove.LOT_NO, moveResult.getLotNo());
        rMove.setValue(ReportMove.RETRIEVAL_AREA_NO, moveResult.getRetrievalAreaNo());
        rMove.setValue(ReportMove.RETRIEVAL_LOCATION_NO, moveResult.getRetrievalLocationNo());
        rMove.setValue(ReportMove.STORAGE_AREA_NO, moveResult.getStorageAreaNo());
        rMove.setValue(ReportMove.STORAGE_LOCATION_NO, moveResult.getStorageLocationNo());
        rMove.setValue(ReportMove.STORAGE_RESULT_QTY, new BigDecimal(moveResult.getStorageResultQty()));
        rMove.setValue(ReportMove.STORAGE_WORK_DATE, WmsFormatter.toParamDate(moveResult.getStorageWorkDate())
                + WmsFormatter.toParamTime(moveResult.getStorageWorkDate()));
        rMove.setValue(ReportMove.USER_ID, moveResult.getStorageUserId());
        rMove.setValue(ReportMove.TERMINAL_NO, moveResult.getStorageTerminalNo());

        // CSVファイルに出力します。
        handler.lock();
        handler.create(rMove);
        handler.unLock();

        return true;
    }

    /**
     * 移動実績情報の実績報告区分を送信済みに更新します。<BR>
     * @param altKey 移動実績情報の更新キー<BR>
     * @throws NotFoundException 情報が無かった場合に通知されます。<BR>
     * @throws ReadWriteException データベースエラーが発生した場合に通知されます。<BR>
     */
    protected void updateMoveResultReportFlag(MoveResultAlterKey altKey)
            throws NotFoundException,
                ReadWriteException
    {
        // 実績報告区分に報告済みをセットします。
        altKey.updateReportFlag(MoveResult.REPORT_FLAG_REPORT);
        // 最終更新処理名をセットします。
        altKey.updateLastUpdatePname(getCallerName());
        _MoveResultandler.modify(altKey);

        return;
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
        return "$Id: StockMovementReportDBDataWorkCreator.java 7735 2010-03-26 06:22:49Z okayama $";
    }
}
