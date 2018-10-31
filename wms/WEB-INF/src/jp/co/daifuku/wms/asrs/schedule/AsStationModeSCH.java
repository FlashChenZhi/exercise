// $Id: AsStationModeSCH.java 7423 2010-03-06 08:36:58Z shibamoto $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsStationModeSCHParams.*;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id42;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler;
import jp.co.daifuku.wms.base.dbhandler.GroupControllerSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.TerminalAreaHandler;
import jp.co.daifuku.wms.base.dbhandler.TerminalAreaSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.TerminalArea;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;
import jp.co.daifuku.wms.handler.db.SysDate;

/**
 * BusiTuneで生成されたSCHクラスです。
 * ここに具体的なクラスの説明を記述して下さい。
 *
 * @version $Revision: 7423 $, $Date:: 2010-03-06 17:36:58 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: shibamoto $
 */
public class AsStationModeSCH
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
    public AsStationModeSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // ハンドラインスタンス生成
        AbstractDBHandler handler = new StationHandler(getConnection());
        Station[] stationChecks = (Station[])handler.find(createSearchKey());

        // 取得件数に応じてメッセージを設定
        if (stationChecks.length == 0)
        {
            // 6003011=対象データはありませんでした。
            setMessage("6003011");
            return new ArrayList<Params>();
        }
        // 6001013 = 表示しました。
        setMessage("6001013");
        // エンティティを画面表示用にパラメータクラスにセットし返す
        return getDisplayData(stationChecks);

    }

    public boolean check(ScheduleParams[] ps)
    {
        for (Params p : ps)
        {
            if (!Station.MODE_REQUEST_NONE.equals((p.getString(AFTER_CURRENT_MODE))))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public List<Params> startSCHgetParams(ScheduleParams... ps)
            throws CommonException
    {
        // 返却パラメータ用
        List<Params> result = new ArrayList<Params>();

        // TODO 処理前のチェックを行う。
        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return null;
        }

        for (Params p : ps)
        {
            // 画面メッセージをセット
            Params returnParam = new Params();
            // ステーションNo.
            returnParam.set(STATION_NO, p.getString(STATION_NO));
            // ステーション名称
            returnParam.set(HIDDEN_STATION_NAME, p.getString(HIDDEN_STATION_NAME));
            // 運用区分
            returnParam.set(HIDDEN_STATION_TYPE, p.getString(HIDDEN_STATION_TYPE));
            // AWCモード切替(動作モード)
            returnParam.set(MODE_TYPE, p.getString(MODE_TYPE));
            returnParam.set(HIDDEN_MODE_TYPE, p.getString(HIDDEN_MODE_TYPE));
            // 現在の作業モード
            returnParam.set(BEFORE_CURRENT_MODE, p.getString(BEFORE_CURRENT_MODE));
            returnParam.set(HIDDEN_BEFORE_CURRENT_MODE, p.getString(HIDDEN_BEFORE_CURRENT_MODE));
            // 作業モード
            returnParam.set(AFTER_CURRENT_MODE, null);
            // 機器状態
            returnParam.set(MACHINE_STATUS, p.getString(MACHINE_STATUS));
            // 作業状態(中断中フラグ)
            returnParam.set(SUSPEND, p.getString(SUSPEND));
            // 作業件数
            returnParam.set(WORK_COUNT, p.getString(WORK_COUNT));
            // 画面メッセージ
            returnParam.set(SETTING_RESULT, p.getString(SETTING_RESULT));
            
            if (!Station.MODE_REQUEST_NONE.equals((p.getString(AFTER_CURRENT_MODE))))
            {
                Station rStation = (Station)checkList(p);

                if (rStation != null)
                {
                    if (modify(rStation, p))
                    {
                        // パッケージ単位でコミット
                        doCommit(this.getClass());
                    }
                }
                // 画面メッセージ
                returnParam.set(SETTING_RESULT,
                        MessageResource.getMessage(WmsMessageFormatter.format(Integer.parseInt(getMessage()))));
            }
            result.add(returnParam);
        }

        // 6001032=設定しました。詳細は設定結果を参照してください。
        setMessage("6001032");
        return result;
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
     * 検索条件をセットします。
     *
     * @throws CommonException 全ての例外を報告します
     * @return SearchKey
     */
    protected SearchKey createSearchKey()
            throws CommonException
    {
        // 端末情報からステーションNo.を取得
        TerminalAreaHandler tahndl = new TerminalAreaHandler(getConnection());
        TerminalAreaSearchKey key = new TerminalAreaSearchKey();

        key.setTerminalNo(this.getTerminalNo());
        TerminalArea[] tmarea = (TerminalArea[])tahndl.find(key);

        StationSearchKey searchKey = new StationSearchKey();

        if (tmarea != null)
        {
            /* 検索条件の指定 */
            // 操作端末から取得できる全てのステーション情報
            String[] st = new String[tmarea.length];
            for (int i = 0; i < tmarea.length; i++)
            {
                st[i] = tmarea[i].getStationNo();
            }
            searchKey.setStationNo(st, true);
            // 動作モードが手動
            searchKey.setModeType(Station.MODE_TYPE_AWC_CHANGE);
            // ステーションタイプが入出庫兼用
            searchKey.setStationType(Station.STATION_TYPE_INOUT);
            /* ソート順の指定 */
            // ステーションNo.順
            searchKey.setStationNoOrder(true);

            /* 取得項目の指定 */
            // ステーションNo.
            searchKey.setStationNoCollect();
            // ステーション名称
            searchKey.setStationNameCollect();
            // ステーション種別(運用区分)
            searchKey.setStationTypeCollect();
            // モード切替種別(動作モード)
            searchKey.setModeTypeCollect();
            // 現在作業モード
            searchKey.setCurrentModeCollect();
            // ステーション状態(機器状態)
            searchKey.setStatusCollect();
            // 作業中フラグ(作業状態)
            searchKey.setSuspendCollect();
            // モード切替要求区分
            searchKey.setModeRequestCollect();
        }

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param stationChecks 検索結果
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(Station[] stationChecks)
            throws ReadWriteException
    {
        List<Params> result = new ArrayList<Params>();

        for (Station ent : stationChecks)
        {
            Params param = new Params();

            // 返却データをセットする
            // ステーションNo.
            param.set(STATION_NO, ent.getStationNo());
            // ステーション名称
            param.set(HIDDEN_STATION_NAME, ent.getStationName());
            // 運用区分
            param.set(HIDDEN_STATION_TYPE, DisplayResource.getStationType(ent.getStationType()));
            // AWCモード切替(動作モード)
            param.set(MODE_TYPE, DisplayResource.getModeType(ent.getModeType()));
            param.set(HIDDEN_MODE_TYPE, ent.getModeType());
            // 現在の作業モード
            param.set(BEFORE_CURRENT_MODE, DisplayResource.getMode(ent.getCurrentMode()));
            param.set(HIDDEN_BEFORE_CURRENT_MODE, ent.getCurrentMode());
            // 機器状態
            param.set(MACHINE_STATUS, DisplayResource.getStationStatus(ent.getStatus()));
            param.set(HIDDEN_MACHINE_STATUS, ent.getStatus());
            // 作業状態(中断中フラグ)
            param.set(SUSPEND, DisplayResource.getSuspend(ent.getSuspend()));
            param.set(HIDDEN_SUSPEND, ent.getSuspend());
            // 作業件数
            param.set(WORK_COUNT, getWorkingCount(ent.getStationNo()));
            // メッセージ
            if (!SystemDefine.MODE_REQUEST_NONE.equals(ent.getModeRequest()))
            {
                param.set(SETTING_RESULT, DisplayResource.getModeRequest(ent.getModeRequest()));
            }

            result.add(param);
        }

        return result;
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    /**
     * ステーションNo.にて、作業件数を取得し返却します。
     * @param pStation ステーションNo.
     * @return 作業件数
     */
    protected int getWorkingCount(String pStation)
    {
        try
        {
            CarryInfoHandler wCiHandler = new CarryInfoHandler(getConnection());
            CarryInfoSearchKey wCiSearchKey = new CarryInfoSearchKey();
            //検索実行

            // 取得条件をセットします
            // 搬送テーブルより、該当ステーションでの作業件数を取得します。
            wCiSearchKey.clearKeys();
            // 搬送元ステーションNo.
            wCiSearchKey.setSourceStationNo(pStation, "=", "(", "", false);
            // 搬送先ステーションNo.
            wCiSearchKey.setDestStationNo(pStation, "=", "", ")", false);

            // 集約条件
            // 搬送Key
            wCiSearchKey.setCarryKeyCollect("DISTINCT");

            int rCount = wCiHandler.count(wCiSearchKey);

            return rCount;
        }
        catch (ReadWriteException ex)
        {
            RmiMsgLogClient.write(new TraceHandler(6006020, ex), this.getClass().getName());
            return 0;
        }
    }

    /**
     * パラメータチェック処理を行ないます。
     * @param inParam チェックするパラメータの内容
     * @return パラメータに異常が無い場合はtrue、異常がある場合はfalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected Station checkList(Params inParam)
            throws CommonException
    {
        StationHandler wStHandler = new StationHandler(getConnection());
        StationSearchKey wStSearchKey = new StationSearchKey();
        CarryInfoHandler wCiHandler = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey wCiSearchKey = new CarryInfoSearchKey();
        GroupControllerHandler wGcHandler = new GroupControllerHandler(getConnection());
        GroupControllerSearchKey wGcSearchKey = new GroupControllerSearchKey();

        try
        {
            // ステーション情報検索条件をセットします。
            wStSearchKey.clearKeys();
            wStSearchKey.setStationNo(inParam.getString(STATION_NO));

            Station rStation = (Station)wStHandler.findPrimaryForUpdate(wStSearchKey);

            // モード切替要求中の場合、更新不可とする。
            if (Station.MODE_REQUEST_STORAGE.equals(rStation.getModeRequest())
                    || Station.MODE_REQUEST_RETRIEVAL.equals(rStation.getModeRequest()))
            {
                //<jp> モード切替中のため処理できません。</jp>
                setMessage("6023105");
                return null;
            }
            // 搬送データのチェックを行います。
            // 今回の要求が出庫モードの場合
            if (Station.CURRENT_MODE_RETRIEVAL.equals(inParam.getString(AFTER_CURRENT_MODE)))
            {
                //<jp> 指定されたステーションが、搬送元ステーションになっている搬送データを取得</jp>
                //<jp> 状態が開始、指示済み、応答待ち、異常であればモード切替不可</jp>
                wCiSearchKey.clearKeys();
                wCiSearchKey.setSourceStationNo(inParam.getString(STATION_NO));
                wCiSearchKey.setCmdStatus(CarryInfo.CMD_STATUS_START, "=", "(", "", false);
                wCiSearchKey.setCmdStatus(CarryInfo.CMD_STATUS_WAIT_RESPONSE, "=", "", "", false);
                wCiSearchKey.setCmdStatus(CarryInfo.CMD_STATUS_INSTRUCTION, "=", "", "", false);
                wCiSearchKey.setCmdStatus(CarryInfo.CMD_STATUS_ERROR, "=", "", ")", false);

                if (wCiHandler.count(wCiSearchKey) > 0)
                {
                    //<jp> モード切替を行うステーションに入庫搬送データが存在しているためモード切替できません。</jp>
                    setMessage("6023182");
                    return null;
                }
            }
            // 今回の要求が入庫モードの場合
            if (Station.CURRENT_MODE_STORAGE.equals(inParam.getString(AFTER_CURRENT_MODE)))
            {
                //<jp> 指定されたステーションが、搬送先ステーションになっている搬送データを取得</jp>
                //<jp> 状態が引当以外であればモード切替不可</jp>
                wCiSearchKey.clearKeys();
                wCiSearchKey.setDestStationNo(inParam.getString(STATION_NO));
                wCiSearchKey.setCmdStatus(CarryInfo.CMD_STATUS_ALLOCATION, "!=", "", "", false);
                
                if (wCiHandler.count(wCiSearchKey) > 0)
                {
                    //<jp> モード切替を行うステーションに出庫搬送データが存在しているためモード切替できません。</jp>
                    setMessage("6023444");
                    return null;
                }
            }
            //<jp> システム状態のチェック</jp>
            wGcSearchKey.clearKeys();
            wGcSearchKey.setControllerNo(rStation.getControllerNo());

            GroupController[] rGroupControll = (GroupController[])wGcHandler.find(wGcSearchKey);

            if (!GroupController.GC_STATUS_ONLINE.equals(rGroupControll[0].getStatusFlag()))
            {
                //<jp> システム状態をオンラインにして下さい</jp>
                //<en> Please set the system status on-line.</en>
                setMessage("6023060");
                return null;
            }

            return rStation;
        }
        catch (LockTimeOutException ex)
        {
            // 6027008=このデータは他の端末で更新中のため、処理できません。
            setMessage("6027008");
            return null;
        }

    }

    /**
     * 
     * 
     * @param rStation
     * @param inParam
     * @return 設定結果を返します。true:成功,false:失敗
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean modify(Station rStation, Params inParam)
            throws CommonException
    {
        try
        {
            StationHandler wStHandler = new StationHandler(getConnection());
            StationAlterKey wStAlterKey = new StationAlterKey();
            //<jp> ステーションNo</jp>
            String stno = inParam.getString(STATION_NO);
            //<jp> 作業モード切替選択値</jp>
            String currentmode = inParam.getString(AFTER_CURRENT_MODE);

            //<jp> 作業モード切替選択が「入庫」の場合</jp>
            //<en> If the work mode is on 'storage'</en>
            if (Station.CURRENT_MODE_STORAGE.equals(currentmode))
            {
                //<jp> 作業Mode切替え指示送信</jp>
                SystemTextTransmission.id42send(rStation, String.valueOf(As21Id42.CLASS_STORAGE), getConnection());

                //<jp>ステーションの入庫モード切替要求を行う</jp>
                wStAlterKey.clearKeys();
                wStAlterKey.setStationNo(stno);
                wStAlterKey.updateModeRequest(Station.MODE_REQUEST_STORAGE);
                wStAlterKey.updateModeRequestDate(new Date());

                wStHandler.modify(wStAlterKey);

                //<jp>切り替えが成功の場合</jp>
                //<jp>入庫モードに切替要求しました。</jp>
                setMessage("6011023");
            }
            //<jp> 作業モード切替選択が「出庫」の場合</jp>
            //<en> If the work mode is on 'retrieval'</en>
            else if (Station.CURRENT_MODE_RETRIEVAL.equals(currentmode))
            {
                //<jp> 作業Mode切替え指示送信</jp>
                SystemTextTransmission.id42send(rStation, String.valueOf(As21Id42.CLASS_RETRIEVAL), getConnection());

                //<jp>ステーションの出庫モード切替要求を行う</jp>
                wStAlterKey.clearKeys();
                wStAlterKey.setStationNo(stno);
                wStAlterKey.updateModeRequest(Station.MODE_REQUEST_RETRIEVAL);
                wStAlterKey.updateModeRequestDate(new SysDate());

                wStHandler.modify(wStAlterKey);
                //<jp>切り替えが成功の場合</jp>
                //<jp>出庫モードに切替要求しました。</jp>
                //<en> If the mode was switched successfully</en>
                //<en> mode switch was requested.</en>
                setMessage("6011024");
            }
            return true;
        }
        catch (NotFoundException e)
        {
            // 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023115, rStation.getStationNo()));
            return false;
        }
        catch (ConnectException e)
        {
            //<jp> メッセージログ・サーバに接続されていないため、送信できません。</jp>
            //<en> Cannot send; no connection with the message log server.</en>
            setMessage("6007032");
            return false;
        }
        catch (NotBoundException e)
        {
            //<jp> AGCとの接続が確立されていません。要求を送信できません。</jp>
            //<en> Cannot send; there is no connection between AWC and AGC,  though this is </en>
            //<en> conected with the message log server.</en>
            setMessage("6017004");
            return false;
        }
        catch (Exception e)
        {
            //<jp> スケジュール処理中に予期しない例外が発生しました。メッセージログを参照してください。</jp>
            //<en> Unexpected error occurred.</en>
            RmiMsgLogClient.write(new TraceHandler(6007001, e), (String)this.getClass().getName());
            //<jp> 致命的なエラーが発生しました。ログを参照してください</jp>
            //<en> Fatal error occurred. Please refer to the log.</en>
            setMessage("6017011");
            return false;
        }
    }

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
