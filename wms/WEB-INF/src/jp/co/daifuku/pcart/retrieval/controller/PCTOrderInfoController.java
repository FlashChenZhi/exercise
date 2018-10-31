// $Id: PCTOrderInfoController.java 3755 2009-03-23 09:37:53Z ose $
package jp.co.daifuku.pcart.retrieval.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.controller.AbstractController;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTOrderInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.PCTOrderInfo;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;

/**
 * PCTオーダー情報を操作するためのコントローラクラスです。<br>
 *
 *
 * @version $Revision: 3755 $, $Date: 2009-03-23 18:37:53 +0900 (月, 23 3 2009) $
 * @author  etakeda
 * @author  Last commit: $Author: ose $
 */
public class PCTOrderInfoController
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

    //------------------------------------------------------------
    // constructors
    //------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)<br>
     * 
     * このコンストラクタでは、WMSSequenceHandlerを初期化します。
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public PCTOrderInfoController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------

    /**
     * PCTオーダー情報の完了処理を行います。 
     * 
     * @param src 完了対象作業情報
     * <ol>
     * 参照される項目は以下の通りです
     * <li>実績オーダーNo.
     * <li>端末No.
     * <li>ユーザID
     * </ol>
     * 
     * @param resultwork 完了作業情報
     * <ol>
     * 参照される項目は以下の通りです
     * <li>実績数
     * </ol>
     * 
     * @param retPlan 完了対象予定情報
     * <ol>
     * 参照される項目は以下の通りです
     * <li>ロット入数
     * </ol>
     * 
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws DataExistsException 分割時に該当のデータがすでに登録されていたときスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在したときスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった
     */
    public void updatePCTOrderInfoAll(PCTRetWorkInfo src, PCTRetWorkInfo resultwork, PCTRetPlan retPlan)
            throws NotFoundException,
                ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        PCTRetWorkInfoHandler winfoHandler = new PCTRetWorkInfoHandler(getConnection());
        PCTRetWorkInfoSearchKey winfoSKey = new PCTRetWorkInfoSearchKey();

        PCTOrderInfoHandler oinfoHandler = new PCTOrderInfoHandler(getConnection());
        PCTOrderInfoSearchKey oinfoSkey = new PCTOrderInfoSearchKey();
        PCTOrderInfoAlterKey oinfoAkey = new PCTOrderInfoAlterKey();

        // パラメータの作業情報を元にオーダー情報を読み込む
        /* 検索条件
         * 
         *  実績オーダーNo.   =  パラメータの作業情報の実績オーダーNo.
         *  状態フラグ        != 削除('9')
         *  
         */

        oinfoSkey.setResultOrderNo(src.getResultOrderNo());
        oinfoSkey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");

        PCTOrderInfo[] oinfo = (PCTOrderInfo[])oinfoHandler.find(oinfoSkey);
        Date wkDate = null;

        // 作業開始日時
        if (!StringUtil.isBlank(oinfo[0].getWorkStarttime()))
        {
            // 現状はNULLにしておく
            oinfoAkey.updateWorkStarttime(wkDate);
        }
        else
        {
            oinfoAkey.updateWorkStarttime(null);
        }

        // 作業終了日時
        if (!StringUtil.isBlank(oinfo[0].getWorkEndtime()))
        {
            // 現状はNULLにしておく
            oinfoAkey.updateWorkEndtime(wkDate);
        }
        else
        {
            oinfoAkey.updateWorkEndtime(null);
        }

        // 状態フラグ
        oinfoAkey.updateStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_COMPLETION);
        // 端末No.
        oinfoAkey.updateTerminalNo(src.getTerminalNo());
        // ユーザID
        oinfoAkey.updateUserId(src.getUserId());
        // 作業数量：読み込んだオーダー情報.作業数 + パラメータの実績情報の実績数
        oinfoAkey.updateWorkQty(oinfo[0].getWorkQty() + resultwork.getResultQty());

        // 作業数量（バラ）
        oinfoAkey.updatePieceQty(oinfo[0].getPieceQty() + (resultwork.getResultQty() * (retPlan.getLotEnteringQty())));

        // 明細数
        // パラメータの作業情報でデータ件数を求める
        /* 検索条件
         * 
         *  実績オーダーNo.   =  パラメータの作業情報の実績オーダーNo.
         *  
         */
        winfoSKey.clear();
        winfoSKey.setResultOrderNo(src.getResultOrderNo());
        oinfoAkey.updateWorkCnt(winfoHandler.count(winfoSKey));

        // 集品箱数
        oinfoAkey.updateBoxCnt(1);
        // 作業時間
        oinfoAkey.updateWorkTime(oinfo[0].getWorkTime());
        // 実作業時間
        oinfoAkey.updateRealWorkTime(oinfo[0].getRealWorkTime());
        // ミススキャン数
        oinfoAkey.updateMissScanCnt(oinfo[0].getMissScanCnt());
        // 最終更新処理名
        oinfoAkey.updateLastUpdatePname(getCallerName());

        // 検索条件
        oinfoAkey.setResultOrderNo(oinfo[0].getResultOrderNo());
        oinfoAkey.setLastUpdateDate(oinfo[0].getLastUpdateDate());

        // 更新
        oinfoHandler.modify(oinfoAkey);
    }

    /**
     * PCTオーダー情報の削除処理を行います。
     * 作業メンテナンス一括
     * 
     * @param src 削除対象データ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 分割時に該当のデータがすでに登録されていたときスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在したときスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった
     */
    public void deletePCTOrderInfoAll(PCTRetWorkInfo src)
            throws ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        PCTRetWorkInfoSearchKey winfoSKey = new PCTRetWorkInfoSearchKey();

        PCTOrderInfoHandler oinfoHandler = new PCTOrderInfoHandler(getConnection());
        PCTOrderInfoSearchKey oinfoSkey = new PCTOrderInfoSearchKey();

        // パラメータの作業情報以外で削除以外の状態のデータが存在するかチェック
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

        // 検索条件
        // 検索条件のセット
        // 荷主コード
        winfoSKey.setConsignorCode(src.getConsignorCode());
        // 予定日
        if (!StringUtil.isBlank(src.getPlanDay()))
        {
            winfoSKey.setPlanDay(src.getPlanDay());
        }
        // バッチNo.
        if (!StringUtil.isBlank(src.getBatchNo()))
        {
            winfoSKey.setBatchNo(src.getBatchNo());
        }
        // バッチSeqNo.
        if (!StringUtil.isBlank(src.getBatchSeqNo()))
        {
            winfoSKey.setBatchSeqNo(src.getBatchSeqNo());
        }
        // エリア
        if (!StringUtil.isBlank(src.getPlanAreaNo()))
        {
            winfoSKey.setPlanAreaNo(src.getPlanAreaNo());
        }
        // 得意先コード
        if (!StringUtil.isBlank(src.getRegularCustomerCode()))
        {
            winfoSKey.setRegularCustomerCode(src.getRegularCustomerCode());
        }
        // 出荷先コード
        if (!StringUtil.isBlank(src.getCustomerCode()))
        {
            winfoSKey.setCustomerCode(src.getCustomerCode());
        }
        // オーダーNo.
        if (!StringUtil.isBlank(src.getResultOrderNo()))
        {
            winfoSKey.setResultOrderNo(src.getResultOrderNo());
        }
        winfoSKey.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_DELETE, "!=");
        winfoSKey.setCollect(PCTRetWorkInfo.RESULT_ORDER_NO);

        try
        {
            /* 削除条件
             * 
             *  実績オーダーNo.   =  上記の条件に当てはまる作業情報の実績オーダーNo.
             *  
             */
            oinfoSkey.setKey(PCTOrderInfo.RESULT_ORDER_NO, winfoSKey);

            // 削除処理
            oinfoHandler.drop(oinfoSkey);
        }
        catch (NotFoundException e)
        {
            // 対象オーダー情報が存在しなくても削除処理なのでエラーにしない
        }
    }

    /**
     * PCTオーダー情報の削除処理を行います。<br>
     * (作業メンテナンス一括用)<br>
     * 
     * @param plan 削除対象データ
     * @return boolean 削除結果を返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws DataExistsException 分割時に該当のデータがすでに登録されていたときスローされます。
     * @throws NoPrimaryException 作業Noに該当する作業情報が複数存在したときスローされます。
     * @throws ScheduleException マスタ情報が検索できなかった
     */
    public boolean deleteOrderInfoWorkMntAll(PCTRetPlan plan)
            throws ReadWriteException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        PCTOrderInfoHandler oinfoHandler = new PCTOrderInfoHandler(getConnection());
        PCTOrderInfoSearchKey oinfoSkey = new PCTOrderInfoSearchKey();

        try
        {
            // 予定オーダーNo.
            oinfoSkey.setPlanOrderNo(plan.getPlanOrderNo());

            // 削除処理
            oinfoHandler.drop(oinfoSkey);

            return true;
        }
        catch (NotFoundException e)
        {
            // 対象オーダー情報が存在しなくても削除処理なのでエラーにしない
            return false;
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
        return "$Id: PCTOrderInfoController.java 3755 2009-03-23 09:37:53Z ose $";
    }
}
