// $Id: ReceivingPlanOperator.java 5029 2009-09-18 05:53:17Z shibamoto $
package jp.co.daifuku.wms.receiving.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.receiving.schedule.ReceivingPlanRegistSCHParams.*;

import java.sql.Connection;

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
import jp.co.daifuku.wms.base.controller.SupplierController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ReceivingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ReceivingWorkInfoHandler;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ReceivingPlan;
import jp.co.daifuku.wms.base.entity.ReceivingWorkInfo;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.receiving.schedule.ReceivingInParameter;
import jp.co.daifuku.wms.receiving.schedule.ReceivingPlanRegistSCHParams;


/**
 * 入荷予定操作用のオペレータクラスです<br>
 * A operator class for controlling receiving plans  
 *
 * @version $Revision: 5029 $, $Date: 2009-09-18 14:53:17 +0900 (金, 18 9 2009) $
 * @author  020246
 * @author  Last commit: $Author: shibamoto $
 */


public class ReceivingPlanOperator
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
     * WareNaviSystem Controller
     */
    private WarenaviSystemController _WNSysCtrl;

    /**
     * Receiving plan handler
     */
    private ReceivingPlanHandler _SPHndl;

    /**
     * Receiving plan search key
     */
    private ReceivingPlanSearchKey _SPSKey;

    /**
     * Receiving plan update key
     */
    private ReceivingPlanAlterKey _SPAKey;

    /**
     * 入荷作業情報ハンドラ
     * Receiving work information handler
     */
    private ReceivingWorkInfoHandler _WIHndl;

    /**
     * 入荷作業情報更新キー
     * Receiving worl information update key
     */
    private ReceivingWorkInfoAlterKey _WIAKey;

    /**
     * Master Item controller
     */
    private ItemController _ItemCtrl;

    /**
     * Master Supplier controller
     */
    private SupplierController _SuppCtrl;

    /**
     * Sequence handler
     */
    private WMSSequenceHandler _SeqHndl;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * データベースコネクションと呼び出し元クラスを指定して
     * インスタンスを生成します。
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知される例外です。
     */
    public ReceivingPlanOperator(Connection conn, Class caller) throws ReadWriteException,
            ScheduleException
    {
        super(conn, caller);

        // Generates DB handler and controller
        _WNSysCtrl = new WarenaviSystemController(conn, this.getClass());
        _SPHndl = new ReceivingPlanHandler(conn);
        _SPSKey = new ReceivingPlanSearchKey();
        _SPAKey = new ReceivingPlanAlterKey();
        _WIHndl = new ReceivingWorkInfoHandler(conn);
        _WIAKey = new ReceivingWorkInfoAlterKey();
        _ItemCtrl = new ItemController(conn, this.getClass());
        _SuppCtrl = new SupplierController(conn, this.getClass());
        _SeqHndl = new WMSSequenceHandler(conn);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 入荷予定情報を検索します。<BR>
     *
     * @param param 入力パラメータ
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Entity[] findPlan(Parameter param)
            throws ReadWriteException
    {
        // Casts
        ReceivingInParameter sParam = (ReceivingInParameter)param;

        // Sets search conditions
        _SPSKey.clear();
        _SPSKey.setConsignorCode(sParam.getConsignorCode());
        _SPSKey.setSupplierCode(sParam.getSupplierCode());
        _SPSKey.setPlanDay(sParam.getReceivingPlanDay());
        _SPSKey.setReceiveTicketNo(sParam.getTicketNo());
        _SPSKey.setReceiveLineNo(sParam.getTicketLineNo());
        _SPSKey.setStatusFlag(ReceivingPlan.STATUS_FLAG_DELETE, "!=");
        _SPSKey.setTcdcFlag(ReceivingPlan.TCDC_FLAG_DC);

        return _SPHndl.find(_SPSKey);
    }


    //////////////////////////////////////////////////////////////////////
    // DAC added this method from here
    //////////////////////////////////////////////////////////////////////
    /**
     * 入荷予定情報を検索します。<BR>
     *
     * @param sParam 入力パラメータ
     * @return 作成されたオブジェクトの配列
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Entity[] findPlan(ReceivingPlanRegistSCHParams sParam)
            throws ReadWriteException
    {

        // Sets search conditions
        _SPSKey.clear();
        _SPSKey.setConsignorCode(sParam.getString(CONSIGNOR_CODE));
        _SPSKey.setSupplierCode(sParam.getString(SUPPLIER_CODE));
        _SPSKey.setPlanDay(WmsFormatter.toParamDate(sParam.getDate(RECEIVING_PLAN_DATE)));
        _SPSKey.setReceiveTicketNo(sParam.getString(SLIP_NUMBER));
        _SPSKey.setReceiveLineNo(sParam.getInt(LINE_NO));
        _SPSKey.setStatusFlag(ReceivingPlan.STATUS_FLAG_DELETE, "!=");
        _SPSKey.setTcdcFlag(ReceivingPlan.TCDC_FLAG_DC);

        return _SPHndl.find(_SPSKey);
    }

    //////////////////////////////////////////////////////////////////////
    // DAC added this method to here
    //////////////////////////////////////////////////////////////////////


    /**
     * 入荷予定情報の登録を行います。<BR>
     *
     * @param registKind 登録区分
     * @param startParam 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 情報を登録しようとした際に、既に同じ情報が登録済みの場合に発生する例外です。
     * @throws NoPrimaryException 定義情報が異常な場合にスローされます。
     * @throws OperatorException 作業データの処理が正常に完了できない場合に通知される例外です。

     */
    public void createPlan(String registKind, Parameter startParam)
            throws ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                OperatorException
    {
        // Gets the plan unit key
        String planUKey = _SeqHndl.nextReceivingPlanUkey();

        // Sets made contents (receiving plan)
        createReceivingPlan(planUKey, registKind, startParam);

        // Makes work information
        createWorkInfo(planUKey, registKind, startParam);

        // Item Master auto registry
        createItemMaster(startParam);

        // Supplier Master auto registry
        createSupplierMaster(startParam);
    }

    /**
     * startParamで指定されたパラメータの内容をもとに、入荷予定データ削除処理を行います。<BR>
     * 削除失敗など異常が発生した場合は、例外を通知します。<BR>
     *
     * @param registKind 登録区分
     * @param startParam 入力パラメータ
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知される例外です。
     */
    public void deletePlan(String registKind, Parameter startParam)
            throws NotFoundException,
                ReadWriteException,
                ScheduleException
    {
        // Casts
        ReceivingInParameter sInParam = (ReceivingInParameter)startParam;

        // Sets Receiving plan deletion conditions
        _SPAKey.clear();
        if (sInParam.getPlanUKey() != null)
        {
            _SPAKey.setPlanUkey(sInParam.getPlanUKey());
        }
        if (sInParam.getConsignorCode() != null)
        {
            _SPAKey.setConsignorCode(sInParam.getConsignorCode());
        }
        if (sInParam.getSupplierCode() != null)
        {
            _SPAKey.setSupplierCode(sInParam.getSupplierCode());
        }
        if (sInParam.getReceivingPlanDay() != null)
        {
            _SPAKey.setPlanDay(sInParam.getReceivingPlanDay());
        }
        if (sInParam.getTicketNo() != null)
        {
            _SPAKey.setReceiveTicketNo(sInParam.getTicketNo());
        }
        _SPAKey.setReceiveLineNo(sInParam.getTicketLineNo());
        _SPAKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        _SPAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _SPAKey.updateTcdcFlag(SystemDefine.TCDC_FLAG_DC);
        if (SystemDefine.REGIST_KIND_DATALOADER.equals(registKind))
        {
            _SPAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_HOST_CANCEL);
        }
        else
        {
            _SPAKey.updateCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        }
        _SPAKey.updateLastUpdatePname(getCallerName());

        // Receiving plan deletion
        _SPHndl.modify(_SPAKey);

        // Sets receiving work information deletion conditions
        _WIAKey.clear();
        if (sInParam.getPlanUKey() != null)
        {
            _WIAKey.setPlanUkey(sInParam.getPlanUKey());
        }
        if (sInParam.getConsignorCode() != null)
        {
            _WIAKey.setConsignorCode(sInParam.getConsignorCode());
        }
        if (sInParam.getSupplierCode() != null)
        {
            _WIAKey.setSupplierCode(sInParam.getSupplierCode());
        }
        if (sInParam.getReceivingPlanDay() != null)
        {
            _WIAKey.setPlanDay(sInParam.getReceivingPlanDay());
        }
        if (sInParam.getTicketNo() != null)
        {
            _WIAKey.setReceiveTicketNo(sInParam.getTicketNo());
        }
        _WIAKey.setReceiveLineNo(sInParam.getTicketLineNo());
        _WIAKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);

        _WIAKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
        _WIAKey.updateLastUpdatePname(getCallerName());

        // Deletes receiving work information
        _WIHndl.modify(_WIAKey);
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
     * Creates receiving plan data
     *
     * @param planUKey Plan Unit key
     * @param registKind Registry Type
     * @param startParam Input parameters
     * @throws ReadWriteException Thrown when DB access errors occur.
     * @throws DataExistsException Thrown if the given plan is already registered.
     */
    protected void createReceivingPlan(String planUKey, String registKind, Parameter startParam)
            throws ReadWriteException,
                DataExistsException
    {
        ReceivingPlan spEnt = new ReceivingPlan();
        ReceivingInParameter sInParam = (ReceivingInParameter)startParam;

        // Sets made contents (receiving plan)
        spEnt.setPlanUkey(planUKey);
        spEnt.setLoadUnitKey(sInParam.getLoadUnitKey());
        spEnt.setFileLineNo(sInParam.getRowNo());
        spEnt.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        spEnt.setTcdcFlag(SystemDefine.TCDC_FLAG_DC);
        spEnt.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        spEnt.setPlanDay(sInParam.getReceivingPlanDay());
        spEnt.setConsignorCode(sInParam.getConsignorCode());
        spEnt.setSupplierCode(sInParam.getSupplierCode());
        spEnt.setReceiveTicketNo(sInParam.getTicketNo());
        spEnt.setReceiveLineNo(sInParam.getTicketLineNo());
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

        // Creates receiving plan data
        _SPHndl.create(spEnt);
    }

    /**
     * Creates receiving work information
     *
     * @param planUKey Plan Unit key
     * @param registKind Registry Type
     * @param startParam Input parameters
     * @throws ReadWriteException Thrown when DB access errors occur
     * @throws DataExistsException Thrown if the given plan is already registered
     */
    protected void createWorkInfo(String planUKey, String registKind, Parameter startParam)
            throws ReadWriteException,
                DataExistsException
    {
        ReceivingWorkInfo wIEnt = new ReceivingWorkInfo();
        ReceivingInParameter sInParam = (ReceivingInParameter)startParam;

        // Sets created contents (work information)
        wIEnt.setJobNo(_SeqHndl.nextReceivingJobNo());
        wIEnt.setSettingUnitKey("");
        wIEnt.setCollectJobNo("");
        wIEnt.setJobType(SystemDefine.JOB_TYPE_RECEIVING);
        wIEnt.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        wIEnt.setTcdcFlag(SystemDefine.TCDC_FLAG_DC);
        wIEnt.setHardwareType(SystemDefine.HARDWARE_TYPE_UNSTART);
        wIEnt.setPlanUkey(planUKey);
        wIEnt.setPlanDay(sInParam.getReceivingPlanDay());
        wIEnt.setConsignorCode(sInParam.getConsignorCode());
        wIEnt.setSupplierCode(sInParam.getSupplierCode());
        wIEnt.setReceiveTicketNo(sInParam.getTicketNo());
        wIEnt.setReceiveLineNo(sInParam.getTicketLineNo());

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
        wIEnt.setResultLotNo("");
        wIEnt.setWorkDay("");
        wIEnt.setUserId("");
        wIEnt.setTerminalNo("");
        wIEnt.setWorkSecond(0);
        wIEnt.setRegistPname(getCallerName());
        wIEnt.setLastUpdatePname(getCallerName());

        // Makes work information
        _WIHndl.create(wIEnt);
    }

    /**
     * 商品マスタの登録を行ないます。<BR>
     * マスタパッケージがない場合は何もしません。<BR>
     * 
     * Registers Item Master. Does nothing if there is no Master package.<BR>
     *
     * @param startParam Input parameters
     * @throws OperatorException Throws all exceptions that occur in this operation.
     * @throws NoPrimaryException Thrown when there exist duplicated same Item Code.
     * @throws ReadWriteException Thrown when DB access errors occur
     */
    protected void createItemMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        if (!_WNSysCtrl.hasMasterPack())
        {
            ReceivingInParameter sInParam = (ReceivingInParameter)startParam;
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
                // Working at another terminal
                throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
            }
            catch (NotFoundException e)
            {
                // Completed thi update at another terminal
                throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            }
            catch (DataExistsException e)
            {
                // Completed thi update at another terminal
                throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            }

        }
    }

    /**
     * supplier masterの登録を行ないます。<BR>
     * マスタパッケージがない場合は何もしません。<BR>
     * 
     * Registers Supplier Master. Does nothing if there is no Master package.<BR>
     *
     * @param startParam Input parameters
     * @throws OperatorException Throws all exceptions that occur in this operation.
     * @throws NoPrimaryException Thrown when there exist duplicated same Item Code.
     * @throws ReadWriteException Thrown when DB access errors occur
     */
    protected void createSupplierMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        if (!_WNSysCtrl.hasMasterPack())
        {
            ReceivingInParameter sInParam = (ReceivingInParameter)startParam;
            Supplier suppEnt = new Supplier();
            suppEnt.setConsignorCode(sInParam.getConsignorCode());
            suppEnt.setSupplierCode(sInParam.getSupplierCode());
            if (!StringUtil.isBlank(sInParam.getSupplierName()))
            {
                suppEnt.setSupplierName(sInParam.getSupplierName());
            }

            try
            {
                _SuppCtrl.autoCreate(suppEnt, sInParam.getWmsUserInfo());
            }
            catch (LockTimeOutException e)
            {
                // Working at another terminal
                throw new OperatorException(OperatorException.ERR_WORKING_INPROGRESS);
            }
            catch (NotFoundException e)
            {
                // Completed this update at another terminal
                throw new OperatorException(OperatorException.ERR_ALREADY_UPDATED);
            }
            catch (DataExistsException e)
            {
                // Completed this update at another terminal
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
     * Returns a revision of this class.
     * @return Revision String
     */
    public static String getVersion()
    {
        return "$Id: ReceivingPlanOperator.java 5029 2009-09-18 05:53:17Z shibamoto $";
    }
}
