package jp.co.daifuku.wms.system.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.system.schedule.RFTStateSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.busitune.rft.haisurf.da.AbstractHSScheduler;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.ScheduleParams.ProcessFlag;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.controller.ReceivingWorkInfoController;
import jp.co.daifuku.wms.base.controller.RftController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.controller.WorkInfoController;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.CrossDockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.InventWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.MoveWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RftAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RftHandler;
import jp.co.daifuku.wms.base.dbhandler.RftSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShipPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.CrossDockPlan;
import jp.co.daifuku.wms.base.entity.InventWorkInfo;
import jp.co.daifuku.wms.base.entity.MoveWorkInfo;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Rft;
import jp.co.daifuku.wms.base.entity.ShipPlan;
import jp.co.daifuku.wms.base.entity.ShipWorkInfo;
import jp.co.daifuku.wms.base.entity.SortWorkInfo;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetCompleteSCHParams;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetReStartSCH;
import jp.co.daifuku.wms.retrieval.rft.schedule.RetReStartSCHParams;
import jp.co.daifuku.wms.ship.rft.schedule.ShpCompleteSCHParams;
import jp.co.daifuku.wms.ship.rft.schedule.ShpReStartSCH;
import jp.co.daifuku.wms.ship.rft.schedule.ShpReStartSCHParams;
import jp.co.daifuku.wms.stock.operator.MoveOperator;
import jp.co.daifuku.wms.stock.schedule.StockInParameter;

