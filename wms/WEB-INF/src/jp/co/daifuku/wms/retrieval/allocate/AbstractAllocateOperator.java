// $Id: AbstractAllocateOperator.java 8053 2013-05-15 01:00:52Z kishimoto $
package jp.co.daifuku.wms.retrieval.allocate;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.communication.as21.*;
import jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator;
import jp.co.daifuku.wms.asrs.location.RouteController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.WorkPlace;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.HostSendController;
import jp.co.daifuku.wms.base.controller.StationController;
import jp.co.daifuku.wms.base.controller.StockController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.base.dbhandler.AllocatePriorityHandler;
import jp.co.daifuku.wms.base.dbhandler.AllocatePrioritySearchKey;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReasonHandler;
import jp.co.daifuku.wms.base.dbhandler.ReasonSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.TerminalAreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.entity.AllocatePriority;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.GroupController;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Reason;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.db.DefaultDDBFinder;
import jp.co.daifuku.wms.handler.db.DirectDBFinder;
import jp.co.daifuku.wms.handler.db.SysDate;
import jp.co.daifuku.wms.handler.field.FieldName;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;

/**
 * 在庫の引当処理を行います。<br>
 *
 *
 * @version $Revision: 8053 $, $Date: 2013-05-15 10:00:52 +0900 (水, 15 5 2013) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: kishimoto $
 */
public abstract class AbstractAllocateOperator
        extends AbstractOperator

