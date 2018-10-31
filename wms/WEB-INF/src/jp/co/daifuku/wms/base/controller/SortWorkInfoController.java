// $Id: SortWorkInfoController.java 5028 2009-09-18 04:31:29Z kishimoto $
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
import jp.co.daifuku.wms.base.dbhandler.SortHostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.SortWorkInfoSearchKey;
import jp.co.daifuku.wms.base.entity.Com_loginuser;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Customer;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.SortHostSend;
import jp.co.daifuku.wms.base.entity.SortWorkInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.util.WmsFormatter;
import jp.co.daifuku.wms.handler.Entity;


/**
 * 仕分作業情報を操作するためのコントローラクラスです。
 *
 * @version $Revision: 5028 $, $Date: 2009-09-18 13:31:29 +0900 (金, 18 9 2009) $
 * @author  Last commit: $Author: kishimoto $
 */
public class SortWorkInfoController
        extends AbstractController
{
    //------------------------------------------------------------
    // fields (upper case only)
    //------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;
    private SortHostSendHandler handler = null;

    private SortWorkInfoHandler wihandler = null;

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
    public SortWorkInfoController(Connection conn, Class caller)
    {
        super(conn, caller);

        handler = new SortHostSendHandler(getConnection());
        wihandler = new SortWorkInfoHandler(getConnection());
    }

    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 作業情報完了処理を行います。<BR>
     * 該当仕分作業情報データの状態を完了に更新し、
     * ハードウェア区分、実績数、欠品数、実績ロットNo.、作業日、ユーザID、端末No、作業秒数をセットします。<BR>
     * パラメータの作業Noより仕分作業情報、各マスタを検索して仕分実績送信情報を作成します。
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
    public void completeWorkInfo(SortWorkInfo src, SortWorkInfo result, WmsUserInfo ui)
            throws ReadWriteException,
                NotFoundException,
                DataExistsException,
                NoPrimaryException,
                ScheduleException
    {
        // 仕分作業情報を更新します。
        SortWorkInfoAlterKey alterKey = new SortWorkInfoAlterKey();

        // 更新内容のセット
        // 設定単位キー
        if (!StringUtil.isBlank(result.getSettingUnitKey()))
        {
            alterKey.updateSettingUnitKey(result.getSettingUnitKey());
        }
        // 仕分作業情報．状態フラグ = ”4”（完了）
        alterKey.updateStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
        // 仕分作業情報．ハードウェア区分 = パラメータの完了情報のハードウェア区分 ※Null以外の場合、更新対象
        if (!StringUtil.isBlank(result.getHardwareType()))
        {
            alterKey.updateHardwareType(result.getHardwareType());
        }
        // 仕分作業情報．実績数 = パラメータの完了情報の実績数
        alterKey.updateResultQty(result.getResultQty());
        // 仕分作業情報．欠品数 = パラメータの完了情報の欠品数
        alterKey.updateShortageQty(result.getShortageQty());
        // 仕分作業情報．実績ロットNo. = パラメータの完了情報の実績ロットNo.
        alterKey.updateResultLotNo(result.getResultLotNo());
        // 仕分作業情報．作業日 = パラメータの完了情報の作業日
        alterKey.updateWorkDay(result.getWorkDay());
        // 仕分作業情報．ユーザID = パラメータのWMSユーザ情報のユーザID
        alterKey.updateUserId(ui.getUserId());
        // 仕分作業情報．端末No. = パラメータのWMSユーザ情報の端末No.
        alterKey.updateTerminalNo(ui.getTerminalNo());
        // 仕分作業情報．作業秒数 = パラメータの完了情報の作業秒数
        alterKey.updateWorkSecond(result.getWorkSecond());
        // 仕分作業情報．最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得
        alterKey.updateLastUpdatePname(getCallerName());

        // 更新条件
        alterKey.setJobNo(src.getJobNo());

        // 更新の実行
        wihandler.modify(alterKey);

        // 仕分実績送信情報の登録を行う。
        insertHostSendByWorkInfo(src.getJobNo());
    }

    /**
     * 仕分実績送信情報登録処理を行います。<BR>
     * パラメータの作業Noより仕分作業情報を検索して仕分実績送信情報を作成します。<BR>
     * 各マスタ情報より必要項目を検索してセットします。
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
        // 仕分作業情報、及び各マスタ情報を結合して検索する（findPrimary）
        Entity entity = getWorkInfo(jobNo);

        if (entity == null)
        {
            // 検索結果がなかった場合、エラーを通知し処理終了
            throw new ScheduleException();
        }

        // 仕分実績送信情報を登録する。
        createHostSendByWorkInfo(entity);
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
     * パラメータの作業Noより仕分作業情報を検索します。
     * 
     * @param jobNo 作業No
     * @return　検索された仕分作業情報
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     */
    protected Entity getWorkInfo(String jobNo)
            throws ReadWriteException,
                NoPrimaryException
    {
        // 仕分作業情報を検索します。
        SortWorkInfoSearchKey searchKey = new SortWorkInfoSearchKey();

        // 取得項目
        // 仕分作業情報．作業日
        searchKey.setWorkDayCollect();
        // 仕分作業情報．作業No.
        searchKey.setJobNoCollect();
        // 仕分作業情報．設定単位キー
        searchKey.setSettingUnitKeyCollect();
        // 仕分作業情報．集約作業No
        searchKey.setCollectJobNoCollect();
        // 仕分作業情報．クロスドック連携キー
        searchKey.setCrossDockUkeyCollect();
        // 仕分作業情報．作業区分
        searchKey.setJobTypeCollect();
        // 仕分作業情報．状態フラグ
        searchKey.setStatusFlagCollect();
        // 仕分作業情報．ハードウェア区分
        searchKey.setHardwareTypeCollect();
        // 仕分作業情報．予定一意キー
        searchKey.setPlanUkeyCollect();
        // 仕分作業情報．予定日
        searchKey.setPlanDayCollect();
        // 仕分作業情報．バッチNo.
        searchKey.setBatchNoCollect();
        // 仕分作業情報．荷主コード
        searchKey.setConsignorCodeCollect();
        // 荷主マスタ情報．荷主名称
        searchKey.setCollect(Consignor.CONSIGNOR_NAME);
        // 仕分作業情報．出荷先コード
        searchKey.setCustomerCodeCollect();
        // 出荷先マスタ情報．出荷先名称
        searchKey.setCollect(Customer.CUSTOMER_NAME);
        // 仕分作業情報．出荷伝票No
        searchKey.setShipTicketNoCollect();
        // 仕分作業情報．出荷伝票行
        searchKey.setShipLineNoCollect();
        // 仕分作業情報．仕分場所
        searchKey.setSortingPlaceCollect();
        // 仕分作業情報．商品コード
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
        // 仕分作業情報．予定ロットNo
        searchKey.setPlanLotNoCollect();
        // 仕分作業情報．予定数
        searchKey.setPlanQtyCollect();
        // 仕分作業情報．実績数
        searchKey.setResultQtyCollect();
        // 仕分作業情報．欠品数
        searchKey.setShortageQtyCollect();
        // 仕分作業情報．実績ロットNo
        searchKey.setResultLotNoCollect();
        // 仕分作業情報．ユーザID
        searchKey.setUserIdCollect();
        // ログインユーザ設定．ユーザ名
        searchKey.setCollect(Com_loginuser.USERNAME);
        // 仕分作業情報．端末No、RFTNo
        searchKey.setTerminalNoCollect();
        // 仕分作業情報．作業秒数
        searchKey.setWorkSecondCollect();


        // 検索条件設定
        // 出荷作業情報．作業No. = パラメータの作業No.
        searchKey.setJobNo(jobNo);

        // 結合条件
        // 仕分作業情報．荷主コード = 荷主マスタ情報．荷主コード
        searchKey.setJoin(SortWorkInfo.CONSIGNOR_CODE, "", Consignor.CONSIGNOR_CODE, "(+)");
        // 仕分作業情報．荷主コード = 出荷先マスタ情報．荷主コード
        searchKey.setJoin(SortWorkInfo.CONSIGNOR_CODE, "", Customer.CONSIGNOR_CODE, "(+)");
        // 仕分作業情報．出荷先コード = 出荷先マスタ情報．出荷先コード
        searchKey.setJoin(SortWorkInfo.CUSTOMER_CODE, "", Customer.CUSTOMER_CODE, "(+)");
        // 仕分作業情報．荷主コード = 商品マスタ情報．荷主コード
        searchKey.setJoin(SortWorkInfo.CONSIGNOR_CODE, "", Item.CONSIGNOR_CODE, "(+)");
        // 仕分作業情報．商品コード = 商品マスタ情報．商品コード
        searchKey.setJoin(SortWorkInfo.ITEM_CODE, "", Item.ITEM_CODE, "(+)");
        // 仕分作業情報．ユーザID = ログインユーザ設定．ユーザID（+）
        searchKey.setJoin(SortWorkInfo.USER_ID, "", Com_loginuser.USERID, "(+)");

        return wihandler.findPrimary(searchKey);
    }

    /**
     * パラメータの仕分作業情報を元に、仕分実績送信情報を登録します。
     * 
     * @param entity 仕分作業情報
     * @throws ReadWriteException データベース処理でエラーを検出した場合にスローされます。
     * @throws DataExistsException 仕分実績送信情報が登録済みの場合にスローされます。
     * @throws NoPrimaryException 一意の項目に対してデータが複数件存在する場合にスローされます。
     */
    protected void createHostSendByWorkInfo(Entity entity)
            throws DataExistsException,
                ReadWriteException,
                NoPrimaryException
    {
        // キャスト
        SortWorkInfo workInfo = (SortWorkInfo)entity;

        // 出荷実績送信情報を登録します。
        SortHostSend hostsend = new SortHostSend();

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
        // 状態フラグ = 1で検索した同項目
        hostsend.setStatusFlag(workInfo.getStatusFlag());
        // ハードウェア区分 = 1で検索した同項目
        hostsend.setHardwareType(workInfo.getHardwareType());
        // 予定一意キー = 1で検索した同項目
        hostsend.setPlanUkey(workInfo.getPlanUkey());
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
        // 仕分場所 = 1で検索した同項目
        hostsend.setSortingPlace(workInfo.getSortingPlace());
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
        // 実績報告区分 = ”0”（未報告）
        hostsend.setReportFlag(SystemDefine.REPORT_FLAG_NOT_REPORT);
        // ユーザID = 1で検索した同項目
        hostsend.setUserId(workInfo.getUserId());
        // ユーザ名称 = 1で検索したユーザ名
        hostsend.setUserName(String.valueOf(workInfo.getValue(Com_loginuser.USERNAME, "")));
        // 端末No、RFTNo = 1で検索した同項目
        hostsend.setTerminalNo(workInfo.getTerminalNo());
        // 作業秒数 = 1で検索した同項目
        hostsend.setWorkSecond(workInfo.getWorkSecond());
        // 登録処理名 = インスタンス変数のクラスよりプログラム名を取得
        hostsend.setRegistPname(getCallerName());
        // 最終更新処理名 = インスタンス変数のクラスよりプログラム名を取得
        hostsend.setLastUpdatePname(getCallerName());

        // 仕分実績送信情報の登録を行う。
        handler.create(hostsend);
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
        return "$Id: SortWorkInfoController.java 5028 2009-09-18 04:31:29Z kishimoto $";
    }
}
