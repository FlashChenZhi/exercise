// $Id: AsShelfMntSCH.java,v 1.2 2009/02/24 02:17:06 ose Exp $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsShelfMntSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsMessageFormatter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.AsStockController;
import jp.co.daifuku.wms.base.controller.CarryInfoController;
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
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
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StockHistory;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.util.DbDateUtil;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS 在庫情報変更のスケジュール処理を行います。
 *
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:17:06 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class AsShelfMntSCH
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
    public AsShelfMntSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
    {
        super(conn, parent, locale, ui);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * マスタパッケージが導入フラグを取得します。<BR>
     * @param searchParam 検索条件をもつ<CODE>StockInParameter</CODE>クラスを継承したクラス
     * @return 検索結果が含まれた<CODE>StockOutParameter</CODE>クラスを実装したインスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public Params initFind(ScheduleParams searchParam)
            throws CommonException
    {
        // システムコントローラよりマスタパッケージの有無を取得します。
        WarenaviSystemController systemController = new WarenaviSystemController(this.getConnection(), this.getClass());

        Params outParam = new Params();
        outParam.set(MASTER, systemController.hasMasterPack());

        return outParam;
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、チェックを行います。<BR>
     *
     * @param p 入力パラメータ
     * @return 入力チェック、オーバーフロー、重複、商品マスタ・入庫棚エラーでない場合はtrueを返す。
     * @throws CommonException チェック処理内で予期しない例外が発生した場合に通知します。
     */
    public boolean check(ScheduleParams p)
            throws CommonException
    {
        setMessage("");
        // 倉庫ステーションNoを取得
        Area wArea = searchArea(p.getString(AREA_NO));
        // 倉庫ステーションNo.
        p.set(WH_STATION_NO, wArea.getWhstationNo());

        // ステーションNoをセット
        AreaController areacon = new AreaController(getConnection(), getClass());
        String station = areacon.toAsrsLocation(p.getString(AREA_NO), p.getString(LOCATION_NO));
        p.set(STATION_NO, station);

        Pallet[] wPlt = null;

        // ソフトゾーンチェックのみのチェックの場合
        if (p.getBoolean(SOFT_ZONE_CHECK) && AsrsInParameter.M_CREATE.equals(p.getString(PROCESSTYPE_KEY)))
        {
            // パレットIDを取得
            wPlt = searchPallet(p.getString(STATION_NO));
            if (!ArrayUtil.isEmpty(wPlt))
            {
                // メンテナンスする棚にパレット情報があるならば、パレットIdを
                // パラメータに取得する。
                p.set(PALLET_ID, wPlt[0].getPalletId());
            }
            Stock[] stock = null;
            if (!StringUtil.isBlank(p.getString(PALLET_ID)))
            {
                StockHandler sthandler = new StockHandler(getConnection());
                StockSearchKey skey = new StockSearchKey();

                // 在庫の検索
                skey.setAreaNo(p.getString(AREA_NO));
                skey.setLocationNo(p.getString(LOCATION_NO));
                skey.setPalletId(p.getString(PALLET_ID));
                stock = (Stock[])sthandler.find(skey);
            }

            // ソフトゾーンチェック要否フラグ
            boolean isCheck = false;
            boolean emppb =
                    WmsParam.EMPTYPB_CONSIGNORCODE.equals(p.getString(CONSIGNOR_CODE))
                            && WmsParam.EMPTYPB_ITEMCODE.equals(p.getString(ITEM_CODE)) ? true
                                                                                       : false;
            // 在庫が1つもない場合
            if (stock == null || stock.length <= 0)
            {
                // 登録する商品が空パレット以外の場合、チェック必要
                if (!emppb)
                {
                    isCheck = true;
                }
            }
            else
            {
                // 空PB在庫で在庫数が1の場合、チェック必要
                if (AsrsInParameter.M_CREATE.equals(p.getString(PROCESSTYPE_KEY)) && stock[0].getStockQty() == 1
                        && stock[0].getItemCode().equals(WmsParam.EMPTYPB_ITEMCODE))
                {
                    isCheck = true;
                }
            }

            // ソフトゾーンチェックが必要か判断する。
            if (isCheck)
            {
                // フリーソフトゾーンの場合はチェックしない
                if (isFreeZoneItem(p))
                {
                    return true;
                }

                String item_sz = getItemSoftZone(p.getString(CONSIGNOR_CODE), p.getString(ITEM_CODE));
                String shelf_sz = searchShelf(p.getString(STATION_NO)).getSoftZoneId();

                SoftZonePriorityHandler szpHandler = new SoftZonePriorityHandler(getConnection());
                SoftZonePrioritySearchKey szpKey = new SoftZonePrioritySearchKey();

                // DMSOFTZONEPRIORITY用検索キー
                szpKey.setWhStationNo(p.getString(WH_STATION_NO));
                szpKey.setSoftZoneId(item_sz);
                szpKey.setPrioritySoftZone(shelf_sz);

                if (szpHandler.count(szpKey) <= 0)
                {
                    // MSG-W0064=ソフトゾーンが異なる商品を登録します。よろしいですか？
                    setDispMessage("MSG-W0064");
                }
            }
            else if (!emppb)
            {
                if (!mixedSoftZoneCheck(p))
                {
                    return false;
                }
            }

            return true;
        }

        // 棚の状態をチェックし、メンテナンス可能かどうか判断します。
        if (AsrsInParameter.M_CREATE.equals(p.getString(PROCESSTYPE_KEY))
                || AsrsInParameter.M_MODIFY.equals(p.getString(PROCESSTYPE_KEY))
                || AsrsInParameter.M_DELETE.equals(p.getString(PROCESSTYPE_KEY)))
        {
            if (checkShelf(p))
            {
                // 棚明細一覧の在庫データを選択した時、その在庫がメンテナンスする棚の
                // パレットに属する在庫データかチェックする。
                if (!StringUtil.isBlank(p.getString(STOCK_ID)))
                {
                    // パレットIDを取得
                    wPlt = searchPallet(p.getString(STATION_NO));
                    if (!ArrayUtil.isEmpty(wPlt))
                    {
                        if (!checkStock(p.getString(STOCK_ID), wPlt[0].getPalletId()))
                        {
                            return false;
                        }
                    }
                }
                if (!checkDoubleDeepLocation(p))
                {
                    // ダブルディープのチェックNG
                    return false;
                }

                if (StringUtil.isBlank(p.getString(ITEM_CODE)))
                {
                    // 登録、修正、削除ボタン押下時はここで終了
                    return true;
                }
            }
            else
            {
                return false;
            }
        }

        // パレット情報の有無チェック
        if (!ArrayUtil.isEmpty(wPlt))
        {
            // メンテナンスする棚にパレット情報があるならば、パレットIdを
            // パラメータに取得する。
            p.set(PALLET_ID, wPlt[0].getPalletId());
        }

        // マスタチェック
        // システムコントローラよりマスタパッケージの有無を取得します。
        WarenaviSystemController systemController = new WarenaviSystemController(this.getConnection(), this.getClass());
        if (systemController.hasMasterPack())
        {
            // マスタパッケージありの場合

            // 商品コードが存在するかチェック
            ItemHandler itemHandler = new ItemHandler(this.getConnection());
            ItemSearchKey itemKey = new ItemSearchKey();

            itemKey.setConsignorCode(p.getString(CONSIGNOR_CODE));
            itemKey.setItemCode(p.getString(ITEM_CODE));

            if (itemHandler.count(itemKey) <= 0)
            {
                // 6023021=商品コードがマスタに登録されていません。
                setMessage("6023021");
                return false;
            }
            else
            {
                //マスタに存在した場合
                Entity ent = itemHandler.findPrimary(itemKey);
                Item itemEnt = (Item)ent;
                //ケース入数
                p.set(ENTERING_QTY, itemEnt.getEnteringQty());
                //JANコード
                p.set(JAN, itemEnt.getJan());
                //ケースITF
                p.set(ITF, itemEnt.getItf());
            }
        }
        else
        {
            //マスタパッケージがない場合、商品マスタを更新する
            ConsignorController consignorCtrl = new ConsignorController(getConnection(), this.getClass());
            ItemController itemCtrl = new ItemController(getConnection(), this.getClass());

            // マスタ情報を格納するEntity
            Consignor consignor = new Consignor();
            Item item = new Item();

            // マスタパッケージなしの場合は登録または更新
            // 荷主マスタ情報
            consignor.clear();
            consignor.setConsignorCode(p.getString(CONSIGNOR_CODE));
            consignorCtrl.autoCreate(consignor, getWmsUserInfo());

            // 商品マスタ情報
            item.clear();
            item.setConsignorCode(p.getString(CONSIGNOR_CODE));
            item.setItemCode(p.getString(ITEM_CODE));
            item.setItemName(p.getString(ITEM_NAME));
            item.setEnteringQty(p.getInt(ENTERING_QTY));
            item.setJan(p.getString(JAN));
            item.setItf(p.getString(ITF));
            itemCtrl.autoCreate(item, getWmsUserInfo());
        }

        StockHandler wStockHandler = new StockHandler(this.getConnection());
        StockSearchKey wStockSearchKey = new StockSearchKey();
        Stock rStock = null;

        int enteringqty = p.getInt(ENTERING_QTY);
        int caseqty = p.getInt(STOCK_CASE_QTY);
        int pieceqty = p.getInt(STOCK_PIECE_QTY);

        //処理区分
        String processkey = p.getString(PROCESSTYPE_KEY);

        // 処理区分により、チェックを行います。
        if (processkey.equals(AsrsInParameter.M_CREATE))
        {
            // 空パレットの入力条件チェック
            if (!isCorrectEmptyPB(p.getString(ITEM_CODE), p.getInt(STOCK_CASE_QTY), p.getInt(STOCK_PIECE_QTY),
                    p.getInt(ENTERING_QTY), p.getString(LOT_NO)))
            {
                return false;
            }
            // 異常棚のチェックを行います
            if (!checkIrregularItem(p.getString(ITEM_CODE)))
            {
                return false;
            }
            // 直行品番のチェックを行います
            if (!checkSimpleDirectTransferItem(p.getString(ITEM_CODE)))
            {
                return false;
            }
            // 入力値チェック
            if (!stockInputCheck(enteringqty, caseqty, pieceqty))
            {
                return false;
            }
            // 棚在庫内の重複チェックを行います。
            if (!multiStockCheck(p))
            {
                return false;
            }
            // 棚在庫内の混載条件チェックを行います。
            if (!mixedloadStockCheck(p))
            {
                return false;
            }
        }
        else if (processkey.equals(AsrsInParameter.M_MODIFY))
        {
            // 在庫IDにて在庫情報の再取得を行います。
            wStockSearchKey.setStockId(p.getString(STOCK_ID));
            wStockSearchKey.setLastUpdateDate(p.getDate(LAST_UPDATE_DATE));

            rStock = (Stock)wStockHandler.findPrimary(wStockSearchKey);
            if (rStock == null)
            {
                // このデータは、他の端末で更新されたため処理できません。
                setMessage("6003006");
                return false;
            }

            // 入力条件チェック
            if (!isCorrectEmptyPB(p.getString(ITEM_CODE), p.getInt(STOCK_CASE_QTY), p.getInt(STOCK_PIECE_QTY),
                    p.getInt(ENTERING_QTY), p.getString(LOT_NO)))
            {
                return false;
            }
            // 入力値チェック
            if (!stockInputCheck(enteringqty, caseqty, pieceqty))
            {
                return false;
            }
            // 棚在庫内の重複チェックを行います。
            if (!multiStockCheck(p))
            {
                return false;
            }
        }
        else
        {
            // 在庫IDにて在庫情報の再取得を行います。
            wStockSearchKey.setStockId(p.getString(STOCK_ID));
            wStockSearchKey.setLastUpdateDate(p.getDate(LAST_UPDATE_DATE));

            rStock = (Stock)wStockHandler.findPrimary(wStockSearchKey);
            if (rStock == null)
            {
                // このデータは、他の端末で更新されたため処理できません。
                setMessage("6003006");
                return false;
            }
        }

        return true;
    }

    /**
     * searchParamで指定されたパラメータの内容を元に、商品マスタデータを検索します。<BR>
     * データが見つからない場合は、要素数0のParameter配列を返します。<BR>
     * @param inParam 検索条件をもつ<CODE>StockInParameter</CODE>クラス
     * @return 検索結果が含まれた<CODE>StockOutParameter</CODE>インスタンス
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    public List<Params> query(ScheduleParams inParam)
            throws CommonException
    {
        // 倉庫ステーションNoを取得
        Area wArea = searchArea(inParam.getString(AREA_NO));
        // 倉庫ステーションNo.
        inParam.set(WH_STATION_NO, wArea.getWhstationNo());

        // システムコントローラよりマスタパッケージの有無を取得します。
        WarenaviSystemController systemController = new WarenaviSystemController(this.getConnection(), this.getClass());
        if (systemController.hasMasterPack())
        {
            ItemHandler itemHandler = new ItemHandler(this.getConnection());
            ItemSearchKey itemKey = new ItemSearchKey();

            // 検索条件をセットする。
            // 荷主コード
            itemKey.setConsignorCode(inParam.getString(CONSIGNOR_CODE));
            // 商品コード
            itemKey.setItemCode(inParam.getString(ITEM_CODE));
            // ソフトゾーンマスタ結合
            itemKey.setJoin(Item.SOFT_ZONE_ID, "", SoftZone.SOFT_ZONE_ID, "(+)");

            // 取得条件
            // 商品マスタ全取得
            itemKey.setCollect(new FieldName(Item.STORE_NAME, FieldName.ALL_FIELDS));
            // ソフトゾーン名称
            itemKey.setCollect(SoftZone.SOFT_ZONE_NAME);

            // 検索する。
            Item[] item = (Item[])itemHandler.find(itemKey);

            // return用のパラメータ
            Params[] outParam = new Params[1];
            outParam[0] = new Params();
            ArrayList<Params> ret = new ArrayList<Params>();

            if (item.length > 0 && item != null)
            {
                // 該当データは一件のはずなので、要素0のみを返す。
                // 荷主コード
                outParam[0].set(CONSIGNOR_CODE, item[0].getConsignorCode());
                // 商品コード
                outParam[0].set(ITEM_CODE, item[0].getItemCode());
                // 商品名称
                outParam[0].set(ITEM_NAME, item[0].getItemName());
                // ソフトゾーン名称
                outParam[0].set(SOFT_ZONE_NAME, item[0].getValue(SoftZone.SOFT_ZONE_NAME, ""));
                // ケース入数
                outParam[0].set(ENTERING_QTY, item[0].getEnteringQty());
                // JANコード
                outParam[0].set(JAN, item[0].getJan());
                // ケースITF
                outParam[0].set(ITF, item[0].getItf());

                ret.add(outParam[0]);
            }
            else
            {
                // 商品コードがマスタに登録されていません。
                setMessage("6023021");
            }

            return ret;
        }
        else
        {
            return new ArrayList<Params>();
        }
    }

    /**
     * 画面から入力された内容をパラメータとして受け取り、予定外入庫設定スケジュールを開始します。<BR>
     * ためうちからの設定など、複数データの入力を想定しているのでパラメータは配列で受け取ります。<BR>
     * 詳しい動作はクラス説明の項を参照してください。<BR>
     * スケジュールが正常終了した場合はtrue、失敗した場合はfalseを返します。
     * @param ps 設定内容を持つ<CODE>IdmControlParameter</CODE>クラスのインスタンスの配列。
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
     * @throws CommonException 処理中に何らかの例外が発生した場合にthrowされます。<BR>
     */
    public boolean startSCH(ScheduleParams... ps)
            throws CommonException
    {
        // 入庫日、入庫時刻チェック
        // 日付、日時どちらか空白の場合
        if (StringUtil.isBlank(ps[0].getDate(STORAGE_DAY)) ^ StringUtil.isBlank(ps[0].getDate(STORAGE_TIME)))
        {
            // 6022064=入庫日時を入力する場合、日付と時間の両方を入力してください。
            setMessage("6022064");
            return false;
        }
        // 現在はBusinessで行っているためありえないがねんのため
        else if (StringUtil.isBlank(ps[0].getDate(STORAGE_DAY)) && StringUtil.isBlank(ps[0].getDate(STORAGE_TIME)))
        {
            String day = DbDateUtil.getSystemDate();
            String time = DbDateUtil.getSystemDateTime();
            // 入庫日時
            ps[0].set(STORAGE_DAY, WmsFormatter.toDate(day));
            ps[0].set(STORAGE_TIME, WmsFormatter.toDateTime(time));
        }

        // 棚状態チェック
        if (!this.check(ps[0]))
        {
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
            setMessage("");

            // パレットIDを取得
            Pallet[] wPlt = searchPallet(ps[0].getString(STATION_NO));
            if (!ArrayUtil.isEmpty(wPlt))
            {
                // メンテナンスする棚にパレット情報があるならば、パレットIdを
                // パラメータに取得する。
                ps[0].set(PALLET_ID, wPlt[0].getPalletId());
            }

            //ロック(在庫情報、パレット情報、AS/RS棚情報)
            if (!StringUtil.isBlank(ps[0].getString(PALLET_ID)))
            {
                // 実棚、或いは、同一棚に再入庫する場合はロック処理をする。
                AsStockController stockCtr = new AsStockController(this.getConnection(), this.getClass());
                stockCtr.lockPallet(ps[0].getString(STATION_NO), ps[0].getString(PALLET_ID));
            }

            String processkey = ps[0].getString(PROCESSTYPE_KEY);
            int inputqty = 0;

            if (allocationFlag(this.getConnection(), ps[0]))
            {
                if (AsrsInParameter.M_CREATE.equals(processkey))
                {
                    // 登録処理
                    // 混載数のチェックを行う。
                    StockHandler stkh = new StockHandler(this.getConnection());
                    StockSearchKey stkKey = new StockSearchKey();
                    //在庫検索キー
                    stkKey.setAreaNo(ps[0].getString(AREA_NO));
                    stkKey.setLocationNo(ps[0].getString(LOCATION_NO));
                    if (StringUtil.isBlank(ps[0].getString(PALLET_ID)))
                    {
                        CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
                        CarryInfoSearchKey carryKey = carryControl.getEmptyShelfPallet();
                        // 検索条件をセットします。
                        stkKey.setKey(Stock.PALLET_ID, carryKey, "!=", "", "", true);
                    }
                    else
                    {
                        stkKey.setPalletId(ps[0].getString(PALLET_ID));
                    }

                    // 最大混載数を超える場合は、エラーとする
                    if (stkh.count(stkKey) >= getMaxMixedPallet(this.getConnection(), ps[0].getString(WH_STATION_NO)))
                    {
                        // 混載数が最大混載数を超えるため、設定できません。
                        setMessage("6023095");
                        return false;
                    }

                    // 入力在庫数***************オペレータに変換
                    inputqty =
                            ps[0].getInt(STOCK_CASE_QTY) * ps[0].getInt(ENTERING_QTY) + ps[0].getInt(STOCK_PIECE_QTY);

                    //在庫情報を登録します。
                    if (!registStockData(this.getConnection(), ps[0], inputqty))
                    {
                        return false;
                    }

                    // 登録しました。
                    setMessage("6401003");

                }
                else if (AsrsInParameter.M_MODIFY.equals(processkey))
                {
                    // 修正処理
                    // 在庫数
                    inputqty =
                            ps[0].getInt(STOCK_CASE_QTY) * ps[0].getInt(ENTERING_QTY) + ps[0].getInt(STOCK_PIECE_QTY);
                    // 在庫情報を修正します。
                    if (!registStockData(this.getConnection(), ps[0], inputqty))
                    {
                        return false;
                    }

                    // 修正しました。
                    setMessage("6001004");
                }
                else
                {
                    // 削除処理
                    // 在庫情報を削除します。
                    deleteStockData(this.getConnection(), ps[0]);
                    // 削除しました。
                    setMessage("6001005");
                }
            }
            else
            {
                // 6023070=指定された棚は現在引当中です。
                setMessage("6023070");
                return false;
            }

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
     * 在庫用の空パレット用の入力チェックを行います。<BR>
     * 抽象クラスのisCorrectEmptyPBメソッドをオーバーライドしています。<BR>
     * 荷主コード・商品コードともに空パレット用のコードでない場合は、trueを返します。<BR>
     * 入力NGと判断する条件は以下の通り<BR>
     * <BR>
     * 1.荷主コードが空パレ用荷主で、商品コードが空パレ用商品でない場合<BR>
     * 2.商品コードが空パレ用商品で、荷主コードが空パレ用荷主でない場合<BR>
     * 3.在庫ケース数の入力は不可<BR>
     * 4.ケース入数の入力は不可<BR>
     * <BR>
     * @param pItemCode 品名コード<BR>
     * @param pCaseqty ケース数<BR>
     * @param pPieceqty ピース数<BR>
     * @param pCaseEntQty ケース入数<BR>
     * @param pLotNo ロットNo
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
     */
    protected boolean isCorrectEmptyPB(String pItemCode, int pCaseqty, int pPieceqty, int pCaseEntQty, String pLotNo)
    {
        // 入力された商品コードが通常商品コードの場合はtrue
        if (!WmsParam.EMPTYPB_ITEMCODE.equals(pItemCode))
        {
            return true;
        }

        if (pCaseEntQty > 0)
        {
            // 空パレットの場合、ケース入数は指定できません。
            setMessage("6023074");
            return false;
        }

        if (pCaseqty > 0)
        {
            // 空パレットの入庫数に、ケース数は指定できません。ピース数を入力して下さい。
            setMessage("6023073");
            return false;
        }

        if (pPieceqty == 0)
        {
            // 在庫ピース数には1以上の値を入力してください。
            setMessage("6023088");
            return false;
        }

        if (pLotNo.length() != 0)
        {
            // 空パレットの場合、ロットNo.は入力できません。
            setMessage("6023099");
            return false;
        }

        return true;
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
        WareHouse wh = null;

        WareHouseHandler whHandler = new WareHouseHandler(conn);
        WareHouseSearchKey whKey = new WareHouseSearchKey();
        whKey.setStationNo(whStationNo);
        wh = (WareHouse)whHandler.findPrimary(whKey);

        return wh.getMaxMixedpallet();
    }


    /**
     * 入力値のチェックを行います。<BR>
     * チェック結果を返します。 <BR>
     * <CODE>getMessage()</CODE>メソッドを使用して内容を取得することができます。<BR>
     * このメソッドでは以下の処理を行います。 <BR>
     * 1.ケース数が入力されている場合、ケース入数が入力されていること。 <BR>
     *   入庫ケース数または入庫ピース数には1以上の値が入力されていること。 <BR>
     *   </DIR>
     * <BR>
     * @param  enteringqty        ケース入数
     * @param  caseqty            ケース数
     * @param  pieceqty           ピース数
     * @return スケジュール処理が正常した場合はtrue、スケジュール処理が失敗するか、スケジュールできない場合はfalse
     */
    protected boolean stockInputCheck(int enteringqty, int caseqty, int pieceqty)
    {
        // 在庫ケース数と在庫ピース数が0の場合
        if (caseqty == 0 && pieceqty == 0)
        {
            //在庫ケース数または在庫ピース数には1以上の値を入力してください。
            setMessage("6023090");
            return false;
        }
        // ケース入数が0以下
        if (enteringqty <= 0)
        {
            // 在庫ケース数が1以上
            if (caseqty > 0)
            {
                // ケース入数が0の場合、ケース数は入力できません。
                setMessage("6023036");
                return false;
            }
        }

        // オーバーフローチェック
        long inputqty = (long)caseqty * (long)enteringqty + pieceqty;
        if (inputqty > WmsParam.MAX_STOCK_QTY)
        {
            // 6023190=在庫数には在庫上限数{0}以下の値を入力してください。
            setMessage(WmsMessageFormatter.format(6023190, MAX_STOCK_QTY_DISP));
            return false;
        }

        return true;
    }

    /**
     * 棚在庫情報にて、ソフトゾーンの違う商品が存在するかチェックを行います。
     * @param rParam 検索条件を含む<CODE>Parameter</CODE>オブジェクト
     * @return 存在しない場合はTrue、存在した場合はFalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean mixedSoftZoneCheck(ScheduleParams param)
            throws CommonException
    {
        if (WmsParam.SOFTZONE_SELECT_ITEM && WmsParam.MIXED_SOFTZONE)
        {
            // フリーソフトゾーンの場合はチェックしない
            if (isFreeZoneItem(param))
            {
                return true;
            }

            // ソフトゾーンが商品マスタ管理かつソフトゾーン違い混載可の場合
            String item_sz = getItemSoftZone(param.getString(CONSIGNOR_CODE), param.getString(ITEM_CODE));
            String shelf_sz = searchShelf(param.getString(STATION_NO)).getSoftZoneId();

            SoftZonePriorityHandler szpHandler = new SoftZonePriorityHandler(getConnection());
            SoftZonePrioritySearchKey szpKey = new SoftZonePrioritySearchKey();

            // DMSOFTZONEPRIORITY用検索キー
            szpKey.setWhStationNo(param.getString(WH_STATION_NO));
            szpKey.setSoftZoneId(item_sz);
            szpKey.setPrioritySoftZone(shelf_sz);

            if (szpHandler.count(szpKey) <= 0)
            {
                // MSG-W0064=ソフトゾーンが異なる商品を登録します。よろしいですか？
                setDispMessage("MSG-W0064");
                return false;
            }
        }
        else if (!(WmsParam.MIXED_SOFTZONE || StringUtil.isBlank(param.getString(PALLET_ID))))
        {
            // ソフトゾーン違い混載不可の場合
            // 画面で入力した商品コードのソフトゾーンを取得する
            String softZone = getItemSoftZone(param.getString(CONSIGNOR_CODE), param.getString(ITEM_CODE));

            StockSearchKey ssKey = new StockSearchKey();
            ssKey.setAreaNo(param.getString(AREA_NO));
            ssKey.setLocationNo(param.getString(LOCATION_NO));
            ssKey.setCollect(Item.SOFT_ZONE_ID);
            ssKey.setGroup(Item.SOFT_ZONE_ID);
            ssKey.setJoin(Stock.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
            ssKey.setJoin(Stock.ITEM_CODE, Item.ITEM_CODE);

            StockHandler sHandler = new StockHandler(getConnection());

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
        return true;
    }

    /**
     * 棚在庫情報にて、同一商品の有無チェックを行います。
     * @param rParam 検索条件を含む<CODE>Parameter</CODE>オブジェクト
     * @return 同一商品が存在しない場合はTrue、存在した場合はFalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean multiStockCheck(ScheduleParams param)
            throws CommonException
    {
        StockHandler wStockHandler = new StockHandler(this.getConnection());
        StockSearchKey wStockSearchKey = new StockSearchKey();

        // 重複キーにて在庫検索を行います。
        wStockSearchKey.setAreaNo(param.getString(AREA_NO));
        wStockSearchKey.setLocationNo(param.getString(LOCATION_NO));
        wStockSearchKey.setConsignorCode(param.getString(CONSIGNOR_CODE));
        wStockSearchKey.setItemCode(param.getString(ITEM_CODE));
        wStockSearchKey.setLotNo(param.getString(LOT_NO));

        // 登録時はパレットIDを指定できないので、空棚であるパレットが検索されないように副問い合わせを行なう
        if (StringUtil.isBlank(param.getString(PALLET_ID)))
        {
            CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
            CarryInfoSearchKey carryKey = carryControl.getEmptyShelfPallet();
            // 検索条件をセットします。
            wStockSearchKey.setKey(Stock.PALLET_ID, carryKey, "!=", "", "", true);
        }
        // パレットID指定の場合
        else
        {
            wStockSearchKey.setPalletId(param.getString(PALLET_ID));
        }

        if (!StringUtil.isBlank(param.getString(STOCK_ID)))
        {
            wStockSearchKey.setStockId(param.getString(STOCK_ID), "!=");
        }

        if (wStockHandler.count(wStockSearchKey) > 0)
        {
            // 既に同一データが存在するため、入力できません。
            setMessage("6023020");
            return false;
        }
        return true;
    }

    /**
     * 棚在庫情報にて、混載条件チェックを行います。
     * @param rParam 検索条件を含む<CODE>Parameter</CODE>オブジェクト
     * @return 混在可だった場合はTrue、混在不可だった場合はFalseを返します。
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean mixedloadStockCheck(ScheduleParams param)
            throws CommonException
    {
        StockHandler wStockHandler = new StockHandler(this.getConnection());
        StockSearchKey wStockSearchKey = new StockSearchKey();

        // 棚Noにて在庫検索を行います。
        wStockSearchKey.setAreaNo(param.getString(AREA_NO));
        wStockSearchKey.setLocationNo(param.getString(LOCATION_NO));

        // 登録時はパレットIDを指定できないので、空棚であるパレットが検索されないように副問い合わせを行なう 
        if (StringUtil.isBlank(param.getString(PALLET_ID)))
        {
            CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
            CarryInfoSearchKey carryKey = carryControl.getEmptyShelfPallet();
            // 検索条件をセットします。
            wStockSearchKey.setKey(Stock.PALLET_ID, carryKey, "!=", "", "", true);
        }
        // パレットID指定の場合
        else
        {
            wStockSearchKey.setPalletId(param.getString(PALLET_ID));
        }

        Stock[] stk = (Stock[])wStockHandler.find(wStockSearchKey);

        // 在庫情報と入庫データの空パレットチェックを行います。
        if (!checkAddStockEnptyPB(param.getString(ITEM_CODE), stk, false))
        {
            return false;
        }

        return true;
    }

    /**
     * 棚・パレット情報の登録処理を行います。
     * 引数で指定されたパラメータより、該当する棚・パレット情報を登録する。
     * 
     * @param conn データベースとのコネクションを保持するインスタンス。
     * @param rParam 設定内容を持つ<CODE>AsScheduleParameter</CODE>クラスのインスタンス。
     * @param pStockQty 在庫数量
     * @return boolean
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean registStockData(Connection conn, ScheduleParams param, int pStockQty)
            throws CommonException
    {
        // 新しく取得するパレットID
        String pallet = null;

        // 空PBの商品コードを取得します。
        String wEmpItemCode = WmsParam.EMPTYPB_ITEMCODE;

        Stock[] readStock = null;

        // パラメータにパレットIDがセットされてる時は在庫情報を取得する。
        // パラメータにパレットIDがセットされてない時は在庫情報は無い。
        if (!StringUtil.isBlank(param.getString(PALLET_ID)))
        {
            StockHandler wStHandler = new StockHandler(conn);
            StockSearchKey wStSearchKey = new StockSearchKey();

            // 棚No+パレットIDで在庫情報を取得します。（混載又は空PB又は空棚情報を取得します）
            wStSearchKey.setAreaNo(param.getString(AREA_NO));
            wStSearchKey.setLocationNo(param.getString(LOCATION_NO));
            wStSearchKey.setPalletId(param.getString(PALLET_ID));
            readStock = (Stock[])wStHandler.find(wStSearchKey);
        }

        // 選択処理キー
        String processkey = param.getString(PROCESSTYPE_KEY);

        boolean isNeedCreatePallet = false;

        // 今から登録する棚に在庫が１つもない場合は空棚なのでパレット情報を作成する
        if (readStock == null || readStock.length <= 0)
        {
            isNeedCreatePallet = true;

            WMSSequenceHandler sequence = new WMSSequenceHandler(conn);
            pallet = sequence.nextPalletId();
        }
        // 棚に在庫又は、空PBが有る場合
        else
        {
            // 空PB在庫で在庫数が１の場合、空PB在庫の削除を行います。
            if (AsrsInParameter.M_CREATE.equals(processkey) && readStock[0].getStockQty() == 1
                    && readStock[0].getItemCode().equals(wEmpItemCode))
            {

                ScheduleParams delParam = new ScheduleParams();
                delParam.set(STOCK_ID, readStock[0].getStockId());
                //                delParam.setWmsUserInfo(getWmsUserInfo());

                // 在庫情報の削除(在庫とパレットを削除)
                deleteStockData(conn, delParam);

                isNeedCreatePallet = true;
            }
            pallet = readStock[0].getPalletId();
        }

        // 棚状態が空棚の場合、パレット情報の登録を行います。
        if (isNeedCreatePallet)
        {
            Pallet regPallet = new Pallet();
            // パレットID
            regPallet.setPalletId(pallet);
            // 現在ステーションNo
            regPallet.setCurrentStationNo(param.getString(STATION_NO));
            // 倉庫ステーションNo
            regPallet.setWhStationNo(param.getString(WH_STATION_NO));
            // 在庫状態
            regPallet.setStatusFlag(Pallet.PALLET_STATUS_REGULAR);
            // 引当状態
            regPallet.setAllocationFlag(Pallet.ALLOCATION_FLAG_NOT_ALLOCATED);
            // パレット状態
            if (wEmpItemCode.equals(param.getString(ITEM_CODE)) && pStockQty == 1)
            {
                regPallet.setEmptyFlag(Pallet.EMPTY_FLAG_EMPTY);
            }
            else
            {
                regPallet.setEmptyFlag(Pallet.EMPTY_FLAG_NORMAL);
            }
            // パレットの荷高
            regPallet.setHeight(0);
            // パレットのソフトゾーン
            Shelf shelf = searchShelf(param.getString(STATION_NO));
            regPallet.setSoftZoneId(shelf.getSoftZoneId());

            // 処理名
            regPallet.setRegistPname(getClass().getSimpleName());
            // 最終更新処理名
            regPallet.setLastUpdatePname(getClass().getSimpleName());

            PalletHandler wPlHandler = new PalletHandler(conn);
            wPlHandler.create(regPallet);
        }

        if (!AsrsInParameter.M_MODIFY.equals(processkey))
        {
            ShelfHandler wSlHandler = new ShelfHandler(conn);
            ShelfAlterKey wSlAlterKey = new ShelfAlterKey();

            // Shelf情報の更新処理を行います。
            wSlAlterKey.setStationNo(param.getString(STATION_NO));
            wSlAlterKey.updateStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED);

            wSlHandler.modify(wSlAlterKey);
        }

        StockHandler stkh = new StockHandler(this.getConnection());
        StockSearchKey stkKey = new StockSearchKey();

        StockController stockCtr = new StockController(conn, this.getClass());

        // 増減区分
        String incDecType = "";
        // 作業タイプ
        String jobType = "";

        if (AsrsInParameter.M_CREATE.equals(processkey))
        {
            // 入力値の在庫情報エンティティ
            Stock stock = new Stock();
            // エリアNo.
            stock.setAreaNo(param.getString(AREA_NO));
            // 棚No.
            stock.setLocationNo(param.getString(LOCATION_NO));
            // 商品コード
            stock.setItemCode(param.getString(ITEM_CODE));
            // 在庫数
            int stockQty = (param.getInt(STOCK_CASE_QTY) * param.getInt(ENTERING_QTY)) + param.getInt(STOCK_PIECE_QTY);
            stock.setStockQty(stockQty);
            // 引当可能数
            stock.setAllocationQty(stockQty);
            // ロットNo.
            stock.setLotNo(param.getString(LOT_NO));
            // 入庫日時
            Date storageDate = WmsFormatter.toDate(param.getDate(STORAGE_DAY), param.getDate(STORAGE_TIME));
            stock.setStorageDate(storageDate);
            // パレットID
            stock.setPalletId(pallet);
            // 荷主コード
            stock.setConsignorCode(param.getString(CONSIGNOR_CODE));

            // 加算
            incDecType = StockHistory.INC_DEC_TYPE_STOCK_INCREMENT;
            jobType = StockHistory.JOB_TYPE_MAINTENANCE_PLUS;

            stockCtr.insert(stock, incDecType, jobType, getWmsUserInfo(), SystemDefine.DEFAULT_REASON_TYPE);
        }
        else if (AsrsInParameter.M_MODIFY.equals(processkey))
        {
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
            Date storageDate = WmsFormatter.toDate(param.getDate(STORAGE_DAY), param.getDate(STORAGE_TIME));
            newParam.setStorageDate(storageDate);
            // 最終出庫日
            //newParam.setRetrievalDay(param.getRetrievalDay());
            // 在庫数
            int inputqty = param.getInt(STOCK_CASE_QTY) * param.getInt(ENTERING_QTY) + param.getInt(STOCK_PIECE_QTY);
            newParam.setStockQty(inputqty);
            // 出庫可能数
            int allocationQty = readStock[0].getAllocationQty() - (readStock[0].getStockQty() - inputqty);
            newParam.setAllocationQty(allocationQty);
            // 荷主コード
            newParam.setConsignorCode(param.getString(CONSIGNOR_CODE));

            // 在庫数は増減判別
            if (oldStock.getStockQty() > inputqty)
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

            if (WmsParam.EMPTYPB_ITEMCODE.equals(param.getString(ITEM_CODE)) && oldStock.getStockQty() != inputqty)
            {
                PalletAlterKey akey = new PalletAlterKey();

                akey.setPalletId(newParam.getPalletId());

                if (inputqty == 1)
                {
                    akey.updateEmptyFlag(Pallet.EMPTY_FLAG_EMPTY);
                }
                else
                {
                    akey.updateEmptyFlag(Pallet.EMPTY_FLAG_NORMAL);
                }
                // 処理名
                akey.setRegistPname(getClass().getSimpleName());
                // 最終更新処理名
                akey.setLastUpdatePname(getClass().getSimpleName());

                PalletHandler handler = new PalletHandler(conn);

                handler.modify(akey);
            }
        }

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
     * 棚の状態をチェックし、メンテナンス可能かどうか判断します。
     * @param param パラメータ
     * @return メンテナンス可能の場合true、それ以外の場合はfalseを返す
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkShelf(ScheduleParams param)
            throws CommonException
    {
        String station = param.getString(STATION_NO);
        String processType = param.getString(PROCESSTYPE_KEY);

        //ASRS棚情報検索
        Shelf wShelf = searchShelf(station);

        if (wShelf == null)
        {
            //実在する棚No.を入力してください
            setMessage("6023067");
            return false;
        }

        // アクセス不可棚はメンテナンス不可とする。
        if (Shelf.ACCESS_NG_FLAG_NG.equals(wShelf.getAccessNgFlag()))
        {
            // 指定された棚はアクセス不可棚のためメンテナンスできません
            setMessage("6023098");
            return false;
        }

        // 禁止棚チェックを行います。
        if (Shelf.PROHIBITION_FLAG_NG.equals(wShelf.getProhibitionFlag()))
        {
            // 指定された棚は禁止棚に設定されているため、設定できません。
            setMessage("6123274");
            return false;
        }

        // 予約棚のチェックを行います
        if (Shelf.LOCATION_STATUS_FLAG_RESERVATION.equals(wShelf.getStatusFlag()))
        {
            // 指定された棚は予約棚のためメンテナンスできません
            setMessage("6023086");
            return false;
        }

        // 修正・削除の場合
        if (AsrsInParameter.M_MODIFY.equals(processType) || AsrsInParameter.M_DELETE.equals(processType))
        {
            if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(wShelf.getStatusFlag()))
            {
                // 指定された棚には在庫が存在しません
                setMessage("6023002");
                return false;
            }
        }

        Pallet[] wPallet = null;
        // 実棚ならば引当,異常棚チェック
        if (Shelf.LOCATION_STATUS_FLAG_STORAGED.equals(wShelf.getStatusFlag()))
        {
            // ASRSパレット情報検索
            wPallet = searchPallet(station);

            String pltstatus = wPallet[0].getStatusFlag();
            if (Pallet.PALLET_STATUS_STORAGE_PLAN.equals(pltstatus)
                    || Pallet.PALLET_STATUS_RETRIEVAL_PLAN.equals(pltstatus)
                    || Pallet.PALLET_STATUS_RETRIEVAL.equals(pltstatus))
            {
                // 指定された棚は現在引当中です。
                setMessage("6023070");
                return false;
            }
            else if (Pallet.PALLET_STATUS_IRREGULAR.equals(pltstatus))
            {
                //  処理区分が登録、修正の場合、異常棚はメンテナンス不可
                if (AsrsInParameter.M_CREATE.equals(processType) || AsrsInParameter.M_MODIFY.equals(processType))
                {
                    // 指定された棚は異常棚のため設定できません。
                    setMessage("6023071");
                    return false;
                }
            }
        }

        // 画面で入力された棚No.の棚状態を取得します。
        // アクセス可能棚
        if (Shelf.ACCESS_NG_FLAG_OK.equals(wShelf.getAccessNgFlag()))
        {
            // 禁止棚
            if (Shelf.PROHIBITION_FLAG_NG.equals(wShelf.getProhibitionFlag()))
            {
                setMessage("LBL-W0059");
            }
            else
            {
                // 空棚
                if (Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(wShelf.getStatusFlag()))
                {
                    setMessage("LBL-W0061");
                }
                // 実棚
                else if (Shelf.LOCATION_STATUS_FLAG_STORAGED.equals(wShelf.getStatusFlag()))
                {
                    // 実棚でかつ禁止棚のときは禁止棚として表示
                    if (Shelf.PROHIBITION_FLAG_NG.equals(wShelf.getProhibitionFlag()))
                    {
                        setMessage("LBL-W0059");
                    }
                    // 出庫中(作業棚として表示)
                    else if (Pallet.PALLET_STATUS_RETRIEVAL.equals(wPallet[0].getStatusFlag())
                            || Pallet.PALLET_STATUS_RETRIEVAL_PLAN.equals(wPallet[0].getStatusFlag()))
                    {
                        setMessage("LBL-W0237");
                    }
                    else
                    {
                        // 空パレット棚
                        if (Pallet.EMPTY_FLAG_EMPTY.equals(wPallet[0].getEmptyFlag()))
                        {
                            setMessage("LBL-W0456");
                        }
                        else
                        {
                            // 異常棚
                            if (Pallet.PALLET_STATUS_IRREGULAR.equals(wPallet[0].getStatusFlag()))
                            {
                                setMessage("LBL-W0036");
                            }
                            // 実棚
                            else
                            {
                                setMessage("LBL-W0104");
                            }
                        }
                    }
                }
                // 予約棚(作業棚として表示)
                else if (Shelf.LOCATION_STATUS_FLAG_RESERVATION.equals(wShelf.getStatusFlag()))
                {
                    setMessage("LBL-W0237");
                }
            }
        }

        return true;
    }

    /**
     * 在庫情報のパレットIDがメンテナンス対象のパレットか確認します。
     * @param stockId 在庫ID
     * @param palletId パレットID
     * @return メンテナンス対象の場合true、それ以外の場合はfalseを返す
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected boolean checkStock(String stockId, String palletId)
            throws CommonException
    {
        StockHandler wStHandler = new StockHandler(this.getConnection());
        StockSearchKey wStSearchKey = new StockSearchKey();
        Stock readStock = new Stock();

        // 在庫IDにて在庫を取得ます。
        wStSearchKey.setStockId(stockId);

        // 検索結果を取得
        readStock = (Stock)wStHandler.findPrimary(wStSearchKey);
        if (readStock != null && readStock.getPalletId().equals(palletId))
        {
            return true;
        }
        else
        {
            // 指定したデータは棚にありませんでした
            setMessage("6023239");
            return false;
        }
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
        // メンテナンスを行う棚情報を取得する。
        ShelfHandler slfh = new ShelfHandler(getConnection());
        ShelfSearchKey slfKey = new ShelfSearchKey();

        // ShelfのステーションNoをキーにShelfを取得する。
        AreaController areaCon = new AreaController(getConnection(), getClass());
        slfKey.setWhStationNo(areaCon.getWhStationNo(param.getString(AREA_NO)));
        slfKey.setStationNo(param.getString(STATION_NO));
        Shelf mntShelf = (Shelf)slfh.findPrimary(slfKey);
        if (StringUtil.isBlank(mntShelf.getPairStationNo()))
        {
            // ダブルディープ棚ではないので、trueを返す。
            return true;
        }

        // 選択処理キー
        String processkey = param.getString(PROCESSTYPE_KEY);
        if (AsrsInParameter.M_CREATE.equals(processkey))
        {
            // 手前棚に登録
            if (Shelf.BANK_SELECT_NEAR.equals(mntShelf.getSide()))
            {
                if (isLastTemporayLocation(mntShelf))
                {
                    // 6023303=ペアの空棚が無くなるので設定できません。
                    setMessage("6023303");
                    return false;
                }

                // 手前棚に登録
                slfKey.clear();
                slfKey.setWhStationNo(mntShelf.getWhStationNo());
                slfKey.setStationNo(mntShelf.getPairStationNo());
                Shelf pairShelf = (Shelf)slfh.findPrimary(slfKey);
                if (Shelf.ACCESS_NG_FLAG_NG.equals(pairShelf.getAccessNgFlag()))
                {
                    // 奥棚がアクセス不可棚ならば、true を返す。
                    return true;
                }
                if (Shelf.PROHIBITION_FLAG_NG.equals(pairShelf.getProhibitionFlag()))
                {
                    // 奥棚が禁止棚なのでtrueを返す。
                    return true;
                }
                if (Shelf.LOCATION_STATUS_FLAG_STORAGED.equals(pairShelf.getStatusFlag()))
                {
                    // 奥棚のShelfが実棚なので、パレットの状態を確認
                    // 倉庫と棚Noでパレット情報を取得する。
                    Pallet[] plt = searchPallet(pairShelf.getStationNo());
                    if (plt.length != 0)
                    {
                        // 奥棚のShelfが実棚の時、手前棚の在庫が有れば取得する
                        Pallet[] nearPlt = searchPallet(mntShelf.getStationNo());

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
        else if (AsrsInParameter.M_DELETE.equals(processkey))
        {
            // 削除
            if (Shelf.BANK_SELECT_FAR.equals(mntShelf.getSide()))
            {
                //倉庫と棚Noでパレット情報を取得する。
                Pallet[] plt = searchPallet(mntShelf.getStationNo());
                if (plt.length != 0)
                {
                    StockSearchKey stkKey = new StockSearchKey();
                    StockHandler stkh = new StockHandler(getConnection());
                    stkKey.setPalletId(plt[0].getPalletId());
                    Stock[] stk = (Stock[])stkh.find(stkKey);
                    if (stk.length == 1)
                    {
                        // 奥棚の最後の一件を削除する時
                        // ShelfのペアステーションNoをキーに手前棚のShelfを取得する。
                        slfKey.clear();
                        slfKey.setStationNo(mntShelf.getPairStationNo());
                        Shelf pairShelf = (Shelf)slfh.findPrimary(slfKey);

                        if (!Shelf.LOCATION_STATUS_FLAG_EMPTY.equals(pairShelf.getStatusFlag()))
                        {
                            // 6023305=手前が空棚ではないため設定できません。
                            setMessage("6023305");
                            return false;
                        }
                        else
                        {
                            return true;
                        }
                    }
                    else
                    {
                        return true;
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
            else
            {
                // 手前棚を削除
                return true;
            }
        }
        else
        {
            // 変更
            // 変更は奥棚、手前棚に関係なくtrueを返す。
            return true;
        }
    }

    /**
     * 棚No.よりAS/RS棚情報を検索します
     * @param station 入力棚No.
     * @return AS/RS棚情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected Shelf searchShelf(String station)
            throws CommonException
    {
        ShelfHandler wShHandler = new ShelfHandler(this.getConnection());
        ShelfSearchKey wShSearchKey = new ShelfSearchKey();

        // 検索条件をセットします。
        wShSearchKey.setStationNo(station);

        Shelf wShelf = null;

        wShelf = (Shelf)wShHandler.findPrimary(wShSearchKey);

        return wShelf;
    }

    /**
     * 棚No.よりAS/RSパレット情報を検索します
     * @param location 棚No.
     * @return AS/RSパレット情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected Pallet[] searchPallet(String location)
            throws CommonException
    {
        PalletHandler wPlHandler = new PalletHandler(this.getConnection());
        PalletSearchKey wPlSearchKey = new PalletSearchKey();
        CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
        CarryInfoSearchKey wCarrySearchKey = carryControl.getEmptyShelfPallet();

        // 検索条件をセットします。
        wPlSearchKey.setCurrentStationNo(location);
        wPlSearchKey.setKey(Pallet.PALLET_ID, wCarrySearchKey, "!=", "", "", true);

        return (Pallet[])wPlHandler.find(wPlSearchKey);
    }

    /**
     * エリアNo.よりエリア情報を検索します
     * @param areano エリアNo.
     * @return エリア情報
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected Area searchArea(String areano)
            throws CommonException
    {
        AreaHandler wAreaHandler = new AreaHandler(this.getConnection());
        AreaSearchKey wAreaSearchKey = new AreaSearchKey();

        // 検索条件をセットします。
        wAreaSearchKey.setAreaNo(areano);

        return (Area)wAreaHandler.findPrimary(wAreaSearchKey);
    }

    /**
     * 商品マスタよりソフトゾーンIDを返します
     * @param consignor 荷主コード
     * @param item 商品コード
     * @return ソフトゾーンID
     * @throws CommonException 例外が発生した場合に通知されます。
     */
    protected String getItemSoftZone(String consignor, String item)
            throws CommonException
    {
        ItemHandler handler = new ItemHandler(this.getConnection());
        ItemSearchKey skey = new ItemSearchKey();

        // 検索条件をセットします。
        skey.setConsignorCode(consignor);
        skey.setItemCode(item);

        Item ent = (Item)handler.findPrimary(skey);

        if (ent != null)
        {
            return ent.getSoftZoneId();
        }

        // データが無い場合は、フリーを返す。
        return Item.SOFT_ZONE_FREE;
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
