// $Id: WarenaviSystemController.java 7996 2011-07-06 00:52:24Z kitamaki $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.emanager.database.entity.AuthenticationSystem;
import jp.co.daifuku.emanager.database.handler.AuthenticationSystemImpl;
import jp.co.daifuku.emanager.database.handler.EmHandlerFactory;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.HostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventResultHandler;
import jp.co.daifuku.wms.base.dbhandler.InventResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveResultHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetHostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingHostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingResultHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ResultHandler;
import jp.co.daifuku.wms.base.dbhandler.ResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipHostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipResultHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.SortHostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortResultHandler;
import jp.co.daifuku.wms.base.dbhandler.SortResultSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHistoryHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHistorySearchKey;
import jp.co.daifuku.wms.base.dbhandler.WarenaviSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WarenaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WarenaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.InventResult;
import jp.co.daifuku.wms.base.entity.MoveResult;
import jp.co.daifuku.wms.base.entity.PCTRetHostSend;
import jp.co.daifuku.wms.base.entity.PCTRetResult;
import jp.co.daifuku.wms.base.entity.ReceivingHostSend;
import jp.co.daifuku.wms.base.entity.ReceivingResult;
import jp.co.daifuku.wms.base.entity.Result;
import jp.co.daifuku.wms.base.entity.ShipHostSend;
import jp.co.daifuku.wms.base.entity.ShipResult;
import jp.co.daifuku.wms.base.entity.SortHostSend;
import jp.co.daifuku.wms.base.entity.SortResult;
import jp.co.daifuku.wms.base.entity.StockHistory;
import jp.co.daifuku.wms.base.entity.WarenaviSystem;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DatabaseHandler;
import jp.co.daifuku.wms.handler.db.SQLSearchKey;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * システム定義情報へのアクセスを行うためのコントローラクラスです。<br>
 * このクラスはインスタンス生成時にシステム定義情報を読み込んでキャッシュします。<br>
 * 最新の情報が必要なときは、read()を呼び出して再読み込みを行ってください。
 *
 * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
 * @author  ss
 * @author  Last commit: $Author: kitamaki $
 */

