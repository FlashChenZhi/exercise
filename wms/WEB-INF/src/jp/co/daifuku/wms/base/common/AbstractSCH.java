//$Id: AbstractSCH.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.base.common;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.da.AbstractScheduler;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.db.BasicDatabaseFinder;

/**
 * Designer : K.Mukai <BR>
 * Maker : T.Kishimoto <BR>
 * 
 * WmsSchedulerインターフェースを実装し、このインターフェースの実装に必要な処理を実装します。<BR>
 * スケジュール処理の個別の振る舞いについては、必要に応じ各クラスでオーバーライドし実装してください。<BR>
 * また、スケジュール処理で共通の処理についてもこのクラスに実装します。<BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/07/15</TD><TD>Y.Okamura</TD><TD>Ver2.4 新規作成</TD></TR>
 * <TR><TD>2007/02/16</TD><TD>T.Kishimoto</TD><TD>Ver3.0対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  $Author: kitamaki $
 */
public abstract class AbstractSCH
        extends AbstractScheduler
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * バラ総数の最大値（画面表示用にカンマ編集したもの）
     */
    public static final String MAX_TOTAL_QTY_DISP = WmsFormatter.getNumFormat(WmsParam.MAX_TOTAL_QTY);

    /**
     * リストセル（ためうちエリア）最大表示件数（画面表示用にカンマ編集したもの）
     */
    public static final String MAX_NUMBER_OF_DISP_DISP = WmsFormatter.getNumFormat(WmsParam.MAX_NUMBER_OF_DISP);

    /**
     * 在庫数（バラ）の最大値（画面表示用にカンマ編集したもの）
     */
    public static final String MAX_STOCK_QTY_DISP = WmsFormatter.getNumFormat(WmsParam.MAX_STOCK_QTY);

    /**
     * NGが発生したリストセルの行No.
     */
    private int _NgCellRow = 0;
    
    /**
     * WareNaviSystemManager
     */
    private WarenaviSystemController _SysController = null;

    
    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;


    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと呼び出しもとクラスを指定してインスタンスを生成します。
     * 
     * @param conn データベースコネクション
     * @param parent 呼び出し元クラス<br>
     *   null が指定された場合は、自身が設定されます。
     * @param locale 対象ロケール<br>
     *   null が指定された場合は、デフォルト・ロケールが設定されます。
     * @param ui ユーザ情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public AbstractSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui)
            throws CommonException
    {
        super(conn, parent, locale, ui);
        if (conn != null)
        {
            // WarenaviSystemControllerのインスタンス化
            _SysController = new WarenaviSystemController(getConnection(), getClass());
        }
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 開始日時と終了日時の入力チェックを行います。<BR>
     * 入力チェックと大小チェックを行います。<BR>
     * 入力が正しかった場合はtrue、不正だった場合はfalseを返します。<BR>
     * @param startDate 開始日付
     * @param startTime 開始時間
     * @param endDate 終了日付
     * @param endTime 終了時間
     * @return true：正 false：不正
     */
    public boolean checkDateTime(String startDate, String startTime, String endDate, String endTime)
    {
        // 時刻が入力されている場合、日付の入力チェックを行う。
        if (!StringUtil.isBlank(startTime))
        {
            if (StringUtil.isBlank(startDate))
            {
                // 6023009=時刻入力時は、日付入力も行ってください。
                setMessage("6023009");
                return false;
            }
        }

        if (!StringUtil.isBlank(endTime))
        {
            if (StringUtil.isBlank(endDate))
            {
                // 6023009=時刻入力時は、日付入力も行ってください。
                setMessage("6023009");
                return false;
            }
        }

        // 開始日時と終了日時のどちらかが入力されていない場合はtrueを返す。
        if (!StringUtil.isBlank(startDate))
        {
            if (StringUtil.isBlank(endDate))
            {
                return true;
            }
        }

        if (!StringUtil.isBlank(endDate))
        {
            if (StringUtil.isBlank(startDate))
            {
                return true;
            }
        }

        // 開始日付と終了日付の大小チェック
        if (startDate.compareTo(endDate) < 0)
        {
            // 開始日付が終了日付より小さい場合、trueを返す。
            return true;
        }
        else
        {
            String stime = startTime;
            String etime = endTime;

            // 開始日付と終了日付が同じ場合は、時間の大小チェックを行う。
            if (startDate.equals(endDate))
            {
                if (StringUtil.isBlank(startTime))
                {
                    // 開始時間の入力がない場合、00:00:00とする。
                    stime = "00:00:00";
                }

                if (StringUtil.isBlank(endTime))
                {
                    // 終了時間の入力がない場合、23:59:59とする。
                    etime = "23:59:59";
                }

                if (stime.compareTo(etime) <= 0)
                {
                    // 開始時間が終了時間以下の場合、trueを返す。
                    return true;
                }
            }
        }

        // 6023010=検索開始日時は、検索終了日時より前の日付にしてください。
        setMessage("6023010");
        return false;
    }

    // Protected methods ---------------------------------------------
    /**
     * WarenaviSystemControllerより日次更新処理中かどうかのチェックを行います。<BR>
     * 日次更新中の場合はtrue、日次更新中でない場合はfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @return true：日次更新処理中 false：日次更新中でない
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean isDailyUpdate()
            throws CommonException
    {
        // WarenaviSystemControllerより日時更新チェックを行う。
        if (_SysController.isDailyUpdating())
        {
            // 6023011 = 日次更新を行っているため、処理できません。
            setMessage("6023011");
            return true;
        }
        return false;
    }

    /**
     * WarenaviSystemControllerより予定データ取り込み中かどうかのチェックを行います。<BR>
     * 取り込み中の場合はtrue、取り込み中でない場合はfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @return true：予定データ取り込み中 false：予定データ取り込み中でない
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean isLoadData()
            throws CommonException
    {
        // WarenaviSystemControllerより予定データ取り込みチェックを行う。
        if (_SysController.isDataLoading())
        {
            // 6023012=データ取込み中のため、処理できません。
            setMessage("6023012");
            return true;
        }
        return false;
    }

    /**
     * WarenaviSystemControllerより報告データ作成中かどうかのチェックを行います。<BR>
     * 報告中の場合はtrue、報告中でない場合はfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @return true：報告データ作成中 false：報告データ作成中でない
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean isReportData()
            throws CommonException
    {
        // WarenaviSystemControllerより報告データ作成チェックを行う。
        if (_SysController.isDataReporting())
        {
            // 6023013=データ報告中のため、処理できません。
            setMessage("6023013");
            return true;
        }
        return false;
    }

    /**
     * WarenaviSystemControllerより出庫引当中かどうかのチェックを行います。<BR>
     * 出庫引当中の場合はtrue、出庫引当中でない場合はfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @return true：出庫引当中 false：出庫引当中でない
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean isRetrievalAllocate()
            throws CommonException
    {
        // WarenaviSystemControllerより出庫引当中チェックを行う。
        if (_SysController.isRetrievalAllocating())
        {
            // 6023101=出庫引当中のため、処理できません。
            setMessage("6023101");
            return true;
        }
        return false;
    }

    /**
     * WarenaviSystemControllerより自動でのホスト通信が有効かどうかを返します。<BR>
     * 有効の場合はtrue、無効の場合はfalseを返します。<BR>
     * 自動でのホスト通信処理から使用される為、<CODE>getMessage()</CODE>メソッドを使用して結果を取得することはできません。<BR>
     * @return true：自動ホスト通信有効 false：自動ホスト通信無効
     */
    protected boolean isHostCommEnabled()
    {
        return _SysController.isHostEnabled();
    }

    /**
     * WarenaviSystemControllerより搬送データクリア中かどうかのチェックを行います。<BR>
     * 搬送データクリア中の場合はtrue、搬送データクリア中でない場合はfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @return true：搬送データクリア中 false：搬送データクリア中でない
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean isAllocationClear()
            throws CommonException
    {
        // WarenaviSystemControllerより搬送データクリアチェックを行う。
        if (_SysController.isAllocationClear())
        {
            // 6023102 = 搬送データクリア中のため、処理できません。
            setMessage("6023102");
            return true;
        }
        return false;
    }
    
    /**
     * WarenaviSystemControllerより終了処理中かどうかのチェックを行います。<BR>
     * 終了処理中の場合はtrue、終了処理中でない場合はfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @return true：終了処理中 false：終了処理中でない
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean isEndProcessing()
            throws CommonException
    {
        // WarenaviSystemControllerより終了処理中チェックを行う。
        if (_SysController.isEndProcessing())
        {
            // 6023322 = 終了処理を行っているため、処理できません。
            setMessage("6023322");
            return true;
        }
        return false;
    }

    /**
     * 予定データ取り込み中フラグを起動中に更新します。<BR>
     * 正常に更新できた場合はtrue、更新できなかった場合はfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @return true：更新成功 false：更新失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean doLoadStart()
            throws CommonException
    {
        try
        {
            // WarenaviSystemControllerで予定データ取り込みフラグ中を起動中にする。
            _SysController.updateDataLoading(true);

            return true;
        }
        catch (NotFoundException e)
        {
            // 6023012=データ取込み中のため、処理できません。
            setMessage("6023012");
            return false;
        }
    }

    /**
     * 予定データ取り込み中フラグを停止中に更新します。<BR>
     * 正常に更新できた場合はtrue、更新できなかった場合はfalseを返します。<BR>
     * @return true：更新成功 false：更新失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean doLoadEnd()
            throws CommonException
    {
        try
        {
            // WarenaviSystemControllerで予定データ取り込み中フラグを停止中にする。
            _SysController.updateDataLoading(false);

            return true;
        }
        catch (NotFoundException e)
        {
            return false;
        }
    }

    /**
     * 報告データ作成中フラグを起動中に更新します。<BR>
     * 正常に更新できた場合はtrue、更新できなかった場合はfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @return true：更新成功 false：更新失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean doReportStart()
            throws CommonException
    {
        try
        {
            // WarenaviSystemControllerで報告データ作成中フラグを起動中にする。
            _SysController.updateDataReporting(true);

            return true;
        }
        catch (NotFoundException e)
        {
            // 6023013=データ報告中のため、処理できません。
            setMessage("6023013");
            return false;
        }
    }

    /**
     * 報告データ作成中フラグを停止中に更新します。<BR>
     * 正常に更新できた場合はtrue、更新できなかった場合はfalseを返します。<BR>
     * @return true：更新成功 false：更新失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean doReportEnd()
            throws CommonException
    {
        try
        {
            // WarenaviSystemControllerで報告データ作成中フラグを停止中にする。
            _SysController.updateDataReporting(false);

            return true;
        }
        catch (NotFoundException e)
        {
            return false;
        }
    }

    /**
     * 出庫引当中フラグを起動中に更新します。<BR>
     * 正常に更新できた場合はtrue、更新できなかった場合はfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @return true：更新成功 false：更新失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean doRetrievalAllocateStart()
            throws CommonException
    {
        try
        {
            // WarenaviSystemControllerで出庫引当中フラグを起動中にする。
            _SysController.updateRetrievalAllocating(true);

            return true;
        }
        catch (NotFoundException e)
        {
            // 6023101=出庫引当中のため、処理できません。
            setMessage("6023101");
            return false;
        }
    }

    /**
     * 出庫引当中フラグを停止中に更新します。<BR>
     * 正常に更新できた場合はtrue、更新できなかった場合はfalseを返します。<BR>
     * @return true：更新成功 false：更新失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean doRetrievalAllocateEnd()
            throws CommonException
    {
        try
        {
            // WarenaviSystemControllerで出庫引当中フラグを停止中にする。
            _SysController.updateRetrievalAllocating(false);

            return true;
        }
        catch (NotFoundException e)
        {
            return false;
        }
    }

    /**
     * 搬送データクリアフラグを起動中に更新します。<BR>
     * 正常に更新できた場合はtrue、更新できなかった場合はfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @return true：更新成功 false：更新失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean doAllocationClearStart()
            throws CommonException
    {
        try
        {
            // WarenaviSystemControllerで搬送データクリアフラグを起動中にする。
            _SysController.updateAllocationClear(true);

            return true;
        }
        catch (NotFoundException e)
        {
            // TODO 6023102=搬送データクリアのため、処理できません。
            setMessage("6023102");
            return false;
        }
    }

    /**
     * 搬送データクリアフラグを停止中に更新します。<BR>
     * 正常に更新できた場合はtrue、更新できなかった場合はfalseを返します。<BR>
     * @return true：更新成功 false：更新失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean doAllocationClearEnd()
            throws CommonException
    {
        try
        {
            // WarenaviSystemControllerで搬送データクリアフラグを停止中にする。
            _SysController.updateAllocationClear(false);

            return true;
        }
        catch (NotFoundException e)
        {
            return false;
        }
    }
    
    
    /**
     * WarenaviSystemControllerより処理を行えるかどうかのチェックを行います。<BR>
     * 処理が可能な場合はtrue、処理が可能でない場合はfalseを返します。<BR>
     * @return true：処理可 false：処理不可
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean canStart()
            throws CommonException
    {
        // 日次更新チェックを行う。
        if (isDailyUpdate())
        {
            return false;
        }
        
        // 終了処理中チェックを行う。
        if (isEndProcessing())
        {
            return false;
        }
        
        return true;
    }

    /**
     * リストセルエリアに対象データを表示できるかチェックします。<BR>
     * 該当件数が0件以下かチェックし、0件より多かった場合はメッセージをセットとtrueを返します。<BR>
     * 0件だった場合はメッセージをセットとfalseを返します。<BR>
     * <CODE>getMessage()</CODE>メソッドを使用して結果を取得してください。<BR>
     * @param pCount リストセルエリア表示件数
     * @return true：リストセルエリアに表示可能 false：表示不可
     */
    protected boolean canLowerDisplay(int pCount)
    {
        // 該当件数が0件以下の場合
        if (pCount <= 0)
        {
            // 6003011=対象データはありませんでした。
            setMessage("6003011");

            return false;
        }
        else if (pCount > WmsParam.MAX_NUMBER_OF_DISP)
        {
            // 6001023={0}件該当しました。表示可能件数を超えるため、先頭{1}件の情報を表示します。
            String msg =
                    WmsMessageFormatter.format(6001023, WmsFormatter.getNumFormat(pCount), MAX_NUMBER_OF_DISP_DISP,
                            WmsFormatter.getNumFormat(WmsParam.MAX_NUMBER_OF_DISP));
            setMessage(msg);

            return true;
        }
        else
        {
            // 6001013=表示しました。
            setMessage("6001013");

            return true;
        }
    }

    /**
     * トランザクションの確定を行います。<BR>
     * ロールバックを行います。<BR>
     * @param pClass 呼び出し元クラス名
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void doRollBack(Class pClass)
            throws CommonException
    {
        try
        {
            getConnection().rollback();
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, pClass.getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * トランザクションの確定を行います。<BR>
     * コミットを行います。<BR>
     * @param pClass 呼び出し元クラス名
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void doCommit(Class pClass)
            throws CommonException
    {
        try
        {
            getConnection().commit();
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, pClass.getName());
            throw new ReadWriteException(e);
        }
    }

    /**
     * DBFinderのclose処理を呼び出します。
     * 引数で指定されたfinderがnullかどうかチェックを行ってからcloseします。
     * 
     * @param finder dbfinder
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void closeFinder(BasicDatabaseFinder finder)
            throws CommonException
    {
        if (finder != null)
        {
            finder.close();
            DEBUG.MSG(DEBUG.HANDLER, "finder close" + this.getClass().getName());
        }
    }
    
    /**
     * ハードウェア区分がリスト作業のユーザ情報を返します。
     * 
     * @return ユーザ情報<br>
     *  ユーザ情報がセットされていない場合は null
     */
    protected WmsUserInfo getWmsUserInfo()
    {
        DfkUserInfo dfkInfo = super.getUserInfo();
        if (dfkInfo == null)
        {
            return null;
        }
        
        WmsUserInfo wmsInfo = new WmsUserInfo(dfkInfo);
        wmsInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_LIST);
        
        return wmsInfo;
    }

    /**
     * リストセル行No.をセットします。
     * @param ngCellRow セットするリストセル行No.
     */
    public void setNgCellRow(int ngCellRow)
    {
        _NgCellRow = ngCellRow;
    }

    /**
     * リストセル行No.を取得します。
     * @return リストセル行No.
     */
    public int getNgCellRow()
    {
        return _NgCellRow;
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
     * このクラスのバージョンを返します。
     * @return バージョンと日付
     */
    public static String getVersion()
    {
        return ("$Revision: 7996 $,$Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $");
    }

}
//end of class
