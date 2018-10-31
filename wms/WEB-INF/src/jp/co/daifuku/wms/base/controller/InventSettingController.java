// $Id: InventSettingController.java 5020 2009-09-17 10:25:05Z kishimoto $
package jp.co.daifuku.wms.base.controller;

import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WMSSequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.InventSettingHandler;
import jp.co.daifuku.wms.base.dbhandler.InventSettingSearchKey;
import jp.co.daifuku.wms.base.entity.InventSetting;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.exception.OperatorException;
import jp.co.daifuku.wms.handler.util.HandlerSysDefines;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 棚卸設定情報コントローラクラスです。
 *
 *
 * @version $Revision: 5020 $, $Date: 2009-09-17 19:25:05 +0900 (木, 17 9 2009) $
 * @author  ss
 * @author  Last commit: $Author: kishimoto $
 */
public class InventSettingController
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
     * コントローラが使用するデータベースコネクションと、呼び出し元プログラム
     * (ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param pname 呼び出し元プログラム名
     */
    public InventSettingController(Connection conn, Class pname)
    {
        super(conn, pname);
    }


    //------------------------------------------------------------
    // public methods
    //------------------------------------------------------------
    /**
     * 棚卸範囲チェック処理を行います<br>
     * 棚卸作業可能な棚範囲かチェックします。<br>
     * 範囲内の場合、スケジュールNoを返します。<br>
     * 
     * @param consignor 荷主コード
     * @param area エリアNo.
     * @param location 棚No.
     * @return スケジュールNo.
     * @throws ReadWriteException データベースエラー
     * @throws OperatorException データが存在しなかった場合内容が返されます。
     * @throws NoPrimaryException 作業データ重複
     */
    public String rangeCheck(String consignor, String area, String location)
            throws ReadWriteException,
                OperatorException,
                NoPrimaryException
    {
        // 棚卸設定情報ハンドラー
        InventSettingHandler handler = new InventSettingHandler(getConnection());

        // 棚卸設定情報検索キー
        InventSettingSearchKey key = new InventSettingSearchKey();

        /* 検索条件の指定 */
        // 状態フラグ(開始)
        key.setStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_SETTING_START);
        // 荷主コード
        key.setConsignorCode(consignor);
        // エリア
        key.setAreaNo(area);
        // 開始棚
        key.setFromLocationNo(location, "<=");
        // 終了棚
        key.setToLocationNo(location, ">=");

        // 棚卸設定情報検索
        InventSetting inventSetting = (InventSetting)handler.findPrimary(key);

        if (inventSetting == null)
        {
            // 該当するデータ無しの場合
            throw new OperatorException(OperatorException.ERR_INVENT_LOCATION_OUTSIDE_RANGE);
        }

        // 取得したスケジュールNo.を返す
        return inventSetting.getScheduleNo();
    }

    /**
     * 棚卸設定追加処理を行います。<br>
     * 新規棚卸設定データを作成し、追加したレコードのスケジュールNoを返す。<br>
     * 
     * @param tgt 棚卸作業情報
     * <ol>
     * <li>荷主コード
     * <li>エリアNo.
     * <li>開始棚
     * <li>終了棚
     * </ol>
     * @return スケジュールNo
     * @throws ReadWriteException データベースエラー
     * @throws OperatorException データが存在しなかった場合内容が返されます。
     * @throws DataExistsException 既にデータが登録済みの場合
     */
    public String insertInventSetting(InventSetting tgt)
            throws ReadWriteException,
                OperatorException,
                DataExistsException
    {
        // シーケンスハンドラー
        WMSSequenceHandler wHandler = new WMSSequenceHandler(getConnection());

        // 棚卸設定情報ハンドラー
        InventSettingHandler handler = new InventSettingHandler(getConnection());

        // 棚卸設定情報検索キー
        InventSettingSearchKey key = new InventSettingSearchKey();

        /* 検索条件の指定 */
        // 状態フラグ(開始)
        key.setStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_SETTING_START);
        // 荷主コード
        key.setConsignorCode(tgt.getConsignorCode());
        // エリア
        key.setAreaNo(tgt.getAreaNo());
        // 開始棚
        key.setFromLocationNo(tgt.getToLocationNo(), "<=");
        // 終了棚
        key.setToLocationNo(tgt.getFromLocationNo(), ">=");

        // 棚卸設定情報検索
        InventSetting[] inventSetting = (InventSetting[])handler.find(key);

        if (inventSetting.length > 0)
        {
            // 1件以上該当した場合
            throw new OperatorException(OperatorException.ERR_INVENT_LOCATION_DUPLICATED);
        }

        // スケジュールNo.採番
        String scheduleNo = wHandler.nextInventScheduleNo();

        /* 棚卸設定情報エンティティ編集 */
        // スケジュールNo.
        tgt.setScheduleNo(scheduleNo);
        // 状態フラグ
        tgt.setStatusFlag(SystemDefine.STATUS_FLAG_INVENTORY_SETTING_START);
        // 登録処理名
        tgt.setRegistPname(getCallerName());
        // 最終更新処理名
        tgt.setLastUpdatePname(getCallerName());

        // 棚卸設定情報登録        
        handler.create(tgt);

        return scheduleNo;
    }

    /**
     * 指定のスケジュールNoの棚卸設定情報をロックします。<br>
     * 棚卸作業情報の新規追加前に呼び出して、削除（棚卸キャンセル）処理中に
     * その範囲の棚卸データが新規追加されることを防ぐために使用します。<br>
     * 削除処理中の場合は、ロックタイムアウトがスローされます。
     * 
     * @param schNo スケジュールNo
     * @throws ReadWriteException   データベースエラー
     * @throws LockTimeOutException ロックタイムアウト
     */
    public void lockInventSetting(String schNo)
            throws ReadWriteException,
                LockTimeOutException
    {
        // 棚卸設定情報ハンドラー
        InventSettingHandler handler = new InventSettingHandler(getConnection());

        // 棚卸設定情報検索キー
        InventSettingSearchKey key = new InventSettingSearchKey();

        /* 検索条件の指定 */
        key.setScheduleNo(schNo);

        // 棚卸設定情報検索
        handler.findForUpdate(key, HandlerSysDefines.WAIT_SEC_NOWAIT);

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
        return "$Id: InventSettingController.java 5020 2009-09-17 10:25:05Z kishimoto $";
    }
}
