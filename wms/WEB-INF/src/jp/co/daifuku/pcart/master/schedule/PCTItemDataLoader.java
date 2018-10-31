// $Id: PCTItemDataLoader.java 7908 2010-05-07 05:43:32Z kumano $
package jp.co.daifuku.pcart.master.schedule;

/*
 * Copyright(c) 2000-2010 DAIFUKU Co.,Ltd. All Rights Reserved.
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
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.base.util.PCTDisplayUtil;
import jp.co.daifuku.pcart.base.util.PCTSystemChecker;
import jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTItemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTItemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.ExchangeEnvironment;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.fileentity.PCTHostItem;
import jp.co.daifuku.wms.handler.AbstractEntity;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.field.FieldMetaData;
import jp.co.daifuku.wms.handler.file.AbstractFileHandler;
import jp.co.daifuku.wms.handler.file.FileHandler;

/**
 * PCT商品マスタの取り込み処理を行います。<br>
 *
 * @version $Revision: 7908 $, $Date: 2010-05-07 14:43:32 +0900 (金, 07 5 2010) $
 * @author  H.Okayama
 * @author  Last commit: $Author: kumano $
 */
public class PCTItemDataLoader
        extends AbstractDataLoaderForJava
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    /** PCT商品マスタハンドラ */
    private PCTItemHandler _itemHandler = null;

    /** PCT商品マスタ検索キー */
    private PCTItemSearchKey _itemKey = null;

    /** PCT商品マスタ更新キー */
    private PCTItemAlterKey _itemAKey = null;

    /** 荷主マスタハンドラ */
    private ConsignorHandler _consigHandler = null;

    /** 荷主マスタ検索キー */
    private ConsignorSearchKey _consigKey = null;

    /** PCT出庫予定情報ハンドラ */
    private PCTRetPlanHandler _retHandler = null;

    /** PCT出庫予定情報検索キー */
    private PCTRetPlanSearchKey _retKey = null;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * 取り込み区分仕入先マスタの受信データの取り込みを行います。<BR>
     * 自動取込みから使用します。
     */
    public PCTItemDataLoader()
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_MASTER_ITEM_PCT);
    }

    /**
     * 取り込み区分仕入先マスタの受信データの取り込みを行います。<BR>
     * WEB画面から使用します。
     *
     * @param userinfo ユーザ情報
     * @param locale ロケール
     */
    public PCTItemDataLoader(DfkUserInfo userinfo, Locale locale)
    {
        super(ExchangeEnvironment.LOAD_DATA_TYPE_MASTER_ITEM_PCT, userinfo, locale);
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
            throws ReadWriteException
    {
        // PCT商品マスタ
        _itemHandler = new PCTItemHandler(getConnection());
        _itemKey = new PCTItemSearchKey();
        _itemAKey = new PCTItemAlterKey();

        // 荷主マスタ
        _consigHandler = new ConsignorHandler(getConnection());
        _consigKey = new ConsignorSearchKey();

        // PCT出庫予定情報
        _retHandler = new PCTRetPlanHandler(getConnection());
        _retKey = new PCTRetPlanSearchKey();
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#check(jp.co.daifuku.wms.handler.Entity)
     */
    @Override
    protected RESULT check(Entity ent)
            throws CommonException
    {
        // 取込情報
        PCTHostItem item = (PCTHostItem)ent;

        // 日付のフォーマットチェック
        RESULT chk1 = checkDate(item);
        if (!chk1.equals(RESULT.LOAD))
        {
            return chk1;
        }
        // 禁止文字を含むかチェック
        RESULT chk2 = hasNGParameterText(item);
        if (!chk2.equals(RESULT.LOAD))
        {
            return chk2;
        }

        // 取込情報のチェック、及び補完
        checkParam(item);

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(item.getLoadFlag()))
        {
            // 同一データが存在する場合はスキップ
            _itemKey.clear();
            _itemKey.setConsignorCode(item.getConsignorCode());
            _itemKey.setItemCode(item.getItemCode());
            _itemKey.setLotEnteringQty(item.getLotEnteringQty());
            if (_itemHandler.count(_itemKey) > 0)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025", PCTHostItem.ITEM_CODE.getName());
                return RESULT.SKIP;
            }

            // 形式チェックで引っかかった場合はスキップ
            RESULT chk3 = checkFormat(item);
            if (!chk3.equals(RESULT.LOAD))
            {
                return chk3;
            }

            // 荷主コードが荷主マスタに登録されていない場合はスキップ
            _consigKey.clear();
            _consigKey.setConsignorCode(item.getConsignorCode());
            if (_consigHandler.count(_consigKey) == 0)
            {
                // MSG-W0023=マスタ未登録
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023", PCTHostItem.CONSIGNOR_CODE.getName());
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が削除の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(item.getLoadFlag()))
        {
            // 同一データが存在する場合はスキップ
            _itemKey.clear();
            _itemKey.setConsignorCode(item.getConsignorCode());
            _itemKey.setItemCode(item.getItemCode());
            _itemKey.setLotEnteringQty(item.getLotEnteringQty());
            PCTItem items = (PCTItem)_itemHandler.findPrimaryForUpdate(_itemKey);
            if (items == null)
            {
                // MSG-W0052=削除該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0052", PCTHostItem.ITEM_CODE.getName());
                return RESULT.SKIP;
            }

            // 対象データがシステム管理されている場合はスキップ
            if (SystemDefine.MANAGEMENT_TYPE_SYSTEM.equals(items.getManagementType()))
            {
                // MSG-W0053=削除不可データ
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0053", PCTHostItem.ITEM_CODE.getName());
                return RESULT.SKIP;
            }

            // PCT出庫予定情報で使用されている場合はスキップ
            _retKey.clear();
            _retKey.setConsignorCode(item.getConsignorCode());
            _retKey.setItemCode(item.getItemCode());
            _retKey.setLotEnteringQty(item.getLotEnteringQty());
            _retKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
            if (_retHandler.count(_retKey) > 0)
            {
                // MSG-W0054=データ使用中(削除不可)
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0054", PCTHostItem.ITEM_CODE.getName());
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        // 取消区分が修正の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_MODIFY.equals(item.getLoadFlag()))
        {
            // 修正対象データを取得
            _itemKey.clear();
            _itemKey.setConsignorCode(item.getConsignorCode());
            _itemKey.setItemCode(item.getItemCode());
            _itemKey.setLotEnteringQty(item.getLotEnteringQty());
            PCTItem items = (PCTItem)_itemHandler.findPrimaryForUpdate(_itemKey);
            if (items != null)
            {
                // 対象データがシステム管理されている場合はスキップ
                if (SystemDefine.MANAGEMENT_TYPE_SYSTEM.equals(items.getManagementType()))
                {
                    // MSG-P0024=修正不可データ
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0024", PCTHostItem.ITEM_CODE.getName());
                    return RESULT.SKIP;
                }
            }

            // 形式チェックで引っかかった場合はスキップ
            RESULT chk3 = checkFormat(item);
            if (!chk3.equals(RESULT.LOAD))
            {
                return chk3;
            }

            // 荷主コードが荷主マスタに登録されていない場合はスキップ
            _consigKey.clear();
            _consigKey.setConsignorCode(item.getConsignorCode());
            if (_consigHandler.count(_consigKey) == 0)
            {
                // MSG-W0023=マスタ未登録
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0023", PCTHostItem.CONSIGNOR_CODE.getName());
                return RESULT.SKIP;
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", PCTHostItem.LOAD_FLAG.getName());
            return RESULT.SKIP;
        }
    }

    /**
     * 日付のフォーマットチェックを行います。<BR>
     * <BR>
     * @param item 取込情報
     * @return チェック結果
     * @throws CommonException
     */
    protected RESULT checkDate(PCTHostItem item)
            throws CommonException
    {
        // 入荷限度日のフォーマットチェック
        if (!StringUtil.isBlank(item.getInstockLimitDate()))
        {
            RESULT chk1 = isDay(PCTHostItem.INSTOCK_LIMIT_DATE, item.getInstockLimitDate());
            if (!chk1.equals(RESULT.LOAD))
            {
                return chk1;
            }
        }

        // 出荷限度日のフォーマットチェック
        if (!StringUtil.isBlank(item.getShippingLimitDate()))
        {
            RESULT chk2 = isDay(PCTHostItem.SHIPPING_LIMIT_DATE, item.getShippingLimitDate());
            if (!chk2.equals(RESULT.LOAD))
            {
                return chk2;
            }
        }

        // 最新賞味期限のフォーマットチェック
        if (!StringUtil.isBlank(item.getLatestUseByDate()))
        {
            RESULT chk3 = isDay(PCTHostItem.LATEST_USE_BY_DATE, item.getLatestUseByDate());
            if (!chk3.equals(RESULT.LOAD))
            {
                return chk3;
            }
        }

        // 最新製造日のフォーマットチェック
        if (!StringUtil.isBlank(item.getLatestManufacutureDate()))
        {
            RESULT chk4 = isDay(PCTHostItem.LATEST_MANUFACUTURE_DATE, item.getLatestManufacutureDate());
            if (!chk4.equals(RESULT.LOAD))
            {
                return chk4;
            }
        }

        // 最新出庫日のフォーマットチェック
        if (!StringUtil.isBlank(item.getLatestRetrievalDate()))
        {
            RESULT chk5 = isDay(PCTHostItem.LATEST_RETRIEVAL_DATE, item.getLatestRetrievalDate());
            if (!chk5.equals(RESULT.LOAD))
            {
                return chk5;
            }
        }

        // 最新在庫のフォーマットチェック
        if (!StringUtil.isBlank(item.getLatestStock()))
        {
            RESULT chk6 = isDay(PCTHostItem.LATEST_STOCK, item.getLatestStock());
            if (!chk6.equals(RESULT.LOAD))
            {
                return chk6;
            }
        }

        // 最古在庫のフォーマットチェック
        if (!StringUtil.isBlank(item.getOldestStock()))
        {
            RESULT chk7 = isDay(PCTHostItem.OLDEST_STOCK, item.getOldestStock());
            if (!chk7.equals(RESULT.LOAD))
            {
                return chk7;
            }
        }

        // エラーなしの場合は取込可能
        return RESULT.LOAD;
    }

    /**
     * 取込情報のチェックを行い、必要があれば値の補完を行います。<BR>
     *
     * @param item
     * @throws CommonException
     */
    protected void checkParam(PCTHostItem item)
            throws CommonException
    {
        // 取込区分が省略されている場合
        if (StringUtil.isBlank(item.getLoadFlag()))
        {
            item.setLoadFlag(SystemDefine.CANCEL_FLAG_HOST_MODIFY);
        }

        // 荷主コードが省略されている場合
        if (StringUtil.isBlank(item.getConsignorCode()))
        {
            item.setConsignorCode(WmsParam.DEFAULT_CONSIGNOR_CODE);
        }

        // 重量誤差率が0の場合
        if (item.getWeightDistinct() == 0)
        {
            // 取込区分が登録、または取消の場合
            if (SystemDefine.CANCEL_FLAG_NORMAL.equals(item.getLoadFlag()))
                    //|| SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(item.getLoadFlag()))
            {
                item.setWeightDistinct(getDefaultDistinctRate());
            }
        }
    }

    /**
     * 登録・修正の場合のチェックを行います。
     *
     * @param item 取込情報
     * @return チェック結果
     * @throws CommonException
     */
    protected RESULT checkFormat(PCTHostItem item)
            throws CommonException
    {
        // ロット入り数チェック
        if (!PCTSystemChecker.checkLotEntering(item.getSingleWeight(), item.getLotEnteringQty(),
                item.getWeightDistinct()))
        {
            // MSG-P0025=ロット誤差重量がバラ重量をオーバー
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-P0025", PCTHostItem.LOT_ENTERING_QTY.getName());
        }

        // ファイルハンドラのメタ情報を取得
        float length = 0;
        PCTHostItem ent = new PCTHostItem();
        FileHandler handler = AbstractFileHandler.getInstance(ent);
        FieldMetaData[] fieldMetas = handler.getStoreMetaData().getFieldMetaDatas();

        // 棚形式チェック(ロケーションNo.1)
        if (!StringUtil.isBlank(item.getLocationNo1()))
        {
            // ロケーションNo.1の桁数を取得
            for (FieldMetaData fieldMeta : fieldMetas)
            {
                if (fieldMeta.getName().equals(PCTHostItem.LOCATION_NO_1.getName()))
                {
                    length = fieldMeta.getLength();
                    break;
                }
            }

            // 桁数が異なればスキップ
            if (item.getLocationNo1().length() != (int)length)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029", PCTHostItem.LOCATION_NO_1.getName());
                return RESULT.SKIP;
            }
        }

        // 棚形式チェック(ロケーションNo.2)
        if (!StringUtil.isBlank(item.getLocationNo2()))
        {
            // ロケーションNo.2の桁数を取得
            for (FieldMetaData fieldMeta : fieldMetas)
            {
                if (fieldMeta.getName().equals(PCTHostItem.LOCATION_NO_2.getName()))
                {
                    length = fieldMeta.getLength();
                    break;
                }
            }

            // 桁数が異なればスキップ
            if (item.getLocationNo2().length() != (int)length)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029", PCTHostItem.LOCATION_NO_2.getName());
                return RESULT.SKIP;
            }
        }

        // 棚形式チェック(ロケーションNo.3)
        if (!StringUtil.isBlank(item.getLocationNo3()))
        {
            // ロケーションNo.3の桁数を取得
            for (FieldMetaData fieldMeta : fieldMetas)
            {
                if (fieldMeta.getName().equals(PCTHostItem.LOCATION_NO_3.getName()))
                {
                    length = fieldMeta.getLength();
                    break;
                }
            }

            // 桁数が異なればスキップ
            if (item.getLocationNo3().length() != (int)length)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029", PCTHostItem.LOCATION_NO_3.getName());
                return RESULT.SKIP;
            }
        }

        // 棚形式チェック(ロケーションNo.4)
        if (!StringUtil.isBlank(item.getLocationNo4()))
        {
            // ロケーションNo.4の桁数を取得
            for (FieldMetaData fieldMeta : fieldMetas)
            {
                if (fieldMeta.getName().equals(PCTHostItem.LOCATION_NO_4.getName()))
                {
                    length = fieldMeta.getLength();
                    break;
                }
            }

            // 桁数が異なればスキップ
            if (item.getLocationNo4().length() != (int)length)
            {
                // MSG-W0029=データの値が不正
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029", PCTHostItem.LOCATION_NO_4.getName());
                return RESULT.SKIP;
            }
        }

        // 下限在庫数が上限在庫数を上回る場合はスキップ
        if (item.getUpperQty() < item.getLowerQty())
        {
            // MSG-W0029=データの値が不正
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0029", PCTHostItem.LOWER_QTY.getName());
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
        // 取込情報
        PCTHostItem item = (PCTHostItem)ent;

        // 取消区分が登録の場合
        if (SystemDefine.CANCEL_FLAG_NORMAL.equals(item.getLoadFlag()))
        {
            // PCT商品マスタエンティティ
            PCTItem items = new PCTItem();

            try
            {
                // 登録
                _itemHandler.create(getRegistItems(items, item));
                return RESULT.LOAD;
            }
            // 同一データがすでに存在した場合はスキップ
            catch (DataExistsException e)
            {
                // MSG-W0025=重複データあり
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025", PCTHostItem.ITEM_CODE.getName());
                return RESULT.SKIP;
            }
        }
        // 取消区分が削除の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_CANCEL.equals(item.getLoadFlag()))
        {
            // 削除条件を満たしていない場合
            if (StringUtil.isBlank(item.getConsignorCode()) && StringUtil.isBlank(item.getItemCode()))
            {
                throw new ScheduleException();
            }

            // 値の設定
            _itemAKey.clear();
            _itemAKey.setConsignorCode(item.getConsignorCode());
            _itemAKey.setItemCode(item.getItemCode());
            _itemAKey.setLotEnteringQty(item.getLotEnteringQty());

            try
            {
                // 削除
                _itemHandler.drop(_itemKey);
                return RESULT.LOAD;
            }
            // 対象データなしの場合はスキップ
            catch (NotFoundException e)
            {
                // MSG-W0027=取消該当データ無し
                insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0027", PCTHostItem.ITEM_CODE.getName());
                return RESULT.SKIP;
            }
        }
        // 取消区分が修正の場合
        else if (SystemDefine.CANCEL_FLAG_HOST_MODIFY.equals(item.getLoadFlag()))
        {
            // 修正対象データを取得
            _itemKey.clear();
            _itemKey.setConsignorCode(item.getConsignorCode());
            _itemKey.setItemCode(item.getItemCode());
            _itemKey.setLotEnteringQty(item.getLotEnteringQty());
            PCTItem[] searchItem = (PCTItem[])_itemHandler.find(_itemKey);
            if (searchItem == null || searchItem.length == 0)
            {
                // PCT商品マスタエンティティ
                PCTItem items = new PCTItem();

                try
                {
                    // 登録
                    _itemHandler.create(getRegistItems(items, item));
                }
                // 同一データがすでに存在した場合はスキップ
                catch (DataExistsException e)
                {
                    // MSG-W0025=重複データあり
                    insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0025", PCTHostItem.ITEM_CODE.getName());
                    return RESULT.SKIP;
                }
            }
            else
            {
                // 修正条件を満たしていない場合
                if (StringUtil.isBlank(item.getConsignorCode()) && StringUtil.isBlank(item.getItemCode()))
                {
                    throw new ScheduleException();
                }

                try
                {
                    // 修正
                    _itemHandler.modify(getModifyItems(item));
                }
                // 同一データが存在しなかった場合
                catch (NotFoundException e)
                {
                    // あり得ないため処理なし
                }
            }

            // エラーなしの場合は取込可能
            return RESULT.LOAD;
        }
        else
        {
            // MSG-W0068=データが有効範囲外
            insertLoadErrorInfo(SystemDefine.ERROR_LEVEL_WARNING, "MSG-W0068", PCTHostItem.LOAD_FLAG.getName());
            return RESULT.SKIP;
        }
    }

    /**
     * 登録するデータをエンティティに設定し返却します。
     *
     * @param items 設定対象のエンティティ
     * @param item 取込情報
     * @return 設定済みエンティティ
     * @throws CommonException
     */
    protected PCTItem getRegistItems(PCTItem items, PCTHostItem item)
            throws CommonException
    {
        // 値の設定
        items.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        items.setConsignorCode(item.getConsignorCode());
        items.setConsignorName(getConsignorName(item.getConsignorCode()));
        items.setItemCode(item.getItemCode());
        items.setItemName(item.getItemName());
        items.setJan(item.getJan());
        items.setItf(item.getItf());
        items.setBundleItf(item.getBundleItf());
        items.setLotEnteringQty(item.getLotEnteringQty());
        items.setEnteringQty(item.getEnteringQty());
        items.setBundleEnteringQty(item.getBundleEnteringQty());
        items.setUnit(item.getUnit());
        items.setSingleWeight(item.getSingleWeight());
        items.setWeightDistinctRate(getDefaultDistinctRate());
        items.setMaxInspectionUnitQty(PCTDisplayUtil.getMaxInspectionUnitQty(item.getWeightDistinct()));
        items.setLocationNo1(item.getLocationNo1());
        items.setEnteringQty1(item.getEnteringQty1());
        items.setLocationNo2(item.getLocationNo2());
        items.setEnteringQty2(item.getEnteringQty2());
        items.setLocationNo3(item.getLocationNo3());
        items.setEnteringQty3(item.getEnteringQty3());
        items.setLocationNo4(item.getLocationNo4());
        items.setEnteringQty4(item.getEnteringQty4());
        items.setInformation(item.getInformation());
        items.setUseByPeriod(item.getUseByPeriod());
        items.setInstockLimitDate(item.getInstockLimitDate());
        items.setShippingLimitDate(item.getShippingLimitDate());
        items.setLatestUseByDate(item.getLatestUseByDate());
        items.setLatestManufacutureDate(item.getLatestManufacutureDate());
        items.setLatestRetrievalDate(item.getLatestRetrievalDate());
        items.setLatestStock(item.getLatestStock());
        items.setOldestStock(item.getOldestStock());
        items.setManagementFlag(item.getManagementFlag());
        items.setUpperQty(item.getUpperQty());
        items.setLowerQty(item.getLowerQty());
        items.setRegistPname(this.getClass().getSimpleName());
        items.setLastUpdatePname(this.getClass().getSimpleName());

        // 設定した値を返却
        return items;
    }

    /**
     * 修正するデータを更新キーに設定し返却します。
     *
     * @param item 取込情報
     * @return 設定済み更新キー
     * @throws CommonException
     */
    protected PCTItemAlterKey getModifyItems(PCTHostItem item)
            throws CommonException
    {
        // 値の設定
        _itemAKey.setConsignorCode(item.getConsignorCode());
        _itemAKey.setItemCode(item.getItemCode());
        _itemAKey.setLotEnteringQty(item.getLotEnteringQty());

        // 荷主名称が取得できた場合
        String consigName = getConsignorName(item.getConsignorCode());
        if (!StringUtil.isBlank(consigName))
        {
            _itemAKey.updateConsignorName(consigName);
        }

        // 商品名称が指定されている場合
        if (!StringUtil.isBlank(item.getItemName()))
        {
            _itemAKey.updateItemName(item.getItemName());
        }

        // JANコードが指定されている場合
        if (!StringUtil.isBlank(item.getJan()))
        {
            _itemAKey.updateJan(item.getJan());
        }

        // ケースITFが指定されている場合
        if (!StringUtil.isBlank(item.getItf()))
        {
            _itemAKey.updateItf(item.getItf());
        }

        // ボールITFが指定されている場合
        if (!StringUtil.isBlank(item.getBundleItf()))
        {
            _itemAKey.updateBundleItf(item.getBundleItf());
        }

        // ケース入数が指定されている場合
        if (0 < item.getEnteringQty())
        {
            _itemAKey.updateEnteringQty(item.getEnteringQty());
        }

        // ボール入数が指定されている場合
        if (0 < item.getBundleEnteringQty())
        {
            _itemAKey.updateBundleEnteringQty(item.getBundleEnteringQty());
        }

        // 単位が指定されている場合
        if (0 < item.getUnit())
        {
            _itemAKey.updateUnit(item.getUnit());
        }

        // 単位重量が指定されている場合
        if (0.0 < item.getSingleWeight())
        {
            _itemAKey.updateSingleWeight(item.getSingleWeight());
        }

        // 誤差率が指定されている場合
        if (0.0 < item.getWeightDistinct())
        {
            // 誤差率
            _itemAKey.updateWeightDistinctRate(item.getWeightDistinct());
            // 最大検品単位数
            _itemAKey.updateMaxInspectionUnitQty(PCTDisplayUtil.getMaxInspectionUnitQty(item.getWeightDistinct()));
        }

        // ロケーションNo.1が指定されている場合
        if (!StringUtil.isBlank(item.getLocationNo1()))
        {
            _itemAKey.updateLocationNo1(item.getLocationNo1());
        }


        // ロケーション入数1が指定されている場合
        if (0 < item.getEnteringQty1())
        {
            _itemAKey.updateEnteringQty1(item.getEnteringQty1());
        }

        // ロケーションNo.2が指定されている場合
        if (!StringUtil.isBlank(item.getLocationNo2()))
        {
            _itemAKey.updateLocationNo2(item.getLocationNo2());
        }

        // ロケーション入数2が指定されている場合
        if (0 < item.getEnteringQty2())
        {
            _itemAKey.updateEnteringQty2(item.getEnteringQty2());
        }

        // ロケーションNo.3が指定されている場合
        if (!StringUtil.isBlank(item.getLocationNo3()))
        {
            _itemAKey.updateLocationNo3(item.getLocationNo3());
        }

        // ロケーション入数3が指定されている場合
        if (0 < item.getEnteringQty3())
        {
            _itemAKey.updateEnteringQty3(item.getEnteringQty3());
        }

        // ロケーションNo.4が指定されている場合
        if (!StringUtil.isBlank(item.getLocationNo4()))
        {
            _itemAKey.updateLocationNo4(item.getLocationNo4());
        }

        // ロケーション入数4が指定されている場合
        if (0 < item.getEnteringQty4())
        {
            _itemAKey.updateEnteringQty4(item.getEnteringQty4());
        }

        // メッセージが指定されている場合
        if (!StringUtil.isBlank(item.getInformation()))
        {
            _itemAKey.updateInformation(item.getInformation());
        }

        // 賞味期限が指定されている場合
        if (!StringUtil.isBlank(item.getUseByPeriod()))
        {
            _itemAKey.updateUseByPeriod(item.getUseByPeriod());
        }

        // 入荷限度日が指定されている場合
        if (!StringUtil.isBlank(item.getInstockLimitDate()))
        {
            _itemAKey.updateInstockLimitDate(item.getInstockLimitDate());
        }

        // 出荷限度日が指定されている場合
        if (!StringUtil.isBlank(item.getShippingLimitDate()))
        {
            _itemAKey.updateShippingLimitDate(item.getShippingLimitDate());
        }

        // 最新賞味期限が指定されている場合
        if (!StringUtil.isBlank(item.getLatestUseByDate()))
        {
            _itemAKey.updateLatestUseByDate(item.getLatestUseByDate());
        }

        // 最新製造日が指定されている場合
        if (!StringUtil.isBlank(item.getLatestManufacutureDate()))
        {
            _itemAKey.updateLatestManufacutureDate(item.getLatestManufacutureDate());
        }

        // 最新出庫日が指定されている場合
        if (!StringUtil.isBlank(item.getLatestRetrievalDate()))
        {
            _itemAKey.updateLatestRetrievalDate(item.getLatestRetrievalDate());
        }

        // 最新在庫が指定されている場合
        if (!StringUtil.isBlank(item.getLatestStock()))
        {
            _itemAKey.updateLatestStock(item.getLatestStock());
        }

        // 最古在庫が指定されている場合
        if (!StringUtil.isBlank(item.getOldestStock()))
        {
            _itemAKey.updateOldestStock(item.getOldestStock());
        }

        // 管理フラグが指定されている場合
        if (!StringUtil.isBlank(item.getManagementFlag()))
        {
            _itemAKey.updateManagementFlag(item.getManagementFlag());
        }

        // 上限在庫数が指定されている場合
        if (0 < item.getUpperQty())
        {
            _itemAKey.updateUpperQty(item.getUpperQty());
        }

        // 下限在庫数が指定されている場合
        if (0 < item.getLowerQty())
        {
            _itemAKey.updateLowerQty(item.getLowerQty());
        }
        _itemAKey.updateLastUpdatePname(this.getClass().getSimpleName());

        // 設定した値を返却
        return _itemAKey;
    }

    /**
     * 荷主コードから荷主名称を取得
     *
     * @param consignorCode 荷主コード
     * @return 荷主名称
     * @throws CommonException
     */
    protected String getConsignorName(String consignorCode)
            throws CommonException
    {
        // 荷主コードを基に荷主マスタを検索
        _consigKey.clear();
        _consigKey.setConsignorCode(consignorCode);
        Consignor consig = (Consignor)_consigHandler.findPrimary(_consigKey);
        if (consig != null)
        {
            return consig.getConsignorName();
        }
        else
        {
            return "";
        }
    }

    /**
     * 初期重量誤差率の取得
     *
     * @return 初期重量誤差率
     * @throws CommonException
     */
    protected int getDefaultDistinctRate()
            throws CommonException
    {
        // PCTシステム情報ハンドラ
        PCTSystemHandler sHandler = new PCTSystemHandler(getConnection());
        // PCTシステム情報検索キー
        PCTSystemSearchKey sKey = new PCTSystemSearchKey();

        // 検索
        sKey.clear();
        PCTSystem system = (PCTSystem)sHandler.findPrimary(sKey);
        if (system == null)
        {
            return 0;
        }
        else
        {
            return system.getDefultDistinctRate();
        }
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.base.common.AbstractDataLoaderForJava#getEntity()
     */
    @Override
    protected AbstractEntity getEntity()
    {
        return new PCTHostItem();
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
        _itemHandler.getStatics();
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
        return "$Id: PCTItemDataLoader.java 7908 2010-05-07 05:43:32Z kumano $";
    }
}
