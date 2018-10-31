// $Id: StockReportDBDataCreator.java 7848 2010-04-21 05:16:25Z shibamoto $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.wms.base.common.AbstractReportDBDataCreator;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.ReportDBDataCreator;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.EWNToHostHandler;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.EWNToHost;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.fileentity.ReportStock;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;
import jp.co.daifuku.wms.system.schedule.SystemInParameter;

/**
 * <BR>
 * <CODE>StockReportDBDataCreator</CODE>クラスは、在庫情報報告処理を行うクラスです。<BR>
 * <CODE>AbstractReportDataCreator</CODE>抽象クラスを継承し、必要な処理を実装します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.データ報告処理（<CODE>report(Parameter startParam)</CODE>メソッド）<BR>
 * <BR>
 *   <DIR>
 *   ConnectionオブジェクトとParameterオブジェクトをパラメータとして受け取り、データベースの<BR>
 *   在庫情報ファイル(CSVファイル)を作成します。<BR>
 *   <BR>
 * <BR>
 *   ＜処理詳細＞ <BR>
 *      1.データ件数を初期化します。<BR>
 *        <DIR>
 *        報告データ件数=0<BR>
 *        </DIR>
 *      2.データ報告フラグを宣言します。<BR>
 *        <DIR>
 *        Falseで宣言します。１件でも報告データがある場合にTrueとします。<BR>
 *        </DIR>
 *      3.在庫情報(Dmstock)検索のクラスインスタンス作成<BR>
 *        <DIR>
 *        ･在庫情報(Dmstock)取得 SearchKeyインスタンスを作成します。<BR>
 *        ･在庫情報(Dmstock)取得 Finderインスタンスを作成します。<BR>
 *        </DIR> 
 *      4.検索条件をセットします。
 *        <DIR>
 *        荷主コード<BR>
 *        在庫情報・状態フラグ（在庫）<BR>
 *        </DIR>
 *      5.検索順序をセットします。
 *        <DIR>
 *        荷主コード<BR>
 *        商品コード<BR>
 *        棚Ｎｏ．<BR>
 *        入庫日時<BR>
 *        </DIR>
 *      6.クラスインスタンス作成<BR>
 *        <DIR>
 *        ･DataReportCsvWriterインスタンス作成<BR>
 *           <DIR>
 *           データ報告ＣＳＶを作成するクラスです<BR>
 *           </DIR>
 *        </DIR>
 *      7.在庫情報からFinderを使用して検索結果データを100件単位で取得し、<BR>
 *        データ終了まで、1件ずつ在庫情報ファイル(CSV)を出力します。<BR>
 *      <BR>
 *      8.在庫情報ファイル(CSV)をクローズします。<BR>
 *   <BR>
 *   ＜在庫情報ファイル(CSV)出力 処理概要＞ <BR>
 *      <DIR>
 *      在庫情報ファイル(CSV)の各項目の編集を行い、CSVファイルに出力します。<BR>
 *      1.在庫情報ファイル(CSV)の1行分の各項目を編集します。<BR>
 *        <DIR>
 *        (編集内容)<BR>
 *           <DIR>
 *           商品コード          = 商品コード<BR>
 *           商品名称　　　　　　= 商品名称<BR>
 *           JAN                 = JANコード<BR>
 *           ケースITF           = ケースITF<BR>
 *           ケース入数          = ケース入数<BR>
 *           ロットNo            = ロットNo<BR>
 *           入庫日時            = 入庫日時<BR>
 *           エリア              = エリア<BR>
 *           棚No.               = 棚No.<BR>
 *           在庫数              = 在庫数<BR>
 *           </DIR>
 *        </DIR>
 *           <BR>
 *      </DIR>
 *      2.在庫情報ファイル(CSV)を1行出力します。<BR>
 *        <DIR>
 *        DataReportCsvWriterのwriteLineメソッドを使用して在庫情報データに出力します。<BR>
 *        </DIR>
 *   </DIR> 
 * Designer : Sakashita <BR>
 * Maker : Sakashita <BR>
 * @version $Revision: 7848 $, $Date: 2010-04-21 14:16:25 +0900 (水, 21 4 2010) $
 * @author  $Author: shibamoto $
 */