{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    /**
     * 出庫フリーステーションクラス名
     */
    private static final String FREE_RETRIEVAL_STATION_CLASSNAME = FreeRetrievalStationOperator.class.getName();


    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /**
     * システム用コントローラ
     */
    private WarenaviSystemController _wnSysCon = null;

    /**
     * ルートコントローラ
     */
    private RouteController _rtCon = null;

    /**
     * シーケンスハンドラ
     */
    private WMSSequenceHandler _sequence = null;

    /**
     * 引当パタンNo.
     */
    private String _pattern = null;

    /**
     * 倉庫ステーションNo.と作業場のマップ
     */
    private Map<String, List<Station>> _stationMap = null;

    /**
     * エリア情報マップ
     */
    private Map<String, Area> _areaMap = null;

    /**
     * スケジュールNo.
     */
    private String _scheduleNo = "";

    /**
     * 引当予定数(欠品完了で使用する)
     */
    private int _planQty = 0;

    /**
     * 引当済数(欠品完了で使用する)
     */
    private int _allocatedQty = 0;

    /**
     * 作成済み作業No.リスト
     */
    private List<String> _createJobNoList = null;

    /**
     * 引当ステーションのモードチェック（出庫モード、中断中チェック）フラグ<br>
     * デフォルト値 : false(チェックしない)
     */
    private boolean _modeCheck = false;

    /**
     * 設定単位キー
     */
    private String _settingUkey = "";

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     *
     * @param conn DBコネクション
     * @param pattern 引当パターンNo.
     * @param caller 呼び出し元クラス
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected AbstractAllocateOperator(Connection conn, String pattern, Class caller) throws CommonException
    {
        super(conn, caller);

        init(pattern);
    }

    /**
     * コンストラクタ
     *
     * @param conn DBコネクション
     * @param pattern 引当パターンNo.
     * @param modeCheck 引当ステーションのモードチェック（出庫モード、中断中チェック）を行うかどうか
     * @param caller 呼び出し元クラス
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected AbstractAllocateOperator(Connection conn, String pattern, boolean modeCheck, Class caller) throws CommonException
    {
        super(conn, caller);

        _modeCheck = modeCheck;
        init(pattern);
    }

    /**
     * 商品コード指定出庫用コンストラクタ
     * @param conn DBコネクション
     * @param inparam
     * @param caller 呼び出し元クラス
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected AbstractAllocateOperator(Connection conn, InParameter inparam, boolean modeCheck, Class caller) throws CommonException
    {
        super(conn, caller);

        _modeCheck = modeCheck;
        init(inparam);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * インスタンス変数で保持している搬送情報のスケジュールNo.をリセットします。<BR>
     */
    public void resetScheduleNo()
    {
        _scheduleNo = "";
    }

    /**
     * インスタンス変数で保持している搬送情報のスケジュールNo.をセットします。<BR>
     * @param str スケジュールNo.
     */
    protected void setScheduleNo(String str)
    {
        _scheduleNo = str;
    }

    /**
     * スケジュールNo.を取得します。
     * @return スケジュールNo.
     */
    protected String getScheduleNo()
    {
        return _scheduleNo;
    }

    /**
     * インスタンス変数で保持している作成済み作業No.リストをリセットします。<BR>
     */
    public void resetCreateJobNoList()
    {
        _createJobNoList.clear();
    }

    /**
     * 作成済み作業No.リストに作業No.を追加します。<BR>
     *
     * @param jobNo 作業No.
     */
    protected void addCreateJobNoList(String jobNo)
    {
        _createJobNoList.add(jobNo);
    }

    /**
     * 作成済み作業No.リストを取得します。<BR>
     * @return 作成済み作業No.リスト
     */
    public List<String> getCreateJobNoList()
    {
        return _createJobNoList;
    }

    /**
     * 同一スケジュールNo.内にある搬送データの出庫指示詳細と再入庫区分の更新を行う。
     *
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws NotFoundException 更新対象データが存在しなかった場合にスローされます。
     * @throws InvalidDefineException 不正なデータが検出された場合にスローされます。
     * @throws NoPrimaryException 1件しか取得できないはずのデータが複数件取得できた場合にスローされます。
     */
    public void decideCarryInfo()
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException,
                NoPrimaryException
    {
        // スケジュールNo.が未設定(搬送情報未作成)の場合は処理しない
        if (!isCreateCarryInfo())
        {
            return;
        }
        StockHandler stkh = new StockHandler(getConnection());
        StockSearchKey stkKey = new StockSearchKey();
        CarryInfoHandler cih = new CarryInfoHandler(getConnection());
        CarryInfoAlterKey ciAltKey = new CarryInfoAlterKey();
        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();

        // 今回スケジュールした搬送情報を取得する
        ciKey.clear();
        ciKey.setScheduleNo(_scheduleNo);

        CarryInfo[] cinfos = (CarryInfo[])new CarryInfoHandler(getConnection()).find(ciKey);

        for (CarryInfo cInfo : cinfos)
        {
            // パレット上の引当可能数を取得する
            stkKey.clear();
            stkKey.setPalletId(cInfo.getPalletId());
            stkKey.setPalletIdGroup();
            stkKey.setLocationNoGroup();
            stkKey.setLocationNoCollect();
            stkKey.setAllocationQtyCollect("SUM");
            Stock stk = (Stock)stkh.findPrimary(stkKey);

            int totalAllocatableQty = stk.getAllocationQty();

            // 搬送先ステーションの取得
            Station destSt = StationFactory.makeStation(getConnection(), cInfo.getDestStationNo());

            // ユニット出庫かどうか
            boolean isUnitRetrieval = false;
            if (totalAllocatableQty == 0)
            {
                // 引当数が０、つまりユニット出庫の場合
                isUnitRetrieval = true;
            }
            else
            {
                // 引当数が０以外、つまりピッキング出庫の場合
            }

            // 出庫指示詳細を決定します
            String retrievalDetail = "";
            String reStoringFlag = "";

            // ユニット出庫の場合
            if (isUnitRetrieval)
            {
                // ステーションが払い出し不可能な場合
                if (!destSt.isRemove())
                {
                    // 作業は全数出庫だが、ステーションが払い出し不可の場合、ピッキング出庫とする
                    retrievalDetail = CarryInfo.RETRIEVAL_DETAIL_PICKING;
                }
                else
                {
                    retrievalDetail = CarryInfo.RETRIEVAL_DETAIL_UNIT;
                }
            }
            // ピッキング出庫の場合
            else
            {
                // ユニット出庫専用ステーションの場合
                if (destSt.isUnitOnly())
                {
                    // 作業はピッキングだが、ステーションが払い出しのみの場合、ユニット出庫とする
                    retrievalDetail = CarryInfo.RETRIEVAL_DETAIL_UNIT;
                }
                else
                {
                    retrievalDetail = CarryInfo.RETRIEVAL_DETAIL_PICKING;
                }
            }

            // 出庫指示詳細より、再入庫フラグの決定を行います。

            // ユニット出庫の場合
            if (CarryInfo.RETRIEVAL_DETAIL_UNIT.equals(retrievalDetail))
            {
                // ユニット作業時、「再入庫なし」とする
                reStoringFlag = CarryInfo.RESTORING_FLAG_NOT_SAME_LOC;
            }
            // ピッキング出庫の場合
            else
            {
                StationController stCtl = new StationController(getConnection(), getCaller());
                if (stCtl.isReStoringEmptyLocationSearch(destSt.getStationNo()))
                {
                    // 「再入庫なし」とする
                    reStoringFlag = CarryInfo.RESTORING_FLAG_NOT_SAME_LOC;
                }
                else
                {
                    // 搬送指示必要(AWC側で指示作成)の場合(eWN側で再入庫データを作成するステーションの場合)
                    if (Station.RESTORING_INSTRUCTION_AWC_STORAGE_SEND.equals(destSt.getRestoringInstruction()))
                    {
                        // 「再入庫なし」とする
                        reStoringFlag = CarryInfo.RESTORING_FLAG_NOT_SAME_LOC;
                    }
                    // 搬送指示不要(AGC側で自動的入庫)の場合
                    else
                    {
                        // 「同一棚に再入庫」とする
                        reStoringFlag = CarryInfo.RESTORING_FLAG_SAME_LOC;
                    }
                }
            }

            // 上記データ内容で搬送情報を更新します
            ciAltKey.clear();
            ciAltKey.setCarryKey(cInfo.getCarryKey());
            ciAltKey.updateRetrievalDetail(retrievalDetail);
            ciAltKey.updateRestoringFlag(reStoringFlag);
            cih.modify(ciAltKey);
        }
    }

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------
    /**
     * patternを返します。
     * @return patternを返します。
     */
    protected String getPattern()
    {
        return _pattern;
    }

    /**
     * wnSysConを返します。
     * @return wnSysConを返します。
     */
    protected WarenaviSystemController getWnSysCon()
    {
        return _wnSysCon;
    }

    /**
     * シーケンスハンドラを返します。
     * @return シーケンスハンドラを返します。
     */
    protected WMSSequenceHandler getSequence()
    {
        return _sequence;
    }

    /**
     * 引当処理での引当済数をセットします。
     *
     * @param qty 引当済数
     */
    protected void setAllocatedQty(int qty)
    {
        _allocatedQty = qty;
    }

    /**
     * 引当処理での引当済数を返します。
     *
     * @return 引当済数
     */
    public int getAllocatedQty()
    {
        return _allocatedQty;
    }

    /**
     * 引当処理での引当予定数をセットします
     *
     * @param qty 引当予定数
     */
    protected void setPlanQty(int qty)
    {
        _planQty = qty;
    }

    /**
     * 設定単位キーを返します。
     *
     * @param nextflg 採番して取得する場合、true
     * @return 設定単位キーを返します。
     * @throws ReadWriteException
     */
    public String getSettingUkey(boolean nextflg)
            throws ReadWriteException
    {
        if (nextflg && StringUtil.isBlank(_settingUkey))
        {
            _settingUkey = getSequence().nextWorkInfoSetUkey();
        }

        return _settingUkey;
    }

    /**
     * 引当処理での引当予定数を返します。
     *
     * @return 引当予定数を返します。
     */
    public int getPlanQty()
    {
        return _planQty;
    }

    /**
     * 引当処理での欠品数を返します。
     *
     * @return 欠品数
     */
    public int getShortageQty()
    {
        return _planQty - _allocatedQty;
    }

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /**
     * 引当に伴う更新処理を行います。<BR>
     * 作業情報・予定情報の更新や、今回いくつ引当を要求するかを算出し、
     * 本クラスの引当処理を呼び出してください。
     *
     * @param ent 引当対象のデータを含むエンティティ
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     * @return 引当数
     */
    protected abstract int allocate(Entity ent)
            throws CommonException;

    /**
     * 作業情報の作成を行います。
     *
     * @param ent 引当対象のデータを含むエンティティ
     * @param stk 引当を行った在庫情報
     * @param allocateQty 引当数
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected abstract void createWork(Entity ent, Stock stk, int allocateQty)
            throws CommonException;

    /**
     * ASRSの作業情報の作成を行います。
     *
     * @param ent 引当対象のデータを含むエンティティ
     * @param stk 引当を行った在庫情報
     * @param allocateQty 引当数
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected abstract void createWork(AsrsInParameter param, Stock stk, int allocateQty)
            throws CommonException;

    /**
     * 各ハンドラ類の初期化処理を行う。
     *
     * @param pattern 引当パターン
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    private void init(String pattern)
            throws CommonException
    {
        _rtCon = new RouteController(getConnection());
        _wnSysCon = new WarenaviSystemController(getConnection(), getCaller());

        _stationMap = new HashMap<String, List<Station>>();

        _sequence = new WMSSequenceHandler(getConnection());

        _pattern = pattern;
        _areaMap = initAreaMap();

        // 作業場マップ(ルートチェックで使用する)の初期化
        initStationMap();

        _createJobNoList = new ArrayList<String>();
    }

    /**
     * 各ハンドラ類の初期化処理を行う。
     *
     * @param inparam 引当パターン
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    private void init(InParameter inparam)
            throws CommonException
    {
        _rtCon = new RouteController(getConnection());
        _wnSysCon = new WarenaviSystemController(getConnection(), getCaller());

        _stationMap = new HashMap<String, List<Station>>();

        _sequence = new WMSSequenceHandler(getConnection());

        AsrsInParameter p = (AsrsInParameter)inparam;
        _areaMap = initAreaMap(p);

        // 作業場マップ(ルートチェックで使用する)の初期化
        initStationMap(p);

        _createJobNoList = new ArrayList<String>();
    }

    /**
     * エリア情報を読み込み、マップとして返します。<BR>
     * キーはエリアNo.、値はエリアエンティティです。<BR>
     *
     * @return エリア情報のマップ
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    private Map<String, Area> initAreaMap()
            throws ReadWriteException
    {
        Map<String, Area> areaMap = new HashMap<String, Area>();

        Area[] areas = (Area[])new AreaHandler(getConnection()).find(new AreaSearchKey());

        for (Area area : areas)
        {
            areaMap.put(area.getAreaNo(), area);
        }
        return areaMap;
    }

    /**
     * エリア情報を読み込み、パラメータで渡されたエリアの分だけマップとして返します。<BR>
     * キーはエリアNo.、値はエリアエンティティです。<BR>
     *
     * @return エリア情報のマップ
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    private Map<String, Area> initAreaMap(AsrsInParameter param)
            throws ReadWriteException
    {
        Map<String, Area> areaMap = new HashMap<String, Area>();
        AreaSearchKey akey = new AreaSearchKey();
        if (!param.getStationNo().startsWith(AsrsInParameter.SELECT_STATION_NONE)
                && !WmsParam.ALL_STATION.equals(param.getStationNo()))
        {
            akey.setKey(Station.STATION_NO, param.getStationNo());
            akey.setJoin(Station.WH_STATION_NO, Area.WHSTATION_NO);
        }
        // 副問い合わせ用TerminalAreaの検索条件のセットをする。
        TerminalAreaSearchKey tKey = new TerminalAreaSearchKey();
        tKey.setAreaIdCollect();
        tKey.setTerminalNo(param.getTerminalNo());

        akey.setAreaType(SystemDefine.AREA_TYPE_FLOOR, "=", "(", "", false);
        akey.setAreaType(SystemDefine.AREA_TYPE_RECEIVING, "=", "", "", false);
        akey.setAreaType(SystemDefine.AREA_TYPE_TEMPORARY, "=", "", "", false);

        akey.setAreaType(SystemDefine.AREA_TYPE_ASRS, "=", "(", "", true);

        // 副問合せ条件セット
        akey.setKey(Area.WHSTATION_NO, tKey, "=", "", "))", true);

        Area[] areas = (Area[])new AreaHandler(getConnection()).find(akey);

        for (Area area : areas)
        {
            if (WmsParam.ALL_AREA_NO.equals(param.getAreaNo()) || area.getAreaNo().equals(param.getAreaNo()))
            {
                areaMap.put(area.getAreaNo(), area);
            }
        }
        return areaMap;
    }

    /**
     * インスタンス変数の引当パターンに紐つくエリアと対応する作業場リストの対応表を作成します。<BR>
     * エリアに作業場が存在しない場合はステーションを取得します。<BR>
     *
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException 引当パターンにエリア、作業場が紐ついていない場合にスローされます。
     */
    private void initStationMap()
            throws ReadWriteException,
                InvalidDefineException

    {
        AllocatePriorityHandler apHandler = new AllocatePriorityHandler(getConnection());
        AllocatePrioritySearchKey apKey = new AllocatePrioritySearchKey();
        AreaSearchKey areaKey = new AreaSearchKey();

        apKey.setAllocateNo(_pattern);
        areaKey.setAreaType(Area.AREA_TYPE_ASRS); // ASRSエリア
        apKey.setKey(areaKey);

        // set join
        apKey.setJoin(AllocatePriority.ALLOCATE_AREA, Area.AREA_NO);

        // 引当パターンを取得する
        AllocatePriority[] priorities = (AllocatePriority[])apHandler.find(apKey);

        if (ArrayUtil.isEmpty(priorities))
        {
            return;
        }

        for (AllocatePriority priority : priorities)
        {
            StationHandler handler = new StationHandler(getConnection());
            StationSearchKey stKey = new StationSearchKey();

            // 引当パターンテーブルに作業場が指定されていた場合、限定する
            if (!StringUtil.isBlank(priority.getStationNo()))
            {
                try
                {
                    Station st = StationFactory.makeStation(getConnection(), priority.getStationNo());

                    // 設定ステーションが作業場の場合のみステーションを限定する
                    if (st instanceof WorkPlace)
                    {
                        WorkPlace wp = (WorkPlace)st;

                        // 作業場にある全ステーションをセット
                        stKey.setStationNo(wp.getWPStations(), true);
                    }
                    // ステーションの場合はそのままMapにセットします。
                    else if (st instanceof Station)
                    {
                        String areaNo = priority.getAllocateArea();

                        List<Station> stationList = _stationMap.get(areaNo);
                        if (stationList == null)
                        {
                            stationList = new ArrayList<Station>();
                        }
                        stationList.add(st);

                        _stationMap.put(areaNo, stationList);
                        continue;
                    }
                }
                catch (NotFoundException e)
                {
                    // ステーションが見つからなかった場合
                    throw new InvalidDefineException();
                }
            }

            // set where
            stKey.setSendable(Station.SENDABLE_TRUE); // 送信可
            stKey.setStationType(Station.STATION_TYPE_INOUT, "=", "(", "", false);
            stKey.setStationType(Station.STATION_TYPE_OUT, "=", "", ")", true);

            areaKey.clear();
            areaKey.setAreaNo(priority.getAllocateArea()); // 引当エリア
            stKey.setKey(areaKey);

            // set join
            stKey.setJoin(Station.WH_STATION_NO, Area.WHSTATION_NO);

            // set collect
            stKey.setCollect(Area.AREA_NO);
            stKey.setStationNoCollect();

            // set order by
            stKey.setStationNoOrder(true);

            Station[] stations = (Station[])handler.find(stKey);

            for (Station station : stations)
            {
                String areaNo = (String)station.getValue(Area.AREA_NO);

                List<Station> stationList = _stationMap.get(areaNo);
                if (stationList == null)
                {
                    stationList = new ArrayList<Station>();
                }
                stationList.add(station);

                _stationMap.put(areaNo, stationList);
            }
        }
    }

    /**
     * インスタンス変数の引当パターンに紐つくエリアと対応する作業場リストの対応表を作成します。<BR>
     * エリアに作業場が存在しない場合はステーションを取得します。<BR>
     * @throws CommonException
     */
    private void initStationMap(AsrsInParameter p)
            throws CommonException

    {
        AreaSearchKey areaKey = new AreaSearchKey();

        String stNo = p.getStationNo();

        List<Area> areaList = new ArrayList<Area>();
        areaList.addAll(_areaMap.values());

        for (Area areadata : areaList)
        {
            if (!isAsrsArea(areadata.getAreaNo()) || stNo.startsWith(AsrsInParameter.SELECT_STATION_NONE))
            {
                continue;
            }
            StationHandler handler = new StationHandler(getConnection());
            StationSearchKey stKey = new StationSearchKey();

            // 全体作業場が指定されていた場合、限定する
            if (!WmsParam.ALL_STATION.equals(stNo))
            {
                // 作業場の場合
                try
                {
                    Station st = StationFactory.makeStation(getConnection(), stNo);

                    // 設定ステーションが作業場の場合のみステーションを限定する
                    if (st instanceof WorkPlace)
                    {
                        WorkPlace wp = (WorkPlace)st;

                        // 作業場にある全ステーションをセット
                        stKey.setStationNo(wp.getWPStations(), true);
                    }
                    else if (st instanceof Station)
                    {
                        //                      stKey.setStationNo(st.getStationNo());
                        String areaNo = areadata.getAreaNo();

                        List<Station> stationList = _stationMap.get(areaNo);
                        if (stationList == null)
                        {
                            stationList = new ArrayList<Station>();
                        }
                        stationList.add(st);

                        _stationMap.put(areaNo, stationList);
                        continue;
                    }
                }
                catch (NotFoundException e)
                {
                    // ステーションが見つからなかった場合
                    throw new InvalidDefineException();
                }
            }

            // 副問い合わせ用TerminalAreaの検索条件のセットをする。
            TerminalAreaSearchKey tKey = new TerminalAreaSearchKey();
            tKey.setStationNoCollect();
            tKey.setTerminalNo(p.getTerminalNo());

            // 副問い合わせ用Stationの検索条件をセットする。
            stKey.setKey(Station.STATION_NO, tKey);

            // set where
            stKey.setSendable(Station.SENDABLE_TRUE); // 送信可
            stKey.setClassName(FREE_RETRIEVAL_STATION_CLASSNAME, "=", "(", "", false);
            stKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", true);

            areaKey.clear();
            areaKey.setAreaNo(areadata.getAreaNo()); // 引当エリア
            stKey.setKey(areaKey);

            // set join
            stKey.setJoin(Station.WH_STATION_NO, Area.WHSTATION_NO);

            // set collect
            stKey.setCollect(Area.AREA_NO);
            stKey.setCollect(new FieldName(Station.STORE_NAME, FieldName.ALL_FIELDS));

            // set order by
            stKey.setStationNoOrder(true);

            Station[] stations = (Station[])handler.find(stKey);

            for (Station station : stations)
            {
                // ユニット出庫運用専用ステーションは対象外
                if (station.isUnitOnly())
                {
                    continue;
                }

                String getAreaNo = (String)station.getValue(Area.AREA_NO);

                List<Station> stationList = _stationMap.get(getAreaNo);
                if (stationList == null)
                {
                    stationList = new ArrayList<Station>();
                }
                stationList.add(station);

                _stationMap.put(getAreaNo, stationList);
            }
        }
    }

    /**
     * 指定されたキーより在庫を検索し、在庫の引当処理を行います。<BR>
     * 作業情報の作成処理は呼び出し元クラスで実装してください。<BR>
     *
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @param lotNo ロットNo.
     * @param planQty 予定出庫数
     * @param ent エンティティ(作業作成時に使用する情報)
     * @param replenishmentAreaType 補充元エリア区分(引当を行うエリア区分)。以下のどちらかを指定して下さい。<BR>
     * <code>SystemDefine.REPLENISHMENT_AREA_TYPE_NORMAL_AREA</code> : 通常エリア<BR>
     * <code>SystemDefine.REPLENISHMENT_AREA_TYPE_REPLENISHMENT_AREA</code> : 補充エリア<BR>
     * @return 引当残数
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    protected int allocateStock(String consignorCode, String itemCode, String lotNo, int planQty, Entity ent,
            String replenishmentAreaType)
            throws CommonException
    {
        DirectDBFinder ddbfinder = null;

        PalletHandler phand = new PalletHandler(getConnection());

        try
        {
            ddbfinder = new DefaultDDBFinder(getConnection(), new Stock());

            // 予定数と引当済数の初期化
            _planQty = 0;
            _allocatedQty = 0;

            // 今回の引当予定数を保持する
            _planQty = planQty;

            // search stock.(ここではまだロックしません)
            searchStocks(ddbfinder, consignorCode, itemCode, lotNo, replenishmentAreaType);

            // 残引当数
            int remainingQty = planQty;

            while (ddbfinder.hasNext())
            {
                Stock[] stks = (Stock[])ddbfinder.getEntities(100);

                for (int i = 0; i < stks.length; i++)
                {
                    // lock stock.
                    StockSearchKey stkKey = new StockSearchKey();
                    stkKey.setStockId(stks[i].getStockId());
                    Stock stk = lock(stkKey);

                    // ロックに失敗した場合はスルー
                    if (stk == null)
                    {
                        continue;
                    }
                    // 搬送ルートチェックがNGの場合はスルー
                    if (!canTransport(stk))
                    {
                        continue;
                    }

                    // パレットの状態チェック
                    if (!canPalletChek(stk))
                    {
                        continue;
                    }

                    int assigningQty = getAssigningQty(stk, remainingQty);

                    // 引当を行う
                    allocateStock(stks[i].getStockId(), assigningQty);

                    // 在庫にパレットが紐つく場合
                    if (!StringUtil.isBlank(stk.getPalletId()))
                    {
                        try
                        {
                            PalletSearchKey plkKey = new PalletSearchKey();
                            plkKey.setPalletId(stk.getPalletId());

                            phand.findPrimary(plkKey);

                            // パレットを引当中、出庫予約にする
                            updatePallet(stk.getPalletId());
                        }
                        catch (NotFoundException e)
                        {
                            // エラーにしない
                        }
                    }

                    // create workinfo
                    createWork(ent, stk, assigningQty);

                    // 残引当数を更新
                    remainingQty -= assigningQty;
                    if (remainingQty == 0)
                    {
                        break;
                    }
                }

                if (remainingQty == 0)
                {
                    break;
                }
            }

            // 今回の引当済数を保持する
            _allocatedQty = _planQty - remainingQty;

            return remainingQty;
        }
        finally
        {
            if (ddbfinder != null)
            {
                ddbfinder.close();
            }
        }
    }

    /**
     * 指定されたキーより在庫を検索し、在庫の引当処理を行います。<BR>
     * 作業情報の作成処理は呼び出し元クラスで実装してください。<BR>
     *
     * @param param <code>AsrsInParameter</code>
     * @return 引当残数
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public int allocateStock(AsrsInParameter param)
            throws CommonException
    {
        DirectDBFinder ddbfinder = null;

        try
        {
            PalletHandler phand = new PalletHandler(getConnection());

            ddbfinder = new DefaultDDBFinder(getConnection(), new Stock());

            // search stock.(ここではまだロックしません)
            searchStocks(ddbfinder, param);

            // 残引当数
            int remainingQty = param.getRetrievalQty();

            while (ddbfinder.hasNext())
            {
                Stock[] stks = (Stock[])ddbfinder.getEntities(100);

                for (int i = 0; i < stks.length; i++)
                {
                    // lock stock.
                    StockSearchKey stkKey = new StockSearchKey();
                    stkKey.setStockId(stks[i].getStockId());
                    Stock stk = lock(stkKey);

                    // ロックに失敗した場合はスルー
                    if (stk == null)
                    {
                        continue;
                    }
                    // 搬送ルートチェックがNGの場合はスルー
                    if (!canTransport(stk, true))
                    {
                        continue;
                    }

                    // パレットの状態をチェック
                    if (!canPalletChek(stk))
                    {
                        continue;
                    }

                    int assigningQty = getAssigningQty(stk, remainingQty);

                    // 引当を行う
                    allocateStock(stk.getStockId(), assigningQty);

                    // 在庫にパレットが紐つく場合
                    if (!StringUtil.isBlank(stk.getPalletId()))
                    {
                        try
                        {
                            // lock stock.
                            PalletSearchKey plkKey = new PalletSearchKey();
                            plkKey.setPalletId(stk.getPalletId());

                            phand.findPrimary(plkKey);

                            // パレットを引当中、出庫予約にする
                            updatePallet(stk.getPalletId());
                        }
                        catch (NotFoundException e)
                        {
                            // エラーにしない
                        }
                    }

                    if (!hasAsrsPack() || !isAsrsArea(stk.getAreaNo()))
                    {
                        // 平置のときの完了処理
                        complateRetrieval(stk, param, assigningQty, remainingQty);
                    }
                    else
                    {
                        // ASRSの時の処理(作業情報作成。開始は最後にまとめて行います)
                        // create workinfo
                        createWork(param, stk, assigningQty);
                        // AS/RS出庫開始を行ったことを記憶する。
                    }

                    // 残引当数を更新
                    remainingQty -= assigningQty;
                    if (remainingQty == 0)
                    {
                        break;
                    }
                }

                if (remainingQty == 0)
                {
                    break;
                }
            }

            return remainingQty;
        }
        finally
        {
            if (ddbfinder != null)
            {
                ddbfinder.close();
            }
        }
    }

    /**
     * 引当対象の在庫情報を検索します。<BR>
     * 荷主コード、商品コード、ロットNo.、をもとに<BR>
     * 対象引当パターンの指定された補充元エリア区分の在庫を検索します。<BR>
     * 検索順は、「引当パタンの優先順位・ロット（空白のものを先に引当てる）・入庫日時」で検索します。<BR>
     * 対象データが存在しない場合はnullを返します。<BR>
     * 又、検索した在庫がASRSの在庫の場合は、紐つくASRSの棚情報の状態が使用可であり、かつ<BR>
     * アクセス可である在庫のみ取得対象とします。<BR>
     *
     * @param finder <code>DirectDBFinder</code>
     * @param consignorCode 荷主コード
     * @param itemCode 商品コード
     * @param lotNo ロットNo.
     * @param replenishmentAreaType 引当を行うエリア区分
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected void searchStocks(DirectDBFinder finder, String consignorCode, String itemCode, String lotNo,
            String replenishmentAreaType)
            throws ReadWriteException
    {
        finder.open(true);

        // ロットNo.が空白の在庫情報から引当てるため、DDBHandlerを使用します。
        // select stock_id, pallet_id, allocation_qty, area_no
        // from (select nullを半角スペースに置き換えたLotNo、他在庫情報 from dnstock where 画面入力条件など)
        // order by 引当優先、ロット空白、入庫日時
        String lotsearch = "";
        if (!StringUtil.isBlank(lotNo))
        {
            lotsearch = "LOT_NO = " + DBFormat.format(lotNo) + " AND ";
        }

        boolean isAsrsPack = getWnSysCon().hasAsrsPack();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("  STOCK_ID, PALLET_ID, ALLOCATION_QTY, AREA_NO ");
        sql.append("FROM ");
        sql.append(" (");
        sql.append(" SELECT ");
        sql.append(" STOCK_ID, DNSTOCK.PALLET_ID PALLET_ID, ALLOCATION_QTY, AREA_NO, ");
        if (getWnSysCon().isFaDaEnabled())
        {
            sql.append(" NVL(LOT_NO, '') AS LOT_NO, STORAGE_DATE, ALLOCATE_PRIORITY ");
        }
        else
        {
            sql.append(" NVL(LOT_NO, ' ') AS LOT_NO, STORAGE_DATE, ALLOCATE_PRIORITY ");
        }

        sql.append(" FROM DNSTOCK, DMALLOCATEPRIORITY");
        if (isAsrsPack)
        {
            // ASRSパッケージありの場合
            sql.append(" ,DNPALLET ,DMSHELF");
        }
        sql.append(" WHERE ");
        sql.append(" CONSIGNOR_CODE = " + DBFormat.format(consignorCode) + " AND ");
        sql.append(" ITEM_CODE = " + DBFormat.format(itemCode) + " AND " + lotsearch);
        sql.append(" DMALLOCATEPRIORITY.ALLOCATE_NO = " + DBFormat.format(_pattern) + " AND ");
        sql.append(" DMALLOCATEPRIORITY.REPLENISHMENT_AREA_TYPE = " + DBFormat.format(replenishmentAreaType) + " AND ");
        sql.append(" ALLOCATION_QTY > 0 AND ");
        if (isAsrsPack)
        {
            // ASRSパッケージありの場合、使用可とアクセス可の条件追加
            sql.append(" (");
            sql.append(" DMSHELF.PROHIBITION_FLAG = " + DBFormat.format(Shelf.PROHIBITION_FLAG_OK) + " OR ");
            sql.append(" DMSHELF.PROHIBITION_FLAG IS NULL "); // 平置の在庫を取得するためNULLを含める
            sql.append(" )");
            sql.append(" AND ");
            sql.append(" (");
            sql.append(" DMSHELF.ACCESS_NG_FLAG = " + DBFormat.format(Shelf.ACCESS_NG_FLAG_OK) + " OR ");
            sql.append(" DMSHELF.ACCESS_NG_FLAG IS NULL "); // 平置の在庫を取得するためNULLを含める
            sql.append(" )");
            sql.append(" AND ");
        }
        sql.append(" DMALLOCATEPRIORITY.ALLOCATE_AREA = DNSTOCK.AREA_NO ");
        if (isAsrsPack)
        {
            // ASRSパッケージありの場合、DMSHELFを結合
            sql.append(" AND DNSTOCK.PALLET_ID = DNPALLET.PALLET_ID (+) ");
            sql.append(" AND DNPALLET.CURRENT_STATION_NO = DMSHELF.STATION_NO (+) ");
        }
        //        if (!WmsParam.MULTI_ALLOCATE_FLAG && isAsrsPack)
        //        {
        //            // 実棚のみを引当対象とする。
        //            sql.append(" AND ");
        //            sql.append(" ( ");
        //            sql.append(" DNPALLET.STATUS_FLAG = " + DBFormat.format(Pallet.PALLET_STATUS_REGULAR) +  " OR ");
        //            sql.append(" DNPALLET.STATUS_FLAG IS NULL ");
        //            sql.append(" ) ");
        //        }

        sql.append(" )");
        sql.append(" ORDER BY");
        sql.append(" ALLOCATE_PRIORITY, ");
        if (RetrievalInParameter.RETRIEVAL_ALLOCATE_PRIORITY_LOT_TRUE == WmsParam.RETRIEVAL_ALLOCATE_PRIORITY)
        {
            sql.append(" LOT_NO NULLS LAST, ");
        }
        sql.append(" STORAGE_DATE ");

        finder.search(String.valueOf(sql));
    }

    /**
     * 引当対象の在庫情報を検索します。<BR>
     * 荷主コード、商品コード、ロットNo.、をもとに<BR>
     * 対象引当パターンの指定された補充元エリア区分の在庫を検索します。<BR>
     * 検索順は、「(エリアの昇順or降順(パラメータで指定))・入庫日時・ロット」で検索します。<BR>
     * 対象データが存在しない場合はnullを返します。<BR>
     * 又、検索した在庫がASRSの在庫の場合は、紐つくASRSの棚情報の状態が使用可であり、かつ<BR>
     * アクセス可である在庫のみ取得対象とします。<BR>
     *
     * @param finder <code>DirectDBFinder</code>
     * @param param エリアNo.,商品コード,ロットNo.
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected void searchStocks(DirectDBFinder finder, AsrsInParameter param)
            throws ReadWriteException
    {
        finder.open(true);

        // ロットNo.が空白の在庫情報から引当てるため、DDBHandlerを使用します。
        // select stock_id, pallet_id, allocation_qty, area_no
        // from (select nullを半角スペースに置き換えたLotNo、他在庫情報 from dnstock where 画面入力条件など)
        // order by 引当優先、ロット空白、入庫日時
        String lotsearch = "";
        if (!StringUtil.isBlank(param.getLotNo()))
        {
            lotsearch = "LOT_NO = " + DBFormat.format(param.getLotNo()) + " AND ";
        }
        String areasearch = "";
        if (!WmsParam.ALL_AREA_NO.equals(param.getAreaNo()))
        {
            areasearch = "AREA_NO = " + DBFormat.format(param.getAreaNo()) + " AND ";
        }
        else
        {
            List<Area> areaList = new ArrayList<Area>();
            areaList.addAll(_areaMap.values());
            boolean first = true;

            for (Area area : areaList)
            {
                if (!first)
                {
                    areasearch += " OR ";
                }
                else
                {
                    areasearch += " ( ";
                    first = false;
                }
                areasearch += "AREA_NO = " + DBFormat.format(area.getAreaNo());
            }
            areasearch += " ) AND ";
        }

        boolean isAsrsPack = getWnSysCon().hasAsrsPack();

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ");
        sql.append("  STOCK_ID, PALLET_ID, ALLOCATION_QTY, AREA_NO ");
        sql.append("FROM ");
        sql.append(" (");
        sql.append(" SELECT ");
        sql.append(" STOCK_ID, DNSTOCK.PALLET_ID PALLET_ID, ALLOCATION_QTY, AREA_NO, ");
        sql.append(" NVL(LOT_NO, '') AS LOT_NO, STORAGE_DATE ");
        sql.append(" FROM DNSTOCK");
        if (isAsrsPack)
        {
            // ASRSパッケージありの場合
            sql.append(" ,DNPALLET ,DMSHELF");
        }
        sql.append(" WHERE ");
        sql.append(areasearch);
        sql.append(" ITEM_CODE = " + DBFormat.format(param.getItemCode()) + " AND " + lotsearch);
        sql.append(" ALLOCATION_QTY > 0 ");
        if (isAsrsPack)
        {
            // ASRSパッケージありの場合、使用可とアクセス可の条件追加
            sql.append(" AND ");
            sql.append(" (");
            sql.append(" DMSHELF.PROHIBITION_FLAG = " + DBFormat.format(Shelf.PROHIBITION_FLAG_OK) + " OR ");
            sql.append(" DMSHELF.PROHIBITION_FLAG IS NULL "); // 平置の在庫を取得するためNULLを含める
            sql.append(" )");
            sql.append(" AND ");
            sql.append(" (");
            sql.append(" DMSHELF.ACCESS_NG_FLAG = " + DBFormat.format(Shelf.ACCESS_NG_FLAG_OK) + " OR ");
            sql.append(" DMSHELF.ACCESS_NG_FLAG IS NULL "); // 平置の在庫を取得するためNULLを含める
            sql.append(" )");

            // ASRSパッケージありの場合、DMSHELFを結合
            sql.append(" AND DNSTOCK.PALLET_ID = DNPALLET.PALLET_ID (+) ");
            sql.append(" AND DNPALLET.CURRENT_STATION_NO = DMSHELF.STATION_NO (+) ");
        }
        //
        //        if (!WmsParam.MULTI_ALLOCATE_FLAG && isAsrsPack)
        //        {
        //            // 実棚のみを引当対象とする。
        //            sql.append(" AND ");
        //            sql.append(" ( ");
        //            sql.append(" DNPALLET.STATUS_FLAG = " + DBFormat.format(Pallet.PALLET_STATUS_REGULAR)  +  " OR " );
        //            sql.append(" DNPALLET.STATUS_FLAG IS NULL ");
        //            sql.append(" ) ");
        //        }


        sql.append(" )");
        sql.append("ORDER BY");
        if (RetrievalInParameter.ITEM_RET_ALLOC_PRIORITY_AREA_ASC == WmsParam.ITEM_RETRIEVAL_ALLOCATE_PRIORITY)
        {
            sql.append(" AREA_NO,");
        }
        if (RetrievalInParameter.ITEM_RET_ALLOC_PRIORITY_AREA_DESC == WmsParam.ITEM_RETRIEVAL_ALLOCATE_PRIORITY)
        {
            sql.append(" AREA_NO DESC,");
        }
        if (RetrievalInParameter.RETRIEVAL_ALLOCATE_PRIORITY_LOT_TRUE == WmsParam.RETRIEVAL_ALLOCATE_PRIORITY)
        {
            sql.append(" LOT_NO NULLS LAST, ");
        }
        sql.append(" STORAGE_DATE ");

        finder.search(String.valueOf(sql));
    }

    /**
     * 在庫情報の更新を行います。<BR>
     *
     * @param stockId 在庫ID
     * @param assigningQty 引当数
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws NotFoundException 更新対象データが存在しない場合にスローされます。
     */
    private void allocateStock(String stockId, int assigningQty)
            throws ReadWriteException,
                NotFoundException
    {
        StockAlterKey stkAltKey = new StockAlterKey();

        stkAltKey.clear();
        stkAltKey.setStockId(stockId);
        stkAltKey.updateAllocationQtyWithColumn(Stock.ALLOCATION_QTY, BigDecimal.valueOf(-1 * assigningQty)); // 引当の為マイナス値をセット
        stkAltKey.updateLastUpdatePname(getCallerName());
        new StockHandler(getConnection()).modify(stkAltKey);
    }

    /**
     * 指定されたパラメータで平置き出庫の完了処理を行います。
     *
     * @param assigningQty 出庫数
     * @throws ScheduleException
     * @throws ReadWriteException
     * @throws DataExistsException
     * @throws NoPrimaryException
     * @throws NotFoundException
     */
    protected void complateRetrieval(Stock stock, AsrsInParameter param, int assigningQty, int remainingQty)
            throws ReadWriteException,
                ScheduleException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException
    {
        String jobType = SystemDefine.JOB_TYPE_NOPLAN_RETRIEVAL;
        // 在庫情報の更新(在庫数が0になる場合は削除)
        StockController stockCtr = new StockController(getConnection(), getCaller());
        stockCtr.stockUpdateRetrieval(stock, jobType, assigningQty, _wnSysCon.getWorkDay(), true,
                param.getWmsUserInfo());

        // 入出庫実績送信情報の作成
        createHostSend(stock, param, jobType, _wnSysCon.getWorkDay(), assigningQty);
    }

    /**
     * 実績送信情報を作成します。
     *
     * @param param 実績元情報
     *        <ol>
     *        以下の情報が参照されます
     *        <li>荷主コード
     *        <li>商品コード
     *        <li>エリアNo
     *        <li>棚No
     *        <li>予定ロットNo
     *        <li>実績数
     *        <li>ロットNo
     *        <li>作業秒数
     *        <li>ハードウェア区分
     *        <li>理由区分
     *        <li>ユーザID
     *        <li>ユーザ名
     *        <li>端末No
     *        </ol>
     *
     * @param settingUkey 設定単位キー
     * @param jobType 作業タイプ
     * @param workDay 作業日
     * @throws NoPrimaryException 荷主コードに対して荷主が複数
     * @throws ReadWriteException データベースエラー
     * @throws DataExistsException
     *         同様の実績がすでに登録済みのときスローされます。
     * @throws ScheduleException システムに整合性がないときスローされます。
     */
    protected void createHostSend(Stock stock, AsrsInParameter param, String jobType, String workDay, int assigningQty)
            throws ReadWriteException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        // getting keys from in-parameter
        String consignorCode = stock.getConsignorCode();
        String itemCode = stock.getItemCode();
        int reasonType = param.getReasonType();

        // get consignor name
        String consignorName = getConsignorName(consignorCode);

        // get item master info
        ItemHandler ith = new ItemHandler(getConnection());
        ItemSearchKey itkey = new ItemSearchKey();
        itkey.setConsignorCode(consignorCode);
        itkey.setItemCode(itemCode);

        Item itement = (Item)ith.findPrimary(itkey);
        if (null == itement)
        {
            // 商品マスタが不整合であっても、動作は続ける
            itement = new Item();
            // 6026102={0}テーブルに({1}, {2})の詳細情報が見つかりません。
            Object[] mps = {
                    Item.STORE_NAME,
                    consignorCode,
                    itemCode,
            };
            RmiMsgLogClient.write(6026102, getClass().getName(), mps);
        }

        // get reason master info
        Reason reasonent = new Reason();
        ReasonHandler reh = new ReasonHandler(getConnection());
        ReasonSearchKey rekey = new ReasonSearchKey();
        rekey.setReasonType(reasonType);
        reasonent = (Reason)reh.findPrimary(rekey);

        // get user info from master
        String userId = param.getUserId();
        String userName = param.getUserName();

        // find hardware_type
        AreaController arc = new AreaController(getConnection(), getClass());
        String area_type = arc.getAreaType(stock.getAreaNo());
        if (area_type.equals(SystemDefine.AREA_TYPE_ASRS))
        {
            param.setHardwareType(SystemDefine.HARDWARE_TYPE_ASRS);
        }
        else if (area_type.equals(SystemDefine.AREA_TYPE_FLOOR))
        {
            param.setHardwareType(SystemDefine.HARDWARE_TYPE_LIST);
        }

        // getting next job number
        String jobNo = _sequence.nextWorkInfoJobNo();
        // 集約作業No.採番
        String collectJobNo = _sequence.nextWorkInfoCollectJobNo();

        // create hostsend
        HostSend hsendent = new HostSend();
        hsendent.setWorkDay(workDay);
        hsendent.setJobNo(jobNo);
        hsendent.setCollectJobNo(collectJobNo);
        hsendent.setSettingUnitKey(getSettingUkey(true));
        hsendent.setStockId(stock.getStockId());
        hsendent.setJobType(jobType);
        hsendent.setStatusFlag(HostSend.STATUS_FLAG_COMPLETION);
        hsendent.setPlanDay(workDay);
        hsendent.setConsignorCode(consignorCode);
        hsendent.setConsignorName(consignorName);
        hsendent.setPlanAreaNo(stock.getAreaNo());
        hsendent.setPlanLocationNo(stock.getLocationNo());

        hsendent.setItemCode(itemCode);
        hsendent.setItemName(itement.getItemName());
        hsendent.setJan(itement.getJan());
        hsendent.setItf(itement.getItf());
        hsendent.setBundleItf(itement.getBundleItf());
        hsendent.setEnteringQty(itement.getEnteringQty());
        hsendent.setBundleEnteringQty(itement.getBundleEnteringQty());
        hsendent.setPlanLotNo(stock.getLotNo());
        hsendent.setPlanQty(assigningQty);
        hsendent.setResultQty(assigningQty);
        hsendent.setShortageQty(0);
        hsendent.setResultAreaNo(stock.getAreaNo());
        hsendent.setResultLocationNo(stock.getLocationNo());
        hsendent.setResultLotNo(stock.getLotNo());
        hsendent.setReportFlag(HostSend.REPORT_FLAG_NOT_REPORT);
        hsendent.setUserId(userId);
        hsendent.setUserName(userName);
        hsendent.setHardwareType(param.getHardwareType());
        hsendent.setTerminalNo(param.getTerminalNo());
        hsendent.setWorkSecond(param.getWorkSeconds());
        hsendent.setRegistPname(getCallerName());
        hsendent.setRegistDate(new SysDate());
        hsendent.setLastUpdatePname(getCallerName());

        hsendent.setReasonType(reasonType);
        hsendent.setReasonName(reasonent.getReasonName());

        // excludes StockId

        HostSendController hsc = new HostSendController(getConnection(), getCaller());
        hsc.insert(hsendent);
    }

    /**
     * 実棚、異常棚のパレットを出庫予約に更新します。<BR>
     *
     * @param palletId パレットID
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws NotFoundException 更新対象データが存在しない場合にスローされます。
     */
    private void updatePallet(String palletId)
            throws ReadWriteException,
                NotFoundException
    {
        PalletAlterKey pltAltKey = new PalletAlterKey();

        pltAltKey.setPalletId(palletId);
        pltAltKey.setStatusFlag(new String[] {
                Pallet.PALLET_STATUS_REGULAR, // 実棚
                Pallet.PALLET_STATUS_IRREGULAR
        // 異常棚
                }, true);
        pltAltKey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN); // 出庫予約
        pltAltKey.updateAllocationFlag(Pallet.ALLOCATION_FLAG_ALLOCATED); // 引当
        pltAltKey.updateLastUpdatePname(getCallerName()); // 最終更新PG

        new PalletHandler(getConnection()).modify(pltAltKey);
    }

    /**
     * 引当可能な在庫かのチェックを行います。<BR>
     * 引当可能な在庫の場合、trueを返します。<BR>
     * 平置き、移動ラックの在庫の場合チェックは行いません。<BR>
     * また、搬送ルートチェックを行います。
     * ルートチェックの結果NGだった場合、falseを返します。<BR>
     *
     * @param stock 在庫情報
     * @return 引当可能な在庫かどうか
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 不正なパラメータが検出された場合に通知されます。
     * @throws NotFoundException ステーションに該当のクラスが見つからなかった場合に通知されます。
     * @throws NoPrimaryException 1件しか取得できないはずのデータが複数件取得できた場合にスローされます。
     */
    protected boolean canTransport(Stock stock)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException,
                NoPrimaryException
    {
        return canTransport(stock, false);
    }

    /**
     * 引当可能な在庫かのチェックを行います。<BR>
     * 引当可能な在庫の場合、trueを返します。<BR>
     * 平置き、移動ラックの在庫の場合チェックは行いません。<BR>
     * また、搬送ルートチェックを行います。
     * ルートチェックの結果NGだった場合、falseを返します。<BR>
     *
     * @param stock 在庫情報
     * @param groupSt 代表ステーションを対象とするか
     * @return 引当可能な在庫かどうか
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 不正なパラメータが検出された場合に通知されます。
     * @throws NotFoundException ステーションに該当のクラスが見つからなかった場合に通知されます。
     * @throws NoPrimaryException 1件しか取得できないはずのデータが複数件取得できた場合にスローされます。
     */
    protected boolean canTransport(Stock stock, boolean groupSt)
            throws ReadWriteException,
                InvalidDefineException,
                NotFoundException,
                NoPrimaryException
    {
        // ASRSパッケージなしもしくは、ASRSエリアではないならチェックしない
        if (!hasAsrsPack() || !isAsrsArea(stock.getAreaNo()))
        {
            return true;
        }

        Pallet pallet = getPalletData(stock.getPalletId());

        // エリアに紐つくステーションリストを取得
        List<Station> stationList = _stationMap.get(stock.getAreaNo());

        if (stationList == null || stationList.size() == 0)
        {
            throw new InvalidDefineException("no station in area : " + stock.getAreaNo());
        }

        // ルートチェック済みのステーションを保持する(チェックされたステーションはリストの最後へ追加する)
        List<Station> checkedStations = new ArrayList<Station>(stationList);

        for (Station station : stationList)
        {
            Station target = StationFactory.makeStation(getConnection(), station.getStationNo());

            // チェックした要素を一番最後のリスト要素へ入れ替える(チェックする順番を最後にする)
            checkedStations.remove(station);
            checkedStations.add(station);

            //            // 出庫可能ステーションかどうかのチェック(在庫確認中チェックあり、オンラインチェックあり、モードチェックなし、中断中チェックなし)
            //            if (_rtCon.isRetrievalStation(target, false, true, true, true))
            //            {
            //                // ユニット出庫運用専用ステーションは対象外
            //                if (!target.isUnitOnly())
            //                {
            //                    // 搬送ルートチェック実行
            //                    if (_rtCon.retrievalDetermin(pallet, target))
            //                    {
            //                        // 入れ替えたリストを再設定する(次のチェックで使用する)
            //                        _stationMap.put(stock.getAreaNo(), checkedStations);
            //                        return true;
            //                    }
            //                }
            //            }

            // 出庫可能ステーションかどうかのチェック
            // (在庫確認中チェックあり、オンラインチェックあり、モード、中断中チェックは_modeCheckに従う)
            if (!_rtCon.isRetrievalStation(target, false, true, !_modeCheck, !_modeCheck))
            {
                continue;
            }

            // ユニット出庫運用専用ステーションは対象外
            if (target.isUnitOnly())
            {
                continue;
            }

            // 代表ステーションのチェック
            if (!groupSt)
            {
                // 代表ステーションは対象外
                if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(target.getWorkplaceType()))
                {
                    continue;
                }
            }

            // 搬送ルートチェック実行
            if (_rtCon.retrievalDetermin(pallet, target))
            {
                // 入れ替えたリストを再設定する(次のチェックで使用する)
                _stationMap.put(stock.getAreaNo(), checkedStations);
                return true;
            }
        }

        return false;
    }

    /**
     * パレットの状態をチェック
     * AS/RSエリアの在庫の場合のみチェックする
     * マルチ引当ありの場合はパレットの状態を実棚以外からも引当可能とする
     * マルチ引当なしの場合はパレットの状態とチェックする。
     * 今回設定した搬送で同一パレットからの引当が無いかチェックする。
     * 同一設定の場合、同一パレットからの引当と可能とする。
     *
     * @param stock 在庫情報
     * @return 引当可能な在庫かどうか
     * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
     * @throws InvalidDefineException 不正なパラメータが検出された場合に通知されます。
     * @throws NoPrimaryException 1件しか取得できないはずのデータが複数件取得できた場合にスローされます。
     *
     */
    protected boolean canPalletChek(Stock stock)
            throws ReadWriteException,
                InvalidDefineException,
                NoPrimaryException
    {
        // ASRSパッケージなしもしくは、ASRSエリアではないならチェックしない
        if (!hasAsrsPack() || !isAsrsArea(stock.getAreaNo()))
        {
            return true;
        }

        // マルチ引当あり・なし
        if (WmsParam.MULTI_ALLOCATE_FLAG)
        {
            // マルチ引当ありの場合、パレットの棚状態を参照しない
            return true;
        }
        else
        {
            // マルチ引当なしの場合、パレットの棚状態を参照する
            Pallet pallet = getPalletData(stock.getPalletId());

            if (SystemDefine.PALLET_STATUS_REGULAR.equals(pallet.getStatusFlag()))
            {
                // 実棚の場合、引当対象在庫とする。
                return true;
            }
            else
            {
                // 実棚以外の場合
                // スケジュールNo.から今回の引当設定を取得する。
                if (StringUtil.isBlank(_scheduleNo))
                {
                    // 今回の引当設定がない場合、引当対象外とする。
                    return false;
                }

                CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
                CarryInfoHandler ciHnad = new CarryInfoHandler(getConnection());
                ciKey.setPalletId(stock.getPalletId());
                // パレットIDから、搬送情報を取得する。
                CarryInfo[] cis = (CarryInfo[])ciHnad.find(ciKey);

                for (CarryInfo ci : cis)
                {
                    // 今回設定した搬送情報のスケジュールNo.とパレットから取得した搬送情報のスケジュールNo.を比較
                    if (_scheduleNo.equals(ci.getScheduleNo()))
                    {
                        // 同一スケジュールの場合、引当対象在庫とする。
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * getting name of consignor.
     * @param consignorCode
     *        荷主コード
     * @return 荷主名称
     * @throws NoPrimaryException
     *         対象の荷主が複数見つかった
     * @throws ReadWriteException
     *         データベースアクセスエラー
     */
    protected String getConsignorName(String consignorCode)
            throws ReadWriteException,
                NoPrimaryException
    {
        ConsignorHandler coh = new ConsignorHandler(getConnection());
        ConsignorSearchKey cokey = new ConsignorSearchKey();

        cokey.setConsignorNameCollect();
        cokey.setConsignorCode(consignorCode);

        Consignor coent = (Consignor)coh.findPrimary(cokey);

        // 荷主マスタが不整合であっても、動作は続ける
        if (null == coent)
        {
            // 6026101={0}テーブルに({1})の詳細情報が見つかりません。
            Object[] mps = {
                    Consignor.STORE_NAME,
                    consignorCode,
            };
            RmiMsgLogClient.write(6026101, getClass().getName(), mps);
            return "";
        }
        return coent.getConsignorName();
    }

    /**
     * パレット情報を取得します。<BR>
     * パレットIDをキーにパレット情報を検索します。
     * 対象のパレットが見つからなかった場合はScheduleExceptionを通知します。
     *
     * @param palletId パレットID
     * @return パレット情報インスタンス
     * @throws ReadWriteException データベースとの接続で異常が発生した場合にスローされます。
     * @throws InvalidDefineException 存在しないパレットの場合にスローされます。
     * @throws NoPrimaryException 1件しか取得できないはずのデータが複数件取得できた場合にスローされます。
     */
    private Pallet getPalletData(String palletId)
            throws ReadWriteException,
                InvalidDefineException,
                NoPrimaryException
    {
        PalletSearchKey pltKey = new PalletSearchKey();

        pltKey.setPalletId(palletId);

        Pallet plt = (Pallet)new PalletHandler(getConnection()).findPrimary(pltKey);
        if (plt == null)
        {
            throw new InvalidDefineException("pallet is not found. palletId is " + palletId);
        }

        return plt;
    }

    /**
     * 搬送情報が存在するかどうかを判定します。<BR>
     * インスタンス変数で保持しているスケジュールNo.が未設定の場合は存在しないと判断します。<BR>
     * スケジュールNo.が設定されている場合は、パラメータのパレットIDとスケジュールNo.を条件に<BR>
     * 搬送情報を検索します。<BR>
     *
     * @param palletId パレットID
     * @return 搬送情報が存在する場合はture、存在しない場合はfalse
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected boolean hasCarryInfo(String palletId)
            throws ReadWriteException
    {
        if (StringUtil.isBlank(_scheduleNo))
        {
            _scheduleNo = _sequence.nextScheduleNo();
            return false;
        }
        else
        {
            CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
            ciKey.setScheduleNo(_scheduleNo);
            ciKey.setPalletId(palletId);
            // 同一スケジュール、同一パレットのデータが存在しない場合は搬送データを作成する
            if (new CarryInfoHandler(getConnection()).count(ciKey) == 0)
            {
                return false;
            }
            return true;
        }
    }

    /**
     * 搬送情報の編集処理を行います。<BR>
     * 作業種別出庫で作成します。<BR>
     *
     * @param stk 在庫情報
     * @return 搬送情報インスタンス（まだDBには登録していません）
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws NoPrimaryException パレットIDを条件にパレットが複数存在した場合にスローされます。
     * @throws DataExistsException 搬送情報が同一キーで既に作成済みであった場合にスローされます。
     * @throws ScheduleException 処理エラーが発生した場合にスローされます。
     */
    protected CarryInfo editCarryInfo(Stock stk, String priority)
            throws ReadWriteException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        AreaController areaCtrl = new AreaController(getConnection(), getCaller());

        CarryInfo carryInfo = new CarryInfo();

        // 搬送キー
        carryInfo.setCarryKey(_sequence.nextCarryKey());
        // パレットID
        carryInfo.setPalletId(stk.getPalletId());
        // 作業種別
        carryInfo.setWorkType(CarryInfo.WORK_TYPE_RETRIEVAL);
        // 出庫グループNo.
        carryInfo.setGroupNo(0);
        // 搬送状態(引当)
        carryInfo.setCmdStatus(CarryInfo.CMD_STATUS_ALLOCATION);
        // 優先区分
        if (WmsParam.MULTI_ALLOCATE_FLAG)
        {
            // マルチ引当ありの場合 (通常)
            carryInfo.setPriority(CarryInfo.PRIORITY_NORMAL);
        }
        else
        {
            if (StringUtil.isBlank(priority))
            {
                // マルチ引当ありの場合 (通常)
                carryInfo.setPriority(CarryInfo.PRIORITY_NORMAL);
            }
            else
            {
                // マルチ引当なしの場合 (選択された区分)
                carryInfo.setPriority(priority);
            }
        }

        // 再入庫フラグ(後で更新します)
        carryInfo.setRestoringFlag(CarryInfo.RESTORING_FLAG_SAME_LOC);
        // 出庫指示詳細(後で更新します)
        carryInfo.setRetrievalDetail(CarryInfo.RETRIEVAL_DETAIL_PICKING);
        // 搬送区分(出庫)
        carryInfo.setCarryFlag(CarryInfo.CARRY_FLAG_RETRIEVAL);
        // 搬送元ステーションNo.
        carryInfo.setSourceStationNo(areaCtrl.toAsrsLocation(stk.getAreaNo(), stk.getLocationNo()));
        // キャンセル要求区分(未要求)
        carryInfo.setCancelRequest(CarryInfo.CANCEL_REQUEST_UNDEMAND);
        // スケジュールNo.
        carryInfo.setScheduleNo(_scheduleNo);
        // 作業No.(出庫作業No)
        carryInfo.setWorkNo(_sequence.nextRetrievalWorkNo());
        // 出庫ステーション
        carryInfo.setRetrievalStationNo(areaCtrl.toAsrsLocation(stk.getAreaNo(), stk.getLocationNo()));

        // アイルステーションNo.
        // 搬送元ステーション（アイル）のステーションNo.（RM）を取得します
        if (_rtCon.getSrcStation() != null)
        {
            carryInfo.setAisleStationNo(_rtCon.getSrcStation().getStationNo());
        }
        if (_rtCon.getDestStation() != null)
        {
            // 搬送先ステーションNo.
            carryInfo.setDestStationNo(_rtCon.getDestStation().getStationNo());
            // 最終ステーションNo.
            carryInfo.setEndStationNo(_rtCon.getDestStation().getStationNo());
        }
        // 登録PG
        carryInfo.setRegistPname(getCallerName());
        // 最終更新PG
        carryInfo.setLastUpdatePname(getCallerName());

        return carryInfo;
    }

    /**
     * パレットIDとインスタンス変数で保持しているスケジュールNo.に該当する搬送キーを取得します。<BR>
     *
     * @param palletId パレットID
     * @return 搬送キー
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException 搬送情報が取得できない、もしくは複数存在する場合にスローされます。
     */
    protected String getCarryKey(String palletId)
            throws ReadWriteException,
                InvalidDefineException
    {
        CarryInfoSearchKey ciKey = new CarryInfoSearchKey();
        ciKey.setScheduleNo(_scheduleNo);
        ciKey.setPalletId(palletId);
        CarryInfo[] carries = (CarryInfo[])new CarryInfoHandler(getConnection()).find(ciKey);

        if (carries == null || carries.length != 1)
        {
            throw new InvalidDefineException("carry info record is invalid");
        }
        return carries[0].getCarryKey();
    }

    /**
     * 搬送情報を作成したかどうかを判定します。<BR>
     *
     * @return 搬送情報を作成した場合はtrue、していない場合はfalse
     */
    private boolean isCreateCarryInfo()
    {
        if (!StringUtil.isBlank(_scheduleNo))
        {
            return true;
        }
        return false;
    }

    /**
     * 在庫情報をロックします。
     * ロック待機する時間、リトライする回数は<code>WmsParam</code>に定義されています。
     * @param stkKey 在庫情報検索キー
     * @return ロックした在庫情報
     * @throws ReadWriteException データベースエラーが発生した場合にスローされます。
     * @throws LockTimeOutException ロックタイムアウトが発生した場合にスローされます。
     * @throws ScheduleException スケジュール処理でエラーが発生した場合にスローされます。
     */
    private Stock lock(StockSearchKey stkKey)
            throws ReadWriteException,
                LockTimeOutException,
                ScheduleException
    {
        StockController stkc = new StockController(getConnection(), getCaller());
        Stock[] stocks = null;
        try
        {
            stocks =
                    (Stock[])stkc.retryLock(stkKey, new StockHandler(getConnection()), WmsParam.ALLOCATE_SLEEP_TIME,
                            WmsParam.ALLOCATE_RETRY_COUNT);
        }
        catch (LockTimeOutException e)
        {
            return null;
        }
        if (stocks == null || stocks.length == 0)
        {
            return null;
        }
        return stocks[0];
    }

    /**
     * ASRSパッケージが導入されているかどうかを判定します。<BR>
     *
     * @return ASRSパッケージ導入あり : true
     */
    protected boolean hasAsrsPack()
    {
        return _wnSysCon.hasAsrsPack();
    }

    /**
     * 指定されたエリアがASRSエリアかどうかの判定を行います。<BR>
     *
     * @param areaNo 判定するエリア
     * @return ture : ASRSエリア false : ASRSエリアではない
     */
    public boolean isAsrsArea(String areaNo)
    {
        Area area = _areaMap.get(areaNo);
        return Area.AREA_TYPE_ASRS.equals(area.getAreaType());
    }

    /**
     * _areaMapのエリアが全てASRSエリアかどうかを判定します。<BR>
     *
     * @return 全てASRSエリア : true それ以外 : false
     */
    public boolean isAllAsrsArea()
    {
        List<Area> areaList = new ArrayList<Area>();
        areaList.addAll(_areaMap.values());

        for (Area area : areaList)
        {
            if (!Area.AREA_TYPE_ASRS.equals(area.getAreaType()))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * _areaMap内にASRSのエリアが存在するかどうかを判定します。<BR>
     *
     * @return ASRSのエリアが存在する場合 : true 存在しない場合 : false
     */
    public boolean hasAsrsArea()
    {
        List<Area> areaList = new ArrayList<Area>();
        areaList.addAll(_areaMap.values());

        for (Area area : areaList)
        {
            if (Area.AREA_TYPE_ASRS.equals(area.getAreaType()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 端末に対応するステーションの検索を行います。<BR>
     *
     * @return 検索結果
     * @throws CommonException
     *             データベースとの接続で異常が発生した場合に通知されます。
     */
    protected Station[] getRetrievalStation(String termNo)
            throws CommonException
    {
        StationHandler handler = new StationHandler(getConnection());
        StationSearchKey sKey = new StationSearchKey();

        // 副問い合わせ用TerminalAreaの検索条件のセットをする。
        TerminalAreaSearchKey tKey = new TerminalAreaSearchKey();
        tKey.setStationNoCollect();
        tKey.setTerminalNo(termNo);

        // set collect
        sKey.setParentStationNoCollect();
        sKey.setStationNoCollect();
        sKey.setStationNameCollect();
        sKey.setWhStationNoCollect();
        sKey.setAisleStationNoCollect();

        // 副問い合わせ用Stationの検索条件をセットする。
        sKey.setKey(Station.STATION_NO, tKey);

        // set where
        sKey.setStationType(Station.STATION_TYPE_OUT, "=", "(", "", false);
        sKey.setStationType(Station.STATION_TYPE_INOUT, "=", "", ")", true);
        sKey.setWorkplaceType(Station.WORKPLACE_TYPE_FLOOR);
        sKey.setSendable(Station.SENDABLE_TRUE);

        // set order by
        sKey.setParentStationNoOrder(true);
        sKey.setStationNoOrder(true);

        // 対象となるステーションを取得する
        Station[] stations = (Station[])handler.find(sKey);

        return stations;

    }

    /**
     * アイルに紐つくステーションを取得します。<BR>
     *
     * @param aisle アイル
     * @return ステーション
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException アイルに紐つくステーションが存在しない場合にスローされます。
     */
    protected Station[] getStations(Aisle aisle)
            throws ReadWriteException,
                InvalidDefineException
    {
        StationSearchKey stKey = new StationSearchKey();

        // set where
        stKey.setWhStationNo(aisle.getWhStationNo());
        stKey.setSendable(Station.SENDABLE_TRUE);

        stKey.setAisleStationNo(new String[] {
                aisle.getStationNo(),
                null
        }, true);

        // set join
        stKey.setJoin(Station.CONTROLLER_NO, GroupController.CONTROLLER_NO);

        // set collect
        stKey.setCollect(new FieldName(Station.STORE_NAME, FieldName.ALL_FIELDS));
        stKey.setCollect(GroupController.CONTROLLER_NO);
        stKey.setCollect(GroupController.STATUS_FLAG);

        Station[] stations = (Station[])new StationHandler(getConnection()).find(stKey);

        if (ArrayUtil.isEmpty(stations))
        {
            throw new InvalidDefineException("no station info in aisle : " + aisle.getStationNo());
        }

        return stations;
    }

    /**
     * 入力されたステーションのモードが出庫モードかどうかを判断します。<BR>
     * ステーションのモードが出庫モードでかつモード切替要求中でなければ出庫モードと判断し、trueを返します。<BR>
     * モードを持たないステーションを指定された場合、無条件でtrueを返します。<BR>
     * 本処理はretrievalStationCheckメソッドから呼び出されるので、直接呼び出す必要はありません。<BR>
     * @param station ステーションエンティティ
     * @return 入力されたステーションのモードが出庫ならばtrueそれ以外ならfalseを返します。
     */
    protected boolean isRetrievalModeEnable(Station station)
    {
        if (Station.STATION_TYPE_INOUT.equals(station.getStationType()))
        {
            // モード切替機能あり
            if (Station.MODE_TYPE_AGC_CHANGE.equals(station.getModeType())
                    || Station.MODE_TYPE_AWC_CHANGE.equals(station.getModeType()))
            {
                // 出庫モードかチェック
                if (Station.CURRENT_MODE_RETRIEVAL.equals(station.getCurrentMode()))
                {
                    // モード切替要求中ではない
                    if (Station.MODE_REQUEST_NONE.equals(station.getModeRequest()))
                    {
                        // 出庫モード
                        return true;
                    }
                }
                // 出庫モード以外かモード切替要求中
                return false;
            }
        }

        // モード管理なし
        return true;
    }

    /**
     * 指定されたステーションの状態が正常かどうかを判定します。
     *
     * @param status 判定するテーションの状態
     * @return 正常な場合ture、それ以外false
     */
    protected boolean isStationStatusNormal(Object status)
    {
        if (Station.STATION_STATUS_NORMAL.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 指定されたステーションの状態が異常かどうかを判定します。
     *
     * @param status 判定するテーションの状態
     * @return 異常な場合ture、それ以外false
     */
    protected boolean isStationStatusError(Object status)
    {
        if (Station.STATION_STATUS_ERROR.equals(status) || Station.STATION_STATUS_DISCONNECTED.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 指定されたステーションが中断中かどうかを判定します。
     *
     * @param status 判定するテーションの状態
     * @return 異常な場合ture、それ以外false
     */
    protected boolean isStationSuspendOn(Object status)
    {
        if (Station.SUSPEND_ON.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 1件でもオフラインのAGCが存在するかどうかを判定します。<BR>
     *
     * @return 1件でもオフラインのグループコントローラが存在するなら : true それ以外 : false
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException アイルに紐つくステーションが存在しない場合にスローされます。
     */
    public boolean hasOffLineAGC()
            throws InvalidDefineException,
                ReadWriteException
    {
        List<Area> areaList = new ArrayList<Area>();
        areaList.addAll(_areaMap.values());

        for (Area area : areaList)
        {
            // 平置きの場合はチェックしない
            if (!isAsrsArea(area.getAreaNo()))
            {
                continue;
            }

            // ステーションの取得
            List<Station> stationList = _stationMap.get(area.getAreaNo());

            Station[] stations = new Station[stationList.size()];
            stationList.toArray(stations);

            for (Station station : stations)
            {
                // オフラインの場合
                if (isOffLine(station.getValue(GroupController.STATUS_FLAG)))
                {
                    return true;
                }
            }

            // ステーションに紐つくアイルの取得
            Aisle[] aisles = getAisles(stations);
            for (Aisle aisle : aisles)
            {
                // オフラインの場合
                if (isOffLine(aisle.getValue(GroupController.STATUS_FLAG)))
                {
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * _areaMapにオンラインの正常な設備が存在するかどうかを判定します。<BR>
     *
     * @return グループコントローラがオンラインでかつ1件でも正常な状態の設備あり : true それ以外 : false
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException エリアに紐つくアイルが存在しない場合又は、アイルに紐つくステーションが存在しない場合にスローされます。
     */
    public boolean hasOnlineNormalStatusEquipment()
            throws ReadWriteException,
                InvalidDefineException
    {
        List<Area> areaList = new ArrayList<Area>();
        areaList.addAll(_areaMap.values());

        for (Area area : areaList)
        {
            // 平置きの場合はチェックしない
            if (!isAsrsArea(area.getAreaNo()))
            {
                continue;
            }

            // ステーションの取得
            List<Station> stationList = _stationMap.get(area.getAreaNo());

            Station[] stations = new Station[stationList.size()];
            stationList.toArray(stations);

            // ステーションのチェック
            for (Station station : stations)
            {
                if (Station.WORKPLACE_TYPE_MAIN_STATION.equals(station.getWorkplaceType()))
                {
                    // 代表ステーションに紐づくステーションNo.を取得する
                    StationSearchKey sskey = new StationSearchKey();
                    StationHandler sth = new StationHandler(getConnection());
                    sskey.setParentStationNo(station.getWorkplaceType());
                    sskey.setStationNoOrder(true);
                    Station[] gStations = (Station[])sth.find(sskey);
                    for (Station gStation : gStations)
                    {
                        if (!isStationStatusNormal(gStation.getStatus()) || isStationSuspendOn(gStation.getSuspend()))
                        {
                            // ステーションが正常でない場合、または中断中の場合
                            continue;
                        }
                    }
                }
                else
                {
                    if (!isStationStatusNormal(station.getStatus()) || isStationSuspendOn(station.getSuspend()))
                    {
                        // ステーションが正常でない場合、または中断中の場合
                        continue;
                    }
                }

                Station[] st = {
                    station
                };
                // アイルの取得
                Aisle[] aisles = getAisles(st);

                for (Aisle aisle : aisles)
                {
                    // AGCがオンラインかどうか
                    if (!isOnLine(aisle.getValue(GroupController.STATUS_FLAG)))
                    {
                        // AGCがオンラインでない場合
                        continue;
                    }

                    // アイルのチェック
                    if (isAisleStatusNormal(aisle.getStatus()))
                    {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    /**
     * _areaMapにオンラインの異常な設備があるかどうかを判定します。<BR>
     *
     * @return 異常の設備あり : true それ以外 : false
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException アイルに紐つくステーションが存在しない場合にスローされます。
     */
    public boolean hasOnlineStatusErrorEquipment()
            throws InvalidDefineException,
                ReadWriteException
    {
        List<Area> areaList = new ArrayList<Area>();
        areaList.addAll(_areaMap.values());

        for (Area area : areaList)
        {
            // 平置きの場合はチェックしない
            if (!isAsrsArea(area.getAreaNo()))
            {
                continue;
            }

            // ステーションの取得
            List<Station> stationList = _stationMap.get(area.getAreaNo());

            Station[] stations = new Station[stationList.size()];
            stationList.toArray(stations);

            // アイルの取得
            Aisle[] aisles = getAisles(stations);

            for (Aisle aisle : aisles)
            {
                // AGCがオンラインかどうか
                if (!isOnLine(aisle.getValue(GroupController.STATUS_FLAG)))
                {
                    continue;
                }

                // アイルのチェック
                // 異常な設備の場合
                if (isAisleStatusError(aisle.getStatus()))
                {
                    return true;
                }
            }


            // ステーションのチェック
            for (Station station : stations)
            {
                if (isStationStatusError(station.getStatus()) || isStationSuspendOn(station.getSuspend())
                        || !isRetrievalModeEnable(station))
                {
                    return true;
                }
            }


        }

        return false;
    }

    /**
     * 指定されたステータスがオンラインかどうかを判定します。
     *
     * @param status 判定するステータス
     * @return オンラインの場合ture、それ以外false
     */
    protected boolean isOnLine(Object status)
    {
        if (GroupController.GC_STATUS_ONLINE.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 指定されたステータスがオフラインかどうかを判定します。
     *
     * @param status 判定するステータス
     * @return オフラインの場合ture、それ以外false
     */
    protected boolean isOffLine(Object status)
    {
        if (GroupController.GC_STATUS_OFFLINE.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * ステーションに紐つくアイルを取得します。<BR>
     *
     * @param stations ステーション
     * @return アイル
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     * @throws InvalidDefineException エリアに紐つくアイルが存在しない場合にスローされます。
     */
    public Aisle[] getAisles(Station[] stations)
            throws ReadWriteException,
                InvalidDefineException
    {
        AisleSearchKey aisleKey = new AisleSearchKey();
        List<String> aisleList = new ArrayList<String>();
        boolean aisleSet = true;

        for (int i = 0; i < stations.length; i++)
        {
            if (StringUtil.isBlank(stations[i].getAisleStationNo()))
            {
                aisleSet = false;
                break;
            }
            if (!aisleList.contains(stations[i].getAisleStationNo()))
            {
                aisleList.add(stations[i].getAisleStationNo());
            }
        }

        // set where
        if (aisleSet)
        {
            // アイルNo.から検索する
            String[] aisles = new String[aisleList.size()];
            aisleList.toArray(aisles);
            aisleKey.setStationNo(aisles, true);
        }
        else
        {
            // 倉庫No.から検索する
            aisleKey.setWhStationNo(stations[0].getWhStationNo());
        }

        // set join
        aisleKey.setJoin(Aisle.CONTROLLER_NO, GroupController.CONTROLLER_NO);

        // set collect
        aisleKey.setCollect(new FieldName(Aisle.STORE_NAME, FieldName.ALL_FIELDS));
        aisleKey.setCollect(GroupController.CONTROLLER_NO);
        aisleKey.setCollect(GroupController.STATUS_FLAG);

        Aisle[] aisles = (Aisle[])new AisleHandler(getConnection()).find(aisleKey);

        if (ArrayUtil.isEmpty(aisles))
        {
            throw new InvalidDefineException("no aisle info of station");
        }

        return aisles;
    }

    /**
     * 指定されたアイルの状態が正常かどうかを判定します。
     *
     * @param status 判定するアイルの状態
     * @return 正常な場合ture、それ以外false
     */
    protected boolean isAisleStatusNormal(Object status)
    {
        if (Aisle.AISLE_STATUS_NORMAL.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 指定されたアイルの状態が異常、または切り離しかどうかを判定します。
     *
     * @param status 判定するアイルの状態
     * @return 異常な場合ture、それ以外false
     */
    protected boolean isAisleStatusError(Object status)
    {
        if (Aisle.AISLE_STATUS_ERROR.equals(status) || Aisle.AISLE_STATUS_DISCONNECTED.equals(status))
        {
            return true;
        }
        return false;
    }

    /**
     * 在庫に対する引当数を取得します。
     *
     * @param remainingQty 残引当数
     * @param stk 引当対象の在庫
     * @return 引当数
     */
    protected int getAssigningQty(Stock stk, int remainingQty)
    {
        // この在庫から引当てる数量を求める
        int assigningQty = remainingQty;
        if (assigningQty > stk.getAllocationQty())
        {
            assigningQty = stk.getAllocationQty();
        }
        return assigningQty;
    }

    /**
     * 今回の引当要求数を取得します。
     * 出庫予定情報の引当予定数 - 出庫予定情報に紐づく作業情報(削除以外)の予定数
     *
     * @param connection DBコネクション
     * @param plan 出庫予定情報
     * @return 今回引当数
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    public static int getAllocationQty(Connection connection, RetrievalPlan plan)
            throws ReadWriteException
    {
        WorkInfoSearchKey wiKey = new WorkInfoSearchKey();
        wiKey.setStatusFlag(WorkInfo.STATUS_FLAG_DELETE, "!=");
        wiKey.setPlanUkey(plan.getPlanUkey());
        wiKey.setPlanQtyCollect("SUM");

        WorkInfo workInfo = ((WorkInfo[])new WorkInfoHandler(connection).find(wiKey))[0];

        return plan.getPlanQty() - workInfo.getPlanQty();
    }

    /**
     * 搬送情報とパレット情報の不整合の補正
     * 引き当て処理中に入庫完了処理が完了すると搬送情報とパレット情報に
     * 不整合が発生する場合があり、状態を正常に補正します。
     *
     */
    public void updatePalletState()
    {
        PalletHandler pland = new PalletHandler(getConnection());
        PalletSearchKey plSkey = new PalletSearchKey();
        String[] status = {
                SystemDefine.CMD_STATUS_ALLOCATION,
                SystemDefine.CMD_STATUS_START
        };
        plSkey.setKey(CarryInfo.CMD_STATUS, status, true);
        plSkey.setJoin(CarryInfo.PALLET_ID, Pallet.PALLET_ID);
        String[] pStatus = {
                SystemDefine.PALLET_STATUS_REGULAR,
                SystemDefine.PALLET_STATUS_STORAGE_PLAN
        };
        plSkey.setKey(Pallet.STATUS_FLAG, pStatus ,true);
        plSkey.setKey(CarryInfo.CARRY_FLAG, CarryInfo.CARRY_FLAG_RETRIEVAL);
        plSkey.setKey(CarryInfo.SCHEDULE_NO, _scheduleNo);
        try
        {
            Pallet[] pl = (Pallet[])pland.findForUpdate(plSkey);
            for (Pallet pallet : pl)
            {
                plSkey.clear();
                plSkey.setPalletId(pallet.getPalletId());
                plSkey.setStatusFlag(SystemDefine.PALLET_STATUS_REGULAR);
                if (pland.count(plSkey) == 0)
                {
                    continue;
                }

                PalletAlterKey plKey = new PalletAlterKey();
                plKey.setPalletId(pallet.getPalletId());
                plKey.updateStatusFlag(SystemDefine.PALLET_STATUS_RETRIEVAL_PLAN);
                plKey.updateAllocationFlag(SystemDefine.ALLOCATION_FLAG_ALLOCATED);
                try
                {
                    pland.modify(plKey);
                }
                catch (NotFoundException e)
                {
                }
            }
        }
        catch (ReadWriteException e)
        {
        }
        catch (LockTimeOutException e)
        {
        }
    }

    /**
     * このクラスのリビジョンを返します。
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AbstractAllocateOperator.java 8053 2013-05-15 01:00:52Z kishimoto $";
    }
}
