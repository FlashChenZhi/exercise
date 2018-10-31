// $Id: NoPlanRetrievalReportDataCreator.java 526 2008-10-22 06:45:56Z kishimoto $
package jp.co.daifuku.wms.stock.schedule;

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
import jp.co.daifuku.wms.base.common.AbstractReportDataCreator;
import jp.co.daifuku.wms.base.common.ReportDataCreator;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanHandler;
import jp.co.daifuku.wms.base.entity.ReStoringPlan;

/**
 * このクラスは予定外入出庫実績の報告内容を作成するクラスです。<BR>
 * <BR>
 * Designer : Yoshida <BR>
 * Maker : Yoshida
 * @version $Revision: 526 $, $Date: 2008-10-22 15:45:56 +0900 (水, 22 10 2008) $
 * @author  Last commit: $Author: kishimoto $
 */
public class FaNoPlanInOutReportDataCreator
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
    public FaNoPlanInOutReportDataCreator()
    {
        this(null);
    }

    /**
     * コンストラクタ
     * @param conn データベースConnection
     */
    public FaNoPlanInOutReportDataCreator(Connection conn)
    {
        super(conn);
        setMessage(null);
    }

    /**
     * コンストラクタ
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    protected FaNoPlanInOutReportDataCreator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 予定外入出庫実績データ報告処理を行います。<BR>
     * @return boolean 予定外入出庫報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        return false;
    }

    /**
     * 予定外入出庫報告データを作成するクラスを生成し通知します。<BR>
     * @return ReportDataCreator 予定外入出庫報告データ作成クラス<BR>
     */
    public ReportDataCreator getReportClass()
    {
        // 作業単位報告クラスを返す。
        return new FaNoPlanInOutReportDataWorkCreator(getConnection(),
				FaNoPlanInOutReportDataWorkCreator.class);
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
     * 再入庫予定情報の実績報告区分を送信済みに更新します。<BR>
     * @param altKey <BR>
     * @throws NotFoundException 情報が無かった場合に通知されます。<BR>
     * @throws ReadWriteException データベースエラーが発生した場合に通知されます。<BR>
     */
    protected void updateReStoringPlanReportFlag(ReStoringPlanAlterKey altKey)
            throws NotFoundException,
                ReadWriteException
    {
        // 実績報告区分に報告済みをセットします。
        altKey.updateReportFlag(ReStoringPlan.REPORT_FLAG_REPORT);
        // 最終更新処理名をセットします。
        altKey.updateLastUpdatePname(getCallerName());
        ReStoringPlanHandler handler = new ReStoringPlanHandler(getConnection());
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
        return "$Id: NoPlanRetrievalReportDataCreator.java 526 2008-10-22 06:45:56Z kishimoto $";
    }

}
