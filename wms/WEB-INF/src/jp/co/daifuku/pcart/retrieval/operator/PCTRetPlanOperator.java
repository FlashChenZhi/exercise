// $Id: PCTRetPlanOperator.java 3209 2009-03-02 06:34:19Z arai $
package jp.co.daifuku.pcart.retrieval.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ArrayUtil;
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
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.controller.AreaController;
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTSystemSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.PCTItem;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTSystem;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.Zone;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.Entity;


/**
 * PCT出庫予定操作用のオペレータクラスです<br>
 *
 *
 * @version $Revision: 3209 $, $Date: 2009-03-02 15:34:19 +0900 (月, 02 3 2009) $
 * @author  020246
 * @author  Last commit: $Author: arai $
 */


public class PCTRetPlanOperator
        extends AbstractOperator
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    //------------------------------------------------------------
    // class variables (prefix '$')
    //------------------------------------------------------------
    // private static String $classVar ;

    //------------------------------------------------------------
    // instance variables (prefix '_')
    //------------------------------------------------------------
    // private String _instanceVar ;
    /**
     * WareNaviSystemコントローラ
     */
    private WarenaviSystemController _wnSysCtrl;

    /**
     * PCT出庫予定ハンドラ
     */
    private PCTRetPlanHandler _prpHndl;

    /**
     * PCT出庫予定検索キー
     */
    private PCTRetPlanSearchKey _prpSKey;

    /**
     * PCT出庫予定更新キー
     */
    private PCTRetPlanAlterKey _prpAKey;

    /**
     * 荷主マスタコントローラ
     */
    private ConsignorController _consignorCtrl;

    /**
     * 商品マスタコントローラ
     */
    private PCTItemController _pctItemCtrl;

    /**
     * エリアマスタコントローラ
     */
    private AreaController _areaCtrl;

    /**
     * ゾーンマスタコントローラ
     */
    private ZoneController _zoneCtrl;

    /**
     * シーケンスハンドラ
     */
    private WMSSequenceHandler _seqHndl;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと呼び出し元クラスを指定して、インスタンスを生成します。<BR>
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException スケジュール処理で発生した例外を報告します。
     */
    public PCTRetPlanOperator(Connection conn, Class caller) throws ReadWriteException,
            ScheduleException
    {
        super(conn, caller);

        // DBハンドラ、コントローラ生成
        // WareNaviSystem
        _wnSysCtrl = new WarenaviSystemController(conn, this.getClass());
        // PCT出庫予定
        _prpHndl = new PCTRetPlanHandler(conn);
        _prpSKey = new PCTRetPlanSearchKey();
        _prpAKey = new PCTRetPlanAlterKey();
        // 荷主マスタコントローラ
        _consignorCtrl = new ConsignorController(conn, this.getClass());
        // 商品マスタコントローラ
        _pctItemCtrl = new PCTItemController(conn, this.getClass());
        // エリアマスタコントローラ
        _areaCtrl = new AreaController(conn, this.getClass());
        // ゾーンマスタコントローラ
        _zoneCtrl = new ZoneController(conn, this.getClass());
        //シーケンスハンドラ
        _seqHndl = new WMSSequenceHandler(conn);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * PCT出庫予定検索<BR>
     * PCTRetrievalInParameterの検索条件に一致するPCT出庫予定情報を検索し、戻り値で返します。<BR>
     * 一致したデータが無かった場合はnullを返します。<BR>
     * 
     * @param param 検索用パラメータ
     * @return Entity[] PCT出庫予定情報の検索結果
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Entity[] findPlan(Parameter param)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pctParam = (PCTRetrievalInParameter)param;

        // 検索条件セット
        _prpSKey.clear();
        // 荷主コード
        if (!StringUtil.isBlank(pctParam.getConsignorCode()))
        {
            _prpSKey.setConsignorCode(pctParam.getConsignorCode());
        }
        // 予定日
        _prpSKey.setPlanDay(pctParam.getRetrievalPlanDay());
        // 得意先コード
        if (!StringUtil.isBlank(pctParam.getRegularCustomerCode()))
        {
            _prpSKey.setRegularCustomerCode(pctParam.getRegularCustomerCode());
        }
        // 出荷先コード
        _prpSKey.setCustomerCode(pctParam.getCustomerCode());
        // 出荷先分類
        if (!StringUtil.isBlank(pctParam.getCustomerCategory()))
        {
            _prpSKey.setCustomerCategory(pctParam.getCustomerCategory());
        }
        // 出荷伝票No
        if (!StringUtil.isBlank(pctParam.getTicketNo()))
        {
            // 出荷伝票No
            _prpSKey.setShipTicketNo(pctParam.getTicketNo());
            // 出荷伝票行No
            _prpSKey.setShipLineNo(pctParam.getLineNo());
            // 作業枝番
            _prpSKey.setBranchNo(pctParam.getBranchNo());
        }
        // バッチNo.
        _prpSKey.setBatchNo(pctParam.getBatchNo());
        // バッチSeqNo.
        if (!StringUtil.isBlank(pctParam.getBatchSeqNo()))
        {
            _prpSKey.setBatchSeqNo(pctParam.getBatchSeqNo());
        }
        // オーダーNo
        if (!StringUtil.isBlank(pctParam.getPlanOrderNo()))
        {
            _prpSKey.setPlanOrderNo(pctParam.getPlanOrderNo());
        }
        // エリアNo
        if (!StringUtil.isBlank(pctParam.getAreaNo()))
        {
            _prpSKey.setPlanAreaNo(pctParam.getAreaNo());
        }
        // 棚No
        _prpSKey.setPlanLocationNo(pctParam.getLocation());
        // 商品コード
        _prpSKey.setItemCode(pctParam.getItemCode());
        // ロット入数
        _prpSKey.setLotEnteringQty(pctParam.getLotEnteringQty());
        // 状態フラグ（削除以外）
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // PCT出庫予定情報の取得
        Entity[] ent = _prpHndl.find(_prpSKey);
        if (ArrayUtil.isEmpty(ent))
        {
            return null;
        }
        else
        {
            return ent;
        }
    }

    /**
     * PCT出庫予定登録<BR>
     * startParamで指定されたパラメータの内容をもとに、PCT出庫予定データ登録処理を行います。<BR>
     * 登録失敗など異常が発生した場合は、例外を通知します。<BR>
     * 
     * @param registKind 登録区分
     * @param startParam 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws LockTimeOutException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 登録データがすでに存在するときスローされます。
     * @throws NoPrimaryException 同一予定一意キーが複数存在した場合、スローされます。
     * @throws ScheduleException スケジュール処理で発生した例外を報告します。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     */
    public void createPlan(String registKind, Parameter startParam)
            throws ReadWriteException,
                LockTimeOutException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException,
                OperatorException
    {
        // 予定一意キー採番
        String planUKey = _seqHndl.nextPCTRetPlanUkey();

        // 作成内容をセット(PCT出庫予定)
        createPCTRetPlan(planUKey, registKind, _wnSysCtrl.hasStockPack(), startParam);

        // PCTマスタパッケージが導入されている場合
        if (_wnSysCtrl.hasPCTMasterPack())
        {
            // 荷主マスタ自動登録
            createConsignorMaster(startParam);
            // 商品マスタ自動登録
            createItemMaster(startParam);
        }
        // エリアマスタ自動登録
        createAreaMaster(startParam);
        // ゾーンマスタ自動登録
        createZoneMaster(startParam);
    }

    /**
     * PCT出庫予定削除<BR>
     * startParamで指定されたパラメータの内容をもとに、PCT出庫予定データ削除処理を行います。<BR>
     * 削除失敗など異常が発生した場合は、例外を通知します。<BR>
     * 
     * @param registKind 登録区分
     * @param startParam 入力パラメータ
     * @throws NotFoundException 該当データが見つからないときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException スケジュール処理で発生した例外を報告します。
     */
    public void deletePlan(String registKind, Parameter startParam)
            throws NotFoundException,
                ReadWriteException,
                ScheduleException
    {
        // キャスト
        PCTRetrievalInParameter pctParam = (PCTRetrievalInParameter)startParam;

        // PCT出庫予定削除条件セット
        _prpAKey.clear();
        if (!StringUtil.isBlank(pctParam.getPlanUkey()))
        {
            // 予定一意キーをセット
            _prpAKey.setPlanUkey(pctParam.getPlanUkey());
        }
        else
        {
            // 荷主コード
            if (!StringUtil.isBlank(pctParam.getConsignorCode()))
            {
                _prpAKey.setConsignorCode(pctParam.getConsignorCode());
            }
            // 予定日
            _prpAKey.setPlanDay(pctParam.getRetrievalPlanDay());
            // 得意先コード
            if (!StringUtil.isBlank(pctParam.getRegularCustomerCode()))
            {
                _prpAKey.setRegularCustomerCode(pctParam.getRegularCustomerCode());
            }
            // 出荷先コード
            _prpAKey.setCustomerCode(pctParam.getCustomerCode());
            // 出荷先分類
            if (!StringUtil.isBlank(pctParam.getCustomerCategory()))
            {
                _prpAKey.setCustomerCategory(pctParam.getCustomerCategory());
            }
            // 出荷伝票No
            if (!StringUtil.isBlank(pctParam.getTicketNo()))
            {
                // 出荷伝票No
                _prpAKey.setShipTicketNo(pctParam.getTicketNo());
                // 出荷伝票行No
                _prpAKey.setShipLineNo(pctParam.getLineNo());
                // 作業枝番
                _prpAKey.setBranchNo(pctParam.getBranchNo());
            }
            // バッチNo.
            _prpAKey.setBatchNo(pctParam.getBatchNo());
            // バッチSeqNo.
            if (!StringUtil.isBlank(pctParam.getBatchSeqNo()))
            {
                _prpAKey.setBatchSeqNo(pctParam.getBatchSeqNo());
            }
            // オーダーNo
            if (!StringUtil.isBlank(pctParam.getPlanOrderNo()))
            {
                _prpAKey.setPlanOrderNo(pctParam.getPlanOrderNo());
            }
            // エリアNo
            if (!StringUtil.isBlank(pctParam.getAreaNo()))
            {
                _prpAKey.setPlanAreaNo(pctParam.getAreaNo());
            }
            // 棚No
            _prpAKey.setPlanLocationNo(pctParam.getLocation());
            // 商品コード
            _prpAKey.setItemCode(pctParam.getItemCode());
            // ロット入数
            _prpAKey.setLotEnteringQty(pctParam.getLotEnteringQty());
        }
        // 状態フラグ「未作業」
        _prpAKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        // 更新値をセット
        _prpAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        if (PCTRetrievalInParameter.REGIST_KIND_DATALOADER.equals(registKind))
        {
            _prpAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_HOST_CANCEL);
        }
        else
        {
            _prpAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        }
        _prpAKey.updateLastUpdatePname(getCallerName());

        // PCT出庫予定更新
        _prpHndl.modify(_prpAKey);
    }

    /**
     * 1オーダー内に複数の予定日が無いかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。予定日が複数存在した場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean isOrderInPlanDay(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)checkParam;

        // PCT出庫予定検索条件セット
        _prpSKey.clear();
        _prpSKey.setPlanOrderNo(pInParam.getPlanOrderNo());
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 今回の出庫予定日以外
        _prpSKey.setPlanDay(pInParam.getRetrievalPlanDay(), "!=");

        // 出庫予定日で集約
        _prpSKey.setPlanDayGroup();
        _prpSKey.setPlanDayCollect();

        int count = _prpHndl.count(_prpSKey);

        // 取得した予定日が複数件該当した場合、falseを返します。
        if (count > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 1オーダー内に複数の得意先コードが無いかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。得意先が複数存在した場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean isOrderInRegularCustomer(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)checkParam;

        // PCT出庫予定検索条件セット
        _prpSKey.clear();
        _prpSKey.setPlanOrderNo(pInParam.getPlanOrderNo());
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 今回の得意先コード以外
        _prpSKey.setRegularCustomerCode(pInParam.getRegularCustomerCode(), "!=");

        // 得意先コードで集約
        _prpSKey.setRegularCustomerCodeGroup();
        _prpSKey.setRegularCustomerCodeCollect();

        int count = _prpHndl.count(_prpSKey);

        // 取得した得意先が複数件該当した場合、falseを返します。
        if (count > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 1オーダー内に複数の出荷先コードが無いかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。出荷先が複数存在した場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean isOrderInCustomer(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)checkParam;

        // PCT出庫予定検索条件セット
        _prpSKey.clear();
        _prpSKey.setPlanOrderNo(pInParam.getPlanOrderNo());
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 今回の出荷先コード以外
        _prpSKey.setCustomerCode(pInParam.getCustomerCode(), "!=");

        // 出荷先コードで集約
        _prpSKey.setCustomerCodeGroup();
        _prpSKey.setCustomerCodeCollect();

        int count = _prpHndl.count(_prpSKey);

        // 取得した出荷先が複数件該当した場合、falseを返します。
        if (count > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 1オーダー内に複数のエリアが無いかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。エリアNo.が複数存在した場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean isOrderInArea(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)checkParam;

        // PCT出庫予定検索条件セット
        _prpSKey.clear();
        _prpSKey.setPlanOrderNo(pInParam.getPlanOrderNo());
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 今回のエリアNo.以外
        _prpSKey.setPlanAreaNo(pInParam.getAreaNo(), "!=");

        // エリアNo.で集約
        _prpSKey.setPlanAreaNoGroup();
        _prpSKey.setPlanAreaNoCollect();

        int count = _prpHndl.count(_prpSKey);

        // 取得したエリアNo.が複数件該当した場合、falseを返します。
        if (count > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 1オーダー内に複数の出荷先分類が無いかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。出荷先分類が複数存在した場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean isOrderInCustomerCategory(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)checkParam;

        // PCT出庫予定検索条件セット
        _prpSKey.clear();
        _prpSKey.setPlanOrderNo(pInParam.getPlanOrderNo());
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 今回の出荷先分類以外
        _prpSKey.setCustomerCategory(pInParam.getCustomerCategory(), "!=");

        // 出荷先分類で集約
        _prpSKey.setCustomerCategoryGroup();
        _prpSKey.setCustomerCategoryCollect();

        int count = _prpHndl.count(_prpSKey);

        // 取得した出荷先分類が複数件該当した場合、falseを返します。
        if (count > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 1バッチSeqNo.内に複数の予定日が無いかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。予定日が複数存在した場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean isPlanDayInBatchSeqNo(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)checkParam;

        // PCT出庫予定検索条件セット
        _prpSKey.clear();
        _prpSKey.setBatchSeqNo(pInParam.getBatchSeqNo());
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 今回の予定日以外
        _prpSKey.setPlanDay(pInParam.getRetrievalPlanDay(), "!=");

        // 予定日で集約
        _prpSKey.setPlanDayGroup();
        _prpSKey.setPlanDayCollect();

        int count = _prpHndl.count(_prpSKey);

        // 取得した予定日が複数件該当した場合、falseを返します。
        if (count > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 1オーダー内に複数のバッチSeqNo.が無いかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。バッチSeqNo.が複数存在した場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean isOrderInBatchSeqNo(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)checkParam;

        // PCT出庫予定検索条件セット
        _prpSKey.clear();
        _prpSKey.setPlanOrderNo(pInParam.getPlanOrderNo());
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 今回のバッチSeqNo.以外
        _prpSKey.setBatchSeqNo(pInParam.getBatchSeqNo(), "!=");

        // バッチSeqNo.で集約
        _prpSKey.setBatchSeqNoGroup();
        _prpSKey.setBatchSeqNoCollect();

        int count = _prpHndl.count(_prpSKey);

        // 取得したバッチSeqNo.が複数件該当した場合、falseを返します。
        if (count > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 1バッチSeqNo.内に複数のバッチNo.が無いかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。バッチNo.が複数存在した場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean isBatchNoInBatchSeqNo(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)checkParam;

        // PCT出庫予定検索条件セット
        _prpSKey.clear();
        _prpSKey.setBatchSeqNo(pInParam.getBatchSeqNo());
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 今回のバッチNo.以外
        _prpSKey.setBatchNo(pInParam.getBatchNo(), "!=");

        // バッチNo.で集約
        _prpSKey.setBatchNoGroup();
        _prpSKey.setBatchNoCollect();

        int count = _prpHndl.count(_prpSKey);

        // 取得したバッチNo.が複数件該当した場合、falseを返します。
        if (count > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 1バッチオーダー内に複数の通番が無いかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。通番が複数存在した場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean isOrderInThroughNo(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)checkParam;

        // PCT出庫予定検索条件セット
        _prpSKey.clear();
        // 荷主コード
        _prpSKey.setConsignorCode(pInParam.getConsignorCode());
        // 出庫予定日
        _prpSKey.setPlanDay(pInParam.getRetrievalPlanDay());
        // 得意先コード
        _prpSKey.setRegularCustomerCode(pInParam.getRegularCustomerCode());
        // 出荷先コード
        _prpSKey.setCustomerCode(pInParam.getCustomerCode());
        // 出荷先分類
        _prpSKey.setCustomerCategory(pInParam.getCustomerCategory());
        // エリアNo.
        _prpSKey.setPlanAreaNo(pInParam.getAreaNo());
        // バッチSeqNo.
        _prpSKey.setBatchSeqNo(pInParam.getBatchSeqNo());
        // オーダーNo.
        _prpSKey.setPlanOrderNo(pInParam.getPlanOrderNo());
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 通番
        _prpSKey.setThroughNo(pInParam.getThroughNo());

        int count = _prpHndl.count(_prpSKey);

        // 取得した通番が複数件該当した場合、falseを返します。
        if (count > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 1バッチオーダー内の商品数が範囲を超えていないかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。アイテム数がオーダー内商品数を超えている場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean checkOrderItemQty(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)checkParam;

        // PCT出庫予定検索条件セット
        _prpSKey.clear();
        // 荷主コード
        _prpSKey.setConsignorCode(pInParam.getConsignorCode());
        // 出庫予定日
        _prpSKey.setPlanDay(pInParam.getRetrievalPlanDay());
        // 得意先コード
        _prpSKey.setRegularCustomerCode(pInParam.getRegularCustomerCode());
        // 出荷先コード
        _prpSKey.setCustomerCode(pInParam.getCustomerCode());
        // 出荷先分類
        _prpSKey.setCustomerCategory(pInParam.getCustomerCategory());
        // エリアNo.
        _prpSKey.setPlanAreaNo(pInParam.getAreaNo());
        // バッチSeqNo.
        _prpSKey.setBatchSeqNo(pInParam.getBatchSeqNo());
        // オーダーNo.
        _prpSKey.setPlanOrderNo(pInParam.getPlanOrderNo());
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // アイテム数となっているが、オーダー内の予定行数を意味しているので、
        // 商品コードの一致条件は、省きます。

        int count = _prpHndl.count(_prpSKey);

        // アイテム数がオーダー内商品数を超えている場合、falseを返します。
        if (count + 1 > pInParam.getOrderItemQty())
        {
            return false;
        }

        return true;
    }

    /**
     * 1バッチSeqNo.内に複数のオーダー通番が無いかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。オーダー通番が複数存在した場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean isOrderInOrderThroughNo(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)checkParam;

        // PCT出庫予定検索条件セット
        _prpSKey.clear();
        // 荷主コード
        _prpSKey.setConsignorCode(pInParam.getConsignorCode());
        // 出庫予定日
        _prpSKey.setPlanDay(pInParam.getRetrievalPlanDay());
        // 得意先コード
        _prpSKey.setRegularCustomerCode(pInParam.getRegularCustomerCode());
        // 出荷先コード
        _prpSKey.setCustomerCode(pInParam.getCustomerCode());
        // 出荷先分類
        _prpSKey.setCustomerCategory(pInParam.getCustomerCategory());
        // エリアNo.
        _prpSKey.setPlanAreaNo(pInParam.getAreaNo());
        // バッチSeqNo.
        _prpSKey.setBatchSeqNo(pInParam.getBatchSeqNo());
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 今回のオーダーNo.以外
        _prpSKey.setPlanOrderNo(pInParam.getPlanOrderNo(), "!=");
        // オーダー通番
        _prpSKey.setOrderThroughNo(pInParam.getOrderThroughNo());

        int count = _prpHndl.count(_prpSKey);

        // 取得したオーダーNo.が複数件該当した場合、falseを返します。
        if (count > 0)
        {
            return false;
        }

        return true;
    }

    /**
     * 1バッチSeqNo.内のオーダー数が範囲を超えていないかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。オーダー数がオーダー内通番合計を超えている場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean checkOrderThroughNoCnt(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)checkParam;

        // PCT出庫予定検索条件セット
        _prpSKey.clear();
        // 荷主コード
        _prpSKey.setConsignorCode(pInParam.getConsignorCode());
        // 出庫予定日
        _prpSKey.setPlanDay(pInParam.getRetrievalPlanDay());
        // 得意先コード
        _prpSKey.setRegularCustomerCode(pInParam.getRegularCustomerCode());
        // 出荷先コード
        _prpSKey.setCustomerCode(pInParam.getCustomerCode());
        // 出荷先分類
        _prpSKey.setCustomerCategory(pInParam.getCustomerCategory());
        // エリアNo.
        _prpSKey.setPlanAreaNo(pInParam.getAreaNo());
        // バッチSeqNo.
        _prpSKey.setBatchSeqNo(pInParam.getBatchSeqNo());
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // 今回のオーダーNo以外
        _prpSKey.setPlanOrderNo(pInParam.getPlanOrderNo(), "!=");

        // オーダーNo.で集約
        _prpSKey.setPlanOrderNoGroup();
        _prpSKey.setPlanOrderNoCollect();

        int count = _prpHndl.count(_prpSKey);

        // オーダー数がオーダー内通番合計を超えている場合、falseを返します。
        if (count + 1 > pInParam.getOrderThroughNoCnt())
        {
            return false;
        }

        return true;
    }

    /**
     * PCT出庫予定検索<BR>
     * PCTRetrievalInParameterのバッチSeqNoにてスケジュール済みを検索します。<BR>
     * 一致したデータが無かった場合はtrueを返します。<BR>
     * 
     * @param param 検索用パラメータ
     * @return boolean スケジュール済みなし:true スケジュール済みあり:false
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean exeistSchedule(Parameter param)
            throws ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pctParam = (PCTRetrievalInParameter)param;

        // 検索条件セット
        _prpSKey.clear();
        // バッチSeqNo.
        _prpSKey.setBatchSeqNo(pctParam.getBatchSeqNo());
        // 状態フラグ（削除以外）
        _prpSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");
        // スケジュールフラグ（スケジュール中）
        _prpSKey.setSchFlag(SystemDefine.SCH_FLAG_SCHEDULE);

        // PCT出庫予定情報の取得
        int count = _prpHndl.count(_prpSKey);
        if (count > 0)
        {
            return false;
        }
        else
        {
            return true;
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
     * PCT出庫予定データを作成します
     * 
     * @param planUKey 予定一意キー
     * @param registKind 登録区分
     * @param stockPack 在庫パッケージ導入フラグ
     * @param startParam 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 登録データがすでに存在するときスローされます。
     * @throws ScheduleException スケジュール処理で発生した例外を報告します。
     */
    protected void createPCTRetPlan(String planUKey, String registKind, boolean stockPack, Parameter startParam)
            throws ReadWriteException,
                DataExistsException,
                ScheduleException
    {
        PCTRetPlan prpEnt = new PCTRetPlan();
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)startParam;

        // 作成内容をセット(PCT出庫予定)
        // 予定一意キー
        prpEnt.setPlanUkey(planUKey);
        // 取込単位キー
        prpEnt.setLoadUnitKey(pInParam.getLoadUnitKey());
        // ファイル行No
        prpEnt.setFileLineNo(pInParam.getRowNo());
        // 状態フラグ
        prpEnt.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        // スケジュール処理フラグ
        prpEnt.setSchFlag(SystemDefine.SCH_FLAG_NOT_SCHEDULE);
        // ホスト取消区分
        prpEnt.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        // 予定日
        prpEnt.setPlanDay(pInParam.getRetrievalPlanDay());
        // 荷主コード
        prpEnt.setConsignorCode(pInParam.getConsignorCode());
        // 荷主名称
        prpEnt.setConsignorName(pInParam.getConsignorName());
        // 出荷先コード
        prpEnt.setCustomerCode(pInParam.getCustomerCode());
        // 出荷先名称
        prpEnt.setCustomerName(pInParam.getCustomerName());
        // 出荷先分類
        prpEnt.setCustomerCategory(pInParam.getCustomerCategory());
        // 得意先コード
        prpEnt.setRegularCustomerCode(pInParam.getRegularCustomerCode());
        // 得意先名称
        prpEnt.setRegularCustomerName(pInParam.getRegularCustomerName());
        // 出荷伝票No
        prpEnt.setShipTicketNo(pInParam.getTicketNo());
        // 出荷伝票行No
        prpEnt.setShipLineNo(pInParam.getLineNo());
        // 作業枝番
        prpEnt.setBranchNo(pInParam.getBranchNo());
        // バッチNo
        prpEnt.setBatchNo(pInParam.getBatchNo());
        // バッチSeqNo
        prpEnt.setBatchSeqNo(pInParam.getBatchSeqNo());
        // オーダーNo
        prpEnt.setPlanOrderNo(pInParam.getPlanOrderNo());
        // オーダー情報コメント
        prpEnt.setOrderInfo(pInParam.getOrderInfo());
        // 通番
        prpEnt.setThroughNo(pInParam.getThroughNo());
        // オーダー内商品数
        prpEnt.setOrderItemQty(pInParam.getOrderItemQty());
        // オーダー通番
        prpEnt.setOrderThroughNo(pInParam.getOrderThroughNo());
        // オーダー通番合計
        prpEnt.setOrderThroughNoCnt(pInParam.getOrderThroughNoCnt());
        // 汎用フラグ
        prpEnt.setGeneralFlag(pInParam.getGeneralFlag());
        // シュートNo
        prpEnt.setShootNo(pInParam.getShootNo());
        // 予定エリア
        prpEnt.setPlanAreaNo(pInParam.getAreaNo());
        // 予定ゾーン
        prpEnt.setPlanZoneNo(pInParam.getZoneNo());
        // 作業ゾーン
        prpEnt.setWorkZoneNo(pInParam.getWorkZoneNo());
        // 予定棚
        prpEnt.setPlanLocationNo(pInParam.getLocation());
        // 商品コード
        prpEnt.setItemCode(pInParam.getItemCode());
        // 商品名称
        prpEnt.setItemName(pInParam.getItemName());
        // ケース入数
        prpEnt.setEnteringQty(pInParam.getEnteringQty());
        // ボール入数
        prpEnt.setBundleEnteringQty(pInParam.getBundleEnteringQty());
        // ロット入数
        prpEnt.setLotEnteringQty(pInParam.getLotEnteringQty());
        // JANコード
        prpEnt.setJan(pInParam.getJanCode());
        // ケースITF
        prpEnt.setItf(pInParam.getItf());
        // ボールITF
        prpEnt.setBundleItf(pInParam.getBundleItf());
        // 基準日
        prpEnt.setUseByDate(pInParam.getUseByDate());
        // アイテム情報コメント
        prpEnt.setItemInfo(pInParam.getItemInfo());
        // 予定ロットNo
        prpEnt.setPlanLotNo(pInParam.getLotNo());
        // 予定数
        prpEnt.setPlanQty(pInParam.getPlanQty());
        // 実績数
        prpEnt.setResultQty(0);
        // 欠品数
        prpEnt.setShortageQty(0);
        // 実績報告区分
        prpEnt.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        // 作業日
        prpEnt.setWorkDay("");
        // 登録区分
        prpEnt.setRegistKind(registKind);
        // 登録処理名
        prpEnt.setRegistPname(getCallerName());
        // 更新処理名
        prpEnt.setLastUpdatePname(getCallerName());

        // PCT出庫予定データ作成
        _prpHndl.create(prpEnt);
    }

    /**
     * 荷主マスタの登録を行ないます。<BR>
     * マスタが存在する場合は何もしません。<BR>
     * 
     * @param startParam 入力パラメータ
     * @throws NoPrimaryException 同一のエリアNoが複数存在したときにスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     */
    protected void createConsignorMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter inParam = (PCTRetrievalInParameter)startParam;
        Consignor entity = new Consignor();

        // システム管理区分
        entity.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        // 荷主コード
        entity.setConsignorCode(inParam.getConsignorCode());
        // 荷主名称
        entity.setConsignorName(inParam.getConsignorName());

        try
        {
            // read master with lock
            Consignor[] consignors = _consignorCtrl.lock(entity.getConsignorCode());
            if (ArrayUtil.length(consignors) > 1)
            {
                throw new NoPrimaryException();
            }

            if (ArrayUtil.isEmpty(consignors))
            {
                _consignorCtrl.insert(entity, inParam.getWmsUserInfo());
                return;
            }
        }
        catch (LockTimeOutException e)
        {
            // 他端末で作業中
            throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
        }
        catch (DataExistsException e)
        {
            // 他端末で更新済み
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * 商品マスタの登録を行ないます。<BR>
     * マスタが存在する場合は何もしません。<BR>
     * 
     * @param startParam 入力パラメータ
     * @throws NoPrimaryException 同一のエリアNoが複数存在したときにスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     */
    protected void createItemMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter inParam = (PCTRetrievalInParameter)startParam;
        PCTItem entity = new PCTItem();

        // システム管理区分
        entity.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        // 荷主コード
        entity.setConsignorCode(inParam.getConsignorCode());
        // 荷主名称
        entity.setConsignorName(inParam.getConsignorName());
        // 商品コード
        entity.setItemCode(inParam.getItemCode());
        // 商品名称
        entity.setItemName(inParam.getItemName());
        // JANコード
        entity.setJan(inParam.getJanCode());
        // ケースITF
        entity.setItf(inParam.getItf());
        // ボールITF
        entity.setBundleItf(inParam.getBundleItf());
        // ロット入数
        entity.setLotEnteringQty(inParam.getLotEnteringQty());
        // ケース入数
        entity.setEnteringQty(inParam.getEnteringQty());
        // ボール入数
        entity.setBundleEnteringQty(inParam.getBundleEnteringQty());

        try
        {
            // read master with lock
            PCTItem[] items =
                    _pctItemCtrl.lock(entity.getConsignorCode(), entity.getItemCode(), entity.getLotEnteringQty());
            if (ArrayUtil.length(items) > 1)
            {
                throw new NoPrimaryException();
            }

            if (ArrayUtil.isEmpty(items))
            {
                // 重量誤差率をPCTシステム情報より取得
                PCTSystemHandler sHandler = new PCTSystemHandler(getConnection());
                PCTSystemSearchKey sKey = new PCTSystemSearchKey();
                PCTSystem system = (PCTSystem)sHandler.findPrimary(sKey);
                if (system != null)
                {
                    entity.setWeightDistinctRate(system.getDefultDistinctRate());
                    entity.setMaxInspectionUnitQty(PCTDisplayUtil.getMaxInspectionUnitQty(entity.getWeightDistinctRate()));
                }

                _pctItemCtrl.insert(entity, inParam.getWmsUserInfo());
                return;
            }
        }
        catch (LockTimeOutException e)
        {
            // 他端末で作業中
            throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
        }
        catch (DataExistsException e)
        {
            // 他端末で更新済み
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * エリアマスタの登録を行ないます。<BR>
     * マスタが存在する場合は何もしません。<BR>
     * 
     * @param startParam 入力パラメータ
     * @throws NoPrimaryException 同一のエリアNoが複数存在したときにスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     */
    protected void createAreaMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)startParam;
        Area aEnt = new Area();

        // システム管理区分
        aEnt.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        // エリアNo
        aEnt.setAreaNo(pInParam.getAreaNo());
        // エリア種別
        aEnt.setAreaType(SystemDefine.AREA_TYPE_FLOOR);
        // 棚管理方式
        aEnt.setLocationType(SystemDefine.LOCATION_TYPE_FREE);
        // 棚表示形式
        aEnt.setLocationStyle(WmsParam.DEFAULT_LOCATE_STYLE);
        // 仮置在庫作成区分
        aEnt.setTemporaryAreaType(SystemDefine.TEMPORARY_AREA_TYPE_NONE);
        // 空棚検索方法
        aEnt.setVacantSearchType(SystemDefine.VACANT_SEARCH_TYPE_BANK_VERTICAL);

        try
        {
            // read master with lock
            Area[] areas = _areaCtrl.lock(aEnt.getAreaNo());
            if (ArrayUtil.length(areas) > 1)
            {
                throw new NoPrimaryException();
            }

            if (ArrayUtil.isEmpty(areas))
            {
                _areaCtrl.insert(aEnt, pInParam.getWmsUserInfo());
                return;
            }
        }
        catch (LockTimeOutException e)
        {
            // 他端末で作業中
            throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
        }
        catch (DataExistsException e)
        {
            // 他端末で更新済み
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
    }

    /**
     * ゾーンマスタの登録を行ないます。<BR>
     * マスタが存在する場合は何もしません。<BR>
     * 
     * @param startParam 入力パラメータ
     * @throws NoPrimaryException 同一のゾーンNoが複数存在したときにスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     */
    protected void createZoneMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        // キャスト
        PCTRetrievalInParameter pInParam = (PCTRetrievalInParameter)startParam;
        Zone zEnt = new Zone();

        // システム管理区分
        zEnt.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        // エリアNo
        zEnt.setAreaNo(pInParam.getAreaNo());
        // 棚No指定のみ時、ゾーンマスタのチェック&登録処理は不要
        // 対象マスタが存在しない場合は、予定情報の登録は行わないため
        if (StringUtil.isBlank(pInParam.getWorkZoneNo()) && StringUtil.isBlank(pInParam.getZoneNo()))
        {
            return;
        }

        // 作業ゾーンNo
        if (!StringUtil.isBlank(pInParam.getWorkZoneNo()))
        {
            zEnt.setWorkZoneNo(pInParam.getWorkZoneNo());
        }
        else
        {
            zEnt.setWorkZoneNo(pInParam.getZoneNo());
        }
        // ゾーンNo
        if (!StringUtil.isBlank(pInParam.getZoneNo()))
        {
            zEnt.setZoneNo(pInParam.getZoneNo());
        }
        else
        {
            zEnt.setZoneNo(pInParam.getWorkZoneNo());
        }
        // 開始棚：NULL登録したいので、編集しない。
        // 終了棚：NULL登録したいので、編集しない。

        try
        {
            // read master with lock
            Zone[] zones = _zoneCtrl.lock(zEnt.getAreaNo(), zEnt.getWorkZoneNo(), zEnt.getZoneNo());
            if (ArrayUtil.length(zones) > 1)
            {
                throw new NoPrimaryException();
            }

            if (ArrayUtil.isEmpty(zones))
            {
                // insert if no such Item
                _zoneCtrl.insert(zEnt, pInParam.getWmsUserInfo());
                return;
            }
        }
        catch (LockTimeOutException e)
        {
            // 他端末で作業中
            throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
        }
        catch (DataExistsException e)
        {
            // 他端末で更新済み
            throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
        }
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
        return "$Id: PCTRetPlanOperator.java 3209 2009-03-02 06:34:19Z arai $";
    }
}
