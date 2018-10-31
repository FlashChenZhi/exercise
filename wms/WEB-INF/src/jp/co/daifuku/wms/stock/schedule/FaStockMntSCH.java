// $Id: Sch_ja.java 117 2008-10-06 11:00:54Z admin $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.stock.schedule.FaStockMntSCHParams.*;

import java.sql.Connection;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.AsStockController;
import jp.co.daifuku.wms.base.controller.CarryInfoController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StockHistory;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;

/**
 * 在庫メンテナンスのスケジュール処理を行います。
 *
 * @version $Revision: 117 $, $Date:: 2008-10-06 20:00:54 +0900#$
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: admin $
 */
public class FaStockMntSCH
        extends AbstractSCH
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /**
     * 設定ボタンチェック
     */
    public static final String CHECK_SET = "CHECK_SET";

    /**
     * 追加ボタンチェック
     */
    public static final String CHECK_ADD = "CHECK_ADD";

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
    public FaStockMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param ps 設定内容を持つ<CODE>ScheduleParams</CODE>の配列。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        String day = DbDateUtil.getSystemDate();
        String time = DbDateUtil.getSystemDateTime();
        for (int i = 0; i < ps.length; i++)
        {
            // 入庫日時入力チェック
            if (StringUtil.isBlank(ps[i].getDate(STORAGE_DATE)) ^ StringUtil.isBlank(ps[i].getDate(STORAGE_TIME)))
            {
                // 6022058=No.{0} 入庫日時を入力する場合、日付と時間の両方を入力してください。
                setMessage(WmsMessageFormatter.format(6022058, ps[i].getRowIndex()));
                setErrorRowIndex(ps[i].getRowIndex());
                return false;
            }
            else if (StringUtil.isBlank(ps[i].getDate(STORAGE_DATE)) && StringUtil.isBlank(ps[i].getDate(STORAGE_TIME)))
            {
                // 入庫日時
                ps[i].set(STORAGE_DATE, WmsFormatter.toDate(day));
                ps[i].set(STORAGE_TIME, WmsFormatter.toDateTime(time));
            }
        }

        // 入力チェック
        if (!inputCheck(ps))
        {
            return false;
        }

        // 指定エリアがAS/RSの場合
        if (checkArea(ps[0].getString(AREA_NO)))
        {
            // AS/RSの登録を行う
            if (!registAsrs(ps))
            {
                // 問題があればfalseを返却
                return false;
            }
        }
        else
        {
            // 平置きの登録を行う
            if (!registLocate(ps))
            {
                // 問題があればfalseを返却
                return false;
            }
        }

        // 6001006=設定しました。
        setMessage("6001006");

        // 問題無ければtrueを返却
        return true;
    }

    /**
     * パラメータの内容を元に、チェックを行います。
     *
     * @param checkParam 入力チェックを行う内容が含まれたパラメータクラス
     * @return true：入力内容が正常な場合
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean check(ScheduleParams checkParam)
            throws CommonException
    {
        // エリア情報コントローラの生成
        AreaController areaCtrl = new AreaController(getConnection(), getClass());
        StockHandler wStockHandler = new StockHandler(this.getConnection());
        StockSearchKey wStockSearchKey = new StockSearchKey();
        Stock rStock = null;

        // 倉庫ステーションNo.の取得
        String whStationNo = areaCtrl.getWhStationNo(checkParam.getString(AREA_NO));
        checkParam.set(WH_STATION_NO, whStationNo);
        // 棚No.(DMSHELF形式)の取得
        String shelfLocationNo =
                areaCtrl.toAsrsLocation(checkParam.getString(AREA_NO), checkParam.getString(LOCATION_NO));
        checkParam.set(SHELF_LOCATION_NO, shelfLocationNo);

        // 棚情報のチェックを行う
        if (checkShelf(checkParam, CHECK_SET))
        {
            // ダブルディープのチェックを行う
            if (!checkDoubleDeepLocation(checkParam, CHECK_SET))
            {
                // 問題があればfalseを返却
                return false;
            }
        }
        else
        {
            // 問題があればfalseを返却
            return false;
        }

        // 修正の場合
        if (ScheduleParams.ProcessFlag.UPDATE.equals(checkParam.getProcessFlag()))
        {
            // 在庫IDにて在庫情報の再取得を行います。
            wStockSearchKey.setStockId(checkParam.getString(STOCK_ID));
            wStockSearchKey.setLastUpdateDate(checkParam.getDate(LAST_UPDATE_DATE));

            rStock = (Stock)wStockHandler.findPrimary(wStockSearchKey);
            if (rStock == null)
            {
                //6003021=No.{0} このデータは、他の端末で更新されたため処理できません。
                setMessage(WmsMessageFormatter.format(6003021, checkParam.getRowIndex()));
                setErrorRowIndex(checkParam.getRowIndex());
                return false;
            }
        }
        else
        {
            // 在庫IDにて在庫情報の再取得を行います。
            wStockSearchKey.setStockId(checkParam.getString(STOCK_ID));
            wStockSearchKey.setLastUpdateDate(checkParam.getDate(LAST_UPDATE_DATE));

            rStock = (Stock)wStockHandler.findPrimary(wStockSearchKey);
            if (rStock == null)
            {
                //6003021=No.{0} このデータは、他の端末で更新されたため処理できません。
                setMessage(WmsMessageFormatter.format(6003021, checkParam.getRowIndex()));
                setErrorRowIndex(checkParam.getRowIndex());
                return false;
            }
        }

        // 問題無ければtrueを返却
        return true;
    }

    /**
     * 次画面に遷移前にチェックを行います。
     *
     * @param p 入力チェックを行う内容が含まれたパラメータクラス
     * @return 成功か失敗か
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public boolean nextCheck(ScheduleParams p)
            throws CommonException
    {
        // 指定エリアがAS/RSの場合
        if (checkArea(p.getString(AREA_NO)))
        {
            // エリア情報コントローラの生成
            AreaController areaCtrl = new AreaController(getConnection(), getClass());

            // 棚No.(DMSHELF形式)の取得
            String shelfLocationNo = areaCtrl.toAsrsLocation(p.getString(AREA_NO), p.getString(LOCATION_NO));
            p.set(SHELF_LOCATION_NO, shelfLocationNo);

            // 棚情報のチェックを行う
            if (checkShelf(p, CHECK_ADD))
            {
                // ダブルディープのチェックを行う
                if (!checkDoubleDeepLocation(p, CHECK_ADD))
                {
                    // 問題があればfalseを返却
                    return false;
                }
            }
            else
            {
                // 問題があればfalseを返却
                return false;
            }
        }

        // 問題なければtrueを返却
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
     * リストセルエリアの入力チェックを行います。<BR>
     * <BR>
     * @param inParam 編集行を含むパラメータ
     * @return boolean
     */
    protected boolean inputCheck(ScheduleParams[] inParam)
    {
        // 件数分繰り返す
        for (int i = 0; i < inParam.length; i++)
        {
            // 修正の場合のみチェックを行う
            if (ScheduleParams.ProcessFlag.UPDATE.equals(inParam[i].getProcessFlag()))
            {
                // 修正数が0の場合
                if (inParam[i].getInt(MODIFIED_QTY) == 0)
                {
                    //6023617=No.{0} {1}には{2}以上の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023617, inParam[i].getRowIndex(),
                            DisplayText.getText("LBL-W1390"), 1));
                    setErrorRowIndex(inParam[i].getRowIndex());
                    return false;
                }

                // 修正数が在庫最大数以上の場合
                if (inParam[i].getInt(MODIFIED_QTY) > WmsParam.MAX_STOCK_QTY)
                {
                    //6023313=No.{0} 修正数には在庫上限数{1}以下の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023313, inParam[i].getRowIndex(), MAX_STOCK_QTY_DISP));
                    setErrorRowIndex(inParam[i].getRowIndex());
                    return false;
                }

                // 修正数が引当済数以下の場合
                if (inParam[i].getInt(MODIFIED_QTY) < inParam[i].getInt(ALLOCATION_QTY))
                {
                    //6023617=No.{0} {1}には{2}以上の値を入力してください。
                    setMessage(WmsMessageFormatter.format(6023617, inParam[i].getRowIndex(),
                            DisplayText.getText("LBL-W1390"), DisplayText.getText("LBL-W0603")));
                    setErrorRowIndex(inParam[i].getRowIndex());
                    return false;
                }
            }
            else
            {
                continue;
            }
        }

        // 問題無ければtrueを返却
        return true;
    }

    /**
     * 指定エリアのエリア種別がAS/RSかチェックを行います。
     *
     * @param areaNo 指定されたエリアNo.
     * @return チェック結果(true : AS/RS false : 平置き)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkArea(String areaNo)
            throws CommonException
    {
        // 在庫情報ハンドラの生成
        AreaHandler areaHandler = new AreaHandler(getConnection());

        // 検索条件のセット
        AreaSearchKey areaKey = new AreaSearchKey();
        // エリアNo.
        areaKey.setAreaNo(areaNo);
        // エリア種別(AS/RS)
        areaKey.setAreaType(Area.AREA_TYPE_ASRS);

        // 指定エリアがAS/RSエリアの場合
        if (areaHandler.count(areaKey) > 0)
        {
            // AS/RSの場合はtrueを返却
            return true;
        }

        // 平置きの場合はfalseを返却
        return false;
    }

    /**
     * 棚の状態をチェックし、メンテナンス可能かどうか判断します。
     *
     * @param param パラメータ
     * @param check 設定チェックか追加チェック
     * @return メンテナンス可能の場合true、それ以外の場合はfalseを返す
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkShelf(ScheduleParams param, String check)
            throws CommonException
    {
        //ASRS棚情報検索
        Shelf wShelf = getShelf(param.getString(SHELF_LOCATION_NO));
        if (wShelf == null)
        {
            // 設定チェックの場合
            if (check.equals(CHECK_SET))
            {
                //6023276=No.{0} 実在する棚No.を入力してください。
                setMessage(WmsMessageFormatter.format(6023276, param.getRowIndex()));
                setErrorRowIndex(param.getRowIndex());
            }
            else
            {
                // 6023067=実在する棚No.を入力してください
                setMessage("6023067");
            }
            return false;
        }

        // アクセス不可棚チェックを行う
        if (Shelf.ACCESS_NG_FLAG_NG.equals(wShelf.getAccessNgFlag()))
        {
            // 設定チェックの場合
            if (check.equals(CHECK_SET))
            {
                //6023265=No.{0} 指定された棚はアクセス不可棚のためメンテナンスできません。
                setMessage(WmsMessageFormatter.format(6023265, param.getRowIndex()));
                setErrorRowIndex(param.getRowIndex());
            }
            else
            {
                // 6023098=指定された棚はアクセス不可棚のためメンテナンスできません
                setMessage("6023098");
            }
            return false;
        }

        // 禁止棚チェックを行う
        if (Shelf.PROHIBITION_FLAG_NG.equals(wShelf.getProhibitionFlag()))
        {
            // 設定チェックの場合
            if (check.equals(CHECK_SET))
            {
                //6023266=No.{0} 指定された棚は禁止棚に設定されているため、設定できません。
                setMessage(WmsMessageFormatter.format(6023266, param.getRowIndex()));
                setErrorRowIndex(param.getRowIndex());
            }
            else
            {
                // 6123296=指定された棚は禁止棚に設定されているため、追加できません。
                setMessage("6123296");
            }
            return false;
        }

        // 予約棚のチェックを行う
        if (Shelf.LOCATION_STATUS_FLAG_RESERVATION.equals(wShelf.getStatusFlag()))
        {
            // 設定チェックの場合
            if (check.equals(CHECK_SET))
            {
                //6023267=No.{0} 指定された棚は予約棚のためメンテナンスできません。
                setMessage(WmsMessageFormatter.format(6023267, param.getRowIndex()));
                setErrorRowIndex(param.getRowIndex());
            }
            else
            {
                // 6023086=指定された棚は予約棚のためメンテナンスできません
                setMessage("6023086");
            }
            return false;
        }

        // 実棚ならば引当,異常棚チェック
        Pallet[] wPallet = null;
        if (Shelf.LOCATION_STATUS_FLAG_STORAGED.equals(wShelf.getStatusFlag()))
        {
            // ASRSパレット情報検索
            wPallet = getPallet(param.getString(SHELF_LOCATION_NO));
            String pltstatus = wPallet[0].getStatusFlag();
            if (Pallet.PALLET_STATUS_STORAGE_PLAN.equals(pltstatus)
                    || Pallet.PALLET_STATUS_RETRIEVAL_PLAN.equals(pltstatus)
                    || Pallet.PALLET_STATUS_RETRIEVAL.equals(pltstatus))
            {
                // 設定チェックの場合
                if (check.equals(CHECK_SET))
                {
                    //6023268=No.{0} 指定された棚は現在引当中です。
                    setMessage(WmsMessageFormatter.format(6023268, param.getRowIndex()));
                    setErrorRowIndex(param.getRowIndex());
                }
                else
                {
                    // 6023070=指定された棚は現在引当中です。
                    setMessage("6023070");
                }
                return false;
            }
            else if (Pallet.PALLET_STATUS_IRREGULAR.equals(pltstatus)
                    && ScheduleParams.ProcessFlag.UPDATE.equals(param.getProcessFlag()))
            {
                // 設定チェックの場合
                if (check.equals(CHECK_SET))
                {
                    //6023269=No.{0} 指定された棚は異常棚のため設定できません。
                    setMessage(WmsMessageFormatter.format(6023269, param.getRowIndex()));
                    setErrorRowIndex(param.getRowIndex());
                }
                else
                {
                    // 6023292=指定された棚は異常棚のため追加できません。
                    setMessage("6023292");
                }
                return false;
            }
        }

        // 問題が無ければtrueを返却
        return true;
    }

    /**
     * 棚No.よりAS/RS棚情報を取得します。
     *
     * @param station 入力棚No.
     * @return AS/RS棚情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected Shelf getShelf(String station)
            throws CommonException
    {
        // AS/RS棚情報ハンドラの生成
        ShelfHandler wShHandler = new ShelfHandler(this.getConnection());
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey wShSearchKey = new ShelfSearchKey();

        // 検索条件の設定
        // ステーションNo.
        wShSearchKey.setStationNo(station);

        // 取得したAS/RS棚情報を返却
        return (Shelf)wShHandler.findPrimary(wShSearchKey);
    }

    /**
     * 棚No.よりAS/RSパレット情報を取得します。
     *
     * @param shelfLocationNo 棚No.(DMSHELF形式)
     * @return AS/RSパレット情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected Pallet[] getPallet(String shelfLocationNo)
            throws CommonException
    {
        // パレット情報ハンドラの生成
        PalletHandler wPlHandler = new PalletHandler(this.getConnection());
        // パレット情報検索キーの生成
        PalletSearchKey wPlSearchKey = new PalletSearchKey();
        // 搬送情報コントローラの生成
        CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
        // 搬送情報検索キーの生成
        CarryInfoSearchKey wCarrySearchKey = carryControl.getEmptyShelfPallet();

        // 検索条件をセットします。
        // 現在ステーションNo.
        wPlSearchKey.setCurrentStationNo(shelfLocationNo);
        // 指定棚にパレット情報が存在する
        wPlSearchKey.setKey(Pallet.PALLET_ID, wCarrySearchKey, "!=", "", "", true);

        // 取得したAS/RSパレット情報を返却
        return (Pallet[])wPlHandler.find(wPlSearchKey);
    }

    /**
     * ダブルディープの棚の状態チェックを行います。
     *
     * @param param パラメータ
     * @param check 設定チェックか追加チェック
     * @return true：メンテナンス可能、false: メンテナンス不可
     * @throws CommonException データベース処理でエラー発生した場合にthrowする
     */
    protected boolean checkDoubleDeepLocation(ScheduleParams param, String check)
            throws CommonException
    {
        // AS/RS棚情報ハンドラの生成
        ShelfHandler slfh = new ShelfHandler(getConnection());
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey slfKey = new ShelfSearchKey();

        // エリア情報コントローラの生成
        AreaController areaCon = new AreaController(getConnection(), getClass());

        // 検索キーのセット
        // 倉庫ステーションNo.
        slfKey.setWhStationNo(areaCon.getWhStationNo(param.getString(AREA_NO)));
        // ステーションNo.
        slfKey.setStationNo(param.getString(SHELF_LOCATION_NO));
        Shelf mntShelf = (Shelf)slfh.findPrimary(slfKey);
        if (StringUtil.isBlank(mntShelf.getPairStationNo()))
        {
            // ダブルディープ棚ではないので、trueを返す。
            return true;
        }

        // 手前棚に登録
        if (Shelf.BANK_SELECT_NEAR.equals(mntShelf.getSide()))
        {
            // 検索キーのセット
            slfKey.clear();
            // 倉庫ステーションNo.
            slfKey.setWhStationNo(mntShelf.getWhStationNo());
            // ステーションNo.
            slfKey.setStationNo(mntShelf.getPairStationNo());
            Shelf pairShelf = (Shelf)slfh.findPrimary(slfKey);

            // 奥棚がアクセス不可棚
            if (Shelf.ACCESS_NG_FLAG_NG.equals(pairShelf.getAccessNgFlag()))
            {
                // trueを返却
                return true;
            }

            // 奥棚が禁止棚
            if (Shelf.PROHIBITION_FLAG_NG.equals(pairShelf.getProhibitionFlag()))
            {
                // trueを返却
                return true;
            }

            // 奥棚が実棚
            if (Shelf.LOCATION_STATUS_FLAG_STORAGED.equals(pairShelf.getStatusFlag()))
            {
                // 倉庫と棚Noでパレット情報を取得する。
                Pallet[] plt = getPallet(pairShelf.getStationNo());
                if (plt.length != 0)
                {
                    if (Pallet.PALLET_STATUS_REGULAR.equals(plt[0].getStatusFlag())
                            && Pallet.ALLOCATION_FLAG_NOT_ALLOCATED.equals(plt[0].getAllocationFlag()))
                    {
                        // 奥棚が実棚で未引当なので、trueを返す。
                        return true;
                    }
                }
                else
                {
                    // 設定チェックの場合
                    if (check.equals(CHECK_SET))
                    {
                        // ここでPalletが無いのは、この処理途中に他で更新が行われた。
                        //6003021=No.{0} このデータは、他の端末で更新されたため処理できません。
                        setMessage(WmsMessageFormatter.format(6003021, param.getRowIndex()));
                        setErrorRowIndex(param.getRowIndex());
                    }
                    else
                    {
                        // このデータは、他の端末で更新されたため処理できません。
                        setMessage("6003006");
                    }
                    return false;
                }
            }
            else if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(pairShelf.getStatusFlag()))
            {
                // 設定チェックの場合
                if (check.equals(CHECK_SET))
                {
                    //6023272=No.{0} 奥棚が空棚のため設定できません。
                    setMessage(WmsMessageFormatter.format(6023272, param.getRowIndex()));
                    setErrorRowIndex(param.getRowIndex());
                }
                else
                {
                    // 6023314=奥棚が空棚のため追加できません。
                    setMessage("6023314");
                }
                return false;
            }
            else
            {
                // 設定チェックの場合
                if (check.equals(CHECK_SET))
                {
                    //6023273=No.{0} 奥棚が予約棚のため設定できません。
                    setMessage(WmsMessageFormatter.format(6023273, param.getRowIndex()));
                    setErrorRowIndex(param.getRowIndex());
                }
                else
                {
                    // 6023315=奥棚が予約棚のため追加できません。
                    setMessage("6023315");
                }
                return false;
            }
        }

        // 問題が無ければtrueを返却
        return true;
    }


    /* 2012/06/12 add start */
    /**
     * 奥棚の在庫削除時に、手前棚に在庫が存在しないか確認します。
     * このメソッドは、奥棚の在庫削除時にパレット上在庫がなくなる場合にのみ呼ばれます。
     * @return true：削除可、false: 削除不可
     * @throws ReadWriteException
     * @throws NoPrimaryException
     */
    protected boolean checkStockOnSideShelf(ScheduleParams param)
            throws ReadWriteException,
                NoPrimaryException
    {
        // AS/RS棚情報ハンドラの生成
        ShelfHandler slfh = new ShelfHandler(getConnection());
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey slfKey = new ShelfSearchKey();
        // エリア情報コントローラの生成
        AreaController areaCon = new AreaController(getConnection(), getClass());
        // 検索キーのセット
        // 倉庫ステーションNo.
        slfKey.setWhStationNo(areaCon.getWhStationNo(param.getString(AREA_NO)));
        // ステーションNo.
        slfKey.setStationNo(param.getString(SHELF_LOCATION_NO));
        Shelf mntShelf = (Shelf)slfh.findPrimary(slfKey);
        if (StringUtil.isBlank(mntShelf.getPairStationNo()))
        {
            // ダブルディープ棚ではないので、trueを返す。
            return true;
        }
        // 奥棚メンテナンス時に手前棚が存在するかチェックします.
        if (Shelf.BANK_SELECT_FAR.equals(mntShelf.getSide()))
        {
            // 検索キーのセット
            slfKey.clear();
            // 倉庫ステーションNo.
            slfKey.setWhStationNo(mntShelf.getWhStationNo());
            // ステーションNo.
            slfKey.setStationNo(mntShelf.getPairStationNo());
            Shelf pairShelf = (Shelf)slfh.findPrimary(slfKey);
            // ペア棚が実棚であれば、エラーを返す
            if (Shelf.LOCATION_STATUS_FLAG_STORAGED.equals(pairShelf.getStatusFlag())
                    || Shelf.LOCATION_STATUS_FLAG_RESERVATION.equals(pairShelf.getStatusFlag()))
            {
                // 6023305=手前棚が空棚でないため設定出来ません。
                setMessage("6023305");
                return false;
            }
        }
        return true;
    }
    /* 2012/06/12 add end */

    /**
     * AS/RSエリアの登録を行います。
     *
     * @param checkParam チェックを行う内容が含まれたパラメータクラス
     * @return チェック結果(true:正常、false:異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean registAsrs(ScheduleParams... ps)
            throws CommonException
    {
        // 件数分繰り返し
        for (ScheduleParams p : ps)
        {
            // 棚情報のチェックを行う
            if (!check(p))
            {
                // 問題があればfalseを返却
                return false;
            }
        }

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

        int rowNum = 0;

        try
        {
            for (rowNum = 0; rowNum < ps.length; rowNum++)
            {
                // パレットIDを取得
                Pallet[] wPlt = getPallet(ps[rowNum].getString(SHELF_LOCATION_NO));
                if (!ArrayUtil.isEmpty(wPlt))
                {
                    // データが存在すればパラメータに追加
                    ps[rowNum].set(PALLET_ID, wPlt[0].getPalletId());
                }

                //ロック(在庫情報、パレット情報、AS/RS棚情報)
                if (!StringUtil.isBlank(ps[rowNum].getString(PALLET_ID)))
                {
                    // 実棚、或いは、同一棚に再入庫する場合はロック処理をする。
                    AsStockController stockCtr = new AsStockController(this.getConnection(), this.getClass());
                    stockCtr.lockPallet(ps[rowNum].getString(SHELF_LOCATION_NO), ps[rowNum].getString(PALLET_ID));
                }
            }

            for (rowNum = 0; rowNum < ps.length; rowNum++)
            {
                // 登録パレットが未引当の場合
                if (allocationFlag(this.getConnection(), ps[rowNum]))
                {
                    if (ScheduleParams.ProcessFlag.UPDATE.equals(ps[rowNum].getProcessFlag()))
                    {
                        // 修正処理
                        // 在庫情報を修正します。
                        if (!registStockData(this.getConnection(), ps[rowNum]))
                        {
                            return false;
                        }
                    }
                    else
                    {
                        /* 2012/06/12 modify  start */
                        // 削除処理
                        // 在庫情報を削除します。
                        Stock st = deleteStockData(this.getConnection(), ps[rowNum]);
                        /* 2012/06/12 modify  end */
                        /* 2012/06/12 add start */
                        if (null == st)
                        {
                            return false;
                        }
                        /* 2012/06/12 add end */
                    }
                }
                else
                {
                    // 6023268=No.{0} 指定された棚は現在引当中です。
                    setMessage(WmsMessageFormatter.format(6023268, ps[rowNum].getRowIndex()));
                    setErrorRowIndex(ps[rowNum].getRowIndex());
                    return false;
                }
            }

            // 問題無ければtrueを返却
            return true;
        }
        catch (LockTimeOutException e)
        {
            // No.{0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, ps[rowNum].getRowIndex()));
            setErrorRowIndex(ps[rowNum].getRowIndex());
            return false;
        }
        catch (NotFoundException e)
        {
            // No.{0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, ps[rowNum].getRowIndex()));
            setErrorRowIndex(ps[rowNum].getRowIndex());
            return false;
        }
        catch (DataExistsException e)
        {
            // No.{0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, ps[rowNum].getRowIndex()));
            setErrorRowIndex(ps[rowNum].getRowIndex());
            return false;
        }
    }

    /**
     * パレット情報が引当中か未引当なのかカウントする。
     *
     * @param conn データベースとのコネクションを保持するインスタンス。
     * @param param 設定内容を持つ<CODE>AsrsInParameter</CODE>クラスのインスタンス。
     * @return boolean true:未引当 false:引当中
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowされます。<BR>
     */
    protected boolean allocationFlag(Connection conn, ScheduleParams param)
            throws CommonException
    {
        if (!StringUtil.isBlank(param.getString(PALLET_ID)))
        {
            // 棚に紐付くパレット情報がない。
            return true;
        }

        StockHandler wStHandler = new StockHandler(conn);
        StockSearchKey wStSearchKey = new StockSearchKey();

        // 棚Noにて在庫情報を取得します。（混載又は空PB又は空棚情報を取得します）
        wStSearchKey.setAreaNo(param.getString(AREA_NO));
        wStSearchKey.setLocationNo(param.getString(LOCATION_NO));
        wStSearchKey.setPalletId(param.getString(PALLET_ID));

        wStSearchKey.setKey(Pallet.ALLOCATION_FLAG, SystemDefine.ALLOCATION_FLAG_ALLOCATED);

        // 結合条件
        wStSearchKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);

        int readStock = wStHandler.count(wStSearchKey);

        if (readStock == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 棚・パレット情報の登録処理を行います。
     * 引数で指定されたパラメータより、該当する棚・パレット情報を登録する。
     *
     * @param conn データベースとのコネクションを保持するインスタンス。
     * @param rParam 設定内容を持つ<CODE>AsScheduleParameter</CODE>クラスのインスタンス。
     * @return boolean
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean registStockData(Connection conn, ScheduleParams param)
            throws CommonException
    {
        // 新しく取得するパレットID
        String pallet = null;
        // 空PBの商品コードを取得します。
        String wEmpItemCode = WmsParam.EMPTYPB_ITEMCODE;

        // 在庫情報エンティティの生成
        Stock[] readStock = null;

        // 新規在庫ではない場合
        if (!StringUtil.isBlank(param.getString(PALLET_ID)))
        {
            // 在庫情報ハンドラの生成
            StockHandler wStHandler = new StockHandler(conn);
            // 在庫情報検索キーの生成
            StockSearchKey wStSearchKey = new StockSearchKey();

            // 検索キーのセット
            // エリアNo.
            wStSearchKey.setAreaNo(param.getString(AREA_NO));
            // 棚No.
            wStSearchKey.setLocationNo(param.getString(LOCATION_NO));
            // パレットID
            wStSearchKey.setPalletId(param.getString(PALLET_ID));

            // 検索実行
            readStock = (Stock[])wStHandler.find(wStSearchKey);
        }

        // 新規在庫の場合はパレット情報を新規作成
        boolean isNeedCreatePallet = false;
        if (readStock == null || readStock.length <= 0)
        {
            // 新規在庫フラグ
            isNeedCreatePallet = true;

            // WMSシーケンスハンドラの生成
            WMSSequenceHandler sequence = new WMSSequenceHandler(conn);

            // 次パレットIDを取得
            pallet = sequence.nextPalletId();
        }
        else
        {
            pallet = param.getString(PALLET_ID);
        }

        // 棚状態が空棚の場合、パレット情報の登録を行います。
        if (isNeedCreatePallet)
        {
            // パレット情報ハンドラの生成
            PalletHandler wPlHandler = new PalletHandler(conn);
            // パレット情報エンティティの生成
            Pallet regPallet = new Pallet();

            // パレットID
            regPallet.setPalletId(pallet);
            // 現在ステーションNo
            regPallet.setCurrentStationNo(param.getString(SHELF_LOCATION_NO));
            // 倉庫ステーションNo
            regPallet.setWhStationNo(param.getString(WH_STATION_NO));
            // 在庫状態
            regPallet.setStatusFlag(Pallet.PALLET_STATUS_REGULAR);
            // 引当状態
            regPallet.setAllocationFlag(Pallet.ALLOCATION_FLAG_NOT_ALLOCATED);
            // パレット状態
            if (wEmpItemCode.equals(param.getString(ITEM_CODE)) && param.getInt(STOCK_QTY) == 1)
            {
                // 空パレット
                regPallet.setEmptyFlag(Pallet.EMPTY_FLAG_EMPTY);
            }
            else
            {
                // 通常パレット
                regPallet.setEmptyFlag(Pallet.EMPTY_FLAG_NORMAL);
            }
            // パレットの荷高
            regPallet.setHeight(0);
            // パレットのソフトゾーン
            Shelf shelf = getShelf(param.getString(SHELF_LOCATION_NO));
            regPallet.setSoftZoneId(shelf.getSoftZoneId());
            // 処理名
            regPallet.setRegistPname(getClass().getSimpleName());
            // 最終更新処理名
            regPallet.setLastUpdatePname(getClass().getSimpleName());

            // 新規作成
            wPlHandler.create(regPallet);
        }

        // 在庫情報ハンドラの生成
        StockHandler stkh = new StockHandler(this.getConnection());
        // 在庫情報検索キーの生成
        StockSearchKey stkKey = new StockSearchKey();
        // 在庫情報コントローラの生成
        StockController stockCtr = new StockController(conn, this.getClass());

        // 増減区分
        String incDecType = "";
        // 作業タイプ
        String jobType = "";

        String stociId = "";

        if (!StringUtil.isBlank(param.getString(STOCK_ID)))
        {
            stociId = param.getString(STOCK_ID);
        }

        // 在庫情報を検索
        Stock oldStock = null;
        stkKey.setStockId(stociId);
        oldStock = (Stock)stkh.findPrimary(stkKey);

        // 入力値の在庫情報エンティティ
        Stock newParam = new Stock();
        // パレットID
        newParam.setPalletId(pallet);
        // 入庫日時
        Date storageDate = WmsFormatter.toDate(param.getDate(STORAGE_DATE), param.getDate(STORAGE_TIME));
        newParam.setStorageDate(storageDate);
        // 最終出庫日
        String retDate = WmsFormatter.toDispDate(param.getDate(LAST_RETRIEVAL_DATE), getLocale());
        newParam.setRetrievalDay(WmsFormatter.toParamDate(retDate, getLocale()));
        // 在庫数
        newParam.setStockQty(param.getInt(MODIFIED_QTY));
        // 出庫可能数
        int allocationQty = readStock[0].getAllocationQty() - (readStock[0].getStockQty() - param.getInt(MODIFIED_QTY));
        newParam.setAllocationQty(allocationQty);
        // 荷主コード
        newParam.setConsignorCode(param.getString(CONSIGNOR_CODE));

        // 在庫数は増減判別
        if (oldStock.getStockQty() > param.getInt(MODIFIED_QTY))
        {
            // 減算
            incDecType = StockHistory.INC_DEC_TYPE_STOCK_DECREMENT;
            jobType = StockHistory.JOB_TYPE_MAINTENANCE_MINUS;
        }
        else
        {
            // 加算
            incDecType = StockHistory.INC_DEC_TYPE_STOCK_INCREMENT;
            jobType = StockHistory.JOB_TYPE_MAINTENANCE_PLUS;
        }

        // 在庫情報、在庫情報更新履歴更新
        stockCtr.update(oldStock, newParam, incDecType, jobType, getWmsUserInfo(), SystemDefine.DEFAULT_REASON_TYPE);

        if (WmsParam.EMPTYPB_ITEMCODE.equals(param.getString(ITEM_CODE))
                && oldStock.getStockQty() != param.getInt(MODIFIED_QTY))
        {
            PalletAlterKey akey = new PalletAlterKey();

            akey.setPalletId(newParam.getPalletId());

            if (param.getInt(MODIFIED_QTY) == 1)
            {
                akey.updateEmptyFlag(Pallet.EMPTY_FLAG_EMPTY);
            }
            else
            {
                akey.updateEmptyFlag(Pallet.EMPTY_FLAG_NORMAL);
            }

            PalletHandler handler = new PalletHandler(conn);

            handler.modify(akey);
        }

        // 問題なければtrueを返却
        return true;
    }

    /**
     * 在庫・棚・パレット情報の削除処理を行います。
     * 引数で指定されたパラメータより、該当する在庫・棚・パレット情報を削除する。
     *
     * @param conn データベースとのコネクションオブジェクト
     * @param rParam パラメータ
     * @return 在庫情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected Stock deleteStockData(Connection conn, ScheduleParams param)
            throws CommonException
    {
        StockHandler wStHandler = new StockHandler(conn);
        StockSearchKey wStSearchKey = new StockSearchKey();
        Stock readStock = new Stock();

        ShelfHandler wSlHandler = new ShelfHandler(conn);
        ShelfAlterKey wSlAlterKey = new ShelfAlterKey();

        PalletHandler wPlHandler = new PalletHandler(conn);
        PalletSearchKey wPlSearchKey = new PalletSearchKey();

        // 在庫IDにて削除を行います。
        wStSearchKey.setStockId(param.getString(STOCK_ID));

        // 検索結果を取得
        readStock = (Stock)wStHandler.findPrimary(wStSearchKey);

        StockController stockCtr = new StockController(conn, this.getClass());

        // 修正時、在庫数は増減か加減判別

        // 減算
        String incDecType = StockHistory.INC_DEC_TYPE_STOCK_DECREMENT;
        String jobType = StockHistory.JOB_TYPE_MAINTENANCE_MINUS;

        // 在庫情報、在庫情報更新履歴更新
        stockCtr.delete(readStock, incDecType, jobType, getWmsUserInfo());

        wStSearchKey.clear();
        // 同一パレットに在庫の詰め合わせがないか確認します
        wStSearchKey.setPalletId(readStock.getPalletId());

        // 在庫情報が存在しない場合、SHelf情報の更新・パレット情報の削除を行います。
        if (wStHandler.count(wStSearchKey) == 0)
        {
            /* 2012/06/12 add start */
            if (!checkStockOnSideShelf(param))
            {
                return null;
            }
            /* 2012/06/12 add end */

            // ステーションNoがロケーションNoにて一致する情報
            AreaController areaCtr = new AreaController(conn, this.getClass());
            wSlAlterKey.setStationNo(areaCtr.toAsrsLocation(readStock.getAreaNo(), readStock.getLocationNo()));
            // 棚状態を空棚の更新します。
            wSlAlterKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);

            wSlHandler.modify(wSlAlterKey);

            // パレット情報の削除を行います。
            wPlSearchKey.setPalletId(readStock.getPalletId());

            wPlHandler.drop(wPlSearchKey);
        }

        return (readStock);
    }

    /**
     * 平置きエリアの登録を行います。
     *
     * @param checkParam チェックを行う内容が含まれたパラメータクラス
     * @return チェック結果(true:正常、false:異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean registLocate(ScheduleParams... ps)
            throws CommonException
    {
        // 在庫情報ハンドラの生成
        StockHandler stockHandler = null;
        // 在庫情報検索キーの生成
        StockSearchKey stockSearchKey = null;

        // 日次更新中かチェックを行う
        if (!canStart())
        {
            return false;
        }

        int rowNum = 0;
        try
        {
            // 在庫コントローラ
            StockController stockcontrol = new StockController(getConnection(), this.getClass());
            //入出庫作業情報Handler類のインスタンス生成
            stockHandler = new StockHandler(getConnection());
            stockSearchKey = new StockSearchKey();

            for (rowNum = 0; rowNum < ps.length; rowNum++)
            {
                // サーチキーをクリア
                stockSearchKey.clear();

                // 在庫IDをセット
                stockSearchKey.setStockId(ps[rowNum].getString(STOCK_ID));
                // 最終更新日時をセット
                stockSearchKey.setLastUpdateDate(ps[rowNum].getDate(LAST_UPDATE_DATE));
                // ロック処理
                if (stockHandler.findPrimaryForUpdate(stockSearchKey, StockHandler.WAIT_SEC_NOWAIT) == null)
                {
                    // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
                    setMessage(WmsMessageFormatter.format(6023015, ps[rowNum].getRowIndex()));
                    setErrorRowIndex(ps[rowNum].getRowIndex());
                    return false;
                }
            }

            for (rowNum = 0; rowNum < ps.length; rowNum++)
            {
                // サーチキーをクリア
                stockSearchKey.clear();

                // 在庫IDをセット
                stockSearchKey.setStockId(ps[rowNum].getString(STOCK_ID));
                // 最終更新日時をセット
                stockSearchKey.setLastUpdateDate(ps[rowNum].getDate(LAST_UPDATE_DATE));

                // 検索結果を取得
                Stock oldStock = (Stock)stockHandler.findPrimary(stockSearchKey);

                // 修正登録
                if (ScheduleParams.ProcessFlag.UPDATE.equals(ps[rowNum].getProcessFlag()))
                {
                    Stock newStock = new Stock();

                    // 修正項目をセット
                    // 在庫数,引当可能数
                    int newTotalStock = ps[rowNum].getInt(MODIFIED_QTY);

                    if (newTotalStock != 0)
                    {
                        newStock.setStockQty(newTotalStock);
                        newStock.setAllocationQty(oldStock.getAllocationQty() + newTotalStock - oldStock.getStockQty());
                    }
                    else
                    {
                        newStock.setStockQty(oldStock.getStockQty());
                        newStock.setAllocationQty(oldStock.getAllocationQty());
                    }

                    // 入庫日時
                    Date storageDate =
                            WmsFormatter.toDate(ps[rowNum].getDate(STORAGE_DATE), ps[rowNum].getDate(STORAGE_TIME));
                    newStock.setStorageDate(storageDate);
                    // 最終出庫日
                    newStock.setRetrievalDay(WmsFormatter.toParamDate(ps[rowNum].getDate(LAST_RETRIEVAL_DATE)));

                    // 在庫増 & 日時修正
                    if (newTotalStock - oldStock.getStockQty() >= 0)
                    {
                        stockcontrol.update(oldStock, newStock, SystemDefine.INC_DEC_TYPE_STOCK_INCREMENT,
                                SystemDefine.JOB_TYPE_MAINTENANCE_PLUS, getWmsUserInfo(), 0);
                    }
                    // 在庫減
                    else if (newTotalStock - oldStock.getStockQty() < 0)
                    {
                        stockcontrol.update(oldStock, newStock, SystemDefine.INC_DEC_TYPE_STOCK_DECREMENT,
                                SystemDefine.JOB_TYPE_MAINTENANCE_MINUS, getWmsUserInfo(), 0);
                    }
                }
                // 削除
                else if (ScheduleParams.ProcessFlag.DELETE.equals(ps[rowNum].getProcessFlag()))
                {
                    // 引当済在庫は削除不可
                    if (oldStock.getStockQty() - oldStock.getAllocationQty() > 0)
                    {
                        // 6023237=No.{0} 引当済み在庫のため削除できません。
                        setMessage(WmsMessageFormatter.format(6023237, ps[rowNum].getRowIndex()));
                        setErrorRowIndex(ps[rowNum].getRowIndex());
                        return false;
                    }

                    // 在庫コントローラ 在庫削除処理
                    stockcontrol.delete(oldStock, SystemDefine.INC_DEC_TYPE_STOCK_DECREMENT,
                            SystemDefine.JOB_TYPE_MAINTENANCE_MINUS, getWmsUserInfo());
                }
            }

            return true;
        }
        catch (LockTimeOutException e)
        {
            // No.{0} 他端末で処理中のため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, ps[rowNum].getRowIndex()));
            setErrorRowIndex(ps[rowNum].getRowIndex());
            return false;
        }
        catch (NotFoundException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, ps[rowNum].getRowIndex()));
            setErrorRowIndex(ps[rowNum].getRowIndex());
            return false;
        }
        catch (DataExistsException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, ps[rowNum].getRowIndex()));
            setErrorRowIndex(ps[rowNum].getRowIndex());
            return false;
        }
        catch (NoPrimaryException e)
        {
            // 6023015=No.{0} 他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023014, ps[rowNum].getRowIndex()));
            setErrorRowIndex(ps[rowNum].getRowIndex());
            return false;
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
