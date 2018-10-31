// $Id: RetrievalPlanOperator.java 719 2008-10-27 08:39:55Z rnakai $
package jp.co.daifuku.wms.retrieval.operator;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

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
import jp.co.daifuku.wms.base.controller.ConsignorController;
import jp.co.daifuku.wms.base.controller.CustomerController;
import jp.co.daifuku.wms.base.controller.ItemController;
import jp.co.daifuku.wms.base.controller.WarenaviSystemController;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;


/**
 * 出庫予定操作用のオペレータクラスです<br>
 *
 *
 * @version $Revision: 719 $, $Date: 2008-10-27 17:39:55 +0900 (月, 27 10 2008) $
 * @author  020246
 * @author  Last commit: $Author: rnakai $
 */


public class RetrievalPlanOperator
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
     * 出庫予定ハンドラ
     */
    private RetrievalPlanHandler _RPHndl;

    /**
     * 出庫予定検索キー
     */
    private RetrievalPlanSearchKey _RPSKey;

    /**
     * 出庫予定更新キー
     */
    private RetrievalPlanAlterKey _RPAKey;

    /**
     * 入出庫作業情報ハンドラ
     */
    private WorkInfoHandler _WIHndl;

    /**
     * 入出庫作業情報更新キー
     */
    private WorkInfoAlterKey _WIAKey;

    /**
     * 荷主マスタコントローラ
     */
    private ConsignorController _ConsignorCtrl;

    /**
     * 出荷先マスタコントローラ
     */
    private CustomerController _CustomerCtrl;

    /**
     * 商品マスタコントローラ
     */
    private ItemController _ItemCtrl;

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
    public RetrievalPlanOperator(Connection conn, Class caller) throws ReadWriteException,
            ScheduleException
    {
        super(conn, caller);

        // DBハンドラ、コントローラ生成
        _WNSysCtrl = new WarenaviSystemController(conn, this.getClass());
        _RPHndl = new RetrievalPlanHandler(conn);
        _RPSKey = new RetrievalPlanSearchKey();
        _RPAKey = new RetrievalPlanAlterKey();
        _WIHndl = new WorkInfoHandler(conn);
        _WIAKey = new WorkInfoAlterKey();
        _ConsignorCtrl = new ConsignorController(conn, this.getClass());
        _CustomerCtrl = new CustomerController(conn, this.getClass());
        _ItemCtrl = new ItemController(conn, this.getClass());
        _SeqHndl = new WMSSequenceHandler(conn);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 出庫予定検索<BR>
     * RetrievalInParameterの検索条件に一致する出庫予定情報を検索し、戻り値で返します。<BR>
     * 一致したデータが無かった場合はnullを返します。<BR>
     * 
     * @param param 検索用パラメータ
     * @return Entity[] 出庫予定情報の検索結果
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public Entity[] findPlan(Parameter param)
            throws ReadWriteException
    {
        // キャスト
        RetrievalInParameter rParam = (RetrievalInParameter)param;

        // 検索条件セット
        _RPSKey.clear();
        _RPSKey.setConsignorCode(rParam.getConsignorCode());
        _RPSKey.setShipTicketNo(rParam.getTicketNo());
        _RPSKey.setShipLineNo(rParam.getLineNo());
        _RPSKey.setBranchNo(rParam.getBranchNo());
        _RPSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        Entity[] ent = _RPHndl.find(_RPSKey);
        if (ent == null || ent.length == 0)
        {
            return null;
        }
        else
        {
            return ent;
        }
    }

    /**
     * 出庫予定登録<BR>
     * startParamで指定されたパラメータの内容をもとに、出庫予定データ登録処理を行います。<BR>
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
        String planUKey = _SeqHndl.nextRetrievalPlanUkey();

        // 作成内容をセット(出庫予定)
        createRetrievalPlan(planUKey, registKind, _WNSysCtrl.hasStockPack(), startParam);

        // 在庫パッケージ未導入の場合
        if (!_WNSysCtrl.hasStockPack())
        {
            // 作業情報作成(入出庫作業情報)
            createWorkInfo(planUKey, startParam);
        }

        // マスタ管理パッケージ未導入の場合
        if (!_WNSysCtrl.hasMasterPack())
        {
            // 荷主マスタ自動登録
            createConsignorMaster(startParam);
            // 出荷先マスタ自動登録
            createCustomerMaster(startParam);
            // 商品マスタ自動登録
            createItemMaster(startParam);
        }

    }

    /**
     * 出庫予定削除<BR>
     * startParamで指定されたパラメータの内容をもとに、出庫予定データ削除処理を行います。<BR>
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
        RetrievalInParameter rInParam = (RetrievalInParameter)startParam;

        // 出庫予定削除条件セット
        _RPAKey.clear();
        if (!StringUtil.isBlank(rInParam.getPlanUKey()))
        {
            _RPAKey.setPlanUkey(rInParam.getPlanUKey());
        }
        else if (!StringUtil.isBlank(rInParam.getConsignorCode()))
        {
            _RPAKey.setConsignorCode(rInParam.getConsignorCode());
            _RPAKey.setShipTicketNo(rInParam.getTicketNo());
            _RPAKey.setShipLineNo(rInParam.getLineNo());
            _RPAKey.setBranchNo(rInParam.getBranchNo());
        }
        else
        {
            // 検索条件が不足している場合、OperatorExceptionをスローします。
            throw new ScheduleException();
        }
        _RPAKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);

        // 更新値をセット
        _RPAKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE);
        if (RetrievalPlan.REGIST_KIND_DATALOADER.equals(registKind))
        {
            _RPAKey.updateCancelFlag(RetrievalPlan.CANCEL_FLAG_HOST_CANCEL);
        }
        else
        {
            _RPAKey.updateCancelFlag(RetrievalPlan.CANCEL_FLAG_NORMAL);
        }
        _RPAKey.updateLastUpdatePname(getCallerName());

        // 出庫予定削除
        _RPHndl.modify(_RPAKey);

        // 在庫パッケージが未導入の場合、入出庫作業情報の更新を行います。
        if (!_WNSysCtrl.hasStockPack())
        {
            _WIAKey.clear();
            if (!StringUtil.isBlank(rInParam.getPlanUKey()))
            {
                _WIAKey.setPlanUkey(rInParam.getPlanUKey());
            }
            else if (!StringUtil.isBlank(rInParam.getConsignorCode()))
            {
                _WIAKey.setConsignorCode(rInParam.getConsignorCode());
                _WIAKey.setShipTicketNo(rInParam.getTicketNo());
                _WIAKey.setShipLineNo(rInParam.getLineNo());
                _WIAKey.setShipBranchNo(rInParam.getBranchNo());
            }
            else
            {
                // 検索条件が不足している場合、ScheduleExceptionをスローします。
                throw new ScheduleException();
            }
            _WIAKey.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
            _WIAKey.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);

            // 更新値をセット
            _WIAKey.updateStatusFlag(WorkInfo.STATUS_FLAG_DELETE);
            _WIAKey.updateLastUpdatePname(getCallerName());

            // 作業情報削除処理
            _WIHndl.modify(_WIAKey);
        }
    }

    /**
     * １オーダー内に複数の予定日が無いかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。予定日が複数存在した場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean isOrderInPlanDay(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        RetrievalInParameter rInParam = (RetrievalInParameter)checkParam;

        // 出庫予定検索条件セット
        _RPSKey.clear();
        _RPSKey.setOrderNo(rInParam.getOrderNo());
        _RPSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 出庫予定日で集約
        _RPSKey.setPlanDayGroup();
        _RPSKey.setPlanDayCollect();

        RetrievalPlan[] retrievalPlan = (RetrievalPlan[])_RPHndl.find(_RPSKey);

        // 取得した予定日が複数件該当した場合、falseを返します。
        if (retrievalPlan.length > 1)
        {
            return false;
        }
        if (retrievalPlan.length == 1)
        {
            // 取得した予定日と入力した予定日が異なる場合、falseを返します。
            if (!retrievalPlan[0].getPlanDay().equals(rInParam.getRetrievalPlanDay()))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * １オーダー内に複数の出荷先コードが無いかチェックを行います。<BR>
     * <BR>
     * @param checkParam 入力パラメータ
     * @return boolean データが正常に終了した場合、trueを返します。出荷先が複数存在した場合、falseを返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public boolean isOrderInCustomer(Parameter checkParam)
            throws ReadWriteException
    {
        // キャスト
        RetrievalInParameter rInParam = (RetrievalInParameter)checkParam;

        // 出庫予定検索条件セット
        _RPSKey.clear();
        _RPSKey.setOrderNo(rInParam.getOrderNo());
        _RPSKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "!=");

        // 出荷先コードで集約
        _RPSKey.setCustomerCodeGroup();
        _RPSKey.setCustomerCodeCollect();

        RetrievalPlan[] retrievalPlan = (RetrievalPlan[])_RPHndl.find(_RPSKey);

        // 取得した出荷先が複数件該当した場合、falseを返します。
        if (retrievalPlan.length > 1)
        {
            return false;
        }

        if (retrievalPlan.length == 1)
        {
            // 取得した出荷先と入力した出荷先が異なる場合、falseを返します。
            if (!retrievalPlan[0].getCustomerCode().equals(rInParam.getCustomerCode()))
            {
                return false;
            }
        }

        return true;
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
     * 出庫予定データを作成します
     * 
     * @param planUKey 予定一意キー
     * @param registKind 登録区分
     * @param stockPack 在庫パッケージ導入フラグ
     * @param startParam 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 登録データがすでに存在するときスローされます。
     * @throws ScheduleException スケジュール処理で発生した例外を報告します。
     */
    protected void createRetrievalPlan(String planUKey, String registKind, boolean stockPack, Parameter startParam)
            throws ReadWriteException,
                DataExistsException,
                ScheduleException
    {
        RetrievalPlan rpEnt = new RetrievalPlan();
        RetrievalInParameter rInParam = (RetrievalInParameter)startParam;

        // 作成内容をセット(出庫予定)
        rpEnt.setPlanUkey(planUKey);
        rpEnt.setLoadUnitKey(rInParam.getLoadUnitKey());
        rpEnt.setFileLineNo(rInParam.getRowNo());
        rpEnt.setConsignorCode(rInParam.getConsignorCode());
        rpEnt.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
        if (stockPack)
        {
            // 在庫パッケージ導入の場合、未スケジュール
            rpEnt.setSchFlag(SystemDefine.SCH_FLAG_NOT_SCHEDULE);
        }
        else
        {
            // 在庫パッケージ未導入の場合、スケジュール済み
            rpEnt.setSchFlag(SystemDefine.SCH_FLAG_SCHEDULE);
        }
        rpEnt.setCancelFlag(SystemDefine.CANCEL_FLAG_NORMAL);
        rpEnt.setPlanDay(rInParam.getRetrievalPlanDay());
        rpEnt.setCustomerCode(rInParam.getCustomerCode());
        rpEnt.setShipTicketNo(rInParam.getTicketNo());
        rpEnt.setShipLineNo(rInParam.getLineNo());
        rpEnt.setBranchNo(rInParam.getBranchNo());
        rpEnt.setBatchNo(rInParam.getBatchNo());
        rpEnt.setOrderNo(rInParam.getOrderNo());
        rpEnt.setPlanAreaNo(rInParam.getRetrievalAreaNo());
        rpEnt.setPlanLocationNo(rInParam.getRetrievalLocation());
        rpEnt.setItemCode(rInParam.getItemCode());
        rpEnt.setPlanLotNo(rInParam.getLotNo());
        rpEnt.setPlanQty(rInParam.getPlanQty());
        rpEnt.setResultQty(0);
        rpEnt.setShortageQty(0);
        rpEnt.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        rpEnt.setWorkDay("");
        rpEnt.setRegistKind(registKind);
        rpEnt.setRegistPname(getCallerName());
        rpEnt.setLastUpdatePname(getCallerName());

        // 出庫予定データ作成
        _RPHndl.create(rpEnt);
    }

    /**
     * 入出庫作業情報の登録を行います。<BR>
     * 在庫パッケージが導入されている場合、登録は行いません。<BR>
     * <BR>
     * @param planUKey 予定一意キー
     * @param startParam 入力パラメータ
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     * @throws ReadWriteException データベースアクセスエラーが発生したときにスローされます。
     * @throws DataExistsException 登録データがすでに存在するときスローされます。
     */
    protected void createWorkInfo(String planUKey, Parameter startParam)
            throws OperatorException,
                ReadWriteException,
                DataExistsException
    {
        RetrievalInParameter rInParam = (RetrievalInParameter)startParam;
        WorkInfo wiEnt = new WorkInfo();

        wiEnt.setJobNo(_SeqHndl.nextWorkInfoJobNo());
        wiEnt.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
        wiEnt.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
        wiEnt.setHardwareType(WorkInfo.HARDWARE_TYPE_UNSTART);
        wiEnt.setPlanUkey(planUKey);
        wiEnt.setPlanDay(rInParam.getRetrievalPlanDay());
        wiEnt.setConsignorCode(rInParam.getConsignorCode());
        wiEnt.setSupplierCode("");
        wiEnt.setReceiveTicketNo("");
        wiEnt.setReceiveLineNo(0);
        wiEnt.setCustomerCode(rInParam.getCustomerCode());
        wiEnt.setShipTicketNo(rInParam.getTicketNo());
        wiEnt.setShipLineNo(rInParam.getLineNo());
        wiEnt.setShipBranchNo(rInParam.getBranchNo());
        wiEnt.setBatchNo(rInParam.getBatchNo());
        wiEnt.setOrderNo(rInParam.getOrderNo());
        wiEnt.setPlanAreaNo(rInParam.getRetrievalAreaNo());
        wiEnt.setPlanLocationNo(rInParam.getRetrievalLocation());
        wiEnt.setItemCode(rInParam.getItemCode());
        wiEnt.setPlanLotNo(rInParam.getLotNo());
        wiEnt.setPlanQty(rInParam.getPlanQty());
        wiEnt.setResultQty(0);
        wiEnt.setShortageQty(0);
        wiEnt.setWorkSecond(0);
        wiEnt.setReasonType(SystemDefine.DEFAULT_REASON_TYPE);
        wiEnt.setRegistPname(getCallerName());
        wiEnt.setLastUpdatePname(getCallerName());

        // 登録処理
        _WIHndl.create(wiEnt);
    }

    /**
     * 荷主マスタの登録を行ないます。<BR>
     * マスタパッケージがある場合は何もしません。<BR>
     * 
     * @param startParam 入力パラメータ
     * @throws NoPrimaryException 同一の商品コードが複数存在したときにスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     */
    protected void createConsignorMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        RetrievalInParameter rInParam = (RetrievalInParameter)startParam;
        Consignor conEnt = new Consignor();
        conEnt.setConsignorCode(rInParam.getConsignorCode());
        //  荷主名称が無ければセットしない
        if (!StringUtil.isBlank(rInParam.getConsignorName()))
        {
            conEnt.setConsignorName(rInParam.getConsignorName());
        }

        try
        {
            _ConsignorCtrl.autoCreate(conEnt, rInParam.getWmsUserInfo());
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

    /**
     * 出荷先マスタの登録を行ないます。<BR>
     * マスタパッケージがある場合は何もしません。<BR>
     * 
     * @param startParam 入力パラメータ
     * @throws NoPrimaryException 同一の商品コードが複数存在したときにスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     */
    protected void createCustomerMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        RetrievalInParameter rInParam = (RetrievalInParameter)startParam;

        // 出荷先コードが未入力の場合、登録は行いません。
        if (StringUtil.isBlank(rInParam.getCustomerCode()))
        {
            return;
        }

        Customer cusEnt = new Customer();
        cusEnt.setConsignorCode(rInParam.getConsignorCode());
        cusEnt.setCustomerCode(rInParam.getCustomerCode());
        // 出荷先名称が無ければセットしない
        if (!StringUtil.isBlank(rInParam.getCustomerName()))
        {
            cusEnt.setCustomerName(rInParam.getCustomerName());
        }

        try
        {
            _CustomerCtrl.autoCreate(cusEnt, rInParam.getWmsUserInfo());
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

    /**
     * 商品マスタの登録を行ないます。<BR>
     * マスタパッケージがある場合は何もしません。<BR>
     * 
     * @param startParam 入力パラメータ
     * @throws NoPrimaryException 同一の商品コードが複数存在したときにスローされます。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws OperatorException オペレータで発生した全ての例外をスローします。
     */
    protected void createItemMaster(Parameter startParam)
            throws OperatorException,
                NoPrimaryException,
                ReadWriteException
    {
        RetrievalInParameter rInParam = (RetrievalInParameter)startParam;
        Item iEnt = new Item();
        iEnt.setManagementType(SystemDefine.MANAGEMENT_TYPE_USER);
        iEnt.setConsignorCode(rInParam.getConsignorCode());
        iEnt.setItemCode(rInParam.getItemCode());
        // 商品名称がnullまたは空白の場合、セットしない
        if (!StringUtil.isBlank(rInParam.getItemName()))
        {
            iEnt.setItemName(rInParam.getItemName());
        }
        // JANコードがnullまたは空白の場合、セットしない
        if (!StringUtil.isBlank(rInParam.getJanCode()))
        {
            iEnt.setJan(rInParam.getJanCode());
        }
        // ケースITFがnullまたは空白の場合、セットしない
        if (!StringUtil.isBlank(rInParam.getItf()))
        {
            iEnt.setItf(rInParam.getItf());
        }
        iEnt.setBundleItf("");
        // ケース入数が０でない場合、セットする
        if (rInParam.getEnteringQty() >= 0)
        {
            iEnt.setEnteringQty(rInParam.getEnteringQty());
        }
        iEnt.setBundleEnteringQty(0);
        iEnt.setLowerQty(0);
        iEnt.setUpperQty(0);

        try
        {
            _ItemCtrl.autoCreate(iEnt, rInParam.getWmsUserInfo());
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
        return "$Id: RetrievalPlanOperator.java 719 2008-10-27 08:39:55Z rnakai $";
    }
}
