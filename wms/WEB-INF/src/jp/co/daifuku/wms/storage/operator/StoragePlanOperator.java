// $Id: StoragePlanOperator.java 3208 2009-03-02 05:42:52Z arai $
package jp.co.daifuku.wms.storage.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import static jp.co.daifuku.wms.storage.schedule.StoragePlanRegistSCHParams.*;


import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.AbstractOperator;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.AreaSearchKey;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.FixedLocateInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.entity.FixedLocateInfo;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.storage.schedule.StorageInParameter;
import jp.co.daifuku.wms.storage.schedule.StoragePlanRegistSCHParams;


/**
 * 入庫予定操作用のオペレータクラスです<br>
 *
 *
 * @version $Revision: 3208 $, $Date: 2009-03-02 14:42:52 +0900 (月, 02 3 2009) $
 * @author  020246
 * @author  Last commit: $Author: arai $
 */


public class StoragePlanOperator
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
    private WarenaviSystemController _WNSysCtrl;

    /**
     * 入庫予定ハンドラ
     */
    private StoragePlanHandler _SPHndl;

    /**
     * 入庫予定検索キー
     */
    private StoragePlanSearchKey _SPSKey;

    /**
     * 入庫予定更新キー
     */
    private StoragePlanAlterKey _SPAKey;

    /**
     * 入出庫作業情報ハンドラ
     */
    private WorkInfoHandler _WIHndl;

    /**
     * 入出庫作業情報更新キー
     */
    private WorkInfoAlterKey _WIAKey;

    /**
     * 商品マスタコントローラ
     */
    private ItemController _ItemCtrl;

    /**
     * 商品固定棚情報ハンドラ
     */
    private FixedLocateInfoHandler _FixLocHndl;

    /**
     * 商品固定棚情報検索キー
     */
    private FixedLocateInfoSearchKey _FixLocSKey;

    /**
     * エリアマスタ検索キー
     */
    private AreaSearchKey _AreaSKey;

    /**
     * シーケンスハンドラ
     */
    private WMSSequenceHandler _SeqHndl;

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
    public StoragePlanOperator(Connection conn, Class caller) throws ReadWriteException,
            ScheduleException
    {
        super(conn, caller);

        // DBハンドラ、コントローラ生成
        _WNSysCtrl = new WarenaviSystemController(conn, this.getClass());
        _SPHndl = new StoragePlanHandler(conn);
        _SPSKey = new StoragePlanSearchKey();
        _SPAKey = new StoragePlanAlterKey();
        _WIHndl = new WorkInfoHandler(conn);
        _WIAKey = new WorkInfoAlterKey();
        _ItemCtrl = new ItemController(conn, this.getClass());
        _FixLocHndl = new FixedLocateInfoHandler(conn);
        _FixLocSKey = new FixedLocateInfoSearchKey();
        _AreaSKey = new AreaSearchKey();
        _SeqHndl = new WMSSequenceHandler(conn);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 入庫予定検索<BR>
     * StorageInParameterの検索条件に一致する入庫予定情報を検索し、戻り値で返します。<BR>
     * 一致したデータが無かった場合はnullを返します。<BR>
     *
     * @param param 入力パラメータ
     * @return Entity[] 入庫予定情報の検索結果
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Entity[] findPlan(Parameter param)
            throws ReadWriteException
    {
        // キャスト
        StorageInParameter sParam = (StorageInParameter)param;

        // 検索条件セット
        _SPSKey.clear();
        _SPSKey.setConsignorCode(sParam.getConsignorCode());
        _SPSKey.setPlanDay(sParam.getStoragePlanDay());
        _SPSKey.setReceiveTicketNo(sParam.getTicketNo());
        _SPSKey.setReceiveLineNo(sParam.getLineNo());
        _SPSKey.setBranchNo(sParam.getBranchNo());
        _SPSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        return _SPHndl.find(_SPSKey);
    }

    
    //////////////////////////////////////////////////////////////////////
    // DAC added this method from here
    //////////////////////////////////////////////////////////////////////
    /**
     * 入庫予定検索<BR>
     * StorageInParameterの検索条件に一致する入庫予定情報を検索し、戻り値で返します。<BR>
     * 一致したデータが無かった場合はnullを返します。<BR>
     *
     * @param param 入力パラメータ
     * @return Entity[] 入庫予定情報の検索結果
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Entity[] findPlan(StoragePlanRegistSCHParams param)
            throws ReadWriteException
    {

        // 検索条件セット
        _SPSKey.clear();
        _SPSKey.setConsignorCode(param.getString(CONSIGNOR_CODE));
        _SPSKey.setPlanDay(param.getString(STORAGE_PLAN_DATE));
        _SPSKey.setReceiveTicketNo(param.getString(SLIP_NUMBER));
        _SPSKey.setReceiveLineNo(param.getInt(LINE_NO));
        _SPSKey.setBranchNo(param.getInt(BRANCH_NO));
        _SPSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        return _SPHndl.find(_SPSKey);
    }
    //////////////////////////////////////////////////////////////////////
    // DAC added this method to here
    //////////////////////////////////////////////////////////////////////

    
    
    /**
     * 入庫予定登録<BR>
     * startParamで指定されたパラメータの内容をもとに、入庫予定データ登録処理を行います。<BR>
     * 登録失敗など異常が発生した場合は、例外を通知します。<BR>
     *
     * @param registKind 登録区分
     * @param startParam 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 登録データがすでに存在するときスローされます。
     * @throws NoPrimaryException 一意項目が複数存在するときスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     */
    public void createPlan(String registKind, Parameter startParam)
            throws ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                OperatorException
    {
        // 入庫棚補完
        mergeFixedLocation(startParam);

        // 予定一意キー採番
        String planUKey = _SeqHndl.nextStoragePlanUkey();

        // 作成内容をセット(入庫予定)
        createStoragePlan(planUKey, registKind, startParam);

        // 作業情報作成
        createWorkInfo(planUKey, registKind, startParam);

        // 商品マスタ自動登録
        createItemMaster(startParam);

    }

    /**
     * 入庫予定削除<BR>
     * startParamで指定されたパラメータの内容をもとに、入庫予定データ削除処理を行います。<BR>
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
        StorageInParameter sInParam = (StorageInParameter)startParam;

        // 入庫予定削除条件セット
        _SPAKey.clear();
        if (sInParam.getPlanUKey() != null)
        {
            _SPAKey.setPlanUkey(sInParam.getPlanUKey());
        }
        if (sInParam.getConsignorCode() != null)
        {
            _SPAKey.setConsignorCode(sInParam.getConsignorCode());
        }
        if (sInParam.getStoragePlanDay() != null)
        {
            _SPAKey.setPlanDay(sInParam.getStoragePlanDay());
        }
        if (sInParam.getTicketNo() != null)
        {
            _SPAKey.setReceiveTicketNo(sInParam.getTicketNo());
        }
        _SPAKey.setReceiveLineNo(sInParam.getLineNo());
        _SPAKey.setBranchNo(sInParam.getBranchNo());
        _SPAKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        _SPAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        if (SystemDefine.REGIST_KIND_DATALOADER.equals(registKind))
        {
            _SPAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_HOST_CANCEL);
        }
        else
        {
            _SPAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        }
        _SPAKey.updateLastUpdatePname(getCallerName());

        // 入庫予定削除
        _SPHndl.modify(_SPAKey);

        // 入出庫作業情報削除条件セット
        _WIAKey.clear();
        if (sInParam.getPlanUKey() != null)
        {
            _WIAKey.setPlanUkey(sInParam.getPlanUKey());
        }
        if (sInParam.getConsignorCode() != null)
        {
            _WIAKey.setConsignorCode(sInParam.getConsignorCode());
        }
        if (sInParam.getStoragePlanDay() != null)
        {
            _WIAKey.setPlanDay(sInParam.getStoragePlanDay());
        }
        if (sInParam.getTicketNo() != null)
        {
            _WIAKey.setReceiveTicketNo(sInParam.getTicketNo());
        }
        _WIAKey.setReceiveLineNo(sInParam.getLineNo());
        _WIAKey.setReceiveBranchNo(sInParam.getBranchNo());
        _WIAKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        _WIAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _WIAKey.updateLastUpdatePname(getCallerName());

        // 入出庫作業情報削除
        _WIHndl.modify(_WIAKey);
    }


    /**
     * 商品固定棚情報から、エリアと棚の補完を行ないます。<BR>
     *
     * @param param 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public void mergeFixedLocation(Parameter param)
            throws ReadWriteException
    {
        // 在庫管理あり時のみエリア、棚の補完を行う
        if (_WNSysCtrl.hasStockPack())
        {
            StorageInParameter sInParam = (StorageInParameter)param;
            // エリアと棚の両方に値が入っていた場合は補完は行わない
            if (StringUtil.isBlank(sInParam.getStorageAreaNo()) || StringUtil.isBlank(sInParam.getStorageLocation()))
            {
                _FixLocSKey.clear();
                // エリアに値が入っていた場合、固定棚管理の条件を追加
                if (!StringUtil.isBlank(sInParam.getStorageAreaNo()))
                {
                    _AreaSKey.clear();
                    // 取得項目
                    _AreaSKey.setAreaNoCollect();

                    // 検索条件のセット
                    _AreaSKey.setAreaNo(sInParam.getStorageAreaNo());
                    _AreaSKey.setLocationType(SystemDefine.LOCATION_TYPE_FIXED);

                    _FixLocSKey.setKey(FixedLocateInfo.AREA_NO, _AreaSKey);
                }

                // 取得項目
                _FixLocSKey.setAreaNoCollect();
                _FixLocSKey.setLocationNoCollect();

                // 検索条件のセット
                _FixLocSKey.setConsignorCode(sInParam.getConsignorCode());
                _FixLocSKey.setItemCode(sInParam.getItemCode());

                // ソート順のセット
                _FixLocSKey.setLastUpdateDateOrder(false);

                // 商品固定棚情報の検索
                FixedLocateInfo[] fixLoc = (FixedLocateInfo[])_FixLocHndl.find(_FixLocSKey);

                if (fixLoc.length > 0)
                {
                    // エリア
                    if (!StringUtil.isBlank(fixLoc[0].getAreaNo()))
                    {
                        sInParam.setStorageAreaNo(fixLoc[0].getAreaNo());
                    }

                    // 棚
                    if (!StringUtil.isBlank(fixLoc[0].getLocationNo()))
                    {
                        sInParam.setStorageLocation(fixLoc[0].getLocationNo());
                    }
                }
            }
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
     * 入庫予定データを作成します
     *
     * @param planUKey 予定一意キー
     * @param registKind 登録区分
     * @param startParam 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 登録データがすでに存在するときスローされます。
     */
    protected void createStoragePlan(String planUKey, String registKind, Parameter startParam)
            throws ReadWriteException,
                DataExistsException
    {
        StoragePlan spEnt = new StoragePlan();
        StorageInParameter sInParam = (StorageInParameter)startParam;

        // 作成内容をセット(入庫予定)
        spEnt.setPlanUkey(planUKey);
        spEnt.setLoadUnitKey(sInParam.getLoadUnitKey());
        spEnt.setFileLineNo(sInParam.getRowNo());
        spEnt.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        spEnt.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        spEnt.setPlanDay(sInParam.getStoragePlanDay());
        spEnt.setConsignorCode(sInParam.getConsignorCode());
        spEnt.setSupplierCode("");
        spEnt.setReceiveTicketNo(sInParam.getTicketNo());
        spEnt.setReceiveLineNo(sInParam.getLineNo());
        spEnt.setBranchNo(sInParam.getBranchNo());
        if (sInParam.getStorageAreaNo() != null)
        {
            spEnt.setPlanAreaNo(sInParam.getStorageAreaNo());
        }
        else
        {
            spEnt.setPlanAreaNo("");
        }

        if (sInParam.getStorageLocation() != null)
        {
            spEnt.setPlanLocationNo(sInParam.getStorageLocation());
        }
        else
        {
            spEnt.setPlanLocationNo("");
        }

        spEnt.setItemCode(sInParam.getItemCode());

        if (sInParam.getLotNo() != null)
        {
            spEnt.setPlanLotNo(sInParam.getLotNo());
        }
        else
        {
            spEnt.setPlanLotNo("");
        }

        spEnt.setPlanQty(sInParam.getPlanQty());
        spEnt.setResultQty(0);
        spEnt.setShortageQty(0);
        spEnt.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        spEnt.setWorkDay("");
        spEnt.setRegistKind(registKind);
        spEnt.setRegistPname(getCallerName());
        spEnt.setLastUpdatePname(getCallerName());

        // 入庫予定データ作成
        _SPHndl.create(spEnt);
    }

    /**
     * 入出庫作業情報を作成します。
     *
     * @param planUKey 予定一意キー
     * @param registKind 登録区分
     * @param startParam 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 登録データがすでに存在するときスローされます。
     */
    protected void createWorkInfo(String planUKey, String registKind, Parameter startParam)
            throws ReadWriteException,
                DataExistsException
    {
        WorkInfo wIEnt = new WorkInfo();
        StorageInParameter sInParam = (StorageInParameter)startParam;

        // 作成内容をセット(作業情報)
        wIEnt.setJobNo(_SeqHndl.nextWorkInfoJobNo());
        wIEnt.setSettingUnitKey("");
        wIEnt.setCollectJobNo("");
        wIEnt.setJobType(SystemDefine.JOB_TYPE_STORAGE);
        wIEnt.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        wIEnt.setHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);
        wIEnt.setPlanUkey(planUKey);
        wIEnt.setStockId("");
        wIEnt.setSystemConnKey("");
        wIEnt.setPlanDay(sInParam.getStoragePlanDay());
        wIEnt.setConsignorCode(sInParam.getConsignorCode());
        wIEnt.setSupplierCode("");
        wIEnt.setReceiveTicketNo(sInParam.getTicketNo());
        wIEnt.setReceiveLineNo(sInParam.getLineNo());
        wIEnt.setReceiveBranchNo(sInParam.getBranchNo());
        wIEnt.setCustomerCode("");
        wIEnt.setShipTicketNo("");
        wIEnt.setShipLineNo(0);
        wIEnt.setShipBranchNo(0);
        wIEnt.setBatchNo("");
        wIEnt.setOrderNo("");
        if (sInParam.getStorageAreaNo() != null)
        {
            wIEnt.setPlanAreaNo(sInParam.getStorageAreaNo());
        }
        else
        {
            wIEnt.setPlanAreaNo("");
        }

        if (sInParam.getStorageLocation() != null)
        {
            wIEnt.setPlanLocationNo(sInParam.getStorageLocation());
        }
        else
        {
            wIEnt.setPlanLocationNo("");
        }

        wIEnt.setItemCode(sInParam.getItemCode());

        if (sInParam.getLotNo() != null)
        {
            wIEnt.setPlanLotNo(sInParam.getLotNo());
        }
        else
        {
            wIEnt.setPlanLotNo("");
        }

        wIEnt.setPlanQty(sInParam.getPlanQty());
        wIEnt.setResultQty(0);
        wIEnt.setShortageQty(0);
        wIEnt.setResultAreaNo("");
        wIEnt.setResultLocationNo("");
        wIEnt.setResultLotNo("");
        // DFKLOOK: DAC commented out from here
        //wIEnt.setReasonType(SystemDefine.DEFAULT_REASON_TYPE);
        // DFKLOOK: DAC commented out to here
        wIEnt.setWorkDay("");
        wIEnt.setUserId("");
        wIEnt.setTerminalNo("");
        wIEnt.setWorkSecond(0);
        wIEnt.setRegistPname(getCallerName());
        wIEnt.setLastUpdatePname(getCallerName());

        // 作業情報作成
        _WIHndl.create(wIEnt);
    }

    /**
     * 商品マスタの登録を行ないます。<BR>
     * マスタパッケージがない場合は何もしません。<BR>
     *
     * @param startParam 入力パラメータ
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     * @throws NoPrimaryException 同一の商品コードが複数存在したときにスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    protected void createItemMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        if (!_WNSysCtrl.hasMasterPack())
        {
            StorageInParameter sInParam = (StorageInParameter)startParam;
            Item iEnt = new Item();
            iEnt.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
            iEnt.setConsignorCode(sInParam.getConsignorCode());
            iEnt.setItemCode(sInParam.getItemCode());
            if (!StringUtil.isBlank(sInParam.getItemName()))
            {
                iEnt.setItemName(sInParam.getItemName());
            }
            if (!StringUtil.isBlank(sInParam.getJanCode()))
            {
                iEnt.setJan(sInParam.getJanCode());
            }
            if (!StringUtil.isBlank(sInParam.getItf()))
            {
                iEnt.setItf(sInParam.getItf());
            }
            iEnt.setBundleItf("");
            if (sInParam.getEnteringQty() > 0)
            {
                iEnt.setEnteringQty(sInParam.getEnteringQty());
            }
            iEnt.setBundleEnteringQty(0);
            iEnt.setLowerQty(0);
            iEnt.setUpperQty(0);

            try
            {
                _ItemCtrl.autoCreate(iEnt, sInParam.getWmsUserInfo());
            }
            catch (LockTimeOutException e)
            {
                // 他端末で作業中
                throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
            }
            catch (NotFoundException e)
            {
                // 他端末で更新済み
                throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            }
            catch (DataExistsException e)
            {
                // 他端末で更新済み
                throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            }

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
        return "$Id: StoragePlanOperator.java 3208 2009-03-02 05:42:52Z arai $";
    }
}
