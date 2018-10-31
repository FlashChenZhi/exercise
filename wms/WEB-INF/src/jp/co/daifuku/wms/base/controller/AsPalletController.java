// $Id: AsPalletController.java,v 1.14 2007/06/05
// 08:40:04 yoshida Exp $
package jp.co.daifuku.wms.base.controller;

/*
 * Copyright(c) 2000-2007 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of
 * DAIFUKU Co.,Ltd. Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.PalletAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PalletHandler;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Pallet;
import jp.co.daifuku.wms.base.entity.Stock;

/**
 * AS/RSパッケージ向けのパレットコントローラクラスです。
 * 
 * 
 * @version $Revision: 87 $, $Date: 2008-10-04 12:07:38 +0900 (土, 04 10 2008) $
 * @author ss
 * @author Last commit: $Author: admin $
 */
// UPDATE_SS (2007-07-06)
public class AsPalletController
        extends AbstractController
{
    // ------------------------------------------------------------
    // fields (upper case only)
    // ------------------------------------------------------------
    // public static final int FIELD_VALUE = 1 ;

    // ------------------------------------------------------------
    // class variables (prefix '$')
    // ------------------------------------------------------------
    // private static String $classVar ;

    // ------------------------------------------------------------
    // constructors
    // ------------------------------------------------------------
    /**
     * コントローラが使用するデータベースコネクションと、呼び出し元クラス
     * (ロギング,更新プログラムの保存用に使用されます)
     * 
     * @param conn データベースコネクション
     * @param caller 呼び出し元クラス
     * @throws ScheduleException システム定義不整合
     * @throws ReadWriteException データベースエラー
     */
    public AsPalletController(Connection conn, Class caller)
            throws ReadWriteException,
                ScheduleException
    {
        super(conn, caller);
    }

    // ------------------------------------------------------------
    // public methods
    // ------------------------------------------------------------
    /**
     * 入庫時のパレットの在庫状態の更新処理を行います。
     * パレットに対する他の引当が有れば出庫予約、無ければ実棚、
     * 商品コードが二重格納用商品コードであれば、異常棚に更新します。
     * 
     * @param palletId パレットId
     * @return パレットに他の引当が有ればtrue、無ければfalseを返します。
     * @throws ReadWriteException データベースエラー
     * @throws NotFoundException 在庫情報がnullのとき
     */
    public boolean updatePalletStatusFlagStorage(String palletId)
            throws ReadWriteException,
                NotFoundException
    {
        boolean allocate = false;

        PalletAlterKey pltAKey = new PalletAlterKey();
        PalletHandler plth = new PalletHandler(getConnection());

        //<jp> パレットに対する他の引当がないか確認</jp>
        //<en> Checks whether/not there are no other allocation for pallets.</en>
        CarryInfoHandler carryh = new CarryInfoHandler(getConnection());
        CarryInfoSearchKey countKey = new CarryInfoSearchKey();

        countKey.setPalletId(palletId);
        if (carryh.count(countKey) == 0)
        {
            // パレットから在庫情報を検索
            StockSearchKey sSKey = new StockSearchKey();
            StockHandler sHandle = new StockHandler(getConnection());
            sSKey.setPalletId(palletId);
            Stock[] stocks = (Stock[])sHandle.find(sSKey);

            //<jp> 商品コードが二重格納用商品コードであれば、異常棚に変更する。</jp>
            //<en> If there are any item codes for double occupations, alter thier states to error locations.</en>
            if (stocks[0].getItemCode().equals(WmsParam.IRREGULAR_ITEMCODE))
            {
                //<jp> 状態を異常棚に変更</jp>
                //<en> Alters the location status to error.</en>
                pltAKey.updateStatusFlag(Pallet.PALLET_STATUS_IRREGULAR);
            }
            else
            {
                //<jp> 状態を実棚に変更</jp>
                //<en> Alter location status to loaded.</en>
                pltAKey.updateStatusFlag(Pallet.PALLET_STATUS_REGULAR);
            }

            //<jp> 引当フラグを未引当に変更</jp>
            //<en> Alters the allocation flag to unallocated.</en>
            pltAKey.updateAllocationFlag(Pallet.ALLOCATION_FLAG_NOT_ALLOCATED);
        }
        else
        {
            //<jp> 状態を出庫予約に変更</jp>
            //<en> Alters the status to 'reserved for retrieval'.</en>
            pltAKey.updateStatusFlag(Pallet.PALLET_STATUS_RETRIEVAL_PLAN);

            allocate = true;
        }

        pltAKey.setPalletId(palletId);
        pltAKey.updateLastUpdatePname(getCallerName());
        plth.modify(pltAKey);

        return allocate;
    }

    // ------------------------------------------------------------
    // accessor methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // package methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // protected methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // private methods
    // ------------------------------------------------------------

    // ------------------------------------------------------------
    // utility methods
    // ------------------------------------------------------------
    /**
     * このクラスのリビジョンを返します。
     * 
     * @return リビジョン文字列。
     */
    public static String getVersion()
    {
        return "$Id: AsPalletController.java 87 2008-10-04 03:07:38Z admin $";
    }
}
