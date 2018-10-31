// $Id: AsrsOperator.java 8015 2011-10-25 04:23:39Z ota $
package jp.co.daifuku.wms.asrs.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.asrs.schedule.AsrsOutParameter;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.LocationNumber;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.AsStockController;
import jp.co.daifuku.wms.base.controller.AsWorkInfoController;
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.MoveWorkInfoController;
import jp.co.daifuku.wms.base.controller.RetrievalPlanController;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.StoragePlanController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.controller.WorkInfoController;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.HardZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.HardZoneSearchKey;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReStoringPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkListHandler;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.OperationDisplay;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.ReStoringPlan;
import jp.co.daifuku.wms.base.entity.Reason;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.entity.WorkList;
import jp.co.daifuku.wms.base.exception.DuplicateOperatorException;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.exception.RouteException;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.DefaultDDBHandler;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;

/**
 * AS/RS作業を行うためのオペレータクラスです。
 *
 * @version $Revision: 8015 $, $Date: 2011-10-25 13:23:39 +0900 (火, 25 10 2011) $
 * @author  ssuzuki@SOFTECS
 * @author  Last commit: $Author: ota $
 */
public class AsrsOperator
        extends AbstractOperator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    private static final int FINDER_READ_SIZE = 100;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    // Handlers , Controllers
    private WMSSequenceHandler _wmsSeqHandler;

    private ConsignorController _consCtlr;

    private ItemController _itemCtlr;

    private AsWorkInfoController _workInfoCtlr;

    private WarenaviSystemController _wmsysCtlr;

    private AsStockController _stockCtlr;

    private StoragePlanController _storagePlanCtlr;

    private RouteController _routeCtlr;

    private AreaController _areaCtlr;

    // maps for cache
    private HashMap<String, HardZone> _zoneMap;

    private HashMap<String, Station> _stationMap;

    // palletID保持用
    private String _savePLId = "";
    // carryKey保持用
    private String _carryKey = "";
    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと呼び出し元クラスを指定して
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public AsrsOperator(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * RFT積付完了処理を行います。<br>
     *
     * @param param 入力パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>
     * </ol>
     *
     * @return 設定単位キー
     *
     * @throws ReadWriteException FIXME DESIGN PENDING
     * @throws LockTimeOutException FIXME DESIGN PENDING
     * @throws NotFoundException FIXME DESIGN PENDING
     * @throws InvalidDefineException FIXME DESIGN PENDING
     * @throws OperatorException FIXME DESIGN PENDING
     */
    public AsrsOutParameter rftCompleteStorage(AsrsInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                InvalidDefineException,
                OperatorException
    {
        // FIXME DESIGN PENDING
        return null;
    }

    /**
     * RFT入庫作業開始処理を行います。<br>
     *
     * @param param 入力パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>
     * </ol>
     *
     * @return 設定単位キー
     * @throws ReadWriteException FIXME DESIGN PENDING
     * @throws LockTimeOutException FIXME DESIGN PENDING
     * @throws NotFoundException FIXME DESIGN PENDING
     * @throws InvalidDefineException FIXME DESIGN PENDING
     * @throws OperatorException FIXME DESIGN PENDING
     */
    public AsrsOutParameter rftStartStorage(AsrsInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NotFoundException,
                InvalidDefineException,
                OperatorException
    {
        // FIXME DESIGN PENDING.
        return null;
    }

    /**
     * WEB画面 積増入庫作業開始処理を行います。<br>
     * パラメータの項目に該当するデータに対して出庫開始処理を行い、設定単位キーを返します。<br>
     *
     * <!--
     * 以下順で処理を行う。入庫予約メソッド及び作業情報作成メソッドはパラメータの配列インデックス分ループし、処理を行う。
     * ・パレットロックメソッド（StockController）
     * ・パレット情報の検索
     * ・搬送先ステーションの取得
     * ・出庫棚決定メソッド（RouteController）
     * ・積増入庫予約メソッド（StockController）：同一在庫あり時
     * ・新規入庫予約メソッド（StockController）：同一在庫なし時
     * ・作業情報作成メソッド
     * ・AS/RS出庫情報作成メソッド
     * -->
     *
     * @param params パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>エリアNo.
     * <li>棚No.
     * <li>入庫数
     * <li>ロットNo.
     * <li>荷主コード
     * <li>商品コード
     * <li>ステーションNo. (ステーションまたは作業場)
     * <br><br>
     * 以下はマスタパッケージが導入されていないとき参照されます
     * <li>商品名称
     * <li>JANコード
     * <li>ケース入数
     * <li>ボール入数
     * <li>ケースITF
     * <li>ボールITF
     * </ol>
     *
     * @return 設定単位キー,搬送先ステーションNo.
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException システム定義が正しくない場合にスローされます。
     * @throws NoPrimaryException 対象パレット,ステーションが複数存在するときスローされます。
     * @throws InvalidDefineException パラメータがnullまたは空であるときスローされます。
     * @throws OperatorException 他の端末で設定済みなど処理が続行できないときスローされます。
     * @throws RouteException 搬送ルートが確保できないときスローされます。
     * @throws DataExistsException 作業情報登録済み
     * @throws LockTimeOutException 在庫,パレット,棚情報のロックタイムアウト
     * @throws NotFoundException 他端末で更新済み
     */
    public AsrsOutParameter webStartAddStorage(AsrsInParameter[] params)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                RouteException,
                DataExistsException,
                LockTimeOutException,
                NotFoundException
    {
        if (ArrayUtil.isEmpty(params))
        {
            throw new InvalidDefineException("parameter is null or zero lengh");
        }

        // 採番用ハンドラ
        WMSSequenceHandler seqHandler = getSeqHandler();

        // prepare controllers
        RouteController routeCtlr = getRouteCtlr();
        AreaController areaCtlr = getAreaCtlr();
        AsStockController stockCtlr = getStockCtlr();

        // get values from parameter
        AsrsInParameter firstParam = params[0];

        String areaNo = firstParam.getAreaNo();

        String location = firstParam.getLocation();

        // lock pallet
        String asLocation = areaCtlr.toAsrsLocation(areaNo, location);
        Stock[] tStocks = stockCtlr.lockPallet(asLocation, firstParam.getPalletId());
        if (ArrayUtil.isEmpty(tStocks))
        {
            // no target stock found.
            // 他端末で更新済み
            throw new NotFoundException();
        }

        // find pallet info
        String palletId = tStocks[0].getPalletId();
        Pallet retrPallet = getPallet(palletId, false);
        if (null != retrPallet)
        {
            // 実棚のパレットならば引き当て可能
            String pStat = retrPallet.getStatusFlag();
            if (!SystemDefine.PALLET_STATUS_REGULAR.equals(pStat))
            {
                // 他端末で更新済み
                throw new NotFoundException();
            }
        }
        else
        {
            throw new ScheduleException("pallet info not found of:" + palletId);
        }

        // check station
        // 自動振り分けステーションチェック
        Station wkStation = getDestStation(firstParam);

        // 出庫棚決定
        boolean retrDet = routeCtlr.retrievalDetermin(retrPallet, wkStation, true, true, false);
        if (!retrDet)
        {
            int stat = routeCtlr.getRouteStatus();
            throw new RouteException(stat);
        }

        // 設定単位キーの採番
        String settingUnitKey = seqHandler.nextWorkInfoSetUkey();
        // スケジュールNo
        String schNo = seqHandler.nextScheduleNo();
        // 作業No
        String workNo = seqHandler.nextStorageWorkNo();
        // 搬送キーの採番
        String carryKey = seqHandler.nextCarryKey();

        // setup work info
        WorkInfo updateWorkInfo = new WorkInfo();
        updateWorkInfo.setSettingUnitKey(settingUnitKey); // 設定単位キー
        updateWorkInfo.setJobType(SystemDefine.JOB_TYPE_NOPLAN_STORAGE);
        updateWorkInfo.setSystemConnKey(carryKey); // システム接続キー
        updateWorkInfo.setPlanAreaNo(areaNo);
        updateWorkInfo.setPlanLocationNo(location);

        updateWorkInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS); // ハードウェア区分(ASRS)

        int numMix = tStocks.length; // current number of mixed
        int maxNumMix = areaCtlr.getNumMixedOfWarehouse(areaNo); // max mixed for the warehouse
        for (int i = 0; i < params.length; i++)
        {
            try
            {
                // 集約作業No.
                String wicJobNo = seqHandler.nextWorkInfoCollectJobNo();
                updateWorkInfo.setCollectJobNo(wicJobNo);

                // 積み増しチェック
                AsrsInParameter iparam = params[i];

                Stock addTargetStock = null;
                for (Stock ckstock : tStocks)
                {
                    if (isSameKey(ckstock, iparam))
                    {
                        // find same key stock, add to this stock
                        addTargetStock = ckstock;
                        break;
                    }
                }

                String stockId = "";
                if (null == addTargetStock)
                {
                    // new item to the pallet
                    // 最大混載数確認
                    if (++numMix > maxNumMix)
                    {
                        // 他端末で更新済み
                        throw updatedOpException(i);
                    }

                    // new item for the pallet
                    Stock newStock = new Stock();

                    areaNo = iparam.getAreaNo();

                    newStock.setAreaNo(areaNo);
                    newStock.setLocationNo(iparam.getLocation());
                    newStock.setConsignorCode(iparam.getConsignorCode());
                    newStock.setItemCode(iparam.getItemCode());
                    newStock.setLotNo(iparam.getLotNo());
                    newStock.setPlanQty(iparam.getStorageQty());
                    newStock.setPalletId(palletId);

                    stockId = stockCtlr.initStorageReserve(newStock, iparam.getReasonType());
                }
                else
                {
                    // add to the stock on the pallet
                    Stock newStock = new Stock();
                    newStock.setPlanQty(iparam.getStorageQty());
                    stockId = stockCtlr.addStorageReserve(addTargetStock, newStock);
                }

                // auto update/regist to master if NO MASTER PACKAGE INSTALLED
                autoCreateMaster(iparam);

                // 作業情報作成
                updateWorkInfo.setConsignorCode(iparam.getConsignorCode());
                updateWorkInfo.setItemCode(iparam.getItemCode());
                updateWorkInfo.setStockId(stockId);
                updateWorkInfo.setPlanLotNo(iparam.getLotNo());
                updateWorkInfo.setPlanQty(iparam.getStorageQty());

                updateWorkInfo.setReasonType(iparam.getReasonType());

                createWorkInfo(iparam, updateWorkInfo);
            }
            catch (OperatorException e)
            {
                if (OperatorException.ERR_OVERFLOW.equals(e.getErrorCode()))
                {
                    e.setErrorLineNo(params[i].getRowNo());
                    throw e;
                }
                throw e;
            }
            catch (DataExistsException e)
            {
                // 他端末で更新済み
                throw updatedOpException(i);
            }
            catch (NotFoundException e)
            {
                // 他端末で更新済み
                throw updatedOpException(i);
            }
        } // end for loop of parameter

        // AS/RS入庫情報作成
        CarryInfo carry = new CarryInfo();

        carry.setCarryKey(carryKey);
        carry.setPalletId(palletId);
        carry.setWorkType(SystemDefine.WORK_TYPE_ADD_STORAGE);
        carry.setRetrievalStationNo(asLocation);
        carry.setWorkNo(workNo);
        carry.setSourceStationNo(asLocation);
        carry.setDestStationNo(routeCtlr.getDestStation().getStationNo());
        carry.setScheduleNo(schNo);

        // AS/RS出庫情報作成メソッド
        createAsrsRetrievalData(retrPallet, carry);

        // 出力パラメータに設定単位キーを追加
        AsrsOutParameter retParam = new AsrsOutParameter();
        retParam.setSettingUnitKey(settingUnitKey);
        // 搬送先ステーションNo.
        retParam.setDestStationNo(routeCtlr.getDestStation().getStationNo());

        return retParam;
    }

    /**
     * WEB画面 AS/RS 補充開始設定開始処理を行います。<br>
     *
     * @param param 補充対象の設定キーを保有するAsrsInParameter
     * @return 検索結果
     * @throws ReadWriteException データベースエラー
     * @throws NoPrimaryException  対象ステーションまたはパレットが複数見つかった
     * @throws LockTimeOutException ロックするときにタイムアウトが発生した
     * @throws OperatorException 他の端末で設定済みなど処理が続行できない
     * @throws NotFoundException 他端末で更新済み
     * @throws ScheduleException システム定義が正しくない
     * @throws RouteException 搬送ルートが確保できないとき
     * @throws DataExistsException 作業情報登録済み
     * @throws InvalidDefineException パラメータがnullまたは空であるとき
     */
    public AsrsOutParameter webStartReplenish(AsrsInParameter param)
            throws ReadWriteException,
                NoPrimaryException,
                LockTimeOutException,
                OperatorException,
                NotFoundException,
                ScheduleException,
                RouteException,
                DataExistsException,
                InvalidDefineException
    {
        // 対象データをロックする
        AsWorkInfoController asCtrl = getAsWorkInfoCtlr();
        WorkInfo[] works = asCtrl.lockReplenishStart(param.getSettingUnitKey());

        // １件も対象データがなかった場合
        if (ArrayUtil.isEmpty(works))
        {
            try
            {
                MoveWorkInfoController mWrkCtlr = new MoveWorkInfoController(getConnection(), getCaller());
                mWrkCtlr.checkReplenishStart(param.getSettingUnitKey());
            }
            catch (OperatorException e)
            {
                // 「ERR_NOT_START_EXISTS」だった場合は、「ERR_ALREADY_UPDATED」にメッセージを置き換え
                if (OperatorException.ERR_NOT_START_EXISTS.equals(e.getErrorCode()))
                {
                    e.setErrorCode(OperatorException.ERR_ALREADY_UPDATED);
                }
                throw e;
            }
        }

        return startReplenish(param, works);
    }


    /**
     * パレットIDからパレットを検索して返します。
     *
     * @param palletId 検索対象パレットID
     * @param withLock 対象パレット情報をロックするときはtrue.
     * @return パレット
     * @throws ReadWriteException データベースエラー
     * @throws NoPrimaryException パレットIDに対応するパレット情報が複数検索された
     * @throws LockTimeOutException ロックするときにタイムアウトが発生した
     */
    protected Pallet getPallet(String palletId, boolean withLock)
            throws ReadWriteException,
                NoPrimaryException,
                LockTimeOutException
    {
        PalletSearchKey pskey = new PalletSearchKey();
        pskey.setPalletId(palletId);

        PalletHandler plH = new PalletHandler(getConnection());
        Pallet retrPallet;
        retrPallet = (withLock) ? (Pallet)plH.findPrimaryForUpdate(pskey)
                               : (Pallet)plH.findPrimary(pskey);
        return retrPallet;
    }

    /**
     * WEB画面在庫確認作業開始処理を行います。<br>
     * パラメータの項目に該当するデータに対して出庫開始処理を行い、設定単位キーを返します。
     * <!--
     * 在庫確認開始設定で使用。
     * パレット情報のロック～作業情報作成メソッドはパラメータの配列インデックス分ループし、以下順で処理を行う。
     * ・搬送先ステーションの取得
     * ・在庫確認情報作成メソッド
     * ・在庫情報の検索
     * ・パレット情報のロック
     * ・出庫棚決定メソッド（RouteController）
     * ・AS/RS出庫情報作成メソッド
     * ・作業情報作成メソッド
     * -->
     * @param param パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>ステーションNo. (ステーションまたは作業場)
     * <li>エリアNo.
     * <li>荷主コード
     * <li>棚No.
     * <li>TO 棚No.
     * <li>商品コード
     * <li>TO 商品コード
     * <li>ユーザID
     * <li>端末No.
     * </ol>
     *
     * @return 設定単位キー
     *
     * @throws ReadWriteException データベースアクセスエラー
     * @throws LockTimeOutException パレット情報のロックタイムアウト
     * @throws NoPrimaryException パレット,対象ステーションが一意に決定できないとき
     * @throws InvalidDefineException パラメータが不正
     * @throws OperatorException 設定が続行できない
     * @throws RouteException 搬送ルートなし
     * @throws ScheduleException システム定義異常
     * @throws NotFoundException 更新対象データが見つからない
     * @throws DataExistsException 作業,搬送情報がすでに登録済み
     */
    public AsrsOutParameter webStartInventory(AsrsInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                RouteException,
                ScheduleException,
                NotFoundException,
                DataExistsException
    {
        if (null == param)
        {
            throw new InvalidDefineException("parameter is null");
        }

        Station tStation = getStation(param.getStationNo());
        if (null == tStation)
        {
            throw new ScheduleException("No station found:" + param.getStationNo());
        }
        String areaNo = param.getAreaNo();

        // 棚番号をAS/RS棚に変換する
        LocationNumber fromLoc = null;
        LocationNumber toLoc = null;
        AreaController areaCtlr = new AreaController(getConnection(), getCaller());
        if (!StringUtil.isBlank(param.getLocation()))
        {
            fromLoc = new LocationNumber(areaCtlr.getLocationStyle(areaNo));
            fromLoc.parseParam(param.getLocation());
        }
        if (!StringUtil.isBlank(param.getToLocation()))
        {
            toLoc = new LocationNumber(areaCtlr.getLocationStyle(areaNo));
            toLoc.parseParam(param.getToLocation());
        }

        String[] aisleArray = null;
        // 設定したステーションがアイル結合ステーションならば、棚の範囲からアイルを取得します。
        ShelfHandler slfh = new ShelfHandler(getConnection());
        ShelfSearchKey slfKey = new ShelfSearchKey();
        slfKey.setParentStationNoCollect();
        if (fromLoc != null)
        {
            String location[] = fromLoc.getLocation();
            slfKey.setBankNo(Integer.parseInt(location[LocationNumber.IDX_BANK]), ">=");
            slfKey.setBayNo(Integer.parseInt(location[LocationNumber.IDX_BAY]), ">=");
            slfKey.setLevelNo(Integer.parseInt(location[LocationNumber.IDX_LEVEL]), ">=");
        }
        if (toLoc != null)
        {
            String location[] = toLoc.getLocation();
            slfKey.setBankNo(Integer.parseInt(location[LocationNumber.IDX_BANK]), "<=");
            slfKey.setBayNo(Integer.parseInt(location[LocationNumber.IDX_BAY]), "<=");
            slfKey.setLevelNo(Integer.parseInt(location[LocationNumber.IDX_LEVEL]), "<=");
        }

        slfKey.setWhStationNo(areaCtlr.getWhStationNo(param.getAreaNo()));
        slfKey.setParentStationNoGroup();
        slfKey.setParentStationNoOrder(true);
        Shelf[] slfs = (Shelf[])slfh.find(slfKey);
        aisleArray = new String[slfs.length];
        for (int i = 0; i < slfs.length; i++)
        {
            aisleArray[i] = slfs[i].getParentStationNo();
        }

        // handlers
        WMSSequenceHandler seqHandler = getSeqHandler();

        // 設定単位キーの採番
        String settingUnitKey = seqHandler.nextWorkInfoSetUkey();

        // スケジュールNo
        String schNo = seqHandler.nextScheduleNo();

        // fix destination station
        // create dest station
        Station wkStation = getDestStation(param);

        // create a record to inventory check
        createInventoryCheck(param, schNo);

        // open as forward only (need to call close() on finally block)
        StockFinder stFinder = new StockFinder(getConnection());
        stFinder.open(true);
        // ダイレクトDBハンドラ
        DefaultDDBFinder ddFinder = new DefaultDDBFinder(getConnection(), new Stock());
        ddFinder.open(true);
        try
        {
            boolean inventCount = false;

            // アイル単位に在庫確認データを作成します。
            String whStNo = getWhStationNo(param);
            AisleHandler aisleh = new AisleHandler(getConnection());
            AisleSearchKey aisleKey = new AisleSearchKey();
            for (String aisleNo : aisleArray)
            {
                aisleKey.clear();
                aisleKey.setWhStationNo(whStNo);
                aisleKey.setStationNo(aisleNo);
                Aisle aisle = (Aisle)aisleh.findPrimary(aisleKey);
                if (!Aisle.AISLE_STATUS_NORMAL.equals(aisle.getStatus()))
                {
                    StockHandler stHand = new StockHandler(getConnection());
                    StockSearchKey stockskey = createInventoryKey(param, aisleNo, fromLoc, toLoc);

                    if (stHand.count(stockskey) == 0)
                    {
                        // no target stock found
                        continue;
                    }

                    // アイルの状態が正常以外、このアイルの在庫確認をパスする。
                    if (Aisle.AISLE_STATUS_DISCONNECTED.equals(aisle.getStatus()))
                    {
                        // アイル切り離し
                        throw new RouteException(RouteController.OFFLINE);
                    }
                    else
                    {
                        // アイル設備異常
                        throw new RouteException(RouteController.FAIL);
                    }
                }

                if (Aisle.DOUBLE_DEEP_KIND_SINGLE.equals(aisle.getDoubleDeepKind()))
                {

                    // シングル
                    StockSearchKey stockskey = createInventoryKey(param, aisleNo, fromLoc, toLoc);

                    int numStocks = stFinder.search(stockskey);
                    if (0 == numStocks)
                    {
                        // no target stock found
                        continue;
                    }
                    while (stFinder.hasNext())
                    {
                        // get entities (read ahead)
                        Stock[] targetStocks = (Stock[])stFinder.getEntities(FINDER_READ_SIZE);

                        if (createInventryInfo(targetStocks, wkStation, param, settingUnitKey, schNo))
                        {
                            inventCount = true;
                        }
                    } // end while loop of Stock Finder
                }
                else
                {
                    // ダブルディープ
                    String stockSql = createInventorySql(param, aisleNo, fromLoc, toLoc);

                    ddFinder.search(stockSql);

                    while (ddFinder.hasNext())
                    {
                        // get entities (read ahead)
                        Stock[] targetStocks = (Stock[])ddFinder.getEntities(FINDER_READ_SIZE);

                        if (createInventryInfo(targetStocks, wkStation, param, settingUnitKey, schNo))
                        {
                            inventCount = true;
                        }
                    } // end while loop of Stock Finder
                }
            }

            if (!inventCount)
            {
                // 在庫確認の該当データが無かった。
                return null;
            }

            // 出力パラメータに設定単位キーを追加
            AsrsOutParameter retParam = new AsrsOutParameter();
            retParam.setSettingUnitKey(settingUnitKey);

            return retParam;
        }
        catch (DataExistsException e)
        {
            // 他端末で更新済み
            throw new NotFoundException();
        }
        finally
        {
            stFinder.close();
            ddFinder.close();

        }
    }


    /**
     * WEB画面予定外出庫作業開始処理を行います。<br>
     * パラメータの項目に該当するデータに対して出庫開始処理を行い、設定単位キーを返します。
     * <!--
     * パレットロックメソッド～作業情報作成メソッドはパラメータの配列インデックス分ループし、以下順で処理を行う。
     * ・搬送先ステーションの取得
     * ・パレットロックメソッド（StockController）
     * ・パレット情報の検索
     * ・出庫棚決定メソッド（RouteController）
     * ・出庫予約メソッド（StockController）
     * ・AS/RS出庫情報作成メソッド
     * ・作業情報作成メソッド
     * ・搬送情報確定メソッド
     * -->
     * @param params パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>ステーションNo. (ステーションまたは作業場)
     * <li>エリアNo.
     * <li>棚No.
     * <li>出庫数
     * <li>荷主コード
     * <li>商品コード
     * <li>ロットNo.
     * <li>ユーザーID
     * <li>端末No.
     * </ol>
     *
     * @return 設定単位キー
     *
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NoPrimaryException ステーションまたはパレットが複数見つかった
     * @throws InvalidDefineException パラメータが不正
     * @throws OperatorException 他端末で更新された, ルートが見つからない
     * @throws ScheduleException システム定義異常
     * @throws LockTimeOutException 在庫,パレット,棚情報のロックタイムアウト
     * @throws DataExistsException 作業リスト情報登録済み
     */
    public AsrsOutParameter webStartNoPlanRetrieval(AsrsInParameter[] params)
            throws ReadWriteException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                ScheduleException,
                LockTimeOutException,
                DataExistsException
    {
        if (ArrayUtil.isEmpty(params))
        {
            throw new InvalidDefineException("parameter is null or zero lengh");
        }

        // 採番用ハンドラ
        WMSSequenceHandler seqHandler = getSeqHandler();

        // prepare controllers
        RouteController routeCtlr = getRouteCtlr();
        AreaController areaCtlr = getAreaCtlr();
        AsStockController stockCtlr = getStockCtlr();

        String currWorkDay = getWSysCtlr().getWorkDay();

        // 設定単位キーの採番
        String settingUnitKey = seqHandler.nextWorkInfoSetUkey();

        // スケジュールNo
        String schNo = seqHandler.nextScheduleNo();

        // get values from parameter
        AsrsInParameter firstParam = params[0];

        // create dest station
        Station wkStation = getStation(firstParam.getStationNo(), firstParam.getAreaNo());

        HashSet<String> compLoc = new HashSet<String>();
        for (int i = 0; i < params.length; i++)
        {
            try
            {
                Stock targetStock = null;

                AsrsInParameter param = params[i];
                // get location for AS/RS
                String areaNo = param.getAreaNo();

                String loc = param.getLocation();
                String asRetrLocation = areaCtlr.toAsrsLocation(areaNo, loc);
                String palletId = param.getPalletId();

                // lock pallet for the location
                Stock[] tStocks = stockCtlr.lockPallet(asRetrLocation, palletId);
                if (ArrayUtil.isEmpty(tStocks))
                {
                    // 他端末で更新済み
                    throw updatedOpException(i);
                }

                // find pallet information
                Pallet retrPallet = getPallet(palletId, false);

                if (null == retrPallet)
                {
                    // 他端末で更新済み
                    throw updatedOpException(i);
                }
                // 引当状態のものは出庫不可
                if (Pallet.PALLET_STATUS_REGULAR.equals(retrPallet.getStatusFlag())
                        || Pallet.PALLET_STATUS_IRREGULAR.equals(retrPallet.getStatusFlag()))
                {
                    if (Pallet.ALLOCATION_FLAG_ALLOCATED.equals(retrPallet.getAllocationFlag()))
                    {
                        OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_ALLOCATED);
                        ex.setErrorLineNo(i + 1);
                        throw ex;
                    }
                }
                else
                {
                	//今回の設定で出庫する棚と同じ棚の場合は処理を続ける
                	if(!compLoc.contains(asRetrLocation))
                	{
	                    OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_ALLOCATED);
	                    ex.setErrorLineNo(i + 1);
	                    throw ex;
                	}
                }


                // 出庫棚決定 (ルートチェックのみ行い、ステーション決定はdecideCarryInfo()で行う)
                boolean retrDet = routeCtlr.retrievalDetermin(retrPallet, wkStation, false);
                if (!retrDet)
                {
                    throw routeOpException(i, routeCtlr.getRouteStatus());
                }

                // 出庫対象在庫検索
                for (Stock tstock : tStocks)
                {
                    if (isSameKey(tstock, param))
                    {
                        targetStock = tstock;
                        break;
                    }
                }
                if (null == targetStock)
                {
                    // 他端末で更新済み
                    throw updatedOpException(i);
                }

                // reserve for retrieval
                stockCtlr.retrievalReserve(targetStock, param.getRetrievalQty());

                // create carry info
                CarryInfo carry = new CarryInfo();

                String workNo = seqHandler.nextRetrievalWorkNo();

                carry.setPalletId(palletId);
                carry.setWorkType(SystemDefine.WORK_TYPE_NOPLAN_RETRIEVAL);
                carry.setWorkNo(workNo);
                carry.setRetrievalStationNo(asRetrLocation);
                carry.setSourceStationNo(retrPallet.getCurrentStationNo());
                carry.setDestStationNo(routeCtlr.getDestStation().getStationNo());
                carry.setScheduleNo(schNo);
                if (!StringUtil.isBlank(param.getPriorityType()))
                {
                	carry.setPriority(param.getPriorityType());
                }

                // create AS/RS data
                String carryKey = createAsrsRetrievalData(retrPallet, carry);

                // work info create
                WorkInfo work = new WorkInfo();

                // 集約作業No.
                String wicJobNo = seqHandler.nextWorkInfoCollectJobNo();
                work.setCollectJobNo(wicJobNo);
                work.setConsignorCode(param.getConsignorCode());
                work.setItemCode(param.getItemCode());
                work.setSettingUnitKey(settingUnitKey);
                work.setJobType(SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL);
                work.setStockId(targetStock.getStockId());
                work.setSystemConnKey(carryKey);

                work.setPlanDay(currWorkDay);
                work.setPlanAreaNo(areaNo);
                work.setPlanLocationNo(loc);
                work.setPlanLotNo(param.getLotNo());
                work.setPlanQty(param.getRetrievalQty());

                // regist work info
                createWorkInfo(param, work);

                compLoc.add(asRetrLocation);
            }
            catch (NotFoundException e)
            {
                // 他端末で更新済み
                throw updatedOpException(i);
            }
            catch (DataExistsException e)
            {
                // 他端末で更新済み
                throw updatedOpException(i);
            }
            catch (OperatorException e)
            {
                e.setErrorLineNo(i + 1);
                throw e;
            }
        } // end for loop of parameter

        // decide carry info
        decideCarryInfo(schNo, firstParam.getStationNo());

        // 作業リスト情報作成メソッド
        createWorkListByWorkInfo(settingUnitKey);

        // 出力パラメータに設定単位キーを追加
        AsrsOutParameter retParam = new AsrsOutParameter();
        retParam.setSettingUnitKey(settingUnitKey);

        return retParam;
    }

    /**
     * WEB画面出庫作業開始を行います。<br>
     * パラメータの項目に該当するデータに対して出庫開始処理を行い、設定単位キーを返します。
     * <!--
     * 以下順で処理を行う。
     * ・出庫開始ロックメソッド（AsWorkInfoController）
     * ・出庫作業開始メソッド
     * -->
     * @param param パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>作業区分
     * <li>荷主コード
     * <li>バッチNo.
     * <li>エリアNo.
     * <li>オーダーNo.
     * <li>ステーションNo. (ステーションまたは作業場)
     * <li>ユーザ情報 (ハードウエア以外)
     * </ol>
     *
     * @return 設定単位キー
     *
     * @throws ReadWriteException データベースアクセスエラー
     * @throws LockTimeOutException 出庫作業情報のロックタイムアウト
     * @throws NoPrimaryException 対象ステーションまたはパレットが複数見つかった
     * @throws OperatorException 作業が続行できない
     * @throws ScheduleException パレット情報が見つからない
     * @throws InvalidDefineException パラメータの内容が不正
     * @throws RouteException 搬送ルートなし
     * @throws NotFoundException 該当する予定データが存在しない
     * @throws DataExistsException 作業情報登録済み
     */
    public AsrsOutParameter webStartRetrieval(AsrsInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                InvalidDefineException,
                OperatorException,
                RouteException,
                ScheduleException,
                NotFoundException,
                DataExistsException
    {
        // prepare controllers
        AsWorkInfoController workCtlr = getAsWorkInfoCtlr();

        // get values from parameter
        String jobType = param.getJobType();
        String consig = param.getConsignorCode();
        String batchNo = param.getBatchNo();
        String area = param.getAreaNo();
        Station destSt = getDestStation(param);
        String[] orders = {
            param.getOrderNo(),
        };

        // Lock target work info.
        WorkInfo stwork = new WorkInfo();
        stwork.setJobType(jobType);
        stwork.setConsignorCode(consig);
        stwork.setBatchNo(batchNo);
        stwork.setPlanAreaNo(area);

        WorkInfo[] tWorks = workCtlr.lockRetrievalStart(stwork, destSt.getStationNo(), orders);

        // if no work found, check cause.
        if (ArrayUtil.isEmpty(tWorks))
        {
            try
            {
                workCtlr.checkRetrievalStart(stwork);
            }
            catch (OperatorException e)
            {
                // change error code, if NOT START EXISTS -> ALREADY UPDATED
                if (OperatorException.ERR_NOT_START_EXISTS.equals(e.getErrorCode()))
                {
                    e.setErrorCode(OperatorException.ERR_ALREADY_UPDATED);
                }
                throw e;
            }
        }

        // start work info for the retrieval
        return startRetrieval(param, tWorks);
    }

    /**
     * WEB画面入庫作業開始<br>
     * パラメータの項目に該当するデータに対して入庫開始処理を行い、設定単位キーを返します。<br>
     * 入庫設定、予定外入庫設定で使用します。<br>
     *
     * @param params ASRS入力パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>エリアNo.
     * <li>入庫数
     * <li>ステーションNo.
     * <li>ゾーンNo.
     * <li>作業区分 (JOB_TYPE)
     * <li>ロットNo.
     * <li>ユーザ情報 (ハードウエア区分を除く)
     * <li>荷主コード
     * <li>商品コード
     * <br><br>
     * 以下は予定外入庫時、マスタパッケージなしの場合
     * <li>商品名称
     * <li>JANコード
     * <li>ケース入数
     * <li>ボール入数
     * <li>ケースITF
     * <li>ボールITF
     * </ol>
     *
     * @return 設定単位キー
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)な場合にスローされます。
     * @throws ScheduleException 予期しない例外が発生した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在した場合にスローされます。
     * @throws RouteException 搬送ルートなし
     * @throws OperatorException オペレータ処理でエラー発生した場合にスローされます。
     * <ul>
     * エラーコード
     * <li>10 : 他端末で更新された
     * <li>11 : 他端末作業中
     * <li>12 : 作業完了済み
     * </ul>
     * @throws DataExistsException 入庫作業情報登録済み
     */
    public AsrsOutParameter webStartStorage(AsrsInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                OperatorException,
                ScheduleException,
                NoPrimaryException,
                RouteException,
                DataExistsException
    {
        if (ArrayUtil.isEmpty(params))
        {
            throw new InvalidDefineException("parameter is null or zero lengh");
        }

        // prepare controllers
        RouteController routeCtlr = getRouteCtlr();
        AreaController areaCtlr = getAreaCtlr();

        // 採番用ハンドラ
        WMSSequenceHandler seqHandler = getSeqHandler();

        // 設定単位キーの採番
        String settingUnitKey = seqHandler.nextWorkInfoSetUkey();
        // パレットIDの採番
        String palletId = seqHandler.nextPalletId();
        // 搬送キーの採番
        String carryKey = seqHandler.nextCarryKey();

        // get values from parameter
        AsrsInParameter firstParam = params[0];

        String warehouseNo = getWhStationNo(firstParam);

        // create pallet
        Pallet pallet = createStoragePallet(palletId, firstParam);

        String destLocation = "";
        // 入庫ルート可能チェック
        if (!checkStorageRoute(routeCtlr, pallet, warehouseNo))
        {
            throw new RouteException(routeCtlr.getRouteStatus());
        }

        // ルートコントローラの搬送先取得メソッド
        Station destSt = routeCtlr.getDestStation();
        String destWhSt = "";
        if (destSt instanceof Shelf)
        {
            Shelf destShelf = (Shelf)destSt;
            destLocation = destShelf.getStationNo();
            pallet.setCurrentStationNo(destLocation);
            destWhSt = destSt.getWhStationNo();
        }
        else if (destSt instanceof WareHouse)
        {
            // 倉庫の場合はWhStationNoが自身のStationNo
            destWhSt = destSt.getStationNo();
        }
        else if (destSt instanceof Station)
        {
            // リジェクトSTの場合は空棚なしとする
            throw new RouteException(RouteController.LOCATION_EMPTY);
        }

        // setup work info
        WorkInfo updateWorkInfo = new WorkInfo();
        updateWorkInfo.setSettingUnitKey(settingUnitKey); // 設定単位キー
        updateWorkInfo.setJobType(firstParam.getJobType()); // 作業区分
        updateWorkInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS); // ハードウェア区分(ASRS)
        updateWorkInfo.setSystemConnKey(carryKey); // システム接続キー

        String destArea = areaCtlr.getAreaNoOfWarehouse(destWhSt);
        updateWorkInfo.setPlanAreaNo(destArea);

        if (!StringUtil.isBlank(destLocation))
        {
            String location = areaCtlr.toParamLocation(destLocation);
            updateWorkInfo.setPlanLocationNo(location);
        }

        for (int i = 0; i < params.length; i++)
        {
            try
            {
                // 集約作業No.
                String wicJobNo = seqHandler.nextWorkInfoCollectJobNo();
                updateWorkInfo.setCollectJobNo(wicJobNo);

                AsrsInParameter iparam = params[i];
                String upLot = iparam.getLotNo();
                updateWorkInfo.setPlanLotNo(upLot); // ロットNo.

                int storageQty = iparam.getStorageQty();
                updateWorkInfo.setPlanQty(storageQty); // 予定数

                String jobType = iparam.getJobType();
                // 入庫の場合
                if (SystemDefine.JOB_TYPE_STORAGE.equals(jobType))
                {
                    startStorage(iparam, updateWorkInfo, pallet);
                }
                // 予定外入庫の場合
                else if (SystemDefine.JOB_TYPE_NOPLAN_STORAGE.equals(jobType))
                {
                    startNoPlanStorage(iparam, updateWorkInfo, pallet);
                }
                else
                {
                    throw new InvalidDefineException();
                }
            }
            catch (DataExistsException e)
            {
                throw updatedOpException(i);
            }
            catch (NotFoundException e)
            {
                throw updatedOpException(i);
            }
            catch (OperatorException e)
            {
                e.setErrorLineNo(i + 1);
                throw e;
            }
        }

        String jobType = firstParam.getJobType();

        // AS/RS入庫情報作成
        CarryInfo carry = new CarryInfo();

        carry.setCarryKey(carryKey);
        carry.setPalletId(palletId);
        carry.setWorkType(jobType);
        carry.setRetrievalStationNo(destLocation);

        // empty pallet check (No plan storage)
        if (1 == params.length)
        {
            String item = firstParam.getItemCode();
            int storeQty = firstParam.getStorageQty();

            boolean empItem = WmsParam.EMPTYPB_ITEMCODE.equals(item);
            boolean oneQty = (1 == storeQty);

            if (empItem && oneQty)
            {
                pallet.setEmptyFlag(SystemDefine.EMPTY_FLAG_EMPTY);
            }
        }
        // AS/RS入庫情報作成メソッド
        createAsrsStorageData(routeCtlr, pallet, carry);

        // 作業リスト情報作成メソッド
        createWorkListByWorkInfo(settingUnitKey);

        // 出力パラメータに設定単位キーを追加
        AsrsOutParameter retParam = new AsrsOutParameter();
        retParam.setSettingUnitKey(settingUnitKey);

        return retParam;
    }

    /**
     * WEB画面直行作業開始<br>
     * パラメータの項目に該当するデータに対して直行開始処理を行い、設定単位キーを返します。<br>
     * 直行設定、簡易直行設定で使用します。<br>
     *
     * @param param ASRS入力パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>エリアNo.
     * <li>予定数
     * <li>入庫ステーションNo.
     * <li>出庫ステーションNo.
     * <li>ロットNo.
     * <li>ユーザ情報 (ハードウエア区分を除く)
     * <li>荷主コード
     * <li>商品コード
     * </ol>
     *
     * @return 設定単位キー
     *
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)な場合にスローされます。
     * @throws ScheduleException 予期しない例外が発生した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在した場合にスローされます。
     * @throws NotFoundException 更新対象データが見つからない
     * @throws RouteException 搬送ルートなし
     * @throws DataExistsException 直行作業情報登録済み
     */
    public AsrsOutParameter webStartDirectTransfer(AsrsInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                ScheduleException,
                NoPrimaryException,
                NotFoundException,
                RouteException,
                DataExistsException
    {
        if (null == param)
        {
            throw new InvalidDefineException("parameter is null");
        }

        // prepare controllers
        RouteController routeCtlr = getRouteCtlr();

        // 採番用ハンドラ
        WMSSequenceHandler seqHandler = getSeqHandler();

        // 設定単位キーの採番
        String settingUnitKey = seqHandler.nextWorkInfoSetUkey();
        // 集約作業No.
        String wicJobNo = seqHandler.nextWorkInfoCollectJobNo();
        // パレットIDの採番
        String palletId = seqHandler.nextPalletId();
        // 搬送キーの採番
        String carryKey = seqHandler.nextCarryKey();

        Station fstation = getStation(param.getStorageStationNo());
        Station tstation = getStation(param.getRetrievalStationNo());

        // 直行ルート可能チェック
        if (!routeCtlr.directTrancefarDeterminSCH(fstation, tstation))
        {
            throw new RouteException(routeCtlr.getRouteStatus());
        }

        // ルートコントローラの搬送先取得メソッド
        Station destSt = routeCtlr.getDestStation();

        // パレットの作成
        Pallet pallet = createDirectTransferPallet(palletId, routeCtlr, param);

        // setup work info
        WorkInfo newWorkInfo = new WorkInfo();
        newWorkInfo.setSettingUnitKey(settingUnitKey); // 設定単位キー
        newWorkInfo.setCollectJobNo(wicJobNo); // 集約作業No.
        newWorkInfo.setJobType(SystemDefine.JOB_TYPE_DIRECT_TRAVEL); // 作業区分
        newWorkInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS); // ハードウェア区分(ASRS)
        newWorkInfo.setSystemConnKey(carryKey); // システム接続キー
        newWorkInfo.setPlanAreaNo(param.getAreaNo()); // エリアNo.
        newWorkInfo.setPlanLocationNo(destSt.getStationNo()); // 搬送先ステーション
        newWorkInfo.setPlanQty(param.getPlanQty()); // 作業数
        newWorkInfo.setPlanLotNo(param.getPlanLotNo()); // 予定ロットNo.

        // empty pallet check
        String item = param.getItemCode();
        int planQty = param.getPlanQty();

        boolean empItem = WmsParam.EMPTYPB_ITEMCODE.equals(item);
        boolean oneQty = (1 == planQty);

        if (empItem && oneQty)
        {
            pallet.setEmptyFlag(SystemDefine.EMPTY_FLAG_EMPTY);
        }

        // 直行作業開始
        startDirectTransfer(param, newWorkInfo, pallet);

        // AS/RS入庫情報作成
        CarryInfo carry = new CarryInfo();

        carry.setCarryKey(carryKey); // 搬送キー
        carry.setPalletId(palletId); // パレットID
        carry.setWorkType(SystemDefine.JOB_TYPE_DIRECT_TRAVEL); // 作業種別（直行）
        carry.setRetrievalStationNo(""); // 出庫ロケーションNo.（空白）

        // AS/RS入庫情報作成メソッド
        createAsrsStorageData(routeCtlr, pallet, carry);

        // 出力パラメータに設定単位キーを追加
        AsrsOutParameter retParam = new AsrsOutParameter();
        retParam.setSettingUnitKey(settingUnitKey);

        return retParam;
    }

    /**
     * WEB画面再入庫作業開始<br>
     * パラメータの項目に該当するデータに対して再入庫開始処理を行い、設定単位キーを返します。<br>
     * 再入庫設定で使用します。<br>
     *
     * @param params ASRS入力パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>エリアNo.
     * <li>ステーションNo.
     * <li>作業No.
     * <li>ソフトゾーンNo.
     * <li>ゾーンNo.
     * <li>理由区分
     * <li>ユーザ情報 (ハードウエア区分を除く)
     * </ol>
     *
     * @return 設定単位キー
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)な場合にスローされます。
     * @throws ScheduleException 予期しない例外が発生した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在した場合にスローされます。
     * @throws RouteException 搬送ルートなし
     * @throws OperatorException オペレータ処理でエラー発生した場合にスローされます。
     * <ul>
     * エラーコード
     * <li>10 : 他端末で更新された
     * <li>11 : 他端末作業中
     * <li>12 : 作業完了済み
     * </ul>
     * @throws DataExistsException データ登録済み
     */
    public AsrsOutParameter webStartReStoring(AsrsInParameter[] params)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                OperatorException,
                ScheduleException,
                NoPrimaryException,
                RouteException,
                DataExistsException
    {
        if (ArrayUtil.isEmpty(params))
        {
            throw new InvalidDefineException("parameter is null or zero lengh");
        }

        // prepare controllers
        // ルートコントローラ(レスポンスUPのため、負荷のかかるチェックはしない)
        RouteController routeCtlr = new RouteController(getConnection(), false, true);

        AreaController areaCtlr = getAreaCtlr();

        // 採番用ハンドラ
        WMSSequenceHandler seqHandler = getSeqHandler();

        // 設定単位キーの採番
        String settingUnitKey = seqHandler.nextWorkInfoSetUkey();

        // get values from parameter
        AsrsInParameter firstParam = params[0];

        String warehouseNo = getWhStationNo(firstParam);

        for (int i = 0; i < params.length; i++)
        {
            // パレットIDの採番
            String palletId = seqHandler.nextPalletId();
            // 搬送キーの採番
            String carryKey = seqHandler.nextCarryKey();

            // create pallet
            Pallet pallet = createStoragePallet(palletId, params[i]);

            String destLocation = "";
            // 入庫ルート可能チェック
            if (!checkStorageRoute(routeCtlr, pallet, warehouseNo))
            {
                throw new RouteException(routeCtlr.getRouteStatus());
            }

            // ルートコントローラの搬送先取得メソッド
            Station destSt = routeCtlr.getDestStation();
            String destWhSt = "";
            if (destSt instanceof Shelf)
            {
                Shelf destShelf = (Shelf)destSt;
                destLocation = destShelf.getStationNo();
                pallet.setCurrentStationNo(destLocation);
                destWhSt = destSt.getWhStationNo();
            }
            else if (destSt instanceof WareHouse)
            {
                // 倉庫の場合はWhStationNoが自身のStationNo
                destWhSt = destSt.getStationNo();
            }
            else if (destSt instanceof Station)
            {
                // リジェクトSTの場合は空棚なしとする
                throw new RouteException(RouteController.LOCATION_EMPTY);
            }

            // setup work info
            WorkInfo updateWorkInfo = new WorkInfo();
            updateWorkInfo.setSettingUnitKey(settingUnitKey); // 設定単位キー
            updateWorkInfo.setJobType(SystemDefine.JOB_TYPE_RESTORING); // 作業区分
            updateWorkInfo.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS); // ハードウェア区分(ASRS)
            updateWorkInfo.setSystemConnKey(carryKey); // システム接続キー

            String destArea = areaCtlr.getAreaNoOfWarehouse(destWhSt);
            updateWorkInfo.setPlanAreaNo(destArea);

            if (!StringUtil.isBlank(destLocation))
            {
                String location = areaCtlr.toParamLocation(destLocation);
                updateWorkInfo.setPlanLocationNo(location);
            }

            // get plan
            ReStoringPlan[] plan = getReStoringPlanData(params[i].getWorkNo());
            if (plan == null || plan.length <= 0)
            {
                throw updatedOpException(i);
            }

            WmsUserInfo ui = params[i].getWmsUserInfo();
            int reason = params[i].getReasonType();
            for (int j = 0; j < plan.length; j++)
            {
                try
                {
                    // 集約作業No.
                    String wicJobNo = seqHandler.nextWorkInfoCollectJobNo();
                    updateWorkInfo.setCollectJobNo(wicJobNo);

                    startReStoring(plan[j], updateWorkInfo, pallet, ui, reason);
                }
                catch (DataExistsException e)
                {
                    throw updatedOpException(i);
                }
                catch (NotFoundException e)
                {
                    throw updatedOpException(i);
                }
                catch (OperatorException e)
                {
                    e.setErrorLineNo(i + 1);
                    throw e;
                }
            }

            // AS/RS入庫情報作成
            CarryInfo carry = new CarryInfo();

            carry.setCarryKey(carryKey);
            carry.setPalletId(palletId);
            carry.setWorkType(SystemDefine.JOB_TYPE_RESTORING);
            carry.setRetrievalStationNo(destLocation);

            // AS/RS入庫情報作成メソッド
            createAsrsStorageData(routeCtlr, pallet, carry);
        }

        // 作業リスト情報作成メソッド
        createWorkListByWorkInfo(settingUnitKey);

        // 出力パラメータに設定単位キーを追加
        AsrsOutParameter retParam = new AsrsOutParameter();
        retParam.setSettingUnitKey(settingUnitKey);

        return retParam;
    }

    /**
     * WEB画面出庫キャンセル処理を行います。<br>
     * パラメータの項目に該当するデータに対してキャンセル処理(引き当て状態まで搬送情報を修正)を行います。
     *
     * @param param パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>荷主コード
     * <li>バッチNo.
     * <li>エリアNo.
     * <li>オーダーNo.(From/To)
     * <li>ステーションNo. (ステーションまたは作業場)
     * </ol>
     *
     * @throws ReadWriteException データベースアクセスエラー
     * @throws LockTimeOutException 出庫作業情報のロックタイムアウト
     * @throws NoPrimaryException 対象ステーションまたはパレットが複数見つかった
     * @throws ScheduleException パレット情報が見つからない
     * @throws InvalidDefineException パラメータの内容が不正
     * @throws NotFoundException 該当する作業データが存在しない
     */
    public void cancelRetrieval(AsrsInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                InvalidDefineException,
                ScheduleException,
                NotFoundException
    {
        // prepare controllers
        AsWorkInfoController workCtlr = getAsWorkInfoCtlr();
        RetrievalPlanController retPlanCtrl = new RetrievalPlanController(getConnection(), getCaller());

        // get values from parameter
        String consig = param.getConsignorCode();
        String batchNo = param.getBatchNo();
        String area = param.getAreaNo();
        Station destSt = getDestStation(param);

        String retrDay = param.getRetrievalDay();

        String fromOrder = param.getOrderNo();
        String toOrder = param.getToOrderNo();

        // ------------------------------------
        // Lock target work info.
        // ------------------------------------
        WorkInfo stwork = new WorkInfo();
        stwork.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
        stwork.setConsignorCode(consig);
        stwork.setBatchNo(batchNo);
        stwork.setPlanAreaNo(area);
        stwork.setPlanDay(retrDay);

        /* ロック対象キー項目 (WorkInfo, CarryInfo)
         *
         *  荷主コード
         *  予定日 (出庫予定日)
         *  予定エリア
         *  行先 (CarryInfo:出庫先作業場またはステーション)
         *  バッチNo.
         *  オーダーNo.
         *
         * 以下固定キー
         *  作業区分 (出庫)
         *  状態フラグ (作業中)
         *  HW区分 (AS/RS)
         */
        WorkInfo[] tWorks = workCtlr.lockRetrievalCancel(stwork, destSt.getStationNo(), fromOrder, toOrder);
        if (ArrayUtil.isEmpty(tWorks))
        {
            // キャンセル対象データなし
            throw new NotFoundException();
        }

        // ------------------------------------
        // キャンセル対象作業情報収集
        // ------------------------------------
        List<WorkInfo> cancelTargetList = new ArrayList<WorkInfo>();
        for (WorkInfo work : tWorks)
        {
            if (isCancelTarget(work))
            {
                cancelTargetList.add(work);
            }
        }
        if (cancelTargetList.isEmpty())
        {
            // キャンセル対象データなし
            throw new NotFoundException();
        }

        // ------------------------------------
        // 入出庫作業情報キャンセル
        // ------------------------------------
        // 作業単位ごとに入出庫作業情報をキャンセル
        for (WorkInfo work : cancelTargetList)
        {
            workCtlr.asCancelWorkInfo(work);
        }

        // ------------------------------------
        // 出庫予定情報キャンセル
        // ------------------------------------
        WorkInfo[] cancelWorks = cancelTargetList.toArray(new WorkInfo[cancelTargetList.size()]);
        retPlanCtrl.cancelPlan(cancelWorks);

        // ------------------------------------
        // 搬送情報を引当済みに差し戻し
        // ------------------------------------
        cancelCarries(cancelWorks);
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * WMSSequenceHandler を取得します。
     * @return WMSSequenceHandler<br>
     * ハンドラはインスタンス変数にキャッシュされます。
     */
    protected WMSSequenceHandler getSeqHandler()
    {
        if (null == _wmsSeqHandler)
        {
            _wmsSeqHandler = new WMSSequenceHandler(getConnection());
        }
        return _wmsSeqHandler;
    }

    /**
     * @return AreaController
     * @throws ReadWriteException データベースアクセスエラー
     */
    protected AreaController getAreaCtlr()
            throws ReadWriteException
    {
        if (null == _areaCtlr)
        {
            _areaCtlr = new AreaController(getConnection(), getCaller());
        }
        return _areaCtlr;
    }

    /**
     * @return StockController
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException システム定義不整合
     */
    protected AsStockController getStockCtlr()
            throws ReadWriteException,
                ScheduleException
    {
        if (null == _stockCtlr)
        {
            _stockCtlr = new AsStockController(getConnection(), getCaller());
        }
        return _stockCtlr;
    }

    /**
     * @return AsWorkInfoController
     */
    protected AsWorkInfoController getAsWorkInfoCtlr()
    {
        if (null == _workInfoCtlr)
        {
            _workInfoCtlr = new AsWorkInfoController(getConnection(), getCaller());
        }
        return _workInfoCtlr;
    }

    /**
     * @return WarenaviSystemController
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException システム定義不整合
     */
    protected WarenaviSystemController getWSysCtlr()
            throws ReadWriteException,
                ScheduleException
    {
        if (null == _wmsysCtlr)
        {
            _wmsysCtlr = new WarenaviSystemController(getConnection(), getCaller());
        }
        return _wmsysCtlr;
    }

    /**
     * @return ConsignorController
     */
    protected ConsignorController getConsigCtlr()
    {
        if (null == _consCtlr)
        {
            _consCtlr = new ConsignorController(getConnection(), getCaller());
        }
        return _consCtlr;
    }

    /**
     * ルートコントローラを返します。
     * @return ルートコントローラを返します。
     */
    protected RouteController getRouteCtlr()
    {
        if (null == _routeCtlr)
        {
            _routeCtlr = new RouteController(getConnection());
        }
        return _routeCtlr;
    }

    /**
     * @return ItemController
     */
    protected ItemController getItemCtlr()
    {
        if (null == _itemCtlr)
        {
            _itemCtlr = new ItemController(getConnection(), getCaller());
        }
        return _itemCtlr;
    }

    /**
     * @return StoragePlanController
     */
    protected StoragePlanController getStorgaePlanCtlr()
    {
        if (null == _storagePlanCtlr)
        {
            _storagePlanCtlr = new StoragePlanController(getConnection(), getCaller());
        }
        return _storagePlanCtlr;
    }

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------

    /**
     * 在庫とパラメータのキー項目を比較します。
     * <ul>
     * <li>荷主
     * <li>商品コード
     * <li>予定ロットNo.
     * </ul>
     * @param ckst 在庫
     * @param iparam パラメータ
     * @return 同じキーを持つときtrue.
     */
    protected boolean isSameKey(Stock ckst, AsrsInParameter iparam)
    {
        // consignor code
        if (!isSameString(ckst.getConsignorCode(), iparam.getConsignorCode()))
        {
            return false;
        }

        // item code
        if (!isSameString(ckst.getItemCode(), iparam.getItemCode()))
        {
            return false;
        }

        // lot number
        if (!isSameString(ckst.getLotNo(), iparam.getPlanLotNo())) // UPDATE_SS (2007-07-06)
        {
            return false;
        }
        return true;
    }

    /**
     * 文字列の比較 (nullを含む)
     *
     * @param str1 比較文字1
     * @param str2 比較文字2
     * @return 両方の文字列が同じときtrue
     */
    protected boolean isSameString(String str1, String str2)
    {
        // same if both empty
        if (StringUtil.isBlank(str1) && StringUtil.isBlank(str2))
        {
            return true;
        }

        // not same str1 is null and str2 is not null
        if (null == str1 && !StringUtil.isBlank(str2))
        {
            return false;
        }

        // checked by str1 (str1 is not null)
        return (null != str1) && str1.equals(str2);
    }

    /**
     * 作業情報の重複内容をチェックします。<br>
     * FIXME DESIGN PENDING.
     *
     * @param works チェック対象作業一覧
     * @throws DuplicateOperatorException 重複している場合にその内容とともにスローされます。
     */
    protected void checkDuplicate(WorkInfo[] works)
            throws DuplicateOperatorException
    {
        DuplicateOperatorException ex = new DuplicateOperatorException();

        List dup = checkDuplicate(works, WorkInfo.PLAN_DAY);
        if (dup != null)
        {
            ex.setErrorCode(OperatorException.ERR_PLAN_DAY_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }

        dup = checkDuplicate(works, WorkInfo.PLAN_AREA_NO, WorkInfo.PLAN_LOCATION_NO);
        if (dup != null)
        {
            ex.setErrorCode(OperatorException.ERR_AREA_LOCATION_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }

        dup = checkDuplicate(works, WorkInfo.PLAN_LOT_NO);
        if (dup != null)
        {
            ex.setErrorCode(OperatorException.ERR_LOT_NO_DUPLICATED);
            ex.setDetail(dup);
            throw ex;
        }
    }

    /**
     * 入庫ルート可能チェック<br>
     * パラメータのコントローラ、倉庫No.より入庫の搬送ルートチェックを行い、<br>
     * 搬送先が棚の場合はパレット情報の現在ステーションを搬送先棚に更新する。<br>
     * 入庫可能ルートが存在した場合はtrue、それ以外はfalseを返す。<br>
     * <br>
     *
     * @param rc ルートコントローラ
     * @param plt パレット情報エンティティ<br>
     * @param whStNo 倉庫ステーションNo.
     * @return 入庫可能ルートが存在した場合はtrue、それ以外はfalse
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     * @throws ScheduleException 予期しない例外が発生した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在した場合にスローされます。
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)な場合にスローされます。
     * @throws LockTimeOutException 入庫棚決定時にロックタイムアウト
     */
    protected boolean checkStorageRoute(RouteController rc, Pallet plt, String whStNo)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException,
                InvalidDefineException,
                LockTimeOutException
    {
        // 倉庫情報の検索
        WareHouseSearchKey whKey = new WareHouseSearchKey();
        whKey.setStationNo(whStNo);
        WareHouse wareHouse = (WareHouse)new WareHouseHandler(getConnection()).findPrimary(whKey);

        if (null == wareHouse)
        {
            throw new ScheduleException("no warehouse found");
        }

        // ルートコントローラの入庫棚決定メソッド
        return rc.storageDeterminSCH(plt, wareHouse, true);
    }

    /**
     * AS/RS出庫情報を作成します。
     *
     * @param plt 出庫対象パレット
     * <ol>
     * 以下の項目を参照します。
     * <li>STATUS FLAG
     * <li>PALLET ID
     * </ol>
     *
     * @param carry 搬送情報
     * <ol>
     * 以下の項目を参照します。
     * <li>WORK TYPE
     * <li>RETRIEVAL STATION NO
     * <li>SCHEDULE NO
     * </ol>
     *
     * @return 搬送キー (CarryKey)
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException ステーション定義異常
     * @throws NoPrimaryException 予定外出庫時に対象搬送情報が複数見つかった
     * @throws DataExistsException 搬送情報登録済み
     * @throws NotFoundException 更新対象パレットなし
     */
    protected String createAsrsRetrievalData(Pallet plt, CarryInfo carry)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException,
                DataExistsException,
                NotFoundException
    {
        // 在庫状態が「実棚」であるか、
        // 在庫状態が「異常」で、予定外出庫のとき、パレット情報を更新
        String stockStat = plt.getStatusFlag();
        String workType = carry.getWorkType();

        boolean regPal = SystemDefine.PALLET_STATUS_REGULAR.equals(stockStat);
        boolean irrPal =
                SystemDefine.WORK_TYPE_NOPLAN_RETRIEVAL.equals(workType)
                        && SystemDefine.PALLET_STATUS_IRREGULAR.equals(stockStat);

        if (regPal || irrPal)
        {
            PalletAlterKey plakey = new PalletAlterKey();
            plakey.updateStatusFlag(SystemDefine.PALLET_STATUS_RETRIEVAL_PLAN);
            plakey.updateAllocationFlag(SystemDefine.ALLOCATION_FLAG_ALLOCATED);
            plakey.updateLastUpdatePname(getCallerName());

            plakey.setPalletId(plt.getPalletId());

            (new PalletHandler(getConnection())).modify(plakey);
        }

        // 予定外出庫の場合搬送情報の検索
        if (SystemDefine.WORK_TYPE_NOPLAN_RETRIEVAL.equals(workType))
        {
            CarryInfoSearchKey cikey = new CarryInfoSearchKey();
            cikey.setRetrievalStationNo(carry.getRetrievalStationNo());
            cikey.setScheduleNo(carry.getScheduleNo());

            CarryInfo ci = (CarryInfo)(new CarryInfoHandler(getConnection())).findPrimary(cikey);
            if (null != ci)
            {
                // find carry info, return carry key
                return ci.getCarryKey();
            }

            // getting new carry key
            String newCkey = getSeqHandler().nextCarryKey();
            carry.setCarryKey(newCkey);
        }

        // create carry info for retrieval
        createRetrievalCarryInfo(carry);
        return carry.getCarryKey();
    }

    /**
     * AS/RS入庫情報作成<br>
     * パラメータの項目より入庫用の搬送情報、パレット情報、作業指示画面情報を登録する。<br>
     * <br>
     * @param rc ルートコントローラ
     * @param plt 登録するパレット情報
     * @param carry 登録する搬送情報エンティティ
     * <ol>
     * 以下の項目を参照します。
     * <li>搬送キー
     * <li>パレットID
     * <li>作業種別
     * <li>出庫ロケーションNo.
     * </ol>
     * @throws ReadWriteException データベースアクセスエラー
     * @throws DataExistsException データ登録済み
     */
    protected void createAsrsStorageData(RouteController rc, Pallet plt, CarryInfo carry)
            throws ReadWriteException,
                DataExistsException
    {
        // 入庫搬送情報作成
        createStorageCarryInfo(rc, carry);

        // パラメータのパレット情報を登録する
        PalletHandler pltHandler = new PalletHandler(getConnection());
        pltHandler.create(plt);

        // ルートコントローラの搬送元ステーションの作業指示運用の判定
        Station srcStation = rc.getSrcStation();
        String opDisp = srcStation.getOperationDisplay();
        if (!SystemDefine.OPERATION_DISPLAY_NONE.equals(opDisp))
        {
            String pgname = getCallerName();

            // 作業指示画面情報の編集
            OperationDisplay opeDisplay = new OperationDisplay();
            opeDisplay.setCarryKey(carry.getCarryKey()); // 搬送キー
            opeDisplay.setStationNo(srcStation.getStationNo()); // ルートコントローラの搬送元のステーションNo.

            opeDisplay.setRegistPname(pgname); // 登録処理名
            opeDisplay.setRegistDate(new SysDate());
            opeDisplay.setLastUpdatePname(pgname); // 最終更新処理名

            OperationDisplayHandler opeHandler = new OperationDisplayHandler(getConnection());
            opeHandler.create(opeDisplay);
        }
    }

    /**
     * 在庫確認情報を登録します。
     *
     * @param param 入力パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>エリアNo.
     * <li>荷主コード
     * <li>棚No.
     * <li>TO 棚No.
     * <li>商品コード
     * <li>TO 商品コード
     * <li>ステーションNo.
     * </ol>
     *
     * @param schNo スケジュールNo.
     * @throws ReadWriteException データベースアクセスエラー
     * @throws DataExistsException 在庫確認情報登録済み
     */
    protected void createInventoryCheck(AsrsInParameter param, String schNo)
            throws ReadWriteException,
                DataExistsException
    {
        String whStNo = getWhStationNo(param);

        InventoryCheck invCheck = new InventoryCheck();

        invCheck.setScheduleNo(schNo);

        // get MIN/MAX if no location set
        String fromLoc = param.getLocation();

        invCheck.setFromLocation(fromLoc);

        String toLoc = param.getToLocation();

        invCheck.setToLocation(toLoc);

        invCheck.setWhStationNo(whStNo);
        invCheck.setConsignorCode(param.getConsignorCode());

        invCheck.setFromItemCode(param.getItemCode());
        invCheck.setToItemCode(param.getToItemCode());
        invCheck.setStationNo(param.getStationNo());

        invCheck.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);

        invCheck.setRegistPname(getCallerName());
        invCheck.setRegistDate(new SysDate());
        invCheck.setLastUpdatePname(getCallerName());

        (new InventoryCheckHandler(getConnection())).create(invCheck);
    }

    /**
     * 在庫確認対象在庫を検索するキーを生成します。
     *
     * @param param 入力パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>ステーションNo.
     * <li>商品コード
     * <li>TO 商品コード
     * <li>エリアNo.
     * <li>荷主コード
     * </ol>
     * @param aisleNo アイルステーションNo
     * @param fromLoc 棚No.
     * @param toLoc   TO 棚No.
     *
     * @return 検索キー
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException ステーション定義異常
     * @throws NoPrimaryException エリアマスタに同一エリアが複数登録されている
     */
    protected StockSearchKey createInventoryKey(AsrsInParameter param, String aisleNo, LocationNumber fromLoc,
            LocationNumber toLoc)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException
    {
        String stNo = param.getStationNo();
        Station tStation = getStation(stNo);
        if (null == tStation)
        {
            throw new ScheduleException("No station found:" + stNo);
        }

        // get parameter values
        String fromItem = param.getItemCode();
        String toItem = param.getToItemCode();

        // convert AS/RS type location
        String whStNo = getWhStationNo(param);

        AreaController areaCtlr = new AreaController(getConnection(), getCaller());

        String eType = areaCtlr.getEmploymentTypeOfWarehouse(whStNo);
        boolean closeSystem = WareHouse.EMPLOYMENT_TYPE_CLOSE.equals(eType);

        //-----------------------------------------------
        // build search key (with join)
        //-----------------------------------------------
        StockSearchKey key = new StockSearchKey();

        key.setAreaNo(param.getAreaNo());
        key.setConsignorCode(param.getConsignorCode());

        // Item range
        key.setRangeKey(Stock.ITEM_CODE, fromItem, toItem, true);

        key.setStockQty(0, ">");

        //----------------------
        // Pallet
        //----------------------
        key.setKey(Pallet.WH_STATION_NO, whStNo);

        // stock status
        String[] palletStats = {
                Pallet.PALLET_STATUS_REGULAR,
                Pallet.PALLET_STATUS_RETRIEVAL_PLAN,
                Pallet.PALLET_STATUS_RETRIEVAL,
        };
        key.setKey(Pallet.STATUS_FLAG, palletStats, true);

        if (closeSystem)
        {
            key.setKey(Pallet.EMPTY_FLAG, SystemDefine.EMPTY_FLAG_EMPTY, "!=", "", "", true);
        }

        //----------------------
        // Shelf
        //----------------------
        key.setKey(Shelf.STATUS_FLAG, SystemDefine.LOCATION_STATUS_FLAG_STORAGED);
        key.setKey(Shelf.PROHIBITION_FLAG, Shelf.PROHIBITION_FLAG_OK);
        key.setKey(Shelf.ACCESS_NG_FLAG, Shelf.ACCESS_NG_FLAG_OK);

        key.setKey(Shelf.PARENT_STATION_NO, aisleNo);
        // Location range
        if (fromLoc != null)
        {
            String location[] = fromLoc.getLocation();
            key.setKey(Shelf.BANK_NO, new BigDecimal(location[LocationNumber.IDX_BANK]), ">=", "", "", true);
            key.setKey(Shelf.BAY_NO, new BigDecimal(location[LocationNumber.IDX_BAY]), ">=", "", "", true);
            key.setKey(Shelf.LEVEL_NO, new BigDecimal(location[LocationNumber.IDX_LEVEL]), ">=", "", "", true);
        }
        if (toLoc != null)
        {
            String location[] = toLoc.getLocation();
            key.setKey(Shelf.BANK_NO, new BigDecimal(location[LocationNumber.IDX_BANK]), "<=", "", "", true);
            key.setKey(Shelf.BAY_NO, new BigDecimal(location[LocationNumber.IDX_BAY]), "<=", "", "", true);
            key.setKey(Shelf.LEVEL_NO, new BigDecimal(location[LocationNumber.IDX_LEVEL]), "<=", "", "", true);
        }

        //----------------------
        // set join keys
        //----------------------
        key.setJoin(Stock.PALLET_ID, Pallet.PALLET_ID);
        key.setJoin(Pallet.CURRENT_STATION_NO, Shelf.STATION_NO);

        //----------------------
        // set sort order
        //----------------------
        key.setOrder(Stock.LOCATION_NO, true);
        key.setOrder(Stock.ITEM_CODE, true);

        return key;
    }

    /**
     * ダブルディープの在庫確認対象在庫を検索するSQLを生成します。
     *
     * @param param 入力パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>ステーションNo.
     * <li>エリアNo.
     * </ol>
     * @param aisleNo アイルステーションNo
     * @param fromLoc 棚No.
     * @param toLoc   TO 棚No.
     *
     * @return 検索Sql
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException ステーション定義異常
     * @throws NoPrimaryException エリアマスタに同一エリアが複数登録されている
     */
    protected String createInventorySql(AsrsInParameter param, String aisleNo, LocationNumber fromLoc,
            LocationNumber toLoc)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException
    {
        String stNo = param.getStationNo();
        Station tStation = getStation(stNo);
        if (null == tStation)
        {
            throw new ScheduleException("No station found:" + stNo);
        }

        // convert AS/RS type location
        String whStNo = getWhStationNo(param);
        // SQL
        // 手前棚、奥棚検索共有のSQLを生成
        //
        //  DNPALLET.CURRENT_STATION_NO >= '101001001000' AND DNPALLET.CURRENT_STATION_NO <= '110003003000'
        // AND DMSHELF.PROHIBITION_FLAG = '0'
        // AND DMSHELF.STATUS_FLAG = '1'
        // AND DMSHELF.ACCESS_NG_FLAG = '0'
        // AND DNPALLET.STATUS_FLAG = '2'
        // AND DNPALLET.WH_STATION_NO = '9000'
        // AND DNSTOCK.STOCK_QTY > 0
        // AND DMSHELF.PARENT_STATION_NO = '9001'
        // AND DNSTOCK.PALLET_ID = DNPALLET.PALLET_ID
        // AND DMSHELF.STATION_NO = DNPALLET.CURRENT_STATION_NO
        StringBuffer comSql = new StringBuffer();
        if (fromLoc != null)
        {
            String location[] = fromLoc.getLocation();
            comSql.append("DMSHELF.BANK_NO >= ").append(Integer.parseInt(location[LocationNumber.IDX_BANK])).append(
                    " AND ");
            comSql.append("DMSHELF.BAY_NO >= ").append(Integer.parseInt(location[LocationNumber.IDX_BAY])).append(
                    " AND ");
            comSql.append("DMSHELF.LEVEL_NO >= ").append(Integer.parseInt(location[LocationNumber.IDX_LEVEL])).append(
                    " AND ");
        }
        if (toLoc != null)
        {
            String location[] = toLoc.getLocation();
            comSql.append("DMSHELF.BANK_NO <= ").append(Integer.parseInt(location[LocationNumber.IDX_BANK])).append(
                    " AND ");
            comSql.append("DMSHELF.BAY_NO <= ").append(Integer.parseInt(location[LocationNumber.IDX_BAY])).append(
                    " AND ");
            comSql.append("DMSHELF.LEVEL_NO <= ").append(Integer.parseInt(location[LocationNumber.IDX_LEVEL])).append(
                    " AND ");
        }

        // '08/12/04 追加開始
        // 手前棚の二重格納の副問合せ
        StringBuffer farShelfDupSql = new StringBuffer();
        farShelfDupSql.append("SELECT DNPALLET.CURRENT_STATION_NO ");
        farShelfDupSql.append("FROM DNPALLET, DMSHELF ");
        farShelfDupSql.append("WHERE ");
        farShelfDupSql.append("DNPALLET.STATUS_FLAG = ");
        farShelfDupSql.append(DBFormat.format(Pallet.PALLET_STATUS_IRREGULAR)).append(" ");
        farShelfDupSql.append("AND DMSHELF.SIDE = ");
        farShelfDupSql.append(DBFormat.format(Shelf.BANK_SELECT_NEAR)).append(" AND ");
        farShelfDupSql.append(String.valueOf(comSql));
        farShelfDupSql.append("DMSHELF.WH_STATION_NO = ");
        farShelfDupSql.append(DBFormat.format(whStNo)).append(" ");
        farShelfDupSql.append("AND DMSHELF.PARENT_STATION_NO = ");
        farShelfDupSql.append(DBFormat.format(aisleNo)).append(" ");
        farShelfDupSql.append("AND DNPALLET.CURRENT_STATION_NO = DMSHELF.STATION_NO");
        // '08/12/04 追加終了

        comSql.append("DMSHELF.PROHIBITION_FLAG = ");
        comSql.append(DBFormat.format(Shelf.PROHIBITION_FLAG_OK)).append(" ");
        comSql.append("AND DMSHELF.STATUS_FLAG = ");
        comSql.append(DBFormat.format(Shelf.LOCATION_STATUS_FLAG_STORAGED)).append(" ");
        comSql.append("AND DMSHELF.ACCESS_NG_FLAG = ");
        comSql.append(DBFormat.format(Shelf.ACCESS_NG_FLAG_OK)).append(" ");
        comSql.append("AND DNPALLET.STATUS_FLAG = ");
        comSql.append(DBFormat.format(Pallet.PALLET_STATUS_REGULAR)).append(" ");
        comSql.append("AND DNPALLET.WH_STATION_NO = ");
        comSql.append(DBFormat.format(whStNo)).append(" ");
        comSql.append("AND DNSTOCK.STOCK_QTY > 0");
        comSql.append("AND DMSHELF.PARENT_STATION_NO = ");
        comSql.append(DBFormat.format(aisleNo)).append(" ");
        comSql.append("AND DNSTOCK.PALLET_ID = DNPALLET.PALLET_ID ");
        comSql.append("AND DMSHELF.STATION_NO = DNPALLET.CURRENT_STATION_NO ");

        // 在庫確認のSQLを生成
        // 手前棚はShelfのStationNoをSTATION_NO、奥棚はShelfのPairStationNoをSTATION_NOとして
        // 取得。それをSTATION_NO+SIDE(SIDEは降順)でソートし、ペア棚の奥前棚、手前棚順になるようにする。
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM (");
        sql.append("SELECT DNSTOCK.*, ");
        sql.append("DMSHELF.STATION_NO STATION_NO, ");
        sql.append("DMSHELF.SIDE SIDE ");
        sql.append("FROM DNSTOCK, DMSHELF, DNPALLET ");
        sql.append("WHERE ");
        sql.append("DMSHELF.SIDE = ");
        sql.append(DBFormat.format(Shelf.BANK_SELECT_NEAR)).append(" ");
        sql.append("AND ").append(comSql);
        sql.append("UNION ");
        sql.append("SELECT DNSTOCK.*, ");
        sql.append("DMSHELF.PAIR_STATION_NO STATION_NO, ");
        sql.append("DMSHELF.SIDE SIDE ");
        sql.append("FROM DNSTOCK, DMSHELF, DNPALLET ");
        sql.append("WHERE ");
        sql.append("DMSHELF.SIDE = ");
        sql.append(DBFormat.format(Shelf.BANK_SELECT_FAR)).append(" ");
        sql.append("AND ").append(comSql);
        // '08/12/04 手前棚の二重格納の副問合せ １行追加
        sql.append("AND DMSHELF.PAIR_STATION_NO NOT IN (");
        sql.append(farShelfDupSql).append(")");
        sql.append(") ORDER BY STATION_NO, SIDE DESC, ITEM_CODE");

        return new String(sql);
    }

    /**
     * 搬送情報をデータベースに登録します。
     *
     * @param newCarry 登録する搬送情報
     * <ol>
     * 以下の項目を参照します。
     * <li>WORK TYPE
     * <li>SRC STATION
     * <li>RETRIEVAL STATION
     * <li>DEST STATION
     * </ol>
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException 棚情報が見つからない
     * @throws NoPrimaryException 対象棚が一意ではない
     * @throws DataExistsException 搬送情報登録済み
     */
    protected void createRetrievalCarryInfo(CarryInfo newCarry)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException,
                DataExistsException
    {
        // check work type and fix priority.
        String workType = newCarry.getWorkType();

        String prio = "";

        if (StringUtil.isBlank(newCarry.getPriority()))
        {
            boolean urgent = (WORK_TYPE_ADD_STORAGE.equals(workType) || WORK_TYPE_NOPLAN_RETRIEVAL.equals(workType));
	        prio = (urgent) ? SystemDefine.PRIORITY_EMERGENCY
	                              : SystemDefine.PRIORITY_NORMAL;
        }
        else
        {
        	prio = newCarry.getPriority();
        }

        // fix restoring flag
        String restFlag = SystemDefine.RESTORING_FLAG_SAME_LOC;
        StationController stCtrl = new StationController(getConnection(), getCaller());
        if (stCtrl.isReStoringEmptyLocationSearch(newCarry.getDestStationNo()))
        {
            restFlag = SystemDefine.RESTORING_FLAG_NOT_SAME_LOC;
        }
        else
        {
            String restInst = getStation(newCarry.getDestStationNo()).getRestoringInstruction();
            boolean awcControl = (SystemDefine.RESTORING_INSTRUCTION_AWC_STORAGE_SEND.equals(restInst));
            restFlag = (awcControl) ? SystemDefine.RESTORING_FLAG_NOT_SAME_LOC
                                   : SystemDefine.RESTORING_FLAG_SAME_LOC;
        }

        // fix retrieval detail
        String retrDetail = SystemDefine.RETRIEVAL_DETAIL_UNIT; // defalt
        if (SystemDefine.WORK_TYPE_ADD_STORAGE.equals(workType))
        {
            retrDetail = SystemDefine.RETRIEVAL_DETAIL_ADD_STORING;
        }
        else if (SystemDefine.WORK_TYPE_INVENTORYCHECK.equals(workType))
        {
            retrDetail = SystemDefine.RETRIEVAL_DETAIL_INVENTORY_CHECK;
        }

        // find AS/RS location for getting parent station (DMSHELF)
        ShelfSearchKey shkey = new ShelfSearchKey();
        String retrStNo = newCarry.getRetrievalStationNo();

        shkey.setParentStationNoCollect();
        shkey.setStationNo(retrStNo);
        Shelf slf = (Shelf)(new ShelfHandler(getConnection())).findPrimary(shkey);

        if (null == slf)
        {
            throw new ScheduleException("No parent station found for :" + retrStNo);
        }
        String parentStNo = slf.getParentStationNo();

        // build carry info
        CarryInfo nci = (CarryInfo)newCarry.clone();

        nci.setCmdStatus(SystemDefine.CMD_STATUS_START);
        nci.setPriority(prio);
        nci.setRestoringFlag(restFlag);
        nci.setCarryFlag(SystemDefine.CARRY_FLAG_RETRIEVAL);
        nci.setRetrievalDetail(retrDetail);
        nci.setCancelRequest(SystemDefine.CANCEL_REQUEST_UNDEMAND);
        nci.setAisleStationNo(parentStNo);
        nci.setEndStationNo(newCarry.getDestStationNo());

        String pgname = getCallerName();
        nci.setRegistPname(pgname);
        nci.setRegistDate(new SysDate());
        nci.setLastUpdatePname(pgname);
        nci.setLastUpdateDate(new SysDate());

        (new CarryInfoHandler(getConnection())).create(nci);
    }


    /**
     * 在庫情報から、在庫確認作業を作成する
     * @param targetStocks 在庫情報
     * @param wkStation 作業ステーション
     * @param param パラメータ
     * @param settingUnitKey 設定単位キー
     * @param schNo スケジュールNo
     * @return 作業が存在した場合：true 存在しない場合:false
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NoPrimaryException パレット,対象ステーションが一意に決定できないとき
     * @throws LockTimeOutException パレット情報のロックタイムアウト
     * @throws NotFoundException 更新対象データが見つからない
     * @throws InvalidDefineException パラメータが不正
     * @throws RouteException 搬送ルートなし
     * @throws ScheduleException システム定義異常
     * @throws DataExistsException 作業,搬送情報がすでに登録済み
     */
    protected boolean createInventryInfo(Stock[] targetStocks, Station wkStation, AsrsInParameter param,
            String settingUnitKey, String schNo)
            throws ReadWriteException,
                NoPrimaryException,
                LockTimeOutException,
                NotFoundException,
                InvalidDefineException,
                RouteException,
                ScheduleException,
                DataExistsException
    {
        // シーケンスハンドラ
        WMSSequenceHandler seqHandler = getSeqHandler();
        // エリアコントローラー
        AreaController areaCtlr = getAreaCtlr();
        // ルートコントローラー
        RouteController routeCtlr = getRouteCtlr();
        // existent flag
        boolean inventFlag = false;

        for (Stock targetStock : targetStocks)
        {
            String pLId = targetStock.getPalletId();

            if (!_savePLId.equals(pLId))
            {
                // get carry key, work number, current station no
                _carryKey = seqHandler.nextCarryKey();
                // 作業No
                String workNo = seqHandler.nextWorkNo();
                // 棚No
                String asRetrLocation = areaCtlr.toAsrsLocation(param.getAreaNo(), targetStock.getLocationNo());

                // get pallet date from pallet ID
                Pallet tPallet = getPallet(pLId, true);
                // check pallet and route
                checkPalletRoute(tPallet, wkStation, routeCtlr);

                // create CarryInfosearchkey
                CarryInfo ci =
                        setCarryInfo(_carryKey, workNo, asRetrLocation, schNo, tPallet,
                                routeCtlr.getDestStation().getStationNo());

                // regist carry info
                createAsrsRetrievalData(tPallet, ci);
                // update saved pallet ID
                _savePLId = pLId;

                inventFlag = true;
            }

            // create work info
            String wicJobNo = seqHandler.nextWorkInfoCollectJobNo();

            WorkInfo workInfo = setWorkInfo(targetStock, _carryKey, settingUnitKey, wicJobNo);

            // regist work info
            createWorkInfo(param, workInfo);

        } // end for loop of Stock entities

        return inventFlag;
    }

    /**
     * 設定単位キーを元に、入出庫作業情報を検索を行い、作業リスト情報を作成します。<br>
     * <ul>
     * 以下のDB情報を検索します。
     * <li>AS/RS搬送情報
     * <li>出荷先マスタ
     * <li>商品マスタ
     * <li>理由区分マスタ
     * <li>ログインユーザ
     * </ul>
     *
     * @param key 設定単位キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     * @throws ScheduleException 作業情報、在庫情報を検索できなかったときスローされます。
     */
    protected void createWorkListByWorkInfo(String key)
            throws ReadWriteException,
                DataExistsException,
                ScheduleException
    {
        WorkInfoHandler wih = new WorkInfoHandler(getConnection());
        WorkInfoSearchKey wkey = new WorkInfoSearchKey();

        // 取得項目一覧の作成
        FieldName[] collects = {
            WorkInfo.JOB_NO,
            CarryInfo.CARRY_KEY,
            WorkInfo.SETTING_UNIT_KEY,
            WorkInfo.COLLECT_JOB_NO,
            WorkInfo.JOB_TYPE,
            WorkInfo.PLAN_UKEY,
            WorkInfo.STOCK_ID,
            CarryInfo.PALLET_ID,
            WorkInfo.PLAN_DAY,
            WorkInfo.CONSIGNOR_CODE,
            Consignor.CONSIGNOR_NAME,
            WorkInfo.CUSTOMER_CODE,
            Customer.CUSTOMER_NAME,
            WorkInfo.SHIP_TICKET_NO,
            WorkInfo.SHIP_LINE_NO,
            WorkInfo.SHIP_BRANCH_NO,
            WorkInfo.BATCH_NO,
            WorkInfo.ORDER_NO,
            WorkInfo.PLAN_AREA_NO,
            WorkInfo.PLAN_LOCATION_NO,
            WorkInfo.ITEM_CODE,
            Item.ITEM_NAME,
            Item.JAN,
            Item.ITF,
            Item.BUNDLE_ITF,
            Item.ENTERING_QTY,
            Item.BUNDLE_ENTERING_QTY,
            WorkInfo.PLAN_LOT_NO,
            WorkInfo.PLAN_QTY,
            WorkInfo.REASON_TYPE,
            Reason.REASON_NAME,
            CarryInfo.PRIORITY,
            CarryInfo.RETRIEVAL_STATION_NO,
            CarryInfo.RETRIEVAL_DETAIL,
            CarryInfo.WORK_NO,
            CarryInfo.SOURCE_STATION_NO,
            CarryInfo.DEST_STATION_NO,
            CarryInfo.SCHEDULE_NO,
            CarryInfo.END_STATION_NO,
            WorkInfo.USER_ID,
            WorkInfo.TERMINAL_NO,
        };

        // 取得フィールドのセット
        for (FieldName fld : collects)
        {
            wkey.setCollect(fld);
        }

        // 作業リストと列名が異なる値の取得を追加
        wkey.setCollect(Com_loginuser.USERNAME, "", WorkList.USER_NAME);

        // 検索条件のセット
        wkey.setSettingUnitKey(key);

        // 結合条件のセット
        wkey.setJoin(WorkInfo.CONSIGNOR_CODE, Consignor.CONSIGNOR_CODE);
        wkey.setJoin(WorkInfo.CONSIGNOR_CODE, Item.CONSIGNOR_CODE);
        wkey.setJoin(WorkInfo.ITEM_CODE, Item.ITEM_CODE);
        wkey.setJoin(WorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        wkey.setJoin(WorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");
        wkey.setJoin(WorkInfo.REASON_TYPE, "", Reason.REASON_TYPE, "(+)");
        wkey.setJoin(WorkInfo.SYSTEM_CONN_KEY, CarryInfo.CARRY_KEY);

        wkey.setJoin(WorkInfo.USER_ID, "", Com_loginuser.USERID, "(+)");

        // ソート
        wkey.setStockIdOrder(true);
        wkey.setJobNoOrder(true);

        // 検索実行
        Entity[] readEnts = wih.find(wkey);
        if (null == readEnts || readEnts.length == 0)
        {
            // 見つからなかった場合は、エラー
            throw new ScheduleException();
        }

        WorkListHandler wlh = new WorkListHandler(getConnection());
        String cname = getCallerName();

        String stock_id = "";
        int stock_qty = 0;
        int alloc_qty = 0;
        int work_qty = 0;
        for (Entity readEnt : readEnts)
        {
            WorkList wlEnt = new WorkList();
            wlEnt.setValueMap(readEnt.getValueMap());

            // 在庫情報のセット
            if (WorkList.JOB_TYPE_RETRIEVAL.equals(wlEnt.getJobType()) ||
                    WorkList.JOB_TYPE_NOPLAN_RETRIEVAL.equals(wlEnt.getJobType()))
            {
                // 出庫、予定外出庫の場合
                if (StringUtil.isBlank(stock_id) || !stock_id.equals(wlEnt.getStockId()))
                {
                    stock_id = wlEnt.getStockId();

                    Stock stk = getStock(stock_id);
                    if (stk == null)
                    {
                        // AS/RS作業の場合、在庫情報が見つからなかったらエラー
                        throw new ScheduleException();
                    }

                    stock_qty = stk.getStockQty();
                    // 作業前の引当可能数
                    alloc_qty = getTotalPlanQty(key, wlEnt.getStockId()) + stk.getAllocationQty();
                }
                else
                {
                    // 引当可能数の減算
                    alloc_qty -= work_qty;
                }

                work_qty = wlEnt.getPlanQty();

                // 在庫数、引当可能数のセット
                wlEnt.setAllocationQty(alloc_qty);
                wlEnt.setStockQty(stock_qty);
            }
            else if (WorkList.JOB_TYPE_STORAGE.equals(wlEnt.getJobType()) ||
                    WorkList.JOB_TYPE_NOPLAN_STORAGE.equals(wlEnt.getJobType()) ||
                    WorkList.JOB_TYPE_RESTORING.equals(wlEnt.getJobType()))
            {
                // 入庫、予定外入庫、再入庫の場合
                // 在庫数、引当可能数に0をセット
                wlEnt.setAllocationQty(0);
                wlEnt.setStockQty(0);
            }

            wlEnt.setRegistDate(new SysDate());
            wlEnt.setRegistPname(cname);

            // insert
            wlh.create(wlEnt);
        }
    }

    /**
     * 同一在庫を対象とした作業の合計予定数を取得します。
     *
     * @param setting_ukey
     * @param stock_id
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected int getTotalPlanQty(String setting_ukey, String stock_id)
            throws ReadWriteException
    {
        WorkInfoSearchKey wkey = new WorkInfoSearchKey();

        wkey.setSettingUnitKey(setting_ukey);
        wkey.setStockId(stock_id);
        wkey.setStatusFlag(WorkInfo.STATUS_FLAG_DELETE, "!=");
        wkey.setPlanQtyCollect("SUM");

        WorkInfo[] work = (WorkInfo[])(new WorkInfoHandler(getConnection())).find(wkey);

        return work[0].getPlanQty();
    }

    /**
     * 在庫IDから在庫情報を取得します。<br>
     * 対象データが無い場合はnullを返します。
     *
     * @param stock_id
     * @throws ReadWriteException データベースエラーの場合にスローされます。
     */
    protected Stock getStock(String stock_id)
            throws ReadWriteException
    {
        StockSearchKey skey = new StockSearchKey();

        skey.setStockId(stock_id);

        Stock[] stk = (Stock[])(new StockHandler(getConnection())).find(skey);

        if (stk == null || stk.length == 0)
        {
            return null;
        }

        return stk[0];
    }

    /**
     * ステーションNo.からステーションインスタンスを取得します。<br>
     * 2回目からはキャッシュされた情報を返します。
     *
     * @param stNo ステーションNo.
     * @return ステーション
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException ステーション定義異常
     */
    protected Station getStation(String stNo)
            throws ReadWriteException,
                ScheduleException
    {
        if (null == _stationMap)
        {
            // cache for zone
            _stationMap = new HashMap<String, Station>();
        }
        Station st = _stationMap.get(stNo);
        if (null == st)
        {
            try
            {
                st = StationFactory.makeStation(getConnection(), stNo);
                _stationMap.put(stNo, st);
            }
            catch (ReadWriteException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                throw new ScheduleException("Invalid Station master define.");
            }
        }
        return st;
    }

    /**
     * ステーションNo.からステーションインスタンスを取得します。<br>
     * 2回目からはキャッシュされた情報を返します。<br>
     * 全体作業場がステーションNo.に指定されていた場合、エリアに紐付くステーションを返します。
     *
     * @param stNo ステーションNo.
     * @param areaNo エリアNo.
     * @return ステーション
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException ステーション定義異常
     */
    protected Station getStation(String stNo, String areaNo)
            throws ReadWriteException,
                ScheduleException
    {
        if (null == _stationMap)
        {
            // cache for zone
            _stationMap = new HashMap<String, Station>();
        }
        Station st = _stationMap.get(stNo);
        if (null == st)
        {
            try
            {
                // 全体作業場/全ステーションの場合
                if (WmsParam.ALL_STATION.equals(stNo))
                {
                    StationHandler sth = new StationHandler(getConnection());
                    StationSearchKey key = new StationSearchKey();

                    key.setKey(Area.AREA_NO, areaNo);
                    key.setSendable(Station.SENDABLE_TRUE);

                    key.setJoin(Station.WH_STATION_NO, Area.WHSTATION_NO);

                    key.setStationNoCollect();

                    Station[] sts = (Station[])sth.find(key);

                    List<String> stList = new ArrayList<String>();
                    for (Station station : sts)
                    {
                        stList.add(station.getStationNo());
                    }

                    String[] stations = new String[stList.size()];
                    stList.toArray(stations);

                    // 取得したステーションでWorkPlaceを作成
                    st = new WorkPlace(WmsParam.ALL_STATION, stations);

                }
                else
                {
                    st = StationFactory.makeStation(getConnection(), stNo);
                }

                _stationMap.put(stNo, st);
            }
            catch (ReadWriteException e)
            {
                throw e;
            }
            catch (Exception e)
            {
                throw new ScheduleException("Invalid Station master define.");
            }
        }
        return st;
    }

    /**
     * 入庫搬送情報作成<br>
     * パラメータの搬送情報、ルートコントローラより入庫用の搬送情報を登録する。<br>
     * <br>
     *
     * @param newCarry 搬送情報エンティティ
     * <ol>
     * 以下の項目を参照します。
     * <li>搬送キー
     * <li>パレットID
     * <li>作業種別
     * <li>出庫ロケーションNo.
     * </ol>
     * @param rc 搬送情報コントローラ
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     * @throws DataExistsException 登録データが既に存在した場合にスローされます。
     */
    protected void createStorageCarryInfo(RouteController rc, CarryInfo newCarry)
            throws ReadWriteException,
                DataExistsException
    {
        WMSSequenceHandler seqHandler = getSeqHandler();

        String workNo = seqHandler.nextStorageWorkNo();
        String schNo = seqHandler.nextScheduleNo();

        Station srcStation = rc.getSrcStation();
        // 搬送状態の決定
        String opDisp = srcStation.getOperationDisplay();
        boolean compBtn = SystemDefine.OPERATION_DISPLAY_INSTRUCTION.equals(opDisp);

        String cmdStatus = (compBtn) ? SystemDefine.CMD_STATUS_ALLOCATION
                                    : SystemDefine.CMD_STATUS_START;

        Station destStation = rc.getDestStation();
        // アイルステーションNo.の決定

        String aisleStNo = (destStation instanceof Shelf) ? destStation.getParentStationNo()
                                                         : srcStation.getAisleStationNo();

        String pgname = getCallerName();

        // パラメータの搬送情報の編集
        newCarry.setCmdStatus(cmdStatus); // 搬送状態

        String priority = SystemDefine.PRIORITY_EMERGENCY; // 優先区分(緊急)
        if (!SystemDefine.WORK_TYPE_DIRECT_TRAVEL.equals(newCarry.getWorkType()))
        {
            // 直行作業で無い場合、WMSParamより入庫時の優先区分をセット
            priority = (WmsParam.STORAGE_PRIORITY_NORMAL) ? SystemDefine.PRIORITY_NORMAL // 通常
                                                          : SystemDefine.PRIORITY_EMERGENCY; // 緊急
        }
        newCarry.setPriority(priority); // 優先区分
        newCarry.setRestoringFlag(SystemDefine.RESTORING_FLAG_NOT_SAME_LOC); // 最入庫フラグ

        if (rc.getDestStation().getStationNo().equals(rc.getSrcStation().getRejectStationNo()))
        {
            newCarry.setCarryFlag(SystemDefine.CARRY_FLAG_DIRECT_TRAVEL); // 搬送区分
        }
        else
        {
            newCarry.setCarryFlag(SystemDefine.CARRY_FLAG_STORAGE); // 搬送区分
        }

        if (SystemDefine.WORK_TYPE_DIRECT_TRAVEL.equals(newCarry.getWorkType()))
        {
            newCarry.setCarryFlag(SystemDefine.CARRY_FLAG_DIRECT_TRAVEL); // 搬送区分
            // アイルステーションNo.は空白
            aisleStNo = "";
        }

        newCarry.setRetrievalDetail(SystemDefine.RETRIEVAL_DETAIL_UNKNOWN); // 出庫指示詳細

        newCarry.setWorkNo(workNo); // 作業No. OK??
        newCarry.setSourceStationNo(srcStation.getStationNo()); // ルートコントローラの搬送元ステーション
        newCarry.setDestStationNo(destStation.getStationNo()); // ルートコントローラの搬送先ステーション
        newCarry.setCancelRequest(SystemDefine.CANCEL_REQUEST_UNDEMAND); // キャンセル要求区分

        newCarry.setScheduleNo(schNo); // スケジュールNo.

        if (!StringUtil.isBlank(aisleStNo))
        {
            newCarry.setAisleStationNo(aisleStNo); // アイルステーションNo.
        }
        newCarry.setEndStationNo(destStation.getStationNo()); // ルートコントローラの搬送先ステーション

        newCarry.setRegistPname(pgname); // 登録処理名
        newCarry.setRegistDate(new SysDate());
        newCarry.setLastUpdatePname(pgname); // 最終更新処理名
        newCarry.setLastUpdateDate(new SysDate());

        new CarryInfoHandler(getConnection()).create(newCarry);
    }

    /**
     * 入庫パレットインスタンス作成<br>
     * パラメータのエリアNo.、ステーションNo.、ハードゾーンよりステーション情報、ハードゾーン情報を検索し。<br>
     * パレットインスタンスを入庫予約状態で作成する。<br>
     * DBの更新は行わない。<br>
     * <br>
     *
     * @param palletId パレットID<br>
     *
     * @param param ASRS入力パラメータ
     * <ol>
     * 以下の項目が参照されます。
     * <li>エリアNo.
     * <li>ステーションNo.
     * <li>ゾーンNo.
     * </ol>
     *
     * @return パレット情報エンティティ
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     * @throws ScheduleException 予期しない例外が発生した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在した場合にスローされます。
     */
    protected Pallet createStoragePallet(String palletId, AsrsInParameter param)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException
    {
        String whStNo = getWhStationNo(param);
        String stNo = param.getStationNo();
        String hardZone = StringUtil.isBlank(param.getZoneNo()) ? SystemDefine.HARD_ZONE_FREE
                                                                    : param.getZoneNo();
        String softZone = StringUtil.isBlank(param.getSoftZoneNo()) ? SystemDefine.SOFT_ZONE_FREE
                                                                   : param.getSoftZoneNo();

        // ハードゾーン情報の検索とキャッシュ
        HardZone hz = getHardZone(hardZone);
        int height = SystemDefine.HEIGHT_FREE;
        if (null == hz)
        {
            if (!SystemDefine.HARD_ZONE_FREE.equals(hardZone))
            {
                throw new ScheduleException("No hard zone found for zone ID: " + hardZone);
            }
        }
        else
        {
            height = hz.getHeight();
        }

        String pgname = getCallerName();

        // パレット情報の編集
        Pallet newPlt = new Pallet();

        newPlt.setPalletId(palletId); // パレットID
        newPlt.setCurrentStationNo(stNo);

        newPlt.setWhStationNo(whStNo); // 倉庫ステーション
        newPlt.setStatusFlag(SystemDefine.PALLET_STATUS_STORAGE_PLAN); // 在庫状態
        newPlt.setAllocationFlag(SystemDefine.ALLOCATION_FLAG_ALLOCATED); // 引当状態
        newPlt.setEmptyFlag(SystemDefine.EMPTY_FLAG_NORMAL); // 空パレット状態

        newPlt.setHeight(height); // 荷高
        newPlt.setWidth(param.getWidth()); // 荷幅
        newPlt.setSoftZoneId(softZone); // ソフトゾーン
        newPlt.setBcrData(""); // バーコード情報

        newPlt.setRegistPname(pgname); // 登録処理名
        newPlt.setRegistDate(new SysDate());
        newPlt.setLastUpdatePname(pgname); // 最終更新処理名
        newPlt.setLastUpdateDate(new SysDate());

        return newPlt;
    }

    /**
     * 直行パレットインスタンス作成<br>
     * ハードゾーン、ソフトゾーン情報は何もセットせず、入庫予約状態で作成する。<br>
     * DBの更新は行わない。<br>
     * <br>
     *
     * @param palletId パレットID
     * @param rc ルートコントローラー
     * <ol>
     * 以下の項目が参照されます。
     * <li>搬送元ステーションNo.
     * </ol>
     * @param param ASRS入力パラメータ
     * <ol>
     * 以下の項目が参照されます。
     * <li>エリアNo.
     * </ol>
     *
     * @return パレット情報エンティティ
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     */
    protected Pallet createDirectTransferPallet(String palletId, RouteController rc, AsrsInParameter param)
            throws ReadWriteException
    {
        Station srcSt = rc.getSrcStation();
        String whStNo = getWhStationNo(param);
        String pgname = getCallerName();

        // パレット情報の編集
        Pallet newPlt = new Pallet();

        newPlt.setPalletId(palletId); // パレットID
        newPlt.setCurrentStationNo(srcSt.getStationNo()); // 搬送元ステーション

        newPlt.setWhStationNo(whStNo); // 倉庫ステーション
        newPlt.setStatusFlag(SystemDefine.PALLET_STATUS_STORAGE_PLAN); // 在庫状態
        newPlt.setAllocationFlag(SystemDefine.ALLOCATION_FLAG_ALLOCATED); // 引当状態
        newPlt.setEmptyFlag(SystemDefine.EMPTY_FLAG_NORMAL); // 空パレット状態

        newPlt.setBcrData(""); // バーコード情報

        newPlt.setRegistPname(pgname); // 登録処理名
        newPlt.setRegistDate(new SysDate());
        newPlt.setLastUpdatePname(pgname); // 最終更新処理名
        newPlt.setLastUpdateDate(new SysDate());

        return newPlt;
    }

    /**
     * ゾーンIDに対応するゾーンを返します。<br>
     * 初回の読み込みでゾーンをすべてキャッシュします。
     *
     * @param zoneId 検索対象
     * @return HardZone ゾーン
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     */
    protected HardZone getHardZone(String zoneId)
            throws ReadWriteException
    {
        if (null == _zoneMap)
        {
            // cache for zone
            _zoneMap = new HashMap<String, HardZone>();
            HardZoneHandler hzoneHandler = new HardZoneHandler(getConnection());
            HardZone[] hzones = (HardZone[])hzoneHandler.find(new HardZoneSearchKey());
            for (HardZone hz : hzones)
            {
                _zoneMap.put(hz.getHardZoneId(), hz);
            }
        }
        return _zoneMap.get(zoneId);
    }

    /**
     * 作業情報の登録の登録を行います。<br>
     *
     *
     * @param param 画面からの入力パラメータ
     * <ol>
     * 以下の項目が参照されます。
     * <li>USER ID
     * <li>TERMINAL NUMBER
     * </ol>
     *
     * @param newWork 登録する作業情報
     * <ul>
     * 以下の項目が上書きされます。
     * <li>JOB NO
     * <li>COLLECT JOB NO
     * <li>STATUS FLAG
     * <li>ユーザ情報
     * </ul>
     *
     * @throws ReadWriteException データベースアクセスエラー
     * @throws DataExistsException 作業情報登録済み
     * @throws ScheduleException システム定義不整合
     */
    protected void createWorkInfo(AsrsInParameter param, WorkInfo newWork)
            throws ReadWriteException,
                DataExistsException,
                ScheduleException
    {
        // getting job numbers
        WMSSequenceHandler seqh = getSeqHandler();
        String jobNo = seqh.nextWorkInfoJobNo();

        // fix work info
        WorkInfo cwi = (WorkInfo)newWork.clone();

        cwi.setJobNo(jobNo);
        cwi.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
        cwi.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS);
        cwi.setPlanDay(getWSysCtlr().getWorkDay());
        cwi.setUserId(param.getUserId());
        cwi.setTerminalNo(param.getTerminalNo());
        cwi.setReasonType(param.getReasonType());

        String pgname = getCallerName();
        cwi.setRegistPname(pgname);
        cwi.setRegistDate(new SysDate());
        cwi.setLastUpdatePname(pgname);
        cwi.setLastUpdateDate(new SysDate());

        // 作業情報の登録
        (new WorkInfoHandler(getConnection())).create(cwi);
    }

    /**
     * 搬送情報確定処理をおこないます。
     *
     * @param schNo スケジュールNo.
     * @param settingStNo 画面設定された行き先ステーション
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException 登録済みの情報が見つからなかったとき
     * @throws InvalidDefineException ステーション定義が不正
     * @throws NoPrimaryException パレットIDに対応するパレットが複数検索された
     * @throws OperatorException ルートが確保できなかったときスローされます。<br>
     * 発生した行番号については、1がセットされます。
     */
    protected void decideCarryInfo(String schNo, String settingStNo)
            throws ReadWriteException,
                ScheduleException,
                InvalidDefineException,
                NoPrimaryException,
                OperatorException
    {
        // find carry info
        CarryInfoSearchKey cikey = new CarryInfoSearchKey();
        cikey.setPalletIdCollect();
        cikey.setCarryKeyCollect();
        cikey.setDestStationNoCollect();
        cikey.setSourceStationNoCollect();

        cikey.setScheduleNo(schNo);

        CarryInfoHandler carryInfoH = (new CarryInfoHandler(getConnection()));
        CarryInfo[] carries = (CarryInfo[])carryInfoH.find(cikey);

        if (ArrayUtil.isEmpty(carries))
        {
            // throw exception if no carry found.
            throw new ScheduleException("No carry info found for schedule number:" + schNo);
        }

        try
        {
            StockHandler stockH = new StockHandler(getConnection());
            // process for each carry info
            for (CarryInfo carry : carries)
            {
                String pltId = carry.getPalletId();

                // get Stock info
                StockSearchKey sskey = new StockSearchKey();
                sskey.setAllocationQtyCollect("SUM");
                sskey.setLocationNoCollect();

                sskey.setPalletId(pltId);

                sskey.setPalletIdGroup();
                sskey.setLocationNoGroup();

                Stock retrStock = (Stock)stockH.findPrimary(sskey);

                if (null == retrStock)
                {
                    // throw exception if no stock found.
                    throw new ScheduleException("No stock found for pallet id:" + pltId);
                }

                // check alloc qty
                boolean unitRetr = (0 == retrStock.getAllocationQty());

                // 搬送情報について最適なルートを再検索
                Pallet plt = getPallet(pltId, false);

                Station checkDest = null;
                if (WmsParam.ALL_STATION.equals(settingStNo))
                {
                    // 全体作業場/全ステーションの場合は_stationMapから取得
                    checkDest = _stationMap.get(settingStNo);
                }
                else
                {
                    checkDest = StationFactory.makeStation(getConnection(), settingStNo);
                }

                RouteController routeCtlr = getRouteCtlr();
                boolean routeFound = routeCtlr.retrievalDetermin(plt, checkDest, true, !unitRetr, unitRetr);
                if (!routeFound)
                {
                    // 最適なルートがないときは作業場の中から再検索
                    routeFound = routeCtlr.retrievalDetermin(plt, checkDest, true);
                    if (!routeFound)
                    {
                        // 搬送ルートなし
                        throw routeOpException(0, routeCtlr.getRouteStatus());
                    }
                }
                Station destSt = routeCtlr.getDestStation();

                // set detail of retrieval
                String retrDetail = "";

                if (unitRetr)
                {
                    // 払い出し可能チェック
                    boolean removable = destSt.isRemove();
                    retrDetail = (removable) ? SystemDefine.RETRIEVAL_DETAIL_UNIT
                                            : SystemDefine.RETRIEVAL_DETAIL_PICKING;
                }
                else
                {
                    // ユニット出庫専用チェック
                    boolean unitOnly = destSt.isUnitOnly();

                    retrDetail = (unitOnly) ? SystemDefine.RETRIEVAL_DETAIL_UNIT
                                           : SystemDefine.RETRIEVAL_DETAIL_PICKING;
                }

                // set re-storage flag
                String reStore = "";
                if (SystemDefine.RETRIEVAL_DETAIL_UNIT.equals(retrDetail))
                {
                    reStore = SystemDefine.RESTORING_FLAG_NOT_SAME_LOC;
                }
                else
                {
                    StationController stCtl = new StationController(getConnection(), getCaller());
                    if (stCtl.isReStoringEmptyLocationSearch(destSt.getStationNo()))
                    {
                        reStore = SystemDefine.RESTORING_FLAG_NOT_SAME_LOC;
                    }
                    else
                    {
                        // 再入庫搬送指示送信有無
                        String resCmd = destSt.getRestoringInstruction();
                        boolean isAWC = SystemDefine.RESTORING_INSTRUCTION_AWC_STORAGE_SEND.equals(resCmd);

                        reStore = (isAWC) ? SystemDefine.RESTORING_FLAG_NOT_SAME_LOC
                                         : SystemDefine.RESTORING_FLAG_SAME_LOC;
                    }
                }

                // update carry info
                String carryKey = carry.getCarryKey();
                CarryInfoAlterKey ciakey = new CarryInfoAlterKey();

                ciakey.updateDestStationNo(destSt.getStationNo());
                ciakey.updateRestoringFlag(reStore);
                ciakey.updateRetrievalDetail(retrDetail);
                ciakey.updateLastUpdatePname(getCallerName());

                ciakey.setCarryKey(carryKey);

                carryInfoH.modify(ciakey);

            } // end for loop of carry infos.
        }
        catch (NoPrimaryException e)
        {
            // throw exception if no stock found.
            throw new ScheduleException("Stock or Pallet is not unique");
        }
        catch (NotFoundException e)
        {
            throw new ScheduleException("No carry info or No station found");
        }
        catch (LockTimeOutException e)
        {
            // never occurs.
        }
    }

    /**
     * 集約入庫作業開始<br>
     * パラメータの対象データキー項目を集約して入庫開始処理を行う。<br>
     * <br>
     * @param tgtWorks 対象データキー項目
     * <ol>
     * 以下の項目を参照します。
     * <li>作業No.
     * <li>荷主コード
     * <li>商品コード
     * <li>予定エリア
     * <li>予定棚
     * <li>予定ロットNo.
     * <li>予定数
     * </ol>
     *
     * @param newWorkInfo 入出庫作業更新内容
     * <ol>
     * 以下の項目を参照します。
     * <li>設定単位キー
     * <li>集約作業No.
     * <li>システム接続キー
     * <li>予定エリア
     * <li>予定棚
     * <li>予定ロットNo.
     * <li>予定数
     * </ol>
     *
     * @param pallet 対象パレット
     *
     * @param ui WMSユーザ情報
     *
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)な場合にスローされます。
     * @throws ScheduleException 予期しない例外が発生した場合にスローされます。
     * @throws DataExistsException 登録データが既に存在した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在した場合にスローされます。
     * @throws NotFoundException 他端末で更新済み
     * @throws OperatorException オペレータ処理でエラー発生した場合にスローされます。<br>
     * <br>エラーコード
     * <ul>
     * <li>10 : 他端末で更新された
     * </ul>
     */
    protected void startCollectStorage(WorkInfo[] tgtWorks, WorkInfo newWorkInfo, Pallet pallet, WmsUserInfo ui,
            int reasonType)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                OperatorException,
                ScheduleException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException
    {
        if (ArrayUtil.isEmpty(tgtWorks))
        {
            throw new InvalidDefineException("null or zero length parameter");
        }

        // prepare controllers
        StockController stockCtrl = getStockCtlr();
        AsWorkInfoController wkInfoCtrl = getAsWorkInfoCtlr();
        StoragePlanController spCtlr = getStorgaePlanCtlr();

        // get first work from parameter
        WorkInfo firstWork = tgtWorks[0];

        // 同一在庫存在チェックを行う
        StockSearchKey sskey = new StockSearchKey();
        StockHandler stockH = new StockHandler(getConnection());
        sskey.setPalletId(pallet.getPalletId());
        sskey.setConsignorCode(firstWork.getConsignorCode());
        sskey.setItemCode(firstWork.getItemCode());
        sskey.setAreaNo(newWorkInfo.getPlanAreaNo());
        sskey.setLocationNo(newWorkInfo.getPlanLocationNo());
        sskey.setLotNo(newWorkInfo.getPlanLotNo());

        Stock stock = new Stock();
        String stockId;
        int newPlanQty = newWorkInfo.getPlanQty();

        Stock addTargetStock = (Stock)stockH.findPrimary(sskey);
        if (null == addTargetStock)
        {
            // 対象在庫の編集を行う
            stock.setPalletId(pallet.getPalletId()); // パレットID

            stock.setConsignorCode(firstWork.getConsignorCode()); // 荷主コード
            stock.setItemCode(firstWork.getItemCode()); // 商品コード

            stock.setAreaNo(newWorkInfo.getPlanAreaNo()); // エリア
            stock.setLocationNo(newWorkInfo.getPlanLocationNo()); // 棚
            stock.setLotNo(newWorkInfo.getPlanLotNo()); // ロットNo.

            stock.setPlanQty(newPlanQty); // 入庫予定数

            // 新規入庫予約
            stockId = stockCtrl.initStorageReserve(stock, reasonType);
        }
        else
        {
            stock.setPlanQty(newPlanQty);

            // 積増入庫予約
            stockId = stockCtrl.addStorageReserve(addTargetStock, stock);
        }

        // 予定数の分配
        WorkInfo updWorkInfo = (WorkInfo)newWorkInfo.clone();
        int restPlanQty = newPlanQty; // 残予定数 (更新作業情報の予定数)

        for (WorkInfo tgtWork : tgtWorks)
        {
            // どちらか小さい方を予定数とする (残予定数:入出庫作業情報.予定数)
            int cwPlanQty = tgtWork.getPlanQty();
            int planQty = Math.min(restPlanQty, cwPlanQty);

            if (0 == planQty)
            {
                // all of plans process completed
                break;
            }

            // 残予定数の減算
            restPlanQty -= planQty;

            updWorkInfo.setPlanQty(planQty); // 予定数
            updWorkInfo.setStockId(stockId); // 在庫ID

            // 入出庫作業情報コントローラのAS/RS作業情報開始
            wkInfoCtrl.startAsrsWorkInfo(tgtWork, updWorkInfo, ui);

            // 入庫予定情報コントローラの予定情報開始
            spCtlr.startPlan(tgtWork.getPlanUkey());
        }
    }

    /**
     * 予定外入庫作業開始<br>
     * パラメータの項目に該当するデータに対して予定外入庫開始処理を行う。<br>
     * <br>
     *
     * @param param ASRS入力パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>荷主コード
     * <li>商品コード
     * <li>ユーザ情報
     * <li>ロットNo.
     * <li>作業区分
     * <br><br>
     * 以下はマスタパッケージなしの場合
     * <li>商品名称
     * <li>JANコード
     * <li>ケース入数
     * <li>ボール入数
     * <li>ケースITF
     * <li>ボールITF
     * </ol>
     *
     * @param newWork 入出庫作業更新内容
     * <ol>
     * 以下の項目を参照します。
     * <li>設定単位キー
     * <li>集約作業No.
     * <li>システム接続キー
     * <li>予定エリア
     * <li>予定棚
     * <li>予定ロットNo.
     * <li>予定数
     * </ol>
     * @param pallet 対象パレット
     *
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)な場合にスローされます。
     * @throws ScheduleException 予期しない例外が発生した場合にスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在した場合にスローされます。
     * @throws DataExistsException 登録データが既に存在した場合にスローされます。
     * @throws OperatorException オペレータ処理でエラー発生した場合にスローされます。<br>
     * <br>エラーコード
     * <ul>
     * <li>10 : 他端末で更新された
     * <li>11 : 他端末作業中
     * <li>12 : 作業完了済み
     * </ul>
     */
    protected void startNoPlanStorage(AsrsInParameter param, WorkInfo newWork, Pallet pallet)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                OperatorException,
                ScheduleException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        // prepare controller, handler
        StockController stockCtlr = getStockCtlr();

        // マスタパッケージを導入していない場合
        autoCreateMaster(param);

        // 在庫登録(予約)処理
        String consignorCode = param.getConsignorCode();
        String itemCode = param.getItemCode();

        Stock stock = new Stock();

        stock.setPalletId(pallet.getPalletId());
        stock.setConsignorCode(consignorCode); // 荷主コード
        stock.setItemCode(itemCode); // 商品コード
        stock.setAreaNo(newWork.getPlanAreaNo()); // エリア
        stock.setLocationNo(newWork.getPlanLocationNo()); // 棚
        stock.setLotNo(param.getLotNo()); // ロットNo.
        stock.setPlanQty(newWork.getPlanQty()); // 予定入庫数

        String stockId = stockCtlr.initStorageReserve(stock, param.getReasonType());

        // 入出庫作業情報登録
        WorkInfo npWork = (WorkInfo)newWork.clone();

        npWork.setConsignorCode(consignorCode);
        npWork.setItemCode(itemCode);
        npWork.setStockId(stockId); // 在庫ID

        createWorkInfo(param, npWork);
    }

    /**
     * 再入庫作業開始<br>
     * パラメータの再入庫予定に対して再入庫開始処理を行う。<br>
     * <br>
     * @param tgtPlan 対象データキー項目
     * <ol>
     * 以下の項目を参照します。
     * <li>予定一意キー
     * </ol>
     *
     * @param newWork 入出庫作業更新内容
     * <ol>
     * 以下の項目を参照します。
     * <li>設定単位キー
     * <li>集約作業No.
     * <li>システム接続キー
     * <li>予定エリア
     * <li>予定棚
     * </ol>
     *
     * @param pallet 対象パレット
     * @param ui WMSユーザ情報
     * @param reasonType 理由区分
     *
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws ScheduleException 予期しない例外が発生した場合にスローされます。
     * @throws DataExistsException 登録データが既に存在した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在した場合にスローされます。
     * @throws NotFoundException 他端末で更新済み
     * @throws OperatorException オペレータ処理でエラー発生した場合にスローされます。<br>
     * <br>エラーコード
     * <ul>
     * <li>10 : 他端末で更新された
     * </ul>
     */
    protected void startReStoring(ReStoringPlan tgtPlan, WorkInfo newWork, Pallet pallet, WmsUserInfo ui,
            int reasonType)
            throws ReadWriteException,
                LockTimeOutException,
                OperatorException,
                ScheduleException,
                DataExistsException,
                NoPrimaryException,
                NotFoundException
    {
        AsWorkInfoController wkInfoCtrl = getAsWorkInfoCtlr();

        // 再入庫開始ロック
        WorkInfo keywork = new WorkInfo();
        keywork.setPlanUkey(tgtPlan.getPlanUkey());

        WorkInfo tgtWork = wkInfoCtrl.lockRestoringStart(keywork);

        if (tgtWork == null)
        {
            wkInfoCtrl.checkReStoringStart(keywork);
        }

        // prepare controllers
        StockController stockCtrl = getStockCtlr();

        // 在庫登録(予約)処理
        String consignorCode = tgtWork.getConsignorCode();
        String itemCode = tgtWork.getItemCode();

        Stock stock = new Stock();

        stock.setPalletId(pallet.getPalletId());
        stock.setConsignorCode(consignorCode); // 荷主コード
        stock.setItemCode(itemCode); // 商品コード
        stock.setAreaNo(newWork.getPlanAreaNo()); // エリア
        stock.setLocationNo(newWork.getPlanLocationNo()); // 棚
        stock.setLotNo(tgtWork.getPlanLotNo()); // ロットNo.
        stock.setPlanQty(tgtWork.getPlanQty()); // 予定入庫数

        stock.setStorageDay(tgtPlan.getStorageDay()); // 入庫日（予定情報）
        stock.setStorageDate(tgtPlan.getStorageDate()); // 入庫日時（予定情報）
        stock.setRetrievalDay(tgtPlan.getRetrievalDay()); // 出庫日（予定情報）

        String stockId = stockCtrl.initStorageReserve(stock, reasonType);

        WorkInfo updWorkInfo = (WorkInfo)newWork.clone();

        updWorkInfo.setStockId(stockId);

        // 入出庫作業情報コントローラのAS/RS作業情報開始
        wkInfoCtrl.startAsrsWorkInfo(tgtWork, updWorkInfo, ui);

        // 再入庫予定情報開始
        startReStoringPlan(tgtWork.getPlanUkey());
    }

    /**
     * 直行作業開始<br>
     * パラメータの項目に該当するデータに対して直行作業開始処理を行う。<br>
     * <br>
     *
     * @param param ASRS入力パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>荷主コード
     * <li>商品コード
     * <li>ユーザ情報
     * <li>ロットNo.
     * <br><br>
     * 以下はマスタパッケージなしの場合
     * <li>商品名称
     * <li>JANコード
     * <li>ケース入数
     * <li>ボール入数
     * <li>ケースITF
     * <li>ボールITF
     * </ol>
     *
     * @param newWork 入出庫作業更新内容
     * <ol>
     * 以下の項目を参照します。
     * <li>設定単位キー
     * <li>集約作業No.
     * <li>作業区分
     * <li>システム接続キー
     * <li>予定エリア
     * <li>予定棚
     * <li>予定ロットNo.
     * <li>予定数
     * </ol>
     * @param pallet 対象パレット
     *
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws ScheduleException 予期しない例外が発生した場合にスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在した場合にスローされます。
     * @throws DataExistsException 登録データが既に存在した場合にスローされます。
     */
    protected void startDirectTransfer(AsrsInParameter param, WorkInfo newWork, Pallet pallet)
            throws ReadWriteException,
                LockTimeOutException,
                ScheduleException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        // prepare controller, handler
        StockController stockCtlr = getStockCtlr();

        // マスタパッケージを導入していない場合
        autoCreateMaster(param);

        // 在庫登録(予約)処理
        String consignorCode = param.getConsignorCode();
        String itemCode = param.getItemCode();

        Stock stock = new Stock();

        stock.setPalletId(pallet.getPalletId());
        stock.setConsignorCode(consignorCode); // 荷主コード
        stock.setItemCode(itemCode); // 商品コード
        stock.setAreaNo(newWork.getPlanAreaNo()); // エリア
        stock.setLocationNo(""); // 棚
        stock.setLotNo(param.getLotNo()); // ロットNo.
        stock.setPlanQty(newWork.getPlanQty()); // 予定数

        String stockId = stockCtlr.insert(stock, null, null, null, param.getReasonType());

        // 入出庫作業情報登録
        WorkInfo npWork = (WorkInfo)newWork.clone();

        npWork.setConsignorCode(consignorCode);
        npWork.setItemCode(itemCode);
        npWork.setStockId(stockId); // 在庫ID

        createWorkInfo(param, npWork);
    }

    /**
     * 出庫開始処理を行います。<br>
     *
     * @param param 入力パラメータ
     * @param works 対象作業一覧<br>
     * <ol>
     * 以下の項目を参照します。
     * <li>ステーションNo.(ステーションまたは作業場)
     * <li>ユーザ情報 (ハードウエア以外)
     * </ol>
     *
     * @return 設定単位キー
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException パレット情報が見つからない
     * @throws NoPrimaryException 対象ステーションまたはパレットが複数見つかった
     * @throws InvalidDefineException パラメータの内容が不正
     * @throws RouteException 搬送ルートなし
     * @throws OperatorException 未開始の搬送あり
     * @throws NotFoundException 他の端末でデータ更新済み
     * @throws DataExistsException 作業情報登録済み
     * @throws ReadWriteException SQL実行時にエラーが発生した場合に通知します
     */
    protected AsrsOutParameter startRetrieval(AsrsInParameter param, WorkInfo[] works)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException,
                InvalidDefineException,
                RouteException,
                OperatorException,
                NotFoundException,
                DataExistsException,
                ReadWriteException

    {
        // create dest station
        //Station wkStation = getDestStation(param);
        boolean isUnDefinedStation = StringUtil.isBlank(param.getStationNo());
        Station wkStation = null;

        if (!isUnDefinedStation)
        {
            // create dest station
            wkStation = getDestStation(param);
        }

        // ハンドラ
        WMSSequenceHandler seqHandler = getSeqHandler();
        PalletHandler palletH = new PalletHandler(getConnection());
        CarryInfoHandler carryInfoH = new CarryInfoHandler(getConnection());

        // prepare controllers
        RouteController routeCtlr = getRouteCtlr();
        AsWorkInfoController winfoCtlr = getAsWorkInfoCtlr();
        MoveWorkInfoController mwiCtlr = new MoveWorkInfoController(getConnection(), getCaller());
        RetrievalPlanController retrPlanCtlr = new RetrievalPlanController(getConnection(), getCaller());

        // 設定単位キーの採番
        String settingUnitKey = seqHandler.nextWorkInfoSetUkey();

        String coJobNo = seqHandler.nextWorkInfoCollectJobNo();
        String savePlanUKey = "_";
        for (WorkInfo work : works)
        {
            // get pallet info and lock
            PalletSearchKey pskey = new PalletSearchKey();

            String stockId = work.getStockId();
            pskey.setKey(Stock.STOCK_ID, stockId);
            pskey.setJoin(Pallet.PALLET_ID, Stock.PALLET_ID);

            Pallet tPallet = (Pallet)palletH.findPrimary(pskey);
            if (null == tPallet)
            {
                // target pallet already removed
                throw new ScheduleException("No pallet found for StockID:" + stockId);
            }

            // ステーションNo.が未定義の場合
            if (isUnDefinedStation)
            {
                wkStation = getStation(getCarryInfo(work, SystemDefine.CMD_STATUS_START).getDestStationNo());
            }
            boolean routeOK = routeCtlr.retrievalDetermin(tPallet, wkStation, true);
            if (!routeOK)
            {
                throw new RouteException(routeCtlr.getRouteStatus());
            }

            // 出庫時、設定順にて出庫開始するよう処理を追加 start
            // 同一パレットに紐づく搬送で開始されていないものがないかチェック
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT COUNT(*) COUNT FROM DNCARRYINFO ");
            sb.append("WHERE ");
            sb.append(" PALLET_ID = ");
            sb.append(DBFormat.format(tPallet.getPalletId()));
            sb.append(" AND CARRY_KEY != ");
            sb.append(DBFormat.format(work.getSystemConnKey()));
            sb.append(" AND CMD_STATUS = ");
            sb.append(DBFormat.format(CarryInfo.CMD_STATUS_ALLOCATION));
            sb.append(" AND REGIST_DATE < (SELECT REGIST_DATE FROM DNCARRYINFO ");
            sb.append(" WHERE ");
            sb.append(" CARRY_KEY = ");
            sb.append(DBFormat.format(work.getSystemConnKey()));
            sb.append(")");

            DefaultDDBHandler dbh = null;
            try
            {
                dbh = new DefaultDDBHandler(getConnection());
                dbh.execute(sb.toString());
                ResultSet rs = dbh.getResultSet();
                if (rs.next())
                {
                    int cnt = rs.getBigDecimal("COUNT").intValue();
                    if (cnt != 0)
                    {
                        if (param.isErrorAllocCarry())
                        {
                            throw new OperatorException(OperatorException.ERR_EXIST_UNSTART_CARRYINFO);
                        }
                    }
                }
            }
            catch (SQLException e)
            {
                throw new ReadWriteException();
            }
            finally
            {
                if (dbh != null)
                {
                    dbh.close();
                }
            }

            // 出庫時、設定順にて出庫開始するよう処理を追加 end
            // start work info
            WorkInfo newWork = new WorkInfo();

            // workinfoの設定単位キー(setting_unit_key)と集約JobNo(collect_job_no)を再割り振りするようになっているので
            // 計画補充または緊急補充の場合は、再割り振りを行わないように変更する。
            if (!SystemDefine.JOB_TYPE_EMERGENCY_REPLENISHMENT.equals(work.getJobType())
                    && !SystemDefine.JOB_TYPE_NORMAL_REPLENISHMENT.equals(work.getJobType()))
            {
                newWork.setSettingUnitKey(settingUnitKey); // update value
                newWork.setCollectJobNo(coJobNo); // update value
            }
            newWork.setPlanLocationNo(work.getPlanLocationNo()); // update value

            String newPlanLot = param.getLotNo();
            if (null != newPlanLot)
            {
                newWork.setPlanLotNo(newPlanLot);
            }

            WmsUserInfo ui = param.getWmsUserInfo();
            ui.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS);

            winfoCtlr.startAsrsWorkInfo(work, newWork, ui);

            String planUKey = work.getPlanUkey();
            if (!StringUtil.isBlank(planUKey) && !savePlanUKey.equals(planUKey))
            {
                retrPlanCtlr.startPlan(planUKey);
                savePlanUKey = planUKey;
            }

            // 計画補充 / 緊急補充の場合
            String jobType = work.getJobType();
            if (SystemDefine.JOB_TYPE_EMERGENCY_REPLENISHMENT.equals(jobType)
                    || SystemDefine.JOB_TYPE_NORMAL_REPLENISHMENT.equals(jobType))
            {
                String startStatus = SystemDefine.STATUS_FLAG_MOVE_RETRIEVAL_WORKING;

                mwiCtlr.startAsWork(work.getJobNo(), settingUnitKey, startStatus, ui);
            }

            // update carry info
            CarryInfoAlterKey ciakey = new CarryInfoAlterKey();

            ciakey.updateCmdStatus(SystemDefine.CMD_STATUS_START);
            ciakey.updateLastUpdatePname(getCallerName());

            ciakey.setCarryKey(work.getSystemConnKey());

            carryInfoH.modify(ciakey);
        } // end for loop of work info

        // 作業リスト情報作成メソッド
        createWorkListByWorkInfo(settingUnitKey);

        // 出力パラメータに設定単位キーを追加
        AsrsOutParameter retParam = new AsrsOutParameter();
        retParam.setSettingUnitKey(settingUnitKey);

        return retParam;
    }

    /**
     * 補充開始処理を行います。<br>
     *
     * @param param 入力パラメータ
     * @param works 対象作業一覧<br>
     * <ol>
     * 以下の項目を参照します。
     * <li>ステーションNo.(ステーションまたは作業場)
     * <li>ユーザ情報 (ハードウエア以外)
     * </ol>
     *
     * @return 値なし
     * @throws ReadWriteException データベースアクセスエラー
     * @throws ScheduleException パレット情報が見つからない
     * @throws NoPrimaryException 対象ステーションまたはパレットが複数見つかった
     * @throws InvalidDefineException パラメータの内容が不正
     * @throws RouteException 搬送ルートなし
     * @throws OperatorException 未開始の搬送あり
     * @throws NotFoundException 他の端末でデータ更新済み
     * @throws DataExistsException 作業情報登録済み
     */
    protected AsrsOutParameter startReplenish(AsrsInParameter param, WorkInfo[] works)
            throws ReadWriteException,
                ScheduleException,
                NoPrimaryException,
                InvalidDefineException,
                RouteException,
                OperatorException,
                NotFoundException,
                DataExistsException

    {
        boolean isUnDefinedStation = StringUtil.isBlank(param.getStationNo());
        Station wkStation = null;

        if (!isUnDefinedStation)
        {
            // create dest station
            wkStation = getDestStation(param);
        }

        // ハンドラ
        WMSSequenceHandler seqHandler = getSeqHandler();
        PalletHandler palletH = new PalletHandler(getConnection());
        CarryInfoHandler carryInfoH = new CarryInfoHandler(getConnection());

        // prepare controllers
        RouteController routeCtlr = getRouteCtlr();
        AsWorkInfoController winfoCtlr = getAsWorkInfoCtlr();
        MoveWorkInfoController mwiCtlr = new MoveWorkInfoController(getConnection(), getCaller());
        RetrievalPlanController retrPlanCtlr = new RetrievalPlanController(getConnection(), getCaller());

        String coJobNo = seqHandler.nextWorkInfoCollectJobNo();
        String savePlanUKey = "_";

        Map<String, Station> modifyCarryInfo = new HashMap<String, Station>();

        for (WorkInfo work : works)
        {
            // get pallet info and lock
            PalletSearchKey pskey = new PalletSearchKey();

            String stockId = work.getStockId();
            pskey.setKey(Stock.STOCK_ID, stockId);
            pskey.setJoin(Pallet.PALLET_ID, Stock.PALLET_ID);

            Pallet tPallet = (Pallet)palletH.findPrimary(pskey);
            if (null == tPallet)
            {
                // target pallet already removed
                throw new ScheduleException("No pallet found for StockID:" + stockId);
            }

            // 同一の搬送キーの場合は同じステーションの為、キャッシュしている情報を使用する
            if (modifyCarryInfo.containsKey(work.getSystemConnKey()))
            {
                wkStation = modifyCarryInfo.get(work.getSystemConnKey());
            }
            // ステーションNo.が未定義の場合
            else if (isUnDefinedStation)
            {
                wkStation = getStation(getCarryInfo(work, SystemDefine.CMD_STATUS_ALLOCATION).getDestStationNo());
            }

            boolean routeOK = routeCtlr.retrievalDetermin(tPallet, wkStation, true);
            if (!routeOK)
            {
                throw new RouteException(routeCtlr.getRouteStatus());
            }

            // 出庫時、設定順にて出庫開始するよう処理を追加 start
            // 同一パレットに紐づく搬送で開始されていないものがないかチェック
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT COUNT(*) COUNT FROM DNCARRYINFO ");
            sb.append("WHERE ");
            sb.append(" PALLET_ID = ");
            sb.append(DBFormat.format(tPallet.getPalletId()));
            sb.append(" AND CARRY_KEY != ");
            sb.append(DBFormat.format(work.getSystemConnKey()));
            sb.append(" AND CMD_STATUS = ");
            sb.append(DBFormat.format(CarryInfo.CMD_STATUS_ALLOCATION));
            sb.append(" AND REGIST_DATE < (SELECT REGIST_DATE FROM DNCARRYINFO ");
            sb.append(" WHERE ");
            sb.append(" CARRY_KEY = ");
            sb.append(DBFormat.format(work.getSystemConnKey()));
            sb.append(")");

            DefaultDDBHandler dbh = null;
            try
            {
                dbh = new DefaultDDBHandler(getConnection());
                dbh.execute(sb.toString());
                ResultSet rs = dbh.getResultSet();
                if (rs.next())
                {
                    int cnt = rs.getBigDecimal("COUNT").intValue();
                    if (cnt != 0)
                    {
                        if (param.isErrorAllocCarry())
                        {
                            throw new OperatorException(OperatorException.ERR_EXIST_UNSTART_CARRYINFO);
                        }
                    }
                }
            }
            catch (SQLException e)
            {
                throw new ReadWriteException();
            }
            finally
            {
                if (dbh != null)
                {
                    dbh.close();
                }
            }

            // 出庫時、設定順にて出庫開始するよう処理を追加 end

            // start work info
            WorkInfo newWork = new WorkInfo();

            newWork.setCollectJobNo(coJobNo); // update value
            newWork.setPlanLocationNo(work.getPlanLocationNo()); // update value

            String newPlanLot = param.getLotNo();
            if (null != newPlanLot)
            {
                newWork.setPlanLotNo(newPlanLot);
            }

            WmsUserInfo ui = param.getWmsUserInfo();
            ui.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS);

            winfoCtlr.startAsrsWorkInfo(work, newWork, ui);

            String planUKey = work.getPlanUkey();
            if (!StringUtil.isBlank(planUKey) && !savePlanUKey.equals(planUKey))
            {
                retrPlanCtlr.startPlan(planUKey);
                savePlanUKey = planUKey;
            }

            String startStatus = SystemDefine.STATUS_FLAG_MOVE_RETRIEVAL_WORKING;
            mwiCtlr.startAsWork(work.getJobNo(), "", startStatus, ui);

            // update carry info
            if (!modifyCarryInfo.containsKey(work.getSystemConnKey()))
            {
                CarryInfoAlterKey ciakey = new CarryInfoAlterKey();
                ciakey.updateCmdStatus(SystemDefine.CMD_STATUS_START);
                ciakey.updateLastUpdatePname(getCallerName());
                ciakey.setCarryKey(work.getSystemConnKey());
                carryInfoH.modify(ciakey);
                modifyCarryInfo.put(work.getSystemConnKey(), wkStation);
            }
        } // end for loop of work info

        AsrsOutParameter retParam = new AsrsOutParameter();

        return retParam;
    }

    /**
     * 入庫作業開始<br>
     * パラメータの項目に該当するデータに対して入庫開始処理を行う。<br>
     * <br>
     *
     * @param param ASRS入力パラメータ
     * @param newWorkInfo 入出庫作業更新内容
     * <ol>
     * 以下の項目を参照します。
     * <li>設定単位キー
     * <li>集約作業No.
     * <li>ハードウェア区分
     * <li>システム接続キー
     * <li>予定エリア
     * <li>予定棚
     * <li>予定ロットNo.
     * <li>予定数
     * </ol>
     *
     * @param pallet 対象パレット
     *
     * @throws ReadWriteException データベース処理でエラー発生した場合にスローされます。
     * @throws LockTimeOutException 一定時間データベースのロックが解除されない場合にスローされます。
     * @throws InvalidDefineException 指定パラメータ値が異常(禁止文字含むなど)な場合にスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws ScheduleException 予期しない例外が発生した場合にスローされます。
     * @throws DataExistsException 登録データが既に存在した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在した場合にスローされます。
     * @throws OperatorException オペレータ処理でエラー発生した場合にスローされます。<br>
     * <br>エラーコード
     * <ul>
     * <li>10 : 他端末で更新された
     * <li>11 : 他端末作業中
     * <li>12 : 作業完了済み
     * </ul>
     */
    protected void startStorage(AsrsInParameter param, WorkInfo newWorkInfo, Pallet pallet)
            throws ReadWriteException,
                LockTimeOutException,
                InvalidDefineException,
                OperatorException,
                NotFoundException,
                ScheduleException,
                DataExistsException,
                NoPrimaryException
    {
        WorkInfoController wkInfoCtrl = getAsWorkInfoCtlr();

        // 入庫開始ロック
        WorkInfo keywork = new WorkInfo();
        keywork.setConsignorCode(param.getConsignorCode());
        keywork.setPlanDay(param.getStoragePlanDay());
        keywork.setItemCode(param.getItemCode());
        keywork.setPlanAreaNo(param.getPlanAreaNo());
        keywork.setPlanLocationNo(param.getLocation());
        keywork.setPlanLotNo(param.getPlanLotNo());

        WorkInfo[] works = wkInfoCtrl.lockStorageStart(keywork);

        if (ArrayUtil.isEmpty(works))
        {
            wkInfoCtrl.checkStorageStart(keywork, param.getItemCode());
        }

        // 集約入庫作業開始
        startCollectStorage(works, newWorkInfo, pallet, param.getWmsUserInfo(), param.getReasonType());
    }

    /**
     * AsrsInParameterから、荷主マスター,商品マスターの登録を行います。<br>
     * 内部でマスターパッケージ導入済みであるかどうか確認しています。<br>
     * 導入済みでない場合だけ、自動登録処理が呼び出されます。
     *
     * @param param 入力パラメータ
     * <ol>
     * 以下の項目を参照します。
     * <li>荷主コード
     * <li>荷主名称
     * <li>商品コード
     * <li>商品名称
     * <li>JANコード
     * <li>ケース入数
     * <li>ボール入数
     * <li>ケースITF
     * <li>ボールITF
     * </ol>
     *
     * @throws ReadWriteException データベースアクセスエラー
     * @throws LockTimeOutException マスタテーブルのロックタイムアウト
     * @throws NoPrimaryException 対象マスタ情報が複数存在するときスローされます。
     * @throws NotFoundException 対象マスタ情報が他のアプリケーションから変更されたときスローされます。
     * @throws DataExistsException 対象マスタ情報が他のアプリケーションから登録されたときスローされます。
     * @throws ScheduleException システム定義異常
     */
    protected void autoCreateMaster(AsrsInParameter param)
            throws ReadWriteException,
                LockTimeOutException,
                NoPrimaryException,
                NotFoundException,
                DataExistsException,
                ScheduleException
    {
        WarenaviSystemController wsCtlr = getWSysCtlr();
        if (wsCtlr.hasMasterPack())
        {
            // マスタパッケージありのときは自動登録しない
            return;
        }

        ItemController itemCtlr = getItemCtlr();
        ConsignorController consigCtlr = getConsigCtlr();

        String consignorCode = param.getConsignorCode();
        String itemCode = param.getItemCode();

        // 荷主マスタ情報の作成
        Consignor consignor = new Consignor();
        consignor.setConsignorCode(consignorCode); // 荷主コード
        consignor.setConsignorName(param.getConsignorName()); // 荷主名称

        consigCtlr.autoCreate(consignor, param.getWmsUserInfo());

        // 商品マスタ情報の作成
        Item item = new Item();
        item.setConsignorCode(consignorCode); // 荷主コード
        item.setItemCode(itemCode); // 商品コード
        item.setItemName(param.getItemName()); // 商品名称
        item.setJan(param.getJanCode()); // JANコード
        item.setEnteringQty(param.getEnteringQty()); // ケース入数
        item.setBundleEnteringQty(param.getBundleEnteringQty()); // ボール入数
        item.setItf(param.getItf()); // ケースITF
        item.setBundleItf(param.getBundleItf()); // ボールITF

        itemCtlr.autoCreate(item, param.getWmsUserInfo());
    }

    /**
     * パラメータのエリアNo.から、倉庫ステーションNo.を返します。
     *
     * @param param 入力パラメータ
     * @return 倉庫ステーションNo.
     * @throws ReadWriteException データベースアクセスエラー
     */
    protected String getWhStationNo(AsrsInParameter param)
            throws ReadWriteException
    {
        String areaNo = param.getAreaNo();
        return getAreaCtlr().getWhStationNo(areaNo);
    }

    /**
     * パラメータのステーションをもとに、ステーションが作業場の場合は、
     * 作業場からステーションを特定して返します。<br>
     * ステーションが作業場でなければステーションテーブルを検索して
     * ステーションを返します。
     *
     * @param param AsrsInParameter
     * <ol>
     * 以下の項目を参照します。
     * <li>ステーションNo. (ステーションまたは作業場)
     * </ol>
     *
     * @return StationNoに対応するステーションインスタンス
     *
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NoPrimaryException ステーションが複数みつかった(定義異常)
     * @throws ScheduleException 対象のステーションまたは作業場が見つからない
     */
    protected Station getDestStation(AsrsInParameter param)
            throws ReadWriteException,
                NoPrimaryException,
                ScheduleException
    {
        Station st = getStation(param.getStationNo());
        return st;
    }

    /**
     * "他端末で更新済み"のオペレータ例外を生成して返します。
     *
     * @param idx 発生したデータのインデックス (1加算して例外にセットされます)<br>
     * 未指定の時は、-1をセットしてください。
     * @return オペレータ例外
     */
    protected OperatorException updatedOpException(int idx)
    {
        OperatorException ex = new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        if (0 <= idx)
        {
            ex.setErrorLineNo(idx + 1);
        }
        return ex;
    }

    /**
     * ルートが確保できない場合のオペレータ例外を生成して返します。
     *
     * @param idx 発生したデータのインデックス (1加算して例外にセットされます)<br>
     * 未指定の時は、-1をセットしてください。
     * @param routeStat ルートステータス
     * @return オペレータ例外
     */
    protected OperatorException routeOpException(int idx, int routeStat)
    {
        OperatorException e = new OperatorException(OperatorException.ERR_ROUTE);
        if (0 <= idx)
        {
            e.setErrorLineNo(idx + 1);
        }
        e.setRouteStatus(routeStat);
        return e;
    }

    /**
     * 作業に結びつく搬送情報を引当済みに差し戻します。
     *
     * @param cancelWorks キャンセル対象作業一覧
     * @throws ReadWriteException 搬送情報の読み込みに失敗 (データベースエラー)
     * @throws LockTimeOutException 搬送情報のロックタイムアウト
     */
    protected void cancelCarries(WorkInfo[] cancelWorks)
            throws ReadWriteException,
                LockTimeOutException
    {
        CarryInfoHandler ciH = new CarryInfoHandler(getConnection());

        try
        {
            // キャンセル対象 Carry Key一覧の生成
            Set<String> cancelCKeySet = new HashSet<String>();
            for (WorkInfo work : cancelWorks)
            {
                CarryInfo ci = getCarryInfo(work, SystemDefine.CMD_STATUS_START);
                cancelCKeySet.add(ci.getCarryKey());
            }
            if (cancelCKeySet.isEmpty())
            {
                throw new NotFoundException();
            }

            // 対象搬送情報をCMD_STATUS_ALLOCATIONに更新
            CarryInfoAlterKey ciaKey = new CarryInfoAlterKey();

            String[] carrKeys = cancelCKeySet.toArray(new String[cancelCKeySet.size()]);
            ciaKey.setCarryKey(carrKeys, true);

            ciaKey.updateCmdStatus(SystemDefine.CMD_STATUS_ALLOCATION);

            ciH.modify(ciaKey);
        }
        catch (NotFoundException e)
        {
            // throw database error.
            // ロック済み搬送情報が削除された
            throw new ReadWriteException(e);
        }
    }

    /**
     * 作業がキャンセル可能であるかどうかチェックします。<br>
     * <ol>
     * 以下の内容がチェックされます。
     * <li>作業区分: 出庫
     * <li>作業状態: 作業中
     * <li>ハードウエア区分: AS/RS
     * <li>対応する搬送情報の搬送状態: 開始済み
     * </ol>
     *
     * @param work チェックする作業
     * @return キャンセル対象作業のとき true
     * @throws ReadWriteException 搬送情報の読み込みに失敗 (データベースエラー)
     */
    protected boolean isCancelTarget(WorkInfo work)
            throws ReadWriteException
    {
        // 搬送情報チェック
        CarryInfo ci = getCarryInfo(work, SystemDefine.CMD_STATUS_START);
        boolean isStarted = (null != ci);
        if (!isStarted)
        {
            return false;
        }

        // キャンセル対象作業条件に合致
        return true;
    }

    /**
     * 作業情報に結びつく搬送情報を取得,ロックして返します。<br>
     * 取得できなかったときはnullが返されます。
     *
     * @param work 作業情報
     * @param status 搬送状態搬送データの現在の状態
     * @return 搬送情報
     * @throws ReadWriteException 搬送情報の読み込みに失敗 (データベースエラー)
     */
    protected CarryInfo getCarryInfo(WorkInfo work, String status)
            throws ReadWriteException
    {
        String carryKey = work.getSystemConnKey();
        if (StringUtil.isBlank(carryKey))
        {
            return null;
        }

        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
        ciKey.setCarryKey(carryKey);
        ciKey.setCmdStatus(status);
        try
        {
            CarryInfoHandler ciH = new CarryInfoHandler(getConnection());
            return (CarryInfo)ciH.findPrimary(ciKey);
        }
        catch (NoPrimaryException e)
        {
            // carry key指定で複数レコードは取得されない
            assert true;
        }
        return null;
    }

    /**
     * パレットの状態と搬送ルートの確認
     * @param tPallet パレット情報
     * @param wkStation ステーション
     * @param routeCtlr ルートコントローラー
     * @throws ReadWriteException データベースアクセスエラー
     * @throws NoPrimaryException パレット,対象ステーションが一意に決定できないとき
     * @throws LockTimeOutException パレット情報のロックタイムアウト
     * @throws InvalidDefineException パラメータが不正
     * @throws RouteException 搬送ルートなし
     * @throws NotFoundException 更新対象データが見つからない
     */
    protected void checkPalletRoute(Pallet tPallet, Station wkStation, RouteController routeCtlr)
            throws ReadWriteException,
                NoPrimaryException,
                LockTimeOutException,
                NotFoundException,
                InvalidDefineException,
                RouteException
    {
        // get pallet info and lock
        if (null == tPallet)
        {
            // target pallet already removed
            // 他端末で更新済み
            throw new NotFoundException();
        }
        // fix retrieval location
        boolean routeOK = routeCtlr.retrievalDetermin(tPallet, wkStation, true, true, false, true);
        if (!routeOK)
        {
            // route not ready
            throw new RouteException(routeCtlr.getRouteStatus());
        }
    }

    /**
     * 作業情報をentityにセットする。
     * @param stock 在庫情報
     * @param carryKey 搬送キー
     * @param settingUnitKey 設定単位キー
     * @param wicJobNo 集約作業No.
     * @return WorkInfo
     */
    protected WorkInfo setWorkInfo(Stock stock, String carryKey, String settingUnitKey, String wicJobNo)
    {

        WorkInfo work = new WorkInfo();
        String areaNo = stock.getAreaNo();

        // 集約作業No.
        work.setCollectJobNo(wicJobNo);
        work.setSettingUnitKey(settingUnitKey);
        work.setJobType(SystemDefine.JOB_TYPE_ASRS_INVENTORYCHECK);
        work.setStockId(stock.getStockId());
        work.setSystemConnKey(carryKey);
        work.setConsignorCode(stock.getConsignorCode());
        work.setPlanQty(0);
        work.setPlanAreaNo(areaNo);
        work.setPlanLocationNo(stock.getLocationNo());
        work.setItemCode(stock.getItemCode());
        work.setPlanLotNo(stock.getLotNo());

        return work;
    }

    /**
     * 搬送情報をentityにセットする。
     * @param carryKey 搬送キー
     * @param workNo 作業No.
     * @param asRetrLocation 出庫先ステーション
     * @param schNo スケジュールNo
     * @param tPallet パレット情報
     * @param desSt 搬送先ステーション
     * @return CarryInfo
     */
    protected CarryInfo setCarryInfo(String carryKey, String workNo, String asRetrLocation, String schNo,
            Pallet tPallet, String desSt)
    {
        // create carry info
        CarryInfo carry = new CarryInfo();

        carry.setCarryKey(carryKey);
        carry.setPalletId(tPallet.getPalletId());
        carry.setWorkType(SystemDefine.WORK_TYPE_INVENTORYCHECK);
        carry.setWorkNo(workNo);
        carry.setRetrievalStationNo(asRetrLocation);
        carry.setSourceStationNo(tPallet.getCurrentStationNo());
        carry.setDestStationNo(desSt);
        carry.setScheduleNo(schNo);

        return carry;
    }

    /**
     * 作業No.から再入庫予定情報を取得する。
     * @param workNo 作業No.
     * @return ReStoringPlan[]
     * @throws ReadWriteException
     */
    protected ReStoringPlan[] getReStoringPlanData(String workNo)
            throws ReadWriteException
    {
        ReStoringPlanSearchKey key = new ReStoringPlanSearchKey();

        key.setWorkNo(workNo);

        key.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        return (ReStoringPlan[])(new ReStoringPlanHandler(getConnection())).find(key);
    }

    /**
     * 再入庫予定情報に紐付く作業情報を取得します。
     * @param plan 再入庫予定情報
     * @return WorkInfo
     * @throws ReadWriteException
     * @throws NoPrimaryException
     */
    protected WorkInfo getReStoringData(ReStoringPlan plan)
            throws ReadWriteException,
                NoPrimaryException
    {
        WorkInfoSearchKey key = new WorkInfoSearchKey();

        key.setPlanUkey(plan.getPlanUkey());

        return (WorkInfo)(new WorkInfoHandler(getConnection())).findPrimary(key);
    }
    /**
     * 再入庫予定情報を作業中に更新します。
     *
     * @param planUkey 対象の予定一意キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     */
    public void startReStoringPlan(String planUkey)
            throws ReadWriteException,
                NotFoundException
    {
        ReStoringPlanHandler sph = new ReStoringPlanHandler(getConnection());
        ReStoringPlanAlterKey akey = new ReStoringPlanAlterKey();

        akey.updateStatusFlag(STATUS_FLAG_NOWWORKING);
        akey.updateLastUpdatePname(getCallerName());

        akey.setPlanUkey(planUkey);

        sph.modify(akey);
    }

    //------------------------------------------------------------
    // private methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // utility methods
    //------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AsrsOperator.java 8015 2011-10-25 04:23:39Z ota $";
    }
}