public class StockReportDBDataCreator
        extends AbstractReportDBDataCreator
{
    //------------------------------------------------------------
    // Class fields
    //------------------------------------------------------------

    /**
     * log出力用
     */
    private static final String BUSINESS_NAME = "business";

    //------------------------------------------------------------
    // Class variables
    //------------------------------------------------------------

    //------------------------------------------------------------
    // Class method
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    private EWNToHostHandler _EWNToHostHandler = null;

    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 7848 $,$Date: 2010-04-21 14:16:25 +0900 (水, 21 4 2010) $");
    }

    //------------------------------------------------------------
    // Constructors
    //------------------------------------------------------------
    /**
     * 1.コンストラクタ<BR>
     * インスタンス変数と処理結果のメッセージを初期化します。<BR>
     * @param  conn データベースとのコネクションオブジェクト
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public StockReportDBDataCreator(Connection conn, Connection customerConn) throws CommonException
    {
        //インスタンス変数に値をセットします。
        super(conn, customerConn);
        _EWNToHostHandler = new EWNToHostHandler(getCustomerConnection()); //for writing to customer's DB

        //処理結果メッセージを初期化します。
        setMessage(null);
    }

    //------------------------------------------------------------
    // Public methods
    //------------------------------------------------------------
    /**
     * 在庫情報報告データを作成します。<BR>
     * 詳しい動作はクラス説明の項を参照してください。<BR>
     * @param startParam 入力パラメータ
     * @throws IOException 入出力の例外が発生した場合に通知されます。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws ScheduleException 
     * @return 報告データ作成処理に成功した場合はtrue、報告データが無い、又は、作成に失敗した場合はfalseを返します。
     */
    public boolean report(Parameter startParam)
            throws IOException,
                ReadWriteException,
                ScheduleException
    {
        // ローカルファイル削除フラグ
        boolean deleteFile = false;

        // Finderを作成します。
        SystemInParameter param = (SystemInParameter)startParam;
        ReportStock rStockEntity = new ReportStock();
        FileHandler handler = AbstractFileHandler.getInstance(rStockEntity);

        //報告データ件数を初期化します。
        setReportCount(0);

        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireFileInfo(StockInParameter.DATA_TYPE_STOCK);

            param.setFilePath(getFileDirectory());
            param.setFileName(getFileName());

            // 報告データ件数を初期化します。
            setReportCount(0);

            // 在庫情報（Dmstock）を検索する為のクラスインスタンスを作成します。
            StockFinder sfinder = new StockFinder(getConnection());

            // 在庫情報と商品マスタ情報の検索キーを作成します。
            StockSearchKey skey = createkey(param);

            //カーソルオープン
            sfinder.open(true);

            // 作成した検索キーで在庫情報をfinderで検索します。
            int count = sfinder.search(skey);
            // 在庫情報が存在しない時
            if (count <= 0)
            {
                // 対象データはありませんでした。
                setMessage("6003011");
                return false;
            }

            //検索データが存在する場合は、CSV出力処理を行います。
            // ローカル変数を宣言します。
            Stock[] stock = null;

            boolean flag = false;

            // 在庫情報報告CSVファイルをオープンします
            while (sfinder.hasNext())
            {
                // 検索結果を100件づつ取得します。
                stock = (Stock[])sfinder.getEntities(RESULT_READ_QTY);

                // 在庫情報報告のCSVファイルへCSVWriteメソッドで出力します。
                for (int i = 0; i < stock.length; i++)
                {

                    if (getReportCount() == 0)
                    {
                        // 初回の出力時にファイルのオープンを行います。
                        try
                        {
                            // 実績ファイル名の生成（一時ファイル保存フォルダに作成する)
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                            String sysTime = df.format(new java.util.Date(System.currentTimeMillis()));

                            // 最終実績ファイル名のセット
                            setResultFileName(getFileName() + sysTime + getExtention());
                            // 一時保存用ファイルに書き込み
                            // ディレクトリが存在しない場合は作成
                            prepareDir(WmsParam.HOSTDATA_LOCAL_PATH + getResultFileName());
                            handler.open(WmsParam.HOSTDATA_LOCAL_PATH, getResultFileName());
                            // ファイルを作成したのでエラー発生時には削除
                            deleteFile = true;
                        }
                        catch (ReadWriteException ex)
                        {
                            //6003019=指定されたフォルダは無効です。
                            setMessage("6003019");
                            return false;
                        }
                        handler.clear();
                    }

                    // 在庫情報データ(CSV)に出力します。
                    if (writeCsv(handler, rStockEntity, stock[i]))
                    {
                        //glm start
                        //rNoPlanStorageEntity now has the host send stuff mapped to entity fields, let's use it again
                        String rec = handler.getRecordFormatter().format(rStockEntity);
                        if (!rec.equals(""))
                        {
                            EWNToHost entity = new EWNToHost();

                            // MESSAGE_DATE
                            entity.setMessageDate(new SysDate());
                            // SEQUENCE_NO
                            entity.setSequenceNo(getNextInReportSequence());
                            // MESSAGE_ID
                            entity.setMessageId(StockInParameter.DATA_TYPE_STOCK);
                            // message DATA
                            entity.setData(rec);

                            _EWNToHostHandler.create(entity);
                            getCustomerConnection().commit();
                        }//glm end
                        flag = true;
                    }

                    // 報告データ有りを記憶します。(1以上の場合、データ有り)
                    // 報告データ件数を加算します。
                    setReportCount(getReportCount() + 1);
                }
            }

            String className = getCaller().getName().toLowerCase();

            if (flag && 0 > className.indexOf(BUSINESS_NAME))
            {
                log_write(getConnection(), EmConstants.OPELOG_CLASS_AUTO_REPORT);
            }

            // 処理が正常に完了したのでファイルは削除しない
            deleteFile = false;

            // 処理結果メッセージを設定します。
            // 6001009=データの書き込みは正常に終了しました。
            setMessage("6001009");
            return true;
        }
        catch (FileNotFoundException e)
        {
            // 6006020=ファイルの入出力エラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
            // 6007031=ファイルの入出力エラーが発生しました。ログを参照してください。
            setMessage("6007031");
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
        catch (ScheduleException e)
        {
            // 6027009=予期しないエラーが発生しました。ログを参照してください。
            setMessage("6027009");
            return false;
        }
        //glm end
        catch (Exception e)
        {
            if (!(e instanceof ReadWriteException))
            {
                // 6006001=予期しないエラーが発生しました。{0}
                RmiMsgLogClient.write(new TraceHandler(6006001, e), this.getClass().getName());
            }
            // 6027009=予期しないエラーが発生しました。ログを参照してください。
            setMessage("6027009");
            return false;
        }
        finally
        {
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
    }

    /**
     * 実績prefix名、ディレクトリを設定します。
     * @throws ReadWriteException データベースアクセスエラー
     */
    public void setResultReportFilePath()
            throws ReadWriteException
    {
        setFileDirectory(getFileDirectory());
        setFileName(getFileName());
    }

    /**
     * 統計情報の取得を行います。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public void statics()
            throws CommonException
    {
        // 取得するテーブルのハンドラーを生成して下さい。
        // DNStock(在庫情報)の統計情報を取得します。
        StockHandler stockHandler = new StockHandler(getConnection());
        stockHandler.getStatics();
    }

    //------------------------------------------------------------
    // Package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // Protected methods
    //------------------------------------------------------------
    /**
     * 在庫情報出力を行います。<BR>
     * 
     * @param handler ファイルハンドラ<BR>
     * @param rStockEntity 出力エンティティ<BR>
     * @param stock 在庫実績送信情報エンティティ<BR>
     * @throws IOException 入出力の例外が発生した場合に通知されます。<BR>
     * @throws ReadWriteException ファイルアクセスでエラーが発生した場合に通知されます。<BR>
     */
    protected boolean writeCsv(FileHandler handler, ReportStock rStockEntity, Stock stock)
            throws IOException,
                ReadWriteException
    {
        // 在庫情報データを項目にセットします。
        // 商品コード
        rStockEntity.setValue(ReportStock.ITEM_CODE, stock.getItemCode());
        // 商品名称
        rStockEntity.setValue(ReportStock.ITEM_NAME, stock.getValue(Item.ITEM_NAME, ""));
        // ＪＡＮ
        rStockEntity.setValue(ReportStock.JAN, stock.getValue(Item.JAN, ""));
        // ケースITF
        rStockEntity.setValue(ReportStock.ITF, stock.getValue(Item.ITF, ""));
        // ケース入数
        rStockEntity.setValue(ReportStock.ENTERING_QTY, stock.getValue(Item.ENTERING_QTY));
        // ロットNo
        rStockEntity.setValue(ReportStock.LOT_NO, stock.getLotNo());
        // 入庫日時
        rStockEntity.setValue(ReportStock.STORAGE_DATE, WmsFormatter.toParamDate(stock.getStorageDate())
                + WmsFormatter.toParamTime(stock.getStorageDate()));
        // エリア
        rStockEntity.setValue(ReportStock.AREA_NO, stock.getAreaNo());
        // 棚No.
        rStockEntity.setValue(ReportStock.LOCATION_NO, stock.getLocationNo());
        // システム在庫数
        rStockEntity.setValue(ReportStock.STOCK_QTY, stock.getValue(Stock.STOCK_QTY));

        // 在庫情報データをCSVに出力します。
        handler.lock();
        handler.create(rStockEntity);
        handler.unLock();

        return true;
    }

    /**
     * 在庫情報と商品マスタ情報の検索キーを作成します。<BR>
     * @param startParam 検索条件パラメータ
     * @return 検索キー
     */
    protected StockSearchKey createkey(Parameter startParam)
    {
        StockSearchKey skey = new StockSearchKey();

        skey.clear();

        // 検索条件をセットします。
        SystemInParameter sparam = (SystemInParameter)startParam;

        // 荷主コード
        if (!StringUtil.isBlank(sparam.getConsignorCode()))
        {
            skey.setConsignorCode(sparam.getConsignorCode());
        }

        //エリア
        if (!WmsParam.ALL_AREA_NO.equals(sparam.getAreaNo()))
        {
            skey.setAreaNo(sparam.getAreaNo());
        }

        //統合条件の指定
        skey.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);
        skey.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);

        // 検索順序をセットします。
        // 荷主コード + 商品コード + ロットNo + エリアNo + 棚No
        skey.setConsignorCodeOrder(true);
        skey.setItemCodeOrder(true);
        skey.setLotNoOrder(true);
        skey.setAreaNoOrder(true);
        skey.setLocationNoOrder(true);

        //項目を取得します。
        //商品コード
        skey.setItemCodeCollect();
        //商品名称
        skey.setCollect(Item.ITEM_NAME);
        //JANコード
        skey.setCollect(Item.JAN);
        //ケースITF
        skey.setCollect(Item.ITF);
        //ケース入数
        skey.setCollect(Item.ENTERING_QTY);
        //ロットNo.
        skey.setLotNoCollect();
        //入庫日時
        skey.setStorageDateCollect();
        //エリアNo.
        skey.setAreaNoCollect();
        //棚No.
        skey.setLocationNoCollect();
        //ｼｽﾃﾑ在庫数
        skey.setStockQtyCollect();

        return skey;
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
        itemLog.add(RetrievalInParameter.DATA_TYPE_RETRIEVAL);

        // ログ出力
        P11LogWriter opeLogWriter = new P11LogWriter(conn);
        opeLogWriter.createOperationLog(user, operationKind, itemLog);
    }

    //------------------------------------------------------------
    // Private methods
    //------------------------------------------------------------
    /**
     * 自動生成されたメソッド
     * @return 特に処理なし
     */
    public ReportDBDataCreator getReportClass()
    {
        return null;
    }

    /**
     * 自動生成されたメソッド
     * @return 特に処理なし
     */
    public boolean report()
    {
        return false;
    }

}
//end of class
