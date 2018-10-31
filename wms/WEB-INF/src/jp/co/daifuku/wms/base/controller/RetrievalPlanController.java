// $Id: RetrievalPlanController.java 5028 2009-09-18 04:31:29Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import static jp.co.daifuku.wms.base.entity.SystemDefine.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.LinkedHashSet;
import java.util.Set;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.WorkInfo;

/**
 * 出庫予定情報コントローラクラスです。
 *
 *
 * @version $Revision: 5028 $, $Date: 2009-09-18 13:31:29 +0900 (金, 18 9 2009) $
 * @author  073019
 * @author  Last commit: $Author: kishimoto $
 */
public class RetrievalPlanController
        extends AbstractController
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


    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public RetrievalPlanController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * 予定情報開始処理を行います。<BR>
     * パラメータの予定一意キーに該当する出庫予定情報を作業中に更新します。
     * 
     * @param planUkey 予定一意キー
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     */
    public void startPlan(String planUkey)
            throws ReadWriteException,
                NotFoundException
    {
        RetrievalPlanAlterKey key = new RetrievalPlanAlterKey();

        // 更新条件の設定
        key.setPlanUkey(planUkey); // 予定一意キー

        // 更新値の設定
        key.updateStatusFlag(STATUS_FLAG_NOWWORKING); // 状態フラグ(作業中)
        key.updateLastUpdatePname(getCallerName()); // 最終処理名

        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());

        handler.modify(key);
    }

    /**
     * 予定情報完了処理を行います
     * パラメータの予定一意キーに該当する予定情報の完了処理を行います。<BR>
     * パラメータの実績数、欠品数を予定情報に加算し、状態フラグを更新します。
     * 
     * @param planUkey 予定一意キー
     * @param resultQty 実績数
     * @param shortageQty 欠品数
     * @param workDay 作業日
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     * @throws InvalidDefineException 指定パラメータが異常な場合にスローされます。
     */
    public void completePlan(String planUkey, int resultQty, int shortageQty, String workDay)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        // 予定情報の状態フラグの取得
        WorkInfoController workInfoCtrl = new WorkInfoController(getConnection(), getCaller());
        String status = workInfoCtrl.getPlanStatus(planUkey);

        // 出庫予定情報更新キーの生成
        RetrievalPlanAlterKey updateKey = new RetrievalPlanAlterKey();

        updateKey.setPlanUkey(planUkey); // 予定一意キー(更新条件)

        updateKey.updateStatusFlag(status); // 状態フラグ
        updateKey.updateLastUpdatePname(getCallerName()); // 最終更新PG

        if (resultQty != 0)
        {
            // 実績数を足しこんで更新
            updateKey.setUpdateWithColumn(RetrievalPlan.RESULT_QTY, RetrievalPlan.RESULT_QTY, new BigDecimal(resultQty));// 実績数
        }
        if (shortageQty != 0)
        {
            // 欠品数を足しこんで更新
            updateKey.setUpdateWithColumn(RetrievalPlan.SHORTAGE_QTY, RetrievalPlan.SHORTAGE_QTY, new BigDecimal(
                    shortageQty)); // 欠品数
        }
        if ((resultQty + shortageQty) != 0)
        {
            updateKey.updateWorkDay(workDay);// 作業日
        }

        // 出庫予定情報ハンドラ
        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());
        // 更新処理
        handler.modify(updateKey);
    }

    /**
     * 予定情報キャンセル処理を行います。<BR>
     * パラメータの設定単位キーに該当する入出庫作業情報と紐づく出庫予定情報のキャンセル処理を行います。<BR>
     * 
     * @param workInfos 入出庫作業情報
     * <ol>
     * 参照される項目は以下のとおりです
     * <li>予定一意キー
     * </ol>
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     * @throws InvalidDefineException 指定パラメータが異常な場合にスローされます。
     */
    public void cancelPlan(WorkInfo[] workInfos)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {

        RetrievalPlanHandler retPlanHdlr = new RetrievalPlanHandler(getConnection());
        WorkInfoController wkInfoCtrl = new WorkInfoController(getConnection(), getCaller());

        // create unique list
        Set<String> planukeySet = new LinkedHashSet<String>();
        for (WorkInfo workInfo : workInfos)
        {
            planukeySet.add(workInfo.getPlanUkey());
        }
        // cancel for plan unique key
        for (String pUkey : planukeySet)
        {
            // update if plan status is NOT STARTED
            if (STATUS_FLAG_UNSTART.equals(wkInfoCtrl.getPlanStatus(pUkey)))
            {
                RetrievalPlanAlterKey alterKey = new RetrievalPlanAlterKey();

                alterKey.updateStatusFlag(STATUS_FLAG_UNSTART); // 未作業
                alterKey.updateLastUpdatePname(getCallerName());
                alterKey.setPlanUkey(pUkey);

                retPlanHdlr.modify(alterKey);
            }
        }
    }


    ////////////////////////////////////////////////
    // Auto Numbering from here
    ////////////////////////////////////////////////
    /**
     * 引数の出荷伝票No.と荷主コードから出庫予定情報を検索し、
     * 使用されている出荷伝票行No.を取得します。
     * 
     * @param slipNo 出荷伝票No.
     * @param consignorCode 荷主コード
     * @return 出庫予定情報(伝票行No.のみ)
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public RetrievalPlan[] getLineNos(String slipNo, String consignorCode)
            throws ReadWriteException
    {
        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey key = new RetrievalPlanSearchKey();

        key.setConsignorCode(consignorCode);
        key.setShipTicketNo(slipNo);
        key.setShipLineNoCollect();
        key.setStatusFlag(STATUS_FLAG_DELETE, "!=");
        key.setShipLineNoOrder(true);

        return (RetrievalPlan[])handler.find(key);
    }


    /**
     * 引数の出荷伝票No.と荷主コードから出庫予定情報を検索し、
     * 使用されているデータの中で最大の出荷伝票行No.を取得します。
     * 
     * @param slipNo 出荷伝票No.
     * @param consignorCode 荷主コード
     * @return 最大の出荷伝票行No.
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     */
    public int getMaxLineNo(String slipNo, String consignorCode)
            throws ReadWriteException
    {
        RetrievalPlanHandler handler = new RetrievalPlanHandler(getConnection());
        RetrievalPlanSearchKey key = new RetrievalPlanSearchKey();

        key.setConsignorCode(consignorCode);
        key.setShipTicketNo(slipNo);
        key.setStatusFlag(STATUS_FLAG_DELETE, "!=");
        key.setShipLineNoCollect("Max");

        return ((RetrievalPlan[])handler.find(key))[0].getShipLineNo();
    }

    ////////////////////////////////////////////////
    // Auto Numbering to here
    ////////////////////////////////////////////////


    //------------------------------------------------------------
    // accessor methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // package methods
    //------------------------------------------------------------


    //------------------------------------------------------------
    // protected methods
    //------------------------------------------------------------


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
        return "$Id: RetrievalPlanController.java 5028 2009-09-18 04:31:29Z kishimoto $";
    }
}
