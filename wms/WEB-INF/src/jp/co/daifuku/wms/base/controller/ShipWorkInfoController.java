// $Id: ShipWorkInfoController.java 5028 2009-09-18 04:31:29Z kishimoto $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2008 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WmsUserInfo;
import jp.co.daifuku.wms.base.dbhandler.ShipHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShipWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.ShipHostSend;
import jp.co.daifuku.wms.base.entity.ShipWorkInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;
import jp.co.daifuku.wms.handler.field.FieldName;


/**
 * 出荷作業情報を操作するためのコントローラクラスです。
 *
 * @version $Revision: 5028 $, $Date: 2009-09-18 13:31:29 +0900 (金, 18 9 2009) $
 * @author  Last commit: $Author: kishimoto $
 */
public class ShipWorkInfoController
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
     * コントローラが使用するデータベースコネクションと、
     * 呼び出し元クラス(ロギング,更新プログラムの保存用に使用されます)を指定して
     * インスタンスを生成します。
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     */
    public ShipWorkInfoController(Connection conn, Class caller)
    {
        super(conn, caller);
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 作業情報完了処理を行います。<BR>
     * 出荷検品状態を完了に更新し、ハードウェア区分、予定数、実績数、欠品数、
     * 実績ロットNo、作業日、ユーザID、端末No.、作業秒数をセットします。<BR>
     * バース登録状態が完了であればパラメータの作業No.より出荷作業情報、
     * 各マスタを検索して出荷実績送信情報を作成します。
     * 
     * @param src 完了対象データ
     * @param result 完了情報
     * @param ui WMSユーザ情報
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NotFoundException 該当するデータがない場合にスローされます。
     * @throws DataExistsException 作業情報が登録済みの場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がない場合にスローされます。
     */
    public void completeWorkInfo(ShipWorkInfo src, ShipWorkInfo result, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        // 出荷作業情報を更新します。
        ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
        ShipWorkInfoAlterKey alterKey = new ShipWorkInfoAlterKey();

        // 更新内容のセット
        // 出荷作業情報．出荷検品状態フラグ = ”4”（完了）
        alterKey.updateWorkStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
        // 出荷作業情報．ハードウェア区分 = パラメータの完了情報のハードウェア区分 ※Null以外の場合、更新対象
        if (!StringUtil.isBlank(result.getHardwareType()))
        {
            alterKey.updateHardwareType(result.getHardwareType());
        }
        // 出荷作業情報．予定数 = パラメータの完了情報の予定数
        alterKey.updatePlanQty(result.getPlanQty());
        // 出荷作業情報．実績数 = パラメータの完了情報の実績数
        alterKey.updateResultQty(result.getResultQty());
        // 出荷作業情報．欠品数 = パラメータの完了情報の欠品数
        alterKey.updateShortageQty(result.getShortageQty());
        // 出荷作業情報．実績ロットNo. = パラメータの完了情報の実績ロットNo.
        alterKey.updateResultLotNo(result.getResultLotNo());
        // 出荷作業情報．作業日 = パラメータの完了情報の作業日
        alterKey.updateWorkDay(result.getWorkDay());
        // 出荷作業情報．出荷検品ユーザID = パラメータのWMSユーザ情報のユーザID
        alterKey.updateShipUserId(ui.getUserId());
        // 出荷作業情報．出荷検品端末No. = パラメータのWMSユーザ情報の端末No.
        alterKey.updateShipTerminalNo(ui.getTerminalNo());
        // 出荷作業情報．出荷検品作業秒数 = パラメータの完了情報の出荷検品作業秒数
        alterKey.updateShipWorkSecond(result.getShipWorkSecond());
        // 出荷作業情報．最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得
        alterKey.updateLastUpdatePname(getCallerName());

        // 更新条件
        alterKey.setJobNo(src.getJobNo());

        // 更新の実行
        handler.modify(alterKey);

        // パラメータの完了対象データのバース登録状態フラグ=”4”（完了）の場合、出荷実績送信情報の登録を行う。
        if (SystemDefine.STATUS_FLAG_COMPLETION.equals(src.getBerthStatusFlag()))
        {
            insertHostSendByWorkInfo(src.getJobNo());
        }
    }

    /**
     * 積込完了処理を行います。<BR>
     * バース登録状態を完了に更新し、結果バース、作業日、ユーザID、
     * 端末No.、作業秒数をセットします。<br>
     * 出荷検品状態が完了であればパラメータの作業Noより出荷作業情報、
     * 各マスタを検索して出荷実績送信情報を作成します。
     * 
     * @param src 完了対象データ
     * @param result 完了情報
     * @param ui WMSユーザ情報
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NotFoundException 該当するデータがない場合にスローされます。
     * @throws DataExistsException 作業情報が登録済みの場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がない場合にスローされます。
     */
    public void completeLoad(ShipWorkInfo src, ShipWorkInfo result, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        // 出荷作業情報を更新します。
        ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
        ShipWorkInfoAlterKey alterKey = new ShipWorkInfoAlterKey();


        // 更新内容のセット
        // 設定単位キー
        if (!StringUtil.isBlank(result.getSettingUnitKey()))
        {
            alterKey.updateSettingUnitKey(result.getSettingUnitKey());
        }
        // 出荷作業情報．バース登録状態フラグ = ”4”（完了）
        alterKey.updateBerthStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
        // 出荷作業情報．ハードウェア区分 = パラメータの完了情報のハードウェア区分 ※Null以外の場合、更新対象
        if (!StringUtil.isBlank(result.getHardwareType()))
        {
            alterKey.updateHardwareType(result.getHardwareType());
        }
        // 出荷作業情報．結果バース = パラメータの完了情報の結果バース
        alterKey.updateResultBerth(result.getResultBerth());
        // 出荷作業情報．作業日 = パラメータの完了情報の作業日
        alterKey.updateWorkDay(result.getWorkDay());
        // 出荷作業情報．バース登録ユーザID = パラメータのWMSユーザ情報のユーザID
        alterKey.updateBerthUserId(ui.getUserId());
        // 出荷作業情報．バース登録端末No. = パラメータのWMSユーザ情報の端末No.
        alterKey.updateBerthTerminalNo(ui.getTerminalNo());
        // 出荷作業情報．バース登録作業秒数 = パラメータの完了情報のバース登録作業秒数
        alterKey.updateBerthWorkSecond(result.getBerthWorkSecond());
        // 出荷作業情報．最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得
        alterKey.updateLastUpdatePname(getCallerName());

        // 更新条件
        alterKey.setJobNo(src.getJobNo());

        // 更新の実行
        handler.modify(alterKey);

        // パラメータの完了対象データの出荷検品状態=”4”（完了）の場合、insertHostSendByWorkInfoを呼び出し、出荷実績送信情報の登録を行う。
        if (SystemDefine.STATUS_FLAG_COMPLETION.equals(src.getWorkStatusFlag()))
        {
            insertHostSendByWorkInfo(src.getJobNo());
        }
    }

    /**
     * 出荷実績送信情報登録処理を行います。<BR>
     * パラメータの作業Noより出荷作業情報を検索して出荷実績送信情報を作成します。<BR>
     * 各マスタ情報より必須項目を検索してセットします。
     * 
     * @param jobNo 作業No
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NotFoundException 該当するデータがない場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     * @throws DataExistsException 作業情報が登録済みの場合にスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がない場合にスローされます。
     */
    public void insertHostSendByWorkInfo(String jobNo)
            throws ReadWriteException,
                NotFoundException,
                NoPrimaryException,
                DataExistsException,
                ScheduleException
    {
        // 出荷作業情報、及び各マスタ情報を結合して検索する（findPrimary）
        Entity entity = getWorkInfo(jobNo);

        if (entity == null)
        {
            // 検索結果がなかった場合、エラーを通知し処理終了
            throw new ScheduleException();
        }

        // 出荷実績送信情報を登録する。
        createHostSendByWorkInfo(entity);

    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 作業情報更新処理を行います。<BR>
     * ハードウェア区分、予定数、実績数、欠品数、実績ロットNo、
     * 作業日、ユーザID、端末No.、作業秒数をセットします。
     * 
     * @param src 更新対象データ
     * @param result 更新情報
     * @param ui WMSユーザ情報
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NotFoundException 該当するデータがない場合にスローされます。
     * @throws DataExistsException 作業情報が登録済みの場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     * @throws ScheduleException DNWarenaviSystemに整合性がない場合にスローされます。
     */
    public void updateWorkInfo(ShipWorkInfo src, ShipWorkInfo result, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        // 出荷作業情報を更新します。
        ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
        ShipWorkInfoAlterKey alterKey = new ShipWorkInfoAlterKey();

        // 更新内容のセット
        // 出荷作業情報．ハードウェア区分 = パラメータの更新情報のハードウェア区分 ※Null以外の場合、更新対象
        if (!StringUtil.isBlank(result.getHardwareType()))
        {
            alterKey.updateHardwareType(result.getHardwareType());
        }
        // 出荷作業情報．予定数 = パラメータの更新情報の予定数
        alterKey.updatePlanQty(result.getPlanQty());
        // 出荷作業情報．実績数 = パラメータの更新情報の実績数
        alterKey.updateResultQty(result.getResultQty());
        // 出荷作業情報．欠品数 = パラメータの更新情報の欠品数
        alterKey.updateShortageQty(result.getShortageQty());
        // 出荷作業情報．実績ロットNo. = パラメータの更新情報の実績ロットNo.
        alterKey.updateResultLotNo(result.getResultLotNo());
        // 出荷作業情報．作業日 = パラメータの更新情報の作業日
        alterKey.updateWorkDay(result.getWorkDay());
        // 出荷作業情報．出荷検品ユーザID = パラメータのWMSユーザ情報のユーザID
        alterKey.updateShipUserId(ui.getUserId());
        // 出荷作業情報．出荷検品端末No. = パラメータのWMSユーザ情報の端末No.
        alterKey.updateShipTerminalNo(ui.getTerminalNo());
        // 出荷作業情報．出荷検品作業秒数 = パラメータの完了情報の出荷検品作業秒数
        alterKey.updateShipWorkSecond(result.getShipWorkSecond());
        // 出荷作業情報．最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得
        alterKey.updateLastUpdatePname(getCallerName());

        // 更新条件
        alterKey.setJobNo(src.getJobNo());

        // 更新の実行
        handler.modify(alterKey);

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
     * パラメータの作業Noより出荷作業情報を検索します。
     * 
     * @param jobNo 作業No
     * @return 検索された出荷作業情報
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     */
    protected Entity getWorkInfo(String jobNo)
            throws ReadWriteException,
                NoPrimaryException
    {
        // 出荷作業情報を検索します。
        ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
        ShipWorkInfoSearchKey searchKey = new ShipWorkInfoSearchKey();

        // 取得項目

        // 出荷作業情報．作業日
        searchKey.setWorkDayCollect();
        // 出荷作業情報．作業No.
        searchKey.setJobNoCollect();
        // 出荷作業情報．設定単位キー
        searchKey.setSettingUnitKeyCollect();
        // 出荷作業情報．集約作業No
        searchKey.setCollectJobNoCollect();
        // 出荷作業情報．クロスドック連携キー
        searchKey.setCrossDockUkeyCollect();
        // 出荷作業情報．作業区分
        searchKey.setJobTypeCollect();
        // 出荷作業情報．出荷検品状態フラグ
        searchKey.setWorkStatusFlagCollect();
        // 出荷作業情報．バース登録状態フラグ
        searchKey.setBerthStatusFlagCollect();
        // 出荷作業情報．ハードウェア区分
        searchKey.setHardwareTypeCollect();
        // 出荷作業情報．予定一意キー
        searchKey.setPlanUkeyCollect();
        // 出荷作業情報．バッチNo.
        searchKey.setBatchNoCollect();
        // 出荷作業情報．TC/DC区分
        searchKey.setTcdcFlagCollect();
        // 出荷作業情報．予定日
        searchKey.setPlanDayCollect();
        // 出荷作業情報．荷主コード
        searchKey.setConsignorCodeCollect();
        // 荷主マスタ情報．荷主名称
        searchKey.setCollect(Consignor.CONSIGNOR_NAME);
        // 出荷作業情報．出荷先コード
        searchKey.setCustomerCodeCollect();
        // 出荷先マスタ情報．出荷先名称
        searchKey.setCollect(Customer.CUSTOMER_NAME);
        // 出荷作業情報．出荷伝票No
        searchKey.setShipTicketNoCollect();
        // 出荷作業情報．出荷伝票行
        searchKey.setShipLineNoCollect();
        // 出荷作業情報．商品コード
        searchKey.setItemCodeCollect();
        // 商品マスタ情報．商品名称
        searchKey.setCollect(Item.ITEM_NAME);
        // 商品マスタ情報．JANコード
        searchKey.setCollect(Item.JAN);
        // 商品マスタ情報．ケースITF
        searchKey.setCollect(Item.ITF);
        // 商品マスタ情報．ボールITF
        searchKey.setCollect(Item.BUNDLE_ITF);
        // 商品マスタ情報．ケース入数
        searchKey.setCollect(Item.ENTERING_QTY);
        // 商品マスタ情報．ボール入数
        searchKey.setCollect(Item.BUNDLE_ENTERING_QTY);
        // 出荷作業情報．予定ロットNo
        searchKey.setPlanLotNoCollect();
        // 出荷作業情報．予定数
        searchKey.setPlanQtyCollect();
        // 出荷作業情報．実績数
        searchKey.setResultQtyCollect();
        // 出荷作業情報．欠品数
        searchKey.setShortageQtyCollect();
        // 出荷作業情報．実績ロットNo
        searchKey.setResultLotNoCollect();
        // 出荷作業情報．結果バース
        searchKey.setResultBerthCollect();
        // 出荷作業情報．出荷検品ユーザID
        searchKey.setShipUserIdCollect();
        // ログインユーザ設定．ユーザ名
        searchKey.setCollect(Com_loginuser.USERNAME);
        // 出荷作業情報．出荷検品端末No、RFTNo
        searchKey.setShipTerminalNoCollect();
        // 出荷作業情報．出荷検品作業秒数
        searchKey.setShipWorkSecondCollect();
        // 出荷作業情報．バース登録ユーザID
        searchKey.setBerthUserIdCollect();
        // 出荷作業情報．バース登録端末No、RFTNo
        searchKey.setBerthTerminalNoCollect();
        // 出荷作業情報．バース登録作業秒数
        searchKey.setBerthWorkSecondCollect();

        // 検索条件設定
        // 出荷作業情報．作業No. = パラメータの作業No.
        searchKey.setJobNo(jobNo);

        // 結合条件
        // 出荷作業情報．荷主コード = 荷主マスタ情報．荷主コード
        searchKey.setJoin(ShipWorkInfo.CONSIGNOR_CODE, "", Consignor.CONSIGNOR_CODE, "(+)");
        // 出荷作業情報．荷主コード = 出荷先マスタ情報．荷主コード
        searchKey.setJoin(ShipWorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        // 出荷作業情報．出荷先コード = 出荷先マスタ情報．出荷先コード
        searchKey.setJoin(ShipWorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");
        // 出荷作業情報．荷主コード = 商品マスタ情報．荷主コード
        searchKey.setJoin(ShipWorkInfo.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        // 出荷作業情報．商品コード = 商品マスタ情報．商品コード
        searchKey.setJoin(ShipWorkInfo.ITEM_CODE, "", Item.ITEM_CODE, "(+)");
        // 出荷作業情報．出荷検品ユーザID = ログインユーザ設定．ユーザID（+）
        searchKey.setJoin(ShipWorkInfo.SHIP_USER_ID, "", Com_loginuser.USERID, "(+)");

        return handler.findPrimary(searchKey);
    }

    /**
     * パラメータの出荷作業情報を元に、出荷実績送信情報を登録します。
     * 
     * @param entity 出荷作業情報
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws DataExistsException 出荷実績送信情報が登録済みの場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     */
    protected void createHostSendByWorkInfo(Entity entity)
            throws DataExistsException,
                ReadWriteException,
                NoPrimaryException
    {
        // キャスト
        ShipWorkInfo workInfo = (ShipWorkInfo)entity;

        // 出荷実績送信情報を登録します。
        ShipHostSendHandler handler = new ShipHostSendHandler(getConnection());
        ShipHostSend hostsend = new ShipHostSend();

        // 登録情報
        // 作業日 = 1で検索した同項目
        hostsend.setWorkDay(workInfo.getWorkDay());
        // 作業No. = 1で検索した同項目
        hostsend.setJobNo(workInfo.getJobNo());
        // 設定単位キー = 1で検索した同項目
        hostsend.setSettingUnitKey(workInfo.getSettingUnitKey());
        // 集約作業No. = 1で検索した同項目
        hostsend.setCollectJobNo(workInfo.getCollectJobNo());
        // クロスドック連携キー = 1で検索した同項目
        hostsend.setCrossDockUkey(workInfo.getCrossDockUkey());
        // 作業区分 = 1で検索した同項目
        hostsend.setJobType(workInfo.getJobType());
        // 出荷検品状態フラグ = 1で検索した同項目
        hostsend.setWorkStatusFlag(workInfo.getWorkStatusFlag());
        // バース登録状態フラグ = 1で検索した同項目
        hostsend.setBerthStatusFlag(workInfo.getBerthStatusFlag());
        // ハードウェア区分 = 1で検索した同項目
        hostsend.setHardwareType(workInfo.getHardwareType());
        // 予定一意キー = 1で検索した同項目
        hostsend.setPlanUkey(workInfo.getPlanUkey());
        // TC/DC区分 = 1で検索した同項目
        hostsend.setTcdcFlag(workInfo.getTcdcFlag());
        // 予定日 = 1で検索した同項目
        hostsend.setPlanDay(workInfo.getPlanDay());
        // バッチNo. = 1で検索した同項目
        hostsend.setBatchNo(workInfo.getBatchNo());
        // 荷主コード = 1で検索した同項目
        hostsend.setConsignorCode(workInfo.getConsignorCode());
        // 荷主名称 = 1で検索した同項目
        hostsend.setConsignorName(String.valueOf(workInfo.getValue(Consignor.CONSIGNOR_NAME, "")));
        // 出荷先コード = 1で検索した同項目
        hostsend.setCustomerCode(workInfo.getCustomerCode());
        // 出荷先名称 = 1で検索した同項目
        hostsend.setCustomerName(String.valueOf(workInfo.getValue(Customer.CUSTOMER_NAME, "")));
        // 出荷伝票No. = 1で検索した同項目
        hostsend.setShipTicketNo(workInfo.getShipTicketNo());
        // 出荷伝票行 = 1で検索した同項目
        hostsend.setShipLineNo(workInfo.getShipLineNo());
        // 商品コード = 1で検索した同項目
        hostsend.setItemCode(workInfo.getItemCode());
        // 商品名称 = 1で検索した同項目
        hostsend.setItemName(String.valueOf(workInfo.getValue(Item.ITEM_NAME, "")));
        // JANコード = 1で検索した同項目
        hostsend.setJan(String.valueOf(workInfo.getValue(Item.JAN, "")));
        // ケースITF = 1で検索した同項目
        hostsend.setItf(String.valueOf(workInfo.getValue(Item.ITF, "")));
        // ボールITF = 1で検索した同項目
        hostsend.setBundleItf(String.valueOf(workInfo.getValue(Item.BUNDLE_ITF, "")));
        // ケース入数 = 1で検索した同項目
        hostsend.setEnteringQty(WmsFormatter.getInt(String.valueOf(workInfo.getValue(Item.ENTERING_QTY, 0))));
        // ボール入数 = 1で検索した同項目
        hostsend.setBundleEnteringQty(WmsFormatter.getInt(String.valueOf(workInfo.getValue(Item.BUNDLE_ENTERING_QTY, 0))));
        // 予定ロットNo. = 1で検索した同項目
        hostsend.setPlanLotNo(workInfo.getPlanLotNo());
        // 予定数 = 1で検索した同項目
        hostsend.setPlanQty(workInfo.getPlanQty());
        // 実績数 = 1で検索した同項目
        hostsend.setResultQty(workInfo.getResultQty());
        // 欠品数 = 1で検索した同項目
        hostsend.setShortageQty(workInfo.getShortageQty());
        // 実績ロットNo = 1で検索した同項目
        hostsend.setResultLotNo(workInfo.getResultLotNo());
        // 結果バース = 1で検索した同項目
        hostsend.setResultBerth(workInfo.getResultBerth());
        // 実績報告区分 = ”0”（未報告）
        hostsend.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        // 出荷検品ユーザID = 1で検索した同項目
        hostsend.setShipUserId(workInfo.getShipUserId());
        // 出荷検品ユーザ名称 = 1で検索したユーザ名
        hostsend.setShipUserName(String.valueOf(workInfo.getValue(Com_loginuser.USERNAME, "")));
        // 出荷検品端末No、RFTNo = 1で検索した同項目
        hostsend.setShipTerminalNo(workInfo.getShipTerminalNo());
        // 出荷検品作業秒数 = 1で検索した同項目
        hostsend.setShipWorkSecond(workInfo.getShipWorkSecond());
        // バース登録ユーザID = 1で検索した同項目
        hostsend.setBerthUserId(workInfo.getBerthUserId());
        // バース登録ユーザ名称 = 1で検索したユーザ名
        hostsend.setBerthUserName(String.valueOf(workInfo.getValue(Com_loginuser.USERNAME, "")));
        // バース登録端末No、RFTNo = 1で検索した同項目
        hostsend.setBerthTerminalNo(workInfo.getBerthTerminalNo());
        // バース登録作業秒数 = 1で検索した同項目
        hostsend.setBerthWorkSecond(workInfo.getBerthWorkSecond());
        // 登録処理名 = インスタンス変数のクラスよりプログラム名を取得
        hostsend.setRegistPname(getCallerName());
        // 最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得
        hostsend.setLastUpdatePname(getCallerName());

        // 出荷作業情報．バース登録ユーザ名称を取得します
        String berthUserName = getBerthUserName(workInfo.getJobNo());
        if (!StringUtil.isBlank(berthUserName))
        {
            hostsend.setBerthUserName(berthUserName);
        }

        // 出荷実績送信情報の登録を行う。
        handler.create(hostsend);
    }


    /**
     * 出荷作業情報．バース登録ユーザIDに対応するログインユーザ名を取得します。
     * 
     * @param jobNo 作業No.
     * @return ユーザ名
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     */
    protected String getBerthUserName(String jobNo)
            throws ReadWriteException,
                NoPrimaryException
    {
        if (StringUtil.isBlank(jobNo))
        {
            return null;
        }

        // ログインユーザ設定情報を検索する（findPrimary）
        ShipWorkInfoHandler handler = new ShipWorkInfoHandler(getConnection());
        ShipWorkInfoSearchKey searchkey = new ShipWorkInfoSearchKey();


        // 取得条件設定
        // ログインユーザ設定．ユーザ名
        FieldName field = Com_loginuser.USERNAME;
        searchkey.setCollect(field);

        // 検索条件設定
        // 出荷作業情報．作業No. = パラメータの作業No.
        searchkey.setJobNo(jobNo);

        // 結合条件設定
        // 出荷作業情報．バース登録ユーザID = ログインユーザ設定．ユーザID
        searchkey.setJoin(ShipWorkInfo.BERTH_USER_ID, Com_loginuser.USERID);

        // 検索の実行
        Entity result = handler.findPrimary(searchkey);

        if (result == null)
        {
            return null;
        }

        // ユーザ名の返却
        return String.valueOf(result.getValue(field, ""));
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
        return "$Id: ShipWorkInfoController.java 5028 2009-09-18 04:31:29Z kishimoto $";
    }
}
