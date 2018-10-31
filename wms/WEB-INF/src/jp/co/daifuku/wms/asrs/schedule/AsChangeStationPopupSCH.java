// $Id: AsChangeStationPopupSCH.java 7910 2010-05-10 00:15:04Z kishimoto $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsChangeStationPopupSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DisplayResource;
import jp.co.daifuku.wms.handler.Entity;

/**
 * AS/RS ステーション状態変更設定のスケジュール処理を行います。
 *
 * @version $Revision: 7910 $, $Date: 2010-05-10 09:15:04 +0900 (月, 10 5 2010) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: kishimoto $
 */
public class AsChangeStationPopupSCH
        extends AbstractAsrsSCH
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
    public AsChangeStationPopupSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        Station[] stations = (Station[])searchStation();

        if (stations.length == 0)
        {
            // 対象データはありませんでした。
            setMessage("6003011");
            return new ArrayList<Params>();
        }

        // エンティティを画面表示用にパラメータクラスにセットし返す
        return getDisplayData(stations);
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。<BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        StationAlterKey skey = null;
        StationHandler handler = null;
        int row = 0;

        try
        {
            handler = new StationHandler(getConnection());

            for (int i = 0; i < ps.length; i++)
            {

                skey = new StationAlterKey();

                if (!checkList(ps[i]))
                {
                    setErrorRowIndex(ps[row].getRowIndex());
                    return false;
                }

                if (SystemDefine.SUSPEND_ON.equals(ps[i].getString(SUSPEND)))
                {
                    // 中断中の場合は、使用可能に更新
                    // 更新条件
                    skey.setStationNo(ps[i].getString(STATION_NO));
                    skey.setSuspend(Station.SUSPEND_ON);
                    // 更新値
                    skey.updateSuspend(Station.SUSPEND_OFF);

                    // 更新処理を行う
                    handler.modify(skey);
                }
                else if (SystemDefine.SUSPEND_OFF.equals(ps[i].getString(SUSPEND)))
                {
                    // 使用可能の場合は、中断中に更新
                    // 更新条件
                    skey.setStationNo(ps[i].getString(STATION_NO));
                    skey.setSuspend(Station.SUSPEND_OFF);
                    // 更新値
                    skey.updateSuspend(Station.SUSPEND_ON);

                    // 更新処理を行う
                    handler.modify(skey);
                }

                row++;
            }

            // 設定しました。
            setMessage("6001006");
            return true;
        }
        catch (NotFoundException e)
        {
            //例外を受け取った場合、処理を中断してfalseを返す
            //MSG = "No. {0} 他端末で処理されたため、処理を中断しました"
            setMessage(WmsMessageFormatter.format(6023015, ps[row].getRowIndex()));
            setErrorRowIndex(ps[row].getRowIndex());
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
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws ReadWriteException データベースエラーがあった場合に通知します
     */
    protected List<Params> getDisplayData(Station[] stations)
            throws ReadWriteException
    {
        List<Params> result = new ArrayList<Params>();

        for (Station ent : stations)
        {
            Params param = new Params();

            // 返却データをセットする
            // ステーションNo.
            param.set(STATION_NO, ent.getStationNo());
            // ステーション名称
            param.set(STATION_NAME, ent.getStationName());
            // 動作モード
            param.set(MODE_TYPE, ent.getModeType());
            // 動作モード名称
            param.set(MODE_TYPE_NAME, DisplayResource.getModeType(ent.getModeType()));
            // 作業モード
            param.set(CURRENT_MODE, ent.getCurrentMode());
            // 作業モード名称
            param.set(CURRENT_MODE_NAME, DisplayResource.getMode(ent.getCurrentMode()));
            // 機器状態
            param.set(STATUS, changeContrllerStatus(ent.getStatus()));
            // 機器状態名称
            param.set(STATUS_NAME, DisplayResource.getStationStatus(changeContrllerStatus(ent.getStatus())));
            // 作業状態
            param.set(SUSPEND, ent.getSuspend());
            // 作業状態名称
            param.set(SUSPEND_NAME, DisplayResource.getSuspend(ent.getSuspend()));
            // 作業件数
            param.set(WORK_COUNT, countWork(ent.getStationNo()));

            result.add(param);
        }

        return result;
    }

    /**
     * ASRSステーション状態設定ために取り受けたパラメータチェック処理。
     * 
     * @param checkParam 検索条件パラメータ
     * @return 正常完了はtrue、条件エラーなどの場合はfalseを返します。
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowされます。
     */
    protected boolean checkList(ScheduleParams checkParam)
            throws CommonException
    {
        StationSearchKey skey = null;
        StationHandler handler = null;

        try
        {
            skey = new StationSearchKey();
            // 検索条件
            skey.setStationNo(checkParam.getString(STATION_NO));

            handler = new StationHandler(getConnection());

            // 取得情報をロック
            handler.findForUpdate(skey);

            return true;
        }
        catch (LockTimeOutException e)
        {
            // 6023114=他端末で処理中のため、処理を中断しました。
            setMessage("6023114");
            return false;
        }
    }

    /**
     * AS/RSステーション情報を検索し、結果を返します。
     * 
     * @throws CommonException 何らかの例外が発生した場合に通知されます。
     * @return 検索結果を格納したEntity配列
     */
    protected Entity[] searchStation()
            throws ReadWriteException
    {
        StationSearchKey stationKey = new StationSearchKey();
        StationHandler handler = new StationHandler(getConnection());

        // 送信可のみ
        stationKey.setSendable(Station.SENDABLE_TRUE);
        
        // コントローラNo.
        stationKey.setJoin(Station.CONTROLLER_NO, GroupController.CONTROLLER_NO);

        // 表示順
        stationKey.setStationNoOrder(true);

        // 検索を行い、結果を返す。
        return handler.find(stationKey);
    }

    /**
     * パラメータで受け取ったステーションNo.に対する作業件数を返します。
     * 
     * @param station ステーションNo.
     * @throws CommonException 何らかの例外が発生した場合に通知されます。
     * @return 検索結果を格納したEntity配列
     */
    protected int countWork(String station)
            throws ReadWriteException
    {
        CarryInfoSearchKey key = new CarryInfoSearchKey();
        CarryInfoHandler handler = new CarryInfoHandler(getConnection());

        // 検索条件
        key.setKey(CarryInfo.SOURCE_STATION_NO, station, "=", "(", "", false);
        key.setKey(CarryInfo.DEST_STATION_NO, station, "=", "", ")", true);

        // 検索を行い、結果を返す。
        return handler.count(key);
    }

    /**
     * DB機器状態フラグを画面用機器状態フラグに変換します。
     * 
     * @param status 機器状態
     * @return 機器状態
     */
    protected String changeContrllerStatus(String status)
    {
        if (StringUtil.isBlank(status))
        {
            return "";
        }
        return status;
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
