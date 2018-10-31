// $Id: AsEndInventoryCheckSCH.java 6339 2009-12-03 06:44:06Z okayama $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsEndInventoryCheckSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.control.AllocationClearance;
import jp.co.daifuku.wms.asrs.location.AisleOperator;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.dbhandler.AisleAlterKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckAlterKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.SearchKey;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;

/**
 * AS/RS 在庫確認終了設定のスケジュール処理を行います。
 * 
 * @version $Revision: 6339 $, $Date: 2009-12-03 15:44:06 +0900 (木, 03 12 2009) $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: okayama $
 */
public class AsEndInventoryCheckSCH
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
    public AsEndInventoryCheckSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        AbstractDBHandler handler = new InventoryCheckHandler(getConnection());
        // 検索処理実行
        InventoryCheck[] inventoryChecks = (InventoryCheck[])handler.find(createSearchKey(p));

        // 取得件数に応じてメッセージを設定
        if (inventoryChecks.length == 0)
        {
            // 対象データはありませんでした。
            setMessage("6003011");
            return new ArrayList<Params>();
        }

        // 6001013 = 表示しました。
        setMessage("6001013");
        // エンティティを画面表示用にパラメータクラスにセットし返す
        return getDisplayData(inventoryChecks);
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
        // 日次更新チェック
        if (!canStart())
        {
            return false;
        }

        // 搬送データクリアチェック
        if (isAllocationClear())
        {
            return false;
        }

        boolean isSkip = false;
        AllocationClearance alloccle = new AllocationClearance(getConnection());
        // 在庫確認情報(ASINVENTORYCHECK)検索
        InventoryCheckHandler invchkHandler = new InventoryCheckHandler(getConnection());
        InventoryCheckSearchKey invchkSearchKey = new InventoryCheckSearchKey();

        // 搬送情報(CARRYINFO)検索
        CarryInfoHandler carryHandler = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey carrySearchKey = new CarryInfoSearchKey();

        for (int i = 0; i < ps.length; i++)
        {
            // スケジュールNo
            String schno = ps[i].getString(BATCH_NO);

            invchkSearchKey.clear();
            invchkSearchKey.setScheduleNo(schno);
            invchkSearchKey.setStatusFlag(InventoryCheck.STATUS_FLAG_CONFIRM);
            InventoryCheck invchk = (InventoryCheck)invchkHandler.findPrimary(invchkSearchKey);

            if (invchk == null)
            {
                setErrorRowIndex(ps[i].getRowIndex());
                // 6023015=No.{0}他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, ps[i].getRowIndex()));
                return false;
            }

            // 引当解除処理
            try
            {
                if (ArrayUtil.isEmpty(alloccle.dropwithScheduleno(schno)))
                {
                    setErrorRowIndex(ps[i].getRowIndex());
                    // 6023097=設定可能な搬送データが存在しません。
                    setMessage(WmsMessageFormatter.format(6023097, ps[i].getRowIndex()));
                    return false;
                }
            }
            catch (NotFoundException ex)
            {
                setErrorRowIndex(ps[i].getRowIndex());
                // 6023015=No.{0}他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023015, ps[i].getRowIndex()));
                return false;
            }

            // 残搬送情報が存在しない場合
            carrySearchKey.clear();
            carrySearchKey.setScheduleNo(schno);
            if (carryHandler.count(carrySearchKey) == 0)
            {
                // 在庫確認設定(ASINVENTORYCHECK)更新処理
                if (!updateAsInventoryCheck(schno))
                {
                    return false;
                }

                // アイル(AISLE)情報更新処理
                if (!updateAisle(invchk.getStationNo()))
                {
                    return false;
                }
            }
            else
            {
                // スキップした搬送データが存在
                isSkip = true;
            }
        }

        if (isSkip)
        {
            // 6021020=設定しました。（一部のデータがスキップされました。）
            setMessage(WmsMessageFormatter.format(6021020));
        }
        else
        {
            // 6001006 = 設定しました。
            setMessage(WmsMessageFormatter.format(6001006));
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
     * 検索条件をセットします。
     *
     * @param p 検索条件を含むScheduleParams
     * @return InventoryCheckSearchKey
     */
    protected SearchKey createSearchKey(ScheduleParams p)
    {
        InventoryCheckSearchKey searchKey = new InventoryCheckSearchKey();

        // set where
        // 状態
        searchKey.setStatusFlag(SystemDefine.STATUS_FLAG_CONFIRM);

        // set order by
        searchKey.setScheduleNoOrder(true);

        return searchKey;
    }

    /**
     * 表示情報を取得します。
     *
     * @param finder 検索結果を含むFinder
     * @return List<Params>
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected List<Params> getDisplayData(InventoryCheck[] inventoryChecks)
            throws CommonException
    {
        List<Params> result = new ArrayList<Params>();

        // エリアマスタ情報コントローラの作成
        AreaController control = new AreaController(getConnection(), this.getClass());
        StationController stControl = new StationController(getConnection(), this.getClass());

        int i = 1;

        for (InventoryCheck ent : inventoryChecks)
        {
            Params param = new Params();

            // 返却データをセットする

            // ステーションNo.
            param.set(STATION_NO, ent.getStationNo());
            // バッチNo.
            param.set(BATCH_NO, ent.getScheduleNo());
            // エリア
            param.set(AREA_NO, control.getAreaNoOfWarehouse(ent.getWhStationNo()));
            // ステーション名（作業場）
            param.set(STATION_NAME, stControl.getAsStationName(ent.getStationNo()));
            // 開始棚
            param.set(FROM_LOCATION_NO, ent.getFromLocation());
            // 終了棚
            param.set(TO_LOCATION_NO, ent.getToLocation());
            // 開始商品コード
            param.set(FROM_ITEM_CODE, ent.getFromItemCode());
            // 終了商品コード
            param.set(TO_ITEM_CODE, ent.getToItemCode());
            // No.
            param.set(NO, i++);

            result.add(param);
        }

        return result;
    }

    /**
     * パラメータチェック処理を行ないます。<BR>
     * ・ステーションに紐づくAGCがオンラインであること <BR>
     * ・ステーションが中断中であること <BR>
     * 
     * @param checkParam 条件チェックを行う情報を持つ<CODE>ScheduleParams</CODE>クラスのインスタンス。 <BR>
     *         <CODE>ScheduleParams</CODE>インスタンス以外を指定された場合CommonExceptionをスローします。 <BR>
     * @return パラメータに異常が無い場合はtrue、異常がある場合はfalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean statusCheck(ScheduleParams checkParam)
            throws CommonException
    {
        // オンラインチェック
        if (!isControllerOnline(getStation(checkParam.getString(STATION_NO))))
        {
            return false;
        }

        // 該当ステーションの中断中チェック
        if (!checkStation(checkParam))
        {
            return false;
        }

        return true;
    }

    /**
     * Stationの中断中チェックを行います。<BR>
     * Stationが中断中でない場合は、処理できません。<BR>
     * 
     * @param param 設定を行うためうち情報
     * @return true:中断中 false:中断中でない
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkStation(ScheduleParams param)
            throws CommonException
    {
        // 同一スケジュールNo.の搬送情報を取得する
        CarryInfoHandler cih = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
        ciKey.setScheduleNo(param.getString(BATCH_NO));
        ciKey.setDestStationNoCollect();
        ciKey.setDestStationNoGroup();
        CarryInfo[] ci = (CarryInfo[])cih.find(ciKey);
        if (ci == null || ci.length == 0)
        {
            return true;
        }

        // ステーションで中断中でないものが存在しないかチェックする
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < ci.length; i++)
        {
            temp.add(ci[i].getDestStationNo());
        }
        StationHandler sth = new StationHandler(getConnection());
        StationSearchKey stKey = new StationSearchKey();
        stKey.setStationNo(temp.toArray(new String[temp.size()]), true);
        stKey.setSuspend(SystemDefine.SUSPEND_ON, "!=");
        if (sth.count(stKey) > 0)
        {
            // 6023122 = No.{0} ステーションが中断中でないため設定できません。
            setMessage(WmsMessageFormatter.format(6023122, param.getRowIndex()));
            setErrorRowIndex(param.getRowIndex());
            return false;
        }
        return true;
    }

    /**
     * アイル情報テーブルを更新します。<BR>
     * アイルの在庫確認搬送データを確認し、アイルに在庫確認の搬送情報が無い場合、アイルの
     * 在庫確認中フラグをオフします。<BR>
     * @param stno ステーションNo.
     * @return 更新が正常に終了したらTrue、失敗したらFalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean updateAisle(String stno)
            throws CommonException
    {
        CarryInfoHandler cryHandle = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey cryKey = new CarryInfoSearchKey();

        AisleHandler aileHandler = new AisleHandler(getConnection());
        AisleAlterKey aileAlterKey = new AisleAlterKey();

        // 入力ステーション（ステーション、又は、作業場）からルートの有るアイルを検索します。
        AisleOperator aop = new AisleOperator();
        String[] stn_aisles = aop.getAisleStationNo(getConnection(), stno);
        for (int i = 0; i < stn_aisles.length; i++)
        {
            cryKey.clear();
            cryKey.setAisleStationNo(stn_aisles[i]);
            cryKey.setWorkType(SystemDefine.JOB_TYPE_ASRS_INVENTORYCHECK);
            cryKey.setCmdStatus(SystemDefine.CMD_STATUS_ARRIVAL, "<");
            cryKey.setRetrievalDetail(SystemDefine.RETRIEVAL_DETAIL_INVENTORY_CHECK, "=", "(", "", false);
            cryKey.setPriority(SystemDefine.PRIORITY_CHECK_EMPTY, "=", "", ")", true);

            if (cryHandle.count(cryKey) <= 0)
            {
                // アイル表の在庫確認中フラグをOFFにする
                aileAlterKey.clear();
                aileAlterKey.setStationNo(stn_aisles[i]);
                // 在庫確認中フラグを更新する。（在庫確認未作業）
                aileAlterKey.updateInventoryCheckFlag(SystemDefine.INVENTORY_CHECK_FLAG_UNSTART);
                // データの更新
                aileHandler.modify(aileAlterKey);
            }
        }

        return true;
    }

    /**
     * 在庫確認設定情報テーブルを更新します。<BR>
     * 
     * @param schno  スケジュールNo.
     * @return 更新が正常に終了したらTrue、失敗したらFalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean updateAsInventoryCheck(String schno)
            throws CommonException
    {
        InventoryCheckHandler inchkHandler = new InventoryCheckHandler(getConnection());
        InventoryCheckAlterKey inchkAlterKey = new InventoryCheckAlterKey();

        // スケジュールNo
        inchkAlterKey.setScheduleNo(schno);
        // 在庫確認中フラグを更新する。（処理済）
        inchkAlterKey.updateStatusFlag(SystemDefine.STATUS_FLAG_NO_CONFIRM);
        // 最終更新名
        inchkAlterKey.updateLastUpdatePname(getClass().getSimpleName());
        // データの更新
        inchkHandler.modify(inchkAlterKey);

        return true;

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
