// $Id: PCTRetHostSendController.java 3755 2009-03-23 09:37:53Z ose $
package jp.co.daifuku.pcart.retrieval.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Calendar;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.pcart.retrieval.schedule.PCTRetrievalInParameter;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.controller.AbstractController;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserHandler;
import jp.co.daifuku.wms.base.dbhandler.Com_loginuserSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetHostSendAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetHostSendSearchKey;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PCTRetWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.PCTRetHostSend;
import jp.co.daifuku.wms.base.entity.PCTRetPlan;
import jp.co.daifuku.wms.base.entity.PCTRetWorkInfo;
import jp.co.daifuku.wms.base.entity.PCTUserResult;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.handler.db.AbstractDBHandler;
import jp.co.daifuku.wms.handler.db.SysDate;


/**
 * PCT出庫実績送信情報を操作するためのコントローラクラスです。<br>
 *
 *
 * @version $Revision: 3755 $, $Date: 2009-03-23 18:37:53 +0900 (月, 23 3 2009) $
 * @author  etakeda
 * @author  Last commit: $Author: ose $
 */

public class PCTRetHostSendController
        extends AbstractController
        implements SystemDefine
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
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public PCTRetHostSendController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * PCT出庫作業情報からPCT出庫実績送信情報を作成します。<br>
     * 
     * @param inputParam 入力データ
     * @param src 完了対象データ
     * @param result 完了情報
     * @param retPlan 予定情報
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 該当するデータが複数存在するときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     * @throws ScheduleException 作業情報からマスタを検索できなかったときスローされます。
     */
    public void insertPCTRetHostsendAll(Parameter inputParam, PCTRetWorkInfo src, PCTRetWorkInfo result,
            PCTRetPlan retPlan)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {

        // update field setup
        PCTRetHostSendHandler hostSendHandler = new PCTRetHostSendHandler(getConnection());
        PCTRetHostSend hostSendEntity = new PCTRetHostSend();

        // PCTRetrievalInParameterにキャスト
        PCTRetrievalInParameter inParam = (PCTRetrievalInParameter)inputParam;

        // 登録する値を設定
        // 作業日
        hostSendEntity.setWorkDay(src.getWorkDay());
        // 作業No.
        hostSendEntity.setJobNo(src.getJobNo());
        // 設定単位キー
        hostSendEntity.setSettingUnitKey(src.getSettingUnitKey());
        // 集約作業No
        hostSendEntity.setCollectJobNo(src.getCollectJobNo());
        // 状態フラグ：メンテ完了
        hostSendEntity.setStatusFlag(PCTRetrievalInParameter.STATUS_FLAG_MAINTENANCE_COMPLETION);
        // ハードウェア区分：リスト
        hostSendEntity.setHardwareType(PCTRetHostSend.HARDWARE_TYPE_LIST);
        // 予定一意キー
        hostSendEntity.setPlanUkey(src.getPlanUkey());
        // 在庫ID
        hostSendEntity.setStockId(src.getStockId());
        // システム接続キー
        hostSendEntity.setSystemConnKey(src.getSystemConnKey());
        // 予定日
        hostSendEntity.setPlanDay(src.getPlanDay());
        // 荷主コード
        hostSendEntity.setConsignorCode(src.getConsignorCode());
        // 荷主名称
        hostSendEntity.setConsignorName(retPlan.getConsignorName());
        // 得意先コード
        hostSendEntity.setRegularCustomerCode(src.getRegularCustomerCode());
        // 得意先名称
        hostSendEntity.setRegularCustomerName(retPlan.getRegularCustomerName());
        // 出荷先コード
        hostSendEntity.setCustomerCode(src.getCustomerCode());
        // 出荷先名称
        hostSendEntity.setCustomerName(retPlan.getCustomerName());
        // 出荷先分類コード
        hostSendEntity.setCustomerCategory(src.getCustomerCategory());
        // 出荷伝票No.
        hostSendEntity.setShipTicketNo(src.getShipTicketNo());
        // 出荷伝票行
        hostSendEntity.setShipLineNo(src.getShipLineNo());
        // 出荷伝票作業連番
        hostSendEntity.setShipBranchNo(src.getShipBranchNo());
        // バッチNo.
        hostSendEntity.setBatchNo(src.getBatchNo());
        // バッチSeqNo.
        hostSendEntity.setBatchSeqNo(src.getBatchSeqNo());
        // オーダーNo.
        hostSendEntity.setOrderNo(src.getOrderNo());
        // オーダーSeqNo.
        hostSendEntity.setOrderSeq(src.getOrderSeq());
        // オーダー情報コメント
        hostSendEntity.setOrderInfo(src.getOrderInfo());
        // 予定オーダーNo.
        hostSendEntity.setPlanOrderNo(src.getPlanOrderNo());
        // 実績オーダーNo.
        hostSendEntity.setResultOrderNo(src.getResultOrderNo());
        // 通番
        hostSendEntity.setThroughNo(retPlan.getThroughNo());
        // オーダー内商品数
        hostSendEntity.setOrderItemQty(retPlan.getOrderItemQty());
        // オーダー通番
        hostSendEntity.setOrderThroughNo(retPlan.getOrderThroughNo());
        // オーダー通番合計
        hostSendEntity.setOrderThroughNoCnt(retPlan.getOrderThroughNoCnt());
        // 汎用フラグ
        hostSendEntity.setGeneralFlag(retPlan.getGeneralFlag());
        // シュートNo.
        hostSendEntity.setShootNo(retPlan.getShootNo());
        // 予定エリア
        hostSendEntity.setPlanAreaNo(src.getPlanAreaNo());
        // 予定ゾーン
        hostSendEntity.setPlanZoneNo(src.getPlanZoneNo());
        // 作業ゾーン
        hostSendEntity.setWorkZoneNo(src.getWorkZoneNo());
        // 予定棚
        hostSendEntity.setPlanLocationNo(retPlan.getPlanLocationNo());
        // 商品コード
        hostSendEntity.setItemCode(src.getItemCode());
        // 商品名称
        hostSendEntity.setItemName(retPlan.getItemName());
        // ケース入数
        hostSendEntity.setEnteringQty(retPlan.getEnteringQty());
        // ボール入数
        hostSendEntity.setBundleEnteringQty(retPlan.getBundleEnteringQty());
        // ロット入数
        hostSendEntity.setLotEnteringQty(retPlan.getLotEnteringQty());
        // JANコード
        hostSendEntity.setJan(retPlan.getJan());
        // ケースITF
        hostSendEntity.setItf(retPlan.getItf());
        // ボールITF
        hostSendEntity.setBundleItf(retPlan.getBundleItf());
        // 基準日付
        hostSendEntity.setUseByDate(src.getUseByDate());
        // アイテム情報コメント
        hostSendEntity.setItemInfo(src.getItemInfo());
        // 予定ロットNo.
        hostSendEntity.setPlanLotNo(src.getPlanLotNo());
        // 予定数(PCT出庫作業情報.予定数)
        hostSendEntity.setPlanQty(result.getPlanQty());
        // 実績数(入力された実績数)
        hostSendEntity.setResultQty(result.getResultQty());
        // 欠品数(PCT出庫作業情報.予定数)
        hostSendEntity.setShortageQty(result.getShortageQty());
        // 実績報告区分
        hostSendEntity.setReportFlag(PCTRetHostSend.REPORT_FLAG_NOT_REPORT);
        // ユーザID
        hostSendEntity.setUserId(src.getUserId());
        // ユーザ名称
        hostSendEntity.setUserName(inParam.getUserName());
        // 端末No、RFTNo
        hostSendEntity.setTerminalNo(src.getTerminalNo());
        // 作業秒数
        hostSendEntity.setWorkSecond(src.getWorkSecond());
        // 登録日時
        hostSendEntity.setRegistDate(new SysDate());
        // 登録処理名
        hostSendEntity.setRegistPname(getCallerName());
        // 最終更新処理名
        hostSendEntity.setLastUpdatePname(getCallerName());
        // 箱替フラグにtrue設定
        inParam.setSubstitutes(true);

        // 登録
        hostSendHandler.create(hostSendEntity);
    }

    /**
     * PCT出庫作業情報からPCT出庫実績送信情報を更新します。<br>
     * 
     * @param inputParam パラメータ
     * @param src
     * @param result
     * @param statusFlag
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 該当するデータが複数存在するときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     * @throws ScheduleException 作業情報からマスタを検索できなかったときスローされます。
     */
    @SuppressWarnings("unused")
    public void updatePCTRetHostsend(Parameter inputParam, PCTRetWorkInfo src, PCTRetWorkInfo result, String statusFlag)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        // update field setup
        AbstractDBHandler wih = new PCTRetHostSendHandler(getConnection());
        PCTRetHostSendAlterKey akey = new PCTRetHostSendAlterKey();

        // 作業日
        akey.updateWorkDay(src.getWorkDay());
        // 状態フラグ
        akey.updateStatusFlag(statusFlag);
        // 実績数
        akey.updateResultQty(result.getResultQty());
        // 欠品数
        akey.updateShortageQty(result.getPlanQty() - result.getResultQty());
        // 最終更新処理名
        akey.updateLastUpdatePname(getCallerName());

        // 検索条件
        akey.setKey(PCTRetHostSend.JOB_NO, src.getJobNo());
        // 修正
        wih.modify(akey);
    }

    /**
     * PCT出庫作業情報からPCTユーザ実績情報を作成します。<br>
     * @param src 完了対象データ
     * @param result 完了情報
     * @param statusFlag 完了フラグ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 該当するデータが複数存在するときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     * @throws ScheduleException 作業情報からマスタを検索できなかったときスローされます。
     */
    public void insertByUserInfoBatch(PCTRetWorkInfo src, PCTRetWorkInfo result, String statusFlag)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        // update field setup
        PCTRetWorkInfoHandler wih = new PCTRetWorkInfoHandler(getConnection());
        PCTUserResult userResult = new PCTUserResult();

        Com_loginuserHandler loginHandler = new Com_loginuserHandler(getConnection());
        Com_loginuserSearchKey loginshKey = new Com_loginuserSearchKey();
        Com_loginuser[] loginEntity = null;

        Calendar cal = Calendar.getInstance();

        // 登録する値を設定
        // 作業日
        userResult.setWorkDate(src.getWorkDay());
        // 曜日
        userResult.setDayOfWeek((cal.get(Calendar.DAY_OF_WEEK) - 1));
        // 作業開始日時
        userResult.setWorkStarttime(new SysDate());
        // 作業終了日時
        userResult.setWorkEndtime(new SysDate());
        // ユーザID
        userResult.setUserId(src.getUserId());
        // ユーザ名称
        // 名称はユーザマスタから取得
        loginshKey.clear();
        loginshKey.setUserid(src.getUserId());
        loginshKey.setUsernameCollect();
        loginEntity = (Com_loginuser[])loginHandler.find(loginshKey);
        userResult.setUserName(loginEntity[0].getUsername());
        // 端末No、RFTNo
        userResult.setTerminalNo(src.getTerminalNo());
        // 実績区分(運用)
        userResult.setResultType(PCTRetrievalInParameter.RESULT_TYPE_OPERATION);
        // 作業区分(ピッキング)
        userResult.setJobType(PCTRetrievalInParameter.DATA_TYPE_RETRIEVAL);
        // 設定単位キー
        userResult.setSettingUnitKey(src.getSettingUnitKey());
        // 完了区分
        userResult.setCompleteKind(PCTRetrievalInParameter.COMPLETE_KIND_WEB);
        // 作業時間
        userResult.setWorkTime(0);
        // 登録日時
        userResult.setRegistDate(new SysDate());
        // 登録処理名
        userResult.setRegistPname(getCallerName());
        // 最終更新処理名
        userResult.setLastUpdatePname(getCallerName());

        // 登録
        wih.create(userResult);
    }

    /**
     * PCT出庫作業情報からPCT出庫実績送信情報を削除します。<br>
     * 
     * @param src 削除対象データ
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 該当するデータが複数存在するときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     * @throws ScheduleException 作業情報からマスタを検索できなかったときスローされます。
     */
    public void deletePCTRetHostsend(PCTRetWorkInfo src)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        // 出庫実績送信情報削除キーの生成
        PCTRetHostSendHandler wih = new PCTRetHostSendHandler(getConnection());
        PCTRetHostSendSearchKey skey = new PCTRetHostSendSearchKey();

        PCTRetWorkInfoSearchKey winfoSKey = new PCTRetWorkInfoSearchKey();

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
        winfoSKey.setCollect(PCTRetWorkInfo.JOB_NO);

        /* 実績報告区分
         *   現状はどの状態でも削除だが条件に必要になるかもしれないので
         *   コメントにしておく
         * skey.setReportFlag(PCTRetrievalInParameter.REPORT_FLAG_UNDO);
         */

        try
        {
            /* 削除条件
             * 
             *  作業No.   =  上記の条件に当てはまる作業情報の作業No.
             *  
             */
            skey.setKey(PCTRetHostSend.JOB_NO, winfoSKey);

            // 削除処理
            wih.drop(skey);
        }
        catch (NotFoundException e)
        {
            // 対象出庫実績送信情報が存在しなくても削除処理なのでエラーにしない
        }
    }

    /**
     * PCT出庫作業情報からPCT出庫実績送信情報を削除します。<br>
     * (作業メンテナンス一括用)<br>
     * 
     * @param plan PCT出庫予定情報
     * @return boolean 削除結果を返します。
     * @throws ReadWriteException データベースアクセスエラーが発生したときスローされます。
     * @throws NotFoundException 該当のデータがなかったときスローされます。
     * @throws NoPrimaryException 該当するデータが複数存在するときスローされます。
     * @throws DataExistsException すでに登録済みであったときスローされます。
     * @throws ScheduleException 作業情報からマスタを検索できなかったときスローされます。
     */
    public boolean deleteHostsendWorkMntAll(PCTRetPlan plan)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        // 出庫実績送信情報削除キーの生成
        PCTRetHostSendHandler wih = new PCTRetHostSendHandler(getConnection());
        PCTRetHostSendSearchKey skey = new PCTRetHostSendSearchKey();

        try
        {
            // 予定一意キー
            skey.setPlanUkey(plan.getPlanUkey());

            // 削除処理
            wih.drop(skey);

            return true;
        }
        catch (NotFoundException e)
        {
            // 対象出庫実績送信情報が存在しなくても削除処理なのでエラーにしない
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
        return "$Id: PCTRetHostSendController.java 3755 2009-03-23 09:37:53Z ose $";
    }
}
