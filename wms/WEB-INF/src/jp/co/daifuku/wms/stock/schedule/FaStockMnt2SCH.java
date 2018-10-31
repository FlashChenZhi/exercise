// $Id: FaStockMnt2SCH.java 117 2008-10-06 11:00:54Z admin $
package jp.co.daifuku.wms.stock.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.stock.schedule.FaStockMnt2SCHParams.*;

import java.sql.Connection;
import java.util.Date;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.bluedog.util.DispResources;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.asrs.entity.DoubleDeepShelf;
import jp.co.daifuku.wms.asrs.location.ShelfOperator;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.AsStockController;
import jp.co.daifuku.wms.base.controller.CarryInfoController;
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.LocateController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZonePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StockHistory;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.WmsChecker;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;

/**
 * 在庫メンテナンス(追加)のスケジュール処理を行います。
 * 
 * @version $Revision: 117 $, $Date:: 2008-10-06 20:00:54 +0900#$
 * @author BusiTune 1.0 Generator.
 * @author Last commit: $Author: admin $
 */
public class FaStockMnt2SCH
        extends AbstractSCH
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

    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * 指定されたパラメータでSCHを作成します。
     * 
     * @param conn DBコネクション
     * @param parent 呼び出し元クラスクラス情報
     * @param locale ロケール
     * @param ui ユーザ情報
     * @throws CommonException ユーザ定義の例外を通知します
     */
    public FaStockMnt2SCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
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
        // 日付項目のチェックを行う
        if (!checkDate(checkParam.getDate(STORAGE_DATE), checkParam.getDate(STORAGE_TIME)))
        {
            return false;
        }

        // 入力項目のチェックを行う
        if (!checkInput(checkParam.getInt(STOCK_QTY)))
        {
            // 問題があればfalseを返却
            return false;
        }

        // 指定エリアがAS/RSの場合
        if (checkArea(checkParam.getString(AREA_NO)))
        {
            // AS/RSのチェックを行う
            if (checkAsrs(checkParam))
            {
                // 問題なければtrueを返却
                return true;
            }
        }
        else
        {
            // 平置きのチェックを行う
            if (checkLocate(checkParam))
            {
                // 問題無ければtrueを返却
                return true;
            }
        }

        // 問題無ければtrueを返却
        return false;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、スケジュールを開始します。<BR>
     *
     * @param checkParam 設定内容を持つ<CODE>ScheduleParams</CODE>。 <BR>
     * @throws CommonException 全ての例外を報告します
     * @return スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     */
    public boolean startSCH(ScheduleParams checkParam)
            throws CommonException
    {
        // 指定エリアがAS/RSの場合
        if (checkArea(checkParam.getString(AREA_NO)))
        {
            // AS/RSの登録を行う
            if (!registAsrs(checkParam))
            {
                // 問題があればfalseを返却
                return false;
            }
        }
        else
        {
            // 平置きの登録を行う
            if (!registLocate(checkParam))
            {
                // 問題があればfalseを返却
                return false;
            }
        }

        // 問題が無ければtrueを返却
        return true;
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
     * AS/RS棚のチェックを行います。
     * 
     * @param checkParam チェックを行う内容が含まれたパラメータクラス
     * @return チェック結果(true:正常、false:異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkAsrs(ScheduleParams checkParam)
            throws CommonException
    {
        // エリア情報コントローラの生成
        AreaController areaCtrl = new AreaController(getConnection(), getClass());

        // 倉庫ステーションNo.の取得
        String whStationNo = areaCtrl.getWhStationNo(checkParam.getString(AREA_NO));
        checkParam.set(WH_STATION_NO, whStationNo);

        // 棚No.(DMSHELF形式)の取得
        String shelfLocationNo =
                areaCtrl.toAsrsLocation(checkParam.getString(AREA_NO), checkParam.getString(LOCATION_NO));
        checkParam.set(SHELF_LOCATION_NO, shelfLocationNo);

        // マスタのチェックを行う
        if (!checkMaster(checkParam.getString(CONSIGNOR_CODE), checkParam.getString(ITEM_CODE)))
        {
            // 問題があればfalseを返却
            return false;
        }

        // 異常棚のチェックを行う
        if (!checkIrregularItem(checkParam.getString(ITEM_CODE)))
        {
            // 問題があればfalseを返却
            return false;
        }

        // 直行品番のチェックを行う
        if (!checkSimpleDirectTransferItem(checkParam.getString(ITEM_CODE)))
        {
            // 問題があればfalseを返却
            return false;
        }

        // 棚在庫内の重複チェックを行う
        if (!multiStockCheck(checkParam))
        {
            // 問題があればfalseを返却
            return false;
        }

        // 棚在庫内の混載条件チェックを行う
        if (!mixedloadStockCheck(checkParam))
        {
            // 問題があればfalseを返却
            return false;
        }

        // ソフトゾーンのチェックを行う
        if (!checkSoftZone(checkParam))
        {
            return false;
        }

        // 棚情報のチェックを行う
        if (checkShelf(checkParam))
        {
            // ダブルディープのチェックを行う
            if (!checkDoubleDeepLocation(checkParam))
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

        // 空パレットの入力条件チェックを行う
        if (!isCorrectEmptyPB(checkParam.getString(ITEM_CODE), checkParam.getString(LOT_NO)))
        {
            // 問題があればfalseを返却
            return false;
        }

        // 問題が無ければtrueを返却
        return true;
    }

    /**
     * ソフトゾーンのチェックを行います。
     * 
     * @param p チェックを行う内容が含まれたパラメータクラス
     * @param areaNo エリアNo.
     * @param shelfLocationNo 棚No.(DMSHELF形式)
     * @return チェック結果(true:正常、false:異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkSoftZone(ScheduleParams p)
            throws CommonException
    {
        // パレット情報エンティティ配列の生成
        Pallet[] wPlt = null;
        // 在庫情報エンティティ配列の生成
        Stock[] stock = null;

        // パレットIDを取得
        wPlt = getPallet(p.getString(SHELF_LOCATION_NO));
        if (!ArrayUtil.isEmpty(wPlt))
        {
            // データが存在すればパラメータに追加
            p.set(PALLET_ID, wPlt[0].getPalletId());
        }

        // パレットが存在する場合のみ
        if (!StringUtil.isBlank(p.getString(PALLET_ID)))
        {
            // 在庫情報ハンドラの生成
            StockHandler sthandler = new StockHandler(getConnection());
            // 在庫情報検索キーの生成
            StockSearchKey skey = new StockSearchKey();

            // 検索キーの設定
            // エリアNo.
            skey.setAreaNo(p.getString(AREA_NO));
            // 棚No.
            skey.setLocationNo(p.getString(LOCATION_NO));
            // パレットID
            skey.setPalletId(p.getString(PALLET_ID));

            // 在庫情報の取得
            stock = (Stock[])sthandler.find(skey);
        }

        // 空パレットかどうかを判断するフラグ
        boolean emppb = false;
        if (WmsParam.EMPTYPB_CONSIGNORCODE.equals(p.getString(CONSIGNOR_CODE))
                && WmsParam.EMPTYPB_ITEMCODE.equals(p.getString(ITEM_CODE)))
        {
            emppb = true;
        }

        // ソフトゾーンチェックが必要か判断するフラグ
        boolean isCheck = false;

        // 在庫が1つもない場合
        if (stock == null || stock.length <= 0)
        {
            // 登録する商品が空パレット以外の場合
            if (!emppb)
            {
                // 必要
                isCheck = true;
            }
        }
        else
        {
            // 空PB在庫で在庫数が1の場合
            if (stock[0].getStockQty() == 1 && stock[0].getItemCode().equals(WmsParam.EMPTYPB_ITEMCODE))
            {
                // 必要
                isCheck = true;
            }
        }

        // ソフトゾーンチェックが必要の場合
        if (isCheck)
        {
            // フリーソフトゾーンの場合はチェックしない
            if (isFreeZoneItem(p))
            {
                return true;
            }

            // ソフトゾーンID(商品マスタ)の取得
            String item_sz = getItemSoftZone(p.getString(CONSIGNOR_CODE), p.getString(ITEM_CODE));
            // ソフトゾーンID(AS/RS棚情報)の取得
            String shelf_sz = getShelf(p.getString(SHELF_LOCATION_NO)).getSoftZoneId();

            // ソフトゾーン優先順情報ハンドラの生成
            SoftZonePriorityHandler szpHandler = new SoftZonePriorityHandler(getConnection());
            // ソフトゾーン優先順情報検索キーの生成
            SoftZonePrioritySearchKey szpKey = new SoftZonePrioritySearchKey();

            // 検索キーのセット
            // 倉庫ステーションNo.
            szpKey.setWhStationNo(p.getString(WH_STATION_NO));
            // ソフトゾーンID(商品マスタ)
            szpKey.setSoftZoneId(item_sz);
            // ソフトゾーンID(AS/RS棚情報)
            szpKey.setPrioritySoftZone(shelf_sz);

            // データが存在しない場合
            if (szpHandler.count(szpKey) <= 0)
            {
                // MSG-W0064=ソフトゾーンが異なる商品を登録します。よろしいですか？
                setDispMessage("MSG-W0064");
                return false;
            }
        }
        else if (!emppb)
        {
            // ソフトゾーンが異なる商品が既に存在する場合
            if (!mixedSoftZoneCheck(p))
            {
                // 問題があればfalseを返却
                return false;
            }
        }

        // 問題が無ければtrueを返却
        return true;
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
     * 商品マスタよりソフトゾーンIDを取得します。
     * 
     * @param cCode 荷主コード
     * @param iCode 商品コード
     * @return ソフトゾーンID
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected String getItemSoftZone(String cCode, String iCode)
            throws CommonException
    {
        // 商品情報ハンドラの生成
        ItemHandler handler = new ItemHandler(this.getConnection());
        // 商品情報検索キーの生成
        ItemSearchKey skey = new ItemSearchKey();

        // 検索条件をセットします。
        // 荷主コード
        skey.setConsignorCode(cCode);
        // 商品コード
        skey.setItemCode(iCode);

        // 商品情報を取得します。
        Item ent = (Item)handler.findPrimary(skey);
        if (ent != null)
        {
            // ソフトゾーンIDを返却
            return ent.getSoftZoneId();
        }

        // データが無い場合は、0を返却
        return Item.SOFT_ZONE_FREE;
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
     * 棚在庫情報にて、ソフトゾーンの違う商品が存在するかチェックを行います。
     * 
     * @param rParam 検索条件を含む<CODE>Parameter</CODE>オブジェクト
     * @return 存在しない場合はTrue、存在した場合はFalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean mixedSoftZoneCheck(ScheduleParams param)
            throws CommonException
    {
        // ソフトゾーンが商品マスタ管理かつソフトゾーン違い混載可の場合
        if (WmsParam.SOFTZONE_SELECT_ITEM && WmsParam.MIXED_SOFTZONE)
        {
            // フリーソフトゾーンの場合はチェックしない
            if (isFreeZoneItem(param))
            {
                return true;
            }

            // ソフトゾーンID(商品マスタ)を取得
            String item_sz = getItemSoftZone(param.getString(CONSIGNOR_CODE), param.getString(ITEM_CODE));
            // ソフトゾーンID(AS/RS棚情報)を取得
            String shelf_sz = getShelf(param.getString(SHELF_LOCATION_NO)).getSoftZoneId();

            // ソフトゾーン優先順情報ハンドラの生成
            SoftZonePriorityHandler szpHandler = new SoftZonePriorityHandler(getConnection());
            // ソフトゾーン優先順情報検索キーの生成
            SoftZonePrioritySearchKey szpKey = new SoftZonePrioritySearchKey();

            // 検索キーのセット
            // 倉庫ステーションNo.
            szpKey.setWhStationNo(param.getString(WH_STATION_NO));
            // ソフトゾーンID(商品コード)
            szpKey.setSoftZoneId(item_sz);
            // ソフトゾーンID(AS/RS棚情報)
            szpKey.setPrioritySoftZone(shelf_sz);

            // データが存在しない場合
            if (szpHandler.count(szpKey) <= 0)
            {
                // MSG-W0064=ソフトゾーンが異なる商品を登録します。よろしいですか？
                setDispMessage("MSG-W0064");
                return false;
            }
        }
        // ソフトゾーン違い混載不可の場合
        else if (!(WmsParam.MIXED_SOFTZONE || StringUtil.isBlank(param.getString(PALLET_ID))))
        {
            // ソフトゾーンID(商品マスタ)を取得
            String softZone = getItemSoftZone(param.getString(CONSIGNOR_CODE), param.getString(ITEM_CODE));

            // 在庫情報ハンドラの生成
            StockHandler sHandler = new StockHandler(getConnection());
            // 在庫情報検索キーの生成
            StockSearchKey ssKey = new StockSearchKey();

            // 検索キーのセット
            // エリアNo.
            ssKey.setAreaNo(param.getString(AREA_NO));
            // 棚No.
            ssKey.setLocationNo(param.getString(LOCATION_NO));

            // 取得項目
            // ソフトゾーンID
            ssKey.setCollect(Item.SOFT_ZONE_ID);

            // 集計情報
            // ソフトゾーンID
            ssKey.setGroup(Item.SOFT_ZONE_ID);

            // 在庫情報と商品情報の結合
            // 荷主コード
            ssKey.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
            // 商品コード
            ssKey.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);

            // 画面で入力したエリア、棚のストック情報を取得する
            Entity[] stocks = sHandler.find(ssKey);
            for (Entity stock : stocks)
            {
                // 検索したパレットのソフトゾーンと画面で入力した商品コードのソフトゾーンが同じか比較する。
                if (!stock.getValue(Item.SOFT_ZONE_ID).equals(softZone))
                {
                    // MSG-W0064=ソフトゾーンが異なる商品を登録します。よろしいですか？
                    setDispMessage("MSG-W0064");
                    return false;
                }
            }
        }

        // 問題が無ければtrueを返却
        return true;
    }

    /**
     * 棚の状態をチェックし、メンテナンス可能かどうか判断します。
     * 
     * @param param パラメータ
     * @return メンテナンス可能の場合true、それ以外の場合はfalseを返す
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkShelf(ScheduleParams param)
            throws CommonException
    {
        //ASRS棚情報検索
        Shelf wShelf = getShelf(param.getString(SHELF_LOCATION_NO));
        if (wShelf == null)
        {
            // 6023067=実在する棚No.を入力してください
            setMessage("6023067");
            return false;
        }

        // アクセス不可棚チェックを行う
        if (Shelf.ACCESS_NG_FLAG_NG.equals(wShelf.getAccessNgFlag()))
        {
            // 6023098=指定された棚はアクセス不可棚のためメンテナンスできません
            setMessage("6023098");
            return false;
        }

        // 禁止棚チェックを行う
        if (Shelf.PROHIBITION_FLAG_NG.equals(wShelf.getProhibitionFlag()))
        {
            // 6123274=指定された棚は禁止棚に設定されているため、設定できません。
            setMessage("6123274");
            return false;
        }

        // 予約棚のチェックを行う
        if (Shelf.LOCATION_STATUS_FLAG_RESERVATION.equals(wShelf.getStatusFlag()))
        {
            // 6023086=指定された棚は予約棚のためメンテナンスできません
            setMessage("6023086");
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
                // 6023070=指定された棚は現在引当中です。
                setMessage("6023070");
                return false;
            }
            else if (Pallet.PALLET_STATUS_IRREGULAR.equals(pltstatus))
            {
                // 6023071=指定された棚は異常棚のため設定できません。
                setMessage("6023071");
                return false;
            }
        }

        // 画面で入力された棚No.の棚状態を取得します。
        // アクセス可能棚
        if (Shelf.ACCESS_NG_FLAG_OK.equals(wShelf.getAccessNgFlag()))
        {
            // 禁止棚
            if (Shelf.PROHIBITION_FLAG_NG.equals(wShelf.getProhibitionFlag()))
            {
                // LBL-W0059=禁止棚
                setMessage("LBL-W0059");
            }
            else
            {
                // 空棚
                if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(wShelf.getStatusFlag()))
                {
                    // LBL-W0061=空棚
                    setMessage("LBL-W0061");
                }
                // 実棚
                else if (Shelf.LOCATION_STATUS_FLAG_STORAGED.equals(wShelf.getStatusFlag()))
                {
                    // 実棚でかつ禁止棚のときは禁止棚として表示
                    if (Shelf.PROHIBITION_FLAG_NG.equals(wShelf.getProhibitionFlag()))
                    {
                        // LBL-W0059=禁止棚
                        setMessage("LBL-W0059");
                    }
                    // 出庫中(作業棚として表示)
                    else if (Pallet.PALLET_STATUS_RETRIEVAL.equals(wPallet[0].getStatusFlag())
                            || Pallet.PALLET_STATUS_RETRIEVAL_PLAN.equals(wPallet[0].getStatusFlag()))
                    {
                        // LBL-W0237=作業棚
                        setMessage("LBL-W0237");
                    }
                    else
                    {
                        // 空パレット棚
                        if (Pallet.EMPTY_FLAG_EMPTY.equals(wPallet[0].getEmptyFlag()))
                        {
                            // LBL-W0456=空パレット棚
                            setMessage("LBL-W0456");
                        }
                        else
                        {
                            // 異常棚
                            if (Pallet.PALLET_STATUS_IRREGULAR.equals(wPallet[0].getStatusFlag()))
                            {
                                // LBL-W0036=異常棚
                                setMessage("LBL-W0036");
                            }
                            // 実棚
                            else
                            {
                                // LBL-W0104=実棚
                                setMessage("LBL-W0104");
                            }
                        }
                    }
                }
                // 予約棚(作業棚として表示)
                else if (Shelf.LOCATION_STATUS_FLAG_RESERVATION.equals(wShelf.getStatusFlag()))
                {
                    // LBL-W0237=作業棚
                    setMessage("LBL-W0237");
                }
            }
        }

        // 問題が無ければtrueを返却
        return true;
    }

    /**
     * ダブルディープの棚の状態チェックを行います。
     * @param param パラメータ
     * @return true：メンテナンス可能、false: メンテナンス不可
     * @throws CommonException データベース処理でエラー発生した場合にthrowする
     */
    protected boolean checkDoubleDeepLocation(ScheduleParams param)
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
            // ダブルディープ棚のチェックを行う
            if (isLastTemporayLocation(mntShelf))
            {
                // 6023303=ペアの空棚が無くなるので設定できません。
                setMessage("6023303");
                return false;
            }

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
                    // 奥棚のShelfが実棚の時、手前棚の在庫が有れば取得する
                    Pallet[] nearPlt = getPallet(mntShelf.getStationNo());

                    if (Pallet.PALLET_STATUS_REGULAR.equals(plt[0].getStatusFlag())
                            && Pallet.ALLOCATION_FLAG_NOT_ALLOCATED.equals(plt[0].getAllocationFlag()))
                    {
                        // 奥棚が実棚で未引当なので、trueを返す。
                        return true;
                    }
                    else if (Pallet.PALLET_STATUS_IRREGULAR.equals(plt[0].getStatusFlag()))
                    {
                        // 奥棚が異常棚の時
                        if (nearPlt.length != 0)
                        {
                            // 手前棚に在庫が有るならば、奥が異常棚でも手前棚のパレットには積み増し可能
                            return true;
                        }
                        else
                        {
                            // 6023308=奥棚が異常棚のため設定できません。
                            setMessage("6023308");
                            return false;
                        }
                    }
                    else
                    {
                        // 奥棚が引当中の時
                        if (nearPlt.length != 0)
                        {
                            // 手前棚に在庫が有るならば、奥が引当中でも手前棚のパレットには積み増し可能
                            return true;
                        }
                        else
                        {
                            // 6023310=奥棚は現在引当中です。
                            setMessage("6023310");
                            return false;
                        }
                    }
                }
                else
                {
                    // ここでPalletが無いのは、この処理途中に他で更新が行われた。
                    // このデータは、他の端末で更新されたため処理できません。
                    setMessage("6003006");
                    return false;
                }
            }
            else if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(pairShelf.getStatusFlag()))
            {
                // 6023307=奥棚が空棚のため設定できません。
                setMessage("6023307");
                return false;
            }
            else
            {
                // 6023306=奥棚が予約棚のため設定できません。
                setMessage("6023306");
                return false;
            }
        }
        // 奥棚に登録
        else
        {
            // 最後のペア空棚かチェックする。
            if (isLastPairEmptyShelf(param.getString(AREA_NO), param.getString(LOCATION_NO)))
            {
                // 6023303=ペアの空棚が無くなるので設定できません。
                setMessage("6023303");
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    /**
     * ダブルディープの最後の仮置棚を確認します。
     * 
     * @param areaNo エリアNo
     * @param shelfNo 棚No（入力形式の棚No）
     * @return 最後仮置棚ならばtrue、そうでないならば、falseを返す。
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean isLastTemporayLocation(Shelf shelf)
            throws CommonException
    {
        // AS/RS棚情報ハンドラの生成
        ShelfHandler slfh = new ShelfHandler(getConnection());
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey slfKey = new ShelfSearchKey();

        // 検索キーのセット
        // 倉庫ステーションNo.
        slfKey.setWhStationNo(shelf.getWhStationNo());
        // 親ステーションNo.
        slfKey.setParentStationNo(shelf.getParentStationNo());
        // ハードゾーンID
        slfKey.setHardZoneId(shelf.getHardZoneId());
        // ステーションNo.
        slfKey.setStationNo(shelf.getStationNo(), "!=");
        // 状態フラグ
        slfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
        // 棚状態
        slfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);
        // アクセス不可棚フラグ
        slfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
        if (slfh.count(slfKey) >= 2)
        {
            // データが2件以上存在
            return false;
        }

        // データが2件未満の場合
        return true;
    }

    /**
     * ダブルディープの最後のペア空棚を確認します。
     * 引数で渡された棚が属するゾーンに有るペアの空棚数を返す。
     * @param areaNo エリアNo
     * @param shelfNo 棚No（入力形式の棚No）
     * @return 最後ペア空棚ならばtrue、そうでないならば、falseを返す。
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean isLastPairEmptyShelf(String areaNo, String shelfNo)
            throws CommonException
    {
        // ダブルディープのペア空棚チェックを行う
        if (!isPairEmptyShelf(areaNo, shelfNo))
        {
            // 空棚では無かった場合falseを返却
            return false;
        }

        // エリア情報コントローラの生成
        AreaController areaCtl = new AreaController(getConnection(), getClass());
        ShelfOperator sOp = new ShelfOperator(getConnection());
        int count = sOp.countPairEmptyShelf(areaCtl.getWhStationNo(areaNo), areaCtl.toAsrsLocation(areaNo, shelfNo));
        if (count <= 1)
        {
            // 最後の場合はtrueを返却
            return true;
        }

        // 最後ではない場合はfalseを返却
        return false;
    }

    /**
     * ダブルディープのペア空棚を確認します。
     * @param areaNo エリアNo
     * @param shelfNo 棚No（入力形式の棚No）
     * @return ペアで空棚ならばtrue、そうでないならば、falseを返します。
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean isPairEmptyShelf(String areaNo, String shelfNo)
            throws CommonException
    {
        // エリア情報コントローラの生成
        AreaController areaCtl = new AreaController(getConnection(), getClass());
        // ステーション情報の生成
        Station st = StationFactory.makeStation(getConnection(), areaCtl.toAsrsLocation(areaNo, shelfNo));

        // ダブルディープではない場合
        if (!(st instanceof DoubleDeepShelf))
        {
            // falseを返却
            return false;
        }

        // 対象棚が空棚の場合
        DoubleDeepShelf ddShelf = (DoubleDeepShelf)st;
        if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(ddShelf.getStatusFlag())
                && Shelf.PROHIBITION_FLAG_OK.equals(ddShelf.getProhibitionFlag())
                && Shelf.ACCESS_NG_FLAG_OK.equals(ddShelf.getAccessNgFlag()))
        {
            // ペアの棚も空棚の場合
            st = StationFactory.makeStation(getConnection(), ddShelf.getPairStationNo());
            DoubleDeepShelf pairShelf = (DoubleDeepShelf)st;
            if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(pairShelf.getStatusFlag())
                    && Shelf.PROHIBITION_FLAG_OK.equals(pairShelf.getProhibitionFlag())
                    && Shelf.ACCESS_NG_FLAG_OK.equals(pairShelf.getAccessNgFlag()))
            {
                // ペアの空棚なのでtrueを返す。
                return true;
            }
        }

        // 空棚ではない場合、falseを返却
        return false;
    }

    /** 
     * 在庫用の空パレット用の入力チェックを行います。<BR>
     * 抽象クラスのisCorrectEmptyPBメソッドをオーバーライドしています。<BR>
     * 荷主コード・商品コードともに空パレット用のコードでない場合は、trueを返します。<BR>
     * 入力NGと判断する条件は以下の通り<BR>
     * <BR>
     * 1.荷主コードが空パレ用荷主で、商品コードが空パレ用商品でない場合<BR>
     * 2.商品コードが空パレ用商品で、荷主コードが空パレ用荷主でない場合<BR>
     * <BR>
     * @param pItemCode 商品コード<BR>
     * @param pLotNo ロットNo
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
     */
    protected boolean isCorrectEmptyPB(String pItemCode, String pLotNo)
    {
        // 入力された商品コードが通常商品コードの場合
        if (!WmsParam.EMPTYPB_ITEMCODE.equals(pItemCode))
        {
            // 通常商品の場合はtrueを返却
            return true;
        }

        // ロットが入力されている場合
        if (pLotNo.length() != 0)
        {
            // 6023099=ロットNo.は入力できません。
            setMessage("6023099");
            return false;
        }

        // 問題が無ければtrueを返却
        return true;
    }

    /**
     * 異常棚用の荷主コード、商品コードが使用されていないかをチェックします。<BR>
     * 入力NGと判断する条件は以下の通りです。<BR>
     * <BR>
     * 1.商品コードが、異常棚用の商品コードの場合<BR>
     * 
     * @param itemcode 商品コード
     * @return 商品コードが通常のコードであればtrueを、異常棚用の商品コードの場合はfalse返します。
     *          falseの場合、メッセージエリアにメッセージをセットします
     */
    protected boolean checkIrregularItem(String itemcode)
    {
        // 異常棚商品コードの場合
        if (WmsParam.IRREGULAR_ITEMCODE.equals(itemcode))
        {
            // 6023078={0}は異常棚用の商品コードのため、使用できません。
            setMessage(WmsMessageFormatter.format(6023078, itemcode));
            return false;
        }

        // 問題が無ければtrueを返却
        return true;
    }

    /**
     * 簡易直行用の商品コードが使用されていないかをチェックします。<BR>
     * 入力NGと判断する条件は以下の通りです。<BR>
     * <BR>
     * 1.商品コードが、簡易直行用の商品コードの場合<BR>
     * 
     * @param itemcode 商品コード
     * @return 商品コードが通常のコードであればtrueを、簡易直行用の商品コードの場合はfalse返します。
     *          falseの場合、メッセージエリアにメッセージをセットします
     */
    protected boolean checkSimpleDirectTransferItem(String itemcode)
    {
        // 簡易直行商品コードの場合
        if (WmsParam.SIMPLEDIRECTTRANSFER_ITEMCODE.equals(itemcode))
        {
            // 6023259={0}は簡易直行用の商品コードのため、使用できません。
            setMessage(WmsMessageFormatter.format(6023259, itemcode));
            return false;
        }

        // 問題が無ければtrueを返却
        return true;
    }

    /**
     * 棚在庫情報にて、同一商品の有無チェックを行います。
     * 
     * @param rParam 検索条件を含む<CODE>Parameter</CODE>オブジェクト
     * @return 同一商品が存在しない場合はTrue、存在した場合はFalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean multiStockCheck(ScheduleParams param)
            throws CommonException
    {
        // 在庫情報ハンドラの生成
        StockHandler wStockHandler = new StockHandler(this.getConnection());
        // 在庫情報検索キーの生成
        StockSearchKey wStockSearchKey = new StockSearchKey();

        // 検索キーのセット
        // エリアNo.
        wStockSearchKey.setAreaNo(param.getString(AREA_NO));
        // 棚No.
        wStockSearchKey.setLocationNo(param.getString(LOCATION_NO));
        // 荷主コード
        wStockSearchKey.setConsignorCode(param.getString(CONSIGNOR_CODE));
        // 商品コード
        wStockSearchKey.setItemCode(param.getString(ITEM_CODE));
        // ロットNo.
        wStockSearchKey.setLotNo(param.getString(LOT_NO));

        // 新規在庫の場合
        if (StringUtil.isBlank(param.getString(PALLET_ID)))
        {
            // 搬送情報コントローラの生成
            CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
            // 搬送情報検索キーの生成
            CarryInfoSearchKey carryKey = carryControl.getEmptyShelfPallet();

            // 検索条件のセット
            // 在庫のパレットIDと同一ではないこと
            wStockSearchKey.setKey(Stock.PALLET_ID, carryKey, "!=", "", "", true);
        }
        else
        {
            // パレットID
            wStockSearchKey.setPalletId(param.getString(PALLET_ID));
        }

        if (wStockHandler.count(wStockSearchKey) > 0)
        {
            // 6023020=既に同一データが存在するため、入力できません。
            setMessage("6023020");
            return false;
        }

        // 問題が無ければtrueを返却
        return true;
    }

    /**
     * 棚在庫情報にて、混載条件チェックを行います。
     * 
     * @param rParam 検索条件を含む<CODE>Parameter</CODE>オブジェクト
     * @return 混載可だった場合はTrue、混載不可だった場合はFalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean mixedloadStockCheck(ScheduleParams param)
            throws CommonException
    {
        // 在庫情報ハンドラの生成
        StockHandler wStockHandler = new StockHandler(this.getConnection());
        // 在庫情報検索キーの生成
        StockSearchKey wStockSearchKey = new StockSearchKey();

        // 検索キーのセット
        // エリアNo.
        wStockSearchKey.setAreaNo(param.getString(AREA_NO));
        // 棚No.
        wStockSearchKey.setLocationNo(param.getString(LOCATION_NO));

        // 新規在庫の場合 
        if (StringUtil.isBlank(param.getString(PALLET_ID)))
        {
            // 搬送情報コントローラの生成
            CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
            // 搬送情報検索キーの生成
            CarryInfoSearchKey carryKey = carryControl.getEmptyShelfPallet();

            // 検索キーのセット
            // 在庫情報のパレットIDと同一ではないこと
            wStockSearchKey.setKey(Stock.PALLET_ID, carryKey, "!=", "", "", true);
        }
        else
        {
            // パレットID
            wStockSearchKey.setPalletId(param.getString(PALLET_ID));
        }

        // 在庫情報と入庫データの空パレットチェックを行います。
        Stock[] stk = (Stock[])wStockHandler.find(wStockSearchKey);
        if (!checkAddStockEnptyPB(param.getString(ITEM_CODE), stk, false))
        {
            // 混載不可の場合はfalseを返却
            return false;
        }

        // 混載可能の場合はtrueを返却
        return true;
    }

    /**
     * 在庫情報と入力データの空パレットチェックを行います。<BR>
     * パラメータには以下のデータをセットしてください。<BR>
     * 
     * @param itemCode 商品コード
     * @param stock 在庫情報
     * @param pileEmptyPb 段積み空パレットフラグ(積増可:true 積み増し不可:false)
     * @return boolean チェックOKならtrue、それ以外でfalseを返します。
     * @throws CommonException 全ての例外をスローします。
     */
    protected boolean checkAddStockEnptyPB(String itemCode, Stock[] stock, boolean pileEmptyPb)
            throws CommonException
    {
        // 在庫データが無い場合、無条件でtrueを返します。
        if (stock == null || stock.length == 0)
        {
            return true;
        }

        // 空パレットの在庫ならば、配列数は１
        getClass();
        if (WmsParam.EMPTYPB_ITEMCODE.equals(stock[0].getItemCode()))
        {
            //空パレットを追加
            if (WmsParam.EMPTYPB_ITEMCODE.equals(itemCode))
            {
                // 在庫メンテ処理では、空パレット(段積)に空パレットを混載できない
                if (stock[0].getStockQty() > 1 && !pileEmptyPb)
                {
                    // 6023076=空パレット在庫です。修正処理にて数量変更して下さい。
                    setMessage("6023076");
                    return false;
                }
            }
            //空パレット(段積)に通常在庫混在できない
            else if (stock[0].getStockQty() > 1)
            {
                // 6023091=段積みの空パレットに、在庫は混載できません。。
                setMessage("6023091");
                return false;
            }
        }

        //入力した商品コードが空パレットの場合
        if (WmsParam.EMPTYPB_ITEMCODE.equals(itemCode))
        {
            for (Stock lStock : stock)
            {
                if (!WmsParam.EMPTYPB_ITEMCODE.equals(lStock.getItemCode()))
                {
                    //6023075=通常の在庫と空パレットは混載できません。
                    setMessage("6023075");
                    return false;
                }
            }
        }

        // 問題が無ければtrueを返却
        return true;
    }

    /**
     * 平置き棚のチェックを行います。
     * 
     * @param checkParam チェックを行う内容が含まれたパラメータクラス
     * @return チェック結果(true:正常、false:異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkLocate(ScheduleParams checkParam)
            throws CommonException
    {
        // マスタのチェックを行う
        if (!checkMaster(checkParam.getString(CONSIGNOR_CODE), checkParam.getString(ITEM_CODE)))
        {
            // 問題があればfalseを返却
            return false;
        }

        // 指定した在庫のチェックを行う
        if (!checkDuplicate(checkParam))
        {
            // 問題があればfalseを返却
            return false;
        }

        // 入庫棚のチェックを行う
        if (!checkStorageLocation(checkParam))
        {
            // 問題があればfalseを返却
            return false;
        }

        // 問題無ければtrueを返却
        return true;
    }

    /**
     * 日付項目のチェックを行います。
     * 
     * @param day 入庫日付
     * @param time 入庫日時
     * @return チェック結果(true:正常、false:異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkDate(Date day, Date time)
            throws CommonException
    {
        // eWareNavi入力チェッカーの生成
        WmsChecker chk = new WmsChecker();

        // 日付項目のチェックを行う
        if (!chk.checkDate(day, time))
        {
            setMessage(chk.getMessage());
            return false;
        }

        // 問題無ければtrueを返却
        return true;
    }

    /**
     * マスタ存在チェックを行います。
     * 
     * @param cCode 荷主コード
     * @param iCode 商品コード
     * @return チェック結果(true:正常、false:異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkMaster(String cCode, String iCode)
            throws CommonException
    {
        // システム定義情報コントローラクラスを生成
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());

        // マスタパッケージを導入している場合のみ
        if (systemController.hasMasterPack())
        {
            // 荷主情報ハンドラの生成
            ConsignorHandler consignorHandler = new ConsignorHandler(getConnection());
            // 荷主情報検索キーの生成
            ConsignorSearchKey consignorKey = new ConsignorSearchKey();
            // 荷主コード 
            consignorKey.setConsignorCode(cCode);

            // 存在チェック(荷主コード)
            if (consignorHandler.count(consignorKey) <= 0)
            {
                // 6023040=荷主コードがマスタに登録されていません。
                setMessage("6023040");
                return false;
            }

            // 商品情報ハンドラの生成
            ItemHandler itemHandler = new ItemHandler(getConnection());
            // 商品情報検索キーの生成
            ItemSearchKey itemKey = new ItemSearchKey();
            // 荷主コード
            itemKey.setConsignorCode(cCode);
            // 商品コード
            itemKey.setItemCode(iCode);

            // 存在チェック(商品コード)
            if (itemHandler.count(itemKey) <= 0)
            {
                // 6023021=商品コードがマスタに登録されていません。
                setMessage("6023021");
                return false;
            }
        }

        // 問題無ければtrueを返却
        return true;
    }

    /**
     * 入力項目のチェックを行います。
     * 
     * @param sQty 在庫数
     * @return チェック結果(true:正常、false:異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkInput(int sQty)
            throws CommonException
    {
        // 在庫数が未入力の場合
        if (sQty == 0)
        {
            // 6023616=在庫数には1以上の値を入力してください。
            setMessage(WmsMessageFormatter.format(6023616, DispResources.getText("LBL-W0073"), 1));
            return false;
        }

        // 在庫上限数を上回っている場合
        if (sQty > WmsParam.MAX_STOCK_QTY)
        {
            // 6023217=在庫数には在庫上限数{0}以下の値を入力してしてください。
            setMessage(WmsMessageFormatter.format(6023190, MAX_STOCK_QTY_DISP));
            return false;
        }

        // 問題無ければtrueを返却
        return true;
    }

    /**
     * 在庫情報の重複チェックを行います。
     * 
     * @param p チェックを行う内容が含まれたパラメータクラス
     * @return チェック結果(true : 正常 false : 異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkDuplicate(ScheduleParams p)
            throws CommonException
    {
        // 在庫情報ハンドラの生成
        StockHandler stockHandler = new StockHandler(getConnection());

        // 検索条件のセット
        StockSearchKey stockKey = createStockSearchKey(p);

        // 在庫データが存在する場合
        if (stockHandler.count(stockKey) > 0)
        {
            // 6023037=既に在庫データが存在するため、入力できません。
            setMessage("6023037");
            return false;
        }

        // データが存在しない場合はtrueを返却
        return true;
    }

    /**
     * 在庫情報検索キーを作成します。
     * 
     * @param p 検索情報を含むパラメータ
     * @return 在庫情報の検索キー
     */
    protected StockSearchKey createStockSearchKey(ScheduleParams p)
    {
        // 在庫情報検索キーの生成
        StockSearchKey stockKey = new StockSearchKey();

        // 検索条件のセット
        // エリア
        stockKey.setAreaNo(p.getString(AREA_NO));
        // 棚
        stockKey.setLocationNo(p.getString(LOCATION_NO));
        // 荷主コード
        stockKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        stockKey.setItemCode(p.getString(ITEM_CODE));
        // ロットNo.
        stockKey.setLotNo(p.getString(LOT_NO));
        // 在庫数が0以上のデータ
        stockKey.setStockQty(0, ">");

        // 生成した検索キーを返却
        return stockKey;
    }

    /**
     * 入庫棚のチェックを行います。
     * 
     * @param p チェックを行う内容が含まれたパラメータクラス
     * @return チェック結果(true:正常、false:異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkStorageLocation(ScheduleParams p)
            throws CommonException
    {
        // 棚情報コントローラの生成
        LocateController locateCtrl = new LocateController(getConnection(), getClass());
        // 在庫情報エンティティの生成
        Stock stock = new Stock();

        // エリアNo.
        stock.setAreaNo(p.getString(AREA_NO));
        // 棚No.
        stock.setLocationNo(p.getString(LOCATION_NO));
        // 荷主コード
        stock.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        stock.setItemCode(p.getString(ITEM_CODE));

        try
        {
            // 入庫棚チェック
            locateCtrl.checkStorageLocate(stock);

            // 問題無ければtrueを返却
            return true;
        }
        // オペレータ例外が発生した場合
        catch (OperatorException e)
        {
            // MSG-W0016=登録棚ではありません。よろしいですか？
            setDispMessage("MSG-W0016");
            return false;
        }
    }

    /**
     * <CODE>checkParam</CODE>の内容を元に、マスタ情報のチェックを行います。<BR>
     * マスタなしの場合にマスタ情報の更新(マスタが存在しなければ登録)を行います。<BR>
     * @param p 入力チェックを行う内容が含まれたパラメータクラス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected void checkMasterUpdate(ScheduleParams p)
            throws CommonException
    {
        // システム定義情報コントローラの生成
        WarenaviSystemController systemController = new WarenaviSystemController(getConnection(), this.getClass());

        // マスタパッケージが導入されている場合
        if (!systemController.hasMasterPack())
        {
            // 荷主情報コントローラの生成
            ConsignorController consignorCtrl = new ConsignorController(getConnection(), this.getClass());
            // 荷主情報エンティティの生成
            Consignor consignor = new Consignor();

            // 荷主マスタ情報
            consignor.clear();
            consignor.setConsignorCode(p.getString(CONSIGNOR_CODE));
            consignorCtrl.autoCreate(consignor, getWmsUserInfo());


            // 商品情報コントローラの生成
            ItemController itemCtrl = new ItemController(getConnection(), this.getClass());
            // 商品情報エンティティの生成
            Item item = new Item();

            // 商品マスタ情報
            item.clear();
            item.setConsignorCode(p.getString(CONSIGNOR_CODE));
            item.setItemCode(p.getString(ITEM_CODE));
            item.setItemName(p.getString(ITEM_NAME));
            itemCtrl.autoCreate(item, getWmsUserInfo());
        }
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
     * AS/RSエリアの登録を行います。
     * 
     * @param checkParam チェックを行う内容が含まれたパラメータクラス
     * @return チェック結果(true:正常、false:異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean registAsrs(ScheduleParams checkParam)
            throws CommonException
    {
        // エリア情報コントローラの生成
        AreaController areaCtrl = new AreaController(getConnection(), getClass());

        // 倉庫ステーションNo.の取得
        String whStationNo = areaCtrl.getWhStationNo(checkParam.getString(AREA_NO));
        checkParam.set(WH_STATION_NO, whStationNo);
        // 棚No.(DMSHELF形式)の取得
        String shelfLocationNo =
                areaCtrl.toAsrsLocation(checkParam.getString(AREA_NO), checkParam.getString(LOCATION_NO));
        checkParam.set(SHELF_LOCATION_NO, shelfLocationNo);

        // 棚情報のチェックを行う
        if (checkShelf(checkParam))
        {
            // ダブルディープのチェックを行う
            if (!checkDoubleDeepLocation(checkParam))
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

        try
        {
            // パレットIDを取得
            Pallet[] wPlt = getPallet(checkParam.getString(SHELF_LOCATION_NO));
            if (!ArrayUtil.isEmpty(wPlt))
            {
                // データが存在すればパラメータに追加
                checkParam.set(PALLET_ID, wPlt[0].getPalletId());
            }

            //ロック(在庫情報、パレット情報、AS/RS棚情報)
            if (!StringUtil.isBlank(checkParam.getString(PALLET_ID)))
            {
                // 実棚、或いは、同一棚に再入庫する場合はロック処理をする。
                AsStockController stockCtr = new AsStockController(this.getConnection(), this.getClass());
                stockCtr.lockPallet(checkParam.getString(SHELF_LOCATION_NO), checkParam.getString(PALLET_ID));
            }

            // 登録パレットが未引当の場合
            if (allocationFlag(this.getConnection(), checkParam))
            {
                // 在庫情報ハンドラの生成
                StockHandler stkh = new StockHandler(this.getConnection());
                // 在庫情報検索キーの生成
                StockSearchKey stkKey = new StockSearchKey();

                // 検索キーのセット
                // エリアNo.
                stkKey.setAreaNo(checkParam.getString(AREA_NO));
                // 棚No.
                stkKey.setLocationNo(checkParam.getString(LOCATION_NO));

                // 新規在庫の場合
                if (StringUtil.isBlank(checkParam.getString(PALLET_ID)))
                {
                    // 搬送情報コントローラの生成
                    CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
                    // 搬送情報検索キーの生成
                    CarryInfoSearchKey carryKey = carryControl.getEmptyShelfPallet();

                    // 検索キーのセット
                    // 在庫情報のパレットIDと同一ではないこと
                    stkKey.setKey(Stock.PALLET_ID, carryKey, "!=", "", "", true);
                }
                else
                {
                    // パレットID
                    stkKey.setPalletId(checkParam.getString(PALLET_ID));
                }

                // 最大混載数を超える場合は、エラーとする
                if (stkh.count(stkKey) >= getMaxMixedPallet(this.getConnection(), checkParam.getString(WH_STATION_NO)))
                {
                    // 6023095=混載数が最大混載数を超えるため、設定できません。
                    setMessage("6023095");
                    return false;
                }

                //在庫情報を登録します。
                if (!registStockData(this.getConnection(), checkParam))
                {
                    // 問題があればfalseを返却
                    return false;
                }

                // 6401003=登録しました。
                setMessage("6401003");
            }
            else
            {
                // 6023070=指定された棚は現在引当中です。
                setMessage("6023070");
                return false;
            }

            // 問題無ければtrueを返却
            return true;
        }
        catch (LockTimeOutException e)
        {
            // 6027008=このデータは他の端末で更新中のため、処理できません。
            setMessage("6027008");
            return false;
        }
        catch (NotFoundException e)
        {
            // このデータは、他の端末で更新されたため処理できません。
            setMessage("6003006");
            return false;
        }
        catch (DataExistsException e)
        {
            // このデータは、他の端末で更新されたため処理できません。
            setMessage("6003006");
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
        // 新規在庫の場合
        if (!StringUtil.isBlank(param.getString(PALLET_ID)))
        {
            // trueを返却
            return true;
        }

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
        // 引当状態
        wStSearchKey.setKey(Pallet.ALLOCATION_FLAG, SystemDefine.ALLOCATION_FLAG_ALLOCATED);

        // 結合条件
        // 在庫情報とパレット情報のパレットIDが同一
        wStSearchKey.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
        if (wStHandler.count(wStSearchKey) == 0)
        {
            // 未引当の場合はtrueを返却
            return true;
        }
        else
        {
            // 引当済みの場合はfalseを返却
            return false;
        }
    }

    /**
     * 倉庫の最大混載数を取得します。
     * 
     * @param conn データベースコネクション
     * @param whStationNo 倉庫ステーションNo.
     * @return 最大混載数
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected int getMaxMixedPallet(Connection conn, String whStationNo)
            throws CommonException
    {
        // 倉庫情報ハンドラの生成
        WareHouseHandler whHandler = new WareHouseHandler(conn);
        // 倉庫情報検索キーの生成
        WareHouseSearchKey whKey = new WareHouseSearchKey();
        // 倉庫情報エンティティの生成
        WareHouse wh = null;

        // 検索キーのセット
        // 倉庫ステーションNo.
        whKey.setStationNo(whStationNo);

        // 検索実行
        wh = (WareHouse)whHandler.findPrimary(whKey);

        // 最大混載数を返却
        return wh.getMaxMixedpallet();
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
            // 空PB在庫の場合
            if (readStock[0].getStockQty() == 1 && readStock[0].getItemCode().equals(wEmpItemCode))
            {
                // 在庫情報の削除(在庫とパレットを削除)
                ScheduleParams delParam = new ScheduleParams();
                delParam.set(STOCK_ID, readStock[0].getStockId());
                deleteStockData(conn, delParam);

                // 新規在庫フラグ
                isNeedCreatePallet = true;
            }
            // 既存在庫がある場合はパレットIDはそのまま
            pallet = readStock[0].getPalletId();
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

        // AS/RS棚情報ハンドラの生成
        ShelfHandler wSlHandler = new ShelfHandler(conn);
        // AS/RS棚情報更新キーの生成
        ShelfAlterKey wSlAlterKey = new ShelfAlterKey();

        // Shelf情報の更新処理を行います。
        // ステーションNo.
        wSlAlterKey.setStationNo(param.getString(SHELF_LOCATION_NO));
        // 状態フラグ(実棚)
        wSlAlterKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED);

        // 更新実行
        wSlHandler.modify(wSlAlterKey);

        // 在庫情報コントローラの生成
        StockController stockCtr = new StockController(conn, this.getClass());

        // 増減区分
        String incDecType = "";
        // 作業タイプ
        String jobType = "";

        // 入力値の在庫情報エンティティ
        Stock stock = new Stock();
        // エリアNo.
        stock.setAreaNo(param.getString(AREA_NO));
        // 棚No.
        stock.setLocationNo(param.getString(LOCATION_NO));
        // 商品コード
        stock.setItemCode(param.getString(ITEM_CODE));
        // 在庫数
        stock.setStockQty(param.getInt(STOCK_QTY));
        // 引当可能数
        stock.setAllocationQty(param.getInt(STOCK_QTY));
        // ロットNo.
        stock.setLotNo(param.getString(LOT_NO));
        // 入庫日時
        Date sDate = param.getDate(STORAGE_DATE);
        Date STime = param.getDate(STORAGE_TIME);
        if (StringUtil.isBlank(sDate))
        {
            sDate = WmsFormatter.toDate(DbDateUtil.getSystemDate());
        }
        if (StringUtil.isBlank(STime))
        {
            STime = WmsFormatter.toDateTime(DbDateUtil.getSystemDateTime());
        }
        stock.setStorageDate(WmsFormatter.toDate(sDate, STime));
        // パレットID
        stock.setPalletId(pallet);
        // 荷主コード
        stock.setConsignorCode(param.getString(CONSIGNOR_CODE));

        // 加算
        incDecType = StockHistory.INC_DEC_TYPE_STOCK_INCREMENT;
        jobType = StockHistory.JOB_TYPE_MAINTENANCE_PLUS;

        // 新規作成
        stockCtr.insert(stock, incDecType, jobType, getWmsUserInfo(), SystemDefine.DEFAULT_REASON_TYPE);

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
        // 在庫情報コントローラの生成
        StockController stockCtr = new StockController(conn, this.getClass());
        // 在庫情報ハンドラの生成
        StockHandler wStHandler = new StockHandler(conn);
        // 在庫情報検索キーの生成
        StockSearchKey wStSearchKey = new StockSearchKey();
        // 在庫情報エンティティの生成
        Stock readStock = new Stock();

        // AS/RS棚情報ハンドラの生成
        ShelfHandler wSlHandler = new ShelfHandler(conn);
        // AS/RS棚情報検索キーの生成
        ShelfAlterKey wSlAlterKey = new ShelfAlterKey();

        // パレット情報ハンドラの生成
        PalletHandler wPlHandler = new PalletHandler(conn);
        // パレット情報検索キーの生成
        PalletSearchKey wPlSearchKey = new PalletSearchKey();

        // 検索キーのセット
        // 在庫ID
        wStSearchKey.setStockId(param.getString(STOCK_ID));

        // 検索実行
        readStock = (Stock)wStHandler.findPrimary(wStSearchKey);

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
            // エリア情報コントローラの生成
            AreaController areaCtr = new AreaController(conn, this.getClass());

            // 更新キーのセット
            // ステーションNo.
            wSlAlterKey.setStationNo(areaCtr.toAsrsLocation(readStock.getAreaNo(), readStock.getLocationNo()));
            // 棚状態を空棚に変更
            wSlAlterKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);

            // 更新実行
            wSlHandler.modify(wSlAlterKey);

            // 削除実行
            wPlSearchKey.setPalletId(readStock.getPalletId());
            wPlHandler.drop(wPlSearchKey);
        }

        // 在庫情報を返却
        return readStock;
    }

    /**
     * 平置きエリアの登録を行います。
     * 
     * @param checkParam チェックを行う内容が含まれたパラメータクラス
     * @return チェック結果(true:正常、false:異常)
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean registLocate(ScheduleParams checkParam)
            throws CommonException
    {
        // 日次更新中かチェックを行う
        if (!canStart())
        {
            return false;
        }

        try
        {
            // 在庫情報コントローラを生成
            StockController stockCtrl = new StockController(getConnection(), this.getClass());
            // 在庫情報エンティティ(ロック用)を生成
            Stock lockStock = new Stock();
            // 在庫情報エンティティ(登録用)を生成
            Stock insStock = new Stock();

            // 検索情報をセット
            // 在庫エンティティ(ロック用)のクリア
            lockStock.clear();
            // エリア
            lockStock.setAreaNo(checkParam.getString(AREA_NO));
            // 棚
            lockStock.setLocationNo(checkParam.getString(LOCATION_NO));
            // 荷主コード
            lockStock.setConsignorCode(checkParam.getString(CONSIGNOR_CODE));
            // 商品コード
            lockStock.setItemCode(checkParam.getString(ITEM_CODE));
            // ロットNo.
            lockStock.setLotNo(checkParam.getString(LOT_NO));

            // 対象データをロックします。
            stockCtrl.lock(lockStock);

            // 指定した在庫のチェックを行う
            if (!checkDuplicate(checkParam))
            {
                return false;
            }

            // マスタチェック
            checkMasterUpdate(checkParam);

            // 登録情報をセット
            // 在庫エンティティ(登録用)のクリア
            insStock.clear();
            // エリア
            insStock.setAreaNo(checkParam.getString(AREA_NO));
            // 棚
            insStock.setLocationNo(checkParam.getString(LOCATION_NO));
            // 荷主コード
            insStock.setConsignorCode(checkParam.getString(CONSIGNOR_CODE));
            // 商品コード
            insStock.setItemCode(checkParam.getString(ITEM_CODE));
            // ロットNo.
            insStock.setLotNo(checkParam.getString(LOT_NO));
            // 在庫数
            insStock.setStockQty(checkParam.getInt(STOCK_QTY));
            // 引当可能数
            insStock.setAllocationQty(checkParam.getInt(STOCK_QTY));
            // 入庫日時
            Date sDate = checkParam.getDate(STORAGE_DATE);
            Date STime = checkParam.getDate(STORAGE_TIME);
            if (StringUtil.isBlank(sDate))
            {
                sDate = WmsFormatter.toDate(DbDateUtil.getSystemDate());
            }
            if (StringUtil.isBlank(STime))
            {
                STime = WmsFormatter.toDateTime(DbDateUtil.getSystemDateTime());
            }
            insStock.setStorageDate(WmsFormatter.toDate(sDate, STime));

            // 在庫情報の登録
            stockCtrl.insert(insStock, SystemDefine.INC_DEC_TYPE_STOCK_INCREMENT,
                    SystemDefine.JOB_TYPE_MAINTENANCE_PLUS, getWmsUserInfo(), SystemDefine.DEFAULT_REASON_TYPE);

            // 登録しました。
            setMessage(WmsMessageFormatter.format(6001003));
            return true;
        }
        catch (LockTimeOutException e)
        {
            // 6027008=このデータは他の端末で更新中のため、処理できません。
            setMessage(WmsMessageFormatter.format(6027008));
            return false;
        }
        catch (DataExistsException e)
        {
            // 6023115=他端末で処理されたため、処理を中断しました。
            setMessage(WmsMessageFormatter.format(6023115));
            return false;
        }
        catch (OperatorException e)
        {
            // Operatorからの例外をcatchし、該当するエラーメッセージを表示する
            // 「他端末で更新済み」か「他端末で作業中」か「作業完了済み」
            if (OperatorException.ERR_ALREADY_UPDATED.equals(e.getErrorCode())
                    || OperatorException.ERR_WORKING_INPROGRESS.equals(e.getErrorCode())
                    || OperatorException.ERR_ALREADY_COMPLETED.equals(e.getErrorCode()))
            {
                // 6023115=他端末で処理されたため、処理を中断しました。
                setMessage(WmsMessageFormatter.format(6023115));
                return false;
            }
            // 上記以外は例外をそのまま投げる
            throw e;
        }
    }

    /**
     * 指定商品がフリーソフトゾーンか返却します。
     * 
     * @param p 画面で指定されたパラメータ
     * @return boolean フリー:true、他:false
     * @throws CommonException
     */
    protected boolean isFreeZoneItem(ScheduleParams p)
            throws CommonException
    {
        // 商品情報ハンドラの生成
        ItemHandler ihandler = new ItemHandler(getConnection());
        // 商品情報検索キーの生成
        ItemSearchKey ikey = new ItemSearchKey();

        // 検索キーの生成
        // 荷主コード
        ikey.setConsignorCode(p.getString(CONSIGNOR_CODE));
        // 商品コード
        ikey.setItemCode(p.getString(ITEM_CODE));

        // 取得項目キーの生成
        // ソフトゾーンID
        ikey.setSoftZoneIdCollect();

        // 検索
        Item item = (Item)ihandler.findPrimary(ikey);

        // フリーソフトゾーンの場合
        if (item != null && Item.SOFT_ZONE_ALL.equals(item.getSoftZoneId()))
        {
            // trueを返却
            return true;
        }

        // フリーソフトゾーンではない場合
        return false;
    }

    // ------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // utility methods
    // ------------------------------------------------------------
    /**
     * このクラスのバージョン情報を返します。
     * 
     * @return version
     */
    public static String getVersion()
    {
        return "";
    }

}
// end of class
