// $Id: PCTRetPlanController.java 3755 2009-03-23 09:37:53Z ose $
package jp.co.daifuku.pcart.retrieval.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.math.BigDecimal;
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.controller.AbstractController;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;


/**
 * PCT出庫予定情報コントローラクラスです。<br>
 *
 *
 * @version $Revision: 3755 $, $Date: 2009-03-23 18:37:53 +0900 (月, 23 3 2009) $
 * @author  etakeda
 * @author  Last commit: $Author: ose $
 */


public class PCTRetPlanController
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
    public PCTRetPlanController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    /**
     * PCT予定情報完了<BR>
     * <BR>
     * パラメータの予定一意キーに該当する予定情報の完了処理を行います。<BR>
     * パラメータの実績数、欠品数を予定情報に加算し、状態フラグを更新します。<BR>
     * 
     * @param planUkey 予定一意キー
     * @param resultQty 実績数
     * @param shortageQty 欠品数
     * @param statusFlag 状態フラグ
     * @param workDay 作業日
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     * @throws InvalidDefineException 指定パラメータが異常な場合にスローされます。
     */
    public void completePlan(String planUkey, int resultQty, int shortageQty, String statusFlag, String workDay)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        // 出庫予定情報更新キーの生成
        PCTRetPlanAlterKey updateKey = new PCTRetPlanAlterKey();

        updateKey.setPlanUkey(planUkey); // 予定一意キー(更新条件)

        // 実績数     
        updateKey.updateResultQty(resultQty);
        // 欠品数 
        updateKey.updateShortageQty(shortageQty);
        // 作業日
        updateKey.updateWorkDay(workDay);
        // 最終更新処理名
        updateKey.updateLastUpdatePname(getCallerName());

        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());
        // 更新処理
        handler.modify(updateKey);
    }

    /**
     * PCT予定情報完了(一括)<BR>
     * <BR>
     * パラメータの予定一意キーに該当する予定情報の完了処理を行います。<BR>
     * パラメータの実績数、欠品数を予定情報に加算し、状態フラグを更新します。<BR>
     * 
     * 作業メンテナンス一括更新内容
     * 実績数
     * 欠品数
     * 状態フラグ
     * 作業日
     * 最終更新日時：システム日時
     * 最終更新処理名： PCTRetAllResultMntSCH                          
     * 
     * @param workInfo 作業データ
     * @param info 画面情報で取得した作業データ
     * @param inputParam 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     * @throws InvalidDefineException 指定パラメータが異常な場合にスローされます。
     */
    public void completeAllPlan(PCTRetWorkInfo workInfo, PCTRetWorkInfo info, PCTRetrievalInParameter inputParam)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        // 出庫予定情報更新キーの生成
        PCTRetPlanAlterKey updateKey = new PCTRetPlanAlterKey();

        // 処理フラグが全欠品完了
        if (PCTRetrievalInParameter.PROCESSING_DIVISION_STOCKOUT.equals(inputParam.getProcessingDivision()))
        {
            // 作業情報の予定数を欠品数に足しこんで更新
            updateKey.setUpdateWithColumn(PCTRetPlan.SHORTAGE_QTY, PCTRetPlan.SHORTAGE_QTY, new BigDecimal(
                    info.getPlanQty()));
        }
        // 処理フラグが全完了
        else if (PCTRetrievalInParameter.PROCESSING_DIVISION_COMPLETION.equals(inputParam.getProcessingDivision()))
        {
            // 作業情報の予定数を実績数に足しこんで更新
            updateKey.setUpdateWithColumn(PCTRetPlan.RESULT_QTY, PCTRetPlan.RESULT_QTY, new BigDecimal(
                    info.getPlanQty()));
        }
        // 状態フラグ
        updateKey.updateStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION);
        // 作業日
        updateKey.updateWorkDay(workInfo.getWorkDay());
        // 最終更新処理名
        updateKey.updateLastUpdatePname(getCallerName());

        /* 更新条件
         * 
         *  予定一意キー   =  作業情報の予定一意キー
         *  
         */
        updateKey.setPlanUkey(info.getPlanUkey());
        // PCT出庫予定情報ハンドラ
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());
        // 更新処理
        handler.modify(updateKey);
    }

    /**
     * PCT予定情報削除<BR>
     * <BR>
     * パラメータの予定一意キーに該当する予定情報の削除処理を行います。<BR>
     * 
     * @param  param 入力パラメータ
     * @param  plan  削除対象データ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws InvalidDefineException 指定パラメータが異常な場合にスローされます。
     */
    public void deletePlan(PCTRetrievalInParameter param, PCTRetPlan plan)
            throws ReadWriteException,
                InvalidDefineException
    {
        PCTRetWorkInfoHandler winfoHandler = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoSearchKey winfoSKey = new PCTRetWorkInfoSearchKey();

        // 出庫予定情報更新キーの生成
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());
        // PCT出庫予定情報ハンドラ
        PCTRetPlanSearchKey updateKey = new PCTRetPlanSearchKey();

        // パラメータの作業情報以外で削除以外の状態のデータが存在するかチェック
        /* 検索条件
         * 
         *  予定一意キー      =  読み込んだ予定情報の予定一意キー
         *  作業No.           != パラメータ作業情報の作業No.
         *  状態フラグ        != 削除('9')
         *  
         */

        winfoSKey.setPlanUkey(plan.getPlanUkey());
        winfoSKey.setJobNo(param.getJobNo(), "!=");
        winfoSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

        try
        {
            // 存在しなければ削除する
            if (winfoHandler.count(winfoSKey) <= 0)
            {

                updateKey.setPlanUkey(plan.getPlanUkey()); // 予定一意キー(削除条件)

                // 更新処理
                handler.drop(updateKey);

            }
        }
        catch (NotFoundException e)
        {
            // 対象出庫予定情報が存在しなくても削除処理なのでエラーにしない
        }
    }

    /**
     * PCT予定情報削除(一括)<BR>
     * <BR>
     * パラメータの予定一意キーに該当する予定情報の削除処理を行います。<BR>
     * 
     * @param workInfo 作業データ
     * @param inputParam 入力パラメータ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当データが存在しない場合にスローされます。
     * @throws InvalidDefineException 指定パラメータが異常な場合にスローされます。
     */
    public void deleteAllPlan(PCTRetWorkInfo workInfo, PCTRetrievalInParameter inputParam)
            throws ReadWriteException,
                NotFoundException,
                InvalidDefineException
    {
        PCTRetWorkInfoSearchKey winfoSKey = new PCTRetWorkInfoSearchKey();

        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());
        PCTRetPlanSearchKey planSKey = new PCTRetPlanSearchKey();

        // 出庫予定情報削除キーの生成
        /* 検索条件
         * 
         *  荷主コード   =  パラメータ作業情報の荷主コード
         *  予定日       =  パラメータ作業情報の予定日
         *  バッチNo.    =  パラメータ作業情報のバッチNo.
         *  バッチSeqNo. =  パラメータ作業情報のバッチSeqNo.
         *  エリア       =  パラメータ作業情報のエリア
         *  得意先コード =  パラメータ作業情報の得意先コード
         *  出荷先コード =  パラメータ作業情報の出荷先コード
         *  オーダーNo.  =  パラメータ作業情報のオーダーNo.
         *  状態フラグ   != 削除('9')
         *  
         */
        // 検索条件のセット
        // 荷主コード
        winfoSKey.setConsignorCode(workInfo.getConsignorCode());
        // 予定日
        if (!StringUtil.isBlank(workInfo.getPlanDay()))
        {
            winfoSKey.setPlanDay(workInfo.getPlanDay());
        }
        // バッチNo.
        if (!StringUtil.isBlank(workInfo.getBatchNo()))
        {
            winfoSKey.setBatchNo(workInfo.getBatchNo());
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(workInfo.getBatchSeqNo()))
        {
            winfoSKey.setBatchSeqNo(workInfo.getBatchSeqNo());
        }
        // エリア
        if (!StringUtil.isBlank(workInfo.getPlanAreaNo()))
        {
            winfoSKey.setPlanAreaNo(workInfo.getPlanAreaNo());
        }
        // 得意先コード
        if (!StringUtil.isBlank(workInfo.getRegularCustomerCode()))
        {
            winfoSKey.setRegularCustomerCode(workInfo.getRegularCustomerCode());
        }
        // 出荷先コード
        if (!StringUtil.isBlank(workInfo.getCustomerCode()))
        {
            winfoSKey.setCustomerCode(workInfo.getCustomerCode());
        }
        // オーダーNo.
        // 3-3
        if (!StringUtil.isBlank(workInfo.getResultOrderNo()))
        {
            winfoSKey.setResultOrderNo(workInfo.getResultOrderNo());
        }
        // 状態フラグ
        winfoSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

        winfoSKey.setCollect(PCTRetWorkInfo.PLAN_UKEY);

        try
        {
            /* 削除条件
             * 
             *  予定一意キー   =  上記の条件に当てはまる作業情報の予定一意キー
             *  
             */
            planSKey.setKey(PCTRetPlan.PLAN_UKEY, winfoSKey);

            // 更新処理
            handler.drop(planSKey);

        }
        catch (NotFoundException e)
        {
            // 対象出庫予定情報が存在しなくても削除処理なのでエラーにしない
        }
    }

    /**
     * PCT予定情報削除<BR>
     * (作業メンテナンス一括用)<BR>
     * 
     * パラメータの予定一意キーに該当する予定情報の削除処理を行います。<BR>
     * 
     * @param  plan  PCT出庫予定情報
     * @return boolean 削除結果を返す
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws InvalidDefineException 指定パラメータが異常な場合にスローされます。
     */
    public boolean deletePlanWorkMntAll(PCTRetPlan plan)
            throws ReadWriteException,
                InvalidDefineException
    {
        // 出庫予定情報更新キーの生成
        PCTRetPlanHandler handler = new PCTRetPlanHandler(getConnection());
        // PCT出庫予定情報ハンドラ
        PCTRetPlanSearchKey updateKey = new PCTRetPlanSearchKey();

        try
        {
            // 予定一意キー(削除条件)
            updateKey.setPlanUkey(plan.getPlanUkey());

            // 削除処理
            handler.drop(updateKey);

            return true;
        }
        catch (NotFoundException e)
        {
            // 対象出庫予定情報が存在しなくても削除処理なのでエラーにしない
            return false;
        }
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
        return "$Id: PCTRetPlanController.java 3755 2009-03-23 09:37:53Z ose $";
    }
}
