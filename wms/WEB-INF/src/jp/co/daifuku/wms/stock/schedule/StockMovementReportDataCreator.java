// $Id: StockMovementReportDataCreator.java 7854 2010-04-22 02:46:11Z kishimoto $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.AbstractReportDataCreator;
import jp.co.daifuku.wms.base.common.ReportDataCreator;


/**
 * このクラスは在庫移動の報告内容を作成するクラスです。<BR>
 * このクラスは報告データファイルの環境情報を取得し、在庫移動<BR>
 * の作業単位報告クラスを返します。<BR>
 * <BR>
 * Designer : suresh <BR>
 * Maker : suresh
 * @version $Revision: 7854 $, $Date: 2010-04-22 11:46:11 +0900 (木, 22 4 2010) $
 * @author  Last commit: $Author: kishimoto $
 */
public class StockMovementReportDataCreator
        extends AbstractReportDataCreator
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
     * デフォルトコンストラクタ
     */
    public StockMovementReportDataCreator()
    {
        this(null);
    }

    /**
     * @param conn データベースConnection
     */
    public StockMovementReportDataCreator(Connection conn)
    {
        super(conn);
    }

    /**
     * @param conn データベースConnection
     * @param caller 呼び出し元クラス
     */
    public StockMovementReportDataCreator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 在庫移動実績データ報告処理を行います。<BR>
     * 
     * @return boolean 在庫移動報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。
     */
    public boolean report()
    {
        return false;
    }

    /**
     * 在庫移動報告データを作成するクラスを生成し通知します。<BR>
     * 
     * @return ReportDataCreator 在庫移動報告データ作成クラス
     */
    public ReportDataCreator getReportClass()
    {
        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(StockInParameter.DATA_TYPE_MOVEMENT);
            //作業単位報告クラスを返す。
            return new StockMovementReportDataWorkCreator(getConnection(),
					StockMovementReportDataWorkCreator.class);
        }
        catch (ScheduleException e)
        {
            // 6027009=予期しないエラーが発生しました。ログを参照してください。
            setMessage("6027009");
            return null;
        }
    }

    /**
     * 統計情報の取得を行います。<BR>
     * 
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public void statics()
            throws CommonException
    {
        // 取得するテーブルのハンドラーを生成して下さい。
        // 移動実績の報告ではResultテーブルが対象のため、負荷を考慮し何もしません。
        return;
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
        return "$Id: StockMovementReportDataCreator.java 7854 2010-04-22 02:46:11Z kishimoto $";
    }
}