public class WarenaviSystemController
        extends AbstractController
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
    // flags for package installed
    private boolean _hasAnalysisPack = false;

    private boolean _hasAsrsPack = false;

    private boolean _hasMasterPack = false;

    private boolean _hasReceivingPack = false;

    private boolean _hasRetrievalPack = false;

    private boolean _hasShippingPack = false;

    private boolean _hasSortingPack = false;

    private boolean _hasStockPack = false;

    private boolean _hasStoragePack = false;

    private boolean _hasCrossdockPack = false;

    private boolean _hasPCTMasterPack = false;

    private boolean _hasPCTReceivingPack = false;

    private boolean _hasPCTRetrievalPack = false;

    private boolean _hasPCTInventoryPack = false;

    // flags for logging enabled
    private boolean _controlLogEnabled = false;

    private boolean _masterLogEnabled = false;

    private boolean _stockLogEnabled = false;


    // flags for system status
    private boolean _dailyUpdating = false;

    private boolean _dataLoading = false;

    private boolean _dataReporting = false;

    private boolean _retrievalAllocating = false;

    private boolean _hostEnabled = false;

    private boolean _allocationClear = false;

    private boolean _endProcessing = false;

    private boolean _faDaEnabled = false;

    // work day
    private String _workDay = null;

    // others
    private Date _dailyUpdateDate;

    private int _resultHoldPeriod;

    private int _planHoldPeriod;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------

    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)<br>
     * コンストラクタでテーブルの読み込みを行います。
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     */
    public WarenaviSystemController(Connection conn, Class caller) throws ReadWriteException,
            ScheduleException
    {
        super(conn, caller);
        readWarenaviSystem(SYSTEM_NO_DEFAULT);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 作業日の更新を行います。
     * @param day 更新する作業日
     * @param userInfo 更新を行うユーザと端末IP
     * @return 正常に更新できたときはtrue.<br>
     * 分析パッケージが導入されていてかつ実績のある日より前に更新しようとしたときfalse.
     * 
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 他の処理で変更されているときスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     */
    public boolean updateWorkDay(String day, WmsUserInfo userInfo)
            throws NotFoundException,
                ReadWriteException,
                ScheduleException
    {
        // read current
        readWarenaviSystem(SYSTEM_NO_DEFAULT);

        // 分析パッケージが導入されている場合のみ、実績の確認を行う。
        // check result if move to past
        if (hasAnalysisPack() && 0 > day.compareTo(_workDay))
        {
            // check result date
            if (!checkResults(day))
            {
                return false;
            }
        }

        // 作業日の更新
        WarenaviSystemHandler wh = new WarenaviSystemHandler(getConnection());
        WarenaviSystemAlterKey akey = new WarenaviSystemAlterKey();

        akey.setWorkDay(_workDay);
        akey.setSystemNo(SYSTEM_NO_DEFAULT);
        akey.updateWorkDay(day);

        wh.modify(akey);

        // eManagerの作業日(ログイン成功画面での表示用)を更新
        updateWorkDate(day, userInfo);

        _workDay = day;

        return true;
    }

    /**
     * 日次処理状態を設定します。
     * @param inprogress 処理中の時true.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 他の処理でフラグ更新済の時スローされます。
     */
    public void updateDailyUpdating(boolean inprogress)
            throws NotFoundException,
                ReadWriteException
    {
        updateProgress(WarenaviSystem.DAILY_UPDATE, SYSTEM_NO_DEFAULT, inprogress);
        _dailyUpdating = inprogress;
    }

    /**
     * 予定データ取り込み中状態を設定します。
     * @param inprogress 処理中の時true.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 他の処理でフラグ更新済の時スローされます。
     */
    public void updateDataLoading(boolean inprogress)
            throws NotFoundException,
                ReadWriteException
    {
        updateProgress(WarenaviSystem.LOAD_DATA, SYSTEM_NO_DEFAULT, inprogress);
        _dataLoading = inprogress;
    }

    /**
     * 報告データ作成中状態を設定します。
     * @param inprogress 処理中の時true.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 他の処理でフラグ更新済の時スローされます。
     */
    public void updateDataReporting(boolean inprogress)
            throws NotFoundException,
                ReadWriteException
    {
        updateProgress(WarenaviSystem.REPORT_DATA, SYSTEM_NO_DEFAULT, inprogress);
        _dataReporting = inprogress;
    }

    /**
     * 出庫引当中状態を設定します。
     * @param inprogress 処理中の時true.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 他の処理でフラグ更新済の時スローされます。
     */
    public void updateRetrievalAllocating(boolean inprogress)
            throws NotFoundException,
                ReadWriteException
    {
        updateProgress(WarenaviSystem.RETRIEVAL_ALLOCATE, SYSTEM_NO_DEFAULT, inprogress);
        _retrievalAllocating = inprogress;
    }

    /**
     * ホストとの接続設定を設定します。
     * 
     * @param enable 接続可能のときtrue.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 他の処理でフラグ更新済の時スローされます。
     */
    public void updateHostEnabled(boolean enable)
            throws NotFoundException,
                ReadWriteException
    {
        String flg = enable ? HOST_ENABLED
                           : HOST_DISABLED;
        String keyFlg = enable ? HOST_DISABLED
                              : HOST_ENABLED;

        WarenaviSystemHandler wh = new WarenaviSystemHandler(getConnection());
        WarenaviSystemAlterKey akey = new WarenaviSystemAlterKey();
        akey.setCommStatusFlag(keyFlg);
        akey.setSystemNo(SYSTEM_NO_DEFAULT);
        akey.updateCommStatusFlag(flg);

        wh.modify(akey);
        _hostEnabled = enable;
    }

    /**
     * 搬送データクリア状態を設定します。
     * @param inprogress 処理中の時true.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 他の処理でフラグ更新済の時スローされます。
     */
    public void updateAllocationClear(boolean inprogress)
            throws NotFoundException,
                ReadWriteException
    {
        updateProgress(WarenaviSystem.ALLOCATION_CLEAR, SYSTEM_NO_DEFAULT, inprogress);
        _allocationClear = inprogress;
    }

    /**
     * 終了処理中フラグを設定します。
     * @param inprogress 処理中の時true.
     * @throws NotFoundException データベースアクセスエラーが発生したときスローされます。
     * @throws ReadWriteException 他の処理でフラグ更新済の時スローされます。
     */
    public void updateEndProcessing(boolean inprogress)
            throws NotFoundException, ReadWriteException
    {
        updateProgress(WarenaviSystem.END_PROCESSING_FLAG, SYSTEM_NO_DEFAULT, inprogress);
        _endProcessing = inprogress;
    }

    /**
     * 日次更新日時を設定します。
     * @param time 更新 する 日時 (nullのときはシステム日時)
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException テーブルにレコードが見つからなかったときスローされます。
     */
    public void updateDailyUpdateDate(Timestamp time)
            throws NotFoundException,
                ReadWriteException
    {
        // このメソッドは場合によってはpublic化する
        // handler version
        WarenaviSystemHandler handler = new WarenaviSystemHandler(getConnection());
        WarenaviSystemAlterKey akey = new WarenaviSystemAlterKey();
        akey.setSystemNo(SYSTEM_NO_DEFAULT);

        Timestamp uptime = (time == null) ? new SysDate()
                                         : time;
        akey.updateDailyUpdateDate(uptime);

        handler.modify(akey);
    }

    /**
     * 日次更新日時を現在のシステム日時に設定します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException テーブルにレコードが見つからなかったときスローされます。
     */
    public void updateDailyUpdateDate()
            throws NotFoundException,
                ReadWriteException
    {
        updateDailyUpdateDate((Timestamp)null);
    }

    /**
     * このインスタンスの保持している情報を最新に更新します。<br>
     * コンストラクタで読み込みを行っていますので、通常は呼び出す必要はありません。
     * 
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     */
    public void read()
            throws ReadWriteException,
                ScheduleException
    {
        readWarenaviSystem(SYSTEM_NO_DEFAULT);
    }

    /**
     * 実績に保存されている日付と比較を行います。
     * 
     * @param day 変更作業日
     * @return 実績の方が作業日より未来のものがあればfalse
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean checkResults(String day)
            throws ReadWriteException
    {
        // env create
        Connection conn = getConnection();
        //        CheckEnv[] envs = {
        //            new CheckEnv(new StockHistoryHandler(conn), new StockHistorySearchKey(), StockHistory.WORK_DAY),
        //            new CheckEnv(new ResultHandler(conn), new ResultSearchKey(), Result.WORK_DAY),
        //            new CheckEnv(new InventResultHandler(conn), new InventResultSearchKey(), InventResult.WORK_DAY),
        //            new CheckEnv(new MoveResultHandler(conn), new MoveResultSearchKey(), MoveResult.STORAGE_WORK_DAY),
        //            new CheckEnv(new SortResultHandler(conn), new SortResultSearchKey(), SortResult.WORK_DAY),
        //            new CheckEnv(new SortHostSendHandler(conn), new SortHostSendSearchKey(), SortHostSend.WORK_DAY),
        //            new CheckEnv(new ShipResultHandler(conn), new ShipResultSearchKey(), ShipResult.WORK_DAY),
        //            new CheckEnv(new ShipHostSendHandler(conn), new ShipHostSendSearchKey(), ShipHostSend.WORK_DAY),
        //            new CheckEnv(new HostSendHandler(conn), new HostSendSearchKey(), HostSend.WORK_DAY),
        //            new CheckEnv(new ReceiveResultHandler(conn), new ReceiveResultSearchKey(), ReceiveResult.WORK_DAY),
        //            new CheckEnv(new ReceiveHostSendHandler(conn), new ReceiveHostSendSearchKey(), ReceiveHostSend.WORK_DAY),
        //        };

        List<CheckEnv> list = new ArrayList<CheckEnv>();

        // 入荷パッケージ
        if (hasReceivingPack())
        {
            list.add(new CheckEnv(new ReceivingResultHandler(conn), new ReceivingResultSearchKey(),
                    ReceivingResult.WORK_DAY));
            list.add(new CheckEnv(new ReceivingHostSendHandler(conn), new ReceivingHostSendSearchKey(),
                    ReceivingHostSend.WORK_DAY));
        }
        // 出荷パッケージ
        if (hasShippingPack())
        {
            list.add(new CheckEnv(new ShipResultHandler(conn), new ShipResultSearchKey(), ShipResult.WORK_DAY));
            list.add(new CheckEnv(new ShipHostSendHandler(conn), new ShipHostSendSearchKey(), ShipHostSend.WORK_DAY));
        }
        // 在庫パッケージ
        if (hasStockPack())
        {
            list.add(new CheckEnv(new StockHistoryHandler(conn), new StockHistorySearchKey(), StockHistory.WORK_DAY));
            if (!_faDaEnabled)
            {
                list.add(new CheckEnv(new MoveResultHandler(conn), new MoveResultSearchKey(),
                        MoveResult.STORAGE_WORK_DAY));
            }
        }
        // 入出庫パッケージ
        if (hasStoragePack() || hasRetrievalPack())
        {
            list.add(new CheckEnv(new ResultHandler(conn), new ResultSearchKey(), Result.WORK_DAY));
            list.add(new CheckEnv(new HostSendHandler(conn), new HostSendSearchKey(), HostSend.WORK_DAY));
            if (hasStoragePack() && !_faDaEnabled)
            {
                list.add(new CheckEnv(new InventResultHandler(conn), new InventResultSearchKey(), InventResult.WORK_DAY));
            }
        }
        // 仕分パッケージ
        if (hasSortingPack())
        {
            list.add(new CheckEnv(new SortResultHandler(conn), new SortResultSearchKey(), SortResult.WORK_DAY));
            list.add(new CheckEnv(new SortHostSendHandler(conn), new SortHostSendSearchKey(), SortHostSend.WORK_DAY));
        }

        // PCT出庫パッケージ
        if (hasPCTRetrievalPack())
        {
            list.add(new CheckEnv(new PCTRetResultHandler(conn), new PCTRetResultSearchKey(), PCTRetResult.WORK_DAY));
            list.add(new CheckEnv(new PCTRetHostSendHandler(conn), new PCTRetHostSendSearchKey(),
                    PCTRetHostSend.WORK_DAY));
        }

        CheckEnv[] envs = new CheckEnv[list.size()];
        list.toArray(envs);

        return checkWorkDate(envs, day);
    }


    /**
     * 認証システム設定情報の作業日を更新します。
     * 
     * @param workDate 変更作業日
     * @param userInfo ユーザ情報
     * @return 更新に成功した時true
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean updateWorkDate(String workDate, WmsUserInfo userInfo)
            throws ReadWriteException
    {
        AuthenticationSystem entity = new AuthenticationSystem();
        entity.setWorkDate(WmsFormatter.toDate(workDate));
        entity.setUpdateUser(userInfo.getUserId());
        entity.setUpdateTerminal(userInfo.getTerminalAddress());
        entity.setUpdateProcess(getCallerName());

        try
        {
            AuthenticationSystemImpl handler =
                    (AuthenticationSystemImpl)EmHandlerFactory.getAuthenticationSystemHandler(getConnection());
            handler.updateWorkDate(entity);
        }
        catch (SQLException e)
        {
            RmiMsgLogClient.writeSQLTrace(e, getClass().getName());
            throw new ReadWriteException(e);
        }

        return true;
    }
    
    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * 作業日を返します。
     * @return 作業日
     */
    public String getWorkDay()
    {
        return _workDay;
    }

    /**
     * 日次更新日時を返します。
     * @return 日次更新日時
     */
    public Date getDailyUpdateDate()
    {
        return _dailyUpdateDate;
    }

    /**
     * 予定保持日数を返します。
     * @return 予定保持日数
     */
    public int getPlanHoldPeriod()
    {
        return _planHoldPeriod;
    }

    /**
     * 実績保持日数を返します。
     * @return 実績保持日数
     */
    public int getResultHoldPeriod()
    {
        return _resultHoldPeriod;
    }

    /**
     * 在庫更新ログ取得を返します。
     * @return 在庫更新ログ取得を行うときtrue.
     */
    public boolean isStockLogEnabled()
    {
        return _stockLogEnabled;
    }

    /**
     * 操作ログ取得を返します。
     * @return 操作ログ取得を行うときtrue.
     */
    public boolean isControlLogEnabled()
    {
        return _controlLogEnabled;
    }

    /**
     * マスタ更新ログ取得を返します。
     * @return マスタ更新ログ取得を行うときtrue.
     */
    public boolean isMasterLogEnabled()
    {
        return _masterLogEnabled;
    }

    /**
     * 分析パッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasAnalysisPack()
    {
        return _hasAnalysisPack;
    }

    /**
     * マスタパッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasMasterPack()
    {
        return _hasMasterPack;
    }

    /**
     * 入荷パッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasReceivingPack()
    {
        return _hasReceivingPack;
    }

    /**
     * 出荷パッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasShippingPack()
    {
        return _hasShippingPack;
    }

    /**
     * 仕分パッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasSortingPack()
    {
        return _hasSortingPack;
    }

    /**
     * 在庫パッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasStockPack()
    {
        return _hasStockPack;
    }

    /**
     * 入庫パッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasStoragePack()
    {
        return _hasStoragePack;
    }

    /**
     * 出庫パッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasRetrievalPack()
    {
        return _hasRetrievalPack;
    }

    /**
     * AS/RSパッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasAsrsPack()
    {
        return _hasAsrsPack;
    }

    /**
     * クロスドックパッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasCrossdockPack()
    {
        return _hasCrossdockPack;
    }

    /**
     * PCTマスタパッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasPCTMasterPack()
    {
        return _hasPCTMasterPack;
    }

    /**
     * PCT入荷パッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasPCTReceivingPack()
    {
        return _hasPCTReceivingPack;
    }

    /**
     * PCTピッキングパッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasPCTRetrievalPack()
    {
        return _hasPCTRetrievalPack;
    }

    /**
     * PCT棚卸パッケージ導入内容を返します。
     * @return 導入されているときtrue.
     */
    public boolean hasPCTInventoryPack()
    {
        return _hasPCTInventoryPack;
    }

    /**
     * ホストとの接続設定を返します。
     * @return ホストとの接続が有効の時true.
     */
    public boolean isHostEnabled()
    {
        return _hostEnabled;
    }

    /**
     * FA/DA区分を返します。
     * @return FAの時true.
     */
    public boolean isFaDaEnabled()
    {
        return _faDaEnabled;
    }

    /**
     * 日次処理状態を返します。
     * @return 処理中の時true.
     */
    public boolean isDailyUpdating()
    {
        return _dailyUpdating;
    }

    /**
     * 予定データ取り込み中状態を返します。
     * @return 処理中の時true.
     */
    public boolean isDataLoading()
    {
        return _dataLoading;
    }

    /**
     * 報告データ作成中状態を返します。
     * @return 処理中の時true.
     */
    public boolean isDataReporting()
    {
        return _dataReporting;
    }

    /**
     * 出庫引当中状態を返します。
     * @return 処理中の時true.
     */
    public boolean isRetrievalAllocating()
    {
        return _retrievalAllocating;
    }

    /**
     * 搬送データクリア状態を返します。
     * @return 処理中の時true.
     */
    public boolean isAllocationClear()
    {
        return _allocationClear;
    }

    /**
     * 終了処理中状態を返します。
     * @return 処理中の時true.
     */
    public boolean isEndProcessing()
    {
        return _endProcessing;
    }
    
    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * DNWARENAVISYSTEMを読み込んで、インスタンス変数に内容を保存します。
     * 
     * @param systemNo 対象のシステムNo.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。<br>
     * 対象のレコードが存在しないか、複数存在するときにもスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がないときスローされます。
     */
    protected void readWarenaviSystem(String systemNo)
            throws ReadWriteException,
                ScheduleException
    {
        WarenaviSystemHandler wh = new WarenaviSystemHandler(getConnection());
        WarenaviSystemSearchKey key = new WarenaviSystemSearchKey();
        key.setSystemNo(systemNo);

        Entity[] ents = wh.find(key);
        // DNWARENAVISYSTEM has only one record
        if (ents == null || ents.length != 1)
        {
            throw new ScheduleException(WarenaviSystem.STORE_NAME + " record not found or too many records.");
        }

        WarenaviSystem went = (WarenaviSystem)ents[0];

        // logging setting
        _controlLogEnabled = LOG_ENABLED.equals(went.getControlLogFlag());
        _masterLogEnabled = LOG_ENABLED.equals(went.getControlLogFlag());
        _stockLogEnabled = LOG_ENABLED.equals(went.getStockUpdateLogFlag());

        // package installed
        _hasAnalysisPack = PACK_INSTALLED.equals(went.getAnalysisPack());
        _hasMasterPack = PACK_INSTALLED.equals(went.getMasterPack());
        _hasReceivingPack = PACK_INSTALLED.equals(went.getReceivingPack());
        _hasShippingPack = PACK_INSTALLED.equals(went.getShippingPack());
        _hasSortingPack = PACK_INSTALLED.equals(went.getSortingPack());
        _hasStockPack = PACK_INSTALLED.equals(went.getStockPack());
        _hasStoragePack = PACK_INSTALLED.equals(went.getStoragePack());
        _hasAsrsPack = PACK_INSTALLED.equals(went.getAsrsPack());
        _hasRetrievalPack = PACK_INSTALLED.equals(went.getRetrievalPack());
        _hasCrossdockPack = PACK_INSTALLED.equals(went.getCrossdockPack());
        _hasPCTMasterPack = PACK_INSTALLED.equals(went.getPctMasterPack());
        _hasPCTReceivingPack = PACK_INSTALLED.equals(went.getPctReceivingPack());
        _hasPCTRetrievalPack = PACK_INSTALLED.equals(went.getPctRetrievalPack());
        _hasPCTInventoryPack = PACK_INSTALLED.equals(went.getPctInventoryPack());


        // process flags
        _dailyUpdating = PROCESS_IN_PROGRESS.equals(went.getDailyUpdate());
        _dataLoading = PROCESS_IN_PROGRESS.equals(went.getLoadData());
        _dataReporting = PROCESS_IN_PROGRESS.equals(went.getReportData());
        _retrievalAllocating = PROCESS_IN_PROGRESS.equals(went.getRetrievalAllocate());
        _allocationClear = PROCESS_IN_PROGRESS.equals(went.getAllocationClear());
        _endProcessing = PROCESS_IN_PROGRESS.equals(went.getEndProcessingFlag());

        // others
        _workDay = went.getWorkDay();
        _hostEnabled = HOST_ENABLED.equals(went.getCommStatusFlag());
        _faDaEnabled = FADA_FA.equals(went.getFadaFlag());
        _resultHoldPeriod = went.getResultHoldPeriod();
        _planHoldPeriod = went.getPlanHoldPeriod();
        _dailyUpdateDate = went.getDailyUpdateDate();
    }

    /**
     * 処理中フラグを更新します。
     * 
     * @param field 更新対象フィールド
     * @param systemNo 対象のシステムNo.
     * @param inprogress 処理中の時true.
     * @throws NotFoundException 他の処理でフラグ更新済の時スローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    protected void updateProgress(FieldName field, String systemNo, boolean inprogress)
            throws NotFoundException,
                ReadWriteException
    {
        String flg = inprogress ? PROCESS_IN_PROGRESS
                               : PROCESS_COMPLETED;
        String keyFlg = inprogress ? PROCESS_COMPLETED
                                  : PROCESS_IN_PROGRESS;

        WarenaviSystemHandler wh = new WarenaviSystemHandler(getConnection());
        WarenaviSystemAlterKey akey = new WarenaviSystemAlterKey();
        akey.setKey(field, keyFlg);
        akey.setSystemNo(systemNo);
        akey.setAdhocUpdateValue(field, flg);

        try
        {
            wh.modify(akey);
        }
        catch (NotFoundException e)
        {
            // ignore exception if flag turn off.
            if (inprogress)
            {
                throw e;
            }
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------
    /**
     * ハンドラとキーを指定して、該当作業日よりも小さいレコードがあるかどうか確認します。
     * @param envs 検索用情報の配列 (すべてチェック対象となります)
     * @param workday 確認する作業日
     * @return 該当レコードが存在しなければtrue
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    private boolean checkWorkDate(CheckEnv[] envs, String workday)
            throws ReadWriteException
    {
        for (int i = 0; i < envs.length; i++)
        {
            CheckEnv checkEnv = envs[i];
            SQLSearchKey key = checkEnv.getKey();
            FieldName targetField = checkEnv.getTargetField();
            DatabaseHandler handler = checkEnv.getHandler();

            key.clear();
            key.setKey(targetField, workday, ">", "", "", true);
            FieldName cntField = new FieldName("", "CNT_");
            key.setCollect(targetField, "COUNT", cntField);

            Entity[] ents = handler.find(key);
            if (ents != null && ents.length > 0)
            {
                int numRec = ents[0].getBigDecimal(cntField, new BigDecimal(0)).intValue();
                if (numRec > 0)
                {
                    // newer result found.
                    return false;
                }
            }
            else
            {
                // 6006002=データベースエラーが発生しました。{0}
                Object[] mps = {
                    targetField.getStoreName(),
                };
                RmiMsgLogClient.write(6006002, getCaller().getName(), mps);
                throw new RuntimeException("SQL count does not return the record.");
            }
        }
        // ALL OK
        return true;
    }


    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: WarenaviSystemController.java 7996 2011-07-06 00:52:24Z kitamaki $";
    }

    /**
     * 作業履歴・実績情報確認のための利用インスタンスを保持するクラスです。
     *
     *
     * @version $Revision: 7996 $, $Date: 2011-07-06 09:52:24 +0900 (水, 06 7 2011) $
     * @author  ss
     * @author  Last commit: $Author: kitamaki $
     */
    class CheckEnv
    {
        private DatabaseHandler _handler;

        private SQLSearchKey _key;

        private FieldName _targetField;

        /**
         * チェック対象情報を指定します。
         * @param handler データベースハンドラ
         * @param key 検索対象キー
         * @param targetField 対象フィールド
         */
        CheckEnv(DatabaseHandler handler, SQLSearchKey key, FieldName targetField)
        {
            _handler = handler;
            _key = key;
            _targetField = targetField;
        }

        /**
         * handlerを返します。
         * @return handlerを返します。
         */
        DatabaseHandler getHandler()
        {
            return _handler;
        }

        /**
         * handlerを設定します。
         * @param handler handler
         */
        void setHandler(DatabaseHandler handler)
        {
            _handler = handler;
        }

        /**
         * keyを返します。
         * @return keyを返します。
         */
        SQLSearchKey getKey()
        {
            return _key;
        }

        /**
         * keyを設定します。
         * @param key key
         */
        void setKey(SQLSearchKey key)
        {
            _key = key;
        }

        /**
         * targetFieldを返します。
         * @return targetFieldを返します。
         */
        FieldName getTargetField()
        {
            return _targetField;
        }

        /**
         * targetFieldを設定します。
         * @param targetField targetField
         */
        void setTargetField(FieldName targetField)
        {
            _targetField = targetField;
        }
    }
}
