// $Id: StorageReportDataCreator.java 7506 2010-03-12 06:44:21Z shibamoto $
package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.AbstractReportDataCreator;
import jp.co.daifuku.wms.base.common.EnvironmentInfoDefine;
import jp.co.daifuku.wms.base.common.ReportDataCreator;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.entity.StoragePlan;

/**
 * 入庫実績データ報告処理クラスです。<BR>
 * <BR>
 * Designer : nakai<BR>
 * Maker : nakai
 * @version $Revision: 7506 $, $Date: 2010-03-12 15:44:21 +0900 (金, 12 3 2010) $
 * @author  Last commit: $Author: shibamoto $
 */
public class StorageReportDataCreator
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
    public StorageReportDataCreator()
    {
        this(null);
    }

    /**
     * @param conn データベースConnection<BR>
     */
    public StorageReportDataCreator(Connection conn)
    {
        super(conn);
    }

    /**
     * @param conn データベースConnection<BR>
     * @param caller 呼び出し元クラス
     */
    public StorageReportDataCreator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 入庫実績データ報告処理を行います。<BR>
     * @return boolean 入庫報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        return false;
    }

    /**
     * 入庫報告データを作成するクラスを生成し通知します。<BR>
     * @return ReportDataCreator 入庫報告データ作成クラス<BR>
     */
    public ReportDataCreator getReportClass()
    {
        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(StorageInParameter.DATA_TYPE_STORAGE);

            // 予定単位(集約)の場合
            if (EnvironmentInfoDefine.COLLECT_CONDITION_PLAN.equals(getReportType()))
            {
                return new StorageReportDataCollectionCreator(getConnection(),
						StorageReportDataCollectionCreator.class);
            }
            // 予定単位(明細)の場合
            else if (EnvironmentInfoDefine.COLLECT_CONDITION_DETAIL.equals(getReportType()))
            {
                return new StorageReportDataDetailCreator(getConnection(),
						StorageReportDataDetailCreator.class);
            }
            // 作業単位の場合
            else if (EnvironmentInfoDefine.COLLECT_CONDITION_WORK.equals(getReportType()))
            {
                return new StorageReportDataWorkCreator(getConnection(),
						StorageReportDataWorkCreator.class);
            }
            return null;
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
        // DNHostSend(入出庫実績送信情報)の統計情報を取得します。
        HostSendHandler hostSendHandler = new HostSendHandler(getConnection());
        hostSendHandler.getStatics();
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
     * 入庫予定情報の実績報告区分を送信済みに更新します。<BR>
     * @param altKey <BR>
     * @throws NotFoundException 情報が無かった場合に通知されます。<BR>
     * @throws ReadWriteException データベースエラーが発生した場合に通知されます。<BR>
     */
    protected void updateStoragePlanReportFlag(StoragePlanAlterKey altKey)
            throws NotFoundException,
                ReadWriteException
    {
        // 実績報告区分に報告済みをセットします。
        altKey.updateReportFlag(StoragePlan.REPORT_FLAG_REPORT);
        // 最終更新処理クラスをセットします。
        altKey.updateLastUpdatePname(getCallerName());
        StoragePlanHandler handler = new StoragePlanHandler(getConnection());
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
        return "$Id: StorageReportDataCreator.java 7506 2010-03-12 06:44:21Z shibamoto $";
    }
}
