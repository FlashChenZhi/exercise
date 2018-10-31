// $Id: RetrievalAllocateOperator.java 7734 2010-03-26 06:22:45Z ota $
package jp.co.daifuku.wms.retrieval.allocate;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.CommonException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.asrs.schedule.AsrsInParameter;
import jp.co.daifuku.wms.base.common.InParameter;
import jp.co.daifuku.wms.base.controller.WorkInfoController;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkInfoHandler;
import jp.co.daifuku.wms.base.entity.AllocatePriority;
import jp.co.daifuku.wms.base.entity.CarryInfo;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WorkInfo;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalInParameter;


/**
 * 引当を行うクラスです。<br>
 * このクラスでは、１出庫予定データごとに引当を行います。
 * 呼び出し元からは、荷主コード、商品コード、ロットNo.、引当数量、出庫予定情報を指定して引当を行います。<BR>
 * 在庫のロックのみ行います。その他の排他処理やロック処理は呼び出し元で行ってください。<BR>
 * また、トランザクションの確定も行いません。呼び出し元にて行ってください。<BR>
 *
 * @version $Revision: 7734 $, $Date: 2010-03-26 15:22:45 +0900 (金, 26 3 2010) $
 * @author  Y.Okamura
 * @author  Last commit: $Author: ota $
 */
