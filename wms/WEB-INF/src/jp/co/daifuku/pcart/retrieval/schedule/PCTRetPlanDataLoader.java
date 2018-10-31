// $Id: PCTRetPlanDataLoader.java 7912 2010-05-10 05:43:31Z kumano $
package jp.co.daifuku.pcart.retrieval.schedule;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.SQLException;
import java.util.Locale;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.common.ArrayUtil;
import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.base.controller.PCTItemController;
import jp.co.daifuku.pcart.base.controller.ZoneController;
import jp.co.daifuku.pcart.base.util.PCTDisplayUtil;
import jp.co.daifuku.pcart.base.util.PCTSystemChecker;
import jp.co.daifuku.pcart.retrieval.operator.PCTRetPlanOperator;
import jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AreaHandler;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ZoneHandler;
import jp.co.daifuku.wms.base.dbhandler.ZoneSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.Zone;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.fileentity.PCTRetrieval;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * PCT出庫予定データの取り込み処理を行います。<br>
 *
 * @version $Revision: 7912 $, $Date: 2010-05-10 14:43:31 +0900 (月, 10 5 2010) $
 * @author  H.Okayama
 * @author  Last commit: $Author: kumano $
 */
public class PCTRetPlanDataLoader
        extends AbstractDataLoaderForJava
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    /** 出荷先分類未指定時補完コード */
    private final String DEFAULT_CUSTOMER_CATEGORY = "000000";

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    /** PCT出庫予定情報ハンドラ */
    private PCTRetPlanHandler _pctRetHandler = null;

    /** PCT出庫予定情報検索キー */
    private PCTRetPlanSearchKey _pctRetKey = null;

    /** PCT出庫予定情報更新キー */
    private PCTRetPlanAlterKey _pctRetAKey = null;

    /** 荷主マスタハンドラ */
    private ConsignorHandler _consigHandler = null;

    /** 荷主マスタ検索キー */
    private ConsignorSearchKey _consigKey = null;

    /** PCT商品マスタハンドラ */
    private PCTItemHandler _pctItemHandler = null;

    /** PCT商品マスタ検索キー */
    private PCTItemSearchKey _pctItemKey = null;

    /** エリアマスタハンドラ */
    private AreaHandler _areaHandler = null;

    /** エリアマスタ検索キー */
    private AreaSearchKey _areaKey = null;

    /** ゾーンマスタハンドラ */
    private ZoneHandler _zoneHandler = null;

    /** ゾーンマスタ検索キー */
    private ZoneSearchKey _zoneKey = null;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 取り込み区分出庫の受信データの取り込みを行います。<BR>
     * 自動取込みから使用します。
     */
    public PCTRetPlanDataLoader()
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_PICKINGRET);
    }

    /**
     * 取り込み区分出庫の受信データの取り込みを行います。<BR>
     * WEB画面から使用します。
     *
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    public PCTRetPlanDataLoader(DfkUserInfo userinfo, Locale locale)
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_PICKINGRET, userinfo, locale);
    }

    /**
     * 取り込み区分出庫の受信データの取り込みを行います。<BR>
     * WEB画面から使用します。
     *
     * @param exchangeJob 取り込みを行う作業種別
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    public PCTRetPlanDataLoader(String exchangeJob, DfkUserInfo userinfo, Locale locale)
    {
        super(exchangeJob, userinfo, locale);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------

    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------
    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#init()
     */
    @Override
    protected void init()
            throws ReadWriteException,
                ScheduleException
    {
        // PCT出庫予定
        _pctRetHandler = new PCTRetPlanHandler(getConnection());
        _pctRetKey = new PCTRetPlanSearchKey();
        _pctRetAKey = new PCTRetPlanAlterKey();

        // 荷主マスタ
        _consigHandler = new ConsignorHandler(getConnection());
        _consigKey = new ConsignorSearchKey();

        // PCT商品マスタ
        _pctItemHandler = new PCTItemHandler(getConnection());
        _pctItemKey = new PCTItemSearchKey();

        // エリアマスタ
        _areaHandler = new AreaHandler(getConnection());
        _areaKey = new AreaSearchKey();

        // ゾーンマスタ
        _zoneHandler = new ZoneHandler(getConnection());
        _zoneKey = new ZoneSearchKey();
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#check(jp.co.daifuku.wms.handler.Entity)
     */
    @Override
    protected RESULT check(Entity ent)
            throws CommonException
    {
        // システム定義コントローラ
        WarenaviSystemController sysCtrl = new WarenaviSystemController(getConnection(), this.getClass());
        PCTRetrieval ret = (PCTRetrieval)ent;

        // 日付のフォーマットチェック
        // 予定日
        if (!StringUtil.isBlank(ret.getPlanDay()))
        {
            RESULT chk = isDay(PCTRetrieval.PLAN_DAY, ret.getPlanDay());
            if (!chk.equals(RESULT.LOAD))
            {
                return chk;
            }
        }
        // 基準日付
        if (!StringUtil.isBlank(ret.getUseByDate()))
        {
            RESULT chk2 = isDay(PCTRetrieval.USE_BY_DATE, ret.getUseByDate());
            if (!chk2.equals(RESULT.LOAD))
            {
                return chk2;
            }
        }
        // 禁止文字を含むかチェック
        RESULT chk3 = hasNGParameterText(ret);
        if (!chk3.equals(RESULT.LOAD))
        {
            return chk3;
        }

        // 取込情報のチェック、及び補完
        checkParam(ret, sysCtrl);

        // 取消区分が登録の場合
        if (ret.getCancelFlag().equals(SystemDefine.CANCEL_FLAG_NORMAL))
        {
            PCTSystemHandler pSHandler = new PCTSystemHandler(getConnection());
            PCTSystemSearchKey sKey = new PCTSystemSearchKey();
            PCTSystem system = (PCTSystem)pSHandler.findPrimary(sKey);

            // オーダーNo.が指定されていない場合
            if (StringUtil.isBlank(ret.getPlanOrderNo()))
            {
                // チェックディジットありの場合
                if (PCTRetrievalInParameter.CHECK_DIGIT_ON.equals(system.getCheckDigitFlag()))
                {
                    try
                    {
                        // 出荷先コードが数値に変換できるか
                        Integer.parseInt(ret.getCustomerCode());
                    }
                    catch (NumberFormatException e)
                    {
                        // MSG-W0029=データの値が不正
                        insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                                PCTRetrieval.CUSTOMER_CODE.getName());
                        return RESULT.ROLLBACK;
                    }
                }
            }
            // オーダNo.を指定している場合
            else
            {
                // SEQNo.桁数 + チェックディジット長 < オーダーNo.桁数であること
                int seqLen = system.getSeqDigit();
                int orderLen = ret.getPlanOrderNo().length();
                int checkDLen = 0;
                if (PCTRetrievalInParameter.CHECK_DIGIT_ON.equals(system.getCheckDigitFlag()))
                {
                    checkDLen = 1;
                }
                if (seqLen + checkDLen >= orderLen)
                {
                    // MSG-W0029=データの値が不正
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                            PCTRetrieval.PLAN_ORDER_NO.getName());
                    return RESULT.ROLLBACK;
                }

                // オーダーNo.内Seqチェック
                String seqCheck = "";
                String seqNo =
                        ret.getPlanOrderNo().substring((orderLen - (seqLen + checkDLen)), (orderLen - checkDLen));
                for (int i = 0; i < seqNo.length(); i++)
                {
                    seqCheck = seqCheck + "0";
                }
                if (!seqCheck.equals(seqNo))
                {
                    // MSG-W0029=データの値が不正
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                            PCTRetrieval.PLAN_ORDER_NO.getName());
                    return RESULT.ROLLBACK;
                }
            }

            // 通番がオーダー内商品数より大きい場合
            if (ret.getThroughNo() > ret.getOrderItemQty())
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029", PCTRetrieval.THROUGH_NO.getName());
                return RESULT.ROLLBACK;
            }

            // オーダー通番がオーダー通番合計より大きい場合
            if (ret.getOrderThroughNo() > ret.getOrderThroughNoCnt())
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                        PCTRetrieval.ORDER_THROUGH_NO.getName());
                return RESULT.ROLLBACK;
            }

            // エリアが指定されていない場合
            boolean errFlg = false;
            String rArea = "";
            if (StringUtil.isBlank(ret.getPlanAreaNo()))
            {
                _zoneKey.clear();

                // 作業ゾーンが指定されている場合
                if (!StringUtil.isBlank(ret.getWorkZoneNo()))
                {
                    rArea = ret.getWorkZoneNo();

                    // 予定ゾーンが指定されている場合
                    if (!StringUtil.isBlank(ret.getPlanZoneNo()))
                    {
                        // ゾーンNo
                        _zoneKey.setZoneNo(ret.getPlanZoneNo());
                    }
                    else
                    {
                        // ゾーンNoを作業ゾーンNoにて検索
                        _zoneKey.setZoneNo(rArea);
                    }
                }
                // 予定ゾーンが指定されている場合
                else if (!StringUtil.isBlank(ret.getPlanZoneNo()))
                {
                    // 作業ゾーンNoを予定ゾーンNoにて検索
                    _zoneKey.setWorkZoneNo(ret.getPlanZoneNo());
                    // ゾーンNo
                    _zoneKey.setZoneNo(ret.getPlanZoneNo());
                    rArea = ret.getPlanZoneNo();
                }
                // 予定棚が指定されている場合
                else if (!StringUtil.isBlank(ret.getPlanLocationNo()))
                {
                    errFlg = true;
                    // 棚No.の範囲
                    _zoneKey.setStartLocationNo(ret.getPlanLocationNo(), "<=");
                    _zoneKey.setEndLocationNo(ret.getPlanLocationNo(), ">=");
                }
                else
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            PCTRetrieval.PLAN_AREA_NO.getName());
                    return RESULT.ROLLBACK;
                }

                // 検索
                Zone[] zone = (Zone[])_zoneHandler.find(_zoneKey);
                // 対象ゾーンが存在する場合
                if (!ArrayUtil.isEmpty(zone))
                {
                    ret.setPlanAreaNo(zone[0].getAreaNo());
                }
                // 対象ゾーン存在しない場合
                else
                {
                    // 予定棚が指定されている場合
                    if (errFlg)
                    {
                        // MSG-W0023=マスタ未登録
                        insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                                PCTRetrieval.PLAN_AREA_NO.getName());
                        return RESULT.ROLLBACK;
                    }
                    else
                    {
                        ret.setPlanAreaNo(rArea);
                    }
                }
            }
            else
            {
                // エリアがマスタ登録されていない場合はスキップとする
                _areaKey.clear();
                _areaKey.setAreaNo(ret.getPlanAreaNo());
                if (_areaHandler.count(_areaKey) == 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            PCTRetrieval.PLAN_AREA_NO.getName());
                    return RESULT.ROLLBACK;
                }

                // ゾーンチェック
                _zoneKey.clear();
                _zoneKey.setAreaNo(ret.getPlanAreaNo());

                // 作業ゾーンが指定されていた場合
                if (!StringUtil.isBlank(ret.getWorkZoneNo()))
                {
                    rArea = ret.getWorkZoneNo();
                    _zoneKey.setWorkZoneNo(rArea);

                    // 予定ゾーンが指定されていた場合
                    if (!StringUtil.isBlank(ret.getPlanZoneNo()))
                    {
                        // ゾーンNo
                        _zoneKey.setZoneNo(ret.getPlanZoneNo());
                    }
                    else
                    {
                        // ゾーンNoを作業ゾーンNoにて検索
                        _zoneKey.setZoneNo(ret.getWorkZoneNo());
                    }

                    // 予定棚が指定されていた場合
                    if (!StringUtil.isBlank(ret.getPlanLocationNo()))
                    {
                        // 棚No.の範囲
                        // 開始棚No以上
                        _zoneKey.setStartLocationNo(ret.getPlanLocationNo(), "<=", "((", "", true);
                        // 終了棚No以下
                        _zoneKey.setEndLocationNo(ret.getPlanLocationNo(), ">=", "", ")", false);
                        // 開始棚NoがNULL
                        _zoneKey.setStartLocationNo("", "=", "(", "", true);
                        // 終了棚NoがNULL
                        _zoneKey.setEndLocationNo("", "=", "", "))", true);
                    }
                }
                // 予定ゾーンが指定されていた場合
                else if (!StringUtil.isBlank(ret.getPlanZoneNo()))
                {
                    rArea = ret.getPlanZoneNo();
                    // 作業ゾーンNoを予定ゾーンNoにて検索
                    _zoneKey.setWorkZoneNo(rArea);
                    // ゾーンNo
                    _zoneKey.setZoneNo(rArea);

                    // 予定棚が指定されていた場合
                    if (!StringUtil.isBlank(ret.getPlanLocationNo()))
                    {
                        // 棚No.の範囲
                        // 開始棚No以上
                        _zoneKey.setStartLocationNo(ret.getPlanLocationNo(), "<=", "((", "", true);
                        // 終了棚No以下
                        _zoneKey.setEndLocationNo(ret.getPlanLocationNo(), ">=", "", ")", false);
                        // 開始棚NoがNULL
                        _zoneKey.setStartLocationNo("", "=", "(", "", true);
                        // 終了棚NoがNULL
                        _zoneKey.setEndLocationNo("", "=", "", "))", true);
                    }
                }
                // 予定棚が指定されていた場合
                else if (!StringUtil.isBlank(ret.getPlanLocationNo()))
                {
                    _zoneKey.setStartLocationNo(ret.getPlanLocationNo(), "<=");
                    _zoneKey.setEndLocationNo(ret.getPlanLocationNo(), ">=");
                }
                else
                {
                    // MSG-W0021=必須項目空白
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0021",
                            PCTRetrieval.PLAN_LOCATION_NO.getName());
                    return RESULT.ROLLBACK;
                }

                // 検索
                Zone[] zone = (Zone[])_zoneHandler.find(_zoneKey);
                if (zone.length <= 0)
                {
                    // MSG-W0023=マスタ未登録
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023",
                            PCTRetrieval.PLAN_ZONE_NO.getName());
                    return RESULT.ROLLBACK;
                }
            }

            // 指定エリアが登録されていない場合
            AreaController aCtrl = new AreaController(getConnection(), getClass());
            String locStyle = aCtrl.getLocationStyle(ret.getPlanAreaNo());
            if (StringUtil.isBlank(locStyle))
            {
                String defLocStyle = WmsParam.DEFAULT_LOCATE_STYLE;
                int defStyle = WmsFormatter.toParamLocation(defLocStyle, defLocStyle).length();
                try
                {
                    if (defStyle != ret.getPlanLocationNo().length())
                    {
                        // MSG-W0029=データの値が不正
                        insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                                PCTRetrieval.PLAN_LOCATION_NO.getName());
                        return RESULT.ROLLBACK;
                    }
                }
                catch (ScheduleException e)
                {
                    // MSG-W0029=データの値が不正
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                            PCTRetrieval.PLAN_LOCATION_NO.getName());
                    return RESULT.ROLLBACK;
                }
            }
            // 指定エリアが登録されている場合
            else
            {
                try
                {
                    // 棚フォーマットを行う
                    AreaController areaCtrl = new AreaController(getConnection(), this.getClass());
                    areaCtrl.checkLocateFormat(ret.getPlanAreaNo(), ret.getPlanLocationNo());
                }
                // 棚変換に失敗した場合
                catch (OperatorException e)
                {
                    // MSG-W0029=データの値が不正
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                            PCTRetrieval.PLAN_LOCATION_NO.getName());
                    return RESULT.ROLLBACK;
                }
                // 棚変換に失敗した場合
                catch (ScheduleException e)
                {
                    // MSG-W0029=データの値が不正
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                            PCTRetrieval.PLAN_LOCATION_NO.getName());
                    return RESULT.ROLLBACK;
                }
            }

            // PCTマスタパッケージが導入されている場合
            if (sysCtrl.hasPCTMasterPack())
            {
                _pctItemKey.clear();
                _pctItemKey.setConsignorCode(ret.getConsignorCode());
                _pctItemKey.setItemCode(ret.getItemCode());
                _pctItemKey.setLotEnteringQty(ret.getLotEnteringQty());
                PCTItem[] items = (PCTItem[])_pctItemHandler.find(_pctItemKey);
                if (!ArrayUtil.isEmpty(items))
                {
                    // ロット入数の許容量チェック
                    if (!PCTSystemChecker.checkLotEntering(items[0].getSingleWeight(), ret.getLotEnteringQty(),
                            items[0].getWeightDistinctRate()))
                    {
                        // MSG-W0029=データの値が不正
                        insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029",
                                PCTRetrieval.LOT_ENTERING_QTY.getName());
                    }
                }
            }

            // 数量チェック
            if (ret.getPlanQty() <= 0)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029", PCTRetrieval.PLAN_QTY.getName());
                return RESULT.ROLLBACK;
            }
            else if (ret.getPlanQty() > WmsParam.MAX_TOTAL_QTY)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029", PCTRetrieval.PLAN_QTY.getName());
                return RESULT.ROLLBACK;
            }

            // バッチSeqNoにてスケジュール中情報が存在した場合スキップする
            if (!StringUtil.isBlank(ret.getBatchSeqNo()))
            {
                // 検索条件セット
                _pctRetKey.clear();
                // バッチSeqNo.
                _pctRetKey.setBatchSeqNo(ret.getBatchSeqNo());
                // 状態フラグ（削除以外）
                _pctRetKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                // スケジュールフラグ（スケジュール中）
                _pctRetKey.setSchFlag(SystemDefine.SCH_FLAG_SCHEDULE);
                if (_pctRetHandler.count(_pctRetKey) > 0)
                {
                    // MSG-P0026=バッチSeqNo.内にスケジュール中情報存在
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0026", PCTRetrieval.BATCH_SEQ_NO.getName());
                    return RESULT.ROLLBACK;
                }
            }

            // オーダーNo指定有りの場合
            if (!StringUtil.isBlank(ret.getPlanOrderNo()))
            {
                // 同一オーダー内に異なる予定日のPCT出庫情報がある場合はスキップ
                // PCT出庫予定検索条件セット
                _pctRetKey.clear();
                _pctRetKey.setPlanOrderNo(ret.getPlanOrderNo());
                _pctRetKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                // 今回の出庫予定日以外
                _pctRetKey.setPlanDay(ret.getPlanDay(), "!=");

                // 出庫予定日で集約
                _pctRetKey.setPlanDayGroup();
                _pctRetKey.setPlanDayCollect();
                if (_pctRetHandler.count(_pctRetKey) > 0)
                {
                    // MSG-W0078=同一オーダー内に複数予定日
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0078", PCTRetrieval.PLAN_DAY.getName());
                    return RESULT.ROLLBACK;
                }

                // 同一オーダー内に異なるエリアのPCT出庫情報がある場合はスキップ
                // PCT出庫予定検索条件セット
                _pctRetKey.clear();
                _pctRetKey.setPlanOrderNo(ret.getPlanOrderNo());
                _pctRetKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                // 今回のエリアNo.以外
                _pctRetKey.setPlanAreaNo(ret.getPlanAreaNo(), "!=");

                // エリアNo.で集約
                _pctRetKey.setPlanAreaNoGroup();
                _pctRetKey.setPlanAreaNoCollect();
                if (_pctRetHandler.count(_pctRetKey) > 0)
                {
                    // MSG-P0014=オーダー内に複数エリア
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0014",
                            PCTRetrieval.PLAN_AREA_NO.getName());
                    return RESULT.ROLLBACK;
                }

                // 同一オーダー内に異なる得意先のPCT出庫情報がある場合はスキップ
                // PCT出庫予定検索条件セット
                _pctRetKey.clear();
                _pctRetKey.setPlanOrderNo(ret.getPlanOrderNo());
                _pctRetKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                // 今回の得意先コード以外
                _pctRetKey.setRegularCustomerCode(ret.getRegularCustomerCode(), "!=");

                // 得意先コードで集約
                _pctRetKey.setRegularCustomerCodeGroup();
                _pctRetKey.setRegularCustomerCodeCollect();
                if (_pctRetHandler.count(_pctRetKey) > 0)
                {
                    // MSG-P0015=オーダー内に複数得意先
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0015",
                            PCTRetrieval.REGULAR_CUSTOMER_CODE.getName());
                    return RESULT.ROLLBACK;
                }

                // 同一オーダー内に異なる出荷先のPCT出庫情報がある場合はスキップ
                // PCT出庫予定検索条件セット
                _pctRetKey.clear();
                _pctRetKey.setPlanOrderNo(ret.getPlanOrderNo());
                _pctRetKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                // 今回の出荷先コード以外
                _pctRetKey.setCustomerCode(ret.getCustomerCode(), "!=");

                // 出荷先コードで集約
                _pctRetKey.setCustomerCodeGroup();
                _pctRetKey.setCustomerCodeCollect();
                if (_pctRetHandler.count(_pctRetKey) > 0)
                {
                    // MSG-W0079=同一オーダー内に複数の出荷先
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0079",
                            PCTRetrieval.CUSTOMER_CODE.getName());
                    return RESULT.ROLLBACK;
                }

                // 同一オーダー内に異なる出荷先分類のPCT出庫情報がある場合はスキップ
                // PCT出庫予定検索条件セット
                _pctRetKey.clear();
                _pctRetKey.setPlanOrderNo(ret.getPlanOrderNo());
                _pctRetKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                // 今回の出荷先分類以外
                _pctRetKey.setCustomerCategory(ret.getCustomerCategory(), "!=");

                // 出荷先分類で集約
                _pctRetKey.setCustomerCategoryGroup();
                _pctRetKey.setCustomerCategoryCollect();
                if (_pctRetHandler.count(_pctRetKey) > 0)
                {
                    // MSG-P0016=オーダー内に複数出荷先分類
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0016",
                            PCTRetrieval.CUSTOMER_CATEGORY.getName());
                    return RESULT.ROLLBACK;
                }

                // 同一オーダー内に異なるバッチSeqNo.のPCT出庫情報がある場合はスキップ
                // PCT出庫予定検索条件セット
                _pctRetKey.clear();
                _pctRetKey.setPlanOrderNo(ret.getPlanOrderNo());
                _pctRetKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                // 今回のバッチSeqNo.以外
                _pctRetKey.setBatchSeqNo(ret.getBatchSeqNo(), "!=");

                // バッチSeqNo.で集約
                _pctRetKey.setBatchSeqNoGroup();
                _pctRetKey.setBatchSeqNoCollect();
                if (_pctRetHandler.count(_pctRetKey) > 0)
                {
                    // MSG-P0017=オーダー内に複数バッチSeqNo.
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0017",
                            PCTRetrieval.BATCH_SEQ_NO.getName());
                    return RESULT.ROLLBACK;
                }

                // オーダー内商品数と通番の整合性チェック
                if (!checkOrderItemQty(ret))
                {
                    return RESULT.ROLLBACK;
                }

                // オーダー内商品数と通番の整合性チェック
                if (!checkOrderThroughNoCnt(ret))
                {
                    return RESULT.ROLLBACK;
                }
            }

            // バッチSeqNo.が指定されている場合
            if (!StringUtil.isBlank(ret.getBatchSeqNo()))
            {
                // 同一バッチSeqNo.内に異なるバッチNo.のPCT出庫予定情報が存在する場合はスキップ
                // PCT出庫予定検索条件セット
                _pctRetKey.clear();
                _pctRetKey.setBatchSeqNo(ret.getBatchSeqNo());
                _pctRetKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                // 今回のバッチNo.以外
                _pctRetKey.setBatchNo(ret.getBatchNo(), "!=");

                // バッチNo.で集約
                _pctRetKey.setBatchNoGroup();
                _pctRetKey.setBatchNoCollect();
                if (_pctRetHandler.count(_pctRetKey) > 0)
                {
                    // MSG-P0022=バッチSeqNo.内に複数バッチNo.
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0022", PCTRetrieval.BATCH_NO.getName());
                    return RESULT.ROLLBACK;
                }

                // 同一バッチSeqNo.内に異なるバッチNo.のPCT出庫予定情報が存在する場合はスキップ
                // PCT出庫予定検索条件セット
                _pctRetKey.clear();
                _pctRetKey.setBatchSeqNo(ret.getBatchSeqNo());
                _pctRetKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
                // 今回の予定日以外
                _pctRetKey.setPlanDay(ret.getPlanDay(), "!=");

                // 予定日で集約
                _pctRetKey.setPlanDayGroup();
                _pctRetKey.setPlanDayCollect();
                if (_pctRetHandler.count(_pctRetKey) > 0)
                {
                    // MSG-P0023=バッチSeqNo.内に複数予定日
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0023", PCTRetrieval.PLAN_DAY.getName());
                    return RESULT.ROLLBACK;
                }
            }

            // 重複チェック
            if (!checkRepeatData(ret))
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025", "");
                return RESULT.ROLLBACK;
            }
            return RESULT.LOAD;
        }
        // 取消区分が削除の場合
        else if (ret.getCancelFlag().equals(SystemDefine.CANCEL_FLAG_HOST_CANCEL))
        {
            // PCT出庫予定情報チェック
            RESULT res = checkCancelPlan(ret);
            if (!RESULT.LOAD.equals(res))
            {
                return res;
            }

            return RESULT.LOAD;
        }
        // 区分誤りの場合はスキップ(ここは通らないはず)
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", PCTRetrieval.CANCEL_FLAG.getName());
            return RESULT.ROLLBACK;
        }
    }

    /**
     * オーダー内商品数と通番の整合性チェックを行います。<BR>
     * 整合性に誤りがあった場合falseを返します。<BR>
     *
     * @param ret 出庫予定Entity
     * @param handler FileHandlerクラス
     * @return 実行結果（正常：true 異常：false
     * @throws CommonException
     */
    protected boolean checkOrderItemQty(PCTRetrieval ret)
            throws CommonException
    {
        // オーダー内商品数に問題がある場合はスキップ
        _pctRetKey.clear();
        _pctRetKey.setLoadUnitKey(getSourceFileNameOutOfExtention());
        _pctRetKey.setPlanDay(ret.getPlanDay());
        _pctRetKey.setConsignorCode(ret.getConsignorCode());
        _pctRetKey.setRegularCustomerCode(ret.getRegularCustomerCode());
        _pctRetKey.setCustomerCode(ret.getCustomerCode());
        _pctRetKey.setCustomerCategory(ret.getCustomerCategory());
        _pctRetKey.setBatchSeqNo(ret.getBatchSeqNo());
        _pctRetKey.setPlanOrderNo(ret.getPlanOrderNo());
        _pctRetKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        _pctRetKey.setOrderItemQtyCollect("MAX");
        _pctRetKey.setPlanUkeyCollect("COUNT");
        PCTRetPlan plan = (PCTRetPlan)_pctRetHandler.findPrimary(_pctRetKey);
        int cnt = plan.getBigDecimal(PCTRetPlan.PLAN_UKEY).intValue();
        if (cnt == 0)
        {
            // オーダー内商品数、通番が1以上の場合
            if (ret.getThroughNo() >= 1 && ret.getOrderItemQty() >= 1)
            {
                // 通番が1ではない場合
                if (ret.getThroughNo() != 1)
                {
                    // MSG-P0018=オーダー内に不正通番
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0018",
                            PCTRetrieval.THROUGH_NO.getName());
                    return false;
                }
            }
        }
        else
        {
            // オーダー内商品数が同一であること
            if (ret.getOrderItemQty() != plan.getOrderItemQty())
            {
                // MSG-P0019=オーダー内に複数オーダー内商品数
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0019",
                        PCTRetrieval.ORDER_ITEM_QTY.getName());
                return false;
            }

            // オーダー内商品数、通番が1以上の場合
            if (ret.getThroughNo() >= 1 && ret.getOrderItemQty() >= 1)
            {
                // オーダー内件数+1が今回取り込む通番であること
                if (ret.getThroughNo() != cnt + 1)
                {
                    // MSG-P0018=オーダー内に不正通番
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0018",
                            PCTRetrieval.THROUGH_NO.getName());
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * オーダー内商品数と通番の整合性チェックを行います。<BR>
     * 整合性に誤りがあった場合falseを返します。<BR>
     *
     * @param ret 出庫予定Entity
     * @param handler FileHandlerクラス
     * @return 実行結果（正常：true 異常：false
     * @throws CommonException
     */
    protected boolean checkOrderThroughNoCnt(PCTRetrieval ret)
            throws CommonException
    {
        // 通番に問題がある場合はスキップ
        _pctRetKey.clear();
        _pctRetKey.setLoadUnitKey(getSourceFileNameOutOfExtention());
        _pctRetKey.setPlanDay(ret.getPlanDay());
        _pctRetKey.setConsignorCode(ret.getConsignorCode());
        _pctRetKey.setRegularCustomerCode(ret.getRegularCustomerCode());
        _pctRetKey.setCustomerCode(ret.getCustomerCode());
        _pctRetKey.setCustomerCategory(ret.getCustomerCategory());
        _pctRetKey.setBatchSeqNo(ret.getBatchSeqNo());
        _pctRetKey.setPlanOrderNo(ret.getPlanOrderNo(), "!=");
        _pctRetKey.setOrderThroughNoCntCollect("MAX");
        _pctRetKey.setOrderThroughNoCollect("COUNT(UNIQUE{0})");
        PCTRetPlan plan = (PCTRetPlan)_pctRetHandler.findPrimary(_pctRetKey);

        int count = plan.getBigDecimal(PCTRetPlan.ORDER_THROUGH_NO).intValue();
        if (count == 0)
        {
            // オーダー通番、オーダー通番合計が1以上の場合
            if (ret.getOrderThroughNo() >= 1 && ret.getOrderThroughNoCnt() >= 1)
            {
                // オーダー通番が1ではない場合
                if (ret.getOrderThroughNo() != 1)
                {
                    // MSG-P0020=バッチSeqNo.内に不正オーダー通番
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0020",
                            PCTRetrieval.ORDER_THROUGH_NO.getName());
                    return false;
                }
            }
        }
        else
        {
            // オーダー通番、オーダー通番合計が1以上の場合
            if (ret.getOrderThroughNoCnt() != plan.getOrderThroughNoCnt())
            {
                // MSG-P0021=バッチSeqNo.内に複数オーダー通番合計
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0021",
                        PCTRetrieval.ORDER_THROUGH_NO_CNT.getName());
                return false;
            }

            // オーダー通番、オーダー通番合計が1以上の場合
            if (ret.getOrderThroughNo() >= 1 && ret.getOrderThroughNoCnt() >= 1)
            {
                // オーダー内件数+1が今回取り込む通番であること
                if (ret.getOrderThroughNo() != count + 1)
                {
                    // MSG-P0020=バッチSeqNo.内に不正オーダー通番
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0020",
                            PCTRetrieval.ORDER_THROUGH_NO.getName());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 
     * 
     * @param ret 出庫予定Entity
     * @return 実行結果（正常：true 異常：false
     * @throws CommonException
     */
    protected boolean checkRepeatData(PCTRetrieval ret)
            throws CommonException
    {
        // 重複チェック
        _pctRetKey.clear();
        _pctRetKey.setConsignorCode(ret.getConsignorCode());
        _pctRetKey.setPlanDay(ret.getPlanDay());
        // 得意先コードが指定されている場合
        if (!StringUtil.isBlank(ret.getRegularCustomerCode()))
        {
            _pctRetKey.setRegularCustomerCode(ret.getRegularCustomerCode());
        }
        _pctRetKey.setCustomerCode(ret.getCustomerCode());
        // 出荷先分類が指定されている場合
        if (!StringUtil.isBlank(ret.getCustomerCategory()))
        {
            _pctRetKey.setCustomerCategory(ret.getCustomerCategory());
        }
        // 出荷伝票No.が指定されている場合
        if (!StringUtil.isBlank(ret.getShipTicketNo()))
        {
            _pctRetKey.setShipTicketNo(ret.getShipTicketNo());
            _pctRetKey.setShipLineNo(ret.getShipLineNo());
            _pctRetKey.setBranchNo(ret.getBranchNo());
        }
        _pctRetKey.setBatchNo(ret.getBatchNo());
        // バッチSeqNo.が指定されている場合
        if (!StringUtil.isBlank(ret.getBatchSeqNo()))
        {
            _pctRetKey.setBatchSeqNo(ret.getBatchSeqNo());
        }
        // 予定オーダーNo.が指定されている場合
        if (!StringUtil.isBlank(ret.getPlanOrderNo()))
        {
            _pctRetKey.setPlanOrderNo(ret.getPlanOrderNo());
        }
        // エリアが指定されている場合
        if (!StringUtil.isBlank(ret.getPlanAreaNo()))
        {
            _pctRetKey.setPlanAreaNo(ret.getPlanAreaNo());
        }
        _pctRetKey.setPlanLocationNo(ret.getPlanLocationNo());
        _pctRetKey.setItemCode(ret.getItemCode());
        _pctRetKey.setLotEnteringQty(ret.getLotEnteringQty());
        _pctRetKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        if (_pctRetHandler.count(_pctRetKey) > 0)
        {
            return false;
        }
        return true;
    }

    /**
     * 取込情報のチェックを行い、必要があれば値の補完を行います。<BR>
     *
     * @param ret 取込情報
     * @param sysCtrl システム定義情報コントローラ
     * @throws CommonException
     */
    protected void checkParam(PCTRetrieval ret, WarenaviSystemController sysCtrl)
            throws CommonException
    {
        // 取込区分が指定されていない場合
        if (StringUtil.isBlank(ret.getCancelFlag()))
        {
            ret.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        }

        // 予定日が指定されていない場合
        if (StringUtil.isBlank(ret.getPlanDay()))
        {
            ret.setPlanDay(sysCtrl.getWorkDay());
        }

        // 荷主コードが指定されていない場合
        if (StringUtil.isBlank(ret.getConsignorCode()))
        {
            ret.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        }

        // 得意先コードが指定されていなく、かつ出荷先コードが指定されている場合
        if (!StringUtil.isBlank(ret.getCustomerCode()) || StringUtil.isBlank(ret.getRegularCustomerCode()))
        {
            ret.setRegularCustomerCode(ret.getCustomerCode());
        }

        // 出荷先分類が指定されていない場合
        if (StringUtil.isBlank(ret.getCustomerCategory()))
        {
            ret.setCustomerCategory(DEFAULT_CUSTOMER_CATEGORY);
        }

        // バッチSeqNo.が指定されていない場合
        if (StringUtil.isBlank(ret.getBatchSeqNo()))
        {
            ret.setBatchSeqNo(ret.getPlanDay() + ret.getBatchNo());
        }

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(ret.getCancelFlag()))
        {
            // 荷主名称が指定されていない場合
            if (StringUtil.isBlank(ret.getConsignorName()))
            {
                // 検索
                _consigKey.clear();
                _consigKey.setConsignorCode(ret.getConsignorCode());
                Consignor[] ents = (Consignor[])_consigHandler.find(_consigKey);
                if (!ArrayUtil.isEmpty(ents) && !StringUtil.isBlank(ents[0].getConsignorCode()))
                {
                    ret.setConsignorName(ents[0].getConsignorName());
                }
            }

            // PCTマスタパッケージが導入されている場合
            if (sysCtrl.hasPCTMasterPack())
            {
                // 商品名称、JANコードが指定されていない場合
                if (StringUtil.isBlank(ret.getItemName()) || StringUtil.isBlank(ret.getJan()))
                {
                    // 検索
                    _pctItemKey.clear();
                    _pctItemKey.setConsignorCode(ret.getConsignorCode());
                    _pctItemKey.setItemCode(ret.getItemCode());
                    _pctItemKey.setLotEnteringQty(ret.getLotEnteringQty());
                    PCTItem[] ents = (PCTItem[])_pctItemHandler.find(_pctItemKey);
                    if (!ArrayUtil.isEmpty(ents))
                    {
                        // 商品名称が指定されていなかった場合
                        if (StringUtil.isBlank(ret.getItemName()))
                        {
                            ret.setItemName(ents[0].getItemName());
                        }

                        // JANコードが指定されていなかった場合
                        if (StringUtil.isBlank(ret.getJan()))
                        {
                            ret.setJan(ents[0].getJan());
                        }
                    }
                }
            }
        }
    }

    /**
     * PCT出庫予定情報の存在、状態チェックを行う。<BR>
     * (取消時)
     *
     * @param ret 取込情報
     * @return 取込可否フラグ
     * @throws CommonException
     */
    protected RESULT checkCancelPlan(PCTRetrieval ret)
            throws CommonException
    {
        _pctRetKey.clear();
        _pctRetKey.setConsignorCode(ret.getConsignorCode());
        _pctRetKey.setPlanDay(ret.getPlanDay());

        // 得意先コードが指定されている場合
        if (!StringUtil.isBlank(ret.getRegularCustomerCode()))
        {
            _pctRetKey.setRegularCustomerCode(ret.getRegularCustomerCode());
        }
        _pctRetKey.setCustomerCode(ret.getCustomerCode());

        // 出荷先分類が指定されている場合
        if (!StringUtil.isBlank(ret.getCustomerCategory()))
        {
            _pctRetKey.setCustomerCategory(ret.getCustomerCategory());
        }

        // 出荷伝票No.が指定されている場合
        if (!StringUtil.isBlank(ret.getShipTicketNo()))
        {
            _pctRetKey.setShipTicketNo(ret.getShipTicketNo());
            _pctRetKey.setShipLineNo(ret.getShipLineNo());
            _pctRetKey.setBranchNo(ret.getBranchNo());
        }
        _pctRetKey.setBatchNo(ret.getBatchNo());

        // バッチSeqNo.が指定されている場合
        if (!StringUtil.isBlank(ret.getBatchSeqNo()))
        {
            _pctRetKey.setBatchSeqNo(ret.getBatchSeqNo());
        }

        // 予定オーダーNo.が指定されている場合
        if (!StringUtil.isBlank(ret.getPlanOrderNo()))
        {
            _pctRetKey.setPlanOrderNo(ret.getPlanOrderNo());
        }

        // エリアが指定されている場合
        if (!StringUtil.isBlank(ret.getPlanAreaNo()))
        {
            _pctRetKey.setPlanAreaNo(ret.getPlanAreaNo());
        }
        _pctRetKey.setPlanLocationNo(ret.getPlanLocationNo());
        _pctRetKey.setItemCode(ret.getItemCode());
        _pctRetKey.setLotEnteringQty(ret.getLotEnteringQty());
        _pctRetKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        try
        {
            PCTRetPlan[] plans = (PCTRetPlan[])_pctRetHandler.findForUpdate(_pctRetKey);
            if (plans == null || plans.length == 0)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027", "");
                return RESULT.SKIP;
            }
            else if (!PCTRetPlan.STATUS_FLAG_UNSTART.equals(plans[0].getStatusFlag()))
            {
                // MSG-W0028=取消データ作業開始済み
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0028", "");
                return RESULT.SKIP;
            }
        }
        catch (LockTimeOutException e)
        {
            // MSG-W0030=取消データ他端末作業中
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0030", "");
            return RESULT.SKIP;
        }

        // エラーなしの場合は取込可能
        return RESULT.LOAD;
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#loadData(jp.co.daifuku.wms.handler.Entity)
     */
    @Override
    protected RESULT loadData(Entity ent)
            throws CommonException
    {
        // システム定義コントローラ
        WarenaviSystemController sysCtrl = new WarenaviSystemController(getConnection(), this.getClass());
        PCTRetrieval ret = (PCTRetrieval)ent;

        // 取消区分が登録の場合
        if (ret.getCancelFlag().equals(SystemDefine.CANCEL_FLAG_NORMAL))
        {
            try
            {
                // シーケンスハンドラ
                WMSSequenceHandler _seq = new WMSSequenceHandler(getConnection());
                String planUKey = _seq.nextPCTRetPlanUkey();

                // PCT出庫予定情報の登録
                createPctRetPlan(ret, planUKey);

                // PCTマスタパッケージが導入されている場合
                if (sysCtrl.hasPCTMasterPack())
                {
                    // 荷主マスタ登録
                    createConsignor(ret);
                    // 商品マスタ登録
                    createPctItem(ret);
                }
                // エリアマスタ登録
                createArea(ret);
                // ゾーンマスタ登録
                createZone(ret);

                // エラーなしの場合は取込可能
                return RESULT.LOAD;
            }
            // 同一データがすでに存在した場合はスキップ
            catch (DataExistsException e)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025", "");
                return RESULT.SKIP;
            }
        }
        // 取消区分が削除の場合
        else if (ret.getCancelFlag().equals(SystemDefine.CANCEL_FLAG_HOST_CANCEL))
        {
            try
            {
                // 出庫予定情報を論理削除
                deletePctRetPlan(ret);

                // エラーなしの場合は取込可能
                return RESULT.LOAD;
            }
            // 対象データなしの場合はスキップ
            catch (NotFoundException e)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027", "");
                return RESULT.SKIP;
            }
        }
        // 区分誤りの場合はスキップ(ここは通らないはず)
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", PCTRetrieval.CANCEL_FLAG.getName());
            return RESULT.SKIP;
        }
    }

    /**
     * PCT出庫予定情報の登録を行います。
     *
     * @param ret 取込情報
     * @param planUkey 予定一意キー
     * @return 生成した登録データ
     * @throws CommonException
     */
    protected void createPctRetPlan(PCTRetrieval ret, String planUKey)
            throws CommonException
    {
        // PCT出庫予定情報エンティティ
        PCTRetPlan plan = new PCTRetPlan();

        // 値の設定
        // 予定一意キー
        plan.setPlanUkey(planUKey);
        // 取込単位キー
        plan.setLoadUnitKey(getSourceFileNameOutOfExtention());
        // ファイル行No
        plan.setFileLineNo(getDataCnt());
        // 状態フラグ
        plan.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        // スケジュール処理フラグ
        plan.setSchFlag(SystemDefine.SCH_FLAG_NOT_SCHEDULE);
        // ホスト取消区分
        plan.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        // 予定日
        plan.setPlanDay(ret.getPlanDay());
        // 荷主コード
        plan.setConsignorCode(ret.getConsignorCode());
        // 荷主名称
        plan.setConsignorName(ret.getConsignorName());
        // 出荷先コード
        plan.setCustomerCode(ret.getCustomerCode());
        // 出荷先名称
        plan.setCustomerName(ret.getCustomerName());
        // 出荷先分類
        plan.setCustomerCategory(ret.getCustomerCategory());
        // 得意先コード
        plan.setRegularCustomerCode(ret.getRegularCustomerCode());
        // 得意先名称
        plan.setRegularCustomerName(ret.getRegularCustomerName());
        // 出荷伝票No
        plan.setShipTicketNo(ret.getShipTicketNo());
        // 出荷伝票行No
        plan.setShipLineNo(ret.getShipLineNo());
        // 作業枝番
        plan.setBranchNo(ret.getBranchNo());
        // バッチNo
        plan.setBatchNo(ret.getBatchNo());
        // バッチSeqNo
        plan.setBatchSeqNo(ret.getBatchSeqNo());
        // オーダーNo
        plan.setPlanOrderNo(ret.getPlanOrderNo());
        // オーダー情報コメント
        plan.setOrderInfo(ret.getOrderInfo());
        // 通番
        plan.setThroughNo(ret.getThroughNo());
        // オーダー内商品数
        plan.setOrderItemQty(ret.getOrderItemQty());
        // オーダー通番
        plan.setOrderThroughNo(ret.getOrderThroughNo());
        // オーダー通番合計
        plan.setOrderThroughNoCnt(ret.getOrderThroughNoCnt());
        // 汎用フラグ
        plan.setGeneralFlag(ret.getGeneralFlag());
        // シュートNo
        plan.setShootNo(ret.getShootNo());
        // 予定エリア
        plan.setPlanAreaNo(ret.getPlanAreaNo());
        // 予定ゾーン
        plan.setPlanZoneNo(ret.getPlanZoneNo());
        // 作業ゾーン
        plan.setWorkZoneNo(ret.getWorkZoneNo());
        // 予定棚
        plan.setPlanLocationNo(ret.getPlanLocationNo());
        // 商品コード
        plan.setItemCode(ret.getItemCode());
        // 商品名称
        plan.setItemName(ret.getItemName());
        // ケース入数
        plan.setEnteringQty(ret.getEnteringQty());
        // ボール入数
        plan.setBundleEnteringQty(ret.getBundleEnteringQty());
        // ロット入数
        plan.setLotEnteringQty(ret.getLotEnteringQty());
        // JANコード
        plan.setJan(ret.getJan());
        // ケースITF
        plan.setItf(ret.getItf());
        // ボールITF
        plan.setBundleItf(ret.getBundleItf());
        // 基準日
        plan.setUseByDate(ret.getUseByDate());
        // アイテム情報コメント
        plan.setItemInfo(ret.getItemInfo());
        // 予定ロットNo
        plan.setPlanLotNo(ret.getPlanLotNo());
        // 予定数
        plan.setPlanQty(ret.getPlanQty());
        // 実績数
        plan.setResultQty(0);
        // 欠品数
        plan.setShortageQty(0);
        // 実績報告区分
        plan.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        // 作業日
        plan.setWorkDay("");
        // 登録区分
        plan.setRegistKind(SystemDefine.REGIST_KIND_DATALOADER);
        // 登録処理名
        plan.setRegistPname(this.getClass().getSimpleName());
        // 更新処理名
        plan.setLastUpdatePname(this.getClass().getSimpleName());

        // 登録
        _pctRetHandler.create(plan);
    }

    /**
     * 荷主マスタの登録、及び更新を行います。
     *
     * @param ret 取込情報
     * @throws CommonException
     */
    protected void createConsignor(PCTRetrieval ret)
            throws CommonException
    {
        // 荷主コードが指定されていない場合は処理しない
        if (StringUtil.isBlank(ret.getConsignorCode()))
        {
            return;
        }

        // 荷主マスタコントローラ
        ConsignorController consigCtrl = new ConsignorController(getConnection(), this.getClass());
        // 荷主マスタエンティティ
        Consignor consignor = new Consignor();

        // 値の設定
        consignor.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        consignor.setConsignorCode(ret.getConsignorCode());
        consignor.setConsignorName(ret.getConsignorName());
        consignor.setRegistPname(this.getClass().getSimpleName());
        consignor.setLastUpdatePname(this.getClass().getSimpleName());

        // 登録
        WmsUserInfo ui = new WmsUserInfo();
        consigCtrl.autoCreate(consignor, ui);
    }

    /**
     * 商品マスタの登録、及び更新を行います。
     *
     * @param ret 取込情報
     * @throws CommonException
     */
    protected void createPctItem(PCTRetrieval ret)
            throws CommonException
    {
        // 商品コードが指定されていない場合は処理しない
        if (StringUtil.isBlank(ret.getItemCode()))
        {
            return;
        }

        // 商品情報コントローラ
        PCTItemController itemctrl = new PCTItemController(getConnection(), this.getClass());
        // 商品マスタエンティティ
        PCTItem item = new PCTItem();
        // PCTシステム情報ハンドラ
        PCTSystemHandler sHandler = new PCTSystemHandler(getConnection());
        // PCTシステム情報更新キー
        PCTSystemSearchKey sKey = new PCTSystemSearchKey();

        // 値の設定
        item.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        item.setConsignorCode(ret.getConsignorCode());
        item.setConsignorName(ret.getConsignorName());
        item.setItemCode(ret.getItemCode());
        item.setItemName(ret.getItemName());
        item.setJan(ret.getJan());
        item.setItf(ret.getItf());
        item.setBundleItf(ret.getBundleItf());
        item.setLotEnteringQty(ret.getLotEnteringQty());
        item.setEnteringQty(ret.getEnteringQty());
        item.setBundleEnteringQty(ret.getBundleEnteringQty());

        // 重量誤差率をPCTシステム情報より取得
        PCTSystem system = (PCTSystem)sHandler.findPrimary(sKey);
        if (system != null)
        {
            item.setWeightDistinctRate(system.getDefultDistinctRate());
            item.setMaxInspectionUnitQty(PCTDisplayUtil.getMaxInspectionUnitQty(item.getWeightDistinctRate()));
        }

        // 登録
        WmsUserInfo ui = new WmsUserInfo();
        itemctrl.autoCreate(item, ui);
    }

    /**
     * エリアマスタの登録を行います。
     *
     * @param ret 取込情報
     * @throws CommonException
     */
    protected void createArea(PCTRetrieval ret)
            throws CommonException
    {
        // エリアが指定されていない場合は処理しない
        if (StringUtil.isBlank(ret.getPlanAreaNo()))
        {
            return;
        }

        // エリアコントローラ
        AreaController areaCtrl = new AreaController(getConnection(), this.getClass());
        // エリアマスタエンティティ
        Area area = new Area();

        // 値の設定
        area.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        area.setAreaNo(ret.getPlanAreaNo());
        area.setAreaType(SystemDefine.AREA_TYPE_FLOOR);
        area.setLocationType(SystemDefine.LOCATION_TYPE_FREE);
        area.setLocationStyle(WmsParam.DEFAULT_LOCATE_STYLE);
        area.setTemporaryAreaType(SystemDefine.TEMPORARY_AREA_TYPE_NONE);
        area.setVacantSearchType(SystemDefine.VACANT_SEARCH_TYPE_BANK_VERTICAL);

        // 検索
        Area[] areas = areaCtrl.lock(ret.getPlanAreaNo());
        if (ArrayUtil.length(areas) > 1)
        {
            throw new NoPrimaryException();
        }

        // 登録
        if (ArrayUtil.isEmpty(areas))
        {
            WmsUserInfo ui = new WmsUserInfo();
            areaCtrl.insert(area, ui);
        }
    }

    /**
     * ゾーンマスタの登録、及び更新を行います。
     *
     * @param ret 取込情報
     * @throws CommonException
     */
    protected void createZone(PCTRetrieval ret)
            throws CommonException
    {
        // 予定ゾーンと作業ゾーンが指定されていない場合は処理しない
        if (StringUtil.isBlank(ret.getPlanZoneNo()) && StringUtil.isBlank(ret.getWorkZoneNo()))
        {
            return;
        }

        // ゾーンコントローラ
        ZoneController zoneCtrl = new ZoneController(getConnection(), this.getClass());
        // ゾーンマスタエンティティ
        Zone zone = new Zone();

        // 値の設定
        // システム管理区分
        zone.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        // エリアNo
        zone.setAreaNo(ret.getPlanAreaNo());

        // 作業ゾーンが指定されている場合
        if (!StringUtil.isBlank(ret.getWorkZoneNo()))
        {
            zone.setWorkZoneNo(ret.getWorkZoneNo());
        }
        else
        {
            zone.setWorkZoneNo(ret.getPlanZoneNo());
        }

        // 予定ゾーンが指定されている場合
        if (!StringUtil.isBlank(ret.getPlanZoneNo()))
        {
            zone.setZoneNo(ret.getPlanZoneNo());
        }
        else
        {
            zone.setZoneNo(ret.getWorkZoneNo());
        }

        // 登録
        WmsUserInfo ui = new WmsUserInfo();
        zoneCtrl.autoCreate(zone, ui);
    }

    /**
     * PCT出庫予定情報の削除を行います。
     *
     * @param rec 取込情報
     * @throws CommonException
     */
    protected void deletePctRetPlan(PCTRetrieval ret)
            throws CommonException
    {
        // 条件の設定
        _pctRetAKey.clear();

        // 荷主コードが指定されている場合
        if (!StringUtil.isBlank(ret.getConsignorCode()))
        {
            _pctRetAKey.setConsignorCode(ret.getConsignorCode());
        }
        _pctRetAKey.setPlanDay(ret.getPlanDay());

        // 得意先コードが指定されている場合
        if (!StringUtil.isBlank(ret.getRegularCustomerCode()))
        {
            _pctRetAKey.setRegularCustomerCode(ret.getRegularCustomerCode());
        }
        _pctRetAKey.setCustomerCode(ret.getCustomerCode());

        // 出荷先分類が指定されている場合
        if (!StringUtil.isBlank(ret.getCustomerCategory()))
        {
            _pctRetAKey.setCustomerCategory(ret.getCustomerCategory());
        }

        // 出荷伝票No.が指定されている場合
        if (!StringUtil.isBlank(ret.getShipTicketNo()))
        {
            _pctRetAKey.setShipTicketNo(ret.getShipTicketNo());
            _pctRetAKey.setShipLineNo(ret.getShipLineNo());
            _pctRetAKey.setBranchNo(ret.getBranchNo());
        }
        _pctRetAKey.setBatchNo(ret.getBatchNo());

        // バッチSeqNo.が指定されている場合
        if (!StringUtil.isBlank(ret.getBatchSeqNo()))
        {
            _pctRetAKey.setBatchSeqNo(ret.getBatchSeqNo());
        }

        // オーダーNo.が指定されている場合
        if (!StringUtil.isBlank(ret.getPlanOrderNo()))
        {
            _pctRetAKey.setPlanOrderNo(ret.getPlanOrderNo());
        }

        // エリアNo.が指定されている場合
        if (!StringUtil.isBlank(ret.getPlanAreaNo()))
        {
            _pctRetAKey.setPlanAreaNo(ret.getPlanAreaNo());
        }
        _pctRetAKey.setPlanLocationNo(ret.getPlanLocationNo());
        _pctRetAKey.setItemCode(ret.getItemCode());
        _pctRetAKey.setLotEnteringQty(ret.getLotEnteringQty());
        _pctRetAKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        // 値の設定
        _pctRetAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _pctRetAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_HOST_CANCEL);
        _pctRetAKey.updateLastUpdatePname(this.getClass().getSimpleName());

        // 更新
        _pctRetHandler.modify(_pctRetAKey);
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#getEntity()
     */
    @Override
    protected AbstractEntity getEntity()
    {
        return new PCTRetrieval();
    }

    /**
     * 統計情報の呼出しを行います。
     *
     * @throws CommonException
     * @throws SQLException
     */
    protected void statics()
            throws CommonException
    {
        // 統計情報の呼出し
        _pctRetHandler.getStatics();
        _consigHandler.getStatics();
        _pctItemHandler.getStatics();
        _areaHandler.getStatics();
        _zoneHandler.getStatics();
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
        return "$Id: PCTRetPlanDataLoader.java 7912 2010-05-10 05:43:31Z kumano $";
    }
}