/**
 * RFT作業状態設定のスケジュール処理を行います。
 * 
 * @version $Revision: 1.3 $, $Date: 2009/02/24 02:52:52 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class RFTStateSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;
    /**
     * 出庫スケジュールクラス名
     */
    private static final String RETRIEVAL_SCH_CLASS_NAME = "RetCompleteSCH";

    /**
     * 仕分スケジュールクラス名
     */
    private static final String SORTING_SCH_CLASS_NAME = "";

    /**
     * 出荷スケジュールクラス名
     */
    private static final String SHIPPING_SCH_CLASS_NAME = "ShpCompleteSCH";

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * RFT管理情報ハンドラ
     */
    private RftHandler _RftHndlr = null;

    /**
     * RFT管理情報検索キー
     */
    private RftSearchKey _RftSKey = null;

    /**
     * RFT管理情報更新キー
     */
    private RftAlterKey _RftAKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public RFTStateSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);

        // DBハンドラ生成
        _RftSKey = new RftSearchKey();
        _RftAKey = new RftAlterKey();
        _RftHndlr = new RftHandler(getConnection());
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param startParams 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams startParams)
            throws CommonException
    {

        RFTStateSCHParams startParam = (RFTStateSCHParams)startParams;

        SystemInParameter inParam = new SystemInParameter(getWmsUserInfo());
        // RFT号機No.
        inParam.setTerminalNo(startParam.getString(RFT_NO));

        List<Params> params = new ArrayList<Params>();

        try
        {
            // 検索条件設定
            _RftSKey.clear();
            // RFT号機No.
            if (!StringUtil.isBlank(inParam.getTerminalNo()))
            {
                _RftSKey.setRftNo(inParam.getTerminalNo());
            }
            // ソート順をセット
            _RftSKey.setRftNoOrder(true);
            // RFT管理情報取得
            Rft[] rftEnt = (Rft[])_RftHndlr.find(_RftSKey);

            if (rftEnt != null && rftEnt.length > 0)
            {

                // ログインユーザ情報ハンドラクラス生成
                Com_loginuserSearchKey loginSKey = new Com_loginuserSearchKey();
                Com_loginuserHandler loginHndr = new Com_loginuserHandler(getConnection());

                for (int i = 0; i < rftEnt.length; i++)
                {

                    Params p = new Params();
                    // No.
                    p.set(SELECT, i + 1);
                    // RFT号機No.
                    p.set(RFT_NO, rftEnt[i].getRftNo());
                    // 端末区分
                    p.set(TERMINAL_TYPE, rftEnt[i].getTerminalType());
                    p.set(TERMINAL_TYPE_NAME, DisplayResource.getTerminalType(rftEnt[i].getTerminalType()));
                    // ユーザID
                    p.set(USER_ID, rftEnt[i].getUserId());

                    // 作業区分が[未作業]
                    if (SystemDefine.JOB_TYPE_UNSTART.equals(rftEnt[i].getJobType()))
                    {
                        // 状態が[起動中]
                        if (SystemDefine.RFT_STATUS_FLAG_START.equals(rftEnt[i].getStatusFlag()))
                        {
                            // RFT状態に[未作業]をセットする
                            p.set(STATUS_FLAG_NAME, DisplayResource.getJobType(SystemDefine.JOB_TYPE_UNSTART));
                            p.set(STATUS_FLAG, SystemDefine.JOB_TYPE_UNSTART);
                        }
                    }
                    else
                    {
                        // RFT状態
                        p.set(STATUS_FLAG_NAME, DisplayResource.getJobType(rftEnt[i].getJobType()));
                        p.set(STATUS_FLAG, rftEnt[i].getJobType());

                        // 検索条件をセット
                        loginSKey.clear();
                        // ユーザID
                        loginSKey.setUserid(rftEnt[i].getUserId());
                        try
                        {
                            // ログインユーザ情報取得
                            Com_loginuser loginEnt = (Com_loginuser)loginHndr.findPrimary(loginSKey);

                            if (loginEnt != null)
                            {
                                // ユーザ名称をセット
                                p.set(USER_NAME, loginEnt.getUsername());
                            }
                        }
                        catch (NoPrimaryException e)
                        {
                            e.printStackTrace();
                            // 6027006 = データの不整合が発生しました。ログを参照してください。TABLE={0}
                            setMessage(WmsMessageFormatter.format(6027006, Com_loginuser.STORE_NAME));
                            return null;
                        }
                    }

                    params.add(p);

                    // 6001013 = 表示しました。
                    setMessage("6001013");
                }
            }
            else
            {
                // 6003011=対象データはありませんでした。
                setMessage("6003011");
                return params;
            }
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください
            setMessage("6007002");
            return null;
        }
        return params;
    }

    /**
     * スケジュールを開始します。<CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、<BR>
     * スケジュール処理を行います。スケジュール処理の実装はこのインタフェースを実装するクラスによって異なります。<BR>
     * このメソッドはスケジュールの結果をもとに、画面表示内容を再表示する場合に使用します。
     * 条件エラーなどでスケジュール処理が失敗した場合はnullを返します。<BR>
     * この場合は<CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。
     * @param startParams データベースとのコネクションオブジェクト
     * @return スケジュール処理が正常終了した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    public boolean startSCH(ScheduleParams startParams)
            throws CommonException
    {

        RFTStateSCHParams startParam = (RFTStateSCHParams)startParams;

        SystemInParameter[] inParam = new SystemInParameter[1];
        inParam[0] = new SystemInParameter(getWmsUserInfo());
        // RFT号機No.
        inParam[0].setTerminalNo(startParam.getString(RFT_NO));
        // ユーザID
        inParam[0].setUserId(startParam.getString(USER_ID));
        // 途中作業区分
        inParam[0].setWorkOnTheWay(startParam.getString(WORK_ON_THE_WAY));
        // 不足数量区分
        inParam[0].setLackFlag(startParam.getString(LACK_FLAG));

        // 入力パラメータチェック
        if (inParam == null || (inParam != null && inParam.length == 0))
        {
            // 6016059 = パラメータ入力エラー。
            setMessage("6016059");
            return false;
        }

        // 日次更新処理中チェック
        if (!canStart())
        {
            return false;
        }

        try
        {
            // 検索条件設定
            _RftSKey.clear();
            // RFT号機No.
            _RftSKey.setRftNo(inParam[0].getTerminalNo());
            // RFT管理情報取得
            Rft rftEnt = (Rft)_RftHndlr.findPrimary(_RftSKey);
            if (rftEnt != null)
            {
                // 作業区分を取得
                inParam[0].setJobType(rftEnt.getJobType());
            }
        }
        catch (NoPrimaryException e)
        {
            e.printStackTrace();
            // 6027006 = データの不整合が発生しました。ログを参照してください。TABLE={0}
            setMessage(WmsMessageFormatter.format(6027006, Rft.STORE_NAME));
            return false;
        }

        try
        {
            // 確定/キャンセル処理を行う
            if (!updateWorkStatus(inParam))
            {
                return false;
            }

            // 更新条件設定
            _RftAKey.clear();
            // 端末号機No.
            _RftAKey.setRftNo(inParam[0].getTerminalNo());

            // 更新値設定
            // ユーザID
            _RftAKey.updateUserId(null);
            // 作業区分(未作業)
            _RftAKey.updateJobType(Rft.JOB_TYPE_UNSTART);
            // 状態フラグ(停止中)
            _RftAKey.updateStatusFlag(Rft.RFT_STATUS_FLAG_STOP);
            // 無線状態フラグ(無線エリア内)
            _RftAKey.updateRadioFlag(Rft.RADIO_FLAG_IN);
            // 休憩開始日時
            _RftAKey.updateRestStartDate(null);
            // 設定単位キー
            _RftAKey.updateSettingUnitKey(null);
            // 作業区分詳細
            _RftAKey.updateJobDetails(Rft.JOB_DETAIL_OTHER);
            // 最終更新処理名
            _RftAKey.updateLastUpdatePname(this.getClass().getSimpleName());

            // RFT管理情報更新
            _RftHndlr.modify(_RftAKey);

            // 6001006 = 設定しました。
            setMessage("6001006");

        }
        catch (InvalidDefineException e)
        {
            // 6027013 = 不正なパラメータが検出されたため、処理を中断しました。
            setMessage("6027013");
            return false;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage("6023004");
            return false;
        }
        return true;
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
     * 作業状態を更新します。
     *
     * @param param 画面に入力されたパラメータの配列
     * @return スケジュール処理が正常終了した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    protected boolean updateWorkStatus(SystemInParameter[] param)
            throws CommonException
    {
        // RFTコントローラ
        RftController rftController = new RftController(getConnection(), this.getClass());

        try
        {
            // [キャンセル]が選択された場合
            if (SystemInParameter.RFT_WORK_CANSEL.equals(param[0].getWorkOnTheWay()))
            {
                WarenaviSystemController wsysCtlr = new WarenaviSystemController(getConnection(), getClass());

                // 入荷パッケージ
                if (wsysCtlr.hasReceivingPack() || wsysCtlr.hasCrossdockPack())
                {
                    // 入荷作業情報キャンセル処理を行う
                    cancelReceivingWorkInfo(param[0].getTerminalNo());
                }

                // 入庫パッケージ、又は、出庫パッケージ
                if (wsysCtlr.hasStoragePack() || wsysCtlr.hasRetrievalPack())
                {
                    // 入出庫作業情報キャンセル処理を行う
                    cancelWorkInfo(param[0].getTerminalNo());
                }

                // 仕分パッケージ
                if (wsysCtlr.hasSortingPack() || wsysCtlr.hasCrossdockPack())
                {
                    // 仕分作業情報キャンセル処理を行う
                    cancelSortWorkInfo(param[0].getTerminalNo());
                }

                // 出荷パッケージ
                if (wsysCtlr.hasShippingPack() || wsysCtlr.hasCrossdockPack())
                {
                    // 出荷作業情報キャンセル処理を行う(出荷検品)
                    cancelShipWorkInfo(param[0].getTerminalNo());

                    // 出荷作業情報キャンセル処理を行う(出荷積込)
                    cancelBerthShipWorkInfo(param[0].getTerminalNo());
                }

                // 在庫パッケージ
                if (wsysCtlr.hasStockPack())
                {
                    // 移動入庫キャンセル処理を行う
                    cancelMoveStorageWorkInfo(param[0]);

                    // 移動出庫キャンセル処理を行う
                    cancelMoveRetrievalWorkInfo(param[0].getTerminalNo());
                }

                // 入庫パッケージ
                if (wsysCtlr.hasStoragePack())
                {
                    // 棚卸キャンセル処理を行う
                    cancelInventWorkInfo(param[0].getTerminalNo());
                }
            }
            // [確定]が選択された場合
            else
            {
                // パッケージマネージャの初期化
                PackageManager.initialize(getConnection(), this.getClass());

                // スケジュールクラスのインスタンス生成
                AbstractHSScheduler idSch = idSCHFactory(param[0].getJobType());
                // 作業中データを持たない処理の場合は終了する
                if (idSch == null)
                {
                    // 6023115 = 他端末で処理されたため、処理を中断しました。
                    setMessage("6023115");
                    return false;
                }
                // コネクション設定
                idSch.setConnection(getConnection());
                idSch.setParent(this.getClass());
                idSch.setLocale(this.getLocale());

                if (rftController.isWorkingData(param[0].getTerminalNo()))
                {
                    // 入力パラメータ生成
                    ScheduleParams inParam = null;

                    // 出庫
                    if (SystemDefine.JOB_TYPE_RETRIEVAL.equals(param[0].getJobType()))
                    {
                        inParam =
                                (ScheduleParams)createRetrievalInParams(param[0].getTerminalNo(),
                                        param[0].getWmsUserInfo());
                        
                        // 確定処理を行う
                        idSch.startSCH(inParam);
                        
                        // RFT作業状態が更新されないデータについては完了にしておく
                        WorkInfoHandler workHndlr = new WorkInfoHandler(getConnection());
                        WorkInfoSearchKey workSKey = new WorkInfoSearchKey();
                        
                        workSKey.setSettingUnitKey(inParam.getString(RetCompleteSCHParams.SETTING_UNIT_KEY));
                        workSKey.setRftStatusFlag(WorkInfo.STATUS_FLAG_COMPLETION, "!=");
                        if (workHndlr.count(workSKey) > 0)
                        {
                            WorkInfoAlterKey workAKey = new WorkInfoAlterKey();
                            
                            workAKey.setSettingUnitKey(inParam.getString(RetCompleteSCHParams.SETTING_UNIT_KEY));
                            workAKey.setRftStatusFlag(WorkInfo.STATUS_FLAG_COMPLETION, "!=");
                            workAKey.updateRftStatusFlag(WorkInfo.STATUS_FLAG_COMPLETION);
                            
                            // 更新
                            workHndlr.modify(workAKey);
                        }
                    }
                    // 出荷
                    else if (SystemDefine.JOB_TYPE_SHIPPING.equals(param[0].getJobType()))
                    {
                        inParam =
                                (ScheduleParams)createShipInParams(param[0].getTerminalNo(), param[0].getWmsUserInfo(),
                                        param[0].getLackFlag());
                        
                        // 確定処理を行う
                        idSch.startSCH(inParam);
                    }
                }
                else
                {
                    // 6023115 = 他端末で処理されたため、処理を中断しました。
                    setMessage("6023115");
                    return false;
                }
            }
            // 作業中データクリア
            rftController.eraseWorkingData(param[0].getTerminalNo());
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return false;
        }
        catch (ScheduleException e)
        {
            // 6007001 = スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください。
            setMessage("6007001");
            return false;
        }
        catch (Exception e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return false;
        }
        return true;
    }

    /**
     * 指定された作業種別のスケジュールクラスのインスタンスを生成します。<BR>
     * このメソッドを呼び出す前にパッケージマネージャーを初期化しておく必要があります。<BR>
     *
     * @param jobType   作業種別
     * @return 作業種別ごとの完了処理クラスのインスタンス。<BR>
     *          指定された作業種別が作業中の状態を持たない作業の場合はnullを返します。
     * @throws ScheduleException チェック処理内で予期しない例外が発生した場合に通知されます。
     */
    protected AbstractHSScheduler idSCHFactory(String jobType)
            throws ScheduleException
    {
        // オペレータクラス
        AbstractHSScheduler idSch = null;
        // クラス名
        String className = "";

        // 各作業種別ごとの処理用インスタンスを生成する
        if (SystemDefine.JOB_TYPE_RETRIEVAL.equals(jobType))
        {
            // 出庫
            className = RETRIEVAL_SCH_CLASS_NAME;
        }
        else if (SystemDefine.JOB_TYPE_SORTING.equals(jobType))
        {
            // 仕分け
            className = SORTING_SCH_CLASS_NAME;
        }
        else if (SystemDefine.JOB_TYPE_SHIPPING.equals(jobType))
        {
            // 出荷
            className = SHIPPING_SCH_CLASS_NAME;
        }
        else
        {
            // 作業中の状態を持たない作業の場合は処理を終了する
            return null;
        }

        try
        {
            // スケジュールクラスを生成する
            idSch = (AbstractHSScheduler)PackageManager.getObject(className, this.getClass());
        }
        catch (IllegalAccessException e)
        {
            throw new ScheduleException(e.getMessage());
        }
        catch (Exception e)
        {
            throw new ScheduleException(e.getMessage());
        }
        return idSch;
    }

    /**
     * 入庫予定情報のキャンセル処理を行います。
     * @param work 入出庫作業情報
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void cancelStoragePlan(WorkInfo work)
            throws CommonException
    {
        // 入庫予定情報
        StoragePlanHandler storageHandler = new StoragePlanHandler(getConnection());
        StoragePlanSearchKey storageSKey = new StoragePlanSearchKey();
        StoragePlanAlterKey storageAKey = new StoragePlanAlterKey();

        // 検索条件
        // 予定一意キー
        storageSKey.setPlanUkey(work.getPlanUkey());

        StoragePlan storageEnt = (StoragePlan)storageHandler.findPrimaryForUpdate(storageSKey);

        // 更新条件設定
        storageAKey.clear();
        // 予定一意キー
        storageAKey.setPlanUkey(storageEnt.getPlanUkey());

        // 更新値設定
        // 最終更新処理名
        storageAKey.updateLastUpdatePname(this.getClass().getSimpleName());
        // 状態フラグ
        WorkInfoController workInfoCont = new WorkInfoController(getConnection(), getClass());
        String status = workInfoCont.getPlanStatus(storageEnt.getPlanUkey());
        storageAKey.updateStatusFlag(status);

        // 出庫予定情報更新
        storageHandler.modify(storageAKey);
    }

    /**
     * 出庫予定情報のキャンセル処理を行います。
     * @param work 入出庫作業情報
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void cancelRetrievalPlan(WorkInfo work)
            throws CommonException
    {
        // 出庫予定情報
        RetrievalPlanHandler retrievalHandler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey retrievalSKey = new RetrievalPlanSearchKey();
        RetrievalPlanAlterKey retrievalAKey = new RetrievalPlanAlterKey();

        // 検索条件
        // 予定一意キー
        retrievalSKey.setPlanUkey(work.getPlanUkey());

        RetrievalPlan retrievalEnt = (RetrievalPlan)retrievalHandler.findPrimaryForUpdate(retrievalSKey);

        // 更新条件設定
        retrievalAKey.clear();
        // 予定一意キー
        retrievalAKey.setPlanUkey(retrievalEnt.getPlanUkey());

        // 更新値設定
        // 最終更新処理名
        retrievalAKey.updateLastUpdatePname(this.getClass().getSimpleName());
        // 状態フラグ
        WorkInfoController workInfoCont = new WorkInfoController(getConnection(), getClass());
        String status = workInfoCont.getPlanStatus(retrievalEnt.getPlanUkey());
        retrievalAKey.updateStatusFlag(status);

        // 出庫予定情報更新
        retrievalHandler.modify(retrievalAKey);
    }

    /**
     * 入荷作業情報のキャンセル処理を行います。
     * @param rftNo RFT号機No
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void cancelReceivingWorkInfo(String rftNo)
            throws CommonException
    {
        // 入荷作業情報ハンドラのインスタンス生成
        ReceivingWorkInfoHandler receivHndlr = new ReceivingWorkInfoHandler(getConnection());
        ReceivingWorkInfoSearchKey receSKey = new ReceivingWorkInfoSearchKey();
        ReceivingWorkInfoAlterKey receAKey = new ReceivingWorkInfoAlterKey();

        try
        {
            // 検索条件設定
            // RFT号機No.
            receSKey.setTerminalNo(rftNo);
            // 状態フラグ(作業中)
            receSKey.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);
            // ハードウェア区分
            receSKey.setHardwareType(WorkInfo.HARDWARE_TYPE_RFT);

            // 入荷作業情報取得
            ReceivingWorkInfo[] receivingEnt = (ReceivingWorkInfo[])receivHndlr.findForUpdate(receSKey);

            for (int i = 0; i < receivingEnt.length; i++)
            {
                // 更新条件設定
                receAKey.clear();
                // 作業No.
                receAKey.setJobNo(receivingEnt[i].getJobNo());

                // 更新値設定
                // 設定単位キー
                receAKey.updateSettingUnitKey(null);
                // 集約作業No.
                receAKey.updateCollectJobNo(null);
                // 状態フラグ(未開始)
                receAKey.updateStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
                // ハードウェア区分
                receAKey.updateHardwareType(WorkInfo.HARDWARE_TYPE_UNSTART);
                // ユーザID
                receAKey.updateUserId(null);
                // 端末No.
                receAKey.updateTerminalNo(null);
                // 最終更新処理名
                receAKey.updateLastUpdatePname(this.getClass().getSimpleName());

                // 入荷作業情報更新
                receivHndlr.modify(receAKey);

                // 入荷予定情報の更新
                cancelReceivingPlan(receivingEnt[i]);
            }
        }
        catch (LockTimeOutException e)
        {
            // 6026018 = 一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
            setMessage(WmsMessageFormatter.format(6026018, WorkInfo.STORE_NAME));
            return;
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return;
        }
    }

    /**
     * 入荷予定情報、TC予定情報のキャンセル処理を行います。
     * 作業情報のTC/DC区分より、入荷予定又は、TC予定を未作業に更新します。
     * @param rWorkInfo 入荷作業情報
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void cancelReceivingPlan(ReceivingWorkInfo rWorkInfo)
            throws CommonException
    {
        try
        {
            if (rWorkInfo.getTcdcFlag().equals(ReceivingWorkInfo.TCDC_FLAG_DC))
            {
                // 入荷予定情報の更新
                ReceivingWorkInfoController wic = new ReceivingWorkInfoController(getConnection(), getParent());
                String pStatus = wic.getReceivingPlanStatus(rWorkInfo.getPlanUkey());

                // update if plan status is NOT STARTED
                if (SystemDefine.STATUS_FLAG_UNSTART.equals(pStatus))
                {
                    ReceivingPlanHandler rcvHdl = new ReceivingPlanHandler(getConnection());
                    ReceivingPlanAlterKey rcvAKey = new ReceivingPlanAlterKey();

                    // 予定一意キー対応の入荷予定情報を未作業に更新します。
                    rcvAKey.setPlanUkey(rWorkInfo.getPlanUkey());
                    rcvAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
                    rcvAKey.updateLastUpdatePname(this.getClass().getSimpleName());

                    rcvHdl.modify(rcvAKey);
                }
            }
            else
            {
                // TC予定情報の更新
                getCrossDockPlanCancel(rWorkInfo.getPlanUkey());
            }
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return;
        }
    }

    /**
     * TC予定情報キャンセル<BR>
     * パラメータのクロスドック連携キーにて入荷作業情報、TC予定情報の検索を行い、セットすべき予定情報の状態を返します。
     * 
     * @param planUkey 予定一意キー
     * @return 状態フラグ
     * @throws ReadWriteException データベース処理でエラー発生
     * @throws NotFoundException 該当データなし
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在
     */
    public String getCrossDockPlanCancel(String planUkey)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException
    {
        // 入荷作業情報、TC予定情報を検索する
        CrossDockPlanHandler handler = new CrossDockPlanHandler(getConnection());
        CrossDockPlanSearchKey skey = new CrossDockPlanSearchKey();

        // 取得項目
        skey.setCollect(ReceivingWorkInfo.STATUS_FLAG, "MAX", null);
        skey.setPlanUkeyCollect("MAX");

        // 結合
        skey.setJoin(CrossDockPlan.CROSS_DOCK_UKEY, ReceivingWorkInfo.CROSS_DOCK_UKEY);

        // 検索条件
        skey.setPlanUkey(planUkey);
        skey.setKey(ReceivingWorkInfo.STATUS_FLAG, SystemDefine.STATUS_FLAG_DELETE, "!=", "", "", true);

        // データ検索
        CrossDockPlan plan = (CrossDockPlan)handler.findPrimary(skey);

        // データが存在しなかった場合
        if (plan == null)
        {
            throw new NotFoundException();
        }
        else if (StringUtil.isBlank(plan.getPlanUkey()))
        {
            throw new NotFoundException();
        }

        // 戻り値(状態フラグ)
        String status = null;

        // MAX（入荷作業情報．状態フラグ）が(未作業)の場合
        if (SystemDefine.STATUS_FLAG_UNSTART.equals(plan.getStatusFlag()))
        {
            CrossDockPlanAlterKey akey = new CrossDockPlanAlterKey();
            akey.setPlanUkey(planUkey);
            akey.updateStatusFlag(CrossDockPlan.STATUS_FLAG_UNSTART);
            akey.updateLastUpdatePname(this.getClass().getSimpleName());
            handler.modify(akey);
        }

        return status;
    }

    /**
     * 入出庫作業情報のキャンセル処理を行います。
     * @param rftNo RFT号機No
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected void cancelWorkInfo(String rftNo)
            throws CommonException
    {
        // 入出庫作業情報ハンドラのインスタンス生成
        WorkInfoHandler workHndlr = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey workSKey = new WorkInfoSearchKey();
        WorkInfoAlterKey workAKey = new WorkInfoAlterKey();

        try
        {
            // 検索条件設定
            // RFT号機No.
            workSKey.setTerminalNo(rftNo);
            // 状態フラグ(作業中)
            workSKey.setStatusFlag(WorkInfo.STATUS_FLAG_NOWWORKING);
            // ハードウェア区分
            workSKey.setHardwareType(WorkInfo.HARDWARE_TYPE_RFT);

            // 入出庫作業情報取得
            WorkInfo[] workEnt = (WorkInfo[])workHndlr.findForUpdate(workSKey);

            for (int i = 0; i < workEnt.length; i++)
            {
                // 更新条件設定
                workAKey.clear();
                // 作業No.
                workAKey.setJobNo(workEnt[i].getJobNo());

                // 更新値設定
                // 設定単位キー
                workAKey.updateSettingUnitKey(null);
                // 集約作業No.
                workAKey.updateCollectJobNo(null);
                // 状態フラグ(未開始)
                workAKey.updateStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
                // RFT状態フラグ(未開始)
                workAKey.updateRftStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
                // ハードウェア区分
                workAKey.updateHardwareType(WorkInfo.HARDWARE_TYPE_UNSTART);
                // ユーザID
                workAKey.updateUserId(null);
                // 端末No.
                workAKey.updateTerminalNo(null);
                // 最終更新処理名
                workAKey.updateLastUpdatePname(this.getClass().getSimpleName());

                // 入出庫作業情報更新
                workHndlr.modify(workAKey);


                if (workEnt[i].getJobType().equals(WorkInfo.JOB_TYPE_STORAGE))
                {
                    // 入庫予定情報キャンセル処理を行う
                    cancelStoragePlan(workEnt[i]);
                }
                else if (workEnt[i].getJobType().equals(WorkInfo.JOB_TYPE_RETRIEVAL))
                {
                    // 出庫予定情報キャンセル処理を行う
                    cancelRetrievalPlan(workEnt[i]);
                }
            }
        }
        catch (LockTimeOutException e)
        {
            // 6026018 = 一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
            setMessage(WmsMessageFormatter.format(6026018, WorkInfo.STORE_NAME));
            return;
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return;
        }
    }

    /**
     * 仕分けのキャンセル処理を行います。
     * @param rftNo RFT号機No
     */
    protected void cancelSortWorkInfo(String rftNo)
    {
        // 仕分作業情報ハンドラのインスタンス生成
        SortWorkInfoHandler sortHndlr = new SortWorkInfoHandler(getConnection());
        SortWorkInfoSearchKey sortSKey = new SortWorkInfoSearchKey();
        SortWorkInfoAlterKey sortAKey = new SortWorkInfoAlterKey();

        try
        {
            // 検索条件設定
            // RFT号機No.
            sortSKey.setTerminalNo(rftNo);
            // 状態フラグ(作業中)
            sortSKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_NOWWORKING);
            // ハードウェア区分
            sortSKey.setHardwareType(MoveWorkInfo.HARDWARE_TYPE_RFT);

            // 仕分作業情報取得
            SortWorkInfo[] shipEnt = (SortWorkInfo[])sortHndlr.findForUpdate(sortSKey);

            for (int i = 0; i < shipEnt.length; i++)
            {
                // 更新条件設定
                sortAKey.clear();
                // 作業No.
                sortAKey.setJobNo(shipEnt[i].getJobNo());

                // 更新値設定
                // 設定単位キー
                sortAKey.updateSettingUnitKey(null);
                // 集約作業No.
                sortAKey.updateCollectJobNo(null);
                // 状態フラグ(未開始)
                sortAKey.updateStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
                // ハードウェア区分
                sortAKey.updateHardwareType(WorkInfo.HARDWARE_TYPE_UNSTART);
                // ユーザID
                sortAKey.updateUserId(null);
                // sortAKey末No.
                sortAKey.updateTerminalNo(null);
                // 最終更新処理名
                sortAKey.updateLastUpdatePname(this.getClass().getSimpleName());

                // 仕分作業情報更新
                sortHndlr.modify(sortAKey);
            }
        }
        catch (LockTimeOutException e)
        {
            // 6026018 = 一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
            setMessage(WmsMessageFormatter.format(6026018, MoveWorkInfo.STORE_NAME));
            return;
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return;
        }
    }

    /**
     * 出荷のキャンセル処理を行います。(出荷検品)
     * @param rftNo RFT号機No
     */
    protected void cancelShipWorkInfo(String rftNo)
    {
        // 出荷作業情報ハンドラのインスタンス生成
        ShipWorkInfoHandler shipHndlr = new ShipWorkInfoHandler(getConnection());
        ShipWorkInfoSearchKey shipSKey = new ShipWorkInfoSearchKey();
        ShipWorkInfoAlterKey shipAKey = new ShipWorkInfoAlterKey();

        try
        {
            // 検索条件設定
            // RFT号機No.
            shipSKey.setShipTerminalNo(rftNo);
            // 状態フラグ(作業中)
            shipSKey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
            // ハードウェア区分
            shipSKey.setHardwareType(ShipWorkInfo.HARDWARE_TYPE_RFT);

            // 出荷作業情報取得
            ShipWorkInfo[] shipEnt = (ShipWorkInfo[])shipHndlr.findForUpdate(shipSKey);

            for (int i = 0; i < shipEnt.length; i++)
            {
                // 更新条件設定
                shipAKey.clear();
                // 作業No.
                shipAKey.setJobNo(shipEnt[i].getJobNo());

                // 更新値設定
                // 設定単位キー
                shipAKey.updateSettingUnitKey(null);
                // 集約作業No.
                shipAKey.updateCollectJobNo(null);
                // 状態フラグ(未開始)
                shipAKey.updateWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_UNSTART);
                // 
                if (shipEnt[i].getBerthStatusFlag().equals(ShipWorkInfo.STATUS_FLAG_UNSTART))
                {
                    // ハードウェア区分
                    shipAKey.updateHardwareType(ShipWorkInfo.HARDWARE_TYPE_UNSTART);
                }
                // ユーザID
                shipAKey.updateShipUserId(null);
                // 端末No.
                shipAKey.updateShipTerminalNo(null);
                // 最終更新処理名
                shipAKey.updateLastUpdatePname(this.getClass().getSimpleName());

                // 出荷作業情報更新
                shipHndlr.modify(shipAKey);

                // 出荷予定情報更新
                cancelShipPlan(shipEnt[i].getPlanUkey());
            }
        }
        catch (LockTimeOutException e)
        {
            // 6026018 = 一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
            setMessage(WmsMessageFormatter.format(6026018, ShipWorkInfo.STORE_NAME));
            return;
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return;
        }
    }

    /**
     * 出荷予定情報のキャンセル処理を行います。
     * 出荷検品状態フラグを未作業に更新します。
     * @param planUkey 出荷予定一意キー
     * @throws ReadWriteException データベース処理でエラー発生
     * @throws NotFoundException 該当データなし
     */
    protected void cancelShipPlan(String planUkey)
            throws ReadWriteException,
                NotFoundException
    {
        try
        {
            // 出荷作業情報を検索する。
            ShipWorkInfoHandler wih = new ShipWorkInfoHandler(getConnection());
            ShipWorkInfoSearchKey wikey = new ShipWorkInfoSearchKey();

            // 取得項目
            wikey.setCollect(ShipWorkInfo.WORK_STATUS_FLAG, "MAX", null);

            // 検索条件
            wikey.setPlanUkey(planUkey);
            wikey.setWorkStatusFlag(ShipWorkInfo.STATUS_FLAG_DELETE, "!=");

            // データ検索
            ShipWorkInfo sWorkInfo = (ShipWorkInfo)wih.findPrimary(wikey);
            // データが存在しなかった場合
            if (sWorkInfo == null)
            {
                throw new NotFoundException();
            }

            // MAX（出荷作業情報．出荷検品状態フラグ）が(未作業)の場合
            if (ShipWorkInfo.STATUS_FLAG_UNSTART.equals(sWorkInfo.getWorkStatusFlag()))
            {
                // 出荷予定情報の更新
                ShipPlanHandler shipHdl = new ShipPlanHandler(getConnection());
                ShipPlanAlterKey shipAKey = new ShipPlanAlterKey();

                // 予定一意キー対応の出荷予定情報の(出荷検品状態フラグ)を更新します。
                shipAKey.setPlanUkey(planUkey);
                shipAKey.updateWorkStatusFlag(ShipPlan.STATUS_FLAG_UNSTART);
                shipAKey.updateLastUpdatePname(this.getClass().getSimpleName());
                shipHdl.modify(shipAKey);
            }
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException();
        }
    }

    /**
     * 出荷のキャンセル処理を行います。(バース登録)
     * @param rftNo RFT号機No
     */
    protected void cancelBerthShipWorkInfo(String rftNo)
    {
        // 出荷作業情報ハンドラのインスタンス生成
        ShipWorkInfoHandler shipHndlr = new ShipWorkInfoHandler(getConnection());
        ShipWorkInfoSearchKey shipSKey = new ShipWorkInfoSearchKey();
        ShipWorkInfoAlterKey shipAKey = new ShipWorkInfoAlterKey();

        try
        {
            // 検索条件設定
            // RFT号機No.
            shipSKey.setBerthTerminalNo(rftNo);
            // 状態フラグ(作業中)
            shipSKey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_NOWWORKING);
            // ハードウェア区分
            shipSKey.setHardwareType(ShipWorkInfo.HARDWARE_TYPE_RFT);

            // 出荷作業情報取得
            ShipWorkInfo[] shipEnt = (ShipWorkInfo[])shipHndlr.findForUpdate(shipSKey);

            for (int i = 0; i < shipEnt.length; i++)
            {
                // 更新条件設定
                shipAKey.clear();
                // 作業No.
                shipAKey.setJobNo(shipEnt[i].getJobNo());

                // 更新値設定
                // 設定単位キー
                shipAKey.updateSettingUnitKey(null);
                // 集約作業No.
                shipAKey.updateCollectJobNo(null);
                // 状態フラグ(未開始)
                shipAKey.updateBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_UNSTART);
                //
                if (shipEnt[i].getWorkStatusFlag().equals(ShipWorkInfo.STATUS_FLAG_UNSTART))
                {
                    // ハードウェア区分
                    shipAKey.updateHardwareType(ShipWorkInfo.HARDWARE_TYPE_UNSTART);
                }
                // ユーザID
                shipAKey.updateBerthUserId(null);
                // 端末No.
                shipAKey.updateBerthTerminalNo(null);
                // 最終更新処理名
                shipAKey.updateLastUpdatePname(this.getClass().getSimpleName());

                // 出荷作業情報更新
                shipHndlr.modify(shipAKey);

                // 出荷予定情報更新
                cancelBerthShipPlan(shipEnt[i].getPlanUkey());
            }
        }
        catch (LockTimeOutException e)
        {
            // 6026018 = 一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
            setMessage(WmsMessageFormatter.format(6026018, ShipWorkInfo.STORE_NAME));
            return;
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return;
        }
    }

    /**
     * 出荷予定情報のキャンセル処理を行います。
     * バース登録状態フラグを未作業に更新します。
     * @param planUkey 出荷予定一意キー
     * @throws ReadWriteException データベース処理でエラー発生
     * @throws NotFoundException 該当データなし
     */
    protected void cancelBerthShipPlan(String planUkey)
            throws ReadWriteException,
                NotFoundException
    {
        try
        {
            // 出荷作業情報を検索する。
            ShipWorkInfoHandler wih = new ShipWorkInfoHandler(getConnection());
            ShipWorkInfoSearchKey wikey = new ShipWorkInfoSearchKey();

            // 取得項目
            wikey.setCollect(ShipWorkInfo.BERTH_STATUS_FLAG, "MAX", null);

            // 検索条件
            wikey.setPlanUkey(planUkey);
            wikey.setBerthStatusFlag(ShipWorkInfo.STATUS_FLAG_DELETE, "!=");

            // データ検索
            ShipWorkInfo sWorkInfo = (ShipWorkInfo)wih.findPrimary(wikey);
            // データが存在しなかった場合
            if (sWorkInfo == null)
            {
                throw new NotFoundException();
            }

            // MAX（出荷作業情報．バース登録状態フラグ）が(未作業)の場合
            if (ShipPlan.STATUS_FLAG_UNSTART.equals(sWorkInfo.getBerthStatusFlag()))
            {
                // 出荷予定情報の更新
                ShipPlanHandler shipHdl = new ShipPlanHandler(getConnection());
                ShipPlanAlterKey shipAKey = new ShipPlanAlterKey();

                // 予定一意キー対応の出荷予定情報の(バース登録状態フラグ)を更新します。
                shipAKey.setPlanUkey(planUkey);
                shipAKey.updateBerthStatusFlag(ShipPlan.STATUS_FLAG_UNSTART);
                shipAKey.updateLastUpdatePname(this.getClass().getSimpleName());
                shipHdl.modify(shipAKey);
            }
        }
        catch (NoPrimaryException e)
        {
            throw new ReadWriteException();
        }
    }

    /**
     * 移動入庫のキャンセル処理を行います。
     * @param param 画面入力パラメータ
     */
    protected void cancelMoveStorageWorkInfo(SystemInParameter param)
    {
        // 移動作業情報ハンドラのインスタンス生成
        MoveWorkInfoHandler moveHndlr = new MoveWorkInfoHandler(getConnection());
        MoveWorkInfoSearchKey moveSKey = new MoveWorkInfoSearchKey();
        MoveWorkInfoAlterKey moveAKey = new MoveWorkInfoAlterKey();

        try
        {
            // 検索条件設定
            // RFT号機No.(移動入庫端末No.)
            moveSKey.setStorageTerminalNo(param.getTerminalNo());
            // 状態フラグ(入庫作業中)
            moveSKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_MOVE_STORAGE_WORKING);
            // ハードウェア区分
            moveSKey.setHardwareType(MoveWorkInfo.HARDWARE_TYPE_RFT);

            // 移動作業情報取得
            MoveWorkInfo[] moveEnt = (MoveWorkInfo[])moveHndlr.findForUpdate(moveSKey);

            for (int i = 0; i < moveEnt.length; i++)
            {
                if (moveEnt[i].getReceivingFlag().equals(MoveWorkInfo.RECEIVING_FLAG_RECEIVEING_STORAGE))
                {
                    // 入荷入庫の場合は、出庫した元の場所に戻す。
                    cancelMoveStorageWorking(param, moveEnt[i]);
                }
                else
                {
                    // 入荷入庫以外
                    moveAKey.clear();
                    // 作業No.
                    moveAKey.setJobNo(moveEnt[i].getJobNo());

                    // 更新値設定
                    // 状態フラグ(出庫済入庫待)
                    moveAKey.updateStatusFlag(MoveWorkInfo.STATUS_FLAG_MOVE_STORAGE_WAITING);
                    // ユーザID(移動入庫ユーザ)
                    moveAKey.updateStorageUserId(null);
                    // 端末No.(移動入庫端末No.)
                    moveAKey.updateStorageTerminalNo(null);
                    // 最終更新処理名
                    moveAKey.updateLastUpdatePname(this.getClass().getSimpleName());

                    // 移動作業情報更新
                    moveHndlr.modify(moveAKey);
                }
            }
        }
        catch (LockTimeOutException e)
        {
            // 6026018 = 一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
            setMessage(WmsMessageFormatter.format(6026018, MoveWorkInfo.STORE_NAME));
            return;
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return;
        }
    }

    /**
     * 入庫完了処理を行ないます。
     * @param sysParam 画面パラメータ
     * @param mvWorkInfo 移動作業情報
     */
    protected void cancelMoveStorageWorking(SystemInParameter sysParam, MoveWorkInfo mvWorkInfo)
    {
        try
        {
            StockInParameter param = new StockInParameter(getWmsUserInfo());
            param.setTerminalNo(sysParam.getTerminalNo());
            param.setUserId(sysParam.getUserId());

            // 在庫移動オペレータの作成
            MoveOperator moOperator = new MoveOperator(getConnection(), getClass());

            // 作業情報情報コントローラの作成
            RftController rController = new RftController(getConnection(), getClass());

            // パラメータクラスにセット
            //  (作業No)
            param.setJobNo(mvWorkInfo.getJobNo());
            //  (ユーザID)
            param.setUserId(sysParam.getUserId());
            //  (荷主コード)
            param.setConsignorCode(mvWorkInfo.getConsignorCode());
            //  (商品コード)
            param.setItemCode(mvWorkInfo.getItemCode());
            //  (ロット)
            param.setLotNo(mvWorkInfo.getLotNo());
            //  (移動元エリアNo)
            param.setSourceAreaNo(mvWorkInfo.getRetrievalAreaNo());
            //  (移動元棚)
            param.setSourceLocation(mvWorkInfo.getRetrievalLocationNo());
            //  (移動先エリアNo)
            param.setDestAreaNo(mvWorkInfo.getRetrievalAreaNo());
            //  (移動先棚)
            param.setDestLocation(mvWorkInfo.getRetrievalLocationNo());
            //  (移動入庫実績数)
            param.setMovementQty(mvWorkInfo.getPlanQty());
            //  (作業時間)
            param.setWorkSeconds(0);
            //  (完了フラグ)
            param.setCompletionFlag(MoveWorkInfo.STATUS_FLAG_COMPLETION);
            //  (設定単位キー)
            param.setSettingUnitKey(mvWorkInfo.getSettingUnitKey());

            /* ユーザ情報を取得し、パラメータにセット */
            param.setWmsUserInfo(rController.getUserInfo(param.getTerminalNo(), param.getUserId()));

            /* RFT移動入庫作業完了処理 */
            moOperator.completeStorage(param);
        }
        catch (LockTimeOutException e)
        {
            // 6026018 = 一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
            setMessage(WmsMessageFormatter.format(6026018, MoveWorkInfo.STORE_NAME));
            return;
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return;
        }
        catch (NoPrimaryException e)
        {
            // 6003011=対象データはありませんでした。
            setMessage("6003011");
            return;
        }
        catch (ScheduleException e)
        {
            // 6007001 = スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください。
            setMessage("6007001");
            return;
        }
        catch (OperatorException e)
        {
            // 6007001 = スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください。
            setMessage("6007001");
            return;
        }
    }

    /**
     * 移動出庫のキャンセル処理を行います。
     * @param rftNo RFT号機No
     */
    protected void cancelMoveRetrievalWorkInfo(String rftNo)
    {
        // 移動作業情報ハンドラのインスタンス生成
        MoveWorkInfoHandler moveHndlr = new MoveWorkInfoHandler(getConnection());
        MoveWorkInfoSearchKey moveSKey = new MoveWorkInfoSearchKey();
        MoveWorkInfoAlterKey moveAKey = new MoveWorkInfoAlterKey();

        try
        {
            // 検索条件設定
            // RFT号機No.(移動出庫端末No.)
            moveSKey.setRetrievalTerminalNo(rftNo);
            // 状態フラグ(出庫作業中)
            moveSKey.setStatusFlag(MoveWorkInfo.STATUS_FLAG_MOVE_RETRIEVAL_WORKING);
            // ハードウェア区分
            moveSKey.setHardwareType(MoveWorkInfo.HARDWARE_TYPE_RFT);

            // 移動作業情報取得
            MoveWorkInfo[] moveEnt = (MoveWorkInfo[])moveHndlr.findForUpdate(moveSKey);

            for (int i = 0; i < moveEnt.length; i++)
            {
                // 更新条件設定
                moveAKey.clear();
                // 作業No.
                moveAKey.setJobNo(moveEnt[i].getJobNo());

                // 更新値設定
                // 状態フラグ(未作業)
                moveAKey.updateStatusFlag(MoveWorkInfo.STATUS_FLAG_UNSTART);
                // ユーザID(移動出庫ユーザ)
                moveAKey.updateRetrievalUserId(null);
                // 端末No.(移動出庫端末No.)
                moveAKey.updateRetrievalTerminalNo(null);
                // 最終更新処理名
                moveAKey.updateLastUpdatePname(this.getClass().getSimpleName());

                // 移動作業情報更新
                moveHndlr.modify(moveAKey);
            }
        }
        catch (LockTimeOutException e)
        {
            // 6026018 = 一定時間経過後も、データベースのロックが解除されませんでした。テーブル名:{0}
            setMessage(WmsMessageFormatter.format(6026018, MoveWorkInfo.STORE_NAME));
            return;
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return;
        }
    }

    /**
     * 棚卸作業のキャンセル処理を行います。
     * @param rftNo RFT号機No
     */
    protected void cancelInventWorkInfo(String rftNo)
    {
        // 棚卸作業情報ハンドラのインスタンス生成
        InventWorkInfoHandler inventHndlr = new InventWorkInfoHandler(getConnection());

        // 棚卸作業情報検索キー
        InventWorkInfoSearchKey sKey = new InventWorkInfoSearchKey();

        // 棚卸作業情報更新キー
        InventWorkInfoAlterKey aKey = new InventWorkInfoAlterKey();

        try
        {
            // 検索条件設定
            // RFT号機No.
            sKey.setTerminalNo(rftNo);
            // 状態フラグ(作業中)
            sKey.setStatusFlag(InventWorkInfo.STATUS_FLAG_NOWWORKING);

            InventWorkInfo inventWorkInfo = (InventWorkInfo)inventHndlr.findPrimary(sKey);

            if (inventWorkInfo == null)
            {
                // 該当するデータ無しの場合
                throw new NoPrimaryException();
            }

            // 取得した元状態フラグのチェック
            if (StringUtil.isBlank(inventWorkInfo.getPreviousStatus()))
            {
                // 取得した元状態フラグがNullの場合

                /* 削除条件の指定 */
                // キーのクリア
                sKey.clear();
                // 作業No.
                sKey.setJobNo(inventWorkInfo.getJobNo());
                // 棚卸作業情報削除       
                inventHndlr.drop(sKey);
            }
            else
            {
                // 取得した元状態フラグがNull以外の場合
                /* 更新条件の指定 */
                // 作業No.
                aKey.setJobNo(inventWorkInfo.getJobNo());

                // RFT号機No.
                sKey.setTerminalNo(rftNo);
                // 状態フラグ(作業中)
                sKey.setStatusFlag(InventWorkInfo.STATUS_FLAG_NOWWORKING);

                /* 更新内容の指定 */
                // 状態フラグ
                aKey.updateStatusFlag(inventWorkInfo.getPreviousStatus());
                // 元状態フラグ
                aKey.updatePreviousStatus("");
                // 棚卸ユーザID
                aKey.updateUserId(inventWorkInfo.getPreUserId());
                // 元棚卸ユーザID
                aKey.updatePreUserId("");
                // 棚卸端末No.
                aKey.updateTerminalNo(inventWorkInfo.getPreTerminalNo());
                // 元棚卸端末No.
                aKey.updatePreTerminalNo("");
                // 最終更新処理名
                aKey.updateLastUpdatePname(this.getClass().getSimpleName());

                // 棚卸作業情報の更新
                inventHndlr.modify(aKey);
            }

            return;
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return;
        }
        catch (NoPrimaryException e)
        {
            // 6003011=対象データはありませんでした。
            setMessage("6003011");
            return;
        }
    }

    /**
     * 出庫完了パラメータを作成します。
     * @param rftNo 端末No.
     * @param info WmsUserInfo
     * @return 出庫完了パラメータ
     */
    protected Params createRetrievalInParams(String rftNo, WmsUserInfo info)
    {
        try
        {
            // オーダー出庫スケジュール
            RetReStartSCHParams param = new RetReStartSCHParams();
            param.set(RetReStartSCHParams.TERMINAL_NO, rftNo);
            RetReStartSCH startSCH = new RetReStartSCH(getConnection(), this.getClass(), this.getLocale());

            // オーダー出庫取得
            List<Params> outParams = startSCH.query(param);
            RetCompleteSCHParams inParam = new RetCompleteSCHParams();

            for (Params outparam : outParams)
            {
                inParam.set(RetCompleteSCHParams.TERMINAL_NO, info.getTerminalNo());
                inParam.set(RetCompleteSCHParams.USER_ID, info.getUserId());
                inParam.set(RetCompleteSCHParams.CONSIGNOR_CODE, outparam.get(RetReStartSCHParams.CONSIGNOR_CODE));
                inParam.set(RetCompleteSCHParams.AREA_NO, outparam.get(RetReStartSCHParams.AREA_NO));
                inParam.set(RetCompleteSCHParams.SETTING_UNIT_KEY, outparam.get(RetReStartSCHParams.SETTING_UNIT_KEY));
                inParam.set(RetCompleteSCHParams.START_DATE_TIME, null);
                inParam.set(RetCompleteSCHParams.MISS_SCAN_COUNT, 0);
                inParam.set(RetCompleteSCHParams.COMPLETION_FLAG, Parameter.COMPLETION_FLAG_DECISION);
                inParam.setProcessFlag(ProcessFlag.UPDATE);
                break;
            }
            return inParam;

        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return null;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return null;
        }
        catch (ScheduleException e)
        {
            // 6007001 = スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください。
            setMessage("6007001");
            return null;
        }
        catch (NoPrimaryException e)
        {
            // 6003011=対象データはありませんでした。
            setMessage("6003011");
            return null;
        }
        catch (CommonException e)
        {
            // 6007001 = スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください。
            setMessage("6007001");
            return null;
        }
    }

    /**
     * 出荷完了パラメータを作成します。
     * @param rftNo 端末No.
     * @param info WmsUserInfo
     * @return 出荷完了パラメータ
     */
    protected Params createShipInParams(String rftNo, WmsUserInfo info, String lackFlag)
    {
        try
        {
            // 出荷スケジュール
            ShpReStartSCH startSCH = new ShpReStartSCH(getConnection(), this.getClass(), this.getLocale());

            // パラメータ生成
            ShpReStartSCHParams param = new ShpReStartSCHParams();
            param.set(ShpReStartSCHParams.TERMINAL_NO, rftNo);

            // 作業データ取得
            List<Params> outParams = startSCH.query(param);
            ShpCompleteSCHParams inParam = new ShpCompleteSCHParams();

            // 出荷作業ファイルより作業ファイルパラメータを生成します。
            for (Params outparam : outParams)
            {
                inParam.set(ShpCompleteSCHParams.CONSIGNOR_CODE, outparam.get(ShpReStartSCHParams.CONSIGNOR_CODE));
                inParam.set(ShpCompleteSCHParams.BATCH_NO, outparam.get(ShpReStartSCHParams.BATCH_NO));
                inParam.set(ShpCompleteSCHParams.CUSTOMER_CODE, outparam.get(ShpReStartSCHParams.CUSTOMER_CODE));
                inParam.set(ShpCompleteSCHParams.CUSTOMER_NAME, outparam.get(ShpReStartSCHParams.CUSTOMER_NAME));
                inParam.set(ShpCompleteSCHParams.PLAN_DATE, outparam.get(ShpReStartSCHParams.PLAN_DATE));
                inParam.set(ShpCompleteSCHParams.START_DATE_TIME, null);
                inParam.set(ShpCompleteSCHParams.MISS_SCAN_COUNT, 0);
                inParam.set(ShpCompleteSCHParams.COMPLETION_FLAG, lackFlag);
                inParam.set(ShpCompleteSCHParams.SETTING_UNIT_KEY, outparam.get(ShpReStartSCHParams.SETTING_UNIT_KEY));
                inParam.set(ShpCompleteSCHParams.TERMINAL_NO, info.getTerminalNo());
                inParam.set(ShpCompleteSCHParams.USER_ID, info.getUserId());
                break;
            }
            return inParam;
        }
        catch (ReadWriteException e)
        {
            // 6007002 = データベースエラーが発生しました。メッセージログを参照してください。
            setMessage("6007002");
            return null;
        }
        catch (NotFoundException e)
        {
            // 6023115 = 他端末で処理されたため、処理を中断しました。
            setMessage("6023115");
            return null;
        }
        catch (ScheduleException e)
        {
            // 6007001 = スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください。
            setMessage("6007001");
            return null;
        }
        catch (NoPrimaryException e)
        {
            // 6003011=対象データはありませんでした。
            setMessage("6003011");
            return null;
        }
        catch (CommonException e)
        {
            // 6007001 = スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください。
            setMessage("6007001");
            return null;
        }
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * @return
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
