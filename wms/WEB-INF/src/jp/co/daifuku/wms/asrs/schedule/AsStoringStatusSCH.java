// $Id: Sch_ja.java 117 2008-10-06 11:00:54Z admin $
package jp.co.daifuku.wms.asrs.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import static jp.co.daifuku.wms.asrs.schedule.AsStoringStatusSCHParams.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.foundation.common.Params;
import jp.co.daifuku.foundation.common.ScheduleParams;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.CarryInfoController;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.BankSelectHandler;
import jp.co.daifuku.wms.base.dbhandler.BankSelectSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.SoftZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.BankSelect;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.SoftZone;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**
 * AS/RS 格納状況照会のスケジュール処理を行います。
 * 
 * @version $Revision: 1.2 $, $Date: 2009/02/24 02:17:10 $
 * @author  BusiTune 1.0 Generator.
 * @author  Last commit: $Author: ose $
 */
public class AsStoringStatusSCH
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
     */
    public AsStoringStatusSCH(Connection conn, Class parent, Locale locale, DfkUserInfo ui) throws CommonException
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
        // AS/RS倉庫情報ハンドラの生成
        WareHouseHandler wareHouseHandle = new WareHouseHandler(getConnection());
        // AS/RS倉庫情報検索キーの生成     
        WareHouseSearchKey wareHouseKey = new WareHouseSearchKey();
        // AS/RSアイル情報ハンドラの生成
        AisleHandler wAisleHandle = new AisleHandler(getConnection());
        // AS/RSアイル情報検索キーの生成
        AisleSearchKey wAisleKey = new AisleSearchKey();

        // 画面表示用パラメータ
        Params dispData = null;
        List<Params> params = new ArrayList<Params>();
        // 検索パラメータ
        AsStoringStatusSCHParams searchParam = (AsStoringStatusSCHParams)p;
        AsrsInParameter param = new AsrsInParameter(getWmsUserInfo());

        // オープンかクローズか
        boolean isCloseSystem = false;
        // 指定倉庫に存在するアイル一覧
        String[] aisleSTArray = null;
        // 指定アイルに属するバンク一覧
        int[] bankArray = null;
        // 指定バンクに属するハードゾーンID一覧
        int[][] zoneArray = null;

        // エリアNo.
        param.setAreaNo(searchParam.getString(AREA_NO));
        // RMNo.
        param.setRmNo(searchParam.getString(RM_NO));

        String areaNo = param.getAreaNo();
        String aislestno = null;
        if (!WmsParam.ALL_AISLE_NO.equals(param.getRmNo()))
        {
            aislestno = param.getRmNo();
        }

        // 棚検索キー
        ShelfSearchKey shelfKey = new ShelfSearchKey();
        // 倉庫ステーションNoの昇順でソート
        shelfKey.setWhStationNoOrder(true);
        wareHouseKey.setKey(Area.AREA_NO, areaNo);
        wareHouseKey.setJoin(WareHouse.STATION_NO, Area.WHSTATION_NO);
        WareHouse[] wh = (WareHouse[])wareHouseHandle.find(wareHouseKey);
        // 自動倉庫運用種別がクローズ運用だった場合
        if (WareHouse.EMPLOYMENT_TYPE_CLOSE.equals(wh[0].getEmploymentType()))
        {
            isCloseSystem = true;
        }
        // 小計(初期値)
        int subtotalStoragedShelf = 0;
        int subtotalEmptyShelf = 0;
        int subtotalEmptyPlt = 0;
        int subtotalIrregularShelf = 0;
        int subtotalErrorShelf = 0;
        int subtotalNotAccess = 0;
        int subtotalShelf = 0;
        // 合計(初期値)
        int totalStoragedShelf = 0;
        int totalEmptyShelf = 0;
        int totalEmptyPlt = 0;
        int totalIrregularShelf = 0;
        int totalErrorShelf = 0;
        int totalNotAccess = 0;
        int totalShelf = 0;

        // RM No.が指定されていなかった場合
        if (AsrsInParameter.SEARCH_ALL.equals(aislestno))
        {
            // 検索条件
            // 倉庫ステーションNo.
            wAisleKey.clear();
            wAisleKey.setWhStationNo(areaNo);

            // 集約条件
            // ステーションNo.
            wAisleKey.setStationNoGroup();

            // 取得項目
            // ステーションNo.
            wAisleKey.setStationNoCollect();

            // 並び順
            // ステーションNo.
            wAisleKey.setStationNoOrder(true);

            // 検索実行
            Aisle[] aile = (Aisle[])wAisleHandle.find(wAisleKey);
            aisleSTArray = new String[aile.length];
            for (int j = 0; j < aile.length; j++)
            {
                aisleSTArray[j] = aile[j].getStationNo();
            }
        }
        else
        {
            // 検索条件
            // エリアNo.
            wAisleKey.clear();
            wAisleKey.setKey(Area.AREA_NO, areaNo);
            // アイルNo.
            if (!StringUtil.isBlank(aislestno))
            {
                wAisleKey.setAisleNo(aislestno);
            }

            // 集約条件
            // ステーションNo.
            wAisleKey.setStationNoGroup();

            // 結合条件
            // AS/RSアイル情報.倉庫ステーションNO.とエリア情報.倉庫ステーションNo.
            wAisleKey.setJoin(Aisle.WH_STATION_NO, Area.WHSTATION_NO);

            // 取得条件
            // ステーションNo.
            wAisleKey.setStationNoCollect();

            // 並び順
            // ステーションNo.
            wAisleKey.setStationNoOrder(true);

            // 検索実行
            Aisle[] aile = (Aisle[])wAisleHandle.find(wAisleKey);
            aisleSTArray = new String[aile.length];
            for (int j = 0; j < aile.length; j++)
            {
                aisleSTArray[j] = aile[j].getStationNo();
            }
        }

        // 各アイルの各ゾーンごとに該当数を表示するため
        // アイルとゾーンでループします
        for (int i = 0; i < aisleSTArray.length; i++)
        {
            // 今回検索するアイルに属するバンク配列を求める
            // AS/RSバンク情報ハンドラの生成
            BankSelectHandler wBankHandle = new BankSelectHandler(getConnection());
            // AS/RSバンク情報検索キーの生成
            BankSelectSearchKey wBankKey = new BankSelectSearchKey();

            // アイル情報からステーションNo.を取得
            String aisleSTNo = aisleSTArray[i];

            // 検索条件
            // アイルステーションNo.
            wBankKey.setAisleStationNo(aisleSTNo);

            // 並び順
            // バンク
            wBankKey.setBankNoOrder(true);

            // 検索実行
            BankSelect[] bank = (BankSelect[])wBankHandle.find(wBankKey);
            bankArray = new int[bank.length];
            for (int s = 0; s < bank.length; s++)
            {
                bankArray[s] = bank[s].getBankNo();
            }

            // 倉庫を指定してハードゾーンのZONEID一覧を返します
            // AS/RS棚情報ハンドラの生成
            ShelfHandler wShelfHandle = new ShelfHandler(getConnection());
            // AS/RS棚情報検索キーの生成
            ShelfSearchKey wShelfKey = new ShelfSearchKey();

            // 検索条件
            // エリアNo.
            wShelfKey.setKey(Area.AREA_NO, areaNo);

            // 集約条件
            // ハードゾーンID
            wShelfKey.setHardZoneIdGroup();
            // ソフトゾーンID
            wShelfKey.setSoftZoneIdGroup();

            // 結合条件
            // エリア情報.倉庫ステーションNo.とAS/RS棚情報.倉庫ステーションNo.
            wShelfKey.setJoin(Area.WHSTATION_NO, Shelf.WH_STATION_NO);

            // 取得項目
            // ハードゾーンID
            wShelfKey.setHardZoneIdCollect();
            // ソフトゾーンID
            wShelfKey.setSoftZoneIdCollect();

            // 並び順
            // ハードゾーンID
            wShelfKey.setHardZoneIdOrder(true);
            // ソフトゾーンID
            wShelfKey.setSoftZoneIdOrder(true);

            // 検索実行
            Shelf[] shelf = (Shelf[])wShelfHandle.find(wShelfKey);
            zoneArray = new int[shelf.length][2];
            for (int j = 0; j < shelf.length; j++)
            {
                // ハードゾーンIDを保持
                zoneArray[j][0] = Integer.parseInt(shelf[j].getHardZoneId());
                // ソフトゾーンIDを保持
                zoneArray[j][1] = Integer.parseInt(shelf[j].getSoftZoneId());
            }

            // 取得したゾーン件数分繰り返す
            for (int k = 0; k < zoneArray.length; k++)
            {
                // 実棚数
                int storagedShelfQty =
                        getStoragedShelfQty(getConnection(), areaNo, bankArray, zoneArray[k][0], zoneArray[k][1]);
                // 空棚数
                int emptyShelfQty =
                        getEmptyShelfQty(getConnection(), areaNo, bankArray, zoneArray[k][0], zoneArray[k][1]);
                // 空棚数(入庫予約棚)
                int reservationShelfQty =
                        getReservationShelfQty(getConnection(), areaNo, bankArray, zoneArray[k][0], zoneArray[k][1]);
                // 実棚数(合計)
                storagedShelfQty = storagedShelfQty + reservationShelfQty;
                // 空PB数
                int emptyPalletQty =
                        getEmptyPalletQty(getConnection(), areaNo, bankArray, zoneArray[k][0], zoneArray[k][1]);
                // 異常棚数
                int irregularShelfQty =
                        getIrregularShelfQty(getConnection(), areaNo, bankArray, zoneArray[k][0], zoneArray[k][1]);
                // 禁止棚数
                int errorShelfQty =
                        getErrorShelfQty(getConnection(), areaNo, aisleSTNo, zoneArray[k][0], zoneArray[k][1]);
                // アクセス禁止棚数
                int notaccessShelfQty =
                        getNotAccessShelfQty(getConnection(), areaNo, aisleSTNo, zoneArray[k][0], zoneArray[k][1]);
                // 総棚数
                int allShelfQty = getAllShelfQty(getConnection(), areaNo, aisleSTNo, zoneArray[k][0], zoneArray[k][1]);
                // 格納率
                double storagedrate = getRate(allShelfQty, emptyPalletQty, emptyShelfQty, isCloseSystem);

                // 小計を求める
                subtotalStoragedShelf += storagedShelfQty;
                subtotalEmptyShelf += emptyShelfQty;
                subtotalEmptyPlt += emptyPalletQty;
                subtotalIrregularShelf += irregularShelfQty;
                subtotalErrorShelf += errorShelfQty;
                subtotalNotAccess += notaccessShelfQty;
                subtotalShelf += allShelfQty;
                // 合計を求める
                totalStoragedShelf += storagedShelfQty;
                totalEmptyShelf += emptyShelfQty;
                totalEmptyPlt += emptyPalletQty;
                totalIrregularShelf += irregularShelfQty;
                totalErrorShelf += errorShelfQty;
                totalNotAccess += notaccessShelfQty;
                totalShelf += allShelfQty;


                //表示用のデータに検索値をセットする
                dispData = new Params();
                // 「RM」+ AisleNoをセットする
                dispData.set(RM_NO, DisplayText.getText("CMB-W0026") + (getAisleNumber(aisleSTNo)));
                // 荷姿
                dispData.set(LOAD_SIZE, getHardZoneName(zoneArray[k][0]));
                // ソフトゾーン名称
                dispData.set(SOFTZONE_NAME, getSoftZoneName(zoneArray[k][1]));
                // 実棚
                dispData.set(OCCUPIED, storagedShelfQty);
                // 空棚
                dispData.set(EMPTY, emptyShelfQty);
                // 空PB棚
                dispData.set(EMPTY_PALLET, emptyPalletQty);
                // 異常棚
                dispData.set(ERROR, irregularShelfQty);
                // 禁止棚
                dispData.set(RESTRICTED, errorShelfQty);
                // アクセス不可棚
                dispData.set(INACCESSIBLE, notaccessShelfQty);
                // 総棚数
                dispData.set(TOTAL, allShelfQty);
                // 格納率
                dispData.set(OCCUPANCY_RATE, String.valueOf((storagedrate)) + "%");

                // 生成したパラメータを配列に格納
                params.add(dispData);
            }

            //「小計」を挿入する
            dispData = new Params();
            // RM No.
            dispData.set(RM_NO, DisplayText.getText("CMB-W0026") + (getAisleNumber(aisleSTNo)));
            // 荷姿
            dispData.set(LOAD_SIZE, "");
            // ソフトゾーン名称
            dispData.set(SOFTZONE_NAME, DisplayText.getText("LBL-W0516"));
            // 実棚
            dispData.set(OCCUPIED, subtotalStoragedShelf);
            // 空棚
            dispData.set(EMPTY, subtotalEmptyShelf);
            // 空PB棚
            dispData.set(EMPTY_PALLET, subtotalEmptyPlt);
            // 異常棚
            dispData.set(ERROR, subtotalIrregularShelf);
            // 禁止棚
            dispData.set(RESTRICTED, subtotalErrorShelf);
            // アクセス不可棚
            dispData.set(INACCESSIBLE, subtotalNotAccess);
            // 総棚数
            dispData.set(TOTAL, subtotalShelf);
            // 格納率
            dispData.set(OCCUPANCY_RATE, String.valueOf(getRate(subtotalShelf, subtotalEmptyPlt, subtotalEmptyShelf,
                    isCloseSystem))
                    + "%");

            // 生成したパラメータを配列に格納
            params.add(dispData);

            // 小計初期化
            subtotalStoragedShelf = 0;
            subtotalEmptyShelf = 0;
            subtotalEmptyPlt = 0;
            subtotalIrregularShelf = 0;
            subtotalErrorShelf = 0;
            subtotalNotAccess = 0;
            subtotalShelf = 0;
        }

        //最後に「合計」を挿入する
        dispData = new Params();
        // RM No.
        dispData.set(RM_NO, "");
        // 荷姿
        dispData.set(LOAD_SIZE, "");
        // ソフトゾーンID
        dispData.set(SOFTZONE_NAME, DisplayText.getText("LBL-W0429"));
        // 実棚
        dispData.set(OCCUPIED, totalStoragedShelf);
        // 空棚
        dispData.set(EMPTY, totalEmptyShelf);
        // 空PB棚
        dispData.set(EMPTY_PALLET, totalEmptyPlt);
        // 異常棚
        dispData.set(ERROR, totalIrregularShelf);
        // 禁止棚
        dispData.set(RESTRICTED, totalErrorShelf);
        // アクセス不可棚
        dispData.set(INACCESSIBLE, totalNotAccess);
        // 総棚数
        dispData.set(TOTAL, totalShelf);
        // 格納率
        dispData.set(OCCUPANCY_RATE, String.valueOf(getRate(totalShelf, totalEmptyPlt, totalEmptyShelf, isCloseSystem))
                + "%");

        // 生成したパラメータを配列に格納
        params.add(dispData);

        // 6001013 = 表示しました。
        setMessage("6001013");

        // パラメータを返却
        return params;
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
     * 総棚数を返します。<BR>
     * 
     * @param conn データベースとのコネクションオブジェクト
     * @param areano エリアNo.
     * @param aisleSTNo アイルステーションNo.
     * @param hZoneId ハードゾーンID
     * @param sZoneId ソフトゾーンID
     * @return 総棚数を返します。
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected int getAllShelfQty(Connection conn, String areano, String aisleSTNo, int hZoneId, int sZoneId)
            throws CommonException
    {
        // AS/RS棚情報ハンドラの生成
        ShelfHandler wShelfHandle = new ShelfHandler(conn);
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey shelfKey = new ShelfSearchKey();

        // 検索条件
        // 親ステーションNo.
        shelfKey.clear();
        shelfKey.setParentStationNo(aisleSTNo);
        // エリアNo.
        shelfKey.setKey(Area.AREA_NO, areano);
        // ハードゾーンID
        shelfKey.setHardZoneId(String.valueOf(hZoneId));
        // ソフトゾーンID
        shelfKey.setSoftZoneId(String.valueOf(sZoneId));

        // 結合条件
        // AS/RS棚情報.倉庫ステーションNo.とエリア情報.倉庫ステーションNo.
        shelfKey.setJoin(Shelf.WH_STATION_NO, Area.WHSTATION_NO);

        // 検索結果件数を返却
        return wShelfHandle.count(shelfKey);
    }

    /**
     * 実棚の数を返します。<BR>
     * SHELFが実棚、アクセス可、使用可で
     * それに紐付くPALLETが異常または空パレでないものを実棚とします。
     * @param conn データベースとのコネクションオブジェクト
     * @param areano エリアNo.
     * @param bankArray バンクナンバーの配列
     * @param hZoneId ハードゾーンID
     * @param sZoneId ソフトゾーンID
     * @return 実棚の数を返します。
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected int getStoragedShelfQty(Connection conn, String areano, int[] bankArray, int hZoneId, int sZoneId)
            throws CommonException
    {
        // AS/RS棚情報ハンドラの生成
        ShelfHandler wShelfHandle = new ShelfHandler(conn);
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey shelfKey = new ShelfSearchKey();

        // AS/RS搬送情報コントローラの生成
        CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
        // AS/RS搬送情報検索キーの生成
        CarryInfoSearchKey caryShKy = carryControl.getEmptyShelfPallet();

        // 検索条件
        // エリアNo.
        shelfKey.clear();
        shelfKey.setKey(Area.AREA_NO, areano);
        // バンク
        shelfKey.setBankNo(bankArray, true);
        // ハードゾーンID
        shelfKey.setHardZoneId(String.valueOf(hZoneId));
        // ソフトゾーンID
        shelfKey.setSoftZoneId(String.valueOf(sZoneId));
        // 棚情報(実棚)
        shelfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED);
        // アクセス不可棚フラグ(アクセス可)
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
        // 状態(使用可)
        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);
        // AS/RSパレット情報.在庫状態(異常以外)
        shelfKey.setKey(Pallet.STATUS_FLAG, Pallet.PALLET_STATUS_IRREGULAR, "!=", "", "", true);
        // AS/RSパレット情報.空パレット状態(空パレット以外)
        shelfKey.setKey(Pallet.EMPTY_FLAG, Pallet.EMPTY_FLAG_EMPTY, "!=", "", "", true);
        // AS/RSパレット情報.パレットID(空棚ではないパレットID)
        shelfKey.setKey(Pallet.PALLET_ID, caryShKy, "!=", "", "", true);

        // 結合条件
        // AS/RS棚情報.ステーションNo.とAS/RSパレット情報.現在ステーションNo.
        shelfKey.setJoin(Shelf.STATION_NO, Pallet.CURRENT_STATION_NO);
        // AS/RS棚情報.倉庫ステーションNo.とエリア情報.倉庫ステーションNO.
        shelfKey.setJoin(Shelf.WH_STATION_NO, Area.WHSTATION_NO);

        // 検索結果件数を返却
        return wShelfHandle.count(shelfKey);
    }

    /**
     * 空棚の数を返します。<BR>
     * SHELFが空棚または入庫予約棚を空棚とします。
     * 
     * @param conn データベースとのコネクションオブジェクト
     * @param areano エリアNo.
     * @param bankArray バンクナンバーの配列
     * @param hZoneId ハードゾーンID
     * @param sZoneId ソフトゾーンID
     * @return 空棚でアクセス可能棚かつ使用可能棚数を返します。
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected int getEmptyShelfQty(Connection conn, String areano, int[] bankArray, int hZoneId, int sZoneId)
            throws CommonException
    {
        // AS/RS棚情報ハンドラの生成
        ShelfHandler wShelfHandle = new ShelfHandler(conn);
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey shelfKey = new ShelfSearchKey();

        // 検索条件
        // エリアNo.
        shelfKey.clear();
        shelfKey.setKey(Area.AREA_NO, areano);
        // バンク
        shelfKey.setBankNo(bankArray, true);
        // ハードゾーンID
        shelfKey.setHardZoneId(String.valueOf(hZoneId));
        // ソフトゾーンID
        shelfKey.setSoftZoneId(String.valueOf(sZoneId));
        // 棚状態(空棚)
        shelfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_EMPTY);
        // アクセス不可棚フラグ(アクセス可)
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
        // 状態(使用可)
        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);

        // 結合条件
        // AS/RS棚情報.倉庫ステーションNo.とエリア情報.倉庫ステーションNo.        
        shelfKey.setJoin(Shelf.WH_STATION_NO, Area.WHSTATION_NO);

        // 検索結果件数を返却
        return wShelfHandle.count(shelfKey);
    }

    /**
     * 入庫予約棚の数を返します。<BR>
     * SHELFが入庫予約棚を空棚とします。
     * 
     * @param conn データベースとのコネクションオブジェクト
     * @param areano エリアNo.
     * @param bankArray バンクナンバーの配列
     * @param hZoneId ハードゾーンID
     * @param sZoneId ソフトゾーンID
     * @return 空棚でアクセス可能棚かつ使用可能棚数を返します。
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected int getReservationShelfQty(Connection conn, String areano, int[] bankArray, int hZoneId, int sZoneId)
            throws CommonException
    {
        // AS/RS棚情報ハンドラの生成
        ShelfHandler wShelfHandle = new ShelfHandler(conn);
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey shelfKey = new ShelfSearchKey();

        // 検索条件
        // エリアNo.
        shelfKey.clear();
        shelfKey.setKey(Area.AREA_NO, areano);
        // バンク
        shelfKey.setBankNo(bankArray, true);
        // ハードゾーンID
        shelfKey.setHardZoneId(String.valueOf(hZoneId));
        // ソフトゾーンID
        shelfKey.setSoftZoneId(String.valueOf(sZoneId));
        // 棚情報(予約棚)
        shelfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_RESERVATION);
        // アクセス不可棚フラグ(アクセス可)
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
        // 状態(使用可)
        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);

        // 結合条件
        // AS/RS棚情報.倉庫ステーションNo.とエリア情報.倉庫ステーションNo.
        shelfKey.setJoin(Shelf.WH_STATION_NO, Area.WHSTATION_NO);

        // 検索結果件数を返却
        return wShelfHandle.count(shelfKey);
    }

    /**
     * 異常棚の数を返します。<BR>
     * SHELFが実棚、アクセス可能、使用可で、<BR>
     * それに紐付くPalletの在庫状態が異常のものを返します。
     * 
     * @param conn データベースとのコネクションオブジェクト
     * @param areano エリアNo.
     * @param bankArray バンクナンバーの配列
     * @param hZoneId ハードゾーンID
     * @param sZoneId ソフトゾーンID
     * @return 異常棚の数を返します
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected int getIrregularShelfQty(Connection conn, String areano, int[] bankArray, int hZoneId, int sZoneId)
            throws CommonException
    {
        // AS/RS棚情報ハンドラの生成
        ShelfHandler wShelfHandle = new ShelfHandler(conn);
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey shelfKey = new ShelfSearchKey();

        // AS/RS搬送情報コントローラの生成
        CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
        // AS/RS搬送情報検索キーの生成
        CarryInfoSearchKey caryShKy = carryControl.getEmptyShelfPallet();

        // 検索条件
        // エリアNo.
        shelfKey.clear();
        shelfKey.setKey(Area.AREA_NO, areano);
        // バンク
        shelfKey.setBankNo(bankArray, true);
        // ハードゾーンID
        shelfKey.setHardZoneId(String.valueOf(hZoneId));
        // ソフトゾーンID
        shelfKey.setSoftZoneId(String.valueOf(sZoneId));
        // 棚状態(実棚)
        shelfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED);
        // アクセス不可棚フラグ(アクセス可)
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
        // 状態(使用可)
        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);
        // AS/RSパレット情報.在庫状態(異常)
        shelfKey.setKey(Pallet.STATUS_FLAG, Pallet.PALLET_STATUS_IRREGULAR);
        // AS/RSパレット情報.パレットID(空棚ではないパレットID)
        shelfKey.setKey(Pallet.PALLET_ID, caryShKy, "!=", "", "", true);

        // 結合条件
        // AS/RS棚情報.ステーションNo.とAS/RSパレット情報.現在ステーションNo.
        shelfKey.setJoin(Shelf.STATION_NO, Pallet.CURRENT_STATION_NO);
        // AS/RS棚情報.倉庫ステーションNo.とエリア情報.倉庫ステーションNo.
        shelfKey.setJoin(Shelf.WH_STATION_NO, Area.WHSTATION_NO);

        // 検索結果件数を返却
        return wShelfHandle.count(shelfKey);
    }

    /**
     * 空パレットの数を返します。<BR>
     * SHELFが実棚、アクセス可能、使用可で
     * それに紐付くPALLETが空パレット状態のものを返します。
     * 
     * @param conn データベースとのコネクションオブジェクト
     * @param areano エリアNo.
     * @param bankArray バンクナンバーの配列
     * @param hZoneId ハードゾーンID
     * @param sZoneId ソフトゾーンID
     * @return 空パレットの数を返します
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected int getEmptyPalletQty(Connection conn, String areano, int[] bankArray, int hZoneId, int sZoneId)
            throws CommonException
    {
        // AS/RS棚情報ハンドラの生成
        ShelfHandler wShelfHandle = new ShelfHandler(conn);
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey shelfKey = new ShelfSearchKey();

        // AS/RS搬送情報コントローラの生成
        CarryInfoController carryControl = new CarryInfoController(getConnection(), getClass());
        // AS/RS搬送情報検索キーの生成
        CarryInfoSearchKey caryShKy = carryControl.getEmptyShelfPallet();

        // 検索条件
        // エリアNo.
        shelfKey.clear();
        shelfKey.setKey(Area.AREA_NO, areano);
        // バンク
        shelfKey.setBankNo(bankArray, true);
        // ハードゾーンID
        shelfKey.setHardZoneId(String.valueOf(hZoneId));
        // ソフトゾーンID
        shelfKey.setSoftZoneId(String.valueOf(sZoneId));
        // 棚状態(実棚)
        shelfKey.setStatusFlag(Shelf.LOCATION_STATUS_FLAG_STORAGED);
        // アクセス不可棚フラグ(アクセス可)
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
        // 状態(使用可)
        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_OK);
        // AS/RSパレット情報.空パレット状態(空パレット)
        shelfKey.setKey(Pallet.EMPTY_FLAG, Pallet.EMPTY_FLAG_EMPTY);
        // AS/RSパレット情報.パレットID(空棚ではないパレットID)
        shelfKey.setKey(Pallet.PALLET_ID, caryShKy, "!=", "", "", true);

        // 結合条件
        // AS/RS棚情報.ステーションNo.とAS/RSパレット情報.現在ステーションNo.
        shelfKey.setJoin(Shelf.STATION_NO, Pallet.CURRENT_STATION_NO);
        // AS/RS棚情報.倉庫ステーションNo.とエリア情報.倉庫ステーションNo.
        shelfKey.setJoin(Shelf.WH_STATION_NO, Area.WHSTATION_NO);

        // 検索結果件数を返却
        return wShelfHandle.count(shelfKey);
    }

    /**
     * 禁止棚の数を返します。<BR>
     * SHELFの状態が使用不可の棚情報の件数を禁止棚とします。
     * 
     * @param conn データベースとのコネクションオブジェクト
     * @param areano エリアNo.
     * @param aisleSTNo アイルステーションNo.
     * @param hZoneId ハードゾーンID
     * @param sZoneId ソフトゾーンID
     * @return 禁止棚の数を返します。
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected int getErrorShelfQty(Connection conn, String areano, String aisleSTNo, int hZoneId, int sZoneId)
            throws CommonException
    {
        // AS/RS棚情報ハンドラの生成
        ShelfHandler wShelfHandle = new ShelfHandler(conn);
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey shelfKey = new ShelfSearchKey();

        // 検索条件
        // エリアNo.
        shelfKey.clear();
        shelfKey.setKey(Area.AREA_NO, areano);
        // 親ステーションNo.
        shelfKey.setParentStationNo(aisleSTNo);
        // ハードゾーンID
        shelfKey.setHardZoneId(String.valueOf(hZoneId));
        // ソフトゾーンID
        shelfKey.setSoftZoneId(String.valueOf(sZoneId));
        // アクセス不可棚フラグ(アクセス可)
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_OK);
        // 状態(使用不可)
        shelfKey.setProhibitionFlag(Shelf.PROHIBITION_FLAG_NG);

        // 結合条件
        // AS/RS棚情報.倉庫ステーションNo.とエリア情報.倉庫ステーションNo.
        shelfKey.setJoin(Shelf.WH_STATION_NO, Area.WHSTATION_NO);

        // 検索結果件数を返却
        return wShelfHandle.count(shelfKey);
    }

    /**
     * アクセス不可棚の数を返します。<BR>
     * SHELFの状態がアクセス不可の棚情報の件数をアクセス不可棚とします。
     * 
     * @param conn データベースとのコネクションオブジェクト
     * @param areano エリアNo.
     * @param aisleSTNo アイルステーションNo.
     * @param hZoneId ハードゾーンID
     * @param sZoneId ソフトゾーンID
     * @return アクセス不可棚の数を返します。
     * @throws CommonException データベースとの接続で異常が発生した場合に通知されます。
     */
    protected int getNotAccessShelfQty(Connection conn, String areano, String aisleSTNo, int hZoneId, int sZoneId)
            throws CommonException
    {
        // AS/RS棚情報ハンドラの生成
        ShelfHandler wShelfHandle = new ShelfHandler(conn);
        // AS/RS棚情報検索キーの生成
        ShelfSearchKey shelfKey = new ShelfSearchKey();

        // 検索条件
        // エリアNo.
        shelfKey.clear();
        shelfKey.setKey(Area.AREA_NO, areano);
        // 親ステーションNo.
        shelfKey.setParentStationNo(aisleSTNo);
        // ハードゾーンID
        shelfKey.setHardZoneId(String.valueOf(hZoneId));
        // ソフトゾーンID
        shelfKey.setSoftZoneId(String.valueOf(sZoneId));
        // アクセス不可棚フラグ(アクセス不可)
        shelfKey.setAccessNgFlag(Shelf.ACCESS_NG_FLAG_NG);

        // 結合条件
        // AS/RS棚情報.倉庫ステーションNo.とエリア情報.倉庫ステーションNo.
        shelfKey.setJoin(Shelf.WH_STATION_NO, Area.WHSTATION_NO);

        // 検索結果件数を返却
        return wShelfHandle.count(shelfKey);
    }

    /**
     * アイルステーションNoを検索キーにしてアイルNoを返します。
     * @param aisleStationNo アイルステーションNo
     * @return アイルNo
     * @throws CommonException データベースとの接続で発生した例外をそのまま通知します。
     */
    protected String getAisleNumber(String aisleStationNo)
            throws CommonException
    {
        // AS/RSアイル情報ハンドラの生成
        AisleHandler aisleHandle = new AisleHandler(getConnection());
        // AS/RSアイル情報検索キーの生成
        AisleSearchKey aisleKey = new AisleSearchKey();

        // 検索条件
        // ステーションNo.
        aisleKey.setStationNo(aisleStationNo);

        // 検索実行
        Aisle[] aisle = (Aisle[])aisleHandle.find(aisleKey);
        if (aisle.length != 0)
        {
            // 存在すればアイルNo.を返却
            return aisle[0].getAisleNo();
        }

        // 存在しなければnullを返却
        return null;
    }


    /**
     * HardZoneIdを検索キーにしてハードゾーン名称を返します。<BR>
     * ハードゾーン名称が定義されていない場合は0バイトの文字列を返します。<BR>
     * @param  clsid ハードゾーンID
     * @return  ハードゾーン名称
     * @throws CommonException データベースとの接続で発生した例外をそのまま通知します。
     */

    protected String getHardZoneName(int clsid)
            throws CommonException
    {
        // AS/RSハードゾーン情報ハンドラの生成
        HardZoneHandler hh = new HardZoneHandler(getConnection());
        // AS/RSハードゾーン情報検索キーの生成
        HardZoneSearchKey hk = new HardZoneSearchKey();

        // 検索条件
        // ハードゾーンID
        hk.setHardZoneId(String.valueOf(clsid));

        // 検索実行
        HardZone[] zone = (HardZone[])hh.find(hk);
        if (zone.length != 0)
        {
            // 存在すればハードゾーン名称を返却
            return zone[0].getHardZoneName();
        }

        // 存在しなければ空文字を返却
        return "";
    }

    /**
     * SoftZoneIdを検索キーにしてソフトゾーン名称を返します。<BR>
     * ソフトゾーン名称が定義されていない場合は0バイトの文字列を返します。<BR>
     * @param  clsid ソフトゾーンID
     * @return  ソフトゾーン名称
     * @throws CommonException データベースとの接続で発生した例外をそのまま通知します。
     */

    protected String getSoftZoneName(int clsid)
            throws CommonException
    {
        // AS/RSソフトゾーン情報ハンドラの生成
        SoftZoneHandler hh = new SoftZoneHandler(getConnection());
        // AS/RSソフトゾーン情報検索キーの生成
        SoftZoneSearchKey hk = new SoftZoneSearchKey();

        // 検索条件
        // ソフトゾーンID
        hk.setSoftZoneId(String.valueOf(clsid));

        // 検索実行
        SoftZone[] zone = (SoftZone[])hh.find(hk);
        if (zone.length != 0)
        {
            // 存在すればソフトゾーン名称を返却
            return zone[0].getSoftZoneName();
        }

        // 存在しなければ空文字を返却
        return "";
    }

    /**
     * 与えられた引数から格納率を返します。<BR>
     * ハードゾーン名称が定義されていない場合は0バイトの文字列を返します。<BR>
     * @param  shelf 棚数 
     * @param  emptyPlt 空パレット数
     * @param  emptyShelf 空棚数
     * @param  closeSystem 自動倉庫運用種別 true時はクローズシステム false時はオープンシステム
     * @return  格納率
     */
    protected double getRate(int shelf, int emptyPlt, int emptyShelf, boolean closeSystem)
    {
        // 返却用変数を生成
        double rate = 0;

        // 棚がある場合
        if (shelf > 0)
        {
            // クローズシステム
            if (closeSystem)
            {
                // ((総棚数 - (空棚数 + 空PB棚)) / 総棚数)
                rate = (double)((shelf - (emptyShelf + emptyPlt)) * 100) / (double)shelf;
            }
            else
            {
                // ((総棚数 - 空棚数) / 総棚数)
                rate = (double)((shelf - emptyShelf) * 100) / (double)shelf;
            }

            // 端数を除く
            rate = java.lang.Math.round(rate * 10.0) / 10.0;
        }

        // 取得した格納率を返却
        return rate;
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