public class RetrievalAllocateOperator
        extends AbstractAllocateOperator
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
    /**
     * 入出庫作業情報コントローラ
     */
    private WorkInfoController _wic;

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コンストラクタ
     * @param conn DBコネクション
     * @param pattern 引当パターンNo.
     * @param caller 呼び出し元クラス
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public RetrievalAllocateOperator(Connection conn, String pattern, Class caller) throws CommonException
    {
        super(conn, pattern, caller);
        _wic = new WorkInfoController(conn, caller);
    }
    
    /**
     * コンストラクタ
     * @param conn DBコネクション
     * @param pattern 引当パターンNo.
     * @param modeCheck 引当ステーションのモードチェック（出庫モード、中断中チェック）を行うかどうか
     * @param caller 呼び出し元クラス
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public RetrievalAllocateOperator(Connection conn, String pattern, boolean modeCheck, Class caller) throws CommonException
    {
        super(conn, pattern, modeCheck, caller);
        _wic = new WorkInfoController(conn, caller);
    }

    /**
     * 商品コード指定出庫用コンストラクタ
     * @param conn DBコネクション
     * @param inparam 
     * @param modeCheck 引当ステーションのモードチェック（出庫モード、中断中チェック）を行うかどうか
     * @param caller 呼び出し元クラス
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public RetrievalAllocateOperator(Connection conn, InParameter inparam, boolean modeCheck, Class caller) throws CommonException
    {
        super(conn, inparam, modeCheck, caller);
        _wic = new WorkInfoController(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 引当を行います。<br>
     * 在庫情報を引当、作業情報を作成し、出庫予定情報をスケジュール済に更新します。<br>
     * また、欠品が発生した場合は、出庫予定情報の更新は行いません。<br>
     * このメソッド内でトランザクションの確定は行いません。<br>
     * 呼び出し元にて、トランザクションの確定を行ってください。
     * 
     * @param ent 引当対象の出庫予定情報
     * @return 引当残数
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public int allocate(Entity ent)
            throws CommonException
    {
        // 欠品発生時の作業判定なしで引当
        return allocate(ent, "");
    }
    
    /**
     * 引当を行います。<br>
     * 在庫情報を引当、作業情報を作成し、出庫予定情報をスケジュール済に更新します。<br>
     * 欠品が発生かつ欠品発生時の作業判定が可能な作業は出庫するであった場合は、
     * 出庫予定情報を欠品予約に更新します。<br>
     * 上記以外の作業判定で欠品が発生した場合は、出庫予定情報の更新は行いません。<br>
     * このメソッド内でトランザクションの確定は行いません。<br>
     * 呼び出し元にて、トランザクションの確定を行ってください。
     * 
     * @param ent 引当対象の出庫予定情報
     * @param shortage_work_flag 欠品発生時の作業判定
     * @return 引当残数
     * @throws CommonException 処理エラーが発生した場合にスローされます。
     */
    public int allocate(Entity ent, String shortage_work_flag)
            throws CommonException
    {
        RetrievalPlan plan = (RetrievalPlan)ent;
        if (super.allocateStock(plan.getConsignorCode(), plan.getItemCode(), plan.getPlanLotNo(), 
                getAllocationQty(plan), plan, AllocatePriority.REPLENISHMENT_AREA_TYPE_NORMAL_AREA) == 0)
        {
            String status = "";
            try
            {
                status = _wic.getPlanStatus(plan.getPlanUkey());
            }
            catch (NotFoundException e)
            {
                // 作業情報が存在しない場合は「未作業」
                status = RetrievalPlan.STATUS_FLAG_UNSTART;
            }
            // update retrievalplan
            RetrievalPlanAlterKey retPlanAltKey = new RetrievalPlanAlterKey();
            retPlanAltKey.setPlanUkey(plan.getPlanUkey());
            retPlanAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_SCHEDULE);
            retPlanAltKey.updateStatusFlag(status);
            retPlanAltKey.updateLastUpdatePname(getCallerName());
            new RetrievalPlanHandler(getConnection()).modify(retPlanAltKey);

        }
        else
        {
            if (RetrievalInParameter.SHORTAGE_WORK_POSSIBLE_RETRIEVAL.equals(shortage_work_flag))
            {
                // 欠品発生時の作業判定が可能な作業は出庫する場合にのみ予定情報を更新
                String status = "";
                try
                {
                    status = _wic.getPlanStatus(plan.getPlanUkey());
                }
                catch (NotFoundException e)
                {
                    // 作業情報が存在しない場合は「未作業」
                    status = RetrievalPlan.STATUS_FLAG_UNSTART;
                }
                // update retrievalplan
                RetrievalPlanAlterKey retPlanAltKey = new RetrievalPlanAlterKey();
                retPlanAltKey.setPlanUkey(plan.getPlanUkey());
                retPlanAltKey.updateSchFlag(RetrievalPlan.SCH_FLAG_RESERVATION_SHORTAGE);
                retPlanAltKey.updateStatusFlag(status);
                retPlanAltKey.updateLastUpdatePname(getCallerName());
                new RetrievalPlanHandler(getConnection()).modify(retPlanAltKey);
            }
        }
        return getShortageQty();
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
    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.retrieval.allocate.AbstractAllocateOperator#createWork(jp.co.daifuku.wms.handler.Entity, jp.co.daifuku.wms.base.entity.Stock, int)
     */
    /**
     * {@inheritDoc}
     */
    @Override
    protected void createWork(Entity ent, Stock stk, int allocateQty)
            throws CommonException
    {
        RetrievalPlan plan = (RetrievalPlan)ent;
        WorkInfo wInfo = new WorkInfo();

        // 作業No.
        String jobNo = getSequence().nextWorkInfoJobNo();
        wInfo.setJobNo(jobNo);
        // 作成した作業No.を保持
        addCreateJobNoList(jobNo);
        // 荷主コード
        wInfo.setConsignorCode(stk.getConsignorCode());
        // 商品コード
        wInfo.setItemCode(stk.getItemCode());
        // 出荷先コード
        wInfo.setCustomerCode(plan.getCustomerCode());
        // 作業区分(出庫)
        wInfo.setJobType(WorkInfo.JOB_TYPE_RETRIEVAL);
        // 状態フラグ(未開始)
        wInfo.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
        // ハードウェア区分(未開始)
        wInfo.setHardwareType(WorkInfo.HARDWARE_TYPE_UNSTART);
        // 予定日
        wInfo.setPlanDay(plan.getPlanDay());
        // 出荷伝票No.
        wInfo.setShipTicketNo(plan.getShipTicketNo());
        // 出荷伝票行
        wInfo.setShipLineNo(plan.getShipLineNo());
        // 出荷枝番
        wInfo.setShipBranchNo(plan.getBranchNo());
        // ロット
        wInfo.setPlanLotNo(stk.getLotNo());
        // エリア
        wInfo.setPlanAreaNo(stk.getAreaNo());
        // 棚
        wInfo.setPlanLocationNo(stk.getLocationNo());
        // 予定数
        wInfo.setPlanQty(allocateQty);
        // オーダーNo.
        wInfo.setOrderNo(plan.getOrderNo());
        // バッチNo.
        wInfo.setBatchNo(plan.getBatchNo());
        // 予定一意キー
        wInfo.setPlanUkey(plan.getPlanUkey());
        // 在庫ID
        wInfo.setStockId(stk.getStockId());
        // 登録処理名
        wInfo.setRegistPname(getCallerName());
        // 最終更新処理名
        wInfo.setLastUpdatePname(getCallerName());

        // ASRSパッケージあり
        if (hasAsrsPack())
        {
            // 在庫がAS/RSエリアならばAS/RS処理を行う
            if (isAsrsArea(stk.getAreaNo()))
            {
                wInfo.setHardwareType(WorkInfo.HARDWARE_TYPE_ASRS); // ハードウェア区分(ASRS)

                String carryKey = "";
                // 搬送情報が存在しない場合
                if (!hasCarryInfo(stk.getPalletId()))
                {
                    CarryInfo ci = editCarryInfo(stk , null);
                    new CarryInfoHandler(getConnection()).create(ci);
                    
                    carryKey = ci.getCarryKey();            
                }
                else
                {
                    carryKey = getCarryKey(stk.getPalletId());
                }
                wInfo.setSystemConnKey(carryKey);// 搬送キー
            }
        }

        // 作業情報の作成
        new WorkInfoHandler(getConnection()).create(wInfo);
    }

    /* (non-Javadoc)
     * @see jp.co.daifuku.wms.retrieval.allocate.AbstractAllocateOperator#createWork(jp.co.daifuku.wms.asrs.schedule.AsrsInParameter, jp.co.daifuku.wms.base.entity.Stock, int)
     */
    /**
     * {@inheritDoc}
     */
    @Override
	protected void createWork(AsrsInParameter param, Stock stk, int allocateQty)
    throws CommonException
	{
		WorkInfo wInfo = new WorkInfo();
		
		// 作業No.
		String jobNo = getSequence().nextWorkInfoJobNo();
		wInfo.setJobNo(jobNo);
		// 荷主コード
		wInfo.setConsignorCode(stk.getConsignorCode());
		// 商品コード
		wInfo.setItemCode(stk.getItemCode());
		// 出荷先コード
		wInfo.setCustomerCode(param.getCustomerCode());
		// 作業区分(予定外出庫)
		wInfo.setJobType(WorkInfo.JOB_TYPE_NOPLAN_RETRIEVAL);
		// 状態フラグ(未開始)
		wInfo.setStatusFlag(WorkInfo.STATUS_FLAG_UNSTART);
		// ハードウェア区分(未開始)
		wInfo.setHardwareType(WorkInfo.HARDWARE_TYPE_UNSTART);
		// 予定日
		wInfo.setPlanDay(getWnSysCon().getWorkDay());
		// ロット
		wInfo.setPlanLotNo(stk.getLotNo());
		// エリア
		wInfo.setPlanAreaNo(stk.getAreaNo());
		// 棚
		wInfo.setPlanLocationNo(stk.getLocationNo());
		// 予定数
		wInfo.setPlanQty(allocateQty);
		// オーダーNo.
		wInfo.setOrderNo(param.getOrderNo());
		// バッチNo.
		wInfo.setBatchNo(param.getBatchNo());
		// 在庫ID
		wInfo.setStockId(stk.getStockId());
		// 登録処理名
		wInfo.setRegistPname(getCallerName());
		// 最終更新処理名
		wInfo.setLastUpdatePname(getCallerName());
		
		// ASRSパッケージあり
		if (hasAsrsPack())
		{
		    // 在庫がAS/RSエリアならばAS/RS処理を行う
		    if (isAsrsArea(stk.getAreaNo()))
		    {
		        wInfo.setHardwareType(WorkInfo.HARDWARE_TYPE_ASRS); // ハードウェア区分(ASRS)
		
		        String carryKey = "";
		        // 搬送情報が存在しない場合
		        if (!hasCarryInfo(stk.getPalletId()))
		        {
		            CarryInfo ci = editCarryInfo(stk ,param.getPriorityType());
                    
                    ci.setWorkType(CarryInfo.WORK_TYPE_NOPLAN_RETRIEVAL);
                    
		            new CarryInfoHandler(getConnection()).create(ci);
		            
		            carryKey = ci.getCarryKey();            
		        }
		        else
		        {
		            carryKey = getCarryKey(stk.getPalletId());
		        }
		        wInfo.setSystemConnKey(carryKey);// 搬送キー
		    }
		}
	
		// 作業情報の作成
		new WorkInfoHandler(getConnection()).create(wInfo);
	}

    /**
     * 今回の引当要求数を取得します。
     * 出庫予定情報の引当予定数 - 出庫予定情報に紐づく作業情報(削除以外)の予定数
     * 
     * @param plan 出庫予定情報
     * @return 今回引当数
     * @throws ReadWriteException DBエラーが発生した場合にスローされます。
     */
    protected int getAllocationQty(RetrievalPlan plan)
            throws ReadWriteException
    {
        return AbstractAllocateOperator.getAllocationQty(getConnection(), plan);
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
        return "$Id: RetrievalAllocateOperator.java 7734 2010-03-26 06:22:45Z ota $";
    }

}
