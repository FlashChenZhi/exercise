// $Id: StockReportDataCreator.java 5272 2009-10-26 10:21:03Z kumano $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.wms.base.common.DsNumberDefine;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.fileentity.ReportStock;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;
import jp.co.daifuku.wms.system.schedule.SystemInParameter;

/**
 * このクラスは在庫報告を作成するクラスです。<br>
 * Designer : Yoshida <BR>
 * Maker : Yoshida <BR>
 * @version $Revision: 2821 $, $Date: 2009-01-20 17:49:24 +0900 (火, 20 1 2009) $
 * @author  Last commit: $Author: kishimoto $
 */
public class StockReportDataWorkCreator
        extends StockReportDataCreator
{
    //------------------------------------------------------------
    // Class fields
    //------------------------------------------------------------
    /** log出力用 */
    private static final String BUSINESS_NAME = "business";

    //------------------------------------------------------------
    // Class variables
    //------------------------------------------------------------

    //------------------------------------------------------------
    // Class method
    //------------------------------------------------------------
    /**
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 5272 $,$Date: 2009-10-26 19:21:03 +0900 (月, 26 10 2009) $");
    }

    //------------------------------------------------------------
    // Constructors
    //------------------------------------------------------------
    /**
     * 1.コンストラクタ<BR>
     * インスタンス変数と処理結果のメッセージを初期化します。<BR>
     * @param  conn データベースとのコネクションオブジェクト
     * @throws CommonException 
     */
    public StockReportDataWorkCreator(Connection conn) throws CommonException
    {
        super(conn);
    }

    /**
     * 1.コンストラクタ<BR>
     * インスタンス変数と処理結果のメッセージを初期化します。<BR>
     * @param  conn データベースとのコネクションオブジェクト
     * @param caller 呼び出し元クラス
     * @throws CommonException 
     */
    public StockReportDataWorkCreator(Connection conn, Class caller) throws CommonException
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // Public methods
    //------------------------------------------------------------
    /**
     * 在庫のデータ報告作成処理を行います。<BR>
     * @return boolean 在庫データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        // ローカルファイル削除フラグ
        boolean deleteFile = false;

        // Finderを作成します。
        ReportStock rStockEntity = new ReportStock();
        FileHandler handler = AbstractFileHandler.getInstance(rStockEntity);

        //報告データ件数を初期化します。
        setReportCount(0);

        try
        {
            // 環境設定ファイルから格納フォルダ、ファイル名を取得
            setResultReportFilePath();

            // 報告データ件数を初期化します。
            setReportCount(0);

            // 在庫情報（Dmstock）を検索する為のクラスインスタンスを作成します。
            StockFinder sfinder = new StockFinder(getConnection());

            // 在庫情報と商品マスタ情報の検索キーを作成します。
            SystemInParameter inParam = new SystemInParameter();
            inParam.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
            inParam.setAreaNo(WmsParam.ALL_AREA_NO);
            StockSearchKey skey = createkey(inParam);

            //カーソルオープン
            sfinder.open(true);

            // 作成した検索キーで在庫情報をfinderで検索します。
            int count = sfinder.search(skey);

            // 在庫情報が存在しない時
            if (count <= 0)
            {
                // 対象データはありませんでした。
                setMessage("6003011");
                return true;
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
                    writeCsv(handler, rStockEntity, stock[i]);

                    // 報告データ有りを記憶します。(1以上の場合、データ有り)
                    // 報告データ件数を加算します。
                    setReportCount(getReportCount() + 1);
                    flag = true;
                }
            }

            // 処理が正常に完了したのでファイルは削除しない
            deleteFile = false;

            String className = getCaller().getName().toLowerCase();
            if (flag && 0 > className.indexOf(BUSINESS_NAME))
            {
                log_write(getConnection(), EmConstants.OPELOG_CLASS_AUTO_REPORT);
            }

            // 処理結果メッセージを設定します。
            // 6001009=データの書き込みは正常に終了しました。
            setMessage("6001009");
            return true;
        }
        catch (ReadWriteException e)
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
        catch (IOException e)
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
            setResultReportFilePath();
        }
        catch (ScheduleException e)
        {
            return false;
        }
        return super.sendReportFile();
    }

    //------------------------------------------------------------
    // Package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // Protected methods
    //------------------------------------------------------------

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
    // Private methods
    //------------------------------------------------------------
}
//end of class
