// $Id: PCTRetReportDataCreator.java 7506 2010-03-12 06:44:21Z shibamoto $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.PrePostFileFilter;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.pcart.system.schedule.PCTSystemInParameter;
import jp.co.daifuku.wms.base.common.AbstractReportDataCreator;
import jp.co.daifuku.wms.base.common.EnvironmentInfoDefine;
import jp.co.daifuku.wms.base.common.ReportDataCreator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.PCTRetHostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.entity.PCTRetHostSend;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;

/**
 * PCT出庫実績データ報告処理を行う。<BR>
 * PCT出庫実績データ報告スケジュールより各処理に合わせたメソッドを持つ。<BR>
 * <BR>
 * Designer : k.bingo <BR>
 * Maker : k.bingo 
 * @version $Revision: 7506 $, $Date: 2010-03-12 15:44:21 +0900 (金, 12 3 2010) $
 * @author admin
 * @author Last commit: $Author: shibamoto $
 */
public class PCTRetReportDataCreator
        extends AbstractReportDataCreator
{
    // ------------------------------------------------------------
    // fields (upper case only)
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // class variables (prefix '$')
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // instance variables (prefix '_')
    // ------------------------------------------------------------
    /** 検索条件用のInParameterを保管 */
    private PCTRetrievalInParameter[] _Param = null;

    /** REPORTID保管 */
    private String _FileInfo = "";

    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * デフォルトコンストラクタ
     */
    public PCTRetReportDataCreator()
    {
        this(null);
    }

    /**
     * @param conn データベースConnection<BR>
     */
    public PCTRetReportDataCreator(Connection conn)
    {
        super(conn);
        this._FileInfo = PCTSystemInParameter.DATA_TYPE_PCTRETRIEVAL_RESULT;
    }

    /**
     * @param conn データベースConnection<BR>
     * @param caller 呼び出し元クラス
     */
    public PCTRetReportDataCreator(Connection conn, Class caller)
    {
        super(conn, caller);
        this._FileInfo = PCTSystemInParameter.DATA_TYPE_PCTRETRIEVAL_RESULT;
    }

    /**
     * @param conn データベースConnection<BR>
     * @param fileInfo REPORT-ID<BR>
     * @param caller 
     */
    public PCTRetReportDataCreator(Connection conn, String fileInfo, Class caller)
    {
        super(conn, caller);
        this._FileInfo = fileInfo;
    }

    /**
     * @param conn データベースConnection<BR>
     * @param fileInfo REPORT-ID<BR>
     * @param inParam 検索条件<BR>
     * @param caller 
     */
    public PCTRetReportDataCreator(Connection conn, String fileInfo, PCTRetrievalInParameter[] inParam, Class caller)
    {
        super(conn, caller);
        this._FileInfo = fileInfo;
        _Param = inParam;
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * 出庫実績データ報告処理を行います。<BR>
     * 
     * @return boolean 出庫報告データの作成に成功した場合は、True、失敗した場合は、Falseを返します。<BR>
     */
    public boolean report()
    {
        return false;
    }

    /**
     * REPORT-IDを返却します。<BR>
     * 
     * @return String REPORT-IDを通知。<BR>
     */
    public String getReportId()
    {
        return this._FileInfo;
    }

    /**
     * PCTRetrievalInParameterを返却します。<BR>
     * 
     * @return String PCTRetrievalInParameter-IDを通知。<BR>
     */
    public PCTRetrievalInParameter[] getInParemeter()
    {
        return this._Param;
    }

    /**
     * PCT実績データ報告作成クラスを生成し通知します。<BR>
     * 
     * @return ReportDataCreator 出庫報告データ作成クラス<BR>
     */
    public ReportDataCreator getReportClass()
    {
        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(_FileInfo);

            if (ArrayUtil.isEmpty(getInParemeter()))
            {
                // 予定単位(集約)の場合
                if (EnvironmentInfoDefine.COLLECT_CONDITION_PLAN.equals(getReportType()))
                {
                    return new PCTRetReportDataCollectionCreator(getConnection(), this._FileInfo,
							PCTRetReportDataCollectionCreator.class);
                }
                // 予定単位(明細)の場合
                else if (EnvironmentInfoDefine.COLLECT_CONDITION_DETAIL.equals(getReportType()))
                {
                    return new PCTRetReportDataDetailCreator(getConnection(), this._FileInfo,
							PCTRetReportDataDetailCreator.class);
                }
                // 作業単位の場合
                else if (EnvironmentInfoDefine.COLLECT_CONDITION_WORK.equals(getReportType()))
                {
                    return new PCTRetReportDataWorkCreator(getConnection(), this._FileInfo,
							PCTRetReportDataWorkCreator.class);
                }
            }
            else
            {
                // 予定単位(集約)の場合
                if (EnvironmentInfoDefine.COLLECT_CONDITION_PLAN.equals(getReportType()))
                {
                    return new PCTRetReportDataCollectionCreator(getConnection(), this._FileInfo,
							getInParemeter(), PCTRetReportDataCollectionCreator.class);
                }
                // 予定単位(明細)の場合
                else if (EnvironmentInfoDefine.COLLECT_CONDITION_DETAIL.equals(getReportType()))
                {
                    return new PCTRetReportDataDetailCreator(getConnection(), this._FileInfo,
							getInParemeter(), PCTRetReportDataDetailCreator.class);
                }
                // 作業単位の場合
                else if (EnvironmentInfoDefine.COLLECT_CONDITION_WORK.equals(getReportType()))
                {
                    return new PCTRetReportDataWorkCreator(getConnection(), this._FileInfo,
							getInParemeter(), PCTRetReportDataWorkCreator.class);
                }
            }
            return null;
        }
        catch (ScheduleException e)
        {
            // 6027009=予期しないエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6027009));
            return null;
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
        // 報告データファイルの環境情報を取得します。
        try
        {
            acquireExchangeEnvironment(getReportId());
        }
        catch (ScheduleException e)
        {
            return false;
        }
        return super.sendReportFile();
    }

    /**
     * 実績報告データの有無をチェックします。<BR>
     * @return boolean 報告ファイルなし:true 報告ファイルあり:false
     */
    public boolean isSendFiles()
    {
        try
        {
            // 報告データファイルの環境情報を取得します。
            acquireExchangeEnvironment(_FileInfo);

            // 一時ファイル保存パスを取得
            File file = new File(getFileDirectory());
            String prefix = getFileName();
            String ext = getExtention();

            FilenameFilter ff = new PrePostFileFilter(prefix, ext);

            String[] tmpFiles = file.list(ff);
            if (ArrayUtil.isEmpty(tmpFiles))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (ScheduleException e)
        {
            // 6027009=予期しないエラーが発生しました。ログを参照してください。
            setMessage(WmsMessageFormatter.format(6027009));
            return false;
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
        // PCTRetPlan(出庫予定情報)・PCTRetHostSend(出庫送信情報)の統計情報を取得します。
        PCTRetPlanHandler planHandler = new PCTRetPlanHandler(getConnection());
        PCTRetHostSendHandler sendHandler = new PCTRetHostSendHandler(getConnection());
        planHandler.getStatics();
        sendHandler.getStatics();
    }

    // ------------------------------------------------------------
    // accessor methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // package methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // protected methods
    // ------------------------------------------------------------
    /**
     * PCT出庫予定情報の実績報告区分を送信済みに更新します。<BR>
     * 
     * @param altKey UPDATEKEY
     * @throws NotFoundException 情報が無かった場合に通知されます。<BR>
     * @throws ReadWriteException データベースエラーが発生した場合に通知されます。<BR>
     */
    protected void updatePCTRetPlanReportFlag(PCTRetPlanAlterKey altKey)
            throws NotFoundException,
                ReadWriteException
    {
        // PCT出庫予定情報を更新
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());

        // 実績報告区分に報告済みをセットします。
        // (更新条件) 予定一意キー：呼び出し元セット済の予定一意キー
        // (更新内容) 実績報告区分：報告済（REPORT_FLAG_REPORT）
        altKey.updateReportFlag(PCTRetPlan.REPORT_FLAG_REPORT);
        altKey.updateLastUpdatePname(getCallerName());

        // PCT出庫予定情報ハンドラの更新処理(modify)を実行する
        handler.modify(altKey);

        return;
    }

    /**
     * PCT出庫実績送信情報の実績報告区分を送信済みに更新します。<BR>
     * 
     * @param altKey PCT出庫実績送信情報の更新キー<BR>
     * @throws NotFoundException 情報が無かった場合に通知されます。<BR>
     * @throws ReadWriteException データベースエラーが発生した場合に通知されます。<BR>
     */
    protected void updatePCTRetHostSendReportFlag(PCTRetHostSendAlterKey altKey)
            throws NotFoundException,
                ReadWriteException
    {
        // 実績報告区分に報告済みをセットします。
        altKey.updateReportFlag(PCTRetHostSend.REPORT_FLAG_REPORT);
        altKey.updateLastUpdatePname(getCallerName());
        PCTRetHostSendHandler handler = new PCTRetHostSendHandler(getConnection());
        handler.modify(altKey);
        return;
    }

    // ------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // utility methods
    // ------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * 
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: PCTRetReportDataCreator.java 7506 2010-03-12 06:44:21Z shibamoto $";
    }
}
