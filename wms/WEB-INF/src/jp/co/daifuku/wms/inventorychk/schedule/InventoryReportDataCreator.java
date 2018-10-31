// $Id: InventoryReportDataCreator.java 7854 2010-04-22 02:46:11Z kishimoto $
package jp.co.daifuku.wms.inventorychk.schedule;

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
 * このクラスは棚卸実績の報告内容を作成するクラスです。<BR>
 * このクラスは報告データファイルの環境情報を取得し、棚卸実績<BR>
 * の作業単位報告クラスを返します。<BR>
 * <BR>
 * Designer : suresh <BR>
 * Maker : suresh 
 * @version $Revision: 7854 $, $Date: 2010-04-22 11:46:11 +0900 (木, 22 4 2010) $
 * @author  Last commit: $Author: kishimoto $
 */
public class InventoryReportDataCreator
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
    public InventoryReportDataCreator()
    {
        this(null);
    }

    /**
     * 
     * @param conn データベースConnection
     */
    public InventoryReportDataCreator(Connection conn)
    {
        super(conn);
    }

    /**
     * @param conn データベースConnection<BR>
     * @param caller 呼び出し元クラス
     */
    public InventoryReportDataCreator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /** 
     * 棚卸実績実績データ報告処理を行います。<BR>
     * @return boolean 棚卸実績報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        return false;
    }

    /** 
     * 棚卸実績報告データを作成するクラスを生成し通知します。<BR>
     * @return ReportDataCreator 棚卸実績報告データ作成クラス<BR>
     * 
     */
    public ReportDataCreator getReportClass()
    {
        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(InventoryInParameter.DATA_TYPE_INVENTORY);
            //作業単位報告クラスを返す。
            return new InventoryReportDataWorkCreator(getConnection(),
					InventoryReportDataWorkCreator.class);
        }
        catch (ScheduleException e)
        {
            // 6027009=予期しないエラーが発生しました。ログを参照してください。
            setMessage("6027009");
            return null;
        }
    }

    /**
     * 統計情報の取得を行います。
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     */
    public void statics()
            throws CommonException
    {
        // 取得するテーブルのハンドラーを生成して下さい。
        // 棚卸実績の報告ではResultテーブルが対象のため、負荷を考慮し何もしません。
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
        return "$Id: InventoryReportDataCreator.java 7854 2010-04-22 02:46:11Z kishimoto $";
    }
}
