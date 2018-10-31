// $Id: InventoryReportDBDataWorkCreator.java 7735 2010-03-26 06:22:49Z okayama $
package jp.co.daifuku.wms.inventorychk.schedule;

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
import jp.co.daifuku.wms.base.dbhandler.InventResultAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventResultFinder;
import jp.co.daifuku.wms.base.dbhandler.InventResultHandler;
import jp.co.daifuku.wms.base.dbhandler.InventResultSearchKey;
import jp.co.daifuku.wms.base.entity.EWNToHost;
import jp.co.daifuku.wms.base.entity.InventResult;
import jp.co.daifuku.wms.base.fileentity.ReportInventory;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;


/**
 * このクラスは棚卸実績の報告内容を作成するクラスです。<br>
 * このクラスは報告データファイルを実績情報から作成します。<br>
 * Designer : suresh <BR>
 * Maker : suresh <BR>
 * @version $Revision: 7735 $, $Date: 2010-03-26 15:22:49 +0900 (金, 26 3 2010) $
 * @author  Last commit: $Author: okayama $
 */


public class InventoryReportDBDataWorkCreator
        extends InventoryReportDBDataCreator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    private EWNToHostHandler _EWNToHostHandler = null;


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     * @param conn データベースConnection
     */
    public InventoryReportDBDataWorkCreator(Connection conn, Connection customerConn)
    {
        super(conn, customerConn);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection());  //for writing to customer's DB
    }

    /**
     * コンストラクタ
     * @param conn データベースConnection
     */
    public InventoryReportDBDataWorkCreator(Connection conn, Connection customerConn, Class caller)
    {
        super(conn, customerConn, caller);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection());  //for writing to customer's DB
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 棚卸実績 作業単位のデータ報告作成処理を行います。<BR>
     * このメソッドは棚卸実績情報から、報告データを作成します。(DNINVENTRESULT)
     * 検索条件 <BR>
     * 報告区分 : 未送信<BR>
     * 報告データを作成後報告区分を送信済にセットする。
     * @return boolean 棚卸報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */

    public boolean report()
    {
        // ローカルファイル削除フラグ
        boolean deleteFile = false;

        ReportInventory rInventory = new ReportInventory();
        //報告データファイル名を指定
        FileHandler handler = AbstractFileHandler.getInstance(rInventory);

        // 報告データ件数を初期化します。
        setReportCount(0);

        // 実績送信情報から報告データを抽出する為のFinderを作成します。
        InventResultFinder irFinder = new InventResultFinder(getConnection());

        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireFileInfo(InventoryInParameter.DATA_TYPE_INVENTORY);

            // 実績送信情報から作業単位で抽出する為の条件を作成します。
            // 取得するカラムを指定します。
            InventResultSearchKey resultSKey = new InventResultSearchKey();
            resultSKey.setCollect(new FieldName(InventResult.STORE_NAME, FieldName.ALL_FIELDS));

            //実績報告区分 = 未送信
            resultSKey.setReportFlag(InventResult.REPORT_FLAG_NOT_REPORT);

            //ソート順　登録日時
            resultSKey.setRegistDateOrder(true);
            irFinder.open(true);

            // 実績送信情報を検索します。
            if (irFinder.search(resultSKey) <= 0)
            {
                // 対象データはありませんでした。
                setMessage("6003011");
                return true;
            }

            while (irFinder.hasNext())
            {
                // 検索結果を100件づつ取得し、ファイルへ出力していく。
                InventResult[] inventResult = (InventResult[])irFinder.getEntities(RESULT_READ_QTY);

                for (InventResult iResult : inventResult)
                {
                    if (getReportCount() == 0)
                    {
                        try
                        {
                            // 実績ファイル名の生成（一時ファイル保存フォルダに作成する)
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                            String sysTime = df.format(new java.util.Date(System.currentTimeMillis()));
                            // ファイル名のセット
                            setResultFileName(getFileName() + sysTime + InventoryInParameter.EXTENSION);
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
                    // 棚卸実績報告ファイルに出力します。
                    if (csvWrite(handler, rInventory, iResult))
                    {
                        setReportCount(getReportCount() + 1);

                        // 棚卸実績送信情報の実績報告区分を送信済みに更新します。
                        InventResultAlterKey iResultAKey = new InventResultAlterKey();
                        iResultAKey.setJobNo(iResult.getJobNo());
                        updateInventoryResultReportFlag(iResultAKey);
                        setMessage("6001009");

                        //glm start
                        //rInventory now has the host send stuff mapped to entity fields, let's use it again
                        String rec = handler.getRecordFormatter().format(rInventory);
                        if (!rec.equals(""))
                        {
                            EWNToHost entity = new EWNToHost();

                            // MESSAGE_DATE
                            entity.setMessageDate(new SysDate());
                            // SEQUENCE_NO
                            entity.setSequenceNo(getNextInReportSequence());
                            // MESSAGE_ID
                            entity.setMessageId(InventoryInParameter.DATA_TYPE_INVENTORY);
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
            irFinder.close();

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
            acquireFileInfo(InventoryInParameter.DATA_TYPE_INVENTORY);
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
     * 棚卸実績報告の出力内容をエンティティにセットし、棚卸実績報告CSVファイルに出力ます。<BR>
     * @param handler ファイルハンドラ<BR>
     * @param rInventory 出力エンティティ<BR>
     * @param inventResult 棚卸実績送信情報エンティティ<BR>
     * @return boolean CSVファイルの出力に成功した場合は、Trueを返します。<BR>
     * @throws ReadWriteException ファイルアクセスでエラーが発生した場合に通知されます。<BR>
     */

    protected boolean csvWrite(FileHandler handler, ReportInventory rInventory, InventResult inventResult)
            throws ReadWriteException
    {

        // 棚卸実績報告CSVファイルの出力内容を編集します。
        rInventory.setValue(ReportInventory.ITEM_CODE, inventResult.getItemCode());
        rInventory.setValue(ReportInventory.ITEM_NAME, inventResult.getItemName());
        rInventory.setValue(ReportInventory.JAN, inventResult.getJan());
        rInventory.setValue(ReportInventory.ITF, inventResult.getItf());
        rInventory.setValue(ReportInventory.ENTERING_QTY, new BigDecimal(inventResult.getEnteringQty()));
        rInventory.setValue(ReportInventory.LOT_NO, inventResult.getLotNo());
        rInventory.setValue(ReportInventory.AREA_NO, inventResult.getAreaNo());
        rInventory.setValue(ReportInventory.LOCATION_NO, inventResult.getLocationNo());
        rInventory.setValue(ReportInventory.STOCK_QTY, new BigDecimal(inventResult.getStockQty()));
        rInventory.setValue(ReportInventory.RESULT_STOCK_QTY, new BigDecimal(inventResult.getResultStockQty()));
        // DFKLOOK WmsFormatter変更により修正
//        rInventory.setValue(ReportInventory.WORK_DATE, WmsFormatter.toParamDateTime(inventResult.getConfirmWorkDate()));
        rInventory.setValue(ReportInventory.WORK_DATE, WmsFormatter.toParamDate(inventResult.getConfirmWorkDate())
                + WmsFormatter.toParamTime(inventResult.getConfirmWorkDate()));
        // DFKLOOK ここまで

        // CSVファイルに出力します。
        handler.lock();
        handler.create(rInventory);
        handler.unLock();

        return true;
    }

    /**
     * 棚卸実績情報の実績報告区分を送信済みに更新します。<BR>
     * @param altKey 棚卸実績情報の更新キー<BR>
     * @throws NotFoundException 情報が無かった場合に通知されます。<BR>
     * @throws ReadWriteException データベースエラーが発生した場合に通知されます。<BR>
     */
    protected void updateInventoryResultReportFlag(InventResultAlterKey altKey)
            throws NotFoundException,
                ReadWriteException
    {
        // 実績報告区分に報告済みをセットします。
        altKey.updateReportFlag(InventResult.REPORT_FLAG_REPORT);
        // 最終更新処理名をセットします。
        altKey.updateLastUpdatePname(getCallerName());
        InventResultHandler handler = new InventResultHandler(getConnection());
        handler.modify(altKey);

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
        return "$Id: InventoryReportDBDataWorkCreator.java 7735 2010-03-26 06:22:49Z okayama $";
    }
}
