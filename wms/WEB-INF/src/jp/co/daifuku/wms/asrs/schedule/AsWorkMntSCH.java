// $Id: AsWorkMntSCH.java 7951 2010-05-24 09:10:14Z ota $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsWorkMntSCHParams.*;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.emanager.EmConstants;
import jp.co.daifuku.emanager.util.P11Config;
import jp.co.daifuku.emanager.util.P11LogWriter;
import jp.co.daifuku.foundation.common.ConvertUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.foundation.common.part11.Part11List;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator.CARRY_COMPLETE;
import jp.co.daifuku.wms.asrs.dasch.AsWorkMntDASCHOperator;
import jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator;
import jp.co.daifuku.wms.asrs.location.ShelfOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.StationOperatorFactory;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.AsPalletController;
import jp.co.daifuku.wms.base.controller.HostSendController;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.dbhandler.ArrivalHandler;
import jp.co.daifuku.wms.base.dbhandler.ArrivalSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.InOutResult;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.retrieval.allocate.ReleaseAllocateOperator;

/**
 * AS/RS 作業メンテナンスのスケジュール処理を行います。
 * 
 * @version $Revision: 7951 $, $Date: 2010-05-24 18:10:14 +0900 (月, 24 5 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ota $
 */
public class AsWorkMntSCH
        extends AbstractSCH
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
     * 指定されたパラメータでSCHを作成します。
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public AsWorkMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、
     * リストセルエリア出力用のデータをデータベースから取得して返します。<BR>
     *
     * @param p 表示データ取得条件を持つ<CODE>ScheduleParams</CODE><BR>
     * @return 検索結果を持つ<CODE>Params</CODE>配列。<BR>
     *          該当レコードが一件もみつからない場合は要素数0のリストを返します。<BR>
     *          検索結果が最大表示件数を超えた場合は最大表示件数まで表示します<BR>
     *          入力条件にエラーが発生した場合はnullを返します。<BR>
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public List<Params> query(ScheduleParams p)
            throws CommonException
    {
        // 搬送データの取得
        CarryInfoHandler carryHandler = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

        carryKey.setCollect(Stock.AREA_NO, "DISTINCT", Stock.AREA_NO);
        carryKey.setCollect(new FieldName(CarryInfo.STORE_NAME, FieldName.ALL_FIELDS));
        carryKey.setCarryKey(p.getString(CARRYING_KEY));
        carryKey.setJoin(CarryInfo.PALLET_ID, Stock.PALLET_ID);

        CarryInfo ci = (CarryInfo)carryHandler.findPrimary(carryKey);

        AreaController area = new AreaController(getConnection(), getClass());

        String location = null;

        // 該当データがない場合
        if (ci == null)
        {
            // 6023293=対象となる搬送データがありませんでした。
            setMessage("6023293");
            return new ArrayList<Params>();
        }

        Params param = new Params();

        List<Params> result = new ArrayList<Params>();

        // 棚No.、搬送元ステーションNo.、搬送先ステーションNo.、ステーションNo.を搬送区分から判断する
        if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag()))
        {
            // 入庫時は、ステーションから棚への搬送
            // 搬送先にはアイルをセットする
            param.set(FROM_CARRYING, ci.getSourceStationNo());
            param.set(TO_CARRYING, ci.getAisleStationNo());

            location = ci.getDestStationNo();
            try
            {
                location = area.getAreaNoOfWarehouse(location);
            }
            catch (ScheduleException ex)
            {
                location =
                        area.toDispLocation(String.valueOf(ci.getValue(Stock.AREA_NO)), area.toParamLocation(location));
            }
            param.set(LOCATION, location);
        }
        else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag()))
        {
            // 出庫時は、棚からステーションへの搬送
            // 搬送元にはアイルをセットする


            // 在庫確認の場合は出庫ロケーションNo.をセット
            if (CarryInfo.RETRIEVAL_DETAIL_INVENTORY_CHECK.equals(ci.getRetrievalDetail()))
            {
                location = ci.getRetrievalStationNo();
            }
            // 在庫確認以外の場合は搬送元ステーションをセット
            else
            {
                location = ci.getSourceStationNo();
            }

            if (!StringUtil.isBlank(location))
            {
                try
                {
                    location = area.getAreaNoOfWarehouse(location);
                }
                catch (ScheduleException ex)
                {
                    location =
                            area.toDispLocation(String.valueOf(ci.getValue(Stock.AREA_NO)),
                                    area.toParamLocation(location));
                }
                param.set(LOCATION, location);
            }
            else
            {
                param.set(LOCATION, "");
            }
            param.set(FROM_CARRYING, ci.getAisleStationNo());
            param.set(TO_CARRYING, ci.getDestStationNo());
        }
        else if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
        {
            // 棚間移動時は、棚から棚への移動
            // 画面上、ロケーションNo.にはなにも表示しない
            // 搬送元搬送先にはアイルをセットする
            // ステーションはなにもセットしない
            if (!StringUtil.isBlank(ci.getRetrievalStationNo()))
            {
                location = ci.getRetrievalStationNo();
                try
                {
                    location = area.getAreaNoOfWarehouse(location);
                }
                catch (ScheduleException ex)
                {
                    location =
                            area.toDispLocation(String.valueOf(ci.getValue(Stock.AREA_NO)),
                                    area.toParamLocation(location));
                }
                param.set(LOCATION, location);
            }
            else
            {
                param.set(LOCATION, "");
            }
            param.set(FROM_CARRYING, ci.getSourceStationNo());
            param.set(TO_CARRYING, ci.getDestStationNo());
        }
        else if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
        {
            // 直行時は、ステーションからステーションへの移動
            // ロケーションNo.にはなにも表示しない
            // 搬送元、搬送先には各ステーションNo.をセットする
            // 画面上、ステーションNo.にはなにも表示しない
            param.set(LOCATION, "");
            param.set(FROM_CARRYING, ci.getSourceStationNo());
            param.set(TO_CARRYING, ci.getDestStationNo());
        }
        else
        {
            // それ以外
            param.set(LOCATION, "");
            param.set(FROM_CARRYING, "");
            param.set(TO_CARRYING, "");
        }

        // 搬送区分
        param.set(CARRYING_FLAG, ci.getCarryFlag());
        // 搬送区分名称
        param.set(CARRYING_FLAG_NAME, DisplayResource.getCarryFlag(ci.getCarryFlag()));

        StationController stCtrl = new StationController(getConnection(), getClass());

        // 搬送元ステーション名称
        param.set(FROM_CARRYING_NAME, stCtrl.getAsStationName(param.getString(FROM_CARRYING)));
        // 搬送先ステーション名称
        param.set(TO_CARRYING_NAME, stCtrl.getAsStationName(param.getString(TO_CARRYING)));
        // 出庫指示詳細
        param.set(RETRIEVAL_DETAIL, ci.getRetrievalDetail());
        // 出庫指示詳細名称
        String detail = DisplayResource.getFaRetrievalDetail(ci.getRetrievalDetail());
        param.set(RETRIEVAL_DETAIL_NAME, detail);
        // 作業種別
        param.set(WORK_KIND, ci.getWorkType());
        // 作業種別名称
        param.set(WORK_KIND_NAME, DisplayResource.getWorkType(ci.getWorkType()));
        // 作業状態（搬送状態）
        param.set(CARRYING_STATUS, ci.getCmdStatus());
        // 作業状態（搬送状態）名称
        param.set(CARRYING_STATUS_NAME, DisplayResource.getCmdStatus(ci.getCmdStatus()));
        // 作業No
        param.set(WORK_NO, ci.getWorkNo());
        // スケジュールNo.
        param.set(SCHEDULE_NO, ci.getScheduleNo());
        // 搬送Key
        param.set(CARRYING_KEY, p.getString(CARRYING_KEY));

        result.add(param);

        // 6001013=表示しました。
        setMessage("6001013");

        return result;
    }

    /**
     * パラメータの内容が正しいかチェックを行います。<BR>
     * <CODE>checkParam</CODE>で指定されたパラメータにセットされた内容に従い、
     * パラメータ入力内容チェック処理を行います。<BR>
     * @param p 入力チェックを行う内容が含まれたパラメータクラス
     * @return true：入力内容が正常な場合  false：そうでない場合
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean check(ScheduleParams p)
            throws CommonException
    {
        // 日次更新処理中のチェック
        if (isDailyUpdate())
        {
            return false;
        }

        // パラメータから搬送キーを取得する。
        String carrykey = p.getString(CARRYING_KEY);
        // 搬送キーを指定し搬送データを取得する。
        CarryInfo ci = find(getConnection(), carrykey);

        if (ci == null)
        {
            // 6023293=対象となる搬送データがありませんでした。
            setMessage("6023293");
            return false;
        }

        // オンラインチェックが必要な場合のみ
        if (checkCarryStatus(p, ci))
        {
            // システム状態チェック
            if (!checkStatus(getConnection(), ci))
            {
                // 6023060=システム状態をオンラインにして下さい。
                setMessage("6023060");
                return false;
            }
        }
        
        // ステーション状態チェック
        if (!checkStation(getConnection(), ci))
        {
            // 6023077=ステーション状態が中断中でなければ、メンテナンスできません。
            setMessage("6023077");
            return false;
        }

        return true;
    }

    /**
     * スケジュールを開始します。
     * <CODE>startParams</CODE>で指定されたパラメータ配列にセットされた内容に従い、スケジュール処理を行います。<BR>
     * 必要に応じて、各継承クラスで実装してください。
     * @param p 設定内容が含まれたパラメータクラスの配列
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗した場合はfalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean startSCH(ScheduleParams p)
            throws CommonException
    {
        AsrsInParameter param = new AsrsInParameter(getWmsUserInfo());
        param.setCarryKey(p.getString(CARRYING_KEY));
        param.setProcessStatus(p.getString(ASRS_PROCESS));
        param.setTerminalNo(getWmsUserInfo().getTerminalNo());

        // スケジュール開始不可チェック
        if (!check(p))
        {
            return false;
        }

        CarryInfo ci = find(getConnection(), p.getString(CARRYING_KEY));

        if (ci == null)
        {
            // 6023293=対象となる搬送データがありませんでした。
            setMessage("6023293");
            return false;
        }
        
        // Part11のログ書き込みを行います
        log_write(p);

        // 画面で選択された処理区分ごとの処理を行います。
        if (AsrsInParameter.PROCESS_STATUS_UNINSTRUCT.equals(p.getString(ASRS_PROCESS)))
        {
            // 未指示に戻す
            if (uninstruct(getConnection(), ci, param))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (AsrsInParameter.PROCESS_STATUS_COMPLETE.equals(p.getString(ASRS_PROCESS)))
        {
            // 強制完了
            if (complete(getConnection(), ci, param))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (AsrsInParameter.PROCESS_STATUS_CANCEL_ALLOCATE.equals(p.getString(ASRS_PROCESS)))
        {
            // 引当を取り消す
            if (release(getConnection(), ci, param))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (AsrsInParameter.PROCESS_STATUS_TRACKING_DELETE.equals(p.getString(ASRS_PROCESS)))
        {
            // トラッキング削除
            if (delete(getConnection(), ci, param))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            // 6026029=予期しない値がセットされました。{0}={1}
            String msg = WmsMessageFormatter.format(6026029, "getProcessStatus()", p.getString(ASRS_PROCESS));
            RmiMsgLogClient.write(msg, (String)this.getClass().getName());
            // 6017011=致命的なエラーが発生しました。ログを参照してください。
            setMessage("6017011");
            return false;
        }
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
     * 搬送区分と搬送状態でオンラインチェックが必要かどうか判断する。
     *
     * @param p 設定内容が含まれたパラメータクラスの配列
     * @param ci 処理対象の搬送情報
     * @return true:必要、false:不要
     */
    protected boolean checkCarryStatus(ScheduleParams p, CarryInfo ci)
    {
        // 処理区分
        String process = p.getString(ASRS_PROCESS);
        // 搬送区分
        // 2010/05/11 Y.Osawa UPD ST
//        String type = ci.getWorkType();
        String type = ci.getCarryFlag();
        // 2010/05/11 Y.Osawa UPD ED
        // 搬送状態
        String status = ci.getCmdStatus();

        // 未指示 かつ
        // 出庫 または 棚間移動 かつ
        // 応答待ち または 指示済み
        if (AsrsInParameter.PROCESS_STATUS_UNINSTRUCT.equals(process)
                && (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(type) || SystemDefine.CARRY_FLAG_RACK_TO_RACK.equals(type))
                && (SystemDefine.CMD_STATUS_WAIT_RESPONSE.equals(status) || SystemDefine.CMD_STATUS_INSTRUCTION.equals(status)))
        {
            return true;
        }

        // 引当取消 かつ
        // 出庫 または 棚間移動 かつ
        // 応答待ち または 指示済み
        if (AsrsInParameter.PROCESS_STATUS_CANCEL_ALLOCATE.equals(process)
                && (SystemDefine.CARRY_FLAG_RETRIEVAL.equals(type) || SystemDefine.CARRY_FLAG_RACK_TO_RACK.equals(type))
                && (SystemDefine.CMD_STATUS_WAIT_RESPONSE.equals(status) || SystemDefine.CMD_STATUS_INSTRUCTION.equals(status)))
        {
            return true;
        }

        // トラッキング削除の場合
        if (AsrsInParameter.PROCESS_STATUS_TRACKING_DELETE.equals(process))
        {
            // 到着の場合
            boolean isFreeStation = false;
            if (CarryInfo.CMD_STATUS_ARRIVAL.equals(status))
            {
                try
                {
                    // 搬送状態が到着の場合は搬送元ステーションを作成し、コの字出庫ステーションかチェックする
                    Station sst = StationFactory.makeStation(getConnection(), ci.getSourceStationNo());
                    StationOperator sop =
                            StationOperatorFactory.makeOperator(getConnection(), sst.getStationNo(), sst.getClassName());
                    isFreeStation = sop instanceof FreeRetrievalStationOperator;
                }
                catch (Exception e)
                {
                    // 予期しないエラーが発生しました。{0}
                    RmiMsgLogClient.write(new TraceHandler(6006001, e), (String)this.getClass().getName());
                    // 6017011=致命的なエラーが発生しました。ログを参照してください。
                    setMessage("6017011");
                    return false;
                }
            }

            // 応答待ち または 指示済み または 掬い完了 または 出庫完了 または (到着 かつ コの字ではない)
            if (SystemDefine.CMD_STATUS_WAIT_RESPONSE.equals(status)
                    || SystemDefine.CMD_STATUS_INSTRUCTION.equals(status)
                    || SystemDefine.CMD_STATUS_PICKUP.equals(status)
                    || SystemDefine.CMD_STATUS_COMP_RETRIEVAL.equals(status)
                    || (SystemDefine.CMD_STATUS_ARRIVAL.equals(status) && !isFreeStation))
            {
                return true;
            }
        }

        // 上記条件に該当しない場合はオンラインチェックは行わない
        return false;
    }

    /**
     * CarryInfo表からwCarryKeyをキーに検索を行います。
     * @param conn データベースとのコネクションオブジェクト。
     * @param carrykey 搬送キー
     * @return CarryInfoの検索結果
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected CarryInfo find(Connection conn, String carrykey)
            throws CommonException
    {
        CarryInfo[] ci = null;
        CarryInfoHandler carryHandler = new CarryInfoHandler(conn);
        CarryInfoSearchKey skey = new CarryInfoSearchKey();

        skey.setCarryKey(carrykey);
        ci = (CarryInfo[])carryHandler.find(skey);

        // 対象データが見つからない
        if (ci == null || ci.length <= 0)
        {
            return null;
        }

        return (ci[0]);
    }

    /**
     * システム状態のオンライン、オフラインチェックを行います。
     * @param conn データベースとのコネクションオブジェクト
     * @param ci 強制完了対象作業データ
     * @return true : オンライン false : オフライン
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkStatus(Connection conn, CarryInfo ci)
            throws CommonException
    {
        PalletHandler palHandler = new PalletHandler(conn);
        PalletSearchKey palkey = new PalletSearchKey();
        palkey.setPalletId(ci.getPalletId());
        Pallet pl = (Pallet)palHandler.findPrimary(palkey);
        Station st = StationFactory.makeStation(conn, pl.getCurrentStationNo());
        if (st instanceof Shelf)
        {
            st = StationFactory.makeStation(conn, st.getParentStationNo());
        }
        GroupControllerHandler gcHandler = new GroupControllerHandler(conn);
        GroupControllerSearchKey gcKey = new GroupControllerSearchKey();
        gcKey.setControllerNo(st.getControllerNo());
        GroupController[] agc = (GroupController[])gcHandler.find(gcKey);
        if (GroupController.GC_STATUS_ONLINE.equals(agc[0].getStatusFlag()))
        {
            return true;
        }
        return false;
    }

    /**
     * Stationの中断中チェックを行います。
     * @param conn データベースとのコネクションオブジェクト。
     * @param ci    CarryInfoインスタンス
     * @return 中断中だったらTrueを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkStation(Connection conn, CarryInfo ci)
            throws CommonException
    {
        // 棚間移動はチェックなし
        if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
        {
            return true;
        }

        // 作業Station取得
        String workstation = "";
        if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag())
                || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
        {
            // 入庫
            workstation = ci.getSourceStationNo();
        }
        else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag()))
        {
            // 出庫
            workstation = ci.getDestStationNo();
        }

        // ステーションの状態を参照します。
        StationHandler stHandler = new StationHandler(conn);
        StationSearchKey sKey = new StationSearchKey();
        sKey.setStationNo(workstation);
        Station[] sts = (Station[])stHandler.find(sKey);
        return sts[0].isSuspend();
    }

    /**
     * 作業メンテナンス修正処理（未指示へ戻す）を実行します。
     * @param conn データベースとのコネクションオブジェクト
     * @param ci CarryInfoインスタンス
     * @param param パラメータクラス
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean uninstruct(Connection conn, CarryInfo ci, AsrsInParameter param)
            throws CommonException
    {
        try
        {
            // 搬送区分が入庫、直行はキャンセル、引当解除ともに不可
            if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag())
                    || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
            {
                // 6023079=搬送区分が入庫／直行の場合、未指示に戻す、引当を取り消すことはできません。
                setMessage("6023079");
                return false;
            }

            // 搬送状態を確認
            // 応答待ち、指示済みの場合
            if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus()))
            {
                // データキャンセル要求中をセット（未指示に戻す場合）
                alterCarryInfoCancelRequest(conn, ci.getCarryKey(), CarryInfo.CANCEL_REQUEST_CANCEL);
                alterCarryInfoMaintenanceTerminal(conn, ci.getCarryKey(), param.getTerminalNo());
                // As21Id04より搬送Data Cancel要求をします。
                SystemTextTransmission.id04send(ci, conn);

                // 6021023=搬送Data Cancel要求をしました。
                setMessage("6021023");
                return true;
            }
            // 引当、開始、掬い完了、出庫完了、到着、異常の場合
            else if (CarryInfo.CMD_STATUS_ALLOCATION.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_START.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_PICKUP.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_COMP_RETRIEVAL.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_ARRIVAL.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_ERROR.equals(ci.getCmdStatus()))
            {
                // 6023081=現在の搬送状態では未指示に戻すことはできません。設定できる状態は、応答待／指示済みです。
                setMessage("6023081");
                return false;
            }
            else
            {
                // 6026029=予期しない値がセットされました。{0}={1}
                String msg = WmsMessageFormatter.format(6026029, "ci.getCmdStatus()", ci.getCmdStatus());
                RmiMsgLogClient.write(msg, (String)this.getClass().getName());
                // 6017011=致命的なエラーが発生しました。ログを参照してください。
                setMessage("6017011");
                return false;
            }
        }
        // rmiregistry、msgserverが起動されていない : 送信できません。
        catch (ConnectException ex)
        {
            // メッセージログ・サーバに接続されていないため、送信できません。
            setMessage("6017003");
            return false;
        }
        // rmiregistry、msgserverが起動されているがAGCと未接続 : 送信できません。
        catch (NotBoundException ex)
        {
            // メッセージログ・サーバには接続していますが、AWC-AGC間は未接続のため、送信できません。
            setMessage("6017004");
            return false;
        }
        // rmiは起動しているが他の理由で送信できない場合はLogを落とす。
        catch (Exception e)
        {
            // 予期しないエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006001, e), (String)this.getClass().getName());
            // 6017011=致命的なエラーが発生しました。ログを参照してください。
            setMessage("6017011");
            return false;
        }
    }

    /**
     * 作業メンテナンス修正処理（強制完了）を実行します。
     * @param conn データベースとのコネクションオブジェクト
     * @param ci CarryInfoインスタンス
     * @param param パラメータクラス
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean complete(Connection conn, CarryInfo ci, AsrsInParameter param)
            throws CommonException
    {
        // 作業メンテナンスリストの条件をセット
        AsrsInParameter writerParam = new AsrsInParameter(getWmsUserInfo());
        writerParam.setCarryKey(ci.getCarryKey());
        writerParam.setTerminalNo(param.getTerminalNo());
        writerParam.setProcessStatus(param.getProcessStatus());

        AsWorkMntDASCHOperator asDaschOpe = new AsWorkMntDASCHOperator(this.getClass());

        // 作業表示データがあれば無条件に削除する
        OperationDisplayHandler odhandle = new OperationDisplayHandler(conn);
        OperationDisplaySearchKey odkey = new OperationDisplaySearchKey();
        odkey.setCarryKey(ci.getCarryKey());
        if (odhandle.count(odkey) > 0)
        {
            odhandle.drop(odkey);
        }

        // 搬送状態を確認し入庫は指示済み、出庫は出庫完了のものを強制完了する
        if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag()))
        {
            // 入庫の場合、搬送状態が到着、指示済み、掬い完了のデータのみ強制完了可能
            if (CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_PICKUP.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_ARRIVAL.equals(ci.getCmdStatus()))
            {
                // 搬送状態が到着の場合は搬送元ステーションを作成し、コの字出庫ステーションかチェックする
                boolean isFreeStation = false;
                if (CarryInfo.CMD_STATUS_ARRIVAL.equals(ci.getCmdStatus()))
                {
                    Station sst = StationFactory.makeStation(conn, ci.getSourceStationNo());
                    StationOperator sop =
                            StationOperatorFactory.makeOperator(conn, sst.getStationNo(), sst.getClassName());
                    isFreeStation = sop instanceof FreeRetrievalStationOperator;
                }

                if (isFreeStation && CarryInfo.CMD_STATUS_ARRIVAL.equals(ci.getCmdStatus()))
                {
                    // 6023212=現在の搬送状態では強制完了できません。設定できる状態は、指示済み／掬い完了です。
                    setMessage("6023212");
                    return false;
                }

                if (asDaschOpe.print(getLocale(), getUserInfo(), param, null))
                {
                    // 6001007=強制完了しました。
                    setMessage("6001007");
                }
                else
                {
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    setMessage("6007042");
                }

                // 在庫更新タイミングが搬送指示応答時の場合、在庫の更新を行う。
                if (WmsParam.STOCK_MODIFY_TIMING)
                {
                    CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());
                    //<jp> 在庫更新を行う。</jp>
                    //<en> Updates the stocks.</en>
                    carryCompOpe.updateStock(ci);
                }

                completeStorage(conn, ci);
                return true;
            }
            else
            {
                // 6023092=現在の搬送状態では強制完了できません。設定できる状態は、到着／指示済み／掬い完了です。
                setMessage("6023092");
                return false;
            }
        }
        else if (CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
        {
            // 直行の場合搬送状態が到着、指示済み、掬い完了のデータのみ強制完了可能
            if (CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_PICKUP.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_ARRIVAL.equals(ci.getCmdStatus()))
            {
                if (asDaschOpe.print(getLocale(), getUserInfo(), param, null))
                {
                    // 6001007=強制完了しました。
                    setMessage("6001007");
                }
                else
                {
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    setMessage("6007042");
                }

                completeDirectTravel(conn, ci);
                return true;
            }
            else
            {
                // 6023092=現在の搬送状態では強制完了できません。設定できる状態は、到着／指示済み／掬い完了です。
                setMessage("6023092");
                return false;

            }
        }
        else if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag()))
        {
            // 出庫の場合、出庫完了のデータのみ強制完了可能
            if (CarryInfo.CMD_STATUS_COMP_RETRIEVAL.equals(ci.getCmdStatus()))
            {
                // 印刷実行
                if (asDaschOpe.print(getLocale(), getUserInfo(), param, null))
                {
                    // 6001007=強制完了しました。
                    setMessage("6001007");
                }
                else
                {
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    setMessage("6007042");
                }

                completeRetrieval(conn, ci);
                return true;
            }
            else
            {
                // 6023093=現在の搬送状態では強制完了できません。設定できる状態は、出庫完了です。
                setMessage("6023093");
                return false;
            }
        }
        else if (CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
        {
            // 棚間移動の場合、搬送状態が指示済み、出庫完了のデータのみ強制完了可能
            if (CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_COMP_RETRIEVAL.equals(ci.getCmdStatus()))
            {
                // 印刷実行
                if (asDaschOpe.print(getLocale(), getUserInfo(), param, null))
                {
                    // 6001007=強制完了しました。
                    setMessage("6001007");
                }
                else
                {
                    // 6007042=設定後、印刷に失敗しました。ログを参照してください。
                    setMessage("6007042");
                }

                completeRackToRack(conn, ci);
                return true;
            }
            else
            {
                // 6023094=現在の搬送状態では強制完了できません。設定できる状態は、指示済み／出庫完了です。
                setMessage("6023094");
                return false;

            }
        }
        else
        {
            // 6026029=予期しない値がセットされました。{0}={1}
            String msg = WmsMessageFormatter.format(6026029, "ci.getCmdStatus()", ci.getCmdStatus());
            RmiMsgLogClient.write(msg, (String)this.getClass().getName());
            // 6017011=致命的なエラーが発生しました。ログを参照してください。
            setMessage("6017011");
            return false;
        }
    }

    /**
     * 作業メンテナンス修正処理（引当を取り消す）を実行します。
     * @param conn           データベースとのコネクションオブジェクト
     * @param ci             CarryInfoインスタンス
     * @param param パラメータクラス
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean release(Connection conn, CarryInfo ci, AsrsInParameter param)
            throws CommonException
    {
        try
        {
            // 対象となる搬送データが入庫/直行の搬送データは引当解除できない
            if (CarryInfo.CARRY_FLAG_STORAGE.equals(ci.getCarryFlag())
                    || CarryInfo.CARRY_FLAG_DIRECT_TRAVEL.equals(ci.getCarryFlag()))
            {
                // 6023079=搬送区分が入庫／直行の場合、未指示に戻す、引当を取り消すことはできません。
                setMessage("6023079");
                return false;
            }

            AsWorkMntDASCHOperator asDaschOpe = new AsWorkMntDASCHOperator(this.getClass());

            // 搬送状態を確認
            // 応答待ち、指示済みの場合
            if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus()))
            {
                // データキャンセル要求中をセット（引当を取り消す場合）
                alterCarryInfoCancelRequest(conn, ci.getCarryKey(), CarryInfo.CANCEL_REQUEST_RELEASE);
                alterCarryInfoMaintenanceTerminal(conn, ci.getCarryKey(), param.getTerminalNo());
                // As21Id04より搬送Data Cancel要求をします。
                SystemTextTransmission.id04send(ci, conn);

                // 6021023=搬送Data Cancel要求をしました。
                setMessage("6021023");
                return true;
            }
            // 引当、開始の場合
            else if (CarryInfo.CMD_STATUS_ALLOCATION.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_START.equals(ci.getCmdStatus()))
            {
                // 引当、開始の場合
                // 印刷実行
                if (asDaschOpe.print(getLocale(), getUserInfo(), param, null))
                {
                    // 6001006=設定しました。
                    setMessage("6001006");
                }
                else
                {
                    // 6007043=削除後、印刷に失敗しました。ログを参照してください。
                    setMessage("6007043");
                }

                // DFKLOOK:ここから修正(v3.1で発生した積み増し入庫引当取消対応)
                // 引当解除
                //ReleaseAllocateOperator releaseOpe = new ReleaseAllocateOperator(getConnection(), getClass());
                //releaseOpe.allocateClearOfCarryKey(ci);

                // '09/06/17 積み増し入庫データが作業メンテナンスで「引当取り消し」した時の対応
                if (CarryInfo.RETRIEVAL_DETAIL_ADD_STORING.equals(ci.getRetrievalDetail()))
                {
                    // CarryCompleteOperator.drop()で参照するキャンセル要求区分をセットする。
                    ci.setCancelRequest(CarryInfo.CANCEL_REQUEST_RELEASE);
                    ci.setMaintenanceTerminal(param.getTerminalNo());

                    // 積み増しの引当解除設定時は、積み増し設定した予定在庫が実在庫に
                    // なってないので実績を作成しないを指定する。
                    CarryCompleteOperator carryCompOpe = new CarryCompleteOperator(getConnection(), getClass());
                    carryCompOpe.drop(ci, false);
                }
                else
                {
                    // 引当解除
                    ReleaseAllocateOperator releaseOpe = new ReleaseAllocateOperator(getConnection(), getClass());
                    releaseOpe.allocateClearOfCarryKey(ci);
                }
                // DFKLOOK:ここまで修正v3.1で発生した積み増し入庫引当取消対応)

                return true;
            }
            // 掬い完了、出庫完了、到着、異常の場合
            else if (CarryInfo.CMD_STATUS_PICKUP.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_COMP_RETRIEVAL.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_ARRIVAL.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_ERROR.equals(ci.getCmdStatus()))
            {
                // 6023164=現在の搬送状態では引当を取り消すことはできません。設定できる状態は、引当／開始／応答待／指示済みです。
                setMessage("6023164");
                return false;
            }
            else
            {
                // 6026029=予期しない値がセットされました。{0}={1}
                String msg = WmsMessageFormatter.format(6026029, "ci.getCmdStatus()", ci.getCmdStatus());
                RmiMsgLogClient.write(msg, (String)this.getClass().getName());
                // 6017011=致命的なエラーが発生しました。ログを参照してください。
                setMessage("6017011");
                return false;
            }
        }
        // rmiregistry、msgserverが起動されていない : 送信できません。
        catch (ConnectException ex)
        {
            // メッセージログ・サーバに接続されていないため、送信できません。
            setMessage("6017003");
            return false;
        }
        // rmiregistry、msgserverが起動されているがAGCと未接続 : 送信できません。
        catch (NotBoundException ex)
        {
            // メッセージログ・サーバには接続していますが、AWC-AGC間は未接続のため、送信できません。
            setMessage("6017004");
            return false;
        }
        // rmiは起動しているが他の理由で送信できない場合はLogを落とす。
        catch (Exception e)
        {
            // 予期しないエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006001, e), (String)this.getClass().getName());
            // 6017011=致命的なエラーが発生しました。ログを参照してください。
            setMessage("6017011");
            return false;
        }
    }

    /**
     * 作業メンテナンス修正処理（トラッキング削除）を実行します。
     * @param conn           データベースとのコネクションオブジェクト
     * @param ci             CarryInfoインスタンス
     * @param param パラメータクラス
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean delete(Connection conn, CarryInfo ci, AsrsInParameter param)
            throws CommonException
    {
        try
        {
            // 対象となる搬送データが出庫、棚間移動の搬送データで 
            // 状態が引当または開始の場合は、トラッキング削除をできないように変更する
            if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag())
                    || CarryInfo.CARRY_FLAG_RACK_TO_RACK.equals(ci.getCarryFlag()))
            {
                if (CarryInfo.CMD_STATUS_ALLOCATION.equals(ci.getCmdStatus())
                        || CarryInfo.CMD_STATUS_START.equals(ci.getCmdStatus()))
                {
                    // 6023096=搬送状態が引当または開始の出庫搬送データに対して、トラッキング削除は実行できません。
                    setMessage("6023096");
                    return false;
                }
            }

            AsWorkMntDASCHOperator asDaschOpe = new AsWorkMntDASCHOperator(this.getClass());

            // 作業表示データがあれば無条件に削除する
            OperationDisplayHandler odhandle = new OperationDisplayHandler(conn);
            OperationDisplaySearchKey odkey = new OperationDisplaySearchKey();
            odkey.setCarryKey(ci.getCarryKey());
            if (odhandle.count(odkey) > 0)
            {
                odhandle.drop(odkey);
            }

            // 搬送状態が到着の場合は搬送元ステーションを作成し、コの字出庫ステーションかチェックする
            boolean isFreeStation = false;
            if (CarryInfo.CMD_STATUS_ARRIVAL.equals(ci.getCmdStatus()))
            {
                Station sst = StationFactory.makeStation(conn, ci.getSourceStationNo());
                StationOperator sop = StationOperatorFactory.makeOperator(conn, sst.getStationNo(), sst.getClassName());
                isFreeStation = sop instanceof FreeRetrievalStationOperator;
            }

            // 搬送元ステーションのCARRYKEY、荷姿情報、BCデータをクリアします。
            Station st = StationFactory.makeStation(conn, ci.getSourceStationNo());
            if (st.isArrivalCheck())
            {
                StationOperator sop = StationOperatorFactory.makeOperator(conn, st.getStationNo(), st.getClassName());
                sop.dropArrival();
            }

            // 搬送状態を確認
            if (CarryInfo.CMD_STATUS_WAIT_RESPONSE.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_PICKUP.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_COMP_RETRIEVAL.equals(ci.getCmdStatus())
                    || (CarryInfo.CMD_STATUS_ARRIVAL.equals(ci.getCmdStatus()) && !isFreeStation))
            {
                // 応答待ち、指示済み、掬い完了、出庫完了、到着の場合
                // 作業メンテナンスリストは、キャンセル応答でキャンセル可能な時に発行するため
                // ここでは印刷しません。

                alterCarryInfoCancelRequest(conn, ci.getCarryKey(), CarryInfo.CANCEL_REQUEST_DROP);
                alterCarryInfoMaintenanceTerminal(conn, ci.getCarryKey(), param.getTerminalNo());

                // As21Id04より搬送Data Cancel要求をします。
                SystemTextTransmission.id04send(ci, conn);

                // 6021023=搬送Data Cancel要求をしました。
                setMessage("6021023");
                return true;
            }
            else if (CarryInfo.CMD_STATUS_ALLOCATION.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_START.equals(ci.getCmdStatus())
                    || CarryInfo.CMD_STATUS_ERROR.equals(ci.getCmdStatus())
                    || (CarryInfo.CMD_STATUS_ARRIVAL.equals(ci.getCmdStatus()) && isFreeStation))
            {
                // 引当、開始、異常の場合
                // 印刷実行
                if (asDaschOpe.print(getLocale(), getUserInfo(), param, null))
                {
                    // 6001005=削除しました。
                    setMessage("6001005");
                }
                else
                {
                    // 6007043=削除後、印刷に失敗しました。ログを参照してください。
                    setMessage("6007043");
                }

                // DFKLOOK:ここから修正(v3.1で発生した積み増し入庫引当取消対応)
                // '09/06/17 搬送情報にキャンセル要求区分をセットする処理を追加
                // CarryCompleteOperator.drop()で参照するキャンセル要求区分をセットする。
                ci.setCancelRequest(CarryInfo.CANCEL_REQUEST_DROP);
                ci.setMaintenanceTerminal(param.getTerminalNo());
                // DFKLOOK:ここまで修正(v3.1で発生した積み増し入庫引当取消対応)

                // CARRY、PALLET、STOCKの作業削除
                CarryCompleteOperator ciop = new CarryCompleteOperator(conn, getClass());
                // 出庫の場合は通常完了とする。
                if (CarryInfo.CARRY_FLAG_RETRIEVAL.equals(ci.getCarryFlag()))
                {
                    //<jp> 削除</jp>
                    //<en> Deletes</en>
                    ciop.drop(ci, InOutResult.WORK_TYPE_CARRYINFODELETE, false, CARRY_COMPLETE.NORMAL);
                }
                else
                {
                    ciop.drop(ci, false);
                }

                return true;
            }
            else
            {
                // 6026029=予期しない値がセットされました。{0}={1}
                String msg = WmsMessageFormatter.format(6026029, "ci.getCmdStatus()", ci.getCmdStatus());
                RmiMsgLogClient.write(msg, (String)this.getClass().getName());
                // 6017011=致命的なエラーが発生しました。ログを参照してください。
                setMessage("6017011");
                return false;
            }
        }
        // rmiregistry、msgserverが起動されていない : 送信できません。
        catch (ConnectException ex)
        {
            // メッセージログ・サーバに接続されていないため、送信できません。
            setMessage("6017003");
            return false;
        }
        // rmiregistry、msgserverが起動されているがAGCと未接続 : 送信できません。
        catch (NotBoundException ex)
        {
            // メッセージログ・サーバには接続していますが、AWC-AGC間は未接続のため、送信できません。
            setMessage("6017004");
            return false;
        }
        // rmiは起動しているが他の理由で送信できない場合はLogを落とす。
        catch (Exception e)
        {
            // 予期しないエラーが発生しました。{0}
            RmiMsgLogClient.write(new TraceHandler(6006001, e), (String)this.getClass().getName());
            // 6017011=致命的なエラーが発生しました。ログを参照してください。
            setMessage("6017011");
            return false;
        }
    }

    /**
     * 入庫完了処理を行います。
     * @param conn データベースとのコネクションオブジェクト
     * @param ci 強制完了対象作業データ
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void completeStorage(Connection conn, CarryInfo ci)
            throws CommonException
    {
        // 変数の定義
        CarryInfoHandler carryHandler = new CarryInfoHandler(conn);
        CarryInfoSearchKey carryKey = new CarryInfoSearchKey();

        // 処理の開始
        // 行き先ステーションのインスタンス取得
        ShelfHandler slfHandler = new ShelfHandler(conn);
        ShelfSearchKey slfKey = new ShelfSearchKey();
        slfKey.setStationNo(ci.getDestStationNo());
        Shelf toShelf = (Shelf)slfHandler.findPrimary(slfKey);

        // 実際の完了処理
        // 入庫棚の予約を解除し、在荷をONにする。
        ShelfOperator sop = new ShelfOperator(conn, toShelf.getStationNo());
        sop.alterPresence(Shelf.LOCATION_STATUS_FLAG_STORAGED);

        // CarryInfoを削除
        carryKey.setCarryKey(ci.getCarryKey());
        carryHandler.drop(carryKey);

        // パレットの現在地を目的地に変更
        PalletHandler palHandler = new PalletHandler(conn);
        PalletSearchKey palkey = new PalletSearchKey();
        palkey.setPalletId(ci.getPalletId());
        Pallet pl = (Pallet)palHandler.findPrimary(palkey);
        alterPalletCurrentStationNo(conn, pl.getPalletId(), toShelf.getStationNo());
        // パレットに対する他の引当がないか確認
        carryKey.clear();
        carryKey.setPalletId(pl.getPalletId());
        if (carryHandler.count(carryKey) == 0)
        {
            // 商品コードが二重格納用商品コードであれば、異常棚に変更する。
            StockHandler stkHandler = new StockHandler(conn);
            StockSearchKey stkKey = new StockSearchKey();
            stkKey.setPalletId(pl.getPalletId());
            Stock[] stks = (Stock[])stkHandler.find(stkKey);
            if (WmsParam.IRREGULAR_ITEMCODE.equals(stks[0].getItemCode()))
            {
                // 状態を異常棚に変更
                alterPalletStatus(conn, pl.getPalletId(), Pallet.PALLET_STATUS_IRREGULAR);
            }
            else
            {
                // 状態を実棚に変更
                alterPalletStatus(conn, pl.getPalletId(), Pallet.PALLET_STATUS_REGULAR);
            }
            // 引当フラグを未引当に変更
            alterPalletAllocation(conn, pl.getPalletId(), Pallet.ALLOCATION_FLAG_NOT_ALLOCATED);
        }
        else
        {
            // 状態を出庫予約に変更
            alterPalletStatus(conn, pl.getPalletId(), Pallet.PALLET_STATUS_RETRIEVAL_PLAN);
        }

        // 強制完了にて、全ての在庫確認データを完了された場合、
        // アイルとAsInventoryCheckのフラグがオフにする。
        CarryCompleteOperator co = new CarryCompleteOperator(conn, getClass());
        co.updateInventoryCheckInfo(ci);

        // 実績送信情報の報告フラグを未報告に更新
        HostSendController hsc = new HostSendController(conn, this.getClass());
        hsc.updateReportFlag(ci);

        // 不要な到着情報を削除
        dropArrivalWithCarryKey(ci.getCarryKey());
    }

    /**
     * 出庫の強制完了(到着報告)の処理を行います。<BR>
     * MC Keyから、<code>CarryInfo</code>を検索し、パレット到着後の処理を行います。<BR>
     * 該当データの検索を行い、その内容でCarryInfoインスタンスを生成し荷姿、現在位置を更新します。<BR>
     * 最後にStationクラスの到着処理を実行し、ステーションに応じた搬送データ更新処理を行います。<BR>
     * また、今回の作業がユニット出庫以外で、搬送元が入庫可能ステーションとなった場合、
     * 以下の条件に一致する場合は入庫完了処理まで行います。<BR>
     *   1.パレットの現在位置が入庫可能ステーション<BR>
     *   2.ピッキング出庫の戻り入庫作業がAGC側で自動的に行なわれる<BR>
     * @param conn データベースとのコネクションオブジェクト
     * @param ci 強制完了対象のCarryInfoインスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void completeRetrieval(Connection conn, CarryInfo ci)
            throws CommonException
    {
        // 到着ステーションを作成
        Station st = StationFactory.makeStation(conn, ci.getDestStationNo());
        // ステーション別の到着処理を行う。
        StationOperator dop = StationOperatorFactory.makeOperator(conn, st.getStationNo(), st.getClassName());

        // コの字出庫ステーションの場合は作業表示運用に関わらず到着処理を行なう
        if (dop instanceof FreeRetrievalStationOperator)
        {
            FreeRetrievalStationOperator frsop = new FreeRetrievalStationOperator(conn, st.getStationNo());
            frsop.completeByForce(ci, this.getClass());
        }
        else
        {
            dop.arrival(ci, new Pallet(), this.getClass());
        }

        // 到着処理後の搬送情報を再検索
        CarryInfo st_ci = find(getConnection(), ci.getCarryKey());

        if (st_ci != null)
        {
            // 今回の作業がユニット出庫以外で、搬送元が入庫可能ステーションとなった場合、
            // 以下の条件に一致する場合は入庫完了処理まで行なう。
            // 1.パレットの現在位置が入庫可能ステーション
            // 2.ピッキング出庫の戻り入庫作業がAGC側で自動的に行なわれる
            if (!CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(st_ci.getRetrievalDetail()))
            {
                Station frst = StationFactory.makeStation(conn, st_ci.getSourceStationNo());
                if ((Station.STATION_TYPE_IN.equals(frst.getStationType()))
                        || (Station.STATION_TYPE_INOUT.equals(frst.getStationType())))
                {
                    if (Station.RESTORING_INSTRUCTION_AGC_STORAGE_SEND.equals(frst.getRestoringInstruction()))
                    {
                        completeStorage(conn, st_ci);
                    }
                }
            }
        }
    }

    /**
     * 直行の強制完了(到着報告)の処理を行います。<BR>
     * MC Keyから、<code>CarryInfo</code>を検索し、パレット到着後の処理を行います。<BR>
     * 該当データの検索を行い、その内容でCarryInfoインスタンスを生成し、現在位置を更新します。<BR>
     * 最後にStationクラスの到着処理を実行し、ステーションに応じた搬送データ更新処理を行います。<BR>
     * @param conn データベースとのコネクションオブジェクト
     * @param ci 強制完了対象のCarryInfoインスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void completeDirectTravel(Connection conn, CarryInfo ci)
            throws CommonException
    {
        // 到着ステーションを作成
        Station st = StationFactory.makeStation(conn, ci.getDestStationNo());

        // 現在位置を更新
        alterPalletCurrentStationNo(conn, ci.getPalletId(), st.getStationNo());

        // ステーション別の到着処理を行う。
        StationOperator sop = StationOperatorFactory.makeOperator(conn, st.getStationNo(), st.getClassName());
        sop.arrival(ci, new Pallet(), this.getClass());
    }

    /**
     * 棚間移動の強制完了処理を行います。
     * @param conn データベースとのコネクションオブジェクト
     * @param cryInfo 強制完了対象作業データ
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void completeRackToRack(Connection conn, CarryInfo cryInfo)
            throws CommonException
    {
        CarryInfo ci = (CarryInfo)cryInfo.clone();

        CarryCompleteOperator cop = new CarryCompleteOperator(getConnection(), this.getClass());

        if (CarryInfo.CMD_STATUS_INSTRUCTION.equals(ci.getCmdStatus()))
        {
            // 搬送状態が指示済みの状態ならば、棚間移動の出庫完了処理を行う。
            cop.retrievalRackToRackMove(ci);

            //<jp> 搬送状態フラグを完了に更新</jp>
            //<en> Updates the carry status flag to 'complete'.</en>
            ci.setCmdStatus(CarryInfo.CMD_STATUS_COMP_RETRIEVAL);

            //<jp> データベースの搬送状態フラグを完了に更新</jp>
            //<en> Updates the carry status flag to 'complete'.</en>
            CarryInfoAlterKey caryAkey = new CarryInfoAlterKey();
            CarryInfoHandler caryh = new CarryInfoHandler(conn);

            caryAkey.updateCmdStatus(CarryInfo.CMD_STATUS_COMP_RETRIEVAL);
            caryAkey.setCarryKey(ci.getCarryKey());
            caryAkey.updateLastUpdatePname(this.getClass().getSimpleName());
            caryh.modify(caryAkey);
        }

        // 搬送データを削除します。
        CarryInfoHandler carryh = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey carrKey = new CarryInfoSearchKey();
        carrKey.clear();
        carrKey.setCarryKey(ci.getCarryKey());
        carryh.drop(carrKey);

        // 棚間移動の入庫完了処理
        PalletSearchKey pltKey = new PalletSearchKey();
        PalletHandler plth = new PalletHandler(getConnection());
        pltKey.setPalletId(ci.getPalletId());
        Pallet plt = (Pallet)plth.findPrimary(pltKey);
        cop.storageRackToRackMove(ci, plt, ci.getSourceStationNo(), ci.getDestStationNo());

        // パレットの状態を更新する
        AsPalletController palCtl = new AsPalletController(getConnection(), getClass());
        palCtl.updatePalletStatusFlagStorage(plt.getPalletId());
    }

    /**
     * AS/RS搬送情報のキャンセル要求フラグを指定された要求区分に変更します。
     * @param conn データベースとのコネクションオブジェクト
     * @param carryKey 搬送Key
     * @param req 要求フラグ
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void alterCarryInfoCancelRequest(Connection conn, String carryKey, String req)
            throws CommonException
    {
        CarryInfoHandler caHandler = new CarryInfoHandler(conn);
        CarryInfoAlterKey cakey = new CarryInfoAlterKey();
        cakey.setCarryKey(carryKey);
        cakey.updateCancelRequest(req);
        cakey.updateCancelRequestDate(DbDateUtil.getTimeStamp());
        cakey.updateLastUpdatePname(getClass().getSimpleName());
        caHandler.modify(cakey);
    }

    /**
     * AS/RS搬送情報のメンテナンス端末Noを指定されたステーションに変更します。
     * @param conn データベースとのコネクションオブジェクト
     * @param carryKey 搬送Key
     * @param no 出庫ステーション
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void alterCarryInfoMaintenanceTerminal(Connection conn, String carryKey, String no)
            throws CommonException
    {
        CarryInfoHandler caHandler = new CarryInfoHandler(conn);
        CarryInfoAlterKey cakey = new CarryInfoAlterKey();
        cakey.setCarryKey(carryKey);
        cakey.updateMaintenanceTerminal(no);
        cakey.updateLastUpdatePname(getClass().getSimpleName());
        caHandler.modify(cakey);
    }

    /**
     * AS/RSパレット情報のカレントステーションNoを指定されたステーションNoに変更します。
     * @param conn データベースとのコネクションオブジェクト
     * @param pid  パレットID
     * @param stno カレントステーションNo.
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void alterPalletCurrentStationNo(Connection conn, String pid, String stno)
            throws CommonException
    {
        PalletHandler palHandler = new PalletHandler(conn);
        PalletAlterKey palkey = new PalletAlterKey();
        palkey.setPalletId(pid);
        palkey.updateCurrentStationNo(stno);
        palkey.updateLastUpdatePname(getClass().getSimpleName());
        palHandler.modify(palkey);
    }

    /**
     * AS/RSパレット情報のStatusを指定されたStatusに変更します。
     * @param conn データベースとのコネクションオブジェクト
     * @param pid  パレットID
     * @param status 在庫状態
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void alterPalletStatus(Connection conn, String pid, String status)
            throws CommonException
    {
        PalletHandler palHandler = new PalletHandler(conn);
        PalletAlterKey palkey = new PalletAlterKey();
        palkey.updateStatusFlag(status);
        palkey.setPalletId(pid);
        palkey.updateLastUpdatePname(getClass().getSimpleName());
        palHandler.modify(palkey);
    }

    /**
     * AS/RSパレット情報の引当状態を指定された引当状態に変更します。
     * @param conn データベースとのコネクションオブジェクト
     * @param pid  パレットID
     * @param alloc 引当状態
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void alterPalletAllocation(Connection conn, String pid, String alloc)
            throws CommonException
    {
        PalletHandler palHandler = new PalletHandler(conn);
        PalletAlterKey palkey = new PalletAlterKey();
        palkey.updateAllocationFlag(alloc);
        palkey.setPalletId(pid);
        palkey.updateLastUpdatePname(getClass().getSimpleName());
        palHandler.modify(palkey);
    }

    /**
     * 操作ログを書き込みます。
     * 
     * @param p スケジュールパラメータ
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void log_write(ScheduleParams p)
            throws CommonException
    {
        try
        {
            if (!P11Config.isOperationLog())
            {
                return;
            }
            
            // 最新の搬送情報を取得
            List<Params> outparams = query(p);
            
            if (outparams != null && outparams.size() > 0)
            {
                Params outparam = outparams.get(0);
                
                // write part11 log.
                P11LogWriter part11Writer = new P11LogWriter(getConnection());
                Part11List part11List = new Part11List();
                part11List.add(p.get(ASRS_PROCESS), "");
                part11List.add(outparam.get(FROM_CARRYING), "");
                part11List.add(outparam.get(TO_CARRYING), "");
                part11List.add(outparam.get(LOCATION), "");
                part11List.add(outparam.get(CARRYING_FLAG), "");
                part11List.add(outparam.get(CARRYING_STATUS), "");
                part11List.add(outparam.get(WORK_KIND), "");
                part11List.add(outparam.get(RETRIEVAL_DETAIL), "");
                part11List.add(outparam.get(WORK_NO), "");
                part11List.add(outparam.get(SCHEDULE_NO), "");
                part11List.add(outparam.get(CARRYING_KEY), "");
                part11Writer.createOperationLog(getUserInfo(), ConvertUtil.objectToInt(EmConstants.OPELOG_CLASS_SETTING), part11List);
            }
        }
        catch (SQLException e)
        {
            throw new ReadWriteException(e);
        }
    }

    /**<jp>
     * ステーションのCARRYKEY、荷姿情報、BCデータをクリアします。
     * 搬送指示応答の受信、データキャンセルなどで到着情報が不要になった場合に使用されます。
     * 
     * @param carrykey 搬送キー
     * @throws InvalidDefineException テーブル更新の条件に不整合があった場合に通知します。
     * @throws ReadWriteException     データアクセスで障害が発生した場合に通知します。
     * @throws NotFoundException      削除すべきデータが見つからない場合に通知されます。
     </jp>*/
    public void dropArrivalWithCarryKey(String carrykey)
            throws InvalidDefineException,
                ReadWriteException,
                NotFoundException
    {
        ArrivalSearchKey altkey = new ArrivalSearchKey();
        altkey.setCarryKey(carrykey);
        ArrivalHandler handl = new ArrivalHandler(getConnection());
        try
        {
            handl.drop(altkey);
        }
        catch (NotFoundException e)
        {
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
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
//end of class
